//由于平台正则BUG，为了加载message
consumer.codPaid.i18n('foss.stl.consumer.cod.startTimeEndTimeAreNotMoreThanDays');
consumer.codPaid.i18n('foss.stl.consumer.cod.waybillNoCodStateNotRefundCannotRemitSuccessOperation');
consumer.codPaid.i18n('foss.stl.consumer.cod.waybillNoCodStateNotRefundCannotRemitFailureOperation');
consumer.codPaid.i18n('foss.stl.consumer.cod.waybillNoCodStateNotRefundCannotReverseRemitSuccessOperation');

/**
 * 请求超时时间
 */
consumer.codPaid.AJAX_TIMEOUT = 2*60*60; //默认2小时

//开始日期与结束日期最大相差天数
consumer.codPaid.MAX_DIFF_DAYS = 7;
consumer.codPaid.isAllSelect='false';

/**
 * 设置排序参数
 */
consumer.codPaid.setSelectParam=function (submitParams,allSelect){
	// 默认状态排序
	Ext.apply(submitParams, {
		      "isAllSelect":allSelect
        }); 
}

//代收货款状态Renderer
consumer.codPaid.codStatusRenderer = function(value){
	/*var status = null;
	switch(value){
		case 'NR':
			status = '未退款';
			break;
		case 'AG':
			status = '待审核';
			break;
		case 'SF':
			status = '营业部冻结';
			break;
		case 'CA':
			status = '收银员审核';
			break;
		case 'FF':
			status = '资金部冻结';
			break;
		case 'RG':
			status = '退款中';
			break;
		case 'RFA':
			status = '退款失败申请';
			break;
		case 'NRS':
			status = '反汇款成功';
			break;
		case 'RF':
			status = '退款失败';
			break;
		case 'RD':
			status = '已退款';
			break;
		default:
			status = '未知代收货款状态';
		}
		
		return status;*/
		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__STATUS');
		return displayField;	
	}

//代收货款
consumer.codPaid.codTypeRenderer = function(value){
	/*var codType = null;
	
	switch(value){
		case 'R1':
			codType = '即日退';
			break;
		case 'R3':
			codType = '三日退';
			break;
		case 'RA':
			codType = '审核退';
			break;
		default:
			codType = '未知代收货款类型'
	}
	
	return codType;*/
	var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__COD_TYPE');
	return displayField;	
}

consumer.codPaid.publicPrivateFlagRenderer = function(value){
	/*var flag = null;
	
	switch(value){
		case 'C':
			flag = '对公';
			break;
		case 'R':
			flag = '对私';
			break;
		default:
			flag = '未知'
	}
	
	return flag;*/
	var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'COD__PUBLIC_PRIVATE_FLAG');
	return displayField;	
}

//处理查询返回结果
consumer.codPaid.doWithResponse = function(success,store){
	
	var rawData = store.proxy.reader.rawData;
	if (!success && !rawData.isException) {
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'), rawData.message);
		return false;
	}
	
}
	
//查询代收货款汇款失败数据
consumer.codPaid.queryCODPayFailed=function(){
	
	var form = this.up('form').getForm();
	if (form.isValid()) {
		//得到Store
		var grid = Ext.getCmp('FOSS_Consumer_CODPaidFailedGrid_ID');
		var store = grid.getStore();
		if(store){
			if(grid.isHidden()){
				grid.show();
			}
			
			var CODQueryForm = Ext.getCmp('FOSS_Consumer_CODPaidFailedQueryForm_ID');
			if (CODQueryForm) {
				var form = CODQueryForm.getForm();
				// 设置查询参数
				grid.setSubmitParams(form.getValues());
			}
			
			store.loadPage(1,{
				callback:function(records,operation,success){
					consumer.codPaid.doWithResponse(success,store);
					var result = Ext.decode(operation.response.responseText);  
					var tbars = grid.dockedItems.items[3].query('textfield');
					if(!Ext.isEmpty(result.codPayFailedVO.cods)&& result.codPayFailedVO.cods.length>0){
						tbars[1].setValue(result.codPayFailedVO.totalRecords);
						tbars[2].setValue(result.codPayFailedVO.totalAmount);
			      	}else{
			      		tbars[1].setValue(0);
						tbars[2].setValue(0);
			      	}
				}});
		}
	}
	//如果查询条件不合法
	else{
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
	}
}

//查询代收货款汇款确认数据
consumer.codPaid.queryCODPayConfirm=function(){
	var form = this.up('form').getForm();
	consumer.codPaid.isAllSelect='false';
	Ext.getCmp('Foss_codPaid_AllFreezeStatu_ID').setValue(false);
	if(form.isValid()){
		
		if(consumer.codPaid.queryCODPayConfirm.valid(form)){
			var grid = Ext.getCmp('FOSS_Consumer_CODPaidConfirmGrid_ID');
			var store = grid.getStore();
			if(store){
				if(grid.isHidden()){
					grid.show();
				}
				
				var CODQueryForm = Ext.getCmp('FOSS_Consumer_CODPaidConfirmQueryForm_ID');
				if (CODQueryForm) {
					var form = CODQueryForm.getForm();
					// 设置查询参数
					grid.setSubmitParams(form.getValues());
				}
				
				store.loadPage(1,{
					callback:function(records,operation,success){
						consumer.codPaid.doWithResponse(success,store);
						
						var result = Ext.decode(operation.response.responseText);  
						var tbars = grid.dockedItems.items[3].query('textfield');
						if(!Ext.isEmpty(result.confirmVo.cods)&& result.confirmVo.cods.length>0){
							tbars[1].setValue(result.totalCount);
							tbars[2].setValue(result.confirmVo.totalAmount);
				      	}else{
				      		tbars[1].setValue(0);
							tbars[2].setValue(0);
				      	}
						
					}});
			}
		}
	}
	//如果查询条件不合法
	else{
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
	}
}

