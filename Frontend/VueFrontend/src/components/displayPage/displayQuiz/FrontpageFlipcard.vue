<script setup>
import { ref } from 'vue';

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
            <h5 id="CardText">Implement study cards within your quizzes</h5>
          </div>
          <div class="back">
            <h5 id="CardText">Learning can be done even while being quizzed! </h5>
          </div>
        </div>
      </div>
      <div class="text-content">
        <h1 id="flip-explained" >A quiz can be built up with many different types of questions</h1>
        <p>Create a mix of Multiple Choice, Fill in the Blank and Study Cards!</p>
      </div>
    </div>
</template>

<style scoped>


h5 {
  font-weight: normal;
  font-size: clamp(1.9rem, 5.6vw, 4rem);
}

.content-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 20px;
}


.card-container {
  perspective: 1000px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
}

.card {
  width: clamp(40vh, 50vw, 70vh);
  height: clamp(40vh, 45vw, 70vh);
  transform-style: preserve-3d;
  transition: transform 0.6s, box-shadow 0.3s;
  cursor: pointer;
  position: relative;
  border-radius: 20px;
  box-shadow: 0 8px 16px rgba(0,0,0,0.1);
}

.card.flipped {
  transform: rotateY(180deg);
}
.card:not(.flipped):hover {
  transform: rotateY(5deg);
  box-shadow: 0 12px 24px rgba(0,0,0,0.2);
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
  min-width: 100%;
  padding: 20px;
  text-align: center;
  font-size: 1.3rem;
}
#CardText{
  font-size: clamp(0.5rem, 3.6vw, 3rem);
}
#flip-explained{
  font-size: clamp(1.7rem, 3.2vw, 5rem);
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
    width: 100%;
    height: 40vh;
    transform-style: preserve-3d;
    transition: transform 0.6s;
    cursor: pointer;
  }

.front, .back {
    font-size: 1rem;
  }

}
</style>
