
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_MAX = 31;  //时间间隔最大不超过90天
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_ONEMONTH = 1;  //时间相差一天
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_ONEPAGESIZE = 10; //每页显示的条数

writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_DATE='QBD';//按时间查询
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_WRITEOFFNO='QBWON';//按核销单号查询
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_WAYBILLNO='QBWBN';//按运单号查询
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_ADVBILLNO='QBABN';//按预付号查询
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_PAYBILLNO='QBPBN';//按应付号查询
writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_DEPBILLNO='QBDBN';//按应付号查询
/**
 * 提示信息
 * @param {} message 
 * @param {} yesFn
 * @param {} noFn
 */
writeoff.reverseWriteoff.reverseWriteoffConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * 获取Grid组件信息
 * @returns
 */
writeoff.reverseWriteoff.getReverseWriteoffPageGridObj=function(){
	var me=Ext.getCmp('T_writeoff-reverseWriteOff_content');
	if(me){
		var grid = me.getQueryGrid();
		var store=grid.getStore();
		if(store.data.length==0){
			Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'), writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.notOpeByNoResult'));
			return null;
		}
		return grid;	
	}
	return null;
};

/**
 * Form重置方法
 */
writeoff.reverseWriteoff.reverseWriteoffReset=function(){
	this.up('form').getForm().reset();
}

/**
 * Form查询方法
 */
writeoff.reverseWriteoff.reverseWriteoffQuery=function(f,me,queryType){
	var form=f.getForm();
	var grid = Ext.getCmp('T_writeoff-reverseWriteOff_content').getQueryGrid();
	
	//按日期查询
	if(writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_DATE==queryType){
		var startBusinessDate = form.findField('reverseBillWriteoffVo.reverseBillWriteoffDto.startBusinessDate').getValue();
		var endBusinessDate = form.findField('reverseBillWriteoffVo.reverseBillWriteoffDto.endBusinessDate').getValue();
		
		if(startBusinessDate==null || startBusinessDate==''){
			
			Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.startBusinessDateIsNotNull'));
			return false;
		}

		if(endBusinessDate==null || endBusinessDate==''){
			Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.endBusinessDateIsNotNull'));
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(startBusinessDate,endBusinessDate);
		if(compareTwoDate>writeoff.reverseWriteoff.STLREVERSEWRITEOFF_MAX){
			Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.startToEndBusinessDateIsNotMaxDay'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.endBusinessDateIsNotBeforeStart'));
			return false;
		}
	}

	if(form.isValid()){
		
		var params=writeoff.reverseWriteoff.reverseWriteoffQuerySetParams(form,queryType);
		//设置查询参数

		grid.store.setSubmitParams(params);
		//设置该按钮灰掉
    	me.disable(false);
    	//30秒后自动解除灰掉效果
    	setTimeout(function() {
    		me.enable(true);
    	}, 30000);
		//设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
//			var rawData = grid.store.proxy.reader.rawData;
//		    if(!success && ! rawData.isException){
//				Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),rawData.message);
//				return false;
//			}  
	    	  
		    //正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				
				toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
				//toolBar.getComponent('totalCounts').setValue(result.vo.totalCounts);
				toolBar.getComponent('totalRows').setValue(result.reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffTotalRows);
				toolBar.getComponent('writeoffAmount').setValue(stl.amountFormat(result.reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffTotalAmout));
		       
				if(result.reverseBillWriteoffVo.reverseBillWriteoffDto.billWriteoffEntityList.length>0){
					grid.show();
				}else{
					Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.noDataUnderThisQuery'));
					me.enable(true);
					return false;
				}
				me.enable(true);
	    	}  
	      }
	    }); 
	}else {
		Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'), writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.validateFailAlert'));
	}
}

