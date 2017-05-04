/**
 *导出
 */
Ext.define('Foss.airfreight.airChangeInventory.ExportEdi', {
	   extend:'Ext.toolbar.Toolbar',	
	   xtype:'toolbar',
	   dock:'right',
	   layout:'column',
	   defaultType:'button',
	   width:1024,
	   callEdi : function(jsonArray,callIsNotEdiFlag,airWaybillNo){
		    if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
	       	}
		    Ext.Ajax.request({
				url:airfreight.realPath('callHangeListEdi.action'),
				form: Ext.fly('downloadAttachFileForm'),
				method : 'POST',
				params : {'airTransPickupBillVo.ids': jsonArray,
					      'airTransPickupBillVo.callIsNotEdiFlag': callIsNotEdiFlag,
					      'airTransPickupBillVo.airWaybillNo': airWaybillNo
				},
				isUpload: true,
				success:function(response){
					var result = Ext.decode(response.responseText);
					if(result.message!=null){
						Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'),airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.uploadingInventoryError'));
						return false;
					}
				}
			})
		},
	   items:[{
		   xtype:'container',
		   html:'&nbsp;',
		   columnWidth:.94
	   },{
		   text:airfreight.airChangeInventory.i18n('foss.airfreight.public.export') ,
		   disabled: !airfreight.airChangeInventory.isPermission('airfreight/callHangeListEdiButton'),
		   hidden: !airfreight.airChangeInventory.isPermission('airfreight/callHangeListEdiButton'),
		   columnWidth:.06,
		   handler: function(){
			   var recordList = airfreight.airChangeInventory.airChangeInventoryGrid.store.data.items;
			   if(recordList.length==0){
				   Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
						   ,airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.inventoryNotDataNotExprot'));
				   return false;
			   }
			   if(!airfreight.airChangeInventory.isNotSave){
				   Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
						   ,airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.notSaveExport'));
				   return false;
			   }
			   var jsonArray = new Array();
			   var airWaybillNo ='';
			   for(var i = 0; i< recordList.length;i++){
				   if(i==0){
					   airWaybillNo = recordList[i].data.airWaybillNo;
				   }
				   jsonArray.push(recordList[i].data.id);
			   }
			   Ext.MessageBox.buttonText.yes = airfreight.airChangeInventory.i18n('foss.airfreight.public.yes');  
			   Ext.MessageBox.buttonText.no = airfreight.airChangeInventory.i18n('foss.airfreight.public.no'); 
			   Ext.Msg.confirm(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'),airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.sendEdi'), function(btn,text){
					if(btn == 'yes'){
						var callIsNotEdiFlag = 'YES';
						airfreight.airChangeInventory.exportEdi.callEdi(jsonArray,callIsNotEdiFlag,airWaybillNo);
						//发送则调用EDI系统
						Ext.ux.Toast.msg(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
								,airfreight.airChangeInventory.i18n('foss.airfreight.public.exportSuccess'));
					}else if(btn == 'no'){
						var callIsNotEdiFlag = 'NO';
						airfreight.airChangeInventory.exportEdi.callEdi(jsonArray,callIsNotEdiFlag,airWaybillNo);
						//不发送保存在本地
						Ext.ux.Toast.msg(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
								,airfreight.airChangeInventory.i18n('foss.airfreight.public.exportSuccess'));
					}
				})
		   }
	   }]
});

/**
 * 添加运单
 */
