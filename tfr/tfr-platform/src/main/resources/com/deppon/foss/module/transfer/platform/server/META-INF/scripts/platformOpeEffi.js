/**
*月台操作效率查询界面
*肖红叶
*2015-03-20
*
*/
//全局变量，设置月台操作效率的查询类型，是查询外场整体操作效率还是月台详细操作效率
platform.platformOpeEffi.queryType = null;

//月台操作效率实体model
Ext.define('Foss.platform.platformOpeEffi.Model',{
	extend : 'Ext.data.Model',
	fields : [{//统计日期
		name : 'statisticDate',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d');
			} else {
				return null;
			}
		}
	},{//经营本部编码
		name : 'businessDeptCode',
		type : 'string'
	},{//经营本部名称
		name : 'businessDept',
		type : 'string'
	},{//外场编码
		name : 'outfieldCode',
		type : 'string'
	},{//外场名称
		name : 'outfield',
		type : 'string'
	},{//月台编码
		name : 'platformNumber',
		type : 'string'
	},{//当日装车货量
		name : 'loadAmountByDay',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当日卸车货量
		name : 'downloadAmountByDay',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当日装车有效操作时长
		name : 'loadTimeByDay',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当日卸车有效操作时长
		name : 'downloadTimeByDay',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当日月台操作效率
		name : 'platformOpeEffiByDay',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当月装车货量
		name : 'loadAmountByMonth',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当月卸车货量
		name : 'downloadAmountByMonth',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当月装车有效操作时长
		name : 'loadTimeByMonth',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当月卸车有效操作时长
		name : 'downloadTimeByMonth',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//当月月台操作效率
		name : 'platformOpeEffiByMonth',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	}]
});
//月台操作效率的store
Ext.define('Foss.platform.platformOpeEffi.store',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.platformOpeEffi.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryPlatformOpeEffi.action'),
		reader : {
			type : 'json',
			root : 'platformOpeEffiList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			if(platform.platformOpeEffi.MainTabPanel.getActiveTab().title === '月台操作效率'){
				platform.platformOpeEffi.queryType = "platform";
			}else if(platform.platformOpeEffi.MainTabPanel.getActiveTab().title === '月台操作效率明细'){
				platform.platformOpeEffi.queryType = "platformDetail";
			}
			//查询窗体及查询参数
			var queryForm = null;
			var businessDeptCode = null;
			var outfieldCode = null;
			var statisticDate = null;
			var platformNumber = null;
			//判断查询类型并给查询条件赋值
			if(!Ext.isEmpty(platform.platformOpeEffi.queryType) && platform.platformOpeEffi.queryType === "platform"){
				queryForm = platform.platformOpeEffi.formByPlatform.getForm();
				businessDeptCode = queryForm.findField('queryCondition.businessDeptCode').getValue();
				outfieldCode = queryForm.findField('queryCondition.outfieldCode').getValue();
				statisticDate = queryForm.findField('queryCondition.statisticDate').getValue();
			}
			else if(!Ext.isEmpty(platform.platformOpeEffi.queryType) && platform.platformOpeEffi.queryType === "platformDetail"){
				queryForm = platform.platformOpeEffi.formByPlatformDetail.getForm();
				platformNumber = queryForm.findField('queryCondition.platformNumber').getValue();
				businessDeptCode = queryForm.findField('queryCondition.businessDeptCode').getValue();
				outfieldCode = queryForm.findField('queryCondition.outfieldCode').getValue();
				statisticDate = queryForm.findField('queryCondition.statisticDate').getValue();
			}
			if(queryForm != null){
				Ext.apply(operation,{
					params : {
						'queryCondition.businessDeptCode':businessDeptCode,
						'queryCondition.outfieldCode':outfieldCode,
						'queryCondition.statisticDate':statisticDate,
						'queryCondition.platformNumber':platformNumber,
						'queryCondition.queryType':platform.platformOpeEffi.queryType
					}
				});
			}
		},
		load : function(store,records,successful,eOpts){
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

//月台操作效率查询条件
Ext.define('Foss.platform.platformOpeEffi.formByPlatform', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.businessDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.platformOpeEffi.initDeptInfo(_this,platform.platformOpeEffi.businessDeptCode,platform.platformOpeEffi.outfieldCode,'businessDeptCode');
			}
		}
	},{
		name : 'queryCondition.outfieldCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.platformOpeEffi.initDeptInfo(_this,platform.platformOpeEffi.businessDeptCode,platform.platformOpeEffi.outfieldCode,'outfieldCode');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.statisticDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m-d',
		columnWidth:.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function() {
				//获得创建的查询条件的form
				var form = platform.platformOpeEffi.formByPlatform.getForm();
				//重置查询条件form
				form.reset();
				var businessDeptCode = form.findField('queryCondition.businessDeptCode');
				var outfieldCode = form.findField('queryCondition.outfieldCode');
				if(!Ext.isEmpty(platform.platformOpeEffi.businessDeptCode)){
					businessDeptCode.setCombValue(
						platform.platformOpeEffi.businessDept,		
						platform.platformOpeEffi.businessDeptCode
					)
				}
				if(outfieldCode.readOnly == true){
					outfieldCode.setCombValue(
						platform.platformOpeEffi.outfield,
						platform.platformOpeEffi.outfieldCode
					)
				}
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.platformOpeEffi.formByPlatform.getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				platform.platformOpeEffi.queryType= "platform";
				platform.platformOpeEffi.pagingBarByPlatform.moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

platform.platformOpeEffi.formByPlatform = Ext.create('Foss.platform.platformOpeEffi.formByPlatform');



//月台操作效率查询结果表格
Ext.define('Foss.platform.platformOpeEffi.gridByPlatform', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	enableColumnHide : true,
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	title : '月台操作效率',
	columns : [{
		xtype : 'ellipsiscolumn',
		align : 'center',
		header : '日期',
		sortable: true,
		dataIndex : 'statisticDate',
		width:100
	},{
		xtype : 'ellipsiscolumn',
		header : '经营本部',
		sortable: true,
		align : 'center',
		dataIndex : 'businessDept',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '转运场',
		sortable: true,
		align : 'center',
		dataIndex : 'outfield',
		width:120,
		renderer:function(val,metaData,record){
			if(!Ext.isEmpty(val)){
				//根据外场code查询月台操作效率明细
				return '<a href=javascript:platform.platformOpeEffi.goToPlatformDetail("'+val+'","'
				+record.get('outfieldCode')+'","'+record.get('statisticDate')
				+'","'+record.get('businessDeptCode')+'","'+record.get('businessDept')
				+'")>'+val+'</a>';
			}				
		}
	},{
		xtype : 'ellipsiscolumn',
		header : '当日月台操作效率', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'装车货量（T）',
            align : 'center',
            dataIndex: 'loadAmountByDay',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'装车有效时长（H）',
            align : 'center',
            dataIndex: 'loadTimeByDay',
            width:150
        }, {
        	xtype : 'ellipsiscolumn',
            header:'卸车货量（T）',
            align : 'center',
            dataIndex: 'downloadAmountByDay',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'卸车有效时长（H）',
            align : 'center',
            dataIndex: 'downloadTimeByDay',
            width:150
        },{
        	xtype : 'ellipsiscolumn',
            header:'月台操作效率(T/H)',
            align : 'center',
            dataIndex: 'platformOpeEffiByDay',
            width:150
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '当月月台操作效率', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'装车货量（T）',
            align : 'center',
            dataIndex: 'loadAmountByMonth',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'装车有效时长（H）',
            align : 'center',
            dataIndex: 'loadTimeByMonth',
            width:150
        }, {
        	xtype : 'ellipsiscolumn',
            header:'卸车货量（T）',
            align : 'center',
            dataIndex: 'downloadAmountByMonth',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'卸车有效时长（H）',
            align : 'center',
            dataIndex: 'downloadTimeByMonth',
            width:150
        },{
        	xtype : 'ellipsiscolumn',
            header:'月台操作效率(T/H)',
            align : 'center',
            dataIndex: 'platformOpeEffiByMonth',
            width:150
        }]
    }],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.platformOpeEffi.store');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				if (me.store.data.length === 0) {
					Ext.ux.Toast.msg('提示', '列表中无可供导出的数据！', 'error', 2000);
					return;
				}
				platform.platformOpeEffi.queryType = "platform";
				var platformNumber = null;
				var queryForm = platform.platformOpeEffi.formByPlatform.getForm();
				var businessDeptCode = queryForm.findField('queryCondition.businessDeptCode').getValue();
				var outfieldCode = queryForm.findField('queryCondition.outfieldCode').getValue();
				var statisticDate = queryForm.findField('queryCondition.statisticDate').getValue();
				var params = {
						'queryCondition.businessDeptCode':businessDeptCode,
						'queryCondition.outfieldCode':outfieldCode,
						'queryCondition.statisticDate':statisticDate,
						'queryCondition.platformNumber':platformNumber,
						'queryCondition.queryType':platform.platformOpeEffi.queryType
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportPlatformOpeEffiData.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败', result.message);
					}
				});
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 100,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50], ['100', 100]]
			})
		});
		platform.platformOpeEffi.pagingBarByPlatform = me.bbar;
		me.callParent([cfg]);
	}
});

