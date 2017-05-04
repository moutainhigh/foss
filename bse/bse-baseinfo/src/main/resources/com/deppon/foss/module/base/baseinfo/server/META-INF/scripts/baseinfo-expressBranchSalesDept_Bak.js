 /**
  * 转换long类型为日期
  *@param value 要转换的时间
  */
baseinfo.expressBranchSalesDept.longToDateConvert = function(value) {
	if (!Ext.isEmpty(value)) {
		return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	} else {
		return null;
	}
}

baseinfo.expressBranchSalesDept.expressBranchSalesDeptConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

//查询营业部信息
baseinfo.expressBranchSalesDept.salesDeptFormQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var salesDeptName = form.findField('salesDeptName').getValue();
	var salesDeptCode = form.findField('salesDeptCode').getValue();
	var salesDeptPinyin = form.findField('salesDeptPinyin').getValue();
	var provCode = form.findField('provCode').getValue();
	var cityCode = form.findField('cityCode').getValue();
	var countyCode = form.findField('countyCode').getValue();
	
	
	// 设置参数
	params = {};
	Ext.apply(params, {
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.salesDeptName' : salesDeptName,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.salesDeptCode' : salesDeptCode,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.salesDeptPinyin' : salesDeptPinyin,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.provCode' : provCode,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.cityCode' : cityCode,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.countyCode' : countyCode
	});
	
	//获取grid及grid的store,并给store赋值
	var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addSalesDeptWindow_Id');
	var grid = a_win.items.items[1];
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		grid.show();
	    	}
	       }
	    }); 
}


//查询已配置(快递点部营业部映射关系)的快递点部FORM查询方法: 
baseinfo.expressBranchSalesDept.expressBranchSalesDeptQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var salesDeptCode = form.findField('salesDeptCode').getValue();
	var expressPartCode = form.findField('expressPartCode').getValue();
	var businessTime = form.findField('businessTime').getValue();
	var active = form.findField('active').getValue();
	
	// 设置参数
	params = {};
	Ext.apply(params, {
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.expressPartCode' : expressPartCode,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.salesDeptCode' : salesDeptCode,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.businessTime' : businessTime,
		'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.active' : active
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	  
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		grid.show();
	    	}
	       }
	    }); 
}

//进入已配置快递点部营业部映射关系的修改界面
baseinfo.expressBranchSalesDept.editDetialExpressBranchSalesDept = function(grid, rowIndex, colIndex){

	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	//只有未激活的才可以修改
	if(selection.data.active=='N'){
		Ext.MessageBox.show({
			title : '温馨提示',
			msg : '请选择有效数据进行操作',
			width : 300,
			buttons : Ext.Msg.OK,
			icon : Ext.MessageBox.WARNING
		});
		return false;
	}
	
	//获取显示弹出win
	var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_Id');
	if (Ext.isEmpty(a_win)) {
		a_win = Ext.create('Foss.baseinfo.expressBranchSalesDept.showOrUpdateExpressBranchSalesDeptWindow');
	}
	//快递点部FORM加载数据
	var expressBranchSalesDeptModel  = new Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel(selection.data);
	var showOrUpdateExpressBranchSalesDeptConfigForm = a_win.items.items[0];
	showOrUpdateExpressBranchSalesDeptConfigForm.loadRecord(expressBranchSalesDeptModel);
	a_win.expressBranchSalesDeptEntity = selection.data;
	
	//快递点部名称只读
	showOrUpdateExpressBranchSalesDeptConfigForm.getForm().findField('partName').setReadOnly(false);
	showOrUpdateExpressBranchSalesDeptConfigForm.getForm().findField('salesDeptCode').setReadOnly(true);
	
	//作废win窗体的按钮
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_cancel_Id').setVisible(true);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_reset_Id').setVisible(true);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_submit_Id').setVisible(true);
	
	//显示快递点部营业部映射关系配置界面
	a_win.show();

}

//进入已配置快递点部营业部映射关系的查看界面
baseinfo.expressBranchSalesDept.showDetialExpressBranchSalesDept = function(grid, rowIndex, colIndex){

	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	//获取显示弹出win
	var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_Id');
	if (Ext.isEmpty(a_win)) {
		a_win = Ext.create('Foss.baseinfo.expressBranchSalesDept.showOrUpdateExpressBranchSalesDeptWindow');
	}
	//快递点部FORM加载数据
	var expressBranchSalesDeptModel  = new Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel(selection.data);
	var showOrUpdateExpressBranchSalesDeptConfigForm = a_win.items.items[0];
	showOrUpdateExpressBranchSalesDeptConfigForm.loadRecord(expressBranchSalesDeptModel);
	a_win.expressBranchSalesDeptEntity = selection.data;
	
	//快递点部名称只读
	showOrUpdateExpressBranchSalesDeptConfigForm.getForm().findField('partName').setReadOnly(true);
	showOrUpdateExpressBranchSalesDeptConfigForm.getForm().findField('salesDeptCode').setReadOnly(true);
	
	//作废win窗体的按钮
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_cancel_Id').setVisible(false);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_reset_Id').setVisible(false);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_submit_Id').setVisible(false);
	
	//显示快递点部营业部映射关系配置界面
	a_win.show();
}


//升级
baseinfo.expressBranchSalesDept.upgradeExpressBranchSalesDept = function(grid, rowIndex, colIndex){

	// grid
	var grid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	// 调用
	Ext.Ajax.request({
			url:baseinfo.realPath('upgradeExpressPartSalesDept.action'),
			params:{
				'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.selectedIds':selection.data.id
			},
			success:function(response){
				//重新查询数据
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : '温馨提示',
								msg : rawData.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}  
				    	
				    	//正常返回
				    	if(success){
				    		var result =   Ext.decode(operation.response.responseText);
				    		grid.show();
				    	}
				       }
				    }); 
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);	
				Ext.MessageBox.show({
				title : '温馨提示',
				msg : result.message,
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
				
				//重新查询数据
				grid.store.loadPage(1,{
				      callback: function(records, operation, success) {
				    	  
				    	//抛出异常  
					    var rawData = grid.store.proxy.reader.rawData;
					    if(!success && ! rawData.isException){
					    	Ext.MessageBox.show({
								title : '温馨提示',
								msg : rawData.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}  
				    	
				    	//正常返回
				    	if(success){
				    		var result =   Ext.decode(operation.response.responseText);
//							if(result.expressPartSalesDeptVo.saleDepartmentResultDtoList.length>0){
//								grid.show();
//							}
				    		grid.show();
				    	}
				       }
				    }); 
			}
		});
}

//---------------------------------------------------以上为方法实现区------------------------------------
//--------------------------------------------------快递点部营业部映射关系初始界面 begin-------------------------

