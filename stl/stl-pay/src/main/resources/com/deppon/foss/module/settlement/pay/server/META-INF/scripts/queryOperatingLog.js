Ext.require('Foss.baseinfo.commonSelector.SaleDeptStore');
pay.operatingLog.DEFLAULT_TIME_INTEVAL= 31;  // 默认时间间隔相差31天
pay.operatingLog.DEFLAULT_TIME_INTEVAL_MAX = 60;  // 默认时间间隔最大不超过60天
pay.operatingLog.ONEPAGESIZE = 100; // 每页显示的条数
/**
 * Form重置方法
 */
pay.operatingLog.operatingLogQueryReset=function(){
	this.up('form').getForm().reset();
}

/**
 * Form查询方法
 */
pay.operatingLog.OperatingLogQuery=function(me){
	
	var form=me.getForm();
	var startBusinessDate = form.findField('operatingLogVo.operatingLogQueryDto.startOperateTime').getValue();
	var endBusinessDate = form.findField('operatingLogVo.operatingLogQueryDto.endOperateTime').getValue();
	
	if(startBusinessDate==null || startBusinessDate==''){
		
		Ext.Msg.alert(pay.operatingLog.i18n('foss.stl.pay.common.alert'),pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.startOperateTimeIsNotNull'));
		return false;
	}

	if(endBusinessDate==null || endBusinessDate==''){
		Ext.Msg.alert(pay.operatingLog.i18n('foss.stl.pay.common.alert'),pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.endOperateTimeIsNotNull'));
		return false;
	}
	
	
	var compareTwoDate = stl.compareTwoDate(startBusinessDate,endBusinessDate);
	if(compareTwoDate>pay.operatingLog.DEFLAULT_TIME_INTEVAL_MAX){
		Ext.Msg.alert(pay.operatingLog.i18n('foss.stl.pay.common.alert'),pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.startToEndOperateTimeNotMax'));
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert(pay.operatingLog.i18n('foss.stl.pay.common.alert'),pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.endOperateTimeIsNotBeforeStartOperateTime'));
		return false;
	}
		
	var grid = Ext.getCmp('T_pay-queryOperatingLog_content').getOperatingLogGrid();
	if(form.isValid()){
		var params=pay.operatingLog.setQueryParams(form);
	
		// 设置查询参数
		grid.store.setSubmitParams(params);
		// 设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	 
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				if(result.operatingLogVo.operatingLogResultDtoList!=null){
					grid.show();
				}else{
					Ext.Msg.alert(pay.operatingLog.i18n('foss.stl.pay.common.alert'),pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.noDataUnderThisQuery'));
					return false;
				}
	    	}
	       }
	    }); 
	}else {
		Ext.Msg.alert(pay.operatingLog.i18n('foss.stl.pay.common.alert'),pay.operatingLog.i18n('foss.stl.pay.common.validateFailAlert'));
	}
}


// 设置参数
pay.operatingLog.setQueryParams=function(form){
	
	// 定义查询参数
	var params={};
	
	// 获取FORM所有值
	params = form.getValues();

	return params;
}

//导出操作日志
pay.operatingLog.OperatingLogListExport = function(){
	
	var grid = Ext.getCmp('T_pay-queryOperatingLog_content').getOperatingLogGrid();
	if(grid.getStore().data.length<1){
		Ext.Msg.alert(pay.operatingLog.i18n('foss.stl.pay.common.alert'),pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.exportFailAlertByNoData'));
		return false;
	}
	
	Ext.MessageBox.buttonText.yes = pay.operatingLog.i18n('foss.stl.pay.common.ok');  
	Ext.MessageBox.buttonText.no = pay.operatingLog.i18n('foss.stl.pay.common.cancel'); 

	Ext.Msg.confirm( pay.operatingLog.i18n('foss.stl.pay.common.alert'), pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.isConfirmExportAlert'), function(btn,text){
		if(btn == 'yes'){
			var params=grid.store.submitParams;
			if(!Ext.fly('operatingLogListExportForm')){
				var frm = document.createElement('form');
				frm.id = 'operatingLogListExportForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
	
			Ext.Ajax.request({
				//url:'../pay/exportOperatingLog.action',
				url:pay.realPath('exportOperatingLog.action'),
				form: Ext.fly('operatingLogListExportForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					var result = Ext.decode(response.responseText);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
				}
			});
		}
	});
}


// 按日期查询 Form
Ext.define('Foss.operatingLog.OperatingLogQueryInfoByDate',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:260,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [
				{
					xtype : 'datefield',
					name : 'operatingLogVo.operatingLogQueryDto.startOperateTime',
					fieldLabel : pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.startOperateTime'),
					value : stl.getTargetDate(new Date(),
							-pay.operatingLog.DEFLAULT_TIME_INTEVAL),
					format : 'Y-m-d',
					allowBlank : false,
					editable:false,
					labelWidth:85,
					columnWidth : .3
				},
				{
					xtype : 'datefield',
					name : 'operatingLogVo.operatingLogQueryDto.endOperateTime',
					fieldLabel : pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.endOperateTime'),
					format : 'Y-m-d',
					value : new Date(),
					allowBlank : false,
					maxValue : new Date(),
					editable:false,
					labelWidth:85,
					columnWidth : .3
				},{
					xtype : 'commonemployeeselector',
					fieldLabel : pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operatorCode'),
					name : 'operatingLogVo.operatingLogQueryDto.operatorCode',
					labelWidth:85,
					columnWidth : .3	
				 },{
//				    	xtype:'dynamiclinkorgdeptcombselector',
//						labelWid:85,
//						bigRegionLabel:pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.businessBigRegionCode'),
//						smallRegionLabel:'&nbsp;&nbsp;'+pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.businessSmallRegionCode'),
//						bigRegionName:'operatingLogVo.operatingLogQueryDto.businessBigRegionCode',
//						smallRegionName:'operatingLogVo.operatingLogQueryDto.businessSmallRegionCode',
//						bigRegionWidth:245,
//						smallRegionWidth:250,
//						bigRegionIsBlank:true,
//						smallRegionIsBlank:true,
//						saleDeptLabel:'&nbsp;&nbsp;'+pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateOrgCode')+':',
//						saleDeptNames :'operatingLogVo.operatingLogQueryDto.operateOrgCode',
//						saleDeptWidth : 250,
//						saleDeptIsBlank : true,
//						type : 'B-S-D',
//						layout:'column',
//						labelSeparator:'',
//						columnWidth:1	
						
						xtype : 'linkagecomboselector',
						eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						itemId : 'Foss_baseinfo_BigRegion_ID',
						store : Ext.create('Foss.baseinfo.commonSelector.SaleDeptStore'),// 从外面传入
						fieldLabel :pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.businessBigRegionCode'),
						value:'',
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						minChars : 0,
						name : 'operatingLogVo.operatingLogQueryDto.businessBigRegionCode',
						allowBlank : true,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true,
						queryParam : 'commonOrgVo.name',
						columnWidth : .3	
						// 分页
					}, {
						xtype : 'linkagecomboselector',
						itemId : 'Foss_baseinfo_SmallRegion_ID',
						eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						name : 'operatingLogVo.operatingLogQueryDto.businessSmallRegionCode',
						fieldLabel : pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.businessSmallRegionCode'),
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
						},// 此处城市不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						minChars : 0,
						queryParam : 'commonOrgVo.name',
						allowBlank : true,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true,
						columnWidth : .3	
						// 分页
        			}
//					,{
//        				xtype:'dynamicorgcombselector',
//        				name:'operatingLogVo.operatingLogQueryDto.operateOrgCode',
//        				fieldLabel:'&nbsp;&nbsp;'+pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateOrgCode'),
//        				transferCenter:'Y',
//        				salesDepartment:'Y',       									
//						columnWidth:0.3	
//        			},
				   , {
						xtype : 'linkagecomboselector',
						name : 'operatingLogVo.operatingLogQueryDto.operateOrgCode',
						eventType : ['callparent'],
						store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
						fieldLabel : pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateOrgCode'),
						parentParamsAndItemIds : {
							'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
						},// 此处区域不需要传入
						minChars : 0,
						displayField : 'name',// 显示名称
						valueField : 'code',
						minChars : 0,
						queryParam : 'commonOrgVo.name',
						allowBlank : true,
						listWidth : 300,// 设置下拉框宽度
						isPaging : true,
						columnWidth : .3	
						// 分页
				},
        		{
					border: 1,
					xtype:'container',
					columnWidth:1,
					colspan:3,
					defaultType:'button',
					layout:'column',
					items:[{
						  text: pay.operatingLog.i18n('foss.stl.pay.common.reset'),
						  columnWidth:.1,
						  handler:pay.operatingLog.operatingLogQueryReset
					  	},{
							xtype:'container',
							border:false,
							html:'&nbsp;',
							columnWidth:.7
						},
					  	{
						  text:pay.operatingLog.i18n('foss.stl.pay.common.query'),
						  columnWidth:.1,
						  cls:'yellow_button',  
						  handler:function(){
							  var form=this.up('form');
							  pay.operatingLog.OperatingLogQuery(form)
						  }
					  	}]
				}]			
});

