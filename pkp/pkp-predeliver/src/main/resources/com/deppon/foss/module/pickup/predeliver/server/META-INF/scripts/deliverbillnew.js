/**
 * 
 * @param {日期}
 *            date
 * @param {小时}
 *            hours
 * @param {分钟}
 *            minutes
 * @param {秒}
 *            seconds
 * @param {微秒}
 *            milliseconds
 * @return {}
 */
predeliver.deliverbillnew.getDate = function(date, hours, minutes, seconds,
		milliseconds) {
	date.setHours(hours);
	date.setMinutes(minutes);
	date.setSeconds(seconds);
	date.setMilliseconds(milliseconds);

	return date;
};

predeliver.deliverbillnew.isArriveSheetDeliverbillId=null;
/** -----------------------------------------派送单 Begin----------------------------------------- */
//派车类型集合
Ext.define('Foss.predeliver.deliverbillnew.DeliverTypeNewStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
		    {'valueCode':'','valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.all')},	//全部
			{'valueCode':'NOMAL','valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.nomal')}, //正常 
			{'valueCode':'SPECIAL','valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.special')}, //专车
			{'valueCode':'MANNED','valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.manned')} //带人
		]
	},
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
	}
});

//派车类型集合
Ext.define('Foss.predeliver.deliverbillnew.vehicleTypeTermscode',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data: {
		'items':[
		    {'valueCode':'','valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.all')}, //全部
			{'valueCode':'OWN','valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.own')}, //公司车
			{'valueCode':'LEASED','valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.leased')} //外请车
		]
	},
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
	}
});

