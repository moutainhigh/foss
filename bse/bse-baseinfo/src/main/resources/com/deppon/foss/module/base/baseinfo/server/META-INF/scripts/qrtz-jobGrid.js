Ext.define('Foss.qrtz.jobMonitorModel',{
		extend: 'Ext.data.Model',
		fields: [
			{name: 'id', type: 'string'},
			{name: 'jobModule', type: 'string'},			//所属模块
			{name: 'jobName', type: 'string'},			//任务名
			{name: 'jobDesc', type: 'String'},			//任务描述
			{name: 'warningReceiver', type: 'string'},	//对应预警消息接收人
			{name: 'maxAccumulation', type: 'number'},				//阀值
			{name: 'querySql', type: 'string'},				//查询sql
			{name: 'active', type: 'string'},			//是否生效
		    {name:'dataAccumulation',type:'number'},//数据积累量
		    {name:'createDate',type:'string'} //创建日期
			]
	});
	Ext.define('Foss.qrtz.jobMonitorStore', {
		extend: 'Ext.data.Store',
		pageSize: 15,
		model: 'Foss.qrtz.jobMonitorModel',
		proxy: {
			type: 'ajax',
			actionMethods:'post',
			url:'../baseinfo/queryJobDataAccumulationByJobGrids.action',
			reader: {
				type: 'json',
				root: 'jobDataAccumulationVo.jobMonitorList',
				totalProperty: 'totalCount'
			}
		}
	});
Ext.define('Foss.baseinfo.qrtzDataAccumulationRecordModel',{
            extend:'Ext.data.Model',
            fields:[
            {name:'id',type:'string'},
            {name:'jobGroup',type:'string'},
            {name:'blongModule',type:'string'},
            {name:'jobName',type:'string'},
            {name:'dataAccumulation',type:'number'},
            {name:'statDate',type:'string'},
            {name:'latestrecord',type:'number'}
            ]
})  ;
Ext.define('Foss.baseinfo.qrtzDataAccumulationStore',{
			extend:'Ext.data.Store',
			model:'Foss.baseinfo.qrtzDataAccumulationRecordModel',
			proxy:{
				type:'ajax',
				actionMethods:'post',
				url:'../baseinfo/queryJobDataAccumulationRecord.action',
				reader: {
					type: 'json',
					root: 'jobDataAccumulationVo.jobDataAccumulationRecord',
					totalProperty: 'totalCount'
			}
		}
		
});
Ext.define('Foss.baseinfo.qrtzEarylWarnWin', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	title : 'job预警',
	//modal : true,
	layout : 'fit',
	width: 1000,
    height: 600,
    minHeight: 400,
    minWidth: 550,
    hidden: false,
    maximizable: true,
	addChart : null,
	getAddChart : function() {
		if (this.addChart == null) {
			this.addChart = Ext.create('Foss.baseinfo.qrtzEarylWarnChart');
		}
		return this.addChart;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var qrtzEarylWarnChart = me.getAddChart();
		me.tbar=[{
            text: '保存图表',
            handler: function() {
                Ext.MessageBox.confirm('提示', '确认保存图表?', function(choice){
                    if(choice == 'yes'){
                    	
                        qrtzEarylWarnChart.save({
                            type: 'image/png'
                        });
                    }
                });
            }
        }, {
            text: '重新加载',
            handler: function() {
                qrtzEarylWarnChart.store.load();
            }
        }],
		me.items = [ qrtzEarylWarnChart ];
		me.listeners = {
			beforehide : function(me) {
			},
			show : function() {

			}
		};
		
		me.callParent([ cfg ]);
	}
});   
Ext.define('Foss.baseinfo.qrtzEarylWarnChart', {
	      	extend: 'Ext.chart.Chart',
            style: 'background:#fff',
           // animate: true,
            store: null,
            shadow: true,
            theme: 'Category2',
            legend: {
                position: 'right'
            },
            axes: [{
                type: 'Numeric',
                minimum: 0,
                position: 'left',
                fields: ['dataAccumulation', 'latestrecord'],
                title: '未处理数据量',
                minorTickSteps: 1,
                grid: {
                    odd: {
                        opacity: 1,
                        fill: '#ddd',
                        stroke: '#bbb',
                        'stroke-width': 0.5
                    }
                }
            }, {
                type: 'Category',
                position: 'bottom',
                 label: {
               	 rotate: { degrees: 45}
               },
                fields: ['statDate'],
                title: '统计日期'
            }],
            series: [{
                type: 'line',
                title : '历史数据',
                highlight: {
                    size: 7,
                    radius: 7
                },
                axis: 'left',
                xField: 'statDate',
                yField: 'dataAccumulation',
                markerConfig: {
                    type: 'cross',
                    size: 4,
                    radius: 4,
                    'stroke-width': 0
                }
            }, {
            	title:'当前数据',
                type: 'line',
                highlight: {
                    size: 7,
                    radius: 7
                },
                axis: 'left',
                smooth: true,
                xField: 'statDate',
                yField: 'latestrecord',
                markerConfig: {
                    type: 'circle',
                    size: 4,
                    radius: 4,
                    'stroke-width': 0
                }
            }],
              constructor : function(config) {
        		var me = this, cfg = Ext.apply({}, config); 
        		me.store = Ext.create('Foss.baseinfo.qrtzDataAccumulationStore', {
        			autoLoad: false
        			});
        		 me.callParent([ cfg ]);
            }
        }); 
 Ext.define('Foss.baseinfo.qrtzModel',{
		extend: 'Ext.data.Model',
		fields: [
			{name: 'id', type: 'string'},
			{name: 'triggerName', type: 'string'},			//触发器名称
			{name: 'triggerGroup', type: 'string'},			//触发器分组
			{name: 'triggerType', type: 'number'},			//触发器类型
			{name: 'triggerExpression', type: 'string'},	//触发器表达式
			{name: 'jobName', type: 'string'},				//任务名称
			{name: 'jobGroup', type: 'string'},				//任务分组
			{name: 'jobClass', type: 'string'},				//任务类
			{name: 'jobData', type: 'string'},				//任务数据
			{name: 'jobDesc', type: 'string'},				//任务描述
			{name: 'active', type: 'number'},			//调度状态
			{name:'dataAccumulation',type:'number'},//未处理数据量
			{name:'blongModule',type:'string'},//job所属模块
			{name: 'instanceId', type: 'string'}//任务分组所属实例
		]
	});

	Ext.define('Foss.baseinfo.qrtzLogModel',{
		extend: 'Ext.data.Model',
		fields: [
			{name: 'id', type: 'string'},
			{name: 'triggerName', type: 'string'},
			{name: 'triggerGroup', type: 'string'},
			{name: 'jobName', type: 'string'},
			{name: 'jobGroup', type: 'string'},
			{name: 'errorMessage', type: 'string'},
			{name: 'startTime', type: 'string' },
			{name: 'endTime', type: 'string' },
			{name:'blongModule',type:'string'}//job所属模块
		]
	});
	//任务群信息
	Ext.define('Foss.qrtzJobGrid.JobGridCluModel',{
		extend: 'Ext.data.Model',
		fields: [
				{name: 'instanceId', type: 'string'},
				{name: 'scopeType', type: 'number'},			
				{name: 'scopeName', type: 'string'},				
				{name: 'accessRule', type: 'number'},
				{name:'blongModule',type:'string'}
		]
	});

	Ext.define('Foss.baseinfo.qrtzStore', {
		extend: 'Ext.data.Store',
		pageSize: 10,
		model: 'Foss.baseinfo.qrtzModel',
		proxy: {
			type: 'ajax',
			actionMethods:'post',
			url:'../baseinfo/queryJobGridSchedules.action',
			reader: {
				type: 'json',
				root: 'jobGridVo.jobGridSchedulesEntityList',
				totalProperty: 'totalCount'
			}
		}
	});
	/**
	 * 查询数据未处理量
	 */
  Ext.define('Foss.baseinfo.qrtzDataAccumulationStore', {
		extend: 'Ext.data.Store',
		pageSize: 10,
		model: 'Foss.baseinfo.qrtzModel',
		proxy: {
			type: 'ajax',
			actionMethods:'post',
			url:'../baseinfo/queryJobDataAccumulationByJobGrids.action',
			reader: {
				type: 'json',
				root: 'jobDataAccumulationVo.jobGridSchedulesEntityList',
				totalProperty: 'totalCount'
			}
		}
	});
	Ext.define('Foss.baseinfo.qrtzLogStore', {
		extend: 'Ext.data.Store',
		model: 'Foss.baseinfo.qrtzLogModel',
		proxy: {
			type: 'ajax',
			actionMethods: 'post',
			url: '../baseinfo/queryJobGridLoggings.action',
			reader: {
				type: 'json',
				root: 'jobGridVo.jobGridLoggingsEntityList',
				totalProperty: 'totalCount'
			}
		}
	});
