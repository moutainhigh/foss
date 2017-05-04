 //定义方法，生成查询条件中“交接时间”的起始和结束时间
load.handoverbillsalequery.getHandOverTime4QueryForm = function(isBegin){
	var nowDate = new Date();
	if(isBegin){
		nowDate.setHours(0);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
	}else{
		nowDate.setHours(23);	
		nowDate.setSeconds(59);
		nowDate.setMinutes(59);
	}
	return nowDate;
}

// 营业部交接单查询条件form
Ext.define('Foss.load.handoverbillsalequery.QueryForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.queryForm.title'),
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.queryForm.handOverBillNoLabel'),
		name : 'handOverBillNo'
	}, {
		fieldLabel : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.queryForm.departDeptLabel'),
		name : 'departDept',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y'
	}, {
		fieldLabel : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.queryForm.arriveDeptLabel'),
		name : 'arriveDept',
		xtype : 'dynamicorgcombselector',
		types : 'ORG,CPPX,CPLD',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y'
	},{
		xtype : 'rangeDateField',
		fieldLabel : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.queryForm.handOverTimeLabel'),
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_stocksale_QueryForm_HandOverTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 7,
		fromName : 'beginHandOverTime',
		fromValue : Ext.Date.format(load.handoverbillsalequery.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.handoverbillsalequery.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endHandOverTime',
		allowBlank : false,
		disallowBlank : true
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.queryForm.resetButtonText'),
			handler : function() {
				this.up('form').getForm().reset();
				//重新初始化交接时间
				this.up('form').getForm().findField('beginHandOverTime').setValue(Ext.Date.format(load.handoverbillsalequery.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endHandOverTime').setValue(Ext.Date.format(load.handoverbillsalequery.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled : !load.handoverbillsalequery.isPermission('load/queryHandOverBillSaleListButton'),
			hidden : !load.handoverbillsalequery.isPermission('load/queryHandOverBillSaleListButton'),
			text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.queryForm.queryButtonText'),
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					load.handoverbillsalequery.pagingBar.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义查询营业部交接单结果列表Model
Ext.define('Foss.load.handoverbillsalequery.QueryHandOverBillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'handOverBillNo',
		type : 'string'
	},{
		name : 'handOverBillState',
		type : 'number'
	},{
		name : 'handOverTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'departDept',
		type : 'string'
	}, {
		name : 'departDeptCode',
		type : 'string'
	}, {
		name : 'arriveDept',
		type : 'string'
	}, {
		name : 'arriveDeptCode',
		type : 'string'
	}, {
		name : 'departTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'arriveTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'volumeTotal',
		type : 'number'
	},{
		name : 'goodsQtyTotal',
		type : 'number'
	},{
		name : 'moneyTotal',	
		type : 'number'
	},{
		name : 'waybillQtyTotal',
		type : 'number'
	},{
		name : 'codAmountTotal',
		type : 'number'
	},{
		name : 'weightTotal',
		type : 'number'
	},{
		name : 'loadEndTime',
		type : 'date',
		convert : dateConvert
	}]
});

//定义营业部交接单列表store
Ext.define('Foss.load.handoverbillsalequery.QueryHandOverBillStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.handoverbillsalequery.QueryHandOverBillModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryHandOverBillSaleList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'handOverBillVo.handOverBillList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = load.handoverbillsalequery.handOverBillQueryForm.getForm().getValues();
			queryParams.handOverType = 'SALES_DEPARTMENT_HANDOVER';
			Ext.apply(operation, {
				params : {
					'handOverBillVo.queryHandOverBillConditionDto.handOverBillNo' : queryParams.handOverBillNo,
					'handOverBillVo.queryHandOverBillConditionDto.handOverType' : queryParams.handOverType,
					'handOverBillVo.queryHandOverBillConditionDto.departDept' :queryParams.departDept,
					'handOverBillVo.queryHandOverBillConditionDto.arriveDept' : queryParams.arriveDept,
					'handOverBillVo.queryHandOverBillConditionDto.beginHandOverTime' : queryParams.beginHandOverTime,
					'handOverBillVo.queryHandOverBillConditionDto.endHandOverTime' : queryParams.endHandOverTime
				}
			});	
		}
	}
});

