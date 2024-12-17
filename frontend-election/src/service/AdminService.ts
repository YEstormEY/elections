import {getCookie} from "@/utils/cookie/GetCookie";

interface Article {
    articleId: number; // Changed to match your selectedArticle structure
    title: string;
    content: string;
    summary: string;
    imagePath: string;
    categoryId: number[]; // Array of category IDs
}



interface User {
    username: string;
    password: string;
    confirmPassword: string;
    role: string;
}

interface EditedUser {
    username: string;
    email: string;
    role: string;
}

interface NewArticle {
    title: string;
    summary: string;
    content: string;
    imagePath: string;
    categoryId: number[];
}


/**
 * Function to create a new article
 * @param newArticle - the article to be created
 */
export async function createArticle(article: NewArticle) {
    const token = getCookie("token");
    try {
        const response = await fetch("http://localhost:8080/article/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `${token}`,
            },
            body: JSON.stringify(article),
        });

        if (response.ok) {
            alert("Article created successfully");
        } else {
            const errorMessage = await response.text();
            console.error("Failed to create article:", errorMessage);
        }
    } catch (error) {
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error("An unknown error occurred");
        }
    }
}


/**
 * Gets all the categories from the backend
 */
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
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error('An unknown error occurred');
        }
    }

}

/**
 * function used to create a new user.
 * @param newUser
 */
export async function createUser(newUser: User) {
    if (!newUser.username || !newUser.password || !newUser.role) {
        alert("Please fill in all fields");
        return;
    }
    if (newUser.password !== newUser.confirmPassword) {
        alert("Passwords do not match");
        return;
    }

    const token = getCookie("token");

    try {
        const response = await fetch("http://localhost:8080/admin/create/user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `${token}`,
            },
            body: JSON.stringify(newUser),
        });

        if (response.ok) {
            alert("User created successfully");
        } else {
            const errorMessage = await response.text();
            console.error("Failed to create user:", errorMessage);
        }
    } catch (error) {
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error("An unknown error occurred");
        }
    }
}

/**
 * Function to delete a user
 * @param userId - used to delete a specific user
 */
export async function deleteUserAdmin(userId: number) {
    // gets token from cookie
    const token = getCookie("token");

    try {
        const response = await fetch(`http://localhost:8080/admin/delete/user`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // adds token to the header
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
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error('An unknown error occurred');
        }
    }

}

/**
 * used to send the edited user to the backend
 * @param editedUser
 */
export async function saveUserChanges(editedUser: EditedUser) {
    const token = getCookie("token");

    if (!editedUser.username || !editedUser.email || !editedUser.role) {
        alert("Please fill in all fields");
        return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(editedUser.email)) {
        alert("Please enter a valid email address");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/admin/update/user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `${token}`,
            },
            body: JSON.stringify(editedUser),
        });

        if (response.ok) {
            alert("User updated successfully");
        } else {
            const errorMessage = await response.text();
            console.error("Failed to update user:", errorMessage);
        }
    } catch (error) {
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error("An unknown error occurred");
        }
    }
}

/**
 * Function used to fetch all users from the backend
 *
 */

export async function fetchAllUsers() {
    //gets token from cookie
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
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error('An unknown error occurred');
        }
    }
}

    /**
     * Function used to fetch all articles from the backend
     */
    export async function fetchAllArticles() {

        try {
            const response = await fetch('http://localhost:8080/article/getall', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                return await response.json();
            } else {
                console.error('Failed to fetch all articles');
            }
        } catch (error) {
            if (error instanceof Error) {
                throw new Error(error.message);
            } else {
                throw new Error('An unknown error occurred');
            }
        }
    }

export async function editArticle(article: Article) {
    const token = getCookie("token");
    try {
        const response = await fetch("http://localhost:8080/admin/article/update", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `${token}`,
            },
            body: JSON.stringify(article),
        });

        if (response.ok) {
            alert("Article updated successfully");
        } else {
            const errorMessage = await response.text();
            console.error("Failed to update article:", errorMessage);
        }
    } catch (error) {
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error("An unknown error occurred");
        }
    }
}



    export async function deleteArticle( articleId: number) {
        const token = getCookie("token");

        try {
            const response = await fetch('http://localhost:8080/admin/article/delete/' + articleId, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `${token}`,
                },
            });

            if (response.ok) {
                alert('Article deleted successfully');
            } else {
                const errorMessage = await response.text();
                console.error('Failed to delete article:', errorMessage);
            }

        } catch (error) {
            if (error instanceof Error) {
                throw new Error(error.message);
            } else {
                throw new Error('An unknown error occurred');
            }
        }
    }

    export async function searchUser(search: string) {
        const token = getCookie("token");
        try {
            const response = await fetch('http://localhost:8080/admin/search/user/' + search, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `${token}`,
                },
            });

            if (response.ok) {
                return await response.json();
            } else {
                console.error('Failed to search for user');
            }
        } catch (error) {
            if (error instanceof Error) {
                throw new Error(error.message);
            } else {
                throw new Error('An unknown error occurred');
            }
        }
    }



