<template>
  <div class="center-container"></div>
  <div class="login-container">
    <h2>Register</h2>
    <form @submit.prevent="registerUser">
      <div class="input-container">
        <input v-model="username" type="text" id="username" placeholder=" " required>
        <label for="username">Username</label>
      </div>
      <div class="input-container">
        <input v-model="email" type="email" id="email" placeholder=" " required>
        <label for="email">Email</label>
      </div>
      <div class="input-container">
        <input v-model="password" type="password" id="password" placeholder=" " required>
        <label for="password">Password</label>
      </div>
      <div class="input-container">
        <input v-model="passwordConfirmation" type="password" id="password-confirmation" placeholder=" " required>
        <label for="password-confirmation">Confirm Password</label>
      </div>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <div id="button-container">
        <button id="register-user" type="submit" :disabled="username.trim() === '' || password.trim() === '' || email.trim() === '' || passwordConfirmation.trim() === ''">Register</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const username = ref('');
const email = ref('');
const password = ref('');
const passwordConfirmation = ref('');
const errorMessage = ref('');

const router = useRouter();
const store = useStore();

const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/; // Simple regex for email validation
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/; // Regex for password validation

const registerUser = async () => {
  errorMessage.value = '';
  if (!emailRegex.test(email.value)) {
    errorMessage.value = "Please enter a valid email address.";
    return;
  }
  if (!passwordRegex.test(password.value)) {
    errorMessage.value = "Password must be at least 8 characters long and include a letter and a number.";
    return;
  }
  if (password.value !== passwordConfirmation.value) {
    errorMessage.value = "Passwords do not match.";
    return;
  }

  try {
    await store.dispatch('user/register', {
      username: username.value,
      email: email.value,
      password: password.value,
    });

    console.log("Registration successful");
    router.push({ name: 'home' });
  } catch (error) {
    console.error("Registration failed:", error);
    errorMessage.value = "An error occurred. Please try again.";
  }
};
</script>

<style scoped>

.error-message {
  color: red;
}

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

#register-user{
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
#register-user:disabled {
  background-color: #d3d3d3;
  color: #8c8c8c;
  cursor: not-allowed;
}

#register-user:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
  background-color: #007bff;
}

#register-user:active{
  background-color: #3232ff;
  transform: translateY(2px);
}

#register-user:disabled, #register-user:disabled:hover {
  background-color: #d3d3d3;
  color: #8c8c8c;
  cursor: not-allowed;
}
</style>