//新加
	var colors = ['url(#v-1)',
    'url(#v-2)',
    'url(#v-3)'
    ];

var baseColor = '#000000';

Ext.define('Ext.chart.theme.Fancy', {
extend: 'Ext.chart.theme.Base',

constructor: function(config) {
	this.callParent([Ext.apply({
		axis: {
			fill: baseColor,
			stroke: baseColor
		},
		axisLabelLeft: {
			fill: baseColor
		},
		axisLabelBottom: {
			fill: baseColor
		},
		axisTitleLeft: {
			fill: baseColor
		},
		axisTitleBottom: {
			fill: baseColor
		},
  colors: colors
}, config)]);
}
});

baseinfo.qrtz.jobMonitorStore = new Ext.create('Foss.qrtz.jobMonitorStore',{
				autoLoad:true,
				 listeners: {
				 }
});
baseinfo.qrtz.jobDataAccumulationStore = Ext.create('Foss.baseinfo.qrtzDataAccumulationStore',{
      autoLoad: false
     
});
baseinfo.qrtz.jobStore = Ext.create('Foss.baseinfo.qrtzStore', {
	autoLoad: false,
	//pageSize: 10,
	listeners: {
		load: function(store, operation, eOpts){
			Ext.apply(operation, {
					params : store.getGroups()
				});
				var condition ={};
				 var i =0;
	  	 store.each(function (record) {  
              var jobGroup =  record.get('jobGroup');
              var jobName = record.get('jobName');
              var gr = "jobDataAccumulationVo.jobGridSchedulesEntityList["+i+"].jobGroup";
              var jn = "jobDataAccumulationVo.jobGridSchedulesEntityList["+i+"].jobName";
              condition[gr] = jobGroup;
               condition[jn] = jobName;
              i++;
          });
	  	    if(i==0)return;
              Ext.apply(baseinfo.qrtz.jobDataAccumulationStore.proxy.extraParams,condition);
	   			baseinfo.qrtz.jobDataAccumulationStore.load();
		},
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('T_baseinfo-jobqrtz_content').down('form').getForm(),
				entity,blongModule;
			queryForm.updateRecord(queryForm.record);
			entity = queryForm.record.data;
			var jobStore =	queryForm.findField('jobGroup').store;
			if(Ext.isEmpty(entity.jobGroup)||entity.jobGroup==""){
				 blongModule = entity.blongModule;
			}else{
				for (var i = 0; i < jobStore.getCount(); i++) {  //getCount() 方法 获取 数据集 的长度
                var scopeName = jobStore.getAt(i).get('scopeName');  //遍历数据集，获取 id为 scopeName 的数据
               if(entity.jobGroup==scopeName){
                 blongModule =jobStore.getAt(i).get('blongModule');
                 break;
               }
          }
			}
			if(queryForm != null) {
				Ext.apply(operation, {
					params: {
						'jobGridVo.jobGridSchedulesEntity.jobGroup': entity.jobGroup,
						'jobGridVo.jobGridSchedulesEntity.jobName': entity.jobName,
						'jobGridVo.jobGridSchedulesEntity.triggerName': entity.triggerName,
						'jobGridVo.jobGridSchedulesEntity.blongModule': blongModule,
						'jobGridVo.jobGridSchedulesEntity.active': entity.active
					}
				});	
			}
		}
    }
});
/** 任务群信息store */
Ext.define('Foss.commonSelector.JobGridCluStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.qrtzJobGrid.JobGridCluModel',
			//pageSize : 10,
			proxy : {
				type : 'ajax',
				url : '../baseinfo/queryJobGridCluSelector.action',
				actionMethods : 'POST',// 否则可能会出现中文乱码
				reader : {
					type : 'json',
					root : 'jobGridVo.jobGridClusterEntityList',
					totalProperty : 'totalCount'
				}
			}
});

