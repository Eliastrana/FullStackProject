//App.vue

<template>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

  <div id="app">
    <header>
      <RouterLink to="/" active-class="active-link" class="brand-name">Qanda</RouterLink>
      <button class="hamburger" @click="toggleNavbar" v-if="!isNavbarOpen && screenWidth <= 768">
        <i class="material-icons">menu</i>
      </button>
      <nav :class="{ 'open': isNavbarOpen, 'closing': isNavbarClosing }">
        <div class="close-icon" @click="toggleNavbar" v-if="isNavbarOpen">✕</div>
        <div class="nav-links" v-show="isNavbarOpen || screenWidth > 768">
          <div @click="navigateAfterClose('/Quizzes')"
               :class="{'alternativeRouter': true, 'active-link': route.path === '/Quizzes'}">Quizzes</div>
          <div @click="navigateAfterClose('/Create')"
               :class="{'alternativeRouter': true, 'active-link': route.path === '/Create'}">Create</div>
          <div @click="navigateAfterClose('/Login')" class="alternativeRouter" v-if="!isAuthenticated">Sign in</div>
          <template v-if="isAuthenticated">
            <div v-if="screenWidth <= 768 && isNavbarOpen" @click.stop="toggleNavbar" class="smallermenu">
              <RouterLink to="/MyAccount" active-class="active-link">{{ userName }}</RouterLink>
              <div class="undermenu">
              <RouterLink to="/Admin" active-class="active-link">Admin</RouterLink>
              <RouterLink to="/Contact" active-class="active-link">Contact</RouterLink>
              <a href="#" @click.prevent="logout">Logout</a>
                </div>
            </div>
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
import { useRoute } from 'vue-router'


/**
 * Vue router instance
 * @type {import('vue-router').Router}
 */

const route = useRoute();

/**
 * Vuex store instance
 * @type {import('vuex').Store}
 */
const store = useStore();

/**
 * State of the navigation bar
 * @type {import('vue').Ref<boolean>}
 */
const isNavbarOpen = ref(false);

/**
 * Screen width
 * @type {import('vue').Ref<number>}
 */
const screenWidth = ref(window.innerWidth);

/**
 * User authentication status
 * @type {import('vue').ComputedRef<boolean>}
 */
const isAuthenticated = computed(() => store.getters['user/isAuthenticated']);

/**
 * Current user's name
 * @type {import('vue').ComputedRef<string>}
 */
const userName = computed(() => store.state.user.userInfo ? store.state.user.userInfo.username : '');

/**
 * State of the navigation bar closing
 * @type {import('vue').Ref<boolean>}
 */
const isNavbarClosing = ref(false);

/**
 * Emits a custom event
 * @type {import('vue').EmitFn}
 */
const emit = defineEmits(['closeNavbar']);

/**
 * Navigates to a specified path after closing the navigation bar
 * @param {string} path - The path to navigate to
 */
const navigateAfterClose = (path) => {
  router.push(path);
  isNavbarClosing.value = true;
  setTimeout(() => {
    isNavbarClosing.value = false;
    isNavbarOpen.value = false;
  }, 500);
};

/**
 * Toggles the state of the navigation bar
 */
const toggleNavbar = () => {
  if (isNavbarOpen.value) {
    isNavbarClosing.value = true;
    setTimeout(() => {
      isNavbarClosing.value = false;
      isNavbarOpen.value = !isNavbarOpen.value;
    }, 500);
  } else {
    isNavbarOpen.value = !isNavbarOpen.value;
  }
};

/**
 * Closes the navigation bar
 */
const closeNavbar = () => {
  isNavbarOpen.value = false;
};

/**
 * Logs out the user
 */
const logout = () => {
  store.dispatch('user/logout').then(() => {
    emit('closeNavbar'); // Assuming emit is received in the setup function
    // router.push({ name: 'home' }).catch(err => {
    //   console.error(err);
    // });
  }).catch(error => {
    console.error('Logout failed:', error);
  });
};



/**
 * Updates the screen width
 */