Ext.define('Foss.baseinfo.expressBranchSalesDept.ActiveStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询已配置(快递点部营业部映射关系)的快递点部FORM
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptQueryForm',{
	extend:'Ext.form.Panel',
	title:'查询条件',
	frame:true,
	height:200,
	defaults:{
		margin :'20 0 0 10',
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :4
	},
	items : [
				{
					xtype:'commonsaledepartmentselector',
					fieldLabel:'营业部名称',
					name:'salesDeptCode',
					labelWidth :80,
					columnWidth:.26,
				},{
					xtype:'dynamicorgcombselector',
					fieldLabel:'快递点部名称',
					name:'expressPartCode',
					type:'ORG',
					expressPart:'Y',
					labelWidth :90,
					columnWidth:.27
				},{
					xtype : 'datetimefield_date97',
					fieldLabel:'业务日期',
					editable:false,
					name:'businessTime',
					labelWidth :70,
					columnWdth:.25,
					time : true,
					id : 'Foss_baseinfo_expressPartSalesDept_businessTime_ID',
					dateConfig: {
						el : 'Foss_baseinfo_expressPartSalesDept_businessTime_ID-inputEl'
					}
				},{
					xtype : 'combobox',
					name : 'active',
					fieldLabel : '是否有效',
					labelWidth :70,
					columnWidth : .21,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : Ext.create('Foss.baseinfo.expressBranchSalesDept.ActiveStore', {
						data : {
							'items' : [{
									'valueCode' : '',
									'valueName' : '全部'
								}, {
									'valueCode' : 'N',
									'valueName' : '否'
								}, {
									'valueCode' : 'Y',
									'valueName' : '是'
								}
							]
						}
					}),
					value : ''
				},{
					xtype : 'container',
					columnWidth : 1,
					defaultType:'button',
					layout:'column',
					items : [{
						xtype : 'button', 
						text:baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.reset'),
						columnWidth:.1,
						handler: function(){
							var form=this.up('form').getForm();
							form.reset();
						}
					},{
						xtype:'container',
						html:'&nbsp;',
						columnWidth:.8
					},{
						xtype : 'button', 
						width:70,
						columnWidth:.1,
						text : baseinfo.expressBranchSalesDept.i18n('foss.baseinfo.query'),//查询
//						hidden:!baseinfo.destinationLine.isPermission('destinationLine/destinationLineQueryButton'),
						cls:'yellow_button',
						handler : function() {
							var form=this.up('form');
							baseinfo.expressBranchSalesDept.expressBranchSalesDeptQuery(form);
						}
					}]
				
				}]			
});

//已配置(快递点部营业部映射关系)的快递点部Store的Model
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'partName'
	},{
		name:'partCode'
	},{
		name:'salesDeptName'
	},{
		name:'salesDeptCode'
	},{
		name:'expressBigRegionName'
	},{
		name:'expressBigRegionCode'
	},{
		name:'createTime',
		type:'Date',
		convert:baseinfo.expressBranchSalesDept.longToDateConvert
	},{	
		name:'createUserName'
	},{
		name:'createUserCode'
	},{
		name:'modifyTime',
		type:'Date',
		convert:baseinfo.expressBranchSalesDept.longToDateConvert
	},{	
		name:'modifyUserName'
	},{
		name:'modifyUserCode'
	},{
		name:'beginTime',
		type:'Date',
		convert:baseinfo.expressBranchSalesDept.longToDateConvert
	},{
		name:'endTime',
		type:'Date',
		convert:baseinfo.expressBranchSalesDept.longToDateConvert
	},{
		name:'active'
	}]
});

