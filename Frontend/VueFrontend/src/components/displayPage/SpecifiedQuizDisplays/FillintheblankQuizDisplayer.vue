// FillintheblankQuizDisplayer.vue
<template>
  <div class="quiz-container">
    <h1>Fill in the Blank Quiz</h1>
    <div v-if="questions.length > 0">
      <FillintheblankDisplayer :question="questions[currentQuestionIndex]" />
      <div class="navigation">
        <button @click="goToPrevious()" :disabled="currentQuestionIndex === 0">Previous</button>
        <button @click="goToNext()" :disabled="currentQuestionIndex === questions.length - 1">Next</button>
      </div>
      <div class="question-navigation">
        <span v-for="(question, index) in questions" :key="question.id" class="navigation-dot" :class="{active: currentQuestionIndex === index}" @click="goToQuestion(index)">
          {{ index + 1 }}
        </span>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import FillintheblankDisplayer from '@/components/displayPage/displayQuiz/FillintheblankDisplayer.vue';

const questions = ref([]);
const currentQuestionIndex = ref(0);

const goToNext = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  }
};

const goToPrevious = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;
  }
};

const goToQuestion = (index) => {
  currentQuestionIndex.value = index;
};

onMounted(async () => {
  try {
    const response = await fetch('mockJSON/onlyOneQuestionType/fillintheblank.json');
    if (!response.ok) throw new Error('Failed to load');
    const data = await response.json();
    questions.value = data.filter(q => q.Type === "Fill in the Blank");
  } catch (error) {
    console.error("Error loading fill-in-the-blank questions:", error);
  }
});
</script>


<style scoped>
.quiz-container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  text-align: center;
}
.navigation {
  margin-top: 20px;
}

.navigation-dot {
  display: inline-block;
  cursor: pointer;
  padding: 5px;
  margin-right: 5px;
  background-color: #eee;
}

.navigation-dot.active {
  background-color: #333;
  color: white;
}


/* Add additional styling as needed */
</style>
