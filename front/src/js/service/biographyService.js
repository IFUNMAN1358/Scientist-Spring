import {getCookie} from "@/js/utils/cookie";
import router from "@/js/config/router";
import axios from "@/js/config/axios";

export async function handleCreateBiography(biographyForm) {
    try {
        const accessToken = getCookie('accessToken');
        await axios.post('/api/biography', biographyForm, {
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'multipart/form-data'
            }
        });
        await router.go(0);
    } catch (error) {
        console.error('Create biography failed:', error);
    }
}

export async function handleGetUserBiographyByUserId(userId) {
    try {
        const response = await axios.get(`/api/biography/${userId}`);
        return response.data;
    } catch (error) {
        console.error('Get user biography by userId failed:', error);
    }
}

export async function handleGetAllUserBiographyByFio(fio) {
    try {
        const response = await axios.get(`/api/biography/all/search/${fio}`);
        return response.data;
    } catch (error) {
        console.error('Get all user biography by fio failed:', error);
    }
}

export async function handleGetSelfUserBiography() {
    try {
        const accessToken = getCookie('accessToken');
        const response = await axios.get('/api/biography', {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        return response.data;
    } catch (error) {
        console.error('Get user biography failed:', error);
    }
}

export async function handleGetAllBiographyByScienceId(scienceId) {
    try {
        const response = await axios.get(`/api/biography/all/science/${scienceId}`);
        return response.data;
    } catch (error) {
        console.error('Get all biographies by scienceId failed:', error);
    }
}

export async function handleGetAllBiographyByLocationId(locationId) {
    try {
        const response = await axios.get(`/api/biography/all/location/${locationId}`);
        return response.data;
    } catch (error) {
        console.error('Get all biographies by locationId failed:', error);
    }
}

export async function handleGetAllUnconfirmedBiography() {
    try {
        const accessToken = getCookie('accessToken');
        const response = await axios.get("/api/biography/all/unconfirmed", {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        return response.data;
    } catch (error) {
        console.error('Get all unconfirmed biographies failed:', error);
    }
}

export async function handleGetAllLocationCount() {
    try {
        const response = await axios.get("/api/biography/all/location");
        return response.data;
    } catch (error) {
        console.error('Get all location count failed:', error);
    }
}

export async function handleGetAllScienceCount() {
    try {
        const response = await axios.get("/api/biography/all/science");
        return response.data;
    } catch (error) {
        console.error('Get all science count failed:', error);
    }
}

export async function handleUpdateBiography(biographyForm) {
    try {
        const accessToken = getCookie('accessToken');
        await axios.put('/api/biography', biographyForm, {
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'multipart/form-data'
            }
        });
        await router.go(0);
    } catch (error) {
        console.error('Update biography failed:', error);
    }
}

export async function handleSetBiographyConfirm(userId) {
    try {
        const accessToken = getCookie('accessToken');
        await axios.patch(`/api/biography/${userId}/confirm`, null, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        await router.go(0);
    } catch (error) {
        console.error('Set confirm biography failed:', error);
    }
}

export async function handleDeleteSelfBiography() {
    try {
        const accessToken = getCookie('accessToken');
        await axios.delete(`/api/biography`, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        await router.go(0);
    } catch (error) {
        console.error('Delete self biography failed:', error);
    }
}

export async function handleDeleteBiographyByUserId(userId) {
    try {
        const accessToken = getCookie('accessToken');
        await axios.delete(`/api/biography/${userId}`, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        await router.go(0);
    } catch (error) {
        console.error('Delete biography by userId failed:', error);
    }
}

export async function handleGetBiographyImage(imageName) {
  try {
    const response = await axios.get(`/api/biography/image/${imageName}`, {
      responseType: 'blob'
    });
    return URL.createObjectURL(response.data);
  } catch (error) {
    return null;
  }
}