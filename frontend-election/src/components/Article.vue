<script lang="ts">
import { defineComponent } from 'vue';
import {
  checkIfLiked,
  fetchArticleById,
  getArticleLikes,
  likeArticle,
  unlikeArticle
} from "@/service/ArticleService";
import Comments from './CommentSection.vue'; // Import the Comments component
import { authState } from "@/utils/auth"; // Import the authentication state
import RandomArticles from "@/components/RandomArticles.vue"; // Import the RandomArticles component

export default defineComponent({
  name: "Article-page",

  // Computed property to track authentication state
  computed: {
    authState() {
      return authState;
    }
  },

  components: {
    RandomArticles, // Register the RandomArticles component
    Comments, // Register the Comments component
  },

  props: {
    // The ID of the article passed as a prop
    articleId: {
      type: Number,
      required: true,
    },
  },

  data() {
    return {
      article: {} as { imagePath?: string; title?: string; summary?: string; content?: string; created_at?: string },
      date: '',
      newArticleId: 0, // Change type to number
      likes: 0,
      isLiked: false,
    };
  },

  methods: {
    /**
     * Fetches the article by its ID and updates the data.
     */
    async getArticle() {
      try {
        const article = await fetchArticleById(this.articleId); // Fetch article from service
        this.article = article;
        this.newArticleId = article.articleId; // Update navigation article ID
        this.date = new Date(article.created_at).toDateString(); // Format and store the date
      } catch (error) {
        if (error instanceof Error) {
          console.log(error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },

    /**
     * Fetches the number of likes for the current article.
     */
    async getLikes() {
      try {
        this.likes = await getArticleLikes(this.articleId); // Fetch likes count from service
      } catch (error) {
        if (error instanceof Error) {
          console.log(error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },

    /**
     * Checks if the current user has liked the article.
     */
    async checkIfLiked() {
      try {
        this.isLiked = await checkIfLiked(this.articleId); // Check like status from service
      } catch (error) {
        if (error instanceof Error) {
          console.log(error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },

    /**
     * Toggles the like status for the article.
     * If already liked, it unlikes; otherwise, it likes the article.
     */
    async likeArticle() {
      try {
        if (this.isLiked) {
          // Unlike the article if already liked
          await unlikeArticle(this.articleId);
          this.likes--; // Decrement the like count
          this.isLiked = false; // Update like status
          return;
        }
        // Like the article if not liked
        await likeArticle(this.articleId);
        this.likes++; // Increment the like count
        this.isLiked = true; // Update like status
      } catch (error) {
        if (error instanceof Error) {
          console.log(error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },

    /**
     * Navigates to the next article.
     */
    nextArticle() {
      this.newArticleId = Number(this.newArticleId) + 1; // Increment the article ID
      this.$router.push({ name: 'Article', params: { articleId: this.newArticleId } }); // Navigate to the next article
    },

    /**
     * Navigates to the previous article.
     */
    previousArticle() {
      this.newArticleId = Number(this.newArticleId) - 1; // Decrement the article ID
      this.$router.push({ name: 'Article', params: { articleId: this.newArticleId } }); // Navigate to the previous article
    },

    /**
     * Scrolls to the top of the page.
     */
    scrollToTop() {
      window.scrollTo(0, 0);
    }
  },

  mounted() {
    this.getArticle(); // Fetch article data on mount
    this.getLikes(); // Fetch likes on mount
    this.checkIfLiked(); // Check like status on mount
    this.scrollToTop(); // Scroll to top of the page on mount
  },

  watch: {
    // Watch for changes in the article ID from the route parameters
    '$route.params.articleId': {
      immediate: true, // Trigger the handler immediately
      handler() {
        this.getArticle(); // Fetch new article data
        this.getLikes(); // Fetch new likes count
        this.checkIfLiked(); // Check like status for the new article
      }
    }
  }
});
</script>


<template>
  <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
  />
  <div class="background">
    <div class="container mt-5">
      <!-- Article card -->
      <router-link to="/articlepage">
        <button class="btn btn-red btn-block mt-3">Back</button>
      </router-link>

      <div class="card shadow-lg">
        <!-- Image Section -->
        <img :src="article.imagePath" class="card-img-top" alt="Article Image">

        <!-- Content Section -->
        <div class="card-body">
          <h5 class="card-title">{{ article.title }}</h5>
          <p class="text-muted">{{ date }}</p>
          <p class="card-text">{{ article.summary }}</p>
          <div class="article-content" v-html="article.content"></div>

          <!-- Likes Section -->
          <div class="likes-section mt-3">
            <button
                v-if="authState"
                @click="likeArticle"
                :class="{'btn-liked': isLiked, 'btn-unliked': !isLiked}"
                class="btn btn-like"
            >
              <i class="fas fa-thumbs-up"></i>
            </button>
            <span class="likes-count">Likes: {{ likes }}</span>
          </div>
          <p class="likes-count" v-if="!authState">login to like an article.</p>

          <!-- Navigation Buttons -->
          <div class="navigation-buttons mt-4">
            <button @click="previousArticle" class="btn btn-outline-primary">Previous Article</button>
            <button @click="nextArticle" class="btn btn-outline-primary">Next Article</button>
          </div>
        </div>
      </div>
    </div>

    <div class="container mt-5">
      <Comments :articleId="articleId" />
    </div>
  </div>
  <div class="container-fluid mt-5">
    <h2 class="text-center">You may also like:</h2>
    <random-articles :articleId="articleId" />
  </div>

</template>


<style scoped>
.background {
  background-color: #24786E99;
  width: 100%;
  padding: 20px;
  min-height: 60vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}

.container {
  max-width: 900px;
}

.card-img-top {
  height: 300px;
  object-fit: cover;
}

.card-body {
  padding: 30px;
}

.card-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
}

.card-text {
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.article-content {
  font-size: 1rem;
  line-height: 1.6;
  color: #333;
}

.likes-section {
  display: flex;
  align-items: center;
  font-size: 1.1rem;
  color: #555;
}

.btn-outline-primary {
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

.btn-outline-primary:hover {
  background-color: #218838;
}

.btn-red {
  background-color: orangered;
  margin: 6px;
  color: white;
  border: none;
}

.btn-red:hover {
  background-color: darkred;
  color: white;
}


.btn-like {
  padding: 8px 16px;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  margin-right: 10px;
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-liked {
  background-color: #28a745;
  color: white;
}

.btn-unliked {
  background-color: #6c757d;
  color: white;
}

.btn-like i {
  margin-right: 5px;
}

.navigation-buttons {
  display: flex;
  justify-content: space-between;
}

@media (max-width: 768px) {
  .card-title {
    font-size: 1.5rem;
  }

  .card-text {
    font-size: 1rem;
  }

  .navigation-buttons {
    flex-direction: column;
    align-items: center;
  }
}


</style>