//查询条件Form
Ext.define('Foss.predeliver.deliverbillnew.QueryDeliverbillNewForm', {
			extend : 'Ext.form.Panel',
			title :predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.queryCondition'),   // 查询条件
			frame : true,
			collapsible : true,
			animCollapse : true,
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			defaults : {
				margin : '5 15 5 15',
				labelWidth : 90
			},
			defaultType : 'textfield',
			layout : 'column',
			items : [{
				name : 'deliverbillNo',
				value : 'P',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverbillNo'),  //派送单编号 
				columnWidth : .25
			}, {
				name : 'waybillNo',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.waybillNo'),  //运单号 
				columnWidth : .25
			},
			{
				name : 'vehicleNo',
				xtype : 'commontruckselector', 
				queryAllFlag : true,
				loopOrgCode : predeliver.fleetCode,
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.vehicleNo'),//车辆 
				columnWidth : .25
			},
			 {
				name : 'driverName',
				xtype : 'commondriverselector',
				fieldLabel:predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.driverName'),//司机姓名
//				parentOrgCode : 'W01140601',
//				waybillFlag:'N',
				columnWidth : .25
			}
			
//			{
//				name : 'vehicleNo',
//				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.vehicleNo'),  //车辆 
//				columnWidth : .25
//			}, {
//				name : 'driverName',
//				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.driverName'),  //司机姓 
//				columnWidth : .25
//			}
			, {
				xtype : 'combobox',
				name : 'deliverType',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliveryType'), //派车类型
				columnWidth : .25,
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : '',
				store : Ext.create('Foss.predeliver.deliverbillnew.DeliverTypeNewStore')
			},{
				xtype : 'combobox',
				name : 'status',
				fieldLabel :predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.status'),  //派送单状态  
				columnWidth : .25,
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value:'ALL',
				store : FossDataDictionary.getDataDictionaryStore('PKP_DELIVERBILL_STATUS',null,{
					'valueCode': 'ALL',
		            'valueName': predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.all')
				})
			}, {
				xtype : 'rangeDateField',
				fieldId : 'predeliver_deliverbillnew_submitTime',
				fieldLabel :predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTime'),   //创建时间 
				dateType : 'datetimefield_date97',
				fromName : 'submitTimeBegin',
				toName : 'submitTimeEnd',
				fromValue : Ext.Date.format(predeliver.deliverbillnew.getDate(new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
				toValue : Ext.Date.format(predeliver.deliverbillnew.getDate(new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
				columnWidth : .5,
				disallowBlank : true,
				editable : false
			}, {
				xtype : 'rangeDateField',
				fieldId : 'predeliver_deliverbillnew_loadEndTime',
				fieldLabel :predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.loadEndTime'), //装车完成时间
				dateType : 'datetimefield_date97',
				fromName : 'loadTimeBegin',
				toName : 'loadTimeEnd',
				fromValue : null,
				toValue : null,
//				fromValue :  Ext.Date.format(predeliver.deliverbillnew.getDate(new Date(), 0, 0, 0, 0), 'Y-m-d H:i:s'),
//				toValue : Ext.Date.format(predeliver.deliverbillnew.getDate(new Date(), 23, 59, 59, 999), 'Y-m-d H:i:s'),
				columnWidth : .5,
				disallowBlank : false,
				editable : false
			}, {
				xtype : 'commonbigzoneselector',
				name : 'deliverLargeArea',
				regionType : 'DE',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverLargeArea'),  //送货大区
				columnWidth : .25
			},{
				xtype : 'commonsmallzoneselector',
				name : 'deliverSmallArea',
				regionType : 'DE',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverSmallArea'),  //送货小区
				columnWidth : .25
			},{
				xtype : 'combobox',
				name : 'vehicleTypeTermscode',
				fieldLabel :predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.vehicleTypeTermscode'),  //车辆归属类型
				columnWidth : .25,
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				value : '',
				editable : false,
				store : Ext.create('Foss.predeliver.deliverbillnew.vehicleTypeTermscode')
			},{		
				name: 'deliverDate', 
				xtype: 'datefield',
				fieldLabel: predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverDate'), //预计送货日期
				labelWidth:85,
				format:'Y-m-d',
				columnWidth: .25,
				align: 'center'
			},{
				border : false,
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaults : {
					margin : '5 0 5 0'
				},
				items : [{
					xtype : 'button',
					columnWidth : .08,
					text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.reset'), //重置 
					handler : function() {
						this.up('form').getForm().reset();

						this.up('form').getForm().setValues({
//							'submitTimeBegin' : Ext.Date.format(predeliver.deliverbillnew.getDate(new Date(),0, 0, 0, 0), 'Y-m-d H:i:s'),
//							'submitTimeEnd' : Ext.Date.format(predeliver.deliverbillnew.getDate(new Date(),23, 59, 59, 999), 'Y-m-d H:i:s')
							'loadTimeBegin' : null,
							'loadTimeEnd' : null
						});
						this.up('form').getForm().setValues({
							'submitTimeBegin' : Ext.Date.format(
									predeliver.deliverbillnew.getDate(new Date(),0, 0, 0, 0), 'Y-m-d H:i:s'),
							'submitTimeEnd' : Ext.Date.format(
									predeliver.deliverbillnew.getDate(new Date(),23, 59, 59, 999), 'Y-m-d H:i:s')
						});
					}
				}, {
					border : false,
					columnWidth : .84,
					html : '&nbsp;'
				}, {
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.query'), //查询 
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexqueryButton'),
					hidden:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexqueryButton'),
					handler : function() {
						predeliver.deliverbillnew.deliverbillNewGrid.getPagingToolbar().moveFirst();
					}
				}]
			}]
		});


//定义一个派送单的表格
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillNewGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverbillHistory'),  //派送单历史 
	emptyText : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.emptyText'), //查询结果为空 
	height:500,
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	viewConfig : {
		enableTextSelection : true
	},
	columns : [{
				dataIndex : 'id',
				align : 'center',
				hidden : true
			}, {
				xtype : 'actioncolumn',
				width:125,
				text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverbillOperation'), //派送单操作
				align : 'center',
				items : [{
					//查看派送单明细
					iconCls: 'deppon_icons_showdetail',
					//tooltip标准的快捷提示实现，用于悬浮在目标元素之上时出现的提示信息。
					tooltip: predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.showdetail'),//查看,
					handler: function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						var deliverId = selection.get('id');
						
						Ext.Ajax.request({
							url:predeliver.realPath('queryDeliverbillDetailsList.action'),
							params:{'deliverbillVo.deliverbillDto.id':deliverId},
							success:function(response){
								var result = Ext.decode(response.responseText);	
								predeliver.deliverbillnew.DeliverbillDetailsGrid.store.loadData(result.deliverbillVo.deliverbillDetailList);
								var queryWin = Ext.create('Foss.predeliver.deliverbillnew.QueryDeliverDetailsWindow').show();
							}
						});
					}
				},{
					iconCls : 'deppon_icons_edit',
					tooltip : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.edit'), //编辑 
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexeditButton'),
					handler : function(grid, rowIndex, colIndex) {
						var status = grid.getStore().getAt(rowIndex).get('status');

						if (status == 'CANCELED') {
							//派送单已取消，不能编辑
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.statusCanceled'), 'error',
									4000);
							return;
						}					
						if (status == 'PDA_DOWNLOADED') {
							//派送单已下拉，不能编辑
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.statusPdaDownLoaded'), 'error',
									4000);
							return;
						}
						
						if (status == 'SIGNINFO_CONFIRMED') {
							//派送单已签收确认，不能编辑
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.statusSignInfoConfirmed'), 'error',
									4000);
							return;
						}
						if (status == 'CONFIRMED') {
							//派送单已确认，不能编辑
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.statusConfirmedNotOperate'), 'error',
									4000);
							return;
						}
						
            //predeliver.realPath('editDeliverbillNewIndex.action?fullScreen=true&vo.deliverbill.id=')+ grid.getStore().getAt(rowIndex).get('id')+
						addTab(
								'T_predeliver-editDeliverbillNewIndex',
								predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.editDelicerBill'),//编辑派送单 ContextPath.PKP_DELIVER + '/predeliver
								'/pkp-predeliver-web/predeliver/editDeliverbillNewIndex.action?fullScreen=true&vo.deliverbill.id='+ grid.getStore().getAt(rowIndex).get('id')+'&vo.deliverbill.status='+ grid.getStore().getAt(rowIndex).get('status'));
					}
				}, {
					iconCls : 'deppon_icons_dispose',
					tooltip : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.determineDelicerBill'), //确认派送单 
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexdetermineDelicerBillButton'),
					handler : function(grid, rowIndex, colIndex) {
						var status = grid.getStore().getAt(rowIndex).get('status');
						var selection = grid.getStore().getAt(rowIndex);
						var	deliverbillId = selection.get('id');
						if (status == 'CONFIRMED'||status == 'PDA_DOWNLOADED'||status == 'SIGNINFO_CONFIRMED') {
							//此派送单已确认，不能操作
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.statusConfirmedNotOperate'), 'error',4000); 
							return;
						}

						if (status != 'LOADED') {
							//派送单未装车，不能操作
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.statusNoLoaded'), 'error',4000); 
							return;
						}
						//306548在确认派送单时，当状态为“已装车”时发送短信通知客户
//						if(status == 'LOADED'){
//							Ext.Ajax.request({
//								url : predeliver.realPath('isSendSMSToConsumer.action'),//306548在确认派送单时，当状态为“已装车”时发送短信通知客户
//								params : {
//									'deliverbillVo.deliverbillNewDto.id' : deliverbillId
//								},
//								success : function(response) {
//									var result = Ext.decode(response.responseText);
//									//通知已发送！
//									Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.noticeHasSent')); 
//								},
//								error : function(response) {
//									var result = Ext.decode(response.responseText);
//									Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
//								},
//								exception : function(response) {
//									var result = Ext.decode(response.responseText);
//									Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
//								}
//							});
//						}
						addTab(
								'T_predeliver-confirmDeliverbillIndex',
								predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.determineDelicerBill'),
								predeliver.realPath('confirmDeliverbillNewIndex.action?deliverbillVo.deliverbill.id=')+ grid.getStore().getAt(rowIndex).get('id')+"&deliverbillVo.deliverbill.createType=Y");
					}
				},{
					iconCls : 'foss_icons_bse_applyReturn',
					tooltip : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.cancelDelicerBill'), //取消派送单 
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexcancelButton'),
					handler : function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						var	deliverbillId = selection.get('id');
						var	deliverbillNo = selection.get('deliverbillNo');
						var status = selection.get('status');
						
						if (status != 'SAVED'&& status != 'SUBMITED' && status != 'LOADED') {
							//此派送单当前状态不是已保存、已提交、已装车，不能进行取消！
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notBeCancel'), 'error',
									4000);
							return;
						}
						//确认取消派送单吗？
						Ext.Msg.confirm(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.confirmdCancellDeliverbill'),
						function(btn, text) {
							if (btn == "yes") {
								Ext.Ajax.request({
											url : predeliver.realPath('cancelDeliverbillNew.action'),
											params : {
												'deliverbillVo.deliverbillNewDto.id' : deliverbillId,
												'deliverbillVo.deliverbillNewDto.deliverbillNo' : deliverbillNo,
												'deliverbillVo.deliverbillNewDto.status':status
											},
											success : function(response) {
												var result = Ext.decode(response.responseText);
												Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message);
												predeliver.deliverbillnew.deliverbillNewGrid.store.load();
											},
											error : function(response) {
												var result = Ext.decode(response.responseText);
												Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',
																4000);
											},
											exception : function(response) {
												var result = Ext.decode(response.responseText);
												Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',
																4000);
											}
										});
							}
						});
					}
				},{
					iconCls : 'deppon_icons_notice',
					tooltip : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notice'), //通知司机
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexnoticeButton'),
					getClass : function(object,metadata,record,rowIndex,colIndex,store)
					{
						// 状态
						var status =FossDataDictionary.rendererDisplayToSubmit(record.get('status'), 'PKP_DELIVERBILL_STATUS');
						
						//是否通知了司机 
						var isSendSMS=record.get('isSendSMS');
						if (status=='ASSIGNED' || status=='CONFIRMED' || status=='SUBMITED' || status=='LOADED' || status=='LOADING') {
							if (isSendSMS == '1') {
								//通知成功Class
								this.items[4].tooltip = predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.noticeOk');
								return 'foss_icons_pkp_noticeOk';
							}else{
								//通知司机Class
								this.items[4].tooltip =  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notice');
								return 'deppon_icons_notice';
							}
						}else {//不可通知Class
							this.items[4].tooltip = predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.noticeHide');
							return 'deppon_icons_noticeHide';
						}
					},
					handler: function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						var	status = selection.get('status');
						var	isSendSMS = selection.get('isSendSMS');
						var	deliverbillId = selection.get('id');
						var	deliverbillNo = selection.get('deliverbillNo');
						var	driverCode = selection.get('driverCode');
						//可以通知司机的状态并且未通知 
						if (isSendSMS != '1' && (status=='ASSIGNED' || status=='CONFIRMED' || status=='SUBMITED' || status=='LOADED' || status=='LOADING')){
							
								Ext.Ajax.request({
									url : predeliver.realPath('isSendSMSToDriver.action'),
									params : {
										'deliverbillVo.deliverbillNewDto.id' : deliverbillId
									},
									success : function(response) {
										var result = Ext.decode(response.responseText);
										//通知已发送！
										Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.noticeHasSent')); 
										predeliver.deliverbillnew.deliverbillNewGrid.store.load();
									},
									error : function(response) {
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
									},
									exception : function(response) {
										var result = Ext.decode(response.responseText);
										Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error',4000);
									}
								});
								
							
							
						}
						
					}
				}]
			}, {
				xtype : 'actioncolumn',
				align : 'center',
				width : 50,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.printDeliverbill'), //打印派送单 
				items : [{
					iconCls : 'deppon_icons_print',
					tooltip : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.printDeliverbill'), //打印派送单 
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexprintdeliverbillButton'),
					handler : function(grid, rowIndex, colIndex) {
						// 打印派送单
						var selection = grid.getStore().getAt(rowIndex), deliverbillId = selection.get('id'),status = selection.get('status');
						
						// 获取打印的派送单基本信息
						Ext.Ajax.request({
							url : predeliver.realPath('queryDeliverbillNew.action'),
							params : {
								'deliverbillVo.deliverbillNewDto.id' : deliverbillId
							},
							success : function(response) {
								//如果为派送单
								if (status == 'CONFIRMED' || status == 'PDA_DOWNLOADED' || status == 'SIGNINFO_CONFIRMED') {
									var printWin = Ext.create('Foss.predeliver.deliverbill.PrintDeliverbillNewWindow',{'deliverbillId' : deliverbillId});
									var result = Ext.decode(response.responseText), model = Ext.ModelManager.create(result.deliverbillVo.deliverbill,'Foss.predeliver.deliverbill.PrintNewModel');
									printWin.getDeliverPrintHeadInfo().loadRecord(model);
									printWin.getDeliverPrintBottomInfo().loadRecord(model);
									printWin.show();
									printWin.getDeliverPrintGrid().store.setDeliverbillId(deliverbillId);
									printWin.getDeliverPrintGrid().getPagingToolbar().moveFirst();
									
								}else{
									var printWin = Ext.create('Foss.predeliver.deliverbill.PrintDeliverbillWindow',{'deliverbillId' : deliverbillId});
									var result = Ext.decode(response.responseText), model = Ext.ModelManager.create(result.deliverbillVo.deliverbill,'Foss.predeliver.deliverbill.PrintModel');
									printWin.getDeliverPrintHeadInfo().loadRecord(model);
									printWin.getDeliverPrintBottomInfo().loadRecord(model);
									printWin.show();
									printWin.getDeliverPrintGrid().store.setDeliverbillId(deliverbillId);
									printWin.getDeliverPrintGrid().getPagingToolbar().moveFirst();
								}
								
							}
						});
					}
				}]
			}, {
				xtype : 'actioncolumn',
				align : 'center',
				width : 50,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.printArriveSheet'), //打印到达联
				items : [{
					iconCls : 'deppon_icons_print',
					tooltip : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.printArriveSheet'), //打印到达联
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexprintarrivesheetButton'),
					getClass : function(object,metadata,record,rowIndex,colIndex,store)
					{
						//是否已经打印到达联
						var isArriveSheet=record.get('isArriveSheet');
						if (isArriveSheet=='Y') {
							return 'deppon_Sicons_print';
						}else {
							//没有通知Class
							return 'deppon_icons_print';
						}
					},
					handler : function(grid, rowIndex, colIndex) {
						//获取当前派送单状态 
						var status = grid.getStore().getAt(rowIndex).get('status');
						//根据派送单状态选择打印达联模板
						//如果为派送单
						if (status == 'CONFIRMED' || status == 'PDA_DOWNLOADED' || status == 'SIGNINFO_CONFIRMED') {
							// 打印到达联
							var printArrivesheetWindow = Ext.getCmp('T_predeliver-deliverbillnewIndex_content').getPrintArrivesheetTwoWindow();
							var deliverbillId = grid.getStore().getAt(rowIndex).get('id');
							printArrivesheetWindow.getDeliverbillDetailGrid().store.setDeliverbillId(deliverbillId);
							printArrivesheetWindow.getDeliverbillDetailGrid().store.setStatus(status);
							printArrivesheetWindow.getDeliverbillDetailGrid().store.load();
							Ext.Ajax.request({
										url : predeliver.realPath('queryDeliverbillNewForArriveSheet.action'),
										params : {
											'deliverbillVo.deliverbillNewDto.id' : deliverbillId
										},
										success : function(response) {
											var result = Ext.decode(response.responseText);
											var deliverbill = Ext.create('Foss.predeliver.deliverbillnew.DeliverbillModel',result.deliverbillVo.deliverbill);
											printArrivesheetWindow.getDeliverbillBasicInfoForm().loadRecord(deliverbill);
											predeliver.deliverbillnew.isArriveSheetDeliverbillId=deliverbillId;
										}
									});
							printArrivesheetWindow.show();
						
						//否则为预派送单
						}else{
							var printArrivesheetWindow = Ext.getCmp('T_predeliver-deliverbillnewIndex_content').getPrintArrivesheetWindow();
							var deliverbillId = grid.getStore().getAt(rowIndex).get('id');
							printArrivesheetWindow.getDeliverbillDetailGrid().store.setDeliverbillId(deliverbillId);
							printArrivesheetWindow.getDeliverbillDetailGrid().store.setStatus(status);
							printArrivesheetWindow.getDeliverbillDetailGrid().store.load();
							Ext.Ajax.request({
								url : predeliver.realPath('queryDeliverbillNewForArriveSheet.action'),
								params : {
									'deliverbillVo.deliverbillNewDto.id' : deliverbillId
								},
								success : function(response) {
									var result = Ext.decode(response.responseText);
									var deliverbill = Ext.create('Foss.predeliver.deliverbillnew.DeliverbillModel',result.deliverbillVo.deliverbill);
									printArrivesheetWindow.getDeliverbillBasicInfoForm().loadRecord(deliverbill);
									predeliver.deliverbillnew.isArriveSheetDeliverbillId=deliverbillId;
								}
							});

							printArrivesheetWindow.show();
						}
						
						
						
					}
				}]
			}, {
				xtype : 'actioncolumn',
				align : 'center',
				width : 50,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.assignTask'), //分配任务
				items : [{
					iconCls : 'foss_icons_pkp_allottask',
					tooltip : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.assignTask'), //分配任务
					disabled:!predeliver.deliverbillnew.isPermission('deliverbillnewIndex/deliverbillnewIndexassignTaskButton'),
					handler : function(grid, rowIndex, colIndex) {
						// 分配任务给司机
						var status = grid.getStore().getAt(rowIndex).get('status');

						if (status == 'CANCELED') {
							//派送单已取消，不能操作
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.statusCanceledNotOperate') , 'error',
									4000);
							return;
						}

						var selectDriverWindow = Ext.getCmp('T_predeliver-deliverbillnewIndex_content').getSelectDriverWindow();

						selectDriverWindow.setDeliverbillId(grid.getStore().getAt(rowIndex).get('id'));

						var vehicleNo = grid.getStore().getAt(rowIndex).get('vehicleNo');
						if (vehicleNo != null) {
							Ext.Ajax.request({
								url : predeliver.realPath('queryNewVehiclePdaSigned.action'),
								params : {
									'deliverbillVo.deliverbillNewDto.vehicleNo' : vehicleNo
								},
								success : function(response) {
									var result = Ext.decode(response.responseText);
									var driver = result.deliverbillVo.driverDto;
									if (driver != null) {
										if (driver.pdaSigned == 'Y') {
											// 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改
											//当前车牌号已经存在绑定关系，不能进行再次分配!
											Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notBeReallocation'),
													'ok', 3000);
										} else {
											selectDriverWindow.show();
										}
									} else {
										selectDriverWindow.show();
									}
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), json.message,'error', 3000);
								}
							});
						}
					}
				}]
			}, {
				dataIndex : 'deliverbillNo',
				align : 'center',
				width : 90,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverbillNo') //派送单编号  
			},{
				dataIndex : 'status',
				align : 'center',
				width : 90,
				renderer : function(value) {
					var displayedValue = FossDataDictionary.rendererSubmitToDisplay(value,'PKP_DELIVERBILL_STATUS');
					return displayedValue;
				},
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.status') //派送单状态  
			}, {
				dataIndex : 'vehicleNo',
				align : 'center',
				width : 70,
				header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.vehicleNo') //车辆  
			}, {
				dataIndex : 'driverName',
				align : 'center',
				width : 80,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.driverName') //司机姓名  
			}, {
				dataIndex : 'driverTel',
				align : 'center',
				width : 100,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.driverTel') //司机电话 号码
			},{
				dataIndex : 'loadEndTime',
				align:'center',
				width : 130,
				renderer : function(value) {
					if (value != null) {
						var date = Ext.Date.format(new Date(
								value), 'Y-m-d H:i:s');
						return date;
					} else {
						return null;
					}
				},
				header: predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.loadEndTime') //装车完成时间 
			},{
				dataIndex : 'departTime',
				align:'center',
				width : 80,
				renderer : function(value) {
					if (value != null) {
						var date = Ext.Date.format(new Date(
								value), 'Y-m-d H:i:s');
						return date;
					} else {
						return null;
					}
				},
				header: predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.departTime') //出车时间 
			}, {
				dataIndex : 'payAmountTotal',
				align : 'center',
				width : 70,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.payAmountTotal') //到付金额  
			}, {
				dataIndex : 'deliverType',
				align : 'center',
				width : 70,
				renderer:function(value){
					if(value=='NOMAL'){
						return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.nomal'); //正常
					}else if(value=='SPECIAL'){
						return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.special'); //专车
					}else if(value=='MANNED'){
						return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.manned'); //带人
					}
				},
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliveryType') //派车类型 
			},{
				dataIndex : 'receiveCustomerDistCode',
				align : 'center',
				width : 100,
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.receiveCustomerDistCode') //所属区域 
			},{
				xtype:'templatecolumn',
				align : 'center',
				width : 80,
				tpl : '{truckModel}/{frequencyNo}',
				header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.truckModelFrequencyNo') //车型/班次 
			},{
				dataIndex : 'deliverDate',
				align:'center',
				width : 95,
				renderer : function(value) {
					if (value != null) {
						var date = Ext.Date.format(new Date(
								value), 'Y-m-d');
						return date;
					} else {
						return null;
					}
				},
				header: predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverDate') //预计送货日期 
			},{
				dataIndex : 'totalGoodsQty',
				align : 'center',
				width : 70,
				header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.totalGoodsQty') //排单票数 
			},{
				xtype:'templatecolumn',
				tpl:'{weightTotal}/{volumeTotal}',
				align : 'center',
				width : 100,
				header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.weightTotalVolumeTotal') //排单重量/体积
			},{
				dataIndex : 'loadingRate',
				align : 'center',
				width : 130,
				header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.loadingRate') //装载率
			},{
				dataIndex : 'createUserName',
				align : 'center',
				width : 70,
				header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.createUserName') //创建人
			}, {
				dataIndex : 'submitTime',
				align : 'center',
				width : 135,
				header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTime'), //创建时间  
				renderer : function(value) {
					if (value != null) {
						var date = Ext.Date.format(new Date(value),'Y-m-d H:i:s');
						return date;
					} else {
						return null;
					}
				}
			}],
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {store : this.store,plugins: 'pagesizeplugin',displayInfo: true});
		}
		return this.pagingToolbar;
	},constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.deliverbillnew.DeliverbillStore');
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			defaults:{
				margin:'0 0 5 3',
				allowBlank:true
			},							
			items: [{
					xtype : 'container',
					border : false,
					columnWidth : .77,
					html : '&nbsp;'
			},{
				xtype:'button',
				allowBlank:true,
				name:'exportDeliverbillDetail',
				columnWidth:.13,
				text: predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.exportDeliverbillDetail'),//导出派送单明细
				handler : function(){
				//获取查询出来的异常信息
				var queryDeliverbillNewForm = predeliver.deliverbillnew.queryDeliverbillNewForm;
				if (!predeliver.deliverbillnew.queryDeliverbillNewForm.getForm().isValid()) {
					//创建时间不能为空
					Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.creatTimeIsNotAllowNull'),'error', 4000); 
					return ;
				}
				// 时间验证
				var submitTimeBegin = queryDeliverbillNewForm.getValues().submitTimeBegin, submitTimeEnd = queryDeliverbillNewForm.getValues().submitTimeEnd;
				if (!Ext.isEmpty(submitTimeBegin) && !Ext.isEmpty(submitTimeEnd)) {	
					var result = Ext.Date.parse(submitTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(submitTimeBegin,'Y-m-d H:i:s');	
					if(result / (24 * 60 * 60 * 1000) >= 30){	
						//起止日期相隔不能超过30天
						Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateIntervalError'),'error', 4000); 
						return ;
					}	
				}else {
					//创建时间不能为空
					Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.creatTimeIsNotAllowNull'),'error', 4000); 

					return ;
				}
				
				var loadTimeBegin = queryDeliverbillNewForm.getValues().loadTimeBegin, loadTimeEnd = queryDeliverbillNewForm.getValues().loadTimeEnd;
				if (!Ext.isEmpty(loadTimeBegin) && !Ext.isEmpty(loadTimeEnd)) {	
					var result1 = Ext.Date.parse(loadTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(loadTimeBegin,'Y-m-d H:i:s');	
					if(result1 / (24 * 60 * 60 * 1000) >= 30){	
						//装车完成起止日期不能超过30天
						Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateFinishLoadingIntervalError'),'error', 4000); 
						return false;
					}	
				}
				if((!Ext.isEmpty(loadTimeBegin) && Ext.isEmpty(loadTimeEnd))||(!Ext.isEmpty(loadTimeEnd) && Ext.isEmpty(loadTimeBegin))){
					//装车完成起止日期不能超过30天
					Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateFinishLoadingIntervalError'),'error', 4000); 
						return false;
				}
				if (queryDeliverbillNewForm != null) {
					var queryParams = queryDeliverbillNewForm.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}
					//获取查询出来的异常信息
					var deliverGridStore = predeliver.deliverbillnew.deliverbillNewGrid.getStore();	
					var driverName	=queryDeliverbillNewForm.down('commondriverselector[name=driverName]').getRawValue();
					//若异常信息不为空
					if(deliverGridStore.getCount()!=0){
					Ext.Ajax.request({
						url:predeliver.realPath('exportDeliverbillNewDetail.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {
									'deliverbillNewVo.deliverbillNewDto.deliverbillNo' : queryParams.deliverbillNo,
									'deliverbillNewVo.deliverbillNewDto.waybillNo' : queryParams.waybillNo,
									'deliverbillNewVo.deliverbillNewDto.vehicleNo' : queryParams.vehicleNo,
									'deliverbillNewVo.deliverbillNewDto.driverName' : driverName,
									'deliverbillNewVo.deliverbillNewDto.deliverType' : queryParams.deliverType,
									'deliverbillNewVo.deliverbillNewDto.status' : queryParams.status,
									'deliverbillNewVo.deliverbillNewDto.submitTimeBegin' : queryParams.submitTimeBegin,
									'deliverbillNewVo.deliverbillNewDto.submitTimeEnd' : queryParams.submitTimeEnd,
									'deliverbillNewVo.deliverbillNewDto.loadTimeBegin' : queryParams.loadTimeBegin,
									'deliverbillNewVo.deliverbillNewDto.loadTimeEnd' : queryParams.loadTimeEnd,
									'deliverbillNewVo.deliverbillNewDto.deliverLargeArea' : queryParams.deliverLargeArea,
									'deliverbillNewVo.deliverbillNewDto.deliverSmallArea' : queryParams.deliverSmallArea,
									'deliverbillNewVo.deliverbillNewDto.vehicleTypeTermscode' : queryParams.vehicleTypeTermscode,
									'deliverbillNewVo.deliverbillNewDto.deliverDate' : queryParams.deliverDate
								},
						isUpload: true
						});
					}
				}else {
					//或者提示不能导出
					Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notExport'), 'error', 3000);
				}
			}
		},{
					xtype:'button',
					allowBlank:true,
					name:'exportDeliverbill',
					columnWidth:.1,
					text:predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.exportDeliverbill'),//导出派送单
					handler : function(){

					var queryDeliverbillNewForm = predeliver.deliverbillnew.queryDeliverbillNewForm;
				
					var loadTimeBegin = queryDeliverbillNewForm.getValues().loadTimeBegin, loadTimeEnd = queryDeliverbillNewForm.getValues().loadTimeEnd;
					if (!Ext.isEmpty(loadTimeBegin) && !Ext.isEmpty(loadTimeEnd)) {	
						var result1 = Ext.Date.parse(loadTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(loadTimeBegin,'Y-m-d H:i:s');	
						if(result1 / (24 * 60 * 60 * 1000) >= 30){	
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateFinishLoadingIntervalError'),'error', 4000);
							return false;
						}	
					}
					if((!Ext.isEmpty(loadTimeBegin) && Ext.isEmpty(loadTimeEnd))||(!Ext.isEmpty(loadTimeEnd) && Ext.isEmpty(loadTimeBegin))){
						Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateFinishLoadingIntervalError'),'error', 4000);
							return false;
					}
					if (queryDeliverbillNewForm != null) {
						var queryParams = queryDeliverbillNewForm.getValues();
						if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
						}	
						//获取查询出来的异常信息
						var deliverGridStore = predeliver.deliverbillnew.deliverbillNewGrid.getStore();	
						var driverName	=queryDeliverbillNewForm.down('commondriverselector[name=driverName]').getRawValue();
						//若异常信息不为空
						if(deliverGridStore.getCount()!=0){
							Ext.Ajax.request({
								url:predeliver.realPath('exportDeliverbillNew.action'),
								form: Ext.fly('downloadAttachFileForm'),
								method : 'POST',
								params : {
									'deliverbillNewVo.deliverbillNewDto.deliverbillNo' : queryParams.deliverbillNo,
									'deliverbillNewVo.deliverbillNewDto.waybillNo' : queryParams.waybillNo,
									'deliverbillNewVo.deliverbillNewDto.vehicleNo' : queryParams.vehicleNo,
									'deliverbillNewVo.deliverbillNewDto.driverName' : driverName,
									'deliverbillNewVo.deliverbillNewDto.deliverType' : queryParams.deliverType,
									'deliverbillNewVo.deliverbillNewDto.status' : queryParams.status,
									'deliverbillNewVo.deliverbillNewDto.submitTimeBegin' : queryParams.submitTimeBegin,
									'deliverbillNewVo.deliverbillNewDto.submitTimeEnd' : queryParams.submitTimeEnd,
									'deliverbillNewVo.deliverbillNewDto.loadTimeBegin' : queryParams.loadTimeBegin,
									'deliverbillNewVo.deliverbillNewDto.loadTimeEnd' : queryParams.loadTimeEnd,
									'deliverbillNewVo.deliverbillNewDto.deliverLargeArea' : queryParams.deliverLargeArea,
									'deliverbillNewVo.deliverbillNewDto.deliverSmallArea' : queryParams.deliverSmallArea,
									'deliverbillNewVo.deliverbillNewDto.vehicleTypeTermscode' : queryParams.vehicleTypeTermscode,
									'deliverbillNewVo.deliverbillNewDto.deliverDate' : queryParams.deliverDate
								},
								isUpload: true
							});
						}else{
							//或者提示不能导出
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notExport'), 'error', 3000);
						}
					}
				}
			}]	
		},{
			xtype: 'toolbar',
			dock: 'bottom',
			layout:'column',
			defaults:{
				margin:'0 0 5 3',
				allowBlank:true
			}
		}],
		me.bbar = me.getPagingToolbar();
		me.callParent([ cfg ]);
	}
});


