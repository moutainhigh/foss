
/**
 * 设置日期的时间为 00:00:00
 */
order.specialDeliveryAddress.getTargetDate = function(date, day) {
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
order.specialDeliveryAddress.getTargetDate1 = function(date, day) {
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
 * 特殊送货地址Model
 * @Fields id 特殊送货地址的Id
 * @Fields deliveryAddress 送货地址
 * @Fields deliveryResidenceName 送货小区名称
 * @Fields deliveryResidenceCode 送货小区编码
 * @Fields addressType 特殊地址类型
 * @Fields vehicleNo 车牌号
 * @Fields vehicleDeptName 车所属部门名称
 * @Fields vehicleDeptCode 车所属部门编码
 * @Fields operatorCode 操作人编码
 * @Fields operatorName 操作人姓名
 * @Fields operateDate 操作日期
 * @Fields operateOrgCode 操作部门编码
 * @Fields operateOrgName 操作部门名称
 * @Fields createrCode 创建人编码
 * @Fields createrName 创建人名称
 * @Fields createDate 创建时间
 * @Fields createOrgCode 创建部门编码
 * @Fields createOrgName 创建部门名称
 */
Ext.define('Foss.order.specialDeliveryAddress.SpecialDeliveryAddressModel', {
	extend : 'Ext.data.Model',
	fields : ['id', 'deliveryAddress', 'deliveryResidenceName', 'deliveryResidenceCode', 'addressType', 
	          'vehicleNo', 'vehicleDeptName', 'vehicleDeptCode', 'operatorCode', 
              'operatorName', 'operateDate', 'operateOrgCode', 'operateOrgName',
              'createrCode', 'createrName', 'createDate', 'createOrgCode', 'createOrgName'
	          ]
});

/**
 * 特殊送货地址Store
 */
Ext.define('Foss.order.specialDeliveryAddress.SpecialDeliveryAddressStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.order.specialDeliveryAddress.SpecialDeliveryAddressModel',
	pageSize : 20,
	proxy: {
        type : 'ajax',
        url :  ContextPath.PKP_ORDER + '/order/querySpecialDeliveryAddress.action',
        actionMethods:{
            create: "POST", read: "POST", update: "POST", destroy: "POST"
        },
        reader: {
            type: 'json',
            root: 'deliveryAddressList',
            totalProperty : 'totalCount'
        }
    }
});

/**
 * 创建特殊送货地址Store
 */
var specialDeliveryAddressStore = Ext.create('Foss.order.specialDeliveryAddress.SpecialDeliveryAddressStore');

/**
 * 绑定车辆window
 */
Ext.define('Foss.order.specialDeliveryAddress.BindVehicleWindow', {
	extend : 'Ext.window.Window',
	title: order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.bindVehicleTitle'), /*车辆调整*/
	resizable : false,
	modal : true,
	width : 330,
	height : 160,
	items : [ {
		xtype : 'form',
		defaults: {
			labelWidth : 100,
			labelAlign : 'top',
			margin : '0 0 0 20px'
		},
		defaultType: 'textfield',
		items : [ {
			fieldLabel : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.vehicleNo'), /*车牌号*/
			xtype : 'commontruckselector',
			id : 'pkp_order_specialDeliveryAddress_vehicleNo_Id',
			anchor : '90%',
			allowBlank : false,
			showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{orgName}',
			name : 'vehicleNo',
			emptyText : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.vehicleNoEmptyText') /*车牌号+归属部门*/
		}, {
			xtype : 'hidden',
			name : 'ids'
		} ]
	} ],
	buttons : [ {
		cls : 'yellow_button',
		text :  order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.cancel'),/*取消*/
		handler : function(btn) {
			btn.up('window').close();
		}
	}, '->' , {
		cls : 'yellow_button',
		text : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.save'),/*保存*/
		handler : function(btn) {
			var win = btn.up('window');
			var form = win.down('form');
			if (!form.getForm().isValid()){
				return ;
			}
			var obj = {};
			obj['vehicleNo'] = form.down('commontruckselector').getValue();
			obj['vehicleDeptName'] = Ext.getCmp('pkp_order_specialDeliveryAddress_vehicleNo_Id').valueModels[0].data.orgName;
			obj['vehicleDeptCode'] = Ext.getCmp('pkp_order_specialDeliveryAddress_vehicleNo_Id').valueModels[0].data.orgId;
			addPrev(obj,'specialDeliveryAddressEntity');
			obj['ids'] = form.down('hidden').getValue();
			Ext.Ajax.request({
			    url: ContextPath.PKP_ORDER + '/order/bindVehicle.action',
			    params : obj,
			    success: function(response, opts) {
			        var obj = Ext.decode(response.responseText);
			        if(obj) {
			        	if(obj.success) {
			        		Ext.ux.Toast.msg(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'), 
			        				order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.bindVehicleSuccess'),
			        				'success', 3000);/*绑定车辆成功*/
			        		win.close();
			        		specialDeliveryAddressStore.load();
			        	} else {
			        		Ext.ux.Toast.msg(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'), obj.message, 'error', 3000);
			        	}
			        }
			    },
			    exception: function(response){
					var obj = Ext.decode(response.responseText);
          			Ext.ux.Toast.msg(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'), obj.message, 'error', 3000);
				}
			});
		}
	} ]
});

/**
 * 特殊送货地址查询Form
 */
Ext.define('Foss.order.specialDeliveryAddress.SpecialDeliveryAddressForm', {
	extend : 'Ext.form.Panel',
	title : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.searchCriteria'),/*查询条件*/
	frame :  true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	collapsible : true,
	animCollapse : true,
	layout : {
		type : 'column'
	},
	defaults : {
		xtype : 'textfield',
		margin : 5,
		labelWidth : 100
	},
	items : [{
		fieldLabel : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.deliveryAddress'),/*送货地址*/
		columnWidth : .3,
		name : 'deliveryAddress'
	}, {
		fieldLabel :  order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.operatorName'),/*操作人*/
		columnWidth : .3,
		name : 'operatorName'
	}, {
		fieldLabel : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.vehicleNo'),/*车牌号*/
		columnWidth : .4,
		xtype : 'commontruckselector',
		name : 'vehicleNo',
		showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{orgName}',
	}, {
		xtype : 'commonsmallzoneselector',
		name : 'deliveryResidenceCode',
		regionType : 'DE',
		columnWidth : .3,
		fieldLabel : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.deliveryResidence'),/*送货小区*/
	}, {
		xtype : 'combobox',
		name : 'addressType',
		columnWidth : .3,
		fieldLabel : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.specialAddressType'),/*特殊送货地址类型*/
		queryModel : 'local',
		displayField : 'valueName',
		valueField : 'valueCode',
		editable : false,
		labelWidth : 120,
		value:'',
		store : FossDataDictionary.getDataDictionaryStore('PKP_SPECIAL_DELIVERYADDRESS_TYPE', null, {
				'valueCode' : '',
				'valueName' : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.all')
			})
	}, {
		xtype : 'rangeDateField',
		fieldId : 'Foss_order_SpecialDeliveryAddressForm_specialDeliveryAddress_ID',
		fieldLabel : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.searchTime'),/*查询时间*/
		dateType : 'datetimefield_date97',
		fromName : 'operateDateStart',
		toName : 'operateDateEnd',
		editable : false,
		disallowBlank:true,
		dateRange : 30,
		allowBlank : false,
		fromValue: Ext.Date.format(order.specialDeliveryAddress.getTargetDate(new Date(),-29),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(order.specialDeliveryAddress.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth : .4,
		labelWidth : 60
	}, {
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.reset'),/*重置*/
			columnWidth : .08,
			handler : function() {
				var myform = this.up('form').getForm();
				myform.reset();
				myform.findField('operateDateStart').setValue(Ext.Date.format(order.specialDeliveryAddress.getTargetDate(new Date(),-29),'Y-m-d H:i:s'));
				myform.findField('operateDateEnd').setValue(Ext.Date.format(order.specialDeliveryAddress.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
			}
		}, {
			xtype : 'container',
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			text : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.search'),/*查询*/
			cls : 'yellow_button',
			disabled:!order.specialDeliveryAddress.isPermission('specialDeliveryAddressIndex/querySpecialDeliveryAddressButton'),
			hidden:!order.specialDeliveryAddress.isPermission('specialDeliveryAddressIndex/querySpecialDeliveryAddressButton'),
			columnWidth : .08,
			handler : function(btn) {
				var form = btn.up('form').getForm();
				if (!form.isValid()) {
					return;
				}
				var formValue = form.getValues();
				addPrev(formValue, 'specialDeliveryAddressVo');
				specialDeliveryAddressStore.proxy.extraParams = formValue;
				specialDeliveryAddressStore.load();
			}
		} ]
	}]
});



Ext.define('Foss.order.specialDeliveryAddress.SpecialDeliveryAddressGrid',{
	extend : 'Ext.grid.Panel',
	title : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.addressInfo'),/*地址信息*/
	frame : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	collapsible : true,
	animCollapse : true,
	store : specialDeliveryAddressStore,
	emptyText : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.addressInfoEmptyText'),/*查询结果为空*/
	selModel :  Ext.create('Ext.selection.CheckboxModel'),
	columns : [ {
		xtype : 'actioncolumn',
		width : 60,
		align : 'center',
		items : [ {
			iconCls : 'foss_icons_bse_applyReturn',
			tooltip : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.delete'),/*作废*/
			handler : function(grid, rowIndex, colIndex) {
				var rec = grid.getStore().getAt(rowIndex);
				var id = "";
				if(rec.data.id) {
					id = rec.data.id;
				}
 				Ext.Msg.show({
				     title: order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'),
				     msg: order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.deleteSureMsg'),/*确定要作废该特殊送货地址吗？*/
				     buttons: Ext.Msg.OKCANCEL,
				     icon: Ext.Msg.QUESTION,
				     fn : function (btnText) {
				    	 if (btnText == 'ok'){
				    		 Ext.Ajax.request({
								    url: ContextPath.PKP_ORDER + '/order/deleteSpecialDeliveryAddress.action',
								    params : {
								    	id : id
								    },
								    success: function(response, opts) {
								    	var obj = Ext.decode(response.responseText);
								        if(obj) {
								        	if(obj.success) {
								        		grid.getStore().load();
								        		Ext.ux.Toast.msg(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'),
								        				order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.deleteSuccess'), 'success', 3000);/*删除成功*/
								        	} else {
								        		Ext.ux.Toast.msg(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'), obj.message, 'error', 3000);
								        	}
								        }
								    },
								    exception: function(response){
										var obj = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'), obj.message, 'error', 3000);
									}
								});
				    	 }
				     }
				});
			}
		} ]
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.deliveryAddress'),/*送货地址*/
		dataIndex : 'deliveryAddress',
		xtype: 'ellipsiscolumn',
		width : 200
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.addressType'),/*地址类型*/
		dataIndex : 'addressType',
		width : 120,
		renderer : function(value) {
			return FossDataDictionary.rendererSubmitToDisplay(
					value, 'PKP_SPECIAL_DELIVERYADDRESS_TYPE');
		}
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.deliveryResidence'),/*送货小区*/
		dataIndex : 'deliveryResidenceName',
		width : 120
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.operatorName'),/*操作人*/
		dataIndex : 'operatorName',
		width : 120
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.operateOrgName'),/*操作部门*/
		dataIndex : 'operateOrgName',
		width : 120
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.operateDate'),/*操作日期*/
		dataIndex : 'operateDate',
		width : 120,
		renderer:function(value){
			if(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}else{
				return '';
			}
		} 
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.deliveryVehicle'),/*送货车辆*/
		dataIndex : 'vehicleNo',
		width : 120
	}, {
		header : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.vehicleDeptName'),/*车辆归属部门*/
		dataIndex : 'vehicleDeptName',
		width : 120
	} ],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'bottom',
		layout : 'column',
		defaults : {
			margin : '0 0 5 3'
		},
		items : [ {
			xtype : 'button',
			text : order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.bindVehicle'),/*绑定车辆*/
			handler : function(btn) {
				var grid = btn.up('grid');
				var rowSelectionModel = grid.getSelectionModel();
				if (rowSelectionModel.hasSelection()) {  
			        var records = rowSelectionModel.getSelection();
			        var ids = [];
			        var obj = {};
			        Ext.Array.each(records, function(record, index, countriesItSelf) {
			        	obj[record.get('deliveryResidenceCode')] = true;
			        	ids.push(record.get('id'));
			        });
			        var count = 0;
			        for (var attr in obj){
			        	count++ ;
			        }
			        if (count > 1) {
			        	Ext.Msg.confirm(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'),
			        			order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.bindVehicleTipMsg')
			        			/*选中数据中存在不同小区名称，是否继续操作？*/,function(btn){
			        		if (btn == 'yes') {
			        			var win = Ext.create('Foss.order.specialDeliveryAddress.BindVehicleWindow').show();
						        win.down('hidden').setValue(ids.join(','));
			        		}
			        	});
			        } else {
			        	var win = Ext.create('Foss.order.specialDeliveryAddress.BindVehicleWindow').show();
				        win.down('hidden').setValue(ids.join(','));
			        }
			    } else {
			    	Ext.ux.Toast.msg(order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.tip'),
			    			order.specialDeliveryAddress.i18n('pkp.order.specialDeliveryAddress.bindVehicleTipMsg1')
			    			/*至少选中一个地址进行绑定*/, 'success', 3000);
			        return ; 
			    }
				
			}
		} ]
	} ],
	bbar : Ext.create('Deppon.StandardPaging',{
		store : specialDeliveryAddressStore,
		displayInfo : true,
		plugins : Ext.create('Deppon.ux.PageSizePlugin', {
			sizeList : [['20', 20], ['50', 50], ['100', 100], ['200', 200]]
		})
	}) 
});

Ext.onReady(function () {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id : 'T_order-specialDeliveryAddressIndex_content',
		cls: 'panelContentNToolbar',
		bodyCls: 'panelContentNToolbar-body',
		items: [Ext.create('Foss.order.specialDeliveryAddress.SpecialDeliveryAddressForm'),Ext.create('Foss.order.specialDeliveryAddress.SpecialDeliveryAddressGrid')],
		renderTo: 'T_order-specialDeliveryAddressIndex-body'
	});
});