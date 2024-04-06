<template>
  <MixedQuizDisplayer :quizData="quizData" />
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import MixedQuizDisplayer from '@/components/displayPage/SpecifiedQuizDisplays/MixedQuizDisplayer.vue';
import { useRouter } from 'vue-router';

/**
 * Vuex Store instance
 * @type {import('vuex').Store}
 */
const store = useStore();

/**
 * Vue Router instance
 * @type {import('vue-router').Router}
 */
const router = useRouter();

/**
 * Quiz data from the Vuex store
 * @type {import('vue').ComputedRef<Object>}
 */
const quizData = computed(() => store.state.quizAttempt.quizData);

onMounted(() => {
  /**
   * On component mount, check if quiz data is available
   * If not, navigate back to the QuizSelection route
   */
  if (!quizData.value) {
    console.warn("No quiz data available, navigating back to quiz selection.");
    router.push({ name: 'QuizSelection' });
  }
});
</script>


