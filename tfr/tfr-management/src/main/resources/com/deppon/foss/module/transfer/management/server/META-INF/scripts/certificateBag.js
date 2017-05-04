//按领取查询
management.certificateBag.queryRepayment_TD='TD';
//按归还查询
management.certificateBag.queryRepayment_RT='RT';

//获取7天之前的日期
var oldDay = new function(){
	 	var date = new Date();
	 	date.setDate(date.getDate()-7);
	 	return date;
}

//证件包状态
Ext.define('Foss.management.CertificatebagStatusStore',{
	extend:'Ext.data.Store',
	fields:[
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data:{
		'items':[
			{code:'N',name:management.certificateBag.i18n('foss.management.certificateBag.label.nothandOverStatus')},
			{code:'Y',name:management.certificateBag.i18n('foss.management.certificateBag.label.alreadyhandOverStatus')}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type: 'json',
			root:'items'
		}
	}
});

Ext.define('Foss.management.certificateBag.ConfigItemsStore',{
	extend:'Ext.data.Store',
	fields: [
	         {name: 'confCode',  type: 'confCode'},
	         {name: 'confName',  type: 'confName'}
	         ],
	         
	         proxy: {
	        	 type: 'memory',
	        	 reader: {
	        		 type: 'json',
	        		 root: 'configOrgRelationEntityList'
	        	 }
	         },
	         constructor: function(config){
	        	 var me = this,
	        	 cfg = Ext.apply({}, config);
	        	 me.callParent([cfg]);
	         }
});

//将英文车牌号转换为中文车牌号 
management.convertVehicleNoForQuery=function(me,vehicleNo){
	Ext.Ajax.request({
		url : management.realPath('convertVehicleCode2NameForQuery.action'),
		params : {'vo.vehicleNo':vehicleNo},
		async: false,
		success : function(response) {
				var result=Ext.decode(response.responseText);
				me.up('form').getForm().findField('vehicleNo').setValue(result.vo.vehicleNo);//返回转换后的车牌号
				
		},
		exception:function(response){
			me.up('form').getForm().findField('vehicleNo').setValue(null); //异常，则为空
			var result=Ext.decode(response.responseText);
			Ext.Msg.alert(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),result.message);
		}
	});
}

//证件包查询界面
Ext.define('Foss.management.queryTakeCertificateBagForm',{
	extend : 'Ext.form.Panel',	
	layout : 'column',
	//height:150,
	//frame : true,
	border : false,	
	defaults : {
		margin : '5 5 5 5',
		columns : 2
	},
	items : [{
		xtype:'dynamicorgcombselector',
		type : 'ORG',
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.orgName'),//车辆归属部门
		transDepartment:'Y',
		//add by liangfuxiang 加入车队组,调度组 2013-03-27
		transTeam:'Y',
		dispatchTeam:'Y',
		name: 'orgId',
		labelWidth:100,
		columnWidth:.28
	},{
		xtype: 'commonowntruckselector',
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.vehicleNo'),//车牌号
		labelWidth:80,
		name: 'vehicleNo',
		columnWidth:.28
	},{
		xtype: 'rangeDateField',
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.beginActualTakeTime'),//领取时间
		labelWidth:70,
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_management_certificateBag_beginTakeTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		//dateType: 'timefield',
		dateRange : 30,
		fromName: 'beginActualTakeTime',
		toName: 'endActualTakeTime',
		disallowBlank:false,
		format:'Y-m-d H:i:s',
		fromValue:Ext.Date.format(oldDay,'Y-m-d')+ ' '+'00:00:00',
		toValue:Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59',		
		columnWidth: .42
	},
	FossDataDictionary.getDataDictionaryCombo('CERTIFICATEBAG_STATUS',{
		name: 'certificatebagStatus',
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.certificatebagStatus'),//证件包状态
		labelWidth:100,
		queryMode: 'local',
		forceSelection: true,
		editable: true,
		value: '',
		columnWidth: .28
		},{
			'valueCode':'',
			'valueName': management.certificateBag.i18n('foss.management.all')
	}),{
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.containerNo'),//货柜号
		labelWidth:80,
		xtype : 'commonowntruckselector',							
		displayField : 'containerCode',// 显示名称
		valueField : 'containerCode',
		myQueryParam : 'containerCode',
		showContent : '{containerCode}',  
		name: 'containerNo',
		vehicleTypes : 'vehicletype_trailer',
		columnWidth:.28	
	},{
		xtype: 'radiogroup',
        fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.businessType'),//业务类型
        labelWidth:70,
        vertical: true,       
		columnWidth:.30,		
        items: [
            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.longtype'), name: 'businessType', margin : '5 5 5 5',inputValue: 'LONG_TYPE'},
            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.shorttype'), name: 'businessType', margin : '5 5 5 5',inputValue: 'SHORT_TYPE'}					           
        ]
	},{
		xtype:'checkboxgroup',
		fieldLabel:'',
		items:[{
			boxLabel  : management.certificateBag.i18n('foss.management.certificateBag.label.exceptionCertificateBag'),//异常证件包
			width:80,
			margin : '5 5 5 5',
			name      : 'handOverStatus',  //表单的参数名
			inputValue: 'N'  //表单的参数值
		}]
	},{
		xtype:'dynamicorgcombselector',		
		type : 'ORG',		
		transDepartment:'Y',
		transferCenter:'Y',		
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualTakeOrgCode'),//领用部门
		name: 'actualTakeOrgCode',
		labelWidth:100,
		columnWidth:.28
	},{
		xtype: 'commonowndriverselector',
		labelWidth:80,
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualTakeUserName'),//领取人		
		name: 'actualTakeUserName',
		columnWidth:.28,
		listeners:{//modify by liangfuxiang 2013-04-09 满足扫描枪回车
			specialKey:function(textfield, e){ //
				if(e.getKey() == Ext.EventObject.ENTER){
					var actualTakeUserName=this.up('form').getForm().findField('actualTakeUserName').getValue();
					this.up('form').getForm().findField('actualTakeUserName').setValue(actualTakeUserName);
				}
			}
		}
	},{
		xtype : 'container',
		columnWidth : .1,
		html : '&nbsp;'
	},{				
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items : [{
			text : management.certificateBag.i18n('foss.management.button.reset'),//重置
			columnWidth : .1,
			handler : function() {
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginActualTakeTime')
				 .setValue(Ext.Date.format(oldDay,'Y-m-d')+ ' '+'00:00:00');
				this.up('form').getForm().findField('endActualTakeTime')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
				
			}
		},{
			xtype: 'container',
			columnWidth:.750,
			html: '&nbsp;'
		},{
			text : management.certificateBag.i18n('foss.management.button.search'),//查询
			disabled: !management.certificateBag.isPermission('management/queryCertificateBagButton'),
			hidden: !management.certificateBag.isPermission('management/queryCertificateBagButton'),
			columnWidth : .1,
			cls:'yellow_button',
			handler : function() {
				var form = this.up('form').getForm();
		    	if(form.isValid()){
		    		management.certificateBag.queryByRepayment(form,management.certificateBag.queryRepayment_TD);				
		    	}
			}
		}]
	}	
	],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		management.queryTakeCertificateBagForm = me;  //加入全局变量中
		me.callParent([cfg]);
		comkey=me.getForm().findField('vehicleNo');
		comkey.removeListener('keypress');
		comkey.on('keypress',function(textfield, e){
			if(e.getKey() == Ext.EventObject.ENTER){
				var oldVehicleNo=this.up('form').getForm().findField('vehicleNo').getValue();
				if(oldVehicleNo!=null && oldVehicleNo!=''){
					if(oldVehicleNo.indexOf("-")!=-1){//包含两个'-'
						management.convertVehicleNoForQuery(this,oldVehicleNo);
						if(comkey.getValue()){
						    comkey.getKeyPress(comkey, e);   
						}
					}	
				}
			}
		});
	}
});


