consumer.billClaim.DEFLAULT_TIME_INTEVAL= 31;  // 默认时间间隔相差31天
consumer.billClaim.DEFLAULT_TIME_INTEVAL_MAX = 60;  // 默认时间间隔最大不超过60天
consumer.billClaim.ONEPAGESIZE = 50; // 每页显示的条数

consumer.billClaim.QUERY_BY_DATE='QBD';// 按时间查询
consumer.billClaim.QUERY_BY_WAYBILL_NOS='QWNS';// 按来源单号查询

/**
 * 
 * @param {} 产品类型
 * @return {}此处如果有全选，则需要转化下
 */
consumer.billClaim.convertProductCode = function(productCodes){
	if(!Ext.isEmpty(productCodes)){
		var productCodeList = [];
		for(var i=0;i<productCodes.length;i++){
			//如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if(Ext.isEmpty(productCodes[i])){
				productCodeList=[];
				break;
			}else{
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	}else{
		return [];
	}
}

/**
 * Form重置方法
 */
consumer.billClaim.billClaimQueryReset=function(){
	this.up('form').getForm().reset();
}


/**
 * Form查询方法
 */
consumer.billClaim.billClaimQuery=function(me,queryType){
	var form=me.getForm();
	
	if(consumer.billClaim.QUERY_BY_DATE==queryType){
		var startCreateTime = form.findField('billClaimVo.billClaimQueryDto.startCreateTime').getValue();
		var endCreateTime = form.findField('billClaimVo.billClaimQueryDto.endCreateTime').getValue();
		
		if(startCreateTime==null || startCreateTime==''){
			
			Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.startCreateTimeIsNotNull'));
			return false;
		}

		if(endCreateTime==null || endCreateTime==''){
			Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.endCreateTimeIsNotNull'));
			return false;
		}
		var compareTwoDate = stl.compareTwoDate(startCreateTime,endCreateTime);
		if(compareTwoDate>consumer.billClaim.DEFLAULT_TIME_INTEVAL_MAX){
			Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.startToEndCreateTimeNotMax'));
			return false;
		}else if(compareTwoDate<1){
			Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.endCreateTimeIsNotBefoerStartCreateTime'));
			return false;
		}
	}

	
	var grid = Ext.getCmp('T_consumer-billClaim_content').getBillClaimGrid();
	if(form.isValid()){
		var params=consumer.billClaim.setQueryParams(form,queryType);
	
		// 参数设置异常时直接返回
		if(!params){
			return false;
		}
		
		// 设置查询参数
		grid.store.setSubmitParams(params);
		// 设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
				Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),rawData.message);
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				
				toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
				toolBar.getComponent('billClaimTotalRows').setValue(result.billClaimVo.billClaimResultDto.billClaimTotalRows);
				if(result.billClaimVo.billClaimResultDto.billClaimEntityList.length>0){
					grid.show();
				}else{
					Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.common.noDataUnderThisQuery'));
					return false;
				}
	    	}
	       }
	    }); 
	}else {
		Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.common.validateFailAlert'));
	}
}


