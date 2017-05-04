/**
 *查询_修改中转提货清单 
 */
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.queryform',{
	extend: 'Ext.form.Panel',
	frame:true,
	title:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.searchCondition'),
	layout:'column',
	defaults: {
        	xtype: 'textfield',
        	margin: ' 5 5 5 5'
	},
	items:[{
		xtype: 'dynamicorgcombselector',
		type:'ORG',
		doAirDispatch:'Y',
		fieldLabel:  airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airEnteringFlightBill.airMasterCalibration'),
		name: 'createOrgName',
		allowBlank: false,
		disabled:true,
		columnWidth:.25
	},{
		xtype:'commonairagencydeptselector',
		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.dedtOrgName'),
		name:'destOrgCode',
		columnWidth:.25
	},{
		xtype: 'rangeDateField',
		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.createTime'),
		fieldId: 'Foss_airfreight_airQueryModifyTfrPickupBill_createTime_Id',
		dateType: 'datetimefield_date97',
		fromName: 'beginInTime',
		toName: 'endInTime',
		allowBlank: false,
		fromValue:Ext.Date.format(new Date(),'Y-m-d')+ ' '+'00:00:00',
		toValue:Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59',
		columnWidth: .50
	},{
		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.airTransferPickupbillNo'),
		name:'airTransferPickupbillNo',
		columnWidth:.25
	},{
		xtype : 'commonairporwithcitynametselector',
		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.arrvRegionName'),
		name:'arrvRegionCode',
		displayField : 'cityName',
		valueField : 'cityCode',
		columnWidth:.25
	},{
		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.transferFlightNo'),
		name:'transferFlightNo',
		columnWidth:.25
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.reset'),
			columnWidth:.08,
			handler:function(){
					var createDeptCode = airfreight.airQueryModifyTfrPickupBill.createDeptCode;
					var createDeptName = airfreight.airQueryModifyTfrPickupBill.createDeptName;
					var queryformReset = airfreight.airQueryModifyTfrPickupBill.queryform.getForm();
					queryformReset.findField('createOrgName').setCombValue(createDeptName,createDeptCode);
					queryformReset.findField('destOrgCode').reset();
					queryformReset.findField('airTransferPickupbillNo').reset();
					queryformReset.findField('arrvRegionCode').reset();
					queryformReset.findField('transferFlightNo').reset();
					queryformReset.findField('beginInTime').setValue(Ext.Date.format(new Date(),'Y-m-d')+ ' '+'00:00:00');
					queryformReset.findField('endInTime').setValue(Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59');
				}
			},{
				xtype: 'container',
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.search'),
				cls:'yellow_button',
				disabled: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/queryTfrPickupBillButton'),
				hidden: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/queryTfrPickupBillButton'),
				columnWidth:.08,
				handler:function(){
					if(!airfreight.airQueryModifyTfrPickupBill.queryform.getForm().isValid()){
						Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
								, airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.inputWrongful'));
						return false;
					}
					var beginDate = airfreight.airQueryModifyTfrPickupBill.queryform.getForm().findField('beginInTime').getValue();
					var endDate = airfreight.airQueryModifyTfrPickupBill.queryform.getForm().findField('endInTime').getValue();
					var checkDateTimeSpan =  function(beginDate, endDate) {
						if(beginDate=='' || beginDate==null){
							Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
									, airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airEnteringFlightBill.createBillStartTime'));
							return false;
						}
						if(endDate=='' || endDate==null){
							Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
									, airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airEnteringFlightBill.createBillEndTime'));
							return false;
						}
						if(!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
							var begin = Ext.Date.parse(beginDate, "Y-m-d H:i:s", true);
							var end = Ext.Date.parse(endDate, "Y-m-d H:i:s", true);
							var pool = begin - end;
							var m = -86400000 * 31;
							if(pool < m) {
								Ext.MessageBox.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
										, airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airEnteringFlightBill.createBillTime'));				
								return false;
							} 
						}
						return true;
					}
					if(!checkDateTimeSpan(beginDate,endDate))
						return false;
					airfreight.airQueryModifyTfrPickupBill.pagingBar.moveFirst();
				}
		}]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
	}
});