//证件包查询界面
Ext.define('Foss.management.queryReturnCertificateBagForm',{
	extend : 'Ext.form.Panel',	
	layout : 'column',
//	height:150,	
	border : false,	
	defaults : {
		margin : '5 5 5 5',
		columns : 2
	},
	items : [{
		xtype:'dynamicorgcombselector',
		type : 'ORG',
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.orgName'),//车辆归属部门
		transDepartment:'Y',
		name: 'orgId',
		//modify by liangfuxiang BUG-12604 2013-05-23 begin
		transTeam:'Y',
		dispatchTeam:'Y',
		//modify by liangfuxiang BUG-12604 2013-05-23 end
		labelWidth:100,
		columnWidth:.28
	},{
		xtype: 'commonowntruckselector',
		fieldLabel:  management.certificateBag.i18n('foss.management.certificateBag.label.vehicleNo'),//车牌号	
		labelWidth:80,
		name: 'vehicleNo',
		columnWidth:.28
	},{
		xtype: 'rangeDateField',
		fieldLabel:management.certificateBag.i18n('foss.management.certificateBag.label.actualReturnTime'),//归还时间
		labelWidth:70,
		dateRange : 7,
		//类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标	    //识的String值
		fieldId: 'Foss_management_beginReturnTime_ID',
		dateType: 'datetimefield_date97',		
		fromName: 'beginActualReturnTime',
		toName: 'endActualReturnTime',
		disallowBlank:false,
		dateRange : 30,
		format:'Y-m-d H:i:s',
		fromValue:Ext.Date.format(oldDay,'Y-m-d')+ ' '+'00:00:00',
		toValue:Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59',		
		columnWidth: .42
	},
	FossDataDictionary.getDataDictionaryCombo('CERTIFICATEBAG_STATUS',{
		name: 'certificatebagStatus',
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.certificatebagStatus'),//证件包状态
		labelWidth:100,
		queryMode: 'local',
		forceSelection: true,
		editable: true,
		value: '',
		columnWidth: .28
		},{
			'valueCode':'',
			'valueName': management.certificateBag.i18n('foss.management.all')
	}),{
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.containerNo'),//货柜号
		labelWidth:80,
		xtype : 'commonowntruckselector',							
		displayField : 'containerCode',// 显示名称
		valueField : 'containerCode',
		myQueryParam : 'containerCode',
		showContent : '{containerCode}',  
		vehicleTypes : 'vehicletype_trailer',
		name: 'containerNo',
		columnWidth:.28	
	},{
		xtype: 'radiogroup',
        fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.businessType'),//业务类型 
        labelWidth:70,
        vertical: true,       
		columnWidth:.30,		
        items: [
            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.longtype'), name: 'businessType',margin : '5 5 5 5', inputValue: 'LONG_TYPE'},
            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.shorttype'), name: 'businessType', margin : '5 5 5 5',inputValue: 'SHORT_TYPE'}					           
        ]
	},{
		xtype:'checkboxgroup',
		fieldLabel:'',
		items:[{
			boxLabel  : management.certificateBag.i18n('foss.management.certificateBag.label.exceptionCertificateBag'),//异常证件包 
			width:80,
			margin : '5 5 5 5',
			name      : 'handOverStatus',  //表单的参数名
			inputValue: 'N'  //表单的参数值
		}]
	},{
		xtype:'dynamicorgcombselector',
		type : 'ORG',
		labelWidth:100,
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualReturnOrgCode'),//归还部门
		transDepartment:'Y',
		transferCenter:'Y',		
		name: 'actualReturnOrgCode',		
		columnWidth:.28
	},{
		xtype: 'commonowndriverselector',
		fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualReturnUserName'),//归还人
		labelWidth:80,
		name: 'actualReturnUserName',		
		columnWidth:.28,
		listeners:{//modify by liangfuxiang 2013-04-09 满足扫描枪回车
			specialKey:function(textfield, e){ //
				if(e.getKey() == Ext.EventObject.ENTER){
					var actualTakeUserName=this.up('form').getForm().findField('actualReturnUserName').getValue();
					this.up('form').getForm().findField('actualReturnUserName').setValue(actualTakeUserName);
				}
			}
		}
	},{
		xtype : 'container',
		columnWidth : .1,
		html : '&nbsp;'
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items : [{  
			text : management.certificateBag.i18n('foss.management.button.reset'),//重置
			columnWidth : .1,
			handler : function() {
				this.up('form').getForm().reset();				
				this.up('form').getForm().findField('beginActualReturnTime')
				.setValue(Ext.Date.format(oldDay,'Y-m-d')+ ' '+'00:00:00');
				this.up('form').getForm().findField('endActualReturnTime')
				.setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'));
				
			}
		},{
			xtype: 'container',
			columnWidth:.750,
			html: '&nbsp;'
		},{
			text : management.certificateBag.i18n('foss.management.button.search'),//查询
			columnWidth : .1,
			cls:'yellow_button',
			handler : function() {
				var form = this.up('form').getForm();
				var queryReturnValues=form.getValues();
		    	if(form.isValid()){		    		
		    		management.certificateBag.queryByRepayment(form,management.certificateBag.queryRepayment_RT);	
		    	}
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		management.queryReturnCertificateBagForm = me;  //加入全局变量中
		me.callParent([cfg]);
		comkey=me.getForm().findField('vehicleNo');
		comkey.removeListener('keypress');
		comkey.on('keypress',function(textfield, e){
			if(e.getKey() == Ext.EventObject.ENTER){
				var oldVehicleNo=this.up('form').getForm().findField('vehicleNo').getValue();
				if(oldVehicleNo!=null && oldVehicleNo!=''){
					if(oldVehicleNo.indexOf("-")!=-1){//包含两个'-'
						management.convertVehicleNoForQuery(this,oldVehicleNo);
						if(comkey.getValue()){
						    comkey.getKeyPress(comkey, e);   
						}
					}	
				}
			}
		});
		
	}
});

Ext.define('Foss.management.queryCertificateBagForm',{
	extend:'Ext.tab.Panel',
	cls:"innerTabPanel",
	bodyCls:"overrideChildLeft",
	activeTab:0,
	autoScroll:false,
	frame: false,	
	constructor: function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				title:  management.certificateBag.i18n('Foss.management.certificateBag.queryTakeCertificateBagForm.title'),//按领取查询 
				tabConfig:{width:100},
				items: Ext.create('Foss.management.queryTakeCertificateBagForm')
			 },{
				title:  management.certificateBag.i18n('Foss.management.certificateBag.queryReturnCertificateBagForm.title'),//按归还查询 
				tabConfig:{width:100},
				name:'queryByReturn',
				items: Ext.create('Foss.management.queryReturnCertificateBagForm')
			}
		];
		me.callParent([cfg]);
	}
});

//证件包  Model  
Ext.define('Foss.management.certificateBagModel',{
	extend: 'Ext.data.Model',
	fields: [      
		{name: 'vehicleNo' , type: 'string'},//车牌号/货柜号
		{name: 'orgId' ,type:'string'},//所属部门		
		{name: 'businessType', type: 'string'},//	业务类型	
		{name: 'actualReturnOrgCode', type: 'string'},//证件包所在处
		{name: 'handOverStatus',type: 'string'},//证件包交接情况
		{name: 'certificatebagStatus', type: 'string'},	//证件包状态		
		{name: 'actualTakeTime', type: 'date',
			convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}},	//证件包领取时间
		{name: 'actualTakeUserName', type: 'string'},	//领取人		
		{name: 'actualTakeOperator', type: 'string'},	//领取操作人		
		{name: 'actualReturnTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);						
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
		}},	//证件包归还时间		
		{name: 'actualReturnUserName', type: 'string'},	//归还人
		{name: 'actualReturnOperator', type: 'string'},	//归还操作人	
		{name: 'refId', type: 'string'}	//关联id
	]
});


//证件包查询
management.certificateBag.queryByRepayment=function(me,queryType){
	
		var ds=management.certificateBag.certificateBagGrid.store;
		var params=management.certificateBag.queryByRepaymentSetParam(me,queryType);
		
		if(null==params || params==false){
			return;
		}
		//设置查询参数
		ds.setSubmitParams(params);
		//查询后台
		ds.loadPage(1,{
		callback: function(records, operation, success) {
			var rawData = ds.proxy.reader.rawData;
			//当success:false ,isException:false  --业务异常
			if(!success && ! rawData.isException){
				Ext.Msg.alert(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),rawData.message);
				return false;
			}
			if(success){
				var result = Ext.decode(operation.response.responseText); 
				var billRepaymentLength = 0;				
	
			}
	    }
	});

}



//设置证件包查询参数
management.certificateBag.queryByRepaymentSetParam=function(form,queryType){
	//定义查询参数
	var params={};
	//按照领取查询
	if(management.certificateBag.queryRepayment_TD==queryType){
		var queryTakeValues=form.getValues();
		Ext.apply(params,{ 
				'vo.certificatebagDto.vehicleNo' : queryTakeValues.vehicleNo,  //车牌号
				'vo.certificatebagDto.containerNo' : queryTakeValues.containerNo,  //货柜号
				'vo.certificatebagDto.certificatebagEntity.orgId' : queryTakeValues.orgId, //所属部门							
				'vo.certificatebagDto.certificatebagEntity.certificatebagStatus' : queryTakeValues.certificatebagStatus, //证件包状态
				'vo.certificatebagDto.certificatebagEntity.businessType' : queryTakeValues.businessType, //业务交接情况
				'vo.certificatebagDto.certificatebagEntity.actualTakeUserName' : queryTakeValues.actualTakeUserName,	 //领取人	
				'vo.certificatebagDto.certificatebagEntity.actualTakeOrgCode' : queryTakeValues.actualTakeOrgCode, //领用部门	
				'vo.certificatebagDto.beginActualTakeTime' : queryTakeValues.beginActualTakeTime, // 开始 领取时间
				'vo.certificatebagDto.endActualTakeTime' : queryTakeValues.endActualTakeTime, // 结束领取时间
				'vo.certificatebagDto.certificatebagEntity.handOverStatus' : queryTakeValues.handOverStatus//异常证件包
		});
		
	//按照归还查询单查询
	}else if(management.certificateBag.queryRepayment_RT==queryType){
		var queryReturnValues=form.getValues();		
		Ext.apply(params,{
			 	'vo.certificatebagDto.vehicleNo' : queryReturnValues.vehicleNo,  //车牌号
				'vo.certificatebagDto.containerNo' : queryReturnValues.containerNo,  //货柜号
				'vo.certificatebagDto.certificatebagEntity.orgId' : queryReturnValues.orgId, //所属部门							
				'vo.certificatebagDto.certificatebagEntity.certificatebagStatus' : queryReturnValues.certificatebagStatus, //证件包状态
				'vo.certificatebagDto.certificatebagEntity.businessType' : queryReturnValues.businessType, //业务交接情况 
				'vo.certificatebagDto.certificatebagEntity.actualReturnUserName' : queryReturnValues.actualReturnUserName,//归还人	
				'vo.certificatebagDto.certificatebagEntity.actualReturnOrgCode' : queryReturnValues.actualReturnOrgCode, //归还部门	
				'vo.certificatebagDto.beginActualReturnTime' : queryReturnValues.beginActualReturnTime, //开始归还时间
				'vo.certificatebagDto.endActualReturnTime' : queryReturnValues.endActualReturnTime, //结束归还时间
				'vo.certificatebagDto.certificatebagEntity.handOverStatus' : queryReturnValues.handOverStatus//异常证件包
			});
	//按照对账单查询	
	}else {
		var vehicleNo=management.certificateBag.vehicleNo;	
		if(vehicleNo!=''&&vehicleNo!=null){
			management.queryTakeCertificateBagForm.getForm().findField('vehicleNo').setValue(vehicleNo);//车牌号
			management.queryReturnCertificateBagForm.getForm().findField('vehicleNo').setValue(vehicleNo);//车牌号
			 Ext.apply(params,{
				 'vo.certificatebagDto.certificatebagEntity.certificatebagStatus' : 'N', //证件包状态
				 'vo.certificatebagDto.vehicleNo' : vehicleNo//任务车辆id	
				 
				 });
		}else{
			Ext.apply(params,{
				'vo.certificatebagDto.beginActualTakeTime' :Ext.Date.format(oldDay,'Y-m-d'), //开始归还时间
				'vo.certificatebagDto.endActualTakeTime' : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),'23','59','59'), 'Y-m-d H:i:s'), //结束归还时间
				'vo.certificatebagDto.certificatebagEntity.certificatebagStatus' : '' //证件包状态
				 });
		}
		
	}
	return params;
}


//证件包Store
Ext.define('Foss.management.certificateBagStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.management.certificateBagModel',	
	pageSize:10,
	proxy: {
	        type: 'ajax',
	        url:management.realPath('queryCertificateBag.action'),
	        actionMethods: {read: 'POST'},
	        reader: {
	            type: 'json',
	            root: 'vo.certificatebagList', 
	            totalProperty : 'totalCount',
	            successProperty: 'success'
	        }
	    },
	    submitParams: {},
		setSubmitParams: function(submitParams){
			this.submitParams = submitParams;
		},
		constructor:function(config){
			var me = this, 
				cfg = Ext.apply({}, config);
			me.listeners = {
		   		'beforeload': function(store, operation, eOpts){
		   			Ext.apply(me.submitParams, {
			          "limit":operation.limit,
			          "page":operation.page,
			          "start":operation.start
			          }); 
		   			Ext.apply(operation, {
		   				params : me.submitParams 
		   			});
		   		} 
			};
			me.callParent([ cfg ]);
		}	
});


