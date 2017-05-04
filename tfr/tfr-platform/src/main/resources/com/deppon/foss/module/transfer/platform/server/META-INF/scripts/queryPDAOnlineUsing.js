
//定义跳转
platform.queryPDAOnlineUsing.showqueryPDAOnlineUsingDetail = function(transferCenterCode){
	 var queryDate =Ext.util.Format.date(Ext.getCmp('Foss.platform.queryPDAOnlineUsing.queryDate.id').getValue(), 'Y-m-d'); //查询日期
	 platform.addTab('T_platform-onLinePDADetailIndex','PDA使用最高峰明细',
			   			platform.realPath('onLinePDADetailIndex.action') + '?transferCenterCode="' + transferCenterCode + '"&queryDate="' + queryDate + '"');
	
}

//定义查询条件的panel
Ext.define('Foss.platform.queryPDAOnlineUsing.queryPDAOnlineForm',{
	extend:'Ext.form.Panel',
	id:'Foss.platform.queryPDAOnlineUsing.queryPDAOnlineForm.id',
	title: platform.queryPDAOnlineUsing.i18n('Foss.platform.queryPDAOnlineUsing.title'),//查询条件
	frame: true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	layout:'column',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		maxLength:20,
	    maxLengthText:platform.queryPDAOnlineUsing.i18n('Foss.platform.queryPDAOnlineUsing.tip.maxLength') //长度已超过最大限制
	},
	items:[{
		id:'Foss.platform.queryPDAOnlineUsing.hqcode.id',
		xtype:'dynamicorgcombselector',
		fieldLabel:platform.queryPDAOnlineUsing.i18n('Foss.platform.queryPDAOnlineUsing.label.hqcode'), //经营本部
		name:'hqCode',
		valueField:'code',
		displayField:'name',
		type:'ORG',
		columnWidth:.25,
		listeners: {
            select: {
		       fn: function(src, records, eOpts ) {
		    	   platform.queryPDAOnlineUsing.hqCode=records[0].data.code;
		    	}
	   		}
       }
	},{
		id:'Foss.platform.queryPDAOnlineUsing.transferCenterCode.id',
	    xtype:'commontransfercenterselector',
	    name:'transferCenterCode',
		fieldLabel:platform.queryPDAOnlineUsing.i18n('Foss.platform.queryPDAOnlineUsing.label.transferCenterCode'), //外场
		displayField:'name', 
		valueField:'orgCode',
		columnWidth:.25,
		listeners: {
            select: {
		       fn: function(src, records, eOpts ) {
		    	   platform.queryPDAOnlineUsing.transferCenterCode=records[0].data.orgCode;
		    	}
	   		}
       }
	},{
		id:'Foss.platform.queryPDAOnlineUsing.queryDate.id',
    	xtype:'datefield',
    	fieldLabel:platform.queryPDAOnlineUsing.i18n('Foss.platform.queryPDAOnlineUsing.label.queryDate'),  //查询日期
    	allowBlank:false,
		name:'queryDate',
		editable:false,
		value: login.currentServerTime,
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -0),
		format:'Y-m-d',
		columnWidth:.25
	},{
		readOnly:true,
		columnWidth:.25
	},{
		xtype:'button',
		text:platform.queryPDAOnlineUsing.i18n('Foss.platform.queryPDAOnlineUsing.button.reset'),  //重置
		width:90,
		handler: function() {
			var form = this.up('form').getForm();
			form.findField('queryDate').setValue(Ext.Date.format(login.currentServerTime,'Y-m-d'));
            form.findField('hqCode').setValue(platform.queryPDAOnlineUsing.hqName);
            form.findField('transferCenterCode').setValue(platform.queryPDAOnlineUsing.transferCenterName);

			
		},
		columnWidth:.10
	},{
		readOnly:true,
		columnWidth:.8
	},{
		xtype:'button',
		text:platform.queryPDAOnlineUsing.i18n('Foss.platform.queryPDAOnlineUsing.button.search'),  //查询
		width:90,
		cls:'yellow_button',
		handler:function(){
			var form = this.up('form').getForm();
			var grid=platform.queryPDAOnlineUsing.queryPDAOnlineUsingGrid;
			if(!form.isValid()){
				return;
			}
			var store=grid.getStore( );
			//清除掉之前的数据
			store.removeAll(false);
			//重新加载数据
			platform.queryPDAOnlineUsing.pagingBar.moveFirst();
		},
		columnWidth:.10
	}]
	
});


