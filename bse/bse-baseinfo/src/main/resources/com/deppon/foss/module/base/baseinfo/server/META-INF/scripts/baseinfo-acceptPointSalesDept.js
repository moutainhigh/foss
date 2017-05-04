
//登陆用户员工号
baseinfo.acceptPointSalesDept.currentEmpCode = FossUserContext.getCurrentUserEmp().empCode;
//登录员工名
baseinfo.acceptPointSalesDept.currentEmpName = FossUserContext.getCurrentUserEmp().empName;

baseinfo.acceptPointSalesDept.data=null;
/**
  * 转换long类型为日期
  *@param value 要转换的时间
  */
baseinfo.acceptPointSalesDept.longToDateConvert = function(value) {
	if (!Ext.isEmpty(value)) {
		return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	} else {
		return null;
	}
}

/**
 * 跳出提示对话框
 */
baseinfo.acceptPointSalesDept.acceptPointSalesDeptConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};


//查询已配置(接驳点营业部映射关系) 供查询页面使用 
baseinfo.acceptPointSalesDept.acceptPointSalesDeptQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var acceptPointCode = form.findField('acceptPointCode').getValue();
	var bigRegion = form.findField('bigRegion').getValue();
	var transferCode = form.findField('transferCode').getValue();
	var status = form.findField('status').getValue();
	
	// 设置参数
	params = {};
	Ext.apply(params, {
		'acceptPointSalesDeptDto.acceptPointEntity.acceptPointCode' : acceptPointCode,
		'acceptPointSalesDeptDto.acceptPointEntity.bigRegion' : bigRegion,
		'acceptPointSalesDeptDto.acceptPointEntity.transferCode' : transferCode,
		'acceptPointSalesDeptDto.acceptPointEntity.status' : status
		
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-acceptPointSalesDept_content').getAcceptPointSalesDeptGrid();
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
	    		var result =  Ext.decode(operation.response.responseText);
					grid.show();
	    	}
	       }
	    }); 
}

// 根据营业区编码查询下面的营业部的方法 供新增页面使用
baseinfo.acceptPointSalesDept.salesDeptQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var smallRegion = form.findField('smallRegion').getValue();
	var smallRegionName = form.findField('smallRegionName').getValue();
	var acceptPointCode = form.findField('acceptPointCode').getValue();
	if(smallRegion==null||smallRegion==''){
		return;
	}
	if(acceptPointCode==null||acceptPointCode==''){
		return;
	}
	// 设置参数
	params = {};
	Ext.apply(params, {
		'acceptPointSalesDeptDto.smallRegion' : smallRegion,
		'acceptPointSalesDeptDto.smallRegionName' : smallRegionName,
		'acceptPointSalesDeptDto.acceptPointCode' : acceptPointCode
		
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('Foss_baseinfo_acceptPointSalesDept_addOrUpdateAcceptPointSalesDeptWindow_Id').getSalesDeptContainer().getSmallRegionSalesDeptGrid();
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.load({
		scope: this,
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
		    	me.up().getSalesDeptContainer().getSmallRegionSalesDeptGrid().getStore().removeAll();
				return false;
			}  
	    	
	    	//正常返回
	    	if(success){
	    		var result =  Ext.decode(operation.response.responseText);
					grid.show();
	    	}
	    }
	});
}

//标记新增修改界面已选列表是否被修改
var editFlag = false;

//进入已配置快递点部营业部映射关系的修改界面
baseinfo.acceptPointSalesDept.editDetialAcceptPointSalesDept = function(grid, rowIndex, colIndex){

	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	//只有未启用的才可以修改
	if(selection.data.status=='Y'){
		Ext.MessageBox.show({
			title : '温馨提示',
			msg : '请选择未启用的数据进行操作',
			width : 300,
			buttons : Ext.Msg.OK,
			icon : Ext.MessageBox.WARNING
		});
		return false;
	}
	//获取显示弹出win
	var a_win = grid.up('grid').getAddOrUpateWindow();
	if(a_win.dockedItems.items[a_win.dockedItems.items.length-1].items.items[4]){
		a_win.dockedItems.items[a_win.dockedItems.items.length-1].items.items[4].setDisabled(false);
	}
	//接驳点FORM清空数据
	var acceptPointSalesDeptConfigForm = a_win.getAcceptPointSalesDeptConfigForm();
	//获取修改人姓名
	modifyUserName=baseinfo.acceptPointSalesDept.currentEmpName;
	// 设置操作人的名称
	acceptPointSalesDeptConfigForm.getForm().findField('modifyUserName').setValue(modifyUserName);
	
	// 把接驳点和经营大区、营业区设置为只读
	acceptPointSalesDeptConfigForm.getForm().findField('acceptPointName').setReadOnly(true);
	acceptPointSalesDeptConfigForm.getForm().findField('bigRegionName').setReadOnly(true);
	acceptPointSalesDeptConfigForm.getForm().findField('smallRegionName').setReadOnly(false);
	//接驳点映射FORM加载数据
	var acceptPointSalesDeptModel  = new Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel(selection.data);
	var showDetialAcceptPointSalesDeptForm = a_win.items.items[0];
	showDetialAcceptPointSalesDeptForm.loadRecord(acceptPointSalesDeptModel);
	//设置修改标志
	acceptPointSalesDeptConfigForm.getForm().findField('addOrUptFlag').setValue('UPDATE');
	var modifyUserName=baseinfo.acceptPointSalesDept.currentEmpName;
	acceptPointSalesDeptConfigForm.getForm().findField('modifyUserName').setValue(modifyUserName);
	// 供重置按钮使用
	a_win.acceptPointEntity = selection.data;
	
	//营业区对应的营业部列表清空数据
	var acceptPointSalesDeptContainer = a_win.getSalesDeptContainer();
	acceptPointSalesDeptContainer.getSmallRegionSalesDeptGrid().getStore().removeAll();
	var selectedSmallRegionSalesDeptGrid = acceptPointSalesDeptContainer.getSelectedSmallRegionSalesDeptGrid();
	selectedSmallRegionSalesDeptGrid.getStore().removeAll();
	
	// 修改时也给营业区赋值，调用后台根据大区code获取营业区信息 给store设置参数 
	var bigRegion = selection.data.bigRegion;
	param = {};
	Ext.apply(param, {
		'acceptPointSalesDeptDto.bigRegion' : bigRegion
	});
	acceptPointSalesDeptConfigForm.getForm().findField('smallRegionName').getStore().setSubmitParams(param);
	//查询
	acceptPointSalesDeptConfigForm.getForm().findField('smallRegionName').getStore().load({
		scope: this,
		callback: function(records, operation, success) {
			//抛出异常  
		    var rawData = acceptPointSalesDeptConfigForm.getForm().findField('smallRegionName').store.proxy.reader.rawData;
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
	    		var result =  Ext.decode(operation.response.responseText);
	    		acceptPointSalesDeptConfigForm.getForm().findField('smallRegionName').show();
	    	}
	    }
	});
	
	
	
	// 清空完以后加载要修改的数据  
	var smallRegion = selection.data.smallRegion;
	var acceptPointCode = selection.data.acceptPointCode;
	// 设置参数
	params = {};
	Ext.apply(params, {
		'acceptPointSalesDeptDto.smallRegion' : smallRegion,
		'acceptPointSalesDeptDto.acceptPointCode' : acceptPointCode
		
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('Foss_baseinfo_acceptPointSalesDept_addOrUpdateAcceptPointSalesDeptWindow_Id').getSalesDeptContainer().getSelectedSmallRegionSalesDeptGrid();
	grid.store.setSubmitParams(params);
	
	//查询
	grid.store.load({
		scope: this,
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
	    		var result =  Ext.decode(operation.response.responseText);
					grid.show();
	    	}
	    }
	});
	editFlag = false;
	//显示接驳点营业部映射关系修改界面
	a_win.show();

}

