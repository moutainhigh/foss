 //定义方法，生成查询条件中“交接时间”的起始和结束时间
load.handoverbillquery.getHandOverTime4QueryForm = function(isBegin){
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

// 交接单查询条件form
Ext.define('Foss.load.handoverbillquery.QueryForm', {
	extend : 'Ext.form.Panel',
	title : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.title'),
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
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.handOverBillNoLabel'),
		name : 'handOverBillNo'
	}, {
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.departDeptLabel'),
		name : 'departDept',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y'
	}, {
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.arriveDeptLabel'),
		name : 'arriveDept',
		xtype : 'dynamicorgcombselector',
		types : 'ORG,CPPX,CPLD',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y'
	}, {
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.handOverType'),
		name : 'handOverType',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'ALL',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"SHORT_DISTANCE_HANDOVER", "value":"短配交接单"},
	            {"key":"LONG_DISTANCE_HANDOVER", "value":"集配交接单"},
	            {"key":"PARTIALLINE_HANDOVER", "value":"外发交接单"},
	            {"key":"LDP_HANDOVER", "value":"快递代理交接单"},
	            {"key":"DIVISION_HANDOVER", "value":"分部交接单"},
	            {"key":"EXPRESS_AIR_HANDOVER", "value":"快递空运交接单"}
	            
	        ]
	    }),
	    listeners:{
	    change : function(text, newValue, oldValue) {
		   var form = this.up('form').getForm();
			if(text.getValue()=='EXPRESS_AIR_HANDOVER'){
				form.findField('vehicleNo').setVisible(false);
				form.findField('flightNo').setVisible(true);
			}else{
			 form.findField('vehicleNo').setVisible(true);
			 form.findField('flightNo').setVisible(false);
			}	
	     }
	    }
	},{
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.tranGoodsType'),
		name : 'tranGoodsType',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'ALL',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"TRANSITGOODS", "value":"转货"},
	            {"key":"CARRYGOODS", "value":"带货"}
	        ]
	    })
	}, {
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.vehicleNoLabel'),
		name : 'vehicleNo',
		xtype : 'commontruckselector'
	},{
		xtype : 'commonflightselector',
		name : 'flightNo',
		fieldLabel : '航班号',
		hidden:true
	},{
		xtype : 'rangeDateField',
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.handOverTimeLabel'),
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_stock_QueryForm_HandOverTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 31,
		fromName : 'beginHandOverTime',
		fromValue : Ext.Date.format(load.handoverbillquery.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.handoverbillquery.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endHandOverTime',
		allowBlank : false,
		disallowBlank : true
	},{
		fieldLabel : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.isArrived'),
		name : 'arriveCondition',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'ALL',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"YES", "value":"是"},
	            {"key":"NO", "value":"否"}
	        ]
	    })
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
			text : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.resetButtonText'),
			handler : function() {
				this.up('form').getForm().reset();
				//重新初始化交接时间
				this.up('form').getForm().findField('beginHandOverTime').setValue(Ext.Date.format(load.handoverbillquery.getHandOverTime4QueryForm(true), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endHandOverTime').setValue(Ext.Date.format(load.handoverbillquery.getHandOverTime4QueryForm(false), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled : !load.handoverbillquery.isPermission('load/queryHandOverBillListButton'),
			hidden : !load.handoverbillquery.isPermission('load/queryHandOverBillListButton'),
			text : load.handoverbillquery.i18n('foss.load.handoverbillquery.queryForm.queryButtonText'),
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					load.handoverbillquery.pagingBar.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义查询交接单结果列表Model
Ext.define('Foss.load.handoverbillquery.QueryHandOverBillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'handOverBillNo',
		type : 'string'
	},{
		name : 'tranGoodsType',
		type : 'string'
	}, {
		name : 'handOverType',
		type : 'string'
	}, {
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
	},{
		name : 'agency',
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
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'beTrailerVehicleNo',
		type : 'string'
	}, {
		name : 'createUserName',
		type : 'string'
	}, {
		name : 'driver',
		type : 'string'
	} ,{
		name : 'driverName',
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
		name : 'driverTel',
		type : 'string'
	},{
		name : 'loadEndTime',
		type : 'date',
		convert : dateConvert
	},{
		name : 'goodsType',
		type : 'string'
	},{
		name : 'modifyUserName',
		type : 'string'
	},{
		name : 'note',
		type : 'string'
	},{
		name : 'isAgencyVisit',
		type : 'string'
	},{
		name : 'handOverBillState',
		type : 'number'
	},{
		name : 'isCreatedByPDA',
		type : 'string'
	},{
		name : 'isCarLoad',
		type : 'string'
	},{
		name : 'waybillQtyAF',
		type : 'number'
	},{
		name : 'goodsWeightAF',
		type : 'number'
	},{
		name : 'goodsVolumeAF',
		type : 'number'
	},{
		name : 'waybillQtyFLF',
		type : 'number'
	},{
		name : 'goodsWeightFLF',
		type : 'number'
	},{
		name : 'goodsVolumeFLF',
		type : 'number'
	},{
		name : 'waybillQtyFSF',
		type : 'number'
	},{
		name : 'goodsWeightFSF',
		type : 'number'
	},{
		name : 'goodsVolumeFSF',
		type : 'number'
	},{
		name : 'waybillQtyBGFLF',
		type : 'number'
	},{
		name : 'goodsWeightBGFLF',
		type : 'number'
	},{
		name : 'goodsVolumeBGFLF',
		type : 'number'
	},{
		name : 'waybillQtyBGFSF',
		type : 'number'
	},{
		name : 'goodsWeightBGFSF',
		type : 'number'
	},{
		name : 'goodsVolumeBGFSF',
		type : 'number'
	},{
		name : 'bePackage',
		type : 'string'
	}]
});

