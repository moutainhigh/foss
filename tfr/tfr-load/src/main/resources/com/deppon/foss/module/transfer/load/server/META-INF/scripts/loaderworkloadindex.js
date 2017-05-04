//操作类型store
Ext.define('Foss.load.loaderworkload.HandleType.Store',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//任务类型store
Ext.define('Foss.load.loaderworkload.TaskType.Store',{
	extend: 'Ext.data.Store',
	fields: [
	         {name: 'code',  type: 'string'},
	         {name: 'name',  type: 'string'}
	         ],
	         proxy: {
	        	 type: 'memory',
	        	 reader: {
	        		 type: 'json',
	        		 root: 'items'
	        	 }
	         },
	         constructor: function(config){
	        	 var me = this,
	        	 cfg = Ext.apply({}, config);
	        	 me.callParent([cfg]);
	         }
});

//个人统计的Model
Ext.define('Foss.load.loaderworkload.PersonCount.Model', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [{
				name : 'id',
				type : 'string',
				hiddenField : true
			}, {
				name : 'joinDate',
				type : 'string'
			}, {
				name : 'loaderName',
				type : 'string'
			}, {
				name : 'loaderOrgName',
				type : 'string'
			}, {
				name : 'orgName',
				type : 'string'
			}, {
				name : 'handleType',
				type : 'string'
			}, {
				name : 'taskType',
				type : 'string'
			}, {
				name : 'handoverNo',
				type : 'string'
			}, {
				name : 'vehicleNo',
				type : 'string'
			}, {
				name : 'taskNo',
				type : 'string'
			}, {
				name : 'taskBeginTime',
				convert : dateConvert,
				type : 'date'
			}, {
				name : 'taskEndTime',
				convert : dateConvert,
				type : 'date'
			}, {
				name : 'joinTime',
				convert : dateConvert,
				type : 'date'
			}, {
				name : 'leaveTime',
				convert : dateConvert,
				type : 'date'
			}, {
				name : 'weight',
				type : 'string'
			}, {
				name : 'waybillQty',
				type : 'string'
			}, {
				name : 'goodsQty',
				type : 'string'
			}, {
				name : 'notes',
				type : 'string'
			}, {
				name : 'goodsType',
				type : 'string'
			}, {
				name : 'expressCount',
				type : 'string'
			}, {
				name : 'acount',
				type : 'string'
			}, {
				name : 'bcount',
				type : 'string'
			}]
});

//队统计的Model
Ext.define('Foss.load.loaderworkload.TeamCount.Model', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [{
		name : 'id',
		type : 'string',
		hiddenField : true
	}, {
		name : 'joinDate',
		type : 'string'
	}, {
		name : 'taskPersonCount',
		type : 'string'
	}, {
		name : 'taskType',
		type : 'string'
	}, {
		name : 'unloadWeight',
		type : 'string'
	}, {
		name : 'loadWeight',
		type : 'string'
	}, {
		name : 'unloadWeight',
		type : 'string'
	}, {
		name : 'unloadWaybillQty',
		type : 'string'
	}, {
		name : 'loadWaybillQty',
		type : 'string'
	}, {
		name : 'unloadGoodsQty',
		type : 'string'
	}, {
		name : 'loadGoodsQty',
		type : 'string'
	}]
});

//差错货量扣除Model
Ext.define('Foss.load.loaderworkload.errorVolumeDeduction.Model', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [{
		name : 'id',
		type : 'string',
		hiddenField : true
	}, {
		name : 'errorId',
		type : 'string'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'respEmpCode',
		type : 'string'
	}, {
		name : 'respEmpName',
		type : 'string'
	}, {
		name : 'respDeptCode',
		type : 'string'
	}, {
		name : 'respDeptName',
		type : 'string'
	}, {
		name : 'weight',
		type : 'float'
	}, {
		name : 'delTime',
		convert : dateConvert,
		type : 'date'
	}]
});
//grid个人统计查询的Store
Ext.define('Foss.load.loaderworkload.PersonCount.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.loaderworkload.PersonCount.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryPersonCount.action'),
		reader : {
			type : 'json',
			root : 'loaderWorkloadVo.loaderWorkloadList',
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
			var queryParams = load.loaderworkload.personCountPanel.getPersonCountForm().getForm()
					.getValues();
			Ext.apply(operation, {
				params : {
					'loaderWorkloadVo.loaderWorkloadDto.handoverNo' : queryParams.handoverNo,
					'loaderWorkloadVo.loaderWorkloadDto.vehicleNo' : queryParams.vehicleNo,
					'loaderWorkloadVo.loaderWorkloadDto.loaderCode' : queryParams.loaderCode,
					'loaderWorkloadVo.loaderWorkloadDto.handleType' : queryParams.handleType,
					'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
				}
			});
		}
	}
});

//grid个人统计（快递）查询的Store
Ext.define('Foss.load.loaderworkload.PersonCountExpress.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.loaderworkload.PersonCount.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryPersonCountExpress.action'),
		reader : {
			type : 'json',
			root : 'loaderWorkloadVo.loaderWorkloadList',
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
			var queryParams = load.loaderworkload.ExpressPersonCountPanel.getExpressPersonCountForm().getForm().getValues();
			Ext.apply(operation, {
				params : {
					'loaderWorkloadVo.loaderWorkloadDto.handoverNo' : queryParams.handoverNo,
					'loaderWorkloadVo.loaderWorkloadDto.vehicleNo' : queryParams.vehicleNo,
					'loaderWorkloadVo.loaderWorkloadDto.loaderCode' : queryParams.loaderCode,
					'loaderWorkloadVo.loaderWorkloadDto.handleType' : queryParams.handleType,
					'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
				}
			});
		}
	}
});

///////快递个人统计///////////////////
Ext.define('Foss.load.loaderworkload.ExpressPersonCountForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 110,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columnWidth : 1 / 4,
		labelWidth: 85
	},
	items : [{
		name: 'handoverNo',
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.handoverNo')
	}, {
		name: 'vehicleNo',
		xtype : 'commontruckselector',
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.vehicleNo')
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderCode'),
		xtype: 'commonemployeeselector',
		name : 'loaderCode',
		parentOrgCode : load.loaderworkload.superOrgCode
	}, {
		xtype: 'combobox',
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.handleType'),
		name : 'handleType',
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.load.loaderworkload.HandleType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.loaderworkload.i18n('foss.load.loaderworkload.ALL')},
					{'code':'LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.LOAD')},
					{'code':'UNLOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.UNLOAD')},
				]
			}
		})
	}, {
		xtype: 'combobox',
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
		name : 'taskType',
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.load.loaderworkload.TaskType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.loaderworkload.i18n('foss.load.loaderworkload.ALL')},
					{'code':'LONG_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD')},
					{'code':'LONG_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD')},
					{'code':'SHORT_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD')},
					{'code':'SHORT_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD')},
					{'code':'OTHER','name':load.loaderworkload.i18n('foss.load.outsidevehiclecharge.label.OTHER')}
				]
			}
		})
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgCode'),
		xtype : 'dynamicorgcombselector',
		name : 'loaderOrgCode'
