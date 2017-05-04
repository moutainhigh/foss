/**
 * @author 105888-foss-zhangxingwang
 * @date 2013-7-26 10:23:45
 */

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
waybill.exceptMsg.getTargetDate = function(date, day) {
  var d, s, t, t2;
  var MinMilli = 1000 * 60;
  var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
  t = Date.parse(date);
  t2 =  new Date(t+day*DyMilli);
  t2.setHours(0);
  t2.setMinutes(0);
  t2.setSeconds(0);
  t2.setMilliseconds(0);  
  return t2;
};


waybill.exceptMsg.getTargetDate1 = function(date, day) {
  var d, s, t, t2;
  var MinMilli = 1000 * 60;
  var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
  t = Date.parse(date);
  t2 =  new Date(t+day*DyMilli);
  t2.setHours(23);
  t2.setMinutes(59);
  t2.setSeconds(59);
  t2.setMilliseconds(0);  
  return t2;
};

//-------------------------代办异常-------------------------------------------------------
//----------代办异常数据
//定义处理待办事项模型
Ext.define('Foss.ToDo.Model.exceptMsgModel', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'waybillRfcId',type:'string' },//更改单ID
    { name: 'waybillNo',type:'string' }, //运单号
    { name: 'darftOrgName',type:'string' }, //起草变更部门
    { name: 'darftOrgCode',type:'string' }, //起草变更部门Code
    { name: 'rfcInfo',type:'string' },  //变更内容
    { name: 'darfter',type:'string' },  //变更申请人
    { name: 'todoOperateTime',convert:dateConvert }//更改受理时间
  ]
});


