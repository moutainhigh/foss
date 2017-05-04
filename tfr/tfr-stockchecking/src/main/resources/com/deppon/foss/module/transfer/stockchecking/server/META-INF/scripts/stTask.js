/***********************************************************************************************************
清仓任务管理

菜单入口： 清仓任务查询
1、 Panel → id:T_stockchecking-sttaskindex_content
 1.1、清仓任务查询条件
     Form → Foss.stockchecking.QueryForm
                任务状态 下拉框
       Store → Foss.Stockchecking.QueryForm.StTaskStatus.Store
                长短途货区
       Store → Foss.Stockchecking.goodsAreaUsage.Store    （公用）
         Model → Foss.Stockchecking.goodsAreaUsage.Model  （公用）
 1.2、清仓任务查询结果
     Grid → Foss.stockchecking.StTaskGrid
       Store → Foss.stockchecking.StTaskStore
         Model → Foss.stockchecking.StTaskModel
 1.3、手工处理新增、编辑页面
     Window → Foss.stockchecking.window.newStockchecking ↓ 2
 1.4、手工确认清仓任务页面
     Window → Foss.stockchecking.window.confirmStockchecking ↓ 3
 1.5、浏览清仓任务明细页面
     Window → Foss.stockchecking.window.viewStockchecking ↓ 4  getViewStockcheckingWindow
查询页面点击“新增”进入手工新建清仓任务界面
2、 Panel → Foss.stockchecking.newStockchecking.panel
	column ↓
	2.1 查询库区
       Form → Foss.stockchecking.newStockchecking.form.searchStock
       	长短途货区
         Store → Foss.Stockchecking.goodsAreaUsage.Store	(公用)
           Model → Foss.Stockchecking.goodsAreaUsage.Model	(公用)
                  任务状态 下拉框
         Store → Foss.Stockchecking.newStockchecking.form.StTaskStatus.Store
       Grid → Foss.stockchecking.newStockchecking.grid.searchStock
         Store → Foss.stockchecking.newStockchecking.store.searchStock
           Model → Foss.stockchecking.newStockchecking.model.searchStock
    2.2分隔
    2.3查询清仓人
       Form → Foss.stockchecking.newStockchecking.form.searchOperator
       Grid → Foss.stockchecking.newStockchecking.grid.searchOperator
         Store → Foss.stockchecking.store.searchOperator  (公用)
          Model → Foss.stockchecking.model.searchOperator (公用)
    2.4提交按钮
       Form → Foss.stockchecking.newStockchecking.form.submit
查询页面点击“确认”按钮进入手工确认清仓任务界面
3、Panel → Foss.stockchecking.confirmStockchecking.panel
   column ↓
   3.1、清仓任务信息及注解
       Form → Foss.stockchecking.confirmStockchecking.form.stTask
   3.2、查询清仓人
   	   Form → Foss.stockchecking.confirmStockchecking.form.searchOperator
       Grid → Foss.stockchecking.confirmStockchecking.grid.searchOperator
         Store → Foss.stockchecking.store.searchOperator  (公用)
          Model → Foss.stockchecking.model.searchOperator (公用)
   3.3、库存快照表格
                一级表格 运单号
       Grid → Foss.stockchecking.confirmStockchecking.grid.stTaskList
         Store → Foss.stockchecking.store.stTaskList	(公用)
           Model → Foss.stockchecking.model.stTaskList	(公用)
                     二级表格 流水号
         Grid → Foss.stockchecking.confirmStockchecking.grid.subStTaskList
   3.4、多货表格
                 一级表格
       Grid → Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList
         Store → Foss.stockchecking.store.stTaskList	(公用)
           Model → Foss.stockchecking.model.stTaskList	(公用)
                     二级表格 流水号
         Grid → Foss.stockchecking.confirmStockchecking.grid.subSuperfluousGoodsList
   3.5、提交按钮
       Form → Foss.stockchecking.confirmStockchecking.form.submit
   3.6、多货弹出窗口
       Window → Foss.stockchecking.confirmStockchecking.window.subSuperfluousGoods ↓ 3.6.1
       3.6.1、提交多货信息
         Form → Foss.stockchecking.confirmStockchecking.subSuperfluousGoods.form
4、Panel → Foss.stockchecking.viewStockchecking.panel
    Window → Foss.stockchecking.viewStockchecking.window.scanDetail ↓ 4.3.1 getSubScanDetailWindow
   column ↓
   4.1、查看清仓任务明细信息
        Form → Foss.stockchecking.viewStockchecking.form.stTask
   4.2、查看运单统计信息
        Grid → Foss.stockchecking.viewStockchecking.grid.stWaybillList
          Store → Foss.stockchecking.viewStockchecking.store.stWaybillList
           Model → Foss.stockchecking.viewStockchecking.model.stWaybillList
        4.3.1、扫描明细浏览页面
             Grid → Foss.stockchecking.viewStockchecking.scanDetail.grid
               Store → Foss.stockchecking.viewStockchecking.scanDetail.store
           		 Model → Foss.stockchecking.viewStockchecking.scanDetail.model
*********************************************************************************************************************/
/**当前转运场库区个数**/
	var goodsAreaCount=0;
	/**已完成清仓任务数**/
	var  taskDoneCount=0; 
	/**未完成任务，库区数**/
	var  areaUndoCount=0;

Ext.define('Foss.stockchecking.StTaskModel',{
	extend: 'Ext.data.Model',
	idProperty : 'id',
	fields: [
		{name: 'id', type: 'string'}, //
		{name: 'taskNo' , type: 'string'},//任务编号
		{name: 'goodsareaname', type: 'string'},//货区
		{name: 'ispda' , type: 'string'},//是否PDA
		{name: 'taskStatus', type: 'string', hidden:true},//任务状态
		{name: 'taskStatusValue', type: 'string', convert: function(value){
			if(value == 'DOING'){
				return stockchecking.i18n('Foss.stockchecking.sttask.status.doing');
			}else if(value == 'DONE'){
				return stockchecking.i18n('Foss.stockchecking.sttask.status.done');
			}else if(value == 'CANCEL'){
				return stockchecking.i18n('Foss.stockchecking.sttask.status.cancel');
			}
		}},
		{name: 'reportCode', type: 'string'},//清仓差异报告编号
		{name: 'handleStatus', type: 'string', convert: function(value){
			if(value == 'DOING'){
				return stockchecking.i18n('Foss.stockchecking.sttaskreport.status.doing');
			}else if(value == 'DONE'){
				return stockchecking.i18n('Foss.stockchecking.sttaskreport.status.done');
			}
		}},//清仓差异报告处理状态
		{name: 'empName', type: 'string'},//清仓员，取一个
		{name: 'createtime', type: 'string', convert : function(value){
			if(value != null){
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}else{
				return null;
			}
		}},//清仓任务建立时间
		{name: 'finishtime', type: 'string', convert : function(value){
			if(value != null){
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}else{
				return null;
			}
		}},//清仓任务完成时间
		{name:'passTwoHours',type:'string'}//标记行颜色
	]
});

Ext.define('Foss.Stockchecking.QueryForm.Operator.Model',{
	extend: 'Ext.data.Model',
	fields: [
	  {name: 'id', type: 'string'},
      {name: 'empName', type: 'string'},
      {name: 'empCode', type: 'string'}
	]
});

Ext.define('Foss.Stockchecking.goodsAreaUsage.Model',{
	extend: 'Ext.data.Model',
	fields: [
	     {name: 'valueName', type: 'string'},
	     {name: 'valueCode', type: 'string'}
	]
});

Ext.define('Foss.stockchecking.model.searchOperator',{
	extend: 'Ext.data.Model',
	idProperty : 'id',
	fields: [
	    {name: 'id', type: 'string'},
		{name: 'empName', type: 'string'},
		{name: 'empCode', type: 'string'}
		
	]
});

Ext.define('Foss.stockchecking.model.stTaskList',{
	extend: 'Ext.data.Model',
	idProperty: 'waybillNo',
	fields: [
    	{name: 'waybillNo', type: 'string'}, //运单号
    	{name: 'wayBillNoDesc' , type: 'string'},//运单号
    	{name: 'serialNoNum', type: 'string'},//运单数
    	{name: 'serialNoNumDesc' , type: 'string'}//库存件数
    ]
});

Ext.define('Foss.Stockchecking.QueryForm.StTaskStatus.Store', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"ALL", "name":stockchecking.i18n('Foss.stockchecking.checkbox.defaultvalue')},
        {"value":"DOING", "name":stockchecking.i18n('Foss.stockchecking.sttask.status.doing')},
        {"value":"DONE", "name":stockchecking.i18n('Foss.stockchecking.sttask.status.done')},
        {"value":"CANCEL", "name":stockchecking.i18n('Foss.stockchecking.sttask.status.cancel')}
    ]
});

Ext.define('Foss.Stockchecking.newStockchecking.form.StTaskStatus.Store', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"ALL", "name":stockchecking.i18n('Foss.stockchecking.checkbox.defaultvalue')},
        {"value":"DOING", "name":stockchecking.i18n('Foss.stockchecking.sttask.status.doing')},
        {"value":"DONE", "name":stockchecking.i18n('Foss.stockchecking.sttask.status.done')},
        {"value":"CANCEL", "name":stockchecking.i18n('Foss.stockchecking.sttask.status.cancel')},
        {"value":"HAVENOT", "name":stockchecking.i18n('Foss.stockchecking.sttask.status.havenot')}
    ]
});

Ext.define('Foss.Stockchecking.goodsAreaUsage.Store',{
	extend: 'Ext.data.Store',
	model: 'Foss.Stockchecking.goodsAreaUsage.Model',
	autoLoad: true,
	proxy: {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods:'POST',
		url: ContextPath.TFR_STOCK + '/tfrcommon/loadDictDataCombox.action',
		reader : {
			type : 'json',
			root : 'tfrCommonVO.baseDataDictDtoList',
			successProperty: 'success'
		}
	},
    listeners: {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'tfrCommonVO.termsCode' : 'BSE_GOODSAREA_USAGE'  //DictionaryConstants.BSE_GOODSAREA_USAGE
				}
			});	
		}
	}
});

Ext.define('Foss.stockchecking.StTaskStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stockchecking.StTaskModel',
	pageSize:10,
	proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: stockchecking.realPath('queryStTask.action'),
		reader : {
			type : 'json',
			root : 'stockcheckingVO.stTaskDtoList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryParams = stockchecking.stockcheckingQueryform.getValues();
			Ext.apply(operation, {
				params : {
					'stockcheckingVO.stTaskDto.taskNo' : queryParams.taskNo,
					'stockcheckingVO.stTaskDto.goodsArea' :queryParams.goodsArea ,
					'stockcheckingVO.stTaskDto.goodsAreaUsage' : queryParams.goodsAreaUsage,
					'stockcheckingVO.stTaskDto.taskStatus' : queryParams.taskStatus,
					'stockcheckingVO.stTaskDto.empCode' : queryParams.empCode,
//					'stockcheckingVO.stTaskDto.createtime' : Ext.Date.parse(queryParams.createtime, 'Y-m-d H:i:s'),
//					'stockcheckingVO.stTaskDto.finishtime' : Ext.Date.parse(queryParams.finishtime,'Y-m-d H:i:s')
					'stockcheckingVO.stTaskDto.createtime' : queryParams.createtime,
					'stockcheckingVO.stTaskDto.finishtime' : queryParams.finishtime
				}
			});	
		},
		load: function(store, records, successful, epots){
			if(store.getCount() == 0){
				 Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.nosearchresult'));
			}
		},
		datachanged:function( store,eOpts){
			if(Ext.getCmp('statistics').hidden==false){
				statistics();
			}
		}
		
	}
});

Ext.define('Foss.stockchecking.newStockchecking.model.searchStock',{
	extend: 'Ext.data.Model',
	idProperty : 'goodsAreaCode',
	fields: [
		{name: 'goodsAreaCode', type: 'string'}, //货区编号
		{name: 'goodsAreaName' , type: 'string'},//货区名称
		{name: 'waybillNum', type: 'string'},//运单数
		{name: 'stockGoodsQty' , type: 'string'},//库存件数
		{name: 'goodWeightTotal', type: 'string'},//重量
		{name: 'goodVolumeTotal', type: 'string'},//体积
		{name: 'deptNo', type: 'string'},//部门编号
		{name: 'taskStatus', type: 'string', convert: function(value){
			if(value == 'DOING'){
				return stockchecking.i18n('Foss.stockchecking.sttask.status.doing');
			}else if(value == 'DONE'){
				return stockchecking.i18n('Foss.stockchecking.sttask.status.done');
			}else if(value == 'CANCEL'){
				return stockchecking.i18n('Foss.stockchecking.sttask.status.cancel');
			}else if(value == 'HAVENOT'){
				return stockchecking.i18n('Foss.stockchecking.sttask.status.havenot');
			}
		}}//清仓任务状态
	]
});

Ext.define('Foss.stockchecking.viewStockchecking.model.stWaybillList',{
	extend: 'Ext.data.Model',
	idProperty : 'waybillNo',
	fields: [
	         {name: 'waybillNo', type: 'string'}, //运单号
	         {name: 'productCode' , type: 'string'},//产品编号
	         {name: 'productCodeDesc' , type: 'string'},//产品编号描述
	         {name: 'goodsAreaNum', type: 'string'},//库存件数
	         {name: 'scanNum', type: 'string'},//扫描件数
	         {name: 'weight' , type: 'string'},//重量
	         {name: 'volume', type: 'string'}//体积
	]
});