//定义查询pda在线使用的model

Ext.define('Foss.platform.queryPDAOnlineUsing.queryPDAOnlineMothModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			}, {
				name : 'hqCode',//本部编码
				type : 'string'
			}, {
				name : 'hqName',//本部名称
				type : 'string'
			}, {
				name : 'transferCenterCode',//外场code
				type : 'string'
			}, {
			    name : 'month',//月份
				type : 'string'
			},{
				name : 'transferCenterName',//外场name
				type : 'string'
			}, {
				name : 'clerkUesdPDACountDay',//日理货员使用PDA台数
				type : 'string'
			}, {
				name : 'clerkUesdPDAOlineTimeDay',//日理货员使用PDA在线最高峰时间
				type : 'string'
			}, {
				name : 'forkUesdPDACountDay',//日电叉车使用PDA台数
				type : 'string'
			}, {
				name : 'forkUesdPDAOlineTimeDay',//日电叉车使用PDA在线最高峰时间
				type : 'string'
			},{
				name : 'allUesdPDACountDay',//日所有人员使用PDA台数
				type : 'string'
			}, {
				name : 'allUesdPDAOlineTimeDay',//日所有人员使用PDA最高峰时间
				type : 'string'
			},{
				name : 'clerkPDAMaxQtyMonth',//月理货员使用PDA台数
				type : 'string'
			}, {
				name : 'clerkDateRelatedMonth',//月理货员使用PDA在线最高峰时间
				type : 'string'
			}, {
				name : 'forkPDAMaxQtyMonth',//月电叉车使用PDA台数
				type : 'string'
			}, {
				name : 'forkDateRelatedMonth',//月电叉车使用PDA在线最高峰时间
				type : 'string'
			},{
				name : 'allPDAMaxQtyMonth',//月所有人员使用PDA台数
				type : 'string'
			}, {
				name : 'allDateRelatedMonth',//月所有人员使用PDA最高峰时间
				type : 'string'
			},{
				name : 'forkMonthDayPoingTime',//叉车某月某天某点
				type : 'string'
			},{
				name : 'clerkMonthDayPoingTime',//理货员某月某天某点
				type : 'string'
			}, {
				name : 'allMonthDayPoingTime',//所有人员某月某天某点
				type : 'string'
			}
			
		 ]
});
//查询pda在线使用的store
Ext.define('Foss.platform.queryPDAOnlineUsing.queryPDAOnlineUsingStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.queryPDAOnlineUsing.queryPDAOnlineMothModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		//Ext.BLANK_IMAGE_URL='sendRateDayQuery.action',
		url : platform.realPath('queryOnLinePDAUsing.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'queryPDAOnlineUsingVo.pdaOnlineUsingDtoList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners: {
		'beforeload' : function(store, operation, eOpts) {
			
			    var queryDate=Ext.getCmp('Foss.platform.queryPDAOnlineUsing.queryDate.id').getValue('queryDate');
			    if(platform.queryPDAOnlineUsing.isHq==0)
				{
					platform.queryPDAOnlineUsing.hqCode=Ext.getCmp('Foss.platform.queryPDAOnlineUsing.hqcode.id').getValue();
					
				}
				if(platform.queryPDAOnlineUsing.isTfc==0)
				{
					platform.queryPDAOnlineUsing.transferCenterCode=Ext.getCmp('Foss.platform.queryPDAOnlineUsing.transferCenterCode.id').getValue();
					
				}
				Ext.apply(operation, {
					params : {
						'queryPDAOnlineUsingVo.hqCode':platform.queryPDAOnlineUsing.hqCode,
						'queryPDAOnlineUsingVo.orgCode':platform.queryPDAOnlineUsing.transferCenterCode,
						'queryPDAOnlineUsingVo.queryDate':queryDate
					}
				});	
		}
	}
});