//设置参数
writeoff.reverseWriteoff.reverseWriteoffQuerySetParams=function(form,queryType){
	//定义查询参数
	var params={};
	
	//按日期查询
	if(writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_DATE==queryType){
		
		//获取FORM所有值
		params = form.getValues();
		var customerDetial = form.findField('customerDetial').getValue();
		var airDetial = form.findField('airDetial').getValue();
		var airAgencyDetial = form.findField('airAgencyDetial').getValue();
		var vehAgencyDetial = form.findField('vehAgencyDetial').getValue();
		var landStowage = form.findField('landStowage').getValue();
		var partnerOrg = form.findField('partnerOrg').getValue();//合伙人部门
		if(customerDetial!=null&&customerDetial!=''){
			Ext.apply(params, {
				'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode' : customerDetial
			});
		}else if(airDetial!=null&&airDetial!=''){
			Ext.apply(params, {
				'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode' : airDetial
			});
		}else if(airAgencyDetial!=null&&airAgencyDetial!=''){
			Ext.apply(params, {
				'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode' : airAgencyDetial
			});
		}else if(vehAgencyDetial!=null&&vehAgencyDetial!=''){
			Ext.apply(params, {
				'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode' : vehAgencyDetial
			});
		}else if(!Ext.isEmpty(landStowage)){
			Ext.apply(params, {
				'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode' : landStowage
			});
		}else if(!Ext.isEmpty(partnerOrg)){
			Ext.apply(params, {
				'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode' : partnerOrg
			});
		}
	//按核销单号查询
	}else if(writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_WRITEOFFNO==queryType){ 
	
		//获取FORM所有值
		params = form.getValues();
		
	//按运单查询
	}else if(writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_WAYBILLNO==queryType){
		
		//获取FORM所有值
		params = form.getValues();
		
	}
	//按预付单查询
	else if(writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_ADVBILLNO==queryType){

		//获取FORM所有值
		params = form.getValues();

	}
	//按应付单查询
	else if(writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_PAYBILLNO==queryType){

		//获取FORM所有值
		params = form.getValues();
		
	}
	//按预收单查询
	else if(writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_DEPBILLNO==queryType){

		//获取FORM所有值
		params = form.getValues();
	}
	

	return params;
}

/**
 * 导出核销单方法
 */
writeoff.reverseWriteoff.reverseWriteoffBillExport = function(){
	var me = this;
	var grid = writeoff.reverseWriteoff.getReverseWriteoffPageGridObj();
	
	Ext.MessageBox.buttonText.yes = writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.ok');  
	Ext.MessageBox.buttonText.no = writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.cancel'); 
	
	Ext.Msg.confirm( writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'), writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.isConfirmExport'), function(btn,text){
		if(btn == 'yes'){
			var params=grid.store.submitParams;
			if(!Ext.fly('reverseWriteoffBillExportForm')){
				var frm = document.createElement('form');
				frm.id = 'reverseWriteoffBillExportForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
	
			me.disable(false);
			//10秒后自动解除灰掉效果
			setTimeout(function() {
			me.enable(true);
			}, 10000);
			Ext.Ajax.request({
				//url:'../writeoff/exportReverseWriteoffBill.action',
				url:writeoff.realPath('exportReverseWriteoffBill.action'),
				form: Ext.fly('reverseWriteoffBillExportForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					var result = Ext.decode(response.responseText);
				},
				failure:function(response){
					var result = Ext.decode(response.responseText);
				}
			});
		}
	});
}

/*
//核销类型model
Ext.define('Foss.StlRevWriteOff.WriteOffTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

//核销类型Store
Ext.define('Foss.StlRevWriteOff.WriteOffTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlRevWriteOff.WriteOffTypeModel',
	data:{
		'items':[
			{name:'全部',value:''},
			{name:'预收冲应收',value:'DR'},
			{name:'应收冲应付',value:'RP'},
			{name:'还款冲应收',value:'RR'},
			{name:'付款冲应付',value:'PD'},
			{name:'预付冲应付',value:'AP'},
			{name:'坏账冲应收',value:'BR'},
			{name:'付款冲预收',value:'PP'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	},
	autoLoad: true
});


//是否红单model
Ext.define('Foss.StlRevWriteOff.IsRedBackTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

//是否红单Store
Ext.define('Foss.StlRevWriteOff.IsRedBackTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlRevWriteOff.IsRedBackTypeModel',
	data:{
		'items':[
			{name:'全部',value:''},
			{name:'是',value:'Y'},
			{name:'否',value:'N'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	},
	autoLoad: true
});

//核销方式model
Ext.define('Foss.StlRevWriteOff.CreateTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

/*核销方式Store
Ext.define('Foss.StlRevWriteOff.CreateTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlRevWriteOff.CreateTypeModel',
	data:{
		'items':[
			{name:'全部',value:''},
			{name:'手工生成',value:'M'},
			{name:'自动生成',value:'A'}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	},
	autoLoad: true
});
*/

