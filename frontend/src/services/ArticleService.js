import axios from 'axios';

function createArticle(article) {
  return axios.post('/api/articles',
    article);
}

function updateArticle(article) {
  return axios.put(`/api/articles/${article.id}`,
    article);
}


export default {
  saveOrUpdateArticle(article) {
    if (article.id) {
      return updateArticle(article);
    }
    return createArticle(article);
  },
};