//PDA在线查询gird
Ext.define('Foss.platform.queryPDAOnlineUsing.queryPDAOnlineUsingGrid', {
	extend : 'Ext.grid.Panel',
	id:'Foss.platform.queryPDAOnlineUsing.queryPDAOnlineUsingGrid.id',
	frame : true,
	//columnLines: true,
	title : 'PDA在线使用情况',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.platform.queryPDAOnlineUsing.queryPDAOnlineUsingStore');
		cfg = Ext.apply({}, config);
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			pageSize : 10,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10], ['20', 20], ['50', 50], ['100', 100]]
			})
		});
		platform.queryPDAOnlineUsing.pagingBar = me.bbar;	
		me.callParent([cfg]);
	},
	columns : [
	   /*{
		   xtype : 'actioncolumn',
		   width : 40,
		   text : '操作',
		   align : 'center',
		   items : [ {
			  iconCls : 'deppon_icons_showdetail',
			  handler : function(grid, rowIndex, colIndex, item, e) {
			   var transferCenterCode = grid.store.getAt(rowIndex).get('transferCenterCode');//外场编码
			   var transferCenterName = grid.store.getAt(rowIndex).get('transferCenterName');//外场编码
			   var queryDate =Ext.util.Format.date(Ext.getCmp('Foss.platform.queryPDAOnlineUsing.queryDate.id').getValue(), 'Y-m-d'); //查询日期
			   platform.addTab('T_platform-onLinePDADetailIndex',//对应打开的目标页面js的onReady里定义的renderTo
			   			'PDA使用最高峰明细',//platform.traybindmanager.i18n('foss.unload.traybindmanager.unloadTaskTraybindDetail'),
			   			platform.realPath('onLinePDADetailIndex.action') + '?transferCenterCode="' + transferCenterCode + '"&queryDate="' + queryDate + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
			   
		    }
		   
		   }]
		 },*/
	{
		dataIndex : 'transferCenterName',
		align : 'center',
		text:'转运场',
		width:100,
		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
		    var transferCenterCode = store.getAt(rowIndex).get('transferCenterCode');//外场编码
			return '<a href="javascript:platform.queryPDAOnlineUsing.showqueryPDAOnlineUsingDetail('+"'" + transferCenterCode + "'"+')">' + value + '</a>';				

		}
		
	}/*,{
	    dataIndex : 'month',
		align : 'center',
		text:'月份',
		width:100	
	}*/,{
		text : '当日最高峰使用情况',
		//width:400,
		//flex : 0.4,
		columns : [{
			text : '理货员',
			//width:160,
			columns:[{
					dataIndex : 'clerkUesdPDACountDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:70
			},{
			
			        dataIndex : 'clerkUesdPDAOlineTimeDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:90
			}]
			
		},{
			text : '电叉司机',
			columns:[{
					dataIndex : 'forkUesdPDACountDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:70
			},{
			
			        dataIndex : 'forkUesdPDAOlineTimeDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:90
			}]
		},{
			text : '所有人员',
			//width:160,
			columns:[{
					dataIndex : 'allUesdPDACountDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:70
					
			},{
			
			        dataIndex : 'allUesdPDAOlineTimeDay',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:90
			}]
		}]
	},{
		text : '当月最高峰使用情况',
		//width:400,
		//flex : 0.4,
		columns : [{
			text : '理货员',
			//width:160,
			columns:[{
					dataIndex : 'clerkPDAMaxQtyMonth',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:70
			},{
			
			        dataIndex : 'clerkMonthDayPoingTime',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:90
			}]
			
		},{
			text : '电叉司机',
			columns:[{
					dataIndex : 'forkPDAMaxQtyMonth',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:70
			},{
			
			        dataIndex : 'forkMonthDayPoingTime',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:90
			}]
		},{
			text : '所有人员',
			//width:160,
			columns:[{
					dataIndex : 'allPDAMaxQtyMonth',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '台数',
					width:70
					
			},{
			
			        dataIndex : 'allMonthDayPoingTime',
					align : 'center',
					xtype : 'ellipsiscolumn',
					text : '时间',
					width:90
			}]
		}]
	}]
});

