/***********合大票清单修改调用js******************/
/***********全局变量******************/
var airWaybillNo='';
//查询_修改制作合大票清单
//显示机场市县名称机场		
Ext.define('Foss.commonSelector.AirportWithCityNameSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonairporwithcitynametselector',
//			fieldLabel : '机场',
			displayField : 'airportName',// 显示名称
			valueField : 'cityCode',// 值
			queryParam : 'airportEntity.queryDistrictParam',// 查询参数
			showContent : '{cityName}&nbsp;&nbsp;&nbsp;{countyName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.AirportStore');
				me.callParent([cfg]);
			}
		});	
//校验是否已保存合票清单
airfreight.airQueryModifyMakeFlightjointList.validateAirwaybillNoisNotExist = function(airwaybillNo){
	var airwaybillNoisNotExist = false;
	Ext.Ajax.request({
		async: false,
		url:airfreight.realPath('queryAirwaybillNoisNotExist.action'),
		params:params = {
				'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo': airwaybillNo
		},
		success:function(response){
			var result = Ext.decode(response.responseText);
			airPickupbillEntity = result.airTransPickupBillVo.airPickupbillEntity;
			if(airPickupbillEntity!=null){
				airwaybillNoisNotExist = true;
			}
		}
	});
	return airwaybillNoisNotExist;
	
}

//查询model
Ext.define('Foss.airfreight.makePickGoodsList.modifyPickGoodsQueryModel',{
	extend:'Ext.data.Model',
	fields: [
	      {name:'airTransferPickupbillNo',type:'string'},
	      {name:'arrvRegionName',type:'string'},
	      {name:'destOrgName',type:'string'},
	      {name:'transferFlightNo',type:'string'},
	      {name:'flightDate',type:'date',convert:dateConvert},
	      {name:'airLineName',type:'string'},
	      {name:'airWaybillNo',type:'string'},
	      {name:'waybillNo',type:'string'},
	      {name:'status', type:'string'}
	]
});
//收集中转提货清单保存数据
Ext.define('Foss.airfreight.makePickGoodsList.modifyAirTranDataCollectionEntity',{
	extend:'Ext.data.Model',
	fields: [
	      {name:'ids',type:'string'},
	      {name:'airTransferPickupbillNo',type:'string'},
	      {name:'arrvRegionName',type:'string'},
	      {name:'destOrgCode',type:'string'},
	      {name:'destOrgName',type:'string'},
	      {name:'transferFlightNo',type:'string'},
	      {name:'transferDate',type:'date'},
	      {name:'billNoTotal',type:'number'},
	      {name:'goodsQtyTotal',type:'number'},//开单件数
	      {name:'airPickQtyTotal',type:'number'},//清单件数
	      {name:'grossWeightTotal',type:'number'},
	      {name:'deliverFeeTotal',type:'number'},
	      {name:'arrivalFeeTotal',type:'number'},
	      {name:'collectionFeeTotal',type:'number'},
	      {name:'createUserName',type:'string'},
	      {name:'createTime',type:'date'},
	      {name:'airWaybillId',type:'string'},
	      {name:'airWaybillNo',type:'string'},
	      {name:'airPickupbillId',type:'string'},
	      {name:'notes',type:'string'},
	      {name:'status',type:'string'}
	]
});

//269701--lln--2016/04/16--begin
/**
 * 流水号的Model
 */
Ext.define('Foss.airfreight.makePickGoodsList.SerialNoModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'serialNo'
	}]
});

//流水明细Store
Ext.define('Foss.airfreight.makePickGoodsList.SerialNoStore',{
	extend: 'Ext.data.Store',
	model : 'Foss.airfreight.makePickGoodsList.SerialNoModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : airfreight.realPath('findAllSerial.action'),
		reader : {
			type : 'json',
			root : 'serialList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 选中流水列表中的一行或多行移动至另一个流水列表中
 * @param grid 选中的列表
 * @param addStore 增加记录的store
 * @param removeStore 删除记录的store
 * @name select
 */
airfreight.airQueryModifyMakeFlightjointList.select = function(grid,addStore,removeStroe) {
	var _records = grid.getSelectionModel().getSelection();
	if (_records == null||_records.length<=0) {
		return false;
	}
	Ext.each(_records, function(item) {
		addStore.insert(0,item);
	});
	removeStroe.remove(_records);
	return true;
};
/**
 * 流水列表中的所有记录移动至另一个流水列表中
 * @param grid 选中的列表
 * @param addStore 增加记录的store
 * @param removeStore 删除记录的store
 * @name allSelect
 */
airfreight.airQueryModifyMakeFlightjointList.allSelect = function(grid,addStore,removeStore) {
	var count = removeStore.getCount();
	if(count==null||count<=0){
		return false;
	}
	removeStore.each( function(_record) {
		addStore.insert(0,_record);
	},removeStore);
	// 从待选运单列表中移除该记录
	addStore.each( function(_record) {
		removeStore.remove(_record);
	},addStore);
	return true;
};

/**
 * 判断清单件数是否小于0
 * @param chooceStore 已选流水列表store
 * @param wayBillForm 运单信息表单
 * @name validateAirPick
 */
airfreight.airQueryModifyMakeFlightjointList.validateAirPick = function(wayBillForm,chooceStore) {
	var count = chooceStore.getCount();
	if(count==null||count<=0){
		Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
			'清单件数必须大于1', 'error');
		
	}
	//修改运单信息的清单件数
		wayBillForm.findField('airPickQty').setValue(count);
		return;
};
//**********************************运单信息表单*********************
//修改清单信息form表单
Ext.define('Foss.airfreight.makePickGoodsList.updateWayBillForm',{
	extend : 'Ext.form.Panel',
 	frame: true,
 	border: false,
 	layout: 'column',
 	defaults: {
 		margin: '5 5 5 5',
 		columnWidth:.49,
 		xtype: 'textfield',
 		allowBlank:false,
 		labelWidth:80
 	},
 	items:[{
 		name: 'id',
 		hidden:true
 	},{
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.waybillNo'),
 		name: 'waybillNo',
 		margin: '0 5 5 5',
 		readOnly:true
 	},{
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.weight') ,
 		name: 'weight',
 		allowBlank:true,
 		disabled:true
 	},{
 		xtype: 'numberfield',
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.billingWeight') ,
 		name: 'billingWeight',
 		minValue: 0,
 	    hideTrigger: true,  
 	    keyNavEnabled: true,  
 	    mouseWheelEnabled: true,
			maxLength : 8,
 		maxLengthText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.jfzlcgzdxz') 
 	},{
 		xtype: 'numberfield',
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.deliverFee'),
 		name: 'deliverFee',
 		readOnly:false,
 		minValue: 0,
 	    hideTrigger: true,  
 	    keyNavEnabled: true,  
 	    mouseWheelEnabled: true,
		maxLength : 8,
 		maxLengthText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.shfcgzdxz')  
 	},{//清单件数--随着流水号件数变更
 		xtype: 'numberfield',
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airPickQty'),
 		name: 'airPickQty',
 		minValue: 1,
 		minText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.goodsQtyError'),
 	    hideTrigger: true,  
 	    keyNavEnabled: true,  
 	    mouseWheelEnabled: true,
 	    allowDecimals: false, 
		maxLength : 9,
		readOnly:true,
 		maxLengthText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.jscgzdxz')
 	},{
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.receiverName'),
 		name: 'receiverName',
 	    maxLength : 100,
 		maxLengthText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.shrcdycgzdxz')
 	},{
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.phone'),
 		name: 'receiverContactPhone',
 	    maxLength : 100,
 		maxLengthText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.dhcdycgzdxz')
 	},{
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.address'),
 		name: 'receiverAddress',
 	    maxLength : 500,
 		maxLengthText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.dzcdycgzdxz')
 	},{
 		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.notes'),
 		name: 'notes',
 		columnWidth:.98,
 		allowBlank:true,
 	    maxLength : 500,
 		maxLengthText:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.bzcgzdxz')
 	}]
 });

//**********************************未选流水号列表*********************
/**
 * 未选流水号grid
 */
Ext.define('Foss.airfreight.makePickGoodsList.LeftSerialGrid', {
	extend : 'Ext.grid.Panel',
	title: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.makePickGoodsList.serialContainer.oldSerialNoGrid.title'),//'未选明细',
	//flex: 0.45,
	autoScroll: true,
	columnLines: true,
	cls : 'tworowbbargirdpanel',
	animCollapse : true,
	frame:true,
	height:250,
	emptyText : '查询结果为空'/*'查询结果为空'*/,
	// 列模板
	columns : [{
		text:'流水号',
		//flex: 1,
		dataIndex : 'serialNo'
	},{
		text:'流水号id',
		hidden:true,
		dataIndex : 'id'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Foss.airfreight.makePickGoodsList.SerialNoStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : airfreight.realPath('findLeftSerialForModify.action'),
				reader : {
					type : 'json',
					root : 'airTransPickupBillVo.serialList'
				}
			}
		});
		me.callParent([cfg]);
	}
});