//进入已配置快递点部营业部映射关系的查看界面
baseinfo.acceptPointSalesDept.showDetialAcceptPointSalesDept = function(grid, rowIndex, colIndex){

	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	//获取显示弹出win
	var a_win = Ext.getCmp('Foss_baseinfo_expressPartSalesDept_showOrupdateAcceptPointSalesDeptWindow_Id');
	if (Ext.isEmpty(a_win)) {
		a_win = Ext.create('Foss.baseinfo.acceptPointSalesDept.showOrupdateAcceptPointSalesDeptWindow');
	}
	//接驳点映射FORM加载数据
	var acceptPointSalesDeptModel  = new Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel(selection.data);
	var showDetialAcceptPointSalesDeptForm = a_win.items.items[0];
	showDetialAcceptPointSalesDeptForm.loadRecord(acceptPointSalesDeptModel);
	showDetialAcceptPointSalesDeptForm.getForm().findField('createDate').setValue(selection.data.createDate);
	if(selection.data.modifyDate){
		showDetialAcceptPointSalesDeptForm.getForm().findField('modifyDate').setValue(selection.data.modifyDate);
	}
	//接驳点子类grid加载数据
	var showDetialAcceptPointSalesDeptGrid = a_win.items.items[1];
	
	showDetialAcceptPointSalesDeptGrid.getStore().loadData(selection.data.childrenSalesDeptEntitys);
	//显示快递点部营业部映射关系配置界面
	a_win.show();
}


//作废
baseinfo.acceptPointSalesDept.deleteAcceptPointSalesDept = function(grid, rowIndex, colIndex){

	// grid
	var grid = Ext.getCmp('T_baseinfo-acceptPointSalesDept_content').getAcceptPointSalesDeptGrid();
	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	// 调用
	Ext.Ajax.request({
			url:baseinfo.realPath('deleteAcceptPointSalesDept.action'),
			params:{
				'acceptPointSalesDeptDto.id':selection.data.id
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
				    		grid.show();
				    	}
				       }
				    }); 
			}
		});
}

//---------------------------------------------------以上为方法实现区------------------------------------
//--------------------------------------------------快递点部营业部映射关系初始界面 begin-------------------------

Ext.define('Foss.baseinfo.acceptPointSalesDept.ActiveStore', {
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

//查询(接驳点营业部映射关系)的FORM
Ext.define('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptQueryForm',{
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
					xtype:'accesspointselector',
					fieldLabel:'接驳点名称',
					name:'acceptPointCode',
					labelWidth :80,
					columnWidth:.26
				},{
					xtype:'bigregionselector',
					fieldLabel:'经营大区',
					name:'bigRegion',
					labelWidth :80,
					columnWidth:.27
				},{
					xtype:'transfercenterselector',
					fieldLabel:'出发中转场',
					name:'transferCode',
					labelWidth :80,
					columnWidth:.27
				},{
					xtype : 'combobox',
					name : 'status',
					fieldLabel : '状态',
					labelWidth :70,
					columnWidth : .20,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : Ext.create('Foss.baseinfo.acceptPointSalesDept.ActiveStore', {
						data : {
							'items' : [{
									'valueCode' : '',
									'valueName' : '全部'
								}, {
									'valueCode' : 'N',
									'valueName' : '未启用'
								}, {
									'valueCode' : 'Y',
									'valueName' : '启用'
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
						text:baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.reset'), //重置
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
						text : baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.query'),//查询
						cls:'yellow_button',
						handler : function() {
							var form=this.up('form');
							baseinfo.acceptPointSalesDept.acceptPointSalesDeptQuery(form);
						}
					}]
				
				}]			
});

//已配置(快递点部营业部映射关系)的快递点部Store的Model
Ext.define('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'  //ID
	},{
		name:'status' //是否启用
	},{
		name:'acceptPointCode' //接驳点编码
	},{
		name:'acceptPointName' //接驳点名称
	},{
		name:'bigRegion' //经营大区编码
	},{
		name:'bigRegionName' //经营大区名称
	},{
		name:'smallRegion' //营业区编码
	},{
		name:'smallRegionName' //营业区名称
	},{
		name:'transferCode' //中转场编码
	},{
		name:'transferName'  //中转场名称
	},{
		name:'createDate', // 创建时间
		type:'Date',
		convert:baseinfo.acceptPointSalesDept.longToDateConvert
	},{	
		name:'createUserName' //创建人
	},{
		name:'createUserCode' //创建人工号
	},{
		name:'modifyDate', //更新时间
		type:'Date',
		convert:baseinfo.acceptPointSalesDept.longToDateConvert
	},{	
		name:'modifyUserName' //修改人
	},{
		name:'modifyUserCode' //修改人工号
	},{
		name:'active' //是否作废
	},{
		name:'childrenSalesDeptEntitys'
	}]
});

