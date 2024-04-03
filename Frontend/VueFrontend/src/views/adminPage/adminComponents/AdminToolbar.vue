<script setup>

const exportAllQuizzes = () => {
  alert('Exporting all quizzes...');
  // Implementation for exporting all quizzes goes here
};

const importQuiz = () => {
  alert('Importing quiz...');
  // Implementation for importing a quiz goes here
};


</script>

<template>
  <div class="admin-toolbar">
    <div class="admin-toolbar__button" @click="importQuiz">
      + Import Quiz
    </div>

    <div class="admin-toolbar__button" @click="exportAllQuizzes">
      Export All Quizzes
    </div>
  </div>
</template>


<style scoped>
.admin-toolbar {
  display: flex;
  align-items: center;
  padding: 10px;
  gap: 20px;
  margin-left: 20px;
}

.admin-toolbar__button {
  cursor: pointer;
  background-color: #f0f0f0;
  padding: 10px 20px;
  min-height: 60px;
  min-width: 150px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s;
  font-family: 'DM Sans', sans-serif;
  font-size: 2rem;
}

.admin-toolbar__button:hover {
  background-color: #e0e0e0;
}

/* Media query for mobile devices */
@media (max-width: 600px) {
  .admin-toolbar {
    flex-direction: column; /* Stack buttons vertically */
    align-items: stretch; /* Stretch buttons to full width */
    margin-left: 0; /* Adjust margins for smaller screens */
    gap: 10px; /* Reduce gap for a tighter layout */
  }

  .admin-toolbar__button {
    min-width: auto; /* Remove minimum width constraint */
    font-size: 1.5rem; /* Adjust font size for smaller screens */
    padding: 8px 16px; /* Adjust padding for smaller buttons */
  }
}
</style>