//		allowBlank : false,
	}, {
		xtype: 'rangeDateField',
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.date'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_load_loaderworkload_discoverTime_tab1_ID_Express',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		columnWidth : 4.8/10,
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank : false,
		disallowBlank : true
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},items : [{
			text: load.loaderworkload.i18n('foss.load.loaderworkload.reset'),
			xtype:"button",
			columnWidth : .08,
			handler:function(){
				this.up('form').getForm().reset();
				
				//重新初始化部门
				var cmbOrgCode = this.up('form').getForm().findField('loaderOrgCode');
				cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
				cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
				
				//重新初始化交接时间
				this.up('form').getForm().findField('beginDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1,'00','00','00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
			},
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text: load.loaderworkload.i18n('foss.load.loaderworkload.query'),
			hidden: !load.loaderworkload.isPermission('load/queryPersonCountSummaryButton'),
			xtype:"button",
			columnWidth : .08,
			cls:'yellow_button',
			handler:function(){
				var form = this.up('form').getForm();
				if(!form.isValid()){
					return;
				}
				var queryParams = load.loaderworkload.ExpressPersonCountPanel.getExpressPersonCountForm().getForm().getValues();
				var loaderCode = queryParams.loaderCode;
				var loaderOrgCode = queryParams.loaderOrgCode;
				if(Ext.isEmpty(loaderCode) && Ext.isEmpty(loaderOrgCode)) {
					Ext.ux.Toast.msg('提示', '理货员或部门必须选择一个!', 'error');
					return;
				}
				load.loaderworkload.pagingBarPersonExpress.moveFirst();
				var param = {
						'loaderWorkloadVo.loaderWorkloadDto.handoverNo' : queryParams.handoverNo,
						'loaderWorkloadVo.loaderWorkloadDto.vehicleNo' : queryParams.vehicleNo,
						'loaderWorkloadVo.loaderWorkloadDto.loaderCode' : queryParams.loaderCode,
						'loaderWorkloadVo.loaderWorkloadDto.handleType' : queryParams.handleType,
						'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
						'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
						'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
						'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
				};
				Ext.Ajax.request({
					url : load.realPath('queryPersonCountSummaryExpress.action'),
					params : param,
					success : function(response) {
						var result = Ext.decode(response.responseText),
							vo = result.loaderWorkloadVo;
						var form = load.loaderworkload.ExpressPersonCountPanel.getExpressPersonCountBottomForm().form;
						form.reset();
						/*var formA = load.loaderworkload.personCountPanel.getPersonCountBottomFormA().form;
						formA.reset();
						var formB = load.loaderworkload.personCountPanel.getPersonCountBottomFormB().form;
						formB.reset();*/
						if(vo == null) {
							form.reset();
							return;
						}
						var loadWeight = 0, 
							loadWaybillQty = 0, 
							loadGoodsQty = 0, 
							unloadWeight = 0, 
							unloadWaybillQty = 0, 
							unloadGoodsQty = 0,
							totWeight = 0,
							totWaybillQty = 0,
							totGoodsQty = 0;
						for(var i = 0; i < vo.loaderWorkloadDtos.length; i++) {
							var dto = vo.loaderWorkloadDtos[i];
							if(dto.goodsType == 'A_TYPE') {
								loadWeight += dto.loadWeight;
								formA.findField('loadWeightA').setValue(dto.loadWeight);
								
								loadWaybillQty += dto.loadWaybillQty;
								formA.findField('loadWaybillQtyA').setValue(dto.loadWaybillQty);
								
								loadGoodsQty += dto.loadGoodsQty;
								formA.findField('loadGoodsQtyA').setValue(dto.loadGoodsQty);
								
								unloadWeight += dto.unloadWeight;
								formA.findField('unloadWeightA').setValue(dto.unloadWeight);

								unloadWaybillQty += dto.unloadWaybillQty;
								formA.findField('unloadWaybillQtyA').setValue(dto.unloadWaybillQty);

								unloadGoodsQty += dto.unloadGoodsQty;
								formA.findField('unloadGoodsQtyA').setValue(dto.unloadGoodsQty);
								
								totWeight += dto.totWeight;
								totWaybillQty += dto.totWaybillQty;
								totGoodsQty += dto.totGoodsQty;
							} else if (dto.goodsType == 'B_TYPE') {
								loadWeight += dto.loadWeight;
								formB.findField('loadWeightB').setValue(dto.loadWeight);
								
								loadWaybillQty += dto.loadWaybillQty;
								formB.findField('loadWaybillQtyB').setValue(dto.loadWaybillQty);
								
								loadGoodsQty += dto.loadGoodsQty;
								formB.findField('loadGoodsQtyB').setValue(dto.loadGoodsQty);
								
								unloadWeight += dto.unloadWeight;
								formB.findField('unloadWeightB').setValue(dto.unloadWeight);
								
								unloadWaybillQty += dto.unloadWaybillQty;
								formB.findField('unloadWaybillQtyB').setValue(dto.unloadWaybillQty);
								
								unloadGoodsQty += dto.unloadGoodsQty;
								formB.findField('unloadGoodsQtyB').setValue(dto.unloadGoodsQty);
								
								totWeight += dto.totWeight;
								totWaybillQty += dto.totWaybillQty;
								totGoodsQty += dto.totGoodsQty;
							} else {
								//货物类型为"全部"
								loadWeight += dto.loadWeight;
								loadWaybillQty += dto.loadWaybillQty;
								loadGoodsQty += dto.loadGoodsQty;
								unloadWeight += dto.unloadWeight;
								unloadWaybillQty += dto.unloadWaybillQty;
								unloadGoodsQty += dto.unloadGoodsQty;
								totWeight += dto.totWeight;
								totWaybillQty += dto.totWaybillQty;
								totGoodsQty += dto.totGoodsQty;
							}
						}
						totWeight= loadWeight+unloadWeight;
						totWeight=totWeight.toFixed(2);
						totWaybillQty = loadWaybillQty + unloadWaybillQty;
						totGoodsQty = loadGoodsQty + unloadGoodsQty;
						loadWeight = loadWeight.toFixed(2);
						form.findField('loadWeight').setValue(loadWeight);
						form.findField('loadWaybillQty').setValue(loadWaybillQty);
						form.findField('loadGoodsQty').setValue(loadGoodsQty);
						unloadWeight = unloadWeight.toFixed(2);
						form.findField('unloadWeight').setValue(unloadWeight);
						form.findField('unloadWaybillQty').setValue(unloadWaybillQty);
						form.findField('unloadGoodsQty').setValue(unloadGoodsQty);
						form.findField('totWeight').setValue(totWeight);
						form.findField('totWaybillQty').setValue(totWaybillQty);
						form.findField('totGoodsQty').setValue(totGoodsQty);
					}
				});
			}
		}]
	}],
	listeners : {
		render : function(panel,text){
			var cmbOrgCode = panel.getForm().findField('loaderOrgCode');
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
		}
	}
});
/////////////////////////////end

//grid队统计查询的Store
Ext.define('Foss.load.loaderworkload.TeamCount.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.loaderworkload.TeamCount.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryTeamCount.action'),
		reader : {
			type : 'json',
			root : 'loaderWorkloadVo.loaderWorkloadList',
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
			var queryParams = load.loaderworkload.teamCountPanel.getTeamCountForm().getForm()
			.getValues();
			Ext.apply(operation, {
				params : {
					'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
				}
			});
		}
	}
});

//grid队统计查询的Store(快递)
Ext.define('Foss.load.loaderworkload.TeamCountExpress.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.loaderworkload.TeamCount.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryTeamCountExpress.action'),
		reader : {
			type : 'json',
			root : 'loaderWorkloadVo.loaderWorkloadList',
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
			var queryParams = load.loaderworkload.ExpressteamCountPanel.getTeamCountForm().getForm()
			.getValues();
			Ext.apply(operation, {
				params : {
					'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
				}
			});
		}
	}
});
//grid差错货量扣除的Store
Ext.define('Foss.load.loaderworkload.errorVolumeDeduction.Store', {
	extend : 'Ext.data.Store',
	model : 'Foss.load.loaderworkload.errorVolumeDeduction.Model',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryErrorVolumeDeductionList.action'),
		reader : {
			type : 'json',
			root : 'loaderWorkloadVo.errorVolumeDeductionList',
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
			var queryParams = load.loaderworkload.errorVolumeDeductionPanel.getErrorVolumeDeductionForm().getForm().getValues();
			Ext.apply(operation, {
				params : {
					'loaderWorkloadVo.errorVolumeDeductionDto.respDeptCode' : queryParams.respDeptCode,
					'loaderWorkloadVo.errorVolumeDeductionDto.respEmpCode' : queryParams.respEmpCode,
					'loaderWorkloadVo.errorVolumeDeductionDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.errorVolumeDeductionDto.endDate' : queryParams.endDate
				}
			});
		},
		load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records)) {
				store.removeAll();
				Ext.ux.Toast.msg('提示信息', '查询结果为空!');
			}
		    var form = load.loaderworkload.errorVolumeDeductionPanel.getErrorVolumeDeductionBottomForm().form;
			var errorCount = records.length;
			var errorTakeOffVolume = 0;
			for (var i = 0; i < errorCount; i++) {
				var record = records[i];
				errorTakeOffVolume = errorTakeOffVolume + record.data.weight;
			}
			form.findField('errorCount').setValue(errorCount);
			form.findField('errorTakeOffVolume').setValue(errorTakeOffVolume);
		}
	}
});

