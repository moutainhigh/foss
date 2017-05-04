//package Foss.queryDpjzSignInDetialBill 家装安装明细及签收确认查询
//家装安装明细及签收确认model
Ext.define('Foss.partialline.queryDpjzSignInDetialBill.model.DpjzSignInBillDetailModel', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'dopId',type:'string'},//DOP推送给FOSS中转待审核信息时带的字段值
		{name: 'id',type:'string'},//ID
		{name: 'waybillNo',type:'string'},//运单号	
		{name: 'transcargoName', type: 'string'},//德邦开单安装明细
		{name: 'realInstallInfo', type: 'string'},//供应商送装明细
		{name: 'signInMsg', type: 'string'},//供应商签收信息=签收状态+签收备注+签收件数+供应商签收时间+供应商名称
		{name: 'status', type: 'string'},//操作状态
		{name: 'lastOperUser', type: 'string'},//最后操作人
		{name: 'checkOpinion', type: 'string'},//核对意见
		{name: 'lastOperTime', type: 'date',//最后操作时间
			convert:function(value){
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{name: 'feedBackTime', type: 'date',//供应商反馈时间
			convert: function(value){
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		},
		{name: 'startEndTime', type: 'date',//起止时间
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		}
	]
});

//家装安装明细及签收确认store
Ext.define('Foss.partialline.queryDpjzSignInDetialBill.store.DpjzSignInBillDetailStore',{
	extend: 'Ext.data.Store',
	// 绑定一个模型
	model: 'Foss.partialline.queryDpjzSignInDetialBill.model.DpjzSignInBillDetailModel',
	pageSize:10,//默认每页显示行
	// 定义一个代理对象
	proxy: {
		type: 'ajax',
		// 请求的url
		url:partialline.realPath('querydpjzSignInDetailBills.action'),
		//请求方式
		actionMethods:'post',
		// 定义一个读取器
		reader: {
			// 以JSON的方式读取
			type: 'json',
			// 定义读取JSON数据的根对象
			root: 'dpjzSignInVo.dpjzSignInDetialBill',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		'beforeload' : function(store, operation, eOpts) {
			//获取form表单对应数据
			var queryParams = partialline.queryDpjzSignInDetialBill.queryForm.getForm().getValues();
			//查询参数绑定
				Ext.apply(operation, {
					params : {
						'dpjzSignInVo.dpjzSignInDetialBillDto.waybillNo' : queryParams.waybillNo,//运单号
						'dpjzSignInVo.dpjzSignInDetialBillDto.startEndTimeFrom' : queryParams.startEndTimeFrom,//开始时间
						'dpjzSignInVo.dpjzSignInDetialBillDto.startEndTimeTo' : queryParams.startEndTimeTo//终止时间
					}
				});	
		},
		load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records)){
				Ext.ux.Toast.msg('提示信息', '没有查询到符合条件的待核对运单!');
			}
		}
	}
});

//查询form表单
Ext.define('Foss.partialline.queryDpjzSignInDetialBill.form.DpjzSignInBillDetailSearch',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:100
	},
	items: [{
		vtype : 'waybill',
		name : 'waybillNo',
		fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.model.waybillNo.label'),//运单号
		columnWidth:.3
	},{
		border : false,
		xtype : 'container',
		columnWidth:.1,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
			}
	},{
		xtype: 'rangeDateField',
		fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.model.startEndTime.label'),//起止时间
		dateType: 'datetimefield_date97',
		id:'Foss_queryDpjzSignInDetialBill_DpjzSignInBillDetailSearch_startEndTime_RangeDateField_ID',
		fieldId:'startEndTime_queryDpjzSignInDetialBillDateFrom_ID',
		fromName: 'startEndTimeFrom',
		toName: 'startEndTimeTo',
		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'),
		toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		dateRange:7,
		labelWidth: 100,
		disallowBlank:true,
		allowBlank:false,
		columnWidth: .6	
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.form.DpjzSignInBillDetailSearch.button.reset'),//重置
			handler: function() {	
				var bform = partialline.queryDpjzSignInDetialBill.queryForm.getForm();
				bform.findField('waybillNo').reset();
				bform.findField('startEndTimeFrom').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
				bform.findField('startEndTimeTo').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'));
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.form.DpjzSignInBillDetailSearch.button.query'),//查询
			cls:'yellow_button',
			handler: function() {
				//若输入框中运单号信息和起止时间冲突，以输入框中输入的运单号信息为准
				//输入框中运单号需在foss储存表中存在、非已中止/已作废、且属于家装类运单的
				//按运单号查询时不受其他条件的限制。若无此单号，弹框提示：请输入正确的单号。
				var form=this.up('form').getForm();
				if(Ext.isEmpty(form.findField('waybillNo').getValue())){				
					var formValid=form.isValid( );
					if(formValid){
						partialline.queryDpjzSignInDetialBill.pagingBar.moveFirst();
					}
				}else{
					if(form.findField('waybillNo').isValid( )){
						partialline.queryDpjzSignInDetialBill.pagingBar.moveFirst();
					}
					
				}
								
			}
		}]
	}]
});

