<script lang="ts">
import {fetchArticles, fetchArticlesByCategory, fetchArticlesBySearch} from '@/service/ArticleService'
import {getCategories} from "@/service/AdminService";

/**
 * Interface for an Article
 */
interface Article {
  articleId: number;    // Unique identifier for the article
  title: string;        // Title of the article
  summary: string;      // Summary of the article
  created_at: string;   // Date when the article was created
  imagePath: string;    // Path to the article's image
}

/**
 * Interface for a Category
 */
interface Category {
  categoryId: number;   // Unique identifier for the category
  name: string;         // Name of the category
}

export default {
  data() {
    return {
      errorMessage: '', // Error message to display in case of API failure
      allArticles: [] as Article[], // Array to store all articles
      allCategories: [] as Category[], // Array to store all categories
      filteredArticles: [] as Article[], // Array to store filtered articles
      selectedCategory: null as Category | null, // Changed from string to Category or null
      searchTerm: '',// Search term for filtering
      categoryId: 0,// Category ID for filtering
    }
  },
  methods: {
    /**
     * Fetch articles and store them in the 'allArticles' array
     */
    async getArticles() {
      try {
        this.allArticles = await fetchArticles()
        this.sortByDate() // Sort articles by date (newest first)
        this.filteredArticles = [...this.allArticles] // Initialize filtered articles
        console.log('Articles:', this.filteredArticles)
      } catch (error) {
        if (error instanceof Error) {
          throw new Error(error.message);
        } else {
          throw new Error('An unknown error occurred');
        }
      }
    },

    /**
     * Fetch categories and store them in the 'allCategories' array
     */
    async getCategory() {
      try {
        this.allCategories = await getCategories(); // Fetch all categories
      } catch (error) {
        if (error instanceof Error) {
          throw new Error(error.message);
        } else {
          throw new Error('An unknown error occurred');
        }
      }
    },

    /**
     * Filter articles based on the selected category
     */
    async filterArticles() {
      if (!this.selectedCategory) {
        this.filteredArticles = [...this.allArticles]; // Reset to all articles if no category is selected
        return;
      }
      try {
        this.searchTerm = ''; // Reset search term
        const categoryId = this.selectedCategory.categoryId; // Access the categoryId safely
        const response = await fetchArticlesByCategory(categoryId); // Fetch articles by category
        if (response) {
          console.log('Filtered Articles:', response);
          this.filteredArticles = response;
        }
        this.sortByDate(); // Sort articles by date (newest first)
      } catch (error) {
        if (error instanceof Error) {
          throw new Error(error.message);
        } else {
          throw new Error('An unknown error occurred');
        }
      }
    },

    /**
     * Sort articles by date (newest first)
     */
    sortByDate() {
      this.allArticles.sort((a, b) => {
        const dateA = new Date(a.created_at).getTime(); // Parse 'created_at' as Date object
        const dateB = new Date(b.created_at).getTime();
        return dateB - dateA // Sort descending (newest first)
      })
    },

    /**
     * Filter articles based on the search term
     */
    async searchArticles() {
      try {
        this.selectedCategory = null // Reset selected category
        const response = await fetchArticlesBySearch(this.searchTerm); // Fetch articles by search term
        if (response) {
          this.filteredArticles = response;
        } else {
          this.filteredArticles = [...this.allArticles] // Reset to all articles if no search results
        }
      } catch (error) {
        if (error instanceof Error) {
          throw new Error(error.message);
        } else {
          throw new Error('An unknown error occurred');
        }
      }
    },
  },

  /**
   * Watcher to trigger filtering when the selected category changes
   */
    watch: {
      selectedCategory() {
        this.filterArticles() // Trigger filtering whenever the selected category changes
      },
    },
  /**
   * Fetch articles and categories when the component is mounted
   */
    mounted() {
      this.getArticles() // Fetch articles when the component is mounted
      this.getCategory(); // Fetch categories when the component is mounted
    }
}
</script>

