/**
 * 外场所属运输财务公司信息
 * 
 * @author:申威华
 * Build date: 2013-11-27
 * 
 */
//外场所属运输财务公司信息MODEL
Ext.define('Foss.baseinfo.leasedContractInfoChange.leasedContractInfoChangeEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id', //ID
        type : 'string'
    },{
        name : 'createDate', //创建时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'modifyDate', //修改时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'createUser',// 创建人
        type : 'string'
    },{
        name : 'modifyUser',// 修改人
        type : 'string'
    },{
        name : 'outfieldCode',//外场编码
        type : 'string'
    },{
        name : 'outfieldName',// 外场名称
        type : 'string'
    },{
        name : 'companyCode',// 外场所属运输财务公司编码
        type : 'string'
    },{
        name : 'active',// 是否有效
        type : 'string'
    },{
        name : 'notes',// 备注
        type : 'string'
    },{
        name : 'companyName',// 外场所属运输财务公司名称
        type : 'string'
    }]
});

//创建一个外场所属运输财务公司信息的store
Ext.define('Foss.baseinfo.leasedContractInfoChange.leasedContractInfoChangeStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.leasedContractInfoChange.leasedContractInfoChangeEntity',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryOutfieldTFCompanys.action'),
		reader : {
			type : 'json',
			root : 'outfieldTFCompanyVo.entityList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedContractInfoChange_QueryForm_Id').getForm();
			if (queryForm != null) {
				var outfieldCode = queryForm.findField('outfieldCode').getValue();
				var companyCode = queryForm.findField('companyCode').getValue();
				Ext.apply(operation, {
					params : {
						'outfieldTFCompanyVo.entity.outfieldCode':outfieldCode,
						'outfieldTFCompanyVo.entity.companyCode':companyCode
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.leasedContractInfoChange.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_leasedContractInfoChange_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[{
		xtype : 'commontransfercenterselector',
		valueField : 'orgCode',
		showContent : '{name}&nbsp;&nbsp;&nbsp;{orgCode}',// 显示表格列
		name: 'outfieldCode',//外场名称
		fieldLabel : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.stowageSector'), //配载部门/外场
	},{
        xtype : 'commonfinancialOrgSelector',
        name:'companyCode',
        fieldLabel: baseinfo.leasedContractInfoChange.i18n('foss.bse.baseinfo.leasedContractInfoChange.contractBody'),//合同主体/所属运输公司
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.reset'),//重置
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.query'),//查询
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-leasedContractInfoChange_content').getOriginatingLineGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});

// 新增/修改外场所属运输财务公司信息表单
Ext.define('Foss.baseinfo.leasedContractInfoChange.AddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 240,
	collapsible: true,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 80,
		colspan : 1
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout:'column',
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
				record = Ext.create('Foss.baseinfo.leasedContractInfoChange.leasedContractInfoChangeEntity');
			}
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			
    		var jsonData = {'outfieldTFCompanyVo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateOutfieldTFCompany.action');//请求外场所属运输财务公司信息修改
			}else{
				url = baseinfo.realPath('addOutfieldTFCompany.action');//请求外场所属运输财务公司信息新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-leasedContractInfoChange_content').getOriginatingLineGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.notice'),json.message);
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [{
			xtype : 'commontransfercenterselector',
			allowBlank:false,
			isEnterQuery : false,
			showContent : '{name}&nbsp;&nbsp;&nbsp;{orgCode}',// 显示表格列
			name: 'outfieldName',//配载部门 
			valueField : 'orgCode',
			fieldLabel : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.stowageSector'),
	        listeners:{
	        	blur : function(field, event, eOpts) {
	        		this.up('form').getForm().findField('outfieldCode').setValue(field.value);
	        		this.up('form').getForm().findField('outfieldName').setValue(field.rawValue);
	        	}
	        }
		},{
	        xtype : 'commonfinancialOrgSelector',
	        name:'companyName',
	        isEnterQuery : false,
	        allowBlank:false,
	        fieldLabel: baseinfo.leasedContractInfoChange.i18n('foss.bse.baseinfo.leasedContractInfoChange.contractBody'),//合同主体
	        listeners:{
	        	blur : function(field, event, eOpts) {
	        		this.up('form').getForm().findField('companyCode').setValue(field.value);
	        		this.up('form').getForm().findField('companyName').setValue(field.rawValue);
	        	}
	        }
		},{
			name: 'outfieldCode',//外场名称
	        fieldLabel: baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.airagencycompany.remark'),
	        colspan : 2,
	        hidden:true
		},{
			name: 'companyCode',//外场所属运输财务公司名称
	        fieldLabel: baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.airagencycompany.remark'),
	        colspan : 2,
	        hidden:true
		},{
			name: 'notes',//备注
	        fieldLabel: baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.airagencycompany.remark'),
	        colspan : 2,
	        maxLength:200,
	        width:400,
	        xtype : 'textareafield'
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.leasedContractInfoChange.leasedContractInfoChangeEntity(me.up('window').bindData.data));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

// 定义一个新增的窗口
Ext.define('Foss.baseinfo.leasedContractInfoChange.AddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedContractInfoChange.i18n('foss.bse.baseinfo.leasedContractInfoChange.addSectorContractBody'),//'新增外场所属运输财务公司信息'
	width : 700,
	height : 380,
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
		beforeshow:function(me){
			me.getAddUpdateForm().getForm().findField('outfieldName').setReadOnly(false);
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.leasedContractInfoChange.AddUpdateForm');
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
			this.infoGrid = Ext.getCmp('T_baseinfo-leasedContractInfoChange_content').getOriginatingLineGrid();
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

// 定义一个修改的窗口
Ext.define('Foss.baseinfo.leasedContractInfoChange.UpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedContractInfoChange.i18n('foss.bse.baseinfo.leasedContractInfoChange.updateSectorContractBody'),//修改合同主体变更
	width : 700,
	height : 380,
	isUpdate:true,//是否为修改，默认false
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
		beforeshow:function(me){
			me.getAddUpdateForm().getForm().findField('outfieldName').setReadOnly(true);
		}
	
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.leasedContractInfoChange.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	startStandardGrid : null,
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-leasedContractInfoChange_content').getOriginatingLineGrid();
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



// 定义一个表格列表
Ext.define('Foss.baseinfo.leasedContractInfoChange.OriginatingLineGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_leasedContractInfoChange_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title : baseinfo.leasedContractInfoChange.i18n('foss.bse.baseinfo.leasedContractInfoChange.stowageSectorContractBody'),
	collapsible : true,
	animCollapse : true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
    addWindow : null,
	// 定义一个获取新增窗口的函数
	getAddWindow : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.leasedContractInfoChange.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.baseinfo.leasedContractInfoChange.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.notice'),baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		
		Ext.Msg.confirm(baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.notice'),baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放虚拟编码的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				
				var url = baseinfo.realPath('deleteOutfieldTFCompany.action');
				var jsonData = {'outfieldTFCompanyVo':{'idList':ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.notice'),json.message);}
            
		});
		
	},
	//Ajax请求
	requestAjax : function(url,jsonData,successFn,failFn)
	{
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			success:function(response){
				var result = Ext.decode(response.responseText);
				if(result.success){
					successFn(result);
				}else{
					failFn(result);
				}
			},
			failure:function(response){
				var result = Ext.decode(response.responseText);
				failFn(result);
			}
		});
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
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
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
				xtype : 'actioncolumn',
				width : 80,
				text : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
                            iconCls:'deppon_icons_edit',
							tooltip : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.edit'),
							// 编辑事件
							handler : function(grid, rowIndex,colIndex) {
								//获取选中的数据
								var record = grid.getStore().getAt(rowIndex);
								//获得修改窗口
								var updateWindow = grid.up('grid').getUpdateWindow();
								updateWindow.resetWindow(record,'update');
								updateWindow.show();
							}
						},
						{
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.void'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.notice'),
									msg:baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.deleteWarnMsg'),
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											var ids = new Array();//虚拟编码数组
											ids.push(record.data.id);
											var url = baseinfo.realPath('deleteOutfieldTFCompany.action');
											var jsonData = {'outfieldTFCompanyVo':{'idList':ids}};
											//调用Ajax请求
											grid.up('grid').ajaxRequest(url,jsonData);
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.stowageSector'),//配载部门
				// 关联model中的字段名
				dataIndex : 'outfieldName',
				flex : 3,
			}, {
				// 字段标题
				header : baseinfo.leasedContractInfoChange.i18n('foss.bse.baseinfo.leasedContractInfoChange.contractBody'),//合同主体
				// 关联model中的字段名
				dataIndex : 'companyName',
				flex : 5,
			}, {
				// 字段标题
				header : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.airagencycompany.remark'),//备注
				// 关联model中的字段名
				dataIndex : 'notes',
				xtype : 'ellipsiscolumn',
				flex : 2,
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.leasedContractInfoChange.leasedContractInfoChangeStore');
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
						text : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.add'),
						width : 80,
						handler : function() {// 作废多项选中的记录
                            this.addWindow = me.getAddWindow();
				            this.addWindow.show();
						}
					},{
						xtype : 'button',
						text : baseinfo.leasedContractInfoChange.i18n('foss.baseinfo.void'),
						width : 80,
						handler : function() {// 作废多项选中的记录
						    //调用删除函数
							me.deleteInfos();
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
	var queryForm = Ext.create('Foss.baseinfo.leasedContractInfoChange.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.leasedContractInfoChange.OriginatingLineGrid');

	Ext.getCmp('T_baseinfo-leasedContractInfoChange').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedContractInfoChange_content',
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