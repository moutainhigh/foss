/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
pickup.cashDiffReport.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);//天数增加
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期  23:59:59
 */
pickup.cashDiffReport.getTargetDateEnd = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);//天数增加
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};

//删除左右两端的空格
pickup.cashDiffReport.trim =	function(str){
	　　return str.replace(/(^\s*)|(\s*$)/g, "");
};


//信息
Ext.define('Foss.QueryCashDiffInfo.InfoGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	id:'Foss_QueryCashDiffReportInfo_QueryCashDiffReportInfoGrid_Id',//id
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	autoScroll : true,
	//定义表格的标题
	title:pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.CashDiffReportInfo'),//接送货现金差异报表
	collapsible: true, //可以展开
	animCollapse: true,
	emptyText: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.emptyText'),//查询结果为空
	store: null,
	
	
	viewConfig :{
		//显示重复样式，不用隔行显示
	 	stripeRows: false,

		enableTextSelection: true,
		getRowClass:function(record, rowIndex, p, ds){
			
			var showamount = new Number(record.get('handwriteMinusPayAmount'));
			
			//运单手写现付金额
			if(showamount<0 ) {
				
				//当鼠标移至此行时提醒此单号少收钱
				//p.tooltip = "此单号少收钱";
				return 'failProcess_mark_color';//变成红色
		 	//短信如果成功	
		 	}else if(showamount==0) {
		 		//当鼠标移至此行时提醒此单号收钱正确
		 		//p.tooltip = "此单号收钱正确";
		 		return 'passProcess_mark_color';//变成绿色
		 	}else if(showamount>0) {
		 		//p.tooltip = "此单号多收钱";
		 		//此单号所在的行显示为红黄色，当鼠标移至此行时提醒此单号多收钱
		 		return 'failProcessYellow_mark_color';//变成黄
		 	}
		},
		listeners: {
			render: function(view) {//每行显示tooltip
			    view.tip = Ext.create('Ext.tip.ToolTip', {
					target: view.el,
					delegate: view.itemSelector,
					trackMouse: true,
					renderTo: Ext.getBody(),
					listeners: {
						beforeshow: function (tip) {
							
							var sumno =  new Number(view.getRecord(tip.triggerElement).get('handwriteMinusPayAmount'));
							
							var tooltip = '';
							if(sumno>0){//此单号多收钱
								tooltip=pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.waybillNoMoreMoney');//此单号多收钱
							}else if (sumno==0){//此单号收钱正确
								tooltip=pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.waybillNoCorrectMoney');//此单号收钱正确
							}else if(sumno<0){//此单号少收钱
								tooltip=pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.waybillNoLessMoney');//此单号少收钱
							}
							
							if(tooltip){//update tooltip
								tip.update(tooltip);
							} else {
								 tip.on('show', function(){
									 Ext.defer(tip.hide, 10, tip);
								 }, tip, {single: true});
							}
						}
					}
				});
			}
		}


	},

	
	//定义表格列信息
	columns: [
	    {
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.waybillNo'), //运单号
			//关联model中的字段名
			dataIndex: 'waybillNo',
			width:80				
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.driverNoAndName'), //接货司机姓名/工号
			//关联model中的字段名
			dataIndex: 'driverNoAndName',
			width:130				
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.driverDepartmentName'), //司机所属部门
			//关联model中的字段名
			dataIndex: 'driverDepartmentName',
			width:100,
			xtype: 'ellipsiscolumn'				
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.vehicleNo'), //车牌号
			//关联model中的字段名
			dataIndex: 'vehicleNo',
			width:80				
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.pickupTime'), //接货时间
			//关联model中的字段名
			dataIndex: 'pickupTime', 
			width:130,
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.receiveOrgName'), //收货部门
			//关联model中的字段名
			dataIndex: 'receiveOrgName', 
			width:100,
			xtype: 'ellipsiscolumn'
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.pdaInputAmount'), //PDA录入收款金额
			//关联model中的字段名
			dataIndex: 'pdaInputAmount', 
			width:130
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.waybillHandwriteAmount'), //运单手写现付金额
			//关联model中的字段名
			dataIndex: 'waybillHandwriteAmount', 
			width:130
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.waybillPayAmount'), //开单应收现付金额
			//关联model中的字段名
			dataIndex: 'waybillPayAmount', 
			width:130
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.pdaMinusHandwriteAmount'), //PDA录入收款金额-运单手写现付金额
			//关联model中的字段名
			dataIndex: 'pdaMinusHandwriteAmount', 
			width:235
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.handwriteMinusPayAmount'), //运单手写现付金额-开单应收现付金额
			//关联model中的字段名
			dataIndex: 'handwriteMinusPayAmount', 
			width:235
		},{
			//字段标题
			header: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.createUserName'), //制单人
			//关联model中的字段名
			dataIndex: 'createUserName', 
			width:100
	}],
	
	

	
	constructor: function(config){//初始化分页组件
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.QueryCashDiffInfo.Store.QueryCashDiffInfoStore');
	
		//自定义分页控件
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin',
			displayInfo: true
		});
		pickup.cashDiffReport.pagingBar = me.bbar;
		
		me.callParent([cfg]);
		
		/**
		me.on('itemmouseup', function(view, record, item, index, e) {
			Ext.fly(item).select('.x-grid-cell-inner').set({'data-qtip': record.get('waybillNo')});
		});*/
		
	}
});



