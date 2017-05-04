/*******************全局变量******************/
var airWaybillNo='';
//查询model
Ext.define('Foss.airfreight.makePickGoodsList.PickGoodsQueryModel',{
	extend:'Ext.data.Model',
	fields: [
	      {name:'airTransferPickupbillNo',type:'string'},
	      {name:'arrvRegionName',type:'string'},
	      {name:'destOrgName',type:'string'},
	      {name:'transferFlightNo',type:'string'},
	      {name:'flightDate',type:'date'},
	      {name:'airLineName',type:'string'},
	      {name:'airWaybillNo',type:'string'},
	      {name:'waybillNo',type:'string'}
	]
});
//收集中转提货清单保存数据
Ext.define('Foss.airfreight.makePickGoodsList.AirTranDataCollectionEntity',{
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
	      {name:'goodsQtyTotal',type:'number'},//运单开单件数
		  {name:'airPickQtyTotal',type:'number'},//清单开单件数
	      {name:'grossWeightTotal',type:'number'},
	      {name:'deliverFeeTotal',type:'number'},
	      {name:'arrivalFeeTotal',type:'number'},
	      {name:'createUserName',type:'string'},
	      {name:'createTime',type:'date'},
	      {name:'airWaybillId',type:'string'},
		   {name:'airWaybillNo',type:'string'},//航空正单
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
airfreight.airMakeFlightjointList.select = function(grid,addStore,removeStroe) {
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
airfreight.airMakeFlightjointList.allSelect = function(grid,addStore,removeStore) {
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
airfreight.airMakeFlightjointList.validateAirPick = function(wayBillForm,chooceStore) {
	var count = chooceStore.getCount();
	if(count==null||count<=0){
		Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
			'清单件数必须大于1', 'error');
		
	}
	//修改运单信息的清单件数
		wayBillForm.findField('airPickQty').setValue(count);
		return;
};

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
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.waybillNo'),
        		name: 'waybillNo',
        		margin: '0 5 5 5',
        		readOnly:true
        	},{
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.weight') ,
        		name: 'grossWeight',
        		allowBlank:true,
        		disabled:true
        	},{
        		xtype: 'numberfield',
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.billingWeight') ,
        		name: 'billingWeight',
        		minValue: 0,
        	    hideTrigger: true,  
        	    keyNavEnabled: true,  
        	    mouseWheelEnabled: true,
    			maxLength : 8,
        		maxLengthText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.jfzlcgzdxz') 
        	},{
        		xtype: 'numberfield',
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.deliverFee'),
        		name: 'deliverFee',
        		minValue: 0,
        	    hideTrigger: true,  
        	    keyNavEnabled: true,  
        	    mouseWheelEnabled: true,
    			maxLength : 8,
        		maxLengthText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.shfcgzdxz')
        	},{
        		xtype: 'numberfield',
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.airPickQty'),
        		name: 'airPickQty',
        		minValue: 1,
        		minText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.goodsQtyError'),
        	    hideTrigger: true,  
        	    keyNavEnabled: true,  
        	    mouseWheelEnabled: true,	
        	    allowDecimals: false, 
    			maxLength : 9,
				readOnly:true,
        		maxLengthText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.jscgzdxz')
        	},{
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.receiverName'),
        		name: 'receiverName',
        		maxLength : 100,
        		maxLengthText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.shrcdycgzdxz')
        	},{
        		fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.phone'),
        		name: 'receiverContactPhone',
        	    maxLength : 100,
        		maxLengthText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.dhcdycgzdxz')
        	},{
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.address'),
        		name: 'receiverAddress',
        		maxLength : 500,
        		maxLengthText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.dzcdycgzdxz')
        	},{
        		fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.notes'),
        		name: 'notes',
        		columnWidth:.98,
        		allowBlank:true,
        		maxLength : 500,
        		maxLengthText:airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.bzcgzdxz')
        	}]
});

//**********************************未选流水号列表*********************
/**
 * 未选流水号grid
 */
Ext.define('Foss.airfreight.makePickGoodsList.LeftSerialGrid', {
	extend : 'Ext.grid.Panel',
	title: airfreight.airMakeFlightjointList.i18n('foss.airfreight.makePickGoodsList.serialContainer.oldSerialNoGrid.title'),//'未选明细',
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
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Foss.airfreight.makePickGoodsList.SerialNoStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : airfreight.realPath('findLeftSerial.action'),
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
			airfreight.airMakeFlightjointList.allSelect(leftSerialGrid,choose_store,noChoose_store);
			//修改运单信息的清单件数
			airfreight.airMakeFlightjointList.validateAirPick(wayBillForm,choose_store);
			
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
				Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
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
    			airfreight.airMakeFlightjointList.select(leftSerialGrid,choose_store,leftSerialGrid.getStore());
				//修改运单信息的清单件数
				airfreight.airMakeFlightjointList.validateAirPick(wayBillForm,choose_store);
			}else{
    			//如果右边已选的流水号为空，并且左边选择为多个，就检查右边已选的流水号的是否有冲突
    			if(chooseSerial.length<1 && lchoose.length > 1){
    				chooseSerial = lchoose;
    			}
				
				airfreight.airMakeFlightjointList.select(leftSerialGrid,choose_store,leftSerialGrid.getStore());
				//修改运单信息的清单件数
				airfreight.airMakeFlightjointList.validateAirPick(wayBillForm,choose_store);
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
			if(!airfreight.airMakeFlightjointList.select(rightSerialGrid,leftSerialGrid.getStore(),rightSerialGrid.getStore())){
				Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
						'请选择流水号', 'error');
			}
				//修改运单信息的清单件数
				airfreight.airMakeFlightjointList.validateAirPick(wayBillForm,rightSerialGrid.getStore());
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
			if(!airfreight.airMakeFlightjointList.allSelect(rightSerialGrid,leftSerialGrid.getStore(),rightSerialGrid.getStore())){
				Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
						'已经没有流水记录', 'error'); 
			}
					//修改运单信息的清单件数
			airfreight.airMakeFlightjointList.validateAirPick(wayBillForm,rightSerialGrid.getStore());
		}
	}]
});

