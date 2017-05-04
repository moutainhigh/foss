/**
 * 获取上个月期间
 */
closing.mvrLwo.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

//客户类型：Model
Ext.define('Foss.mvrLwo.CustomerTypeModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'customerTypeCode'
	},{
		name:'customerTypeName'
	}]
	
});

//客户类型：store
Ext.define('Foss.mvrLwo.CustomerTypeStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrLwo.CustomerTypeModel',
	data:{
		'items':[
			{customerTypeCode:'01',customerTypeName:'客户'},
			{customerTypeCode:'02',customerTypeName:'空运代理'},
			{customerTypeCode:'03',customerTypeName:'偏线代理'},
			{customerTypeCode:'04',customerTypeName:'航空公司'},
			{customerTypeCode:'05',customerTypeName:'快递代理'}]
	},
	proxy:{
		type:'memory',
		reader:{
			type:'json',
			root:'items'
		}
	}
});

/**
 * 声明账期model
 */
Ext.define('Foss.mvrLwo.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrLwo.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrLwo.PeriodModel',
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

closing.mvrLwo.setParams=function(form){
	//定义查询参数
	var params={};
	
	var period = form.findField('mvrLwoDto.period').getValue();
	if(period==null||period==''){
		//期间不能为空
		Ext.Msg.alert('温馨提示', '期间不能为空');
		return null;	
	}
		
	//获取FORM所有值
	params = form.getValues();
		
	//客户类型
	var custType = form.findField('customerType').getValue();
	//给客户默认值
	var customerCode = null;
	//客户类型
	if(custType=='01'){
		customerCode=Ext.getCmp('closing.mvrLwo.commoncustomerselector').getValue();
	}else if(custType=='02'){
		customerCode=Ext.getCmp('closing.mvrLwo.commonairagencycompanyselector').getValue();
	}else if(custType=='03'){
		customerCode=Ext.getCmp('closing.mvrLwo.commonvehagencycompselector').getValue();
	}else if(custType=='04'){
		customerCode=Ext.getCmp('closing.mvrLwo.commonairlinesselector').getValue();
	}else if(custType=='05'){
		customerCode=Ext.getCmp('closing.mvrLwo.commonLdpAgencyCompanySelector').getValue();
	}
	
	
	
	return params;
}

/**
 * Form重置方法
 */
closing.mvrLwo.reset=function(){
	this.up('form').getForm().reset();
	// 客户信息
	Ext.getCmp('closing.mvrLwo.commoncustomerselector').show();
	// 空运代理公司
	Ext.getCmp('closing.mvrLwo.commonairagencycompanyselector').hide();
	// 偏线代理公司
	Ext.getCmp('closing.mvrLwo.commonvehagencycompselector').hide();
	// 航空公司
	Ext.getCmp('closing.mvrLwo.commonairlinesselector').hide();
	// 快递代理
	Ext.getCmp('closing.mvrLwo.commonLdpAgencyCompanySelector').hide();
}

/**
 * Form查询方法
 */
closing.mvrLwo.query=function(f,me){
	var form=f.getForm();
	var grid = Ext.getCmp('T_closing-mvrLwo_content').getQueryGrid();
	if(form.isValid()){
		var params=closing.mvrLwo.setParams(form);
		if(null==params){
			return;
		}
		//设置查询参数
		grid.store.setSubmitParams(params);
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		//设置统计值
		grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
			var result =   Ext.decode(operation.response.responseText);	
			me.enable(true);
	       }
	    });
	}else {
		
		//请检查输入条件是否合法
		Ext.Msg.alert('温馨提示', '请检查输入条件合法性');
	}
}

/**
 * 导出月报表
 */
