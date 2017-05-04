//发车任务model
Ext.define('Foss.arrival.SecondCarModel', {
			extend : 'Ext.data.Model',
					fields : [{
						name : 'id',
						type : 'string',
						hiddenField : true
					}, {
						name : 'vehicleNo',//车牌号
						type : 'string'
					}, {
						name : 'connectionBillNo', //接驳单号
						type : 'string'
					}, {
						name : 'origOrgCode',//出发部门code
						type : 'string'
					}, {
						name : 'destOrgCode',//到达部门code
						type : 'string'
					}, {
						name : 'origOrgName',//出发部门名称
						type : 'string'
					}, {
						name : 'destOrgName',//到达部门名称
						type : 'string'
					},{
						name : 'departTime',//出发时间
						type : 'string'
					},{
						name : 'arrivalTime',//到达时间
						type : 'string'
					}, {
						name : 'arrivalStatus',//到达情况
						type : 'string'
					},{
						name : 'loader',//装车人
						type : 'string'
					},{
						name : 'handoverNo',//交接单号
						type : 'string'
					}

			]
		});
//
Ext.define('Foss.arrival.ArrivalTypeStore', {
	extend : 'Ext.data.Store',
	fields : [{
				name : 'code',
				type : 'string'
			}, {
				name : 'name',
				type : 'string'
			}],
	data : {
		'items' : [{
					code : 0,
					name : '未到'
				},{
					code : 1,
					name : '已到'
				},{
					code : '',
					name : '全部'
				}]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});



//发车任务Store
Ext.define('Foss.arrival.SecondCarDepartStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.arrival.SecondCarModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : arrival.realPath('querySecondCarDepartureGrid.action'),
		reader : {
			type : 'json',
			root : 'arrivalVO.departureList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = arrival.departureform.getValues();
			Ext.apply(operation, {
				params : {
					//交接编号
					'arrivalVO.queryDepartureEntity.connectionBillNo' : queryParams.billNo,
					//车牌号
					'arrivalVO.queryDepartureEntity.vehicleNo' : queryParams.vehicleNo,
					//起止时间
					'arrivalVO.queryDepartureEntity.startTime' : queryParams.startTime,
					//结束时间
					'arrivalVO.queryDepartureEntity.endTime' : queryParams.endTime,
					//到达情况
					'arrivalVO.queryDepartureEntity.arrivalStatus' : queryParams.arrivalStatus,
					//出发部门
					'arrivalVO.queryDepartureEntity.origOrgCode' : queryParams.origOrgCode,
					//到达部门
					'arrivalVO.queryDepartureEntity.destOrgCode' : queryParams.destOrgCode
				}
			});
		},
	}
});			
//到达本部门任务store
Ext.define('Foss.arrival.SecondCarArrivalStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.arrival.SecondCarModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : arrival.realPath('querySecondCarArrivalGrid.action'),
		reader : {
			type : 'json',
			root : 'arrivalVO.arrivalList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryParams = arrival.arriveform.getValues();
			Ext.apply(operation, {
				params : {
					//交接编号
					'arrivalVO.queryArriveEntity.handoverNo' : queryParams.billNo,
					//车牌号
					'arrivalVO.queryArriveEntity.vehicleNo' : queryParams.vehicleNo,
					//起止时间
					'arrivalVO.queryArriveEntity.startTime' : queryParams.startTime,
					//结束时间
					'arrivalVO.queryArriveEntity.endTime' : queryParams.endTime,
					//到达情况
					'arrivalVO.queryArriveEntity.arrivalStatus' : queryParams.arrivalStatus,
					//到达部门
					'arrivalVO.queryArriveEntity.destOrgCode' : queryParams.destOrgCode
				}
			});
		},
	}
});	
/**********************************本部门出发的数据start*****************************************/
Ext.define('Foss.arrival.DepartureSecondCarGrid', {
	extend : 'Ext.grid.Panel',
	height : 390,
	autoScroll : false,
	emptyText:arrival.i18n('tfr.arrival.ArrivalGrid.grid.empty.title'),//"查询结果为空",
	columnLines : true,
	frame : true,
	columns : [ {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleNo.label'),//'车牌号',
		dataIndex : 'vehicleNo'
	}, {
		header : '交接单号',
		dataIndex : 'connectionBillNo'
	},{
		header : '到达部门',//'到达部门',
		dataIndex : 'destOrgName'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.departTime.label'),// '出发时间（获取类型）',
		dataIndex : 'departTime',
		width:150
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalTime.label'),// '到达时间（获取类型）',
		dataIndex : 'arrivalTime',
		width:150
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalStatus.label'),//'到达情况',
		dataIndex : 'arrivalStatus',
		renderer : function(value) {
			 if(value==20||value==30){
				 return "未到";
			 }else if(value==40||value==50){
				 return "已到";
			 }else{
				 return "";
			 }
		}
	},{header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.loader.label'),// '装车人',
		dataIndex : 'loader'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.store = Ext.create('Foss.arrival.SecondCarDepartStore');
		me.tbar = [{
					xtype : 'button',
					plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
						seconds: 5
					}),
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.departconfirm.button'),//'发车确认',
					disabled:!arrival.isPermission('arrival/departconfirmbutton.action'),
					hidden:!arrival.isPermission('arrival/departconfirmbutton.action'),
					gridContainer : this,
					handler:function(){
						var ids="";
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length<=0)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('请至少选择一条记录'));
						  return false;
						}
						//遍历数组对象取值进行发车确认更新操作
						Ext.Array.forEach(rowObjs,function(item){
							ids+=item.data.id+",";
						} )
						var params = {
								'arrivalVO.operationDTO.ids' : ids,
							};
						//校验车牌号对应的交接单号是否已经发车
						Ext.Ajax.request({
							url : arrival.realPath('checkSecondCarDepartConfirm.action'),
							params : params,
							success : function(response) {
								//判断是否向后台发起发车确认操作标志位
								var flag=false;
								var result = Ext.decode(response.responseText);
								var departList=result.arrivalVO.departureConfirmList;
								Ext.Array.forEach( departList, function(item){
									if(item.arrivalStatus==30||item.arrivalStatus==50 && item.departTime!=null && item.departOperator!=null){
										Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),'你所选择的车牌号所对应的交接单号已包含已经发车的,请重新进行选择');
										flag=true;
										return;
									}
								});
								if(!flag){
									//发车确认处理操作
									Ext.Ajax.request({
														url : arrival.realPath('secondCarDepartConfirm.action'),
														params : params,
														success : function(response) {
															arrival.departureSecondCarPagingBar.moveFirst();
															Ext.ux.Toast.msg(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.departconfirm.ok.tips'), 'ok', 3000);
														},
														exception : function(response) {
										    				var result = Ext.decode(response.responseText);
										    				if(result.message){
										    					Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
										    				}else
										    				{
										    					Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n(result.message));
										    				}
										    			}
													});
								}
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    			}
						});
						
					}
				}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					maximumSize : 800,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
						sizeList : [['10', 10], ['50', 50], ['100', 100], ['800', 800]]
					})
				});
		arrival.departureSecondCarPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});


