//定义：查询条件 配置项类型store 车柜
Ext.define('Foss.management.takecertificateBag.ConfigItemsContainerStore',{
	extend:'Ext.data.Store',
	fields: [
	 		{name: 'confCode',  type: 'confCode'},
	 		{name: 'confName',  type: 'confName'}
	 	],
	
		proxy: {
			type: 'memory',
			reader: {
				type: 'json',
				root: 'configOrgRelationEntityList'
			}
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});
//车头
Ext.define('Foss.management.takecertificateBag.ConfigItemsHeadStore',{
	extend:'Ext.data.Store',
	fields: [
	         {name: 'confCode',  type: 'confCode'},
	         {name: 'confName',  type: 'confName'}
	         ],
	         
	         proxy: {
	        	 type: 'memory',
	        	 reader: {
	        		 type: 'json',
	        		 root: 'configOrgRelationEntityList'
	        	 }
	         },
	         constructor: function(config){
	        	 var me = this,
	        	 cfg = Ext.apply({}, config);
	        	 me.callParent([cfg]);
	         }
});

//车辆
Ext.define('Foss.management.takecertificateBag.ConfigItemsVehicleStore',{
	extend:'Ext.data.Store',
	fields: [
	 		{name: 'confCode',  type: 'confCode'},
	 		{name: 'confName',  type: 'confName'}
	 	],
	
		proxy: {
			type: 'memory',
			reader: {
				type: 'json',
				root: 'configOrgRelationEntityList'
			}
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});


management.getPakageInfo=function(url,store,flag){
	Ext.Ajax.request({
		url:management.realPath(url),
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
//				store.loadData(result.vo.configOrgRelationEntityList);
				if(flag==1){//车头
					management.takecertificateBag.ConfigItemsHeadStore.loadData(result.vo.configOrgRelationEntityList);
					management.takeForm.items.items[1].items.items[2].add(management.checkTakeVicleHeadType());
//					management.takeForm.items.items[1].items.items[6].add(management.checkTakeVehicleType());
				}
				if(flag==2){//车柜
					management.takecertificateBag.ConfigItemsContainerStore.loadData(result.vo.configOrgRelationEntityList);
					management.takeForm.items.items[1].items.items[4].add(management.checkTakeContainerType());
				}
				if(flag==3){//车辆
					management.takecertificateBag.ConfigItemsVehicleStore.loadData(result.vo.configOrgRelationEntityList);
					management.takeForm.items.items[1].items.items[6].add(management.checkTakeVehicleType());
				}
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
			     Ext.Msg.alert(management.takecertificateBag.i18n('Foss.management.takecertificateBag.requestTimeout'));//请求超时
			}else{
				 Ext.Msg.alert(management.takecertificateBag.i18n('Foss.management.takecertificateBag.requestFail'),result.message);
			}
		}
	});
};

//将英文车牌号转换为中文车牌号 
management.convertVehicleNo=function(me,vehicleNo){
	Ext.Ajax.request({
		url : management.realPath('convertVehicleCode2Name.action'),
		params : {'vo.vehicleNo':vehicleNo},
		async: false,
		success : function(response) {
				var result=Ext.decode(response.responseText);
				me.up('form').getForm().findField('vehicleNo').setValue(result.vo.vehicleNo);//返回转换后的车牌号
		},
		exception:function(response){
			me.up('form').getForm().findField('vehicleNo').setValue(null); //异常，则为空
			var result=Ext.decode(response.responseText);
			Ext.Msg.alert(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),result.message);
		}
	});
}


