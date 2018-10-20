<template>
  <div class="article-editor" id="editor">
    <textarea v-model="input" @input="update()"></textarea>
    <div class="compiled-view" v-html="compiledMarkdown"></div>
  </div>
</template>

<script>

  const debounce = require('lodash/debounce');
  const marked = require('marked');

  export default {
    data: function () {
      return {
        input: '# hello'
      };
    },
    methods: {
      update: function () {
        debounce(function (e) {
          this.input = e.target.value
        }, 300)
      }
    },
    computed: {
      compiledMarkdown: function () {
        return marked(this.input, {sanitize: true})
      }
    }
  }
</script>

<style>
  .article-editor textarea, #editor div {
    display: inline-block;
    width: 49%;
    height: 100%;
    vertical-align: top;
    box-sizing: border-box;
    padding: 0 20px;
  }

  .article-editor textarea {
    border: none;
    border-right: 1px solid #ccc;
    resize: none;
    outline: none;
    background-color: #f6f6f6;
    font-size: 14px;
    font-family: 'Monaco', courier, monospace;
    padding: 20px;
  }

  .article-editor code {
    color: #f66;
  }
</style>