closing.mvrLwo.exportmvrLwo = function(){
	//获取主面板、查询GRID
	var queryGrid = Ext.getCmp('T_closing-mvrLwo_content').getQueryGrid();
	if(queryGrid.store.data.length==0){
		Ext.Msg.alert('温馨提示','表格没有数据，不能进行导出操作');
		return false;
	}
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = queryGrid.store.submitParams;
			
			//创建一个form
			if(!Ext.fly('exportmvrLwoForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrLwoForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('exportMvrLwo.action'), 
				form: Ext.fly('exportmvrLwoForm'),
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
closing.mvrLwo.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrLwoComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrLwo.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrLwoComboModel', {
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

// 数据模型
Ext.define('Foss.closing.mvrLwoModel', {
			extend : 'Ext.data.Model',
			fields : [
			    'id', 'period', 'productCode', 'customerCode', 'customerName','customerType','origOrgCode', 'origOrgName', 'destOrgCode', 'destOrgName',
			    {
					name:'refundWoOrigLandRcvNpod',
					type:'double'
				},{
					name:'landClaimWoOrigRcvPod',
					type:'double'
				},{
					name:'landClaimWoOrigRcvNpod',
					type:'double'
				},{
					name:'landRefundWoOrigRcvPod',
					type:'double'
				},{
					name:'landRefundWoOrigRcvNpod',
					type:'double'
				},{
					name:'orRcvWoLandClaimPay',
					type:'double'
				},{
					name:'claimWoOrigLandRcvPod',
					type:'double'
				},{
					name:'claimWoOrigLandRcvNpod',
					type:'double'
				},{
					name:'custDrOrigLandRcvNpod',
					type:'double'
				},{
					name:'custDrOrigLandRcvPod',
					type:'double'
				},{
					name:'orLandRcvWoClaimPay',
					type:'double'
				},{
					name:'orLandRcvWoCustDr',
					type:'double'
				},{
					name:'refundWoOrigLandRcvPod',
					type:'double'
				},{
					name:'origOrgPayApply',
					type:'double'
				},{
					name:'destOrgPayApply',
					type:'double'
				}]
		})

//应收Store
Ext.define('Foss.closing.mvrLwoStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrLwoModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrLwo.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrLwoDto.queryList',
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
		
// 月报查询Form
Ext.define('Foss.closing.mvrLwoQueryForm', {
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
				name : 'mvrLwoDto.period',
				fieldLabel : '期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrLwo.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .33
			},
		    {
		    	xtype:'combobox',
		    	name:'customerType',
		    	columnWidth:.33,
		    	fieldLabel:'客户类型',
				store:Ext.create('Foss.mvrLwo.CustomerTypeStore'),
				queryModel:'local',
				value:'01',
				editable:false,
				displayField:'customerTypeName',
				valueField:'customerTypeCode',
		    	listeners:{
					"select": {
						fn: function(_combo, _r){
							var cusValue=_combo.ownerCt.getForm().findField('customerType').getValue();
							if(cusValue==='01'){
								// 客户信息
								Ext.getCmp('closing.mvrLwo.commoncustomerselector').show();
								// 空运代理公司
								Ext.getCmp('closing.mvrLwo.commonairagencycompanyselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrLwo.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrLwo.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrLwo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='02'){
								// 空运代理公司
								Ext.getCmp('closing.mvrLwo.commonairagencycompanyselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrLwo.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrLwo.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrLwo.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrLwo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='03'){
								// 偏线代理公司
								Ext.getCmp('closing.mvrLwo.commonvehagencycompselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrLwo.commoncustomerselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrLwo.commonairagencycompanyselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrLwo.commonairlinesselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrLwo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='04'){
								// 航空公司
								Ext.getCmp('closing.mvrLwo.commonairlinesselector').show();
								// 客户信息
								Ext.getCmp('closing.mvrLwo.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrLwo.commonvehagencycompselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrLwo.commonairagencycompanyselector').hide();
								// 快递代理
								Ext.getCmp('closing.mvrLwo.commonLdpAgencyCompanySelector').hide();
							}else if(cusValue==='05'){
								// 快递代理
								Ext.getCmp('closing.mvrLwo.commonLdpAgencyCompanySelector').show();
								// 客户信息
								Ext.getCmp('closing.mvrLwo.commoncustomerselector').hide();
								// 偏线代理公司
								Ext.getCmp('closing.mvrLwo.commonvehagencycompselector').hide();
								// 航空公司
								Ext.getCmp('closing.mvrLwo.commonairlinesselector').hide();
								// 空运代理公司
								Ext.getCmp('closing.mvrLwo.commonairagencycompanyselector').hide();
							}
						}
					}
				}
		    },{	
		    	xtype:'commoncustomerselector',
		    	listWidth:300,
		    	fieldLabel :'客户信息',
		    	name : 'mvrLwoDto.customerCode',
		    	singlePeopleFlag : 'Y',
		    	id:'closing.mvrLwo.commoncustomerselector',
		    	columnWidth:.33,
				isPaging:true // 分页
		    }
		    ,{	
		    	xtype:'commonallagentselector',
		    	fieldLabel : '空运代理公司',
		    	columnWidth:.33,
		    	name : 'mvrLwoDto.customerCode',
		    	id:'closing.mvrLwo.commonairagencycompanyselector',
				isPaging:true ,// 分页
				hidden:true
		    },
		    {	
		    	xtype:'commonvehagencycompselector',
		    	fieldLabel :'偏线代理公司',
		    	columnWidth:.33,
		    	name : 'mvrLwoDto.customerCode',
		    	id:'closing.mvrLwo.commonvehagencycompselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonairlinesselector',
		    	fieldLabel :'航空公司',
		    	columnWidth:.33,
		    	name : 'mvrLwoDto.customerCode',
		    	id:'closing.mvrLwo.commonairlinesselector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype:'commonLdpAgencyCompanySelector',
		    	fieldLabel :'快递代理',
		    	columnWidth:.33,
		    	name : 'mvrLwoDto.customerCode',
		    	id:'closing.mvrLwo.commonLdpAgencyCompanySelector',
				isPaging:true ,// 分页
				hidden:true
		    },{
				xtype : 'dynamicorgcombselector',
				name : 'mvrLwoDto.origOrgCode',
				fieldLabel : '营业部',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrLwoDto.destOrgCode',
				fieldLabel : '快递代理点部',
				type:'ORG',
				expressPart:'Y',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .33
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
							handler : closing.mvrLwo.reset
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
								  var form=this.up('form');
								  var me = this;
								  closing.mvrLwo.query(form,me)
							  }
						}]
			}]
		})

// 报表查询Grid
Ext.define('Foss.closing.mvrLwoQueryGrid', {
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
	height : 850,
	emptyText : '查询结果为空',
	viewConfig : {
		enableTextSelection : true,
		// 设置行可以选择，进而可以复制
		getRowClass:function(record, rowIndex, rowParams, store){
			count = store.getCount();
			if(count > 0 && rowIndex == count - 1){
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
	exportButton:null,
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrLwo.exportmvrLwo,
				disabled:!closing.mvrLwo.isPermission('/stl-web/closing/mvrLwoExport.action'),
				hidden:!closing.mvrLwo.isPermission('/stl-web/closing/mvrLwoExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text : '数据统计维度',
		defaults:{
			style:"text-align:center"
		},
		columns : [ {
			xtype:'rownumberer',
			width:40
		}, {
			text : '期间',
			width:110,
			dataIndex : 'period'
		}, {
			text : '客户名称',
			width:110,
			dataIndex : 'customerName'
		}, {
			text : '客户编码',
			width:110,
			dataIndex : 'customerCode'		
		},{
			text : '营业部编码',
			width:110,
			dataIndex : 'origOrgCode'
		}, {
			text : '营业部名称',
			width:110,
			dataIndex : 'origOrgName'
		}, {
			text : '快递代理点部编码',
			width:110,
			dataIndex : 'destOrgCode'
		},{
			text : '快递代理点部名称',
			width:110,
			dataIndex : 'destOrgName'
		} ]
	}, {
		text : '营业部预收客户冲销快递代理应收',
		columns:[{
			text:'营业部预收客户',
			columns : [{
				text : '预收客户冲应收始发运费未签收',
				width : 180,
				align : 'right',
				dataIndex : 'custDrOrigLandRcvNpod'
			}, {
				text : '预收客户冲应收始发运费已签收',
				width : 180,
				align : 'right',
				dataIndex : 'custDrOrigLandRcvPod'
			}]
		}]
	
	}, {
		text : '营业部理赔冲销快递代理应收',
		columns:[{
			text:'出发部门申请理赔',
			columns : [{
				text : '理赔冲始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimWoOrigLandRcvPod'
			}, {
				text : '理赔冲始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'claimWoOrigLandRcvNpod'
			}]
		}]
	}, {
		text : '营业部冲销快递代理小票应收',
		columns:[{
			text:'小票应收核销',
			columns : [{
				text : '应付理赔冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orLandRcvWoClaimPay'
			}, {
				text : '预收客户冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orLandRcvWoCustDr'
			}]
		}]
		
	}, {
		text : '营业部退运费冲销快递代理应收',
		columns : [{
			text:'出发部门申请退运费',
			columns : [{
				text : '退运费冲始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'refundWoOrigLandRcvPod'
			}, {
				text : '退运费冲始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'refundWoOrigLandRcvNpod'
			}]
		}]
	}, {
		text : '快递代理理赔冲销营业部应收',
		columns : [{
			text : '出发部门申请理赔',
			columns : [{
				text : '理赔冲始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'landClaimWoOrigRcvPod'
			}, {
				text : '理赔冲始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'landClaimWoOrigRcvNpod'
			}]
		}]
	}, {
		text : '快递代理冲销营业部应收小票',
		columns : [{
			text : '小票应收核销',
			columns : [{
				text : '应付理赔冲小票应收',
				width : 180,
				align : 'right',
				dataIndex : 'orRcvWoLandClaimPay'
			}]
		}]
	}, {
		text : '快递代理退运费冲销营业部应收',
		columns : [{
			text : '出发部门申请',
			columns : [{
				text : '退运费冲始发应收已签收',
				width : 180,
				align : 'right',
				dataIndex : 'landRefundWoOrigRcvPod'
			}, {
				text : '退运费冲始发应收未签收',
				width : 180,
				align : 'right',
				dataIndex : 'landRefundWoOrigRcvNpod'
			}]
		}]
	}, {
		text: '快递代理付款申请',
		defaults: {
			style: "text-align:center"
		},
		columns: [{
			text: '出发部门付款申请',
			width: 180,
			dataIndex: 'origOrgPayApply'
		}, {
			text: '到达部门付款申请',
			width: 180,
			dataIndex: 'destOrgPayApply'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.closing.mvrLwoStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.timeout = 600000;

			if (Ext.getCmp('T_closing-mvrLwo_content')) {
				return;
			}

			closing.mvrLwo.mvrLwoTotalRecord = Ext.create('Foss.closing.mvrLwoModel');
			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrLwoQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrLwoQueryGrid');
			
			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrLwo_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						//获得查询FORM
						getQueryFrom : function() {
							return queryForm;
						},
						//获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						items : [queryForm,queryGrid],
						renderTo : 'T_closing-mvrLwo-body'
					});
		});