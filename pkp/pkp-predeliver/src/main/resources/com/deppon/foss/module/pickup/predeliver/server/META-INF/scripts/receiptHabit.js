/**
 * 收货习惯Model
 * @Fields customerCode 客户编码
 * @Fields customerName 客户名称
 * @Fields customerMobilePhone 客户手机号码
 * @Fields customerPhone 客户电话号码
 * @Fields customerContactName  客户联系人
 * @Fields deliveryTimeInterval 送货时间段
 * @Fields deliveryTimeStart 送货时间点（开始）
 * @Fields deliveryTimeOver 送货时间点 （结束）
 * @Fields invoiceType 发票类型
 * @Fields invoiceDetail 发票类型描述
 * @Fields receiptHabitRemark 收货习惯备注
 * @Fields operatorName 操作人姓名
 * @Fields operateOrgCode 创建部门编码
 * @Fields operateOrgName 创建部门名称
 * @Fields createrName 创建人名称
 * @Fields id 收货习惯的Id
 * @Fields createDate 创建时间
 * @Fields createUser 创建人编码
 * @Fields modifyDate 修改时间
 * @Fields modifyUser 修改人编码
 */
Ext.define('Foss.predeliver.receiptHabit.CustomerReceiptHabitModel', {
	extend : 'Ext.data.Model',
	fields : ['customerCode', 'customerName', 'customerMobilePhone', 
	      	'customerPhone', 'customerContactName', 'deliveryTimeInterval',
	      	'deliveryTimeStart', 'deliveryTimeOver', 'invoiceType', 'invoiceDetail',
	      	'receiptHabitRemark', 'operatorName', 'operateOrgCode', 'operateOrgName', 
	      	'createrName', 'id', 'createDate', 'createUser', 'modifyDate', 'modifyUser'
	      	]
});

/**
 * 收货习惯Store
 */
Ext.define('Foss.predeliver.receiptHabit.CustomerReceiptHabitStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.predeliver.receiptHabit.CustomerReceiptHabitModel',
	pageSize : 30,
	proxy: {
        type : 'ajax',
        url :  predeliver.realPath('queryReceiptHabit.action'), 
        actionMethods:{
            create: "POST", read: "POST", update: "POST", destroy: "POST"
        },
        reader: {
            type: 'json',
            root: 'customerReceiptHabitList',
            totalProperty : 'totalCount'
        }
    }
});

/**
 * 设置日期的时间为 00:00:00
 */