// 查询tab
Ext.define('Foss.operatingLog.OperatingLogQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	//width : 1060,
	height : 200,
	items : [ {
		title: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.OperatingLogQueryInfoTabTitle'),
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.operatingLog.OperatingLogQueryInfoByDate')
               ]
	}]
});


Ext.define('Foss.operatingLog.OperatingLogGridModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'operateBillNo'
	},{	
		name:'operateBillType'
	},{
		name:'operateOrgCode'
	},{
		name:'operateOrgName'
	},{
		name:'operatorCode'		
	},{
		name:'operatorName'		
	},{
		name:'operatorIp'		
	},{
		name:'operateTime',
		type:'Date',
		convert:stl.longToDateConvert
	},{
		name:'operateType'
	},{	
		name:'notes'
	},{
		name:'divisionCode'
	},{
		name:'divisionName'
	},{
		name:'businessBigRegionCode'
	},{	
		name:'businessBigRegionName'
	},{	
		name:'businessSmallRegionCode'
	},{	
		name:'businessSmallRegionName'
	}]
});
//日志store
Ext.define('Foss.operatingLog.OperatingLogGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.operatingLog.OperatingLogGridModel',
	pageSize: pay.operatingLog.ONEPAGESIZE,
	sorters: [{
	     property: 'operateTime',
	     direction: 'DESC'
	 }],
	proxy:{
		type:'ajax',
		//url:'../pay/queryOperatingLogList.action',
		url:pay.realPath('queryOperatingLogList.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'operatingLogVo.operatingLogResultDtoList',
			totalProperty:'totalCount'
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
//日志列表
Ext.define('Foss.operatingLog.OperatingLogGrid',{
	extend:'Ext.grid.Panel',
    title: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.OperatingLogGridTitle'),
    frame:true,
    hidden:true,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.operatingLog.OperatingLogGridStore'),
    hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	columns:[{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.id'),
		dataIndex: 'id'
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operatorName'),
		dataIndex: 'operatorName'
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateBillNo'),
		dataIndex: 'operateBillNo'
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateBillType'),
		dataIndex: 'operateBillType',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.OPERATING_LOG__OPERATE_BILL_TYPE);
    		return displayField;
		}
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateType'),
		dataIndex: 'operateType',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.OPERATING_LOG__OPERATE_TYPE);
    		return displayField;
		}
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.divisionName'), 
		dataIndex: 'divisionName'		
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.businessBigRegionCode'),
		dataIndex: 'businessBigRegionName'
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.businessSmallRegionCode'), 
		dataIndex: 'businessSmallRegionName'
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateOrgCode'),
		dataIndex: 'operateOrgName'		
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.operateTime'),
		dataIndex: 'operateTime',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 		
	},{
		header: pay.operatingLog.i18n('foss.stl.pay.queryOperatingLog.notes'),
		dataIndex: 'notes'	
	}],
	viewConfig: {
		enableTextSelection: true
	},
	constructor:function(config){
	
		var me = this;
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			},{
				xtype :'button',
				text : pay.operatingLog.i18n('foss.stl.pay.common.export'),
				columnWidth :.06,
				handler: pay.operatingLog.OperatingLogListExport,
				disabled: !pay.operatingLog.isPermission('/stl-web/pay/exportOperatingLog.action'),
				hidden: !pay.operatingLog.isPermission('/stl-web/pay/exportOperatingLog.action')
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:500
				})
			}]
		}];	
		me.callParent();
	}
});


// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_pay-queryOperatingLog_content')) {
		return;
	} 
	
	//查询日志tab
	var operatingLogQueryInfoTab = Ext.create('Foss.operatingLog.OperatingLogQueryInfoTab');
	
	//显示日志grid
	var operatingLogGrid = Ext.create('Foss.operatingLog.OperatingLogGrid');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-queryOperatingLog_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getOperatingLogQueryInfoTab:function(){
			return operatingLogQueryInfoTab;
		},
		getOperatingLogGrid:function(){
			return operatingLogGrid;
		},
		items: [operatingLogQueryInfoTab,operatingLogGrid],
		renderTo: 'T_pay-queryOperatingLog-body'
	});
});