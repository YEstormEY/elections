<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
  name: "ChatRooms",
  data() {
    return {
      themes: [
        {name: "Economie", description: "Bespreek beleid, belasting en economische groei."},
        {name: "Onderwijs", description: "Debatteer over onderwijsvernieuwingen en beleid."},
        {name: "Gezondheidszorg", description: "Praat over de toekomst van de zorgsector."},
        {name: "Klimaat", description: "Discussieer over klimaatverandering en milieubeleid."},
        {name: "Immigratie", description: "Bespreek migratie, integratie en asielbeleid."},
        {name: "Defensie", description: "Debatteer over veiligheid en defensie-uitgaven."},
      ],
    };
  },
  methods: {
    goToChatroom(theme: string) {
      this.$router.push({name: "ChatRoom", params: {theme}}); // Navigate to ChatRoom page with the selected theme
    },
  },
});
</script>

<template>
  <div class="background">
    <div class="chat-rooms">
      <h1>Select a Chat Room</h1>
      <div class="themes-container">
        <div
            class="theme-card"
            v-for="theme in themes"
            :key="theme.name"
            @click="goToChatroom(theme.name)"
        >
          <h2>{{ theme.name }}</h2>
          <p>{{ theme.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.background {
  background-color: #24786E99;
  width: 100%;
  padding: 20px;
  min-height: 60vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.chat-rooms {
  max-width: 1200px; /* Increase width to fit more cards */
  margin: 0 auto;
  font-family: Arial, sans-serif;
  text-align: center;
}

.themes-container {
  display: flex;
  flex-wrap: wrap; /* Ensure cards wrap to the next row if they don't fit */
  justify-content: center;
  gap: 20px; /* Space between cards */
}

.theme-card {
  background: white;
  width: 300px; /* Fixed card width */
  height: 300px; /* Fixed card height */
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.theme-card:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.theme-card h2 {
  font-size: 1.8em; /* Slightly larger title */
  margin: 0 0 10px;
}

.theme-card p {
  font-size: 1.1em; /* Slightly larger description */
  color: #555;
  text-align: center;
}

/* Responsive adjustments */
@media (max-width: 1024px) {
  .themes-container {
    justify-content: center; /* Center cards for smaller screens */
  }

  .theme-card {
    width: 250px; /* Reduce card size on smaller screens */
    height: 250px;
  }
}

@media (max-width: 768px) {
  .themes-container {
    gap: 15px; /* Reduce spacing between cards */
  }

  .theme-card {
    width: 200px; /* Further reduce card size for small screens */
    height: 200px;
  }

  .theme-card h2 {
    font-size: 1.5em; /* Adjust title size */
  }

  .theme-card p {
    font-size: 1em; /* Adjust description size */
  }
}
</style>
