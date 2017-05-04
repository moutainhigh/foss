closing.mvrLdi.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

closing.mvrLdi.querymvrLdiByConditions = function(form,me){
	var m =Ext.getCmp('T_closing-mvrLdi_content');
	var grid = m.getQueryGrid();
	
	closing.mvrLdi.period = form.findField('mvrLdiDto.period').getValue();
	closing.mvrLdi.customer = form.findField('mvrLdiDto.landStowage').getValue();
	closing.mvrLdi.orgType = form.findField('mvrLdiDto.orgType').getValue();
	closing.mvrLdi.orgCode = form.findField('mvrLdiDto.orgCode').getValue();
	
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'vo.dto.period':closing.mvrLdi.period,
				'vo.dto.customerCode':closing.mvrLdi.customer,
				'vo.dto.orgType':closing.mvrLdi.orgType,
				'vo.dto.orgCode':closing.mvrLdi.orgCode
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
Ext.define('Foss.mvrLdi.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrLdi.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrLdi.PeriodModel',
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
 * 导出快递代理往来月报表
 */
closing.mvrLdi.exportmvrLdi = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrLdi_content');
	var queryGrid = mainPane.getQueryGrid();
	var customerCode = closing.mvrLdi.customer;
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出始发快递代理往来月报表吗?',function(btn,text){
		
		if('yes' == btn){
			if(queryGrid.store.data.length==0){
				Ext.Msg.alert('温馨提示', '报表明细为空，不能进行导出操作！');
				return false;
			}
			var params  = {
				'vo.dto.period':closing.mvrLdi.period,
				'vo.dto.customerCode':customerCode,
				'vo.dto.orgType':closing.mvrLdi.orgType,
				'vo.dto.orgCode':closing.mvrLdi.orgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportmvrLdiForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrLdiForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrLdiExport.action'), 
				form: Ext.fly('exportmvrLdiForm'),
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
closing.mvrLdi.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrLdiComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrLdi.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrLdiComboModel', {
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

// 快递代理月报表数据模型
Ext.define('Foss.closing.mvrLdiModel', {
	extend : 'Ext.data.Model',
	fields : [{
	        name:'landCodPodNwoCod',
	        type:'decimal'
	    },{
	        name:'landCodChPod',
	        type:'decimal'
	    },{
	        name:'landCodCdPod',
	        type:'decimal'
	    },{
	        name:'landCodNpodNwoCod',
	        type:'decimal'
	    },{
	        name:'landCodPodWoCod',
	        type:'decimal'
	    },{
	        name:'landCodNpodWoCod',
	        type:'decimal'
	    },{
	        name:'landCodWoAgencyPayPod',
	        type:'decimal'
	    },{
	        name:'landCodWoOthPayCod',
	        type:'decimal'
	    },{
	        name:'landUrDestChNpod',
	        type:'decimal'
	    },{
	        /*客户名称*/
	        name:'customerName',
	        type:'string'
	    },{
	        name:'landUrDestCdNpod',
	        type:'decimal'
	    },{
	        name:'landUrDestChPod',
	        type:'decimal'
	    },{
	        name:'landUrDestCdPod',
	        type:'decimal'
	    },{
	        /*部门编码*/
	        name:'orgCode',
	        type:'string'
	    },{
	        /*始发/快递代理*/
	        name:'orgType',
	        type:'string'
	    },{
	        name:'landPrAgencyWoDestRcvPod',
	        type:'decimal'
	    },{
	        name:'landPrAgencyWoDestRcvNp',
	        type:'decimal'
	    },{
	        name:'landPrOtWoDestRcvPod',
	        type:'decimal'
	    },{
	        name:'landPrOthWoDestRcvNpod',
	        type:'decimal'
	    },{
	        /*ID*/
	        name:'id',
	        type:'string'
	    },{
	        name:'landDrDestRcvPod',
	        type:'decimal'
	    },{
	        name:'landDrDestRcvNpod',
	        type:'decimal'
	    },{
	        name:'landDrWoCodRcvPod',
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
});

//Store
Ext.define('Foss.closing.mvrLdiStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrLdiModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('queryMvrLdiByConditions.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'vo.dto.queryList',
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
    			var form = Ext.getCmp('T_closing-mvrLdi_content').getQueryForm().getForm();
    			closing.mvrLdi.period = form.findField('mvrLdiDto.period').getValue();
    			closing.mvrLdi.customer = form.findField('mvrLdiDto.landStowage').getValue();
    			closing.mvrLdi.orgType = form.findField('mvrLdiDto.orgType').getValue();
    			closing.mvrLdi.orgCode = form.findField('mvrLdiDto.orgCode').getValue();
    			var customerCode = closing.mvrLdi.customer;
    			
    			//获取查询条件
    			searchParams = {
    					'vo.dto.period':closing.mvrLdi.period,
    					'vo.dto.customerCode':customerCode,
    					'vo.dto.orgType':closing.mvrLdi.orgType,
    					'vo.dto.orgCode':closing.mvrLdi.orgCode
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
Ext.define('Foss.closing.mvrLdiQueryForm', {
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
				name : 'mvrLdiDto.period',
				fieldLabel:'查询期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrLdi.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .45
			},{
				xtype:'commonLdpAgencyCompanySelector',
		    	fieldLabel :'快递代理',
		    	name : 'mvrLdiDto.landStowage',
				isPaging:true ,// 分页
				columnWidth:.45
	    	},{
				xtype : 'combo',
				name : 'mvrLdiDto.orgType',
				fieldLabel:'始发/到达',
		    	store:FossDataDictionary.getDataDictionaryStore('VOUCHER__ORG_TYPE',null,{
					 'valueCode': '',
		       		 'valueName': '全部'
				}),
		    	editable:false,
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				columnWidth :.45
			},{
				xtype : 'dynamicorgcombselector',
				name : 'mvrLdiDto.orgCode',
				fieldLabel : '部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .45
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
									closing.mvrLdi.querymvrLdiByConditions(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}
						}]
			}]
		})

// 快递代理月报表查询Grid
Ext.define('Foss.closing.mvrLdiQueryGrid', {
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
		enableTextSelection : true,
		// 设置行可以选择，进而可以复制
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
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrLdi.exportmvrLdi,
				disabled:!closing.mvrLdi.isPermission('/stl-web/closing/mvrLdiExport.action'),
				hidden:!closing.mvrLdi.isPermission('/stl-web/closing/mvrLdiExport.action')
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
			text : '期间',
			dataIndex : 'period'
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
    			if(!Ext.isEmpty(value) &&value=='G'){
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
	},{
		text : '快递代理代收货款',
		columns : [ {
			text : '快递代理签收时</br>未核销代收货款',
			width:120,
			dataIndex : 'landCodPodNwoCod'
		}, {
			text : '快递代理反签收时</br>未核销代收货款',
			width:130,
			dataIndex : 'landCodNpodNwoCod'
		}, {
			text : '快递代理还款代收货款</br>现金已签收',
			width:130,
			dataIndex : 'landCodChPod'
		}, {
			text : '快递代理还款代收货款</br>银行已签收',
			width:130,
			dataIndex : 'landCodCdPod'
		}, {
			text : '快递代理签收时</br>已核销代收货款',
			width:150,
			dataIndex : 'landCodPodWoCod'
		}, {
			text : '快递代理反签收时</br>已核销代收货款',
			width:150,
			dataIndex : 'landCodNpodWoCod'
		}, {
			text : '快递代理应付冲</br>应收代收货款已签收',
			width:150,
			dataIndex : 'landCodWoAgencyPayPod'
		}, {
			text : '快递代理其他应付冲</br>应收代收货款已签收',
			width:150,
			dataIndex : 'landCodWoOthPayCod'
		}]
	},{
		text : '还款运单总运费（到付）',
		columns : [ {
			text : '还款现金未签收',
			width:150,
			dataIndex : 'landUrDestChNpod'
		}, {
			text : '还款银行未签收',
			width:150,
			dataIndex : 'landUrDestCdNpod'
		}, {
			text : '还款现金已签收',
			width:150,
			dataIndex : 'landUrDestChPod'
		}, {
			text : '还款银行已签收',
			width:150,
			dataIndex : 'landUrDestCdPod'
		}]
	}, {
		text : '快递代理应付冲应收',
		columns : [ {
			text : '应付到达代理成本冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'landPrAgencyWoDestRcvPod'
		}, {
			text : '应付到达代理成本冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'landPrAgencyWoDestRcvNp'
		}, {
			text : '其他应付冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'landPrOtWoDestRcvPod'
		}, {
			text : '其他应付冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'landPrOthWoDestRcvNpod'
		}]
	},{
		text : '预收快递代理',
		columns : [ {
			text : '预收快递代理冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'landDrDestRcvPod'
		}, {
			text : '预收快递代理冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'landDrDestRcvNpod'
		}, {
			text : '预收快递代理冲</br>应收代收货款已签收',
			width:150,
			dataIndex : 'landDrWoCodRcvPod'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrLdiStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrLdi_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrLdiQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrLdiQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrLdi_content',
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
						renderTo : 'T_closing-mvrLdi-body'
					});
		});