<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';
import { useStore } from 'vuex';

const { uuid } = defineProps(['uuid']);
const emits = defineEmits(['submitData']);
const store = useStore();

const title = ref('');
const blankAnswer = ref('');

watch([title, blankAnswer], () => {
  emits('submitData', {
    uuid,
    type: 'fillInTheBlank',
    title: title.value,
    blankAnswer: blankAnswer.value,
  });
}, { deep: true });

// Initialize component with existing data if available
const existingQuestion = store.state.quizzes.questions.find(q => q.uuid === uuid);
if (existingQuestion) {
  title.value = existingQuestion.title;
  blankAnswer.value = existingQuestion.blankAnswer;
}
</script>

<template>
  <div class="question-container">
    <input v-model="title" placeholder="Question title" />
    <input v-model="blankAnswer" placeholder="Correct answer for the blank" />
  </div>
</template>

<style scoped>
.question-container {

  display: flex;
  flex-direction: column;
  align-content: center;
  padding: 20px;
  border-radius: 10px;
  border: none;
  min-width: 500px;
  max-width: 500px;
  background-color: #62B6CB;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'DM Sans', sans-serif;
  margin-right: 20px;
  margin-left: 20px;

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
  margin-right: 20px;

}
</style>