//定义预派送单明细（待排运单复用）的模型
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailsModel',
				{
					extend : 'Ext.data.Model',
					// 定义字段
					fields : [
							{
								name : 'id',
								type : 'string'
							},
							{
								name : 'tSrvDeliverbillId',
								type : 'string'
							},
							{
								name : 'serialNo', // 派送单明细序列号，仅用于派送单明细Model
								type : 'int'
							},
							{
								name : 'waybillNo', // 运单号
								type : 'string'
							},
							{
								name : 'goodsName', // 货物名称
								type : 'string'
							},
							{
								name : 'waybillGoodsQty', // 对于派送单明细，为排单货物件数；对于待排运单，为待排运单整票货物件数。
								type : 'int'
							},
							{
								name : 'arrangableGoodsQty', // 可排单件数，仅用于待排运单Model
								type : 'int'
							},
							{
								name : 'arrangeGoodsQty', // 排单件数
								type : 'int'
							},
							{
								name : 'preArrangeGoodsQty', // 预排单件数。对于待排运单，其初始值为可排单件数
								type : 'int'
							},
							{
								name : 'weight', // 对于派送单明细，为排单重量；对于待排运单，为待排运单整票货物重量。
								type : 'number'
							},
							{
								name : 'arrangedWeight', // 待排运单的排单重量
								type : 'number',
								convert : function(v, record) {
									return Ext.util.Format
											.round(
													record.data.arrangableGoodsQty > 0 ? record.data.weight
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
															: record.data.weight,
													2);
								}
							},
							{
								name : 'dimension', // 运单尺寸
								type : 'string'
							},
							{
								name : 'goodsVolumeTotal', // 对于派送单明细，为排单体积；对于待排运单，为待排运单整票货物体积。
								type : 'number'
							},
							{
								name : 'arrangedGoodsVolume', // 待排运单的排单体积
								type : 'number',
								convert : function(v, record) {
									return Ext.util.Format
											.round(
													record.data.arrangableGoodsQty > 0 ? record.data.goodsVolumeTotal
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
															: record.data.goodsVolumeTotal,
													2);
								}
							}, {
								name : 'transportType', // 运输方式
								type : 'string'
							}, {
								name : 'arriveTime', // 到达时间
								type : 'date',
								convert : dateConvert
							}, {
								name : 'consignee', // 收货人
								type : 'string'
							}, {
								name : 'consigneeContact', // 联系方式
								type : 'string'
							}, {
								name : 'consigneeAddress', // 送货地址
								type : 'string'
							}, {
								name : 'deliverRequire', // 送货要求
								type : 'string'
							}, {
								name : 'goodsStatus', // 货物状态
								type : 'string'
							}, {
								name : 'isAlreadyContact', // 是否已联系客户
								type : 'string'
							}, {
								name : 'estimatedDeliverTime', // 预计送货时间
								type : 'date',
								convert : dateConvert
							}, {
								name : 'isException', // 是否异常
								type : 'string'
							}, {
								name : 'isNeedInvoice', // 是否需要发票
								type : 'string'
							}, {
								name : 'paymentType', // 付款方式
								type : 'string'
							}, {
								name : 'notes', // 备注
								type : 'string'
							}, {
								name : 'deliverType', // 送货方式
								type : 'string'
							}, {
								name : 'arrangeStatus', // 排单状态
								type : 'string'
							}, {
								name : 'payAmount', // 到付款
								type : 'number'
							}, {
								name : 'fastWaybillFlag', // 卡货标志
								type : 'int'
							}, {
								name : 'isSentRequired', // 是否必送货
								type : 'string'
							}, {
								name : 'stockGoodQty', // 提货网点库存
								type : 'int'
							}, {
								name : 'currencyCode', // 币种
								type : 'string'
							}, {
								name : 'returnBillType', // 返单类型
								type : 'string'
							}, {
								name : 'goodsPackage', // 包装
								type : 'string'
							} ]
				});	
