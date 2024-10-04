import router from "@/js/config/router";
import {getCookie} from "@/js/utils/cookie";
import axios from "@/js/config/axios";

export async function handleChangePassword(passwordForm) {
    try {
        const accessToken = getCookie('accessToken');
        await axios.post('/api/user/password', passwordForm, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });
        await router.push({ name: 'Profile' });
    } catch (error) {
        console.error('Update password failed:', error);
    }
}