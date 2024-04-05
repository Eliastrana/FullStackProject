<script setup>
import FrontpageFlipcard from '@/components/displayPage/displayQuiz/FrontpageFlipcard.vue'

/**
 * Props for the FlipcardDisplayer component
 * @property {string} title - The title of the functionality
 * @property {string} description - The description of the functionality
 * @property {string} imageUrl - The URL of the image to display
 * @property {string} [imagePosition='left'] - The position of the image ('left' or 'right')
 * @property {string} [backgroundColor='#CAE9FF'] - The background color of the component
 */
const props = defineProps({
  title: String,
  description: String,
  imageUrl: String,
  imagePosition: {
    type: String,
    default: 'left',
  },
  backgroundColor: {
    type: String,
    default: '#CAE9FF',
  },
});
</script>

<template>
  <div class="functionality" :style="{ backgroundColor: props.backgroundColor }">
    <div class="content-wrapper">
      <div class="card-container">
        <FrontpageFlipcard />
      </div>
      <div class="text-content">
        <h1>{{ props.title }}</h1>
        <p>{{ props.description }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.functionality {
  font-family: 'DM Sans', sans-serif;
  padding: 20px;
  border: none;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-align: center;
  width: 90%;
  margin-top: 2%;
  margin-right: auto;
  margin-left: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.content-wrapper {
  font-size: 1rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 20px;
  width: 100%;
}

.content-wrapper.image-right {
  flex-direction: row-reverse;
}

.text-content {
  flex: 1;
  padding: 20px;
  max-width: 60%;
  height: auto;
  line-height: 1.3;

}

.functionality-image {
  flex: 1;
  max-width: 60%;
  height: auto;
  border-radius: 10px;
}

.text-content h1 {
  font-size: clamp(0.9rem, 2.6vw, 20rem);
}

.text-content p {
  font-size: clamp(1.2rem, 1vw, 10rem);
}

.content-wrapper {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: start;
  width: 100%;
  gap: 20px;
}

.card-container {
  flex: 1;
  max-width: 40%;
  min-width: 300px;
}

.text-content {
  flex: 2;
  padding: 20px;
}

@media (max-width: 868px) {
  .content-wrapper, .content-wrapper.image-right {
    flex-direction: column;
    text-align: center;
  }
  .text-content, .functionality-image {
    max-width: 100%;
    padding: 10px 0;
    border-radius: 30px;
  }
  .text-content h1 {
    font-size: clamp(1.7rem, 2.6vw, 20rem);
  }

  .text-content p {
    font-size: clamp(1.2rem, 1vw, 10rem);
  }
  .content-wrapper {
    flex-direction: column;
  }
  .card-container, .text-content {
    max-width: 100%;
    min-width: auto;
  }
}

</style>
