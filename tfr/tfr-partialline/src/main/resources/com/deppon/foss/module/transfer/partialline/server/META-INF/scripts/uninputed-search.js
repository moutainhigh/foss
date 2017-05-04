//package Foss.uninputedpartial 未录入偏线外发查询
//未录入偏线model
Ext.define('Foss.uninputedpartial.model.HandoverBillDetail', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'detailId',type:'string'},//
		{name: 'waybillNo',type:'string'},//运单号			
		{name: 'handoverNo', type: 'string'},//交接单号
		{name: 'handoverGoodsQty', type: 'float'},//件数
		{name: 'handoverWeight', type: 'float'},//重量（公斤）
		{name: 'handoverVolume', type: 'float'},//体积（方）
		{name: 'destOrgName', type: 'string'},//外发代理
		{name: 'destOrgCode', type: 'string'},//外发代理COde
		{name: 'handoverTime', type: 'date',//外发交接时间
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		}
	]
});

//未录入偏线store
Ext.define('Foss.uninputedpartial.store.HandoverBillDetail',{
	extend: 'Ext.data.Store',
	model: 'Foss.uninputedpartial.model.HandoverBillDetail',
	pageSize:10,
	proxy: {
		type: 'ajax',
		url:partialline.realPath('queryUninputedpartial.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.uninputedpartials',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = partialline.uninputedpartial.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();				
				Ext.apply(operation, {
					params : queryParams
				});	
			}
		},
		load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records))
			Ext.ux.Toast.msg('提示信息', '查询结果为空!');
		}
	}
});

//查询表单
Ext.define('Foss.uninputedpartial.form.HandoverBillDetailSearch',{
	extend: 'Ext.form.Panel',
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:100
	},
	items: [{
		xtype : 'commonvehagencycompselector',
		fieldLabel: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.destOrgName.label'),//外发代理
		name: 'vo.uninputDto.destOrgCode',
		allowBlank:true,
		columnWidth:.3	
	},{
		fieldLabel: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverNo.label'),//交接单号
		name: 'vo.uninputDto.handoverNo',
		allowBlank:true,
		columnWidth:.3
	},{
		vtype:'order',
		enableKeyEvents:true,
		fieldLabel: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.waybillNo.label'),//运单号
		name: 'vo.uninputDto.waybillNo',
		allowBlank:true,
		columnWidth:.3,
		listeners:{
		 blur:function(field,eopts){
		 	if(field.isValid( )){
		 		var waybillNo = field.getValue();
		 		partialline.uninputedpartial.queryHandedUninputWaybill(waybillNo);
		 	}		 	
		 }
		}
	},{
		xtype: 'rangeDateField',
		fieldLabel: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverTime.label'),//交接时间
		dateType: 'datetimefield_date97',
		id:'Foss.uninputedpartial.model.handoverBillDetail.handoverTime_RangeDateField_ID',
		fieldId:'handoverTime_scheduleDateFrom_ID',
		fromName: 'vo.uninputDto.handoverTimeFrom',
		toName: 'vo.uninputDto.handoverTimeTo',
		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
					,'00','00','00'), 'Y-m-d H:i:s'),
		toValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'),
		editable: false,
		dateRange:7,
		labelWidth: 100,
		disallowBlank:true,
		allowBlank:false,
		columnWidth: .6	
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.form.handoverBillDetailSearch.button.reset'),//重置
			handler: function() {	
				var bform = partialline.uninputedpartial.queryForm.getForm();
				bform.findField('vo.uninputDto.destOrgCode').reset();
				bform.findField('vo.uninputDto.handoverNo').reset();
				bform.findField('vo.uninputDto.waybillNo').reset();
				bform.findField('vo.uninputDto.handoverTimeFrom').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
					,'00','00','00'), 'Y-m-d H:i:s'));
				bform.findField('vo.uninputDto.handoverTimeTo').setRawValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
		,'23','59','59'), 'Y-m-d H:i:s'));
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.form.handoverBillDetailSearch.button.query'),//查询
			cls:'yellow_button',
			handler: function() {
				
				//按运单号查询时不受其他条件的限制。若无此单号，弹框提示：请输入正确的单号。
				var form=this.up('form').getForm();
				if(Ext.isEmpty(form.findField('vo.uninputDto.waybillNo').getValue())){				
					var formValid=form.isValid( );
					if(formValid){
						partialline.uninputedpartial.pagingBar.moveFirst();
					}
				}else{
					if(form.findField('vo.uninputDto.waybillNo').isValid( )){
						partialline.uninputedpartial.pagingBar.moveFirst();
					}
					
				}
								
			}
		}]
	}]
});