/**
 *查询_修改中转提货清单model
 */ 
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillModel',{
	extend:'Ext.data.Model',
	fields: [{
		name:'id',
		type:'string'
	},{
		name:'airTransferPickupbillNo',
		type:'string'
	},{
		name:'arrvRegionCode',
		type:'string'
	},{
		name:'arrvRegionName',
		type:'string'
	},{
		name:'destOrgCode',
		type:'string'
	},{
		name:'destOrgName',
		type:'string'
	},{
		name:'transferFlightNo',
		type:'string'
	},{
		name:'transferDate',
		type:'date',convert:dateConvert
	},{
		name:'airLineCode',
		type:'string'
	},{
		name:'airLineName',
		type:'string'
	},{
		name:'airWaybillNo',
		type:'string'
	},{
		name:'createUserCode',
		type:'string'
	},{
		name:'createUserName',
		type:'string'
	},{
		name:'createOrgCode',
		type:'string'
	},{
		name:'createOrgName',
		type:'string'
	},{
		name:'createTime',
		type:'date',convert:dateConvert
	},{
		name:'modifyUserCode',
		type:'string'
	},{
		name:'modifyUserName',
		type:'string'
	},{
		name:'modifyTime',
		type:'date'
	},{
		name:'waybillQtyTotal',
		type:'number'
	},{
		name:'goodsQtyTotal',
		type:'number'
	},{
		name:'grossWeightTotal',
		type:'number'
	},{
		name:'deliverFeeTotal',
		type:'number'
	},{
		name:'arrivalFeeTotal',
		type:'number'
	},{
		name:'collectionFeeTotal',
		type:'number'
	},{
		name:'currencyCode',
		type:'string'
	}]
});

/**
 *查询_修改中转提货清单store 
 */
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url:airfreight.realPath('queryTfrPickupBill.action'),
		reader : {
			type : 'json',
			root : 'airTransPickupBillVo.airTransPickupbillList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			//查询条件
			var formValue = airfreight.airQueryModifyTfrPickupBill.queryform.getForm().getValues();
			Ext.apply(operation,{
				params : {
					'airTransPickupBillVo.airTransPickupBillDto.airTransferPickupbillNo' : formValue.airTransferPickupbillNo,
					'airTransPickupBillVo.airTransPickupBillDto.arrvRegionCode' : formValue.arrvRegionCode,
					'airTransPickupBillVo.airTransPickupBillDto.destOrgCode' : formValue.destOrgCode,
					'airTransPickupBillVo.airTransPickupBillDto.origOrgCode' : airfreight.airQueryModifyTfrPickupBill.createDeptCode,
					'airTransPickupBillVo.airTransPickupBillDto.beginInTime' : formValue.beginInTime,
					'airTransPickupBillVo.airTransPickupBillDto.endInTime' : formValue.endInTime,
					'airTransPickupBillVo.airTransPickupBillDto.flightNo' : formValue.transferFlightNo
				}
			});			
		}
	}
});

