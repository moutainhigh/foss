closing.mvrAfi.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

closing.mvrAfi.queryMvrAfiByConditions = function(form,me){
	var m =Ext.getCmp('T_closing-mvrAfi_content');
	var grid = m.getQueryGrid();
	
	closing.mvrAfi.period = form.findField('mvrAfiDto.period').getValue();
	closing.mvrAfi.customer = form.findField('mvrAfiDto.customerCode').getValue();
	closing.mvrAfi.customerDetail = form.findField('mvrAfiDto.customerDetail').getValue();
	closing.mvrAfi.agencyDetail = form.findField('mvrAfiDto.agencyDetail').getValue();
	closing.mvrAfi.orgType = form.findField('mvrAfiDto.orgType').getValue();
	closing.mvrAfi.orgCode = form.findField('mvrAfiDto.orgCode').getValue();
	var customerCode = "";
	if(closing.mvrAfi.customer != null && closing.mvrAfi.customer != ""){
		customerCode = closing.mvrAfi.customer;
	}else if(closing.mvrAfi.customerDetail != null && closing.mvrAfi.customerDetail != ""){
		customerCode = closing.mvrAfi.customerDetail;
	}else if(closing.mvrAfi.agencyDetail != null && closing.mvrAfi.agencyDetail != ""){
		customerCode = closing.mvrAfi.agencyDetail;
	}
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'mvrAfiVo.mvrAfiDto.period':closing.mvrAfi.period,
				'mvrAfiVo.mvrAfiDto.customerCode':customerCode,
				'mvrAfiVo.mvrAfiDto.orgType':closing.mvrAfi.orgType,
				'mvrAfiVo.mvrAfiDto.orgCode':closing.mvrAfi.orgCode
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

/**
 * 声明账期model
 */
Ext.define('Foss.mvrAfi.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrAfi.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrAfi.PeriodModel',
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
 * 导出始发空运往来月报表
 */
closing.mvrAfi.exportMvrAfi = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrAfi_content');
	var queryGrid = mainPane.getQueryGrid();
	var customerCode = "";
	if(closing.mvrAfi.customer != null && closing.mvrAfi.customer != ""){
		customerCode = closing.mvrAfi.customer;
	}else if(closing.mvrAfi.customerDetail != null && closing.mvrAfi.customerDetail != ""){
		customerCode = closing.mvrAfi.customerDetail;
	}else if(closing.mvrAfi.agencyDetail != null && closing.mvrAfi.agencyDetail != ""){
		customerCode = closing.mvrAfi.agencyDetail;
	}
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出始发空运往来月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params  = {
				'mvrAfiVo.mvrAfiDto.period':closing.mvrAfi.period,
				'mvrAfiVo.mvrAfiDto.customerCode':customerCode,
				'mvrAfiVo.mvrAfiDto.orgType':closing.mvrAfi.orgType,
				'mvrAfiVo.mvrAfiDto.orgCode':closing.mvrAfi.orgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportMvrAfiForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrAfiForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrAfiExport.action'), 
				form: Ext.fly('exportMvrAfiForm'),
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
closing.mvrAfi.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrAfiComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrAfi.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrAfiComboModel', {
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
Ext.define('Foss.closing.mvrAfiModel', {
			extend : 'Ext.data.Model',
			fields : [{
                    /*应付到达代理成本冲应收到付运费已签收*/
                    name:'airPrAgencyWoDestRcvPod',
                    type:'decimal'
                },{
                    /*应付到达代理成本冲应收到付运费未签收*/
                    name:'airPrAgencyWoDestRcvNpod',
                    type:'decimal'
                },{
                    /*其他应付冲应收到付运费已签收*/
                    name:'airPrOtWoDestRcvPod',
                    type:'decimal'
                },{
                    /*其他应付冲应收到付运费未签收*/
                    name:'airPrOthWoDestRcvNPod',
                    type:'decimal'
                },{
                    /*空运签收时未核销代收货款*/
                    name:'airCodPodNwoCod',
                    type:'decimal'
                },{
                    /*空运反签收时未核销代收货款*/
                    name:'airCodNpodNwoCod',
                    type:'decimal'
                },{
                    /*空运还款代收货款现金已签收*/
                    name:'airCodChPod',
                    type:'decimal'
                },{
                    /*空运还款代收货款银行已签收*/
                    name:'airCodCdPod',
                    type:'decimal'
                },{
                    /*空运签收时已核销代收货款*/
                    name:'airCodPodWoCod',
                    type:'decimal'
                },{
                    /*客户名称*/
                    name:'customerName',
                    type:'string'
                },{
                    /*空运反签收时已核销代收货款*/
                    name:'airCodNpodWoCod',
                    type:'decimal'
                },{
                    /*空运到达代理应付冲应收代收货款已签收*/
                    name:'airCodWoAgencyPayPod',
                    type:'decimal'
                },{
                    /*空运其他应付冲应收代收货款已签收*/
                    name:'airCodWoOthPayCod',
                    type:'decimal'
                },{
                    /*部门编码*/
                    name:'orgCode',
                    type:'string'
                },{
                    /*始发/空运*/
                    name:'orgType',
                    type:'string'
                },{
                    /*预收空运代理冲应收到付运费已签收*/
                    name:'airDrDestRcvPod',
                    type:'decimal'
                },{
                    /*预收空运代理冲应收到付运费未签收*/
                    name:'airDrDestRcvNpod',
                    type:'decimal'
                },{
                    /*预收空运代理冲应收到付运费已签收*/
                    name:'airDrWoCodRcvPod',
                    type:'decimal'
                },{
                    /*还款现金未签收*/
                    name:'urDestChNpod',
                    type:'decimal'
                },{
                    /*ID*/
                    name:'id',
                    type:'string'
                },{
                    /*还款银行未签收*/
                    name:'urDestCdNpod',
                    type:'decimal'
                },{
                    /*还款现金已签收*/
                    name:'urDestChPod',
                    type:'decimal'
                },{
                    /*还款银行已签收*/
                    name:'urDestCdPod',
                    type:'decimal'
                },{
                    /*期间*/
                    name:'period',
                    type:'string'
                },{
                    /*部门名称*/
                    name:'orgName',
                    type:'string'
                },{
                    /*客户编码*/
                    name:'customerCode',
                    type:'string'
                }]
		})

//Store
Ext.define('Foss.closing.mvrAfiStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrAfiModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrAfiByConditions.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrAfiVo.mvrAfiDto.mvrAfiEntities',
			totalProperty : 'totalCount'
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
    			var form = Ext.getCmp('T_closing-mvrAfi_content').getQueryForm().getForm();
    			closing.mvrAfi.period = form.findField('mvrAfiDto.period').getValue();
    			closing.mvrAfi.customer = form.findField('mvrAfiDto.customerCode').getValue();
    			closing.mvrAfi.customerDetail = form.findField('mvrAfiDto.customerDetail').getValue();
    			closing.mvrAfi.agencyDetail = form.findField('mvrAfiDto.agencyDetail').getValue();
    			closing.mvrAfi.orgType = form.findField('mvrAfiDto.orgType').getValue();
    			closing.mvrAfi.orgCode = form.findField('mvrAfiDto.orgCode').getValue();
    			var customerCode = "";
    			if(closing.mvrAfi.customer != null && closing.mvrAfi.customer != ""){
    				customerCode = closing.mvrAfi.customer;
    			}else if(closing.mvrAfi.customerDetail != null && closing.mvrAfi.customerDetail != ""){
    				customerCode = closing.mvrAfi.customerDetail;
    			}else if(closing.mvrAfi.agencyDetail != null && closing.mvrAfi.agencyDetail != ""){
    				customerCode = closing.mvrAfi.agencyDetail;
    			}
    			//获取查询条件
    			searchParams = {
    					'mvrAfiVo.mvrAfiDto.period':closing.mvrAfi.period,
    					'mvrAfiVo.mvrAfiDto.customerCode':customerCode,
    					'mvrAfiVo.mvrAfiDto.orgType':closing.mvrAfi.orgType,
    					'mvrAfiVo.mvrAfiDto.orgCode':closing.mvrAfi.orgCode
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
		
// 定义查询Form
Ext.define('Foss.closing.mvrAfiQueryForm', {
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
				name : 'mvrAfiDto.period',
				fieldLabel:'查询期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrAfi.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .3
			},{
		    	xtype: 'combobox',
				fieldLabel:'客户类型',
				name:'mvrAfiDto.customerType',
				labelWidth:85,
		    	editable:false,
				store:FossDataDictionary.getDataDictionaryStore('SETTLEMENT__CUSTOMER_TYPE',null,null,
						[closing.mvrAfi.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
						 closing.mvrAfi.SETTLEMENT__CUSTOMER_TYPE__AIR,
						 closing.mvrAfi.SETTLEMENT__CUSTOMER_TYPE__AIR_AGENCY]),
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				value:closing.mvrAfi.SETTLEMENT__CUSTOMER_TYPE__LINE_CUSTOMER,
		    	columnWidth:.3,
		    	listeners:{
        		    	'change':function(th,newValue,oldValue){
						//获取表单等控件
						var form,//表单
							customerCode,
							customerDetail,
							agencyDetial;
						//获取表单	
						form= this.up('form').getForm();
						//获取下面组件
						customerCode = form.findField('mvrAfiDto.customerCode');
						customerDetail = form.findField('mvrAfiDto.customerDetail');
						agencyDetail = form.findField('mvrAfiDto.agencyDetail');
						if( newValue=='LC'){
							customerCode.show();
							customerDetail.hide();
							customerDetail.setValue("");
							agencyDetail.hide();
							agencyDetail.setValue("");
						}else if(newValue=='A'){
							customerCode.hide();
							customerCode.setValue("");
							customerDetail.show();
							agencyDetail.hide();
							agencyDetail.setValue("");
						}else if(newValue=='AA'){
							customerCode.hide();
							customerCode.setValue("");
							customerDetail.hide();
							customerDetail.setValue("");
							agencyDetail.show();
						}
					}
				}
		    },{
		    	xtype:'commoncustomerselector',
		    	name:'mvrAfiDto.customerCode',
		    	fieldLabel:'客户信息',
		    	singlePeopleFlag : 'Y',
	    		listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
		 	},{
				xtype : 'commonairlinesselector',
				fieldLabel : '航空公司',
				name : 'mvrAfiDto.customerDetail',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			}, {
				xtype : 'commonallagentselector',
				fieldLabel : '空运代理',
				name : 'mvrAfiDto.agencyDetail',
				hidden:true,
				listWidth:300,//设置下拉框宽度
				isPaging:true, //分页
				columnWidth : .3
			},{
				xtype : 'combo',
				name : 'mvrAfiDto.orgType',
				fieldLabel:'始发/到达',
		    	store:FossDataDictionary.getDataDictionaryStore('VOUCHER__ORG_TYPE',null,{
					 'valueCode': '',
		       		 'valueName': '全部'
				}),
		    	editable:false,
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				columnWidth : .3
			},{
				xtype : 'dynamicorgcombselector',
				name : 'mvrAfiDto.orgCode',
				fieldLabel : '部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3
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
							handler :function(){
								this.up('form').getForm().reset();
							}
						}, {
							xtype : 'container',
							border : false,
							html : '&nbsp;',
							columnWidth : .7
						}, {
							text : '查询',
							columnWidth : .1,
							cls : 'yellow_button',
							handler:function(){
								var form = this.up('form').getForm();
								var me = this;
								if(form.isValid()){
									closing.mvrAfi.queryMvrAfiByConditions(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.mvrAfiQueryGrid', {
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
		enableTextSelection : true
		// 设置行可以选择，进而可以复制
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
				handler:closing.mvrAfi.exportMvrAfi,
				disabled:!closing.mvrAfi.isPermission('/stl-web/closing/mvrAfiExport.action'),
				hidden:!closing.mvrAfi.isPermission('/stl-web/closing/mvrAfiExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
			xtype:'rownumberer',
			width:40
		}, {
			text : '数据统计维度',
			columns : [ {
			text : 'ID',
			hidden:true,
			dataIndex : 'id' 
		}, {
			text : '客户编码',
			dataIndex : 'customerCode'
		}, {
			text : '客户名称',
			dataIndex : 'customerName'		
		}, {
			text : '部门类型',
			dataIndex : 'orgType',
			renderer:function(value){
    			var display = null;
    			if(!Ext.isEmpty(value) &&value=='A'){
    				display = '到达';
    			}else if(!Ext.isEmpty(value)){
    				display = '始发';
    			}
    			return display;
    		}
		
		}, {
			text : '部门编码',
			dataIndex : 'orgCode'
		}, {
			text : '部门名称',
			dataIndex : 'orgName'
		}]
	}, {
		text : '空运应付冲应收',
		columns : [ {
			text : '应付到达代理成本冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'airPrAgencyWoDestRcvPod'
		}, {
			text : '应付到达代理成本冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'airPrAgencyWoDestRcvNpod'
		}, {
			text : '其他应付冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'airPrOtWoDestRcvPod'
		}, {
			text : '其他应付冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'airPrOthWoDestRcvNPod'
		}]
	},{
		text : '空运代收货款',
		columns : [ {
			text : '空运签收时</br>未核销代收货款',
			width:120,
			dataIndex : 'airCodPodNwoCod'
		}, {
			text : '空运反签收时</br>未核销代收货款',
			width:130,
			dataIndex : 'airCodNpodNwoCod'
		}, {
			text : '空运还款代收货款</br>现金已签收',
			width:130,
			dataIndex : 'airCodChPod'
		}, {
			text : '空运还款代收货款</br>银行已签收',
			width:130,
			dataIndex : 'airCodCdPod'
		}, {
			text : '空运签收时</br>已核销代收货款',
			width:150,
			dataIndex : 'airCodPodWoCod'
		}, {
			text : '空运反签收时</br>已核销代收货款',
			width:150,
			dataIndex : 'airCodNpodWoCod'
		}, {
			text : '空运到达代理应付冲</br>应收代收货款已签收',
			width:150,
			dataIndex : 'airCodWoAgencyPayPod'
		}, {
			text : '空运其他应付冲</br>应收代收货款已签收',
			width:150,
			dataIndex : 'airCodWoOthPayCod'
		}]
	},{
		text : '预收空运代理',
		columns : [ {
			text : '预收空运代理冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'airDrDestRcvPod'
		}, {
			text : '预收空运代理冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'airDrDestRcvNpod'
		}, {
			text : '预收空运代理冲</br>应收代收货款已签收',
			width:150,
			dataIndex : 'airDrWoCodRcvPod'
		}]
	},{
		text : '还款运单总运费（到付）',
		columns : [ {
			text : '还款现金未签收',
			width:150,
			dataIndex : 'urDestChNpod'
		}, {
			text : '还款银行未签收',
			width:150,
			dataIndex : 'urDestCdNpod'
		}, {
			text : '还款现金已签收',
			width:150,
			dataIndex : 'urDestChPod'
		}, {
			text : '还款银行已签收',
			width:150,
			dataIndex : 'urDestCdPod'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrAfiStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrAfi_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrAfiQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrAfiQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrAfi_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [queryForm,queryGrid],
						getQueryGrid : function() {
							return queryGrid;
						},
						getQueryForm:function(){
							return queryForm;
						},
						renderTo : 'T_closing-mvrAfi-body'
					});
		});