// 设置参数
consumer.billClaim.setQueryParams=function(form,queryType){
	// 定义查询参数
	var params={};
	
	// 按日期查询
	if(consumer.billClaim.QUERY_BY_DATE==queryType){
		
		// 获取FORM所有值
		params = form.getValues();
		//获取产品类型进行转化
		var productCode= form.findField('billClaimVo.billClaimQueryDto.productCode').getValue();
		//重新封装产品类型
		Ext.apply(params, {
			'billClaimVo.billClaimQueryDto.productCode' : consumer.billClaim.convertProductCode(productCode)
		});
		
		var customerDetial = form.findField('customerDetial').getValue();
		var airDetial = form.findField('airDetial').getValue();
		var airAgencyDetial = form.findField('airAgencyDetial').getValue();
		var vehAgencyDetial = form.findField('vehAgencyDetial').getValue();
		var landStowage = form.findField('landStowage').getValue();
		if(customerDetial!=null&&customerDetial!=''){
			Ext.apply(params, {
				'billClaimVo.billClaimQueryDto.customerCode' : customerDetial
			});
		}else if(airDetial!=null&&airDetial!=''){
			Ext.apply(params, {
				'billClaimVo.billClaimQueryDto.customerCode' : airDetial
			});
		}else if(airAgencyDetial!=null&&airAgencyDetial!=''){
			Ext.apply(params, {
				'billClaimVo.billClaimQueryDto.customerCode' : airAgencyDetial
			});
		}else if(vehAgencyDetial!=null&&vehAgencyDetial!=''){
			Ext.apply(params, {
				'billClaimVo.billClaimQueryDto.customerCode' : vehAgencyDetial
			});
		}else if(!Ext.isEmpty(landStowage)){
			Ext.apply(params, {
				'billClaimVo.billClaimQueryDto.customerCode' : landStowage
			});
		}
	
	// 按运单号查询
	}else if(consumer.billClaim.QUERY_BY_WAYBILL_NOS==queryType){
		
		// 获取FORM所有值
		params = form.getValues();
		
		var wayBillNos = form.findField('wayBillNos').getValue();
		var wayBillNosArray_tmp = stl.splitToArray(wayBillNos);
		var wayBillNosArray=new Array();
		
		for(var i=0;i<wayBillNosArray_tmp.length;i++){
			if(wayBillNosArray_tmp[i].trim()!=''){
				wayBillNosArray.push(wayBillNosArray_tmp[i].trim());
			} 
		}
		 
		if(wayBillNosArray.length==0){
			Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.wayBillNosNotInputAlert'));
		 	return false;
		}
		if(wayBillNosArray.length>10){
			Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.wayBillNosInputToMoreAlert'));
		 	return false;
		}
		
		Ext.apply(params,{
			'billClaimVo.billClaimQueryDto.wayBillNosArray' : wayBillNosArray
		});
	}
	return params;
}

//退回理赔单
consumer.billClaim.returnBillClaim = function(grid, rowIndex, colIndex){
	
	// 获取选中的理赔单数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	
	// 退回中或者已退回的理赔单不能退回
	if(selection.get('status')!=consumer.billClaim.BILL_CLAIM__RETURN__STATUS){
		Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.statusIsReturned'));
		return false;
	}
	
	Ext.MessageBox.show({
        title: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.returnMessageTitle'),
        msg: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.returnMessageMsg'),
        width:300,
        buttons: Ext.MessageBox.OKCANCEL,
        buttonText:{ 
            ok: consumer.billClaim.i18n('foss.stl.consumer.common.OK'),
            cancel:consumer.billClaim.i18n('foss.stl.consumer.common.cancel')
        },
        multiline: true,
        fn: function(e,text){
       	 if(e=='ok'){
       	
       		//退回请求 
  			Ext.Ajax.request({
       			url:consumer.realPath('returnBillClaim.action'),
       			params:{
       				'billClaimVo.billClaimQueryDto.waybillNo':selection.data.waybillNo,
       				'billClaimVo.billClaimQueryDto.rtnReason':text
       			},
       			success:function(response){
       				
       				// 重新载入数据
       				grid.store.load({
       					callback: function(records, operation, success) {
       						var result =   Ext.decode(operation.response.responseText);
       						
       						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
       						toolBar.getComponent('billClaimTotalRows').setValue(result.billClaimVo.billClaimResultDto.billClaimTotalRows);
       				       }
       				});
       				
       				Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.returnSuccessAlert'));
       			},
       			exception:function(response){
       				var result = Ext.decode(response.responseText);	
       				Ext.Msg.alert(consumer.billClaim.i18n('foss.stl.consumer.common.warmTips'),result.message);
       				
       			    // 重新载入数据
       				grid.store.load({
       					callback: function(records, operation, success) {
       						var result =   Ext.decode(operation.response.responseText);
       						
       						toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
       						toolBar.getComponent('billClaimTotalRows').setValue(result.billClaimVo.billClaimResultDto.billClaimTotalRows);
       				       }
       				});
       			}
       		});	
       	 }else{
       		 return false;
       	 }
        }
    });
}