/** 任务群单选公共选择器 */
Ext.define('Foss.commonSelector.JobGridCluSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.jobGridCluselector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'scopeName',// 显示名称
	valueField : 'scopeName',// 值
	queryParam : 'jobGridVo.selectorParam',// 查询参数
	showContent : '{scopeName}&nbsp;&nbsp;&nbsp;{scopeTypeName}',// 显示表格列
	blongModule:'jobGridVo.jobGridClusterEntity.blongModule',//任务所属模块blongModule
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.commonSelector.JobGridCluStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
		});
		me.callParent([cfg]);
	}
});
/** 任务名单选公共选择器 */
Ext.define('Foss.commonSelector.JobNameSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.jobNameselector',
	listWidth : 400,// 设置下拉框宽度
	displayField : 'jobName',// 显示名称
	valueField : 'jobName',// 值
	myActive:null,
	queryParam : 'jobGridVo.jobGridSchedulesEntity.jobName',// 查询参数
	showContent : '{jobName}&nbsp;&nbsp;&nbsp;{jobDesc}',// 显示表格列
	store:null,
	blongModule:'jobGridVo.jobGridSchedulesEntity.blongModule',//任务所属模块blongModule
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.qrtzStore',{
		  		//	autoLoad:false
			
		});
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
		});
		me.callParent([cfg]);
	}
});
//新增或编辑任务群
Ext.define('Foss.monitor.jobGrid.JobGridCluForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.jobgridCluform',
	defaultType: 'textfield',
	initComponent: function() {
		var me = this;
		me.items = [
{
	name : 'blongModule',
	xtype : 'combobox',
	fieldLabel : '任务模块',
	queryMode : 'local',
	displayField : 'name',
	allowBlank: false,
	valueField : 'code',
	editable:false,
	value:'PKP',
	//margin : '10 0 0 10',
	// value : 'distance',
/*	store:FossDataDictionary.getDataDictionaryStore('DELIVERY_NATURE',null,{
		'valueCode':'',
		'valueName':baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.all')
	}*/
	store : Ext.create('Ext.data.Store', {
		// 定义字段
		fields : [ {
				name : 'code',
				type : 'string'
				}, {
					name : 'name',
					type : 'string'
				} ],
				//目前 综合，中转的定时任务放在对应的bse,tfr表空间下，结算 接送货的放在dpap下
				data : {
					'items' : [ {
						"code" : "BSE",
						"name" : '综合' // 
					}, {
						"code" : "TFR",
						"name" : '中转' // 
					}, {
						"code" : "PKP",
						"name" : '接送货' //
					}, {
						"code" : "STL",
						"name" : '结算' //
					} ]
				},
				proxy : {
					type : 'memory',
					reader : {
						type : 'json',
						root : 'items'
					}
					}
				})
			},    
		      {
				fieldLabel:'任务节点',//'任务节点',
				allowBlank: false,
				name: 'instanceId'
			}, {
				fieldLabel:'判定类型',//'判定类型',
				allowBlank: false,
				name: 'scopeType',
				xtype: 'combobox',
				store: {
					xtype: 'store',
					fields: ['key', 'value'],
				    data: [{'key': '1', 'value':'任务组'},
				           {'key': '2', 'value':'任务名'}
				           ]
				},
				queryMode: 'local',
			    editable: false,
			    displayField: 'value',
			    valueField: 'key'
			},{
				fieldLabel:'任务组'+'/'+'任务名',
				allowBlank: false,
				name: 'scopeName'
			}, {
				fieldLabel:'规则判定',
				name: 'accessRule',
				allowBlank: false,
				xtype: 'combobox',
				store: {
					xtype: 'store',
					fields: ['key', 'value'],
				    data: [{'key': '1', 'value':'允许执行'},
				           {'key': '2', 'value':'不允许执行'}
				           ]
				},
				queryMode: 'local',
			    editable: false,
			    displayField: 'value',
			    valueField: 'key'	
		}];
		me.callParent();
	}
});
Ext.define('Foss.monitor.jobGrid.JobGridCluWin', {
	extend: 'Ext.window.Window',
	alias: 'wigdet.jobgridcluwin',
	width: 350,
	height: 260,
	closable: true,
	resizable: false,
	closeAction: 'hide',
	modal: true,
	layout: 'fit',
	operationUrl: null,
	jobGridFormCluMode: null,
	initComponent: function() {
		var me = this;
		me.items = [{
			xtype: 'jobgridCluform'
		}],
		me.buttons = [{
			text: '取消',//'取消',
			handler: function() {
				 var form = me.down('form').getForm();
				 form.reset();
				 me.close();
			}
		},{
			text: '保存',//'保存',
			cls:'yellow_button',
			handler: me.onSaveRecord,
			scope: me
		}];
		me.callParent();
	},
	getJobGridCluFormModel: function() {
		if (null == this.jobGridFormCluMode) {
			this.jobGridFormCluMode = Ext.create("Foss.qrtzJobGrid.JobGridCluModel");
		}
		return this.jobGridFormCluMode;
	},
	onSaveRecord: function() {
		var me = this,
			form = me.down('form'),
			jobGridCluForm = form.getForm();
		if (jobGridCluForm.isValid()) {
			jobGridCluForm.updateRecord(me.getJobGridCluFormModel());
			Ext.Ajax.request({
				url: '../baseinfo/addJobGridCluSchedule.action',
				jsonData: {
					'jobGridVo': {
						'jobGridClusterEntity': me.getJobGridCluFormModel().data
					}
				},
				success: function(response) {
					Ext.ux.Toast.msg(
							'提示', 
							'保存成功');
					jobGridCluForm.reset();
					me.hide();
				},
				exception : function(response) {
	            	var json = Ext.decode(response.responseText); 
		        	Ext.ux.Toast.msg(
		        			'错误', 
		        			json.message, 'error');
	            }

			});
		}
	}
});
Ext.define('Foss.qrtz.JobGridChart', {
	extend: 'Ext.chart.Chart',
	      //alias: 'widget.jobgridChart',
           // xtype: 'chart',
            style: 'background:#fff',
            animate: true,
            shadow: true,
            store:baseinfo.qrtz.jobMonitorStore,
    		theme: 'Fancy',
           /* animate: {
                easing: 'bounceOut',
                duration: 750
            },*/
    		
            gradients: [ {
                'id': 'v-1',
                'angle': 0,
                stops: {
                    0: {
                        color: '#373C64'
                    },
                    100: {
                        color: '#373C64'
                    }
                }
                //373C64
            },
            {
                'id': 'v-2',
                'angle': 0,
                stops: {
                    0: {
                        color: '#373C64'
                    },
                    100: {
                        color: '#373C64'
                    }
                }
            },
            {
                'id': 'v-3',
                stops: {
                    0: {
                        color: '#E73400'
                    },
                    100: {
                        color: '#E73400'
                    }
                }
            }],
            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['dataAccumulation'],
                label: {
                	 rotate: { degrees: 45, angle: 45},
                    renderer: Ext.util.Format.numberRenderer('0,0')
                },
                title: '未处理数据量',
                minimum: 0,
                //maximum:6000
            }, {
                type: 'Category',
                position: 'bottom',
                //设置轴下文本的显示的角度
				label:{
				rotate: {
				degrees: 45
 					}
       	  },
                fields: ['jobDesc'],
                title: '定时任务名称',
                fixedSteps: [5, 10,20]
            }],
            series: [{
            	style: { width: 30 },//这里是宽度
                type: 'column',
                axis: 'left',
                gutter: 80,
                xField: 'jobDesc',
                yField: ['dataAccumulation'],
                //设置柱状图颜色
                renderer: function(sprite, storeItem, barAttr, i, store) {
                   // barAttr.fill = colors[i % colors.length];
                   barAttr.fill=colors[1];
                    var maxAccumulation =storeItem.get('maxAccumulation');
		   			 var dataAccumulation = storeItem.get('dataAccumulation');
		   			 if(maxAccumulation>dataAccumulation){
		   			   barAttr.fill=colors[1];
		   			   }else{
		   			 	  barAttr.fill=colors[2];
		   			   }
                    return barAttr;
                    
                },
                label: {
                    contrast: true,
                    display :  'outside' ,//标签显现方式  
                   // display: 'insideEnd',
                    field: 'dataAccumulation',
                    color: '#000',
                    orientation: 'vertical',
                    'text-anchor': 'inherit'
                },
                tips: {
                    trackMouse: true,
                    width: 274,
                    height: 138,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('jobName'));
                        var jobDesc ="任务描述:"+storeItem.get('jobDesc');
		    			 var maxAccumulation ="预警阀值:"+storeItem.get('maxAccumulation');
		   			   var dataAccumulation = "当前未处理量:"+storeItem.get('dataAccumulation');
                        this.update(jobDesc+'<br/>'+maxAccumulation+'<br/>'+dataAccumulation);
                    }
                },
                listeners: {
                    'itemmouseup': function(item) {
                    	 //alert("sdf");
                        // var series = this.series.get(0),
                          //   qrtz = Ext.Array.qrtzOf(series.items, item),
                          //   selectionModel = gridPanel.getSelectionModel();
                          // selectedStoreItem = item.storeItem;
                         //  selectionModel.select(qrtz);
                    }
                },
                style: {
                    fill: '#38B8BF'
                },
                initComponent: function() {
		var me = this;
		me.dockedItems = [ {
			xtype : 'standardpaging',
			store : me.store,
			dock : 'bottom',
			plugins : {
				ptype : 'pagesizeplugin',
				// 超出输入最大限制是否提示，true则提示，默认不提示
				alertOperation : true,
				// 自定义分页comobo数据
				sizeArray : [ [ '10', 10 ], [ '20', 20 ] ],
				// 入最大限制，默认为200
				maximumSize : 10
			}
		} ]
		me.callParent(arguments);
	}
            }]
           
        });

           

