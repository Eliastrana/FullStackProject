<template>
  <div class="tag-input-container">
    <input
      class="tag-input"
      v-model="newTagInput"
      @keyup.enter="handleTagInputEnter"
      placeholder="Type a tag and press Enter... (Pythagoras Theorem)"
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
// Importing necessary modules from Vue
import { ref, computed } from 'vue';

/**
 * Array of tags
 * @type {import('vue').Ref<Array<string>>}
 */
const tags = ref([]);

/**
 * Input value for the new tag
 * @type {import('vue').Ref<string>}
 */
const newTagInput = ref('');

/**
 * Maximum number of tags allowed
 * @type {number}
 */
const maxTags = 10; // Adjust as needed

/**
 * Computed property to check if more tags can be added
 * @type {import('vue').ComputedRef<boolean>}
 */
const canAddMoreTags = computed(() => tags.value.length < maxTags);

/**
 * Handles the event when the Enter key is pressed in the tag input field
 * If the input is not empty and the tag does not already exist, it is added to the tags array
 * The input field is then cleared
 */
function handleTagInputEnter() {
  const trimmedTag = newTagInput.value.trim();
  if (trimmedTag && !tags.value.includes(trimmedTag)) {
    tags.value.push(trimmedTag);
    newTagInput.value = ''; // Clear input field
  }
}

/**
 * Removes a tag from the tags array at the specified index
 * @param {number} index - The index of the tag to remove
 */
function removeTag(index) {
  tags.value.splice(index, 1);
}
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