platform.platformOpeEffi.gridByPlatform =Ext.create('Foss.platform.platformOpeEffi.gridByPlatform') ;

//月台操作效率页签页面
Ext.define('Foss.platform.platformOpeEffi.PanelByPlatform', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//月台操作效率查询结果表格
		var gridByPlatform = platform.platformOpeEffi.gridByPlatform;
		//月台操作效率查询条件
		var queryFormByPlatform = platform.platformOpeEffi.formByPlatform;
		me.items = [queryFormByPlatform, gridByPlatform];
		me.callParent([cfg]);
	}
})

/*----------月台操作效率明细标签页中内容------------------*/
//月台操作效率明细查询条件
Ext.define('Foss.platform.platformOpeEffi.formByPlatformDetail', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.businessDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.platformOpeEffi.initDeptInfo(_this,platform.platformOpeEffi.businessDeptCode,platform.platformOpeEffi.outfieldCode,'businessDeptCode');
			}
		}
	},{
		name : 'queryCondition.outfieldCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.platformOpeEffi.initDeptInfo(_this,platform.platformOpeEffi.businessDeptCode,platform.platformOpeEffi.outfieldCode,'outfieldCode');
			},
			select:function(th,records,e){
				//获取月台号
				var platformNumber = th.up('form').getForm().findField('queryCondition.platformNumber');
				th.up('form').getForm().findField('queryCondition.platformNumber').reset();
				platformNumber.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					//设置外场参数
					searchParams['platformVo.platformEntity.organizationCode'] = th.getValue();
					Ext.apply(operation, {
						params : searchParams 
					});
				}); 
				platformNumber.store.load();
			},
			change:function(th,newValue,oldValue){
				if(Ext.isEmpty(th.getValue())){
					th.setValue("");
				}
				if(Ext.isEmpty(newValue)){
					//获取月台号
					var platformNumber = th.up('form').getForm().findField('queryCondition.platformNumber');
					th.up('form').getForm().findField('queryCondition.platformNumber').reset();
					platformNumber.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						//设置外场参数
						searchParams['platformVo.platformEntity.organizationCode'] = th.getValue();
						Ext.apply(operation, {
							params : searchParams 
						});
					});
					platformNumber.store.load();
				}
			}
		}
	},{
		xtype : 'commonplatformselector',
		fieldLabel : '月台号',
		name : 'queryCondition.platformNumber',
		orgCode : platform.platformOpeEffi.outfieldCode,
		displayField : 'platformCode',// 显示名称
		valueField : 'platformCode',// 值
		columnWidth : .25
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.statisticDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m-d',
		columnWidth:.25
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function() {
				//获得创建的查询条件的form
				var form = platform.platformOpeEffi.formByPlatformDetail.getForm();
				//重置查询条件form
				form.reset();
				var outfieldCode = form.findField('queryCondition.outfieldCode');
				var businessCode = form.findField('queryCondition.businessDeptCode');
				if(outfieldCode.readOnly == true){
					outfieldCode.setCombValue(
						platform.platformOpeEffi.outfield,
						platform.platformOpeEffi.outfieldCode
					)
				}
				if(businessCode.readOnly == true){
					businessCode.setCombValue(
						platform.platformOpeEffi.businessDept,
						platform.platformOpeEffi.businessDeptCode
					)
				}
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.platformOpeEffi.formByPlatformDetail.getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				platform.platformOpeEffi.queryType = "platformDetail";
				platform.platformOpeEffi.pagingBarByPlatformDetail.moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

platform.platformOpeEffi.formByPlatformDetail = Ext.create('Foss.platform.platformOpeEffi.formByPlatformDetail');



//月台操作效率明细查询结果表格
Ext.define('Foss.platform.platformOpeEffi.gridByPlatformDetail', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	enableColumnHide : true,
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	title : '月台操作效率明细',
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '日期',
		align : 'center',
		sortable: true,
		dataIndex : 'statisticDate',
		width:100
	},{
		xtype : 'ellipsiscolumn',
		header : '经营本部',
		sortable: true,
		align : 'center',
		dataIndex : 'businessDept',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '转运场',
		sortable: true,
		align : 'center',
		dataIndex : 'outfield',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '月台号',
		sortable: true,
		align : 'center',
		dataIndex : 'platformNumber',
		width:120
	},{
		xtype : 'ellipsiscolumn',
		header : '当日月台操作效率', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'装车货量（T）',
            align : 'center',
            dataIndex: 'loadAmountByDay',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'装车有效时长（H）',
            align : 'center',
            dataIndex: 'loadTimeByDay',
            width:150
        }, {
        	xtype : 'ellipsiscolumn',
            header:'卸车货量（T）',
            align : 'center',
            dataIndex: 'downloadAmountByDay',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'卸车有效时长（H）',
            align : 'center',
            dataIndex: 'downloadTimeByDay',
            width:150
        },{
        	xtype : 'ellipsiscolumn',
            header:'月台操作效率(T/H)',
            align : 'center',
            dataIndex: 'platformOpeEffiByDay',
            width:150
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '当月月台操作效率', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'装车货量（T）',
            align : 'center',
            dataIndex: 'loadAmountByMonth',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'装车有效时长（H）',
            align : 'center',
            dataIndex: 'loadTimeByMonth',
            width:150
        }, {
        	xtype : 'ellipsiscolumn',
            header:'卸车货量（T）',
            align : 'center',
            dataIndex: 'downloadAmountByMonth',
            width:120
        },{
        	xtype : 'ellipsiscolumn',
            header:'卸车有效时长（H）',
            align : 'center',
            dataIndex: 'downloadTimeByMonth',
            width:150
        },{
        	xtype : 'ellipsiscolumn',
            header:'月台操作效率(T/H)',
            align : 'center',
            dataIndex: 'platformOpeEffiByMonth',
            width:150
        }]
    }],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.platformOpeEffi.store');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 50,
			maximumSize : 100,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['25', 25], ['50', 50], ['100', 100]]
			})
		});
		platform.platformOpeEffi.pagingBarByPlatformDetail = me.bbar;
		me.callParent([cfg]);
	}
});

