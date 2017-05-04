
closing.dvdReturnCod.querydvdReturnCodByConditions = function(form,me){
	var m=Ext.getCmp('T_closing-dvdReturnCod_content');
	var grid = m.getQueryGrid();
	
	closing.dvdReturnCod.startDate = form.findField('dvdReturnCodDto.startDate').getValue();
	closing.dvdReturnCod.endDate = form.findField('dvdReturnCodDto.endDate').getValue();
	closing.dvdReturnCod.refundPath = form.findField('dvdReturnCodDto.refundPath').getValue();
	closing.dvdReturnCod.codType = form.findField('dvdReturnCodDto.codType').getValue();
	closing.dvdReturnCod.payableOrgCode = form.findField('dvdReturnCodDto.payableOrgCode').getValue();
	closing.dvdReturnCod.productCodeList = form.findField('dvdReturnCodDto.productCodeList').getValue();
	
	//校验日期是否正确
	var compareTwoDate = stl.compareTwoDate(closing.dvdReturnCod.startDate,closing.dvdReturnCod.endDate);
	if(compareTwoDate>stl.DATELIMITDAYS_MONTH){
		Ext.Msg.alert('温馨提示','查询日期范围不能超过31天！');
		return false;
	}else if(compareTwoDate<1){
		Ext.Msg.alert('温馨提示','起始日期不能晚于结束日期！');
		return false;
	}
	
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'dvdReturnCodVo.dvdReturnCodDto.startDate':closing.dvdReturnCod.startDate,
				'dvdReturnCodVo.dvdReturnCodDto.endDate':closing.dvdReturnCod.endDate,
				'dvdReturnCodVo.dvdReturnCodDto.refundPath':closing.dvdReturnCod.refundPath,
				'dvdReturnCodVo.dvdReturnCodDto.codType':closing.dvdReturnCod.codType,
				'dvdReturnCodVo.dvdReturnCodDto.productCodeList':closing.dvdReturnCod.convertProductCode(closing.dvdReturnCod.productCodeList),
				'dvdReturnCodVo.dvdReturnCodDto.payableOrgCode':closing.dvdReturnCod.payableOrgCode
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

closing.dvdReturnCod.convertProductCode = function(productCodes) {
	if (!Ext.isEmpty(productCodes)) {
		var productCodeList = [];
		for ( var i = 0; i < productCodes.length; i++) {
			// 如果产品类型中存在值为空，则表明为全部，那么默认全部查询
			if (Ext.isEmpty(productCodes[i])) {
				productCodeList = [];
				break;
			} else {
				productCodeList.push(productCodes[i]);
			}
		}
		return productCodeList;
	} else {
		return [];
	}
}

closing.dvdReturnCod.exportdvdReturnCod = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-dvdReturnCod_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出退代收货款报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = {
					'dvdReturnCodVo.dvdReturnCodDto.startDate':closing.dvdReturnCod.startDate,
					'dvdReturnCodVo.dvdReturnCodDto.endDate':closing.dvdReturnCod.endDate,
					'dvdReturnCodVo.dvdReturnCodDto.refundPath':closing.dvdReturnCod.refundPath,
					'dvdReturnCodVo.dvdReturnCodDto.codType':closing.dvdReturnCod.codType,
					'dvdReturnCodVo.dvdReturnCodDto.productCodeList':closing.dvdReturnCod.convertProductCode(closing.dvdReturnCod.productCodeList),
					'dvdReturnCodVo.dvdReturnCodDto.payableOrgCode':closing.dvdReturnCod.payableOrgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportdvdReturnCodForm')){
				var frm = document.createElement('form');
				frm.id = 'exportdvdReturnCodForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('dvdReturnCodExport.action'), 
				form: Ext.fly('exportdvdReturnCodForm'),
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

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.dvdReturnCodComboModel', {
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
Ext.define('Foss.closing.dvdReturnCodModel', {
			extend : 'Ext.data.Model',
			fields : [{
                /*id*/
                name:'id',
                type:'String'
            },{
                /*付款所属期间*/
                name:'period',
                type:'String'
            },{
                /*运单号*/
                name:'waybillNo',
                type:'String'
            },{
            	/*运输性质*/
            	name:'productCode',
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
                /*应付子公司编码*/
                name:'payableComCode',
                type:'String'
            },{
                /*应付子公司名称*/
                name:'payableComName',
                type:'String'
            },{
                /*代收货款类型*/
                name:'codType',
                type:'String'
            },{
                /*发货客户编码*/
                name:'customerCode',
                type:'String'
            },{
                /*客户类型*/
                name:'customerType',
                type:'String'
            },{
                /*发货客户名称/代理名称*/
                name:'customerName',
                type:'String'
            },{
                /*收款人*/
                name:'payeeName',
                type:'String'
            },{
                /*收款人银行帐号*/
                name:'payeeAccount',
                type:'String'
            },{
                /*开户行编码*/
                name:'bankHqCode',
                type:'String'
            },{
                /*开户行名称*/
                name:'bankHqName',
                type:'String'
            },{
                /*记账日期*/
                name:'accountDate',
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
                /*业务日期*/
                name:'businessDate',
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
                /*签收日期*/
                name:'signDate',
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
                /*退款部门（固定值）*/
                name:'returnOrg',
                type:'String'
            },{
                /*退款子公司（固定值）*/
                name:'returnComOrg',
                type:'String'
            },{
                /*银行账户*/
                name:'comAccount',
                type:'String'
            },{
                /*退款路径*/
                name:'refundPath',
                type:'String'
            },{
                /*退款金额*/
                name:'codAmount',
                type:'Long'
            }]
		})

//Store
Ext.define('Foss.closing.dvdReturnCodStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.dvdReturnCodModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('querydvdReturnCodByConditions.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'dvdReturnCodVo.dvdReturnCodDto.dvdReturnCodEntities',
			totalProperty : 'dvdReturnCodVo.dvdReturnCodDto.sum'
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
    			var form = Ext.getCmp('T_closing-dvdReturnCod_content').getQueryTab().getForm();
    			closing.dvdReturnCod.startDate = form.findField('dvdReturnCodDto.startDate').getValue();
    			closing.dvdReturnCod.endDate = form.findField('dvdReturnCodDto.endDate').getValue();
    			closing.dvdReturnCod.refundPath = form.findField('dvdReturnCodDto.refundPath').getValue();
    			closing.dvdReturnCod.codType = form.findField('dvdReturnCodDto.codType').getValue();
    			closing.dvdReturnCod.payableOrgCode = form.findField('dvdReturnCodDto.payableOrgCode').getValue();
    			//获取查询条件
    			searchParams = {
    					'dvdReturnCodVo.dvdReturnCodDto.startDate':closing.dvdReturnCod.startDate,
    					'dvdReturnCodVo.dvdReturnCodDto.endDate':closing.dvdReturnCod.endDate,
    					'dvdReturnCodVo.dvdReturnCodDto.refundPath':closing.dvdReturnCod.refundPath,
    					'dvdReturnCodVo.dvdReturnCodDto.codType':closing.dvdReturnCod.codType,
    					'dvdReturnCodVo.dvdReturnCodDto.payableOrgCode':closing.dvdReturnCod.payableOrgCode
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
Ext.define('Foss.dvdReturnCod.RefundTypeModel',{
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
Ext.define('Foss.dvdReturnCod.RefundTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.dvdReturnCod.RefundTypeModel',
	data:{
		'items':[
		    {name:"全部",value:""},
			{name:"三日退(审核退)",value:closing.dvdReturnCod.COD__COD_TYPE__RETURN_R3RA_DAY_CODE},
			{name:"即日退",value:closing.dvdReturnCod.COD__COD_TYPE__RETURN_1_DAY }
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
Ext.define('Foss.closing.dvdReturnCodQueryForm', {
			extend : 'Ext.form.Panel',
			frame : true,
			title : '退代收货款明细报表',
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
		    	xtype:'datefield',
		    	name : 'dvdReturnCodDto.startDate',
		    	fieldLabel:'开始时间',
		    	value: stl.getTargetDate(new Date(),-1),
		    	format:'Y-m-d',
		    	allowBlank:false,
		    	columnWidth:.3
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.02
			},{
		    	xtype:'datefield',
		    	name : 'dvdReturnCodDto.endDate',
		    	fieldLabel:'结束时间',
		    	value: new Date(),
		    	format:'Y-m-d',
		    	allowBlank:false,
		    	columnWidth:.3
			},{
				xtype : 'combo',
				fieldLabel : '付款途径',
				name : 'dvdReturnCodDto.refundPath',
				store:FossDataDictionary.getDataDictionaryStore('COD__REFUND_PATH',null,{
					 'valueCode': '',
		       		 'valueName': '全部'
				}),
		    	editable:false,
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				columnWidth : .3
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.02
			}, {
				xtype : 'combo',
				name : 'dvdReturnCodDto.codType',
		    	fieldLabel:'业务类型',
		    	store:Ext.create('Foss.dvdReturnCod.RefundTypeStore'),
		    	listeners:{
					change:stl.comboSelsct
				},
		    	editable:false,
				queryModel:'local',
				displayField:'name',
				valueField:'value',
				value:"",
				columnWidth : .3
			
			},{
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.02
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'dvdReturnCodDto.payableOrgCode',
				fieldLabel : '部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3
			},
			{
		    	xtype: 'combobox',
				name:'dvdReturnCodDto.productCodeList',
		        fieldLabel: '运输性质',
				store:Ext.create('Foss.pkp.ProductStore'),
				queryMode:'local',
				multiSelect:true,
		    	editable:false,
				displayField:'name',
				valueField:'code',
				height:24,
				columnWidth:.3,
				value:''
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
							columnWidth : .72
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler : function(){
    							var form = this.up('form').getForm();
    							var me = this;
    							closing.dvdReturnCod.querydvdReturnCodByConditions(form,me)
    						}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.dvdReturnCodQueryGrid', {
	extend : 'Ext.grid.Panel',
	title : '退代收货款明细报表',
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
				handler:closing.dvdReturnCod.exportdvdReturnCod,
				disabled:!closing.dvdReturnCod.isPermission('/stl-web/closing/dvdReturnCodExport.action'),
				hidden:!closing.dvdReturnCod.isPermission('/stl-web/closing/dvdReturnCodExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [{
		xtype:'rownumberer',
		width:40
	},{
		text : 'ID',
		hidden:true,
		dataIndex : 'id'
	},  {
		text : '付款所属期间',
		sortable : false,
		dataIndex : 'period'
	}, {
		text : '运单号',
		sortable : false,
		dataIndex : 'waybillNo'
	},{
		header: '运输性质', 
		dataIndex: 'productCode',
		renderer:function(value){
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}
	},{
		text : '应付部门编码',
		sortable : false,
		dataIndex : 'payableOrgCode'
	}, {
		text : '应付部门名称',
		sortable : false,
		dataIndex : 'payableOrgName'
	},{
		text : '应付子公司编码',
		sortable : false,
		dataIndex : 'payableComCode'
	}, {
		text : '应付子公司名称',
		sortable : false,
		dataIndex : 'payableComName'
	}, {
		text : '业务类型',
		sortable : false,
		dataIndex : 'codType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,"COD__COD_TYPE");
    		return displayField;
    	}
	},{
		text : '发货客户编码',
		sortable : false,
		dataIndex : 'customerCode'
	},{
		text : '客户类型',
		sortable : false,
		dataIndex : 'customerType',
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,"SETTLEMENT__CUSTOMER_TYPE");
    		return displayField;
    	}
	},{
		text : '发货客户名称/代理名称',
		sortable : false,
		dataIndex : 'customerName'
	},{
		text : '收款人',
		sortable : false,
		dataIndex : 'payeeName'
	},{
		text : '收款人银行帐号',
		sortable : false,
		dataIndex : 'payeeAccount'
	},{
		text : '开户行编码',
		sortable : false,
		dataIndex : 'bankHqCode'
	},{
		text : '开户行名称',
		sortable : false,
		dataIndex : 'bankHqName'
	},{
		text : '记账日期',
		sortable : false,
		dataIndex : 'accountDate',
		renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d');
    		}else{
    			return null;
    		}
    	} 
	},{
		text : '业务日期',
		sortable : false,
		dataIndex : 'businessDate',
		renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d');
    		}else{
    			return null;
    		}
    	} 
	},{
		text : '签收日期',
		sortable : false,
		dataIndex : 'signDate',
		renderer:function(value){
    		if(value!=null){
    			return Ext.Date.format(new Date(value), 'Y-m-d');
    		}else{
    			return null;
    		}
    	} 
	},{
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
		text : '退款部门（固定值）',
		sortable : false,
		dataIndex : 'returnOrg'
	}, {
		text : '退款子公司（固定值）',
		sortable : false,
		dataIndex : 'returnComOrg'
	},{
		text : '银行账户',
		sortable : false,
		dataIndex : 'comAccount'
	},{
		text : '退款路径',
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
		
		me.store = Ext.create('Foss.closing.dvdReturnCodStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-dvdReturnCod_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.dvdReturnCodQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.dvdReturnCodQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-dvdReturnCod_content',
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
						renderTo : 'T_closing-dvdReturnCod-body'
					});
		});