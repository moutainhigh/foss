/**
 * 查询
 * @param {} thisForm
 */
consumer.codManagerAudit.queryCodManagerAudit=function(thisForm){
	//得到Store
	var grid = Ext.getCmp('Foss_codManagerAudit_CodManagerAuditGrid_ID');
	var store = grid.getStore();
	if(store){
		if(grid.isHidden()){
			grid.show();
		}
		grid.getLoadMask().show();
		
		var CODQueryForm = Ext.getCmp('Foss_codManagerAudit_CodManagerAuditForm_ID');
		if (CODQueryForm) {
			var form = CODQueryForm.getForm();
			// 设置查询参数
			grid.setSubmitParams(form.getValues());
		}
		
		// 加载第一页数据
		store.loadPage(1,{
					callback : function(records, operation, success) {
						var rawData = store.proxy.reader.rawData;
						if(!success && !rawData.isException){
							Ext.Msg.alert(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'),rawData.message);
							return false;
						}
					}
				});
		
		grid.getLoadMask().hide();
	}
}

/**
 * 代收货款信息model
 */
Ext.define('Foss.codManagerAudit.CodManagerAuditModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	/**
	 * extid,id,运单号,应付单编号,总金额,冲应收金额,应退金额,代收货款类型,代收货款状态
	 * 发货客户名称,收款人,账号,开户行,发货客户与收款人关系,对公对私标志,省,
	 * 市,支行,收款人电话,应付单客户名称,业务日期
	 */
	fields : ['extid', 'id', 'waybillNo', 'payableNo', 'amount',
					'verReceivableAmount', 'returnAmount', 'codType', 'status',
					'customerName', 'payeeName', 'payeeAccount', 'bank',
					'payeeAndConsignor', 'publicPrivateFlag', 'province',
					'city', 'bankSubbranch', 'payeePhone',
					'payableCustomerName', {
						name : 'businessDate',
						type : 'date',
						convert : stl.longToDateConvert
					},'signDate', 'payableOrgName', 'notes']
});

/**
 * 代收货款信息数据Store
 */
Ext.define('Foss.codManagerAudit.CodManagerAuditStore',{
	extend:'Ext.data.Store',
	model:'Foss.codManagerAudit.CodManagerAuditModel',
	pageSize:20,
	proxy:{
		type:'ajax',
		actionMethods : 'post',
		url:consumer.realPath('queryManagerAuditCOD.action'),
		reader:{
			type:'json',
			root:'salesPayCODVO.cods',
			totalProperty:'salesPayCODVO.totalRecords'
		}
	}
});

/**
 * 审核支付申请Form
 */