Ext.define('Foss.stockchecking.viewStockchecking.scanDetail.model',{
	extend: 'Ext.data.Model',
	idProperty : 'serialNo',
	fields: [
	         {name: 'serialNo', type: 'string'}, //流水号
	         {name: 'scanStatus' , type: 'string'},//扫描状态
	         {name: 'scanStatusDesc' , type: 'string', convert: function(value){
	 			if(value == 'DONE'){
					return stockchecking.i18n('Foss.stockchecking.sttask.scanstatus.done');
				}else if(value == 'HAVENOT'){
					return stockchecking.i18n('Foss.stockchecking.sttask.scanstatus.havenot');
				}else if(value == 'MANUAL'){
					return stockchecking.i18n('Foss.stockchecking.sttask.scanstatus.manual');
				}
			}},//扫描状态描述
	         {name: 'goodsStatus', type: 'string'},//货物状态
	         {name: 'goodsStatusDesc', type: 'string', convert: function(value){
		 			if(value == 'OK'){
						return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.ok');
					}else if(value == 'LACK'){
						return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.lack');
					}else if(value == 'SURPLUS'){
						return stockchecking.i18n('Foss.stockchecking.sttask.goodsstatus.surplus');
					}
				}},//货物状态描述
	         {name: 'uploadTime' , type: 'string', convert : function(value){
	 			if(value != null){
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				}else{
					return null;
				}
			}}//操作时间
    ]
});

Ext.define('Foss.stockchecking.newStockchecking.store.searchStock',{
	extend: 'Ext.data.Store',
	model: 'Foss.stockchecking.newStockchecking.model.searchStock'
});

Ext.define('Foss.stockchecking.store.searchOperator',{
	extend: 'Ext.data.Store',
	model: 'Foss.stockchecking.model.searchOperator'
});

Ext.define('Foss.stockchecking.store.stTaskList',{
	extend: 'Ext.data.Store',
	model: 'Foss.stockchecking.model.stTaskList'
});

Ext.define('Foss.stockchecking.viewStockchecking.scanDetail.store',{
	extend: 'Ext.data.Store',
	model: 'Foss.stockchecking.viewStockchecking.scanDetail.model'
});

Ext.define('Foss.stockchecking.viewStockchecking.store.stWaybillList',{
	extend: 'Ext.data.Store',
	model: 'Foss.stockchecking.viewStockchecking.model.stWaybillList'
});

Ext.define('Foss.stockchecking.QueryForm',{
	extend: 'Ext.form.Panel',
	id:'Foss.stockchecking.QueryForm.id',
	layout: 'column',
	frame: true,
	border: false,
	title : stockchecking.i18n('Foss.stockchecking.search.title'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.taskNo'),  //任务编号
		name: 'taskNo',
		columnWidth:.3
	},{
		xtype: 'combo',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.taskStatus'), //清仓任务状态
		name: 'taskStatus',
		store: Ext.create('Foss.Stockchecking.QueryForm.StTaskStatus.Store'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : 'ALL',	
		editable :false,
		columnWidth:.2
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'combo',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsAreaUsage'), //库区类别
		name: 'goodsAreaUsage',
		displayField: 'valueName',
		valueField:'valueCode', 
		value : 'ALL',
		columnWidth:.2,
		store:Ext.create('Foss.Stockchecking.goodsAreaUsage.Store'),
		editable:false
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'commonemployeeselector',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'),  //清仓员
		name: 'empCode',
		parentOrgCode : FossUserContext.getCurrentDept().code,
		columnWidth:.2
	},{
		xtype: 'rangeDateField',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.createtime'),  //任务创建时间
		fieldId: 'Foss_stockchecking_QueryForm_stTaskCreateTime_ID',
		dateType: 'datetimefield_date97',
		disallowBlank: true,
		fromName: 'createtime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(), 
										    new Date().getMonth(), 
										    new Date().getDate() - 7,
										    new Date().getHours() + 1,
										    new Date().getMinutes(),
										    new Date().getSeconds()),
								   'Y-m-d H:i:s'),	//默认查询7天内的数据
		toName: 'finishtime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(), 
							     new Date().getMonth(), 
							     new Date().getDate(),
							     new Date().getHours() + 1,
							     new Date().getMinutes(),
							     new Date().getSeconds()),
				   				'Y-m-d H:i:s'),
		columnWidth: .5
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'commongoodsareaselector',
		fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), //货区
		name: 'goodsArea',
		//deptCode: FossUserContext.getCurrentDeptCode(),
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:stockchecking.i18n('Foss.stockchecking.button.reset'),	//重置
			columnWidth:.08,
			handler:function(){
				var theForm = this.up('form').getForm();
				theForm.reset();
				theForm.findField('createtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), 
					    new Date().getMonth(), 
					    new Date().getDate() - 7,
					    new Date().getHours() + 1,
					    new Date().getMinutes(),
					    new Date().getSeconds()),
						'Y-m-d H:i:s'));
				theForm.findField('finishtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), 
					     new Date().getMonth(), 
					     new Date().getDate(),
					     new Date().getHours() + 1,
					     new Date().getMinutes(),
					     new Date().getSeconds()),
		   				'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:stockchecking.i18n('Foss.stockchecking.button.search'),
			disabled: !stockchecking.isPermission('stockchecking/queryStTaskButton'),
			hidden: !stockchecking.isPermission('stockchecking/queryStTaskButton'),
			cls: 'yellow_button',
			columnWidth:.08,
			handler: function() {
				var operators = stockchecking.stockcheckingQueryform.getValues().empCode;
				var startTime = stockchecking.stockcheckingQueryform.getValues().createtime;
				var endTime = stockchecking.stockcheckingQueryform.getValues().finishtime;
				var difTime = 0;
				
//				if(operators == null || operators == ''){
//					Ext.MessageBox.alert('警告', '清仓人不能为空');
//					
//					return;
//				}
				
				if(startTime.length == 0 || endTime.length == 0){
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.search.validator.createtime.notnull')); //“任务创建时间”不能为空
					
					return;
				}
				
				difTime = parseInt(Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y-m-d H:i:s')) / (24 * 60 * 60 * 1000);
				if(difTime > 7){
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.search.validator.createtime.span.limit')); //“任务创建时间”跨度不能超过7天
					
					return;
				}
				
				stockchecking.pagingBar.moveFirst();
			}
		}]
	  }
	],
	constructor: function(config){
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.stockchecking.StTaskGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss.stockchecking.StTaskGrid.id',
	height: 500,
	autoScroll:true,
	columnLines: true,
    frame: true,
    forceFit: true,
    emptyText: stockchecking.i18n('Foss.stockchecking.warn.resultnull'),	//查询结果为空
    enableColumnHide: false,
    sortableColumns: false,
	columns: [{
		xtype:'actioncolumn',
		width:90,
		flex: 1,
		text: stockchecking.i18n('Foss.stockchecking.button.operator'),  //操作
		align: 'center',
		items: [{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.edit'),  //编辑
                iconCls:'deppon_icons_edit',
                handler: function(grid, rowIndex, colIndex) {
                	/*
                	 * 打开编辑清仓窗口，由stTaskId获取库区信息及清仓人信息
                	 * 库区信息不能修改，只浏览，清仓人信息可以做更新操作
                	 * 显示“保存”、“取消任务”按钮、隐藏“新增清仓任务”按钮
                	 */
                	stockchecking.isEditUI = true;
                	var stTaskId = grid.getStore().getAt(rowIndex).data.id;
                	Ext.getCmp('T_stockchecking-sttaskindex_content').getNewStockcheckingWindow().show();
					
					//清空查询库区form查询条件及grid
					var goodsAreaSearchForm = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.id');
					goodsAreaSearchForm.getForm().reset();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.searchButton.id').hide();
					//隐藏件数控件
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').hide();
					
					Ext.Ajax.request({
						url: stockchecking.realPath('queryGoodsStock.action'),
						params: {
							'stockcheckingVO.goodsStockDto.stTaskId':stTaskId
						},
						success: function(response){
							result = Ext.decode(response.responseText);
							
							var searchStockGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
							//加载结果集数据
							searchStockGrid.store.loadData(result.stockcheckingVO.goodsStockDtoList);
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		    			}
					});
					
					//清空查询清仓人form查询条件及grid
					var operatorForm = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchOperator.id');
					operatorForm.getForm().reset();
					
					Ext.Ajax.request({
						url: stockchecking.realPath('queryOperatorsByStTaskId.action'),
						params: {
							'stockcheckingVO.stTaskDto.id':stTaskId
						},
						success: function(response){
							result = Ext.decode(response.responseText);
							
							var searchOperatorGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.id');
							//加载结果集数据
							searchOperatorGrid.store.loadData(result.stockcheckingVO.stOperatorEntityList);
							Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.operatorNum.id').setValue(result.stockcheckingVO.stOperatorEntityList.length);
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		    			}
					});
					
//					显示“保存”、“取消任务”按钮、隐藏“新增清仓任务”按钮
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.updateButton.id').show();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.cancelButton.id').show();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.newButton.id').hide();
//					保存清仓任务ID
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.stTask.id').setValue(stTaskId);
                }
            },{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.confirm'), //确认
                iconCls:'deppon_icons_affirm',
                handler: function(grid, rowIndex, colIndex) {	
//                	打开确认清仓窗口
                	Ext.getCmp('T_stockchecking-sttaskindex_content').getConfirmStockcheckingWindow().show();
                	
                	var stTaskId = grid.getStore().getAt(rowIndex).data.id;
                	
                	
                	//清空清仓人form查询条件及grid
					var operatorForm = Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.searchOperator.id');
					operatorForm.getForm().reset();
					
//					由stTaskId获取库区信息、清仓人信息、库存快照
					Ext.Ajax.request({
						url: stockchecking.realPath('selectStTaskById.action'),
						params: {
							'stockcheckingVO.stTaskDto.id':stTaskId
						},
						success: function(response){
							result = Ext.decode(response.responseText);
							
//							载入清仓任务信息
							var stTaskForm = Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.stTask.id');
							
							stTaskForm.getForm().findField('id').setValue(result.stockcheckingVO.stTaskDto.id);
							stTaskForm.getForm().findField('taskNo').setValue(result.stockcheckingVO.stTaskDto.taskNo);
							stTaskForm.getForm().findField('goodsareaname').setValue(result.stockcheckingVO.stTaskDto.goodsareaname);
							stTaskForm.getForm().findField('createtime').setValue(Ext.Date.format(new Date(result.stockcheckingVO.stTaskDto.createtime),'Y-m-d H:i:s'));
//							初始化清仓人grid
							var searchOperatorGrid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.id');
//							加载清仓人结果集数据
							searchOperatorGrid.store.loadData(result.stockcheckingVO.stOperatorEntityList);
							Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.operatorNum.id').setValue(result.stockcheckingVO.stOperatorEntityList.length);
//							加载货物清单信息，按运单分组
							var stTaskWaybillNoListGrid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.stTaskList.id');
							stTaskWaybillNoListGrid.store.loadData(result.stockcheckingVO.stTaskWaybillNoList);
//							将货物清单放入页面的map对象中
							Ext.Array.each(result.stockcheckingVO.stTaskWaybillNoList, function(record, i){
								stockchecking.stTaskListMap.add(record.waybillNo, record);
							});
							
							var superfluousGoodsListGrid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList.id');
							superfluousGoodsListGrid.store.removeAll();
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		    			}
					});
                }
            },{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.export'),
                disabled: !stockchecking.isPermission('stockchecking/exportTaskButton'),
                hidden: !stockchecking.isPermission('stockchecking/exportTaskButton'),
                iconCls:'deppon_icons_export',
                handler: function(grid, rowIndex, colIndex) {	
                	var stTaskId = grid.getStore().getAt(rowIndex).data.id;
                	
                	if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
                	}
                	
                	Ext.Ajax.request({
                		url: stockchecking.realPath('exportTaskById.action'),
		    			form: Ext.fly('downloadAttachFileForm'),
		    			method : 'POST',
		    			params: {
							'stockcheckingVO.stTaskDto.id':stTaskId
						},
		    			isUpload: true,
		    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.export.title'), result.message);
		    			}
		    		});
                }
            },{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.view'),
                iconCls:'deppon_icons_showdetail',
                handler: function(grid, rowIndex, colIndex) {
//                	打开查看清仓任务明细窗口
                	Ext.getCmp('T_stockchecking-sttaskindex_content').getViewStockcheckingWindow().show();
                	
                	var stTaskId = grid.getStore().getAt(rowIndex).data.id;
                	
//					由stTaskId获取清仓任务明细信息
					Ext.Ajax.request({
						url: stockchecking.realPath('viewStTaskById.action'),
						params: {
							'stockcheckingVO.stTaskDto.id':stTaskId
						},
						timeout:300000,
						success: function(response){
							result = Ext.decode(response.responseText);
							
//							载入清仓任务信息
							var stTaskForm = Ext.getCmp('Foss.stockchecking.viewStockchecking.form.stTask.id');
							
							stTaskForm.getForm().findField('id').setValue(result.stockcheckingVO.stTaskDto.id);
							stTaskForm.getForm().findField('taskNo').setValue(result.stockcheckingVO.stTaskDto.taskNo);
							stTaskForm.getForm().findField('goodsareaname').setValue(result.stockcheckingVO.stTaskDto.goodsareaname);
							stTaskForm.getForm().findField('createtime').setValue(Ext.Date.format(new Date(result.stockcheckingVO.stTaskDto.createtime),'Y-m-d H:i:s'));
							stTaskForm.getForm().findField('finishtime').setValue(result.stockcheckingVO.stTaskDto.finishtime == null ? '' : Ext.Date.format(new Date(result.stockcheckingVO.stTaskDto.finishtime),'Y-m-d H:i:s'));
							stTaskForm.getForm().findField('ispda').setValue(result.stockcheckingVO.stTaskDto.ispda == 'Y' ? '是' : '否');
							var taskStatus = "";
							if(result.stockcheckingVO.stTaskDto.taskStatus == "DOING"){
								taskStatus = stockchecking.i18n('Foss.stockchecking.sttask.status.doing');
							}else if(result.stockcheckingVO.stTaskDto.taskStatus == "DONE"){
								taskStatus = stockchecking.i18n('Foss.stockchecking.sttask.status.done');
							}else if(result.stockcheckingVO.stTaskDto.taskStatus == "CANCEL"){
								taskStatus = stockchecking.i18n('Foss.stockchecking.sttask.status.cancel');
							}
							stTaskForm.getForm().findField('taskStatus').setValue(taskStatus);
							
							var operatorNames = '';
							Ext.Array.each(result.stockcheckingVO.stOperatorEntityList, function(data){
								operatorNames += data.empName + '';
							});
							stTaskForm.getForm().findField('operators').setValue(operatorNames + '(共：'+ result.stockcheckingVO.stOperatorEntityList.length +'人)');

//							初始化运单统计grid
							var stWaybillInfoGrid = Ext.getCmp('Foss.stockchecking.viewStockchecking.grid.stWaybillList.id');
//							加载运单统计结果集数据
							stWaybillInfoGrid.store.loadData(result.stockcheckingVO.stWaybillInfoDtoList);
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('查询失败', result.message);
		    			}
					});
                }
            }],
            renderer:function(value, metadata, record){
            	//人工清仓任务，可以编辑(撤销)、确认、导出、查看
            	if(record.data.ispda == 'Y'){
            		this.items[0].iconCls = '';
            		this.items[1].iconCls = '';
            	}else if(record.data.ispda == 'N'){
	            	if(record.data.taskStatus == 'DOING'){
	            		this.items[0].iconCls = 'deppon_icons_edit';
	            		this.items[1].iconCls = 'deppon_icons_affirm';
	            	}else if(record.data.taskStatus == 'DONE'){
	            		this.items[0].iconCls = '';
	            		this.items[1].iconCls = '';
	            	}else if(record.data.taskStatus == 'CANCEL'){
	            		this.items[0].iconCls = '';
	            		this.items[1].iconCls = '';
	            	}else{
	            		this.items[0].iconCls = '';
	            		this.items[1].iconCls = '';
	            	}
            	}
            }
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.taskNo'),  //任务编号
			dataIndex: 'taskNo',
			fixed: true,
			width: 120,
			flex:1
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), //货区
			dataIndex: 'goodsareaname',
			width:80,
			flex: 1
		},{
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.ispda'), //是否PDA 
			dataIndex: 'ispda',
			width:30,
			flex: 0.5,
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					if(value == 'Y'){
						return stockchecking.i18n('Foss.stockchecking.yes.defaultvalue'); //是
					}
					else if(value == 'N'){
						return stockchecking.i18n('Foss.stockchecking.no.defaultvalue');  //否
					}
				}
			}
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.taskStatus'), //任务状态
			dataIndex: 'taskStatusValue',
			width:65,
			flex: 0.8
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.reportCode'), //清仓差异报告
			dataIndex: 'reportCode',
			fixed: true,
			width: 125,
			flex: 1
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttaskreport.model.handleStatus'), //报告处理状态
			dataIndex: 'handleStatus',
			width:65,
			flex: 0.8
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'), //清仓员
			dataIndex: 'empName',
			width:70,
			flex: 0.8
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.createtime'), //任务创建时间
			dataIndex: 'createtime',
			fixed: true,
			width: 145,
			flex: 1
		},{ xtype: 'ellipsiscolumn',
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.finishtime'), //任务完成时间
			dataIndex: 'finishtime',
			fixed: true,
			width: 145,
			flex: 1
		}],
		dockedItems: [{
		    xtype: 'toolbar',
		    dock: 'bottom',
		    layout : 'column',
		    defaults: {
				xtype : 'textfield',
				readOnly : true,
				labelWidth : 80
			},
			items: [{
					fieldLabel : '统计信息',
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 0.5,
					value: null,
					id : 'statistics'
				}]
		  }],
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stockchecking.StTaskStore');
			me.tbar = [{
				xtype:'button',
				text:stockchecking.i18n('Foss.stockchecking.button.new'),  //新增
				handler: function(){
					/*
                	 * 打开清仓编辑新增窗口，由登陆人所在部门编号获取库区信息及清仓人信息
                	 * 隐藏“保存”、“取消任务”按钮、显示“新增清仓任务”按钮
                	 */
					//是否编辑界面
					stockchecking.isEditUI = false;
					Ext.getCmp('T_stockchecking-sttaskindex_content').getNewStockcheckingWindow().show();
					//设置宽度
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.gap.id').columnWidth = '0.4';
					//清空查询库区form查询条件及grid
					var goodsAreaSearchForm = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.id');
					goodsAreaSearchForm.getForm().reset();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.searchButton.id').show();
					
					var goodsAreaSearchGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
					goodsAreaSearchGrid.store.removeAll();
					
					Ext.Ajax.request({
						url: stockchecking.realPath('queryGoodsStock.action'),
						success: function(response){
							result = Ext.decode(response.responseText);
							
							//加载结果集数据
							goodsAreaSearchGrid.store.loadData(result.stockcheckingVO.goodsStockDtoList);
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		    			}
					});
					
					//清空查询清仓人form查询条件及grid
					var operatorForm = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchOperator.id');
					operatorForm.getForm().reset();
					
					var operatorGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.id');
					operatorGrid.store.removeAll();
					Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.operatorNum.id').setValue(0);
					
//					隐藏“保存”、“取消任务”按钮、显示“新增清仓任务”按钮
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.updateButton.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.cancelButton.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.newButton.id').show();
//					将清仓任务ID置空
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.stTask.id').setValue('');
					//隐藏件数控件
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').hide();
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').hide();
				}
			}];
			/*me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});*/
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store : me.store,
				pageSize : 10,
				maximumSize : 50,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['20', 20], ['30', 30], ['40', 40],['50', 50]]
				})
			});
			stockchecking.pagingBar = me.bbar;
			me.callParent([cfg]);
		},
		viewConfig: {
			enableTextSelection: true,
			stripeRows : false,
			getRowClass: function(record, rowParams, rp, store) {
				var passTwoHours = record.get('passTwoHours');
				if(passTwoHours == 'Y') {
					return 'stockchecking-row-red';
				}
			}
	    }
});

