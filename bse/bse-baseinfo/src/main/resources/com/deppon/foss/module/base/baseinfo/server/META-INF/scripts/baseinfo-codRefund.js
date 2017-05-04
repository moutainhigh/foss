/**
 * 代收货款打包退款基础信息及附件
 */
baseinfo.codRefund.additionalShow = function(targetFile){
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}
	
	Ext.Ajax.request({
		url : baseinfo.realPath('downloadAdditionalPath.action'),
		form : Ext.fly('downloadAttachFileForm'),
		method :'POST',
		params : {
			'additionalName':targetFile
		},
		isUpload:true,
	});
};

baseinfo.codRefund.additionalDel = function(targetFile){
	del(targetFile,targetFile+'Button');
	Ext.Ajax.request({
		url : baseinfo.realPath('deleteAdditional.action'),
		method :'POST',
		params : {
			'additionalNameDel':targetFile
		},
	});
};
/**
 * Model
 */
Ext.define('Foss.baseinfo.codRefund.CodRefundModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'deptName',//部门名称
		type:'string'
	},{
		name:'deptCode',//部门编码
		type:'string'
	},{
		name:'customerName',//用户名称
		type:'string'
	},{
		name:'customerCode',//用户编码
		type:'string'
	},{
		name:'operatorName',//操作人名称
		type:'string'
	},{
		name:'operatorCode',//操作人编码
		type:'string'
	},{
		name:'timeLimit',//期限
		type:'string'
	},{
		name:'timeLimitStart',//期限开始时间
		type:'date'
	},{
		name:'timeLimitEnd',//期限结束时间
		type:'date'
	},{
		name:'enteringTime',//录入时间
		defaultValue : new Date(),
		convert : dateConvert,
		type:'date'
	},{
		name:'enteringTimeStart',//录入开始时间
		type:'date'
	},{
		name: 'enteringTimeEnd',// 录入结束时间
		type:'date'
	},{
		name: 'additional',// 附件
		type: 'string'
	},{
		name:'remark',//备注
		type:'string'
	}]
});
/**
 * Store
 */
Ext.define('Foss.baseinfo.codRefund.codRefundStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.codRefund.CodRefundModel',
	pageSize:10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryCodRefundListByCondition.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'codRefundVo.codRefundEntityList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
/**
 * =====================================主界面============================
 */
/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.codRefund.QueryCodRefundForm',{
	extend:'Ext.form.Panel',
	title : '合并退款客户信息查询',//baseinfo.codRefund.i18n('baseinfo.codRefund.queryCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height :180,
	defaultType : 'textfield',
	 //列布局
	layout:'column',
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			xtype:'commoncustomerselector',
			fieldLabel:'客户编码',//baseinfo.codRefund.i18n('baseinfo.codRefund.customerCode'),//客户编码
			name:'customerCode',
			columnWidth:0.35
		},{
			xtype:'dynamicorgcombselector',
			fieldLabel:'部门名称',//baseinfo.codRefund.i18n('foss.baseinfo.orgName'),//部门名称
			type : 'ORG',
			name:'deptCode',
			columnWidth:0.35
		},{
		    xtype: 'rangedatefield',
		    fieldLabel:'录入时间',//baseinfo.codRefund.i18n('baseinfo.codRefund.enteringTime'),//录入时间
		    dateType: 'datefield',
		    //区间间隔，以天为单位。
		    dateRange: 99999,
		    //起始日期组件的name属性。
		    fromName: 'enteringTimeStart',
		    //终止日期组件的name属性。
		    toName: 'enteringTimeEnd',
		    //起始日期组件的初始值,不允许为空
		    //disallowBlank: true,
		    columnWidth: 0.55
		},{
			xtype:'container' 
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : '重置',//baseinfo.codRefund.i18n('foss.baseinfo.reset'),//重置
				  disabled:!baseinfo.codRefund.isPermission('codRefund/codRefundQueryButton'),
				  hidden:!baseinfo.codRefund.isPermission('codRefund/codRefundQueryButton'),
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
				  text : '查询',//baseinfo.codRefund.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.codRefund.isPermission('codRefund/codRefundQueryButton'),
				  hidden:!baseinfo.codRefund.isPermission('codRefund/codRefundQueryButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					  //表单校验，分页查询
					if(me.getForm().isValid()){
						me.up().getQueryCodRefundGrid().getPagingToolbar().moveFirst();
					}
				  }
			  	}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查询结果列表
 */
