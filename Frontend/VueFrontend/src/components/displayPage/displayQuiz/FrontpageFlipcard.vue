<script setup>
import { ref } from 'vue';

// Define props for title and description; we won't need imageUrl and imagePosition anymore.


const flipped = ref(false);

const toggleFlip = () => {
  flipped.value = !flipped.value;
};
</script>


<template>
    <div class="content-wrapper">
      <div class="card-container" @click="toggleFlip">
        <div :class="['card', { flipped: flipped }]">
          <div class="front">
            <!-- Display hardcoded text for the front -->
            <h5>Implement study cards within your quizzes</h5>
          </div>
          <div class="back">
            <!-- Display hardcoded text for the back -->
            <h5>Learning can be done even while being quizzed! </h5>
          </div>
        </div>
      </div>
      <div class="text-content">
        <h1>A quiz can be built up with many different types of questions</h1>
        <p>Create a mix of Multiple Choice, Fill in the Blank and Study Cards!</p>
      </div>
    </div>
</template>

<style scoped>
/* Your existing styles for .functionality, .content-wrapper, .text-content, etc. */


h5 {
  font-size: 3rem;
  font-weight: normal;
}

.content-wrapper {
  display: flex;
  flex-direction: row; /* Keep content side-by-side */
  justify-content: center;
  align-items: center;
  width: 100%; /* Ensure wrapper takes full width */
  gap: 20px; /* Space between elements */
}


.card-container {
  perspective: 1000px;
  min-width: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.card {
  width: 100%; /* Adjust based on layout needs */
  height: 80vh; /* Adjust based on layout needs */
  transform-style: preserve-3d;
  transition: transform 0.6s;
  cursor: pointer;
}

.card.flipped {
  transform: rotateY(180deg);
}

.front, .back {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 5px solid #BEE9E8;
  border-radius: 20px;
  background-color: #FFFFFF;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
}

.back {
  transform: rotateY(180deg);
  background-color: #BEE9E8;
}


.text-content {
  min-width: 100%; /* Use the full width for text content */
  padding: 20px; /* Provide some padding */
  text-align: center; /* Align text to the left */
  font-size: 1.3rem; /* Adjust font size as needed */
  margin-left: 8%;
}

@media (max-width: 868px) {
  .content-wrapper {
    flex-direction: column;
    text-align: center;
    max-height: 70vh;
  }

  .text-content {
    margin-left: 0;
  }

  .card-container {
    min-width: 100%;
    height: 50vh;
  }

  .card {
    width: 100%; /* Adjust based on layout needs */
    height: 40vh; /* Adjust based on layout needs */
    transform-style: preserve-3d;
    transition: transform 0.6s;
    cursor: pointer;
  }

.front, .back {
    font-size: 1rem;
  }


}
</style>
