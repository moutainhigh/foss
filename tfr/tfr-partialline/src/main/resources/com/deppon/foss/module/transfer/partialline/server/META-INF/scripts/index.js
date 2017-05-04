//package Foss.partialline
//偏线模型
Ext.define('Foss.partialline.model.Partialline', {
	extend: 'Ext.data.Model',
	idProperty : 'uuid',
	fields: [
		{name: 'id',type:'string'},//ID
		{name: 'uuid',type:'string'},//UUID
		{name: 'externalBillNo',type:'string'},//外发单号			
		{name: 'externalUserCode', type: 'string'},//外发员工号
		{name: 'externalUserName', type: 'string'},//外发员
		{name: 'externalAgencyFee', type: 'float'},//外发代理费
		{name: 'deliveryFee', type: 'float'},//代理送货费
		{name: 'costAmount', type: 'float'},//外发成本总额
		{name: 'receiveAgencyFee', type: 'float'},//实收代理费
		{name: 'payAgencyFee', type: 'float'},//实付代理费
		{name: 'isWriteOff', type: 'string'},//自动核销申请		
		{name: 'registerTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}},//录入日期
		{name: 'registerUserCode', type: 'string'},//录入人工号
		{name: 'registerUser', type: 'string'},//录入人
		{name: 'externalOrgCode', type: 'string'},//外发部门编号
		{name: 'externalOrgName', type: 'string'},//外发部门名称	
		{name: 'auditStatus', type: 'string'},//审核状态
		{name: 'notes', type: 'string'},//备注
		{name: 'waybillNo',type:'string'},//运单号	
		{name: 'handoverNo',type:'string'},//交接单号	
		{name: 'agentCompanyCode', type: 'string'},//偏线代理编号
		{name: 'agentCompanyName', type: 'string'},//偏线代理名称
		{name: 'toPayAmount', type: 'float'},//到付运费	
		{name: 'beAutoDelivery', type: 'string'},//自提	
		{name: 'currencyCode', type: 'string'},//币种	
		{name: 'transferExternal', type: 'string'},//中转外发	
		{name: 'modifyDate', type: 'int'},//修改时间
		{name: 'otherFee', type: 'string'}//其他费用
	]
});

//审核状态模型
Ext.define('Foss.partialline.model.AuditStatus', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'id',type:'string'},
		{name: 'name',type:'string'},
		{name: 'value',type:'string'}
	]
});

Ext.define('Foss.partialline.model.WaybillNoSelector', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'waybillNo',type:'string'},
		{name: 'id',type:'string'}
	]
});