//已配置(快递点部营业部映射关系)的快递点部列表Store
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel',
	pageSize: 20,
	sorters: [{
	     property: 'modifyTime',
	     direction: 'DESC'
	 }],
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryExpressPartSalesDept.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'expressPartSalesDeptVo.expressPartSalesDeptResultDtoList',
			totalProperty:'totalCount'
		}
	},
	submitParams:null,
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	getSubmitParams: function(){
		return this.submitParams;
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

//已配置(快递点部营业部映射关系)的快递点部列表
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptGrid',{
	extend:'Ext.grid.Panel',
    title: '快递点部营业部映射列表',
    frame:true,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:'操作',
    	width:120,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',
			tooltip:'修改',
			getClass : function (v, m, r, rowIndex) {
				if (r.get('active') === 'Y'&&baseinfo.expressBranchSalesDept.isPermission('baseinfo/updateExpressBranchSalesDept')) {
				    return 'deppon_icons_edit';
				} else {
				    return 'statementBill_hide';
				}
			},
			//disabled:!baseinfo.expressPartSalesDept.isPermission('baseinfo/updateExpressPartSalesDept'),
			handler:function(grid, rowIndex, colIndex){
				baseinfo.expressBranchSalesDept.editDetialExpressBranchSalesDept(grid, rowIndex, colIndex);
			}
//    	},{
//    		iconCls : 'deppon_icons_softwareUpgrade',
//			tooltip:'升级',
//			getClass : function (v, m, r, rowIndex) {
//				if (r.get('active') === 'Y') {
//				    return 'deppon_icons_softwareUpgrade';
//				} else {
//				    return 'statementBill_hide';
//				}
//			},
//			handler:function(grid, rowIndex, colIndex){
//				baseinfo.expressBranchSalesDept.upgradeExpressBranchSalesDept(grid, rowIndex, colIndex);
//			}
    	},{
    		iconCls : 'deppon_icons_showdetail',
			tooltip:'查看',
			handler:function(grid, rowIndex, colIndex){
				baseinfo.expressBranchSalesDept.showDetialExpressBranchSalesDept(grid, rowIndex, colIndex);
			}
    	}]
	},{
		header: '营业部名称',
		dataIndex: 'salesDeptName'
	},{
		header: '营业部编码',
		dataIndex: 'salesDeptCode'
	},{
		header: '快递点部名称',
		dataIndex: 'partName'
	},{
		header: '快递点部编码',
		dataIndex: 'partCode'
	},{
		header: '所属快递大区',
		dataIndex: 'expressBigRegionName'
	},{
		header: '所属快递大区编码',
		dataIndex: 'expressBigRegionCode',
		hidden:true
	},{
		header: '修改人',
		dataIndex: 'modifyUserName'
	},{
		header: '修改人编码',
		dataIndex: 'modifyUserCode',	
		hidden:true
	},{
		header: '最后修改时间',
		dataIndex: 'modifyTime'
	},{
		header: '开始时间',
		dataIndex: 'beginTime'
	},{
		header: '结束时间',
		dataIndex: 'endTime'
	},{
		header: '状态',
		dataIndex: 'active',
		renderer:function(value){
			if(value=='Y'){
				return '有效';
			}else if(value=='N'){
				return '无效';
			}else{
				return '';
			}
		}
	},{
		header: 'ID',
		dataIndex: 'id',
		hidden:true
	}],
	viewConfig: {
		enableTextSelection: true
	},
	
	//立即激活
    immediatelyActive:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	var selectDeleteIds = [];
	 	if(selections.length < 1){
	 		Ext.MessageBox.show({
				title : '温馨提示',
				msg : '请至少选择一条数据进行操作',
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return false;
	 	}
	 	for(var i=0;i<selections.length;i++){
	 		if(selections[i].get('active')=='Y'){
		 		Ext.MessageBox.show({
					title : '温馨提示',
					msg : '请选择未激活数据进行操作',
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
		 	}else{
		 		selectDeleteIds.push(selections[i].get('id'));
		 	}
	 	}
	 	
 		me.getImmediatelyActiveWindow().selectDeleteIds = selectDeleteIds;
 		me.getImmediatelyActiveWindow().show();
	 	
	},
	//立即激活时间选择window
	immediatelyActiveWindow:null,
	getImmediatelyActiveWindow: function(){
		if(Ext.isEmpty(this.immediatelyActiveWindow)){
			this.immediatelyActiveWindow = Ext.create('Foss.baseinfo.expressBranchSalesDept.ImmediatelyActiveTimeWindow');
			this.immediatelyActiveWindow.parent = this;
		}
		return this.immediatelyActiveWindow;
	},
	
	//立即中止
    immediatelyStop:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	var selectDeleteIds = [];
	 	if(selections.length < 1){
	 		Ext.MessageBox.show({
				title : '温馨提示',
				msg : '请至少选择一条数据进行操作',
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return false;
	 	}
	 	if(selections.length>1){
	 		Ext.MessageBox.show({
				title : '温馨提示',
				msg : '请选择一条记录进行激活操作',
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return false;
	 	}
	 	if(selections[0].get('active')=='N'){
	 		Ext.MessageBox.show({
				title : '温馨提示',
				msg : '请选择已激活数据进行操作',
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return false;
	 	}
//	 	for(var i=0;i<selections.length;i++){
//	 		if(selections[i].get('active')=='N'){
//		 		Ext.MessageBox.show({
//					title : '温馨提示',
//					msg : '请选择已激活数据进行操作',
//					width : 300,
//					buttons : Ext.Msg.OK,
//					icon : Ext.MessageBox.WARNING
//				});
//				return false;
//		 	}else{
//		 		selectDeleteIds.push(selections[i].get('id'));
//		 	}
//	 	}
	 	selectDeleteIds.push(selections[0].get('id'));
	 	me.getImmediatelyStopWindow().selectDeleteIds = selectDeleteIds;
 		me.getImmediatelyStopWindow().show();
	},
	//立即中止window
    immediatelyStopWindow:null,
	getImmediatelyStopWindow: function(){
		if(Ext.isEmpty(this.immediatelyStopWindow)){
			this.immediatelyStopWindow = Ext.create('Foss.baseinfo.expressBranchSalesDept.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	
	constructor:function(config){
	
		var me = this;
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype :'button',
				text : '新增',
				columnWidth :.08,
				disabled:!baseinfo.expressBranchSalesDept.isPermission('baseinfo/addExpressBranchSalesDept'),
				hidden:!baseinfo.expressBranchSalesDept.isPermission('baseinfo/addExpressBranchSalesDept'),
				handler : function(grid, rowIndex, colIndex) {

					var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addOrUpdateExpressBranchSalesDeptWindow_Id');
					if (Ext.isEmpty(a_win)) {
						a_win = Ext.create('Foss.baseinfo.expressBranchSalesDept.addOrUpdateExpressBranchSalesDeptWindow');
					}
					
					//快递分部FORM清空数据
					var expressBranchSalesDeptConfigForm = a_win.items.items[0];
					expressBranchSalesDeptConfigForm.getForm().reset();
					//设置新增标志
					expressBranchSalesDeptConfigForm.getForm().findField('addOrUptFlag').setValue("ADD");
					//快递分部名称只读
					expressBranchSalesDeptConfigForm.getForm().findField('partName').setReadOnly(false);
					
					//营业部grid清空数据
					var expressBranchSalesDeptConfigGrid = a_win.items.items[1];
					expressBranchSalesDeptConfigGrid.store.removeAll();
					//作废新增按钮
					expressBranchSalesDeptConfigGrid.getDockedItems('toolbar[dock="top"]')[0].getComponent('expressBranchSalesDeptConfigGrid_new_ID').setVisible(true);
					expressBranchSalesDeptConfigGrid.getDockedItems('toolbar[dock="top"]')[0].getComponent('expressBranchSalesDeptConfigGrid_delete_ID').setVisible(true);
					//作废win窗体的提交按钮
					a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('addOrUpdateExpressBranchSalesDeptWindow_submit_ID').setVisible(true);
					
					a_win.show();
				}
			},{
				xtype :'button',
				text : '作废',
				columnWidth :.08,
				disabled:!baseinfo.expressBranchSalesDept.isPermission('baseinfo/deleteExpressBranchSalesDept'),
				hidden:!baseinfo.expressBranchSalesDept.isPermission('baseinfo/deleteExpressBranchSalesDept'),
				handler : function(grid, rowIndex, colIndex) {

					// grid
					var grid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
					
					// 获取选中的数据
					var selections = grid.getSelectionModel().getSelection();
					
					// 如果未选中数据，提示至少选择一条记录进行操作
					if(selections.length==0){
						Ext.MessageBox.show({
							title : '温馨提示',
							msg : '请至少选择一条数据进行操作',
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
						return false;
					}
					
					var selectDeleteIds = [];
					for(var i=0;i<selections.length;i++){
						
						if(selections[i].get('active')=='N'){
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : '只有有效的映射信息才可作废',
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
							return false;
						}
						selectDeleteIds.push(selections[i].get('id'));
					}
					
					var yesFn=function(){
						// 调用
				 		Ext.Ajax.request({
				 			url:baseinfo.realPath('deleteExpressPartSalesDept.action'),
				 			params:{
				 				'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.selectedIds':selectDeleteIds
				 			},
				 			success:function(response){
				 				//重新查询数据
				 				grid.store.loadPage(1,{
				 				      callback: function(records, operation, success) {
				 				    	  
				 				    	//抛出异常  
				 					    var rawData = grid.store.proxy.reader.rawData;
				 					    if(!success && ! rawData.isException){
				 					    	Ext.MessageBox.show({
				 								title : '温馨提示',
				 								msg : rawData.message,
				 								width : 300,
				 								buttons : Ext.Msg.OK,
				 								icon : Ext.MessageBox.WARNING
				 							});
				 							return false;
				 						}  
				 				    	
				 				    	//正常返回
				 				    	if(success){
				 				    		var result =   Ext.decode(operation.response.responseText);
				 				    		grid.show();
				 				    	}
				 				       }
				 				    }); 
				 			},
				 			exception:function(response){
				 				var result = Ext.decode(response.responseText);	
				 				Ext.MessageBox.show({
									title : '温馨提示',
									msg : result.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
				 				
				 				//重新查询数据
				 				grid.store.loadPage(1,{
				 				      callback: function(records, operation, success) {
				 				    	  
				 				    	//抛出异常  
				 					    var rawData = grid.store.proxy.reader.rawData;
				 					    if(!success && ! rawData.isException){
				 					    	Ext.MessageBox.show({
				 								title : '温馨提示',
				 								msg : rawData.message,
				 								width : 300,
				 								buttons : Ext.Msg.OK,
				 								icon : Ext.MessageBox.WARNING
				 							});
				 							return false;
				 						}  
				 				    	
				 				    	//正常返回
				 				    	if(success){
				 				    		var result =   Ext.decode(operation.response.responseText);
//				 							if(result.expressPartSalesDeptVo.saleDepartmentResultDtoList.length>0){
//				 								grid.show();
//				 							}
				 				    		grid.show();
				 				    	}
				 				       }
				 				    }); 
				 			
				 			}
				 		});	
					};
					var noFn=function(){
					 	return false;
					};
					baseinfo.expressBranchSalesDept.expressBranchSalesDeptConfirmAlert('确认是否要作废映射信息',yesFn,noFn);

				}
//			},{
//				xtype :'button',
//				text : '立即激活',
//				columnWidth :.08,
//				handler : function() {
//					me.immediatelyActive();
//				}
//			},{
//				xtype :'button',
//				text : '立即终止',
//				columnWidth :.08,
//				//handler: baseinfo.expressPartSalesDept.deleteExpressPartSalesDeptBatch
//				handler : function() {
//					me.immediatelyStop();
//				}
			},{	
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.7
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.3
			},{
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					maximumSize:500
				})
			}]
		}];	
		me.callParent();
	}
});

//立即激活Window
Ext.define('Foss.baseinfo.expressBranchSalesDept.ImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		id:'Foss_baseinfo_expressBranchSalesDept_ImmediatelyActiveTimeWindow_Id',
		title: '立即激活映射',//立即激活映射
		width:250,
		height:150,
		selectDeleteIds:null,
		closeAction: 'hide' ,
		immediatelyActiveFormPanel:null,
		getImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
	    		this.immediatelyActiveFormPanel = Ext.create('Foss.baseinfo.expressBranchSalesDept.ImmediatelyActiveFormPanel');
	    	}
	    	return this.immediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var value = '是否立即激活该映射?';
	    		me.getImmediatelyActiveFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getImmediatelyActiveFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getImmediatelyActiveFormPanel()];
			me.callParent(cfg);
		}
});

//立即激活功能Form
Ext.define('Foss.baseinfo.expressBranchSalesDept.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
//    		var date = Ext.Date.format(new Date(new Date().getTime()), 'Y-m-d H:i:s');
//    		var beginDate = form.findField('beginTime').getValue();
//    		if(beginDate < date){
//    			Ext.MessageBox.show({
//					title : '温馨提示',
//					msg : '立即激活的生效时间不能早于当前时间',
//					width : 300,
//					buttons : Ext.Msg.OK,
//					icon : Ext.MessageBox.WARNING
//				});
//				return false;
//			}
    		var selectDeleteIds = me.up('window').selectDeleteIds;
    		Ext.Ajax.request({
	 			url:baseinfo.realPath('activeImmediatelyExpressPartSalesDept.action'),
	 			params:{
	 				'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.selectedIds':selectDeleteIds
	 			},
	 			success:function(response){
	 				
	 				//隐藏立即激活win
	 				var immediatelyActiveTimeWindow = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_ImmediatelyActiveTimeWindow_Id');
	 				immediatelyActiveTimeWindow.hide();
	 				
	 				//重新查询数据
	 				var grid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
	 				grid.store.loadPage(1,{
	 				      callback: function(records, operation, success) {
	 				    	  
	 				    	//抛出异常  
	 					    var rawData = grid.store.proxy.reader.rawData;
	 					    if(!success && ! rawData.isException){
	 					    	Ext.MessageBox.show({
	 								title : '温馨提示',
	 								msg : rawData.message,
	 								width : 300,
	 								buttons : Ext.Msg.OK,
	 								icon : Ext.MessageBox.WARNING
	 							});
	 							return false;
	 						}  
	 				    	
	 				    	//正常返回
	 				    	if(success){
	 				    		var result =   Ext.decode(operation.response.responseText);
	 				    		grid.show();
	 				    	}
	 				       }
	 				    }); 
	 			},
	 			exception:function(response){
	 				var result = Ext.decode(response.responseText);	
	 				Ext.MessageBox.show({
						title : '温馨提示',
						msg : result.message,
						width : 300,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.WARNING
					});
	 				
	 				//重新查询数据
	 				var grid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
	 				grid.store.loadPage(1,{
	 				      callback: function(records, operation, success) {
	 				    	  
	 				    	//抛出异常  
	 					    var rawData = grid.store.proxy.reader.rawData;
	 					    if(!success && ! rawData.isException){
	 					    	Ext.MessageBox.show({
	 								title : '温馨提示',
	 								msg : rawData.message,
	 								width : 300,
	 								buttons : Ext.Msg.OK,
	 								icon : Ext.MessageBox.WARNING
	 							});
	 							return false;
	 						}  
	 				    	
	 				    	//正常返回
	 				    	if(success){
	 				    		var result =   Ext.decode(operation.response.responseText);
	 				    		grid.show();
	 				    	}
	 				       }
	 				    }); 
	 			
	 			}
	 		});	
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				margin:'0 0 5 5',
				width:150,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
//			},{
//				fieldLabel:'生效日期',//'生效日期',
//				name : 'beginTime',
//				xtype : 'datetimefield_date97',
//				editable:false,
//				time : true,
//				allowBlank:false,
//				id : 'Foss_regionValueAdded_activeEndTime_ID',
//				dateConfig: {
//					el : 'Foss_regionValueAdded_activeEndTime_ID-inputEl'
//				},
//				columnWidth:.9
			},{
				xtype: 'container',
				columnWidth:.25,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.25,
				text : '确认',//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.25,
				text : '取消',//"取消",
				handler : function() {
					me.up('window').hide();
				}
			},{
				xtype: 'container',
				columnWidth:.25,
				html: '&nbsp;'
			}];
        this.callParent(cfg);
    }
});