//查询结果列表
Ext.define('Foss.partialline.grid.UninputGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	collapsible: true,
	animCollapse: false,
	title:partialline.uninputedpartial.i18n('Foss.uninputedpartial.grid.uninputGrid.title'),//未录入外发单列表
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	selModel: null,	
	autoScroll:true,
	columns: [{
		xtype:'actioncolumn',
		menuDisabled:true,
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.grid.uninputGrid.actioncolumn.action'),//操作
		align: 'center',
		fixed: true,
		width: 60,
		sortable: false,
		items: [{
                tooltip: partialline.uninputedpartial.i18n('Foss.uninputedpartial.grid.uninputGrid.actioncolumn.tooltip'),//录入外发单
				iconCls:'deppon_icons_showdetail',		
				width:20,
                handler: function(grid, rowIndex, colIndex) {		
					this.up('grid').openWindow();
					var record = grid.getStore().getAt(rowIndex);
					//加载运单基本信息
					var tmpForm=Ext.getCmp('Foss_uninputedpartial_form_viewpartialline_ID');
					tmpForm.getForm().findField('waybillNo').setValue(record.data.waybillNo);
					 tmpForm.getForm().findField('externalOrgName').setValue(FossUserContext. getCurrentDept().name);
					 tmpForm.getForm().findField('registerUser').setValue(FossUserContext.getCurrentUser().employee.empName);
					 tmpForm.getForm().findField('registerTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()), 'Y-m-d'));
					tmpForm.queryWayBillInfo(record.data.waybillNo,tmpForm.getForm());
					Ext.getCmp('Foss_partialline_form_Viewpartialline_button_save_ID').enable(true);
					Ext.getCmp('Foss_uninputedpartial_form_Viewpartialline_button_reset_ID').enable(true);
                }
            }]
	},{		
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.waybillNo.label'),//运单号
		flex: 1, 
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'waybillNo'
	},{
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverNo.label'),//交接单号
		flex: 1, 
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'handoverNo'
	},{
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverGoodsQty.label'),//件数
		flex: 1, 
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'handoverGoodsQty'
	},{
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverWeight.label'),//重量(公斤)
		flex: 1, 
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'handoverWeight'
	},{
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverVolume.label'),//体积（方）
		flex: 1, 
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'handoverVolume'
	},{
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.destOrgName.label'),//外发代理
		flex: 1, 
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'destOrgName'
	},{
		text: partialline.uninputedpartial.i18n('Foss.uninputedpartial.model.handoverBillDetail.handoverTime.label'),//外发交接时间
		flex: 1, 
		menuDisabled:true,
		sortable: false, 
		dataIndex: 'handoverTime'
	}],
	//打开录入窗口进行录入
	openWindow:function(){
		//获取窗口设置
		var plwindow ;						
		if(partialline.uninputedpartial.plwindow == null){
			partialline.uninputedpartial.plwindow=Ext.create('Foss.uninputedpartial.window.Viewpartialline');			
		}
		plwindow=partialline.uninputedpartial.plwindow;
		
		//获取录入窗口表单
		var tmpForm=Ext.getCmp('Foss_uninputedpartial_form_viewpartialline_ID');
		partialline.uninputedpartial.plform=tmpForm;
		tmpForm.getForm().reset();
		//重置数据
		//设置窗口居中		
		plwindow.center().show();			
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.uninputedpartial.store.HandoverBillDetail');
		me.bbar =Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		partialline.uninputedpartial.pagingBar = me.bbar;
		me.callParent([cfg]);
	}	
});

//提示
Ext.define('Foss.uninputedpartial.form.tip',{
	extend: 'Ext.form.Panel',
	layout:'column',
	frame: false,
	 defaults:{
			xtype: 'textfield',
			margin:'0 5 5 5',
			anchor: '90%'					
	},	
	items:[{
		name: 'contactPhone',							
		fieldLabel: '',
		labelSeparator: '',
		xtype: 'textareafield',
		labelWidth:70,		
		readOnly:true,	
		fieldStyle:'color:red',
		columnWidth:.9
	}],	
	bindData : function(record){
		me=this;
		me.items.items[0].setValue(record);
	},
	constructor: function(config){		
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
		
	}
});

