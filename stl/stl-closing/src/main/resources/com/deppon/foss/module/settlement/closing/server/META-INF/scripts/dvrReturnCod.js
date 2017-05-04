/**
 * 获取上个月期间
 */
closing.dvrReturnCod.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.dvrReturnCod.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.dvrReturnCod.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.dvrReturnCod.PeriodModel',
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

closing.dvrReturnCod.querydvrReturnCodByConditions = function(form,me){
	var m =Ext.getCmp('T_closing-dvrReturnCod_content');
	var grid = m.getQueryGrid();
	
	//var form = this.up('form').getForm();
	closing.dvrReturnCod.period = form.findField('dvrReturnCodDto.period').getValue();
	closing.dvrReturnCod.refundPath = form.findField('dvrReturnCodDto.refundPath').getValue();
	closing.dvrReturnCod.productCode = form.findField('dvrReturnCodDto.productCode').getValue();
	closing.dvrReturnCod.payableOrgCode = form.findField('dvrReturnCodDto.payableOrgCode').getValue();
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'dvrReturnCodVo.dvrReturnCodDto.period':closing.dvrReturnCod.period,
				'dvrReturnCodVo.dvrReturnCodDto.refundPath':closing.dvrReturnCod.refundPath,
				'dvrReturnCodVo.dvrReturnCodDto.productCode':closing.dvrReturnCod.productCode,
				'dvrReturnCodVo.dvrReturnCodDto.payableOrgCode':closing.dvrReturnCod.payableOrgCode
			}
		});	
	});
	//设置该按钮灰掉
	me.disable(false);
	//30秒后自动解除灰掉效果
	setTimeout(function() {
		me.enable(true);
	}, 30000);
	grid.store.loadPage(1,{
		callback: function(records, operation, success) {
			if(success){
				var result =   Ext.decode(operation.response.responseText);  
				if(result.isException){
					Ext.Msg.alert('温馨提示',result.message);
					me.enable(true);
					return false;
				}
				me.enable(true);
			}
		}
	});
}

closing.dvrReturnCod.exportdvrReturnCod = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-dvrReturnCod_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出退代收货款报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = {
					'dvrReturnCodVo.dvrReturnCodDto.period':closing.dvrReturnCod.period,
					'dvrReturnCodVo.dvrReturnCodDto.refundPath':closing.dvrReturnCod.refundPath,
					'dvrReturnCodVo.dvrReturnCodDto.productCode':closing.dvrReturnCod.productCode,
					'dvrReturnCodVo.dvrReturnCodDto.payableOrgCode':closing.dvrReturnCod.payableOrgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportdvrReturnCodForm')){
				var frm = document.createElement('form');
				frm.id = 'exportdvrReturnCodForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('dvrReturnCodExport.action'), 
				form: Ext.fly('exportdvrReturnCodForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//var json = Ext.decode(response.responseText);
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', json.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', json.message);
				}
		    });
			
		}
	});	
}

/**
 * 获取期间控件下拉框Store
 */
