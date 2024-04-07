<template>
  <footer class="footer">
    <div class="container">
      <div class="content">
        <p>© {{ currentYear }} Our Name</p>
        <nav class="footer-nav">
          <ul>

          </ul>
        </nav>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { ref } from 'vue';

/**
 * Current year
 * @type {import('vue').Ref<number>}
 */
const currentYear = ref(new Date().getFullYear());
</script>


<style scoped>

.footer {
  margin-top: 5%;
  padding-top: 5%;
  background-color: #f5f5f5;
  color: #333;
  text-align: center;
}

</style>
