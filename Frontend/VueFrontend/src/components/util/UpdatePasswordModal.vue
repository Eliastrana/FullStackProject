<template>
  <div v-if="isVisible" class="modal">
    <div class="modal-content">
      <h2>Update Password</h2>
      <input v-model="oldPassword" type="password" placeholder="Old Password" />
      <input v-model="newPassword" type="password" placeholder="New Password" />
      <input v-model="confirmPassword" type="password" placeholder="Confirm New Password" />
      <p v-if="error">{{ error }}</p>
      <button @click="submitForm">Update</button>
      <button @click="closeModal">Cancel</button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { defineEmits, defineProps } from 'vue';

/**
 * Emits custom events
 * @type {Function}
 */
const props = defineProps({
  isVisible: Boolean
});

const emits = defineEmits(['close', 'updatePassword']);

const oldPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');
const error = ref('');

/**
 * Watches for changes in the newPassword and confirmPassword refs
 * and sets the error message if the passwords do not match
 */

watch([newPassword, confirmPassword], () => {
  if (newPassword.value !== confirmPassword.value) {
    error.value = "New passwords do not match.";
  } else if (!newPassword.value.match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)) {
    error.value = "New password does not meet complexity requirements.";
  } else {
    error.value = '';
  }
});

/**
 * Submits the form
 */
const submitForm = () => {
  if (newPassword.value !== confirmPassword.value) {
    error.value = "New passwords do not match.";
    return;
  }
  if (!newPassword.value.match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)) {
    error.value = "New password does not meet complexity requirements.";
    return;
  }
  if (!error.value) {
    emits('updatePassword', { oldPassword: oldPassword.value, newPassword: newPassword.value });
    oldPassword.value = '';
    newPassword.value = '';
    confirmPassword.value = '';
  }
};

/**
 * Closes the modal
 */
const closeModal = () => {
  error.value = ''; // Reset error message
  emits('close');
};
</script>

<style scoped>
.modal {
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  max-width: 500px;
  width: 90%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.modal-content input {
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.modal-content button {
  cursor: pointer;
  padding: 10px 20px;
  margin-top: 10px;
  border: none;
  border-radius: 20px;
  max-width: 100%;
  background-color: #007bff;
  color: white;
  font-weight: bold;
}

.modal-content button:last-child {
  background-color: #a9a9a9;
}

.modal-content button:hover {
  opacity: 0.9;
}

.modal-content p {
  color: red;
  font-size: 0.9rem;
}
</style>