//判断货柜是否被领取
management.judgeTakeInfo=function(me,containerNo){
	Ext.Ajax.request({
		url:management.realPath('queryContainerTakenInfo.action'),
		params:{'vo.certificatebagDto.containerNo':containerNo},
		success : function(response) {
			var result=Ext.decode(response.responseText);//有返回值，说明已经被领用
			//领取人编号
			if(result.vo.certificatebagDto!=null){
				var userCode=result.vo.certificatebagDto.certificatebagEntity.actualTakeUserCode;
				var userName=result.vo.certificatebagDto.certificatebagEntity.actualTakeUserName;
				Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.containerNoTaken')+userCode+management.takecertificateBag.i18n('foss.management.certificateBag.exception.containerNoTakenName')+userName, 'error', 3000);
			}
			return;
		},
		exception:function(response){
			me.up('form').getForm().findField('containerNo').setValue( containerNo); //异常，则返回
		}
	});
}
//领取证件包车头信息
Ext.define('Foss.management.takeVehicleForm',{
	extend: 'Ext.form.Panel',
    title:management.takecertificateBag.i18n('foss.management.certificateBag.vehicleInfo.title'), //车辆信息	
    width:'95%',
    layout:'column',	   
    defaults:{	
	   	xtype: 'textfield',
	   	margin:'10 10 10 20'
	 },	
	items:[{
					name: 'id',
					xtype: 'hiddenfield'
				},{
					xtype: 'commonowntruckselector',
					name: 'vehicleNo',							
					fieldLabel: management.takecertificateBag.i18n('foss.management.certificateBag.label.vehicleNo'),//车牌号
					labelWidth:80,					
					columnWidth:.30
					
				},{
					fieldLabel: management.takecertificateBag.i18n('foss.management.certificateBag.label.containerNo'),//货柜号
					xtype : 'commonowntruckselector',							
					displayField : 'containerCode',// 显示名称
					valueField : 'containerCode',
					myQueryParam : 'containerCode',
					showContent : '{containerCode}',  
					name: 'containerNo',
					vehicleTypes : 'vehicletype_trailer',
					columnWidth:.28,
					listeners: {//BUG-5898  货柜被谁领取，请显示出来 modify by liangfuxiang 2013-04-09
						'select': function(field, records, eOpts) {	
							this.up('form').form.findField('actualTakeUserName').setValue(field.rawValue);
							var containerNo=this.up('form').form.findField('actualTakeUserName').getValue();
							//判断货柜号是否已经被使用，若被领用，则返回领用者信息alert
							management.judgeTakeInfo(this,containerNo);
						}
					}
				},{
					xtype: 'radiogroup',
			        fieldLabel: management.takecertificateBag.i18n('foss.management.certificateBag.label.businessType'),//业务类型 			       
			        vertical: true,
			        labelWidth:70,
					columnWidth:.33,					
			        items: [
			            { boxLabel: management.takecertificateBag.i18n('foss.management.certificateBag.label.longtype'), name: 'businessType', inputValue: 'LONG_TYPE',checked:true},
			            { boxLabel: management.takecertificateBag.i18n('foss.management.certificateBag.label.shorttype'), name: 'businessType', inputValue: 'SHORT_TYPE'}					           
			        ],
			        listeners:{change:function(ewfa,newValue,oldValue, eOpts){				        	
			        	if(newValue.businessType=='LONG_TYPE'){	
			        		Ext.getCmp('Foss_management_takeCertificateBagPanel_containerType_Id').setValue({'vehicleheadType':false,'vehicleheadPermisoDeCirculacion':false,'vehicleheadOverlandTransportation':false,'vehicleheadCarKey':false,'vehicleheadBusinessRegistrationCertificate':false,'vehicleheadPurchaseTax':false});						   		
				        	Ext.getCmp('Foss_management_takeCertificateBagPanel_vehicleheadType_Id').setValue({'vehicleheadType':false,'containerPermisoDeCirculacion':false,'containerOverlandTransportation':false,'containerDebitNote':false,'containerBusinessRegistrationCertificate':false,'containerVehiclePurchaseTax':false,'containerInsuranceCard':false});
				        	Ext.getCmp('Foss_management_takeCertificateBagPanel_vehicleType_Id').setValue({'vehicleheadType':false,'vehiclePermisoDeCirculacion':false,'vehicleOverlandTransportation':false,'vehicleCarKey':false,'vehicleInsuranceCard':false});
				        	management.takeForm.items.items[1].items.items[1].show();
							management.takeForm.items.items[1].items.items[2].show();
			        		management.takeForm.items.items[1].items.items[3].show();
							management.takeForm.items.items[1].items.items[4].show();
							management.takeForm.items.items[0].items.items[2].show();								
			        		management.takeForm.items.items[1].items.items[5].hide();
							management.takeForm.items.items[1].items.items[6].hide();	
			        	}else{
							management.takeForm.items.items[1].items.items[1].hide();
							management.takeForm.items.items[1].items.items[2].hide();
			        		management.takeForm.items.items[1].items.items[3].hide();
							management.takeForm.items.items[1].items.items[4].hide();
							management.takeForm.items.items[0].items.items[2].hide();							
							management.takeForm.items.items[0].items.items[2].setValue('');
							management.takeForm.items.items[1].items.items[5].show();
							management.takeForm.items.items[1].items.items[6].show();
							
			        	}
						
					 }
			       }
				}
		       ],	
		 constructor : function(config) {
		   		var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
				comkey=me.getForm().findField('vehicleNo');
			    comkey.removeListener('keypress');
			    comkey.on('keypress',function(textfield, e){
					if(e.getKey() == Ext.EventObject.ENTER){
						var oldVehicleNo=this.up('form').getForm().findField('vehicleNo').getValue();
						if(oldVehicleNo!=null && oldVehicleNo!=''){
							if(oldVehicleNo.indexOf("-")!=-1){//包含两个'-'
								management.convertVehicleNo(this,oldVehicleNo);
								if(comkey.getValue()){
								    comkey.getKeyPress(comkey, e);   
								}
							}	
						}
					}
			});
	     }
});


