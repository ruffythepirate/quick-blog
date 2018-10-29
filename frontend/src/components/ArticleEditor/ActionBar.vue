<template>
  <div class="action-bar">
    <button class="submit" @click="save">Save</button>
  </div>

</template>

<script>

  const articleService = require('../../services/ArticleService');

  export default {
    props: ['article'],
    methods: {
      save: () => {
        articleService.saveOrUpdateArticle(this.article).then(function (savedArticle) {
          this.article.id = savedArticle.id;
        }.bind(this));
      }
    }
  }
</script>

<style>

</style>