<script setup>
import PersonalStatisticsView from '@/views/userPage/myAccountComponents/PersonalStatisticsView.vue'
import PersonalInfoView from '@/views/userPage/myAccountComponents/PersonalInfoView.vue'
import PersonalQuizzesView from '@/views/userPage/myAccountComponents/PersonalQuizzesView.vue'

</script>

<template>

    <div class="container">
      <PersonalStatisticsView/>
      <PersonalQuizzesView/>
      <PersonalInfoView/>
    </div>

</template>

<style scoped>
.container {
  display: flex;
  justify-content: space-around; /* Adjusts spacing between the components */
  align-items: flex-start; /* Aligns components vertically at the top */
}
</style>
