<script lang="ts">
import {ref, onMounted, defineComponent, computed} from 'vue';
import { useRoute } from 'vue-router';

// Interfaces for each model
interface PartyBasicInfo {
  partyName: string;
  slogan: string;
  introduction: string;
  funFacts: string;
  socialMediaInstagram: string;
  socialMediaTwitter: string;
  socialMediaTiktok: string;
  website: string;
}

interface PartyPolicies {
  socialIssues: string;
  economicIssues: string;
  climatePolicies: string;
  socialPolicies: string;
  housingPolicies: string;
  economicPolicies: string;
  freedomRights: string;
  linkSocialIssues: string;
  linkEconomicIssues: string;
  linkClimatePolicies: string;
  linkSocialPolicies: string;
  linkHousingPolicies: string;
  linkEconomicPolicies: string;
  linkFreedomRights: string;
}

interface PartyEngagement {
  leaderBio: string;
  volunteeringInfo: string;
  engagementOpportunities: string;
}

export default defineComponent({
  setup() {
    const searchQuery = ref('');
    const route = useRoute();
    const basicInfo = ref<PartyBasicInfo | null>(null);
    const policies = ref<PartyPolicies | null>(null);
    const engagement = ref<PartyEngagement | null>(null);
    const loading = ref(true);
    const error = ref<string | null>(null);

    const filteredContent = computed(() => {
      if (!searchQuery.value.trim()) return null;

      const query = searchQuery.value.toLowerCase();
      return {
        basicInfo: basicInfo.value && Object.values(basicInfo.value).some(value => value.toLowerCase().includes(query)),
        policies: policies.value && Object.values(policies.value).some(value => value.toLowerCase().includes(query)),
        engagement: engagement.value && Object.values(engagement.value).some(value => value.toLowerCase().includes(query)),
      };
    });

    const matches = (key: string, value: string | undefined | null): boolean => {
      if (!value || !searchQuery.value.trim()) return true;
      return value.toLowerCase().includes(searchQuery.value.toLowerCase());
    };


    const highlight = (text: string | undefined | null) => {
      if (!text || !searchQuery.value.trim()) return text;
      const regex = new RegExp(`(${searchQuery.value})`, 'gi');
      return text.replace(regex, '<mark>$1</mark>');
    };

      const fetchBasicInfo = async (partyId: number) => {
      try {
        const response = await fetch(`http://localhost:8080/api/party-basic-info/${partyId}`);
        if (!response.ok) throw new Error('Failed to fetch basic info');
        basicInfo.value = await response.json();
      } catch (err) {
        error.value = 'Failed to load basic information.';
        console.error(err);
      }
    };

    const fetchPolicies = async (partyId: number) => {
      try {
        const response = await fetch(`http://localhost:8080/api/party-policies/${partyId}`);
        if (!response.ok) throw new Error('Failed to fetch policies');
        policies.value = await response.json();
      } catch (err) {
        error.value = 'Failed to load policies.';
        console.error(err);
      }
    };

    const fetchEngagement = async (partyId: number) => {
      try {
        const response = await fetch(`http://localhost:8080/api/party-engagement/${partyId}`);
        if (!response.ok) throw new Error('Failed to fetch engagement info');
        engagement.value = await response.json();
      } catch (err) {
        error.value = 'Failed to load engagement information.';
        console.error(err);
      }
    };

    onMounted(() => {
      const partyId = Number(route.params.partyId);
      if (partyId > 0) {
        fetchBasicInfo(partyId);
        fetchPolicies(partyId);
        fetchEngagement(partyId);
      } else {
        error.value = 'Invalid Party ID.';
      }
      loading.value = false;
    });

    return {
      searchQuery,
      matches,
      highlight,
      basicInfo,
      policies,
      engagement,
      loading,
      error,
    };
  },
});
</script>

