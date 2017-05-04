//Ajax请求--json
baseinfo.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author WangPeng
 * @时间 2013-06-24
 */
baseinfo.getStore = function(storeId,model,fields,data) {
	var store = null;
	if(!Ext.isEmpty(storeId)){
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if(Ext.isEmpty(data)){
		data = [];
	}
	if(!Ext.isEmpty(model)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    model:model,
			    data:data
		     });
		}
	}
	if(!Ext.isEmpty(fields)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    fields:fields,
			    data:data
		     });
		}
	}
	return store;
};

//------------------------------------model---------------------------------------------------

//值MODEL
Ext.define('Foss.baseinfo.downLoadExcelFileInfo.loadFileEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'empCode',//员工工号
        type : 'string'
    },{
        name : 'orgCode',//所属部门
        type : 'string'
    },{
        name : 'fileName',//下载文件名称
        type : 'string'
    },{
        name : 'fileLoadPath',//下载文件路径
        type : 'string'
    },{
        name : 'fileOuterLoadPath',//文件存放的物理路径
        type : 'string'
    },{
        name : 'active',//是否有效
        type : 'string'
    },{
        name : 'saveLocalPath',//文件保存在本地的路径
        type : 'string'
    },{
        name : 'creator',//创建人
        type : 'string'
    },{
        name : 'modifyUser',//修改人
        type : 'string'
    },{
        name : 'createTime',//创建时间
        type : 'date',
        convert:dateConvert

    },{
        name : 'modifyTime',//修改时间
        type : 'date',
        convert:dateConvert
    }]
});

//-----------------------------------button---------------------------------------------------
/**
 * 文件资源存储Store
 */
Ext.define('Foss.baseinfo.downLoadExcelFileInfo.excelFileResourceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.downLoadExcelFileInfo.loadFileEntity',//下载文件资源MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../baseinfo/queryNeedDownLoadExcelFileInfo.action",//查询的url
		reader : {
			type : 'json',
			root : 'downloadInfoEntityVo.downloadInfoEntityList',//结果集
			totalProperty : 'totalCount'//行数
		}
	}
});

//----------------------------------------store---------------------------------
/**
 * 表单
 */
Ext.define('Foss.baseinfo.downLoadExcelFileInfo.QueryExcleFileResourceForm', {
	extend : 'Ext.form.Panel',
	//title: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.searchCondiction'),//查询条件?????????
	id:'Foss_baseinfo_QueryExcleFileResourceForm_Id',
//	title:'资源查询',
	title: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.searchResource'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .35,
    	maxLength:50,
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name: 'empCode',
//			fieldLabel:'员工工号',
			fieldLabel: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.empCode'),
	        xtype : 'textfield',
			regex:new RegExp('^[0-1][0-9]{5}$'),
		},{
			name: 'fileName', 
//			fieldLabel:'文件名称',
			fieldLabel: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.fileName'),
	        xtype : 'textfield'
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
//				   text:'重置',
				   text: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.reset'),
				  columnWidth:.08,
//				  hidden:!baseinfo.downLoadExcelFileInfo.isPermission('loadFileEntity/excelFileResoureceQueryButton'),
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.66
				},{
				  //text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.search'),//查询
//					text:'查询',
					text: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.search'),
				  columnWidth:.08,
//				  hidden:!baseinfo.downLoadExcelFileInfo.isPermission('loadFileEntity/excelFileResoureceQueryButton'),
				  cls:'yellow_button',  
				  handler:function() {
					if(me.getForm().isValid()){
						if(me.getForm().findField('empCode').getValue() != FossUserContext.getCurrentUser().employee.empCode
							     && me.getForm().findField('empCode').getValue() != '' ){
							         me.getForm().findField('empCode').setValue(null);
//								baseinfo.showInfoMes("只能查询当前登录人所能下载的资源！");
							    baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.searchCurrentUserResources'));
								return;
							}
 					    me.up().getExcelFileResourceGrid().getPagingToolbar().moveFirst();
					}else{
						if(!me.getForm().findField('empCode').isValid()){
							me.getForm().findField('empCode').setValue(null);
//							baseinfo.showInfoMes("工号输入不合法，请重新输入！");
							 baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.repeatInput'));
							return;
						}
					}
				  }
			  	}]
			}]; 
		me.callParent([cfg]);
	}
});
/**
 * 表格
 */