//(接驳点营业部映射关系)的Store
Ext.define('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel',
	pageSize: 20,
	sorters: [{
	     property: 'modifyDate',
	     direction: 'DESC'
	 }],
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryAcceptPointSalesDepts.action'),  //放置查询方法
		actionMethods:'post',
		reader:{
			type:'json',
			root:'acceptPointSalesDeptDto.acceptPointEntitys',
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

//已配置(接驳点营业部映射关系)的快递点部列表
Ext.define('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptGrid',{
	extend:'Ext.grid.Panel',
    title: '接驳点与营业部映射列表',
    frame:true,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:'操作',
    	width:120,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',
			tooltip:'修改',
			getClass : function (v, m, r, rowIndex) {
				if (r.get('status') === 'N'&&baseinfo.acceptPointSalesDept.isPermission('baseinfo/updateAcceptPointSalesDept')) {
				    return 'deppon_icons_edit';
				} else {
				    return 'statementBill_hide';
				}
			},
			handler:function(grid, rowIndex, colIndex){
				baseinfo.acceptPointSalesDept.editDetialAcceptPointSalesDept(grid, rowIndex, colIndex);
			}
    	},{
    		iconCls : 'deppon_icons_delete',
			tooltip:'删除',
			getClass : function (v, m, r, rowIndex) {
				if (r.get('status') === 'N'&&baseinfo.acceptPointSalesDept.isPermission('baseinfo/updateAcceptPointSalesDept')) {
				    return 'deppon_icons_delete';
				} else {
				    return 'statementBill_hide';
				}
			},
			handler:function(grid, rowIndex, colIndex){
				Ext.Msg.confirm(baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.notice'),baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
					Ext.MessageBox.buttonText.yes = baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.confirm');
					Ext.MessageBox.buttonText.no = baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.cancel');
					if(e == 'yes'){//询问是否删除
						baseinfo.acceptPointSalesDept.deleteAcceptPointSalesDept(grid, rowIndex, colIndex);
					}
				});
			}
    	},{
    		iconCls : 'deppon_icons_showdetail',
			tooltip:'查看',
			handler:function(grid, rowIndex, colIndex){
				baseinfo.acceptPointSalesDept.showDetialAcceptPointSalesDept(grid, rowIndex, colIndex);
			}
    	}]
	},{
		header: '接驳点名称',
		width : 200,
		dataIndex: 'acceptPointName'
	},{
		header: '接驳点code',
		dataIndex: 'acceptPointCode',
		hidden:true
	},{
		header: '经营大区',
		width : 120,
		dataIndex: 'bigRegionName'
	},{
		header: '经营大区编码',
		dataIndex: 'bigRegion',
		hidden:true
	},{
		header: '出发中转场',
		width : 120,
		dataIndex: 'transferName'
	},{
		header: '出发中转场编码',
		dataIndex: 'transferCode',
		hidden:true
	},{
		header: '状态',
		width : 90,
		dataIndex: 'status',
		renderer:function(value){
			if(value=='Y'){
				return '启用';
			}else if(value=='N'){
				return '未启用';
			}else{
				return '';
			}
		}
	},{
		header: '创建时间',
		width : 150,
		dataIndex: 'createDate'
	},{
		header: '创建人',
		width : 90,
		dataIndex: 'createUserName'
	},{
		header: '最后一次修改时间',
		width : 150,
		dataIndex: 'modifyDate'
	},{
		header: '修改人',
		width : 90,
		dataIndex: 'modifyUserName'
	},{
		header: '修改人编码',
		dataIndex: 'modifyUserCode',	
		hidden:true
	},{
		header: 'ID',
		dataIndex: 'id',
		hidden:true
	},{
		header: '创建人编码',
		dataIndex: 'createUserCode',	
		hidden:true
	}],
	viewConfig: {
		enableTextSelection: true
	},
	//创建新增修改的window
	addOrUpateWindow : null,
	getAddOrUpateWindow : function(){
		if(Ext.isEmpty(this.addOrUpateWindow)){
			this.addOrUpateWindow = Ext.create('Foss.baseinfo.acceptPointSalesDept.addOrupdateAcceptPointSalesDeptWindow');
		}
		return this.addOrUpateWindow;
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
				disabled:!baseinfo.acceptPointSalesDept.isPermission('baseinfo/addAcceptPointSalesDept'),
				hidden:!baseinfo.acceptPointSalesDept.isPermission('baseinfo/addAcceptPointSalesDept'),
				handler : function(grid, rowIndex, colIndex) {
					if(editFlag){
						editFlag = false;
					}
					var a_win = me.getAddOrUpateWindow();
					//接驳点FORM清空数据
					var acceptPointSalesDeptConfigForm = a_win.getAcceptPointSalesDeptConfigForm();
					acceptPointSalesDeptConfigForm.getForm().reset();
					if(a_win.dockedItems.items[a_win.dockedItems.items.length-1].items.items[4]){
						a_win.dockedItems.items[a_win.dockedItems.items.length-1].items.items[4].setDisabled(false);
					}
					modifyUserName=baseinfo.acceptPointSalesDept.currentEmpName;
					// 设置操作人的名称
					acceptPointSalesDeptConfigForm.getForm().findField('modifyUserName').setValue(modifyUserName);
					//设置新增标志
					acceptPointSalesDeptConfigForm.getForm().findField('addOrUptFlag').setValue("ADD");
					// 把接驳点和经营大区、营业区设置为非只读
					acceptPointSalesDeptConfigForm.getForm().findField('acceptPointName').setReadOnly(true);
					acceptPointSalesDeptConfigForm.getForm().findField('bigRegionName').setReadOnly(false);
					acceptPointSalesDeptConfigForm.getForm().findField('smallRegionName').setReadOnly(true);
					// 清空新增界面接驳点store里的值
					acceptPointSalesDeptConfigForm.getForm().findField('acceptPointName').getStore().removeAll();
					// 清空新增界面营业区store里的值
					acceptPointSalesDeptConfigForm.getForm().findField('smallRegionName').getStore().removeAll();
					//营业区对应的营业部列表清空数据
					var acceptPointSalesDeptContainer = a_win.getSalesDeptContainer();
					acceptPointSalesDeptContainer.getSmallRegionSalesDeptGrid().getStore().removeAll();
					acceptPointSalesDeptContainer.getSelectedSmallRegionSalesDeptGrid().getStore().removeAll();
					a_win.show();
				}
			},{
				xtype :'button',
				text : '禁用',
				columnWidth :.08,
				disabled:!baseinfo.acceptPointSalesDept.isPermission('baseinfo/notEnabledAcceptPointSalesDept'),
				hidden:!baseinfo.acceptPointSalesDept.isPermission('baseinfo/notEnabledAcceptPointSalesDept'),
				handler : function(grid, rowIndex, colIndex) {

					// grid
					var grid = Ext.getCmp('T_baseinfo-acceptPointSalesDept_content').getAcceptPointSalesDeptGrid();
					
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
						
						if(selections[i].get('status')=='N'){
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : '该信息已禁用，不需要再次禁用',
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
				 			url:baseinfo.realPath('updateAcceptPointSalesDeptStatus.action'),
				 			params:{
				 				'acceptPointSalesDeptDto.idList':selectDeleteIds,
				 				'acceptPointSalesDeptDto.status':'N'
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
					baseinfo.acceptPointSalesDept.acceptPointSalesDeptConfirmAlert('确认是否要禁用映射信息',yesFn,noFn);
				}
			},{
				xtype :'button',
				text : '启用',
				columnWidth :.08,
				disabled:!baseinfo.acceptPointSalesDept.isPermission('baseinfo/enabledAcceptPointSalesDept'),
				hidden:!baseinfo.acceptPointSalesDept.isPermission('baseinfo/enabledAcceptPointSalesDept'),
				handler : function(grid, rowIndex, colIndex) {

					// grid
					var grid = Ext.getCmp('T_baseinfo-acceptPointSalesDept_content').getAcceptPointSalesDeptGrid();
					
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
						
						if(selections[i].get('status')=='Y'){
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : '该信息已启用，不需要再次启用',
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
				 			url:baseinfo.realPath('updateAcceptPointSalesDeptStatus.action'),
				 			params:{
				 				'acceptPointSalesDeptDto.idList':selectDeleteIds,
				 				'acceptPointSalesDeptDto.status':'Y'
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
					baseinfo.acceptPointSalesDept.acceptPointSalesDeptConfirmAlert('确认是否要启用映射信息',yesFn,noFn);
				}
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

//--------------------------------------------------接驳点营业部映射关系初始界面 end-------------------------


//-------------------------------------------接驳点营业部配置 begin------------------------------------------------------

/**
 * 已配置(快递点部营业部映射子类实体)的快递点部Store的Model
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesChildrenDeptModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'  //ID
	},{
		name:'status' //是否启用
	},{
		name:'smallRegion' //营业区编码
	},{
		name:'smallRegionName' //营业区名称
	},{
		name:'salesDepartmentCode' //营业部编码
	},{
		name:'salesDepartmentName' //营业部名称
	},{
		name:'active' //是否作废
	}]
});

/**
 * 创建一个营业区对应营业部的store(待选营业部信息store)
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.SmallRegionSalesDeptStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesChildrenDeptModel',
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('querySalesDeptBySmallRegion.action'),  //放置查询方法
		actionMethods:'post',
		reader:{
			type:'json',
			root:'acceptPointSalesDeptDto.childAcceptPointEntitys'
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
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

/**
 * 创建一个营业区对应营业部的store(已选营业部信息的的store)
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.SelectedSmallRegionSalesDeptStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesChildrenDeptModel',
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryChildrenDeptByAcceptSmallCode.action'),  //放置查询方法
		actionMethods:'post',
		reader:{
			type:'json',
			root:'acceptPointSalesDeptDto.childAcceptPointEntitys'
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
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

/**
 * 营业部列表
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.SmallRegionSalesDeptGrid',{
	extend : 'Ext.grid.Panel',
	// 表格对象增加一个边框
	frame : true,
	height : 190,
	parent : null,
	flex:1,
	// 定义表格的标题
	title : '营业部列表',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 定义表格列信息
	columns : [ {
				// 字段标题
				header : '营业区',
				// 关联model中的字段名
				dataIndex : 'smallRegionName',
				xtype : 'ellipsiscolumn',
				width : 120
			},{
				// 字段标题
				header : '营业区编码',
				// 关联model中的字段名
				dataIndex : 'smallRegion',
				hidden:true,
				width : 120
			},{
				// 字段标题
				header : '部门名称',
				// 关联model中的字段名
				dataIndex : 'salesDepartmentName',
				xtype : 'ellipsiscolumn',
				width : 160
			},{
				// 字段标题
				header : '部门编码',
				// 关联model中的字段名
				dataIndex : 'salesDepartmentCode',
				hidden:true,
				width : 120
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.acceptPointSalesDept.SmallRegionSalesDeptStore');
				
		me.callParent([ cfg ]);
	}
	});


/**
 * 已选营业部列表
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.SelectedSmallRegionSalesDeptGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	height : 190,
	parent : null,
	flex:1,
	// 定义表格的标题
	title : '已选营业部列表',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 定义表格列信息
	columns : [ {
		// 字段标题
		header : 'ID',
		// 关联model中的字段名
		dataIndex : 'id',
		hidden: true,
		width : 120
	},{
		// 字段标题
		header : '营业区',
		// 关联model中的字段名
		dataIndex : 'smallRegionName',
		xtype : 'ellipsiscolumn',
		width : 120
	},{
		// 字段标题
		header : '营业区编码',
		// 关联model中的字段名
		dataIndex : 'smallRegion',
		hidden:true,
		width : 120
	},{
		// 字段标题
		header : '部门名称',
		// 关联model中的字段名
		dataIndex : 'salesDepartmentName',
		xtype : 'ellipsiscolumn',
		width : 160
	},{
		// 字段标题
		header : '部门编码',
		// 关联model中的字段名
		dataIndex : 'salesDepartmentCode',
		hidden:true,
		width : 120
	}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.acceptPointSalesDept.SelectedSmallRegionSalesDeptStore');
				
		me.callParent([ cfg ]);
	}
	});

/**
 * 定义添加营业部界面
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.SalesDeptContainer',{
	extend: 'Ext.container.Container',
	layout : 'hbox',
	width : 810,
	height:200,
	border : 1,
	//待选择营业区对应营业部列表
	smallRegionSalesDeptGrid : null,
	getSmallRegionSalesDeptGrid : function(){
		if(Ext.isEmpty(this.smallRegionSalesDeptGrid)){
			this.smallRegionSalesDeptGrid = Ext.create('Foss.baseinfo.acceptPointSalesDept.SmallRegionSalesDeptGrid');
		}
		return this.smallRegionSalesDeptGrid;
	},
	//营业区对应营业部操作按钮
	operatSoureContainer : null,
	getOperatSoureContainer : function(){
		var me = this;
		if(Ext.isEmpty(this.operatSoureContainer)){
			this.operatSoureContainer = Ext.create('Ext.container.Container',{
				flex:0.2,
				buttonAlign : 'center',
				layout : 'column',
				items : [{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:15px;border:none',
						hide:true
					},{
						columnWidth : 1,
					    xtype : 'button',
						text:'-->',
						handler : function(){
							//获得选择对象
							var sourceSelModel = me.getSmallRegionSalesDeptGrid().getSelectionModel();
							var modelLength = sourceSelModel.getSelection().length;
							if(modelLength > 0){
								editFlag = true;
								for(var i = 0; i < modelLength;i++){
									var salesDepartmentName = sourceSelModel.getSelection()[i].data.salesDepartmentName;
									var salesDepartmentCode = sourceSelModel.getSelection()[i].data.salesDepartmentCode;
									var smallRegion = sourceSelModel.getSelection()[i].data.smallRegion;
									var smallRegionName = sourceSelModel.getSelection()[i].data.smallRegionName;
									//创建一个新的Model
									var record = Ext.create('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel');
									record.set('salesDepartmentName',salesDepartmentName);
									record.set('salesDepartmentCode',salesDepartmentCode);
									record.set('smallRegion',smallRegion);
									record.set('smallRegionName',smallRegionName);
									
									var record1 = me.getSelectedSmallRegionSalesDeptGrid().getStore().findRecord('salesDepartmentCode',salesDepartmentCode);
									if(record1 == null){//判断已选营业部列表是否存在该记录
										//把选中的记录添加到已选营业部列表中
										me.getSelectedSmallRegionSalesDeptGrid().getStore().add(record);
									}
								}
								var records = new Array();
								for(var i = 0; i < modelLength;i++){
									records[i] = sourceSelModel.getSelection()[i];
								}
								//删除选中的记录
								me.getSmallRegionSalesDeptGrid().getStore().remove(records);
							}
						}
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'->>',
						handler : function(){
							//获得选择对象
							var store = me.getSmallRegionSalesDeptGrid().getStore();
							store.each(function(record){
								if(!editFlag){
									editFlag = true;
								}
								var record1 = me.getSelectedSmallRegionSalesDeptGrid().getStore().findRecord('salesDepartmentCode',record.get('salesDepartmentCode'));
								if(record1 == null){//判断已选营业部列表是否存在该记录
									//把选中的记录添加到已选营业部列表中
									me.getSelectedSmallRegionSalesDeptGrid().getStore().add(record);
								}
							});
							me.getSmallRegionSalesDeptGrid().getStore().removeAll();
                        }
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'<--',
						handler : function(){
							//获得选择对象
							var selectedSelModel = me.getSelectedSmallRegionSalesDeptGrid().getSelectionModel();
							var modelLength = selectedSelModel.getSelection().length;
							if(modelLength > 0){							
								editFlag = true;
								var records = new Array();
								for(var i = 0; i < modelLength;i++){
									records[i] = selectedSelModel.getSelection()[i];
									var salesDepartmentName = records[i].data.salesDepartmentName;
									var salesDepartmentCode = records[i].data.salesDepartmentCode;
									var smallRegion = records[i].data.smallRegion;
									var smallRegionName = records[i].data.smallRegionName;
									//创建一个新的Model
									var record = Ext.create('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel');
									record.set('salesDepartmentName',salesDepartmentName);
									record.set('salesDepartmentCode',salesDepartmentCode);
									record.set('smallRegion',smallRegion);
									record.set('smallRegionName',smallRegionName);
									
									var record1 = me.getSmallRegionSalesDeptGrid().getStore().findRecord('salesDepartmentCode',salesDepartmentCode);
									if(record1 == null){//判断营业部列表是否存在该记录
										//把选中的记录添加到营业部列表中
										me.getSmallRegionSalesDeptGrid().getStore().add(record);
									}
								}
								//删除选中的记录
								me.getSelectedSmallRegionSalesDeptGrid().getStore().remove(records);
							}
                        }
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'<<-',
						handler : function(){
								//获得选择对象
								var store = me.getSelectedSmallRegionSalesDeptGrid().getStore();
								store.each(function(record){
									if(!editFlag){
										editFlag = true;
									}
									var record1 = me.getSmallRegionSalesDeptGrid().getStore().findRecord('salesDepartmentCode',record.get('salesDepartmentCode'));
									if(record1 == null){//判断营业部列表是否存在该记录
										//把选中的记录添加到营业部列表中
										me.getSmallRegionSalesDeptGrid().getStore().add(record);
									}
								});								//清除所有数据
								me.getSelectedSmallRegionSalesDeptGrid().getStore().removeAll();
                        }
					}]
			});
		}
		return this.operatSoureContainer;		
	},
	//已选择营业部列表
	selectedSmallRegionSalesDeptGrid : null,
	getSelectedSmallRegionSalesDeptGrid : function(){
		if(Ext.isEmpty(this.selectedSmallRegionSalesDeptGrid)){
			this.selectedSmallRegionSalesDeptGrid = Ext.create('Foss.baseinfo.acceptPointSalesDept.SelectedSmallRegionSalesDeptGrid');
		}
		return this.selectedSmallRegionSalesDeptGrid;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		this.items = [ 
		    me.getSmallRegionSalesDeptGrid(),
		    me.getOperatSoureContainer(),
			me.getSelectedSmallRegionSalesDeptGrid()
		];
		me.callParent([cfg]);
	}
});

//接驳点下拉框store
Ext.define('Foss.baseinfo.acceptPointSalesDept.AcceptPointStore', {
	extend : 'Ext.data.Store',
	model:'Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel',
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryAcceptPointTransferByBigRegion.action'),  //放置查询方法
		actionMethods:'post',
		reader:{
			type:'json',
			root:'acceptPointSalesDeptDto.acceptPointEntitys'
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
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

//营业区下拉框store
Ext.define('Foss.baseinfo.acceptPointSalesDept.SalesDeptStore', {
	extend : 'Ext.data.Store',
	model:'Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel',
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('querySmallRegionInfoByBigRegion.action'),  //放置查询方法
		actionMethods:'post',
		reader:{
			type:'json',
			root:'acceptPointSalesDeptDto.childAcceptPointEntitys'
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
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});

//新增或者修改的接驳点FORM
Ext.define('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptConfigForm',{
	extend:'Ext.form.Panel',
	title:'查询条件',
	frame:true,
	width : 800,
	height : 180,
	defaults:{
		margin :'10 0 0 10',
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :4
	},
	items : [{
					xtype:'bigregionselector',
					fieldLabel:'经营大区',
					name:'bigRegionName',
					labelWidth :60,
					columnWidth:.25,
					listeners:{
						'select':function(combo,records,eOpts){
							if(records.length>0){
								var form = this.up('form').getForm();
								var record = records[0];
								if(record!=null && record!=''){
									form.findField('bigRegion').setValue(record.get('code'));
									//设置参数
									params = {};
									Ext.apply(params, {
										'acceptPointSalesDeptDto.bigRegion':record.get('code')
									});
									form.findField('acceptPointName').setReadOnly(false);
									// 调用后台根据大区code获取接驳点中转场信息 给store设置参数 
									form.findField('acceptPointName').getStore().setSubmitParams(params);
									//查询
									form.findField('acceptPointName').getStore().load({
										scope: this,
										callback: function(records, operation, success) {
											//抛出异常  
										    var rawData = form.findField('acceptPointName').store.proxy.reader.rawData;
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
									    		var result =  Ext.decode(operation.response.responseText);
									    		form.findField('acceptPointName').show();
									    	}
									    }
									});
									form.findField('acceptPointName').focus();
									form.findField('acceptPointName').expand();
									
									// 调用后台根据大区code获取营业部信息 给store设置参数 
									form.findField('smallRegionName').getStore().setSubmitParams(params);
									//查询
									form.findField('smallRegionName').getStore().load({
										scope: this,
										callback: function(records, operation, success) {
											//抛出异常  
										    var rawData = form.findField('smallRegionName').store.proxy.reader.rawData;
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
									    		var result =  Ext.decode(operation.response.responseText);
									    		form.findField('smallRegionName').show();
									    	}
									    }
									});
									
								}
							}
						}
					}
				},{
					fieldLabel:'经营大区名称',
					name:'bigRegion',
					hidden:true
				},{
					xtype:'combobox',
					fieldLabel:'接驳点',
					name:'acceptPointName',
					labelWidth :50,
					columnWidth:.25,
					editable : false,
					displayField : 'acceptPointName',
					valueField : 'acceptPointCode',
					store : Ext.create('Foss.baseinfo.acceptPointSalesDept.AcceptPointStore'),
					listeners:{
						'select':function(combo,records,eOpts){
							if(records.length>0){
								var form = this.up('form').getForm();
								var record = records[0];
								if(record!=null && record!=''){
									form.findField('acceptPointCode').setValue(record.get('acceptPointCode'));
									//设置中转场的值
									form.findField('transferName').setValue(record.get('transferName'));
									//设置中场场编码的值
									form.findField('transferCode').setValue(record.get('transferCode'));
								}
								form.findField('smallRegionName').setReadOnly(false);
								form.findField('smallRegionName').focus();
								form.findField('smallRegionName').expand();
							}
						}
					}
				},{
					fieldLabel:'接驳点编码',
					name:'acceptPointCode',
					hidden:true
				},{
					fieldLabel:'出发中转场',
					name:'transferName',
					readOnly:true,
					labelWidth :80,
					columnWidth:.25
				},{
					fieldLabel:'出发中转场编码',
					name:'transferCode',
					hidden:true
				},{
					xtype:'combobox',
					fieldLabel:'营业区',
					labelWidth :50,
					columnWidth:.25,
					editable : false,
					name:'smallRegionName',
					displayField : 'smallRegionName',
					valueField : 'smallRegion',
					store : Ext.create('Foss.baseinfo.acceptPointSalesDept.SalesDeptStore'),
					listeners:{
						'select':function(combo,records,eOpts){
							if(records.length>0){
								var form = this.up('form').getForm();
								var record = records[0];
								if(record!=null && record!=''){
									//设置中转场的值
									form.findField('smallRegion').setValue(record.get('smallRegion'));
									form.findField('smallRegionName').setValue(record.get('smallRegionName'));
								}
							}
						}
					}
				},{
					fieldLabel:'营业区编码',
					name:'smallRegion',
					hidden:true
				},{
					fieldLabel:'操 作 人',
					name:'modifyUserName',
					readOnly:true,
					columnWidth:1
				},{
					fieldLabel:'addOrUptFlag',
					name:'addOrUptFlag',
					hidden:true
				},{
					xtype : 'container',
					columnWidth : 1,
					layout:'column',
					items : [{
						xtype:'container',
						html:'&nbsp;',
						columnWidth:.9
					},{
						xtype : 'button', 
						columnWidth:.1,
						text : '查询',
						handler : function() {
							var form=this.up('form');
							baseinfo.acceptPointSalesDept.salesDeptQuery(form);
						}
					}]
				
				}]			
});

var closeWin = false;
/**
 * 声明新增快递点部营业部映射关系windows
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.addOrupdateAcceptPointSalesDeptWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_acceptPointSalesDept_addOrUpdateAcceptPointSalesDeptWindow_Id',
	title : '新增/修改 接驳点与营业部映射',
	width : 850,
	height : 550,
	resizable:false,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	//acceptPointEntity: null,
	acceptPointSalesDeptConfigForm : null,
	getAcceptPointSalesDeptConfigForm : function(){
		if(Ext.isEmpty(this.acceptPointSalesDeptConfigForm)){
			this.acceptPointSalesDeptConfigForm = Ext.create('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptConfigForm');
		}
		return this.acceptPointSalesDeptConfigForm;
	},
	listeners:{
		beforehide:function(option){
			//新增的时候如果已选营业部数据为空，直接关闭
			var addOrUptFlag = option.getAcceptPointSalesDeptConfigForm().getForm().findField('addOrUptFlag').getValue();
			if(addOrUptFlag=='ADD'){
				var selectedSalesDeptGrid = option.getSalesDeptContainer().getSelectedSmallRegionSalesDeptGrid();
				var selections = selectedSalesDeptGrid.store.data.items;
				if(selections.length==0){
					return true;
				}
			}
			if(closeWin){
				closeWin = false;
				return true;
			}
			if(editFlag){
				Ext.Msg.confirm('温馨提示','是否确定关闭窗体?',function(o){
					if(o=='yes'){
						closeWin = true;
						option.hide();
					}
				});
			}else{
				return true;
			}
			return false;
		}
	},
	salesDeptContainer : null,
	getSalesDeptContainer : function(){
		if(Ext.isEmpty(this.salesDeptContainer)){
			this.salesDeptContainer = Ext.create('Foss.baseinfo.acceptPointSalesDept.SalesDeptContainer');
		}
		return this.salesDeptContainer;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            me.getAcceptPointSalesDeptConfigForm(),
		            me.getSalesDeptContainer()
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},	
			 items: [{
					text : baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.reset'),//重置
					columnWidth:.1,
					handler :function(){
						if(editFlag){
							editFlag = false;
						}
						// 获取window上的form
						var acceptPointSalesDeptForm = me.getAcceptPointSalesDeptConfigForm().getForm();
						// 获取新增或者修改的标志
						var flag = acceptPointSalesDeptForm.findField('addOrUptFlag').getValue();
						// 获取window上的container
						var salesDeptContainer = me.getSalesDeptContainer();
						if(flag=='ADD'){
							acceptPointSalesDeptForm.reset();
							salesDeptContainer.getSelectedSmallRegionSalesDeptGrid().getStore().removeAll();
							salesDeptContainer.getSmallRegionSalesDeptGrid().getStore().removeAll();
						}else if(flag=='UPDATE'){
							//先把数据清空
							acceptPointSalesDeptForm.reset();
							salesDeptContainer.getSelectedSmallRegionSalesDeptGrid().getStore().removeAll();
							salesDeptContainer.getSmallRegionSalesDeptGrid().getStore().removeAll();
							// 获取要修改的数据
							var modifyData = me.acceptPointEntity;
							//接驳点映射FORM加载数据
							var acceptPointSalesDeptModel  = new Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptModel(modifyData);
							acceptPointSalesDeptForm.loadRecord(acceptPointSalesDeptModel);
							//设置修改标志
							acceptPointSalesDeptForm.findField('addOrUptFlag').setValue('UPDATE');
							// 清空完以后加载要修改的数据
							var smallRegion = modifyData.smallRegion;
							var acceptPointCode = modifyData.acceptPointCode;
							// 设置参数
							params = {};
							Ext.apply(params, {
								'acceptPointSalesDeptDto.smallRegion' : smallRegion,
								'acceptPointSalesDeptDto.acceptPointCode' : acceptPointCode
								
							});
							
							//获取要赋值的grid及grid的store,并给store赋值
							var grid = salesDeptContainer.getSelectedSmallRegionSalesDeptGrid();
							grid.store.setSubmitParams(params);
							
							//查询
							grid.store.load({
								scope: this,
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
							    		var result =  Ext.decode(operation.response.responseText);
											grid.show();
							    	}
							    }
							});
						}
						// 不管新增还是修改 操作人是不变的
						var modifyUserName = baseinfo.acceptPointSalesDept.currentEmpName;
						acceptPointSalesDeptForm.findField('modifyUserName').setValue(modifyUserName);
						
					} 
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.66
				},{
					text :baseinfo.acceptPointSalesDept.i18n('foss.baseinfo.cancel'),//取消
					columnWidth:.1,
					handler :function(){
						me.hide();
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.02
				},{
					xtype:'button',
					text : '保存',
					itemId:'addOrupdateAcceptPointSalesDeptWindow_submit_ID',
					columnWidth:.1,
					handler : function() {
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_acceptPointSalesDept_addOrUpdateAcceptPointSalesDeptWindow_Id');
						this.setDisabled(true);
						if(!Ext.isEmpty(a_win)){
							
							//接驳点与营业部映射FORM数据
							var acceptPointSalesDeptForm = a_win.getAcceptPointSalesDeptConfigForm();
							// 获取form要提交的参数
							var bigRegion = acceptPointSalesDeptForm.getForm().findField('bigRegion').getValue();
							var acceptPointCode = acceptPointSalesDeptForm.getForm().findField('acceptPointCode').getValue();
							var transferCode = acceptPointSalesDeptForm.getForm().findField('transferCode').getValue();
							
							var flag = acceptPointSalesDeptForm.getForm().findField('addOrUptFlag').getValue();
							
							//获取营业部grid选中数据
							var acceptPointSalesDeptContainer = a_win.getSalesDeptContainer();
							var notSelectedSalesDeptGrid = acceptPointSalesDeptContainer.getSmallRegionSalesDeptGrid();
							var selectedSalesDeptGrid = acceptPointSalesDeptContainer.getSelectedSmallRegionSalesDeptGrid();
							var selections = selectedSalesDeptGrid.store.data.items;
							
							//接驳点与营业部映射FORM数据不能为空
							if((acceptPointCode==null||acceptPointCode=='')
									||(bigRegion==null || bigRegion=='' )
									||(transferCode==null || transferCode=='' ) ){
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '请选择待配置的接驳点和营业区',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								this.setDisabled(false);
						        return false;
							}
							
							//营业部Grid选中数据不能为空
							if(selections.length==0){
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '请至少选中一条带配置的营业部信息',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								this.setDisabled(false);
						        return false;
							}
							
							//设置营业部数据参数和接驳点数据参数
							var jsonData = new Array();
							for(var i=0;i<selections.length;i++){
								jsonData.push(selections[i].data);
							}
							var acceptPointEntity = new Object();
							acceptPointEntity.childrenSalesDeptEntitys = jsonData;
							
							
							acceptPointEntity.bigRegion = bigRegion;
							acceptPointEntity.acceptPointCode = acceptPointCode;
							acceptPointEntity.transferCode= transferCode;
							
							var acceptPointSalesDeptDto = new Object();
							acceptPointSalesDeptDto.acceptPointEntity = acceptPointEntity;
							
								var acceptPointSalesDeptGrid = Ext.getCmp('T_baseinfo-acceptPointSalesDept_content').getAcceptPointSalesDeptGrid();
								var params = acceptPointSalesDeptGrid.store.getSubmitParams();
								if(params==null||params==''){
									params = {};
									Ext.apply(params, {
										'acceptPointSalesDeptDto.acceptPointEntity.acceptPointCode' : null,
										'acceptPointSalesDeptDto.acceptPointEntity.bigRegion' : null,
										'acceptPointSalesDeptDto.acceptPointEntity.transferCode' : null,
										'acceptPointSalesDeptDto.acceptPointEntity.status' : null
									});
									acceptPointSalesDeptGrid.store.setSubmitParams(params);
								}
								
								var URL = null;
								if(flag=='ADD'){
									URL = baseinfo.realPath('addAcceptPointSalesDept.action');
								}else if(flag=='UPDATE'){
									URL = baseinfo.realPath('updateAcceptPointSalesDept.action');
								}
								//调用提交后台保存
								Ext.Ajax.request({
									url:URL,
									jsonData:{'acceptPointSalesDeptDto':acceptPointSalesDeptDto},
									method:'post',
									success:function(response){
										editFlag = false;
										closeWin = true;
										//清空grid
										notSelectedSalesDeptGrid.store.removeAll();
										selectedSalesDeptGrid.store.removeAll();
										//重置form
										acceptPointSalesDeptForm.getForm().reset();
										//将win隐藏
										a_win.hide();
										//重新查询数据
										acceptPointSalesDeptGrid.store.loadPage(1,{
										      callback: function(records, operation, success) {
										    	  
										    	//抛出异常  
											    var rawData = acceptPointSalesDeptGrid.store.proxy.reader.rawData;
											    if(!success && ! rawData.isException){
											    	Ext.MessageBox.show({
														title : '温馨提示',
														msg : rawData.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
											    	this.setDisabled(false);
													return false;
												}  
										    	//正常返回
										    	if(success){
										    		var result =   Ext.decode(operation.response.responseText);
										    		acceptPointSalesDeptGrid.show();
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
										this.setDisabled(false);
										return false;
									}			
								});
							
						}		
					}
				}]
		}];		
		me.callParent([cfg]);
	}
});


//接驳点对应营业部用来显示的FORM（查看功能下面的form）
Ext.define('Foss.baseinfo.acceptPointSalesDept.ShowDetialAcceptPointSalesDeptForm',{
	extend:'Ext.form.Panel',
	title:'',
	frame:false,
	width : 520,
	height : 170,
	defaults:{
		margin :'20 0 0 20',
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		columns :2
	},
	items : [{
					fieldLabel:'接驳点',
					name:'acceptPointName',
					readOnly:true,
					labelWidth :50,
					columnWidth:.55
				},{	
					fieldLabel:'经营大区',
					name:'bigRegionName',
					labelWidth :60,
					readOnly:true,
					columnWidth:.45
				},{	
					fieldLabel:'创建时间',
					name:'createDate',
					labelWidth :60,
					readOnly:true,
					columnWidth:.55
				},{
					fieldLabel:'创建人',
					name:'createUserName',
					readOnly:true,
					labelWidth :50,
					columnWidth:.45
				},{
					fieldLabel:'最后一次修改时间',
					name:'modifyDate',
					readOnly:true,
					labelWidth :110,
					columnWidth:.55
				},{
					fieldLabel:'修改人',
					name:'modifyUserName',
					readOnly:true,
					labelWidth :50,
					columnWidth:.45
				}
]			
});

/**
 * 创建一个营业区对应营业部的store
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.ShowRegionSalesDeptStore',{
	extend:'Ext.data.Store',
	//绑定model
	fields : [{
		name : 'SMALLREGIONNAME',
		type : 'string'
	}, {
		name : 'SALESDEPARTMENTNAME',
		type : 'string'
	}],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	//构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 查看功能下面的grid
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.ShowRegionSalesDeptGrid',{
	extend : 'Ext.grid.Panel',
	// 表格对象增加一个边框
	frame : true,
	width : 520,
	height : 300,
	parent : null,
	// 定义表格的标题
	title : '营业部列表',
	store : null,
	// 定义表格列信息
	columns : [
	           {
				// 字段标题
				text : '营业区',
				// 关联model中的字段名
				dataIndex : 'SMALLREGIONNAME',
				width : 200
	           },{
				// 字段标题
				text : '部门名称',
				// 关联model中的字段名
				dataIndex : 'SALESDEPARTMENTNAME',
				width : 200
	           }],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.acceptPointSalesDept.ShowRegionSalesDeptStore');
				
		me.callParent([ cfg ]);
	}
	});


/**
 * 声明查看与修改接驳点营业部映射关系windows
 */
Ext.define('Foss.baseinfo.acceptPointSalesDept.showOrupdateAcceptPointSalesDeptWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_expressPartSalesDept_showOrupdateAcceptPointSalesDeptWindow_Id',
	title : '接驳点与营业部映射信息',
	width : 550,
	height : 560,
	columnWidth : 0.98,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : 'column',
	expressPartSalesDeptEntity:null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.acceptPointSalesDept.ShowDetialAcceptPointSalesDeptForm'),
		            Ext.create('Foss.baseinfo.acceptPointSalesDept.ShowRegionSalesDeptGrid')
		            ];
		me.callParent([cfg]);
	}
});
//-------------------------------------------接驳点营业部配置 end--------------------------------------------------------

// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_baseinfo-acceptPointSalesDept_content')) {
		return;
	} 
	
	//查询已配置(快递点部营业部映射关系)的快递点部FORM
	var acceptPointSalesDeptQueryForm = Ext.create('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptQueryForm');
	
	//已配置(快递点部营业部映射关系)的快递点部列表grid
	var acceptPointSalesDeptGrid = Ext.create('Foss.baseinfo.acceptPointSalesDept.AcceptPointSalesDeptGrid');
	
	Ext.getCmp('T_baseinfo-acceptPointSalesDept').add(Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-acceptPointSalesDept_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getAcceptPointSalesDeptQueryForm:function(){
			return acceptPointSalesDeptQueryForm;
		},
		getAcceptPointSalesDeptGrid:function(){
			return acceptPointSalesDeptGrid;
		},
		items: [acceptPointSalesDeptQueryForm,acceptPointSalesDeptGrid]
	}));
});