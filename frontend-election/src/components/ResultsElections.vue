<template>
<div class="background">
<div class="text">
  <h1>Shadow election results and information</h1>
  <p>On this page, you will find the results of the teen votes and how many have voted for each political party.</p>
</div>
</div>

  <div class="button-container">
    <button @click="something">Compare with the real elections!</button>
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
    <h3>Selected Party: {{ parties[selectedParty] }}</h3>
    <p><strong>Percentage of Seats:</strong> {{ calculatePartyPercentage(selectedParty) }}%</p>
    <p><strong>Total Amount of Votes:</strong> {{ calculatePartyVotes(selectedParty) }}</p>
    <p><strong>Political Spectrum:</strong> {{ getPoliticalSpectrum(selectedParty) }}</p>
  </div>

</template>

<script>
import * as d3 from 'd3';

console.log(d3); // Check if d3 is defined and contains the select method

export default {
  data() {
    return {
      seatDistribution: [],
      loading: true,
      noData: false,
      parties: {}, // Object to hold party ID and names
      selectedParty: null, // Currently selected party
      voteCounts: {}, // Store the raw vote counts by party ID
    };
  },
  mounted() {
    this.fetchParties(); // Fetch parties on component mount
  },
  methods: {
    async fetchParties() {
      try {
        const response = await fetch("http://localhost:8080/api/parties");
        if (!response.ok) throw new Error("Network response was not ok");

        const partyData = await response.json();
        if (!Array.isArray(partyData)) throw new Error("Invalid party data format");

        // Create a mapping of party IDs to names
        this.parties = {};
        partyData.forEach(party => {
          if (party.party_id && party.party_name) {
            this.parties[party.party_id] = party.party_name;
            console.log(`Party ID: ${party.party_id}, Party Name: ${party.party_name}`);
          } else {
            console.error("Invalid party object:", party);
          }
        });

        await this.fetchVoteCounts(); // Fetch vote counts after parties are loaded
      } catch (error) {
        console.error("Error fetching parties:", error);
        this.loading = false; // Update loading state even on error
      }
    },

    async fetchVoteCounts() {
      try {
        const response = await fetch("http://localhost:8080/auth/votes/counts");
        if (!response.ok) throw new Error("Network response was not ok");

        const voteCounts = await response.json();
        if (typeof voteCounts !== "object" || voteCounts === null) {
          throw new Error("Invalid vote counts format");
        }

        this.voteCounts = voteCounts; // Store the raw vote counts

        // Calculate total votes
        const totalVotes = Object.values(voteCounts).reduce((acc, count) => acc + count, 0);
        if (totalVotes === 0) {
          this.loading = false;
          this.noData = true;
          console.warn("No votes cast. Exiting early.");
          return;
        }

        // Map vote counts to seat distribution with seat allocation
        this.seatDistribution = Object.entries(voteCounts).map(([partyId, votes]) => {
          const seats = Math.round(150 * (votes / totalVotes));
          const partyName = this.parties[partyId] || "Unknown Party";
          const color = this.getPartyColor(partyId);

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
            console.log("Visualization div exists:", !!visualizationDiv);
            console.log("Visualization div innerHTML:", visualizationDiv.innerHTML);

            if (visualizationDiv) {
              this.createVisualization(); // Ensure that this call is made only when the div exists
            } else {
              console.error("Visualization div not found.");
            }
          }, 0); // Short delay to allow DOM updates
        });
      } catch (error) {
        console.error("Error fetching vote counts:", error);
        this.loading = false;
      }
    },



    calculatePartyVotes(partyId) {
      if (!partyId) return 0;

      // Return the count of votes for the selected party from voteCounts
      return this.voteCounts[partyId] || 0; // If no votes, return 0
    },



    calculatePartyPercentage(partyId) {
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

    updatePartyInfo() {
      console.log(`Selected party changed to: ${this.parties[this.selectedParty]}`);
    },

    getPoliticalSpectrum(partyId) {
      const spectrumMap = {
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
      return spectrumMap[partyId] || "Unknown"; // Default to "Unknown" if no match
    },

    createVisualization() {
      console.log("Creating visualization...");

      // Set the SVG dimensions
      const svgWidth = 1400;
      const svgHeight = 600;

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
          .attr("viewBox", `0 0 ${svgWidth} ${svgHeight}`) // Maintain coordinate system
          .attr("preserveAspectRatio", "xMidYMid meet") // Preserve aspect ratio
          .append("g")
          .attr("transform", `translate(${svgWidth / 2}, ${svgHeight / 2})`);

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
            .style("margin-bottom", "0");

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

    getPartyColor(partyId) {
      const colorMap = {
        1: '#1f77b4', // VVD
        2: '#2ca02c', // D66
        3: '#9467bd', // PvdA
        4: '#8c564b', // GroenLinks
        5: '#e377c2', // CDA
        6: '#535353', // PVV
        7: '#9b17cf', // SP
        8: '#ff7f0e', // FVD
        9: '#75b1dc', // CU
        10: '#006100', // PvdD
        11: '#9467bd', // SGP
        12: '#A9A9A9', // Volt
        13: '#efb7df', // JA21
        14: '#c5bb46', // BIJ1
        15: '#17becf', // BBB
        16: '#a34b00', // DENK
      };
      return colorMap[partyId] || '#ccc'; // Default color if partyId not found
    },
  },
};
</script>

<style scoped>
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
  padding: 20px;
  text-align: center;
}

.text h1 {
  font-size: 36px;
  margin-bottom: 60px;
}

.text p {
  font-size: 18px;
  line-height: 1.5;
  margin-bottom: 20px;
  text-align: center;
  width: 50%;
  margin-left: 25%;
}

.chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
  flex-grow: 1; /* Allows it to take the available space */
}

.legend-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-top: 20px; /* Adjust for spacing */
}

.legend-container div {
  display: flex;
  align-items: center;
  margin-bottom: 0; /* Ensure no extra space between items */
}

.legend-container div span {
  margin-left: 5px; /* Small left margin for text */
  font-size: 14px;
}

#visualization {
  margin-top: 20px; /* Adjust margin as needed */
}

.party-details h3 {
  margin: 0;
  font-size: 16px;
  color: #333;
}

.party-details p {
  margin-top: 20px;
  font-size: 14px;
  color: #666;
}

.button-container button {
  background-color: #24786e; /* Common background color */
  color: white;
  font-size: 18px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 20px; /* Margin to separate from party details */
  margin-left: 20px;
}

.button-container button:hover {
  background-color: #1e5f55; /* Darker shade on hover */
}



.tooltip {
  position: absolute;
  background: #f9f9f9;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  pointer-events: none;

}
</style>