Ext.define('Foss.airfreight.airChangeInventory.AddChangeInventory',{
	  extend:'Ext.form.Panel',
	  layout: 'column',
	  frame: true,
	  height:55,
	  width:440,
	  layout:'column',
	  items:[{
			xtype: 'textfield',
			fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.waybillNo'),
			name:'waybillNo',
			allowBlank: false,
			columnWidth:.65
	  },{
			xtype:'button',
			text:airfreight.airChangeInventory.i18n('foss.airfreight.public.add'),
			disabled: !airfreight.airChangeInventory.isPermission('airfreight/queryChangeInventoryDetailButton'),
			hidden: !airfreight.airChangeInventory.isPermission('airfreight/queryChangeInventoryDetailButton'),
			columnWidth:.15,
			plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
				seconds:2
			}),
			handler:function(){
				var waybillNo = airfreight.airChangeInventory.addChangeInventory.getForm().findField('waybillNo').getValue();
				var airChangeInventoryRecord = airfreight.airChangeInventory.airChangeInventoryGrid.store.data.items;
				for(var i=0;i<airChangeInventoryRecord.length;i++){
					var airChangeInventoryWaybillNo = airChangeInventoryRecord[i].data.waybillNo;
					if(waybillNo == airChangeInventoryWaybillNo){
						Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'), 
								airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.waybillNoMessage')+waybillNo+airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.ydmxczbyxcftj'));
						return false;
					}
				}
				var params= {
    					'airTransPickupBillVo.airTransPickupBillDto.waybillNo': waybillNo
    			};
				Ext.Ajax.request({
					url:airfreight.realPath('queryChangeInventoryDetail.action'),
					params: params,
					success:function(response){
						var result = Ext.decode(response.responseText);
						var airPickupbillDetailList = result.airTransPickupBillVo.airChangeInventoryList;
						var airChangeInventoryDetailList = result.airTransPickupBillVo.airChangeInventoryDetailList;
						if(airPickupbillDetailList.length==0){
							Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.waybillNoNot'));
							return false;
						}
						airfreight.airChangeInventory.airPickupbillDetailList = airPickupbillDetailList;
						airfreight.airChangeInventory.airChangeInventoryDetailList = airChangeInventoryDetailList;
						airfreight.airChangeInventory.messagePanel.showAirRevisebillLogger(waybillNo);
						if(!Ext.isEmpty(airPickupbillDetailList)){
							//保留原始数据
							/*for(var i = 0 ;i<airPickupbillDetailList.length;i++){
								airfreight.airChangeInventory.oldAirPickupbillDetailMap.add(airPickupbillDetailList[i].id,airPickupbillDetailList[i]);
							}*/
							
							airfreight.airChangeInventory.airChangeInventoryGrid.store.add(airPickupbillDetailList);
							airfreight.airChangeInventory.addChangeInventory.getForm().reset();
							Ext.ux.Toast.msg(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airChangeInventory.i18n('foss.airfreight.public.addSuccess'));
							return null;
						}else{
							Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.addWaybillFail'));
							return null;
						}
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
					}
				});
			}
	  }]
});
Ext.define('Foss.airfreight.airChangeInventory.delAirChangeInventory',{
	extend:'Ext.container.Container',
	height:55,
	width:440,
	layout : 'column',
	items: [{
		items:[{
			xtype:'button',
			text:airfreight.airChangeInventory.i18n('foss.airfreight.public.delete'),
			handler:function(){
				var delRecord = airfreight.airChangeInventory.airChangeInventoryGrid.getSelectionModel().getSelection();
				if(delRecord.length==0){
					Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
							,airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.selectRequireDelDetail'));
					return false;
				}
				Ext.MessageBox.buttonText.yes = airfreight.airChangeInventory.i18n('foss.airfreight.public.yes');  
				Ext.MessageBox.buttonText.no = airfreight.airChangeInventory.i18n('foss.airfreight.public.no'); 
				Ext.Msg.confirm(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'),airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.everconfirmedDel'), function(btn,text){
					if(btn == 'yes'){
						var view = document.getElementById(airfreight.airChangeInventory.messagePanel.getId()+'-body');
						//删除头信息
						var deleteTitle = function(waybillNo){
							var count = 0;
							var spans = view.getElementsByTagName('span');
							for(var m = 0; m<spans.length; m++){
								if(!spans[m] || !spans[m].id)
									continue;
								var id = spans[m].id;
								if(id.indexOf(waybillNo+'_waybillNo')!=-1 && id.lastIndexOf('span')!=-1){
									count ++;
								}
							}
							if(count<=0){
								var title = document.getElementById(waybillNo +'_waybillNo');
								if(!title)
								return false;
								view.removeChild(title);
							}
						}
						//删除明细
						var deleteChildNotes = function (waybillNo,airWaybillNo){
							var name = waybillNo +'_waybillNo'+airWaybillNo;
							var span = document.getElementsByName(name + 'span');
		  					if(span){
		  						var length = span.length;
		  						for(var j = 0; j<length; j++){
		  							var child = span[0].parentNode;
		  							span[0].innerHTML='';
		  							child.removeChild(span[0]);
		  						}
		  					}
						}
		  				if(delRecord.length!=0){
			  				//记录删除的运单号
			  				var wayBillNoMap = airfreight.airChangeInventory.delWayBillNoMap;
		  					//将选择的record对象从列表中移除
		  					for(var i = 0; i< delRecord.length; i++){
		  						var record = delRecord[i].data;
		  						deleteChildNotes (record.waybillNo,record.airWaybillNo);
		  						deleteTitle(record.waybillNo);
		  						var delRecordWaybillNo = record.waybillNo;
		  						wayBillNoMap.add(delRecordWaybillNo,delRecordWaybillNo);
		  					}
		  					//将已删除的流运单明细保存在map中
		  					airfreight.airChangeInventory.delWayBillNoMap = wayBillNoMap;
	  					airfreight.airChangeInventory.airChangeInventoryGrid.store.remove(delRecord);
	  					Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'), airfreight.airChangeInventory.i18n('foss.airfreight.public.deleteSuccess') );
		  				}
					}else{
						return false;
					}
				});
			}			
		}]
	}]
});
/**
 * 运单明细model
 */
