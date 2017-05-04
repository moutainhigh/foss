
Ext.define('Foss.commonSelector.PlatformSelectorByVirtualCode', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonplatformselectorByVirtualCode',
			fieldLabel : '月台',
			displayField : 'platformCode',// 显示名称
			valueField : 'virtualCode',// 值
			orgCode:null,
			queryParam : 'platformVo.platformEntity.platformCode',// 查询参数
			showContent : '{platformCode}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.commonSelector.PlatformStore');
				
				me.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {}; 
					}
					if(!Ext.isEmpty(config.orgCode)){ 
						searchParams['platformVo.platformEntity.organizationCode'] = config.orgCode;
					}
					Ext.apply(operation, {
						params : searchParams
					});
				}); 
				me.callParent([cfg]);
			}
		});


Ext.define('Foss.commonSelector.PlatformSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonplatformselector',
	displayField : 'platformCode',// 显示名称
	valueField : 'platformCode',// 值
	orgCode:null,
	queryParam : 'platformVo.platformEntity.platformCode',// 查询参数
	showContent : '{platformCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.commonSelector.PlatformStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {}; 
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if(!Ext.isEmpty(me.orgCode)){ 
				searchParams['platformVo.platformEntity.organizationCode'] = me.orgCode;
			} else {
				searchParams['platformVo.platformEntity.organizationCode'] = FossUserContext.getCurrentDept().code;
			}
			Ext.apply(operation, {
				params : searchParams
			});
		}); 
		me.callParent([cfg]);
	}
});

//查询月台停靠信息
Ext.define('Foss.scheduling.platformDispatch.PlatformDispatchQueryForm',{
	extend:'Ext.form.Panel',
	title: '查询月台信息',
	frame:true,
	collapsible: true,
    animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 85
	},
	defaultType: 'textfield',
	layout: 'column',
	queryPlatformDispatchUseInfo: function() {
		var values = scheduling.platform.platformDispatchQueryForm.getForm().getValues();
		if(!values.showAvailable) {
			values.showAvailable = false;
		}
		if(!Ext.isEmpty(values.startPlatformCode) && !Ext.isEmpty(values.endPlatformCode)) {
			var begin = values.startPlatformCode * 1;
			var end = values.endPlatformCode * 1;
			if(begin >= end) {
				Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'),scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.platformNoWarning'));				
				return false;
			}
		}
		// 起始时间不可为空
		if(Ext.isEmpty(values.useBeginTime)) {
			Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'), scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.timeNotNullWarning'));				
			return false;
		}
		if(!Ext.isEmpty(values.useBeginTime) && !Ext.isEmpty(values.useEndTime)) {
			var begin = Ext.Date.parse(values.useBeginTime, "Y-m-d H:i:s", true);
			var end = Ext.Date.parse(values.useEndTime, "Y-m-d H:i:s", true);
			var pool = begin - end;
			// 起始时间不能大于截止时间
			if(pool > 0) {
				Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'), scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.tiemSequenceWarning'));				
				return false;
			}
			var m = -86400000;
			//时间跨度不超过24小时
			if(pool < m) {
				Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'),scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.timeDiffWarning'));				
				return false;
			} 
		}
		
		var platformDistribute = {
			// 月台区间查询
			'platformDispatchVo.platformDistributeDto.startPlatformCode':values.startPlatformCode,
			'platformDispatchVo.platformDistributeDto.endPlatformCode':values.endPlatformCode,
			// 时间
			'platformDispatchVo.platformDistributeDto.useBeginTime':values.useBeginTime,
			'platformDispatchVo.platformDistributeDto.useEndTime':values.useEndTime,
			// 车型
			'platformDispatchVo.platformDistributeDto.vehicleType':values.vehicleType,
			// 车号
			'platformDispatchVo.platformDistributeDto.platformDistributeEntity.vehicleNo':values.vehicleNo,
			// 外场
			'platformDispatchVo.platformDistributeDto.platformDistributeEntity.transferCenterNo':values.transferCenterNo,
			// 只显示可用月台
			'platformDispatchVo.platformDistributeDto.showAvailable':  values.showAvailable
		};
		Ext.Ajax.request({
			url:scheduling.realPath('queryPlatformUseInfo.action'),
			params:platformDistribute,
			success:function(response){
				var result = Ext.decode(response.responseText);
				var list = result.platformDispatchVo.platformDistributeDtoList;
				try {
					scheduling.platform.platformDispatchQueryGrid.store.loadData(list);
				}catch(e){}
				//不能show
				scheduling.platform.mergeCellsAndHighlightedTimeAxis(scheduling.platform.platformDispatchQueryGrid);
//				scheduling.platformDispatchQueryGrid.setVisible(true)
				if(scheduling.platform.taskExplainContainer.hidden){
					scheduling.platform.taskExplainContainer.setVisible(true);
				}
			},
			exception: function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'), result.message);
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name: 'startPlatformCode',
			xtype:'commonplatformselector',
			orgCode: FossUserContext.getCurrentDept().code,
			fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.startPlatformCode'),//'月台号',
			columnWidth: .25
		},{
			fieldLabel: '——',
			labelSeparator:'',
			xtype:'commonplatformselector',
			orgCode: FossUserContext.getCurrentDept().code,
			name: 'endPlatformCode',
			columnWidth: .25
		},{
			xtype:'rangeDateField',
			dateType:'datetimefield_date97',
			fieldId:'Foss_scheduling_PlatformDispatchQueryForm_useTime_ID',
			fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.useBeginTime'),//'使用时间',
			fromName: 'useBeginTime',
			toName: 'useEndTime',
			dateRange:1,
			columnWidth: .5
		},{
			name: 'vehicleType',
			fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.vehicleType'),//'车型',
			xtype:'combobox',
		    store: Ext.create('Ext.data.Store', {
					    fields: ['text', 'value'],
					    data : [
					        {'text':'金杯', 'value': 'jinbei'},
					        {'text':'依维柯', 'value': 'naveco'},
					        {'text':'4.2米', 'value': '42'},
					        {'text':'6.5米', 'value': '65'},
					        {'text':'7.6米', 'value': '76'},
					        {'text':'9.6米', 'value': '96'},
					        {'text':'17.5米', 'value':'175'}
					    ]
					}),
		    queryMode: 'local',
		    displayField: 'text',
		    valueField: 'value',
		    editable:false,
		    forceSelection:true,
		    triggerAction:'all',
		    columnWidth:.25
		},{
			name: 'vehicleNo',
			fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.vehicleNo'),//'车号',
			xtype:'commontruckselector',
			columnWidth: .25
		},{
			name: 'transferCenterNo',
			fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.transferCenterNo'),//'外场',
			xtype:'dynamicorgcombselector',
			transferCenter:'Y',
			type : 'ORG',
			columnWidth: .25,
			listeners : {
				select : function(combo, records, eOpts ) {
					var record = records[0].data;
					var form = scheduling.platform.platformDispatchQueryForm.getForm();
					var findField = function(name) {
						return form.findField(name);
					};
					var orgCode = record.code;
					findField('startPlatformCode').orgCode = orgCode;
					findField('startPlatformCode').store.load();
					
					findField('endPlatformCode').orgCode = orgCode;
					findField('endPlatformCode').store.load();
				},
			   change : function(ths, newValue, oldValue, eOpts) {
					if (Ext.isEmpty(ths.getRawValue())) {
						var form = scheduling.platform.platformDispatchQueryForm.getForm();
						var findField = function(name) {
							return form.findField(name);
						};
						ths.setValue(null);
						findField('startPlatformCode').orgCode = null;
						findField('startPlatformCode').store.load();
						
						findField('endPlatformCode').orgCode = null;
						findField('endPlatformCode').store.load();
					}
				}
			}
		},{
			name: 'showAvailable',
			xtype:'checkboxfield',
			boxLabel:scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.showAvailable'),//'只显示可用月台',
			inputValue:'true',
			columnWidth: .25
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.reset'),//'重置',
				columnWidth:.08,
				handler:function(){
					scheduling.platform.platformDispatchQueryForm.getForm().reset();
					this.ownerCt.ownerCt.resetQueryCondition();
				}
			},{
				xtype: 'container',
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text:scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryForm.query'),//'查询',
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					
					if(!scheduling.platform.isPermission('scheduling/queryPlatformUseInfoButton')){
						return;
					}
					
					scheduling.platform.platformDispatchQueryForm.queryPlatformDispatchUseInfo();
				}
			}]
		}];
		me.callParent([cfg]);
	},
	resetQueryCondition:function() {
		var form = this.getForm();
		var findField = function(name) {
			return form.findField(name);
		};
		var $ = function(id) {
			return document.getElementById(id);
		};
		var formatStr = 'Y-m-d H:i:s';
		var now = new Date();
		var begin = Ext.Date.format(now, formatStr);
		var endDate = new Date(now.getFullYear(),now.getMonth(),now.getDate() + 1, now.getHours(), now.getMinutes(), now.getSeconds()-1);
		var end = Ext.Date.format(endDate, formatStr);
		
		var id = "Foss_scheduling_PlatformDispatchQueryForm_useTime_ID";
		$(id + '_first-inputEl').value = begin;
		$(id + '_second-inputEl').value = end;
		
		form.findField('transferCenterNo').setCombValue(scheduling.platform.deptName, scheduling.platform.deptCode);
	},
	initTransferDept:function(deptCode,deptName){
		var form = this.getForm();
		form.findField('transferCenterNo').setCombValue(deptName, deptCode);
		form.findField('transferCenterNo').setReadOnly(true);
	},
	handleDeptError:function(){
		var form = this.getForm();
		//form.findField('transferCenterNo').setCombValue(deptName, deptCode);
		form.findField('transferCenterNo').setReadOnly(true);
	}
});

