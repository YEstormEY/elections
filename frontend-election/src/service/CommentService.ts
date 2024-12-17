import { getCookie } from "@/utils/cookie/GetCookie";

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
        throw new Error((error as Error).message);
    }
}

export async function postComment(articleId: number, newComment: string) {
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/comments/post`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
            body: JSON.stringify({
                articleId: articleId,
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
        throw new Error((error as Error).message);
    }
}

export async function deleteComment(commentId){
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/comments/delete/${commentId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
            body: JSON.stringify({
                commentId: commentId,
            }),
        });

        if (!response.ok) {
            console.error('Failed to delete comment');
            return;
        } else{
            alert('Comment deleted successfully');
            return await response.json
        }

    }
    catch (error) {
        throw new Error((error as Error).message);
    }
}

export async function editComment(commentId, editedComment){
    const token = getCookie('token');

    try {
        const response = await fetch(`http://localhost:8080/comments/update/${commentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${token}`,
            },
            body: JSON.stringify({
                content: editedComment,
            }),
        })
        if (!response.ok) {
            console.error('Failed to edit comment');
            return;
        }
        else{
            alert('Comment edited successfully');
            return await response.json
        }
    } catch (error) {
        throw new Error((error as Error).message);
    }
}

