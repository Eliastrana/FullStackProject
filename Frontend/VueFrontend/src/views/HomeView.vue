<script setup>

import WelcomeIntro from '@/components/displayPage/WelcomeIntro.vue'
import FunctionalityDisplayer from '@/components/displayPage/FunctionalityDisplayer.vue'

</script>

<template>

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


  <FunctionalityDisplayer
    title="Practice with quizzes to improve your knowledge."
    description="See your stats and track your progress!"
    imageUrl="/images/dalle/corpcore4.png"
    imagePosition="left"
    backgroundColor="#CAE9FF" />






</template>


<style scoped>


</style>
