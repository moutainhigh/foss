
//点击查询结果列表
baseinfo.queryCourierSchedule.queryGirdWhenClicked =function(partCodes,fieldName,expressSmallZoneCode){
	if(partCodes.length==0||Ext.isEmpty(fieldName)||Ext.isEmpty(expressSmallZoneCode)){
		return;
	}
	var courierScheduleGrid =Ext.getCmp('T_baseinfo-queryCourierSchedule_content').getCourierScheduleGrid();
	//得到当前表头,获取当前天日期
	var day = Ext.String.leftPad(fieldName.substring(4,fieldName.length), 2, '0');
	//得到原始年月
	var  ymd =baseinfo.queryCourierSchedule.ymd;
	//根据原始的日期和选择的列动态解析当前编辑的排班日期
	var currentDate=ymd.substring(0,7)+'-'+day +' 00:00:00';
	//转换成日期格式
	var schedulingDate =Ext.Date.parse(currentDate,'Y-m-d H:i:s');
	var store =courierScheduleGrid.getStore();
	var searchParams ={
			//'vo.courierScheduleEntity.expressPartCode':partCodes,  由于现在查询排班室查询多点部排班的，所以查询时只需要小区和日期即可
			'vo.courierScheduleEntity.expressSmallZoneCode':expressSmallZoneCode,
			'vo.courierScheduleEntity.schedulingDate':schedulingDate,
			'vo.courierScheduleEntity.planType':'WORK'
		}
	Ext.apply(store.proxy.extraParams,searchParams);
	//加载数据
	store.load();
}


// 报表式查看列表
Ext.define('Foss.baseinfo.queryCourierSchedule.CourierSchedulingTask', {
	extend: 'Ext.data.Model',
	// 定义字段
	fields: [
			{name: 'expressSmallZoneCode',type:'string'},//小区编码
			{name: 'expressSmallZoneName',type:'string'},//小区名称
			{name: 'expressPartCode',type:'string'},//所属快递点部门
			{name: 'schedulingDate',type:'date'},//排班日期
			{name: 'yearMonth',type:'string'},//排班年月
			{name: 'date1',type:'string'},//一月的排班数据
			{name: 'date2',type:'string'},
			{name: 'date3',type:'string'},
			{name: 'date4',type:'string'},
			{name: 'date5',type:'string'},
			{name: 'date6',type:'string'},
			{name: 'date7',type:'string'},
			{name: 'date8',type:'string'},
			{name: 'date9',type:'string'},
			{name: 'date10',type:'string'},
			{name: 'date11',type:'string'},
			{name: 'date12',type:'string'},
			{name: 'date13',type:'string'},
			{name: 'date14',type:'string'},
			{name: 'date15',type:'string'},
			{name: 'date16',type:'string'},
			{name: 'date17',type:'string'},
			{name: 'date18',type:'string'},
			{name: 'date19',type:'string'},
			{name: 'date20',type:'string'},
			{name: 'date21',type:'string'},
			{name: 'date22',type:'string'},
			{name: 'date23',type:'string'},
			{name: 'date24',type:'string'},
			{name: 'date25',type:'string'},
			{name: 'date26',type:'string'},
			{name: 'date27',type:'string'},
			{name: 'date28',type:'string'},
			{name: 'date29',type:'string'},
			{name: 'date30',type:'string'},
			{name: 'date31',type:'string'}
	        ]
});
/**
 * 查询结果列表Model
 */
Ext.define('Foss.baseinfo.queryCourierSchedule.CourierScheduleModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',
		type:'string'
	},{
		name:'schedulingDate',//排班日期
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	},{
		name:'expressSmallZoneCode',//收派小区编码
		type:'string'
	},{
		name:'expressSmallZoneName',//收派小区名称
		type:'string'
	},{
		name:'startFloor',//起始楼层
		type:'number'
	},{
		name:'endFloor',//结束楼层
		type:'number'
	},{
		name:'courierCode',//快递员编码
		type:'string'
	},{
		name:'courierName',//快递员名称
		type:'string'
	},{
		name:'courierNature',//快递员属性
		type:'string'
	},{
		name:'planType',//工作类别
		type:'string'
	},{
		name: 'vehicleNo',// 车牌号
		type:'string'
	},{
		name: 'vehicleLenghtCode',// 车型
		type: 'string'
	},{
		name:'courierPhone',//快递员电话
		type:'string'
	},{
		name:'expressPartCode',//所属快递点部
		type:'string'
	},{
		name:'expressPartName',//所属快递点部名称
		type:'string'
	},{
		name:'yearMonth',//排班年月
		type:'string'
	}]
});
/**
 * 查询结果列表Store
 */
