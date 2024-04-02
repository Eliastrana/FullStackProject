<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';
import { useStore } from 'vuex';

const { uuid } = defineProps(['uuid']);
const emits = defineEmits(['submitData']);
const store = useStore();

const title = ref('');
const answers = ref([{ text: '', isCorrect: false }]);

function addAnswer() {
  answers.value.push({ text: '', isCorrect: false });
}

watch([title, answers], () => {
  emits('submitData', {
    uuid,
    type: 'multipleChoice',
    title: title.value,
    answers: answers.value.map(answer => ({ text: answer.text, isCorrect: !!answer.isCorrect })),
  });
}, { deep: true });

// Initialize component with existing data if available
const existingQuestion = store.state.quizzes.questions.find(q => q.uuid === uuid);
if (existingQuestion) {
  title.value = existingQuestion.title;
  answers.value = existingQuestion.answers;
}
</script>

<template>
  <div class="question-container">
    <input v-model="title" placeholder="Question title" />
    <div v-for="(answer, index) in answers" :key="index">
      <input v-model="answer.text" placeholder="Answer text" />
      <input type="checkbox" v-model="answer.isCorrect" /> Correct
    </div>
    <button @click="addAnswer">Add Answer</button>
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


