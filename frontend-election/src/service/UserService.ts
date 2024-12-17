import router from '@/router/router';
import { getCookie } from '@/utils/cookie/GetCookie';
import TokenVerification from '@/utils/TokenVerification';

/**
 * Fetches the user data from the backend
 */
export async function fetchUser() {
  const token = getCookie('token');
  try {
    const response = await fetch('http://localhost:8080/auth/protected/user', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `${token}`,
      },
    });

    if (!response.ok) {
      console.error('Failed to fetch user');
      return;
    } else {
      return await response.json();
    }
  } catch (error) {
    if (error instanceof Error) {
      throw new Error(error.message);
    } else {
      throw new Error('An unknown error occurred');
    }
  }
}

/**
 * Deletes the user from the backend
 */
export async function deleteUser() {
  const token = getCookie('token');
  try {
    const response = await fetch('http://localhost:8080/auth/protected/delete/user', {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `${token}`,
      },
    });

    if (response.ok) {
      const tokenVerification = new TokenVerification();
      await tokenVerification.logout();

      alert('User deleted successfully');
      await router.push('/');
    } else {
      const errorMessage = await response.text();
      console.error('Failed to delete user:', errorMessage);
      alert('Failed to delete user: ' + errorMessage);
    }
  } catch (error) {
    console.error('Error during user deletion:', error);
    alert('Error during user deletion: ' + (error as Error).message);
  }
}

/**
 * Saves the changes made to the user
 * @param user_id - The ID of the user
 * @param username - The username of the user
 * @param email - The email address of the user
 */
export async function saveChanges(user_id: number, username: string, email: string) {
  const token = getCookie('token');

  if (username === '' || email === '') {
    alert('Please fill in all fields');
    return;
  }

  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailPattern.test(email)) {
    alert('Please enter a valid email address');
    return;
  }

  try {
    const response = await fetch('http://localhost:8080/auth/protected/update/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `${token}`,
      },
      body: JSON.stringify({
        userId: user_id,
        username: username,
        email: email,
      }),
    });

    if (!response.ok) {
      console.error('Failed to edit user');
      return;
    } else {
      alert('User edited successfully');
      return await response.json();
    }
  } catch (error) {
    if (error instanceof Error) {
      console.error('Error during user edit:', error);
    } else {
      throw new Error('An unknown error occurred');
    }
  }
}

/**
 * Updates the password of the user
 * @param user_id - The ID of the user
 * @param currentPassword - The current password of the user
 * @param newPassword - The new password of the user
 * @param confirmPassword - Confirmation of the new password
 */
export async function updatePassword(
    user_id: number,
    currentPassword: string,
    newPassword: string,
    confirmPassword: string
) {
  const token = getCookie('token');

  if (!token) {
    alert('Please login first');
    await router.push('/login');
    return;
  }

  if (currentPassword === '' || newPassword === '' || confirmPassword === '') {
    alert('Please fill in all fields');
    return;
  }

  if (newPassword !== confirmPassword) {
    alert('Passwords do not match');
    return;
  }

  if (newPassword.length < 8) {
    alert('Password must be at least 8 characters long');
    return;
  }

  try {
    const response = await fetch('http://localhost:8080/auth/protected/update/password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `${token}`,
      },
      body: JSON.stringify({
        userId: user_id,
        password: currentPassword,
        newPassword: newPassword,
      }),
    });

    if (response.ok) {
      alert('Password updated successfully, login again with new password');
      const tokenVerification = new TokenVerification();
      await tokenVerification.logout();
      return await response.json();
    } else {
      const errorMessage = await response.text();
      alert(errorMessage);
      return;
    }
  } catch (error) {
    if (error instanceof Error) {
      console.error('Error during password update:', error);
    } else {
      throw new Error('An unknown error occurred');
    }
  }
}