//创建一个待办事项store
Ext.define('Foss.ToDo.Store.exceptMsgStore', {
  extend: 'Ext.data.Store',
  //绑定一个模型
  model:'Foss.ToDo.Model.exceptMsgModel',
  //是否自动查询
  autoLoad: false,
  //默认每页数据大小
  pageSize:10,
  proxy: {
    //代理的类型为内存代理
    type: 'ajax',
    //提交方式
    actionMethods:'POST',
    url:waybill.realPath('queryExceptMsgAction.action'),
    //定义一个读取器
    reader: {
      //以JSON的方式读取
      type: 'json',
      //定义读取JSON数据的根对象
      root: 'vo.exceptMsgActionDtoList',
      //返回总数
      totalProperty : 'totalCount'
    }
  },
  //事件监听
  listeners: {
  //查询事件
    beforeload : function(s, operation, eOpts) {
      //执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
      var queryParams = waybill.exceptMsg.ExceptMsgForm.getValues();
      var form = waybill.exceptMsg.ExceptMsgForm.getForm();
      if(!form.isValid()){
        return false;
      }
      var waybillNo = form.getValues().waybillNo;
      // 验证运单号输入的行数
      if (!Ext.isEmpty(waybillNo)) {
        var arrayWaybillNo = waybillNo.split('\n');
        if (arrayWaybillNo.length > 50) {
          Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation'), 'error', 3000); 
          return false;  
        }
        for (var i = 0; i < arrayWaybillNo.length; i++) {
          if (Ext.isEmpty(arrayWaybillNo[i])) {
            Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation'), 'error', 3000); 
            return false;  
          }
        }
      }
      Ext.apply(operation, {
        params : {          
            'vo.exceptMsgConditionDto.waybillNo': queryParams.waybillNo,
            'vo.exceptMsgConditionDto.darftOrgCode': queryParams.darftOrgCode,
            'vo.exceptMsgConditionDto.inStockOrgCode': queryParams.inStockOrgCode,
            'vo.exceptMsgConditionDto.handlerOverNo': queryParams.handlerOverNo,
            'vo.exceptMsgConditionDto.loadNo': queryParams.loadNo,
            'vo.exceptMsgConditionDto.todoOperateTimeBegin': queryParams.todoOperateTimeBegin,
            'vo.exceptMsgConditionDto.todoOperateTimeEnd': queryParams.todoOperateTimeEnd,
            'vo.exceptMsgConditionDto.keyWord': queryParams.keyWord
          }
      });  
    }
  }
});
//查询条件
Ext.define('Foss.ToDo.Form.exceptMsgForm',{
  extend:'Ext.form.Panel',
  title: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.queryCondition'),//查询条件,
  frame:true,
  collapsible: true,
  animCollapse: true,
  defaults: {
    margin: '5 10 5 10',
    labelWidth: 100
  },
  defaultType: 'textfield',
  layout: 'column',
  constructor: function(config){
    var me = this,
    cfg = Ext.apply({}, config);
    me.items = [{
      name : 'waybillNo',
      xtype : 'textarea',
      labelWidth: 60,
      height:95,
      fieldLabel : waybill.exceptMsg.i18n('pkp.predeliver.notifyDetailsAction.query.waybillNo'),
      columnWidth : .25,
      emptyText : waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation'),
      regex : /^([0-9]{8,10}\n?)+$/i,
      regexText : waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation')
    },{
      name:'handlerOverNo',
      fieldLabel:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.handlerOverNo'),//交接单号,
      xtype: 'textfield',
      columnWidth: 0.25
    },{
      name:'loadNo',
      fieldLabel:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.loadNo'),//配载单号,
      xtype: 'textfield',
      columnWidth: 0.25
    },{
      xtype : 'dynamicorgcombselector',
      name: 'darftOrgCode',
      fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.darftOrgName'),//变更申请部门,
      columnWidth: 0.25
    },{
      xtype : 'dynamicorgcombselector',
      name: 'inStockOrgCode',
      fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.inStockOrgName'),//变更申请部门,
      columnWidth: 0.25
    },{
      xtype: 'rangeDateField',
      fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.operateTime'),//更改受理时间,
      dateType: 'datetimefield_date97',
      fieldId: 'FOSS_todooperateTimeexceptMsgAction_Id',
      fromName: 'todoOperateTimeBegin',
      toName: 'todoOperateTimeEnd',
      editable:false,
      fromValue: Ext.Date.format(waybill.exceptMsg.getTargetDate(new Date(),-6),'Y-m-d H:i:s'),
      toValue: Ext.Date.format(waybill.exceptMsg.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
      columnWidth: .50
    },{
      name:'keyWord',
      fieldLabel:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.keyWord'),//关键字,
      xtype: 'textfield',
      columnWidth: 0.25
    },{
      xtype: 'displayfield',
      columnWidth : .5,
      name:'attention',
      fieldStyle:'color:red;font-size:5px;',
      value:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.query.beiginNote')
    }];
    me.buttons = [{
      text:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.reset'),//重置,
      handler:function(){
        var form = this.up('form').getForm();
        form.findField('waybillNo').setValue("");
        form.findField('loadNo').setValue("");
        form.findField('handlerOverNo').setValue("");
        form.findField('darftOrgCode').setValue("");
        form.findField('inStockOrgCode').setValue("");
        form.findField('keyWord').setValue("");
        form.findField('todoOperateTimeBegin').setValue(Ext.Date.format(waybill.exceptMsg.getTargetDate(new Date(),-6),'Y-m-d H:i:s'));
        form.findField('todoOperateTimeEnd').setValue(Ext.Date.format(waybill.exceptMsg.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
      }
    },'->',{
      text:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.query'),//查询,
      disabled:!waybill.exceptMsg.isPermission('exceptmsgindex/exceptmsgindexquerybutton'),
      hidden:!waybill.exceptMsg.isPermission('exceptmsgindex/exceptmsgindexquerybutton'),
      cls:'yellow_button',
      handler:function(){
        var form = this.up('form').getForm();
        if(form.isValid())
        {          
          waybill.exceptMsg.pagingBar1.moveFirst();
        }
        else
        {
          Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNoError'), 'error', 3000);
        }
      }
    }];
    me.callParent([cfg]);
  }
});

//显示异常Grid数据
Ext.define('Foss.ToDo.ExceptMsgGrid', {
  extend:'Ext.grid.Panel',
  //增加表格列的分割线
  columnLines: true,
  emptyText:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.emptyText'),//查询结果为空！,
  bodyCls: 'autoHeight',
  cls: 'autoHeight',
  id:'Foss_ToDo_pendTodoMsgGrid_ExceptMsgTodo',
  //表格对象增加一个边框
  frame: true,
  stripeRows: true,
  //定义表格的标题
  title:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.exceptMsgProccess'),//待办事项,
  collapsible: true,
  animCollapse: true,   
  selModel:Ext.create('Ext.selection.CheckboxModel'),
  dockedItems : [ {
        xtype : 'toolbar',
        dock : 'top',
        layout : 'column',
        items : [{
          xtype:'button',
          plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 3
          }),
          margin : '0 0 0 30',
          name:'pendingExceptMsgInfo',
          text:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.createTodobatch'),
          handler: function() {
          
            //定义流水号数组
            var waybillRfcIdList = new Array();
            records = Ext.getCmp('Foss_ToDo_pendTodoMsgGrid_ExceptMsgTodo').getSelectionModel().getSelection();
            
            for (var i = 0; i < records.length; i++) {  
              waybillRfcIdList.push(records[i].data.waybillRfcId);
            }  
            if (waybillRfcIdList.length == 0) {  
              Ext.Msg.alert(waybill.exceptMsg.i18n('pkp.waybill.todoAction.tip'),waybill.exceptMsg.i18n('pkp.waybill.todoAction.choseOperateData'));
              return;
            }
          Ext.Msg.confirm( waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.makeSure'), function(btn,text){
          if(btn=="yes"){
            
            var newVo = {
              'vo':{
                'waybillRfcIdList':waybillRfcIdList
              }
            }
            
            Ext.Ajax.request({
                url:waybill.realPath('updateExceptMsgBatch.action'),
                jsonData: newVo,
                success: function(response){
                  var json = Ext.decode(response.responseText);
                  alert("重新生成待办成功！");
               },
                    exception : function(response) {
                      var result = Ext.decode(response.responseText);
                      Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), result.message, 'error', 3000);
                    }
            });
          }
        });
        }
        },'->',{
          xtype:'button',
          plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 3
          }),
          margin : '0 0 0 30',
          name:'pendingExceptMsgInfo',
          text:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.resetTodobatch'),
          handler: function() {
          
            //定义流水号数组
            var waybillRfcIdList = new Array();
            records = Ext.getCmp('Foss_ToDo_pendTodoMsgGrid_ExceptMsgTodo').getSelectionModel().getSelection();
            
            for (var i = 0; i < records.length; i++) {  
              waybillRfcIdList.push(records[i].data.waybillRfcId);
            }  
            if (waybillRfcIdList.length == 0) {  
              Ext.Msg.alert(waybill.exceptMsg.i18n('pkp.waybill.todoAction.tip'),waybill.exceptMsg.i18n('pkp.waybill.todoAction.choseOperateData'));
              return;
            }
          Ext.Msg.confirm( waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.makeSure'), function(btn,text){
          if(btn=="yes"){
            
            var newVo = {
              'vo':{
                'waybillRfcIdList':waybillRfcIdList
              }
            }
            
            Ext.Ajax.request({
                url:waybill.realPath('updateExceptMsgBatchStatus.action'),
                jsonData: newVo,
                success: function(response){
                  var json = Ext.decode(response.responseText);
                  alert("重新生成待办成功！");
               },
                    exception : function(response) {
                      var result = Ext.decode(response.responseText);
                      Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), result.message, 'error', 3000);
                    }
            });
          }
        });
      }
    }]
    }],
  //定义表格列信息
  columns: [{
    xtype:'actioncolumn',
    flex:0.05,
    text: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.operating'),//操作,
    align: 'center',
    items: [{
      iconCls: 'deppon_icons_showdetail',
      tooltip: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.lookOver'),//查看,
      disabled:!waybill.exceptMsg.isPermission('exceptmsgindex/exceptmsgindexviewbutton'),
      handler: function(grid, rowIndex, colIndex) {
        var selection = grid.getStore().getAt(rowIndex);
        var  waybillRfcId = selection.get('waybillRfcId');
        var win = Ext.create('Foss.waybill.QueryExceptMsgWindow').show();
        Ext.Ajax.request({
          url:waybill.realPath('queryTodoExceptMsg.action'),
          params:{'vo.exceptMsgConditionDto.waybillRfcId':waybillRfcId},
          
          success:function(response){
        	Ext.getCmp('Foss_ToDo_Form_ExceptTodo_CreateDetail').getStore().removeAll();
            var result = Ext.decode(response.responseText);  
            var formModel = new Foss.ToDo.Model.ExceptTodoModel(result.vo.labelGoodTodoList) ;
            waybill.exceptMsg.ExceptTodoForm.loadRecord(formModel);
            waybill.exceptMsg.ExceptTodoForm.items.items[0].getSelectionModel().deselectAll();
            waybill.exceptMsg.ExceptTodoForm.items.items[0].store.loadData(result.vo.labelGoodTodoList);
            
          }
        });
        
      }
    }]
  },{
    header:'id',//id,
    dateIndex:'waybillRfcId',
    hidden: true,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo'),//运单号, 
    //关联model中的字段名
    dataIndex: 'waybillNo',
    flex:0.11,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.darftOrgName'),//变更申请部门, 
    //关联model中的字段名
    dataIndex: 'darftOrgName', 
    xtype: 'ellipsiscolumn',
    flex:0.11,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.rfcInfo'),//更改内容, 
    //关联model中的字段名
    dataIndex: 'rfcInfo', 
    xtype: 'ellipsiscolumn',
    flex:0.3,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillRfcApplicant'),//变更申请人, 
    //关联model中的字段名
    dataIndex: 'darfter', 
    flex:0.11,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.operateTime'),//更改受理时间, 
    //关联model中的字段名
    dataIndex: 'todoOperateTime', 
    flex : 0.11,
    align: 'center',
    renderer:function(value){
      if(value!=null){
        return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
      }else{
        return null;
      }
    } 
  }],
  constructor: function(config){
    var me = this,
    cfg = Ext.apply({}, config);
    me.store = Ext.create('Foss.ToDo.Store.exceptMsgStore');
    //添加分页工具条
    me.bbar = Ext.create('Deppon.StandardPaging',{
      store:me.store,
      plugins: {
            ptype: 'pagesizeplugin',
              //超出输入最大限制是否提示，true则提示，默认不提示
            alertOperation: true,
            //自定义分页comobo数据
            sizeList: [['5', 5], ['10', 10], ['20', 20], ['50', 50], ['100', 100], ['200', 200], ['500', 500], ['1000', 1000]],
            //入最大限制，默认为200
            maximumSize: 1000
          },
      displayInfo: true
    });
    waybill.exceptMsg.pagingBar1 = me.bbar;
    me.callParent([cfg]);
  }
});


