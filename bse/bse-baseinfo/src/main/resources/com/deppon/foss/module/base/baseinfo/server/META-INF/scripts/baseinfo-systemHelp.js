
baseinfo.systemHelpConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * Ajax请求--json(自己封装好ajax请求的方法)
 */
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
		url : url,
		jsonData : params,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				failFn(result);
			}
		},
		failure : function(response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
/**
 * ----------------------------查询表单------------------------
 */
// 查询表单
Ext.define('Foss.baseinfo.systemHelp.querySystemHelpForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',// 查询条件
	frame : true,
	collapsible : true,
	defaults : {
		columnWidth : .35,
		maxLength : 50,
		margin : '8 10 5 10',
	    anchor : '100%'
	},
	 height :200,
	 defaultType : 'textfield',
	 //列布局
	 layout:'column',
	 constructor:function(config){
		//me指代当前这个form
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name:'topic',
			fieldLabel:'系统帮助标题',//系统帮助标题
			xtype :'textarea',
			height : 80,
			columnWidth: .4
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.5
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : '重置',//重置
				  columnWidth:.08,
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : '查询',//查询
				  //hidden:!baseinfo.announcement.isPermission('announcement/announceRestButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					  //表单校验，分页查询
					if(me.getForm().isValid()){
						me.up().getSystemHelpGrid().getPagingToolbar().moveFirst();
					}
				  }
			  	}]
			}]; 
		me.callParent([cfg]);
	 }
});



//--------------------------------Model和store的定义-------------------------------
//定义公告model
Ext.define('Foss.baseinfo.systemHelp.SystemHelpEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'topic'// 标题
	}, {
		name : 'systemHelp'// 内容
	}, {
		name : 'active'//
	}, {
		name : 'createUser'// 发布人
	}, {
		name : 'createDate',// 创建时间
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	}, {
		name : 'modifyUser'// 修改人
	}, {
		name : 'modifyDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	} ]
});

//定义公告的Store
Ext.define('Foss.baseinfo.systemHelp.SystemHelpStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.systemHelp.SystemHelpEntity',// 绑定MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../baseinfo/querySystemHelp.action',// 查询的url
		reader : {
			type : 'json',
			root : 'systemHelpVo.systemHelpEntitys',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});

/**
 * --------------------系统帮助Grid----------------------------
 */
