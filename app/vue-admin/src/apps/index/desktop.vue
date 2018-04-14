<template>
  <el-row :gutter="18">
    <el-col :span="12">
      <div class="panel">
        <div class="panel-title">{{ "xxxx"}}</div>
        <div class="panel-body">
          <div class="index-container">

          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="12">
      <div class="panel">
        <div class="panel-title">{{"xxxx"}}</div>
        <div class="panel-body">
          <div class="index-container">

          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    data(){
      return{}
    },
    methods: {
    }
  };
</script>

<style lang="scss" scoped>
  @import '../../style/config.scss';
  .index-container {
    height: 270px;
    overflow: auto;
  }

  .panel{
    background-color: $bg-color-container;
    overflow: hidden;
    border: none;
    position: relative;
    -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05);
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05);
    .panel-title{
      font-size: 14px;
      height: 32px;
      line-height: 32px;
      padding: 0 20px;
      border-bottom: 1px solid $border-color;
    }
    .panel-body{
      padding: $base-spacing;
    }
  }
</style>
