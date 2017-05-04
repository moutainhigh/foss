/**
 * 保管费的修改
 * 
 * @author:yangkang
 * Build date: 2014-05-27
 * 
 */
//定义一个model
Ext.define('Foss.sign.storageChargeUpdate.OriginatingLineModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'waybillNo',  //运单号
		type : 'string'
	}, {
		name : 'storageCharge', //保管费
		type : 'float'
	}]
});

//创建一个保管费修改的store
Ext.define('Foss.sign.storageChargeUpdate.OriginatingLineStore',{
	extend:'Ext.data.Store',
//	pageSize:10,
	//绑定model
    model: 'Foss.sign.storageChargeUpdate.OriginatingLineModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: sign.realPath('querystorageChargeUpdateInfo.action'),
		reader : {
			type : 'json',
			root : 'waybillVo.dto',
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
			var queryForm = Ext.getCmp('Foss_sign_storageChargeUpdate_QueryForm_Id').getForm();
			var infoGrid = Ext.getCmp('T_sign-storageChargeUpdate_content').getOriginatingLineGrid(); 
			if (queryForm != null) {
				var waybillNo = queryForm.findField('waybillNo').getValue();
				if(waybillNo==null || waybillNo==''){
					waybillNo =infoGrid.wayBillNo;
				}
				Ext.apply(operation, {
					params : {
						'waybillVo.dto.waybillNo':waybillNo //运单号
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('Foss.sign.storageChargeUpdate.QueryForm', {
	extend : 'Ext.form.Panel',
	title: '查询条件',
	id : 'Foss_sign_storageChargeUpdate_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[
		    {
				xtype : 'textfield',
				columnWidth : 0.3,
				name : 'waybillNo',
				fieldLabel : '运单号',
				allowBlank:false
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.reset'),//重置
			disabled:!sign.storageChargeUpdate.isPermission('storageChargeUpdate/autoResetButton'),
			hidden:!sign.storageChargeUpdate.isPermission('storageChargeUpdate/autoResetButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
				me.getForm().findField('waybillNo').setValue('');
			}
		},{
			xtype : 'button', 
			width:70,
			text : sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.search'),//查询
			disabled:!sign.storageChargeUpdate.isPermission('storageChargeUpdate/autoResetButton'),
			hidden:!sign.storageChargeUpdate.isPermission('storageChargeUpdate/autoResetButton'),
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_sign-storageChargeUpdate_content').getOriginatingLineGrid().getStore().load();
				}
			}
		}]
		me.callParent([cfg]);
	}
});
//定义一个表格列表
Ext.define('Foss.sign.storageChargeUpdate.OriginatingLineGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_sign_storageChargeUpdate_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title :sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.title'),
	collapsible : true,
	animCollapse : true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	wayBillNo:null,
	storageChargeOld:null,
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.sign.storageChargeUpdate.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
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
	// 定义表格列信息
	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.operate'),
				align : 'center',
				items : [{
                    iconCls:'deppon_icons_edit',
					tooltip :'修改',
					disabled:!sign.storageChargeUpdate.isPermission('storageChargeUpdate/expressEditButton'),
					// 编辑事件
					handler : function(grid, rowIndex,colIndex) {
						//获取选中的数据
						var record = grid.getStore().getAt(rowIndex);
						//设定中间值  方便传修改前的保管费给后台
						grid.up('grid').wayBillNo = record.get("waybillNo");
						grid.up('grid').storageChargeOld = record.get("storageCharge");
						//获得修改窗口
						var updateWindow = grid.up('grid').getUpdateWindow();
						updateWindow.resetWindow(record,'update');
						updateWindow.show();
					}
				}]
			},{
				header : '运单号',
				dataIndex : 'waybillNo',
				flex : 1
			}, {
				header : '保管费',
				dataIndex : 'storageCharge',
				width : 400
			},],
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.sign.storageChargeUpdate.OriginatingLineStore');
				me.callParent([ cfg ]);
	}
	});

// 定义一个修改的窗口
Ext.define('Foss.sign.storageChargeUpdate.UpdateWindow', {
	extend : 'Ext.window.Window',
	title : '保管费修改',//'修改'
	width : 700,
	height : 400,
	isUpdate:true,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	modal : true,
	closeAction : 'hidden',
	addUpdateForm : null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.parent.getStore().load();
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.sign.storageChargeUpdate.UpdateExchForm');
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
			this.infoGrid = Ext.getCmp('T_sign-storageChargeUpdate_content').getOriginatingLineGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.getAddUpdateForm().getForm().findField('waybillNo').setValue(record.get('waybillNo'));
		this.getAddUpdateForm().getForm().findField('storageCharge').setRawValue(record.get('storageCharge'));
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

//保管费修改信息表单
Ext.define('Foss.sign.storageChargeUpdate.UpdateExchForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 300,
	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
     type: 'table',
     columns: 1
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
				record =new Foss.sign.storageChargeUpdate.OriginatingLineModel();
			}
			
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			//保管费
			var storageCharge =basicForm.findField('storageCharge').getValue( ),
			    waybillNo =basicForm.findField('waybillNo').getValue( );
			    updateStorageChargeReason = basicForm.findField('updateStorageChargeReason').getValue( );
			var infoGrid = Ext.getCmp('T_sign-storageChargeUpdate_content').getOriginatingLineGrid();
			record.set('storageCharge',storageCharge);
			record.set('waybillNo',waybillNo);
			record.set('updateStorageChargeReason',updateStorageChargeReason);
			record.set('storageChargeOld',infoGrid.storageChargeOld);
 		   var jsonData = {'waybillVo':{dto:record.data}};
 		
			var url = null;
			if(me.isUpdate){
				url = sign.realPath('updatestorageCharge.action');//请求修改
			}
			var infoGrid = Ext.getCmp('T_sign-storageChargeUpdate_content').getOriginatingLineGrid(); 
           Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getStore().load();
					  //修改成功消息
					  infoGrid.showWarningMsg(sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.tip'),'保管费修改成功');
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.tip'),json.message);
		        }
			});
		}
	},
	items : [{
		name: 'waybillNo',
        fieldLabel: '运单号',
        allowBlank:false,
        readOnly:true,
        xtype : 'textfield'
	},{
		xtype: 'numberfield',
        name: 'storageCharge',
        minValue:0,
        maxValue:1000,
        fieldLabel: '保管费',
        allowBlank:false
	},{
		xtype: 'textarea',
        name: 'updateStorageChargeReason',
        maxLength:200,
        width:600,
        fieldLabel: '备注',
        allowBlank:false
	}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.cancel'),
				handler :function(){
					me.up().close();
				}
			}
			,{
				text :sign.storageChargeUpdate.i18n('pkp.sign.storageChargeUpdate.save'),
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
    //查询FORM
	var queryForm = Ext.create('Foss.sign.storageChargeUpdate.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.sign.storageChargeUpdate.OriginatingLineGrid');

	Ext.getCmp('T_sign-storageChargeUpdate').add(Ext.create('Ext.panel.Panel', {
		id : 'T_sign-storageChargeUpdate_content',
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