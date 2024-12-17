<template>
  <div class="background">
    <div class="text">
      <h1>Shadow election results and information</h1>
      <p>On this page, you will find the results of the teen votes and how many have voted for each political party.</p>
    </div>
  </div>

  <div class="button-container">
    <button>
    <router-link to="/ResultsElections" :style="{ color: '#ffffff' }">Stop comparing the elections</router-link>
    </button>
  </div>

  <!-- Container for side-by-side comparison -->
  <div class="comparison-container">
    <!-- Real Election: Graph and Info -->
    <div class="chart-container">
      <h3 class="chart-title">Real Election Results</h3>
      <div class="chart-wrapper">
        <div class="svg-wrapper">
          <div id="visualization" v-if="!loading && seatDistribution.length > 0"></div>
        </div>
        <div v-if="loading">Loading...</div>
        <div v-if="!loading && noData">No data available.</div>
      </div>

      <!-- Real Election Party Details -->
      <div class="party-details-display">
        <h3>Selected Party: {{ selectedParty ? parties[selectedParty] : 'None' }}</h3>
        <p v-if="selectedParty">
          <strong>Percentage of Votes:</strong> {{ calculateVotePercentage(selectedParty, voteCounts) }}%
        </p>
        <p v-if="selectedParty">
          <strong>Total Amount of Votes:</strong> {{ calculatePartyVotes(selectedParty, voteCounts) }}
        </p>
        <p v-if="selectedParty">
          <strong>Total Amount of Seats:</strong> {{ calculatePartySeats(selectedParty, seatDistribution) }}
        </p>
      </div>

    <!-- Shadow Election: Graph and Info -->
    <div class="chart-container">
      <h3 class="chart-title">Shadow Election Results</h3>
      <div class="chart-wrapper">
        <div class="svg-wrapper">
          <div id="second-visualization" v-if="!loading && secondSeatDistribution.length > 0"></div>
        </div>
        <div v-if="loading">Loading...</div>
        <div v-if="!loading && noData">No data available.</div>
      </div>

      <!-- Shadow Election Party Details -->
      <div class="party-details-display">
        <h3>Selected Party: {{ selectedParty ? parties[selectedParty] : 'None' }}</h3>
        <p v-if="selectedParty">
          <strong>Percentage of Votes:</strong> {{ calculateVotePercentage(selectedParty, voteCountsSecond) }}%
        </p>
        <p v-if="selectedParty">
          <strong>Total Amount of Votes:</strong> {{ calculatePartyVotes(selectedParty, voteCountsSecond) }}
        </p>
        <p v-if="selectedParty">
          <strong>Total Amount of Seats:</strong> {{ calculatePartySeats(selectedParty, secondSeatDistribution) }}
        </p>
      </div>
    </div>
  </div>
  </div>

  <!-- Party selection -->
  <div class="party-selection">
    <label for="party-select">Select a Political Party:</label>
    <select id="party-select" v-model="selectedParty">
      <option v-for="(name, id) in parties" :value="id" :key="id">{{ name }}</option>
    </select>
  </div>
</template>

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

.button-container button {
  background-color: #28a745;
  color: white;
  font-size: 18px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 20px;
  margin-left: 20px;
}

.button-container button:hover {
  background-color: #218838;
}

.comparison-container {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap; /* Allows items to wrap on smaller screens */
}

.chart-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;  /* Ensures the content is aligned from top */
  flex: 1; /* Distribute space evenly */
  min-width: 300px; /* Prevents the chart from becoming too narrow */
  padding: 10px;
}

.chart-title {
  width: 700px;  /* Ensure the title aligns with the chart */
  margin: 0 auto 10px;  /* Center the title and reduce the margin below it */
  font-size: 24px;
  text-align: center;
}

.chart-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.svg-wrapper {
  width: 700px;  /* Ensure the wrapper is only 700px to match the chart's width */
  height: auto;
  display: block;
}