//定义预派送单明细的Store
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailsStore',
				{
					extend : 'Ext.data.Store',
					// 绑定一个模型
					model : 'Foss.predeliver.deliverbillnew.DeliverbillDetailsModel',
					//是否自动查询
					autoLoad: false,
					proxy: {
				        type: 'memory',
				        reader: {
				            type: 'json',
				            root: 'items'
				        }
					}
				});

//定义派送单明细的表格
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailsGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverbillInfo'), // 排单信息
					emptyText : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.emptyText'), //查询结果为空,
					height : 500,
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					store: Ext.create('Foss.predeliver.deliverbillnew.DeliverbillDetailsStore'),	
					viewConfig :{
						//显示重复样式，不用隔行显示
					 	stripeRows: false,
					 	enableTextSelection: true
					},
					columns : [
							{
								dataIndex : 'id',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'stockGoodQty',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'waybillNo',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.waybillNo') //运单号
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsName') //货物名称
							},
							{
								dataIndex : 'weight',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.weight') //排单重量
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.dimension') //尺寸
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsVolumeTotal') //体积
							},
							{
								dataIndex : 'goodsPackage',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsPackage') //包装
							},
							{
								dataIndex : 'waybillGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.waybillGoodsQty') //件数
							},
							{
								dataIndex : 'arrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.arrangeGoodsQty') //排单件数
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.transportType'), //运输方式,
								renderer : function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}
							}, {
								dataIndex : 'arriveTime',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.arriveTime'), //到达时间,
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							}, {
								dataIndex : 'consignee',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consignee') //收货人
							}, {
								dataIndex : 'consigneeContact',
								align : 'center',
								xtype: 'ellipsiscolumn',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consigneeContact') //联系方式
							}, {
								dataIndex : 'consigneeAddress',
								align : 'center',
								xtype: 'ellipsiscolumn',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consigneeAddress') //送货地址
							}]
				});	
predeliver.deliverbillnew.DeliverbillDetailsGrid = Ext.create('Foss.predeliver.deliverbillnew.DeliverbillDetailsGrid');	
//定义查询详细派送单明细window
Ext.define('Foss.predeliver.deliverbillnew.QueryDeliverDetailsWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	modal : 'true',
	width : 1024,
	height : 600,
	items : [predeliver.deliverbillnew.DeliverbillDetailsGrid],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//定义派送单的模型
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			// 定义字段
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'id',
						type : 'string'
					}, {
						name : 'deliverbillNo',
						type : 'string'
					}, {
						name : 'status',
						type : 'string'
					}, {
						name : 'vehicleNo',
						type : 'string'
					}, {
						name : 'driverName',
						type : 'string'
					}, {
						name : 'driverTel',
						type : 'string'
					}, {
						name : 'isSendSMS',
						type : 'string'
					}, {
						name : 'isArriveSheet',
						type : 'string'
					}, {
						name : 'loadEndTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'departTime',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'payAmountTotal',
						type : 'string'
					}, {
						name : 'deliverType',
						type : 'string'
					}, {
						name : 'receiveCustomerDistCode',
						type : 'string'
					}, {
						name : 'truckModel',
						type : 'string'
					},{
						name : 'frequencyNo',
						type : 'string'
					},{
						name : 'transferCenter',
						type : 'string'
					}, {
						name : 'deliverDate',
						type : 'date',
						convert : dateConvert
					}, {
						name : 'totalGoodsQty',
						type : 'string'
					}, {
						name : 'weightTotal',
						type : 'string'
					}, {
						name : 'volumeTotal',
						type : 'string'
					}, {
						name : 'loadingRate',
						type : 'string'
					}, {
						name : 'createUserName',
						type : 'string'
					},{
						name:'submitTime',
						type : 'date',
						convert : dateConvert
					}]
		});

