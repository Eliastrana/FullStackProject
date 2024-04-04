<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps({
  uuid: String,
  text: String,
  answers: Array
});
const emits = defineEmits(['submitData']);

const question = ref(props.text);
const answers = ref(props.answers);

watch([question, answers], () => {
  emits('submitData', {
    uuid: props.uuid,
    text: question.value,
    questionType: 'STUDY',
    answers: [{ text: answers.value, correct: true }]
  });
}, { deep: true });
</script>

<template>
  <div class="question-container">
    <input v-model="question" placeholder="Study card question" />
    <textarea v-model="answers[0].text" placeholder="Study card answer"></textarea>
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