platform.platformOpeEffi.gridByPlatformDetail =Ext.create('Foss.platform.platformOpeEffi.gridByPlatformDetail') ;

//月台操作效率明细页签页面
Ext.define('Foss.platform.platformOpeEffi.PanelByPlatformDetail', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//月台操作效率查询结果表格
		var gridByPlatformDetail = platform.platformOpeEffi.gridByPlatformDetail;
		//月台操作效率查询条件
		var queryFormByPlatformDetail = platform.platformOpeEffi.formByPlatformDetail;
		me.items = [queryFormByPlatformDetail, gridByPlatformDetail];
		me.callParent([cfg]);
	}
})


/*------------------------------------------------*/

/**
 * 月台操作效率的两个页签panel
 */
Ext.define('Foss.platform.platformOpeEffi.MainTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,
	autoScroll : false,
	frame : false,
	items : [{
		tabConfig : {
			width : 100
		},
		title : '月台操作效率',
//		html:'月台操作效率'
		items : Ext.create('Foss.platform.platformOpeEffi.PanelByPlatform')
	}, {
		tabConfig : {
			width : 130
		},
		title : '月台操作效率明细',
//		html:'月台操作效率明细'
		items : Ext.create('Foss.platform.platformOpeEffi.PanelByPlatformDetail')
	}]
});

