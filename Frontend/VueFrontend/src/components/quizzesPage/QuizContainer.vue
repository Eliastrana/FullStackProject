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
          <option v-for="difficulty in difficulties" :key="difficulty" :value="difficulty">{{ difficulty }}</option>
        </select>
        <select v-model="selectedCategory">
          <option value="">All Categories</option>
          <option v-for="(categoryName, id) in categories" :key="id" :value="id">{{ categoryName }}</option>
        </select>
      </div>
    </div>


    <div class="quiz-container">
      <div v-for="quiz in filteredQuizzes" :key="quiz.id" class="quiz-box" @click="handleQuizClick(quiz)">
        <img v-if="quiz.imageData" :src="quiz.imageData" alt="Quiz Image">
        <h2>{{ quiz.title }}</h2>
        <p>{{ quiz.description }}</p>
        <p class="category-badge">#{{ categories[quiz.categoryId] }}</p>
        <p class="category-badge">{{ quiz.difficulty }}</p>
      </div>
    </div>
  </div>
</template>


<script setup>
import { computed, defineEmits, onMounted, ref } from 'vue'
import { useStore } from 'vuex'
import { CategoryService } from '@/services/CategoryService.js'
import { DifficultyService } from '@/services/DifficultyService.js'

const store = useStore();
const emit = defineEmits(['select-quiz']);
const searchQuery = ref('');

const selectedDifficulty = ref('');
const selectedCategory = ref('');
const categories = ref({});
const difficulties = ref([]);

onMounted(async () => {
  await store.dispatch('quizzes/fetchAllQuizzes');
  await store.dispatch('quizzes/fetchQuizImages');
  const allCategories = await CategoryService.getAllCategories();
  categories.value = allCategories.reduce((acc, current) => {
    acc[current.id] = current.categoryName;
    return acc;
  }, {});

  difficulties.value = await DifficultyService.getAllDifficulties();
});


const quizzes = computed(() => store.state.quizzes.quizzes);

const filteredQuizzes = computed(() => {
  return quizzes.value.filter((quiz) => {
    return quiz.title.toLowerCase().includes(searchQuery.value.toLowerCase()) &&
      (selectedDifficulty.value === '' || quiz.difficulty === selectedDifficulty.value) &&
      (selectedCategory.value === '' || quiz.categoryId === selectedCategory.value);
  });
});

computed(() => {
  const difficulties = quizzes.value.map(quiz => quiz.difficulty).filter(Boolean);
  return Array.from(new Set(difficulties)).sort();
})

computed(() => Object.values(categories.value).sort())

function handleQuizClick(quiz) {
  emit('select-quiz', quiz);
}
</script>





<style scoped>

.top-container select {
  padding: 10px;
  margin: 10px 0;
  margin-right: 20px;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  border: none;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  cursor: pointer;
  width: auto; /* Adjusted to fit content */
}

.top-container select:focus {
  outline: none; /* Removes default focus outline */
  box-shadow: 0 0 0 2px #62B6CB; /* Adds a custom focus style */
}

#bottom-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 20px; /* Adjust as needed for spacing */
}

.controls-container {
  display: flex;
  flex-direction: row; /* Standard layout for større skjermer */
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}


.quiz-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* Endret til center for å sentrere boksene */
  gap: 20px; /* Bevarer mellomrommet mellom boksene */
}



.quiz-box {
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin: 10px; /* Gir en liten margin rundt hver boks */
  border-radius: 8px;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  flex-basis: calc((100% / 3) - 40px); /* For tre i bredden */

}

.category-badge {
  display: inline-block; /* Treat the <p> tag more like an inline element */
  background-color: #3232ff; /* Example background color */
  color: #ffffff; /* Text color */
  padding: 5px 15px; /* Vertical and horizontal padding */
  border-radius: 20px; /* Rounded corners */
  font-size: 0.8rem; /* Adjust font size as needed */
  margin: 0; /* Remove default <p> margin if needed */
}


.quiz-box:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
@media (max-width: 1024px) {
  .quiz-box {
    flex-basis: calc((100% / 2) - 30px); /* Justerer til to i bredden */
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
  gap: 10px;
}

@media (max-width: 768px) {
  .controls-container {
    flex-direction: column;
    align-items: center;
    margin-bottom: 20px;
  }

  .search-bar, .filters {
    width: 90%; /* Tilpasser bredden til mindre skjermer */
    margin: 0 10px 0 0; /* Legger til litt vertikal margin */
  }

  select, input[type="text"] {
    width: 100%; /* Sørger for at inputfelt og select-elementer tar opp hele bredden */
    box-sizing: border-box; /* Inkluderer padding og border i elementets totalbredde */
  }

  input[type="text"], select {
    width: 97%;
    padding: 15px 10px; /* Øker padding for bedre berøringsområde */
    font-size: 1.2rem; /* Øker fontstørrelsen for bedre lesbarhet */
    border: 1px solid #ddd; /* Legger til en grense for å gjøre feltet mer synlig */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* Legger til en boks-skygge for å fremheve feltet */
  }

    #bottom-container {
      flex-direction: column;
      align-items: stretch; /* Align items to stretch to full width */
    }

    .top-container select {
      width: 100%; /* Full width for smaller screens */
      margin: 10px 0; /* Adjust margin for vertical stacking */
    }

}
@media (max-width: 668px) {
  .quiz-box {
    flex-basis: calc((100% / 1) - 20px); /* Justerer til en i bredden */
  }
}
</style>