//审核状态数据源
Ext.define('Foss.partialline.store.WaybillNoSelector',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.WaybillNoSelector',
	data : {
		'items':[
			{id : '1', waybillNo : 'y12345'},
			{id : '2', waybillNo : 'y12346'},
			{id : '3', waybillNo : 'y12347'},
			{id : '4', waybillNo : 'y12348'}
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

//审核状态数据源
Ext.define('Foss.partialline.store.AuditStatusStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.AuditStatus',
	data : {
		'items':[		
			{id : '1', name : partialline.i18n('Foss.partialline.model.partialline.auditStatus.waitingAudit'),  value: 'WAITINGAUDIT'},//待审核
			{id : '2', name : partialline.i18n('Foss.partialline.model.partialline.auditStatus.hasAudited'),  value: 'HASAUDITED'},//已审核
			{id : '3', name : partialline.i18n('Foss.partialline.model.partialline.auditStatus.backAudit'),  value: 'BACKAUDIT'},//反审核
			{id : '0', name : partialline.i18n('Foss.partialline.model.partialline.auditStatus.Invalid'),  value: 'INVALID'},//作废
			{id : '4', name : partialline.i18n('Foss.partialline.model.partialline.auditStatus.All'),  value: 'ALL'}//全部
		
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

//偏线据源
Ext.define('Foss.partialline.store.Partialline',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.model.Partialline',
	pageSize:10,
	proxy: {
		type: 'ajax',
		url:partialline.realPath('list.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.externalBillList',
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
			var queryForm = partialline.queryForm;
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

//查询表单
Ext.define('Foss.partialline.form.PartiallineSearch',{
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
		fieldLabel: partialline.i18n('Foss.partialline.model.partialline.waybillNo.label'),
		name: 'vo.dto.waybillNo',
		allowBlank:true,
		columnWidth:.3		
	},{
		fieldLabel: partialline.i18n('Foss.partialline.model.partialline.externalBillNo.label'),
		name: 'vo.dto.externalBillNo',
		allowBlank:true,
		columnWidth:.3	
	},{
		fieldLabel: partialline.i18n('Foss.partialline.model.partialline.externalUserName.label'),
		xtype : 'commonemployeeselector',
		//deptCode: FossUserContext.getCurrentDeptCode(),
		parentOrgCode : FossUserContext.getCurrentDept().code,
		name: 'vo.dto.externalUserCode',
		allowBlank:true,
		columnWidth:.3		
	}
	/*,{
		fieldLabel: partialline.i18n('Foss.partialline.model.partialline.externalUserCode.label'),
		name: 'vo.dto.externalUserCode',
		allowBlank:true,
		columnWidth:.3		
	}*/
	,{
		fieldLabel: partialline.i18n('Foss.partialline.model.partialline.registerUserCode.label'),
		name: 'vo.dto.registerUserCode',
		xtype : 'commonemployeeselector',
		//deptCode: FossUserContext.getCurrentDeptCode(),
		parentOrgCode : FossUserContext.getCurrentDept().code,
		allowBlank:true,
		columnWidth:.3		
	},{
		fieldLabel: partialline.i18n('Foss.partialline.model.partialline.agentCompanyName.label'),
		name: 'vo.dto.agentCompanyCode',
		xtype : 'commonvehagencycompselector',
		allowBlank:true,
		columnWidth:.3		
	},{
		columnWidth:.3,
		xtype: 'combobox',
		mode:'local',
		queryMode: 'local',		
		triggerAction:'all',
		forceSelection:true,
		editable:true,
		fieldLabel:     partialline.i18n('Foss.partialline.model.partialline.auditStatus.label'),
		name:           'vo.dto.auditStatus',
		displayField:   'name',
		valueField:     'value',		
		store: Ext.create('Foss.partialline.store.AuditStatusStore'),
		value:'ALL'
	},{
		xtype: 'rangeDateField',
		fieldLabel: partialline.i18n('Foss.partialline.model.partialline.registerTime.label'),
		dateType: 'datetimefield_date97',
		id:'Foss_partialline_model.partialline_registerTime_RangeDateField_ID',
		fieldId:'partialline_scheduleDateFrom_ID',
		fromName: 'vo.dto.registerTimeFrom',
		toName: 'vo.dto.registerTimeTo',
		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'),
		toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		dateRange:31,
		editable: false,
		labelWidth: 80,
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
			text: partialline.i18n('Foss.partialline.form.partiallineSearch.button.reset'),
			handler: function() {
				var bform = partialline.queryForm.getForm();
				bform.findField('vo.dto.waybillNo').reset();
				bform.findField('vo.dto.externalBillNo').reset();
				bform.findField('vo.dto.externalUserCode').reset();
				bform.findField('vo.dto.registerUserCode').reset();
				bform.findField('vo.dto.auditStatus').reset();
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
			text: partialline.i18n('Foss.partialline.form.partiallineSearch.button.query'),
			cls:'yellow_button',
			handler: function() {
				if(this.up('form').getForm().isValid( )){
					partialline.pagingBar.moveFirst();
				}
				
			}
		},{
			xtype : 'button',
			columnWidth:.08,
			text: '导出',
			handler: function() {
				 var queryParams = partialline.queryForm.getValues();					
					if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
						}						
						Ext.Ajax.request({
						url:partialline.realPath('exportExternalBill.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : queryParams,
						isUpload: true,
						exception : function(response,opts) {							
						},success : function(response,opts) {							
						}		
						});
	        		
			}
		}]
	}]
});

//偏线外发单列表
Ext.define('Foss.partialline.grid.PartiallineGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:partialline.i18n('Foss.partialline.grid.partiallineGrid.title'),
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	autoScroll:true,
	//查看偏线外发单
	viewpartialline:function(grid, rowIndex, colIndex){
		//设置全局变量，为录入运单时，判断自提的情况,false 表示查看，true表示录入（录入和查看两个按钮都会设置此值）
		partialline.isWritePartialline=false;
		//获取弹出查看窗口
		var plwindow = Ext.getCmp('Foss_partialline_window_viewpartialline_Id');
		if(Ext.getCmp('Foss_partialline_window_viewpartialline_Id') == null)
			plwindow=Ext.create('Foss.partialline.window.Viewpartialline');	
			plwindow.setTitle(partialline.i18n('Foss.partialline.window.Viewpartialline.view.title'));//查看偏线外发单
			
			//获取窗口
			var tmpForm=Ext.getCmp('Foss_partialline_form_viewpartialline_ID');
			//将所有的字段设置为只读属性
			var fields = tmpForm.getForm().getFields( );
			//查看时，显示外发部门、录入人、录入日期
			 tmpForm.getForm().findField('externalOrgName').show();
			 tmpForm.getForm().findField('registerUser').show();
			 tmpForm.getForm().findField('registerTime').show();			 
			//隐藏重置按钮
			 Ext.getCmp('Foss_partialline_form_viewpartialline_button_reset_Id').hide();
			 Ext.getCmp('Foss_partialline_form_viewpartialline_button_save_Id').hide();			
			//隐藏更新按钮
			 Ext.getCmp('Foss_partialline_form_viewpartialline_button_update_Id').hide();	
			 Ext.getCmp('Foss_partialline_form_viewpartialline_button_holder_Id').columnWidth=0.84;
			//加载数据
			 var record = grid.getStore().getAt(rowIndex);
			 tmpForm.loadRecord(record);
			 //外发员combox设值
			 tmpForm.getForm().findField('externalUserCode').setCombValue(record.data.externalUserName,record.data.externalUserCode);
			//加载运单基本信息
			 var selectedVal=record.data.waybillNo;
			 tmpForm.queryWayBillInfo(selectedVal,tmpForm.getForm());
			//运单号等信息不允许被修改
			 Ext.suspendLayouts();
			 for(var i=0;i<16;i++){
			 	tmpForm.getForm().getFields().get(i).setReadOnly(true);
			 }				
			Ext.resumeLayouts(true);
			tmpForm.doLayout();	
			//再次重置录入时间
			 tmpForm.getForm().findField('registerUser').setValue(record.data.registerUser);
			tmpForm.getForm().findField('registerTime').setValue(record.data.registerTime);
			plwindow.center().show();	
			//如果是财务人员则，编辑按钮不可见
			if(!partialline.isPermission('partialline/updateExternalBillButton')){
				Ext.getCmp('Foss_partialline_form_viewpartialline_button_edit_Id').hide();
			}//如果为录入员
			if(partialline.isPermission('partialline/updateExternalBillButton')){
				//需要根据外发单判断是否为待审核可编辑状态
				if(record.data.auditStatus == partialline.WAITINGAUDIT||record.data.auditStatus == partialline.BACKAUDIT)
					Ext.getCmp('Foss_partialline_form_viewpartialline_button_edit_Id').show();
			}
		
	},
	//财务操作偏线外发单审核状态（审核，反审核，作废）
	auditPartialline:function(auditStatusCode){		
		var selected = partialline.searchResultGrid.getSelectionModel().getSelection( );
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
				if(auditStatusCode=='auditPartialline'){//审核操作
					tipContent='确认审核?';	
					if(selected[i].data.auditStatus==partialline.WAITINGAUDIT||selected[i].data.auditStatus==partialline.BACKAUDIT){//待审核或反审核
						waitingAuditIds.push(selected[i].data.id);   
					}else{
						Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择[待审核|反审核]状态的外发单进行审核!');
						flag=false;
						return ;
					}
				}else if(auditStatusCode=='deAuditPartialline'){
					tipContent='确认反审核?';
					if(selected[i].data.auditStatus==partialline.HASAUDITED){//已审核
						waitingAuditIds.push(selected[i].data.id);   
					}else{
						Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择[已审核]状态的外发单进行反审核!');
						flag=false;
						return ;
					}			
				}else if(auditStatusCode=='invalidePartialline'){
					tipContent='确认作废?';
					if(selected[i].data.auditStatus==partialline.WAITINGAUDIT||selected[i].data.auditStatus==partialline.BACKAUDIT){//待审核或反审核
						waitingAuditIds.push(selected[i].data.id);   
					}else if(selected[i].data.auditStatus==partialline.HASAUDITED){//已审核
						Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'存在已审核的外发单，需先反审核再进行作废，请确认');
						flag=false;
						return ;
					}else if(selected[i].data.auditStatus==partialline.INVALID){//已作废
						Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'不能对已作废的外发单进行再次作废，请确认');
						flag=false;
						return ;
					}else{//其他情况
						Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择[待审核|反审核]状态的外发单进行作废!');
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
								Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'请选择需要操作的外发单！');
								return ;
							}else{
								var params = {vo:{auditIds:waitingAuditIds}};					
								//执行相应操作
								Ext.Ajax.request({
				        			url : actionUrl,
				        			jsonData:params,
				        			success : function(response) {
				        				if(auditStatusCode=='auditPartialline'){//审核操作
				        					Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'审核成功！');
				        				}else if(auditStatusCode=='deAuditPartialline'){
				        					Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'反审核成功！');
				        				}else if(auditStatusCode=='invalidePartialline'){
				        					Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'作废成功！');
				        				}
				        				//查询最新情况
				        				partialline.pagingBar.moveFirst();
				        			},
				        			exception : function(response) {
				        				var json = Ext.decode(response.responseText);
				        				Ext.MessageBox.alert(partialline.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
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
		//if(partialline.userRole  == 'FINANCIAL_USER'){//财务权限
			tbar=[/*{
				xtype: 'button',
				hidden:partialline.isPermission('partialline/addExternalBillButton')?false:true,//录入员权限
				text: '新增',//partialline.i18n('Foss.partialline.grid.partiallineGrid.button.addBtn'),
				handler: function() {
					//设置全局变量，为录入运单时，判断自提的情况,false 表示查看，true表示录入（录入和查看两个按钮都会设置此值）
					partialline.isWritePartialline=true;
					var plwindow = Ext.getCmp('Foss_partialline_window_viewpartialline_Id');						
					if(Ext.getCmp('Foss_partialline_window_viewpartialline_Id') == null)
						plwindow=Ext.create('Foss.partialline.window.Viewpartialline');	
					plwindow.setTitle(partialline.i18n('Foss.partialline.window.Viewpartialline.write.title'));//录入偏线外发单
					//获取窗口
					var tmpForm=Ext.getCmp('Foss_partialline_form_viewpartialline_ID');
					tmpForm.getForm().reset();
					var tmpModel = Ext.create('Foss.partialline.model.Partialline');
					tmpForm.loadRecord(tmpModel);
					var fields = tmpForm.getForm().getFields();
					//将disable的组件复原
					 for(var i=0;i<5;i++){
					 	 fields.get(i).setReadOnly(false);
					 }
					 for(var i=6;i<13;i++){
					 	fields.get(i).setReadOnly(false);
					 }
					 //将中转外发设置为不可更改
					 fields.get(12).setReadOnly(true);
					 fields.get(13).setReadOnly(true);
					 fields.get(14).setReadOnly(true);
					 //录入时，显示外发部门、录入人、录入日期
					 tmpForm.getForm().findField('externalOrgName').show();
					 tmpForm.getForm().findField('registerUser').show();
					 tmpForm.getForm().findField('registerTime').show();
					 //tmpForm.getForm().findField('externalOrgName').setValue(FossUserContext. getCurrentDept().name);
					 tmpForm.getForm().findField('registerUser').setValue(FossUserContext.getCurrentUser().employee.empName);
					 tmpForm.getForm().findField('registerTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
					Ext.getCmp('Foss_partialline_form_viewpartialline_button_reset_Id').show();
					Ext.getCmp('Foss_partialline_form_viewpartialline_button_update_Id').hide();
					Ext.getCmp('Foss_partialline_form_viewpartialline_button_edit_Id').hide();
					Ext.getCmp('Foss_partialline_form_viewpartialline_button_save_Id').show();
					Ext.getCmp('Foss_partialline_form_viewpartialline_button_holder_Id').columnWidth=0.76;
					tmpForm.doLayout();					
					//设置当前录入时间					
					plwindow.center().show();
				}
			},*/{
				xtype:'hiddenfield',
				flex:1
			},{
				xtype: 'button', 
				disabled:partialline.isPermission('partialline/auditPartiallineButton')==true?false:true,
				hidden:partialline.isPermission('partialline/auditPartiallineButton')==true?false:true,
				text: '审核',//partialline.i18n('Foss.partialline.grid.partiallineGrid.button.audited'),//审核
				handler: function() {					
					this.up('grid').auditPartialline('auditPartialline');//审核[待审核]偏线外发单
				}
			},{
				xtype: 'button', 
				disabled:partialline.isPermission('partialline/deAuditPartiallineButton')==true?false:true,
				hidden:partialline.isPermission('partialline/deAuditPartiallineButton')==true?false:true,
				text: '反审核',//partialline.i18n('Foss.partialline.grid.partiallineGrid.button.backaudited'),//反审核
				handler: function() {
					this.up('grid').auditPartialline('deAuditPartialline');//反审核[已审核]偏线外发单
				}
			},{
				xtype: 'button', 
				disabled:partialline.isPermission('partialline/invalidePartiallineButton')==true?false:true,
				hidden:partialline.isPermission('partialline/invalidePartiallineButton')==true?false:true,
				text:  '作废',//partialline.i18n('Foss.partialline.grid.partiallineGrid.button.invalid'),//作废
				handler: function() {
					this.up('grid').auditPartialline('invalidePartialline');//作废[待审核]偏线外发单
				}
			}];
		/*}else
		if(arg == 'REGISTER'){//普通偏线录入权限
			tbar=[];
		}*/		
		return tbar;
	},
	columns: [{
		xtype:'actioncolumn',
		menuDisabled:true,
		text: partialline.i18n('Foss.partialline.grid.partiallineGrid.actioncolumn.action'),
		align: 'center',
		width:40,
		sortable:false,
		autoScroll:true,	
		viewConfig: {
			stripeRows: true
		},
		items: [{
                tooltip: partialline.i18n('Foss.partialline.grid.partiallineGrid.actioncolumn.tooltip'),
				iconCls:'deppon_icons_showdetail',		
				width:40,
                handler: function(grid, rowIndex, colIndex) {		
					var uuid=grid.getStore().getAt(rowIndex).data.uuid;					
					this.up('grid').viewpartialline(grid, rowIndex, colIndex);
                }
            }]
	},{		
		text: partialline.i18n('Foss.partialline.model.partialline.waybillNo.label'),//'运单号',
		width:120,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'waybillNo'
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.externalBillNo.label'),//'外发单号',
		width:120,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'externalBillNo'
	}
	/*,{
		text: '交接单号',//'交接单号',
		width:120,
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'handoverNo'
	}*/
	,{
		text: '中转外发',//'中转外发',
		width:60,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'transferExternal',
		renderer:function(value){
			if(value=='N'){
				return '否';
			}else{
				return '是'
			}
		}		
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.agentCompanyName.label'),//'外发代理',
		width:150,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'agentCompanyName'
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.toPayAmount.label'),//'到付运费',
		width:100,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'toPayAmount'
	},{
		text:'外发代理费',
		width:100,
		menuDisabled:true,
		sortable: true, 
		readOnly:true,
		dataIndex: 'externalAgencyFee'
	},{
		text:'代理送货费',
		width:100,
		menuDisabled:true,
		sortable: true, 
		readOnly:true,
		dataIndex: 'deliveryFee'
	},{
		text:'其他费用',
		width:100,
		menuDisabled:true,
		sortable: true, 
		readOnly:true,
		dataIndex: 'otherFee'
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.costAmount.label'),//'外发成本总额',
		width:100,
		menuDisabled:true,
		sortable: true, 
		readOnly:true,
		dataIndex: 'costAmount'
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.payAgencyFee.label'),//'实付代理费',
		hidden : true,
		width:100,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'payAgencyFee'
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.receiveAgencyFee.label'),//'实收代理费',
		width:100,
		hidden : true,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'receiveAgencyFee'
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.auditStatus.label'),//'状态',
		width:120, 
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'auditStatus',
		renderer:function(value){
			if (value == partialline.WAITINGAUDIT) {	// 待审核			
				return partialline.i18n('Foss.partialline.model.partialline.auditStatus.waitingAudit');
			}
			else if(value == partialline.HASAUDITED){//已审核
				return partialline.i18n('Foss.partialline.model.partialline.auditStatus.hasAudited');
			} 
			else if(value == partialline.BACKAUDIT){//反审核
				return partialline.i18n('Foss.partialline.model.partialline.auditStatus.backAudit');
			} 
			else if(value == partialline.INVALID){//已作废
				return partialline.i18n('Foss.partialline.model.partialline.auditStatus.Invalid');
			} 		
			else {//未知
				 return partialline.i18n('Foss.partialline.model.partialline.auditStatus.unknown');
			}		
		}
	},{
		text: partialline.i18n('Foss.partialline.model.partialline.externalUserName.label'),//'外发员',
		width:120,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'externalUserName'
	},{
		text: '录入时间',//'录入时间',
		width:130,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'registerTime'
	},
	{
		text: '币种',//'币种',
		width:60,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'currencyCode'
	},//chigo
	{
		text: '备注',
		width:60,
		menuDisabled:true,
		sortable: true, 
		dataIndex: 'notes'
	}//chigo
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.partialline.store.Partialline');
		if(partialline.isPermission('partialline/invalidePartiallineButton')){//财务角色
			me.selModel = Ext.create('Ext.selection.CheckboxModel');			
		}
		if(partialline.isPermission('partialline/deAuditPartiallineButton')){
			if(Ext.isEmpty(me.selModel))
			me.selModel = Ext.create('Ext.selection.CheckboxModel');
		}if(partialline.isPermission('partialline/auditPartiallineButton')){
			if(Ext.isEmpty(me.selModel))
			me.selModel = Ext.create('Ext.selection.CheckboxModel');
		}
		me.tbar=this.makeTbar();
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		partialline.pagingBar = me.bbar;
		me.callParent([cfg]);
	}	
});

//提示
Ext.define('Foss.partialline.form.tip',{
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

//查看偏线外发单表单
Ext.define('Foss.partialline.form.Viewpartialline',{
		extend: 'Ext.form.Panel',			
		bodyStyle:'padding:5px 5px 0',
		id:'Foss_partialline_form_viewpartialline_ID',
		fieldDefaults: {
			msgTarget: 'side',
			labelWidth: 90,
			margin:'5 5 5 0'
		},
		defaults: {
			anchor: '97%'
		},
		queryWayBillInfo:function(selectedVal,form){
			var params ;
			//录入外发单情况,需要验证运单号是否已录入validateWaybillNo:yes
			if(partialline.isWritePartialline)
				params = {vo:{dto:{waybillNo:selectedVal},validateWaybillNo:'yes'}};//需要验证运单号
			//查询编辑情况，不需要验证运单号是否已录入validateWaybillNo:no
			else
				params = {vo:{dto:{waybillNo:selectedVal},validateWaybillNo:'no'}};			
				Ext.Ajax.request({
        			url : partialline.realPath('queryWaybillInfo.action'),
        			jsonData:params,
        			success : function(response) {
        				Ext.getCmp('Foss_partialline_form_viewpartialline_button_save_Id').setDisabled(false);
        				var json = Ext.decode(response.responseText);
        				var billInfo=json.vo.billInfo;					        				
        				var tmpForm=Ext.getCmp('Foss_partialline_form_viewpartialline_ID');
        				//设置数据
        				tmpForm.getForm().findField('currencyCode').setValue(billInfo.currencyCode);
        				tmpForm.getForm().findField('paidMethod').setValue(billInfo.paidMethod);
        				tmpForm.getForm().findField('toPayAmount').setValue(billInfo.toPayAmount);
        				tmpForm.getForm().findField('agentCompanyName').setValue(billInfo.agentCompanyName);
        				tmpForm.getForm().findField('agentDeptName').setValue(billInfo.agentDeptName);
        				tmpForm.getForm().findField('contactPhone').setValue(billInfo.contactPhone);
        				tmpForm.getForm().findField('address').setValue(billInfo.address);
        				tmpForm.getForm().findField('targetOrgCode').setValue(billInfo.targetOrgCode);
        				tmpForm.getForm().findField('handGoodsTime').setValue(billInfo.handGoodsTime);
        				tmpForm.getForm().findField('createOrgCode').setValue(billInfo.createOrgCode);
        				tmpForm.getForm().findField('goodsWeightTotal').setValue(billInfo.goodsWeightTotal);
        				tmpForm.getForm().findField('goodsVolumeTotal').setValue(billInfo.goodsVolumeTotal);
        				tmpForm.getForm().findField('goodsQtyTotal').setValue(billInfo.goodsQtyTotal);
        				tmpForm.getForm().findField('transportFee').setValue(billInfo.transportFee);
        				tmpForm.getForm().findField('goodsName').setValue(billInfo.goodsName);
        				tmpForm.getForm().findField('insuranceFee').setValue(billInfo.insuranceFee);
        				tmpForm.getForm().findField('goodsPackage').setValue(billInfo.goodsPackage);
        				tmpForm.getForm().findField('deliveryCustomerName').setValue(billInfo.deliveryCustomerName);
        				tmpForm.getForm().findField('insuranceAmount').setValue(billInfo.insuranceAmount);
        				tmpForm.getForm().findField('yunshushixiang').setValue(billInfo.yunshushixiang);
        				tmpForm.getForm().findField('totalFee').setValue(billInfo.totalFee);
        				tmpForm.getForm().findField('codAmount').setValue(billInfo.codAmount);
        				tmpForm.getForm().findField('receiveCustomerName').setValue(billInfo.receiveCustomerName);
        				tmpForm.getForm().findField('partnerContactPhone').setValue(billInfo.partnerContactPhone);					        				
        				tmpForm.getForm().findField('receiveCustomerContact').setValue(billInfo.receiveCustomerContact);					        				
        				tmpForm.getForm().findField('paidMethodCode').setValue(billInfo.paidMethodCode);
        				//tmpForm.getForm().findField('externalOrgName').setValue(FossUserContext.getCurrentDept().name);
					    //tmpForm.getForm().findField('registerUser').setValue(FossUserContext.getCurrentUser().employee.empName);
					    //给外发代理费用和送货费赋值
        				/*tmpForm.getForm().findField('externalAgencyFee').setValue(billInfo.externalAgencyFee);	
        				tmpForm.getForm().findField('deliveryFee').setValue(billInfo.deliveryFee);*/
        				
        				if(Ext.isEmpty(tmpForm.getForm().findField('registerTime').getValue()))
					    tmpForm.getForm().findField('registerTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
        				//SR-12	根据运单号查开单信息，判断是否自提，如果是则不能输入代理送货费
        				if(partialline.isWritePartialline){	//录入外发单情况，判断是否自提，判断是否录入付送货费做准备			        					
							var beAutoDelivery = billInfo.beAutoDelivery;
							if(beAutoDelivery=='SELF_PICKUP'){
								tmpForm.getForm().findField('deliveryFee').setValue(0);
								tmpForm.getForm().findField('deliveryFee').setReadOnly(true);
							}													
							else
								tmpForm.getForm().findField('deliveryFee').setReadOnly(true);
							//验证费用信息
							partialline.validateFeesAndWriteOff(form);        				
        				}
						else{
							//在查看修改的情况，需要更新自提字段到表单，为后续点击"编辑"按钮时，判断是否自提，录入付送货费做准备，
        					//这里的数据在点击“编辑”按钮时候会用到
								var new_record = tmpForm.getForm().getRecord();
								//由于火狐的原因，先取出时间，后面再置入
								var registerTime=new_record.data.registerTime;
								tmpForm.getForm().updateRecord(new_record);
								new_record.data.registerTime=registerTime;
								
								new_record.beAutoDelivery=billInfo.beAutoDelivery;
								tmpForm.getForm().loadRecord(new_record);
						}						
        				
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				//重置所有的运单信息
        				var tmpForm=Ext.getCmp('Foss_partialline_form_viewpartialline_ID');
        				var fields = tmpForm.getForm().getFields( );
        				for( var i=15 ;i<fields.length;i++){
        					fields.get(i).reset();
        				}
        				/*for( var i=24 ;i<fields.length;i++){
        					fields.get(i).reset();
        				}*/
        				Ext.getCmp('Foss_partialline_form_viewpartialline_button_save_Id').setDisabled(true);
        				Ext.MessageBox.alert(partialline.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
        			}
        		});
		},
		items : [{				
				xtype:'fieldset',
				title: partialline.i18n('Foss.partialline.form.Viewpartialline.title.fieldset1'),//偏线外发单,
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [
						{
							vtype:'order',
							enableKeyEvents:true,
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.waybillNo'),//'运单号',
							name: 'waybillNo',
							allowBlank:false,
							columnWidth:.33,
							listeners:{
								blur:function(txtField,eOpts ){
									if(txtField.isValid()){
										var selectedVal=txtField.value;
										this.up('form').queryWayBillInfo(selectedVal,this.up('form').getForm());
									}
								}
							}		
						},
							{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalBillNo'),//'外发单号',
							name: 'externalBillNo',
							allowBlank:false,
							maxLength:50,
							columnWidth:.33	
						},{
							xtype : 'commonemployeeselector',
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalUserName'),// '外发员',
							name: 'externalUserCode',
							//deptCode: FossUserContext.getCurrentDeptCode(),
							parentOrgCode : FossUserContext.getCurrentDept().code,
							allowBlank:false,
							columnWidth:.33,
							listeners:{
								blur:function(field, eOpts){
									if(!Ext.isEmpty(field.getValue( ))){
										partialline.queryExternalOrgName(this.up('form'),field.getValue( ));
									}
								
								}
							}
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel:partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalAgencyFee'),// '外发代理费', 
							name: 'externalAgencyFee',
							allowBlank:false,
							maxLength:8,
							columnWidth:.33,
							value:0,
							readOnly:true,
							minValue:0,
							listeners: {
						       change: function(field, value) {	
						               //送货费
						                var deliveryFee=this.up('form').getValues().deliveryFee;
						                if(deliveryFee =='' ||deliveryFee == 'undefined' || deliveryFee == undefined )
						                	deliveryFee=0.000;
						                //其他费用
						                var otherFee=this.up('form').getValues().otherFee;	
						                if(otherFee =='' ||otherFee == 'undefined' || otherFee == undefined)
						                	otherFee=0.000;
						                var total =0.00;
						                total=parseFloat(deliveryFee)+parseFloat(otherFee)+value;
						                this.up('form').getForm().findField('costAmount').setValue(total.toFixed(3));
						                
						            },
						         blur:function(field,eOpts){
						         	//验证费用信息
						            partialline.validateFeesAndWriteOff( this.up('form').getForm());
						         }
						        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '外发代理费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '外发代理费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '外发代理费必须是数字';
									}
								}
							}
						        
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.deliveryFee'),// '付送货费',
							name: 'deliveryFee',
							allowBlank:false,
							maxLength:8,
							columnWidth:.33,
							value:0,
							minValue:0,
							readOnly:true,
							listeners: {
					            change: function(field, value) {
						                //代理费用
						                var externalAgencyFee=this.up('form').getValues().externalAgencyFee;	
						                if(externalAgencyFee =='' ||externalAgencyFee == 'undefined' || externalAgencyFee == undefined)
						                	externalAgencyFee=0.000;
						                //其他费用
						                var otherFee=this.up('form').getValues().otherFee;	
						                if(otherFee =='' ||otherFee == 'undefined' || otherFee == undefined)
						                	otherFee=0.000;
						                	
						                var total = parseFloat(otherFee)+parseFloat(externalAgencyFee)+value;
						                this.up('form').getForm().findField('costAmount').setValue(total.toFixed(3));
						            },
						         blur:function(field,eOpts){
						         	//验证费用信息
						            partialline.validateFeesAndWriteOff( this.up('form').getForm());
						         }
						        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '代理送货费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '代理送货费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '代理送货费必须是数字';
									}
								}
							}		
						},{
							xtype: 'numberfield',
							//hideTrigger: true,
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.otherFee'),// '其他费用',
							name: 'otherFee',
							value:0,
//							minValue:0,	//chigo 修改 保证其他费用能为负数
							allowBlank:false,
							maxLength:10,
							columnWidth:.30,
							listeners: {
					            change: function(field, value) {
					            	//外发代理费
					                var externalAgencyFee=this.up('form').getValues().externalAgencyFee;	
					                if(externalAgencyFee =='' ||externalAgencyFee == 'undefined' || externalAgencyFee == undefined)
					                	externalAgencyFee=0.000;
					                //送货费
					                var deliveryFee=this.up('form').getValues().deliveryFee;
						                if(deliveryFee =='' ||deliveryFee == 'undefined' || deliveryFee == undefined )
						                	deliveryFee=0.000;
					                var total=parseFloat(externalAgencyFee)+parseFloat(deliveryFee)+value;
						                	
					                this.up('form').getForm().findField('costAmount').setValue(total.toFixed(3));
					            },
						        blur:function(field,eOpts){
						         	//验证费用信息
						            partialline.uninputedpartial.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        }
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.costAmount'),// '外发成本总额',
							name: 'costAmount',
							allowBlank:false,
							value:0,
							maxLength:18,
							readOnly:true,
							columnWidth:.33
						},{
							xtype: 'numberfield',
							hidden:true,
							hideTrigger: true,
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.receiveAgencyFee'),// '实收代理费',
							name: 'receiveAgencyFee',
							allowBlank:false,
							columnWidth:.33,
							value:0,
							minValue:0,
							maxLength:8,
							listeners: {
					            change: function(field, value) {
					            	var val = parseInt(value);
					            	if(isNaN(val)){
					            		field.setValue(0);
					            	}else{
					            	/*if(val>0){
						            		this.up('form').getForm().findField('payAgencyFee').setValue(0);					            		
						            	}*/
					            	}					                
					            },
					            blur:function(field,eOpts){
					            	/*var val=field.getValue();
					            	if(!Ext.isEmpty(val)&&val>0){
					            		this.up('form').getForm().findField('payAgencyFee').setValue(0);	
					            	}*/
						         	//验证费用信息
						            partialline.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '实收代理费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '实收代理费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '实收代理费必须是数字';
									}
								}
							}				
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.payAgencyFee'),// '实付代理费',
							name: 'payAgencyFee',
							allowBlank:false,
							columnWidth:.33,
							hidden:true,
							value:0,
							minValue:0,
							maxLength:8,
							listeners: {
					            change: function(field, value) {
					            	var val = parseInt(value);
					            	if(isNaN(val)){
					            		field.setValue(0);
					            	}else{
					            		/*if(val>0){
						            		this.up('form').getForm().findField('receiveAgencyFee').setValue(0);				            		
						            	}*/
					            	}					            	
					            },
					            blur:function(field,eOpts){
					            	/*var val=field.getValue();
					            	if(!Ext.isEmpty(val)&&val>0){
					            		this.up('form').getForm().findField('receiveAgencyFee').setValue(0);
					            	}	*/				            	
						         	//验证费用信息
						            partialline.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '实付代理费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '实付代理费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '实付代理费必须是数字';
									}
								}
							}		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.isWriteOff'),// '自动核销申请',
							xtype: 'radiogroup',
					        vertical: true,
							columnWidth:.33,
							labelWidth:120,
							hidden:true,
							allowBlank:false,
							items: [
					            { boxLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.isWriteOff.yes'), name: 'isWriteOff', inputValue: 'Y' },
					            { boxLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.isWriteOff.no'), name: 'isWriteOff', inputValue: 'N', checked: true}
					        ],
					        listeners:{
					        change:function( field, newValue, oldValue, eOpts ){
						         	//验证费用信息
						            partialline.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        }
						},{
							xtype:'textarea',
							height:60,
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.notes'),// '备注',
							name: 'notes',
							allowBlank:true,
							maxLength:1000,
							columnWidth:.99		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal'),// '中转外发',
							xtype: 'radiogroup',
					        vertical: true,
							columnWidth:.33,
							labelWidth:120,
							allowBlank:false,
							items: [
					            { boxLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.yes'), name: 'transferExternal', inputValue: 'Y' },
					            { boxLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.no'), name: 'transferExternal', inputValue: 'N', checked: true}
					        ],//chigo
					        listeners: {
					        	change:function( field, newValue, oldValue, eOpts ){//chigo 当是否中转选择'是'时候，代理送货费和外发代理费变为可编辑状态
					        		if(this.up('form').getForm().findField('transferExternal').getValue()){
					        			this.up('form').getForm().findField('deliveryFee').setReadOnly(false);		
					        			this.up('form').getForm().findField('externalAgencyFee').setReadOnly(false);	
					        		}else{
					        			this.up('form').getForm().findField('deliveryFee').setReadOnly(true);		
					        			this.up('form').getForm().findField('externalAgencyFee').setReadOnly(true);	
					        		}
					        	}
					           
					        }
						},{
							fieldLabel: '币种',// '币种',
							name: 'currencyCode',
							readOnly:true,
							columnWidth:.33
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.paidMethod'),// '付款方式',
							name: 'paidMethod',
							readOnly:true,
							columnWidth:.33
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.toPayAmount'),// '到付金额',
							name: 'toPayAmount',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.origOrgName'),//'外发代理',
							name: 'agentCompanyName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: '外发代理',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.agentDeptName'),// '到达网点',
							name: 'agentDeptName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: '到达网点',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.contactPhone'),// '到达网点电话',
							name: 'contactPhone',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.contactPhone'),//'到达网点电话',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.address'),// '到达网点地址',
							name: 'address',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.address'),
								height: 150,
								width: 800,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalOrgName'),// '外发部门',
							name: 'externalOrgName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: '外发部门',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}		
						},{
							fieldLabel:  partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.registerUser'),// '录入员',
							name: 'registerUser',
							readOnly:true,							
							columnWidth:.33		
						},{
							//xtype: 'datefield',
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.registerTime'),// '录入时间',
							name: 'registerTime',
							readOnly:true,
							columnWidth:.33	
						}]
			},{				
				xtype:'fieldset',
				title: partialline.i18n('Foss.partialline.form.Viewpartialline.title.fieldset2'),//运单基本信息
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.targetOrgCode'),// '目的站',
							name: 'targetOrgCode',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.targetOrgCode'),//'目的站',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}	
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.handGoodsTime'),// '收货日期',
							name: 'handGoodsTime',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.createOrgCode'),// '收货部门',
							name: 'createOrgCode',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: '收货部门',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}			
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsWeightTotal'),// '重量',
							name: 'goodsWeightTotal',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsVolumeTotal'),// '体积',
							name: 'goodsVolumeTotal',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsQtyTotal'),// '件数',
							name: 'goodsQtyTotal',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transportFee'),// '运费',
							name: 'transportFee',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsName'),// '货物名称',
							name: 'goodsName',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.insuranceFee'),// '保险费',
							name: 'insuranceFee',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsPackage'),// '包装',
							name: 'goodsPackage',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsPackage'),//'包装',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.deliveryCustomerName'),// '托运人姓名',
							name: 'deliveryCustomerName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title:  partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.deliveryCustomerName'),//'托运人姓名',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}					
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.insuranceAmount'),// '保险价值',
							name: 'insuranceAmount',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.yunshushixiang'),// '运输事项',
							name: 'yunshushixiang',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.yunshushixiang'),//'运输事项',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}							
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.totalFee'),// '开单金额',
							name: 'totalFee',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.codAmount'),// '代收货款',
							name: 'codAmount',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.receiveCustomerName'),// '收货客户',
							name: 'receiveCustomerName',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.receiveCustomerName'),//'收货客户',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.partnerContactPhone'),// '代理电话',
							name: 'partnerContactPhone',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.partnerContactPhone'),//'外发代理电话',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}
						},{
							xtype:'hiddenfield',
							fieldLabel: '',// '',
							name: 'paidMethodCode',
							readOnly:true,
							columnWidth:.33		
						},{
							xtype:'hiddenfield',
							fieldLabel: '',// '',
							name: 'modifyDate',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.receiveCustomerContact'),// '收货联系人',
							name: 'receiveCustomerContact',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: partialline.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.receiveCustomerContact'),//'收货联系人',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.partialline.form.tip'
							}		
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
			xtype : 'button',
			id:'Foss_partialline_form_viewpartialline_button_reset_Id',
			columnWidth:.08,
			text: partialline.i18n('Foss.partialline.form.Viewpartialline.button.reset'),// '重置',
			handler: function() {
				var form = this.up('form').getForm();
				form.findField('waybillNo').reset();
				form.findField('externalBillNo').reset();
				form.findField('externalUserCode').reset();
				//form.findField('externalAgencyFee').reset();
				//form.findField('deliveryFee').reset();
				form.findField('costAmount').reset();
				form.findField('receiveAgencyFee').reset();
				form.findField('payAgencyFee').reset();
				form.findField('isWriteOff').reset();
				form.findField('notes').reset();
				form.findField('transferExternal').reset();
				form.findField('otherFee').reset();
			}
		},{
			border : false,
			columnWidth:.84,
			id:'Foss_partialline_form_viewpartialline_button_holder_Id',
			html: '&nbsp;'
		},{
			columnWidth:.08,
			id:'Foss_partialline_form_viewpartialline_button_save_Id',
			xtype : 'button',
			text:  partialline.i18n('Foss.partialline.form.Viewpartialline.button.save'),//'保存',			
			handler: function() {
				var formEl=this.up('form');
				var form = formEl.getForm();
				
				if (form.isValid()) { 
	        		var new_record = form.getRecord();
	        		var values=form.getValues();
	        		form.updateRecord(new_record);
	        		//验证外发成本总额不能为0或负数
	        		//外发成本总额
	        		var costAmount=Ext.Number.from(new_record.data.costAmount,0);
	        		if(costAmount<=0){
	        			Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),
	        			partialline.i18n('foss.partialline.messageBox.alert.tip.error.costAmountLtZero'));
	        			return ;
	        		}
	        		//外发成本总额比开单金额大时出现提示信息，当外发成本总额比开单金额大1倍时不能录入，默认为1倍，可配置
	        		/*var num=2;//2可配置
	        		if(costAmount>values.totalFee*num){
	        			Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),"外发成本总额超过开单金额的2倍");
	        			return ;
	        		}*/
	        		//业务规则SR-10校验	        		
	        		//到付方式
	        		/*var paidMethodCode=values.paidMethodCode;
	        		//到付运费(到付金额)
	        		var toPayAmount=values.toPayAmount;
	        		//实付代理金额(实付代理费)
	        		var payAgencyFee=new_record.data.payAgencyFee;        		
	        		//当运单付款方式为到付时(FC), 外发成本总额 - 到付运费=X,如果X>0,录入实付代理金额>X时，则不允许录入;
	        		//取消原有SR-10
	        		if(paidMethodCode == 'FC'){
	        			var x=costAmount-toPayAmount;
	        			if(x>0 && payAgencyFee>x){
	        				Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'给予代理的费用>外发成本总额，产生亏本，请核实后再录入');
	        				return ;
	        			}
	        			//如果X<0,录入实收代理金额<|X|（绝对值）时，则不允许录入，以防止多付给代理钱的情况；
	        			if(x<0){
	        				//实收代理费（实收代理金额）
	        				var receiveAgencyFee=new_record.data.receiveAgencyFee;    
	        				var xval=Math.abs(x);
	        				if(receiveAgencyFee<xval){
	        					Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'录入实收代理费<到付费用减去外发成本总额，产生亏本，请核实后再录入');
	        					return ;
	        				}	        				
	        			}
	        		}else 
	        		//当运单付款方式为现金时，录入实付代理金额>外发成本总额时，实付代理金额则不允许录入；
	        		if(paidMethodCode == 'CH'){//CH 现金
	        			if(parseFloat(payAgencyFee)>parseFloat(costAmount)){
	        				Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'录入实付代理金额>外发成本总额，产生亏本，请核实后再录入');
	        				return ;
	        			}
	        		}*/
	        		//验证费用信息
	        		if(!partialline.validateFeesAndWriteOff(form)){
	        			return;
	        		}
	        		//验证通过，执行录入操作	   
	        		//清空不需要保存的数据
	        		new_record.data.registerTime=null;
	        		new_record.data.registerUser=null;
	        		new_record.data.externalOrgName=null;
	        		var array = {vo:{dto:new_record.data}};
	        		//遮罩
	        		formEl.getEl().mask("正在保存，请稍等...");
	        		Ext.Ajax.request({
	        			url : partialline.realPath('addExternalBill.action'),
	        			jsonData:array,
	        			success : function(response) {
	        				formEl.getEl().unmask();
	        				//信息提示
							Ext.ux.Toast.msg('提示信息', '保存成功!');
	        			},
	        			exception : function(response) {
	        				formEl.getEl().unmask();
	        				var json = Ext.decode(response.responseText);
	        				Ext.MessageBox.alert(partialline.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
	        			}
	        		});
				}else{
					Ext.MessageBox.alert("验证未通过",'请检查表单错误，请见表单对应错误提示');
				}
			}
		},{
			columnWidth:.08,
			xtype : 'button',
			id:'Foss_partialline_form_viewpartialline_button_update_Id',
			text:  partialline.i18n('Foss.partialline.form.Viewpartialline.button.update'),//'更新',			
			handler: function() {
				var formEl=this.up('form');
				var form = formEl.getForm();
				if (form.isValid()) { 
	        		var new_record = form.getRecord();
	        		var values=form.getValues();
	        		form.updateRecord(new_record);
	        		//验证外发成本总额不能为0或负数
	        		//外发成本总额
	        		var costAmount=Ext.Number.from(new_record.data.costAmount,0);
	        		if(costAmount<=0){
	        			Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),
	        			partialline.i18n('foss.partialline.messageBox.alert.tip.error.costAmountLtZero'));
	        			return ;
	        		}
	        		//外发成本总额比开单金额大时出现提示信息，当外发成本总额比开单金额大1倍时不能录入，默认为1倍，可配置
	        		/*var num=2;//2可配置
	        		if(costAmount>values.totalFee*num){
	        			Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),"外发成本总额超过开单金额的2倍");
	        			return ;
	        		}*/
	        		//业务规则SR-10校验	        		
	        		//到付方式
	        		/*var paidMethodCode=values.paidMethodCode;
	        		//到付运费(到付金额)
	        		var toPayAmount=values.toPayAmount;
	        		//实付代理金额(实付代理费)
	        		var payAgencyFee=new_record.data.payAgencyFee;        		
	        		//当运单付款方式为到付时(FC), 外发成本总额 - 到付运费=X,如果X>0,录入实付代理金额>X时，则不允许录入;	        		
	        		if(paidMethodCode == 'FC'){
	        			var x=costAmount-toPayAmount;
	        			if(x>0 && payAgencyFee>x){
	        				Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'给予代理的费用大于外发成本总额，产生亏本，请核实后再录入');
	        				return ;
	        			}
	        			//如果X<0,录入实收代理金额<|X|（绝对值）时，则不允许录入，以防止多付给代理钱的情况；
	        			if(x<0){
	        				//实收代理费（实收代理金额）
	        				var receiveAgencyFee=new_record.data.receiveAgencyFee;    
	        				var xval=Math.abs(x);
	        				if(receiveAgencyFee<xval){
	        					Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'录入实收代理费小于到付费用减去外发成本总额，产生亏本，请核实后再录入');
	        					return ;
	        				}	        				
	        			}
	        		}
	        		else
	        		//当运单付款方式为现金时，录入实付代理金额>外发成本总额时，实付代理金额则不允许录入；
	        		if(paidMethodCode == 'CH'){//CH现金
	        			if(parseFloat(payAgencyFee)>parseFloat(costAmount)){
	        				Ext.MessageBox.alert(partialline.i18n('foss.partialline.messageBox.alert.tip.title'),'录入实付代理金额>外发成本总额，产生亏本，请核实后再录入');
	        				return ;
	        			}
	        		}*/
	        		//验证费用信息
	        		if(!partialline.validateFeesAndWriteOff(form)){
	        			return;
	        		}
	        		new_record.data.registerTime=null;
	        		//验证通过，执行更新操作
	        		var array = {vo:{dto:new_record.data}};
	        		if(this.up('form').getForm().findField('externalBillNo').getValue().length>5){
	        			formEl.getEl().mask("正在保存，请稍等...");
	        		}else{
	        			 Ext.Msg.alert('提示信息', '更新失败，外发单号总长度要大于5 个数字!');
	        			 return ;
	        		}
	        		Ext.Ajax.request({
	        			url : partialline.realPath('updateExternalBill.action'),
	        			jsonData:array,
	        			success : function(response) {
	        				formEl.getEl().unmask();
	        				//信息提示
							Ext.ux.Toast.msg('提示信息', '保存成功!');
	        			},
	        			exception : function(response) {
	        				formEl.getEl().unmask();
	        				var json = Ext.decode(response.responseText);
	        				Ext.MessageBox.alert(partialline.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
	        			}
	        		});
				}else{
					Ext.MessageBox.alert("验证未通过",'请检查表单错误，请见表单对应错误提示');
				}
			}
		},{
			columnWidth:.08,
			xtype : 'button',
			id:'Foss_partialline_form_viewpartialline_button_edit_Id',
			text:  '编辑',//'编辑',			
			hidden:true,
			handler: function() {
				var form = this.up('form').getForm();
				//隐藏外发部门、录入员、 录入时间
				form.findField('externalOrgName').hide();
				form.findField('registerUser').hide();
				form.findField('registerTime').hide();
				//更新、编辑按钮控制
				Ext.getCmp('Foss_partialline_form_viewpartialline_button_update_Id').show();
				Ext.getCmp('Foss_partialline_form_viewpartialline_button_edit_Id').hide();
				//将所有的字段设置为非只读属性
				 Ext.suspendLayouts();
				 for(var i=1 ;i<5 ;i++){
				 	form.getFields( ).get(i).setReadOnly(false);
				 }	
				 for(var i=6 ;i<14 ;i++){
				 	form.getFields( ).get(i).setReadOnly(false);
				 }		
				//SR-12	根据运单号查开单信息，判断是否自提，如果是则不能输入代理送货费
				var beAutoDelivery = form.getRecord().beAutoDelivery;
				/*if(beAutoDelivery=='SELF_PICKUP'){
					form.findField('deliveryFee').setValue(0);
					form.findField('deliveryFee').setReadOnly(true);					
				}					
				else
					form.findField('deliveryFee').setReadOnly(true);*/

				
				Ext.resumeLayouts(true);
				//验证费用
				partialline.validateFeesAndWriteOff(form);
				
				//需要重新计算外发费用及外发送货费
				var selectedVal=form.findField('waybillNo').getValue();
				params = {vo:{dto:{waybillNo:selectedVal}}};			
				var json;
				Ext.Ajax.request({
        			url : partialline.realPath('calculateDelAndEtdFee.action'),
        			jsonData:params,
        			success : function(response) {
        				var json = Ext.decode(response.responseText);
        				var billInfo=json.vo.billInfo;
        				//设置数据
        				
					    //给外发代理费用和送货费赋值
        				//
        				//form.findField('externalAgencyFee').setValue(billInfo.externalAgencyFee);	
        				//chigo注释，代理送货费从数据库中获得，不需要重新设置
        				//form.findField('deliveryFee').setValue(billInfo.deliveryFee);
        				
        				//SR-12	根据运单号查开单信息，判断是否自提，如果是则不能输入代理送货费
        				
        			},
        			exception : function(response) {
        				form.findField('deliveryFee').setValue(0);
        				//重新计算外发成本总额
		        		partialline.calculateCostAmount(form);
		        		
		        		form.findField('deliveryFee').setReadOnly(true);	
						form.findField('externalAgencyFee').setReadOnly(true);
						form.findField('otherFee').setReadOnly(false);	
						form.findField('costAmount').setReadOnly(true);
				
        				Ext.MessageBox.alert(partialline.i18n('Foss.partialline.form.Viewpartialline.error'),Ext.decode(response.responseText).message);
        			}
        		});
        		//重新计算外发成本总额
        		//外发代理费
                partialline.calculateCostAmount(form);
                
        		form.findField('deliveryFee').setReadOnly(true);	
				form.findField('externalAgencyFee').setReadOnly(true);
				form.findField('otherFee').setReadOnly(false);	
				form.findField('costAmount').setReadOnly(true);
			
				//如果之前选择的是中转外发，则显示可编辑
				if(form.findField('transferExternal').getValue()){
					form.findField('deliveryFee').setReadOnly(false);		//chigo true 变成 false
					form.findField('externalAgencyFee').setReadOnly(false);	//chigo true 变成 false
				}
				
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

//重新计算外发总额
 partialline.calculateCostAmount=function(form){
 
 	           var externalAgencyFee=form.findField('externalAgencyFee').getValue();	
                if(externalAgencyFee =='' ||externalAgencyFee == 'undefined' || externalAgencyFee == undefined)
                	externalAgencyFee=0.000;
                //送货费
                var deliveryFee=form.findField('deliveryFee').getValue();
	                if(deliveryFee =='' ||deliveryFee == 'undefined' || deliveryFee == undefined )
	                	deliveryFee=0.000;
	                	
	             //其他费用
	            var otherFee=form.findField('otherFee').getValue();
	                if(otherFee =='' ||otherFee == 'undefined' || otherFee == undefined )
	                	otherFee=0.000;
	                	
                var total=parseFloat(externalAgencyFee)+parseFloat(deliveryFee)+otherFee;
	                	
                form.findField('costAmount').setValue(total.toFixed(3));
 }


//验证费用
partialline.validateFeesAndWriteOff=function(form){
	
	var saveBtn = Ext.getCmp('Foss_partialline_form_viewpartialline_button_update_Id');
	//如果是查看,保存按钮时隐藏的，则说明是查看，则不显示先编辑
	if(!Ext.isEmpty(partialline.isWritePartialline)&&!Ext.isEmpty(saveBtn)&&!partialline.isWritePartialline&&saveBtn.isHidden()){
	}else{
		//可填写
		form.findField('payAgencyFee').setReadOnly(false);
		//可填写
		form.findField('receiveAgencyFee').setReadOnly(false);		
	}
	var flag=true;
	//验证费用
	if(!Ext.isEmpty(form)){
		var values=form.getValues();
		//到付方式
		var paidMethodCode=values.paidMethodCode;
		//到付运费(到付金额)
		var toPayAmount=Ext.Number.from(values.toPayAmount,0);
		//外发成本总额
		var costAmount=Ext.Number.from(form.findField('costAmount').getValue(), 0) ;
		//实付代理金额(实付代理费)
		var payAgencyFee=Ext.Number.from(form.findField('payAgencyFee').getValue(), 0);  
		//实收代理金额(实收代理费)
		var receiveAgencyFee=Ext.Number.from(form.findField('receiveAgencyFee').getValue(), 0);  
		//自动核销
		var isWriteOff=values.isWriteOff;
		//1.当运单到付金额＞0时，运单到付金额-外发成本总额＞0时，是否自动核销为否时，
		//0≤实收代理金额≤运单到付金额，0≤实付代理金额≤外发成本总额；
		//是否自动核销为是时，0≤实收代理金额≤（运单到付金额-外发成本总额），
		//实付代理金额为灰色，不可编辑，只能为0
		if(toPayAmount>0){
			//运单到付金额-外发成本总额
			var x=toPayAmount-costAmount;
			//运单到付金额-外发成本总额＞0时
			if(x>0){
				//非自动核销
				if(isWriteOff=='N'){					
					//验证实付代理费
					if(payAgencyFee>=0&&payAgencyFee<=costAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额>外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]");
						flag=false;
					}
					//验证实收代理费
					if(receiveAgencyFee>=0&&receiveAgencyFee<=toPayAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额>外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]");
						flag=false;
					}
					
				}
				//自动核销
				else{
					//实付代理金额为灰色，不可编辑，只能为0
					form.findField('payAgencyFee').setValue(0);
					form.findField('payAgencyFee').setReadOnly(true);
					//是否自动核销为是时，0≤实收代理金额≤（运单到付金额-外发成本总额）
					if(receiveAgencyFee>=0&&receiveAgencyFee<=x){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额>外发成本总额,且自动核销时，需要[0≤实收代理费≤（到付金额-外发成本总额）]");
						flag=false;
					}
				}
			}
			//2.当运单到付金额＞0时，运单到付金额-外发成本总额≤0时（此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置），
			//是否自动核销为否时，0≤实收代理金额≤运单到付金额，0≤实付代理金额≤外发成本总额；是否自动核销为是时，实收代理金额为灰色，
			//不可编辑，只能为0，0≤实付代理金额≤（外发成本总额-运单到付金额）
			//运单到付金额-外发成本总额≤0时（此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置）
			else{
				//非自动核销
				if(isWriteOff=='N'){					
					//验证实付代理费,此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置
					if(payAgencyFee>=0&&payAgencyFee<=costAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额<=外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]");
						flag=false;
					}
					//验证实收代理费
					if(receiveAgencyFee>=0&&receiveAgencyFee<=toPayAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额<=外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]");
						flag=false;
					}
					
				}
				//自动核销
				else{
					//是否自动核销为是时，实收代理金额为灰色，不可编辑，只能为0
					form.findField('receiveAgencyFee').setValue(0);
					form.findField('receiveAgencyFee').setReadOnly(true);
					//是否自动核销为是时，0≤实付代理金额≤（外发成本总额-运单到付金额）
					var y=costAmount-toPayAmount;
					if(payAgencyFee>=0&&payAgencyFee<=y){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额<=外发成本总额,且自动核销时，需要[0≤实付代理金额≤（外发成本费-运单到付金额）]");
						flag=false;
					}
				}
			}
		}
		//当运单到付金额=0时（此时，发货人会和我司进行运费结算，外发部门无需委托代理收款，我司只需给代理支付成本），
		//实收代理金额为灰色，不可编辑，只能为0，0≤实付代理金额≤外发成本总额。 
		else if(toPayAmount==0){
			//实收代理金额为灰色，不可编辑，只能为0
			form.findField('receiveAgencyFee').setValue(0);
			form.findField('receiveAgencyFee').setReadOnly(true);
			//0≤实付代理金额≤外发成本总额。 
			if(payAgencyFee>=0&&payAgencyFee<=costAmount){
				//验证通过
			}else{
				Ext.MessageBox.alert('提示',"当运单到付金额=0时，需要[0≤实付代理费≤外发成本总额]");
				flag=false;
			}
		}
		
	}
	return flag; 
}

