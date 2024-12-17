import { reactive } from 'vue';

export const authState = reactive({
    isLoggedIn: false,
    userName: '',
});

// You can also export functions to update this state
export function login(userName: string) {
    authState.isLoggedIn = true;
    authState.userName = userName;
}

export function logout() {
    console.log('Logging out...');
    // Helper function to remove a cookie
    function removeCookie(name) {
        document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/`;
    }

    console.log('Before logout:', document.cookie);
    removeCookie('token', '/');
    removeCookie('userId', '/');
    console.log('After logout:', document.cookie);

    // Update the authentication state
    alert('You have been logged out');


    authState.isLoggedIn = false;
    authState.userName = '';

    // Clear any other necessary state or data
}

