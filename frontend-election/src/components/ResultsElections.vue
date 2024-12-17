<template>
  <div class="background">
    <div class="text">
      <h1>Shadow election results and information</h1>
      <p>On this page, you will find the results of the teen votes and how many have voted for each political party.</p>
    </div>
  </div>

  <div class="button-container">
    <button>
      <router-link to="/CompareElections" :style="{ color: '#ffffff' }">Compare with the real elections!</router-link>
    </button>
  </div>

  <div class="chart-container">
    <div class="svg-wrapper">
      <div id="visualization" v-if="!loading && seatDistribution.length > 0"></div>
    </div>
    <div v-if="loading">Loading...</div>
    <div v-if="!loading && noData">No data available.</div>
  </div>

  <!-- Party selection dropdown and additional information -->
  <div class="party-selection">
    <label for="party-select">Select a Political Party:</label>
    <select id="party-select" v-model="selectedParty" @change="updatePartyInfo">
      <option v-for="(name, id) in parties" :value="id" :key="id">{{ name }}</option>
    </select>
  </div>

  <div class="party-details-display">
    <h3 v-if="selectedParty">Selected Party: {{ parties[selectedParty] }}</h3>
    <h3 v-else>No party selected</h3>
    <p><strong>Percentage of Seats:</strong> {{ calculatePartyPercentage(selectedParty) }}%</p>
    <p><strong>Total Amount of Votes:</strong> {{ calculatePartyVotes(selectedParty) }}</p>
    <p><strong>Political Spectrum:</strong> {{ getPoliticalSpectrum(selectedParty) }}</p>
  </div>

</template>

<script lang="ts">
import * as d3 from "d3";

interface Party {
  partyId: number;
  partyName: string;
  color: string;
}

interface SeatDistribution {
  party: string;
  seats: number;
  color: string;
}

