//package Foss.partialline.ldpExternalBill
//落地配外发模型
Ext.define('Foss.partialline.ldpExternalBill.model.ldpExternalBillModel', {
	extend: 'Ext.data.Model',
	idProperty : 'uuid',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'uuid',type:'string'},//UUID
		{name: 'waybillNo',type:'string'},//运单号
		{name: 'serialNo',type:'string'},//流水号--269701追加
		{name: 'agencyWaybillNo',type:'string'},//代理单号
		{name: 'handoverNo',type:'string'},//交接单号
		{name: 'externalBillNo',type:'string'},//外发单号	
		{name: 'externalUserCode', type: 'string'},//外发员工号
		{name: 'externalUserName', type: 'string'},//外发员
		{name: 'externalOrgCode', type: 'string'},//外发部门编号
		{name: 'externalOrgName', type: 'string'},//外发部门名称	
		{name: 'agentCompanyCode', type: 'string'},//落地配公司编号
		{name: 'agentCompanyName', type: 'string'},//落地配公司名称
		{name: 'agentOrgCode', type: 'string'},//落地配网点编号
		{name: 'agentOrgName', type: 'string'},//落地配网点名称
		{name: 'returnType', type: 'string'},//返单类型：原件返回-ORIGINAL
		{name: 'freightFee', type: 'number'},//外发运费（德邦付给代理）
		{name: 'agencyReturnFee', type: 'number'},//快递代理返货费
		{name: 'codAgencyFee', type: 'number'},//代收货款手续费
		{name: 'payDpFee', type: 'number'},//到付运费
		{name: 'toPayFee', type: 'number'},//到付手续费
		{name: 'externalInsuranceFee', type: 'number'},//外发保价费
		{name: 'notes', type: 'string'},//备注
		{name: 'auditStatus', type: 'string'},//审核状态
		{name: 'registerTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//录入日期
		{name: 'modifyTime', type: 'date',
				convert: function(value) {
					if (value != null) {
						var date = new Date(value);
						return Ext.Date.format(date,'Y-m-d H:i:s');
					} else {
						return null;
					}
				}},//修改日期
		{name: 'modifyUserCode', type: 'string'},//修改人编码
		{name: 'modifyUserName', type: 'string'},//修改人
        {name: 'receiveCustomerAddress', type: 'string'},//详细地址
        {name: 'address', type: 'string'},//详细地址
		{name: 'transferExternal', type: 'string'},//是否中转外发
		{name: 'sendStatus', type: 'string'},//发送状态
		{name: 'currencyCode', type: 'string'},//币制
		{name: 'codAmount', type: 'number'},//代收货款
		{name: 'billReceiveable', type: 'number'},//应收费用	
		{name: 'billPayable', type: 'number'}//应付费用
	]
});

//审核状态模型
Ext.define('Foss.partialline.ldpExternalBill.model.AuditStatus', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'id',type:'string'},
		{name: 'name',type:'string'},
		{name: 'value',type:'string'}
	]
});