Ext.define('Foss.scheduling.platformDispatch.PlatformDispatchQueryStore',{
	extend:'Ext.data.Store',
	fields:['platformNo', 'useType', 'hour1800','hour1830','hour1900','hour1930','hour2000',
	        'hour2030', 'hour2100','hour2130','hour2200','hour2230','hour2300','hour2330',
	        'hour0000','hour0030','hour0100','hour0130','hour0200','hour0230','hour0300',
	        'hour0330','hour0400','hour0430','hour0500','hour0530','hour0600','hour0630',
	        'hour0700','hour0730','hour0800','hour0830','hour0900','hour0930','hour1000',
	        'hour1030','hour1100','hour1130','hour1200','hour1230','hour1300','hour1330',
	        'hour1400','hour1430','hour1500','hour1530','hour1600','hour1630','hour1700',
	        'hour1730','platformVirtualCode'
	]
});

Ext.define('Foss.scheduling.platformDispatch.PlatformDispatchQueryGrid', {
	extend:'Ext.grid.Panel',
    //bodyCls: 'autoHeight',
	cls: 'custom-grid',
	//表格对象增加一个边框
    frame: true,
    //增加表格列的分割线
	columnLines: true,
	//定义表格的标题
    title:scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.title'),//'月台信息',
    emptyText: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.emptyText'),//'查询结果为空',
	//表格绑定store
	store: null,
	//选择模式
	selType: 'cellmodel',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.platformDispatch.PlatformDispatchQueryStore');
		me.tbar = [{
			xtype : 'button',
			text : scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.forceDeleteButton'),//'强制清空',
			tooltip: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.forceDeleteButton.tooltip'),//'清空手动占用月台',
			handler : function(){
				Ext.create('Ext.window.Window', {
				    title: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.window.title'),//'强制清空月台信息',
				    cls:'specialWin',
				    height: 150,
				    width: 200,
				    modal: true,
				    layout: 'fit',
				    items: [
				            Ext.create('Ext.form.Panel',{
				            	extend: 'Ext.form.Panel',				            				            	
				            	defaults: {
				            		margin:'5 10 5 10',
				            		anchor: '90%',
				            		labelWidth:60
				            	},
				            	defaultType : 'textfield',
				            	layout:'column',
				            	items:[{
					            		//月台号
						            		name: 'platFormNo',
						            		//id: 'Foss_querypackedindex_PackageQueryForm_packDeptCode_ID',
						            		xtype:'textfield',
						            		allowBlank: false,
						            		fieldLabel:scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.window.platFormNo'),//'月台号',
						            		columnWidth:.99
				            			},{
				            				xtype:'button',
				            				text:scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.window.deleteButton'),//'清空',
						            		columnWidth:.5,
						            		handler: function() {
						            			var form = this.up('form').getForm();
						            			var platFormNo = form.findField('platFormNo').getValue();
						            			if(form.isValid()){
						            				//部门 scheduling.platform.deptCode
						            				//alert(platFormNo);
						            				Ext.Ajax.request({
						            					url : scheduling.realPath('forceDeletePlatFrom.action'),
						            					params: {
						        							'platformDispatchVo.platFormNo':platFormNo,
						        							'platformDispatchVo.deptCode': scheduling.platform.deptCode 
						        						},
						        						success: function(response){
						        							var result = Ext.decode(response.responseText);	
						        							Ext.ux.Toast.msg(scheduling.platform.i18n('foss.scheduling.platform.hint'), scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.window.deleteSuccess'), 'success', 5000);
						        						},
						        						exception: function(response){
						        							var result = Ext.decode(response.responseText);
						    	                        	//错误
						    	                            Ext.Msg.alert(scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.window.deleteError'),result.message); 
						        						}
						            				});
						            			}
				            				}
				            			},{
				            				xtype:'button',
				            				text:scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.window.cancelButton'),//'取消',
				            				columnWidth:.5,
				            				handler: function() {
				            					this.up('window').hide();
				            				}
				            			}]
				            })
				            ]         
				}).show();
			}
		}],
		me.callParent([cfg]);
	},
	enableColumnHide:false,
		//定义表格列信息
		columns: [/*{
            xtype:'actioncolumn',
            flex: 0.5,
			text: '操作',//'操作',
			align: 'center',
            items: [{
            	iconCls:'deppon_icons_print',
                tooltip: '强制解除任务已完成的月台占用',//'打印新流水号',
                handler: function(grid, rowIndex, colIndex) {
                	alert("确定解除吗？");
                }
            }]
		},*/{
			header: scheduling.platform.i18n('foss.scheduling.platform.PlatformDispatchQueryGrid.platformNo'),//'月台号',
			dataIndex: 'platformNo',
			windowClassName : 'Foss.scheduling.platformDispatch.PlatformDetailWindow',
			xtype: 'openwindowcolumn',
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '18:00', 
			dataIndex: 'hour1800',
			renderer : makeUp,
			width:66,
			draggable :false,
			hideable : false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1830',
			renderer : makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '19:00', 
			dataIndex: 'hour1900',
			renderer : makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1930',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '20:00', 
			dataIndex: 'hour2000',
			renderer: makeUp,	
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour2030',
			width:66,
			renderer: makeUp,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '21:00', 
			dataIndex: 'hour2100',
			renderer: makeUp,		
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour2130',
			width:66,
			renderer: makeUp,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '22:00', 
			dataIndex: 'hour2200',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour2230',
			renderer: makeUp,
			width:66,
			hideable : false,
			sortable: false
		},{
			header: '23:00', 
			dataIndex: 'hour2300',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour2330',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '0:00', 
			dataIndex: 'hour0000',
			renderer: makeUp,
			width:66,
			hideable : false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0030',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '1:00', 
			dataIndex: 'hour100',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0130',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '2:00', 
			dataIndex: 'hour0200',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0230',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '3:00', 
			dataIndex: 'hour0300',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0330',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '4:00', 
			dataIndex: 'hour0400',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0430',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '5:00', 
			dataIndex: 'hour0500',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0530',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '6:00', 
			dataIndex: 'hour0600',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0630',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '7:00', 
			dataIndex: 'hour0700',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0730',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '8:00', 
			dataIndex: 'hour0800',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0830',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '9:00', 
			dataIndex: 'hour0900',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour0930',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '10:00', 
			dataIndex: 'hour1000',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1030',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '11:00', 
			dataIndex: 'hour1100',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1130',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '12:00', 
			dataIndex: 'hour1200',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1230',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '13:00', 
			dataIndex: 'hour1300',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1330',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '14:00', 
			dataIndex: 'hour1400',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1430',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '15:00', 
			dataIndex: 'hour1500',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1530',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '16:00', 
			dataIndex: 'hour1600',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1630',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '17:00', 
			dataIndex: 'hour1700',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		},{
			header: '', 
			dataIndex: 'hour1730',
			renderer: makeUp,
			width:66,
			hideable : false,
			draggable :false,
			sortable: false
		}]
}); 

/**
 * 高亮时间轴显示
 */
scheduling.platform.highlightedTimeAxis = function (gridObject) {
	var grid = gridObject;
	if(!grid) return;
	var arrayTr = document.getElementById(grid.getId()+"-body").firstChild.firstChild.firstChild.getElementsByTagName('tr');
	var trCount = arrayTr.length;
	var tdCount;
	var arrayTd;
	var date = new Date();
	var hour = date.getHours();
	var minute = date.getMinutes();
	
	var addHour = 0;
	if(minute > 45) {
		minute = 0;
		addHour = 1;
	} else if (minute < 15){
		minute = 0;
		addHour = 0;
	} else  {
		addHour = 0;
		minute = 0.5;
	}
	var timeStr = (hour + addHour) + minute;
	var map = new Ext.util.HashMap();
	var tdIndex = 1;
	for(var i = 18; true; i = i * 1 + 0.5 ) {
		if(i == 24) {
			i = 0;
		}
		if(i == 17.5) {
			map.add(i, tdIndex);
			break;
		}
		map.add(i, tdIndex);
		tdIndex ++;
	}
	var index =  map.get(timeStr) - 1;
	var style = "border-right:1px #111111 solid;border-right-width:3px;";
	if(index == 0) {
		// 涉及到第一列跨行操作
		var colSpan = 0;
		for(var i = 1; i < trCount; i++ ) {
			var tr = arrayTr[i];
			if(!tr) continue;
			if(colSpan > 0) {
				colSpan --;
				continue;
			}
			if(colSpan = 0) {
				continue;
			}
			arrayTd = tr.getElementsByTagName("td");
			if(!arrayTd) continue;
			var tdObject = arrayTd[index]; 
			if(tdObject.getAttribute("rowSpan")) {
				colSpan = tdObject.getAttribute("rowSpan");
				colSpan --;
			}
			if(!oldStyle) {
				oldStyle = "";
			}
			style = oldStyle + style;
			tdObject.setAttribute("style", style);
		}
		return;
	}
	for(var i = 1; i < trCount; i++ ) {
		var tr = arrayTr[i];
		if(!tr) continue;
		arrayTd = tr.getElementsByTagName("td");
		if(!arrayTd) continue;
		var tdObject = arrayTd[index]; 
		if(tdObject.getAttribute("colSpan")) continue;
		var oldStyle = tdObject.getAttribute("style");
		if(!oldStyle) {
			oldStyle = "";
		} 
		style = oldStyle + style;
		tdObject.setAttribute("style", style);
	}
}

/**
* 合并单元格 跨列
* @param {} grid  要合并单元格的grid对象
* @param {} cols  要合并哪几列 [1,2,4]
*/
scheduling.platform.mergeCellsTd = function (grid, cols) {
	var arrayTr = document.getElementById(grid.getId()+"-body").firstChild.firstChild.firstChild.getElementsByTagName('tr');
	var trCount = arrayTr.length;
	var td;
	var merge = function(colspanObj, removeObjs, arrayTd) {
		if(colspanObj.colspan != 1 && colspanObj.tr) {
			arrayTd = arrayTr[colspanObj.tr].getElementsByTagName("td"); 
			td = arrayTd[colspanObj.td];
			var div = td.firstChild;
			//对齐方式
			var style = div.getAttribute("style");
			style = style + "text-align: center;";
			div.setAttribute("style", style);
			//跨列
			td.colSpan = colspanObj.colspan;
			//隐身被合并的单元格
			Ext.each(removeObjs, function(obj) { 
				arrayTd = arrayTr[obj.tr].getElementsByTagName("td");
				arrayTd[obj.td].style.display = 'none';
			});
		}  
	};
	var colspanObj = {}; //{tr:1,td:2,colspan:5}
	var removeObjs = []; //{tr:2,td:2},{tr:3,td:2}]
	var tdCount;
	var arrayTd;
	for(var i = 1; i < trCount; i++ ) {
		var tr = arrayTr[i];
		if(!tr) continue;
		arrayTd = tr.getElementsByTagName("td");
		if(!arrayTd) continue;
		tdCount = arrayTd.length;
		var divHtml = null;
		var colspan = 1;
		for(var j = 0 ; j < cols.length; j++) {
			var colIndex = cols[j] - 1;
			var divs = arrayTd[colIndex].getElementsByTagName("div");
			var addf = function() { 
				colspanObj["colspan"] = colspanObj["colspan"] + 1;
				removeObjs.push({tr: i,td: colIndex});
				if(j == cols.length - 1) {
					var removeObject = new Array();
					Ext.each(removeObjs, function(obj) { 
						removeObject.push({tr: obj.tr,td: (obj.td +1)});
					});
					merge(colspanObj, removeObjs);
				}
			};
			var mergef = function() {
				merge(colspanObj, removeObjs, arrayTd);//执行合并函数
				divHtml = cellText;
				colspanObj = {tr: i, td: colIndex, colspan: colspan}
				removeObjs = [];
			};
			var htmlStr = divs[0].innerHTML;
			if(htmlStr.indexOf('nbsp') != -1) {
				mergef();
				divHtml = htmlStr;
				continue;
			}
			var cellText = arrayTd[colIndex].innerHTML;
			if(!divHtml) {
				divHtml = cellText;
				colspanObj = {tr: i, td: colIndex, colspan: colspan}
			} else {
				if(cellText == divHtml) {
					addf(); 
				} else {
					mergef();
				}
			}
		}
	}
};

scheduling.platform.mergeCellsAndHighlightedTimeAxis = function(gridObject) {
	var grid = gridObject;
	//跨行
	mergeCells(grid, [1]);
	//跨列
	var colArray = new Array();
	for(var i = 2; i <= 49; i++) {
		colArray.push(i);
	}
	scheduling.platform.mergeCellsTd(grid, colArray)
	//高亮时间轴显示
	scheduling.platform.highlightedTimeAxis(grid);
}
	
function makeUp(value, metaData, record, rowIndex, colIndex, store, view) {
	var platformNo = record.get('platformNo');
	var platformVirtualCode = record.get('platformVirtualCode');
	if(!Ext.isEmpty(value)) {
		var index = value.indexOf('_');
		var id = value.substring(index + 1)
		var text = value.substring(0, index);
		if(rowIndex % 2 == 0) {
			metaData.style = 'background: #007ACC;cursor:pointer;';
			return '<span style="color:#FFFFFF" onclick="scheduling.platform.openUpdateAndEmptyPlatformWindow(\''+ id +'\',\''+platformNo+'\',\''+platformVirtualCode+'\')">'+text+'</span>';
		} else {
			metaData.style = 'background: #C4C4C4;cursor:pointer;';
			return '<span style="color:black" onclick="scheduling.platform.openUpdateAndEmptyPlatformWindow(\''+ id +'\',\''+platformNo+'\',\''+platformVirtualCode+'\')">'+text+'</span>';
		}
	} else {
		// 空白单元格  处理使用月台
		metaData.style = 'cursor:pointer;'
		return '<span style="cursor:pointer;" onclick="scheduling.platform.openUsePlatformWindow(\''+ platformVirtualCode +'\',\''+platformNo+'\',\''+colIndex+'\')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>';
	}
}
//使用月台
scheduling.platform.openUsePlatformWindow = function (platformVirtualCode, platformNo, colIndex) {
	
	if(!scheduling.platform.isPermission('scheduling/dispatchPlatformButton')){
		return;
	}
	
	if(!platformVirtualCode || !colIndex || !platformNo) {
		return false;
	}
	var grid = scheduling.platform.platformDispatchQueryGrid;
	var columns = grid.columns;
	var time = columns[colIndex].text;
	if(time == '') {
		time = columns[colIndex - 1].text;
		var index = time.indexOf(':');
		time = time.substring(0, index + 1) + "" + "30" ;
	}
	var windowObject = Ext.create('Foss.scheduling.platformDispatch.UsePlatformWindow', {platformVirtualCode:platformVirtualCode, time:time, platformNo:platformNo});
	scheduling.platform.usePlatformWindow = windowObject;   
	windowObject.show();
	windowObject.initData();
}

scheduling.platform.openUpdateAndEmptyPlatformWindow = function (id, platformNo, platformVirtualCode) {
	
	if(!scheduling.platform.isPermission('scheduling/updatePlatformUseInfoButton')){
		return;
	}
	
	//清空  修改月台
	//ajax后台请求   如果点击的车牌号所占时间段大于等于当前时间，则弹出清空月台和修改月台界面，界面包含两个页签，否则不响应
	var params = {
				"platformDispatchVo.platformDistributeEntity.id":id
			};
	Ext.Ajax.request({
		url:scheduling.realPath('doUseTimeIfGreaterThanOrEqualCurrentTime.action'),
		params: params,
		success:function(response) {
			var result = Ext.decode(response.responseText);
			var platformDispatchVo = result.platformDispatchVo;
			if(platformDispatchVo.userTimeIfGreaterThanOrEqualCurrentTime) {
				var windowObject = Ext.create('Foss.scheduling.platformDispatch.UpdateAndEmptyPlatformWindow', {platformId:id, platformNo:platformNo, platformVirtualCode:platformVirtualCode});
				scheduling.platform.updateAndEmptyPlatformWindow = windowObject;
				windowObject.show();
				windowObject.updateAndEmptyTab.initData();
			} 
		},
		exception:function(response) {
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'), result.message);
		}
	});

}

//提示信息
Ext.define('Foss.scheduling.platformDispatch.TaskExplainContainer',{
	extend:'Ext.container.Container',
	layout:'column',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	margin: '5 0 5 30',
	defaults: {
		xtype : 'panel',
		border : false,
		height: 22,
		width: 55
	},
	items:[{
		xtype:'container',
		columnWidth:.06,
		minHeight:18,
		maxHeight:18,
		margin:'0 0 0 0',
		style: 'background-color:#007ACC;'
	},{
		html:scheduling.platform.i18n('foss.scheduling.platform.TaskExplainContainer.planVehicle'),//'&nbsp;计划停靠车辆',
		columnWidth:.1
	},{
		xtype:'container',
		columnWidth:.06,
		minHeight:18,
		maxHeight:18,
		margin:'0 0 0 0',
		style: 'background-color:#C4C4C4;'
	},{
		html:scheduling.platform.i18n('foss.scheduling.platform.TaskExplainContainer.actualVehicle'),//'&nbsp;当前停靠车辆',
		columnWidth:.1
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var platformDispatchQueryForm = Ext.create('Foss.scheduling.platformDispatch.PlatformDispatchQueryForm');
	var platformDispatchQueryGrid = Ext.create('Foss.scheduling.platformDispatch.PlatformDispatchQueryGrid');
	var taskExplainContainer = Ext.create('Foss.scheduling.platformDispatch.TaskExplainContainer',{hidden:true});
	scheduling.platform.platformDispatchQueryForm = platformDispatchQueryForm;
	scheduling.platform.platformDispatchQueryGrid = platformDispatchQueryGrid;
	
	scheduling.platform.taskExplainContainer = taskExplainContainer;
	Ext.create('Ext.panel.Panel',{
		id: 'T_scheduling-platformIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [platformDispatchQueryForm, platformDispatchQueryGrid, taskExplainContainer],
		renderTo: 'T_scheduling-platformIndex-body'
	});
	platformDispatchQueryForm.resetQueryCondition();
	Ext.Ajax.request({
		url : scheduling.realPath('queryTransferDept.action'),
		success: function(response){
			var result = Ext.decode(response.responseText);	
			Ext.apply(scheduling.platform,{
				deptName : result.platformDispatchVo.transferDeptInfo.deptName,
				deptCode : result.platformDispatchVo.transferDeptInfo.deptCode
			});
			platformDispatchQueryForm.initTransferDept(result.platformDispatchVo.transferDeptInfo.deptCode,
					result.platformDispatchVo.transferDeptInfo.deptName);
		},
		exception: function(response){
			var result = Ext.decode(response.responseText);
			platformDispatchQueryForm.handleDeptError();
			Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'), result.message);
		}
	});
});

/**
 * 修改月台 & 清空月台 页签 window
 */
Ext.define('Foss.scheduling.platformDispatch.UpdateAndEmptyPlatformWindow',{
	extend:'Ext.window.Window',
	title:'',
	width:800,
	height:350,
	modal:true,
	resizable:false,
	platformId: null,
	platformVirtualCode:null,
	updateAndEmptyTab:null,
	platformNo:null,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		this.updateAndEmptyTab = Ext.create('Foss.scheduling.platformDispatch.UpdateAndEmptyTab', {platformId:this.platformId, platformNo:this.platformNo});
		me.add([this.updateAndEmptyTab]);
	}
});

/**
 * 修改月台 & 清空月台 页签
 */
Ext.define('Foss.scheduling.platformDispatch.UpdateAndEmptyTab',{
	extend:'Ext.tab.Panel',
	activeTab:0,
	autoScroll:false,
	frame: false,
	platformId: null,
	emptyTab: null,
	updateTab: null,
	platformNo:null,
	platformDistributeEntity: null,
	constructor: function(config) {
		Ext.apply(this, config);
		this.emptyTab = Ext.create('Foss.scheduling.platformDispatch.EmptyTab'); 
		this.updateTab = Ext.create('Foss.scheduling.platformDispatch.UpdateTab',{platformNo:this.platformNo});
		this.items = [{
				title: scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.deletePlatformTitle'),//'清空月台',
				tabConfig:{width:100},
				items: this.emptyTab
			 },{
				title: scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.updatePlatformTitle'),//'修改月台',
				tabConfig:{width:100},
				items: this.updateTab
			}
		];
		this.callParent(arguments);
	}, 
	listeners:{ 
		tabchange:function(src, newCard){
			var tab = src.getActiveTab();
			var settingFun = src.emptyTabSettingFun;
			if(newCard.title == scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.updatePlatformTitle')) {
				settingFun = src.updateTabSettingFun;
			}
			src.initData(settingFun);
        } 
    },
    emptyTabSettingFun: function(platformDistributeEntity, queryProgressResultDto) {
    	var operateForm = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTab.emptyTab.operateForm;
		var findField = function(name) {
			return operateForm.getForm().findField(name); 
		};
		var formatDate = function(value, formatStr) {
			if(!formatStr) formatStr = 'Y-m-d';
			return Ext.Date.format(new Date(value), formatStr); 
		};
		// 使用开始时间
		findField('useStartTime').setValue(formatDate(platformDistributeEntity.useStartTime));
		// 使用结束时间
		findField('useEndTime').setValue(formatDate(platformDistributeEntity.useEndTime));
		
		var start_hms = formatDate(platformDistributeEntity.useStartTime, 'H:i');
		findField('useStartTime_hms').setValue(start_hms);
		
		var end_hms = formatDate(platformDistributeEntity.useEndTime, 'H:i');
		findField('useEndTime_hms').setValue(end_hms);

		
		var scheduleArea = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTab.emptyTab.scheduleArea;
		if(!queryProgressResultDto || !queryProgressResultDto.taskType){
			scheduleArea.setVisible(false);
			return false;
		}
		
		// 装卸标示
		var loadOrUnLoad = queryProgressResultDto.taskType;
		loadOrUnLoad = loadOrUnLoad + scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.progress');//进度
		
		var id = scheduleArea.id + "-legendTitle";
		document.getElementById(id).innerText = loadOrUnLoad;
	
		var date = (queryProgressResultDto.taskEndTime == null)? null:formatDate(queryProgressResultDto.taskEndTime);
		var time = (queryProgressResultDto.taskEndTime == null)? '':formatDate(queryProgressResultDto.taskEndTime, 'H:i:s');
		
		var completeTime = document.getElementById('Foss_scheduling_platformDispatch_emptyTab_completeTime');
		completeTime.innerHTML =  "<span style='color:white;'>"+scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.finishedTime')+"</span></br>"  + date + "</br>" + time ;
		
		//进度条
		var array = new Array();
		array.push(queryProgressResultDto);
		scheduling.platform.emptyTabLoadingProgressView.store.loadData(array);
		scheduling.platform.emptyTabLoadingProgressView.getStore().each(function(record){
			var progressContainer = Ext.create('Foss.scheduling.platformDispatch.emptyTab.UnLoadingProgressContainer',{
				renderTo: 'queryLoadingProgressEmptyTab_taskNumber_progress'
			});
			progressContainer.setProgress(record);					
		});
		
    },
    updateTabSettingFun: function(platformDistributeEntity, queryProgressResultDto) {
    	var operateForm = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTab.updateTab.operateForm;
		var findField = function(name) {
			return operateForm.getForm().findField(name); 
		};
		var formatDate = function(value, formatStr) {
			if(!formatStr) formatStr = 'Y-m-d';
			return Ext.Date.format(new Date(value), formatStr); 
		};
		var setttingReadOnly = function(name) {
			findField(name).setReadOnly(true);
		};
		//如果为实际停靠  月台号,开始时间不能修改
		if(platformDistributeEntity.type == 'ACTUALUSE') {
			setttingReadOnly('platformNo');	
			setttingReadOnly('useStartTime');
			setttingReadOnly('useStartTime_hms');
			findField('useEndTime').setMinValue(new Date(platformDistributeEntity.useStartTime));
		}
		// 月台号
		var platformNo = scheduling.platform.updateAndEmptyPlatformWindow.platformNo;
		var platformVirtualCode = scheduling.platform.updateAndEmptyPlatformWindow.platformVirtualCode;
		
		findField('platformNo').setCombValue(platformNo, platformVirtualCode);
		
		findField('useStartTime').setValue(formatDate(platformDistributeEntity.useStartTime));
		// 使用结束时间
		findField('useEndTime').setValue(formatDate(platformDistributeEntity.useEndTime));
		
		var start_hms = formatDate(platformDistributeEntity.useStartTime, 'H:i');
		findField('useStartTime_hms').setValue(start_hms);
		
		var end_hms = formatDate(platformDistributeEntity.useEndTime, 'H:i');
		findField('useEndTime_hms').setValue(end_hms);

		var scheduleArea = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTab.updateTab.scheduleArea;
		if(!queryProgressResultDto || !queryProgressResultDto.taskType){
			scheduleArea.setVisible(false);
			return false;
		}
		
		// 装卸标示
		var loadOrUnLoad = queryProgressResultDto.taskType;
		loadOrUnLoad = loadOrUnLoad + scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.progress');//进度
		var id = scheduleArea.id + "-legendTitle";
		document.getElementById(id).innerText = loadOrUnLoad;
		
		var date = (queryProgressResultDto.taskEndTime == null)? null:formatDate(queryProgressResultDto.taskEndTime);
		var time = (queryProgressResultDto.taskEndTime == null)? '':formatDate(queryProgressResultDto.taskEndTime, 'H:i:s');
		
		var completeTime = document.getElementById('Foss_scheduling_platformDispatch_updateTab_completeTime');
		completeTime.innerHTML = "<span style='color:white;'>"+scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.finishedTime')+"</span></br>" + date + "</br>" + time ;//
		
		//进度
		var array = new Array();
		array.push(queryProgressResultDto);
		scheduling.platform.updateTabLoadingProgressView.store.loadData(array);
		scheduling.platform.updateTabLoadingProgressView.getStore().each(function(record){
			var progressContainer = Ext.create('Foss.scheduling.platformDispatch.updateTab.UnLoadingProgressContainer',{
				renderTo: 'queryLoadingProgressUpdateTab_taskNumber_progress'
			});
			progressContainer.setProgress(record);					
		});
    },
	initData: function(settingValueFun) {
		//var tab = this.getActiveTab();
		if(!settingValueFun) 
			settingValueFun = this.emptyTabSettingFun;  
		var id = this.platformId;
		var params = {"platformDispatchVo.platformDistributeEntity.id": id};
		Ext.Ajax.request({
			url:scheduling.realPath('queryPlatformDistributeInfo.action'),
			params: params,
			success:function(response) {
				var result = Ext.decode(response.responseText);
				var platformDistributeEntity = result.platformDispatchVo.platformDistributeEntity;
				var queryProgressResultDto = result.platformDispatchVo.queryProgressResultDto;
				scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTabPlatformEntity = platformDistributeEntity;
				settingValueFun(platformDistributeEntity, queryProgressResultDto);
			},
			exception:function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'),result.message);
			}
		})
	}
});

Ext.define('Foss.scheduling.platformDispatch.UpdateAndEmptyTab.LoadingProgressModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'taskType', type: 'string'},
		{name: 'taskEndTime', type: 'date'},
		{name: 'volumeProgress', type: 'string'},
		{name: 'weightProgress', type: 'string'}
	]
});

