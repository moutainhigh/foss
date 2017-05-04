//公共属性
/**
 * 请求超时时间
 */
consumer.codFundBillPaid.AJAX_TIMEOUT = 2*60*60; //默认2小时

 /**
  * 总条数
  * @type Number
  */
consumer.codFundBillPaid.NUM_ALL = 0;
/**
 * 总条数
 * @type Number
 */
consumer.codFundBillPaid.AMOUNT_ALL = 0;
/**
 * 冻结条数
 * @type Number
 */
consumer.codFundBillPaid.NUM_FF = 0;
/**
 * 冻结总金额
 * @type Number
 */
consumer.codFundBillPaid.AMOUNT_FF = 0;

/**
 * 通过图标操作
 * @type String
 */
consumer.codFundBillPaid.IMGBUTTON_WAY_OPERATE = 'imgButtonWay';
/**
 * 通过多选checkbox操作
 * @type String
 */
consumer.codFundBillPaid.CHECKBOX_WAY_OPERATE = 'checkboxWay';
/**
 * 冻结操作
 * @type String
 */
consumer.codFundBillPaid.FREEZECOD_STATU_OPERATE = 'freezeCOD';
/**
 * 取消冻结操作
 * @type String
 */
consumer.codFundBillPaid.RELEASECOD_STATU_OPERATE = 'releaseCOD';

/**
 * 全选状态
 * @type String
 */
consumer.codFundBillPaid.COD_ALL_CHECKBOX_STATUS = 'ALL_CHECKBOX';

/**
 * 重置全选checkbox
 */
consumer.codFundBillPaid.resetFreezeButton=function() {
	Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').setValue(false);
	Ext.getCmp('Foss_codFundBillPaid_AllNoFreezeStatu_ID').setValue(false);
}

/**
 * 设置排序参数
 */
consumer.codFundBillPaid.setQuerySortParam=function (submitParams,operation){
	// 默认状态排序
	Ext.apply(submitParams, {
		      "ajaxState":operation==null?'query':null,//通过查询方式，不需要设置排序参数
    		  "sortProperty":operation!=null&&operation.sorters.length>0?operation.sorters[0].property:"status",
			  "sortDirection":operation!=null&&operation.sorters.length>0?operation.sorters[0].direction:"ASC"
        }); 
}

/**
 * 查询
 * @param {} thisForm
 */
consumer.codFundBillPaid.queryCodFundBillPaid=function(thisForm){
	var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
	grid.getLoadMask().show();
	var store = grid.getStore();
	//查询
	
	if(store){
		if(grid.isHidden()){
			grid.show();
		}
		grid.getLoadMask().show();
		
		if (thisForm) {
			var form = thisForm.up('form').getForm();
			// 设置查询参数
			grid.store.setSubmitParams(form.getValues());
			
			// 设置排序参数
			consumer.codFundBillPaid.setQuerySortParam(grid.store.submitParams,null);
		}
		
		// 加载第一页数据
		store.loadPage(1,{
					callback : function(records, operation, success) {
						var rawData = store.proxy.reader.rawData;
						if(!success && !rawData.isException){
							Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),rawData.message);
							grid.getLoadMask().hide();
							return false;
						}
						
						if(success){
							var result = Ext.decode(operation.response.responseText);  
							if(!Ext.isEmpty(result.codFundBillVO.cods)&& result.codFundBillVO.cods.length>0){
								
								consumer.codFundBillPaid.NUM_ALL = result.totalCount;
								consumer.codFundBillPaid.AMOUNT_ALL = result.codFundBillVO.totalAmount;
								consumer.codFundBillPaid.NUM_FF = result.codFundBillVO.freezeTotalCount;
								consumer.codFundBillPaid.AMOUNT_FF = result.codFundBillVO.freezeTotalAmount;
								
					      	}else{
					      		consumer.codFundBillPaid.NUM_ALL = 0;
								consumer.codFundBillPaid.AMOUNT_ALL = 0;
								consumer.codFundBillPaid.NUM_FF = 0;
								consumer.codFundBillPaid.AMOUNT_FF = 0;
					      	}
							consumer.codFundBillPaid.loadCodSumUI();
							
							consumer.codFundBillPaid.resetFreezeButton();
							grid.show();
						}
						
					}
				});
		
		grid.getLoadMask().hide();
	}
	
	grid.show();
}

/**
 * 加载合计数据UI
 */