//代收货款汇款确认查询条件校验
consumer.codPaid.queryCODPayConfirm.valid = function(form){
	var startTime_str = form.findField('confirmVo.exportStartTimeString').getValue();
	var endTime_str =form.findField('confirmVo.exportEndTimeString').getValue();
	var startTime = new Date(startTime_str.replace(/-/g,"/")).getTime();
	var endTime = new Date(endTime_str.replace(/-/g,"/")).getTime();
	var waybillNoString = form.findField('confirmVo.waybillNoString').getValue();
	
	if(Ext.isEmpty(waybillNoString)){
		if(Ext.isEmpty(startTime)){
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.startTime')+consumer.codPaid.i18n('foss.stl.consumer.cod.cannotEmpty'));
			return false;
		}
		
		if(Ext.isEmpty(endTime)){
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.endTime')+consumer.codPaid.i18n('foss.stl.consumer.cod.cannotEmpty'));
			return false;
		}
		
		if(startTime > endTime){
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.startTime')+consumer.codPaid.i18n('foss.stl.consumer.cod.cannotGreaterThan')+consumer.codPaid.i18n('foss.stl.consumer.cod.endTime'));
			return false;
		}
		
		if(stl.compareTwoDate(startTime,endTime) > consumer.codPaid.MAX_DIFF_DAYS){
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.startTimeEndTimeAreNotMoreThanDays',[consumer.codPaid.MAX_DIFF_DAYS]));
			return false;
		}
	}
	
	return true;
}

//代收货款确认汇款成功
consumer.codPaid.confirmSuccess = function(){
	var grid = Ext.getCmp('FOSS_Consumer_CODPaidConfirmGrid_ID');
	grid.getLoadMask().show();
	var store = grid.getStore();
	var recordArray = grid.getSelectionModel().getSelection();
	
	if(!recordArray || recordArray.length == 0){
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
		grid.getLoadMask().hide();
		return false;
	}
	
	//得到代收货款ID
	var codEntityIds = [];
	for(var i=0;i<recordArray.length;i++){
		var record = recordArray[i];
		var status = record.get('status');
		var waybillNo = record.get('waybillNo');
		
		if(status != 'RG'){
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.waybillNoCodStateNotRefundCannotRemitSuccessOperation',[waybillNo]));
			grid.getLoadMask().hide();
			return false;
		}
		
		codEntityIds.push(record.get('id'));
	}
	
	if(Ext.isEmpty(codEntityIds)){
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
		grid.getLoadMask().hide();
		return false;
	}
	
	var paramsV = grid.submitParams;
	Ext.apply(paramsV, {'confirmVo.codEntityIds':codEntityIds}); 
	
	Ext.Ajax.request({
		timeout: consumer.codPaid.AJAX_TIMEOUT*1000,
		url:consumer.realPath('confirmSuccess.action'),
		method:'post',
		params:paramsV,
		success:function(r,o){
			
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.confirmTheCollectionOfMoneyRemittanceSuccessfullyCompleted'),function(){
				//store.load();
				store.load(function(records, operation, success) {
					var result = Ext.decode(operation.response.responseText);  
					var tbars = grid.dockedItems.items[3].query('textfield');
					if(!Ext.isEmpty(result.confirmVo.cods)&& result.confirmVo.cods.length>0){
						tbars[1].setValue(result.totalCount);
						tbars[2].setValue(result.confirmVo.totalAmount);
			      	}else{
			      		tbars[1].setValue(0);
						tbars[2].setValue(0);
			      	}
				});	
			});
			grid.getLoadMask().hide();
		},
		exception:function(response){
			grid.getLoadMask().hide();
			var json = Ext.decode(response.responseText);
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.confirmTheCollectionOfMoneyRemittanceSuccessAnomaly') + ":" + json.message);
		}
	});
}

//确认代收货款汇款失败
consumer.codPaid.confirmFailure = function(){
	
	var grid = Ext.getCmp('FOSS_Consumer_CODPaidConfirmGrid_ID');
	grid.getLoadMask().show();
	
	var recordArray = grid.getSelectionModel().getSelection();
	
	if(!recordArray || recordArray.length == 0){
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
		grid.getLoadMask().hide();
		return false;
	}
	
	//得到代收货款ID
	var codEntityIds = [];
	for(var i=0;i<recordArray.length;i++){
		var record = recordArray[i];
		var status = record.get('status');
		var waybillNo = record.get('waybillNo');
		
		if(status != 'RG'){
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.waybillNoCodStateNotRefundCannotRemitFailureOperation',[waybillNo]));
			grid.getLoadMask().hide();
			return false;
		}
		
		codEntityIds.push(record.get('id'));
	}
	
	var store = grid.getStore();
	 
	//弹出汇款失败原因
	Ext.Msg.show({
		title : consumer.codPaid.i18n('foss.stl.consumer.cod.informationInput'),
		msg : consumer.codPaid.i18n('foss.stl.consumer.cod.remittanceFailureCause'),
		width : 300, // 宽度
		buttons : Ext.Msg.OKCANCEL, // 按钮类型,和个数
		multiline : true, // 多行
		fn : function(btn,text,me){
			if(btn == 'ok'){
				//如果原因为空，则返回
				if(Ext.isEmpty(text)){
					Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseInputTheCollectionOfMoneyTheReasonForFailure'));
					return false;
				}
				else{
					
					if(Ext.isEmpty(codEntityIds)){
						Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
						grid.getLoadMask().hide();
						return false;
					}
					
					var paramsV = grid.submitParams;
					Ext.apply(paramsV, {'confirmVo.codEntityIds':codEntityIds,'confirmVo.failNotes':text}); 
					Ext.Ajax.request({
						timeout: consumer.codPaid.AJAX_TIMEOUT*1000,
						url:consumer.realPath('confirmFailure.action'),
						method:'post',
						params:paramsV,
						success:function(r,o){
							Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.confirmTheCollectionOfMoneyRemittanceCompleteFailure'),function(){
								//store.load();
								store.load(function(records, operation, success) {
									var result = Ext.decode(operation.response.responseText);  
									var tbars = grid.dockedItems.items[3].query('textfield');
									if(!Ext.isEmpty(result.confirmVo.cods)&& result.confirmVo.cods.length>0){
										tbars[1].setValue(result.totalCount);
										tbars[2].setValue(result.confirmVo.totalAmount);
							      	}else{
							      		tbars[1].setValue(0);
										tbars[2].setValue(0);
							      	}
								});	
							});
							
							grid.getLoadMask().hide();
						},
						exception:function(response){
							grid.getLoadMask().hide();
							var json = Ext.decode(response.responseText);
							Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.confirmTheCollectionOfMoneyRemittanceCompleteAnomaly')+ ":" + json.message);
							
						}
					});
				}
			}
			
			grid.getLoadMask().hide();
		}
	});
}