Ext.define('Foss.arrival.QuerySecondCarDepartureForm', {
			extend : 'Ext.form.Panel',
			layout : 'column',
			frame : true,
			border : false,
			defaults : {
				margin : '5 5 5 5',
				columns : 4
			},
			items : [{
				xtype : 'textfield',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.billNo.label'),// '交接编号',
				name : 'billNo',
				columnWidth : .25,
				regex:/[A-Za-z0-9]+-?[A-Za-z0-9]+/
				},{
				xtype : 'commontruckselector',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.vehicleNo.label'),//'车牌号',
				name : 'vehicleNo',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				listeners: {
					'select': function(field, records, eOpts) {
						var record = records[0];
						arrival.departureform.getForm().findField('vehicleNo').setValue(record.get('vehicleNo'));
					}
				}
			}, {
				xtype : 'rangeDateField',
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.createTime.label'),//'起止时间',
				columnWidth : .5,
				disallowBlank:true,
				fieldId:'Foss_arrival_QuerySecondCarDepartureForm_createTime_Id',
				dateType : 'datetimefield_date97',
				fromName : 'startTime',
				allowBlank:false,
				dateRange:30,
				fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'),
				toName : 'endTime',
				toValue : Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')
			},{
				xtype : 'combo',
				fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.arrivalStatus.label'),// '到达情况',
				value : '',
				name : 'arrivalStatus',
				displayField : 'name',
				valueField : 'code',
				columnWidth : .25,
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				store : Ext.create('Foss.arrival.ArrivalTypeStore')
				},{
				xtype : 'dynamicorgcombselector',//
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.origOrgName.label'),//'出发部门',
				type : 'ORG',
				readOnly:true,
				name : 'origOrgCode',
				columnWidth : .25,
				listeners :{
					render : function(panel,text){
						var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
						Ext.Ajax.request({
									url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
									success : function(response) {
										var result = Ext.decode(response.responseText);
											cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
							                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
									}
								});
				  }
				}
			},{
				fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.destOrgName.label'),//'到达部门',
				name : 'destOrgCode',
				xtype : 'accesspointselector',
				columnWidth : .3
			}, 
			
			{
	    		   xtype:'container',
	    		   border:false,
	    		   html:'&nbsp;',
	    		   columnWidth:.25
	    	   },{
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [{
					text : arrival.i18n('tfr.arrival.QueryForm.form.reset.button'),//'重置',
					columnWidth : .2,
					handler : function() {
						this.up('form').getForm().reset();
						this.up('form').getForm().findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'00', '00', '00'), 'Y-m-d H:i:s'));
						this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s')); 
						var cmbOrgCode = this.up('form').getForm().findField('origOrgCode');
						Ext.Ajax.request({
							url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
							success : function(response) {
								var result = Ext
										.decode(response.responseText);
									cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
					                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
							}
						});
					}
				}, {
					xtype : 'container',
					columnWidth : .6,
					html : '&nbsp;'
				}, {
					text : arrival.i18n('tfr.arrival.QueryForm.form.query.button'),//'查询',
					columnWidth : .2,
					cls:'yellow_button',
					handler : function() {
						  var form = arrival.departureform.getForm();
						if (!form.isValid())
						return false;
						arrival.departureSecondCarPagingBar.moveFirst();
					}
				}]
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
/**********************************本部门出发的数据end**************************/



/*********************************到达本部门的数据start**************************/
Ext.define('Foss.arrival.ArrivalSecondCarGrid', {
	extend : 'Ext.grid.Panel',
	height : 390,
	autoScroll : false,
	emptyText:arrival.i18n('tfr.arrival.ArrivalGrid.grid.empty.title'),//"查询结果为空",
	columnLines : true,
	frame : true,
	columns : [ {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.vehicleNo.label'),//'车牌号',
		dataIndex : 'vehicleNo'
	}, {
		header : '交接单号',//'交接单号',
		dataIndex : 'handoverNo'
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.departTime.label'),// '出发时间（获取类型）',
		dataIndex : 'departTime',
		width:150
	}, {
		header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalTime.label'),// '到达时间（获取类型）',
		dataIndex : 'arrivalTime',
		width:150
	}, {
		header : arrival.i18n('tfr.arrival.ArrivalGrid.grid.arrivalStatus.label'),//'到达情况',
		dataIndex : 'arrivalStatus',
		renderer : function(value) {
			 if(value==""){
				 return ""; 
			 }
			 if(value==0){
				 return "未到";
			 }else if(value==1){
				 return "已到";
			 }else{
				 return "";
			 }
		}
	},{header :arrival.i18n('tfr.arrival.ArrivalGrid.grid.loader.label'),// '装车人',
		dataIndex : 'loader'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.store = Ext.create('Foss.arrival.SecondCarArrivalStore');
		me.tbar = [{
					xtype : 'button',
					plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
						seconds: 5
					}),
					text : arrival.i18n('tfr.arrival.ArrivalGrid.grid.arriveconfirm.button'),//'到达确认',
					disabled:!arrival.isPermission('arrival/departconfirmbutton.action'),
					hidden:!arrival.isPermission('arrival/departconfirmbutton.action'),
					gridContainer : this,
					handler:function(){
						var ids="";
						var rowObjs = me.getSelectionModel().getSelection();
						if(rowObjs.length<=0)
						{
						  Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n('请至少选择一条记录'));
						  return false;
						}
						//遍历数组对象取值进行发车确认更新操作
						Ext.Array.forEach(rowObjs,function(item){
							ids+=item.data.id+",";
						} )
						var params = {
								'arrivalVO.operationDTO.ids' : ids
							};
						//校验车牌号对应的交接单号是否已经确认到达
						Ext.Ajax.request({
							url : arrival.realPath('checkSecondCarArrivalConfirm.action'),
							params : params,
							success : function(response) {
								//判断是否向后台发起发车确认操作标志位
								var flag=false;
								var result = Ext.decode(response.responseText);
								var arrivalList=result.arrivalVO.arrivalConfirmList;
								Ext.Array.forEach( arrivalList, function(item){
									if(item.arrivalStatus==1 && item.arrivalTime!=null && item.arrivalOperator!=null){
										Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),'你所选择的车牌号所对应的交接单号已包含到达确认的,请重新进行选择');
										flag=true;
										return;
									}
								});
								if(!flag){
									Ext.Ajax.request({
										url : arrival.realPath('secondCarArrivalConfirm.action'),
										params : params,
										success : function(response) {
											arrival.secondCarArrivalPagingBar.moveFirst();
											Ext.ux.Toast.msg(arrival.i18n('tfr.arrival.DepartGrid.tips.label'), arrival.i18n('tfr.arrival.ArrivalGrid.grid.arriveconfirm.ok.tips'), 'ok', 3000);
										},
										exception : function(response) {
						    				var result = Ext.decode(response.responseText);
						    				if(result.message){
						    					Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),result.message);
						    				}else
						    				{
						    					Ext.MessageBox.alert(arrival.i18n('tfr.arrival.DepartGrid.tips.label'),arrival.i18n(result.message));
						    				}
						    			}
									});
							}
						},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    			}
						});
							
						
					}
				}];

		me.bbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					maximumSize : 800,
					plugins : Ext.create('Deppon.ux.PageSizePlugin', {
						sizeList : [['10', 10], ['50', 50], ['100', 100], ['800', 800]]
					})
				});
		arrival.secondCarArrivalPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});


