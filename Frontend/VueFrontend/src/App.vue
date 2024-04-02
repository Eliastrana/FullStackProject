//App.vue

<template>
  <div id="app">
    <header :class="{ 'open': isNavbarOpen }">
    <button class="hamburger" @click="toggleNavbar" v-if="!isNavbarOpen">&#9776;</button>
      <div class="wrapper" :class="{ open: isNavbarOpen }">
        <nav>
          <!-- Close Icon for Mobile View -->
          <div class="close-icon" @click="toggleNavbar" v-if="isNavbarOpen">✕</div>

          <!-- Navigation Links -->
          <RouterLink to="/" active-class="active-link" class="brand-name">Qanda</RouterLink>
          <div class="nav-links" v-show="isNavbarOpen || screenWidth > 768">
            <RouterLink to="/Quizzes" active-class="active-link">Quizzes</RouterLink>
            <RouterLink to="/Create" active-class="active-link">Create</RouterLink>
            <RouterLink v-if="!isAuthenticated" to="/Login" active-class="active-link">Sign in</RouterLink>
            <UserMenuDropdown v-else :userName="userName" activeRoute="/MyAccount" />
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
import { ref, computed, onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex';
import UserMenuDropdown from '@/components/userDropdown/UserMenuDropdown.vue'

const store = useStore();
const isNavbarOpen = ref(false);
const screenWidth = ref(window.innerWidth);



const toggleNavbar = () => {
  isNavbarOpen.value = !isNavbarOpen.value;
};


const updateWidth = () => {
  screenWidth.value = window.innerWidth;
  if (screenWidth.value > 768) isNavbarOpen.value = false; // Close navbar on screen resize if > 768px
};

onMounted(() => {
  window.addEventListener('resize', updateWidth);
});

onUnmounted(() => {
  window.removeEventListener('resize', updateWidth);
});

const isAuthenticated = computed(() => store.getters['user/isAuthenticated']);
const userName = computed(() => store.state.user.userInfo ? store.state.user.userInfo.username : '');
</script>



<style>

/* Include the Roboto font */
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

header {
  font-family: 'DN Sans', sans-serif;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
  margin-bottom: 200px;

    width: 100%;
    position: fixed; /* Keeps the header at the top */
    top: 0; /* Aligns it to the top of the viewport */
    background-color: #3232ff; /* Ensure the background is not transparent */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Add a subtle shadow */
    z-index: 1000; /* High z-index to ensure it stays on top of other content */



}

.hamburger {
  display: none;
  position: absolute;
  right: 20px;
  top: 20px;
  font-size: 30px;
  background: none;
  border: none;
  color: #fff;
  z-index: 20;
}

.close-icon {
  display: none;
  position: absolute;
  right: 20px;
  top: 20px;
  font-size: 30px;
  color: #fff;
  cursor: pointer;
  z-index: 20;
}

   /* Reset CSS for body */
 body {
   margin: 0;
   padding: 0;

   margin-top: 5%;
 }

* {
  box-sizing: border-box;
}


/* Adjust the #app to ensure it takes full viewport width */
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%; /* Make sure app container is full viewport width */
}

/* Ensure header stretches fully */


/* Adjust wrapper class if it restricts the navbar from stretching fully */
.wrapper {
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  flex: 1;
  width: 95%; /* Ensure wrapper is full width */
  max-width: none; /* Remove max-width or adjust it accordingly */
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%; /* Ensure nav takes up full width of its parent */
  gap: 20px;
}

/* Remaining styles unchanged */

nav a {
  font-size: 40px; /* Larger font size */
  text-decoration: none;
  color: #fffffa; /* Adjust color based on your preference */
}

nav a:hover {
  color: #ffffaa; /* Adjust hover color based on your preference */

}

.nav-center {
  display: flex;
  justify-content: center;
  flex: 1;
}

.nav-right {
  display: flex;
  justify-content: flex-end; /* Keep it aligned to the right */
  gap: 20px; /* Adds space between each component */
  color: #FFFFFF;

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


.nav-links {
  display: flex;
  gap: 20px; /* Adds space between each link */
}

.active-link {
  font-weight: bold;
  color: #FFD700; /* Feel free to adjust the color to fit your design */
}


@media (max-width: 768px) {
  header {
    position: fixed; /* Keep the header at the top */
    top: 0;
    left: 0;
    right: 0;
    height: 100vh; /* Expand to full screen height when open */
    width: 100vw; /* Full viewport width */
    display: flex;
    flex-direction: column;
    justify-content: center; /* Center nav items vertically */
    align-items: center; /* Center nav items horizontally */
    background-color: #3232ff; /* Change to desired blue shade */
    transform: translateY(-100%); /* Hide off-screen initially */
    transition: transform 0.3s ease; /* Smooth transition */
    padding-top: 0; /* Remove padding at the top */
    z-index: 20; /* Ensure it's above other content */
  }

  header.open {
    transform: translateY(0); /* Slide in */
  }

  .wrapper, .nav-links {
    width: 100%; /* Full width */
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }


  .hamburger {
    display: block;
    position: absolute;
    right: 20px;
    top: 20px;
    font-size: 30px;
    background: none;
    border: none;
    color: rgba(0, 0, 0, 0.8); /* Adjust color as needed */
    z-index: 20;
  }

  .hamburger, .close-icon {
    display: block; /* Show the hamburger and close icon */
    position: absolute;
    top: 20px; /* Adjust as necessary */
    right: 20px; /* Adjust as necessary */
    z-index: 30; /* Above the nav items */
  }

  .nav-links {
    display: none; /* Hide initially */
  }

  .wrapper.open .nav-links {
    display: flex; /* Show when menu is open */
  }

  nav a, .user-menu-dropdown {
    font-size: 24px; /* Adjust based on preference */
    color: #ffffff; /* Adjust for visibility against blue background */
    margin: 10px 0; /* Spacing between nav items */
  }
}



</style>


