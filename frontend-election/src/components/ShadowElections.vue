<template>
  <!-- First green background section, now responsive -->
  <div class="background">
    <div class="text">
      <h1>Cast your vote in our teen shadow election!</h1>
      <p>
        Why should you vote in a shadow election? Because it gives you a voice in shaping the future, even before you can officially vote. It helps you understand how elections work, engages you in important issues, and shows that your opinion matters. Plus, it prepares you to be an informed voter when you reach voting age.
      </p>
    </div>
  </div>

  <h2 class="shadowElectionText">Select your choice and press vote!</h2>

  <div class="background-second">
    <div class="party-images">
      <img
          v-for="party in parties"
          :key="party.party_id"
          :src="getImageUrl(party.party_logo)"
          :alt="party.party_name"
          class="party-logo"
          :class="{ selected: party.party_id === selectedPartyId }"
          @click="selectParty(party)"
      />
    </div>
    <div class="button-group">
      <button class="vote-button" @click="vote">Vote</button>
      <button class="results-button" @click="showResults">Results</button> <!-- Added Results Button -->
    </div>
  </div>

  <!-- Display selected party details -->
  <div v-if="selectedParty" class="party-details">
    <h2>{{ selectedParty.party_name }}</h2>
    <p><strong>Leader:</strong> {{ selectedParty.party_leader }}</p>
    <p><strong>Description:</strong> {{ selectedParty.party_description }}</p>
    <p><strong>Key Issues:</strong> {{ selectedParty.party_focus }}</p>
    <p><strong>Ideology:</strong> {{ selectedParty.party_ideology }}</p>
    <button class="more-info-button" @click="goToMoreInfo(selectedParty.partyId)">More Info</button>
  </div>

</template>


<script>
export default {
  data() {
    return {
      parties: [], // To hold the parties data fetched from backend
      selectedPartyId: null,
      selectedParty: null, // Store the selected party's details
      userId: null,
    };
  },
  mounted() {
    this.userId = localStorage.getItem('userId'); // Retrieve userId from local storage
    this.fetchParties(); // Fetch parties data from backend
  },
  methods: {
    fetchParties() {
      fetch('http://localhost:8080/api/parties')
          .then((response) => response.json())
          .then((data) => {
            this.parties = data; // Store fetched parties data
          })
          .catch((error) => {
            console.error('Error fetching parties:', error);
          });
    },

    goToMoreInfo(partyId) {
      // Show an alert instead of redirecting
      alert(`More information for party ID ${partyId} is coming soon!`);
    },
    getImageUrl(partyLogo) {
      if (partyLogo.startsWith('/')) {
        return `http://localhost:8080${partyLogo}`;
      }
      return `http://localhost:8080/${partyLogo}`;
    },
    selectParty(party) {
      this.selectedPartyId = party.party_id;
      this.selectedParty = party; // Set selected party details
    },
    vote() {
      if (this.selectedPartyId) {
        this.submitVote(this.selectedPartyId);
      } else {
        alert('Please select a party before voting.');
      }
    },
    submitVote(partyId) {
      const userId = this.userId;

      fetch('http://localhost:8080/auth/shadowElections', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId, partyId }),
      })
          .then((response) =>
              response.json().then((data) => {
                if (!response.ok) {
                  throw new Error(data.message); // Use the message from the response
                }
                return data;
              })
          )
          .then((data) => {
            alert(data.message); // Show success message
          })
          .catch((error) => {
            console.error('Error:', error);
            alert(error.message); // Show the specific error message
          });
    },
    showResults() {
      alert("Results feature is not yet implemented.");
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

.shadowElectionText {
  font-size: 24px;
  margin-top: 60px;
  width: 50%;
  justify-content: center;
  align-items: center;
  display: flex;
}

.background-second {
  background-color: #d9d9d9;
  padding: 20px;
  margin-top: 40px;
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  border-radius: 10px;
  text-align: center;
  height: auto;
}

.party-images {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px; /* Space between images */
}

.party-logo {
  width: 100px; /* Set a fixed width */
  height: 100px; /* Set a fixed height */
  object-fit: contain; /* Maintain aspect ratio without cropping */
  border-radius: 5px;
  cursor: pointer;
  transition: transform 0.3s;
  border: 2px solid transparent; /* Default border to indicate selection */
}

.party-logo:hover {
  transform: scale(1.05); /* Scale the image slightly on hover */
}

.party-logo.selected {
  border: 5px solid #24786e; /* Highlight selected image */
}

.button-group {
  display: flex; /* Flexbox to align buttons horizontally */
  justify-content: center; /* Center the buttons */
  gap: 20px; /* Space between buttons */
  margin-top: 20px; /* Margin to separate from party images */
}

.vote-button, .results-button {
  background-color: #24786e; /* Common background color */
  color: white;
  font-size: 18px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.vote-button:hover, .results-button:hover {
  background-color: #1e5f55; /* Darker shade on hover */
}

.results-button {
  background-color: #6c757d; /* Different background color for results button */
}

.results-button:hover {
  background-color: #5a6268; /* Darker shade on hover for results button */
}

@media (max-width: 768px) {
  .background {
    height: auto;
    padding: 10px;
  }

  .text h1 {
    font-size: 28px;
    margin-bottom: 40px;
  }

  .text p {
    font-size: 16px;
    width: 80%;
    margin-left: 10%;
  }

  .shadowElectionText {
    font-size: 20px;
    margin-top: 40px;
  }

  .party-logo {
    width: 80px; /* Adjust for smaller screens */
    height: 80px; /* Adjust for smaller screens */
  }
}

@media (max-width: 480px) {
  .text h1 {
    font-size: 22px;
    margin-bottom: 30px;
  }

  .text p {
    font-size: 14px;
    width: 90%;
    margin-left: 5%;
  }

  .shadowElectionText {
    font-size: 18px;
    margin-top: 30px;
  }
}

.party-details {
  margin-top: 20px;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

.party-details h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.party-details p {
  font-size: 18px;
  margin: 5px 0;
}

.more-info-button {
  background-color: #24786e; /* Common background color */
  color: white;
  font-size: 18px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 10px; /* Margin to separate from party details */
}

.more-info-button:hover {
  background-color: #1e5f55; /* Darker shade on hover */
}


</style>
