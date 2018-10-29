<template>
  <div>
    <action-bar article="article"></action-bar>
    <markdown-editor ></markdown-editor>
  </div>
</template>

<script>
  import MarkdownEditor from '../MarkdownEditor.vue';

  import ActionBar from './ActionBar.vue';

  export default {
    components: { ActionBar, MarkdownEditor },
  };
</script>

<style>

</style>