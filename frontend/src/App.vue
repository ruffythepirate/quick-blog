<template>
  <markdown-editor></markdown-editor>
</template>

<script>
  import MarkdownEditor from './components/MarkdownEditor.vue';
  import Vue from 'vue';
  import focus from './directives/FocusDirective';

  Vue.directive('focus', focus);

  export default {
    components: {
      MarkdownEditor
    }
  }
</script>

