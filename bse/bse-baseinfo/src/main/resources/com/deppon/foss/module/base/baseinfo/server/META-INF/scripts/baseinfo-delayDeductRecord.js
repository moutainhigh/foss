//延时扣款记录审核状态
var delayDeductRecordCheckStatus = {'0':'待审核','1':'已审核','2':'已拒绝','3':'已失效'};
//延时扣款记录扣款状态
var delayDeductRecordFlowStatus = {'WHF':'扣款失败','WHS':'扣款成功','UWH':'未扣款'};

/**
 * 添加开始,结束时间方法 day--
 * 比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 */
baseinfo.delayDeductRecord.getStartTime =function(date,day){
	var d, s, t, t2; 
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};
baseinfo.delayDeductRecord.getEndTime =function(date,day){
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};

//代收延时扣款记录Model
Ext.define('Foss.baseinfo.delayDeductRecord.DelayDeductModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',// ID
		type : 'string'
	},{
		name : 'applyOrgName',// 申请部门名称
		type : 'string'
	},{
	    name : 'applyOrgCode',// 申请部门编码
	    type : 'string'
	},{
		name : 'applicantName',// 申请人名称/创建人名称
		type : 'string'
	},{
		name : 'applicantCode',// 申请人编码/创建人编码
		type : 'string'
	},{
		name : 'checkOrgName',// 审核部门名称
		type : 'string'
	},{
		name : 'checkOrgCode',// 审核部门编码
		type : 'string'
	},{
		name : 'checkPersonName',// 审核人名称/修改人名称
		type : 'string'
	},{
		name : 'checkPersonCode',// 审核人编码/修改人编码
		type : 'string'
	},{
		name : 'waybillNo',// 运单号
		type : 'string'
	},{
		name : 'checkStatus',// 审核状态,0：待审核；1：已审核；2：已拒绝；3：已失效
		type : 'string'
	},{
		name : 'flowStatus',// 扣款状态 WHF：扣款失败，WHS：扣款成功，UWH：未扣款
		type : 'string'
	},{
		name : 'checkOpinion',// 审核意见
		type : 'string'
	},{
		name:'applyDeductTime',//申请扣款时间
		type : 'datefield'
	},{
		name:'applyTime',//申请时间/创建时间
		type : 'datefield'
	},{
		name:'checkTime',//审核时间/修改时间
		type : 'datefield'
	}]

});

//审核状态store
Ext.define('Foss.baseinfo.delayDeductRecord.CheckStatusStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//代收延时扣款记录查询store
Ext.define('Foss.baseinfo.delayDeductRecord.DelayDeductStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.delayDeductRecord.DelayDeductModel',
	pageSize : 200,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		timeout : 300000,
		url : baseinfo.realPath('queryDelayDeductRecord.action'),//查询url
		reader : {
			type : 'json',
			root : 'delayDeductVo.delayDeductRecordList',
			totalProperty : 'totalCount'
		}
	}
});
//条件查询代收延迟扣款记录FORM
Ext.define('Foss.baseinfo.delayDeductRecord.QuerydelayDeductForm', {
	extend : 'Ext.form.Panel',
	id:'Foss_baseinfo_QuerydelayDeductForm_Id',
	title : '查询条件', // 查询条件
	layout: 'form',
	frame : true,
	collapsible : true,
	animCollapse : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	defaults : {
		margin : '5 10 5 10'
	},
	defaultType : 'textfield',
	layout : {
		type : 'column'
	},
	constructor : function(config) { // 构造器
		var me = this, cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([ cfg ]);
	},
	getItems : function() {
		var me = this;
		return [{
					xtype : 'commonsaledepartmentselector',
					fieldLabel : '申请部门名称', // //子系统功能模块
					name : 'applyOrgCode',
					labelWidth : 85,
					width:330,
					isLeagueSaleDept:'Y'
				},{
					name:'applyStartTime',
					xtype : 'datefield',
					fieldLabel : '申请开始时间',
					allowBlank : false,//必填项
					editable : false,//不可编辑
					labelWidth:90,
					format : 'Y-m-d H:i:s',
					value : baseinfo.delayDeductRecord.getStartTime(new Date(),-30)//默认当天的0时0分0秒
				},{
					xtype : 'datefield',
					fieldLabel : '申请结束时间',
					editable : false,//不可编辑
					allowBlank : false,//必填项
					name : 'applyEndTime',
					format : 'Y-m-d H:i:s',
					labelWidth:85,
					value : baseinfo.delayDeductRecord.getEndTime(new Date(),0)//默认当天的23时59分59秒
				},{
					style:'font-size:20px',
					xtype: 'container',
					border : false,
					html: '&nbsp;'
				},{
					xtype : 'combobox',
					name: 'checkStatus',	 
					fieldLabel: '审核状态',
					labelWidth : 60,
					displayField: 'valueName',
					valueField: 'valueCode',
					queryMode: 'local',
					triggerAction : 'all',
				    editable:false,
				    store : Ext.create('Foss.baseinfo.delayDeductRecord.CheckStatusStore', {
						data : {
							'items' : [{
									'valueCode' : '',
									'valueName' : '全部'
								}, {
									'valueCode' : '0',
									'valueName' : '待审核'
								}, {
									'valueCode' : '1',
									'valueName' : '已审核'
								}, {
									'valueCode' : '2',
									'valueName' : '已拒绝'
								}, {
									'valueCode' : '3',
									'valueName' : '已失效'
								}
							]
						}
					}),
				    value : ''
				},{
					xtype : 'textfield',
					fieldLabel : '运单号', // //子系统功能模块
					name : 'waybillNo',
					labelWidth : 50
				}];
	},
	buttons : [{
				text : '重置',// 重置
				handler : function() {
					var queryForm = this.up('form');
					queryForm.getForm().reset();					
				}
			}, {
				text : '查询',// 查询
				cls : 'yellow_button',
				handler : function(btn) {
					Foss.baseinfo.delayDeductRecord.searchByCondition();
				},
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					// 设定间隔秒数,如果不设置，默认为2秒
					seconds : 1
				})
			}]
});

