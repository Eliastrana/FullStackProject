<script setup>

import WelcomeIntro from '@/components/displayPage/WelcomeIntro.vue'
import FunctionalityDisplayer from '@/components/displayPage/FunctionalityDisplayer.vue'
import FlipcardDisplayer from '@/components/displayPage/FlipcardDisplayer.vue'

</script>

<template>

  <div class="display-page-container">


  <WelcomeIntro />

  <FunctionalityDisplayer
    title="Discover quizzes in all categories, and test your knowledge."
    description="We have something for all tastes!"
    imageUrl="/images/screenshots/quizcover.png"
    imagePosition="left"
    backgroundColor="#62B6CB" />

  <FunctionalityDisplayer
    title="Create your own quizzes and share them with the world."
    description="Our intuitive quiz creator makes it easy!"
    imageUrl="/images/screenshots/createquiz.png"
    imagePosition="right"
    backgroundColor="#BEE9E8" />


    <FlipcardDisplayer />

  </div>

</template>

<style scoped>
/* Use a Flexbox layout to center the content vertically and horizontally */
.display-page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 20px; /* Adjust based on your spacing preference */
  padding: 20px; /* Add some padding around the content */
}

@media (max-width: 768px) {
  .display-page-container {
    padding: 10px;
  }
}

/* Inside FunctionalityDisplayer.vue */
.functionality-image {
  width: 100%; /* Make the image responsive */
  max-width: 400px; /* Prevent it from becoming too large */
  height: auto; /* Maintain aspect ratio */
}


@media (max-width: 768px) {
  /* Adjust styles for smaller screens */
  .functionality-displayer-title {
    font-size: 18px; /* Smaller font size on small devices */
  }
}

</style>
