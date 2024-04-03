// QuizContainer.vue
<template>
  <div>


    <div class="controls-container">
      <div class="search-bar">
        <input type="text" v-model="searchQuery" placeholder="Search quizzes...">
      </div>
      <div class="filters">
        <select v-model="selectedDifficulty">
          <option value="">All Difficulties</option>
          <option v-for="difficulty in uniqueDifficulties" :key="difficulty" :value="difficulty">{{ difficulty }}</option>
        </select>
        <select v-model="selectedCategory">
          <option value="">All Categories</option>
          <option v-for="category in uniqueCategories" :key="category" :value="category">{{ category }}</option>
        </select>
      </div>
    </div>



    <div class="quiz-container">
      <div v-for="quiz in filteredQuizzes" :key="quiz.id" class="quiz-box" @click="handleQuizClick(quiz)">
        <img :src="quiz.image" alt="Quiz Image">
        <h2>{{ quiz.title }}</h2>
        <p>{{ quiz.description }}</p>
        <p class="category-badge">#{{ quiz.category }}</p>
        <!-- Optionally display difficulty if present in your data -->
      </div>
    </div>
  </div>
</template>


<script setup>

import { ref, onMounted, computed } from 'vue';
import { defineEmits } from 'vue';

const emit = defineEmits(['select-quiz']);
const quizzes = ref([]);
const searchQuery = ref('');

const selectedDifficulty = ref('');
const selectedCategory = ref('');


// Assuming QuizContainer directly manages individual quiz items

const handleQuizClick = (quiz) => {
  emit('select-quiz', quiz);
};


const filteredQuizzes = computed(() => {
  return quizzes.value.filter((quiz) => {
    return quiz.title.toLowerCase().includes(searchQuery.value.toLowerCase()) &&
      (selectedDifficulty.value === '' || quiz.difficulty === selectedDifficulty.value) &&
      (selectedCategory.value === '' || quiz.category === selectedCategory.value);
  });
});


onMounted(async () => {
  try {
    const response = await fetch('mockJSON/testdata.json');
    if (response.ok) {
      quizzes.value = await response.json();

    } else {
      console.error('Failed to load testdata.json', response.status);
    }
  } catch (error) {
    console.error('Error while fetching testdata.json', error);
  }
});

const uniqueDifficulties = computed(() => {
  const difficulties = quizzes.value.map(quiz => quiz.difficulty);
  return Array.from(new Set(difficulties)).sort(); // Deduplicate and sort
});

const uniqueCategories = computed(() => {
  const categories = quizzes.value.map(quiz => quiz.category);
  return Array.from(new Set(categories)).sort(); // Deduplicate and sort
});



</script>



<style scoped>
.controls-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-bar, .filters {
  display: flex;
  flex-wrap: wrap; /* Allows content to wrap on smaller screens */
}

input[type="text"], select {
  padding: 10px;
  margin: 10px 0;
  border: none;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem; /* Keep font size reasonable on all devices */
}

.search-bar input[type="text"] {
  flex-grow: 1; /* Allows the search input to grow and fill available space */
  margin-right: 20px; /* Ensures spacing between the search bar and filters */
  margin-left: 20px;
}

.filters select {
  margin-right: 20px; /* Consistent spacing around filters */
}

.quiz-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  gap: 20px;
}

.quiz-box {
  flex: 0 0 calc(33.333% - 20px); /* Allows for 3 boxes per row, adjusting the gap */
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  background-color: #f9f9f9;
}

.quiz-box img {
  width: 100%;
  max-height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

h2, p {
  margin-top: 10px; /* Adjust spacing for readability */
}

.category-badge {
  background-color: #3232ff;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin-top: 10px; /* Adjust spacing for consistency */
}

/* Adjustments for smaller screens */
@media (max-width: 768px) {
  .quiz-box {
    flex: 0 0 calc(50% - 20px); /* Adjust to 2 boxes per row on medium screens */
  }
}

@media (max-width: 480px) {
  .controls-container {
    flex-direction: column;
    align-items: stretch; /* Stretch children to match parent's width */
  }

  .search-bar, .filters {
    width: 50%; /* Ensure full width for the search bar and filters container */
    justify-content: center; /* Center children if needed */
    margin: 0 10px; /* Add some margin on the left and right */
  }

  .search-bar input[type="text"], .filters select {
    width: 100%; /* Make input and select elements take up full width */
    margin: 10px 0; /* Adjust margin for vertical spacing */
  }

  .quiz-box {
    flex-basis: 100%; /* Make quiz box take full width */
    margin-left: 2%; /* Adjust margins as needed for consistent spacing */
    margin-right: 2%;

  }
}
</style>



