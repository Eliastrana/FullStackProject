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

.display-page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 20px;
  padding: 20px;
}

@media (max-width: 768px) {
  .display-page-container {
    padding: 10px;
  }
}

.functionality-image {
  width: 100%;
  max-width: 400px;
  height: auto;
}

@media (max-width: 768px) {
  .functionality-displayer-title {
    font-size: 18px;
  }
}

</style>
