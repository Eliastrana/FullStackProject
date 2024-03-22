<template>
  <div class="container" v-for="(item, index) in items" :key="index" draggable="true" @dragstart="dragStart(index)" @drop="drop(index)" @dragover.prevent>
    {{ item }}
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: ['Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5'],
      draggedItem: null
    };
  },
  methods: {
    dragStart(index) {
      this.draggedItem = index;
    },
    drop(index) {
      const itemToMove = this.items.splice(this.draggedItem, 1)[0];
      this.items.splice(index, 0, itemToMove);
    }
  }
};
</script>

<style scoped>
.container {
  padding: 10px;
  margin-bottom: 5px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  cursor: move;
}
</style>
