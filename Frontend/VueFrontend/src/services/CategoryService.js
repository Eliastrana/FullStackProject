import axios from 'axios';

const API_URL = 'http://localhost:8080/api/categories'

/**
 *
 * @type {{getCategoryById(*): Promise<any|undefined>, createCategory(*): Promise<any>, getAllCategories(): Promise<any|undefined>}}
 */
export const CategoryService = {
  async getAllCategories() {
    try {
      const response = await axios.get(`${API_URL}`)
      return response.data
    } catch (error) {
      console.error('Error fetching categories:', error)
      throw error
    }
  },

  /**
   *
   * @param categoryDetails
   * @returns {Promise<any>}
   */

  async createCategory(categoryDetails) {
    const response = await axios.post(`${API_URL}`, categoryDetails)
    return response.data
  },

  /**
   *
   * @param categoryId
   * @returns {Promise<any>}
   */
  async getCategoryById(categoryId) {
    try {
      const response = await axios.get(`${API_URL}/${categoryId}`)
      return response.data
    } catch (error) {
      console.error('Error fetching category by ID:', error)
      throw error
    }
  },
}