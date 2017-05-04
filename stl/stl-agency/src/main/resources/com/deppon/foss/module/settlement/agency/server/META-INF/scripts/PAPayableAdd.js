/**
 * 提示信息
 * 
 * @param {}
 *            message
 * @param {}
 *            yesFn
 * @param {}
 *            noFn
 */
agency.PAPayableAdd.billPAPayableAddConfirmAlert = function(message, yesFn, noFn) {
	Ext.Msg.confirm(agency.PAPayableAdd.i18n('foss.stl.agency.common.alert'), message, function(o) {
			if (o == 'yes') {
				yesFn();
			} else {
				noFn();
			}
		});
};

/**
 * 新增快递代理其他应付单信息
 * 
 * @returns {Boolean}
 */
agency.PAPayableAdd.addBillPayable = function(form, me) {
	var waybillNo = Ext.String.trim(form.findField('waybillNo').getValue());
	var customerDetialCode = form.findField('customerDetial').getValue();
	var customerDetialName = form.findField('customerDetial').getRawValue();
	var amount = form.findField('amount').getValue();
	var notes = form.findField('notes').getValue();
	if ((waybillNo == "" || waybillNo == null)) {
		Ext.Msg.alert(agency.PAPayableAdd.i18n('foss.stl.agency.common.alert'), '运单号不能为空');
		return false;
	}
	var customerCode = "";
	if (customerDetialCode != "" && customerDetialCode != null) {
		customerCode = customerDetialCode;
	} else {
		Ext.Msg
			.alert(agency.PAPayableAdd.i18n('foss.stl.agency.common.alert'), agency.PAPayableAdd.i18n('foss.stl.agency.PAPayAndRec.PANameNotNull'));
		return false;
	}
	var customerName = "";
	if (customerDetialName != "" && customerDetialName != null) {
		customerName = customerDetialName;
	} else {
		Ext.Msg
			.alert(agency.PAPayableAdd.i18n('foss.stl.agency.common.alert'), agency.PAPayableAdd.i18n('foss.stl.agency.PAPayAndRec.PANameNotNull'));
		return false;
	}

	if (form.isValid()) {
		var params = {
			'billPayableAgencyVo.billPayableAgencyDto.waybillNo' : waybillNo,
			'billPayableAgencyVo.billPayableAgencyDto.customerCode' : customerCode,
			'billPayableAgencyVo.billPayableAgencyDto.customerName' : customerName,
			'billPayableAgencyVo.billPayableAgencyDto.amount' : amount,
			'billPayableAgencyVo.billPayableAgencyDto.notes' : notes
		}
		var yesFn = function() {
			// 设置该按钮灰掉
			me.disable(false);
			// 30秒后自动解除灰掉效果
			setTimeout(function() {
					me.enable(true);
				}, 10000);
			Ext.Ajax.request({
					url : agency.realPath('addPABillPayable.action'),
					params : params,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						form.reset();
						Ext.Msg.alert(agency.PAPayableAdd.i18n('foss.stl.agency.common.alert'), agency.PAPayableAdd
								.i18n('foss.stl.agency.airPayAndRec.saveSuccess'));
						me.enable(true);
					},
					exception : function(response) {
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(agency.PAPayableAdd.i18n('foss.stl.agency.common.alert'), result.message);
						me.enable(true);
					},
					failure : function(response) {
						var result = Ext.decode(response.responseText);
						me.enable(true);
					}
				});
		};
		var noFn = function() {
			return false;
		};
		agency.PAPayableAdd.billPAPayableAddConfirmAlert(agency.PAPayableAdd.i18n('foss.stl.agency.airPayAndRec.isSave'), yesFn, noFn);
	} else {
		Ext.Msg.alert(agency.PAPayableAdd.i18n('foss.stl.agency.common.alert'), agency.PAPayableAdd.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

Ext.define('Foss.StlPAPayable.StlPAPayableAddInfoTab', {
		extend : 'Ext.tab.Panel',
		frame : false,
		bodyCls : 'autoHeight',
		cls : 'innerTabPanel',
		activeTab : 0,
		height : 380,
		items : [{
				title : agency.PAPayableAdd.i18n('foss.stl.agency.payable.PAPayableAdd'),
				tabConfig : {
					width : 200
				},
				width : '200',
				layout : 'fit',
				items : [{
						xtype : 'form',
						defaults : {
							margin : '5 10 5 5',
							labelWidth : 90
						},
						layout : 'column',
						items : [{
								xtype : 'textfield',
								fieldLabel : '<span style="color:red;">*</span>' + agency.PAPayableAdd.i18n('foss.stl.agency.airPayAndRec.waybillNo'),
								name : 'waybillNo',
								regex : /^\s*\d{8,}\s*$/,
								columnWidth : .34
							}, {
								xtype : 'container',
								border : false,
								columnWidth : .35,
								height : 24,
								html : '&nbsp;'
							}, {
								xtype : 'commonvehagencycompselector',
								fieldLabel : '<span style="color:red;">*</span>' + agency.PAPayableAdd.i18n('foss.stl.agency.PAPayAndRec.PADetial'),
								name : 'customerDetial',
								columnWidth : .34,
								listWidth : 300,// 设置下拉框宽度
								isPaging : true
								// 分页
						}	, {
								xtype : 'container',
								border : false,
								columnWidth : .02,
								html : '&nbsp;'
							}, {
								xtype : 'numberfield',
								fieldLabel : agency.PAPayableAdd.i18n('foss.stl.agency.common.amount'),
								name : 'amount',
								decimalPrecision : 2,
								columnWidth : .34,
								allowBlank : false,
								html : '&nbsp;'
							}, {
								xtype : 'container',
								border : false,
								columnWidth : .5,
								html : '&nbsp;'
							}, {
								xtype : 'textarea',
								fieldLabel : agency.PAPayableAdd.i18n('foss.stl.agency.airPayAndRec.notes'),
								allowBlank : false,
								name : 'notes',
								allowBlank : false,
								emptyText : agency.PAPayableAdd.i18n('foss.stl.agency.payable.notesEmptyText'),
								columnWidth : .7
							}, {
								border : 1,
								xtype : 'container',
								columnWidth : 1,
								defaultType : 'button',
								layout : 'column',
								items : [{
										xtype : 'container',
										border : false,
										columnWidth : .62,
										html : '&nbsp;'
									}, {
										text : agency.PAPayableAdd.i18n('foss.stl.agency.common.save'),
										disabled : !agency.PAPayableAdd.isPermission('/stl-web/agency/addPABillPayable.action'),
										hidden : !agency.PAPayableAdd.isPermission('/stl-web/agency/addPABillPayable.action'),
										cls : 'yellow_button',
										columnWidth : .08,
										handler : function() {
											var form = this.up('form').getForm();
											var me = this;
											agency.PAPayableAdd.addBillPayable(form, me);
										}
									}]
							}]
					}]
			}]
	});

// 初始化界面
Ext.onReady(function() {
		Ext.QuickTips.init();

		var StlPAPayableQueryInfoTab = Ext.create('Foss.StlPAPayable.StlPAPayableAddInfoTab');

		Ext.create('Ext.panel.Panel', {
				id : 'T_agency-PAPayableAdd_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [StlPAPayableQueryInfoTab],
				renderTo : 'T_agency-PAPayableAdd-body'
			});
	});