//立即中止Window
Ext.define('Foss.baseinfo.expressBranchSalesDept.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		id:'Foss_baseinfo_expressBranchSalesDept_ImmediatelyStopEndTimeWindow_Id',
		title: '立即中止映射',//立即中止映射
		width:250,
		height:150,
		selectDeleteIds:null,
		closeAction: 'hide' ,
		immediatelyStopFormPanel:null,
		getImmediatelyStopFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyStopFormPanel)){
	    		this.immediatelyStopFormPanel = Ext.create('Foss.baseinfo.expressBranchSalesDept.ImmediatelyStopFormPanel');
	    	}
	    	return this.immediatelyStopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var value = '是否立即中止该映射?';
	    		me.getImmediatelyStopFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getImmediatelyStopFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getImmediatelyStopFormPanel()];
			me.callParent(cfg);
		}
});

//立即中止功能Form
Ext.define('Foss.baseinfo.expressBranchSalesDept.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
//    		var date = Ext.Date.format(new Date(new Date().getTime()), 'Y-m-d H:i:s');
//    		var beginDate = form.findField('beginTime').getValue();
//    		if(beginDate < date){
//    			Ext.MessageBox.show({
//					title : '温馨提示',
//					msg : '立即中止的生效时间不能早于当前时间',
//					width : 300,
//					buttons : Ext.Msg.OK,
//					icon : Ext.MessageBox.WARNING
//				});
//				return false;
//			}
    		var selectDeleteIds = me.up('window').selectDeleteIds;
    		Ext.Ajax.request({
	 			url:baseinfo.realPath('inActiveImmediatelyExpressPartSalesDept.action'),
	 			params:{
	 				'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.selectedIds':selectDeleteIds
	 			},
	 			success:function(response){
	 				
	 				//隐藏立即中止win
	 				var immediatelyStopEndTimeWindow = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_ImmediatelyStopEndTimeWindow_Id');
	 				immediatelyStopEndTimeWindow.hide();
	 				
	 				//重新查询数据
	 				var grid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
	 				grid.store.loadPage(1,{
	 				      callback: function(records, operation, success) {
	 				    	  
	 				    	//抛出异常  
	 					    var rawData = grid.store.proxy.reader.rawData;
	 					    if(!success && ! rawData.isException){
	 					    	Ext.MessageBox.show({
	 								title : '温馨提示',
	 								msg : rawData.message,
	 								width : 300,
	 								buttons : Ext.Msg.OK,
	 								icon : Ext.MessageBox.WARNING
	 							});
	 							return false;
	 						}  
	 				    	
	 				    	//正常返回
	 				    	if(success){
	 				    		var result =   Ext.decode(operation.response.responseText);
	 				    		grid.show();
	 				    	}
	 				       }
	 				    }); 
	 			},
	 			exception:function(response){
	 				var result = Ext.decode(response.responseText);	
	 				Ext.MessageBox.show({
						title : '温馨提示',
						msg : result.message,
						width : 300,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.WARNING
					});
	 				
	 				//重新查询数据
	 				var grid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
	 				grid.store.loadPage(1,{
	 				      callback: function(records, operation, success) {
	 				    	  
	 				    	//抛出异常  
	 					    var rawData = grid.store.proxy.reader.rawData;
	 					    if(!success && ! rawData.isException){
	 					    	Ext.MessageBox.show({
	 								title : '温馨提示',
	 								msg : rawData.message,
	 								width : 300,
	 								buttons : Ext.Msg.OK,
	 								icon : Ext.MessageBox.WARNING
	 							});
	 							return false;
	 						}  
	 				    	
	 				    	//正常返回
	 				    	if(success){
	 				    		var result =   Ext.decode(operation.response.responseText);
	 				    		grid.show();
	 				    	}
	 				       }
	 				    }); 
	 			
	 			}
	 		});	
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				margin:'0 0 5 5',
				width:150,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
//			},{
//				fieldLabel:'中止日期',//中止日期
//				name : 'beginTime',
//				xtype : 'datetimefield_date97',
//				editable:false,
//				time : true,
//				allowBlank:false,
//				id : 'Foss_regionValueAdded_activeEndTime_ID',
//				dateConfig: {
//					el : 'Foss_regionValueAdded_activeEndTime_ID-inputEl'
//				},
//				columnWidth:.9
			},{
				xtype: 'container',
				columnWidth:.25,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.25,
				text : '确认',//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.25,
				text : '取消',//"取消",
				handler : function() {
					me.up('window').hide();
				}
			},{
				xtype: 'container',
				columnWidth:.25,
				html: '&nbsp;'
			}];
        this.callParent(cfg);
    }
});