//查询结果列表
//家装安装明细及签收确认
Ext.define('Foss.partialline.queryDpjzSignInDetialBill.grid.searchResultGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.dpjzSignInGrid.title'),//查询结果
	emptyText: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.grid.emptyText'),//查询结果为空
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	autoScroll:true,
	columns: [{
		text: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.id'),//id
		dataIndex: 'id',
		hidden:true
	},{
		text: 'dopID',//dopId
		dataIndex: 'dopId',
		hidden:true
	},{		
		text: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.grid.waybillNo.label'),//运单号
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'waybillNo'
	},{
		xtype : 'actioncolumn',
		text : partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.grid.actionColumn'),//操作
		align : 'center',
		items :[ {
			tooltip : '核对',//核对
			iconCls :'foss_icons_pkp_viewOrderlocation', //deppon_icons_edit	
		 handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				//只有运单状态为”未操作“时，系统才允许点击核对按钮进行下一步操作
				if(record.get('status')!='UNCOMMITTED'){
					Ext.Msg.alert("提示","只有运单状态为'未操作'时,才可以点击核对按钮");
				}else{
					partialline.queryDpjzSignInDetialBill.checkSignInMsgWin = Ext.create('Foss.partialline.queryDpjzSignInDetialBill.checkSignInMsgWin').show();
					partialline.queryDpjzSignInDetialBill.checkSignInMsg.getForm().loadRecord(record)
//					var checkForm = checkInfoWindow.form;
//					//运单号
//					checkForm.findField('waybillNo').setValue(rec.get('waybillNo'));
//					//德邦开单安装明细
//					checkForm.findField('transcargoName').setValue(rec.get('transcargoName'));
//					//供应商送装明细
//					checkForm.findField('realInstallInfo').setValue(rec.get('realInstallInfo'));
//					//供应商签收信息
//					checkForm.findField('signInMsg').setValue(rec.get('signInMsg'));
//					//显示核对窗口
//					checkInfoWindow.show();
				}
			}
		  }]
	},
	{
		text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.transcargoName.label'),//德邦开单安装明细
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'transcargoName'
	},{
		text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.realInstallInfo.label'),//供应商送装明细
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'realInstallInfo'
	},{//供应商签收信息=签收状态+签收备注+签收件数+供应商签收时间+供应商名称
		text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.signInMsg.label'),//供应商签收信息
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'signInMsg'
	},{
		text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.feedBackTime.label'),//供应商反馈时间
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'feedBackTime'
	},{
		text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.status.label'),//操作状态
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'status',
		renderer : function(value){
			if(value === 'UNCOMMITTED'){
				return '未操作';
			}else if(value === 'PASS'){
				return '同意';
			}else if(value=='NOTPASS'){
				return '不同意';
			}else{
				return '';
			}
			return value;
		}
	},{
		text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.lastOperTime.label'),//最后操作时间
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'lastOperTime'
	},{
		text: partialline.queryDpjzSignInDetialBill.i18n('Foss.partialline.queryDpjzSignInDetialBill.grid.lastOperUser.label'),//最后操作人
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'lastOperUser'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		//定义数据集
		me.store = Ext.create('Foss.partialline.queryDpjzSignInDetialBill.store.DpjzSignInBillDetailStore');	
		//分页
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		partialline.queryDpjzSignInDetialBill.pagingBar = me.bbar;
		me.callParent([cfg]);
	}	
});

