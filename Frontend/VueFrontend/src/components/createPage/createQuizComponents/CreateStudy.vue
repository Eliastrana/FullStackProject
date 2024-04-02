<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';
import { useStore } from 'vuex';

const { uuid } = defineProps(['uuid']);
const emits = defineEmits(['submitData']);
const store = useStore();

const question = ref('');
const answer = ref('');

watch([question, answer], () => {
  emits('submitData', {
    uuid,
    type: 'study',
    question: question.value,
    answer: answer.value,
  });
}, { deep: true });

// Initialize component with existing data if available
const existingQuestion = store.state.quizzes.questions.find(q => q.uuid === uuid);
if (existingQuestion) {
  question.value = existingQuestion.question;
  answer.value = existingQuestion.answer;
}
</script>

<template>
  <div class="question-container">
    <input v-model="question" placeholder="Study card question" />
    <textarea v-model="answer" placeholder="Study card answer"></textarea>
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
  max-height: 200px;
  max-width: 500px;
  background-color: #BEE9E8;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'DM Sans', sans-serif;
}

.question-box {
  margin-bottom: 20px;
  margin-right: 20px;
}

.question-title, .answer-text {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border-radius: 4px;
  border: none;
  font-family: 'DM Sans', sans-serif;
}

.answer-text {
  min-height: 50px; /* Retained for the textarea */
  resize: none; /* Retained for the textarea */
}
</style>

