//App.vue

<template>
  <div id="app">
    <header>
      <div class="wrapper">
        <nav>
          <!-- Emoji on the left -->
          <RouterLink to="/" active-class="active-link">💻</RouterLink>

          <!-- Centered Links -->
          <div class="nav-center">
            <RouterLink to="/Quizzes" active-class="active-link">Quizzes</RouterLink>
            <h1> | </h1>
            <RouterLink to="/Create" active-class="active-link">Create</RouterLink>
          </div>

          <!-- User Authentication Links -->
          <div class="nav-right">
            <!-- Show this link if the user is not authenticated -->
            <RouterLink v-if="!isAuthenticated" to="/Login" active-class="active-link">Sign in</RouterLink>

            <!-- Show this link if the user is authenticated -->
            <RouterLink v-else to="/MyAccount">{{ userName }}</RouterLink>
          </div>
        </nav>
      </div>
    </header>

    <div class="content">
      <RouterView />
    </div>
  </div>
</template>


<script setup>
import { computed, watch, onMounted } from 'vue'
import { useStore } from 'vuex';

const store = useStore();

const isAuthenticated = computed(() => store.getters['user/isAuthenticated']);
const userName = computed(() => store.state.user.userInfo ? store.state.user.userInfo.username : '');

watch(() => store.state.user.token, async (newToken) => {
  console.log('Token changed:', newToken);
  if (newToken) {
    // When a token is detected, try fetching user details
    try {
      await store.dispatch('user/fetchUserDetails', newToken);
    } catch (error) {
      console.error('Failed to fetch user details:', error);
      // Handle the error appropriately
    }
  }
});

onMounted(async () => {
  if (isAuthenticated.value && !store.state.user.userInfo) {
    // If authenticated but user info is not loaded, fetch it
    try {
      await store.dispatch('user/fetchUserDetails', store.state.user.token);
    } catch (error) {
      console.error('Failed to fetch user details on mount:', error);
      // You might want to clear the session or handle the error appropriately here
    }
  }

  console.log('Mounted isAuthenticated:', isAuthenticated.value);
  console.log('Mounted userName:', userName.value);
});
</script>


<style>

/* Include the Roboto font */
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

header {
  font-family: 'Roboto', sans-serif;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;


}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.content {
  flex: 1; /* This will make the content grow */
}


.wrapper {
  width: 100%;
  max-width: 1200px; /* Adjust based on your preference */
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  flex: 1;

}

nav {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;

}

nav a {
  font-size: 30px; /* Larger font size */
  text-decoration: none;
  color: #333; /* Adjust color based on your preference */
}

nav a:hover {
  color: #3232ff; /* Adjust hover color based on your preference */
}

.nav-center {
  display: flex;
  justify-content: center;
  flex: 1;
}

.nav-right {
  display: flex;
  justify-content: flex-end;
}

.nav-center {
  display: flex;
  justify-content: center;
  align-items: center; /* This ensures vertical alignment */
  flex: 1;
}

.nav-center h1 {
  margin: 0 10px; /* Add some horizontal spacing */
  font-size: 24px; /* Adjust the font size as needed */
}


.active-link {
  font-weight: bold;
  color: #3232ff; /* Feel free to adjust the color to fit your design */
}


</style>