Ext.define('Foss.baseinfo.queryCourierSchedule.CourierScheduleStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.queryCourierSchedule.CourierScheduleModel',
	pageSize:15,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryCourierScheduleListByOther.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.courierScheduleList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
/**
 * 报表查询列表Store
 */
Ext.define('Foss.baseinfo.queryCourierSchedule.CourierSchedulingTaskStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.queryCourierSchedule.CourierSchedulingTask',
	pageSize:15,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryCourierScheduleReportList.action'),// 查询的url
		reader : {
			type : 'json',
			root : 'vo.reportEntityList',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
					//多部门排班，传递的参数将不再是 一个部门了，
					'vo.courierScheduleEntity.orgCodeList':baseinfo.queryCourierSchedule.expressPartCodes,
					'vo.courierScheduleEntity.yearMonth':baseinfo.queryCourierSchedule.ymd,
					'vo.courierScheduleEntity.planType':'WORK'
					}
				});
		}
	}
});
/**
 * 排班报表
 */
Ext.define('Foss.baseinfo.queryCourierSchedule.ReportGird', {
	extend: 'Ext.grid.Panel',
	frame:true,
	title:'',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	autoScroll:true,
	columns:null,	
	getColumn:function(){
		return this.columns;
	},
	viewConfig: {
	    stripeRows: false
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.columns=config.columns;
		me.store=Ext.create('Foss.baseinfo.queryCourierSchedule.CourierSchedulingTaskStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			pageSize:15,
			plugins: 'pagesizeplugin'
		});
		baseinfo.queryCourierSchedule.pagebar=me.bbar ;
		me.callParent([cfg]);
	}
});
/**
 * 
 */
