// StudycardsQuizDisplayer.vue

<script setup>
import { ref, onMounted } from 'vue';
import StudycardDisplayer from '@/components/displayPage/displayQuiz/StudycardDisplayer.vue'

/**
 * Title of the quiz
 * @type {import('vue').Ref<string>}
 */
const quizTitle = ref('');

/**
 * Array of study cards
 * @type {import('vue').Ref<Array>}
 */
const studyCards = ref([]);

/**
 * Index of the current card
 * @type {import('vue').Ref<number>}
 */
const currentCardIndex = ref(0);

/**
 * Boolean value to check if the card is flipped
 * @type {import('vue').Ref<boolean>}
 */
const flipped = ref(false);

onMounted(() => {
  /**
   * On component mount, fetch quiz data
   */
  fetch('mockJSON/onlyOneQuestionType/frontpageshowoff.json')
    .then(response => response.json())
    .then(data => {
      quizTitle.value = data.title;
      studyCards.value = data.questions;
    });
});

/**
 * Function to navigate to the next card
 */
const nextCard = () => {
  if (currentCardIndex.value < studyCards.value.length - 1) {
    currentCardIndex.value++;
    flipped.value = false;
  }
};

/**
 * Function to navigate to the previous card
 */
const prevCard = () => {
  if (currentCardIndex.value > 0) {
    currentCardIndex.value--;
    flipped.value = false;
  }
};

/**
 * Function to toggle the flip state of the card
 */
const toggleFlip = () => {
  flipped.value = !flipped.value;
};
</script>

<template>
  <div class="quiz-container">
    <h1>{{ quizTitle }}</h1>
    <div v-if="studyCards.length > 0">
      <div class="buttons-container">
        <button class="button" @click="prevCard" :disabled="currentCardIndex === 0">Previous</button>
        <StudycardDisplayer
          :key="studyCards[currentCardIndex].id"
          :question="studyCards[currentCardIndex]"
          @click.native="toggleFlip" />
        <button class="button" @click="nextCard" :disabled="currentCardIndex === studyCards.length - 1">Next</button>
      </div>
    </div>
    <div class="navigation-dots">
      <span
        v-for="(card, index) in studyCards"
        :key="card.id"
        @click="() => { currentCardIndex = index; flipped.value = false; }"
        :class="{ active: currentCardIndex === index }">
        {{ index + 1 }}
      </span>
    </div>
  </div>
</template>

<style scoped>

h1 {
  text-align: center;
  font-size: 3rem;
  margin-bottom: -80px;
}

.button {
  align-content: center;
  text-align: center;
  padding: 10px 20px;
  background-color: #BEE9E8;
  color: #000000;
  border: none;
  border-radius: 40px;
  cursor: pointer;
  min-width: 200px;
  margin: 0 5px;
  font-size: 2rem;
  font-family: 'DM Sans', sans-serif;
}

.button:hover {
  background-color: #A2D2CE;
}

.buttons-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  margin-left: 100px;
  margin-right: 100px;
}

.studycard-displayer-wrapper {
  flex-grow: 1;
  display: flex;
  justify-content: center;
}

.button {
  margin: 0 10px;
}

.navigation-dots {
  display: flex;
  justify-content: center;
  margin-top: 5px;
}

</style>
