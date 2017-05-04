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
agency.airReceivableAdd.billAirReceivableAddConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * 新增空运其他应收单信息
 * 
 * @returns {Boolean}
 */
agency.airReceivableAdd.addBillReceivable = function(form,me){
	var sourceBillNo = form.findField('sourceBillNo').getValue();
	var waybillNo = form.findField('waybillNo').getValue();
		
	var customerDetialCode=null;
	var customerDetialName=null;
	var agencyDetialCode=null;  
	var agencyDetialName=null;
	var productCode = form.findField('productCode').getValue();
	var productName = form.findField('productName').getValue();
	var customerCodeandName=null;
	
	customerDetialCode = form.findField('customerDetial').getValue();
    customerDetialName = form.findField('customerDetial').getRawValue();
	agencyDetialCode = form.findField('agencyDetial').getValue();
	agencyDetialName = form.findField('agencyDetial').getRawValue();
	customerDetialCode = form.findField('customerDetial').getValue();
	customerDetialCode = form.findField('customerDetial').getValue();
// if(waybillNo!=null&&waybillNo!="")
// {
// customerDetialCode = form.findField('customerDetial').getValue();
// customerDetialName = form.findField('customerDetial').getRawValue();
// agencyDetialCode = form.findField('agencyDetial').getValue();
// agencyDetialName = form.findField('agencyDetial').getRawValue();
// }
//	
// if(sourceBillNo!=null&&sourceBillNo!="")
// {
// //
// customerCodeandName=form.findField('customerDetial').getValue().split(',');
// // customerDetialName=customerCodeandName[0];
// // customerDetialCode=customerCodeandName[1];
// }
	
	var amount = form.findField('amount').getValue();
	var notes = form.findField('notes').getValue();
	if((sourceBillNo == "" || sourceBillNo == null)
			&& (waybillNo == "" || waybillNo == null)){
		Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.sbnAndwbnNotNull'));
		return false;
	}
	if((sourceBillNo != "" && sourceBillNo != null)
			&& (waybillNo != "" && waybillNo != null)){
		Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.sbnAndwbnExist'));
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
		Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.customerNameNotNull'));
		return false;
	}
	var customerName = "";
	if(customerDetialName != "" && customerDetialName != null){
		customerName = customerDetialName;
	}else if(agencyDetialName != "" && agencyDetialName != null){
		customerName = agencyDetialName;
	}else{
		Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.customerNameNotNull'));
		return false;
	}
	var params = {
			'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':sourceBillNo,
			'billReceivableAgencyVo.billReceivableAgencyDto.waybillNo':waybillNo,
			'billReceivableAgencyVo.billReceivableAgencyDto.customerCode':customerCode,
			'billReceivableAgencyVo.billReceivableAgencyDto.customerName':customerName,
			'billReceivableAgencyVo.billReceivableAgencyDto.amount':amount,
			'billReceivableAgencyVo.billReceivableAgencyDto.notes':notes,
			'billReceivableAgencyVo.billReceivableAgencyDto.productCode':productCode,
			'billReceivableAgencyVo.billReceivableAgencyDto.productName':productName
	}
	
	if(form.isValid()){
		var yesFn=function(){
			// 设置该按钮灰掉
			me.disable(false);
			// 30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			Ext.Ajax.request({
				url :agency.realPath('addBillReceivable.action'),
				params:params,
				success:function(response){
					form.reset();
    				Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),
    						agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.saveSuccess'));
    				me.enable(true);
    				form.reset();
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);	
					Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),result.message);
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
    	agency.airReceivableAdd.billAirReceivableAddConfirmAlert(agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.isSave'),yesFn,noFn);
	}else{
		Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),
				agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.pageCheck'));
		return false;
	}
}

