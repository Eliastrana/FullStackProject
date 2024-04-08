<script setup>
/**
 * Importing necessary components for the admin page
 */
import AllQuizzesView from '@/views/adminPage/adminComponents/AllQuizzesView.vue'
import AllUsersView from '@/views/adminPage/adminComponents/AllUsersView.vue'
import FlaggedQuizzes from '@/views/adminPage/adminComponents/FlaggedQuizzes.vue'
import AdminToolbar from '@/views/adminPage/adminComponents/AdminToolbar.vue'
</script>

<template>
  <h1>Administrator console</h1>
  <h2>Moderate users and quizzes</h2>
  <AdminToolbar />
  <div class="admin-container">
    <AllQuizzesView />
    <AllUsersView />
  </div>
</template>


<style scoped>
.admin-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
  align-items: flex-start;
  padding: 20px;
}

.admin-container {
  padding: 20px;
}

.admin-container > * {
  min-width: 300px;
  max-width: 48%;
  flex-basis: 48%;
}

h1 {
  margin-top: 40px;
  text-align: center;
}

h2 {
  text-align: center;
  color: #3232ff;
}

h1 {
  font-size: 3rem;
  margin-top: 20px;
  margin-bottom: 0;
}

h2 {
  font-size: 2rem;
  margin-top: 10px;
}

@media (max-width: 768px) {
  .admin-container > * {
    max-width: 100%;
    flex-basis: 100%;
  }

  h1 {
    font-size: 2.5rem;
  }

  h2 {
    font-size: 1.5rem;
  }
}
</style>