//个人统计查询结果Grid
Ext.define('Foss.load.loaderworkload.PersonCountGridPanel', {
	extend : 'Ext.grid.Panel',
	sortableColumns : true,
	enableColumnHide : false,
	enableColumnMove : false,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	bodyStyle : 'width:100%;',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	height:320,
	emptyText: "查询结果为空",
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
				text : load.loaderworkload.i18n('foss.load.loaderworkload.joinDate'),
				dataIndex : 'joinDate',
				width : 100,
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.loaderName'),
				dataIndex : 'loaderName',
				width : 120,
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgName'),
				dataIndex : 'loaderOrgName',
				width : 100,
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.orgName'),
				dataIndex : 'orgName',
				width : 100,
			}, {
				//任务类型
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
				dataIndex : 'taskType',
				width : 60,
				renderer : function(value) {
					switch(value) {
					case 'LONG_DISTANCE_LOAD':
						//长途装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD');
					case 'LONG_DISTANCE':
						//长途卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD');
					case 'SHORT_DISTANCE_LOAD':
						//短途装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD');
					case 'SHORT_DISTANCE':
						//短途卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD');
					case 'DELIVER_LOAD':
						//派送装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER_LOAD');
					case 'DELIVER':
						//集中卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER');
					case 'PARTIALLINE_LOAD':
						//偏线装车
						return load.loaderworkload.i18n('foss.load.loaderworkload.PARTIALLINE_LOAD');
						default: return value;
					}
				}
			}, {
				//货物类型
				text : load.loaderworkload.i18n('foss.load.loaderworkload.goodsType'),
				dataIndex : 'goodsType',
				width : 60,
				renderer : function(value) {
					switch(value) {
					case 'A_TYPE':
						//A货
						return load.loaderworkload.i18n('foss.load.loaderworkload.LD');
					case 'B_TYPE':
						//B货
						return load.loaderworkload.i18n('foss.load.loaderworkload.LD');
					case 'ALL':
						//全部
						return load.loaderworkload.i18n('foss.load.loaderworkload.LD');
					default: return value;
					}
				}
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskNo'),
				dataIndex : 'taskNo',
				width : 100
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.handoverNo'),
				dataIndex : 'handoverNo',
				width : 100,
				renderer : function(value) {
					switch(value) {
					case 'N/A':
						return '';
					default: return value;
					}
				}
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.vehicleNo'),
				dataIndex : 'vehicleNo',
				width : 100,
				renderer : function(value) {
					switch(value) {
					case 'N/A':
						return '';
					default: return value;
					}
				}
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskBeginTime'),
				dataIndex : 'taskBeginTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskEndTime'),
				dataIndex : 'taskEndTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.joinTime'),
				dataIndex : 'joinTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.leaveTime'),
				dataIndex : 'leaveTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.weight'),
				dataIndex : 'weight',
				width : 60
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.waybillQty'),
				dataIndex : 'waybillQty',
				width : 60
			}, {
				text : '件数',
				dataIndex : 'goodsQty',
				width : 60
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.notes'),
				dataIndex : 'notes',
				width : 60
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loaderworkload.PersonCount.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		load.loaderworkload.pagingBarPerson = me.bbar;
		me.callParent([cfg]);
	}
});
//////////////(快递)个人统计查询结果Grid///
Ext.define('Foss.load.loaderworkload.ExpressPersonCountGridPanel', {
	extend : 'Ext.grid.Panel',
	sortableColumns : true,
	enableColumnHide : false,
	enableColumnMove : false,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	bodyStyle : 'width:100%;',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	height:320,
	emptyText: "查询结果为空",
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
				text : load.loaderworkload.i18n('foss.load.loaderworkload.joinDate'),
				dataIndex : 'joinDate',
				width : 100,
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.loaderName'),
				dataIndex : 'loaderName',
				width : 120,
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgName'),
				dataIndex : 'loaderOrgName',
				width : 100,
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.orgName'),
				dataIndex : 'orgName',
				width : 100,
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.handleType'),
				dataIndex : 'handleType',
				width : 80,
				renderer : function(value) {
					switch(value) {
						case 'LOAD':
							//装车
							return load.loaderworkload.i18n('foss.load.loaderworkload.LOAD');
						case 'UNLOAD':
							//卸车
							return load.loaderworkload.i18n('foss.load.loaderworkload.UNLOAD');
						default: return value;
					}
				}
			}, {
				//任务类型
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
				dataIndex : 'taskType',
				width : 60,
				renderer : function(value) {
					switch(value) {
					case 'LONG_DISTANCE_LOAD':
						//集中装卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD');
					case 'LONG_DISTANCE':
						//长短途装卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD');
					case 'SHORT_DISTANCE_LOAD':
						//长短途装卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD');
					case 'SHORT_DISTANCE':
						//长短途装卸车
						return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD');
						default: return load.loaderworkload.i18n('foss.load.outsidevehiclecharge.label.OTHER');
					}
				}
			},{
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskNo'),
				dataIndex : 'taskNo',
				width : 100
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.handoverNo'),
				dataIndex : 'handoverNo',
				width : 100,
				renderer : function(value) {
					switch(value) {
					case 'N/A':
						return '';
					default: return value;
					}
				}
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.vehicleNo'),
				dataIndex : 'vehicleNo',
				width : 100,
				renderer : function(value) {
					switch(value) {
					case 'N/A':
						return '';
					default: return value;
					}
				}
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskBeginTime'),
				dataIndex : 'taskBeginTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.taskEndTime'),
				dataIndex : 'taskEndTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.joinTime'),
				dataIndex : 'joinTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.leaveTime'),
				dataIndex : 'leaveTime',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i:s',
				width : 130
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.weight'),
				dataIndex : 'weight',
				width : 60
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.waybillQty'),
				dataIndex : 'waybillQty',
				width : 60
			}, {
				text : '件数',
				dataIndex : 'goodsQty',
				width : 60
			}, {
				text : load.loaderworkload.i18n('foss.load.loaderworkload.notes'),
				dataIndex : 'notes',
				width : 60
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loaderworkload.PersonCountExpress.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		load.loaderworkload.pagingBarPersonExpress = me.bbar;
		me.callParent([cfg]);
	}
});