Ext.define('Foss.airfreight.airChangeInventory.airChangeInventoryModel',{
	extend:'Ext.data.Model',
	fields:[
	        {name:'airPickupbillId',type:'string'},
	        {name:'airWaybillId',type:'string'},
	        {name:'waybillNo',type:'string'},
	        {name:'airWaybillNo',type:'string'},
	        {name:'beTransfer',type:'string'},
	        {name:'arrvRegionName',type:'string'},
	        {name:'grossWeight',type:'number'},
	        {name:'billingWeight',type:'number'},
	        {name:'volume',type:'number'},
	        {name:'weight',type:'number'},
	        {name:'goodsQty',type:'number'},
	        {name:'goodsName',type:'string'},
	        {name:'createTime',type:'date',convert:dateConvert},
	        {name:'deliverFee',type:'number'},
	        {name:'arrivalFee',type:'number'},
	        {name:'receiverContactPhone',type:'string'},
	        {name:'pickupType',type:'string'},
	        {name:'collectionFee',type:'number'},
		    {name:'receiverAddress',type:'string'},
		    {name:'receiverName',type:'string'},
		    {name:'billType',type:'string'},
		    {name:'agentCode',type:'string'},
		    {name:'arrvRegionCode',type:'string'},
		    {name:'notes',type:'string'}
	 ]
});
/**
 * 运单明细store
 */