//复选框全选
management.checkTakeAll=function(array,type){
	if(type=='vehicle'){
		if(management.takeForm.items.items[0].items.items[1].getValue()==null){
			Ext.getCmp('Foss_management_takeCertificateBagPanel_vehicleheadType_Id').setValue({'vehicleheadType':false})
			return;
		}
	}else{
		if(management.takeForm.items.items[0].items.items[2].getValue()==null){
			Ext.getCmp('Foss_management_takeCertificateBagPanel_containerType_Id').setValue({'vehicleheadType':false})
	   	    return;
		}
	}
	if(array.items[0].getValue()==true){
	  array.each(function(item){ 
	   item.setValue(true);             
     });  
	}else{
	    array.each(function(item){ 
	   item.setValue(false);             
     }); 
	}
	
}

//给复选框赋值
management.checkTakeClick=function(array,type){
	if(type=='vehicle'){
		if(management.takeForm.items.items[0].items.items[1].getValue()==null){
			Ext.getCmp('Foss_management_takeCertificateBagPanel_vehicleheadType_Id').setValue({'vehicleheadType':false})
			return;
		}
	}else{
		if(management.takeForm.items.items[0].items.items[2].getValue()==null){
			Ext.getCmp('Foss_management_takeCertificateBagPanel_containerType_Id').setValue({'vehicleheadType':false})
	   	    return;
		}
	}
	 var num=0;//
	 array.each(function(item){ 
            item.setValue(item.getValue()); 
            if(item.getValue()==true){
		    	num=num+1;
		    }
      });	 
	 if(array.items[0].getValue()==true){
		num=num-1; 
	 }
	 if(array.length-1==num){
		 array.items[0].setValue(true); 
	 }else{
		 array.items[0].setValue(false);
	 }	
}
//找归还证件包证件复选框赋值 (所有的车头证件)
management.checkTakeVicleHeadType=function(){
	
	var checkboxitems="[{boxLabel : '"+management.takecertificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	
	//modify by liangfuxiang 2013-04-13 begin ISSUE-2093
	//获取车头证件
	//构建选项
	//var data=FossDataDictionary.getDataDictionaryStore('VEHICLEHEAD_CARD_TYPE').data;//得到车头证件
	var data=management.takecertificateBag.ConfigItemsHeadStore.data;
	//modify by liangfuxiang 2013-04-13 end
	//判断字典非空
	if(data==undefined)
	{
		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.vehicleheadDictionaryNull'), 'error', 3000);
  		return;
	}
	
	management.takecertificateBag.ItemsHeadCount=data.length;
	
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehiclehead',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.takecertificateBag.i18n('foss.management.certificateBag.vehiclehead.title'),//车头证件
		labelAlign: 'top',		
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss_management_takeCertificateBagPanel_vehicleheadType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss_management_takeCertificateBagPanel_vehicleheadType_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.checkTakeAll(array,'vehicle');
							}else{
								management.checkTakeClick(array,'vehicle');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}


