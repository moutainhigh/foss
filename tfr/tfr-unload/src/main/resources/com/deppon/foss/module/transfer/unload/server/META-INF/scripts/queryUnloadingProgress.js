//卸车类型
Ext.define('Foss.QueryUnloadingProgress.UnloadingTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
			{'code':'LONG_DISTANCE','name':unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.longDistanceUnload')},//长途卸车
			{'code':'SHORT_DISTANCE','name':unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.shortDistanceUnload')},//短途卸车
			{'code':'DELIVER','name':unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.deliverUnload')}//集中卸车
		]
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});

//卸车状态
Ext.define('Foss.QueryUnloadingProgress.UnloadingStatusStore',{
	extend:'Ext.data.Store',
	fields:[
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data:{
		'items':[
			{code:'UNLOADING',name:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.unloading')},//进行中
			{code:'FINISHED',name:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.finished')}//已完成
		]
	},
	proxy:{
		type:'memory',
		reader:{
			type: 'json',
			root:'items'
		}
	}
});

//装车是否超时
Ext.define('Foss.QueryUnloadingProgress.UnloadingTimeOverStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
			{'code':'ALL','name':unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.all')},//全部
			{'code':'TIME_OUT','name':unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.timeOut')},//是
			{'code':'NO_TIME_OUT','name':unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.noTimeOut')}//否
		]
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询卸车信息表单
Ext.define('Foss.QueryUnloadingProgress.queryUnloadingProgressForm',{
	extend:'Ext.form.Panel',
	title: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.queryConditon'),//查询条件
	frame:true,
	collapsible: true,
    animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 85
	},
	defaultType: 'textfield',
	layout: 'column',
	items:[{
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.createTaskDate'),//建立任务时间
		fieldId: 'Foss_queryunloadingprogress_queryUnloadingProgressForm_createTaskDate_Id',
		fromName: 'taskStartTime',
		//设置开始时间默认值
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,00,00,00), 'Y-m-d H:i:s'),
		toName: 'taskEndTime',
		//设置结束时间默认值
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59), 'Y-m-d H:i:s'),
		disallowBlank:true,
		allowBlank : false,
		columnWidth: .5
	},{
		name: 'billNumber',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.billNumber'),//单据编号
		columnWidth: .25
	},{
		name: 'leaveDept',
		xtype: 'dynamicorgcombselector',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.leaveDept'),//出发部门
		type : 'ORG',
		transferCenter: 'Y',
		columnWidth: .25
	},{
		xtype:'combo',
		name:'taskState',
		fieldLabel:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskState'),//卸车状态
		columnWidth: .28,
		value : 'UNLOADING',
		displayField: 'name',
		editable : false,
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		store: Ext.create('Foss.QueryUnloadingProgress.UnloadingStatusStore')
	},{
		name: 'vehicleNumber',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.vehicleNumber'),//车牌号
		labelWidth:50,
		xtype : 'commontruckselector',
		columnWidth: .22
	},{
		xtype:'combo',
		name:'taskType',
		fieldLabel:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskType'),//卸车类型
		editable : false,
		value : 'LONG_DISTANCE',
		columnWidth: .25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		store: Ext.create('Foss.QueryUnloadingProgress.UnloadingTypeStore')
	},{
		xtype:'hiddenfield',
		columnWidth:.25
	}, {
		xtype: 'combobox',
		name: 'timeOver',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.timeOver'),//是否超时
		columnWidth: .25,
		value : 'ALL',
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable : false,
		store: Ext.create('Foss.QueryUnloadingProgress.UnloadingTimeOverStore')
	},/*{
		xtype:'fieldcontainer',
		fieldLabel:'是否超时',
		columnWidth:1,
		allowBlank:true,
		defaultType:'radiofield',
		layout: 'hbox',
		defaults:{
			width:50
		},
    items:[{
	        boxLabel  : '是',
	        name      : 'isTimeOut',//此处名字必须相同，这样保证单选框只能选择一个
	        inputValue: '1'
        },{
	        boxLabel  : '否',
	        name      : 'isTimeOut',
	        inputValue: '2'
        },{
	        boxLabel  : '全部',
	        name      : 'isTimeOut',
	        checked   : true,  //默认选中
	        inputValue: '3'
        }]
	},*/{
		border: true,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.reset'),//重置
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.reset();
				form.findField('taskStartTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,00,00,00), 'Y-m-d H:i:s'));
				form.findField('taskEndTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59), 'Y-m-d H:i:s'));
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.84
		},{
			text:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.query'),//查询
			cls:'yellow_button',
			disabled:unload.queryUnloadingProgress.isPermission('unload/queryUnloadingProgressInfoButton')==true?false:true,
			hidden:unload.queryUnloadingProgress.isPermission('unload/queryUnloadingProgressInfoButton')==true?false:true,
			
			columnWidth:.08,
			handler:function(){
				/**
				 * 复原分页参数，当点击查询按钮时，默认重新分页
				 */
				unload.queryUnloadingProgress.initStart = 0;
				unload.queryUnloadingProgress.pageSize = 5;
				//执行查询操作，查询功能提取出来，供各个排序功能使用
				unload.queryUnloadingProgress.executeQuery();
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 执行查询操作
 */
unload.queryUnloadingProgress.executeQuery = function(){
	/**
	 * 判断查询时间段，手动判断
	 */
	var beginDate = unload.queryUnloadingProgressForm.getValues().taskStartTime,
        endDate = unload.queryUnloadingProgressForm.getValues().taskEndTime,
        difDate = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(beginDate,'Y-m-d H:i:s'),
        difTime = 2*24*60*60*1000-parseInt(difDate);
	//如果没有卸车标准，不执行查询操作，即使执行了也是白执行了，而且标准不能为0
	if(unload.queryUnloadingProgress.unloadWeightStd == 0 || 
			unload.queryUnloadingProgress.unloadVolumeStd ==0){
		Ext.Msg.alert(unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.alert') , unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.addUnloadStandard'));//警告、本部门没有卸车标准，请添加本部门的卸车标准！
		return;
	}
	//合法性校验
	if(unload.queryUnloadingProgressForm.getForm().isValid()){
		
		if(difTime>0){
			var queryParams = unload.queryUnloadingProgressForm.getValues();
			Ext.Ajax.request({
				//url:'../unload/queryUnloadingProgressInfo.action',
				url : unload.realPath('queryUnloadingProgressInfo.action'),
				//获取当前行的运单号
				params : {
					//单据编号
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.billNumber':queryParams.billNumber,
					//出发部门
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.leaveDept':queryParams.leaveDept,
					//车牌号
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.vehicleNumber':queryParams.vehicleNumber,
					//任务状态
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.taskState':queryParams.taskState,
					//任务类型
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.taskType':queryParams.taskType,
					//是否超时
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.timeOver':queryParams.timeOver,
					//任务开始时间
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.taskStartTime':queryParams.taskStartTime,
					//任务结束时间
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.taskEndTime':queryParams.taskEndTime,
					//排序方式
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.sequence':unload.queryUnloadingProgress.sequence,
					//排序类型
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.sequenceType':unload.queryUnloadingProgress.sequenceType,
					//卸车重量标准
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.unloadWeightStd':unload.queryUnloadingProgress.unloadWeightStd,
					//卸车体积标准
					'queryUnloadingProgressVo.queryUnloadingProgressConditionDto.unloadVolumeStd':unload.queryUnloadingProgress.unloadVolumeStd,
					//页面大小
					'queryUnloadingProgressVo.pageSize':unload.queryUnloadingProgress.pageSize,
					//起始页
					'queryUnloadingProgressVo.initStart':unload.queryUnloadingProgress.initStart
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					//取得返回结果条数，同设置的页面大小进行判断
					var length = result.queryUnloadingProgressVo.queryUnloadingProgressResultDtoList.length;
					//画面显示共几页
					unload.queryUnloadingProgress.viewTotalPageSize = result.queryUnloadingProgressVo.totalPageSize;
					//画面显示当前是第几页
					unload.queryUnloadingProgress.viewCurrentPageSize = result.queryUnloadingProgressVo.currentPageSize;
					if(unload.queryUnloadingProgress.viewTotalPageSize != 0){
						Ext.getCmp('Foss_QueryUnloadingProgress_SortButtonContainer_text_viewTotal').setValue('共'+unload.queryUnloadingProgress.viewTotalPageSize+' 页' + '/' + '第'+unload.queryUnloadingProgress.viewCurrentPageSize+'页');
					}

					//当前大小
					unload.queryUnloadingProgress.currentPageSize = length;
					if(length==0){
						Ext.ux.Toast.msg(unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.message'), unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.noQueryedData'), 'success', 5000);//提示、未查询到符合条件的装车进度数据！
					}else{
						//load.queryLoadingProgress.initStart = load.queryLoadingProgress.initStart + length;
						/**
						 * 如果取的数据小于设置的页面大小，
						 * 表明已经没数据了，
						 * 故下一页按钮可以设置不可用，
						 * （可以用其他更好的方式代替）
						 */
						if(unload.queryUnloadingProgress.currentPageSize<unload.queryUnloadingProgress.pageSize || unload.queryUnloadingProgress.viewTotalPageSize == unload.queryUnloadingProgress.viewCurrentPageSize){
							Ext.getCmp('Foss_QueryUnloadingProgress_SortButtonContainer_downButton').setDisabled(true);	
						}else{
							Ext.getCmp('Foss_QueryUnloadingProgress_SortButtonContainer_downButton').setDisabled(false);	
						}
					}
					
					if(unload.queryUnloadingProgress.viewCurrentPageSize == 1){
						Ext.getCmp('Foss_QueryUnloadingProgress_SortButtonContainer_upButton').setDisabled(true);	
					}else{
						Ext.getCmp('Foss_QueryUnloadingProgress_SortButtonContainer_upButton').setDisabled(false);	
					}
					
					//加载view对象
					unload.unloadingprogressView.store.loadData(result.queryUnloadingProgressVo.queryUnloadingProgressResultDtoList);
					/**
					 * 遍历查询结果，没循环一次，产生一个view，
					 * 整个view用单据编号区分，
					 * 即如果单据编号相同，view会重叠，谨记
					 */
					unload.unloadingprogressView.getStore().each(function(record){
						var progressContainer = Ext.create('Foss.QueryUnloadingProgress.ProgressContainer',{
							renderTo: 'queryUnloadingProgress_'+record.data.billNumber+'_progress'
						});
						//进度条
						progressContainer.setProgress(record,new Date());	
						//进度条下方的form表格
						var unloadingInfo = Ext.create('Foss.QueryUnloadingProgress.UnloadingInfoForm',{
							renderTo:'queryUnloadingProgress_'+record.data.billNumber+'_taskInfo'
						});
						unloadingInfo.getForm().loadRecord(record);					
					});
				
				
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
		            Ext.Msg.alert(unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.error'), result.message);  //错误
				}
			})
		}else{
			Ext.Msg.alert(unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.alert'), unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.timePeriodOverOneDay'));//警告、时间跨度不能超过1天！
		}				
	}else{
		Ext.Msg.alert(unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.alert'), unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.inputQueryingCondion'));//警告、请输入查询条件！
	}
	
};

//中间排序按钮
Ext.define('Foss.QueryUnloadingProgress.SortButtonContainer',{
	extend:'Ext.container.Container',
	layout:'column',
	//frame:true,
	defaults:{
		columnWidth:.08,
		cls : 'btnAfterTextfield',
		margin : '0 2 0 0'
	},
	items:[{
		xtype : 'button',
		text:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.orderByLeftVolume'),//按剩余体积
		handler:function(){
			/**
			 * 点击排序按钮时，将需要排序的类型放在全局变量中，
			 * 后台会根据排序类型进行处理，将排序类型和方式进行组合
			 * 如：ORDER_BY_CREATETASKDATE_DESC
			 */
			unload.queryUnloadingProgress.sequenceType = 'ORDER_BY_LEFTVOLUME';
			unload.queryUnloadingProgress.executeQuery();
		}
	},{
		xtype : 'button',
		text:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.orderByLeftWeight'),//按剩余重量
		handler:function(){
			unload.queryUnloadingProgress.sequenceType = 'ORDER_BY_LEFTWEIGHT';
			unload.queryUnloadingProgress.executeQuery();
		}
	},{
		xtype : 'button',
		text:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.creatTaskDate'),//建立任务时间
		columnWidth : .09,
		handler:function(){
			unload.queryUnloadingProgress.sequenceType = 'ORDER_BY_CREATETASKDATE';
			unload.queryUnloadingProgress.executeQuery();
		}
	},{
		xtype : 'button',
		text:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.predictCompleteTime'),//预计完成时间
		columnWidth : .09,
		handler:function(){
			unload.queryUnloadingProgress.sequenceType = 'ORDER_BY_PREDICTCOMPLETETIME';
			unload.queryUnloadingProgress.executeQuery();
		}
	},{
		border : false,
		columnWidth:.09,
		html: '&nbsp;'
	},{
		columnWidth:.1,
		xtype : 'textfield',
		text: '',
		id:'Foss_QueryUnloadingProgress_SortButtonContainer_text_viewTotal',
		readOnly:true,
		margin : '5 0 0 0'
	},{
		columnWidth:.06,
		xtype : 'button',
		text: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.upButton'),//上一页
		id:'Foss_QueryUnloadingProgress_SortButtonContainer_upButton',
		handler: function() {
			/**
			 * 如果起始页大于0，则需要减去设置的页面大小（应该是当前页的大小，没数据测试）
			 * 
			 */
			if(unload.queryUnloadingProgress.initStart>0){
				unload.queryUnloadingProgress.initStart = unload.queryUnloadingProgress.initStart - unload.queryUnloadingProgress.pageSize;
				unload.queryUnloadingProgress.executeQuery();
			}else{
				
			}
		}
	},{
		columnWidth:.06,
		xtype : 'button',
		text: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.downButton'),//下一页
		id:'Foss_QueryUnloadingProgress_SortButtonContainer_downButton',
		handler: function() {
			/**
			 * 如果当前页大于0，则起始页需加上设置的页面大小，此处不是当前页的大小
			 */
			if(unload.queryUnloadingProgress.currentPageSize>0){
				unload.queryUnloadingProgress.initStart = unload.queryUnloadingProgress.initStart + unload.queryUnloadingProgress.pageSize;
				unload.queryUnloadingProgress.executeQuery();
			}
		}
	},{
		border : false,
		columnWidth:.19,
		html: '&nbsp;'
	},{
		xtype : 'button',
		text: '<div style="float:left;margin:2px 0 0 10px">'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.desc')+'</div><div style="margin-left:60px;"class="deppon_icons_dsc"></div>',//由大到小
		handler:function(){
			unload.queryUnloadingProgress.sequence = 'DESC';
			unload.queryUnloadingProgress.executeQuery();
		}
	},{
		xtype : 'button',
		text: '<div style="float:left;margin:2px 0 0 10px">'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.asc')+'</div><div style="margin-left:60px;"class="deppon_icons_asc"></div>',//由小到大
		handler:function(){
			unload.queryUnloadingProgress.sequence = 'ASC';
			unload.queryUnloadingProgress.executeQuery();
		}
	}]
	
});
//卸车进度model
Ext.define('Foss.QueryUnloadingProgress.UnloadingProgressModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'billNumber', type: 'string'},//单据编号
		{name: 'arrivedTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return '';
				}
		}},//车辆到达时间
		{name: 'distributeTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return //;
				}
		}},//分配时间
		{name: 'taskBeginTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return '';
				}
		}},//建立任务时间
		{name: 'taskCompleteTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return '';
				}
		}},//已完成时间
		{name: 'taskPlanTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return '';
				}
		}},//计划完成时间
		{name: 'predictCompleteTime', type: 'date',
				convert: function(value) {
					if (value != null) {
						var date = new Date(value);
						return Ext.Date.format(date,'Y-m-d H:i:s');
					} else {
						return '';
					}
		}},//预计完成时间
		{name: 'vehicleNumber', type: 'string'},//车辆计划
		{name: 'totalPieces', type: 'int'},
		{name: 'totalWeight', type: 'float'},
		{name: 'totalVolume', type: 'float'},
		{name: 'isTimeout', type: 'string',
			convert: function(value) {
				if (value == 'TIME_OUT') {
					return unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.timeOut');//是
				} else if(value == 'NO_TIME_OUT'){
					return unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.noTimeOut');//否
				} else{
					return '';
				}
		}},
		{name: 'leaveDept', type: 'string',
			convert: function(value) {
				if (value == null) {
					return '';
				}else{
					return value;
				}
		}},//出发部门
		//剩余件数
		{name: 'leftPieces', type: 'int'},
		//重量字符进度
		{name: 'unLoadingWeightProgress', type: 'string'},
		//体积字符进度
		{name: 'unLoadingVolumnProgress', type: 'string'},
		//卸车人
		{name: 'unLoadMember', type: 'string'},
		//月台
		{name: 'platform', type: 'string'},
		//剩余重量
		{name: 'leftWeight', type: 'float'},
		//剩余体积
		{name: 'leftVolume', type: 'float'},
		//已卸体积
		{name: 'unloadedVolume', type: 'float'},
		//已卸重量
		{name: 'unloadedWeight', type: 'float'},
		//体积进度
		{name: 'volumeProgress', type: 'string'},
		//重量进度
		{name: 'weightProgress', type: 'string'},
		//任务状态
		{name: 'taskState', type: 'string'}
	]
});
//卸车进度store
Ext.define('Foss.QueryUnloadingProgress.UnloadingProgressStore',{
	extend:'Ext.data.Store',
	model:'Foss.QueryUnloadingProgress.UnloadingProgressModel',
	/*data :{ 'items':[
		{billNumber:'1',arrivedTime:new Date(2012,5,20,11,23,50),distributeTime:new Date(2012,5,20,12,01,50),taskBeginTime:new Date(2012,5,20,12,20,20),taskCompleteTime:new Date(2012,5,20,12,20,20),taskPlanTime:new Date(2012,5,20,16,30,00),vehicleNumber:'00000112',pieces:100,weight:5,volume:2,isTimeOut:true,leaveDept:'上海徐泾营业部',leftPieces:2,unLoadingWeightProgress:'3KG/5KG',unLoadingVolumnProgress:'0.8L/2L',unLoadMember:'理货员1',platform:'203135',leftWeight:0,leftVolume:0},
		{billNumber:'2',arrivedTime:new Date(2012,6,20,12,15,36),distributeTime:new Date(2012,6,20,13,05,35),taskBeginTime:new Date(2012,5,20,12,20,20),taskCompleteTime:new Date(2012,5,20,12,20,20),taskPlanTime:new Date(2012,5,20,16,30,00),vehicleNumber:'00000112',pieces:100,weight:5,volume:2,isTimeOut:false,leaveDept:'上海徐泾营业部',leftPieces:2,unLoadingWeightProgress:'3KG/5KG',unLoadingVolumnProgress:'0.8L/2L',unLoadMember:'理货员1',platform:'203135',leftWeight:'3',leftVolume:'0.2'}
		]},*/
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory'
		//定义一个读取器
		/*reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}*/
	}
});