consumer.codFundBillPaid.loadCodSumUI = function() {
	var sum = Ext.getCmp('Foss_codFundBillPaid_Sum_ID');
	sum.setText(consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.total')+"&nbsp;"+
			consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.allNum') +": " + consumer.codFundBillPaid.NUM_ALL + "&nbsp;&nbsp;"+
			consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.allAmount') + ": " + consumer.codFundBillPaid.AMOUNT_ALL + "&nbsp;&nbsp;"+
			consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.frozenNum')+ ": " + consumer.codFundBillPaid.NUM_FF + "&nbsp;&nbsp;"+
			consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.frozenAllAmount')+ ": " + consumer.codFundBillPaid.AMOUNT_FF);
}

/**
 * 冻结取消冻结Ajax请求
 * @param rows 选中的row或是指定的单个row
 * @param operateStatu 操作状态:冻结、取消冻结
 * @param operateWay 操作方式:通过图标操作，通过多选checkbox操作
 */
consumer.codFundBillPaid.freezeReleaseCODAjax=function(rows,operateStatu,operateWay){
	var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
	grid.getLoadMask().show();
	var actionUrl = '';
	if(operateStatu == consumer.codFundBillPaid.FREEZECOD_STATU_OPERATE){//冻结
		actionUrl = consumer.realPath('freezeCOD.action');
	}else if(operateStatu == consumer.codFundBillPaid.RELEASECOD_STATU_OPERATE){
		actionUrl = consumer.realPath('releaseCOD.action');
	}
	
	var jsonData = new Array();
	var params= grid.store.submitParams;
	var paramsdata = {};
	
	if(operateWay == consumer.codFundBillPaid.IMGBUTTON_WAY_OPERATE){
		jsonData.push(rows.get('id'));
	}else if(operateWay == consumer.codFundBillPaid.CHECKBOX_WAY_OPERATE){
		
		// 用于判断全选后，全选的行中，冻结操作中是否存在已冻结，或者取消冻结操作中存在未冻结的。
		if(operateStatu == consumer.codFundBillPaid.FREEZECOD_STATU_OPERATE){//冻结
			for(var i=0;i<rows.length;i++){
				if(rows[i].get('status') =='FF'){// 冻结操作中是否存在已冻结
					Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),"运单["+rows[i].get('waybillNo')+"]代收货款已冻结，不能再次冻结");
					grid.getLoadMask().hide();
					return false;
				}else{
					jsonData.push(rows[i].get('id'));
				}
			}
		}else if(operateStatu == consumer.codFundBillPaid.RELEASECOD_STATU_OPERATE){
			for(var i=0;i<rows.length;i++){
				if(rows[i].get('status') !='FF'){//取消冻结操作中存在未冻结的
					Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),"运单["+rows[i].get('waybillNo')+"]代收货款未冻结，不能取消冻结");
					grid.getLoadMask().hide();
					return false;
				}else{
					jsonData.push(rows[i].get('id'));
				}
			}
		}
		
	}
	
	if(Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').getValue() || Ext.getCmp('Foss_codFundBillPaid_AllNoFreezeStatu_ID').getValue()){
		// 通过checkbox全选，后操作，加判断
		paramsdata = {
				'codFundBillVO': {
					'allCheckBoxStatus': consumer.codFundBillPaid.COD_ALL_CHECKBOX_STATUS,
					'endSignDate': params['codFundBillVO.endSignDate'],
					'codType':params['codFundBillVO.codType'],
					'bankList':params['codFundBillVO.bankList'],
					'waybillNos':params['codFundBillVO.waybillNos'],
					'entityIds':jsonData,
					'publicPrivateFlag':params['codFundBillVO.publicPrivateFlag']
				}
		};
	} else {
		paramsdata = {
				'codFundBillVO': {
					'entityIds':jsonData
				}
		};
	}
	
	Ext.Ajax.request({
		timeout: consumer.codFundBillPaid.AJAX_TIMEOUT*1000,
		url:actionUrl,
		method : "POST",
		jsonData:paramsdata,
		success:function(response){
			if(operateStatu == consumer.codFundBillPaid.FREEZECOD_STATU_OPERATE){ // 冻结
				// 返回冻结后发生更改单的运单号
				var result = Ext.decode(response.responseText);
				var waybillNoFreezeErrorList = result.codFundBillVO.waybillNoFreezeErrorList;//冻结失败的运单list
				var waybillNoErrorList = result.codFundBillVO.waybillNoErrorList;//无应收冻结失败list
				var waybillNoErrorListlen = (waybillNoErrorList==null) ? 0:waybillNoErrorList.length;
				var waybillNoFreezeSuccessList = result.codFundBillVO.waybillNoFreezeSuccessList;//冻结成功的运单list集合
				var waybillNoFreezeErrorListLen = (waybillNoFreezeErrorList==null) ? 0:waybillNoFreezeErrorList.length;
				var codListLen = (waybillNoFreezeSuccessList==null)? 0 : waybillNoFreezeSuccessList.length;
				
				var msg = '';
				if(codListLen>0){ // 重新加载
					//grid.getStore().load();	
					//msg += "运单号["+ waybillNoFreezeSuccessList +"]代收货款冻结成功!";
					msg += "代收货款冻结成功!";
					grid.getStore().load(function(records, operation, success) {
						
						var result = Ext.decode(operation.response.responseText);  
						if(!Ext.isEmpty(result.codFundBillVO.cods)&& result.codFundBillVO.cods.length>0){
							
							consumer.codFundBillPaid.NUM_ALL = result.totalCount;
							consumer.codFundBillPaid.AMOUNT_ALL = result.codFundBillVO.totalAmount;
							consumer.codFundBillPaid.NUM_FF = result.codFundBillVO.freezeTotalCount;
							consumer.codFundBillPaid.AMOUNT_FF = result.codFundBillVO.freezeTotalAmount;
							
				      	}else{
				      		consumer.codFundBillPaid.NUM_ALL = 0;
							consumer.codFundBillPaid.AMOUNT_ALL = 0;
							consumer.codFundBillPaid.NUM_FF = 0;
							consumer.codFundBillPaid.AMOUNT_FF = 0;
				      	}
						consumer.codFundBillPaid.loadCodSumUI();	 
						
						if(waybillNoFreezeErrorListLen>0){ // 存在更改单的运单号,提示
							msg += "<br/>运单号["+ waybillNoFreezeErrorList +"]代收货款存在未受理的始发更改单或到达更改单,资金部冻结失败";
							grid.getStore().each(function(record){
									for(var j=0;j<waybillNoFreezeErrorListLen;j++){// 冻结失败加红
										if(record.get('waybillNo') == waybillNoFreezeErrorList[j]){// 冻结失败加红
											record.set('password','1');
											record.commit();
										}
									}
							});
						}
						if(waybillNoErrorListlen>0){//存在无应收或应收应付金额不一致，提示
							msg += "<br/>运单号["+ waybillNoErrorList +"]代收货款应收单不存在或代收应收应付金额不一致,资金部冻结失败";
							grid.getStore().each(function(record){
								for(var j=0;j<waybillNoErrorListlen;j++){// 冻结失败加红
									if(record.get('waybillNo') == waybillNoErrorList[j]){// 冻结失败加红
										record.set('password','1');
										record.commit();
									}
								}
						});
						}
						
						if(msg!=''){
							if(waybillNoFreezeErrorListLen<=0 && waybillNoErrorListlen<=0){ 
								Ext.ux.Toast.msg(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'), msg,'ok',3000);
							}else{
								Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),msg);
							}
						}
					});
				}else{
					if(waybillNoFreezeErrorListLen>0){ // 存在更改单的运单号,提示
						msg += "运单号["+ waybillNoFreezeErrorList +"]代收货款存在未受理的始发更改单或到达更改单,资金部冻结失败";
						var index = 0;
						grid.getStore().each(function(record){
							for(var j=0;j<waybillNoFreezeErrorListLen;j++){// 冻结失败加红
								if(record.get('waybillNo') == waybillNoFreezeErrorList[j]){// 冻结失败加红
									grid.getView().addRowCls(index, 'redrow');
								}
							}
							index++;
						});
					}
					if(waybillNoErrorListlen>0){//存在无应收或应收应付金额不一致，提示
						msg += "<br/>运单号["+ waybillNoErrorList +"]代收货款应收单不存在或代收应收应付金额不一致,资金部冻结失败"+"</br>";
						grid.getStore().each(function(record){
							for(var j=0;j<waybillNoErrorListlen;j++){// 冻结失败加红
								if(record.get('waybillNo') == waybillNoErrorList[j]){// 冻结失败加红
									record.set('password','1');
									record.commit();
								}
							}
					});
					}
					
					if(msg!=''){
						Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),msg);
					}
				}
				
			}else if(operateStatu == consumer.codFundBillPaid.RELEASECOD_STATU_OPERATE){
				//grid.getStore().load();
				grid.getStore().load(function(records, operation, success) {
					var result = Ext.decode(operation.response.responseText);  
					if(!Ext.isEmpty(result.codFundBillVO.cods)&& result.codFundBillVO.cods.length>0){
						
						consumer.codFundBillPaid.NUM_ALL = result.totalCount;
						consumer.codFundBillPaid.AMOUNT_ALL = result.codFundBillVO.totalAmount;
						consumer.codFundBillPaid.NUM_FF = result.codFundBillVO.freezeTotalCount;
						consumer.codFundBillPaid.AMOUNT_FF = result.codFundBillVO.freezeTotalAmount;
						
			      	}else{
			      		consumer.codFundBillPaid.NUM_ALL = 0;
						consumer.codFundBillPaid.AMOUNT_ALL = 0;
						consumer.codFundBillPaid.NUM_FF = 0;
						consumer.codFundBillPaid.AMOUNT_FF = 0;
			      	}
					consumer.codFundBillPaid.loadCodSumUI();
				});
				Ext.ux.Toast.msg(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'), "资金部取消冻结成功！",'ok',1000);
			}
			consumer.codFundBillPaid.resetFreezeButton();
			grid.getLoadMask().hide();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
		  	Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),json.message);
		  	grid.getLoadMask().hide();
		},
		failure:function(form,action){
			grid.getLoadMask().hide();
		},
		unknownException:function(form,action){
			grid.getLoadMask().hide();
		}			
	});
}

