/**
 * 获取上个月期间
 */
closing.mvrWooden.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrWooden.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrWooden.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrWooden.PeriodModel',
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

closing.mvrWooden.querymvrWoodenByConditions = function(form,me){
	var m =Ext.getCmp('T_closing-mvrWooden_content');
	var grid = m.getQueryGrid();
	//期间
	closing.mvrWooden.period = form.findField('period').getValue();
	//客户
	closing.mvrWooden.customer = form.findField('customerCode').getValue();
	//起始部门
	closing.mvrWooden.orgCode = form.findField('origOrgCode').getValue();
	//到达部门
	closing.mvrWooden.destCode = form.findField('destOrgCode').getValue();
	
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'mvrWoodenVo.mvrWoodenDto.period':closing.mvrWooden.period,
				'mvrWoodenVo.mvrWoodenDto.customerCode':closing.mvrWooden.customer,
				'mvrWoodenVo.mvrWoodenDto.origOrgCode':closing.mvrWooden.orgCode,
				'mvrWoodenVo.mvrWoodenDto.destOrgCode':closing.mvrWooden.destCode
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
			var result =   Ext.decode(operation.response.responseText);  
			if (!success && result.isException) {
				Ext.Msg.alert("提示", result.message);
				me.enable(true);
				return false;
			}
			me.enable(true);
		}
	});
}

closing.mvrWooden.exportmvrWooden = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrWooden_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出代打木架月报表吗?',function(btn,text){
		if('yes' == btn){
			var params = {
				'mvrWoodenVo.mvrWoodenDto.period':closing.mvrWooden.period,
				'mvrWoodenVo.mvrWoodenDto.customerCode':closing.mvrWooden.customer,
				'mvrWoodenVo.mvrWoodenDto.origOrgCode':closing.mvrWooden.orgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportmvrWoodenForm')){
				var frm = document.createElement('form');
				frm.id = 'exportmvrWoodenForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrWoodenExport.action'), 
				form: Ext.fly('exportmvrWoodenForm'),
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
closing.mvrWooden.getComboPeriodStore = function() {
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
		model : 'Foss.closing.mvrWoodenComboModel',
		data : periods
	});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrWooden.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrWoodenComboModel', {
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
Ext.define('Foss.closing.mvrWoodenModel', {
			extend : 'Ext.data.Model',
			fields : [
			          {name:'period',               type:	'string'},                                           
			          {name:'customerCode',         type:	'string'},                             
			          {name:'customerName',         type:	'string'},                             
			          {name:'origOrgCode',          type:	'string'},                        
			          {name:'origOrgName',          type:	'string'},
			          {name:'destOrgCode',          type:	'string'},                        
			          {name:'destOrgName',          type:	'string'}, 
			          {name:'woodenCost',           type:'decimal'},   
			          {name:'wrEntry',            type:'decimal'},
			          {name:'wpEntry',           type:'decimal'},
			          {name:'wothPayWoOthRc',  type:'decimal'},
			          {name:'woodenOrigPayTail',    type:'decimal'}]
		});

//Store
Ext.define('Foss.closing.mvrWoodenStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrWoodenModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('querymvrWooden.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrWoodenVo.mvrWoodenDto.list',
			totalProperty : 'totalCount'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
        			var searchParams;
        			var form = Ext.getCmp('T_closing-mvrWooden_content').getQueryTab().getForm();
        			closing.mvrWooden.period = form.findField('period').getValue();
        			closing.mvrWooden.customer = form.findField('customerCode').getValue();
        			closing.mvrWooden.orgCode = form.findField('origOrgCode').getValue();
        			closing.mvrWooden.destCode = form.findField('destOrgCode').getValue();
    				//获取查询条件
    				searchParams = {
						'mvrWoodenVo.mvrWoodenDto.period':closing.mvrWooden.period,
						'mvrWoodenVo.mvrWoodenDto.customerCode':closing.mvrWooden.customer,
						'mvrWoodenVo.mvrWoodenDto.origOrgCode':closing.mvrWooden.orgCode,
    				    'mvrWoodenVo.mvrWoodenDto.destOrgCode':closing.mvrWooden.destCode
    				};
    	   		    Ext.apply(me.submitParams, {
    		          "limit":operation.limit,
    		          "page":operation.page,
    		          "start":operation.start
    		        }); 
    	   			Ext.apply(operation, {
    	   				params : searchParams 
    	   			});
    	   		} 
		};
		me.callParent([ cfg ]);
	} 
}); 

//定义查询Form
Ext.define('Foss.closing.mvrWoodenQueryForm', {
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
				type : 'column'
			},
			items : [{
				xtype : 'combo',
				name : 'period',
				fieldLabel:'查询期间',
				queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrWooden.PeriodStore'),
				displayField:'name',
				valueField:'name',
				allowBlank : false,
				columnWidth : .3
			},{ 
				 fieldLabel:'包装代理',
			     xtype:'dynamicPackagingSupplierSelector',
			     listWidth:300,
			     name:'customerCode',
			     active:'Y',
				 columnWidth : .3
		 	},{
				xtype : 'dynamicorgcombselector',
				name : 'origOrgCode',
				fieldLabel : '始发部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3
			},{
				xtype : 'dynamicorgcombselector',
				name : 'destOrgCode',
				fieldLabel : '到达部门',
				allowblank : true,
				listWidth : 300,// 设置下拉框宽度
				isPaging : true,
				columnWidth : .3
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
							closing.mvrWooden.querymvrWoodenByConditions(form,me);
						}else{
							Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
							return false;
						}
					}
				}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.mvrWoodenQueryGrid', {
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
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrWooden.exportmvrWooden,
				disabled:!closing.mvrWooden.isPermission('/stl-web/closing/mvrWoodenExport.action'),
				hidden:!closing.mvrWooden.isPermission('/stl-web/closing/mvrWoodenExport.action')
			});
		}
		return me.exportButton;
	},
	columns : [{
			text:'序号',
			hidden:true,
			xtype:'rownumberer',
			width:40
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
			text : '始发部门编码',
			dataIndex : 'origOrgCode'
		}, {
			text : '始发部门名称',
			dataIndex : 'origOrgName'
		},{
			text : '到达部门编码',
			dataIndex : 'destOrgCode'
		}, {
			text : '到达部门名称',
			dataIndex : 'destOrgName'
		},{
			text : '包装入成本',
			width:150,
			dataIndex : 'woodenCost'
		}, {
			text : '包装其他应收录入',
			width:150,
			dataIndex : 'wrEntry'
		},{
			text : '包装其他应付录入',
			width:150,
			dataIndex : 'wpEntry'
		}, {
			text : '应付冲其他应收',
			width:150,
			dataIndex : 'wothPayWoOthRc'
		}, {
			text : '打木架付款申请',
			width:150,
			dataIndex : 'woodenOrigPayTail'
		}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.closing.mvrWoodenStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrWooden_content')) {
				return;
			}
			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrWoodenQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrWoodenQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrWooden_content',
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
						renderTo : 'T_closing-mvrWooden-body'
					});
		});