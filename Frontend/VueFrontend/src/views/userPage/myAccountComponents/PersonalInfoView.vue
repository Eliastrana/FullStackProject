//PersonalInfoView.vue
<script setup>
import { computed, onMounted, ref } from 'vue'
import router from '@/router/index.js'
import { UserService } from '@/services/UserService.js'
import store from '@/store/index.js'
import UpdatePasswordModal from '@/components/util/UpdatePasswordModal.vue'

/**
 * User information
 * @type {import('vue').Ref<Object>}
 */

const userInfo = ref(null);
const isUpdatePasswordModalVisible = ref(false);
const totalQuizzesDone = computed(() => store.getters['quizAttempt/totalQuizzesDone']);
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

onMounted(async () => {
  try {
    userInfo.value = await UserService.getUserDetails();
  } catch (error) {
    console.error('Failed to load user info:', error);
    await router.push({ name: 'login' });
  }
});

const showUpdatePasswordModal = () => {
  isUpdatePasswordModalVisible.value = true;
};

const handleUpdatePassword = async ({ oldPassword, newPassword }) => {
  try {
    await UserService.updatePassword({ oldPassword, newPassword });
    alert('Password updated successfully'); // Consider using a more integrated notification system
    isUpdatePasswordModalVisible.value = false;
  } catch (error) {
    console.error('Failed to update password:', error);
    alert('Failed to update password'); // Consider using a more integrated notification system for errors
  }
};

</script>

<template>
  <div class="user-info-container" v-if="userInfo">

    <div class="title">
      <h1>Your Info</h1>
      <h2>This is you</h2>
      <img :src="'/images/profilepic.png'" alt="User photo" class="user-photo"/>
    </div>

    <div class="basic-info">
      <p><strong>Name:</strong> {{ userInfo.username }}</p>
      <p><strong>Email:</strong> {{ userInfo.email }}</p>
    </div>

    <div class="updateinfo">
      <button class="updatepassword" @click="showUpdatePasswordModal">
        <i class="fas fa-user-edit"></i>
        Update password
      </button>
      <UpdatePasswordModal
        :isVisible="isUpdatePasswordModalVisible"
        @close="isUpdatePasswordModalVisible = false"
        @updatePassword="handleUpdatePassword"
      />
    </div>
    <div class="level-info">
      <p><strong>Total Quizzes Done:</strong> {{totalQuizzesDone}}</p>
    </div>

  </div>
</template>

<style scoped>

.user-info-container {
  max-width: 800px; /* or your desired width */
  margin-right: auto;
  margin-left: auto;
  display: block; /* Default, but explicitly stated for clarity */
  padding: 20px;
  /* other styles */
}

.user-info-container {
  max-width: 800px; /* or your desired width */
  margin-right: auto;
  margin-left: auto;
  display: block; /* Default, but explicitly stated for clarity */
  padding: 20px;
  /* other styles */

  flex-direction: column;
  align-items: center;
  min-height: 83vh;
  margin-top: 5%;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.title, .basic-info, .level-info, .achievements, .updateinfo {
  text-align: center;
  width: 100%; /* Ensures the container fills its parent's width */
}

/* Ensure buttons are centered by adjusting the .updateinfo class if they are not */
.updateinfo {
  justify-content: center; /* Centers flex items on the main axis (horizontally) */
  flex-direction: column; /* Stacks flex items vertically */
  gap: 20px; /* Adds space between vertically stacked items */
}

.updateusername, .updatepassword {
  margin: 10px auto; /* Automatically margins on the sides center the buttons */
}

.user-photo {
  display: block; /* Makes the <img> block level for margin auto to work */
  margin: 20px auto; /* Centers the image */
  align-items: center;
  width: 200px;
  height: 200px;
  border-radius: 50%;
}

.updateinfo {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  width: 100%;
  margin: 10px auto;
}

.updateusername, .updatepassword {

  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  max-width: 30%;
  max-height: 30px;
  margin: 10px ;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  background-color: #ececec;
  border: none;
  font-size: 1rem;
}

.updateusername:hover, .updatepassword:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px);
}

.basic-info, .level-info, .achievements {
  width: 100%;
  max-width: 600px;
  text-align: center;
  margin: 10px auto;
}

h1, h2, .highlight {
  font-family: 'DM Sans', sans-serif;
}

h2 {
  color: #3232ff;
}


.highlight {
  font-size: 1.2em;
  color: #4a5568;
}

.achievements ul {
  list-style-type: none;
  padding: 0;
}

.achievements li {
  background-color: #f0f0f0;
  margin: 5px 0;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 1px 2px rgba(0,0,0,0.1);
}

@media (max-width: 768px) {
  .user-info-container {
    width: auto;
    margin: 5% 10px;
    padding: 10px;
  }

  .user-photo {
    width: 80px;
    height: 80px;
  }

  .highlight {
    font-size: 1em;
  }
}
</style>