//--------------------------------------------------快递点部营业部映射关系初始界面 end-------------------------


//-------------------------------------------快递点部营业部配置 begin------------------------------------------------------
//配置快递点部FORM
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptConfigForm',{
	extend:'Ext.form.Panel',
	title:'快递点部信息',
	frame:true,
	width : 800,
	height : 180,
	defaults:{
		margin :'15 0 0 0',
		labelWidth :100,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :2
	},
	items : [{
					xtype:'dynamicorgcombselector',
					fieldLabel:'快递点部名称',
					name:'partName',
					type:'ORG',
					expressPart:'Y',
					columnWidth:.35,
					listeners:{
						'select':function(combo,records,eOpts){
							if(records.length>0){
								var form = this.up('form').getForm();
								var record = records[0];
								if(record!=null||record!=''){
									

									form.findField('partCode').setValue(record.get('code'));
									
									//调用后台根据code获取所属快递大区编码
									Ext.Ajax.request({
										url:baseinfo.realPath('getExpressPartBigRegionByPartCode.action'),
										params:{
											'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.expressPartCode':record.get('code')
										},
										success:function(response){
											var result = Ext.decode(response.responseText);	
											form.findField('expressBigRegionName').setValue(null);	
											if(result.expressPartSalesDeptVo.orgAdministrativeInfoEntity!=null){
												form.findField('expressBigRegionName').setValue(result.expressPartSalesDeptVo.orgAdministrativeInfoEntity.name);										
											}
											
										},
										exception:function(response){
											var result = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : '温馨提示',
												msg : result.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}			
									});
								}else{
									form.findField('partCode').setValue(null);
				 					form.findField('expressBigRegionName').setValue(null);
								}
								
							}
						}
					}
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'快递点部编码',
					name:'partCode',
					readOnly:true,
					columnWidth:.35
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'所属快递大区',
					name:'expressBigRegionName',
					readOnly:true,
					columnWidth:.35
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'addOrUptFlag',
					name:'addOrUptFlag',
					hidden:true
				}]			
});

//营业部Model
Ext.define('Foss.baseinfo.expressBranchSalesDept.SalesDeptModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'code'
	},{
		name:'name'
	},{
		name:'provName'
	},{
		name:'provCode'
	},{
		name:'cityName'
	},{	
		name:'cityCode'
	},{
		name:'orgSimpleName'
	},{
		name:'orgEnSimple'
	}]
});

//营业部Store
Ext.define('Foss.baseinfo.expressBranchSalesDept.SalesDeptConfigStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressBranchSalesDept.SalesDeptModel',
//	pageSize: 20,
//	sorters: [{
//	     property: 'modifyTime',
//	     direction: 'DESC'
//	 }],
//	proxy:{
//		type:'ajax',
//		url:baseinfo.realPath('queryExpressPartSalesDept.action'),
//		actionMethods:'post',
//		reader:{
//			type:'json',
//			root:'expressPartSalesDeptVo.expressPartSalesDeptResultDtoList',
//			totalProperty:'totalCount'
//		}
//	},
//	submitParams: {},
//	setSubmitParams: function(submitParams){
//		this.submitParams = submitParams;
//	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
//		me.listeners = {
//	   		'beforeload': function(store, operation, eOpts){
//	   			Ext.apply(me.submitParams, {
//		          "limit":operation.limit,
//		          "page":operation.page,
//		          "start":operation.start
//		          }); 
//	   			Ext.apply(operation, {
//	   				params : me.submitParams 
//	   			});
//	   		} 
//		};
		me.callParent([ cfg ]);
	} 
});