//找归还证件包证件复选框赋值(所有的车柜证件)
management.checkTakeContainerType=function(){
	var checkboxitems="[{boxLabel : '"+management.takecertificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
	
	//modify by liangfuxiang 2013-04-13 begin ISSUE-2093
	//获取车头证件
	//构建选项
	//var data=FossDataDictionary.getDataDictionaryStore('CONTAINER_CARD_TYPE').data;//得到车柜证件
	var data=management.takecertificateBag.ConfigItemsContainerStore.data;
	//modify by liangfuxiang 2013-04-13 end
	//判断字典非空
	if(data==undefined)
	{
		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.containerDictionaryNull'), 'error', 3000);
  		return;
	}
	
	management.takecertificateBag.ItemsContaineCount=data.length;
	
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'container',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.takecertificateBag.i18n('foss.management.certificateBag.container.title'),//车柜证件
		labelAlign: 'top',		
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss_management_takeCertificateBagPanel_containerType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss_management_takeCertificateBagPanel_containerType_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.checkTakeAll(array,'container');
							}else{
								management.checkTakeClick(array,'container');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}

//查找归还证件包车辆证件复选框赋值(所有的车辆证件)
management.checkTakeVehicleType=function(){
	var checkboxitems="[{boxLabel : '"+management.takecertificateBag.i18n('foss.management.selectAll')+"',margin:'5 5 5 5',name : 'vehicleheadType',inputValue : 'ALL'}  ";
	//构建选项
    //var data=FossDataDictionary.getDataDictionaryStore('VEHICLE_CARD_TYPE').data;//得到车柜证件
	var data=management.takecertificateBag.ConfigItemsVehicleStore.data;//得到车头证件
	
	//判断字典非空
	if(data==undefined)
	{
		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.vehicleDictionaryNull'), 'error', 3000);
  		return;
	}
	
	management.takecertificateBag.ItemsVehicleCount=data.length;
	
	for(var i = 0 ;i<data.length; i++){
		if(checkboxitems != "")
			checkboxitems += ",";
		var checkboxSingleItem = "{boxLabel:' " + data.items[i].data.confName+ "', margin:'5 5 5 5',name : 'vehicle',inputValue:'" + data.items[i].data.confCode +"'}";
		checkboxitems = checkboxitems+ checkboxSingleItem;
	}
	checkboxitems += "]";
	//重新实例化所有的checkbox
	var checkBoxGroup = Ext.create('Ext.form.CheckboxGroup',{
		fieldLabel : management.takecertificateBag.i18n('foss.management.certificateBag.vehicle.title'),//车辆证件
		labelAlign: 'top',
		labelSeparator : '',		
		margin : '0 0 0 6',
		id:'Foss_management_takeCertificateBagPanel_vehicleType_Id',
		defaults : { 		    
		    margin : '0 0 5 0'
		},
		layout : 'column',
		items : eval(checkboxitems)
	});
	var checkBoxes = checkBoxGroup.items.items;//得到所有的checkbox
	var boxesCount = checkBoxes.length;//长度	
		//给每个checkbox加监听，监听勾选和反选
		for(var i in checkBoxes){
			var checkBox = checkBoxes[i];
			//添加值改变事件
			checkBox.addListener(
					'afterrender',function(cmp){											   			
						cmp.getEl().getById(cmp.getId() + '-bodyEl').on('click',function(){	
							var array = Ext.getCmp('Foss_management_takeCertificateBagPanel_vehicleType_Id').items;   
							if(cmp.inputValue=='ALL'){//如果是全选给全选新增事件
								management.checkTakeAll(array,'vehicle');
							}else{
								management.checkTakeClick(array,'vehicle');
							}
							
						})
					}
				);
		}
	return checkBoxGroup;
}

