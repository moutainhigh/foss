waybill.waybillToSupple.getTargetDate = function(date, day) {
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

waybill.waybillToSupple.getTargetDate1 = function(date, day) {
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
waybill.invalidType = null;
//创建一个待办事项枚举store
Ext.define('Foss.ToDo.Store.ToDoTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//创建一个待办事项枚举store
Ext.define('Foss.Supple.Store.WaybillSuppleStore',{
	extend: 'Ext.data.Store',
	fields: [
	    { name: 'waybillSuppleId',type:'string' },//运单号
		{ name: 'oldWaybillNo',type:'string' },//运单号
		{ name: 'newWaybillNo',type:'string' },//运单号
		{ name: 'workflowNo',type:'string' },//运单号
	    { name: 'orderNo',type:'string' }, //订单号
		{ name: 'billTime',convert:dateConvert }, //业务时间
		{ name: 'invalidReason',type:'string' },  //作废原因
		{ name: 'invalidor',type:'string' },  //作废人
		{ name: 'invalidOrgName',type:'string' },//作废人所在组织
		{ name: 'invalidTime',convert:dateConvert },//作废时间,
		{ name: 'invalidType',type:'string' }//作废类型
	],
	//定义一个代理对象
	//是否自动查询
	autoLoad: false,
	//默认每页数据大小
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		url:waybill.realPath('queryWaybillToSuppleAction.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.waybillSuppleResultDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			var queryParams = waybill.waybillToSupple.waybillToSuppleForm.getValues();
			var form = waybill.waybillToSupple.waybillToSuppleForm.getForm();
			if(!form.isValid()){
				return false;
			}
			Ext.apply(operation, {
				params : {					
					'vo.waybillToSuppleCondtionDto.waybillNo': queryParams.waybillNo,
					'vo.waybillToSuppleCondtionDto.orderNo': queryParams.orderNo,
					'vo.waybillToSuppleCondtionDto.beginInvalidTime': queryParams.beginInvalidTime,
					'vo.waybillToSuppleCondtionDto.endInvalidTime': queryParams.endInvalidTime,
					'vo.waybillToSuppleCondtionDto.invalidType': queryParams.invalidType,
					'vo.waybillToSuppleCondtionDto.invalidOrgCode': queryParams.invalidOrgCode,
					'vo.waybillToSuppleCondtionDto.workflowNo': queryParams.workflowNo
				}
			});	
		}
	}
});
//单票入库界面 流水号表格 Store
Ext.define('Foss.waybill.stockmanage.GoodsSerialNOStore',{
	extend: 'Ext.data.Store',
	fields: ['serialNO'],
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: waybill.realPath('queryGoods.action'),
        timeout:300000,
		reader : {
			type : 'json',
			root : 'vo.stockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'vo.waybillToSuppleCondtionDto.waybillNo' : Ext.getCmp('Foss_stock_stockmanage_GoodsSerialNOGrid_WaybillNO_ID').getValue()
				}
			});	
		},
	}
});
Ext.define('Foss.waybill.waybillSupple.waybillSuppleModel', {
    extend: 'Ext.data.Model',
    fields : [
    {
        name : 'id',//ID
        type : 'string'
    },{
      name : 'waybillId',//方案名称
        type : 'string'
    },{
      name : 'waybillPendingId',//方案名称
        type : 'string'
    },{
        name : 'actualFreightId',
        type : 'string'
    },{
        name : 'invalidReason',
        type : 'string'
    },{
      name : 'invalidOrgCode',
        type : 'string'
    },{
        name : 'createTime',
        type : 'date',
        defaultValue : null,
        convert : waybill.changeLongToDate
    },{
        name : 'modifyTime',
        type : 'date',
        defaultValue : null,
        convert : waybill.changeLongToDate
    },{
      name : 'invalidType',
        type : 'string'
    },{
      name : 'workflowNo',
        type : 'string'
    },{
      name : 'oldWaybillNo',
        type : 'string'
    },{
      name : 'orderNo',
        type : 'string'
    },{
      name : 'newWaybillNo',
        type : 'string'
    }]
});
//查询条件
Ext.define('Foss.Supple.Form.waybillToSuppleForm',{
	extend:'Ext.form.Panel',
	title: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.queryCondition'),//查询条件,
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 60
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name:'waybillNo',
		fieldLabel:waybill.waybillToSupple.i18n('pkp.waybill.todoAction.waybillNo'),//运单号,
		vtype: 'waybill',
		minLength:8,
		maxLength:60,
		columnWidth: 0.2
	},{
		name:'orderNo',
		fieldLabel:waybill.waybillToSupple.i18n('waybill.waybillToSupple.orderNo'),//订单号,
		minLength:8,
		maxLength:60,
		columnWidth: 0.2
	},{
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		name: 'invalidType',
		fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.operateStatus'),//处理状态,
		value:'',
		columnWidth: 0.16,
		editable : false,
		store:Ext.create('Foss.ToDo.Store.ToDoTypeStore',{
		  data:{
	       'items':[
	            {'code':'','name':waybill.waybillToSupple.i18n('pkp.waybill.todoAction.all')},
				{'code':'WAYBILL_INVALID','name':waybill.waybillToSupple.i18n('waybill.waybillToSupple.invalidWaybill')},
				{'code':'WAYBILL_RENEW','name':waybill.waybillToSupple.i18n('waybill.waybillToSupple.renewWaybill')},
				{'code':'EWAYBILL_INVALID','name':waybill.waybillToSupple.i18n('waybill.waybillToSupple.ewaybillInvalid')}
		   ]}
		})
	},{
		xtype: 'rangeDateField',
		fieldLabel: waybill.waybillToSupple.i18n('waybill.waybillToSupple.invalidTime'),//业务开单时间,
		dateType: 'datetimefield_date97',
		fieldId: 'FOSS_waybillToSupple_Id',
		fromName: 'beginInvalidTime',
		toName: 'endInvalidTime',
		disallowBlank:true,
		editable:false,
		fromValue: Ext.Date.format(waybill.waybillToSupple.getTargetDate(new Date(),-6),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(waybill.waybillToSupple.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: .44
	},{
		name:'workflowNo',
		fieldLabel:waybill.waybillToSupple.i18n('pkp.waybill.todoAction.workflowNo'),//订单号,
		minLength:8,
		maxLength:60,
		labelWidth: 60,
		columnWidth: 0.2
	},{
		name:'invalidOrgCode',
		labelWidth: 30,
		xtype : 'dynamicorgcombselector',
		fieldLabel:waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeiren') + waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeirenBumen'),//作废人所在部门,
		labelWidth: 80,
		columnWidth: 0.2
	},{
		xtype: 'displayfield',
		columnWidth : .6,
		name:'attention',
		fieldStyle:'color:red;font-size:2px;',
		value:'该功能只适用于待补录运单数据作废与转储运单数据恢复。在作废待补录运单数据之前，请确保已经新开单并且已经更换好货物标签。已经作废的待补录运单数据不能被还原，请谨慎操作，感谢大家的配合'//交接单号,
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:waybill.waybillToSupple.i18n('pkp.waybill.todoAction.reset'),//重置,
			columnWidth:.08,
			handler:function(){
				waybill.waybillToSupple.waybillToSuppleForm.getForm().reset();
				var form = this.up('form').getForm();
				form.findField('waybillNo').setValue("");
				form.findField('orderNo').setValue("");
				form.findField('workflowNo').setValue("");
				form.findField('invalidType').setValue("");//invalidOrgCode
				form.findField('invalidOrgCode').setValue("");//
				form.findField('beginInvalidTime').setValue(Ext.Date.format(waybill.waybillToSupple.getTargetDate(new Date(),-6),'Y-m-d H:i:s'));
				form.findField('endInvalidTime').setValue(Ext.Date.format(waybill.waybillToSupple.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:waybill.waybillToSupple.i18n('pkp.waybill.todoAction.query'),//查询,
			disabled:!waybill.waybillToSupple.isPermission('waybillToSuppleIndex/waybillToSuppleIndexquerybutton'),
			hidden:!waybill.waybillToSupple.isPermission('waybillToSuppleIndex/waybillToSuppleIndexquerybutton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				//入库可见时间根据两个时间进行对比，结束时间比开始时间还小
				var beginBillTime = form.getValues().beginBillTime;
				var endBillTime = form.getValues().endBillTime;
				var result = Ext.Date.parse(endBillTime,'Y-m-d H:i:s') - Ext.Date.parse(endBillTime,'Y-m-d H:i:s');
				if(result / (24 * 60 * 60 * 1000) >=30){
					Ext.ux.Toast.msg(waybill.waybillToSupple.i18n('pkp.waybill.todoAction.tip'), waybill.waybillToSupple.i18n('foss.pkp.waybill.waybillManagerService.exception.overThirtyDays'), 'error', 3000);
					return;
				}
				if(form.isValid()){
					var grid = Ext.getCmp('T_waybill-waybillToSuppleIndex_content').getWaybillToSuppleGrid();
		        	grid.getPagingToolbar().moveFirst();
				}
			}
		}]
	}]
});
//Foss.waybill.WayBillStockWindow
Ext.define('Foss.waybill.WayBillStockWindow',{
	extend : 'Ext.window.Window',
	title: waybill.waybillToSupple.i18n('waybill.waybillToSupple.deleteWaybillStock'),//新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :400,
	height :350,
	grid:null,	
	//父 grid
	listeners:{
		beforehide:function(){
			this.getSuppleWayBillForm().getForm().reset();
		},
		beforeshow:function(me){
//			me.getSuppleWayBillForm().getForm().findField('invalidType').setValue(waybill.invalidType); 
		}
	},
    //明细信息新增-FORM
    wayBillStockForm:null,
    getWayBillStockForm:function(){
    	if(Ext.isEmpty(this.suppleWayBillForm)){
    		this.wayBillStockForm = Ext.create('Foss.waybill.suppleWayBill.DeleteWayBillStockGridForm');
    	}
    	return this.wayBillStockForm;
    },
    
    //想GRID中设置数据
    commitWayBillStockForm:function(){
    	
    },
    constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items = [me.getWayBillStockForm()];//设置window的元素
		me.fbar = [{
			text : waybill.waybillToSupple.i18n('foss.pricing.save'),//提交
			cls:'yellow_button',
			handler :function(){
				me.commitWayBillStockForm();
			} 
		}];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.waybill.SuppleWayBillWindow',{
	extend : 'Ext.window.Window',
	title: waybill.waybillToSupple.i18n('waybill.waybillToSupple.abortWaybill'),//新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,
	width :400,
	height :390,
	grid:null,																	//父 grid
	listeners:{
		beforehide:function(me){
			me.getSuppleWayBillForm().getForm().reset();
		},
		beforeshow:function(me){
//			me.getSuppleWayBillForm().getForm().findField('invalidType').setValue(waybill.invalidType); 
		}
	},
    //明细信息新增-FORM
    suppleWayBillForm:null,
    getSuppleWayBillForm:function(){
    	if(Ext.isEmpty(this.suppleWayBillForm)){
    		this.suppleWayBillForm = Ext.create('Foss.waybill.suppleWayBill.AddSuppleWayBillForm');
    	}
    	return this.suppleWayBillForm;
    },
    
    //想GRID中设置数据
    commitSuppleWayBillForm:function(){
    	var me = this;
    	//得到明细form
    	var form = me.getSuppleWayBillForm().getForm();
    	if(form.isValid()){
    		var waybillStockLog = new Foss.waybill.waybillSupple.waybillSuppleModel();
    		var oldWaybillNo = form.findField('oldWaybillNo').getValue();
    		var newWaybillNo = form.findField('newWaybillNo').getValue();
    		var orderNo = form.findField('orderNo').getValue();
    		var workflowNo = form.findField('workflowNo').getValue();
    		var invalidType = form.findField('invalidType').getValue();
    		var invalidReason = form.findField('invalidReason').getValue();
    		if('WAYBILL_INVALID' == invalidType){
    			if(Ext.isEmpty(oldWaybillNo) && Ext.isEmpty(orderNo)){
        			Ext.ux.Toast.msg(waybill.waybillToSupple.i18n('pkp.waybill.todoAction.tip'), waybill.waybillToSupple.i18n('waybill.waybillToSupple.waybillNoOrderNoNotAllNull'), 'error', 3000);
        			return;
        		}
    		}else if('WAYBILL_RENEW' == invalidType){
    			if(Ext.isEmpty(oldWaybillNo)){
        			Ext.ux.Toast.msg(waybill.waybillToSupple.i18n('pkp.waybill.todoAction.tip'), waybill.waybillToSupple.i18n('foss.waybillRfc.waybillNumberNotExist'), 'error', 3000);
        			return;
        		}
    		}
    		if(Ext.isEmpty(invalidReason)){
    			Ext.ux.Toast.msg(waybill.waybillToSupple.i18n('pkp.waybill.todoAction.tip'), waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeiyuanyinNotNull'), 'error', 3000);
    			return;
    		}
    		waybillStockLog.set('oldWaybillNo',oldWaybillNo);
    		waybillStockLog.set('newWaybillNo',newWaybillNo);
    		waybillStockLog.set('orderNo',orderNo);
    		waybillStockLog.set('workflowNo',workflowNo);
    		waybillStockLog.set('invalidType',invalidType);
    		waybillStockLog.set('invalidReason',invalidReason);
    		//制定json请求参数
			var params = {'vo':{'waybillSupplementLogEntity':waybillStockLog.data}};
			//ajax请求
			var url = waybill.realPath('addWaybillToSuppleRecord.action');			
			//成功提示
			var successFun = function(json){
				waybill.showInfoMes(json.message);
		    };
		    //失败提示
		    var failureFun = function(json){
		    	if(Ext.isEmpty(json)){
		    		waybill.showErrorMes(waybill.waybillToSupple.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					waybill.showErrorMes(json.message);
				}
		    };
		    //调用ajax请求
		    waybill.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
	    	me.close();
    	}
    },
    constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items = [me.getSuppleWayBillForm()];//设置window的元素
		me.fbar = [{
			text : waybill.waybillToSupple.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : waybill.waybillToSupple.i18n('foss.pricing.reset'),//重置
			handler :function(){
				me.getSuppleWayBillForm().getForm().reset();
			} 
		},{
			text : waybill.waybillToSupple.i18n('foss.pricing.save'),//提交
			cls:'yellow_button',
			handler :function(){
				me.commitSuppleWayBillForm();
			} 
		}];
		me.callParent([cfg]);
	}
});

//单票入库界面 流水号表格
Ext.define('Foss.waybill.suppleWayBill.DeleteWayBillStockGridForm', {
	extend:'Ext.grid.Panel',
	columnLines: true,
    frame: true,
    height: 300,
	columns: [{
		//header: '流水号',
		header: waybill.waybillToSupple.i18n('foss.stock.serialno'), 
		dataIndex: 'serialNo' 
	}],   
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.waybill.stockmanage.GoodsSerialNOStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		waybill.waybillToSupple.inStockpagBar = me.bbar;
		me.tbar = [{
			xtype: 'textfield',
			//fieldLabel: '运单号',
			fieldLabel: waybill.waybillToSupple.i18n('foss.stock.waybill'),
	        name: 'waybillNo',
	        allowBlank: false,
			//blankText:'字段不能为空'
			blankText:waybill.waybillToSupple.i18n('foss.stock.notnull'),
	        columnWidth:.4,
	        vtype: 'waybill'
		},{
			xtype: 'button',
			text: waybill.waybillToSupple.i18n('foss.stock.search'),//查询
			gridContainer: this,
			handler: function() {
				waybill.waybillToSupple.inStockSerialNOMap.clear();
//				stock.stockmanage.goodsSerialNOGrid.store.load();
			}
		}];	
		me.callParent([cfg]);
	},
	listeners: {
		select : function(rowModel, record, index, eOpts) {
				var serialNO = record.get('serialNo');
				waybill.waybillToSupple.inStockSerialNOMap.add(serialNO,serialNO);
		},
		deselect : function(rowModel, record, index, eOpts) {
			var serialNO = record.get('serialNO');
			waybill.waybillToSupple.inStockSerialNOMap.removeAtKey(serialNO);
		}
}
});

