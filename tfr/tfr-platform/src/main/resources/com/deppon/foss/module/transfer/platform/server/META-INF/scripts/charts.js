Ext.define('Foss.forecastQuantity.showChartDtoModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'totalWeight',//总重量
		type : 'float'
	},{
		name : 'totalVolume',//总体积
		type : 'float'
	},{
		name : 'statisticsDate',//预测发起日期
		type : 'date',
		convert: dateConvert
	},{
		name : 'statisticsHHMM',//预测发起小时分钟
		type : 'string'
	},{
		name : 'realWeight',//实际重量
		type : 'float'
	},{
		name : 'realVolume',//实际体积
		type : 'float'
	},{
		name : 'warnWeight',//警戒重量
		type : 'float'
	},{
		name : 'warnVolume',//警戒体积
		type : 'float'
	}]
});

Ext.define('Foss.forecastQuantity.OperatePanel',{
	extend:'Ext.form.Panel',
	layout:'column',
	align : 'center',
	queryByVolume: Ext.emptyFn,
	queryByWeight: Ext.emptyFn,
	countMenssage: null,
	getCountMenssage: function(){
		if(this.countMenssage==null){
			this.countMenssage = Ext.create('Ext.container.Container', {
				columnWidth:.37,
				layout: 'card',
				activeItem: 0,
				items: [{
					xtype: 'container',
					layout: 'column',
					items: [{
						xtype: 'container',
						columnWidth: 0.79,
						html: '&nbsp;'
					},{
						xtype:'container',
						columnWidth: .08,
						margin:'14 5 0 0',
						minHeight:5,
						maxHeight:5,
						style: 'background-color:Yellow;'
					},{
						xtype:'label',
						text:platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.warnLine'),
						padding:'10 0 0 0',
						columnWidth: .13
					}]
				},{
					xtype: 'container',
					html: '&nbsp;'
				}]
			});
		}
		return this.countMenssage;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype: 'container',
			columnWidth:.04,
			html: '&nbsp;'
		},{
			xtype:'label',
			text:platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.showDay'),
			padding:'9 0 0 0',
			html:'&nbsp;&nbsp;',
			columnWidth:.1
			
		},{
			xtype: 'numberfield',
			padding: '5 5 5 5',
			name: 'dayNumber',
			columnWidth: .05
		},{
			xtype:'label',
			text:platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.day'),
			padding:'10 0 0 0',
			columnWidth:.03
			
		},{
			xtype:'button',
			text:platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.asVolume'),
			columnWidth:.07,
			maxHeight:27,
			handler:function(){
				me.queryByVolume();
			}
		},{
			xtype: 'container',
			columnWidth:.01,
			maxHeight:27,
			minHeight:27,
			html: '&nbsp;'
		},{
			xtype:'button',
			text:platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.asWeight'),
			columnWidth:.07,
			maxHeight:27,
			handler:function(){
				me.queryByWeight();
			}
		},me.getCountMenssage(),{
			xtype:'container',
			columnWidth:.03,
			margin:'14 5 0 0',
			minHeight:5,
			maxHeight:5,
			style: 'background-color:Green;'
		},{
			xtype:'label',
			text:platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.realNumber'),
			padding:'10 0 0 0',
			columnWidth:.05
			
		},{
			xtype:'container',
			columnWidth:.03,
			minHeight:15,
			maxHeight:15,
			margin:'10 5 0 0',
			style: 'background-color:#999999;'
		},{
			xtype:'label',
			text:platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.forecastGoods'),
			padding:'10 0 0 0',
			columnWidth:.08
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.LineGoodCharts.OperatePanel',{
	extend:'Foss.forecastQuantity.OperatePanel',
	queryByVolume: function(){
		var win = this.up('window'),
			record = win.getCurrentRecord();
		this.buttonHandler({
			total: 'totalVolume',
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.volumeUnit'),
			real: 'realVolume',
			isCount: record.get('relevantOrgCode')?false:true,
			warn: 'warnVolume'
		});
	},
	queryByWeight: function(){
		var win = this.up('window'),
			record = win.getCurrentRecord();
		this.buttonHandler({
			total: 'totalWeight',
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.weightUnit'),
			real: 'realWeight',
			isCount: record.get('relevantOrgCode')?false:true,
			warn: 'warnWeight'
		});
	},
	buttonHandler: function(param){
		var win = this.up('window'),
			charts = win.query('chart'),
			form = this.getForm(),
			currentRecord = win.getCurrentRecord(),
			dayNumber = form.findField('dayNumber').getValue();
		if(dayNumber == null){
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'),platform.forecastQuantity.i18n('foss.platform.forecastQuantity.notEmpty'));
			return;
		}
		if(dayNumber%1!=0){
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'),platform.forecastQuantity.i18n('foss.platform.forecastQuantity.onlyInt'));
			return;
		}
		if(dayNumber>7||dayNumber<0) {
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'),platform.forecastQuantity.i18n('foss.platform.forecastQuantity.zeroToSeven'));
			return;
		}
		for(var i=0;i<charts.length;i++){
			win.remove(charts[i],true);
		}
		for(var i=0; i<=dayNumber; i++) {
			if(i==0){
				param.lineView = false;
			}else{
				param.lineView = true;
			}
			var item = null,
				statisticsDate = currentRecord.get('statisticsDate'),
				statisticsDate = new Date(statisticsDate.getTime()-24*3600*1000*i);
				forecastTime = currentRecord.get('forecastTime'),
				forecastTime = new Date(forecastTime.getTime()-24*3600*1000*i);
			param.statisticsDate = statisticsDate;
			var	item = Ext.create('Foss.forecastQuantity.LineGood.Chart',param);
			if(i==0){
				item.series.items[0].listeners = {
					'itemdblclick': function(src, event) {
						var currentRecord = win.getCurrentRecord(),
							hourGoodChartWin = item.getHourGoodChartWin();
						hourGoodChartWin.bindData(src.storeItem,currentRecord);
						hourGoodChartWin.show();
					}
				}
			}
			win.add(item);
			item.getStore().load({
				params : {
					'forecastVO.forecastQuantityEntity.type': currentRecord.get('type'),
					'forecastVO.forecastQuantityEntity.relevantOrgCode': currentRecord.get('relevantOrgCode'),
					'forecastVO.forecastQuantityEntity.forecastTime': forecastTime,
					'forecastVO.forecastQuantityEntity.statisticsDate': statisticsDate,
					'forecastVO.forecastQuantityEntity.belongOrgCode' : currentRecord.get('belongOrgCode')
				}
			});
		}
	}
});

Ext.define('Foss.forecastQuantity.LineGoodStore', {
	extend: 'Ext.data.Store',
	model : 'Foss.forecastQuantity.showChartDtoModel',
	proxy : {
		type : 'ajax',
		actionMethods: 'POST',
		url : platform.realPath('showChartSpecPath.action'),
		reader : {
			type : 'json',
			root : 'forecastVO.showChartDto'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.CountLineGoodStore', {
	extend: 'Ext.data.Store',
	model : 'Foss.forecastQuantity.showChartDtoModel',
	proxy : {
		type : 'ajax',
		actionMethods: 'POST',
		url : platform.realPath('showChartAllPath.action'),
		reader : {
			type : 'json',
			root : 'forecastVO.showChartDto'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.LineGood.Chart',{
	extend: 'Ext.chart.Chart',
	width: 980,
	height: 350,
	mask: 'horizontal',
	// 标注动画效果
	animate: {
	    duration: 1000
	},
	shadow: true,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config),
			total = config.total,
			title = config.title,
			real = config.real,
			warn = config.warn,
			lineView = config.lineView,
			isCount = config.isCount,
			statisticsDate = config.statisticsDate;
		if(isCount){
			me.store = Ext.create('Foss.forecastQuantity.CountLineGoodStore');
			me.theme = 'Category4'
		}else{
			me.store = Ext.create('Foss.forecastQuantity.LineGoodStore');	
			me.theme = 'Green'
		}
		me.axes = [ {
			// 坐标的类型
			type: 'Numeric',
			// 坐标的位置
			position: 'left',
			// 坐标上面显示的值 和图标实际值没有关系
			fields: [ total ],
			// 坐标的title
			title: title,
			// 网格的轴线
			grid: true,
			// 坐标最小值
			minimum: 0,
			//maximum: 1000,
			// 图标上面显示值的方式
			label: {
				renderer: Ext.util.Format.numberRenderer('0,0'),
				font: '10px Arial'
			}
		}, {
			type: 'Category',
			position: 'bottom',
			fields: [ 'statisticsHHMM' ],
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.time')+'('+Ext.Date.format(statisticsDate, 'Y-m-d')+')',
			label: {
				font: '11px Arial',
				renderer: function(value) {
					if(Ext.isEmpty(value)){
						return;
					}
					return value.substring(0,2)+':'+value.substring(2,4);
				}
			}
		}];
		me.series = [{
			//图标的类型 
			type: 'column',
			// 相对那个坐标做参照
			axis: 'left',
			// 鼠标移动到上面 高亮显示
			highlight: true,
			gutter: 60,
			// 鼠标移动到图标上时提示文本
			tips: {
				trackMouse: true,
				width: 120,
				height: 28,
				renderer: function(storeItem, item) {
					var value = storeItem.get('statisticsHHMM'),
						title = value.substring(0,2)+':'+value.substring(2,4);
					this.setTitle(title + ' -- ' + storeItem.get(total));
				}
			},
			renderer : function(sprite, storeItem, src, i, store) {
				//更改柱子的背景颜色
				src.fill = '#999999';
				return src;
			},
			//x坐标绑定的值, 此值应该在axes设置的坐标范围中
			xField: 'statisticsHHMM',
			//y坐标绑定的值
			yField: total
		}];
		if(isCount){
			me.series.push({
				// 图标的类型
				type: 'line',
				axis: 'left',
				xField: 'statisticsHHMM',
				yField: warn,
				tips: {
					trackMouse: true,
					width: 110,
					height: 25,
					renderer: function(storeItem, item) {
						this.setTitle(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.warnLine') + storeItem.get(warn));
					}
				},
				highlight: {
                    size: 7,
                    radius: 7
                },
				style: {
                    fill: 'Yellow',
                    stroke: 'Yellow',
                    'stroke-width': 50
                },
                markerConfig: {
                    type: 'circle',
                    'stroke-width': 0,
                    fill: 'Yellow',
                    stroke: 'Yellow'
                }
			});
		}
		if(lineView){
			me.series.push({
				// 图标的类型
				type: 'line',
				axis: 'left',
				xField: 'statisticsHHMM',
				yField: real,
				tips: {
					trackMouse: true,
					width: 110,
					height: 25,
					renderer: function(storeItem, item) {
						this.setTitle(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.actual') + storeItem.get(real));
					}
				},
				highlight: {
                    size: 7,
                    radius: 7
                },
				markerConfig: {
					type: 'circle',
                    'stroke-width': 0,
                    fill: 'Green',
                    stroke: 'Green'
				},
				style: {
                    fill: 'Green',
                    stroke: 'Green',
                    'stroke-width': 50
                }
			});
		}
		me.callParent([cfg]);
	},
	hourGoodChartWin: null,
	getHourGoodChartWin: function() {
		var me = this;
		if (me.hourGoodChartWin==null) {
			me.hourGoodChartWin = Ext.create("Foss.forecastQuantity.HourGoodChartWindow");
		}
		return me.hourGoodChartWin;
	}
});

Ext.define('Foss.forecastQuantity.LineGoodChartsWindow',{
	extend:'Ext.window.Window',
	width: 1024,
	height: 420,
	closeAction: 'hide',
	// 自动增加滚动条
	autoScroll : true,
	modal : true,
	padding:'2px 0px 0px 5px',
	currentRecord: null,
	getCurrentRecord: function(){
		return this.currentRecord;
	},
	operatePanel : null,
	getOperatePanel: function() {
		if(this.operatePanel == null) {
			this.operatePanel = Ext.create('Foss.forecastQuantity.LineGoodCharts.OperatePanel');
		}
		return this.operatePanel;
	},
	goodChart : null,
	getGoodChart: function() {
		return this.goodChart;
	},
	bindData: function(record){
		var me = this,charts = me.query('chart'),title = '';
		if(record.get('relevantOrgCode')){
			title = record.get('belongOrgCodeName') + "-" +record.get('relevantOrgCodeName') +' '+record.get('rawValue')+ platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.lineChart');
			me.getOperatePanel().getCountMenssage().getLayout().setActiveItem(1);
		}else{
			title = record.get('rawValue')+ platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.totalChart');
			me.getOperatePanel().getCountMenssage().getLayout().setActiveItem(0);
		}
		me.getOperatePanel().getForm().findField('dayNumber').setValue(0);
		me.setTitle(title);
		for(var i=0;i<charts.length;i++){
			me.remove(charts[i],true);
		}
		me.currentRecord = record;
		me.goodChart = Ext.create('Foss.forecastQuantity.LineGood.Chart',{
			total: 'totalWeight',
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.weightUnit'),
			real: 'realWeight',
			isCount: record.get('relevantOrgCode')?false:true,
			statisticsDate: record.get('statisticsDate'),
			warn: 'warnWeight'
		});
		me.getGoodChart().series.items[0].listeners = {
			'itemdblclick': function(src, event) {
				var currentRecord = me.getCurrentRecord(),
					hourGoodChartWin = me.getGoodChart().getHourGoodChartWin();
				hourGoodChartWin.bindData(src.storeItem,currentRecord);
				hourGoodChartWin.show();
			}
		};
		me.getGoodChart().getStore().load({
			params : {
				'forecastVO.forecastQuantityEntity.type': record.get('type'),
				'forecastVO.forecastQuantityEntity.relevantOrgCode': record.get('relevantOrgCode'),
				'forecastVO.forecastQuantityEntity.forecastTime': record.get('forecastTime'),
				'forecastVO.forecastQuantityEntity.statisticsDate': record.get('statisticsDate'),
				'forecastVO.forecastQuantityEntity.belongOrgCode' : record.get('belongOrgCode')
			}
		});
		me.add(me.goodChart);
	},
	constructor: function(config) {
		var me = this;
		Ext.apply(this, config);
		me.items = [
			me.getOperatePanel()
		];
		me.callParent(arguments);
	}
});




Ext.define('Foss.forecastQuantity.HourGoodChart.OperatePanel',{
	extend:'Foss.forecastQuantity.OperatePanel',
	queryByVolume: function(){
		this.buttonHandler({
			total: 'totalVolume',
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.volumeUnit'),
			real: 'realVolume'
		});
	},
	queryByWeight: function(){
		this.buttonHandler({
			total: 'totalWeight',
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.weightUnit'),
			real: 'realWeight'
		});
	},
	buttonHandler: function(param){
		var window = this.up('window'),
			charts = window.query('chart'),
			form = this.getForm(),
			currentRecord = window.getCurrentRecord(),
			record = window.getRecord(),
			dayNumber = form.findField('dayNumber').getValue();
		if(dayNumber==null) {
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'),platform.forecastQuantity.i18n('foss.platform.forecastQuantity.notEmpty'));
			return;
		}
		if(dayNumber%1!=0){
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'),platform.forecastQuantity.i18n('foss.platform.forecastQuantity.onlyInt'));
			return;
		}
		if(dayNumber>7||dayNumber<0) {
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'),platform.forecastQuantity.i18n('foss.platform.forecastQuantity.zeroToSeven'));
			return;
		}
		for(var i=0;i<charts.length;i++){
			window.remove(charts[i],true);
		}
		param.statisticsDate = currentRecord.get('statisticsDate');
		var item = item = Ext.create('Foss.forecastQuantity.HourGoodChart.Chart',param);
		window.add(item);
		item.getStore().load({
			params : {
				'forecastVO.forecastQuantityEntity.type': currentRecord.get('type'),
				'forecastVO.day': dayNumber,
				'forecastVO.forecastQuantityEntity.relevantOrgCode': currentRecord.get('relevantOrgCode'),
				'forecastVO.forecastQuantityEntity.forecastTime': currentRecord.get('forecastTime'),
				'forecastVO.forecastQuantityEntity.statisticsDate': currentRecord.get('statisticsDate'),
				'forecastVO.forecastQuantityEntity.statisticsHHMM': record.get('statisticsHHMM'),
				'forecastVO.forecastQuantityEntity.belongOrgCode' : currentRecord.get('belongOrgCode')
			}
		});
	}
});

Ext.define('Foss.forecastQuantity.HourGoodVolumeStore', {
	extend: 'Ext.data.Store',
	model : 'Foss.forecastQuantity.showChartDtoModel',
	proxy : {
		type : 'ajax',
		actionMethods: 'POST',
		url : platform.realPath('showChartSpecHour.action'),
		reader : {
			type : 'json',
			root : 'forecastVO.showChartDto'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.HourGoodChart.Chart',{
	extend:'Ext.chart.Chart',
	theme: 'Green',
	width: 980,
	height: 350,
	//标注动画效果
	animate: {
	    duration: 1000
	},
	shadow: true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.forecastQuantity.HourGoodVolumeStore');
		var total = config.total,
			title = config.title,
			real = config.real,
			lineView = config.lineView;
		me.axes = [ {
			// 坐标的类型
			type: 'Numeric',
			// 坐标的位置
			position: 'left',
			// 坐标上面显示的值 和图标实际值没有关系
			fields: [ total ],
			// 坐标的title
			title: title,
			// 网格的轴线
			grid: true,
			// 坐标最小值
			minimum: 0,
			//maximum: 1000,
			// 图标上面显示值的方式
			label: {
				renderer: Ext.util.Format.numberRenderer('0,0'),
				font: '10px Arial'
			}
		}, {
			type: 'Category',
			position: 'bottom',
			fields: [ 'statisticsDate' ],
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.time'),
			label: {
				font: '11px Arial',
				renderer: function(value) {
					if(Ext.isEmpty(value)){
						return;
					}
					return Ext.Date.format(value, 'Y-m-d');
				}
			}
		}];
		me.series = [{
			// 图标的类型
			type: 'line',
			axis: 'left',
			xField: 'statisticsDate',
			yField: real,
			tips: {
				trackMouse: true,
				width: 110,
				height: 25,
				renderer: function(storeItem, item) {
					this.setTitle(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.actual') + storeItem.get(real) + platform.forecastQuantity.i18n('foss.platform.forecastQuantity.weightUnit'));
				}
			},
			highlight: {
                    size: 7,
                    radius: 7
                },
				style: {
                    fill: 'Yellow',
                    stroke: 'Yellow',
                    'stroke-width': 50
                },
                markerConfig: {
                    type: 'circle',
                    'stroke-width': 0,
                    fill: 'Green',
                    stroke: 'Green'
                }
		},{
			//图标的类型 
			type: 'column',
			//相对那个坐标做参照
			axis: 'left',
			//鼠标移动到上面 高亮显示
			highlight: true,
			gutter: 60,
			//鼠标移动到图标上时提示文本
			tips: {
				trackMouse: true,
				width: 100,
				height: 28,
				renderer: function(storeItem, item) {
					//提示文本
					this.setTitle(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.forecast') + storeItem.get(total) + platform.forecastQuantity.i18n('foss.platform.forecastQuantity.weightUnit'));
				}
			},
			renderer: function(sprite, storeItem, src, i, store) {
				//更改柱子的背景颜色
				src.fill = '#999999';
				return src;
			},
			//x坐标绑定的值, 此值应该在axes设置的坐标范围中
			xField: 'statisticsDate',
			//y坐标绑定的值
			yField: total
		}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.HourGoodChartWindow',{
	extend:'Ext.window.Window',
	width: 1024,
	height: 420,
	closeAction: 'hide',
	// 自动增加滚动条
	autoScroll : true,
	modal : true,
	padding:'2px 0px 0px 5px',
	currentRecord: null,
	getCurrentRecord: function(){
		return this.currentRecord;
	},
	record: null,
	getRecord: function(){
		return this.currentRecord;
	},
	operatePanel : null,
	getOperatePanel: function() {
		if(this.operatePanel == null) {
			this.operatePanel = Ext.create('Foss.forecastQuantity.HourGoodChart.OperatePanel');
		}
		return this.operatePanel;
	},
	hourGoodChart : null,
	bindData: function(record,currentRecord){
		var value = record.get('statisticsHHMM').toString(),
			title = value.substring(0,2)+':'+value.substring(2,4),
			me = this,charts = me.query('chart');
		me.setTitle(title+ ' ' +currentRecord.get('rawValue') + platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.goodsChart'));
		me.getOperatePanel().getCountMenssage().getLayout().setActiveItem(1);
		me.getOperatePanel().getForm().findField('dayNumber').setValue(7);
		for(var i=0;i<charts.length;i++){
			me.remove(charts[i],true);
		}
		currentRecord.set('statisticsHHMM',record.get('statisticsHHMM'));
		me.currentRecord = currentRecord;
		me.record = record;
		me.hourGoodChart = Ext.create('Foss.forecastQuantity.HourGoodChart.Chart',{
			total: 'totalWeight',
			title: platform.forecastQuantity.i18n('foss.platform.forecastQuantity.charts.weightUnit'),
			real: 'realWeight'	
		});
		me.hourGoodChart.getStore().load({
			params : {
				'forecastVO.forecastQuantityEntity.type': currentRecord.get('type'),
				'forecastVO.day': 7,
				'forecastVO.forecastQuantityEntity.relevantOrgCode': currentRecord.get('relevantOrgCode'),
				'forecastVO.forecastQuantityEntity.forecastTime': currentRecord.get('forecastTime'),
				'forecastVO.forecastQuantityEntity.statisticsDate': currentRecord.get('statisticsDate'),
				'forecastVO.forecastQuantityEntity.statisticsHHMM': record.get('statisticsHHMM'),
				'forecastVO.forecastQuantityEntity.belongOrgCode' : currentRecord.get('belongOrgCode')
			}
		});
		me.add(me.hourGoodChart);
	},
	constructor: function(config) {
		var me = this;
		Ext.apply(me, config);
		me.items = [
			me.getOperatePanel()
		];
		me.callParent(arguments);
	}
});
