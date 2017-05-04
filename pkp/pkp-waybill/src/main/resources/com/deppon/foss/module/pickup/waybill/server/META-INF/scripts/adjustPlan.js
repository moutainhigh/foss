/**	
 *author : 105089-foss-yangtong	
 *page : 调整运单执行计划(手工) 	
*/	
waybill.waybill.rfcTypeStore = null;
waybill.waybill.WAYBILL_RFC_TYPE_CUSTOMER = "WAYBILL_RFC_TYPE_CUSTOMER";
(function(){
	waybill.waybill.rfcTypeStore = FossDataDictionary.getDataDictionaryStore(waybill.waybill.WAYBILL_RFC_TYPE_CUSTOMER, null, [{	
		'valueCode': 'INSIDE_CHANGE',	
        'valueName': '内部更改'
		}, {	
		'valueCode': '',	
        'valueName': waybill.waybill.i18n('pkp.waybill.waybill.complete') // '全部'	
		}
	]);
	waybill.waybill.rfcTypeStore.removeAt(waybill.waybill.rfcTypeStore.find('valueCode','INVALID'));//中止
})();
waybill.waybill.getOperateTimeStart = function(date, day) {	
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
	
waybill.waybill.getOperateTimeEnd = function(date, day) {	
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
// 查询条件----第一层 查询表单	
Ext.define('Foss.waybill.QueryFormPanel',{	
	extend: 'Ext.form.Panel',	
	id:'Foss_waybill_QueryFormPanel_Id',	
	title: waybill.waybill.i18n('pkp.waybill.waybill.query.condition'),	 // 查询条件
    defaultType : 'textfield',	
	collapsible: true,	
	layout: 'column',	
	cls:'autoHeight',	
	bodyCls:'autoHeight',	
	frame:true,	
	defaults: {	
		margin:'5 10 5 10',	
		anchor: '90%',	
		labelWidth:90	
	},	
	items: [{	
		fieldLabel: waybill.waybill.i18n('pkp.waybill.waybill.waybill.no'),	 // 运单号
		name: 'waybillNo',	
		columnWidth:.24	,	
		vtype : 'waybill'	
	},/*{	
		name: 'serialNo',	
		fieldLabel: waybill.waybill.i18n('pkp.waybill.waybill.serial.no'),	 // 标签编号
		columnWidth:.24	
	},*/{	
		name: 'receiveOrgCode',	
		fieldLabel: waybill.waybill.i18n('pkp.waybill.waybill.receove.org'),	 // 出发部门
		xtype:'commonsaledepartmentselector',				//营业部	
		columnWidth:.24	
	},{	
		name: 'customerPickupOrgCode',	
		fieldLabel: waybill.waybill.i18n('pkp.waybill.waybill.old.arrival.org'),	 // 原到达部门
		xtype:'commonsaledepartmentselector',				//营业部	
		columnWidth:.24	
	},{	
		xtype:'combobox',	
		name: 'rfcType',	
		queryMode:'local',	
		triggerAction:'all',	
		displayField : 'valueName',	
		valueField : 'valueCode',	
		editable:true,	
		value : '',	
		fieldLabel: waybill.waybill.i18n('pkp.waybill.waybill.change.type'),	 // 更改类型
		columnWidth:.24,	
		store : waybill.waybill.rfcTypeStore 
	},{	
		xtype: 'rangeDateField',	
		fieldLabel: waybill.waybill.i18n('pkp.waybill.waybill.change.accept'),	 // 变更受理时间
		dateType: 'datetimefield_date97',	
		fieldId : 'Foss_waybill_operateTime_Id',	
		fromName: 'operateTimeStart',	
		toName: 'operateTimeEnd',	
		disallowBlank: true,	
		editable:false,	
		fromValue: Ext.Date.format(waybill.waybill.getOperateTimeStart(new Date(),-1),'Y-m-d H:i:s'),	
		toValue: Ext.Date.format(waybill.waybill.getOperateTimeEnd(new Date(),0),'Y-m-d H:i:s'),	
		columnWidth: .48	
	},{	
		border: 1,	
		xtype:'container',	
		columnWidth:1,	
		defaultType:'button',	
		layout:'column',	
		items:[{	
			text:waybill.waybill.i18n('pkp.waybill.waybill.reset'),	 // 重置
			columnWidth:.08,	
			handler:function(){	
				var myForm = this.up('form').getForm();	
				myForm.reset();	
				myForm.findField('operateTimeStart').setValue(Ext.Date.format(waybill.waybill.getOperateTimeStart(new Date(),-1),'Y-m-d H:i:s'));	
				myForm.findField('operateTimeEnd').setValue(Ext.Date.format(waybill.waybill.getOperateTimeEnd(new Date(),0),'Y-m-d H:i:s'));	
			}	
		},{	
			xtype: 'container',	
			border : false,	
			columnWidth:.84,	
			html: '&nbsp;'	
		},{	
			text:waybill.waybill.i18n('pkp.waybill.waybill.query'),	 // 查询
			disabled:!waybill.waybill.isPermission('adjustplanbyhandlerindex/adjustplanbyhandlerindexquerybutton'),
			hidden:!waybill.waybill.isPermission('adjustplanbyhandlerindex/adjustplanbyhandlerindexquerybutton'),
			cls:'yellow_button',	
			columnWidth:.08,	
			handler:function(){	
					var grid = Ext.getCmp('T_waybill-adjustPlanByHandlerIndex_content').getResultGridPanel();	
					grid.getPagingToolbar().moveFirst();	
			}	
		}]	
	}]	
});	
	
/**********************************/	
//第二层待处理信息	
Ext.define('Foss.waybill.ChangeInfoModel', {	
	extend : 'Ext.data.Model',	
	fields : [	
		{name : 'rfcItemsName',type : 'string'},//变更项	
		{name : 'beforeRfcInfo',type : 'string'},//变更前信息	
		{name : 'afterRfcInfo',type : 'string'}//变更后信息	
	]	
});	
	
//创建一个表格多行可展开的表格(展开的内容以组件方式)	
//定义在表格行中的组件对象	
Ext.define('Foss.waybill.ChangeInfoGrid', {	
	extend: 'Ext.grid.Panel',	
	title: waybill.waybill.i18n('pkp.waybill.waybill.change.info'),	 // 变更信息
	frame: false,	
	store : null,	
	stripeRows : true,	
	collapsible : true,	
	viewConfig: {	
		// 单元格可复制	
        enableTextSelection: true	
    },	
    selType : 'rowmodel',	
	/*width:700,	
	height: 106,*/	
	columns: [	
			{ header: waybill.waybill.i18n('pkp.waybill.waybill.rfc.items.name'), dataIndex: 'rfcItemsName',   	align: 'center', flex: 1 },	 // 变更项
			{ header: waybill.waybill.i18n('pkp.waybill.waybill.before.rfc.info'), dataIndex: 'beforeRfcInfo',  align: 'center', flex: 1 },	 // 变更前信息
			{ header: waybill.waybill.i18n('pkp.waybill.waybill.change.rfc.info'), dataIndex: 'afterRfcInfo',   align: 'center', flex: 1 }	 // 变更后信息
	    ],	
	constructor: function(config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.store = Ext.create('Ext.data.Store', {	
			model: 'Foss.waybill.ChangeInfoModel',	
			data: []	
		});	
		me.callParent([cfg]);	
	}	
});	
/***************************************/	
//第三层待处理信息	
Ext.define('Foss.waybill.ChangeNodeModel', {	
	extend : 'Ext.data.Model',	
	fields : [	
		{name : 'serialNo',type : 'string'},//标签编号	
		{name : 'origOrgCode',type : 'string'},//库存部门	
		{name : 'objectiveOrgCode',type : 'string'},// 下一库存部门	
		{name : 'exeNode',type : 'string'},// 执行节点code	
		{name : 'exeNodeName',type : 'string'},// 执行节点名称	
		{name : 'ofreightRoute',type : 'string'}, // 原走货路径	
		{name : 'dfreightRoute',type : 'string'},// 新走货路径	
		{name : 'actuatingNode',type : 'string'},// 执行节点可选范围	
		{name : 'id',type : 'string'}, //主键	
		{name : 'waybillRfcId',type : 'string'}, //更改单主键	
		{name : 'waybillNo',type : 'string'}, //运单号 	
		{name : 'timeoutHandleMinute',type : 'string'}, //超时处理（分钟）
		{name : 'timeoutRemindTotalNum',type : 'string'} //超时提醒（次数）
		]	
});	

//下拉框	
Ext.define('Foss.waybill.NodeListModel', {	
	extend: 'Ext.data.Model',  	
	fields: [        	
		{name: 'code', type: 'string'},   	
		{name: 'name', type: 'string'}	 	
	    ]}); 	
var teststore = Ext.create('Ext.data.Store', {	
	extend: 'Ext.data.Store',		
	//绑定一个模型	
	model: 'Foss.waybill.NodeListModel',	
	data: []	
	//autoLoad : true,	
	/*proxy : {	
		type : 'ajax',	
		url: waybill.realPath('queryExeNodeBySelect.action'),	
		//actionMethods : 'post',	
		reader : {	
			type: 'json',	
	        root: 'vo.orgList'	
		}	
	}*/	
});	
	
Ext.define('Foss.waybill.ChangeNodeGrid', {	
	extend: 'Ext.grid.Panel',	
    title: waybill.waybill.i18n('pkp.waybill.waybill.change.note'),	 // 变更节点
    store : null,	
    frame: false,	
    viewConfig: {	
		// 单元格可复制	
        enableTextSelection: true	
    },	
    columns: [	
        { header: waybill.waybill.i18n('pkp.waybill.waybill.serial.no'),  dataIndex: 'serialNo', align: 'center', width : 62 },	 // 标签编号
        { header: waybill.waybill.i18n('pkp.waybill.waybill.stock.org'), dataIndex: 'origOrgCode', align: 'center', width : 76,xtype: 'linebreakcolumn' },	 // 库存部门
        { header: waybill.waybill.i18n('pkp.waybill.waybill.next.stock.org'), dataIndex: 'objectiveOrgCode',align: 'center', width : 86,xtype: 'linebreakcolumn' },	 // 下一库存部门
        { header: waybill.waybill.i18n('pkp.waybill.waybill.execute.node'), dataIndex: 'exeNodeName',align: 'center', width : 76,xtype: 'linebreakcolumn' },	 // 执行节点
        { header: waybill.waybill.i18n('pkp.waybill.waybill.timeout.handle.minute'), dataIndex: 'timeoutHandleMinute',align: 'center', width : 94 },	 // 超时处理（分钟）
        { header: waybill.waybill.i18n('pkp.waybill.waybill.timeout.remind.total.num'), dataIndex: 'timeoutRemindTotalNum',align: 'center', width : 94 },	 // 超时提醒（次数）
        { header: waybill.waybill.i18n('pkp.waybill.waybill.old.freight.route'), xtype: 'linebreakcolumn', dataIndex: 'ofreightRoute',align: 'center', flex: 1 },	 // 原走货路径
        { header: waybill.waybill.i18n('pkp.waybill.waybill.new.freight.route'), xtype: 'linebreakcolumn', dataIndex: 'dfreightRoute',align: 'center', flex: 1 }	 // 新走货路径
    ],	
    listeners : {	
		//----运单号的点击事件	
		itemclick:  function(dv, record, item, index, e){	
			teststore.removeAll();	
			var bbr = this.dockedItems.items[2];	
			bbr.down('combobox').reset();	
		}	
	},	
    dockedItems: [{	
    	xtype : 'toolbar',	
		dock : 'bottom',	
		items : [{	
			xtype:'combobox',	
			name: 'actuatingNode',	
			queryMode:'local',	
			displayField : 'name',	
			valueField : 'code',	
			editable:false,	
			fieldLabel: waybill.waybill.i18n('pkp.waybill.waybill.execute.node'),	 // 执行节点
		    store: teststore,	
		    triggerAction : 'all',	
		    listeners: {	
		    	focus : {	
					fn : function() {	
						teststore.removeAll();	
						var mygrid = this.up('gridpanel');	
		 				 //var combo = this;	
		 				 var sms = mygrid.getSelectionModel().getSelection();	
		 				//simpleCombos =simpleCombo.getValue(); 	
		 				if (sms.length == 0) {	
		 					Ext.ux.Toast.msg(waybill.waybill.i18n('pkp.waybill.waybill.tip'),waybill.waybill.i18n('pkp.waybill.waybill.must.select.result'),'error', 3000);	
		 				} else {	
		 					var ids='';	
		 					var actuatingNodes='';	
							for(var i = 0 ;i<sms.length ;i++){	
								if(ids.length == 0) {	
									ids = sms[i].data.id;	
									actuatingNodes = sms[i].data.actuatingNode;	
								} else {	
									ids = ids + "," + sms[i].data.id;	
									actuatingNodes = actuatingNodes + ":" + sms[i].data.actuatingNode;	
								}		
							}	
							Ext.Ajax.request({	
							    url: waybill.realPath('queryExeNodeBySelect.action'),	
							    params: {	
							    	'vo.changeNodeDto.id': ids,	
							    	'vo.changeNodeDto.actuatingNode': actuatingNodes	
							    },	
							    success: function(response){	
							    	var json = Ext.decode(response.responseText);	
							    	teststore.loadData(json.vo.orgList);	
							    },	
							    exception: function(response){	
									var json = Ext.decode(response.responseText);	
			              			Ext.ux.Toast.msg(waybill.waybill.i18n('pkp.waybill.waybill.tip'), json.message, 'error', 3000);	
								}	
						});	
					}	
				 }	
		    	 }	
		   		}	
			},    	
            { xtype: 'button',	
        	  text:waybill.waybill.i18n('pkp.waybill.waybill.submit'),	 // 提交
        	  disabled:!waybill.waybill.isPermission('adjustplanbyhandlerindex/adjustplanbyhandlerindexsubmitbutton'),
        	  hidden:!waybill.waybill.isPermission('adjustplanbyhandlerindex/adjustplanbyhandlerindexsubmitbutton'),
        	  plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {	
				seconds: 3	
			  }),	
 			  columnWidth:.08,	
 			  handler:function(){	
 				 var bbar  = this.up('toolbar'),combo = bbar.down('combobox'),mygrid = this.up('gridpanel');	
 				 var grid = Ext.getCmp('T_waybill-adjustPlanByHandlerIndex_content').getResultGridPanel()	
 				 var record = grid.getSelectionModel().getSelection()	
 				 var sms = mygrid.getSelectionModel().getSelection();	
 				 if (sms.length == 0) {	
 					Ext.ux.Toast.msg(waybill.waybill.i18n('pkp.waybill.waybill.tip'),waybill.waybill.i18n('pkp.waybill.waybill.must.select.result'),'error', 3000);	
 					return;	
 				 }	
 				 if (Ext.isEmpty(combo.value)) {	
 					Ext.ux.Toast.msg(waybill.waybill.i18n('pkp.waybill.waybill.tip'),waybill.waybill.i18n('pkp.waybill.waybill.must.select.execute.note'),'error', 3000);	
 					return;	
 				 }
 				 var array = new Array(), changeNodeDto = '';
 				 for(var i = 0 ;i<sms.length ;i++){	
					changeNodeDto = {'id' : sms[i].data.id, 'serialNo' : sms[i].data.serialNo, 'waybillNo' : sms[i].data.waybillNo,'exeNode' : combo.value };
					array.push(changeNodeDto);
				 }	
 				 var newVo = {
					'vo': {
						'changeNodeDtoList' : array
					}
				}
//				var ids='',waybillRfcId ='',waybillNo ='',serialNos = '';	
//				for(var i = 0 ;i<sms.length ;i++){	
//					if(ids.length == 0) {	
//						ids = sms[i].data.id;	
//						serialNos = sms[i].data.serialNo;	
//						waybillNo = sms[i].data.waybillNo;	
//					} else {	
//						ids = ids + "," + sms[i].data.id;	
//						serialNos = serialNos + "," + sms[i].data.serialNo;	
//						waybillRfcId = sms[i].data.waybillRfcId;	
//						waybillNo = sms[i].data.waybillNo;	
//					}		
//				}	
				Ext.Ajax.request({	
				    url: waybill.realPath('updateExeNodeBySelect.action'),	
//				    params: {	
//				    	'vo.changeNodeDto.exeNode': combo.value,	
//				    	'vo.changeNodeDto.id': ids,
//				    	'vo.changeNodeDto.serialNo': serialNos,
//				    	'vo.changeNodeDto.waybillNo': waybillNo
//				    },	
				    jsonData: newVo,
				    success: function(response){	
				    	Ext.Ajax.request({	
						    url:waybill.realPath('queryWaybillRfcByWaybillRfcId.action'),	
						    params: {	
						    	'vo.adjustPlanResultDto.waybillRfcId': record[0].data.waybillRfcId,	
						    	'vo.adjustPlanResultDto.waybillNo': record[0].data.waybillNo,	
						    	'vo.adjustPlanResultDto.ocustomerPickupOrgCode': record[0].data.ocustomerPickupOrgCode,	
						    	'vo.adjustPlanResultDto.dcustomerPickupOrgCode': record[0].data.dcustomerPickupOrgCode	
						    },	
						    success: function(response1){	
						    	var result = Ext.decode(response1.responseText);	
						    	mygrid.getStore().loadData(result.vo.changeNodeDtoList);	
						    }	
						});	
						teststore.removeAll();	
						combo.reset();	
				    	Ext.ux.Toast.msg(waybill.waybill.i18n('pkp.waybill.waybill.tip'),waybill.waybill.i18n('pkp.waybill.waybill.update.note.success'),'ok',2000); 	
				    },	
				    exception: function(response){	
						var json = Ext.decode(response.responseText);	
              			Ext.ux.Toast.msg(waybill.waybill.i18n('pkp.waybill.waybill.tip'), json.message, 'error', 3000);	
					}	
				});	
 			  }	
            },	
            { xtype: 'button',	
    			text:waybill.waybill.i18n('pkp.waybill.waybill.cancel'),	 // 取消
    			columnWidth:.08,	
    			handler:function(){	
    				var mygrid = this.up('gridpanel');	
    				mygrid.selModel.deselectAll();	
    			}	
    		}	
    ]}],	
	constructor: function(config){	
		var me = this,	
			cfg = Ext.apply({}, config);	
		me.selModel = Ext.create('Ext.selection.CheckboxModel');	
		me.store = Ext.create('Ext.data.Store', {	
			model: 'Foss.waybill.ChangeNodeModel',	
			data: []	
		});	
		me.callParent([cfg]);	
	}	
});	
	
/**********************************/	
//顶层面板	
Ext.define('FOSS.waybill.underPanel',{	
		extend:'Ext.container.Container',	
		changeInfoGrid : null,	
		changeNodeGrid : null,	
		constructor : function(config) {	
			var me = this;	
				Ext.apply(this, config);	
			this._initCompoment();	
			this.items = [this.changeInfoGrid, this.changeNodeGrid];	
			me.callParent(arguments);	
		},	
		_initCompoment : function() {	
			this.getChangeInfoGrid();	
			this.getChangeNodeGrid();	
		},	
		getChangeInfoGrid : function() {	
			if (!this.changeInfoGrid) {	
				this.changeInfoGrid = Ext.create('Foss.waybill.ChangeInfoGrid');	
			}	
			return this.changeInfoGrid;	
		},	
		getChangeNodeGrid : function() {	
			if (!this.changeNodeGrid) {	
				this.changeNodeGrid = Ext.create('Foss.waybill.ChangeNodeGrid');	
			}	
			return this.changeNodeGrid;	
		},	
		bindData : function(record, grid, rowBodyElement) {	
			Ext.Ajax.request({	
			    url:waybill.realPath('queryWaybillRfcByWaybillRfcId.action'),	
			    params: {	
			    	'vo.adjustPlanResultDto.waybillRfcId': record.get('waybillRfcId'),	
			    	'vo.adjustPlanResultDto.waybillNo': record.get('waybillNo'),	
			    	'vo.adjustPlanResultDto.ocustomerPickupOrgCode': record.get('ocustomerPickupOrgCode'),	
			    	'vo.adjustPlanResultDto.dcustomerPickupOrgCode': record.get('dcustomerPickupOrgCode')	
			    },	
			    success: function(response){	
			    	var result = Ext.decode(response.responseText);	
			    	rowBodyElement.getChangeInfoGrid().getStore().loadData(result.vo.waybillRfcChangeDetailList);	
			    	rowBodyElement.getChangeNodeGrid().getStore().loadData(result.vo.changeNodeDtoList);	
			    },	
			    exception : function(response){	
					var result = Ext.decode(response.responseText);	
					top.Ext.MessageBox.alert(waybill.waybill.i18n('pkp.waybill.waybill.tip'),result.message);	
				}	
			});	
		}	
});	
	
//待处理-GridPanel---第一层	
Ext.define('Foss.waybill.GridPanel',{	
	extend:'Ext.grid.Panel',	
    bodyCls: 'autoHeight',	
	cls: 'autoHeight',	
    frame: true,	
	id: 'Foss_waybill_GridPanel_Id',	
	hidden: false,	
    stripeRows: true,	
    title:waybill.waybill.i18n('pkp.waybill.waybill.exe.plan'),	 // 执行计划
    emptyText:waybill.waybill.i18n('pkp.waybill.waybill.query.result.empty'),	 // 查询结果为空
	collapsible: true,	
	animCollapse: true,	
	store: null,	
	siginOutStoraWindow : null,	
	viewConfig: {	
		// 单元格可复制	
        enableTextSelection: true	
    },	
	//表格行可展开的插件	
    plugins: [{	
		//定义插件的类型	
        ptype: 'rowexpander',	
		//定义行展开模式（单行与多行），默认是多行展开(值true)	
		rowsExpander: false,	
		layout : 'hbox',	
		//行体内容	
		rowBodyElement : 'FOSS.waybill.underPanel'	
    }],	
	columns: [	
		{ header: waybill.waybill.i18n('pkp.waybill.waybill.waybill.no'), dataIndex: 'waybillNo',   	align: 'center', width:79 },	 // 运单号
		{ header: waybill.waybill.i18n('pkp.waybill.waybill.receove.org'), dataIndex: 'receiveOrgName',  align: 'center', flex: 1 },	 // 出发部门
		{ header: waybill.waybill.i18n('pkp.waybill.waybill.old.arrival.org'), dataIndex: 'ocustomerPickupOrgName',   align: 'center', flex: 1 },	 // 原到达部门
		{ header: waybill.waybill.i18n('pkp.waybill.waybill.new.arrival.org'), dataIndex: 'dcustomerPickupOrgName',   align: 'center', flex: 1},	 // 新到达部门
		{ header: waybill.waybill.i18n('pkp.waybill.waybill.change.time'), 		 // 变更时间
		  dataIndex: 'operateTime',	
		  align: 'center',	
		  width:133,	
		  renderer : function (value) {	
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
		  }	
		}
    ],	
    pagingToolbar : null,	
  	getPagingToolbar : function() {	
  		if (this.pagingToolbar == null) {	
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {	
  				store : this.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true	
  			});	
  		}	
  		return this.pagingToolbar;	
  	},	
  	constructor: function(config){	
	var me = this,	
		cfg = Ext.apply({}, config);	
	me.store = Ext.create('Foss.waybill.AdjustPlanStore');	
	me.bbar = me.getPagingToolbar();	
	me.callParent([cfg]);	 	
	}	
});	
	