//定义派送单的Store
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillStore', {
	extend : 'Ext.data.Store',
	// autoLoad : true,
	// 绑定一个模型
	model : 'Foss.predeliver.deliverbillnew.DeliverbillModel',
	pageSize : 10,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : predeliver.realPath('queryDeliverbillNewList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverbillVo.deliverbillNewDtoList',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryDeliverbillForm = predeliver.deliverbillnew.queryDeliverbillNewForm;
			
			var submitTimeBegin = queryDeliverbillForm.getForm().getValues().submitTimeBegin;
			var submitTimeEnd = queryDeliverbillForm.getForm().getValues().submitTimeEnd;
			var result = Ext.Date.parse(submitTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(submitTimeBegin,'Y-m-d H:i:s');
			if(result / (24 * 60 * 60 * 1000) >= 30){
				Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateIntervalError'), 'error', 3000); //起止日期相隔不能超过30天！
				return false;
			}
			var loadTimeBegin = queryDeliverbillForm.getForm().getValues().loadTimeBegin, loadTimeEnd = queryDeliverbillForm.getForm().getValues().loadTimeEnd;
			if (!Ext.isEmpty(loadTimeBegin) && !Ext.isEmpty(loadTimeEnd)) {	
				var result1 = Ext.Date.parse(loadTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(loadTimeBegin,'Y-m-d H:i:s');	
				if(result1 / (24 * 60 * 60 * 1000) >= 30){
					Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateIntervalError'), 'error', 3000); //起止日期相隔不能超过30天！
					return false;
				}	
			}
			if((!Ext.isEmpty(loadTimeBegin) && Ext.isEmpty(loadTimeEnd))||(!Ext.isEmpty(loadTimeEnd) && Ext.isEmpty(loadTimeBegin))){
				Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.submitTimeDateFinishLoadingIntervalError'), //装车完成起止日期不能超过30天
							'error', 4000);
				return false;
			}
			if (!predeliver.deliverbillnew.queryDeliverbillNewForm.getForm().isValid()) {
				Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.creatTimeIsNotAllowNull'),  //创建时间不能为空
				'error', 4000);
				return false;
			}
			
			
			if (queryDeliverbillForm != null) {
				var queryParams = queryDeliverbillForm.getValues();
				var driverName	=queryDeliverbillForm.down('commondriverselector[name=driverName]').getRawValue();
				Ext.apply(operation, {
					params : {
						'deliverbillVo.deliverbillNewDto.deliverbillNo' : queryParams.deliverbillNo,
						'deliverbillVo.deliverbillNewDto.waybillNo' : queryParams.waybillNo,
						'deliverbillVo.deliverbillNewDto.vehicleNo' : queryParams.vehicleNo,
						'deliverbillVo.deliverbillNewDto.driverName' : driverName,
						'deliverbillVo.deliverbillNewDto.deliverType' : queryParams.deliverType,
						'deliverbillVo.deliverbillNewDto.status' : queryParams.status,
						'deliverbillVo.deliverbillNewDto.submitTimeBegin' : queryParams.submitTimeBegin,
						'deliverbillVo.deliverbillNewDto.submitTimeEnd' : queryParams.submitTimeEnd,
						'deliverbillVo.deliverbillNewDto.loadTimeBegin' : queryParams.loadTimeBegin,
						'deliverbillVo.deliverbillNewDto.loadTimeEnd' : queryParams.loadTimeEnd,
						'deliverbillVo.deliverbillNewDto.deliverLargeArea' : queryParams.deliverLargeArea,
						'deliverbillVo.deliverbillNewDto.deliverSmallArea' : queryParams.deliverSmallArea,
						'deliverbillVo.deliverbillNewDto.vehicleTypeTermscode' : queryParams.vehicleTypeTermscode,
						'deliverbillVo.deliverbillNewDto.deliverDate' : queryParams.deliverDate
					}
				});
			}
		}
	}
});

/** -----------------------------------------派送单 End----------------------------------------- */


/** -----------------------------------------打印到达联（预派送单） Begin----------------------------------------- */

//定义预派送单明细的模型
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'arrivesheetNo',
						type : 'string'
					}, {
						name : 'tSrvDeliverbillId',
						type : 'string'
					}, {
						name : 'serialNo', // 派送单明细序列号，仅用于派送单明细Model
						type : 'int'
					}, {
						name : 'waybillNo', // 运单号
						type : 'string'
					}, {
						name : 'goodsName', // 货物名称
						type : 'string'
					}, {
						name : 'waybillGoodsQty', // 对于派送单明细，为排单货物件数；对于待排运单，为待排运单整票货物件数。
						type : 'int'
					}, {
						name : 'arrangableGoodsQty', // 可排单件数，仅用于待排运单Model
						type : 'int'
					}, {
						name : 'arrangeGoodsQty', // 排单件数
						type : 'int'
					}, {
						name : 'preArrangeGoodsQty', // 预排单件数。对于待排运单，其初始值为可排单件数
						type : 'int'
					}, {
						name : 'weight', // 对于派送单明细，为排单重量；对于待排运单，为待排运单整票货物重量。
						type : 'number'
					}, {
						name : 'arrangedWeight', // 待排运单的排单重量
						type : 'number',
						convert : function(v, record) {
							return Ext.util.Format
									.round(
											record.data.arrangableGoodsQty > 0
													? record.data.weight
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
													: record.data.weight, 2);
						}
					}, {
						name : 'dimension', // 运单体积 
						type : 'string'
					}, {
						name : 'goodsVolumeTotal', // 对于派送单明细，为排单体积；对于待排运单，为待排运单整票货物体积。
						type : 'number'
					}, {
						name : 'arrangedGoodsVolume', // 待排运单的排单体积
						type : 'number',
						convert : function(v, record) {
							return Ext.util.Format
									.round(
											record.data.arrangableGoodsQty > 0
													? record.data.goodsVolumeTotal
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
													: record.data.goodsVolumeTotal,
											2);
						}
					}, {
						name : 'transportType',
						convert:function(value) {
							return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
						}//运输性质
					}, {
						name : 'arriveTime', // 到达时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'consignee', // 收货人
						type : 'string'
					}, {
						name : 'consigneeContact', // 联系方式
						type : 'string'
					}, {
						name : 'consigneeAddress', // 收货地址
						type : 'string'
					}, {
						name : 'deliverRequire', // 送货要求
						type : 'string'
					}, {
						name : 'goodsStatus', // 货物状态
						type : 'string'
					}, {
						name : 'isAlreadyContact', // 是否已联系客户
						type : 'string'
					}, {
						name : 'estimatedDeliverTime', // 预计送货时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'isException', // 是否异常
						type : 'string'
					}, {
						name : 'isNeedInvoice', // 是否需要发票
						type : 'string'
					}, {
						name : 'paymentType', // 付款方式
						type : 'string'
					}, {
						name : 'notes', // 备注
						type : 'string'
					}, {
						name : 'deliverType', // 送货方式
						type : 'string'
					}, {
						name : 'arrangeStatus', // 排单状态
						type : 'string'
					}, {
						name : 'waybillrfcStatus', // 更改状态
						type : 'string'
					}, {
						name : 'payAmount', // 到付款
						type : 'number'
					}, {
						name : 'fastWaybillFlag', // 卡货标志
						type : 'int'
					}, {
						name : 'isSentRequired', // 是否必送货
						type : 'string'
					}, {
						name : 'stockGoodQty', // 提货网点库存
						type : 'int'
					},{
						name : 'printtimes', // 打印次数
						type : 'int'
					}]
		});

//定义派送单明细的Store
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailStore', {
			extend : 'Ext.data.Store',
			autoLoad : false,
			// 绑定一个模型
			model : 'Foss.predeliver.deliverbillnew.DeliverbillDetailModel',
			// 定义一个代理对象
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : predeliver
						.realPath('queryDeliverbillNewArrivesheetList.action'),
				// 定义一个读取器
				reader : {
					// 以JSON的方式读取
					type : 'json',
					// 定义读取JSON数据的根对象
					root : 'deliverbillVo.deliverbillDetailList'
				}
			},
			deliverbillId : '',
			setDeliverbillId : function(deliverbillId) {
				this.deliverbillId = deliverbillId;
			},
			getDeliverbillId : function() {
				return this.deliverbillId;
			},
			status : '',
			setStatus : function(status){
				this.status = status;
			},
			getStatus : function(){
				return this.status;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					Ext.apply(operation, {
								params : {
									'deliverbillVo.deliverbillNewDto.id' : store.getDeliverbillId(),
									'deliverbillVo.deliverbillNewDto.status' : store.getStatus()
								}
							});
				}
			}
		});