//点击查询  条件查询数据方法
Foss.baseinfo.delayDeductRecord.searchByCondition = function(){
	// 获取查询表单,构造查询条件
	var form =Ext.getCmp('T_baseinfo-delayDeductRecord_content').getQueryForm();
	var entity = form.getValues();
	//定义两个用来判断
	var getStartTime = Ext.Date.parse(entity.applyStartTime, 'Y-m-d H:i:s'); 
    var getEndTime = Ext.Date.parse(entity.applyEndTime, 'Y-m-d H:i:s');
	/**
	 * 添加查询时间逻辑
	 * 1、起始日期大于截止日期
	 * 2、同一自然月
	 */
	if(getStartTime>getEndTime){
		Ext.Msg.alert("FOSS提醒","起始时间不能大于结束时间！");  //FOSS提醒    起始时间不能大于结束时间！
		selectform.findField('applyStartTime').setValue(baseinfo.delayDeductRecord.getStartTime(new Date(),-30));
		selectform.findField('applyEndTime').setValue(baseinfo.delayDeductRecord.getEndTime(new Date(),0));
		return;
	}
	//获取开始时间到结束的时间的天数差
	var diff =Math.round((getEndTime.getTime()-getStartTime.getTime())/(86400*1000));
	if(diff>31){
		Ext.Msg.alert("FOSS提醒","起止时间最大跨度为31天");
		selectform.findField('applyStartTime').setValue(baseinfo.delayDeductRecord.getStartTime(new Date(),-30));
		selectform.findField('applyEndTime').setValue(baseinfo.delayDeductRecord.getEndTime(new Date(),0));
		return;
	}
	//封装参数
	var  params={
			'delayDeductVo.queryDto.applyOrgCode':entity.applyOrgCode,//申请部门名称
			'delayDeductVo.queryDto.applyStartTime':entity.applyStartTime,//申请开始时间
			'delayDeductVo.queryDto.applyEndTime':entity.applyEndTime,//申请结束时间
			'delayDeductVo.queryDto.checkStatus':entity.checkStatus,//审核状态
			'delayDeductVo.queryDto.waybillNo':entity.waybillNo//运单号
		};
	var grid = Ext.getCmp('T_baseinfo-delayDeductRecord_content').getdelayDeductQueryGrid();
	Ext.apply(grid.store.proxy.extraParams,params);
	grid.store.loadPage(1);
}
//审核代收延迟扣款记录窗口
Ext.define('Foss.baseinfo.delayDeductRecord.checkDelayDeductGridWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	title : '延时扣款审核', // 延时扣款申请窗口
	layout : 'auto',
	modal : true,
	width : 800,
	height : 300,
	checkForm : null,
	getCheckForm : function() {
		if (this.checkForm == null) {
			this.checkForm = Ext.create('Foss.baseinfo.delayDeductRecord.checkDelayDeductPanel');
		}
		return this.checkForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var checkDelayRecordFormPanel = me.getCheckForm();
		me.items = [ checkDelayRecordFormPanel ];
		me.callParent([ cfg ]);
	}
});