Ext.define('Foss.qrtz.JobGridQueryChartWindow', {
	extend: 'Ext.panel.Panel',
	//alias: 'widget.jobgridqueryChart',
    width: 1031,
    height: 700,
    //minHeight: 400,
    //minWidth: 550,
    hidden: false,
    store:baseinfo.qrtz.jobMonitorStore,
  // maximizable: true,
  	title: '定时任务数据未处理量',
    layout : 'fit', // 如果为'form'，则竖着显示，如果'column',则横着显示  fit
    JobGridChart:null,
    items:null,
    getJobGridChart: function() {
		if(this.JobGridChart == null) {
			this.JobGridChart = Ext.create('Foss.qrtz.JobGridChart');
		}
		return this.JobGridChart;
	},
    tbar: null,
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config); 
    	 me.items= me.getJobGridChart() ;
    	 me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
			baseinfo.qrtz.pagingBar = me.bbar;
    	 me.tbar = [{
             text: '保存图表',
             handler: function() {
                 Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as an image?', function(choice){
                     if(choice == 'yes'){
                    	 me.getJobGridChart().save({
                             type: 'image/png'
                         });
                     }
                 });
             }
         }, {
        	 text: '综合',
             handler: function() {
            	 var  condition ={
               			'jobDataAccumulationVo.blongModule':'BSE'
               	};
             	  Ext.apply(baseinfo.qrtz.jobMonitorStore.proxy.extraParams,condition);
             	  baseinfo.qrtz.jobMonitorStore.removeAll();
             	  baseinfo.qrtz.pagingBar.moveFirst();
             	  //baseinfo.qrtz.jobMonitorStore.load();
             }
         },
             {
        	 text: '中转',
             handler: function() {
            	 var  condition ={
              			'jobDataAccumulationVo.blongModule':'TFR',
              			'jobDataAccumulationVo.jobGridSchedulesEntity.jobName': null
              	};
             	  Ext.apply(baseinfo.qrtz.jobMonitorStore.proxy.extraParams,condition);
             	  baseinfo.qrtz.jobMonitorStore.removeAll();
             	baseinfo.qrtz.pagingBar.moveFirst();
             	  // baseinfo.qrtz.jobMonitorStore.load();
             }
             },
             {
            	 text: '接送货',
            	 handler: function() {
            		 var  condition ={
                 			'jobDataAccumulationVo.blongModule':'PKP',
                 				'jobDataAccumulationVo.jobGridSchedulesEntity.jobName': null
                 	};
            	  Ext.apply(baseinfo.qrtz.jobMonitorStore.proxy.extraParams,condition);
            	  baseinfo.qrtz.jobMonitorStore.removeAll();
            	baseinfo.qrtz.pagingBar.moveFirst();
            	  //  baseinfo.qrtz.jobMonitorStore.load();
            	 }
             },
            {
                text: '结算',
                 handler: function() {
                	var  condition ={
                			'jobDataAccumulationVo.blongModule':'STL',
                    		'jobDataAccumulationVo.jobGridSchedulesEntity.jobName': null

                			
                	};
                      Ext.apply(baseinfo.qrtz.jobMonitorStore.proxy.extraParams,condition);
                      baseinfo.qrtz.jobMonitorStore.removeAll();
                   	baseinfo.qrtz.pagingBar.moveFirst();
                    // baseinfo.qrtz.jobMonitorStore.load();
               }
         },  {
        	 xtype :'textfield',
             name:'jobName',
             id:"queryJobName",
             margin:'0 0 0 520'	 

         },
         {
             text: '查询',
             margin:'0 0 0 20'	, 
             handler: function() {
            	var  condition ={
            			'jobDataAccumulationVo.blongModule':null,
            			'jobDataAccumulationVo.jobGridSchedulesEntity.jobName':  Ext.getCmp("queryJobName").getValue()
            			
            	};
            	
                  Ext.apply(baseinfo.qrtz.jobMonitorStore.proxy.extraParams,condition);
                  baseinfo.qrtz.jobMonitorStore.removeAll();
               	baseinfo.qrtz.pagingBar.moveFirst();
                // baseinfo.qrtz.jobMonitorStore.load();
           }
     }];
    	 me.callParent([ cfg ]);
   }
    //items: getJobGridChart()    ,
});
/**
 *	任务管理
 *	2013/03/22 15:10
 */