Ext.define('Foss.arrival.QuerySecondCarArriveForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
		xtype : 'textfield',
		fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.billNo.label'),// '交接编号',
		name : 'billNo',
		columnWidth : .25,
		regex:/[A-Za-z0-9]+-?[A-Za-z0-9]+/
		},{
		xtype : 'commontruckselector',
		fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.vehicleNo.label'),//'车牌号',
		name : 'vehicleNo',
		displayField : 'name',
		valueField : 'code',
		columnWidth : .25,
		listeners: {
			'select': function(field, records, eOpts) {
				var record = records[0];
				arrival.arriveform.getForm().findField('vehicleNo').setValue(record.get('vehicleNo'));
			}
		}
	}, {
		xtype : 'rangeDateField',
		fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.createTime.label'),//'起止时间',
		columnWidth : .5,
		disallowBlank:true,
		fieldId:'Foss_arrival_QuerySecondCarArriveForm_createTime_Id',
		dateType : 'datetimefield_date97',
		fromName : 'startTime',
		allowBlank:false,
		dateRange:30,
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'00', '00', '00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'23', '59', '59'), 'Y-m-d H:i:s')
	},{
		xtype : 'combo',
		fieldLabel :arrival.i18n('tfr.arrival.QueryForm.form.arrivalStatus.label'),// '到达情况',
		value : '',
		name : 'arrivalStatus',
		displayField : 'name',
		valueField : 'code',
		columnWidth : .25,
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		store : Ext.create('Foss.arrival.ArrivalTypeStore')
		}, {
		xtype : 'dynamicorgcombselector',
		fieldLabel : arrival.i18n('tfr.arrival.QueryForm.form.destOrgName.label'),//'到达部门',
		type : 'ORG',
		name : 'destOrgCode',
		readOnly:true,
		columnWidth : .25,
		listeners :{
			render : function(panel,text){
				var cmbOrgCode = this.up('form').getForm().findField('destOrgCode');
				Ext.Ajax.request({
					url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
					success : function(response) {
						var result = Ext
								.decode(response.responseText);
							cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
			                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
					}
				});
		}}
	}, {
		   xtype:'container',
		   border:false,
		   html:'&nbsp;',
		   columnWidth:.25
	   },{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : arrival.i18n('tfr.arrival.QueryForm.form.reset.button'),//'重置',
			columnWidth : .2,
			handler : function() {
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('startTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'00', '00', '00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
								new Date().getMonth(), new Date().getDate(),
								'23', '59', '59'), 'Y-m-d H:i:s')); 
				var cmbOrgCode = this.up('form').getForm().findField('destOrgCode');
				Ext.Ajax.request({
					url : arrival.realPath('querySuperiorOrgByOrgCode.action'),
					success : function(response) {
						var result = Ext.decode(response.responseText);
							cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : result.arrivalVO.arrivalEntity.origOrgName}});
			                cmbOrgCode.setValue(result.arrivalVO.arrivalEntity.origOrgCode);
					}
				});
			}
		}, {
			xtype : 'container',
			columnWidth : .6,
			html : '&nbsp;'
		}, {
			text : arrival.i18n('tfr.arrival.QueryForm.form.query.button'),//'查询',
			columnWidth : .2,
			cls:'yellow_button',
			handler : function() {
				   var form = arrival.arriveform.getForm();
					if (!form.isValid())
					return false;
					arrival.secondCarArrivalPagingBar.moveFirst();
				
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/********************************* 到达本部门的数据end***************************/
Ext.onReady(function() {
	Ext.QuickTips.init();
	var departureform = Ext.create('Foss.arrival.QuerySecondCarDepartureForm');
	var departuregrid = Ext.create('Foss.arrival.DepartureSecondCarGrid');
	var arriveform = Ext.create('Foss.arrival.QuerySecondCarArriveForm');
	var arrivegrid = Ext.create('Foss.arrival.ArrivalSecondCarGrid');
	arrival.departureform=departureform;
	arrival.arriveform=arriveform; 
	Ext.create('Ext.panel.Panel',{
		id: 'T_arrival-arrivalSecondCarIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			activeTab : 0,
		    items :[{
						title : "本部门出发",//'临时任务',
						tabConfig : {
							width : 120
						},
						itemId : 'departureTab',
						items : [departureform, departuregrid]
					}, {
						title :"到达本部门",// '任务车辆',
						tabConfig : {
							width : 120
						},
						itemId : 'arriveTab',
						items : [
						         arriveform, arrivegrid
						         ]
					}]
			}],
		renderTo: 'T_arrival-arrivalSecondCarIndex-body'
	});

});