//------------------------------------------
//定义未生成待办
Ext.define('Foss.ToDo.Model.pendingMsgModel', {
  extend: 'Ext.data.Model',
  fields: [
          { name: 'waybillNo',type:'string' }, //运单号
        { name: 'darftOrgCode',type:'string' }, //起草变更部门
        { name: 'darftOrgName',type:'string' }, //起草变更部门
        { name: 'darfter',type:'string' },  //变更申请人
        { name: 'failReason',type:'string' },  //变更申请人
        { name: 'changeItem',type:'string' },  //变更申请人
        { name: 'waybillRfcId',type:'string' },  //变更申请人
        { name: 'pendTodoId',type:'string' }, //运单号
        { name: 'pendOperateTime',convert:dateConvert }//更改受理时间
       ]
});
//创建一个待办事项store
Ext.define('Foss.ToDo.Store.pendingMsgStore', {
  extend: 'Ext.data.Store',
  //绑定一个模型
  model:'Foss.ToDo.Model.pendingMsgModel',
  //默认每页数据大小
  pageSize:10,
  proxy: {
    //代理的类型为内存代理
    type: 'ajax',
    //提交方式
    actionMethods:'POST',
    url:waybill.realPath('queryPendTodoMsgAction.action'),
    //定义一个读取器
    reader: {
      //以JSON的方式读取
      type: 'json',
      //定义读取JSON数据的根对象
      root: 'vo.pendingMsgActionDtoList',
      //返回总数
      totalProperty : 'totalCount'
    }
  },
  //事件监听
  listeners: {
  //查询事件
    beforeload : function(s, operation, eOpts) {
      //执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
      var queryParams = waybill.exceptMsg.pendingMsgForm.getValues();
      var form = waybill.exceptMsg.pendingMsgForm.getForm();
      if(!form.isValid())
      {
        return false;
      }
      var waybillNo = form.getValues().waybillNo;
      // 验证运单号输入的行数
      if (!Ext.isEmpty(waybillNo)) {
        var arrayWaybillNo = waybillNo.split('\n');
        if (arrayWaybillNo.length > 50) {
          Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation'), 'error', 3000); 
          return false;  
        }
        for (var i = 0; i < arrayWaybillNo.length; i++) {
          if (Ext.isEmpty(arrayWaybillNo[i])) {
            Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation'), 'error', 3000); 
            return false;  
          }
        }
      }
      Ext.apply(operation, {
        params : {          
            'vo.pendingMsgConditionDto.waybillNo': queryParams.waybillNo,
            'vo.pendingMsgConditionDto.darftOrgCode': queryParams.darftOrgCode,
            'vo.pendingMsgConditionDto.pendOperateTimeBegin': queryParams.pendOperateTimeBegin,
            'vo.pendingMsgConditionDto.pendOperateTimeEnd': queryParams.pendOperateTimeEnd,//keyWord
            'vo.pendingMsgConditionDto.keyWord': queryParams.keyWord,//keyWord
          }
      });  
    }
  }
});
//查询条件
Ext.define('Foss.ToDo.Form.pendTodoMsgForm',{
  extend:'Ext.form.Panel',
  title: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.queryCondition'),//查询条件,
  frame:true,
  collapsible: true,
  animCollapse: true,
  defaults: {
    margin: '5 10 5 10',
    labelWidth: 100
  },
  defaultType: 'textfield',
  layout: 'column',
  constructor: function(config){
    var me = this,
      cfg = Ext.apply({}, config);
    me.items = [{
      name : 'waybillNo',
      xtype : 'textarea',
      labelWidth: 60,
      fieldLabel : waybill.exceptMsg.i18n('pkp.predeliver.notifyDetailsAction.query.waybillNo'),
      columnWidth : .25,
      emptyText : waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation'),
      regex : /^([0-9]{8,10}\n?)+$/i,
      regexText : waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo.valitation')
    },{
      xtype : 'dynamicorgcombselector',
      name: 'darftOrgCode',
      fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.darftOrgName'),//变更申请部门,
      columnWidth: 0.25
    },{
      xtype: 'rangeDateField',
      fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.operateTime'),//受理时间,
      dateType: 'datetimefield_date97',
      fieldId: 'FOSS_pendingOperateTime_Id1',
      fromName: 'pendOperateTimeBegin',
      toName: 'pendOperateTimeEnd',
      editable: false,
      disallowBlank: true,
      fromValue: Ext.Date.format(waybill.exceptMsg.getTargetDate(new Date(),-6),'Y-m-d H:i:s'),
      toValue: Ext.Date.format(waybill.exceptMsg.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
      columnWidth: .50
    },{
        name:'keyWord',
        fieldLabel:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.keyWord'),//关键字,
        xtype: 'textfield',
        columnWidth: 0.25
    }];
    me.buttons = [{
      text:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.reset'),//重置,
      handler:function(){
        var form = this.up('form').getForm();
        form.findField('waybillNo').setValue("");
        form.findField('darftOrgCode').setValue("");
        form.findField('keyWord').setValue("");
        form.findField('pendOperateTimeBegin').setValue(Ext.Date.format(waybill.exceptMsg.getTargetDate(new Date(),-6),'Y-m-d H:i:s'));
        form.findField('pendOperateTimeEnd').setValue(Ext.Date.format(waybill.exceptMsg.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
      }
    },'->',{
      text:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.query'),//查询,
      disabled:!waybill.exceptMsg.isPermission('exceptmsgindex/exceptmsgindexquerybutton'),
      hidden:!waybill.exceptMsg.isPermission('exceptmsgindex/exceptmsgindexquerybutton'),
      cls:'yellow_button',
      handler:function(){
        var form = this.up('form').getForm();
        
        if(form.isValid())
        {
          waybill.exceptMsg.pagingBar.moveFirst();  
        }
        else
        {
          Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNoError'), 'error', 3000);
        }
      }
    }]
    me.callParent([cfg]);
  }
});

//待办事项
Ext.define('Foss.ToDo.pendTodoMsgGrid', {
  extend:'Ext.grid.Panel',
  //增加表格列的分割线
  columnLines: true,
  emptyText:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.emptyText'),//查询结果为空！,
  bodyCls: 'autoHeight',
  cls: 'autoHeight',
  //表格对象增加一个边框
  frame: true,
  id:'Foss_ToDo_pendTodoMsgGrid_PendTodo',
  stripeRows: true,
  //定义表格的标题
  title:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.pending'),//待办事项,
  collapsible: true,
  animCollapse: true, 
  selModel:Ext.create('Ext.selection.CheckboxModel'),
  dockedItems : [ {
        xtype : 'toolbar',
        dock : 'top',
        layout : 'column',
        items : [{
          xtype:'button',
          plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
            seconds: 3
          }),
          margin : '0 0 0 30',
          name:'pendingExceptMsgInfo',
          text:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.resetTodobatch'),
          handler: function() {
          
            //定义流水号数组
            var pendTodoIdList = new Array();
            records = Ext.getCmp('Foss_ToDo_pendTodoMsgGrid_PendTodo').getSelectionModel().getSelection();  
            
            for (var i = 0; i < records.length; i++) {  
              pendTodoIdList.push(records[i].data.pendTodoId);
            }  
            if (pendTodoIdList.length == 0) {  
              Ext.Msg.alert(waybill.exceptMsg.i18n('pkp.waybill.todoAction.tip'),waybill.exceptMsg.i18n('pkp.waybill.todoAction.choseOperateData'));
              return;
            }
          Ext.Msg.confirm( waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.makeSure'), function(btn,text){
          if(btn=="yes"){
            
            var newVo = {
              'vo':{
                'pendTodoIdList':pendTodoIdList
              }
            }
            
            Ext.Ajax.request({
                url:waybill.realPath('updatePendTodoFailReason.action'),
                jsonData: newVo,
                success: function(response){
                  var json = Ext.decode(response.responseText);
                  alert("重新生成待办成功！");
               },
                    exception : function(response) {
                      var result = Ext.decode(response.responseText);
                      Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), result.message, 'error', 3000);
                    }
            });
          }
        });
        }
        }
            ]
    } ],
  //定义表格列信息
  columns: [
  {
    header:'id',//id,
    dateIndex:'pendTodoId',
    hidden: true,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo'),//运单号, 
    //关联model中的字段名
    dataIndex: 'waybillNo',
    width:120,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.darftOrgName'),//变更申请部门, 
    //关联model中的字段名
    dataIndex: 'darftOrgName', 
    xtype: 'ellipsiscolumn',
    width:120,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.failReason'),//失败原因
    //关联model中的字段名
    dataIndex: 'failReason',
    width:300,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.changeItem'),//失败原因
    //关联model中的字段名
    dataIndex: 'changeItem',
    width:200,
    align: 'center'
  },{
    //字段标题
    header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.operateTime'),//更改受理时间, 
    //关联model中的字段名
    dataIndex: 'pendOperateTime', 
    width:100,
    flex : 1,
    align: 'center', 
    width:120,
    renderer:function(value){
      if(value!=null){
        return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
      }else{
        return null;
      }
    } 
  }],
  constructor: function(config){
    var me = this,
    cfg = Ext.apply({}, config);
    me.store = Ext.create('Foss.ToDo.Store.pendingMsgStore');
    //添加分页工具条
    me.bbar = Ext.create('Deppon.StandardPaging',{
      store:me.store,
      plugins: 'pagesizeplugin',
      displayInfo: true
    });
    waybill.exceptMsg.pagingBar = me.bbar;
    me.callParent([cfg]);
  }
});

