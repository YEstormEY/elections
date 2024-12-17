<script lang="ts">
import {defineComponent} from 'vue';
import {deleteArticle, editArticle, fetchAllArticles, getCategories} from '@/service/AdminService'

interface Article {
  articleId: number; // Changed from id to articleId
  title: string;
  content: string;
  summary: string;
  imagePath: string;
  categoryId: number[]; // Array of category IDs
}


/**
 * this component is used to display all articles in the admin panel
 * this component also allows admins to edit and delete articles
 */
export default defineComponent({
  name: 'ArticlesAdmin',

  data() {
    return {
      articles: [] as Array<{
        articleId: number;
        title: string;
        content: string;
        summary: string;
        imagePath: string;
        categories: { categoryId: number; name: string }[]; // Correct categories type
      }>,
      selectedArticle: null as Article | null,
      selectedCategories: [] as Array<{ categoryId: number; name: string }>,
      categories: [] as Array<{ categoryId: number; name: string }>,
      editCategorie: false,
      categoryIds: [] as Array<number>
    };
  },

  methods: {
    /**
     * Fetches all articles from the backend and stores them in the 'articles' array
     * it calls the fetchAllArticles method from the AdminService
     */
    async getArticles() {
      const response = await fetchAllArticles();
      this.articles = await response;
      console.log(this.articles);
    },
    /**
     * this method saves the edited article in a separate object
     * this object gets send to the backend to update the article
     * @param article
     */
    editArticle(article: {
      articleId: number;
      title: string;
      content: string;
      summary: string;
      imagePath: string;
      categories: { categoryId: number; name: string }[]
    }) {
      // Clone the article to avoid direct mutation
      this.selectedArticle = {
        ...article,
        categoryId: article.categories.map(category => category.categoryId), // Extract category IDs
      };
    },

    /**
     * this method saves the edited article back to the backend
     * before saving all fields are validated
     * and closes the form after saving
     */
    async saveArticle() {
      if (this.selectedArticle === null) return;

      // Validate form fields
      if (!this.selectedArticle.title || !this.selectedArticle.content || !this.selectedArticle.summary) {
        alert('Please fill in all fields.');
        return;
      }

      if (this.selectedArticle.title.trim() === '' || this.selectedArticle.content.trim() === '' || this.selectedArticle.summary.trim() === '') {
        alert('Please fill in all fields.');
        return;
      }

      if (this.selectedArticle.title.length < 5 || this.selectedArticle.content.length < 10 || this.selectedArticle.summary.length < 10) {
        alert('Please enter at least 5 characters for the title and 10 characters for the content and summary.');
        return;
      }

      if (!confirm('Are you sure you want to save changes?')) {
        return;
      }

      // Save the article back to the backend
      const response = await editArticle(this.selectedArticle);
      console.log('Response:', response);

      // Refresh the articles list
      this.cancelEdit();
      await this.getArticles();
    },

    /**
     * this method deletes an article from the backend
     * @param id
     */
    async deleteArticle(id: number) {
      console.log('Deleting article with ID:', id);
      if (id === undefined || id === null) {
        console.error("Delete failed: Invalid article ID (undefined or null).");
        alert("Cannot delete: Invalid article ID.");
        return;
      }

      if (!(confirm('Are you sure you want to delete this article?'))) return;

      console.log("Attempting to delete article with ID:", id);

      const response = await deleteArticle(id);
      console.log('Response:', response);
      await this.getArticles(); // Refresh the articles list
    },

    async fetchCategories() {
      const response = await getCategories();
      this.categories = await response;
      console.log(this.categories);
    },

    cancelEdit() {
      this.selectedArticle = null;
    }

  },
  mounted() {
    this.getArticles();
    this.fetchCategories();
  }
});
</script>

<template>
  <div>
    <h1>Articles</h1>
    <div v-if="articles.length && !selectedArticle">
      <ul>
        <li v-for="article in articles" :key="article.articleId">
          <h2>{{ article.title }}</h2>
          <p>{{ article.summary }}</p>
          <p>{{ article.imagePath }}</p>
          <div>
            <strong>Categories:</strong>
            <span v-for="category in article.categories" :key="category.categoryId"></span>
            <span v-if="!article.categories.length">No categories</span>
          </div>
          <button class="btn-green" @click="editArticle(article)">Edit</button>
          <button class="btn-red" @click="deleteArticle(article.articleId)">Delete</button>
        </li>
      </ul>
    </div>
    <p v-else-if="!articles.length">No articles available. Click "Get Articles" to load.</p>
    <div v-else-if="selectedArticle">
      <h2>Edit Article</h2>
      <form @submit.prevent="saveArticle">
        <div>
          <label>
            Title:
            <input type="text" v-model="selectedArticle.title"/>
          </label>
        </div>
        <div>
          <label>
            Content:
            <textarea v-model="selectedArticle.content"></textarea>
          </label>
        </div>
        <div>
          <label>
            Summary:
            <textarea v-model="selectedArticle.summary"></textarea>
          </label>
        </div>
        <div>
          <label>
            Image path:
            <textarea v-model="selectedArticle.imagePath"></textarea>
          </label>
        </div>
        <div v-if="categories.length">
          <label>Categories:</label>
          <div class="category-checkboxes">
            <div
                v-for="category in categories"
                :key="category.categoryId"
                class="checkbox-wrapper"
            >
              <input
                  type="checkbox"
                  :id="'category-' + category.categoryId"
                  :value="category.categoryId"
                  v-model="selectedArticle.categoryId"
              />
              <label :for="'category-' + category.categoryId">{{ category.name }}</label>
            </div>
          </div>
        </div>



        <button class="btn-green" type="submit">Save</button>
        <button class="btn-red" type="button" @click="cancelEdit">Cancel</button>
      </form>
    </div>
  </div>
</template>


<style scoped>

h1 {
  font-size: 2rem;
  color: #333;
  margin-bottom: 20px;
}

button {
  margin: 10px 5px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-green {
  margin-left: 30px;
  border: none;
  color: white;
  background-color: #28a745;
}

.btn-red {
  background-color: orangered;
  border: none;
  color: white;
}

.btn-green:hover {
  background-color: #218838;
}

.btn-red:hover {
  background-color: darkred;
}

ul {
  list-style-type: none;
  padding: 0;
  width: 100%;
  max-width: 800px;
}

li {
  margin: 15px 0;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

li h2 {
  margin: 0 0 10px;
  font-size: 1.5rem;
}

li p {
  margin: 0 0 10px;
  font-size: 1rem;
  color: #666;
}

form {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 600px;
}

form h2 {
  margin-bottom: 20px;
  font-size: 1.8rem;
}

form {
  margin-bottom: 15px;
}

form .form-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

form {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

form:focus {
  border-color: #8ef78f;
  outline: none;
  box-shadow: 0 0 5px rgba(142, 247, 143, 0.5);
}

form button {
  margin-right: 10px;
  padding: 10px 20px;
  font-size: 1rem;
}

input[type="checkbox"] {
  margin-right: 5px;
}

input[type=text] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

input[type=text]:focus {
  border-color: #8ef78f;
  outline: none;
  box-shadow: 0 0 5px rgba(142, 247, 143, 0.5);
}

textarea {
  width: 100%;
  height: 150px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
  resize: vertical;
}

textarea:focus {
  border-color: #8ef78f;
  outline: none;
  box-shadow: 0 0 5px rgba(142, 247, 143, 0.5);
}
</style>