//配置快递点部Grid
Ext.define('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptConfigGrid',{
	extend:'Ext.grid.Panel',
    title: '营业部信息',
    frame:true,
    width : 800,
	height : 420,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.expressBranchSalesDept.SalesDeptConfigStore'),
	columns:[{
		text:'序号',
		xtype:'rownumberer',
		width:40,
		align:'center'
	},{
		header: '部门编码',
		width:150,
		dataIndex: 'code'
	},{
		header: '部门名称',
		width:150,
		dataIndex: 'name'
	},{
		header: '省份',
		width:120,
		dataIndex: 'provName'
	},{
		header: '省份编码',
		dataIndex: 'provCode',
		hidden:true
	},{
		header: '城市',
		width:120,
		dataIndex: 'cityName'
	},{
		header: '城市编码',
		dataIndex: 'cityCode',	
		hidden:true
	}],
	viewConfig: {
		enableTextSelection: true
	},
	constructor:function(config){
		var me = this;
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype :'button',
				text : '新增',
				itemId:'expressBranchSalesDeptConfigGrid_new_ID',
				columnWidth :.06,
				handler : function(grid, rowIndex, colIndex) {

					var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addSalesDeptWindow_Id');
					if (Ext.isEmpty(a_win)) {
						a_win = Ext.create('Foss.baseinfo.expressBranchSalesDept.addSalesDeptWindow');
					}
					
					//营业部查询FORM清空数据
					var salesDeptForm = a_win.items.items[0];
					salesDeptForm.getForm().reset();
					
					//营业部grid清空数据
					var salesDeptGrid = a_win.items.items[1];
					salesDeptGrid.store.removeAll();
					
					a_win.show();
				}
			},{
				xtype :'button',
				text : '删除',
				itemId:'expressBranchSalesDeptConfigGrid_delete_ID',
				columnWidth :.06,
				handler : function(grid, rowIndex, colIndex) {

					//获取配置映射win
					var a_winConfig = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addOrUpdateExpressBranchSalesDeptWindow_Id');
				
					//获取选中的营业部数据
					var selections = a_winConfig.items.items[1].getSelectionModel().getSelection();
					//删除选中数据
					for(var i=0;i<selections.length;i++){
						a_winConfig.items.items[1].store.remove(selections[i]);
					}
					
					//重新获取store的全部数据，并排序
					var storeData = a_winConfig.items.items[1].store.data.items;
					a_winConfig.items.items[1].store.removeAll();
					
					for(var i=0;i<storeData.length;i++){
						storeData[i].index = i;
						a_winConfig.items.items[1].store.add(storeData[i]);
					}
				}
			}]
		}];	
		me.callParent();
	}
});


/**
 * 声明新增快递分部营业部映射关系windows
 */
Ext.define('Foss.baseinfo.expressBranchSalesDept.addOrUpdateExpressBranchSalesDeptWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_expressBranchSalesDept_addOrUpdateExpressBranchSalesDeptWindow_Id',
	title : '新增/修改快递分部-营业部映射',
	width : 850,
	height : 750,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptConfigForm'),
		            Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptConfigGrid')
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},	
			 items: [{
				 	xtype:'button',
					text:'返回列表',
					columnWidth:.1,
					handler : function() {
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addOrUpdateExpressBranchSalesDeptWindow_Id');
						if(!Ext.isEmpty(a_win)){
							
							//快递点部FORM清空数据
							var expressBranchSalesDeptConfigForm = a_win.items.items[0];
							expressBranchSalesDeptConfigForm.getForm().reset();
							
							//营业部grid清空数据
							var expressBranchSalesDeptConfigGrid = a_win.items.items[1];
							expressBranchSalesDeptConfigGrid.store.removeAll();
							
							//销毁当前win
							a_win.hide();
						}	
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.8
				},{
					xtype:'button',
					//cls : 'yellow_button',
					text : '提交',
					itemId:'addOrUpdateExpressBranchSalesDeptWindow_submit_ID',
					columnWidth:.1,
					handler : function() {
						
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addOrUpdateExpressBranchSalesDeptWindow_Id');
						if(!Ext.isEmpty(a_win)){
							
							//快递点部FORM数据
							var expressBranchSalesDeptConfigForm = a_win.items.items[0];
							var partCode = expressBranchSalesDeptConfigForm.getForm().findField('partCode').getValue();
							var partName = expressBranchSalesDeptConfigForm.getForm().findField('partName').getRawValue();
							var flag = expressBranchSalesDeptConfigForm.getForm().findField('addOrUptFlag').getValue();
							
							//获取营业部grid选中数据
							var expressBranchSalesDeptConfigGrid = a_win.items.items[1];
							var selections = expressBranchSalesDeptConfigGrid.store.data.items;
							
							//快递点部FORM数据不能为空
							if((partCode==null||partCode=='')
									||(partName==null||partName=='')){
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '请选择待配置的快递点部',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
						        return false;
							}
							
							//营业部Grid选中数据不能为空
							if(selections.length==0){
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '请至少选中一条带配置的营业部',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
						        return false;
							}
							
							//设置营业部数据参数和快递点部参数
							var jsonData = new Array();
							for(var i=0;i<selections.length;i++){
								jsonData.push(selections[i].data);
							}
							var expressBranchSalesDeptQueryDto = new Object();
							expressBranchSalesDeptQueryDto.saleDepartmentResultDtoList = jsonData;
							
							var expressBranchSalesDeptResultDto = new Object();
							expressBranchSalesDeptResultDto.partName = partName;
							expressBranchSalesDeptResultDto.partCode = partCode;
							expressBranchSalesDeptQueryDto.expressBranchSalesDeptResultDto = expressBranchSalesDeptResultDto;
							
							var expressBranchSalesDeptVo = new Object();
							expressBranchSalesDeptVo.expressBranchSalesDeptQueryDto = expressBranchSalesDeptQueryDto;
							
							if(flag=='ADD'){
							
								
								var expressBranchSalesDeptGrid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
								var params = expressBranchSalesDeptGrid.store.getSubmitParams();
								if(params==null||params==''){
									params = {};
									Ext.apply(params, {
										'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.expressPartCode' : null,
										'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.salesDeptCode' : null,
										'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.businessTime' : null,
										'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.active' : null
									});
									expressBranchSalesDeptGrid.store.setSubmitParams(params);
								}
								
								//调用提交后台保存
								Ext.Ajax.request({
									url:baseinfo.realPath('addExpressPartSalesDept.action'),
									jsonData:{'expressPartSalesDeptVo':expressPartSalesDeptVo},
									method:'post',
									success:function(response){
										
										//清空grid
										expressBranchSalesDeptConfigGrid.store.removeAll();
										//重置form
										expressBranchSalesDeptConfigForm.getForm().reset();
										//将win隐藏
										a_win.hide();
										
										//重新查询数据
										expressBranchSalesDeptGrid.store.loadPage(1,{
										      callback: function(records, operation, success) {
										    	  
										    	//抛出异常  
											    var rawData = expressBranchSalesDeptGrid.store.proxy.reader.rawData;
											    if(!success && ! rawData.isException){
											    	Ext.MessageBox.show({
														title : '温馨提示',
														msg : rawData.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
													return false;
												}  
										    	//正常返回
										    	if(success){
										    		var result =   Ext.decode(operation.response.responseText);
										    		expressBranchSalesDeptGrid.show();
										    	}
										       }
										    }); 	
									},
									exception:function(response){
										var result = Ext.decode(response.responseText);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : result.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
										return false;
									}			
								});
							}
						}		
					}
				}]
		}];		
		me.callParent([cfg]);
	}
})