<template>
  <div class="background">
    <div class="container-fluid news-page">
      <div class="flex-grow-1 text-center">
        <h2 class="section-title">Latest Articles</h2>
      </div>
      <div class="articles-header d-flex align-items-center mb-4">
        <!-- Filter Dropdown -->
        <div class="filter-container">
          <form class="form-inline my-2 my-lg-0">
            <div class="input-group">
              <input class="input-search" type="search" placeholder="Search" aria-label="Search" v-model="searchTerm">
              <button class="btn btn-outline-primary" type="button" @click="searchArticles">Search</button>
            </div>
          </form>
          <br>
          <label for="category" class="mr-2">Filter by Category:</label>
          <select class="filter-select selectpicker" v-model="selectedCategory">
            <option :value="null">All</option> <!-- Default option -->
            <option v-for="category in allCategories" :key="category.categoryId" :value="category">
              {{ category.name }}
            </option>
          </select>
        </div>
      </div>

      <section class="articles-section py-5">
        <h4 v-if="filteredArticles.length === 0">No articles found</h4>
        <div class="row">
          <div
            class="col-lg-4 col-md-6 col-sm-12 d-flex align-items-stretch mb-4"
            v-for="(article, index) in filteredArticles"
            :key="index"
          >
            <div class="card h-100">
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
  </div>
</template>


<style scoped>
.background {
  background-color: #24786e99;
  padding: 20px;
  min-height: 60vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}

/* Articles Header */
.articles-header {
  padding: 20px;
  display: flex;
}

.filter-container {
  flex: 0 1 auto;
}

.section-title {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
}

/* General section background color */
.articles-section {
  padding: 50px 0;
}

.articles-section .row {
  display: flex;
  flex-wrap: wrap; /* Allow the cards to wrap to the next row if needed */
}

/* Filter Dropdown Styling */
.filter-select {
  border: 2px solid #28a745;
  font-size: 1rem;
  padding: 10px;
  border-radius: 8px;
  transition: background-color 0.3s ease, color 0.3s ease;
}


/* Card styles */
.articles-section .card {
  flex: 1 1 calc(33.333% - 30px); /* Each card takes up 1/3 of the container minus some margin */
  margin: 15px;
  display: flex;
  flex-direction: column;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  height: 100%; /* Ensure all cards take up full height */
  min-height: 400px; /* Set a minimum height to ensure uniformity */
}

/* Set minimum height for card */
.articles-section .card-body {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* Ensure even spacing between elements */
}

/* Card images */
.card-img-top {
  height: 400px;
  object-fit: cover; /* Ensures image covers the space and maintains aspect ratio */
}

/* Card titles */
.card-title {
  font-size: 1.5rem;
  margin-bottom: 15px;
  color: #333;
  font-weight: bold;
}

/* Card text */
.card-text {
  font-size: 1rem;
  color: #555;
  flex-grow: 1; /* Ensures the text takes available space in the card */
}

/* Styling for the search button */
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

.input-search {
  width: 100%;
  max-width: 400px;
  padding: 10px;
  border: 2px solid #28a745;
  border-radius: 8px;
  font-size: 1rem;
  transition: background-color 0.3s ease, color 0.3s ease;
}

/* Responsive design adjustments */
@media (max-width: 768px) {
  .filter-select {
    font-size: 1rem;
    padding: 8px;
  }

  .card-img-top {
    height: 300px;
  }

  .card {
    min-height: 350px; /* Adjust minimum height for smaller screens */
  }

  .card-title {
    font-size: 1.3rem;
  }

  .card-text {
    font-size: 0.9rem;
  }

  .input-search {
    max-width: 100%; /* Allow full width on smaller screens */
    padding: 8px;
  }
}

@media (max-width: 576px) {
  .filter-select {
    font-size: 0.9rem;
    padding: 6px;
  }

  .card-title {
    font-size: 1.2rem;
  }

  .card-text {
    font-size: 0.85rem;
  }

  .btn-outline-primary {
    font-size: 0.85rem;
  }

  .card-img-top {
    height: 250px;
  }

  .card {
    min-height: 300px; /* Further reduce the minimum height for mobile */
  }

  .input-search {
    padding: 6px;
    font-size: 0.9rem; /* Adjust font size for mobile screens */
  }
}
</style>
