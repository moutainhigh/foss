/**
 * 查询借车界面
 */
//查询条件区域
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryForm',{
	extend:'Ext.form.Panel',
	title: scheduling.i18n('foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryForm.title'),    //查询条件
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaultType: 'textfield',
	layout:'column',
	defaults:{
		labelWidth: 85,
		margin: '5 10 5 10'
	},
	items:[{
		name: 'borrowNo',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowNo'),    //借车单号
		columnWidth: .25
	},{
		xtype:'rangeDateField',
		dateType:'datetimefield_date97',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowTime'),    //借车时间
		fieldId:'Foss_borrowvehicle_borrowVehicleApplyQueryForm_borrowTime_ID',
		fromName: 'borrowBeginTime',
		toName: 'borrowEndTime',
		columnWidth: .5
	},{
		name: 'useType',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.useType'),    //使用类型
		xtype:'combobox',
	    store:Ext.create('Ext.data.Store', {
		    fields: ['valueName', 'valueCode'],
		    data : [
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.all'), 'valueCode': ''},   //全部
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.regular'), 'valueCode': 'REGULAR'},   //班车
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.pickGoods'), 'valueCode': 'PICK_GOODS'}  //接送货
		    ]
		}),
	    queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    forceSelection:true,
	    triggerAction:'all',
	    columnWidth:.25
	},{
		name: 'orderVehicleModel',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.orderVehicleModel'),    //借车车型
		xtype:'commonvehicletypeselector',
	    columnWidth:.25
	},{
		xtype:'rangeDateField',
		dateType:'datetimefield_date97',
		fieldId:'Foss_borrowvehicle_borrowVehicleApplyQueryForm_AuditTime_ID',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.auditTime'),    //受理时间
		fromName: 'auditBeginTime',
		toName: 'auditEndTime',
		columnWidth: .5
	},{
		xtype:'dynamicorgcombselector',
		name: 'applyOrgCode',
		type:'ORG',
		transTeam:'Y',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyOrgName'),    //借车部门
		columnWidth: .25
	},{
		xtype : 'dynamicorgcombselector',
		fieldLabel : scheduling.i18n('foss.scheduling.borrowvehicle.label.auditOrgName'),    //受理部门
		name:'auditOrgCode',
		type:'ORG',
		transTeam:'Y',
		columnWidth: .25
	},{
		xtype:'rangeDateField',
		dateType:'datetimefield_date97',
		fieldId:'Foss_borrowvehicle_borrowVehicleApplyQueryForm_ApplyTime_ID',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyTime'),    //申请时间
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s'),
		fromName: 'applyBeginTime',
		toName: 'applyEndTime',
		columnWidth: .5
	},{
		xtype:'commonowntruckselector',
		name: 'vehicleNo',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.planVehicleNo'),    //安排车牌号
		columnWidth: .25
	},{
		name: 'borrowPurpose',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowPurpose'),    //借车用途
		xtype:'combobox',
	    store:Ext.create('Ext.data.Store', {
		    fields: ['valueName', 'valueCode'],
		    data : [
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.all'), 'valueCode': ''},  //全部
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.takeGoods'), 'valueCode': 'TAKE_GOODS'},  //接货
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.deliversGoods'), 'valueCode': 'DELIVERS_GOODS'},  //送货
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.transferringGoods'), 'valueCode': 'TRANSFERRING_GOODS'},  //转货
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.bus'), 'valueCode': 'BUS'},  //物流班车
		        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.other'), 'valueCode': 'OTHER'}  //其他
		    ]
		}),
	    queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    forceSelection:true,
	    triggerAction:'all',
	    columnWidth:.25
	},{
		name: 'status',
		fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.operatorStatus'),    //操作状态
		xtype:'combobox',
	    store:FossDataDictionary.getDataDictionaryStore('BORROWVEHICLE_STATUS'),
	    queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    forceSelection:true,
	    triggerAction:'all',
	    columnWidth:.25
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:scheduling.i18n('foss.scheduling.borrowvehicle.button.reset'),    //重置
			columnWidth:.08,
			handler:function(){
				scheduling.borrowVehicleApplyQueryForm.getForm().reset();
				this.ownerCt.ownerCt.resetQueryCondition();
			}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:scheduling.i18n('foss.scheduling.borrowvehicle.button.search'),    //查询
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form = scheduling.borrowVehicleApplyQueryForm.getForm();
				if(!form.isValid()) {
					Ext.Msg.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.enterRightInfo'));     //请输入合法的查询条件!
					return false;
				}
				var checkDateTimeSpan =  function(beginDate, endDate, message1, message2) {
					if(!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
						var begin = Ext.Date.parse(beginDate, "Y-m-d H:i:s", true);
						var end = Ext.Date.parse(endDate, "Y-m-d H:i:s", true);
						var pool = begin - end;
						// 起始时间不能大于截止时间
						if(pool > 0) {
							Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), message1);				
							return false;
						}
						var m = -86400000 * 30;
						if(pool < m) {
							Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), message2);				
							return false;
						} 
					}
					return true;
				}
				var values = scheduling.borrowVehicleApplyQueryForm.getValues();
				var borrowTimeMessage = scheduling.i18n('foss.scheduling.borrowvehicle.msg.borrowTimeMessage');   //借车起始时间不能大于结束时间
				var borrowSpanMessage = scheduling.i18n('foss.scheduling.borrowvehicle.msg.borrowSpanMessage');   //借车起始时间与结束时间跨度不能超过30天
				if(!checkDateTimeSpan(values.borrowBeginTime, values.borrowEndTime, borrowTimeMessage, borrowSpanMessage)) {
					return false;
				}
				
				var auditTimeMessage = scheduling.i18n('foss.scheduling.borrowvehicle.msg.auditTimeMessage');   //受理起始时间不能大于结束时间
				var auditSpanMessage = scheduling.i18n('foss.scheduling.borrowvehicle.msg.auditSpanMessage');   //受理起始时间与结束时间跨度不能超过30天
				if(!checkDateTimeSpan(values.auditBeginTime, values.auditEndTime, auditTimeMessage, auditSpanMessage)) {
					return false;
				}
				
				var applyTimeMessage = scheduling.i18n('foss.scheduling.borrowvehicle.msg.applyTimeMessage');   //申请起始时间不能大于结束时间
				var applySpanMessage = scheduling.i18n('foss.scheduling.borrowvehicle.msg.applySpanMessage');   //申请起始时间与结束时间跨度不能超过30天
				if(!checkDateTimeSpan(values.applyBeginTime, values.applyEndTime, applyTimeMessage, applySpanMessage)) {
					return false;
				}
				for(var i = 0;i<scheduling.vehicleTypeStore.data.items.length;i++){
				    var record = scheduling.vehicleTypeStore.data.items[i];
				    if(record.data.active = 'Y') {
						scheduling.vehicleTypeMap.add(record.data.vehicleLengthCode, record.data.vehicleLengthName);
				    }  
				}
				scheduling.borrowvehiclePagingBar.moveFirst();
			}
		}]
	}],
	resetQueryCondition:function() {
		var form = this.getForm();
		var findField = function(name) {
			return form.findField(name);
		}
		var formatStr = 'Y-m-d H:i:s';
		var now = new Date();
		var begin = Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'00','00','00'), 'Y-m-d H:i:s');

		var end = Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,'23','59','59'), 'Y-m-d H:i:s');
		findField('applyBeginTime').setValue(begin);
		findField('applyEndTime').setValue(end);
