//装车状态
Ext.define('Foss.platform.QueryLoadingProgress.LoadingStateStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'}, //编码
		{name: 'name',  type: 'string'}	//名称
	],
	data: {
		'items':[
			{'code':'LOADING','name':'进行中'},//进行中
			{'code':'FINISHED','name':'已完成'}//已完成
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
Ext.define('Foss.platform.QueryLoadingProgress.LoadingTimeOverStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
			{'code':'TIME_OUT','name':'是'},//是
			{'code':'NO_TIME_OUT','name':'否'},//否
			{'code':'ALL','name':'全部'}//全部
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
Ext.define('Foss.platform.QueryLoadingProgress.QueryLoadingProgressForm', {
	extend: 'Ext.form.Panel',
	title: '查询条件',//查询条件
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
		fieldLabel: '装车任务编号',//装车任务编号
		labelWidth: 90,
		columnWidth: .25
	}, {
		name: 'arrivedDept',
		xtype: 'dynamicorgcombselector',
		fieldLabel:'到达站' ,//到达站
		type : 'ORG',
		transferCenter: 'Y',
		columnWidth: .25
	}, {
		xtype: 'commontruckselector',
		name: 'vehicleNumber',
		fieldLabel: '车牌号',//车牌号
		columnWidth: .25
	}, {
		xtype: 'combobox',
		name: 'taskState',
		fieldLabel:'装车状态' ,//装车状态
		columnWidth: .25,
		displayField: 'name',
		valueField:'code', 
		value:'LOADING',
		queryMode:'local',
		store: Ext.create('Foss.platform.QueryLoadingProgress.LoadingStateStore')
	}, 
	FossDataDictionary.getDataDictionaryCombo('TASK_TYPE',{
		fieldLabel : '装车类型' ,//装车类型
		name : 'taskType',
		value : 'ALL',
		editable : false,
		labelWidth: 90,
		columnWidth:.25
	}), {
		xtype: 'combobox',
		name: 'timeOver',
		fieldLabel: '是否超时',//是否超时
		columnWidth: .25,
		displayField: 'name',
		valueField:'code', 
		queryMode:'local',
		value : 'ALL',
		store: Ext.create('Foss.platform.QueryLoadingProgress.LoadingTimeOverStore')
	}, {
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fieldLabel: '建立任务时间',//建立任务时间
		fieldId: 'Foss_platform_queryloadingprogress_QueryLoadingProgressForm_createTaskDate_Id',
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
			text: '重置',//重置
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
			text: '查询',//查询
			handler: function() {
				/**
				 * 复原分页参数，当点击查询按钮时，默认重新分页
				 */
				platform.queryLoadingProgress.initStart = 0;
				platform.queryLoadingProgress.pageSize = 5;
				platform.queryLoadingProgress.sequence = 'DESC';
				platform.queryLoadingProgress.executeQuery();
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
platform.queryLoadingProgress.executeQuery = function(){
	/**
	 * 判断查询时间段
	 */
	var beginDate = platform.queryLoadingProgressForm.getValues().createTaskDateFrom;
	var endDate = platform.queryLoadingProgressForm.getValues().createTaskDateTo;
	var difDate = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(beginDate,'Y-m-d H:i:s');
	var difTime = 2*24*60*60*1000-parseInt(difDate);
	
	var timeOver = platform.queryLoadingProgressForm.getValues().timeOver;
	var taskState = platform.queryLoadingProgressForm.getValues().taskState;
	
	if(platform.queryLoadingProgressForm.getForm().isValid()){
		//一天之内的数据
		if(difTime>=0){
			var queryParams = platform.queryLoadingProgressForm.getValues();
			Ext.Ajax.request({
				url : platform.realPath('queryLoadingProgressInfo.action'),
				//获取当前行的运单号
				params : {
					'loadingProgressVo.loadingProgressConditionDto.taskNumber':queryParams.taskNumber,
					'loadingProgressVo.loadingProgressConditionDto.arrivedDept':queryParams.arrivedDept,
					'loadingProgressVo.loadingProgressConditionDto.vehicleNumber':queryParams.vehicleNumber,
					'loadingProgressVo.loadingProgressConditionDto.taskState':queryParams.taskState,
					'loadingProgressVo.loadingProgressConditionDto.taskType':queryParams.taskType,
					'loadingProgressVo.loadingProgressConditionDto.timeOver':queryParams.timeOver,
					'loadingProgressVo.loadingProgressConditionDto.createTaskDateFrom':queryParams.createTaskDateFrom,
					'loadingProgressVo.loadingProgressConditionDto.createTaskDateTo':queryParams.createTaskDateTo,
					'loadingProgressVo.loadingProgressConditionDto.sequence':platform.queryLoadingProgress.sequence,
					'loadingProgressVo.loadingProgressConditionDto.sequenceType':platform.queryLoadingProgress.sequenceType,
					'loadingProgressVo.initStart':platform.queryLoadingProgress.initStart,
					'loadingProgressVo.pageSize':platform.queryLoadingProgress.pageSize
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					//取得返回结果条数，同设置的页面大小进行判断
					var length = result.loadingProgressVo.loadingProgressResultList.length;
					//当前大小
					platform.queryLoadingProgress.currentPageSize = length;
					if(length==0){
						Ext.ux.Toast.msg('提示', '未查询到符合条件的装车进度数据！','success', 5000);
						//提示 、未查询到符合条件的装车进度数据！
					}else{
						/**
						 * 如果取的数据小于设置的页面大小，
						 * 表明已经没数据了，
						 * 故下一页按钮可以设置不可用，
						 * （可以用其他更好的方式代替）
						 */
						if(platform.queryLoadingProgress.currentPageSize<platform.queryLoadingProgress.pageSize || result.loadingProgressVo.count==(platform.queryLoadingProgress.initStart+platform.queryLoadingProgress.pageSize)){
							Ext.getCmp('Foss_platform_QueryLoadingProgress_SortButtonContainer_downButton').setDisabled(true);	
						}else{
							Ext.getCmp('Foss_platform_QueryLoadingProgress_SortButtonContainer_downButton').setDisabled(false);	
						}
					}
					//加载view对象
					platform.loadingprogressView.store.loadData(result.loadingProgressVo.loadingProgressResultList);
					/**
					 * 遍历查询结果，没循环一次，产生一个view，
					 * 整个view用单据编号区分，
					 * 即如果单据编号相同，view会重叠，谨记
					 */
					platform.loadingprogressView.getStore().each(function(record){
						var progressContainer = Ext.create('Foss.platform.QueryLoadingProgress.ProgressContainer',{
							renderTo: 'queryLoadingProgress_'+record.get('taskNumber')+'_progress'
						});
						var loadingInfoForm = Ext.create('Foss.platform.QueryLoadingProgress.LoadingInfoForm',{
							renderTo: 'queryLoadingProgress_'+record.get('taskNumber')+'_taskInfo'
						});
						progressContainer.setProgress(record,new Date());					
						loadingInfoForm.bindData(record);
					});
				
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
		            Ext.Msg.alert('错误', result.message); //错误 
				}
			})
		}else{
			Ext.Msg.alert('警告', '时间跨度不能超过两天！');
		}//警告、时间跨度不能超过2天				
	}else{
		Ext.Msg.alert('警告', '请输入查询条件！');
	}//警告 、请输入查询条件！
	
};

//定义一个排序按钮的组件
Ext.define('Foss.platform.QueryLoadingProgress.SortButtonContainer', {
	extend: 'Ext.container.Container',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	border : false,
	layout:'column',
	defaults:{
		columnWidth:.08,
		cls : 'btnAfterTextfield',
		margin : '0 2 0 0'
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'button',
			text: '按已装体积',//按已装体积
			handler: function() {
				/**
				 * 点击排序按钮时，将需要排序的类型放在全局变量中，
				 * 后台会根据排序类型进行处理，将排序类型和方式进行组合
				 * 如：ORDER_BY_CREATETASKDATE_DESC
				 */
				//platform.queryLoadingProgress.executeQuery(byVolume)
				platform.queryLoadingProgress.sequenceType = 'ORDER_BY_LOADEDVOLUME';
				platform.queryLoadingProgress.sequence = 'DESC';
				platform.queryLoadingProgress.executeQuery();	
			}
		},{
			xtype : 'button',
			text:  '按已装重量',//按已装重量
			handler: function() {
				platform.queryLoadingProgress.sequenceType = 'ORDER_BY_LOADEDWEIGHT';
				platform.queryLoadingProgress.sequence = 'DESC';
				platform.queryLoadingProgress.executeQuery();
			}
		},{
			xtype : 'button',
			columnWidth : .09,
			text: '建立任务时间',//建立任务时间
			handler: function() {
				platform.queryLoadingProgress.sequenceType = 'ORDER_BY_CREATETASKDATE';
				platform.queryLoadingProgress.sequence = 'DESC';
				platform.queryLoadingProgress.executeQuery();
			}
		},{
			xtype : 'button',
			columnWidth : .09,
			text:'规定发车时间',//规定发车时间
			handler: function() {
				platform.queryLoadingProgress.sequenceType = 'ORDER_BY_PLANDEPARTDATE';
				platform.queryLoadingProgress.sequence = 'DESC';
				platform.queryLoadingProgress.executeQuery();
			}
		},{
			border : false,
			columnWidth:.19,
			html: '&nbsp;'
		},{
			columnWidth:.1,
			xtype : 'button',
			text: '上一页',//上一页
			columnWidth : .06,
			id:'Foss_platform_QueryLoadingProgress_SortButtonContainer_upButton',
			handler: function() {
				/**
				 * 如果起始页大于0，则需要减去设置的页面大小（应该是当前页的大小，没数据测试）
				 * 
				 */
				if(platform.queryLoadingProgress.initStart>0){
					platform.queryLoadingProgress.initStart = platform.queryLoadingProgress.initStart - platform.queryLoadingProgress.pageSize;
					platform.queryLoadingProgress.sequence = 'DESC';
					platform.queryLoadingProgress.executeQuery();
				}else{
					
				}
			}
		},{
			columnWidth:.1,
			xtype : 'button',
			text: '下一页',//下一页
			columnWidth : .06,
			id:'Foss_platform_QueryLoadingProgress_SortButtonContainer_downButton',
			handler: function() {
				/**
				 * 如果当前页大于0，则起始页需加上设置的页面大小，此处不是当前页的大小
				 */
				if(platform.queryLoadingProgress.currentPageSize>0){
					platform.queryLoadingProgress.initStart = platform.queryLoadingProgress.initStart + platform.queryLoadingProgress.pageSize;
					platform.queryLoadingProgress.sequence = 'DESC';
					platform.queryLoadingProgress.executeQuery();
				}
				
			}
		},{
			border : false,
			columnWidth:.19,
			html: '&nbsp;'
		},{
			xtype : 'button',
			text: '<div style="float:left;margin:2px 0 0 10px">'+'由大到小'+'</div><div style="margin-left:60px;"class="deppon_icons_dsc"></div>',//由大到小
			handler: function() {
				platform.queryLoadingProgress.sequence = 'DESC';
				platform.queryLoadingProgress.executeQuery();
			}
		},{
			xtype : 'button',
			text: '<div style="float:left;margin:2px 0 0 10px">'+'由小到大'+'</div><div style="margin-left:60px;"class="deppon_icons_asc"></div>',//由小到大
			handler: function() {
				platform.queryLoadingProgress.sequence = 'ASC';
				platform.queryLoadingProgress.executeQuery();
			}
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.platform.QueryLoadingProgress.LoadingProgressModel', {
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
					return '长途装车';//长途装车
				} else if(value == 'SHORT_DISTANCE_LOAD'){
					return '短途装车';//短途装车
				}else if(value == 'DELIVER_LOAD'){
					return '派送装车';//派送装车
				}else if(value == 'LDP_LOAD'){
					return '快递代理装车';//落地配装车
				}else{
					return '偏线装车';//偏线装车
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
		//已装体积
		{name: 'loadedVolume', type: 'string'},
		//已装重量
		{name: 'loadedWeight', type: 'string'},
		//到达部门
		{name: 'arrivedDept', type: 'string'},
		//月台号
		{name: 'platform', type: 'string'},
		//装车人数
		{name: 'loadMember', type: 'string'},
		//任务状态
		{name: 'taskState', type: 'string'},
		{name: 'volumeProgress', type: 'string'},
		{name: 'weightProgress', type: 'string'},
		//装车效率
		{name: 'loadEfficiency', type: 'string'}
	]
});

Ext.define('Foss.platform.QueryLoadingProgress.LoadingProgressStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.platform.QueryLoadingProgress.LoadingProgressModel',
	
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
Ext.define('Foss.platform.QueryLoadingProgress.ProgressContainer', {
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
		//任务状态
		var taskState = record.get('taskState');
		
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
		
		if(taskState == 'LOADING'){
			if(weightprogress<0.8){
				this.getWeightProgress().addCls('below80');
			}else if(weightprogress>=0.8&&weightprogress<0.9){
				this.getWeightProgress().addCls('over80');
			}else if(weightprogress>=0.9){
				this.getWeightProgress().addCls('over90');
			}
			
			if(volumeProgress<0.8){
				this.getVolumeProgress().addCls('below80');
			}else if(volumeProgress>=0.8&&volumeProgress<0.9){
				this.getVolumeProgress().addCls('over80');
			}else if(volumeProgress>=0.9){
				this.getVolumeProgress().addCls('over90');
			}
		}
		
		this.getWeightProgress().updateProgress(weightprogress,record.get('weightProgress'));
		this.getVolumeProgress().updateProgress(volumeProgress,record.get('volumeProgress'));
		
		/**
		 * 若任务未完成且剩余时间<0,则该任务超时，如果任务超时，需要用红色框框包起来
		 * 若任务未完成，则剩余时间=规定发车时间-当前时间
		 * 若任务已完成，则不显示剩余时间
		 * 若任务已完成且装车完成时间>规定发车时间，则任务超时，超时任务用红色标示出来
		 */
		var leafTime=record.get('arrivedDate')-nowDate;
		
		if(taskState == 'FINISHED' || taskState == 'SUBMITED'){
			this.getWeightProgress().addCls('complete');
			this.getVolumeProgress().addCls('complete');
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
			html: '<span>'+'体积进度'+'</span>'//体积进度：
		},me.getVolumeProgress(),{
			border : false,
			columnWidth:.2,
			html: '<span>'+'重量进度'+'</span>'//重量进度
		},me.getWeightProgress()];
		me.callParent([cfg]);
	}
});

//装车信息的表单
Ext.define('Foss.platform.QueryLoadingProgress.LoadingInfoForm', {
	extend: 'Ext.form.Panel',
	height: 70,
	defaults: {
		margin: '5 5 0 5',
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
		fieldLabel: '线路',//线路
		columnWidth: .2
	}, {
		name: 'arrivedDept',
		fieldLabel: '到达部门',//到达部门
		columnWidth: .16
	}, {
		name: 'ratedVolume',
		fieldLabel: '额定净空',//额定净空
		columnWidth: .16
	}, {
		name: 'ratedWeight',
		fieldLabel: '额定载重',//额定载重
		columnWidth: .16
	}, {
		name: 'taskType',
		fieldLabel: '装车类型',//装车类型
		columnWidth: .16
	}, {
		name: 'vehicleNumber',
		fieldLabel: '车牌号',//车牌号
		columnWidth: .16
	}, {
		name: 'platform',
		fieldLabel: '月台号',//月台号
		columnWidth: .2
	}, {
		name: 'loadMember',
		fieldLabel: '理货员',//理货员
		columnWidth: .16
	}, {
		name: 'loadedVolume',
		fieldLabel: '已装体积',//已装体积
		columnWidth: .16
	}, {
		name: 'loadedWeight',
		fieldLabel: '已装重量',//已装重量
		columnWidth: .16
	}, {
		name: 'tallyMember',
		fieldLabel: '装车人数',//装车人数
		columnWidth: .16
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义一个线路信息的表格
Ext.define('Foss.platform.QueryLoadingProgress.LoadingProgressView', {
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
		me.store = Ext.create('Foss.platform.QueryLoadingProgress.LoadingProgressStore');
		me.itemSelector = 'div.foss_queryLoadingProgress_thumb_wrap',
		me.tpl = new Ext.XTemplate(
			'<tpl for=".">',
				'<div class="foss_queryLoadingProgress_thumb_wrap">',
					'<h2><span style="text-align:left;">'+'装车任务编号{taskNumber}' + '</span>' + '{[this.changeLoadEfficiencyColor(values.loadEfficiency)]}' + '</h2>',//装车任务编号：
					'<div id="foss_queryLoadingProgress_{taskNumber}_loading" class="foss_queryLoadingProgress_search_task">',
						'<div class="foss_queryLoadingProgress_top_task">',
							'<div class="foss_queryLoadingProgress_time_task">',
							'<div class="foss_icons_tfr_taskTime"></div>'+'建立任务时间'+'<br/>{createTaskDate}',//建立任务时间
							'</div>',
							'<div class="foss_queryLoadingProgress_dline">---------</div>',
							'<div id="queryLoadingProgress_{taskNumber}_progress" class="foss_queryLoadingProgress_loading"></div>',
							'<div class="foss_queryLoadingProgress_time_complete"><span>'+'装车完成时间'+'</span><br/>{[this.getCompleteDate(values.completeDate)]}</div>',//装车完成时间
							'<div class="foss_queryLoadingProgress_time_surplus"><span>'+'剩余时间'+'</span><br/>{[this.getLeftTime(values.planDepartDate,values, out)]}</div>',//剩余时间
							'<div class="foss_queryLoadingProgress_dline">---------</div>',
							'<div class="foss_queryLoadingProgress_time_task">',
							'<div class="foss_icons_tfr_departTime"></div>'+'规定发车时间'+'<br/>{[this.getPlanTime(values.planDepartDate)]}</span>',//规定发车时间
							'</div>',
						'</div>',
						'<div id="queryLoadingProgress_{taskNumber}_taskInfo" class="foss_queryLoadingProgress_task_content foss_queryLoadingProgress_clear"></div>',
					'</div>',
				'</div>',
			'</tpl>',
			{
				//装车效率变色
				changeLoadEfficiencyColor : function(value){
					if(value == null || value == ''){
						value = 0;
					}
					if(value > 12){
						value = '<span style="float:right;color:red;font-weight:bold">装车效率：'+value+'min/T</span>';
					}else{
						value = '<span style="float:right">装车效率：'+value+'min/T</span>';
					}
					return value;
				},
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
						var leftTime = h+'小时'+m+'分';//小时 、 分
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
Ext.define('Foss.platform.QueryLoadingProgress.TaskExplainContainer', {
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
			html: '装车体积或重量进度超过80%'
			//装车体积或重量进度超过80%
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-over90'
		},{
			columnWidth:.21,
			html: '装车体积或重量超过90%'
			//装车体积或重量进度超过90%
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-below80'
		},{
			columnWidth:.21,
			html: '装车体积或重量进度低于80%'
			//装车体积或重量进度低于80%
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-notopen'
		},{
			columnWidth:.12,
			html: '任务未开始记录'
			//任务未开始记录
		},{
			xtype: 'image', 
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-complete'
		},{
			columnWidth:.12,
			html: '任务已完成记录'
			//任务已完成记录
		},{
			xtype: 'image', 
			margin: '0 2 0 0',
			border : true,
			cls: 'foss_queryLoadingProgress_task-explain-base foss_queryLoadingProgress_task-explain-timeover'
		},{
			columnWidth:.11,
			html: '任务超时'
			//任务超时
		}];
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryLoadingProgressForm = Ext.create('Foss.platform.QueryLoadingProgress.QueryLoadingProgressForm'); //查询表单
	platform.queryLoadingProgressForm = queryLoadingProgressForm; //加入全局变更
	var sortButtons = Ext.create('Foss.platform.QueryLoadingProgress.SortButtonContainer'); //排序按钮
	var loadingprogressView = Ext.create('Foss.platform.QueryLoadingProgress.LoadingProgressView'); //线路信息表格
	platform.loadingprogressView = loadingprogressView; //加入全局变更
	/**
	 * 设置排序方式，分页数据
	 */
	Ext.apply(platform.queryLoadingProgress,{
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
	var taskExplain = Ext.create('Foss.platform.QueryLoadingProgress.TaskExplainContainer'); //说明
	Ext.create('Ext.panel.Panel',{
		id: 'T_platform-queryLoadingProgressIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [ queryLoadingProgressForm, sortButtons,loadingprogressView,taskExplain],
		renderTo: 'T_platform-queryLoadingProgressIndex-body'
	});
});