Ext.define('Foss.StlairReceivable.StlairReceivableAddInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 380,
	items : [ {
		title: '<span class="statementBill_tabHead">&nbsp;&nbsp;'+agency.airReceivableAdd.i18n('foss.stl.agency.receivable.receivableAdd')+'&nbsp;&nbsp;</span>',
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
				fieldLabel:agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.sourceBillNo'),// 航空正单号
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
	        			var frmCus=form.findField('customerDetial');
	        			var waybillNo  = this.up('form').getForm().findField('waybillNo');
	        			var agencyDetial=this.up('form').getForm().findField('agencyDetial');
	        			var productCode = this.up('form').getForm().findField('productCode');
	        			var productName = this.up('form').getForm().findField('productName');
	        			var srcNo=form.findField('sourceBillNo').getValue();
	        			// 判空
	        			if(Ext.isEmpty(srcNo) )
	        			{
	        				if(Ext.isEmpty(waybillNo.getValue())){
	        					frmCus.setValue(null);
	        				}
	        			}else{	        				
	        				var params = {
	        					'billReceivableAgencyVo.billReceivableAgencyDto.sourceBillNo':srcNo        						
	        				}
	        				Ext.Ajax.request({
	        					url :agency.realPath('queryAirReceivableCusName.action'),
	        					params:params,
	        					success:function(response){	  
	        						var result=Ext.decode(response.responseText);
	        						if(!Ext.isEmpty(result)){
	        							// 如果是外发单据，则显示出发代理名称和编码,否则显示航空公司信息
	        							if(result.billReceivableAgencyVo.airWaybillEntity.airAssembleType=='DDWFD'||
	        									result.billReceivableAgencyVo.airWaybillEntity.airAssembleType=='HDPWF')
	        								{		  								
	        									agencyDetial.show();	
	        									frmCus.hide();
		        								var displayText = result.billReceivableAgencyVo.airWaybillEntity.agencyName;
			        							var valueText = result.billReceivableAgencyVo.airWaybillEntity.agenctCode;
			        							if(!Ext.isEmpty(valueText)){
			        								var store = agencyDetial.store;
				        							var  key = agencyDetial.displayField + '';
				        							var value = agencyDetial.valueField+ '';
				        							var m = Ext.create(store.model.modelName);
				        							m.set(key, displayText);
				        							m.set(value, valueText);
				        							store.loadRecords([m]);
				        							agencyDetial.setValue(valueText);
				        							productCode.setValue(result.billReceivableAgencyVo.airWaybillEntity.productCode); 
				        							productName.setValue(result.billReceivableAgencyVo.airWaybillEntity.productName);
			        							}else{
			        								agencyDetial.setValue(null);		        		        				
			        							}		        										       							
	        								}else{
	        									agencyDetial.hide();	
	        									frmCus.show();
	    	        							// 获取代理名称和编码
	    	        							var displayText = result.billReceivableAgencyVo.airWaybillEntity.agencyName;
	    	        							var valueText = result.billReceivableAgencyVo.airWaybillEntity.agenctCode;
	    	        							var store = frmCus.store;
	    	        							if(!Ext.isEmpty(displayText)&& !Ext.isEmpty(valueText)){
	    	        								var  key = frmCus.displayField + '';
	    		        							var value = frmCus.valueField+ '';
	    		        							var m = Ext.create(store.model.modelName);
	    		        							m.set(key, displayText);
	    		        							m.set(value, valueText);
	    		        							store.loadRecords([m]);
	    		        							frmCus.setValue(valueText);	
	    		        							productCode.setValue(result.billReceivableAgencyVo.airWaybillEntity.productCode);
	    		        							productName.setValue(result.billReceivableAgencyVo.airWaybillEntity.productName);
	    	        							}else{
	    	        								frmCus.setValue(null);
	    	        							}
	        								}
	        							
	        						}else{
	        							frmCus.setValue(null);
	        						}
	        					},
	        					exception:function(response){
	        						var result = Ext.decode(response.responseText);	
	        						frmCus.setValue(null);	
	        						agencyDetial.setValue(null);
	        						Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),result.message);
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
				fieldLabel:agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.waybillNo'),
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
	        			if(Ext.isEmpty(waybillNo)){
	        				if(Ext.isEmpty(sourceBillNo.getValue())){
	        					agencyDetial.setValue(null);
	        				}
	        			}
	        			else{	        				
	        				var params = {
	        						'billReceivableAgencyVo.billReceivableAgencyDto.waybillNo':waybillNo        						
	        				}
	        				Ext.Ajax.request({
	        					url :agency.realPath('queryAiragencyCusName.action'),
	        					params:params,
	        					success:function(response){	  
	        						var result=Ext.decode(response.responseText);
	        						if(!Ext.isEmpty(result)){
        							var displayText = result.billReceivableAgencyVo.airPickupbillEntity.destOrgName;
        							var valueText = result.billReceivableAgencyVo.airPickupbillEntity.destOrgCode;
        							var store = agencyDetial.store;
        							if(!Ext.isEmpty(displayText)&&!Ext.isEmpty(valueText)){
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
        								productCode.setValue(null);
	        			                productName.setValue(null);
        							}
        							
	        					}else{
	        						agencyDetial.setValue(null);
	        						productCode.setValue(null);
	        			            productName.setValue(null);
	        					}
	        					},
	        					exception:function(response){
	        						var result = Ext.decode(response.responseText);	
	        						agencyDetial.setValue(null);
	        						Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),result.message);
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
		    },{
	        	xtype:'commonairlinesselector',
				fieldLabel:'<span style="color:red;">*</span>'+agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.customerDetial'),
				name:'customerDetial',
				columnWidth:.34,
				listWidth:300,// 设置下拉框宽度
				isPaging:true
	        },{
	        	xtype:'commonairagentselector',
		    	fieldLabel:'<span style="color:red;">*</span>'+agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.agencyDetial'),
				name:'agencyDetial',
				hidden:true,
				columnWidth:.34,
				listWidth:300,// 设置下拉框宽度
				isPaging:true // 分页
	        },/*
				 * { xtype:'commonairagentselector',
				 * fieldLabel:agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.agencyDetial'),
				 * name:'agencyDetial', hidden:true, columnWidth:.34,
				 * listWidth:300,//设置下拉框宽度 isPaging:true //分页
				 * 
				 * xtype:'textfield', fieldLabel:'<span style="color:red;">*</span>'+agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.agencyDetial'),
				 * name:'agencyDetial1', hidden:true, columnWidth:.34,
				 * listeners:{ 'focus':function(){ var me=this; var
				 * form=this.up('form').getForm(); var
				 * frmCus=form.findField('agencyDetial'); var
				 * srcNo=form.findField('sourceBillNo').getValue(); if((srcNo == "" ||
				 * srcNo == null)) {
				 * Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),
				 * agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.sbnAndwbnExist')); }
				 * else{
				 * 
				 * var form=this.up('form').getForm(); var
				 * sourceBillNo=form.findField('sourceBillNo').getValue(); var
				 * params = { 'airWaybillNo':sourceBillNo } Ext.Ajax.request({
				 * url :agency.realPath('queryAirReceivableCusName.action'),
				 * params:params, success:function(response){ var
				 * result=Ext.decode(response.responseText);
				 * form.findField('agencyDetial').setValue(
				 * result.airweResult.agencyName+","+
				 * result.airweResult.agenctCode ); me.enable(true);
				 * frmCus.setDisabled(true); }, exception:function(response){
				 * var result = Ext.decode(response.responseText);
				 * Ext.Msg.alert(agency.airReceivableAdd.i18n('foss.stl.agency.common.alert'),result.message);
				 * me.enable(true); }, failure:function(response){ var result =
				 * Ext.decode(response.responseText); me.enable(true); } });
				 *  } } } }
				 */{
		    	xtype: 'container',
				border : false,
				columnWidth:.02,
				html: '&nbsp;'
		    },{
		    	xtype:'numberfield',
		    	fieldLabel:agency.airReceivableAdd.i18n('foss.stl.agency.common.amount'),
		    	name:'amount',
		    	decimalPrecision : 2,
				columnWidth:.34,
				allowBlank:false,
				html: '&nbsp;'
		    },
		    	/*{
		    	xtype: 'container',
				border : false,
				columnWidth:.5,
				html: '&nbsp;'
		    },*/
		      {
		    	xtype:'textfield',
		    	fieldLabel:agency.airReceivableAdd.i18n('foss.stl.agency.common.transportType'),
		    	name:'productName',
				columnWidth:.34,
				//allowBlank:false,
				readOnly:true,
				html: '&nbsp;'
		    },
		    	{
		    	xtype:'textfield',
		    	fieldLabel:agency.airReceivableAdd.i18n('foss.stl.agency.common.transportType'),
		    	name:'productCode',
		    	hidden:'true',
				columnWidth:.34,
				//allowBlank:false
				html: '&nbsp;'
		    },{
		    	xtype: 'textarea',
		    	fieldLabel:agency.airReceivableAdd.i18n('foss.stl.agency.airPayAndRec.notes'),
		    	name:'notes',
		    	columnWidth:.7
		    },{	
				border: 1,
				xtype:'container',
				columnWidth:1,
				defaultType:'button',
				layout:'column',
				items:[{
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
					text:agency.airReceivableAdd.i18n('foss.stl.agency.common.save'),
					disabled:!agency.airReceivableAdd.isPermission('/stl-web/agency/addBillReceivable.action'),
					hidden:!agency.airReceivableAdd.isPermission('/stl-web/agency/addBillReceivable.action'),
					cls:'yellow_button',
					columnWidth:.08,
					handler:function(){
    					var me = this;
    					var form  = this.up('form').getForm();	
    					agency.airReceivableAdd.addBillReceivable(form,me);
    				}
				}]
		    }]
        }]
	}]
});

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();

	var StlairReceivableQueryInfoTab = Ext.create('Foss.StlairReceivable.StlairReceivableAddInfoTab');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_agency-airReceivableAdd_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [StlairReceivableQueryInfoTab],
		renderTo: 'T_agency-airReceivableAdd-body'
	});
});