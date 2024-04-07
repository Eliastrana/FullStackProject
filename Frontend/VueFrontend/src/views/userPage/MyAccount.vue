<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios';
import PersonalStatisticsView from '@/views/userPage/myAccountComponents/PersonalStatisticsView.vue'
import PersonalInfoView from '@/views/userPage/myAccountComponents/PersonalInfoView.vue'
import PersonalQuizzesView from '@/views/userPage/myAccountComponents/PersonalQuizzesView.vue'
import store from '@/store/index.js'

/**
 * Current user's name
 * @type {import('vue').ComputedRef<string>}
 */
const userName = computed(() => store.state.user.userInfo ? store.state.user.userInfo.username : '');

/**
 * User data
 * @type {import('vue').Ref<Object>}
 */
const user = ref({
  name: 'Loading...',
});

/**
 * Fetches user data from the API when the component is mounted
 */
onMounted(async () => {
  try {
    const response = await axios.get('/api/user');
    user.value = response.data;
  } catch (error) {
    console.error('Failed to load user info:', error);
  }
});

</script>

<template>
  <h1>Welcome {{userName}}!</h1>
  <h2>Here you can view your private quizzes, statistics, and info</h2>

  <div class="container">
    <PersonalQuizzesView class="personal-quizzes"/>

    <PersonalInfoView class="personal-info"/>

    <div/>


    <PersonalStatisticsView class="personal-statistics"/>

  </div>
</template>

<style scoped>

h1 {
  font-family: 'DM Sans', sans-serif;
  text-align: left;
  margin-top: 20px;
  margin-left: 40px;
  font-size: 4rem;
}

h2 {
  font-family: 'DM Sans', sans-serif;
  text-align: left;
  margin-left: 40px;
  margin-right: 2%;
  font-size: 2rem;
}






@media (max-width: 768px) {
  .personal-statistics, .personal-info, .personal-quizzes {
    flex-basis: 100%;
    max-width: 100%;
  }
}
</style>

