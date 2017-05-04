//装车状态
Ext.define('Foss.QueryLoadingProgress.LoadingStateStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'}, //编码
		{name: 'name',  type: 'string'}	//名称
	],
	data: {
		'items':[
			{'code':'LOADING','name':load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.loading')},//进行中
			{'code':'FINISHED','name':load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.finished')},//已完成
			{'code':'ALL','name':load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.all')}//全部
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

//装车是否超时
Ext.define('Foss.QueryLoadingProgress.LoadingTimeOverStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
			{'code':'TIME_OUT','name':load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.timeOut')},//是
			{'code':'NO_TIME_OUT','name':load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.noTimeOut')},//否
			{'code':'ALL','name':load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.all')}//全部
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

//查询装车信息的表单
Ext.define('Foss.QueryLoadingProgress.QueryLoadingProgressForm', {
	extend: 'Ext.form.Panel',
	title: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.queryCondition'),//查询条件
	frame:true,
	collapsible: true,
    animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 80
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name: 'taskNumber',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskNumber'),//装车任务编号
		labelWidth: 90,
		columnWidth: .25
	}, {
		name: 'arrivedDept',
		xtype: 'dynamicorgcombselector',
		fieldLabel:load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.arrivedDept') ,//到达站
		type : 'ORG',
		transferCenter: 'Y',
		columnWidth: .25
	}, {
		xtype: 'commontruckselector',
		name: 'vehicleNumber',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.vehicleNumber'),//车牌号
		columnWidth: .25
	}, {
		xtype: 'combobox',
		name: 'taskState',
		fieldLabel:load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskState') ,//装车状态
		columnWidth: .25,
		displayField: 'name',
		valueField:'code', 
		value:'ALL',
		queryMode:'local',
		store: Ext.create('Foss.QueryLoadingProgress.LoadingStateStore')
	}, 
	FossDataDictionary.getDataDictionaryCombo('TASK_TYPE',{
		fieldLabel : load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskType') ,//装车类型
		name : 'taskType',
		value : 'ALL',
		editable : false,
		labelWidth: 90,
		columnWidth:.25
	}), {
		xtype: 'combobox',
		name: 'timeOver',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.timeOver'),//是否超时
		columnWidth: .25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		value : 'ALL',
		store: Ext.create('Foss.QueryLoadingProgress.LoadingTimeOverStore')
	}, {
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.createTaskDate'),//建立任务时间
		fieldId: 'Foss_queryloadingprogress_QueryLoadingProgressForm_createTaskDate_Id',
		fromName: 'createTaskDateFrom',
		//设置开始时间默认值
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,00,00,00), 'Y-m-d H:i:s'),
		toName: 'createTaskDateTo',
		//设置结束时间默认值
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59), 'Y-m-d H:i:s'),
		allowBlank:false,	
		labelWidth: 90,
		columnWidth: .5
	}, {
		border : false,
		xtype : 'container',
		columnWidth:1,
		layout:'column',
		defaults: {
			margin:'5 0 5 0'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.reset'),//重置
			handler: function() {
				var form = this.up('form').getForm();
				form.reset();
				form.findField('createTaskDateFrom').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,00,00,00), 'Y-m-d H:i:s'));
				form.findField('createTaskDateTo').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,23,59,59), 'Y-m-d H:i:s'));
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			cls:'yellow_button',
			text: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.query'),//查询
			disabled:load.queryLoadingProgress.isPermission('load/queryLoadingProgressInfoButton')?false:true,
			hidden:load.queryLoadingProgress.isPermission('load/queryLoadingProgressInfoButton')?false:true,
			handler: function() {
				/**
				 * 复原分页参数，当点击查询按钮时，默认重新分页
				 */
				load.queryLoadingProgress.initStart = 0;
				load.queryLoadingProgress.pageSize = 5;
				load.queryLoadingProgress.executeQuery();
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//执行查询操作
load.queryLoadingProgress.executeQuery = function(){
	/**
	 * 判断查询时间段
	 */
	var beginDate = load.queryLoadingProgressForm.getValues().createTaskDateFrom;
	var endDate = load.queryLoadingProgressForm.getValues().createTaskDateTo;
	var difDate = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(beginDate,'Y-m-d H:i:s');
	var difTime = 1*24*60*60*1000-parseInt(difDate);
	
	var timeOver = load.queryLoadingProgressForm.getValues().timeOver;
	var taskState = load.queryLoadingProgressForm.getValues().taskState;
	
	if(load.queryLoadingProgressForm.getForm().isValid()){
		//一天之内的数据
		if(difTime>=0){
			var queryParams = load.queryLoadingProgressForm.getValues();
			Ext.Ajax.request({
				url : load.realPath('queryLoadingProgressInfo.action'),
				//获取当前行的运单号
				params : {
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.taskNumber':queryParams.taskNumber,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.arrivedDept':queryParams.arrivedDept,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.vehicleNumber':queryParams.vehicleNumber,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.taskState':queryParams.taskState,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.taskType':queryParams.taskType,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.timeOver':queryParams.timeOver,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.createTaskDateFrom':queryParams.createTaskDateFrom,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.createTaskDateTo':queryParams.createTaskDateTo,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.sequence':load.queryLoadingProgress.sequence,
					'queryLoadingProgressVo.queryLoadingProgressConditionDto.sequenceType':load.queryLoadingProgress.sequenceType,
					'queryLoadingProgressVo.initStart':load.queryLoadingProgress.initStart,
					'queryLoadingProgressVo.pageSize':load.queryLoadingProgress.pageSize
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					//取得返回结果条数，同设置的页面大小进行判断
					var length = result.queryLoadingProgressVo.queryLoadingProgressResultList.length;
					//当前大小
					load.queryLoadingProgress.currentPageSize = length;
					if(length==0){
						Ext.ux.Toast.msg(load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.message'), load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.dataNotFind'),'success', 5000);
						//提示 、未查询到符合条件的装车进度数据！
					}else{
						/**
						 * 如果取的数据小于设置的页面大小，
						 * 表明已经没数据了，
						 * 故下一页按钮可以设置不可用，
						 * （可以用其他更好的方式代替）
						 */
						if(load.queryLoadingProgress.currentPageSize<load.queryLoadingProgress.pageSize || result.queryLoadingProgressVo.count==(load.queryLoadingProgress.initStart+load.queryLoadingProgress.pageSize)){
							Ext.getCmp('Foss_QueryLoadingProgress_SortButtonContainer_downButton').setDisabled(true);	
						}else{
							Ext.getCmp('Foss_QueryLoadingProgress_SortButtonContainer_downButton').setDisabled(false);	
						}
					}
					//加载view对象
					load.loadingprogressView.store.loadData(result.queryLoadingProgressVo.queryLoadingProgressResultList);
					/**
					 * 遍历查询结果，没循环一次，产生一个view，
					 * 整个view用单据编号区分，
					 * 即如果单据编号相同，view会重叠，谨记
					 */
					load.loadingprogressView.getStore().each(function(record){
						var progressContainer = Ext.create('Foss.QueryLoadingProgress.ProgressContainer',{
							renderTo: 'queryLoadingProgress_'+record.get('taskNumber')+'_progress'
						});
						var loadingInfoForm = Ext.create('Foss.QueryLoadingProgress.LoadingInfoForm',{
							renderTo: 'queryLoadingProgress_'+record.get('taskNumber')+'_taskInfo'
						});
						progressContainer.setProgress(record,new Date());					
						loadingInfoForm.bindData(record);
					});
				
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
		            Ext.Msg.alert(load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.error'), result.message); //错误 
				}
			})
		}else{
			Ext.Msg.alert(load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.alert'), load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.timePeriodOverOneDay'));
		}//警告、时间跨度不能超过1天				
	}else{
		Ext.Msg.alert(load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.alert'), load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.inputQueryCondition'));
	}//警告 、请输入查询条件！
	
};

//定义一个排序按钮的组件
Ext.define('Foss.QueryLoadingProgress.SortButtonContainer', {
	extend: 'Ext.container.Container',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	border : false,
	layout:'column',
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'button',
			columnWidth:.1,
			text: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.orderByLeftVolume'),//按剩余体积
			handler: function() {
				/**
				 * 点击排序按钮时，将需要排序的类型放在全局变量中，
				 * 后台会根据排序类型进行处理，将排序类型和方式进行组合
				 * 如：ORDER_BY_CREATETASKDATE_DESC
				 */
				//load.queryLoadingProgress.executeQuery(byVolume)
				load.queryLoadingProgress.sequenceType = 'ORDER_BY_LEFTVOLUME';
				load.queryLoadingProgress.executeQuery();	
			}
		},{
			xtype : 'button',
			columnWidth:.1,
			text:  load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.orderByLeftWeight'),//按剩余重量
			handler: function() {
				load.queryLoadingProgress.sequenceType = 'ORDER_BY_LEFTWEIGHT';
				load.queryLoadingProgress.executeQuery();
			}
		},{
			columnWidth:.1,
			xtype : 'button',
			text: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.orderByCreateTaskDate'),//建立任务时间
			handler: function() {
				load.queryLoadingProgress.sequenceType = 'ORDER_BY_CREATETASKDATE';
				load.queryLoadingProgress.executeQuery();
			}
		},{
			columnWidth:.1,
			xtype : 'button',
			text:load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.orderByPlanDepartDate'),//规定发车时间
			handler: function() {
				load.queryLoadingProgress.sequenceType = 'ORDER_BY_PLANDEPARTDATE';
				load.queryLoadingProgress.executeQuery();
			}
		},{
			border : false,
			columnWidth:.4,
			html: '&nbsp;'
		},{
			columnWidth:.1,
			xtype : 'button',
			text: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.desc'),//由大到小
			handler: function() {
				load.queryLoadingProgress.sequence = 'DESC';
				load.queryLoadingProgress.executeQuery();
			}
		},{
			columnWidth:.1,
			xtype : 'button',
			text:load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.asc'),//由小到大
			handler: function() {
				load.queryLoadingProgress.sequence = 'ASC';
				load.queryLoadingProgress.executeQuery();
			}
		},{
			columnWidth:.1,
			xtype : 'button',
			text: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.upButton'),//上一页
			id:'Foss_QueryLoadingProgress_SortButtonContainer_upButton',
			handler: function() {
				/**
				 * 如果起始页大于0，则需要减去设置的页面大小（应该是当前页的大小，没数据测试）
				 * 
				 */
				if(load.queryLoadingProgress.initStart>0){
					load.queryLoadingProgress.initStart = load.queryLoadingProgress.initStart - load.queryLoadingProgress.pageSize;
					load.queryLoadingProgress.executeQuery();
				}else{
					
				}
			}
		},{
			columnWidth:.1,
			xtype : 'button',
			text: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.downButton'),//下一页
			id:'Foss_QueryLoadingProgress_SortButtonContainer_downButton',
			handler: function() {
				/**
				 * 如果当前页大于0，则起始页需加上设置的页面大小，此处不是当前页的大小
				 */
				if(load.queryLoadingProgress.currentPageSize>0){
					load.queryLoadingProgress.initStart = load.queryLoadingProgress.initStart + load.queryLoadingProgress.pageSize;
					load.queryLoadingProgress.executeQuery();
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.QueryLoadingProgress.LoadingProgressModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
	         //任务编号
		{name: 'taskNumber', type: 'string'},
		//任务建立时间
		{name: 'createTaskDate', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
		}},
		{name: 'loadedVolume', type: 'float'},//已装体积
		{name: 'loadedWeight', type: 'float'},//已装重量
		//任务完成时间
		{name: 'completeDate', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
		}},
		//到达时间
		{name: 'arrivedDate', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
		}},
		//规定发车时间
		{name: 'planDepartDate', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
		}},
		//线路名称
		{name: 'lineInfo', type: 'string'},
		//装车类型
		{name: 'taskType', type: 'string',
			convert: function(value) {
				if (value == 'LONG_DISTANCE_LOAD') {
					return load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.shortDistanceLoad');//长途装车
				} else if(value == 'SHORT_DISTANCE_LOAD'){
					return load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.longDistanceLoad');//短途装车
				}else if(value == 'DELIVER_LOAD'){
					return load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.deliverLoad');//派送装车
				}else if(value == 'LDP_LOAD'){
					return load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.ldpLoad');//落地配装车
				}else{
					return load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.partiallineLoad');//偏线装车
				}
		}},
		//车牌号
		{name: 'vehicleNumber', type: 'string'},
		//理货员
		{name: 'tallyMember', type: 'string'},
		//额定净空
		{name: 'ratedVolume', type: 'string'},
		//额定载重
		{name: 'ratedWeight', type: 'string'},
		//剩余体积
		{name: 'leftVolume', type: 'string'},
		//剩余重量
		{name: 'leftWeight', type: 'string'},
		//到达部门
		{name: 'arrivedDept', type: 'string'},
		//月台号
		{name: 'platform', type: 'string'},
		//装车人数
		{name: 'loadMember', type: 'string'},
		//任务状态
		{name: 'taskState', type: 'string'},
		{name: 'volumeProgress', type: 'string'},
		{name: 'weightProgress', type: 'string'}
	]
});