/**
 * FORM 定义
 */

//按日期查询 Form
Ext.define('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByDate',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:280,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items : [
				{
					xtype : 'datefield',
					name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.startBusinessDate',
					editable:false,
					fieldLabel : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.startBusinessDate'),
					value : stl.getTargetDate(new Date(),
							-writeoff.reverseWriteoff.STLREVERSEWRITEOFF_ONEMONTH),
					format : 'Y-m-d',
					allowBlank : false
				},
				{
					xtype : 'dynamicorgcombselector',
					fieldLabel : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.orgName'),
					name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.orgCode',
					allowBlank : false //ISSUE-3086 设为必输项 杨书硕 2013-06-27
				},
				{
					xtype : 'combobox',
					name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffType',
					fieldLabel : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.writeoffType'),
					//store : Ext.create('Foss.StlRevWriteOff.WriteOffTypeStore'),
					store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_WRITEOFF__WRITEOFF_TYPE,null,{
						 'valueCode': '',
	               		 'valueName': writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.all')
					}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value:''
				},{
					xtype : 'datefield',
					name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.endBusinessDate',
					editable:false,	
					fieldLabel : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.endBusinessDate'),
					format : 'Y-m-d',
					value : new Date(),
					allowBlank : false,
					maxValue : new Date()
				},{
					xtype : 'combobox',
					name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.createType',
					fieldLabel : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.createType'),
					//store : Ext.create('Foss.StlRevWriteOff.CreateTypeStore'),
					store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__CREATE_TYPE,null,{
						 'valueCode': '',
	               		 'valueName': writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.all')
					}),
					queryModel : 'local',
					displayField:'valueName',
					valueField:'valueCode',
					value:''
				},
				/* ISSUE-3086 是否红单查询条件建议修改为是否有效查询条件，其默认为有效 
				 * 杨书硕
				 * 2013-06-27
				 */
				{
					xtype : 'combobox',
					name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.active',
					fieldLabel : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.active'),
					//store : Ext.create('Foss.StlRevWriteOff.IsRedBackTypeStore'),
					store:FossDataDictionary.getDataDictionaryStore('FOSS_ACTIVE',null,{
						 'valueCode': '',
	               		 'valueName': writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.all')
					}),
					queryModel : 'local',
					displayField:'valueName',
					valueField:'valueCode',
					value:'Y'
				},{	
					xtype      : 'fieldcontainer', 
					defaultType: 'radiofield',
					layout:'table',
					columnWidth:.9,
					colspan:3,
		            items: [{
	                    boxLabel  : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.customerDetial'),
	                    name      : 'custType',
	                    checked:true,
	                    handler:function(val){
	                		if(val.value == true){
                    			var form =this.up('form').getForm();
        	                	form.findField('customerDetial').show();
        	                	form.findField('airAgencyDetial').hide();
	    	                	form.findField('airAgencyDetial').setValue("");
	    	                	form.findField('airDetial').hide();
	    	                	form.findField('airDetial').setValue("");
	    	                	form.findField('vehAgencyDetial').hide();
        	                	form.findField('vehAgencyDetial').setValue("");
        	                	form.findField('landStowage').hide();
        	                	form.findField('landStowage').setValue("");
        	                	form.findField('partnerOrg').hide();
        	                	form.findField('partnerOrg').setValue("");
	                		}
                		}
	                },{
	                    boxLabel  : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.airDetial'),
	                    name      : 'custType',
	                    handler:function(val){
	                		if(val.value == true){
                    			var form =this.up('form').getForm();
        	                	form.findField('airDetial').show();
        	                	form.findField('customerDetial').hide();
	    	                	form.findField('customerDetial').setValue("");
	    	                	form.findField('airAgencyDetial').hide();
	    	                	form.findField('airAgencyDetial').setValue("");
	    	                	form.findField('vehAgencyDetial').hide();
        	                	form.findField('vehAgencyDetial').setValue("");
        	                	form.findField('landStowage').hide();
        	                	form.findField('landStowage').setValue("");
        	                	form.findField('partnerOrg').hide();
        	                	form.findField('partnerOrg').setValue("");
	                		}
                		}
	                },{
		                    boxLabel  : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.airAgencyDetial'),
		                    name      : 'custType',
		                    handler:function(val){
		                		if(val.value == true){
	                    			var form =this.up('form').getForm();
	        	                	form.findField('airAgencyDetial').show();
	        	                	form.findField('customerDetial').hide();
		    	                	form.findField('customerDetial').setValue("");
		    	                	form.findField('airDetial').hide();
		    	                	form.findField('airDetial').setValue("");
		    	                	form.findField('vehAgencyDetial').hide();
	        	                	form.findField('vehAgencyDetial').setValue("");
	        	                	form.findField('landStowage').hide();
        	                		form.findField('landStowage').setValue("");
        	                		form.findField('partnerOrg').hide();
            	                	form.findField('partnerOrg').setValue("");
		                		}
	                		}
		                }, {
		                    boxLabel  : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.vehAgencyDetial'),
		                    name      : 'custType',
	    	                handler:function(value){
	    	                	if(value.value == true){
	    	                		var form =this.up('form').getForm();
	    	                		form.findField('vehAgencyDetial').show();
	        	                	form.findField('customerDetial').hide();
		    	                	form.findField('customerDetial').setValue("");
		    	                	form.findField('airDetial').hide();
		    	                	form.findField('airDetial').setValue("");
		    	                	form.findField('airAgencyDetial').hide();
	        	                	form.findField('airAgencyDetial').setValue("");
	        	                	form.findField('landStowage').hide();
        	                		form.findField('landStowage').setValue("");
        	                		form.findField('partnerOrg').hide();
            	                	form.findField('partnerOrg').setValue("");
	    	                	}
	                		}
		                }, {
		                    boxLabel  : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.landStowage'),
		                    name      : 'custType',
	    	                handler:function(value){
	    	                	if(value.value == true){
	    	                		var form =this.up('form').getForm();
	    	                		form.findField('landStowage').show();
	    	                		form.findField('vehAgencyDetial').hide();
	    	                		form.findField('vehAgencyDetial').setValue("");
	        	                	form.findField('customerDetial').hide();
		    	                	form.findField('customerDetial').setValue("");
		    	                	form.findField('airDetial').hide();
		    	                	form.findField('airDetial').setValue("");
		    	                	form.findField('airAgencyDetial').hide();
	        	                	form.findField('airAgencyDetial').setValue("");
	        	                	form.findField('partnerOrg').hide();
	        	                	form.findField('partnerOrg').setValue("");
	    	                	}
	                		}
		                },{//合伙人
		                    boxLabel  : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.partner'),
		                    name      : 'custType',
	    	                handler:function(value){
	    	                	if(value.value == true){
	    	                		var form =this.up('form').getForm();
	    	                		form.findField('partnerOrg').show();
	    	                		form.findField('vehAgencyDetial').hide();
	    	                		form.findField('vehAgencyDetial').setValue("");
	        	                	form.findField('customerDetial').hide();
		    	                	form.findField('customerDetial').setValue("");
		    	                	form.findField('airDetial').hide();
		    	                	form.findField('airDetial').setValue("");
		    	                	form.findField('airAgencyDetial').hide();
	        	                	form.findField('airAgencyDetial').setValue("");
	        	                	form.findField('landStowage').hide();
        	                		form.findField('landStowage').setValue("");
	    	                	}
	                		}
		                 }
		            ]
				},{
					xtype:'commoncustomerselector',
					all:'true',
					fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.customerDetial'),
					colspan:1,
					name:'customerDetial',
					allowBlank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true ,//分页
					singlePeopleFlag : 'Y'
					//name : 'reverseBillWriteoffVo.reverseBillWriteoffDto.customerCode'
						
				 },{	
			        	xtype:'commonairlinesselector',
			        	all:'true',
						fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.airDetial'),
						colspan:1,
						name:'airDetial',
						hidden:true,
						allowBlank:true,
						listWidth:300,//设置下拉框宽度
						isPaging:true //分页
				 },{	
				    	xtype:'commonairagentselector',
				    	all:'true',
						fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.airAgencyDetial'),
						colspan:1,
						name:'airAgencyDetial',
						hidden:true,
						allowBlank:true,
						listWidth:300,//设置下拉框宽度
						isPaging:true //分页		
				 },{	
				    	xtype:'commonvehagencycompselector',
				    	all:'true',
						fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.vehAgencyDetial'),
						colspan:1,
						name:'vehAgencyDetial',
						hidden:true,
						allowBlank:true,
						listWidth:300,//设置下拉框宽度
						isPaging:true //分页				
				},{
					xtype:'commonLdpAgencyCompanySelector',
					fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.landStowage'),
					colspan:1,
					name:'landStowage',
					allowBlank:true,
					hidden:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true 
				 },{//合伙人部门
					xtype:'commonsaledepartmentselector',
					fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.partnerOrg'),
					colspan:1,
					name:'partnerOrg',
					allowBlank:true,
					hidden:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true 
					 },{
					xtype:'combobox',
			    	name:'reverseBillWriteoffVo.reverseBillWriteoffDto.isInit',
			    	fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.isInit'),
					store:FossDataDictionary. getDataDictionaryStore('FOSS_BOOLEAN',null,{
						 'valueCode': '',
	               		 'valueName': writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.all')
					}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value:'',
					editable:false,
					colspan:1
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					colspan:1
				},{

					border: 1,
					xtype:'container',
					columnWidth:1,
					colspan:3,
					defaultType:'button',
					layout:'column',
					items:[{
						  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.reset'),
						  columnWidth:.1,
						  handler:writeoff.reverseWriteoff.reverseWriteoffReset
					  	},{
							xtype:'container',
							border:false,
							html:'&nbsp;',
							columnWidth:.8
						},
					  	{
						  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.query'),
						  columnWidth:.1,
						  cls:'yellow_button',  
						  handler:function(){
							  var form=this.up('form');
							  var me = this;
							  writeoff.reverseWriteoff.reverseWriteoffQuery(form,me,writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_DATE)
						  }
					  	}]
			    
				}]
});


