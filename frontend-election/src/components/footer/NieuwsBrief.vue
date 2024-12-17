<script lang="ts">
import { ref } from "vue";

export default {
  setup() {
    const name = ref("");
    const email = ref("");
    const successMessage = ref("");
    const errorMessage = ref("");

    const subscribeToNewsletter = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/newsletter/subscribe", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ name: name.value, email: email.value }),
        });

        if (!response.ok) {
          throw new Error("Failed to subscribe. Probeer het later opnieuw.");
        }

        successMessage.value = "Je bent succesvol aangemeld voor de nieuwsbrief!";
        errorMessage.value = "";
        name.value = "";
        email.value = "";
      } catch (error) {
        if (error instanceof Error) {
          throw new Error(error.message);
        } else {
          throw new Error('An unknown error occurred');
        }
      }
    };

    return { name, email, successMessage, errorMessage, subscribeToNewsletter };
  },
};
</script>

<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">Meld je aan voor onze nieuwsbrief!</h2>
    <p class="text-center mb-4">
      Blijf op de hoogte van de laatste updates en het nieuws over politiek.
    </p>
    <div class="row justify-content-center">
      <div class="col-md-6">
        <form @submit.prevent="subscribeToNewsletter">
          <!-- Name Field -->
          <div class="mb-3">
            <label for="name" class="form-label">Naam</label>
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
            <label for="email" class="form-label">E-mailadres</label>
            <input
                type="email"
                class="form-control"
                id="email"
                v-model="email"
                required
                placeholder="Vul je e-mailadres in"
            />
          </div>

          <!-- Submit Button -->
          <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary">Meld je aan</button>
          </div>
        </form>

        <!-- Success and Error Messages -->
        <p v-if="successMessage" class="text-success mt-3">{{ successMessage }}</p>
        <p v-if="errorMessage" class="text-danger mt-3">{{ errorMessage }}</p>
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

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
