import axios from "@/js/config/axios";

export async function getAllScience() {
    try {
        const response = await axios.get('/api/science/all');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to get all sciences: ${error}`);
    }
}