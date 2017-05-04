//package Foss.uninputLdpExternalBill 未录入落地配外发单查询
//未录入落地配外发单model
Ext.define('Foss.uninputLdpExternalBill.model.HandoverBillDetailModel', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'detailId',type:'string'},//
		{name: 'waybillNo',type:'string'},//运单号	
		{name: 'serialNo',type:'string'},//流水号
		{name: 'handoverNo', type: 'string'},//交接单号
		{name: 'handoverGoodsQty', type: 'float'},//件数
		{name: 'handoverWeight', type: 'float'},//重量（公斤）
		{name: 'handoverVolume', type: 'float'},//体积（方）
		{name: 'destOrgName', type: 'string'},//落地配公司名称
		{name: 'destOrgCode', type: 'string'},//落地配公司编码
		{name: 'agentOrgCode', type: 'string'},//落地配公司网点编码
		{name: 'agentOrgName', type: 'string'},//落地配公司网点名称
		{name: 'handoverTime', type: 'date',//外发交接时间
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

//未录入落地配外发单store
Ext.define('Foss.uninputLdpExternalBill.store.HandoverBillDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.uninputLdpExternalBill.model.HandoverBillDetailModel',
	pageSize:10,
	proxy: {
		type: 'ajax',
		url:partialline.realPath('queryUninputLdpExternalBills.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.uninputLdpExternalBill',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = partialline.uninputLdpExternalBill.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();				
				Ext.apply(operation, {
					params : queryParams
				});	
			}
		},
		load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records))
			Ext.ux.Toast.msg('提示信息', '查询结果为空!');
		}
	}
});

//查询form表单
Ext.define('Foss.uninputLdpExternalBill.form.HandoverBillDetailSearch',{
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
		xtype : 'commonLdpAgencyCompanySelector',
		fieldLabel: partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.model.handoverBillDetail.destOrgName.label'),//落地配公司
		name: 'vo.uninputLdpExternalBillDto.destOrgCode',
		allowBlank:true,
		columnWidth:.3	
	},{
		fieldLabel: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverNo.label'),//交接单号
		name: 'vo.uninputLdpExternalBillDto.handoverNo',
		allowBlank:true,
		columnWidth:.3
	},{
		vtype:'order',
		enableKeyEvents:true,
		fieldLabel: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.waybillNo.label'),//运单号
		name: 'vo.uninputLdpExternalBillDto.waybillNo',
		allowBlank:true,
		columnWidth:.3
	},{
		xtype: 'rangeDateField',
		fieldLabel: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverTime.label'),//交接时间
		dateType: 'datetimefield_date97',
		id:'Foss.uninputLdpExternalBill.model.handoverBillDetail.handoverTime_RangeDateField_ID',
		fieldId:'handoverTime_uninputLdpExternalBillDateFrom_ID',
		fromName: 'vo.uninputLdpExternalBillDto.handoverTimeFrom',
		toName: 'vo.uninputLdpExternalBillDto.handoverTimeTo',
		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'),
		toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		editable: false,
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
			text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.form.handoverBillDetailSearch.button.reset'),//重置
			handler: function() {	
				var bform = partialline.uninputLdpExternalBill.queryForm.getForm();
				bform.findField('vo.uninputLdpExternalBillDto.destOrgCode').reset();
				bform.findField('vo.uninputLdpExternalBillDto.handoverNo').reset();
				bform.findField('vo.uninputLdpExternalBillDto.waybillNo').reset();
				bform.findField('vo.uninputLdpExternalBillDto.handoverTimeFrom').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
				bform.findField('vo.uninputLdpExternalBillDto.handoverTimeTo').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'));
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.form.handoverBillDetailSearch.button.query'),//查询
			cls:'yellow_button',
			handler: function() {
				//按运单号查询时不受其他条件的限制。若无此单号，弹框提示：请输入正确的单号。
				var form=this.up('form').getForm();
				if(Ext.isEmpty(form.findField('vo.uninputLdpExternalBillDto.waybillNo').getValue())){				
					var formValid=form.isValid( );
					if(formValid){
						partialline.uninputLdpExternalBill.pagingBar.moveFirst();
					}
				}else{
					if(form.findField('vo.uninputLdpExternalBillDto.waybillNo').isValid( )){
						partialline.uninputLdpExternalBill.pagingBar.moveFirst();
					}
					
				}
								
			}
		}]
	}]
});

