//定义方法，生成查询条件中“制单时间”的起始和结束时间
load.vehicleassemblebillquery.getCreateTime4QueryForm = function(isBegin){
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

// 查询条件form
Ext.define('Foss.load.vehicleassemblebillquery.QueryForm', {
	extend : 'Ext.form.Panel',
	title : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.queryForm.title')/*'查询条件'*/,
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
		fieldLabel : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.vehicleAssembleNoColumn')/*'配载车次号'*/,
		name : 'vehicleAssembleNo'
	}, {
		fieldLabel : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.assembleDeptColumn')/*'配载部门'*/,
		name : 'assembleDept',
		xtype : 'dynamicorgcombselector'
	}, {
		fieldLabel : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.queryForm.assembleTypeLabel')/*'配载类型'*/,
		name : 'assembleType',
		value : 'ALL',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"OWNER_LINE", "value":"专线"},
	            {"key":"CAR_LOAD", "value":"整车"}
	        ]
	    })
	},{
		fieldLabel : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.queryForm.driverLabel')/*'司机'*/,
		name : 'driver',
		xtype : 'commondriverselector'
	},{
		fieldLabel : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.arriveDeptColumn')/*'到达部门'*/,
		name : 'arriveDept',
		xtype : 'dynamicorgcombselector'
	}, {
		fieldLabel : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.vehicleNoColumn')/*'车牌号'*/,
		name : 'vehicleNo',
		xtype : 'commontruckselector'
	},{
		xtype : 'rangeDateField',
		fieldLabel : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.queryForm.createTimeLabel')/*'制单时间'*/,
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_load_vehicleAssembleBill_QueryForm_CreateTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 31,
		fromName : 'beginCreateTime',
		fromValue : Ext.Date.format(load.vehicleassemblebillquery.getCreateTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.vehicleassemblebillquery.getCreateTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endCreateTime',
		allowBlank : false,
		disallowBlank : true
	},{
		fieldLabel :"车辆所有权类别"/*'车辆所属类型'*/,
		name : 'vehicleType',
		value : 'ALL',
		xtype : 'combobox',
		labelWidth : 100,
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"Company", "value":"公司车"},
	            {"key":"Leased", "value":"外请车"}
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
			text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.queryForm.resetButtonText')/*'重置'*/,
			handler : function() {
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginCreateTime').setValue(Ext.Date.format(load.vehicleassemblebillquery.getCreateTime4QueryForm(true), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endCreateTime').setValue(Ext.Date.format(load.vehicleassemblebillquery.getCreateTime4QueryForm(false), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled : !load.vehicleassemblebillquery.isPermission('load/queryVehicleAssembleBillListButton'),
			hidden : !load.vehicleassemblebillquery.isPermission('load/queryVehicleAssembleBillListButton'),
			text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.queryForm.queryButtonText')/*'查询'*/,
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					load.vehicleassemblebillquery.pagingBar.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义查询配载单结果列表Model
Ext.define('Foss.load.vehicleassemblebillquery.QueryVehicleAssembleBillModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'vehicleAssembleNo',
		type : 'string'
	}, {
		name : 'state',
		type : 'number'
	}, {
		name : 'feeTotal',
		type : 'number'
	}, {
		name : 'assembleType',
		type : 'string'
	}, {
		name : 'origOrgName',
		type : 'string'
	}, {
		name : 'origOrgCode',
		type : 'string'
	}, {
		name : 'destOrgName',
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
		name : 'frequencyNo',
		type : 'string'
	}, {
		name : 'vehicleNo',
		type : 'string'
	}, {
		name : 'vehicleOwnerShip',
		type : 'string'
	}, {
		name : 'loadingRate',
		type : 'string'
	}, {
		name : 'createDate',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'createUserName',
		type : 'string'
	} ,{
		name : 'driverName',
		type : 'string'
	},{
		name : 'modifyUserName',
		type : 'string'
	},{
		name : 'modifyDate',	
		type : 'date',
		convert: dateConvert
	},{
		name : 'ratedClearance',
		type : 'string'
	},{
		name : 'ratedLoad',
		type : 'string'
	},{
		name : 'truckOwnerName',
		type : 'String'
	},{
		name : 'applyPath',
		type : 'String'
	},{
		name : 'ministryinformationCode',
		type : 'String'
	},{
		name:'goodsQtyTotal',
		type:'number'
	},{
		name:'waybillQtyTotal',
		type:'number'
	},{
		name:'weightTotal',
		type:'number'
	},{
		name:'volumeTotal',
		type:'number'
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
	}]
});

