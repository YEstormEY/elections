import { getCookie } from "@/utils/cookie/GetCookie";

/**
 * Function to fetch all comments for a specific article
 * @param articleId - the id of the article
 */
export async function fetchCommentsByArticleId(articleId: number) {
    try {
        const response = await fetch(`http://localhost:8080/comments/get/${articleId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error('Failed to fetch comments');
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
 * Function to post a new comment
 * @param articleId
 * @param newComment
 */
export async function postComment(articleId: number, newComment: string) {
    // Get the token from the cookie
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/comments/post`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Add the token to the headers
                'Authorization': `${token}`,
            },
            body: JSON.stringify({
                // the articleId where the comment should be posted
                articleId: articleId,
                // the content of the comment
                content: newComment,
            }),
        });

        const responseText = await response.text();

        if (!response.ok) {
            const errorMessage = await JSON.parse(responseText);
            console.error('Failed to create user:', errorMessage);
        } else {
            alert('Comment posted successfully');
            return responseText;
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
 * Function to delete a comment
 * @param commentId - the id of the comment
 */
export async function deleteComment(commentId: number) {
    // Get the token from the cookie
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/comments/delete/${commentId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                // Add the token to the headers
                'Authorization': `${token}`,
            },
            body: JSON.stringify({
                // the commentId of the comment that should be deleted
                commentId: commentId,
            }),
        });

        if (!response.ok) {
            console.error('Failed to delete comment');
            return;
        } else {
            alert('Comment deleted successfully');
            return await response.json(); // Fix: Add missing parentheses
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
 * Function to edit a comment
 * @param commentId - the id of the comment
 * @param editedComment - the edited content of the comment
 */
export async function editComment(commentId: number, editedComment: string) {
    // Get the token from the cookie
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/comments/update/${commentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Add the token to the header
                'Authorization': `${token}`,
            },
            body: JSON.stringify({
                // the edited content of the comment
                content: editedComment,
            }),
        });

        if (!response.ok) {
            console.error('Failed to edit comment');
            return;
        } else {
            alert('Comment edited successfully');
            return await response.json(); // Fix: Add missing parentheses
        }
    } catch (error) {
        if (error instanceof Error) {
            throw new Error(error.message);
        } else {
            throw new Error('An unknown error occurred');
        }
    }
}