Ext.define('Foss.baseinfo.codRefund.QueryCodRefundGrid',{
	extend:'Ext.grid.Panel',
	title:'合并退款客户信息列表',//baseinfo.codRefund.i18n('baseinfo.codRefund.queryGrid'),//查询结果列表
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:'查询结果为空',//baseinfo.codRefund.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				plugins: 'pagesizeplugin'
			});
		}
		return this.pagingToolbar;
	},
	//新增窗口
	addCodRefundWin:null,
	getAddCodRefundWin:function(){
		this.addCodRefundWin=Ext.create('Foss.baseinfo.codRefund.AddCodRefundWin');
		this.addCodRefundWin.parent=this;
		return this.addCodRefundWin;
	},
	//修改窗口
	updateCodRefundWin:null,
	getUpdateCodRefundWin:function(){
		this.updateCodRefundWin =Ext.create('Foss.baseinfo.codRefund.UpdateCodRefundWin');
		this.updateCodRefundWin.parent =this;
		return this.updateCodRefundWin;
	},
	//查看窗口
	codRefundShowWindow : null,
	getCodRefundShowWindow : function () {
		if (this.codRefundShowWindow == null) {
			this.codRefundShowWindow = Ext.create('Foss.baseinfo.codRefund.CodRefundShowWindow');
			this.codRefundShowWindow.parent = this; //父元素
		}
		this.codRefundShowWindow.getCodRefundForm().getForm().getFields().each(function (item) {
			item.setReadOnly(true);
		});
		return this.codRefundShowWindow;
	},
	//导出
	exportToExcel: function () {
		var me = this,
		queryFormValue = me.up().getQueryCodRefundForm().getForm().getValues();
//		var orgCode = queryForm.findField('organizationCode').getValue();
//		if(Ext.isEmpty(orgCode)) {
//			Ext.MessageBox.alert(baseinfo.codRefund.i18n('foss.baseinfo.tipInfo'),
//					baseinfo.codRefund.i18n('foss.baseinfo.import.tip'));
//			return;
//		}
		Ext.MessageBox.buttonText.yes = '确认';//baseinfo.codRefund.i18n('foss.baseinfo.confirm');
		Ext.MessageBox.buttonText.no = '取消';//baseinfo.codRefund.i18n('foss.baseinfo.cancel');
		if (!Ext.fly('downloadCodRefundForm')) {
			var frm = document.createElement('form');
			frm.id = 'downloadCodRefundForm';
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		Ext.Msg.confirm('提示信息', '确定要导出查询结果吗?',
			function (btn, text) {//baseinfo.codRefund.i18n('foss.baseinfo.tipInfo');baseinfo.codRefund.i18n('foss.baseinfo.exportMsg'),
				if (btn == 'yes') {
					var params = {
							'codRefundVo.codRefundEntity.customerCode':queryFormValue.customerCode,
							'codRefundVo.codRefundEntity.deptCode':queryFormValue.deptCode,
							'codRefundVo.codRefundEntity.enteringTimeStart':queryFormValue.enteringTimeStart,
							'codRefundVo.codRefundEntity.enteringTimeEnd':queryFormValue.enteringTimeEnd
					};
					Ext.Ajax.request({
						url: baseinfo.realPath('exportCodRefund.action'),
						form: Ext.fly('downloadCodRefundForm'),
						params: params,
						method: 'post',
						isUpload: true,
						failure: function (response) {
							Ext.MessageBox.alert('提示信息',//baseinfo.codRefund.i18n('foss.baseinfo.tipInfo')
								'导出失败');//baseinfo.codRefund.i18n('foss.baseinfo.exportFailed')
						}
					});
				}
			}
		);
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.columns =[{xtype: 'rownumberer',
			width:40,
			text : '序号'
		},{
			align:'center',
			xtype:'actioncolumn',
			flex:1,
			text : '操作',//baseinfo.codRefund.i18n('foss.baseinfo.operate'),//操作
			items:[{
				iconCls: 'deppon_icons_edit',
				tooltip:'编辑',//baseinfo.codRefund.i18n('foss.baseinfo.edit'),//编辑
				disabled:!baseinfo.codRefund.isPermission('codRefund/codRefundEditButton'),
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var rowModel =grid.getStore().getAt(rowIndex);
					var id = rowModel.get('id'); //详情id
					var params = {
						'codRefundVo' : {
							'codRefundEntity' : {
								'id' : id
							}
						}
					};
					var successFun = function (json) {
						var updateWin = me.getUpdateCodRefundWin(); //获得更新窗口
						updateWin.codRefundModel = json.codRefundVo.codRefundEntity; 
						updateWin.show(); //显示更新窗口
					};
					var failureFun = function (json) {
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes('请求超时'); //请求超时baseinfo.codRefund.i18n('foss.baseinfo.requestTimeout')
						} else {
							baseinfo.showErrorMes(json.message);
						}
					};
					var url = baseinfo.realPath('queryCodRefundAdditionalById.action');
					baseinfo.requestJsonAjax(url, params, successFun, failureFun);
				}
			},{
				iconCls: 'deppon_icons_cancel',
				tooltip:'作废',//baseinfo.codRefund.i18n('foss.baseinfo.void'),//作废
				disabled:!baseinfo.codRefund.isPermission('codRefund/codRefundVoidButton'),
				width:30,
				handler:function(grid,rowIndex,colIndex){
					var rowModel =grid.getStore().getAt(rowIndex);
					var customerCodes =new Array();
					customerCodes.push(rowModel.data.customerCode);
					//判断是否要作废
					baseinfo.showQuestionMes('作废数据后不可恢复，确定要作废么？',function(e){
						if(e=='yes'){//baseinfo.codRefund.i18n('foss.baseinfo.deleteWarnMsg')
							Ext.Ajax.request({
								jsonData:{'codRefundVo':{'codeList':customerCodes}},
								url:baseinfo.realPath('deleteCodRefunds.action'),
								success:function(response){
									var json =Ext.decode(response.responseText);
									baseinfo.showInfoMes(json.message);
									me.getPagingToolbar().moveFirst();
								},
								exception:function(response){
									var json =Ext.decode(response.responseText);
									baseinfo.showErrorMes(json.message);
								}
							});
						}
					});
				}
			},{
				iconCls : 'deppon_icons_showdetail',
				tooltip : '查看详情',//baseinfo.codRefund.i18n('foss.baseinfo.details'), //查看详情
				disabled:!baseinfo.codRefund.isPermission('codRefund/codRefundViewButton'),
				width : 30,
				handler : function (grid, rowIndex, colIndex) {
					//获取选中的数据
					var record = grid.getStore().getAt(rowIndex);
					var id = record.get('id'); //详情id
					var params = {
						'codRefundVo' : {
							'codRefundEntity' : {
								'id' : id
							}
						}
					};
					var successFun = function (json) {
						var showWindow = me.getCodRefundShowWindow(); //获得查看窗口
						showWindow.codRefundEntity = json.codRefundVo.codRefundEntity; 
						showWindow.show(); //显示查看窗口
					};
					var failureFun = function (json) {
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes('请求超时'); //请求超时baseinfo.codRefund.i18n('foss.baseinfo.requestTimeout')
						} else {
							baseinfo.showErrorMes(json.message);
						}
					};
					var url = baseinfo.realPath('queryCodRefundById.action');
					baseinfo.requestJsonAjax(url, params, successFun, failureFun);
					
				}
			}]
		},{
			text:'部门名称',//baseinfo.codRefund.i18n('foss.baseinfo.orgName'),//部门名称
			flex:1,
			dataIndex:'deptName'
		},{
			text:'客户编码',//baseinfo.codRefund.i18n('baseinfo.codRefund.customerCode'),//客户编码
			flex:1,
			dataIndex:'customerCode'
		},{
			text:'期限',//baseinfo.codRefund.i18n('baseinfo.codRefund.timeLimit'),//期限
			flex:2,
			dataIndex:'timeLimit'
		},{
			text:'操作员',//baseinfo.codRefund.i18n('baseinfo.codRefund.operator'),//操作员
			flex:1,
			dataIndex:'operatorName'
		},{
			text:'录入时间',//baseinfo.codRefund.i18n('baseinfo.codRefund.enteringTime'),//录入时间
			flex:1,
			dataIndex:'enteringTime',
			xtype: 'datecolumn',   
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value),'Y-m-d H:i:s');
				}else{
					return null;
				} 
			}
		}];
		me.store=Ext.create('Foss.baseinfo.codRefund.codRefundStore',{
			autoLoad:false,
			pageSize:10,
			listeners:{
				beforeload:function(store, operation, eOpts){
					var queryForm =Ext.getCmp('T_baseinfo-codRefund_content').getQueryCodRefundForm();
					if(queryForm !=null){
						var queryFormValue =queryForm.getForm().getValues();
						Ext.apply(operation,{
							params:{
								'codRefundVo.codRefundEntity.customerCode':queryFormValue.customerCode,
								'codRefundVo.codRefundEntity.deptCode':queryFormValue.deptCode,
								'codRefundVo.codRefundEntity.enteringTimeStart':queryFormValue.enteringTimeStart,
								'codRefundVo.codRefundEntity.enteringTimeEnd':queryFormValue.enteringTimeEnd
							}
						});
					}
				}
			}
		});
		me.tbar=[{
			xtype: 'button',
			text: '导出', //导出
			disabled: !baseinfo.codRefund.isPermission('codRefund/codRefundExportButton'),
			hidden: false,
			plugins: {
				ptype: 'buttondisabledplugin',
				seconds: 5
			},
			handler: me.exportToExcel,
			scope: me
		},{
			text:'新增', //新增
			disabled:!baseinfo.codRefund.isPermission('codRefund/codRefundAddButton'),
			hidden:!baseinfo.codRefund.isPermission('codRefund/codRefundAddButton'),
			handler:function(){
				me.getAddCodRefundWin().show();
			}
		}];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.bbar =me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});