//证件包
Ext.define('Foss.management.certificateBagGrid', {
	extend: 'Ext.grid.Panel',
	//增加表格列的分割线
    columnLines: true,
    cls:'autoHeight',
    emptyText : management.certificateBag.i18n('Foss.management.certificateBag.certificateBagGrid.emptyText'),
	bodyCls:'autoHeight',	
    title: management.certificateBag.i18n('Foss.management.certificateBag.certificateBagGrid.title'),      
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			var handOverStatus = record.get('handOverStatus');
			if(handOverStatus=='N')
			{
    			return 'predeliver_notice_customer_row_purole';
			}else
			{
			    return 'predeliver_notice_customer_row_white';
			}
		}
	},
	columns: [{
		text: management.certificateBag.i18n('foss.management.button.operate'),
		width:110,
		renderer: function(v, metadata, record, rowIndex, columnIndex, store) { 
			var url='';			
        	if(record.data.certificatebagStatus=='Y'){
        		var takeCertificateBag = '<a href="javascript:management.takeCertificateBag(\''+record.data.id+'\',\''+record.data.refId+'\');" style="color:blue;text-decoration:none; " >'+' '+management.certificateBag.i18n('foss.management.certificateBag.label.takeCertificateBag')+' &nbsp;&nbsp&nbsp&nbsp;'+'</a>';
        		url+=takeCertificateBag;
        	}else if(record.data.certificatebagStatus=='N'){
        		var returnCertificateBag = '<a href="javascript:management.returnCertificateBag(\''+record.data.id+'\',\''+record.data.refId+'\');" style="color:blue;text-decoration:none;" >'+' '+management.certificateBag.i18n('foss.management.certificateBag.label.returnCertificateBag')+'&nbsp&nbsp;'+'</a>';
        		url+=returnCertificateBag;
        	}
        	var queryCertificateBag = '<a href="javascript:management.queryCertificateBag(\''+record.data.id+'\',\''+record.data.refId+'\',\''+record.data.certificatebagStatus+'\');" style="color:blue;text-decoration:none;" >'+management.certificateBag.i18n('foss.management.certificateBag.label.queryCertificateBag')+'</a>';
            
        	return url+queryCertificateBag;
		}
	},{			
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.vehicleNoOrContainerNo'),//车牌号/货柜号 
		//关联model中的字段名
		dataIndex: 'vehicleNo' 
	},{		
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.orgId'),//所属部门
		//关联model中的字段名
		dataIndex: 'orgId',
		field: {			
			xtype:'dynamicorgcombselector'
		}
	},{		 		 
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.businessType'),//业务类型 
		//关联model中的字段名
		dataIndex: 'businessType',
		renderer:function(value){
			if(value=='LONG_TYPE') return management.certificateBag.i18n('foss.management.certificateBag.label.longtype');
			else if(value=='SHORT_TYPE') return management.certificateBag.i18n('foss.management.certificateBag.label.shorttype');
							
		}
	},{		
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.certificateOrgCode'),//证件包所在处 
		//关联model中的字段名
		dataIndex: 'actualReturnOrgCode'
	},{		 		
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.handOverStatus'),//证件包交接情况
		//关联model中的字段名		
		dataIndex: 'handOverStatus',
		renderer:function(value){
			if(value=='Y') return management.certificateBag.i18n('foss.management.certificateBag.label.normal');//正常
			else if(value=='N') return management.certificateBag.i18n('foss.management.certificateBag.label.error');//异常							
		}
	},{				
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.certificatebagStatus'),//证件包状态 
		//关联model中的字段名
		dataIndex: 'certificatebagStatus',
		renderer:function(value){
			if(value=='N') return management.certificateBag.i18n('foss.management.certificateBag.label.nothandOverStatus');//未上交
			else if(value=='Y') return management.certificateBag.i18n('foss.management.certificateBag.label.alreadyhandOverStatus');//已上交';
					
		}
	},{ 		
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.beginActualTakeTime'),//领取时间
		//关联model中的字段名
		dataIndex: 'actualTakeTime'
	},{		
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.actualTakeUserName'),//领取人 
		//关联model中的字段名
		dataIndex: 'actualTakeUserName'
	},{ 		
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.actualTakeOperator'),//领取操作人
		//关联model中的字段名
		dataIndex: 'actualTakeOperator'
	},{  
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.actualReturnTime'),//证件包归还时间
		//关联model中的字段名
		dataIndex: 'actualReturnTime'
	},{
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.actualReturnUserName'),//归还人
		//关联model中的字段名
		dataIndex: 'actualReturnUserName'
	},{ 
		//字段标题
		header: management.certificateBag.i18n('foss.management.certificateBag.label.actualReturnOperator'),//归还操作人
		//关联model中的字段名
		dataIndex: 'actualReturnOperator'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.management.certificateBagStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		management.pagingBar = me.bbar;
		me.callParent([cfg]);		
	}	
});