Ext.define('Foss.baseinfo.queryCourierSchedule.CourierScheduleGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.courierSchedule.i18n('foss.baseinfo.queryGrid'),//'排班查询结果列表',
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.courierSchedule.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.columns =[{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.scheduleDate'),//排班日期
			dataIndex:'schedulingDate',
			xtype: 'datecolumn',   
			format:'Y-m-d'
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.expressSmallZone'),//收派小区
			dataIndex:'expressSmallZoneName',
			width:140
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.startFloor'),//起始楼层
			dataIndex:'startFloor',
			renderer:function(value){
			 if(value==0){
			 	return '';
			 }else{
			 	return value;
			 }
			}
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.endFloor'),//结束楼层
			dataIndex:'endFloor',
			renderer:function(value){
			 if(value==0){
			 	return '';
			 }else{
			 	return value;
			 }
			}
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.courier'),//快递员
			dataIndex:'courierName'
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.courierNature'),//快递员属性
			dataIndex:'courierNature',
			renderer:function(val){
			 if(Ext.isEmpty(val)){
			  	return;
			 }
			 if(val=='FIXED'){
			    return '主责';
			 }else if(val =='MOTORIZED'){
			 	return '替班';
			 }else{
			 	return val;
			 }
			}
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.planType'),//工作类别
			dataIndex:'planType',
			renderer:function(val){
				if(Ext.isEmpty(val)){
			  		return;
				 }
				 if(val=='WORK'){
				    return '出车';
				 }else if(val =='REST'){
				 	return '休息';
				 }else if(val =='DUTY'){
				 	return '值班';
				 }else if(val =='LEAVE'){
				 	return '离岗';
				 }else if(val =='TRAINING'){
				 	return '培训';
				 }
			}
		},{
			text:baseinfo.queryCourierSchedule.i18n('foss.baseinfo.vehicle.vehicleNo'),//车牌
			dataIndex:'vehicleNo'
		},{
			text:baseinfo.queryCourierSchedule.i18n('foss.baseinfo.LTRmodles'),//车型foss.baseinfo.LTRmodles
			dataIndex:'vehicleLenghtCode'
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.courierPhone'),//快递员电话
			dataIndex:'courierPhone'
		},{
			text:baseinfo.queryCourierSchedule.i18n('baseinfo.courierSchedule.expressPart'),//快递员所属快递点部
			dataIndex:'expressPartName',
			width:140
		}];
		me.store=Ext.create('Foss.baseinfo.queryCourierSchedule.CourierScheduleStore');
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		/*me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});*/
		//me.bbar =me.getPagingToolbar();
		me.callParent([cfg]);
	}
});
Ext.onReady(function(){
	//校验非空
	if(!Ext.isEmpty(baseinfo.courierSchedule.expressPartNames)){
		 baseinfo.queryCourierSchedule.expressPartName = baseinfo.courierSchedule.expressPartNames;
	}
	//校验传递进来的参数
	if(!Ext.isEmpty(newExpressPartCodes)){
		//将参数由字符串转换成数据
		baseinfo.queryCourierSchedule.expressPartCodes = newExpressPartCodes.split(',');
	}
	//本月的头一天
	var schedulingDate =baseinfo.queryCourierSchedule.ymd+'-01';
	//动态创建表头
	var url=baseinfo.realPath('queryGridHeader.action');	
	var queryParams ={'vo.ymd':schedulingDate};
	Ext.Ajax.request({
		url:url,
		params:queryParams,
		success:function(response){
		 var json =Ext.decode(response.responseText);
		 var fields =json.vo.gridHeaderFields;
		 //动态构建列
			if(fields!=null){
				var gridHeaderFields="[";
				//设置小区列
				gridHeaderFields+="{text : '收派小区',locked :true,sortable:false,menuDisabled:true,columnWidth:100,width:140,dataIndex: 'expressSmallZoneName'},"
				for(var i = 0 ;i<fields.length; i++){
					var field=fields[i];
					var fieldItem="{text: '"+field.headerDate+"',menuDisabled:true,columns: [{text : '"+field.dayText+"',menuDisabled:true,width : 80,sortable : false," +
							"renderer : function(val, metadata, record){" +
							"if(Ext.isEmpty(val)){	metadata.tdCls = metadata.tdCls+'alertedCellGrey'; return ' 未知 ';}" +
							//截取获取前面之和的值，与后面的总数进行比对，(FIXED：0，MOTORIZED：1),val 的值得格式 '0-3',前面是和，后面是条数
							"var sum= val.substring(0,1), count =val.substring(2,3);"+
							//若之和都为0 说明是定区
							"if(sum=='0'){ metadata.tdCls = metadata.tdCls +'alertedCellBlue'; return ' 主责 '; }"+
							//若之和不为0， 且与总条数相等，说明是机动属性
							"else if(sum!='0'&& sum==count){ metadata.tdCls = metadata.tdCls +'alertedCellGreen'; return ' 替班 ';} " +
							//若之和不为0，且与总条数不等，说明既有机动属性又有定区排班
							"else if(sum!='0'&& sum!=count){ metadata.tdCls = metadata.tdCls +'alertedCellRed';  return '主责/替班'; }" +
							"else {	metadata.tdCls = metadata.tdCls+'alertedCellGrey'; return ' 未知 ';}" +
							"},dataIndex: '"+field.dataIndex+"'," +
							"}]},"
					gridHeaderFields=gridHeaderFields+fieldItem;
				}
				gridHeaderFields+="]";
			}
			//解析表格列
			var gcs=eval(gridHeaderFields);
			//表格标题
			var gridTitle = baseinfo.queryCourierSchedule.expressPartName+'的'+baseinfo.queryCourierSchedule.ymd+'排班信息';
			
			var grid =Ext.create('Foss.baseinfo.queryCourierSchedule.ReportGird',{
						columns:gcs,				 	
					 	title:gridTitle,
				 		selModel: Ext.create('Ext.selection.CellModel',{
					 		listeners: {
					 			//当点击单元格时，根据当前的值，查询动态列表显示
					 			'select': function(cellModel, record, row, column, eOpts){
					 				//列表
					 				var fieldName = 'date'+(column+1);//(column+1);
					 				//收派小区编码
					 				var expressSmallZoneCode = record.data.expressSmallZoneCode;
					 				//快递点部
					 				var partCodes =baseinfo.queryCourierSchedule.expressPartCodes;
					 				//当前的快递属性
					 				var colVal=record.data[fieldName];
					 				if(Ext.isEmpty(colVal)){
					 					return;
					 				}
					 				//动态查询列表信息
									baseinfo.queryCourierSchedule.queryGirdWhenClicked(partCodes,fieldName,expressSmallZoneCode);
									baseinfo.queryCourierSchedule.oldValue=colVal;
									baseinfo.queryCourierSchedule.expressSmallZoneCode=expressSmallZoneCode;
									baseinfo.queryCourierSchedule.fieldName=fieldName;	
					 			}
					 		}
					 	}) 
			});
			var courierScheduleGrid =Ext.create('Foss.baseinfo.queryCourierSchedule.CourierScheduleGrid');
			 //显示于主页面
			 Ext.create('Ext.panel.Panel',{
					id:'T_baseinfo-queryCourierSchedule_content',
					cls:"panelContent",
					bodyCls:'panelContent-body',
					layout:'auto',
					margin:'0 0 0 0',
					getGrid : function() {
						return grid;
					},
					getCourierScheduleGrid : function() {
						return courierScheduleGrid;
					},
					items : [grid,courierScheduleGrid],
					renderTo: 'T_baseinfo-queryCourierSchedule-body'
				});
			 grid.getStore().load();
		}
	});
});