Ext.define('Foss.airfreight.airChangeInventory.airChangeInventoryStore',{
	extend: 'Ext.data.Store',
	model:'Foss.airfreight.airChangeInventory.airChangeInventoryModel',
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
			//送货费
			var deliverFeeTotal = 0;
			//到付费
			var arrivalFeeTotal = 0;
			
			for(var i=0;i<totalArray.length;i++){
				//计算件数
				goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty,0);
				//计算毛重
				grossWeightTotal = grossWeightTotal + Ext.Number.from(totalArray[i].data.weight,0);
				//计算送货费
				deliverFeeTotal = deliverFeeTotal + Ext.Number.from(totalArray[i].data.deliverFee,0);
				//计算到付费
				arrivalFeeTotal = arrivalFeeTotal + Ext.Number.from(totalArray[i].data.arrivalFee,0);
			}
			var count = 0;
			var toolbarArray = airfreight.airChangeInventory.airChangeInventoryGrid.down('toolbar').query('textfield');
			for(var j=0;j<toolbarArray.length;j++){
				if(count==0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count==1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count==2){
					toolbarArray[j].setValue(grossWeightTotal + '  ' + airfreight.airChangeInventory.i18n('foss.airfreight.public.kilo'));
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

/**
 * 修改运单信息
 */
Ext.define('Foss.airfreight.airChangeInventory.updateAirChangeInventoryWindow',{
	extend: 'Ext.window.Window',
	title:airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.modifyInventory'),
	modal:true,
	closeAction:'hide',
	width: 580,
	height: 300,
	layout: 'auto',
	items: [
	        airfreight.airChangeInventory.airChangeInventoryForm = Ext.create('Ext.form.Panel',{
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
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.waybillNo'),
        		name: 'waybillNo',
        		margin: '0 5 5 5',
        		readOnly:true
        	},{
        		xtype:'container',
        		columnWidth:.86
        	},{
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.billingWeight'),
        		regex:/^\d*\.?\d{1,3}$/,
        		name: 'billingWeight'
        	},{
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.deliverFee'),
        		regex:/^\d*\.?\d{1,3}$/,
        		name: 'deliverFee'
        	},{
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.poll'),
        		regex:/^\d+$/,
        		name: 'goodsQty'
        	},{
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.receiverName'),
        		regex:/^.{0,200}$/,
        		regexText:airfreight.airChangeInventory.i18n('foss.airfreight.airEnteringFlightBill.lengthTooLong'),
        		name: 'receiverName'
        	},{
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.phone'),
        		regex:/^.{0,200}$/,
        		regexText:airfreight.airChangeInventory.i18n('foss.airfreight.airEnteringFlightBill.lengthTooLong'),
        		name: 'receiverContactPhone'
        	},{
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.address'),
        		regex:/^.{0,1000}$/,
        		regexText:airfreight.airChangeInventory.i18n('foss.airfreight.airEnteringFlightBill.lengthTooLong'),
        		name: 'receiverAddress'
        	},{
        		fieldLabel: airfreight.airChangeInventory.i18n('foss.airfreight.public.notes'),
        		name: 'notes',
        		regex:/^.{0,1000}$/,
        		regexText:airfreight.airChangeInventory.i18n('foss.airfreight.airEnteringFlightBill.lengthTooLong'),
        		columnWidth:.98,
        		allowBlank:true
        	},{
        		xtype:'container',
        		columnWidth:.68
        	},{
        		xtype:'button',
        		text:airfreight.airChangeInventory.i18n('foss.airfreight.public.ensure'),
        		columnWidth:.15,
        		handler:function(){
        			
        			//获取表单
        			var formRecord = this.up('form').getForm();
        			if(!formRecord.isValid()){
        				return;
        			}
        			//得到表单record对象
        			var newRecord = formRecord.getRecord();
        			//保存之前的备注信息
        			var notes = newRecord.data.notes;
        			var recordId = newRecord.data.id;
        			//送货费
        			var deliverFee = newRecord.data.deliverFee;
        			//收货人地址
        			var receiverAddress = newRecord.data.receiverAddress;
        			var notesMap = airfreight.airChangeInventory.notesMap;
        			//将上一次修改的记录移除
        			notesMap.add(recordId,notes);
        			//保存修改之前的送货费
        			notesMap.add(recordId+'deliverFee',deliverFee);
        			//保存修改之前的收货人地址
        			notesMap.add(recordId+'receiverAddress',receiverAddress);
        			airfreight.airChangeInventory.notesMap = notesMap;
        			//将修改的数据update到record对象中
        			formRecord.updateRecord(newRecord);
        			airfreight.airChangeInventory.airChangeInventoryGrid.store.update(formRecord.getRecord());
        			//Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'),airfreight.airChangeInventory.i18n('foss.airfreight.public.modifySuccess'));
        			this.up('window').close();
        		}
        	},{
        		xtype:'button',
        		text:airfreight.airChangeInventory.i18n('foss.airfreight.public.cancel'),
        		columnWidth:.15,
        		handler:function(){
        			this.up('window').close();
        		}
        	}]
        })
	]
});

/**
 * 运单明细列表
 */
Ext.define('Foss.airfreight.airChangeInventory.airChangeInventoryGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
	border:false,
	title:airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.inventoryDetail'),
	emptyText: airfreight.airChangeInventory.i18n('foss.airfreight.public.searchResultInexistence'),
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			handler: function(grid, rowIndex, colIndex){
				//获取当前record对象
				var record = grid.getStore().getAt(rowIndex);
				Ext.create('Foss.airfreight.airChangeInventory.updateAirChangeInventoryWindow').show();
				airfreight.airChangeInventory.airChangeInventoryForm.getForm().loadRecord(record);
			}
		}],
		text:airfreight.airChangeInventory.i18n('foss.airfreight.public.operate'),
	    width: 40,
	    dataIndex: 'id'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.waybillNo'),
		flex: 0.6,
		dataIndex: 'waybillNo'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.airWaybillNo'),
		flex: 0.6,
		dataIndex: 'airWaybillNo'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.jointTicketTime'),
		flex: 0.6,
		dataIndex: 'createTime',
		format: 'Y-m-d h:i:s',
		xtype:'datecolumn'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.arrvRegionName'),
		flex: 0.6,
		dataIndex: 'arrvRegionName'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.goodsName'),
		flex: 0.6,
		dataIndex: 'goodsName'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.poll'),
		flex: 0.6,
		dataIndex: 'goodsQty'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.weightKilo'),
		flex: 0.6,
		dataIndex: 'weight'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.billingWeightKilo'),
		flex: 0.6,
		dataIndex: 'billingWeight'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.pickupType'),
		flex: 0.6,
		dataIndex: 'pickupType',
		renderer: function(value){
			if(value != null){
				var pickupType = FossDataDictionary.rendererSubmitToDisplay (value,'PICKUPGOODSAIR');
				return pickupType;
			}
		}
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.deliverFee'),
		flex: 0.6,
		dataIndex: 'deliverFee'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.arrivalFee'),
		flex: 0.6,
		dataIndex: 'arrivalFee'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.collectionFee'),
		flex: 0.6,
		dataIndex: 'collectionFee'
	},{
		text: airfreight.airChangeInventory.i18n('foss.airfreight.public.notes'),
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
			   fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.ticket'),
			   columnWidth:.10,
			   dataIndex: 'billNoTotal'
		   },{
			   fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.poll'),
			   columnWidth:.10,
			   dataIndex: 'goodsQtyTotal'
		   },{
			   fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.weight'),
			   columnWidth:.12,
			   hideValue:'',
			   dataIndex: 'grossWeightTotal'
		   },{
			   fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.deliverFee'),
			   labelWidth:80,
			   columnWidth:.15,
			   dataIndex: 'deliverFeeTotal'
		   },{
			   fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.arrivalFee'),
			   columnWidth:.15,
			   labelWidth:60,
			   dataIndex: 'arrivalFeeTotal'
		   }]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.airChangeInventory.airChangeInventoryStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.callParent([cfg]);
	}
});

