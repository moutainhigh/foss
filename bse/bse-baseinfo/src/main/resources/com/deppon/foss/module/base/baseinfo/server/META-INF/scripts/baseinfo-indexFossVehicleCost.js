baseinfo.indexFossVehicleCost.errMsg = "上传的附件文件不能超过5M！！！";
//浏览器兼容提示
baseinfo.indexFossVehicleCost.tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过5M，建议使用Chrome浏览器。";

baseinfo.indexFossVehicleCost.checkFile = function(){
	var maxsize = 5*1024*1024;//5M  
    try{  
        var obj_file = document.getElementsByName("inviteFossVehicleCostVo.uploadFile");
        var filesize = 0;
        filesize = obj_file[obj_file.length-1].files[0].size;  
        if(filesize==-1){  
        	baseinfo.showInfoMes(baseinfo.indexFossVehicleCost.tipMsg);  
            return true;  
        }else if(filesize>maxsize){  
        	baseinfo.showInfoMes(baseinfo.indexFossVehicleCost.errMsg);  
            return false;  
        }else{ 
            return true;  
        }  
    }catch(e){ 
    }  
}

baseinfo.indexFossVehicleCost.indexFossVehicleCostConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

//查询零担外请车单票费用FORM查询方法: 
baseinfo.indexFossVehicleCost.indexFossVehicleCostQuery=function(me){
	//获取form及其参数值
	var form=me.getForm();
	var businessCode = form.findField('businessCode').getValue();
	var regionalCode = form.findField('regionalCode').getValue();
	// 设置参数
	params = {};
	Ext.apply(params, {         
		'inviteFossVehicleCostVo.inviteFossVehicleCostQueryDto.businessCode' : businessCode,
		'inviteFossVehicleCostVo.inviteFossVehicleCostQueryDto.regionalCode' : regionalCode
	});
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-inviteFossVehicleCostIndex_content').getindexFossVehicleCostGrid();
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
		    /*var result =   Ext.decode(operation.response.responseText);
	    		grid.show();*/
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
	    		if(result.inviteFossVehicleCostVo.inviteFossVehicleCostEntityList == null){
	    		Ext.MessageBox.show({
							title : '温馨提示',
							msg : '没有数据信息，请维护',
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
						return false;
	    		}
	    		grid.show();
	    	}
	       }
	    }); 
}

//事业部和大区映射Store的Model
Ext.define('Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type : 'string'
	},{
		name:'businessCode',//事业部编码
		type : 'string'
	},{
		name:'businessName',//事业部名称
		type : 'string'
	},{
		name:'regionalCode',//大区编码
		type : 'string'
	},{
		name:'regionalName',//大区名称
		type : 'string'
	},{
		name:'vehicleCostMax',//单票费用上限
		type : 'double'
	}]
});
//事业部和大区映射Store
Ext.define('Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostModel',
	pageSize: 20,
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryInviteFossVehicleCost.action'),//查询action 
		actionMethods:'post',
		reader:{
			type:'json',
			root:'inviteFossVehicleCostVo.inviteFossVehicleCostEntityList',
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


//查询零担外请车单票费用映射FORM
Ext.define('Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostQueryForm',{
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
					xtype : 'commonbusinessdivisionselector',
					fieldLabel : '事业部',
					name:'businessCode',
					labelWidth : 90

				}, {
					xtype : 'commonregionalselector',
					fieldLabel : '大区',
					name:'regionalCode',
					labelWidth : 90 
				},{
					xtype : 'container',
					columnWidth : 1,
					defaultType:'button',
					layout:'column',
					items : [{
						xtype : 'button', 
						text:baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.reset'),//重置
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
						text : baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.query'),//查询
						cls:'yellow_button',
						handler : function() {
							var form=this.up('form');
							baseinfo.indexFossVehicleCost.indexFossVehicleCostQuery(form);
						}
					}]
				
				}]			
});