//定义派送单明细的表格
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailGrid', {
			extend : 'Ext.grid.Panel',
			frame : true,
			title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.arriveSheet'), // 到达联
			emptyText :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.emptyText'), // 查询结果为空
			height : 300,
			autoScroll : true,
			collapsible : true,
			animCollapse : true,
			printWindow : null,
			getPrintWindow : function() {
				var me = this;
				if (this.printWindow == null) {
					me.printWindow = Ext.create(
							'Foss.printArriveSheet.printOneWindow', me, 'Y');
				}
				return me.printWindow;
			},
			viewConfig : {
				// 显示重复样式，不用隔行显示
				stripeRows : false,
				enableTextSelection : true,
				getRowClass : function(record, rowIndex, p, ds) {
					// 如果存在未受理的更改，标记为粉红色
					if (!Ext.isEmpty(record.get('waybillrfcStatus'))) {
						return 'printDeliver_mark_color';
					}else{
						if (record.get('printtimes') > 0) {
							return 'passProcess_mark_color';
						}else{
							return '';
						}
					}
					
				}
			},
			dockedItems : [{
							xtype : 'toolbar',
							dock : 'top',
							layout : 'column',
							items : [{
								xtype : 'label',
								margin : '0 0 0 600',
								html :'&nbsp;&nbsp;',
								height:15,
								width:20,
								style:'background-color:#99FF00;'
							},
							{
								xtype : 'label',
								margin : '0 0 0 10',
								text : '已打印过到达联'
							},{
								xtype : 'image',
								margin : '0 0 0 10',
								imgCls : 'foss_icons_pkp_3daysNoPicking'
							}, {
								xtype : 'label',
								margin : '0 0 0 10',
								text :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notSdmissibleRFC') // 存在未受理的更改单
							}]
					}],
			columns : [{
						dataIndex : 'id',
						align : 'center',
						flex : 1,
						hidden : true
					}, {
						dataIndex : 'stockGoodQty',
						align : 'center',
						flex : 1,
						hidden : true
					}, {
						dataIndex : 'waybillNo',
						align : 'center',
						width : 80,
						xtype: 'ellipsiscolumn',
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.waybillNo') // 运单号
					}, {
						dataIndex : 'arrivesheetNo',
						align : 'center',
						flex : 1,
						header : '到达联编号'
					},{
						dataIndex : 'goodsName',
						align : 'center',
						width : 80,
						xtype: 'ellipsiscolumn',
						header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsName') // 货物名称
					}, {
						dataIndex : 'arrangeGoodsQty',
						align : 'center',
						width : 60,
						header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.arrangeGoodsQty') // 排单件数
					}, {
						dataIndex : 'weight',
						align : 'center',
						width : 60,
						header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.weight') // 排单重量
					}, {
						dataIndex : 'goodsVolumeTotal',
						align : 'center',
						width : 60,
						header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsVolumeTotal') // 体积
					}, {
						dataIndex : 'dimension',
						align : 'center',
						width : 60,
						header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.dimension') // 尺寸
					}, {
						dataIndex : 'transportType',
						align : 'center',
						width : 100,
						xtype: 'ellipsiscolumn',
						header :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.transportType'), // 运输性质
						setValue : function(value) {
							this.setRawValue(Foss.pkp.ProductData.rendererSubmitToDisplay(value));
						}
					}, {
						dataIndex : 'consignee',
						align : 'center',
						width : 80,
						xtype: 'ellipsiscolumn',
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consignee') // 收货人
					}, {
						dataIndex : 'consigneeContact',
						align : 'center',
						width : 80,
						xtype: 'ellipsiscolumn',
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consigneeContact') // 联系方式
					}, {
						dataIndex : 'consigneeAddress',
						align : 'center',
						width : 100,
						xtype: 'ellipsiscolumn',
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consigneeAddress') // 收货地址
					}, {
						dataIndex : 'deliverRequire',
						align : 'center',
						width : 100,
						xtype: 'ellipsiscolumn',
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverRequire') // 送货要求
					}, {
						dataIndex : 'goodsStatus',
						align : 'center',
						width : 60,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsStatus') // 货物状态
					}, {
						dataIndex : 'isAlreadyContact',
						align : 'center',
						width : 60,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.isAlreadyContact'), // 是否已联系客户
						renderer : function(value) {
							if (value == 'Y') {
								return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.yes');//是
							} else {
								return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.no');//否
							}
						}
					}, {
						dataIndex : 'estimatedDeliverTime',
						align : 'center',
						width : 130,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverDate'), // 预计送货时间
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s'
					}, {
						dataIndex : 'isException',
						align : 'center',
						width : 60,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.isException'), // 是否异常
						renderer : function(value) {
							if (value == 'Y') {
								return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.yes');//是
							} else {
								return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.no');//否
							}
						}
					}, {
						dataIndex : 'isNeedInvoice',
						align : 'center',
						width : 100,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.isNeedInvoice'), // 是否需要发票
						renderer : function(value) {
							if (value == 'Y') {
								return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.yes');//是
							} else {
								return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.no');//否
							}
						}
					}, {
						dataIndex : 'paymentType',
						align : 'center',
						width : 80,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.paymentType'), // 付款方式
						renderer : function(value) {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						}
					}, {
						dataIndex : 'notes',
						align : 'center',
						width : 60,
						xtype: 'ellipsiscolumn',
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notes') // 备注
					}, {
						dataIndex : 'deliverType',
						align : 'center',
						width : 100,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverType'), // 送货方式
						renderer : function(value) {
							var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
							if(Ext.isEmpty(v) || value == v){
								v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
							}
							return v;
						}
					}, {
						dataIndex : 'waybillrfcStatus',
						align : 'center',
						width : 60,
						hidden : true,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.waybillrfcStatus') // 更改状态
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.predeliver.deliverbillnew.DeliverbillDetailStore');
				me.callParent([cfg]);
			}
		});

//基础信息Form
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillBasicInfoForm', {
			extend : 'Ext.form.Panel',
			collapsible : false,
			defaults : {
				margin : '5 15 5 15',
				labelWidth : 90
			},
			defaultType : 'textfield',
			layout : 'column',
			items : [{
						name : 'id',
						xtype : 'hidden'
					}, {
						name : 'deliverbillNo',
						fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.preDeliverbillNo'), //预派送单编号
						columnWidth : .33,
						readOnly : true
					}, {
						name : 'vehicleNo',
						fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.vehicleNo'), //车辆 
						columnWidth : .33,
						readOnly : true
					}, {
						name : 'driverName',
						fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.driverName'), //司机姓名 
						columnWidth : .33,
						readOnly : true
					}]
		});

//打印到达联Form
Ext.define('Foss.predeliver.deliverbillnew.PrintArrivesheetForm', {
			extend : 'Ext.form.Panel',
			// frame : true,
			collapsible : false,
			defaults : {
				margin : '5 15 5 15',
				labelWidth : 90
			},
			layout : 'column',
			items : [{
				columnWidth : .08,
				xtype : 'button',
				cls : 'yellow_button',
				text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.print'), // 打印
				handler : function(button, e) {
					button.up('window').getDeliverbillDetailGrid()
							.getPrintWindow().show();
					//更改打印到达联状态
					Ext.Ajax.request({
						url : predeliver.realPath('updateIsArriveSheet.action'),
						params : {
							'deliverbillVo.deliverbill.id' : predeliver.deliverbillnew.isArriveSheetDeliverbillId
						},success : function(response) {
							predeliver.deliverbillnew.deliverbillNewGrid.getPagingToolbar().moveFirst();
						},error : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
						}
					})
				}
					
					
			}, {
				border : false,
				columnWidth : .92,
				html : '&nbsp;'
			}]
		});

//打印到达联窗口
Ext.define('Foss.predeliver.deliverbillnew.PrintArrivesheetWindow', {
	extend : 'Ext.window.Window',
	modal : true,
	closeAction : 'close',
	width : 1000,
	title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.accordingPredeliverPrintArriveSheet'), // 根据预派送单打印到达联
	deliverbillBasicInfoForm : null,
	getDeliverbillBasicInfoForm : function() {
		if (this.deliverbillBasicInfoForm == null) {
			this.deliverbillBasicInfoForm = Ext
					.create('Foss.predeliver.deliverbillnew.DeliverbillBasicInfoForm');
		}
		return this.deliverbillBasicInfoForm;
	},
	// 派送单明细GRID
	deliverbillDetailGrid : null,
	getDeliverbillDetailGrid : function() {
		if (this.deliverbillDetailGrid == null) {
			this.deliverbillDetailGrid = Ext
					.create('Foss.predeliver.deliverbillnew.DeliverbillDetailGrid');
		}
		return this.deliverbillDetailGrid;
	},
	printArrivesheetForm : null,
	getPrintArrivesheetForm : function() {
		if (this.printArrivesheetForm == null) {
			this.printArrivesheetForm = Ext
					.create('Foss.predeliver.deliverbillnew.PrintArrivesheetForm');
		}
		return this.printArrivesheetForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getDeliverbillBasicInfoForm(),
				me.getDeliverbillDetailGrid(), me.getPrintArrivesheetForm()];
		me.callParent([cfg]);
	}
});


/** -----------------------------------------打印到达联（预派送单） End----------------------------------------- */




/** -----------------------------------------分配派送任务(司机) Begin----------------------------------------- */
//查询司机form
Ext.define('Foss.predeliver.deliverbillnew.QueryDriverForm', {
	title : '查询条件', // 查询条件
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 80
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [{
				name : 'empCode',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.empCode'), // 工号 
				columnWidth : .33
			}, {
				name : 'empName',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.empName'), // 姓名
				columnWidth : .33
			}, {
				name : 'empPhone',
				fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.empPhone'), // 电话号码
				columnWidth : .33
			}, {
				border : false,
				xtype : 'container',
				columnWidth : 1,
				layout : 'column',
				defaults : {
					margin : '5 0 5 0'
				},
				items : [{
					xtype : 'button',
					columnWidth : .08,
					text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.reset'),  // 重置
					handler : function() {
						this.up('form').getForm().reset();
					}
				}, {
					border : false,
					columnWidth : .84,
					html : '&nbsp;'
				}, {
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.query'),  // 查询
					handler : function() {
						var pagingToolbar = this.up('window').getDriverGrid().store.load();
					}
				}]
			}]
});


//公司司机的Model
Ext.define('Foss.predeliver.deliverbillnew.DriverModel', {
			extend : 'Ext.data.Model',
			// 定义字段
			fields : [{
						name : 'empCode', // 司机工号
						type : 'string'
					}, {
						name : 'empName', // 司机姓名
						type : 'string'
					}, {
						name : 'empPhone', // 司机电话
						type : 'int'
					}, {
						name : 'orgId', // 部门编号
						type : 'string'
					}]
		});

//定义公司司机的Store
Ext.define('Foss.predeliver.deliverbillnew.DriverStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	// 绑定一个模型
	model : 'Foss.predeliver.deliverbillnew.DriverModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : predeliver.realPath('queryNewDriver.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'deliverbillVo.driverList'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryDriverForm = Ext
					.getCmp('T_predeliver-deliverbillnewIndex_content')
					.getSelectDriverWindow().getQueryDriverForm();
			if (queryDriverForm != null) {
				var queryParams = queryDriverForm.getValues();
				Ext.apply(operation, {
					params : {
						'deliverbillVo.driverDto.empCode' : queryParams.empCode,
						'deliverbillVo.driverDto.empName' : queryParams.empName,
						'deliverbillVo.driverDto.empPhone' : queryParams.empPhone
					}
				});
			}
		}
	}
});

