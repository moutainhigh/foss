
//查询model
Ext.define('Foss.airfreight.maketfrPickGoodsList.PickGoodsQueryModel',{
	extend:'Ext.data.Model',
	fields: [
	      {name:'airTransferPickupbillNo',type:'string'},
	      {name:'arrvRegionCode',type:'string'},
	      {name:'destOrgCode',type:'string'},
	      {name:'transferFlightNo',type:'string'},
	      {name:'transferDate',type:'date'},
	      {name:'airLineName',type:'string'},
	      {name:'airWaybillNo',type:'string'},
	      {name:'waybillNo',type:'string'}
	      
	]
});
//收集中转提货清单保存数据
Ext.define('Foss.airfreight.maketfrPickGoodsList.AirTranDataCollectionEntity',{
	extend:'Ext.data.Model',
	fields: [
	      {name:'ids',type:'string'},
	      {name:'airTransferPickupbillNo',type:'string'},
	      {name:'arrvRegionCode',type:'string'},
	      {name:'destOrgCode',type:'string'},
	      {name:'arrvRegionName',type:'string'},
	      {name:'destOrgName',type:'string'},
	      {name:'transferFlightNo',type:'string'},
	      {name:'transferDate',type:'date'},
	      {name:'billNoTotal',type:'number'},
	      {name:'goodsQtyTotal',type:'number'},
	      {name:'grossWeightTotal',type:'number'},
	      {name:'deliverFeeTotal',type:'number'},
	      {name:'arrivalFeeTotal',type:'number'},
	      {name:'createUserName',type:'string'},
	      {name:'createTime',type:'date'},
	      {name:'airWaybillId',type:'string'},
	      {name:'notes',type:'string'},
	      {name:'flag',type:'string'}
	]
});

//空运代理网点
Ext.define('Foss.commonSelector.AirAgencyDeptSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonairagencydeptselector',
	displayField : 'agentDeptName',// 显示名称
	valueField : 'agentDeptName',// 值
	active : 'Y',//有效状态
	destCityCode:null,//目的站城市代码
	queryParam : 'outerBranchEntity.agentDeptName',// 查询参数
	showContent : '{agentDeptName}&nbsp;&nbsp;&nbsp;{agentDeptCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.AirAgencyDeptStore');
		//加载前事件
	    me.store.addListener('beforeload', function(store, operation, eOpts) {
	    	var searchParams = operation.params;
			//如果查询参数为空
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
	      //目的站城市代码
	      if (!Ext.isEmpty(me.destCityCode)) {
	    	  searchParams['outerBranchEntity.cityCode'] = me.destCityCode;
	      }
	      searchParams['outerBranchEntity.active'] = me.active;
		})
		me.callParent([cfg]);
	}
});

