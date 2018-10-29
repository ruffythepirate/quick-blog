<template>
  <action-bar article="article"></action-bar>
  <markdown-editor ></markdown-editor>
</template>

<script>
  import MarkdownEditor from "../MarkdownEditor";
  import ActionBar from "./ActionBar";
  export default {
    components: {ActionBar, MarkdownEditor}
  }
</script>

<style>

</style>