Ext.define('Foss.scheduling.platformDispatch.UpdateAndEmptyTab.LoadingProgressStore',{
	extend: 'Ext.data.Store',
	model:'Foss.scheduling.platformDispatch.UpdateAndEmptyTab.LoadingProgressModel',
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 清空页签
 */
//定义一个查看进度的组件
Ext.define('Foss.scheduling.platformDispatch.emptyTab.UnLoadingProgressContainer', {
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
	setProgress : function(record){
		
		var volumeProgressStr = record.get('volumeProgress');
		var weightprogressStr = record.get('weightProgress');
		
		var volumeProgress = volumeProgressStr.substring(0, volumeProgressStr.indexOf('%'));
		var weightprogress = weightprogressStr.substring(0, weightprogressStr.indexOf('%'));
		
		volumeProgress = volumeProgress / 100;
		this.getVolumeProgress().updateProgress(volumeProgress, volumeProgressStr);

		var volumeProgressCls = "volumeProgress";
		if(volumeProgress<0.8){
			var volumeProgressCls = "below80";
		}else if(volumeProgress>=0.8&&volumeProgress<0.9){
			var volumeProgressCls = "over80";
		}else if(volumeProgress>=0.9&&volumeProgress<1){
			this.getVolumeProgress().addCls('over90');
		}
		this.getVolumeProgress().addCls(volumeProgressCls);
		
		weightprogress = weightprogress / 100;
		this.getWeightProgress().updateProgress(weightprogress, weightprogressStr);
		var cls = "complete";
		if(weightprogress<0.8){
			cls = 'below80';
		}else if(weightprogress>=0.8&&weightprogress<0.9){
			cls = 'over80';
		}else if(weightprogress>=0.9&&weightprogress<1){
			cls = 'over90';
		}
		this.getWeightProgress().addCls(cls);
	},
	initComponent: function(){
		var me = this;
		me.items = [{
			border : false,
			columnWidth:.3,
			html: '<span>'+scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.volumeProgress')+'</span>'
		},me.getVolumeProgress(),{
			border : false,
			columnWidth:.3,
			html: '<span>'+scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.weightProgress')+'</span>'
		},me.getWeightProgress()];
		me.callParent();
	}
});

Ext.define('Foss.scheduling.platformDispatch.emptyTab.EmptyTabLoadingProgressView', {
	extend: 'Ext.view.View',
	frame: false,
	columnWidth: .6,
	autoScroll: false,
	bodyCls: 'autoHeight',
//	cls: 'autoHeight x-panel-default-view',
	tpl: null,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.platformDispatch.UpdateAndEmptyTab.LoadingProgressStore');
		me.itemSelector = 'div.foss_queryLoadingProgress_thumb_wrap',
		me.tpl = new Ext.XTemplate(
			'<tpl for=".">',
				'<div class="foss_queryLoadingProgress_thumb_wrap">',
					'<div id="foss_queryLoadingProgress_taskNumber_loading" class="foss_queryLoadingProgress_search_task">',
						'<div class="foss_queryLoadingProgress_top_task">',
							'<div id="queryLoadingProgressEmptyTab_taskNumber_progress" class="foss_queryLoadingProgress_loading"></div>',
						'</div>',
					'</div>',
				'</div>',
			'</tpl>'
		);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.platformDispatch.EmptyTab',{
		extend:'Ext.panel.Panel',
		width:800,
		height:340,
		margin:'10 5 10 10',
		frame:false,
		layout:'column',
		//清空
		emptyArea: null,
		//进度区域
		scheduleArea: null,
		operateForm: null,
		createEmptyArea: function() {
			if(this.emptyArea) {
				return this.emptyArea;
			}
			this.emptyArea = Ext.create('Ext.form.FieldSet',{
				defaultType: 'textfield',
				title:scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.EmptyTab.FieldSet.title'),//'清空月台',
				width:720,
				items:[{
					xtype: 'container',
					layout:'column',
					defaultType:'textfield',
					defaults:{
						labelWidth: 85,
						margin: '5 10 5 10'
					},
				    items: [{ 
				    	xtype:'container',
				    	html:'&nbsp',
				    	columnWidth:1
				    },{ 
				    	xtype:'container',
				    	html:'&nbsp;',
				    	columnWidth: .1
				    },{ 
				    	labelWidth: 45,
				    	name:'useStartTime',
				    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.EmptyTab.FieldSet.useStartTime'),//'时间', 
				    	format: 'Y-m-d',
				    	xtype:'datefield',
				    	editable: false,
				    	readOnly:true,
				    	columnWidth: .28
				    },{ 
				    	xtype: 'timefield',
			            fieldLabel: '',
			            altFormats: 'H:i|g:i A',
			            format: 'H:i',
			            name:'useStartTime_hms',
			            increment: 30,
			            readOnly:true,
				    	columnWidth: .17
				    },{ 
				    	name:'useEndTime',
				    	labelWidth: 45,
				    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.EmptyTab.FieldSet.to'),//'至',
				    	xtype:'datefield',
				    	format: 'Y-m-d',
				    	editable: false,
				    	readOnly:true,
				    	columnWidth: .28
				    },{ 
				    	xtype: 'timefield',
			            fieldLabel: '',
			            altFormats: 'H:i|g:i A',
			            format: 'H:i',
			            increment: 30,
			            readOnly:true,
			            name:'useEndTime_hms',
				    	columnWidth: .17
				    },{ 
				    	xtype:'container',
				    	html:'&nbsp',
				    	columnWidth: .8
				    },{ 
				    	xtype:'button',
				    	text:scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.EmptyTab.FieldSet.sureDeleteButton'),//'确认清空',
				    	columnWidth: .2,
				    	handler:function(){
				    		var formatDate = function(value, formatStr) {
				    			if(!formatStr) formatStr = 'Y-m-d';
				    			return Ext.Date.format(new Date(value), formatStr); 
				    		};
				        	var operateForm = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTab.emptyTab.operateForm;
				        	var useEndTimeObject = operateForm.getForm().findField('useEndTime');
				        	var useEndTimeValue = useEndTimeObject.getValue();
				        	var useEndTime_hmsObject = operateForm.getForm().findField('useEndTime_hms');
				        	var useEndTime_hmsValue = useEndTime_hmsObject.getValue();
				        	var value = formatDate(useEndTimeValue) + " " + formatDate(useEndTime_hmsValue, 'H:i:s')
				        	
				    		var updateAndEmptyTabPlatformEntity = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTabPlatformEntity;
				    		updateAndEmptyTabPlatformEntity.useEndTime = value;
				    		
				    		var platformDispatchVo = {platformDispatchVo:{platformDistributeEntity:updateAndEmptyTabPlatformEntity}};
				    		
				    		Ext.Ajax.request({
				    			url:scheduling.realPath('clearPlatform.action'),
				    			jsonData:platformDispatchVo,
				    			success:function(response) {
				    				scheduling.platform.updateAndEmptyPlatformWindow.close();
				    				scheduling.platform.platformDispatchQueryForm.queryPlatformDispatchUseInfo();
				    			},
				    			exception:function(response) {
				    			}
				    		});
						}
				    }]
				}]
			})
			return this.emptyArea;
		},
		createOperateForm: function() {
			var form = this.operateForm;
			if(form) {
				return form;
			}
			form  = Ext.create('Ext.form.Panel', {
			    frame: false
			});
			this.operateForm = form;
			return this.operateForm;
		},
		createScheduleArea: function() {
			if(this.scheduleArea) {
				return this.scheduleArea;
			}
			var loadingprogressView = Ext.create('Foss.scheduling.platformDispatch.emptyTab.EmptyTabLoadingProgressView');
			scheduling.platform.emptyTabLoadingProgressView = loadingprogressView;
			this.scheduleArea = Ext.create('Ext.form.FieldSet',{
				defaultType: 'textfield',
				title:scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.progress'),//'进度',
				width:720,
				height:90,
				items:[{
					xtype: 'container',
					layout:'column',
					defaultType:'textfield',
					defaults:{
						labelWidth: 85,
						margin: '-2 10 5 10'
					},
				    items: [{ 
				    	xtype:'container',
				    	html:'&nbsp',
				    	columnWidth: .15
				    },loadingprogressView,{
				    	xtype:'container',
				    	margin: '0 10 5 10',
				    	style:'font-weight: bold;background-color:#B3B3B3;text-align:center;',
				    	id:'Foss_scheduling_platformDispatch_emptyTab_completeTime',
				    	html:'',
				    	frame:true
				    }]
				}]
			});
			return this.scheduleArea;
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([cfg]);
			me.createEmptyArea();
			me.createScheduleArea();
			
			var form = this.createOperateForm();
			form.add([me.emptyArea, me.scheduleArea]);
//			me.add([me.emptyArea, me.scheduleArea]);
			me.add([form]);
		}
});

/**
 * 修改页签
 */
Ext.define('Foss.scheduling.platformDispatch.updateTab.UnLoadingProgressContainer', {
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
	setProgress : function(record){
		
		var volumeProgressStr = record.get('volumeProgress');
		var weightprogressStr = record.get('weightProgress');
		
		var volumeProgress = volumeProgressStr.substring(0, volumeProgressStr.indexOf('%'));
		var weightprogress = weightprogressStr.substring(0, weightprogressStr.indexOf('%'));
		
		volumeProgress = volumeProgress / 100;
		this.getVolumeProgress().updateProgress(volumeProgress, volumeProgressStr);

		var volumeProgressCls = "volumeProgress";
		if(volumeProgress<0.8){
			var volumeProgressCls = "below80";
		}else if(volumeProgress>=0.8&&volumeProgress<0.9){
			var volumeProgressCls = "over80";
		}else if(volumeProgress>=0.9&&volumeProgress<1){
			this.getVolumeProgress().addCls('over90');
		}
		this.getVolumeProgress().addCls(volumeProgressCls);
		
		weightprogress = weightprogress / 100;
		this.getWeightProgress().updateProgress(weightprogress, weightprogressStr);
		var cls = "complete";
		if(weightprogress<0.8){
			cls = 'below80';
		}else if(weightprogress>=0.8&&weightprogress<0.9){
			cls = 'over80';
		}else if(weightprogress>=0.9&&weightprogress<1){
			cls = 'over90';
		}
		this.getWeightProgress().addCls(cls);
	},
	initComponent: function(){
		var me = this;
		me.items = [{
			border : false,
			columnWidth:.3,
			html: '<span>'+scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.volumeProgress')+'</span>'
		},me.getVolumeProgress(),{
			border : false,
			columnWidth:.3,
			html: '<span>'+scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.weightProgress')+'</span>'
		},me.getWeightProgress()];
		me.callParent();
	}
});


Ext.define('Foss.scheduling.platformDispatch.updateTab.UpdateTabLoadingProgressView', {
	extend: 'Ext.view.View',
	frame: false,
	columnWidth: .6,
	autoScroll: false,
	bodyCls: 'autoHeight',
	tpl: null,
	constructor: function(config){
		var me = this,
			nowDate = new Date(),
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.platformDispatch.UpdateAndEmptyTab.LoadingProgressStore');
		me.itemSelector = 'div.foss_queryLoadingProgress_thumb_wrap',
		me.tpl = new Ext.XTemplate(
			'<tpl for=".">',
				'<div class="foss_queryLoadingProgress_thumb_wrap">',
					'<div id="foss_queryLoadingProgressUpdateTab_taskNumber_loading" class="foss_queryLoadingProgress_search_task">',
						'<div class="foss_queryLoadingProgress_top_task">',
							'<div id="queryLoadingProgressUpdateTab_taskNumber_progress" class="foss_queryLoadingProgress_loading"></div>',
						'</div>',
					'</div>',
				'</div>',
			'</tpl>'
		);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.scheduling.platformDispatch.UpdateTab',{
		extend:'Ext.panel.Panel',
		width:800,
		height:340,
		margin:'10 5 10 10',
		frame:false,
		layout:'column',
		//清空
		emptyArea: null,
		//进度区域
		scheduleArea: null,
		platformNo:null,
		createEmptyArea: function() {
			if(this.emptyArea) {
				return this.emptyArea;
			}
			this.emptyArea = Ext.create('Ext.form.FieldSet',{
				defaultType: 'textfield',
				title:'',
				width:720,
				items:[{
					xtype: 'container',
					layout:'column',
					defaults:{
						labelWidth: 85,
						defaultType:'textfield',
						margin: '05 10 05 10'
					},
				    items: [{
				    	name:'platformNo',
				    	xtype:'commonplatformselectorByVirtualCode',
				    	orgCode: FossUserContext.getCurrentDept().code,
				    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.UpdateTab.FieldSet.platformNo'),//'月台号', 
				    	columnWidth: .35
				    },{ 
				    	xtype:'container',
				    	html:'&nbsp',
				    	height: 30,
				    	columnWidth: .65
				    },{ 
				    	labelWidth: 85,
				    	name:'useStartTime',
				    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.UpdateTab.FieldSet.time'),//'时间', 
				    	format: 'Y-m-d',
				    	editable: false,
				    	xtype:'datefield',
				    	columnWidth: .35
				    },{ 
				    	xtype: 'timefield',
				    	name:'useStartTime_hms',
			            fieldLabel: '',
			            altFormats: 'H:i|g:i A',
			            format: 'H:i',
			            increment: 30,
			            editable :false,
				    	columnWidth: .17
				    },{ 
				    	labelWidth: 45,
				    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.UpdateTab.FieldSet.to'),//'至',
				    	format: 'Y-m-d',
				    	xtype:'datefield',
				    	editable :false,
				    	minValue: new Date(),
				    	name:'useEndTime',
				    	columnWidth: .28
				    },{ 
				    	xtype: 'timefield',
			            fieldLabel: '',
			            altFormats: 'H:i|g:i A',
			            format: 'H:i',
			            increment: 30,
			            editable :false,
			            name:'useEndTime_hms',
				    	columnWidth: .17
				    },{ 
				    	xtype:'container',
				    	html:'&nbsp',
				    	columnWidth: .8
				    },{ 
				    	xtype:'button',
				    	columnWidth: .2,
				    	text:scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.UpdateTab.FieldSet.sureUpdateButton'),//'确认修改',
				    	handler:function(){
				    		var operateForm = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTab.updateTab.operateForm;
				    		// 日期格式化
				    		var formatDate = function(value, formatStr) {
				    			if(!formatStr) formatStr = 'Y-m-d';
				    			return Ext.Date.format(new Date(value), formatStr); 
				    		};
				    		// 查找dom元素
				    		var findField = function(name) {
				    			return operateForm.getForm().findField(name); 
				    		};
				    		//月台号
			    			var platformNoObject = findField('platformNo')
			    			var platformNoValue = platformNoObject.getValue();
			    			
			    			// 开始时间
				        	var useStartTimeObject = findField('useStartTime');
				        	var useStartTimeValue = useStartTimeObject.getValue();
				        	var useStartTime_hmsObject = findField('useStartTime_hms');
				        	var useStartTime_hmsValue = useStartTime_hmsObject.getValue();
				        	
				        	// 结束时间
				        	var useEndTimeObject = findField('useEndTime');
				        	var useEndTimeValue = useEndTimeObject.getValue();
				        	var useEndTime_hmsObject = findField('useEndTime_hms');
				        	var useEndTime_hmsValue = useEndTime_hmsObject.getValue();
				        	
				        	// 校验   起始时间不能小于当前时间 & 结束时间必须大于开始时间
				        	var userStartTime = formatDate(useStartTimeValue) + " " + formatDate(useStartTime_hmsValue, 'H:i:s');
				        	var userEndTime = formatDate(useEndTimeValue) + " " + formatDate(useEndTime_hmsValue, 'H:i:s');
				        	
				        	var userStartDate = new Date(userStartTime);
				        	var userEndDate = new Date(userEndTime);
				        	// 当前时间
				        	var nowDate = new Date();
				        	// window 中保存的实体
				    		var updateAndEmptyTabPlatformEntity = scheduling.platform.updateAndEmptyPlatformWindow.updateAndEmptyTabPlatformEntity;
				    		// 停靠类型
				    		var type = updateAndEmptyTabPlatformEntity.type;
				    		// 修改提交操作
				    		var updateFun =  function() {
					        	//月台号
					        	updateAndEmptyTabPlatformEntity.platformNo = platformNoValue;
					        	//月台开始使用时间
					        	updateAndEmptyTabPlatformEntity.useStartTime = userStartTime;
					        	//月台结束时间
					    		updateAndEmptyTabPlatformEntity.useEndTime = userEndTime;
					    		// 
					    		var platformDispatchVo = {platformDispatchVo:{platformDistributeEntity:updateAndEmptyTabPlatformEntity}};
					    		Ext.Ajax.request({
					    			url:scheduling.realPath('updatePlatformUseInfo.action'),
					    			jsonData:platformDispatchVo,
					    			success:function(response) {
					    				scheduling.platform.updateAndEmptyPlatformWindow.close();
					    				scheduling.platform.platformDispatchQueryForm.queryPlatformDispatchUseInfo();
					    			},
					    			exception:function(response) {
					    				var result = Ext.decode(response.responseText);
										Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'), result.message);
					    			}
				    			});
				    		};
				    		
				    		// 计划来源
				    		var scheduleSource = updateAndEmptyTabPlatformEntity.scheduleSource;
				    		// 发车计划 || 装卸车
				    		var confirmMessage;
				    		// 如果停靠类型为 计划 & 计划来源  发车计划 和卸车 修改前confirm确认
				    		if(type == 'PLAN' && (scheduleSource== 'STARTSCHEDULE' || scheduleSource == 'UNLOAD' )) {
				    			confirmMessage = scheduleSource== 'STARTSCHEDULE' ? scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.UpdateTab.FieldSet.updatePlanHint'): scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.UpdateTab.FieldSet.updateUnloadHint');  
				    		}
				    		// 非计划停靠  confirm确认是否修改
				    		if(!confirmMessage) {
				    			confirmMessage = scheduling.platform.i18n('foss.scheduling.platform.UnLoadingProgressContainer.UpdateTab.FieldSet.updateHint');//'您确认要修改吗？';
				    		}
				    		// confirm提示  计划停靠
			    			Ext.MessageBox.confirm(scheduling.platform.i18n('foss.scheduling.platform.hint'), confirmMessage, function(button){
			    				 if(button == 'yes') {
			    					 // 执行保存操作.
			    					 updateFun();
			    				 }
				    		});
						}
				    }]
				}]
			})
			return this.emptyArea;
		},
		createScheduleArea: function() {
			if(this.scheduleArea) {
				return this.scheduleArea;
			}
			var loadingprogressView = Ext.create('Foss.scheduling.platformDispatch.updateTab.UpdateTabLoadingProgressView');
			scheduling.platform.updateTabLoadingProgressView = loadingprogressView;
			this.scheduleArea = Ext.create('Ext.form.FieldSet',{
				defaultType: 'textfield',
				title:scheduling.platform.i18n('foss.scheduling.platform.UpdateAndEmptyTab.progress'),//'进度',
				width:720,
				height:90,
				items:[{
					xtype: 'container',
					layout:'column',
					defaultType:'textfield',
					defaults:{
						labelWidth: 85,
						margin: '-2 10 5 10'
					},
				    items: [{ 
				    	xtype:'container',
				    	html:'&nbsp',
				    	columnWidth: .15
				    },loadingprogressView,{
				    	xtype:'container',
				    	margin: '0 10 5 10',
				    	style:'font-weight: bold;background-color:#B3B3B3;text-align:center;',
				    	id:'Foss_scheduling_platformDispatch_updateTab_completeTime',
				    	html:'',
				    	frame:true
				    }]
				}]
			})
			return this.scheduleArea;
		},
		createOperateForm: function() {
			if(this.operateForm) {
				return this.operateForm;
			}
			this.operateForm  = Ext.create('Ext.form.Panel', {
			    frame: false
			});
			return this.operateForm;
		},
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.callParent([cfg]);
			me.createEmptyArea();
			me.createScheduleArea();
			
			var operateForm = this.createOperateForm();
			operateForm.add([me.emptyArea, me.scheduleArea]);
			me.add([operateForm]);
		}
});

/**
 * 月台详情 window
 */
Ext.define('Foss.scheduling.platformDispatch.PlatformDetailWindow',{
	extend:'Ext.window.Window',
	title:scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.title'),//'月台详情',
	width:400,
	height:340,
	resizable:false,
	closable: true,
	closeAction: 'hide',
	modal:true,
	platformNo: null,
	detail: null,
	operateForm:null,
	bindData:function(record) {
		this.platformNo = record.get('platformVirtualCode');
	},
	createDetail: function() {
		if(this.detail) {
			return this.detail;
		}
		this.detail = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title:'',
			width:370,
			items:[{
				xtype: 'container',
				layout:'vbox',
				defaultType:'textfield',
				defaults:{
					labelWidth: 100,
					margin: '2 10 2 10'
				},
			    items: [{
			    	name:'platformCode',
			    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.platformCode'),//'月台编号'
			    },{ 
			    	name:'height',
			    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.height'),//'离地面高度(米)'
			    },{
					name:'width',
					fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.width'),//'宽度(米)'
				},{
					name:'hasLift',
					fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.hasLift'),//'是否有升降台 '
				},{
					name:'pointFourPointTwo',
					fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.pointFourPointTwo'),//'是否可停4.2米 '
				},{
					name:'pointSixPointFive',
					fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.pointSixPointFive'),//'是否可停6.5米'
				},{
					name:'pointSevenPointSix',
					fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.pointSevenPointSix'),//'是否可停7.6米'
				},{
					name:'pointNinePointSix',
					fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.pointNinePointSix'),//'是否可停9.6米'
				},{
					name:'pointSeventeenPointFive',
					fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.FieldSet.pointSeventeenPointFive'),//'是否可停17.5米'
				}]
			}]
		})
		return this.detail;
	},
	createOperateForm: function() {
		if(this.operateForm) {
			return this.operateForm;
		}
		this.operateForm  = Ext.create('Ext.form.Panel', {
		    frame: false
		});
		return this.operateForm;
	},
	initData: function(){
		var platformVirtualCode = this.platformNo;
		var params = {"platformDispatchVo.platformDistributeDto.platformVirtualCode":platformVirtualCode};
		var form = this.operateForm.getForm();
		var setValue = function (name, value) {
			var obj = form.findField(name);
			obj.setValue(value);
		}
		Ext.Ajax.request({
			url:scheduling.realPath('queryPlatformDetail.action'),
			params:params,
			success:function(response) {
				var result = Ext.decode(response.responseText);
				var platformEntity = result.platformDispatchVo.platformEntity;
				if(!platformEntity) {
					return false;
				}
				setValue('platformCode', platformEntity.platformCode);
				setValue('height', isNaN(platformEntity.height) ? '' : platformEntity.height / 1000 );
				setValue('width', isNaN(platformEntity.width) ? '' : platformEntity.width / 1000 );
				setValue('hasLift', platformEntity.hasLift == 'Y' ?scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.have'):scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.not'));
				
				//可停靠车型
				setValue('pointFourPointTwo', platformEntity.fourPointTwo == 'Y'? scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.yes'):scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.no'));
				setValue('pointSixPointFive', platformEntity.sixPointFive == 'Y'? scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.yes'):scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.no'));
				setValue('pointSevenPointSix', platformEntity.sevenPointSix == 'Y'? scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.yes'):scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.no'));
				setValue('pointNinePointSix', platformEntity.ninePointSix == 'Y'? scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.yes'):scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.no'));
				setValue('pointSeventeenPointFive', platformEntity.seventeenPointFive == 'Y'? scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.yes'):scheduling.platform.i18n('foss.scheduling.platform.PlatformDetailWindow.no'));

			},
			exception:function(response) {
				
			}
		});
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.createDetail();
		var operateForm = this.createOperateForm();
		operateForm.add([me.detail]);
		setFormEditAble(operateForm, false);
		me.add([operateForm]);
	},
	listeners: {
		beforeshow: function() {
			this.initData();
		}
	 }
});