//队统计查询结果Grid
Ext.define('Foss.load.loaderworkload.TeamCountGridPanel', {
	extend : 'Ext.grid.Panel',
	sortableColumns : true,
	enableColumnHide : false,
	enableColumnMove : false,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	bodyStyle : 'width:100%;',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	height:320,
	emptyText: load.loaderworkload.i18n('foss.load.loaderworkload.emptyResult'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
		text : load.loaderworkload.i18n('foss.load.loaderworkload.joinDate'),
		dataIndex : 'joinDate',
		width : 100
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.taskPersonCount'),
		dataIndex : 'taskPersonCount',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
		dataIndex : 'taskType',
		flex: 1,
		renderer : function(value) {
			switch(value) {
			case 'LONG_DISTANCE_LOAD':
				//长途装车
				return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD');
			case 'LONG_DISTANCE':
				//长途卸车
				return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD');
			case 'SHORT_DISTANCE_LOAD':
				//短途装车
				return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD');
			case 'SHORT_DISTANCE':
				//短途卸车
				return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD');
			case 'DELIVER_LOAD':
				//派送装车
				return load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER_LOAD');
			case 'DELIVER':
				//集中卸车
				return load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER');
			case 'PARTIALLINE_LOAD':
				//偏线装车
				return load.loaderworkload.i18n('foss.load.loaderworkload.PARTIALLINE_LOAD');
				default: return value;
			}
		}
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWeight'),
		dataIndex : 'unloadWeight',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.loadWeight'),
		dataIndex : 'loadWeight',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWaybillQty'),
		dataIndex : 'unloadWaybillQty',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.loadWaybillQty'),
		dataIndex : 'loadWaybillQty',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.unloadGoodsQty'),
		dataIndex : 'unloadGoodsQty',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.loadGoodsQty'),
		dataIndex : 'loadGoodsQty',
		flex: 1
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loaderworkload.TeamCount.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		me.tbar = [{
			xtype : 'button',
			text : load.loaderworkload.i18n('foss.load.loaderworkload.export'),
			disabled: !load.loaderworkload.isPermission('load/exportTeamCountExcelButton'),
			hidden: !load.loaderworkload.isPermission('load/exportTeamCountExcelButton'),
			handler : function(){
				var form = load.loaderworkload.teamCountPanel.getTeamCountForm().getForm();
				if(!form.isValid()){
					return;
				}
				var queryParams = form.getValues(); 
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}		
				Ext.Ajax.request({
					url:load.realPath('exportTeamCountExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
						'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
						'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
						'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
					},
	    			isUpload: true,
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert(load.loaderworkload.i18n('foss.load.loaderworkload.exportFail'),result.message);
	    			}
				});
			}
		}];
		load.loaderworkload.pagingBarTeam = me.bbar;
		me.callParent([cfg]);
	}
});
///////////////////////(快递)队统计查询结果Grid//////////////////
Ext.define('Foss.load.loaderworkload.ExpressTeamCountGridPanel', {
	extend : 'Ext.grid.Panel',
	sortableColumns : true,
	enableColumnHide : false,
	enableColumnMove : false,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	bodyStyle : 'width:100%;',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	height:320,
	emptyText: load.loaderworkload.i18n('foss.load.loaderworkload.emptyResult'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
		text : load.loaderworkload.i18n('foss.load.loaderworkload.joinDate'),
		dataIndex : 'joinDate',
		width : 100
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.taskPersonCount'),
		dataIndex : 'taskPersonCount',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
		dataIndex : 'taskType',
		flex: 1,
		renderer : function(value) {
			switch(value) {
				case 'LONG_DISTANCE_LOAD':
					//长途装车
					return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD');
				case 'LONG_DISTANCE':
					//长途卸车
					return load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD');
				case 'SHORT_DISTANCE_LOAD':
					//短途装车
					return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD');
				case 'SHORT_DISTANCE':
					//短途卸车
					return load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD');
				default: return load.loaderworkload.i18n('foss.load.outsidevehiclecharge.label.OTHER');
			}
		}
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWeight'),
		dataIndex : 'unloadWeight',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.loadWeight'),
		dataIndex : 'loadWeight',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWaybillQty'),
		dataIndex : 'unloadWaybillQty',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.loadWaybillQty'),
		dataIndex : 'loadWaybillQty',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.unloadGoodsQty'),
		dataIndex : 'unloadGoodsQty',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.loadGoodsQty'),
		dataIndex : 'loadGoodsQty',
		flex: 1
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loaderworkload.TeamCountExpress.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
	/*	me.tbar = [{
			xtype : 'button',
			//text : load.loaderworkload.i18n('foss.load.loaderworkload.export'),
			//hidden: !load.loaderworkload.isPermission('load/exportTeamCountExcelButton'),
			handler : function(){
				var form = load.loaderworkload.ExpressPersonCountPanel.getTeamCountForm().getForm();
				if(!form.isValid()){
					return;
				}
				var queryParams = form.getValues(); 
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}		
				Ext.Ajax.request({
					url:load.realPath('exportTeamCountExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
						'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
						'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
						'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
					},
	    			isUpload: true,
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert(load.loaderworkload.i18n('foss.load.loaderworkload.exportFail'),result.message);
	    			}
				});
			}
		}];*/
		load.loaderworkload.pagingBarTeamExpress = me.bbar;
		me.callParent([cfg]);
	}
});
//差错货量扣除Grid
Ext.define('Foss.load.loaderworkload.errorVolumeDeductionGrid', {
	extend : 'Ext.grid.Panel',
	sortableColumns : true,
	enableColumnHide : false,
	enableColumnMove : false,
	// 自动增加滚动条
	autoScroll : true,
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : true,
	bodyStyle : 'width:100%;',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	height:320,
	emptyText: load.loaderworkload.i18n('foss.load.loaderworkload.emptyResult'),
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [{
		text : load.loaderworkload.i18n('foss.load.loaderworkload.respDeptName'),//部门
		dataIndex : 'respDeptName',
		width : 100
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.respEmpName'),//责任人
		dataIndex : 'respEmpName',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.delTime'),//差错成立时间
		dataIndex : 'delTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.waybillNo'),//运单号
		dataIndex : 'waybillNo',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.errorId'),//差错编号
		dataIndex : 'errorId',
		flex: 1
	}, {
		text : load.loaderworkload.i18n('foss.load.loaderworkload.deductionWeight'),//重量
		dataIndex : 'weight',
		flex: 1
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loaderworkload.errorVolumeDeduction.Store');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		me.tbar = [];
		load.loaderworkload.pagingBarTeamError = me.bbar;
		me.callParent([cfg]);
	}
});
//个人统计查询Form
Ext.define('Foss.load.loaderworkload.PersonCountForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 110,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columnWidth : 1 / 4,
		labelWidth: 85
	},
	items : [{
		name: 'handoverNo',
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.handoverNo')
	}, {
		name: 'vehicleNo',
		xtype : 'commontruckselector',
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.vehicleNo')
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderCode'),
		xtype: 'commonemployeeselector',
		name : 'loaderCode',
		parentOrgCode : load.loaderworkload.superOrgCode
	}, {
		xtype: 'combobox',
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
		name : 'taskType',
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.load.loaderworkload.TaskType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.loaderworkload.i18n('foss.load.loaderworkload.ALL')},
					// 272681 2015/10/28 任务类型
					{'code':'LONG_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD')}, //长途装车任务
					{'code':'LONG_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD')}, //长途卸车任务
					{'code':'SHORT_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD')}, //短途装车任务
					{'code':'SHORT_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD')}, //短途卸车任务
					{'code':'DELIVER_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER_LOAD')}, //派送装车任务
					{'code':'DELIVER','name':load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER')}, //集中接货卸车任务
					{'code':'PARTIALLINE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.PARTIALLINE_LOAD')} //偏线装车任务
				]
			}
		})
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgCode'),
		xtype : 'dynamicorgcombselector',
		name : 'loaderOrgCode'