/*** 手工建立清仓任务 ***/
//查询库存 条件
Ext.define('Foss.stockchecking.newStockchecking.form.searchStock',{
	extend: 'Ext.form.Panel',
	id:'Foss.stockchecking.newStockchecking.form.searchStock.id',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:90
	},
	items:[
	    {
			xtype: 'combo',
			fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsAreaUsage'), //库区类别
			name: 'goodsAreaUsage',
			id:'Foss.stockchecking.newStockchecking.form.searchStock.goodsAreaUsage.id',
			displayField: 'valueName',
			valueField:'valueCode', 
			value : 'ALL',
//			emptyText:'333',
			columnWidth:0.4,
			store:Ext.create('Foss.Stockchecking.goodsAreaUsage.Store'),
			editable:false
		},{
	    	xtype: 'commongoodsareaselector',
	        fieldLabel:stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), //货区 
	        name:'goodsArea', 
	        //deptCode: FossUserContext.getCurrentDeptCode(),
	        id:'Foss.stockchecking.newStockchecking.form.searchStock.goodsArea.id',
	        columnWidth:0.6,
	        listeners : {
		    	'select' : function(combo, record, index, eOpts){
		    		//goodArea是获取的编号
		    		var goodsArea = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsArea.id').getValue();
		    		/*var theForm = this.up('form').getForm();//获取Form
		    		var selectedGoodsArea = theForm.findField('goodsArea');//找到某个选择器
		    		var text = selectedGoodsArea.getRawValue()//获取选择器选择的显示值
		    		if(text=='异常货区'||text=='异常货库区'){
	    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').enable(true);
	    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.gap.id').columnWidth = '0.0';
	    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').show();
	    			 }else{
	    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').enable(false);
	    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').hide();
	    			 }*/
		    		//判断用户选择的是不是异常货区,如果是异常货区将显示清仓类别控件(快递和零担)
		    		Ext.Ajax.request({
		    			url:stockchecking.realPath('isExceptionArea.action'),
		    			params:{'stockcheckingVO.goodsStockDto.goodsAreaType':goodsArea},
		    			success:function(response){
		    			 result=Ext.decode(response.responseText);
		    			 if(result.stockcheckingVO.goodsStockDto.goodsAreaType=='excp'){
		    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').enable(true);
		    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.gap.id').columnWidth = '0.0';
		    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').show();
		    			 }else{
		    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').enable(false);
		    				 Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').hide();
		    			 }
		    			},
		    			exception : function(response){
		    				result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert('异常提示', result.message);
		    			}
		    		});
		    		Ext.Ajax.request({
						url: stockchecking.realPath('isBasStation.action'),
						params: {
							'stockcheckingVO.goodsStockDto.goodsArea':goodsArea
						},
						success: function(response){
							result = Ext.decode(response.responseText);
							//驻地派送货区
							stockchecking.isBasStation = result.stockcheckingVO.basStation;
							if(stockchecking.isBasStation) {
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.gap.id').columnWidth = '0.0';
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').show();
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').show();
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
								//判断是否试点外场
								///
								Ext.Ajax.request({
									url:stockchecking.realPath('isTestTrans.action'),
									success: function(response) {
										resultStr = Ext.decode(response.responseText);
										stockchecking.isTestTrans = resultStr.stockcheckingVO.testTrans;
										if(stockchecking.isTestTrans){
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').show();
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').show();
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').disable();
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').disable();
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').disable();
											
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').setValue(null);
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').setValue(null);
										}else {
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').hide();
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').hide();
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').enable();
											Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').enable();
										}
									},
									exception : function(response) {
										var resultStr = Ext.decode(response.responseText);
					    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), resultStr.message);
									}
								});
								///
							} else {
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.gap.id').columnWidth = '0.4';
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').hide();
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').hide();
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').hide();
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').hide();
							}
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		    			}
					});
		    	},
		    	'change':function(cbk,newValue,oldValue){
		    		if(Ext.isEmpty(newValue)) {
						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.gap.id').columnWidth = '0.4';
						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').hide();
						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').hide();
						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').hide();
						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').hide();
		    		}
		    		var goodsAreaSearchGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
					goodsAreaSearchGrid.store.removeAll();
		    	}
		    }
	    },{
			xtype: 'combo',
			fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.taskStatus'), //清仓任务状态
			name: 'taskStatus',
			id:'Foss.stockchecking.newStockchecking.form.searchStock.taskStatus.id',
			store: Ext.create('Foss.Stockchecking.newStockchecking.form.StTaskStatus.Store'),
			queryMode: 'local',
			displayField: 'name',
			valueField: 'value',
			value : 'ALL',
			editable :false,
			columnWidth:0.4
		},{
			xtype: 'combo',
			fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.type'), //类别\
			name:'goodsType',
			id:'Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id',
			columnWidth:0.4,
			store:{
				fields:[{name: 'tid', type: 'string'},
				        {name: 'tname', type: 'string'}
				        ],
				data:[{'tid':'ALL','tname':'全部'},{'tid':'Y','tname':'快递'},{'tid':'N','tname':'零担'}]
			},
			displayField:'tname',
			valueField:'tid',
			value:'全部',
			editable :false,
			hidden:true,
			disabled:true
		},{
			//fieldLabel: scheduling.passOrderVehicle.i18n('foss.scheduling.passOrderVehicle.ifNeedReleaseBill'),//'是否生成放行需求',
			xtype: 'checkboxgroup',
	        vertical: true,
	        columnWidth:0.2,
			labelWidth:1,
			margin:'10 0 0 10',
			name:'receiveMethod',
			id:'Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id',
			allowBlank:true,
			items: [
	           {   boxLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.pickup'), 
	        	   name: 'receiveMethod', 
	        	   inputValue: 'PICKUP',
	        	   listeners:{
	   				'change': function(cbk,newValue,oldValue){
	   					//若为自提
	   					if(cbk.getValue() == true) {
	   						var items = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').items;
	   						//stockchecking.receiveMethod = "PICKUP";
	   						//派送不可同时选择
	   						items.items[1].setValue(false);
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').setValue(null);
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').disable();
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').enable();
							Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').enable();
	   					} else {
	   						//stockchecking.receiveMethod = "";
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').setValue(null);
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').disable();
	   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').disable();
							Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').disable();
	   					}
	   					//清空查询结果列表，避免改变查询条件忘记重新查询导致后台保存数据错误
	   					var goodsAreaSearchGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
						goodsAreaSearchGrid.store.removeAll();
	   				}
	   			}
	           },{ boxLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.deliver'), 
	        	   name: 'receiveMethod', 
	        	   inputValue: 'DELIVER',
	        	   listeners:{
		   				'change': function(cbk,newValue,oldValue){
		   					//若为派送，则分区名称可编辑
		   					if(cbk.getValue() == true) {
		   						var items = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').items;
		   						//stockchecking.receiveMethod = "DELIVER";
		   						//自提不可同时选择
		   						items.items[0].setValue(false);
		   						
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').setValue(null);
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').enable();
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').disable();
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').disable();
		   					} else {
		   						//stockchecking.receiveMethod = "";
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').setValue(null);
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').disable();
		   						Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').disable();
								Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').disable();
		   					}
		   					var goodsAreaSearchGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
							goodsAreaSearchGrid.store.removeAll();
		   				}
		   			}
	           }
	       ]       
	       
		},{
			 xtype:'commonSendDistrictMapSelector',
		      //分区名称
		      fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.districtCode'), 
		      id:'Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id',
		      name:'districtCode', 
		      transferCenterCode:stockchecking.tranferCode,
		      columnWidth:0.4,
		      labelWidth:40,
		      margin:'5 10 5 0',
		      listeners:{
		    	  'change': function(cbk,newValue,oldValue) {
		    		  if(Ext.isEmpty(newValue)) {
	   					  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
	   					  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
		    			  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').disable();
						  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').disable();
		    		  } else {
		    			  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
	   					  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
		    			  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').enable();
						  Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').enable();
		    		  }
		    		  
		    		  var goodsAreaSearchGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
						goodsAreaSearchGrid.store.removeAll();
		    	  }
		      }
		},{
			xtype: 'numberfield',
			fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.startQty'),  //起始件数
			//fieldLabel:'起始件数',
			name: 'startQty',
			id:'Foss.stockchecking.newStockchecking.form.searchStock.startQty.id',
			value: '',
			allowDecimals: false,
			regex:/^[1-9]\d{0,3}$/,
			regexText:'件数输入有误.必须是不为0的数字,长度小于等于4位!',
			hideTrigger: true,
			hidden:true,
			columnWidth:.28,
			 listeners:{
				 'change':function() {
					 var goodsAreaSearchGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
						goodsAreaSearchGrid.store.removeAll();
		    	  } 
		    }
		},{
			xtype: 'numberfield',
			fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.endQty'),  //结束件数
			//fieldLabel:'结束件数',
			name: 'endQty',
			id:'Foss.stockchecking.newStockchecking.form.searchStock.endQty.id',
			value: '',
			allowDecimals: false,
			regex:/^[1-9]\d{0,3}$/,
			regexText:'件数输入有误.必须是不为0的数字,长度小于等于4位!',
			hideTrigger: true,
			hidden:true,
			columnWidth:.28,
			listeners:{
				 'change':function() {
					 var goodsAreaSearchGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
					 goodsAreaSearchGrid.store.removeAll();
		    	  } 
		    }
		},{
			xtype: 'container',
			columnWidth:0.4,
			id:'Foss.stockchecking.newStockchecking.form.searchStock.gap.id'/*,
			html: '&nbsp;'*/
		},{xtype:'button', 
			text: stockchecking.i18n('Foss.stockchecking.button.search'), 
			id:'Foss.stockchecking.newStockchecking.form.searchStock.searchButton.id', 
			disabled: !stockchecking.isPermission('stockchecking/queryGoodsStockButton'),
			hidden: !stockchecking.isPermission('stockchecking/queryGoodsStockButton'),
			cls: 'yellow_button',
			handler: function() {
			var goodsArea = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsArea.id').getValue();
			var goodsAreaUsage = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsAreaUsage.id').getValue();
			
			var taskStatus = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.taskStatus.id').getValue();
			//提货方式
			var receiveMethods = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').getChecked();
			var receiveMethod = "";
			Ext.Array.each(receiveMethods, function(item){
				receiveMethod = item.inputValue;
			     });
			//分区名称
			var districtCode = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').getValue();
			var districtName = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').getRawValue();
			//提货方式为派送时，分区名称不可为空
			if(receiveMethod == "DELIVER" && Ext.isEmpty(districtCode)) {
				var msg = "请选择分区";
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), msg);
				return;
			}
			//件数
			var startQty = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').getValue();
			var endQty = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').getValue();
			
			//货区类型
			var goodsAreaType = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').getValue();
			if(goodsAreaType=='全部'){
				goodsAreaType='ALL';
			}
			var startValid = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').isValid();
			var endValid = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').isValid();
			
			if((startQty == null && endQty != null) || (startQty != null && endQty == null)){
				var msg = '请输入起始件数和结束件数!';
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), msg);
				return;
			}
			if(!startValid || !endValid) {
				var msg = '件数输入有误.必须是不为0的数字,长度小于等于4位!';
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), msg);
				if(!startValid) {
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').setValue(null);
				}
				if(!endValid) {
					Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').setValue(null);
				}
				return;
			} else {
				if(startQty != null && endQty != null && startQty > endQty){
					var msg = '结束件数必须大于等于起始件数,请重新输入!';
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), msg);
					return;
				}
			}
			
			Ext.Ajax.request({
				url: stockchecking.realPath('queryGoodsStock.action'),
				params: {
					'stockcheckingVO.goodsStockDto.goodsArea':goodsArea,
					'stockcheckingVO.goodsStockDto.goodsAreaUsage':goodsAreaUsage,
					'stockcheckingVO.goodsStockDto.taskStatus':taskStatus,
					'stockcheckingVO.goodsStockDto.receiveMethod':receiveMethod,
					'stockcheckingVO.goodsStockDto.districtCode':districtCode,
					'stockcheckingVO.goodsStockDto.startQty':startQty,
					'stockcheckingVO.goodsStockDto.endQty':endQty,
					'stockcheckingVO.goodsStockDto.goodsAreaType':goodsAreaType
				},
				success: function(response){
					result = Ext.decode(response.responseText);
					
					var searchStockGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
					//加载结果集数据
					searchStockGrid.store.loadData(result.stockcheckingVO.goodsStockDtoList);
					
					if(result.stockcheckingVO.goodsStockDtoList.length == 0){
						Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.nosearchresult'));
					}
					//如果之前勾选的数据需在初始化时勾选上
					searchStockGrid.getStore().each(function(record){
						if(stockchecking.goodsAreaCodeMap.contains(record.data.goodsAreaCode)){
							searchStockGrid.getSelectionModel().select(record, true, true);
						}
					});
				},
				exception : function(response) {
					//清空数据
					var searchStockGrid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id');
					searchStockGrid.store.removeAll();
    				var result = Ext.decode(response.responseText);
    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
    			}
			});
		}}
	]
});

