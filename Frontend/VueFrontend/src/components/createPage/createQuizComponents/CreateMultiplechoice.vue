<script setup>
import { ref, watch, onMounted } from 'vue';
import { useStore } from 'vuex';
import { debounce } from 'lodash-es';

const store = useStore();
const title = ref('');
const answers = ref([{ text: '' }, { text: '' }, { text: '' }, { text: '' }]);
const correctAnswer = ref(null);

// Definer debouncedUpdateStore utenfor onMounted for å unngå å re-definere den ved hver komponentoppdatering
const debouncedUpdateStore = debounce(() => {
  store.dispatch('quizComponents/updateCurrentQuiz', {
    title: title.value,
    answers: answers.value,
    correctAnswer: correctAnswer.value,
  });
}, 500); // Debounce for å begrense hyppigheten av oppdateringer

watch([title, answers, correctAnswer], debouncedUpdateStore, { deep: true });

onMounted(() => {
  // Hent den lagrede quiz-tilstanden fra Vuex når komponenten monteres
  const currentQuiz = store.state.quizzes.currentQuiz;
  if (currentQuiz) {
    title.value = currentQuiz.title;
    answers.value = currentQuiz.answers.length > 0 ? currentQuiz.answers : answers.value;
    correctAnswer.value = currentQuiz.correctAnswer;
  }
});
</script>

<template>
  <div class="question-container">
    <h3>Multiple Choice</h3>
    <div class="question-box">
      <input v-model="title" type="text" placeholder="Enter your question here" class="question-title"/>
      <div class="answers-container">
        <div class="answer-row" v-for="(answer, index) in answers" :key="index">
          <input v-model="answer.text" type="text" :placeholder="'Answer ' + (index + 1)" class="answer-text"/>
          <input type="radio" :value="index" v-model="correctAnswer" class="correct-answer-checkbox"/>
        </div>
      </div>
    </div>
  </div>
</template>




<style scoped>
.question-container {
  display: flex;
  flex-direction: column;
  padding: 20px;
  border-radius: 8px;
  border: none;
  min-width: 500px;
  max-width: 500px;
  background-color: #CAE9FF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'DM Sans', sans-serif;
}

.question-box {
  margin-right: 20px;
}

.question-title {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border-radius: 10px;
  border: none;
  font-family: 'DM Sans', sans-serif;


}

.answers-container {
  display: flex;
  flex-direction: column;
  gap: 10px; /* Space between rows */
  font-family: 'DM Sans', sans-serif;
  border: none;
  border-radius: 10px;



}

.answer-row {
  display: flex;
  justify-content: space-between;
  border: none;

}

.answer-group {
  display: flex;
  align-items: center;
  gap: 10px; /* Space between checkbox and input */
  border: none;
  border-radius: 10px;


}

.answer-text {
  flex-grow: 1;
  padding: 8px;
  border: none;
  border-radius: 10px;

}

.correct-answer-checkbox {
  /* Adjustments might not be necessary, but ensure they align well */
}
</style>