//修改零担外请车单票费用映射信息FORM
Ext.define('Foss.baseinfo.indexFossVehicleCost.updateindexFossVehicleCostConfigForm',{
	extend:'Ext.form.Panel',
	title:'',
	frame:true,
	width : 360,
	height : 180,
	defaults:{
		margin :'20 0 0 20',
		labelWidth :100,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type :'column',
		axis : 'left',
		columns :1
	},
	items : [	{
					fieldLabel:'事业部',
					name:'businessName',
					allowBlank:false,
					readOnly:true,
					labelWidth : 60,
					columnWidth:1
				},{
					fieldLabel : '大区',
					name:'regionalName',
					allowBlank:false,
					readOnly:true,
					labelWidth : 60,
					columnWidth:1
				},{
					
					xtype : 'numberfield',
					fieldLabel: '外请车单票费用上限（元）', 
					name: 'vehicleCostMax',
					inputType:'text',
					allowDecimals:true,
				    minValue:0,//最小值
					allowBlank:false,
					labelWidth : 180,
					columnWidth:1
				}
				]			
});
//进入零担外请车单票费用映射修改界面
baseinfo.indexFossVehicleCost.editDetialindexFossVehicleCost = function(grid, rowIndex, colIndex){
	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	//获取显示弹出win
	var a_win = Ext.getCmp('Foss_baseinfo_indexFossVehicleCost_updateindexFossVehicleCostWindow_Id');
	if (Ext.isEmpty(a_win)) {
		a_win = Ext.create('Foss.baseinfo.indexFossVehicleCost.updateindexFossVehicleCostWindow');
	}
	//零担外请车单票费用映射FORM加载数据
	var indexFossVehicleCostModel  = new Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostModel(selection.data);
	var updateindexFossVehicleCostConfigForm = a_win.items.items[0];
	updateindexFossVehicleCostConfigForm.loadRecord(indexFossVehicleCostModel);
	a_win.indexFossVehicleCostEntity = selection.data;
	

	a_win.show();

}

/**
 * 声明零担外请车单票费用映射windows
 */