//**********************************已选流水号列表*********************
/**
 * 已选流水号grid
 */
Ext.define('Foss.airfreight.makePickGoodsList.RightSerialGrid', {
	extend : 'Ext.grid.Panel',
	title: airfreight.airMakeFlightjointList.i18n('foss.airfreight.makePickGoodsList.serialContainer.newSerialNoGrid.title'),//'已选明细',
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
		text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.ensure'),
		columnWidth:.15,
		handler:function(){
				var window = this.up('window'),
				//已选流水列表
				rightSerialGrid = window.getRightSerialGrid(),
				//运单信息表单
				wayBillForm = window.getWayBillForm().getForm();
			//校验重量
			if(wayBillForm.findField('billingWeight').value <= 0){
				Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
						,airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.billingWeightError'));
				return false; 
			}
			//校验必须录入项是否录入
			if(!wayBillForm.isValid()){
				Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
						,airfreight.airMakeFlightjointList.i18n('foss.airfreight.checkoutMessage.validError'));
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
			airfreight.airMakeFlightjointList.pickGoodsResult.store.update(formValue.getRecord());
			
			//将list数据转成数组 以json形式传给后台
			if(formValue.getRecord().data.waybillNo.substring(0,1)!='B'){
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
		text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.cancel'),
		columnWidth:.15,
		handler:function(){
			this.up('window').close();
		}
	}]
 });

//269701--lln--2016/04/16--end



		

//修改合大票清单
Ext.define('Foss.airfreight.modifyPickUpbill',{
	extend:'Ext.window.Window',
	title:airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.modifyJointTicketInventory'),
	modal:true,
	closable:true,
	closeAction:'hide',
	width: 1100,
	layout: 'auto',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
		//form表单
		var editPickGoodsForm = Ext.create('Foss.airfreight.makePickGoodsList.EditPickGoodsForm');
		//gird列表
		var editPickGoodsResult = Ext.create('Foss.airfreight.makePickGoodsList.EditPickGoodsResult');
		//window窗口
		var editToolbarMessage = Ext.create('Foss.airfreight.makePickGoodsList.EditToolbarMessage');
		var editPickGoodsQueryModel = Ext.create('Foss.airfreight.makePickGoodsList.EditPickGoodsQueryModel');
		airfreight.airMakeFlightjointList.editPickGoodsForm = editPickGoodsForm;
		airfreight.airMakeFlightjointList.editPickGoodsResult = editPickGoodsResult;
		airfreight.airMakeFlightjointList.editToolbarMessage = editToolbarMessage;
		airfreight.airMakeFlightjointList.editPickGoodsQueryModel = airfreight.airMakeFlightjointList.editPickGoodsQueryModel;
		///修改清单信息
		me.add([editPickGoodsForm,editPickGoodsResult,editToolbarMessage,editPickGoodsQueryModel]);
	}
});