//查看偏线外发单表单
Ext.define('Foss.uninputedpartial.form.Viewpartialline',{
		extend: 'Ext.form.Panel',			
		bodyStyle:'padding:5px 5px 0',
		id:'Foss_uninputedpartial_form_viewpartialline_ID',
		fieldDefaults: {
			msgTarget: 'side',
			labelWidth: 90,
			margin:'5 5 5 0'
		},
		defaults: {
			anchor: '97%'
		},
		//查看运单基本信息
		queryWayBillInfo:function(selectedVal,form){
			//可填写
			form.findField('payAgencyFee').setReadOnly(false);
			//可填写
			form.findField('receiveAgencyFee').setReadOnly(false);
			var params ;
			//录入外发单情况,需要验证运单号是否已录入validateWaybillNo:yes
				params = {vo:{dto:{waybillNo:selectedVal},validateWaybillNo:'yes'}};		
				Ext.Ajax.request({
        			url : partialline.realPath('queryWaybillInfo.action'),
        			jsonData:params,
        			success : function(response) {
        				var json = Ext.decode(response.responseText);
        				var billInfo=json.vo.billInfo;					        				
        				var tmpForm=Ext.getCmp('Foss_uninputedpartial_form_viewpartialline_ID');
        				//设置数据
        				tmpForm.getForm().findField('currencyCode').setValue(billInfo.currencyCode);
        				tmpForm.getForm().findField('paidMethod').setValue(billInfo.paidMethod);
        				tmpForm.getForm().findField('toPayAmount').setValue(billInfo.toPayAmount);
        				tmpForm.getForm().findField('agentCompanyName').setValue(billInfo.agentCompanyName);
        				tmpForm.getForm().findField('agentDeptName').setValue(billInfo.agentDeptName);
        				tmpForm.getForm().findField('contactPhone').setValue(billInfo.contactPhone);
        				tmpForm.getForm().findField('address').setValue(billInfo.address);
        				tmpForm.getForm().findField('targetOrgCode').setValue(billInfo.targetOrgCode);
        				tmpForm.getForm().findField('handGoodsTime').setValue(billInfo.handGoodsTime);
        				tmpForm.getForm().findField('createOrgCode').setValue(billInfo.createOrgCode);
        				tmpForm.getForm().findField('goodsWeightTotal').setValue(billInfo.goodsWeightTotal);
        				tmpForm.getForm().findField('goodsVolumeTotal').setValue(billInfo.goodsVolumeTotal);
        				tmpForm.getForm().findField('goodsQtyTotal').setValue(billInfo.goodsQtyTotal);
        				tmpForm.getForm().findField('transportFee').setValue(billInfo.transportFee);
        				tmpForm.getForm().findField('goodsName').setValue(billInfo.goodsName);
        				tmpForm.getForm().findField('insuranceFee').setValue(billInfo.insuranceFee);
        				tmpForm.getForm().findField('goodsPackage').setValue(billInfo.goodsPackage);
        				tmpForm.getForm().findField('deliveryCustomerName').setValue(billInfo.deliveryCustomerName);
        				tmpForm.getForm().findField('insuranceAmount').setValue(billInfo.insuranceAmount);
        				tmpForm.getForm().findField('yunshushixiang').setValue(billInfo.yunshushixiang);
        				tmpForm.getForm().findField('totalFee').setValue(billInfo.totalFee);
        				tmpForm.getForm().findField('codAmount').setValue(billInfo.codAmount);
        				tmpForm.getForm().findField('receiveCustomerName').setValue(billInfo.receiveCustomerName);
        				tmpForm.getForm().findField('partnerContactPhone').setValue(billInfo.partnerContactPhone);					        				
        				tmpForm.getForm().findField('paidMethodCode').setValue(billInfo.paidMethodCode);					        				
        				//SR-12	根据运单号查开单信息，判断是否自提，如果是则不能输入代理送货费
        				//录入外发单情况，判断是否自提，为判断是否录入付送货费做准备			        					
							var beAutoDelivery = billInfo.beAutoDelivery;
							if(beAutoDelivery=='SELF_PICKUP'){
								tmpForm.getForm().findField('deliveryFee').setValue(0);
								tmpForm.getForm().findField('deliveryFee').setReadOnly(true);
							}													
							else{
								tmpForm.getForm().findField('deliveryFee').setReadOnly(false);
							}								  
					    Ext.getCmp('Foss_partialline_form_Viewpartialline_button_save_ID').enable(true);
					    //验证费用信息
						partialline.uninputedpartial.validateFeesAndWriteOff(form);  
						
        			},
        			exception : function(response) {
        				var json = Ext.decode(response.responseText);
        				Ext.getCmp('Foss_partialline_form_Viewpartialline_button_save_ID').disable(true);
        				//重置所有的运单信息
        				var tmpForm=Ext.getCmp('Foss_uninputedpartial_form_viewpartialline_ID');
        				var fields=tmpForm.getForm().getFields( );
        				for( var i=15 ;i<21;i++){
        					fields.get(i).reset();
        				}
        				for( var i=21 ;i<fields.length;i++){
        					fields.get(i).reset();
        				}
        				//弹出提示信息
        				Ext.MessageBox.alert(partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
        			}
        		});
		},
		items : [{				
				xtype:'fieldset',
				title: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.title.fieldset1'),//偏线外发单,
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							vtype:'order',
							enableKeyEvents:true,
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.waybillNo'),//'运单号',
							name: 'waybillNo',
							allowBlank:false,
							readOnly:true,
							columnWidth:.33,
							listeners:{
								blur:function(txtField,eOpts ){
									if(txtField.isValid()){
									var selectedVal=txtField.value;
									this.up('form').queryWayBillInfo(selectedVal,this.up('form').getForm());
									}
								}
							}		
						},
							{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalBillNo'),//'外发单号',
							name: 'externalBillNo',
							allowBlank:false,
							maxLength:50,
							columnWidth:.33	
						},{
							xtype : 'commonemployeeselector',
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalUserName'),// '外发员',
							name: 'externalUserCode',
							deptCode: FossUserContext.getCurrentDeptCode(),
							allowBlank:false,
							columnWidth:.33,
							listeners:{
								blur:function(field, eOpts){
									if(!Ext.isEmpty(field.getValue( ))){
										partialline.uninputedpartial.queryExternalOrgName(this.up('form'),field.getValue( ));
									}
								
								}
							}	
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: '外发代理费',//partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.externalAgencyFee'),// '外发代理费',
							name: 'externalAgencyFee',
							allowBlank:false,
							columnWidth:.33,
							value:0,
							minValue:0,
							maxLength:8,
							listeners: {
						            change: function(field, value) {	
						                var deliveryFee=this.up('form').getValues().deliveryFee;
						                if(deliveryFee =='' ||deliveryFee == 'undefined' || deliveryFee == undefined )
						                	deliveryFee=0.000;
						                var total =0.00;
						                total=parseFloat(deliveryFee)+value;
						                this.up('form').getForm().findField('costAmount').setValue(total.toFixed(3));
						            },
							         blur:function(field,eOpts){
							         	//验证费用信息
							            partialline.uninputedpartial.validateFeesAndWriteOff( this.up('form').getForm());
							         }
						        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '外发代理费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '外发代理费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '外发代理费必须是数字';
									}
								}
							}	
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.deliveryFee'),// '付送货费',
							name: 'deliveryFee',
							allowBlank:false,
							columnWidth:.33,
							value:0,
							minValue:0,
							maxLength:8,
							listeners: {
					            change: function(field, value) {
					                var externalAgencyFee=this.up('form').getValues().externalAgencyFee;	
					                if(externalAgencyFee =='' ||externalAgencyFee == 'undefined' || externalAgencyFee == undefined)
					                	externalAgencyFee=0.000;
					                var total = parseFloat(externalAgencyFee)+value;
					                this.up('form').getForm().findField('costAmount').setValue(total.toFixed(3));
					            },
						        blur:function(field,eOpts){
						         	//验证费用信息
						            partialline.uninputedpartial.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '代理送货费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '代理送货费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '代理送货费必须是数字';
									}
								}
							}		
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.costAmount'),// '外发成本总额',
							name: 'costAmount',
							allowBlank:false,
							value:0,
							maxLength:18,
							readOnly:true,
							columnWidth:.33
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.receiveAgencyFee'),// '实收代理费',
							name: 'receiveAgencyFee',
							allowBlank:false,
							columnWidth:.33,
							value:0,
							minValue:0,
							maxLength:8,
							listeners: {
					            change: function(field, value) {
					            	var val = parseInt(value);
					            	if(isNaN(val)){
					            		field.setValue(0);
					            	}else{
					            	/*if(val>0){
						            		this.up('form').getForm().findField('payAgencyFee').setValue(0);					            		
						            	}*/
					            	}					                
					            },
					            blur:function(field,eOpts){
					            	/*var val=field.getValue();
					            	if(!Ext.isEmpty(val)&&val>0){
					            		this.up('form').getForm().findField('payAgencyFee').setValue(0);	
					            	}*/
						         	//验证费用信息
						            partialline.uninputedpartial.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '实收代理费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '实收代理费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '实收代理费必须是数字';
									}
								}
							}				
						},{
							xtype: 'numberfield',
							hideTrigger: true,
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.payAgencyFee'),// '实付代理费',
							name: 'payAgencyFee',
							allowBlank:false,
							columnWidth:.33,
							value:0,
							minValue:0,
							maxLength:8,
							listeners: {
					            change: function(field, value) {
					            	var val = parseInt(value);
					            	if(isNaN(val)){
					            		field.setValue(0);
					            	}else{
					            		if(val>0){
						            		this.up('form').getForm().findField('receiveAgencyFee').setValue(0);				            		
						            	}
					            	}					            	
					            },
					            blur:function(field,eOpts){
					            	var val=field.getValue();
					            	if(!Ext.isEmpty(val)&&val>0){
					            		this.up('form').getForm().findField('receiveAgencyFee').setValue(0);
					            	}					            	
						         	//验证费用信息
						            partialline.uninputedpartial.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        },
							validator:function(val){
								if(Ext.isEmpty(val)){
									return '实付代理费不能为空'
								}else{
									if(Ext.isNumeric(val)){
										if(val<0){
											return '实付代理费不能小于0';
										}else{
											return true;
										}										
									}else{
										return '实付代理费必须是数字';
									}
								}
							}		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.isWriteOff'),// '自动核销申请',
							xtype: 'radiogroup',
					        vertical: true,
							columnWidth:.33,
							labelWidth:120,
							allowBlank:false,
							items: [
					            { boxLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.isWriteOff.yes'), name: 'isWriteOff', inputValue: 'Y', checked: true },
					            { boxLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.isWriteOff.no'), name: 'isWriteOff', inputValue: 'N'}
					        ],
					        listeners:{
					        change:function( field, newValue, oldValue, eOpts ){
						         	//验证费用信息
						            partialline.uninputedpartial.validateFeesAndWriteOff( this.up('form').getForm());
						         }
					        }
						},{
							xtype:'textarea',
							height:60,
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.notes'),// '备注',
							name: 'notes',
							allowBlank:true,
							maxLength:1000,
							columnWidth:.99		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal'),// '中转外发',
							xtype: 'radiogroup',
					        vertical: true,
							columnWidth:.33,
							labelWidth:120,
							allowBlank:false,
							readOnly:true,
							items: [
					            { boxLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.yes'), name: 'transferExternal', inputValue: 'Y' ,readOnly:true},
					            { boxLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transferExternal.no'), name: 'transferExternal', inputValue: 'N', checked: true,readOnly:true}
					        ]
						},{
							fieldLabel: '币种',// '币种',
							name: 'currencyCode',
							readOnly:true,
							columnWidth:.33
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.paidMethod'),// '付款方式',
							name: 'paidMethod',
							readOnly:true,
							columnWidth:.33
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.toPayAmount'),// '到付金额',
							name: 'toPayAmount',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.origOrgName'),//'外发代理',
							name: 'agentCompanyName',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '外发代理',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}	
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.agentDeptName'),// '到达网点',
							name: 'agentDeptName',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '到达网点',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.contactPhone'),// '到达网点电话',
							name: 'contactPhone',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '到达网点电话',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.address'),// '到达网点地址',
							name: 'address',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '到达网点地址',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}		
						},{
							fieldLabel: '外发部门',// '外发部门',
							name: 'externalOrgName',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '外发部门',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}						
						},{
							fieldLabel: '录入员',// '录入员',
							name: 'registerUser',
							readOnly:true,							
							columnWidth:.33		
						},{
							xtype: 'datefield',
							fieldLabel: '录入日期',// '录入时间',
							name: 'registerTime',
							format:'Y-m-d',
							readOnly:true,
							columnWidth:.33	
						}]
			},{				
				xtype:'fieldset',
				title: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.title.fieldset2'),//运单基本信息
				defaultType: 'textfield',
				layout: 'column',
				defaults: {
					anchor: '100%'
				},
				items: [{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.targetOrgCode'),// '目的站',
							name: 'targetOrgCode',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '目的站',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}	
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.handGoodsTime'),// '收货日期',
							name: 'handGoodsTime',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.createOrgCode'),// '收货部门',
							name: 'createOrgCode',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '收货部门',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}			
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsWeightTotal'),// '重量',
							name: 'goodsWeightTotal',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsVolumeTotal'),// '体积',
							name: 'goodsVolumeTotal',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsQtyTotal'),// '件数',
							name: 'goodsQtyTotal',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.transportFee'),// '运费',
							name: 'transportFee',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsName'),// '货物名称',
							name: 'goodsName',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.insuranceFee'),// '保险费',
							name: 'insuranceFee',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.goodsPackage'),// '包装',
							name: 'goodsPackage',
							readOnly:true,
							columnWidth:.33,
							tipConfig: {
								title: '包装',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.deliveryCustomerName'),// '托运人姓名',
							name: 'deliveryCustomerName',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.insuranceAmount'),// '保险价值',
							name: 'insuranceAmount',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.yunshushixiang'),// '运输事项',
							name: 'yunshushixiang',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '运输事项',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}					
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.totalFee'),// '开单金额',
							name: 'totalFee',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.codAmount'),// '代收货款',
							name: 'codAmount',
							readOnly:true,
							columnWidth:.33		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.receiveCustomerName'),// '收货客户',
							name: 'receiveCustomerName',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '收货客户',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}		
						},{
							fieldLabel: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.fieldLabel.partnerContactPhone'),// '代理电话',
							name: 'partnerContactPhone',
							readOnly:true,
							columnWidth:.33,							
							tipConfig: {
								title: '外发代理电话',
								height: 150,
								width: 500,
								//是否随鼠标滑动
								trackMouse: true,
								//隐藏的延迟时间,默认为500ms
								//hideDelay: 2000,
								//普通Form上必须配置tipType(区分普通Form和行展开的Form)
								tipType: 'normal',
								//提示的数据，和tpl相关联								
								tipBodyElement: 'Foss.uninputedpartial.form.tip'
							}				
						},{
							xtype:'hiddenfield',
							fieldLabel: '',// '',
							name: 'paidMethodCode',
							readOnly:true,
							columnWidth:.33		
						}]
			},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			id:'Foss_uninputedpartial_form_Viewpartialline_button_reset_ID',
			text: partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.button.reset'),// '重置',
			handler: function() {
				var form = this.up('form').getForm();
				form.findField('externalBillNo').reset();
				form.findField('externalUserCode').reset();
				form.findField('externalAgencyFee').reset();
				form.findField('deliveryFee').reset();
				form.findField('costAmount').reset();
				form.findField('receiveAgencyFee').reset();
				form.findField('payAgencyFee').reset();
				form.findField('isWriteOff').reset();
				form.findField('notes').reset();
				form.findField('transferExternal').reset();
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			id:'Foss_partialline_form_Viewpartialline_button_save_ID',
			text:  partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.button.save'),//'保存',			
			handler: function() {
				var formEl=this.up('form');
				var form = formEl.getForm();
				
				if (form.isValid()) { 
	        		var new_record = form.getValues();
	        		//form.updateRecord(new_record);
	        		//验证外发成本总额不能为0或负数
	        		//外发成本总额
	        		var costAmount=Ext.Number.from(new_record.costAmount,0);
	        		//310248 允许为0
	        		if(costAmount<0){
	        			Ext.MessageBox.alert(partialline.uninputedpartial.i18n('foss.partialline.messageBox.alert.tip.title'),
	        			partialline.uninputedpartial.i18n('foss.partialline.messageBox.alert.tip.error.costAmountLtZero'));
	        			return ;
	        		}
	        		//外发成本总额比开单金额大时出现提示信息，当外发成本总额比开单金额大1倍时不能录入，默认为1倍，可配置	        		
	        		/*if(costAmount>new_record.totalFee*2){
	        			Ext.MessageBox.alert(partialline.uninputedpartial.i18n('foss.partialline.messageBox.alert.tip.title'),"外发成本总额超过开单金额的2倍");
	        			return ;
	        		}*/
	        		//业务规则SR-10校验
	        		/*var values=form.getValues();
	        		//到付方式
	        		var paidMethodCode=values.paidMethodCode;
	        		//到付运费(到付金额)
	        		var toPayAmount=values.toPayAmount;
	        		//实付代理金额(实付代理费)
	        		var payAgencyFee=new_record.payAgencyFee;        		
	        		//当运单付款方式为到付时(FC), 外发成本总额 - 到付运费=X,如果X>0,录入实付代理金额>X时，则不允许录入;	        		
	        		if(paidMethodCode == 'FC'){
	        			var x=costAmount-toPayAmount;
	        			if(x>0 && payAgencyFee>x){
	        				Ext.MessageBox.alert(partialline.uninputedpartial.i18n('foss.partialline.messageBox.alert.tip.title'),'给予代理的费用>外发成本总额，产生亏本，请核实后再录入');
	        				return ;
	        			}
	        			//如果X<0,录入实收代理金额<|X|（绝对值）时，则不允许录入，以防止多付给代理钱的情况；
	        			if(x<0){
	        				//实收代理费（实收代理金额）
	        				var receiveAgencyFee=new_record.receiveAgencyFee;    
	        				var xval=Math.abs(x);
	        				if(receiveAgencyFee<xval){
	        					Ext.MessageBox.alert(partialline.uninputedpartial.i18n('foss.partialline.messageBox.alert.tip.title'),'录入实收代理费<到付费用减去外发成本总额，产生亏本，请核实后再录入');
	        					return ;
	        				}	        				
	        			}
	        		}else 
	        		//当运单付款方式为现金时，录入实付代理金额>外发成本总额时，实付代理金额则不允许录入；
	        		if(paidMethodCode == 'CH'){//CH现金
	        			if(payAgencyFee>costAmount){
	        				Ext.MessageBox.alert(partialline.uninputedpartial.i18n('foss.partialline.messageBox.alert.tip.title'),'录入实付代理金额>外发成本总额，产生亏本，请核实后再录入');
	        				return ;
	        			}
	        		}*/
	        		
	        		//验证费用信息
	        		if(!partialline.uninputedpartial.validateFeesAndWriteOff(form)){
	        			return;
	        		}
	        		//清空不需要保存的数据
	        		new_record.registerTime=null;
	        		new_record.registerUser=null;
	        		new_record.externalOrgName=null;
	        		var array = {vo:{dto:new_record}};
	        		formEl.getEl().mask("正在保存，请稍等...");
	        		Ext.Ajax.request({
	        			url : partialline.realPath('addExternalBill.action'),
	        			jsonData:array,
	        			success : function(response) {
	        				formEl.getEl().unmask();
	        				form.findField('waybillNo').setReadOnly(true);
	        				//信息提示
	        			   Ext.ux.Toast.msg('提示信息', '保存成功!');
	        			   Ext.getCmp('Foss_partialline_form_Viewpartialline_button_save_ID').disable(true);
	        			   Ext.getCmp('Foss_uninputedpartial_form_Viewpartialline_button_reset_ID').disable(true);
	        			   //删除已录入的记录
	        			   var vals = new_record;
		                  	if(!Ext.isEmpty(vals)&&!Ext.isEmpty(vals.waybillNo)){
		                  		if(!Ext.isEmpty(partialline.uninputedpartial.searchGrid)){
		                  			var store = partialline.uninputedpartial.searchGrid.getStore();
		                  			if(!Ext.isEmpty(store)){
		                  				var record;
		                  				for(var i=0;i< store.getCount();i++){
		                  					record= store.getAt(i);
		                  					if(record.data.waybillNo==vals.waybillNo){
		                  						store.remove(record);
		                  						break;
		                  					}
		                  				}
		                  			}
		                  		}
		                  	}
	        			},
	        			exception : function(response) {
	        				formEl.getEl().unmask();
	        				var json = Ext.decode(response.responseText);
	        				Ext.MessageBox.alert(partialline.uninputedpartial.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
	        			}
	        		});
				}else{
					Ext.MessageBox.alert("验证未通过",'请检查表单错误，请见表单对应错误提示');
				}
			}
		}]
	}]
});