Ext.define('Foss.airfreight.maketfrPickGoodsList.PickGoodsForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	callEdi : function(ids,airWaybillNo,callIsNotEdiFlag,transferFlightNo){
		var flagSuccess = true;
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
						flagSuccess = false;
						Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
								, airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.uploadingTransfrInventory'));
						return false;
					}
				}
			})
		return flagSuccess
	},
	defaults: {
		margin:'5 5 5 5'
	},
	items:[
	       //导出中转提货清单
		Ext.create('Ext.toolbar.Toolbar', {
				   xtype:'toolbar',
				   dock:'right',
				   layout:'column',
				   defaultType:'button',
				   width:1024,
				   items:[{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.80
				   },{
					   text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.newAdd'),
					   columnWidth:.06,
					   handler: function(){
							var tabPanel1 = Ext.getCmp('mainAreaPanel');
							var activeTab = tabPanel1.getActiveTab();
							tabPanel1.remove(activeTab);
							addTab('T_airfreight-airMakeTfrPickupGoodsList' ,
									 airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.makeTransfrInventory'), airfreight.realPath('airMakeTfrPickupGoodsList.action'));
					   }  
				   },{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.08
				   },{
					   text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.export'),
					   disabled: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/uploadTranPickupCallEdiButton'),
					   hidden: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/uploadTranPickupCallEdiButton'),
					   columnWidth:.06,
					   handler:function(){
						   var selectRecords = airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.getSelectionModel().getSelection();
						   var transferFlightNo = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('transferFlightNo').value;
						   //原设计是用返回正单号，后台查询正单号进行查询操作，但因为设计原因，会有重复数据
			               //故返回中转单号，唯一能表示此中转提货清单，参数不变
						   //var airWaybillNo = selectRecords[0].data.airWaybillNo;
						   var airWaybillNo = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('airTransferPickupbillNo').value;
						   var saveIsNotSuccess = airfreight.airMakeTfrPickupGoodsList.saveIsNotSuccess;
						   if(!saveIsNotSuccess){
							   Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
									   ,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.notSaveExprot'));
							   return false;
						   }
						   if(selectRecords.length==0){
							    Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
							    		, airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.qxzydcdqdmx'));
							    return false;
						   }
						   var jsonArryIds = new Array();
					   	   for(var i=0;i<selectRecords.length;i++){
					   		   jsonArryIds.push(selectRecords[i].data.id);
					   	   }
				   	   	   Ext.MessageBox.buttonText.yes = airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.yes');  
				   	   	   Ext.MessageBox.buttonText.no = airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.no'); 
					   	   Ext.Msg.confirm(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
					   			   ,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.sendEdi'), function(btn,text){
								if(btn == 'yes'){
									var callIsNotEdiFlag = 'YES'; 
									var flag = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.callEdi(jsonArryIds,airWaybillNo,callIsNotEdiFlag,transferFlightNo);
									if(flag){
										Ext.ux.Toast.msg(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
												,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.callEdiSuccess'));
										return false;
									}
								}else if(btn == 'no'){
									var callIsNotEdiFlag = 'NO'; 
									var flag = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.callEdi(jsonArryIds,airWaybillNo,callIsNotEdiFlag,transferFlightNo);
									if(flag){
										Ext.ux.Toast.msg(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
												,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.exportSuccess'));
										return false;
									}
								}
							})
					   }
				   }]
			}),
		//提货清单信息
			airfreight.airMakeTfrPickupGoodsList.saveTfr = Ext.create('Ext.form.FieldSet',{
		    height:105,
		    width:1040,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.airTransferPickupbillNo'),
				name: 'airTransferPickupbillNo',
				allowBlank: false,
				readOnly:true,
				columnWidth:.25
			},{
				xtype:'commonairporwithcitynametselector',
				fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.arrvRegionName'),
				name:'arrvRegionCode',
				displayField : 'cityName',
				valueField : 'cityCode',
				allowBlank: false,
				columnWidth:.25,
				listeners: {
					select : function(me, records, eOpts){
						if(Ext.isEmpty(me.getValue())){
							return;
						}else{
							var tempForm = me.up('form').getForm();
						}
						var record = records[0].data;
						var destCityCode = record.cityCode;
						airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('destOrgCode').destCityCode = destCityCode;
    					airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('destOrgCode').value = '';
    					airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('destOrgCode').store.load();
					}
				}
			},{
				xtype:'commonairagencydeptselector',
				fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.dedtOrgName'),
				name:'destOrgCode',
				displayField : 'agentDeptName',// 显示名称
				valueField : 'agentDeptCode',// 值
				allowBlank: false,
				columnWidth:.25,
    			listeners: {
    				select: function(combo, record, index) {
    					var recordAgent = record[0].data;
    					var tfrPickGoodsForm = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm();
    	            	tfrPickGoodsForm.findField('arrvRegionCode').setCombValue(recordAgent.cityName, recordAgent.cityCode);
    	            }
    			}
			},{
				xtype:'container',
				columnWidth:.86
			},{
				fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.transferFlightNo'),
				name:'transferFlightNo',
				allowBlank: false,
				columnWidth:.25
			},{
				xtype: 'datefield',
		        fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.transferDate'),
		        name: 'transferDate',
		        format: 'Y-m-d',
		        allowBlank: false,
		        columnWidth:.25
			}]
		}),
		//查询条件
		airfreight.airMakeTfrPickupGoodsList.queryTfr	= Ext.create('Ext.form.FieldSet',{
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
    			fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.airLineTwoletter'),
    			name: 'airLineTwoletter',
    			columnWidth:.45
			},{
				fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.airWaybillNo'),
				name:'airWaybillNo',
				columnWidth:.45
			},{
				xtype:'button',
				disabled: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/queryAirPickupbillDetailInfoButton'),
				hidden: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/queryAirPickupbillDetailInfoButton'),
				text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.search'),
				columnWidth:.10,
				handler:function(){
					var v = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().getValues();
					if(Ext.isEmpty(v.airWaybillNo)){
						Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
								,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.pleaseInputWaybillNo'));
						return null;
					}
//					if(Ext.isEmpty(v.airLineTwoletter)){
//						Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
//								,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.pleaseInputAirLineTwoletter'));
//						return null;
//					}
					var params= {
						'airTransPickupBillVo.airTransPickupBillDto.airLineTwoletter' : v.airLineTwoletter,
						'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo' : v.airWaybillNo
					}
					Ext.Ajax.request({
						url:airfreight.realPath('queryAirPickupbillDetailInfo.action'),
						params: params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							//将查询结果赋值给records
							var records = result.airTransPickupBillVo;
							if(records.airPickupbillDetailList.length==0){
								Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
										,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.airWaybillNo1')+v.airWaybillNo+airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.inexistence'));
								return false;
							}
							//将records绑定到grid列表中
							airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.store.loadData(records.airPickupbillDetailList);
							//给form表单中的目的站、到达网点赋值
							//046130-foss-xuduowei,2013-03-07,修改目的站，到达网点code
							//airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('arrvRegionName').setCombValue(records.airPickupbillEntity.arrvRegionName, records.airPickupbillEntity.arrvRegionCode);
							//airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('destOrgName').setCombValue(records.airPickupbillEntity.destOrgName, records.airPickupbillEntity.destOrgCode);

							//airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord.data.arrvRegionCode = records.airPickupbillEntity.arrvRegionCode;
							//airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord.data.destOrgCode = records.airPickupbillEntity.destOrgCode;
							airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord.data.transferFlightNo = records.airPickupbillEntity.flightNo;
							var flightDate = records.airPickupbillEntity.flightDate;
							airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord.data.transferDate =Ext.Date.format(new Date(flightDate),'Y-m-d'); 
							//保留查询条件
							airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord.data.airWaybillNo = v.airWaybillNo;
							airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.loadRecord(airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord);		
							airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('destOrgCode').setCombValue(records.airPickupbillEntity.destOrgName, records.airPickupbillEntity.destOrgCode);
							airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().findField('arrvRegionCode').setCombValue(records.airPickupbillEntity.arrvRegionName, records.airPickupbillEntity.arrvRegionCode);
							
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
						}
					});
				}
			}]
		}),
		//运单号添加
		airfreight.airMakeTfrPickupGoodsList.addTfr = Ext.create('Ext.form.FieldSet',{
		    height:55,
		    width:430,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.waybillNo'),
				name:'receiverName',
				columnWidth:.65
			},{
				xtype:'button',
				disabled: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/waybillNoAddToTransferDetailButton'),
				hidden: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/waybillNoAddToTransferDetailButton'),
				text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.add'),
				plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
					seconds:2
				}),
				columnWidth:.15,
				handler:function(){
					//获取运单号
					var waybillNo = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().getFields().items[7].value;
					if(Ext.isEmpty(waybillNo)){
						Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
								,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.waybillNotNull'));
						return null;
					}
					var pickGoodsstoreLength =  airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.store.data.items.length;
					var pickGoodsResult = airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.store.data.items;
					for(var i=0;i<pickGoodsstoreLength;i++){
						if(waybillNo==pickGoodsResult[i].data.waybillNo){
							Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.inventoryDetailExist')+waybillNo+airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.nonRepeatableRead'));
							return null;
						}
					}
					var params= {
	    					'airTransPickupBillVo.airTransPickupBillDto.waybillNo': waybillNo
	    			};
					Ext.Ajax.request({
						url:airfreight.realPath('waybillNoAddToTransferDetail.action'),
						params: params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							//获取合票清单明细
							var transfersNumber = result.airTransPickupBillVo.airPickupbillDetailEntity;
							if(!Ext.isEmpty(transfersNumber)){
								transfersNumber.flag = 'Y';
								airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.store.add(transfersNumber);
								 Ext.ux.Toast.msg(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
										 ,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.addSuccess'));
								return  null;
							}else{
								Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
										, airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.addWaybillNoInexistence'));
								return  null;
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
						}
					});
				}
			}]
		}),
		//设置运单明细收缩展开样式
		airfreight.airMakeTfrPickupGoodsList.delTfr = Ext.create('Ext.form.Panel',{
			height:30,
			width:300,
			frame:false,
			border:false,
			defaults:{
				margin:'0 5 5 5',
				readOnly:true
			},
			items:[{
			    dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'bottom',
		        layout:'column',
		        items: [{
		        	xtype:'button',
		        	text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.delete'),
		        	handler:function(){
          				var record = airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.getSelectionModel().getSelection();
          				if(record.length!=0){
          					//将选择的record对象从列表中移除
          					airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.store.remove(record);
          					Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
          							,airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.deleteSuccess'));
          				}else{
          					Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage')
          							, airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airMakeTfrPickupGoodsList.pleaseSelectRequire'));
          				}
		        	}
		        }]
		    }]
		}],
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({},config);
				me.callParent([cfg]);
			}
		})
	]
});
//合大票清单model
Ext.define('Foss.airfreight.maketfrPickGoodsList.pickGoodsResultModel',{
	extend:'Ext.data.Model',
	fields:[
			{
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
				name:'notes',
				type:'string'
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
				name:'id',
				type:'string'
			},{
				name:'flag',
				type:'string'
			}
	 ]
});
//合大票清单store
Ext.define('Foss.airfreight.maketfrPickGoodsList.pickGoodsResultStore',{
	extend: 'Ext.data.Store',
	model:'Foss.airfreight.maketfrPickGoodsList.pickGoodsResultModel',
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
			var toolbarArray = airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.down('toolbar').query('textfield');
			for(var j=0;j<toolbarArray.length;j++){
				if(count==0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count==1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count==2){
					toolbarArray[j].setValue(grossWeightTotal + '  ' + airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.kilo'));
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

//运单明细列表
Ext.define('Foss.airfreight.maketfrPickGoodsList.pickGoodsResult',{
	extend:'Ext.grid.Panel',
	title:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.inventoryDetail'),
	frame:true,
	border:true,
	layout:'column',
	emptyText: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.searchResultInexistence'),
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			handler: function(grid, rowIndex, colIndex){
				if(airfreight.airMakeTfrPickupGoodsList.saveTfrPickUpbill){
					return false;
				}
				//获取当前record对象
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
						//window显示
						airfreight.airMakeTfrPickupGoodsList.updateWindowTfrPickupBill = Ext.create('Foss.airfreight.maketfrPickGoodsList.updateWindowPickGoodsList').show();
						//将grid列中的数据绑定到window中的form中
						airfreight.airMakeTfrPickupGoodsList.updateWindowPickGoodsList.getForm().loadRecord(record);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
						return false;
					}
				});
				
				
		
			}
		}],
		text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.operate'),
	    width: 40,
	    dataIndex: 'id'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.waybillNo'),
		flex: 0.6,
		dataIndex: 'waybillNo'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.arrvRegionName'),
		flex: 0.6,
		dataIndex: 'arrvRegionName'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airChangeInventory.goodsName'),
		flex: 0.6,
		dataIndex: 'goodsName'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.poll'),
		flex: 0.6,
		dataIndex: 'goodsQty'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.weightKilo'),
		flex: 0.6,
		dataIndex: 'weight'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.billingWeightKilo'),
		flex: 0.6,
		dataIndex: 'billingWeight'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.pickupType'),
		flex: 0.6,
		dataIndex: 'pickupType',
		renderer: function(value){
			if(value != null){
				var pickupType = FossDataDictionary.rendererSubmitToDisplay (value,'PICKUPGOODSAIR');
				return pickupType;
			}
		}
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.deliverFee'),
		flex: 0.6,
		dataIndex: 'deliverFee'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.arrivalFee'),
		flex: 0.6,
		dataIndex: 'arrivalFee'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.collectionFee'),
		flex: 0.6,
		dataIndex: 'collectionFee'
	},{
		text: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.notes'),
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
			   fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.ticket'),
			   columnWidth:.10,
			   dataIndex: 'billNoTotal'
		   },{
			   fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.poll'),
			   columnWidth:.10,
			   dataIndex: 'goodsQtyTotal'
		   },{
			   fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.weight'),
			   columnWidth:.12,
			   hideValue:'',
			   dataIndex: 'grossWeightTotal'
		   },{
			   fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.kiloDeliverFee'),
			   labelWidth:80,
			   columnWidth:.15,
			   dataIndex: 'deliverFeeTotal'
		   },{
			   fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.arrivalFee'),
			   labelWidth:60,
			   columnWidth:.15,
			   dataIndex: 'arrivalFeeTotal'
		   }]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.maketfrPickGoodsList.pickGoodsResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.callParent([cfg]);
	}
});
Ext.define('Foss.airfreight.maketfrPickGoodsList.toolbarMessage',{
	extend:'Ext.form.Panel',
	frame:false,
	border:false,
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
			   fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.createUser'),
			   labelWidth:55,
			   columnWidth:.15,
			   dataIndex: 'createUserName'
		   },{
			   fieldLabel:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.createTime'),
			   labelWidth:75,
			   columnWidth:.20,
			   dataIndex: 'createTime'
		   },{
			   fieldLabel:'',
			   columnWidth:.91
		   },{
			   xtype:'button',
			   text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.save'),
			   disabled: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/addAirTransPickBILLAirPickupBillButton'),
			   hidden: !airfreight.airMakeTfrPickupGoodsList.isPermission('airfreight/addAirTransPickBILLAirPickupBillButton'),
			   labelWidth:100,
			   columnWidth:.06,
			   handler:function(){
			   	   var hiddenButton2 = airfreight.airMakeTfrPickupGoodsList.toolbarTfrMessage.down('toolbar').query('button');
				   hiddenButton2[0].disable(true);
				   var record = airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.store.data.items;
				   if(!airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().isValid()){
					   Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.includebx'));
					   hiddenButton2[0].setDisabled(false);
					   return false;
				   }
				   if(record.length==0){
					   Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.inventoryDetailNotData'));
					   hiddenButton2[0].setDisabled(false);
					   return false;
				   }
				   var arrys = {};
				   var fromValues = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm().getValues();
				   //获取中转单号
				   arrys['airTransferPickupbillNo'] = fromValues['airTransferPickupbillNo'];
				   //获取目的站
				   arrys['arrvRegionCode'] = fromValues['arrvRegionCode'];
				   //获取到达网点
				   arrys['destOrgCode'] = fromValues['destOrgCode'];
				   //获取中转航班号
				   arrys['transferFlightNo'] = fromValues['transferFlightNo'];
				   //获取中转日期
				   arrys['transferDate'] = fromValues['transferDate'];
				   //046130-foss-xuduowei,2013-03-07,增加目的站名称和到达网点名称
				   var pickGoodsForm = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm();
				   var arriveRegion = pickGoodsForm.findField('arrvRegionCode');
				   var destOrg = pickGoodsForm.findField('destOrgCode');
				   var arrvRegionName = arriveRegion.rawValue;
				   if(arriveRegion.store.findRecord('airportCode',fromValues['arrvRegionCode'],0,false,true,true)){
				       arrvRegionName = arriveRegion.store.findRecord('airportCode',fromValues['arrvRegionCode'],0,false,true,true).get('cityName');
				   }
				   var destOrgName = destOrg.rawValue;
				   if(destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true)){
				       destOrgName = destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true).get('agentDeptName');
				   }
				   //目的站名称
				   arrys['arrvRegionName'] = arrvRegionName;
				   //到达网点名称
				   arrys['destOrgName'] = destOrgName;
				   
				   //获取toolbar统计栏信息
				   var toolbars = airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.down('toolbar').items.items;
				   for(var j=0;j<toolbars.length;j++){
					   if(toolbars[j].dataIndex=='grossWeightTotal'){
						   arrys[toolbars[j].dataIndex] = toolbars[j].hideValue;
					   }else{
						   arrys[toolbars[j].dataIndex] = toolbars[j].value;   
					   }
				   }
				   //获取制单人、制单时间
				   var getCreateMesages = airfreight.airMakeTfrPickupGoodsList.toolbarTfrMessage.down('toolbar').items.items;
				   for(var m=0;m<getCreateMesages.length;m++){
					   arrys[getCreateMesages[m].dataIndex] = getCreateMesages[m].value;
				   }
				   //航空正单组建id
				   arrys['airWaybillId'] = record[0].data.airWaybillId;
				   var jsonArry = new Array();
				   //获取航空正单明细组建id
				   var ids = "";
	           	   for(var i=0;i<record.length;i++){
	           		    jsonArry.push(record[i].data);
	           		    if(record.length-1==i){
	           				ids = ids + obj.id;
				 				continue;
				 		}
	           			ids = ids + obj.id+',';
	           	   }
	           	   arrys['ids'] = ids;
	           	   var records = Ext.create('Foss.airfreight.maketfrPickGoodsList.AirTranDataCollectionEntity',arrys);
	           	   //请求后台保存制作中转提货清单信息
	           	   var jsons = {airTransPickupBillVo:{airTranDataCollectionEntity:records.data,airPickupbillDetailList:jsonArry}};
	           	   Ext.Ajax.request({
		           		url:airfreight.realPath('addAirTransPickBILLAirPickupBill.action'),
		           		jsonData:jsons,
		           		timeout: 600000,
		        		success:function(response){
		        			var result = Ext.decode(response.responseText);
		        			airfreight.airMakeTfrPickupGoodsList.saveIsNotSuccess = true;
		        			airfreight.airMakeTfrPickupGoodsList.saveTfrPickUpbill = true;
		 				    airfreight.airMakeTfrPickupGoodsList.saveTfr.setDisabled(true);
						    airfreight.airMakeTfrPickupGoodsList.queryTfr.setDisabled(true);
						    airfreight.airMakeTfrPickupGoodsList.addTfr.setDisabled(true);
						    var hiddenButton1 = airfreight.airMakeTfrPickupGoodsList.delTfr.down('toolbar').query('button');
						    hiddenButton1[0].disable(true);
		        			Ext.ux.Toast.msg(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.saveSuccess'));
		        		},
		        		exception:function(response){
		        			hiddenButton2[0].setDisabled(false);
		        			var result = Ext.decode(response.responseText);
		        			Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.airSpace.msg.saveFailure'),result.message);
		        		}
	           	   });
			   }
		   }]
	}]
});
//修改清单信息window
Ext.define('Foss.airfreight.maketfrPickGoodsList.updateWindowPickGoodsList',{
	extend: 'Ext.window.Window',
	title:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.modifyInventory'),
	modal:true,
	closeAction:'hide',
	width: 580,
	height: 300,
	layout: 'auto',
	items: [
	        //修改清单信息form表单
       airfreight.airMakeTfrPickupGoodsList.updateWindowPickGoodsList = Ext.create('Ext.form.Panel',{
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
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.waybillNo'),
        		name: 'waybillNo',
        		margin: '0 5 5 5',
        		readOnly:true
        	},{
        		xtype:'container',
        		columnWidth:.86
        	},{
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.billingWeight'),
        		name: 'billingWeight'
        	},{
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.deliverFee'),
        		name: 'deliverFee'
        	},{
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.poll'),
        		name: 'goodsQty'
        	},{
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.receiverName'),
        		name: 'receiverName'
        	},{
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.phone'),
        		name: 'receiverContactPhone'
        	},{
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.address'),
        		name: 'receiverAddress'
        	},{
        		fieldLabel: airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.notes'),
        		name: 'notes',
        		columnWidth:.98,
        		allowBlank:true
        	},{
        		xtype:'container',
        		columnWidth:.68
        	},{
        		xtype:'button',
        		text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.ensure'),
        		columnWidth:.15,
        		handler:function(){
        			if(!airfreight.airMakeTfrPickupGoodsList.updateWindowPickGoodsList.getForm().isValid()){
        				Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.includebx'));
        				return false; 
        			}
        			//获取表单
        			var formValue = this.up('form').getForm();
        			//得到表单record对象
        			var newRecord = formValue.getRecord();
        			//将修改的数据update到record对象中
        			formValue.updateRecord(newRecord);
        			airfreight.airMakeTfrPickupGoodsList.pickGoodsResult.store.update(formValue.getRecord());
        			airfreight.airMakeTfrPickupGoodsList.updateWindowTfrPickupBill.close();
        			//Ext.Msg.alert(airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.promptMessage'),'修改成功!');
        		}
        	},{
        		xtype:'button',
        		text:airfreight.airMakeTfrPickupGoodsList.i18n('foss.airfreight.public.cancel'),
        		columnWidth:.15,
        		handler:function(){
        			this.up('window').close();
        		}
        	}]
        })
	]
});
//渲染制作中转提货清单
Ext.onReady(function(){
	Ext.QuickTips.init();
	//form表单
	var tfrPickGoodsForm = Ext.create('Foss.airfreight.maketfrPickGoodsList.PickGoodsForm');
	//gird列表
	var pickGoodsResult = Ext.create('Foss.airfreight.maketfrPickGoodsList.pickGoodsResult');
	//window窗口
	var toolbarTfrMessage = Ext.create('Foss.airfreight.maketfrPickGoodsList.toolbarMessage');
	//修改清单信息
	var pickGoodsQueryRecord = Ext.create('Foss.airfreight.maketfrPickGoodsList.PickGoodsQueryModel');
	airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm = tfrPickGoodsForm;
	airfreight.airMakeTfrPickupGoodsList.pickGoodsResult = pickGoodsResult;
	airfreight.airMakeTfrPickupGoodsList.toolbarTfrMessage = toolbarTfrMessage;
	airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord = pickGoodsQueryRecord;
	airfreight.airMakeTfrPickupGoodsList.saveTfrPickUpbill = false;
	Ext.Ajax.request({
		//设置后台返回的中转单号
		async: false,
		url:airfreight.realPath('generateTransfersNumber.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			//获取中转单号
			var transfersNumber = result.airTransPickupBillVo.airTransPickupBillDto;
			//设置中转单号
			airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord.data.airTransferPickupbillNo= transfersNumber.airTransferPickupbillNo;
			var toolbarMessageArray = airfreight.airMakeTfrPickupGoodsList.toolbarTfrMessage.down('toolbar').query('textfield');
			var createUserName = FossUserContext.getCurrentUserEmp().empName;
			//设置制单人
			toolbarMessageArray[1].setValue(createUserName);
			//设置制单时间
			var createTime = Ext.Date.format(new Date(),'Y-m-d H:m:s');
			toolbarMessageArray[2].setValue(createTime);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
		}
	});
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airMakeTfrPickupGoodsList_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [tfrPickGoodsForm,pickGoodsResult,toolbarTfrMessage],
		renderTo: 'T_airfreight-airMakeTfrPickupGoodsList-body'
	});
	//加入form元素并初始化中转单号等数据...
	airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.loadRecord(airfreight.airMakeTfrPickupGoodsList.pickGoodsQueryRecord);
	var pickGoodsResetForm = airfreight.airMakeTfrPickupGoodsList.tfrPickGoodsForm.getForm();
	pickGoodsResetForm.findField('arrvRegionCode').reset();
	pickGoodsResetForm.findField('destOrgCode').reset();
	pickGoodsResetForm.findField('transferFlightNo').reset();
	pickGoodsResetForm.findField('transferDate').reset();
	airfreight.airMakeTfrPickupGoodsList.saveIsNotSuccess = false;
});