baseinfo.qrtz.formatDefaultDate = function(isBegin) {
	var nowDate = new Date();
	if(isBegin) {
		nowDate.setHours(0);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
	} else {
		nowDate.setHours(23);
		nowDate.setSeconds(59);
		nowDate.setMinutes(59);
	}
	return nowDate;
};
baseinfo.qrtz.regs = {
	quartz: /^([\d*/\-,?]+\s){5}([\d*\-?#A-Z]+)$|^([\d*/\-,?A-Z]+\s){6}([\d*/\-]+)$/
};
//查询表单
Ext.define('Foss.baseinfo.qrtzQueryForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.jobgridqueryform',
	frame: true,
	title: '查询条件',
	autoScroll: true,
	collapsible: true,
	defaults: {
		width:300
		//lableWidth: 60,
		//margin:'0 0 0 0'
	},
	defaultType: 'textfield',
	layout: {
	    type: 'column'
	},
	initComponent: function() {
		var me = this;
		me.items = [
			{
				name : 'blongModule',
				xtype : 'combobox',
				fieldLabel : '任务模块',
				queryMode : 'local',
				displayField : 'name',
				valueField : 'code',
				value:'ALL',
				editable:false,
				columnWidth: .2,
				lableWidth: 60,
				listeners:{
					'blur' : function(ths, the,eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change':function(ths,the, eOpts) {
							var jobGroup = this.up('form').getForm().findField('jobGroup').clearValue();
						}
					},
				store : Ext.create('Ext.data.Store', {
					// 定义字段
					fields : [ {
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					} ],
					//目前 综合，中转的定时任务放在对应的bse,tfr表空间下，结算 接送货的放在dpap下
					data : {
						'items' : [
						     {
							"code" : "ALL",
							"name" : '全部' // 
						},
						{
							"code" : "BSE",
							"name" : '综合' // 
						}, {
							"code" : "TFR",
							"name" : '中转' // 
						}, {
							"code" : "PKP",
							"name" : '接送货' //
						}, {
						"code" : "STL",
						"name" : '结算' //
					} ]
					},
					proxy : {
						type : 'memory',
						reader : {
							type : 'json',
							root : 'items'
						}
					}
				})
			},
				{
			xtype:'jobGridCluselector',
			fieldLabel: '任务分组',
			name: 'jobGroup',
			queryMode: 'local', //查询模式： 默认‘remote
			columnWidth: .2,
			labelWidth : 60,
			listeners:{
					expand:function(_this,eOpts){
						var blongModule = _this.up('form').getForm().findField('blongModule').getValue();
						_this.store.load({
							params:{
								'jobGridVo.jobGridClusterEntity.blongModule':blongModule
							}
						});
					}
				}	
		},{
		//	xtype:'textfield',
			xtype:'jobNameselector',
			fieldLabel: '任务名称',
			name: 'jobName',
			columnWidth: .3,
			lableWidth: 60,
			queryMode: 'local', 
			listeners:{
				expand:function(_this,eOpts){
					var blongModule = _this.up('form').getForm().findField('blongModule').getValue();
					var jobGroup = _this.up('form').getForm().findField('jobGroup').getValue();
					_this.store.load({
						params:{
							'jobGridVo.jobGridSchedulesEntity.blongModule':blongModule,
							'jobGridVo.jobGridSchedulesEntity.jobGroup':jobGroup
						}
					});
				}
			}	
		}, /*{
			xtype:'textfield',
			fieldLabel: '触发器名称',
			name: 'triggerName',
			columnWidth: .2,
			lableWidth: 60
		},*/{
				name : 'active',
				xtype : 'combobox',
				fieldLabel : '任务状态',
				queryMode : 'local',
				displayField : 'name',
				valueField : 'code',
				editable:false,
				columnWidth: .2,
				lableWidth: 60,
				value:1,
				store : Ext.create('Ext.data.Store', {
					// 定义字段
					fields : [ {
						name : 'code',
						type : 'number'
					}, {
						name : 'name',
						type : 'string'
					} ],
					//目前 综合，中转的定时任务放在对应的bse,tfr表空间下，结算 接送货的放在dpap下
					data : {
						'items' : [
						     {
							"code" : 1,
							"name" : '启动中' // 
						},
						{
							"code" : 0,
							"name" : '停止中' // 
						},{
							"code" : 3,
							"name" : '全部' // 
						}]
					},
					proxy : {
						type : 'memory',
						reader : {
							type : 'json',
							root : 'items'
						}
					}
				})
			}, {
			
						xtype: 'button',
						text: '重置',
						cls: 'yellow_button',
						handler: me.onReset,
						scope: me,
						width: 75
					}, 
					{
						xtype: 'button',
						text: '查询',
						//margin:'20 40 0 0' ,
						cls: 'yellow_button',
						plugins: {
							ptype: 'buttonlimitingplugin',
							seconds: 4
						},
						handler: me.onQuery,
						scope: me,
						width: 75
					}]
			
		me.callParent();
	},
	onQuery: function() {
		if(this.getForm().isValid()) {
			//var grid = Ext.getCmp('T_qrtz-qrtz_content').getJobGridList();
			//baseinfo.qrtz.chartWindow.getJobGridChart().store.loadPage(1);
			//grid.store.loadPage(1);
			baseinfo.qrtz.jobStore.loadPage(1);
			baseinfo.qrtz.chartWindow.show();
		} else {
			Ext.Msg.alert('请检查查询条件正确性！');
		}
	},
	onReset: function() {
		this.getForm().reset();
	}
});

//新增编辑
Ext.define('Foss.baseinfo.qrtzForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.jobgridform',
	defaultType: 'fieldset',
	initComponent: function() {
		var me = this;
		me.items = [{
			title: '触发器信息',
			defaultType: 'textfield',
			layout: {
				type: 'table',
				columns: 2
			},
			defaults: {
				labelWidth: 80,
				width: 300,
				allowBlank: false
			},
			items:[{
				fieldLabel: '触发器名称',
				name: 'triggerName'
			}, {
				fieldLabel: '触发器分组',
				name: 'triggerGroup'
			}, {
				fieldLabel: '触发器类型',
				name: 'triggerType',
				xtype: 'combobox',
				store: {
					xtype: 'store',
					fields: ['key', 'value'],
				    data: [{'key': '1', 'value': '表达式'}]
				},
				queryMode: 'local',
			    editable: false,
			    displayField: 'value',
			    valueField: 'key'
			}, {
				fieldLabel: '表达式',
				regex: baseinfo.qrtz.regs.quartz,
				regexText: '触发器表达式格式有误',
				name: 'triggerExpression'
			}]
		},{
			title: '任务信息',
			defaultType: 'textfield',
			layout: {
				type: 'table',
				columns: 2
			},
			defaults: {
				labelWidth: 80,
				allowBlank: false,
				width: 300
			},
			items: [{
				fieldLabel: '任务名称',
				name: 'jobName',
				colspan: '2'
			}, 
			{
				name : 'blongModule',
				xtype : 'combobox',
				fieldLabel : '任务模块',
				allowBlank: false,
				queryMode : 'local',
				displayField : 'name',
				valueField : 'code',
				editable:false,
				//margin : '10 0 0 10',
				// value : 'distance',
			/*	store:FossDataDictionary.getDataDictionaryStore('DELIVERY_NATURE',null,{
					'valueCode':'',
					'valueName':baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.all')
				}*/
				listeners:{
					'blur' : function(ths, the,eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change':function(ths,the, eOpts) {
							var jobGroup = this.up('form').getForm().findField('jobGroup').clearValue();
						}
					},
				store : Ext.create('Ext.data.Store', {
					// 定义字段
					fields : [ {
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					} ],
					//目前 综合，中转的定时任务放在对应的bse,tfr表空间下，结算 接送货的放在dpap下
					data : {
						'items' : [ {
							"code" : "BSE",
							"name" : '综合' // 
						}, {
							"code" : "TFR",
							"name" : '中转' // 
						}, {
							"code" : "PKP",
							"name" : '接送货' //
						}, {
							"code" : "STL",
							"name" : '结算' //
						} ]
					},
					proxy : {
						type : 'memory',
						reader : {
							type : 'json',
							root : 'items'
						}
					}
				})
			},
			{
				fieldLabel: '任务分组',
				xtype:'jobGridCluselector',
				name: 'jobGroup',
				queryMode : 'local',
				listeners:{
						expand:function(_this,eOpts){
							var blongModule = baseinfo.qrtz.queryGrid.jobGridWin.down('form').getForm().findField('blongModule').getValue();
							//var blongModule=this.up('form').getForm().findField('blongModule').getValue();
							_this.reset();
							if(Ext.isEmpty(blongModule)) {
								alert("请先选择任务所属模块");
							    me.getForm().findField('jobGroup').setValue('');
							}else{
							
							_this.store.load({
								params:{
									'jobGridVo.jobGridClusterEntity.blongModule':blongModule
								},
								callback: function(records, operation, success) {
							        // 对象 operation 包含
							        // 所有 load 操作的详细信息
							        console.log(records);
							    }
							});
						}
						}
					}	
			}, {
				fieldLabel: '任务类',
				width: 604,
				colspan: '2',
				name: 'jobClass'
			}, {
				fieldLabel: '任务参数',
				xtype: 'textarea',
				allowBlank: true,
				name: 'jobData'
			}, {
				fieldLabel: '任务描述',
				xtype: 'textarea',
				allowBlank: true,
				name: 'jobDesc'
			}]
		}];
		me.callParent();
	}
});