//外发录入窗口
Ext.define('Foss.partialline.window.Viewpartialline',{
	extend:'Ext.window.Window',
	id:'Foss_partialline_window_viewpartialline_Id',
	title: partialline.i18n('Foss.partialline.window.Viewpartialline.view.title'),//'查看偏线外发单',
	modal:true,
	closeAction:'hide',
	width: 800,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		Ext.getCmp('Foss_partialline_form_viewpartialline_ID')==null?Ext.create('Foss.partialline.form.Viewpartialline'):Ext.getCmp('Foss_partialline_form_viewpartialline_ID')
	],
	listeners: {
      hide: function( component, eOpts ){                     
          partialline.pagingBar.moveFirst();
      }
   }
	
});

//查询外发部门
partialline.queryExternalOrgName=function(form,externalUserCode){
	var params = {"vo.dto.externalUserCode":externalUserCode};
	var actionUrl =partialline.realPath('queryExternalOrgName.action');
	//执行相应操作
	Ext.Ajax.request({
		url : actionUrl,
		params:params,
		success : function(response) {
			var json = Ext.decode(response.responseText);
	        	var dto=json.vo.dto;
	        	form.getForm().findField('externalOrgName').setValue(dto.externalOrgName)
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
		}
	});
}

//显示于标签页
Ext.onReady(function() {
	partialline.WAITINGAUDIT="WAITINGAUDIT";//待审核
	partialline.HASAUDITED="HASAUDITED";//已经审核
	partialline.BACKAUDIT="BACKAUDIT";//反审核
	partialline.INVALID="INVALID";	//已作废
	
		//偏线查询表单
		//partialline.userRole='FINANCIAL_USER';//FINANCIAL_USER表示财务  REGISTER表示偏线外发员
		var searchfrom=Ext.create('Foss.partialline.form.PartiallineSearch');
		var searchResult=Ext.create('Foss.partialline.grid.PartiallineGrid');
		//全局可获取查询窗口和查询结果表格
		partialline.queryForm=searchfrom;
		partialline.searchResultGrid=searchResult;
		
		//显示偏线界面
		Ext.create('Ext.panel.Panel',{
		id:'T_partialline-index_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [searchfrom,searchResult],
		renderTo: 'T_partialline-index-body'
	});	
		searchResult.getStore().load();
});