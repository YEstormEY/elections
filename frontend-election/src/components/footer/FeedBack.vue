<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">📝 Laat Jouw Stem Horen!</h2>
    <p class="text-center mb-5">Vertel ons wat je denkt over onze website en help ons verbeteren. 👇</p>

    <!-- User Information Form -->
    <form @submit.prevent="submitFeedback" class="feedback-form">
      <!-- User Information Section -->
      <div class="user-info">
        <div class="mb-3">
          <label for="name" class="form-label">🔤 Naam</label>
          <input type="text" v-model="feedbackData.name" class="form-control" required placeholder="Bijv. Sarah Jansen" />
        </div>

        <div class="mb-3">
          <label for="age" class="form-label">🎂 Leeftijd</label>
          <input type="number" v-model="feedbackData.age" class="form-control" required placeholder="Bijv. 17" />
        </div>

        <div class="mb-3">
          <label for="education" class="form-label">🎓 Onderwijsniveau</label>
          <input type="text" v-model="feedbackData.educationLevel" class="form-control" required placeholder="Bijv. VWO, HAVO" />
        </div>
      </div>

      <!-- Feedback Items Section -->
      <div v-for="(item, index) in feedbackData.feedbackItems" :key="index" class="feedback-item">
        <hr />
        <div class="mb-3">
          <label class="form-label">📄 Pagina</label>
          <select v-model="item.page" class="form-control" required>
            <option disabled value="">Selecteer een pagina</option>
            <option>Homepage</option>
            <option>Quiz Pagina</option>
            <option>Contact Pagina</option>
          </select>
        </div>

        <div class="mb-3">
          <label class="form-label">💬 Type Feedback</label>
          <select v-model="item.type" class="form-control" required>
            <option disabled value="">Selecteer een type</option>
            <option value="LIKE">Leuk</option>
            <option value="DISLIKE">Niet leuk</option>
            <option value="SUGGESTION">Suggestie</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="comment" class="form-label">🖊️ Opmerkingen</label>
          <textarea v-model="item.comment" class="form-control" placeholder="Wat wil je kwijt?" required></textarea>
        </div>

        <button type="button" @click="removeFeedbackItem(index)" class="btn btn-danger">🗑️ Verwijder dit item</button>
      </div>

      <!-- Add More Feedback Item Button -->
      <button type="button" @click="addFeedbackItem" class="btn btn-secondary mt-3">+ Voeg meer feedback toe</button>

      <!-- Submit Button -->
      <button type="submit" class="btn btn-primary mt-4">Verzend Feedback 🚀</button>
    </form>

    <!-- Success/Error Messages -->
    <div v-if="successMessage" class="alert alert-success mt-4">{{ successMessage }}</div>
    <div v-if="errorMessage" class="alert alert-danger mt-4">{{ errorMessage }}</div>
  </div>
</template>

<script lang="ts">
import { ref } from "vue";

export default {
  setup() {
    const feedbackData = ref({
      name: "",
      age: null,
      educationLevel: "",
      feedbackItems: [{ page: "", type: "", comment: "" }],
    });

    const successMessage = ref("");
    const errorMessage = ref("");

    const addFeedbackItem = () => {
      feedbackData.value.feedbackItems.push({ page: "", type: "", comment: "" });
    };

    const removeFeedbackItem = (index:number) => {
      feedbackData.value.feedbackItems.splice(index, 1);
    };

    const submitFeedback = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/feedback/submit", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(feedbackData.value),
        });

        if (!response.ok) throw new Error("Er is iets fout gegaan bij het verzenden van de feedback.");

        successMessage.value = "Feedback succesvol verzonden!";
        errorMessage.value = "";
        feedbackData.value = { name: "", age: null, educationLevel: "", feedbackItems: [{ page: "", type: "", comment: "" }] };
      } catch (error: any) {
        successMessage.value = "";
        errorMessage.value = error.message;
      }
    };

    return {
      feedbackData,
      successMessage,
      errorMessage,
      addFeedbackItem,
      removeFeedbackItem,
      submitFeedback,
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 600px;
  padding: 1rem;
  background: #f7f9fc;
  border-radius: 8px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

h2, p {
  color: #333;
}

.feedback-form {
  font-size: 1rem;
}

.user-info {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.feedback-item {
  background-color: #f0f4ff;
  padding: 1rem;
  border-radius: 8px;
  margin-top: 1rem;
}

.form-label {
  font-weight: bold;
  color: #4a4a4a;
}

.btn-primary {
  background-color: #007bff;
  border: none;
  font-weight: bold;
  margin-left: 0.5rem;
  margin-bottom: 0.5rem;
}

.btn-secondary {
  background-color: #6c757d;
  border: none;
  color: white;
}

.btn-danger {
  background-color: #ff4757;
  border: none;
  color: white;
  font-weight: bold;
}
</style>




