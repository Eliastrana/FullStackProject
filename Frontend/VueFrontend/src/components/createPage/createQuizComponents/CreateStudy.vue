<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';
import CreateTags from '@/components/createPage/createQuizComponents/CreateTags.vue';
import store from '@/store/index.js'

/**
 * Props for the CreateStudy component
 * @property {String} uuid - The unique identifier for the question
 * @property {String} text - The text of the question
 * @property {Array} answers - The array of answers
 */
const props = defineProps({
  uuid: String,
  text: String,
  answers: Array,
  tags: Array
});

/**
 * Emits custom events
 * @type {Function}
 */
const emits = defineEmits(['submitData', 'removeQuestion']);

/**
 * The question text
 * @type {import('vue').Ref<string>}
 */
const question = ref(props.text);

/**
 * The answers for the question
 * @type {import('vue').Ref<Array>}
 */
const answers = ref(props.answers);
const tags = ref(props.tags || []);

function handleTagUpdate(newTags) {
  store.dispatch('quizzes/updateQuestionTags', {
    uuid: props.uuid,
    newTags
  });
}


/**
 * Watches for changes in the question and answers and emits the updated data
 */
watch([question, answers], () => {
  emits('submitData', {
    uuid: props.uuid,
    text: question.value,
    questionType: 'STUDY',
    answers: answers.value.map(answer => ({ text: answer.text, correct: answer.correct })),
    tags: tags.value,
  });
}, { deep: true });

/**
 * Emits the 'removeQuestion' event with the question's uuid
 */
function removeQuestion() {
  emits('removeQuestion', props.uuid);
}
</script>


<template>
  <div class="question-container">
    <h2>Study Card</h2>
    <div class="remove-icon" @click="removeQuestion">
      <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41l5.59 5.59L5 17.59 6.41 19l5.59-5.59L17.59 19 19 17.59l-5.59-5.59L19 6.41z" />
      </svg>
    </div>
    <input class="question-title" v-model="question" placeholder="Study card question" />
    <textarea class="answer-text" v-model="answers[0].text" placeholder="Study card answer"></textarea>

    <CreateTags :initialTags="tags" @update-tags="handleTagUpdate" />

  </div>
</template>

<style scoped>

.remove-icon {
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  color: #000000;
  z-index: auto;
}

.remove-icon svg {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.question-container {
  position: relative;
}

.question-container {
  position: relative;
  display: flex;
  flex-direction: column;
  padding: 20px;
  padding-top: 40px;
  border-radius: 8px;
  border: none;
  min-width: 500px;
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
  font-size: 1rem;

  border-radius: 8px;
  border: none;
  font-family: 'DM Sans', sans-serif;
}

.answer-text {
  min-height: 50px;
  resize: none;
}


.uploadimagebutton {
  display: inline-block;
  margin: 20px 0;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.uploadimagebutton:hover {
  transform: translateY(-2px);
  background-color: #47a0d5;
}


.image-preview img {
  max-width: 100%;
  max-height: 200px;
  object-fit: cover;
}

.image-preview {
  position: relative;
  margin: 20px 0;
}

.remove-image {
  max-width: 100%;
  position: absolute;
  top: 0;
  right: 0;
  background-color: rgba(0,0,0,0.6);
  color: white;
  padding: 0 5px;
  cursor: pointer;
  border-radius: 0 0 0 10px;
}

.remove-image:hover {
  background-color: rgba(0,0,0,0.8);
}



</style>