predeliver.receiptHabit.getTargetDate = function(date, day) {
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

/**
 * 设置日期的时间为23:59:59
 */
predeliver.receiptHabit.getTargetDate1 = function(date, day) {
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

/**
 * 为对象属性添加前缀
 * @param obj 传入对象
 * @param prevName 前缀名称
 * @returns
 */
function addPrev(obj, prevName){
	if (Ext.isObject(obj)){
		for (var attr in obj){
			var keyName = prevName + '.' + attr;
			obj[keyName] = obj[attr];
			delete obj[attr];
		}
	} 
}
 
/**
 * 创建收货习惯Store
 */
var customerReceiptHabitStore = Ext.create('Foss.predeliver.receiptHabit.CustomerReceiptHabitStore');

/**
 * 收货习惯时间段Store
 */
var receiptHabitDeliveryTimeIntervalStore =  Ext.create('Ext.data.Store', {
	fields : ['name'],
	data : [ 
	         {name : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.allDay')},/*全天*/
	         {name : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.forenoon')},/*上午*/
	         {name : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.afternoon')}/*下午*/
	       ]
});

/**
 * 收货习惯查询条件Form
 */
Ext.define('Foss.predeliver.receiptHabit.QueryConditionForm', {
	extend :'Ext.form.Panel' ,
	title : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.searchCriteria'), 
	frame : true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 20 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: {
	    type : 'table',
	    columns : 3
	},
	items : [{
		fieldLabel: predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerName'), /*收货客户名称*/
		xtype : 'commoncustomerselector',
		all : 'true',
		singlePeopleFlag : 'Y',
		isPaging : true, 
		listWidth : 300,
		name : 'customerCode'	
	}, {
		fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerMobilePhone'),/* 联系人手机*/
		regex : /^\d+$/,
		regexText : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.mobilePhoneRegexText'), /* 只可以填入数字*/
		name : 'customerMobilePhone'
	}, {
		fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerPhone'), /* 联系人电话*/
		regex : /^[\d\-]+$/,
		regexText : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.phoneRegexText'), /* 只可以填入数字和-*/
		name : 'customerPhone'
	}, {
		fieldLabel  : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerContactName'),/*收货联系人*/
		maxLength : 15,
		name :　'customerContactName'
	}, {
		fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deliveryTimeInterval'),/* 送货时间段*/
		xtype : 'combobox',
		queryMode : 'local',
	    displayField : 'name',
	    valueField : 'name',
	    editable:false,
	    name : 'deliveryTimeInterval',
		store :receiptHabitDeliveryTimeIntervalStore
	}, {
		xtype : 'container',
		layout : 'hbox',
		width : 300,
		items : [ {
	        xtype: 'timefield',
	        name: 'deliveryTimeStart',
	        fieldLabel: predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deliveryTimePoint') ,/*送货时间点,*/
	        width : 180,
	        increment: 30,
	        submitFormat : 'H:i',
	        format : 'H:i',
	        listeners : {
	        	blur : function (timefield) {
	        		 var val = timefield.getValue();
	        		 if (val) {
	        			 timefield.nextSibling().allowBlank = false;
	        		 } else if (!val && !timefield.nextSibling().getValue()) {
						 timefield.reset();
						 timefield.nextSibling().reset();
						 timefield.allowBlank = true;
						 timefield.nextSibling().allowBlank = true;
					 }else {
	        			 timefield.nextSibling().allowBlank = true;
	        		 }
	        	 },
	        	 select : function(combo, records, eOpts) {
	        		 var val = combo.getValue() ;
	        		 if (val) {
	        			 combo.nextSibling().setMinValue(val);
	        		 }
	        	 }
	        }
	    }, {
	        xtype: 'timefield',
	        name: 'deliveryTimeOver',
	        fieldLabel:predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.to'), /* 至*/
	        labelWidth : 20,
	        width : 100,
	        labelSeparator : '', 
	        increment: 30,
	        submitFormat : 'H:i',
	        format : 'H:i',
	        listeners : {
	        	blur : function (timefield) {
	        		 var val = timefield.getValue();
	        		 if (val) {
	        			 timefield.previousSibling().allowBlank = false;
	        		 } else if (!val && !timefield.previousSibling().getValue()) {
						 timefield.reset();
						 timefield.previousSibling().reset();
						 timefield.allowBlank = true;
						 timefield.previousSibling().allowBlank = true;
					 }else {
	        			 timefield.previousSibling().allowBlank = true;
	        		 }
	        	 },
	        	 select : function(combo, records, eOpts) {
	        		 var val = combo.getValue() ;
	        		 if (val) {
	        			 combo.previousSibling().setMaxValue(val);
	        		 }
	        	 }
	        }
	   } ]
	}, {
		xtype : 'combobox',
		fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.invoiceType') ,/*发票类型*/
		name : 'invoiceType',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		editable:false,
		value:'N/A',
		store:FossDataDictionary.getDataDictionaryStore('PKP_RECEIPT_INVOICE_TYPE',null, [ {
			'valueCode': '',
            'valueName': predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.none')
		}, {
			'valueCode': 'N/A',
            'valueName': predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.all')
		} ])
	}, {
		colspan: 2,
		xtype: 'rangeDateField',
		fieldLabel:  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.modifyDate'), /* 操作时间*/
		fieldId : 'Foss_predeliver_receiptHabit_ReceiptHabitTime_Id',
		dateType: 'datetimefield_date97',
		fromName: 'operateDateStart',
		toName: 'operateDateEnd',
		disallowBlank:true,
		editable:false,
		dateRange : 30,
		width : 500,
		fromValue: Ext.Date.format(predeliver.receiptHabit.getTargetDate(new Date(),-29),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(predeliver.receiptHabit.getTargetDate1(new Date(),0),'Y-m-d H:i:s')
	}, {
		colspan: 2,
		xtype : 'button',
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.reset') ,/* 重置*/
		width : 100,
		handler : function (btn) {
			var formPanel = btn.up('form');
			formPanel.down('timefield[name=deliveryTimeStart]').allowBlank = true;
			formPanel.down('timefield[name=deliveryTimeOver]').allowBlank = true;
			var form = formPanel.getForm();
			form.reset();
			form.findField('operateDateStart').setValue(Ext.Date.format(predeliver.receiptHabit.getTargetDate(new Date(),-29),'Y-m-d H:i:s'));
			form.findField('operateDateEnd').setValue(Ext.Date.format(predeliver.receiptHabit.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
		}
	}, {
		xtype : 'button',
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.search') ,/* 查询*/
		disabled:!predeliver.receiptHabit.isPermission('receiptHabitIndex/queryReceiptHabitButton'),
		hidden:!predeliver.receiptHabit.isPermission('receiptHabitIndex/queryReceiptHabitButton'),
		width : 100,
		margin : '0 0 0 100',
		handler : function (btn) {
			var form = btn.up('form').getForm();
			if (!form.isValid()){
				return ;
			}
			var values = form.getValues();
			addPrev(values,'customerReceiptHabitVo');
			var grid = Ext.getCmp('T_predeliver-receiptHabitIndex_content').down('grid');
			var store = grid.getStore();
			store.proxy.extraParams = values;
			store.load();
			grid.down('standardpaging').bind(store);
		}
	} ]
});

/**
 * 添加收货习惯Window
 */
Ext.define('Foss.predeliver.receiptHabit.AddReceiptHabitWindow',{
	extend : 'Ext.window.Window',
	title : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.addReceiptHabit') ,/* 新增客户收货习惯*/
	modal : true,
	resizable : false,
	items : [{
		xtype : 'form',
		defaults: {
			margin: '5 20 5 10',
			labelWidth: 90
		},
		defaultType: 'textfield',
		layout: {
		    type : 'table',
		    columns : 2
		},
		items : [ {
			fieldLabel:  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerName'), /*收货客户名称*/
			xtype : 'commoncustomerselector',
			all : 'true',
			singlePeopleFlag : 'Y',
			isPaging : true, 
			listWidth : 300,
			allowBlank : false,
			name : 'customerCode'	
		}, {
			fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerMobilePhone'),/* 联系人手机*/
			regex : /^\d+$/,
			regexText : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.mobilePhoneRegexText'), /* 只可以填入数字*/
			name : 'customerMobilePhone'
		}, {
			fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerPhone'), /* 联系人电话*/
			regex : /^[\d\-]+$/,
			regexText : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.phoneRegexText'), /* 只可以填入数字和-*/
			name : 'customerPhone'
		}, {
			fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerContactName'),/*收货联系人*/
			allowBlank : false,
			maxLength : 15,
			name : 'customerContactName'
		}, {
			fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deliveryTimeInterval'),/* 送货时间段*/
			xtype : 'combobox',
			queryMode : 'local',
		    displayField : 'name',
		    valueField : 'name',
		    editable:false,
		    name : 'deliveryTimeInterval',
		    value : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.allDay'),
			store : receiptHabitDeliveryTimeIntervalStore
		}, {
			xtype : 'container',
			layout : 'hbox',
			width : 300,
			items : [ {
		        xtype: 'timefield',
		        name: 'deliveryTimeStart',
		        fieldLabel: predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deliveryTimePoint') ,/*送货时间点,*/
		        labelWidth : 90,
		        width : 180,
		        increment: 30,
		        submitFormat : 'H:i',
		        format : 'H:i',
		        listeners : {
		        	blur : function (timefield) {
		        		 var val = timefield.getValue();
		        		 if (val) {
		        			 timefield.nextSibling().allowBlank = false;
		        		 } else if (!val && !timefield.nextSibling().getValue()) {
							 timefield.reset();
							 timefield.nextSibling().reset();
							 timefield.allowBlank = true;
							 timefield.nextSibling().allowBlank = true;
						 } else {
		        			 timefield.nextSibling().allowBlank = true;
		        		 }
		        	 },
		        	 select : function(combo, records, eOpts) {
		        		 var val = combo.getValue() ;
		        		 if (val) {
		        			 combo.nextSibling().setMinValue(val);
		        		 }
		        	 }
		        }
		    }, {
		        xtype: 'timefield',
		        name: 'deliveryTimeOver',
		        fieldLabel: predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.to'), /* 至*/
		        labelWidth : 20,
		        width : 100,
		        labelSeparator : '', 
		        increment: 30,
		        submitFormat : 'H:i',
		        format : 'H:i',
		        listeners : {
		        	 blur : function (timefield) {
		        		 var val = timefield.getValue();
		        		 if (val) {
		        			 timefield.previousSibling().allowBlank = false;
		        		 } else if (!val && !timefield.previousSibling().getValue()) {
							 timefield.reset();
							 timefield.previousSibling().reset();
							 timefield.allowBlank = true;
							 timefield.previousSibling().allowBlank = true;
						 } else {
		        			 timefield.previousSibling().allowBlank = true;
		        		 }
		        	 },
		        	 select : function(combo, records, eOpts) {
		        		 var val = combo.getValue() ;
		        		 if (val) {
		        			 combo.previousSibling().setMaxValue(val);
		        		 }
		        	 }
		        }
		   } ]
		},{
			xtype : 'combobox',
			labelWidth : 90,
			fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.invoiceType') ,/*发票类型*/
			name : 'invoiceType',
			displayField:'valueName',
			valueField:'valueCode',
			triggerAction:'all',
			queryMode:'local',
			editable:false,
			value:'',
			store:FossDataDictionary.getDataDictionaryStore('PKP_RECEIPT_INVOICE_TYPE',null,{
				'valueCode': '',
	            'valueName': predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.none')
			})
		},{
			fieldLabel :  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.invoiceDetail'), /* 发票类型备注*/
			xtype : 'textfield',
			maxLength : 30,
			name : 'invoiceDetail'
		}, {
			colspan: 2,
			xtype : 'textarea',
			width : 580,
			maxLength : 80,
			name: 'receiptHabitRemark',
			allowBlank : true,
			fieldLabel : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.receiptHabitRemark') /*收货习惯备注*/
		}, {
			xtype : 'hidden',name : 'id'
		} ]
	}],
	buttons : [ {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.back'), /* 返回*/
		handler : function (btn) {
			var win = btn.up('window');
			win.close();
		}
	},  '->' , {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.save'), /*保存*/
		handler : function (btn) {
			var win = btn.up('window');
			var form = win.down('form');
			if (!form.getForm().isValid()){
				return ;
			}
			var values = form.getForm().getValues();
			if (values.customerPhone == '' && values.customerMobilePhone == '') {
				Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), 
						predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.addReceiptHabitPhoneMsg'), 
						'success', 3000);/*联系人手机和联系人电话至少有一个不为空*/
				return ;
			}
			if (!values.id) {
				values['customerName'] = form.down('commoncustomerselector').getRawValue();
				addPrev(values,'customerReceiptHabitEntity');
				Ext.Ajax.request({
				    url: predeliver.realPath('addReceiptHabit.action'),
				    params : values,
				    success: function(response, opts) {
				        var obj = Ext.decode(response.responseText);
				        if(obj) {
				        	if(obj.success) {
				        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'),
				        				predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.addReceiptHabitSuccess')
				        		, 'success', 3000);/*添加成功 */
				        		win.close();
				    			var grid = Ext.getCmp('T_predeliver-receiptHabitIndex_content').down('grid');
				    			var form = Ext.getCmp('T_predeliver-receiptHabitIndex_content').down('form').getForm();
				    			var values = form.getValues();
				    			addPrev(values,'customerReceiptHabitVo');
				    			var store = grid.getStore();
				    			store.proxy.extraParams = values;
				    			store.load();
				    			grid.down('standardpaging').bind(store);
				        	} else {
				        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
				        	}
				        }
				    },
				    exception: function(response){
						var obj = Ext.decode(response.responseText);
              			Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
					}
				});
			} else {
				addPrev(values,'customerReceiptHabitEntity');
				Ext.Ajax.request({
				    url: predeliver.realPath('updateReceiptHabit.action'),
				    params : values,
				    success: function(response, opts) {
				        var obj = Ext.decode(response.responseText);
				        if(obj) {
				        	if(obj.success) {
				        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'),
				        				predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.editReceiptHabitSuccess')

				        				, 'success', 3000);/*修改成功*/
				        		win.close();
				        		customerReceiptHabitStore.load();
				        	} else {
				        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
				        	}
				        }
				    },
				    exception: function(response){
						var obj = Ext.decode(response.responseText);
              			Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
					}
				});
			}
		}
	} ]
});

/**
 * 收货习惯Grid
 */
Ext.define('Foss.predeliver.receiptHabit.ReceiptHabitGrid', {
	extend : 'Ext.grid.Panel',
	title : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.searchResultList'), /* 查询结果列表*/
	frame :  true,
	columnLines : true,
	store : customerReceiptHabitStore,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns : [ {
		text :  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.operate'),/* 操作*/
		xtype : 'actioncolumn',
		align: 'center',
		items : [ {
			iconCls: 'deppon_icons_edit',
			tooltip :  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.edit'), /* 修改*/
			disabled:!predeliver.receiptHabit.isPermission('receiptHabitIndex/editReceiptHabitButton'),
			handler: function(grid, rowIndex, colIndex) {
				var rec = grid.getStore().getAt(rowIndex);
				var win = Ext.create('Foss.predeliver.receiptHabit.AddReceiptHabitWindow').show();
				win.setTitle(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.editReceiptHabit'));/* 修改客户收货习惯*/
				var form = win.down('form');
				form.loadRecord(rec);
				if(rec.data.invoiceType == null) {
					form.down('combobox[name=invoiceType]').setValue('');
				}
				form.down('commoncustomerselector').setRawValue(rec.data.customerName);
				form.down('commoncustomerselector').setReadOnly(true);
				form.down('textfield[name=customerMobilePhone]').setReadOnly(true);
				form.down('textfield[name=customerPhone]').setReadOnly(true);
				form.down('textfield[name=customerContactName]').setReadOnly(true);
				form.down('commoncustomerselector').allowBlank = true;
				form.down('textfield[name=customerContactName]').allowBlank= true;
			}
		}, {
			iconCls : 'deppon_icons_cancel',
			tooltip : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.delete'), /* 作废*/
			disabled:!predeliver.receiptHabit.isPermission('receiptHabitIndex/deleteReceiptHabitButton'),
			handler: function(grid, rowIndex, colIndex) {
				var rec = grid.getStore().getAt(rowIndex);
				var id = "";
				if(rec.data.id) {
					id = rec.data.id;
				}
 				Ext.Msg.show({
				     title:predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'),
				     msg: predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deleteReceiptHabitSure'), /*'确定要作废该客户收货习惯吗？'*/
				     buttons: Ext.Msg.OKCANCEL,
				     icon: Ext.Msg.QUESTION,
				     fn : function (btnText) {
				    	 if (btnText == 'ok'){
				    		 Ext.Ajax.request({
								    url:predeliver.realPath('deleteReceiptHabit.action'),
								    params : {
								    	id : id
								    },
								    success: function(response, opts) {
								    	var obj = Ext.decode(response.responseText);
								        if(obj) {
								        	if(obj.success) {
								        		grid.getStore().load();
								        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'),
								        				predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deleteReceiptHabitSuccess')
								        				, 'success', 3000);/*删除成功 */
								        	} else {
								        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
								        	}
								        }
								    },
								    exception: function(response){
										var obj = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
									}
								});
				    	 }
				     }
				});
			}
		}, {
			iconCls : 'deppon_icons_showdetail',
			tooltip : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.check'), /* 查看*/
			handler: function(grid, rowIndex, colIndex) {
				var rec = grid.getStore().getAt(rowIndex);
				var win = Ext.create('Foss.predeliver.receiptHabit.AddReceiptHabitWindow', {
					title : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.checkReceiptHabit'),/* 查看客户收货习惯*/
					buttons : [{
						text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.back'),/*返回*/
						handler : function (btn) {
							var win = btn.up('window');
							win.close();
						}
					}]
				}).show();
			
				var form = win.down('form');
				form.loadRecord(rec);
				if(rec.data.invoiceType == null) {
					form.down('combobox[name=invoiceType]').setValue('');
				}
				form.down('commoncustomerselector').setRawValue(rec.data.customerName);
				form.down('commoncustomerselector').setReadOnly(true);
				form.down('textfield[name=customerMobilePhone]').setReadOnly(true);
				form.down('textfield[name=customerPhone]').setReadOnly(true);
				form.down('textfield[name=customerContactName]').setReadOnly(true);
				form.down('combo[name=deliveryTimeInterval]').setReadOnly(true);
				form.down('timefield[name=deliveryTimeStart]').setReadOnly(true);
				form.down('timefield[name=deliveryTimeOver]').setReadOnly(true);
				form.down('combo[name=invoiceType]').setReadOnly(true);
				form.down('textfield[name=invoiceDetail]').setReadOnly(true);
				form.down('textarea').setReadOnly(true);
			}
		} ]
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerName'), /*收货客户名称*/
		dataIndex : 'customerName'
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerMobilePhone'),/* 联系人手机*/
		dataIndex : 'customerMobilePhone'
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerPhone'), /* 联系人电话*/
		dataIndex : 'customerPhone'
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.customerContactName'),/*收货联系人*/
		dataIndex : 'customerContactName'
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deliveryTimeInterval'),/* 送货时间段*/
		dataIndex : 'deliveryTimeInterval'
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deliveryTimePoint') ,/*送货时间点*/
		xtype : 'templatecolumn',
		tpl :  new Ext.XTemplate(
			        '<tpl if="this.isNull(deliveryTimeStart)">',
			            '<p>{deliveryTimeStart} - {deliveryTimeOver}</p>',
			        '<tpl else>',
			            '<p></p>',
			        '</tpl>',
			    {
			        /*XTemplate 配置：*/
			        disableFormats: true,
			        /* 成员函数:*/
			        isNull: function(deliveryTimeStart){
			        	if (deliveryTimeStart == null || deliveryTimeStart == ''){
			        		return false;
			        	} else {
			        		return true;
			        	}
			        }
			    }
			)
		
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.invoiceType') ,/*发票类型*/
		dataIndex : 'invoiceType',
		renderer : function(value) {
			if (value == null || value == '') {
				return predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.none');
			}
			return FossDataDictionary.rendererSubmitToDisplay(
					value, 'PKP_RECEIPT_INVOICE_TYPE');
		}
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.invoiceDetail'), /* 发票类型备注*/
		dataIndex : 'invoiceDetail'
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.receiptHabitRemark'), /*收货习惯备注*/
		dataIndex : 'receiptHabitRemark'
	}, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.operatorName') ,/* 操作人*/
		dataIndex : 'operatorName'
	}, {
		text :  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.modifyDate') ,/*操作时间*/
		dataIndex : 'modifyDate',
		renderer:function(value){
			if(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return '';
			}
		} 
	} ],
	tbar : [ {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.add') ,/* 新增*/
		disabled:!predeliver.receiptHabit.isPermission('receiptHabitIndex/addReceiptHabitButton'),
		hidden:!predeliver.receiptHabit.isPermission('receiptHabitIndex/addReceiptHabitButton'),
		handler : function (btn) {
			Ext.create('Foss.predeliver.receiptHabit.AddReceiptHabitWindow').show();
		}
	}, {
		text :  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.delete') ,/* 作废*/
		disabled:!predeliver.receiptHabit.isPermission('receiptHabitIndex/deleteReceiptHabitButton'),
		hidden:!predeliver.receiptHabit.isPermission('receiptHabitIndex/deleteReceiptHabitButton'),
		handler : function (btn) {
			var grid = btn.up('grid');
			var rowSelectionModel = grid.getSelectionModel();
			if (rowSelectionModel.hasSelection()) {  
		        var records = rowSelectionModel.getSelection();
		        var ids = [];
		        Ext.Array.each(records, function(record, index, countriesItSelf) {
		        	ids.push(record.get('id'));
		        });
		        
		        Ext.Msg.show({
				     title:predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'),
				     msg:  predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deleteReceiptHabitSure'), /*'确定要作废该客户收货习惯吗？'*/
				     buttons: Ext.Msg.OKCANCEL,
				     icon: Ext.Msg.QUESTION,
				     fn : function (btnText) {
				    	 if (btnText == 'ok'){
				    		 Ext.Ajax.request({
								    url: predeliver.realPath('deleteReceiptHabitList.action'),
								    params : {
								    	ids : ids.join(',')
								    },
								    success: function(response, opts) {
								    	var obj = Ext.decode(response.responseText);
								        if(obj) {
								        	if(obj.success) {
								        		grid.getStore().load();
								        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), 
								        				predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deleteReceiptHabitSuccess')
								        				, 'success', 3000);/* 删除成功*/
								        	} else {
								        		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
								        	}
								        }
								    },
								    exception: function(response){
										var obj = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), obj.message, 'error', 3000);
									}
								});
				    	 }
				     }
				});
		    } else {  
		    	Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'),
		    			predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.deleteReceiptHabitMsg')
		    			, 'success', 3000);/*"至少选中一行数据进行删除"*/
		        return ;  
		    }  
		}
	}, '->' /*, {
		text : predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.export') ,// 导出
		disabled:!predeliver.receiptHabit.isPermission('receiptHabitIndex/exportReceiptHabitButton'),
		hidden:!predeliver.receiptHabit.isPermission('receiptHabitIndex/exportReceiptHabitButton'),
		handler : function (btn) {
			var form = Ext.getCmp('T_predeliver-receiptHabitIndex_content').down('form');
			var values = form.getValues();
			addPrev(values,'customerReceiptHabitVo');
			if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}	
			Ext.Ajax.request({
			    url: predeliver.realPath('exportReceiptHabit.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    params : values,
			    success: function(response, opts) {
			    	var obj = Ext.decode(response.responseText);
			    	if (obj.info.flag) {
			    		Ext.ux.Toast.msg(predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.tip'), 
			    				
			    				predeliver.receiptHabit.i18n('pkp.predeliver.receiptHabit.exportReceiptHabitMsg')
			    				, 'success', 3000);//"查询结果为空,尝试点击查询查看列表是否有值"
			    	}
			    },
				isUpload: true
			});
		}
	}*/ ],
	bbar : Ext.create('Deppon.StandardPaging',{
		store:customerReceiptHabitStore,
		displayInfo: true,
		plugins : Ext.create('Deppon.ux.PageSizePlugin', {
			sizeList : [['30', 30], ['50', 50], ['100', 100], ['200', 200]]
		})
	})
});

Ext.onReady(function () {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id : 'T_predeliver-receiptHabitIndex_content',
		cls: 'panelContentNToolbar',
		bodyCls: 'panelContentNToolbar-body',
		items: [Ext.create('Foss.predeliver.receiptHabit.QueryConditionForm'), Ext.create('Foss.predeliver.receiptHabit.ReceiptHabitGrid')],
		renderTo: 'T_predeliver-receiptHabitIndex-body'
	});
});
