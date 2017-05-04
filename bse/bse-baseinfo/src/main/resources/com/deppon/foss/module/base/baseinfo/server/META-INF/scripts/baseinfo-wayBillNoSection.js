/**
 * 运单号段信息
 * 
 * @author:262036 HuangWei
 * Build date: 2015-06-17
 * 
 */
baseinfo.wayBillNoSection.dateFormat=function(value,format){
	if(!Ext.isEmpty(value)){
		return Ext.Date.format(new Date(value), format);
	}else{
		return null;
	}	
};

//定义一个model
Ext.define('Foss.baseinfo.wayBillNoSection.OriginatingLineModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'systemName',  //系统
		type : 'string'
	}, {
		name : 'channelSourceCode', //渠道来源
		type : 'string'
	},{
		name : 'customerCode',    //客户编码
		type : 'string'
	}, {
		name : 'wayBillNoStart',     //起始运单号
		type : 'string'
	}, {
		name : 'wayBillNoEnd',     //截止运单号
		type : 'string'
	}, {
		name : 'remark',     //备注
		type : 'string'
	},{
		name : 'createUserName',     //创建人
		type : 'string'
	},{
		name : 'createUserCode',     //创建人
		type : 'string'
	},{
		name : 'createTime',     //创建时间
		type : 'date',
		convert: dateConvert,
		defaultValue:null
	},{
		name:'applyCount' //申请数量
	}]
});

//创建一个运单号段信息的store
Ext.define('Foss.baseinfo.wayBillNoSection.OriginatingLineStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.wayBillNoSection.OriginatingLineModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryWayBillNoSections.action'),
		reader : {
			type : 'json',
			root : 'wayBillNoSectionVo.entityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_WayBillNoSection_QueryForm_Id').getForm();
			if (queryForm != null) {
				var systemName = queryForm.findField('systemName').getValue();
				var channelSourceCode = queryForm.findField('channelSourceCode').getValue();
				var wayBillNoStart = queryForm.findField('wayBillNoStart').getValue();
				var wayBillNoEnd = queryForm.findField('wayBillNoEnd').getValue();
				var startDate = queryForm.findField('startDate').getValue();
				var endDate = queryForm.findField('endDate').getValue();
				var customerCode = queryForm.findField('customerCode').getValue();
				Ext.apply(operation, {
					params : {
						'wayBillNoSectionVo.entity.systemName':systemName,
						'wayBillNoSectionVo.entity.channelSourceCode':channelSourceCode,
						'wayBillNoSectionVo.entity.customerCode':customerCode,
						'wayBillNoSectionVo.entity.wayBillNoStart':wayBillNoStart,
						'wayBillNoSectionVo.entity.wayBillNoEnd':wayBillNoEnd,
						'wayBillNoSectionVo.entity.startDate':startDate, //生效时间,
						'wayBillNoSectionVo.entity.endDate':endDate //生效时间,
					}
				});	
			}
		}
	}
});


