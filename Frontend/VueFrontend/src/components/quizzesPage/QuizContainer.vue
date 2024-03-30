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
        <p><strong>Category:</strong> {{ quiz.category }}</p>
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

.quiz-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start; /* Adjust alignment if necessary */
  gap: 20px; /* Adjust based on your design */
}



.quiz-box {
  flex: 0 0 calc((100% / 3) - 30px); /* Adjust calculation for 3 in a row */
  /* Explanation for calculation:
     100% / 3 = 33.33% for each box to take one-third of the container's width.
     Subtract a bit more space for the gap to ensure they fit.
     The exact value may need tweaking based on the total gap size and any margins. */
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-left: 12px;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-sizing: border-box; /* Includes padding and border in the element's total width/height */
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  height: auto; /* Allows box height to grow with content */
}




.quiz-box:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Media query for larger screens */
@media (max-width: 800px) {
  .quiz-box {
    flex: 0 0 100%; /* Full width for smaller screens */
    margin-right: 20px;
    margin-left: 20px;
  }
}

h2 {
  font-size: 2rem;
  margin-bottom: 10px;
}

img {
  object-fit: cover;
  border-radius: 8px;

  width: 100%; /* Ensure the image takes up the full width */
  max-height: 200px; /* Set a max-height   to prevent images from stretching */

}

h2, p {
  display: -webkit-box;
  -webkit-line-clamp: 2; /* Adjust number of lines for h2, and separately for p */
  -webkit-box-orient: vertical;
  overflow: hidden;
}


input[type="text"], select {
  padding: 10px;
  margin: 10px 0;
  margin-right: 20px;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  border: none;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}


select {
  cursor: pointer;
}

.search-bar {
  flex: 1;
  margin-left: 20px;
  border: none;
}

.filters {
  display: flex;
  gap: 10px; /* Space between filters */
}



</style>