closing.dvrReturnCod.getComboPeriodStore = function() {
	var now = new Date();
	var nowMonth = now.getMonth();
	var format = 'Ym';

	var periods = [];
	period = Ext.Date.format(now, format);
	periods.push({
				'name' : period,
				'value' : period
			});

	for (var i = 0; i < 12; i++) {
		nowMonth = now.getMonth();
		now.setMonth(nowMonth - 1);
		period = Ext.Date.format(now, format);
		periods.push({
					'name' : period,
					'value' : period
				});
	}

	return Ext.create('Ext.data.Store', {
				model : 'Foss.closing.dvrReturnCodComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.dvrReturnCod.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.dvrReturnCodComboModel', {
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

// 空运月报表数据模型
Ext.define('Foss.closing.dvrReturnCodModel', {
			extend : 'Ext.data.Model',
			fields : [{
                /*id*/
                name:'id',
                type:'String'
            },{
                /*付款日期*/
                name:'paymentDate',
                type:'Date',
        		convert:function(value) {
        			if (value != null) {
        				var date = new Date(value);
        				return date;
        			} else {
        				return null;
        			}
        		}
            },{
                /*付款所属期间*/
                name:'period',
                type:'String'
            },{
                /*应付部门编码*/
                name:'payableOrgCode',
                type:'String'
            },{
                /*应付部门名称*/
                name:'payableOrgName',
                type:'String'
            },{
                /*标杆编码*/
                name:'unifiedCode',
                type:'String'
            },{
                /*应付子公司编码*/
                name:'payableComCode',
                type:'String'
            },{
                /*应付子公司名称*/
                name:'payableComName',
                type:'String'
            },{
                /*退款部门（固定值）*/
                name:'returnOrg',
                type:'String'
            },{
                /*退款子公司（固定值）*/
                name:'returnComOrg',
                type:'String'
            },{
                /*业务类型*/
                name:'productCode',
                type:'String'
            },{
                /*银行账户*/
                name:'bankAccount',
                type:'String'
            },{
                /*付款途径*/
                name:'refundPath',
                type:'String'
            },{
                /*退款金额*/
                name:'codAmount',
                type:'Long'
            }]
		})

//Store
Ext.define('Foss.closing.dvrReturnCodStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.dvrReturnCodModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('querydvrReturnCodByConditions.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'dvrReturnCodVo.dvrReturnCodDto.dvrReturnCodEntities',
			totalProperty : 'dvrReturnCodVo.dvrReturnCodDto.sum'
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
    			var searchParams;
    			var form = Ext.getCmp('T_closing-dvrReturnCod_content').getQueryTab().getForm();
    			closing.dvrReturnCod.period = form.findField('dvrReturnCodDto.period').getValue();
    			closing.dvrReturnCod.refundPath = form.findField('dvrReturnCodDto.refundPath').getValue();
    			closing.dvrReturnCod.productCode = form.findField('dvrReturnCodDto.productCode').getValue();
    			closing.dvrReturnCod.payableOrgCode = form.findField('dvrReturnCodDto.payableOrgCode').getValue();
    			//获取查询条件
    			searchParams = {
    					'dvrReturnCodVo.dvrReturnCodDto.period':closing.dvrReturnCod.period,
    					'dvrReturnCodVo.dvrReturnCodDto.refundPath':closing.dvrReturnCod.refundPath,
    					'dvrReturnCodVo.dvrReturnCodDto.productCode':closing.dvrReturnCod.productCode,
    					'dvrReturnCodVo.dvrReturnCodDto.payableOrgCode':closing.dvrReturnCod.payableOrgCode
    			}
    			Ext.apply(operation,{
    				params :searchParams
    			}); 
    	   		 Ext.apply(me.submitParams, {
    		          "limit":operation.limit,
    		          "page":operation.page,
    		          "start":operation.start
    		    });
	   		} 
		};
		me.callParent([ cfg ]);
	} 
}); 
		
/**
 * 退款类型model
 */
Ext.define('Foss.dvrReturnCod.RefundTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'name'
	},{
		name:'value'
	}]
});

/**
 * 退款类型store
 */
