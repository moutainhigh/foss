
querying.writeNetMsgOnline.baseinfoRealPath = ContextPath.BSE_BASEINFO+"/baseinfo/";

/**
 * 全网消息发送
 */
querying.writeNetMsgOnline.submit=function(){
	var me = this;
	var form = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().getForm();
	var SendMsgOpeMsgFor =Ext.getCmp('Foss.Messages.SendMsgOpeMsgForm_id').getForm();
	var gridRec = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgOpePanel().items.items[0].items.items[0];
	var context=SendMsgOpeMsgFor.findField('msgOnlineVo.msgOnlineDto.remarks');
	var selectionRec = gridRec.getSelectionModel().getSelection();
	//校验是否选择单号
	if(selectionRec.length==0){
		Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.noBillNo'));
		return false;
	}
	//校验是否输入通知内容
	if(Ext.isEmpty(context.getValue())){
		Ext.MessageBox.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.plaseWriteContext'));
		return;
	}
	//通知內容长度判断
	if(form.isValid()){
		if(context.getValue().length>330){
			Ext.MessageBox.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.contextToLength'));
			return;
		}
	Ext.Msg.confirm(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.isSureChoose'),function(o){
		if(o=='yes'){
			var entity = new Object();

			var jsonDataRec = new Array();
			for(var i=0;i<selectionRec.length;i++){
				jsonDataRec.push(selectionRec[i].data);
				jsonDataRec[i].context = context.getValue();
			}
			entity.msgOnlineDtos = jsonDataRec;
//			entity.context = context.getValue();
//			me.disable(false);
//			//10秒后自动解除灰掉效果
//			setTimeout(function() {
//			me.enable(true);
//			}, 10000);
			
			Ext.Ajax.request({
				url:querying.writeNetMsgOnline.baseinfoRealPath+'sendMsgSend.action',
				//url:querying.realPath('sendMsgSend.action'),
				jsonData:{'msgOnlineVo':entity},
				method:'post',
				success : function(response) { 
					var json = Ext.decode(response.responseText); 
					Ext.MessageBox.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),json.message); 
					form.reset();
					Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgOpePanel().items.items[0].items.items[0].getSelectionModel().deselect(selectionRec);
					SendMsgOpeMsgFor.reset();
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),json.message);
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),json.message);
					}
				});
			}
		});
	}
}
     