//-------------------------------------------------------
//打印模型
Ext.define('Foss.ToDo.Model.ExceptTodoModel', {
  extend: 'Ext.data.Model',
  fields: [
        { name: 'labelGoodId',type:'string' },//打印ID
          { name: 'serialNo',type:'string' }, //流水号
        { name: 'waybillRfcId',type:'string' }, //是否已打印
        { name: 'waybillNo',type:'string' }, //是否已打印
        { name: 'exceptionMsg',type:'string' } //是否已打印
       ]
});
//代办异常详细信息Store
Ext.define('Foss.ToDo.Store.ExceptTodoStore', {
  extend: 'Ext.data.Store',
  //绑定一个模型
  model:'Foss.ToDo.Model.ExceptTodoModel',
  //定义一个代理对象
  proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
  }
});
////代办异常详细信息
Ext.define('Foss.ToDo.Form.ExceptTodoForm',{
  extend:'Ext.form.Panel',  
  title: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.exceptMsgProccess'),//异常信息,
  frame:true,
  layout:'column',
  waybillRfcId:null,
  items:[{
    xtype:'grid',
    id : 'Foss_ToDo_Form_ExceptTodo_CreateDetail',
    height : 400,
    width: 800,
    stripeRows: true,
    store: Ext.create('Foss.ToDo.Store.ExceptTodoStore'),   
    //定义表格列信息
    columns: [
      {
        header:waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.number'),//序号,
        dataIndex:'waybillRfcId',
        hidden: true,
        align: 'center'
      },{
        //字段标题
        header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo'),//运单流水号, 
        //关联model中的字段名
        dataIndex: 'waybillNo',
        align: 'center',
        width:100,
        xtype: 'openwindowcolumn', 
              windowClassName:'dpap.openwindowcolumn.Window'
      },{
        //字段标题
        header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.serialNo'),//运单流水号, 
        //关联model中的字段名
        dataIndex: 'serialNo',
        align: 'center',
        width:50
      },{
        //字段标题
        header: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.exceptionMsg'),//异常信息, 
        //关联model中的字段名
        dataIndex: 'exceptionMsg',
        align: 'center',
        width:650
      }],
      bbar :[{
        xtype: 'button',
            text: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.reCreateTodo'),//重新生成代办,
            disabled:!waybill.exceptMsg.isPermission('exceptmsgindex/exceptmsgindexprintbutton'),
            hidden:!waybill.exceptMsg.isPermission('exceptmsgindex/exceptmsgindexprintbutton'),
        handler: function() {
          var selection = Ext.getCmp('Foss_ToDo_Form_ExceptTodo_CreateDetail').getStore().getAt(0);
          var  waybillRfcId = selection.get('waybillRfcId');
          Ext.Msg.confirm( waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.makeSure'), function(btn,text){
            if(btn=="yes"){
              var newVo = {
                'vo':{
                  'exceptMsgConditionDto':{
                    'waybillRfcId':waybillRfcId
                  }
                }
              }
              
              Ext.Ajax.request({
                  url:waybill.realPath('updateTodoStatusAndNewPathDetail.action'),
                  jsonData: newVo,
                  success: function(response){
                    var json = Ext.decode(response.responseText);
                    alert("重置成功！");
                 },
                      exception : function(response) {
                        var result = Ext.decode(response.responseText);
                        Ext.ux.Toast.msg(waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tip'), result.message, 'error', 3000);
                      }
              });
            }
        });
        }
      },{
                xtype : 'label',
                margin : '0 0 0 15',
                text : waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.tipResetpathDetail') //提示语
              }]      
  }]
  
});

