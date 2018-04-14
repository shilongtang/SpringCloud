<template>

  <ym-panel name="菜单配置">
    <div style=" padding-bottom: 10px;">
      <el-button  size="small" onclick="history.go(-1)">返回</el-button>
      <el-button @click="addNode" type="primary"  size="small">新增</el-button>
    </div>
    <div class="container-two-wrap">

      <div class="table-tree left">
        <div class="title-module">菜单结构</div>

        <div class="table-tree">
          <el-row class="table-head">
            <el-col :span="12">编码
            </el-col>
            <el-col :span="12">名称</el-col>
          </el-row>
          <div class="table-body">
            <el-tree
              :data="data1"
              :props="defaultProps"
              node-key="id"
              @node-expand="saveTreeStatus"
              @node-collapse="deleteTreeStatus"
              :highlight-current="true"
              :expand-on-click-node="false"
              :default-expanded-keys="treeStatus"
              :render-content="renderContent">
            </el-tree>
          </div>
        </div>

      </div>
      <div >
        <div class="title-module">菜单信息</div>
        <el-row class="bg-f">

          <div class="p10">
            <el-button @click="saveForm()" type="primary"  size="small">保存</el-button>
            <el-button @click="deleteNode()" type="danger" size="small">删除</el-button>
            <div style=" padding-top: 10px;">
            </div>

          </div>

          <el-col :span="24" class="form-wrap">
            <!--:rules="rules" 验证-->
            <el-form :model="formInline" ref="formInline" label-width="100px" :label-position="'left'">

              <el-form-item label="编码" prop="description" class="item-5">
                <el-input v-model="formInline.code" size="small"></el-input>
              </el-form-item>

              <el-form-item label="名称" prop="text" class="item-5">
                <el-input v-model="formInline.description" size="small" placeholder="请输入内容"></el-input>
              </el-form-item>

              <el-form-item label="父级编码" prop="description" class="item-5">
                <el-input v-model="formInline.parentCode" size="small" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="父级名称" prop="text" class="item-5">
                <el-input v-model="formInline.parentDescription" size="small" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="菜单级别" prop="text" class="item-5">
                <el-input v-model="formInline.level" size="small" :disabled="true"></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>

  </ym-panel>

</template>

