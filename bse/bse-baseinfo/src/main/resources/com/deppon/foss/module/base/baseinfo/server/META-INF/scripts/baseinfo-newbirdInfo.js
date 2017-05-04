 /**
  * 转换long类型为日期
  *@param value 要转换的时间
  */
baseinfo.newbirdInfo.longToDateConvert = function(value) {
	if (!Ext.isEmpty(value)) {
		return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	} else {
		return null;
	}
}

baseinfo.newbirdInfo.newbirdInfoConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

//查询营业部信息
baseinfo.newbirdInfo.salesDeptFormQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var transport = form.findField('transport').getValue();	
	
	
	// 设置参数
	params = {};
	Ext.apply(params, {
		'newbirdinfoVO.newbirdinfoEntity.transport' : transport		
	});
	
	//获取grid及grid的store,并给store赋值
	var a_win = Ext.getCmp('Foss_baseinfo_newbirdInfo_addSalesDeptWindow_Id');
	var grid = a_win.items.items[1];
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		grid.show();
	    	}
	       }
	    }); 
}


//查询已配置(快递点部营业部映射关系)的快递点部FORM查询方法: 
baseinfo.newbirdInfo.newbirdInfoQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var transport = form.findField('transport').getValue();		
	// 设置参数
	params = {};
	Ext.apply(params, {
		'newbirdinfoVO.newbirdinfoEntity.transport' : transport
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-newbirdInfoIndex_content').getNewbirdInfoGrid();
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		grid.show();
	    	}
	       }
	    }); 
}

//---------------------------------------------------以上为方法实现区------------------------------------
//--------------------------------------------------快递点部营业部映射关系初始界面 begin-------------------------

Ext.define('Foss.baseinfo.newbirdInfo.ActiveStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询已配置(快递点部营业部映射关系)的快递点部FORM
Ext.define('Foss.baseinfo.newbirdInfo.NewbirdInfoQueryForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.newbirdInfo.i18n('foss.baseinfo.queryCondition'),
	frame:true,
	height:120,
	defaults:{
		margin :'20 0 0 10',
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [
				{
					fieldLabel:baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.transport'),
					name:'transport',
					labelWidth :80,
					columnWidth:.26,
				},{
					xtype : 'button', 
					tooltip: baseinfo.newbirdInfo.i18n('foss.baseinfo.reset'),
					text:baseinfo.newbirdInfo.i18n('foss.baseinfo.reset'),
					columnWidth:.1,
					handler: function(){
						var form=this.up('form').getForm();
						form.reset();
					}
				},{
					xtype : 'button', 
					width:70,
					columnWidth:.1,
					tooltip: baseinfo.newbirdInfo.i18n('foss.baseinfo.query'),
					text : baseinfo.newbirdInfo.i18n('foss.baseinfo.query'),//查询
					cls:'yellow_button',
					handler : function() {
						var form=this.up('form');
						baseinfo.newbirdInfo.newbirdInfoQuery(form);
					}
				}]			
});

//已配置(快递点部营业部映射关系)的快递点部Store的Model
Ext.define('Foss.baseinfo.newbirdInfo.NewbirdInfoModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'transport'
	},{
		name:'name'
	},{
		name:'receiver'
	},{
		name:'telphone'
	},{
		name:'phone'
	},{
		name:'operater'
	},{
		name:'operateTime',
		type:'Date',
		convert:baseinfo.newbirdInfo.longToDateConvert
	}
	/*
	 * 
	,{
		name:'active'
	}
	 */
	]
});

//已配置(快递点部营业部映射关系)的快递点部列表Store
Ext.define('Foss.baseinfo.newbirdInfo.NewbirdInfoStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.newbirdInfo.NewbirdInfoModel',
	pageSize: 20,	
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryNewbirdInfoList.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'newbirdinfoVO.newbirdinfoEntitys',
			totalProperty:'totalCount'
		}
	},
	submitParams:null,
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	getSubmitParams: function(){
		return this.submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

//已配置(快递点部营业部映射关系)的快递点部列表
Ext.define('Foss.baseinfo.newbirdInfo.NewbirdInfoGrid',{
	extend:'Ext.grid.Panel',
    title: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.newbirdInfoGridTitle'),
    frame:true,
	height:500,
	//selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.newbirdInfo.NewbirdInfoStore'),
	columns:[{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.transport'),		
		dataIndex: 'transport'
	},{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.name'),
		dataIndex: 'name'
	},{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.receiver'),
		dataIndex: 'receiver'
	},{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.telphone'),
		dataIndex: 'telphone'
	},{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.phone'),
		dataIndex: 'phone'
	},{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.operater'),
		dataIndex: 'operater'
	},{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.operateTime'),
		dataIndex: 'operateTime'
	},
	/*
	 * 
	{
		header: baseinfo.newbirdInfo.i18n('foss.baseinfo.newbirdInfo.active'),
		dataIndex: 'active',
		renderer:function(value){
			if(value=='Y'){
				return '有效';
			}else if(value=='N'){
				return '无效';
			}else{
				return '';
			}
		}
	},
	 */
	{
		header: 'ID',
		dataIndex: 'id',
		hidden:true
	}],
	viewConfig: {
		enableTextSelection: false
	},
	
	constructor:function(config){
	
		var me = this;
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:500
				})
			}]
		}];	
		me.callParent();
	}
});
//--------------------------------------------------快递点部营业部映射关系初始界面 end-------------------------

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_baseinfo-newbirdInfoIndex_content')) {
		return;
	} 
	
	//查询已配置(菜鸟异常单号查询映射关系)的FORM
	var newbirdInfoQueryForm = Ext.create('Foss.baseinfo.newbirdInfo.NewbirdInfoQueryForm');
	
	//已配置(菜鸟异常单号查询映射关系)的列表grid
	var newbirdInfoGrid = Ext.create('Foss.baseinfo.newbirdInfo.NewbirdInfoGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-newbirdInfoIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getExpressPartSalesDeptQueryForm:function(){
			return newbirdInfoQueryForm;
		},
		getNewbirdInfoGrid:function(){
			return newbirdInfoGrid;
		},
		items: [newbirdInfoQueryForm,newbirdInfoGrid],
		renderTo : 'T_baseinfo-newbirdInfoIndex-body'
	});
});