//		allowBlank : false,
	}, {
		xtype: 'rangeDateField',
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.date'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_load_loaderworkload_discoverTime_tab1_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		columnWidth : 4.8/10,
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank : false,
		disallowBlank : true
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},items : [{
			text: load.loaderworkload.i18n('foss.load.loaderworkload.reset'),
			xtype:"button",
			columnWidth : .08,
			handler:function(){
				this.up('form').getForm().reset();
				
				//重新初始化部门
				var cmbOrgCode = this.up('form').getForm().findField('loaderOrgCode');
				cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
				cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
				
				//重新初始化交接时间
				this.up('form').getForm().findField('beginDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1,'00','00','00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endDate')
					.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
			},
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text: load.loaderworkload.i18n('foss.load.loaderworkload.query'),
			disabled: !load.loaderworkload.isPermission('load/exportTeamCountExcelButton'),
			hidden: !load.loaderworkload.isPermission('load/queryPersonCountSummaryButton'),
			xtype:"button",
			columnWidth : .08,
			cls:'yellow_button',
			handler:function(){
				var form = this.up('form').getForm();
				if(!form.isValid()){
					return;
				}
				var queryParams = load.loaderworkload.personCountPanel.getPersonCountForm().getForm().getValues();
				var loaderCode = queryParams.loaderCode;
				var loaderOrgCode = queryParams.loaderOrgCode;
				if(Ext.isEmpty(loaderCode) && Ext.isEmpty(loaderOrgCode)) {
					Ext.ux.Toast.msg('提示', '理货员或部门必须选择一个!', 'error');
					return;
				}
				load.loaderworkload.pagingBarPerson.moveFirst();
				var param = {
						'loaderWorkloadVo.loaderWorkloadDto.handoverNo' : queryParams.handoverNo,
						'loaderWorkloadVo.loaderWorkloadDto.vehicleNo' : queryParams.vehicleNo,
						'loaderWorkloadVo.loaderWorkloadDto.loaderCode' : queryParams.loaderCode,
						'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
						'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
						'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
						'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
				};
				Ext.Ajax.request({
					url : load.realPath('queryPersonCountSummary.action'),
					params : param,
					success : function(response) {
						var result = Ext.decode(response.responseText),
							vo = result.loaderWorkloadVo;
						var form = load.loaderworkload.personCountPanel.getPersonCountBottomForm().form;
						form.reset();
						/*var formA = load.loaderworkload.personCountPanel.getPersonCountBottomFormA().form;
						formA.reset();
						var formB = load.loaderworkload.personCountPanel.getPersonCountBottomFormB().form;
						formB.reset();*/
						if(vo == null) {
							form.reset();
							return;
						}
						var loadWeight = 0, 
							loadWaybillQty = 0, 
							loadGoodsQty = 0, 
							unloadWeight = 0, 
							unloadWaybillQty = 0, 
							unloadGoodsQty = 0,
							totWeight = 0,
							totWaybillQty = 0,
							totGoodsQty = 0;
						for(var i = 0; i < vo.loaderWorkloadDtos.length; i++) {
							var dto = vo.loaderWorkloadDtos[i];
							/*if(dto.goodsType == 'A_TYPE') {
								loadWeight += dto.loadWeight;
								formA.findField('loadWeightA').setValue(dto.loadWeight);
								
								loadWaybillQty += dto.loadWaybillQty;
								formA.findField('loadWaybillQtyA').setValue(dto.loadWaybillQty);
								
								loadGoodsQty += dto.loadGoodsQty;
								formA.findField('loadGoodsQtyA').setValue(dto.loadGoodsQty);
								
								unloadWeight += dto.unloadWeight;
								formA.findField('unloadWeightA').setValue(dto.unloadWeight);

								unloadWaybillQty += dto.unloadWaybillQty;
								formA.findField('unloadWaybillQtyA').setValue(dto.unloadWaybillQty);

								unloadGoodsQty += dto.unloadGoodsQty;
								formA.findField('unloadGoodsQtyA').setValue(dto.unloadGoodsQty);
								
								totWeight += dto.totWeight;
								totWaybillQty += dto.totWaybillQty;
								totGoodsQty += dto.totGoodsQty;
							} else if (dto.goodsType == 'B_TYPE') {
								loadWeight += dto.loadWeight;
								formB.findField('loadWeightB').setValue(dto.loadWeight);
								
								loadWaybillQty += dto.loadWaybillQty;
								formB.findField('loadWaybillQtyB').setValue(dto.loadWaybillQty);
								
								loadGoodsQty += dto.loadGoodsQty;
								formB.findField('loadGoodsQtyB').setValue(dto.loadGoodsQty);
								
								unloadWeight += dto.unloadWeight;
								formB.findField('unloadWeightB').setValue(dto.unloadWeight);
								
								unloadWaybillQty += dto.unloadWaybillQty;
								formB.findField('unloadWaybillQtyB').setValue(dto.unloadWaybillQty);
								
								unloadGoodsQty += dto.unloadGoodsQty;
								formB.findField('unloadGoodsQtyB').setValue(dto.unloadGoodsQty);
								
								totWeight += dto.totWeight;
								totWaybillQty += dto.totWaybillQty;
								totGoodsQty += dto.totGoodsQty;
							} else {*/
								//货物类型为"全部"
								loadWeight += dto.loadWeight;
								loadWaybillQty += dto.loadWaybillQty;
								loadGoodsQty += dto.loadGoodsQty;
								unloadWeight += dto.unloadWeight;
								unloadWaybillQty += dto.unloadWaybillQty;
								unloadGoodsQty += dto.unloadGoodsQty;
								totWeight += dto.totWeight;
								totWaybillQty += dto.totWaybillQty;
								totGoodsQty += dto.totGoodsQty;
							/*}*/
						}
						totWeight= loadWeight+unloadWeight;
						totWeight=totWeight.toFixed(2);
						totWaybillQty = loadWaybillQty + unloadWaybillQty;
						totGoodsQty = loadGoodsQty + unloadGoodsQty;
						loadWeight = loadWeight.toFixed(2);
						form.findField('loadWeight').setValue(loadWeight);
						form.findField('loadWaybillQty').setValue(loadWaybillQty);
						form.findField('loadGoodsQty').setValue(loadGoodsQty);
						unloadWeight = unloadWeight.toFixed(2);
						form.findField('unloadWeight').setValue(unloadWeight);
						form.findField('unloadWaybillQty').setValue(unloadWaybillQty);
						form.findField('unloadGoodsQty').setValue(unloadGoodsQty);
						form.findField('totWeight').setValue(totWeight);
						form.findField('totWaybillQty').setValue(totWaybillQty);
						form.findField('totGoodsQty').setValue(totGoodsQty);
					}
				});
			}
		}]
	}],
	listeners : {
		render : function(panel,text){
			var cmbOrgCode = panel.getForm().findField('loaderOrgCode');
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
		}
	}
});

//个人统计查询BottomForm
Ext.define('Foss.load.loaderworkload.PersonCountBottomForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 120,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 100
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadWaybillQty'),
		name : 'loadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWaybillQty'),
		name : 'unloadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totWaybillQty'),
		name : 'totWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		text: load.loaderworkload.i18n('foss.load.loaderworkload.modify'),
		disabled: !load.loaderworkload.isPermission('load/loaderworkloadmodifyButton'),
		hidden: !load.loaderworkload.isPermission('load/loaderworkloadmodifyButton'),
		xtype:"button",
		columnWidth:.125,
		height:30,
		handler:function(){
			load.loaderworkload.showLoaderworkloadmodify();
		}
	}, {
		text: load.loaderworkload.i18n('foss.load.loaderworkload.export'),
		disabled: !load.loaderworkload.isPermission('load/exportPersonCountExcelButton'),
		hidden: !load.loaderworkload.isPermission('load/exportPersonCountExcelButton'),
		xtype:"button",
		columnWidth:.125,
		height:30,
		handler:function(){
			var form = load.loaderworkload.personCountPanel.getPersonCountForm().getForm();
			if(!form.isValid()){
				return;
			}
			var queryParams = form.getValues();
			var loaderCode = queryParams.loaderCode;
			var loaderOrgCode = queryParams.loaderOrgCode;
			if(Ext.isEmpty(loaderCode) && Ext.isEmpty(loaderOrgCode)) {
				Ext.ux.Toast.msg('提示', '理货员或部门必须选择一个!', 'error');
				return;
			}
			
			if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
			}		
			Ext.Ajax.request({
				url:load.realPath('exportPersonCountExcel.action'),
				form: Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : {
					'loaderWorkloadVo.loaderWorkloadDto.loaderCode' : queryParams.loaderCode,
					'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
				},
    			isUpload: true,
    			exception : function(response) {
    				var result = Ext.decode(response.responseText);
    				top.Ext.MessageBox.alert(load.loaderworkload.i18n('foss.load.loaderworkload.exportFail'),result.message);
    			}
			});
		}
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.loadGoodsQty'),
		name : 'loadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.unloadGoodsQty'),
		name : 'unloadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.totGoodsQty'),
		name : 'totGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		xtype: 'container',
		columnWidth:.25,
		readOnly: true,
		html: '&nbsp;'
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.loadWeight'),
		name : 'loadWeight',
		readOnly: true,
		columnWidth : .253
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.unloadWeight'),
		name : 'unloadWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.totWeight'),
		name : 'totWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.takeOffWeight'),
		name : 'takeOffWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.actualTotWeight'),
		name : 'actualTotWeight',
		readOnly: true,
		columnWidth : .25
	}]
});
/////////////////(快递)个人统计查询BottomForm////////////////////
Ext.define('Foss.load.loaderworkload.ExpressPersonCountBottomForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 120,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 100
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadWaybillQty'),
		name : 'loadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWaybillQty'),
		name : 'unloadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totWaybillQty'),
		name : 'totWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		text: load.loaderworkload.i18n('foss.load.loaderworkload.modify'),
		hidden: !load.loaderworkload.isPermission('load/loaderworkloadmodifyButton'),
		xtype:"button",
		columnWidth:.125,
		height:30,
		handler:function(){
			load.loaderworkload.showLoaderworkloadmodifyExpress();
		}
	},{
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.loadGoodsQty'),
		name : 'loadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.unloadGoodsQty'),
		name : 'unloadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.totGoodsQty'),
		name : 'totGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		xtype: 'container',
		columnWidth:.25,
		readOnly: true,
		html: '&nbsp;'
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.loadWeight'),
		name : 'loadWeight',
		readOnly: true,
		columnWidth : .253
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.unloadWeight'),
		name : 'unloadWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.totWeight'),
		name : 'totWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.takeOffWeight'),
		name : 'takeOffWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.actualTotWeight'),
		name : 'actualTotWeight',
		readOnly: true,
		columnWidth : .25
	}]
});