Ext.define('Foss.dvrReturnCod.RefundTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.dvrReturnCod.RefundTypeModel',
	data:{
		'items':[
		    {name:"全部",value:""},
			{name:"三日退(审核退)",value:closing.dvrReturnCod.COD__COD_TYPE__RETURN_R3RA_DAY_CODE},
			{name:"即日退",value:closing.dvrReturnCod.COD__COD_TYPE__RETURN_1_DAY }
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});

// 定义查询Form
Ext.define('Foss.closing.dvrReturnCodQueryForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			title : '退代收货款报表',
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
				xtype:'combobox',
				name : 'dvrReturnCodDto.period',
		    	fieldLabel:'查询期间',
		    	/*editable:false,
				store:Ext.create('Foss.common.PeriodStore'),
			    queryMode: 'local', 	
				displayField:'name',
				valueField:'value',
				value:closing.dvrReturnCod.getLastMonthPeriod(),
		    	columnWidth:.33*/
		    	queryMode: 'remote', 
		    	store:Ext.create('Foss.dvrReturnCod.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.04
			},{
				xtype : 'combo',
				fieldLabel : '付款途径',
				name : 'dvrReturnCodDto.refundPath',
				store:FossDataDictionary.getDataDictionaryStore('COD__REFUND_PATH',null,{
					 'valueCode': '',
		       		 'valueName': '全部'
				}),
		    	editable:false,
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				columnWidth : .33
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.26
			},{
				xtype : 'combo',
				name : 'dvrReturnCodDto.productCode',
		    	fieldLabel:'业务类型',
		    	store:Ext.create('Foss.dvrReturnCod.RefundTypeStore'),
		    	listeners:{
					change:stl.comboSelsct
				},
		    	editable:false,
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value:"",
				columnWidth : .33
			},{
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.04
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'dvrReturnCodDto.payableOrgCode',
				fieldLabel : '部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			},{
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.26
			},{
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
							columnWidth : .5
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler:function(){
								var form = this.up('form').getForm();
								var me = this;
								if(form.isValid()){
									closing.dvrReturnCod.querydvrReturnCodByConditions(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}	
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.dvrReturnCodQueryGrid', {
	extend : 'Ext.grid.Panel',
	title : '退代收货款报表',
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
		enableTextSelection : true,
		// 设置行可以选择，进而可以复制
		stripeRows: false,//显示重复样式，不用隔行显示
  	 	getRowClass:function(record, rowIndex, p, store){
  	 		var count = store.data.length;
  	 		if(count>0 && rowIndex==store.data.length-1) {
  	 			return 'closing-totalBgColor';
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
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.dvrReturnCod.exportdvrReturnCod,
				disabled:!closing.dvrReturnCod.isPermission('/stl-web/closing/dvrReturnCodExport.action'),
				hidden:!closing.dvrReturnCod.isPermission('/stl-web/closing/dvrReturnCodExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [{
		xtype:'rownumberer',
		width:40
	},{
		text : 'ID',
		sortable : false,
		hidden:true,
		dataIndex : 'id'
	}, {
		text : '付款日期',
		sortable : false,
		dataIndex : 'paymentDate',
		renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d');
    		}else{
    			return null;
    		}
    	} 
	}, {
		text : '付款所属期间',
		sortable : false,
		dataIndex : 'period'
	}, {
		text : '应付部门编码',
		sortable : false,
		dataIndex : 'payableOrgCode'
	}, {
		text : '应付部门名称',
		sortable : false,
		dataIndex : 'payableOrgName'
	}, {
		text : '标杆编码',
		sortable : false,
		dataIndex : 'unifiedCode'
	}, {
		text : '应付子公司编码',
		sortable : false,
		dataIndex : 'payableComCode'
	}, {
		text : '应付子公司名称',
		sortable : false,
		dataIndex : 'payableComName'
	}, {
		text : '退款部门（固定值）',
		sortable : false,
		dataIndex : 'returnOrg'
	}, {
		text : '退款子公司（固定值）',
		sortable : false,
		dataIndex : 'returnComOrg'
	}, {
		text : '业务类型',
		sortable : false,
		dataIndex : 'productCode',
		renderer:function(value){
			var displayField  = "";
			if(value == closing.dvrReturnCod.COD__COD_TYPE__RETURN_1_DAY){
				displayField = "即日退";
			}else if(value == closing.dvrReturnCod.COD__COD_TYPE__RETURN_R3RA_DAY_CODE){
				displayField = "三日退(审核退)";
			}
    		return displayField;
    	}
	},{
		text : '银行账户',
		sortable : false,
		dataIndex : 'bankAccount'
	},{
		text : '付款途径',
		sortable : false,
		dataIndex : 'refundPath',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,"COD__REFUND_PATH");
    		return displayField;
    	}
	},{
		text : '退款金额',
		sortable : false,
		dataIndex : 'codAmount'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.dvrReturnCodStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-dvrReturnCod_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.dvrReturnCodQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.dvrReturnCodQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-dvrReturnCod_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [queryForm,queryGrid],
						getQueryTab : function() {
							return queryForm;
						},
						getQueryGrid : function() {
							return queryGrid;
						},
						renderTo : 'T_closing-dvrReturnCod-body'
					});
		});