//审核状态数据源
Ext.define('Foss.partialline.ldpExternalBill.store.AuditStatusStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.ldpExternalBill.model.AuditStatus',
	data : {
		'items':[		
			{id : '1', name : partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.waitingAudit'),  value: 'WAITINGAUDIT'},//待审核
			{id : '2', name : partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.hasAudited'),  value: 'HASAUDITED'},//已审核
			{id : '3', name : partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.backAudit'),  value: 'BACKAUDIT'},//反审核
			{id : '0', name : partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.Invalid'),  value: 'INVALID'},//作废
			{id : '4', name : partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.All'),  value: 'ALL'}//全部
		
		]
	},
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

//落地配外发数据源
Ext.define('Foss.partialline.ldpExternalBill.store.ldpExternalBillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.ldpExternalBill.model.ldpExternalBillModel',
	pageSize:10,
	proxy: {
		type: 'ajax',
		url:partialline.realPath('listLdpExternalBill.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.ldpExternalBillList',
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
			var queryForm = partialline.ldpExternalBill.queryForm;
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
Ext.define('Foss.partialline.ldpExternalBill.form.searchForm',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:80
	},
	items: [{
		vtype:'order',
		enableKeyEvents:true,
		fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.waybillNo.label'),
		name: 'vo.dto.waybillNo',
		allowBlank:true,
		columnWidth:.25		
	},{//代理单号
		xtype:'textfield',
		enableKeyEvents:true,
		fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.agencyWaybillNo.label'),
		name: 'vo.dto.agencyWaybillNo',
		maxLength:15,//最多输入15位
		regex : /^([0-9]{0,15}\n?)+$/i,
		allowBlank:true,
		columnWidth:.25		
	},{//外发员
		fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.externalUserName.label'),
		xtype : 'commonemployeeselector',
		parentOrgCode : FossUserContext.getCurrentDept().code,
		name: 'vo.dto.externalUserCode',
		allowBlank:true,
		columnWidth:.25	
	},{//修改人
		fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.modifyUserName.label'),
		xtype : 'commonemployeeselector',
		parentOrgCode : FossUserContext.getCurrentDept().code,
		name: 'vo.dto.modifyUserCode',
		allowBlank:true,
		columnWidth:.25		
	},{//落地配公司
		fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.agentCompanyCode.label'),
		name: 'vo.dto.agentCompanyCode',
		xtype : 'commonLdpAgencyCompanySelector',
		allowBlank:true,
		columnWidth:.25		
	},{
		columnWidth:.25,
		xtype: 'combobox',
		mode:'local',
		queryMode: 'local',		
		triggerAction:'all',
		forceSelection:true,
		editable:true,
		fieldLabel:     partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.label'),
		name:           'vo.dto.auditStatus',
		displayField:   'name',
		valueField:     'value',		
		store: Ext.create('Foss.partialline.ldpExternalBill.store.AuditStatusStore'),
		value:'ALL'
	},{
		xtype: 'rangeDateField',
		fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.registerTime.label'),
		dateType: 'datetimefield_date97',
		id:'Foss_partialline_model.ldpExternalBill_registerTime_RangeDateField_ID',
		fieldId:'partialline_ldpExternalBillDateFrom_ID',
		fromName: 'vo.dto.registerTimeFrom',
		toName: 'vo.dto.registerTimeTo',
		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'),
		toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		dateRange:7,
		editable: false,
		labelWidth: 80,
		disallowBlank:true,
		allowBlank:false,
		columnWidth: .5	
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
			text: partialline.ldpExternalBill.i18n('Foss.partialline.form.partiallineSearch.button.reset'),
			handler: function() {
				var bform = partialline.ldpExternalBill.queryForm.getForm();
				bform.findField('vo.dto.waybillNo').reset();
				bform.findField('vo.dto.externalUserCode').reset();
				bform.findField('vo.dto.modifyUserCode').reset();
				bform.findField('vo.dto.agentCompanyCode').reset();
				bform.findField('vo.dto.auditStatus').reset();
				bform.findField('vo.dto.registerTimeFrom').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
				bform.findField('vo.dto.registerTimeTo').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'23','59','59'), 'Y-m-d H:i:s'));
					
			}
		},{
			border : false,
			columnWidth:.76,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: partialline.ldpExternalBill.i18n('Foss.partialline.form.partiallineSearch.button.query'),
			cls:'yellow_button',
			handler: function() {
				if(this.up('form').getForm().isValid( )){
					partialline.ldpExternalBill.pagingBar.moveFirst();
				}
				
			}
		}]
	}]
});

//落地配外发单列表
Ext.define('Foss.partialline.ldpExternalBill.grid.searchResultGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:partialline.ldpExternalBill.i18n('Foss.partialline.grid.partiallineGrid.title'),
	emptyText: partialline.ldpExternalBill.i18n('Foss.partialline.grid.emptyText'),
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	autoScroll:true,
	//查看落地配外发单
	viewLdpExternalBill:function(grid, rowIndex, colIndex){
		//设置全局变量，为录入运单时，判断自提的情况,false 表示查看，true表示录入（录入和查看两个按钮都会设置此值）
		partialline.ldpExternalBill.isWrite = false;
		//获取弹出查看窗口
		var plwindow = Ext.getCmp('Foss_partialline_window_viewLdpExternalBill_Id');
		if(Ext.getCmp('Foss_partialline_window_viewLdpExternalBill_Id') == null)
			plwindow = Ext.create('Foss.partialline.ldpExternalBill.window.ViewLdpExternalBill');	
		    //查看落地配外发单
			plwindow.setTitle(partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.window.ViewLdpExternalBill.title'));
			
			//获取窗口
			var tmpForm = Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_ID');
			
			//加载数据
			var record = grid.getStore().getAt(rowIndex);
			
			Ext.Ajax.request({
    			url : partialline.realPath('queryLdpExternalBillDetail.action'),
    			params: {
					'vo.dto.id': record.data.id
				},
    			success : function(response) {
    				var json = Ext.decode(response.responseText);
    				var ldpExternalBillDto = json.vo.dto;	
    				partialline.ldpExternalBill.ldpExternalBillDto = ldpExternalBillDto;
    				var tmpForm = Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_ID');
    				//设置数据
    				tmpForm.getForm().findField('id').setValue(ldpExternalBillDto.id); //ID
    				tmpForm.getForm().findField('waybillNo').setValue(ldpExternalBillDto.waybillNo); //运单号
    				tmpForm.getForm().findField('serialNo').setValue(ldpExternalBillDto.serialNo); //流水号
    				tmpForm.getForm().findField('agencyWaybillNo').setValue(ldpExternalBillDto.agencyWaybillNo); //代理单号
    				tmpForm.getForm().findField('externalBillNo').setValue(ldpExternalBillDto.externalBillNo);//外发单号
    				tmpForm.getForm().findField('freightFee').setValue(ldpExternalBillDto.freightFee); // 外发运费
    				tmpForm.getForm().findField('externalInsuranceFee').setValue(ldpExternalBillDto.externalInsuranceFee);// 保价费
    				tmpForm.getForm().findField('payDpFee').setValue(ldpExternalBillDto.payDpFee);// 到付运费
    				tmpForm.getForm().findField('codAmount').setValue(ldpExternalBillDto.codAmount);// 代收货款金额
    				tmpForm.getForm().findField('agencyReturnFee').setValue(ldpExternalBillDto.agencyReturnFee);// 快递代理返货费
    				tmpForm.getForm().findField('codAgencyFee').setValue(ldpExternalBillDto.codAgencyFee);// 代收货款手续费
    				tmpForm.getForm().findField('toPayFee').setValue(ldpExternalBillDto.toPayFee); //到付手续费
    				tmpForm.getForm().findField('notes').setValue(ldpExternalBillDto.notes);// 备注
    				tmpForm.getForm().findField('agentCompanyName').setValue(ldpExternalBillDto.agentCompanyName);//落地配公司
    				tmpForm.getForm().findField('agentOrgName').setValue(ldpExternalBillDto.agentOrgName);	// 到达网点
    				tmpForm.getForm().findField('externalUserName').setValue(ldpExternalBillDto.externalUserName);// 外发员
    				tmpForm.getForm().findField('externalUserCode').setValue(ldpExternalBillDto.externalUserCode);// 外发员工号
    				tmpForm.getForm().findField('externalOrgName').setValue(ldpExternalBillDto.externalOrgName);// 外发部门
    				tmpForm.getForm().findField('externalOrgCode').setValue(ldpExternalBillDto.externalOrgCode);// 外发员部门编码
    				tmpForm.getForm().findField('registerTime').setValue(Ext.Date.format(new Date(ldpExternalBillDto.registerTime), 'Y-m-d H:i:s'));// 生成日期
    				tmpForm.getForm().findField('modifyUserName').setValue(ldpExternalBillDto.modifyUserName);// 修改人
    				tmpForm.getForm().findField('modifyTime').setValue(Ext.Date.format(new Date(ldpExternalBillDto.modifyTime), 'Y-m-d H:i:s'));// 修改日期
    				tmpForm.getForm().findField('currencyCode').setValue(ldpExternalBillDto.currencyCode);//币种
    				tmpForm.getForm().findField('billReceiveable').setValue(ldpExternalBillDto.billReceiveable);//应收费用					        			
    				tmpForm.getForm().findField('billPayable').setValue(ldpExternalBillDto.billPayable);//应付费用
    				tmpForm.getForm().findField('transferExternal').setValue(ldpExternalBillDto.transferExternal);//是否中转
    				
    				//将 是否中转，备注 设置为非只读属性
    				Ext.getCmp('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.Y').setReadOnly(true);
					Ext.getCmp('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.N').setReadOnly(true);
    				tmpForm.getForm().findField('notes').setReadOnly(true);
    				//外发费和快递代理返货费设置为只读属性
    				tmpForm.getForm().findField('freightFee').setReadOnly(true);
    				tmpForm.getForm().findField('agencyReturnFee').setReadOnly(true);
    				//隐藏更新按钮
    				Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_update_Id').hide();
    				//加载运单基本信息
    				var selectedVal = record.data.waybillNo;
    				var serialNo = record.data.serialNo;
    				tmpForm.queryWayBillInfo(selectedVal,serialNo);
    				plwindow.center().show();
    				/*
    				//如果是财务人员则，编辑按钮不可见
    				if(!partialline.ldpExternalBill.isPermission('partialline/updateExternalBillButton')){
    					Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_edit_Id').hide();
    				}
    				//如果是已作废或者标志为中转的外发单不可以再编辑
    				if(record.data.auditStatus == partialline.ldpExternalBill.INVALID || record.data.transferExternal == 'Y'){
    					//隐藏编辑按钮
    					Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_edit_Id').hide();
    				}
    				//如果为录入员
    				if(partialline.ldpExternalBill.isPermission('partialline/updateExternalBillButton')){
    					//需要根据外发单判断是否为待审核可编辑状态
    					if(record.data.auditStatus == partialline.ldpExternalBill.WAITINGAUDIT || record.data.auditStatus == partialline.ldpExternalBill.BACKAUDIT)
    						Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_edit_Id').show();
    				}
    				*/
    				if(partialline.ldpExternalBill.isPermission('partialline/updateLdpExternalBillButton')){
    					Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_edit_Id').show();
    				}else{
    					Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_edit_Id').hide();
    				}
    			},
    			exception : function(response) {
    				var json = Ext.decode(response.responseText);
    				Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.error'), json.message);
    			}
    		});
	},
	//财务操作偏线外发单审核状态（审核，反审核，作废）
	auditLdpExternalBill:function(auditStatusCode){		
		var selected = partialline.ldpExternalBill.searchResultGrid.getSelectionModel().getSelection( );
		//校验是否存在选择非“待审核状态的外发单”
		var flag=true;
		//选中的操作列表
		var waitingAuditIds =  new Array();
		//服务器端请求actionUrl
		var actionUrl=partialline.realPath(auditStatusCode+'.action');
		var tipContent;
		//判断是否选中
		if(Ext.isEmpty(selected)){
			Ext.MessageBox.alert("提示",'请选择需要操作的外发单!');
			return ;
		}else{
			for(var i in selected){
				if(auditStatusCode=='auditLdpExternalBill'){//审核操作
					tipContent='确认审核?';	
					if(selected[i].data.auditStatus==partialline.ldpExternalBill.WAITINGAUDIT||selected[i].data.auditStatus==partialline.ldpExternalBill.BACKAUDIT){//待审核或反审核
						waitingAuditIds.push(selected[i].data.id);   
					}else{
						Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择[待审核|反审核]状态的外发单进行审核!');
						flag=false;
						return ;
					}
				}else if(auditStatusCode=='deAuditLdpExternalBill'){
					tipContent='确认反审核?';
					if(selected[i].data.auditStatus==partialline.ldpExternalBill.HASAUDITED){//已审核
						waitingAuditIds.push(selected[i].data.id);   
					}else{
						Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择[已审核]状态的外发单进行反审核!');
						flag=false;
						return ;
					}			
				}else if(auditStatusCode=='invalidLdpExternalBill'){
					tipContent='确认作废?';
					if(selected[i].data.auditStatus==partialline.ldpExternalBill.WAITINGAUDIT||selected[i].data.auditStatus==partialline.ldpExternalBill.BACKAUDIT){//待审核或反审核
						waitingAuditIds.push(selected[i].data.id);   
					}else if(selected[i].data.auditStatus==partialline.ldpExternalBill.HASAUDITED){//已审核
						Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'存在已审核的外发单，需先反审核再进行作废，请确认');
						flag=false;
						return ;
					}else if(selected[i].data.auditStatus==partialline.ldpExternalBill.INVALID){//已作废
						Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'不能对已作废的外发单进行再次作废，请确认');
						flag=false;
						return ;
					}else{//其他情况
						Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择[待审核|反审核]状态的外发单进行作废!');
						flag=false;
						return ;
					}	
				}			
			}
			//确认操作提示
			Ext.Msg.confirm('提示',tipContent, function(btn){
			    if (btn == 'yes'){
			    	//继续执行
			    	//如果校验通过，则将收集的数据进行审核操作
					if(flag){
							//是否选择需要操作的外发单
							if(waitingAuditIds.length<1){
								Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择需要操作的外发单！');
								return ;
							}else{
								var params = {vo:{auditIds:waitingAuditIds}};					
								//执行相应操作
								Ext.Ajax.request({
				        			url : actionUrl,
				        			jsonData:params,
				        			success : function(response) {
				        				if(auditStatusCode=='auditLdpExternalBill'){//审核操作
				        					Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'审核成功！');
				        				}else if(auditStatusCode=='deAuditLdpExternalBill'){
				        					Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'反审核成功！');
				        				}else if(auditStatusCode=='invalidLdpExternalBill'){
				        					Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('foss.partialline.messageBox.alert.tip.title'),'作废成功！');
				        				}
				        				//查询最新情况
				        				partialline.ldpExternalBill.pagingBar.moveFirst();
				        			},
				        			exception : function(response) {
				        				var json = Ext.decode(response.responseText);
				        				Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
				        			}
				        		});
							}
							
					}
			    }
			});
		}
		
	},
	//不同权限，创建按钮显示
	makeTbar:function(){
		var tbar;
			tbar=[{
				xtype:'hiddenfield',
				flex:1
			},{
				xtype: 'button', //导出
				disabled: !partialline.ldpExternalBill.isPermission('partialline/exportLdpExternalBill'),
				hidden: !partialline.ldpExternalBill.isPermission('partialline/exportLdpExternalBill'),
				text: partialline.ldpExternalBill.i18n('Foss.partialline.ldpExternalBill.button.export'),
				handler: function() {					
					var queryParams = partialline.ldpExternalBill.queryForm.getValues();          
			        if(!Ext.fly('downloadAttachFileForm')){
			        	var frm = document.createElement('form');
			           	frm.id = 'downloadAttachFileForm';
			           	frm.style.display = 'none';
			           	document.body.appendChild(frm);
			        }            
		            Ext.Ajax.request({
			            url:partialline.realPath('exportLdpExternalBill.action'),
			            form: Ext.fly('downloadAttachFileForm'),
			            method : 'POST',
			            params : queryParams,
			            isUpload: true,
			            exception : function(response,opts) {              
			            },
			            success : function(response,opts) {              
			            }    
		            });
			    }
			},{
				xtype: 'button', 
				disabled: !partialline.ldpExternalBill.isPermission('partialline/auditLdpExternalBill'),
				hidden: !partialline.ldpExternalBill.isPermission('partialline/auditLdpExternalBill'),
				text: partialline.ldpExternalBill.i18n('Foss.partialline.grid.partiallineGrid.button.audited'),//审核
				handler: function() {					
					this.up('grid').auditLdpExternalBill('auditLdpExternalBill');//审核[待审核]偏线外发单
				}
			},{
				xtype: 'button', 
				disabled: !partialline.ldpExternalBill.isPermission('partialline/deAuditLdpExternalBill'),
				hidden: !partialline.ldpExternalBill.isPermission('partialline/deAuditLdpExternalBill'),
				text: partialline.ldpExternalBill.i18n('Foss.partialline.grid.partiallineGrid.button.backaudited'),//反审核
				handler: function() {
					this.up('grid').auditLdpExternalBill('deAuditLdpExternalBill');//反审核[已审核]偏线外发单
				}
			},{
				xtype: 'button', 
				disabled: !partialline.ldpExternalBill.isPermission('partialline/invalidLdpExternalBill'),
				hidden: !partialline.ldpExternalBill.isPermission('partialline/invalidLdpExternalBill'),
				text: partialline.ldpExternalBill.i18n('Foss.partialline.grid.partiallineGrid.button.invalid'),//作废
				handler: function() {
					this.up('grid').auditLdpExternalBill('invalidLdpExternalBill');//作废[待审核]偏线外发单
				}
			}];
		return tbar;
	},
	columns: [{
		xtype:'actioncolumn',
		menuDisabled:true,
		text: partialline.ldpExternalBill.i18n('Foss.partialline.grid.partiallineGrid.actioncolumn.action'),
		align: 'center',
		width:40,
		sortable:false,
		autoScroll:true,	
		viewConfig: {
			stripeRows: true
		},
		items: [{
                tooltip: partialline.ldpExternalBill.i18n('Foss.partialline.grid.partiallineGrid.actioncolumn.tooltip'),
				iconCls:'deppon_icons_showdetail',		
				width:40,
                handler: function(grid, rowIndex, colIndex) {		
					var uuid = grid.getStore().getAt(rowIndex).data.uuid;					
					this.up('grid').viewLdpExternalBill(grid, rowIndex, colIndex);
                }
            }]
	},{ //运单号
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.waybillNo.label'),
//		flex: 1,
		width : 90,
		dataIndex: 'waybillNo'
	},{ //269701--lln--2015/10/25--begin
		//流水号
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.serialNo.label'),
//		flex: 1,
		width : 90,
		dataIndex: 'serialNo'
		//269701--lln--2015/10/25--begin
	},{ //代理单号
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.agencyWaybillNo.label'),
//		flex: 1,
		width : 100,
		dataIndex: 'agencyWaybillNo'
	},{ //外发单号
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.externalBillNo.label'),
//		flex: 1,
		width : 150,
		dataIndex: 'externalBillNo'
	},{  //中转外发
		text: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal'),
//		flex: 0.8,
		width : 80,
		dataIndex: 'transferExternal',
		renderer:function(value){
			if(value=='N'){
				return partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.no');
			}else{
				return partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.yes');
			}
		}		
	},{ //落地配公司
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.agentCompanyCode.label'),
//		flex: 1.2,
		width : 110,
		dataIndex: 'agentCompanyName'
	},{ //到付运费
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.payDpFee.label'),
//		flex: 0.8,
		width : 80,
		dataIndex: 'payDpFee'
	},{//代收货款
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.codAmount.label'),
//		flex: 0.8,
		width : 80,
		dataIndex: 'codAmount'
	},{//外发运费
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.freightFee.label'),
//		flex: 0.8,
		width : 80,
		dataIndex: 'freightFee'
	},{//保价费
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.externalInsuranceFee.label'),
//		flex: 0.6, 
		width : 60,
		dataIndex: 'externalInsuranceFee'
	},{//快递代理返货费
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.agencyReturnFee.label'),
//		flex: 0.6,
		width : 110,
		dataIndex: 'agencyReturnFee'
	},{//代收货款手续费
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.codAgencyFee.label'),
//		flex: 0.8,
		width : 90,
		dataIndex: 'codAgencyFee'
	},{//到付手续费
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.toPayFee.label'),
//		flex: 0.8,
		width : 90,
		dataIndex: 'toPayFee'
	},{//返单类型
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.returnType.label'),
//		flex: 0.8,
		width : 80,
		dataIndex: 'returnType',
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value, 'RETURNBILLTYPE');
		}
	},{ //状态
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.auditStatus.label'),
//		flex: 0.6,
		width : 60,
		dataIndex: 'auditStatus',
		renderer:function(value){
			if (value == partialline.ldpExternalBill.WAITINGAUDIT) {	// 待审核			
				return partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.waitingAudit');
			}
			else if(value == partialline.ldpExternalBill.HASAUDITED){//已审核
				return partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.hasAudited');
			} 
			else if(value == partialline.ldpExternalBill.BACKAUDIT){//反审核
				return partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.backAudit');
			} 
			else if(value == partialline.ldpExternalBill.INVALID){//已作废
				return partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.Invalid');
			} 		
			else {//未知
				 return partialline.ldpExternalBill.i18n('Foss.partialline.model.partialline.auditStatus.unknown');
			}		
		}
	},{//应付费用
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.billPayable.label'),
//		flex: 0.8,
		width : 80,
		dataIndex: 'billPayable'
	},{//应收费用
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.billReceiveable.label'),
//		flex: 0.8,
		width : 80,
		dataIndex: 'billReceiveable'
	},{//外发员
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.externalUserName.label'),
//		flex: 0.8,
		width : 60,
		dataIndex: 'externalUserName'
	},{//生成时间
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.registerTime.label'),
//		flex: 0.8,
		width : 135,
		dataIndex: 'registerTime'
	},{ //币种
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.currencyCode.label'),
//		flex: 0.6,
		width : 50,
		dataIndex: 'currencyCode'
	},{//修改人
		xtype : 'ellipsiscolumn',
		text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.model.ldpExternalBill.modifyUserName.label'),
//		flex: 0.8,
		width : 60,
		dataIndex: 'modifyUserName'
	},
    {//详细地址
        xtype : 'ellipsiscolumn',
        text: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiverAddr'),
//		flex: 0.8,
        width : 60,
        dataIndex: 'receiveCustomerAddress'
    }
    ],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.partialline.ldpExternalBill.store.ldpExternalBillStore');
		if(Ext.isEmpty(me.selModel)){
			me.selModel = Ext.create('Ext.selection.CheckboxModel');
		}
		/*
		if(partialline.ldpExternalBill.isPermission('partialline/invalidePartiallineButton')){//财务角色
			me.selModel = Ext.create('Ext.selection.CheckboxModel');			
		}
		if(partialline.ldpExternalBill.isPermission('partialline/deAuditPartiallineButton')){
			if(Ext.isEmpty(me.selModel))
			me.selModel = Ext.create('Ext.selection.CheckboxModel');
		}
		if(partialline.ldpExternalBill.isPermission('partialline/auditPartiallineButton')){
			if(Ext.isEmpty(me.selModel))
			me.selModel = Ext.create('Ext.selection.CheckboxModel');
		}
		*/
		me.tbar = this.makeTbar();
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		partialline.ldpExternalBill.pagingBar = me.bbar;
		me.callParent([cfg]);
	}	
});