//延时扣款记录审核面板
Ext.define('Foss.baseinfo.delayDeductRecord.checkDelayDeductPanel',{
			extend : 'Ext.form.Panel',
			frame : true,
			collapsible : false,
			closeAction:'hide',
			defaults : {
				margin : '10 0 10 0'			
			},
			defaultType : 'textfield',
			layout : {
				type : 'column'
			},
			constructor:function(config){
				var me = this, cfg = Ext.apply({}, config); 
				me.items = [{
						xtype : 'textfield',
						name : 'applyOrgName',
						fieldId : 'applyOrgName',
						fieldLabel : '申请部门', // 申请部门名称
						readOnly : true,
						allowBlank:false,// 不允许为空
						labelWidth : 85,
						width:330
					},{
						xtype : 'textfield',
						name : 'applicantName',
						fieldId : 'applicantName',
						fieldLabel : '申请人', // 申请人
						readOnly : true,
						allowBlank:false,// 不允许为空
						labelWidth : 85,
						width:330
					},{
						xtype : 'textfield',
						name : 'checkOrgName',
						fieldId : 'checkOrgName',
						fieldLabel : '审核部门', 
						readOnly : true,
						allowBlank:false,// 不允许为空
						labelWidth : 85,
						width:330
					},{
						xtype : 'textfield',
						name : 'checkOrgCode',
						fieldId : 'checkOrgCode',
						readOnly : true,
						allowBlank:false,// 不允许为空						
						hidden:true,
						labelWidth : 85,
						width:330
					},{
						xtype : 'textfield',
						name : 'waybillNo',
						fieldLabel : '运单号', // 运单号
						readOnly : true,
						labelWidth : 85,
						width:330
					},{
						xtype : 'textfield',   
						name: "applyDeductTime",
						fieldLabel: "申请扣款时间",						
						readOnly : true,
						labelWidth : 85,
						width:330
					},{
						xtype : 'textfield',
						name : 'checkOpinion',
						fieldLabel : '审批意见',
						fieldId : 'checkOpinion',
						allowBlank:false,
						labelWidth : 85,
						width:330
					},{
						xtype : 'textfield',
						name : 'id',
						allowBlank:false,// 不允许为空						
						hidden:true
					}],
		me.fbar = [
		           {
						xtype : 'label',
						text :'*请核实是否确实需要延时扣款',						
						cls:'ftCls',
						labelWidth:200
					},{
				text : '同意',// 同意
				cls : 'yellow_button',
				handler : function() {
					var form = me.getForm();
					// 表单信息校验
					if (form.isValid()) {
						var checkStatus = "1";//审核状态 ：1-已审核
						Foss.baseinfo.delayDeductRecord.checkDelayDeduct(form,checkStatus);//提交延时扣款申请
					} else {
						Ext.Msg.alert('提示', '请确认信息是否填写完整');
					}
				}
			}, {
				text : '不同意',// 同意
				cls : 'yellow_button',
				handler : function() {
					var form = me.getForm();
					// 表单信息校验
					if (form.isValid()) {
						var checkStatus = "2";//审核状态 ： 2-已拒绝
						Foss.baseinfo.delayDeductRecord.checkDelayDeduct(form,checkStatus);//提交延时扣款申请
					} else {
						Ext.Msg.alert('提示', '请确认信息是否填写完整');
					}
				}
			} ];
		me.callParent([ cfg ]);
	}
});
//延时扣款审核
Foss.baseinfo.delayDeductRecord.checkDelayDeduct = function(form,checkStatus){
	var entity = form.getValues();
	Ext.Ajax.request({
		url : baseinfo.realPath('checkDelayDeduct.action'),//查询url
		params :{
			'delayDeductVo.delayDeductRecordentity.id':entity.id,
			'delayDeductVo.delayDeductRecordentity.checkOrgCode':entity.checkOrgCode,
			'delayDeductVo.delayDeductRecordentity.checkOrgName':entity.checkOrgName,
			'delayDeductVo.delayDeductRecordentity.checkStatus':checkStatus,
			'delayDeductVo.delayDeductRecordentity.checkOpinion':entity.checkOpinion,
			'delayDeductVo.delayDeductRecordentity.waybillNo':entity.waybillNo,
			'delayDeductVo.delayDeductRecordentity.applyDeductTime':entity.applyDeductTime
		},
		timeout : 600000,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				Ext.ux.Toast.msg('提示信息','审核成功!', 'success', 3000);				
			} else {
				Ext.Msg.alert(result.message);
			}
			Ext.getCmp('t_baseinfo_delayDeductRecord_GridPanel_Id').getCheckWindow().close();
			Foss.baseinfo.delayDeductRecord.searchByCondition();
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			if (Ext.isEmpty(result)) {
				Ext.ux.Toast.msg('提示信息','数据提交异常!', 'success', 3000);		
			} else {				
				Ext.ux.Toast.msg('提示信息',result.message, 'success', 3000);
			}
			Ext.getCmp('t_baseinfo_delayDeductRecord_GridPanel_Id').getCheckWindow().close();
		}
	});
}
//代收延迟扣款查询记录数据列表START
Ext.define('Foss.baseinfo.delayDeductRecord.DelayDeductGridPanel', {
	extend: 'Ext.grid.Panel',
	id:"t_baseinfo_delayDeductRecord_GridPanel_Id",
	title: '合伙人延时扣款记录',
	frame: true,
	collapsible: true,
	hidden: false,
	height: 500,
	emptyText:'查询结果为空',// 查询结果为空
	selModel: Ext.create('Ext.selection.CheckboxModel'),
	viewConfig: {
		enableTextSelection: true
	},
	constructor: function(config) {
		var me = this;
		cfg = Ext.apply({}, config);
		//查看实体列表
		me.store = Ext.create('Foss.baseinfo.delayDeductRecord.DelayDeductStore');
		me.listeners = me.getMyListeners(config);
		me.columns = [
	      		{
	    			xtype: 'rownumberer',
	    			width: 40,
	    			align: 'center',
	    			text: '序号' //序号
	    		},{
	    			dataIndex :'id',
	    			hidden:true
	    		},{
	    			text : '运单号', // 运单号
	    			dataIndex : 'waybillNo'
	    		},{
	    			text : '申请部门', // 申请部门
	    			dataIndex :'applyOrgName'
	    		},{
	    			dataIndex :'applyOrgCode',// 申请部门编码
	    			hidden:true
	    		}, {
	    			text : '申请扣款时间', // 申请扣款时间
	    			dataIndex : 'applyDeductTime',
	    			renderer : function(value) {
	    				if (!Ext.isEmpty(value)) {
	    					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	    				} else {
	    					return null;
	    				}
	    			}
	    		}, {
	    			text : '申请人', // 申请人名称/创建人名称
	    			dataIndex : 'applicantName'
	    		}, {
	    			dataIndex : 'applicantCode',//申请人编码/创建人编码
	    			hidden:true
	    		}, {
	    			text : '申请时间', // 申请时间/创建时间
	    			dataIndex : 'applyTime',
	    			width : 132,
	    			renderer : function(value) {
	    				if (!Ext.isEmpty(value)) {
	    					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	    				} else {
	    					return null;
	    				}
	    			}
	    		}, {
	    			text : ' 审核部门', //  审核部门名称
	    			dataIndex : 'checkOrgName'
	    		}, {
	    			dataIndex : 'checkOrgCode',//审核部门编码
	    			hidden:true
	    		}, {
	    			text : '审核人', // 审核人名称/修改人名称
	    			dataIndex : 'checkPersonName'
	    		}, {
	    			dataIndex : 'checkPersonCode', // 审核人编码
	    			hidden:true
	    		}, {
	    			text : '审核时间', // 审核时间/修改时间
	    			dataIndex : 'checkTime',
	    			width : 132,
	    			renderer : function(value) {
	    				if (!Ext.isEmpty(value)) {
	    					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	    				} else {
	    					return null;
	    				}
	    			}
	    		}, {
	    			text : '审核状态', // 审核状态,0：待审核；1：已审核；2：已拒绝；3：已失效
	    			dataIndex : 'checkStatus',
	    			renderer : function(value) {
	    				if (!Ext.isEmpty(value)) {
	    					return delayDeductRecordCheckStatus[value];
	    				} else {
	    					return null;
	    				}
	    			}
	    		}, {
	    			text : '扣款状态', // 扣款状态 
	    			dataIndex : 'flowStatus',
	    			renderer : function(value) {
	    				if (!Ext.isEmpty(value)) {
	    					return delayDeductRecordFlowStatus[value];
	    				} else {
	    					return null;
	    				}
	    			}
	    		}, {
	    			text : '审核意见', // 审核意见
	    			dataIndex : 'checkOpinion'
	    		}];
	me.dockedItems = [{
		xtype: 'toolbar',
		dock: 'top',
		layout: 'column',
		defaults: {
			margin: '0 10 0 0'
		},
		items: [
		{
			xtype: 'button',
			text: '审核',
			columnWidth: .08,
			//hidden: !Foss.baseinfo.dailyExpense.isPermission('dailyExpense/dailyExpenseExamineButton'),
			plugins: {
				ptype: 'buttondisabledplugin',
				seconds: 3
			},
			handler: function() {//审核按钮对应操作
				var selections = this.up('grid').getSelectionModel().getSelection(); //获取选中数据
				if (selections.length != 1) { //判断选中条数
					Ext.Msg.alert('请选中一条数据进行审核！');
					return;
				}
				var record = selections[0];//待审核数据
				if(record.get('checkStatus')!='0'){
					Ext.Msg.alert('当前运单的审核状态非待审核状态！');
					return;
				}
				var wind = me.getCheckWindow();
				wind.setTitle('延时扣款审核');
				 var form = wind.getCheckForm();
				 form.getForm().reset();//重置表单
				 //申请扣款时间
				 var applyDeductTime = Ext.Date.format(new Date(record.get('applyDeductTime')),'Y-m-d H:i:s');
				 //初始化审核界面数据
				 form.getForm().findField('id').setValue(record.get('id'));
				 form.getForm().findField('applyOrgName').setValue(record.get('applyOrgName'));
				 form.getForm().findField('applicantName').setValue(record.get('applicantName'));
				 form.getForm().findField('checkOrgName').setValue(record.get('checkOrgName'));
				 form.getForm().findField('checkOrgCode').setValue(record.get('checkOrgCode'));
				 form.getForm().findField('waybillNo').setValue(record.get('waybillNo'));				 
				 form.getForm().findField('applyDeductTime').setValue(applyDeductTime);
				 wind.show();
			},
			scope: this.up('grid')
		}]
	},
	{
		xtype : 'standardpaging',
		store : me.store,
		dock : 'bottom',
		plugins : {
			ptype : 'pagesizeplugin',
			// 超出输入最大限制是否提示，true则提示，默认不提示
			alertOperation : true,
			// 自定义分页comobo数据
			sizeArray : [ [ '10', 10 ], [ '20', 20 ], [ '50', 50 ],
					[ '100', 100 ] ],
			// 入最大限制，默认为200
			maximumSize : 200
		}
	
	}];
	//me.callParent();
	me.callParent([ cfg ]);
},
getMyListeners : function() {
	var me = this;
	return {
		// 增加滚动条事件，防止出现滚动条后却不能用
		scrollershow : function(scroller) {
			if (scroller && scroller.scrollEl) {
				scroller.clearManagedListeners();
				scroller.mon(scroller.scrollEl, 'scroll',
						scroller.onElScroll, scroller);
			}
		}
	};
},
//审核窗口
getCheckWindow : function() {
	if (this.checkWindow == null) {
		this.checkWindow = Ext.create('Foss.baseinfo.delayDeductRecord.checkDelayDeductGridWindow');
	}
	return this.checkWindow;
}
});
/**
 * ------------------------------------------初始化界面------------------------------------------------------------------------
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-delayDeductRecord_content')) {
		return;
	}
	//查询条件 表单from
	querydelayDeductForm = Ext.create('Foss.baseinfo.delayDeductRecord.QuerydelayDeductForm'); 
	//代收延迟扣款查询列表
	delayDeductGridPanel=Ext.create('Foss.baseinfo.delayDeductRecord.DelayDeductGridPanel');
	//面板
	Ext.create('Ext.panel.Panel',{ 
		id: 'T_baseinfo-delayDeductRecord_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm : function() {
			return querydelayDeductForm;
		},
		getdelayDeductQueryGrid:function(){
			return delayDeductGridPanel;
		},
		items: [querydelayDeductForm,delayDeductGridPanel],
		renderTo: 'T_baseinfo-delayDeductRecord-body'
	});	
});