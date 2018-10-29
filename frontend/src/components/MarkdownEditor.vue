<template>
  <div class="article-editor" id="editor">
    <textarea v-focus v-model="value" @input="update()"></textarea>
    <div class="compiled-view" v-html="compiledMarkdown"></div>
  </div>
</template>

<script>
  import focus from '../directives/FocusDirective';

  const debounce = require('lodash/debounce');
  const marked = require('marked');


  export default {
    directives: {
      focus
    },
    props: {
      value: {
        type: String,
        default: ''
      }
    },
    methods: {
      update: function () {
        debounce(function (e) {
          this.value = e.target.value
        }, 300)
      }
    },
    computed: {
      compiledMarkdown: function () {
        if (this.value) {
          return marked(this.value, {sanitize: true})
        }
        return '';
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