//确认代收货款反汇款成功
consumer.codPaid.confirmAntiRemittance = function(){
	var grid = Ext.getCmp('FOSS_Consumer_CODPaidConfirmGrid_ID');
	grid.getLoadMask().show();
	var store = grid.getStore();
	var recordArray = grid.getSelectionModel().getSelection();
	
	if(!recordArray || recordArray.length == 0){
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
		grid.getLoadMask().hide();
		return false;
	}
	
	//得到代收货款ID
	var codEntityIds = [];
	for(var i=0;i<recordArray.length;i++){
		var record = recordArray[i];
		var status = record.get('status');
		var waybillNo = record.get('waybillNo');
		
		if(status != 'RD'){
			Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.waybillNoCodStateNotRefundCannotReverseRemitSuccessOperation',[waybillNo]));
			grid.getLoadMask().hide();
			return false;
		}
		
		codEntityIds.push(record.get('id'));
	}
	
	//弹出汇款失败原因
	Ext.Msg.show({
		title : consumer.codPaid.i18n('foss.stl.consumer.cod.informationInput'),
		msg : consumer.codPaid.i18n('foss.stl.consumer.cod.reverseRemittanceSuccessfulReason'),
		width : 300, // 宽度
		buttons : Ext.Msg.OKCANCEL, // 按钮类型,和个数
		multiline : true, // 多行
		fn : function(btn,text,me){
			if(btn == 'ok'){
				//如果原因为空，则返回
				if(Ext.isEmpty(text)){
					Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseInputTheCollectionOfMoneyBackRemittanceSuccessfulReason'));
					grid.getLoadMask().hide();
					return false;
				}
				else{
					
					if(Ext.isEmpty(codEntityIds)){
						Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
						grid.getLoadMask().hide();
						return false;
					}
					
					var paramsV = grid.submitParams;
					Ext.apply(paramsV, {'confirmVo.codEntityIds':codEntityIds,'confirmVo.failNotes':text}); 
					
					Ext.Ajax.request({
						timeout: consumer.codPaid.AJAX_TIMEOUT*1000,
						url:consumer.realPath('confirmAntiRemittance.action'),
						method:'post',
						params:paramsV,
						success:function(r,o){
							//确认代收货款反汇款成功完成
							Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.confirmTheCollectionOfMoneyBackRemittanceSuccessCompleted'),function(){
								//store.load();
								store.load(function(records, operation, success) {
									var result = Ext.decode(operation.response.responseText);  
									var tbars = grid.dockedItems.items[3].query('textfield');
									if(!Ext.isEmpty(result.confirmVo.cods)&& result.confirmVo.cods.length>0){
										tbars[1].setValue(result.totalCount);
										tbars[2].setValue(result.confirmVo.totalAmount);
							      	}else{
							      		tbars[1].setValue(0);
										tbars[2].setValue(0);
							      	}
								});	
							});
							
							grid.getLoadMask().hide();
						},
						exception:function(response){
							grid.getLoadMask().hide();
							var json = Ext.decode(response.responseText);
							//确认代收货款反汇款成功异常
							Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.confirmTheCollectionOfMoneyBackRemittanceSuccessAnomaly')+ ":" + json.message);
						
						}
					});
				}
			}
			grid.getLoadMask().hide();
		}
	});
}

// 代收货款审核通过、退回
consumer.codPaid.auditFailedCOD=function(result){
	var grid = Ext.getCmp('FOSS_Consumer_CODPaidFailedGrid_ID');
	grid.getLoadMask().show();
	var store = grid.getStore();
	var recordArray = grid.getSelectionModel().getSelection();
	
	if(!recordArray || recordArray.length == 0){
		Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.pleaseSelectAtLeastOneDataConductOperation'));
		grid.getLoadMask().hide();
		return false;
	}
	
	//得到ID
	var codIds = [];
	for(var i=0;i<recordArray.length;i++){
		var record = recordArray[i];
		codIds.push(record.get('id'));
	}
	
	Ext.Ajax.request({
		timeout: consumer.codPaid.AJAX_TIMEOUT*1000,
		url:consumer.realPath('processPayFailed.action'),
		method:'post',
		params:{
			codIds:codIds,
			auditResult:result
		},
		success:function(r,o){
			//审核代收货款成功
			if(result == "passed"){
				Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.codAuditSuccess'),function(){
					//store.load();
					store.load(function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);  
						var tbars = grid.dockedItems.items[3].query('textfield');
						if(!Ext.isEmpty(result.codPayFailedVO.cods)&& result.codPayFailedVO.cods.length>0){
							tbars[1].setValue(result.codPayFailedVO.totalRecords);
							tbars[2].setValue(result.codPayFailedVO.totalAmount);
				      	}else{
				      		tbars[1].setValue(0);
							tbars[2].setValue(0);
				      	}
					});	
				});
			}else if(result == "returned"){
				Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.codReturnSuccess'),function(){
					//store.load();
					store.load(function(records, operation, success) {
						var result = Ext.decode(operation.response.responseText);  
						var tbars = grid.dockedItems.items[3].query('textfield');
						if(!Ext.isEmpty(result.codPayFailedVO.cods)&& result.codPayFailedVO.cods.length>0){
							tbars[1].setValue(result.codPayFailedVO.totalRecords);
							tbars[2].setValue(result.codPayFailedVO.totalAmount);
				      	}else{
				      		tbars[1].setValue(0);
							tbars[2].setValue(0);
				      	}
					});	
				});
			}
			grid.getLoadMask().hide();
		},
		exception:function(response){
			grid.getLoadMask().hide();
			var json = Ext.decode(response.responseText);
			//审核代收货款失败
			if(result == "passed"){
				Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.codAuditFailure') + ":" + json.message);
			}else if(result == "returned"){
				Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),consumer.codPaid.i18n('foss.stl.consumer.cod.codReturnFailure') + ":" + json.message);
			}
		}
	});
}