Ext.define('Foss.baseinfo.systemHelp.systemHelpGridPanel',{
	extend:'Ext.grid.Panel',
	title:'系统帮助列表',//查询结果列表
	frame: true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:'查询结果为空',//查询结果为空
	//得到分页bar
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
	 // 废除方法
    toVoid: function(){
    	var me =this;
    	var selections =me.getSelectionModel().getSelection();
    	if(selections.length<1){
    		Ext.Msg.show({
                title:'提示信息',
                msg:'没有选中一条要删除的数据',
                minWidth:200,
                modal:true,
                buttons:Ext.Msg.OK 
              });
    		return;
    	}
    	var ids =new Array();
    	for(var i = 0 ; i<selections.length ; i++){
    		ids.push(selections[i].get('id'));
		}
   
    	var yesFn=function(){
    		var params = {"systemHelpVo":{"idlist":ids}};
		 	var successFun = function(json){
				me.getPagingToolbar().moveFirst();
			};
			var failureFun =function(json){
				Ext.Msg.show({
                    title:'提示信息',
                    msg:json.message,
                    minWidth:200,
                    modal:true,
                    buttons:Ext.Msg.OK 
                  });
			};
			var url = baseinfo.realPath('deleteSystemHelp.action');	
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
		};
		var noFn=function(){
		 	return false;
		};
		baseinfo.systemHelpConfirmAlert('是否要作废这些值?',yesFn,noFn);	
    },
    // 新增
	valueAddWindow:null,
	getValueAddWindow : function() {
		if (this.valueAddWindow == null) {
			this.valueAddWindow = Ext.create('Foss.baseinfo.systemHelp.ValueAddWindow');
			this.valueAddWindow.parent = this;
		}
		return this.valueAddWindow;
	},
	//查询
	queryWin:null,
	getQueryWin : function(){
		if (this.queryWin == null) {
			this.queryWin = Ext.create('Foss.baseinfo.systemHelp.QuerySystemHelpWindow');
			this.queryWin.parent = this;
		}
		return this.queryWin;
	},
	// 值修改
	valueUpdateWindow:null,
	getValueUpdateWindow : function() {
		if (this.valueUpdateWindow == null) {
			this.valueUpdateWindow = Ext.create('Foss.baseinfo.systemHelp.ValueUpdateWindow');
			this.valueUpdateWindow.parent = this;
		}
		return this.valueUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
			me.columns = [{
				xtype: 'rownumberer',
				width:40,
				text : '序号'//序号
			},{
				align : 'center',
				xtype : 'actioncolumn',
				text : '操作',//操作
				items:[{
						iconCls: 'deppon_icons_edit',
						tooltip:'编辑',//编辑
						width:30,
						handler:function(grid,rowIndex,colIndex){
							//获取这条记录数
							var record =grid.getStore().getAt(rowIndex);
							var systemHelpId =record.get('id');//获取记录的id
							var params = {'systemHelpVo':{'systemHelpDto':{'systemHelpEntity':{'id':systemHelpId}}}};
		    				var successFun = function(json){
		    					var updateWindow = me.getValueUpdateWindow();//获得修改窗口
		    					updateWindow.systemHelpEntity = json.systemHelpVo.systemHelpDto.systemHelpEntity;//值
		    					updateWindow.show();//显示修改窗口
		    				};
		    				var failureFun = function(json){
		    					Ext.Msg.show({
		    	                    title:'提示信息',
		    	                    msg:json.message,
		    	                    minWidth:200,
		    	                    modal:true,
		    	                    buttons:Ext.Msg.OK 
		    	                  });
		    				};
		    				var url = baseinfo.realPath('querySystemHelpById.action');
		    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
						}					
					},{
						iconCls: 'deppon_icons_showdetail',
						tooltip:'查询详情',//查询详情
						width:30,
						handler:function(grid,rowIndex,colIndex){
							var record =grid.getStore().getAt(rowIndex);
							var systemHelpId =record.get('id');//获取记录的id
							var params = {'systemHelpVo':{'systemHelpDto':{'systemHelpEntity':{'id':systemHelpId}}}};
							var successFun = function(json){
		    					var queryWin = me.getQueryWin();//获得修改窗口
		    					queryWin.systemHelpEntity = json.systemHelpVo.systemHelpDto.systemHelpEntity;//值
		    					queryWin.show();//显示修改窗口
		    				};
		    				var failureFun = function(json){
		    					//传过来的数据为空的话，链接超时:
		    					Ext.Msg.show({
		    	                    title:'提示信息',
		    	                    msg:json.message,
		    	                    minWidth:200,
		    	                    modal:true,
		    	                    buttons:Ext.Msg.OK 
		    	                  });
		    				};
		    				var url = baseinfo.realPath('querySystemHelpById.action');
		    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
						}
					},{
					iconCls: 'deppon_icons_cancel',
					tooltip:'删除',//删除
					width:30,
					handler:function(grid,rowIndex,colIndex){
						
						var yesFn=function(){
							//获取选中的数据
			    			var  record =me.getStore().getAt(rowIndex);
			    			var ids =new Array();
			    			ids.push(record.get('id'));
			   
			    			var params = {"systemHelpVo":{"idlist":ids}};
			    			var successFun = function(json){
			    				me.getPagingToolbar().moveFirst();
			    			};
			    			var failureFun =function(json){
			    				Ext.Msg.show({
			                        title:'提示信息',
			                        msg:json.message,
			                        minWidth:200,
			                        modal:true,
			                        buttons:Ext.Msg.OK 
			                      });
			    			};
			    			var url =baseinfo.realPath('deleteSystemHelp.action');	
			    			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
						};
						var noFn=function(){
						 	return false;
						};
						baseinfo.systemHelpConfirmAlert('是否要作废这些值?',yesFn,noFn);	
					}
				}]
			},{
					width:400,
					text : '系统帮助标题',//公告标题
					dataIndex : 'topic',
//			},{
//					width:360,
//					text : '公告内容',// 公告内容
//					dataIndex : 'systemHelp',
//			},{		
//					width:160,
//					text : '发布人',// 发布人
//					dataIndex : 'createUser',
			},{		
					width:160,
					text : '发布时间',// 发布时间
					xtype: 'datecolumn',   
					format:'Y-m-d H:i:s',
					dataIndex : 'createDate',
			}];
			//表单缓存
			me.store = Ext.create('Foss.baseinfo.systemHelp.SystemHelpStore',{
				autoLoad : false,//不自动加载
				pageSize : 20,
				listeners: {
					//查询事件的监听
					beforeload: function(store, operation, eOpts){
						var queryForm = Ext.getCmp('T_baseinfo-systemHelp_content').getQueryForm();
						if(queryForm!=null){
							Ext.apply(operation,{
								params : {
									'systemHelpVo.systemHelpDto.systemHelpEntity.topic':
										queryForm.getForm().findField('topic').getValue()//标题
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
					text : '作废',//作废
					//hidden:!baseinfo.announcement.isPermission('announcement/announceVoidButton'),
					handler :function(){
						me.toVoid();
					} 
				},{
					text : '新增',//新增
					//hidden:!baseinfo.announcement.isPermission('announcement/announceAddButton'),
					handler :function(){
						me.getValueAddWindow().show();
					} 
				} ];
				me.bbar = me.getPagingToolbar();
				me.getPagingToolbar().store = me.store;
				me.callParent([cfg]);
		}
});
/**
 * ---------------------------------新增的window------------------
 */
Ext.define('Foss.baseinfo.systemHelp.ValueAddWindow',{
	extend : 'Ext.window.Window',
	title : '新增帮助信息',//新增帮助信息
	closable : true,
	modal : true,
	resizable:false,
	parent:null,
	//关闭动作为hide
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :850,
	height :600,
	listeners:{
		//隐藏窗口时 清空里面的数据
		beforehide:function(me){
			me.getValueForm().getForm().reset();//清空数据
		}
	},
	//内容的Form
	valueForm :null,
	getValueForm:function(){
		if(Ext.isEmpty(this.valueForm)){
			this.valueForm = Ext.create('Foss.baseinfo.systemHelp.ValueForm');
		}
		return this.valueForm;
	},
	//重置数据
	resetValue:function(){
		var me =this;
		me.getValueForm().getForm().reset();
	},
	//提交数据
	commitValue:function(){
		var me=this;
		if(me.getValueForm().getForm().isValid()){
			var valueModel =Ext.create('Foss.baseinfo.systemHelp.SystemHelpEntity'); //创建新一个
			var form =me.getValueForm().getForm();
			//内容的验证
			if(Ext.isEmpty(form.findField('systemHelp').getValue())){
				Ext.Msg.show({
                    title:'提示信息',
                    msg:'公共内容为空',
                    minWidth:200,
                    modal:true,
                    buttons:Ext.Msg.OK 
                  });
				return;
			}
			form.updateRecord(valueModel);
			var params ={'systemHelpVo':{'systemHelpDto':{'systemHelpEntity':valueModel.data}}};
			var successFun = function(json){
				  Ext.Msg.show({
                      title:'提示信息',
                      msg:'新增系统帮助成功',
                      minWidth:200,
                      modal:true,
                      buttons:Ext.Msg.OK 
                    });
				me.parent.getPagingToolbar().moveFirst();
				me.close();
			};
			var failureFun =function(json){
				Ext.Msg.show({
                    title:'提示信息',
                    msg:json.message,
                    minWidth:200,
                    modal:true,
                    buttons:Ext.Msg.OK 
                  });
			};
			var url =baseinfo.realPath('addSystemHelp.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getValueForm()];
		me.fbar = [{
			text : '取消',//取消
			handler :function(){
				me.close();
			} 
		},{
			text : '重置',//重置
			handler :function(){
				me.resetValue();
			} 
		},{
			text : '确定',//确定
			cls:'yellow_button',
			handler :function(){
				me.commitValue();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * -----------------------修改的window-------------
 */
Ext.define('Foss.baseinfo.systemHelp.ValueUpdateWindow',{
	extend:'Ext.window.Window',
	title : '编辑公告信息',//编辑公告信息
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	//关闭动作为hide，默认为destroy
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :850,
	height :600,
	listeners:{
		//隐藏窗口时 清空里面的数据
		beforehide:function(me){
			me.getValueForm().getForm().reset();//清空数据
		},
		//显示之前 先加载数据
		beforeshow:function(me){
			//判断数据是否不为空
			if(!Ext.isEmpty(me.systemHelpEntity)){
				var form = me.getValueForm().getForm();
				//把数据显示在界面
				form.loadRecord(new Foss.baseinfo.systemHelp.SystemHelpEntity(me.systemHelpEntity));//赋值
			}
		}
	},
	valueForm : null,
	getValueForm:function(){
		if(Ext.isEmpty(this.valueForm)){
			this.valueForm =Ext.create('Foss.baseinfo.systemHelp.ValueForm');
		}
		return this.valueForm;
	},
	//提交数据的方法
	commitValue:function(){
		var me=this;
		if(me.getValueForm().getForm().isValid()){
			var valueModel =new Foss.baseinfo.systemHelp.SystemHelpEntity(me.systemHelpEntity); 
			var form =me.getValueForm().getForm();
			//公告内容的验证
			if(Ext.isEmpty(form.findField('systemHelp').getValue())){
				Ext.Msg.show({
                    title:'提示信息',
                    msg:'公告内容为空',
                    minWidth:200,
                    modal:true,
                    buttons:Ext.Msg.OK 
                  });
				return;
			}
			form.updateRecord(valueModel);
			var params ={'systemHelpVo':{'systemHelpDto':{'systemHelpEntity':valueModel.data}}};
			var successFun = function(json){
				 Ext.Msg.show({
                     title:'提示信息',
                     msg:'修改系统帮助成功',
                     minWidth:200,
                     modal:true,
                     buttons:Ext.Msg.OK 
                   });
				me.parent.getPagingToolbar().moveFirst();
				me.close();
			};
			var failureFun =function(json){
				Ext.Msg.show({
                    title:'提示信息',
                    msg:json.message,
                    minWidth:200,
                    modal:true,
                    buttons:Ext.Msg.OK 
                  });
			};
			var url =baseinfo.realPath('updateSystemHelp.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	 constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [ me.getValueForm()];
			me.fbar = [{
				text : '取消',//取消
				handler :function(){
					me.close();
				} 
			},{
				text : '重置',//重置
				handler :function(){
					//重新加载之前的数据
					me.getValueForm().getForm().loadRecord(new Foss.baseinfo.systemHelp.SystemHelpEntity(me.systemHelpEntity));
				} 
			},{
				text : '确定',//确定
				cls:'yellow_button',
				handler :function(){
					me.commitValue();
				} 
			}];
			me.callParent([cfg]);
		}
});
/**
 * -------------查询详情的window-------------------------
 */
Ext.define('Foss.baseinfo.systemHelp.QuerySystemHelpWindow',{
	extend:'Ext.window.Window',
	title : '查看详情',//
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	//关闭动作为hide，默认为destroy
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :850,
	height :600,
	listeners:{
		//隐藏窗口时 清空里面的数据
		beforehide:function(me){
			me.getValueForm().getForm().reset();//清空数据
		},
		//显示之前 先加载数据
		beforeshow:function(me){
			//判断数据是否不为空
			if(!Ext.isEmpty(me.systemHelpEntity)){
				var form = me.getValueForm().getForm();
				//把数据显示在界面
				form.loadRecord(new Foss.baseinfo.systemHelp.SystemHelpEntity(me.systemHelpEntity));//赋值
			}
		}
	},
	valueForm : null,
	getValueForm:function(){
		if(Ext.isEmpty(this.valueForm)){
			this.valueForm =Ext.create('Foss.baseinfo.systemHelp.QueryInfoForm');
		}
		return this.valueForm;
	},
	 constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [ me.getValueForm()];
			me.fbar = [{
				text : '确定',//确定
				cls:'yellow_button',
				margin:'0 0 0 235',
				handler :function(){
					me.close();
				} 
			}];
			me.callParent([cfg]);
		}
});
/**
 * 添加或编辑的Form
 */
Ext.define('Foss.baseinfo.systemHelp.ValueForm',{
	extend:'Ext.form.Panel',
	frame: true,
	height:480,
	collapsible: true,
	defaults:{
		colspan : 1,
		margin : '10 15 5 15',
		maxLength:50,
		allowBlank:false,
		labelWidth:60,
		anchor : '100%'
	},
	defaultType:'textfield',
	layout: {
        type: 'table',
        columns: 1
    },
	constructor:function(config){
		var me =this,
		cfg =Ext.apply({},config);
		me.items=[{
			name:'topic',
			allowBlank:false,
			width:300,
			fieldLabel: '标题',//
			xtype : 'textfield'
				
		},{
			xtype:'container' 
		},{
			fieldLabel: '内容',//内容
			name:'systemHelp',
		    width:750,
		    height:350,
		    autoScroll:true,
			xtype:'htmleditor',
			 plugins: new Ext.ux.plugins.HtmlEditorImageInsert({
                 popTitle: '',
                 popMsg: '',
                 popWidth: 400,
                 popValue: ''
             })
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查询信息详情的Form
 */
Ext.define('Foss.baseinfo.systemHelp.QueryInfoForm',{
	extend:'Ext.form.Panel',
	title:'查看详情', 
	frame: true,
	height:480,
	collapsible: true,
	defaults:{
		colspan : 1,
		margin : '10 15 5 15',
		maxLength:50,
		allowBlank:false,
		labelWidth:60,
		anchor : '100%'
	},
	defaultType:'displayfield',
	layout: {
        type: 'table',
        columns: 2
    },
	constructor:function(config){
		var me =this,
		cfg =Ext.apply({},config);
		me.items=[{
//				name: 'modifyUser',
//				fieldLabel:'公布人',//公布人
//				labelWidth: 85,
//				columWidth:'100%',
//			},{
//				xtype:'combobox',
//				displayField:'valueName',
//				valueField:'valueCode',
//				queryMode:'local',
//				triggerAction:'all',
//				disabled:true,
//				name: 'active',
//				fieldLabel:'是否有效',//公告信息
//				store:FossDataDictionary.getDataDictionaryStore('FOSS_ACTIVE'),
//			},{
				name: 'topic',
				fieldLabel:'标题',//标题
				labelWidth: 65,
				colspan : 2,
			},{
				width:750,
				height:350,
				name:'systemHelp',
				autoScroll:true,
				xtype:'htmleditor',
				fieldLabel:'内容',//内容
				labelWidth: 65,
				colspan : 2,
			}];
		me.callParent([cfg]);
		}
});

//--------------------------公告页面---------------
Ext.onReady(function() {
	//初始化Ext.QuickTips，以使得tip提示可用
	Ext.QuickTips.init();
	//获取现有组件的id
	if (Ext.getCmp('T_baseinfo-systemHelp_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.baseinfo.systemHelp.querySystemHelpForm');//查询FORM
	var systemHelpGrid = Ext.create('Foss.baseinfo.systemHelp.systemHelpGridPanel');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-systemHelp_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getSystemHelpGrid : function() {
			return systemHelpGrid;
		},
		items : [queryForm, systemHelpGrid],
		renderTo : 'T_baseinfo-systemHelp-body'
	});
});


//---------------------------------------------图片-----------------------------------
Ext.namespace('Ext.ux','Ext.ux.plugins');
Ext.ux.plugins.HtmlEditorImageInsert=function(config) {
    config=config||{};
    Ext.apply(this,config);
    this.init=function(htmlEditor) {
        this.editor=htmlEditor;
        this.editor.on('render',onRender,this);
    };
    this.imageInsertConfig={
        popTitle: config.popTitle||'Image URL',
        popMsg: config.popMsg||'Please select the URL of the image you want to insert:',
        popWidth: config.popWidth||350,
        popValue: config.popValue||''
    }
    this.imageInsert=function() {
        
    	var editor = this.editor;
        var imgform = new Ext.FormPanel({
            region : 'center',
            labelWidth : 55,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            autoScroll : true,
            border : false,
            fileUpload : true,
            items : [{
            	 xtype: 'filefield',
                 name: 'photo',
                 fieldLabel: '图片',
                 labelWidth: 50,
                 msgTarget: 'side',
                 allowBlank: false,
                 blankText : '文件不能为空',
                 anchor: '90%',
                 buttonText: '浏览'
            }],
            buttons : [{
                text : '上传',
                //type : 'submit',
                handler : function() {
                  	var form = this.up('form').getForm();
                    if (!form.isValid()) {return;}
                    form.submit({
                        url: baseinfo.realPath('uploadSystemHelpPic.action'),
                        //waitMsg: '正在保存文件...',
                        standardSubmit: true,
                        success: function(fp, o) {
                     
                          var relativePath = o.result.relativePath;                          
                          var allPath = baseinfo.realPath("reviewSystemHelpImg.action?relativePath=" + relativePath);
                        	
                          var element = document.createElement("img");
                          element.src = allPath;
                          editor.insertAtCursor(element.outerHTML);
                         
//                          Ext.Msg.show({
//                              title:'提示信息',
//                              msg:'图片上传成功',
//                              minWidth:200,
//                              modal:true,
//                              buttons:Ext.Msg.OK 
//                            });
                            
                          form.findField('photo').setRawValue('');
                          win.close(this);
                        }
                    });
                }
            }, {
                text : '关闭',
                type : 'submit',
                handler : function() {
                    win.close(this);
                }
            }]
        });
        
        var win = new Ext.Window({
            title : "上传图片",
            width : 500,
            height : 200,
            modal : true,
            border : false,
            iconCls : 'deppon_icons_import',
            layout : "fit",
            items : imgform

        });
        win.show(); 
    }
function onRender(){
   if( ! Ext.isSafari){
    this.editor.toolbar.add({
     itemId : 'htmlEditorImage',
     iconCls : 'deppon_icons_import',
     enableToggle : true,
     scope : this,
     handler : function(){
      this.imageInsert();
     },
     clickEvent : 'mousedown',
     tooltip : config.buttonTip ||
     {
      title : '图片',
      //text : '插入图片到编辑器',
      iconCls : 'deppon_icons_import'
     },
     tabIndex :- 1
    });
   }
}
}