/**
 * 退款类型model
 */
Ext.define('Foss.codFundBillPaid.RefundTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

/**
 * 退款类型store
 */
Ext.define('Foss.codFundBillPaid.RefundTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.codFundBillPaid.RefundTypeModel',
	data:{
		'items':[
			{name:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.R3RA'),value:consumer.codFundBillPaid.COD__COD_TYPE__RETURN_3_A_DAY_CODE},
			{name:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.R1'),value:consumer.codFundBillPaid.COD__COD_TYPE__RETURN_1_DAY },
			/**
			 * @author 218392 zhangyongxue 2015-08-06 09:45:20
			 * 代收货款类型下拉新增两个类型：打包退(即日),打包退(三日)
			 */
			{name:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.P1R1'),value:consumer.codFundBillPaid.COD__COD_TYPE__RETURN_PACK_1_DAY},//打包退(即日)
			{name:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.P1R3RA'),value:consumer.codFundBillPaid.COD__COD_TYPE__RETURN_PACK_3_A_DAY_CODE} //打包退(三日)
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

/**
 * 账户性质store
 */
/*Ext.define('Foss.codFundBillPaid.AccountPropertiesStore',{
	extend:'Ext.data.Store',
	model:'Foss.codFundBillPaid.RefundTypeModel',
	data:{
		'items':[
			{name:'对公',value:consumer.codFundBillPaid.COD__PUBLIC_PRIVATE_FLAG__COMPANY },
			{name:'对私',value:consumer.codFundBillPaid.COD__PUBLIC_PRIVATE_FLAG__RESIDENT}
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});	*/

/**
 * 代收货款信息model
 */
Ext.define('Foss.codFundBillPaid.CodFundBillPaidModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	/**
	 * extid,id,运单号,总金额,应退金额,代收货款类型,代收货款状态,收款人,账号,开户行,
	 * 银行行号,对公对私标志,汇款导出人名称,汇款导出时间,批次号,省,市,所属子公司编码,所属子公司
	 * 支行,收款人电话,应付部门,签收时间
	 */
	fields : ['extid', 'id', 'waybillNo', 'amount', 'returnAmount',
					'codType', 'status', 'payeeName', 'payeeAccount', 'bank',
					'bankCode', 'publicPrivateFlag', 'codExportUserName',
					'codExportTime', 'batchNumber', 'province', 'city','payableComCode','payableComName',
					'bankSubbranch', 'payeePhone', 'payableOrgName', {
						name : 'signDate',
						type : 'date',
						convert : stl.longToDateConvert
					}, {
						name : 'password',
						type : 'string',
						defaultValue : '0'
					}]
});

/**
 * 代收货款信息数据Store
 */
Ext.define('Foss.codFundBillPaid.CodFundBillPaidStore',{
	extend:'Ext.data.Store',
	model:'Foss.codFundBillPaid.CodFundBillPaidModel',
	pageSize:20,
	remoteSort: true,
	proxy:{
		type:'ajax',
		actionMethods : 'post',
		timeout: consumer.codFundBillPaid.AJAX_TIMEOUT*1000,
		url:consumer.realPath('queryBillPayableCOD.action'),
		reader:{
			type:'json',
			root:'codFundBillVO.cods',
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
	   			if(me.submitParams.ajaxState==null){
	   				consumer.codFundBillPaid.setQuerySortParam(me.submitParams,operation);
	   			}
	   			
	   			//用于查询后，其他请求方式设置null
	   			me.submitParams.ajaxState=null;
	   			
	   			Ext.apply(me.submitParams, {
			          "limit":operation.limit,
			          "page":operation.page,
			          "start":operation.start
			          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams
	   			});
	   			
	   			consumer.codFundBillPaid.resetFreezeButton();
	   		}
		};
		me.callParent([ cfg ]);
	} 
});

/**
 * 按日期 资金管理中心汇款Form
 */
Ext.define('Foss.codFundBillPaid.CodFundBillPaidDateForm',{
	extend:'Ext.form.Panel',
	//title:'代收货款支付',
	frame:false,
	//collapsible: true,
	//animCollapse: true,
	layout : {
		type : 'column'
	},
	autoScroll:true,
	defaults : {
		msgTarget: 'under',
		margin :'10 10 10 10',
		labelWidth :85,
		allowBlank : true
	},
	items:[{
			xtype:'container',
			columnWidth:.6,
			layout:'column',
			items:[{
			    	xtype:'datetimefield_date97',
			    	fieldLabel:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.endSignDate'),
			    	id:'FOSS_consumer_codFundBillPaidForm_EndSignDate_ID',
					time:true,
					name:'codFundBillVO.endSignDate',
					columnWidth:.5,
					editable:false,
					allowBlank : false,
					value: Ext.Date.format(stl.getTargetDate(new Date(),1), 'Y-m-d')+' 00:00:00',
					dateConfig: {
						el: 'FOSS_consumer_codFundBillPaidForm_EndSignDate_ID-inputEl',
						dateFmt: 'yyyy-MM-dd',
						//最大选择日期
						maxDate: Ext.Date.format(stl.getTargetDate(new Date(),1), 'Y-m-d')+' 00:00:00'
					}
			
				},{
					xtype: 'combobox',
					name:'codFundBillVO.codType',
					height: 30,
			        fieldLabel: consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.codType'),
			        /*store : FossDataDictionary.getDataDictionaryStore(
							settlementDict.COD__COD_TYPE, null,[consumer.codFundBillPaid.COD__COD_TYPE__RETURN_1_DAY] ),*/
					store:Ext.create('Foss.codFundBillPaid.RefundTypeStore'),
					queryModel:'local',
					value:consumer.codFundBillPaid.COD__COD_TYPE__RETURN_1_DAY ,
					forceSelection:true,
					allowBlank : false,
					listeners:{
						change:stl.comboSelsct
					},
					displayField:'name',
					valueField:'value',
			        columnWidth:.5
				},{
					xtype:'commonbankmultiselector',
			    	name:'codFundBillVO.bankList',
			    	fieldLabel:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.bank'),
			    	columnWidth:.5,
			    	headOffice:'Y', // 只查询总行
			    	valueField:this.displayField,
			    	showContent : '{name}'// 显示表格列
				},{
					xtype: 'combobox',
					name:'codFundBillVO.publicPrivateFlag',
					height: 30,
			        fieldLabel: consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.accountProperties'),
			        store : FossDataDictionary.getDataDictionaryStore(
			        		settlementDict.COD__PUBLIC_PRIVATE_FLAG, null,null ),
					/*store:Ext.create('Foss.codFundBillPaid.AccountPropertiesStore'),
					*/
					forceSelection:true,
					listeners:{
						change:stl.comboSelsct
					},
					value:'',
					queryModel:'local',
					displayField:'valueName',
					valueField:'valueCode',
			        columnWidth:.5
				}]
		},{
			xtype:'container',
			columnWidth:1,
			layout:'column',
			items:[{
				xtype:'button',
				text:consumer.codFundBillPaid.i18n('foss.stl.consumer.common.reset'),
				width:70,
				handler:function(){
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:0.8
			},{
				xtype:'button',
				text:consumer.codFundBillPaid.i18n('foss.stl.consumer.common.query'),
				cls:'yellow_button',
				width:118,
				handler:function(){
					if(this.up('form').getForm().isValid()){
						consumer.codFundBillPaid.queryCodFundBillPaid(this);
					}else{
						Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
					}
				}
			}]
		}]
	
});

/**
 * 按运单 资金管理中心汇款Form
 */
Ext.define('Foss.codFundBillPaid.CodFundBillPaidWaybillNoForm',{
	extend:'Ext.form.Panel',
	//title:'代收货款支付',
	frame:false,
	//collapsible: true,
	//animCollapse: true,
	layout : {
		type : 'column'
	},
	defaults : {
		margin :'10 10 10 10',
		labelWidth :85,
		msgTarget: 'under'
	},
	items:[{
			xtype:'container',
			columnWidth:.6,
			layout:'column',
			items:[{
					xtype:'textarea',
					name:'codFundBillVO.waybillNos',
					fieldLabel: consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.No'),
					allowBlank : false,
					flex: 6,
					emptyText:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.eightToTenMostInputTenWaybillNumber'),
					width:300,
					height:60,
					//regex:/^([0-9]{8,10},?){0,10}$/i,
					//354658-校验至14位运单号
					regex:/^([0-9]{8,14})(,[0-9]{8,14}){0,9},?$/i,
					regexText:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.eightToTenMostInputTenWaybillNumber'),
					blankText:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.NoCannotEmpty'),
					columnWidth:1
			
				}]
		},{
			xtype:'container',
			columnWidth:1,
			layout:'column',
			items:[{
				xtype:'button',
				text:consumer.codFundBillPaid.i18n('foss.stl.consumer.common.reset'),
				width:70,
				handler:function(){
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:0.8
			},{
				xtype:'button',
				text:consumer.codFundBillPaid.i18n('foss.stl.consumer.common.query'),
				cls:'yellow_button',
				width:118,
				handler:function(){
					if(this.up('form').getForm().isValid()){
						consumer.codFundBillPaid.queryCodFundBillPaid(this);
					}else{
						Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
					}
				}
			}]
		}]
	
});

/**
 * 显示代收货款单据
 */
Ext.define('Foss.codFundBillPaid.CodFundBillPaidGrid',{
	extend:'Ext.grid.Panel',
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners : {
			deselect:function(rthis,record,index,eOpts){  
				rthis.view.getEl().dom.onclick = function(){ 
                	Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').setValue(false);
                	Ext.getCmp('Foss_codFundBillPaid_AllNoFreezeStatu_ID').setValue(false);
                }; 	 
            }  
        
		}
	}),
	frame:true,
	title: consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.codMessage'),
	cls:'autoHeight',
	bodyCls:'autoHeight',
	height:600,
	store: null,
	columns : {
				defaults:{
					draggable:false
				},
				items:[{ 
				    xtype:'actioncolumn',
					width:73,
					text : consumer.codFundBillPaid.i18n('foss.stl.consumer.common.actionColumn'),
					align: 'center',
					items:[{
						iconCls : 'foss_icons_stl_freeze',
						tooltip : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.applyFreeze'),
						handler:function(grid, rowIndex, colIndex){
							var selection = grid.getStore().getAt(rowIndex);
							//冻结Ajax
							consumer.codFundBillPaid
									.freezeReleaseCODAjax(
											selection,
											consumer.codFundBillPaid.FREEZECOD_STATU_OPERATE,
											consumer.codFundBillPaid.IMGBUTTON_WAY_OPERATE);
						},
						getClass:function(v,m,r,rowIndex){// 根据代收货款类型判断显示哪种功能(冻结\反冻结)
							if(r.get('status')==consumer.codFundBillPaid.COD__STATUS__FUND_FREEZE){
								return 'statement_consumer_hide';
							}else {
								return 'foss_icons_stl_freeze';
							}
						}
					  },{
						iconCls : 'foss_icons_stl_noFreeze',
						tooltip : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.cancelFreeze'),
						handler:function(grid, rowIndex, colIndex){
							var selection = grid.getStore().getAt(rowIndex);
							//取消冻结Ajax
							consumer.codFundBillPaid
									.freezeReleaseCODAjax(
											selection,
											consumer.codFundBillPaid.RELEASECOD_STATU_OPERATE,
											consumer.codFundBillPaid.IMGBUTTON_WAY_OPERATE);
						},
						getClass:function(v,m,r,rowIndex){// 根据代收货款类型判断显示哪种功能(冻结\反冻结)
							if(r.get('status')!=consumer.codFundBillPaid.COD__STATUS__FUND_FREEZE){
								return 'statement_consumer_hide';
							}else {
								return 'foss_icons_stl_noFreeze';
							}
						}
					  }
					]
					
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.codType'),
					dataIndex : 'codType',
					renderer:function(value){
						/*if(value == 'R1'){
							return '即日退';
						}
						else if(value == 'R3'){
							return '三日退';
						}
						else if(value == 'RA'){
							return '审核退';
						}else{
							return value;
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.COD__COD_TYPE);
			    		return displayField;
					}
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.wayBillNo'),
					dataIndex : 'waybillNo'
						
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.returnAmount'),
					dataIndex : 'returnAmount'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.payableOrgName'),
					dataIndex : 'payableOrgName'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.frozenState'),
					dataIndex : 'status',
					renderer:function(value){
						if(value == consumer.codFundBillPaid.COD__STATUS__FUND_FREEZE){
							return consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.freeze');
						}
						else{
							return consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.unFreeze');
						}
						/*var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.COD__STATUS);
			    		return displayField;*/
					}
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.payeeName'),
					dataIndex : 'payeeName'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.payeeAccount'),
					dataIndex : 'payeeAccount'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.bank'),
					dataIndex : 'bank'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.province'),
					dataIndex : 'province'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.city'),
					dataIndex : 'city'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.subbranch'),
					dataIndex : 'bankSubbranch'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.publicPrivateFlag'),
					dataIndex : 'publicPrivateFlag',
					renderer:function(value){
						/*if(value == 'C'){
							return '对公';
						}
						else if(value == 'R'){
							return '对私';
						}else{
							return '未知';
						}*/
						var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.COD__PUBLIC_PRIVATE_FLAG);
			    		return displayField;
					}
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.signDate'),
					dataIndex : 'signDate',
					format : 'Y-m-d H:i:s',
					xtype : 'datecolumn'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.phone'),
					dataIndex : 'payeePhone'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.bankCode'),
					dataIndex : 'bankCode'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.payableComName'),
					dataIndex : 'payableComName'
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.codExportTime'),
					dataIndex : 'codExportTime',
					hidden: true
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.codExportUserName'),
					dataIndex : 'codExportUserName',
					hidden: true
				}, {
					header : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.batchNumber'),
					dataIndex : 'batchNumber',
					hidden: true
				}
				]},    
	loadMask:null,
	getLoadMask:function(){
		var me = this;
		me.loadMask = Ext.getCmp('FOSS_consumer_CodFundBillPaidGrid_LoadMask_ID');
		if(Ext.isEmpty(me.loadMask)){
			me.loadMask = new Ext.LoadMask(me.up('panel'),{
				id:'FOSS_consumer_CodFundBillPaidGrid_LoadMask_ID',
				msg:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.dataLoading'),
				autoShow:false
			});
		}
		return me.loadMask;
	},
	initComponent:function(){
		var me = this;
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',	
		    defaults:{
				margin:'0 0 5 0'
			},	
		    items: [{
					height:5,
					columnWidth:1
				},{
		    	xtype:'tbtext',
				readOnly:true,
				id:'Foss_codFundBillPaid_Sum_ID',
				name:'totalRows',
				columnWidth:0.35
				},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:0.65,
				pageSize: 20,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 500
				})
			  },{
					xtype:'container',
					columnWidth:0.75,
					width:10,
					html:'&nbsp;',
					border:0
				},{
				xtype:'button',
				text:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.refundApply'),
				disabled:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/payCODOnline.action'),
				hidden:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/payCODOnline.action'),
				width:54,
				columnWidth:0.1,
				handler: function() {
					var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
					grid.getLoadMask().show();
					var selectionModel = grid.getSelectionModel();
					var rows= selectionModel.getSelection(); //获取所有选中行，
					if(rows.length==0){
							Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
							grid.getLoadMask().hide();
							return false;
					}
					
					var params= grid.store.submitParams;
					var jsonData = new Array();
					for(var i=0;i<rows.length;i++){
						if(rows[i].get('status') !='FF'){//退款操作中存在未冻结的
							Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),"运单["+rows[i].get('waybillNo')+"]代收货款未冻结，不能退款操作");
							grid.getLoadMask().hide();
							return false;
						}else{
							jsonData.push(rows[i].get('id'));
						}
					}
				
					var paramsdata = {};
					if(Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').getValue()){
						// 通过checkbox全选，后操作，加判断
						paramsdata = {
								'codFundBillVO': {
									'allCheckBoxStatus': consumer.codFundBillPaid.COD_ALL_CHECKBOX_STATUS,
									'endSignDate': params['codFundBillVO.endSignDate'],
									'codType':params['codFundBillVO.codType'],
									'bankList':params['codFundBillVO.bankList'],
									'waybillNos':params['codFundBillVO.waybillNos'],
									'entityIds':jsonData,
									'publicPrivateFlag':params['codFundBillVO.publicPrivateFlag']
								}
						};
					}else {
						 paramsdata = {
								'codFundBillVO': {
									'entityIds':jsonData,
									'codType':params['codFundBillVO.codType']
								}
						};
					}
					
					//退款申请
					Ext.Ajax.request({
						timeout: consumer.codFundBillPaid.AJAX_TIMEOUT*1000,
						url:consumer.realPath('payCODOnline.action'),
						jsonData:paramsdata,
						method : "post",
						success:function(response){
							var rt = Ext.decode(response.responseText); 
							/*var store = grid.getStore();
							var selectionModel = grid.getSelectionModel();
							var rows= selectionModel.getSelection(); //获取所有选中行，
							*/							
							//Ext.Msg.alert('提示信息','退款申请成功！');
							Ext.ux.Toast.msg(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'), consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.batchNumber')+"["+rt.codFundBillVO.batchNumber+"]"+consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.refundApplySuccess'),'ok',8000);
							//store.load();
							// 退款申请成功，重新加载统计数据
							grid.getStore().load(function(records, operation, success) {
								var result = Ext.decode(operation.response.responseText); 
								if(!Ext.isEmpty(result.codFundBillVO.cods)&& result.codFundBillVO.cods.length>0){
									consumer.codFundBillPaid.NUM_ALL = result.totalCount;
									consumer.codFundBillPaid.AMOUNT_ALL = result.codFundBillVO.totalAmount;
									consumer.codFundBillPaid.NUM_FF = result.codFundBillVO.freezeTotalCount;
									consumer.codFundBillPaid.AMOUNT_FF = result.codFundBillVO.freezeTotalAmount;
									
						      	}else{
						      		consumer.codFundBillPaid.NUM_ALL = 0;
									consumer.codFundBillPaid.AMOUNT_ALL = 0;
									consumer.codFundBillPaid.NUM_FF = 0;
									consumer.codFundBillPaid.AMOUNT_FF = 0;
						      	}
								consumer.codFundBillPaid.loadCodSumUI();
								consumer.codFundBillPaid.resetFreezeButton();
								
							});
							
							grid.getLoadMask().hide();
							
						},
						exception : function(response) {
						  var json = Ext.decode(response.responseText);
						  Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),json.message);
						  grid.getLoadMask().hide();
						},
						failure:function(form,action){
							grid.getLoadMask().hide();
						},
						unknownException:function(form,action){
							grid.getLoadMask().hide();
						}					
					}); 
					  
		        }
			},{
				xtype:'container',
				columnWidth:0.02,
				width:10,
				html:'&nbsp;',
				border:0
			},{
				xtype:'button',
				text:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.codExport'),
				disabled:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/payCODOffline.action'),
				hidden:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/payCODOffline.action'),
				width:54,
				columnWidth:0.1,
				handler: function() {
					
					var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
					grid.getLoadMask().show();
					var selectionModel = grid.getSelectionModel();
					var rows= selectionModel.getSelection(); //获取所有选中行，
					if(rows.length==0){
							Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
							grid.getLoadMask().hide();
							return false;
					}
					
					var params= grid.store.submitParams;
					var jsonData = new Array();
					for(var i=0;i<rows.length;i++){
						if(rows[i].get('status') !='FF'){//汇款操作中存在未冻结的
							Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),"运单["+rows[i].get('waybillNo')+"]代收货款未冻结，不能汇款操作");
							grid.getLoadMask().hide();
							return false;
						}else{
							jsonData.push(rows[i].get('id'));
						}
					}
				
					if(Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').getValue()){
						// 通过checkbox全选，后操作，加判断
						Ext.apply(params, {
							'codFundBillVO.allCheckBoxStatus' : consumer.codFundBillPaid.COD_ALL_CHECKBOX_STATUS
						});
					}else {
						Ext.apply(params, {
							'codFundBillVO.allCheckBoxStatus' : null
						});
					}
						
					Ext.apply(params, {
						'codFundBillVO.entityIds' : jsonData
					});
					
					if(!Ext.fly('downloadAttachFileForm')){
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					
					//设置该按钮灰掉
					var me = this;
					me.disable(false);
					//30秒后自动解除灰掉效果
					setTimeout(function() {
						me.enable(true);
					}, 30000);
					
					var errBool = false;
					//汇款导出业务处理
					Ext.Ajax.request({
						timeout: consumer.codFundBillPaid.AJAX_TIMEOUT*1000,
						url:consumer.realPath('payCODOffline.action'),
		   			 	method : 'post',
						params:params,
						form: Ext.fly('downloadAttachFileForm'),
						async: false, 
						isUpload: true,
						success : function(response) {
						  	grid.getLoadMask().hide();
						  	//获取响应的json字符串
							var jsonText = Ext.decode(response.responseText.trim());
		                   	//导出失败
		                   	if(jsonText.message!=null&&jsonText.message!=''){
		                   		errBool = true;
		                     	Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),jsonText.message);
		                     }
						},
						failure:function(response){
							errBool = true;
							grid.getLoadMask().hide();
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'), jsonText.message);
						},
						exception : function(response) {
							errBool = true;
							grid.getLoadMask().hide();
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'), jsonText.message);
						}		
					});
					
					// 汇款导出成功，重新加载统计数据
					setTimeout(function(){
						grid.getLoadMask().hide();
						if(!errBool){
							grid.getStore().load(function(records, operation, success) {
								var result = Ext.decode(operation.response.responseText);  
								if(!Ext.isEmpty(result.codFundBillVO.cods)&& result.codFundBillVO.cods.length>0){
									
									consumer.codFundBillPaid.NUM_ALL = result.totalCount;
									consumer.codFundBillPaid.AMOUNT_ALL = result.codFundBillVO.totalAmount;
									consumer.codFundBillPaid.NUM_FF = result.codFundBillVO.freezeTotalCount;
									consumer.codFundBillPaid.AMOUNT_FF = result.codFundBillVO.freezeTotalAmount;
									
						      	}else{
						      		consumer.codFundBillPaid.NUM_ALL = 0;
									consumer.codFundBillPaid.AMOUNT_ALL = 0;
									consumer.codFundBillPaid.NUM_FF = 0;
									consumer.codFundBillPaid.AMOUNT_FF = 0;
						      	}
								consumer.codFundBillPaid.loadCodSumUI();
							});
							consumer.codFundBillPaid.resetFreezeButton();
							Ext.ux.Toast.msg(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'), consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.remitExportSuccess'),'ok',1000);
						}
					},15000);
		        }
			}]
		}];
		 me.callParent();
	},
	constructor:function(config){
		var me = this;
		var cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.codFundBillPaid.CodFundBillPaidStore');
		me.callParent([cfg]);
	}
});