//查询库区 grid
Ext.define('Foss.stockchecking.newStockchecking.grid.searchStock',{
	extend:'Ext.grid.Panel',
	id:'Foss.stockchecking.newStockchecking.grid.searchStock.id',
	selModel: Ext.create('Ext.selection.CheckboxModel'),
	columnLines: true,
	height: 350,
	verticalScroller: {
        xtype: 'paginggridscroller',
        activePrefetch: false
    },
	columns:[
        {
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'),  //货区
			dataIndex: 'goodsAreaName',
			flex: 2
		},
		{
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNum'), //运单数
			dataIndex: 'waybillNum',
			flex: 1
		},
		{
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.stockGoodsQty'), //库存件数
			dataIndex: 'stockGoodsQty',
			flex: 1
		},
		{
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodWeightTotal'), //重量(公斤) 
			dataIndex: 'goodWeightTotal',
			flex: 1
		},
		{
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodVolumeTotal'), //体积(立方米), 
			dataIndex: 'goodVolumeTotal',
			flex: 1
		},
		{
			header: stockchecking.i18n('Foss.stockchecking.sttask.model.stockcheckingstatus'), //清仓状态 
			dataIndex: 'taskStatus',
			flex: 1
		}
	],
	listeners:{
		select: function(rowModel, record, index, eOpts) {
			var theGrid = this;
			var stTaskId = theGrid.getStore().getAt(index).data.id;
//			验证当前所选库区是否存在未结束的任务，如果存在，不允许勾选(不允许再次新建此库区的清仓任务)
			if(!stockchecking.isEditUI) {
				/*Ext.Ajax.request({
					url: stockchecking.realPath('queryTaskInProcess.action'),
					params: {
						'stockcheckingVO.goodsStockDto.deptNo': record.data.deptNo,
						'stockcheckingVO.goodsStockDto.goodsAreaCode': record.data.goodsAreaCode
					},
					success: function(response){
						result = Ext.decode(response.responseText);
						var taskNos = "";
						if(result.stockcheckingVO.stTaskDtoList.length > 0){
							Ext.Array.each(result.stockcheckingVO.stTaskDtoList, function(data, i){
								taskNos += data.taskNo + ",";
							});
							
							var msg = "";
							if(record.data.goodsAreaName == null || record.data.goodsAreaName.length == 0){
								msg = "本部门已存在未结束的清仓任务，";
							}else{
								msg = "该货区已存在未结束的清仓任务，";
							}
							
							msg = msg + taskNos + "<BR>请先处理未结束的任务";
							Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), msg);
							
							theGrid.getSelectionModel().deselect(record, true, true);
						}else{*/
							var goodsAreaCode = record.get('goodsAreaCode');
							stockchecking.goodsAreaCodeMap.add(goodsAreaCode, goodsAreaCode);
						/*}
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), result.message);
					}
				});*/
			} 
		},
		deselect: function(rowModel, record, index, eOpts) {
			var goodsAreaCode = record.get('goodsAreaCode');
			stockchecking.goodsAreaCodeMap.remove(goodsAreaCode);
		},
		load: function(store, records, successful, epots){
			if(store.getCount() == 0){
				 Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.nosearchresult'));
			}
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stockchecking.newStockchecking.store.searchStock');
		
		me.callParent([cfg]);	
	}
});

Ext.define('Foss.stockchecking.confirmStockchecking.subSuperfluousGoods.form',{
	extend: 'Ext.form.Panel',
	id:'Foss.stockchecking.confirmStockchecking.subSuperfluousGoods.form.id',
	layout: 'column',
	frame: true,
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:1
	},
	items: [
	    {xtype:'textfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'), name:'waybillNo', vtype: 'waybill', allowBlank:false, columnWidth:1}, //运单号
	    {
	      xtype:'textareafield',
	      //流水号
	      fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo'), 
	      rows:10, 
	      name:'serialNo', 
	      allowBlank:false, 
	      emptyText: stockchecking.i18n('Foss.stockchecking.sttask.form.serialNo.emptyText'), 
	      regex: /^((\d{4})*\,*(\d{4}))*$/,
		  regexText: '流水号输入非法',
		  //maxLength: 50,
	      columnWidth:1
	     }, 
	    {xtype: 'container', html: '&nbsp;', columnWidth:.3},
	    {xtype:'button', text: stockchecking.i18n('Foss.stockchecking.button.add'), cls: 'yellow_button', columnWidth:.2, handler:function(){
	    	var subSuperfluousGoodsForm = this.up('form').getForm();
	    	if(!this.up('form').getForm().isValid()){
	    		return
	    	}
	    	
//	    	获取运单号
	    	var waybillNo = subSuperfluousGoodsForm.findField('waybillNo').getValue();
//	    	获取序列号
	    	var serialNos = subSuperfluousGoodsForm.findField('serialNo').getValue().replace('，', ',');
//	    	验证运单号是否存在于系统中
	    	Ext.Ajax.request({
				url: ContextPath.TFR_STOCK + '/tfrcommon/validateWaybillNoExist.action',
				params: {
					'tfrCommonVO.waybillNo': waybillNo,
					'tfrCommonVO.serialNo': serialNos
				},
				success: function(response){
					result = Ext.decode(response.responseText);
					if(result.tfrCommonVO.resultSuccessful){
//				    	将序列号转为数组，并去除重复序列号
				    	var serialNosArray = serialNos.split(',');
				    	var serialNosMap = new Ext.util.HashMap(); //此map中将存入不重复的serialNo
				    	Ext.Array.each(serialNosArray, function(data, i){
				    		if(data.length > 0){
				    			serialNosMap.add(data, data);
				    		}
				    	});
				    	serialNosArray = new Array();
				    	//验证输入的多货记录，若输入的运单号+流水号已存在于库存快照中，提示，此流水号已存在
				    	var valid = true;
				    	var reduplicateSerialNo = "";
				    	serialNosMap.each(function(key, value){
				    		if(stockchecking.stTaskListMap.containsKey(waybillNo)){
				    			var tempGoodsArray = stockchecking.stTaskListMap.get(waybillNo).serialNoList;
				    			if(Ext.Array.contains(tempGoodsArray, key)){
				    				valid = false;
				    				reduplicateSerialNo = key;
				    				
				    				return false;
				    			}
				    		}
				    		serialNosArray.push(key);
				    	});
				    	
				    	if(!valid){
				    		Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), "您输入的流水号" + reduplicateSerialNo + "已存在，请核对多货的运单号及流水号，并重新输入");
				    		
				    		return;
				    	}
				    	
//				    	以下准备提交确认清仓的数据，分为正常确认、少货、多货3个List提交
//				    	创建多货表格的对象
				    	var newRowObject = new Object();
				    	var superfluousGoodsGrid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList.id');
//				    	1、需判断，如果此运单号已存在于多货grid中的，需merge对应的流水号，首先删除此行，再在第一行插入
				    	if(stockchecking.superfluousGoodsMap.containsKey(waybillNo)){
//				    		从已有的map中取出此运单对应的所有序列号，并merge当前输入的流水号
				    		var tempSerialMap = new Ext.util.HashMap();
				    		var existSerialArray = new Array();
				    		Ext.Array.each(stockchecking.superfluousGoodsMap.get(waybillNo).serialNoList, function(data, i){
				    			existSerialArray.push(data);
				    		});
				    		//merge serial no
				    		Ext.Array.each(existSerialArray, function(data, i){
				    			tempSerialMap.add(data, data);
					    	});
				    		Ext.Array.each(serialNosArray, function(data, i){
				    			tempSerialMap.add(data, data);
					    	});
				    		serialNosArray = new Array();
				    		tempSerialMap.each(function(key, value){
					    		serialNosArray.push(key);
					    	});
//				    		从map中删除此运单号对应的数据
				    		stockchecking.superfluousGoodsMap.removeAtKey(waybillNo);
//				    		从grid中删除此运单号对应行
				    		superfluousGoodsGrid.getStore().each(function(record){
								if(waybillNo == record.data.waybillNo){
									superfluousGoodsGrid.getSelectionModel().select(record, true, true);
									superfluousGoodsGrid.getStore().remove(record);
									return false;
								}
							});
//				    		创建此运单号对应的新的数据行
				    		newRowObject = {waybillNo: waybillNo,
								            wayBillNoDesc: waybillNo,
											serialNoNum: serialNosArray.length,
											serialNoList: serialNosArray};
							stockchecking.superfluousGoodsMap.add(waybillNo, newRowObject);
				    	}else{
				    		newRowObject = {waybillNo: waybillNo,
								            wayBillNoDesc: waybillNo,
											serialNoNum: serialNosArray.length,
											serialNoList: serialNosArray};
				    		stockchecking.superfluousGoodsMap.add(waybillNo, newRowObject);
				    	}
//				    	2、如果此运单号不存在于多货grid中的，直接在第一行新增
				    	superfluousGoodsGrid.store.insert(0, newRowObject);
				    	
				    	subSuperfluousGoodsForm.reset();
				    	Ext.getCmp('Foss.stockchecking.confirmStockchecking.panel.id').getSubSuperfluousGoodsWindow().hide();
					}else{
						Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), result.tfrCommonVO.resultMessage);
						
						return;
					}
				},
				exception : function(response) {
    				var result = Ext.decode(response.responseText);
    				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), result.message);
    			}
			});
	    }},
	    {xtype:'button', text: stockchecking.i18n('Foss.stockchecking.button.close'), cls: 'yellow_button', columnWidth:.2, handler:function(){
	    	this.up('form').getForm().reset();
	    	
	    	Ext.getCmp('Foss.stockchecking.confirmStockchecking.panel.id').getSubSuperfluousGoodsWindow().hide();
	    }},
	    {xtype: 'container', html: '&nbsp;', columnWidth:.3}
	],
	constructor: function(config){
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//扫描明细 grid
Ext.define('Foss.stockchecking.viewStockchecking.scanDetail.grid',{
	extend: 'Ext.grid.Panel',
	id: 'Foss.stockchecking.viewStockchecking.scanDetail.grid.id',
	frame: true,
	autoScroll: true,
	columns: [
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo'), dataIndex: 'serialNo',flex: 0.2}, //流水号
		{header: stockchecking.i18n('Foss.stockchecking.sttask.model.scanStatusDesc'), dataIndex: 'scanStatusDesc',flex: 0.25},  //扫描状态描述
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsStatusDesc'), dataIndex: 'goodsStatusDesc',flex: 0.25}, //货物状态描述
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.uploadTime'), dataIndex: 'uploadTime',flex: 0.3}  //操作时间
	],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stockchecking.viewStockchecking.scanDetail.store');
		
		me.callParent([cfg]);	
	}
});