//配置快递点部FORM
Ext.define('Foss.baseinfo.expressBranchSalesDept.ShowOrUpdateExpressBranchSalesDeptConfigForm',{
	extend:'Ext.form.Panel',
	title:'',
	frame:true,
	width : 800,
	height : 250,
	defaults:{
		margin :'20 0 0 20',
		labelWidth :100,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :2
	},
	items : [{
					xtype:'commonsaledepartmentselector',
					fieldLabel:'营业部名称',
					name:'salesDeptName',
					allowBlank:false,
					readOnly:true,
					columnWidth:.35,
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{	
					fieldLabel:'营业部部编码',
					name:'salesDeptCode',
					hidden:true,
					allowBlank:false,
					columnWidth:.35
				},{	
					xtype:'dynamicorgcombselector',
					fieldLabel:'快递点部名称',
					name:'partName',
					type:'ORG',
					expressPart:'Y',
					columnWidth:.35,
					allowBlank:false,
					listeners:{
						'select':function(combo,records,eOpts){
							if(records.length>0){
								var form = this.up('form').getForm();
								var record = records[0];
								if(record!=null||record!=''){
									
									form.findField('partCode').setValue(record.get('code'));
									
									//调用后台根据code获取所属快递大区编码
									Ext.Ajax.request({
										url:baseinfo.realPath('getExpressPartBigRegionByPartCode.action'),
										params:{
											'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.expressPartCode':record.get('code')
										},
										success:function(response){
											var result = Ext.decode(response.responseText);	
											form.findField('expressBigRegionName').setValue(null);	
											if(result.expressPartSalesDeptVo.orgAdministrativeInfoEntity!=null){
												form.findField('expressBigRegionName').setValue(result.expressPartSalesDeptVo.orgAdministrativeInfoEntity.name);										
											}
											
										},
										exception:function(response){
											var result = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : '温馨提示',
												msg : result.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}			
									});
								}else{
									form.findField('partCode').setValue(null);
				 					form.findField('expressBigRegionName').setValue(null);
								}
								
							}
						}
					}
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'快递点部编码',
					name:'partCode',
					readOnly:true,
					allowBlank:false,
					columnWidth:.35
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'所属快递大区',
					name:'expressBigRegionName',
					readOnly:true,
					//allowBlank:false,
					columnWidth:.35
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
//				},{
//					xtype:'radiogroup',
//					vertical:true,
//					allowBlank:false,
//					name:'active',
//					columnWidth:.35,
//					fieldLabel:'是否激活',//是否激活
//					items:[{
//						xtype:'radio',
//					    boxLabel:'激活',
//					    name:'active',
//					    inputValue:'Y'
//				    },{
//				    	xtype:'radio',
//					    boxLabel:'未激活',
//					    name:'active',
//					    inputValue:'N'
//					    }]	
//				},{
//					xtype:'container',
//					html:'&nbsp;',
//					height : 24,
//					columnWidth:.6
//				},{
//					xtype : 'datefield',
//					name : 'beginTime',
//					format : 'Y-m-d H:i:s',
//					readOnly:true,
//					fieldLabel :'开始时间'
//				},{
//					xtype:'container',
//					html:'&nbsp;',
//					height : 24,
//					columnWidth:.6
//				},{
//					xtype : 'datefield',
//					name : 'endTime',
//					format : 'Y-m-d H:i:s',
//					readOnly:true,
//					fieldLabel :'结束时间'
//				},{
//					xtype:'container',
//					html:'&nbsp;',
//					height : 24,
//					columnWidth:.6
				},{
					fieldLabel:'addOrUptFlag',
					name:'addOrUptFlag',
					hidden:true
				}]			
});
/**
 * 声明查看与修改快递点部营业部映射关系windows
 */
Ext.define('Foss.baseinfo.expressBranchSalesDept.showOrUpdateExpressBranchSalesDeptWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_Id',
	title : '查看/修改快递点部-营业部映射',
	width : 850,
	height : 400,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	expressBranchSalesDeptEntity:null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.expressBranchSalesDept.ShowOrUpdateExpressBranchSalesDeptConfigForm')
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 5'
			},	
			 items: [{
				 	xtype:'button',
					text:'取消',
					itemId:'Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_cancel_Id',
					columnWidth:.1,
					handler : function() {
						var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_Id');
						var showOrUpdateExpressBranchSalesDeptConfigForm = a_win.items.items[0];
						showOrUpdateExpressBranchSalesDeptConfigForm.getForm().reset();
						a_win.hide();
					}
				},{
				 	xtype:'button',
					text:'重置',
					itemId:'Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_reset_Id',
					columnWidth:.1,
					handler : function() {
						
						var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_Id');
						var expressBranchSalesDeptModel  = new Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptModel(a_win.expressBranchSalesDeptEntity);
						var showOrUpdateExpressBranchSalesDeptConfigForm = a_win.items.items[0];
						showOrUpdateExpressBranchSalesDeptConfigForm.loadRecord(expressBranchSalesDeptModel);
						
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.7
				},{
					xtype:'button',
					//cls : 'yellow_button',
					text : '提交',
					itemId:'Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_submit_Id',
					columnWidth:.1,
					handler : function() {
							
							//重新查询数据准备
							var expressBranchSalesDeptGrid = Ext.getCmp('T_baseinfo-expressBranchSalesDept_content').getExpressBranchSalesDeptGrid();
							var params = expressBranchSalesDeptGrid.store.getSubmitParams();
							if(params==null||params==''){
								params = {};
								Ext.apply(params, {
									'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.expressPartCode' : null,
									'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.salesDeptCode' : null,
									'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.businessTime' : null,
									'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.active' : null
								});
								expressBranchSalesDeptGrid.store.setSubmitParams(params);
							}
							
							
							var a_win = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_showOrUpdateExpressBranchSalesDeptWindow_Id');
							var toUpdateId = a_win.expressBranchSalesDeptEntity.id;
							
							var showOrUpdateExpressBranchSalesDeptConfigForm = a_win.items.items[0];
							var salesDeptCode = showOrUpdateExpressBranchSalesDeptConfigForm.getForm().findField('salesDeptCode').getValue();
							var expressPartCode = showOrUpdateExpressBranchSalesDeptConfigForm.getForm().findField('partCode').getValue();
							
							//调用提交后台修改
							Ext.Ajax.request({
								url:baseinfo.realPath('updateExpressPartSalesDept.action'),
								params:{
									'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.selectedIds':toUpdateId,
									'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.salesDeptCode':salesDeptCode,
									'expressPartSalesDeptVo.expressPartSalesDeptQueryDto.expressPartCode':expressPartCode
								},
								method:'post',
								success:function(response){
									
									//重置form
									showOrUpdateExpressBranchSalesDeptConfigForm.getForm().reset();
									//将win隐藏
									a_win.hide();
									
									expressBranchSalesDeptGrid.store.loadPage(1,{
									      callback: function(records, operation, success) {
									    	  
									    	//抛出异常  
										    var rawData = expressBranchSalesDeptGrid.store.proxy.reader.rawData;
										    if(!success && ! rawData.isException){
										    	Ext.MessageBox.show({
													title : '温馨提示',
													msg : rawData.message,
													width : 300,
													buttons : Ext.Msg.OK,
													icon : Ext.MessageBox.WARNING
												});
												return false;
											}  
									    	//正常返回
									    	if(success){
									    		var result =   Ext.decode(operation.response.responseText);
									    		expressBranchSalesDeptGrid.show();
									    	}
									       }
									    }); 	
								},
								exception:function(response){
									var result = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '温馨提示',
										msg : result.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									return false;
								}			
							});
					}
				}]
		}];		
		me.callParent([cfg]);
	}
})
//-------------------------------------------快递点部营业部配置 end--------------------------------------------------------