/**
 *新增 
 */
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.addTfrPickupBill',{
	extend:'Ext.container.Container',
	width : 1080,
	layout : 'column',
	items: [{
			xtype: 'container',
			margin:'5 5 5 10',
			html: '&nbsp;'
		},{
			width:65,
			xtype:'button',
			text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.newAdd'),
			columnWidth:.08,
			handler:function(){
				addTab('T_airfreight-airMakeTfrPickupGoodsList',//对应打开的目标页面js的onReady里定义的renderTo
						 airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.makeTransfrInventory'),//打开的Tab页的标题
						airfreight.realPath('airMakeTfrPickupGoodsList.action'));
			}
	}]
});

Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.pickupAirwayBillDetailsModel',{
	extend:'Ext.data.Model',
	fields:[{
			name:'airTransferPickupbillId',
			type:'string'
		},{
			name:'waybillNo',
			type:'string'
		},{
			name:'arrvRegionName',
			type:'string'
		},{
			name:'goodsName',
			type:'string'
		},{
			name:'goodsQty',
			type:'number'
		},{
			name:'weight',
			type:'number'
		},{
			name:'billingWeight',
			type:'number'
		},{
			name:'pickupType',
			type:'string'
		},{
			name:'deliverFee',
			type:'number'
		},{
			name:'arrivalFee',
			type:'number'
		},{
			name:'collectionFee',
			type:'number'
		},{
			name:'airWaybillNo',
			type:'string'
		},{
			name:'createTime',
			type:'date'
		},{
			name:'currencyCode',
			type:'string'
		},{
			name:'receiverContactPhone',
			type:'string'
		},{
			name:'receiverAddress',
			type:'string'
		},{
			name:'receiverName',
			type:'string'
		},{
			name:'beTransfer',
			type:'string'
		},{
			name:'notes',
			type:'string'
		},{
			name:'id',
			type:'string'
		}]
	
});

Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.pickupAirwayBillDetailsStore',{
	extend: 'Ext.data.Store',
	model:'Foss.airfreight.airQueryModifyTfrPickupBill.pickupAirwayBillDetailsModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	},
	listeners: {
		datachanged: function(store, operation, eOpts){
			var totalArray = store.data.items;
			//票数
			var billNoTotal = totalArray.length;
			//件数
			var goodsQtyTotal = 0;
			//毛重
			var grossWeightTotal = 0;
			//公斤送货费
			var deliverFeeTotal = 0;
			//到付费
			var arrivalFeeTotal = 0;
			
			for(var i=0;i<totalArray.length;i++){
				//计算件数
				goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty,0);
				//计算毛重
				grossWeightTotal = grossWeightTotal + Ext.Number.from(totalArray[i].data.weight,0);
				//计算公斤送货费
				deliverFeeTotal = deliverFeeTotal + Ext.Number.from(totalArray[i].data.deliverFee,0);
				//计算到付费
				arrivalFeeTotal = arrivalFeeTotal + Ext.Number.from(totalArray[i].data.arrivalFee,0);
			}
			var count = 0;
			var toolbarArray = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.down('toolbar').query('textfield');
			for(var j=0;j<toolbarArray.length;j++){
				if(count==0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count==1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count==2){
					toolbarArray[j].setValue(grossWeightTotal + '  ' +  airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.kilo'));
					toolbarArray[j].hideValue = grossWeightTotal;
				}else if(count==3){
					toolbarArray[j].setValue(deliverFeeTotal);
				}else{
					toolbarArray[j].setValue(arrivalFeeTotal);
				}
				count ++;
			}
		}
	}
});

/******************************************************************************/
//导出中转提货清单
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillExport', {
   extend:'Ext.toolbar.Toolbar',
   xtype:'toolbar',
   dock:'right',
   layout:'column',
   defaultType:'button',
	   width:1024,
	   callEdi : function(ids,airWaybillNo,callIsNotEdiFlag,transferFlightNo){
		   	var flagSuccess =  true;
			if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
	       	}
			Ext.Ajax.request({
				url:airfreight.realPath('uploadTranPickupCallEdi.action'),
				form: Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : {'airTransPickupBillVo.ids':ids,
				      	  'airTransPickupBillVo.airWaybillNo': airWaybillNo,
				          'airTransPickupBillVo.callIsNotEdiFlag': callIsNotEdiFlag,
				          'airTransPickupBillVo.fightNo': transferFlightNo
				},
				isUpload: true,
				success : function(response) {
						var result = Ext.decode(response.responseText);
						if(result.message!=null){
							flagSuccess =  false;
							Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
									, airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.uploadingTransfrInventory'));
							return false;
						}
					}
				})
				return flagSuccess;
		},
	   items:[{
		   xtype:'container',
		   html:'&nbsp;',
		   columnWidth:.94
	   },{
		   text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.export'),
		   disabled: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/exportUploadTranPickupCallEdiButton'),
		   hidden: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/exportUploadTranPickupCallEdiButton'),
		   columnWidth:.06,
		   handler: function(){
			   var record = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.getSelectionModel().getSelection();
			   if(record.length==0){
				   Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
						   , airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeFlightjointList.selectRequireExprotDetail'));
				   return false;
			   }
			   var tfrPickupBillRecord = airfreight.airQueryModifyTfrPickupBill.queryform.getForm();
			   var transferFlightNo = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillMainMessage.items.items[4].value;
			   //原设计是用返回正单号，后台查询正单号进行查询操作，但因为设计原因，会有重复数据
			   //故返回中转单号，唯一能表示此中转提货清单，参数不变
			   //var airWaybillNo = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillSelect.items.items[1].value;
			   var airWaybillNo = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillMainMessage.items.items[0].value;
			   var ids = new Array();
			   for(var i = 0; i<record.length;i++){
				   var id = record[i].data.id;
				   ids.push(id);
			   }
			   Ext.Msg.confirm(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage'),airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.sendEdi'), function(btn,text){
					if(btn == 'yes'){
						var callIsNotEdiFlag = 'YES'; 
						var flag = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillExport.callEdi(ids,airWaybillNo,callIsNotEdiFlag,transferFlightNo);
						if(flag){
							Ext.ux.Toast.msg(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.callEdiSuccess'));
							return false;
						}
					}else if(btn == 'no'){
						var callIsNotEdiFlag = 'NO'; 
						var flag = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillExport.callEdi(ids,airWaybillNo,callIsNotEdiFlag,transferFlightNo);
						if(flag){
							Ext.ux.Toast.msg(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.exportSuccess'));
							return false;
						}
					}
				})
		   }
   }]
});
		
