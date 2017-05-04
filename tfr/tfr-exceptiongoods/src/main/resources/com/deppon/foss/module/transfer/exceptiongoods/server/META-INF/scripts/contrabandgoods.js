
Ext.define('Foss.exceptiongoods.contrabandgoods.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	//title : '查询违禁品',
	title : exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.query.contraband'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		//fieldLabel: '运单号',
		fieldLabel: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.waybill'),
		name: 'waybillNo',
		vtype: 'waybill',
		enableKeyEvents: true,
		columnWidth:.25
	},{
	
		xtype: 'dynamicorgcombselector',
		//fieldLabel: '报告部门',
		fieldLabel: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.report.org'),
		labelWidth: 100,
		name: 'findOrgCode',
		type : 'ORG',
		transferCenter: 'Y',
		columnWidth:.25
	
	},{
		xtype: 'dynamicorgcombselector',
		//fieldLabel: '移交部门',
		fieldLabel: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.handover.org'),
		name: 'handoverOrgCode',
		salesDepartment: 'Y',
		columnWidth:.25
	},{
		xtype: 'dynamicorgcombselector',
		//fieldLabel: '开单部门',
		fieldLabel: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.createbill.org'),
		name: 'createBillOrgCode',
		salesDepartment: 'Y',
		id: 'Foss_exceptiongoods_contrabandgoods_QueryForm_CreateBillOrgCode_ID',
		columnWidth:.25
	},{
		xtype: 'rangeDateField',
		//fieldLabel: '发现时间',
		fieldLabel: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.findtime'),
		fieldId: 'Foss_exceptiongoods_contrabandgoods_QueryForm_BeginEndTime_ID',
		dateType: 'datetimefield_date97',
		fromName: 'beginTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'),
		toName: 'endTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		dateRange: 30,
		disallowBlank: true,
		allowBlank: false,
		//blankText:'字段不能为空',
		blankText: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.notnull'),
		columnWidth: .5
	},
	FossDataDictionary.getDataDictionaryCombo('CONTRABAND_PROCESS_RESULT',
		{
			//fieldLabel : '处理结果',
			fieldLabel : exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.process.result'),
			name : 'processResult',
			value : 'ALL',
			editable : false,
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false
		},
		{
            'valueCode': 'ALL',
            'valueName': '全部'
		}
	),
	FossDataDictionary.getDataDictionaryCombo('CONTRABAND_HANDOVER_STATUS',
		{
			//fieldLabel : '移交状态',
			fieldLabel : exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.handover.status'),
			name : 'handoverStatus',
			value : 'ALL',
			editable : false,
			columnWidth:.25,
			queryMode:'local',
			triggerAction:'all',
			editable:false
		},
		{
            'valueCode': 'ALL',
            'valueName': '全部'
		}
	),{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			//text: '重置',
			text: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.reset'),
			columnWidth:.08,
			handler: function(){
				var queryBasicForm = exceptiongoods.contrabandgoods.queryform.getForm();
				queryBasicForm.reset();
				/**
				 * 根据登陆人部门信息 设置查询条件初始值
				 */
				if(exceptiongoods.contrabandgoods.orgType == 'SALE'){//营业部
					var dept = FossUserContext.getCurrentDept();
					//设置开单部门
					queryBasicForm.findField('createBillOrgCode').setValue(dept.code);
					queryBasicForm.findField('createBillOrgCode').getStore().load({params:{'commonOrgVo.name' : dept.name}});
					queryBasicForm.findField('createBillOrgCode').addCls('readonlyhaveborder');
					queryBasicForm.findField('createBillOrgCode').setReadOnly(true);
					Ext.getCmp('Foss_exceptiongoods_contrabandgoods_ContrabandGrid_HandoverButton_ID').disable(true);
				}else{//外场或驻地部门
					//设置报告部门
					queryBasicForm.findField('findOrgCode').setValue(exceptiongoods.contrabandgoods.transferCenterCode);
					queryBasicForm.findField('findOrgCode').getStore().load({params:{'commonOrgVo.name' : exceptiongoods.contrabandgoods.transferCenterName}});
					queryBasicForm.findField('findOrgCode').addCls('readonlyhaveborder');
					queryBasicForm.findField('findOrgCode').setReadOnly(true);
					//设置移交部门
					queryBasicForm.findField('handoverOrgCode').setDisabled(true);
					if( exceptiongoods.contrabandgoods.orgType == 'STATION'){//驻地派送部
						//设置【移交】按钮不可用
						Ext.getCmp('Foss_exceptiongoods_contrabandgoods_ContrabandGrid_HandoverButton_ID').disable(true);
					}
				}
				//设置起始时间默认值
				queryBasicForm.findField('beginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-7,
										'00', '00'), 'Y-m-d H:i:s'));
				queryBasicForm.findField('endTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			//text: '查询',
			text: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.query'),
			disabled: !exceptiongoods.contrabandgoods.isPermission('exceptiongoods/queryContrabandGoodsButton'),
			hidden: !exceptiongoods.contrabandgoods.isPermission('exceptiongoods/queryContrabandGoodsButton'),
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				if(exceptiongoods.contrabandgoods.queryform.getForm().isValid()){
					exceptiongoods.contrabandgoods.pagingBar.moveFirst();
				}
			}
		}]
	}],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//查询违禁品表格Model