/**
 * ----------------------------表单------------------------------
 */
/**
 * 新增Form
 */
Ext.define('Foss.baseinfo.codRefund.AddOrUpdateCodRefundForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : '合并退款客户信息',//baseinfo.codRefund.i18n('baseinfo.codRefund.addOrUpdateForm'),//(新增/修改)合并退款客户信息
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:80
	},
	isUpdate:false,//默认是新增form
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			fieldLabel:'客户编码',//baseinfo.codRefund.i18n('baseinfo.codRefund.customerCode'),//客户编码
			xtype:'commoncustomerselector',
			name:'customerCode',
			columnWidth: .45,
			allowBlank:false
		},{
			fieldLabel:'部门名称',//baseinfo.codRefund.i18n('foss.baseinfo.orgName'),//部门名称
			xtype:'dynamicorgcombselector',
			name:'deptCode',
			type:'ORG',
			columnWidth: .45,
			allowBlank:false
		 },{
			 fieldLabel:'期限',//baseinfo.codRefund.i18n('baseinfo.codRefund.timeLimit'),//期限
		     xtype: 'rangedatefield',
		     dateType: 'datefield',
		     //区间间隔，以天为单位。
		     dateRange: 99999,
		     //起始日期组件的name属性。
		     fromName: 'timeLimitStart',
		     //终止日期组件的name属性。
		     toName: 'timeLimitEnd',
		     //起始日期组件的初始值,
		     //不允许为空
		     allowBlank:false,
		     columnWidth: .90
		},{
			 xtype: 'filefield',
		     name: 'uploadFile',
		     readOnly:false,
		     allowBlank:false,
		     buttonOnly:false,
		     fieldLabel:'附件',//baseinfo.codRefund.i18n('baseinfo.codRefund.additional'),//附件
		     msgTarget: 'side',
		     cls:'uploadFile',
		     fileUpload:true,
		     buttonText: '浏览',//baseinfo.codRefund.i18n('foss.baseinfo.browse'),//浏览
		     columnWidth:.66
		},{
			xtype : 'button',
			columnWidth:.12,
			cls:'uploadFile',
			height:25,
			margin:3,
			text: '清除',//baseinfo.codRefund.i18n('baseinfo.codRefund.cleanUp'),//清除
			handler: function() {
				this.up('form').getForm().findField('uploadFile').reset();						
			}
		},{
			xtype : 'button',
			columnWidth:.12,
			height: 25,
			cls:'uploadFile',
			margin:3,
			text: '更多',//baseinfo.codRefund.i18n('baseinfo.codRefund.more'),//更多
			handler: function() {
				this.up('form').add({
					 xtype: 'filefield',
				     name: 'uploadFile',
				     //id:'T_baseinfo-additionalForm_content',
				     readOnly:false,
				     allowBlank:false,
				     buttonOnly:false,
				     fieldLabel:'附件',//baseinfo.codRefund.i18n('baseinfo.codRefund.additional'),//附件
				     msgTarget: 'side',
				     cls:'uploadFile',
				     //allowBlank: false,	
				     fileUpload:true,
				     buttonText: '浏览',//baseinfo.codRefund.i18n('foss.baseinfo.browse'),//浏览
				     columnWidth:.66
				});
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改Form
 */
Ext.define('Foss.baseinfo.codRefund.UpdateCodRefundForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : '合并退款客户信息',//baseinfo.codRefund.i18n('baseinfo.codRefund.addOrUpdateForm'),//(新增/修改)合并退款客户信息
	cls:'autoHeight',
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:80
	},
	isUpdate:false,//默认是新增form
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			fieldLabel:'客户编码',//baseinfo.codRefund.i18n('baseinfo.codRefund.customerCode'),//客户编码
			xtype:'commoncustomerselector',
			name:'customerCode',
			columnWidth: .45,
			allowBlank:false
		},{
			fieldLabel:'部门名称',//baseinfo.codRefund.i18n('foss.baseinfo.orgName'),//部门名称
			xtype:'dynamicorgcombselector',
			name:'deptCode',
			type:'ORG',
			columnWidth: .45,
			allowBlank:false
		 },{
			 fieldLabel:'期限',//baseinfo.codRefund.i18n('baseinfo.codRefund.timeLimit'),//期限
		     xtype: 'rangedatefield',
		     dateType: 'datefield',
		     //区间间隔，以天为单位。
		     dateRange: 99999,
		     //起始日期组件的name属性。
		     fromName: 'timeLimitStart',
		     //终止日期组件的name属性。
		     toName: 'timeLimitEnd',
		     //起始日期组件的初始值,
		     //不允许为空
		     allowBlank:false,
		     columnWidth: .90
		},{
			 xtype : 'displayfield',
		     name: 'additional',
		     readOnly:true,
		     fieldLabel:'附件',//baseinfo.codRefund.i18n('baseinfo.codRefund.additional'),//附件
		     columnWidth: .88,
		},{
			xtype : 'button',
			columnWidth:.12,
			cls:'uploadFile',
			margin:3,
			height: 25,
			text: '更多',//baseinfo.codRefund.i18n('baseinfo.codRefund.more'),//更多
			handler: function() {
				this.up('form').add({
					 xtype: 'filefield',
				     name: 'uploadFile',
				     readOnly:false,
				     buttonOnly:false,
				     fieldLabel:'附件',//baseinfo.codRefund.i18n('baseinfo.codRefund.additional'),//附件
				     msgTarget: 'side',
				     cls:'uploadFile',
				     fileUpload:true,
				     buttonText: '浏览',//baseinfo.codRefund.i18n('foss.baseinfo.browse'),//浏览
				     columnWidth:.85
				});
			}
		}];
		me.callParent([cfg]);
	}
});