//领取证件包 from
Ext.define('Foss.management.takeCertificateBagForm',{
	   extend: 'Ext.form.FieldSet',
	   title:management.takecertificateBag.i18n('foss.management.certificateBag.certificateBagInfo.title'),//证件包信息	   
	   width:'95%',
	   layout:'column',	   
	   defaults:{	
		   	xtype: 'textfield',
		   	margin:'10 10 10 10'
		},
		items:[{			
					xtype: 'commonowndriverselector',
	    			name: 'actualTakeUserCode',	
	    			allowBlank: false,	
//	    			forceSelection: true,
	    			labelWidth: 60,	
					fieldLabel: management.takecertificateBag.i18n('foss.management.certificateBag.label.actualTakeUserName'),//领取人
					listeners: {
						'select': function(field, records, eOpts) {					
							var record = records[0],
								name = record.get('name');
							this.up('form').form.findField('actualTakeUserName').setValue(field.rawValue);
							
						},
						//modify by liangfuxiang 2013-04-09
						specialKey:function(textfield, e){ 
							if(e.getKey() == Ext.EventObject.ENTER){
								var actualTakeUserName=this.up('form').getForm().findField('actualTakeUserCode').getValue();
								this.up('form').getForm().findField('actualTakeUserCode').setValue(actualTakeUserName);
							}
						}
					}
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr style="width:900px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.checkTakeVicleHeadType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="900px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.checkTakeContainerType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="900px">' 
				},{
					xtype : 'container'
//						,
//					items : [
//					         management.checkTakeVehicleType()
//					         ]
				},{   
					xtype : "panel",  
					border : false,  
					height : 10, 
					html : '<hr  width="900px">' 
				},{
					xtype : 'textarea',
					name : 'actualTakeNotes' , 
					height: 40,
					maxLength : 60,
					maxLengthText:management.takecertificateBag.i18n('foss.management.certificateBag.label.maxLengthText'),//长度已超过最大限制!
					labelWidth: 60,	
					fieldLabel : management.takecertificateBag.i18n('foss.management.certificateBag.label.notes'),//备注
					rowspan : 3
				},{
					name: 'actualTakeUserName',
					xtype: 'hiddenfield'
				}
				
				]
	
});


