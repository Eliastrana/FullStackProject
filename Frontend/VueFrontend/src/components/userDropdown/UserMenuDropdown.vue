<template>
  <div class="user-menu-dropdown" @mouseleave="hideDropdown" @mouseenter="showDropdown">
    <!-- Wrap the username in a RouterLink for navigation on click -->
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



const emit = defineEmits(['closeNavbar']);

const route = useRoute();

// Accepting userName as a prop
const props = defineProps({
  userName: String,
  activeRoute: String,

});

const isRouteActive = computed(() => {
  return route.path === props.activeRoute;
});


const isDropdownVisible = ref(false);

const store = useStore();

// Sample userName, replace with actual dynamic userName if needed


const showDropdown = () => {
  isDropdownVisible.value = true;
};

const hideDropdown = () => {
  isDropdownVisible.value = false;
};

const logout = () => {
  store.dispatch('user/logout').then(() => {
    emit('closeNavbar'); // Emit the event right before redirecting
    router.push({ name: 'home' }).catch(err => {
      console.error(err);
    });
  }).catch(error => {
    console.error('Logout failed:', error);
    // Handle the error, maybe show a message to the user
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
  color: #FFD700; /* Example active color */
  font-weight: bold;

}
</style>