//查看form
Ext.define('Foss.baseinfo.codRefund.ShowCodRefundForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : '查看合并退款客户信息',//baseinfo.codRefund.i18n('baseinfo.codRefund.showCodRefund'), 
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:100
	},
	standardSubmit:true,
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			fieldLabel:'部门名称',//baseinfo.codRefund.i18n('foss.baseinfo.orgName'),//部门名称
			flex:1,
			columnWidth:.6,
			readOnly : true,
			xtype : 'textfield',
			name:'deptName'
		},{
			fieldLabel:'客户编码',//baseinfo.codRefund.i18n('baseinfo.codRefund.customerCode'),//客户编码
			flex:1,
			columnWidth:.4,
			readOnly : true,
			xtype : 'textfield',
			name:'customerCode'
		},{
			fieldLabel:'期限',//baseinfo.codRefund.i18n('baseinfo.codRefund.timeLimit'),//期限
			flex:2,
			columnWidth:.6,
			readOnly : true,
			xtype : 'textfield',
			name:'timeLimit'
		},{
			fieldLabel:'操作员',//baseinfo.codRefund.i18n('baseinfo.codRefund.operator'),//操作员
			flex:1,
			columnWidth:.4,
			readOnly : true,
			xtype : 'textfield',
			name:'operatorName'
		},{
			fieldLabel:'录入时间',//baseinfo.codRefund.i18n('baseinfo.codRefund.enteringTime'),//录入时间
			flex:1,
			columnWidth:.6,
			readOnly : true,
			xtype : 'textfield',
			name:'enteringTime',
		},{
			fieldLabel:'附件',//baseinfo.codRefund.i18n('baseinfo.codRefund.additional'),//附件
			flex:1,
			columnWidth:.8,
			xtype : 'displayfield',
			name:'additional',
		}];
		me.callParent([cfg]);
	}
});

