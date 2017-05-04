/**
 * 声明账期model
 */
Ext.define('Foss.mvrDhk.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrDhk.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrDhk.PeriodModel',
	proxy:{
		type:'ajax',
		url:closing.realPath('queryClosingPeriod.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'periodList'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'load': function(store, operation, eOpts){

	   			if(operation.length == 0){
	   				Ext.Msg.alert("提示", "没有生成凭证报表数据，凭证期间为空");
					return false;
	   			}
	   		}
		};
		me.callParent([ cfg ]);
	} 
});


/**
 * 查询方法
 */
closing.mvrDhk.queryMvrDhk=function(form,me){
	var m=Ext.getCmp('T_closing-mvrDhk_content');
	if(m){
		var grid = m.getQueryGrid();
		var store=grid.getStore();
		if(store){
			if(grid.isHidden()){
				grid.show();
			}
			//设置该按钮灰掉
			me.disable(false);
			//30秒后自动解除灰掉效果
			setTimeout(function() {
				me.enable(true);
			}, 30000);
			// 加载第一页数据
			store.loadPage(1,{
						callback	 : function(records, operation, success) {
							var rawData = store.proxy.reader.rawData;
							if(!success && !rawData.isException){
								Ext.Msg.alert("提示", rawData.message);
								me.enable(true);
								return false;
							}
							
							me.enable(true);
						}
					});
		}
	}

}

/**
 * 导出代汇款报表
 */
closing.mvrDhk.exportMvrDhk = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrDhk_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出代汇款月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = mainPane.getQueryForm().getValues();
			
			//创建一个form
			if(!Ext.fly('exportMvrDhkForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrDhkForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrDhkExport.action'), 
				form: Ext.fly('exportMvrDhkForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//获取响应的json字符串
					var jsonText = Ext.decode(response.responseText.trim());
                   	//导出失败
                   	if(jsonText.message!=null&&jsonText.message!=''){
                     	Ext.Msg.alert('温馨提示',jsonText.message);
                     }
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', jsonText.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', jsonText.message);
				}
		    });
			
		}
	});	
}

/**
 * 获取期间控件下拉框Store
 */
closing.mvrDhk.getComboPeriodStore = function() {
	return Ext.create('Foss.common.PeriodStore');
}

 

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.MvrDhkComboModel', {
			extend : 'Ext.data.Model',
			fields : [{
						/* 显示名 */
						name : 'name',
						type : 'string'
					}, {
						/* 实际值 */
						name : 'value',
						type : 'string'
					}]
		})

// 代汇款报表数据模型
Ext.define('Foss.closing.MvrDhkModel', {
			extend : 'Ext.data.Model',
			fields : [
			    {name:'id',               type:'string'},     
				{name:'period',           type:'string'},
				{name:'remitOrgCode',   type:'string'},
				{name:'remitOrgName',   type:'string'},
				{name:'byremitOrgCode', type:'string'},
				{name:'byremitOrgName', type:'string'},
				{name:'amount',           type:'decimal'},
				{name:'collectionType',      type:'string'}
				]
		})

//Store
Ext.define('Foss.closing.MvrDhkStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.MvrDhkModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrDhk.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrDhkVo.mvrDhkEntityList',
			totalProperty : 'mvrDhkVo.mvrDhkDto.count'
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
	   		/* Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); */ 		
	   		var queryForm = Ext.getCmp('T_closing-mvrDhk_content').getQueryForm();
			if (queryForm) {
				var form = queryForm.getForm();
				var params = form.getValues();
				
				Ext.apply(operation, {
					params:params
	   			});				
			}
	   			
	   		}
		};
		me.callParent([ cfg ]);
	} 
}); 
		