//点击核对，弹出核对窗口
//送装明细及签收信息核对
Ext.define('Foss.partialline.queryDpjzSignInDetialBill.checkSignInMsgWin',{
	extend: 'Ext.window.Window',
	title: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.checkSignInMsgTitle'),//送装明细及签收信息核对
	modal:true,
	closeAction:'hide',
	width: 680,
	height: 400,
	layout: 'auto',
	items: [
	        partialline.queryDpjzSignInDetialBill.checkSignInMsg = Ext.create('Ext.form.Panel', {
			layout : 'column',
			frame : false,
			defaults : {
					margin: '5 5 5 5',
	        		columnWidth:1,
	        		xtype: 'textareafield',
	        		labelWidth:120
			},
		items: [{
					xtype: 'textfield',
					fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.id'),//id
					name: 'id',
					hidden:true
				}, {
					xtype: 'textfield',
					fieldLabel: 'dopId',//dopID
					name: 'dopId',
					hidden:true
				},{
					xtype: 'textfield',
					fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.waybillNo'),//运单号
					name: 'waybillNo',
					hidden:true
				},{
					fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.transcargoName'),//德邦开单安装明细 
					name: 'transcargoName',
					readOnly:true
				},{
					fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.realInstallInfo'),//供应商送装明细
					name: 'realInstallInfo',
					readOnly:true
				},{
					fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.signInMsg'), //供应商签收信息
					name: 'signInMsg',
				    readOnly:true
				},{
					xtype: 'textareafield',
					fieldLabel: partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.checkOpinion'), //核对意见
					name: 'checkOpinion',
					maxLength:500,
					emptyText : partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.check.checkOpinion.valitation'),
					allowBlank:false
				}],
				buttons : [{
					text : partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.button.notPass'),// 不同意
					cls : 'yellow_button',
					handler : function() {
						var upForm=this.up('form');
						var form = this.up('form').getForm();
						var vals = this.up('form').getForm().getValues();
						vals.checkState = 'NOTPASS';
						Ext.Msg.confirm('是否不同意','是否审核不同意',function(btn,text){   //提示是否审核不同意
							//询问是否不同意，是则发送请求
							if(btn == 'yes'){
								if (vals.checkOpinion == '') {
									Ext.Msg.alert('异常','核对意见不能为空');
									return;
									} else {
											if (form.isValid()) {
												var params = {
													dpjzSignInVo : {
														dpjzSignInDetialCheckEntity : vals
													}
												};
												Ext.Ajax.request({
													url : partialline.realPath('dpjzSignInDetialCheckNotPass.action'),
													jsonData : params,
													success : function(response, opts) {
														form.reset();
														Ext.ux.Toast.msg('提示','核对不同意保存成功','ok', 1000);
														upForm.up('window').close();
														partialline.queryDpjzSignInDetialBill.pagingBar.moveFirst()
													},
													failure : function(response, opts) {
														Ext.Msg.alert('提示','核对不同意保存失败');
													},
													exception : function(response) {
														var json = Ext.decode(response.responseText);
														Ext.Msg.alert('异常',json.message);
													}
												});
											}else{
							        				Ext.Msg.alert('有必填项未写');
							        				return false; 
											}
										}
							}
						});
						}
				}, {
					text : partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.button.cancle'),// 取消操作
					cls : 'yellow_button',
					handler : function() {
						var upForm=this.up('form');
						Ext.Msg.confirm('是否取消','是否取消，关闭窗口',function(btn,text){   //提示是否审核同意
							//询问是否同意，是则发送请求
							if(btn == 'yes'){
								//点击“取消操作”，则弹出二级窗口消失，系统不执行其他规则
								upForm.up('window').close();
							}
						});
						
					}
				},{
					text : partialline.queryDpjzSignInDetialBill.i18n('foss.partialline.queryDpjzSignInDetialBill.button.pass'),// 同意
					cls : 'yellow_button',
					handler : function() {
						var upForm=this.up('form');
						var form = this.up('form').getForm();
						var vals = this.up('form').getForm().getValues();
						vals.checkState = 'PASS';
						Ext.Msg.confirm('是否同意','是否审核同意',function(btn,text){   //提示是否审核同意
							//询问是否同意，是则发送请求
							if(btn == 'yes'){
								if(vals.checkOpinion==''){
									Ext.Msg.alert('异常','核对意见不能为空');
									return;
									}else{
										if (form.isValid()) {
											var params = {
												dpjzSignInVo : {
													dpjzSignInDetialCheckEntity : vals
													}
												};
											Ext.Ajax.request({
												url : partialline.realPath('dpjzSignInDetialCheckPass.action'),
												jsonData : params,
												success : function(response, opts) {
													form.reset();
													Ext.ux.Toast.msg('提示','核对意见同意保存成功','ok', 1000);
													upForm.up('window').close();
													partialline.queryDpjzSignInDetialBill.pagingBar.moveFirst()
												},
												failure : function(response, opts) {
														Ext.Msg.alert('提示','核对不同意保存失败');
													},
												exception : function(response) {
														var json = Ext.decode(response.responseText);
														Ext.Msg.alert('异常',json.message);
													}
											});
										}else{
											Ext.Msg.alert('有必填项未写');
					        				return false; 
										}
									}
							}
						});	
						}
														
					}]
				})
	]
});

//模块入口函数
Ext.onReady(function() {
	
	//查询表单
	var queryForm = Ext.create('Foss.partialline.queryDpjzSignInDetialBill.form.DpjzSignInBillDetailSearch');
	partialline.queryDpjzSignInDetialBill.queryForm=queryForm;
	
	//查询结果
	var searchResultGrid = Ext.create('Foss.partialline.queryDpjzSignInDetialBill.grid.searchResultGrid');
	partialline.queryDpjzSignInDetialBill.searchResultGrid=searchResultGrid;
	
	//显示偏线界面
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-queryDpjzSignInDetialBillIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		checkInfoWindow : null,
		
		getCheckInfoWindow : function() {
			if (this.checkInfoWindow == null) {
				this.checkInfoWindow = Ext.create('Foss.partialline.queryDpjzSignInDetialBill.checkSignInMsgWin');
			}
			return this.checkInfoWindow;
		},
		items : [queryForm,searchResultGrid],
		renderTo: 'T_partialline-queryDpjzSignInDetialBillIndex-body'
		
	});
	partialline.queryDpjzSignInDetialBill.pagingBar.moveFirst();
});