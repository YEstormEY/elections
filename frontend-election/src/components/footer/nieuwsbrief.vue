<script lang="ts">
import { ref } from 'vue';

export default {
  setup() {
    const name = ref('');
    const email = ref('');
    const successMessage = ref('');
    const errorMessage = ref('');

    const submitForm = async () => {
      try {
        const response = await fetch('http://localhost:8080/newsletter/submit', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            name: name.value,
            email: email.value,
          }),
        });

        if (!response.ok) {
          throw new Error('Failed to submit newsletter form');
        }

        const result = await response.json();
        console.log('Newsletter form successfully submitted:', result);

        // Display success message and clear form fields
        successMessage.value = 'Bedankt voor je inschrijving! Je bent nu aangemeld voor onze nieuwsbrief.';
        errorMessage.value = '';
        name.value = '';
        email.value = '';
      } catch (error) {
        console.error('Error submitting form:', error);
        successMessage.value = '';
        errorMessage.value = 'Er is iets misgegaan. Probeer het opnieuw.';
      }
    };

    return {
      name,
      email,
      successMessage,
      errorMessage,
      submitForm,
    };
  },
};
</script>

<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">Schrijf je in voor de Nieuwsbrief</h2>
    <p class="text-center mb-5">
      Blijf op de hoogte van het laatste nieuws en updates door je in te schrijven voor onze nieuwsbrief. We sturen regelmatig updates over belangrijke gebeurtenissen, politieke inzichten, en evenementen direct naar je inbox.
    </p>
    <div class="row justify-content-center">
      <div class="col-md-6">
        <form @submit.prevent="submitForm">
          <!-- Name Field -->
          <div class="mb-3">
            <label for="name" class="form-label">Je Naam</label>
            <input
                type="text"
                class="form-control"
                id="name"
                v-model="name"
                required
                placeholder="Vul je naam in"
            />
          </div>

          <!-- Email Field -->
          <div class="mb-3">
            <label for="email" class="form-label">Je E-mail</label>
            <input
                type="email"
                class="form-control"
                id="email"
                v-model="email"
                required
                placeholder="Vul je e-mail in"
            />
          </div>

          <!-- Subscribe Button -->
          <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary">Inschrijven</button>
          </div>
        </form>

        <!-- Success Message -->
        <div v-if="successMessage" class="alert alert-success mt-3" role="alert">
          {{ successMessage }}
        </div>

        <!-- Error Message -->
        <div v-if="errorMessage" class="alert alert-danger mt-3" role="alert">
          {{ errorMessage }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  margin-top: 3rem;
}

h2 {
  font-size: 2rem;
  font-weight: bold;
}

p {
  font-size: 1.1rem;
  color: #6c757d;
}

.form-label {
  font-weight: 600;
}

.btn-primary {
  background-color: #24786E99;
  border-color: #24786E99;
}

.btn-primary:hover {
  background-color: #24786E99;
  border-color: #24786E99;
}
</style>