Ext.define('Foss.baseinfo.qrtzWin', {
	extend: 'Ext.window.Window',
	alias: 'wigdet.jobgridwin',
	title: '新增任务',
	width: 680,
	height: 450,
	closable: true,
	resizable: false,
	closeAction: 'hide',
	modal: true,
	layout: 'fit',
	operationUrl: null,
	jobGridFormMode: null,
	initComponent: function() {
		var me = this;
		me.items = [{
			xtype: 'jobgridform'
		}],
		me.buttons = [{
			text: '取消',
			handler: function() {
				me.close();
			}
		}, {
			text: '保存',
			handler: me.onSaveRecord,
			scope: me
		}]
		me.callParent();
	},
	getJobGridFormModel: function() {
		if (null == this.jobGridFormMode) {
			this.jobGridFormMode = Ext.create("Foss.baseinfo.qrtzModel");
		}
		return this.jobGridFormMode;
	},
	setOperationUrl: function(url) {
		this.operationUrl = url;
	},
	loadJobGridForm: function (record) {
		this.down('form').getForm().loadRecord(record);
		this.jobGridFormMode = record;
	},
	onSaveRecord: function() {
		var me = this,
			form = me.down('form'),
			jobGridForm = form.getForm(),node;
		if (jobGridForm.isValid()) {
			jobGridForm.updateRecord(me.getJobGridFormModel());
			var jobGroup = me.getJobGridFormModel().data.jobGroup;
			var store = form.getForm().findField('jobGroup').store;
			
			 if(me.title ==='新增任务信息'){
				 store.each(function(record){
				      var instanceId =  record.get('instanceId');
				      var scopeName = record.get('scopeName');
				     if(jobGroup===scopeName){
				    	 node = instanceId;
				     }
				});
				
			 }else{
				 node = me.getJobGridFormModel().data.instanceId;
			 }
			Ext.Ajax.request({
				
				url: '/'+node+me.operationUrl.toString(),
				jsonData: {
					'jobGridVo': {
						'jobGridSchedulesEntity': me.getJobGridFormModel().data
					}
				},
				success: function(response) {
					
					Ext.getCmp('T_baseinfo-jobqrtz_content').down('grid').store.load();
					Ext.MessageBox.show({
						title: "提示",
						msg: "保存成功！",
						buttons: Ext.Msg.OK,
						callback: function() {
							me.hide();
						},
						icon: Ext.MessageBox.INFO
					});
				},
			   exception : function(response) {
	            	var json = Ext.decode(response.responseText); 
		        	Ext.ux.Toast.msg('错误', json.message, 'error');
	            }
			});
		}
	}
});


