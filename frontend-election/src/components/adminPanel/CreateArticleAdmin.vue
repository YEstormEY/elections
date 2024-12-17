<script lang="ts">
import {defineComponent} from 'vue'
import {createArticle, getCategories} from "@/service/AdminService";

interface Category {
  categoryId: number;
  name: string;
}

export default defineComponent({
  name: "CreateArticleAdmin",

      data() {
        return {
          newArticle: {
            articleId: 0, // Placeholder for a new article
            title: '',
            summary: '',
            content: '',
            imagePath: 'localhost:8080/img/', // Updated field name
            categoryId: [] as number[], // Ensure categoryId is an array
          },

          categories: [] as Category[], // Array of categories
          errorMessage: '', // Error message
        };
    },

  methods: {
    /**
     * Create a new article by the admin
     */
    async createArticle() {
      try {
        const articlePayload = {
          title: this.newArticle.title,
          summary: this.newArticle.summary,
          content: this.newArticle.content,
          imagePath: this.newArticle.imagePath, // Correct field name
          categoryId: this.newArticle.categoryId,
        };
        await createArticle(articlePayload); // Pass the structured payload
        alert("Article created successfully");
      } catch (error) {
        if (error instanceof Error) {
          console.error("Error creating article:", error.message);
          this.errorMessage = "An error occurred while creating the article";
        } else {
          console.error("An unknown error occurred:", error);
          this.errorMessage = "An unknown error occurred";
        }
      }
    },

    /**
     * Fetch all categories from the backend
     */
    async getCategories() {
      try {
        // Call the getCategories method
        await getCategories();

      } catch (error) {
        if (error instanceof Error) {
          console.error('Error fetching categories:', error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    }
  },

  mounted() {
    // Fetch all categories
    this.getCategories();
  }
});
</script>

<template>

  <form class="user-form mt-4">
    <div class="form-group mb-3">
      <label for="title">Title:</label>
      <input type="text" id="title" v-model="newArticle.title" class="form-control"/>
    </div>

    <div class="form-group mb-3">
      <label for="summary">Summary:</label>
      <input type="text" id="summary" v-model="newArticle.summary" class="form-control"/>
    </div>

    <div class="form-group mb-3">
      <label for="content">Content:</label>
      <textarea id="content" v-model="newArticle.content" class="form-control"></textarea>
    </div>

    <div class="form-group mb-3">
      <label for="imagePath">Image Path:</label>
      <input type="text" id="imagePath" v-model="newArticle.imagePath" class="form-control" />
    </div>


    <div class="form-group mb-3">
      <label>Categories:</label>
      <div>
        <div v-for="category in categories" :key="category.categoryId" class="form-check">
          <input
              class="form-check-input"
              type="checkbox"
              :id="'category-' + category.categoryId"
              :value="category.categoryId"
              v-model="newArticle.categoryId"
          />
          <label class="form-check-label" :for="'category-' + category.categoryId">
            {{ category.name }}
          </label>
        </div>
      </div>
    </div>

    <div class="form-group mt-4">
      <button class="btn btn-green me-3" @click.prevent="createArticle">Create Article</button>
    </div>
  </form>

</template>

<style scoped>
.user-form {
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.user-form .form-group, .article-form .form-group {
  margin-bottom: 15px;
}

.article-form label {
  font-weight: bold;
  color: #333;
}

.article-form .form-control {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-top: 5px;
}

.btn-green {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.btn-green:hover {
  background-color: #218838;
}
</style>