// 按日期查询 Form
Ext.define('Foss.billClaim.BillClaimQueryInfoByDate',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:240,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		labelAlign:'right',
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
					name : 'billClaimVo.billClaimQueryDto.startCreateTime',
					fieldLabel : consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.startCreateTime'),
					labelWidth : 110,
					value : stl.getTargetDate(new Date(),
							-consumer.billClaim.DEFLAULT_TIME_INTEVAL),
					format : 'Y-m-d',
					allowBlank : false,
					editable:false
					//columnWidth : .33
				},
				{
					xtype : 'datefield',
					name : 'billClaimVo.billClaimQueryDto.endCreateTime',
					fieldLabel : consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.endCreateTime'),
					labelWidth : 110,
					format : 'Y-m-d',
					value : new Date(),
					allowBlank : false,
					maxValue : new Date(),
					editable:false
				},
				{					
					xtype : 'combobox',
					name : 'billClaimVo.billClaimQueryDto.paymentCategories',
					fieldLabel : consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.paymentCategories'),
					store:FossDataDictionary.getDataDictionaryStore(settlementDict.BILL_CLAIM__PAYMENT_CATEGORIES,null,{
						 'valueCode': '',
	               		 'valueName': consumer.billClaim.i18n('foss.stl.consumer.common.all')
					}),
					queryModel : 'local',
					displayField : 'valueName',
					valueField : 'valueCode',
					value:''
				},{

					xtype      : 'fieldcontainer', 
					defaultType: 'radiofield',
					layout:'table',
					colspan:1,
		            items: [{
	                    boxLabel  : consumer.billClaim.i18n('foss.stl.consumer.common.customerDetial'),
	                    name      : 'color',
	                    checked:true,
	                    inputValue: '11',
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
	                		}
                		}
	                },{
	                    boxLabel  : consumer.billClaim.i18n('foss.stl.consumer.common.airDetial'),
	                    name      : 'color',
	                    inputValue: '22',
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
	                		}
                		}
	                },{
		                    boxLabel  : consumer.billClaim.i18n('foss.stl.consumer.common.airAgencyDetial'),
		                    name      : 'color',
		                    inputValue: '33',
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
		                		}
	                		}
		                }, {
		                    boxLabel  : consumer.billClaim.i18n('foss.stl.consumer.common.vehAgencyDetial'),
		                    name      : 'color',
		                    inputValue: '44',
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
	    	                	}
	                		}
		                }, {
		                    boxLabel  : consumer.billClaim.i18n('foss.stl.consumer.common.landStowage'),
		                    name      : 'color',
		                    inputValue: '55',
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
	    	                	}
	                		}
		                }
		            ]
				
				},{
			    	xtype:'combobox',
			    	name:'billClaimVo.billClaimQueryDto.productCode',
			    	labelWidth : 110,
			    	fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.productCode'),
					store:Ext.create('Foss.pkp.ProductStore'),
					multiSelect : true,
				    queryMode: 'local', 	
					displayField:'name',
					valueField:'code',
					colspan:2
			  	},{
					xtype:'commoncustomerselector',
					all:'true',
					singlePeopleFlag : 'Y',
					fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.common.customerDetial'),
					labelWidth : 110,
					colspan:1,
					name:'customerDetial',
					allowBlank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true //分页		
			 	},{	
		        	xtype:'commonairlinesselector',
		        	all:'true',
					fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.common.airDetial'),
					labelWidth : 110,
					colspan:1,
					name:'airDetial',
					hidden:true,
					allowBlank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true //分页
				 },{	
			    	xtype:'commonairagentselector',
			    	all:'true',
					fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.common.airAgencyDetial'),
					labelWidth : 110,
					colspan:1,
					name:'airAgencyDetial',
					hidden:true,
					allowBlank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true //分页		
				 },{	
			    	xtype:'commonvehagencycompselector',
			    	all:'true',
					fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.common.vehAgencyDetial'),
					labelWidth : 110,
					colspan:1,
					name:'vehAgencyDetial',
					hidden:true,
					allowBlank:true,
					listWidth:300,//设置下拉框宽度
					isPaging:true //分页	
				},{
					xtype:'commonLdpAgencyCompanySelector',
					fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.common.landStowage'),
					labelWidth : 110,
					colspan:1,
					name:'landStowage',
					allowBlank:true,
					listWidth:300,//设置下拉框宽度
					hidden:true,
					isPaging:true //分页		
				 },{
					xtype : 'dynamicorgcombselector',
					fieldLabel : consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.payableOrg'),
					labelWidth : 110,
					name : 'billClaimVo.billClaimQueryDto.payableOrgCode'
					//columnWidth : .3
				},{					
					xtype:'container',
					border:false,
					html:'&nbsp;'
				},{
					border: 1,
					xtype:'container',
					columnWidth:1,
					colspan:3,
					defaultType:'button',
					layout:'column',
					items:[{
						  text:consumer.billClaim.i18n('foss.stl.consumer.common.reset'), 
						  columnWidth:.1,
						  handler:consumer.billClaim.billClaimQueryReset
					  	},{
							xtype:'container',
							border:false,
							html:'&nbsp;',
							columnWidth:.8
						},
					  	{
						  text:consumer.billClaim.i18n('foss.stl.consumer.common.query'), 
						  columnWidth:.1,
						  cls:'yellow_button',  
						  handler:function(){
							  var form=this.up('form');
							  consumer.billClaim.billClaimQuery(form,consumer.billClaim.QUERY_BY_DATE);
						  }
					  	}]
				}]			
});