//提货清单信息
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillMainMessage',{
	extend:'Ext.form.FieldSet',
    height:105,
    width:1040,
    layout:'column',
    defaults:{
		margin:'5 5 5 5',
		xtype: 'textfield'
	},
	items:[{
		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.airTransferPickupbillNo'),
		name:'airTransferPickupbillNo',
		allowBlank: false,
		readOnly:true,
		columnWidth:.25
	},{
		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.arrvRegionName'),
		name:'arrvRegionName',
		readOnly:true,
		columnWidth:.25
	},{
		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.dedtOrgName'),
		name:'deptOrgName',
		readOnly:true,
		columnWidth:.25
	},{
		xtype:'container',
		columnWidth:.86
	},{
		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.transferFlightNo'),
		name:'transferFlightNo',
		readOnly:true,
		columnWidth:.25
	},{
		xtype: 'datefield',
        fieldLabel:  airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.transferDate'),
        name: 'transferDate',
        format: 'Y-m-d',
        allowBlank: false,
        columnWidth:.25
	}]
});

//查询条件
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillSelect',{
	extend:'Ext.form.FieldSet',
    height:55,
    width:600,
    layout:'column',
    defaults:{
		margin:'5 5 5 5',
		xtype: 'textfield'
	},
	items:[{
		xtype:'commonairlinesselector',
		displayField : 'code',// 显示名称
		valueField : 'code',// 值 
		editable:false,
		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.airLineTwoletter'),
		name: 'airLineTwoletter',
		readOnly:true,
		columnWidth:.45
	},{
		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.airWaybillNo'),
		name:'airWaybillNo',
		readOnly:true,
		columnWidth:.45
	}]
});
//运单号添加
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillAddAirWaybill',{
	extend:'Ext.form.FieldSet',
    height:55,
    width:430,
    layout:'column',
    defaults:{
		margin:'5 5 5 5',
		xtype: 'textfield'
	},
	items:[{
		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.waybillNo'),
		name:'receiverName',
		columnWidth:.65
	},{
		xtype:'button',
		text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.add'),
		disabled: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/queryToAddTfrPickupBillDetailButton'),
		hidden: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/queryToAddTfrPickupBillDetailButton'),
		columnWidth:.15,
		handler:function(){
			var record = airfreight.airQueryModifyTfrPickupBill.tfrPickupbill.data;
			var waybillNo = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillAddAirWaybill.items.items[0].value;
			var tfrPickupBillGridList = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.store.data.items;
			for(var i = 0;i<tfrPickupBillGridList.length;i++){
				var recordTfrPickupBill = tfrPickupBillGridList[i].data;
				if(recordTfrPickupBill.waybillNo == waybillNo){
					Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
							, airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airChangeInventory.waybillNoMessage')+ waybillNo + airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.yczqdmxlezbncftj'));
					return false;
				}
			}
			Ext.Ajax.request({
				async: false,
				url:airfreight.realPath('queryToAddTfrPickupBillDetail.action'),
				params : {
					'airTransPickupBillVo.airTransPickupBillDto.waybillNo':waybillNo,
					'airTransPickupBillVo.airTransPickupBillDto.arrvRegionName':record.arrvRegionName,
					'airTransPickupBillVo.airTransPickupBillDto.destOrgName':record.destOrgName,
					'airTransPickupBillVo.airTransPickupBillDto.flightNo':record.transferFlightNo
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					var list = result.airTransPickupBillVo.airPickupbillDetailList;
					if(list.length==0){
						Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
								,airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.bbmwcyd'));
						return false;
					}
					airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.store.add(list);
				}
			});
		}
	}]
});

Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillDelete',{
	extend:'Ext.container.Container',
	width : 1080,
	layout : 'column',
	items: [{
  		width:100,
  		items:[{
  			xtype:'button',
  			text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.delete'),
  			handler:function(){
  				var record = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.getSelectionModel().getSelection();
  				if(record.length==0){
  					Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage'), airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.pleaseSelectDelDetail'));
  					return false;
  				}
				Ext.Msg.confirm(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage'),airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.isorisntDel'), function(btn,text){
					if(btn == 'yes'){
						airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.store.remove(record);
					}else{
						return false;
					}
				})
  			}
  		}]
	}]
});