//验证费用
partialline.uninputedpartial.validateFeesAndWriteOff=function(form){
	//可填写
	form.findField('payAgencyFee').setReadOnly(false);
	//可填写
	form.findField('receiveAgencyFee').setReadOnly(false);
	var flag=true;
	//验证费用
	if(!Ext.isEmpty(form)){
		var values=form.getValues();
		//到付方式
		var paidMethodCode=values.paidMethodCode;
		//到付运费(到付金额)
		var toPayAmount=Ext.Number.from(values.toPayAmount,0);
		//外发成本总额
		var costAmount=Ext.Number.from(form.findField('costAmount').getValue(), 0) ;
		//实付代理金额(实付代理费)
		var payAgencyFee=Ext.Number.from(form.findField('payAgencyFee').getValue(), 0);  
		//实收代理金额(实收代理费)
		var receiveAgencyFee=Ext.Number.from(form.findField('receiveAgencyFee').getValue(), 0);  
		//自动核销
		var isWriteOff=values.isWriteOff;
		//1.当运单到付金额＞0时，运单到付金额-外发成本总额＞0时，是否自动核销为否时，
		//0≤实收代理金额≤运单到付金额，0≤实付代理金额≤外发成本总额；
		//是否自动核销为是时，0≤实收代理金额≤（运单到付金额-外发成本总额），
		//实付代理金额为灰色，不可编辑，只能为0
		if(toPayAmount>0){
			//运单到付金额-外发成本总额
			var x=toPayAmount-costAmount;
			//运单到付金额-外发成本总额＞0时
			if(x>0){
				//非自动核销
				if(isWriteOff=='N'){					
					//验证实付代理费
					if(payAgencyFee>=0&&payAgencyFee<=costAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额>外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]");
						flag=false;
					}
					//验证实收代理费
					if(receiveAgencyFee>=0&&receiveAgencyFee<=toPayAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额>外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]");
						flag=false;
					}
					
				}
				//自动核销
				else{
					//实付代理金额为灰色，不可编辑，只能为0
					form.findField('payAgencyFee').setValue(0);
					form.findField('payAgencyFee').setReadOnly(true);
					//是否自动核销为是时，0≤实收代理金额≤（运单到付金额-外发成本总额）
					if(receiveAgencyFee>=0&&receiveAgencyFee<=x){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额>外发成本总额,且自动核销时，需要[0≤实收代理费≤（到付金额-外发成本总额）]");
						flag=false;
					}
				}
			}
			//2.当运单到付金额＞0时，运单到付金额-外发成本总额≤0时（此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置），
			//是否自动核销为否时，0≤实收代理金额≤运单到付金额，0≤实付代理金额≤外发成本总额；是否自动核销为是时，实收代理金额为灰色，
			//不可编辑，只能为0，0≤实付代理金额≤（外发成本总额-运单到付金额）
			//运单到付金额-外发成本总额≤0时（此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置）
			else{
				//非自动核销
				if(isWriteOff=='N'){					
					//验证实付代理费,此时亏本走货，外发成本总额在容忍亏本的范围内，通过参数配置
					if(payAgencyFee>=0&&payAgencyFee<=costAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额<=外发成本总额,且非自动核销时，需要[0≤实付代理费≤外发成本总额]");
						flag=false;
					}
					//验证实收代理费
					if(receiveAgencyFee>=0&&receiveAgencyFee<=toPayAmount){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额<=外发成本总额,且非自动核销时，需要[0≤实收代理费≤到付金额]");
						flag=false;
					}
					
				}
				//自动核销
				else{
					//是否自动核销为是时，实收代理金额为灰色，不可编辑，只能为0
					form.findField('receiveAgencyFee').setValue(0);
					form.findField('receiveAgencyFee').setReadOnly(true);
					//是否自动核销为是时，0≤实付代理金额≤（外发成本总额-运单到付金额）
					var y=costAmount-toPayAmount;
					if(payAgencyFee>=0&&payAgencyFee<=y){
						//验证通过
					}else{
						Ext.MessageBox.alert('提示',"到付金额<=外发成本总额,且自动核销时，需要[0≤实付代理金额≤（外发成本费-运单到付金额）]");
						flag=false;
					}
				}
			}
		}
		//当运单到付金额=0时（此时，发货人会和我司进行运费结算，外发部门无需委托代理收款，我司只需给代理支付成本），
		//实收代理金额为灰色，不可编辑，只能为0，0≤实付代理金额≤外发成本总额。 
		else if(toPayAmount==0){
			//实收代理金额为灰色，不可编辑，只能为0
			form.findField('receiveAgencyFee').setValue(0);
			form.findField('receiveAgencyFee').setReadOnly(true);
			//0≤实付代理金额≤外发成本总额。 
			if(payAgencyFee>=0&&payAgencyFee<=costAmount){
				//验证通过
			}else{
				Ext.MessageBox.alert('提示',"当运单到付金额=0时，需要[0≤实付代理费≤外发成本总额]");
				flag=false;
			}
		}
		
	}
	return flag; 
}

