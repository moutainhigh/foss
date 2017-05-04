/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
agency.landPayableAdd.billLandPayableAddConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};
/**
 * @author 218392 zhangyongxue 2015-07-24
 * 其他应付类型
 */
Ext.define('Foss.StllandPayable.OtherPayType', {
			extend : 'Ext.data.Store',
			fields : ['valueName', 'valueCode'],
			data : [{
						valueName : agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.trailerFee'),//拖车费
						valueCode : 'TF'
					},{
						valueName : agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.transferSendFee'),//转寄费
						valueCode : 'TSF'
					},{
						valueName : agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.returnGoodsFee'),//返货费
						valueCode : 'RGF'
					},{
						valueName : agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.weightDeviationFee'),//重量偏差费
						valueCode : 'WDF'
					},{
						valueName : agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.intoHouseFee'),//进仓费
						valueCode : 'IHF'
					},{
						valueName : agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.overweightDeliveryFee'),//超重派送费
						valueCode : 'ODF'
					},{
						valueName : agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.otherFee'),//其他
						valueCode : 'OF'
					}]
});

/**
 * 新增快递代理其他应付单信息
 * @returns {Boolean}
 */
agency.landPayableAdd.addBillPayable = function(form,me){
	var waybillNo = Ext.String.trim(form.findField('waybillNo').getValue());
	var customerDetialCode = form.findField('customerDetial').getValue();
	var customerDetialName = form.findField('customerDetial').getRawValue();
	var amount = form.findField('amount').getValue();
	var notes = form.findField('notes').getValue();
	var payableType = form.findField('payableType').getValue();//@218392 zhangyongxue
	if((waybillNo == "" || waybillNo == null)){
		Ext.Msg.alert(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),'运单号不能为空');
		return false;
	}
	//@author 218392 zhangyongxue 2015-07-27
	if((payableType == "" || payableType == null)){
		Ext.Msg.alert(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),'其他应付类型不能为空');
		return false;
	}
	var customerCode = "";
	if(customerDetialCode != "" && customerDetialCode != null){
		customerCode = customerDetialCode;
	}else{
		Ext.Msg.alert(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.landNameNotNull'));
		return false;
	}
	var customerName = "";
	if(customerDetialName != "" && customerDetialName != null){
		customerName = customerDetialName;
	}else{
		Ext.Msg.alert(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.landNameNotNull'));
		return false;
	}
	
	if(form.isValid()){
		var params = {
				'billPayableAgencyVo.billPayableAgencyDto.waybillNo':waybillNo,
				'billPayableAgencyVo.billPayableAgencyDto.customerCode':customerCode,
				'billPayableAgencyVo.billPayableAgencyDto.customerName':customerName,
				'billPayableAgencyVo.billPayableAgencyDto.amount':amount,
				'billPayableAgencyVo.billPayableAgencyDto.notes':notes,
				'billPayableAgencyVo.billPayableAgencyDto.otherPayType':payableType
		}
		var yesFn=function(){
			//设置该按钮灰掉
			me.disable(false);
			//30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			Ext.Ajax.request({
				url : agency.realPath('addLandBillPayable.action'),
				params:params,
				success:function(response){
					var result = Ext.decode(response.responseText);
					form.reset();
					Ext.Msg.alert(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),
							agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.saveSuccess'));
					me.enable(true);
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),result.message);
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
		agency.landPayableAdd.billLandPayableAddConfirmAlert(agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.isSave'),yesFn,noFn);
	}else{
		Ext.Msg.alert(agency.landPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

Ext.define('Foss.StllandPayable.StllandPayableAddInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 380,
	items : [ {
		title: agency.landPayableAdd.i18n('foss.stl.agency.payable.landPayableAdd'),
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
				fieldLabel:'<span style="color:red;">*</span>'+agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.waybillNo'),
				name:'waybillNo',
				regex:/^\s*\d{8,}\s*$/,
				columnWidth:.34
		    },{
		    	//@author 218392 zhangyongxue 2015-07-24
		    	xtype : 'combobox',
				name : 'payableType',
				labelWidth:110,
				fieldLabel : '<span style="color:red;">*</span>'+agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.otherPayType'),//其他应付类型
				editable : false,
				store : Ext.create('Foss.StllandPayable.OtherPayType'),
				queryModel : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				value : '',
				columnWidth : .34
		    },
		    
/*		    {
		    	xtype: 'container',
				border : false,
				columnWidth:.35,
				height:24,
				html: '&nbsp;'
		    },*/
		    {	
	        	xtype:'commonLdpAgencyCompanySelector',
				fieldLabel:'<span style="color:red;">*</span>'+agency.landPayableAdd.i18n('foss.stl.agency.landPayAndRec.landDetial'),
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
		    	fieldLabel:agency.landPayableAdd.i18n('foss.stl.agency.common.amount'),
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
		    	fieldLabel:agency.landPayableAdd.i18n('foss.stl.agency.airPayAndRec.notes'),
		    	allowBlank:false,
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
					text:agency.landPayableAdd.i18n('foss.stl.agency.common.save'),
					disabled:!agency.landPayableAdd.isPermission('/stl-web/agency/addLandBillPayable.action'),
					hidden:!agency.landPayableAdd.isPermission('/stl-web/agency/addLandBillPayable.action'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
						var form  = this.up('form').getForm();	
    					var me = this;
    					agency.landPayableAdd.addBillPayable(form,me);
    				}
				}]
		    }]
        }]
	}]
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StllandPayableQueryInfoTab = Ext.create('Foss.StllandPayable.StllandPayableAddInfoTab');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-landPayableAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StllandPayableQueryInfoTab],
		renderTo: 'T_agency-landPayableAdd-body'
	});
});