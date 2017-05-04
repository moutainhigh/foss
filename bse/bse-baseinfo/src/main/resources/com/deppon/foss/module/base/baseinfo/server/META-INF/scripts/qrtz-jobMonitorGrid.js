
Ext.define('Foss.jobMonitor.qrtzDataAccumulationRecordModel',{
            extend:'Ext.data.Model',
            fields:[
            {name:'id',type:'string'},
            {name:'jobGroup',type:'string'},
            {name:'blongModule',type:'string'},
            {name:'jobName',type:'string'},
            {name:'dataAccumulation',type:'number'},
            {name:'createDate',type:'string'},
            {name:'latestrecord',type:'number'}
            ]
})  ;
Ext.define('Foss.jobMonitor.qrtzDataAccumulationStore',{
			extend:'Ext.data.Store',
			model:'Foss.jobMonitor.qrtzDataAccumulationRecordModel',
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
Ext.define('Foss.jobMonitor.jobMonitorModel',{
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
		    {name:'dataAccumulation',type:'string'},//数据积累量
		    {name:'createDate',type:'string'} //创建日期
			]
	});
	Ext.define('Foss.jobMonitor.jobMonitorStore', {
		extend: 'Ext.data.Store',
		pageSize: 10,
		model: 'Foss.jobMonitor.jobMonitorModel',
		proxy: {
			type: 'ajax',
			actionMethods:'post',
			url:'../baseinfo/searchMonitorJobByCondition.action',
			reader: {
				type: 'json',
				root: 'jobMonitorVo.jobMonitorList',
				totalProperty: 'totalCount'
			}
		}
	});
	Ext.define('Foss.jobMonitor.qrtzEarylWarnWin', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	title : 'job预警', // 计划生效窗口
	//modal : true,
	layout : 'fit',
	width: 800,
    height: 600,
    minHeight: 400,
    minWidth: 550,
    hidden: false,
    maximizable: true,
	addChart : null,
	getAddChart : function() {
		if (this.addChart == null) {
			this.addChart = Ext.create('Foss.jobMonitor.qrtzEarylWarnChart');
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
Ext.define('Foss.jobMonitor.qrtzEarylWarnChart', {
	      	extend: 'Ext.chart.Chart',
            style: 'background:#fff',
           // animate: true,
            store: null,
            shadow: true,
         //   theme: 'Category2',
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
                fields: ['createDate'],
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
                xField: 'createDate',
                yField: 'dataAccumulation',
                markerConfig: {
                    type: 'cross',
                    size: 4,
                    radius: 4,
                    'stroke-width': 0
                }
            }, {
            	title:'预警阀值',
                type: 'line',
                highlight: {
                    size: 7,
                    radius: 7
                },
                axis: 'left',
                smooth: true,
                xField: 'createDate',
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
        		me.store = Ext.create('Foss.jobMonitor.qrtzDataAccumulationStore', {
        			autoLoad: false
        			});
        		 me.callParent([ cfg ]);
            }
        }); 

	/**
	 * 查询数据未处理量
	 */
/*  Ext.define('Foss.jobMonitor.qrtzDataAccumulationStore', {
		extend: 'Ext.data.Store',
		pageSize: 10,
		model: 'Foss.jobMonitor.jobMonitorModel',
		proxy: {
			type: 'ajax',
			actionMethods:'post',
			url:'../baseinfo/queryJobDataAccumulationRecord.action',
			reader: {
				type: 'json',
				root: 'jobDataAccumulationVo.jobDataAccumulationRecord',
				totalProperty: 'totalCount'
			}
		}
	});*/
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
baseinfo.jobMonitor.jobDataAccumulationStore = Ext.create('Foss.jobMonitor.qrtzDataAccumulationStore',{
      autoLoad: false
});
baseinfo.jobMonitor.jobStore = Ext.create('Foss.jobMonitor.jobMonitorStore', {
	autoLoad: false,
	//pageSize: 10,
	listeners: {
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('T_baseinfo-jobMonitor_content').down('form').getForm(),
				entity,jobModule;
			queryForm.updateRecord(queryForm.record);
			entity = queryForm.record.data;
			if(queryForm != null) {
				Ext.apply(operation, {
					params: {
						'jobMonitorVo.jobMonitor.jobName': entity.jobName,
						'jobMonitorVo.jobMonitor.jobModule':entity.jobModule,
						'jobMonitorVo.jobMonitor.active': entity.active
					}
				});	
			}
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
	jobModule:'jobGridVo.jobGridClusterEntity.jobModule',//任务所属模块jobModule
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

Ext.define('Foss.jobMonitor.JobGridChart', {
	extend: 'Ext.chart.Chart',
	      //alias: 'widget.jobgridChart',
           // xtype: 'chart',
            style: 'background:#fff',
            animate: true,
            shadow: true,
            store:baseinfo.jobMonitor.jobDataAccumulationStore,
    		theme: 'Fancy',
           /* animate: {
                easing: 'bounceOut',
                duration: 750
            },*/
    		
            gradients: [
            {
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
                        color: '#373C64'
                    },
                    100: {
                        color: '#373C64'
                    }
                }
            }
            ],
            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['dataAccumulation'],
                label: {
                	 rotate: { degrees: 45, angle: 45},
                    renderer: Ext.util.Format.numberRenderer('0,0')
                },
                title: '未处理数据量',
                minimum: 0
            }, {
                type: 'Category',
                position: 'bottom',
                //设置轴下文本的显示的角度
                label: {
               	 rotate: { degrees: 45}
               },
                fields: ['jobDesc'],
                title: '定时任务名称',
                fixedSteps: [5, 10,20]
            }],
            series: [{
            	style: { width: 10 },//这里是宽度
                type: 'column',
                axis: 'left',
                gutter: 80,
                xField: 'jobDesc',
                yField: ['dataAccumulation'],
                //设置柱状图颜色
                renderer: function(sprite, storeItem, barAttr, i, store) {
                   // barAttr.fill = colors[i % colors.length];
                   barAttr.fill=colors[1];
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
                        this.update(storeItem.get('jobDesc'));
                    }
                },
                listeners: {
                    'itemmouseup': function(item) {
                    	 //alert("sdf");
                        // var series = this.series.get(0),
                          //   jobMonitor = Ext.Array.jobMonitorOf(series.items, item),
                          //   selectionModel = gridPanel.getSelectionModel();
                          // selectedStoreItem = item.storeItem;
                         //  selectionModel.select(jobMonitor);
                    }
                },
                style: {
                    fill: '#38B8BF'
                }
            }]
           
        });

           

Ext.define('Foss.jobMonitor.JobMonitorQueryChartWindow', {
	extend: 'Ext.panel.Panel',
	//alias: 'widget.jobgridqueryChart',
    width: 1031,
    height: 700,
    //minHeight: 400,
    //minWidth: 550,
    hidden: true,
  // maximizable: true,
  // title: 'Column Chart',
    layout : 'fit', // 如果为'form'，则竖着显示，如果'column',则横着显示  fit
    JobGridChart:null,
    items:null,
    getJobGridChart: function() {
		if(this.JobGridChart == null) {
			this.JobGridChart = Ext.create('Foss.jobMonitor.JobGridChart');
		}
		return this.JobGridChart;
	},
    tbar: null,
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config); 
    	 me.items= me.getJobGridChart() ;
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
               			'jobDataAccumulationVo.jobModule':'BSE'
               	};
             	  Ext.apply(baseinfo.jobMonitor.jobDataAccumulationStore.proxy.extraParams,condition);
             	 baseinfo.jobMonitor.jobDataAccumulationStore.load();
             }
         },
             {
        	 text: '中转',
             handler: function() {
            	 var  condition ={
              			'jobDataAccumulationVo.jobModule':'TFR'
              	};
             	  Ext.apply(baseinfo.jobMonitor.jobDataAccumulationStore.proxy.extraParams,condition);
             	 baseinfo.jobMonitor.jobDataAccumulationStore.load();
             }
             },
             {
            	 text: '接送货',
            	 handler: function() {
            		 var  condition ={
                 			'jobDataAccumulationVo.jobModule':'PKP'
                 	};
            	  Ext.apply(baseinfo.jobMonitor.jobDataAccumulationStore.proxy.extraParams,condition);
            	  baseinfo.jobMonitor.jobDataAccumulationStore.load();
            	 }
             },
            {
                text: '结算',
                 handler: function() {
                	var  condition ={
                			'jobDataAccumulationVo.jobModule':'STL'
                			
                	};
                      Ext.apply(baseinfo.jobMonitor.jobDataAccumulationStore.proxy.extraParams,condition);
                   	 baseinfo.jobMonitor.jobDataAccumulationStore.load();
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
baseinfo.jobMonitor.formatDefaultDate = function(isBegin) {
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
baseinfo.jobMonitor.regs = {
	quartz: /^([\d*/\-,?]+\s){5}([\d*\-?#A-Z]+)$|^([\d*/\-,?A-Z]+\s){6}([\d*/\-]+)$/
};
//查询表单
Ext.define('Foss.jobMonitor.qrtzQueryForm', {
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
				name : 'jobModule',
				xtype : 'combobox',
				fieldLabel : '任务模块',
				queryMode : 'local',
				displayField : 'name',
				valueField : 'code',
				//value:'',
				editable:false,
				columnWidth: .4,
				lableWidth: 40,
				listeners:{
					'blur' : function(ths, the,eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change':function(ths,the, eOpts) {
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
					data : {
						'items' : [
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
			xtype:'textfield',
			fieldLabel: '任务名称',
			name: 'jobName',
			columnWidth: .45,
			lableWidth: 60
		}, {
						xtype: 'button',
						text: '重置',
						margin:'0 0 0 350' ,
						cls: 'yellow_button',
						handler: me.onReset,
						scope: me,
						width: 75
					}, 
					{
						xtype: 'button',
						text: '查询',
						margin:'0 0 0 50' ,
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
			baseinfo.jobMonitor.jobStore.loadPage(1);
			baseinfo.jobMonitor.chartWindow.show();
		} else {
			Ext.Msg.alert('请检查查询条件正确性！');
		}
	},
	onReset: function() {
		this.getForm().reset();
	}
});

//新增编辑
Ext.define('Foss.jobMonitor.jobMonitorForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.jobMonitorgridform',
	defaultType: 'fieldset',
	initComponent: function() {
		var me = this;
		me.items = [{
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
				name: 'jobName'
			}, 
			{
				name : 'jobModule',
				xtype : 'combobox',
				fieldLabel : '任务模块',
				allowBlank: false,
				queryMode : 'local',
				displayField : 'name',
				valueField : 'code',
				editable:false,
				listeners:{
					'blur' : function(ths, the,eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change':function(ths,the, eOpts) {
						
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
			}, {
				fieldLabel: '任务描述',
				allowBlank: false,
				name: 'jobDesc'
			}, {
				fieldLabel: '预警消息接收人',
				allowBlank: false,
				name: 'warningReceiver'
			}, {
				fieldLabel: '预警阀值',
				name:'maxAccumulation'
			}, {
				name : 'active',
				xtype : 'combobox',
				fieldLabel : '是否生效',
				allowBlank: false,
				queryMode : 'local',
				displayField : 'name',
				valueField : 'code',
				value:'生效',
				editable:false,
				listeners:{
					'blur' : function(ths, the,eOpts) {
						if (Ext.isEmpty(ths.value)) {
							ths.setRawValue(null);
						}
					},
					'change':function(ths,the, eOpts) {
						
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
					data : {
						'items' : [ {
							"code" : "Y",
							"name" : '生效' // 
						}, {
							"code" : "N",
							"name" : '不生效' // 
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
				fieldLabel: '查询sql',
				xtype: 'textarea',
				allowBlank: false,
				width:500,
				colspan: '4',
				///^([\d*/\-,?]+\s){5}([\d*\-?#A-Z]+)$|^([\d*/\-,?A-Z]+\s){6}([\d*/\-]+)$/
				regex:	/^[Ss][Ee][Ll][Ee][Cc][Tt][^;]*([^;]$)/,
				regexText: '只能输入查询脚本(select/SELECT),脚本不能已分号结尾',
				name: 'querySql'
			}
			]
		}];
		me.callParent();
	}
});

Ext.define('Foss.jobMonitor.jobMonitorWin', {
	extend: 'Ext.window.Window',
	alias: 'wigdet.jobgridwin',
	title: '新增任务',
	width: 680,
	height: 300,
	closable: true,
	resizable: false,
	closeAction: 'hide',
	modal: true,
	layout: 'fit',
	operationUrl: null,
	jobMonitorgridformMode: null,
	initComponent: function() {
		var me = this;
		me.items = [{
			xtype: 'jobMonitorgridform'
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
	getjobMonitorgridformModel: function() {
		if (null == this.jobMonitorgridformMode) {
			this.jobMonitorgridformMode = Ext.create("Foss.jobMonitor.jobMonitorModel");
		}
		return this.jobMonitorgridformMode;
	},
	setOperationUrl: function(url) {
		this.operationUrl = url;
	},
	loadjobMonitorgridform: function (record) {
		this.down('form').getForm().loadRecord(record);
		this.jobMonitorgridformMode = record;
	},
	onSaveRecord: function() {
		var me = this,
			form = me.down('form'),
			jobMonitorgridform = form.getForm(),node;
		if (jobMonitorgridform.isValid()) {
			jobMonitorgridform.updateRecord(me.getjobMonitorgridformModel());
			
			Ext.Ajax.request({
				
				//url: '../baseinfo/addMonitorJob.action',
				url:this.operationUrl,
				jsonData: {
					'jobMonitorVo': {
						'jobMonitor': me.getjobMonitorgridformModel().data
					}
				},
				success: function(response) {
					Ext.getCmp('T_baseinfo-jobMonitor_content').down('grid').store.load();
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
Ext.define('Foss.jobMonitor.jobMonitorList', {
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
       /*  selectionchange: function(model, records) {
             var json, name, i, l, items, series, fields;
             if (records[0]) {
            	 storeItem = records[0];
                 var name = storeItem.get('jobName'),
                 series = baseinfo.jobMonitor.chartWindow.getJobGridChart().series.get(0),
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
         }*/
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
			{ text: '任务名称', width: 140, dataIndex: 'jobName' },
			{ text: '所属模块', width: 140, dataIndex: 'jobModule' },
			{ text: '任务描述', width: 140, dataIndex: 'jobDesc' },
			{ text: '预警阀值', width: 140, dataIndex: 'maxAccumulation' },
			{ text: '预警消息接收人', width: 140, dataIndex: 'warningReceiver' },
			{ text: '查询sql', width: 300, dataIndex: 'querySql' },
			{ text: '状态', width: 80, dataIndex: 'active', renderer: me.onRenderActive }
		];
		
		me.store = baseinfo.jobMonitor.jobStore;
		me.tbar = [{
			text: '新增',
			handler: me.onAddRecord,
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
		me.callParent(arguments);
	},
	//job预警窗口
	onReviewEarlyWarning:function(grid, rowjobMonitor, coljobMonitor){
	      var me = this,
	      record = me.getStore().getAt(rowjobMonitor),
	      jobGroup = record.get('jobGroup');
	      jobName = record.get('jobName');
	      jobModule = record.get('jobModule');
	      var chart = me.getJobGridEarlyWarnWin().getAddChart();
	      var condition ={
	           'jobDataAccumulationVo.jobGridSchedulesEntity.jobGroup':jobGroup,
	            'jobDataAccumulationVo.jobGridSchedulesEntity.jobName':jobName,
	             'jobDataAccumulationVo.jobGridSchedulesEntity.jobModule':jobModule
	      };
	      //var grid = Ext.getCmp('T_base-agreement_content').getAgreementQueryGrid();
	    	Ext.apply(chart.store.proxy.extraParams,condition);
	    	chart.store.load();
	      me.getJobGridEarlyWarnWin().show();
	     
	},
	getOperationUrl: function(actionType) {
		var operationUrl = null;
		if (actionType === 'add') {
			operationUrl = '../baseinfo/addMonitorJob.action';
		} else if (actionType === 'update') {
			operationUrl = '../baseinfo/modifyMonitorJob.action';
		}
		return operationUrl;
	},
	
	getJobGridWin: function(title, actionType,jobModule) {
		var me = this,
			jobMonitorgridform;
		if (!me.jobGridWin) {
			me.jobGridWin = Ext.create('Foss.jobMonitor.jobMonitorWin');
		}
		jobMonitorgridform = me.jobGridWin.down('form').getForm()
		
		me.jobGridWin.setOperationUrl(me.getOperationUrl(actionType));
		me.jobGridWin.setTitle(title);
		if (actionType) {
			if (actionType === "add") {
				jobMonitorgridform.findField('jobName').setReadOnly(false);
				jobMonitorgridform.findField('jobModule').setReadOnly(false);
				jobMonitorgridform.findField('active').setReadOnly(false);
				me.jobGridWin.down('form').getForm().reset();
			} else if (actionType === "update") {
				jobMonitorgridform.findField('jobName').setReadOnly(true);
				jobMonitorgridform.findField('jobModule').setReadOnly(true);
				jobMonitorgridform.findField('jobModule').setReadOnly(false);
			}
		}
		return me.jobGridWin;
	},
	getJobGridEarlyWarnWin:function(){
	    if(!this.jobGridEarlyWarnWin){
	       this.jobGridEarlyWarnWin =Ext.create('Foss.jobMonitor.qrtzEarylWarnWin');
	    }
	    return this.jobGridEarlyWarnWin;
	},
	onAddRecord: function() {
		this.getJobGridWin("新增任务监控信息", "add").show();
	},
	onEditRecord: function() {
		var me = this,
			store = me.store,
			sm = me.getSelectionModel(),
			jobGridWin;
		if (sm.hasSelection()) {
			var data = sm.getSelection()[0];
			jobGridWin = me.getJobGridWin("编辑任务监控信息", "update");
			jobGridWin.loadjobMonitorgridform(data);
			jobGridWin.show();
		} else {
			Ext.Msg.alert("提示", "请先选择一条任务信息。");
		}
	},
	onRowEditRecord: function(grid, rowjobMonitor, coljobMonitor) {
		var me = this,
			record = me.getStore().getAt(rowjobMonitor),
			jobGridWin = me.getJobGridWin("编辑任务信息", "update");
		jobGridWin.loadjobMonitorgridform(record);
		jobGridWin.show();
	},
	onRenderType: function(value) {
		return value === '1' ? '表达式' : '表达式';
	},
	onRenderActive: function(value) {
		return value == 'Y' ? '启用中' : '停止中';
	}
});

Ext.onReady(function() {
	 baseinfo.jobMonitor.queryForm  = Ext.create('Foss.jobMonitor.qrtzQueryForm', {
			record: Ext.create('Foss.jobMonitor.jobMonitorModel')
		}),
	 baseinfo.jobMonitor.queryGrid  = Ext.create('Foss.jobMonitor.jobMonitorList');
	 baseinfo.jobMonitor.chartWindow = Ext.create('Foss.jobMonitor.JobMonitorQueryChartWindow',{
			id : 'Foss_qrtz_JobMonitorQueryChartWindow',
			margin : '40 0 10 0'
	}) ;
	Ext.getCmp('T_baseinfo-jobMonitor').add(
		Ext.create('Ext.panel.Panel', {
			id: 'T_baseinfo-jobMonitor_content',
			cls: 'panelContentNToolbar',
			bodyCls: 'panelContentNToolbar-body',
			getJobGridList: function() {
				return baseinfo.jobMonitor.queryGrid;
			},
			getJobChartWindow:function(){
				return baseinfo.jobMonitor.chartWindow;
			},
			items: [ baseinfo.jobMonitor.queryForm, baseinfo.jobMonitor.queryGrid/*, baseinfo.jobMonitor.chartWindow*/]
		})
	);
});