//**********************************移动按钮列表*********************
/**
 * 移动按钮
 */
Ext.define('Foss.airfreight.makePickGoodsList.SerialButtonPanel', {   
	extend : 'Ext.panel.Panel',
	height:270,
	buttonAlign : 'center',
	defaults: {
		margin : '10 0 0 0',
		minWidth: 63,
		xtype: 'button'
	},
	items : [{
		text : '&gt;&gt;',//全部右移
		handler :  function() {
			var window = this.up('window'),
				//未选流水列表
				leftSerialGrid = window.getLeftSerialGrid(),
				//已选流水列表
				rightSerialGrid = window.getRightSerialGrid(),
				//运单信息表单
				wayBillForm = window.getWayBillForm().getForm();
			var noChoose_store = leftSerialGrid.getStore();
			//将左边待选的流水为空或者供选择的数据数目小于或者等于零
			if(noChoose_store.getCount()==null || noChoose_store.getCount()<=0){
				Ext.ux.Toast.msg('提示','已经没有流水记录', 'error');
				return;
			}
			//将左边待选的流水号构成一个数组
			var noChooseSerial = new Array();
			noChoose_store.each( function(serial) {
				noChooseSerial.push(serial.get('serialNo'));
    		},noChoose_store);
			//将右边已选的流水号构成一个数组
    		var chooseSerial = new Array();
			//获取已选流水store
    		choose_store = rightSerialGrid.getStore();
    		choose_store.each( function(serial) {
    			chooseSerial.push(serial.get('serialNo'));
    		},choose_store);
			
    		//传递的参数值,如果右边已选的角色为空，将chooseRoles赋给secondRoleCodes，检查全选中的角色是否有冲突
			airfreight.airQueryModifyMakeFlightjointList.allSelect(leftSerialGrid,choose_store,noChoose_store);
			//修改运单信息的清单件数
			airfreight.airQueryModifyMakeFlightjointList.validateAirPick(wayBillForm,choose_store);
			
		}
	}, {
		text : '&nbsp;&gt;&nbsp;',//单条右移
		handler : function() {
			var window = this.up('window'),
				//未选流水列表
				leftSerialGrid = window.getLeftSerialGrid(),
				//已选流水列表
				rightSerialGrid = window.getRightSerialGrid(),
				//运单信息表单
				wayBillForm = window.getWayBillForm().getForm();
				
			var _records = leftSerialGrid.getSelectionModel().getSelection();
			if (_records == null||_records.length<=0) {
				Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
					'请选择流水号', 'error');
				return false;
			}
			//将左边已选的流水号构成一个数组
			var lchoose = new Array();
			Ext.each(_records, function(item) {
				lchoose.push(item.get('serialNo'));
			});
			//将右边已选的流水号构成一个数组
    		var chooseSerial = new Array();
    		choose_store = rightSerialGrid.getStore();
    		choose_store.each( function(serialNo) {
    			chooseSerial.push(serialNo.get('serialNo'));
    		},choose_store);
    		//如果右边已选的流水号为空，并且左边选择为单个
    		if(chooseSerial.length<1 && lchoose.length==1){
    			airfreight.airQueryModifyMakeFlightjointList.select(leftSerialGrid,choose_store,leftSerialGrid.getStore());
				//修改运单信息的清单件数
				airfreight.airQueryModifyMakeFlightjointList.validateAirPick(wayBillForm,choose_store);
			}else{
    			//如果右边已选的流水号为空，并且左边选择为多个，就检查右边已选的流水号的是否有冲突
    			if(chooseSerial.length<1 && lchoose.length > 1){
    				chooseSerial = lchoose;
    			}
				
				airfreight.airQueryModifyMakeFlightjointList.select(leftSerialGrid,choose_store,leftSerialGrid.getStore());
				//修改运单信息的清单件数
				airfreight.airQueryModifyMakeFlightjointList.validateAirPick(wayBillForm,choose_store);
    		}
		}
	}, {
		text : '&nbsp;&lt;&nbsp;',//单条左移
		handler : function() {
			var window = this.up('window'),
				//未选流水列表
				leftSerialGrid = window.getLeftSerialGrid(),
				//已选流水列表
				rightSerialGrid = window.getRightSerialGrid(),
				//运单信息表单
				wayBillForm = window.getWayBillForm().getForm();
			if(!airfreight.airQueryModifyMakeFlightjointList.select(rightSerialGrid,leftSerialGrid.getStore(),rightSerialGrid.getStore())){
				Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
						'请选择流水号', 'error');
			}
				//修改运单信息的清单件数
				airfreight.airQueryModifyMakeFlightjointList.validateAirPick(wayBillForm,rightSerialGrid.getStore());
		}
	}, {
		text : '&lt;&lt;',//全部左移
		handler : function() {
			var window = this.up('window'),
				//未选流水列表
				leftSerialGrid = window.getLeftSerialGrid(),
				//已选流水列表
				rightSerialGrid = window.getRightSerialGrid(),
				//运单信息表单
				wayBillForm = window.getWayBillForm().getForm();
			if(!airfreight.airQueryModifyMakeFlightjointList.allSelect(rightSerialGrid,leftSerialGrid.getStore(),rightSerialGrid.getStore())){
				Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
						'已经没有流水记录', 'error'); 
			}
					//修改运单信息的清单件数
			airfreight.airQueryModifyMakeFlightjointList.validateAirPick(wayBillForm,rightSerialGrid.getStore());
		}
	}]
});
//**********************************已选流水号列表*********************
/**
 * 已选流水号grid
 */
Ext.define('Foss.airfreight.makePickGoodsList.RightSerialGrid', {
	extend : 'Ext.grid.Panel',
	title: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.makePickGoodsList.serialContainer.newSerialNoGrid.title'),//'已选明细',
	//flex: 0.45,
	autoScroll: true,
	columnLines: true,
	cls : 'tworowbbargirdpanel',
	animCollapse : true,
	frame:true,
	height:250,
	emptyText : '查询结果为空'/*'查询结果为空'*/,
	// 列模板
	columns : [{
		text:'流水号',
		//flex: 1,
		dataIndex : 'serialNo'
	},{
		text:'流水号ID',
		hidden:true,
		dataIndex : 'id'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Foss.airfreight.makePickGoodsList.SerialNoStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : airfreight.realPath('findRightSerial.action'),
				reader : {
					type : 'json',
					root : 'airTransPickupBillVo.serialList'
				}
			}
		});
		me.callParent([cfg]);
	}
});

//**********************************确认按钮*********************
/**
 * 修改确定按钮以及取消按钮
 */
Ext.define('Foss.airfreight.makePickGoodsList.updateWayBillButton',{
	extend : 'Ext.container.Container',
	defaultType : 'button',
	name : 'buttonArea',
	defaults : {
		margin : '2 7 2 7'
	},
	layout : 'column',
	items : [{
		xtype:'container',
		columnWidth:.68
	},
			{
		xtype:'button',
		text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.ensure'),
		columnWidth:.15,
		handler:function(){
				var window = this.up('window'),
				//已选流水列表
				rightSerialGrid = window.getRightSerialGrid(),
				//运单信息表单
				wayBillForm = window.getWayBillForm().getForm();
			//校验重量
			if(wayBillForm.findField('billingWeight').value <= 0){
				Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
						,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.billingWeightError'));
				return false; 
			}
			//校验必须录入项是否录入
			if(!wayBillForm.isValid()){
				Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
						,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.validError'));
				return false; 
			}
			//获取表单
			var formValue = wayBillForm;
			//得到表单record对象
			var wayBillRecord = formValue.getRecord();
			//主键id
			var record = wayBillRecord.data;
			//将修改的数据update到record对象中
			formValue.updateRecord(wayBillRecord);
			//将选中的流水号构成一个数组
        	var serialNoList = new Array();
        	rightSerialGrid.getStore().each( function(serialNo) {
        			serialNoList.push(serialNo.get('serialNo'));
        	},rightSerialGrid.getStore());
			
			//更新已制作流水
			rightSerialGrid.store.loadData(rightSerialGrid.getStore().data);
			airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.update(formValue.getRecord());
			if(formValue.getRecord().data.waybillNo.substring(0,1)!='B'){
				//将list数据转成数组 以json形式传给后台
				var serialEntityList =new Array();
				for(var i=0;i<serialNoList.length;i++){
					var serialEntity = {'serialNo':serialNoList[i],'waybillNo':formValue.getRecord().data.waybillNo,'airPickUpDetialId':formValue.getRecord().data.id}
					serialEntityList.push(serialEntity);
				}
				//传递的参数值
				var array = {airTransPickupBillVo : {serialList :serialEntityList}};
	
				Ext.Ajax.request({
					url : airfreight.realPath('saveSerialNo.action'),
					jsonData:array,
					success : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', '保存成功');
						window.hide();
						},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg('提示', json.message, 'error');
							}
					});
			}
			window.close();
		}
	},{
		xtype:'button',
		text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.cancel'),
		columnWidth:.15,
		handler:function(){
			this.up('window').close();
		}
	}]
 });