//提示
Ext.define('Foss.partialline.ldpExternalBill.form.tip',{
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	 defaults:{
			xtype: 'textfield',
			margin:'0 5 5 5',
			anchor: '90%'					
	},	
	items:[{
		name: 'contactPhone',							
		fieldLabel: '',
		labelSeparator: '',
		xtype: 'textareafield',
		labelWidth:70,		
		readOnly:true,	
		fieldStyle:'color:red',
		columnWidth:.9
	}],	
	bindData : function(record){
		me=this;
		me.items.items[0].setValue(record);
	},
	constructor: function(config){		
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
		
	}
});

//查看落地配外发单表单
Ext.define('Foss.partialline.ldpExternalBill.form.ViewLdpExternalBill',{
		extend: 'Ext.form.Panel',			
		bodyStyle:'padding:5px 5px 0',
		id:'Foss_partialline_form_viewLdpExternalBill_ID',
		fieldDefaults: {
			msgTarget: 'side',
			labelWidth: 90,
			margin:'5 5 5 0'
		},
		defaults: {
			anchor: '97%'
		},
		queryWayBillInfo:function(selectedVal,serialNo){
			var params ;
			//录入外发单情况,需要验证运单号是否已录入validateWaybillNo:yes
			if(partialline.ldpExternalBill.isWrite)
				params = {vo:{dto:{waybillNo:selectedVal},validateWaybillNo:'yes'}};//需要验证运单号
			//查询编辑情况，不需要验证运单号是否已录入validateWaybillNo:no
			else
				params = {vo:{dto:{waybillNo:selectedVal,serialNo:serialNo},validateWaybillNo:'no'}};			
				Ext.Ajax.request({
        			url : partialline.realPath('queryWaybillInfoByWaybillNo.action'),
        			jsonData:params,
        			success : function(response) {
        				var json = Ext.decode(response.responseText);
        				var billInfo = json.vo.billInfo;					        				
        				var tmpForm = Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_ID').getForm();
        				//设置数据
        				tmpForm.findField('targetOrgCode').setValue(billInfo.targetOrgCode);
        				tmpForm.findField('agentDeptName').setValue(billInfo.agentDeptName);
        				tmpForm.findField('billTime').setValue(billInfo.billTime);
        				tmpForm.findField('preArriveTime').setValue(billInfo.preArriveTime);
        				tmpForm.findField('receiveOrgName').setValue(billInfo.createOrgCode);
        				tmpForm.findField('productCode').setValue(billInfo.productCode);
        				tmpForm.findField('receiveMethod').setValue(billInfo.receiveMethod);
        				tmpForm.findField('contactPhone').setValue(billInfo.contactPhone);
        				tmpForm.findField('address').setValue(billInfo.address);	
        				tmpForm.findField('returnBillType').setValue(billInfo.returnBillType);
        				tmpForm.findField('transportFee').setValue(billInfo.transportFee);
        				tmpForm.findField('paidMethod').setValue(billInfo.paidMethod);

        				tmpForm.findField('codRate').setValue(billInfo.codRate);
        				tmpForm.findField('insuranceFee').setValue(billInfo.insuranceFee);
        				tmpForm.findField('insuranceAmount').setValue(billInfo.insuranceAmount);
        				tmpForm.findField('deliveryCustomerName').setValue(billInfo.deliveryCustomerName);
        				tmpForm.findField('receiveCustomerName').setValue(billInfo.receiveCustomerName);
        				tmpForm.findField('receiveCustomerContact').setValue(billInfo.receiveCustomerContact);					        			
        				tmpForm.findField('receiverPhone').setValue(billInfo.receiveCustomerPhone);
        				tmpForm.findField('receiverAddr').setValue(billInfo.receiveCustomerAddress);
        				tmpForm.findField('goodsWeightTotal').setValue(billInfo.goodsWeightTotal);
        				tmpForm.findField('goodsVolumeTotal').setValue(billInfo.goodsVolumeTotal);
        				tmpForm.findField('goodsQtyTotal').setValue(billInfo.goodsQtyTotal);
        				tmpForm.findField('goodsName').setValue(billInfo.goodsName);
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
        			}
        		});
		},
		items : [{				
				xtype:'fieldset',//落地配外发单信息
				title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.title.fieldset1'),
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					labelWidth : 100,
					anchor: '100%'
				},
				items: [
				        {name: 'id', hidden:true, hideLabel:true},
						{	// 运单号
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.waybillNo'),
							name: 'waybillNo',
							labelWidth : 70,
							readOnly:true,
							columnWidth:.25	
						},{	// 269701--lln--2015/10/26--begin
							//流水号
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.serialNo'),
							name: 'serialNo',
							labelWidth : 70,
							readOnly:true,
							columnWidth:.22
							// 269701--lln--2015/10/26--end
						},{	// 代理单号
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.agencyWaybillNo'),
							name: 'agencyWaybillNo',
							labelWidth : 60,
							readOnly:true,
							columnWidth:.23		
						},{	// 外发单号
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalBillNo'),
							name: 'externalBillNo',
							labelWidth : 60,
							readOnly:true,
							columnWidth:.30	
						},{	// 外发运费
							xtype: 'numberfield',
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.freightFee'),
							name: 'freightFee',
							allowBlank: false,
							disallowBlank:true,
							minValue:0, 
							maxLength:10,
							labelWidth : 100,
							columnWidth:.25,
							margin:'5 15 5 0',
							listeners: {
					            change: function(field, value) {
					                //代理返货费
					                var agencyReturnFee=this.up('form').getValues().agencyReturnFee;
						                if(agencyReturnFee =='' ||agencyReturnFee == 'undefined' || agencyReturnFee == undefined )
						                	agencyReturnFee=0.00;
					                //保价费
					                var externalInsuranceFee=this.up('form').getValues().externalInsuranceFee;
					                if(externalInsuranceFee =='' ||externalInsuranceFee == 'undefined' || externalInsuranceFee == undefined )
					                	externalInsuranceFee=0.00;
					                //到付手续费
					                var toPayFee=this.up('form').getValues().toPayFee;
					                if(toPayFee =='' ||toPayFee == 'undefined' || toPayFee == undefined )
					                	toPayFee=0.00;
					                //代收手续费
					                var codAgencyFee=this.up('form').getValues().codAgencyFee;
					                if(codAgencyFee =='' ||codAgencyFee == 'undefined' || codAgencyFee == undefined )
					                	codAgencyFee=0.00;
					                var total=parseFloat(value)+parseFloat(agencyReturnFee)+parseFloat(externalInsuranceFee)+parseFloat(codAgencyFee)+parseFloat(toPayFee);
					                total = Ext.Number.from(total.toFixed(3), 0);
					                this.up('form').getForm().findField('billPayable').setValue(total);
					            }
						
					        }
						},{	// 保价费
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.externalInsuranceFee'),
							name: 'externalInsuranceFee',
							readOnly:true,
							labelWidth : 50,
							columnWidth:.22
						},{// 到付运费
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.payDpFee'),
							name: 'payDpFee',
							labelWidth : 60,
							readOnly:true,
							columnWidth:.23
						},{ // 代收货款金额
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.codAmount'),
							name: 'codAmount',
							labelWidth : 60,
							readOnly:true,
							rowspan: 2,
							columnWidth:.30
						},{// 代收货款手续费
							fieldLabel:partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.codAgencyFee'),
							name: 'codAgencyFee',
							readOnly:true,
							columnWidth:.25
						},{// 快递代理返货费
							xtype: 'numberfield',
							fieldLabel:partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.agencyReturnFee'),
							name: 'agencyReturnFee',
							minValue:0, 
							value:0,
							allowBlank: false,
							disallowBlank:true,
							maxLength:10,
							labelWidth : 100,
							columnWidth:.25,
							margin:'5 15 5 0',
							listeners: {
					            change: function(field, value) {
					            	//外发费用
					                var freightFee=this.up('form').getForm().findField('freightFee').getValue();	
					                if(freightFee =='' ||freightFee == 'undefined' || freightFee == undefined)
					                	freightFee=0.000;
					                //保价费
					                var externalInsuranceFee=this.up('form').getValues().externalInsuranceFee;
					                if(externalInsuranceFee =='' ||externalInsuranceFee == 'undefined' || externalInsuranceFee == undefined )
					                	externalInsuranceFee=0.000;
					                //代收手续费
					                var codAgencyFee=this.up('form').getValues().codAgencyFee;
					                if(codAgencyFee =='' ||codAgencyFee == 'undefined' || codAgencyFee == undefined )
					                	codAgencyFee=0.000;
					                var total=parseFloat(freightFee)+value+parseFloat(externalInsuranceFee)+parseFloat(codAgencyFee);
					                total = Ext.Number.from(total.toFixed(3), 0);
					                this.up('form').getForm().findField('billPayable').setValue(total);
					            }
					        }
						},{// 到付手续费
							//xtype: 'numberfield',
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.toPayFee'), 
							name: 'toPayFee',
							labelWidth : 80,
							readOnly:true,
							columnWidth:.50	
						},{// 备注
							xtype:'textarea',
							height:60,
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.notes'),// '备注',
							name: 'notes',
							readOnly:true,
							allowBlank:true,
							maxLength:1000,
							columnWidth:.99	
						},{ // 中转外发
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal'),
							xtype: 'radiogroup',
							readOnly:true,
					        vertical: true,
							columnWidth:.33,
							items: [
					            { id:'Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.Y', boxLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.yes'), name: 'transferExternal', inputValue: 'Y' },
					            { id:'Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.N', boxLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.no'), name: 'transferExternal', inputValue: 'N'}
					        ],
					        listeners: {
					        	change:function( field, newValue, oldValue, eOpts ){//chigo 当是否中转选择'是',“外发运费” “快递代理返货费”为手输状态，不能输入负数，允许多次修改
					        		var freightFee1 = this.up('form').getForm().findField('freightFee').getValue();
					        		if(this.up('form').getForm().findField('transferExternal').getValue()){
					        			this.up('form').getForm().findField('freightFee').setReadOnly(false);		
					        			this.up('form').getForm().findField('agencyReturnFee').setReadOnly(false);
					        			this.up('form').getForm().findField('billReceiveable').setValue(0);
										this.up('form').getForm().findField('payDpFee').setValue(0);
										this.up('form').getForm().findField('codAmount').setValue(0);
										this.up('form').getForm().findField('externalInsuranceFee').setValue(0);
										this.up('form').getForm().findField('codAgencyFee').setValue(0);
										this.up('form').getForm().findField('toPayFee').setValue(0);
										
					        			var billPayable = parseFloat(this.up('form').getForm().findField('freightFee').getValue())+
			        					parseFloat(this.up('form').getForm().findField('codAgencyFee').getValue())+
			        					parseFloat(this.up('form').getForm().findField('externalInsuranceFee').getValue())+
			        					parseFloat(this.up('form').getForm().findField('agencyReturnFee').getValue())+
			        					parseFloat(this.up('form').getForm().findField('toPayFee').getValue());
					        			
					        			billPayable = Ext.Number.from(billPayable.toFixed(3), 0);
										this.up('form').getForm().findField('billPayable').setValue(billPayable);
					        			
					        			
					        			
					        			var agencyReturnFee =partialline.ldpExternalBill.ldpExternalBillDto.agencyReturnFee;
										 if(agencyReturnFee =='' ||agencyReturnFee == 'undefined' || agencyReturnFee == undefined )
							                	agencyReturnFee=0.00;
										this.up('form').getForm().findField('agencyReturnFee').setValue(agencyReturnFee);
										this.up('form').getForm().findField('codAgencyFee').setValue(0);
					        		}else{
					        			this.up('form').getForm().findField('freightFee').setReadOnly(true);		
					        			this.up('form').getForm().findField('agencyReturnFee').setReadOnly(true);
					        			this.up('form').getForm().findField('billPayable').setValue(partialline.ldpExternalBill.ldpExternalBillDto.billPayable);
					        			this.up('form').getForm().findField('billReceiveable').setValue(partialline.ldpExternalBill.ldpExternalBillDto.billReceiveable);
										this.up('form').getForm().findField('payDpFee').setValue(partialline.ldpExternalBill.ldpExternalBillDto.payDpFee);
										this.up('form').getForm().findField('codAmount').setValue(partialline.ldpExternalBill.ldpExternalBillDto.codAmount);
										this.up('form').getForm().findField('externalInsuranceFee').setValue(0);
										this.up('form').getForm().findField('codAgencyFee').setValue(partialline.ldpExternalBill.ldpExternalBillDto.codAgencyFee);
										this.up('form').getForm().findField('freightFee').setValue(partialline.ldpExternalBill.ldpExternalBillDto.freightFee);
										this.up('form').getForm().findField('toPayFee').setValue(partialline.ldpExternalBill.ldpExternalBillDto.toPayFee);
										this.up('form').getForm().findField('agencyReturnFee').setValue(0);
										
					        		}
					        	}
					           
					        }
						},{//落地配公司
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.agentCompanyName'),
							name: 'agentCompanyName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: '快递代理公司',
								height: 100,
								width: 400,
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{ // 到达网点
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.agentDeptName'),
							name: 'agentOrgName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: '到达网点',
								height: 100,
								width: 400,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{ // 外发员
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.externalUserName'),
							name: 'externalUserName',
							readOnly:true,
							rowspan: 2,
							columnWidth:.33	
						},{ // 外发员工号
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.externalUserCode'),
							name: 'externalUserCode',
							readOnly:true,
							columnWidth:.33	
						},{ // 外发部门
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.externalOrgName'),
							name: 'externalOrgName',
							readOnly:true,
							columnWidth:.33	
						},{ // 外发员部门编码
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.externalOrgCode'),
							name: 'externalOrgCode',
							readOnly:true,
							columnWidth:.33	
						},{ // 生成日期
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.registerTime'),
							name: 'registerTime',
							readOnly:true,
							columnWidth:.33	
						},{ // 修改人
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.modifyUserName'),
							name: 'modifyUserName',
							readOnly:true,
							columnWidth:.33	
						},{ // 修改日期
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.modifyTime'),
							name: 'modifyTime',
							readOnly:true,
							columnWidth:.33	
						},{//币种
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.currencyCode'),
							name: 'currencyCode',
							readOnly:true,
							columnWidth:.33
						},{//应收费用
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.billReceiveable'),
							name: 'billReceiveable',
							readOnly:true,
							columnWidth:.33
						},{//应付费用
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.fieldLabel.billPayable'),
							name: 'billPayable',
							readOnly:true,
							columnWidth:.33
						}]
			},{				
				xtype:'fieldset',//运单基本信息
				title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.title.fieldset2'),
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.targetOrgCode'),// '目的站',
							name: 'targetOrgCode',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.targetOrgCode'),//'目的站',
								height: 150,
								width: 500,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}	
						},{// 提货网点
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.agentDeptName'),
							name: 'agentDeptName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.agentDeptName'),
								height: 100,
								width: 400,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{//开单时间
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.billTime'),
							name: 'billTime',
							readOnly:true,
							columnWidth:.33		
						},{//承诺到达时间
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.preArriveTime'),
							name: 'preArriveTime',
							readOnly:true,
							columnWidth:.33		
						},{//收货部门
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiveOrgName'),
							name: 'receiveOrgName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {//'到达网点电话',
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiveOrgName'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{//运输性质
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.productCode'),
							name: 'productCode',
							readOnly:true,
							columnWidth:.33		
						},{//提货方式
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiveMethod'),
							name: 'receiveMethod',
							readOnly:true,
							columnWidth:.33		
						},{// 到达网点电话
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.contactPhone'),
							name: 'contactPhone',
							readOnly:true,
							columnWidth:.66,
							tipConfig: {//'到达网点电话',
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.contactPhone'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{ //返单类别
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.returnBillType'),
							name: 'returnBillType',
							readOnly:true,
							columnWidth:.33	
						},{// '到达网点地址',
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.address'),
							name: 'address',
							readOnly:true,
							columnWidth:.66,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.address'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{//运费
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.transaportFee'),
							name: 'transportFee',
							readOnly:true,
							columnWidth:.33	
						},{//付款方式
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.paidMethod'),
							name: 'paidMethod',
							readOnly:true,
							columnWidth:.33	
						},{//代收货款费率
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.codRate'),
							name: 'codRate',
							readOnly:true,
							columnWidth:.33	
						},{//开单保险费
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.insuranceFee'),
							name: 'insuranceFee',
							readOnly:true,
							columnWidth:.33	
						},{//保险价值
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.insuranceAmount'),
							name: 'insuranceAmount',
							readOnly:true,
							columnWidth:.33	
						},{//寄件单位
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.deliveryCustomerName'),
							name: 'deliveryCustomerName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.deliveryCustomerName'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{//收件单位
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiveCustomerName'),
							name: 'receiveCustomerName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiveCustomerName'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{ // 收件联系人
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiveCustomerContact'),
							name: 'receiveCustomerContact',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiveCustomerContact'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{ // 收件联系方式
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiverPhone'),
							name: 'receiverPhone',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiverPhone'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
							
						},{// 货物名称
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsName'),
							name: 'goodsName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsName'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{ // 收件详细地址
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiverAddr'),
							name: 'receiverAddr',
							readOnly:true,
							columnWidth:.66,
							tipConfig: {
								title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.form.ViewLdpExternalBill.waybillInfo.receiverAddr'),
								height: 100,
								width: 300,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.ldpExternalBill.form.tip'
							}
						},{// 重量
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsWeightTotal'),
							name: 'goodsWeightTotal',
							readOnly:true,
							columnWidth:.33		
						},{// 体积
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsVolumeTotal'),
							name: 'goodsVolumeTotal',
							readOnly:true,
							columnWidth:.33		
						},{// 件数
							fieldLabel: partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsQtyTotal'),
							name: 'goodsQtyTotal',
							readOnly:true,
							columnWidth:.33		
						}]
			},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			border : false,
			columnWidth:.84,
			id:'Foss_partialline_form_viewLdpExternalBill_button_holder_Id',
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',//更新	
			id:'Foss_partialline_form_viewLdpExternalBill_button_update_Id',
			text:  partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.button.update'),		
			handler: function() {
				var formEl = Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_ID');
				var form = formEl.getForm();
				//倍数默认为4倍（更新后的应付费用不大于更新前的4倍）
				var times = 4 ;
				var freightFee = form.findField('freightFee').getValue();
				if (Ext.isEmpty(freightFee)) {
					Ext.ux.Toast.msg("提示信息",
							"外发费用不能为空！");
					return;
				}
				var agencyReturnFee = form.findField('agencyReturnFee').getValue();
				if (Ext.isEmpty(agencyReturnFee)) {
					Ext.ux.Toast.msg("提示信息",
							"快递代理返货费不能为空！");
					return;
				}
				//修改前的应付费用
				var p_billPayable = partialline.ldpExternalBill.ldpExternalBillDto.billPayable;
				//修改后的应付费用
				var billPayable = form.findField('billPayable').getValue();
			if (billPayable>times*p_billPayable){
					Ext.MessageBox.alert('提示',"应付费用>修改前的应付费用的4倍,需要[0≤应付费用≤修改前的应付费用的4倍]");
					return ;
				}
				
        		formEl.getEl().mask("正在保存，请稍等...");
        		Ext.Ajax.request({
        			url : partialline.realPath('updateLdpExternalBill.action'),
        			params: {
	    				'vo.dto.id': form.getValues().id,
	    				'vo.dto.notes': form.getValues().notes,
	    				'vo.dto.transferExternal': form.getValues().transferExternal,
	    				'vo.dto.billPayable': form.getValues().billPayable,
	    				'vo.dto.freightFee': form.getValues().freightFee,
	    				'vo.dto.agencyReturnFee': form.getValues().agencyReturnFee,

	    			},
        			success : function(response) {
        				formEl.getEl().unmask();
        				
        				var json = Ext.decode(response.responseText);
        				var ldpExternalBillDto = json.vo.dto;
        				//更新页面的修改人和修改时间
        				form.findField('modifyUserName').setValue(FossUserContext.getCurrentUser().employee.empName);
        				form.findField('modifyTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
        				
        				// 快递代理返货费
        				form.findField('agencyReturnFee').setValue(ldpExternalBillDto.agencyReturnFee == null ? 0 : ldpExternalBillDto.agencyReturnFee);
        				// 代收货款手续费
        				form.findField('codAgencyFee').setValue(ldpExternalBillDto.codAgencyFee == null ? 0 : ldpExternalBillDto.codAgencyFee);
        				// 保价费
        				form.findField('externalInsuranceFee').setValue(ldpExternalBillDto.externalInsuranceFee == null ? 0 : ldpExternalBillDto.externalInsuranceFee);
        				// 应付（成本总额）
        				form.findField('billPayable').setValue(ldpExternalBillDto.billPayable == null ? 0 : ldpExternalBillDto.billPayable);
        				// 外发运费
        				form.findField('freightFee').setValue(ldpExternalBillDto.freightFee == null ? 0 : ldpExternalBillDto.freightFee);
        				// 应收费用
        				form.findField('billReceiveable').setValue(ldpExternalBillDto.billReceiveable == null ? 0 : ldpExternalBillDto.billReceiveable);
        				
        				if(form.getValues().transferExternal == 'Y'){
        					Ext.getCmp('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.Y').setReadOnly(true);
        					Ext.getCmp('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.N').setReadOnly(true);
        				}
        				//信息提示
						Ext.ux.Toast.msg('提示信息', '保存成功!');
        			},
        			exception : function(response) {
        				formEl.getEl().unmask();
        				var json = Ext.decode(response.responseText);
        				Ext.MessageBox.alert(partialline.ldpExternalBill.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
        			}
        		});
			}
		},{
			columnWidth:.08,
			xtype : 'button',
			id:'Foss_partialline_form_viewLdpExternalBill_button_edit_Id',
			text:  '编辑',//'编辑',			
			handler: function() {
				var form = this.up('form').getForm();
				//更新、编辑按钮控制
				Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_update_Id').show();
				Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_button_edit_Id').hide();
				
				if(form.getValues().transferExternal == 'N'){
					Ext.getCmp('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.Y').setReadOnly(false);
					Ext.getCmp('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.N').setReadOnly(false);
				}
				form.findField('notes').setReadOnly(false);
				//外发费和快递代理返货费设置为非只读属性
				if(form.getValues().transferExternal == 'Y'){
					form.findField('freightFee').setReadOnly(false);
					form.findField('agencyReturnFee').setReadOnly(false);				}
				
			}
		},{
			xtype : 'button',
			columnWidth:.08,
			text: '关闭',// '关闭',
			handler: function() {				
				this.up('window').close();
			}
		}]
	}]
});

//查看落地配外发单
Ext.define('Foss.partialline.ldpExternalBill.window.ViewLdpExternalBill',{
	extend:'Ext.window.Window',
	id:'Foss_partialline_window_viewLdpExternalBill_Id',
	title: partialline.ldpExternalBill.i18n('Foss.ldpExternalBill.window.ViewLdpExternalBill.title'),
	modal:true,
	closeAction:'hide',
	width: 800,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_ID')==null?Ext.create('Foss.partialline.ldpExternalBill.form.ViewLdpExternalBill'):Ext.getCmp('Foss_partialline_form_viewLdpExternalBill_ID')
	]
});

//显示于标签页
Ext.onReady(function() {
	partialline.ldpExternalBill.WAITINGAUDIT="WAITINGAUDIT";//待审核
	partialline.ldpExternalBill.HASAUDITED="HASAUDITED";//已经审核
	partialline.ldpExternalBill.BACKAUDIT="BACKAUDIT";//反审核
	partialline.ldpExternalBill.INVALID="INVALID";	//已作废
	
	//外发单费用Map
	var ldpBillFeeInfoMap = new Ext.util.HashMap();
	partialline.ldpExternalBill.ldpBillFeeInfoMap = ldpBillFeeInfoMap;
	
	//偏线查询表单
	var searchForm=Ext.create('Foss.partialline.ldpExternalBill.form.searchForm');
	var searchResult=Ext.create('Foss.partialline.ldpExternalBill.grid.searchResultGrid');
	//全局可获取查询窗口和查询结果表格
	partialline.ldpExternalBill.queryForm=searchForm;
	partialline.ldpExternalBill.searchResultGrid=searchResult;
	//保存dto的全局变量，为了中转外发选择否时，恢复初始状态
	partialline.ldpExternalBill.ldpExternalBillDto="";
		
	//显示偏线界面
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-queryLdpExternalBillIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [searchForm,searchResult],
		renderTo: 'T_partialline-queryLdpExternalBillIndex-body'
	});	
	searchResult.getStore().load();
});