//修改清单信息window
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.updateWindowTfrPickupBill',{
	extend: 'Ext.window.Window',
	title: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeFlightjointList.modifyWaybill'),
	modal:true,
	closeAction:'hide',
	width: 580,
	height: 300,
	layout: 'auto',
	items: [
       airfreight.airQueryModifyTfrPickupBill.updateBindingTfrPickupBill = Ext.create('Ext.form.Panel',{
        	frame: false,
        	border: false,
        	layout: 'column',
        	defaults: {
        		margin: '5 5 5 5',
        		columnWidth:.49,
        		xtype: 'textfield',
        		allowBlank:false,
        		labelWidth:80
        	},
        	items:[{
        		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.waybillNo'),
        		name: 'waybillNo',
        		margin: '0 5 5 5',
        		readOnly:true
        	},{
        		xtype:'container',
        		columnWidth:.86
        	},{
        		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.billingWeight'),
        		name: 'billingWeight',
        		maxLength : 50
        	},{
        		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.deliverFee'),
        		name: 'deliverFee'
        	},{
        		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.poll'),
        		name: 'goodsQty'
        	},{
        		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.receiverName'),
        		name: 'receiverName'
        	},{
        		fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.phone'),
        		name: 'receiverContactPhone'
        	},{
        		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.address'),
        		name: 'receiverAddress'
        	},{
        		fieldLabel: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.notes'),
        		name: 'notes',
        		columnWidth:.98,
        		allowBlank:true
        	},{
        		xtype:'container',
        		columnWidth:.68
        	},{
        		xtype:'button',
        		text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.ensure'),
        		columnWidth:.15,
        		handler:function(){
        			if(!airfreight.airQueryModifyTfrPickupBill.updateBindingTfrPickupBill.getForm().isValid()){
        				Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage')
        						,airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.includebx'));
        				return false; 
        			}
        			//获取表单
        			var formValue = this.up('form').getForm();
        			//得到表单record对象
        			var oldRecord = formValue.getRecord();
        			formValue.updateRecord(oldRecord);
        			airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.store.update(formValue.getRecord());
        			airfreight.airQueryModifyTfrPickupBill.updateWindowTfrPickupBill.close();
        		}
        	},{
        		xtype:'button',
        		text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.cancel'),
        		columnWidth:.15,
        		handler:function(){
        			this.up('window').close();
        		}
        	}]
        })
	]
});
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillGridList',{
	extend:'Ext.grid.Panel',
	title:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.inventoryDetail'),
	frame:true,
	border:true,
	layout:'column',
	width:1035,
	emptyText: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.searchResultInexistence'),
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			handler: function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				//增加签收判断，签收的运单不允许修改
				//wqh 
				//2013-08-13
				var params= {
    					'airTransPickupBillVo.airTransPickupBillDto.waybillNo': record.data.waybillNo
    			};
				Ext.Ajax.request({
					async: false,
					url:airfreight.realPath('checkIfCanModify.action'),
					params:params,
					success:function(response){
									
						airfreight.airQueryModifyTfrPickupBill.updateWindowTfrPickupBill = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.updateWindowTfrPickupBill').show();
						airfreight.airQueryModifyTfrPickupBill.updateBindingTfrPickupBill.getForm().loadRecord(record);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
						return false;
					}
				});

			}
		}],
		text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.operate'),
	    width: 40,
	    dataIndex: 'id'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.waybillNo'),
		flex: 0.6,
		dataIndex: 'waybillNo'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.arrvRegionName'),
		flex: 0.6,
		dataIndex: 'arrvRegionName'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airChangeInventory.goodsName'),
		flex: 0.6,
		dataIndex: 'goodsName'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.poll'),
		flex: 0.6,
		dataIndex: 'goodsQty'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.weightKilo'),
		flex: 0.6,
		dataIndex: 'weight'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.billingWeightKilo'),
		flex: 0.6,
		dataIndex: 'billingWeight'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.pickupType'),
		flex: 0.6,
		dataIndex: 'pickupType',
		renderer : function(value){
			if(value=='SELF_PICKUP_AIR'){
				return airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.zt');
			}else{
				return airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.ps');
			}
		}
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.deliverFee'),
		flex: 0.6,
		dataIndex: 'deliverFee'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.arrivalFee'),
		flex: 0.6,
		dataIndex: 'arrivalFee'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.collectionFee'),
		flex: 0.6,
		dataIndex: 'collectionFee'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.notes'),
		flex: 0.6,
		dataIndex: 'notes'
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.ticket'),
			   columnWidth:.10,
			   dataIndex: 'billNoTotal'
		   },{
			   fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.poll'),
			   columnWidth:.10,
			   dataIndex: 'goodsQtyTotal'
		   },{
			   fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.weight'),
			   columnWidth:.12,
			   hideValue:'',
			   dataIndex: 'grossWeightTotal'
		   },{
			   fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.kiloDeliverFee'),
			   labelWidth:80,
			   columnWidth:.15,
			   dataIndex: 'deliverFeeTotal'
		   },{
			   fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.arrivalFee'),
			   labelWidth:60,
			   columnWidth:.15,
			   dataIndex: 'arrivalFeeTotal'
		   }]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.pickupAirwayBillDetailsStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.callParent([cfg]);
	}
});

Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillDisablePanel',{
	extend:'Ext.form.Panel',
	frame:false,
	border:false,
	width:1035,
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 margin:'10 0 0 0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:'',
			   columnWidth:.60
		   },{
			   fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.createUser'),
			   labelWidth:55,
			   columnWidth:.15,
			   dataIndex: 'createUserName'
		   },{
			   fieldLabel:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.createTime'),
			   labelWidth:75,
			   columnWidth:.20,
			   dataIndex: 'createTime'
		   },{
			   fieldLabel:'',
			   columnWidth:.91
		   },{
			   xtype:'button',
			   text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.save'),
			   disabled: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/modifyOrSaveTfrPickupBillDetailButton'),
			   hidden: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/modifyOrSaveTfrPickupBillDetailButton'),
			   plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			       seconds:3
			   }),
			   labelWidth:100,
			   columnWidth:.06,
			   handler:function(){
				    var records = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.store.data.items;
					//中转提货清单主键id
					var airTransferPickUpBillId = airfreight.airQueryModifyTfrPickupBill.airTransferPickUpBillId;
					//中转提货清单明细主键id
					var jsonArray = new Array();
					for (var i = 0; i< records.length; i++){
						jsonArray.push(records[i].data);
					}
					var jsons = {airTransPickupBillVo:{modifyTransPickupDetailList:jsonArray,airTransferPickUpBillId:airTransferPickUpBillId}};
					Ext.Ajax.request({
						async: false,
						url:airfreight.realPath('modifyOrSaveTfrPickupBillDetail.action'),
						jsonData:jsons,
						success:function(response){
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage'), airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.saveSuccess'));
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
		        			Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage'),result.message);
						}
					});
			   }
		   }]
	}]
});
//修改中转提货清单
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.editAirwayBillWindow',{
	extend:'Ext.window.Window',
	modal:true,
	closable:true,
	title: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.modifyTransfrInventory'),
	closeAction:'hide',
	width: 1100,
	layout: 'column',
	items:[
		      airfreight.airQueryModifyTfrPickupBill.tfrPickupBillExport = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillExport'),
		      airfreight.airQueryModifyTfrPickupBill.tfrPickupBillMainMessage = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillMainMessage'),
		      airfreight.airQueryModifyTfrPickupBill.tfrPickupBillSelect = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillSelect'),
		      airfreight.airQueryModifyTfrPickupBill.tfrPickupBillAddAirWaybill = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillAddAirWaybill'),
		      airfreight.airQueryModifyTfrPickupBill.tfrPickupBillDelete = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillDelete'),
		      airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillGridList'),
		      airfreight.airQueryModifyTfrPickupBill.tfrPickupBillDisablePanel = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillDisablePanel')
	      ],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
	}
});
/*********************************************************************************/