//定义配载单列表store
Ext.define('Foss.load.vehicleassemblebillquery.QueryVehicleAssembleBillStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.load.vehicleassemblebillquery.QueryVehicleAssembleBillModel',
	pageSize : 20,
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryVehicleAssembleBillList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'vehicleAssembleBillVo.queriedBillList',
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
			var queryParams = load.vehicleassemblebillquery.queryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'vehicleAssembleBillVo.queryConditionDto.vehicleAssembleNo' : queryParams.vehicleAssembleNo,
					'vehicleAssembleBillVo.queryConditionDto.assembleDept' : queryParams.assembleDept,
					'vehicleAssembleBillVo.queryConditionDto.assembleType' : queryParams.assembleType,
					'vehicleAssembleBillVo.queryConditionDto.driver' : queryParams.driver,
					'vehicleAssembleBillVo.queryConditionDto.arriveDept' : queryParams.arriveDept,
					'vehicleAssembleBillVo.queryConditionDto.vehicleNo' : queryParams.vehicleNo,
					'vehicleAssembleBillVo.queryConditionDto.beginCreateTime' : queryParams.beginCreateTime,
					'vehicleAssembleBillVo.queryConditionDto.endCreateTime' : queryParams.endCreateTime,
					'vehicleAssembleBillVo.queryConditionDto.vehicleType' : queryParams.vehicleType
				}
			});	
		}
	}
});