//点击领取证件包信息链接
management.takeCertificateBag=function(id,refId){
	var takeCertificateBagWindow = Ext.create('Ext.window.Window', {
		title: management.certificateBag.i18n('foss.management.certificateBag.takeCertificateBag.title'),		
		width:690,
		closable:true,
		closeAction:'hide',
		resizable : false,
		modal: true,
		items: [
			Ext.create('Foss.management.takeCertificateBagPanel')
		]
	});
	
	management.certificateBag.takeCertificateBagWindow = takeCertificateBagWindow;
	//请求action获取界面上需要的数据  
	Ext.Ajax.request({
		url : management.realPath('queryCertificateBagByVehicleNo.action'),
		params : {'vo.certificatebagDto.certificatebagEntity.id' : id,//id
				   'vo.certificatebagDto.certificatebagEntity.refId' : refId
		},
		success : function(response) {					
			var result = Ext.decode(response.responseText);	//查询的结果			
        	var  certificatebagList=result.vo.certificatebagList;
        	for(var i=0;i<certificatebagList.length;i++){
        		if(certificatebagList[i].status=='0'){		            			
        			management.certificateBag.takeVehicleInfo.items.items[0].setValue(certificatebagList[i].id);
					management.certificateBag.takeVehicleInfo.items.items[1].setValue(certificatebagList[i].vehicleNo);
					if(certificatebagList[i].businessType=='LONG_TYPE'){
						//management.certificateBag.takeVehicleInfo.items.items[2].setValue(certificatebagList[i].vehicleNo);									
						management.certificateBag.takeCertificateBagInfo.items.items[5].hide();
						management.certificateBag.takeCertificateBagInfo.items.items[6].hide();
						management.certificateBag.takeVehicleInfo.items.items[2].show();
						management.certificateBag.takeCertificateBagInfo.items.items[1].show();
						management.certificateBag.takeCertificateBagInfo.items.items[2].show();
						management.certificateBag.takeCertificateBagInfo.items.items[3].show();
						management.certificateBag.takeCertificateBagInfo.items.items[4].show();
					}else{
						management.certificateBag.takeVehicleInfo.items.items[2].hide();
						management.certificateBag.takeVehicleInfo.items.items[2].setValue('');
						management.certificateBag.takeCertificateBagInfo.items.items[1].hide();
						management.certificateBag.takeCertificateBagInfo.items.items[2].hide();
						management.certificateBag.takeCertificateBagInfo.items.items[3].hide();
						management.certificateBag.takeCertificateBagInfo.items.items[4].hide();									
					}	
        		}else{
        			management.certificateBag.takeVehicleInfo.items.items[2].setValue(certificatebagList[i].vehicleNo);
        				
        		}
        		Ext.getCmp('Foss_management_certificateBag_take_businessType').setValue({'businessType':certificatebagList[i].businessType});
        	   }
        	Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false,'vehicleheadPermisoDeCirculacion':false,'vehicleheadOverlandTransportation':false,'vehicleheadCarKey':false,'vehicleheadBusinessRegistrationCertificate':false,'vehicleheadPurchaseTax':false});						   		
        	Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false,'containerPermisoDeCirculacion':false,'containerOverlandTransportation':false,'containerDebitNote':false,'containerBusinessRegistrationCertificate':false,'containerVehiclePurchaseTax':false,'containerInsuranceCard':false});
        	Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicle_Id').setValue({'vehicleheadType':false,'vehiclePermisoDeCirculacion':false,'vehicleOverlandTransportation':false,'vehicleCarKey':false,'vehicleInsuranceCard':false});
        	
			//请求action获取界面上需要的数据
        	takeCertificateBagWindow.show();   
		}
	});
}
//点击归还证件包信息链接
management.returnCertificateBag=function(id,refId){
	var returnwindow = Ext.create('Ext.window.Window', {
		title: management.certificateBag.i18n('foss.management.certificateBag.returnCertificateBagPanel.title'),		
		width:690,
		closable:true,
		closeAction:'hide',
		resizable : false,
		modal: true,
		items: [
			Ext.create('Foss.management.returnCertificateBagPanel')
		]
	});	
	management.returnwindow = returnwindow;            	
	//请求action获取界面上需要的数据  
	Ext.Ajax.request({
		url : management.realPath('queryCertificateBagByVehicleNo.action'),
		params : {'vo.certificatebagDto.certificatebagEntity.id' : id,//id
			  'vo.certificatebagDto.certificatebagEntity.refId' :refId		
		},
		success : function(response) {						
			var result = Ext.decode(response.responseText);	//查询的结果						
        	var  certificatebagList=result.vo.certificatebagList;
        	for(var i=0;i<certificatebagList.length;i++){
        		if(certificatebagList[i].businessType=='LONG_TYPE'){
					management.certificateBag.returnVehicleInfo.items.items[2].show();
					management.certificateBag.returnCertificateBagInfo.items.items[1].show();
					management.certificateBag.returnCertificateBagInfo.items.items[2].show();
					management.certificateBag.returnCertificateBagInfo.items.items[3].show();
					management.certificateBag.returnCertificateBagInfo.items.items[4].show();						
					management.certificateBag.returnCertificateBagInfo.items.items[5].hide();
					management.certificateBag.returnCertificateBagInfo.items.items[6].hide();						
				}else{
					management.certificateBag.returnVehicleInfo.items.items[2].hide();
					management.certificateBag.returnCertificateBagInfo.items.items[1].hide();
					management.certificateBag.returnCertificateBagInfo.items.items[2].hide();
					management.certificateBag.returnCertificateBagInfo.items.items[3].hide();
					management.certificateBag.returnCertificateBagInfo.items.items[4].hide();
					management.certificateBag.returnCertificateBagInfo.items.items[5].show();
					management.certificateBag.returnCertificateBagInfo.items.items[6].show();						
				}
        		
        		//归还人 字段(添加默认的归还人)by 134019-yuyongxiang-2013年6月20日 10:05:43 BUG->BUG-29078(属于优化类型)
				if(certificatebagList[i].actualTakeUserCode){
					var actualReturnUserCode = management.certificateBag.returnCertificateBagInfo.items.items[0];
					actualReturnUserCode.setValue(certificatebagList[i].actualTakeUserCode);
					actualReturnUserCode.getStore().load({params:{'driverVo.driverEntity.empCode' : certificatebagList[i].actualTakeUserCode}});
				}
        		
        		if(certificatebagList[i].status=='0'){	        			
        			management.certificateBag.returnVehicleInfo.items.items[0].setValue(certificatebagList[i].id);
					management.certificateBag.returnVehicleInfo.items.items[1].setValue(certificatebagList[i].vehicleNo);	
					management.certificateBag.returnVehicleInfo.items.items[0].setValue(certificatebagList[i].id);					
					management.certificateBag.returnVehicleInfo.items.items[4].setValue(certificatebagList[i].refId);										
        		}else{
        			management.certificateBag.returnVehicleInfo.items.items[2].setValue(certificatebagList[i].vehicleNo);
        			management.certificateBag.returnVehicleInfo.items.items[5].setValue(certificatebagList[i].id);
        		}
        		Ext.getCmp('Foss.management.certificateBag.return_businessType').setValue({'businessType':certificatebagList[i].businessType});
        		     		
	         }	
        	Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false});						   		
        	Ext.getCmp('Foss.management.returnCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false});
        	Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicle_Id').setValue({'vehicleheadType':false});  
			returnwindow.show();
		}
	});	
}
//点击查看证件包信息连接
management.queryCertificateBag=function(id,refId,certificatebagStatus){
	var queryCertificateBagWindow = Ext.create('Ext.window.Window', {
		title: management.certificateBag.i18n('foss.management.certificateBag.queryCertificateBagPanel.title'),		
		width:690,
		closable:true,
		closeAction:'hide',
		resizable : false,
		modal: true,
		items: [
			Ext.create('Foss.management.queryCertificateBagPanel') 
		]
	});
	management.queryCertificateBagWindow = queryCertificateBagWindow; 
	management.certificateBag.vehicleInfo.items.items[1].setValue('');
	management.certificateBag.vehicleInfo.items.items[2].setValue('');
	management.certificateBag.vehicleInfo.items.items[4].setValue('');
	management.certificateBag.vehicleInfo.items.items[5].setValue('');
//	management.certificateBag.vehicleInfo.reset();
//	management.certificateBag.certificateBagInfo.reset();
	if(certificatebagStatus=='Y'){          		
		//请求action获取界面上需要的数据  
    	Ext.Ajax.request({
    			url : management.realPath('queryReturnByRefId.action'),
				params : {'vo.certificatebagDto.certificatebagEntity.id' : id,//id					
					  'vo.certificatebagDto.certificatebagEntity.refId' : refId		
				},
				success : function(response) {						
					var result = Ext.decode(response.responseText);	//查询的结果					 
	            	var  certificatebagList=result.vo.certificatebagQueryList;
	            	var vehicleList=new Array();
	            	var containerList=new Array();
	            	var vehicleheadList=new Array();
	            	for(var i=0;i<certificatebagList.length;i++){	
	            		if(certificatebagList[i].status=='0'){//显示车牌证件
	            			management.certificateBag.vehicleInfo.items.items[0].setValue(certificatebagList[i].id);
							management.certificateBag.vehicleInfo.items.items[1].setValue(certificatebagList[i].vehicleNo);
							management.certificateBag.vehicleInfo.items.items[2].setValue(certificatebagList[i].orgId);						
							management.certificateBag.certificateBagInfo.items.items[10].setValue(certificatebagList[i].actualTakeNotes);
													
	            		}else if(certificatebagList[i].status=='1'){
	            			management.certificateBag.vehicleInfo.items.items[4].setValue(certificatebagList[i].vehicleNo);
	            			 management.certificateBag.vehicleInfo.items.items[5].setValue(certificatebagList[i].orgId);
	            		}            		
	            		if(certificatebagList[i].takeStatus=='VEHICLEHEAD'){
	            			vehicleheadList.push(certificatebagList[i].takeType);
	            		}else if(certificatebagList[i].takeStatus=='CONTAINER'){
	            			containerList.push(certificatebagList[i].takeType);
	            		}else if(certificatebagList[i].takeStatus=='VEHICLE'){	
	            			vehicleList.push(certificatebagList[i].takeType);
	            		}            	
	            	}
	            	if(certificatebagList[0].businessType=='LONG_TYPE'){//判断是长途车还是短途
						management.certificateBag.vehicleInfo.items.items[3].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.longtype'));
						management.certificateBag.vehicleInfo.items.items[4].show();
	        			management.certificateBag.vehicleInfo.items.items[5].show();
						management.certificateBag.certificateBagInfo.items.items[3].show();
						management.certificateBag.certificateBagInfo.items.items[4].show();
						management.certificateBag.certificateBagInfo.items.items[5].show();
						management.certificateBag.certificateBagInfo.items.items[6].show();
						management.certificateBag.certificateBagInfo.items.items[7].hide();
						management.certificateBag.certificateBagInfo.items.items[8].hide();
					}else{											
						management.certificateBag.vehicleInfo.items.items[3].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.shorttype'));										
						management.certificateBag.vehicleInfo.items.items[4].hide();
	        			management.certificateBag.vehicleInfo.items.items[5].hide();
	        			management.certificateBag.certificateBagInfo.items.items[3].hide();
						management.certificateBag.certificateBagInfo.items.items[4].hide();
						management.certificateBag.certificateBagInfo.items.items[5].hide();
						management.certificateBag.certificateBagInfo.items.items[6].hide();										
						management.certificateBag.certificateBagInfo.items.items[7].show();
						management.certificateBag.certificateBagInfo.items.items[8].show();
					}
	            	//证件包状态     如果是N未上交  证件包所在处是在司机处，否则为Y是已上交 证件包所在处是领取地
					if(certificatebagList[0].certificatebagStatus=='N'){
						 management.certificateBag.certificateBagInfo.items.items[1].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.nothandOverStatus')); 
						 management.certificateBag.certificateBagInfo.items.items[0].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.driverbureau'));
					}else{
						 management.certificateBag.certificateBagInfo.items.items[1].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.alreadyhandOverStatus')); 
						 management.certificateBag.certificateBagInfo.items.items[0].setValue(certificatebagList[0].actualReturnOrgCode);											
					}    									
					//证件包交接情况
					if(certificatebagList[0].handOverStatus=='Y'){    										
						 management.certificateBag.certificateBagInfo.items.items[2].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.normal'));    										 
					}else{
						 management.certificateBag.certificateBagInfo.items.items[2].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.error'));
					}
	            	//Ext.getCmp('Foss.management.queryCertificateBagPanel.vehicleType_Id').setValue({'vehicleCarKey' : certificatebagList[i].takeType,'vehicleOverlandTransportation' : certificatebagList[i].takeType,'vehiclePermisoDeCirculacion' : certificatebagList[i].takeType,'vehicleInsuranceCard' : certificatebagList[i].takeType});
	            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicle_Id').setValue({'vehicle' :vehicleList});
	            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.containerType_Id').setValue({'container' :containerList});
	            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicleheadType_Id').setValue({'vehiclehead' :vehicleheadList});
	            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicle_Id').setReadOnly(true);
	            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.containerType_Id').setReadOnly(true);
	            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicleheadType_Id').setReadOnly(true);
            	queryCertificateBagWindow.show();
			}
		});
	  
	}else{  
	//请求action获取界面上需要的数据  
		Ext.Ajax.request({
			url : management.realPath('queryTakeByRefId.action'),
			params : {'vo.certificatebagDto.certificatebagEntity.id' : id,//id				 
				  'vo.certificatebagDto.certificatebagEntity.refId' :refId		
			},
			success : function(response) {						
				var result = Ext.decode(response.responseText);	//查询的结果    							
            	var  certificatebagList=result.vo.certificatebagQueryList;
            	var vehicleList=new Array();
            	var containerList=new Array();
            	var vehicleheadList=new Array();
            	for(var i=0;i<certificatebagList.length;i++){	
            		if(certificatebagList[i].status=='0'){//显示车牌证件
            			management.certificateBag.vehicleInfo.items.items[0].setValue(certificatebagList[i].id);
						management.certificateBag.vehicleInfo.items.items[1].setValue(certificatebagList[i].vehicleNo);
						management.certificateBag.vehicleInfo.items.items[2].setValue(certificatebagList[i].orgId);						
						management.certificateBag.certificateBagInfo.items.items[10].setValue(certificatebagList[i].actualTakeNotes);
												
            		}else if(certificatebagList[i].status=='1'){
            			management.certificateBag.vehicleInfo.items.items[4].setValue(certificatebagList[i].vehicleNo);
            			 management.certificateBag.vehicleInfo.items.items[5].setValue(certificatebagList[i].orgId);
            		}            		
            		if(certificatebagList[i].takeStatus=='VEHICLEHEAD'){
            			vehicleheadList.push(certificatebagList[i].takeType);
            		}else if(certificatebagList[i].takeStatus=='CONTAINER'){
            			containerList.push(certificatebagList[i].takeType);
            		}else if(certificatebagList[i].takeStatus=='VEHICLE'){	
            			vehicleList.push(certificatebagList[i].takeType);
            		}            	
            	}
            	if(certificatebagList[0].businessType=='LONG_TYPE'){//判断是长途车还是短途
					management.certificateBag.vehicleInfo.items.items[3].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.longtype'));
					management.certificateBag.vehicleInfo.items.items[4].show();
        			management.certificateBag.vehicleInfo.items.items[5].show();
					management.certificateBag.certificateBagInfo.items.items[3].show();
					management.certificateBag.certificateBagInfo.items.items[4].show();
					management.certificateBag.certificateBagInfo.items.items[5].show();
					management.certificateBag.certificateBagInfo.items.items[6].show();
					management.certificateBag.certificateBagInfo.items.items[7].hide();
					management.certificateBag.certificateBagInfo.items.items[8].hide();
				}else{											
					management.certificateBag.vehicleInfo.items.items[3].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.shorttype'));										
					management.certificateBag.vehicleInfo.items.items[4].hide();
        			management.certificateBag.vehicleInfo.items.items[5].hide();
        			management.certificateBag.certificateBagInfo.items.items[3].hide();
					management.certificateBag.certificateBagInfo.items.items[4].hide();
					management.certificateBag.certificateBagInfo.items.items[5].hide();
					management.certificateBag.certificateBagInfo.items.items[6].hide();										
					management.certificateBag.certificateBagInfo.items.items[7].show();
					management.certificateBag.certificateBagInfo.items.items[8].show();
				}
            	//证件包状态     如果是N未上交  证件包所在处是在司机处，否则为Y是已上交 证件包所在处是领取地
				if(certificatebagList[0].certificatebagStatus=='N'){
					 management.certificateBag.certificateBagInfo.items.items[1].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.nothandOverStatus')); 
					 management.certificateBag.certificateBagInfo.items.items[0].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.driverbureau'));
				}else{
					 management.certificateBag.certificateBagInfo.items.items[1].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.alreadyhandOverStatus')); 
					 management.certificateBag.certificateBagInfo.items.items[0].setValue(certificatebagList[0].actualReturnOrgCode);											
				}    									
				//证件包交接情况
				if(certificatebagList[0].handOverStatus=='Y'){    										
					 management.certificateBag.certificateBagInfo.items.items[2].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.normal'));    										 
				}else{
					 management.certificateBag.certificateBagInfo.items.items[2].setValue(management.certificateBag.i18n('foss.management.certificateBag.label.error'));
				}
            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicle_Id').setValue({'vehicle' :vehicleList});
            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.containerType_Id').setValue({'container' :containerList});
            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicleheadType_Id').setValue({'vehiclehead' :vehicleheadList});
            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicle_Id').setReadOnly(true);
            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.containerType_Id').setReadOnly(true);
            	Ext.getCmp('Foss.management.certificateBag.queryCertificateBagPanel.vehicleheadType_Id').setReadOnly(true);
        	queryCertificateBagWindow.show();
		}
	 });
   }
}
//归还证件包界面
Ext.define('Foss.management.returnVehicleInfoForm',{
	   extend: 'Ext.form.FieldSet',
	   layout: 'column',
	   frame: true,
	   title: management.certificateBag.i18n('foss.management.certificateBag.vehicleInfo.title'), //车辆信息	
	   width:650,
	   layout:'column',
	   defaults:{
			xtype: 'textfield',
			margin : '5 5 5 5',
			anchor: '90%'					
		},
		items:[{
					name: 'id',
					xtype: 'hiddenfield'
				},{
					xtype: 'commonowntruckselector',
					name: 'vehicleNo',						
					fieldLabel:  management.certificateBag.i18n('foss.management.certificateBag.label.vehicleNo'),//车牌号
					labelWidth:50,
					columnWidth:.3,
					readOnly : true
				},{
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.containerNo'),//货柜号
					labelWidth:50,	
					xtype : 'commonowntruckselector',							
					displayField : 'containerCode',// 显示名称
					valueField : 'containerCode',
					myQueryParam : 'containerCode',
					showContent : '{containerCode}',
					vehicleTypes : 'vehicletype_trailer',
					name: 'containerNo',
					columnWidth:.28,
					readOnly : true
				},{
					xtype: 'radiogroup',
			        fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.businessType'),//业务类型  
			        labelWidth:70,				       
			        vertical: true, 					      
			        id:'Foss.management.certificateBag.return_businessType',	
			        columnWidth:.4,
			        items: [
			            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.longtype'),readOnly : true, name: 'businessType',margin : '5 5 5 5', inputValue: 'LONG_TYPE'},
			            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.shorttype'),readOnly : true, name: 'businessType',margin : '5 5 5 5', inputValue: 'SHORT_TYPE'}				           
			        ],
			        listeners:{change:function(ewfa,newValue,oldValue, eOpts){					        
			        	if(newValue.businessType=='LONG_TYPE'){	
			        		Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false});						   		
				        	Ext.getCmp('Foss.management.returnCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false});
				        	Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicle_Id').setValue({'vehicleheadType':false});
				        	management.certificateBag.returnCertificateBagInfo.items.items[1].show();
							management.certificateBag.returnCertificateBagInfo.items.items[2].show();
			        		management.certificateBag.returnCertificateBagInfo.items.items[3].show();
							management.certificateBag.returnCertificateBagInfo.items.items[4].show();
							management.certificateBag.returnVehicleInfo.items.items[2].show();							
							management.certificateBag.returnCertificateBagInfo.items.items[5].hide();
							management.certificateBag.returnCertificateBagInfo.items.items[6].hide();	
			        	}else{
							management.certificateBag.returnCertificateBagInfo.items.items[1].hide();
							management.certificateBag.returnCertificateBagInfo.items.items[2].hide();
			        		management.certificateBag.returnCertificateBagInfo.items.items[3].hide();
							management.certificateBag.returnCertificateBagInfo.items.items[4].hide();
							management.certificateBag.returnVehicleInfo.items.items[2].hide();							
							management.certificateBag.returnVehicleInfo.items.items[2].setValue('');
							management.certificateBag.returnCertificateBagInfo.items.items[5].show();
							management.certificateBag.returnCertificateBagInfo.items.items[6].show();							
			        	}
						
					 }
			       }
				},{
					name: 'refId',	
					xtype: 'hiddenfield',							
					labelWidth:60,
					columnWidth:.3	
				},{
					name: 'containerId',	
					xtype: 'hiddenfield',							
					labelWidth:60,
					columnWidth:.3	
				}]
});