<template>
  <div>
    <!-- Search Bar -->
    <div class="search-container">
      <input
          type="text"
          v-model="searchQuery"
          placeholder="Zoek binnen deze pagina..."
          class="search-input"
      />
    </div>

    <div class="party-page">
      <!-- Loading State -->
      <div v-if="loading" class="loading">
        <p>Loading...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="error">
        <p>{{ error }}</p>
      </div>

      <!-- Party Details -->
      <div v-else>
        <!-- Basic Info -->
        <section v-if="matches('basicInfo', basicInfo?.introduction)" class="section basic-info">
          <h1>{{ basicInfo?.partyName }}</h1>
          <h2>{{ basicInfo?.slogan }}</h2>
          <p v-html="highlight(basicInfo?.introduction)"></p>
          <a :href="basicInfo?.website" target="_blank" class="btn btn-primary">
            🌐 Visit Official Website
          </a>
        </section>

        <!-- Fun Facts -->
        <section v-if="matches('funFacts', basicInfo?.funFacts)" class="section fun-facts">
          <h3>🎉 Fun Facts</h3>
          <div class="fun-facts-container">
            <p v-for="fact in JSON.parse(basicInfo?.funFacts || '[]')" :key="fact" v-html="highlight(fact)"></p>
          </div>
        </section>

        <!-- Leader Bio -->
        <section v-if="matches('leaderBio', engagement?.leaderBio)" class="section leader-bio">
          <h3>👤 Leader Bio</h3>
          <p v-html="highlight(engagement?.leaderBio)"></p>
        </section>

        <!-- Policies -->
        <section v-if="matches('policies', policies ? JSON.stringify(policies) : null)" class="policies">

        <h2>Party Policies</h2>

          <div v-if="matches('socialIssues', policies?.socialIssues)">
            <h3>⚖️ Social Issues</h3>
            <p v-html="highlight(policies?.socialIssues)"></p>
            <a :href="policies?.linkSocialIssues" target="_blank" class="btn btn-link">Learn More</a>
          </div>

          <div v-if="matches('economicIssues', policies?.economicIssues)">
            <h3>💼 Economic Issues</h3>
            <p v-html="highlight(policies?.economicIssues)"></p>
            <a :href="policies?.linkEconomicIssues" target="_blank" class="btn btn-link">Learn More</a>
          </div>

          <div v-if="matches('climatePolicies', policies?.climatePolicies)">
            <h3>🌍 Climate Policies</h3>
            <p v-html="highlight(policies?.climatePolicies)"></p>
            <a :href="policies?.linkClimatePolicies" target="_blank" class="btn btn-link">Learn More</a>
          </div>

          <div v-if="matches('socialPolicies', policies?.socialPolicies)">
            <h3>🏛️ Social Policies</h3>
            <p v-html="highlight(policies?.socialPolicies)"></p>
            <a :href="policies?.linkSocialPolicies" target="_blank" class="btn btn-link">Learn More</a>
          </div>

          <div v-if="matches('housingPolicies', policies?.housingPolicies)">
            <h3>🏠 Housing Policies</h3>
            <p v-html="highlight(policies?.housingPolicies)"></p>
            <a :href="policies?.linkHousingPolicies" target="_blank" class="btn btn-link">Learn More</a>
          </div>

          <div v-if="matches('economicPolicies', policies?.economicPolicies)">
            <h3>📊 Economic Policies</h3>
            <p v-html="highlight(policies?.economicPolicies)"></p>
            <a :href="policies?.linkEconomicPolicies" target="_blank" class="btn btn-link">Learn More</a>
          </div>

          <div v-if="matches('freedomRights', policies?.freedomRights)">
            <h3>🗽 Freedom and Rights</h3>
            <p v-html="highlight(policies?.freedomRights)"></p>
            <a :href="policies?.linkFreedomRights" target="_blank" class="btn btn-link">Learn More</a>
          </div>
        </section>

        <!-- Engagement Opportunities -->
        <section v-if="matches('engagementOpportunities', engagement?.engagementOpportunities)" class="section engagement-opportunities">
          <h2>🙌 Engagement Opportunities</h2>
          <p v-html="highlight(engagement?.engagementOpportunities)"></p>
        </section>

        <!-- Volunteering Opportunities -->
        <section v-if="matches('volunteeringInfo', engagement?.volunteeringInfo)" class="section volunteering">
          <h2>🤝 Volunteering Opportunities</h2>
          <p v-html="highlight(engagement?.volunteeringInfo)"></p>
        </section>

        <!-- Social Media Links -->
        <section class="section social-media">
          <h3>📱 Follow Us</h3>
          <ul>
            <li v-if="matches('socialMediaInstagram', basicInfo?.socialMediaInstagram)">
              <a :href="'https://instagram.com/' + basicInfo?.socialMediaInstagram" target="_blank">
                Instagram: {{ basicInfo?.socialMediaInstagram }}
              </a>
            </li>
            <li v-if="matches('socialMediaTwitter', basicInfo?.socialMediaTwitter)">
              <a :href="'https://twitter.com/' + basicInfo?.socialMediaTwitter" target="_blank">
                Twitter: {{ basicInfo?.socialMediaTwitter }}
              </a>
            </li>
            <li v-if="basicInfo?.socialMediaTiktok && matches('socialMediaTiktok', basicInfo?.socialMediaTiktok)">
              <a :href="'https://tiktok.com/@' + basicInfo?.socialMediaTiktok" target="_blank">
                TikTok: {{ basicInfo?.socialMediaTiktok }}
              </a>
            </li>
          </ul>
        </section>
      </div>
    </div>
  </div>