//269701--lln--2016/04/16--end


Ext.define('Foss.airfreight.makePickGoodsList.modifyPickGoodsForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	defaults: {
		margin:'5 5 5 5'
	},
	callEdi:function(ids,airWaybillNo,callIsNotEdiFlag,flightNo){
		var flagSuccess = true;
	    if(!Ext.fly('downloadAttachFileForm')){
		    var frm = document.createElement('form');
		    frm.id = 'downloadAttachFileForm';
		    frm.style.display = 'none';
		    document.body.appendChild(frm);
       	}
		Ext.Ajax.request({
			url:airfreight.realPath('uploadPickupCallEdi.action'),
			form: Ext.fly('downloadAttachFileForm'),
			method : 'POST',
			params : {'airTransPickupBillVo.ids':ids,
				      'airTransPickupBillVo.airWaybillNo': airWaybillNo,
				      'airTransPickupBillVo.callIsNotEdiFlag': callIsNotEdiFlag,
				      'airTransPickupBillVo.fightNo': flightNo
			},
			isUpload: true,
			success:function(response){
				var result = Ext.decode(response.responseText);
				if(result.message!=null){
					flagSuccess = false;
					Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
				}
			}
		})
		return flagSuccess;
	},
	items:[
	       //导出合大票清单
		Ext.create('Ext.toolbar.Toolbar', {
				   xtype:'toolbar',
				   dock:'right',
				   layout:'column',
				   defaultType:'button',
				   width:1024,
				   items:[{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.84
				   },{
					   text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.print'),
					   disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/printPickupBillButton'),
					   hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/printPickupBillButton'),
					   columnWidth:.06,
					   handler: function(){
					   		var airPickupbillId = airfreight.airQueryModifyMakeFlightjointList.airPickupbillEntity.id;
					   		do_printpreview('airPickupbill',
								{
									'airPickupbillId':airPickupbillId
								}, ContextPath.TFR_PARTIALLINE
							);
					   }
				   },{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.02
				   },{
					   text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.export'),
					   disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/exportUploadPickupCallEdiButton'),
					   hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/exportUploadPickupCallEdiButton'),
					   columnWidth:.06,
					   handler: function(){
						    var record = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.getSelectionModel().getSelection();
						    if(record.length==0){
						    	Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
						    			, airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.pleaseSelectRequireJointTicketDetail'));
						    	return false;
						    }
						    var ids = new Array();
						    for(var i = 0; i<record.length;i++){
						    	var id = record[i].data.id;
						    	ids.push(id);
						    }
						    var airWaybillNo = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().findField('airWaybillNo').getValue();
						    var flightNo = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().findField('flightNo').getValue();
							Ext.MessageBox.buttonText.yes = airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.yes');  
							Ext.MessageBox.buttonText.no = airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.no'); 
							Ext.Msg.confirm(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.sendEdi'), function(btn,text){
								if(btn == 'yes'){
									//发送则调用EDI系统
									var callIsNotEdiFlag = 'YES';
									var flag = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.callEdi(ids,airWaybillNo,callIsNotEdiFlag,flightNo);
									if(flag){
										Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
												,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.callEdiSuccess'));
										return false;
									}
								}else if(btn == 'no'){
									var callIsNotEdiFlag = 'NO';
									var flag = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.callEdi(ids,airWaybillNo,callIsNotEdiFlag,flightNo);
									if(flag){
										Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
												,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.exportSuccess'));
										return false;
									}
								}
							})
					   }
				   }]
			}),
		//查询条件
		Ext.create('Ext.form.FieldSet',{
		    height:55,
		    width:590,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
    			xtype:'commonairlinesselector',
    			displayField : 'code',// 显示名称
    			valueField : 'code',// 值 
    			fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airLineTwoletter'),
    			name: 'airLineTwoletter',
    			readOnly:true,
    			columnWidth:.45
			},{
				fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airWaybillNo'),
				name:'airWaybillNo',
				readOnly:true,
				columnWidth:.45
			},{
				name:'status',
				hidden:true
			}]
		}),
		//运单号添加
		Ext.create('Ext.form.FieldSet',{
		    height:55,
		    width:440,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.waybillNo'),
				name:'waybillNo',
				columnWidth:.65
			},{
				xtype:'button',
				text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.add'),
				disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/queryToAddAirPickupbillDetailButton'),
				hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/queryToAddAirPickupbillDetailButton'),
				columnWidth:.15,
				handler:function(){
					//获取运单号
					var waybillNo = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().getValues()['waybillNo'];
					if(Ext.isEmpty(waybillNo)){
						Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
								,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.waybillNotNull'));
						return null;
					}
					var pickGoodsstoreLength =  airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.data.items.length;
					var pickGoodsResult = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.data.items;
					for(var i=0;i<pickGoodsstoreLength;i++){
						if(waybillNo==pickGoodsResult[i].data.waybillNo){
							Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
									,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.inventoryDetailExist')+waybillNo+airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.nonRepeatableRead'));
							return null;
						}
					}
					var params= {
	    					'airTransPickupBillVo.airTransPickupBillDto.waybillNo': waybillNo
	    			};
					Ext.Ajax.request({
						url:airfreight.realPath('addAirPickupbillDetailInfo.action'),
						params: params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							//获取合票清单明细
							var waybillDetailList = result.airTransPickupBillVo.resultAirWaybillDetailList;
							if(!Ext.isEmpty(waybillDetailList)){
								 var transportType=waybillDetailList[0].transportType;
								var length=airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.data.length;
								var items=airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.data.items;
								if(length>0){
									for(var i=0;i<length;i++){
									  if(items[i].data.transportType!=transportType){
									  	 Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), '【'+waybillNo+'】 与 【'+items[i].data.waybillNo+'】 运输类型不一致,不能添加!');
									  	 return;
									  }
									}
								}
								 airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.add(waybillDetailList);
								 Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
										 airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.addSuccess'));
								return  null;
							}else{
								Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
										, airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.addWaybillFail'));
								return  null;
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
							return false;
						}
					});
				}
			}]
		}),
		//提货清单信息
		Ext.create('Ext.form.FieldSet',{
		    height:60,
		    width:1040,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				xtype:'commonairagencydeptselector',
				fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.dedtOrgName'),
				name:'destOrgCode',
				displayField : 'agentDeptName',// 显示名称
				valueField : 'agentDeptCode',// 值
				allowBlank: false,
				columnWidth:.25
			},{
				fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.flightNo'),
				name:'flightNo',
				disabled:true,
				allowBlank: false,
				columnWidth:.25
			},{
				fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.arrvRegionName'),
				name:'arrvRegionName',
				allowBlank: false,
				disabled:true,
				columnWidth:.25
			},{
				xtype: 'datefield',
		        fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.flightNoDate'),
		        name: 'flightDate',
		        format: 'Y-m-d',
		        allowBlank: false,
		        columnWidth:.25
			}]
		}),
		//运单号添加、查询条件
		Ext.create('Ext.container.Container',{
			width : 1080,
			layout : 'column',
			defaults: {
				width:65,
				columnWidth:.08
			},
      		items:[{
      			xtype:'button',
      			text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.delete'),
      			handler:function(){
      				var record = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.getSelectionModel().getSelection();
      				if(record.length!=0){
      					Ext.Msg.confirm(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
      						airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.isorisntDel'), function(btn,text){
      						if(btn == 'yes'){
      	      					//将选择的record对象从列表中移除
      	      					airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.remove(record);
      						}else{
      							return false;
      						}
      					})
      				}else{
      					Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
      							, airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.pleaseSelectRequireDelInventory'));
      				}
      			}
      		},{
      			xtype:'button',
      			text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.transfer'),
      			
      			handler:function(){
      				var record = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.getSelectionModel().getSelection();
      				if(record.length==0){
      					Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.pleaseRequirFlagInventory'));
      					return null;
      				}
      			

      				//获取当前record对象
							var modifyArrivalFeeMap = airfreight.airQueryModifyMakeFlightjointList.modifyArrivalFeeMap;
  					//将选择的record对象从列表中移除
  					for(var i=0;i<record.length;i++){
  						var recordValue = record[i].data;
  						//BUG-50874,空运运单已签收，还可以修改合大票清单和制作中转提货清单
  						//desc: 标记中转时判断是否已经签收,如果已经签收则不允许修改
      			        // wqh  2013-08-13
  							var params= {
	    					'airTransPickupBillVo.airTransPickupBillDto.waybillNo': record[i].data.waybillNo
	    			      };
  							Ext.Ajax.request({
								async: false,
								url:airfreight.realPath('checkIfCanModify.action'),
								params:params,
								success:function(response){
							      //什么也不做
						 if(record[i].data.beTransfer=='Y'){
      				
  							record[i].data.beTransfer='N';	
  							var arrivalFee = modifyArrivalFeeMap.get(recordValue.id);
  							var collectionFee = modifyArrivalFeeMap.get(recordValue.id+"collectionFee");
  							if(arrivalFee){
  								recordValue.arrivalFee = arrivalFee;
  							}else{
  								recordValue.arrivalFee = 0;
  							}
  							if(arrivalFee){
  								recordValue.collectionFee = collectionFee;
  							}else{
  								recordValue.collectionFee = 0;
  							}
  						}else {
  							record[i].data.beTransfer='Y';
  							modifyArrivalFeeMap.add(recordValue.id,recordValue.arrivalFee);
  							modifyArrivalFeeMap.add(recordValue.id+"collectionFee",recordValue.collectionFee);
  							recordValue.arrivalFee = 0;
  							recordValue.collectionFee = 0;
  						}
  						airfreight.airQueryModifyMakeFlightjointList.modifyArrivalFeeMap = modifyArrivalFeeMap;
  						  					airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.update(record);
  					   Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.flagSuccess'));
		
								},
								exception:function(response){
									var result = Ext.decode(response.responseText);
									Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
									return false;
								}
							});
  					 
  					}
				
  					
      			}
      		}]
		})
	]
});
//合大票清单model
Ext.define('Foss.airfreight.makePickGoodsList.modifyPickGoodsResultModel',{
	extend:'Ext.data.Model',
	fields:[
	        {name:'id',type:'string'},
	        {name:'airPickupbillId',type:'string'},
	        {name:'waybillNo',type:'string'},
	        {name:'beTransfer',type:'string',defaultValue:'N'},
	        {name:'arrvRegionName',type:'string'},
	        {name:'weight',type:'number'},
	        {name:'billingWeight',type:'number'},
	        {name:'volume',type:'number'},
	        {name:'goodsQty',type:'number'},
	        {name:'airPickQty',type:'number'},
	        {name:'goodsName',type:'string'},
	        {name:'createTime',type:'date'},
	        {name:'deliverFee',type:'number'},
	        {name:'arrivalFee',type:'number'},
	        {name:'receiverContactPhone',type:'string'},
	        {name:'pickupType',type:'string'},
	        {name:'collectionFee',type:'number'},
		    {name:'receiverAddress',type:'string'},
		    {name:'receiverName',type:'string'},
		    {name:'notes',type:'string'},
		    {name:'transportType',type:'string'}

	 ]
});
//合大票清单store
Ext.define('Foss.airfreight.makePickGoodsList.modifyPickGoodsResultStore',{
	extend: 'Ext.data.Store',
	model:'Foss.airfreight.makePickGoodsList.modifyPickGoodsResultModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	},
	listeners: {
		datachanged: function(store, operation, eOpts){
			var totalArray = store.data.items;
			//票数
			var billNoTotal = totalArray.length;
			//运单开单总件数
			var goodsQtyTotal = 0;
			//清单总件数
			var airPickQtyTotal = 0;
			//毛重
			var grossWeightTotal = 0;
			//公斤送货费
			var deliverFeeTotal = 0;
			//到付费
			var arrivalFeeTotal = 0;
			//代收款
			var collectionFeeTotal = 0;
			
			for(var i=0;i<totalArray.length;i++){
				//计算运单件数
				goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty,0);
				//计算清单件数
				airPickQtyTotal = airPickQtyTotal + Ext.Number.from(totalArray[i].data.airPickQty,0);
				//计算毛重
				grossWeightTotal = grossWeightTotal + Ext.Number.from(totalArray[i].data.weight,0);
				//计算公斤送货费
				deliverFeeTotal = deliverFeeTotal + Ext.Number.from(totalArray[i].data.deliverFee,0);
				//计算到付费
				arrivalFeeTotal = arrivalFeeTotal + Ext.Number.from(totalArray[i].data.arrivalFee,0);
				//计算代收费
				collectionFeeTotal = collectionFeeTotal + Ext.Number.from(totalArray[i].data.collectionFee,0);
			}
			var count = 0;
			var toolbarArray = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.down('toolbar').query('textfield');
			for(var j=0;j<toolbarArray.length;j++){
				if(count==0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count==1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count==2){
					toolbarArray[j].setValue(airPickQtyTotal);
				}else if(count==3){
					toolbarArray[j].setValue(grossWeightTotal + '  ' + airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.kilo'));
					toolbarArray[j].hideValue = grossWeightTotal;
				}else if(count==4){
					toolbarArray[j].setValue(deliverFeeTotal);
				}else if(count==5){
					toolbarArray[j].setValue(arrivalFeeTotal);
				}else{
					toolbarArray[j].setValue(collectionFeeTotal);
				}
				count ++;
			}
		}
	}
});

