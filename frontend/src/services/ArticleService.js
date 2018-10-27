import axios from 'axios';

function createArticle(article) {
  return axios.post('/api/articles',
    article);
}

function updateArticle(article){
  return axios.put(`/api/articles/${article.id}`,
    article);
}


export default {
  saveOrUpdateArticle: function(article) {
    if(article.id) {
      return updateArticle(article);
    } else {
      return createArticle(article);
    }
  }
}