<script>

  //import { getList,deleteById,save,getDataById,lookUp,querySql} from './api';

  export default {
    data() {
      return {
        treeStatus:[],
        sign:false,
        currentRow: null,
        labelPosition: 'left',
        formInline: {
          parentCode: '',
          parentDescription: '',
          parentId: '',
          code: '',
          id:'',
          description: '',
          level:''
        },
        data1: [],  //树型列表
        formInline: {},
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        selectedNode: {},
        pageData:[],
      }
    },
    mounted(){
      const _this=this;
     // _this.tableDataInit();
    },
    methods: {
      setCurrent(row) {
        this.$refs.singleTable.setCurrentRow(row);
      },
      //保存节点
      saveForm(){
        const _this=this;
        if(_this.formInline.level === undefined ||_this.formInline.level === "") {
          _this.$message({
            message: '当前没有菜单被选中！',
            type: 'warning'
          });
          return;
        }
        if(_this.formInline.description === "" || _this.formInline.description === undefined) {
          _this.$message({
            message: '名称不能为空！',
            type: 'warning'
          });
          return;
        }
        let flag=true;
        if(_this.formInline.code!=''){
          _this.pageData.forEach(data=>{
            if(data.functionMenu.code===_this.formInline.code&&
              data.functionMenu.id!=_this.formInline.id){
              _this.$message({
                message: '编码重复！',
                type: 'warning'
              });
              flag=false;
              return;
            }
          })
        }
        if(flag){
          let data= {
            "functionMenu": {
              "code": _this.formInline.code,
              "id": _this.formInline.id,
              "description": _this.formInline.description,
              "parentId": _this.formInline.parentId,
              "level":_this.formInline.level
            }
          }
          global.httpClient(save, data, function (error, result) {
            console.info('error',error);
            _this.$message({
              message: '保存成功!',
              type: 'success'
            });
            _this.tableDataInit();
            _this.sign=false;
          });
        }
      },
      deleteNode(){
        const _this=this;
        if(_this.selectedNod===undefined||_this.selectedNod==null){
          _this.$message({
            message: '请选择一个菜单！',
            type: 'warning'
          });
        }else if(_this.selectedNod.data.children != undefined && _this.selectedNod.data.children.length > 0) {
          _this.$message({
            message: '所选菜单已使用，不可删除！',
            type: 'warning'
          });
        }else {
          this.$confirm('是否确认删除此菜单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            global.httpClient(deleteById,[""+_this.selectedNod.data.id], function(error, results) {
              _this.$message({
                message: '删除成功!',
                type: 'success'
              });
              _this.tableDataInit();
              _this.sign=false;
              _this.clearFormData();
            },true);
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        }
      },
      handleChange(value) {
      },
      addNode(){//添加父级菜单
        let _this=this;
        if(_this.sign==true){
          _this.$message({
            message: '请先保存新增数据！',
            type: 'warning'
          });
          return;
        }
        let obj={
          "id": _this.getUUid(),
          "description": '',
          "code": '',
          "level": 0
        };
        let item={'functionMenu':obj};
        _this.pageData.push(item);
        let a=_this.dataFormat(_this.pageData);
        _this.data1=a;
        _this.sign=true;
        //_this.treeStatus.push(data.id);
      },
      //添加子级别
      append( store, data) {
        let _this=this;
        if(_this.sign==true){
          _this.$message({
            message: '请先保存新增数据！',
            type: 'warning'
          });
          return;
        }
        if(data.level===5){
          _this.$message({
            message: '该菜单不可添加子菜单！',
            type: 'warning'
          });
          return;
        }
        let obj={
          "parentId": data.id,
          "id": _this.getUUid(),
          "description": '',
          "code": '',
          "level": parseInt(data.level)+1
        };
        let item={'functionMenu':obj};
        _this.pageData.push(item);
        let a=_this.dataFormat(_this.pageData);
        _this.data1=a;
        _this.sign=true;
        _this.treeStatus.push(data.id);
      },
      getUUid() {//生成时间戳
        //debugger;
        let num="";
        for(var i=0;i<6;i++)
        {
          num+=Math.floor(Math.random()*10);
        }
        return new Date().getTime()+num;
      },
      /**
       * 删除选中节点
       */
      remove() {
        this.selectedNode.store.remove(this.selectedNode.data);
      },
      /**
       * 选中节点信息
       */
      selected(node, store, data){
        const _this=this;
        _this.selectedNod = {
          data: data,
          store: store
        };
        //根据父节点查找,父节点信息
        if (data.parentId!=undefined&&data.parentId != ''){
          global.httpClient(getDataById,[""+data.parentId], function(error, results) {
            _this.formInline = data;
            _this.formInline.parentDescription=results.functionMenu.description;
            _this.formInline.parentCode=_this.treeCheckCode(results.functionMenu.code);
          },true);
        }else {
          this.formInline = data;
          _this.specialInd=data.specialInd+"";
          _this.blockInd=data.blockInd+"";
        }
      },
      //格式化数据list=>tree
      dataFormat(data){
        const _this=this;
        let jsonData=[];
        for (let i = 0; i < data.length; i++) {
          jsonData.push({
            parentId: data[i].functionMenu.parentId,
            id:data[i].functionMenu.id,
            code:_this.treeCheckCode(data[i].functionMenu.code),
            description:data[i].functionMenu.description,
            level:data[i].functionMenu.level
          });
        };
        //转换数据格式
        return global.list2tree(jsonData);
      },
      //初始化table数据
      tableDataInit(){
        const _this=this;
        let page = {
          'number':1,
          'size':4000
        };
        global.httpClient(getList,page, function(error, results) {
          let theData=_this.dataFormat(results.list);
          _this.pageData=results.list;
          _this.data1 =theData;
        },true);
      },
      //清除form显示数据
      clearFormData(){
        const _this=this;
        _this.formInline.parentCode="";
        _this.formInline.parentDescription="";
        _this.formInline.parentId="";
        _this.formInline.code="";
        _this.formInline.description="";
        _this.formInline.level='';
        _this.sign=false;
        _this.selectedNod=null;
      },
      //记录树的显示状态
      saveTreeStatus(data){
        const _this=this;
        let a=0;
        for(let i=0;i<_this.treeStatus.length;i++){
          if (data.id == _this.treeStatus[i]) {
            a=a+1;
          }
        }
        if(a==0) {
          _this.treeStatus.push(data.id);
        }
      },
      //清除树状态
      deleteTreeStatus(data){
        const _this=this;
        for(let i=0;i<_this.treeStatus.length;i++){
          if (data.id == _this.treeStatus[i]) {
            if(i==0){
              _this.treeStatus=[];
            }else if(i == _this.treeStatus.length-1){
              _this.treeStatus.splice(i,1);
            }else {
              _this.treeStatus.splice(i,_this.treeStatus.length-i);
            }
          }
        }
      },
      treeCheckCode(code){
        if((code+"").length % 2 == 0){
          return code+"";
        }else {
          return code;
        }
      },

      renderContent(h, { node, data, store }) {
        return (
          <span on-click={ () => this.selected(node, store, data) } class="table-tr">
          <span class="table-button">
          <el-button size="mini" on-click={() => this.append(store, data)}> + </el-button>
        </span>
        <span class="table-text">
          <span>{data.description}</span>
        <span>{data.code}</span>
        </span>
        </span>);
      }
    }
  };

</script>

<style>
</style>