//查询结果列表
Ext.define('Foss.uninputLdpExternalBill.grid.searchResultGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.title'),//未录入落地配外发单列表
	emptyText: partialline.uninputLdpExternalBill.i18n('Foss.partialline.grid.emptyText'),
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	autoScroll:true,
	columns: [
	{		
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.waybillNo.label'),//运单号
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'waybillNo'
	},
	//269701--lln--20151013--begin
	//在运单号与代理单号之间添加流水号字段，展示对应单号的流水号，同一单号，不同流水号显示多行；
	{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.serialNo.label'),//流水号
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'serialNo'
	},
	//269701--lln--20151013--end
	{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverNo.label'),//交接单号
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'handoverNo'
	},{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverGoodsQty.label'),//件数
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'handoverGoodsQty'
	},{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverWeight.label'),//重量(公斤)
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'handoverWeight'
	},{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverVolume.label'),//体积（方）
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'handoverVolume'
	},{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.model.handoverBillDetail.destOrgName.label'),//落地配公司
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'destOrgName'
	},{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.model.handoverBillDetail.destAgentOrgName.label'),//落地配公司网点
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'agentOrgName'
	},{
		text: partialline.uninputLdpExternalBill.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverTime.label'),//外发交接时间
		flex: 1, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'handoverTime'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.uninputLdpExternalBill.store.HandoverBillDetailStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.tbar=[
		{
			xtype:'hiddenfield',
			flex:1
		},{
			xtype: 'button', 
			id: 'Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton',
			disabled : !partialline.uninputLdpExternalBill.isPermission('partialline/addLdpExternalBill'),
			hidden : !partialline.uninputLdpExternalBill.isPermission('partialline/addLdpExternalBill'),
			text: partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.generateBill'),//生成外发单
			name: 'generateBill',
			handler: function() {
				Ext.getCmp('Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton').disable(true);
				var records = partialline.uninputLdpExternalBill.searchResultGrid.getSelectionModel().getSelection();
				
				if(records.length == 0){
					Ext.Msg.alert(partialline.uninputLdpExternalBill.i18n('Foss.partialline.messageBox.alert.tip'),	partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.pleaseSelectRecord'));
					Ext.getCmp('Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton').enable(true);
					
					return false;
				}
				//269701--lln--2015/10/16-begin
				//重量体积为必填项，未录入重量体积不可生成外发单
				//重量
				var handoverWeight=records[0].data.handoverWeight;
				//体积
				var handoverVolume=records[0].data.handoverVolume
				
				if(Ext.isEmpty(handoverWeight) || Ext.isEmpty(handoverVolume)){
					//重量体积不可为空
					Ext.ux.Toast.msg('提示信息', '重量体积不可为空!');//重量体积不可为空
				}
				/**
				 * 一票多件,按照流水号生成外发单;生成外发单时,可以部分生成
				 * 以实体的形式，将前台运单和流水号数据传给后台
				 */
				var recordList = new Array();
				for(var i = 0;i < records.length; i++){
				    var selectRecord = records[i];
				    recordList.push(selectRecord.data);
				}
				var jsonDatas = {vo:{uninputLdpExternalBill:recordList}};
				//269701--lln--2015/10/16-end
				/**
				 * 一票多件 按照流水号生成外发单；
				 * 注释按照运单号生成外发单代码
				 * var waybillNos = new Array();
				 *	for(var i = 0; i < records.length; i++){
				 *		waybillNos.push(records[i].data.waybillNo);
	             *	}
				 *	var jsonDatas = {vo:{waybillNoList:waybillNos}};
				 * 
				 */
				Ext.Ajax.request({
					url:partialline.realPath('validateLdpExternalBill.action'),
					jsonData:jsonDatas,
					success:function(response){
						var result = Ext.decode(response.responseText);
						var unValidateWaybillSerialNos = result.vo.waybillNos;
						validateRecordList = result.vo.uninputLdpExternalBill;
						if(unValidateWaybillSerialNos != 'undefined' && unValidateWaybillSerialNos.length !=0){
							Ext.ux.Toast.msg(partialline.uninputLdpExternalBill.i18n('Foss.partialline.messageBox.alert.tip'),
									unValidateWaybillSerialNos + partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.unInputWeightVolumn'));
						}
						if(validateRecordList ==null || validateRecordList.length == 0){
							Ext.getCmp('Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton').enable(true);
							return;
						}
						jsonDatas = {vo:{uninputLdpExternalBill:validateRecordList}};
						Ext.Ajax.request({
		            		url:partialline.realPath('addLdpExternalBill.action'),
		            		jsonData:jsonDatas,
		    				success:function(response){
		    					Ext.getCmp('Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton').enable(true);
		    					var result = Ext.decode(response.responseText);
		    					var failedWaybillNos = result.vo.waybillNos;
		    					if(failedWaybillNos.length == 0){
		    						Ext.ux.Toast.msg(partialline.uninputLdpExternalBill.i18n('Foss.partialline.messageBox.alert.tip'),
		    								partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.saveSuccess'));
		    						partialline.uninputLdpExternalBill.pagingBar.moveFirst();
		    					}else{
		    						partialline.uninputLdpExternalBill.pagingBar.moveFirst();
		    						Ext.Msg.alert(partialline.uninputLdpExternalBill.i18n('Foss.partialline.messageBox.alert.tip'),
		    								partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.saveFailed') + '['+failedWaybillNos+']');
		    					}
								
		    				},
		    				exception:function(response){
		    					Ext.getCmp('Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton').enable(true);
		    					var result = Ext.decode(response.responseText);
		    					Ext.Msg.alert(partialline.uninputLdpExternalBill.i18n('Foss.partialline.messageBox.alert.tip'),
		    							partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.saveFailed') + '<br>' + result.message);
		    					partialline.uninputLdpExternalBill.pagingBar.moveFirst();
		    					return false;
		    				}
		            	});
					},
    				exception:function(response){
    					Ext.getCmp('Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton').enable(true);
    					var result = Ext.decode(response.responseText);
    					Ext.Msg.alert(partialline.uninputLdpExternalBill.i18n('Foss.partialline.messageBox.alert.tip'),
    							partialline.uninputLdpExternalBill.i18n('Foss.uninputLdpExternalBill.grid.uninputGrid.saveFailed') + '<br>' + result.message);
    					partialline.uninputLdpExternalBill.pagingBar.moveFirst();
    					Ext.getCmp('Foss.uninputLdpExternalBill.grid.searchResultGrid.generateBillButton').enable(true);
    					return false;
    				}
				});
			}
		}];
		
		
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		partialline.uninputLdpExternalBill.pagingBar = me.bbar;
		me.callParent([cfg]);
	}	
});

//模块入口函数
Ext.onReady(function() {
	
	//查询表单
	var queryForm = Ext.create('Foss.uninputLdpExternalBill.form.HandoverBillDetailSearch');
	partialline.uninputLdpExternalBill.queryForm=queryForm;
	
	//查询结果
	var searchResultGrid = Ext.create('Foss.uninputLdpExternalBill.grid.searchResultGrid');
	partialline.uninputLdpExternalBill.searchResultGrid=searchResultGrid;
	
	//显示偏线界面
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-queryUninputLdpExternalBillIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [queryForm,searchResultGrid],
		renderTo: 'T_partialline-queryUninputLdpExternalBillIndex-body'
	});
	partialline.uninputLdpExternalBill.pagingBar.moveFirst();
});