//运单明细列表
Ext.define('Foss.airfreight.makePickGoodsList.modifyPickGoodsResult',{
	extend:'Ext.grid.Panel',
	title:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.inventoryDetail'),
	frame:true,
	border:true,
	width:1035,
	layout:'column',
	emptyText: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.searchResultInexistence'),
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			handler: function(grid, rowIndex, colIndex){
				//获取当前record对象
				var record = grid.getStore().getAt(rowIndex);
				var detailId = record.data.id;
				//如果新增加的明细，不允许修改
				if("N/A" == detailId){
						Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
								,airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.notSavedDetail'));
						return;		
				}
				var params= {
    					'airTransPickupBillVo.airTransPickupBillDto.waybillNo': record.data.waybillNo
    			};
				Ext.Ajax.request({
					async: false,
					url:airfreight.realPath('checkIfCanModify.action'),
					params:params,
					success:function(response){
						//269701--2016/04/24--begin
						//定义运单修改窗口
					var wayBillEditWindow = airfreight.airQueryModifyMakeFlightjointList.wayBillEditWindow,
						//grid = this.up('grid'),
						selection = grid.getSelectionModel().getSelection(),
						//运单信息表单
						wayBillForm = wayBillEditWindow.getWayBillForm(),
						//未选择流水信息
						leftSerialGrid = wayBillEditWindow.getLeftSerialGrid(),
						//已选择流水信息
						rightSerialGrid = wayBillEditWindow.getRightSerialGrid();
						wayBillForm.loadRecord(record);
						//未选择流水数据加载
						rightSerialGrid.getStore().load({
							params : {
								'airTransPickupBillVo.airTransPickupBillDto.waybillNo': record.data.waybillNo,
								'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo': airWaybillNo//正单号
								//serialNo:record.get('serialNo')	            				
								}
						});
						//已选择数据加载
						leftSerialGrid.getStore().load({
							params : {
								'airTransPickupBillVo.airTransPickupBillDto.waybillNo': record.data.waybillNo,
								'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo': airWaybillNo//正单号            				
							}
						});
						wayBillEditWindow.show();
						//269701--2016/04/24--begin
						//window显示
						//airfreight.airQueryModifyMakeFlightjointList.updateWindowPickGoodsList =Ext.create('Foss.airfreight.makePickGoodsList.updateWindowPickGoodsList').show();
						//将grid列中的数据绑定到window中的form中
						//airfreight.airQueryModifyMakeFlightjointList.updateBingdingPickGoodsList.loadRecord(record);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
						return false;
					}
				});
				var formValue = airfreight.airQueryModifyMakeFlightjointList.wayBillEditWindow.getWayBillForm();
        		for(var i=0;i<formValue.items.length;i++){
        			if(formValue.items.items[i].name == 'deliverFee'){
        				if(formValue.getForm().findField('waybillNo').getValue().substring(0,1)=='B'){
        					formValue.getForm().findField('deliverFee').readOnly = true;
		       			}else{
		       				formValue.getForm().findField('deliverFee').readOnly = false;
		       			}
        			}
        		}
			}
		}],
		text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.operate'),
	    width: 40,
	    dataIndex: 'id'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.waybillNo'),
		flex: 0.6,
		dataIndex: 'waybillNo'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.arrvRegionName'),
		flex: 0.6,
		dataIndex: 'arrvRegionName'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airChangeInventory.goodsName'),
		flex: 0.6,
		dataIndex: 'goodsName'
	},{//269701--2016/04/21--begin
		//运单开单件数
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.poll'),
		flex: 0.6,
		dataIndex: 'goodsQty'
	},{
		//清单件数
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airPickQty'),
		flex: 0.6,
		dataIndex: 'airPickQty'
		//269701--2016/04/21--end
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.weightKilo'),
		flex: 0.6,
		dataIndex: 'weight'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.billingWeightKilo'),
		flex: 0.6,
		dataIndex: 'billingWeight'
	},{
		text:  airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.ifTransfer'),
		flex: 0.6,
		dataIndex: 'beTransfer',
		renderer :function(value){
			return value=='Y'?airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.yes'):airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.no');
		}
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.pickupType'),
		flex: 0.6,
		dataIndex: 'pickupType',
		renderer: function (value) {
                if (value == 'DELIVER_INGA') {
                  return  airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_INGA');
                } else if(value=='DELIVER_NOUP'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_NOUP');
                }else if(value=='DELIVER'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER');
                }else if(value=='DELIVER_UP'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_UP');
                }else if(value=='INNER_PICKUP'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.INNER_PICKUP');
                }else if(value=='SELF_PICKUP'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP');
                }else if(value=='LARGE_DELIVER_UP'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.LARGE_DELIVER_UP');
                }else if(value=='SELF_PICKUP_FREE_AIR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP_FREE_AIR');
                }else if(value=='DELIVER_INGA_AIR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_INGA_AIR');
                }else if(value=='DELIVER_UP_AIR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_UP_AIR');
                }else if(value=='DELIVER_AIR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_AIR');
                }else if(value=='AIRPORT_PICKUP'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.AIRPORT_PICKUP');
                }else if(value=='DELIVER_NOUP_AIR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_NOUP_AIR');
                }else if(value=='SELF_PICKUP_AIR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP_AIR');
                }else if(value=='PICKUP_TO_DOOR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.PICKUP_TO_DOOR');
                }
            }
		
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.deliverFee'),
		flex: 0.6,
		dataIndex: 'deliverFee'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.arrivalFee'),
		flex: 0.6,
		dataIndex: 'arrivalFee'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.collectionFee'),
		flex: 0.6,
		dataIndex: 'collectionFee'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.notes'),
		flex: 0.6,
		dataIndex: 'notes'
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.ticket'),
			   columnWidth:.10,
			   dataIndex: 'billNoTotal'
		   },{//269701--2016/04/20--begin
			   //运单总件数
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.poll'),
			   columnWidth:.12,
			   labelWidth:80,
			   dataIndex: 'goodsQtyTotal'
		   },{
			   //清单总件数
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airPickQty'),
			   columnWidth:.12,
			   labelWidth:80,
			   dataIndex: 'airPickQtyTotal'
			 //269701--2016/04/20--end
		   },{
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.weight'),
			   columnWidth:.12,
			   hideValue:'',
			   dataIndex: 'grossWeightTotal'
		   },{
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.kiloDeliverFee'),
			   labelWidth:80,
			   columnWidth:.12,
			   dataIndex: 'deliverFeeTotal'
		   },{
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.arrivalFee'),
			   columnWidth:.12,
			   labelWidth:60,
			   dataIndex: 'arrivalFeeTotal'
		   },{
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.arrivalFee'),
			   columnWidth:.15,
			   labelWidth:60,
			   hidden:true,
			   dataIndex: 'collectionFeeTotal'
		   }]
	}],
		
	//修改运单信息窗口
	wayBillEditWindow : null,
	getWayBillEditWindow : function(){
		if(this.wayBillEditWindow==null){
			this.wayBillEditWindow = airfreight.airQueryModifyMakeFlightjointList.wayBillEditWindow;
		}
		return this.wayBillEditWindow;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.makePickGoodsList.modifyPickGoodsResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.callParent([cfg]);
	}
});
Ext.define('Foss.airfreight.makePickGoodsList.modifyToolbarMessage',{
	extend:'Ext.form.Panel',
	frame:false,
	border:false,
	width:1035,
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 margin:'10 0 0 0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:'',
			   columnWidth:.03
		   },{
			   xtype:'button',
			   text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.commit'),
			   disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifySaveDeleteAirPickupbillDetailsButton'),
			   hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifySaveDeleteAirPickupbillDetailsButton'),
			   plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			       seconds:3
			   }),
			   labelWidth:80,
			   columnWidth:.06,
			   handler:function(){
				   var record = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.data.items;
				   if(record.length==0){
					   Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
							   , airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.inventoryDetailNotData'));
					   return null;
				   }
				   var arrys = {};
				   var fromValues = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().getValues();
				   //获取目的站
				   //arrys['arrvRegionName'] = fromValues['arrvRegionName'];
				   //获取到达网点
				   arrys['destOrgCode'] = fromValues['destOrgCode'];
				   var destOrg = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().findField('destOrgCode');
				   var destOrgName = destOrg.rawValue;
				   if(destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true)){
				       destOrgName = destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true).get('agentDeptName');
				   }
				   //到达网点名称
				   arrys['destOrgName'] = destOrgName;
				   //获取中转航班号
				   //arrys['transferFlightNo'] = fromValues['flightNo'];
				   //获取中转日期
				   arrys['transferDate'] = fromValues['flightDate'];
				   //获取toolbar统计栏信息
				   var toolbars = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.down('toolbar').items.items;
				   for(var j=0;j<toolbars.length;j++){
					   if(toolbars[j].dataIndex=='grossWeightTotal'){
						   arrys[toolbars[j].dataIndex] = toolbars[j].hideValue;
					   }else{
						   arrys[toolbars[j].dataIndex] = toolbars[j].value;   
					   }
				   }
				   //获取制单人、制单时间
				   var getCreateMesages = airfreight.airQueryModifyMakeFlightjointList.modifyToolbarMessage.down('toolbar').items.items;
				   for(var m=0;m<getCreateMesages.length;m++){
					   arrys[getCreateMesages[m].dataIndex] = getCreateMesages[m].value;
				   }
				   var jsonArry = [];
				   var airPickupbillId = airfreight.airQueryModifyMakeFlightjointList.airPickupbillEntity.id;
				   var airWaybillNo = airfreight.airQueryModifyMakeFlightjointList.airPickupbillEntity.airWaybillNo;
				   arrys['airWaybillNo'] = airWaybillNo;
				   arrys['airPickupbillId'] = airPickupbillId;
				    arrys['status'] = 'Y';
	           	   for(var i=0;i<record.length;i++){
	           	       jsonArry.push(record[i].data);
	           	   }
	           	   var records = Ext.create('Foss.airfreight.makePickGoodsList.modifyAirTranDataCollectionEntity',arrys);
	           	   //请求后台保存制作中转提货清单信息
	           	   var jsons = {'airTransPickupBillVo':{
	           	   			'airTranDataCollectionEntity':records.data,
	           	   			'airPickupbillDetailList':jsonArry}};
	           	   Ext.Ajax.request({
		           		url:airfreight.realPath('modifySaveDeleteAirPickupbillDetails.action'),
		           		jsonData:jsons,
		           		timeout: 600000,
		        		success:function(response){
		        			var result = Ext.decode(response.responseText);
		        			Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
		        					airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.saveSuccess'));	
		        			airfreight.airQueryModifyMakeFlightjointList.deletePickupDetailList.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.addPickupDetailList.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.stlPickupDetailList.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.modifyNotesTempMaps.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.modifyNotesMaps.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.editAirwayBillWindow.close();
		        		},
		        		exception:function(response){
		        			var result = Ext.decode(response.responseText);
		        			Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
		        		}
	           	   });
			   }
		   },{
			   xtype:'displayfield',
			   value:'<span style="color:red;">    注意：提交后，代理即可查询数据，请慎重提交!</span>',
			   columnWidth:.50
		   },{
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.createUser'),
			   labelWidth:55,
			   columnWidth:.13,
			   dataIndex: 'createUserName'
		   },{
			   fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.createTime'),
			   labelWidth:75,
			   columnWidth:.22,
			   dataIndex: 'createTime'
		   },{
			   xtype:'button',
			   text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.precommit'),
			   disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifySaveDeleteAirPickupbillDetailsButton'),
			   hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifySaveDeleteAirPickupbillDetailsButton'),
			   plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
			       seconds:3
			   }),
			   labelWidth:80,
			   columnWidth:.06,
			   handler:function(){
				   var record = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.data.items;
				   if(record.length==0){
					   Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
							   , airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.inventoryDetailNotData'));
					   return null;
				   }
				   var arrys = {};
				   var fromValues = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().getValues();
				   //获取目的站
				   //arrys['arrvRegionName'] = fromValues['arrvRegionName'];
				   //获取到达网点
				   arrys['destOrgCode'] = fromValues['destOrgCode'];
				   var destOrg = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().findField('destOrgCode');
				   var destOrgName = destOrg.rawValue;
				   if(destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true)){
				       destOrgName = destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true).get('agentDeptName');
				   }
				   //到达网点名称
				   arrys['destOrgName'] = destOrgName;
				   //获取中转航班号
				   //arrys['transferFlightNo'] = fromValues['flightNo'];
				   //获取中转日期
				   arrys['transferDate'] = fromValues['flightDate'];
				   //获取toolbar统计栏信息
				   var toolbars = airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.down('toolbar').items.items;
				   for(var j=0;j<toolbars.length;j++){
					   if(toolbars[j].dataIndex=='grossWeightTotal'){
						   arrys[toolbars[j].dataIndex] = toolbars[j].hideValue;
					   }else{
						   arrys[toolbars[j].dataIndex] = toolbars[j].value;   
					   }
				   }
				   //获取制单人、制单时间
				   var getCreateMesages = airfreight.airQueryModifyMakeFlightjointList.modifyToolbarMessage.down('toolbar').items.items;
				   for(var m=0;m<getCreateMesages.length;m++){
					   arrys[getCreateMesages[m].dataIndex] = getCreateMesages[m].value;
				   }
				   var jsonArry = [];
				   var airPickupbillId = airfreight.airQueryModifyMakeFlightjointList.airPickupbillEntity.id;
				   var airWaybillNo = airfreight.airQueryModifyMakeFlightjointList.airPickupbillEntity.airWaybillNo;
				   arrys['airWaybillNo'] = airWaybillNo;
				   arrys['airPickupbillId'] = airPickupbillId;
				   //未提交
				   arrys['status'] = 'N';
	           	   for(var i=0;i<record.length;i++){
	           	       jsonArry.push(record[i].data);
	           	   }
	           	   var records = Ext.create('Foss.airfreight.makePickGoodsList.modifyAirTranDataCollectionEntity',arrys);
	           	   //请求后台保存制作中转提货清单信息
	           	   var jsons = {'airTransPickupBillVo':{
	           	   			'airTranDataCollectionEntity':records.data,
	           	   			'airPickupbillDetailList':jsonArry}};
	           	   Ext.Ajax.request({
		           		url:airfreight.realPath('modifySaveDeleteAirPickupbillDetails.action'),
		           		jsonData:jsons,
		           		timeout: 600000,
		        		success:function(response){
		        			var result = Ext.decode(response.responseText);
		        			Ext.ux.Toast.msg(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
		        					airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.saveSuccess'));	
		        			airfreight.airQueryModifyMakeFlightjointList.deletePickupDetailList.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.addPickupDetailList.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.stlPickupDetailList.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.modifyNotesTempMaps.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.modifyNotesMaps.clear();
		        			airfreight.airQueryModifyMakeFlightjointList.editAirwayBillWindow.close();
		        		},
		        		exception:function(response){
		        			var result = Ext.decode(response.responseText);
		        			Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
		        		}
	           	   });
			   }
		   }]
	}]
});

