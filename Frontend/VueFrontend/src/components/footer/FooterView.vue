<template>
  <footer class="footer">
    <div class="container">
      <div class="content">
        <p>© {{ currentYear }} Our Name</p>
        <nav class="footer-nav">
          <ul>
            <!-- Placeholder for social media links -->

          </ul>
        </nav>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'TheFooter',
  data() {
    return {
      currentYear: new Date().getFullYear(),
    };
  },
};
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
