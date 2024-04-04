<template>
  <MixedQuizDisplayer :quizData="quizData" />
</template>

<script setup>
import { computed } from 'vue';
import { useStore } from 'vuex';
import MixedQuizDisplayer from '@/components/displayPage/SpecifiedQuizDisplays/MixedQuizDisplayer.vue';
import { useRouter } from 'vue-router';

// Access the Vuex store
const store = useStore();
const router = useRouter();

// Computed property to get quizData from the Vuex store
const quizData = computed(() => store.state.quizAttempt.quizData);

// Use onMounted lifecycle hook from Vue 3 for setup script
import { onMounted } from 'vue';

onMounted(() => {
  if (!quizData.value) {
    console.warn("No quiz data available, navigating back to quiz selection.");
    // Navigate back or fetch the quiz again based on your app's flow
    router.push({ name: 'QuizSelection' });
  }
});
</script>