Ext.define('Foss.exceptiongoods.contrabandgoods.ContrabandModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'waybillNo', type: 'string'},
		{name: 'oaErrorNo', type: 'string'},
		{name: 'findTime', type:'date', convert: dateConvert},
		{name: 'findOrgCode' , type: 'string'},
		{name: 'findOrgName' , type: 'string'},
		{name: 'processResult' , type: 'string'},
		{name: 'handoverStatus' , type: 'string'},
		{name: 'handoverOrgName', type: 'string'},
		{name: 'createBillOrgName', type: 'string'},
		{name: 'productName', type: 'string'}
	]
});
//查询违禁品表格Store
Ext.define('Foss.exceptiongoods.contrabandgoods.ContrabandStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.exceptiongoods.contrabandgoods.ContrabandModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: exceptiongoods.realPath('queryContrabandGoods.action'),
		reader : {
			type : 'json',
			root : 'contrabandGoodsVo.contrabandGoodsList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = exceptiongoods.contrabandgoods.queryform.getValues();
				if(queryParams.processResult == 'ALL'){
					queryParams.processResult = '';
				}
				if(queryParams.handoverStatus == 'ALL'){
					queryParams.handoverStatus = '';
				}
				Ext.apply(operation, {
					//查询条件参数
					params : {
						//运单号
						'contrabandGoodsVo.contrabandGoods.waybillNo' : queryParams.waybillNo,
						//报告部门编号
						'contrabandGoodsVo.contrabandGoods.findOrgCode' : queryParams.findOrgCode,
						//移交部门编号
						'contrabandGoodsVo.contrabandGoods.handoverOrgCode' : queryParams.handoverOrgCode,
						//开单部门编号
						'contrabandGoodsVo.contrabandGoods.createBillOrgCode' : queryParams.createBillOrgCode,
						//开始时间
						'contrabandGoodsVo.contrabandGoods.beginTime' : queryParams.beginTime,
						//结束时间
						'contrabandGoodsVo.contrabandGoods.endTime' : queryParams.endTime,
						//处理结果
						'contrabandGoodsVo.contrabandGoods.processResult' : queryParams.processResult,
						//移交状态
						'contrabandGoodsVo.contrabandGoods.handoverStatus' : queryParams.handoverStatus
					}
				});	
		}
	}
	
});
//查询违禁品表格
Ext.define('Foss.exceptiongoods.contrabandgoods.ContrabandGrid', {
	extend:'Ext.grid.Panel',
	height: 500,
	autoScroll:true,
	//表格列的分割线
	columnLines: true,
	//表格对象边框
    frame: true,
	columns: [{
			//header: '运单号', 
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.waybill'),
			dataIndex: 'waybillNo'
		},{ 
			//header: 'OA差错编号', 
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.oa.error.no'),
			dataIndex: 'oaErrorNo'
		},{ 
			//header: '处理结果', 
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.process.result'),
			dataIndex: 'processResult',
			renderer : function(value){
				if(value!=null){
					var processResult = FossDataDictionary.rendererSubmitToDisplay (value,'CONTRABAND_PROCESS_RESULT');
					return processResult;
				}
			}
		},{ 
			//header: '发现时间', 
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.findtime'),
			dataIndex: 'findTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s'
		},{ 
			//header: '发现部门', 
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.findorg'),
			dataIndex: 'findOrgName'
		},{ 
			//header: '移交状态', 
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.handover.status'),
			dataIndex: 'handoverStatus',
			renderer : function(value){
				if(value!=null){
					var handoverStatusName = FossDataDictionary.rendererSubmitToDisplay (value,'CONTRABAND_HANDOVER_STATUS');
					return handoverStatusName;
				}
			}
		},{ 
			//header: '移交部门',
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.handover.org'),
			dataIndex: 'handoverOrgName'
		},{ 
			//header: '开单部门',
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.createbill.org'),
			dataIndex: 'createBillOrgName'
		},{ 
			//header: '运输性质',
			header: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.product'),
			dataIndex: 'productName'
		}],
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{});
			me.store = Ext.create('Foss.exceptiongoods.contrabandgoods.ContrabandStore');
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
			exceptiongoods.contrabandgoods.pagingBar = me.bbar;
			me.tbar = [{
					xtype: 'button',
					//text: '移交',
					disabled: !exceptiongoods.contrabandgoods.isPermission('exceptiongoods/handoverContrabandGoodsButton'),
					hidden: !exceptiongoods.contrabandgoods.isPermission('exceptiongoods/handoverContrabandGoodsButton'),
					text: exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.handover'),
					id: 'Foss_exceptiongoods_contrabandgoods_ContrabandGrid_HandoverButton_ID',
					gridContainer: this,
					handler: function() {
						//获取选中的行记录
						var selectedRecords = exceptiongoods.contrabandgoods.contrabandgrid.getSelectionModel().getSelection();
					 	if(selectedRecords.length < 1){
			        		//Ext.ux.Toast.msg('提示', '请勾选需要移交的违禁品', 'error', 2000);
			        		Ext.ux.Toast.msg(exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.select.contraband'), 'error', 2000);
			        		return;
			        	}
					 	var contrabandList = new Array();
					 	//校验选中记录的处理结果和移交状态
					 	for(var i in selectedRecords){
					 		if(selectedRecords[i].data.processResult != 'CONTRABAND'){
					 			//Ext.ux.Toast.msg('提示', '只能移交处理结果为违禁品的货物', 'error', 2000);
					 			Ext.ux.Toast.msg(exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.only.handover.contraband'), 'error', 2000);
					 			return;
					 		}
					 		if(selectedRecords[i].data.handoverStatus == 'HANDOVER'){
					 			//Ext.ux.Toast.msg('提示', '只能移交移交状态为未移交的货物', 'error', 2000);
					 			Ext.ux.Toast.msg(exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.only.handover.on.handover'), 'error', 2000);
					 			return;
					 		}
							contrabandList.push(selectedRecords[i].data); 
						}
						var jsonParam = {contrabandGoodsVo: {contrabandGoodsList:contrabandList}};
						//移交违禁品请求
						Ext.Ajax.request({
							url: exceptiongoods.realPath('handoverContrabandGoods.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('提示', '移交成功', 'ok', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.prompt'), exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.handover.success'), 'ok', 3000);
			    				exceptiongoods.contrabandgoods.contrabandgrid.store.load();
			    			},
			    			exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('移交失败', result.message, 'error', 3000);
			    				Ext.ux.Toast.msg(exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.handover.failure'), result.message, 'error', 3000);
			    			}
		    			});
					}
				}];
			me.callParent([cfg]);
		}
});
Ext.onReady(function() {
	//请求后台查询登陆人部门类型信息
	Ext.Ajax.request({
		url: exceptiongoods.realPath('queryCurrentOrgType.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			//部门类型
			exceptiongoods.contrabandgoods.orgType = result.contrabandGoodsVo.orgType;
			//外场编号
			exceptiongoods.contrabandgoods.transferCenterCode = result.contrabandGoodsVo.transferCenterCode;
			//外场名称
			exceptiongoods.contrabandgoods.transferCenterName = result.contrabandGoodsVo.transferCenterName;
			
			var queryform = Ext.create('Foss.exceptiongoods.contrabandgoods.QueryForm');
			exceptiongoods.contrabandgoods.queryform = queryform;
			var contrabandgrid = Ext.create('Foss.exceptiongoods.contrabandgoods.ContrabandGrid');
			exceptiongoods.contrabandgoods.contrabandgrid = contrabandgrid;
			Ext.create('Ext.panel.Panel',{
				id:'T_exceptiongoods-contrabandgoodsindex_content',
				cls:"panelContentNToolbar",
				bodyCls:'panelContent-body',
				items : [queryform, contrabandgrid],
				renderTo: 'T_exceptiongoods-contrabandgoodsindex-body'
			});
			
			/**
			 * 根据登陆人部门信息 设置查询条件初始值
			 */
			var queryBasicForm = exceptiongoods.contrabandgoods.queryform.getForm();
			if( exceptiongoods.contrabandgoods.orgType == 'SALE'){//营业部
				var dept = FossUserContext.getCurrentDept();
				queryBasicForm.findField('createBillOrgCode').setValue(dept.code);
				queryBasicForm.findField('createBillOrgCode').getStore().load({params:{'commonOrgVo.name' : dept.name}});
				queryBasicForm.findField('createBillOrgCode').addCls('readonlyhaveborder');
				queryBasicForm.findField('createBillOrgCode').setReadOnly(true);
				Ext.getCmp('Foss_exceptiongoods_contrabandgoods_ContrabandGrid_HandoverButton_ID').disable(true);
			}else{
				queryBasicForm.findField('findOrgCode').setValue(exceptiongoods.contrabandgoods.transferCenterCode);
				queryBasicForm.findField('findOrgCode').getStore().load({params:{'commonOrgVo.name' : exceptiongoods.contrabandgoods.transferCenterName}});
				queryBasicForm.findField('findOrgCode').addCls('readonlyhaveborder');
				queryBasicForm.findField('findOrgCode').setReadOnly(true);
				queryBasicForm.findField('handoverOrgCode').setDisabled(true);
				if( exceptiongoods.contrabandgoods.orgType == 'STATION'){//驻地派送部
					Ext.getCmp('Foss_exceptiongoods_contrabandgoods_ContrabandGrid_HandoverButton_ID').disable(true);
				}
			}
		},
		exception : function(response) {
				var result = Ext.decode(response.responseText);
				//Ext.ux.Toast.msg('获取部门失败', result.message, 'error', 3000);
				Ext.ux.Toast.msg(exceptiongoods.contrabandgoods.i18n('foss.exceptiongoods.query.org.failure'), result.message, 'error', 3000);
		}
	});
});