querying.writeNetMsgOnline.init=function(queryForm){
	 queryForm.getForm().findField('msgOnlineVo.msgOnlineDto.queryHandOverType').setRawValue(true);//查询FORM
}
//航空单类型：model
Ext.define('Foss.Messages.AirBillTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'airBillTypeCode'
	},{
		name:'airBillTypeName'
	}]
	
});
//航空单类型：store
Ext.define('Foss.Messages.AirBillTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.Messages.AirBillTypeModel',
	data:{
		'items':[
			{airBillTypeCode:'11',airBillTypeName:'CZ'},
			{airBillTypeCode:'22',airBillTypeName:'CA'},
			{airBillTypeCode:'33',airBillTypeName:'MU'},
			{airBillTypeCode:'44',airBillTypeName:'ZH'},
			{airBillTypeCode:'55',airBillTypeName:'MU'},
			{airBillTypeCode:'66',airBillTypeName:'SC'},
			{airBillTypeCode:'77',airBillTypeName:'MF'},
			{airBillTypeCode:'88',airBillTypeName:'FM'},
			{airBillTypeCode:'99',airBillTypeName:'NS'},
			{airBillTypeCode:'00',airBillTypeName:'HB'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});





//全网消息发送表单查询FORM
Ext.define('Foss.querying.writeNetMsgOnline.SendMsgQueryForm',{
	extend:'Ext.form.Panel',
	id:'Foss.Messages.SendMsgQueryForm_id',
//	title:querying.writeNetMsgOnline.i18n('foss.querying.sendAllNetMsg.allNetMsgSend'),
	frame:false,
	//collapsible:true,
	//animcollapse:true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth :65,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'column',
		columns : 8
	},
	items:[{
		        xtype : 'textfield',
		        columnWidth :.2,
		        labelWidth :50,
				name: 'msgOnlineVo.msgOnlineDto.billNo',
			    fieldLabel:querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.billNo'),
			    listeners:{
			    	change:function(field,new_v,old_v){
			    		//先去两端的空格
						var new_value = new_v.trim();
						if(!Ext.isEmpty(new_v)&& new_value.length >=14){
							//再把值设置给文本框
							field.setValue(new_value.substring(0,new_value.length>=14?14:new_value.length));
						}
					},
					'blur':function(th){
						if(th.getValue()!=null&&th.getValue()!=''){
							// 313353 快递在线通知需求优化  start
							var billNo = th.getValue().trim();
							var form =Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().form;
							var sendMsgOpeMsgForm =Ext.getCmp('Foss.Messages.SendMsgOpeMsgForm_id');
							if((billNo[0] == '0'
								|| billNo[0] == '5'
								|| billNo[0] == '6'
								|| billNo[0] == '7'
								|| billNo[0] == '8'
								|| billNo[0] == '9') && (billNo.length == 10||billNo.length == 14)){
								Ext.Ajax.request({
									url:querying.realPath('queryOrgByBillNo.action'),//ContextPath.QUERYING_WEB+'queryOrgByBillNo.action', 
									jsonData:{'msgOnlineVo':{'msgOnlineDto':{'billNo':th.getValue()}}},
									method:'post',
									success:function(response){
										var  result = Ext.decode(response.responseText);	
										
										form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setCombValue(result.msgOnlineVo.msgOnlineDto.receiveOrgName,result.msgOnlineVo.msgOnlineDto.receiveOrgCode);
									}
								});
								return;
							}
							// 313353 快递在线通知需求优化  end
							//调用后台接口根据输入单号带出网点编码
							Ext.Ajax.request({
								url:querying.realPath('queryOrgByBillNo.action'),//ContextPath.QUERYING_WEB+'queryOrgByBillNo.action', 
								jsonData:{'msgOnlineVo':{'msgOnlineDto':{'billNo':th.getValue()}}},
								method:'post',
								success:function(response){
									var  result = Ext.decode(response.responseText);	
									
									form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setCombValue(result.msgOnlineVo.msgOnlineDto.receiveOrgName,result.msgOnlineVo.msgOnlineDto.receiveOrgCode);
								},
								exception:function(response){
									var result = Ext.decode(response.responseText);	
									form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setValue(null);
									form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setRawValue(null);
									form.findField('msgOnlineVo.msgOnlineDto.billNo').setValue(null);
									Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),result.message);
								}		
							});
//							
							
						}
					}
				}
			},{
		        xtype : 'dynamicorgcombselector',
		        fieldLabel : querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.receiveOrgCode'),
				name : 'msgOnlineVo.msgOnlineDto.receiveOrgCode',
				columnWidth :.2,
				listeners:{
					change:function(_this,newValue,oldValue, eOpts){
						var form =Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().form;
						form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setValue(newValue);
					}
				}
		  	},{

	 		  	xtype:'button',
	 		  	text:querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.addMsgGrid'),
	 		  	columnWidth:.1,
	 		  	cls:'yellow_button',  
	 		  	handler:function(){
	 		  		//获取form
	 		  		var form = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().form;
	 		  		var grid = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgOpePanel().items.items[0].items.items[0];
	 		  		
	 		  		//获取单据查询类型
	 		  		var billNo = form.findField('msgOnlineVo.msgOnlineDto.billNo').getValue();
	 		  		var receiveOrgCode = form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').getValue();
	 		  		var receiveOrgName = form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').getRawValue();
	 		  		if(Ext.isEmpty(billNo)){
	 		  			Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.choseOneBillNo'));
	 		  		}else{
		 		  	//验证部门是否存在
		 		  		Ext.Ajax.request({
							url:querying.realPath('queryOrgByCode.action'),//ContextPath.QUERYING_WEB+'queryOrgByBillNo.action', 
							jsonData:{'msgOnlineVo':{'msgOnlineEntity':{'receiveOrgCode':receiveOrgCode}}},
							method:'post',
							//如果部门存在 这加入发送列表
							success:function(response){
								if(!Ext.isEmpty(billNo)&&!Ext.isEmpty(receiveOrgCode)&&!Ext.isEmpty(receiveOrgName)){
						 		  	var msgOnLineStore = grid.getStore();
						 		  	var msgOnlineDto = new Object();
						 		  	var msgOnLineEntitys = msgOnLineStore.data.items;
						 		  	for(var i=0;i<msgOnLineEntitys.length;i++){
										gridBillNo = msgOnLineEntitys[i].data.billNo;
										if(gridBillNo==billNo){
											msgOnLineStore.remove(msgOnLineEntitys[i]);
										}
									}
						 		  	msgOnlineDto.billNo=billNo;
									msgOnlineDto.billSendOrgName=receiveOrgName;
									msgOnlineDto.billSendOrgCode=receiveOrgCode;
									var record = new Foss.Messages.SendMsgOpeDataModel(msgOnlineDto);
									form.findField('msgOnlineVo.msgOnlineDto.billNo').setValue(null);//清空受理部门
									form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setRawValue(null);//清空受理部门
									form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setValue(null);//清空受理部门
									msgOnLineStore.add(record);
				 		  		}else if(Ext.isEmpty(billNo)){
				 		  			Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.choseOneBillNo'));
				 		  		}else{
					 		  		Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.choseOneDept'));
				 		  		}
							},
							exception:function(response){
								var result = Ext.decode(response.responseText);	
								form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setValue(null);
								form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setRawValue(null);
								form.findField('msgOnlineVo.msgOnlineDto.billNo').setValue(null);
								Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),result.message);
							}		
						});
		 		  	}	
	 		  		
//	 		  		if(!Ext.isEmpty(billNo)&&!Ext.isEmpty(receiveOrgCode)&&!Ext.isEmpty(receiveOrgName)){
//			 		  	var msgOnLineStore = grid.getStore();
//			 		  	var msgOnlineDto = new Object();
//			 		  	var msgOnLineEntitys = msgOnLineStore.data.items;
//			 		  	for(var i=0;i<msgOnLineEntitys.length;i++){
//							gridBillNo = msgOnLineEntitys[i].data.billNo;
//							if(gridBillNo==billNo){
//								msgOnLineStore.remove(msgOnLineEntitys[i]);
//							}
//						}
//			 		  	msgOnlineDto.billNo=billNo;
//						msgOnlineDto.billSendOrgName=receiveOrgName;
//						msgOnlineDto.billSendOrgCode=receiveOrgCode;
//						var record = new Foss.Messages.SendMsgOpeDataModel(msgOnlineDto);
//						form.findField('msgOnlineVo.msgOnlineDto.billNo').setValue(null);//清空受理部门
//						form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setRawValue(null);//清空受理部门
//						form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setValue(null);//清空受理部门
//						msgOnLineStore.add(record);
//	 		  		}else if(Ext.isEmpty(billNo)){
//	 		  			Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.choseOneBillNo'));
//	 		  		}else{
//		 		  		Ext.Msg.alert(querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.choseOneDept'));
//	 		  		}
	 		  	}
	 	  
		  	},
		  	{
				xtype: 'fieldcontainer',
				defaultType: 'radiofield',
				columnWidth :.06,
		        layout: 'table',
		        name:'msgOnlineVo.msgOnlineDto.queryAirTypeRadio',
		        items: [{ 
		        	boxLabel: querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.queryAirType'),
		        	name: 'msgOnlineVo.msgOnlineDto.queryAirType',
		        	checked:false,
		            inputValue: '1',
		        	handler:function(val){
	                		if(val.value == true){
	                			var form =Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().form;
	                			form.findField('msgOnlineVo.msgOnlineDto.queryHandOverType').reset();
	                	//		form.findField('msgOnlineVo.msgOnlineDto.queryHandOverType').setValue(false);
                 				form.findField('msgOnlineVo.msgOnlineDto.handOverBillNo').setValue("");
                 				form.findField('msgOnlineVo.msgOnlineDto.handOverBillNo').disable();
                 				form.findField('msgOnlineVo.msgOnlineDto.airBillType').enable();
                 				form.findField('msgOnlineVo.msgOnlineDto.airBillNo').enable();
	                		}
             		}
				}]
		  	},{
			  	xtype:'combobox',
		    	name:'msgOnlineVo.msgOnlineDto.airBillType',
		    	fieldLabel:'', 
//		    	store:FossDataDictionary.getDataDictionaryStore('AIR_BILL_TYPE',null,{
//					 'valueCode': '',
//              		 'valueName': 'all'
//				}),
				store:Ext.create('Foss.Messages.AirBillTypeStore'),
				queryModel:'local',
				editable:false,
				displayField:'airBillTypeName',
				valueField:'airBillTypeCode',
				columnWidth:.1
	 	  },{
	 		  	xtype : 'textfield',
		        columnWidth :.1,
				name: 'msgOnlineVo.msgOnlineDto.airBillNo',
			    listeners:{
			    	blur:function(field){
			    		var v =field.getValue();
			    		if(!Ext.isEmpty(v)&&v.length>=10){
			    			//判断长度是否大于9
			    			var str =v.trim();
			    			//给空格设置值
			    			field.setValue(str.substring(0,str.length>=10?10:str.length));
			    		}
			    	}
			    }
	 	  },{
				xtype: 'fieldcontainer',
				defaultType: 'radiofield',
				columnWidth :.06,
		        layout: 'table',
		       // checked : true,
		        name:'msgOnlineVo.msgOnlineDto.queryHandOverTypeRadio',
		        items: [{ 
					boxLabel: querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.queryHandOverType'), 
					name: 'msgOnlineVo.msgOnlineDto.queryHandOverType',
		            inputValue: '2',
		            checked:false,
		            handler:function(val){
		            	if(val.value == true){
		            	var form =Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().form;
              			form.findField('msgOnlineVo.msgOnlineDto.queryAirType').reset();
              			//form.findField('msgOnlineVo.msgOnlineDto.queryAirType').setValue(false);
         				form.findField('msgOnlineVo.msgOnlineDto.airBillType').setValue("");
         				form.findField('msgOnlineVo.msgOnlineDto.airBillNo').setValue("");
         				form.findField('msgOnlineVo.msgOnlineDto.airBillType').disable();
         				form.findField('msgOnlineVo.msgOnlineDto.airBillNo').disable();
         				form.findField('msgOnlineVo.msgOnlineDto.handOverBillNo').enable();
              		}
		            }
				}]
	 	  },{
	 		    xtype : 'textfield',
		        columnWidth :.1,
				name: 'msgOnlineVo.msgOnlineDto.handOverBillNo',
			    listeners:{
			    	//change :function(field,new_v,old_v)
			    	blur:function(field){
			    		var v =field.getValue();
			    		var str =v.trim();
			    		if(!Ext.isEmpty(v)&&str.length>=15){
			    			//判断长度是否大于15
			    			 //给空格设置值
					    	field.setValue(str.substring(0,str.length>=15?15:str.length));
			    		}
			    	}
			    }
	 	  },{
	 		  	xtype:'button',
	 		  	text:querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.query'),
	 		  	columnWidth:.06,
	 		  	cls:'yellow_button', 
	 		  	tooltip:querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.operateScope'),
//	 		    listeners : {      
//	 		    	mouseover: function(button, event) { 
//	 		    		var tip = Ext.create('Ext.tip.ToolTip', {
//	 		    		    target: 'clearButton',
//	 		    		    html: '3'
//	 		    		});     
//	 		    		}
//	 		    },
	 		  	handler:function(){
	 		  		//获取form
	 		  		var form = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().form;
	 		  		var grid = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgOpePanel().items.items[0].items.items[0];
	 		  		
	 		  		//获取单据查询类型
	 		  		var queryAirType = form.findField('msgOnlineVo.msgOnlineDto.queryAirType').getValue();
	 		  		var queryHandOverType = form.findField('msgOnlineVo.msgOnlineDto.queryHandOverType').getValue();
	 		  		var airBillNo = form.findField('msgOnlineVo.msgOnlineDto.airBillNo').getValue();
	 		  		var handOverBillNo = form.findField('msgOnlineVo.msgOnlineDto.handOverBillNo').getValue();
	 		  		var billNo = form.findField('msgOnlineVo.msgOnlineDto.billNo').getValue();
	 		  		var receiveOrgCode = form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').getValue();
	 		  		//验证是否勾选了单号类型
	 		  		if(queryAirType=='false'&&queryHandOverType=='false'){
	 		  			Ext.MessageBox.show({
							title : querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),
							msg : querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoByQueryTypeIsEmpty'),
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
	 		  			return false;
	 		  		//验证输入的单号不能同时为空	
	 		  		}else if((airBillNo==null||airBillNo=='')
	 		  				&&(handOverBillNo==null||handOverBillNo=='')){
	 		  			Ext.MessageBox.show({
							title : querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),
							msg : querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoByQueryNoIsEmpty'),
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
	 		  			return false;
	 		  		}
	 		  		
	 		  		//定义查询参数
	 		  		var params={'msgOnlineVo.msgOnlineDto.airBillNo':airBillNo,
	 		  				'msgOnlineVo.msgOnlineDto.handOverBillNo':handOverBillNo,
		 					'msgOnlineVo.msgOnlineDto.billNo':billNo,
		 					'msgOnlineVo.msgOnlineDto.receiveOrgCode':receiveOrgCode};
	 		  	    //设置账单号参数到DTO中
	 		  		
	 		  		
	 				grid.store.setSubmitParams(params);
	 				grid.store.load({
	 			      callback: function(records, operation, success){
	 			    	
	 			    	//抛出异常  
	 					var rawData = grid.store.proxy.reader.rawData;
	 				    if(!success && ! rawData.isException){
	 				    	Ext.MessageBox.show({
								title : querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),
								msg : rawData.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
	 						return false;
	 					}  
	 				  //正常返回
	 			    	if(success){
	 			    		var result =  Ext.decode(operation.response.responseText);
	 			    		//如果正常返回的话，清空运单号信息和受理部门信息
	 			    		form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setValue(null);
							form.findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setRawValue(null);
							form.findField('msgOnlineVo.msgOnlineDto.billNo').setValue(null);
	 			    	}
	 			      }
	 			    }); 
	 		  	}
	 		    
	 		    
	 	  }]  
});