Ext.define('dpap.openwindowcolumn.grid', {
    extend:'Ext.form.Panel',
    title: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.exceptMsgProccess'),//异常信息,
    frame:true, 
    height: 160,
    width: 600,
    defaults: {
        margin:'5 10 5 10',
        anchor: '90%',
        labelWidth:60
    },
    defaultType : 'textfield',
    layout:'column',
    items: [{ 
        name: 'waybillNo',
        fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.waybillNo'),//运单流水号, 
        readOnly:true,
        columnWidth:1
    },{
        name: 'serialNo',
        fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.serialNo'),//运单流水号,
        columnWidth:1,
        readOnly:true
    },{ 
        name: 'exceptionMsg',
        fieldLabel: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.exceptionMsg'),//异常信息, 
        columnWidth:1,
        readOnly:true
    }],
    bindData: function(record){
        this.getForm().loadRecord(record);
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});
Ext.define('dpap.openwindowcolumn.Window', {
    extend : 'Ext.window.Window',
    title : waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.exceptMsgProccess'),//异常信息,
    modal : true,
    closeAction : 'hide',
    width : 650,
    height : 230,
    openwindowcolumnForm : null,
    getOpenwindowcolumnForm : function(){
        if(this.openwindowcolumnForm == null){
            this.openwindowcolumnForm = Ext.create('dpap.openwindowcolumn.grid');
        }   
        return this.openwindowcolumnForm;
    },
    bindData : function(record, cellIndex, rowIndex){
        this.getOpenwindowcolumnForm().getForm().loadRecord(record);
    },
    initComponent :function(config){
        var me = this ,cfg = Ext.apply({}, config);
        me.items = [ me.getOpenwindowcolumnForm() ];
        me.callParent(cfg);
    }
});
//-------------------------------------------------------------

//-----------------------------------------

//处理代办异常
waybill.exceptMsg.ExceptMsgForm=Ext.create('Foss.ToDo.Form.exceptMsgForm');
waybill.exceptMsg.ExceptMsgGrid=Ext.create('Foss.ToDo.ExceptMsgGrid');
//未生成待办
waybill.exceptMsg.pendingMsgForm=Ext.create('Foss.ToDo.Form.pendTodoMsgForm');
waybill.exceptMsg.pendTodoMsgGrid=Ext.create('Foss.ToDo.pendTodoMsgGrid');

waybill.exceptMsg.ExceptTodoForm=Ext.create('Foss.ToDo.Form.ExceptTodoForm');

//点击查询时候弹出来的Window
Ext.define('Foss.waybill.QueryExceptMsgWindow', {
  extend : 'Ext.window.Window',
  closeAction : 'hide',
  modal : 'true',
  cls: 'autoHeight',
  height : 550,
  width : 850,
  items : [waybill.exceptMsg.ExceptTodoForm]
});

//定义一个TabPanel，放置不同的Panel
waybill.exceptMsg.mainPanel = Ext.create('Ext.tab.Panel',{
  bodyCls: 'autoHeight',
  cls: 'innerTabPanel',
  items: [{
    title: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.processed'),//生成代办异常
    tabConfig: {
      width: 120
    },
    items:[waybill.exceptMsg.ExceptMsgForm,waybill.exceptMsg.ExceptMsgGrid]
  },{
    title: waybill.exceptMsg.i18n('pkp.waybill.exceptMsgAction.pending'),//未生成代办
    tabConfig: {
      width: 120
    },
    items:[waybill.exceptMsg.pendingMsgForm,waybill.exceptMsg.pendTodoMsgGrid]
  }]
});

Ext.onReady(function() {
  Ext.QuickTips.init();
  Ext.create('Ext.panel.Panel',{
    id: 'T_waybill-exceptMsgIndex_content',
    cls: "panelContentNToolbar",
    bodyCls: 'panelContentNToolbar-body',
    layout: 'auto',
    items: [waybill.exceptMsg.mainPanel],
    renderTo: 'T_waybill-exceptMsgIndex-body'
  });
});