/**
 * 运单明细信息展示 
 */
Ext.define('Foss.airfreight.airChangeInventory.messagePanel',{
	extend:'Ext.panel.Panel',
	  frame:true,
	  border:true,
	  autoScroll:true,
	  width: 1042,
	  height: 200,
	  showAirRevisebillLogger : function (waybillNo){
			var collectionFee = airfreight.airChangeInventory.airPickupbillDetailList[0].collectionFee;
			if(airfreight.airChangeInventory.airChangeInventoryDetailList!=null){
				if(airfreight.airChangeInventory.airChangeInventoryDetailList.length>0){
						var airChangeInventoryDetailList = airfreight.airChangeInventory.airChangeInventoryDetailList;
						var view = document.getElementById(airfreight.airChangeInventory.messagePanel.getId()+'-body');
						var div = '<div id="ext-comp-1093-clearEl" class="x-clear" role="presentation"></div>';
						var nbsp = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
							       '&nbsp;&nbsp;&nbsp;';
						var nsbpHead = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
						//头信息根据运单号分组
						view.innerHTML = view.innerHTML + '<span id='+waybillNo+'_waybillNo></br>'+waybillNo + nbsp +airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.jointTicketCase');
						//运单体明细信息
						for(var i = 0; i<airChangeInventoryDetailList.length; i++){
							var airChangeInventoryId = airChangeInventoryDetailList[i].id;
							var waybillNo =  airChangeInventoryDetailList[i].waybillNo;
							var createTime = Ext.Date.format(new Date(airChangeInventoryDetailList[i].createTime),'Y-m-d H:i:s');
							var airWaybillNo =  airChangeInventoryDetailList[i].airWaybillNo;
							var operatorCode =  airChangeInventoryDetailList[i].operatorCode;
							var reviseContent = airChangeInventoryDetailList[i].reviseContent;
							var name = waybillNo +'_waybillNo'+ airWaybillNo;
							var headText = ''
							var DetailText = ''
							var billTypeText = airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.bigTicketProve');
							if(airChangeInventoryDetailList[i].billType == 'TP'){
								billTypeText = airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.transTicketProve');
							}
							
							if(i==0){
								headText = nsbpHead +airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.pickBranch') +
								airChangeInventoryDetailList[i].arrvRegionName + nsbpHead +
								airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.deliverFee') + airChangeInventoryDetailList[i].deliverFee + nsbpHead + airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.arrival') +
								airChangeInventoryDetailList[i].arrivalFee + nsbpHead + airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.collectionFee') + collectionFee;
							}
							if(i==0){
								DetailText = '<span id= '+name+'span name='+ name +'span>'+ createTime + nsbpHead + operatorCode + nsbpHead + billTypeText+ nsbpHead + reviseContent + headText +'</br></span>';
							}else{
								DetailText = '<span id= '+name+'span name='+ name +'span>'+ createTime + nsbpHead + operatorCode + nsbpHead + billTypeText+ nsbpHead + reviseContent +'</br></span>';
							}
							view.innerHTML =view.innerHTML + DetailText;
						}
						if(!document.getElementById('ext-comp-1093-clearEl')){
							view.innerHTML =view.innerHTML+div;
						}else{
							view.innerHTML =view.innerHTML;
						}
						airfreight.airChangeInventory.view = view.innerHTML;
		  			}
				}
		},
	  constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
	  }
});

