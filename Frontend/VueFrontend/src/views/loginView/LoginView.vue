//LoginView.vue
<template>
  <div class="center-container"></div>
  <div class="login-container">
    <h2>Sign in</h2>
    <form @submit.prevent="login">
      <div class="input-container">
        <input v-model="username" type="text" id="username" placeholder=" " required>
        <label for="username">Username</label>
      </div>
      <div class="input-container">
        <input v-model="password" type="password" id="password" placeholder=" " required>
        <label for="password">Password</label>
      </div>
      <div id="button-container">
        <button id="sign-in" type="submit" :disabled="username.trim() === '' || password.trim() === '' ">Sign in</button>
        <button id="create-user-link" type="button" @click="navigateToCreateUser">Register</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const username = ref('');
const password = ref('');
const router = useRouter();
const store = useStore();


const login = async () => {
  try {
    // Dispatching a Vuex action instead of directly calling the API
    await store.dispatch('user/login', {
      username: username.value,
      password: password.value
    });

    // Navigate to the home view upon successful login
    router.push({name: 'MyAccount'}); // Make sure the route name matches your routes configuration
  } catch (error) {
    console.error("Login failed:", error);
    // Handle login failure, such as displaying an error message to the user
  }
};

const navigateToCreateUser = () => {
  router.push({ name: 'CreateUser' }); // Adjust this to match your route configuration
};
</script>



<style scoped>

.center-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 10vh;
}

.login-container {
  margin: auto;
  background-color: rgb(249, 249, 249);
  padding: 20px;
  border-radius: 8px;
  width: 40%;
  max-width: 500px;
  box-sizing: border-box;
  min-width: 400px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.input-container {
  position: relative;
  margin-bottom: 10px;
  padding-right: 20px;
}

#button-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
  max-width: 300px;
  margin: 0 auto;
}

input {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: none;
  border-bottom: 2px solid #343434;
  background-color: transparent;
  color: #343434;
}

input::placeholder {
  color: transparent;
}

input:focus {
  outline: none;
}

input:focus + label,
input:not(:placeholder-shown) + label {
  transform: translateY(-20px);
  font-size: 12px;
  color: #000000;
}

label {
  position: absolute;
  left: 10px;
  top: 10px;
  transition: all 0.3s ease;
  pointer-events: none;
}

#sign-in {
  padding: 10px 30px;
  margin-top: 20px;
  border: none;
  border-radius: 5px;
  background-color: #0056b3;
  color: #ffffff;
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.2s, transform 0.2s; /* Sørger for jevne overganger */
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
  font-size: 20px;
}

#sign-in:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
  background-color: #007bff;

}

#sign-in:active {
  background-color: #3232ff;
  transform: translateY(2px);
}

#sign-in:disabled {
  background-color: #d3d3d3;
  color: #8c8c8c;
  cursor: not-allowed;
}

#create-user-link {
  background-color: transparent;
  color: #424242;
  padding: 0;
  border: none;
  cursor: pointer;
  text-decoration: none;
  font-size: 15px;
  margin-top: 10px;
}

#create-user-link:hover {
  color: #0056b3;
  text-decoration: underline;
}
</style>
