// MultiplechoiceQuizDisplayer.vue
<script setup>
import { ref, onMounted, defineProps } from 'vue';
import MultiplechoiceDisplayer from '@/components/displayPage/displayQuiz/MultiplechoiceDisplayer.vue';

// Step 1: Define props to receive the JSON path
const props = defineProps({
  jsonPath: String,
});

const quizTitle = ref('');
const questions = ref([]);
const currentQuestionIndex = ref(0);
const score = ref(0);

// Step 2: Use the prop for fetching data
onMounted(async () => {
  try {
    const response = await fetch(props.jsonPath);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    quizTitle.value = data.title;
    questions.value = data.questions;
  } catch (error) {
    console.error("Fetching error:", error.message);
    // Optionally, update your UI to reflect the error
  }
});


const handleAnswer = ({ isCorrect }) => {
  if (isCorrect) {
    score.value++;
  }
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  } else {
    alert(`Quiz completed! Your score: ${score.value}/${questions.value.length}`);
  }
};

const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  }
};

const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;
  }
};
</script>


<template>
  <div class="quiz-container">
    <h1>{{ quizTitle }}</h1>
    <div v-if="questions.length > 0">
      <button @click="prevQuestion" :disabled="currentQuestionIndex === 0">
        Previous
      </button>
      <MultiplechoiceDisplayer
        :key="questions[currentQuestionIndex].id"
        :question="questions[currentQuestionIndex].questionText"
        :options="questions[currentQuestionIndex].options"
        :correctIndex="questions[currentQuestionIndex].correctIndex"
        @answer="handleAnswer"
      />
      <button @click="nextQuestion" :disabled="currentQuestionIndex === questions.length - 1">
        Next
      </button>
    </div>
  </div>
</template>



<style scoped>

.quiz-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  width: 100%; /* Ensure the container takes full width */
  justify-content: center; /* Center content vertically */
}

.answers-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  width: 100%;
  max-width: 400px; /* Adjust this if necessary */
  justify-content: center; /* Center grid items horizontally */
  margin: auto; /* This helps in centering the grid itself if it's smaller than its container */
}



.question-title {
  margin-bottom: 20px;
}



.answer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 150px;
  width: 400px;
  background-color: #CAE9FF;
  border-radius: 10px;
  font-family: 'DM Sans', sans-serif;
  font-size: 2rem;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  position: relative; /* Added for pseudo-elements */
}

.answer:hover {
  //background-color: #f0f0f0;
  transform: translateY(-2px);
}

.selected {
}

.correct {
  background-color: #CAE9FF;


}

.incorrect {
  background-color: #e7e7e7;

}

.correct::before,
.incorrect::before {
  content: '';
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 20px;
}

.correct::before {
  content: '✓';
  color: white; /* Adjust as needed for visibility */
}

.incorrect::before {
  content: '✕';
  color: white; /* Adjust as needed for visibility */
}

.answer:active {
  transform: translateY(0);
}


.button {
  align-content: center;
  text-align: center;
  padding: 10px 20px;
  background-color: #BEE9E8;
  color: #000000;
  border: none;
  border-radius: 40px;
  cursor: pointer;
  min-width: 200px;
  margin: 0 5px;
  font-size: 2rem;
  font-family: 'DM Sans', sans-serif;
}

.button:hover {
  background-color: #A2D2CE;
}

.buttons-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  margin-left: 100px;
  margin-right: 100px;
}
</style>