//		// 默认为未审核状态
//		findField('status').setValue('UNAPPROVED');
	}
});


scheduling.vehicleTypeMap = new Ext.util.HashMap();
scheduling.vehicleTypeStore = Ext.create('Foss.baseinfo.commonSelector.VehicleTypeStore',{pageSize:2147483647});
scheduling.vehicleTypeStore.load({params:{'vehicleTypeEntity.active' : 'Y'}});
//(function(records, operation, success) {
//	Ext.each(records, function(record) {
//		if(record.data.active = 'Y') {
//			scheduling.vehicleTypeMap.add(record.data.vehicleLengthCode, record.data.vehicleLengthName);
//		}
//	});
//});

scheduling.queryAcceptanceTime = function() {
	if(scheduling.acceptanceTime) {
		return scheduling.acceptanceTime;
	}
	scheduling.acceptanceTime = 15;
	Ext.Ajax.request({
	       actionMethods:'POST',
	       async:true,
	       url: scheduling.realPath('queryAcceptanceTime.action'),
		   params:{},
		   success: function(response) {
			   var result = Ext.decode(response.responseText);
			   scheduling.acceptanceTime = result.borrowVehicleVo.acceptanceTime;
		   },
		   //异常message
		   exception: function(response) {
			   var result = Ext.decode(response.responseText);
			   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
		   }
	});	
	return scheduling.acceptanceTime;
}
scheduling.queryAcceptanceTime();


// combobox 绑定的值  转换为map， map的key位combobox value , value位text
scheduling.getComboboxValueMap = function(from, name) {
	var fields = from.getForm().getFields();
	var map = new Ext.util.HashMap();
	if(!fields) return map; 
	var items = fields.items;
	if(!items) return map;
	for(var i = 0; i < items.length; i++) {
		var item = items[i];
		// xtype为 combobox
		if("combobox" != item.xtype) 
			continue;
		if(item.name != name) 
			continue;
		var dispalyKey = item.displayField;
		if(!dispalyKey) return map;
		var valueKey = item.valueField;
		if(!valueKey) return map;
		var store = item.getStore();
		if(!store) return map;
		store.each(function(record){
			map.add(record.get(valueKey), record.get(dispalyKey))
		})
		return map;
	}
	return map;
}

//定义所属车队小组信息的model
Ext.define('Foss.scheduling.queryTransTeamListModel',{
	extend:'Ext.data.Model',
	fields:[{name:'code', type:'string'},
	        {name:'name', type:'string'}
	        ]
});

//定义所属车队小组信息的store
Ext.define('Foss.scheduling.queryTransTeamListStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	model: 'Foss.scheduling.queryTransTeamListModel',
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods: 'POST',
		url: scheduling.realPath('queryTransTeamListForBorrow.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'passBorrowApplyVo.transTeamList'
		}
	}
});

//model
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryModel',{
	extend:'Ext.data.Model',
	fields:[{
			// 借车单号
			name:'borrowNo',
			type:'string',
		},{
			// 申请时间
			name:'applyTime',
			type:'date',
			convert: dateConvert
		},{
			// 使用用途
			name:'borrowPurpose',
			type:'string',
		},{
			// 使用类型
			name:'useType',
			type:'string',
		},{
			// 车型
			name:'orderVehicleModel',
			type:'string',
		},{
			// 受理部门编码
			name:'auditOrgCode',
			type:'string',
		},{
			// 受理部门名称
			name:'auditOrgName',
			type:'string',
		},{
			// 备注
			name:'notes',
			type:'string',
		},{
			// 重量
			name:'weight',
			type:'string',
		},{
			// 体积
			name:'volume',
			type:'string',
		},{
			// 借车开始时间
			name:'borrowBeginTime',
			type:'date',
			convert: dateConvert
		},{
			// 借车结束时间
			name:'borrowEndTime',
			type:'date',
			convert: dateConvert
		},{
			// 申请人员编码
			name:'applyEmpCode',
			type:'string',
		},{
			// 申请人员名称
			name:'applyEmpName',
			type:'string',
		},{
			// 电话
			name:'telephoneNo',
			type:'string',
		},{
			// 移动电话
			name:'mobilephoneNo',
			type:'string',
		},{
			// 借车部门编码
			name:'applyOrgCode',
			type:'string',
		},{
			// 借车部门名称
			name:'applyOrgName',
			type:'string',
		},{
			// 状态
			name:'status',
			type:'string',
		},{
			// 车牌号
			name:'vehicleNo',
			type:'string',
		},{
			name:'id',
			type:'string',
		},{
			// 受理时间
			name:'auditTime',
			type:'string',
			convert: dateConvert
		}]
	
});