//按核销单号查询 Form
Ext.define('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByWriteoffNo',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:280,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
		xtype:'textfield',
		regex:/^((\s)*(HX)?[0-9]{0,})(\s)*$/i,
		regexText: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.writeoffBillNoRegexText'),
		fieldLabel: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.writeoffBillNo'),
		emptyText: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.writeoffBillNoEmptyText'),
		allowBlank:false,
		name:'reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffBillNo',
		columnWidth:.3
	},{
		xtype:'container',
		border:false,
		html:'&nbsp;',
		height:80,
		columnWidth:1
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.reset'),
			  columnWidth:.1,
			  handler:writeoff.reverseWriteoff.reverseWriteoffReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.09
			},
		  	{
			  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.query'),
			  columnWidth:.1,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  var me = this;
				  writeoff.reverseWriteoff.reverseWriteoffQuery(form,me,writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_WRITEOFFNO)
			  }
		  	}]
	}]
});

//按运单号查询 Form
Ext.define('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByWaybillNo',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:280,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
	         xtype:'textfield',
	         allowBlank:false,
	         fieldLabel: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.waybillNo'),
	         emptyText: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.waybillNoEmptyText'),
//	         height : 80,
//			 width : 600,
	         name:'reverseBillWriteoffVo.reverseBillWriteoffDto.waybillNo',
	         columnWidth:.3
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			height:80,
			columnWidth:1
		},{

			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.reset'),
				  columnWidth:.1,
				  handler:writeoff.reverseWriteoff.reverseWriteoffReset
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.09
				},
			  	{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					  var form=this.up('form');
					  var me = this;
					  writeoff.reverseWriteoff.reverseWriteoffQuery(form,me,writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_WAYBILLNO)
				  }
			  	}]
		
		}]
});