Ext.define('Foss.QueryLoadingProgress.LoadingProgressStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.QueryLoadingProgress.LoadingProgressModel',
	
	//定义一个代理对象
	proxy: {//代理的类型为ajax
		type: 'memory'
	},	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义一个查看进度的组件
Ext.define('Foss.QueryLoadingProgress.ProgressContainer', {
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
				columnWidth:.70
			});
		}
		return this.volumeProgress;  //返回体积进度
	},
	weightProgress : null,
	getWeightProgress : function(valueProgress){
		if(this.weightProgress==null){
			this.weightProgress = Ext.create('Ext.ProgressBar', {
				animate: true,
				margin: '1 0 1 0',
				columnWidth:.70
			});
		}
		return this.weightProgress;  //返回重量进度
	},
	setProgress : function(record,nowDate){
		if(record.get('taskState')==2){
			this.getVolumeProgress().addCls('below80');
			this.getWeightProgress().addCls('below80');
			var selecters = Ext.dom.Element.select('span',false,Ext.getDom('foss_queryLoadingProgress_'+record.get('taskNumber')+'_loading'));
			for(var i=0;i<selecters.elements.length;i++){
				selecters.elements[i].style.color = '#999';
			}
			return;
		}
		
		/**
		 * ==当装车体积进度中有一个超过90%，该条记录用绿色标示出来
		 * ==当装车体积进度中有一个超过80%，该条记录用黄色标示出来
		 */
		
		/**
		 * 装车重量/总重量，依此判断需要显示哪种重量样式
		 */
		var weightprogress = record.get('loadedWeight')/record.get('ratedWeight');
		
		/**
		 * 装车体积/总体积，依此判断需要显示哪种体积样式
		 */
		var volumeProgress = record.get('loadedVolume')/record.get('ratedVolume');
		
		this.getWeightProgress().updateProgress(weightprogress,record.get('weightProgress'));
		if(weightprogress<0.8){
			this.getWeightProgress().addCls('below80');
		}else if(weightprogress>=0.8&&weightprogress<0.9){
			this.getWeightProgress().addCls('over80');
		}else if(weightprogress>=0.9){
			this.getWeightProgress().addCls('over90');
		}
		
		this.getVolumeProgress().updateProgress(volumeProgress,record.get('volumeProgress'));
		if(volumeProgress<0.8){
			this.getVolumeProgress().addCls('below80');
		}else if(volumeProgress>=0.8&&volumeProgress<0.9){
			this.getVolumeProgress().addCls('over80');
		}else if(volumeProgress>=0.9){
			this.getVolumeProgress().addCls('over90');
		}
		/**
		 * 若任务未完成且剩余时间<0,则该任务超时，如果任务超时，需要用红色框框包起来
		 * 若任务未完成，则剩余时间=规定发车时间-当前时间
		 * 若任务已完成，则不显示剩余时间
		 * 若任务已完成且装车完成时间>规定发车时间，则任务超时，超时任务用红色标示出来
		 */
		var leafTime=record.get('arrivedDate')-nowDate;
		//任务状态
		var taskState = record.get('taskState');
		
		this.getWeightProgress().addCls('complete');
		this.getVolumeProgress().addCls('complete');
		
		if(taskState == 'FINISHED'){
			if(record.get('completeDate')>record.get('planDepartDate')){
				Ext.get('queryLoadingProgress_'+record.get('taskNumber')+'_progress').dom.style.border = '2px solid #F00';
			}
		}else if(taskState == 'LOADING' && leafTime < 0){
			Ext.get('queryLoadingProgress_'+record.get('taskNumber')+'_progress').dom.style.border = '2px solid #F00';
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
			border : false,
			columnWidth:.2,
			html: '<span>'+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.volumeProgress')+'</span>'//体积进度：
		},me.getVolumeProgress(),{
			border : false,
			columnWidth:.2,
			html: '<span>'+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.weightProgress')+'</span>'//重量进度
		},me.getWeightProgress()];
		me.callParent([cfg]);
	}
});

