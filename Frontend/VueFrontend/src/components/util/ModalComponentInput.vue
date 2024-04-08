<template>
  <div v-if="props.isVisible" class="modal">
    <div class="modal-content">
      <h2>Create Category</h2>
      <input v-model="categoryName" placeholder="Category Name" />
      <input
        v-model="description"
        placeholder="Description"
        @keyup.enter="submitForm"
      />
      <button @click="submitForm">Submit</button>
      <button @click="close">Cancel</button>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';



const emits = defineEmits(['close', 'created']);
const isVisible = ref(true);

const categoryName = ref('');
const description = ref('');

const submitForm = () => {
  if (!categoryName.value || !description.value) {
    alert('Please fill in all fields.');
    return;
  }

  emits('created', categoryName.value, description.value);
  categoryName.value = '';
  description.value = '';
};


const close = () => {
  emits('close');
};


const props = defineProps({
  isVisible: Boolean
});


</script>

<style scoped>

.modal {

  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  max-width: 500px;
  width: 90%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: stretch;
  font-family: 'DM Sans', sans-serif;

}

.modal-content input,
.modal-content textarea {
  font-family: 'DM Sans', sans-serif;
  padding: 10px;
  margin-top: 5px;
  border: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 4px;
}

.modal-content button {
  cursor: pointer;
  padding: 10px 20px;
  margin-top: 10px;
  border: none;
  border-radius: 20px;
  max-width: 20%;
  background-color: #007bff;
  color: white;
  font-weight: bold;
  align-items: center;
}

.modal-content button:last-child {
  background-color: #a9a9a9;
}

.modal-content button:hover {
  background-color: #0056b3;
}

.modal-content button:last-child:hover {
  background-color: #7a7a7a;
}

</style>