//定义查询模型
Ext.define('Foss.QueryCashDiff.Model.QueryCashDiffModel', {
	extend: 'Ext.data.Model',
	fields: [
        {name: 'extid', type: 'string'},//额外的用于生成的EXT使用的列
	    { name: 'id',type:'id' },//id
	    { name: 'waybillNo',type:'string' }, //运单号
	    { name: 'driverNoAndName',type:'string' }, //接货司机姓名/工号
	    { name: 'driverDepartmentName',type:'string' }, //接货司部门name
	    { name: 'vehicleNo',type:'string' }, //车牌号
		{ name: 'pickupTime',type:'date',convert: dateConvert }, //返单时间
	    { name: 'receiveOrgName',type:'string' }, //收货部门name
	    { name: 'pdaInputAmount',type:'string' }, //PDA录入收款金额
	    { name: 'waybillHandwriteAmount',type:'string' }, //运单手写现付金额
	    { name: 'waybillPayAmount',type:'string' }, //开单应收现付金额 
	    { name: 'pdaMinusHandwriteAmount',type:'string' }, //PDA录入收款金额-运单手写现付金额
	    { name: 'handwriteMinusPayAmount',type:'string' }, //运单手写现付金额-开单应收现付金额
		{ name: 'createUserName',type:'string'}//制单人
		]
});