//装车信息的表单
Ext.define('Foss.QueryLoadingProgress.LoadingInfoForm', {
	extend: 'Ext.form.Panel',
	height: 80,
	defaults: {
		margin: '5 5 5 5',
		readOnly: true,
		labelWidth: 70
	},
	defaultType: 'textfield',
	layout: 'column',
	bindData : function(record){
		this.getForm().loadRecord(record);
	},
	items: [{
		name: 'lineInfo',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.lineInfo'),//线路
		columnWidth: .2
	}, {
		name: 'taskType',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskType'),//装车类型
		columnWidth: .16
	}, {
		name: 'vehicleNumber',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.vehicleNumber'),//车牌号
		columnWidth: .16
	}, {
		name: 'loadMember',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.loadMember'),//理货员
		columnWidth: .16
	}, {
		name: 'ratedVolume',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.ratedVolume'),//额定净空
		columnWidth: .16
	}, {
		name: 'ratedWeight',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.ratedWeight'),//额定载重
		columnWidth: .16
	}, {
		name: 'arrivedDept',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.arrivedDept'),//到达部门
		columnWidth: .36
	}, {
		name: 'platform',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.platform'),//月台号
		columnWidth: .16
	}, {
		name: 'tallyMember',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.tallyMember'),//装车人数
		columnWidth: .16
	}, {
		name: 'leftVolume',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.leftVolume'),//剩余体积
		columnWidth: .16
	}, {
		name: 'leftWeight',
		fieldLabel: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.leftWeight'),//剩余重量
		columnWidth: .16
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义一个线路信息的表格
Ext.define('Foss.QueryLoadingProgress.LoadingProgressView', {
	extend: 'Ext.view.View',
	frame: true,
	autoScroll: false,
	bodyCls: 'autoHeight',
	cls: 'autoHeight x-panel-default-view',
	tpl: null,
	constructor: function(config){
		var me = this,
			nowDate = new Date(),
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.QueryLoadingProgress.LoadingProgressStore');
		me.itemSelector = 'div.foss_queryLoadingProgress_thumb_wrap',
		me.tpl = new Ext.XTemplate(
			'<tpl for=".">',
				'<div class="foss_queryLoadingProgress_thumb_wrap">',
					'<h2>'+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskNumber')+'{taskNumber}</h2>',//装车信息编号：
					'<div id="foss_queryLoadingProgress_{taskNumber}_loading" class="foss_queryLoadingProgress_search_task">',
						'<div class="foss_queryLoadingProgress_top_task">',
							'<div class="foss_queryLoadingProgress_time_task">',
							'<div class="foss_icons_tfr_taskTime"></div>'+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.createTaskDate')+'<br/>{createTaskDate}',//建立任务时间
							'</div>',
							'<div class="foss_queryLoadingProgress_dline">---------</div>',
							'<div id="queryLoadingProgress_{taskNumber}_progress" class="foss_queryLoadingProgress_loading"></div>',
							'<div class="foss_queryLoadingProgress_time_complete"><span>'+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.completeDate')+'</span><br/>{[this.getCompleteDate(values.completeDate)]}</div>',//装车完成时间
							'<div class="foss_queryLoadingProgress_time_surplus"><span>'+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.leftTime')+'</span><br/>{[this.getLeftTime(values.planDepartDate,values, out)]}</div>',//剩余时间
							'<div class="foss_queryLoadingProgress_dline">---------</div>',
							'<div class="foss_queryLoadingProgress_time_task">',
							'<div class="foss_icons_tfr_departTime"></div>'+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.planDepartDate')+'<br/>{[this.getPlanTime(values.planDepartDate)]}</span>',//规定发车时间
							'</div>',
						'</div>',
						'<div id="queryLoadingProgress_{taskNumber}_taskInfo" class="foss_queryLoadingProgress_task_content foss_queryLoadingProgress_clear"></div>',
					'</div>',
				'</div>',
			'</tpl>',
			{
				//计算剩余时间
				getLeftTime: function(arrivedDate, values, out){
					if(values.taskState != 'LOADING'){
						return;
					}
					if(arrivedDate==null){
						return;
					}else{
						//若任务未完成，则剩余时间=规定发车时间-当前时间
						var d3=new Date(arrivedDate.substring(0,4),arrivedDate.substring(5,7)-1,arrivedDate.substring(8,10),arrivedDate.substring(11,13),arrivedDate.substring(14,16),arrivedDate.substring(17,19))-new Date();
						var h=Math.floor(d3/3600000); //小时
						var m=Math.floor((d3-h*3600000)/60000); //分钟
						var leftTime = h+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.hour')+m+load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.minute');//小时 、 分
						//超时任务用红色标示
						if(d3<0){
							leftTime = '<span style="color:#F00">'+leftTime+'</span>';
						}
						return leftTime;
					}
				},
				//规定发车时间
				getPlanTime: function(planDepartDate,values){
					if(planDepartDate==null){
						return;
					}else{
						return planDepartDate;
					}
				},
				//装车完成时间
				getCompleteDate: function(completeDate,values){
					if(completeDate==null){
						return;
					}else{
						return completeDate;
					}
				}
			}
		);
		me.callParent([cfg]);
	}
});

//定义一个任务说明组件
Ext.define('Foss.QueryLoadingProgress.TaskExplainContainer', {
	extend: 'Ext.container.Container',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout:'column',
	margin: '5 0 5 60',
	defaults: {
		xtype : 'panel',
		border : false,
		height: 14,
		width: 18
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype: 'image', 
			margin: '0 2 0 0',
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-over80'
		},{
			columnWidth:.21,
			html: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskExplainOver80')
			//装车体积或重量进度超过80%
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-over90'
		},{
			columnWidth:.21,
			html: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskExplainOver90')
			//装车体积或重量进度超过90%
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-below80'
		},{
			columnWidth:.21,
			html: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskExplainBelow80')
			//装车体积或重量进度低于80%
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-notopen'
		},{
			columnWidth:.12,
			html: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskExplainNotOpen')
			//任务未开始记录
		},{
			xtype: 'image', 
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-complete'
		},{
			columnWidth:.12,
			html: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskExplainComplete')
			//任务已完成记录
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			border : true,
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-timeover'
		},{
			columnWidth:.11,
			html: load.queryLoadingProgress.i18n('foss.load.queryLoadingProgress.taskExplainTimeOver')
			//任务超时
		}];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryLoadingProgressForm = Ext.create('Foss.QueryLoadingProgress.QueryLoadingProgressForm'); //查询表单
	load.queryLoadingProgressForm = queryLoadingProgressForm; //加入全局变更
	var sortButtons = Ext.create('Foss.QueryLoadingProgress.SortButtonContainer'); //排序按钮
	var loadingprogressView = Ext.create('Foss.QueryLoadingProgress.LoadingProgressView'); //线路信息表格
	load.loadingprogressView = loadingprogressView; //加入全局变更
	/**
	 * 设置排序方式，分页数据
	 */
	Ext.apply(load.queryLoadingProgress,{
		//默认从大到小排序
		sequence : 'DESC',
		//默认按创建任务时间排序
		sequenceType : 'ORDER_BY_CREATETASKDATE',
		//初始页
		initStart: 0,
		//每页大小
		pageSize: 5,
		//当前页显示条数
		currentPageSize: 0,
	});
	var taskExplain = Ext.create('Foss.QueryLoadingProgress.TaskExplainContainer'); //说明
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-queryloadingprogressindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [ queryLoadingProgressForm, sortButtons,loadingprogressView,taskExplain],
		renderTo: 'T_load-queryloadingprogressindex-body'
	});
});