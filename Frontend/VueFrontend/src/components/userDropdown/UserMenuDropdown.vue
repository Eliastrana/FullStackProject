<template>
  <div class="user-menu-dropdown" @mouseleave="hideDropdown" @mouseenter="showDropdown">
    <RouterLink :to="'/MyAccount'" class="username-link" :class="{ 'active-link': isRouteActive }">
      {{ props.userName }}
    </RouterLink>
    <div v-if="isDropdownVisible" class="dropdown-content">
      <RouterLink to="/Admin">Admin</RouterLink>
      <RouterLink to="/Contact">Contact</RouterLink>
      <a href="#" @click.prevent="logout">Logout</a>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useStore } from 'vuex';
import { RouterLink, useRoute } from 'vue-router'
import router from '@/router/index.js'

/**
 * Emits closeNavbar event
 */
const emit = defineEmits(['closeNavbar']);

/**
 * Vue Router instance
 * @type {import('vue-router').RouteLocationNormalized}
 */
const route = useRoute();

/**
 * Props for the component
 * @type {Object}
 */
const props = defineProps({
  userName: String,
  activeRoute: String,
});

/**
 * Checks if the current route is active
 * @type {import('vue').ComputedRef<boolean>}
 */
const isRouteActive = computed(() => {
  return route.path === props.activeRoute;
});

/**
 * Dropdown visibility state
 * @type {import('vue').Ref<boolean>}
 */
const isDropdownVisible = ref(false);

/**
 * Vuex Store instance
 * @type {import('vuex').Store}
 */
const store = useStore();

/**
 * Shows the dropdown
 */
const showDropdown = () => {
  isDropdownVisible.value = true;
};


/**
 * Hides the dropdown
 */
const hideDropdown = () => {
  isDropdownVisible.value = false;
};

/**
 * Logs out the user
 * Dispatches a logout action to the Vuex store
 * If successful, emits closeNavbar event and redirects to the home route
 * If unsuccessful, logs the error
 */
const logout = () => {
  store.dispatch('user/logout').then(() => {
    emit('closeNavbar');
    router.push({ name: 'home' }).catch(err => {
      console.error(err);
    });
  }).catch(error => {
    console.error('Logout failed:', error);
  });
};
</script>

<style scoped>
.user-menu-dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: flex;
  flex-direction: column;
  position: absolute;
  background-color: #f9f9f9;
  border-radius: 20px;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  padding: 12px 16px;
  z-index: 1;
}

.dropdown-content a, .dropdown-content router-link {
  padding: 10px 0;
  text-decoration: none;
  display: block;
  color: black;
  font-size: 20px;
}

.dropdown-content a:hover {
  color: rgba(0, 0, 0, 0.8);
  font-weight: bold;
}

.active-link {
  color: #FFD700;
  font-weight: bold;
}

</style>