//store
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryStore',{
	extend:'Ext.data.Store',
	model:'Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryModel',
	proxy: {
        type : 'ajax',
        actionMethods:'POST',
        url: scheduling.realPath('queryBorrowVehicleApplyList.action'),
		reader : {
			type : 'json',
			root : 'borrowVehicleVo.borrowVehicleList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation) {
				var borrowVehicleApplyQueryForm = scheduling.borrowVehicleApplyQueryForm.getValues();
				Ext.apply(operation, {
					params : {
						//借车单号
						'borrowVehicleVo.borrowVehicleDto.borrowNo' : borrowVehicleApplyQueryForm.borrowNo,
						//借车时间
						'borrowVehicleVo.borrowVehicleDto.borrowBeginTime' :borrowVehicleApplyQueryForm.borrowBeginTime ,
						'borrowVehicleVo.borrowVehicleDto.borrowEndTime' :borrowVehicleApplyQueryForm.borrowEndTime,
						//使用类型
						'borrowVehicleVo.borrowVehicleDto.useType' :borrowVehicleApplyQueryForm.useType ,
						//借车车型
						'borrowVehicleVo.borrowVehicleDto.orderVehicleModel' :borrowVehicleApplyQueryForm.orderVehicleModel ,
						//受理时间
						'borrowVehicleVo.borrowVehicleDto.auditBeginTime' : borrowVehicleApplyQueryForm.auditBeginTime,
						'borrowVehicleVo.borrowVehicleDto.auditEndTime' : borrowVehicleApplyQueryForm.auditEndTime,
						// 借车部门
						'borrowVehicleVo.borrowVehicleDto.applyOrgCode' : borrowVehicleApplyQueryForm.applyOrgCode,
						// 受理部门
						'borrowVehicleVo.borrowVehicleDto.auditOrgCode' : borrowVehicleApplyQueryForm.auditOrgCode,
						//申请时间
						'borrowVehicleVo.borrowVehicleDto.applyBeginTime' : borrowVehicleApplyQueryForm.applyBeginTime,
						'borrowVehicleVo.borrowVehicleDto.applyEndTime' : borrowVehicleApplyQueryForm.applyEndTime,
						//车牌号
						'borrowVehicleVo.borrowVehicleDto.vehicleNo' :borrowVehicleApplyQueryForm.vehicleNo,
						//借车用途
						'borrowVehicleVo.borrowVehicleDto.borrowPurpose' :borrowVehicleApplyQueryForm.borrowPurpose,
						//操作状态
						'borrowVehicleVo.borrowVehicleDto.status' :borrowVehicleApplyQueryForm.status,
					}
				});	
		}
	}
});