//复选框全选
management.certificateBag.checkAll=function(array,type){
	if(type=='vehicle'){
		if(management.certificateBag.returnVehicleInfo.items.items[1].getValue()==null){
			Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false})
			return;
		}
	}else{
		if(management.certificateBag.returnVehicleInfo.items.items[2].getValue()==null){
			Ext.getCmp('Foss.management.returnCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false})
	   	    return;
		}
	}
	
	if(array.items[0].getValue()==true){
		array.each(function(item){ 
		    item.setValue(true);             
	     });  
	}else{
	    array.each(function(item){ 
	    item.setValue(false);             
	    }); 
	}
}

//给复选框赋值
management.certificateBag.checkReturnAll=function(array,type){
	if(type=='vehicle'){
		if(management.certificateBag.returnVehicleInfo.items.items[1].getValue()==null){
			Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false})
			return;
		}
	}else{
		if(management.certificateBag.returnVehicleInfo.items.items[2].getValue()==null){
			Ext.getCmp('Foss.management.returnCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false})
	   	    return;
		}
	}
	 var num=0;//
	 array.each(function(item){ 
            item.setValue(item.getValue()); 
            if(item.getValue()==true){
		    	num=num+1;
		    }
      });	 
	 if(array.items[0].getValue()==true){
		num=num-1; 
	 }
	 if(array.length-1==num){
		 array.items[0].setValue(true); 
	 }else{
		 array.items[0].setValue(false);
	 }	
}
//找归还证件包证件复选框赋值
management.certificateBag.checkVicleHeadType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsHeadStore.data;//得到车头证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehiclehead',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.vehiclehead.title'),//车头证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.returnCertificateBagPanel.vehicleheadType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicleheadType_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.certificateBag.checkAll(array,'vehicle');
							}else{
								management.certificateBag.checkReturnAll(array,'vehicle');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}


//找归还证件包证件复选框赋值
management.certificateBag.checkReturnContainerType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsContainerStore.data;//得到车头证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'container',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.container.title'),//车柜证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.returnCertificateBagPanel.containerType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss.management.returnCertificateBagPanel.containerType_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.certificateBag.checkAll(array,'container');
							}else{
								management.certificateBag.checkReturnAll(array,'container');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}

//查找归还证件包车辆证件复选框赋值
management.certificateBag.checkReturnVehicleType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsVehicleStore.data;//得到车柜证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehicle',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.vehicle.title'),//车辆证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.returnCertificateBagPanel.vehicle_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss.management.certificateBag.returnCertificateBagPanel.vehicle_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.certificateBag.checkAll(array,'vehicle');
							}else{
								management.certificateBag.checkReturnAll(array,'vehicle');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}

//归还证件包界面
Ext.define('Foss.management.returnCertificateBagInfoForm',{
	   extend: 'Ext.form.FieldSet',
	   layout: 'column',
	   frame: true,
	   title:management.certificateBag.i18n('foss.management.certificateBag.certificateBagInfo.title'),//证件包信息	    	   
	   layout:'column',
	   width:650,
	   defaults:{	
		   	xtype: 'textfield',
		   	margin:'5 5 5 5'
		},
		items:[{
					xtype: 'commonowndriverselector',
	    			name: 'actualReturnUserCode',	
	    			allowBlank: false,
	    			labelWidth: 60,	
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualReturnUserName'),//归还人
					listeners: {
						'select': function(field, records, eOpts) {					
							var record = records[0],
								name = record.get('name');
							this.up('form').form.findField('actualReturnUserName').setValue(field.rawValue);
							
						}
					}
						
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					id:'Foss.management.certificateBag.returnCertificateBagPanel.vehicleheadhr',
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.checkVicleHeadType()
//					         ]
				},{   
					xtype : "panel",  
					border : false, 
					id:'Foss.management.certificateBag.certificateBag.returnCertificateBagPanel.containerhr',
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.checkReturnContainerType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					id:'Foss.management.certificateBag.returnCertificateBagPanel.vehiclehr',
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.checkReturnVehicleType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					 xtype : 'textarea',
					name : 'actualReturnNotes' , 
					fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.label.notes'),//备注
					labelWidth: 60,	
					maxLength : 60,
				    maxLengthText:  management.certificateBag.i18n('foss.management.certificateBag.label.maxLengthText'),//长度已超过最大限制!
					height:60,
					width:300,
					rowspan : 3
				},{
					name: 'actualReturnUserName',
					xtype: 'hiddenfield'
				}]
});