Ext.define('Foss.baseinfo.wayBillNoSection.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.wayBillNoSection.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_WayBillNoSection_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	labelSeparator:':',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[ {
		name : 'systemName',
		margin : '8 10 5 0',
		fieldLabel : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.systemName'),//系统名称
		maxLength:200,
		listeners:{
			blur:function(field,new_v,old_v){
				field.setValue(field.value.toLocaleUpperCase());
			}
		}
	},{
		name:'channelSourceCode',
		fieldLabel:baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.channelSourceCode'), //派送属性
		xtype:'combobox',
		valueField:'valueCode',
		displayField:'valueName',
		queryMode:'local',
		triggerAction:'all',
		value:'',
		store:FossDataDictionary.getDataDictionaryStore('ORDERTYPE',null,{
			'valueCode':'',
			'valueName':baseinfo.wayBillNoSection.i18n('foss.baseinfo.all')
		})
	},{
		name : 'customerCode',
		fieldLabel : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.customerCode'),//客户编码
		maxLength:200
	},{
		name : 'wayBillNoStart',
		fieldLabel : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.wayBillNo'),//运单号
		margin : '8 0 5 0',
		maxLength:200,
		regex : /^\d{10,10}$/,
		listeners:{
			change:function(field,new_v,old_v){
				if(!Ext.isEmpty(new_v)&& new_v.length >=10){
					//先去两端的空格
					var new_value = new_v.trim();
					//再把值设置给文本框
					field.setValue(new_value.substring(0,new_value.length>=10?10:new_value.length));
				}
			}
		}
	},{
		name : 'wayBillNoEnd',//截止运单号
		fieldLabel : '—',
		labelSeparator:'',
		labelWidth : 20,
		margin : '8 10 5 0',
		maxLength:200,
		regex : /^\d{10,10}$/,
		listeners:{
			change:function(field,new_v,old_v){
				if(!Ext.isEmpty(new_v)&& new_v.length >=10){
					//先去两端的空格
					var new_value = new_v.trim();
					//再把值设置给文本框
					field.setValue(new_value.substring(0,new_value.length>=10?10:new_value.length));
				}
			}
		}
	},{
		xtype: 'rangeDateField',
		fieldLabel:baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.createTime'),  //起止时间
		dateType: 'datetimefield_date97',
		fromName: 'startDate',
		toName: 'endDate',
		dateRange : 31,
		width: 500
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.wayBillNoSection.i18n('foss.baseinfo.reset'),//重置
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.wayBillNoSection.i18n('foss.baseinfo.query'),//查询
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-wayBillNoSection_content').getOriginatingLineGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});

//详细运单号段信息表单
Ext.define('Foss.baseinfo.wayBillNoSection.DetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	height : 300,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120,
		readOnly : true
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
	items : [ {
			name: 'systemName',
	        fieldLabel: baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.systemName') //系统
		},{
			name: 'channelSourceCode',
	        fieldLabel: baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.channelSourceCode'), //渠道来源
	        listeners: {
	             change:function(_this,newValue,oldValue,eOpts){
	            	var store = FossDataDictionary.getDataDictionaryStore('ORDERTYPE');
	            	var returnValue = newValue;
	 				if(!Ext.isEmpty(store)){
	 					store.each(function(record){
	 						if(record.get('valueCode')==newValue){
	 							returnValue = record.get('valueName');
	 						}
	 					});
	 				}
	 				_this.setRawValue(returnValue);
	             }
	        }
		},{
			name: 'customerCode',
	        fieldLabel: baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.customerCode') //客户编号
		},{
			name: 'wayBillNoStart',
	        fieldLabel: baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.wayBillNoStart') //开始运单号
		},{
			name: 'wayBillNoEnd',
	        fieldLabel: baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.wayBillNoEnd') //截止运单号
		},{
			name: 'createUserName',
	        fieldLabel: baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.createUserName') //创建人
		},{
			xtype:'datefield',
			name: 'createTime',
			colspan:2,
	        fieldLabel: baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.createTime'), //创建时间
	        format:'Y-m-d H:i:s'
		},{
			fieldLabel:baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.remark'),//备注
			name:'remark',
			labelWidth: 70,
			xtype:'textareafield',
			width: 630,
			height:100,
			colspan : 5,
			maxLength:200,
			colspan:3
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

//查看记录的详细信息
Ext.define('Foss.baseinfo.wayBillNoSection.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.wayBillNoSection.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取运单号段信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.wayBillNoSection.DetailForm');
		}
		return this.infoForm;
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [ this.getInfoForm()];
		this.callParent(arguments);
	},
	bindData : function(record) {
		var me = this;
		// 绑定表格数据到表单上
		me.getInfoForm().getForm().loadRecord(record);
	}
});