//队统计查询Form
Ext.define('Foss.load.loaderworkload.TeamCountForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 110,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 80
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgCode'),
		xtype : 'dynamicorgcombselector',
		name : 'loaderOrgCode',
		allowBlank : false,
		columnWidth : .25,
		listeners: {
			'select': function(field, records, eOpts) {
				this.up('form').getForm().findField('loadOrgName').setValue(records[0].get('name'));
			}
		}
	}, {
		name: 'loadOrgName',
		columnWidth:.0,
		hidden: true
	}, {
		xtype: 'combobox',
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
		name : 'taskType',
		columnWidth : .25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.load.loaderworkload.TaskType.Store',{
			data: {
				'items':[
					{'code':'ALL','name':load.loaderworkload.i18n('foss.load.loaderworkload.ALL')},
					// 272681 2015/10/28 任务类型
					{'code':'LONG_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD')}, //长途装车任务
					{'code':'LONG_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD')}, //长途卸车任务
					{'code':'SHORT_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD')}, //短途装车任务
					{'code':'SHORT_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD')}, //短途卸车任务
					{'code':'DELIVER_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER_LOAD')}, //派送装车任务
					{'code':'DELIVER','name':load.loaderworkload.i18n('foss.load.loaderworkload.DELIVER')}, //集中接货卸车任务
					{'code':'PARTIALLINE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.PARTIALLINE_LOAD')} //偏线装车任务
				]
			}
		})
	}, {
		text: load.loaderworkload.i18n('foss.load.loaderworkload.query'),
		disabled: !load.loaderworkload.isPermission('load/queryTeamCountSummaryButton'),
		hidden: !load.loaderworkload.isPermission('load/queryTeamCountSummaryButton'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.12,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			load.loaderworkload.pagingBarTeam.moveFirst();
			var queryForm = load.loaderworkload.teamCountPanel.getTeamCountForm().getForm();
			var queryParams = queryForm.getValues();
			var param = {
					'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
			};
			Ext.Ajax.request({
				url : load.realPath('queryTeamCountSummary.action'),
				params : param,
				success : function(response) {
					var result = Ext.decode(response.responseText),
						dto = result.loaderWorkloadVo.loaderWorkloadDto;
					var form = load.loaderworkload.teamCountPanel.getTeamCountBottomForm().form;
					form.reset();
					if(dto == null) {
						return;
					}
					form.findField('loaderOrgCode').setValue(queryForm.findField('loaderOrgCode').rawValue);
					form.findField('taskPersonCount').setValue(dto.taskPersonCount);
					form.findField('loadWeight').setValue(dto.loadWeight);
					form.findField('loadWaybillQty').setValue(dto.loadWaybillQty);
					form.findField('loadGoodsQty').setValue(dto.loadGoodsQty);
					form.findField('unloadWeight').setValue(dto.unloadWeight);
					form.findField('unloadWaybillQty').setValue(dto.unloadWaybillQty);
					form.findField('unloadGoodsQty').setValue(dto.unloadGoodsQty);
					var totWeight = dto.loadWeight + dto.unloadWeight;
					totWeight = totWeight.toFixed(2);
					form.findField('totWeight').setValue(totWeight);
					var totWaybillQty = dto.loadWaybillQty + dto.unloadWaybillQty;
					form.findField('totWaybillQty').setValue(totWaybillQty);
					var totGoodsQty = dto.loadGoodsQty + dto.unloadGoodsQty;
					form.findField('totGoodsQty').setValue(totGoodsQty);
					var personWeightAVG = totWeight / dto.taskPersonCount;
					personWeightAVG = personWeightAVG.toFixed(2);
					form.findField('personWeightAVG').setValue(personWeightAVG);
				}
			});
		}
	}, {
		xtype: 'rangeDateField',
		labelWidth : 90,
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.date'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_load_loaderworkloadindex_discoverTime_tab2_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank : false,
		disallowBlank : true,
		columnWidth: .50
	}, {
		text: load.loaderworkload.i18n('foss.load.loaderworkload.reset'),
		xtype:"button",
		columnWidth:.12,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();

			//重新初始化部门
			var cmbOrgCode = this.up('form').getForm().findField('loaderOrgCode');
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			this.up('form').getForm().findField('loadOrgName').setValue(FossUserContext.getCurrentDept().name);
			
			//重新初始化交接时间
			this.up('form').getForm().findField('beginDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1,'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('endDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	}],
	listeners : {
		render : function(panel,text){
			var cmbOrgCode = panel.getForm().findField('loaderOrgCode');
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
			panel.getForm().findField('loadOrgName').setValue(FossUserContext.getCurrentDept().name);
		}
	}
});
///////////(快递)队查询FORM/////////////////
Ext.define('Foss.load.loaderworkload.ExpressTeamCountForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 110,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 80
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgCode'),
		xtype : 'dynamicorgcombselector',
		name : 'loaderOrgCode',
		allowBlank : false,
		columnWidth : .25,
		listeners: {
			'select': function(field, records, eOpts) {
				this.up('form').getForm().findField('loadOrgName').setValue(records[0].get('name'));
			}
		}
	}, {
		name: 'loadOrgName',
		columnWidth:.0,
		hidden: true
	}, {
		xtype: 'combobox',
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.taskType'),
		name : 'taskType',
		columnWidth : .25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		value:'ALL',
		editable:false,
		store: Ext.create('Foss.load.loaderworkload.TaskType.Store',{
			data: {
				'items':[
							{'code':'ALL','name':load.loaderworkload.i18n('foss.load.loaderworkload.ALL')},
							{'code':'LONG_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_LOAD')},
							{'code':'LONG_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.LONG_UNLOAD')},
							{'code':'SHORT_DISTANCE','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_UNLOAD')},
							{'code':'SHORT_DISTANCE_LOAD','name':load.loaderworkload.i18n('foss.load.loaderworkload.SHORT_LOAD')},
							{'code':'OTHER','name':load.loaderworkload.i18n('foss.load.outsidevehiclecharge.label.OTHER')}
						]
			}
		})
	  },{
		xtype: 'rangeDateField',
		labelWidth : 90,
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.date'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		//fieldId: 'Foss_load_loaderworkloadindex_discoverTime_tab2_ID_Express2',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		fromName: 'beginDate',
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank : false,
		disallowBlank : true,
		columnWidth: .50
	},{
		text: load.loaderworkload.i18n('foss.load.loaderworkload.reset'),
		xtype:"button",
		columnWidth:.12,
		height:30,
		handler:function(){
			this.up('form').getForm().reset();

			//重新初始化部门
			var cmbOrgCode = this.up('form').getForm().findField('loaderOrgCode');
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			this.up('form').getForm().findField('loadOrgName').setValue(FossUserContext.getCurrentDept().name);
			
			//重新初始化交接时间
			this.up('form').getForm().findField('beginDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1,'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('endDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	},{
		xtype: 'displayfield',
		border : false,
		columnWidth : .50,
		html : '&nbsp;'
	},{
		text: load.loaderworkload.i18n('foss.load.loaderworkload.query'),
		hidden: !load.loaderworkload.isPermission('load/queryTeamCountSummaryButton'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.12,
		height:30,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			load.loaderworkload.pagingBarTeamExpress.moveFirst();
			var queryForm = load.loaderworkload.ExpressteamCountPanel.getTeamCountForm().getForm();
			var queryParams = queryForm.getValues();
			var param = {
					'loaderWorkloadVo.loaderWorkloadDto.loaderOrgCode' : queryParams.loaderOrgCode,
					'loaderWorkloadVo.loaderWorkloadDto.taskType' : queryParams.taskType,
					'loaderWorkloadVo.loaderWorkloadDto.beginDate' : queryParams.beginDate,
					'loaderWorkloadVo.loaderWorkloadDto.endDate' : queryParams.endDate
			};
			Ext.Ajax.request({
				url : load.realPath('queryTeamCountSummaryExpress.action'),
				params : param,
				success : function(response) {
					var result = Ext.decode(response.responseText),
						dto = result.loaderWorkloadVo.loaderWorkloadDto;
					var form = load.loaderworkload.ExpressteamCountPanel.getTeamCountBottomForm().form;
					form.reset();
					if(dto == null) {
						return;
					}
					form.findField('loaderOrgCode').setValue(queryForm.findField('loaderOrgCode').rawValue);
					form.findField('taskPersonCount').setValue(dto.taskPersonCount);
					form.findField('loadWeight').setValue(dto.loadWeight);
					form.findField('loadWaybillQty').setValue(dto.loadWaybillQty);
					form.findField('loadGoodsQty').setValue(dto.loadGoodsQty);
					form.findField('unloadWeight').setValue(dto.unloadWeight);
					form.findField('unloadWaybillQty').setValue(dto.unloadWaybillQty);
					form.findField('unloadGoodsQty').setValue(dto.unloadGoodsQty);
					var totWeight = dto.loadWeight + dto.unloadWeight;
					totWeight = totWeight.toFixed(2);
					form.findField('totWeight').setValue(totWeight);
					var totWaybillQty = dto.loadWaybillQty + dto.unloadWaybillQty;
					form.findField('totWaybillQty').setValue(totWaybillQty);
					var totGoodsQty = dto.loadGoodsQty + dto.unloadGoodsQty;
					form.findField('totGoodsQty').setValue(totGoodsQty);
					var personWeightAVG = totWeight / dto.taskPersonCount;
					personWeightAVG = personWeightAVG.toFixed(2);
					form.findField('personWeightAVG').setValue(personWeightAVG);
				}
			});
		}
	}],
	listeners : {
		render : function(panel,text){
			var cmbOrgCode = panel.getForm().findField('loaderOrgCode');
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
			panel.getForm().findField('loadOrgName').setValue(FossUserContext.getCurrentDept().name);
		}
	}
});
/////////////end
//队统计查询BottomForm
Ext.define('Foss.load.loaderworkload.TeamCountBottomForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 120,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 100
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgName'),
		name : 'loaderOrgCode',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadWaybillQty'),
		name : 'loadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWaybillQty'),
		name : 'unloadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totWaybillQty'),
		name : 'totWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.taskPersonCountTot'),
		name : 'taskPersonCount',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadGoodsQty'),
		name : 'loadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadGoodsQty'),
		name : 'unloadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totGoodsQty'),
		name : 'totGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.personWeightAVG'),
		name : 'personWeightAVG',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadWeight'),
		readOnly: true,
		name : 'loadWeight',
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWeight'),
		name : 'unloadWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totWeight'),
		readOnly: true,
		name : 'totWeight',
		columnWidth : .25
	}]
});
//货量扣除查询Form
Ext.define('Foss.load.loaderworkload.errorVolumeDeductionForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 110,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 80
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.respDeptName'), //部门
		xtype : 'dynamicorgcombselector',
		name : 'respDeptCode',
		allowBlank : true,
		columnWidth : .25,
		listeners: {
			'select': function(field, records, eOpts) {
				this.up('form').getForm().findField('respDeptName').setValue(records[0].get('name'));
			}
		}
	}, {
		name: 'respDeptName',
		columnWidth:.0,
		hidden: true
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.respEmpName'), //责任人
		xtype: 'commonemployeeselector',
		name : 'respEmpCode',
		parentOrgCode : load.loaderworkload.superOrgCode
	}, {
		xtype: 'rangeDateField',
		labelWidth : 90,
		fieldLabel: load.loaderworkload.i18n('foss.load.loaderworkload.delTime'),
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_load_loaderworkloadindex_delTime_tab2_ID',
		dateType: 'datetimefield_date97',
		fromName: 'beginDate',
		dateRange : 30,
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1
				,'00','00','00'), 'Y-m-d H:i:s'),
		toName : 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		toName: 'endDate',
		allowBlank : false,
		disallowBlank : true,
		columnWidth: .50
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
		text: load.loaderworkload.i18n('foss.load.loaderworkload.reset'),
		xtype:"button",
		columnWidth:0.08,
		handler:function(){
			this.up('form').getForm().reset();
			//重新初始化部门
			var cmbOrgCode = this.up('form').getForm().findField('loaderOrgCode');
			cmbOrgCode.setValue(FossUserContext.getCurrentDept().code);
			cmbOrgCode.getStore().load({params:{'commonOrgVo.name' : FossUserContext.getCurrentDept().name}});
			this.up('form').getForm().findField('loadOrgName').setValue(FossUserContext.getCurrentDept().name);
			//差错成立时间
			this.up('form').getForm().findField('beginDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),1,'00','00','00'), 'Y-m-d H:i:s'));
			this.up('form').getForm().findField('endDate')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
		}
	},{
			border : false,
			columnWidth:.78,
			html: '&nbsp;'
		},{
		text: load.loaderworkload.i18n('foss.load.loaderworkload.query'),
		xtype:"button",
		cls:'yellow_button',
		columnWidth:.12,
		handler:function(){
			var form = this.up('form').getForm();
			if(!form.isValid()){
				return;
			}
			var queryParams = load.loaderworkload.errorVolumeDeductionPanel.getErrorVolumeDeductionForm().getForm().getValues();
			var respEmpCode = queryParams.respEmpCode;
			var respDeptCode = queryParams.respDeptCode;
			if(Ext.isEmpty(respEmpCode) && Ext.isEmpty(respDeptCode)) {
				Ext.ux.Toast.msg('提示', '责任人和部门至少选一个!', 'error');
				return;
			}
			load.loaderworkload.pagingBarTeamError.moveFirst();
		}
	  }]
	}],
	listeners : {
	}
});
///////////////////(快递)队统计查询BottomForm
Ext.define('Foss.load.loaderworkload.ExpressTeamCountBottomForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 120,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 100
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loaderOrgName'),
		name : 'loaderOrgCode',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadWaybillQty'),
		name : 'loadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWaybillQty'),
		name : 'unloadWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totWaybillQty'),
		name : 'totWaybillQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.taskPersonCountTot'),
		name : 'taskPersonCount',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadGoodsQty'),
		name : 'loadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadGoodsQty'),
		name : 'unloadGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totGoodsQty'),
		name : 'totGoodsQty',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.personWeightAVG'),
		name : 'personWeightAVG',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.loadWeight'),
		readOnly: true,
		name : 'loadWeight',
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.unloadWeight'),
		name : 'unloadWeight',
		readOnly: true,
		columnWidth : .25
	}, {
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.totWeight'),
		readOnly: true,
		name : 'totWeight',
		columnWidth : .25
	}]
});
/////////////end//货量扣除查询Form