//待处理信息	
Ext.define('Foss.waybill.AdjustPlanResultModel', {	
	extend : 'Ext.data.Model',	
	fields : [	
		{name : 'waybillNo',type : 'string'},//运单号	
		{name : 'receiveOrgName',type : 'string'},//出发部门	
		{name : 'ocustomerPickupOrgName',type : 'string'},// 原到达部门Name	
		{name : 'dcustomerPickupOrgName',type : 'string', 
		convert : function(v, record) {
			if (record.data.ocustomerPickupOrgName == v) {
				return waybill.waybill.i18n('pkp.waybill.waybill.no');
			} else {
				return v;
			}
		}},// 新到达部门Name	
		{name : 'ocustomerPickupOrgCode',type : 'string'},// 原到达部门Code	
		{name : 'dcustomerPickupOrgCode',type : 'string'},// 新到达部门Code	
		{name : 'operateTime'}, // 变更时间	
		{name : 'waybillRfcId',type : 'string'}// 更改单ID	
			]	
});	
	
//待处理数据源	
Ext.define('Foss.waybill.AdjustPlanStore',{	
	extend: 'Ext.data.Store',	
	//绑定一个模型	
	model: 'Foss.waybill.AdjustPlanResultModel',	
	pageSize : 10,	
	//定义一个代理对象	
	proxy: {	
		//代理的类型为内存代理	
		type: 'ajax',	
		actionMethods:'POST',	
		url:waybill.realPath('queryAdjustPlanList.action'),	
		//定义一个读取器	
		reader: {	
			//以JSON的方式读取	
			type: 'json',	
			//定义读取JSON数据的根对象	
			root: 'vo.adjustPlanResultDtoList',	
			//返回总数	
			totalProperty : 'totalCount'	
		}	
	},// 事件监听	
	listeners:{	
		// 查询事件	
		beforeload:function(s,operation,eOpts){	
			var myForm = waybill.waybill.queryform.getForm();	
			var operateTimeStart = myForm.getValues().operateTimeStart, operateTimeEnd = myForm.getValues().operateTimeEnd;	
			var result = Ext.Date.parse(operateTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(operateTimeStart,'Y-m-d H:i:s');	
			if(result / (24 * 60 * 60 * 1000) >= 3){	
				Ext.ux.Toast.msg(waybill.waybill.i18n('pkp.waybill.waybill.tip'), waybill.waybill.i18n('pkp.waybill.waybill.date.not.more.three.days.apart'), 'error', 3000);	
				return false;	
			}	
			if(!myForm.isValid()){
				return false;
			}
			// 执行查询	
			var queryParams=waybill.waybill.queryform.getValues();	
			Ext.apply(operation,{	
				params:{	
					'vo.adjustPlanSearcherDto.waybillNo':queryParams.waybillNo,// 运单号	
//					'vo.adjustPlanSearcherDto.serialNo':queryParams.serialNo,// 标签编号	
					'vo.adjustPlanSearcherDto.receiveOrgCode':queryParams.receiveOrgCode,//  出发部门	
					'vo.adjustPlanSearcherDto.customerPickupOrgCode':queryParams.customerPickupOrgCode,// 原到达部门	
					'vo.adjustPlanSearcherDto.rfcType':queryParams.rfcType,// 更改类型	
					'vo.adjustPlanSearcherDto.operateTimeStart':queryParams.operateTimeStart,// 变更受理时间	
					'vo.adjustPlanSearcherDto.operateTimeEnd':queryParams.operateTimeEnd// 变更受理时间	
				}	
			});	
		}	
	}	
});	
	
Ext.onReady(function() {	
	Ext.QuickTips.init();	
	if (Ext.getCmp('adjustPlanByHandlerIndex_content')) {	
		return;	
	}	
	waybill.waybill.queryform =  Ext.create('Foss.waybill.QueryFormPanel');	
	waybill.waybill.gridPanel = Ext.create('Foss.waybill.GridPanel');	
		
	// // 定义到达联查询列表	
	Ext.create('Ext.panel.Panel',{	
		id:'T_waybill-adjustPlanByHandlerIndex_content',	
		cls : 'panelContentNToolbar',	
		bodyCls:'panelContentNToolbar-body',	
		layout:'auto',	
		getQueryForm: function(){	
			return waybill.waybill.queryform;	
		},	
		getResultGridPanel: function(){	
			return waybill.waybill.gridPanel;	
		},	
		items : [waybill.waybill.queryform, waybill.waybill.gridPanel],	
		renderTo: 'T_waybill-adjustPlanByHandlerIndex-body'	
	});	
});  	