Ext.define('Foss.baseinfo.indexFossVehicleCost.updateindexFossVehicleCostWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_indexFossVehicleCost_updateindexFossVehicleCostWindow_Id',
	title : '修改外请车单票费用',
	width : 400,
	height : 300,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	indexFossVehicleCostEntity:null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
		            Ext.create('Foss.baseinfo.indexFossVehicleCost.updateindexFossVehicleCostConfigForm')
		            ];
		me.dockedItems = [{
			xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 5'
			},	
			 items: [{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.4
				},{
					xtype:'button',
					text : '提交',
					columnWidth:.15,
					handler : function() {
							var a_win = Ext.getCmp('Foss_baseinfo_indexFossVehicleCost_updateindexFossVehicleCostWindow_Id');
							var id = a_win.indexFossVehicleCostEntity.id;
							var updateindexFossVehicleCostConfigForm = a_win.items.items[0];
							var vehicleCostMax = updateindexFossVehicleCostConfigForm.getForm().findField('vehicleCostMax').getValue();
							
							//调用提交后台修改
							Ext.Ajax.request({
								url:baseinfo.realPath('updateInviteFossVehicleCost.action'),//修改action 参数  selectedIds
								params:{
									'inviteFossVehicleCostVo.inviteFossVehicleCostEntity.id':id,
									'inviteFossVehicleCostVo.inviteFossVehicleCostEntity.vehicleCostMax':vehicleCostMax
								},
								method:'post',
								success:function(response){
									
									//重置form
									updateindexFossVehicleCostConfigForm.getForm().reset();
									//将win隐藏
									a_win.hide();
									
										// 设置参数
									var indexFossVehicleCostGrid = Ext.getCmp('T_baseinfo-inviteFossVehicleCostIndex_content').getindexFossVehicleCostGrid();
										var params = indexFossVehicleCostGrid.store.getSubmitParams();
										if(params==null||params==''){
											params = {};
											Ext.apply(params, {
												'inviteFossVehicleCostVo.inviteFossVehicleCostEntity.vehicleCostMax' : null
											});
											indexFossVehicleCostGrid.store.setSubmitParams(params);
										}
									indexFossVehicleCostGrid.store.loadPage(1,{
									      callback: function(records, operation, success) {
									    	  
									    	//抛出异常  
										    var rawData = indexFossVehicleCostGrid.store.proxy.reader.rawData;
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
									    		indexFossVehicleCostGrid.show();
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

/**
 * 声明零担外请车单票费用映射windows
 */
Ext.define('Foss.baseinfo.AddExcelWin',{
	extend : 'Ext.window.Window',
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
			form.getForm().findField('inviteFossVehicleCostVo.uploadFile').reset();
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
		    				name:'inviteFossVehicleCostVo.uploadFile',
		    				fieldLabel:baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.pleaseSelectAttachments'),
		    				labelWidth:80,
		    				buttonText:baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.browse'),
		    				flex:1
		    			}]
		    		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.cancel'),
					handler : function() {
						me.hide();
					}
				},{
					text : baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.save'),  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						var form = me.down('form').getForm();
						var filePath = form.findField('inviteFossVehicleCostVo.uploadFile').getValue();
						if(Ext.isEmpty(filePath)){
							baseinfo.showInfoMes('请选择文件....');
							return;
						}
						var arr = filePath.split('.');
						if(arr[arr.length-1]!='xls'){
							baseinfo.showInfoMes('请使用模板,上传.xls格式的文件....');
							return;
						}
						if(!baseinfo.indexFossVehicleCost.checkFile()){
							return;
						}
						var url = baseinfo.realPath('addInviteFossVehicleCost.action');
						var successFn = function(json){
							me.close();
							if(Ext.isEmpty(json.inviteFossVehicleCostVo.numList)){
								baseinfo.showInfoMes(baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.allDataImportSuccess'));//全部数据导入成功！
							}else{
								var message = baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.di')+json.inviteFossVehicleCostVo.numList[0];//第
								for(var i = 1;i<json.inviteFossVehicleCostVo.numList.length;i++){
									message = message + "," +json.inviteFossVehicleCostVo.numList[i];
								}
								message = message+baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.lineNoImport');
								baseinfo.showWoringMessage(message);
							}
							Ext.getCmp('T_baseinfo-inviteFossVehicleCostIndex_content').getindexFossVehicleCostGrid().getPagingToolbar().moveFirst();

						};
						var failureFn = function(json){
							me.close();
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.formatError'));
							}else{
								baseinfo.showErrorMes(json.message);
							}
						};
						form.submit({
				            url: url,
				            waitMsg: baseinfo.indexFossVehicleCost.i18n('foss.baseinfo.uploadYourAttachments'),
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
				    			failureFn(result);
				    		}
				        });
					}
				}];
	}
});

//零担外请车单票费用映射列表
Ext.define('Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostGrid',{
	extend:'Ext.grid.Panel',
    title: '外请车单票费用列表',
    frame:true,
	height:500,
	// 定义一个获取新增窗口的函数
	getAddExcelWin : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.AddExcelWin');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	//分页组件
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					pageSize:10,
					prependButtons: true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
       return me.pagingToolbar;
	},
    store: Ext.create('Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostStore'),
	columns:[{
    	xtype:'actioncolumn',
    	header:'操作',
    	width:120,
    	align: 'center',
    	items:[{
    		iconCls : 'deppon_icons_edit',  
			tooltip:'修改',
			disabled:!baseinfo.indexFossVehicleCost.isPermission('baseinfo/updateindexFossVehicleCost'),
			hidden:!baseinfo.indexFossVehicleCost.isPermission('baseinfo/updateindexFossVehicleCost'),
			handler:function(grid, rowIndex, colIndex){
				baseinfo.indexFossVehicleCost.editDetialindexFossVehicleCost(grid, rowIndex, colIndex);
			}
    	}]
	},{
		header: '事业部', 
		width:220,
		dataIndex: 'businessName'
	},{
		
		header: '大区',
		width:220,
		dataIndex: 'regionalName'
	},{
		
		header: '外请车单票费用上限(元)',
		width:250,
		dataIndex: 'vehicleCostMax'
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
				text : '导入',
				columnWidth :.08,
				disabled:!baseinfo.indexFossVehicleCost.isPermission('baseinfo/addindexFossVehicleCost'),
				hidden:!baseinfo.indexFossVehicleCost.isPermission('baseinfo/addindexFossVehicleCost'),
				width : 100,
				handler : function() {// 作废多项选中的记录
                    this.addExcelWin = me.getAddExcelWin();
		            this.addExcelWin.show();
				}
			},{	
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.9
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
	if (Ext.getCmp('T_baseinfo-inviteFossVehicleCostIndex_content')) {
		return;
	} 
	
	/*Ext.Loader.setConfig({enabled:true});*/
	//查询已配置零担外请车单票费用映射信息FORM
	var indexFossVehicleCostQueryForm = Ext.create('Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostQueryForm');
	
	//已配置零担外请车单票费用映射信息列表grid
	var indexFossVehicleCostGrid = Ext.create('Foss.baseinfo.indexFossVehicleCost.indexFossVehicleCostGrid');
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-inviteFossVehicleCostIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getindexFossVehicleCostQueryForm:function(){
			return indexFossVehicleCostQueryForm;
		},
		getindexFossVehicleCostGrid:function(){
			return indexFossVehicleCostGrid;
		},
		items: [indexFossVehicleCostQueryForm,indexFossVehicleCostGrid],
		renderTo : 'T_baseinfo-inviteFossVehicleCostIndex-body'
	});
});