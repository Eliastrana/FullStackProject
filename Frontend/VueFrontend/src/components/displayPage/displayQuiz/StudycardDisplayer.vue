//StudycardDisplayer.vue

<template>
  <div class="card-container" @click="toggleFlip">
    <div :class="['card', { flipped: flipped }]">
      <div class="front">
        <div>
          <img v-if="question.frontImage" :src="question.frontImage" alt="Question Image" class="question-image">
        </div>
        <!-- Update to use question.front -->
        <p>{{ question.front }}</p>

      </div>

      <div class="back">
        <!-- Update to use question.back -->
        <img v-if="question.backImage" :src="question.backImage" alt="Question Image" class="question-image">
        <p>{{ question.back }}</p>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';

const props = defineProps({
  question: {
    type: Object,

  }
});

const flipped = ref(false);

const toggleFlip = () => {
  flipped.value = !flipped.value;
};

// Debugging
console.log(props.question);
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

  flex-direction: column; /* Stack image and text vertically */
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

.front {
  /* Front side styling */
}

.back {
  transform: rotateY(180deg);
  background-color: #BEE9E8;

  /* Back side styling */
}
</style>
