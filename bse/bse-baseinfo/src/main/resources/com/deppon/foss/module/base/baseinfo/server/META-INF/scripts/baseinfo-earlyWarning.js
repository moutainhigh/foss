/**
 * 提前预警线路信息
 * 
 * @author:262036 huangwei
 * Build date: 2015-08-19
 * 
 */
baseinfo.earlyWarning.errMsg = "上传的附件文件不能超过2M！！！";
//浏览器兼容提示
baseinfo.earlyWarning.tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用Chrome浏览器。";

baseinfo.earlyWarning.checkFile = function(){
	var maxsize = 2*1024*1024;//2M  
    try{  
        var obj_file = document.getElementsByName("earlyWarningVo.uploadFile");
        var filesize = 0;
        filesize = obj_file[obj_file.length-1].files[0].size;  
        if(filesize==-1){  
        	baseinfo.showInfoMes(baseinfo.earlyWarning.tipMsg);  
            return true;  
        }else if(filesize>maxsize){  
        	baseinfo.showInfoMes(baseinfo.earlyWarning.errMsg);  
            return false;  
        }else{ 
            return true;  
        }  
    }catch(e){ 
    }  
}

//警告
baseinfo.showWoringMessage = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
	    msg:message,
	    //cls:'mesbox',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.WARNING,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
};
//信息
baseinfo.showInfoMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.INFO,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
};
//错误
baseinfo.showErrorMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.ERROR,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
};

//定义一个model
Ext.define('Foss.baseinfo.earlyWarning.OriginatingLineModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'startCityCode',  //MAC地址
		type : 'string'
	}, {
		name : 'startCityName', //是否有效
		type : 'string'
	}, {
		name : 'endCityCode',  //MAC地址
		type : 'string'
	}, {
		name : 'endCityName', //是否有效number
		type : 'string'
	}, {
		name : 'operaTickets', //是否有效number
		type : 'number'
	}, {
		name : 'promiseTickets', //是否有效number
		type : 'number'
	},{
		name : 'promiseRate', //是否有效number
		type : 'string'
	}, {
		name : 'createDate',     //创建人
		type : 'date',
		convert: dateConvert,
		defaultValue:null
	}, {
		name : 'createUser',     //工号
		type : 'string'
	}]
});

