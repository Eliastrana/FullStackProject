<script setup>
import { ref, computed, defineProps, defineEmits, watch } from 'vue'

const props = defineProps({
  uuid: String,
  text: String,
  answers: Array,
});
const emits = defineEmits(['submitData']);

const title = ref(props.text);
const answers = ref(props.answers);

function addAnswer() {
  answers.value.push({ text: '', correct: false });
}

const canAddMoreAnswers = computed(() => answers.value.length < 4);

// Whenever the title or answers change, emit the update to the parent.
watch([title, answers], () => {
  emits('submitData', {
    uuid: props.uuid,
    text: title.value,
    questionType: 'MULTIPLE_CHOICE',
    answers: answers.value,
  });
}, { deep: true });
</script>

<template>
  <div class="question-container">
    <input class="question-input" v-model="title" placeholder="Your question" />
    <div class="answer-group" v-for="(answer, index) in answers" :key="index">
      <input class="question-answer" v-model="answer.text" placeholder="Answer text" />
      <input class="question-checkbox" type="checkbox" v-model="answer.correct" />
    </div>
    <button id="add-answers" @click="addAnswer" :disabled="!canAddMoreAnswers">Add Answer</button>
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


.answer-group {
  display: flex;
  align-items: center;
  gap: 10px; /* Space between checkbox and input */
  border: none;
  border-radius: 10px;
}

.question-input{
  margin: 10px 0 30px 0;
  font-size: 20px;
  border: none;
  padding: 10px;
  border-radius: 10px;
}
.question-answer{
  margin: 0 0 15px 0;
  font-size: 15px;
  border: none;
  padding: 10px;
  border-radius: 10px;
}
.question-checkbox{
  margin: 0 0 15px 0;

  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  text-align: center;
}
#add-answers {
  padding: 10px 20px;
  border: none;
  border-radius: 10px;
  background-color: #FFD700;
  color: #171616;
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 10px;
}

</style>