//定义卸车信息表单
Ext.define('Foss.QueryUnloadingProgress.UnloadingInfoForm',{
	extend: 'Ext.form.Panel',
	margin: '0 0 0 30',
	height : 70,
	defaults: {
		readOnly: true,
		labelWidth: 60,
		readOnly:true
	},
	defaultType: 'textfield',
	layout: 'column',
	items:[{
		name: 'vehicleNumber',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.vehicleNumber'),//车牌号
		columnWidth: .25
	},{
		name: 'totalPieces',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.totalPieces'),//总件数
		columnWidth: .15
	},{
		name: 'totalWeight',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.totalWeight'),//总重量
		columnWidth: .2
	},{
		name: 'totalVolume',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.totalVolume'),//总体积
		columnWidth: .2
	},{
		name: 'isTimeout',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.isTimeout'),//是否超时
		columnWidth: .2
	},{
		name: 'leaveDept',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.leaveDept'),//出发部门
		//labelWidth: 75,
		columnWidth: .25
	},{
		name: 'leftPieces',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.leftPieces'),//剩余件数
		columnWidth: .15
	},{
		name: 'unLoadingWeightProgress',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.unLoadingWeightProgress'),//卸车重量进度
		labelWidth: 85,
		columnWidth: .2
	},{
		name: 'unLoadingVolumnProgress',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.unLoadingVolumnProgress'),//卸车体积进度
		labelWidth: 85,
		columnWidth: .3
	},{
		name: 'unLoadMember',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.unLoadMember'),//理货员
		columnWidth: .25
	},{
		name: 'platform',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.platform'),//月台号
		columnWidth: .15
	},{
		name: 'leftWeight',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.leftWeight'),//剩余重量
		columnWidth: .2
	},{
		name: 'leftVolume',
		fieldLabel: unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.leftVolume'),//剩余体积
		columnWidth: .2
	}]
});

