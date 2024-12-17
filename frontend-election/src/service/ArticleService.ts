import router from "@/router/router";
import {getCookie} from "@/utils/cookie/GetCookie";

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
        throw new Error(error.message);
    }
}

export async function fetchArticleById(articleId) {
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
        throw new Error(error.message);
    }
}

export async function getArticleLikes(articleId) {
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
        throw new Error(error.message);
    }
}

export async function likeArticle(articleId) {
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/article/like/${articleId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
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
        throw new Error((error as Error).message);
    }
}

export async function checkIfLiked(articleId) {
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/article/checklike/${articleId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
        });
        if (response.ok) {
            return await response.json();
        }else {
            return false;
        }
    } catch (error) {
        throw new Error(error.message);
    }
}

export async function unlikeArticle(articleId) {
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/article/unlike/${articleId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
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
        throw new Error((error as Error).message);
    }
}