//归还证件包界面
Ext.define('Foss.management.returnCertificateBagPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	       management.certificateBag.returnVehicleInfo = Ext.create('Foss.management.returnVehicleInfoForm'),
	       management.certificateBag.returnCertificateBagInfo = Ext.create('Foss.management.returnCertificateBagInfoForm')   
	 ],
	  buttons: [{
	      text: management.certificateBag.i18n('foss.management.certificateBag.button.affirmsubmit'),//确认提交
	      disabled: !management.certificateBag.isPermission('management/returnCertificateBagButton'),
	      hidden: !management.certificateBag.isPermission('management/returnCertificateBagButton'),
	      cls:'yellow_button',
	      handler: function() {
	      	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues();
	      	  //车牌号或者货柜号是否输入了
	      	 if(vals.vehicleNo==undefined&&vals.containerNo==undefined){
		      		Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.exception.vehicleNoOrContainerNoSelectOne'), 'ok', 1000);
		      		return;
	      	 }
	     	//得到复选的车辆证件 
		      	var fieldVehicleVaules=form.getFieldValues().vehicle;
		      	var fieldVehicleLen=0;
		      	for(var i=0;i<fieldVehicleVaules.length;i++){
		      		if(fieldVehicleVaules[i]==true){
		      			fieldVehicleLen+=1;
		      		}
		      	}
		      	var vehicles=new Array(); //定义一个车辆数组
		      	var vehicleValues=vals.vehicle;
		      	 if(fieldVehicleLen==1){
		      		vehicles.push(vehicleValues);
		      	 }else if(fieldVehicleLen>1){
		      		for(var j=0;j<vehicleValues.length;j++){
		      			vehicles.push(vehicleValues[j]);
			      	}
		      	 }
		      	vals.vehicle=vehicles;
		      	//等到复选的车柜证件
		      	var fieldcontainerVaules=form.getFieldValues().container;
		      	var fieldcontainerLen=0;
		      	for(var i=0;i<fieldcontainerVaules.length;i++){
		      		if(fieldcontainerVaules[i]==true){
		      			fieldcontainerLen+=1;
		      		}
		      	}
		      	var containers=new Array(); //定义一个车辆数组
		      	var containerValues=vals.container;
		      	 if(fieldcontainerLen==1){
		      		containers.push(containerValues);
		      	 }else if(fieldcontainerLen>1){
		      		for(var j=0;j<containerValues.length;j++){
		      			containers.push(containerValues[j]);
			      	}
		      	 }
		      	vals.container=containers;
		      	//复选的车头证件
		      	var fieldvehicleheadVaules=form.getFieldValues().vehiclehead;
		      	var fieldvehicleheadLen=0;
		      	for(var i=0;i<fieldvehicleheadVaules.length;i++){
		      		if(fieldvehicleheadVaules[i]==true){
		      			fieldvehicleheadLen+=1;
		      		}
		      	}
		      	var vehicleheads=new Array(); //定义一个车辆数组
		      	var vehicleheadValues=vals.vehiclehead;
		      	 if(fieldvehicleheadLen==1){
		      		vehicleheads.push(vehicleheadValues);
		      	 }else if(fieldvehicleheadLen>1){
		      		for(var j=0;j<vehicleheadValues.length;j++){
		      			vehicleheads.push(vehicleheadValues[j]);
			      	}
		      	 }
		      	vals.vehiclehead=vehicleheads;
	      	var conLength=0;//车柜证件
	      	if(vals.container!=''&&vals.container!=undefined){
	      		conLength=vals.container.length;	//车牌证件长度      		
	      	}	      
	      	var vehicleHeadLength=0;//车头证件
	      	if(vals.vehiclehead!=''&&vals.vehiclehead!=undefined){
	      		vehicleHeadLength=vals.vehiclehead.length;    		
	      	}
	    	var vehicleLength=0;//车辆证件
	      	if(vals.vehicle!=''&&vals.vehicle!=undefined){
	      		vehicleLength=vals.vehicle.length;   //车辆证件长度 		
	      	}
	      	var isNotes=true;//备注是否必填
	      	var data=management.certificateBag.configItemsHeadStore.data;//得到车头证件
	    	var containerData=management.certificateBag.configItemsContainerStore.data;//得到车头证件
	    	var vehicleData=management.certificateBag.configItemsVehicleStore.data;//得到车柜证件
	      	if(vals.vehicleNo!=''&&vals.vehicleNo!=undefined){//
	      		if(vals.businessType=='LONG_TYPE'){//长途车
	      			//判断车牌证件是否选择
		      		if(vehicleHeadLength==0){
		      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehiclehead'), 'ok', 1000);
		      			return;
		      		}else if(vehicleHeadLength!=data.length){		      		
		      			//车柜证件未全部选择备注项必填
		      			isNotes=false;
		      			vals.vehicleHandOverStatus='N';
		      		}else{
		      			vals.vehicleHandOverStatus='Y';
		      		}	 
	      		}else{//短途车
	      			//判断车牌证件是否选择
		      		if(vehicleLength==0){
		      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehicle'), 'ok', 1000);
		      			return;
		      		}else if(vehicleLength!=vehicleData.length){		      		
		      			//车柜证件未全部选择备注项必填
		      			isNotes=false;
		      			vals.vehicleHandOverStatus='N';
		      		}else{
		      			vals.vehicleHandOverStatus='Y';
		      		}	 
	      		}     		  
	      	  }else{
	      		  //车牌号为空的情况，检查证件是否点击了
	      		if(vals.businessType=='LONG_TYPE'){//长途车
	      			//判断车牌证件是否选择
		      		if(vehicleHeadLength>0){
		      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehicleNo'), 'ok', 1000);
		      			return;
		      		}	 
	      		}else{//短途车
	      			//判断车牌证件是否选择
		      		if(vehicleLength>0){
		      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehicleNo'), 'ok', 1000);
		      			return;
		      		}
	      		}		 
	      	  }
	      	//车柜证件是否输入
	      	 if(vals.containerNo!=undefined&&vals.containerNo!=''){
	      		 //车头证件一个都未选择
	      		if(conLength==0){
	      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.containerselectAll'), 'ok', 1000);
	      			//是否全选  
	      			return ;
	      		  }else if(conLength!=containerData.length){
	      			isNotes=false;      			  
	      			vals.containerHandOverStatus='N';
		      	 }else{
		      		vals.containerHandOverStatus='Y';
		      	 }	       		  
	      	  }else{
	      		 //车头证件一个都未选择
		      		if(conLength>0){
		      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectcontainerNo'), 'ok', 1000);
		      			//是否全选  
		      			return ;
		      		  }
	      	  }
	      	
	      	 if(isNotes){
	      		//
	      	 }else{
	      		 if(vals.actualReturnNotes==''){
	      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.papersNotselectAll'), 'ok', 1000);
	      			return;		      		
	      		 }
	      		  
	      	 }
	      	 
	      	  var formPanel=this.up('form');
	      	  Ext.MessageBox.confirm(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.certificateBagAlreadyreturn'), function(button,text){   
						if(button=='yes'){
							if(form.isValid()){
								//modify by liangfuxiang BUG-13616
								formPanel.getEl().mask(management.certificateBag.i18n('foss.management.certificateBag.button.affirmsubmit'));
								var params={vo:{returnDto:vals}};
							     Ext.Ajax.request({
						       	  url: management.realPath('returnCertificateBag.action'),
						       	  jsonData: params,
						       	  success: function(response, opts) { 
						       		//modify by liangfuxiang BUG-13616
						       		formPanel.getEl().unmask();
							    	form.reset();
							    	management.returnwindow.close();
							    	management.pagingBar.moveFirst();
							       Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'), management.certificateBag.i18n('Foss.management.message.saveSuccess'), 'ok', 1000);
						       	  },
						       	 exception: function(response, opts) {
							       		//modify by liangfuxiang BUG-13616
						       		 	formPanel.getEl().unmask();
							       		var result = Ext.decode(response.responseText);	
								       Ext.Msg.alert(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),result.message);
							      }
						      });
					    	}
						}
				});
	      
		    

	      }
	  }],
	  constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
		me.returnVehicleInfo = management.certificateBag.returnVehicleInfo;
		me.returnCertificateBagInfo = management.certificateBag.returnCertificateBagInfo;
		
	}
});

//领取证件包车头信息
Ext.define('Foss.management.takeVehicleInfoForm',{
	extend: 'Ext.form.FieldSet',
	title: management.certificateBag.i18n('foss.management.certificateBag.vehicleInfo.title'), //车辆信息
	frame: true,
	id:'Foss_management_certificateBag_takeVehicleInfoForm_id',
	width:650,
    layout:'column',
    defaults:{
			xtype: 'textfield',
			margin : '5 5 5 5',
			anchor: '90%'					
		},
		items:[{
					name: 'id',
					xtype: 'hiddenfield'
				},{
					xtype: 'commonowntruckselector',
					name: 'vehicleNo',							
					fieldLabel:  management.certificateBag.i18n('foss.management.certificateBag.label.vehicleNo'),//车牌号
					labelWidth:50,					
					columnWidth:.3	
					
				},{
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.containerNo'),//货柜号
					labelWidth:50,	
					xtype : 'commonowntruckselector',							
					displayField : 'containerCode',// 显示名称
					valueField : 'containerCode',
					myQueryParam : 'containerCode',
					showContent : '{containerCode}',
					vehicleTypes : 'vehicletype_trailer',
					name: 'containerNo',
					columnWidth:.28		
				},{
					xtype: 'radiogroup',
			        fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.businessType'),//业务类型
			        columns: 2,
			        vertical: true,			       
			        labelWidth:70,
					columnWidth:.4,
					id:'Foss_management_certificateBag_take_businessType',
			        items: [
			            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.longtype'), name: 'businessType',margin : '5 5 5 5', inputValue: 'LONG_TYPE'},
			            { boxLabel: management.certificateBag.i18n('foss.management.certificateBag.label.shorttype'), name: 'businessType',margin : '5 5 5 5', inputValue: 'SHORT_TYPE'}					           
			        ],
			        listeners:{change:function(ewfa,newValue,oldValue, eOpts){				        	
			        	if(newValue.businessType=='LONG_TYPE'){	
			        		Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false,'vehicleheadPermisoDeCirculacion':false,'vehicleheadOverlandTransportation':false,'vehicleheadCarKey':false,'vehicleheadBusinessRegistrationCertificate':false,'vehicleheadPurchaseTax':false});						   		
				        	Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false,'containerPermisoDeCirculacion':false,'containerOverlandTransportation':false,'containerDebitNote':false,'containerBusinessRegistrationCertificate':false,'containerVehiclePurchaseTax':false,'containerInsuranceCard':false});
				        	Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicle_Id').setValue({'vehicleheadType':false,'vehiclePermisoDeCirculacion':false,'vehicleOverlandTransportation':false,'vehicleCarKey':false,'vehicleInsuranceCard':false});
				        	management.certificateBag.takeCertificateBagInfo.items.items[1].show();
							management.certificateBag.takeCertificateBagInfo.items.items[2].show();
			        		management.certificateBag.takeCertificateBagInfo.items.items[3].show();
							management.certificateBag.takeCertificateBagInfo.items.items[4].show();
							management.certificateBag.takeVehicleInfo.items.items[2].show();								
			        		management.certificateBag.takeCertificateBagInfo.items.items[5].hide();
							management.certificateBag.takeCertificateBagInfo.items.items[6].hide();	
			        	}else{
							management.certificateBag.takeCertificateBagInfo.items.items[1].hide();
							management.certificateBag.takeCertificateBagInfo.items.items[2].hide();
			        		management.certificateBag.takeCertificateBagInfo.items.items[3].hide();
							management.certificateBag.takeCertificateBagInfo.items.items[4].hide();
							management.certificateBag.takeVehicleInfo.items.items[2].hide();							
							management.certificateBag.takeVehicleInfo.items.items[2].setValue('');
							management.certificateBag.takeCertificateBagInfo.items.items[5].show();
							management.certificateBag.takeCertificateBagInfo.items.items[6].show();
							
			        	}
						
					 }
			       }
				}
		       ]
	
});

//复选框全选
management.certificateBag.checkTakeAll=function(array,type){
	if(type=='vehicle'){
		if(management.certificateBag.takeVehicleInfo.items.items[1].getValue()==null){
			Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false})
			return;
		}
	}else{
		if(management.certificateBag.takeVehicleInfo.items.items[2].getValue()==null){
			Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false})
	   	    return;
		}
	}
	if(array.items[0].getValue()==true){
		array.each(function(item){ 
	    item.setValue(true);             
     });  
	}else{
	    array.each(function(item){ 
	   item.setValue(false);             
     }); 
	}
}

//给复选框赋值
management.certificateBag.checkTakeClick=function(array,type){
	if(type=='vehicle'){
		if(management.certificateBag.takeVehicleInfo.items.items[1].getValue()==null){
			Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicleheadType_Id').setValue({'vehicleheadType':false})
			return;
		}
	}else{
		if(management.certificateBag.takeVehicleInfo.items.items[2].getValue()==null){
			Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.containerType_Id').setValue({'vehicleheadType':false})
	   	    return;
		}
	}
	 var num=0;//
	 array.each(function(item){ 
            item.setValue(item.getValue()); 
            if(item.getValue()==true){
		    	num=num+1;
		    }
      });	 
	 if(array.items[0].getValue()==true){
		num=num-1; 
	 }
	 if(array.length-1==num){
		 array.items[0].setValue(true); 
	 }else{
		 array.items[0].setValue(false);
	 }	
}
//找归还证件包证件复选框赋值 (所有的车头证件)
management.certificateBag.checkTakeVicleHeadType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsHeadStore.data;//得到车头证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehiclehead',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.vehiclehead.title'),//车头证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.takeCertificateBagPanel.vehicleheadType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicleheadType_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.certificateBag.checkTakeAll(array,'vehicle');
							}else{
								management.certificateBag.checkTakeClick(array,'vehicle');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}


//找归还证件包证件复选框赋值(所有的车柜证件)
management.certificateBag.checkTakeContainerType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsContainerStore.data;//得到车头证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'container',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.container.title'),//车柜证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.takeCertificateBagPanel.containerType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.containerType_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.certificateBag.checkTakeAll(array,'container');
							}else{
								management.certificateBag.checkTakeClick(array,'container');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}