/**
 * 导出execl
 */
consumer.codPaid.exportCod = function(){
	//获取主面板、查询GRID
	var CODQueryForm = Ext.getCmp('FOSS_Consumer_CODPaidFailedQueryForm_ID');
	var grid = Ext.getCmp('FOSS_Consumer_CODPaidFailedGrid_ID');
	
	//提示是否导出
	Ext.Msg.confirm(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),'确定导出代收货款信息吗?',function(btn,text){
		if('yes' == btn){
			
			var params = grid.submitParams;
			if(params!=null && params ==false){
				return false;
			}
			//创建一个form
			if(!Ext.fly('exportCodForm')){
				var frm = document.createElement('form');
				frm.id = 'exportCodForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				timeout: consumer.codPaid.AJAX_TIMEOUT*1000,
				url:consumer.realPath('exprotFailedCod.action'), 
				form: Ext.fly('exportCodForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//获取响应的json字符串
					var jsonText = Ext.decode(response.responseText.trim());
                   	//导出失败
                   	if(jsonText.message!=null&&jsonText.message!=''){
                     	Ext.Msg.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'),jsonText.message);
                     }
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'), jsonText.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(consumer.codPaid.i18n('foss.stl.consumer.common.warmTips'), jsonText.message);
				}
		    });
			
		}
	});	
}

//代收货款汇款确认数据模型
Ext.define('FOSS.Consumer.CODPaidDataModel',{
	extend:'Ext.data.Model',
	idgen: 'uuid',
	idProperty : 'extid',
	fields:['extid','id','codType','waybillNo','amount','payableOrgCode','payableOrgName',
			'status','payeeCode','payeeName','payeeAccount','returnAmount','verifyAmount','bank','province','city',
			'bankSubbranch','publicPrivateFlag',{name:'signTime',mapping:'signDate',type:'date',convert:stl.longToDateConvert},
			'payeePhone',{name:'codExportTime',type:'date',convert:stl.longToDateConvert},'codExportUserCode'
			,'codExportUserName','bankCode','remittanceFailNotes','batchNumber']
});