/**
 * 使用月台
 */
Ext.define('Foss.scheduling.platformDispatch.UsePlatformWindow',{
	extend:'Ext.window.Window',
	title:scheduling.platform.i18n('foss.scheduling.platform.UsePlatformWindow.title'),//'使用月台',
	width:800,
	height:240,
	resizable:false,
	modal:true,
	closable: true,
	closeAction: 'hide',
	platformId: null,
	detail: null,
	operateForm:null,
	layout:'column',
	platformNo:null, 
	platformVirtualCode:null,
	time:null,
	createDetail: function() {
		if(this.detail) {
			return this.detail;
		}
		this.detail = Ext.create('Ext.form.FieldSet',{
			defaultType: 'textfield',
			title:'',
			width:750,
			items:[{
				xtype: 'container',
				layout:'column',
				defaultType:'textfield',
				defaults:{
					labelWidth: 100,
					margin: '5 10 5 10'
				},
			    items: [{
			    	name:'platformNo',
			    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UsePlatformWindow.FieldSet.platformNo'),//'月台号',
			    	readOnly:true,
			    	columnWidth: .35
			    },{ 
			    	xtype:'container',
			    	html:'&nbsp',
			    	height: 30,
			    	columnWidth: .65
			    },{ 
			    	name:'vehicleNo',
			    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UsePlatformWindow.FieldSet.vehicleNo'),//'车牌号', 
			    	xtype:'commontruckselector',
			    	columnWidth: .35
			    },{ 
			    	xtype:'container',
			    	html:'&nbsp',
			    	height: 30,
			    	columnWidth: .65
			    },{ 
			    	labelWidth: 99,
			    	name:'useStartTime',
			    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UsePlatformWindow.FieldSet.useStartTime'),//'时间', 
			    	format: 'Y-m-d',
			    	editable: false,
			    	xtype:'datefield',
			    	columnWidth: .33
			    },{ 
			    	xtype: 'timefield',
		            fieldLabel: '',
		            name:'useStartTime_hms',
		            altFormats: 'H:i|g:i A',
		            format: 'H:i',
		            increment: 30,
		            editable: false,
			    	columnWidth: .17
			    },{ 
			    	labelWidth: 60,
			    	name:'useEndTime',
			    	fieldLabel: scheduling.platform.i18n('foss.scheduling.platform.UsePlatformWindow.FieldSet.to'),//'至',
			    	format: 'Y-m-d',
			    	xtype:'datefield',
			    	editable: false,
			    	columnWidth: .27
			    },{ 
			    	xtype: 'timefield',
		            fieldLabel: '',
		            altFormats: 'H:i|g:i A',
		            format: 'H:i',
		            increment: 30,
		            name:'useEndTime_hms',
		            editable: false,
			    	columnWidth: .17
			    },{ 
			    	xtype:'container',
			    	html:'&nbsp',
			    	height: 30,
			    	columnWidth: .65
			    },{
			    	xtype:'button',
					text: scheduling.platform.i18n('foss.scheduling.platform.UsePlatformWindow.FieldSet.sureUse'),//'确认使用',
					columnWidth: .18,
					handler:function(){
						var operateForm = scheduling.platform.usePlatformWindow.operateForm;
						var findField = function(name) {
							return operateForm.getForm().findField(name); 
						};
			    		// 日期格式化
			    		var formatDate = function(value, formatStr) {
			    			if(!formatStr) formatStr = 'Y-m-d';
			    			return Ext.Date.format(new Date(value), formatStr); 
			    		};
						var values = operateForm.getForm().getValues();
						var useStartTime_hms = findField('useStartTime_hms').getValue();
						var useEndTime_hms = findField('useEndTime_hms').getValue();
						
						useStartTime_hms = formatDate(useStartTime_hms, 'H:i:s')
						useEndTime_hms = formatDate(useEndTime_hms, 'H:i:s');

						
						var useStartTime = null;
						var useEndTime = null;
						if(!Ext.isEmpty(values.useStartTime) && !Ext.isEmpty(useStartTime_hms)){
							useStartTime = values.useStartTime + " " + useStartTime_hms;
						}
						if(!Ext.isEmpty(values.useEndTime) && !Ext.isEmpty(useEndTime_hms)){
							useEndTime = values.useEndTime + " " + useEndTime_hms;
						}
						
						// 虚拟编号
						var platformVirtualCode = scheduling.platform.usePlatformWindow.platformVirtualCode;
						// 月台分配表中对应的编号为虚拟编号
						var platformDistributeEntity = {
								"platformNo":platformVirtualCode,
								"useStartTime":useStartTime,
								"useEndTime":useEndTime,
								"vehicleNo":values.vehicleNo
						};
						var platformDispatchVo = {platformDispatchVo:{platformDistributeEntity:platformDistributeEntity}};
						Ext.Ajax.request({
		    				url : scheduling.realPath('dispatchPlatform.action'),
		    				jsonData: platformDispatchVo,
		    				success:function(response){
		    					Ext.ux.Toast.msg(scheduling.platform.i18n('foss.scheduling.platform.hint'), scheduling.platform.i18n('foss.scheduling.platform.UsePlatformWindow.FieldSet.opeateSuccess'));
		    					scheduling.platform.usePlatformWindow.close();
			    				scheduling.platform.platformDispatchQueryForm.queryPlatformDispatchUseInfo();
		    				},
		    				exception: function(response) {
		    				   var result = Ext.decode(response.responseText);
		  					   Ext.MessageBox.alert(scheduling.platform.i18n('foss.scheduling.platform.hint'), result.message);
		  				   }
		    			});	
					}
				}]
			}]
		})
		return this.detail;
	},
	createOperateForm: function() {
		if(this.operateForm) {
			return this.operateForm;
		}
		this.operateForm  = Ext.create('Ext.form.Panel', {
		    frame: false
		});
		return this.operateForm;
	},
	initData: function(){
		var operateForm = this.operateForm;
		var findField = function(name) {
			return operateForm.getForm().findField(name); 
		};
		var formatDate = function(value, formatStr) {
			if(!formatStr) formatStr = 'Y-m-d';
			return Ext.Date.format(new Date(value), formatStr); 
		};
		findField('platformNo').setValue(this.platformNo);
		var ymd = formatDate(new Date());
		findField('useStartTime').setValue(ymd);
		
		var date = new Date(); 
		var hour = date.getHours()
		var minutes = date.getMinutes() * 1;
		if(minutes < 30) {
			minutes  = "00";
		}
		if(minutes  > 30) {
			minutes  = 30;
		}
		hour = (hour < 10 ? ("0" + hour) : hour);
		var startHms = hour  + ":" + minutes;
		findField('useStartTime_hms').setValue(startHms);
		
		
		
		var finalDate = new Date(ymd);
		finalDate.setHours(hour);
		finalDate.setMinutes(minutes);
		
		var endMinutes = finalDate.getMinutes() * 1 + 30;
		finalDate.setMinutes(endMinutes);
		var endHour = finalDate.getHours()
		endMinutes = finalDate.getMinutes();
		
		var endYmd = formatDate(finalDate);;
		findField('useEndTime').setValue(endYmd);
		if(endMinutes == 0) {
			endMinutes = "00";
		}
		var endHms = (endHour < 10 ? ("0" + endHour) : endHour) + ":" + endMinutes;
		findField('useEndTime_hms').setValue(endHms);
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		me.createDetail();
		var operateForm = this.createOperateForm();
		operateForm.add([me.detail]);
		me.add([operateForm]);
	}
});
