<script lang="ts">
import {defineComponent} from 'vue';
import {checkIfLiked, fetchArticleById, getArticleLikes, likeArticle, unlikeArticle} from "@/service/ArticleService";
import Comments from './CommentSection.vue';
import {authState} from "@/utils/auth";
import RandomArticles from "@/components/RandomArticles.vue"; // Import the Comments component

export default defineComponent({
  name: "Article-page",
  computed: {
    authState() {
      return authState
    }
  },
  components: {
    RandomArticles,
    Comments, // Register the Comments component
  },
  props: {
    articleId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      article: {},
      date: '',
      newArticleId: '',
      likes: 0,
      isLiked: false, // Track whether the user has liked the article
    };
  },
  methods: {
    async getArticle() {
      try {
        const article = await fetchArticleById(this.articleId);
        this.article = article;
        this.newArticleId = article.articleId;
        this.date = new Date(article.created_at).toDateString();
      } catch (error) {
        console.log(error.message);
      }
    },

    async getLikes() {
      try {
        this.likes = await getArticleLikes(this.articleId);
      } catch (error) {
        console.log(error.message);
      }
    },

    async checkIfLiked() {
      try {
        this.isLiked = await checkIfLiked(this.articleId);
      } catch (error) {
        console.log('Failed to check if liked:', error.message);
      }
    },

    async likeArticle() {
      try {
        if (this.isLiked) {
          await unlikeArticle(this.articleId);
          this.likes--;
          this.isLiked = false;
          return;
        }
        await likeArticle(this.articleId);
        this.likes++;
        this.isLiked = true;
      } catch (error) {
        console.log('Failed to like the article:', error.message);
      }
    },

    nextArticle() {
      this.newArticleId = Number(this.newArticleId) + 1;
      this.$router.push({name: 'Article', params: {articleId: this.newArticleId}});
    },

    previousArticle() {
      this.newArticleId = Number(this.newArticleId) - 1;
      this.$router.push({name: 'Article', params: {articleId: this.newArticleId}});
    },

    scrollToTop() {
      window.scrollTo(0, 0);
    }
  },
  mounted() {
    this.getArticle();
    this.getLikes();
    this.checkIfLiked();
    this.scrollToTop();
  },
  watch: {
    '$route.params.articleId': {
      immediate: true,
      handler() {
        this.getArticle();
        this.getLikes();
        this.checkIfLiked();
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
        <button class="btn btn-secondary btn-block mt-3">Back</button>
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
  background-color: #007bff;
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
