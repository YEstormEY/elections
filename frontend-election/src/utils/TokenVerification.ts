import router from "@/router/router";
import {authState} from "@/utils/auth";
import {deleteCookie} from "@/utils/cookie/DeleteCookie";

class TokenVerification {
  private static instance: TokenVerification;
  private token: string | null;
  private isLoggingOut: boolean = false; // Flag to indicate if logout is in progress

  constructor() {
    if (!TokenVerification.instance) {
      this.token = this.getCookie('token'); // Initialize token from cookies
      this.setToken(this.token); // Set the token in the singleton instance
      TokenVerification.instance = this;
    }
    return TokenVerification.instance;
  }

  // Helper method to set a cookie
  private setCookie(name: string, value: string, days: number) {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    document.cookie = `${name}=${value}; expires=${date.toUTCString()}; path=/`;
  }

  // Helper method to get a cookie value
  private getCookie(name: string): string | null {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
      return parts.pop()?.split(';').shift() || null;
    }
    return null;
  }

  // Method to set or remove the token in both cookies and the singleton instance
  setToken(token: string | null) {
    if (token === null) {
      const removeCookie = deleteCookie;
      removeCookie('token');
      this.token = null;
    } else {
      this.setCookie('token', token, 7); // Set cookie with a 7-day expiry
      this.token = token;
    }
  }

  // Method to get the token, either from memory or cookies
  getToken() {
    return this.token || this.getCookie('token');
  }

  // Method to verify the token via an API call
  public async verifyToken(): Promise<boolean> {
    if (this.isLoggingOut) {
      console.log('Logout in progress, skipping token verification');
      return false;
    }

    this.setToken(this.getToken()); // Set the token in the singleton instance
    try {
      const token = this.getToken();
      if (!token) {
        return false;
      } // If no token, return false

      const response = await fetch('http://localhost:8080/verifytoken', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${token}`,
        },
      });

      if (response.ok) {
        console.log('Token verified');
        const userId = await response.json(); // Get the userId from the response
        this.setCookie('userId', userId, 7); // Store the userId in cookies with a 7-day expiry
        return true;
      } else {
        console.error('Failed to verify token:', response.statusText);
        const removeCookie = deleteCookie;
        removeCookie('token');
        removeCookie('userId');
        return false;
      }
    } catch (error) {
      console.error('Failed to verify token:', error);
      this.removeCookie('token');
      this.removeCookie('userId');
      return false;
    }
  }

  // Method to log out and clear the token and user data
  public async logout() {
    this.isLoggingOut = true; // Set the flag to indicate a logout is in progress
    const removeCookie = deleteCookie;
    removeCookie('token');
    removeCookie('userId');
    this.token = null;

    // Optionally, update any global state here, e.g., authState
    console.log('Logged out successfully');

    authState.isLoggedIn = false;
    authState.userName = '';


    await router.push("/");

    this.isLoggingOut = false; // Reset the flag after logout is complete
  }
}

export default TokenVerification;