Ext.define('Foss.Messages.SendMsgOpeDataGridMedel', {
    extend: 'Ext.data.Model',
    fields: [
       {name: 'billNo',type:'string'},
       {name: 'receiveOrgName',type: 'string'},
       {name: 'receiveOrgCode',type: 'string'}
    ],
    idProperty: 'billNo'
});

//var myData = [
//              ['111111','Name1','Code1'],
//              ['222222','Name2','Code2'],
//              ['333333','Name3','Code3'],
//              ['444444','Name4','Code4']
//          ];
//var myBillStore = Ext.create('Ext.data.Store', {
//    model: 'Foss.Messages.SendMsgOpeDataGridMedel',
//    data: 'Foss.Messages.SendMsgOpeDataStore'
//});


Ext.define('Foss.Messages.SendMsgOpeDataModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'billNo'
	},{	
		name:'billSendOrgName'
	},{
		name:'billSendOrgCode'
	}]
});

Ext.define('Foss.Messages.SendMsgOpeDataStore',{
	extend:'Ext.data.Store',
	model:'Foss.Messages.SendMsgOpeDataModel',
	//pageSize: 100,
	proxy:{
		type:'ajax',
		url:querying.realPath('queryBillNoByNo.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'msgOnlineVo.msgOnlineDtos',
			totalProperty:'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			params : me.submitParams ;
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

//单据列表
Ext.define('Foss.Messages.SendMsgOpeDataGrid',{
	extend:'Ext.grid.Panel',
    title: '',
     id: 'Foss.Messages.SendMsgOpeDataGrid_Id',
    frame:true,
    height : 300,
    oldStore:null,
    getOldStore:function(){
    	return this.oldStore;
    },
    setOldStore:function(oldStore){
    	this.oldStore = oldStore;
    },
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
	//store: myBillStore,
	store:Ext.create('Foss.Messages.SendMsgOpeDataStore'),
	columns:[{
		header: querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.billNo'),
		dataIndex: 'billNo'
	},{
		header: querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.receiveOrgNameList'),
		width:200,
		dataIndex: 'billSendOrgName'
	},{
		header: querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.receiveOrgCodeList'),
		dataIndex: 'billSendOrgCode',
		hidden:true
		
	}],
	constructor:function(config){
		var me = this;
		//me.store=Ext.create('Foss.Messages.SendMsgOpeDataStore');
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype :'button',
				text :querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.deleteSelectedRecord'),
				columnWidth :.4,
				handler:function(){
					
					var grid = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgOpePanel().items.items[0].items.items[0];
					var selection = grid.getSelectionModel().getSelection();
					if(selection.length==0){
						Ext.MessageBox.show({
							title : querying.writeNetMsgOnline.i18n('foss.querying.FOSSRemindYou'),
							msg : querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.alertInfoByDeleteSelectedRecord'),
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
 						return false;
					}
					for(var i=0;i<selection.length;i++){
						grid.store.remove(selection[i]);
					}
				}
			},{
				xtype :'button',
				text :querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.deleteAllRecord'),
				columnWidth :.4,
				handler:function(){
					var grid = Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgOpePanel().items.items[0].items.items[0];
					grid.store.removeAll();
				}
			},{
			  	xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.2
			}]
		}];	
		me.listeners ={
//				itemclick:function(grid,record,item,index,e){
//					//获取选中的行
//				var billNo =record.raw.billNo;
//				var billSendOrgName =record.raw.billSendOrgName;
//				var billSendOrgCode =record.raw.billSendOrgCode;
//				var sendMsgOpeMsgForm =Ext.getCmp('Foss.Messages.SendMsgQueryForm_id');
//
//				sendMsgOpeMsgForm.getForm().findField('msgOnlineVo.msgOnlineDto.billNo').setValue(billNo);
//				sendMsgOpeMsgForm.getForm().findField('msgOnlineVo.msgOnlineDto.receiveOrgCode').setCombValue(billSendOrgName,billSendOrgCode);
//				}
		};
		me.callParent();
	}
});

//消息发送 Form
Ext.define('Foss.Messages.SendMsgOpeMsgForm',{
	id:'Foss.Messages.SendMsgOpeMsgForm_id',
	extend:'Ext.form.Panel',
	frame:false,
	height : 305,
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'container',
		border:false,
		html:'<font size="3" style="font-weight:bold">'+querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.MsgDetail')+'</font>',
		height : 30,
		columnWidth:.15
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		height : 30,
		columnWidth:.7
	},{
		xtype :'button',
		text :querying.writeNetMsgOnline.i18n('foss.querying.writeNetMsgOnline.submitMsgDetail'),
		name:'queryButton',
		columnWidth :.15,
		height : 30,
		handler:function(){
			querying.writeNetMsgOnline.submit();
		}
	},{
		xtype:'textarea',
		fieldLabel:'',
		height : 270,
		allowBlank:false,
		maxLength:330,
		name:'msgOnlineVo.msgOnlineDto.remarks',
		columnWidth:1
	}]			
});

