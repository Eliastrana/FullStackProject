<template>
  <div class="tag-input-container">
    <input
      class="tag-input"
      v-model="newTagInput"
      @keyup.enter="handleTagInputEnter"
      placeholder="Type a tag and press Enter... (e.g., Pythagoras Theorem)"
      :disabled="!canAddMoreTags"
    />
    <div class="tags-display">
      <span v-for="(tag, index) in tags" :key="`tag-${index}`" class="tag">
        {{ tag }}
        <button @click="removeTag(index)">×</button>
      </span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, defineProps, defineEmits } from 'vue';

const props = defineProps({
  initialTags: Array
});

const emit = defineEmits(['update-tags']);

const tags = ref(props.initialTags || []);
const newTagInput = ref('');
const maxTags = 10;

const canAddMoreTags = computed(() => tags.value.length < maxTags);

function handleTagInputEnter() {
  const trimmedTag = newTagInput.value.trim();
  if (trimmedTag && !tags.value.includes(trimmedTag)) {
    tags.value.push(trimmedTag);
    newTagInput.value = '';
    emitUpdateTags();
  }
}

function removeTag(index) {
  tags.value.splice(index, 1);
  emitUpdateTags();
}

function emitUpdateTags() {
  emit('update-tags', tags.value);
}

watch(tags, (newTags) => {
  emit('update-tags', newTags);
}, { deep: true });
</script>


<style scoped>
.tag-input-container {
  display: flex;
  flex-direction: column;
  min-width: 80%;
}

.tags-display {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  margin-top: 10px;
}

.tag {
  display: inline-flex;
  align-items: center;
  padding: 5px 10px;
  background-color: #e0e0e0;
  border-radius: 16px;
  font-size: 0.875rem;
}

.tag button {
  margin-left: 8px;
  background: none;
  border: none;
  cursor: pointer;
}

.tag-input {
  margin-top: 2%;
  border: none;
  max-width: 100%  ;
  border-radius: 8px;
  padding: 8px;
  outline: none;
}
</style>