/**
 * 保存操作
 */
Ext.define('Foss.airfreight.airChangeInventory.airChangeInventorySave',{
	extend:'Ext.form.Panel',
	frame:false,
	border:false,
	height: 60,
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:'',
			   columnWidth:.60
		   },{
			   fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.createUser'),
			   labelWidth:55,
			   columnWidth:.15,
			   dataIndex: 'createUserName'
		   },{
			   fieldLabel:airfreight.airChangeInventory.i18n('foss.airfreight.public.createTime'),
			   labelWidth:75,
			   columnWidth:.20,
			   dataIndex: 'createTime'
		   },{
			   fieldLabel:'',
			   columnWidth:.91
		   },{
			   xtype:'button',
			   text:airfreight.airChangeInventory.i18n('foss.airfreight.public.save'),
			   disabled: !airfreight.airChangeInventory.isPermission('airfreight/updateAirWaybillDetailOrLoggerButton'),
			   hidden: !airfreight.airChangeInventory.isPermission('airfreight/updateAirWaybillDetailOrLoggerButton'),
			   plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			       seconds:3
			   }),
			   labelWidth:100,
			   columnWidth:.06,
			   handler:function(){
				   var records = airfreight.airChangeInventory.airChangeInventoryGrid.store.data.items;
				   if(records.length==0){
					   Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
							   ,airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.waybillDetailNotData'));
					   return false;
				   }
				   //收集需要update的运单
				   var jsonData = new Array();
	            	//遍历航空正单list集合
	               for(var i=0;i<records.length;i++){
	            		jsonData.push(records[i].data);
	               }
	               var parameterMap = airfreight.airChangeInventory.parameterMap;
	               var stlWayBillNoMap = airfreight.airChangeInventory.stlWayBillNoMap;
				   for(var j = 0; j<records.length; j++){
					   var notesMap = airfreight.airChangeInventory.notesMap;
					   //获取修改后的(备注、送货费、收货人地址)
					   var recordId = records[j].data.id;
					   var newNotes = records[j].data.notes;
					   var waybillNo = records[j].data.waybillNo;
					   var newDeliverFee = records[j].data.deliverFee;
					   var newReceiverAddress = records[j].data.receiverAddress;
					   //获取修改前的(备注、送货费、收货人地址)
					   var oldNotes = notesMap.get(recordId);
					   var oldDeliverFee = notesMap.get(recordId+'deliverFee');
					   var oldReceiverAddress = notesMap.get(recordId+'receiverAddress');
					   if(newDeliverFee!=oldDeliverFee){
						   //记录需要调用结算的运单明细
						   stlWayBillNoMap.add(recordId,recordId);
					   }
					  
					   //不为空者表示被修改过
					   if(oldNotes){
						   if(newNotes!=oldNotes){
							   //记录被修改的主键id合notes 保存到后台
							   var empCode = FossUserContext.getCurrentUser().employee.empCode;
							   var saveNotes = Ext.Date.format(new Date(),'Y-m-d H:i:s') + empCode + airfreight.airChangeInventory.i18n('foss.airfreight.airChangeInventory.bigTicketProve') + newNotes;
							   parameterMap.add(recordId,saveNotes);
						   }
					   }
					 //2013-03-06 046130-foss-xuduowei,比较变更清单修改前后的内容，提取变更项
					  /* var updateContent = "";
					   
					   var oldAirPickupbillDetail = airfreight.airChangeInventory.oldAirPickupbillDetailMap.get(recordId);
					   if(oldAirPickupbillDetail.weight != records[j].data.weight){
						   //					
						   if(oldAirPickupbillDetail.weight != null || records[j].data.weight != ''){
							   updateContent = updateContent + "计费重量：" + records[j].data.weight + "；";
						   } 
					   }
					   if(oldAirPickupbillDetail.deliverFee != records[j].data.deliverFee){
						   if(oldAirPickupbillDetail.deliverFee != null || records[j].data.deliverFee != ''){
							   updateContent = updateContent + "送货费：" + records[j].data.deliverFee + "；";
						   }	  
					   }
					   if(oldAirPickupbillDetail.goodsQty != records[j].data.goodsQty){
						   if(oldAirPickupbillDetail.goodsQty != null || records[j].data.goodsQty != ''){
							   updateContent = updateContent + "件数：" + records[j].data.goodsQty + "；";
						   }			   
					   }
					   if(oldAirPickupbillDetail.receiverName != records[j].data.receiverName){
						   if(oldAirPickupbillDetail.receiverName != null || records[j].data.receiverName != ''){
							   updateContent = updateContent + "收货人：" + records[j].data.receiverName + "；";
						   }		   
					   }
					   if(oldAirPickupbillDetail.receiverContactPhone != records[j].data.receiverContactPhone){
						   if(oldAirPickupbillDetail.receiverContactPhone != null || records[j].data.receiverContactPhone != ''){
							   updateContent = updateContent + "收货人电话：" + records[j].data.receiverContactPhone + "；";
						   }				   
					   }
					   if(oldAirPickupbillDetail.receiverAddress != records[j].data.receiverAddress){
						   if(oldAirPickupbillDetail.receiverAddress != null || records[j].data.receiverAddress != ''){
							   updateContent = updateContent + "收货人地址：" + records[j].data.receiverAddress + "；";
						   }					   
					   }
					   if(oldAirPickupbillDetail.notes != records[j].data.notes){
						   if(oldAirPickupbillDetail.notes != null || records[j].data.notes != ''){
							   updateContent = updateContent + "备注：" + records[j].data.notes + "；";
						   }						   
					   }
					   parameterMap.add(recordId,updateContent);*/
				   }
				    
				   
				   //已删除的流水号
				   var delWayBillNoMap = airfreight.airChangeInventory.delWayBillNoMap;
				   var jsonCollectionData = {airTransPickupBillVo:{airPickupbillDetailList:jsonData,parameter:parameterMap.map,stlWayBillNoMap:stlWayBillNoMap.map,delWayBillNoMap:delWayBillNoMap.map}};
				   Ext.Ajax.request({
					   url:airfreight.realPath('updateAirWaybillDetailOrLogger.action'),
					   jsonData:jsonCollectionData,
					   success:function(response){
						    var result = Ext.decode(response.responseText);
						    //用于刷新前端修改日志展示
						    var allList = result.airTransPickupBillVo.allList;
						    //airfreight.airChangeInventory.airChangeInventoryDetailList = airChangeInventoryDetailList;
						   /* var d1 = document.getElementById('ext-comp-1093-clearEl');
						    
						    if(view != null){					
						    	view.parentNode.removeChild(view);
							}*/
						    var view = document.getElementById(airfreight.airChangeInventory.messagePanel.getId()+'-body');
						    for(var i = 0;i<allList.length;i++){
						    	airfreight.airChangeInventory.airChangeInventoryDetailList = allList[i];
						    	var waybillNo = allList[i][0].waybillNo;
						    	var airWaybillNo = allList[i][0].airWaybillNo;
						    	
						    	//删除明细								
								var name = waybillNo +'_waybillNo'+airWaybillNo;
								var span = document.getElementsByName(name + 'span');
			  					if(span){
			  						var length = span.length;
			  						for(var j = 0; j<length; j++){
			  							var child = span[0].parentNode;
			  							span[0].innerHTML='';
			  							child.removeChild(span[0]);
			  						}
			  					}
						    	
						    	//删除头信息								
								var count = 0;
								var spans = view.getElementsByTagName('span');
								for(var m = 0; m<spans.length; m++){
									if(!spans[m] || !spans[m].id)
										continue;
									var id = spans[m].id;
									if(id.indexOf(waybillNo+'_waybillNo')!=-1 && id.lastIndexOf('span')!=-1){
										count ++;
									}
								}
								if(count<=0){
									var title = document.getElementById(waybillNo +'_waybillNo');
									if(!title)
									return false;
									view.removeChild(title);
								}															
							
						    	airfreight.airChangeInventory.messagePanel.showAirRevisebillLogger(waybillNo);
						    }
							
						    airfreight.airChangeInventory.isNotSave = true;
						    Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'),'保存成功!');
						    //保存完毕后将所有的全局map清空
						    //airfreight.airChangeInventory.oldAirPickupbillDetailMap.clear();
						    airfreight.airChangeInventory.notesMap.clear();
							airfreight.airChangeInventory.parameterMap.clear();
							airfreight.airChangeInventory.delWayBillNoMap.clear();
							airfreight.airChangeInventory.stlWayBillNoMap.clear();
					   },
					   exception:function(response){
						   var result = Ext.decode(response.responseText);
						   Ext.Msg.alert(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage'),result.message);
					   }
					});
			   }
		   }]
	}]
});

