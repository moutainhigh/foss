//清除方法
unload.assignunloadtask.cleanFunction = function(){
	var loaderSm = Ext.getCmp('Foss_Unload_RightGrid_Id').getSelectionModel(),
	leftSupGrid = Ext.getCmp('Foss_Unload_LeftSupGird_Id'),
	vehicleSm = leftSupGrid.getSelectionModel();
	loaderSm.deselectAll();
	vehicleSm.deselectAll();
	var plugin = leftSupGrid.getPlugin('rowexpander_plugin_Id');
	if(!Ext.isEmpty(plugin.getExpendRow())) {
		plugin.getExpendRowBody().getSelectionModel().deselectAll();
	}
}
//分配方法
unload.assignunloadtask.assignFunction = function(){
	var loaderRecords = Ext.getCmp('Foss_Unload_RightGrid_Id').getSelectionModel().getSelection(),
	leftSupGrid = Ext.getCmp('Foss_Unload_LeftSupGird_Id'),
	vehicleRecords = leftSupGrid.getSelectionModel().getSelection(),
	plugin = leftSupGrid.getPlugin('rowexpander_plugin_Id'),
	windowGrid = Ext.getCmp('Foss_Unload_WindowGrid_Id'),
	loaderForm = Ext.getCmp('Foss_Unload_LoaderWindowForm_Id'),
	vehicleForm = Ext.getCmp('Foss_Unload_VehicleWindowForm_Id');
	unload.assignunloadtask.assignUnloadTaskWindow.restore();
	windowGrid.store.removeAll();
	if(loaderRecords.length!=0){
		if(!Ext.isEmpty(plugin.getExpendRow())) {
		//if(plugin.getExpendRowBody()){
			var billGrid = plugin.getExpendRowBody(),
			    billRecords = billGrid.getSelectionModel().getSelection();
			
			if(billRecords.length!=0){
				unload.assignunloadtask.assignMainPanelMask.show();
				leftSupGrid.store.each(function(record) {
					if(record.get('vehicleNo')==billRecords[0].get('vehicleNo') && record.get('arriveTime')==billRecords[0].get('arriveTime')){
						vehicleRecords.push(record);
					}
				});
				var vehicleNo=billRecords[0].get('vehicleNo');
				var billNo=billRecords[0].get('billNo');
				//去后台取月台号
				Ext.Ajax.request({
				 url : unload.realPath('queryPrePlatformNo.action'),
				 params:{
				   'vo.bill.vehicleNo':vehicleNo,
				   'vo.bill.billNo':billNo
				 },
				success:function(response){
				     var result = Ext.decode(response.responseText);
				    // platformNo=result.vo.platformNo;
				     //给月台初始化
					var prePlatformNo=result.vo.platformNo;
					if(prePlatformNo!=""||prePlatformNo!=null){
						Ext.getCmp('Foss_Unload_VehicleWindowForm_Id').getForm().findField('platformNo').setValue(prePlatformNo)
					}

				},	
				exception:function(response){
							unload.assignunloadtask.assignMainPanelMask.hide();
							var result = Ext.decode(response.responseText);
							//Ext.Msg.alert('提示',result.message);
							Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), result.message, 'error', 3000);
						}
					
				});
				
				windowGrid.store.loadData(billRecords); 
				loaderForm.loadRecord(loaderRecords[0]);
				if(loaderRecords[0].data.state!=null && loaderRecords[0].data.state!='UNBUNDLE'&& loaderRecords[0].data.state!=''){
					//loaderForm.getForm().findField('state').setValue('在线');
					loaderForm.getForm().findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.onLine'));
				}else{
					//loaderForm.getForm().findField('state').setValue('离线');
					loaderForm.getForm().findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assign'));
				}
				if(vehicleRecords.length!=0){
					vehicleForm.loadRecord(vehicleRecords[0]);
				}
				Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getCancledButton().setVisible(true);
				Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getConfirmButton().setVisible(true);
				//Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').items.items[8].setVisible(true);
				unload.assignunloadtask.assignMainPanelMask.hide();
				unload.assignunloadtask.assignUnloadTaskWindow.show();
			}else{
				if(vehicleRecords.length!=0){
					unload.assignunloadtask.assignMainPanelMask.show();
					Ext.Ajax.request({
						url : unload.realPath('queryArriveBill.action'),//'../unload/queryArriveBill.action',
						params :{
								'vo.bill.arriveVehicleId':vehicleRecords[0].get('id'),
								'vo.bill.assignState':'UNASSIGN',
								'vo.bill.vehicleNo':vehicleRecords[0].get('vehicleNo'),
								'vo.bill.flightNo': vehicleRecords[0].get('flightNo'),
								'vo.bill.arriveTime':vehicleRecords[0].get('arriveTime'),
								'vo.bill.unloadType':vehicleRecords[0].get('unloadType')
						},
						success:function(response){
							var result = Ext.decode(response.responseText);
							windowGrid.store.loadData(result.vo.bills);
							loaderForm.loadRecord(loaderRecords[0]);
							if(loaderRecords[0].data.state!=null && loaderRecords[0].data.state!='UNBUNDLE'&& loaderRecords[0].data.state!=''){
								//loaderForm.getForm().findField('state').setValue('在线');
								loaderForm.getForm().findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.onLine'));
							}else{
								//loaderForm.getForm().findField('state').setValue('离线');
								loaderForm.getForm().findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.offLine'));
							}
							vehicleForm.loadRecord(vehicleRecords[0]);
							//给月台初始化
							var prePlatformNo=null;
							if(result.vo.bills.length!=0){
								prePlatformNo=result.vo.bills[0].prePlatformNo;
							}
							if(prePlatformNo!=""||prePlatformNo!=null){
								Ext.getCmp('Foss_Unload_VehicleWindowForm_Id').getForm().findField('platformNo').setValue(prePlatformNo)
							}
							Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getCancledButton().setVisible(true);
							Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getConfirmButton().setVisible(true);
							//Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').items.items[8].setVisible(true);
							unload.assignunloadtask.assignMainPanelMask.hide();
							unload.assignunloadtask.assignUnloadTaskWindow.show();
						},
						exception:function(response){
							unload.assignunloadtask.assignMainPanelMask.hide();
							var result = Ext.decode(response.responseText);
							//Ext.Msg.alert('提示',result.message);
							Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), result.message, 'error', 3000);
						}
					});
				}else{
					//Ext.Msg.alert('提示','请选择车辆!');
					Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.chooseVehicle'), 'error', 3000);
				}
			}
		}else{
			if(vehicleRecords.length!=0){
				unload.assignunloadtask.assignMainPanelMask.show();
				Ext.Ajax.request({
					url : unload.realPath('queryArriveBill.action'),//'../unload/queryArriveBill.action',
					params :{
							'vo.bill.arriveVehicleId':vehicleRecords[0].get('id'),
							'vo.bill.assignState':'UNASSIGN',
							'vo.bill.vehicleNo':vehicleRecords[0].get('vehicleNo'),
							'vo.bill.flightNo':vehicleRecords[0].get('flightNo'),
							'vo.bill.arriveTime':vehicleRecords[0].get('arriveTime'),
							'vo.bill.unloadType':vehicleRecords[0].get('unloadType')
					},
					success:function(response){
						var result = Ext.decode(response.responseText);
						windowGrid.store.loadData(result.vo.bills);
						loaderForm.loadRecord(loaderRecords[0]);
						//给月台初始化
						if(loaderRecords[0].data.state!=null && loaderRecords[0].data.state!='UNBUNDLE'&& loaderRecords[0].data.state!=''){
							//loaderForm.getForm().findField('state').setValue('在线');
							loaderForm.getForm().findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.onLine'));
						}else{
							//loaderForm.getForm().findField('state').setValue('离线');
							loaderForm.getForm().findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.offLine'));
						}
						vehicleForm.loadRecord(vehicleRecords[0]);
						var prePlatformNo=null;
						if(result.vo.bills.length!=0){
							prePlatformNo=result.vo.bills[0].prePlatformNo;
						}
						if(prePlatformNo!=""||prePlatformNo!=null){
							Ext.getCmp('Foss_Unload_VehicleWindowForm_Id').getForm().findField('platformNo').setValue(prePlatformNo)
						}
						Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getCancledButton().setVisible(true);
						Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getConfirmButton().setVisible(true);
						//Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').items.items[8].setVisible(true);
						unload.assignunloadtask.assignMainPanelMask.hide();
						unload.assignunloadtask.assignUnloadTaskWindow.show();
					},
					exception:function(response){
						unload.assignunloadtask.assignMainPanelMask.hide();
						var result = Ext.decode(response.responseText);
						//Ext.Msg.alert('提示',result.message);
						Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), result.message, 'error', 3000);
					}
				});
			}else{
				//Ext.Msg.alert('提示','请选择车辆!');
				Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.chooseVehicle'), 'error', 3000);
			}
		}
}else{
		//Ext.Msg.alert('提示','请选择理货员!');
		Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.chooseLoader'), 'error', 3000);
	}
}
/**-----------------------------------------------Left------------------------------**/
/**-----------------------------------------------LeftForm------------------------------**/
//运输性质
Ext.define('foss.unload.assignunloadtask.ProductStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	data:[
		    {"valueName": "全部", "valueCode": "ALL"},
		    {"valueName": "城际", "valueCode": "FSF"},
		    {"valueName": "卡航", "valueCode": "FLF"},
		    {"valueName": "大票城际", "valueCode": "BGFSF"},
		    {"valueName": "大票卡航", "valueCode": "BGFLF"},
		    {"valueName": "空运", "valueCode": "AF"}
		]
});
Ext.define('Foss.Unload.LeftForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	//width: 510,
	frame: false,
	border: false,
	title : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryVehicle'),//'查询待卸车辆',
	defaults: {
		margin: '2 10 3 5',
		labelWidth: 90
	},
	items: [{
		xtype: 'commontruckselector',
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.vehicleNo'),//'车牌号',
		name: 'vehicleNo',
		hidden:false,
		columnWidth:.20,
		labelWidth: 65
	}, {
		xtype: 'commonflightselector',
		fieldLabel: '航班号',
		name: 'flightNo',
		hidden:true,
		columnWidth:.20,
		labelWidth: 65
	}, {
		xtype: 'textfield',
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.billNo'),//'单据编号',
		name: 'billNo',
		columnWidth:.20,
		labelWidth: 60
	}, {
		xtype: 'datetimefield_date97',
		labelWidth:65,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.arriveTime'),//'到达时间',
		allowBlank:false,
		editable : false,
		columnWidth: .25,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,00,00,00),'Y-m-d H:i:s'),
		name: 'arriveTimeBegin',
		id:'Foss_unload_assignUnloadTask_arriveTimeBegin',
		time : true,
		dateConfig: {
			el : 'Foss_unload_assignUnloadTask_arriveTimeBegin-inputEl'
		}
	},{
		xtype: 'datetimefield_date97',
		labelWidth:60,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.to'),//'至',
		allowBlank:false,
		editable : false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .25,
		name: 'arriveTimeEnd',
		id:'Foss_unload_assignUnloadTask_arriveTimeEnd',
		time : true,
		dateConfig: {
			el : 'Foss_unload_assignUnloadTask_arriveTimeEnd-inputEl'
		}
	},{
		xtype: 'combo',
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.product'),//'运输性质'
		name: 'productCode',
		displayField: 'valueName',
		valueField:'valueCode', 
		value: 'ALL',
		columnWidth:.2,
		queryMode:'local',
		labelWidth: 65,
		triggerAction:'all',
		editable:false,
		store:Ext.create('foss.unload.assignunloadtask.ProductStore')
	},{
		xtype: 'dynamicorgcombselector',
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.departOrg'),//'出发部门',
		name: 'departOrg',
		columnWidth:.2,
		labelWidth: 60
	},
	FossDataDictionary.getDataDictionaryCombo('ASSIGNUNLOADTASK_UNLOADTYPE',{
		fieldLabel : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unloadType'),//'卸车类型',
		allowBlank : false,
		name : 'unloadType',
		value : 'ALL',
		editable : false,
		columnWidth:.25,
		labelWidth: 60,
		listeners:{
			beforerender:{
				fn: function(src, newValue, oldValue, eOpts){
					this.up('form').getForm().findField('flightNo').setVisible(false);
				 } 
			},
				change:{
				fn : function(src, newValue, oldValue, eOpts){
					if(newValue == 'BUSINESS_AIR'){
						this.up('form').getForm().findField('flightNo').setVisible(true);
						this.up('form').getForm().findField('vehicleNo').setVisible(false);
						
					}
					else{
						this.up('form').getForm().findField('vehicleNo').setVisible(true);
						this.up('form').getForm().findField('flightNo').setVisible(false);
					}
				}
			}}
		
		
	}),{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.reset'),//'重置',
			columnWidth:.1,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.70,
			html: '&nbsp;'
		},{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.query'),//'查询',
			disabled : !unload.assignunloadtask.isPermission('unload/queryArriveVehicleButton'),
			hidden : !unload.assignunloadtask.isPermission('unload/queryArriveVehicleButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){
				if(this.up('form').getForm().isValid()){
					var time1 = this.up('form').getForm().getValues().arriveTimeBegin,
					time2 = this.up('form').getForm().getValues().arriveTimeEnd,
					quertTimeBegin = new Date(time1.substring(0,4),time1.substring(5,7)-1,time1.substring(8,10),time1.substring(11,13),time1.substring(14,16),time1.substring(17,19)),
					queryTimeEnd = new Date(time2.substring(0,4),time2.substring(5,7)-1,time2.substring(8,10),time2.substring(11,13),time2.substring(14,16),time2.substring(17,19));
					if(queryTimeEnd>quertTimeBegin){
						if( queryTimeEnd.getTime() -quertTimeBegin.getTime() <= 31*24*60*60*1000){
							var myMask = new Ext.LoadMask(this,{
								msg:""
							});
							myMask.show();
							Ext.getCmp('Foss_Unload_LeftSupGird_Id').store.removeAll();
							var formRecord = this.up('form').getForm().getValues();
							var params ={
									'vo.vehicle.vehicleNo':formRecord.vehicleNo,
									'vo.vehicle.flightNo':formRecord.flightNo,
									'vo.vehicle.billNo':formRecord.billNo,
									'vo.vehicle.arriveTimeBegin':formRecord.arriveTimeBegin,
									'vo.vehicle.arriveTimeEnd':formRecord.arriveTimeEnd,
									'vo.vehicle.productCode':formRecord.productCode,
									'vo.vehicle.departOrg':formRecord.departOrg,
									'vo.vehicle.unloadType':formRecord.unloadType
								};
							
							var data = {
									'vo':{	
										'vehicle':Ext.getCmp('Foss_Unload_VehicleWindowForm_Id').getForm().getValues(),
										'loader':Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getForm().getValues(),
										'assignWeightTotal':Ext.getCmp('Foss_Unload_assignunloadtask_TotalWeight').getValue(),
										'assignVolumeTotal' : Ext.getCmp('Foss_Unload_assignunloadtask_TotalVolume').getValue(),
									    'assignGoodsQtyTotal' : Ext.getCmp('Foss_Unload_assignunloadtask_TotalPieces').getValue(),
									    'assignWayBillQtyTotal':Ext.getCmp('Foss_Unload_assignunloadtask_TotalWaybill').getValue()
									}
							};
							
							Ext.Ajax.request({
								params : params,
								url : unload.realPath('queryArriveVehicletotal.action'),
				                success : function(response){
									var result = Ext.decode(response.responseText);
					                if(result!=null){
					                	var totalWeightCmp = 0;
										var totalVolumeCmp = 0;
										var totalPiecesCmp = 0;
										var totalWaybillCmp = 0; 
					 					if(result.vo.assignWeightTotal!=null){
					 						totalWeightCmp=result.vo.assignWeightTotal;//总重量		
										}
										if(result.vo.assignVolumeTotal!=null){
											totalVolumeCmp=result.vo.assignVolumeTotal;//		
										}
										if(result.vo.assignGoodsQtyTotal!=null){
											totalPiecesCmp=result.vo.assignGoodsQtyTotal;	
										}
										if(result.vo.assignWayBillQtyTotal!=null){
											totalWaybillCmp=result.vo.assignWayBillQtyTotal;	
										}
										Ext.getCmp('Foss_Unload_assignunloadtask_TotalWeight').setValue(totalWeightCmp);
										Ext.getCmp('Foss_Unload_assignunloadtask_TotalVolume').setValue(totalVolumeCmp);
									     Ext.getCmp('Foss_Unload_assignunloadtask_TotalPieces').setValue(totalPiecesCmp);
									    Ext.getCmp('Foss_Unload_assignunloadtask_TotalWaybill').setValue(totalWaybillCmp);
					                }else{
					                	top.Ext.MessageBox.alert(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'),result.message);
					                }
					                /*Ext.getCmp('Foss_Unload_LeftSupGird_Id').getStore().loadData(result.vo.vehicles);
					                var records =Ext.getCmp('Foss_Unload_LeftSupGird_Id').getStore();
					                
								    if(records.count()>0){
									  if(params['vo.vehicle.billNo']!=null&&params['vo.vehicle.billNo']!=''){
										    var grid = Ext.getCmp('Foss_Unload_LeftSupGird_Id');
											plugin = grid.getPlugin('rowexpander_plugin_Id');
											plugin.toggleRow(grid.getView().getNode(records.getAt(0)));
									  }	
									}*/
								    
								    var remindMsgLabel = Ext.getCmp('T_unload-assignunloadtaskindex_content').query("label[pid='remindMessage']");
								    if(!Ext.isEmpty(remindMsgLabel)){
								    	remindMsgLabel[0].setText(result.vo.remind);
								    }							    
				                },
				                exception : function(response){
					    			Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), '???!', 'error', 3000);
								}
							});
							
							/*Ext.getCmp('Foss_Unload_LeftSupGird_Id').store.load({
								params : params,
								callback : function(records, operation, success) {
									myMask.hide();
									if (success == true) {
										if(records.length>0){
											if(params['vo.vehicle.billNo']!=null&&params['vo.vehicle.billNo']!=''){
												var grid = Ext.getCmp('Foss_Unload_LeftSupGird_Id');
												plugin = grid.getPlugin('rowexpander_plugin_Id');
												plugin.toggleRow(grid.getView().getNode(records[0]));
											}	
										}
									}else{
										//Ext.Msg.alert('提示',"???");
										//Ext.ux.Toast.msg('提示', '???!', 'error', 3000);
										Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), '???!', 'error', 3000);
									}
								}
							});*/
							unload.assignunloadtask.leftSupPagingBar.moveFirst();
							myMask.hide();
							Ext.getCmp('Foss_Unload_LeftSupGird_Id').show();
						}else{
							//Ext.ux.Toast.msg('提示', '查询时间跨度不能超过31天!', 'error', 3000);
							Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryError1'), 'error', 3000);
						}
					}else{
						//Ext.ux.Toast.msg('提示', '查询开始时间不能大于查询截止时间!', 'error', 3000);
						Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryError2'), 'error', 3000);
					}
				}else{
					//Ext.Msg.alert('提示','请补全查询条件!');
					Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryError3'), 'error', 3000);
				}
			}
		},{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.export'),//'导出'
			disabled : !unload.assignunloadtask.isPermission('unload/queryArriveVehicleButton'),
			hidden : !unload.assignunloadtask.isPermission('unload/queryArriveVehicleButton'),
			columnWidth:.1,
			handler:function(){
				var queryParams = this.up('form').getForm().getValues();
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
				}
				Ext.Ajax.request({
					url:unload.realPath('exportAssignunloadtask.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'vo.vehicle.vehicleNo':queryParams.vehicleNo,
						'vo.vehicle.billNo':queryParams.billNo,
						'vo.vehicle.arriveTimeBegin':queryParams.arriveTimeBegin,
						'vo.vehicle.arriveTimeEnd':queryParams.arriveTimeEnd,
						'vo.vehicle.productCode':queryParams.productCode,
						'vo.vehicle.departOrg':queryParams.departOrg,
						'vo.vehicle.unloadType':queryParams.unloadType
					},
	    			isUpload: true,
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert('提示',result.message);
	    			}
				});
				
			}
			
		}]
	}]
});
/**-----------------------------------------------LeftSupModel------------------------------**/
Ext.define('Foss.Unload.LeftSupGirdModel',{
	extend: 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	fields: [
	    {name: ' extid ', type: 'string'},//额外的用于生成的EXT使用的列
	    {name: 'id' , type: 'string'},     
		{name: 'vehicleNo' , type: 'string'},
		{name: 'vehicleType', type: 'string'},
		{name: 'unloadType', type: 'string'},
		{name: 'arriveTime', type: 'string'},
		{name: 'line', type: 'string'},
		{name: 'weightTotal', type: 'float'},
		{name: 'volumeTotal', type: 'float'},
		{name: 'goodsQtyTotal', type: 'int'},
		{name: 'billQtyTotal', type: 'int'},
		{name: 'platformNo', type: 'string'},
		{name: 'platformVirtualCode', type: 'string'},
		/**新增快递票数字段*/
		{name: 'expressWayBillQty', type: 'string'},
		{name: 'fastWayBillQtyTotal', type: 'number'},
		{name: 'afWayBillQtyTotal', type: 'number'},
		{name: 'afVolumeTotal', type: 'number'},
		{name: 'afWeightTotal', type: 'number'},
		{name: 'flfWayBillQtyTotal', type: 'number'},
		{name: 'flfVolumeTotal', type: 'number'},
		{name: 'flfWeightTotal', type: 'number'},
		{name: 'fsfWayBillQtyTotal', type: 'number'},
		{name: 'fsfVolumeTotal', type: 'number'},
		{name: 'fsfWeightTotal', type: 'number'},
		{name: 'assignWayBillQtyTotal', type: 'number'},
		{name: 'assignGoodsQtyTotal', type: 'int'},
		{name: 'assignWeightTotal', type: 'float'},
		{name: 'assignVolumeTotal', type: 'float'}
	]
});
/**-----------------------------------------------LeftSupStore------------------------------**/
Ext.define('Foss.Unload.LeftSupGirdStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.Unload.LeftSupGirdModel',
	//pageSize: 10,
	proxy : {
		type : 'ajax',
		url : unload.realPath('queryArriveVehicle.action'),
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.vehicles',
		//	totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}/*,listeners: {
		datachanged: function(store, operation, epots){
			//总重量、体积、件数、票数
			var totalWeightCmp = Ext.getCmp('Foss_unload_assignunloadtask_TotalWeight');
			var totalVolumeCmp = Ext.getCmp('Foss_unload_assignunloadtask_TotalVolume');
			var totalPiecesCmp = Ext.getCmp('Foss_unload_assignunloadtask_TotalPieces');
			var totalWaybillCmp = Ext.getCmp('Foss_unload_assignunloadtask_TotalWaybill');
			
			//统计总重量、体积、件数、票数
			totalWeightCmp.setValue(store.getr);
			totalVolumeCmp.setValue(totalVolumeCmp.getValue() + record.get('volumeTotal'));
			totalPiecesCmp.setValue(totalPiecesCmp.getValue() + record.get('goodsQtyTotal'));
			totalWaybillCmp.setValue(totalWaybillCmp.getValue() + record.get('waybillQtyTotal'));
			}
		}*/
});
/**-----------------------------------------------LeftSubModel------------------------------**/
Ext.define('Foss.Unload.LeftSubGirdModel',{
	extend: 'Ext.data.Model',
	idProperty : 'id',
	fields:[
	    {name: 'id' , type: 'string'},
		{name: 'billNo', type: 'string'},
		{name: 'origOrgName', type: 'string',
			convert: function(value) {
				if (value == 'RECEIVE') {					
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.receive');//'接货';
				} else if(value == 'GOODS_BACK'){
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.goodsBack');//'拉回货';
				}else if(value =='PACKAGE_AIR'){
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.packageAir');//空运货
				}else if(value == 'EXPRESS_PICK'){
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.expressPick');//快递接货
				}else if(value == 'EXPRESS_DRIVER'){
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.expressDriver');//快递接驳接货
				}else{
					return value;
				}
			}
		},
		{name:'arriveVehicleId', type:'string'},
		{name: 'assignState', type: 'string',
			convert: function(value) {
				if (value == 'UNASSIGN') {					
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unassign');//'未分配';
				} else{
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assigned');//'已分配';
				}
			}
		},
		{name: 'weightTotal', type: 'float'},
		{name: 'volumeTotal', type: 'float'},
		{name: 'goodsQtyTotal', type: 'int'},
		{name: 'fastWayBillQtyTotal', type: 'int'},
		{name: 'expressWayBillQty', type: 'int'},
		{name: 'vehicleNo', type: 'string'},
		{name: 'arriveTime', type: 'string'},
		{name: 'origOrgCode', type: 'string'},
		{name: 'destOrgCode', type: 'string'}
	]
});
/**-----------------------------------------------LeftSubStore------------------------------**/
Ext.define('Foss.Unload.LeftSubGirdStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.Unload.LeftSubGirdModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'vo.bills'
		}
	}
});
/**-----------------------------------------------LeftSubGrid------------------------------**/
Ext.define('Foss.Unload.LeftSubGrid',{
	extend: 'Ext.grid.Panel',
		frame: false,
		border : false,
		autoScroll:false,
		hideHeaders: true,
		margin: '0 0 0 50',
		//id: 'Foss_BillCar_assginsTask_Id',
		viewConfig: {
			//处理行勾选事件
			getRowClass: function(record, rowIndex, rowParams, store) {
				if(record.get('assignState')==unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assigned')) {
					return 'disabledrow';
				}
			}
		},
		columns:[     
			{text: '',dataIndex : 'billNo', width: 140},
			{text: '',dataIndex : 'origOrgName', width: 200},
			{text: '',dataIndex : 'assignState'}
		],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.Unload.LeftSubGirdStore').load();
			me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'SIMPLE'
			});
			me.callParent([cfg]);
		},
		bindData :function(record){
			var grid = this;
			var params = {
					'vo.bill.arriveVehicleId':record.get('id'),
					'vo.bill.vehicleNo':record.get('vehicleNo'),
					'vo.bill.flightNo':record.get('flightNo'),
					'vo.bill.arriveTime':record.get('arriveTime'),
					'vo.bill.unloadType':record.get('unloadType')
			};
			Ext.Ajax.request({
				url : unload.realPath('queryArriveBill.action'),//'../unload/queryArriveBill.action',
				params :params,
				success:function(response){
					var result = Ext.decode(response.responseText);
					grid.store.loadData(result.vo.bills);
					var supGrid =  Ext.getCmp('Foss_Unload_LeftSupGird_Id');
					var vehicleRecords =supGrid.getSelectionModel().getSelection();
					if(vehicleRecords.length != 0){
						if(vehicleRecords[0].get('vehicleNo')==params.vehicleNo && vehicleRecords[0].get('arriveTime')==params.arriveTime){
							var unAssignRecords = [];
							grid.store.each(function(record){
								if(record.get('assignState')==unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unassign')){
									unAssignRecords.push(record);
								}
							});
							supGrid.getPlugin('rowexpander_plugin_Id').getExpendRowBody().getSelectionModel().select(unAssignRecords);
						}
					}
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
					//Ext.Msg.alert('提示',result.message);
					Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), result.message, 'error', 3000);
				}
			});
		},
		listeners: {
			itemdblclick:function( view,record,item,index,e,eOpts ){
				//alert("test"+item);
			},
			select: function(record,index,eOpts) {
				var grid = this,
				supGrid = Ext.getCmp('Foss_Unload_LeftSupGird_Id'),
				supSm = supGrid.getSelectionModel();
				if(supSm.selected.getCount() > 0) {
					var vehicleNo = supSm.selected.items[0].data.vehicleNo,
					    arriveTime = supSm.selected.items[0].data.arriveTime;
					if(vehicleNo!=index.data.vehicleNo && arriveTime!=index.data.arriveTime){
						supSm.deselectAll();
					}
				}		
			}
		}
});
/**-----------------------------------------------LeftSupGrid------------------------------**/
Ext.define('Foss.Unload.LeftSupGird',{
	extend: 'Ext.grid.Panel',
	//id:'Foss_Unload_LeftSupGird_Dom_Id',
	selType:'rowmodel',
	emptyText : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.dataNotFind'),//'查询结果为空',
	autoScroll:true,
	stripeRows : true, 
	//width: 540,
	height:460,
	frame: false,
	columns:[
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.vehicleNo'),//'车牌号',
			dataIndex : 'vehicleNo', flex: 1.4},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.vechileType'),//'车型',
			dataIndex : 'vehicleType', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.arriveTime'),//'到达时间',
			dataIndex : 'arriveTime', flex: 1.5},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.line'),//'线路',
			dataIndex : 'line', flex: 1.3},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.totalWeight'),//'总<br/>重量',
			dataIndex : 'weightTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.totalVolume'),//'总<br/>体积',
			dataIndex : 'volumeTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.totalQty'),//'总<br/>件数',
			dataIndex : 'goodsQtyTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.totalBillQty'),//'单<br/>据数',
			dataIndex : 'billQtyTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.platform'),//'月台',
			dataIndex : 'platformNo', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.fastWayBillQtyTotal'),//'卡货<br/>总票数',
			dataIndex : 'fastWayBillQtyTotal', flex: 0.6},
			/**新增快递票数*/
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.expressWayBillQty'),//'快递<br/>票数',
				dataIndex : 'expressWayBillQty', flex: 0.6},	
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.fsfWayBillQtyTotal'),//'城际<br/>票数',
			dataIndex : 'fsfWayBillQtyTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.fsfWeightTotal'),//'城际<br/>重量,
			dataIndex : 'fsfWeightTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.fsfVolumeTotal'),//'城际<br/>体积',
			dataIndex : 'fsfVolumeTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.afWayBillQtyTotal'),//'空运<br/>票数',
			dataIndex : 'afWayBillQtyTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.afWeightTotal'),//'空运<br/>重量',
			dataIndex : 'afWeightTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.afVolumeTotal'),//'空运<br/>体积',
			dataIndex : 'afVolumeTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.flfWayBillQtyTotal'),//'卡航<br/>票数',
			dataIndex : 'flfWayBillQtyTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.flfWeightTotal'),//'卡航<br/>重量',
			dataIndex : 'flfWeightTotal', flex: 0.6},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.flfVolumeTotal'),//'卡航<br/>体积',
			dataIndex : 'flfVolumeTotal', flex: 0.6}
	],
	dockedItems: [{
	    xtype: 'toolbar',
	    id : 'Foss_Unload_assignunloadtask_toobar',
	    dock: 'bottom',
	    layout : 'column',
	    defaults: {
	    	margin:'0 0 0 0',
			xtype: 'textfield',
			readOnly:true,
			anchor: '100%',
			labelWidth:80
		},
		items: [{
			id : 'Foss_Unload_assignunloadtask_TotalWaybill',
			fieldLabel: '总票数',
			columnWidth : 1/5,
			xtype : 'numberfield',
			value : 0
			},{
				id : 'Foss_Unload_assignunloadtask_TotalPieces',
				fieldLabel: '总件数',
				columnWidth : 1/5,
				xtype : 'numberfield',
				value : 0
			},{
				id : 'Foss_Unload_assignunloadtask_TotalWeight',
				fieldLabel: '总重量(千克)',
				columnWidth : 1/4,
				labelWidth : 120,
				xtype : 'numberfield',
				value : 0
			},{
				id : 'Foss_Unload_assignunloadtask_TotalVolume',
				fieldLabel: '总体积(方)',
				labelWidth : 120,
				columnWidth : 1/4,
				xtype : 'numberfield',
				value : 0
			}
			]
	  }],
	plugins: [{
		pluginId : 'rowexpander_plugin_Id',
		ptype: 'rowexpander',
		rowsExpander: false,
		rowBodyElement: 'Foss.Unload.LeftSubGrid'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.LeftSupGirdStore',{
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					var formRecord = Ext.getCmp('Foss_Unload_LeftForm_Id').getForm().getValues();
					Ext.apply(operation, {
						params : {
							'vo.vehicle.vehicleNo':formRecord.vehicleNo,
							'vo.vehicle.flightNo':formRecord.flightNo,
							'vo.vehicle.billNo':formRecord.billNo,
							'vo.vehicle.arriveTimeBegin':formRecord.arriveTimeBegin,
							'vo.vehicle.arriveTimeEnd':formRecord.arriveTimeEnd,
							'vo.vehicle.productCode':formRecord.productCode,
							'vo.vehicle.departOrg':formRecord.departOrg,
							'vo.vehicle.unloadType':formRecord.unloadType
						}
					});	
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			mode: 'SINGLE',
			allowDeselect : true,
			checkOnly: true,
			showHeaderCheckbox : false 
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		unload.assignunloadtask.leftSupPagingBar = me.bbar;
		me.callParent([cfg]);	
	},
	listeners: {
		itemdblclick:function( view,record,item,index,e,eOpts ){
			//alert("test"+item);
		},
		select: function(record,index,eOpts) {
			var grid = this,
			plugin = grid.getPlugin('rowexpander_plugin_Id');
			if(!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				var store = item.getStore();
				var vehicleNo = store.getAt(0).get('vehicleNo'),
				    arriveTime = store.getAt(0).get('arriveTime');
				if(vehicleNo==index.data.vehicleNo && arriveTime==index.data.arriveTime){
					grid.setSelections(item);
				}else{
					grid.setDeselections(item);
				}
			}		
		},
		deselect:function(record,index,eOpts){
			var grid = this,
			plugin = grid.getPlugin('rowexpander_plugin_Id');
			if(!Ext.isEmpty(plugin.getExpendRow())) {
				var item = plugin.getExpendRowBody();
				var store = item.getStore();
				var vehicleNo = store.getAt(0).get('vehicleNo'),
				    arriveTime = store.getAt(0).get('arriveTime');
				if(vehicleNo==index.data.vehicleNo && arriveTime==index.data.arriveTime){
					grid.setDeselections(item);
				}
			}
		}
	},
	setSelections: function(item) {
		var state = "",
		records = [],
		store = item.getStore(),
		sm = item.getSelectionModel();
		for(var i=0,count=store.getCount(); i<count; i++) {
			state = store.getAt(i).get('assignState');
			if(state == unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unassign')) {//未分配
				records.push(store.getAt(i));
			}
		}
		sm.select(records);
	},
	setDeselections: function(item) {
		sm = item.getSelectionModel();
		sm.deselectAll();
	}
});
/**-----------------------------------------------LeftMainPanel------------------------------**/
Ext.define('Foss.Unload.LeftMainPanel', {
	extend: 'Ext.panel.Panel',
	frame: true,
	//width: 500,
	layout: 'auto',
	items: [
		Ext.create('Foss.Unload.LeftForm',
				{id: 'Foss_Unload_LeftForm_Id'}),
		Ext.create('Foss.Unload.LeftSupGird',
				{id: 'Foss_Unload_LeftSupGird_Id'})
	]
});
/**-----------------------------------------------Right------------------------------**/
/**-----------------------------------------------RightForm------------------------------**/
Ext.define('Foss.Unload.RightForm',{
	extend: 'Ext.form.Panel',
	title: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryLoader'),//'查找理货员',
	layout: 'column',
	//width: 490,
	border: false,
	frame: false,
	defaults: {
		margin: '5 3 7 3'
	},
	items: [{
		xtype: 'commonemployeeselector',
		//xtype: 'textfield',
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.loader'),//'理货员',
		name: 'loader',
		columnWidth:.30,
		labelWidth: 50
	},{
		xtype: 'commonpositionselector',
		//xtype: 'textfield',
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.title'),//'职位',
		name: 'title',
		columnWidth:.30,
		labelWidth: 40
	},{
		xtype: 'dynamicorgcombselector',
		//xtype: 'textfield',
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.org'),//'部门',
		name: 'org',
		columnWidth:.30,
		labelWidth: 40
	},{
		xtype: 'datetimefield_date97',
		labelWidth:50,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.time'),//'时间',
		allowBlank:false,
		editable : false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),01
				,00,00,00),'Y-m-d H:i:s'),
		columnWidth: .25,
		name: 'queryTimeBegin',
		id:'Foss_unload_assignUnloadTask_queryTimeBegin',
		time : true,
		dateConfig: {
			el : 'Foss_unload_assignUnloadTask_queryTimeBegin-inputEl'
		}
	},{
		xtype: 'datetimefield_date97',
		labelWidth:50,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.to'),//'至',
		allowBlank:false,
		editable : false,
		value:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .25,
		name: 'queryTimeEnd',
		id: 'Foss_unload_assignUnloadTask_queryTimeEnd',
		time : true,
		dateConfig: {
			el : 'Foss_unload_assignUnloadTask_queryTimeEnd-inputEl'
		}
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.reset'),//'重置',
			columnWidth:.1,
			handler:function(){
				this.up('form').getForm().reset();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.1,
			html: '&nbsp;'
		},{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.clean'),//'清除',
			columnWidth:.1,
			handler: unload.assignunloadtask.cleanFunction
		},{
			xtype: 'container',
			border : false,
			columnWidth:.15,
			html: '&nbsp;'
		},{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assign'),//'分配',
			disabled : !unload.assignunloadtask.isPermission('unload/assignButton'),
			hidden : !unload.assignunloadtask.isPermission('unload/assignButton'),
			cls:'yellow_button',
			columnWidth:.1,
			handler: unload.assignunloadtask.assignFunction
		},{
			xtype: 'container',
			border : false,
			columnWidth:.1,
			html: '&nbsp;'
		},{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.query'),//'查询',
			disabled : !unload.assignunloadtask.isPermission('unload/queryLoaderButton'),
			hidden : !unload.assignunloadtask.isPermission('unload/queryLoaderButton'),
			name:'query',
			cls:'yellow_button',
			columnWidth:.1,
			handler:function(){
				if(this.up('form').getForm().isValid()){	
					/*var record = this.up('form').getForm().getValues();
					var params ={
							'vo.loader.loaderCode':record.loader,
							'vo.loader.orgCode':record.org,
							'vo.loader.queryTimeBegin':record.queryTimeBegin,
							'vo.loader.queryTimeEnd':record.queryTimeEnd
						};
					Ext.getCmp('Foss_Unload_RightGrid_Id').store.load({
						params : params,
						callback : function(records, operation, success) {
							if (success == false) {
								Ext.Msg.alert('提示',"???");
							}
						}
					});*/
					var time1 = this.up('form').getForm().getValues().queryTimeBegin,
					time2 = this.up('form').getForm().getValues().queryTimeEnd,
					quertTimeBegin = new Date(time1.substring(0,4),time1.substring(5,7)-1,time1.substring(8,10),time1.substring(11,13),time1.substring(14,16),time1.substring(17,19)),
					queryTimeEnd = new Date(time2.substring(0,4),time2.substring(5,7)-1,time2.substring(8,10),time2.substring(11,13),time2.substring(14,16),time2.substring(17,19));
					if(queryTimeEnd>quertTimeBegin){
						if( queryTimeEnd.getTime() -quertTimeBegin.getTime() <= 31*24*60*60*1000){
							unload.assignunloadtask.loaderPagingBar.moveFirst();
							Ext.getCmp('Foss_Unload_RightGrid_Id').show();
						}else{
							//Ext.ux.Toast.msg('提示', '查询时间跨度不能大于31天!', 'error', 3000);
							Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryError1'), 'error', 3000);
						}
					}else{
						//Ext.ux.Toast.msg('提示', '查询开始时间不能大于查询截止时间!', 'error', 3000);
						Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryError2'), 'error', 3000);
					}
				}else{
					//Ext.ux.Toast.msg('提示', '请补全查询条件!', 'error', 3000);
					Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.queryError3'), 'error', 3000);
				}
			}
		}]
	}]
});
/**-----------------------------------------------RightModel------------------------------**/
Ext.define('Foss.Unload.RightModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name:'id',type:'string'},
		{name: 'loaderCode',type: 'string'},
		{name: 'loaderName',type: 'string'},
		{name: 'orgName',type: 'string'},
		{name: 'orgCode',type: 'string'},
		{name: 'title',type: 'string'},
		{name: 'state',type: 'string'},
		{name: 'unfinishedWeight',type: 'float'},
		{name: 'unfinishedTaskQty',type: 'int'},
		{name: 'assignedWeight',type: 'float'},
		{name: 'finishedWeight',type: 'float'}
	]
});
/**-----------------------------------------------RightStore------------------------------**/
Ext.define('Foss.Unload.RightStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.Unload.RightModel',
	proxy : {
		type : 'ajax',
		url : unload.realPath('queryLoader.action'),//'../unload/queryLoader.action',
		actionMethods : 'post',
		pageSize: 10,
		reader : {
			type : 'json',
			root : 'vo.loaders',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	}
});
/**-----------------------------------------------RightGrid------------------------------**/
Ext.define('Foss.Unload.RightGrid',{
	extend: 'Ext.grid.Panel',
	frame: false,
	//width: 400,
	autoScroll:true,
	emptyText : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.dataNotFind'),//'查询结果为空',
	height:460,
	columns:[
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.loaderCode'),//'工号',
			dataIndex : 'loaderCode', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.name'),//'姓名',
			dataIndex : 'loaderName', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.title'),//'职位',
			dataIndex : 'title', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.org'),//'部门',
			dataIndex : 'orgName', flex: 1.0},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.state'),//'状态',
			dataIndex : 'state', flex: 0.8,
			renderer: function(value) {
				if(value=='BUNDLE'){
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.onLine');//'在线';
				}else{
					return unload.assignunloadtask.i18n('foss.unload.assignunloadtask.offLine');//'离线';
				}
			}	
		},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unfinishedWeight'),//'未完成<br/>货量',
			dataIndex : 'unfinishedWeight', flex: 0.8,
			renderer: function(value) {
				if(value){
					return value;
				}else{
					return 0;
				}
			}	
		},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unfinishedTaskQty'),//'未完成<br/>任务数',
			dataIndex : 'unfinishedTaskQty', flex: 0.8,
			renderer: function(value) {
				if(value){
					return value;
				}else{
					return 0;
				}
			}	
		},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assignedWeight'),//'已分配<br/>货量',
			dataIndex : 'assignedWeight', flex: 0.8,
			renderer: function(value) {
				if(value){
					return value;
				}else{
					return 0;
				}
			}	
		},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.finishedWeight'),//'已完成<br/>货量',
			dataIndex : 'finishedWeight', flex: 0.8,
			renderer: function(value) {
				if(value){
					return value;
				}else{
					return 0;
				}
			}	
		}
	],	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.RightStore',{
			listeners : {
				'beforeload' : function(store, operation, eOpts){
					unload.assignunloadtask.loaderButtonMask.show();
					var formRecord = Ext.getCmp('Foss_Unload_RightFormPanel_Id').getForm().getValues();
					Ext.apply(operation, {
						params : {
							'vo.loader.loaderCode':formRecord.loader,
							'vo.loader.orgCode':formRecord.org,
							'vo.loader.queryTimeBegin':formRecord.queryTimeBegin,
							'vo.loader.queryTimeEnd':formRecord.queryTimeEnd,
							'vo.loader.title':formRecord.title
						}
					});	
				},
				'load':function(store, operation, eOpts){
					unload.assignunloadtask.loaderButtonMask.hide();
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.RadioModel', {
			mode: 'SINGLE',
			allowDeselect : true,
			checkOnly: true,
			showHeaderCheckbox : false 
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		unload.assignunloadtask.loaderPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
/**-----------------------------------------------RightMainPanel------------------------------**/
Ext.define('Foss.Unload.RightMainPanel',{
	extend: 'Ext.panel.Panel',
	frame: true,
	//margin :'10 10 10 10',
	layout: 'auto',
	//width: 515,
	items: [
		Ext.create('Foss.Unload.RightForm',
				{id: 'Foss_Unload_RightFormPanel_Id'}),
	    Ext.create('Foss.Unload.RightGrid',
	    		{id: 'Foss_Unload_RightGrid_Id'})
	]
});

/**-----------------------------------------------AssignButtonPanel------------------------------**/
/*Ext.define('Foss.Unload.AssignButtonPanel',{
	extend: 'Ext.form.Panel',
	frame:false,
	border : false,
	layout: 'column',
	items: [{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.clean'),//'清除',
			columnWidth:.08,
			handler: unload.assignunloadtask.cleanFunction
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assign'),//'分配',
			cls:'yellow_button',
			columnWidth:.08,
			handler: unload.assignunloadtask.assignFunction
			}]
		}]
});*/
/**-----------------------------------------------AssignPanel------------------------------**/
Ext.define('Foss.Unload.AssignPanel', {
	extend:'Ext.container.Container',
	frame: false,
    /*layout: {
    	type: 'hbox',
    	//padding:'5',
        align: 'top'
    },*/
    items: [{
		xtype:'container',
		columnWidth:1,
		layout:'column',
		items:[Ext.create('Foss.Unload.LeftMainPanel',{id:'Foss_Unload_LeftMainPanel_Id'})],
    	flex: 1.0
    },{
    	xtype:'container',
		columnWidth:1,
		layout:'column',
		items:[Ext.create('Foss.Unload.RightMainPanel',{id:'Foss_Unload_RightMainPanel_Id'})],
    	flex: 1.0	 
    }
           ]
});
/**---------------------------------------------VehicleWindowForm------------------------------**/
Ext.define('Foss.Unload.VehicleWindowForm',{
	extend: 'Ext.form.Panel',
	border: false,
	modal:true,
	autoScroll: true,
	layout: 'column',
	defaults: {
		margin:'5 10 10 5',
		labelWidth:80
	},
	items: [{
			xtype: 'textfield',
			readOnly:true,
			readOnly : true,
			name: 'vehicleNo',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.vehicleNo')//'车牌号'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'weightTotal',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.weightTotal1')//'总重量'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'vehicleType',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.vechileType')//'车型'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'volumeTotal',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.volumeTotal1')//'总体积'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'arriveTime',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.arriveTime')//'到达时间'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'goodsQtyTotal',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.qtyTotal1')//'总件数'
		},{
			xtype: 'commonplatformselector',
			orgCode : FossUserContext.getCurrentDept().code,
			//readOnly:true,
			name: 'platformNo',
			allowBlank: true,
			//labelWidth:90,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.platformNo')//'月台号'
		},{
			xtype: 'textfield',
			name: 'platformVirtualCode',
			allowBlank: true,
			//labelWidth:90,
			columnWidth :.5,
			hidden : true,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.platformCode')//'月台虚拟编号'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'billQtyTotal',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.billQty')//'单据数'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'line',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.line')//'线路'
		},{
			xtype: 'textfield',
			readOnly:true,
			name: 'fastWayBillQtyTotal',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.fastWayBillQty')//'卡货票数'
		},{
			xtype: 'textfield',
			readOnly:true,
			hidden:true,
			name: 'unloadType',
			allowBlank: true,
			columnWidth :.5,
			fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unloadType')//'卸车类型'
		}]
});
/**---------------------------------------------LoaderWindowForm------------------------------**/
Ext.define('Foss.Unload.LoaderWindowForm',{
	extend: 'Ext.form.Panel',
	border: false,
	autoScroll: true,
	layout: 'column',
	defaults: {
		margin:'5 10 10 5',
		labelWidth:85
	},
	items:[{
		xtype: 'textfield',
		readOnly:true,
		name: 'loaderCode',
		allowBlank: true,
		columnWidth :.5,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.loaderCode')//'工号'
	},{
		xtype: 'textfield',
		readOnly:true,
		name: 'unfinishedWeight',
		allowBlank: true,
		columnWidth :.5,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unfinishedWeight1')//'未完成货量'
	},{
		xtype: 'textfield',
		readOnly:true,
		name: 'loaderName',
		allowBlank: true,
		columnWidth :.5,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.name')//'姓名'
	},{
		xtype: 'textfield',
		readOnly:true,
		name: 'unfinishedTaskQty',
		allowBlank: true,
		columnWidth :.5,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unfinishedTaskQty1')//'未完成任务数'
	},{
		xtype: 'textfield',
		readOnly:true,
		name: 'orgName',
		allowBlank: true,
		columnWidth :.5,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.org')//'部门'
	},{
		xtype: 'textfield',
		readOnly:true,
		name: 'state',
		allowBlank: true,
		columnWidth :.5,
		//emptyText : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.offLine'),//'离线'
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.state')//'状态',
	},{
		xtype: 'textfield',
		readOnly:true,
		name: 'title',
		allowBlank: true,
		columnWidth :.5,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.title')//'职位'
	},{
		xtype: 'textfield',
		readOnly:true,
		name: 'orgCode',
		allowBlank: true,
		hidden : true,
		fieldLabel: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.orgCode')//'部门编码'
	}],
	cancledButton: null,
	getCancledButton: function(){
		if(this.cancledButton==null){
			this.cancledButton = Ext.create('Ext.button.Button',{
				text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.cancled'),//'取消',
				name: 'button_cancel',
				handler:function(){
					unload.assignunloadtask.assignUnloadTaskWindow.hide();
				}
			});
		}
		return this.cancledButton;
	},
	confirmButton: null,
	getConfirmButton: function(){
		if(this.confirmButton==null){
			this.confirmButton = Ext.create('Ext.button.Button',{
				text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.confirm'),//'确认',
				cls:'yellow_button',
				name: 'button_confirm',
				handler:function(){
					var myMask = new Ext.LoadMask(this,{
						msg:""
					});
					myMask.show();
					//unload.assignunloadtask.assignUnloadTaskWindow.hide();
					var billArray = new Array(),
					billStore = Ext.getCmp('Foss_Unload_WindowGrid_Id').store;
					billStore.each(function(record){
						billArray.push(record.data);
					});
					var data = {
							'vo':{	
								'vehicle':Ext.getCmp('Foss_Unload_VehicleWindowForm_Id').getForm().getValues(),
								'loader':Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getForm().getValues(),
								'bills':billArray
							}
					};
					Ext.Ajax.request({
						url : unload.realPath('assign.action'),//'../unload/assign.action',
						jsonData : data,
						success:function(response){
							unload.assignunloadtask.assignUnloadTaskWindow.hide();
							//Ext.ux.Toast.msg('提示', '分配成功!', 'info', 3000);
							Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assignSuccess'), 'info', 3000);
							var loaderGrid = Ext.getCmp('Foss_Unload_RightGrid_Id'),
							loaderRecords = loaderGrid.getSelectionModel().getSelection(),
							leftSupGrid = Ext.getCmp('Foss_Unload_LeftSupGird_Id'),
							vehicleRecords = leftSupGrid.getSelectionModel().getSelection(),
							plugin = leftSupGrid.getPlugin('rowexpander_plugin_Id'),
							windowGrid = Ext.getCmp('Foss_Unload_WindowGrid_Id');
							/*var record = this.up('form').getForm().getValues();
							var params ={
									'vo.loader.loaderCode':record.loader,
									'vo.loader.orgCode':record.org,
									'vo.loader.queryTimeBegin':record.queryTimeBegin,
									'vo.loader.queryTimeEnd':record.queryTimeEnd
								};*/
							var loaderQCRecord = Ext.getCmp('Foss_Unload_RightFormPanel_Id').getForm().getValues();
							var time1 = loaderQCRecord.queryTimeBegin,
							time2 = loaderQCRecord.queryTimeEnd,
							quertTimeBegin = new Date(time1.substring(0,4),time1.substring(5,7)-1,time1.substring(8,10),time1.substring(11,13),time1.substring(14,16),time1.substring(17,19));
							queryTimeEnd = new Date(time2.substring(0,4),time2.substring(5,7)-1,time2.substring(8,10),time2.substring(11,13),time2.substring(14,16),time2.substring(17,19));
							currentTime = new Date(),
							flag = Ext.Date.between(currentTime,quertTimeBegin,queryTimeEnd);
							var lrecords=[];
							loaderGrid.store.each(function(loaderRecord) {
								if(loaderRecord.get('loaderCode')==loaderRecords[0].get('loaderCode')){
									windowGrid.store.each(function(windowRecord){
										loaderRecord.data['unfinishedTaskQty'] = loaderRecord.data['unfinishedTaskQty']+1;
										loaderRecord.data['unfinishedWeight'] = loaderRecord.data['unfinishedWeight']+windowRecord.get('weightTotal');
										if(flag){
											loaderRecord.data['assignedWeight'] = loaderRecord.data['assignedWeight']+windowRecord.get('weightTotal');
										}
									});
								}
								lrecords.push(loaderRecord);
							});
							loaderGrid.store.loadData(lrecords);
							var brecords = [];
							if(!Ext.isEmpty(plugin.getExpendRow())) {
								var billGrid = plugin.getExpendRowBody(),
								billRecords = billGrid.getSelectionModel().getSelection();
								if(billRecords.length!=0){
									billGrid.store.each(function(record) {
										for(var i=0;i<billRecords.length;i++){
											if(record.get('billNo')==billRecords[i].get('billNo')){
												record.data['assignState']=unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assigned');//已分配
											}
										}		
										brecords.push(record);
									});
									billGrid.store.loadData(brecords);
									var assignedAll = true;
									billGrid.store.each(function(record){
										if(record.get('assignState')==unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unAssign')){//未分配
											assignedAll = false;
										}
									});
									if(assignedAll){
										var vrecords = [];
										leftSupGrid.store.each(function(record){
											if(record.get('vehicleNo')==billRecords[0].get('vehicleNo') && record.get('arriveTime')==billRecords[0].get('arriveTime')){
												vrecords.push(record);
											}
										});
										billGrid.store.removeAll();
										leftSupGrid.store.remove(vrecords);
									}else{
										billGrid.getSelectionModel().deselectAll();
									}
								}else{
									if(vehicleRecords.length!=0){
										leftSupGrid.store.remove(vehicleRecords);
									}
								}
							}else{
								if(vehicleRecords.length!=0){
									leftSupGrid.store.remove(vehicleRecords);
									}
								}
							myMask.hide();
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
							//Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
							Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), result.message, 'error', 3000);
							if(result.message == unload.assignunloadtask.i18n('foss.unload.assignunloadtask.invalidPlatform')){//'非本外场月台'
								Ext.getCmp('Foss_Unload_VehicleWindowForm_Id').getForm().findField('platformNo').setValue(null);
							}else{
								unload.assignunloadtask.assignUnloadTaskWindow.hide();
							}
							myMask.hide();
						}
						});
					}
			});
		}
		return this.confirmButton;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.buttons = [me.getCancledButton(),'->',me.getConfirmButton()];
		me.callParent([cfg]);
	}
});
/**---------------------------------------------WindowGrid------------------------------**/
Ext.define('Foss.Unload.WindowGrid',{
	extend: 'Ext.grid.Panel',
	frame: true,
	height: 120,
	autoScroll:true,
	emptyText : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.dataNotFind'),//'查询结果为空',
	//width: 440,
	columns: [
	    Ext.create('Ext.grid.RowNumberer'),
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.billNo'),//'单据编号',
	    	dataIndex : 'billNo', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.origOrg'),//'出发部门',
	    	dataIndex : 'origOrgName', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.fastWayBillQty'),//'卡货票数',
	    	dataIndex : 'fastWayBillQtyTotal', flex: 1},
	    {text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.expressBillQty'),//'快递票数',
		    dataIndex : 'expressWayBillQty', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.weightTotal1'),//'总重量',
	    	dataIndex : 'weightTotal', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.qtyTotal1'),//'总件数',
	    	dataIndex : 'goodsQtyTotal', flex: 1}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.LeftSubGirdStore');
		me.callParent([cfg]);
	}
});
/**---------------------------------------------AssignWindow------------------------------**/
unload.assignunloadtask.assignUnloadTaskWindow = Ext.create('Ext.window.Window',{
	autoDestroy : true,
	closable : true,
	closeAction : 'hide',
	draggable : true,
	width: 488,
	height : 630,
	modal:true,
    layout: 'auto',
    id: 'windForm',
	title: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.detailMessage'),//'详细信息',
	items: [
		Ext.create('Foss.Unload.VehicleWindowForm',{id:'Foss_Unload_VehicleWindowForm_Id'}),
		Ext.create('Foss.Unload.WindowGrid',{id:'Foss_Unload_WindowGrid_Id'}),
		Ext.create('Foss.Unload.LoaderWindowForm',{id:'Foss_Unload_LoaderWindowForm_Id'})
	]
});
/**---------------------------------------------Cancel------------------------------**/
/**---------------------------------------------CancelModel------------------------------**/
Ext.define('Foss.Unload.CancelModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name: 'id',type:'string'},
		{name: 'vehicleNo',type:'string'},
		{name: 'vehicleType',type:'string'},
		{name: 'arriveTime',type:'string'},
		{name: 'weightTotal',type:'float'},
		{name: 'volumeTotal',type:'float'},
		{name: 'goodsQtyTotal',type:'int'},
		{name: 'billQtyTotal',type:'int'},
		{name: 'platformNo',type:'string'},
		{name: 'fastWayBillQtyTotal',type:'float'},
		{name: 'unloadType',type:'string'},
		{name: 'line',type:'string'},
		{name: 'platformVirtualCode',type:'string'},
		{name: 'createTime',type:'string'},
		{name: 'loaderName',type:'string'},
		{name: 'loaderCode',type:'string'},
		{name: 'loaderCode',type:'string'},
		{name: 'loaderOrgName',type:'string'}
	]
});
/**---------------------------------------------CancelStore------------------------------**/
Ext.define('Foss.Unload.CancelStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.Unload.CancelModel',
	pageSize:10,
	proxy : {
		type : 'ajax',
		url : unload.realPath('refreshAssignedTaskList.action'),//'../unload/refreshAssignedTaskList.action',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'vo.totalTasks',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			unload.assignunloadtask.refrushButtonMask.show();
		},
		'load':function(store, operation, eOpts){
			unload.assignunloadtask.refrushButtonMask.hide();
		}
	}
});
/**---------------------------------------------CancelGrid------------------------------**/
Ext.define('Foss.Unload.CancelGrid',{
	extend: 'Ext.grid.Panel',
	autoScroll:true,
	emptyText : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.dataNotFind'),//'查询结果为空',
	frame: true,
	border: false,
	//bodyCls: 'autoHeight',
	height:400,
	margin: '10 0 0 0',
	columns:[
		{
			xtype : 'actioncolumn',
			flex: 0.3,
			text : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.operate'),//'操作',//操作
			align : 'center',
			items : [ {
				tooltip : unload.assignunloadtask.i18n('foss.unload.assignunloadtask.watch'),//'查看',//查看
				iconCls : 'deppon_icons_showdetail',
				handler : function(grid, rowIndex, colIndex) {
					unload.assignunloadtask.cancelPanelMask.show();
					var windowGrid = Ext.getCmp('Foss_Unload_WindowGrid_Id'),
					loaderForm = Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getForm(),
					vehicleForm = Ext.getCmp('Foss_Unload_VehicleWindowForm_Id').getForm();
					unload.assignunloadtask.assignUnloadTaskWindow.restore();
					windowGrid.store.removeAll();
					var record = grid.store.getAt(rowIndex);
					
					Ext.Ajax.request({
						url : unload.realPath('refreshAssignedTaskDetail.action'),
						params :{
							'vo.task.vehicle.vehicleNo':record.get('vehicleNo'),
							'vo.task.vehicle.unloadType':record.get('unloadType'),
							'vo.task.createTime':record.get('createTime'),
							'vo.task.loader.loaderCode':record.get('loaderCode'),
							'vo.task.loader.loaderName':record.get('loaderName'),
							'vo.task.loader.orgCode':record.get('loaderOrgCode'),
							'vo.task.loader.orgName':record.get('loaderOrgName')
						},
						success:function(response){
							var result = Ext.decode(response.responseText),
							loader = result.vo.task.loader,
							bills = result.vo.task.bills;
							windowGrid.store.loadData(bills);
							vehicleForm.findField('vehicleNo').setValue(record.get('vehicleNo'));
							vehicleForm.findField('weightTotal').setValue(record.get('weightTotal'));
							vehicleForm.findField('vehicleType').setValue(record.get('vehicleType'));
							vehicleForm.findField('volumeTotal').setValue(record.get('volumeTotal'));
							vehicleForm.findField('arriveTime').setValue(record.get('arriveTime'));
							vehicleForm.findField('goodsQtyTotal').setValue(record.get('goodsQtyTotal'));
							vehicleForm.findField('platformNo').setValue(record.get('platformNo'));
							vehicleForm.findField('platformVirtualCode').setValue(record.get('platformVirtualCode'));
							vehicleForm.findField('billQtyTotal').setValue(record.get('billQtyTotal'));
							vehicleForm.findField('line').setValue(record.get('line'));
							vehicleForm.findField('fastWayBillQtyTotal').setValue(record.get('fastWayBillQtyTotal'));
							vehicleForm.findField('unloadType').setValue(record.get('unloadType'));
							
							loaderForm.findField('loaderCode').setValue(loader.loaderCode);
							loaderForm.findField('unfinishedWeight').setValue(loader.unfinishedWeight);
							loaderForm.findField('loaderName').setValue(loader.loaderName);
							loaderForm.findField('unfinishedTaskQty').setValue(loader.unfinishedTaskQty);
							loaderForm.findField('orgName').setValue(loader.orgName);
							if(loader.state!=null && loader.state!='UNBUNDLE'&& loader.state!=''){
								loaderForm.findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.onLine'));//在线
							}else{
								loaderForm.findField('state').setValue(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.offLine'));//离线
							}
							loaderForm.findField('title').setValue(loader.title);
							loaderForm.findField('orgCode').setValue(loader.orgCode);
							loaderForm.findField('button_confirm');
							Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getCancledButton().setVisible(false);
							Ext.getCmp('Foss_Unload_LoaderWindowForm_Id').getConfirmButton().setVisible(false);
							unload.assignunloadtask.assignUnloadTaskWindow.show();
							unload.assignunloadtask.cancelPanelMask.hide();
						},
						exception:function(response){
							unload.assignunloadtask.cancelPanelMask.hide();
							var result = Ext.decode(response.responseText);
							//Ext.Msg.alert('提示',result.message);
							Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), result.message, 'error', 3000);
						}
					});
					}
				} ]
		},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.vehicleNo'),//'车牌号',
			dataIndex : 'vehicleNo', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.vechileType'),//'车型',
			dataIndex : 'vehicleType', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.arriveTime'),//'到达时间',
			dataIndex : 'arriveTime', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.weightTotal1'),//'总重量',
			dataIndex : 'weightTotal', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.volumeTotal1'),//'总体积',
			dataIndex : 'volumeTotal', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.qtyTotal1'),//'总件数',
			dataIndex : 'goodsQtyTotal', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.billQty'),//'单据数',
			dataIndex : 'billQtyTotal', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.platformNo'),//'月台号',
			dataIndex : 'platformNo', flex: 0.8},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.fastWayBillQty'),//'卡货票数',
			dataIndex : 'fastWayBillQtyTotal', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assignTime'),//'分配时间',
			dataIndex : 'createTime', flex: 1},
		{text: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.loader'),//'理货员',
			dataIndex : 'loaderName', flex: 1}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.Unload.CancelStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode: 'SINGLE',
			allowDeselect : true,
			checkOnly: true,
			showHeaderCheckbox : false 
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		unload.assignunloadtask.cancelPagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
/**---------------------------------------------CancelButton------------------------------**/
Ext.define('Foss.Unload.CancelButtonPanel',{
	extend: 'Ext.form.Panel',
	frame:false,
	border : false,
	layout: 'column',
	items: [{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.reflush'),//'刷新',
			disabled : !unload.assignunloadtask.isPermission('unload/refreshAssignedTaskListButton'),
			hidden : !unload.assignunloadtask.isPermission('unload/refreshAssignedTaskListButton'),
			columnWidth:.08,
			handler:function(){
				/*Ext.getCmp('Foss_Unload_CancelGrid_Id').store.load(
						{
							callback : function(records, operation, success) {
								if (success == false) {
									Ext.Msg.alert('提示',"???");
								}
							}
						}		
				);*/
				unload.assignunloadtask.cancelPagingBar.moveFirst();
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.cancelAssign'),//'取消分配',
			disabled : !unload.assignunloadtask.isPermission('unload/cancelAssignedUnloadTaskButton'),
			hidden : !unload.assignunloadtask.isPermission('unload/cancelAssignedUnloadTaskButton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				records = Ext.getCmp('Foss_Unload_CancelGrid_Id').getSelectionModel().getSelection();
				if(records.length!=0){
					//Ext.Msg.confirm('提示', '是否确认取消分配', function(btn){
					Ext.Msg.confirm(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.beConfirmCancel'), function(btn){
						if (btn == 'yes'){
							unload.assignunloadtask.cancelPanelMask.show();
							Ext.Ajax.request({
								url : unload.realPath('cancelAssignedUnloadTask.action'),//'../unload/cancelAssignedUnloadTask.action',
								params :{
									'vo.task.vehicle.vehicleNo':records[0].get('vehicleNo'),
									'vo.task.vehicle.unloadType':records[0].get('unloadType'),
									'vo.task.createTime':records[0].get('createTime')
								},
								success:function(response){
									unload.assignunloadtask.cancelPanelMask.hide();
									Ext.getCmp('Foss_Unload_CancelGrid_Id').store.remove(records[0]);
									//Ext.Msg.alert('提示','已取消分配');
									Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.cancelSuccess'), 'info', 3000);
								},
								exception:function(response){
									unload.assignunloadtask.cancelPanelMask.hide();
									var result = Ext.decode(response.responseText);
									//Ext.Msg.alert('提示',result.message);
									Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), result.message, 'error', 3000);
								}
							});
						}
				});
				}else{
					//Ext.Msg.alert('提示','没有选择记录');
					Ext.ux.Toast.msg(unload.assignunloadtask.i18n('foss.unload.assignunloadtask.prompt'), unload.assignunloadtask.i18n('foss.unload.assignunloadtask.unchooseRecord'), 'error', 3000);
				}
			}
			}]
	}]
});
Ext.define('Foss.Unload.CancelPanel',{
	extend: 'Ext.form.Panel',
	frame:false,
	border : false,
	layout: 'auto',
	items: [
	        Ext.create('Foss.Unload.CancelButtonPanel',{id:'Foss_Unload_CancelButtonPanel_Id'}),
	        Ext.create('Foss.Unload.CancelGrid',{id:'Foss_Unload_CancelGrid_Id'})
	        ]
});
Ext.define('Foss.Unload.AssignMainPanel',{
	extend: 'Ext.form.Panel',
	frame:false,
	border : false,
	layout: 'auto',
	items: [
	    Ext.create('Foss.Unload.AssignPanel')
	    //,Ext.create('Foss.Unload.AssignButtonPanel')
    ],
    buttons: [{
		text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.clean'),//'清除',
		handler: unload.assignunloadtask.cleanFunction
	},{
		xtype: 'label',
        pid: 'remindMessage',
        text: '',
        margin: '0 0 0 10'
	},'->',{
		text:unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assign'),//'分配',
		disabled : !unload.assignunloadtask.isPermission('unload/assignButton'),
		hidden : !unload.assignunloadtask.isPermission('unload/assignButton'),
		cls:'yellow_button',
		handler: unload.assignunloadtask.assignFunction
	}]
});
/**---------------------------------------------MainTabPanel------------------------------**/
Ext.define('Foss.Unload.MainTabPanel',{
	extend: 'Ext.tab.Panel',
	frame:false,
	//bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	layout: 'auto',
	items: [{
		itemId: 'temporary',
		title: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assignUnloadTask'),//'分配卸车任务',
		items: [  
		    //{margin: '20 0 20 0'},
			Ext.create('Foss.Unload.AssignMainPanel',{id:'Foss_Unload_AssignMainPanel_Id'})
		]
	},{
		itemId: 'forever',
		title: unload.assignunloadtask.i18n('foss.unload.assignunloadtask.assignedUnloadTask'),//'已分配卸车任务',
		items:[
		    //{margin: '20 10 20 10'},
		    Ext.create('Foss.Unload.CancelPanel',{id:'Foss_Unload_CancelPanel_Id'})
		],
		hidden : true
	}],
	listeners : {
		beforerender: {
			fn : function(tabPanel, eOpts ){
				Ext.getCmp('Foss_Unload_LeftSupGird_Id').setVisible(false);
				Ext.getCmp('Foss_Unload_RightGrid_Id').setVisible(false);
				Ext.getCmp('Foss_Unload_MainTabPanel_Id').setActiveTab(0);
			}
		},
		beforetabchange :{
			fn: function(tabPanel, newCard, oldCard, eOpts ){
			}
		}
	}
});