//定义一个查看进度的组件
Ext.define('Foss.QueryUnloadingProgress.ProgressContainer', {
	extend: 'Ext.container.Container',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout:'column',
	margin: '6 0 0 10',
	volumeProgress : null,
	getVolumeProgress : function(){
		if(this.volumeProgress==null){
			this.volumeProgress = Ext.create('Ext.ProgressBar', {
				animate: true,
				margin: '1 0 1 0',
				columnWidth:.65
			});
		}
		return this.volumeProgress;
	},
	weightProgress : null,
	getWeightProgress : function(valueProgress){
		if(this.weightProgress==null){
			this.weightProgress = Ext.create('Ext.ProgressBar', {
				animate: true,
				margin: '1 0 1 0',
				columnWidth:.65
			});
		}
		return this.weightProgress;
	},
	setProgress : function(record,nowDate){
		if(record.get('state')==2){
			this.getVolumeProgress().addCls('below80');
			this.getWeightProgress().addCls('below80');
			var selecters = Ext.dom.Element.select('span',false,Ext.getDom('queryUnloadingProgress_'+record.get('billNumber')+'_loading'));
			for(var i=0;i<selecters.elements.length;i++){
				selecters.elements[i].style.color = '#999';
			}
			return;
		}
		/**
		 * ==当卸车卸车体积进度中有一个超过80%，该条记录用绿色标示出来
		 * ==当卸车、卸车体积进度中有一个超过90%，该条记录用黄色标示出来
		 */
		/**
		 * 卸车体积/总体积，依此判断需要显示哪种体积样式
		 */
		var volumeProgress = record.get('unloadedVolume')/record.get('totalVolume');
		/**
		 * 卸车重量/总重量，依此判断需要显示哪种重量样式
		 */
		var weightprogress = record.get('unloadedWeight')/record.get('totalWeight');
		this.getVolumeProgress().updateProgress(volumeProgress,record.get('volumeProgress'));
		this.getWeightProgress().updateProgress(weightprogress,record.get('weightProgress'));
		if(record.get('taskCompleteTime') != null && record.get('taskCompleteTime') != ''){
			this.getVolumeProgress().addCls('complete');
			this.getWeightProgress().addCls('complete');
		}else{
			if(volumeProgress<0.8){
				this.getVolumeProgress().addCls('below80');
			}else if(volumeProgress>=0.8&&volumeProgress<0.9){
				this.getVolumeProgress().addCls('over80');
			}else if(volumeProgress>=0.9){
				this.getVolumeProgress().addCls('over90');
			}
			
			if(weightprogress<0.8){
				this.getWeightProgress().addCls('below80');
			}else if(weightprogress>=0.8&&weightprogress<0.9){
				this.getWeightProgress().addCls('over80');
			}else if(weightprogress>=0.9){
				this.getWeightProgress().addCls('over90');
			}
		}
		
		/*if(weightprogress==1 && volumeProgress==1 && (record.get('taskCompleteTime')>record.get('taskPlanTime'))){
			Ext.get('queryUnloadingProgress_'+record.get('billNumber')+'_progress').dom.style.border = '2px solid #F00';
		}else if((weightprogress<1 ||volumeProgress<1)&& nowDate>record.get('taskPlanTime')){
			Ext.get('queryUnloadingProgress_'+record.get('billNumber')+'_progress').dom.style.border = '2px solid #F00';
		}*/
		/**
		 * 如果任务超时，需要用红色框框包起来
		 */
		if(record.data.isTimeout==unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.timeOut')){//是
			Ext.get('queryUnloadingProgress_'+record.get('billNumber')+'_progress').dom.style.border = '2px solid #F00';
		}
	},
	initComponent: function(){
		var me = this;
		me.items = [{
			border : false,
			columnWidth:.3,
			html: '<span>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.volumeProgress')+'</span>'//体积进度：
		},me.getVolumeProgress(),{
			border : false,
			columnWidth:.3,
			html: '<span>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.weightProgress')+'</span>'//重量进度：
		},me.getWeightProgress()];
		me.callParent();
	}
});