//定义打印模版window
Ext.define('Foss.load.vehicleassemblebillquery.PrintWindow', {
	extend: 'Ext.window.Window',
	title: '打印模板选择',
	layout:'column',
	height: 150,
	width: 300,
	closable:true,
	closeAction:'hide',
	modal: true,
	vehicleAssembleNos : null,
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
	            {"key":"外发清单(流水)", "value":"外发清单(流水号)打印"}
	        ]
	    })
	},{
		xtype: 'container',
		columnWidth: .6,
		html: '&nbsp;'
	},{
		columnWidth : .39,
		xtype : 'button',
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.printButtonText')/*'打印'*/,
		handler : function(){
			var upwindow = this.up('window'),
				printTemplate = upwindow.items.items[0].getValue(),
				vehicleAssembleNos = upwindow.vehicleAssembleNos;
			var currentDeptCode = FossUserContext.getCurrentDept().code;
			var currentDeptName = FossUserContext.getCurrentDept().name;
			var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
			var currentUserName = FossUserContext.getCurrentUser().employee.empName;
			
			if(printTemplate == '配载单') {
				do_printpreview('vehicleassemblebill',{
					"vehicleAssembleNos": vehicleAssembleNos, 
					"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
					"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
				return;
			} else if(printTemplate == '配载单(流水)') {
				do_printpreview('vehicleassemblebillsn',{
					"vehicleAssembleNos": vehicleAssembleNos, 
					"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
					"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
				return;
			} 
			Ext.Ajax.request({
				url : load.realPath('queryHandOverBillNosByVehicleAssembleNo.action'),
				params : {'vehicleAssembleBillVo.vehicleAssembleNos' : vehicleAssembleNos},
				success : function(response) {
					var result = Ext.decode(response.responseText),
						handOverBillNos = result.vehicleAssembleBillVo.handOverBillNos;
					
					if (printTemplate == '交接单') {
						do_printpreview('load',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					} else if (printTemplate == '交接单(流水)') {
						do_printpreview('loadsn',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					} else if (printTemplate == '外发清单') {
						do_printpreview('partialline',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					} else if (printTemplate == '外发清单(流水)') {
						do_printpreview('partiallinesn',{"handOverBillNos": handOverBillNos, 
							"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
							"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION);
					}
				}
			});
		}
	}]
});

//定义配载单查询结果列表
Ext.define('Foss.load.vehicleassemblebillquery.QueryVehicleAssembleBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.title')/*'配载单列表'*/,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	emptyText : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.emptyText')/*'查询结果为空'*/,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.vehicleassemblebillquery.QueryVehicleAssembleBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			disabled : !load.vehicleassemblebillquery.isPermission('load/vehicleassemblebilladdnewButton'),
			hidden : !load.vehicleassemblebillquery.isPermission('load/vehicleassemblebilladdnewButton'),
			text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.addnewButtonText')/*'新增'*/,
			handler : function(){
				load.addTab('T_load-vehicleassemblebilladdnewindex',//对应打开的目标页面js的onReady里定义的renderTo
							load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.addnewMainTabTitle')/*'新增配载单'*/,//打开的Tab页的标题
							load.realPath('vehicleassemblebilladdnewindex.action'));//对应的页面URL，可以在url后使用?x=123这种形式传参
			}
		},'->',{
			xtype : 'button',
			text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.expertExcelButtonText'),//'导出'
			handler : function(){
				var form = load.vehicleassemblebillquery.queryForm.getForm(),
					queryParams = form.getValues();
				
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的制单时间！','error',2000);
					return;
				}
				
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}			
				Ext.Ajax.request({
					url : load.realPath('exportVehicleAssembleBillExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'vehicleAssembleBillVo.queryConditionDto.vehicleAssembleNo' : queryParams.vehicleAssembleNo,
						'vehicleAssembleBillVo.queryConditionDto.assembleDept' : queryParams.assembleDept,
						'vehicleAssembleBillVo.queryConditionDto.assembleType' : queryParams.assembleType,
						'vehicleAssembleBillVo.queryConditionDto.driver' : queryParams.driver,
						'vehicleAssembleBillVo.queryConditionDto.arriveDept' : queryParams.arriveDept,
						'vehicleAssembleBillVo.queryConditionDto.vehicleNo' : queryParams.vehicleNo,
						'vehicleAssembleBillVo.queryConditionDto.beginCreateTime' : queryParams.beginCreateTime,
						'vehicleAssembleBillVo.queryConditionDto.endCreateTime' : queryParams.endCreateTime,
						'vehicleAssembleBillVo.queryConditionDto.vehicleType' : queryParams.vehicleType
					},
					isUpload: true,
					success:function(response){
						
					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败'/*'导出失败'*/,result.message);
					}
				});
			}
		},{
			xtype : 'button',
			text : '导出运单',
			disabled : !load.vehicleassemblebillquery.isPermission('load/exportWayBillExcelByVNo.action'),
			hidden : !load.vehicleassemblebillquery.isPermission('load/exportWayBillExcelByVNo.action'),
			handler : function(){
				//导出时只能选择一条记录
				var records = me.getSelectionModel().getSelection();
				if(records.length == 0){
					Ext.ux.Toast.msg('提示','请先选择要导出运单的配载单！','error');
					return;
				}
				if(records.length > 1){
					Ext.ux.Toast.msg('提示','导出运单时只能选择一个配载单！','error');
					return;
				}
				var vehicleAssembleNo = records[0].get('vehicleAssembleNo');
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}			
				Ext.Ajax.request({
					url : load.realPath('exportWayBillExcelByVNo.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'vehicleAssembleBillVo.vehicleAssembleNo' : vehicleAssembleNo
						},
					isUpload: true,
					success:function(response){
						
					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败'/*'导出失败'*/,result.message);
					}
				});
			}
		},{
			xtype : 'button',
			text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.printButtonText')/*'打印'*/,
			disabled: !load.vehicleassemblebillquery.isPermission('load/printvehicleassemblebillButton'),
			hidden: !load.vehicleassemblebillquery.isPermission('load/printvehicleassemblebillButton'),
			handler : function() {
				var records = me.getSelectionModel().getSelection();
				if(records.length == 0){
					Ext.MessageBox.show({
						title:"提示",
						msg:"请先选择您要操作的行!"
					});
					return;
				}
				//如果选择的配载单属于多个不同的车牌则不能打印
				var vehicleNo = records[0].get('vehicleNo'),
					isdiff = false,
					vehicleAssembleNos = new Array();
				for(var i = 0; i < records.length; i++) {
					var no = records[i].get('vehicleNo');
					var vehicleAssembleNo = records[i].get('vehicleAssembleNo');
					vehicleAssembleNos.push(vehicleAssembleNo);
					if(vehicleNo != no) {
						//所选择的车牌号不相同
						isdiff = true;
						break;
					}
				}
				if(isdiff) {
					Ext.MessageBox.show({
						title:"提示",
						msg:"选择的配载单单属于多个车牌, 不能打印!"
					});
					return;
				}
				//如果选择的配载单的车牌号下还有其他的配载单则提示还有其他配载单,请注意
				Ext.Ajax.request({
					url : load.realPath('checkPrintVehicleAssembleBill.action'),
					params : {'vehicleAssembleBillVo.vehicleAssembleNos' : vehicleAssembleNos},
					success : function(response) {
						var result = Ext.decode(response.responseText),
							count = result.vehicleAssembleBillVo.checkPrintVehicleAssembleBillRestlt;
						if(count > 0) {
							//大于0则说明该车牌号下还有其他配载单尚未选择
							Ext.ux.Toast.msg(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, 
									"此车牌中还有" + count + "个配载单没有选择打印，请注意!", 
									'error');
						} else {
							
						}
						var vehicleAssembleNo = records[0].get('vehicleAssembleNo');
						Ext.create('Foss.load.vehicleassemblebillquery.PrintWindow', {
							vehicleAssembleNos : vehicleAssembleNos,
							grid : me
						}).show();
					},
					exception : function(response){
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, result.message);
					}
				});
			}
		},{
			xtype : 'button',
			disabled: !load.vehicleassemblebillquery.isPermission('load/printTransportProtocolButton'),
			hidden: !load.vehicleassemblebillquery.isPermission('load/printTransportProtocolButton'),
			text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.printContractNoteButtonText')/*'打印运输合同'*/,
			handler : function(){
				var records = me.getSelectionModel().getSelection();
				if(records.length == 0){
					Ext.ux.Toast.msg(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, '请先选择您要操作的行!', 'error');
					return;
				}
				if(records.length > 1) {
					Ext.ux.Toast.msg(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, '每次只能打印一行!', 'error');
					return;
				}
				//1:必须是外请车
				//2:如果【是否在途装卸】 没打勾，本条记录有效,可以打印
				//3:如果【是否在途装卸】 打勾的话，那么是否最终到达也必须是打勾的，本条记录才有效,可以打印
				var record = records[0],
					vehicleAssembleNo = record.get('vehicleAssembleNo');
				if(record.get('vehicleOwnerShip') != 'Leased') {
					Ext.ux.Toast.msg(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, '非外请车, 不能打印!', 'error');
					return;
				}
				
				//判断该单据是否打印过运输合同
				Ext.Ajax.request({
					url : load.realPath('checkCarriageContractPrinted.action'),
					params : {'vehicleAssembleBillVo.vehicleAssembleNo' : vehicleAssembleNo},
					success : function(response){
						var result = Ext.decode(response.responseText),
							checkCarriageContractPrinted = result.vehicleAssembleBillVo.checkCarriageContractPrinted;
						//说明该单据已经被打印过, 询问是否继续打印
						if(checkCarriageContractPrinted == 1) {
							var msg = load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.checkCarriageContractPrinted.msg');
		    				Ext.Msg.confirm(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title'), msg, function(optional){
		    					if(optional == 'yes'){
		    						//后台读取配载单基本信息
									Ext.Ajax.request({
										url : load.realPath('queryVehicleAssembleBillByNo.action'),
										params : {'vehicleAssembleBillVo.vehicleAssembleNo' : vehicleAssembleNo},
										success : function(response){
											var result = Ext.decode(response.responseText);
											var basicInfo = result.vehicleAssembleBillVo.baseEntity;
											//2:如果【是否在途装卸】 没打勾，本条记录有效,可以打印
											if(basicInfo.beMidWayLoad == 'Y'){
												//3:如果【是否在途装卸】 打勾的话，那么是否最终到达也必须是打勾的，本条记录才有效,可以打印
												if(basicInfo.beFinallyArrive == 'N'){
													Ext.ux.Toast.msg(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, '非最终到达部门, 不能打印!', 'error');
													return;
												}
											}
											var currentDeptCode = FossUserContext.getCurrentDept().code,
												currentDeptName = FossUserContext.getCurrentDept().name;
												currentUserCode = FossUserContext.getCurrentUser().employee.empCode,
												currentUserName = FossUserContext.getCurrentUser().employee.empName;
											do_printpreview('carriagecontract',{"vehicleAssembleNo": vehicleAssembleNo, 
														"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
														"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
										}
									});
		    					}
		    				});
						} else {
							//后台读取配载单基本信息
							Ext.Ajax.request({
								url : load.realPath('queryVehicleAssembleBillByNo.action'),
								params : {'vehicleAssembleBillVo.vehicleAssembleNo' : vehicleAssembleNo},
								success : function(response){
									var result = Ext.decode(response.responseText);
									var basicInfo = result.vehicleAssembleBillVo.baseEntity;
									//2:如果【是否在途装卸】 没打勾，本条记录有效,可以打印
									if(basicInfo.beMidWayLoad == 'Y'){
										//3:如果【是否在途装卸】 打勾的话，那么是否最终到达也必须是打勾的，本条记录才有效,可以打印
										if(basicInfo.beFinallyArrive == 'N'){
											Ext.ux.Toast.msg(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, '非最终到达部门, 不能打印!', 'error');
											return;
										}
									}
									//4：与零担合车不能打印配载单
									if(basicInfo.beInLTLCar=='Y'){
										Ext.ux.Toast.msg(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, '与零担合车, 不能打印!', 'error');
										return;
									}
									var currentDeptCode = FossUserContext.getCurrentDept().code,
										currentDeptName = FossUserContext.getCurrentDept().name;
										currentUserCode = FossUserContext.getCurrentUser().employee.empCode,
										currentUserName = FossUserContext.getCurrentUser().employee.empName;
									do_printpreview('carriagecontract',{"vehicleAssembleNo": vehicleAssembleNo, 
												"currentDeptName" : currentDeptName, "currentUserName" : currentUserName,
												"currentDeptCode" : currentDeptCode, "currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
								}
							});
						}
					}
				});
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
	load.vehicleassemblebillquery.pagingBar = me.bbar;
	me.callParent([cfg]);
},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.actionColumn')/*'操作'*/,
		align : 'center',
		items : [ {
			tooltip : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.modifyToolTipText')/*'修改'*/,
			iconCls : 'deppon_icons_edit',
			handler : function(grid, rowIndex, colIndex) {
				var vehicleAssembleNo = grid.store.getAt(rowIndex).get('vehicleAssembleNo');
				var vehicleOwnerShip = grid.store.getAt(rowIndex).get('vehicleOwnerShip');
				load.vehicleassemblebillquery.showVehicleAssembleBillModify(vehicleAssembleNo,vehicleOwnerShip);
			}
		},{
			tooltip : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.cancelToolTipText')/*'作废'*/,
			iconCls : 'deppon_icons_delete',
			handler : function(grid, rowIndex, colIndex) {
				var record = grid.store.getAt(rowIndex);
				var vehicleAssembleNo = record.get('vehicleAssembleNo');
				Ext.MessageBox.confirm(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/, 
					load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.confirmCancel')/*'确定要作废此配载单吗？'*/,function(btn){
					if(btn == 'yes'){
						Ext.Ajax.request({
							url : load.realPath('cancelVehicleAssembleBill.action'),
							params : {'vehicleAssembleBillVo.vehicleAssembleNo': vehicleAssembleNo},
							success : function(response){
								grid.store.removeAt(rowIndex);
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/,
										load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.cancelResult1') + 
										vehicleAssembleNo + 
										load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.cancelResult2'));
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/,
			    						load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.operateFailure')/*'操作失败，'*/ + result.message);
			    			}
						});
					}
				});
		} 
	} ],
	renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
		//获取当前部门code
		var currentDeptCode = load.vehicleassemblebillquery.superOrgCode;
		/**
		 * 只能修改符合以下条件的配载单：
				1、“配载部门”为本部门；
				2、状态为“未发车”或“已出发”；
				3、未做出发付款确认；
				有任意一条不符合，则隐藏作废按钮
		 */
		if(record.get('origOrgCode') == currentDeptCode
				&& (record.get('state') == 10 || record.get('state') == 20)){
			this.items[0].iconCls = 'deppon_icons_edit';
		}else{
			this.items[0].iconCls = '';
		}
		/**
		 * 只能作废符合以下条件的交接单：
				1、“配载部门”为本部门；
				2、状态为“未发车”；
				3、未做出发付款确认；
				有任意一条不符合，则隐藏作废按钮
		 */
		if(record.get('origOrgCode') == currentDeptCode
				&& record.get('state') == 10){
			this.items[1].iconCls = 'deppon_icons_delete';
		}else{
			this.items[1].iconCls = '';
		}
	}
	}, {
		dataIndex : 'vehicleAssembleNo',
		align : 'center',
		width : 125,
		xtype : 'ellipsiscolumn',
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.vehicleAssembleNoColumn')/*'配载车次号'*/,
		renderer : function(value){
			if(value!=null){
				return '<a href="javascript:load.vehicleassemblebillquery.showVehicleAssembleBillDetail('+"'" + value + "'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	}, {
		dataIndex : 'state',
		align : 'center',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.stateColumn')/*'状态'*/,
		renderer : function(value){
			if(value == 10){
				return '未发车';
			}else if(value == 20){
				return '已出发';
			}else if(value == 30){
				return '已到达';
			}else if(value == 90){
				return '已作废';
			}
		}
	},{
		dataIndex : 'feeTotal',
		align : 'center',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.feeTotalColumn')/*'总运费'*/
	}, {
		dataIndex : 'assembleType',
		align : 'center',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.queryForm.assembleTypeLabel')/*'配载类型'*/,
		renderer : function(value){
			if(value == 'OWNER_LINE'){
				return '专线';
			}else if(value == 'CAR_LOAD'){
				return '整车';
			}else{
				return null;
			}
		}
	}, {
		dataIndex : 'frequencyNo',
		align : 'center',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.frequencyNo')/*'班次'*/
	},{
		dataIndex : 'origOrgName',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.assembleDeptColumn')/*'配载部门'*/
	}, {
		dataIndex : 'departTime',
		align : 'center',
		width : 135,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.departTimeColumn')/*'发车时间'*/,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'destOrgName',
		align : 'center',
		width : 120,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.arriveDeptColumn')/*'到达部门'*/
	},{
		dataIndex : 'arriveTime',
		align : 'center',
		width : 135,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.arriveTime')/*'到达时间'*/,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		width : 72,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.vehicleNoColumn')/*'车牌号'*/
	}, {
		dataIndex : 'loadingRate',
		align : 'center',
		width : 50,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.loadingRateColumn')/*'装载率'*/
	}, {
		dataIndex : 'createDate',
		align : 'center',
		width : 80,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.createDateColumn')/*'配载日期'*/,
		xtype : 'datecolumn',
		format : 'Y-m-d'
	}, {
		dataIndex : 'createUserName',
		align : 'center',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.createUserNameColumn')/*'制单人'*/
	}, {
		dataIndex : 'driverName',
		align : 'center',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.driverNameColumn')/*'主驾驶姓名'*/
	} , {
		dataIndex : 'modifyUserName',
		align : 'center',
		width : 60,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.modifyUserNameColumn')/*'修改人'*/
	}, {
		dataIndex : 'modifyDate',
		align : 'center',
		width : 135,
		text : load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.modifyDateColumn')/*'修改时间'*/,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'ratedClearance',
		align : 'center',
		width : 80,
		text : '净空'
	},{
		dataIndex : 'ratedLoad',
		align : 'center',
		width : 80,
		text : '载重'
	},{
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		width : 80,
		text : '总票数'
	},{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		width : 80,
		text : '总件数'
	},{
		dataIndex : 'weightTotal',
		align : 'center',
		width : 80,
		text : '总重量'
	},{
		dataIndex : 'volumeTotal',
		align : 'center',
		width : 80,
		text : '总体积'
	},{
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
		dataIndex : 'applyPath',
		align : 'center',
		width : 80,
		text : '信息部名称'
	}, {//310248
		dataIndex : 'ministryinformationCode',
		align : 'center',
		width : 80,
		text : '信息部编码'
	}, {
		dataIndex : 'truckOwnerName',
		align : 'center',
		width : 80,
		text : '车主姓名'
	}]
});

