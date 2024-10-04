import axios from "@/js/config/axios";

export async function getAllLocation() {
    try {
        const response = await axios.get('/api/location/all');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to get all location: ${error}`);
    }
}