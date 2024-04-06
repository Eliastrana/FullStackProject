import axios from 'axios'

const API_URL = "http://localhost:8080/api/tags/"

export const TagService = {
  async getAllTags() {
    try {
      const response = await axios.get(`${API_URL}`)
      return response.data
    } catch (error) {
      console.error('Error fetching tags:', error)
      throw error
    }
  },
}