//点击“配载单编号”打开详情界面方法
load.vehicleassemblebillquery.showVehicleAssembleBillDetail = function(vehicleAssembleNo){
	load.addTab('T_load-vehicleassemblebillshowindex',//对应打开的目标页面js的onReady里定义的renderTo
			load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.billDetailMainTabTitle')/*'配载单详情'*/,//打开的Tab页的标题
			load.realPath('vehicleassemblebillshowindex.action') + '?vehicleAssembleNo="' + vehicleAssembleNo + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}

//点击操作列“修改”图标打开修改界面方法
load.vehicleassemblebillquery.showVehicleAssembleBillModify = function(vehicleAssembleNo,vehicleOwnerShip){
	//如果为外请车，则需调用结算接口，看是否已经做出发付款确认；
	if(vehicleOwnerShip == 'Leased'){
		Ext.Ajax.request({
					url : load.realPath('queryDepartPaymentConfirmState.action'),
					params : {'vehicleAssembleBillVo.vehicleAssembleNo': vehicleAssembleNo},
					success : function(response){
						var result = Ext.decode(response.responseText);
						var isConfirm = result.vehicleAssembleBillVo.isDepartPaymentConfirm;
						var isHoding = result.vehicleAssembleBillVo.isHoding;
						//如果出发付款已确认
						if(isConfirm == 'N'){
							Ext.MessageBox.alert(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/,
									load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.cannotCancel'));	
						}else if(isHoding=='Y'){
							Ext.MessageBox.alert(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/,
									'该配载单已被租车标记不能修改！');	
						}else{
							load.addTab('T_load-vehicleassemblebillmodifyindex',//对应打开的目标页面js的onReady里定义的renderTo
								load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.billModifyMainTabTitle')/*'修改配载单'*/,//打开的Tab页的标题
								load.realPath('vehicleassemblebillmodifyindex.action') + '?vehicleAssembleNo="' + vehicleAssembleNo + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
						}
						
					},
					exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert(load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.alertInfo.title')/*'提示'*/,result.message);
	    			}
				});		
	}else{
		load.addTab('T_load-vehicleassemblebillmodifyindex',//对应打开的目标页面js的onReady里定义的renderTo
			load.vehicleassemblebillquery.i18n('foss.load.vehicleassemblebillquery.resultGrid.billModifyMainTabTitle')/*'修改配载单'*/,//打开的Tab页的标题
			load.realPath('vehicleassemblebillmodifyindex.action') + '?vehicleAssembleNo="' + vehicleAssembleNo + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
	}
}

//定义交接单查询结果列表
load.vehicleassemblebillquery.queryGrid = Ext.create('Foss.load.vehicleassemblebillquery.QueryVehicleAssembleBillGrid');
load.vehicleassemblebillquery.queryForm = Ext.create('Foss.load.vehicleassemblebillquery.QueryForm');
Ext.onReady(function() {
	console.log(load.vehicleassemblebillquery.superOrgCode);
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-vehicleassemblebillqueryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',//
		items : [ load.vehicleassemblebillquery.queryForm,load.vehicleassemblebillquery.queryGrid],
		renderTo : 'T_load-vehicleassemblebillqueryindex-body'
	});
});