//入口函数
Ext.onReady(function() {
			// 创建查询form
			var queryForm = Ext
					.create('Foss.platform.queryPDAOnlineUsing.queryPDAOnlineForm');
			// 创建查询grid
			var queryResult = Ext
					.create('Foss.platform.queryPDAOnlineUsing.queryPDAOnlineUsingGrid');

			platform.queryPDAOnlineUsing.queryPDAOnlineForm=queryForm;
			platform.queryPDAOnlineUsing.queryPDAOnlineUsingGrid=queryResult;
			//员工所属部门找所属经营本部
			platform.queryPDAOnlineUsing.hqCde='';
			platform.queryPDAOnlineUsing.hqName='';
			//员工所属部门找所属外场
			platform.queryPDAOnlineUsing.transferCenterCode='';
			platform.queryPDAOnlineUsing.transferCenterName='';
			
			platform.queryPDAOnlineUsing.isHq=0;//0表示不能找本部 1表示bu能找到本部
			platform.queryPDAOnlineUsing.isTfc=0;//0表示不能找本外场 1表示bu能找到外场
			
			//发请求去找员工的外场或则经营本部
			Ext.Ajax.request({
				url : platform.realPath('queryTransferCenterCode.action'),
				success : function(response) {
					var json = Ext.decode(response.responseText);
					platform.queryPDAOnlineUsing.hqCode=json.queryPDAOnlineUsingVo.hqCode;
					platform.queryPDAOnlineUsing.hqName=json.queryPDAOnlineUsingVo.hqName;
					platform.queryPDAOnlineUsing.transferCenterCode=json.queryPDAOnlineUsingVo.orgCode;
				    platform.queryPDAOnlineUsing.transferCenterName=json.queryPDAOnlineUsingVo.orgName;

					
					if(platform.queryPDAOnlineUsing.hqCode!=null&&platform.queryPDAOnlineUsing.hqCode!='')
					{
						Ext.getCmp('Foss.platform.queryPDAOnlineUsing.hqcode.id').setValue(platform.queryPDAOnlineUsing.hqName);
						Ext.getCmp('Foss.platform.queryPDAOnlineUsing.hqcode.id').setReadOnly(true);
						platform.queryPDAOnlineUsing.isHq=1;
					}
					if(platform.queryPDAOnlineUsing.transferCenterCode!=null&&platform.queryPDAOnlineUsing.transferCenterCode!='')
					{
						Ext.getCmp('Foss.platform.queryPDAOnlineUsing.transferCenterCode.id').setValue(platform.queryPDAOnlineUsing.transferCenterName);
						Ext.getCmp('Foss.platform.queryPDAOnlineUsing.transferCenterCode.id').setReadOnly(true);
						platform.queryPDAOnlineUsing.isTfc=1;
					}
					
				},
				exception : function(response) {
					
				}
			});
			
			
			
			
		Ext.create('Ext.panel.Panel', {
						id : 'T_platform-queryPDAOnlineUsingIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						layout : 'auto',
						items : [queryForm,queryResult],
						renderTo : 'T_platform-queryPDAOnlineUsingIndex-body'
					});
		});