//创建一提前预警线路信息的store
Ext.define('Foss.baseinfo.earlyWarning.OriginatingLineStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.earlyWarning.OriginatingLineModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryEarlyWarnings.action'),
		reader : {
			type : 'json',
			root : 'earlyWarningVo.entityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_EarlyWarning_QueryForm_Id').getForm();
			if (queryForm != null) {
				var startCityCode = queryForm.findField('startCityCode').getValue();
				var endCityCode = queryForm.findField('endCityCode').getValue();
				Ext.apply(operation, {
					params : {
						'earlyWarningVo.entity.startCityCode':startCityCode,
						'earlyWarningVo.entity.endCityCode':endCityCode
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.earlyWarning.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.earlyWarning.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_EarlyWarning_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[ {
		xtype : 'commoncityselector',
		fieldLabel:baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.startCity'),									//出发城市编码
		name: 'startCityCode'
	},{
		xtype : 'commoncityselector',
		fieldLabel:baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.endCity'),									//到达城市编码
		name: 'endCityCode'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.earlyWarning.i18n('foss.baseinfo.reset'),//重置
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.earlyWarning.i18n('foss.baseinfo.query'),//查询
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-earlyWarning_content').getOriginatingLineGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});


// 汇率详细信息表单
Ext.define('Foss.baseinfo.earlyWarning.DetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	height : 250,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120,
		readOnly : true
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
	items : [{
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.startCityCode'),//MAC地址
			// 关联model中的字段名
			name : 'startCityCode'
		}, {
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.startCityName'),//工号
			// 关联model中的字段名
			name : 'startCityName',
		}, {
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.endCityCode'),//姓名
			// 关联model中的字段名
			name : 'endCityCode',
		}, {
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.endCityName'),//部门
			// 关联model中的字段名
			name : 'endCityName',
		},{
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.operaTickets'),//大区
			// 关联model中的字段名
			name : 'operaTickets',
		}, {
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.promiseTickets'),//部门
			// 关联model中的字段名
			name : 'promiseTickets',
		},{
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.promiseRate'),//大区
			// 关联model中的字段名
			name : 'promiseRate',
		},{
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.createUserCode'),//大区
			// 关联model中的字段名
			name : 'createUser',
		}, {
			xtype:'datefield',
			// 字段标题
			fieldLabel : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.createTime'),//部门
			// 关联model中的字段名
			name : 'createDate',
			colspan:2,
			format:'Y-m-d H:i:s'
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

/**
 * 上传窗体
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
			form.getForm().findField('earlyWarningVo.uploadFile').reset();
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
		    				name:'earlyWarningVo.uploadFile',
		    				fieldLabel:baseinfo.earlyWarning.i18n('foss.baseinfo.pleaseSelectAttachments'),
		    				labelWidth:80,
		    				buttonText:baseinfo.earlyWarning.i18n('foss.baseinfo.browse'),
		    				flex:1
		    			}]
		    		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},getFbar:function(){
		var me = this;
		return [{
					width: 60,
					text : baseinfo.earlyWarning.i18n('foss.baseinfo.cancel'),
					handler : function() {
						me.hide();
					}
				},{
					text : baseinfo.earlyWarning.i18n('foss.baseinfo.save'),  //保存
					width: 60,
					cls : 'yellow_button',
					handler : function() {
						var form = me.down('form').getForm();
						var filePath = form.findField('earlyWarningVo.uploadFile').getValue();
						if(Ext.isEmpty(filePath)){
							baseinfo.showInfoMes('请选择文件....');
							return;
						}
						var arr = filePath.split('.');
						if(arr[arr.length-1]!='xls'){
							baseinfo.showInfoMes('请使用模板,上传.xls格式的文件....');
							return;
						}
						if(!baseinfo.earlyWarning.checkFile()){
							return;
						}
						var url = baseinfo.realPath('importEarlyWarning.action');
						var successFn = function(json){
							me.close();
							if(Ext.isEmpty(json.earlyWarningVo.numList)){
								baseinfo.showInfoMes(baseinfo.earlyWarning.i18n('foss.baseinfo.allDataImportSuccess'));//全部数据导入成功！
							}else{
								var message = baseinfo.earlyWarning.i18n('foss.baseinfo.di')+json.earlyWarningVo.numList[0];//第
								for(var i = 1;i<json.earlyWarningVo.numList.length;i++){
									message = message + "," +json.earlyWarningVo.numList[i];
								}
								message = message+baseinfo.earlyWarning.i18n('foss.baseinfo.lineNoImport');
								baseinfo.showWoringMessage(message);
							}
							Ext.getCmp('T_baseinfo-earlyWarning_content').getOriginatingLineGrid().getPagingToolbar().moveFirst();
						};
						var failureFn = function(json){
							me.close();
							if(Ext.isEmpty(json)){
								baseinfo.showErrorMes(baseinfo.earlyWarning.i18n('foss.baseinfo.formatError'));
							}else{
								baseinfo.showErrorMes(json.message);
							}
						};
						form.submit({
				            url: url,
				            waitMsg: baseinfo.earlyWarning.i18n('foss.baseinfo.uploadYourAttachments'),
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

// 定义一个表格列表
Ext.define('Foss.baseinfo.earlyWarning.OriginatingLineGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_earlyWarning_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title : baseinfo.earlyWarning.i18n('foss.baseinfo.queryGrid'),
	collapsible : true,
	animCollapse : true,
	store : null,
	// 表格行可展开的插件
	plugins : [ {
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.baseinfo.earlyWarning.InfoPanel'
	} ],
	
	addExcelWin : null,
	// 定义一个获取新增窗口的函数
	getAddExcelWin : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.AddExcelWin');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.earlyWarning.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.earlyWarning.i18n('foss.baseinfo.notice'),json.message);}
            
		});
		
	},
	//Ajax请求
	requestAjax : function(url,jsonData,successFn,failFn)
	{
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
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
			}
		});
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
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
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.startCityCode'),//出发城市编码
				// 关联model中的字段名
				dataIndex : 'startCityCode',
				width : 100
			}, {
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.startCityName'),//出发城市名称
				// 关联model中的字段名
				dataIndex : 'startCityName',
				width : 100
			}, {
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.endCityCode'),//到达城市编码
				// 关联model中的字段名
				dataIndex : 'endCityCode',
				width : 100
			}, {
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.endCityName'),//到达城市名称
				// 关联model中的字段名
				dataIndex : 'endCityName',
				width : 100
			},{
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.operaTickets'),//操作票数
				// 关联model中的字段名
				dataIndex : 'operaTickets',
				width : 80
			}, {
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.promiseTickets'),//兑现票数
				// 关联model中的字段名
				dataIndex : 'promiseTickets',
				width : 80
			},{
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.promiseRate'),//兑现率
				// 关联model中的字段名
				dataIndex : 'promiseRate',
				width : 80
			},{
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.createUserCode'),//创建人
				// 关联model中的字段名
				dataIndex : 'createUser',
				width : 100
			}, {
				// 字段标题
				header : baseinfo.earlyWarning.i18n('foss.bse.baseinfo.earlyWarning.createTime'),//创建时间
				// 关联model中的字段名
				dataIndex : 'createDate',
				width : 150,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.earlyWarning.OriginatingLineStore');
				me.bbar = me.getPagingToolbar();
				me.getPagingToolbar().store = me.store;
				me.dockedItems = [{
					xtype : 'toolbar',
					dock : 'top',
					layout : 'column',
					defaults : {
						margin : '0 0 5 3'
					},
					items : [{
						xtype : 'button',
						text : '预警线路导入',
						disabled:!baseinfo.earlyWarning.isPermission('EarlyWarningIndex/ImportEarlyWarningButton'),
						hidden:!baseinfo.earlyWarning.isPermission('EarlyWarningIndex/ImportEarlyWarningButton'),
						width : 100,
						handler : function() {// 作废多项选中的记录
                            this.addExcelWin = me.getAddExcelWin();
				            this.addExcelWin.show();
						}
					},{
						xtype : 'button',
						text : '模板下载',
						width : 80,
						handler : function() {// 作废多项选中的记录
						    //调用删除函数
							window.location=baseinfo.realPath("exportTemplate.action");
						}
					}
					]
		}], 
		me.callParent([ cfg ]);
	}
	});

// 查看记录的详细信息
Ext.define('Foss.baseinfo.earlyWarning.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.earlyWarning.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取汇率详细信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.earlyWarning.DetailForm');
		}
		return this.infoForm;
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [ this.getInfoForm()];
		this.callParent(arguments);
	},
	bindData : function(record) {
		var me = this;
		// 绑定表格数据到表单上
		me.getInfoForm().getForm().loadRecord(record);
	}
});

//初始化页面
Ext.onReady(function() {
	Ext.QuickTips.init();
    //查询FORM
	var queryForm = Ext.create('Foss.baseinfo.earlyWarning.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.earlyWarning.OriginatingLineGrid');

	Ext.getCmp('T_baseinfo-earlyWarning').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-earlyWarning_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getOriginatingLineGrid : function(){
			return queryResult;
		},
		items : [ queryForm, queryResult]
	}));
});