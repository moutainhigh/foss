/**
  * 转换long类型为日期
  *@param value 要转换的时间
  */
baseinfo.classifiedIncome.longToDateConvert = function(value) {
	if (!Ext.isEmpty(value)) {
		return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	} else {
		return null;
	}
}

baseinfo.classifiedIncome.classifiedIncomeConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};



baseinfo.classifiedIncome.errMsg = "上传的附件文件不能超过2M！！！";
//浏览器兼容提示
baseinfo.classifiedIncome.tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用Chrome浏览器。";

baseinfo.classifiedIncome.checkFile = function(){
	var maxsize = 2*1024*1024;//2M  
    try{  
        var obj_file = document.getElementsByName("classifiedIncomeVo.uploadFile");
        var filesize = 0;
        filesize = obj_file[obj_file.length-1].files[0].size;  
        if(filesize==-1){  
        		Ext.MessageBox.show({
					title : '温馨提示',
					msg : baseinfo.classifiedIncome.tipMsg,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
            return true;  
        }else if(filesize>maxsize){  
        		Ext.MessageBox.show({
					title : '温馨提示',
					msg : baseinfo.classifiedIncome.errMsg,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
            return false;  
        }else{ 
            return true;  
        }  
    }catch(e){ 
    }  
}
//是否有效
Ext.define('Foss.baseinfo.classifiedIncome.ActiveStore', {
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

//产品类型
Ext.define('Foss.baseinfo.classifiedIncome.productTypeStore',{  
    extend:'Ext.data.Store',  
    fields: [
        {name: 'valueCode', type: 'string'},
 		{name: 'valueName', type: 'string'}
     ],  
    data : [
     	{'valueCode': '',    valueName: '全部'},
        {'valueCode': 'LTL',    valueName: '零担'},
        {'valueCode': 'EXPRESS',    valueName: '快递'},
        {'valueCode': 'VEHICLE',    valueName: '整车'}
     ]
  
}); 

var baseinfo_classifiedIncome_classifiedIncomeStore = Ext.create('Foss.baseinfo.classifiedIncome.productTypeStore'); 

//查询已配置(重分类基础信息营业部映射关系)的重分类基础信息FORM查询方法: 
baseinfo.classifiedIncome.classifiedIncomeQuery=function(me){
	
	//获取form及其参数值
	var form=me.getForm();
	var productTypeCode = form.findField('productTypeCode').getValue();
	var owendSubsidiaryCode = form.findField('owendSubsidiaryCode').getValue();
	var businessTime = form.findField('businessTime').getValue();
	var active = form.findField('active').getValue();
	
	// 设置参数
	params = {};
	Ext.apply(params, {
		'classifiedIncomeVo.classifiedIncomeQueryDto.productTypeCode' : productTypeCode,
		'classifiedIncomeVo.classifiedIncomeQueryDto.owendSubsidiaryCode' : owendSubsidiaryCode,
		'classifiedIncomeVo.classifiedIncomeQueryDto.businessTime' : businessTime,
		'classifiedIncomeVo.classifiedIncomeQueryDto.active' : active
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-classifiedIncome_content').getClassifiedIncomeGrid();
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
				//baseinfo.showInfoMes(rawData.message);
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

//重分类基础信息Store的Model
Ext.define('Foss.baseinfo.classifiedIncome.ClassifiedIncomeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'productTypeName',//产品类型名称
		type : 'string'
	},{
		name:'productTypeCode',//产品类型编码
		type : 'string'
	},{
		name:'owendSubsidiaryName',//所属子公司名称
		type : 'string'
	},{
		name:'owendSubsidiaryCode',//所属子公司编码
		type : 'string'
	},{
		name:'sixPercent',//6%比例
		type : 'string'
	},{
		name:'elevenPercent',//11%比例
		type : 'string'
	},{
		name:'createTime',
		type:'Date',
		convert:baseinfo.classifiedIncome.longToDateConvert
	},{	
		name:'createUserName',
		type : 'string'
	},{
		name:'createUser',
		type : 'string'
	},{
		name:'modifyTime',
		type:'Date',
		convert:baseinfo.classifiedIncome.longToDateConvert
	},{	
		name:'modifyUserName',
		type : 'string'
	},{
		name:'modifyUser',
		type : 'string'
	},{
		name:'beginTime',
		type:'Date',
		convert:baseinfo.classifiedIncome.longToDateConvert
	},{
		name:'endTime',
		type:'Date',
		convert:baseinfo.classifiedIncome.longToDateConvert
	},{
		name:'active',
		type : 'string'
	}]
});
//重分类基础信息Store
Ext.define('Foss.baseinfo.classifiedIncome.ClassifiedIncomeStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.classifiedIncome.ClassifiedIncomeModel',
	pageSize: 20,
	sorters: [{
	     property: 'modifyTime',
	     direction: 'DESC'
	 }],
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryClassifiedIncome.action'),//查询action 
		actionMethods:'post',
		reader:{
			type:'json',
			root:'classifiedIncomeVo.classifiedIncomeEntityList',
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


//查询重分类收入基础信息FORM
Ext.define('Foss.baseinfo.classifiedIncome.ClassifiedIncomeQueryForm',{
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
					xtype:'combo',
					fieldLabel:'产品类型',
					labelWidth :70,
					columnWidth : .21,
					store:baseinfo_classifiedIncome_classifiedIncomeStore,
					name:'productTypeCode',
					displayField: 'valueName',
				    valueField: 'valueCode'
				},{
					xtype:'commonsubsidiaryselector',
					fieldLabel:'所属子公司',
					name:'owendSubsidiaryCode',
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
					id : 'Foss_baseinfo_classifiedIncome_businessTime_ID',
					dateConfig: {
						el : 'Foss_baseinfo_classifiedIncome_businessTime_ID-inputEl'
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
					store : Ext.create('Foss.baseinfo.classifiedIncome.ActiveStore', {
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
						text:baseinfo.classifiedIncome.i18n('foss.baseinfo.reset'),//重置
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
						text : baseinfo.classifiedIncome.i18n('foss.baseinfo.query'),//查询
						cls:'yellow_button',
						handler : function() {
							var form=this.up('form');
						
							baseinfo.classifiedIncome.classifiedIncomeQuery(form);
						}
					}]
				
				}]			
});

//修改查看重分类基础信息FORM
Ext.define('Foss.baseinfo.classifiedIncome.ShowOrUpdateClassifiedIncomeConfigForm',{
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
	items : [	{
					fieldLabel:'所属子公司',
					name:'owendSubsidiaryName',
					allowBlank:false,
					readOnly:true,
					columnWidth:.35
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'业务类型',
					name:'productTypeName',
					readOnly:true,
					allowBlank:false,
					columnWidth:.35
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'6%比例',
					name:'sixPercent',
					allowBlank: false,
					columnWidth:.35,
					regex:/^-?(100|(([1-9]\d|\d)(\.\d{1,2})?))%$/,
							    listeners : {
				       				blur : function(){
				       					var form = this.up('form').getForm();
				       					if(form.findField('sixPercent').isValid()){
				       					var sixPER = form.findField('sixPercent').getValue();
										var elevenPER = (100-parseFloat(sixPER)).toFixed(2)+'%';
										form.findField('elevenPercent').setValue(elevenPER);
			       						}
			       				 	}
								}
					
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'11%比例',
					name:'elevenPercent',
					readOnly:true,
					//allowBlank:false,
					columnWidth:.35
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'业务类型',
					name:'productTypeCode',
					readOnly:true,
					allowBlank:false,
					columnWidth:.35,
					hidden:true
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				},{
					fieldLabel:'所属子公司编码',
					name:'owendSubsidiaryCode',
					allowBlank:false,
					columnWidth:.35,
					hidden:true
				},{
					xtype:'container',
					html:'&nbsp;',
					height : 24,
					columnWidth:.6
				}
				]			
});
//进入重分类基础信息修改界面
baseinfo.classifiedIncome.editDetialClassifiedIncome = function(grid, rowIndex, colIndex){

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
		//baseinfo.showInfoMes('请选择有效数据进行操作');
		return false;
	}
	
	//获取显示弹出win
	var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_Id');
	if (Ext.isEmpty(a_win)) {
		a_win = Ext.create('Foss.baseinfo.classifiedIncome.showOrUpdateClassifiedIncomeWindow');
	}
	//重分类基础信息FORM加载数据
	var classifiedIncomeModel  = new Foss.baseinfo.classifiedIncome.ClassifiedIncomeModel(selection.data);
	var showOrUpdateClassifiedIncomeConfigForm = a_win.items.items[0];
	showOrUpdateClassifiedIncomeConfigForm.loadRecord(classifiedIncomeModel);
	a_win.classifiedIncomeEntity = selection.data;
	
	//业务类型，所属子公司只读
	showOrUpdateClassifiedIncomeConfigForm.getForm().findField('owendSubsidiaryName').setReadOnly(true);
	showOrUpdateClassifiedIncomeConfigForm.getForm().findField('productTypeName').setReadOnly(true);
	showOrUpdateClassifiedIncomeConfigForm.getForm().findField('sixPercent').setReadOnly(false);
	
	//作废win窗体的按钮
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_cancel_Id').setVisible(true);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_reset_Id').setVisible(true);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_submit_Id').setVisible(true);
	
	//显示重分类基础信息配置界面
	a_win.show();

}
//进入重分类基础信息查看界面
baseinfo.classifiedIncome.showDetialClassifiedIncome = function(grid, rowIndex, colIndex){

	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	
	//获取显示弹出win
	var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_Id');
	if (Ext.isEmpty(a_win)) {
		a_win = Ext.create('Foss.baseinfo.classifiedIncome.showOrUpdateClassifiedIncomeWindow');
	}
	//重分类基础信息FORM加载数据
	var classifiedIncomeModel  = new Foss.baseinfo.classifiedIncome.ClassifiedIncomeModel(selection.data);
	var showOrUpdateClassifiedIncomeConfigForm = a_win.items.items[0];
	showOrUpdateClassifiedIncomeConfigForm.loadRecord(classifiedIncomeModel);
	a_win.classifiedIncomeEntity = selection.data;
	
	//业务类型，所属子公司只读
	showOrUpdateClassifiedIncomeConfigForm.getForm().findField('owendSubsidiaryName').setReadOnly(true);
	showOrUpdateClassifiedIncomeConfigForm.getForm().findField('productTypeName').setReadOnly(true);
	showOrUpdateClassifiedIncomeConfigForm.getForm().findField('sixPercent').setReadOnly(true);
	
	
	//作废win窗体的按钮
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_cancel_Id').setVisible(false);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_reset_Id').setVisible(false);
	a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_submit_Id').setVisible(false);
	
	//显示重分类基础信息界面
	a_win.show();
}

/**
 * 声明查看与修改重分类基础信息windows
 */
Ext.define('Foss.baseinfo.classifiedIncome.showOrUpdateClassifiedIncomeWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_Id',
	title : '查看/修改重分类基础信息',
	width : 850,
	height : 400,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	classifiedIncomeEntity:null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.classifiedIncome.ShowOrUpdateClassifiedIncomeConfigForm')
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
					itemId:'Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_cancel_Id',
					columnWidth:.1,
					handler : function() {
						var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_Id');
						var showOrUpdateClassifiedIncomeConfigForm = a_win.items.items[0];
						showOrUpdateClassifiedIncomeConfigForm.getForm().reset();
						a_win.hide();
					}
				},{
				 	xtype:'button',
					text:'重置',
					itemId:'Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_reset_Id',
					columnWidth:.1,
					handler : function() {
						
						var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_Id');
						var classifiedIncomeModel  = new Foss.baseinfo.classifiedIncome.ClassifiedIncomeModel(a_win.classifiedIncomeEntity);
						var showOrUpdateClassifiedIncomeConfigForm = a_win.items.items[0];
						showOrUpdateClassifiedIncomeConfigForm.loadRecord(classifiedIncomeModel);
						
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
					itemId:'Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_submit_Id',
					columnWidth:.1,
					handler : function() {
							
						
							var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_showOrUpdateClassifiedIncomeWindow_Id');
							var toUpdateId = a_win.classifiedIncomeEntity.id;
							
							var showOrUpdateClassifiedIncomeConfigForm = a_win.items.items[0];
							var elevenPercent = showOrUpdateClassifiedIncomeConfigForm.getForm().findField('elevenPercent').getValue();
							var sixPercent = showOrUpdateClassifiedIncomeConfigForm.getForm().findField('sixPercent').getValue();
							
							//调用提交后台修改
							Ext.Ajax.request({
								url:baseinfo.realPath('updateClassifiedIncome.action'),//修改action 参数  selectedIds
								params:{
									'classifiedIncomeVo.classifiedIncomeQueryDto.selectedIds':toUpdateId,
									'classifiedIncomeVo.classifiedIncomeQueryDto.sixPercent':sixPercent,
									'classifiedIncomeVo.classifiedIncomeQueryDto.elevenPercent':elevenPercent
								},
								method:'post',
								success:function(response){
									
									//重置form
									showOrUpdateClassifiedIncomeConfigForm.getForm().reset();
									//将win隐藏
									a_win.hide();
									
										// 设置参数
									var classifiedIncomeGrid = Ext.getCmp('T_baseinfo-classifiedIncome_content').getClassifiedIncomeGrid();
										var params = classifiedIncomeGrid.store.getSubmitParams();
										if(params==null||params==''){
											params = {};
											Ext.apply(params, {
												'classifiedIncomeVo.classifiedIncomeQueryDto.productTypeCode' : null,
												'classifiedIncomeVo.classifiedIncomeQueryDto.owendSubsidiaryCode' : null,
												'classifiedIncomeVo.classifiedIncomeQueryDto.businessTime' : null,
												'classifiedIncomeVo.classifiedIncomeQueryDto.active' : null
											});
											classifiedIncomeGrid.store.setSubmitParams(params);
										}
									classifiedIncomeGrid.store.loadPage(1,{
									      callback: function(records, operation, success) {
									    	  
									    	//抛出异常  
										    var rawData = classifiedIncomeGrid.store.proxy.reader.rawData;
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
									    		classifiedIncomeGrid.show();
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
									//baseinfo.showInfoMes(result.message);
									return false;
								}			
							});
					}
				}]
		}];		
		me.callParent([cfg]);
	}
})

//配置新增重分类基础信息FORM
Ext.define('Foss.baseinfo.classifiedIncome.ClassifiedIncomeConfigForm',{
	extend:'Ext.form.Panel',
	title:'重分类基础信息',
	frame:true,
	width : 800,
	height : 350,
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
	constructor : function(config) { // 构造器
					var me = this, cfg = Ext.apply({}, config);
				    me.items = [{xtype:'combo',
								fieldLabel:'产品类型',
								allowBlank: false,
								columnWidth:.35,
								store:baseinfo_classifiedIncome_classifiedIncomeStore,
								name:'productTypeCode',
								displayField: 'valueName',
							    valueField: 'valueCode'
							},{
								xtype:'container',
								html:'&nbsp;',
								height : 24,
								columnWidth:.6
							},{
								xtype:'commonsubsidiaryselector',
								fieldLabel:'所属子公司',
								name:'owendSubsidiaryCode',
								allowBlank: false,
								columnWidth:.35
							},{
								xtype:'container',
								html:'&nbsp;',
								height : 24,
								columnWidth:.6
							},{ 
								fieldLabel:'6%比例',
								name:'sixPercent',
								allowBlank: false,
								columnWidth:.35,
								regex:/^-?(100|(([1-9]\d|\d)(\.\d{1,2})?))%$/,
							    listeners : {
				       				blur : function(){
				       					var form = me.getForm();
				       					if(form.findField('sixPercent').isValid()){
				       					
				       					var sixPER = form.findField('sixPercent').getValue();
										var elevenPER = (100-parseFloat(sixPER)).toFixed(2)+'%';
										form.findField('elevenPercent').setValue(elevenPER);
			       						}
			       				 	}
								}
							
							},{
								xtype:'container',
								html:'&nbsp;',
								height : 24,
								columnWidth:.6
							},{
								name : 'elevenPercent',
								fieldLabel : '11%比例',
								readOnly:true,
								columnWidth:.35
							
							},{
								xtype:'container',
								html:'&nbsp;',
								height : 24,
								columnWidth:.6
							}];
						
						me.callParent([cfg]);
					}
		
});
/**
 * 声明新增重分类基础信息windows
 */
Ext.define('Foss.baseinfo.classifiedIncome.addClassifiedIncomeWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_classifiedIncome_addClassifiedIncomeWindow_Id',
	title : '新增重分类基础信息',
	width : 850,
	height : 450,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.classifiedIncome.ClassifiedIncomeConfigForm')
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
						var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_addClassifiedIncomeWindow_Id');
						if(!Ext.isEmpty(a_win)){
							
							//重分类基础信息FORM清空数据
							var expressPartSalesDeptConfigForm = a_win.items.items[0];
							expressPartSalesDeptConfigForm.getForm().reset();
							
							//销毁当前win
							a_win.hide();
						}	
					}
				},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.7
				},{
				 	xtype:'button',
					text:'重置',
					columnWidth:.1,
					handler : function() {
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_addClassifiedIncomeWindow_Id');
						if(!Ext.isEmpty(a_win)){
							
							//重分类基础信息FORM清空数据
							var expressPartSalesDeptConfigForm = a_win.items.items[0];
							expressPartSalesDeptConfigForm.getForm().reset();
							
						}	
					}
				},{
					xtype:'button',
					//cls : 'yellow_button',
					text : '提交',
					itemId:'addClassifiedIncomeWindow_submit_ID',
					columnWidth:.1,
					handler : function() {
						
						//获取当前win
						var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_addClassifiedIncomeWindow_Id');
						if(!a_win.items.items[0].getForm().isValid()){
									Ext.MessageBox.show({
										title : '温馨提示',
										msg : '数据不合法',
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									return false;
						}
						if(!Ext.isEmpty(a_win)){
							var model =new Foss.baseinfo.classifiedIncome.ClassifiedIncomeModel();
							//重分类基础信息FORM数据
							var classifiedIncomeConfigForm = a_win.items.items[0];
							classifiedIncomeConfigForm.getForm().updateRecord(model);
							var owendSubsidiaryName = classifiedIncomeConfigForm.getForm().findField('owendSubsidiaryCode').getRawValue();
							var productTypeName = classifiedIncomeConfigForm.getForm().findField('productTypeCode').getRawValue();
							var sixPercent = classifiedIncomeConfigForm.getForm().findField('sixPercent').getValue();
							var elevenPercent = classifiedIncomeConfigForm.getForm().findField('elevenPercent').getValue();
							
							
							//把所属子公司名称传到后台
							model.set('owendSubsidiaryName',owendSubsidiaryName);
							model.set('productTypeName',productTypeName);
							if (owendSubsidiaryName == null
								|| owendSubsidiaryName == ''
								|| productTypeName == null
								|| productTypeName == '' || sixPercent == null
								|| elevenPercent == '') {

							Ext.MessageBox.show({
										title : '温馨提示',
										msg : '数据不能为空',
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									
							return false;

						}
						// 设置参数
							var classifiedIncomeGrid = Ext.getCmp('T_baseinfo-classifiedIncome_content').getClassifiedIncomeGrid();
								var params = classifiedIncomeGrid.store.getSubmitParams();
								if(params==null||params==''){
									params = {};
									Ext.apply(params, {
										'classifiedIncomeVo.classifiedIncomeQueryDto.productTypeCode' : null,
										'classifiedIncomeVo.classifiedIncomeQueryDto.owendSubsidiaryCode' : null,
										'classifiedIncomeVo.classifiedIncomeQueryDto.businessTime' : null,
										'classifiedIncomeVo.classifiedIncomeQueryDto.active' : null
									});
									classifiedIncomeGrid.store.setSubmitParams(params);
								}
								
								// 调用提交后台保存
								Ext.Ajax.request({
									url:baseinfo.realPath('addClassifiedIncome.action'),//新增action  参数classifiedIncomeEntity
									jsonData:{'classifiedIncomeVo':{'classifiedIncomeEntity':model.data}},
									method:'post',
									success:function(response){
										
										//重置form
										classifiedIncomeConfigForm.getForm().reset();
										//将win隐藏
										a_win.hide();
										
										//重新查询数据
										classifiedIncomeGrid.store.loadPage(1,{
										      callback: function(records, operation, success) {
										    	  
										    	//抛出异常  
											    var rawData = classifiedIncomeGrid.store.proxy.reader.rawData;
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
										    		var result = Ext.decode(operation.response.responseText);
										    		classifiedIncomeGrid.show();
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
				}]
		}];		
		me.callParent([cfg]);
	}
})


/**
 * 上传窗体
 */
Ext.define('Foss.baseinfo.AddExcelWin',{
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_classifiedIncome_AddExcelWin_Id',
	closable : true,
	title : '上传',
	modal : true,
	layout : 'auto',
	layout:{
		type:'vbox',
		align:'stretch'
	},
	resizable:false,
	closeAction : 'hide',
	width :350,
	height :200,
	parent:null,
	listeners:{
		beforehide:function(me){
			var form = me.down('form');
			form.getForm().findField('classifiedIncomeVo.uploadFile').reset();
		}
	},
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [
		    		{
		    			xtype:'form',
		    			flex:1,
		    			layout:{
		    				type : 'hbox'
		    			},
		    			defaults : {
		    				margins : '0 5 0 0'
		    			},
		    			items:[{
		    				xtype:'filefield',
		    				name:'classifiedIncomeVo.uploadFile',
		    				fieldLabel:baseinfo.classifiedIncome.i18n('foss.baseinfo.pleaseSelectAttachments'),
		    				labelWidth:80,
		    				buttonText:baseinfo.classifiedIncome.i18n('foss.baseinfo.browse'),
		    				flex:1
		    			}]
		    		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : '取消',
					handler : function() {
						me.hide();
					}
				},{
					text : '保存',  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						var form = me.down('form').getForm();
						var filePath = form.findField('classifiedIncomeVo.uploadFile').getValue();
						if(Ext.isEmpty(filePath)){
							Ext.MessageBox.show({
									title : '温馨提示',
									msg : '请选择文件....',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							return;
						}
						var arr = filePath.split('.');
						var str = arr[arr.length-1];
						if(str.substr(0, 3)!='xls' ){
							Ext.MessageBox.show({
									title : '温馨提示',
									msg : '请使用模板,上传.xls或xlsx格式的文件....',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							return;
						}
						if(!baseinfo.classifiedIncome.checkFile()){
							return;
						}
						var url = baseinfo.realPath('importClassifiedIncome.action');
						var successFn = function(json){
							me.close();
							if(Ext.isEmpty(json.classifiedIncomeVo.numList)){
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '全部数据导入成功！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}else{
								var message = baseinfo.classifiedIncome.i18n('foss.baseinfo.di')+json.classifiedIncomeVo.numList[0];//第
								for(var i = 1;i<json.classifiedIncomeVo.numList.length;i++){
									message = message + "," +json.classifiedIncomeVo.numList[i];
								}
								message = message+baseinfo.classifiedIncome.i18n('foss.baseinfo.lineNoImport');
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						};
						var failureFn = function(json){
							me.close();
							if(Ext.isEmpty(json)){
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '上传失败，请检查文件是否解锁、文件行数是否超过5千条或者格式是否正确！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}else{
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : json.message,
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								//baseinfo.showInfoMes(json.message);
							}
						};
						form.submit({
				            url: url,
				            waitMsg: baseinfo.classifiedIncome.i18n('foss.baseinfo.uploadYourAttachments'),
				            success:function(form, action){
				    			var result = action.result;
				    			if(result.success){
				    				successFn(result);
				    			}else{
				    				failureFn(result);
				    			}
				    		},
				    		failure:function(form, action){
				    			var result = action.result;
				    			failureFn(result);
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
				}];
	}
});

//通过查询条件导出数据
baseinfo.classifiedIncome.exportClassifiedIncome = function(){
	//先判断有数据才能导出
     	grid = Ext.getCmp('Foss_baseinfo_classifiedIncome_ExportExcelWin_Id');
	    if (grid.store.data.length == 0) {
	    	Ext.MessageBox.show({
				title : '温馨提示',
				msg : '导出数据为空，请查询后再导出数据',
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return false;
	    }
	    var queryForm = Ext.getCmp('T_baseinfo-classifiedIncome_content').getClassifiedIncomeQueryForm();
	    var queryForm = queryForm.getForm();//得到查询的FORM表单
		var productTypeCode = queryForm.findField('productTypeCode').getValue()
		var owendSubsidiaryCode = queryForm.findField('owendSubsidiaryCode').getValue()
		var businessTime = queryForm.findField('businessTime').getValue()
		var active = queryForm.findField('active').getValue()
		
		Ext.MessageBox.buttonText.yes ='确认';
		Ext.MessageBox.buttonText.no ='取消';
		if(!Ext.fly('classifiedIncome')){
			    var frm = document.createElement('form');
			    frm.id = 'classifiedIncome';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		
		Ext.Msg.confirm('提示信息','确定要导出查询结果吗?', function(btn,text){
			if(btn == 'yes'){
				var params ={
					'classifiedIncomeVo.classifiedIncomeQueryDto.productTypeCode' : productTypeCode,
					'classifiedIncomeVo.classifiedIncomeQueryDto.owendSubsidiaryCode' : owendSubsidiaryCode,
					'classifiedIncomeVo.classifiedIncomeQueryDto.businessTime' : businessTime,
					'classifiedIncomeVo.classifiedIncomeQueryDto.active' : active
				}
		
				Ext.Ajax.request({
					url:baseinfo.realPath('exportClassifiedIncomeList.action'),
					form: Ext.fly('classifiedIncome'),
					params:params,
					method:'post',
					isUpload: true,
					success:function(response){
						var result = Ext.decode(response.responseText);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert('导出失败',result.message);
					}
				});
			}
		});
	
};
//重分类收入基础信息列表
Ext.define('Foss.baseinfo.classifiedIncome.ClassifiedIncomeGrid',{
	extend:'Ext.grid.Panel',
	id:'Foss_baseinfo_classifiedIncome_ExportExcelWin_Id',
    title: '重分类基础信息明细',
    frame:true,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    store: Ext.create('Foss.baseinfo.classifiedIncome.ClassifiedIncomeStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:'操作',
    	width:120,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',
			tooltip:'修改',
			getClass : function (v, m, r, rowIndex) {
				if (r.get('active') === 'Y') {
				    return 'deppon_icons_edit';
				} else {
				    return 'statementBill_hide';
				}
			},
			//disabled:!baseinfo.expressPartSalesDept.isPermission('baseinfo/updateExpressPartSalesDept'),
			handler:function(grid, rowIndex, colIndex){
				baseinfo.classifiedIncome.editDetialClassifiedIncome(grid, rowIndex, colIndex);
			}
    	},{
    		iconCls : 'deppon_icons_showdetail',
			tooltip:'查看',
			handler:function(grid, rowIndex, colIndex){
				baseinfo.classifiedIncome.showDetialClassifiedIncome(grid, rowIndex, colIndex);
			}
    	}]
	},{
		header: '产品类型', 
		dataIndex: 'productTypeName'
	},{
		
		header: '所属子公司',
		dataIndex: 'owendSubsidiaryName'
	},{
		header: '6%比例',
		dataIndex: 'sixPercent'
	},{
		header: '11%比例',
		dataIndex: 'elevenPercent'
	},{
		header: '修改人', 
		dataIndex: 'modifyUserName'
	},{
		header: '修改人编码', 
		dataIndex: 'modifyUser',
		hidden:true
	},{
		header: '开始时间',
		width:130,
		dataIndex: 'createTime'
	},{
		header: '修改时间',
		width:130,
		dataIndex: 'modifyTime'
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
		
		header: '所属子公司编码',
		dataIndex: 'owendSubsidiaryCode',
		hidden:true
	},{
		header: '产品类型编码', 
		dataIndex: 'productTypeCode',
		hidden:true
	},{
		header: 'ID',
		dataIndex: 'id',
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
				margin :'0 8 0 0'
			},
			items :[{
				xtype :'button',
				text : '新增',
				columnWidth :.08,
				disabled:!baseinfo.classifiedIncome.isPermission('baseinfo/addClassifiedIncome'),
				hidden:!baseinfo.classifiedIncome.isPermission('baseinfo/addClassifiedIncome'),
				handler : function(grid, rowIndex, colIndex) {

					var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_addClassifiedIncomeWindow_Id');
					if (Ext.isEmpty(a_win)) {
						a_win = Ext.create('Foss.baseinfo.classifiedIncome.addClassifiedIncomeWindow');
					}
					var classifiedIncomeConfigForm = a_win.items.items[0];
					//重置form
					classifiedIncomeConfigForm.getForm().reset();
					//作废win窗体的按钮
					a_win.getDockedItems('toolbar[dock="bottom"]')[0].getComponent('addClassifiedIncomeWindow_submit_ID').setVisible(true);
					a_win.show();
				}
			},{
				xtype :'button',
				text : '作废',
				columnWidth :.08,
				disabled:!baseinfo.classifiedIncome.isPermission('baseinfo/deleteClassifiedIncome'),
				hidden:!baseinfo.classifiedIncome.isPermission('baseinfo/deleteClassifiedIncome'),
				handler : function(grid, rowIndex, colIndex) {

					// grid
					var grid = Ext.getCmp('T_baseinfo-classifiedIncome_content').getClassifiedIncomeGrid();
					
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
						
						//baseinfo.showInfoMes('请至少选择一条数据进行操作');
						return false;
					}
					
					var selectDeleteIds = [];
					for(var i=0;i<selections.length;i++){
						
						if(selections[i].get('active')=='N'){
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : '只有有效的信息才可作废',
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						//	baseinfo.showInfoMes('只有有效的信息才可作废');
							return false;
						}
						selectDeleteIds.push(selections[i].get('id'));
					}
					
					var yesFn=function(){
						// 调用
				 		Ext.Ajax.request({
				 			url:baseinfo.realPath('deleteClassifiedIncome.action'),//作废 action 参数 selectedIds
				 			params:{
				 				'classifiedIncomeVo.classifiedIncomeQueryDto.selectedIds':selectDeleteIds
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
				 							//baseinfo.showInfoMes(rawData.message);
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
				 				//baseinfo.showInfoMes(result.message);
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
				 							//baseinfo.showInfoMes(rawData.message);
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
					baseinfo.classifiedIncome.classifiedIncomeConfirmAlert('确认是否要作废重分类基础信息',yesFn,noFn);

				}
				},{
						xtype : 'button',
						text : '重分类基础信息导入',
						//disabled:!baseinfo.classifiedIncome.isPermission('EarlyWarningIndex/ImportEarlyWarningButton'),
						//hidden:!baseinfo.classifiedIncome.isPermission('EarlyWarningIndex/ImportEarlyWarningButton'),
						columnWidth :.15,
						handler : function() {
	                        var a_win = Ext.getCmp('Foss_baseinfo_classifiedIncome_AddExcelWin_Id');
							if (Ext.isEmpty(a_win)) {
									a_win = Ext.create('Foss.baseinfo.AddExcelWin');
							}
							a_win.show();
							
					}
					},{
						xtype : 'button',
						text : '重分类基础信息导出',
						columnWidth :.15,
						handler : function() {
						    baseinfo.classifiedIncome.exportClassifiedIncome();
							
						}
					},{	
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.6
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
// 初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	/*if (Ext.getCmp('T_baseinfo-classifiedIncome_content')) {
		return;
	} */
	
	//查询已配置(重分类基础信息营业部映射关系)的重分类基础信息FORM
	var classifiedIncomeQueryForm = Ext.create('Foss.baseinfo.classifiedIncome.ClassifiedIncomeQueryForm');
	
	//已配置(重分类基础信息营业部映射关系)的重分类基础信息列表grid
	var classifiedIncomeGrid = Ext.create('Foss.baseinfo.classifiedIncome.ClassifiedIncomeGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-classifiedIncome_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getClassifiedIncomeQueryForm:function(){
			return classifiedIncomeQueryForm;
		},
		getClassifiedIncomeGrid:function(){
			return classifiedIncomeGrid;
		},
		items: [classifiedIncomeQueryForm,classifiedIncomeGrid],
		renderTo : 'T_baseinfo-classifiedIncome-body'
	});
});