//卸车进度信息
Ext.define('Foss.QueryUnloadingProgress.UnloadingProgressView', {
	extend: 'Ext.view.View',
	frame: true,
	autoScroll: false,
	bodyCls: 'autoHeight',
	cls: 'autoHeight x-panel-default-view',
	itemSelector:'div.unloading_thumb-wrap',  
	tpl: null,
	initComponent:function(){
		var me = this;
		me.store = Ext.create('Foss.QueryUnloadingProgress.UnloadingProgressStore');
		me.tpl = new Ext.XTemplate(
		'<div style="text-align: right;">'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.volumeStandard')+''+unload.queryUnloadingProgress.unloadVolumeStd+''+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.weightStandard')+''+unload.queryUnloadingProgress.unloadWeightStd+''+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.timeStandard')+'</div>',
		//卸车体积标准：    F/min  卸车重量标准：  T/min
		'<tpl for=".">',
		'<div class="unloading_thumb-wrap">',
			'<h2>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.billNumber')+'{billNumber}</h2>',//单据编号：
			'<div id="queryUnloadingProgress_{billNumber}_loading" class="unloading_search_task">',
				'<div class = "unloading_top_task">',
					'<div class="unloading_time_task">',
						'<div class="foss_icons_tfr_arrivedTime"></div>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.arrivedTime')+'<br/>{arrivedTime}',//车辆已到达
					'</div>',
					'<div class="unloading_dline">------</div>',
					'<div class="unloading_time_task">',
					'<div class="foss_icons_tfr_distributeTime"></div>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.distributeTime')+'<br/>{distributeTime}',//分配时间
					'</div>',		
					'<div class="unloading_dline">------</div>',
					'<div class="unloading_time_task">',
					'<div class="foss_icons_tfr_taskTime"></div>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskBeginTime')+'<br/>{taskBeginTime}',//建立任务时间
					'</div>',
					'<div id="queryUnloadingProgress_{billNumber}_progress" class="unloading_loading"></div>',
					
					'<tpl if="this.beFinished(values.taskState)">',
					'<div class="unloading_time_complete"><span>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskCompleteTime')+'</span><br/>{taskCompleteTime}</div>',//已完成时间
					'</tpl>',
					'<tpl if="!this.beFinished(values.taskState)">',
					'<div class="unloading_time_complete"><span>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.predictCompleteTime')+'</span><br/>{predictCompleteTime}</div>',//预计完成时间
					'</tpl>',
					'<div class="unloading_dline">------</div>',	
					'<div class="unloading_time_task">',
						'<span>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskPlanTime')+'<br/>{taskPlanTime}</span>',//计划完成时间
					'</div>',
			'</div>',
			'<div class="unloading_task_area">',
				'<div id="queryUnloadingProgress_{billNumber}_taskInfo" class="unloading_task_content unloading_clear"></div>',
				
				'<tpl if="!this.beFinished(values.taskState)">',
					'<div class="unloading_time_surplus"><span>'+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskPlanTime')+'</span><br/>{[this.getLeftTime(values.taskPlanTime)]}</div>',//剩余时间
				'</tpl>',
				
			'</div>',
		'</div>',
		'</tpl>',
		{
				getLeftTime: function(taskPlanTime){
					var d3=new Date(taskPlanTime.substring(0,4),taskPlanTime.substring(5,7)-1,taskPlanTime.substring(8,10),taskPlanTime.substring(11,13),taskPlanTime.substring(14,16),taskPlanTime.substring(17,19))-new Date();
					var h=Math.floor(d3/3600000);
					var m=Math.floor((d3-h*3600000)/60000);
					var leftTime = h+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.hour')+m+unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.minute');//小时  分
					if(d3<0){
						leftTime = '<span style="color:#F00">'+leftTime+'</span>';
					}
					return leftTime;
				},
				beFinished: function(taskState){
					if(taskState=='FINISHED'){
						return true;
					}else{
						return false;
					}
				}
			}
		);  
		
		this.callParent();
	}
});