//按预付单号查询 Form
Ext.define('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByAdvanceNo',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:280,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
	         xtype:'textfield',
	         regex:/^((\s)*(UF)?[0-9]{0,})(\s)*$/i,
	         allowBlank:false,
	         fieldLabel: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.advancesNo'),
	         emptyText: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.advanceBillNoEmptyText'),
	         name:'reverseBillWriteoffVo.reverseBillWriteoffDto.advancePayBillNo',
	         columnWidth:.3
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			height:80,
			columnWidth:1
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.reset'),
				  columnWidth:.1,
				  handler:writeoff.reverseWriteoff.reverseWriteoffReset
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.09
				},
			  	{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					  var form=this.up('form');
					  var me = this;
					  writeoff.reverseWriteoff.reverseWriteoffQuery(form,me,writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_ADVBILLNO)
				  }
			}]
		}]
});


//按应付单号查询 Form
Ext.define('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByPayableNo',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:280,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
	         xtype:'textfield',
	         regex:/^((\s)*(YF)?[0-9]{0,})(\s)*$/i,
	         allowBlank:false,
	         fieldLabel: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.payableNo'),
	         emptyText: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.payableBillNoEmptyText'),
	         name:'reverseBillWriteoffVo.reverseBillWriteoffDto.payableBillNo',
	         columnWidth:.3
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			height:80,
			columnWidth:1
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.reset'),
				  columnWidth:.1,
				  handler:writeoff.reverseWriteoff.reverseWriteoffReset
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.09
				},
			  	{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					  var form=this.up('form');
					  var me = this;
					  writeoff.reverseWriteoff.reverseWriteoffQuery(form,me,writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_PAYBILLNO)
				}
			}]
		}]
});