//新增多货信息窗口
Ext.define('Foss.stockchecking.confirmStockchecking.window.subSuperfluousGoods', {
	extend:'Ext.window.Window',
	id:'Foss.stockchecking.confirmStockchecking.window.subSuperfluousGoods.id',
	title: stockchecking.i18n('Foss.stockchecking.window.subSuperfluousGoods.title'),  //添加货区多货
	modal:true,
	align:'center',
	width:600,
	height:400,
	closeAction: "hide",
	autoScroll:true,
	border:false,
	items:[
       Ext.getCmp('Foss.stockchecking.confirmStockchecking.subSuperfluousGoods.form.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.subSuperfluousGoods.form') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.subSuperfluousGoods.form.id')
    ]
});

//扫描明细信息窗口
Ext.define('Foss.stockchecking.viewStockchecking.window.scanDetail', {
	extend:'Ext.window.Window',
	id:'Foss.stockchecking.viewStockchecking.window.scanDetail.id',
	title:stockchecking.i18n('Foss.stockchecking.window.scanDetail.title'), //货物明细
	modal:true,
	align:'center',
	width:600,
	height:400,
	closeAction: "hide",
	autoScroll:true,
	border:false,
	items:[
	    Ext.getCmp('Foss.stockchecking.viewStockchecking.scanDetail.grid.id') == null ? Ext.create('Foss.stockchecking.viewStockchecking.scanDetail.grid') : Ext.getCmp('Foss.stockchecking.viewStockchecking.scanDetail.grid.id')
	]
});

//获取清仓任务库区信息
Ext.define('Foss.stockchecking.confirmStockchecking.form.stTask',{
	extend: 'Ext.form.Panel',
	id:'Foss.stockchecking.confirmStockchecking.form.stTask.id',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaults: {
		margin:'5 5 5 5',
		anchor: '90%'
	},
	items:[
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.taskNofull'), name:'taskNo', value:'', columnWidth:.5}, //清仓任务编号
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), name:'goodsareaname',value:'', columnWidth:.5}, //货区
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.createtime'), name:'createtime',value:'',columnWidth:1}, //任务建立时间
	    {xtype: 'container', html: '注：1、货物清单列表中打勾的表示少货；2、请使用Ctrl+F搜索运单号',columnWidth:.9},
	    {xtype: 'textfield', fieldLabel: 'ID', name: 'id', hidden:true, hideLabel:true,columnWidth:.1}
	]
});

//新增编辑页面 查询清仓人
Ext.define('Foss.stockchecking.newStockchecking.form.searchOperator',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	id:'Foss.stockchecking.newStockchecking.form.searchOperator.id',
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 5 5 5',
		anchor: '90%',
		labelWidth:90
	},
	items:[{
			xtype: 'commonemployeeselector',
			fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'), //清仓员
			name: 'empCode',
			parentOrgCode : FossUserContext.getCurrentDept().code,
			allowBlank:false
		},
		{xtype:'button',
		 text: stockchecking.i18n('Foss.stockchecking.button.addoperator'), //添加清仓人
		 cls: 'yellow_button',handler:function(){
			//获得grid
			var grid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.id');
			//设置验证变量
			var isValid = true;
			//获取所需验证的form
			var theForm = this.up('form').getForm();
			//获取清仓人编号
			var selectedOperator = theForm.findField('empCode');
			//验证非空
			if(!theForm.isValid()){
				return;
			}
			
			//判断是不是快递员
			var flag = false;//用于判断是不是快递
			Ext.Ajax.request({
				url: stockchecking.realPath('isExpressClerk.action'),
				async:false,
				success:function(response){
					var result = Ext.decode(response.responseText);
					flag=result.stockcheckingVO.queryIfIsExpressClerk;
				},
				exception:function(response){
					Ext.Msg.alert('提示','获取当前登录的是不是快递员失败！');
				}
			});
			if(flag==true){
			  var b=FossUserContext.getCurrentUser();
			   var empCode=b.employee.empCode;
				if(selectedOperator.getValue()!=empCode){
					Ext.Msg.alert('提示','当前登录为快递员,只能选择自己为清仓人');
					return;
				}
			}
			//提示已存在的清仓人禁止再次插入
			var theStore = grid.getStore();
			
			theStore.each(function(record){
				if(selectedOperator.getValue() == record.get('empCode')){
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.update.validator.repeat.operator')); //此清仓人员已经存在，无需再次添加!
					isValid = false;
					return false;
				}
			});
			
			if(!isValid){
				return;
			}
			
			//获取清仓人姓名，拆分字符串
			var text = selectedOperator.getRawValue();
			
			//像grid的store中插入新的一条记录
			var newOperator = Ext.create('Foss.stockchecking.model.searchOperator', {
				'empName':text,
				'empCode':selectedOperator.getValue()
			});
			grid.store.insert(0, newOperator);
			//更新grid中的人数统计信息
			var operatorNum = theStore.count();
			Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.operatorNum.id').setValue(operatorNum);
	    }}
	]
});

//查询清仓人 grid
Ext.define('Foss.stockchecking.newStockchecking.grid.searchOperator',{
	extend:'Ext.grid.Panel',
	id:'Foss.stockchecking.newStockchecking.grid.searchOperator.id',
	width:300,
	height:200,
	autoScroll:true,
	overflow:'auto',
	
	columns:[
        {header: stockchecking.i18n('Foss.stockchecking.operator.model.name'), dataIndex: 'empName'}, //姓名
		{header: stockchecking.i18n('Foss.stockchecking.operator.model.code'), dataIndex: 'empCode'}, //工号
		{
			xtype:'actioncolumn',
			flex: 1,
			text: stockchecking.i18n('Foss.stockchecking.button.operator'),
			align: 'center',
			items: [{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.remove'), //移除
                iconCls: 'deppon_icons_remove',
                handler: function(grid, rowIndex, colIndex) {
                	//点击删除按钮删除本行数据
                	var theStore = grid.getStore();
                	var rec = theStore.getAt(rowIndex);
                	theStore.removeAt(rowIndex);
					//更新已选清仓人人数
                	var operatorNum = theStore.count();
					Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.operatorNum.id').setValue(operatorNum);
                }
		    }]
		}
	],
	dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        defaults: {
        	margin:'0 0 0 0',
    		xtype: 'textfield',
    		readOnly:true,
    		anchor: '100%',
    		labelWidth:100
    	},
        items: [
	        {name: 'operatorNum',
             id: 'Foss.stockchecking.newStockchecking.grid.searchOperator.operatorNum.id',
             labelWidth:70,
             fieldLabel: stockchecking.i18n('Foss.stockchecking.operator.model.operatorNum'), //清仓人数'
             flex: 1,
             value:0}
	    ]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stockchecking.store.searchOperator');
		
		me.callParent([cfg]);	
	}
});

//确认页面 查询清仓人
Ext.define('Foss.stockchecking.confirmStockchecking.form.searchOperator',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	id:'Foss.stockchecking.confirmStockchecking.form.searchOperator.id',
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 5 5 0'
	},
	items:[{
			xtype: 'commonemployeeselector',
			fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'), //清仓员
			name: 'empCode',
			parentOrgCode : FossUserContext.getCurrentDept().code,
			allowBlank:false
		},
		{xtype:'button', text: stockchecking.i18n('Foss.stockchecking.button.addoperator'), cls: 'yellow_button',handler:function(){
			//获得grid
			var grid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.id');
			//设置验证变量
			var isValid = true;
			//获取所需验证的form
			var theForm = this.up('form').getForm();
			//获取清仓人编号
			var selectedOperator = theForm.findField('empCode');
			//验证非空
			if(!theForm.isValid()){
				return;
			}
			//提示已存在的清仓人禁止再次插入
			var theStore = grid.getStore();
			
			theStore.each(function(record){
				if(selectedOperator.getValue() == record.get('empCode')){
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.update.validator.operator'));
					isValid = false;
					
					return false;
				}
			});
			
			if(!isValid){
				return;
			}
			//判断是不是快递员
			var flag = false;//用于判断是不是快递
			Ext.Ajax.request({
				url: stockchecking.realPath('isExpressClerk.action'),
				async:false,
				success:function(response){
					var result = Ext.decode(response.responseText);
					flag=result.stockcheckingVO.queryIfIsExpressClerk;
				},
				exception:function(response){
					Ext.Msg.alert('提示','获取当前登录的是不是快递员失败！');
				}
			});
			if(flag==true){
			  var b=FossUserContext.getCurrentUser();
			   var empCode=b.employee.empCode;
				if(selectedOperator.getValue()!=empCode){
					Ext.Msg.alert('提示','当前登录为快递员,只能选择自己为清仓人');
					return;
				}
			}
			//获取清仓人姓名，拆分字符串
			var text = selectedOperator.getRawValue();
			
			//像grid的store中插入新的一条记录
			var newOperator = Ext.create('Foss.stockchecking.model.searchOperator', {
				'empName':text,
				'empCode':selectedOperator.getValue()
			});
			grid.store.insert(0, newOperator);
			//更新grid中的人数统计信息
			var operatorNum = theStore.count();
			Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.operatorNum.id').setValue(operatorNum);
	    }}
	]
});

//查询清仓人 grid
Ext.define('Foss.stockchecking.confirmStockchecking.grid.searchOperator',{
	extend:'Ext.grid.Panel',
	id:'Foss.stockchecking.confirmStockchecking.grid.searchOperator.id',
	width:300,
	height:150,
	autoScroll:true,
	columns:[
        {header: stockchecking.i18n('Foss.stockchecking.operator.model.name'), dataIndex: 'empName'},
		{header: stockchecking.i18n('Foss.stockchecking.operator.model.code'), dataIndex: 'empCode'},
		{
			xtype:'actioncolumn',
			flex: 1,
			text: stockchecking.i18n('Foss.stockchecking.button.operator'),
			align: 'center',
			items: [{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.remove'),
                iconCls: 'deppon_icons_remove',
                handler: function(grid, rowIndex, colIndex) {
                	//点击删除按钮删除本行数据
                	var theStore = grid.getStore();
                	var rec = theStore.getAt(rowIndex);
                	theStore.removeAt(rowIndex);
					//更新已选清仓人人数
                	var operatorNum = theStore.count();
					Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.operatorNum.id').setValue(operatorNum);
                }
		    }]
		}
	],
	dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        defaults: {
        	margin:'0 0 0 0',
    		xtype: 'textfield',
    		readOnly:true,
    		anchor: '100%',
    		labelWidth:100
    	},
        items: [
	        {name: 'operatorNum',
             id: 'Foss.stockchecking.confirmStockchecking.grid.searchOperator.operatorNum.id',
             labelWidth:70,
             fieldLabel: stockchecking.i18n('Foss.stockchecking.operator.model.operatorNum'),
             flex: 1,
             value:0}
	    ]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stockchecking.store.searchOperator');
		
		me.callParent([cfg]);	
	}
});

