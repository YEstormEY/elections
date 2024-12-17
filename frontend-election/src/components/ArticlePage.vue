<script lang="ts">
import { fetchArticles } from '@/service/ArticleService'

export default {
  data() {
    return {
      errorMessage: '',
      allArticles: [],
      filteredArticles: [],
      selectedCategory: '',
      searchTerm: ''
    }
  },
  methods: {
    async getArticles() {
      try {
        this.allArticles = await fetchArticles()
        this.sortByDate() // Sort articles by date (newest first)
        this.filteredArticles = [...this.allArticles] // Initialize filtered articles
        console.log('Articles:', this.filteredArticles)
      } catch (error) {
        this.errorMessage = error.message
        console.log(this.errorMessage)
      }
    },

    filterArticles() {
      if (this.selectedCategory) {
        this.filteredArticles = this.allArticles.filter(article =>
          article.categories.some(category => category.name === this.selectedCategory)
        )
      } else {
        this.filteredArticles = [...this.allArticles]
      }
    },

    sortByDate() {
      this.allArticles.sort((a, b) => {
        const dateA = new Date(a.created_at) // Parse 'created_at' as Date object
        const dateB = new Date(b.created_at)
        return dateB - dateA // Sort descending (newest first)
      })
    },

    seachArticles() {
      this.filteredArticles = this.allArticles.filter(article =>
        article.title.toLowerCase().includes(this.searchTerm.toLowerCase())
      )
    }
  },
  watch: {
    selectedCategory() {
      this.filterArticles() // Trigger filtering whenever the selected category changes
    },
    searchTerm() {
      this.seachArticles() // Trigger search whenever the search term changes
    }
  },
  mounted() {
    this.getArticles()
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
              <input class="form-control" type="search" placeholder="Search" aria-label="Search" v-model="searchTerm">
            </div>
          </form>
          <br>
          <label for="category" class="mr-2">Filter by Category:</label>
          <select class="filter-select selectpicker" v-model="selectedCategory">
            <option value="">all</option>
            <option value="Politics">Politics</option>
            <option value="Elections">Elections</option>
            <option value="Healthcare">Healthcare</option>
            <option value="Environment">Environment</option>
          </select>
        </div>
      </div>

      <section class="articles-section py-5">
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
  border: 2px solid #007bff;
  color: #007bff;
  font-size: 1rem;
  padding: 10px;
  border-radius: 8px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.filter-select:focus {
  outline: none;
  box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
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

/* Button inside card */
.btn-outline-primary {
  border-color: #007bff;
  color: #007bff;
  transition: background-color 0.3s ease, color 0.3s ease;
  margin-top: auto; /* Ensures button stays at the bottom */
}

.btn-outline-primary:hover {
  background-color: #007bff;
  color: #fff;
}

.form-control {
  width: 100%;
  max-width: 400px;
  border: 2px solid #007bff;
  padding: 10px;
  border-radius: 8px;
  transition: box-shadow 0.3s ease;
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

  .form-control {
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

  .form-control {
    padding: 6px;
    font-size: 0.9rem; /* Adjust font size for mobile screens */
  }
}
</style>
