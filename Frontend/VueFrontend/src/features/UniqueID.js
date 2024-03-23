export function generateUniqueID() {
  return 'xxxx-xxxx-4xxx-yxxx-xxxx-xxxx'.replace(/[xy]/g, (c) => {
    const r = (Math.random() * 16) | 0,
      v = c === 'x' ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
}