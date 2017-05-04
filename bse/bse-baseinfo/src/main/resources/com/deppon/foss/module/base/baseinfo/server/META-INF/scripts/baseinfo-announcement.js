/**
 * 系统公告 foss-zengjunfan
 * 
 */
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
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
baseinfo.getStartDate = function(date, day) {
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
baseinfo.getEndDate = function(date, day) {
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

// --------------------------------Model和store的定义-------------------------------
// 定义公告model
Ext.define('Foss.baseinfo.announcement.AnnouncementEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'topic'// 标题
	}, {
		name : 'announcement'// 公告内容
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

// 定义公告的Store
Ext.define('Foss.baseinfo.announcement.AnnouncementStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.announcement.AnnouncementEntity',// 绑定公告MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../baseinfo/queryAnnouncement.action',// 查询的url
		reader : {
			type : 'json',
			root : 'vo.announcementEntitys',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
/**
 * ----------------------------查询表单------------------------
 */
// 查询表单
Ext.define('Foss.baseinfo.announcement.queryAnnouncementForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.announcement.i18n('foss.baseinfo.queryCondition'),// 查询条件
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
			xtype: 'rangeDateField',
			fieldLabel: baseinfo.announcement.i18n('foss.baseinfo.announcement.createDate'),//发布时间,
			dateType: 'datetimefield_date97',
			fromName: 'startTime',
			toName: 'endTime',
			disallowBlank: true,
			editable:false,
			fromValue: Ext.Date.format(baseinfo.getStartDate(new Date(),-10),'Y-m-d H:i:s'),
			toValue: Ext.Date.format(baseinfo.getEndDate(new Date(),0),'Y-m-d H:i:s'),
			columnWidth: 0.45
		},{
			name:'topic',
			columnWidth: 0.275,
			fieldLabel:baseinfo.announcement.i18n('foss.baseinfo.announcement.topic'),//公告标题
			xtype :'textfield'
		},{
			name: 'createUser',
			columnWidth: 0.275,
	        fieldLabel: baseinfo.announcement.i18n('foss.baseinfo.announcement.createUser'),//发布人
	        xtype :'textfield'
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			name: 'active',
			fieldLabel: baseinfo.announcement.i18n('foss.baseinfo.announcement.active'),//公告状态
			columnWidth: 0.275,
			value:'ALL',
			store:FossDataDictionary.getDataDictionaryStore('FOSS_ACTIVE', null, {
				'valueCode': 'ALL',
	            'valueName': baseinfo.announcement.i18n('foss.baseinfo.announcement.all')
			})	
		},{
			xtype:'container' 
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.announcement.i18n('foss.baseinfo.reset'),//重置
				  disabled:!baseinfo.announcement.isPermission('announcement/announceRestButton'),
				  hidden:!baseinfo.announcement.isPermission('announcement/announceRestButton'),
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
				  text : baseinfo.announcement.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.announcement.isPermission('announcement/announceRestButton'),
				  hidden:!baseinfo.announcement.isPermission('announcement/announceRestButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					  //表单校验，分页查询
					if(me.getForm().isValid()){
						me.up().getAnnouncementGrid().getPagingToolbar().moveFirst();
					}
				  }
			  	}]
			}]; 
		me.callParent([cfg]);
	 }
});
/**
 * --------------------公告信息Grid----------------------------
 */
