<script lang="ts">
import { ref } from 'vue';

export default {
  setup() {
    const name = ref('');
    const email = ref('');
    const message = ref('');

    const submitForm = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/contact/submit', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            name: name.value,
            email: email.value,
            message: message.value,
          }),
        });

        if (!response.ok) {
          throw new Error('Failed to submit contact form');
        }

        const result = await response.json();
        console.log('Form successfully submitted:', result);

        // Clear form fields after submission
        name.value = '';
        email.value = '';
        message.value = '';
      } catch (error) {
        console.error(error);
      }
    };

    return {
      name,
      email,
      message,
      submitForm,
    };
  },
};
</script>

<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">Contact Us</h2>
    <div class="row justify-content-center">
      <div class="col-md-6">
        <form @submit.prevent="submitForm">
          <!-- Name Field -->
          <div class="mb-3">
            <label for="name" class="form-label">Your Name</label>
            <input
              type="text"
              class="form-control"
              id="name"
              v-model="name"
              required
              placeholder="Enter your name"
            />
          </div>

          <!-- Email Field -->
          <div class="mb-3">
            <label for="email" class="form-label">Your Email</label>
            <input
              type="email"
              class="form-control"
              id="email"
              v-model="email"
              required
              placeholder="Enter your email"
            />
          </div>

          <!-- Message Field -->
          <div class="mb-3">
            <label for="message" class="form-label">Your Message</label>
            <textarea
              class="form-control"
              id="message"
              rows="5"
              v-model="message"
              required
              placeholder="Enter your message"
            ></textarea>
          </div>

          <!-- Submit Button -->
          <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary">Send Message</button>
          </div>
        </form>
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
</style>