//按预付单号查询 Form
Ext.define('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByDepNo',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:280,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
	         xtype:'textfield',
	         regex : /^((US)[0-9]{7,10}[;,]?)+$/i,
			 regexText : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.depositErrorRegText'),
	         allowBlank:false,
	         fieldLabel: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.depositNo'),
	         emptyText: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.depositNoEmptyText'),
	         name:'reverseBillWriteoffVo.reverseBillWriteoffDto.depositNo',
	         columnWidth:.3
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			height:80,
			columnWidth:1
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.reset'),
				  columnWidth:.1,
				  handler:writeoff.reverseWriteoff.reverseWriteoffReset
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.09
				},
			  	{
				  text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.query'),
				  columnWidth:.1,
				  cls:'yellow_button',  
				  handler:function(){
					  var form=this.up('form');
					  var me = this;
					  writeoff.reverseWriteoff.reverseWriteoffQuery(form,me,writeoff.reverseWriteoff.STLREVERSEWRITEOFF_QUERY_BY_DEPBILLNO)
				}
			}]
		}]
});

Ext.define('Foss.StlRevWriteOff.StlRevWriteOffQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	//width : 1060,
	height : 280,
	items : [ {
		title: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.queryByDate'),
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByDate')
               ]
	}, {
		title: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.queryByWriteoffNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByWriteoffNo')
               ]
	},{
		title: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.queryByWaybillNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByWaybillNo')
               ]
	},{
		title: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.queryByAdvanceNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByAdvanceNo')
               ]
	},{
		title: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.queryByPayableNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByPayableNo')
               ]
	},{
		title: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.queryByDepositNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
       Ext.create('Foss_StlRevWriteOff_StlRevWriteOffQueryInfoByDepNo')
       ]
	}]
});

//核销单model
Ext.define('Foss.StlRevWriteOff.BillWriteoffEntityModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'writeoffBillNo'
	},{
		name:'beginNo'
	},{
		name:'endNo'
	},{
		name:'beginWaybillNo'
	},{
		name:'endWaybillNo'
	},{
		name:'customerName'
	},{
		name:'customerCode'
	},{
		name:'amount',
		type:'long'
	},{
		name:'writeoffType'
	},{
		name:'orgName'
	},{
		name:'createUserName'
	},{
		name:'createUserCode'
	},{
		name:'writeoffTime',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'accountDate',
		type:'date',
		convert:stl.longToDateConvert
	},{
		name:'isRedBack'
	},{
		name:'active'
	},{
		name:'orgCode'
	},{
		name:'createUserCode'
	},{
		name:'isInit'
	},{
		//核销方式   --毛建强  2012-1-4
		name:'createType'
	}]
});

