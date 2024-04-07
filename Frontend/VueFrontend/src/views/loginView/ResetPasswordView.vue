<template>
  <div class="center-container">
    <div class="reset-password-container">
      <h2>Reset Password</h2>
      <form @submit.prevent="sendResetLink">
        <div class="input-container">
          <input v-model="email" type="email" id="email" placeholder=" " required>
          <label for="email">Email</label>
        </div>
        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
        <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
        <div id="button-container">
          <button id="send-reset-link" type="submit">Send Reset Link</button>
        </div>
      </form>
    </div>
  </div>
</template>


<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

/**
 * Email for reset password form
 * @type {import('vue').Ref<string>}
 */
const email = ref('');

/**
 * Error message for reset password form
 * @type {import('vue').Ref<string>}
 */
const errorMessage = ref('');

/**
 * Success message for reset password form
 * @type {import('vue').Ref<string>}
 */
const successMessage = ref('');

/**
 * Vue Router instance
 * @type {import('vue-router').Router}
 */
const router = useRouter();

/**
 * Vuex Store instance
 * @type {import('vuex').Store}
 */
const store = useStore();

/**
 * Function to send reset password link
 * This function logs the email to which the reset link is being sent.
 * If successful, it sets the success message.
 * If there is an error, it sets the error message.
 */
const sendResetLink = async () => {
  try {

    console.log(`Sending reset link to ${email.value}`);

    successMessage.value = "If an account exists for this email, a reset link has been sent.";
    errorMessage.value = '';

  } catch (error) {
    console.error("Error sending reset link:", error);
    errorMessage.value = "An error occurred. Please try again.";
    successMessage.value = '';
  }
};
</script>


<style scoped>
.center-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin-top: -20vh;
}

.reset-password-container {
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
  margin-bottom: 20px;
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

#error-message, .success-message {
  color: red;
  margin-top: 10px;
}

.success-message {
  color: green;
}

#send-reset-link {
  padding: 10px 30px;
  margin-top: 20px;
  border: none;
  border-radius: 5px;
  background-color: #0056b3;
  color: #ffffff;
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.2s, transform 0.2s;
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
  font-size: 20px;
}

#send-reset-link:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
  background-color: #007bff;
}

#send-reset-link:active {
  background-color: #3232ff;
  transform: translateY(2px);
}
</style>