//根据查询条件中经营本部与外场的信息设置combobox的readOnly属性
platform.platformOpeEffi.initDeptInfo = function(item,businessDeptCode,outfieldCode,str){
	if(!Ext.isEmpty(outfieldCode)){
		if(str == 'outfieldCode' || str == 'businessDeptCode'){
			item.readOnly = true;
		}
	}else if(Ext.isEmpty(outfieldCode) && !Ext.isEmpty(businessDeptCode)){
		if(str == 'businessDeptCode'){
			item.readOnly = true;
		}
	}
}

//当在月台操作效率页签单击查询结果中的外场名称时，可以跳转到月台操作效率明细页签，显示该外场下各月台的明细信息
platform.platformOpeEffi.goToPlatformDetail = function(outfieldName,outfileCode,statisticDate,businessCode,businessName){
	//跳转到月台操作效率明细页签
	platform.platformOpeEffi.MainTabPanel.setActiveTab(1);
	//获取月台操作效率明细查询条件form
	var queryForm = platform.platformOpeEffi.formByPlatformDetail.getForm();
	//为月台操作效率明细的查询条件form赋值
	queryForm.findField('queryCondition.platformNumber').setValue("");
	//初始化月台号下拉列表
	var platformNumber = queryForm.findField('queryCondition.platformNumber');
	platformNumber.store.addListener('beforeload', function(store, operation, eOpts) {
		var searchParams = operation.params;
		//设置外场参数
		searchParams['platformVo.platformEntity.organizationCode'] = outfileCode;
		Ext.apply(operation, {
			params : searchParams 
		});
	}); 
	platformNumber.store.load();
	queryForm.findField('queryCondition.outfieldCode').setCombValue(
		outfieldName,
		outfileCode
	);
	queryForm.findField('queryCondition.businessDeptCode').setCombValue(
		businessName,
		businessCode
	);
	queryForm.findField('queryCondition.statisticDate').setValue(statisticDate);
	//显示查询结果
	platform.platformOpeEffi.queryType = "platformDetail"
	platform.platformOpeEffi.pagingBarByPlatformDetail.moveFirst();
}
/** -----------------------------------------------页面显示view--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	platform.platformOpeEffi.MainTabPanel = Ext.create('Foss.platform.platformOpeEffi.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-platformOpeEffi_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		items : [platform.platformOpeEffi.MainTabPanel],
		renderTo : 'T_platform-platformOpeEffi-body'
	});
	
	//初始化经营本部信息
	if(Ext.isEmpty(platform.platformOpeEffi.outfieldCode) && !Ext.isEmpty(platform.platformOpeEffi.businessDeptCode)){
		platform.platformOpeEffi.formByPlatform.getForm().findField('queryCondition.businessDeptCode').setCombValue(
			platform.platformOpeEffi.businessDept,		
			platform.platformOpeEffi.businessDeptCode
		);
		platform.platformOpeEffi.formByPlatformDetail.getForm().findField('queryCondition.businessDeptCode').setCombValue(
			platform.platformOpeEffi.businessDept,		
			platform.platformOpeEffi.businessDeptCode
		);
	};
	//初始化外场信息
	if(!Ext.isEmpty(platform.platformOpeEffi.outfieldCode)){
		//月台操作效率页签外场初始化
		platform.platformOpeEffi.formByPlatform.getForm().findField('queryCondition.outfieldCode').setCombValue(
			platform.platformOpeEffi.outfield,
			platform.platformOpeEffi.outfieldCode
		);
		//月台操作效率明细页签外场初始化
		platform.platformOpeEffi.formByPlatformDetail.getForm().findField('queryCondition.outfieldCode').setCombValue(
				platform.platformOpeEffi.outfield,
				platform.platformOpeEffi.outfieldCode
		);
	}
});