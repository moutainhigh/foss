/**
 * 报表清单model									Foss.querying.salesstatement.SMSTemplateEntityModel
 * 报表清单store									Foss.querying.salesstatement.ResultDtoStore
 * 报表清单form									Foss.querying.salesstatement.QueryConditionForm
 * 报表清单grid									Foss.querying.salesstatement.QueryResultGrid
 */

//------------------------------------常量和公用方法----------------------------------
//YYBB 营业报表，CZQD操作清单，【BY，CQ】对应后台常量值com.deppon.foss.base.util.ComnConst 【BUSINESS_REPORT,OPERATION_LIST】
querying.salesstatement.gridType = {yybb:querying.salesstatement.i18n('foss.querying.statementsList'),czqd:querying.salesstatement.i18n('foss.querying.operatingList'),yb:'BUSINESS_REPORT',cq:'OPERATION_LIST'};	//营业报表   //操作清单
querying.salesstatement.timeType = {pck:'PCK',sh:'SH'};												//YYBB 营业报表，CZQD操作清单
querying.salesstatement.deptType = {shbm:'SHBM',zdbm:'ZDBM'};										//YYBB 营业报表，CZQD操作清单
querying.salesstatement.isInvalid = {y:'Y',n:'N'};													//	y生效，n作废
querying.salesstatement.receivingType = {rc:'RECEIVING_CLEARANCE',igs:'INTO_GANGPA_SEND'};			//	收获类型【收货出港，进港派送】
querying.salesstatement.regLimit = {
		m:/^.{0,40}$/,mobil:/^1\d{10}$/,w:/^\d{8,9}$/,k:/^(\d{8,9}|\d{10,11})$/
	};
	

 
 Ext.define('Foss.querying.salesstatement.custLeaveMonthStore',{  
    extend:'Ext.data.Store',  
    fields: [
        {name: 'valueCode', type: 'string'},
 		{name: 'valueName', type: 'string'}
     ],  
    data : [
     	{'valueCode':'' ,	'valueName':'全部'},
        {'valueCode': 'VIP',    valueName: 'VIP群'},
        {'valueCode': 'OMNI-ACTIVE',    valueName: '全网活跃群'},
        {'valueCode': 'LINER-ACTIVE',    valueName: '专线活跃群'},
        {'valueCode': 'OMNI-UNACTIVE',    valueName: '全网低频群'},
        {'valueCode': 'LINER-UNACTIVE',    valueName: '专线低频群'},
        {'valueCode': 'LOW-VALUE',    valueName: '低价值群'},
        {'valueCode': 'NEWCUST',    valueName: '新客户群'}
        
     ]
  
}); 

var querying_salesstatement_custLeaveMonthStore = Ext.create('Foss.querying.salesstatement.custLeaveMonthStore'); 
/**
 * 不再需要根据营业部信息去判断出货类型,注释掉此代码
 * 2013-03-01
 * 张斌
 * **/
//querying.salesstatement.saleDepartment = {};														//当前登录用户所在营业部
querying.salesstatement.packageParamType = {p:'AJAX'};												//组装的参数格式
querying.salesstatement.product = null;		//价格对象	

/**
 * 添加常量
 * 2013-03-01
 * 张斌
 * **/
querying.salesstatement.receiveMethod = 'PICKUPGOODSHIGHWAYS';//提货方式数据字典
querying.salesstatement.paidMethod = 'SETTLEMENT__PAYMENT_TYPE';//付款方式
querying.salesstatement.custleavemonth = 'CRM_CUSTOMER_GROUP';//发货人客户分群
querying.salesstatement.receivingDefault = FossUserContext.getCurrentDept().salesDepartment =='Y'? querying.salesstatement.receivingType.rc:querying.salesstatement.receivingType.igs;//'RECEIVING_CLEARANCE';//收货类型——收货出港（默认）
querying.salesstatement.threeLevelProduct = [];//三级产品
querying.salesstatement.deleteWaybillNos =new Array();//前台中废除的单号
querying.salesstatement.direction ='';	//排序（升序，还是降序）
querying.salesstatement.sortField ='';//排序字段的常量；
/**
 * 添加开始,结束时间方法 day--
 * 比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 */
querying.salesstatement.getStartTime =function(date,day){
	var d, s, t, t2; 
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};
querying.salesstatement.getEndTime =function(date,day){
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};

querying.salesstatement.getMyStore = function(){
	var store = FossDataDictionary.getDataDictionaryStore('CRM_CUSTOMER_GROUP');
	
	if (Ext.isEmpty(store)) {
		return store;
	} else {
		for (var i = 0; i < store.getCount(); i++) {
			if (store.getAt(i).get('valueCode') == 'OMNI-UNACTIVE') {
				store.remove(store.getAt(i));
			}
		}
	return store;
	}
	
}

/**
 * 不再需要根据营业部信息去判断出货类型，出货类型默认为“收货出港”
 * 2013-03-01
 * 张斌
 * **/
//组装的参数格式
/*querying.salesstatement.initSearchForm = function(queryForm,saleDepartment){
	if(!Ext.isEmpty(saleDepartment)){
		var form = queryForm.getForm()
			,receiveType = form.findField('receiveType')
			,sh = Ext.getCmp(querying.salesstatement.timeType.sh)
			,pck = Ext.getCmp(querying.salesstatement.timeType.pck)
			,deliverDept  = form.findField('deliverDept')
			,deptCode  = form.findField('deptCode');
	    //可出发(营业部) 营业部进入，收货类型默认显示收货出港，且日期默认选择收货日期
		if(querying.salesstatement.isInvalid.y === saleDepartment.leave){
			receiveType.setValue(querying.salesstatement.receivingType.rc);
			sh.setValue(true);
			pck.setValue(false);
			deptCode.setReadOnly(false);
			deliverDept.setReadOnly(true);
			deliverDept.setValue(null);
			deptCode.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());//部门
		}
		//可到达不可出发(派送部门) 派送部进入，收获类型默认显示进港派送，且时间默认选择派送出港时间）收货部门（制单部门）/派送部门默认为空，应该显示本部门
		else if(querying.salesstatement.isInvalid.y === saleDepartment.arrive){
			receiveType.setValue(querying.salesstatement.receivingType.igs);
			sh.setValue(false);
			pck.setValue(true);
			deliverDept.setReadOnly(false);
			deptCode.setReadOnly(true);
			deptCode.setValue(null);
			deliverDept.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());//部门
		}
	}
};*/
/**
 * 重新构造初始化方法
 * 2013-03-01
 * 张斌
 * **/
