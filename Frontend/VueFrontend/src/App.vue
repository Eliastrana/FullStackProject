//App.vue

<template>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

  <div id="app">
    <header>
      <RouterLink to="/" active-class="active-link" class="brand-name">Qanda</RouterLink>
      <button class="hamburger" @click="toggleNavbar" v-if="!isNavbarOpen && screenWidth <= 768">
        <i class="material-icons">menu</i>
      </button>
      <nav :class="{ 'open': isNavbarOpen }">
        <div class="close-icon" @click="toggleNavbar" v-if="isNavbarOpen">✕</div>

        <div class="nav-links" v-show="isNavbarOpen || screenWidth > 768">
          <RouterLink to="/Quizzes" active-class="active-link" @click.stop="closeNavbar()">Quizzes</RouterLink>
          <RouterLink to="/Create" active-class="active-link" @click.stop="closeNavbar()">Create</RouterLink>
          <RouterLink to="/Login" active-class="active-link" @click.stop="closeNavbar()" v-if="!isAuthenticated">Sign in</RouterLink>

          <template v-if="isAuthenticated">
            <div v-if="screenWidth <= 768 && isNavbarOpen" @click.stop="toggleNavbar" class="smallermenu">
              <!-- Fullscreen Mobile Navbar Links -->
              <RouterLink to="/MyAccount" active-class="active-link">{{ userName }}</RouterLink>
              <RouterLink to="/Admin" active-class="active-link">Admin</RouterLink>
              <RouterLink to="/Contact" active-class="active-link">Contact</RouterLink>
              <a href="#" @click.prevent="logout">Logout</a>
            </div>
            <!-- Desktop View Dropdown -->
            <UserMenuDropdown v-else @closeNavbar="closeNavbar()" :userName="userName" activeRoute="/MyAccount" />
          </template>
        </div>



      </nav>
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
import router from '@/router/index.js'

const store = useStore();
const isNavbarOpen = ref(false);
const screenWidth = ref(window.innerWidth);



const toggleNavbar = () => {
  isNavbarOpen.value = !isNavbarOpen.value;
  console.log("Navbar toggled: ", isNavbarOpen.value);
};

const closeNavbar = () => {
  isNavbarOpen.value = false;
};


const emit = defineEmits(['closeNavbar']);

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


const updateScreenWidth = () => {
  screenWidth.value = window.innerWidth;
};

onMounted(() => {
  window.addEventListener('resize', updateScreenWidth);
});

onUnmounted(() => {
  window.removeEventListener('resize', updateScreenWidth);
});



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
  position: absolute;
  right: 20px;
  top: 15px; /* Decrease this value to push the icon higher */
  font-size: 24px;
  background: none;
  border: none;
  color: #fff;

  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
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

/* Ensure the icon inherits the color and size from the button */

.hamburger .material-icons, .close-icon .material-icons {
  font-size: 30px;
  color: inherit; /* Should inherit white color from .hamburger or .close-icon */
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



.brand-name{

  font-size: 40px; /* Larger font size */
  text-decoration: none;
  color: #fffffa; /* Adjust color based on your preference */
}

.brand-name:hover {
  color: #ffffaa; /* Adjust hover color based on your preference */
}

nav a:hover {
  color: #ffffaa; /* Adjust hover color based on your preference */

}


.nav-center h1 {
  margin: 0 10px; /* Add some horizontal spacing */
  font-size: 24px; /* Adjust the font size as needed */
}


.nav-links {
  display: flex;
  gap: 20px; /* Adds space between each link */
  justify-content: flex-end; /* Aligns nav-links to the right */
  flex: 1;
}

.active-link {
  font-weight: bold;
  color: #FFD700; /* Feel free to adjust the color to fit your design */
}

.hamburger {
  display: none; /* Hidden by default */
  cursor: pointer;
  color: rgba(0, 0, 0, 0.8);


  /* Additional styling */
}

.close-icon {
  display: none; /* Hidden by default */
  cursor: pointer;
  color: rgba(0, 0, 0, 0.8);
  /* Additional styling */
}

nav.open .close-icon {
  display: block; /* Show close icon when menu is open */
}

@media (max-width: 768px) {

  .content {
    padding-top: 80px; /* Increase if navbar height increases in mobile view */
  }

  header {
    height: 60px; /* Or whatever height you prefer */
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    display: flex;
    align-items: center; /* Ensures content (e.g., hamburger icon) is vertically centered */
    justify-content: space-between; /* Keeps items spaced out */
    padding: 0 20px; /* Adjust as needed */
    z-index: 1000; /* Ensures header is above other content */
    background-color: #3232ff; /* Your preferred background color */
    margin-bottom: 200px;
  }


  .smallermenu {
    display: flex;
    flex-direction: column;
    text-align: center  ;

    align-items: flex-start; /* Aligns items to the start (left) */

    gap: 20px;
    padding-left: 40px;
    font-family: 'DM Sans', sans-serif;
  }


  .hamburger, .close-icon {
    display: block;
    position: fixed; /* Fixed position to keep it visible */
    top: 20px;
    right: 20px;
    z-index: 1000; /* Above the nav */
  }

  .nav-links {
    display: flex;
    flex-direction: column; /* Stack the links vertically */
    align-items: flex-start; /* Aligns items to the start (left) */
    gap: 20px; /* Space between each link */
    width: 100%; /* Full width to center align easier */
    padding-left: 20px; /* Adds padding to align left but keep centered */
  }

  .nav-links a, .nav-links router-link {
    font-family: 'DM Sans', sans-serif;
    font-size: 3rem; /* Larger font size for easy reading */
    padding: 10px; /* Padding for larger tap targets */
    color: #ffffff; /* Color for better contrast */
    text-align: center;
  }

  .nav-links a:hover, .nav-links router-link:hover {
    color: #ffffaa; /* Adjust hover color based on your preference */
  }


  router-link:focus {
    color: #f3dc5e; /* Adjust hover color based on your preference */
  }


  nav {
    display: none; /* Initially hide the nav menu */
    flex-direction: column;
    position: absolute;
    top: 60px; /* Adjust based on header height */
    left: 0;
    width: 100%;
    background-color: #3232ff;
    padding: 20px;
    z-index: 20;
    margin-bottom: 200px;

  }

  nav.open {
    display: flex;
    flex-direction: column;
    justify-content: center; /* Centers items vertically */
    align-items: center; /* Centers items horizontally */
    position: fixed; /* Use fixed to cover the entire screen */
    top: 0;
    left: 0;
    width: 100%;
    height: 100vh; /* Full viewport height */
    background-color: #3232ff; /* Adjust background color as needed */
    z-index: 999; /* Ensure nav is above other content */
  }
}





</style>