Ext.define('Foss.baseinfo.downLoadExcelFileInfo.ShowLoadExcelResourceGridPanel', {
	extend: 'Ext.grid.Panel',
	//title : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.searchResult'),//查询结果列表
//	title:'查询结果列表',
	title:baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.searchResultList'),
	frame: true,
    sortableColumns:true,
    enableColumnHide:true,
    enableColumnMove:true,
    selModel : Ext.create('Ext.selection.CheckboxModel'),
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
//	emptyText: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.selectResultNull'),//查询结果为空
	//得到bbar 分页工具栏
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//作废数据
	toVoid: function(btn){
		var me = this;
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
//			baseinfo.showErrorMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.pleaseOneToOp'));
//			baseinfo.showErrorMes("没有选中的行");
			baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.pleaseOneRow'));
			return;
		}else if(selections.length>1){
//			baseinfo.showErrorMes("每次只能作废一条记录");
			baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.OneTimeOnlySelectOneRow'));
			return;
		}
		var downloadInfoEntity = [];
		for(var i = 0 ; i<selections.length ; i++){
			if(null == selections[i].get('id')){
//				baseinfo.showErrorMes("选中行的ID为空");
				baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.selectIdIsEmpty'));
				return;
			}
			downloadInfoEntity.push({'id':selections[i].get('id')});
		}
//		baseinfo.showQuestionMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.isVoidValues'),function(e){//是否要作废这些值？
		baseinfo.showQuestionMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.isVoidValues'),function(e){//是否要作废这些值？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'downloadInfoEntityVo':{'downloadInfoEntityList':downloadInfoEntity}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
//						baseinfo.showErrorMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.requestTimeout'));//请求超时
						baseinfo.showErrorMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.requestTimeout'));//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteSomeRecords.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	//下载数据

	toDownLoad: function(btn){
		var me = this;
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
//			baseinfo.showErrorMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.pleaseOneToOp'));
			baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.pleaseOneRow'));
			return;
		}/*else if(selections.length>1){
			baseinfo.showErrorMes("暂不支持批量下载，每次只能下载一条记录");
			return;
		}*/
		/*var downloadInfoEntity = new Array();
		for(var i = 0 ; i<selections.length ; i++){
			if(null == selections[i].get('id')){
				baseinfo.showErrorMes("选中行的ID为空");
				return;
			}
			if(null == selections[i].get('fileLoadPath')){
				baseinfo.showErrorMes("选中行的文件下载路径为空");
				return;
			}
			downloadInfoEntity.push({'fileLoadPath':selections[i].get('fileLoadPath')});
		}*/
	    
		baseinfo.showQuestionMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.isSureDownLoad'),function(e){//确定要下载？
			if(e=='yes'){//询问是否下载，发送请求
				for(var i = 0 ; i<selections.length ; i++){
					if(null == selections[i].get('id')){
						baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.selectIdIsEmpty'));
						return;
					}
					if(null == selections[i].get('fileLoadPath')){
//						baseinfo.showErrorMes("选中行的文件下载路径为空");
						baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.selectfileLoadPathIsEmpty'));
						return;
					}
				var url = selections[i].get('fileLoadPath');    				 				
				window.open(url,'newwindow') ;
				}
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
//			text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.num')//序号
//			text:'序号',
			text: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.number')
		},{
			align : 'center',
			xtype : 'actioncolumn',
//			text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.op'),//操作
//			text:'操作',
			text: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.operate'),
			items: [{
				iconCls: 'deppon_icons_download',
//				tooltip:'下载',
				tooltip:baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.downLoad'),
//                disabled:!baseinfo.downLoadExcelFileInfo.isPermission('loadFileEntity/excelFileResoureceQueryButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	if(null == record.get('id')){
//                		baseinfo.showErrorMes("选中的id为空");
						baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.selectIdIsEmpty'));
        				return;
        			}
                	if(null == record.get('fileLoadPath')){
//                		baseinfo.showErrorMes("选中行的文件下载路径为空");
						baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.selectfileLoadPathIsEmpty'));
        				return;
        			}
    				var url = record.get('fileLoadPath');    				 				
    				window.open(url,'newwindow') ;
                	
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.void'),//作废
//                disabled:!baseinfo.downLoadExcelFileInfo.isPermission('loadFileEntity/excelFileResoureceQueryButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	if(null == record.get('id')){
//                		baseinfo.showErrorMes("选中的id为空");
						baseinfo.showInfoMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.selectIdIsEmpty'));
        				return;
        			}
//                	baseinfo.showQuestionMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.isVoidThisValue'),function(e){//是否要作废这条值？
                	baseinfo.showQuestionMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.isCancell'),function(e){//是否要作废这条值？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var downloadInfoEntityList = new Array();
            				downloadInfoEntityList.push({'id':record.get('id')});
            				var params = {'downloadInfoEntityVo':{'downloadInfoEntityList':downloadInfoEntityList}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteSomeRecords.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                	
                }
			}]

		},
		{
			text : 'ID',
			dataIndex : 'id',
		    hidden:true
	    },{
//	    	text : '员工工号',//员工工号
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.empCode'),
	    	dataIndex : 'empCode'
	    },{
//	    	text : '所属部门',
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.orgCode'),
	    	dataIndex : 'orgCode'
	    },{
//	    	text : '下载文件名称',//下载文件名称
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.downLoadFileName'),
	    	dataIndex : 'fileName'
	    },{
//	    	text : '下载文件路径',//下载文件路径
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.downLoadFilePath'),
	    	dataIndex : 'fileLoadPath'
	    },{
//	    	text : '文件存放的物理路径',//文件存放的物理路径
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.fileOuterLoadPath'),
	    	dataIndex : 'fileOuterLoadPath',
	    	hidden:true
	    },{
//	    	text : '是否有效',//是否有效
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.isActive'),
	    	dataIndex : 'active',
	    	renderer:function(value){
	            if(value=='Y'){
	                 return baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.active');
	            }else if(value=='N'){
	                 return baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.inActive');
	            }else{
	                  return '';
	            }
	       }
	    },{
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.creator'),//创建人
	    	dataIndex : 'creator'
	    },{
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.modifyUser'),//修改人
	    	dataIndex : 'modifyUser',
	    	hidden:true
	    },{
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.createTime'),//创建时间
	    	dataIndex : 'createTime',
	    	width:180,
	    	renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			}
	    },{
	    	text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.modifyTime'),//修改时间
	    	dataIndex : 'modifyTime',
	    	hidden:true
	    }];
		me.store = Ext.create('Foss.baseinfo.downLoadExcelFileInfo.excelFileResourceStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-downLoadExcelFileInfo_content').getQueryForm();
					if(queryForm.getForm().findField('empCode').getValue() == ''
					     && queryForm.getForm().findField('fileName').getValue() == ''){
					    queryForm.getForm().findField('empCode').setValue(FossUserContext.getCurrentUser().employee.empCode);
					}
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'downloadInfoEntityVo.downloadInfoEntity.empCode':
									queryForm.getForm().findField('empCode').getValue(),//员工工号
								'downloadInfoEntityVo.downloadInfoEntity.fileName':
									queryForm.getForm().findField('fileName').getValue()//文件名称
							}
						});	
					}
				}
		    }
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
					mode:'MULTI',
					checkOnly:true
				});
		me.tbar = [{
			text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.void'),//作废
			handler :function(){
				me.toVoid();
			} 
		},{
//			text : baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.add'),//新增
//			text:'下载',
			text:baseinfo.downLoadExcelFileInfo.i18n('foss.baseinfo.downLoadExcelFileInfo.downLoad'),
			handler :function(){
				me.toDownLoad();
			} 
		} ];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});



/**
 * @description 下载资源主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-downLoadExcelFileInfo_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.baseinfo.downLoadExcelFileInfo.QueryExcleFileResourceForm');//查询FORM
	var excelFileResourceGrid = Ext.create('Foss.baseinfo.downLoadExcelFileInfo.ShowLoadExcelResourceGridPanel');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-downLoadExcelFileInfo_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getExcelFileResourceGrid : function() {
			return excelFileResourceGrid;
		},
		items : [queryForm, excelFileResourceGrid],
		renderTo : 'T_baseinfo-downLoadExcelFileInfo-body'
	});
});

 

 


