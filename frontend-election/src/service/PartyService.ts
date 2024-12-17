export async function getAllParties() {
    try {
        const response = await fetch('http://localhost:8080/api/parties', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',

            },
        });

        if (!response.ok) {
            console.log('Fetched Parties:', response); // Log the fetched data
            console.error('Failed to fetch parties');
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


