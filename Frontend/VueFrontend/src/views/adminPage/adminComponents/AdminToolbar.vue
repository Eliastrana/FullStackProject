<script setup>
/**
 * Function to export all quizzes
 * Alerts the user that all quizzes are being exported
 */
const exportAllQuizzes = () => {
  alert('Exporting all quizzes...');
};

/**
 * Function to import a quiz
 * Alerts the user that a quiz is being imported
 */
const importQuiz = () => {
  alert('Importing quiz...');
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

@media (max-width: 600px) {

  .admin-toolbar {
    flex-direction: column;
    align-items: stretch;
    margin-left: 0;
    gap: 10px;
  }

  .admin-toolbar__button {
    min-width: auto;
    font-size: 1.5rem;
    padding: 8px 16px;
  }
}

</style>