//代收货款状态Status
Ext.define('FOSS.Consumer.CODPaidConfirm.StatusModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

//代收货款汇款失败审核数据Store
Ext.define('FOSS.Consumer.CODPaidFailedStore',{
	extend:'Ext.data.Store',
	model:'FOSS.Consumer.CODPaidDataModel',
	pageSize:20,
	proxy:{
		timeout: consumer.codPaid.AJAX_TIMEOUT*1000,
		type:'ajax',
		actionMethods : 'post',
		url:consumer.realPath('queryPayFailed.action'),
		reader:{
			type:'json',
			root:'codPayFailedVO.cods',
			totalProperty:'codPayFailedVO.totalRecords'
		}
	}
});

//代收货款汇款确认数据Store
Ext.define('FOSS.Consumer.CODPaidConfirmStore',{
	extend:'Ext.data.Store',
	model:'FOSS.Consumer.CODPaidDataModel',
	pageSize:20,
	proxy:{
		timeout: consumer.codPaid.AJAX_TIMEOUT*1000,
		type:'ajax',
		actionMethods : 'post',
		url:consumer.realPath('queryPayConfirm.action'),
		reader:{
			type:'json',
			root:'confirmVo.cods',
			totalProperty:'totalCount'
		}
	}
});

//代收货款状态Store
/*Ext.define('FOSS.Consumer.CODPaidConfirm.StatusStore',{
	extend:'Ext.data.Store',
	model:'FOSS.Consumer.CODPaidConfirm.StatusModel',
	data:[{
		name:'退款中',
		code:consumer.codPaid.COD__STATUS__RETURNING 
	},{
		name:'已退款',
		code:consumer.codPaid.COD__STATUS__RETURNED
	}]
});*/

//显示代收货款失败审核表格
Ext.define('FOSS.Consumer.CODPaidFailedGrid',{
	extend:'Ext.grid.Panel',
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	title:consumer.codPaid.i18n('foss.stl.consumer.cod.codMessage'),
	stripeRows: true,
	columnLines: true,
	collapsible: false, 
	height:600,
    bodyCls: 'autoHeight',
    bodyStyle:{
    	padding:'0px'
    },
    margin:'10 5 20 5',
    frame: true,
	columns : {
				defaults:{
					sortable:false,
					draggable:false
				},
				items:[{
					xtype:'rownumberer',
					width:40
				},{
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.category'),
					dataIndex : 'codType',
					renderer:consumer.codPaid.codTypeRenderer
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.startDepartment'),
					dataIndex : 'payableOrgName'
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.No'),
					dataIndex : 'waybillNo'
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.codStatus'),
					dataIndex : 'status',
					renderer:consumer.codPaid.codStatusRenderer
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.returnAmount'),
					dataIndex : 'returnAmount',
					renderer : function(value,metadata, record, rowIndex, columnIndex, store,view) {
						
						if(value == null || value == 0){
							var verifyAmount = store.getAt(rowIndex).get('verifyAmount'); 
							if(verifyAmount != null && verifyAmount>0 ){
								return store.getAt(rowIndex).get('amount') - verifyAmount; // 减去 应收冲代收货款应付的金额
							}else{
								return store.getAt(rowIndex).get('amount');
							}
						}else{
							return value;
						}
					}
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.payeeName'),
					dataIndex : 'payeeName'
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.payeeAccount'),
					dataIndex : 'payeeAccount',
					width : 150
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.bank'),
					dataIndex:'bank',
					width:100
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.province'),
					dataIndex:'province'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.city'),
					dataIndex:'city'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.subbranch'),
					dataIndex:'bankSubbranch'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.publicPrivateFlag'),
					dataIndex:'publicPrivateFlag',
					renderer:consumer.codPaid.publicPrivateFlagRenderer
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.signDate'),
					dataIndex:'signTime',
					format : 'Y-m-d G:i:s',
					xtype : 'datecolumn',
					width : 150
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.phoneNumber'),
					dataIndex:'payeePhone'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.bankCode'),
					dataIndex:'bankCode'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.batchNumber'),
					dataIndex:'batchNumber'
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.codExportTime'),
					dataIndex : 'codExportTime',
					format : 'Y-m-d G:i:s',
					xtype : 'datecolumn',
					width : 150
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.codExportUserName'),
					dataIndex:'codExportUserName'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.codExportFailureCause'),
					dataIndex:'remittanceFailNotes',
					width:250
				}]},
	store:null,
	loadMask:null,
	bbar:null,
	getLoadMask:function(){
		var me = this;
		me.loadMask = Ext.getCmp('FOSS_Consumer_CODPaidFailedGrid_LoadMask_ID');
		if(Ext.isEmpty(me.loadMask)){
			me.loadMask = new Ext.LoadMask(me.up('panel'),{
				id:'FOSS_Consumer_CODPaidFailedGrid_LoadMask_ID',
				msg:consumer.codPaid.i18n('foss.stl.consumer.cod.dataLoading'),
				autoShow:false
			});
		}
		return me.loadMask;
	},
	viewConfig : {       
		    enableTextSelection: true,
	        forceFit : true,
	        stripeRows: false,//显示重复样式，不用隔行显示
	        emptyText : consumer.codPaid.i18n('foss.stl.consumer.common.emptyText')
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
		me.store = Ext.create('FOSS.Consumer.CODPaidFailedStore',{
			listeners : {
				beforeload : function(store, operation, eOpts) {
					Ext.apply(me.submitParams, {
				          "limit":operation.limit,
				          "page":operation.page,
				          "start":operation.start
				          }); 
					
					Ext.apply(operation, {
		   				params : me.submitParams
		   			});
				}
			}
		});
		me.bbar = Ext.create('Ext.toolbar.Toolbar',{
			layout:'column',	
			defaults:{
				margin:'0 0 5 0'
			},
			items: [{
						xtype:'textfield',
						readOnly:true,
						name:'total',
						columnWidth:.05,
						labelSeparator: '',
						labelWidth:40,
						fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.total')
					},{
						xtype:'textfield',
						readOnly:true,	
						name:'codTotalNumber',
						columnWidth:.2,
						labelWidth:60,
						fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.allNum')
					},{
						xtype:'textfield',
						readOnly:true,	
						name:'codTotalAmount',
						columnWidth:.2,
						labelWidth:100,
						fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.codAmount')
					},
			        Ext.create('Deppon.StandardPaging', {
							store: me.store,
							pageSize: 20,
							columnWidth:1,
							plugins: Ext.create('Deppon.ux.PageSizePlugin', {
								//设置分页记录最大值，防止输入过大的数值
								maximumSize: 500
							})
					}),{
							xtype:'container',
							columnWidth:0.75,
							html:'&nbsp;',
							border:0
						},{
							xtype:'button',
							text:consumer.codPaid.i18n('foss.stl.consumer.cod.RA'),
							disabled:!consumer.codPaid.isPermission('/stl-web/consumer/processPayFailed.action'),
							hidden:!consumer.codPaid.isPermission('/stl-web/consumer/processPayFailed.action'),
							columnWidth:0.1,
							handler:function(){
								consumer.codPaid.auditFailedCOD('passed');
							}
						},{
							xtype:'container',
							columnWidth:0.05,
							html:'&nbsp;',
							border:0
						},{
							xtype:'button',
							text:consumer.codPaid.i18n('foss.stl.consumer.common.return'),
							disabled:!consumer.codPaid.isPermission('/stl-web/consumer/processPayFailed.action'),
							hidden:!consumer.codPaid.isPermission('/stl-web/consumer/processPayFailed.action'),
							columnWidth:0.1,
							handler:function(){
								consumer.codPaid.auditFailedCOD('returned')
							}
						}
					]
		})
		me.callParent([cfg]);
	}
});

//显示代收货款确认表格
Ext.define('FOSS.Consumer.CODPayConfirmGrid',{
	extend:'Ext.grid.Panel',
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners : {
			deselect:function(rthis,record,index,eOpts){  
				rthis.view.getEl().dom.onclick = function(){ 
					consumer.codPaid.isAllSelect='false';
					Ext.getCmp('Foss_codPaid_AllFreezeStatu_ID').setValue(false);
                }; 	 
            }  
        
		}
	}),
	title:consumer.codPaid.i18n('foss.stl.consumer.cod.codMessage'),
	stripeRows: true,
	columnLines: true,
	collapsible: false, 
	height:600,
    bodyCls: 'autoHeight',
    bodyStyle:{
    	padding:'0px'
    },
    margin:'10 5 20 5',
    frame: true,
	columns : {
				defaults:{
					sortable:false,
					draggable:false
				},
				items:[{
					xtype:'rownumberer',
					width:40
				},{
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.category'),
					dataIndex : 'codType',
					renderer:consumer.codPaid.codTypeRenderer
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.startDepartment'),
					dataIndex : 'payableOrgName'
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.No'),
					dataIndex : 'waybillNo'
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.codStatus'),
					dataIndex : 'status',
					renderer:consumer.codPaid.codStatusRenderer
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.returnAmount'),
					dataIndex : 'amount',
					renderer : function(value,metadata, record, rowIndex, columnIndex, store,view) {
						var returnAmount = store.getAt(rowIndex).get('returnAmount'); 
						if(returnAmount == null || returnAmount == 0){
							var verifyAmount = store.getAt(rowIndex).get('verifyAmount'); 
							if(verifyAmount != null && verifyAmount>0 ){
								return value - verifyAmount; // 减去 应收冲代收货款应付的金额
							}else{
								return value;
							}
						}else{
							return returnAmount;
						}
					}
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.payeeName'),
					dataIndex : 'payeeName'
				}, {
					header :consumer.codPaid.i18n('foss.stl.consumer.cod.payeeAccount'),
					dataIndex : 'payeeAccount',
					width : 150
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.bank'),
					dataIndex:'bank',
					width:100
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.province'),
					dataIndex:'province'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.city'),
					dataIndex:'city'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.subbranch'),
					dataIndex:'bankSubbranch'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.publicPrivateFlag'),
					dataIndex:'publicPrivateFlag',
					renderer:consumer.codPaid.publicPrivateFlagRenderer
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.signDate'),
					dataIndex:'signTime',
					format : 'Y-m-d G:i:s',
					xtype : 'datecolumn',
					width : 150
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.phoneNumber'),
					dataIndex:'payeePhone'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.bankCode'),
					dataIndex:'bankCode'
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.batchNumber'),
					dataIndex:'batchNumber'
				}, {
					header : consumer.codPaid.i18n('foss.stl.consumer.cod.codExportTime'),
					dataIndex : 'codExportTime',
					format : 'Y-m-d G:i:s',
					xtype : 'datecolumn',
					width : 150
				},{
					header:consumer.codPaid.i18n('foss.stl.consumer.cod.codExportUserName'),
					dataIndex:'codExportUserName'
				}]},
	store:null,
	bbar:null,
	loadMask:null,
	//创建LoadMask
	getLoadMask:function(){
		var me = this;
		me.loadMask = Ext.getCmp('FOSS_Consumer_CODPaidConfirmGrid_LoadMask_ID');
		if(Ext.isEmpty(me.loadMask)){
			me.loadMask = new Ext.LoadMask(me.up('panel'),{
				id:'FOSS_Consumer_CODPaidConfirmGrid_LoadMask_ID',
				msg:consumer.codPaid.i18n('foss.stl.consumer.cod.dataLoading'),
				autoShow:false
			});
		}
		return me.loadMask;
	},
	viewConfig : {    
		    enableTextSelection: true,
	        forceFit : true,
	        stripeRows: false,//显示重复样式，不用隔行显示
	        emptyText : consumer.codPaid.i18n('foss.stl.consumer.common.emptyText')
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	constructor:function(config){
		var me = this;
		cfg = Ext.apply({},config);
		
		//创建Store
		me.store = Ext.create('FOSS.Consumer.CODPaidConfirmStore',{
			listeners : {
				beforeload : function(store, operation, eOpts) {
					
					Ext.apply(me.submitParams, {
				          "limit":operation.limit,
				          "page":operation.page,
				          "start":operation.start
				          }); 
					
					Ext.apply(operation, {
		   				params : me.submitParams
		   			});
					
					consumer.codPaid.isAllSelect='false';
					Ext.getCmp('Foss_codPaid_AllFreezeStatu_ID').setValue(false);
				}
		
				/*load:function(){
					consumer.codPaid.isAllSelect='false';
					Ext.getCmp('Foss_codPaid_AllFreezeStatu_ID').setValue(false);
						
				}*/
			}
		});
		me.bbar = Ext.create('Ext.toolbar.Toolbar',{
			layout:'column',	
			defaults:{
				margin:'0 0 5 0'
			},
			items: [
					{
						xtype:'textfield',
						readOnly:true,
						name:'total',
						columnWidth:.05,
						labelSeparator: '',
						labelWidth:40,
						fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.total')
					},{
						xtype:'textfield',
						readOnly:true,	
						name:'codTotalNumber',
						columnWidth:.2,
						labelWidth:60,
						fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.allNum')
					},{
						xtype:'textfield',
						readOnly:true,	
						name:'codTotalAmount',
						columnWidth:.2,
						labelWidth:100,
						fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.codAmount')
					},
			        Ext.create('Deppon.StandardPaging', {
							store: me.store,
							pageSize: 20,
							columnWidth:1,
							plugins: Ext.create('Deppon.ux.PageSizePlugin', {
								//设置分页记录最大值，防止输入过大的数值
								maximumSize: 500
							})
					}),{
							xtype:'container',
							columnWidth:0.6,
							html:'&nbsp;',
							border:0
						},{
							xtype:'button',
							text:consumer.codPaid.i18n('foss.stl.consumer.cod.remitSuccess'),
							disabled:!consumer.codPaid.isPermission('/stl-web/consumer/confirmSuccess.action'),
							hidden:!consumer.codPaid.isPermission('/stl-web/consumer/confirmSuccess.action'),
							columnWidth:0.1,
							handler:consumer.codPaid.confirmSuccess
						},{
							xtype:'container',
							columnWidth:0.05,
							html:'&nbsp;',
							border:0
						},{
							xtype:'button',
							text:consumer.codPaid.i18n('foss.stl.consumer.cod.reverseRemittanceSuccess'),
							disabled:!consumer.codPaid.isPermission('/stl-web/consumer/confirmAntiRemittance.action'),
							hidden:!consumer.codPaid.isPermission('/stl-web/consumer/confirmAntiRemittance.action'),
							columnWidth:0.1,
							handler:consumer.codPaid.confirmAntiRemittance
						},{
							xtype:'container',
							columnWidth:0.05,
							html:'&nbsp;',
							border:0
						},{
							xtype:'button',
							text:consumer.codPaid.i18n('foss.stl.consumer.cod.remitError'),
							disabled:!consumer.codPaid.isPermission('/stl-web/consumer/confirmFailure.action'),
							hidden:!consumer.codPaid.isPermission('/stl-web/consumer/confirmFailure.action'),
							columnWidth:0.1,
							handler:consumer.codPaid.confirmFailure
						}
					]
		});
		me.callParent([cfg]);
	}
});