//多货清单 序列号 二级 grid
Ext.define('Foss.stockchecking.confirmStockchecking.grid.subSuperfluousGoodsList', {
	extend: 'Ext.form.Panel',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	fieldDefaults: {
		labelWidth: 110
	},
	margin: '0 0 0 20',
	selectAll: function(boxGroup){
		Ext.each(boxGroup.items,
			function(c) {
				c.setValue(true);
				c.fieldCls = '';
				
				return true;
			}
		);
	},
	dsselectAll: function(boxGroup){
		Ext.each(boxGroup.items,
			function(c) {
				c.setValue(false);
				c.setfieldCls = 'x-form-field';
				
				return true;
			}
		);
	},
	bindData: function(firstLevelRecord, grid, rowBody){
		//动态生成CheckBoxGroup的item
		var checkGroup = rowBody.getCheckBoxGroup();
		var tmpForm = rowBody;
		var waybillNo = firstLevelRecord.data.waybillNo;
		var serialNoNum = firstLevelRecord.data.serialNoNum;	//运单号对应的流水号总数
		var superGrid = this.superGrid;
// 		通过运单号获取对应的流水号
		var serialNoList = stockchecking.superfluousGoodsMap.get(waybillNo).serialNoList;
		if(serialNoList != null){
			//只有第一次才渲染
			if(tmpForm.checkBoxGroup == null){
				var checkboxitems = "[";
				//构建选项
				for(var i = 0 ; i < serialNoList.length; i++){
					if(checkboxitems != ""){
	        			checkboxitems += ",";
					}
					var checkboxSingleItem = "{boxLabel:' " + stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo') + " " + serialNoList[i] + 
					                         "', margin:'5 0 5 0', name:'" + serialNoList[i] + 
					                         "', waybillNo:'" + waybillNo + "'}";
					checkboxitems = checkboxitems + checkboxSingleItem;
				}
				checkboxitems += "]";
				//重新实例化所有的checkbox
				var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup', {
					fieldLabel: '',
					labelSeparator: '',		
					margin:'0 0 0 20',
					defaults: { 
					    columnWidth:.20,
					    margin:'5 0 5 0'
					},
					layout:'column',
					items: eval(checkboxitems),
					listeners:{
						change: function(obj, newValue, oldValue){
							var oldCheckedCount = Ext.Object.getKeys(oldValue).length;
							var newCheckedCount = Ext.Object.getKeys(newValue).length;
//							如果二级checkbox中勾选中第一个时，需勾选一级grid对应的checkbox
							if(oldCheckedCount == 0 && newCheckedCount == 1){
								superGrid.getSelectionModel().select(firstLevelRecord, true, true);
//							如果二级checkbox中去掉勾选最后一个时，需去掉勾选一级grid对应的checkbox
							}else if(oldCheckedCount == 1 && newCheckedCount == 0){
								superGrid.getSelectionModel().deselect(firstLevelRecord, true, true);
							}else{
								;//不处理
							}
						}
					}
				});								
				//添加重新渲染
				tmpForm.add(checkBoxGroup);
				tmpForm.checkBoxGroup = checkBoxGroup;
				tmpForm.doLayout();
			}
		}
//		控制选择事件
		var isSelected = grid.getSelectionModel().isSelected(firstLevelRecord);
//		若二级的checkbox存在至少选中一个并且不为全选中的情况，不改变二级的勾选情况
		var checkBoxGroupItems = rowBody.getCheckBoxGroup().items;
		var count = 0;
		
		Ext.Array.each(checkBoxGroupItems.items, function(data, i){
			if(data.checked){
				count++;
			}
		});
		
		if(count > 0 && serialNoNum > count){
			return;
		}
		
//		否则按照上级选定的方式，选中一行则全选二级的所有checkbox,反选一行，则反选所有二级的checkbox
		if(isSelected){
			rowBody.selectAll(rowBody.getCheckBoxGroup().items);
		}else{
			rowBody.dsselectAll(rowBody.getCheckBoxGroup().items);
		}
	},
	unbindData: function(record, grid, rowBody){
		rowBody.bindData(record, grid, rowBody);
	},
	checkBoxGroup: null,
	getCheckBoxGroup: function(){
		return this.checkBoxGroup;
	},
	constructor: function(config){
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//多货清单一级 grid
Ext.define('Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList',{
	extend:'Ext.grid.Panel',
	id:'Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList.id',
	selModel: Ext.create('Ext.selection.CheckboxModel',{checkOnly: true, showHeaderCheckbox: false}),
	columnLines: true,
	height: 400,
	title: stockchecking.i18n('Foss.stockchecking.panel.superfluousGoodsList.title'), //货区多货清单
	frame: true,
	columns:[
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'), dataIndex: 'wayBillNoDesc', flex: 1/5},
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNoNum'), dataIndex: 'serialNoNum', flex: 4/5}
	],
	listeners:{
		select: function(rowModel, record, index, eOpts) {
			var grid = this;
			var view = grid.getView();
			var rowIdx = view.getNode(record);
			
			view.fireEvent('itemdblclick', view, record, rowIdx);
		},
		deselect: function(rowModel, record, index, eOpts) {
			var grid = this;
			var view = grid.getView();
			var rowIdx = view.getNode(record);
			
			view.fireEvent('itemdblclick', view, record, rowIdx);
		}
	},
	plugins: [{
        ptype: 'rowexpander',
        rowBodyElement: 'Foss.stockchecking.confirmStockchecking.grid.subSuperfluousGoodsList'
    }],
    collapsible: true,
    animCollapse: true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stockchecking.store.stTaskList');
		me.tbar = [{
			xtype:'button',
			text: stockchecking.i18n('Foss.stockchecking.button.addstocksuperfluousGoods'),  //添加库区多货
			handler: function(){
				Ext.getCmp('Foss.stockchecking.confirmStockchecking.panel.id').getSubSuperfluousGoodsWindow().show();
				Ext.getCmp('Foss.stockchecking.confirmStockchecking.subSuperfluousGoods.form.id').getForm().reset();
			}
		},{
			xtype:'button',
			text: stockchecking.i18n('Foss.stockchecking.button.delete'),
			handler: function(){
//				从store中删除勾选的所有序列号。
//				按照stockchecking.superfluousGoodsMap重置 "添加多货"的grid.store
				var superfluousGoodsGrid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList.id');
//				1、循环多货grid的store，逐行与stockchecking.superfluousGoodsMap做对比
				superfluousGoodsGrid.getStore().each(function(record){
					var waybillNo = record.data.waybillNo;
					var serialNoNum = record.data.serialNoNum;
					var waybillNoRowMapRecord = stockchecking.superfluousGoodsMap.get(waybillNo);
					var checkedSerialNoArray = new Array();		//已勾选的待删除的序列号
					
					var extendedNode = superfluousGoodsGrid.getView().getNode(record);								
					var	rowBodyElementId = record.internalId + '-rowbody-element';
					//获取到表单
					var form = superfluousGoodsGrid.plugins[0].elementIdMap.get(rowBodyElementId);
					//获取到已经选中的流水号
					if(form != null){
						var ch = form.getCheckBoxGroup();							
			            for (var j = 0; j < ch.items.length; j++){
			            	if (ch.items.items[j].checked){
			            		checkedSerialNoArray.push(ch.items.items[j].name);
			                }   
			            } 
					}							
//					2、如果某一运单下包含的序列号都已勾选，则将此运单号从map中去除
					if(serialNoNum == checkedSerialNoArray.length){
						stockchecking.superfluousGoodsMap.removeAtKey(waybillNo);
					}else{
//					3、如果非全选，则更新此map.key(waybillNo)对应的serialNoList列表，将序列号从array中去除
						Ext.Array.each(checkedSerialNoArray, function(data, i){
							Ext.Array.remove(waybillNoRowMapRecord.serialNoList, data);
							waybillNoRowMapRecord.serialNoNum--;//此运单号对应件数减1
				    	});
					}
				});//superfluousGoodsGrid.getStore().each(function(record){  --end
//				4、清空当前的store，并按照最新的map重新load数据
				superfluousGoodsGrid.getStore().each(function(record){
					superfluousGoodsGrid.getSelectionModel().select(record, true, true);
				});
				
				superfluousGoodsGrid.getStore().removeAll(false);
				
				stockchecking.superfluousGoodsMap.each(function(key, value){
					superfluousGoodsGrid.store.insert(0, value);
				});
				
			}//删除按钮 handler: function(){
		}];
		me.callParent([cfg]);	
	}
});

//库存快照表格 序列号 二级 grid
Ext.define('Foss.stockchecking.confirmStockchecking.grid.subStTaskList', {
	extend: 'Ext.form.Panel',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	fieldDefaults: {
		labelWidth: 110
	},
	margin: '0 0 0 20',
	selectAll: function(boxGroup){
		Ext.each(boxGroup.items,
			function(c) {
				c.setValue(true);
				c.fieldCls = '';
				
				return true;
			}
		);
	},
	dsselectAll: function(boxGroup){
		Ext.each(boxGroup.items,
			function(c) {
				c.setValue(false);
				c.setfieldCls = 'x-form-field';
				
				return true;
			}
		);
	},
	bindData: function(firstLevelRecord, grid, rowBody){
		//动态生成CheckBoxGroup的item
		var checkGroup = rowBody.getCheckBoxGroup();
		var tmpForm = rowBody;
		var waybillNo = firstLevelRecord.data.waybillNo;
		var serialNoNum = firstLevelRecord.data.serialNoNum;	//运单号对应的流水号总数
		var superGrid = this.superGrid;
// 		通过运单号获取对应的流水号
		var serialNoList = stockchecking.stTaskListMap.get(waybillNo).serialNoList;
		if(serialNoList != null){
			//只有第一次才渲染
			if(tmpForm.checkBoxGroup == null){
				var checkboxitems = "[";
				//构建选项
				for(var i = 0 ; i < serialNoList.length; i++){
					if(checkboxitems != ""){
	        			checkboxitems += ",";
					}
					var checkboxSingleItem = "{boxLabel:'" + stockchecking.i18n('Foss.stockchecking.sttask.model.serialNo') + " " + serialNoList[i] + 
					                         "', margin:'5 0 5 0', name:'" + serialNoList[i] + 
					                         "', waybillNo:'" + waybillNo + "'}";
					checkboxitems = checkboxitems + checkboxSingleItem;
				}
				checkboxitems += "]";
				//重新实例化所有的checkbox
				var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup', {
					fieldLabel: '',
					labelSeparator: '',		
					margin:'0 0 0 20',
					defaults: { 
					    columnWidth:.20,
					    margin:'5 0 5 0'
					},
					layout:'column',
					items: eval(checkboxitems),
					listeners:{
						change: function(obj, newValue, oldValue){
							var oldCheckedCount = Ext.Object.getKeys(oldValue).length;
							var newCheckedCount = Ext.Object.getKeys(newValue).length;
//							如果二级checkbox中勾选中第一个时，需勾选一级grid对应的checkbox
							if(oldCheckedCount == 0 && newCheckedCount == 1){
								superGrid.getSelectionModel().select(firstLevelRecord, true, true);
//							如果二级checkbox中去掉勾选最后一个时，需去掉勾选一级grid对应的checkbox
							}else if(oldCheckedCount == 1 && newCheckedCount == 0){
								superGrid.getSelectionModel().deselect(firstLevelRecord, true, true);
							}else{
								;//不处理
							}
						}
					}
				});								
				//添加重新渲染
				tmpForm.add(checkBoxGroup);
				tmpForm.checkBoxGroup = checkBoxGroup;
				tmpForm.doLayout();
			}
		}
//		控制选择事件
		var isSelected = grid.getSelectionModel().isSelected(firstLevelRecord);
//		若二级的checkbox存在至少选中一个并且不为全选中的情况，不改变二级的勾选情况
		var checkBoxGroupItems = rowBody.getCheckBoxGroup().items;
		var count = 0;
		
		Ext.Array.each(checkBoxGroupItems.items, function(data, i){
			if(data.checked){
				count++;
			}
		});
		
		if(count > 0 && serialNoNum > count){
			return;
		}
		
//		否则按照上级选定的方式，选中一行则全选二级的所有checkbox,反选一行，则反选所有二级的checkbox
		if(isSelected){
			rowBody.selectAll(rowBody.getCheckBoxGroup().items);
		}else{
			rowBody.dsselectAll(rowBody.getCheckBoxGroup().items);
		}
	},
	unbindData: function(record, grid, rowBody){
		rowBody.bindData(record, grid, rowBody);
	},
	checkBoxGroup: null,
	getCheckBoxGroup: function(){
		return this.checkBoxGroup;
	},
	constructor: function(config){
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//库存快照表格一级 grid
Ext.define('Foss.stockchecking.confirmStockchecking.grid.stTaskList',{
	extend:'Ext.grid.Panel',
	id:'Foss.stockchecking.confirmStockchecking.grid.stTaskList.id',
	selModel: Ext.create('Ext.selection.CheckboxModel',{checkOnly: true}),
	columnLines: true,
    collapsible: true,
    animCollapse: true,
	height: 400,
	title: stockchecking.i18n('Foss.stockchecking.panel.GoodsList.title'),
	frame: true,
	columns:[
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'), dataIndex: 'wayBillNoDesc', flex: 1},
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.serialNoNum'), dataIndex: 'serialNoNum', flex: 4}
	],
	listeners:{
		select: function(rowModel, record, index, eOpts) {
			var grid = this;
			var view = grid.getView();
			var rowIdx = view.getNode(record);
			
			view.fireEvent('itemdblclick', view, record, rowIdx);
		},
		deselect: function(rowModel, record, index, eOpts) {
			var grid = this;
			var view = grid.getView();
			var rowIdx = view.getNode(record);
			
			view.fireEvent('itemdblclick', view, record, rowIdx);
		}
	},
	plugins: [{
		pluginId: 'Foss.stockchecking.confirmStockchecking.grid.stTaskList.pluginId',
        ptype: 'rowexpander',
        rowBodyElement: 'Foss.stockchecking.confirmStockchecking.grid.subStTaskList'
    }],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stockchecking.store.stTaskList');
		
		me.callParent([cfg]);	
	}
});

