<script setup>

import AllQuizzesView from '@/views/adminPage/adminComponents/AllQuizzesView.vue'
import AllUsersView from '@/views/adminPage/adminComponents/AllUsersView.vue'
</script>

  <template>


    <h1>Administrator console</h1>
    <h2>Moderate users and quizzes</h2>

    <div class="admin-container">
      <AllQuizzesView />
      <AllUsersView />
    </div>
  </template>


<style scoped>
.admin-container {
  display: flex; /* Enable flexbox */
  flex-wrap: wrap; /* Allow items to wrap onto the next line as needed */
  gap: 20px; /* Optional: Adds space between components */
  justify-content: center; /* Center-align items horizontally */
  align-items: flex-start; /* Align items to the start of the cross axis */
}

/* If needed, you can set a min-width on each component to ensure they don't get too narrow before wrapping */
.admin-container > * {
  min-width: 300px; /* Example min-width; adjust as necessary */
  flex: 1; /* Allow components to grow and fill available space */
}

h1 {
  font-size: 4.5rem;
  padding-left: 20px;
}

h2 {
  font-size: 2.5rem;
  padding-left: 20px;
  margin-bottom: -20px;
  color: #3232ff;
}

</style>