//汇款失败审核查询
Ext.define('FOSS.Consumer.CODPaidFailedQueryForm',{
	extend:'Ext.form.Panel',
	margin:'10 5 10 5',
	layout:'hbox',
	title:consumer.codPaid.i18n('foss.stl.consumer.cod.codQueryBuilder'),
	frame:true,
	bodyCls: 'autoHeight',
	layout:'column',
	defaults : {
		msgTarget : 'qtip',
		margin : '5 10 5 10',
		allowBlank : true
	},
	items:[{
			xtype:'fieldcontainer',
			columnWidth:1,
			layout:'hbox',
			items:[{
				xtype:'radio',
				name:'codPayFailedVO.queryCondition',
				inputValue:'all',
				boxLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.queryAll'),
				width:100,
				checked:true,
				listeners:{
					change:function(_this,newValue,oldValue,eOpts){
						var waybillNos =this.up('form').getForm().findField('codPayFailedVO.waybillNos');
						if(newValue){
							waybillNos.disable();
						}
						else{
							waybillNos.enable();
						}
					}
				}
			},{
				xtype:'radio',
				name:'codPayFailedVO.queryCondition',
				inputValue:'waybill',
				boxLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.queryNo'),
				width:70
			},{
				xtype:'textarea',
				name:'codPayFailedVO.waybillNos',
				emptyText:consumer.codPaid.i18n('foss.stl.consumer.cod.eightToTenMostInputFiftyWaybillNumber'),
				width:300,
				height:60,
				//regex:/^([0-9]{8,10},?){0,50}$/i,
				//354658-校验至14位运单号
				regex:/^([0-9]{8,14})(,[0-9]{8,14}){0,49},?$/i,
				regexText:consumer.codPaid.i18n('foss.stl.consumer.cod.eightToTenMostInputFiftyWaybillNumber'),
				disabled:true
			}]
	},{
		xtype:'button',
		text:consumer.codPaid.i18n('foss.stl.consumer.common.reset'),
		columnWidth:0.1,
		handler:function(){
			//得到form表单
			var form = this.up('form').getForm();
			
			//重置
			form.reset();
			
		}
	},{
		xtype:'component',
		columnWidth:0.8,
		html:'&nbsp;'
	},{
		xtype:'button',
		cls:'yellow_button',
		text:consumer.codPaid.i18n('foss.stl.consumer.common.query'),
		columnWidth:0.1,
		handler:consumer.codPaid.queryCODPayFailed
	}]
});