//任务列表
Ext.define('Foss.baseinfo.qrtzList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.jobgridlist',
	title: '任务列表',
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	minHeight: 250,
	closeAction: 'hide',
	
	enableColumnHide: false,
	sortableColumns: false,
	viewConfig: {
        enableTextSelection: true
    },
	jobGridWin: null,
	jobGridLogWin : null,
	jobGridEarlyWarnWin:null,
	listeners: {
         selectionchange: function(model, records) {
             var json, name, i, l, items, series, fields;
             if (records[0]) {
            	 storeItem = records[0];
                 var name = storeItem.get('jobName'),
                 series = baseinfo.qrtz.chartWindow.getJobGridChart().series.get(0),
                 i, items, l;
                 series.highlight = true;
                 series.unHighlightItem();
                 series.cleanHighlights();
             for (i = 0, items = series.items, l = items.length; i < l; i++) {
                 if (name == items[i].storeItem.get('jobName')) {
                     selectedStoreItem = items[i].storeItem;
                     series.highlightItem(items[i]);
                     break;
                 }
             }
             series.highlight = false;
             }
         }
     },
	initComponent: function() {
		var me = this;
		me.columns =  [{
				xtype: 'actioncolumn',
				width: 60,
				align: 'center',
				text: '操作',
				items: [{
					iconCls: 'deppon_icons_edit',
	                tooltip: '编辑',
	                handler: me.onRowEditRecord,
	                scope: me
				}, {
					iconCls: 'deppon_icons_showdetail',
	                tooltip: '查看日志',
	                handler: me.onReviewLoggings,
	                scope: me
				}]
	    	},
	    	/*{
				xtype: 'actioncolumn',
				width: 60,
				align: 'center',
				text: 'job预警',
				items: [{
					iconCls: 'deppon_icons_showdetail',
	                tooltip: '查看图表',
	                handler: me.onReviewEarlyWarning,
	                scope: me
				}]
	    	},*/
			{ text: '触发器名称', width: 140, dataIndex: 'triggerName' },
			{ text: '触发器分组', width: 80, dataIndex: 'triggerGroup' },
			{ text: '触发器类型', width: 80, dataIndex: 'triggerType', renderer: me.onRenderType },
			{ text: '表达式', width: 100, dataIndex: 'triggerExpression' },
			{ text: '任务名称', width: 140, dataIndex: 'jobName' },
			{ text: '描述', width: 300, dataIndex: 'jobDesc' },
			{ text: '任务分组', width: 80, dataIndex: 'jobGroup' },
			{ text: '任务类', width: 400, dataIndex: 'jobClass' },
			{ text: '状态', width: 80, dataIndex: 'active', renderer: me.onRenderActive }
		];
		
		me.store = baseinfo.qrtz.jobStore;
		me.tbar = [{
			text: '新增',
			handler: me.onAddRecord,
			scope: this
		}, {
			text: '新增分组',
			handler: me.onAddcluRecord,
			scope: this
		}, "<font color=red>双击记录可查看任务日志</font>", "->", {
			text: '启动任务',
			handler: me.onStartTask,
			scope: this
		}, {
			text: '停止任务',
			handler: me.onStopTask,
			scope: this
		}];
		/*me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store
		});*/
		me.dockedItems = [ {
			xtype : 'standardpaging',
			store : me.store,
			dock : 'bottom',
			plugins : {
				ptype : 'pagesizeplugin',
				// 超出输入最大限制是否提示，true则提示，默认不提示
				alertOperation : true,
				// 自定义分页comobo数据
				sizeArray : [ [ '10', 10 ], [ '20', 20 ] ],
				// 入最大限制，默认为200
				maximumSize : 10
			}
		} ]
		me.on('itemdblclick', me.onRowDbClick, me);
		me.callParent(arguments);
	},
	onRowDbClick: function(view, record) {
		var me = this,
			jobName = record.get('jobName'),
			jobGroup = record.get('jobGroup'),
			win = me.getJobGridLogWin();
			logGird = win.down('grid');
		win.show();
		logGird.setBaseParams(jobName, jobGroup);
		logGird.store.loadPage(1);
	},
	onReviewLoggings: function(grid, rowqrtz, colqrtz) {
		var me = this,
			record = me.getStore().getAt(rowqrtz),
			jobName = record.get('jobName'),
			jobGroup = record.get('jobGroup'),
			blongModule = record.get('blongModule');
			win = me.getJobGridLogWin();
			logGird = win.down('grid');
		win.show();
		logGird.setBaseParams(jobName, jobGroup,blongModule);
		logGird.store.loadPage(1);
	},
	//job预警窗口
	onReviewEarlyWarning:function(grid, rowqrtz, colqrtz){
	      var me = this,
	      record = me.getStore().getAt(rowqrtz),
	      jobGroup = record.get('jobGroup');
	      jobName = record.get('jobName');
	      blongModule = record.get('blongModule');
	      var chart = me.getJobGridEarlyWarnWin().getAddChart();
	      var condition ={
	           'jobDataAccumulationVo.jobGridSchedulesEntity.jobGroup':jobGroup,
	            'jobDataAccumulationVo.jobGridSchedulesEntity.jobName':jobName,
	             'jobDataAccumulationVo.jobGridSchedulesEntity.blongModule':blongModule
	      };
	      //var grid = Ext.getCmp('T_base-agreement_content').getAgreementQueryGrid();
	    	Ext.apply(chart.store.proxy.extraParams,condition);
	    	chart.store.load();
	      me.getJobGridEarlyWarnWin().show();
	     
	},
	getOperationUrl: function(actionType) {
		var operationUrl = null;
		if (actionType === 'add') {
			operationUrl = '/baseinfo/addJobGridSchedule.action';
		} else if (actionType === 'update') {
			operationUrl = '/baseinfo/updateJobGridSchedule.action';
		}
		return operationUrl;
	},
	
	getJobGridWin: function(title, actionType,blongModule) {
		var me = this,
			jobGridForm;
		if (!me.jobGridWin) {
			me.jobGridWin = Ext.create('Foss.baseinfo.qrtzWin');
		}
		jobGridForm = me.jobGridWin.down('form').getForm()
		
		me.jobGridWin.setOperationUrl(me.getOperationUrl(actionType));
		me.jobGridWin.setTitle(title);
		if (actionType) {
			if (actionType === "add") {
				jobGridForm.findField('triggerName').setReadOnly(false);
				jobGridForm.findField('triggerGroup').setReadOnly(false);
				jobGridForm.findField('jobName').setReadOnly(false);
				jobGridForm.findField('jobGroup').setReadOnly(false);
				jobGridForm.findField('blongModule').setReadOnly(false);
				me.jobGridWin.down('form').getForm().reset();
			} else if (actionType === "update") {
				jobGridForm.findField('triggerName').setReadOnly(true);
				jobGridForm.findField('triggerGroup').setReadOnly(true);
				jobGridForm.findField('jobName').setReadOnly(true);
				jobGridForm.findField('jobGroup').setReadOnly(true);
				jobGridForm.findField('blongModule').setReadOnly(true);
			}
		}
		return me.jobGridWin;
	},
	getJobGridEarlyWarnWin:function(){
	    if(!this.jobGridEarlyWarnWin){
	       this.jobGridEarlyWarnWin =Ext.create('Foss.baseinfo.qrtzEarylWarnWin');
	    }
	    return this.jobGridEarlyWarnWin;
	},
	getJobGridLogWin: function() {
		if(!this.jobGridLogWin) {
			this.jobGridLogWin = Ext.create('Foss.baseinfo.qrtzLogWin');
		}
		return this.jobGridLogWin;
	},
	jobGridCluWin:null,
	getJobGridCluWin: function(title) {
		var me = this,
			jobGridCluForm;
		if (!me.jobGridCluWin) {
			me.jobGridCluWin = Ext.create('Foss.monitor.jobGrid.JobGridCluWin');
		}
		jobGridCluForm = me.jobGridCluWin.down('form').getForm()
		me.jobGridCluWin.setTitle(title);
		return me.jobGridCluWin;
	},
	onAddRecord: function() {
		this.getJobGridWin("新增任务信息", "add").show();
	},
	onAddcluRecord: function() {
		this.getJobGridCluWin("新建任务组").show();
	},
	onEditRecord: function() {
		var me = this,
			store = me.store,
			sm = me.getSelectionModel(),
			jobGridWin;
		if (sm.hasSelection()) {
			var data = sm.getSelection()[0];
			jobGridWin = me.getJobGridWin("编辑任务信息", "update");
			jobGridWin.loadJobGridForm(data);
			jobGridWin.show();
		} else {
			Ext.Msg.alert("提示", "请先选择一条任务信息。");
		}
	},
	onRowEditRecord: function(grid, rowqrtz, colqrtz) {
		var me = this,
			record = me.getStore().getAt(rowqrtz),
			jobGridWin = me.getJobGridWin("编辑任务信息", "update");
		jobGridWin.loadJobGridForm(record);
		jobGridWin.show();
	},
	onStartTask: function() {
		var me = this,
			sm = me.getSelectionModel(),
			id, active,node;
		if(sm.hasSelection()) {
			id = sm.getSelection()[0].get('id');
			active = sm.getSelection()[0].get('active');
			//任务分组所属实例节点
			node = sm.getSelection()[0].get('instanceId');
			//任务所属模块
			blongModule =sm.getSelection()[0].get('blongModule');
			if(active == "1") {
				Ext.MessageBox.alert("提示", "当前任务正在执行中...");
				return;
			} else {
				Ext.Ajax.request({
					url: '/'+node+'/baseinfo/startupTask.action',
					jsonData: {
						'jobGridVo': {
							'jobGridSchedulesEntity': {
								'id': id,
								'blongModule':blongModule
							}
						}
					},
					success: function(response) {
						me.getStore().load();
						Ext.MessageBox.alert("提示", "启动成功！");
					},
					exception : function(response) {
	            	var json = Ext.decode(response.responseText); 
		        	Ext.ux.Toast.msg('错误', json.message, 'error');
	            }
				});
			}
		} else {
			Ext.MessageBox.alert("提示", "请先选择一条任务！");
		}
	},
	onStopTask: function() {
		var me = this,
			sm = me.getSelectionModel(),
			id,node,blongModule;
		if(sm.hasSelection()) {
			id = sm.getSelection()[0].get('id');
			active = sm.getSelection()[0].get('active');
				//任务分组所属实例节点
			node = sm.getSelection()[0].get('instanceId');
			blongModule =sm.getSelection()[0].get('blongModule');
			if(active == "0") {
				Ext.MessageBox.alert("提示", "当前任务已经处于停止状态...");
				return;
			} else {
				Ext.Ajax.request({
					url: '/'+node+'/baseinfo/stopTask.action',
					jsonData: {
						'jobGridVo': {
							'jobGridSchedulesEntity': {
								'id': id,
								'blongModule':blongModule
								
							}
						}
					},
					success: function(response) {
						me.getStore().load();
						Ext.MessageBox.alert("提示", "停止成功！");
					},
				   exception : function(response) {
	            	var json = Ext.decode(response.responseText); 
		        	Ext.ux.Toast.msg('错误', json.message, 'error');
	            }
				});
			}
		} else {
			Ext.MessageBox.alert("提示", "请先选择一条任务！");
		}
	},
	onRenderType: function(value) {
		return value === '1' ? '表达式' : '表达式';
	},
	onRenderActive: function(value) {
		return value == '1' ? '启用中' : '停止中';
	}
});