//定义打印模版window
Ext.define('Foss.load.handoverbillsalequery.PrintWindow', {
	extend: 'Ext.window.Window',
	title: '打印模板选择',
	layout:'column',
	height: 150,
	width: 300,
	closable:true,
	closeAction:'hide',
	modal: true,
	handOverBillNos : null,
	vehicleNo : null,
	grid : null,
	items : [{
		fieldLabel : '打印模版',
		name : 'printTemplate',
		columnWidth: 1,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    defaults: {
			margin: '10 5 10 5'
		},
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"交接单", "value":"交接单打印"},
	            {"key":"交接单(流水)", "value":"交接单(流水号)打印"}
	        ]
	    })
	},{
		xtype: 'container',
		columnWidth: .6,
		html: '&nbsp;'
	},{
		columnWidth : .39,
		xtype : 'button',
		text : '打印',
		handler : function(){
			var upwindow = this.up('window'),
				printTemplate = upwindow.items.items[0].getValue(),
				records	= upwindow.grid.getSelectionModel().getSelection(),
				vehicleNo	= upwindow.vehicleNo,
				handOverType = upwindow.handOverType,
				handOverBillNos = upwindow.handOverBillNos;
			var vehicleAssembleNo = '';
			var currentDeptCode = FossUserContext.getCurrentDept().code;
			var currentDeptName = FossUserContext.getCurrentDept().name;
			var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
			var currentUserName = FossUserContext.getCurrentUser().employee.empName;
			if (printTemplate == '交接单') {
				do_printpreview('load',{
					"handOverBillNos": handOverBillNos, 
					"handOverType" : handOverType,
					"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
					"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
			} else if (printTemplate == '交接单(流水)') {
				do_printpreview('loadsn',{
					"handOverBillNos": handOverBillNos, 
					"handOverType" : handOverType,
					"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
					"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
		 } 
		}
	}]
});

//定义营业部交接单查询结果列表
Ext.define('Foss.load.handoverbillsalequery.QueryHandOverBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.title'),
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.resultEmptyText'),
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		handOverBillNos = new Array();
		me.store = Ext.create('Foss.load.handoverbillsalequery.QueryHandOverBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.addnewButtonText'),
			disabled : !load.handoverbillsalequery.isPermission('load/handoverbillsaleaddnewButton'),
			hidden : !load.handoverbillsalequery.isPermission('load/handoverbillsaleaddnewButton'),
			handler : function(){
				load.addTab('T_load-handoverbillsaleaddnewindex',//对应打开的目标页面js的onReady里定义的renderTo
							load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.addnewMainTabTitle'),//打开的Tab页的标题
							load.realPath('handoverbillsaleaddnewindex.action'));//对应的页面URL，可以在url后使用?x=123这种形式传参
			}
		},'->',{
			xtype : 'button',
			text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.printButtonText'),
			disabled: !load.handoverbillsalequery.isPermission('load/printhandoverbillsaleButton'),
			hidden: !load.handoverbillsalequery.isPermission('load/printhandoverbillsaleButton'),
			handler : function() {
				var records = me.getSelectionModel().getSelection();
				if(records.length == 0){
					Ext.MessageBox.show({
						title:"提示",
						msg:"请先选择您要操作的行!"
					});
					return;
				}
				//如果选择的交接单属于多个不同的车牌则不能打印
				var vehicleNo = '',
					isdiff = false,
					handOverBillNos = new Array();
					var handOverType='';
				for(var i = 0; i < records.length; i++) {
					vehicleNo = records[i].get('vehicleNo');
					handOverType =  records[i].get('handOverType');
					break;
				}
				for(var i = 0; i < records.length; i++) {
					var no = records[i].get('vehicleNo');
					var handOverBillNo = records[i].get('handOverBillNo');
					handOverBillNos.push(handOverBillNo);
					if(vehicleNo != no) {
						//所选择的车牌号不相同
						isdiff = true;
						break;
					}
				}
				if(isdiff) {
					Ext.MessageBox.show({
						title:"提示",
						msg:"选择的交接单属于多个车牌, 不能打印!"
					});
					return;
				}
				//如果选择的交接单的车牌号下还有其他的交接单则提示还有其他交接单,请注意
				Ext.Ajax.request({
					url : load.realPath('checkPrintHandOverBill.action'),
					params : {'handOverBillVo.vehicleNo' : vehicleNo,
						'handOverBillVo.handOverBillNos' : handOverBillNos
					},
					success : function(response) {
						var result = Ext.decode(response.responseText),
							count = result.handOverBillVo.checkPrintHandOverBillRestlt;
						if(count > 0) {
							//大于0则说明该车牌号下还有其他交接单尚未选择
							Ext.ux.Toast.msg('提示', 
									"此车牌中还有" + count + "个交接单没有选择打印，请注意!", 
									'error');
						} else {
							
						}
					}
				});
				Ext.create('Foss.load.handoverbillsalequery.PrintWindow', {
					vehicleNo : vehicleNo,
					handOverBillNos : handOverBillNos,
					handOverType : handOverType,
					grid : me
				}).show();
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
	load.handoverbillsalequery.pagingBar = me.bbar;
	me.callParent([cfg]);
},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.actionColumn'),
		align : 'center',
		items : [ {
			tooltip : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.modifyToolTipText'),
			iconCls : 'deppon_icons_edit',
			handler : function(grid, rowIndex, colIndex) {
				var rec = grid.store.getAt(rowIndex),
					handOverBillNo = rec.get('handOverBillNo'),
					isCreatedByPDA = rec.get('isCreatedByPDA'),
					bePackage = rec.get('bePackage');
				/**
				 *205109 zenghaibin 修改 
				 *2014/08/14
				 ***/
				var holdingState1='N'//预提状态，默认可修改，如果该交接单在租车记录表中预提状态为：预提中或者已预提，则不可修改
				Ext.Ajax.request({
					url : load.realPath('queryHoldingstate.action'),//检查交接单在租车标记表的预提状态
					async :  false,
					params : {
							   'handOverBillVo.handOverBillNo':handOverBillNo
							  },
					success : function(response) {
						var result = Ext.decode(response.responseText);
						 holdingState1=result.handOverBillVo.holdingState;//是否预提状态
					},
					exception:function (response){
					
						Ext.Msg.alert("异常",Ext.decode(response.responseText).message);
					}
				});

				if(holdingState1=='Y'){
						
					Ext.Msg.alert("提示","该交接单不可修改");//("提示","不可修改")
					return;
				}
				load.handoverbillsalequery.showHandOverBillModify(handOverBillNo,isCreatedByPDA,bePackage);
			}
		},{
			tooltip : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.cancelToolTipText'),
			iconCls : 'deppon_icons_delete',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				var handOverBillNo = record.get('handOverBillNo');
				
				/**
				 *205109 zenghaibin 修改 
				 *2014/08/14
				 ***/
				var holdingState2='Y'//预提状态，默认不可修改，如果该交接单在租车记录表中预提状态为：预提中或者已预提，则不可修改
				Ext.Ajax.request({
					url : load.realPath('queryHoldingstate.action'),//检查交接单在租车标记表的预提状态
					async :  false,
					params : {
							   'handOverBillVo.handOverBillNo':handOverBillNo
							  },
					success : function(response) {
						var result = Ext.decode(response.responseText);
						holdingState2=result.handOverBillVo.holdingState;//是否预提状态
					},
					exception:function (response){
					
						Ext.Msg.alert("异常",Ext.decode(response.responseText).message);
					}
				});
				if(holdingState2=='Y'){
						
					Ext.Msg.alert("提示","该交接单不可删除");//("提示","不可删除")
					return;
				}
				Ext.MessageBox.confirm(load.handoverbillsalequery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
				load.handoverbillsalequery.i18n('foss.load.handoverbillquery.alertInfo.confirmCancel'),
				function(btn){
					if(btn == 'yes'){
						var myMask = new Ext.LoadMask(grid, {
							msg : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.alertInfo.waitingForCancel')
						});
						myMask.show();
						Ext.Ajax.request({
							url : load.realPath('cancelHandOverBillSalebyNo.action'),
							params:{'handOverBillVo.handOverBillNo': handOverBillNo},
							timeout : 1800000,
							success:function(response){
								grid.store.removeAt(rowIndex);
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert(load.handoverbillsalequery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
								'操作成功，交接单【' + handOverBillNo + '】已作废');
								myMask.hide();
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.handoverbillsalequery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
			    				result.message);
			    				myMask.hide();
			    			}
						});
					}
				});
		} 
	} ],
	renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
		//获取当前部门编码(大部门)
		var currentDeptCode = load.handoverbillsalequery.superOrgCode;
		/**
		 * 只能修改符合以下条件的交接单：
				1、“出发部门”为本部门；
				2、交接单状态为“已预配”、'已配载'、“已交接”或“已出发”；
				有任意一条不符合，则隐藏作废按钮
		 */
		if(currentDeptCode == record.get('departDeptCode')
				&& (record.get('handOverBillState') == 10
						|| record.get('handOverBillState') == 20
						|| record.get('handOverBillState') == 21
						|| (record.get('handOverBillState') == 30 
					   && record.get('handOverType') != 'SALES_DEPARTMENT_HANDOVER'))){
			this.items[0].iconCls = 'deppon_icons_edit';
		}else{
			this.items[0].iconCls = '';
		}
		/**
		 * 只能作废符合以下条件的交接单：
				1、“出发部门”为本部门；
				2、状态为“已预配”或“已交接”；
				3、非PDA生成；
				有任意一条不符合，则隐藏作废按钮
		 */
		if(currentDeptCode == record.get('departDeptCode')
				&& (record.get('handOverBillState') == 10 || record.get('handOverBillState') == 20)
				&& ( record.get('isCreatedByPDA') != 'Y' || (record.get('isCreatedByPDA') == 'Y'&& record.get('handOverType')=='LDP_HANDOVER') 
						)){
			this.items[1].iconCls = 'deppon_icons_delete';
		}else{
			this.items[1].iconCls = '';
		}
	}
	}, {
		dataIndex : 'handOverBillNo',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.handOverBillNoColumn'),
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			if(value!=null){	
				return '<a href="javascript:load.handoverbillsalequery.showHandOverBillDetail('+"'" + value + "'"+
				",'"+record.get('handOverType')+"'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	}, {
		dataIndex : 'handOverBillState',
		align : 'center',
		width : 80,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.stateColumn'),
		renderer : function(value){
			if(value == 10){
				return '已预配';
			}else if(value == 20){
				return '已交接';
			}else if(value == 21){
				return '已配载';
			}else if(value == 30){
				return '已出发';
			}else if(value == 40){
				return '已到达';
			}else if(value == 50){
				return '已入库';
			}else if(value == 90){
				return '已作废';
			}
		}
	},{
		dataIndex : 'handOverTime',
		align : 'center',
		width : 100,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.handOverDateColumn'),
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d');									
			}else{
					return null;
			}
	   }
	}, {
		dataIndex : 'departDept',
		align : 'center',
		width : 150,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.departDeptColumn')
	}, {
		dataIndex : 'departTime',
		align : 'center',
		width : 136,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.departTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.arriveDeptColumn'),
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			if(record.get('handOverType') == 'PARTIALLINE_HANDOVER'
				|| record.get('handOverType') == 'LDP_HANDOVER'){
				return '';
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'arriveTime',
		align : 'center',
		width : 136,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.arriveTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.totalWaybillColumn')
	}, {
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.totalPiecesColumn')
	}, {
		dataIndex : 'weightTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.totalWeightColumn')
	},{
		dataIndex : 'volumeTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.totalVolumeColumn')
	}],
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowParams, rp, store) {
			var isCreatedByPDA = record.get('isCreatedByPDA');
			if(isCreatedByPDA == 'Y') {
				return 'handOverBill_query_background';
			}
		}
	}
});