export default {
  data() {
    return {
      seatDistribution: [] as SeatDistribution[], // Explicit type for seat distribution
      loading: true,
      noData: false,
      parties: {} as Record<string, string>, // Mapping of partyId to partyName
      selectedParty: null as string | null, // Selected party can be null or a string
      voteCounts: {} as Record<string, number>, // Mapping of partyId to vote count
    };
  },
  mounted() {
    this.fetchParties();
  },
  methods: {
    /**
     * Fetches the list of political parties from the API and updates the parties object.
     * Once parties are fetched, it triggers the fetching of vote counts.
     */
    async fetchParties() {
      try {
        const response = await fetch("http://localhost:8080/api/parties");
        if (!response.ok) throw new Error("Network response was not ok");

        const partyData = await response.json();
        if (!Array.isArray(partyData)) throw new Error("Invalid party data format");

        // Create a mapping of party IDs to names
        this.parties = {};
        partyData.forEach(party => {
          if (party.partyId && party.party_name) {
            this.parties[party.partyId] = party.party_name;
            console.log(`Party ID: ${party.partyId}, Party Name: ${party.party_name}`);
          } else {
            console.error("Invalid party object:", party);
          }
        });

        await this.fetchVoteCounts();
      } catch (error) {
        console.error("Error fetching parties:", error);
        this.loading = false;
      }
    },


    /**
     * Fetches the vote counts for each political party and calculates seat distribution.
     * This method also handles the case when no votes are cast.
     */
    async fetchVoteCounts() {
      try {
        const response = await fetch("http://localhost:8080/auth/votes/counts");
        if (!response.ok) {
          console.error("Network response was not ok");
          this.loading = false;
          return;
        }


        if (!response.ok) {
          console.error("Network response was not ok");
          this.loading = false;
          return;
        }

        const voteCounts = await response.json();
        if (typeof voteCounts !== "object" || voteCounts === null) {
          console.error("Invalid vote counts format");
          this.loading = false;
          return;
        }

        this.voteCounts = voteCounts; // Store the raw vote counts

        // Calculate total votes
        const totalVotes = Object.values(voteCounts as Record<string, number>)
            .reduce((acc: number, count: number) => acc + count, 0);

        if (totalVotes === 0) {
          this.loading = false;
          this.noData = true;
          console.warn("No votes cast. Exiting early.");
          return;
        }

        // Map vote counts to seat distribution with seat allocation
        this.seatDistribution = Object.entries(voteCounts).map(([partyId, votes]) => {
          const seats = Math.round(150 * ((votes as number) / totalVotes));
          const partyName = this.parties[partyId] || "Unknown Party";
          const color = this.getPartyColor(Number(partyId));

          return {
            party: partyName,
            seats,
            color,
          };
        });

        // Filter out parties with 0 seats
        this.seatDistribution = this.seatDistribution.filter(party => party.seats > 0);

        this.loading = false;
        this.noData = this.seatDistribution.length === 0;

        // Debugging logs
        console.log("Loading:", this.loading);
        console.log("Seat Distribution Length:", this.seatDistribution.length);

        // Wait for DOM updates before calling createVisualization
        this.$nextTick(() => {
          setTimeout(() => {
            const visualizationDiv = document.getElementById("visualization");


            if (visualizationDiv) {
              this.createVisualization(); // Ensure that this call is made only when the div exists
            } else {
              console.error("Visualization div not found.");
            }
          }, 0);
        });
      } catch (error) {
        console.error("Error fetching vote counts:", error);
        this.loading = false;
      }
    },


    /**
     * Calculates the total votes for a specific political party based on its ID.
     * @param {string} partyId - The ID of the party.
     * @returns {number} - The total number of votes for the selected party.
     */
    calculatePartyVotes(partyId: any) {
      if (!partyId) return 0;


      return this.voteCounts[partyId] || 0; // If no votes, return 0
    },


    /**
     * Calculates the percentage of seats occupied by a specific party in the seat distribution.
     * @param {string} partyId - The ID of the party.
     * @returns {string} - The percentage of seats occupied by the party, rounded to two decimal places.
     */
    calculatePartyPercentage(partyId: any) {
      // Check if seatDistribution is populated
      if (!this.seatDistribution || this.seatDistribution.length === 0) {
        console.warn("Seat distribution is empty.");
        return 0; // Return 0 if no seat distribution is available
      }


      const totalVotes = this.seatDistribution.reduce((acc, party) => acc + party.seats, 0);

      // Find the party in seat distribution
      const partyVotes = this.seatDistribution.find(party => party.party === this.parties[partyId]);

      // If partyVotes is not found, return 0 and log a warning
      if (!partyVotes) {
        console.warn(`No votes found for party: ${this.parties[partyId]}`);
        return 0;
      }

      // Calculate and return the percentage of seats
      return ((partyVotes.seats / totalVotes) * 100).toFixed(2);
    },


    /**
     * Returns the political spectrum (e.g., Left, Right, Center) of a specific party based on its ID.
     * @param {string} partyId - The ID of the party.
     * @returns {string} - The political spectrum of the party.
     */
    getPoliticalSpectrum(partyId: any): string {
      const spectrumMap: Record<number, string> = {
        1: "Center-Right", // VVD
        2: "Center",       // D66
        3: "Left",         // PvdA
        4: "Left",         // GroenLinks
        5: "Center-Right", // CDA
        6: "Far-Right",    // PVV
        7: "Left",         // SP
        8: "Right",        // FVD
        9: "Center-Right", // CU
        10: "Left",        // PvdD
        11: "Far-Right",   // SGP
        12: "Center-Left", // Volt
        13: "Right",       // JA21
        14: "Left",        // BIJ1
        15: "Center-Right",// BBB
        16: "Center-Left"  // DENK
      };

      return spectrumMap[partyId as keyof typeof spectrumMap] || "Unknown"; // Default to "Unknown" if no match
    },

    updatePartyInfo() {
      if (this.selectedParty) {
        console.log(`Selected Party: ${this.selectedParty}`);
        // You can add any other logic to handle the selection, like fetching more data or updating other properties
      }
    },

    /**
     * Creates a D3.js visualization of the seat distribution in the election.
     * This method uses SVG to display each party's seats in a very specific lay out that's also used for the real party seats.
     */
    createVisualization() {
      console.log("Creating visualization...");

      // Set the SVG dimensions
      const svgWidth = 1400;
      const svgHeight = 300;

      // Ensure that the #visualization div is selected correctly
      const visualizationDiv = d3.select("#visualization");
      console.log("Visualisation div exists:", !!document.getElementById("visualization"));
      console.log("Visualization Div Content:", visualizationDiv.node()); // Check what is being selected
      console.log("Visualization div selected:", visualizationDiv.empty()); // Should log false if the selection is successful

      // Clear any existing content in the #visualization div to avoid duplications
      visualizationDiv.selectAll("*").remove();

      // Create an SVG element and append a group for transformations
      const svg = visualizationDiv
          .append("svg")
          .attr("width", svgWidth)
          .attr("height", svgHeight)
          .attr("viewBox", `0 0 ${svgWidth} ${svgHeight}`)
          .attr("preserveAspectRatio", "xMidYMid meet")
          .append("g")
          .attr("transform", `translate(${svgWidth / 2}, ${svgHeight})`);

      // Log the D3 selection to verify contents
      console.log("D3 Visualization Div:", visualizationDiv);
      console.log("D3 Visualization Div Nodes:", visualizationDiv.nodes());

      const totalSeats = 150;
      const seatSize = 15;
      const seatData = this.seatDistribution.flatMap(party => Array(party.seats).fill(party));

      const pattern = [2, 3, 4, 5, 5, 6];
      const segments = 6;
      let currentSeatIndex = 0;

      const tooltip = d3.select("#visualization")
          .append("div")
          .style("display", "none")
          .style("position", "absolute")
          .style("background", "#f9f9f9")
          .style("padding", "5px")
          .style("border", "1px solid #ccc")
          .style("border-radius", "5px")
          .style("pointer-events", "none");

      if (tooltip.empty()) {
        console.error("Tooltip creation failed.");
      }

      for (let segment = 0; segment < segments; segment++) {
        const startAngle = (Math.PI / 6) * segment;
        const endAngle = (Math.PI / 6) * (segment + 1);

        for (let row = 0; row < pattern.length; row++) {
          const seatsInThisRow = pattern[row];
          const radius = 100 + row * (seatSize + 15);
          const angleStep = (endAngle - startAngle) / (seatsInThisRow + 1);

          for (let seat = 0; seat < seatsInThisRow; seat++) {
            const angle = startAngle + (seat + 1) * angleStep;
            const x = radius * Math.cos(angle);
            const y = -radius * Math.sin(angle);

            if (currentSeatIndex < seatData.length) {
              const currentParty = seatData[currentSeatIndex];
              svg.append("circle")
                  .attr("cx", x)
                  .attr("cy", y)
                  .attr("r", seatSize / 2)
                  .attr("fill", currentParty.color)
                  .attr("stroke", "#fff")
                  .attr("stroke-width", 1)
                  .on("mouseover", (event) => {
                    tooltip
                        .style("display", "block")
                        .html(currentParty.party)
                        .style("top", `${event.pageY + 10}px`)
                        .style("left", `${event.pageX + 10}px`);
                  })
                  .on("mousemove", (event) => {
                    tooltip
                        .style("top", `${event.pageY + 10}px`)
                        .style("left", `${event.pageX + 10}px`);
                  })
                  .on("mouseout", () => {
                    tooltip.style("display", "none");
                  });
            }
            currentSeatIndex++;
          }
        }
      }

      svg.append("text")
          .attr("text-anchor", "middle")
          .attr("y", -15)
          .attr("font-size", "28px")
          .attr("fill", "#000")
          .text(totalSeats);

      // Create the legend container
      const legendContainer = d3.select("#visualization")
          .append("div")
          .attr("class", "legend-container")
          .style("display", "flex")
          .style("flex-direction", "column")
          .style("align-items", "flex-start")
          .style("margin-top", "5px");

// Populate the legend
      this.seatDistribution.forEach((party) => {
        const legendItem = legendContainer.append("div")
            .style("display", "flex")
            .style("align-items", "center")
            .style("margin-top", "2%")
            .style("margin-bottom", "2%")
            .style("margin-left", "10%");

        legendItem.append("div")
            .style("width", "15px")
            .style("height", "15px")
            .style("background-color", party.color);

        legendItem.append("span")
            .text(`${party.party}: ${party.seats} seats`)
            .style("font-size", "14px")
            .style("margin-left", "5px");

    });
    },


    /**
     * Returns the color associated with a specific party based on its ID.
     * @param {string} partyId - The ID of the party.
     * @returns {string} - The color associated with the party.
     */
    getPartyColor(partyId: number): string {
      const colorMap: Record<number, string> = {
        1: "#1f77b4",
        2: "#2ca02c",
        3: "#9467bd",
        4: "#8c564b",
        5: "#e377c2",
        6: "#7f7f7f",
        7: "#bcbd22",
        8: "#17becf",
        9: "#ff7f0e",
        10: "#f58556",
        11: "#ffbb78",
        12: "#2b83ba",
        13: "#55bfc6",
        14: "#6d3c23",
        15: "#d62728",
        16: "#7b8c55",
        17: "#c4e1d2",
        18: "#f1c2c2",
        19: "#e7ba35",
        20: "#c7c7c7",
        21: "#b1c2d2",
        22: "#f0855e",
        23: "#ff736d",
        24: "#98c6e5",
        25: "#a0b1c5",
        26: "#c92b4f",
        27: "#00a0b6",
        28: "#9d5e4d",
        29: "#2f4f4f",
        30: "#ff4e50",
        31: "#e2a99d",
        32: "#7d4c48",
        33: "#b84f56",
        34: "#8eac46",
        35: "#4267b2",
        36: "#a14a72",
        37: "#d2a8f0",
      };

      return colorMap[partyId] || "#ccc"; // Default to gray if no color found
    },
  },
};
</script>

