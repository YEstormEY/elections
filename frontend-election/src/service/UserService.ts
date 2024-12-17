import router from '@/router/router'
import {getCookie} from "@/utils/cookie/GetCookie";
import TokenVerification from "@/utils/TokenVerification";

export async function fetchUser() {
const token = getCookie("token");
  try {
    const response = await fetch('http://localhost:8080/auth/protected/user', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${token}`
      }
    })

    if (!response.ok) {
      console.error('Failed to fetch user')
      return
    } else {
      return await response.json()
    }
  } catch (error) {
    throw new Error(error.message)
  }
}

export async function deleteUser() {
    const token = getCookie('token');

    try {
        const response = await fetch('http://localhost:8080/auth/protected/delete/user', {
            method: 'DELETE', // It's better to use 'DELETE' method for delete operations
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`
            }
        });

        if (response.ok) {
            // Use TokenVerification's logout method
            const tokenVerification = new TokenVerification();
            await tokenVerification.logout(); // Make sure to wait for the logout to complete

            alert('User deleted successfully');
            await router.push('/'); // Redirect to the homepage
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

export async function saveChanges(user_id, username, email) {
const token = getCookie("token");

  console.log(user_id, username, email)

  if (username === '' || email === '') {
    alert('Please fill in all fields')
    return
  }

  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailPattern.test(email)) {
    alert('Please enter a valid email address')
    return
  }

  try {
    const response = await fetch('http://localhost:8080/auth/protected/update/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'authorization': `${token}`
      },
      body: JSON.stringify({
        userId: user_id,
        username: username,
        email: email
      })
    })

    if (!response.ok) {
      console.error('Failed to edit user')
      return
    } else {

      alert('User edited successfully')

      return await response.json()
    }


  } catch (error) {
    console.error('Error during user edit:', error)
  }
}

export async function updatePassword(user_id, currentPassword, newPassword, confirmPassword) {
const token = getCookie("token");

  console.log( currentPassword, newPassword, confirmPassword)

  if (!token) {
    alert('Please login first')
    await router.push('/login')
    return
  }

  if (currentPassword === '' || newPassword === '' || confirmPassword === '') {
    alert('Please fill in all fields')
    return
  }

  if (newPassword !== confirmPassword) {
    alert('Passwords do not match')
    return
  }

  if (newPassword.length < 8) {
    alert('Password must be at least 8 characters long')
    return
  }

  try {
    const response = await fetch('http://localhost:8080/auth/protected/update/password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${token}`
      },
      body: JSON.stringify({
        userId: user_id,
        password: currentPassword,
        newPassword: newPassword
      })
    })
    if (response.ok) {
      alert('Password updated successfully, login again with new password')
        const tokenVerification = new TokenVerification();
        await tokenVerification.logout(); // Make sure to wait for the logout to complete
      return await response.json()

    } else {
      const errorMessage = await response.text() // Get the error message from the backend
      alert(errorMessage) // Display the error message
      return
    }
  } catch (error) {
    console.error('Error during password update:', error)
  }
}