//差错货量扣除BottomForm
Ext.define('Foss.load.loaderworkload.errorVolumeDeductionBottomForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : false,
	height : 80,
	defaultType: 'textfield',
	defaults : {
		margin : '5 5 5 5',
		columns : 4,
		labelWidth: 100
	},
	items : [{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.errorCount'), //差错票数
		readOnly: true,
		name : 'errorCount',
		columnWidth : .25
	},{
		fieldLabel : load.loaderworkload.i18n('foss.load.loaderworkload.errorTakeOffVolume'), //差错货量扣除
		name : 'errorTakeOffVolume',
		readOnly: true,
		columnWidth : .25
	}],
	listeners : {
		
	}
});
//个人统计panel
Ext.define('Foss.load.loaderworkload.PersonCountPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	personCountForm : null,
	getPersonCountForm : function() {
		if (this.personCountForm == null) {
			this.personCountForm = Ext.create('Foss.load.loaderworkload.PersonCountForm');
		}
		return this.personCountForm;
	},
	personCountGridPanel : null,
	getPersonCountGridPanel : function() {
		if (this.personCountGridPanel == null) {
			this.personCountGridPanel = Ext.create('Foss.load.loaderworkload.PersonCountGridPanel');
		}
		return this.personCountGridPanel;
	},
	personCountBottomForm : null,
	getPersonCountBottomForm : function() {
		if (this.personCountBottomForm == null) {
			this.personCountBottomForm = Ext.create('Foss.load.loaderworkload.PersonCountBottomForm');
		}
		return this.personCountBottomForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getPersonCountForm(), me.getPersonCountGridPanel(), me.getPersonCountBottomForm()/*, me.getPersonCountBottomFormA(), me.getPersonCountBottomFormB()*/]
		me.callParent([cfg]);
	}
});

//////////////////快递个人工作量管理/////////////////////
Ext.define('Foss.load.loaderworkload.ExpressPersonCountPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	expressPersonCountForm : null,
	getExpressPersonCountForm : function() {
		if (this.expressPersonCountForm == null) {
			this.expressPersonCountForm = Ext.create('Foss.load.loaderworkload.ExpressPersonCountForm');
		}
		return this.expressPersonCountForm;
	},
	expressPersonCountGridPanel : null,
	getExpressCountGridPanel : function() {
		if (this.expressPersonCountGridPanel == null) {
			this.expressPersonCountGridPanel = Ext.create('Foss.load.loaderworkload.ExpressPersonCountGridPanel');
		}
		return this.expressPersonCountGridPanel;
	},
	expressPersonCountBottomForm : null,
	getExpressPersonCountBottomForm : function() {
		if (this.expressPersonCountBottomForm == null) {
			this.expressPersonCountBottomForm = Ext.create('Foss.load.loaderworkload.ExpressPersonCountBottomForm');
		}
		return this.expressPersonCountBottomForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getExpressPersonCountForm(), me.getExpressCountGridPanel(), me.getExpressPersonCountBottomForm()]
		me.callParent([cfg]);
	}
});