<style scoped>
/* General Background Section */
.background {
  background-color: #24786e99;
  padding: 20px;
  height: 50vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.text {
  width: 100%;
  color: white;
  text-align: center;
}

.text h1 {
  font-size: 36px;
  margin-bottom: 20px;
}

.text p {
  font-size: 18px;
  line-height: 1.5;
  max-width: 800px;
  margin: 0 auto 20px auto;
}

/* Chart Section */
.chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px auto 20px auto;
  max-width: 90%;
}

.svg-wrapper {
  width: 100%;
  margin-bottom: 20px;
}

#visualization {
  margin: 0 auto;
  display: block;
}


.legend-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
  max-width: 800px;
}

.legend-container div {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.legend-container div span {
  margin-left: 8px;
  font-size: 14px;
}

/* Party Selection Section */
.party-selection {
  margin: 20px auto;
  text-align: center;
}

.party-selection label {
  font-size: 16px;
  margin-right: 10px;
}

.party-selection select {
  font-size: 16px;
  padding: 5px 10px;
}

/* Party Details Section */
.party-details-display {
  margin-top: 20px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  text-align: center;
  display: flex;
  flex-direction: column; /* Align contents vertically */
  justify-content: center; /* Center content vertically */
  align-items: center; /* Center content horizontally */
  max-width: 30%; /* Limit the width */
  margin-left: auto; /* Center horizontally */
  margin-right: auto; /* Center horizontally */
}



.party-details-display h3 {
  font-size: 20px;
  margin-bottom: 10px;
  color: #333;
}

.party-details-display p {
  font-size: 16px;
  margin: 5px 0;
  color: #333;
}

/* Button Container */
.button-container {
  text-align: center;
  margin-top: 20px;
}

.button-container button {
  background-color: #28a745;
  color: white;
  font-size: 18px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.button-container button:hover {
  background-color: #218838;
}

/* Responsive Design */
@media (max-width: 768px) {
  .text h1 {
    font-size: 28px;
  }

  .text p {
    font-size: 16px;
    padding: 0 10px;
  }

  .party-selection label,
  .party-selection select {
    font-size: 14px;
  }

  .party-details-display h3,
  .party-details-display p {
    font-size: 14px;
  }

  .legend-container {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
