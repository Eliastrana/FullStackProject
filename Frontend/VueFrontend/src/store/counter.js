import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

/**
 *
 * @type {StoreDefinition<"counter", _ExtractStateFromSetupStore<{count: Ref<UnwrapRef<number>>, doubleCount: ComputedRef<*>, increment: function(): void}>, _ExtractGettersFromSetupStore<{count: Ref<UnwrapRef<number>>, doubleCount: ComputedRef<*>, increment: function(): void}>, _ExtractActionsFromSetupStore<{count: Ref<UnwrapRef<number>>, doubleCount: ComputedRef<*>, increment: function(): void}>>}
 */
export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})