//公司司机GRID
Ext.define('Foss.predeliver.deliverbillnew.DriverGrid', {
			extend : 'Ext.grid.Panel',
			frame : true,
			title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.companyDrivers'), // 公司司机
			emptyText : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.emptyText'), // 查询结果为空
			autoScroll : true,
			height : 450,
			collapsible : true,
			animCollapse : true,
			columns : [{
						dataIndex : 'empCode',
						align : 'center',
						flex : 1,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.empCode') // 工号
					}, {
						dataIndex : 'empName',
						align : 'center',
						flex : 1,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.empName') // 姓名
					}, {
						dataIndex : 'orgId',
						align : 'center',
						flex : 1,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.orgId') // 部门
					}, {
						dataIndex : 'empPhone',
						align : 'center',
						flex : 1,
						header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.empPhone') // 电话号码
					}],
			viewConfig : {
				enableTextSelection : true
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.predeliver.deliverbillnew.DriverStore');
				me.selModel = Ext.create('Ext.selection.RadioModel', {showHeaderCheckbox : false});
				me.callParent([cfg]);
			}
		});


//确认司机form
Ext.define('Foss.predeliver.deliverbillnew.ConfirmDriverForm', {
	extend : 'Ext.form.Panel',
	frame : false,
	height : 80,
	defaults : {
		margin : '0 45 0 25'
	},
	layout : 'column',
	items : [{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '0 0 0 0'
		},
		items : [{
					border : false,
					columnWidth : .92,
					html : '&nbsp;'
				}, {
					columnWidth : .08,
					xtype : 'button',
					cls : 'yellow_button',
					text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.confirm'), // 确认
					handler : function() {
						var selectRow = predeliver.deliverbillnew.selectDriverWindow.getDriverGrid().getSelectionModel().getSelection();

						if (selectRow.length == 0) {
							Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), 'error', 4000); // 请选择派送司机
							//请选择派送司机
							return;
						}
						var driverName = selectRow[0].get('empName');
						Ext.Msg.confirm(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.confirmAllotDriver')  + driverName + '?', //是否确认分配给司机
								function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											url : predeliver
													.realPath('assignNewDriver.action'),
											params : {
												'deliverbillVo.deliverbill.id' : predeliver.deliverbillnew.selectDriverWindow.getDeliverbillId(),
												'deliverbillVo.driverDto.empCode' : selectRow[0].get('empCode'),
												'deliverbillVo.driverDto.empName' : selectRow[0].get('empName')
											},
											success : function(response) {
												predeliver.deliverbillnew.selectDriverWindow.close();

												predeliver.deliverbillnew.deliverbillNewGrid.store.load();

												Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message);
											},
											error : function(response) {
												var result = Ext.decode(response.responseText);
												Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
											},
											exception : function(response) {
												var result = Ext.decode(response.responseText);
												Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
											}
										})
									}
								});
					}
				}]
	}]
});


//分配派送任务(司机)窗口
Ext.define('Foss.predeliver.deliverbillnew.SelectDriverWindow', {
			extend : 'Ext.window.Window',
			title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), //公司司机
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			modal : true,
			closeAction : 'hide',
			width : 1000,
			// 派送单ID
			deliverbillId : '',
			getDeliverbillId : function() {
				return this.deliverbillId;
			},
			setDeliverbillId : function(deliverbillId) {
				this.deliverbillId = deliverbillId;
			},
			// 查询条件FORM
			queryDriverForm : null,
			getQueryDriverForm : function() {
				if (this.queryDriverForm == null) {
					this.queryDriverForm = Ext
							.create('Foss.predeliver.deliverbillnew.QueryDriverForm');
				}
				return this.queryDriverForm;
			},
			// 公司司机信息GRID
			driverGrid : null,
			getDriverGrid : function() {
				if (this.driverGrid == null) {
					this.driverGrid = Ext
							.create('Foss.predeliver.deliverbillnew.DriverGrid');
				}
				return this.driverGrid;
			},
			// 确认司机FORM
			confirmDriverForm : null,
			getConfirmDriverForm : function() {
				if (this.confirmDriverForm == null) {
					this.confirmDriverForm = Ext
							.create('Foss.predeliver.deliverbillnew.ConfirmDriverForm');
				}
				return this.confirmDriverForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [me.getQueryDriverForm(), me.getDriverGrid(),
						me.getConfirmDriverForm()];
				me.callParent([cfg]);
			}
		});


/** -----------------------------------------分配派送任务(司机) End----------------------------------------- */



/** -----------------------------------------打印到达联（派送单） Begin----------------------------------------- */


//基础信息Form
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillBasicInfoTwoForm',
				{
					extend : 'Ext.form.Panel',
					collapsible : false,
					defaults : {
						margin : '5 15 5 15',
						labelWidth : 90
					},
					defaultType : 'textfield',
					layout : 'column',
					items : [
							{
								name : 'id',
								xtype : 'hidden'
							},
							{
								name : 'deliverbillNo',
								fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverbillNo'), // 派送单编号
								columnWidth : .33,
								readOnly : true
							},
							{
								name : 'vehicleNo',
								fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.vehicle'), // 车辆	
								columnWidth : .33,
								readOnly : true
							},
							{
								name : 'driverName',
								fieldLabel : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.driverName'), // 司机姓名
								columnWidth : .33,
								readOnly : true
							} ]
				});

// 打印到达联Form
Ext.define('Foss.predeliver.deliverbillnew.PrintArrivesheetTwoForm',
		{
			extend : 'Ext.form.Panel',
			collapsible : false,
			defaults : {
				margin : '5 15 5 15',
				labelWidth : 90
			},
			layout : 'column',
			items : [
					{
						columnWidth : .16,
						xtype : 'button',
						cls : 'yellow_button',
						text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.printChanged'), //打印已选,
						handler : function(button, e) {
							var deliverbillDetailGrid = button.up('window')
									.getDeliverbillDetailGrid();

							var selectRow = deliverbillDetailGrid
									.getSelectionModel().getSelection();

							if (selectRow.length == 0) {
								Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'), predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.choiceArriveSheet'));
								return;
							}
							deliverbillDetailGrid.getPrintWindow().show();
							
							//更改打印到达联状态
							Ext.Ajax.request({
								url : predeliver.realPath('updateIsArriveSheet.action'),
								params : {
									'deliverbillVo.deliverbill.id' : predeliver.deliverbillnew.isArriveSheetDeliverbillId
								},success : function(response) {
									predeliver.deliverbillnew.deliverbillNewGrid.getPagingToolbar().moveFirst();
								},error : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
								},
								exception : function(response) {
									var result = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.tip'),result.message,'error', 4000);
								}
							})
							
							
							
						}
					}, {
						columnWidth : .08,
						xtype : 'button',
						cls : 'yellow_button',
						text : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.cancel'), //取消,
						handler : function(button, e) {
							button.up('window').close();
						}
					}, {
						border : false,
						columnWidth : .76,
						html : '&nbsp;'
					} ]
		});




//定义预派送单明细的模型
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailTwoModel',
				{
					extend : 'Ext.data.Model',
					// 定义字段
					fields : [
							{
								name : 'id',
								type : 'string'
							},
							{
								name : 'arrivesheetNo',
								type : 'string'
							},
							{
								name : 'tSrvDeliverbillId',
								type : 'string'
							},
							{
								name : 'serialNo', // 派送单明细序列号，仅用于派送单明细Model
								type : 'int'
							},
							{
								name : 'waybillNo', // 运单号
								type : 'string'
							},
							{
								name : 'goodsName', // 货物名称
								type : 'string'
							},
							{
								name : 'waybillGoodsQty', // 对于派送单明细，为排单货物件数；对于待排运单，为待排运单整票货物件数。
								type : 'int'
							},
							{
								name : 'arrangableGoodsQty', // 可排单件数，仅用于待排运单Model
								type : 'int'
							},
							{
								name : 'arrangeGoodsQty', // 排单件数
								type : 'int'
							},
							{
								name : 'preArrangeGoodsQty', // 预排单件数。对于待排运单，其初始值为可排单件数
								type : 'int'
							},
							{
								name : 'weight', // 对于派送单明细，为排单重量；对于待排运单，为待排运单整票货物重量。
								type : 'number'
							},
							{
								name : 'arrangedWeight', // 待排运单的排单重量
								type : 'number',
								convert : function(v, record) {
									return Ext.util.Format
											.round(
													record.data.arrangableGoodsQty > 0 ? record.data.weight
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
															: record.data.weight,
													2);
								}
							},
							{
								name : 'goodsVolumeTotal', // 运单体积   
								type : 'string'
							},
							{
								name : 'dimension', // 运单尺寸   
								type : 'string'
							},
							{
								name : 'goodsVolumeTotal', // 对于派送单明细，为排单体积；对于待排运单，为待排运单整票货物体积。
								type : 'number'
							},
							{
								name : 'arrangedGoodsVolume', // 待排运单的排单体积
								type : 'number',
								convert : function(v, record) {
									return Ext.util.Format
											.round(
													record.data.arrangableGoodsQty > 0 ? record.data.goodsVolumeTotal
															* record.data.arrangeGoodsQty
															/ record.data.waybillGoodsQty
															: record.data.goodsVolumeTotal,
													2);
								}
							}, {
								name : 'transportType',convert:function(value) {
									return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
								}//运输性质
							}, {
								name : 'arriveTime', // 到达时间
								type : 'date',
								convert : dateConvert
							}, {
								name : 'consignee', // 收货人
								type : 'string'
							}, {
								name : 'consigneeContact', // 联系方式
								type : 'string'
							}, {
								name : 'consigneeAddress', // 收货地址
								type : 'string'
							}, {
								name : 'deliverRequire', // 送货要求
								type : 'string'
							}, {
								name : 'goodsStatus', // 货物状态
								type : 'string'
							}, {
								name : 'isAlreadyContact', // 是否已联系客户
								type : 'string'
							}, {
								name : 'estimatedDeliverTime', // 预计送货时间
								type : 'date',
								convert : dateConvert
							}, {
								name : 'isException', // 是否异常
								type : 'string'
							}, {
								name : 'isNeedInvoice', // 是否需要发票
								type : 'string'
							}, {
								name : 'paymentType', // 付款方式
								type : 'string'
							}, {
								name : 'notes', // 备注
								type : 'string'
							}, {
								name : 'deliverType', // 送货方式
								type : 'string'
							}, {
								name : 'arrangeStatus', // 排单状态
								type : 'string'
							}, {
								name : 'waybillrfcStatus', // 更改状态
								type : 'string'
							}, {
								name : 'payAmount', // 到付款
								type : 'number'
							}, {
								name : 'fastWaybillFlag', // 卡货标志
								type : 'int'
							}, {
								name : 'isSentRequired', // 是否必送货
								type : 'string'
							}, {
								name : 'stockGoodQty', // 提货网点库存
								type : 'int'
							},{
								name : 'destroyed', // 作废
								type : 'string'
							},{
								name : 'printtimes', // 打印次数
								type : 'int'
							} ]
				});




