<script setup>
import { ref, } from 'vue';
import CreateMultiplechoice from '@/components/createPage/createQuizComponents/CreateMultiplechoice.vue';
import CreateFillintheblank from '@/components/createPage/createQuizComponents/CreateFillintheblank.vue';
import CreateStudy from '@/components/createPage/createQuizComponents/CreateStudy.vue';

const quizTypes = ref([
  { id: 'multipleChoice', name: 'Multiple Choice', color: "#CAE9FF" },
  { id: 'fillInTheBlank', name: 'Fill in the Blank', color: "#62B6CB" },
  { id: 'study', name: 'Study', color: "#BEE9E8" }
]);

// Changed to store a list of selected types
const selectedQuizTypes = ref([]);

function addQuizType(quizTypeId) {
  selectedQuizTypes.value.push(quizTypeId);
}


function getComponentName(quizTypeId) {
  switch (quizTypeId) {
    case 'multipleChoice':
      return CreateMultiplechoice;
    case 'fillInTheBlank':
      return CreateFillintheblank;
    case 'study':
      return CreateStudy;
    default:
      return null; // or a default component
  }
}

function getQuizTypeColor(quizTypeId) {
  const quizType = quizTypes.value.find(type => type.id === quizTypeId);
  return quizType ? quizType.color : 'transparent'; // Default to 'transparent' if not found
}


const quizData = ref([]);

function handleQuizData(data) {
  const existingIndex = quizData.value.findIndex(item => item.question === data.question);
  if (existingIndex !== -1) {
    // Overskriv eksisterende oppføring for å unngå duplikater
    quizData.value[existingIndex] = data;
  } else {
    // Legg til ny data
    quizData.value.push(data);
  }
}

async function compileQuizToJson() {
  // Anta at quizData.value allerede inneholder all data korrekt samlet fra barnekomponentene
  const quizJson = JSON.stringify(quizData.value, null, 2);
  console.log(quizJson);
  downloadQuizJson(quizJson); // Utløser nedlasting av JSON-filen
}


function downloadQuizJson(quizJson) {
  const blob = new Blob([quizJson], { type: 'application/json' });
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = 'quizData.json';
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}
</script>

<template>
  <div class="quiz-container">
    <div class="quiz-type-selector">
      <h2>Choose a quiz type:</h2>
      <div class="quiz-type-buttons">
        <button
            v-for="quizType in quizTypes"
            :key="quizType.id"
            @click="addQuizType(quizType.id)"
            :style="{ backgroundColor: quizType.color }"
            class="quiz-type-button"
        >
          + {{ quizType.name }}
        </button>
      </div>
    </div>

    <h2>Active Questions:</h2>

    <!-- Container for quiz components with dynamic border color -->
    <div
        v-for="(quizTypeId, index) in selectedQuizTypes"
        :key="`quiz-${index}`"
        class="quiz-component-container"
        :style="{ borderColor: getQuizTypeColor(quizTypeId) }"
    >
      <component
          :is="getComponentName(quizTypeId)"
          @submitData="handleQuizData"
      />
    </div>

    <button class="compileButton" @click="compileQuizToJson">Compile Quiz to JSON</button>
  </div>
</template>




<style scoped>

body {
  font-family: 'DM Sans', sans-serif;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f0f0;
}

h2 {
  font-size: 2rem;
  margin-bottom: 20px;
}

.quiz-type-selector .quiz-type-buttons .quiz-type-button {
  padding: 10px 40px;
  border: none;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.2s;
  cursor: pointer;
  color: #171616; /* Ensure text is readable on colored backgrounds */
  font-family: 'DM Sans', sans-serif;
  font-size: 3rem;
  margin-right: 10px;
  text-align: center;
  //margin-bottom: 40px;

}

.quiz-type-button:hover {
  transform: translateY(-2px);
}

.quiz-component-container {
  padding: 10px;
  border-radius: 8px;
}

.quiz-container {
  display: flex;
  align-items: center;
  height: 100vh;
  width: 100vw;
  flex-direction: column;

}

.compileButton {
  padding: 10px 20px;
  border: none;
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.2s;
  cursor: pointer;
  color: #ffffff; /* Ensure text is readable on colored backgrounds */
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  margin-right: 10px;
  text-align: center;
  margin-top: 20px;
  margin-bottom: 40px;
  background-color: #007bff;
}

.compileButton:hover {
  transform: translateY(-2px);
  background-color: #47a0d5;
}
</style>