//修改清单信息window
Ext.define('Foss.airfreight.makePickGoodsList.updateWindowPickGoodsList',{
	extend: 'Ext.window.Window',
	title: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.modifyWaybill'),
	modal:true,
	closeAction:'hide',
	width: 580,
	height: 630,
	layout: 'auto',
	wayBillForm : null,//运单信息
    getWayBillForm : function(){
    	if(this.wayBillForm==null){
    		this.wayBillForm = Ext.create('Foss.airfreight.makePickGoodsList.updateWayBillForm');
    	}
    	return this.wayBillForm;
    },
	leftSerialGrid : null,//未选流水列表
	getLeftSerialGrid : function(){
		if(this.leftSerialGrid==null){
			this.leftSerialGrid = Ext.create('Foss.airfreight.makePickGoodsList.LeftSerialGrid',{
				columnWidth: .4
			});
		}
		return this.leftSerialGrid;
	},
	serialButtonPanel : null,//移动按钮
	getSerialButtonPanel : function(){
		if(this.serialButtonPanel==null){
			this.serialButtonPanel = Ext.create('Foss.airfreight.makePickGoodsList.SerialButtonPanel',{
				columnWidth: .18,
				style : 'padding:60px 10px 10px 10px;border:none'
			});
		}
		return this.serialButtonPanel;
	},
	rightSerialGrid : null,//已选流水列表
	getRightSerialGrid : function(){
		if(this.rightSerialGrid==null){
			this.rightSerialGrid = Ext.create('Foss.airfreight.makePickGoodsList.RightSerialGrid',{
				columnWidth: .4
			});
		}
		return this.rightSerialGrid;
	},
	wayBillPanel: null,//流水明细以及移动按钮panel
	getWayBillPanel : function(){
	   var me = this;
	   if(this.wayBillPanel==null){
		    this.wayBillPanel = Ext.create('Ext.panel.Panel',{
		    frame: true,
		    layout:'column',
		    items : [ me.getLeftSerialGrid(), me.getSerialButtonPanel(), me.getRightSerialGrid() ]
		    });
		    }
		    return this.wayBillPanel;
	    },
		
	updateWayBillButton: null,//确认取消按钮panel
	getUpdateWayBillButton : function(){
	   var me = this;
	   if(this.updateWayBillButton==null){
		    this.updateWayBillButton = Ext.create('Foss.airfreight.makePickGoodsList.updateWayBillButton');
	    }
		return this.updateWayBillButton;
	},
	 constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [ me.getWayBillForm(), me.getWayBillPanel(),me.getUpdateWayBillButton()];
			me.callParent([cfg]);
		}
});

