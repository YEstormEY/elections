import {getCookie} from "@/utils/cookie/GetCookie";

export async function createArticle(newArticle) {

    if (newArticle.title === '' || newArticle.summary === '' || newArticle.content === '') {
        alert("Please fill in all fields");
        return;
    }

    const token = getCookie("token");


    try {
        const response = await fetch('http://localhost:8080/article/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
            body: JSON.stringify(newArticle),
        });

        if (response.ok) {
            alert('Article created successfully');
        } else {
            const errorMessage = await response.text();
            console.error('Failed to create article:', errorMessage);
        }
    } catch (error) {
        console.error('Error during article creation:', error);
    }
}

export async function getCategories() {
    const token = getCookie("token");
    try {
        const response = await fetch('http://localhost:8080/article/category/getall', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
        });

        if (!response.ok) {
            console.error('Failed to fetch categories');
            return;
        }

        return await response.json();
    } catch (error) {
        throw new Error(error.message);
    }

}

export async function createUser(newUser) {
    if (newUser.username === '' || newUser.password === '' || newUser.role === '') {
        alert("Please fill in all fields");
        return;
    }
    if (newUser.password !== newUser.confirmPassword) {
        alert("Passwords do not match");
        return;
    }

    const token = getCookie("token");

    try {
        const response = await fetch('http://localhost:8080/admin/create/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
            body: JSON.stringify(newUser),
        });

        if (response.ok) {
            alert('User created successfully');
        } else {
            const errorMessage = await response.text();
            console.error('Failed to create user:', errorMessage);
        }
    } catch (error) {
        console.error('Error during user creation:', error);
    }
}

export async function deleteUserAdmin(userId) {
    const token = getCookie("token");

    try {
        const response = await fetch(`http://localhost:8080/admin/delete/user`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
            body: JSON.stringify({userId}),
        });

        if (response.ok) {
            alert('User deleted');

            console.log('User deleted successfully');
        } else {
            const errorMessage = await response.text();
            console.error('Failed to delete user:', errorMessage);
        }
    } catch (error) {
        console.error('Error during user deletion:', error);
    }

}

export async function saveUserChanges(editedUser) {
    const token = getCookie("token");

    if (editedUser.username === '' || editedUser.email === '' || editedUser.role === '') {
        alert("Please fill in all fields");
        return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(editedUser.email)) {
        alert("Please enter a valid email address");
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/admin/update/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
            body: JSON.stringify(editedUser),
        });

        if (!response.ok) {
            const errorMessage = await response.text(); // Get the error message from the backend
            alert(errorMessage); // Display the error message
            return;
        } else {
            alert('User updated successfully');
        }
    } catch (error) {
        console.error('Error during user update:', error);
    }
}

export async function fetchAllUsers() {
    const token = getCookie("token");

    try {
        const response = await fetch('http://localhost:8080/admin/allusers', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
        });

        if (response.ok) {
            return await response.json();
        } else {
            console.error('Failed to fetch all users');
        }
    } catch (error) {
        console.error('Error during user fetch:', error);
    }
}