querying.salesstatement.initSearchForm = function(queryForm){
		var form = queryForm.getForm()
			,receiveType = form.findField('receiveType')
			,sh = Ext.getCmp(querying.salesstatement.timeType.sh)
			,pck = Ext.getCmp(querying.salesstatement.timeType.pck)
			,deliverDept  = form.findField('deliverDept')
			,deptCode  = form.findField('deptCode');
			sh.setValue(true);
			pck.setValue(false);
			receiveType.setValue(querying.salesstatement.receivingDefault);
			//判断收货类型要是收货出港的话，
			if(receiveType.getValue()== querying.salesstatement.receivingType.rc){
				deptCode.setReadOnly(false);
				deptCode.allowBlank = false;
				deliverDept.setReadOnly(true);
				deliverDept.allowBlank = true;
				deliverDept.setValue(null);
				deptCode.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());//部门
			}else{
				//否则就是为派送进港
				deliverDept.setReadOnly(false);
				deliverDept.allowBlank = false;
				deptCode.setReadOnly(true);
				deptCode.allowBlank = true;
				deptCode.setValue(null);
				deliverDept.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());//部门
			}
	
};
//删除一条数据
querying.salesstatement.deleteEntity = function(grid){
	var selection=grid.getSelectionModel().getSelection()
		,record = Ext.create('Foss.querying.salesstatement.TotalModel');
	if(selection.length<=0){
		Ext.MessageBox.alert(querying.salesstatement.i18n('foss.querying.remind'),querying.salesstatement.i18n('foss.querying.selectOneData')); //提醒     请选择一条数据！
		return;
	}
	var waybillNos =new Array();
	//获取已经废除的单号
	for(var i = 0 ; i<selection.length ; i++){
		waybillNos.push("'"+selection[i].get('waybillNo')+"'");
	}
	//设置给全局变量
	querying.salesstatement.deleteWaybillNos.push(waybillNos.toString());
	
	for(var i = 0;i<selection.length;i++){
		grid.store.remove(selection[i]);
		// 汇总计算上 减去该数据额度
		var form = grid.up('container').down('form');
		form.getForm().updateRecord(record);
		var totalEntity = record.data,totalSelectEntity = selection[i].data;
		//'件数',
		totalEntity.goodsQtyTotal=record.get('goodsQtyTotal') - totalSelectEntity.goodsQtyTotal;
		//'重量',
		totalEntity.goodsWeightTotal=record.get('goodsWeightTotal') - totalSelectEntity.goodsWeightTotal;
		//'体积',
		totalEntity.goodsVolumeTotal=record.get('goodsVolumeTotal') - totalSelectEntity.goodsVolumeTotal;
		//'预付总额',
		totalEntity.prePayAmountTotal=record.get('prePayAmountTotal') - totalSelectEntity.prePayAmount;
		//'到付总额',
		totalEntity.toPayAmountTotal=record.get('toPayAmountTotal') - totalSelectEntity.toPayAmount;
		//'代收贷款',
		totalEntity.codAmountTotal=record.get('codAmountTotal') - totalSelectEntity.codAmount;
		//'包装费',
		totalEntity.packageFeeTotal=record.get('packageFeeTotal') - totalSelectEntity.packageFee;
		//保价费
		totalEntity.insuranceFee =record.get('insuranceFee')-totalSelectEntity.insuranceFee;
	    //票数
	    totalEntity.pageCount = totalEntity.pageCount - 1;
		//'收入总额',   预付总额+到付总额-代收貨款
		totalEntity.amountTotal=totalEntity.prePayAmountTotal + totalEntity.toPayAmountTotal-totalEntity.codAmountTotal;
	    form.loadRecord(Ext.create('Foss.querying.salesstatement.TotalModel',totalEntity));
	}
};
//获得 查询条件参数
querying.salesstatement.searchConditionParams = function(mainPanel,tab,queryForm,packageParamType){
	var objectVo ={},condition = {};
	queryForm.updateRecord(queryForm.record);
	var entity = queryForm.record.data;
	//该方法，只是针对前台页面运输性质，只选择全部或者什么都不选择的时候（也是全部）的值是'',进行处理，空变成'all'
	//这样处理的原因是：打印报表进行预览的时候，会将空或者' '带空格的这种，传到接送货接口中进行调用时候，
	//会变成null,会报空指针异常。处理之后就OK了,当多选的时候，将字符串里面是带逗号的，一并传到接送货后台进行切，转换集合
	if(Ext.isEmpty(entity.productCode)){
		entity.productCode = 'all';
	}
	var params = {
		  //收货类型
		  'objectVo.condition.receiveType':entity.receiveType,
	      //2、运输性质： 产品条目编码. 
	      'objectVo.condition.productCode':entity.productCode,
	      //目的站： 到达网点/简称. 
	      'objectVo.condition.targetOrgCode':entity.targetOrgCode,
	      // 起始单号 
	      'objectVo.condition.startWaybillNo':entity.startWaybillNo,
	      // 结束单号： 运单号 
	      'objectVo.condition.endWaybillNo':entity.endWaybillNo,
	      //  发货人： 发货人姓名. 
	      'objectVo.condition.deliveryCustomerContact':entity.deliveryCustomerContact,
	      // 收货人： 收货人姓名. 
	      'objectVo.condition.receiveCustomerContact':entity.receiveCustomerContact,
	      // 发货联系人手机. 
	      //'objectVo.condition.deliveryCustomerMobilephone':entity.deliveryCustomerMobilephone,
	      // 收货客户手机. 
	      //'objectVo.condition.receiveCustomerMobilephone':entity.receiveCustomerMobilephone,
	      // 发货人电话. 
	      //'objectVo.condition.deliveryCustomerPhone':entity.deliveryCustomerPhone,
	      // 收货人电话.
	      //'objectVo.condition.receiveCustomerPhone':entity.receiveCustomerPhone,
	      // 运单号
	      'objectVo.condition.waybillNo':entity.waybillNo,
	      // 发货人客户分群
	      'objectVo.condition.flabelleavemonth':entity.flabelleavemonth,
	      // 付款方式
	      'objectVo.condition.payMethod':entity.payMethod,
	      // 始发配载部门
	      'objectVo.condition.loadOrgCode':entity.loadOrgCode,
	      //获取已经废除的单号列表
	      'objectVo.condition.deleteWaybillNos':querying.salesstatement.deleteWaybillNos.toString(),
	      //获取前台排序的字段
	      'objectVo.condition.sortField':querying.salesstatement.sortField,
	      //获取前台排序的类型
	      'objectVo.condition.sortType':querying.salesstatement.direction
	   },tabType = {},timeType = {},deptType ={};
	//派送部门
	params['objectVo.condition.deliverDept'] = queryForm.findField('deliverDept').getValue();
	var startTime = queryForm.findField('startTime').getValue(),
	endTime = queryForm.findField('endTime').getValue();
	//查询时间段 不能为空
	if(Ext.isEmpty(startTime) || Ext.isEmpty(endTime)){
		Ext.Msg.alert(querying.salesstatement.i18n('foss.querying.fossAlert'),querying.salesstatement.i18n('foss.querying.queryTimeNotEmpty'));  //FOSS提醒    查询时间不能为空！
		return;
	}else{
		//定义两个用来判断
		 var getStartTime = Ext.Date.parse(startTime, 'Y-m-d H:i:s'); 
	     var getEndTime = Ext.Date.parse(endTime, 'Y-m-d H:i:s');
		/**
		 * 添加查询时间逻辑
		 * 1、起始日期大于截止日期
		 * 2、同一自然月
		 * 2013-03-01
		 * 张斌
		 * **/
		if(getStartTime>getEndTime){
			Ext.Msg.alert(querying.salesstatement.i18n('foss.querying.fossAlert'),querying.salesstatement.i18n('foss.querying.starNotBigEndTime'));  //FOSS提醒    起始时间不能大于结束时间！
			return;
		}
//		if(!(getStartTime.getFullYear()==getEndTime.getFullYear()&&getStartTime.getMonth()==getEndTime.getMonth())){
//			Ext.Msg.alert(querying.salesstatement.i18n('foss.querying.fossAlert'),querying.salesstatement.i18n('foss.querying.mustSearchOneMonth'));  //FOSS提醒      只能查询同一个自然月的数据！
//			return;
//		}
		//获取开始时间到结束的时间的天數差
		var diff =Math.round((getEndTime.getTime()-getStartTime.getTime())/(86400*1000));
		if(diff>7){
			Ext.Msg.alert(querying.salesstatement.i18n('foss.querying.fossAlert'),querying.salesstatement.i18n('foss.querying.mustSearchSevenDay'));  //FOSS提醒      时间！
			return;
		}
	}
	//排送出库日期queryForm.findField('timeType')
	if(Ext.getCmp(querying.salesstatement.timeType.pck).getValue()){
		// 派送开始时间
		params['objectVo.condition.beginDeliverTime'] = startTime;
		// 派送结束时间
		params['objectVo.condition.endDeliverTime'] = endTime;
		// 起始开单日期： 运单开单日期. 
		params['objectVo.condition.startBillTime'] = null;
		// 结束开单日期： 运单结束日期.
		params['objectVo.condition.endBilltime'] = null;
	}else if(Ext.getCmp(querying.salesstatement.timeType.sh).getValue()){
		//收货日期
		// 起始开单日期： 运单开单日期. 
		params['objectVo.condition.startBillTime'] = startTime;
		// 结束开单日期： 运单结束日期.
		params['objectVo.condition.endBilltime'] = endTime;
		// 派送开始时间
		params['objectVo.condition.beginDeliverTime'] = null;
		// 派送结束时间
		params['objectVo.condition.endDeliverTime'] = null;
	}
	if(Ext.getCmp(querying.salesstatement.deptType.shbm).getValue()){
		// 收获部门queryForm.findField('deptType').getValue()
		params['objectVo.condition.receiveOrgCode'] = queryForm.findField('deptCode').getValue();
		// 制单部门
		params['objectVo.condition.createOrgCode'] = null;
	}else if(Ext.getCmp(querying.salesstatement.deptType.zdbm).getValue()){
		// 制单部门
		params['objectVo.condition.createOrgCode'] = queryForm.findField('deptCode').getValue();
		// 收获部门queryForm.findField('deptType').getValue()
		params['objectVo.condition.receiveOrgCode'] = null;
	}
	if(querying.salesstatement.gridType.yybb === tab.getActiveTab().title){
		//营业报表[清单类型]
		params['objectVo.condition.billType'] = querying.salesstatement.gridType.yb;
	}else if(querying.salesstatement.gridType.czqd === tab.getActiveTab().title){
		//操作清单[清单类型]
		params['objectVo.condition.billType'] = querying.salesstatement.gridType.cq;
	}
	if(!Ext.isEmpty(packageParamType) && querying.salesstatement.packageParamType === packageParamType){
		Ext.Object.each(params, function(key, value) {
			key = key.replace('objectVo.condition.','');
			if(!Ext.isEmpty(value)&&Ext.Array.contains(['startBillTime','endBilltime','beginDeliverTime','endDeliverTime'],key)){
				value = Ext.Date.parse(value, "Y-m-d H:i:s", true);
			}
		    condition[key] = value;
		});
		objectVo.condition = condition;
		return {'objectVo':objectVo};
	}
	// 判断 收货部门和制单部门和派送部门不能同时为空
	if(Ext.isEmpty(params['objectVo.condition.receiveOrgCode']) &&
			Ext.isEmpty(params['objectVo.condition.createOrgCode']) &&
			Ext.isEmpty(params['objectVo.condition.deliverDept'])){
			Ext.Msg.alert(querying.salesstatement.i18n('foss.querying.fossAlert'),querying.salesstatement.i18n('foss.querying.createOrgNameORdeliverDeptIsNll'));  //FOSS提醒      收货部门\制单部门和派送部门不能同时为空！
			return;
		}
	
	return params;
};
//查询
querying.salesstatement.searchByCondition = function(){
	var mainPanel  = Ext.getCmp('T_querying-salesStatementIndex_content'),
	tab  = mainPanel.down('tabpanel')
	,queryForm = mainPanel.getQueryForm().getForm();//得到查询的FORM表单
	//合并 参数
	/**
	 * 查询FORM必须通过校验
	 * 2013-03-01
	 * 张斌
	 * **/
	if(queryForm.isValid()){
		//每一次进行新的查询之前，都要先置空 废除单号变量
		querying.salesstatement.deleteWaybillNos =new Array();
		//获取查询参数
		var storeParams = querying.salesstatement.searchConditionParams(mainPanel,tab,queryForm)
		,ajaxParams = querying.salesstatement.searchConditionParams(mainPanel,tab,queryForm,querying.salesstatement.packageParamType)
		,grid =tab.getActiveTab().down('grid'), store = grid.store,form = tab.getActiveTab().down('form');
		if(Ext.isEmpty(storeParams)){
		    return;
		}
		/**
		 * 现不做累计数据查询操作
		 */
		//		if(store.getCount()!=0){
		//			querying.showQuestionMes(querying.salesstatement.i18n('foss.querying.isVoidLastData'),function(e){
		//				//若选择为是的话，查詢
		//				if(e=='yes'){
		//					Ext.apply(store.proxy.extraParams,storeParams);
		//					store.load();
		//					//当数据加载完之后 计算数据汇总
		//					store.on('load',function(s,records){   
		//					    var girdcount=0,totalEntity = Ext.create('Foss.querying.salesstatement.TotalModel').data;
		//						s.each(function(record){
		//					    	if(querying.salesstatement.isInvalid.n === record.get('isInvalid')){
		//					       	   //给单元格涂色
		//					       	   	var cells = grid.getView().getNodes()[girdcount].children;
		//								for(var i= 0;i<cells.length;i++){
		//									cells[i].style.backgroundColor='#ff0000';
		//								}
		//					        }
		//					    	girdcount++;
		//					    });
		//					});
		//					querying.requestAjaxJson(querying.realPath('querySalesStatementByComplexCondation.action'),ajaxParams,function(result){
		//					    form.loadRecord(Ext.create('Foss.querying.salesstatement.TotalModel',result.objectVo.totalFeeDto));
		//					},function(result){
		//						Ext.Msg.alert(result.message);
		//					});
		//				}else{
		//					//否則点击否的时候 ，直接退出，
		//					return;
		//				}
		//			});
		//	}else{
		
		
			Ext.apply(store.proxy.extraParams,storeParams);
			store.load();
			
			//当数据加载完之后 计算数据汇总
			store.on('load',function(s,records){   
			    var girdcount=0,totalEntity = Ext.create('Foss.querying.salesstatement.TotalModel').data;
				s.each(function(record){
			    	if(querying.salesstatement.isInvalid.n === record.get('isInvalid')){
			       	   //给单元格涂色
			       	   	var cells = grid.getView().getNodes()[girdcount].children;
						for(var i= 0;i<cells.length;i++){
							cells[i].style.backgroundColor='#ff0000';
						}
			        }
			    	girdcount++;
			    });
			});
			querying.requestAjaxJson(querying.realPath('querySalesStatementByComplexCondation.action'),ajaxParams,function(result){
			    form.loadRecord(Ext.create('Foss.querying.salesstatement.TotalModel',result.objectVo.totalFeeDto));
			},function(result){
				Ext.Msg.alert(result.message);
			});
//		}
			
	}else{
    	Ext.Msg.alert(querying.salesstatement.i18n('foss.querying.FOSSRemindYou'),querying.salesstatement.i18n('foss.querying.makeSureDataFillCorrect'));  //FOSS提醒您     请确定数据都填写正确！
    	return;
	}


};
//通过查询条件导出数据
querying.salesstatement.exportStatement = function(){
	var mainPanel  = Ext.getCmp('T_querying-salesStatementIndex_content'),
	tab  = mainPanel.down('tabpanel')
	,queryForm = mainPanel.getQueryForm().getForm();//得到查询的FORM表单
	//合并 参数
	var exportParams = querying.salesstatement.searchConditionParams(mainPanel,tab,queryForm)
		,grid =tab.getActiveTab().down('grid'), store = grid.store,form = tab.getActiveTab().down('form')
		,ids = [];
	// 【缺失后台代码】获得 当前选择 行的 id 集合，便于 后端导出数据时 不导出 剔除的数据，只导出 id集合中存在的数据
	
	/*如若导出所选 数据则用此方法
	 * var selectionArray = grid.getSelectionModel().getSelection();
	 * for(var i in selectionArray){
		ids.push(selectionArray[i].get('id'));
	}*/
	//导出 grid中可见的所有数据无论是否选择
	grid.store.each(function(record){
		ids.push(record.get('waybillNo'));
	});
	exportParams['objectVo.codeArr'] = ids;
	
	if(Ext.isEmpty(exportParams)){
	    return;
	}
    if (queryForm != null) {
		Ext.MessageBox.buttonText.yes = querying.salesstatement.i18n('foss.querying.ensur');    //确定
		Ext.MessageBox.buttonText.no = querying.salesstatement.i18n('foss.querying.cancel');   //取消
		if(!Ext.fly('downloadStatementForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadStatementForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		
		Ext.Msg.confirm( querying.salesstatement.i18n('foss.querying.message'), querying.salesstatement.i18n('foss.querying.ensurExportList'), function(btn,text){ //提示信息    确定要导出查询结果吗?
			if(btn == 'yes'){
				var params = exportParams;
		
				Ext.Ajax.request({
					url:querying.realPath('exportStatementByComplexCondation.action'),
					form: Ext.fly('downloadStatementForm'),
					params:params,
					method:'post',
					isUpload: true,
					success:function(response){
						var result = Ext.decode(response.responseText);
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(querying.salesstatement.i18n('foss.querying.exportFail'),result.message);  //导出失败
					}
				});
			}
		});
	}
};
/**
 * 查询三级产品
 * 2013-03-01
 * 张斌
 * **/
querying.searchThreeLeveProduct = function(){
	Ext.Ajax.request({
		url:querying.realPath('../pricing/findThreeLevelProduct.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			querying.salesstatement.threeLevelProduct = result.pricingValuationVo.productEntityList;//查询三级产品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				querying.showErrorMes(querying.salesstatement.i18n('foss.querying.requestTimeout'));//请求超时！
			}else{
				querying.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				querying.showErrorMes(querying.salesstatement.i18n('foss.querying.requestTimeout'));//请求超时！
			}else{
				querying.showErrorMes(result.message);
			}
		}
	});
};
//------------------------------------MODEL----------------------------------
//合计 Model
Ext.define('Foss.querying.salesstatement.TotalModel', {
extend: 'Ext.data.Model',
fields : [{
	//'票数',
	    name: 'pageCount',type:'int'
	}, {
	//'件数',
	    name: 'goodsQtyTotal',type:'int'
	},{
	//'重量',
	    name: 'goodsWeightTotal',type:'number'
	}, {
	//'体积',
	    name: 'goodsVolumeTotal',type:'number'
	},{
	//'预付总额',
	    name: 'prePayAmountTotal',type:'number'
	},{
	//'到付总额',
	    name: 'toPayAmountTotal',type:'number'
	}, {
	//'代收贷款',
	    name: 'codAmountTotal',type:'number'
	},{
	//'包装费',
	    name: 'packageFeeTotal',type:'number'
	},{
	//保价费
		name: 'insuranceFee',type:'number'
	},{
	//'收入总额',
	    name: 'amountTotal',type:'number'
	}]
});
//报表清单查询Model
Ext.define('Foss.querying.salesstatement.ConditionModel', {
extend: 'Ext.data.Model',
fields : [//收货类型
        {name:'receiveType',type:'string'},
        //1、收货部门： 运单开单部门编码. 
        {name:'receiveOrgCode',type:'string'},
        //2、运输性质： 产品条目编码. 
        {name:'productCode',type:'string'},

        //目的站： 到达网点/简称. 
        {name:'targetOrgCode',type:'string'},

        // 起始单号 
        {name:'startWaybillNo',type:'string'},
        // 结束单号： 运单号 
        {name:'endWaybillNo',type:'string'},
        // 起始开单日期： 运单开单日期. 
        {name:'startBillTime',type:'date',convert: dateConvert,defaultValue:null},

        // 结束开单日期： 运单结束日期. 
        {name:'endBilltime',type:'date',convert: dateConvert,defaultValue:null},

        //  发货人： 发货人姓名. （托运人）--传的是ID，前端使用共工组件）：
        {name:'deliveryCustomerContact',type:'string'},

        // 收货人： 收货人姓名. 
        {name:'receiveCustomerContact',type:'string'},

        // 发货联系人手机. 
        {name:'deliveryCustomerMobilephone',type:'string'},

        // 收货客户手机. 
        {name:'receiveCustomerMobilephone',type:'string'},

        // 发货人电话. 
        {name:'deliveryCustomerPhone',type:'string'},
        	
        // 收货人电话.
        	 
        {name:'receiverCustomerPhone',type:'string'},
        
        
        // 运单号
        
        {name:'waybillNo',type:'string'},
        
        // 发货人客户分群
        
        {name:'flabelleavemonth',type:'string'},
        
        // 派送开始时间
        
        {name:'beginDeliverTime',type:'date',convert: dateConvert,defaultValue:null},
        // 派送结束时间
        {name:'endDeliverTime',type:'date',convert: dateConvert,defaultValue:null},
        // 付款方式
        {name:'payMethod',type:'string'},
        //  开单部门
        {name:'createOrgCode',type:'string'},
        // 始发配载部门
        {name:'loadOrgCode',type:'string'},
        {//废除单号的列表
         name:'deleteWaybillNos',
         type:'string'
        },{//排序字段
        	name:'sortField',
            type:'string'
        },{//排序类型
        	name:'sortType',
            type:'string'
        }]
});
//报表清单查询 结果 Model
Ext.define('Foss.querying.salesstatement.ResultDtoModel', {
extend: 'Ext.data.Model',
fields : [//PDA制单时间

	{name:'billTime',type:'date',convert: dateConvert,defaultValue:null},
	
	
	//运单号
	
	{name:'waybillNo',type:'string'},
	
	
	//目的站
	
	{name:'targetOrgName',type:'string'},
	
	
	// 发货人客户分群
    
    {name:'flabelleavemonth',type:'string'},
	
	//货物总件数
	
	{name:'goodsQtyTotal',type:'int'},
	
	
	//货物总重量
	
	{name:'goodsWeightTotal',type:'number'},
	
	
	//预付金额
	
	{name:'prePayAmount',type:'number'},
	
	
	//货物尺寸
	
	{name:'goodsSize',type:'string'},
	
	
	//发货客户联系人
	
	{name:'deliveryCustomerContact',type:'string'},
	
	//发货人手机
	{name:'deliveryCustomerMobilephone',type:'string'},
	//发货人电话
	{name:'deliveryCustomerPhone',type:'string'},
	
	//计费重量
	
	{name:'billWeight',type:'number'},
	
	
	//货物总体积
	
	{name:'goodsVolumeTotal',type:'number'},
	
	
	//包装手续费
	
	{name:'packageFee',type:'number'},
	
	//到付金额
	
	{name:'toPayAmount',type:'number'},
	
	//代收货款
	
	{name:'codAmount',type:'number'},
	
	//代收货款手续费
	
	{name:'codFee',type:'number'},
	
	//退款金额（暂留）
	
	{name:'refundAmount',type:'number'},
	
	//开单送货费
	
	{name:'deliveryGoodsFee',type:'number'},
	
	//到付送货费
	
	{name:'arriveDeliGoodsFee',type:'number'},
	
	//开单费
	
	{name:'totalFee',type:'number'},
	
	//装卸费
	
	{name:'service_fee',type:'string'},
	
	//收货部门(出发部门)
	
	{name:'receiveOrgName',type:'string'},
	
	//对账部门
	
	{name:'checkAccountOrgName',type:'string'},
	
	//制单部门	
	
	{name:'createOrgName',type:'string'},
	
	//货物名称
	
	{name:'goodsName',type:'string'},
	
	//货物包装
	
	{name:'goodsPackage',type:'string'},
	
	//收货客户名称
	
	{name:'receiveCustomerName',type:'string'},
	
	//收货人手机
	{name:'receiveCustomerMobilephone',type:'string'},
	//收货人电话
	{name:'receiverCustomerTel',type:'string'},
	
	//收货具体地址
	
	{name:'receiveCustomerAddress',type:'string'},
	
	//签收人
	
	{name:'signer',type:'string'},
	
	//保险费
	
	{name:'insuranceFee',type:'number'},
	
	//保价声明价值
	
	{name:'insuranceAmount',type:'number'},
	
	//开单付款方式
	
	{name:'paidMethod',type:'string'},
	
	//运输类型
	
	{name:'transportType',type:'string'},
	
	//开单人
	
	{name:'createUserName',type:'string'},
	
	//运费计费费率（运价）
	
	{name:'unitPrice',type:'number'},
	
	//提货方式
	
	{name:'receiveMethod',type:'string'},
	
	//储运事项
	
	{name:'transportationRemark',type:'string'},
	
	//公布价运费（运费）
	
	{name:'transportFee',type:'number'},
	//是否作废
	{name:'isInvalid',type:'string'},
	//FOSS制单时间
	{name:'createTime',type:'date',convert: dateConvert,defaultValue:null},
	//是否PDA开单
	{name:'isPdaCreate',
		convert:function(value){
			 if(value!=null && value === 'Y'){
			 return '是';
			 }else {
			 return '否';
			 }
			 }
		}]
});
//------------------------------------STORE----------------------------------
//报表清单STORE
Ext.define('Foss.querying.salesstatement.ResultDtoStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.querying.salesstatement.ResultDtoModel',
	pageSize:5000,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		timeout:300000,
		url : querying.realPath('querySalesStatementByComplexCondation.action'),
		reader : {
			type : 'json',
			root : 'objectVo.dtoList',
			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//合计
Ext.define('Foss.querying.salesstatement.TotalForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.totalform',
	items:[{
		xtype:'fieldset',
		title: '合计',   //合计
		defaultType: 'numberfield',
		defaults: {readOnly:true},
		layout: {
		    type: 'table',
		    columns: 4
		},
		items :[{
		    fieldLabel: querying.salesstatement.i18n('foss.querying.votesNum'),  //票数
		    name: 'pageCount'
		}, {
		    fieldLabel: querying.salesstatement.i18n('foss.querying.number'),  //件数
		    name: 'goodsQtyTotal'
		},{
		    fieldLabel: querying.salesstatement.i18n('foss.querying.weight'),  //重量
		    name: 'goodsWeightTotal'
		}, {
		    fieldLabel: querying.salesstatement.i18n('foss.querying.volume'),  //体积
		    name: 'goodsVolumeTotal'
		},{
		    fieldLabel: querying.salesstatement.i18n('foss.querying.prePaidTotal'),  //预付总金额
		    name: 'prePayAmountTotal'
		},{
		    fieldLabel: querying.salesstatement.i18n('foss.querying.reachPayTotal'),  //到付总金额
		    name: 'toPayAmountTotal'
		}, {
		    fieldLabel: querying.salesstatement.i18n('foss.querying.collectionLoans'),  //代收贷款
		    name: 'codAmountTotal'
		},{
		    fieldLabel: querying.salesstatement.i18n('foss.querying.packingCharges'),  //包装费
		    name: 'packageFeeTotal'
		}, {
			fieldLabel: querying.salesstatement.i18n('foss.querying.insuranceFee'),  //保价费
		    name: 'insuranceFee'
		},{
		    fieldLabel: querying.salesstatement.i18n('foss.querying.totalRevenue'),  //收入总额
		    name: 'amountTotal'
		}]
	}]
});
//报表清单 查询条件
Ext.define('Foss.querying.salesstatement.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: querying.salesstatement.i18n('foss.querying.searchCondiction'),  //查询条件
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
		columnWidth : .25
    },
	defaultType : 'textfield',
    layout: 'column',
    record:null,												//绑定的model Foss.querying.salesstatement.SMSTemplateEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [FossDataDictionary.getDataDictionaryCombo('RECEIVING_TYPE',{
			fieldLabel:querying.salesstatement.i18n('foss.querying.receiptType'),	//收货类型
			name: 'receiveType',
			forceSelection:true,
			value:querying.salesstatement.receivingDefault,
			listeners:{
        		select:function(field,rs){
        			var deliverDept  = me.getForm().findField('deliverDept');
        			var deptCode  = me.getForm().findField('deptCode');
    				//当选择收货出港时，派送部门不可选择；
        			//me.getForm().findField('createOrgCode')
        			if(!Ext.isEmpty(rs)  && querying.salesstatement.receivingType.rc === rs[0].get('valueCode')){
        				deptCode.setReadOnly(false);
        				/**
        				 * [收货部门],[制单部门]必填
        				 * 2013-03-01
        				 * 张斌
        				 * **/
        				deptCode.allowBlank = false;
        				deptCode.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());//派送为当前部门
            			deliverDept.setReadOnly(true);
            			/**
        				 * 派送部门不必填
        				 * 2013-03-01
        				 * 张斌
        				 * **/
            			deliverDept.allowBlank = true;
        				deliverDept.setValue(null);
        			}//当选择进港派送时，收货部门/制单部门不可选择
        			else if(!Ext.isEmpty(rs)  && querying.salesstatement.receivingType.igs === rs[0].get('valueCode')){
        				deliverDept.setReadOnly(false);
        				/**
        				 * 派送部门必填
        				 * 2013-03-01
        				 * 张斌
        				 * **/
        				deliverDept.allowBlank = false;
        				deliverDept.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());//派送为当前部门
        				deptCode.setReadOnly(true);
        				deptCode.setValue(null);
        				/**
        				 * [收货部门],[制单部门]不必填
        				 * 2013-03-01
        				 * 张斌
        				 * **/
        				deptCode.allowBlank = true;
        			}
	        	}
        	}
		}),{
			xtype: 'fieldcontainer',
			columnWidth : .5,
	        hideLabel: true,
	        layout: 'column',
	        items: [{ xtype:'radiofield',columnWidth : .25,boxLabel: querying.salesstatement.i18n('foss.querying.deliveryLibraryTime'), name: 'timeType',  //派送出库时间
	        	id:querying.salesstatement.timeType.pck,
	        	checked:true,
	        	listeners:{
	        		change:function(field,newV,oldV ){
	        			
	        		}
	        	}
			},{ xtype:'radiofield',columnWidth : .25,boxLabel: querying.salesstatement.i18n('foss.querying.receiptsDate'), name: 'timeType',  //收货日期
	        	id:querying.salesstatement.timeType.sh,
				listeners:{
	        		change:function(field,newV,oldV ){
	        			
	        		}
	        	}
			},{
				xtype : 'rangeDateField',
				fieldId : 'Deppon_integrativeQueryIndex_StartEndDate-date98',
				dateType : 'datetimefield_date97',
				columnWidth : .5,
				hideLabel :true,
				//dateRange : 30,
				fromName : 'startTime',
				toName : 'endTime',
				//dateFat : 'yyyy-MM-dd HH:mm:ss',
				fromValue : Ext.Date.format(querying.salesstatement.getStartTime(new Date(),0),'Y-m-d H:i:s'),
				toValue : Ext.Date.format(querying.salesstatement.getEndTime(new Date(),0),'Y-m-d H:i:s')
			}]
		},{
//			xtype:'commonemployeeselector',
			fieldLabel:querying.salesstatement.i18n('foss.querying.consignor'),	//发货人					//子系统功能模块
			name:'deliveryCustomerContact'
		},{
			xtype:'combobox',//多选
			name:'productCode',
			fieldLabel:querying.salesstatement.i18n('foss.querying.transportPropertiesOf'),//	运输性质
			store:Ext.create('Foss.pkp.ProductStore'),//引用PKP的
			multiSelect:true,
			queryModel:'local',
			displayField:'name',
			valueField:'code'
		},