//修改合大票清单
Ext.define('Foss.airfreight.editPickUpAirwayBillWindow',{
	extend:'Ext.window.Window',
	modal:true,
	closable:true,
	closeAction:'hide',
	width: 1100,
	layout: 'column',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
		//form表单
		var pickGoodsForm = Ext.create('Foss.airfreight.makePickGoodsList.modifyPickGoodsForm');
		//gird列表
		var pickGoodsResult = Ext.create('Foss.airfreight.makePickGoodsList.modifyPickGoodsResult');
		//window窗口
		var toolbarMessage = Ext.create('Foss.airfreight.makePickGoodsList.modifyToolbarMessage');
		//修改清单信息
		var pickGoodsQueryRecord = Ext.create('Foss.airfreight.makePickGoodsList.modifyPickGoodsQueryModel');
		//获取当前操作部门名次
		var currentDeptName = FossUserContext.getCurrentUserEmp().empName;
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm = pickGoodsForm;
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult = pickGoodsResult;
		airfreight.airQueryModifyMakeFlightjointList.modifyToolbarMessage = toolbarMessage;
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord = pickGoodsQueryRecord;
		me.add([pickGoodsForm,pickGoodsResult,toolbarMessage]);
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.loadRecord(airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord);
		var toolbarArray = airfreight.airQueryModifyMakeFlightjointList.modifyToolbarMessage.down('toolbar').query('textfield');
		toolbarArray[1].setValue(currentDeptName);
		toolbarArray[2].setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
		airfreight.airQueryModifyMakeFlightjointList.modifyToolbarMessage;
	}
});