Ext.define('Foss.codManagerAudit.CodManagerAuditForm',{
	extend:'Ext.form.Panel',
	title:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.codAudit'),
	frame:true,
	collapsible: true,
	animCollapse: true,
	height:180,
	layout : {
		type : 'column'
	},
	defaults : {
		msgTarget : 'under',
		allowBlank : true
	},
	items:[{
		xtype:'radio',
		name:'salesPayCODVO.queryCondition',
		inputValue:'all',
		boxLabel:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.queryAll'),
		columnWidth:.15,
		width:100,
		checked:true,
		listeners:{
			//判断是查询全部，还是根据单号查询
			change:function(_this,newValue,oldValue,eOpts){
				var waybillNos =_this.ownerCt.getForm().findField('salesPayCODVO.waybillNos');
				if(newValue){
					waybillNos.disable();
				}else{
					waybillNos.enable();
				}
			}
		}
	},{
		xtype:'radio',
		name:'salesPayCODVO.queryCondition',
		inputValue:'waybill',
		boxLabel:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.queryNo'),
		columnWidth:.15,
		width:70
	},{
		xtype : 'container',
		layout : 'column',
		columnWidth : .35,
		items : [ {
			xtype : 'textarea',
			name:'salesPayCODVO.waybillNos',
			allowBlank : false,
			blankText:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.queryNoCannotEmpty'),
			emptyText:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.eightToTenMostInputTenWaybillNumber'),
			width : 300,
			height : 60,
			//regex:/^([0-9]{8,10},?){0,10}$/i,
			//354658-校验至14位运单号
			regex:/^([0-9]{8,14})(,[0-9]{8,14}){0,9},?$/i,
			regexText:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.eightToTenMostInputTenWaybillNumber'),
			disabled:true
		} ]
	},{
		xtype:'container',
		html : '&nbsp;',
		columnWidth:.15
	},{
		xtype:'container',
		columnWidth:0.5,
		items:[{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.reset'),
			width:70,
			columnWidth:.1,
			handler:function(){
				//得到form表单
				var form = this.up('form').getForm();
				//重置
				form.reset();
			}
		}]
	},{
		xtype:'container',
		columnWidth:0.5,
		layout : {
			type : 'hbox',
			pack: 'end',
	        align: 'middle'
		},
		items:[{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.query'),
			width:118,
			cls:'yellow_button',
			handler:function(){
				if(this.up('form').getForm().isValid()){
					consumer.codManagerAudit.queryCodManagerAudit(this);
				}else{
					Ext.Msg.alert(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'),consumer.codManagerAudit.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
				}
			}
		}]
	}]
	
});

/**
 * 显示代收货款单据
 */
Ext.define('Foss.codManagerAudit.CodManagerAuditGrid',{
	extend:'Ext.grid.Panel',
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	frame:true,
	bodyCls : 'autoHeight',
	bodyStyle : {
		padding : '0px'
	},
	height:600,
	margin : '10 5 20 5',
	title: consumer.codManagerAudit.i18n('foss.stl.consumer.cod.codMessage'),
	columns : {
				defaults:{
					draggable:false
				},
				items:[{ 
				    xtype:'actioncolumn',
					width:73,
					text: consumer.codManagerAudit.i18n('foss.stl.consumer.common.actionColumn'),
					align: 'center',
					items:[{
						iconCls : 'foss_icons_stl_auditing',
						tooltip : consumer.codManagerAudit.i18n('foss.stl.consumer.common.audit'),
						handler:function(grid, rowIndex, colIndex){
							var selection = grid.getStore().getAt(rowIndex);
							// 审核创建弹出窗口，输入密码校验
							var passwordWindow = Ext.getCmp('Foss_codManagerAudit_PasswordWindow_ID');
							if(!passwordWindow){
									passwordWindow=Ext.create('Foss.codManagerAudit.PasswordWindow',{
									id:'Foss_codManagerAudit_PasswordWindow_ID'
								});
							}
							passwordWindow.show();
							var form = Ext.getCmp('Foss_codManagerAudit_PasswordForm_ID').getForm();
							form.loadRecord(selection);
							form.findField('password').setValue('');// 每次加载，给默认值
						}
					  },{
						iconCls : 'foss_icons_stl_fauditing',
						tooltip : consumer.codManagerAudit.i18n('foss.stl.consumer.common.return'),
						handler:function(grid, rowIndex, colIndex){
							var selection = grid.getStore().getAt(rowIndex);
							// 退回创建弹出窗口，输入退回原因
							var returnWindow = Ext.getCmp('Foss_codManagerAudit_ReturnWindow_ID');
							if(!returnWindow){
									returnWindow=Ext.create('Foss.codManagerAudit.ReturnWindow',{
									id:'Foss_codManagerAudit_ReturnWindow_ID'
								});
							}
							returnWindow.show();
							var form = Ext.getCmp('Foss_codManagerAudit_ReturnForm_ID').getForm();
							form.loadRecord(selection);
							form.findField('refundNotes').setValue('');
						}
					  }
					]
				},{
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.wayBillNo'),
					dataIndex : 'waybillNo'
				},{
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.payableNo'),
					dataIndex : 'payableNo'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.billAmount'),
					dataIndex : 'amount'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.verReceivableAmount'),
					dataIndex : 'verReceivableAmount'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.returnAmount'),
					dataIndex : 'returnAmount'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.codReturnType'),
					dataIndex : 'codType',
					renderer:function(value){
						/*if(value == 'R1'){
							return '即日退';
						}
						else if(value == 'R3'){
							return '三日退';
						}
						else if(value == 'RA'){
							return '审核退';
						}else{
							return value;
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__COD_TYPE');
			    		return displayField;
					}
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.codStatus'),
					dataIndex : 'status',
					renderer:function(value){
						/*if(value == 'CA'){
							return '收银员审核';
						}else{
							return null;
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__STATUS');
			    		return displayField;
					}
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.customerName'),
					dataIndex : 'payableCustomerName'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.payeeName'),
					dataIndex : 'payeeName'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.payeeAccount'),
					dataIndex : 'payeeAccount'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.bank'),
					dataIndex : 'bank'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.payeeAndConsignor'),
					dataIndex : 'payeeAndConsignor'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.publicPrivateFlag'),
					dataIndex : 'publicPrivateFlag',
					renderer:function(value){
						/*if(value == 'C'){
							return '对公';
						}
						else if(value == 'R'){
							return '对私';
						}else{
							return '未知';
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__PUBLIC_PRIVATE_FLAG');
			    		return displayField;
					},
					hidden: true
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.province'),
					dataIndex : 'province',
					hidden: true
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.city'),
					dataIndex : 'city',
					hidden: true
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.bankSubbranch'),
					dataIndex : 'bankSubbranch',
					hidden: true
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.payeePhone'),
					dataIndex : 'payeePhone',
					hidden: true
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.businessDate'),
					dataIndex : 'businessDate',
					format : 'Y-m-d',
					xtype : 'datecolumn'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.orgName'),
					dataIndex : 'payableOrgName'
				}, {
					header : consumer.codManagerAudit.i18n('foss.stl.consumer.cod.notes'),
					dataIndex : 'notes'
				}]}	,
	store:null,
	bottomBar:null,
	getBottomBar:function(){
		var me = this;
		if(Ext.isEmpty(me.bottomBar)){
			me.bottomBar = Ext.create('Ext.panel.Panel',{
				border:0,
				items:[Ext.create('Deppon.StandardPaging', {
						store: me.store,
						pageSize: 20,
			    		plugins: Ext.create('Deppon.ux.PageSizePlugin', {
							//设置分页记录最大值，防止输入过大的数值
							maximumSize: 100
						})
				}),Foss.codManagerAudit.footBottomPanel]
			});
		}
		return me.bottomBar
	},
	loadMask:null,
	getLoadMask:function(){
		var me = this;
		me.loadMask = Ext.getCmp('FOSS_consumer_CodManagerAuditGrid_LoadMask_ID');
		if(Ext.isEmpty(me.loadMask)){
			me.loadMask = new Ext.LoadMask(me.up('panel'),{
				id:'FOSS_consumer_CodManagerAuditGrid_LoadMask_ID',
				msg:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.dataLoading'),
				autoShow:false
			});
		}
		return me.loadMask;
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this;
		var cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.codManagerAudit.CodManagerAuditStore',{
			listeners : {
				beforeload : function(store, operation, eOpts) {
					Ext.apply(me.submitParams, {
				          "limit":operation.limit,
				          "page":operation.page,
				          "start":operation.start
				          }); 
					
					Ext.apply(operation, {
		   				params : me.submitParams
		   			});
				}
			}
		});
		me.bbar = me.getBottomBar();
		me.callParent([cfg]);
	}
});

Foss.codManagerAudit.footBottomPanel = Ext.create('Ext.panel.Panel',{
	border:0,
	dockedItems:[{
   		xtype: 'toolbar',
	    dock: 'bottom',
	    layout:'column',		    	
	    defaults:{
		margin:'0 0 5 3'
		},		
	    items: [{
	    	xtype:'tbtext',
			readOnly:true,
			name:'totalRows',
			columnWidth:0.75
		},{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.audit'),
			columnWidth:0.1,
			width:54,
			handler: function() {
				var selectionModel = Ext.getCmp('Foss_codManagerAudit_CodManagerAuditGrid_ID').getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				if(rows.length==0){
						Ext.Msg.alert(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'),consumer.codManagerAudit.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataManagerForReview'));
						return false;
				}
				
				var passwordWindow = Ext.getCmp('Foss_codManagerAudit_PasswordWindow_ID');
				if(!passwordWindow){
						passwordWindow=Ext.create('Foss.codManagerAudit.PasswordWindow',{
						id:'Foss_codManagerAudit_PasswordWindow_ID'
					});
				}
				passwordWindow.show();
				Ext.getCmp('Foss_codManagerAudit_PasswordForm_ID').getForm().reset();
	        }
		},{
			xtype:'container',
			columnWidth:0.02,
			html:'&nbsp;',
			border:0
		},{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.return'),
			columnWidth:0.1,
			width:54,
			handler: function() {
				var selectionModel = Ext.getCmp('Foss_codManagerAudit_CodManagerAuditGrid_ID').getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				if(rows.length==0){
						Ext.Msg.alert(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'),consumer.codManagerAudit.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneReturnDataOperation'));
						return false;
				}
				
				var returnWindow = Ext.getCmp('Foss_codManagerAudit_ReturnWindow_ID');
				if(!returnWindow){
						returnWindow=Ext.create('Foss.codManagerAudit.ReturnWindow',{
						id:'Foss_codManagerAudit_ReturnWindow_ID'
					});
				}
				returnWindow.show();
				Ext.getCmp('Foss_codManagerAudit_ReturnForm_ID').getForm().reset();
	        }
		}]
	}]
});

/**
 * 填写密码form
 */
Ext.define('Foss.codManagerAudit.PasswordForm',{
	extend:'Ext.form.Panel',
	frame:true,
	collapsible:false,
	animcollapse:true,
	layout:{
		type : 'column'
	},	
    bodyStyle: 'padding: 5px;',
    fieldDefaults: {
		msgTarget : 'qtip'
	},
	constructor: function(config){
  		var me = this,cfg = Ext.apply({}, config);
  		me.items=[{
  			xtype: 'textfield',
  		   	name: 'id',
	   		fieldLabel: 'ID',
	   		value:null,
	   		hidden:true
	   	},{
	    	xtype: 'textfield',
			fieldLabel: consumer.codManagerAudit.i18n('foss.stl.consumer.cod.password'),
			margin:'60 0 60 0',
			name: 'password',
			columnWidth:1,
			allowBlank:false,
			inputType: 'password',    //密码框属性设置
			value:null,
			blankText:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.passwordCannotEmpty')
		},{
			xtype:'container',
			html : '&nbsp;',
			columnWidth:.3
		},{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.OK'),
			disabled:!consumer.codManagerAudit.isPermission('/stl-web/consumer/agreeChangeAccounts.action'),
			hidden:!consumer.codManagerAudit.isPermission('/stl-web/consumer/agreeChangeAccounts.action'),
			columnWidth:.2,
			margin:'5 10 3 0',
			handler:function(){
				var grid = Ext.getCmp('Foss_codManagerAudit_CodManagerAuditGrid_ID');
				var formPanel = Ext.getCmp('Foss_codManagerAudit_PasswordForm_ID');
				var form = formPanel.getForm(); 
				formPanel.getLoadMask().show();
				var selectionModel = grid.getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				jsonData = new Array();
				var val = form.findField('id').getValue();
				if(val != null && val != ''){//通过操作列弹框，获取点击的列id
					jsonData.push(val);
				}else{
					for(var i=0;i<rows.length;i++){
						jsonData.push(rows[i].get('id'));
					}
				}
				if(form.isValid()){
					//经理审核同意
					Ext.Ajax.request({
						url : consumer.realPath('agreeChangeAccounts.action'),
						params : {
									'salesPayCODVO.entityIds':jsonData,
									'salesPayCODVO.password':form.findField("password").getValue() 
								},
						method:'post',		
						success:function(response){
							Ext.ux.Toast.msg(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'), consumer.codManagerAudit.i18n('foss.stl.consumer.cod.auditSuccess'),'ok',1000);
							var store = grid.getStore();
							if(val != null && val != ''){//通过操作列弹框，请求成功后删除列
								var storeLength = store.getCount();
								var record = null;
								for (var i = 0; i < storeLength; i++) {
									var rd = store.getAt(i);
									var rid =rd.get('id');
									if(rid == val){
										record = rd;
									}
								}
								if(record!=null){
									store.remove(record);
								}
								
							}else{
								var selectionModel = grid.getSelectionModel();
								var rows= selectionModel.getSelection(); //获取所有选中行，
								for(var i=0;i<rows.length;i++){
									store.remove(rows[i]);
								}
							}
							formPanel.getLoadMask().hide();
							store.load();
							var passwordWindow = Ext.getCmp('Foss_codManagerAudit_PasswordWindow_ID');
							passwordWindow.hide();
						},
						exception : function(response) {
						  var json = Ext.decode(response.responseText);
						  Ext.ux.Toast.msg(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
						  formPanel.getLoadMask().hide();
						},
						failure:function(form,action){
							formPanel.getLoadMask().hide();
						},
						unknownException:function(form,action){
							formPanel.getLoadMask().hide();
						}					
					}); 
				}else{
					formPanel.getLoadMask().hide();
					Ext.Msg.alert(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'),consumer.codManagerAudit.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
				}
			}
		},{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.cancel'),
			columnWidth:.2,
			margin:'5 10 3 0',
			handler:function(){
				var passwordWindow = Ext.getCmp('Foss_codManagerAudit_PasswordWindow_ID');
				passwordWindow.hide(); 
			}
		},{
			xtype:'container',
			html : '&nbsp;',
			columnWidth:.3
		}];
	me.callParent([cfg]);
  },
  loadMask:null,
  getLoadMask:function(){
	var me = this;
	me.loadMask = Ext.getCmp('FOSS_consumer_PasswordForm_LoadMask_ID');
	if(Ext.isEmpty(me.loadMask)){
		me.loadMask = new Ext.LoadMask(me.up('panel'),{
			id:'FOSS_consumer_PasswordForm_LoadMask_ID',
			msg:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.dataLoading'),
			autoShow:false
		});
	}
	return me.loadMask;
  }
});

/**
 * 退回原因form
 */
Ext.define('Foss.codManagerAudit.ReturnForm',{
	extend:'Ext.form.Panel',
	frame:true,
	collapsible:false,
	animcollapse:true,
	layout:{
		type : 'column'
	},	
    fieldDefaults: {
		msgTarget : 'qtip'
	},	
    bodyStyle: 'padding: 5px;',
	constructor: function(config){
  		var me = this,cfg = Ext.apply({}, config);
  		me.items=[{
  			xtype: 'textfield',
  		   	name: 'id',
	   		fieldLabel: 'ID',
	   		value:null,
	   		hidden:true
	   	},{
			xtype:'textarea',
			fieldLabel: ' ',
			labelSeparator: '',
			labelWidth:1,
			name:'refundNotes',
			width:300,
			height:140,
			columnWidth:1,
			value:null,
			allowBlank:false,
			blankText:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.returnOpinionCannotEmpty')
		},{
			xtype:'container',
			html : '&nbsp;',
			columnWidth:.3
		},{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.OK'),
			disabled:!consumer.codManagerAudit.isPermission('/stl-web/consumer/denyChageAccounts.action'),
			hidden:!consumer.codManagerAudit.isPermission('/stl-web/consumer/denyChageAccounts.action'),
			columnWidth:.2,
			margin:'5 10 3 0',
			handler:function(){
				var formPanel = Ext.getCmp('Foss_codManagerAudit_ReturnForm_ID');
				formPanel.getLoadMask().show();
				var grid = Ext.getCmp('Foss_codManagerAudit_CodManagerAuditGrid_ID');
				var selectionModel = grid.getSelectionModel();
				var form = formPanel.getForm(); 
				var rows= selectionModel.getSelection(); //获取所有选中行，
				jsonData = new Array();
				var val = form.findField('id').getValue();
				if(val != null && val != ''){//通过操作列弹框，获取点击的列id
					jsonData.push(val);
				}else{
					for(var i=0;i<rows.length;i++){
						jsonData.push(rows[i].get('id'));
					}
				}
				if(form.isValid()){
					//退回
					Ext.Ajax.request({
						url:consumer.realPath('denyChageAccounts.action'),
						method:'post',
						params : {
									'salesPayCODVO.entityIds':jsonData,
									"salesPayCODVO.refundNotes":form.findField("refundNotes").getValue() 
								},
						success:function(response){
							Ext.ux.Toast.msg(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'), consumer.codManagerAudit.i18n('foss.stl.consumer.cod.returnSuccess'),'ok',1000);
							var store = grid.getStore();
							if(val != null && val != ''){//通过操作列弹框，请求成功后删除列
								var storeLength = store.getCount();
								var record = null;
								for (var i = 0; i < storeLength; i++) {
									var rd = store.getAt(i);
									var rid =rd.get('id');
									if(rid == val){
										record = rd;
									}
								}
								if(record!=null){
									store.remove(record);
								}
								
							}else{
								var selectionModel = grid.getSelectionModel();
								var rows= selectionModel.getSelection(); //获取所有选中行，
								for(var i=0;i<rows.length;i++){
									store.remove(rows[i]);
								}
							}
							formPanel.getLoadMask().hide();
							store.load();
							var returnWindow = Ext.getCmp('Foss_codManagerAudit_ReturnWindow_ID');
							returnWindow.hide(); 
							
						},
						exception : function(response) {
						  var json = Ext.decode(response.responseText);
						  Ext.ux.Toast.msg(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
						  formPanel.getLoadMask().hide();
						},
						failure:function(form,action){
							formPanel.getLoadMask().hide();
						},
						unknownException:function(form,action){
							formPanel.getLoadMask().hide();
						}					
					}); 
				}else{
					formPanel.getLoadMask().hide();
					Ext.Msg.alert(consumer.codManagerAudit.i18n('foss.stl.consumer.common.warmTips'),consumer.codManagerAudit.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
				}
				
			}
		},{
			xtype:'button',
			text:consumer.codManagerAudit.i18n('foss.stl.consumer.common.cancel'),
			columnWidth:.2,
			margin:'5 10 3 0',
			handler:function(){
				var returnWindow = Ext.getCmp('Foss_codManagerAudit_ReturnWindow_ID');
				returnWindow.hide(); 
			}
		},{
			xtype:'container',
			html : '&nbsp;',
			columnWidth:.3
		}];
	me.callParent([cfg]);
  },
  loadMask:null,
  getLoadMask:function(){
	var me = this;
	me.loadMask = Ext.getCmp('FOSS_consumer_ReturnForm_LoadMask_ID');
	if(Ext.isEmpty(me.loadMask)){
		me.loadMask = new Ext.LoadMask(me.up('panel'),{
			id:'FOSS_consumer_ReturnForm_LoadMask_ID',
			msg:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.dataLoading'),
			autoShow:false
		});
	}
	return me.loadMask;
  }
});

/**
 * 填写密码window
 */
Ext.define('Foss.codManagerAudit.PasswordWindow',{
  extend: 'Ext.Window', 
	closeAction: 'close',
	title:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.inputPassword'),
	x:(stl.SCREENWIDTH-400)/2,
	y:150,
	modal:true,
	width:400,
	height:300,
  resizable:false,
  //layout: 'fit',
  layout : 'column',
  codManagerAuditPasswordForm:null,
  getCodManagerAuditPasswordForm:function(){  
  	if(this.writeoffNoteForm==null){
  		this.writeoffNoteForm=Ext.create('Foss.codManagerAudit.PasswordForm',{id:'Foss_codManagerAudit_PasswordForm_ID'});
  	}
  	return this.writeoffNoteForm;
  }, 
  constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getCodManagerAuditPasswordForm()];
		me.callParent([cfg]);
  }
});

/**
 * 退回原因window
 */
Ext.define('Foss.codManagerAudit.ReturnWindow',{
  	extend: 'Ext.Window', 
	closeAction: 'close',
	title:consumer.codManagerAudit.i18n('foss.stl.consumer.cod.returnReason'),
	x:(stl.SCREENWIDTH-400)/2,
	y:150,
	modal:true,
	width:400,
	height:300,
  	resizable:false,
  	layout : 'column',
  	codManagerAuditReturnForm:null,
  	getCodManagerAuditReturnForm:function(){  
  		if(this.writeoffNoteForm==null){
  			this.writeoffNoteForm=Ext.create('Foss.codManagerAudit.ReturnForm',{id:'Foss_codManagerAudit_ReturnForm_ID'});
  		}  
  		return this.writeoffNoteForm;
  	}, 
  	constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getCodManagerAuditReturnForm()];
		me.callParent([cfg]);
  	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	//创建QUERY表单
	var codManagerAuditForm = Ext.getCmp('Foss_codManagerAudit_CodManagerAuditForm_ID');
	if(!codManagerAuditForm){
		codManagerAuditForm = Ext.create('Foss.codManagerAudit.CodManagerAuditForm',{
			id: 'Foss_codManagerAudit_CodManagerAuditForm_ID'
		});
	}
	//创建显示表格
	var codManagerAuditGrid = Ext.create('Foss.codManagerAudit.CodManagerAuditGrid',{
		id : 'Foss_codManagerAudit_CodManagerAuditGrid_ID',
		hidden:true,
		//enableColumnHide: false,      //取消列头菜单
      	//sortableColumns: false,          //取消列头排序功能
		viewConfig : {   
			enableTextSelection: true,           
	        forceFit : true,
	        //stripeRows: false,//显示重复样式，不用隔行显示
	        emptyText : consumer.codManagerAudit.i18n('foss.stl.consumer.common.emptyText')
    	}
	});
	//显示到JSP页面
	Ext.create('Ext.panel.Panel',{
		id: 'T_consumer-codManagerAudit_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [codManagerAuditForm,codManagerAuditGrid],
		renderTo: 'T_consumer-codManagerAudit-body'
	});
});