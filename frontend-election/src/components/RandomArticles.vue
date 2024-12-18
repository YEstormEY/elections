<script lang="ts">
import { defineComponent } from 'vue';
import { fetchArticles } from "@/service/ArticleService";

/**
 * Component to display random articles excluding the current article
 * this component is used in the ArticleDetails component
 */
export default defineComponent({
  name: "RandomArticles",
  props: {
    // The ID of the current article
    articleId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      articles: [] as any[],
      randomArticle: [] as any[],
    };
  },
  methods: {
    /**
     * Fetches all articles from the backend
     */
    async fetchArticles() {
      try {
        // Fetch articles from the backend
        const response = await fetchArticles();
        if (!response) {
          console.error('Failed to fetch articles');
          return;
        } else {
          // Store the articles in the data property
          this.articles = response;
          this.randomArticles(); // Call to generate random articles
        }
      } catch (error) {
        if (error instanceof Error) {
          throw new Error(error.message);
        } else {
          throw new Error('An unknown error occurred');
        }
      }
    },
    /**
     * Generates random articles excluding the current article
     */
    randomArticles() {
      this.randomArticle = []; // Reset the array
      const articlesCopy = [...this.articles]; // Make a shallow copy to avoid modifying the original array
      for (let i = 0; i < articlesCopy.length; i++) {
        if (articlesCopy[i].articleId === this.articleId) {
          articlesCopy.splice(i, 1); // Remove the current article from the list
          break;
        }
      }
      // Select up to 3 random unique articles
      for (let i = 0; i < 4 && articlesCopy.length > 0; i++) {
        const randomIndex = Math.floor(Math.random() * articlesCopy.length); // Generate a random index
        this.randomArticle.push(articlesCopy[randomIndex]); // Add the random article to the list
        articlesCopy.splice(randomIndex, 1);
      }
    }
  },
  // Fetch articles when the component is mounted
  mounted() {
    this.fetchArticles();
  },
  // Watch for changes in the articleId prop
  watch: {
    articleId() {
      this.randomArticles(); // Call to generate random articles whenever the articleId changes
    }
  }
});
</script>

<template>
  <div class="container-fluid mt-5">
    <section class="articles-section py-3">
      <div class="row g-3">
        <div
            class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4"
            v-for="(article, index) in randomArticle"
            :key="index"
        >
          <div class="card h-100 article-card">
            <img
                :src="article.imagePath"
                :alt="article.title"
                class="card-img-top img-fluid"
            />
            <div class="card-body d-flex flex-column">
              <h3 class="card-title">{{ article.title }}</h3>
              <p class="card-text flex-grow-1">{{ article.summary }}</p>
              <router-link
                  :to="{ name: 'Article', params: { articleId: Number(article.articleId) } }"
                  class="btn btn-outline-primary mt-auto"
              >
                Read More
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.articles-section {
  padding: 30px;
}

.article-card {
  border: 1px solid #ddd;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  min-height: 500px; /* Added minimum height to make the cards taller */
}

.article-card:hover {
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.3);
}

.card-img-top {
  height: 300px; /* Increased the height of the image to make the card taller */
  object-fit: cover;
}

.card-body {
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.card-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.card-text {
  font-size: 1rem;
  margin-bottom: 15px;
  flex-grow: 1;
}

.btn {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
  margin-top: 10px;
}

.btn:hover {
  background-color: #218838;
}
</style>