//创建一个查询货量store
Ext.define('Foss.QueryCashDiffInfo.Store.QueryCashDiffInfoStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.QueryCashDiff.Model.QueryCashDiffModel',
	//默认每页数据大小
	pageSize:10,
	//是否自动查询
	autoLoad: false,
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
		idProperty : 'extid',//以上生成的主键用在名叫“extid”的列//定义字段
		url: pickup.realPath('queryCashDiffReport.action'),//老的url格式：
		//'../pickup/queryCashDiffReport.action',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.rtCashDiffReportDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
			var queryParams = pickup.cashDiffReport.queryPanel.getValues();//获得表单数据
			
/*			queryParams.startTime = Ext.Date.parse(queryParams.startTime, "Y-m-d H:i:s");
			queryParams.endTime = Ext.Date.parse(queryParams.endTime, "Y-m-d H:i:s");
*/			
			//查询条件是否合法（非空等相关约束）
			var form = pickup.cashDiffReport.queryPanel.getForm();
			var startTime = form.findField('startTime').getValue();//得到开始时间
			var endTime = form.findField('endTime').getValue(); //得到结束时间
			//选择时间 null
			if(endTime == '' || startTime == ''){
				Ext.ux.Toast.msg(pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.tip'), pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.chooseTime'), 'error', 1000);//选择时间 null -选择时间
				return false;
			}
			if(startTime!='' && endTime!=''){
				var startDate = new Date(Date.parse(startTime.replace(/-/g,   "/")));  //解析开始时间
				var endDate = new  Date(Date.parse(endTime.replace(/-/g,   "/")));   //解析结束时间
				
				if(startDate < pickup.cashDiffReport.getTargetDate(endDate, -30)){
					//结束时间要在开始时间30天以内
					Ext.ux.Toast.msg(pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.warn'), pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.the.date.range.cannot.be.more.than.30.days'), 'error', 2000);//结束时间要在开始时间30天以内
					return false;
				}					
			}
				
			Ext.apply(operation, {
				params : {					
					'vo.cashDiffReportDto.pickupTimeBegin': queryParams.startTime,//接货时间begin
					'vo.cashDiffReportDto.pickupTimeEnd': queryParams.endTime,//接货时间End
					'vo.cashDiffReportDto.createUserCode': queryParams.createUserCode,//制单人
					'vo.cashDiffReportDto.vehicleNo': queryParams.vehicleNo,//车牌号
					'vo.cashDiffReportDto.receiveOrgCode': queryParams.receiveOrgCode,//收货部门
					'vo.cashDiffReportDto.driverNo': queryParams.driverNo,//接货司机工号 
					'vo.cashDiffReportDto.driverDepartmentCode': queryParams.driverDepartmentCode//司机所属部门
				}
			});	
			
			
		},
		load: function( store, records, successful, eOpts ){
			
			//统计列表信息
			if(Ext.isEmpty(records)){//查询结果为空
				Ext.ux.Toast.msg(pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.tip'), pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.emptyText'));
			}
		}
	}
});