//查询tab
Ext.define('Foss.codFundBillPaid.CodFundBillPaidQueryInfoTab', {
	extend:'Ext.tab.Panel',
	frame:false,
	bodyCls: 'autoHeight',
	cls: 'innerTabPanel',
	activeTab: 0,
	height : 210,
	items : [ {
		title: consumer.codFundBillPaid.i18n('foss.stl.consumer.common.queryByDate'),
		tabConfig: {
			width: 150
		},
		width: '200',
        layout:'fit',
        items:[
               Ext.create('Foss.codFundBillPaid.CodFundBillPaidDateForm')
               ]
	}, {
		title: consumer.codFundBillPaid.i18n('foss.stl.consumer.common.queryByNo'),
		tabConfig: {
			width: 150
		},
        layout:'fit',
        items:[
               Ext.create('Foss.codFundBillPaid.CodFundBillPaidWaybillNoForm')
               ]
	}]
});

Ext.onReady(function(){
	Ext.QuickTips.init();
	
	//创建Tab
	var codSalesPayQueryInfoTab = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidQueryInfoTab_ID');
	if(!codSalesPayQueryInfoTab){
		codSalesPayQueryInfoTab = Ext.create('Foss.codFundBillPaid.CodFundBillPaidQueryInfoTab',{
			id: 'Foss_codFundBillPaid_CodFundBillPaidQueryInfoTab_ID'
		});
	}
	
	//创建显示表格
	var codFundBillPaidGrid = Ext.create('Foss.codFundBillPaid.CodFundBillPaidGrid',{
		id : 'Foss_codFundBillPaid_CodFundBillPaidGrid_ID',
		hidden:true,
		tbar : [{
			xtype:'checkboxfield',
			boxLabel  : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.allFrozenDocument'),
			id : 'Foss_codFundBillPaid_AllFreezeStatu_ID',
			listeners:{
                 render:function(el){
                     el.getEl().addCls('label-class-green');
                 },
                 afterrender:function(obj){  
                     obj.getEl().dom.onclick = function(){ 
                    	 var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
        				 grid.getLoadMask().show();
        				 var checkMsg = Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').getValue();
        				 var selectionModel = grid.getSelectionModel();
        				 var store = grid.getStore();
        				 var codFundBillPaidModel = Ext.create('Foss.codFundBillPaid.CodFundBillPaidModel');
        				 // 设置冻结单据选择状态
        				 var ffStore = [];
        				 for (var i = 0; i < store.getCount(); i++) {
        					 var record = store.getAt(i);
        					 var statu =record.get('status');
        					 if(consumer.codFundBillPaid.COD__STATUS__FUND_FREEZE == statu){
        						 ffStore.push(record);
        					 }
        				 }
        				 // 判断是全部选中，还是全部取消选中
        				 if( checkMsg ){
        					 selectionModel.select(ffStore);
        					 Ext.getCmp('Foss_codFundBillPaid_AllNoFreezeStatu_ID').setValue(false);
        					 //Ext.getCmp('Foss_codFundBillPaid_doFreeze_ID').disable(true);
        				 }else{
        					 selectionModel.deselect(ffStore);
        				 }
        				 grid.getLoadMask().hide();
                     }; 	 
	             }  
             }
		},{
			xtype:'container',
			columnWidth:0.05,
			width:10,
			html:'&nbsp;',
			border:0
		},{
			xtype:'checkboxfield',
			boxLabel  : consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.allNoFrozenDocument'),
			id : 'Foss_codFundBillPaid_AllNoFreezeStatu_ID',
			listeners:{
                 render:function(el){
                     el.getEl().addCls('label-class-red');
                 },
		         afterrender: function (obj) {  
		        	 obj.getEl().dom.onclick = function(){ 
		        		 
		        		 var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
						 grid.getLoadMask().show();
						 var checkMsg = Ext.getCmp('Foss_codFundBillPaid_AllNoFreezeStatu_ID').getValue();
						 var selectionModel = grid.getSelectionModel();
						 var store = grid.getStore();
						 var codFundBillPaidModel = Ext.create('Foss.codFundBillPaid.CodFundBillPaidModel');
						 // 设置冻结单据选择状态
						 var ffStore = [];
						 for (var i = 0; i < store.getCount(); i++) {
							 var record = store.getAt(i);
							 var statu =record.get('status');
							 if(consumer.codFundBillPaid.COD__STATUS__FUND_FREEZE != statu){
								 ffStore.push(record);
							 }
						 }
						 // 判断是全部选中，还是全部取消选中
						 if( checkMsg ){
							 selectionModel.select(ffStore);
							 Ext.getCmp('Foss_codFundBillPaid_AllFreezeStatu_ID').setValue(false);
							 //Ext.getCmp('Foss_codFundBillPaid_cancelFreeze_ID').disable( true );
						 }else{
							 selectionModel.deselect(ffStore);
						 }
						 grid.getLoadMask().hide();
	                 };  
	             }  
             }
		},{
			xtype:'container',
			columnWidth:0.05,
			width:10,
			html:'&nbsp;',
			border:0
		},{
			xtype:'button',
			text:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.doFreeze'),
			id : 'Foss_codFundBillPaid_doFreeze_ID',
			disabled:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/freezeCOD.action'),
			hidden:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/freezeCOD.action'),
			handler: function() {
				var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
				var selectionModel = grid.getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				if(rows.length==0){
						Ext.Msg.alert('提示信息','请至少选择一条数据，再进行冻结操作');
						return false;
				}
				//冻结Ajax
				consumer.codFundBillPaid
								.freezeReleaseCODAjax(
										rows,
										consumer.codFundBillPaid.FREEZECOD_STATU_OPERATE,
										consumer.codFundBillPaid.CHECKBOX_WAY_OPERATE);
		    }
		},{
			xtype:'container',
			columnWidth:0.05,
			width:10,
			html:'&nbsp;',
			border:0
		},{
			xtype:'button',
			text:consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.cancelFreeze'),
			id : 'Foss_codFundBillPaid_cancelFreeze_ID',
			disabled:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/releaseCOD.action'),
			hidden:!consumer.codFundBillPaid.isPermission('/stl-web/consumer/releaseCOD.action'),
			handler: function() {
				var grid = Ext.getCmp('Foss_codFundBillPaid_CodFundBillPaidGrid_ID');
				var selectionModel = grid.getSelectionModel();
				var rows= selectionModel.getSelection(); //获取所有选中行，
				if(rows.length==0){
						Ext.Msg.alert(consumer.codFundBillPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codFundBillPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataAndThenToCancelTheFreezingOperation'));
						return false;
				}
				//取消冻结Ajax
				consumer.codFundBillPaid
								.freezeReleaseCODAjax(
										rows,
										consumer.codFundBillPaid.RELEASECOD_STATU_OPERATE,
										consumer.codFundBillPaid.CHECKBOX_WAY_OPERATE);
		    }
		}],
		//enableColumnHide: false,      //取消列头菜单
      	//sortableColumns: false,          //取消列头排序功能
		viewConfig : {   
			enableTextSelection: true,
	        forceFit : true,
	        stripeRows: false,//显示重复样式，不用隔行显示
	        emptyText : consumer.codFundBillPaid.i18n('foss.stl.consumer.common.emptyText'),
	        getRowClass : function(record,rowIndex,rowParams,store){
	            if(record.data.password=="1"){ // 账号修改成功返回的样式
	            	return 'redrow';
	            }else{
	            	return ''; 
	            }
         }
	    }
	});
	
	//显示到JSP页面
	Ext.create('Ext.panel.Panel',{
		id: 'T_consumer-codFundBillPaid_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [codSalesPayQueryInfoTab,codFundBillPaidGrid],
		renderTo: 'T_consumer-codFundBillPaid-body'
	});
});