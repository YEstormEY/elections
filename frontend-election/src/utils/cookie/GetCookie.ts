export function getCookie(name: string): string | null {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);

    if (parts.length === 2) {
        const cookiePart = parts.pop();
        if (cookiePart !== undefined) {
            return cookiePart.split(';').shift() || null;
        }
    }

    return null;
}