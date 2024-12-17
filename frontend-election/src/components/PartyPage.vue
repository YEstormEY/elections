<script lang="ts">
import {defineComponent} from 'vue'
import {getAllParties} from "@/service/PartyService";


export default defineComponent({
  name: "PartyPage",
  data() {
    return {
      parties: [] as Array < any > ,
      partyLogo: [],
      errorMessage: '',

    };
  },
  methods: {
  async getAllParties() {
  try {
  const response = await getAllParties();
  if (response) {
    console.log('Fetched Parties Data:', response); // Controleer de volledige response
    this.parties = response;
  } else {
  this.parties = response;
  }
  } catch (error: any) {
    console.log('Failed to fetch parties:', error.message);
  }
  },
  getImageUrl(partyLogo: string) {
    if (partyLogo.startsWith('/')) {
      return `http://localhost:8080${partyLogo}`;
    }
    return `http://localhost:8080/img/${partyLogo}`;

  },

    goToPartyDetail(partyId: number) {
      console.log('Clicked Party ID:', partyId); // Debugging Party ID
      if (!partyId) {
        console.error('Party ID is invalid:', partyId);
        return;
      }
      this.$router.push({ name: 'partij', params: { partyId } });
    },

  },
  mounted() {
    // Fetch all parties when the component is mounted
    this.getAllParties();
  },
});

</script>

<template>
  <div class="background">
    <div class="text">
      <h1>Search for the party you are interested in!</h1>
      <p>Here are all the parties that are participating in the 2021 elections.</p>
    </div>
  </div>

  <div class="party-list">
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    <div
        v-for="party in parties"
        :key="party.party_id"
        :class="`card ${party.party_name}-card`"
        @click="goToPartyDetail(party.partyId)"

    >
      <img
          :src="getImageUrl(party.party_logo)"
          :alt="`${party.party_name} logo`"
          class="card-img-top"
      />
      <div class="card-body">
        <h5>{{ party.party_name }}</h5>
        <ul>
          <li>Leader: {{ party.party_leader }}</li>
          <li>Focus: {{ party.party_focus }}</li>
          <li>Ideology: {{ party.party_ideology }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Background and text styling */
.background {
  background-color: #24786E99;
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

/* Party list styling */
.party-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  padding: 2rem;
  max-width: 1400px;
  margin: 0 auto;
}

/* Card styling */
.card {
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  flex: 1 1 calc(20% - 20px);
  padding: 1rem;
  text-align: center;
  overflow: hidden;
  max-width: 260px;
  min-width: 220px;
  border-top: 5px solid #333;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.card img {
  width: 100%;
  height: 30%;
  border-bottom: 3px solid #ccc;
  padding-bottom: 15px;
}

.card h5 {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 1rem;
}

.card ul {
  list-style-type: none;
  padding: 0;
}

.card li {
  padding: 0.5rem 0;
  font-size: 1rem;
}

/* Add party-specific colors */
.VVD-card {
  border-top-color: #003399;
}

.D66-card {
  border-top-color: #28a745;
}

/* Responsive design */
@media (max-width: 1200px) {
  .card {
    flex: 1 1 calc(33.33% - 20px);
  }
}

@media (max-width: 768px) {
  .card {
    flex: 1 1 calc(50% - 20px);
    height: auto;
  }
}

@media (max-width: 480px) {
  .card {
    flex: 1 1 100%;
    margin-left: auto;
    margin-right: auto;
  }
}
</style>