Ext.define('Foss.stockchecking.newStockchecking.form.submit',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	id:'Foss.stockchecking.newStockchecking.form.submit.id',
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	items:[
	    {xtype: 'textfield', fieldLabel: 'ID', name: 'id', hidden:true, hideLabel:true, id:'Foss.stockchecking.newStockchecking.form.submit.stTask.id'},
	    {xtype: 'container', columnWidth: .85, html: '&nbsp;'},
		{xtype: 'button', 
	      id: 'Foss.stockchecking.newStockchecking.form.submit.newButton.id', 
	      text: stockchecking.i18n('Foss.stockchecking.button.addsttask'), 
	      disabled: !stockchecking.isPermission('stockchecking/createStTaskButton'),
	      hidden: !stockchecking.isPermission('stockchecking/createStTaskButton'),
	      cls: 'yellow_button', 
	      handler:function(){
	    	  //判断清仓人为快递员且车辆绑定的营业部为驻地营业部不允许建立清仓任务
	    	if(stockchecking.isBasDept=='Y'){
	    		Ext.Msg.alert('提示','驻地营业部快递员不能创建清仓任务！');
	    		return;
	    	}  
	    	  
			if(stockchecking.goodsAreaCodeMap.getCount() == 0){
				//新建清仓任务前，必须至少选取一个库区
				Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.sttask.update.validator.goodsarea'), 'error', 3000);
				return;
			}
			
//			判断是否为外场，若登陆人切换为外场，则所选库区编号不允许为空
			var currentDept = FossUserContext.getCurrentDept();
			//***************需要找到快递员车辆绑定的营业部
			//判断是不是快递员
			var isTransferCenter = "";//用于判断是不是快递
			Ext.Ajax.request({
				url: stockchecking.realPath('isExpressClerk.action'),
				async:false,
				success:function(response){
					var result = Ext.decode(response.responseText);
					isTransferCenter=result.stockcheckingVO.isTransferCenter;
				},
				exception:function(response){
					Ext.Msg.alert('提示','获取当前登录的快递员的车辆绑定的营业部失败！');
				}
			});
//	    	取得选取的库区
			var existNullGoodsAreaCode = false;
			var selectedGoodsAreasArray = new Array();
			stockchecking.goodsAreaCodeMap.each(function(key, value){
				selectedGoodsAreasArray.push(key);
				
				if(isTransferCenter == 'Y'){
					if(null == key || '' == key){
						existNullGoodsAreaCode = true;
						return false;
					}
				}
			});
			
			if(isTransferCenter == 'Y'){
				if(existNullGoodsAreaCode){
					Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.update.validator.goodsareacode'), this); //所选库区存在库区编号为空的情况，请检查数据
					
					return;
				}
			}
			
			//起始、终止件数
			var startQty = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.startQty.id').getValue();
			var endQty = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.endQty.id').getValue();
			//提交时校验件数范围
			if(startQty != null && endQty != null && startQty > endQty) {
				var msg = '结束件数必须大于等于起始件数,请重新输入!';
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), msg);
				return;
			}
			var goodsArea = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsArea.id').getValue();

			var form = this.up('form');
			form.getEl().mask(stockchecking.i18n('Foss.stockchecking.saving'));
			//生成清仓任务时再次校验是否存在交叉的任务
			/*Ext.Ajax.request({
		        url: stockchecking.realPath('checkStockChecking.action'),
		        params: {
		          'stockcheckingVO.goodsStockDto.goodsArea':goodsArea,
//		          'stockcheckingVO.goodsStockDto.goodsAreaUsage':'ALL',
//		          'stockcheckingVO.goodsStockDto.taskStatus':'DOING',
		          'stockcheckingVO.goodsStockDto.receiveMethod':receiveMethod,
				  'stockcheckingVO.goodsStockDto.districtCode':districtCode,
		          'stockcheckingVO.goodsStockDto.startQty':startQty,
		          'stockcheckingVO.goodsStockDto.endQty':endQty
		        },
		        success: function(response){*/
					//form.getEl().mask(stockchecking.i18n('Foss.stockchecking.saving'));	
					
					//取得清仓人列表 
					var operatorArray = new Array();
					//获得查询清仓人grid
					var grid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.id');
					var theStore = grid.getStore();
					
					theStore.each(function(record, i){
						operatorArray.push(record.data);
					});
					
					//提货方式
					var receiveMethods = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.receiveMethod.id').getChecked();
					var receiveMethod = "";
					Ext.Array.each(receiveMethods, function(item){
						receiveMethod = item.inputValue;
					     });
					//货区类型
					var goodsAreaType = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsType.id').getValue();
					if(goodsAreaType=='全部'){
						goodsAreaType='ALL';
					}
					//分区名称
					var districtCode = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').getValue();
					var districtName = Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.districtCode.id').getRawValue();
					var jsonParam = {stockcheckingVO:{
						selectedGoodsAreasList:selectedGoodsAreasArray,
						selectedOperatorList:operatorArray,
						receiveMethod:receiveMethod,
						districtCode:districtCode,
						districtName:districtName,
						startQty:startQty,
						endQty:endQty,
						stType:goodsAreaType
					}
					};
					
					//按照库区列表，创建1~N条清仓任务
					Ext.Ajax.request({
						url: stockchecking.realPath('createStTask.action'),
						jsonData: jsonParam,
						success: function(response){
							form.getEl().unmask();
							result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.addsttask'));
							Ext.getCmp('T_stockchecking-sttaskindex_content').getNewStockcheckingWindow().hide();
							stockchecking.pagingBar.moveFirst();
						},
						exception : function(response) {
							form.getEl().unmask();
							var result = Ext.decode(response.responseText);
							Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), result.message);
							
						}
					});
		        /*},
		        exception : function(response) {
		        	form.getEl().unmask();
		          //清空数据
		            var result = Ext.decode(response.responseText);
		            top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		          }
		      });*/
	    }},
	    {xtype:'button', 
	    	id:'Foss.stockchecking.newStockchecking.form.submit.updateButton.id', 
	    	text: stockchecking.i18n('Foss.stockchecking.button.updateoperator'), 
	    	disabled: !stockchecking.isPermission('stockchecking/updateStTaskButton'),
	    	hidden: !stockchecking.isPermission('stockchecking/updateStTaskButton'),
	    	cls: 'yellow_button', 
	    	handler:function(){
	    	
//	    	获取清仓任务ID
	    	var stTaskId = Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.stTask.id').getValue();
	    	//取得清仓人列表 
			var operatorArray = new Array();
			//获得查询清仓人grid
			var grid = Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.id');
			var theStore = grid.getStore();
			
			if(theStore.getCount() == 0){
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.update.validator.operator'), this);
				
				return;
			}
			
			var form = this.up('form');
			form.getEl().mask(stockchecking.i18n('Foss.stockchecking.saving'));	
			
			theStore.each(function(record, i){
				operatorArray.push(record.data);
			});
			
			var jsonParam = {stockcheckingVO:{
								stTaskDto:{'id':stTaskId},
								selectedOperatorList:operatorArray
								}
							};
			
			//按清仓任务ID更新清仓人信息
			Ext.Ajax.request({
				url: stockchecking.realPath('updateStTask.action'),
				jsonData: jsonParam,
				success: function(response){
					form.getEl().unmask();
					result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.updatesttask'));
					Ext.getCmp('T_stockchecking-sttaskindex_content').getNewStockcheckingWindow().hide();
					stockchecking.pagingBar.moveFirst();
				},
				exception : function(response) {
					form.getEl().unmask();
    				var result = Ext.decode(response.responseText);
    				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), result.message);
    			}
			});
	    }},
	    {xtype:'button', 
	    	id:'Foss.stockchecking.newStockchecking.form.submit.cancelButton.id', 
	     text: stockchecking.i18n('Foss.stockchecking.button.canceltask'), 
	     disabled: !stockchecking.isPermission('stockchecking/cancelStTaskButton'),
	     hidden: !stockchecking.isPermission('stockchecking/cancelStTaskButton'),
	     cls: 'yellow_button', 
	     handler:function(){
	    	var confirm = Ext.Msg.confirm(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.cancelsttask'), function(button, text){
			       if(button == 'yes'){
//				 		获取清仓任务ID
				    	var stTaskId = Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.stTask.id').getValue();
						
						var jsonParam = {stockcheckingVO:{
											stTaskDto:{'id':stTaskId}
											}
										};
						
						//按清仓任务ID更新清仓人信息
						Ext.Ajax.request({
							url: stockchecking.realPath('cancelStTask.action'),
							jsonData: jsonParam,
							success: function(response){
								result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.cancelsttasksuccessful'));
								Ext.getCmp('T_stockchecking-sttaskindex_content').getNewStockcheckingWindow().hide();
								stockchecking.pagingBar.moveFirst();
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.operate.alert.title'), result.message);
			    			}
						});
					}
				});	
	    }},
	    {xtype:'button', text: stockchecking.i18n('Foss.stockchecking.button.close'), cls:'yellow_button', handler:function(){
	    	Ext.getCmp('T_stockchecking-sttaskindex_content').getNewStockcheckingWindow().hide();
	    }}
	]
});

Ext.define('Foss.stockchecking.confirmStockchecking.form.submit',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	id:'Foss.stockchecking.confirmStockchecking.form.submit.id',
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	items:[
       {xtype:'container', columnWidth:.85, html: '&nbsp;'},
       {xtype:'button', 
    	   id:'Foss.stockchecking.confirmStockchecking.form.submit.confirmButton.id',
    	   text: stockchecking.i18n('Foss.stockchecking.button.confirmtask'), 
    	   disabled: !stockchecking.isPermission('stockchecking/confirmStTaskButton'),
    	   hidden: !stockchecking.isPermission('stockchecking/confirmStTaskButton'),
    	   cls: 'yellow_button',
    	   handler:function(){
    	   var form = this.up('form');
//	 		取得清仓人列表 
   	    	var operatorArray = new Array();
//   	         获得查询清仓人grid
   	    	var grid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.id');
   	    	var theStore = grid.getStore();
   	   
   	    	if(theStore.getCount() == 0){
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.update.validator.operator'), this);
				
				return;
			}
            var confirm = Ext.Msg.confirm(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.tip.confirmsubmittask'), function(button, text){
		        if(button == 'yes'){
		        	
		    	    theStore.each(function(record, i){
		    		    operatorArray.push(record.data);
		    	    });
		    	    
//		    	         获取已确认的清仓快照数据，同时筛选出未勾选的货件
		    	    var stTaskGrid = Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.stTaskList.id');
		    	    //少货件数
		    	    var lackNum = 0;
		    	    Ext.Array.each(stTaskGrid.getSelectionModel().getSelection(), function(record){
		    	    	var checkedSerialNoArray = new Array();
//		    	    	若勾选的流水号，需加入array中保存
		    	    	var extendedNode = stTaskGrid.getView().getNode(record);						
						var	rowBodyElementId = record.internalId + '-rowbody-element';
						//获取到表单
						var form = stTaskGrid.plugins[0].elementIdMap.get(rowBodyElementId);
						//获取到未选中的流水号
						if(form != null){
							var ch = form.getCheckBoxGroup();							
				            for (var j = 0; j < ch.items.length; j++){
				            	if (ch.items.items[j].checked){
				            		checkedSerialNoArray.push(ch.items.items[j].name);
				            		lackNum++;
				                }
				            }
						}
						record.data.serialNoList = checkedSerialNoArray;
//						将勾选的货件存入Map
						stockchecking.stTaskResultListMap.add(record.data.waybillNo, record.data);
						
//						更新库存快照map，将打勾确认的货件从此map中去除，留下只为未勾选的数据
						
//						对应于此运单，比较已确认的流水号，将已打勾的从中除去
						var oldSerialNoArray = new Array();
						if(stockchecking.stTaskListMap.length > 0) {
							oldSerialNoArray = stockchecking.stTaskListMap.get(record.data.waybillNo).serialNoList;	//获得库存快照的流水号
						}
						var newSerialNoArray = Ext.Array.difference(oldSerialNoArray, checkedSerialNoArray); //筛选出未勾选的流水号
//						若此运单号下的所有流水号都已确认的，则将此运单号从快照Map中去除
						if(newSerialNoArray.length == 0){
							stockchecking.stTaskListMap.removeAtKey(record.data.waybillNo);
						}else{
//					          否则更新此运单下对应的流水号，此处保存的为未勾选的数据
							stockchecking.stTaskListMap.get(record.data.waybillNo).serialNoList = newSerialNoArray;
						}
		    	    });
		    	    
//		    	         少货数据
		    	    var scanResultHaveNotArray = new Array();
		    	    stockchecking.stTaskResultListMap.each(function(key, value){
		    	    	scanResultHaveNotArray.push(value);
			    	});
		    	    
//	    	                  正常数据
		    	    var scanResultOkArray = new Array();
		    	    if(stockchecking.stTaskListMap.length > 0) {
		    	    	stockchecking.stTaskListMap.each(function(key, value){
		    	    		scanResultOkArray.push(value);
		    	    	});	    	    
		    	    }
		    	    
//		    	         多货数据
		    	    var scanResultSurplusArray = new Array();
		    	    stockchecking.superfluousGoodsMap.each(function(key, value){
		    	    	scanResultSurplusArray.push(value);
			    	});
		    	    
		    	    var stTaskId = Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.stTask.id').getForm().findField('id').getValue();
		    	    var jsonParam = {stockcheckingVO:{
						stTaskDto:{'id': stTaskId},
						selectedOperatorList: operatorArray,	   		 //清仓人
						scanResultOkList: scanResultOkArray,			 //正常确认货件
						scanResultHaveNotList: scanResultHaveNotArray,	 //少货货件
						scanResultSurplusList: scanResultSurplusArray	 //多货货件
						}
					};

        			//二次校验，少货件数大于50件时给出提示
        			if(lackNum > 50) {
        				var secondconfirm = Ext.Msg.confirm(stockchecking.i18n('Foss.stockchecking.alert.title'),stockchecking.i18n('Foss.stockchecking.tip.secconfirmsubmittask'), 
        						function(button, text) {
        					if(button == 'yes') {
        						form.getEl().mask(stockchecking.i18n('Foss.stockchecking.saving'));	
        						// 提交数据
        						Ext.Ajax.request({
        							url: stockchecking.realPath('confirmStTask.action'),
        							jsonData: jsonParam,
        							success: function(response){
        								form.getEl().unmask();
        								result = Ext.decode(response.responseText);
        								Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.confirmsubmittasksuccessful'));
        								Ext.getCmp('T_stockchecking-sttaskindex_content').getConfirmStockcheckingWindow().hide();
        								stockchecking.pagingBar.moveFirst();
        							},
        							exception : function(response) {
        								form.getEl().unmask();
        								var result = Ext.decode(response.responseText);
        								top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
        							}
        						});
        					} else {
        						return;
        					}
        					
        				});
        				secondconfirm.toFront();
        			} else {
        				form.getEl().mask(stockchecking.i18n('Foss.stockchecking.saving'));	
        				// 提交数据
						Ext.Ajax.request({
							url: stockchecking.realPath('confirmStTask.action'),
							jsonData: jsonParam,
							success: function(response){
								form.getEl().unmask();
								result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(stockchecking.i18n('Foss.stockchecking.tip.title'), stockchecking.i18n('Foss.stockchecking.tip.confirmsubmittasksuccessful'));
								Ext.getCmp('T_stockchecking-sttaskindex_content').getConfirmStockcheckingWindow().hide();
								stockchecking.pagingBar.moveFirst();
							},
							exception : function(response) {
								form.getEl().unmask();
								var result = Ext.decode(response.responseText);
								top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
							}
						});
        			}
				}
			});	
       }},
       {xtype:'button', text: stockchecking.i18n('Foss.stockchecking.button.close'), cls: 'yellow_button', handler:function(){
    	   Ext.getCmp('T_stockchecking-sttaskindex_content').getConfirmStockcheckingWindow().hide();
       }}
       ]
});