//领取证件包界面
Ext.define('Foss.management.takeCertificateBagInfoPanel',{	
	extend: 'Ext.form.Panel',
	layout:'column',
	buttonAlign :'center',
	frame: false,	
	defaultType: 'textfield',	
	items:[	       
	        Ext.create('Foss.management.takeVehicleForm'),
	       Ext.create('Foss.management.takeCertificateBagForm')
	 ],
	  buttons: [{
	      text: management.takecertificateBag.i18n('foss.management.certificateBag.button.affirmsubmit'),//确认提交
	      cls:'yellow_button',
	      handler: function() {
	    	  var form = this.up('form').getForm();
	      	  var vals = this.up('form').getForm().getValues();
	      	 
	      	  //领取人是否选择
	      	  if(vals.actualTakeUserCode==undefined){
	      		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificate.exception.receiverWrongEmpId'), 'ok', 1000);
	      		return;
	      	  }
	      	  
	      	  //车牌号或者货柜号是否输入了
		      	 if(vals.vehicleNo==undefined&&vals.containerNo==undefined){
		      		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.vehicleNoOrContainerNoSelectOne'), 'ok', 1000);
		      		return;
		      	 }
		     	//得到复选的车辆证件 
			      	var fieldVehicleVaules=form.getFieldValues().vehicle;
			      	var fieldVehicleLen=0;
			      	for(var i=0;i<fieldVehicleVaules.length;i++){
			      		if(fieldVehicleVaules[i]==true){
			      			fieldVehicleLen+=1;
			      		}
			      	}
			      	var vehicles=new Array(); //定义一个车辆数组
			      	var vehicleValues=vals.vehicle;
			      	 if(fieldVehicleLen==1){
			      		vehicles.push(vehicleValues);
			      	 }else if(fieldVehicleLen>1){
			      		for(var j=0;j<vehicleValues.length;j++){
			      			vehicles.push(vehicleValues[j]);
				      	}
			      	 }
			      	vals.vehicle=vehicles;
			      	//等到复选的车柜证件
			      	var fieldcontainerVaules=form.getFieldValues().container;
			      	var fieldcontainerLen=0;
			      	for(var i=0;i<fieldcontainerVaules.length;i++){
			      		if(fieldcontainerVaules[i]==true){
			      			fieldcontainerLen+=1;
			      		}
			      	}
			      	var containers=new Array(); //定义一个车辆数组
			      	var containerValues=vals.container;
			      	 if(fieldcontainerLen==1){
			      		containers.push(containerValues);
			      	 }else if(fieldcontainerLen>1){
			      		for(var j=0;j<containerValues.length;j++){
			      			containers.push(containerValues[j]);
				      	}
			      	 }
			      	vals.container=containers;
			      	//复选的车头证件
			      	var fieldvehicleheadVaules=form.getFieldValues().vehiclehead;
			      	var fieldvehicleheadLen=0;
			      	for(var i=0;i<fieldvehicleheadVaules.length;i++){
			      		if(fieldvehicleheadVaules[i]==true){
			      			fieldvehicleheadLen+=1;
			      		}
			      	}
			      	var vehicleheads=new Array(); //定义一个车辆数组
			      	var vehicleheadValues=vals.vehiclehead;
			      	 if(fieldvehicleheadLen==1){
			      		vehicleheads.push(vehicleheadValues);
			      	 }else if(fieldvehicleheadLen>1){
			      		for(var j=0;j<vehicleheadValues.length;j++){
			      			vehicleheads.push(vehicleheadValues[j]);
				      	}
			      	 }
			      	vals.vehiclehead=vehicleheads;
			      	
		      	var conLength=0;//车柜证件
		      	if(vals.container!=''&&vals.container!=undefined){
		      		conLength=vals.container.length;	//车牌证件长度      		
		      	}	      
		      	var vehicleHeadLength=0;//车头证件
		      	if(vals.vehiclehead!=''&&vals.vehiclehead!=undefined){
		      		vehicleHeadLength=vals.vehiclehead.length;    		
		      	}
		    	var vehicleLength=0;//车辆证件
		      	if(vals.vehicle!=''&&vals.vehicle!=undefined){
		      		vehicleLength=vals.vehicle.length;   //车辆证件长度 		
		      	}
		      	var isNotes=true;//备注是否必填
//		      	var data=FossDataDictionary.getDataDictionaryStore('VEHICLEHEAD_CARD_TYPE').data;//得到车头证件
//		    	//判断字典非空
//		    	if(data==undefined)
//		    	{
//		    		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.vehicleheadDictionaryNull'), 'error', 3000);
//		      		return;
//		    	}
//		    	var containerData=FossDataDictionary.getDataDictionaryStore('CONTAINER_CARD_TYPE').data;//型得到车柜证件
//		    	
//		    	//判断字典非空
//		    	if(containerData==undefined)
//		    	{
//		    		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.containerDictionaryNull'), 'error', 3000);
//		      		return;
//		    	}
//		    	
//		    	var vehicleData=FossDataDictionary.getDataDictionaryStore('VEHICLE_CARD_TYPE').data;//车辆证件类
//		    	//判断字典非空
//		    	if(vehicleData==undefined)
//		    	{
//		    		Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.exception.vehicleDictionaryNull'), 'error', 3000);
//		      		return;
//		    	}
		    	
		      	if(vals.vehicleNo!=''&&vals.vehicleNo!=undefined){//
		      		if(vals.businessType=='LONG_TYPE'){//长途车
		      			//判断车牌证件是否选择
			      		if(vehicleHeadLength==0){
			      			Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.selectvehiclehead'), 'ok', 1000);
			      			return;
			      		//}else if(vehicleHeadLength!=data.length){		      		
			      		}else if(vehicleHeadLength!=management.takecertificateBag.ItemsHeadCount){		      		
			      			//车柜证件未全部选择备注项必填
			      			isNotes=false;
			      			vals.vehicleHandOverStatus='N';
			      		}else{
			      			vals.vehicleHandOverStatus='Y';
			      		}	 
		      		}else{//短途车
		      			//判断车牌证件是否选择
			      		if(vehicleLength==0){
			      			Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.selectvehicle'), 'ok', 1000);
			      			return;
			      		//}else if(vehicleLength!=vehicleData.length){		      		
			      		}else if(vehicleLength!=management.takecertificateBag.ItemsVehicleCount){		      		
			      			//车辆证件未全部选择备注项必填
			      			isNotes=false;
			      			vals.vehicleHandOverStatus='N';
			      		}else{
			      			vals.vehicleHandOverStatus='Y';
			      		}	 
		      		}     		  
		      	  }else{
		      		  //车牌号为空的情况，检查证件是否点击了
		      		if(vals.businessType=='LONG_TYPE'){//长途车
		      			//判断车牌证件是否选择
			      		if(vehicleHeadLength>0){
			      			Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.selectvehicleNo'), 'ok', 1000);
			      			
			      			return;
			      		}	 
		      		}else{//短途车
		      			//判断车牌证件是否选择
			      		if(vehicleLength>0){
			      			Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.selectvehicleNo'), 'ok', 1000);
			      			
			      			return;
			      		}
		      		}		 
		      	  }
		      	//车柜证件是否输入
		      	 if(vals.containerNo!=undefined&&vals.containerNo!=''){
		      		 //车头证件一个都未选择
		      		if(conLength==0){
		      			Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.containerselectAll'), 'ok', 1000);
		      			//是否全选 
		      			return ;
//		      		  }else if(conLength!=containerData.length){
		      		  }else if(conLength!=management.takecertificateBag.ItemsContaineCount){
		      			isNotes=false;      			  
		      			vals.containerHandOverStatus='N';
			      	 }else{
			      		vals.containerHandOverStatus='Y';
			      	 }	       		  
		      	  }else{
		      		 //车头证件一个都未选择
			      		if(conLength>0){
			      			Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.selectcontainerNo'), 'ok', 1000);
			      			//是否全选  
			      			return ;
			      		  }
		      	  }
		      	
		      	 if(isNotes){
		      		//
		      	 }else{
		      		 if(vals.actualTakeNotes==''){
		      			Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.papersNotselectAll'), 'ok', 1000);
		      			return;		      		
		      		 }
		      	 }	
	      		 Ext.MessageBox.confirm(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),management.takecertificateBag.i18n('foss.management.certificateBag.label.certificateBagAlreadyTake'), function(button,text){   
	 				if(button=='yes'){	
	 					management.takeForm.getEl().mask(management.takecertificateBag.i18n('foss.management.certificateBag.button.affirmsubmit'));
	 					if(form.isValid()){
	 						var params={vo:{takeDto:vals}};
	 					     Ext.Ajax.request({
	 				       	  url: management.realPath('takeCertificateBag.action'),
	 				       	  jsonData: params,
	 				       	  success: function(response, opts) { 
	 				       		management.takeForm.getEl().unmask();
	 					    	form.reset();
	 					    	Ext.ux.Toast.msg(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'), management.takecertificateBag.i18n('Foss.management.message.saveSuccess'), 'ok', 1000);
	 				       	  },
	 				       	 exception: function(response, opts) {
	 				       		   management.takeForm.getEl().unmask();
	 					       	   var result = Ext.decode(response.responseText);	
	 					       	   //modify by liangfuxiang 2013-04-18 BUG-7505
	 						       Ext.Msg.alert(management.takecertificateBag.i18n('foss.management.messageBox.alert.tip.title'),result.message);
	 					       	  }
	 				         });
	 			    	}
	 				 }
	 				});
	      }
	  }],
	  constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);		
		
	}
});