//查找归还证件包车辆证件复选框赋值(所有的车辆证件)
management.certificateBag.checkTakeVehicleType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsVehicleStore.data;//得到车柜证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehicle',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.vehicle.title'),//车辆证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.takeCertificateBagPanel.vehicle_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss.management.certificateBag.takeCertificateBagPanel.vehicle_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.certificateBag.checkTakeAll(array,'vehicle');
							}else{
								management.certificateBag.checkTakeClick(array,'vehicle');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}

//领取证件包 from
Ext.define('Foss.management.takeCertificateBagInfoForm',{
	   extend: 'Ext.form.FieldSet',
	   title:management.certificateBag.i18n('foss.management.certificateBag.certificateBagInfo.title'),//证件包信息	    	   
	   layout:'column',
	   width:650,
	   defaults:{	
		   	xtype: 'textfield',
		   	margin:'5 5 5 5'
		},
		items:[{			
					xtype: 'commonowndriverselector',
	    			name: 'actualTakeUserCode',	
	    			allowBlank: false, 
	    			labelWidth: 60,	
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualTakeUserName'),//领取人
					listeners: {
						'select': function(field, records, eOpts) {					
							var record = records[0],
								name = record.get('name');
							this.up('form').form.findField('actualTakeUserName').setValue(field.rawValue);
							
						}
					}
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.checkTakeVicleHeadType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.checkTakeContainerType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.checkTakeVehicleType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'textarea',
					name : 'actualTakeNotes' , 
					height:60,
					width:300,
					maxLength : 60,
				    maxLengthText:management.certificateBag.i18n('foss.management.certificateBag.label.maxLengthText'),//长度已超过最大限制!
					labelWidth: 60,	
					fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.label.notes'),//备注
					rowspan : 3
				},{
					name: 'actualTakeUserName',
					xtype: 'hiddenfield'
				}
				
				]
	
});


//领取证件包界面
Ext.define('Foss.management.takeCertificateBagPanel',{	
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	       management.certificateBag.takeVehicleInfo = Ext.create('Foss.management.takeVehicleInfoForm'),
	       management.certificateBag.takeCertificateBagInfo = Ext.create('Foss.management.takeCertificateBagInfoForm')   
	 ],
	  buttons: [{
	      text: management.certificateBag.i18n('foss.management.certificateBag.button.affirmsubmit'),//确认提交
	      disabled: !management.certificateBag.isPermission('management/takeCertificateBagButton'),
	      hidden: !management.certificateBag.isPermission('management/takeCertificateBagButton'),
	      cls:'yellow_button',
	      handler: function() {
	    	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues();
	      	 
	      	  //车牌号或者货柜号是否输入了
		      	 if(vals.vehicleNo==undefined&&vals.containerNo==undefined){
			      		Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.exception.vehicleNoOrContainerNoSelectOne'), 'ok', 1000);
			      		return;
		      	 }
		     	//得到复选的车辆证件 
			      	var fieldVehicleVaules=form.getFieldValues().vehicle;
			      	var fieldVehicleLen=0;
			      	for(var i=0;i<fieldVehicleVaules.length;i++){
			      		if(fieldVehicleVaules[i]==true){
			      			fieldVehicleLen+=1;
			      		}
			      	}
			      	var vehicles=new Array(); //定义一个车辆数组
			      	var vehicleValues=vals.vehicle;
			      	 if(fieldVehicleLen==1){
			      		vehicles.push(vehicleValues);
			      	 }else if(fieldVehicleLen>1){
			      		for(var j=0;j<vehicleValues.length;j++){
			      			vehicles.push(vehicleValues[j]);
				      	}
			      	 }
			      	vals.vehicle=vehicles;
			      	//等到复选的车柜证件
			      	var fieldcontainerVaules=form.getFieldValues().container;
			      	var fieldcontainerLen=0;
			      	for(var i=0;i<fieldcontainerVaules.length;i++){
			      		if(fieldcontainerVaules[i]==true){
			      			fieldcontainerLen+=1;
			      		}
			      	}
			      	var containers=new Array(); //定义一个车辆数组
			      	var containerValues=vals.container;
			      	 if(fieldcontainerLen==1){
			      		containers.push(containerValues);
			      	 }else if(fieldcontainerLen>1){
			      		for(var j=0;j<containerValues.length;j++){
			      			containers.push(containerValues[j]);
				      	}
			      	 }
			      	vals.container=containers;
			      	//复选的车头证件
			      	var fieldvehicleheadVaules=form.getFieldValues().vehiclehead;
			      	var fieldvehicleheadLen=0;
			      	for(var i=0;i<fieldvehicleheadVaules.length;i++){
			      		if(fieldvehicleheadVaules[i]==true){
			      			fieldvehicleheadLen+=1;
			      		}
			      	}
			      	var vehicleheads=new Array(); //定义一个车辆数组
			      	var vehicleheadValues=vals.vehiclehead;
			      	 if(fieldvehicleheadLen==1){
			      		vehicleheads.push(vehicleheadValues);
			      	 }else if(fieldvehicleheadLen>1){
			      		for(var j=0;j<vehicleheadValues.length;j++){
			      			vehicleheads.push(vehicleheadValues[j]);
				      	}
			      	 }
			      	vals.vehiclehead=vehicleheads;
			      	
		      	var conLength=0;//车柜证件
		      	if(vals.container!=''&&vals.container!=undefined){
		      		conLength=vals.container.length;	//车牌证件长度      		
		      	}	      
		      	var vehicleHeadLength=0;//车头证件
		      	if(vals.vehiclehead!=''&&vals.vehiclehead!=undefined){
		      		vehicleHeadLength=vals.vehiclehead.length;    		
		      	}
		    	var vehicleLength=0;//车辆证件
		      	if(vals.vehicle!=''&&vals.vehicle!=undefined){
		      		vehicleLength=vals.vehicle.length;   //车辆证件长度 		
		      	}
		      	var isNotes=true;//备注是否必填
		      	var data=management.certificateBag.configItemsHeadStore.data;//得到车头证件
		    	var containerData=management.certificateBag.configItemsContainerStore.data;//得到车头证件
		    	var vehicleData=management.certificateBag.configItemsVehicleStore.data;//得到车柜证件
		      	if(vals.vehicleNo!=''&&vals.vehicleNo!=undefined){//
		      		
		      		if(vals.businessType=='LONG_TYPE'){//长途车
		      			//判断车牌证件是否选择
			      		if(vehicleHeadLength==0){
			      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehiclehead'), 'ok', 1000);
			      			return;
			      		}else if(vehicleHeadLength!=data.length){		      		
			      			//车柜证件未全部选择备注项必填
			      			isNotes=false;
			      			vals.vehicleHandOverStatus='N';
			      		}else{
			      			vals.vehicleHandOverStatus='Y';
			      		}	 
		      		}else{//短途车
		      			//判断车牌证件是否选择
			      		if(vehicleLength==0){
			      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehicle'), 'ok', 1000);
			      			return;
			      		}else if(vehicleLength!=vehicleData.length){		      		
			      			//车柜证件未全部选择备注项必填
			      			isNotes=false;
			      			vals.vehicleHandOverStatus='N';
			      		}else{
			      			vals.vehicleHandOverStatus='Y';
			      		}	 
		      		}     		  
		      	  }else{
		      		  //车牌号为空的情况，检查证件是否点击了
		      		if(vals.businessType=='LONG_TYPE'){//长途车
		      			//判断车牌证件是否选择
			      		if(vehicleHeadLength>0){
			      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehicleNo'), 'ok', 1000);
			      			return;
			      		}	 
		      		}else{//短途车
		      			//判断车牌证件是否选择
			      		if(vehicleLength>0){
			      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectvehicleNo'), 'ok', 1000);
			      			return;
			      		}
		      		}		 
		      	  }
		      	//车柜证件是否输入
		      	 if(vals.containerNo!=undefined&&vals.containerNo!=''){
		      		 //车头证件一个都未选择
		      		if(conLength==0){
		      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.containerselectAll'), 'ok', 1000);
		      			//是否全选  
		      			return ;
		      		  }else if(conLength!=containerData.length){
		      			isNotes=false;      			  
		      			vals.containerHandOverStatus='N';
			      	 }else{
			      		vals.containerHandOverStatus='Y';
			      	 }	       		  
		      	  }else{
		      		 //车头证件一个都未选择
			      		if(conLength>0){
			      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.selectcontainerNo'), 'ok', 1000);
			      			//是否全选  
			      			return ;
			      		  }
		      	  }
		      	
		      	 if(isNotes){
		      		//
		      	 }else{
		      		 if(vals.actualTakeNotes==''){
		      			Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.papersNotselectAll'), 'ok', 1000);
		      			return;		      		
		      		 }
		      		  
		      	 }
		      
		      	 var formPanel=this.up('form');
	      		 Ext.MessageBox.confirm(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.label.certificateBagAlreadyTake'), function(button,text){   
	 				if(button=='yes'){					
	 					if(form.isValid()){
							//modify by liangfuxiang BUG-13616
	 						formPanel.getEl().mask(management.certificateBag.i18n('foss.management.certificateBag.button.affirmsubmit'));
	 						var params={vo:{takeDto:vals}};
	 					     Ext.Ajax.request({
	 				       	  url: management.realPath('takeCertificateBag.action'),
	 				       	  jsonData: params,
	 				       	  success: function(response, opts) { 
	 				       		//modify by liangfuxiang BUG-13616
	 				       		formPanel.getEl().unmask();
	 					    	form.reset();					    	
	 					    	management.certificateBag.takeCertificateBagWindow.close();
	 					    	management.pagingBar.moveFirst();
	 					       Ext.ux.Toast.msg(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'), management.certificateBag.i18n('Foss.management.message.saveSuccess'), 'ok', 1000);
	 				       	  },
	 				       	 exception: function(response, opts) {
	 				       		 	//modify by liangfuxiang BUG-13616
	 				       		 	formPanel.getEl().unmask();
	 					       		var result = Ext.decode(response.responseText);	
	 						       Ext.Msg.alert(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),result.message);
	 					       	  }
	 				         });
	 			    	}
	 				 }
	 				});
	      }
	  }],
	  constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
		me.takeVehicleInfo = management.certificateBag.takeVehicleInfo;
		me.takeCertificateBagInfo = management.certificateBag.takeCertificateBagInfo;
		
	}
});