//按运单号查询 Form
Ext.define('Foss.billClaim.BillClaimQueryInfoByNos',{
	extend:'Ext.form.Panel',
	frame:false,
	columnWidth:1,
	height:240,
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
		xtype:'textarea',
		fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.common.waybillNo'), 
		regex:/^((\s)*[A-Za-z0-9]{8,20}[;,])*((\s)*[A-Za-z0-9]{8,20}[;,]?)(\s)*$/i,
		//regexText:'输入以FK开头加数字或只输入数字，必须是7-10位数字以， 或；隔开',
		emptyText: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.wayBillNosEmptyText'), 
		height : 80,
		allowBlank:false,
		name:'wayBillNos',
		columnWidth:.4
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:consumer.billClaim.i18n('foss.stl.consumer.common.reset'), 
			  columnWidth:.1,
			  handler:consumer.billClaim.billClaimQueryReset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.195
			},
		  	{
		      text:consumer.billClaim.i18n('foss.stl.consumer.common.query'), 
			  columnWidth:.1,
			  cls:'yellow_button',  
			  handler:function(){
				  var form=this.up('form');
				  consumer.billClaim.billClaimQuery(form,consumer.billClaim.QUERY_BY_WAYBILL_NOS);
			  }
		  	}]
	}]
});

// 查询tab
Ext.define('Foss.billClaim.BillClaimQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	//width : 1060,
	height : 240,
	items : [ {
		title: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.BillClaimQueryInfoByDateTitle'), 
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.billClaim.BillClaimQueryInfoByDate')
               ]
	}, {
		title: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.BillClaimQueryInfoByNosTitle'), 
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.billClaim.BillClaimQueryInfoByNos')
               ]
	}]
});

/**
 * 理赔单模型
 */
Ext.define('Foss.billClaim.BillClaimGridModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'waybillNo'
	},{	
		name:'customerCode'
	},{
		name:'customerName'
	},{
		name:'amount'		
	},{
		name:'paymentCategories'		
	},{
		name:'payableOrgCode'
	},{
		name:'payableOrgName'
	},{
		name:'createTime',
		type:'Date',
		convert:stl.longToDateConvert
	},{
		name:'createUserCode'
	},{	
		name:'createUserName'
	},{	
		name:'modifyTime',
		type:'Date',
		convert:stl.longToDateConvert
	},{	
		name:'modifyUserCode'
	},{
		name:'modifyUserName'
	},{
		name:'type'
	},{
		name:'status'
	},{
		name:'active'
	},{
		name:'productCode'
	}]
});

/**
 * 理赔单表格store
 */
