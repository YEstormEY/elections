<script lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

export default {
  setup() {
    const route = useRoute();
    const partyDetail = ref<any>(null); // Using 'any' for flexibility, but you should define a proper type for 'partyDetail'

    const fetchPartyDetail = async (partyId: number) => {
      try {
        const response = await fetch(`http://localhost:8080/api/party-details/${partyId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error('Failed to fetch party details');
        }
        partyDetail.value = await response.json();
      } catch (error) {
        console.error('Failed to fetch party details:', error);
      }
    };

    onMounted(() => {
      const partyId = Number(route.params.partyId);
      if (partyId) {
        fetchPartyDetail(partyId);
      }
    });

    return {
      partyDetail,
    };
  },
};
</script>

<template>

  <div class="background">
    <div class="text">
      <h1>See here the details of the party you are interested in!</h1>
      <p>
        Here are the details of the party you have chosen, learn more about their core beliefs, goals, and vision, and how you can get involved.
      </p>
    </div>
  </div>

  <div v-if="partyDetail" :class="['party-page', partyDetail.party.party_name.toLowerCase()]">
    <!-- Party Name -->
    <h1>{{ partyDetail.party.party_name }}</h1>

    <!-- Party Slogan -->
    <h2>{{ partyDetail.party_slogan }}</h2>

    <!-- Party Introduction -->
    <section class="introduction">
      <h3>📖 Introduction</h3>
      <p>{{ partyDetail.party_introduction }}</p>
    </section>

    <!-- Core Beliefs -->
    <section class="core-beliefs">
      <h3>🔑 Core Beliefs</h3>
      <p>{{ partyDetail.core_beliefs }}</p>
    </section>

    <!-- Goals and Vision -->
    <section class="goals-and-vision">
      <h3>🎯 Goals and Vision</h3>
      <p>{{ partyDetail.goals_and_vision }}</p>
    </section>

    <!-- Youth Policies -->
    <section class="youth-policies">
      <h3>👦 Youth Policies</h3>
      <p>{{ partyDetail.youth_policies }}</p>
    </section>

    <!-- Climate Policies -->
    <section class="climate-policies">
      <h3>🌍 Climate Policies</h3>
      <p>{{ partyDetail.climate_policies }}</p>
    </section>

    <!-- Social Issues -->
    <section class="social-issues">
      <h3>⚖️ Social Issues</h3>
      <p>{{ partyDetail.social_issues }}</p>
    </section>

    <!-- Housing Policies -->
    <section class="housing-policies">
      <h3>🏠 Housing Policies</h3>
      <p>{{ partyDetail.housing_policies }}</p>
    </section>

    <!-- Freedom and Rights -->
    <section class="freedom-rights">
      <h3>🗽 Freedom and Rights</h3>
      <p>{{ partyDetail.freedom_rights }}</p>
    </section>

    <!-- Fun Facts -->
    <section class="fun-facts">
      <h3>🎉 Fun Facts</h3>
      <p>{{ partyDetail.fun_facts }}</p>
    </section>

    <!-- Engagement Opportunities -->
    <section class="engagement-opportunities">
      <h3>🙌 Engagement Opportunities</h3>
      <p>{{ partyDetail.engagement_opportunities }}</p>
    </section>

    <!-- Leader Bio -->
    <section class="leader-bio">
      <h3>👤 Leader Bio</h3>
      <p>{{ partyDetail.leader_bio }}</p>
    </section>

    <!-- Volunteering Information -->
    <section class="volunteering-info">
      <h3>🤝 Volunteering Information</h3>
      <p>{{ partyDetail.volunteering_info }}</p>
    </section>

    <!-- Social Media -->
    <section class="follow-us">
      <h3>📱 Follow Us</h3>
      <p>
        <a :href="'https://instagram.com/' + partyDetail.social_media_instagram" target="_blank">Instagram: {{ partyDetail.social_media_instagram }}</a><br>
        <a :href="'https://twitter.com/' + partyDetail.social_media_twitter" target="_blank">Twitter: {{ partyDetail.social_media_twitter }}</a><br>
        <a v-if="partyDetail.social_media_tiktok" :href="'https://tiktok.com/@' + partyDetail.social_media_tiktok" target="_blank">TikTok: {{ partyDetail.social_media_tiktok }}</a>
      </p>
    </section>
  </div>

  <!-- Display an error message if no data is found -->
  <div v-else>
    <p>Party details not found.</p>
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

/* General Styling */
body {
  font-family: 'Poppins', sans-serif;
  background-color: #f4f4f4;
  color: #333;
  margin: 0;
  padding: 0;
}

h1, h2, h3 {
  margin: 0;
}

h1 {
  font-size: 3rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

h3 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

p {
  font-size: 1.1rem;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

a {
  text-decoration: none;
  color: inherit;
}

a:hover {
  text-decoration: underline;
}

/* Dynamic Party Page */
.party-page {
  padding: 2rem;
}

.header {
  background: var(--primary-color);
  color: white;
  text-align: center;
  padding: 40px 20px;
  border-radius: 10px;
}

.content {
  padding: 2rem;
}

section {
  margin-bottom: 2rem;
  padding: 1.5rem;
  background: white;
  border-left: 5px solid var(--primary-color);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;
}

section:hover {
  transform: translateY(-5px);
}

/* Responsive Design */
@media (max-width: 768px) {
  h1 {
    font-size: 2.4rem;
  }

  h2 {
    font-size: 1.8rem;
  }

  h3 {
    font-size: 1.3rem;
  }
}

@media (max-width: 480px) {
  h1 {
    font-size: 2rem;
  }

  h2 {
    font-size: 1.6rem;
  }

  h3 {
    font-size: 1.2rem;
  }
}

/* Party-specific styles */
/* VVD */
.vvd {
  --primary-color: #003399;
}

/* D66 */
.d66 {
  --primary-color: #00b39b;
}

/* PvdA */
.pvda {
  --primary-color: #ee1c25;
}

/* GroenLinks */
.groenlinks {
  --primary-color: #36b34a;
}

/* CDA */
.cda {
  --primary-color: #006400;
}

/* PVV */
.pvv {
  --primary-color: #1a2a6c;
}

/* SP */
.sp {
  --primary-color: #e60000;
}

/* FVD */
.fvd {
  --primary-color: #8b0000;
}

/* CU */
.cu {
  --primary-color: #0082c8;
}

/* PvdD */
.pvdd {
  --primary-color: #008f00;
}

/* SGP */
.sgp {
  --primary-color: #ff6600;
}

/* Volt */
.volt {
  --primary-color: #5624d0;
}

/* JA21 */
.ja21 {
  --primary-color: #003399;
}

/* BIJ1 */
.bij1 {
  --primary-color: #ffed00;
}

/* BBB */
.bbb {
  --primary-color: #4682b4;
}

/* DENK */
.denk {
  --primary-color: #00a5a8;
}
</style>