//汇款确认查询表单
Ext.define('FOSS.consumer.CODPayConfirmQueryForm',{
	extend:'Ext.form.Panel',
	margin:'10 5 10 5',
	layout:'hbox',
	title:consumer.codPaid.i18n('foss.stl.consumer.cod.codQueryBuilder'),
	frame:true,
	bodyCls: 'autoHeight',
	layout:'column',
	defaults : {
		msgTarget : 'qtip',
		margin : '5 10 5 10',
		allowBlank : true,
		labelWidth:85
	},
	items:[{
		xtype:'textarea',
		name:'confirmVo.waybillNoString',
		emptyText:consumer.codPaid.i18n('foss.stl.consumer.cod.eightToTenMostInputFiftyWaybillNumber'),
		columnWidth:0.4,
		height:88,
		//regex:/^([0-9]{8,10},?){0,50}$/i,
		//354658-校验至14位运单号
		regex:/^([0-9]{8,14})(,[0-9]{8,14}){0,49},?$/i,
		regexText:consumer.codPaid.i18n('foss.stl.consumer.cod.eightToTenMostInputFiftyWaybillNumber'),
		fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.wayBillNo')
	},{
		xtype:'textfield',
		name:'confirmVo.batchNumber',
		columnWidth:0.3,
		fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.batchNumber')
	}, {
		xtype : 'combo',
		name : 'confirmVo.selectStatus',
		fieldLabel : consumer.codPaid.i18n('foss.stl.consumer.cod.codStatus'),
		store:FossDataDictionary.getDataDictionaryStore('COD__STATUS',null,null,[consumer.codPaid.COD__STATUS__RETURNING,consumer.codPaid.COD__STATUS__RETURNED]),
		/*store : Ext.create('FOSS.Consumer.CODPaidConfirm.StatusStore'),*/
		displayField : 'valueName',
		value:'RG',//默认退款中
		valueField : 'valueCode',
		queryMode : 'local',
		columnWidth:0.3
	},{
		xtype:'commonemployeeselector',
		name:'confirmVo.exportUserCode',
		columnWidth:0.3,
		fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.payerName')
	},{
		xtype:'container',
		html:'&nbsp;',
		height:24,
		columnWidth:0.3
	},{
		xtype:'datetimefield_date97',
		fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.startTime'),
		id:'FOSS_Consumer_CODPayConfirmQueryForm_StartTime_ID',
		time:true,
		name:'confirmVo.exportStartTimeString',
		columnWidth:0.3,
		editable:false,
		value: Ext.Date.format(stl.getTargetDate(new Date(),-1), 'Y-m-d')+' 00:00:00',
		dateConfig: {
			el: 'FOSS_Consumer_CODPayConfirmQueryForm_StartTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd'
		}
	},{
		xtype:'datetimefield_date97',
		fieldLabel:consumer.codPaid.i18n('foss.stl.consumer.cod.endTime'),
		id:'FOSS_Consumer_CODPayConfirmQueryForm_EndTime_ID',
		time:true,
		name:'confirmVo.exportEndTimeString',
		columnWidth:0.3,
		editable:false,
		value: Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
		dateConfig: {
			el: 'FOSS_Consumer_CODPayConfirmQueryForm_EndTime_ID-inputEl',
			dateFmt: 'yyyy-MM-dd'
		}
	},{
		xtype:'button',
		text:consumer.codPaid.i18n('foss.stl.consumer.common.reset'),
		columnWidth:0.1,
		handler:function(){
			this.up('form').getForm().reset();
			consumer.codPaid.isAllSelect='false';
			Ext.getCmp('Foss_codPaid_AllFreezeStatu_ID').setValue(false);
		}
	},{
		xtype:'component',
		columnWidth:0.8,
		html:'&nbsp;'
	},{
		xtype:'button',
		cls:'yellow_button',
		text:consumer.codPaid.i18n('foss.stl.consumer.common.query'),
		columnWidth:0.1,
		handler:consumer.codPaid.queryCODPayConfirm
	}]
});