//查询结果grid
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryGrid',{
	extend:'Ext.grid.Panel',
	frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText: scheduling.i18n('foss.scheduling.borrowvehicle.tip.queryIsNull'),    //查询结果为空
	title:scheduling.i18n('foss.scheduling.passborrowvehicle.BorrowVehicleApplyQueryGrid.title'),    //查询结果
	//提交请求
	ajaxRequet : function(url, submitBeforeMsg) {
		//提交前confirm message
		if(!submitBeforeMsg) submitBeforeMsg = scheduling.i18n('foss.scheduling.borrowvehicle.msg.sureOperator');    //您确定要操作吗？
		var grid = this;
	    Ext.MessageBox.confirm(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), submitBeforeMsg, function(button){
	    	//确定按钮
		   if(button == 'yes') {
			   //grid 选中的行
			   var dataList = grid.getSelectionModel().getSelection();
			   var borrowNoList = new Array();
			   var applyOrgCodeList = new Array();
			   //处理id
			   Ext.each(dataList, function(object) {
				   borrowNoList.push(object.get('borrowNo'));
				   applyOrgCodeList.push(object.get('applyOrgCode'));
			   })
			   Ext.Ajax.request({
				   url:scheduling.realPath(url),
				   params:{'borrowVehicleVo.borrowNoList' : borrowNoList,'borrowVehicleVo.applyOrgCodeList' :applyOrgCodeList},
				   success: function(response) {
					   Ext.ux.Toast.msg(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.operatorSuccess'));    //操作成功
					   scheduling.borrowvehiclePagingBar.moveFirst();
				   },
				   //异常message
				   exception: function(response) {
					   var result = Ext.decode(response.responseText);
					   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
				   }
			   });
		   }
	   });
	},
	//申请  撤销  确认到达前 检查状态
   checkStatus: function(map, errorMessage, noSelectMsg, groupZoneMsg) {
	   // 状态错误mesage
	   if(!errorMessage) errorMessage = scheduling.i18n('foss.scheduling.borrowvehicle.msg.statusError');   //状态错误
	   // 没有选中行时的message
	   if(!noSelectMsg) noSelectMsg = scheduling.i18n('foss.scheduling.borrowvehicle.msg.choiseNeedInfo');   //请选择您要操作的数据行
	   // 集中转货申请 不能操作message
	   if(!groupZoneMsg) groupZoneMsg = scheduling.i18n('foss.scheduling.borrowvehicle.msg.cannotOperator');   //集中转货申请不能操作
	   var grid = this;
	   if(!grid.getSelectionModel().hasSelection()) {
		   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), noSelectMsg);
	 	   return false;
	   }
	   var flag = true;
	   if(grid.getSelectionModel().hasSelection()) {
		   // grid 选中行list
		   var dataList = grid.getSelectionModel().getSelection();
		   Ext.each(dataList, function(object) {
			   var objStatus = object.raw.status;
			   // 如果map中不包含  当前行的状态 ， 认为状态错误. 
			   if(!map.containsKey(objStatus)) {
				   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), errorMessage);
				   flag = false;
				   return false;
			   }
		   })
	   	} 
	   return flag;
	},
	dockedItems:[{
	   xtype:'toolbar',
	   dock:'top',
	   layout:'column',
	   defaultType:'button',
	   items:[{
		   xtype:'container',
		   html:'&nbsp;',
		   columnWidth:.6
	   },{
		   text:scheduling.i18n('foss.scheduling.borrowvehicle.button.apply'),   //申请
		   columnWidth:.08,
		   name:'btnSubmit',
		   handler:function(){
			   // grid object
			   var grid = this.ownerCt.ownerCt;
			   var borrowVehicleApplyId = null;
			   var borrowVehicleCreateOrgCode = null
			   // 选中的行
			   if(grid.getSelectionModel().hasSelection()) {
				   var dataList = grid.getSelectionModel().getSelection();
				   if(dataList.length > 1) {
					   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.cannotOperatorAtOneTime'));    //该动作不能支持多条目同时操作!
					   return false;
				   }
				   var map = new Ext.util.HashMap();
				   //暂存
				   map.add('STAGING','STAGING');
				   //未审核
				   map.add('UNAPPROVED','UNAPPROVED');
				   //退回
				   map.add('RETURN', 'RETURN');
				   // 撤销
				   var status = dataList[0].raw.status;
				   //只有 暂存，未审核, 退回状态的才能做修改操作
				   var message = scheduling.i18n('foss.scheduling.borrowvehicle.msg.thisStatusCannotEdit');   //该状态不支持编辑!
				   if(!grid.checkStatus(map, message)) {
					   return false;
				   }
				   //当前选中行的主键id
				   borrowVehicleApplyId = dataList[0].get('id');
				   borrowVehicleCreateOrgCode = dataList[0].get('applyOrgCode');
			   }
			   var array = {borrowVehicleVo:{borrowVehicleDto:{id:borrowVehicleApplyId,applyOrgCode:borrowVehicleCreateOrgCode}}};
			   Ext.Ajax.request({
				    url:scheduling.realPath('validateApplyBorrow.action'),
				    jsonData:array,
					success :function(response){
					   var json = Ext.decode(response.responseText);
					   // 约车申请window
					   if(!scheduling.borrowVehicleApplyWindow) {
						   scheduling.borrowVehicleApplyWindow = Ext.create('Foss.scheduling.borrowvehicle.BorrowVehicleApplyWindow');
					   }
					   // 选中行的id
					   scheduling.borrowVehicleApplyWindow.borrowVehicleApplyId = borrowVehicleApplyId;
					   scheduling.borrowVehicleApplyWindow.show();
					   // 如果是修改操作  load数据
					   scheduling.borrowVehicleApplyWindow.initData();
					},
					exception:function(response){
						var json = Ext.decode(response.responseText);
	    				Ext.Msg.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'),json.message);  //提示失败
					}
			   });
		   }
	   },{
		   text:scheduling.i18n('foss.scheduling.borrowvehicle.button.undo'),   //撤销
		   columnWidth:.08,
		   handler:function() {
			   var grid = this.ownerCt.ownerCt;
			   var message = scheduling.i18n('foss.scheduling.borrowvehicle.msg.onlyUnApproved');   //只有未审核状态的才能撤销!
			   var map = new Ext.util.HashMap();
//			   	暂存
//			   map.add('STAGING','STAGING');
			   //未审核
			   map.add('UNAPPROVED','UNAPPROVED');
			   //只有 暂存，未审核才可以做撤销操作
			   if(!grid.checkStatus(map, message)) {
				   return false;
			   }
			   grid.ajaxRequet('doUndoBorrowVehicleApply.action', scheduling.i18n('foss.scheduling.borrowvehicle.msg.sureUndo'));   //您确定要撤销吗？
			   
		   }
	   },{
		   text:scheduling.i18n('foss.scheduling.borrowvehicle.button.approvedAccepted'),   //审核受理
		   columnWidth:.08,
		   handler:function() {
//			   // grid object
			   var grid = this.ownerCt.ownerCt;
			   if(!grid.getSelectionModel().hasSelection()) {
				   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.choiseNeedInfo'));
				   return false;
			   }
			   var map = new Ext.util.HashMap();
			   //未审核
			   map.add('UNAPPROVED','UNAPPROVED');
			   map.add('ACCEPTED','ACCEPTED');
			   // 选中的行
			   var dataList = grid.getSelectionModel().getSelection();
			   var borrowNoList = new Array();
			   for(var i = 0; i< dataList.length; i++) {
				   var record = dataList[i];
				   var status = record.get('status');
				   var borrowNo = record.get('borrowNo');
				   if(!map.containsKey(status)) {
					   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.errorBorrowNo') + borrowNo);   //只有未审核,审核状态，才能做审核受理操作,错误借车单号:
					   return false;
				   }
				   borrowNoList.push(borrowNo);
			   }
			   // 借车受理window
			   scheduling.acceptedBorrowVehicleApplyWindow = Ext.create('Foss.scheduling.borrowvehicle.PassBorrowVehicleWindow') 
			   scheduling.acceptedBorrowVehicleApplyWindow.show();
			   scheduling.acceptedBorrowVehicleApplyWindow.borrowNoList = borrowNoList;
			   scheduling.acceptedBorrowVehicleApplyWindow.initData();
		   }
	   },{
		   text:scheduling.i18n('foss.scheduling.borrowvehicle.button.confirmArrive'),   //确认到达
		   columnWidth:.08,
		   handler:function() {
			   var grid = this.ownerCt.ownerCt;
			   var message = scheduling.i18n('foss.scheduling.borrowvehicle.msg.cannotConfirmArrive');   //该条目没有达到已受理状态,无法确认到达!
			   var map = new Ext.util.HashMap();
			   // 已受理状态 
			   map.add('ACCEPTED','ACCEPTED');
			   if(!grid.checkStatus(map, message)) {
				   return false;
			   }
			   grid.ajaxRequet('doBorrowVehicleConfirmTo.action');
		   }
	   },{
		   text:scheduling.i18n('foss.scheduling.borrowvehicle.button.vehicleGiveBack'),   //车辆归还
		   columnWidth:.08,
		   handler:function() {
			   var grid = this.ownerCt.ownerCt;
			   var message = scheduling.i18n('foss.scheduling.borrowvehicle.msg.cannotGiveBack');   //该条目没有达到确认到达状态,无法车辆归还!
			   var map = new Ext.util.HashMap();
			   map.add('VERIFY_ARRIVE','VERIFY_ARRIVE');
			   if(!grid.checkStatus(map, message)) {
				   return false;
			   }
			   grid.ajaxRequet('doBorrowVehicleGiveBack.action');
		   }
	   }]
	}],
	columns:[{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowNo'),    //借车单号
		dataIndex: 'borrowNo',
		windowClassName : 'Foss.scheduling.borrowvehicle.BorrowVehicleDetailWindow',
		xtype: 'openwindowcolumn',
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.operatorStatus'),    //操作状态
		dataIndex: 'status',
		renderer: function(value) {
			if(!value) {
				return ''
			}
			return FossDataDictionary.rendererSubmitToDisplay(value, 'BORROWVEHICLE_STATUS');
		},
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.useType'),    //使用类型
		dataIndex: 'useType',
		renderer: function(value) {
			if(!value) {
				return ''
			}
			return scheduling.useTypeMap.get(value);
		},
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.orderVehicleModel'),    //借车车型
		dataIndex: 'orderVehicleModel',
		flex:1.1,
		renderer:function(value){
			return scheduling.vehicleTypeMap.get(value);
		}
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyOrgName'),    //借车部门
		dataIndex: 'applyOrgName',
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.auditOrgName'),    //受理部门
		dataIndex: 'auditOrgName',
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowBeginTime'),    //借车起始时间
		xtype: 'datecolumn',
		dataIndex: 'borrowBeginTime',
		format:'Y-m-d H:i:s',
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowEndTime'),    //借车结束时间
		xtype: 'datecolumn',
		dataIndex: 'borrowEndTime',
		format:'Y-m-d H:i:s',
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.planVehicleNo'),    //安排车牌号
		dataIndex: 'vehicleNo',
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.auditTime'),    //受理时间
		xtype: 'datecolumn',
		dataIndex: 'auditTime',
		format:'Y-m-d H:i:s',
		flex:1.1
	},{
		header: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyTime'),    //申请时间
		xtype: 'datecolumn',
		dataIndex: 'applyTime',
		format:'Y-m-d H:i:s',
		flex:1.1
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		scheduling.borrowvehiclePagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowParams, rp, store) {
			var status = record.get('status');
			if(status != 'UNAPPROVED') {
				return 'notice-customer-row-white';
			}
			var borrowvehicleNo = record.get('borrowNo');
			// 申请时间
			var applyTime = record.get('applyTime');
			var now  = new Date();
			// 配置的时间
			var acceptanceTime = scheduling.acceptanceTime;
			// 时间差的毫秒数
			var date3 = now.getTime()- applyTime.getTime();
			//计算相差分钟数
			var minutes= Math.floor(date3 / 1000 / 60);
			if(minutes <= acceptanceTime) {
				return 'notice-customer-row-white';
			}
			return 'notice-customer-row-yellow';
		}
	},
	listeners : {
		render : function(grid,opt){
			var currentDept = FossUserContext.getCurrentDept();
			//如果是调度组，可以审核
			var btns = grid.dockedItems.items[2].query('button');
			Ext.Array.each(btns, function(item, index){
				item.hide();
			});
			//申请按钮
			if(scheduling.isPermission('scheduling/saveBorrowVehicleApplyButton')){
				btns[0].show();
			}
			//撤销按钮
			if(scheduling.isPermission('scheduling/doUndoBorrowVehicleApplyButton')){
				btns[1].show();
			}
			//审核按钮
			if(scheduling.isPermission('scheduling/doBorrowVehicleApprovedButton')){
				btns[2].show();
			}
			//确认到达
			if(scheduling.isPermission('scheduling/doBorrowVehicleConfirmToButton')){
				btns[3].show();
			}
			//空车归还
			if(scheduling.isPermission('scheduling/doBorrowVehicleGiveBackButton')){
				btns[4].show();
			}
		}
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询条件
	var borrowVehicleApplyQueryForm = Ext.create('Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryForm');
	//grid
	var borrowVehicleApplyQueryGrid = Ext.create('Foss.scheduling.borrowvehicle.BorrowVehicleApplyQueryGrid');
	// 保存到全局变量中
	scheduling.borrowVehicleApplyQueryForm = borrowVehicleApplyQueryForm;
	scheduling.borrowVehicleApplyQueryGrid = borrowVehicleApplyQueryGrid;
	Ext.create('Ext.panel.Panel',{
		id: 'T_scheduling-borrowVehicleIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [borrowVehicleApplyQueryForm, borrowVehicleApplyQueryGrid],
		renderTo: 'T_scheduling-borrowVehicleIndex-body'
	});
	scheduling.useTypeMap = scheduling.getComboboxValueMap(scheduling.borrowVehicleApplyQueryForm, 'useType');
	scheduling.borrowPurposeMap = scheduling.getComboboxValueMap(scheduling.borrowVehicleApplyQueryForm, 'borrowPurpose');
	scheduling.borrowVehicleApplyQueryForm.resetQueryCondition();
});