//定义派送单明细的Store
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailTwoStore',
				{
					extend : 'Ext.data.Store',
					autoLoad : false,
					// 绑定一个模型
					model : 'Foss.predeliver.deliverbillnew.DeliverbillDetailTwoModel',
					// 定义一个代理对象
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : predeliver
								.realPath('queryDeliverbillNewArrivesheetList.action'),
						// 定义一个读取器
						reader : {
							// 以JSON的方式读取
							type : 'json',
							// 定义读取JSON数据的根对象
							root : 'deliverbillVo.deliverbillDetailList'
						}
					},
					deliverbillId : '',
					setDeliverbillId : function(deliverbillId) {
						this.deliverbillId = deliverbillId;
					},
					getDeliverbillId : function() {
						return this.deliverbillId;
					},
					status : '',
					setStatus : function(status){
						this.status = status;
					},
					getStatus : function(){
						return this.status;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.callParent([ cfg ]);
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							Ext.apply(operation, {
								params : {
									'deliverbillVo.deliverbillNewDto.id' : store.getDeliverbillId(),
									'deliverbillVo.deliverbillNewDto.status' : store.getStatus()
								}
							});
						}
					}
				});



//定义派送单明细的表格
Ext.define('Foss.predeliver.deliverbillnew.DeliverbillDetailTwoGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.arriveSheet'), //到达联,
					emptyText : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.emptyText'), //查询结果为空,
					bodyCls : 'autoHeight',
					cls : 'autoHeight',
					autoScroll : true,
					collapsible : true,
					animCollapse : true,
					printWindow : null,
					getPrintWindow : function() {
						var me = this;
						if (this.printWindow == null) {
							me.printWindow = Ext.create('Foss.printArriveSheet.printOneWindow', me);
						}
						return me.printWindow;
					},
					viewConfig : {
						// 显示重复样式，不用隔行显示
						stripeRows : false,
						enableTextSelection : true,
						getRowClass : function(record, rowIndex, p, ds) {
							// 如果存在未受理的更改，标记为粉红色
							if (!Ext.isEmpty(record.get('waybillrfcStatus'))) {
								return 'printDeliver_mark_color';
							}else{
								if (record.get('printtimes') > 0) {
									return 'passProcess_mark_color';
								}else{
									return '';
								}
							}
							
						}
					},
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'top',
						layout : 'column',
						items : [{
							xtype : 'label',
							margin : '0 0 0 600',
							html :'&nbsp;&nbsp;',
							height:15,
							width:20,
							style:'background-color:#99FF00;'
						},
						{
							xtype : 'label',
							margin : '0 0 0 10',
							text : '已打印过到达联'
						},{
							xtype : 'image',
							margin : '0 0 0 10',
							imgCls : 'foss_icons_pkp_3daysNoPicking'
						}, {
							xtype : 'label',
							margin : '0 0 0 10',
							text :  predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notSdmissibleRFC') // 存在未受理的更改单
						}]
					} ],
					columns : [
							{
								dataIndex : 'id',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'stockGoodQty',
								align : 'center',
								flex : 1,
								hidden : true
							},
							{
								dataIndex : 'waybillNo',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.waybillNo') //运单号
							},
							 {
								dataIndex : 'arrivesheetNo',
								align : 'center',
								flex : 1,
								header : '到达联编号'
							},
							{
								dataIndex : 'goodsName',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsName') //货物名称
							},
							{
								dataIndex : 'arrangeGoodsQty',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.arrangeGoodsQty') //排单件数
							},
							{
								dataIndex : 'weight',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.weight') //排单重量
							},
							{
								dataIndex : 'goodsVolumeTotal',
								align : 'center',
								flex : 1,	
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsVolumeTotal') //体积
							},
							{
								dataIndex : 'dimension',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.dimension') //尺寸
							},
							{
								dataIndex : 'transportType',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.transportType'), //运输性质
								setValue : function(value) {
									this.setRawValue(Foss.pkp.ProductData.rendererSubmitToDisplay(value));
								}
							},
							{
								dataIndex : 'consignee',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consignee') //收货人
							},
							{
								dataIndex : 'consigneeContact',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consigneeContact') //联系方式
							},
							{
								dataIndex : 'consigneeAddress',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.consigneeAddress') //收货地址
							},
							{
								dataIndex : 'deliverRequire',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverRequire') //送货要求
							},
							{
								dataIndex : 'goodsStatus',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.goodsStatus') //货物状态
							},
							{
								dataIndex : 'isAlreadyContact',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.isAlreadyContact'),
								renderer : function(value) {
									if (value == 'Y') {
										return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.yes');  //是
									} else {
										return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.no'); //否
									}
								}
							},
							{
								dataIndex : 'estimatedDeliverTime',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.estimatedDeliverTime'), //预计送货时间
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s'
							},
							{
								dataIndex : 'isException',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.isException'), //是否异常
								renderer : function(value) {
									if (value == 'Y') {
										return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.yes');  //是
									} else {
										return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.no'); //否
									}
								}
							},
							{
								dataIndex : 'isNeedInvoice',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.isNeedInvoice'), //是否需要发票
								renderer : function(value) {
									if (value == 'Y') {
										return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.yes');  //是
									} else {
										return predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.no'); //否
									}
								}
							},
							{
								dataIndex : 'paymentType',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.paymentType'), //付款方式
								renderer : function(value) {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'SETTLEMENT__PAYMENT_TYPE');
								}
							},
							{
								dataIndex : 'notes',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.notes') //备注
							},
							{
								dataIndex : 'deliverType',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.deliverType'), //送货方式
								renderer : function(value) {
									var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
									if(Ext.isEmpty(v) || value == v){
										v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
									}
									return v;
								}
							}, {
								dataIndex : 'arrangeStatus',
								align : 'center',
								flex : 1,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.arrangeStatus') //排单状态
							}, {
								dataIndex : 'waybillrfcStatus',
								align : 'center',
								flex : 1,
								hidden : true,
								header : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.waybillrfcStatus') //更改状态
							} ],
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Foss.predeliver.deliverbillnew.DeliverbillDetailTwoStore');
						me.selModel = Ext.create('Ext.selection.CheckboxModel');
						me.callParent([ cfg ]);
					}
				});
























//打印到达联窗口
Ext.define('Foss.predeliver.deliverbillnew.printArrivesheetTwoWindow',
				{
					extend : 'Ext.window.Window',
					modal : true,
					closeAction : 'hide',
					width : 1000,
					title : predeliver.deliverbillnew.i18n('pkp.predeliver.deliverbillnew.accordingdeliverPrintArriveSheet'), //根据派送单打印到达联
					deliverbillBasicInfoForm : null,
					getDeliverbillBasicInfoForm : function() {
						if (this.deliverbillBasicInfoForm == null) {
							this.deliverbillBasicInfoForm = Ext
									.create('Foss.predeliver.deliverbillnew.DeliverbillBasicInfoTwoForm');
						}
						return this.deliverbillBasicInfoForm;
					},
					// 派送单明细GRID
					deliverbillDetailGrid : null,
					getDeliverbillDetailGrid : function() {
						if (this.deliverbillDetailGrid == null) {
							this.deliverbillDetailGrid = Ext
									.create('Foss.predeliver.deliverbillnew.DeliverbillDetailTwoGrid');
						}
						return this.deliverbillDetailGrid;
					},
					printArrivesheetForm : null,
					getPrintArrivesheetForm : function() {
						if (this.printArrivesheetForm == null) {
							this.printArrivesheetForm = Ext
									.create('Foss.predeliver.deliverbillnew.PrintArrivesheetTwoForm');
						}
						return this.printArrivesheetForm;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.items = [ me.getDeliverbillBasicInfoForm(),
								me.getDeliverbillDetailGrid(),
								me.getPrintArrivesheetForm() ];
						me.callParent([ cfg ]);
					}
				});




/** -----------------------------------------打印到达联（派送单） End----------------------------------------- */


Ext.onReady(function() {
	Ext.QuickTips.init();

	predeliver.deliverbillnew.queryDeliverbillNewForm = Ext.create('Foss.predeliver.deliverbillnew.QueryDeliverbillNewForm');
	predeliver.deliverbillnew.deliverbillNewGrid = Ext.create('Foss.predeliver.deliverbillnew.DeliverbillNewGrid');


	// 打印到达联窗口
	predeliver.deliverbillnew.printArrivesheetWindow = null;
	predeliver.deliverbillnew.printArrivesheetTwoWindow = null;
	predeliver.deliverbillnew.selectDriverWindow = null;
	
	Ext.create('Ext.panel.Panel',{
		id : 'T_predeliver-deliverbillnewIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//打印到达联（预派送单）
		getPrintArrivesheetWindow : function() {
			if (predeliver.deliverbillnew.printArrivesheetWindow == null) {
				predeliver.deliverbillnew.printArrivesheetWindow = Ext
						.create('Foss.predeliver.deliverbillnew.PrintArrivesheetWindow');
			}

			return predeliver.deliverbillnew.printArrivesheetWindow;
		},
		//打印到达联（派送单）
		getPrintArrivesheetTwoWindow : function() {
			if (predeliver.deliverbillnew.printArrivesheetTwoWindow == null) {
				predeliver.deliverbillnew.printArrivesheetTwoWindow = Ext
						.create('Foss.predeliver.deliverbillnew.printArrivesheetTwoWindow');
			}

			return predeliver.deliverbillnew.printArrivesheetTwoWindow;
		},
		//分配派送任务(司机)窗口
		getSelectDriverWindow : function() {
			if (predeliver.deliverbillnew.selectDriverWindow == null) {
				predeliver.deliverbillnew.selectDriverWindow = Ext
												.create('Foss.predeliver.deliverbillnew.SelectDriverWindow');
			}

			return predeliver.deliverbillnew.selectDriverWindow;
		},
		items : [predeliver.deliverbillnew.queryDeliverbillNewForm,predeliver.deliverbillnew.deliverbillNewGrid],
		renderTo :'T_predeliver-deliverbillnewIndex-body'
	});
});