//--------------------------------------------------营业部信息界面 begin----------------------------------------------------

//营业部FORM
Ext.define('Foss.baseinfo.expressBranchSalesDept.SalesDeptForm',{
	extend:'Ext.form.Panel',
	title:'组织查询条件',
	frame:true,
	width : 750,
	height : 210,
	defaults:{
		margin :'13 0 0 0',
		labelWidth :40,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :3
	},
	items : [{
					fieldLabel:'名称',
					name:'salesDeptName',
					columnWidth:.3
				},{
					fieldLabel:'编码',
					name:'salesDeptCode',
					columnWidth:.3
				},{
					fieldLabel:'拼音',
					name:'salesDeptPinyin',
					columnWidth:.3
				},{
					provinceName:'provCode',// 省份名称—对应name
					cityName:'cityCode',// 城市name
					areaName:'countyCode',// 县name
					provinceLabel : '省份',
					cityLabel : '城市',
					areaLabel : '地区',
					//width:768,
					//hideLabel:true,
					//allowBlank:true,
					provinceWidth : 150,
					cityWidth : 150,
					areaWidth : 150,
					//nationIsBlank:true,
					provinceIsBlank:true,
					cityIsBlank:true,
					areaIsBlank:true,
					labelWid : 40,
					type : 'P-C-C',
					xtype : 'linkregincombselector',
					columnWidth:.8
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.1
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.09
				},{

					border: 1,
					xtype:'container',
					columnWidth:1,
					colspan:3,
					defaultType:'button',
					layout:'column',
					items:[{
							xtype:'container',
							border:false,
							html:'&nbsp;',
							columnWidth:.8
						},{
							text:'查询',
							columnWidth:.1,
							cls:'yellow_button',  
							handler:function(){
								var form=this.up('form');
								baseinfo.expressBranchSalesDept.salesDeptFormQuery(form);
							}
					  	},{
							 text:'重置',
							 columnWidth:.1,
							 handler:function(){
								 var form=this.up('form');
								 form.getForm().reset();
							 }
					  	}]
				
				}]			
});

//营业部Store
Ext.define('Foss.baseinfo.expressBranchSalesDept.SalesDeptStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressBranchSalesDept.SalesDeptModel',
//	pageSize: 20,
//	sorters: [{
//	     property: 'modifyTime',
//	     direction: 'DESC'
//	 }],
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('querySalesDeptResultDtoList.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'expressPartSalesDeptVo.saleDepartmentResultDtoList',
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

//营业部信息Grid
Ext.define('Foss.baseinfo.expressBranchSalesDept.SalesDeptGrid',{
	extend:'Ext.grid.Panel',
    title: '组织列表信息',
    frame:true,
    width : 750,
	height : 350,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.expressBranchSalesDept.SalesDeptStore'),
	columns:[{
		header: '名称',
		width:200,
		dataIndex: 'name'
	},{
		header: '简称',
		width:200,
		dataIndex: 'orgSimpleName'
	},{
		header: '编码',
		width:200,
		dataIndex: 'code'
	},{
		header: '省份',
		dataIndex: 'provName',
		hidden:true	
	},{
		header: '省份编码',
		dataIndex: 'provCode',
		hidden:true
	},{
		header: '城市',
		dataIndex: 'cityName',
		hidden:true	
	},{
		header: '城市编码',
		dataIndex: 'cityCode',	
		hidden:true		
	}],
	viewConfig: {
		enableTextSelection: true
	},
	constructor:function(config){
		var me = this;
		me.callParent();
	}
});

/**
 * 声明营业部信息windows
 */
Ext.define('Foss.baseinfo.expressBranchSalesDept.addSalesDeptWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_expressBranchSalesDept_addSalesDeptWindow_Id',
	title : '营业部信息',
	width : 800,
	height : 700,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.expressBranchSalesDept.SalesDeptForm'),
		            Ext.create('Foss.baseinfo.expressBranchSalesDept.SalesDeptGrid')
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},	
			 items: [{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.9
				},{
					xtype:'button',
					//cls : 'yellow_button',
					text : '确定',
					columnWidth:.1,
					handler : function() {
						
						//获取配置映射win
						var a_winConfig = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addOrUpdateExpressBranchSalesDeptWindow_Id');
						//获取营业部win
						var a_winSalesDept = Ext.getCmp('Foss_baseinfo_expressBranchSalesDept_addSalesDeptWindow_Id');
						//获取选中的营业部数据
						//var selections = a_winSalesDept.items.items[1].store.data.items;
						var selections = a_winSalesDept.items.items[1].getSelectionModel().getSelection();
						
						if(selections.length==0){
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : '请至少选中一条带配置的营业部',
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
					        return false;
						}
						
						var count = a_winConfig.items.items[1].store.data.items.length;
						for(var i=0;i<selections.length;i++){
							var flagExist = false;
							for(var j=0;j<a_winConfig.items.items[1].store.data.items.length;j++){
								if(a_winConfig.items.items[1].store.data.items[j].data.code==selections[i].data.code){
									flagExist = true;
								}
							}
							if(!flagExist){
								selections[i].index = a_winConfig.items.items[1].store.data.items.length;
								a_winConfig.items.items[1].store.add(selections[i]);
							}
						}
						//将营业部数据加入到配置界面的营业部grid的store中
					
						
						//清空营业部win的grid中数据
						a_winSalesDept.items.items[0].getForm().reset();
						a_winSalesDept.items.items[1].store.removeAll();
//						a_winSalesDept.destroy();
						
						a_winSalesDept.hide();
						
					}
				}]
		}];		
		me.callParent([cfg]);
	}
})

//--------------------------------------------------部营业信息界面 end------------------------------------------------------

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_baseinfo-expressBranchSalesDept_content')) {
		return;
	} 
	
	//查询已配置(快递点部营业部映射关系)的快递点部FORM
	var expressBranchSalesDeptQueryForm = Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptQueryForm');
	
	//已配置(快递点部营业部映射关系)的快递点部列表grid
	var expressBranchSalesDeptGrid = Ext.create('Foss.baseinfo.expressBranchSalesDept.ExpressBranchSalesDeptGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-expressBranchSalesDept_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getExpressPartSalesDeptQueryForm:function(){
			return expressBranchSalesDeptQueryForm;
		},
		getExpressBranchSalesDeptGrid:function(){
			return expressBranchSalesDeptGrid;
		},
		items: [expressBranchSalesDeptQueryForm,expressBranchSalesDeptGrid],
		renderTo : 'T_baseinfo-expressBranchSalesDept-body'
	});
});