Ext.define('Foss.billClaim.BillClaimGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.billClaim.BillClaimGridModel',
	pageSize: consumer.billClaim.ONEPAGESIZE ,
	sorters: [{
	     property: 'createTime',
	     direction: 'DESC'
	 }],
	proxy:{
		type:'ajax',
		url:consumer.realPath('queryBillClaimList.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'billClaimVo.billClaimResultDto.billClaimEntityList',
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

//理赔单列表
Ext.define('Foss.billClaim.BillClaimGrid',{
	extend:'Ext.grid.Panel',
    title: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.BillClaimGridTitle'), 
    frame:true,
    hidden:true,
	height:500,
	//selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.billClaim.BillClaimGridStore'),
    viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
	// store:null,
    hidMeParams:null,
	setHidMeParams:function(params){
		this.hidMeParams=params;
	},
	getHidMeParams:function(){
		return this.hidMeParams;
	},
	billClaimTotalRows:null,// 总条数
	getBillClaimTotalRows:function(){
		var me=this;
		if(Ext.isEmpty(me.billClaimTotalRows)){
			me.billClaimTotalRows=0;
		}
		return me.billClaimTotalRows;
	},
	columns:[{
    	xtype:'actioncolumn',
    	header:consumer.billClaim.i18n('foss.stl.consumer.common.actionColumn'), 
    	width:70,
    	align: 'center',
    	items:[{
    		iconCls : 'foss_icons_bse_applyReturn',
			tooltip:consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.applyReturn'), 
			handler:function(grid, rowIndex, colIndex){
				consumer.billClaim.returnBillClaim(grid, rowIndex, colIndex)
			},
			getClass:function(v,m,r,rowIndex){
				if(r.get('status')!=consumer.billClaim.BILL_CLAIM__RETURN__STATUS||!consumer.billClaim.isPermission('/stl-web/consumer/returnBillClaim.action')){
					return 'statementBill_hide';
				}else{
					return 'foss_icons_bse_applyReturn';
				}
				
			}
    	}]
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.waybillNo'), 
		dataIndex: 'waybillNo'
	}, {
		header :consumer.billClaim.i18n('foss.stl.pay.queryBillPayable.productCode'),
		dataIndex : 'productCode',
		renderer : function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.customerName'), 
		dataIndex: 'customerName'		
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.customerCode'), 
		dataIndex: 'customerCode'
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.payableOrgName'), 
		dataIndex: 'payableOrgName'		
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.payableOrgCode'), 
		dataIndex: 'payableOrgCode'
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.amount'), 
		dataIndex: 'amount',
		align: 'right', 
		renderer:stl.amountFormat		
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.paymentCategories'), 
		dataIndex: 'paymentCategories',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_CLAIM__PAYMENT_CATEGORIES);
    		return displayField;
    	}
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.queryBillClaim.type'), 
		dataIndex: 'type',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_CLAIM__TYPE);
    		return displayField;
    	}	
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.active'), 
	    hidden:true,
		dataIndex: 'active',
		renderer:function(value){
			var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.FOSS_ACTIVE);
    		return displayField;
		}
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.createTime'), 
		dataIndex: 'createTime',
	    //hidden:true,
		width:140,
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 		
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.createUserName'), 
	    //hidden:true,
		dataIndex: 'createUserName'	
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.modifyTime'), 
	    //hidden:true,
		dataIndex: 'modifyTime',
		width:140,
		renderer:function(value){
			return stl.dateFormat(value, 'Y-m-d H:i:s');
		} 		
	},{
		header: consumer.billClaim.i18n('foss.stl.consumer.common.modifyUserName'), 
	    //hidden:true,
		dataIndex: 'modifyUserName'	
	}],
	constructor:function(config){
	
		var me = this;
		me.store=Ext.create('Foss.billClaim.BillClaimGridStore');

		me.dockedItems =[{
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
				itemId: 'billClaimTotalRows',
				name:'billClaimTotalRows',
				columnWidth:.1,
				labelWidth:100,
				fieldLabel:consumer.billClaim.i18n('foss.stl.consumer.common.billClaimTotalRows'), 
				value:me.getBillClaimTotalRows()
			}]
		}];	
		me.callParent();
	}
});

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_consumer-billClaim_content')) {
		return;
	} 
	
	//查询日志tab
	var billClaimQueryInfoTab = Ext.create('Foss.billClaim.BillClaimQueryInfoTab');
	
	//显示日志grid
	var billClaimGrid = Ext.create('Foss.billClaim.BillClaimGrid');
	
	Ext.create('Ext.panel.Panel',{
		id: 'T_consumer-billClaim_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getBillClaimQueryInfoTab:function(){
			return billClaimQueryInfoTab;
		},
		getBillClaimGrid:function(){
			return billClaimGrid;
		},
		items: [billClaimQueryInfoTab,billClaimGrid],
		renderTo: 'T_consumer-billClaim-body'
	});
});