</template>


<style scoped>
/* General Styling */
body {
  font-family: 'Poppins', sans-serif;
  background-color: #f4f4f4;
  color: #333;
  margin: 0;
  padding: 0;
}

.search-container {
  margin-bottom: 20px;
  text-align: center;
}

.search-input {
  padding: 10px;
  width: 80%;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

mark {
  background-color: yellow;
  font-weight: bold;
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

/* Party Page Container */
.party-page {
  padding: 2rem;
}

.fun-facts-container {
  background-color: #fdfbe2; /* Light background */
  padding: 20px; /* Padding around the content */
  border-radius: 8px; /* Rounded corners */
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow */
}

.fun-facts-container p {
  margin-bottom: 15px; /* Spacing between facts */
  font-size: 1rem; /* Font size for readability */
  line-height: 1.6; /* Line height for readability */
}

.header {
  background: var(--primary-color);
  color: white;
  text-align: center;
  padding: 40px 20px;
  border-radius: 10px;
  margin-bottom: 2rem;
}

.header h1 {
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.header p {
  font-size: 1.2rem;
  line-height: 1.6;
}

/* Section Styling */
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

/* Fun Facts Section */
.fun-facts {
  background-color: #f9fbe7; /* Light yellow background */
  border-left-color: var(--primary-color);
  padding: 2rem;
  border-radius: 8px;
  text-align: center;
}

.fun-facts p {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

/* Policies Section */
.policies {
  background-color: #eef7f1; /* Light green background */
  border-left-color: var(--primary-color);
  padding: 2rem;
  border-radius: 8px;
}

/* Social Media Section */
.social-media ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: center;
  gap: 2rem;
}

.social-media li a {
  text-decoration: none;
  font-size: 1.2rem;
  font-weight: bold;
  color: var(--primary-color);
}

.social-media li a:hover {
  color: #003366;
  text-decoration: underline;
}

/* Engagement Section */
.engagement {
  background-color: #e9f5ff; /* Light blue background */
  padding: 2rem;
  border-left-color: var(--primary-color);
  border-radius: 8px;
  text-align: justify;
}

.section p {
  margin-bottom: 15px;
}

.btn {
  padding: 9px 15px;
  border: none;
  border-radius: 4px;
  text-decoration: none;
  cursor: pointer;
  margin: 10px;

}

.btn-primary {
  background-color: #0056b3;
  color: white;
}

.btn-link {
  background-color: #0056b3;
  color: white;
  text-decoration: underline;
}

.btn-link:hover {
  color: #003f8a;
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

  .header {
    padding: 2rem;
  }

  section {
    padding: 1.5rem;
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

  section {
    padding: 1rem;
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