//查询修改合大票
Ext.define('Foss.airfreight.maketfrPickGoodsList.modifyMaketfrPickGoods',{
	extend: 'Ext.form.Panel',
	frame:true,
	airWaybillNo:null,
	title:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.searchCondition'),
	layout:'column',
	defaults: {
        	xtype: 'textfield',
        	margin: ' 5 5 5 5'
	},
    setData : function (){
    	var reserForm = airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.getForm();
    	var formatStr = 'Y-m-d H:i:s';
    	var now = new Date();
    	var beginDate = new Date(now.getFullYear(),now.getMonth(),now.getDate() - 1, 23, 59, 59);
    	var beginTime = Ext.Date.format(beginDate, formatStr);
    	var endDate = new Date(now.getFullYear(),now.getMonth(),now.getDate(), 23, 59, 59);
    	var endTime = Ext.Date.format(endDate, formatStr);
    	var createDeptcode = airfreight.airQueryModifyMakeFlightjointList.createDeptCode;
    	var createDeptName = airfreight.airQueryModifyMakeFlightjointList.createDeptName;
    	//reserForm.findField('airLineTwoletter').setCombValue('CA','CA');
    	reserForm.findField('createOrgName').setCombValue(createDeptName,createDeptcode);
    	reserForm.findField('beginInTime').setValue(beginTime);
    	reserForm.findField('endInTime').setValue(endTime);
    },
    searchPickupDetail : function(airWaybillNo){
		var params= {
				'airTransPickupBillVo.airWaybillNo': airWaybillNo
		};
		Ext.Ajax.request({
			async: false,
			url:airfreight.realPath('queryPickupInventory.action'),
			params:params,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var airPickupbillEntity = result.airTransPickupBillVo.airPickupbillEntity;
				airfreight.airQueryModifyMakeFlightjointList.modifyPickGoods.store.add(airPickupbillEntity);
			}
		});
    },
    defaults:{
		margin:'5 5 5 8',
		xtype: 'textfield'
	},
	items:[{
		xtype: 'rangeDateField',
		fieldLabel: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.createTime'),
		fieldId: 'Foss_airfreight_editcreateTime_Id'+(Ext.Date.format(new Date(),'Y-m-d H:i:s')),
		dateType: 'datetimefield_date97',
		fromName: 'beginInTime',
		toName: 'endInTime',
		allowBlank: false,
		columnWidth: .66
	},{
		xtype:'commonairlinesselector',
		displayField : 'code',// 显示名称
		valueField : 'code',// 值 
		fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airLineTwoletter'),
		name: 'airLineTwoletter',
		columnWidth:.33
	},{
		fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airWaybillNo'),
		name:'airWaybillNo',
		columnWidth:.33
	},{
		xtype : 'commonairporwithcitynametselector',
		fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.arrvRegionName'),
		name:'arrvRegionCode',
		displayField : 'cityName',
		columnWidth:.33
	},{
		xtype:'commonairagencydeptselector',
		fieldLabel:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.dedtOrgName'),
		name:'deptOrgCode',
		columnWidth:.33
	},{
		xtype: 'dynamicorgcombselector',
		type:'ORG',
		doAirDispatch:'Y',
		fieldLabel:  airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.airMasterCalibration'),
		name: 'createOrgName',
		allowBlank: false,
		disabled:true,
		columnWidth:.33
	},{
		xtype: 'combobox',
		fieldLabel:  airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.commitStatus'),
        allowBlank: false,
        name: 'status',
        store: Ext.create('Ext.data.Store', {
            fields: ['valueName', 'valueCode'],
             data: [{'valueName': airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.all'), 'valueCode': ''},
                {'valueName': airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.notcommit'), 'valueCode': 'N'},
                {'valueName': airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.iscommit'), 'valueCode': 'Y'}
            ]
        }),
        queryMode: 'local',
        value: '',
        displayField: 'valueName',
        valueField: 'valueCode',
        editable: false,
        columnWidth: .33
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.reset'),
			columnWidth:.08,
			handler:function(){
					airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.getForm().reset();
					airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.setData();
				}
			},{
				xtype: 'container',
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.search'),
				disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/queryMakePickGoodsButton'),
				hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/queryMakePickGoodsButton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
	            	if(!airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.getForm().isValid()){
	            		Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
	            				, airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.includeNotInputItem'));
	            		return false;
	            	}
					var checkDateTimeSpan =  function(beginDate, endDate) {
						if(beginDate=='' || beginDate==null){
							Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
									, airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.createBillStartTime'));
							return false;
						}
						if(endDate=='' || endDate==null){
							Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
									, airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.createBillEndTime'));
							return false;
						}
						if(!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
							var begin = Ext.Date.parse(beginDate, "Y-m-d H:i:s", true);
							var end = Ext.Date.parse(endDate, "Y-m-d H:i:s", true);
							var pool = begin - end;
							var m = -86400000 * 31;
							if(pool < m) {
								Ext.MessageBox.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
										,  airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.createBillTimeNot31'));				
								return false;
							} 
						}
						return true;
					}
			    	var reserForm = airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.getForm();
			    	var beginDate = reserForm.findField('beginInTime').getValue();
			    	var endDate = reserForm.findField('endInTime').getValue();
			    	if(!checkDateTimeSpan(beginDate,endDate))
			    		return false;
					//查询合票明细
			    	airfreight.airQueryModifyMakeFlightjointList.pagingBar.moveFirst();
				}
		}]
	}]
});

//新增
Ext.define('Foss.airfreight.maketfrPickGoodsList.modifyContainer',{
	extend:'Ext.container.Container',
	width : 1080,
	layout : 'column',
	items: [{
			xtype: 'container',
			margin:'5 5 5 10',
			html: '&nbsp;'
		},{
			width:65,
			xtype:'button',
			text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.newAdd'),
			columnWidth:.08,
			handler:function(){
				addTab('T_airfreight-airMakeFlightjointList',//对应打开的目标页面js的onReady里定义的renderTo
						 airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.makeJointTicketInventory'),//打开的Tab页的标题
						airfreight.realPath('airMakeFlightjointList.action'));
			}
	}]
});
//合票明细model
Ext.define('Foss.airfreight.makePickGoodsList.modifyMakePickGoodsModel',{
	extend:'Ext.data.Model',
	fields: [
	  {name:'id',type:'string'},
	  {name:'airLineCode',type:'string'},
      {name:'airLineName',type:'string'},
      {name:'airWaybillNo',type:'string'},
      {name:'destOrgCode',type:'string'},
      {name:'destOrg_Name',type:'string'},
      {name:'flightNo',type:'string'},
      {name:'arrvRegionCode',type:'string'},
      {name:'arrvRegionName',type:'string'}, 
	  {name:'flightDate',type:'date'},
      {name:'createUserCode',type:'string'},
      {name:'createUserName',type:'string'},
      {name:'origOrgCode',type:'string'},
      {name:'origOrgName',type:'string'},
      {name:'createTime',type:'date',convert:dateConvert},
      {name:'modifyUserCode',type:'string'},
      {name:'modifyUserName',type:'string'},
      {name:'modifyTime',type:'date'},
      {name:'waybillQtyTotal',type:'number'},
      {name:'goodsQtyTotal',type:'number'},//运单总件数
      {name:'airPickQtyTotal',type:'number'},//清单总件数
      {name:'grossWeightTotal',type:'number'},
      {name:'deliverFeeTotal',type:'number'},
      {name:'arrivalFeeTotal',type:'number'},
      {name:'collectionFeeTotal',type:'number'},
      {name:'currencyCode',type:'string'} , 
       {name:'status',type:'string'} 
	]
});

//合票明细store
Ext.define('Foss.airfreight.makePickGoodsList.modifyMakePickGoodsStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.makePickGoodsList.modifyMakePickGoodsModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url:airfreight.realPath('queryMakePickGoods.action'),
		reader : {
			type : 'json',
			root : 'airTransPickupBillVo.airPickupbillList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			//查询条件
			var queryCondition = airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.getForm();
			var beginInTime = queryCondition.findField('beginInTime').getValue();
			var endInTime = queryCondition.findField('endInTime').getValue();
			var airLineName = queryCondition.findField('airLineTwoletter').getValue();
			var airWaybillNo = queryCondition.findField('airWaybillNo').getValue();
			var arrvRegionCode = queryCondition.findField('arrvRegionCode').getValue();
			var destOrgCode = queryCondition.findField('deptOrgCode').getValue();
			var createOrgName = queryCondition.findField('createOrgName').getValue();
			var status = queryCondition.findField('status').getValue();
			Ext.apply(operation,{
				params : {
					'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo' : airWaybillNo,
					'airTransPickupBillVo.airTransPickupBillDto.airLineName' : airLineName,
					'airTransPickupBillVo.airTransPickupBillDto.arrvRegionCode' : arrvRegionCode,
					'airTransPickupBillVo.airTransPickupBillDto.beginInTime' : beginInTime,
					'airTransPickupBillVo.airTransPickupBillDto.endInTime' : endInTime,
					'airTransPickupBillVo.airTransPickupBillDto.destOrgCode' : destOrgCode,
					'airTransPickupBillVo.airTransPickupBillDto.deptCode' : createOrgName,
					'airTransPickupBillVo.airTransPickupBillDto.status' : status
				}
			});			
		}
	}
});

