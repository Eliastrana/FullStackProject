<template>
  <div class="modal-background" v-if="isVisible">
    <div class="modal">
      <p>{{ message }}</p>
      <button @click="confirmAction">Yes</button>
      <button @click="cancelAction">No</button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['isVisible', 'message'],
  emits: ['confirm', 'cancel'],
  methods: {
    confirmAction() {
      this.$emit('confirm');
    },
    cancelAction() {
      this.$emit('cancel');
    }
  }
}
</script>

<style scoped>
.modal-background {
  z-index: 1000;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'DM Sans', sans-serif;
}

.modal {
  background: white;
  padding: 20px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

button {
  padding: 5px 10px;
  cursor: pointer;
  border: none;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  font-family: 'DM Sans', sans-serif;
  min-height: 40px;
  font-size: 1rem;
}

button:hover {
  background-color: rgb(169, 169, 169);

}


</style>