//任务日志Grid
Ext.define('Foss.baseinfo.qrtzLogPanel', {
	extend: 'Ext.grid.Panel',
	frame: false,
	height: 350,
	enableColumnHide: false,
	sortableColumns: false,
	viewConfig: {
        enableTextSelection: true
    },
    autoScroll: true,
    jobName: null,
    jobGroup: null,
    blongModule:null,
    setBaseParams: function(jobName, jobGroup,blongModule) {
    	this.jobName = jobName;
    	this.jobGroup = jobGroup;
    	this.blongModule = blongModule;
    },
	initComponent: function() {
		var me = this;
		me.columns =  [
			{ text: '触发时间', width: 160, dataIndex: 'startTime' },
			{ text: '结束时间', width: 160, dataIndex: 'endTime' },
			{ text: '错误信息', flex: 1, dataIndex: 'errorMessage'}
		];
		me.store = Ext.create('Foss.baseinfo.qrtzLogStore', {
			autoLoad: false,
			pageSize: 10,
			listeners: {
				beforeload: function(store, operation, eOpts) {
					var form = me.up('window').down('form'),
						startTime = form.getValues().startTime,
						endTime = form.getValues().endTime;
					if(me.jobName != null && me.jobGroup != null) {
						Ext.apply(operation, {
							params: {
								'jobGridVo.jobGridLoggingsEntity.jobGroup': me.jobGroup,
								'jobGridVo.jobGridLoggingsEntity.jobName': me.jobName,
								'jobGridVo.jobGridLoggingsEntity.blongModule': me.blongModule,
								'jobGridVo.jobGridLoggingsEntity.startTime': startTime || Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
								'jobGridVo.jobGridLoggingsEntity.endTime': endTime || Ext.Date.format(new Date(), 'Y-m-d H:i:s')
							}
						});
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store
		});
		me.callParent(arguments);
	}
});

//任务日志查询表单
Ext.define('Foss.baseinfo.qrtzLogQueryFrom', {
	extend: 'Ext.form.Panel',
	initComponent: function() {
		var me = this;
		me.items = [{
    		xtype: 'fieldset',
			title: '查询区间',
			layout: {
			    type: 'column'
			},
			defaults: {
				lableWidth: 60,
				width: 480
			},
			items: [{
	    		xtype: 'rangeDateField',
	    		margin: '10 0 10 0',
	    		fieldId: 'jobGird_loggingTime',
	    		dateType: 'datetimefield_date97',
	    		fromName: 'startTime',
	    		toName: 'endTime',
	    		fromValue: Ext.Date.format(baseinfo.qrtz.formatDefaultDate(true), 'Y-m-d H:i:s'),
	    		toValue: Ext.Date.format(baseinfo.qrtz.formatDefaultDate(false), 'Y-m-d H:i:s'),
	    		disallowBlank: true
			}, {
				xtype: 'container',
				layout: 'column',
				items: [{
		    		xtype: 'button',
					text: '重置',
					cls: 'yellow_button',
					handler: me.onReset,
					scope: me,
					columnWidth: 0.2
				}, {
					xtype: 'container',
					height: 30,
					columnWidth: 0.6
				}, {
		    		xtype: 'button',
		    		text: '查询',
					cls: 'yellow_button',
					plugins: {
						ptype: 'buttonlimitingplugin',
						seconds: 4
					},
					handler: me.onQuery,
					scope: me,
					columnWidth: 0.2
				}]
			}]
		}];
		me.callParent();
	},
	onQuery: function() {
		if(this.getForm().isValid()) {
			var grid = this.up('window').down('grid');
/*			var jobChart =me.getQueryGrid();
         	jobChart.store.loadData(generateData());
*/			grid.store.loadPage(1);
		} else {
			Ext.Msg.alert('提示', '请检查查询条件正确性！');
		}
	},
	onReset: function() {
		this.getForm().reset();
	}
});

//任务日志window
Ext.define('Foss.baseinfo.qrtzLogWin', {
	extend: 'Ext.window.Window',
	title: '任务日志',
	width: 680,
	closable: true,
	resizable: false,
	closeAction: 'hide',
	modal: true,
	layout: 'auto',
	jobGridLogPanel: null,
	jobGridLogQueryFrom: null,
	initComponent: function() {
		var me = this;
		me.items = [me.getJobGridLogQueryFrom(), me.getJobGridLogPanel()];
		me.callParent(arguments);
	},
	getJobGridLogPanel: function() {
		if(this.jobGridLogPanel == null) {
			this.jobGridLogPanel = Ext.create('Foss.baseinfo.qrtzLogPanel');
		}
		return this.jobGridLogPanel;
	},
	getJobGridLogQueryFrom: function() {
		if(this.jobGridLogQueryFrom == null) {
			this.jobGridLogQueryFrom = Ext.create('Foss.baseinfo.qrtzLogQueryFrom');
		}
		return this.jobGridLogQueryFrom;
	}
});

Ext.onReady(function() {
	 baseinfo.qrtz.queryForm  = Ext.create('Foss.baseinfo.qrtzQueryForm', {
			record: Ext.create('Foss.baseinfo.qrtzModel')
		}),
	 baseinfo.qrtz.queryGrid  = Ext.create('Foss.baseinfo.qrtzList');
	 baseinfo.qrtz.chartWindow = Ext.create('Foss.qrtz.JobGridQueryChartWindow',{
			id : 'Foss_qrtz_JobGridQueryChartWindow',
			margin : '40 0 10 0'
	}) ;
	Ext.getCmp('T_baseinfo-jobqrtz').add(
		Ext.create('Ext.panel.Panel', {
			id: 'T_baseinfo-jobqrtz_content',
			cls: 'panelContentNToolbar',
			bodyCls: 'panelContentNToolbar-body',
			getJobGridList: function() {
				return baseinfo.qrtz.queryGrid;
			},
			getJobChartWindow:function(){
				return baseinfo.qrtz.chartWindow;
			},
			items: [ baseinfo.qrtz.queryForm, baseinfo.qrtz.queryGrid, baseinfo.qrtz.chartWindow]
		})
	);
});