/**
 * ---------------------------窗口-----------------------------
 */
/**
 * 新增window
 */
Ext.define('Foss.baseinfo.codRefund.AddCodRefundWin',{
	extend:'Ext.window.Window',
	title : '新增合并退款客户信息',//baseinfo.codRefund.i18n('baseinfo.codRefund.addCodRefund'),//新增合并退款客户信息
	closable : true,
	modal : true,
	resizable:false,
	autoScroll: true,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	autoDestroy:true,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :600,
	height :300,
	listeners:{
		'beforeshow':function(me){
			me.getAddCodRefundForm().getForm().reset();
		},
	},
	addCodRefundForm:null,
	getAddCodRefundForm:function(){
		if(this.addCodRefundForm ==null){
			this.addCodRefundForm= Ext.create('Foss.baseinfo.codRefund.AddOrUpdateCodRefundForm',{
				isUpdate:false
			});
		}
		return this.addCodRefundForm;
	},
	constructor:function(config){
		var me=this,cfg=Ext.apply({},config);
		me.items =[me.getAddCodRefundForm()];
		me.fbar=[{
			text:'确定',//确定
			handler:function(){
				var myForm=me.getAddCodRefundForm().getForm();
				if(myForm.isValid()){
					//加上罩
					var myMask = new Ext.LoadMask(me,  {msg:'正在添加，请稍等...'});
		 			myMask.show();
		 			var myInfo=Ext.getCmp('T_baseinfo-codRefund_content').getQueryCodRefundGrid();
					myForm.submit({
		 				url:baseinfo.realPath('addCodRefund.action'),
		 				success:function(form, action){
		 					myMask.hide();
					        var json =action.result;
					        if(!Ext.isEmpty(json.message)){
					        	baseinfo.showInfoMes(json.message);
					        	myInfo.getPagingToolbar().moveFirst();
					        }
					        me.getAddCodRefundForm().close();
					        me.close();
		 				},
		 				failure:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				},
		 				exception:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				}
		 			});
				}
			}
		},{
			text:'取消',//取消
			handler:function(){
				me.close();
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改window
 */
Ext.define('Foss.baseinfo.codRefund.UpdateCodRefundWin',{
	extend:'Ext.window.Window',
	title : '修改合并退款客户信息',//baseinfo.codRefund.i18n('baseinfo.codRefund.updateCodRefund'),//修改合并退款客户信息
	closable : true,
	modal : true,
	resizable:false,
	autoScroll: true,
	parent:null,//父元素
	closeAction : 'hide',//关闭动作为hide，默认为destroy
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :600,
	height :300,
	codRefundModel:null,
	listeners:{
		'beforeshow':function(me){
			if(!Ext.isEmpty(me.codRefundModel)){
				var model =me.codRefundModel;
				me.getUpdateCodRefundForm().getForm().loadRecord(model);
				me.getUpdateCodRefundForm().getForm().findField('customerCode').setValue(model.customerCode);
				me.getUpdateCodRefundForm().getForm().findField('deptCode').setCombValue(model.deptName,model.deptCode);
				me.getUpdateCodRefundForm().getForm().findField('timeLimitStart').setValue(Ext.Date.format(new Date(model.timeLimitStart),'Y-m-d'));
				me.getUpdateCodRefundForm().getForm().findField('timeLimitEnd').setValue(Ext.Date.format(new Date(model.timeLimitEnd),'Y-m-d'));
				me.getUpdateCodRefundForm().getForm().findField('customerCode').setReadOnly(true);
				me.getUpdateCodRefundForm().getForm().findField('additional').setValue(model.additional);
			}
		},
	},
	updateCodRefundForm:null,
	getUpdateCodRefundForm:function(){
		if(this.updateCodRefundForm ==null){
			this.updateCodRefundForm= Ext.create('Foss.baseinfo.codRefund.UpdateCodRefundForm',{
				isUpdate:true
			});
		}
		return this.updateCodRefundForm;
	},
	constructor:function(config){
		var me=this,cfg=Ext.apply({},config);
		me.items=[me.getUpdateCodRefundForm()];
		me.fbar=[{
			text:'确定',//确定
			handler:function(){
				var myForm=me.getUpdateCodRefundForm().getForm();
				myForm.findField('deptCode').setValue(myForm.findField('deptCode').getValue());
				if(myForm.isValid()){
					//加上罩
					var myMask = new Ext.LoadMask(me,  {msg:'正在修改，请稍等...'});
		 			myMask.show();
		 			var myInfo=Ext.getCmp('T_baseinfo-codRefund_content').getQueryCodRefundGrid();
		 			myForm.submit({
		 				url:baseinfo.realPath('updateCodRefund.action'),
		 				success:function(form, action){
		 					myMask.hide();
					        var json =action.result;
					        if(!Ext.isEmpty(json.message)){
					        	baseinfo.showInfoMes(json.message);
					        	myInfo.getPagingToolbar().moveFirst();
					        }
					        me.getUpdateCodRefundForm().close();
					    	me.close();
		 				},
		 				failure:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				},
		 				exception:function(form, action){
		 					myMash.hide();
		 					var json =action.result;
		 					baseinfo.showInfoMes(json.message);
		 				}
		 			});
				}
			}
		},{
			text:'取消',//取消
			handler:function(){
				me.close();
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * 查看代收货款打包退款信息
 */
Ext.define('Foss.baseinfo.codRefund.CodRefundShowWindow', {
	extend : 'Ext.window.Window',
	title : '查看合并退款客户信息',//baseinfo.codRefund.i18n('baseinfo.codRefund.showCodRefund'), //查看代收货款打包退款信息
	closable : true,
	resizable : false,
	codRefundEntity : null, //查看库区数据
	autoScroll: true,
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.codRefund.CodRefundGrid）
	closeAction : 'hide',
	width : 600,
	height : 300,
	listeners : {
		beforehide : function (me) {
			me.resetData(); //清除掉这次的数据
		},
		beforeshow : function (me) {
			me.loadValue();
		}
	},
	//查看FORM
	codRefundForm : null,
	getCodRefundForm : function () {
		if (Ext.isEmpty(this.codRefundForm)) {
			this.codRefundForm = Ext.create('Foss.baseinfo.codRefund.ShowCodRefundForm', {
					'isUpdate' : true, //证明是修改
					'isShow' : true
				});
		}
		return this.codRefundForm;
	},
	//清除数据
	resetData : function(){
		var me = this;
		var form = me.getCodRefundForm();
		if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
			var oldItems =form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				form.remove(oldItems[i]);
			}
		}
		form.getForm().reset(); //表格重置
	},
	//加载原有数据
	loadValue : function () { 
		var me = this;
		var codRefundModel = new Foss.baseinfo.codRefund.CodRefundModel(me.codRefundEntity);
		me.getCodRefundForm().getForm().findField('deptName').setValue(codRefundModel.get('deptName'));
		me.getCodRefundForm().getForm().findField('customerCode').setValue(codRefundModel.get('customerCode'));
		me.getCodRefundForm().getForm().findField('timeLimit').setValue(codRefundModel.get('timeLimit'));
		me.getCodRefundForm().getForm().findField('operatorName').setValue(codRefundModel.get('operatorName'));
		me.getCodRefundForm().getForm().findField('enteringTime').setValue(Ext.Date.format(new Date(codRefundModel.get('enteringTime')),'Y-m-d H:i:s'));
		me.getCodRefundForm().getForm().findField('additional').setValue(codRefundModel.get('additional'));
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : '取消',//baseinfo.codRefund.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}
		];
		me.items = [me.getCodRefundForm()];
		me.callParent([cfg]);
	}
});

/**
 * ----------界面入口--------------
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-codRefund_content')) {
		return;
	}
	var queryCodRefundForm =Ext.create('Foss.baseinfo.codRefund.QueryCodRefundForm');
	var queryCodRefundGrid =Ext.create('Foss.baseinfo.codRefund.QueryCodRefundGrid');
	Ext.getCmp('T_baseinfo-codRefund').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-codRefund_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryCodRefundForm : function() {
			return queryCodRefundForm;
		},
		getQueryCodRefundGrid : function() {
			return queryCodRefundGrid;
		},
		items: [queryCodRefundForm,queryCodRefundGrid]
	}));
});