/**-----------------------------------------------view panel--------------------------------------------------**/
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id:'T_unload-assignunloadtaskindex_content',
		frame:false,
		cls:'panelContentNToolbar',
		//style:'padding-top:10px',
		items : [Ext.create('Foss.Unload.MainTabPanel',
				{id: 'Foss_Unload_MainTabPanel_Id'})],
		listeners: {
		    'boxready': function(){
		    	unload.assignunloadtask.cancelPanelMask = new Ext.LoadMask(Ext.getCmp('Foss_Unload_CancelPanel_Id'),{
		    		msg:""
		    	});
		    	unload.assignunloadtask.assignMainPanelMask = new Ext.LoadMask(Ext.getCmp('Foss_Unload_AssignMainPanel_Id'),{
		    		msg:""
		    	});
		    	unload.assignunloadtask.loaderButtonMask = new Ext.LoadMask(Ext.getCmp('Foss_Unload_RightFormPanel_Id').items.items[5].items.items[6],{
		    		msg:""
		    	});
		    	unload.assignunloadtask.refrushButtonMask = new Ext.LoadMask(Ext.getCmp('Foss_Unload_CancelButtonPanel_Id').items.items[0].items.items[0],{
		    		msg:""
		    	});
		    }
		},		
		renderTo: 'T_unload-assignunloadtaskindex-body'
	});
});