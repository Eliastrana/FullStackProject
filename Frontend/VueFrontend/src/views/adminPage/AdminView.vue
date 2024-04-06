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
<!--  <FlaggedQuizzes />--> <!-- This is possible for a further implementation of a flagging system. -->
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
  padding: 20px; /* Add some padding around the container */
}

/* Enhance child components styling for scalability */
.admin-container {
  padding: 20px; /* This adds space around the content */
}

.admin-container > * {
  min-width: 300px;
  max-width: 48%; /* Adjusting max-width to slightly less than half, considering the gap */
  flex-basis: 48%; /* Adjust flex-basis similarly */
}


h1, h2 {
  text-align: center; /* Centers the titles */
  color: #3232ff; /* Sets the color for both headings for consistency */
}

h1 {
  font-size: 3rem; /* Adjusted for better scaling */
  margin-top: 20px;
  margin-bottom: 0;
}

h2 {
  font-size: 2rem;
  margin-top: 10px; /* Adjust spacing above h2 for tighter grouping with h1 */
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .admin-container > * {
    max-width: 100%; /* Each component takes full width of the container on smaller screens */
    flex-basis: 100%; /* Adjusts initial size for responsiveness */
  }

  h1 {
    font-size: 2.5rem; /* Slightly smaller font size for smaller screens */
  }

  h2 {
    font-size: 1.5rem;
  }
}
</style>