Ext.onReady(function() {
	//1.禁止使用全局变量,可以使用module标签生成的模块名的object对象{}
	//	用法：模块名.自定义变量
	//2.对象都是用Ext.define定义的方式声明
	Ext.QuickTips.init();
	//modify by liangfuxiang 2013-04-13 begin ISSUE-2093
	management.takecertificateBag.ConfigItemsHeadStore=Ext.create('Foss.management.takecertificateBag.ConfigItemsHeadStore');
	management.takecertificateBag.ConfigItemsContainerStore=Ext.create('Foss.management.takecertificateBag.ConfigItemsContainerStore');
	management.takecertificateBag.ConfigItemsVehicleStore=Ext.create('Foss.management.takecertificateBag.ConfigItemsVehicleStore');
	management.getPakageInfo('queryCarHeadItems.action',management.takecertificateBag.ConfigItemsHeadStore,1);
	management.getPakageInfo('queryContainerCardItems.action',management.takecertificateBag.ConfigItemsContainerStore,2);
	management.getPakageInfo('queryVehicleItems.action',management.takecertificateBag.ConfigItemsVehicleStore,3);
	management.takecertificateBag.ItemsHeadCount=null;//车头证数量
	management.takecertificateBag.ItemsContaineCount=null;//车柜数量
	management.takecertificateBag.ItemsVehicleCount=null;//车辆证件数量
	//modify by liangfuxiang 2013-04-13 end
	//查询表单
	var takeForm = Ext.create('Foss.management.takeCertificateBagInfoPanel');
	management.takeForm=takeForm;	
	management.takeForm.items.items[1].items.items[5].hide();
	management.takeForm.items.items[1].items.items[6].hide();
	Ext.create('Ext.panel.Panel',{
		id: 'T_management-takecertificatebagindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		detailInfoWindow:null,
		editDetailInfoWindow:null,		
		items: [takeForm],
		renderTo: 'T_management-takecertificatebagindex-body'
	});
});

