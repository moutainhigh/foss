
//查询时间初始话方法
backupquery.airflightsquery.getairflightqueryTime = function(isBegin){
	var nowDate=new Date();
	if(isBegin){
		nowDate.setHours(0);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
	}else{
		nowDate.setHours(23);
		nowDate.setSeconds(59);
		nowDate.setMinutes(59);
	}
	return nowDate;
}
//查询空运航班FORM
Ext.define('Foss.airfreight.airflightsquery.QueryForm',{
	extend :'Ext.form.Panel',
	title : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.queryForm.title'),//'查询空运航班'
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.queryForm.departDept'),//'出发部门'
		name : 'departOrgCode',
		xtype : 'dynamicorgcombselector',
		types : 'ORG,CPPX,CPLD',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y'
	},{
		fieldLabel : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.queryForm.arriveDept'),//'到达部门'
		name : 'destOrgCode',
		xtype : 'commonAiragentAndDeptselector'
		
	},{
		xtype : 'rangeDateField',
		fieldLabel : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.queryForm.queryTime'),//'查询时间'
		columnWidth : 1/2,
		fieldId : 'Foss_stock_QueryForm_HandOverTime_ID',
		dateType: 'datetimefield_date97',
		dateRange : 31,
		fromName : 'beginFligtTime',
		fromValue : Ext.Date.format(backupquery.airflightsquery.getairflightqueryTime(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(backupquery.airflightsquery.getairflightqueryTime(false), 'Y-m-d H:i:s'),
		toName : 'endFligtTime',
		allowBlank : false,
		disallowBlank : true
	},{
		fieldLabel : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.queryForm.beingSingleNo'),//'正单号'
		name : 'beingSingleNo'
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.queryForm.restButton'),//'重置'
			handler : function() {
				this.up('form').getForm().reset();
				//重新初始化交接时间
				this.up('form').getForm().findField('beginFligtTime').setValue(Ext.Date.format(backupquery.airflightsquery.getairflightqueryTime(true), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endFligtTime').setValue(Ext.Date.format(backupquery.airflightsquery.getairflightqueryTime(false), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled : !backupquery.airflightsquery.isPermission('airfreight/queryairflightsListButton'),
			hidden : !backupquery.airflightsquery.isPermission('airfreight/queryairflightsListButton'),
			text : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.queryForm.queryButton'), //'查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					backupquery.airflightsquery.pagingBar.moveFirst();
				}
			}
		} ]
	
	}]
});
//查询航班model
Ext.define('Foss.airfreight.airflightsquery.QueryAirFlightsModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'departDepet',  		type: 'string'},
        {name: 'departDepetCode',   type: 'string'},
        {name: 'arriveDept', 		type: 'string'},
        {name: 'arriveDeptCode', 	type: 'string'},
        {name: 'departureTime',		type:'date',	convert : dateConvert}, 
        {name: 'arrivedTime',		type:'date',	convert : dateConvert},
        {name: 'totalWeight',		type:'int'},
        {name: 'totalVolum',		type:'int'},
        {name: 'totalGoods',		type:'int'},
        {name: 'flightNo', 			type: 'string'}]
});
//查询航班store
Ext.define('Foss.airfreight.airflightsquery.QueryAirFlightsStore',{
	extend : 'Ext.data.Store',
	pageSize : 20,
	model : 'Foss.airfreight.airflightsquery.QueryAirFlightsModel',
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : backupquery.realPath('queryAirFlights.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'airFlightVo.airFlightList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = backupquery.airflightsquery.QueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'airFlightVo.airFlightQueryDto.departOrgCode' : queryParams.departOrgCode,
					'airFlightVo.airFlightQueryDto.destOrgCode' :queryParams.destOrgCode,
					'airFlightVo.airFlightQueryDto.beginFligtTime' : queryParams.beginFligtTime,
					'airFlightVo.airFlightQueryDto.endFligtTime' : queryParams.endFligtTime,
					'airFlightVo.airFlightQueryDto.beingSingleNo' : queryParams.beingSingleNo
				}
			});	
		}
	}
   
});
//查询空运航班信息列表
Ext.define('Foss.airfreight.airflightsquery.QueryAirFlightsGrid',{
	extend : 'Ext.grid.Panel',
	title: 'Simpsons',
    frame : true,
	columnLines: true,
	title : backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.title'),//'空运航班信息列表'
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	emptyText :backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.emptyText'),//'查询结果为空'
    columns: [
        { 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.flightNo'), //'航班号' 
        	dataIndex: 'flightNo',
        	align : 'center',
        	width : 120
        },{ 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.departDepet'),//'出发部门'
        	dataIndex: 'departDepet',
        	align : 'center',
        	width : 150
        },{ 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.arriveDept'),//'到达部门' 
        	dataIndex: 'arriveDept',
        	align : 'center',
        	width : 150
        },{ 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.departureTime'),//'出发时间' 
        	dataIndex: 'departureTime',
        	align : 'center',
        	xtype : 'datecolumn',
    		format : 'Y-m-d H:i:s',
        	width : 140
        },{ 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.arrivedTime'),//'到达时间' 
        	dataIndex: 'arrivedTime',
        	align : 'center',
        	xtype : 'datecolumn',
    		format : 'Y-m-d H:i:s',
        	width : 140
        },{ 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.totalWeight'),//'总重量' 
        	dataIndex: 'totalWeight',
        	align : 'center',
        	width : 85
        },{ 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.totalVolum'),//'总体积'
        	dataIndex: 'totalVolum',
        	align : 'center',
        	width : 85
        },{ 
        	text: backupquery.airflightsquery.i18n('foss.airfreight.airflightsquery.resultGrid.totalGoods'),//'总票数'
        	dataIndex: 'totalGoods',
        	align : 'center',
        	width : 85
        }],
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		handOverBillNos = new Array();
		me.store = Ext.create('Foss.airfreight.airflightsquery.QueryAirFlightsStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
	backupquery.airflightsquery.pagingBar = me.bbar;
	me.callParent([cfg]);
    }
});
backupquery.airflightsquery.QueryAirFlightsGrid = Ext.create('Foss.airfreight.airflightsquery.QueryAirFlightsGrid');
backupquery.airflightsquery.QueryForm =Ext.create('Foss.airfreight.airflightsquery.QueryForm'); 
//渲染录入航空正单界面
Ext.onReady(function(){
	Ext.QuickTips.init();
	//主页面框架
	Ext.create('Ext.panel.Panel',{
		id:'T_backupquery-airflightsquery_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout : 'auto',
		items : [backupquery.airflightsquery.QueryForm,backupquery.airflightsquery.QueryAirFlightsGrid],
		renderTo: 'T_backupquery-airflightsquery-body'
	});

})