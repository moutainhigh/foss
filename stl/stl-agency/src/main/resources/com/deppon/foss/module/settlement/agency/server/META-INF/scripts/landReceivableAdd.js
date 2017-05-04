/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.landReceivableAdd.billLandReceivableAddConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.landReceivableAdd.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * 新增快递代理其他应收单信息
 * @returns {Boolean}
 */
agency.landReceivableAdd.addBillReceivable = function(form,me){

	var waybillNo = Ext.String.trim(form.findField('waybillNo').getValue());
	var customerDetialCode = form.findField('customerDetial').getValue();
	var customerDetialName = form.findField('customerDetial').getRawValue();
	var amount = form.findField('amount').getValue();
	var notes = form.findField('notes').getValue();
	if(waybillNo == "" || waybillNo == null){
		Ext.Msg.alert(agency.landReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.landReceivableAdd.i18n('运单号不能为空'));
		return false;
	}
	var customerCode = "";
	if(customerDetialCode != "" && customerDetialCode != null){
		customerCode = customerDetialCode;
	}else{
		Ext.Msg.alert(agency.landReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.landReceivableAdd.i18n('foss.stl.agency.airPayAndRec.landNameNotNull'));
		return false;
	}
	var customerName = "";
	if(customerDetialName != "" && customerDetialName != null){
		customerName = customerDetialName;
	}else{
		Ext.Msg.alert(agency.landReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.landReceivableAdd.i18n('foss.stl.agency.airPayAndRec.landNameNotNull'));
		return false;
	}
	var params = {
			'billReceivableAgencyVo.billReceivableAgencyDto.waybillNo':waybillNo,
			'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':customerCode,
			'billReceivableAgencyVo.billReceivableAgencyDto.customerName':customerName,
			'billReceivableAgencyVo.billReceivableAgencyDto.amount':amount,
			'billReceivableAgencyVo.billReceivableAgencyDto.notes':notes
	}
	
	if(form.isValid()){
		var yesFn=function(){
			//设置该按钮灰掉
			me.disable(false);
			//30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			Ext.Ajax.request({
				url :agency.realPath('addLandBillReceivable.action'),
				params:params,
				success:function(response){
					form.reset();
    				Ext.Msg.alert(agency.landReceivableAdd.i18n('foss.stl.agency.common.alert'),
    						agency.landReceivableAdd.i18n('foss.stl.agency.airPayAndRec.saveSuccess'));
    				me.enable(true);
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(agency.landReceivableAdd.i18n('foss.stl.agency.common.alert'),result.message);
					me.enable(true);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
					me.enable(true);
				}
			});
    	};
    	var noFn=function(){
    	 	return false;
    	};
    	agency.landReceivableAdd.billLandReceivableAddConfirmAlert(agency.landReceivableAdd.i18n('foss.stl.agency.airPayAndRec.isSave'),yesFn,noFn);
	}else{
		Ext.Msg.alert(agency.landReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.landReceivableAdd.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

Ext.define('Foss.StlairReceivable.StllandReceivableAddInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 380,
	items : [ {
		title:  agency.landReceivableAdd.i18n('foss.stl.agency.receivable.landReceivableAdd'),
		tabConfig: {
			width: 200
		},
		width: '200',
        layout:'fit',
        items:[{
        	xtype:'form',
        	defaults:{
	        	margin:'5 10 5 5',
	        	labelWidth:90
       		 },
        	layout:'column',
		    items:[{	
		    	xtype:'textfield',
				fieldLabel:'<span style="color:red;">*</span>'+agency.landReceivableAdd.i18n('foss.stl.agency.airPayAndRec.waybillNo'),
				name:'waybillNo',
				regex:/^\s*\d{8,}\s*$/,
				columnWidth:.34
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.35,
				height:24,
				html: '&nbsp;'
		    },{	
	        	xtype:'commonLdpAgencyCompanySelector', //快递代理
				fieldLabel:'<span style="color:red;">*</span>'+agency.landReceivableAdd.i18n('foss.stl.agency.landPayAndRec.landDetial'),
				name:'customerDetial',
				columnWidth:.34,
				listWidth:300,//设置下拉框宽度
				isPaging:true //分页
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.02,
				html: '&nbsp;'
		    },{
		    	xtype:'numberfield',
		    	fieldLabel:agency.landReceivableAdd.i18n('foss.stl.agency.common.amount'),
		    	name:'amount',
		    	decimalPrecision : 2,
				columnWidth:.34,
				allowBlank:false,
				html: '&nbsp;'
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.5,
				html: '&nbsp;'
		    },{
		    	xtype: 'textarea',
		    	fieldLabel:agency.landReceivableAdd.i18n('foss.stl.agency.airPayAndRec.notes'),
		    	name:'notes',
		    	columnWidth:.7
		    },{	
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
					xtype: 'container',
					border : false,
					columnWidth:.62,
					html: '&nbsp;'
				},{
					text:agency.landReceivableAdd.i18n('foss.stl.agency.common.save'),
					disabled:!agency.landReceivableAdd.isPermission('/stl-web/agency/addLandBillReceivable.action'),
					hidden:!agency.landReceivableAdd.isPermission('/stl-web/agency/addLandBillReceivable.action'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
    					var me = this;
    					var form  = this.up('form').getForm();	
    					agency.landReceivableAdd.addBillReceivable(form,me);
    				}
				}]
		    }]
        }]
	}]
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlairReceivableQueryInfoTab = Ext.create('Foss.StlairReceivable.StllandReceivableAddInfoTab');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-landReceivableAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StlairReceivableQueryInfoTab],
		renderTo: 'T_agency-landReceivableAdd-body'
	});
});