//全网消息发送表单操作panel
Ext.define('Foss.querying.writeNetMsgOnline.SendMsgOpePanel', {
	extend:'Ext.panel.Panel',
	
	frame:false,
	height : 500,
	layout: 'column',
	defaults: {                     //设置没一列的子元素的默认配置
		layout: 'anchor',
	    defaults: {
	    	anchor: '100%'
	    }
	},
	items : [ {
		title: '',
        columnWidth:.35,
        items:[
               Ext.create('Foss.Messages.SendMsgOpeDataGrid')
               ]
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		columnWidth:.02
	},{
		title: '',
	    columnWidth:.60,
        items:[
               Ext.create('Foss.Messages.SendMsgOpeMsgForm')
               ]
	}]
});



Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_querying-writeNetMsgOnline_content')) {
		return;
	} 
	
	var sendMsgQueryForm = Ext.create('Foss.querying.writeNetMsgOnline.SendMsgQueryForm');//查询FORM
	var sendMsgOpePanel = Ext.create('Foss.querying.writeNetMsgOnline.SendMsgOpePanel');//操作Panel
	
	
	
	//初始化方法
	querying.writeNetMsgOnline.init(sendMsgQueryForm);
	
	Ext.getCmp('T_querying-writeNetMsgOnline').add(Ext.create('Ext.panel.Panel', {
		id : 'T_querying-writeNetMsgOnline_content',
		cls : "panelContent",
		margin:'30 0 0 0',
		bodyCls : 'panelContent-body',
		layout : 'auto',
		getSendMsgQueryForm : function() {
			return sendMsgQueryForm; 
		}, 
	//	获得操作模版
		getSendMsgOpePanel : function() {
			return sendMsgOpePanel; 
		}, 
		items : [sendMsgQueryForm,sendMsgOpePanel],
	}));
	
	var form =Ext.getCmp('T_querying-writeNetMsgOnline_content').getSendMsgQueryForm().form;
	form.findField('msgOnlineVo.msgOnlineDto.queryAirType').reset();
	form.findField('msgOnlineVo.msgOnlineDto.airBillType').setValue("");
	form.findField('msgOnlineVo.msgOnlineDto.airBillNo').setValue("");
	form.findField('msgOnlineVo.msgOnlineDto.airBillType').disable();
	form.findField('msgOnlineVo.msgOnlineDto.airBillNo').disable();
	form.findField('msgOnlineVo.msgOnlineDto.handOverBillNo').enable();



});