//定义交接单列表store
Ext.define('Foss.load.handoverbillquery.QueryHandOverBillStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.handoverbillquery.QueryHandOverBillModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryHandOverBillList.action'),
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
			var queryParams = load.handoverbillquery.handOverBillQueryForm.getForm().getValues();
			if(!Ext.isEmpty(queryParams.flightNo)){
				queryParams.vehicleNo=queryParams.flightNo;
			}
			Ext.apply(operation, {
				params : {
					'handOverBillVo.queryHandOverBillConditionDto.handOverBillNo' : queryParams.handOverBillNo,
					'handOverBillVo.queryHandOverBillConditionDto.departDept' :queryParams.departDept,
					'handOverBillVo.queryHandOverBillConditionDto.arriveDept' : queryParams.arriveDept,
					'handOverBillVo.queryHandOverBillConditionDto.handOverType' : queryParams.handOverType,
					'handOverBillVo.queryHandOverBillConditionDto.arriveCondition' : queryParams.arriveCondition,
					'handOverBillVo.queryHandOverBillConditionDto.vehicleNo' : queryParams.vehicleNo,
					'handOverBillVo.queryHandOverBillConditionDto.beginHandOverTime' : queryParams.beginHandOverTime,
					'handOverBillVo.queryHandOverBillConditionDto.endHandOverTime' : queryParams.endHandOverTime,
					'handOverBillVo.queryHandOverBillConditionDto.tranGoodsType' : queryParams.tranGoodsType
				}
			});	
		}
	}
});

