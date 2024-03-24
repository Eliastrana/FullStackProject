<script setup>
import { watch, ref, defineEmits } from 'vue';

const emits = defineEmits(['submitData']);

const studyCard = ref({
  question: '',
  answer: ''
});

// Enkel debounce-funksjon
function debounce(func, wait) {
  let timeout;
  return function(...args) {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      func.apply(this, args);
    }, wait);
  };
}

// Debounced funksjon som emitter studiekortdata
const emitDataDebounced = debounce(() => {
  emits('submitData', {
    type: 'study',
    question: studyCard.value.question,
    answer: studyCard.value.answer
  });
}, 5000); // 500ms ventetid for å forhindre for mange oppdateringer

// Bruker watch for å reagere på endringer i studyCard og kaller den debounced funksjonen
watch(studyCard, emitDataDebounced, { deep: true });
</script>



<template>
  <div class="question-container">
    <div class="question-box">
      <h3>Study Card</h3>
      <input v-model="studyCard.question" type="text" placeholder="Enter your question here" class="question-title"/>
      <textarea v-model="studyCard.answer" placeholder="Enter the answer here" class="answer-text"></textarea>
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