Ext.define('Foss.airfreight.makePickGoodsList.PickGoodsForm',{
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
					Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
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
					   columnWidth:.75
				   },{
					   text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.newAdd'),
					   columnWidth:.06,
					   handler: function(){
							var tabPanel1 = Ext.getCmp('mainAreaPanel');
							var activeTab = tabPanel1.getActiveTab();
							tabPanel1.remove(activeTab);
							addTab('T_airfreight-airMakeFlightjointList' , airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.makeJointTicketInventory'), airfreight.realPath('airMakeFlightjointList.action'));
					   }  
				   },{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.03
				   },{
					   text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.print'),
					   disabled: !airfreight.airMakeFlightjointList.isPermission('airfreight/uploadPickupCallEdiButton'),
					   hidden: !airfreight.airMakeFlightjointList.isPermission('airfreight/uploadPickupCallEdiButton'),
					   columnWidth:.06,
					   handler: function(){
					   		var airPickupbillId = airfreight.airMakeFlightjointList.airPickupbillId;
			            	if(Ext.isEmpty(airPickupbillId)){
			            		Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
												airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.notSaveCantPrint'));
			            		return false;
			            	}
					   		do_printpreview('airPickupbill',
								{
									'airPickupbillId':airPickupbillId
								}, ContextPath.TFR_PARTIALLINE
							);
					   }
				   },{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.03
				   },{
					   text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.export'),
					   columnWidth:.06,
					   disabled: !airfreight.airMakeFlightjointList.isPermission('airfreight/uploadPickupCallEdiButton'),
					   hidden: !airfreight.airMakeFlightjointList.isPermission('airfreight/uploadPickupCallEdiButton'),
					   handler: function(){
						    var records = airfreight.airMakeFlightjointList.pickGoodsResult.getSelectionModel().getSelection();
						    var flightNo = airfreight.airMakeFlightjointList.pickGoodsForm.getForm().findField('flightNo').value;
						    var sucessFlag = airfreight.airMakeFlightjointList.sucessFlag;
						    if(!sucessFlag){
						    	Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.notSaveExprot'));
						    	return false;
						    }
						    if(records.length==0){
						    	Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.selectRequireExprotDetail'));
						    	return false;
						    }
						    var ids = new Array();
						    for(var i = 0; i<records.length; i++ ){
						    	ids.push(records[i].data.id);
						    }
						    var airWayBillNo = airfreight.airMakeFlightjointList.airWayBillNo.getValues()[0];
							Ext.MessageBox.buttonText.yes =airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.yes');  
							Ext.MessageBox.buttonText.no =airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.no'); 
							Ext.Msg.confirm(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.sendEdi'), function(btn,text){
								if(btn == 'yes'){
									var callIsNotEdiFlag = 'YES'; 
									var flag = airfreight.airMakeFlightjointList.pickGoodsForm.callEdi(ids,airWayBillNo,callIsNotEdiFlag,flightNo);
									if(flag){
										Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.callEdiSuccess'));
										return false;
									}
								}else if(btn == 'no'){
									var callIsNotEdiFlag = 'NO';
									var flag = airfreight.airMakeFlightjointList.pickGoodsForm.callEdi(ids,airWayBillNo,callIsNotEdiFlag,flightNo);
									if(flag){
										Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.exportSuccess'));
										return false;
									}
								}
							})
					   }
				   }]
			}),
		//查询条件
			airfreight.airMakeFlightjointList.queryFieldSet = Ext.create('Ext.form.FieldSet',{
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
    			fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.airLineTwoletter'),
    			name: 'airLineTwoletter',
    			columnWidth:.45
			},{
				fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.airWaybillNo'),
				name:'airWaybillNo',
				columnWidth:.45
			},{
				xtype:'button',
				disabled: !airfreight.airMakeFlightjointList.isPermission('airfreight/queryAirPickupbillListButton'),
				hidden: !airfreight.airMakeFlightjointList.isPermission('airfreight/queryAirPickupbillListButton'),
				text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.search'),
				columnWidth:.10,
				handler:function(){
					var v = airfreight.airMakeFlightjointList.pickGoodsForm.getForm().getValues();
					if(Ext.isEmpty(v.airWaybillNo)){
						Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.pleaseInputWaybillNo'));
						return null;
					}
					var params= {
						'airTransPickupBillVo.airTransPickupBillDto.airLineTwoletter' : v.airLineTwoletter,
						'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo' : v.airWaybillNo
					}
					//全局变量赋值
					airWaybillNo=v.airWaybillNo;
					Ext.Ajax.request({
						url:airfreight.realPath('queryAirPickupbillList.action'),
						params: params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							var records = result.airTransPickupBillVo;
							
							//将查询结果赋值给records
							var inexistence = result.airTransPickupBillVo.airTransPickupBillDto.inexistence;
							if(!inexistence){
								if(records.resultAirWaybillDetailList.length==0){
									Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.airWaybillNo')+v.airWaybillNo+airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.inexistence'));
									return false;
								}
								//将records绑定到grid列表中
								airfreight.airMakeFlightjointList.pickGoodsResult.store.loadData(records.resultAirWaybillDetailList);
								//获取合大票清单
								var airWaybillEntity = records.airWaybillEntity;
								airfreight.airMakeFlightjointList.pickGoodsQueryRecord.data.destOrgCode = airWaybillEntity.destOrgCode;
								airfreight.airMakeFlightjointList.pickGoodsQueryRecord.data.flightNo = airWaybillEntity.flightNo;
								//保留查询条件
								airfreight.airMakeFlightjointList.pickGoodsQueryRecord.data.airWaybillNo = v.airWaybillNo;
								airfreight.airMakeFlightjointList.pickGoodsQueryRecord.data.arrvRegionName = airWaybillEntity.arrvRegionName;
								var flightDate = Ext.Date.format(new Date(airWaybillEntity.flightDate),'Y-m-d');
								airfreight.airMakeFlightjointList.pickGoodsQueryRecord.data.flightDate = flightDate;
								airfreight.airMakeFlightjointList.pickGoodsForm.loadRecord(airfreight.airMakeFlightjointList.pickGoodsQueryRecord);
								airfreight.airMakeFlightjointList.pickGoodsForm.getForm().findField('destOrgCode').setCombValue(airWaybillEntity.dedtOrgName, airWaybillEntity.destOrgCode);
								var printMap = new Ext.util.HashMap();
								printMap.add('airWayBillNo', v.airWaybillNo);
								airfreight.airMakeFlightjointList.airWayBillNo = printMap;
							}else{
								var airWayBillNoList = new Array();
								airWayBillNoList.push(v.airWaybillNo);
								Ext.MessageBox.buttonText.yes = airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.yes');  
								Ext.MessageBox.buttonText.no = airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.no');
								Ext.Msg.confirm(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.sjjrhdpqd'), function(btn,text){
									if(btn == 'yes'){
										var tabPanel1 = Ext.getCmp('mainAreaPanel');
										var activeTab = tabPanel1.getActiveTab();
										addTab('T_airfreight-airQueryModifyMakeFlightjointList' ,'查询_修改合大票清单', airfreight.realPath('airQueryModifyMakeFlightjointList.action')+'?airWaybillNo=' + airWayBillNoList);
									}else{
										return false;
									}
								})
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
						}
					});
				}
			}]
		}),
		//运单号添加
		airfreight.airMakeFlightjointList.addFieldSet = Ext.create('Ext.form.FieldSet',{
		    height:55,
		    width:440,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.waybillNo'),
				name:'waybillNo',
				columnWidth:.65
			},{
				xtype:'button',
				text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.add'),
				disabled: !airfreight.airMakeFlightjointList.isPermission('airfreight/addAirPickupbillDetailInfoButton'),
				hidden: !airfreight.airMakeFlightjointList.isPermission('airfreight/addAirPickupbillDetailInfoButton'),
				plugins:Ext.create('Deppon.ux.ButtonLimitingPlugin',{
					seconds:2
				}),
				columnWidth:.15,
				handler:function(){
					//获取运单号
					var waybillNo = airfreight.airMakeFlightjointList.pickGoodsForm.getForm().getValues()['waybillNo'];
					if(Ext.isEmpty(waybillNo)){
						Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.waybillNotNull'));
						return null;
					}
					var pickGoodsstoreLength =  airfreight.airMakeFlightjointList.pickGoodsResult.store.data.items.length;
					var pickGoodsResult = airfreight.airMakeFlightjointList.pickGoodsResult.store.data.items;
					for(var i=0;i<pickGoodsstoreLength;i++){
						if(waybillNo==pickGoodsResult[i].data.waybillNo){
							Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.inventoryDetailExist')+waybillNo+ airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.nonRepeatableRead'));
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
							var transfersNumber = result.airTransPickupBillVo.resultAirWaybillDetailList;
							if(!Ext.isEmpty(transfersNumber)){
								/*这里需要判断是否都是同一种产品*/
								var transportType=transfersNumber[0].transportType;
								var length=airfreight.airMakeFlightjointList.pickGoodsResult.store.data.length;
								var items=airfreight.airMakeFlightjointList.pickGoodsResult.store.data.items;
								if(length>0){
									for(var i=0;i<length;i++){
									  if(items[i].data.transportType!=transportType){
									  	 Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), '【'+waybillNo+'】 与 【'+items[i].data.waybillNo+'】 运输类型不一致,不能添加!');
									  	 return;
									  }
									}
								}
								airfreight.airMakeFlightjointList.pickGoodsResult.store.add(transfersNumber);
								 Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.addSuccess'));
								return  null;
							}else{
								Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.addWaybillFail'));
								return  null;
							}
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
							return false;
						}
					});
				}
			}]
		}),
		//提货清单信息
		airfreight.airMakeFlightjointList.saveFieldSet = Ext.create('Ext.form.FieldSet',{
		    height:60,
		    width:1040,
		    layout:'column',
		    defaults:{
				margin:'5 5 5 5',
				xtype: 'textfield'
			},
			items:[{
				xtype:'commonairagencydeptselector',
				fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.dedtOrgName'),
				name:'destOrgCode',
				displayField : 'agentDeptName',// 显示名称
				valueField : 'agentDeptCode',// 值
				allowBlank: false,
				columnWidth:.25
			},{
				fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.flightNo'),
				name:'flightNo',
				disabled:true,
				allowBlank: false,
				columnWidth:.25
			},{
				fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.arrvRegionName'),
				name:'arrvRegionName',
				allowBlank: false,
				disabled:true,
				columnWidth:.25
			},{
				xtype: 'datefield',
		        fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.flightNoDate'),
		        name: 'flightDate',
		        format: 'Y-m-d',
		        allowBlank: false,
		        columnWidth:.25
			}]
		}),
		//设置运单明细收缩展开样式
		airfreight.airMakeFlightjointList.operateContainer = Ext.create('Ext.form.Panel',{
			height:30,
			width:300,
			frame:false,
			border:false,
			defaults:{
				margin:'0 5 5 5',
				readOnly:true
			},
			items:[{
			    dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'bottom',
		        layout:'column',
		        items: [{
		        	xtype:'button',
		        	text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.delete'),
		        	handler:function(){
          				var record = airfreight.airMakeFlightjointList.pickGoodsResult.getSelectionModel().getSelection();
          				if(record.length!=0){
            				Ext.MessageBox.buttonText.yes = airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.delete');  
            				Ext.MessageBox.buttonText.no = airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.cancel'); 
            				Ext.Msg.confirm( airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.isorisntDel'), function(btn,text){
            					if(btn == 'yes'){
            						//将选择的record对象从列表中移除
                  					airfreight.airMakeFlightjointList.pickGoodsResult.store.remove(record);
                  					Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.deleteSuccess'));
            					}else{
            						return false;
            					}
            				})
          					
          				}else{
          					Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.pleaseRequireDelInventory'));
          				}
		        	}
		        },{
		        	xtype: 'container',
		        	html:'&nbsp;',
					width:20
		        },{
		        	xtype:'button',
		        	text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.transfer'),
		        	handler:function(){
          				var record = airfreight.airMakeFlightjointList.pickGoodsResult.getSelectionModel().getSelection();
          				if(record.length==0){
          					Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.pleaseRequirFlagInventory'));
          					return null;
          				}
          				var backArrivalFeeMap = airfreight.airMakeFlightjointList.backArrivalFeeMap;
      					//将选择的record对象从列表中移除
      					for(var i=0;i<record.length;i++){
      						var recordValue = record[i].data;
      						if(recordValue.beTransfer=='Y'){
      							var arrivalFee = backArrivalFeeMap.get(recordValue.id);
      							var collectionFee = backArrivalFeeMap.get(recordValue.id+"collectionFee");
      							recordValue.beTransfer='N';	
      							recordValue.arrivalFee = arrivalFee;
      							recordValue.collectionFee = collectionFee;
      						}else {
      							recordValue.beTransfer='Y';
      							backArrivalFeeMap.add(recordValue.id,recordValue.arrivalFee);
      							backArrivalFeeMap.add(recordValue.id+"collectionFee",recordValue.collectionFee);
      							recordValue.arrivalFee = 0;
      							recordValue.collectionFee = 0;
      						}
      						airfreight.airMakeFlightjointList.backArrivalFeeMap = backArrivalFeeMap;
      					}
      					airfreight.airMakeFlightjointList.pickGoodsResult.store.update(record);
      					Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'), airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.flagSuccess'));
		        	}
		        }]
		    }]
		}],
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({},config);
				me.callParent([cfg]);
			}
		})
	]
});
//合大票清单model
Ext.define('Foss.airfreight.makePickGoodsList.pickGoodsResultModel',{
	extend:'Ext.data.Model',
	fields:[
	        {name:'airWaybillId',type:'string'},
	        {name:'waybillNo',type:'string'},
			 {name:'airWaybillNo',type:'string'},//清单号
	        {name:'beTransfer',type:'string',defaultValue:'N'},
	        {name:'arrvRegionName',type:'string'},
	        {name:'grossWeight',type:'number'},
	        {name:'billingWeight',type:'number'},
	        {name:'volume',type:'number'},
	        {name:'goodsQty',type:'number'},//运单件数
			{name:'airPickQty',type:'number'},//清单件数
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
Ext.define('Foss.airfreight.makePickGoodsList.pickGoodsResultStore',{
	extend: 'Ext.data.Store',
	model:'Foss.airfreight.makePickGoodsList.pickGoodsResultModel',
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
			
			for(var i=0;i<totalArray.length;i++){
				//计算运单件数
				goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty,0);
				//计算清单件数
				airPickQtyTotal = airPickQtyTotal + Ext.Number.from(totalArray[i].data.airPickQty,0);
				//计算毛重
				grossWeightTotal = grossWeightTotal + Ext.Number.from(totalArray[i].data.grossWeight,0);
				//计算公斤送货费
				deliverFeeTotal = deliverFeeTotal + Ext.Number.from(totalArray[i].data.deliverFee,0);
				//计算到付费
				arrivalFeeTotal = arrivalFeeTotal + Ext.Number.from(totalArray[i].data.arrivalFee,0);
			}
			var count = 0;
			var toolbarArray = airfreight.airMakeFlightjointList.pickGoodsResult.down('toolbar').query('textfield');
			for(var j=0;j<toolbarArray.length;j++){
				if(count==0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count==1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count==2){
					toolbarArray[j].setValue(airPickQtyTotal);
				}else if(count==3){
					toolbarArray[j].setValue(grossWeightTotal + '  ' + airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.kilo'));
					toolbarArray[j].hideValue = grossWeightTotal;
				}else if(count==4){
					toolbarArray[j].setValue(deliverFeeTotal);
				}else{
					toolbarArray[j].setValue(arrivalFeeTotal);
				}
				count ++;
			}
		}
	}
});

//运单明细列表
Ext.define('Foss.airfreight.makePickGoodsList.pickGoodsResult',{
	extend:'Ext.grid.Panel',
	title:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.inventoryDetail'),
	frame:true,
	border:true,
	layout:'column',
	emptyText: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.searchResultInexistence'),
	columns:[{
		xtype: 'actioncolumn',
		items: [/*{
			iconCls: 'deppon_icons_edit',
			handler: function(grid, rowIndex, colIndex){
				if(airfreight.airMakeFlightjointList.vlidateAirWaybillNo){
					Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),
									airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.cannotModifyDetail'));
  					return false;
  				}
				//获取当前record对象
				var record = grid.getStore().getAt(rowIndex);
				//269701--2016/04/24--begin
						//定义运单修改窗口
					var wayBillEditWindow = airfreight.airMakeFlightjointList.wayBillEditWindow,
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
								'airTransPickupBillVo.airTransPickupBillDto.waybillNo': record.data.waybillNo,//运单号
								'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo': airWaybillNo//正单号
								//serialNo:record.get('serialNo')	            				
								}
						});
						//已选择数据加载
						leftSerialGrid.getStore().load({
							params : {
								'airTransPickupBillVo.airTransPickupBillDto.waybillNo': record.data.waybillNo,//运单号
								'airTransPickupBillVo.airTransPickupBillDto.airWaybillNo': airWaybillNo//正单号
								//serialNo:record.get('serialNo')	            				
							}
						});
						wayBillEditWindow.show();
				//269701--2016/04/24--end
				
				
				//window显示
				//airfreight.airMakeFlightjointList.updateWindowPickGoodsListWin = Ext.create('Foss.airfreight.airMakePickGoodsList.updateWindowPickGoodsList').show();
				//将grid列中的数据绑定到window中的form中
				//airfreight.airMakeFlightjointList.updateWindowPickGoodsList.getForm().loadRecord(record);
			}
		}*/],
		//text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.operate'),
	    width: 40,
	    hidden:true,
	    dataIndex: 'id'
	},{
		hidden: true,
		dataIndex: 'airWaybillNo'
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.waybillNo'),
		flex: 0.6,
		dataIndex: 'waybillNo'
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.arrvRegionName'),
		flex: 0.6,
		dataIndex: 'arrvRegionName'
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.airChangeInventory.goodsName'),
		flex: 0.6,
		dataIndex: 'goodsName'
	},{//269701--2016/04/21--begin
		//运单开单件数
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.poll'),
		flex: 0.6,
		dataIndex: 'goodsQty'
	},{
		//清单件数
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.airPickQty'),
		flex: 0.6,
		dataIndex: 'airPickQty'
		//269701--2016/04/21--end
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.weightKilo'),
		flex: 0.6,
		dataIndex: 'grossWeight'
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.billingWeightKilo'),
		flex: 0.6,
		dataIndex: 'billingWeight'
	},{
		text:  airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.ifTransfer'),
		flex: 0.6,
		dataIndex: 'beTransfer',
		renderer :function(value){
			return value=='N'?airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.no'):airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.yes');
		}
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.pickupType'),
		flex: 0.6,
		dataIndex: 'pickupType',
		renderer: function (value) {
                if (value == 'DELIVER_INGA') {
                  return  airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_INGA');
                } else if(value=='DELIVER_NOUP'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_NOUP');
                }else if(value=='DELIVER'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER');
                }else if(value=='DELIVER_UP'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_UP');
                }else if(value=='INNER_PICKUP'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.INNER_PICKUP');
                }else if(value=='SELF_PICKUP'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP');
                }else if(value=='LARGE_DELIVER_UP'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.LARGE_DELIVER_UP');
                }else if(value=='SELF_PICKUP_FREE_AIR'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP_FREE_AIR');
                }else if(value=='DELIVER_INGA_AIR'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_INGA_AIR');
                }else if(value=='DELIVER_UP_AIR'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_UP_AIR');
                }else if(value=='DELIVER_AIR'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_AIR');
                }else if(value=='AIRPORT_PICKUP'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.AIRPORT_PICKUP');
                }else if(value=='DELIVER_NOUP_AIR'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_NOUP_AIR');
                }else if(value=='SELF_PICKUP_AIR'){
                   return airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP_AIR');
                }else if(value=='PICKUP_TO_DOOR'){
                   return airfreight.airQueryModifyMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.pickup.PICKUP_TO_DOOR');
                }
            }
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.deliverFee'),
		flex: 0.6,
		dataIndex: 'deliverFee'
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.arrivalFee'),
		flex: 0.6,
		dataIndex: 'arrivalFee'
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.collectionFee'),
		flex: 0.6,
		dataIndex: 'collectionFee'
	},{
		text: airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.notes'),
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
			   fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.ticket'),
			   columnWidth:.10,
			   dataIndex: 'billNoTotal'
		   },{//269701--2016/04/20--begin
			   //运单总件数
			   fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.poll'),
			   columnWidth:.12,
			   labelWidth:80,
			   dataIndex: 'goodsQtyTotal'
		   },{
			   //清单总件数
			   fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.airPickQty'),
			   columnWidth:.12,
			   labelWidth:80,
			   dataIndex: 'airPickQtyTotal'
			 //269701--2016/04/20--end
		   },{
			   fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.weight'),
			   columnWidth:.12,
			   hideValue:'',
			   dataIndex: 'grossWeightTotal'
		   },{
			   fieldLabel: airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.kiloDeliverFee'),
			   labelWidth:80,
			   columnWidth:.15,
			   dataIndex: 'deliverFeeTotal'
		   },{
			   fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.arrivalFee'),
			   columnWidth:.15,
			   labelWidth:60,
			   dataIndex: 'arrivalFeeTotal'
		   }]
	}],
		//修改运单信息窗口
	wayBillEditWindow : null,
	getWayBillEditWindow : function(){
		if(this.wayBillEditWindow==null){
			this.wayBillEditWindow = airfreight.airMakeFlightjointList.wayBillEditWindow;
		}
		return this.wayBillEditWindow;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.makePickGoodsList.pickGoodsResultStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.callParent([cfg]);
	}
});
Ext.define('Foss.airfreight.makePickGoodsList.toolbarMessage',{
	extend:'Ext.form.Panel',
	frame:false,
	border:false,
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
			   columnWidth:.60
		   },{
			   fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.createUser'),
			   labelWidth:55,
			   columnWidth:.15,
			   dataIndex: 'createUserName'
		   },{
			   fieldLabel:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.createTime'),
			   labelWidth:75,
			   columnWidth:.25,
			   dataIndex: 'createTime'
		   },{
			   fieldLabel:'',
			   columnWidth:.05
		   },{
			   xtype:'button',
			   text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.addSubmit'),
			   disabled: !airfreight.airMakeFlightjointList.isPermission('airfreight/addAirPickBILLAirPickupBillButton'),
			   hidden: !airfreight.airMakeFlightjointList.isPermission('airfreight/addAirPickBILLAirPickupBillButton'),
			   labelWidth:100,
			   columnWidth:.06,
			   handler:function(){
			   	   //点击保存时禁用"保存"按钮，防止重复提交
			   	   var hiddenButton2 = airfreight.airMakeFlightjointList.toolbarMessage.down('toolbar').query('button');
			   	   hiddenButton2[0].disable(true);
			   	   hiddenButton2[1].disable(true);
				   var record = airfreight.airMakeFlightjointList.pickGoodsResult.store.data.items;
				   if(record.length==0){
					   Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
							   , airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.inventoryDetailNotData'));
					   hiddenButton2[0].setDisabled(false);
					   hiddenButton2[1].setDisabled(false);
					   return false;
				   }
				   if(!airfreight.airMakeFlightjointList.pickGoodsForm.getForm().isValid()){
	            		Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
	            				,airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.basicInfoIsNull'));
	            		hiddenButton2[0].setDisabled(false);
	            		hiddenButton2[1].setDisabled(false);
	            		return false;
	               }
				   var airWayBillNo = '';
				   if(airfreight.airMakeFlightjointList.airWayBillNo){
				       var airWayBillNo = airfreight.airMakeFlightjointList.airWayBillNo.get('airWayBillNo');
				   }else{
				       Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
							   , airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.noInputAirWayBillNo'));
					   hiddenButton2[0].setDisabled(false);
					   hiddenButton2[1].setDisabled(false);
					   return false;
				   }
				   var arrys = {};
				   var fromValues = airfreight.airMakeFlightjointList.pickGoodsForm.getForm().getValues();
				   //获取中转单号
				   arrys['airTransferPickupbillNo'] = fromValues['airTransferPickupbillNo'];
				   //获取目的站
				   arrys['arrvRegionName'] = fromValues['arrvRegionName'];
				   //获取到达网点
				   arrys['destOrgCode'] = fromValues['destOrgCode'];
				   var destOrg = airfreight.airMakeFlightjointList.pickGoodsForm.getForm().findField('destOrgCode');
				   var destOrgName = destOrg.rawValue;
				   if(destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true)){
				       destOrgName = destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true).get('agentDeptName');
				   }
				   //到达网点名称
				   arrys['destOrgName'] = destOrgName;
				   //获取中转航班号
				   arrys['transferFlightNo'] = fromValues['flightNo'];
				   //获取中转日期
				   arrys['transferDate'] = fromValues['flightDate'];
				   //获取toolbar统计栏信息
				   var toolbars = airfreight.airMakeFlightjointList.pickGoodsResult.down('toolbar').items.items;
				   for(var j=0;j<toolbars.length;j++){
					   if(toolbars[j].dataIndex=='grossWeightTotal'){
						   arrys[toolbars[j].dataIndex] = toolbars[j].hideValue;
					   }else{
						   arrys[toolbars[j].dataIndex] = toolbars[j].value;   
					   }
				   }
				   //获取制单人、制单时间
				   var getCreateMesages = airfreight.airMakeFlightjointList.toolbarMessage.down('toolbar').items.items;
				   for(var m=0;m<getCreateMesages.length;m++){
					   arrys[getCreateMesages[m].dataIndex] = getCreateMesages[m].value;
				   }
				    // 航空正单
				   arrys['airWaybillNo'] = airWayBillNo;
				   //航空正单组建id
				   arrys['airWaybillId'] = record[0].data.airWaybillId;
				   //获取航空正单明细组建id
				   var ids = "";
				   var jsonArry = [];
	           	   for(var i=0;i<record.length;i++){
	           		 var temp = record[i].data; 
	           		 var obj = record[i].data
	           		     obj.weight = temp.grossWeight;
	           		     obj.airWaybillNo = airfreight.airMakeFlightjointList.airWayBillNo.get('airWayBillNo');
			      	 jsonArry.push(obj);
	           			if(record.length-1==i){
	           				ids = ids + obj.id;
				 			continue;
				 		}
	           			ids = ids + obj.id+',';
	           	    }
	           	   arrys['ids'] = ids;
	           	   arrys['status'] = 'Y';//已提交状态
	           	   var records = Ext.create('Foss.airfreight.makePickGoodsList.AirTranDataCollectionEntity',arrys);
	           	   //请求后台保存制作中转提货清单信息
	           	   var jsons = {airTransPickupBillVo:{airTranDataCollectionEntity:records.data,airPickupbillDetailList:jsonArry}};
                   Ext.Ajax.request({
		           		url:airfreight.realPath('addAirPickBILLAirPickupBill.action'),
		           		jsonData:jsons,
		        		success:function(response){
		        			var result = Ext.decode(response.responseText);
		        			var airPickupbillId = result.airTransPickupBillVo.airPickupbillId;
    						airfreight.airMakeFlightjointList.airPickupbillId = airPickupbillId;
		        			airfreight.airMakeFlightjointList.sucessFlag = true;
		        			airfreight.airMakeFlightjointList.vlidateAirWaybillNo = true;
		 				    airfreight.airMakeFlightjointList.addFieldSet.setDisabled(true);
						    airfreight.airMakeFlightjointList.queryFieldSet.setDisabled(true);
						    airfreight.airMakeFlightjointList.saveFieldSet.setDisabled(true);
						    var hiddenButton1 = airfreight.airMakeFlightjointList.operateContainer.down('toolbar').query('button');
						    hiddenButton1[0].disable(true);
						    hiddenButton1[1].disable(true);
						    Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.saveSuccess'));
		        		},
		        		exception:function(response){
		        			hiddenButton2[0].setDisabled(false);
		        			hiddenButton2[1].setDisabled(false);
		        			var result = Ext.decode(response.responseText);
		        			Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
		        		}
	           	   });
			   }
		   },{
			   fieldLabel:'',
			   columnWidth:.01
		   },{
			   xtype: "label",
			   text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.prompted'),
			   style : 'color:red;',
			   labelWidth : 300,
			   columnWidth:.76
		   },{
			   xtype:'button',
			   text:airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.transient'),//暂存
			   disabled: !airfreight.airMakeFlightjointList.isPermission('airfreight/addAirPickBILLAirPickupBillButton'),
			   hidden: !airfreight.airMakeFlightjointList.isPermission('airfreight/addAirPickBILLAirPickupBillButton'),
			   labelWidth:100,
			   columnWidth:.06,
			   handler:function(){
			   	   //点击保存时禁用"保存"按钮，防止重复提交
			   	   var hiddenButton2 = airfreight.airMakeFlightjointList.toolbarMessage.down('toolbar').query('button');
			   	   hiddenButton2[0].disable(true);
			   	   hiddenButton2[1].disable(true);
				   var record = airfreight.airMakeFlightjointList.pickGoodsResult.store.data.items;
				   if(record.length==0){
					   Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
							   , airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.inventoryDetailNotData'));
					   hiddenButton2[0].setDisabled(false);
					   hiddenButton2[1].setDisabled(false);
					   return false;
				   }
				   if(!airfreight.airMakeFlightjointList.pickGoodsForm.getForm().isValid()){
	            		Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
	            				,airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.basicInfoIsNull'));
	            		hiddenButton2[0].setDisabled(false);
	            		hiddenButton2[1].setDisabled(false);
	            		return false;
	               }
				   var airWayBillNo = '';
				   if(airfreight.airMakeFlightjointList.airWayBillNo){
				       var airWayBillNo = airfreight.airMakeFlightjointList.airWayBillNo.get('airWayBillNo');
				   }else{
				       Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage')
							   , airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.noInputAirWayBillNo'));
					   hiddenButton2[0].setDisabled(false);
					   hiddenButton2[1].setDisabled(false);
					   return false;
				   }
				   var arrys = {};
				   var fromValues = airfreight.airMakeFlightjointList.pickGoodsForm.getForm().getValues();
				   //获取中转单号
				   arrys['airTransferPickupbillNo'] = fromValues['airTransferPickupbillNo'];
				   //获取目的站
				   arrys['arrvRegionName'] = fromValues['arrvRegionName'];
				   //获取到达网点
				   arrys['destOrgCode'] = fromValues['destOrgCode'];
				   var destOrg = airfreight.airMakeFlightjointList.pickGoodsForm.getForm().findField('destOrgCode');
				   var destOrgName = destOrg.rawValue;
				   if(destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true)){
				       destOrgName = destOrg.store.findRecord('agentDeptCode',fromValues['destOrgCode'],0,false,true,true).get('agentDeptName');
				   }
				   //到达网点名称
				   arrys['destOrgName'] = destOrgName;
				   //获取中转航班号
				   arrys['transferFlightNo'] = fromValues['flightNo'];
				   //获取中转日期
				   arrys['transferDate'] = fromValues['flightDate'];
				   //获取toolbar统计栏信息
				   var toolbars = airfreight.airMakeFlightjointList.pickGoodsResult.down('toolbar').items.items;
				   for(var j=0;j<toolbars.length;j++){
					   if(toolbars[j].dataIndex=='grossWeightTotal'){
						   arrys[toolbars[j].dataIndex] = toolbars[j].hideValue;
					   }else{
						   arrys[toolbars[j].dataIndex] = toolbars[j].value;   
					   }
				   }
				   //获取制单人、制单时间
				   var getCreateMesages = airfreight.airMakeFlightjointList.toolbarMessage.down('toolbar').items.items;
				   for(var m=0;m<getCreateMesages.length;m++){
					   arrys[getCreateMesages[m].dataIndex] = getCreateMesages[m].value;
				   }
				    // 航空正单
				   arrys['airWaybillNo'] = airWayBillNo;
				   //航空正单组建id
				   arrys['airWaybillId'] = record[0].data.airWaybillId;
				   //获取航空正单明细组建id
				   var ids = "";
				   var jsonArry = [];
	           	   for(var i=0;i<record.length;i++){
	           		 var temp = record[i].data; 
	           		 var obj = record[i].data
	           		     obj.weight = temp.grossWeight;
	           		     obj.airWaybillNo = airfreight.airMakeFlightjointList.airWayBillNo.get('airWayBillNo');
			      	 jsonArry.push(obj);
	           			if(record.length-1==i){
	           				ids = ids + obj.id;
				 			continue;
				 		}
	           			ids = ids + obj.id+',';
	           	    }
	           	   arrys['ids'] = ids;
	           	   arrys['status'] = 'N';//未提交状态
	           	   var records = Ext.create('Foss.airfreight.makePickGoodsList.AirTranDataCollectionEntity',arrys);
	           	   //请求后台保存制作中转提货清单信息
	           	   var jsons = {airTransPickupBillVo:{airTranDataCollectionEntity:records.data,airPickupbillDetailList:jsonArry}};
                   Ext.Ajax.request({
		           		url:airfreight.realPath('addAirPickBILLAirPickupBill.action'),
		           		jsonData:jsons,
		        		success:function(response){
		        			var result = Ext.decode(response.responseText);
		        			var airPickupbillId = result.airTransPickupBillVo.airPickupbillId;
    						airfreight.airMakeFlightjointList.airPickupbillId = airPickupbillId;
		        			airfreight.airMakeFlightjointList.sucessFlag = true;
		        			airfreight.airMakeFlightjointList.vlidateAirWaybillNo = true;
		 				    airfreight.airMakeFlightjointList.addFieldSet.setDisabled(true);
						    airfreight.airMakeFlightjointList.queryFieldSet.setDisabled(true);
						    airfreight.airMakeFlightjointList.saveFieldSet.setDisabled(true);
						    var hiddenButton1 = airfreight.airMakeFlightjointList.operateContainer.down('toolbar').query('button');
						    hiddenButton1[0].disable(true);
						    hiddenButton1[1].disable(true);
						    Ext.ux.Toast.msg(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.saveSuccess'));
		        		},
		        		exception:function(response){
		        			hiddenButton2[0].setDisabled(false);
		        			hiddenButton2[1].setDisabled(false);
		        			var result = Ext.decode(response.responseText);
		        			Ext.Msg.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.promptMessage'),result.message);
		        		}
	           	   });
			   }
		   }]
	}]
});
//修改清单信息window
Ext.define('Foss.airfreight.airMakePickGoodsList.updateWindowPickGoodsList',{
	extend: 'Ext.window.Window',
	title: airfreight.airMakeFlightjointList.i18n('foss.airfreight.airMakeFlightjointList.modifyWaybill'),
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
//渲染制作中转提货清单
Ext.onReady(function(){
	Ext.QuickTips.init();
	//form表单
	var pickGoodsForm = Ext.create('Foss.airfreight.makePickGoodsList.PickGoodsForm');
	//gird列表
	var pickGoodsResult = Ext.create('Foss.airfreight.makePickGoodsList.pickGoodsResult');
	//window窗口
	var toolbarMessage = Ext.create('Foss.airfreight.makePickGoodsList.toolbarMessage');
	//修改清单信息
	var pickGoodsQueryRecord = Ext.create('Foss.airfreight.makePickGoodsList.PickGoodsQueryModel');
	airfreight.airMakeFlightjointList.pickGoodsForm = pickGoodsForm;
	airfreight.airMakeFlightjointList.pickGoodsResult = pickGoodsResult;
	airfreight.airMakeFlightjointList.toolbarMessage = toolbarMessage;
	airfreight.airMakeFlightjointList.pickGoodsQueryRecord = pickGoodsQueryRecord;
	airfreight.airMakeFlightjointList.saveSucess = false;
	airfreight.airMakeFlightjointList.backArrivalFeeMap =  new Ext.util.HashMap();
	airfreight.airMakeFlightjointList.vlidateAirWaybillNo = false;
	Ext.Ajax.request({
		//设置后台返回的中转单号
		async: false,
		url:airfreight.realPath('generateTransfersNumber.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			var toolbarMessageArray = airfreight.airMakeFlightjointList.toolbarMessage.down('toolbar').query('textfield');
			var createUserName = FossUserContext.getCurrentUserEmp().empName;
			var createTime = Ext.Date.format(new Date(),'Y-m-d H:i:s');
			toolbarMessageArray[1].setValue(createUserName);
			toolbarMessageArray[2].setValue(createTime);
		},
		exception : function(response) {
				var result = Ext.decode(response.responseText);
				top.Ext.MessageBox.alert(airfreight.airMakeFlightjointList.i18n('foss.airfreight.public.exportFail'),result.message);
		}
	});
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airMakeFlightjointList_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [pickGoodsForm,pickGoodsResult,toolbarMessage],
		listeners: {
			boxready: function(){
				airfreight.airMakeFlightjointList.wayBillEditWindow = Ext.create('Foss.airfreight.airMakePickGoodsList.updateWindowPickGoodsList');
			}
		},
		renderTo: 'T_airfreight-airMakeFlightjointList-body'
	});
	//加入form元素并初始化中转单号等数据...
	airfreight.airMakeFlightjointList.pickGoodsForm.loadRecord(airfreight.airMakeFlightjointList.pickGoodsQueryRecord);
	
	airfreight.airMakeFlightjointList.pickGoodsForm.getForm().findField('airLineTwoletter').setCombValue('CA','CA');
	airfreight.airMakeFlightjointList.sucessFlag = false;
});