// 定义查询Form
Ext.define('Foss.closing.MvrDhkQueryForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			title : '查询条件',
			bodyCls : 'autoHeight',
			defaults : {
				margin : '10 5 10 5',
				labelWidth : 85,
				colspan : 1
			},
			defaultType : 'textfield',
			layout : {
				type : 'column',
				columns : 3
			},
			items : [{
				xtype : 'combo',
				name : 'mvrDhkVo.period',
				fieldLabel : '查询期间',
				/*forceSelection : true,
				displayField : 'name',
				valueField : 'value',
				queryMode : 'local',
				triggerAction : 'all',
				editable : false,
				value : closing.mvrDhk.getComboPeriodStore().getAt(1)
						.get('value'),
				store : closing.mvrDhk.getComboPeriodStore(),*/
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrDhk.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .4
			}, {
				xtype: 'combobox',
				name: 'mvrDhkVo.collectionType',
				fieldLabel: '收款类别',
				store: FossDataDictionary.getDataDictionaryStore('SETTLEMENT__PAYMENT_TYPE', null, 
						[{'valueCode': closing.mvrDhk.SETTLEMENT__COLLECTION_TYPE__NOCASH,'valueName': '非现金'},
						{'valueCode': '','valueName': '全部'}],
						 [closing.mvrDhk.SETTLEMENT__COLLECTION_TYPE__CASH]),
				queryMode: 'local',
				editable: false,
				displayField: 'valueName',
				valueField: 'valueCode',
				value: '',
				columnWidth : .4
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrDhkVo.remitOrgCode',
				fieldLabel : '代汇款部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .4
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrDhkVo.byremitOrgCode',
				fieldLabel : '被代汇款部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .4
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				colspan : 3,
				defaultType : 'button',
				layout : 'column',
				items : [{
							text : '重置',
							columnWidth : .1,
							handler : function(){
								this.up('form').getForm().reset();
							}
						}, {
							xtype : 'container',
							border : false,
							html : '&nbsp;',
							columnWidth : .8
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler:function(){
								var form = this.up('form').getForm();
								var me = this;
								if(form.isValid()){
									closing.mvrDhk.queryMvrDhk(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.MvrDhkQueryGrid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	columnWidth : 1,
	stripeRows : true,
	columnLines : true,
	collapsible : false,
	bodyCls : 'autoHeight',
	frame : true,
	cls : 'autoHeight',
	store : null,
	autoScroll : true,
	height : 650,
	emptyText : '查询结果为空',
	viewConfig : {
		enableTextSelection : true, // 设置行可以选择，进而可以复制
		getRowClass : function(record,rowIndex,rowParams,store){
	            if(record.data.period == '汇总'){ // 汇总的样式
	            	return 'closing-totalBgColor';
	            }else{
	            	return ''; 
	            }
      }
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : me.store,
						pageSize : 100,
						maximumSize : 500,
						plugins : 'pagesizeplugin',
						items:[me.getExportButton()]
					});
		}
		return me.pagingToolbar;
	},
	exportButton:null,
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrDhk.exportMvrDhk,
				disabled:!closing.mvrDhk.isPermission('/stl-web/closing/mvrDhkExport.action'),
				hidden:!closing.mvrDhk.isPermission('/stl-web/closing/mvrDhkExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text:'序号',
		xtype:'rownumberer',
		width:40
	},{
		text : 'ID',
		sortable : false,
		dataIndex: 'id',
		hidden: true
	},{
		text : '代汇款所属期间',
		sortable : false,
		dataIndex : 'period'
	}, {
		text : '代汇款部门名称',
		sortable : false,
		dataIndex : 'remitOrgName'
	},  {
		text : '代汇款部门编码',
		sortable : false,
		dataIndex : 'remitOrgCode',
	},{
		text : '被代汇款部门名称（快递代理点部名称）',
		sortable : false,
		width : 200,
		dataIndex : 'byremitOrgName'
	}, {
		text : '被代汇款部门编码（快递代理点部编码）',
		sortable : false,
		width : 200,
		dataIndex : 'byremitOrgCode'
	}, {
		text : '代汇金额',
		sortable : false,
		dataIndex : 'amount'
	}, {
		text : '收款类别',
		sortable : false,
		dataIndex : 'collectionType',
		renderer:function(value){
			var displayField  = "";
			if(value == closing.mvrDhk.SETTLEMENT__COLLECTION_TYPE__NOCASH){
				displayField = "非现金";
			}else if(value == closing.mvrDhk.SETTLEMENT__COLLECTION_TYPE__CASH){
				displayField = "现金";
			}
    		return displayField;
    	}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.MvrDhkStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrDhk_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.MvrDhkQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.MvrDhkQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrDhk_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [queryForm,queryGrid],
						//获得查询FORM
						getQueryForm : function() {
							return queryForm;
						},
						//获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						renderTo : 'T_closing-mvrDhk-body'
					});
		});