//核销单Store
Ext.define('Foss.StlRevWriteOff.BillWriteoffEntityStore',{
	extend:'Ext.data.Store',
	model:'Foss.StlRevWriteOff.BillWriteoffEntityModel',
	pageSize: writeoff.reverseWriteoff.STLREVERSEWRITEOFF_ONEPAGESIZE,
	//autoLoad:true,
	sorters: [{
	     property: 'beginNo',
	     direction: 'ASC'
	 }],
	proxy:{
		type:'ajax',
		//url:'../writeoff/queryBillWriteoffEntityListByParams.action',
		url:writeoff.realPath('queryBillWriteoffEntityListByParams.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'reverseBillWriteoffVo.reverseBillWriteoffDto.billWriteoffEntityList',
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

//核销单列表
Ext.define('Foss.StlRevcWriteOff.StlRevWriteOffQueryInfoGrid',{
	extend:'Ext.grid.Panel',
    title: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.StlRevWriteOffQueryInfoGridTitle'),
    frame:true,
	height:500,
	hidden:true,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.StlRevWriteOff.BillWriteoffEntityStore'),
	//store:null,
    hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	writeoffAmount:null,//核销总金额
	getWriteoffAmount:function(){
		var me=this;
		if(Ext.isEmpty(me.writeoffAmount)){
			me.writeoffAmount=0;
		}
		return me.writeoffAmount;
	},
	totalRows:null,//总条数
	getTotalRows:function(){
		var me=this;
		if(Ext.isEmpty(me.totalRows)){
			me.totalRows=0;
		}
		return me.totalRows;
	},
	columns:[{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.writeoffBillNo'),
		dataIndex: 'writeoffBillNo'
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.beginNo'),
		dataIndex: 'beginNo'
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.endNo'),
		dataIndex: 'endNo'
//	},{
//		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.beginWaybillNo'),
//		dataIndex: 'beginWaybillNo'
//	},{
//		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.endWaybillNo'),
//		dataIndex: 'endWaybillNo'		
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.orgName'),
		dataIndex: 'orgName'				
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.orgCode'),
		dataIndex: 'orgCode'				
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.customerName'),
		dataIndex: 'customerName'		
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.customerCode'),
		dataIndex: 'customerCode'
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.writeoffAmount'),
		dataIndex: 'amount',
		align: 'right',  
		renderer:stl.amountFormat	
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.writeoffType'),
		dataIndex: 'writeoffType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_WRITEOFF__WRITEOFF_TYPE);
    		return displayField;
    	}
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.createType'),
		dataIndex: 'createType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__CREATE_TYPE);
    		return displayField;
    	}	
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.createUserName'),
		dataIndex: 'createUserName' 
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.createUserCode'),
		dataIndex: 'createUserCode' 
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.isRedBack'),
		dataIndex: 'isRedBack',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.SETTLEMENT__IS_RED_BACK);
    		return displayField;
		}
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.active'),
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.writeoffTime'),
		dataIndex: 'writeoffTime',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.accountTime'),
		dataIndex: 'accountDate',
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 
	},{
		header: writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.isInit'),
		dataIndex: 'isInit',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
		}
	}],
	viewConfig: {
		enableTextSelection: true
	},
	constructor:function(config){
	
		var me = this;
		me.store=Ext.create('Foss.StlRevWriteOff.BillWriteoffEntityStore');

		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			},{
				xtype :'button',
				text : writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.export'),
				columnWidth :.06,
				handler: writeoff.reverseWriteoff.reverseWriteoffBillExport,
				hidden: !writeoff.reverseWriteoff.isPermission('/stl-web/writeoff/exportReverseWriteoffBill.action')
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'displayfield',
				allowBlank:true,
				itemId: 'totalRows',
				name:'totalRows',
				columnWidth:.1,
				labelWidth:100,
				fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.totalRows'),
				value:me.getTotalRows()
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.05
			},{
				xtype:'displayfield',
				allowBlank:true,
				itemId: 'writeoffAmount',
				name:'writeoffAmount',
				columnWidth:.1,
				labelWidth:95,
				fieldLabel:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.writeoffAmount'),
				value:me.getWriteoffAmount()
					
			},{
				margin :'0 0',
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.6,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:100
				})
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				height:1,
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
			},{
				xtype:'button',
				text:writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.reWriteoff'),
				hidden: !writeoff.reverseWriteoff.isPermission('/stl-web/writeoff/reverseBillWriteoff.action'),
				columnWidth:.06,
				handler:function(){
					var me = this;	
					//获取参数
					var grid = writeoff.reverseWriteoff.getReverseWriteoffPageGridObj();
					var selections = grid.getSelectionModel().getSelection();
					
					//如果未选中数据，提示至少选择一条记录进行操作
					if(selections.length==0){
						Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.reWriteoffFailAlertByNoData'));
						return false;
					}
					
					var yesFn=function(){
						
						var paramstemp=grid.store.submitParams;		
						
						//定义批量更新对账单状态的number
						var writeoffBillNos = [];
						for(var i=0;i<selections.length;i++){
							
							//红单无法再次被核销
							if(selections[i].get('isRedBack')==writeoff.reverseWriteoff.SETTLEMENT__IS_RED_BACK__YES){
								Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.billWriteoff')+selections[i].get('writeoffBillNo')+writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.reWriteoffFailAlertByRed'));
								return false;
							}
							
							//无效单无法再次被核销
							if(selections[i].get('active')==writeoff.reverseWriteoff.INACTIVE){
								Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.billWriteoff')+selections[i].get('writeoffBillNo')+writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.reWriteoffFailAlertByInvaild'));
								return false;
							}
							
							//判断核销方式  --手动生成可以反核销，自动不可以
							if(selections[i].get('createType')!=writeoff.reverseWriteoff.SETTLEMENT__CREATE_TYPE__MANUAL){
								Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.billWriteoff')+selections[i].get('writeoffBillNo')+writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.reWriteoffFailAlertByAutoCreateType'));
								return false;
							}
							writeoffBillNos.push(selections[i].get('writeoffBillNo'));
						}
							
						Ext.apply(paramstemp, {
							'reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffBillNos':writeoffBillNos
						});
						
						me.disable(false);
						//10秒后自动解除灰掉效果
						setTimeout(function() {
						me.enable(true);
						}, 10000);
						
						//调用
						Ext.Ajax.request({
							//url:'../writeoff/reverseBillWriteoff.action',
							url:writeoff.realPath('reverseBillWriteoff.action'),
							params:paramstemp,
							success:function(response){
								grid.store.load({
								      callback: function(records, operation, success) {
										var result =   Ext.decode(operation.response.responseText);
										
										toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
										toolBar.getComponent('totalRows').setValue(result.reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffTotalRows);
										toolBar.getComponent('writeoffAmount').setValue(stl.amountFormat(result.reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffTotalAmout));
								       }
								    }); 
								Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.reWriteoffSuccess'));								
							},exception:function(response){
								//TODO
								var exceptionResult =   Ext.decode(response.responseText);
								Ext.Msg.alert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.common.alert'),exceptionResult.message);
								grid.store.load({
								      callback: function(records, operation, success) {
										var result =  Ext.decode(operation.response.responseText);
										
										toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
										toolBar.getComponent('totalRows').setValue(result.reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffTotalRows);
										toolBar.getComponent('writeoffAmount').setValue(stl.amountFormat(result.reverseBillWriteoffVo.reverseBillWriteoffDto.writeoffTotalAmout));
								       }
								    }); 
								
							}
						});	
					};
					var noFn=function(){
					 	return false;
					};
					writeoff.reverseWriteoff.reverseWriteoffConfirmAlert(writeoff.reverseWriteoff.i18n('foss.stl.writeoff.reverseWriteoff.isConfirmReWriteoff'),yesFn,noFn);
				}
			
			}]
			 }];	
		me.callParent();
	}
});



//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_writeoff-reverseWriteOff_content')) {
		return;
	} 
	
	//查询tab
	var StlRevWriteOffQueryInfoTab = Ext.create('Foss.StlRevWriteOff.StlRevWriteOffQueryInfoTab');	
	
	//核销单列表
	var StlRevWriteOffQuerInfoGrid = Ext.create('Foss.StlRevcWriteOff.StlRevWriteOffQueryInfoGrid');

	Ext.create('Ext.panel.Panel',{
		id: 'T_writeoff-reverseWriteOff_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		
		//获得查询FORM
		getQueryTab : function() {
			return StlRevWriteOffQueryInfoTab;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return StlRevWriteOffQuerInfoGrid;
		},
		
		items: [StlRevWriteOffQueryInfoTab,StlRevWriteOffQuerInfoGrid],
		renderTo: 'T_writeoff-reverseWriteOff-body'
	});
});