<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps({
  uuid: String,
  text: String,
  answers: Array // Assuming you are handling it in a similar way to multiple choice
});
const emits = defineEmits(['submitData']);

const title = ref(props.text);
const answers = ref(props.answers);

watch([title, answers], () => {
  console.log('Submitting data for fill in the blank question');
  emits('submitData', {
    uuid: props.uuid,
    text: title.value,
    questionType: 'FILL_IN_BLANK',
    answers: [{ text: answers.value, correct: true }]
  });
}, { deep: true });
</script>

<template>
  <div class="question-container">
    <input v-model="title" placeholder="Question title" />
    <input v-model="answers[0].text" placeholder="Correct answer for the blank" />
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
