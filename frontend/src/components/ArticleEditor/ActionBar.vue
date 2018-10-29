<template>
  <div class="action-bar">
    <div class="wrapper">
      <button class="submit" @click="save">Save</button>
    </div>
  </div>

</template>

<script>

  import articleService from '../../services/ArticleService';

  export default {
    props: ['article'],
    methods: {
      save() {
        articleService.saveOrUpdateArticle(this.article).then((savedArticle) => {
          this.article.id = savedArticle.id;
        });
      },
    },
  };
</script>

<style>
</style>