//查询
Ext.define('Foss.QueryCashDiffInfo.Form.QueryCashDiffForm',{
	extend:'Ext.form.Panel',
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',//默认textfield输入类型
	layout: 'column',
	items: [{
		xtype: 'rangeDateField',//接货时间
		fieldId: 'FOSS_Time_cashDiffReport_Id_start',
		fieldLabel: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.pickupTime'),//字段标题 -- 接货时间
		allowFromBlank : false,
		allowToBlank : false,
		editable:false,
		dateType: 'datetimefield_date97',
		fromName: 'startTime',
		toName: 'endTime',
		fromValue: Ext.Date.format(pickup.cashDiffReport.getTargetDate(new Date(),0),'Y-m-d H:i:s'),
		toValue:   Ext.Date.format(pickup.cashDiffReport.getTargetDateEnd(new Date(),0),'Y-m-d H:i:s'),
		//showDefaultFlag: false,
		format: 'Y-m-d H:i:s' ,
		columnWidth: 0.5
	},{
		xtype : 'commonemployeeselector', 
		id : 'createUserCode',//id
		name:'createUserCode',//运单号
		fieldLabel: pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.createUserName'),//字段标题 -- 制单人
		columnWidth: 0.25
	},{
		xtype : 'commonleasedvehicleselector',
		id : 'vehicleNo',//id
		name:'vehicleNo',  //返单类型
		fieldLabel:pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.vehicleNo'),//字段标题--车牌号
		columnWidth: 0.25
		
	},{
		xtype : 'dynamicorgcombselector', //出发部门  这里应该使用公共选择器
		columnWidth : 0.25,
		name : 'receiveOrgCode',
		id : 'receiveOrgCode',//关联的名称
		fieldLabel : pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.receiveOrgName')//字段标题-收货部门
	},{
		xtype : 'commonemployeeselector', 
		id : 'driverNo',//id
		name:'driverNo',//
		fieldLabel:pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.driverNoInfo'),//字段标题-接货司机信息
		columnWidth: 0.25
	},{
		xtype : 'dynamicorgcombselector', //司机所属部门 这里应该使用公共选择器
		columnWidth : 0.25,
		name : 'driverDepartmentCode',
		id : 'driverDepartmentCode',//关联的名称
		fieldLabel : pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.driverDepartmentCode')//字段标题-司机所属部门
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			xtype: 'container',
			border : false,
			columnWidth:.76,
			html: '&nbsp;'
		},{
			text:pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.reset'),//重置
			columnWidth:.08,
			handler:function(){
				pickup.cashDiffReport.queryPanel.getForm().reset();//重置表单内容
				var form = pickup.cashDiffReport.queryPanel.getForm();
				form.findField('startTime').setValue(Ext.Date.format(pickup.cashDiffReport.getTargetDate(new Date(),0),'Y-m-d H:i:s'));
				form.findField('endTime').setValue(Ext.Date.format(pickup.cashDiffReport.getTargetDateEnd(new Date(),0),'Y-m-d H:i:s'));
				
			}
		},{
			text:pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.search'),//查询
			cls:'yellow_button',
			disabled:!pickup.cashDiffReport.isPermission('cashdiffreportindex/cashdiffreportindexquerybutton'),
			hidden:!pickup.cashDiffReport.isPermission('cashdiffreportindex/cashdiffreportindexquerybutton'),
			columnWidth:.08,
			handler:function(){
					pickup.cashDiffReport.pagingBar.moveFirst();
			}
		},{
			text:pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.export'),//导出
			disabled:!pickup.cashDiffReport.isPermission('cashdiffreportindex/cashdiffreportindexexportbutton'),
			hidden:!pickup.cashDiffReport.isPermission('cashdiffreportindex/cashdiffreportindexexportbutton'),
			columnWidth:.08,
			handler : function(){
					//查询条件是否合法（非空等相关约束）
					var form = pickup.cashDiffReport.queryPanel.getForm();
					var startTime = form.findField('startTime').getValue();//得到开始时间
					var endTime = form.findField('endTime').getValue(); //得到结束时间
					//选择时间 null
					if(endTime == '' 
							|| startTime == ''){
						Ext.ux.Toast.msg(pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.tip'), pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.chooseTime'), 'error', 1000);//选择时间 null -选择时间
						return;
					}
					if(startTime!='' && endTime!=''){
						var startDate = new Date(Date.parse(startTime.replace(/-/g,   "/")));  //解析开始时间
						var endDate = new  Date(Date.parse(endTime.replace(/-/g,   "/")));   //解析结束时间
						
						if(startDate < pickup.cashDiffReport.getTargetDate(endDate, -30)){
							
							//结束时间要在开始时间30天以内
							Ext.ux.Toast.msg(pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.warn'), pickup.cashDiffReport.i18n('pkp.pickup.cashDiffReport.the.date.range.cannot.be.more.than.30.days'), 'error', 2000);//结束时间要在开始时间30天以内
							
							return;
						}					
					}
					
					
					var queryParams = form.getValues();
					if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
					}
					
					
					Ext.Ajax.request({
						url:pickup.realPath('exportCashDiffReportExcel.action'),
						form: Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : {					
							'vo.cashDiffReportDto.pickupTimeBegin': queryParams.startTime,//接货时间begin
							'vo.cashDiffReportDto.pickupTimeEnd': queryParams.endTime,//接货时间End
							'vo.cashDiffReportDto.createUserCode': queryParams.createUserCode,//制单人
							'vo.cashDiffReportDto.vehicleNo': queryParams.vehicleNo,//车牌号
							'vo.cashDiffReportDto.receiveOrgCode': queryParams.receiveOrgCode,//收货部门
							'vo.cashDiffReportDto.driverNo': queryParams.driverNo,//接货司机工号 
							'vo.cashDiffReportDto.driverDepartmentCode': queryParams.driverDepartmentCode//司机所属部门
					
						},
						isUpload: true

					});
			}
			
		}]
	}]
});

//查询结果
pickup.cashDiffReport.queryResult = Ext.create('Foss.QueryCashDiffInfo.InfoGrid');

//查询panel
pickup.cashDiffReport.queryPanel =  Ext.create('Foss.QueryCashDiffInfo.Form.QueryCashDiffForm');




/**
 * extjs初始化
 */
Ext.onReady(function() {
	Ext.QuickTips.init();//初始化
	
	Ext.create('Ext.panel.Panel',{
		
		id: 'T_pickup-cashDiffReportIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [pickup.cashDiffReport.queryPanel,//查询条件panel
		        pickup.cashDiffReport.queryResult],//查询结果panel
		renderTo: 'T_pickup-cashDiffReportIndex-body'
	});
});

