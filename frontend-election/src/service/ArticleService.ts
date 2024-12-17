import router from "@/router/router";
import {getCookie} from "@/utils/cookie/GetCookie";

/**
 * Function used to fetch all articles from the backend
 */
export async function fetchArticles() {
    try {
        const response = await fetch('http://localhost:8080/article/getall', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error('Failed to fetch articles');
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
 * Function used to fetch an article by its id
 * @param articleId - id connected to a specific article
 */
export async function fetchArticleById(articleId: number) {
    try {
        const response = await fetch(`http://localhost:8080/article/get/${articleId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error('Failed to fetch article');
            alert('Article not found');
            router.push('/articlepage');

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
 * Function used to check likes on an article
 * @param articleId - id connected to a specific article
 */

export async function getArticleLikes(articleId: number) {
    try {
        const response = await fetch(`http://localhost:8080/article/getlikes/${articleId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error('Failed to fetch article likes');
            return;
        } else
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
 * Function used to like an article
 * @param articleId - id connected to a specific article
 */
export async function likeArticle(articleId: number) {
    // Get the token from the cookie
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/article/like/${articleId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Add the token to the header
                'Authorization': `${token}`,
            },
        });

        const responseText = await response.text();

        if (!response.ok) {
            const errorMessage = await JSON.parse(responseText);
            console.error('Failed to like article:', errorMessage);

            return;
        } else
            return responseText;
    } catch (error) {
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error('An unknown error occurred');
        }
    }
}

/**
 * Function used to check if an article is liked by the user
 * @param articleId - id connected to a specific article
 */
export async function checkIfLiked(articleId: number) {
    // Get the token from the cookie
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/article/checklike/${articleId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // Add the token to the header
                'Authorization': `${token}`,
            },
        });
        if (response.ok) {
            return await response.json();
        }else {
            return false;
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
 * Function used to unlike an article
 * @param articleId - id connected to a specific article
 */
export async function unlikeArticle(articleId: number) {
    // Get the token from the cookie
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/article/unlike/${articleId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Add the token to the header
                'Authorization': `${token}`,
            },
        });

        const responseText = await response.text();

        if (!response.ok) {
            const errorMessage = await JSON.parse(responseText);
            console.error('Failed to unlike article:', errorMessage);

            return;
        } else
            return responseText;
    } catch (error) {
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error('An unknown error occurred');
        }
    }
}

/**
 * Function used to fetch articles by category
 * @param categoryId
 */
export async function fetchArticlesByCategory(categoryId: number) {
    try {
        const response = await fetch(`http://localhost:8080/article/getbycategory/${categoryId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error('Failed to fetch articles');
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
 * Function used to fetch articles by search
 * @param search
 */
export async function fetchArticlesBySearch(search: string) {
    if (search === '' || search.trim() === '') {
        console.error('Search field is empty');
        return;
    }
    try {
        const response = await fetch(`http://localhost:8080/article/search/${search}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error('Failed to fetch articles');
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
