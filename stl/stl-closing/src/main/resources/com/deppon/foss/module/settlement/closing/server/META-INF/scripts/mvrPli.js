/**
 * 获取上个月期间
 */
closing.mvrPli.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrPli.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrPli.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPli.PeriodModel',
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

closing.mvrPli.querymvrPliByConditions = function(form,me){
	var m =Ext.getCmp('T_closing-mvrPli_content');
	var grid = m.getQueryGrid();
	
	closing.mvrPli.period = form.findField('mvrPliDto.period').getValue();
	closing.mvrPli.customerCode = form.findField('mvrPliDto.customerCode').getValue();
	closing.mvrPli.orgType = form.findField('mvrPliDto.orgType').getValue();
	closing.mvrPli.orgCode = form.findField('mvrPliDto.orgCode').getValue();
	grid.store.on('beforeload',function(store, operation, eOpts){
		Ext.apply(operation,{
			params :{
				'mvrPliVo.mvrPliDto.period':closing.mvrPli.period,
				'mvrPliVo.mvrPliDto.customerCode':closing.mvrPli.customerCode,
				'mvrPliVo.mvrPliDto.orgType':closing.mvrPli.orgType,
				'mvrPliVo.mvrPliDto.orgCode':closing.mvrPli.orgCode
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

closing.mvrPli.exportMvrPli = function(){
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrPli_content');
	var queryGrid = mainPane.getQueryGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出始发偏线往来月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params = {
					'mvrPliVo.mvrPliDto.period':closing.mvrPli.period,
					'mvrPliVo.mvrPliDto.customerCode':closing.mvrPli.customerCode,
					'mvrPliVo.mvrPliDto.orgType':closing.mvrPli.orgType,
					'mvrPliVo.mvrPliDto.orgCode':closing.mvrPli.orgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportMvrPliForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrPliForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrPliExport.action'), 
				form: Ext.fly('exportMvrPliForm'),
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
closing.mvrPli.getComboPeriodStore = function() {
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
				model : 'Foss.closing.mvrPliComboModel',
				data : periods
			});
}

/**
 * 获取业务类型下拉框Store
 */
closing.mvrPli.getComboProductTypeStore = function() {
	return Ext.create('Foss.pkp.ProductStore');
}

/** 数据模型 */

// 下拉框(combo)数据模型
Ext.define('Foss.closing.mvrPliComboModel', {
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
Ext.define('Foss.closing.mvrPliModel', {
			extend : 'Ext.data.Model',
			fields : [{
                    /*期间*/
                    name:'period',
                    type:'decimal'
                },{
                    /*应付偏线代理成本冲应收到付运费已签收*/
                    name:'plCostWoDestRcvPod',
                    type:'decimal'
                },{
                    /*应付偏线代理成本冲应收到付运费未签收*/
                    name:'plCostWoDestRcvNpod',
                    type:'decimal'
                },{
                    /*还款现金未签收*/
                    name:'urDestChNpod',
                    type:'decimal'
                },{
                    /*还款银行未签收*/
                    name:'urDestCdNpod',
                    type:'decimal'
                },{
                    /*还款现金已签收*/
                    name:'urDestChPod',
                    type:'decimal'
                },{
                    /*始发/偏线*/
                    name:'orgType',
                    type:'decimal'
                },{
                    /*还款银行已签收*/
                    name:'urDestCdPod',
                    type:'decimal'
                },{
                    /*预收偏线代理冲应收到付运费已签收*/
                    name:'plDrWoDestRcvPod',
                    type:'decimal'
                },{
                    /*ID*/
                    name:'id',
                    type:'decimal'
                },{
                    /* 预收偏线代理冲应收到付运费未签收*/
                    name:'plDrWoDestRcvNpod',
                    type:'decimal'
                },{
                    /*部门名称*/
                    name:'orgName',
                    type:'decimal'
                },{
                    /*部门编码*/
                    name:'orgCode',
                    type:'decimal'
                },{
                    /*客户编码*/
                    name:'customerCode',
                    type:'decimal'
                },{
                    /*客户名称*/
                    name:'customerName',
                    type:'decimal'
                }]
		})

//Store
Ext.define('Foss.closing.mvrPliStore',{
	extend:'Ext.data.Store',
	model:'Foss.closing.mvrPliModel',  
	pageSize:100,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url :closing.realPath('querymvrPliByConditions.action'),
		timeout:10*60*1000,
		reader : {
			type : 'json',
			root : 'mvrPliVo.mvrPliDto.mvrPliEntities',
			totalProperty : 'totalCount'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
        			var searchParams;
        			var form = Ext.getCmp('T_closing-mvrPli_content').getQueryTab().getForm();
        			closing.mvrPli.period = form.findField('mvrPliDto.period').getValue();
        			closing.mvrPli.customerCode = form.findField('mvrPliDto.customerCode').getValue();
        			closing.mvrPli.orgType = form.findField('mvrPliDto.orgType').getValue();
        			closing.mvrPli.orgCode = form.findField('mvrPliDto.orgCode').getValue();
    				//获取查询条件
    				searchParams = {
    						'mvrPliVo.mvrPliDto.period':closing.mvrPli.period,
    						'mvrPliVo.mvrPliDto.customerCode':closing.mvrPli.customerCode,
    						'mvrPliVo.mvrPliDto.orgType':closing.mvrPli.orgType,
    						'mvrPliVo.mvrPliDto.orgCode':closing.mvrPli.orgCode
    				}
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
		
// 定义查询Form
Ext.define('Foss.closing.mvrPliQueryForm', {
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
				xtype:'combobox',
				name : 'mvrPliDto.period',
		    	fieldLabel:'查询期间',
		    	/*editable:false,
				store:Ext.create('Foss.common.PeriodStore'),
			    queryMode: 'local', 	
				displayField:'name',
				valueField:'value',
				value:closing.mvrPli.getLastMonthPeriod(),
		    	columnWidth:.33*/
		    	queryMode: 'remote', 
		    	store:Ext.create('Foss.mvrPli.PeriodStore'),
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
				xtype : 'commonvehagencycompselector',
				fieldLabel : '偏线代理公司',
				name : 'mvrPliDto.customerCode',
				columnWidth : .33
			}, {
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.26
			},{
				xtype : 'combo',
				name : 'mvrPliDto.orgType',
		    	fieldLabel:'始发/到达',
		    	store:FossDataDictionary.getDataDictionaryStore('VOUCHER__ORG_TYPE',null,{
					 'valueCode': '',
		       		 'valueName': '全部'
				}),
		    	editable:false,
				queryModel:'local',
				displayField:'valueName',
				valueField:'valueCode',
				columnWidth : .33
			},{
				xtype: 'container',
				border : false,
				html: '&nbsp;',
				columnWidth:.04
			}, {
				xtype : 'dynamicorgcombselector',
				name : 'mvrPliDto.orgCode',
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
							handler : Ext.emptyFn
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
									closing.mvrPli.querymvrPliByConditions(form,me);
								}else{
									Ext.Msg.alert("温馨提醒","请检查输入条件是否合法");
									return false;
								}
							}			
						}]
			}]
		})

// 空运月报表查询Grid
Ext.define('Foss.closing.mvrPliQueryGrid', {
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
				handler:closing.mvrPli.exportMvrPli,
				disabled:!closing.mvrPli.isPermission('/stl-web/closing/mvrPliExport.action'),
				hidden:!closing.mvrPli.isPermission('/stl-web/closing/mvrPliExport.action')	
			});
		}
		return me.exportButton;
	},
	columns : [ {
		text : '数据统计维度',
		columns : [ {
			xtype:'rownumberer',
			width:40
		}, {
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
			/*renderer:function(value){
    			var displayField = FossDataDictionary.rendererSubmitToDisplay (value,'VOUCHER__ORG_TYPE');
    			return displayField;
    		}*/
			renderer:function(value){
    			var display = null;
    			if(!Ext.isEmpty(value) &&value=='P'){
    				display = '到达';
    			}else if(!Ext.isEmpty(value) &&value=='O'){
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
		text : '偏线代理成本',
		columns : [ {
			text : '应付偏线代理成本冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'plCostWoDestRcvPod'
		}, {
			text : '应付偏线代理成本冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'plCostWoDestRcvNpod'
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
	},{
		text : '预收偏线代理',
		columns : [ {
			text : '预收偏线代理冲</br>应收到付运费已签收',
			width:150,
			dataIndex : 'plDrWoDestRcvPod'
		}, {
			text : '预收偏线代理冲</br>应收到付运费未签收',
			width:150,
			dataIndex : 'plDrWoDestRcvNpod'
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.closing.mvrPliStore');

		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// 显示
Ext.onReady(function() {
			Ext.QuickTips.init();

			if (Ext.getCmp('T_closing-mvrPli_content')) {
				return;
			}

			// 查询FORM
			var queryForm = Ext.create('Foss.closing.mvrPliQueryForm');
			
			//显示grid
			var queryGrid = Ext.create('Foss.closing.mvrPliQueryGrid');

			// 显示到JSP页面
			Ext.create('Ext.panel.Panel', {
						id : 'T_closing-mvrPli_content',
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
						renderTo : 'T_closing-mvrPli-body'
					});
		});