//查询外发部门
partialline.uninputedpartial.queryExternalOrgName=function(form,externalUserCode){
	var params = {"vo.dto.externalUserCode":externalUserCode};
	var actionUrl =partialline.realPath('queryExternalOrgName.action');
	//执行相应操作
	Ext.Ajax.request({
		url : actionUrl,
		params:params,
		success : function(response) {
			var json = Ext.decode(response.responseText);
	        	var dto=json.vo.dto;
	        	form.getForm().findField('externalOrgName').setValue(dto.externalOrgName)
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.i18n('Foss.partialline.form.Viewpartialline.error'),json.message);
		}
	});
}
//外发录入窗口
Ext.define('Foss.uninputedpartial.window.Viewpartialline',{
	extend:'Ext.window.Window',
	title: partialline.uninputedpartial.i18n('Foss.partialline.window.Viewpartialline.write.title'),//'录入偏线外发单',
	modal:true,
	closeAction:'hide',
	width: 800,	
	bodyCls: 'autoHeight',
	layout: 'auto',	
	items:[
		Ext.getCmp('Foss_uninputedpartial_form_viewpartialline_ID')==null?Ext.create('Foss.uninputedpartial.form.Viewpartialline'):Ext.getCmp('Foss_uninputedpartial_form_viewpartialline_ID')
	],
	listeners: {
                  hide: function( component, eOpts ){                     
                      //partialline.uninputedpartial.pagingBar.moveFirst();
                  	
                  }
              }
	
});

partialline.uninputedpartial.queryHandedUninputWaybill=function(waybillNo){	
	var params = {"vo.dto.waybillNo":waybillNo};
	var actionUrl =partialline.realPath('queryHandedUninputWaybill.action');
	//执行相应操作
	Ext.Ajax.request({
		url : actionUrl,
		params:params,
		success : function(response) {
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert('提示',json.message);
		}
	});
}

//模块入口函数
Ext.onReady(function() {
	//1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
	//	用法：模块名.自定义变量
	//2.对象都是用Ext.define定义的方式声明
	
	//查询表单
	var queryForm = Ext.create('Foss.uninputedpartial.form.HandoverBillDetailSearch');
	partialline.uninputedpartial.queryForm=queryForm;
	
	//查询结果
	var searchGrid = Ext.create('Foss.partialline.grid.UninputGrid');
	partialline.uninputedpartial.searchGrid=searchGrid;
	
	//显示偏线界面
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-uninputedpartialIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [queryForm,searchGrid],
		renderTo: 'T_partialline-uninputedpartialIndex-body'
	});
	partialline.uninputedpartial.pagingBar.moveFirst();
});