/**
 *中转明细 
 */
Ext.define('Foss.airfreight.airQueryModifyTfrPickupBill.viewTfrPickupBill',{
	extend:'Ext.grid.Panel',
	frame:true,
	title: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.view'),
	layout:'column',
	emptyText: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.searchResultInexistence'),
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			disabled: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/ModifyTfrPickupBillButton'),
			hidden: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/ModifyTfrPickupBillButton'),
			handler: function(grid, rowIndex, colIndex){
				airfreight.airQueryModifyTfrPickupBill.tfrPickupBillAddAirWaybill.items.items[0].setValue(null);
				var record = grid.getStore().getAt(rowIndex);
				airfreight.airQueryModifyTfrPickupBill.tfrPickupbill = record;
				//保存主键id
				airfreight.airQueryModifyTfrPickupBill.airTransferPickUpBillId = record.data.id;
				Ext.Ajax.request({
					async: false,
					url:airfreight.realPath('queryTfrPickupBillDetail.action'),
					params : {
						'airTransPickupBillVo.airTransPickupBillDto.airTransferPickUpBillId' : record.data.id
					},
					success:function(response){
						var result = Ext.decode(response.responseText);
						var airTransPickupDetailList = result.airTransPickupBillVo.airTransPickupDetailList;
						Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.editAirwayBillWindow').show();
						var tfrPickupBillMainMessage = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillMainMessage.items.items;
						var tfrPickupBillSelect = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillSelect.items.items;
						for (var i = 0; i< tfrPickupBillMainMessage.length; i++){
							if(tfrPickupBillMainMessage[i].name == 'airTransferPickupbillNo'){
								tfrPickupBillMainMessage[i].setValue(record.data.airTransferPickupbillNo);
							}else if(tfrPickupBillMainMessage[i].name == 'arrvRegionName'){
								tfrPickupBillMainMessage[i].setValue(record.data.arrvRegionName);
							}else if(tfrPickupBillMainMessage[i].name == 'deptOrgName'){
								tfrPickupBillMainMessage[i].setValue(record.data.destOrgName);
							}else if(tfrPickupBillMainMessage[i].name == 'transferFlightNo'){
								tfrPickupBillMainMessage[i].setValue(record.data.transferFlightNo);
							}else if(tfrPickupBillMainMessage[i].name == 'transferDate'){
								tfrPickupBillMainMessage[i].setValue(record.data.transferDate);
							}
						}
						for(var j = 0; j<tfrPickupBillSelect.length; j++){
							if(tfrPickupBillSelect[j].name == 'airLineTwoletter'){
								tfrPickupBillSelect[j].setCombValue(record.data.airLineCode,record.data.airLineCode)
							}
							if(tfrPickupBillSelect[j].name == 'airWaybillNo'){
								tfrPickupBillSelect[j].setValue(record.data.airWaybillNo)
							}
						}
						airfreight.airQueryModifyTfrPickupBill.tfrPickupBillGridList.store.loadData(airTransPickupDetailList);
						var toolbars = airfreight.airQueryModifyTfrPickupBill.tfrPickupBillDisablePanel.down('toolbar').items.items;
						var currentDeptName = FossUserContext.getCurrentUserEmp().empName;
						var createTime = Ext.Date.format(new Date(),'Y-m-d H:i:s');
						   for(var j=0;j<toolbars.length;j++){
							   if(toolbars[j].dataIndex=='createUserName'){
								   toolbars[j].setValue(currentDeptName);
							   }else if (toolbars[j].dataIndex=='createTime'){
								   toolbars[j].setValue(createTime);   
							   }
						   }
					}
				});
			}
		},{
			iconCls: 'deppon_icons_delete',
			disabled: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/modifyAirPickupbillButton'),
			hidden: !airfreight.airQueryModifyTfrPickupBill.isPermission('airfreight/modifyAirPickupbillButton'),
			handler: function(grid, rowIndex, colIndex){
				Ext.MessageBox.confirm(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage'),'确认删除中转提货清单？',
						function(btn){
						 if(btn == 'yes'){
								//获取当前record对象
								var record = grid.getStore().getAt(rowIndex);
								var params= {
										'airTransPickupBillVo.airTransPickupBillDto.airTransferPickUpBillId' : record.data.id
				    			};
								Ext.Ajax.request({
									async: false,
									url:airfreight.realPath('deleteTfrAirPickupbillById.action'),
									params:params,
									success:function(response){
										var result = Ext.decode(response.responseText);
										//后台删除成功，前台删除本条记录
										var store=grid.getStore();
										store.removeAt(rowIndex);
									},		
									exception:function(response){
										var result = Ext.decode(response.responseText);
										//提示信息
										Ext.Msg.alert(airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.promptMessage'),result.message);
									}
								});
						 }

					});
			}
		}],
		text:airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.operate'),
	    width: 40,
	    dataIndex: 'id'
	},{
		text:  airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.airTransferPickupbillNo'),
		flex: 1,
		dataIndex: 'airTransferPickupbillNo'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.airLineTwoletter'),
		flex: 1,
		dataIndex: 'airLineCode'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.dedtOrgName'),
		flex: 1,
		dataIndex: 'destOrgName'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.arrvRegionName'),
		flex: 1,
		dataIndex: 'arrvRegionName'
	},{
		text:  airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.transferFlightNo'),
		flex: 1,
		dataIndex: 'transferFlightNo'
	},{
		text:  airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.airMakeTfrPickupGoodsList.transferDate'),
		flex: 1,
		dataIndex: 'transferDate',
		format: 'Y-m-d',
		xtype:'datecolumn'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.createUser'),
		flex: 1,
		dataIndex: 'createUserName'
	},{
		text: airfreight.airQueryModifyTfrPickupBill.i18n('foss.airfreight.public.createTime'),
		flex: 1.5,
		dataIndex: 'createTime',
		format: 'Y-m-d H:i:s',
		xtype:'datecolumn'
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.TfrPickupBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		airfreight.airQueryModifyTfrPickupBill.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 *渲染查询_修改中转提货清单 
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	var queryform = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.queryform');
	var addTfrPickupBill = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.addTfrPickupBill');
	var viewTfrPickupBill = Ext.create('Foss.airfreight.airQueryModifyTfrPickupBill.viewTfrPickupBill');
	//需要新增的运单明细map
	airfreight.airQueryModifyTfrPickupBill.addTfrPickupBillMap = new Ext.util.HashMap();
	//需要删除的运单明细map
	airfreight.airQueryModifyTfrPickupBill.delTfrPickupBillMap = new Ext.util.HashMap();
	//需要修改的运单明细map
	airfreight.airQueryModifyTfrPickupBill.addTfrPickupBillDetailNotesMap = new Ext.util.HashMap();
	//需要调用结算的map
	airfreight.airQueryModifyTfrPickupBill.callStlMap = new Ext.util.HashMap();
	//需要更新logger日志的map
	airfreight.airQueryModifyTfrPickupBill.modifyLogger = new Ext.util.HashMap();
	airfreight.airQueryModifyTfrPickupBill.queryform = queryform;
	//根据当前部门查找所属空运总调
	Ext.Ajax.request({
		//设置后台返回的合票号
		async: false,
		url:airfreight.realPath('queryAirDispatch.action'),
		params : {'airDispatchVo.deptCode':FossUserContext.getCurrentDept().code},
		success:function(response){
			var result = Ext.decode(response.responseText);
			var deptEntity = result.airDispatchVo.orgAdministrativeInfoEntity;
			var createDeptCode = deptEntity.code;
			var createDeptName = deptEntity.name;
			airfreight.airQueryModifyTfrPickupBill.createDeptCode = createDeptCode;
			airfreight.airQueryModifyTfrPickupBill.createDeptName = createDeptName;
			airfreight.airQueryModifyTfrPickupBill.queryform.getForm().findField('createOrgName').setCombValue(createDeptName,createDeptCode);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//提示信息
			Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
		}
	});
	
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airQueryModifyTfrPickupBill_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [queryform,addTfrPickupBill,viewTfrPickupBill],
		renderTo: 'T_airfreight-airQueryModifyTfrPickupBill-body'
	});
});