<script setup lang="ts">
import { ref } from 'vue'
import Login from '@/components/Login.vue'

defineProps<{
  msg: string;
}>()

const test = ref<string | null>(null) // Reactive variable to hold the fetched text

// Fetch data function
const fetchData = async () => {
  try {
    // Update the URL to use HTTP instead of HTTPS if your backend is not configured for HTTPS
    const response = await fetch('http://localhost:8080/api/v1/test', {
      method: 'GET'
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    // Use response.text() since the backend returns a plain string
    const data = await response.json()
    test.value = data.message // Assign the plain string response to 'test'
    console.log(data) // Log the entire response for debugging
  } catch (err) {
    console.error(err)
  }
}
</script>

<script lang="ts">
export default {
  methods: {
    goToLogin() {
      this.$router.push('/login')
    },
  },
}
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
  </div>

  <div>
    <button @click="goToLogin">login</button>
    <button @click="fetchData">Fetch Data</button>
    <p>{{ test }}</p> <!-- Correctly reference the 'test' variable -->
  </div>
  <div>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

p {
  font-size: 1.2rem;
  color: #181818;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>