//队统计panel
Ext.define('Foss.load.loaderworkload.TeamCountPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	teamCountForm : null,
	getTeamCountForm : function() {
		if (this.teamCountForm == null) {
			this.teamCountForm = Ext.create('Foss.load.loaderworkload.TeamCountForm');
		}
		return this.teamCountForm;
	},
	teamCountGridPanel : null,
	getTeamCountGridPanel : function() {
		if (this.teamCountGridPanel == null) {
			this.teamCountGridPanel = Ext.create('Foss.load.loaderworkload.TeamCountGridPanel');
		}
		return this.teamCountGridPanel;
	},
	teamCountBottomForm : null,
	getTeamCountBottomForm : function() {
		if (this.teamCountBottomForm == null) {
			this.teamCountBottomForm = Ext.create('Foss.load.loaderworkload.TeamCountBottomForm');
		}
		return this.teamCountBottomForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getTeamCountForm(), me.getTeamCountGridPanel(), me.getTeamCountBottomForm()]
		me.callParent([cfg]);
	}
});
///////////////(快递)队统计panel////////////////
Ext.define('Foss.load.loaderworkload.ExpressTeamCountPanel', {
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	teamCountForm : null,
	getTeamCountForm : function() {
		if (this.teamCountForm == null) {
			this.teamCountForm = Ext.create('Foss.load.loaderworkload.ExpressTeamCountForm');
		}
		return this.teamCountForm;
	},
	teamCountGridPanel : null,
	getTeamCountGridPanel : function() {
		if (this.teamCountGridPanel == null) {
			this.teamCountGridPanel = Ext.create('Foss.load.loaderworkload.ExpressTeamCountGridPanel');
		}
		return this.teamCountGridPanel;
	},
	teamCountBottomForm : null,
	getTeamCountBottomForm : function() {
		if (this.teamCountBottomForm == null) {
			this.teamCountBottomForm = Ext.create('Foss.load.loaderworkload.ExpressTeamCountBottomForm');
		}
		return this.teamCountBottomForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getTeamCountForm(), me.getTeamCountGridPanel(), me.getTeamCountBottomForm()]
		me.callParent([cfg]);
	}
});
//////////////////////////////end

//差错货量扣除
Ext.define('Foss.load.loaderworkload.errorVolumeDeductionPanel',{
	extend : 'Ext.container.Container',
	frame : true,
	autoScroll : true,
	errorVolumeDeductionGrid : null,
	getErrorVolumeDeductionPanel : function(){
		if(this.errorVolumeDeductionGrid == null){
			this.errorVolumeDeductionGrid = Ext.create('Foss.load.loaderworkload.errorVolumeDeductionGrid');
		}
		return this.errorVolumeDeductionGrid;
	},
	errorVolumeDeductionForm : null,
	getErrorVolumeDeductionForm : function(){
		if(this.errorVolumeDeductionForm == null){
			this.errorVolumeDeductionForm = Ext.create('Foss.load.loaderworkload.errorVolumeDeductionForm');
		}
		return this.errorVolumeDeductionForm;
	},
	errorVolumeDeductionBottomForm : null,
	getErrorVolumeDeductionBottomForm : function(){
		if(this.errorVolumeDeductionBottomForm == null){
			this.errorVolumeDeductionBottomForm = Ext.create('Foss.load.loaderworkload.errorVolumeDeductionBottomForm');
		}
		return this.errorVolumeDeductionBottomForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
	//	me.items = [me.getErrorVolumeDeductionPanel(), me.getTeamCountGridPanel(), me.getTeamCountBottomForm()]
		me.items = [me.getErrorVolumeDeductionForm(),me.getErrorVolumeDeductionPanel(),me.getErrorVolumeDeductionBottomForm()]
		me.callParent([cfg]);
	}
});
//点击“任务编号”打开详情界面方法,修改装卸车工作量
load.loaderworkload.showLoaderworkloadmodify = function(){
	addTab('T_load-loaderworkloadmodify',//对应打开的目标页面js的onReady里定义的renderTo
			load.loaderworkload.i18n('foss.load.loaderworkload.loaderworkloadmodify'),//打开的Tab页的标题
			load.realPath('loaderworkloadmodify.action'));//对应的页面URL，可以在url后使用?x=123这种形式传参
			
}
//点击“任务编号”打开详情界面方法,修改装卸车工作量（快递）
load.loaderworkload.showLoaderworkloadmodifyExpress = function(){
	addTab('T_load-loaderworkloadmodifyExpress',//对应打开的目标页面js的onReady里定义的renderTo
			load.loaderworkload.i18n('foss.load.loaderworkload.loaderworkloadmodifyExpress'),//打开的Tab页的标题
			load.realPath('loaderworkloadmodifyExpress.action'));//对应的页面URL，可以在url后使用?x=123这种形式传参
			
}

Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	
	
	Ext.Ajax.request({
		url: load.realPath('querySuperiorOrgByOrgCode.action'),
		async: false,
		success: function(response){
			var result = Ext.decode(response.responseText),
				loaderWorkloadVo = result.loaderWorkloadVo;
			load.loaderworkload.superOrgCode = loaderWorkloadVo.superOrgCode;
		},
		exception: function(response){
	    	var result = Ext.decode(response.responseText);
	    	Ext.ux.Toast.msg(load.loaderworkload.i18n('foss.load.loaderworkload.prompt'), result.message);
	    }
	});
	
	var personCountPanel = Ext.create('Foss.load.loaderworkload.PersonCountPanel');
	var ExpresspersonCountPanel=Ext.create('Foss.load.loaderworkload.ExpressPersonCountPanel');
	var teamCountPanel = Ext.create('Foss.load.loaderworkload.TeamCountPanel');
	var ExpressteamCountPanel= Ext.create('Foss.load.loaderworkload.ExpressTeamCountPanel');
	var errorVolumeDeductionPanel = Ext.create("Foss.load.loaderworkload.errorVolumeDeductionPanel");
	load.loaderworkload.personCountPanel = personCountPanel;
	load.loaderworkload.ExpressPersonCountPanel = ExpresspersonCountPanel;
	load.loaderworkload.teamCountPanel = teamCountPanel;
	load.loaderworkload.ExpressteamCountPanel = ExpressteamCountPanel;
	load.loaderworkload.errorVolumeDeductionPanel = errorVolumeDeductionPanel;
	Ext.create('Ext.panel.Panel', {
				id : 'T_load-loaderworkloadindex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				// 定义容器中的项
				items : [{
							xtype : 'tabpanel',
							frame : false,
							bodyCls : 'autoHeight',
							cls : 'innerTabPanel',
							activeTab : 0,
							items : [{
										title : load.loaderworkload.i18n('foss.load.loaderworkload.personCount'),
										tabConfig : {
											width : 120
										},
										itemId : 'TemporaryAssignments',
										items : personCountPanel
								     }, {
										title : load.loaderworkload.i18n('foss.load.loaderworkload.teamCountPanel'),
										tabConfig : {
											width : 120
										},
										itemId : 'TaskAssignments',
										items : teamCountPanel
									 },{
										title : load.loaderworkload.i18n('foss.load.loaderworkload.errorVolumeDeduction'),
										tabConfig : {
											width : 120
										},
										itemId : 'errorVolumeDeductionPanel',
										items : errorVolumeDeductionPanel
									},{
										title : load.loaderworkload.i18n('foss.load.loaderworkload.ExpresspersonCount'),
										tabConfig : {
										width : 120
											},
										itemId : 'ExpressTemporaryAssignments',
										items : ExpresspersonCountPanel
						            },{
								        title : load.loaderworkload.i18n('foss.load.loaderworkload.ExpressteamCountPanels'),
									    tabConfig : {
										width : 120
									},
									    itemId : 'ExpressTaskAssignments',
									    items : ExpressteamCountPanel
								   }]
						}],
				renderTo : 'T_load-loaderworkloadindex-body'
			});
});