//查看证件包车辆
Ext.define('Foss.management.vehicleInfoForm',{
	   extend: 'Ext.form.FieldSet',
 	   title:management.certificateBag.i18n('foss.management.certificateBag.vehicleInfo.title'), //车辆信息	    	  
	   layout:'column',
	   width:650,
	   defaults:{
			xtype: 'textfield',
			margin:'10 5 3 10',
			anchor: '90%'					
		},
		items:[{
					name: 'id',
					xtype: 'hiddenfield'
				},{
					name: 'vehicleNo',							
					fieldLabel:  management.certificateBag.i18n('foss.management.certificateBag.label.vehicleNo'),//车牌号
					readOnly:true,
					labelWidth:50,
					columnWidth:.3	
					
				},{
					name: 'orgId',
					xtype:'dynamicorgcombselector',
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualTakeOrgCode'),//领用部门
					labelWidth:60,
					readOnly:true,
					columnWidth:.4		
				},{
					name: 'businessType',							
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.businessType'),//业务类型
					labelWidth:90,
					readOnly:true,
					columnWidth:.3		
				},{
					name: 'containerNo',							
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.containerNo'),//货柜号
					readOnly:true,
					labelWidth:60,
					columnWidth:.3	
				},{
					name: 'orgId',	
					xtype:'dynamicorgcombselector',
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.actualTakeOrgCode'),//领用部门
					readOnly:true,
					labelWidth:60,
					columnWidth:.4	
				}    		       
		       ]
});

management.certificateBag.getPakageInfo=function(url,flag){
	Ext.Ajax.request({
		url:management.realPath(url),
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
//				store.loadData(result.vo.configOrgRelationEntityList);
				if(flag==1){//车头
					management.certificateBag.configItemsHeadStore.loadData(result.vo.configOrgRelationEntityList);
					management.certificateBag.certificateBagInfo.items.items[4].add(management.certificateBag.queryVicleHeadType());
					management.certificateBag.returnCertificateBagInfo.items.items[2].add(management.certificateBag.checkVicleHeadType());
					management.certificateBag.takeCertificateBagInfo.items.items[2].add(management.certificateBag.checkTakeVicleHeadType());
				}
				if(flag==2){//车柜
					management.certificateBag.configItemsContainerStore.loadData(result.vo.configOrgRelationEntityList);
					management.certificateBag.certificateBagInfo.items.items[6].add(management.certificateBag.queryContainerType());
					management.certificateBag.returnCertificateBagInfo.items.items[4].add(management.certificateBag.checkReturnContainerType());
					management.certificateBag.takeCertificateBagInfo.items.items[4].add(management.certificateBag.checkTakeContainerType());
				}
				if(flag==3){//车辆
					management.certificateBag.configItemsVehicleStore.loadData(result.vo.configOrgRelationEntityList);
					management.certificateBag.certificateBagInfo.items.items[8].add(management.certificateBag.queryVehicleType());
					management.certificateBag.returnCertificateBagInfo.items.items[6].add(management.certificateBag.checkReturnVehicleType());
					management.certificateBag.takeCertificateBagInfo.items.items[6].add(management.certificateBag.checkTakeVehicleType());
				}
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
			     Ext.Msg.alert(management.takecertificateBag.i18n('Foss.management.takecertificateBag.requestTimeout'));//请求超时
			}else{
				 Ext.Msg.alert(management.takecertificateBag.i18n('Foss.management.takecertificateBag.requestFail')+result.message);
			}
		}
	});
};

//查看证件包证件复选框赋值 (所有的车头证件)
management.certificateBag.queryVicleHeadType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsHeadStore.data;//得到车头证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehiclehead',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.vehiclehead.title'),//车头证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.queryCertificateBagPanel.vehicleheadType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});	
	return checkBoxGroup;
}


//查看证件包证件复选框赋值(所有的车柜证件)
management.certificateBag.queryContainerType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsContainerStore.data;//得到车头证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'container',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.container.title'),//车柜证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.queryCertificateBagPanel.containerType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	return checkBoxGroup;
}

//查查看证件包车辆证件复选框赋值(所有的车辆证件)
management.certificateBag.queryVehicleType=function(){
	var checkboxitems="[{boxLabel : '"+management.certificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	var data=management.certificateBag.configItemsVehicleStore.data;//得到车柜证件
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehicle',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.vehicle.title'),//车辆证件
		labelAlign: 'top',
		width:600,
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss.management.certificateBag.queryCertificateBagPanel.vehicle_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});	
	return checkBoxGroup;
}

//查看证件包Form
Ext.define('Foss.management.certificateBagInfoForm',{
	   extend: 'Ext.form.FieldSet',
	   title:management.certificateBag.i18n('foss.management.certificateBag.certificateBagInfo.title'),//证件包信息	
	   width:650,
	   layout:'column',
	   defaults:{	
		   	xtype: 'textfield',
		   	margin:'5 5 5 5'
		},
		items:[{
					
					xtype:'dynamicorgcombselector',
	    			name: 'actualTakeOrgCode',	
	    			readOnly:true,
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.certificateOrgCode')
				},{
					name: 'certificatebagStatus',	
					readOnly:true,
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.certificatebagStatus'),//证件包状态
				},{
					name: 'handOverStatus',
					readOnly:true,
					fieldLabel: management.certificateBag.i18n('foss.management.certificateBag.label.handOverStatus')//证件包交接情况
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.queryVicleHeadType() 
//				]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.queryContainerType()
//				]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.certificateBag.queryVehicleType()
//				]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="600px">' 
				},{
					 xtype : 'textarea',
					name : 'noticeContent' , 
					labelWidth: 60,	
					readOnly:true,
					height:60,
					width:300,
					fieldLabel : management.certificateBag.i18n('foss.management.certificateBag.label.notes'),//备注
					rowspan : 3
				}]
});

//显示打印窗口PANEL
Ext.define('Foss.management.VehicleForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	border : true,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
				xtype : 'commontruckselector',
				fieldLabel :management.certificateBag.i18n('foss.management.certificateBag.vehicleNo'),// '车牌号',
				name : 'vehicleNo',
				columnWidth : 1,
				allowBlank : false
			}, {
				xtype : 'container',
				columnWidth : .6,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text :management.certificateBag.i18n('foss.management.certificateBag.print'),//'打印'
				columnWidth : .4,
				handler : function() {
					//获取车牌号
					var realVehicleNo=this.up('panel').getForm().findField('vehicleNo').getValue();
					//车牌号为空，则提醒用户选择车牌号
					if(realVehicleNo==null || realVehicleNo==''){
						Ext.Msg.alert(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.certificateBag.i18n('foss.management.certificateBag.selectVehcleNo'));
						return;
					}
					var params = {'vo.vehicleNo' :realVehicleNo};
					Ext.Ajax.request({
						url : management.realPath('queryVehicleInfoForPrint.action'),
						params : params,
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var vurl = "http://localhost:8077/print";
							var data = {
								lblprtworker : "VehicleLabelPrintWorker",
								optusernum : result.vo.vehiclePrintDTO.userCode,
								printdate : result.vo.vehiclePrintDTO.printTime,
								serialnos : result.vo.vehiclePrintDTO.vehicleCode,
								carnos : result.vo.vehiclePrintDTO.vehicleNo,
								teams : result.vo.vehiclePrintDTO.vehicleMotorcadeName,
								groups : result.vo.vehiclePrintDTO.vehicleOrganizationName
							};
							Ext.data.JsonP.request({
								url : vurl,
								callbackKey : 'callback',
								params : data,
								callback : function() {
								},
								success : function(result,request) {
									Ext.Msg.alert(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),result.data.msg);
								},
								failure : function(result,request) {
									Ext.Msg.alert(management.certificateBag.i18n('foss.management.messageBox.alert.tip.title'),result.data.msg);
								}			
						});
						}				
					});								
				}
			}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//跳出打印窗口
Ext.define('Foss.management.VehicleWindow', {
	extend : 'Ext.window.Window',
	title : management.certificateBag.i18n('foss.management.certificateBag.selectVehcleNo'),//选择车牌号
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	closeAction : 'hide',
	width : 400,
	vehicleForm : null,
	getVehicleForm : function() {
		if (this.vehicleForm == null) {
			this.vehicleForm = Ext
					.create('Foss.management.VehicleForm')
		}
		return this.vehicleForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getVehicleForm()];
		me.callParent([cfg]);
	},
	listeners:{
		beforeshow:function(me){//设置数据
			var vehicleNoPrint=management.certificateBag.vehicleInfo.items.items[1].getValue();
			if(vehicleNoPrint!=null && vehicleNoPrint!=''){
				me.getVehicleForm().getForm().findField('vehicleNo').setValue(vehicleNoPrint);
			}
	    }
	}
});


//打印车牌form
Ext.define('Foss.management.printVehcleNoForm', {
	extend : 'Ext.panel.Panel',
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :50,
    width:650,
	defaultType : 'textfield',
	layout:'column',
	vehicleWindow : null,
    getVehicleWindow : function(){
    	if(this.vehicleWindow==null){
    		this.vehicleWindow=Ext.create("Foss.management.VehicleWindow");
    	}
    	return this.vehicleWindow;
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 830',
			text : management.certificateBag.i18n('foss.management.certificateBag.printVehcleNo'),//打印车牌
			handler : function() {
				//判断是否打印
				Ext.Msg.confirm(management.certificateBag.i18n('foss.management.certificateBag.isOrNotprint.title'), management.certificateBag.i18n('foss.management.certificateBag.isOrNotprint.tip'), function(btn){
				    if (btn == 'yes'){
				    	//展示打印窗口
						me.getVehicleWindow().show();
				    }
				});	
			}
		}];
		me.callParent([cfg]);
	}
});

//查看证件包界面
Ext.define('Foss.management.queryCertificateBagPanel',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	frame: false,
	defaultType: 'textfield',	
	items:[	       
	       management.certificateBag.vehicleInfo = Ext.create('Foss.management.vehicleInfoForm'),
	       management.certificateBag.certificateBagInfo = Ext.create('Foss.management.certificateBagInfoForm'),
	       management.certificateBag.printVehcleNoForm = Ext.create('Foss.management.printVehcleNoForm')
	 ],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
		me.vehicleInfo = management.certificateBag.vehicleInfo;
		me.certificateBagInfo = management.certificateBag.certificateBagInfo;
		
	}
});


Ext.onReady(function() {
	//1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
	//	用法：模块名.自定义变量
	//2.对象都是用Ext.define定义的方式声明
	Ext.QuickTips.init();
	
	management.certificateBag.configItemsHeadStore=Ext.create('Foss.management.certificateBag.ConfigItemsStore');
	management.certificateBag.configItemsContainerStore=Ext.create('Foss.management.certificateBag.ConfigItemsStore');
	management.certificateBag.configItemsVehicleStore=Ext.create('Foss.management.certificateBag.ConfigItemsStore');
	
	management.certificateBag.getPakageInfo('queryCarHeadItems.action',1);
	management.certificateBag.getPakageInfo('queryContainerCardItems.action',2);
	management.certificateBag.getPakageInfo('queryVehicleItems.action',3);
	
	//查询表单
	var queryForm = Ext.create('Foss.management.queryCertificateBagForm');
	management.certificateBag.queryForm=queryForm;	
	var certificateBagGrid=Ext.create('Foss.management.certificateBagGrid');
	management.certificateBag.certificateBagGrid=certificateBagGrid;	
	
	management.certificateBag.queryByRepayment(queryForm,'');	
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-certificateBagindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		detailInfoWindow:null,
		editDetailInfoWindow:null,		
		items: [queryForm,certificateBagGrid],
		renderTo: 'T_management-certificateBagindex-body'
	});
});