//查看明细页面，获取清仓任务库区信息
Ext.define('Foss.stockchecking.viewStockchecking.form.stTask',{
	extend: 'Ext.form.Panel',
	id:'Foss.stockchecking.viewStockchecking.form.stTask.id',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaults: {
		margin:'5 5 5 5',
		anchor: '90%'
	},
	items:[
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.taskNofull'), name:'taskNo', value:'', columnWidth:.35},
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.goodsArea'), name:'goodsareaname', value:'', columnWidth:.35},
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.ispda'), name:'ispda', value:'', columnWidth:.3},
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.createtime'), name:'createtime', value:'',columnWidth:.35},
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.finishtime'), name:'finishtime', value:'',columnWidth:.35},
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.taskStatus'), name:'taskStatus', value:'', columnWidth:.3},
	    {xtype:'displayfield',fieldLabel: stockchecking.i18n('Foss.stockchecking.sttask.model.operator'), name:'operators', value:'', columnWidth:1},
	    {xtype: 'textfield', fieldLabel: 'ID', name: 'id', hidden:true, hideLabel:true}
	]
});

//浏览窗口 运单统计查询 grid
Ext.define('Foss.stockchecking.viewStockchecking.grid.stWaybillList',{
	extend:'Ext.grid.Panel',
	id:'Foss.stockchecking.viewStockchecking.grid.stWaybillList.id',
	height: 500,
	frame: true,
	columns:[
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.waybillNo'), dataIndex: 'waybillNo'},
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.productCodeDesc'), dataIndex: 'productCodeDesc'},	//运输性质
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.stockGoodsQty'), dataIndex: 'goodsAreaNum'}, //库存件数
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.scanNum'), dataIndex: 'scanNum'},  //扫描件数
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodWeightTotal'), dataIndex: 'weight'},
        {header: stockchecking.i18n('Foss.stockchecking.sttask.model.goodVolumeTotal'), dataIndex: 'volume'},
        {
			xtype:'actioncolumn',
			text: stockchecking.i18n('Foss.stockchecking.button.operator'),
			align: 'center',
			items: [{
                tooltip: stockchecking.i18n('Foss.stockchecking.button.viewScanDetail'),
                iconCls: 'deppon_icons_showdetail',
                handler: function(grid, rowIndex, colIndex) {
                	var waybillNo = grid.getStore().getAt(rowIndex).data.waybillNo;
					
					Ext.getCmp('Foss.stockchecking.viewStockchecking.panel.id').getSubScanDetailWindow().show();
					
					var stTaskId = Ext.getCmp('Foss.stockchecking.viewStockchecking.form.stTask.id').getForm().findField('id').getValue();
					
					Ext.Ajax.request({
						url: stockchecking.realPath('queryScanDetailInStTask.action'),
						params: {
							'stockcheckingVO.stTaskDto.id': stTaskId,
							'stockcheckingVO.stTaskDto.waybillNo': waybillNo
						},
						success: function(response){
							result = Ext.decode(response.responseText);
							
							var scanDetailGrid = Ext.getCmp('Foss.stockchecking.viewStockchecking.scanDetail.grid.id');
//							加载结果集数据
							scanDetailGrid.store.loadData(result.stockcheckingVO.scanDetailDtoList);
						},
						exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				top.Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.exception.search.title'), result.message);
		    			}
					});
                }
		    }]
		}
	],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stockchecking.viewStockchecking.store.stWaybillList');
		
		me.callParent([cfg]);	
	}
});

//手工新增清仓任务 Panel
Ext.define('Foss.stockchecking.newStockchecking.panel',{
	extend: 'Ext.panel.Panel',
	id:'Foss.stockchecking.newStockchecking.panel.id',
	border:false,
	layout:'column',
	items:[{
			title: stockchecking.i18n('Foss.stockchecking.panel.GoodsAreaList.title'),
			columnWidth:65/100,
			height:500,
			items:[
			    Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.id') == null ? Ext.create('Foss.stockchecking.newStockchecking.form.searchStock') : Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.id'),
				Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id') == null ? Ext.create('Foss.stockchecking.newStockchecking.grid.searchStock') : Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchStock.id')	
			]
		},
		{
			columnWidth:1/100,
			height:500
		},
		{
			title: stockchecking.i18n('Foss.stockchecking.panel.operatorList.title'),
			columnWidth:34/100,
			height:500,
			items:[
			    Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchOperator.id') == null ? Ext.create('Foss.stockchecking.newStockchecking.form.searchOperator') : Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchOperator.id'),
				Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.id') == null ? Ext.create('Foss.stockchecking.newStockchecking.grid.searchOperator') : Ext.getCmp('Foss.stockchecking.newStockchecking.grid.searchOperator.id')
			]
		},
		{
			columnWidth:1,
			height:100,
			items:[
		       	Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.id') == null ? Ext.create('Foss.stockchecking.newStockchecking.form.submit') : Ext.getCmp('Foss.stockchecking.newStockchecking.form.submit.id')
			]
		}
	]
});

//手工确认清仓任务 Panel
Ext.define('Foss.stockchecking.confirmStockchecking.panel',{
	extend: 'Ext.panel.Panel',
	id:'Foss.stockchecking.confirmStockchecking.panel.id',
	frame:true,
	border:false,
	layout:'column',
	items:[{
//		title:'清仓库区信息',
		columnWidth:.6,
		height:200,
		items:[
		    Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.stTask.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.form.stTask') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.stTask.id')
		]
	},{
//		title:'清仓人',
		columnWidth:.4,
		height:200,
		items:[
		    Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.searchOperator.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.form.searchOperator') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.searchOperator.id'),
		    Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.grid.searchOperator') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.searchOperator.id')
	    ]
	},{
//		title:'货物清单',
		columnWidth:1,
		height:400,
		items:[
		    Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.stTaskList.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.grid.stTaskList') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.stTaskList.id')
		]
	},{
//		title:'货区多货清单',
		columnWidth:1,
		height:400,
		items:[
		    Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.grid.superfluousGoodsList.id')
		]
	},{
		columnWidth:1,
		height:100,
		items:[
		    Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.submit.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.form.submit') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.submit')
		]
	}
	],
	subSuperfluousGoodsWindow:null,	//多货 窗口
	getSubSuperfluousGoodsWindow:function(){
		if(this.subSuperfluousGoodsWindow == null){
			this.subSuperfluousGoodsWindow = Ext.create('Foss.stockchecking.confirmStockchecking.window.subSuperfluousGoods');
		}
		
		var superfluousGoodsForm = Ext.getCmp('Foss.stockchecking.confirmStockchecking.form.submit.id');
		superfluousGoodsForm.getForm().reset();
		
		return this.subSuperfluousGoodsWindow;
	}
});

//查看清仓任务明细 Panel
Ext.define('Foss.stockchecking.viewStockchecking.panel',{
	extend: 'Ext.panel.Panel',
	id:'Foss.stockchecking.viewStockchecking.panel.id',
	frame:true,
	border:false,
	layout:'column',
	items:[{
//		title:'清仓任务信息',
		columnWidth:1,
		items:[
	       Ext.getCmp('Foss.stockchecking.viewStockchecking.form.stTask.id') == null ? Ext.create('Foss.stockchecking.viewStockchecking.form.stTask') : Ext.getCmp('Foss.stockchecking.viewStockchecking.form.stTask.id')
	    ]
	},{
//		title:'清仓任务相关运单统计信息',
		columnWidth:1,
		height:500,
		items:[
		    Ext.getCmp('Foss.stockchecking.viewStockchecking.grid.stWaybillList.id') == null ? Ext.create('Foss.stockchecking.viewStockchecking.grid.stWaybillList') : Ext.getCmp('Foss.stockchecking.viewStockchecking.grid.stWaybillList.id')
		]
	}],
	subScanDetailWindow:null,	//手工建立清仓任务 窗口
	getSubScanDetailWindow:function(){
		if(this.subScanDetailWindow == null){
			this.subScanDetailWindow = Ext.create('Foss.stockchecking.viewStockchecking.window.scanDetail');
		}
		
		return this.subScanDetailWindow;
	}
});

//手工新增、编辑清仓任务窗口
Ext.define('Foss.stockchecking.window.newStockchecking', {
	extend:'Ext.window.Window',
	title: stockchecking.i18n('Foss.stockchecking.window.newtask.title'),
	modal:true,
	align:'center',
	width:1000,
	height:700,
	closeAction:'hide',
	autoScroll:true,
	border:false,
	items:[
	    Ext.getCmp('Foss.stockchecking.newStockchecking.panel.id') == null ? Ext.create('Foss.stockchecking.newStockchecking.panel') : Ext.getCmp('Foss.stockchecking.newStockchecking.panel.id')
	]
});

//手工确认清仓任务窗口
Ext.define('Foss.stockchecking.window.confirmStockchecking', {
	extend:'Ext.window.Window',
	title: stockchecking.i18n('Foss.stockchecking.window.confirmtask.title'),
	//modal:true,
	align:'center',
	closeAction:'hide',
	width:1000,
	height:700,
	autoScroll:true,
	border:false,
	items:[
       Ext.getCmp('Foss.stockchecking.confirmStockchecking.panel.id') == null ? Ext.create('Foss.stockchecking.confirmStockchecking.panel') : Ext.getCmp('Foss.stockchecking.confirmStockchecking.panel.id')
    ]
});

//浏览清仓任务明细窗口
Ext.define('Foss.stockchecking.window.viewStockchecking', {
	extend: 'Ext.window.Window',
	title: stockchecking.i18n('Foss.stockchecking.window.viewtaskdetail.title'),
	modal: true,
	align: 'center',
	closeAction: 'hide',
	width: 1000,
	height: 700,
	autoScroll: true,
	border: false,
	items: [
	    Ext.getCmp('Foss.stockchecking.viewStockchecking.panel.id') == null ? Ext.create('Foss.stockchecking.viewStockchecking.panel') : Ext.getCmp('Foss.stockchecking.viewStockchecking.panel.id')
	]
});

 
/**
 *查询统计信息的方法
 ***/
 function statistics(){ 
	 Ext.Ajax.request({
			url: stockchecking.realPath('statistics.action'),
			async :  false,//同步调用；
			success:function(response){
				var result = Ext.decode(response.responseText);
				if(result.stockcheckingVO!=null){
					goodsAreaCount=result.stockcheckingVO.goodsAreaCount;//当前转运场库区个数
					 taskDoneCount=result.stockcheckingVO.taskDoneCount; //已完成清仓任务数
					 areaUndoCount=result.stockcheckingVO.areaUndoCount;	//未完成任务，库区数	
					Ext.getCmp('statistics').setValue('当前转运场库区'+goodsAreaCount+'个，今日已完成清仓任务数'+taskDoneCount+'个，未清仓库区'+areaUndoCount+'个');

				}
			},
			exception : function(response) {
				Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), result.message);
			}
		});
 }


Ext.onReady(function() {
	Ext.QuickTips.init();
	var stockcheckingQueryform = Ext.create('Foss.stockchecking.QueryForm');
	stockchecking.stockcheckingQueryform = stockcheckingQueryform;
	var stockcheckingQuerygrid = Ext.create('Foss.stockchecking.StTaskGrid');
//	存放库区查询中勾选的库区编号
	stockchecking.goodsAreaCodeMap = new Ext.util.HashMap();
//	确认清仓任务页面中，存放货物列表，也在手工确认时存放少货列表
	stockchecking.stTaskListMap	= new Ext.util.HashMap();
//	多货的映射map
	stockchecking.superfluousGoodsMap = new Ext.util.HashMap();
//	最终需提交的的货物列表,正常确认的货件列表
	stockchecking.stTaskResultListMap = new Ext.util.HashMap();
	
	Ext.create('Ext.panel.Panel',{
		id:'T_stockchecking-sttaskindex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		newStockcheckingWindow: null,	//手工建立清仓任务 窗口
		getNewStockcheckingWindow:function(){
			if(this.newStockcheckingWindow == null){
				this.newStockcheckingWindow = Ext.create('Foss.stockchecking.window.newStockchecking');
			}
			
			//清空已选的库区
			stockchecking.goodsAreaCodeMap.clear();
			
			return this.newStockcheckingWindow;
		},
		confirmStockcheckingWindow: null,	//手工确认清仓任务 窗口
		getConfirmStockcheckingWindow:function(){
			if(this.confirmStockcheckingWindow == null){
				this.confirmStockcheckingWindow = Ext.create('Foss.stockchecking.window.confirmStockchecking');
			}
			
			//清空已选的库区
			stockchecking.goodsAreaCodeMap.clear();
			//清空货物列表相关临时map
			stockchecking.stTaskListMap.clear();
			stockchecking.stTaskResultListMap.clear();
			stockchecking.superfluousGoodsMap.clear();
			
			return this.confirmStockcheckingWindow;
		},
		viewStockcheckingWindow: null,  //查看清仓任务明细窗口
		getViewStockcheckingWindow: function(){
			if(this.viewStockcheckingWindow == null){
				this.viewStockcheckingWindow = Ext.create('Foss.stockchecking.window.viewStockchecking');
			}
			
			return this.viewStockcheckingWindow;
		},
		items : [stockcheckingQueryform, stockcheckingQuerygrid],
		renderTo: 'T_stockchecking-sttaskindex-body'
	});
	
//	判断是否为营业部，若登陆人切换为营业部，则隐藏"库区"选择框
	Ext.Ajax.request({
		url: stockchecking.realPath('queryStockOrg.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.stockcheckingVO.isTransferCenter == 'N'){
//				隐藏查询页面中的"库区"选项
				Ext.getCmp('Foss.stockchecking.QueryForm.id').getForm().findField('goodsArea').hide();
				Ext.getCmp('Foss.stockchecking.QueryForm.id').getForm().findField('goodsAreaUsage').hide();
//				隐藏手工建立清仓任务 页面中的"库区"选项
				Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsArea.id').hide();
				Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsAreaUsage.id').hide();
				Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.gap.id').columnWidth = '0.9';
				//把统计信息隐藏
				Ext.getCmp('statistics').hide();
			}else{
				Ext.getCmp('Foss.stockchecking.newStockchecking.form.searchStock.goodsArea.id').deptCode = result.stockcheckingVO.stockOrgCode;
				Ext.getCmp('Foss.stockchecking.QueryForm.id').getForm().findField('goodsArea').deptCode = result.stockcheckingVO.stockOrgCode;
				statistics();
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			//Ext.ux.Toast.msg('获取库存部门失败', result.message, 'error', 3000);
			Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), result.message);
		}
	});
});