<script lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'; // Import the router to navigate

export default {
  setup() {
    const parties = ref([]);  // To store all fetched parties
    const errorMessage = ref('');
    const router = useRouter();  // Get the router instance

    // Fetch all parties
    const getAllParties = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/parties', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });

        if (response.ok) {
          parties.value = await response.json();
          console.log('Fetched all parties:', parties.value);
        } else {
          console.error('Failed to fetch parties');
          errorMessage.value = 'Failed to fetch parties';
        }
      } catch (error) {
        console.error('Error fetching parties:', error);
        errorMessage.value = 'Error fetching parties';
      }
    };

    // Function to generate correct image URL
    const getImageUrl = (partyLogo) => {
      if (partyLogo.startsWith('/')) {
        return `http://localhost:8080${partyLogo}`;
      }
      return `http://localhost:8080/img/${partyLogo}`;
    };

    const goToPartyPage = (partyId) => {
      router.push(`/partij/${partyId}`);
    };




    onMounted(() => {
      getAllParties();  // Fetch all parties when the component mounts
    });

    return {
      parties,
      errorMessage,
      getImageUrl,
      goToPartyPage,
    };
  },
};
</script>




<template>

  <div class="background">
    <div class="text">
      <h1>Search for the party you are interested in !</h1>
      <p>
        Here are all the parties that are participating in the 2021 elections.
      </p>
    </div>
  </div>

  <div class="party-list">
    <div
        v-for="party in parties"
        :key="party.party_id"
        :class="`card ${party.party_name}-card`"
        @click="goToPartyPage(party.party_id)"
    >
    <img :src="getImageUrl(party.party_logo)" :alt="`${party.party_name} logo`" class="card-img-top">
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


.background {
  background-color: #24786E99;
  padding: 20px;
  height: 60vh;
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


body {
  font-family: 'Roboto', sans-serif;
  background-color: #f4f4f9;
  color: #333;
  margin: 0;
  padding: 0;
}

/* Container voor alle partijen */
.party-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
  padding: 2rem;
  max-width: 1400px;
  margin: 0 auto;
}

/* Algemene stijl voor kaarten */
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

/* Hover effect voor kaarten */
.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

/* Afbeeldingstijl in de kaart */
.card img {
  width: 100%;
  height: 30%;
  border-bottom: 3px solid #ccc;
  padding-bottom: 15px;
}

/* Titel van de kaart */
.card h5 {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 1rem;
}

/* Lijststijl in de kaart */
.card ul {
  list-style-type: none;
  padding: 0;
}

.card li {
  padding: 0.5rem 0;
  font-size: 1rem;
}

/* Kleuren voor verschillende partijen */

/* VVD - Blauw */
.VVD-card {
  border-top-color: #003399;
}

/* D66 - Groen */
.D66-card {
  border-top-color: #28a745;
}

/* PVV - Donkerblauw */
.PVV-card {
  border-top-color: #002366;
}

/* CDA - Groen */
.CDA-card {
  border-top-color: #339933;
}

/* GroenLinks - Lichtgroen */
.GL-card {
  border-top-color: #00A859;
}

/* PvdA - Rood */
.PvdA-card {
  border-top-color: #FF0000;
}

/* FvD - Maroon */
.FvD-card {
  border-top-color: #800000;
}

/* SP - Donkerrood */
.SP-card {
  border-top-color: #e30613;
}

/* PvdD - Donkergroen */
.PvdD-card {
  border-top-color: #006400;
}

/* 50PLUS - Paars */
.FiftyPLUS-card {
  border-top-color: #9932CC;
}

/* DENK - Lichtblauw */
.DENK-card {
  border-top-color: #00ADEF;
}

/* BBB - Donkergroen */
.BBB-card {
  border-top-color: #006400;
}

/* SGP - Oranje */
.SGP-card {
  border-top-color: #FF4500;
}

/* Volt - Paars */
.Volt-card {
  border-top-color: #6600CC;
}

/* BVNL - Blauw */
.BVNL-card {
  border-top-color: #1E90FF;
}

/* BIJ1 - Zwart */
.BIJ1-card {
  border-top-color: #000000;
}

/* Add this CSS for the container to center the cards */
.party-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* This ensures the cards are centered */
  gap: 20px; /* Adds space between the cards */
}

/* Responsief ontwerp */
@media (max-width: 1200px) {
  .card {
    flex: 1 1 calc(33.33% - 20px); /* 3 kaarten per rij voor tablets */
  }
}

@media (max-width: 768px) {
  .card {
    flex: 1 1 calc(50% - 20px); /* 2 kaarten per rij voor kleine schermen */
  }
}

@media (max-width: 480px) {
  .card {
    flex: 1 1 100%; /* 1 kaart per rij voor mobiele schermen */
    margin-left: auto;
    margin-right: auto; /* Centers the card on mobile */
  }
}


</style>