Ext.define('Foss.waybill.suppleWayBill.DeleteWayBillStockForm', {
	extend : 'Ext.form.Panel',
	title : waybill.waybillToSupple.i18n('waybill.waybillToSupple.addWaybillSupple'),//新增作废数据
	frame: true,
	flex:1,
	margin:'0 0 0 0',
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	width:300,
    	colspan : 1
    },
    layout:{
    	type: 'table',
        columns: 1
	},
    constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items = [{
			name: 'waybillNo',//运单号
			vtype: 'waybill',
			allowBlank:false,
			maxLength:200,
	        fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.waybillNo'),
	        xtype : 'textfield'
		},{
			name: 'orderNo',//订单号
			allowBlank:false,
			maxLength:200,
	        fieldLabel: waybill.waybillToSupple.i18n('waybill.waybillToSupple.orderNo'),
	        xtype : 'textfield'
		},{
			name: 'workflowNo',//工作流号
			allowBlank:false,
			maxLength:200,
	        fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.workflowNo'),
	        xtype : 'textfield'
		},{
			name: 'invalidType',//作废类型
			hide : true,
	        fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.workflowNo')
		},{
			name: 'invalidReason',//作废原因
	        fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeiyuanyin'),
	        maxLength:200,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.waybill.suppleWayBill.AddSuppleWayBillForm', {
	extend : 'Ext.form.Panel',
	title : waybill.waybillToSupple.i18n('waybill.waybillToSupple.addWaybillSupple'),//新增作废数据
	frame: true,
	flex:1,
	margin:'0 0 0 0',
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	width:300,
    	colspan : 1
    },
    layout:{
    	type: 'table',
        columns: 1
	},
    constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items = [{
		    xtype:'combobox',
			displayField:'name',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
			name: 'invalidType',
			fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.operateStatus'),//处理状态,
			value:'WAYBILL_INVALID',
			editable : false,
			store:Ext.create('Foss.ToDo.Store.ToDoTypeStore',{
			  data:{
		       'items':[
					{'code':'WAYBILL_INVALID','name':waybill.waybillToSupple.i18n('waybill.waybillToSupple.invalidWaybill')},
					{'code':'WAYBILL_RENEW','name':waybill.waybillToSupple.i18n('waybill.waybillToSupple.renewWaybill')}
			   ]}
			}),listeners : { //是否征收保管费
				'select':function(combo,records,eOpts){
		          if(records.length>0){
		            var form = this.up('form').getForm();             
		            var record = form.findField("invalidType").getValue();
		            if('WAYBILL_RENEW' == record){
		            	form.findField('orderNo').hide();
		            	form.findField('newWaybillNo').hide();
		            }else{
		            	form.findField('orderNo').show();
		            	form.findField('newWaybillNo').show();
		            }
		          }
		        }
			}
		},{
			name: 'oldWaybillNo',//运单号
			vtype: 'waybill',
			maxLength:50,
	        fieldLabel: waybill.waybillToSupple.i18n('waybill.waybillToSupple.invalidWaybillNo'),
	        xtype : 'textfield'
		},{
			name: 'orderNo',//订单号
			maxLength:50,
	        fieldLabel: waybill.waybillToSupple.i18n('waybill.waybillToSupple.orderNo'),
	        xtype : 'textfield'
		},{
			name: 'newWaybillNo',//运单号
			vtype: 'waybill',
			maxLength:50,
	        fieldLabel: waybill.waybillToSupple.i18n('waybill.waybillToSupple.NewWaybillNo'),
	        xtype : 'textfield'
		},{
			name: 'workflowNo',//工作流号
			maxLength:50,
	        fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.workflowNo'),
	        xtype : 'textfield'
		},{
			name: 'invalidReason',//作废原因
	        fieldLabel: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeiyuanyin'),
	        maxLength:150,
	        disallowBlank:true,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
//待删除记录表
Ext.define('Foss.Supple.waybillToSuppleResultGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	emptyText:waybill.waybillToSupple.i18n('pkp.waybill.todoAction.emptyText'),//查询结果为空！,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//增加表格列的分割线
	columnLines: true,
	//定义表格的标题
	title:waybill.waybillToSupple.i18n('pkp.waybill.todoAction.suppleRecord'),//作废记录结果表,
	collapsible: true,
	animCollapse: true,
	store: null,   
	//----------定义某行的颜色结束---------------    
    dockedItems : [{
        xtype : 'toolbar',
        dock : 'top',
        layout : 'column',
        items:[{
			xtype:'button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			margin : '0 0 0 15',
			columnWidth:.082,
			name:'exportTodoActionInfo',
			text:waybill.waybillToSupple.i18n('waybill.waybillToSupple.abortWaybill'),//作废运单
			disabled:!waybill.waybillToSupple.isPermission('waybillToSupple/waybillToSuppleAddButton'),//ExportButton
			hidden:!waybill.waybillToSupple.isPermission('waybillToSupple/waybillToSuppleAddButton'),//ExportButton
			handler : function(){
				var win = Ext.create('Foss.waybill.SuppleWayBillWindow').show();
			}
		}/*,{//暂且把这段代码注释掉，因为一线肯定不知道怎么操作
			xtype:'button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			margin : '0 0 0 15',
			columnWidth:.082,
			name:'exportTodoActionInfo',
			text:waybill.waybillToSupple.i18n('waybill.waybillToSupple.deleteWaybillStock'),//删除库存
			handler : function(){
				waybill.invalidType = 'STOCK_INVALID';
				var win = Ext.create('Foss.waybill.WayBillStockWindow').show();
			}
		}*/,{
			xtype:'button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				seconds: 3
			}),
			margin : '0 0 0 15',
			columnWidth:.082,
			name:'exportTodoActionInfo',
			text:waybill.waybillToSupple.i18n('waybill.waybillToSupple.exportWaybillSuppleData'),//导出数据
			disabled:!waybill.waybillToSupple.isPermission('waybillToSupple/waybillToSuppleExportButton'),//
			hidden:!waybill.waybillToSupple.isPermission('waybillToSupple/waybillToSuppleExportButton'),//
			handler : function(){
				var waybillToSuppleForm = waybill.waybillToSupple.waybillToSuppleForm;
				if (waybillToSuppleForm != null) {
					var queryParams = waybillToSuppleForm.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}	
					//获取查询出来的异常信息
					var waybillToSuppleGridStore = waybill.waybillToSupple.waybillToSuppleGrid.getStore();	
					//若异常信息不为空
					if(waybillToSuppleGridStore.getCount()!=0){
						Ext.Ajax.request({
							url:waybill.realPath('exportWaybillSuppleAction.action'),
							form: Ext.fly('downloadAttachFileForm'),
							method : 'POST',
							params : {					
								'waybillToSuppleVo.waybillToSuppleCondtionDto.waybillNo': queryParams.waybillNo,
								'waybillToSuppleVo.waybillToSuppleCondtionDto.orderNo': queryParams.orderNo,
								'waybillToSuppleVo.waybillToSuppleCondtionDto.beginInvalidTime': queryParams.beginInvalidTime,
								'waybillToSuppleVo.waybillToSuppleCondtionDto.endInvalidTime': queryParams.endInvalidTime,
								'waybillToSuppleVo.waybillToSuppleCondtionDto.invalidType': queryParams.invalidType,
								'waybillToSuppleVo.waybillToSuppleCondtionDto.invalidOrgCode': queryParams.invalidOrgCode,
								'waybillToSuppleVo.waybillToSuppleCondtionDto.workflowNo': queryParams.workflowNo
							},
							isUpload: true
						});
					}else{
						//或者提示不能导出
						Ext.ux.Toast.msg('提示','没有记录，不能导出', 'error', 3000);
					}
			 }}
		}]
	}],
	//定义表格列信息
	columns: [{
		header:'id',//id,
		dateIndex:'waybillSuppleId',
		hidden: true,
		align: 'center'
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('waybill.waybillToSupple.invalidWaybillNo'),//运单号, 
		//关联model中的字段名
		dataIndex: 'oldWaybillNo',
		flex:0.1,
		align: 'center'
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('waybill.waybillToSupple.NewWaybillNo'),//运单号, 
		//关联model中的字段名
		dataIndex: 'newWaybillNo',
		flex:0.1,
		align: 'center'
	},{ 
		//字段标题
		header: waybill.waybillToSupple.i18n('waybill.waybillToSupple.orderNo'),//订单号, 
		//关联model中的字段名
		dataIndex: 'orderNo',
		flex:0.1,
		align: 'center'
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.workflowNo'),//工作流号, 
		//关联model中的字段名
		dataIndex: 'workflowNo',
		flex:0.1,
		align: 'center'//invalidType
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.invalidType'),//工作流号, 
		//关联model中的字段名
		dataIndex: 'invalidType',
		flex:0.1,
		align: 'center',//invalidType
		renderer : function(value, metadata, record, rowIndex, columnIndex, store){
			if(record.get('invalidType')=='WAYBILL_INVALID'){
				return waybill.waybillToSupple.i18n('waybill.waybillToSupple.invalidWaybill');
			}else if(record.get('invalidType')=='WAYBILL_RENEW'){//WAYBILL_INVALID  WAYBILL_RENEW
				return waybill.waybillToSupple.i18n('waybill.waybillToSupple.renewWaybill');
			}else if(record.get('invalidType')=='EWAYBILL_INVALID'){//WAYBILL_INVALID  WAYBILL_RENEW
				return waybill.waybillToSupple.i18n('waybill.waybillToSupple.ewaybillInvalid');
			}else{
				return record.get('invalidType');
			}
		}
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeiyuanyin'),//作废原因,
		//关联model中的字段名
		dataIndex: 'invalidReason', 
		flex:0.3,
		align: 'center'
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeiren'),//作废人, 
		//关联model中的字段名
		dataIndex: 'invalidor',
		flex:0.07,
		align: 'center'
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeiren') + '<br>' + waybill.waybillToSupple.i18n('pkp.waybill.todoAction.zuofeirenBumen'),//作废人所在部门,
		//关联model中的字段名
		dataIndex: 'invalidOrgName', 
		flex:0.12,
		align: 'center'
	},{
		//字段标题
		header: waybill.waybillToSupple.i18n('waybill.waybillToSupple.invalidTime'),//作废时间, 
		//关联model中的字段名
		dataIndex: 'invalidTime', 
		align: 'center',
		flex:0.14,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return null;
			}
		} 
	}],
	pagingToolbar : null,	
  	getPagingToolbar : function() {	
  		if (this.pagingToolbar == null) {	
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:this.store,
//				plugins: 'pagesizeplugin',
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
  		}	
  		return this.pagingToolbar;	
  	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Supple.Store.WaybillSuppleStore');
		//添加分页工具条
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_waybill-waybillToSuppleIndex_content')) {
		return;
	}
	waybillToSuppleForm = Ext.create('Foss.Supple.Form.waybillToSuppleForm'); 
	waybillToSuppleGrid = Ext.create('Foss.Supple.waybillToSuppleResultGrid'); 
	waybill.waybillToSupple.waybillToSuppleForm = waybillToSuppleForm;
	waybill.waybillToSupple.waybillToSuppleGrid = waybillToSuppleGrid;
	waybill.waybillToSupple.inStockSerialNOMap = new Ext.util.HashMap();//单件入库界面存放已勾选的流水号
	Ext.create('Ext.panel.Panel',{
		id: 'T_waybill-waybillToSuppleIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getWaybillToSuppleForm : function() {
			if(Ext.isEmpty(this.queryform)){
				this.waybillToSuppleForm = Ext.create('Foss.Supple.Form.waybillToSuppleForm');//查询结果GRID
			}
			return waybillToSuppleForm;
		},
		//获得查询结果GRID
		getWaybillToSuppleGrid : function() {
			if(Ext.isEmpty(this.gridPanel)){
				this.waybillToSuppleGrid = Ext.create('Foss.Supple.waybillToSuppleResultGrid');//查询结果GRID
			}
			return waybillToSuppleGrid;
		},
		items: [waybillToSuppleForm,waybillToSuppleGrid],
		renderTo: 'T_waybill-waybillToSuppleIndex-body'
	});
});