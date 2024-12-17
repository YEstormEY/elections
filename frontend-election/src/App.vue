<script lang="ts">
import { onMounted, provide } from 'vue';
import NavigationBar from '@/components/NavigationBar.vue'; // Ensure correct path
import Footer from '@/components/footer/Footer.vue'; // Ensure correct path
import TokenVerification from '@/utils/TokenVerification'; // Corrected the path
import { useRouter } from 'vue-router';

export default {
  setup() {
    // Create a singleton instance of TokenVerification
    const tokenServiceInstance = new TokenVerification();
    const router = useRouter();

    // Provide the TokenVerification instance globally to child components
    provide('TokenVerification', tokenServiceInstance);

    const checkToken = async () => {
      const isValid = await tokenServiceInstance.verifyToken();
      console.log('Token is valid:', isValid);
    };

    // Logout function to clear the token and redirect the user
    const logout = () => {
      tokenServiceInstance.logout(); // Call the logout function
      router.push('/login'); // Redirect to the login page after logout
      console.log('User has been logged out');
    };

    onMounted(() => {
      checkToken();
    });

    return {
      checkToken,
      logout,
    };
  },

  components: {
    NavigationBar,
    Footer,
  },
};
</script>

<template>
  <header>
    <NavigationBar />
  </header>

  <main>
    <RouterView />
    <Footer />
  </main>
</template>

<style scoped>
/* Add your styles here */
button {
  margin: 10px;
  padding: 8px 12px;
  background-color: #f56c6c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #d95454;
}
</style>