//点击“交接单编号”打开详情界面方法
load.handoverbillsalequery.showHandOverBillDetail = function(handOverBillNo,handOverType){
	load.addTab('T_load-handoverbillsaleshowindex',//对应打开的目标页面js的onReady里定义的renderTo
			load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.handOverBillDetailMainTabTitle'),
			load.realPath('handoverbillsaleshowindex.action') + '?handOverBillNo="' + handOverBillNo + '"&handOverType="'+handOverType+'"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}

//点击操作列“修改”图标打开修改界面方法
load.handoverbillsalequery.showHandOverBillModify = function(handOverBillNo,isCreatedByPDA,bePackage){
	if(bePackage == 'Y' || isCreatedByPDA == 'Y'){
		if(isCreatedByPDA == 'N' && bePackage == 'Y' && (handOverBillNo.match('^[kK]'))){
			load.addTab('T_load-handoverbillsalemodifyindex',
					load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.modifyHandOverBillMainTabTitle'),
					load.realPath('handoverbillsalemodifyindex.action') + '?handOverBillNo="' + handOverBillNo + '"');
		}else{
			load.addTab('T_load-handoverbillsalemodifypdaindex',
					load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.modifyHandOverBillMainTabTitle'),
					load.realPath('handoverbillsalemodifypdaindex.action') + '?handOverBillNo="' + handOverBillNo + '"' + '&bePackage="' + bePackage + '"');
		}
	}else{
		load.addTab('T_load-handoverbillsalemodifyindex',
				load.handoverbillsalequery.i18n('foss.load.handoverbillquery.resultGrid.modifyHandOverBillMainTabTitle'),
				load.realPath('handoverbillsalemodifyindex.action') + '?handOverBillNo="' + handOverBillNo + '"');
	}
}

//定义交接单查询结果列表
load.handoverbillsalequery.queryHandOverBillGrid = Ext.create('Foss.load.handoverbillsalequery.QueryHandOverBillGrid');
load.handoverbillsalequery.handOverBillQueryForm = Ext.create('Foss.load.handoverbillsalequery.QueryForm');
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-handoverbillsalequeryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [ load.handoverbillsalequery.handOverBillQueryForm,load.handoverbillsalequery.queryHandOverBillGrid],
		renderTo : 'T_load-handoverbillsalequeryindex-body'
	});
});