Ext.onReady(function(){
	Ext.QuickTips.init();
	
	//创建付款确认QUERY表单
	var CODPaidConfirmQueryForm = Ext.getCmp('FOSS_Consumer_CODPaidConfirmQueryForm_ID');
	if(Ext.isEmpty(CODPaidConfirmQueryForm)){
		CODPaidConfirmQueryForm = Ext.create('FOSS.consumer.CODPayConfirmQueryForm',{
			id:'FOSS_Consumer_CODPaidConfirmQueryForm_ID',
			style:{
				width:'98%'
			}
		});
	}
	
	//创建付款失败审核QUERY表单
	var CODPaidFailedQueryForm = Ext.getCmp('FOSS_Consumer_CODPaidFailedQueryForm_ID');
	if(Ext.isEmpty(CODPaidFailedQueryForm)){
	 	CODPaidFailedQueryForm = Ext.create('FOSS.Consumer.CODPaidFailedQueryForm',{
			id:'FOSS_Consumer_CODPaidFailedQueryForm_ID',
			style:{
				width:'98%'
			}
		});
	}
	
	//创建付款确认GRID
	var CODPaidConfirmGrid = Ext.getCmp('FOSS_Consumer_CODPaidConfirmGrid_ID');
	if(Ext.isEmpty(CODPaidConfirmGrid)){
		//创建显示表格
		CODPaidConfirmGrid = Ext.create('FOSS.Consumer.CODPayConfirmGrid',{
			id : 'FOSS_Consumer_CODPaidConfirmGrid_ID',
			hidden:true,
			tbar : [{
				xtype:'checkboxfield',
				//boxLabel  : consumer.codPaid.i18n('foss.stl.consumer.cod.allFrozenDocument'),
				boxLabel  : "全选所有",
				id : 'Foss_codPaid_AllFreezeStatu_ID',
				listeners:{
	                 render:function(el){
	                     el.getEl().addCls('label-class-green');
	                 },
	                 afterrender:function(obj){  
	                     obj.getEl().dom.onclick = function(){ 
	                    	 var grid = Ext.getCmp('FOSS_Consumer_CODPaidConfirmGrid_ID');
	        				 grid.getLoadMask().show();
	        				 var checkMsg = Ext.getCmp('Foss_codPaid_AllFreezeStatu_ID').getValue();
	        				 var selectionModel = grid.getSelectionModel();
	        				 var store = grid.getStore();
	        				 var codPaidModel = Ext.create('FOSS.Consumer.CODPaidDataModel');
	        				 // 设置冻结单据选择状态
	        				 var ffStore = [];
	        				 for (var i = 0; i < store.getCount(); i++) {
	        					 var record = store.getAt(i);
	        					 var statu =record.get('status');
	        					  ffStore.push(record);
	        				 }
	        				 // 判断是全部选中，还是全部取消选中
	        				 if( checkMsg ){
	        					 selectionModel.select(ffStore);
	        					 consumer.codPaid.isAllSelect='true';
	        					 consumer.codPaid.setSelectParam(grid.submitParams,consumer.codPaid.isAllSelect);
	        				 }else{
	        					 selectionModel.deselect(ffStore);
	        					 consumer.codPaid.isAllSelect='false';
	        					 consumer.codPaid.setSelectParam(grid.submitParams,consumer.codPaid.isAllSelect);
	        				 }
	        				 grid.getLoadMask().hide();
	                     }; 	 
		             }  
	             }
			}],
			style:{
				width:'98%'
			}
		});
	}
	
	//创建付款失败审核GRID
	var CODPaidFailedGrid = Ext.getCmp('FOSS_Consumer_CODPaidFailedGrid_ID');
	if(Ext.isEmpty(CODPaidFailedGrid)){
		//创建显示表格
		CODPaidFailedGrid = Ext.create('FOSS.Consumer.CODPaidFailedGrid',{
			id : 'FOSS_Consumer_CODPaidFailedGrid_ID',
			hidden:true,
			tbar : [{
				xtype:'button',
				text:consumer.codPaid.i18n('foss.stl.consumer.common.export'),
				columnWidth:.1,
				handler: consumer.codPaid.exportCod
			}],
			style:{
				width:'98%'
			}
		});
	}
	
	//创建TAB控件
	var tabPanel = Ext.create('Ext.tab.Panel',{
		frame:false,
		bodyCls: 'autoHeight',
		cls: 'innerTabPanel',
		activeTab: 0,
		items:[{
			title:consumer.codPaid.i18n('foss.stl.consumer.cod.remitConfirm'),
			tabConfig: {
				width: 120
			},
			items:[CODPaidConfirmQueryForm,CODPaidConfirmGrid,{
				xtype:'panel',
				height:5,
				border:0,
				html:'&nbsp;'
			}]
		},{
			title:consumer.codPaid.i18n('foss.stl.consumer.cod.remitErrorAudit'),
			tabConfig: {
				width: 120
			},
			items:[CODPaidFailedQueryForm,CODPaidFailedGrid,{
				xtype:'panel',
				height:5,
				border:0,
				html:'&nbsp;'
			}]
		}]
	});
	
	//显示到JSP页面
	Ext.create('Ext.panel.Panel',{
		id:'T_consumer-codPaid_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [tabPanel],
		renderTo: 'T_consumer-codPaid-body'
	});
});