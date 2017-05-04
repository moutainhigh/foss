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
agency.airPayableAdd.billAirPayableAddConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * 新增空运其他应付单信息
 * 
 * @returns {Boolean}
 */
agency.airPayableAdd.addBillPayable = function(form,me){
	var sourceBillNo = form.findField('sourceBillNo').getValue();
	var waybillNo = form.findField('waybillNo').getValue();
	var customerDetialCode = form.findField('customerDetial').getValue();
	var customerDetialName = form.findField('customerDetial').getRawValue();
	var agencyDetialCode = form.findField('agencyDetial').getValue();
	var agencyDetialName = form.findField('agencyDetial').getRawValue();
	var productName = form.findField('productName').getValue();
	var productCode = form.findField('productCode').getValue();
	var amount = form.findField('amount').getValue();
	var notes = form.findField('notes').getValue();
	
		
	if((sourceBillNo == "" || sourceBillNo == null)
			&& (waybillNo == "" || waybillNo == null)){
		Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.sbnAndwbnNotNull'));
		return false;
	}
	
	if((sourceBillNo != "" && sourceBillNo != null)
			&& (waybillNo != "" && waybillNo != null)){
		Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.sbnAndwbnExist'));
		form.findField('sourceBillNo').setValue(sourceBillNo);
		form.findField('waybillNo').setValue(null);
		form.findField('agencyDetial').setValue("");
		form.findField('agencyDetial').hide();
		form.findField('customerDetial').show();
		return false;
	}
	
	var customerCode = "";
	if(customerDetialCode != "" && customerDetialCode != null){
		customerCode = customerDetialCode;
	}else if(agencyDetialCode != "" && agencyDetialCode != null){
		customerCode = agencyDetialCode;
	}else{
		Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.customerNameNotNull'));
		return false;
	}
	
	var customerName = "";
	if(customerDetialName != "" && customerDetialName != null){
		customerName = customerDetialName;
	}else if(agencyDetialName != "" && agencyDetialName != null){
		customerName = agencyDetialName;
	}else{
		Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.customerNameNotNull'));
		return false;
	}
	
	if(form.isValid()){
		var params = {
				'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo':sourceBillNo,
				'billPayableAgencyVo.billPayableAgencyDto.waybillNo':waybillNo,
				'billPayableAgencyVo.billPayableAgencyDto.customerCode':customerCode,
				'billPayableAgencyVo.billPayableAgencyDto.customerName':customerName,
				'billPayableAgencyVo.billPayableAgencyDto.amount':amount,
				'billPayableAgencyVo.billPayableAgencyDto.notes':notes,
				'billPayableAgencyVo.billPayableAgencyDto.productCode':productCode,
				'billPayableAgencyVo.billPayableAgencyDto.productName':productName
		}
		var yesFn=function(){
			// 设置该按钮灰掉
			me.disable(false);
			// 30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			Ext.Ajax.request({
				url : agency.realPath('addBillPayable.action'),
				params:params,
				success:function(response){
					var result = Ext.decode(response.responseText);
					form.reset();
					Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),
							agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.saveSuccess'));
					me.enable(true);
					form.reset();
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),result.message);
					me.enable(true);
					form.reset();
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
					me.enable(true);
					form.reset();
				}
			});
		};
		var noFn=function(){
		 	return false;
		};
		agency.airPayableAdd.billAirPayableAddConfirmAlert(agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.isSave'),yesFn,noFn);
	}else{
		Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

Ext.define('Foss.StlairPayable.StlairPayableAddInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 380,
	items : [ {
		title: '<span class="statementBill_tabHead">&nbsp;&nbsp;'+agency.airPayableAdd.i18n('foss.stl.agency.payable.payableAdd')+'&nbsp;&nbsp;</span>',
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
				fieldLabel:agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.sourceBillNo'),
				name:'sourceBillNo',
				columnWidth:.34,
				listeners:{
					'change':function(th,newValue,oldValue){
						if(!Ext.isEmpty(Ext.String.trim(newValue))){
							var cusdetail=this.up('form').getForm().findField('customerDetial');	
							var agency=this.up('form').getForm().findField('agencyDetial');		
							var waybillNo  = this.up('form').getForm().findField('waybillNo');
							waybillNo.setValue(null);
							agency.setValue(null);
							agency.hide();	
							cusdetail.show();
						}
					},
					'blur':function(){
	        			var me=this;
	        			var form=this.up('form').getForm();
	        			var customerDetial=form.findField('customerDetial');
	        			var agencyDetial=this.up('form').getForm().findField('agencyDetial');	
	        			var waybillNo  = this.up('form').getForm().findField('waybillNo');
	        			var productCode = this.up('form').getForm().findField('productCode');
	        			var productName = this.up('form').getForm().findField('productName');
	        			var srcNo=form.findField('sourceBillNo').getValue();
	        			if(Ext.isEmpty(srcNo))
	        			{	        		
	        				if(Ext.isEmpty(waybillNo.getValue())){
	        					customerDetial.setValue(null);
	        				}
	        			}else{	        				
	        				var params = {
	        				'billPayableAgencyVo.billPayableAgencyDto.sourceBillNo':srcNo      					
	        				};
	        				Ext.Ajax.request({
	        					url :agency.realPath('queryAirPayablebleCusName.action'),
	        					params:params,
	        					success:function(response){	  
	        						var result=Ext.decode(response.responseText);
	        						if(!Ext.isEmpty(result)){
	        							// 如果是外发单据，则显示出发代理名称和编码，否则显示航空公司信息
	        							if(result.billPayableAgencyVo.airWaybillEntity.airAssembleType=='DDWFD'||
	        									result.billPayableAgencyVo.airWaybillEntity.airAssembleType=='HDPWF')
	        								{		  								
	        									agencyDetial.show();	
	        									customerDetial.hide();
		        								var displayText = result.billPayableAgencyVo.airWaybillEntity.agencyName;
			        							var valueText = result.billPayableAgencyVo.airWaybillEntity.agenctCode;
			        							if(!Ext.isEmpty(valueText)){
			        								var store = agencyDetial.store;
				        							var  key = agencyDetial.displayField + '';
				        							var value = agencyDetial.valueField+ '';
				        							var m = Ext.create(store.model.modelName);
				        							m.set(key, displayText);
				        							m.set(value, valueText);
				        							store.loadRecords([m]);
				        							agencyDetial.setValue(valueText);
				        							productCode.setValue(result.billPayableAgencyVo.airWaybillEntity.productCode); 
				        							productName.setValue(result.billPayableAgencyVo.airWaybillEntity.productName); 
			        							}else{
			        								agencyDetial.setValue(null);		        		        				
			        							}		        										       							
	        								}else{
	        									agencyDetial.hide();	
	        									customerDetial.show();
		        								var displayText = result.billPayableAgencyVo.airWaybillEntity.agencyName;
			        							var valueText = result.billPayableAgencyVo.airWaybillEntity.airLineTwoletter;
			        							if(!Ext.isEmpty(valueText)){
			        								var store = customerDetial.store;
				        							var  key = customerDetial.displayField + '';
				        							var value = customerDetial.valueField+ '';
				        							var m = Ext.create(store.model.modelName);
				        							m.set(key, displayText);
				        							m.set(value, valueText);
				        							store.loadRecords([m]);
				        							customerDetial.setValue(valueText);	
				        							productCode.setValue(result.billPayableAgencyVo.airWaybillEntity.productCode); 
				        							productName.setValue(result.billPayableAgencyVo.airWaybillEntity.productName);
			        							}else{
			        								customerDetial.setValue(null);		        		        				
			        							}
	        							}	     							
	        						}else{
	        							customerDetial.setValue(null);
	        	        				
	        						}
	        					},
	        					exception:function(response){
	        						var result = Ext.decode(response.responseText);	
	        						customerDetial.setValue(null);
	        						agencyDetial.setValue(null);
	        						Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),result.message);
	        						me.enable(true);
	        					},
	        					failure:function(response){
	        						var result = Ext.decode(response.responseText);
	        						me.enable(true);
	        					}
	        				});      				
	        			}						
					}
				}				
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.02,
				html: '&nbsp;'
		    },{	
		    	xtype:'textfield',
				fieldLabel:agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.waybillNo'),
				name:'waybillNo',
				columnWidth:.34,
				listeners:{
					'change':function(th,newValue,oldValue){
						if(!Ext.isEmpty(Ext.String.trim(newValue))){
							var cusdetail=this.up('form').getForm().findField('customerDetial');
							var agency=this.up('form').getForm().findField('agencyDetial');			
							var sourceBillNo  = this.up('form').getForm().findField('sourceBillNo');
							sourceBillNo.setValue(null);
							cusdetail.setValue(null);
							cusdetail.hide();
							agency.show();		
						}
					},
					'blur':function(){
	        			var me=this;
	        			var form=this.up('form').getForm();
	        			var agencyDetial=form.findField('agencyDetial');// 代理
	        			var waybillNo=form.findField('waybillNo').getValue();
	        			var sourceBillNo  = this.up('form').getForm().findField('sourceBillNo');
	        			var productCode = this.up('form').getForm().findField('productCode');
	        			var productName = this.up('form').getForm().findField('productName');
	        			if(Ext.isEmpty(waybillNo))
	        			{
	        				if(Ext.isEmpty(sourceBillNo.getValue())){
	        					agencyDetial.setValue(null);
	    						agencyDetial.reset();
	        				}
	        			}else{	        				
	        				var params = {
	        				'billPayableAgencyVo.billPayableAgencyDto.waybillNo':waybillNo        						
	        					}	
	        					Ext.Ajax.request({
	        					url :agency.realPath('queryPayAiragencyCusName.action'),
	        					params:params,
	        					success:function(response){	  
	        						var result=Ext.decode(response.responseText);
	        						if(!Ext.isEmpty(result))
		        					{
	        							var displayText = result.billPayableAgencyVo.airPickupbillEntity.destOrgName;
	        							var valueText = result.billPayableAgencyVo.airPickupbillEntity.destOrgCode;
	        							if(!Ext.isEmpty(displayText)&&!Ext.isEmpty(valueText)){
	        								var store = agencyDetial.store;
		        							var  key = agencyDetial.displayField + '';
		        							var value = agencyDetial.valueField+ '';
		        							var m = Ext.create(store.model.modelName);
		        							m.set(key, displayText);
		        							m.set(value, valueText);
		        							store.loadRecords([m]);
		        							agencyDetial.setValue(valueText);
		        						    productCode.setValue(null);
	        			                    productName.setValue(null);
	        							}else{
	        								agencyDetial.setValue(null);
	        	    						agencyDetial.reset();
	        	    						productCode.setValue(null);
	        			                    productName.setValue(null);
	        							}
		        					}else{
		        						agencyDetial.setValue(null);
		        						agencyDetial.reset();
		        					    productCode.setValue(null);
	        			                productName.setValue(null);
		        					}
	        					},
	        					exception:function(response){
	        						var result = Ext.decode(response.responseText);	
	        						agencyDetial.setValue(null);
	        						Ext.Msg.alert(agency.airPayableAdd.i18n('foss.stl.agency.common.alert'),result.message);
	        						me.enable(true);
	        					},
	        					failure:function(response){
	        						var result = Ext.decode(response.responseText);
	        						me.enable(true);
	        					}
	        				});        				
	        		     }
					}
				}
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.03,
				html: '&nbsp;'
		    }, /*
				 * { xtype : 'radiogroup', defaultType: 'radiofield',
				 * name:'radioAir', columnWidth:1, labelSeparator : "",
				 * labelWidth:90, fieldLabel:'&nbsp;', layout: 'hbox', items: [{
				 * width:100, boxLabel :
				 * agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.customerDetial'),
				 * name : 'color', checked : true, inputValue: '11',
				 * handler:function(val){ if(val.value == true){ var form
				 * =this.up('form').getForm();
				 * form.findField('customerDetial').show();
				 * form.findField('customerDetial').labelEl.update('<span
				 * style="color:red;">*</span>'
				 * +agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.customerDetial')+':');
				 * form.findField('agencyDetial').hide();
				 * form.findField('agencyDetial').labelEl.update('&nbsp;&nbsp;'
				 * +agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.agencyDetial')+':');
				 * form.findField('agencyDetial').setValue(""); } } }, {
				 * width:100, boxLabel :
				 * agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.agencyDetial'),
				 * name : 'color', inputValue: '22', handler:function(value){
				 * if(value.value == true){ var form =this.up('form').getForm();
				 * form.findField('agencyDetial').show();
				 * form.findField('agencyDetial').labelEl.update('<span
				 * style="color:red;">*</span>'
				 * +agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.agencyDetial')+':');
				 * form.findField('customerDetial').hide();
				 * form.findField('customerDetial').labelEl.update('&nbsp;&nbsp;'
				 * +agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.customerDetial')+':');
				 * form.findField('customerDetial').setValue(""); } } } ] },
				 */
		    {	
	        	xtype:'commonairlinesselector',
				fieldLabel:'<span style="color:red;">*</span>'+agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.customerDetial'),
				name:'customerDetial',
				columnWidth:.34,
				listWidth:300,// 设置下拉框宽度
				isPaging:true // 分页
		    },{	
		    	xtype:'commonairagentselector',
		    	fieldLabel:'<span style="color:red;">*</span>'+agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.agencyDetial'),
				name:'agencyDetial',
				hidden:true,
				columnWidth:.34,
				listWidth:300,// 设置下拉框宽度
				isPaging:true // 分页
		    },{
		    	xtype: 'container',
				border : false,
				columnWidth:.02,
				html: '&nbsp;'
		    },{
		    	xtype:'numberfield',
		    	fieldLabel:agency.airPayableAdd.i18n('foss.stl.agency.common.amount'),
		    	name:'amount',
		    	decimalPrecision : 2,
				columnWidth:.34,
				allowBlank:false,
				html: '&nbsp;'
	    },
//	    	{
//		    	xtype: 'container',
//				border : false,
//				columnWidth:.5,
//				html: '&nbsp;'
//		    },
				{
		    	xtype:'textfield',
		    	fieldLabel:agency.airPayableAdd.i18n('foss.stl.agency.common.transportType'),
		    	name:'productCode',
		    	hidden:'true',
				columnWidth:.34
				//allowBlank:false    			
		    },{
		    	xtype:'textfield',
		    	fieldLabel:agency.airPayableAdd.i18n('foss.stl.agency.common.transportType'),
		    	name:'productName',
		    	readOnly:true,
				columnWidth:.34
				//readOnly:true,
			    //allowBlank:false    			
		    },{
		    	xtype: 'textarea',
		    	fieldLabel:agency.airPayableAdd.i18n('foss.stl.agency.airPayAndRec.notes'),
		    	allowBlank:false,
		    	name:'notes',
		    	columnWidth:.7
		    },    
		    {	
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[
				       {
				    	 text:'重置',
				    	 columnWidth:.08,
				    	 handler:function(){
				    	 var me = this;
				    	 var form  = this.up('form').getForm();					  				    	
				        form.reset();
				    	   }	   
				       },{
					xtype: 'container',
					border : false,
					columnWidth:.54,
					html: '&nbsp;'
				},{
					text:agency.airPayableAdd.i18n('foss.stl.agency.common.save'),
					disabled:!agency.airPayableAdd.isPermission('/stl-web/agency/addBillPayable.action'),
					hidden:!agency.airPayableAdd.isPermission('/stl-web/agency/addBillPayable.action'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
						var form  = this.up('form').getForm();	
    					var me = this;
    					agency.airPayableAdd.addBillPayable(form,me);
    				}
				}]
		    }]
        }]
	}]
});

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlairPayableQueryInfoTab = Ext.create('Foss.StlairPayable.StlairPayableAddInfoTab');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-airPayableAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StlairPayableQueryInfoTab],
		renderTo: 'T_agency-airPayableAdd-body'
	});
});