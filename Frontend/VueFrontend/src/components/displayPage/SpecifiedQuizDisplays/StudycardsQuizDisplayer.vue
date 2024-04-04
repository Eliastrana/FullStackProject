// StudycardsQuizDisplayer.vue
<script setup>
import { ref, onMounted } from 'vue';
import StudycardDisplayer from '@/components/displayPage/displayQuiz/StudycardDisplayer.vue'

const quizTitle = ref('');
const studyCards = ref([]);
const currentCardIndex = ref(0);
const flipped = ref(false);

onMounted(() => {
  fetch('mockJSON/onlyOneQuestionType/frontpageshowoff.json')
    .then(response => response.json())
    .then(data => {
      quizTitle.value = data.title; // Access the title
      studyCards.value = data.questions; // Correctly assign the questions array
    });
});


const nextCard = () => {
  if (currentCardIndex.value < studyCards.value.length - 1) {
    currentCardIndex.value++;
    flipped.value = false;
  }
};

const prevCard = () => {
  if (currentCardIndex.value > 0) {
    currentCardIndex.value--;
    flipped.value = false;
  }
};

const toggleFlip = () => {
  flipped.value = !flipped.value;
};
</script>

<template>
  <div class="quiz-container">
    <h1>{{ quizTitle }}</h1>

    <div v-if="studyCards.length > 0">
      <!-- Flex container for navigation buttons and card display -->
      <div class="buttons-container">
        <button class="button" @click="prevCard" :disabled="currentCardIndex === 0">Previous</button>

        <StudycardDisplayer
          :key="studyCards[currentCardIndex].id"
          :question="studyCards[currentCardIndex]"
          @click.native="toggleFlip"
        />

        <button class="button" @click="nextCard" :disabled="currentCardIndex === studyCards.length - 1">Next</button>
      </div>
    </div>
    <div class="navigation-dots">
      <span
        v-for="(card, index) in studyCards"
        :key="card.id"
        @click="() => { currentCardIndex = index; flipped.value = false; }"
        :class="{ active: currentCardIndex === index }"
      >
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
  justify-content: space-between; /* Aligns children to each side */
  align-items: center;
  gap: 10px; /* Adjust this value to manage space between flex items */
  margin-left: 100px;
  margin-right: 100px;

}

/* Style adjustments to ensure the card displayer is centered if needed */
/* For example, if StudycardDisplayer is not naturally centered in its flex item */
.studycard-displayer-wrapper {
  flex-grow: 1;
  display: flex;
  justify-content: center;
}

.button {
  /* Existing button styles */
  margin: 0 10px; /* Adjust margin as needed */
}



.navigation-dots {
  display: flex;
  justify-content: center;
  margin-top: 5px;

}

</style>