const updateScreenWidth = () => {
  screenWidth.value = window.innerWidth;
};

/**
 * Updates the screen width and closes the navigation bar if the screen width is greater than 768
 */
const updateWidth = () => {
  screenWidth.value = window.innerWidth;
  if (screenWidth.value > 768) isNavbarOpen.value = false;
};

onMounted(() => {
  window.addEventListener('resize', updateScreenWidth);
});

onUnmounted(() => {
  window.removeEventListener('resize', updateScreenWidth);
});

onMounted(() => {
  window.addEventListener('resize', updateWidth);
});

onUnmounted(() => {
  window.removeEventListener('resize', updateWidth);
});

</script>


<style>

@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

@keyframes slideDownBackground {
  from {
    transform: translateY(-100%);
  }
  to {
    transform: translateY(0);
  }
}

@keyframes slideUpBackground {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(-100%);
  }
}


header {
  font-family: 'DN Sans', sans-serif;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
  margin-bottom: 200px;
  width: 100%;
  position: fixed;
  top: 0;
  background-color: #3232ff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.hamburger {
  position: absolute;
  right: 20px;
  top: 15px;
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

.hamburger .material-icons, .close-icon .material-icons {
  font-size: 30px;
  color: inherit;
}

body {
   margin: 0;
   padding: 0;
   margin-top: 5%;
}

* {
  box-sizing: border-box;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  gap: 20px;
}

.undermenu {
  display: flex;
  flex-direction: column;
  text-align: center  ;
  align-items: flex-start;
  gap: 20px;
  padding-left: 40px;
  font-family: 'DM Sans', sans-serif;
}

nav a {
  font-size: 40px;
  text-decoration: none;
  color: #fffffa;
}




.alternativeRouter {
  font-size: 40px;
  text-decoration: none;
  color: #fffffa;
}

.alternativeRouter:hover {
  color: #ffffaa;
  cursor: pointer;
}



nav a:hover {
  color: #ffffaa;

}



.brand-name{

  font-size: 40px;
  text-decoration: none;
  color: #fffffa;
}

.brand-name:hover {
  color: #ffffaa;
}

nav a:hover {
  color: #ffffaa;

}

.nav-center h1 {
  margin: 0 10px;
  font-size: 24px;
}

.nav-links {
  display: flex;
  gap: 20px;
  justify-content: flex-end;
  flex: 1;
}

.active-link {
  font-weight: bold;
  color: #FFD700;
}

.hamburger {
  display: none;
  cursor: pointer;
  color: rgba(0, 0, 0, 0.8);
}

.close-icon {
  display: none;
  cursor: pointer;
  color: rgba(0, 0, 0, 0.8);
}

nav.open .close-icon {
  display: block;
}

@media (max-width: 768px) {

  .content {
    padding-top: 80px;
  }

  header {
    height: 60px;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    z-index: 1000;
    background-color: #3232ff;
    margin-bottom: 200px;
  }


  .smallermenu {
    display: flex;
    flex-direction: column;
    text-align: center  ;
    align-items: flex-start;
    padding-left: 40px;
    font-family: 'DM Sans', sans-serif;
  }


  .hamburger, .close-icon {
    display: block;
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 1000;
  }

  .nav-links {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 20px;
    width: 100%;
    padding-left: 20px;
  }

  .nav-links a, .nav-links router-link {
    font-family: 'DM Sans', sans-serif;
    font-size: 40px;
    padding: 10px;
    color: #ffffff;
    text-align: center;
  }

  .nav-links a:hover, .nav-links router-link:hover {
    color: #ffffaa;
  }


  router-link:focus {
    color: #f3dc5e;
  }


  nav {
    display: none;
    flex-direction: column;
    position: absolute;
    top: 60px;
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
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100vh;
    background-color: #3232ff;
    animation: slideDownBackground 0.5s ease forwards;
    z-index: 999;
  }

  nav.open {
    animation: slideDownBackground 0.5s ease forwards;
  }

  nav.closing {
    animation: slideUpBackground 0.5s ease forwards;
  }

}

</style>