//合票明细列表
Ext.define('Foss.airfreight.maketfrPickGoodsList.modifyPickGoods',{
	extend:'Ext.grid.Panel',
	title: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.jointTicketDetail'),
	frame:true,
	border:true,
	layout:'column',
	emptyText: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.searchResultInexistence'),
	//绑定合票信息
	bindingJointTicket : function (entity){
		var buttonTools =  airfreight.airQueryModifyMakeFlightjointList.modifyToolbarMessage.down('toolbar').query('button');
		if('N' != entity.status){
			//如果为已提交状态 暂存不可用
			buttonTools[1].setDisabled(true);
		}else{
			buttonTools[1].setDisabled(false);
		}
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord.data.status = entity.status;
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord.data.flightNo = entity.flightNo;
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord.data.arrvRegionName = entity.arrvRegionName;
		var flightDate = Ext.Date.format(new Date(entity.flightDate),'Y-m-d');
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord.data.flightDate = flightDate;
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().findField('airLineTwoletter').setCombValue(entity.airLineCode,entity.airLineCode);
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord.data.airWaybillNo = entity.airWaybillNo;
		airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.loadRecord(airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsQueryRecord);
	    airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsForm.getForm().findField('destOrgCode').setCombValue(entity.destOrgName, entity.destOrgCode);
	},
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifyAirPickupbillButton'),
			hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifyAirPickupbillButton'),
			handler: function(grid, rowIndex, colIndex){
				//获取当前record对象
				var record = grid.getStore().getAt(rowIndex);
				airfreight.airQueryModifyMakeFlightjointList.editAirwayBillWindow = Ext.create('Foss.airfreight.editPickUpAirwayBillWindow',{title: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.modifyJointTicketInventory')}).show();
				var params= {
    					'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo': record.data.id
    			};
				//全局变量赋值
				airWaybillNo=record.data.airWaybillNo;
				Ext.Ajax.request({
					async: false,
					url:airfreight.realPath('queryAirPickupbillDetail.action'),
					params:params,
					success:function(response){
						var result = Ext.decode(response.responseText);
						//合票清单
						airPickupbillEntity = result.airTransPickupBillVo.airPickupbillEntity;
						airfreight.airQueryModifyMakeFlightjointList.modifyPickGoods.bindingJointTicket(airPickupbillEntity);
						airfreight.airQueryModifyMakeFlightjointList.airPickupbillEntity = airPickupbillEntity;
						//合票明细
						airPickupbillDetailList = result.airTransPickupBillVo.airPickupbillDetailList;
						airfreight.airQueryModifyMakeFlightjointList.modifyPickGoodsResult.store.loadData(airPickupbillDetailList);
						if(airPickupbillEntity!=null){
							airwaybillNoisNotExist = true;
						}
					}
				});
			}
		},{
			iconCls: 'deppon_icons_delete',
			disabled: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifyAirPickupbillButton'),
			hidden: !airfreight.airQueryModifyMakeFlightjointList.isPermission('airfreight/modifyAirPickupbillButton'),
			handler: function(grid, rowIndex, colIndex){
			Ext.MessageBox.confirm(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),'确认删除合票清单？',
			function(btn){
			 if(btn == 'yes'){
					//获取当前record对象
					var record = grid.getStore().getAt(rowIndex);
					var params= {
	    					'airTransPickupBillVo.airPickupbillEntity.id': record.data.id
	    			};
					Ext.Ajax.request({
						async: false,
						url:airfreight.realPath('deleteAirPickupbillById.action'),
						params:params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							//后台删除成功，前台删除本条记录
							var store=grid.getStore();
							store.removeAt(rowIndex);
						},		
						exception:function(response){
							var result = Ext.decode(response.responseText);
							//提示信息
							Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
						}
					});
			 }

			});
		  }	
		}],
		text:airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.operate'),
	    width: 60,
	    dataIndex: 'id'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airWaybillNo'),
		flex: 1.5,
		dataIndex: 'airWaybillNo'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.airLineTwoletter'),
		flex: 1.5,
		dataIndex: 'airLineCode'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.arrvRegionName'),
		flex: 1.5,
		dataIndex: 'arrvRegionName'
	},{
		text:  airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.flightNo'),
		flex: 1.5,
		dataIndex: 'flightNo'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.createUser'),
		flex: 1,
		dataIndex: 'createUserName'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.createTime'),
		flex: 1.5,
		dataIndex: 'createTime',
		format: 'Y-m-d H:m:s',
		xtype:'datecolumn'
	},{
		text: airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.commitStatus'),
		flex: 1.5,
		dataIndex: 'status',
		 renderer : function(value){
        	if(value=='N'){
        	   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.notcommit');
        	}else{
        	   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airQueryModifyMakeFlightjointList.iscommit');
        	}
        }
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.makePickGoodsList.modifyMakePickGoodsStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		airfreight.airQueryModifyMakeFlightjointList.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
//渲染制查询修改合大票清单
Ext.onReady(function(){
	Ext.QuickTips.init();
	var modifyMaketfrPickGoods = Ext.create('Foss.airfreight.maketfrPickGoodsList.modifyMaketfrPickGoods');
	var modifyContainer = Ext.create('Foss.airfreight.maketfrPickGoodsList.modifyContainer');
	var modifyPickGoods = Ext.create('Foss.airfreight.maketfrPickGoodsList.modifyPickGoods');
	airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods = modifyMaketfrPickGoods;
	airfreight.airQueryModifyMakeFlightjointList.modifyPickGoods = modifyPickGoods;
	airfreight.airQueryModifyMakeFlightjointList.modifyContainer = modifyContainer;
	airfreight.airQueryModifyMakeFlightjointList.deletePickupDetailList = new Ext.util.HashMap();
	airfreight.airQueryModifyMakeFlightjointList.addPickupDetailList = new Ext.util.HashMap();
	airfreight.airQueryModifyMakeFlightjointList.stlPickupDetailList = new Ext.util.HashMap();
	airfreight.airQueryModifyMakeFlightjointList.modifyNotesTempMaps = new Ext.util.HashMap();
	airfreight.airQueryModifyMakeFlightjointList.modifyNotesMaps = new Ext.util.HashMap();
	airfreight.airQueryModifyMakeFlightjointList.modifyArrivalFeeMap =  new Ext.util.HashMap();
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airQueryModifyMakeFlightjointList_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [modifyMaketfrPickGoods,modifyContainer,modifyPickGoods],
		listeners: {
			boxready: function(){
				airfreight.airQueryModifyMakeFlightjointList.wayBillEditWindow = Ext.create('Foss.airfreight.makePickGoodsList.updateWindowPickGoodsList');
			}
		},
		renderTo: 'T_airfreight-airQueryModifyMakeFlightjointList-body'
	});
	//根据当前部门查找所属空运总调
	Ext.Ajax.request({
		//设置后台返回的合票号
		async: false,
		url:airfreight.realPath('queryAirDispatch.action'),
		params : {'airDispatchVo.deptCode':FossUserContext.getCurrentDept().code},
		success:function(response){
			var result = Ext.decode(response.responseText);
			var deptEntity = result.airDispatchVo.orgAdministrativeInfoEntity;
			var createDeptCode = deptEntity.code;
			var createDeptName = deptEntity.name;
			airfreight.airQueryModifyMakeFlightjointList.createDeptCode = createDeptCode;
			airfreight.airQueryModifyMakeFlightjointList.createDeptName = createDeptName;
			airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.setData();
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//提示信息
			Ext.Msg.alert(airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
		}
	});
	
	//modifyMaketfrPickGoods.getForm().findField('airLineTwoletter').setCombValue('CA','CA');
	if(!Ext.isEmpty(airfreight.airQueryModifyMakeFlightjointList.airWaybillNo)){
		airfreight.airQueryModifyMakeFlightjointList.modifyMaketfrPickGoods.searchPickupDetail(airfreight.airQueryModifyMakeFlightjointList.airWaybillNo);
	}
});