#visualization, #second-visualization {
  width: 100%;  /* Ensures the SVG scales with the wrapper */
  height: 100%; /* Maintains the aspect ratio */
  display: block;
  max-width: 700px;  /* Optional: Cap the width of the SVG */
  max-height: 300px; /* Optional: Cap the height of the SVG */
}

.party-details-display {
  width: 700px;  /* Align this with the chart width */
  margin-top: 10px;  /* Tighten the space between the chart and the text below */
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 5px;
  text-align: center;
  margin-left: auto;
  margin-right: auto; /* Ensures the text block aligns in the center */
}

.party-selection {
  text-align: center;
  margin-top: 30px;
}

.party-selection label {
  font-size: 18px;
  margin-right: 10px;
}

.party-selection select {
  font-size: 16px;
  padding: 5px;
  border-radius: 4px;
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

<script lang="ts">
import * as d3 from "d3";

export default {
  data() {
    return {
      seatDistribution: [] as { party: string; seats: number; fractionalSeats: number; color: string }[],
      secondSeatDistribution: [] as { party: string; seats: number; fractionalSeats: number; color: string }[],
      loading: true,
      noData: false,
      parties: {} as Record<string, string>,
      selectedParty: null as string | null,
      voteCounts: {} as Record<string, number>,
      voteCountsSecond: {} as Record<string, number>,
    };
  },
  mounted() {
    this.fetchElectionData();
  },
  methods: {
    async fetchElectionData() {
      try {
        await this.fetchPartyData();

        const response = await fetch("http://localhost:8080/api/election-data");
        if (!response.ok) throw new Error("Network response was not ok");

        const electionData = await response.json();
        console.log("Real Election Data:", electionData); // Log fetched data
        this.voteCounts = electionData;

        const totalVotes = Object.values(electionData as Record<string, number>).reduce((acc: number, voteCount) => acc + Number(voteCount), 0);
        console.log("Total Votes (Real):", totalVotes); // Log total votes

        const totalSeats = 150;

        const nameToIdMap = Object.keys(this.parties).reduce((map, id) => {
          map[this.parties[id].toLowerCase()] = id;
          return map;
        }, {} as Record<string, string>);

        let seatDistribution = Object.keys(electionData).map((partyName) => {
          const partyNameLower = partyName.toLowerCase();
          const votes = electionData[partyName];
          const proportion = votes / totalVotes;
          const seats = proportion * totalSeats;
          const partyId = nameToIdMap[partyNameLower];

          if (!partyId) {
            console.warn(`Party name ${partyName} is missing in parties data!`);
            return {
              party: partyName,
              seats: Math.floor(seats),
              fractionalSeats: seats - Math.floor(seats),
              color: this.getPartyColor(partyName),
            };
          }

          return {
            party: this.parties[partyId],
            seats: Math.floor(seats),
            fractionalSeats: seats - Math.floor(seats),
            color: this.getPartyColor(partyId),
          };
        });

        const totalAssignedSeats = seatDistribution.reduce((acc, party) => acc + party.seats, 0);
        const remainingSeats = totalSeats - totalAssignedSeats;

        if (remainingSeats > 0) {
          seatDistribution.sort((a, b) => b.fractionalSeats - a.fractionalSeats);
          for (let i = 0; i < remainingSeats; i++) {
            seatDistribution[i].seats += 1;
          }
        }

        this.seatDistribution = seatDistribution;

        const responseSecond = await fetch("http://localhost:8080/auth/votes/counts");
        if (!responseSecond.ok) throw new Error("Network response was not ok");

        const electionDataSecond = await responseSecond.json();
        console.log("Shadow Election Data:", electionDataSecond); // Log fetched data
        this.voteCountsSecond = electionDataSecond;

        const totalVotesSecond = Object.values(electionDataSecond).reduce(
            (acc: number, voteCount) => acc + Number(voteCount),
            0
        );
        console.log("Total Votes (Shadow):", totalVotesSecond); // Log total votes

        let secondSeatDistribution = Object.keys(electionDataSecond).map((partyId) => {
          const votes = electionDataSecond[partyId];
          const proportion = votes / totalVotesSecond;
          const seats = proportion * totalSeats;

          return {
            party: this.parties[partyId],
            seats: Math.floor(seats),
            fractionalSeats: seats - Math.floor(seats),
            color: this.getPartyColor(partyId),
          };
        });

        const totalAssignedSeatsSecond = secondSeatDistribution.reduce((acc, party) => acc + party.seats, 0);
        const remainingSeatsSecond = totalSeats - totalAssignedSeatsSecond;

        if (remainingSeatsSecond > 0) {
          secondSeatDistribution.sort((a, b) => b.fractionalSeats - a.fractionalSeats);
          for (let i = 0; i < remainingSeatsSecond; i++) {
            secondSeatDistribution[i].seats += 1;
          }
        }

        this.secondSeatDistribution = secondSeatDistribution;

        this.loading = false;
        this.$nextTick(() => {
          this.createVisualization();
          this.createSecondVisualization();
        });
      } catch (error) {
        console.error("Error fetching election data:", error);
        this.loading = false;
      }
    },

    async fetchPartyData() {
      try {
        const response = await fetch("http://localhost:8080/api/parties");
        if (!response.ok) throw new Error("Network response was not ok");

        const partyData = await response.json();
        if (!Array.isArray(partyData)) throw new Error("Invalid party data format");

        this.parties = {};
        partyData.forEach((party) => {
          if (party.partyId && party.party_name) {
            this.parties[party.partyId] = party.party_name;
          } else {
            console.error("Invalid party object:", party);
          }
        });
      } catch (error) {
        console.error("Error fetching parties:", error);
        this.loading = false;
      }
    },

    calculatePartyPercentage(partyId: string, distribution: { party: string; seats: number }[]): number {
      if (!distribution || distribution.length === 0) return 0;

      const totalSeats = distribution.reduce((acc, party) => acc + party.seats, 0);
      const partyVotes = distribution.find((party) => party.party === this.parties[partyId]);

      if (!partyVotes) return 0;

      return parseFloat(((partyVotes.seats / totalSeats) * 100).toFixed(2));
    },

    calculatePartyVotes(partyId: string, voteCounts: Record<string, number>): number {
      const partyName = this.parties[partyId];
      console.log(`Calculating votes for party: ${partyName} (ID: ${partyId})`);
      console.log(`Vote counts object:`, voteCounts);

      const votes = voteCounts[partyId] || voteCounts[partyName] || 0;

      console.log(`Votes for ${partyName} (${partyId}):`, votes);
      return votes;
    },

    calculateVotePercentage(partyId: string | null, voteCounts: Record<string, number>): number {
      if (!partyId || !voteCounts || Object.keys(voteCounts).length === 0) return 0;

      const partyName = this.parties[partyId];
      const partyVotes = voteCounts[partyId] || voteCounts[partyName] || 0;

      const totalVotes = Object.values(voteCounts).reduce((acc: number, votes) => acc + Number(votes), 0);
      if (totalVotes === 0) return 0;

      return parseFloat(((partyVotes / totalVotes) * 100).toFixed(2));
    },


    calculatePartySeats(partyId: string, seatDistribution: { party: string; seats: number }[]): number {
      if (!seatDistribution || seatDistribution.length === 0) return 0;

      const partyData = seatDistribution.find((party) => party.party === this.parties[partyId]);
      if (!partyData) return 0;

      return partyData.seats;
    },

    getPartyColor(partyId: string): string {
      const colorMap: Record<string, string> = {
        "1": "#1f77b4",
        "2": "#2ca02c",
        "3": "#9467bd",
        "4": "#8c564b",
        "5": "#e377c2",
        "6": "#7f7f7f",
        "7": "#bcbd22",
        "8": "#17becf",
        "9": "#ff7f0e",
        "10": "#f58556",
        "11": "#ffbb78",
        "12": "#2b83ba",
        "13": "#55bfc6",
        "14": "#6d3c23",
        "15": "#d62728",
        "16": "#7b8c55",
        "17": "#c4e1d2",
        "18": "#f1c2c2",
        "19": "#e7ba35",
        "20": "#c7c7c7",
        "21": "#b1c2d2",
        "22": "#f0855e",
        "23": "#ff736d",
        "24": "#98c6e5",
        "25": "#a0b1c5",
        "26": "#c92b4f",
        "27": "#00a0b6",
        "28": "#9d5e4d",
        "29": "#2f4f4f",
        "30": "#ff4e50",
        "31": "#e2a99d",
        "32": "#7d4c48",
        "33": "#b84f56",
        "34": "#8eac46",
        "35": "#4267b2",
        "36": "#a14a72",
        "37": "#d2a8f0",
      };
      return colorMap[partyId] || "#ccc";
    },

    createVisualization() {
      const svgWidth = 700;
      const svgHeight = 300;
      const seatSize = 15;
      const seatData = this.seatDistribution.flatMap((party) => Array(party.seats).fill(party));

      const pattern = [2, 3, 4, 5, 5, 6];
      const segments = 6;
      let currentSeatIndex = 0;

      const tooltip = d3
          .select("#visualization")
          .append("div")
          .style("display", "none")
          .style("position", "absolute")
          .style("background", "#f9f9f9")
          .style("padding", "5px")
          .style("border", "1px solid #ccc")
          .style("border-radius", "5px")
          .style("pointer-events", "none");

      const svg = d3
          .select("#visualization")
          .append("svg")
          .attr("width", svgWidth)
          .attr("height", svgHeight)
          .attr("viewBox", `0 0 ${svgWidth} ${svgHeight}`)
          .attr("preserveAspectRatio", "xMidYMid meet")
          .append("g")
          .attr("transform", `translate(${svgWidth / 2}, ${svgHeight})`);

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
              svg
                  .append("circle")
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
                    tooltip.style("top", `${event.pageY + 10}px`).style("left", `${event.pageX + 10}px`);
                  })
                  .on("mouseout", () => {
                    tooltip.style("display", "none");
                  });
            }
            currentSeatIndex++;
          }
        }
      }
    },

    createSecondVisualization() {
      const svgWidth = 700;
      const svgHeight = 300;
      const seatSize = 15;
      const seatData = this.secondSeatDistribution.flatMap((party) => Array(party.seats).fill(party));

      const pattern = [2, 3, 4, 5, 5, 6];
      const segments = 6;
      let currentSeatIndex = 0;

      const tooltip = d3
          .select("#second-visualization")
          .append("div")
          .style("display", "none")
          .style("position", "absolute")
          .style("background", "#f9f9f9")
          .style("padding", "5px")
          .style("border", "1px solid #ccc")
          .style("border-radius", "5px")
          .style("pointer-events", "none");

      const svg = d3
          .select("#second-visualization")
          .append("svg")
          .attr("width", svgWidth)
          .attr("height", svgHeight)
          .attr("viewBox", `0 0 ${svgWidth} ${svgHeight}`)
          .attr("preserveAspectRatio", "xMidYMid meet")
          .append("g")
          .attr("transform", `translate(${svgWidth / 2}, ${svgHeight})`);

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
              svg
                  .append("circle")
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
                    tooltip.style("top", `${event.pageY + 10}px`).style("left", `${event.pageX + 10}px`);
                  })
                  .on("mouseout", () => {
                    tooltip.style("display", "none");
                  });
            }
            currentSeatIndex++;
          }
        }
      }
    },
  },
};
</script>