/**
 * 渲染
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	var exportEdi = Ext.create('Foss.airfreight.airChangeInventory.ExportEdi');
	var addChangeInventory = Ext.create('Foss.airfreight.airChangeInventory.AddChangeInventory');
	var delAirChangeInventory = Ext.create('Foss.airfreight.airChangeInventory.delAirChangeInventory');
	var airChangeInventoryGrid = Ext.create('Foss.airfreight.airChangeInventory.airChangeInventoryGrid');
	var messagePanel = Ext.create('Foss.airfreight.airChangeInventory.messagePanel');
	var airChangeInventorySave = Ext.create('Foss.airfreight.airChangeInventory.airChangeInventorySave');
	var toolbarMessageArray = airChangeInventorySave.down('toolbar').query('textfield');
	airfreight.airChangeInventory.isNotSave = false;
	//原始运单明细
	//airfreight.airChangeInventory.oldAirPickupbillDetailMap = new Ext.util.HashMap();
	//需要变更的备注信心
	airfreight.airChangeInventory.notesMap = new Ext.util.HashMap();
	//记录最终调用后台的修改的运单信息
	airfreight.airChangeInventory.parameterMap = new Ext.util.HashMap();
	//需要删除的运单号
	airfreight.airChangeInventory.delWayBillNoMap = new Ext.util.HashMap();
	//需要调用结算的运单号
	airfreight.airChangeInventory.stlWayBillNoMap = new Ext.util.HashMap();
	airfreight.airChangeInventory.exportEdi = exportEdi;
	airfreight.airChangeInventory.addChangeInventory = addChangeInventory;
	airfreight.airChangeInventory.delAirChangeInventory = delAirChangeInventory;
	airfreight.airChangeInventory.messagePanel = messagePanel;
	airfreight.airChangeInventory.airChangeInventorySave = airChangeInventorySave;
	airfreight.airChangeInventory.airChangeInventoryGrid = airChangeInventoryGrid;
	var createTime = Ext.Date.format(new Date(),'Y-m-d H:i:s');
	//获取当前操作人姓名
	var userName = FossUserContext.getCurrentUser().userName;
	//设置制单人
	toolbarMessageArray[1].setValue(userName);
	//设置制单时间
	toolbarMessageArray[2].setValue(createTime);
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airChangeInventory_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [exportEdi,addChangeInventory,delAirChangeInventory,airChangeInventoryGrid,messagePanel,airChangeInventorySave],
		renderTo: 'T_airfreight-airChangeInventory-body'
	});
});
