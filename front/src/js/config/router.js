import { createRouter, createWebHistory } from 'vue-router';
import store from "@/js/config/store";
import RegisterComponent from "@/components/auth/RegisterComponent.vue";
import LoginComponent from "@/components/auth/LoginComponent.vue";
import SearchComponent from "@/components/service/SearchComponent.vue";
import ScienceComponent from "@/components/service/ScienceComponent.vue";
import ProfileComponent from "@/components/profile/ProfileComponent.vue";
import AccessDeniedComponent from "@/components/core/AccessDeniedComponent.vue";
import PasswordComponent from "@/components/profile/PasswordComponent.vue";
import SelfBiographyComponent from "@/components/profile/SelfBiographyComponent.vue";
import ConfirmBiographyComponent from "@/components/manager/ConfirmBiographyComponent.vue";
import LocationComponent from "@/components/service/LocationComponent.vue";
import BiographyListComponent from "@/components/service/BiographyListComponent.vue";
import ShowBiographyComponent from "@/components/service/ShowBiographyComponent.vue";

const routes = [

    {path: '/', name: "Search", component: SearchComponent},
    {path: '/location', name: "Location", component: LocationComponent},
    {path: '/science', name: "Science", component: ScienceComponent},
    {path: '/:type/:id', name: "BiographyList", component: BiographyListComponent},
    {path: '/biography/:id', name: "ShowBiography", component: ShowBiographyComponent},

    {path: '/auth/registration', name: "Register", component: RegisterComponent},
    {path: '/auth/login', name: "Login", component: LoginComponent},

    {path: '/profile', name: 'Profile', component: ProfileComponent, meta: { requiresRole: 'ROLE_USER' }},
    {path: '/profile/biography', name: 'SelfBiography', component: SelfBiographyComponent, meta: { requiresRole: 'ROLE_USER' }},
    {path: '/profile/password', name: 'Password', component: PasswordComponent, meta: { requiresRole: 'ROLE_USER' }},

    {path: '/manager/confirm-biography', name: 'ConfirmBiography', component: ConfirmBiographyComponent, meta: { requiresRole: 'ROLE_MANAGER' }},

    {path: '/access-denied', name: 'AccessDenied', component: AccessDeniedComponent},

];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const requiresRole = to.meta.requiresRole;

  if (store.state.accessToken && store.actions.isTokenExpired(store.state.accessToken)) {
    store.actions.logout();
  }

  if (requiresRole) {
    const hasRequiredRole = store.getters.hasRole(requiresRole).value;

    if (hasRequiredRole) {
      next();
    } else {
      next({ name: 'AccessDenied' });
    }
  } else {
    next();
  }
});

export default router;