//提示信息
Ext.define('Foss.QueryUnloadingProgress.TaskExplainContainer',{
	extend:'Ext.container.Container',
	layout:'column',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	margin: '5 0 5 60',
	defaults: {
		xtype : 'panel',
		border : false,
		height: 14,
		width: 18
	},
	items:[{
		margin:'2 5 0 0'	,
		xtype: 'image', 
		cls: 'task-explain-base task-explain-over80'
	},{
		html:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskExplainOver80'),//卸车体积或重量进度超过80%
		columnWidth:.21
	},{
			margin: '2 5 0 0',
			xtype: 'image', 
			cls: 'task-explain-base task-explain-over90'
	},{
		html:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskExplainOver90'),//卸车体积或重量进度超过90%
		columnWidth:.21
	},{
			margin: '2 5 0 0',
			xtype: 'image', 
			cls: 'task-explain-base task-explain-below80'
	},{
		html:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskExplainBelow80'),//卸车体积或重量进度低于80%
		columnWidth:.21
	},{
			margin: '2 5 0 0',
			xtype: 'image', 
			cls: 'task-explain-base task-explain-notopen'
	},{
		html:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskExplainNotOpen'),//任务未开始记录
		columnWidth:.12
	},{
			margin: '2 5 0 0',
			xtype: 'image', 
			cls: 'task-explain-base task-explain-complete'
	},{
		html:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskExplainComplete'),//任务已完成记录
		columnWidth:.12
	},{
			margin: '2 5 0 0',
			xtype: 'image', 
			border : true,	
			cls: 'task-explain-base task-explain-timeover'
	},{
		html:unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.taskExplainTimeOver'),//任务超时
		columnWidth:.12
	}]
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	
	/**
	 * 获取本部门的卸车标准，
	 * 原意是在JSP中获取全局变量，
	 * 但浏览器缓存实在是强大，
	 * 没卸车标准的部门在切换有卸车的标准时就有值了
	 */
	Ext.Ajax.request({
		url: unload.realPath('queryLoadAndUnloadStd.action'),
		//异步改同步，只有此ajax加载后，界面才会出现装卸车标准
		async:false,
		success: function(response){
			var result = Ext.decode(response.responseText);
			//设置全局变量
			Ext.apply(unload.queryUnloadingProgress,{
				//重量标准
				unloadWeightStd : result.queryUnloadingProgressVo.loadAndUnloadStandardDto.unloadWeightStd,
				//体积标准
				unloadVolumeStd : result.queryUnloadingProgressVo.loadAndUnloadStandardDto.unloadVolumeStd
			});
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//出现异常时，重量标准和体积标准均 为0
			Ext.apply(unload.queryUnloadingProgress,{
				unloadWeightStd : 0,
				unloadVolumeStd : 0
			});
			Ext.ux.Toast.msg(unload.queryUnloadingProgress.i18n('foss.unload.queryUnloadingProgress.message'), result.message, 'error', 5000);//提示
		}
	});
	
	var queryUnloadingProgressForm = Ext.create('Foss.QueryUnloadingProgress.queryUnloadingProgressForm');
	unload.queryUnloadingProgressForm = queryUnloadingProgressForm;
	var sortButtons = Ext.create('Foss.QueryUnloadingProgress.SortButtonContainer');
	var unloadingprogressView = Ext.create('Foss.QueryUnloadingProgress.UnloadingProgressView');
	unload.unloadingprogressView = unloadingprogressView;
	
	
	/**
	 * 设置排序方式，分页数据
	 */
	Ext.apply(unload.queryUnloadingProgress,{
		//默认从大到小排序
		sequence : 'DESC',
		//默认按创建任务时间排序
		sequenceType : 'ORDER_BY_CREATETASKDATE',
		//初始页
		initStart: 0,
		//每页大小
		pageSize: 5,
		//当前页显示条数
		currentPageSize: 0
	});
	
	var taskExplain = Ext.create('Foss.QueryUnloadingProgress.TaskExplainContainer');
	Ext.create('Ext.panel.Panel',{
		id: 'T_unload-queryunloadingprogressindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [ queryUnloadingProgressForm,sortButtons,unloadingprogressView,taskExplain],
		renderTo: 'T_unload-queryunloadingprogressindex-body'
	});
});