/**
 * 借车申请Model
 */
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleApplyFormModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'borrowNo',
		type:'string',
	},{
		name:'applyTime',
		type:'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'borrowPurpose',
		type:'string',
	},{
		name:'useType',
		type:'string',
	},{
		name:'orderVehicleModel',
		type:'string',
	},{
		name:'auditOrgCode',
		type:'string',
	},{
		name:'auditOrgName',
		type:'string',
	},{
		name:'notes',
		type:'string',
	},{
		name:'weight',
		type:'string',
	},{
		name:'volume',
		type:'string',
	},{
		name:'borrowBeginTime',
		type:'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'borrowEndTime',
		type:'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name:'applyEmpCode',
		type:'string',
	},{
		name:'applyEmpName',
		type:'string',
	},{
		name:'telephoneNo',
		type:'string',
	},{
		name:'mobilephoneNo',
		type:'string',
	},{
		name:'applyOrgCode',
		type:'string',
	},{
		name:'applyOrgName',
		type:'string',
	},{
		name:'status',
		type:'string',
	},{
		name:'id',
		type:'string',
	}]
});

/**
 * 借车申请window
 */
Ext.define('Foss.scheduling.borrowvehicle.BorrowVehicleApplyWindow',{
	extend:'Ext.window.Window',
	title:scheduling.i18n('foss.scheduling.passborrowvehicle.BorrowVehicleApplyWindow.title'),    //借车申请
	width:700,
	height:430,
	resizable:false,
	carInfo:null,
	applyInfo: null,
	buttonArea: null,
	operateForm:null,
	//用于保存 从约车申请查询页面传入的主键id
	borrowVehicleApplyId:null,
	modal:true,
	closable: true,
	closeAction: 'hide',
	// 车辆信息
	createCarInfo: function() {
		if(this.carInfo) {
			return this.carInfo;
		}
		this.carInfo = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title:scheduling.i18n('foss.scheduling.passborrowvehicle.VehicleInfoPanel.title'),    //车辆信息
			width:655,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 85,
					margin: '5 10 5 10'
				},
			    items: [{
					name: 'useType',
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.useType'),    //使用类型
					xtype:'combobox',
				    store:Ext.create('Ext.data.Store', {
					    fields: ['valueName', 'valueCode'],
					    data : [
					        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.regular'), 'valueCode': 'REGULAR'},   //班车
					        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.pickGoods'), 'valueCode': 'PICK_GOODS'}  //接送货
					    ]
					}),
				    queryMode: 'local',
				    displayField: 'valueName',
				    valueField: 'valueCode',
				    editable:false,
				    forceSelection:true,
				    triggerAction:'all',
				    allowBlank:false,
				    columnWidth:.5
				},{
					name: 'borrowPurpose',
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowPurpose'),    //借车用途
					xtype:'combobox',
				    store:Ext.create('Ext.data.Store', {
					    fields: ['valueName', 'valueCode'],
					    data : [
					        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.takeGoods'), 'valueCode': 'TAKE_GOODS'},  //接货
					        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.deliversGoods'), 'valueCode': 'DELIVERS_GOODS'},  //送货
					        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.transferringGoods'), 'valueCode': 'TRANSFERRING_GOODS'},  //转货
					        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.bus'), 'valueCode': 'BUS'},  //物流班车
					        {'valueName':scheduling.i18n('foss.scheduling.borrowvehicle.options.other'), 'valueCode': 'OTHER'}  //其他
					    ]
					}),
				    queryMode: 'local',
				    displayField: 'valueName',
				    valueField: 'valueCode',
				    editable:false,
				    forceSelection:true,
				    triggerAction:'all',
				    allowBlank:false,
				    columnWidth:.5
			    },{
					name:'orderVehicleModel',
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.orderVehicleModel'),    //借车车型
					xtype:'commonvehicletypeselector',
					allowBlank:false,
					columnWidth: .5
				},{
					name:'notes',
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.notes'),    //备注
	        		maxLength : 333,
	        		maxLengthText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.maxLength'),    //长度已超过最大限制
					columnWidth: .5
			    },{
					name:'weight',
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.weight'),    //货物重量 
			    	columnWidth: .39,
			    	regex:/^\d+\.?\d{0,3}$/,
	        		regexText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.mustNumber'),    //格式输入有误.必须是数字!
	        		maxLength : 9,
	        		maxLengthText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.maxLength'),    //长度已超过最大限制
			    	allowBlank:false
				},{
					columnWidth: .05,
					readOnly:true,
					labelWidth :42,
					labelSeparator:'',
					fieldLabel: 'kg'
				},{
					name:'volume',
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.volume'),    //货物体积 
			    	columnWidth: .39,
			    	regex:/^\d+\.?\d{0,3}$/,
	        		regexText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.mustNumber'),    //格式输入有误.必须是数字!
	        		maxLength : 9,
	        		maxLengthText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.maxLength'),    //长度已超过最大限制
			    	allowBlank:false
			    },{
					columnWidth: .05,
					readOnly:true,
					labelSeparator:'',
					labelWidth :33,
					fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.square')    //方
				}]
			}]
		})
		return this.carInfo;
	},
	// 货物信息
	createApplyInfo:function() {
		if(this.applyInfo) {
			return this.applyInfo;
		}
		this.applyInfo = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title:scheduling.i18n('foss.scheduling.passborrowvehicle.BorrowVehicleApplyWindow.apply.title'),    //申请信息
			width:655,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 85,
					margin: '5 10 5 10'
				},
			    items: [
//			      {
//			    		xtype : 'combobox',
//			    		valueField:'code',
//			    		displayField:'name',
//			    		store:Ext.create('Foss.scheduling.queryTransTeamListStore'),
//			    		fieldLabel : scheduling.i18n('foss.scheduling.borrowvehicle.label.auditOrgName'),    //受理部门
//						name:'auditOrgCode',
//						allowBlank:false,
//						editable : false,
//						forceSelection:true,
//						transDepartment:'Y',
//						columnWidth: .5
//				}
			      {
					xtype : 'dynamicorgcombselector',
					fieldLabel : scheduling.i18n('foss.scheduling.borrowvehicle.label.auditOrgName'),    //受理部门
					name:'auditOrgCode',
					type:'ORG',
					transTeam:'Y',
					allowBlank:false,
					forceSelection:true,
					columnWidth: .5
				},{
					},
//					{
//						xtype : 'dynamicorgcombselector',
//						fieldLabel : scheduling.i18n('foss.scheduling.borrowvehicle.label.auditOrgName'),    //受理部门
//						name:'auditOrgCode',
//						type:'ORG',
//						allowBlank:false,
//						forceSelection:true,
//						transTeam:'Y',
//						columnWidth: .5
//					},
					{
						xtype : 'dynamicorgcombselector',
						name:'applyOrgCode',
						fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyOrgName'),    //借车部门
						readOnly :true,
						allowBlank:false,
						columnWidth: .5
					},{
						xtype:'rangeDateField',
						dateType:'datetimefield_date97',
						fieldId:'Foss_borrowvehicle_borrowVehicleApplyWindow_borrowTime_ID',
						fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.borrowTime'),    //借车时间
						fromName: 'borrowBeginTime',
						toName: 'borrowEndTime',
						disallowBlank:true,
						editable:false,
						allowBlank:false,
						columnWidth: 1
					},{
						name:'applyEmpCode',
						fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.applyEmpName'),    //申请人
						xtype:'commonemployeeselector',
						allowBlank:false,
						columnWidth: .5
					},{
						name:'telephoneNo',
						fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.telephoneNo'),    //固定电话 
						maxLength : 16,
						columnWidth: .5
					},{
						name:'mobilephoneNo',
						fieldLabel: scheduling.i18n('foss.scheduling.borrowvehicle.label.mobilephoneNo'),    //手机 
						maxLength : 11,
						minLength : 11,
						maxLengthText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.maxLength'),    //长度已超过最大限制
						regex:/^\d+$/,
		        		regexText:scheduling.i18n('foss.scheduling.borrowvehicle.tip.mustNumber'),    //格式输入有误.必须是数字!
						columnWidth: .5
					}]
			}]
		})
		return this.applyInfo;
	},
	//按钮区域
	createButtonArea: function() {
		if(this.buttonArea) {
			return this.buttonArea;
		}
		this.buttonArea = Ext.create('Ext.container.Container',{
			defaultType:'button',
			name:'buttonArea',
			defaults:{
				margin: '2 7 2 7'
			},
			//新增 修改操作
			saveBorrowVehicleApply : function(url, status, callback) {
				// form object
				var borrowVehicleApplyForm = scheduling.borrowVehicleApplyForm;
				var operateForm = borrowVehicleApplyForm.getForm();
				var findField = function(name) {
					return operateForm.findField(name);
				};
				var record = operateForm.getRecord();
				operateForm.updateRecord(record);
				// 受理部门
				record.data.auditOrgName = findField('auditOrgCode').getRawValue();
				// 借车部门
				record.data.applyOrgName = FossUserContext.getCurrentDept().name;
				record.data.applyEmpName = findField('applyEmpCode').getRawValue();
				record.data.borrowBeginTime =findField('borrowBeginTime').getValue();
				record.data.borrowEndTime =  findField('borrowEndTime').getValue();
				// 状态
				record.data.status = status;
				// 校验整数部分
				var weight = parseInt(record.data.weight) / 10000;
				if(weight >= 10) {
					Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.weightOnlyFive'));  //货物重量整数部分最多5位!
					return false;
				}
				var volume = parseInt(record.data.volume) / 10000;
				if(volume >= 10) {
					Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.volumeOnlyFive'));   //货物体积整数部分最多5位!
					return false;
				}
				// 封装vo
            	var vo = {borrowVehicleVo:{borrowVehicleEntity:record.data}};
            	Ext.Ajax.request({
    				url : scheduling.realPath(url),
    				jsonData: vo,
    				success:function(response){
    					Ext.ux.Toast.msg(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.operatorSuccess'));    //操作成功
    					scheduling.borrowVehicleApplyWindow.close();
    					scheduling.borrowvehiclePagingBar.moveFirst();
    				},
    				exception: function(response) {
  					   var result = Ext.decode(response.responseText);
  					   Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
  				    }
    			});	
            	
			},
			layout:'column',
			items:[{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.29
			},{
				text:scheduling.i18n('foss.scheduling.borrowvehicle.button.submit'),    //提交
				disabled : !scheduling.isPermission('scheduling/saveBorrowVehicleApplyButton'),
				hidden : !scheduling.isPermission('scheduling/saveBorrowVehicleApplyButton'),
				columnWidth:.14,
				handler:function() {
					var btnSumbit = this;
					var borrowVehicleApplyForm = scheduling.borrowVehicleApplyForm;
					var operateForm = borrowVehicleApplyForm.getForm();
					var findField = function(name) {
						return operateForm.findField(name);
					};
					// 验证
					if(!operateForm.isValid()) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.pleaseEnterAll'));    //有借车必填项未填,请补全后提交!
						return false;
					}
					// 固话 与 手机号码必须输入一个
					var telephoneNo = findField('telephoneNo').getValue();
					var mobilephoneNo = findField('mobilephoneNo').getValue();
					if(Ext.isEmpty(telephoneNo) && Ext.isEmpty(mobilephoneNo)) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.mustEnterOne'));   //固话 与 手机号码必须输入一个!
						return false;
					}
					var record = operateForm.getRecord();
					operateForm.updateRecord(record);
					// 校验整数部分
					var weight = parseInt(record.data.weight) / 10000;
					if(weight >= 10) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.weightOnlyFive'));  //货物重量整数部分最多5位!
						return false;
					}
					var volume = parseInt(record.data.volume) / 10000;
					if(volume >= 10) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.volumeOnlyFive'));   //货物体积整数部分最多5位!
						return false;
					}

					//提交 状态为UNAPPROVED 未审核
					Ext.Msg.show({
	            		title:scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'),     //提示
						msg:scheduling.i18n('foss.scheduling.borrowvehicle.msg.confirmSubmit'),     //确定要提交吗?
						buttons:Ext.Msg.YESNO,
						icon: Ext.Msg.QUESTION, 
						fn : function(btn){
							if(btn == 'no'){
								return;
							}else{
								var url = "saveBorrowVehicleApply.action";
								btnSumbit.ownerCt.saveBorrowVehicleApply(url, 'UNAPPROVED');
							}
						}
	            	});
				}
			},{
				name:'doTemporarySaveBtn',
				text:scheduling.i18n('foss.scheduling.borrowvehicle.button.staging'),    //暂存
				disabled : !scheduling.isPermission('scheduling/doTempSaveBorrowVehicleApplyButton'),
				hidden : !scheduling.isPermission('scheduling/doTempSaveBorrowVehicleApplyButton'),
				columnWidth:.14,
				handler:function() {
					var borrowVehicleApplyForm = scheduling.borrowVehicleApplyForm;
					var operateForm = borrowVehicleApplyForm.getForm();
					var findField = function(name) {
						return operateForm.findField(name);
					};
					// 验证
					if(!operateForm.isValid()) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.pleaseEnterAll'));    //有借车必填项未填,请补全后提交!
						return false;
					}
					// 固话 与 手机号码必须输入一个
					var telephoneNo = findField('telephoneNo').getValue();
					var mobilephoneNo = findField('mobilephoneNo').getValue();
					if(Ext.isEmpty(telephoneNo) && Ext.isEmpty(mobilephoneNo)) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.mustEnterOne'));   //固话 与 手机号码必须输入一个!
						return false;
					}
					
					var record = operateForm.getRecord();
					operateForm.updateRecord(record);
					// 校验整数部分
					var weight = parseInt(record.data.weight) / 10000;
					if(weight >= 10) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.weightOnlyFive'));  //货物重量整数部分最多5位!
						return false;
					}
					var volume = parseInt(record.data.volume) / 10000;
					if(volume >= 10) {
						Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), scheduling.i18n('foss.scheduling.borrowvehicle.msg.volumeOnlyFive'));   //货物体积整数部分最多5位!
						return false;
					}
					
					
					//暂存  状态为 STAGING
					var url = "saveBorrowVehicleApply.action";
					this.ownerCt.saveBorrowVehicleApply(url, 'STAGING');
				}
			},{
				name:'resetBtn',
				text:scheduling.i18n('foss.scheduling.borrowvehicle.button.reset'),    //重置
				columnWidth:.14,
				handler:function() {
					var id = this.ownerCt.ownerCt.ownerCt.borrowVehicleApplyId;
					if(!id) {
						//新增重置
						scheduling.borrowVehicleApplyForm.getForm().reset();
						scheduling.borrowVehicleApplyWindow.setDefaultValue();
						return;
					}
					//修改重置, 重新从数据库load数据
					this.ownerCt.ownerCt.ownerCt.initData();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.29
			}]
		});
		return this.buttonArea;
	},
	//初始化全局form
	createOperateForm: function() {
		if(this.operateForm) {
			return this.operateForm;
		}
		this.operateForm  = Ext.create('Ext.form.Panel', {
		    frame: false
		});
		return this.operateForm;
	},
	//初始化所有组件
	initAllComponent: function() {
		this.createCarInfo();
		this.createApplyInfo();
		this.createButtonArea();
	},
	setDefaultValue: function() {
		var borrowVehicleApplyForm = scheduling.borrowVehicleApplyForm;
		var operateForm = borrowVehicleApplyForm.getForm();
		// 用户姓名
		var userName =  FossUserContext.getCurrentUser().employee.empName;
		// 用户编号
		var userCode =  FossUserContext.getCurrentUser().employee.empCode;
		// 部门名称 编码
		var deptName = FossUserContext.getCurrentDept().name;
		var deptcode = FossUserContext.getCurrentDept().code;
		// 手机号码
		var mobilePhone = FossUserContext.getCurrentUserEmp().mobilePhone;
		var form = scheduling.borrowVehicleApplyForm.getForm();
		var findField = function(name) {
			return form.findField(name);
		};
		// 借车部门
		findField('applyOrgCode').setCombValue(deptName, deptcode);
		// 申请人
		findField('applyEmpCode').setCombValue(userName, userCode);
		// 移动电话
		findField('mobilephoneNo').setValue(mobilePhone);
		
		// 获得服务端时间
		Ext.Ajax.request({
			url:scheduling.realPath('queryCurrentTime.action'),
			params:{},
			success:function(response) {
				var result = Ext.decode(response.responseText);
				var currentTime = result.borrowVehicleVo.currentTime;
				var findField = function(name) {
					return scheduling.borrowVehicleApplyForm.getForm().findField(name);
				}
				
				var now = new Date(currentTime);
				var formatStr = 'Y-m-d H:i:s';
				var begin = Ext.Date.format(now, formatStr);
				var endDate = new Date(now.getFullYear(),now.getMonth(),now.getDate() + 1, now.getHours(), now.getMinutes(), now.getSeconds());
				var end = Ext.Date.format(endDate, formatStr);
				
				findField('borrowBeginTime').setValue(begin);
				findField('borrowEndTime').setValue(end);
			},
		});
	},
	// 编辑操作  初始化数据.
	initData: function(){
		var id = this.borrowVehicleApplyId;
		var form = scheduling.borrowVehicleApplyForm;
		if(!id) {
			var record = Ext.create('Foss.scheduling.borrowvehicle.BorrowVehicleApplyFormModel', {});
			form.loadRecord(record);
			scheduling.borrowVehicleApplyForm.getForm().reset();			
			this.setDefaultValue();
			return;
		}
		//获得暂存按钮object
		//var items = form.items.items;
		//var buttonAreaItem = items[2].items.items;
		//var doTemporarySaveBtn = buttonAreaItem[2];
		// 隐藏暂存按钮
		//doTemporarySaveBtn.hidden = !doTemporarySaveBtn.hidden;
		var params = {"BorrowVehicleVo.borrowVehicleEntity.id":id};
		Ext.Ajax.request({
			url:scheduling.realPath('queryBorrowVehicleApply.action'),
			params:params,
			success:function(response) {
				var result = Ext.decode(response.responseText);
				var record = Ext.create('Foss.scheduling.borrowvehicle.BorrowVehicleApplyFormModel', result.borrowVehicleVo.borrowVehicleEntity);
				form.loadRecord(record);
				var findField = function(name) {
					return form.getForm().findField(name);
				}
				// 车型
				var orderVehicleModelCode = record.data.orderVehicleModel;
				findField('orderVehicleModel').setCombValue(scheduling.vehicleTypeMap.get(orderVehicleModelCode), orderVehicleModelCode);
				// 受理部门
				var auditOrgCode = record.data.auditOrgCode;
				var auditOrgName = record.data.auditOrgName;
				findField('auditOrgCode').setCombValue(auditOrgName, auditOrgCode);
				// 借车部门
				var applyOrgCode = record.data.applyOrgCode;
				var applyOrgName = record.data.applyOrgName;
				findField('applyOrgCode').setCombValue(applyOrgName, applyOrgCode);
				//申请人
				var applyEmpCode = record.data.applyEmpCode;
				var applyEmpName = record.data.applyEmpName;
				findField('applyEmpCode').setCombValue(applyEmpName, applyEmpCode);
			},
			exception:function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.i18n('foss.scheduling.borrowvehicle.msg.message'), result.message);
			}
		});
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		//初始化所有组件
		me.initAllComponent();
		//初始化全局form
		var form = me.createOperateForm();
		form.add([me.carInfo, me.applyInfo, me.buttonArea]);
		//保存form到全局变量中
		scheduling.borrowVehicleApplyForm = form;
		me.add([form]);
	}
});

