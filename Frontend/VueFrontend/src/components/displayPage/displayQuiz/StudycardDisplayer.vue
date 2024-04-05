//StudycardDisplayer.vue

<template>
  <div class="card-container" @click="toggleFlip">
    <div :class="['card', { flipped: flipped }]">
      <div class="front">
        <div>
          <img v-if="question.frontImage" :src="question.frontImage" alt="Question Image" class="question-image">
        </div>
        <p>{{ question.text }}</p>
      </div>
      <div class="back">
        <img v-if="question.backImage" :src="question.backImage" alt="Question Image" class="question-image">
        <p>{{ question.answers[0].text }}</p>
      </div>
    </div>
  </div>
</template>

```vue
<script setup>
import { ref } from 'vue';

/**
 * Props for the StudycardDisplayer component
 * @property {Object} question - The question object
 */


/**
 * Boolean value to check if the card is flipped
 * @type {import('vue').Ref<boolean>}
 */
const flipped = ref(false);

/**
 * Toggles the flipped state of the card
 */
const toggleFlip = () => {
  flipped.value = !flipped.value;
};

</script>

<style scoped>
.card-container {
  perspective: 1000px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.card {
  width: 800px;
  height: 600px;
  transform-style: preserve-3d;
  transition: transform 0.6s;
  cursor: pointer;
}

.card.flipped {
  transform: rotateY(180deg);
}

.question-image {
  max-width: 100%;
  max-height: 300px;
  object-fit: contain;

  border-radius: 8px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.front,
.back {
  flex-direction: column;
  text-align: center;
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 10px solid #BEE9E8;
  border-radius: 20px;
  background-color: #FFFFFF;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  font-family: 'DM Sans', sans-serif;
  font-size: 2rem;
}

.back {
  transform: rotateY(180deg);
  background-color: #BEE9E8;
}
</style>