Ext.define('Foss.baseinfo.announcement.announcementGridPanel',{
	extend:'Ext.grid.Panel',
	title:baseinfo.announcement.i18n('foss.baseinfo.queryGrid'),//查询结果列表
	frame: true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.announcement.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//得到bbar,分页
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
	 // 废除公告的方法
    toVoid: function(){
    	var me =this;
    	var selections =me.getSelectionModel().getSelection();
    	if(selections.length<1){
    		baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.deleteNoticeMsg'));// 沒有选中一条要删除的公告
    		return;
    	}
    	var ids =new Array();
    	for(var i = 0 ; i<selections.length ; i++){
    		ids.push(selections[i].get('id'));
		}
    	baseinfo.showQuestionMes(baseinfo.announcement.i18n('foss.baseinfo.deleteWarnMsg'),function(e){// 是否要作废这些值？
    		if(e=='yes'){// 询问是否删除，是则发送请求
    			//循环判断是否选择的数据组有无效的数据存在
    			for(var i = 0 ; i<selections.length ; i++){
    				if(selections[i].get('active')== 'N'){
    					baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.announcement.existNoActiveRecord'));
    					return;
    				}
    			}	
    			var params = {"vo":{"idlist":ids}};
    		 	var successFun = function(json){
    				baseinfo.showInfoMes(json.message);
    				me.getPagingToolbar().moveFirst();
    			};
    			var failureFun =function(json){
    				if(Ext.isEmpty(json)){
    					baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.requestTimeout'));// 请求超时
    				}else{
    					baseinfo.showErrorMes(json.message);
    				}
    			};
    			var url = baseinfo.realPath('deleteAnnouncement.action');	
    			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    			}
    	})
    },
    // 新增公告
	valueAddWindow:null,
	getValueAddWindow : function() {
		if (this.valueAddWindow == null) {
			this.valueAddWindow = Ext.create('Foss.baseinfo.announcement.ValueAddWindow');
			this.valueAddWindow.parent = this;
		}
		return this.valueAddWindow;
	},
	//查询公告
	queryWin:null,
	getQueryWin : function(){
		if (this.queryWin == null) {
			this.queryWin = Ext.create('Foss.baseinfo.announcement.QueryAnnouncementWindow');
			this.queryWin.parent = this;
		}
		return this.queryWin;
	},
	// 值修改
	valueUpdateWindow:null,
	getValueUpdateWindow : function() {
		if (this.valueUpdateWindow == null) {
			this.valueUpdateWindow = Ext.create('Foss.baseinfo.announcement.ValueUpdateWindow');
			this.valueUpdateWindow.parent = this;
		}
		return this.valueUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
			me.columns = [{
				xtype: 'rownumberer',
				width:40,
				text : baseinfo.announcement.i18n('foss.baseinfo.sequence')//序号
			},{
				align : 'center',
				xtype : 'actioncolumn',
				text : baseinfo.announcement.i18n('foss.baseinfo.operate'),//操作
				items:[{
						iconCls: 'deppon_icons_edit',
						tooltip:baseinfo.announcement.i18n('foss.baseinfo.edit'),//编辑
						width:30,
						getClass:function(value,metadata,record,rowIndex,colIndex,store){
							//获取信息状态
							var active =FossDataDictionary.rendererDisplayToSubmit(record.get('active'),'FOSS_ACTIVE');
							if(active == 'Y'){
								return 'deppon_icons_edit';
							}else{
								return 'deppon_icons_edit_hide';
							}
						},
						handler:function(grid,rowIndex,colIndex){
							//获取这条记录数
							var record =grid.getStore().getAt(rowIndex);
							var announcementId =record.get('id');//获取记录的id
							var params = {'vo':{'announcementDto':{'announcementEntity':{'id':announcementId}}}};
		    				var successFun = function(json){
		    					var updateWindow = me.getValueUpdateWindow();//获得修改窗口
		    					updateWindow.announcementEntity = json.vo.announcementDto.announcementEntity;//值
		    					updateWindow.show();//显示修改窗口
		    				};
		    				var failureFun = function(json){
		    					//传过来的数据为空的话，链接超时:
		    					if(Ext.isEmpty(json)){
		    						baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.requestTimeout'));// 请求超时
		    					}else{
		    						baseinfo.showErrorMes(json.message);
		    					}
		    				};
		    				var url = baseinfo.realPath('queryAnnouncementById.action');
		    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
						}					
					},{
						iconCls: 'deppon_icons_showdetail',
						tooltip:baseinfo.announcement.i18n('foss.baseinfo.details'),//查询详情
						disabled:!baseinfo.announcement.isPermission('announcement/announceDetailsButton'),
						width:30,
						handler:function(grid,rowIndex,colIndex){
							var record =grid.getStore().getAt(rowIndex);
							var announcementId =record.get('id');//获取记录的id
							var params = {'vo':{'announcementDto':{'announcementEntity':{'id':announcementId}}}};
							var successFun = function(json){
		    					var queryWin = me.getQueryWin();//获得修改窗口
		    					queryWin.announcementEntity = json.vo.announcementDto.announcementEntity;//值
		    					queryWin.show();//显示修改窗口
		    				};
		    				var failureFun = function(json){
		    					//传过来的数据为空的话，链接超时:
		    					if(Ext.isEmpty(json)){
		    						baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.requestTimeout'));// 请求超时
		    					}else{
		    						baseinfo.showErrorMes(json.message);
		    					}
		    				};
		    				var url = baseinfo.realPath('queryAnnouncementById.action');
		    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
						}
					},{
					iconCls: 'deppon_icons_cancel',
					tooltip:baseinfo.announcement.i18n('foss.baseinfo.void'),//删除
					width:30,
					getClass:function(value,metadata,record,rowIndex,colIndex,store){
						//获取信息状态
						var active =FossDataDictionary.rendererDisplayToSubmit(record.get('active'),'FOSS_ACTIVE');
						if(active == 'Y'){
							return 'deppon_icons_cancel';
						}else{
							return 'deppon_icons_cancel_hide';
						}
					},
					handler:function(grid,rowIndex,colIndex){
						baseinfo.showQuestionMes(baseinfo.announcement.i18n('foss.baseinfo.deleteWarnMsg'),function(e){// 是否要作废这些值？
				    		if(e=='yes'){// 询问是否删除，是则发送请求
				    			
				    			//获取选中的数据
				    			var  record =me.getStore().getAt(rowIndex);
				    			var ids =new Array();
				    			ids.push(record.get('id'));
				   
				    			var params = {"vo":{"idlist":ids}};
				    			var successFun = function(json){
				    				baseinfo.showInfoMes(json.message);
				    				me.getPagingToolbar().moveFirst();
				    			};
				    			var failureFun =function(json){
				    				if(Ext.isEmpty(json)){
				    					baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				    				}else{
				    					baseinfo.showErrorMes(json.message);
				    				}
				    			};
				    			var url =baseinfo.realPath('deleteAnnouncement.action');	
				    			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
				    			}
				    	}); 
					}
				}]
			},{
					width:200,
					text : baseinfo.announcement.i18n('foss.baseinfo.announcement.topic'),//公告标题
					dataIndex : 'topic',
			},{
					width:360,
					text : baseinfo.announcement.i18n('foss.baseinfo.announcement.context'),// 公告内容
					dataIndex : 'announcement',
			},{		
					width:160,
					text : baseinfo.announcement.i18n('foss.baseinfo.announcement.createUser'),// 发布人
					dataIndex : 'createUser',
			},{		
					width:160,
					text : baseinfo.announcement.i18n('foss.baseinfo.announcement.createDate'),// 发布时间
					xtype: 'datecolumn',   
					format:'Y-m-d H:i:s',
					dataIndex : 'createDate',
			}];
			//表单缓存
			me.store = Ext.create('Foss.baseinfo.announcement.AnnouncementStore',{
				autoLoad : false,//不自动加载
				pageSize : 20,
				listeners: {
					//查询事件的监听
					beforeload: function(store, operation, eOpts){
						var queryForm = Ext.getCmp('T_baseinfo-announcement_content').getQueryForm();
						if(queryForm!=null){
							Ext.apply(operation,{
								params : {
									'vo.announcementDto.startTime':
										queryForm.getForm().findField('startTime').getValue(),//查询开始时间
									'vo.announcementDto.endTime':
										queryForm.getForm().findField('endTime').getValue(),//查询结束时间
									'vo.announcementDto.announcementEntity.topic':
										queryForm.getForm().findField('topic').getValue(),//标题
									'vo.announcementDto.announcementEntity.createUser':
										queryForm.getForm().findField('createUser').getValue(),//发布人
									'vo.announcementDto.announcementEntity.active':
										queryForm.getForm().findField('active').getValue(),//状态
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
					text : baseinfo.announcement.i18n('foss.baseinfo.void'),//作废
					disabled:!baseinfo.announcement.isPermission('announcement/announceVoidButton'),
					hidden:!baseinfo.announcement.isPermission('announcement/announceVoidButton'),
					handler :function(){
						me.toVoid();
					} 
				},{
					text : baseinfo.announcement.i18n('foss.baseinfo.add'),//新增
					disabled:!baseinfo.announcement.isPermission('announcement/announceAddButton'),
					hidden:!baseinfo.announcement.isPermission('announcement/announceAddButton'),
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
 * ---------------------------------公告新增的window------------------
 */
Ext.define('Foss.baseinfo.announcement.ValueAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.announcement.i18n('foss.baseinfo.announcement.addAnnouncement'),//新增公告信息
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素（Foss.baseinfo.announcement.announcementGridPanel）
	//关闭动作为hide，默认为destroy
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :400,
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
			this.valueForm = Ext.create('Foss.baseinfo.announcement.ValueForm');
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
			var valueModel =Ext.create('Foss.baseinfo.announcement.AnnouncementEntity'); //创建新一个Announcement的model
			var form =me.getValueForm().getForm();
			//公告内容的验证
			if(Ext.isEmpty(form.findField('announcement').getValue())){
				baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.announcement.NullContext'));// 公告内容为空
				return;
			}
			form.updateRecord(valueModel);
			var params ={'vo':{'announcementDto':{'announcementEntity':valueModel.data}}};
			var successFun = function(json){
				baseinfo.showInfoMes(json.message);
				me.parent.getPagingToolbar().moveFirst();
				me.close();
			};
			var failureFun =function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				}else{
					baseinfo.showErrorMes(json.message);
				}
			};
			var url =baseinfo.realPath('addAnnouncement.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getValueForm()];
		me.fbar = [{
			text : baseinfo.announcement.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.announcement.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.resetValue();
			} 
		},{
			text : baseinfo.announcement.i18n('foss.baseinfo.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 235',
			handler :function(){
				me.commitValue();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * -----------------------公告修改的window-------------
 */
Ext.define('Foss.baseinfo.announcement.ValueUpdateWindow',{
	extend:'Ext.window.Window',
	title : baseinfo.announcement.i18n('foss.baseinfo.announcement.updateAnnouncement'),//编辑公告信息
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
	width :700,
	height :400,
	listeners:{
		//隐藏窗口时 清空里面的数据
		beforehide:function(me){
			me.getValueForm().getForm().reset();//清空数据
		},
		//显示之前 先加载数据
		beforeshow:function(me){
			//判断数据是否不为空
			if(!Ext.isEmpty(me.announcementEntity)){
				var form = me.getValueForm().getForm();
				//把数据显示在界面
				form.loadRecord(new Foss.baseinfo.announcement.AnnouncementEntity(me.announcementEntity));//赋值
			}
		}
	},
	valueForm : null,
	getValueForm:function(){
		if(Ext.isEmpty(this.valueForm)){
			this.valueForm =Ext.create('Foss.baseinfo.announcement.ValueForm');
		}
		return this.valueForm;
	},
	//提交数据的方法
	commitValue:function(){
		var me=this;
		if(me.getValueForm().getForm().isValid()){
			var valueModel =new Foss.baseinfo.announcement.AnnouncementEntity(me.announcementEntity); 
			var form =me.getValueForm().getForm();
			//公告内容的验证
			if(Ext.isEmpty(form.findField('announcement').getValue())){
				baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.announcement.NullContext'));// 公告内容为空
				return;
			}
			form.updateRecord(valueModel);
			var params ={'vo':{'announcementDto':{'announcementEntity':valueModel.data}}};
			var successFun = function(json){
				baseinfo.showInfoMes(json.message);
				me.parent.getPagingToolbar().moveFirst();
				me.close();
			};
			var failureFun =function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes(baseinfo.announcement.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				}else{
					baseinfo.showErrorMes(json.message);
				}
			};
			var url =baseinfo.realPath('updateAnnouncement.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	 constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [ me.getValueForm()];
			me.fbar = [{
				text : baseinfo.announcement.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.close();
				} 
			},{
				text : baseinfo.announcement.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					//重新加载之前的数据
					me.getValueForm().getForm().loadRecord(new Foss.baseinfo.announcement.AnnouncementEntity(me.announcementEntity));
				} 
			},{
				text : baseinfo.announcement.i18n('foss.baseinfo.confirm'),//确定
				cls:'yellow_button',
				margin:'0 0 0 235',
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
Ext.define('Foss.baseinfo.announcement.QueryAnnouncementWindow',{
	extend:'Ext.window.Window',
	title : baseinfo.announcement.i18n('foss.baseinfo.announcement.QueryInfo'),//查看详情
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
	width :700,
	height :400,
	listeners:{
		//隐藏窗口时 清空里面的数据
		beforehide:function(me){
			me.getValueForm().getForm().reset();//清空数据
		},
		//显示之前 先加载数据
		beforeshow:function(me){
			//判断数据是否不为空
			if(!Ext.isEmpty(me.announcementEntity)){
				var form = me.getValueForm().getForm();
				//把数据显示在界面
				form.loadRecord(new Foss.baseinfo.announcement.AnnouncementEntity(me.announcementEntity));//赋值
			}
		}
	},
	valueForm : null,
	getValueForm:function(){
		if(Ext.isEmpty(this.valueForm)){
			this.valueForm =Ext.create('Foss.baseinfo.announcement.QueryInfoForm');
		}
		return this.valueForm;
	},
	 constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [ me.getValueForm()];
			me.fbar = [{
				text : baseinfo.announcement.i18n('foss.baseinfo.confirm'),//确定
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
Ext.define('Foss.baseinfo.announcement.ValueForm',{
	extend:'Ext.form.Panel',
	frame: true,
	height:280,
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
			fieldLabel: baseinfo.announcement.i18n('foss.baseinfo.announcement.topic'),//标题
			xtype : 'textfield'
				
		},{
			xtype:'container' 
		},{
			fieldLabel: baseinfo.announcement.i18n('foss.baseinfo.announcement.context'),//内容
			name:'announcement',
		    width:620,
			xtype:'htmleditor',
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查询公告信息详情的Form
 */
Ext.define('Foss.baseinfo.announcement.QueryInfoForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.announcement.i18n('foss.baseinfo.announcement.announcementInfo'), 
	frame: true,
	height:250,
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
				name: 'modifyUser',
				fieldLabel:baseinfo.announcement.i18n('foss.baseinfo.announcement.createUser'),//公布人
				labelWidth: 85,
				columWidth:'100%',
			},{
				xtype:'combobox',
				displayField:'valueName',
				valueField:'valueCode',
				queryMode:'local',
				triggerAction:'all',
				disabled:true,
				name: 'active',
				fieldLabel:baseinfo.announcement.i18n('foss.baseinfo.announcement.active'),//公告信息
				store:FossDataDictionary.getDataDictionaryStore('FOSS_ACTIVE'),
			},{
				name: 'topic',
				fieldLabel:baseinfo.announcement.i18n('foss.baseinfo.announcement.topic'),//标题
				labelWidth: 65,
				colspan : 2,
			},{
				name: 'announcement',
				fieldLabel:baseinfo.announcement.i18n('foss.baseinfo.announcement.context'),//内容
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
	if (Ext.getCmp('T_baseinfo-announcement_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.baseinfo.announcement.queryAnnouncementForm');//查询FORM
	var announcementGrid = Ext.create('Foss.baseinfo.announcement.announcementGridPanel');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-announcement_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getAnnouncementGrid : function() {
			return announcementGrid;
		},
		items : [queryForm, announcementGrid],
		renderTo : 'T_baseinfo-announcement-body'
	});
});