//定义打印模版window
Ext.define('Foss.load.handoverbillquery.PrintWindow', {
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
	            {"key":"配载单", "value":"配载单打印"},
	            {"key":"配载单(流水)", "value":"配载单(流水号)打印"},
	            {"key":"交接单", "value":"交接单打印"},
	            {"key":"交接单(流水)", "value":"交接单(流水号)打印"},
	            {"key":"外发清单", "value":"外发清单打印"},
	            {"key":"外发清单(流水)", "value":"外发清单(流水号)打印"},
	            {"key":"快递代理外发清单", "value":"快递代理外发清单打印"},
				{"key":"快递代理外发清单(流水)", "value":"快递代理外发清单(流水号)打印"}
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
			if(printTemplate == '配载单' || printTemplate == '配载单(流水)') {
				//如果选择为配载单则需要先从数据库中根据交接单号查出配载单号
				Ext.Ajax.request({
					url : load.realPath('getVehicleassembleNoByHandoverNo.action'),
					params : {'handOverBillVo.handOverBillNo' : handOverBillNos[0]},
					success : function(response) {
						var result = Ext.decode(response.responseText),
							vehicleAssembleNo = result.handOverBillVo.vehicleassembleNo;
						if(vehicleAssembleNo == null || vehicleAssembleNo == '') {
							Ext.ux.Toast.msg('提示', 
									"配载单尚未生成, 不能打印!", 
									'error');
						} else {
							if(printTemplate == '配载单') {
								do_printpreview('vehicleassemblebill',{
									"vehicleAssembleNos": vehicleAssembleNo, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							} else if (printTemplate == '配载单(流水)') {
								do_printpreview('vehicleassemblebillsn',{
									"vehicleAssembleNos": vehicleAssembleNo, 
									"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
									"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
							} 
						}
					}
				});
			} else {
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
				} else if (printTemplate == '外发清单') {
					if(handOverType=='PACKAGE_HANDOVER'){
						 Ext.ux.Toast.msg('提示', 
									"快递空运交接单, 不能打印外发清单!", 
									'error');
					}else{
					 do_printpreview('partialline',{
						"handOverBillNos": handOverBillNos,
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
					}
				} else if (printTemplate == '外发清单(流水)') {
					if(handOverType=='PACKAGE_HANDOVER'){
						 Ext.ux.Toast.msg('提示', 
									"快递空运交接单, 不能打印外发清单(流水)!", 
									'error');
					}else{
					do_printpreview('partiallinesn',{
						"handOverBillNos": handOverBillNos, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
					}
				}else if (printTemplate == '快递代理外发清单') {
					if(handOverType=='PACKAGE_HANDOVER'){
						 Ext.ux.Toast.msg('提示', 
									"快递空运交接单, 不能打印快递代理外发清单!", 
									'error');
					}else{
					do_printpreview('ldppartialline',{
						"handOverBillNos": handOverBillNos,
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
					}
				} else if (printTemplate == '快递代理外发清单(流水)') {
					if(handOverType=='PACKAGE_HANDOVER'){
						 Ext.ux.Toast.msg('提示', 
									"快递空运交接单, 不能打印快递代理外发清单(流水)!", 
									'error');
					}else{
					do_printpreview('ldppartiallinesn',{
						"handOverBillNos": handOverBillNos, 
						"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
					}
				}
			}
		}
	}]
});

//定义交接单查询结果列表
Ext.define('Foss.load.handoverbillquery.QueryHandOverBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.title'),
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.resultEmptyText'),
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		handOverBillNos = new Array();
		me.store = Ext.create('Foss.load.handoverbillquery.QueryHandOverBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.addnewButtonText'),
			disabled : !load.handoverbillquery.isPermission('load/handoverbilladdnewButton'),
			hidden : !load.handoverbillquery.isPermission('load/handoverbilladdnewButton'),
			handler : function(){
				load.addTab('T_load-handoverbilladdnewindex',//对应打开的目标页面js的onReady里定义的renderTo
							load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.addnewMainTabTitle'),//打开的Tab页的标题
							load.realPath('handoverbilladdnewindex.action'));//对应的页面URL，可以在url后使用?x=123这种形式传参
			}
		},'->',{
			xtype : 'button',
			text : load.handoverbillquery.i18n('foss.load.handoverbillquery.expertExcelButtonText'),//'导出'
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}
				//获取查询参数
				var form = load.handoverbillquery.handOverBillQueryForm.getForm();
				var queryParams = form.getValues();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的交接时间！','error', 2000);
					return;
				}
				Ext.Ajax.request({
					url : load.realPath('exportHandOverBillExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'handOverBillVo.queryHandOverBillConditionDto.handOverBillNo' : queryParams.handOverBillNo,
						'handOverBillVo.queryHandOverBillConditionDto.departDept' :queryParams.departDept,
						'handOverBillVo.queryHandOverBillConditionDto.arriveDept' : queryParams.arriveDept,
						'handOverBillVo.queryHandOverBillConditionDto.handOverType' : queryParams.handOverType,
						'handOverBillVo.queryHandOverBillConditionDto.arriveCondition' : queryParams.arriveCondition,
						'handOverBillVo.queryHandOverBillConditionDto.vehicleNo' : queryParams.vehicleNo,
						'handOverBillVo.queryHandOverBillConditionDto.beginHandOverTime' : queryParams.beginHandOverTime,
						'handOverBillVo.queryHandOverBillConditionDto.endHandOverTime' : queryParams.endHandOverTime,
						'handOverBillVo.queryHandOverBillConditionDto.tranGoodsType' : queryParams.tranGoodsType
					},
					isUpload: true,
					success:function(response){
						
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(load.handoverbillshow.i18n('foss.load.handoverbillshow.waybillGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
					}
				});
			}
		},{
			xtype : 'button',
			text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.generateVehicleAssembleBillButtonText'),
			disabled : !load.handoverbillquery.isPermission('load/createvehicleassemblebillButton'),
			hidden : !load.handoverbillquery.isPermission('load/createvehicleassemblebillButton'),
			handler : function(){
				var selectedRecord = load.handoverbillquery.queryHandOverBillGrid.getSelectionModel().getSelection();
				if(selectedRecord.length == 0){
					Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
					//没有选择任何交接单！
					load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.checkedNoneBill'),
					'error', 2000);
					return;
				}else{
					var row = selectedRecord[0],
						vehicleNo = row.get('vehicleNo'),
						arriveDeptCode = row.get('arriveDeptCode'),
						driverCode = row.get('driver'),
						isCarLoad = row.get('isCarLoad'),
						handOverBillNo = row.get('handOverBillNo'),
						bePackage = row.get('bePackage');
					//比较勾选的交接单的交接类型（必须为集配）、到达部门、车牌号、状态（必须为20：已交接）
					var record;
					for(var i in selectedRecord){
						record = selectedRecord[i];
						//交接类型为集配交接单
						if(record.get('handOverType') != 'LONG_DISTANCE_HANDOVER'){
							Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							//操作失败，所选交接单必须全部为集配交接单！
							load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.allBillShouldBeLongDistanceHandOvered'),
							'error', 3000);
							return;
						}
						if(record.get('vehicleNo') != vehicleNo){
							Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							//操作失败，所选交接单车牌号必须全部相同！
							load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.vehicleNoMustBeTheSame'),
							'error', 3000);
							return;
						}
						if(record.get('driver') != driverCode){
							Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							//操作失败，所选交接单的司机必须全部相同！
							load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.driverMustBeTheSame'),
							'error', 3000);
							return;
						}
						if(record.get('isCarLoad') != isCarLoad){
							Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							//操作失败，所选交接单必须全是整车交接单或者非整车交接单
							load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.billMustBeCarLoadOrNot'),
							'error', 3000);
							return;
						}
						if(record.get('arriveDeptCode') != arriveDeptCode){
							Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							//操作失败，所选交接单到达部门必须全部相同！
							load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.arriveDeptMustBeTheSame'),
							'error', 3000);
							return;
						}
						if(record.get('handOverBillState') != 20){
							Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							//操作失败，所选交接单状态必须全部是“已交接”！
							load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.allBillMustBeHandOvered'),
							'error', 3000);
							return;
						}
						// 200968 2015-12-22  取消挂牌号交接单不能直接生成配载单的限制
						/*if(record.get('beTrailerVehicleNo') =='Y'){
							Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							'不允许使用挂牌号交接单直接生成配载单，请在配载单页面手动新增配载单！',
							'error', 3000);
							return;
						}*/
						//第一个交接单号是否为空
						if(!Ext.isEmpty(handOverBillNo)){
							//第一个交接单是否为ky KYB开头的 商务专递散件交接单或者包交接单
						  if(handOverBillNo.substring(0,2)=="ky"||handOverBillNo.substring(0,3)=="KYB"){
						  	//
						    if(!Ext.isEmpty(record.get('handOverBillNo'))){
						    //选择的交接单是否为ky KYB开头的 商务专递散件交接单或者包交接单
						     if(record.get('handOverBillNo').substring(0,2)!="ky"&&record.get('handOverBillNo').substring(0,3)!="KYB"){
						      Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
								'操作失败，不全部是商务专递的交接单不可生成配载单',
								'error', 3000);
								return;
							  }
						     }
						   }else{
						      if(record.get('handOverBillNo').substring(0,2)=="ky"||record.get('handOverBillNo').substring(0,3)=="KYB"){
							    Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
							     '操作失败，不全部是商务专递的交接单不可生成配载单', 'error', 3000);
								 return;
							   }
								if(record.get('bePackage') != bePackage){
									Ext.ux.Toast.msg(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
									'勾选的交接单必须全为包交接单或全为普通交接单！',
									'error', 3000);
									return;
								}
						  }
						}
					}
				}
				//如果tab已打开，则调用该界面的方法，对本界面勾选的交接单号进行校验、校验通过后插入该tab主页的列表内
				if(Ext.get('T_load-vehicleassemblebilladdnewindex') != null){
					load.vehicleassemblebilladdnew.validateUnAddHandOverBillList(selectedRecord);
				}
				load.addTab('T_load-vehicleassemblebilladdnewindex',//对应打开的目标页面js的onReady里定义的renderTo
						load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.crreateVehicleAssembleBillMainTabTitle'),
				load.realPath('vehicleassemblebilladdnewindex.action') + '?comeFromHandOverBillQuery=1');//对应的页面URL，可以在url后使用?x=123这种形式传参
			}
		},{
			xtype : 'button',
			text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.printButtonText'),
			disabled: !load.handoverbillquery.isPermission('load/printhandoverbillButton'),
			hidden: !load.handoverbillquery.isPermission('load/printhandoverbillButton'),
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
				Ext.create('Foss.load.handoverbillquery.PrintWindow', {
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
	load.handoverbillquery.pagingBar = me.bbar;
	me.callParent([cfg]);
},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.actionColumn'),
		align : 'center',
		items : [ {
			tooltip : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.modifyToolTipText'),
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
				load.handoverbillquery.showHandOverBillModify(handOverBillNo,isCreatedByPDA,bePackage);
			}
		},{
			tooltip : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.cancelToolTipText'),
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
				Ext.MessageBox.confirm(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
				load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.confirmCancel'),
				function(btn){
					if(btn == 'yes'){
						var myMask = new Ext.LoadMask(grid, {
							msg : load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.waitingForCancel')
						});
						myMask.show();
						Ext.Ajax.request({
							url : load.realPath('cancelHandOverBillbyNo.action'),
							params:{'handOverBillVo.handOverBillNo': handOverBillNo},
							timeout : 1800000,
							success:function(response){
								grid.store.removeAt(rowIndex);
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
								'操作成功，交接单【' + handOverBillNo + '】已作废');
								myMask.hide();
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.handoverbillquery.i18n('foss.load.handoverbillquery.alertInfo.alertTitle'),
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
		var currentDeptCode = load.handoverbillquery.superOrgCode;
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
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.handOverBillNoColumn'),
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			if(value!=null){	
				return '<a href="javascript:load.handoverbillquery.showHandOverBillDetail('+"'" + value + "'"+
				",'"+record.get('handOverType')+"'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	},{
		dataIndex: 'tranGoodsType',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.tranGoodsTypeColumn'),
		renderer: function(value){
			if(value=='TRANSITGOODS'){
				return '转货';
			}else if(value=='CARRYGOODS'){
				return '带货';
			}
		}
			
	}, {
		dataIndex : 'handOverBillState',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.stateColumn'),
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
		dataIndex : 'handOverType',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.handOverTypeColumn'),
		renderer : function(value){
			if(value == 'SALES_DEPARTMENT_HANDOVER'){
				return '营业部交接单';
			}else if(value == 'SHORT_DISTANCE_HANDOVER'){
				return '短配交接单';
			}else if(value == 'LONG_DISTANCE_HANDOVER'){
				return '集配交接单';
			}else if(value == 'PARTIALLINE_HANDOVER'){
				return '外发交接单';
			}else if(value == 'LDP_HANDOVER'){
				return '快递代理交接单';
			}else if(value=='DIVISION_HANDOVER'){
				return '分部交接单';
			}else if(value=='PACKAGE_HANDOVER'){
				return '空运快递交接单';
			}
			return value;
		}
	}, {
		dataIndex : 'handOverTime',
		align : 'center',
		width : 100,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.handOverDateColumn'),
		renderer : function(value) {
			if(value!=null){
					var date = new Date(value);
					return Ext.Date.format(date, 'Y-m-d');									
			}else{
					return null;
	}}
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.vehicleNoColumn')
	}, {
		dataIndex : 'beTrailerVehicleNo',
		align : 'center',
		width : 80,
		text :　'是否挂牌号',
		renderer : function(value){
			if(value=='Y'){
				return '是';
			}else{
				return null;
			}
		}
	}, {
		dataIndex : 'departDept',
		align : 'center',
		width : 150,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.departDeptColumn')
	}, {
		dataIndex : 'departTime',
		align : 'center',
		width : 136,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.departTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'arriveDept',
		align : 'center',
		width : 150,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.arriveDeptColumn'),
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			if(record.get('handOverType') == 'PARTIALLINE_HANDOVER'
				|| record.get('handOverType') == 'LDP_HANDOVER'){
				return '';
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'agency',
		align : 'center',
		width : 150,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.agencyColumn'),
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			if(record.get('handOverType') != 'PARTIALLINE_HANDOVER'
				&& record.get('handOverType') != 'LDP_HANDOVER'){
				return '';
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'arriveTime',
		align : 'center',
		width : 136,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.arriveTimeColumn'),
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.totalWaybillColumn')
	}, {
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.totalPiecesColumn')
	}, {
		dataIndex : 'weightTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.totalWeightColumn')
	},{
		dataIndex : 'volumeTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.totalVolumeColumn')
	}, {
		dataIndex : 'moneyTotal',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.totalMoneyColumn')
	} , {
		dataIndex : 'waybillQtyAF',
		align : 'center',
		width : 80,
		text : '空运票数'
	}, {
		dataIndex : 'goodsWeightAF',
		align : 'center',
		width : 80,
		text : '空运重量'
	}, {
		dataIndex : 'goodsVolumeAF',
		align : 'center',
		width : 80,
		text : '空运体积'
	}, {
		dataIndex : 'waybillQtyFLF',
		align : 'center',
		width : 80,
		text : '卡航票数'
	}, {
		dataIndex : 'goodsWeightFLF',
		align : 'center',
		width : 80,
		text : '卡航重量'
	}, {
		dataIndex : 'goodsVolumeFLF',
		align : 'center',
		width : 80,
		text : '卡航体积'
	}, {
		dataIndex : 'waybillQtyFSF',
		align : 'center',
		width : 80,
		text : '城运票数'
	}, {
		dataIndex : 'goodsWeightFSF',
		align : 'center',
		width : 80,
		text : '城运重量'
	}, {
		dataIndex : 'goodsVolumeFSF',
		align : 'center',
		width : 80,
		text : '城运体积'
	}, {
		dataIndex : 'waybillQtyBGFLF',
		align : 'center',
		width : 110,
		text : '大票卡航票数'
	}, {
		dataIndex : 'goodsWeightBGFLF',
		align : 'center',
		width : 110,
		text : '大票卡航重量'
	}, {
		dataIndex : 'goodsVolumeBGFLF',
		align : 'center',
		width : 110,
		text : '大票卡航体积'
	}, {
		dataIndex : 'waybillQtyBGFSF',
		align : 'center',
		width : 110,
		text : '大票城运票数'
	}, {
		dataIndex : 'goodsWeightBGFSF',
		align : 'center',
		width : 110,
		text : '大票城运重量'
	}, {
		dataIndex : 'goodsVolumeBGFSF',
		align : 'center',
		width : 110,
		text : '大票城运体积'
	}, {
		dataIndex : 'createUserName',
		align : 'center',
		width : 60,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.creatorColumn')
	}, {
		dataIndex : 'driverName',
		align : 'center',
		width : 80,
		text : load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.driverColumn')
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
load.handoverbillquery.showHandOverBillDetail = function(handOverBillNo,handOverType){
	load.addTab('T_load-handoverbillshowindex',//对应打开的目标页面js的onReady里定义的renderTo
			load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.handOverBillDetailMainTabTitle'),
			load.realPath('handoverbillshowindex.action') + '?handOverBillNo="' + handOverBillNo + '"&handOverType="'+handOverType+'"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}

//点击操作列“修改”图标打开修改界面方法
load.handoverbillquery.showHandOverBillModify = function(handOverBillNo,isCreatedByPDA,bePackage){
	if(bePackage == 'Y' || isCreatedByPDA == 'Y'){
		if(isCreatedByPDA == 'N' && bePackage == 'Y' && (handOverBillNo.match('^[kK]'))){
			load.addTab('T_load-handoverbillmodifyindex',
					load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.modifyHandOverBillMainTabTitle'),
					load.realPath('handoverbillmodifyindex.action') + '?handOverBillNo="' + handOverBillNo + '"');
		}else{
			load.addTab('T_load-handoverbillmodifypdaindex',
					load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.modifyHandOverBillMainTabTitle'),
					load.realPath('handoverbillmodifypdaindex.action') + '?handOverBillNo="' + handOverBillNo + '"' + '&bePackage="' + bePackage + '"');
		}
	}else{
		load.addTab('T_load-handoverbillmodifyindex',
				load.handoverbillquery.i18n('foss.load.handoverbillquery.resultGrid.modifyHandOverBillMainTabTitle'),
				load.realPath('handoverbillmodifyindex.action') + '?handOverBillNo="' + handOverBillNo + '"');
	}
}

//定义交接单查询结果列表
load.handoverbillquery.queryHandOverBillGrid = Ext.create('Foss.load.handoverbillquery.QueryHandOverBillGrid');
load.handoverbillquery.handOverBillQueryForm = Ext.create('Foss.load.handoverbillquery.QueryForm');
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-handoverbillqueryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [ load.handoverbillquery.handOverBillQueryForm,load.handoverbillquery.queryHandOverBillGrid],
		renderTo : 'T_load-handoverbillqueryindex-body'
	});
	var form=load.handoverbillquery.handOverBillQueryForm.getForm();
	 form.findField('vehicleNo').setVisible(true);
     form.findField('flightNo').setVisible(false);
	 form.findField('vehicleNo').setValue(null);
	 form.findField('flightNo').setValue(null);
});