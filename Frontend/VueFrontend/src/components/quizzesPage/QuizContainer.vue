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
        <img :src="quiz.imageData || '/images/default.png'" alt="Quiz Image" />
        <h2>{{ quiz.title }}</h2>
        <p>{{ quiz.description }}</p>
        <div class="quiz-info">
          <p class="category-badge">#{{ categories[quiz.categoryId] }}</p>
          <p :class="['difficulty-badge', difficultyClass(quiz.difficulty)]">{{ quiz.difficulty }}</p>
        </div>
        <StarRating :rating="quiz?.ratings.average" />
      </div>
    </div>
  </div>
  <button v-if="filteredQuizzes.length < quizzes.length" @click="viewMore" class="view-more">View More</button>


</template>


<script setup>
import { computed, defineEmits, onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import { CategoryService } from '@/services/CategoryService.js'
import { DifficultyService } from '@/services/DifficultyService.js'
import { TagService } from '@/services/TagService.js'
import { QuestionService } from '@/services/QuestionService.js'
import StarRating from '@/components/util/StarRating.vue'

/**
 * Emits custom events
 * @type {Function}
 */
const store = useStore();
const emit = defineEmits(['select-quiz']);
const searchQuery = ref('');
const visibleQuizzesCount = ref(12);
const selectedDifficulty = ref('');
const selectedCategory = ref('');
const categories = ref({});
const difficulties = ref([]);
const allTags = ref([]);
const quizToTagsMap = ref({});

/**
 * Fetches all quizzes, quiz images, quiz ratings, tags, categories, and difficulties
 */

onMounted(async () => {
  await store.dispatch('quizzes/fetchAllQuizzes');
  await store.dispatch('quizzes/fetchQuizImages');
  await store.dispatch('quizzes/fetchAllQuizRatings');
  allTags.value = await TagService.getAllTags();

  const allCategories = await CategoryService.getAllCategories();
  categories.value = allCategories.reduce((acc, current) => {
    acc[current.id] = current.categoryName;
    return acc;
  }, {});

  difficulties.value = await DifficultyService.getAllDifficulties();

  for (const quiz of store.state.quizzes.quizzes) {
    const questions = await QuestionService.getQuestionsByQuizId(quiz.id);
    const tagsForQuiz = allTags.value.filter(tag =>
      tag.questionIds.some(id => questions.map(q => q.id).includes(id))
    );
    quizToTagsMap.value[quiz.id] = tagsForQuiz.map(t => t.name);
  }
});

/**
 * Computed properties
 */

const quizzes = computed(() => store.state.quizzes.quizzes);

/**
 * Filters quizzes based on search query, difficulty, and category
 */

const filteredQuizzes = computed(() => {
  const searchLower = searchQuery.value.toLowerCase();
  const limitedQuizzes = quizzes.value.filter(quiz => {
    const isPublic = quiz.isPublic;
    const titleMatch = quiz.title.toLowerCase().includes(searchLower);
    const tagMatch = quizToTagsMap.value[quiz.id]?.some(tagName => tagName.toLowerCase().includes(searchLower));
    const difficultyMatch = selectedDifficulty.value === '' || quiz.difficulty === selectedDifficulty.value;
    const categoryMatch = selectedCategory.value === '' || quiz.categoryId === selectedCategory.value;

    return isPublic && (titleMatch || tagMatch) && difficultyMatch && categoryMatch;
  }).slice(0, visibleQuizzesCount.value);

  return limitedQuizzes;
});


/**
 * Function to view more quizzes
 */
function viewMore() {
  visibleQuizzesCount.value += 12;
}

/**
 * Handles the quiz click event
 * @param {Object} quiz - The quiz object
 */

function handleQuizClick(quiz) {
  emit('select-quiz', quiz);
}


/**
 * Returns the class name for the difficulty badge
 * @param {string} difficulty - The difficulty level
 * @returns {string} - The class name
 */
function difficultyClass(difficulty) {
  switch (difficulty.toLowerCase()) {
    case 'easy':
      return 'difficulty-easy';
    case 'medium':
      return 'difficulty-medium';
    case 'hard':
      return 'difficulty-hard';
    default:
      return '';
  }
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
  width: auto;
}

.top-container select:focus {
  outline: none;
  box-shadow: 0 0 0 2px #62B6CB;
}

#bottom-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
}

.controls-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.quiz-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
}

.quiz-box {
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin: 10px;
  border-radius: 8px;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  flex-basis: calc((100% / 3) - 40px);
}

.category-badge {
  display: inline-block;
  background-color: #3232ff;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 0;
}

.quiz-box:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
@media (max-width: 1024px) {
  .quiz-box {
    flex-basis: calc((100% / 2) - 30px);
  }
}


h2 {
  font-size: 2rem;
  margin-bottom: 10px;
}

img {
  object-fit: cover;
  border-radius: 8px;
  width: 100%;
  max-height: 200px;
}

h2, p {
  display: -webkit-box;
  -webkit-line-clamp: 2;
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
    width: 90%;
    margin: 0 10px 0 0;
  }

  select, input[type="text"] {
    width: 100%;
    box-sizing: border-box;
  }

  input[type="text"], select {
    width: 97%;
    padding: 15px 10px;
    font-size: 1.2rem;
    border: 1px solid #ddd;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  }

    #bottom-container {
      flex-direction: column;
      align-items: stretch;
    }

    .top-container select {
      width: 100%;
      margin: 10px 0;
    }

}

.category-badge {
  display: inline-block;
  background-color: #007bff;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 0;
}

.difficulty-easy {
  background-color: #28a745;
    display: inline-block;
    color: #ffffff;
    padding: 5px 15px;
    border-radius: 20px;
    font-size: 0.8rem;
    margin: 5px 0;
}

.difficulty-medium {
  background-color: #f3dc5e;
  display: inline-block;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 5px 0;
}

.difficulty-hard {
  background-color: #dc3545;
  display: inline-block;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 5px 0;
}

.average-rating {
  margin-top: 10px;
  font-weight: bold;
}

.quiz-info {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;
}

.view-more {
  padding: 10px 20px;
  margin: 20px auto;
  display: block;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.view-more:hover {
  background-color: #0056b3;

}


@media (max-width: 668px) {
  .quiz-box {
    flex-basis: calc((100% / 1) - 20px);
  }
}
</style>