baseinfo.showWarningMsg = function(title,message,fun){
	Ext.Msg.show({
	    title:title,
	    msg:message,
	    width:120,
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.WARNING,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
}

// 新增/修改汇率表单
Ext.define('Foss.baseinfo.wayBillNoSection.AddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height: 300,
	collapsible: true,
	defaults : {
		margin : '5 5 5 0',
		labelWidth : 75
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 3
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
    //提交表单
    commitInfo : function(){
    	var me = this;
    	//获取表单
    	var basicForm = this.getForm();
    	if(basicForm.isValid()){//校验form是否通过校验
    	
    		//获取model实例
			var record = null;
			
			if(me.isUpdate){
				record = basicForm.getRecord();//修改
			}else{
				record = Ext.create('Foss.baseinfo.wayBillNoSection.OriginatingLineModel');
			}
			
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			
			if(record.data.systemName == '' && record.data.channelSourceCode == '' && record.data.customerCode == ''){
				baseinfo.showWarningMsg("FOSS提醒您：","系统名称、渠道来源、客户编号必填其一");
				return;
			}
			if(record.data.applyCount % 100000 != 0){
				baseinfo.showWarningMsg("FOSS提醒您：","申请的数量必须为10万或10万的整数倍，最多为100万");
				return;
			}
			
    		var jsonData = {'wayBillNoSectionVo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				//url = baseinfo.realPath('updateExchangeRate.action');//修改
			}else{
				url = baseinfo.realPath('addWayBillNoSection.action');//新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-wayBillNoSection_content').getOriginatingLineGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.wayBillNoSection.i18n('foss.baseinfo.notice'),json.message);
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.wayBillNoSection.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [{
			fieldLabel:baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.systemName'),//系统
			name:'systemName',
			labelWidth: 70,
			allowBlank: true,
			maxLength:20,
			xtype : 'textfield',
			listeners:{
				blur:function(field,new_v,old_v){
					if(!Ext.isEmpty(field.value)){
						var new_value = field.value.trim();
						field.setValue(new_value.toLocaleUpperCase());
					}
				}
			}
		},
			FossDataDictionary.getDataDictionaryCombo('ORDERTYPE',{
			fieldLabel : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.channelSourceCode'),//渠道来源
			forceSelection:true,
			labelWidth: 70,
			name : 'channelSourceCode'
		}),{
			fieldLabel:baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.customerCode'),//客户编码
			name:'customerCode',
			allowBlank: true,
			labelWidth: 70,
			maxLength:20,
			xtype : 'textfield',
			listeners:{
				change:function(field,new_v,old_v){
					if(!Ext.isEmpty(new_v)){
						//先去两端的空格
						var new_value = new_v.trim();
						//再把值设置给文本框
						field.setValue(new_value);
					}
				}
			}
		},{
			fieldLabel:baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.applyCount'),//申请数量
			name:'applyCount',
			labelWidth: 70,
			allowBlank: false,
			maxValue: 1000000,
			minValue: 100000,
			step: 100000,
	        maxText:'输入最大值为1000000',
	        minText:'输入最小值为100000',
			xtype : 'numberfield',
			allowDecimals:false
		},{
			xtype: 'displayfield',
	        value: '(申请的数量必须为10万或10万的整数倍，最多为100万)',//请先选择类型
	        colspan:2
		},{
			fieldLabel:baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.remark'),//备注
			name:'remark',
			labelWidth: 70,
			allowBlank: true,
			xtype:'textareafield',
			width: 630,
			height:100,
			colspan : 5,
			maxLength:200,
			colspan:3
		}
	],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.wayBillNoSection.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.wayBillNoSection.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.wayBillNoSection.OriginatingLineModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.wayBillNoSection.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

// 定义一个新增的窗口
Ext.define('Foss.baseinfo.wayBillNoSection.AddWindow', {
	extend : 'Ext.window.Window',
	title : '新增运单号段信息',//'新增汇率'
	width : 700,
	height : 400,
	isUpdate:false,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	standardList : null,//发车标准信息列表
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){//窗口显示之前事件
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.wayBillNoSection.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-wayBillNoSection_content').getOriginatingLineGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm()];
		me.callParent([ cfg ]);
	}
});

//定义一个表格列表
Ext.define('Foss.baseinfo.wayBillNoSection.OriginatingLineGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_wayBillNoSection_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title : baseinfo.wayBillNoSection.i18n('foss.baseinfo.queryGrid'),
	collapsible : true,
	animCollapse : true,
	store : null,
	// 表格行可展开的插件
	plugins : [ {
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.baseinfo.wayBillNoSection.InfoPanel'
	} ],
    
    addWindow : null,
	// 定义一个获取新增窗口的函数
	getAddWindow : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.wayBillNoSection.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
	},
	//分页组件
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					pageSize:10,
					prependButtons: true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
       return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.systemName'),//系统
				// 关联model中的字段名
				dataIndex : 'systemName',
				width : 100
			}, {
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.channelSourceCode'),//渠道
				// 关联model中的字段名
				dataIndex : 'channelSourceCode',
				width : 110,
				renderer:function(value){
					var store = FossDataDictionary.getDataDictionaryStore('ORDERTYPE');
					var returnValue = value;
					if(!Ext.isEmpty(store)){
						store.each(function(record){
							if(record.get('valueCode')==value){
								returnValue = record.get('valueName');
							}
						});
					}
					return returnValue;
				}
			}, {
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.customerCode'),//客户编码
				// 关联model中的字段名
				dataIndex : 'customerCode',
				width : 100
			}, {
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.wayBillNoStart'),//起始号段
				// 关联model中的字段名
				dataIndex : 'wayBillNoStart',
				width : 95
			},{
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.wayBillNoEnd'),//截止号段
				// 关联model中的字段名
				dataIndex : 'wayBillNoEnd',
				width : 95
			},{
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.remark'),//备注
				// 关联model中的字段名
				dataIndex : 'remark',
				width : 180
			},{
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.createUserName'),//创建人
				// 关联model中的字段名
				dataIndex : 'createUserName',
				width : 100,
				renderer : function(v, meta, record){ 
						if(record.get("createUserCode") != null && record.get("createUserCode") != ''){
							return record.get("createUserName") + '(' + record.get("createUserCode") + ')'; 
						}else{
							return record.get("createUserName");
						}
				} 
			},{
				// 字段标题
				header : baseinfo.wayBillNoSection.i18n('foss.baseinfo.wayBillNoSection.createTime'),//创建时间
				dataIndex : 'createTime',
				width : 140,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
		    	}
				return v;
            }
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.wayBillNoSection.OriginatingLineStore');
				me.bbar = me.getPagingToolbar();
				me.getPagingToolbar().store = me.store;
				me.dockedItems = [{
					xtype : 'toolbar',
					dock : 'top',
					layout : 'column',
					defaults : {
						margin : '0 0 5 3'
					},
					items : [{
						xtype : 'button',
						text : baseinfo.wayBillNoSection.i18n('foss.baseinfo.add'),
						width : 80,
						disabled:!baseinfo.wayBillNoSection.isPermission('WayBillNoSectionIndex/AddWayBillNoSectionButton'),
						hidden:!baseinfo.wayBillNoSection.isPermission('WayBillNoSectionIndex/AddWayBillNoSectionButton'),
						handler : function() {// 作废多项选中的记录
                            this.addWindow = me.getAddWindow();
				            this.addWindow.show();
						}
					}
					]
		}], 
		me.callParent([ cfg ]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
    //查询FORM
	var queryForm = Ext.create('Foss.baseinfo.wayBillNoSection.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.wayBillNoSection.OriginatingLineGrid');

	Ext.getCmp('T_baseinfo-wayBillNoSection').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-wayBillNoSection_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getOriginatingLineGrid : function(){
			return queryResult;
		},
		items : [ queryForm, queryResult]
	}));
});