//		{
//			fieldLabel:querying.salesstatement.i18n('foss.querying.transportPropertiesOf'),//	运输性质					//模块名称
//			xtype:'commonproductselector',//单选
//			name:'productCode',
//			levelses : '3'
//		},
		{
			xtype: 'fieldcontainer',
			columnWidth : .5,
	        hideLabel: true,
	        layout: 'column',
	        items: [{ xtype:'radiofield',columnWidth : .33,boxLabel: querying.salesstatement.i18n('foss.querying.receivingDepartment'), name: 'deptType',//收货部门
	        	id:querying.salesstatement.deptType.shbm,
	        	checked:true,
	        	listeners:{
	        		change:function(field,newV,oldV ){
	        			
	        		}
	        	}
			},{ xtype:'radiofield',columnWidth : .33,boxLabel: querying.salesstatement.i18n('foss.querying.singleDepartmentOn'), name: 'deptType',//制单部门
				id:querying.salesstatement.deptType.zdbm,
				listeners:{
	        		change:function(field,newV,oldV ){
	        			
	        		}
	        	}
			},{
				xtype : 'commonsaledeptandouterbranchselector',//[收货部门],[制单部门]
				columnWidth : .34,
				type:'ORG',
				leave:'Y',//可出发的
				saleDeptCode:FossUserContext.getCurrentDeptCode(), //设置当前部门的code
				hideLable:true,
				name:'deptCode'
			}]
		},FossDataDictionary.getDataDictionaryCombo('SETTLEMENT__PAYMENT_TYPE',{
			fieldLabel:querying.salesstatement.i18n('foss.querying.termsPayment'),	//付款方式							
			name: 'payMethod'
		}),{
			xtype : 'commonsaledeptandouterbranchselector',
			name:'deliverDept',
			type:'ORG',
			saleDeptCode:FossUserContext.getCurrentDeptCode(),
			arrive:'Y', //可到达
			fieldLabel : querying.salesstatement.i18n('foss.querying.deliverySector')//派送部门
		},{
			xtype : 'dynamicorgcombselector',
			name:'loadOrgCode',
			type:'ORG',
			transferCenter : 'Y',
			fieldLabel : querying.salesstatement.i18n('foss.querying.stowageDepartment')//配载部门
		},{
//			xtype:'commonemployeeselector',
			fieldLabel:querying.salesstatement.i18n('foss.querying.consignee'),	//收货人
			name:'receiveCustomerContact'
		},{
//			xtype:'commondestfreightroutelineselector',
			xtype:'dynamicorgcombselector',
			types:'ORG',
			fieldLabel:querying.salesstatement.i18n('foss.querying.destinationStation'),	//目的站
			salesDepartment : 'Y',
			name:'targetOrgCode'
		},{
			fieldLabel:querying.salesstatement.i18n('foss.querying.waybillNumber'),	//运单号
			name:'waybillNo',
			regex :querying.salesstatement.regLimit.k,
			listeners:{
				change:function(field,new_v,old_v){
					if(!Ext.isEmpty(new_v)&& new_v.length >11){
						field.setValue(new_v.substring(0,11));
					}
				}
			}
		},/*FossDataDictionary.getDataDictionaryCombo('CRM_CUSTOMER_GROUP',{
			fieldLabel:querying.salesstatement.i18n('foss.querying.consignorcustomersegmentation'),	//发货人客户分群							
			name: 'flabelleavemonth',
			listeners:{
				beforequery:function(){
				var store = FossDataDictionary.getDataDictionaryStore('CRM_CUSTOMER_GROUP');
				if(Ext.isEmpty(store)){
					return value;
				}else{
					
					store.each(function(record){
						if(record.get('valueCode')=='VIP'){
							store.remove(record.get('valueCode'));
						}
						
					});
					
				}
			
				}
			}
		},{'valueCode':'','valueName':'全部'}
		)*/
		{
			xtype:'combo',
			fieldLabel:querying.salesstatement.i18n('foss.querying.consignorcustomersegmentation'),	//发货人客户分群
			store:querying_salesstatement_custLeaveMonthStore,
			name: 'flabelleavemonth',
			displayField: 'valueName',
		    valueField: 'valueCode'
			
		}
		/***隐藏域***/
		,{
			fieldLabel:querying.salesstatement.i18n('foss.querying.listTypeDel'),	//清单类型del
			hidden:true,
			name:'billType'
		}];
	},
	buttons : [{
		text : querying.salesstatement.i18n('foss.querying.reset'),//重置
		margin : '0 730 0 0',
		hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementQueryButton'),
		handler : function() {
			var queryForm = this.up('form');
			queryForm.getForm().reset();
			/**
			 * 重置之后设置数据
			 * 2013-03-01
			 * 张斌
			 * **/
			querying.salesstatement.initSearchForm(queryForm);
			var startTime = Ext.Date.format(querying.salesstatement.getStartTime(new Date(),0),'Y-m-d H:i:s');
			var endTime = Ext.Date.format(querying.salesstatement.getEndTime(new Date(),0),'Y-m-d H:i:s');
			queryForm.getForm().findField('startTime').setValue(startTime);
			queryForm.getForm().findField('endTime').setValue(endTime);
			
		}
	},{
		text : querying.salesstatement.i18n('foss.querying.exportReport'),//导出报表
		cls:'yellow_button',
		hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementExportButton'),
		handler : function() {
			querying.salesstatement.exportStatement();
		},
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			  //设定间隔秒数,如果不设置，默认为2秒
			  seconds: 3
			})
	},{
		text : querying.salesstatement.i18n('foss.querying.query'),//查询
		cls:'yellow_button',
		hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementQueryButton'),
		handler : function(btn) {
			querying.salesstatement.searchByCondition();
		},
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			  //设定间隔秒数,如果不设置，默认为2秒
			  seconds: 3
			})
	}]
});
//------------------------------------GRID----------------------------------
//营业报表 查询结果grid
Ext.define('Foss.querying.salesstatement.SalesstatementQueryGrid', {
	extend: 'Ext.grid.Panel',
	//sortableColumns:false,排序
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	height:700,
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: querying.salesstatement.i18n('foss.querying.searchListNull'),	//查询结果为空
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	frame: true,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = Ext.create('Foss.querying.salesstatement.ResultDtoStore');
		me.listeners = me.getMyListeners(config);
	    //添加多选框
		//me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar(config);
		me.callParent([cfg]);
	},
	getTbar:function(config){
		var me = this;
		return[{
			text : querying.salesstatement.i18n('foss.querying.printAReport'),	//打印报表
			hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementPrintButton'),
			handler :function(){
				var store =me.store;
				if(store.getCount()==0){
					querying.showWoringMessage(querying.salesstatement.i18n('foss.querying.NoDataToPrint'));//没有数据可以导出
					return;
				}else{
					if(store.getSorters().length >0){ //若用户有根据某个字段排序，
						var sort =store.getSorters();
						querying.salesstatement.sortField =sort[0].property;//获取排序常量
						querying.salesstatement.direction =sort[0].direction;//获取是否升降序排序
						
					}
					querying.salesstatement.printSalesStartement();
				}
				
			} 
		},'-', {
			text : querying.salesstatement.i18n('foss.querying.removeData'),	//移除数据
			hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementVoidButton'),
			handler :function(){
				querying.salesstatement.deleteEntity(me);
			} 
		},'-', {
			text : querying.salesstatement.i18n('foss.querying.emptyData'),	//清空数据
			hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementClearButton'),
			handler :function(){
				me.store.removeAll();
				me.up('container').down('form').getForm().reset();
			} 
		},'-', {
			xtype:'label',
			html: querying.salesstatement.i18n('foss.querying.cancelRedStart') //<span style = "color:red;">红色代表已作废</span>
		}];
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    };
	},
	getColumns:function(config){
		var me = this;
		return [{
			text : querying.salesstatement.i18n('foss.querying.receiptDate'),	//PDA制单时间
			dataIndex : 'billTime',
			width : 132,
			renderer:function(value) {
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.singleSystemTime'),	//FOSS制单时间
			dataIndex : 'createTime',
			width : 132,
			renderer:function(value) {
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.whetherPDABilling'),	//是否PDA开单
			dataIndex : 'isPdaCreate'
		},{
			text : querying.salesstatement.i18n('foss.querying.billNumber'),	//单号
			dataIndex : 'waybillNo'
		},{
			text : querying.salesstatement.i18n('foss.querying.destinationStation'),	//目的站
			dataIndex : 'targetOrgName'
		},{
			text : querying.salesstatement.i18n('foss.querying.consignorcustomersegmentation'),	//发货人客户分群
			width : 132,
			dataIndex : 'flabelleavemonth',
			renderer : function(value){
				/**
				 * 发货人客户分群显示名称
				 * 
				 * 
				 * **/
				var store = FossDataDictionary.getDataDictionaryStore(querying.salesstatement.custleavemonth);
				if(Ext.isEmpty(store)){
					return value;
				}else{
					var name = '';
					store.each(function(record){
						if(record.get('valueCode')==value){
							name = record.get('valueName');
						}
					});
					return name;
				}
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.number'),	//件数
			dataIndex : 'goodsQtyTotal'
		},{
			text : querying.salesstatement.i18n('foss.querying.weight'),	//重量
			dataIndex : 'goodsWeightTotal'
		},{
			text : querying.salesstatement.i18n('foss.querying.prepaidAmount'),//预付金额
			dataIndex : 'prePayAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.size'),	//尺寸
			dataIndex : 'goodsSize'
		},{
			text : querying.salesstatement.i18n('foss.querying.consignor'),	//发货人
			dataIndex : 'deliveryCustomerContact'
		},{
			text : querying.salesstatement.i18n('foss.querying.consignorShipperMobile'),	//发货人手机
			dataIndex : 'deliveryCustomerMobilephone'
		},{
			text : querying.salesstatement.i18n('foss.querying.consignorShipperTel'),	//发货人电话
			dataIndex : 'deliveryCustomerPhone'
		},{
			text : querying.salesstatement.i18n('foss.querying.chargeableWeight'),	//计费重量
			dataIndex : 'billWeight'
		},{
			text : querying.salesstatement.i18n('foss.querying.volume'),	//体积
			dataIndex : 'goodsVolumeTotal'
		},{
			text : querying.salesstatement.i18n('foss.querying.packingCharges'),	//包装费
			dataIndex : 'packageFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.freight'),	//运费
			dataIndex : 'transportFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.ayTheAmount'),	//到付金额
			dataIndex : 'toPayAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.paymentCollection'),	//代收货款
			dataIndex : 'codAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.paymentCollectionFee'),	//代收货款手续费
			dataIndex : 'codFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.refundAccount'),	//退款金额
			dataIndex : 'refundAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.billDeliveryFee'),	 //开单送货费
			dataIndex : 'deliveryGoodsFee',hidden:true
		},{
			text : querying.salesstatement.i18n('foss.querying.toPayDeliveryCharge'),		//到付送货费
			dataIndex : 'arriveDeliGoodsFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.billedAmount'),	//开单金额
			dataIndex : 'totalFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.laborCosts'),	//装卸费
			dataIndex : 'service_fee'
		},{
			text : querying.salesstatement.i18n('foss.querying.receivingDepartment'),	//收货部门
			dataIndex : 'receiveOrgName'
		},{
			text : querying.salesstatement.i18n('foss.querying.reconciliationSector'),	//对账部门
			dataIndex : 'checkAccountOrgName'
		},{
			text : querying.salesstatement.i18n('foss.querying.singleDepartmentOn'),	//制单部门
			dataIndex : 'createOrgName'
		},{
			text : querying.salesstatement.i18n('foss.querying.cargoName'),	//货物名称
			dataIndex : 'goodsName'
		},{
			text : querying.salesstatement.i18n('foss.querying.package'),	//包装
			dataIndex : 'goodsPackage'
		},{
			text : querying.salesstatement.i18n('foss.querying.consignee'),	 //收货人
			dataIndex : 'receiveCustomerName'
		},{
			text : querying.salesstatement.i18n('foss.querying.consigneeMobile'),//收货人手机
			dataIndex : 'receiveCustomerMobilephone'
		},{
			text : querying.salesstatement.i18n('foss.querying.consigneePhone'),//收货人电话
			//dataIndex : 'receiveCustomerPhone'
			dataIndex : 'receiverCustomerTel'
		},{
			text : querying.salesstatement.i18n('foss.querying.consigneeAddress'),		//收货人地址
			dataIndex : 'receiveCustomerAddress'
		},{
			text : querying.salesstatement.i18n('foss.querying.signedPerson'),	//签收人
			dataIndex : 'signer'
		},{
			text : querying.salesstatement.i18n('foss.querying.premium'),	//保险费
			dataIndex : 'insuranceFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.insuredValue'),	//保险价值
			dataIndex : 'insuranceAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.termsPayment'),	//付款方式
			dataIndex : 'paidMethod',
			renderer : function(value){
				/**
				 * 付款方式显示名称
				 * 2013-03-01
				 * 张斌
				 * **/
				var store = FossDataDictionary.getDataDictionaryStore(querying.salesstatement.paidMethod);
				if(Ext.isEmpty(store)){
					return value;
				}else{
					var name = '';
					store.each(function(record){
						if(record.get('valueCode')==value){
							name = record.get('valueName');
						}
					});
					return name;
				}
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.transportPropertiesOf'),	//运输性质
			dataIndex : 'transportType',
			renderer:function(value){
				/**
				 * 运输性质显示名称
				 * 2013-03-01
				 * 张斌
				 * **/
				var product = querying.salesstatement.threeLevelProduct;
				if(Ext.isEmpty(product)){
					return value;
				}else{
					for(var i = 0;i<product.length;i++){
						if(product[i].code == value){
							return product[i].name;
						}
					}
				}				
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.systemSingle'),		//制单人
			dataIndex : 'createUserName'
		},{
			text : querying.salesstatement.i18n('foss.querying.tariffs'),		//运价
			dataIndex : 'unitPrice'
		},{
			text : querying.salesstatement.i18n('foss.querying.collectMode'),		//提货方式
			dataIndex : 'receiveMethod',
			renderer : function(value){
				/**
				 * 提货方式显示名称
				 * 2013-03-01
				 * 张斌
				 * 汽运：PICKUPGOODSHIGHWAYS 【空运】：PICKUPGOODSAIR
				 * **/
				var v = FossDataDictionary. rendererSubmitToDisplay (value,querying.salesstatement.receiveMethod);
				return (Ext.isEmpty(v) || value == v)?FossDataDictionary. rendererSubmitToDisplay (value,'PICKUPGOODSAIR'):v;
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.storageTransportationMatters'),		//储运事项
			dataIndex : 'transportationRemark'
		},{
			text:querying.salesstatement.i18n('foss.querying.whetherCancel'),//是否作废
			dataIndex:'isInvalid',
			renderer:function(v){
				if(querying.salesstatement.isInvalid.y === v){
					return querying.salesstatement.i18n('foss.querying.no');  //否
				}else if(querying.salesstatement.isInvalid.n === v){
					return querying.salesstatement.i18n('foss.querying.yes');  //是
				}
				return v;
			}
		}];
	}
});
//操作清单 查询结果grid
Ext.define('Foss.querying.salesstatement.InventoryQueryGrid', {
	extend: 'Ext.grid.Panel',
    //sortableColumns:false,是否可排序
    enableColumnHide:false,
    enableColumnMove:false,
    height:700,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: querying.salesstatement.i18n('foss.querying.searchListNull'),	//查询结果为空
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	frame: true,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = Ext.create('Foss.querying.salesstatement.ResultDtoStore');
		me.listeners = me.getMyListeners(config);
	    //添加多选框
	//	me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar(config);
		me.callParent([cfg]);
	},
	getTbar:function(config){
		var me = this;
		return[{
			text : querying.salesstatement.i18n('foss.querying.printAReport'),	//打印报表
			hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementPrintButton'),
			handler :function(){
				var store =me.store;
				if(store.getCount()==0){
					querying.showWoringMessage(querying.salesstatement.i18n('foss.querying.NoDataToPrint'));//没有数据可以导出
					return;
				}else{
					if(store.getSorters().length >0){ //若用户有根据某个字段排序，
						var sort =store.getSorters();
						querying.salesstatement.sortField =sort[0].property;//获取排序常量
						querying.salesstatement.direction =sort[0].direction;//获取是否升降序排序
					}
				querying.salesstatement.printSalesStartement();
				}	
			} 
		},'-', {
			text : querying.salesstatement.i18n('foss.querying.removeData'),	//移除数据
			hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementVoidButton'),
			handler :function(){
				querying.salesstatement.deleteEntity(me);
			} 
		},'-', {
			text : querying.salesstatement.i18n('foss.querying.emptyData'),	//清空数据
			hidden:!querying.salesstatement.isPermission('salesStatementIndex/salesStatementClearButton'),
			handler :function(){
				me.store.removeAll();
				me.up('container').down('form').getForm().reset();
			} 
		},'-', {
			xtype:'label',
			html: querying.salesstatement.i18n('foss.querying.cancelRedStart')  //<span style = "color:red;">红色代表已作废</span>
		}];
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    };
	},
	getColumns:function(config){
		var me = this;
		return [{
			text : querying.salesstatement.i18n('foss.querying.receivingDepartment'),	//收货部门
			dataIndex : 'receiveOrgName'
		},{
			text : querying.salesstatement.i18n('foss.querying.singleDepartmentOn'),	//制单部门
			dataIndex : 'createOrgName'
		},{
			text : querying.salesstatement.i18n('foss.querying.receiptDate'),	//PDA制单时间
			dataIndex : 'billTime',
			width : 132,
			renderer:function(value) {
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.singleSystemTime'),	//FOSS制单时间
			dataIndex : 'createTime',
			width : 132,
			renderer:function(value) {
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.whetherPDABilling'),	//是否PDA开单
			dataIndex : 'isPdaCreate'
		},{
			text : querying.salesstatement.i18n('foss.querying.billNumber'),	//单号
			dataIndex : 'waybillNo'
		},{
			text : querying.salesstatement.i18n('foss.querying.destinationStation'),	//目的站
			dataIndex : 'targetOrgName'
		},{
			text : querying.salesstatement.i18n('foss.querying.cargoName'), //货物名称
			dataIndex : 'goodsName'
		},{
			text : querying.salesstatement.i18n('foss.querying.number'),	//件数
			dataIndex : 'goodsQtyTotal'
		},{
			text : querying.salesstatement.i18n('foss.querying.weight'),	//重量
			dataIndex : 'goodsWeightTotal'
		},{
			text : querying.salesstatement.i18n('foss.querying.chargeableWeight'),	//计费重量
			dataIndex : 'billWeight'
		},{
			text : querying.salesstatement.i18n('foss.querying.volume'),	//体积
			dataIndex : 'goodsVolumeTotal'
		},{
			text : querying.salesstatement.i18n('foss.querying.packingCharges'),		//包装费
			dataIndex : 'packageFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.prepaidAmount'),	//预付金额
			dataIndex : 'prePayAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.ayTheAmount'),	//到付金额
			dataIndex : 'toPayAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.paymentCollection'),	//代收货款
			dataIndex : 'codAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.refundAccount'),	//退款金额
			dataIndex : 'refundAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.billDeliveryFee'),	//开单送货费
			dataIndex : 'deliveryGoodsFee',hidden:true
		},{
			text : querying.salesstatement.i18n('foss.querying.toPayDeliveryCharge'),		//到付送货费
			dataIndex : 'arriveDeliGoodsFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.billedAmount'),		//开单金额
			dataIndex : 'totalFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.laborCosts'),		//装卸费
			dataIndex : 'service_fee'
		},{
			text : querying.salesstatement.i18n('foss.querying.consignor'),		//发货人
			dataIndex : 'deliveryCustomerContact'
		},{
			text : querying.salesstatement.i18n('foss.querying.consignee'),		//收货人
			dataIndex : 'receiveCustomerName'
		},{
			text : querying.salesstatement.i18n('foss.querying.consigneeAddress'),		//收货人地址
			dataIndex : 'receiveCustomerAddress'
		},{
			text : querying.salesstatement.i18n('foss.querying.consigneePhone'),		//收货人电话
			dataIndex : 'receiverCustomerTel'
		},{
			text : querying.salesstatement.i18n('foss.querying.signedPerson'),			//签收人
			dataIndex : 'signer'
		},{
			text : querying.salesstatement.i18n('foss.querying.systemSingleMember'),		//制单员
			dataIndex : 'createUserName'
		},{
			text : querying.salesstatement.i18n('foss.querying.premium'),		//保险费
			dataIndex : 'insuranceFee'
		},{
			text : querying.salesstatement.i18n('foss.querying.insuredValue'),		//保险价值
			dataIndex : 'insuranceAmount'
		},{
			text : querying.salesstatement.i18n('foss.querying.transportPropertiesOf'),		//运输性质
			dataIndex : 'transportType',
			renderer:function(value){
				/**
				 * 运输性质显示名称
				 * 2013-03-01
				 * 张斌
				 * **/
				var product = querying.salesstatement.threeLevelProduct;
				if(Ext.isEmpty(product)){
					return value;
				}else{
					for(var i = 0;i<product.length;i++){
						if(product[i].code == value){
							return product[i].name;
						}
					}
				}				
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.tariffs'),		//运价
			dataIndex : 'unitPrice'
		},{
			text : querying.salesstatement.i18n('foss.querying.collectMode'),	//提货方式
			dataIndex : 'receiveMethod',
			renderer : function(value){
				/**
				 * 提货方式显示名称
				 * 2013-03-01
				 * 张斌
				 * 汽运：PICKUPGOODSHIGHWAYS 【空运】：PICKUPGOODSAIR
				 * **/
				var v = FossDataDictionary. rendererSubmitToDisplay (value,querying.salesstatement.receiveMethod);
				return (Ext.isEmpty(v) || value == v)?FossDataDictionary. rendererSubmitToDisplay (value,'PICKUPGOODSAIR'):v;
			}
		},{
			text : querying.salesstatement.i18n('foss.querying.termsPayment'),	//付款方式
			dataIndex : 'paidMethod',
			renderer : function(value){
				/**
				 * 付款方式显示名称
				 * 2013-03-01
				 * 张斌
				 * **/
				var store = FossDataDictionary.getDataDictionaryStore(querying.salesstatement.paidMethod);
				if(Ext.isEmpty(store)){
					return value;
				}else{
					var name = '';
					store.each(function(record){
						if(record.get('valueCode')==value){
							name = record.get('valueName');
						}
					});
					return name;
				}
			}
		},{
			text:querying.salesstatement.i18n('foss.querying.whetherCancel'), //是否作废
			dataIndex:'isInvalid',
			renderer:function(v){
				if(querying.salesstatement.isInvalid.y === v){
					return querying.salesstatement.i18n('foss.querying.no'); //否
				}else if(querying.salesstatement.isInvalid.n === v){
					return querying.salesstatement.i18n('foss.querying.yes'); //是
				}
				return v;
			}
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
//	Ext.Ajax.request({
//		url:ContextPath.STL + '/pricing/queryStatement.action',
//		jsonData:{'productVo':{'productEntity':{'active':'Y','levelses':['2']}}},
//		async:false,
//		success:function(response){
//			var result = Ext.decode(response.responseText);
//			querying.salesstatement.product = result.productVo.productEntityList;
//		},
//		exception:function(response){
//			var result = Ext.decode(response.responseText);
//			top.Ext.Msg.alert('Foss提醒您','获取运输性质失败');
//		}
//	});
	/**
	 * 不再需要根据营业部信息去判断出货类型,注释掉此代码
	 * 2013-03-01
	 * 张斌
	 * **/
/*	Ext.Ajax.request({
		url:querying.realPath('querySaleDepartmentByCode.action'),
		jsonData:{'objectVo':{'codeStr':FossUserContext.getCurrentDeptCode()}},
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			querying.salesstatement.saleDepartment = result.objectVo.saleDepartment;
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			top.Ext.Msg.alert('Foss提醒您','获取当前登录人所在营业部失败');
		}
	});*/
	/**
	 * 查询三级产品
	 * 2013-03-01
	 * 张斌
	 * **/
	querying.searchThreeLeveProduct();
	Ext.QuickTips.init();
	if (Ext.getCmp('T_querying-salesStatementIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.querying.salesstatement.QueryConditionForm',{
		'record':Ext.create('Foss.querying.salesstatement.ConditionModel'),
		'height':250
	});//查询FORM
	var salesstatementGrid  = Ext.create('Foss.querying.salesstatement.SalesstatementQueryGrid',{
    		margin : '40 0 10 0'
    });//查询结果显示列表
	var inventoryGrid  = Ext.create('Foss.querying.salesstatement.InventoryQueryGrid',{
		margin : '40 0 10 0'
	});
	//初始化查询 条件
	querying.salesstatement.initSearchForm(queryForm);
	Ext.getCmp('T_querying-salesStatementIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_querying-salesStatementIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询报表结果GRID
		getQueryGrid : function() {
			return salesstatementGrid;
		},
		//获得查询清单结果GRID
		getInventoryGrid : function() {
			return inventoryGrid;
		},
		items : [ queryForm,{
			xtype:'tabpanel',
			bodyCls:'autoHeight',
			cls:'innerTabPanel',
//			height:160,
			items: [{
				title : querying.salesstatement.gridType.yybb,
				items:[salesstatementGrid,{
					xtype:'totalform'
			    }]
			},{
				title : querying.salesstatement.gridType.czqd,
				items:[inventoryGrid,{
					xtype:'totalform'
			    }]
			}]
		}] 
	}));
});
//------------------------------------WINDOW--------------------------------
