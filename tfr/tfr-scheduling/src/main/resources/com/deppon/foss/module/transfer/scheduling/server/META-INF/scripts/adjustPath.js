/** **************1.调整走货路径界面 begin************* */
//id序列
scheduling.adjustTransportationPath.index = 0;
// 最上层 原路径 调整截止时间区域
Ext.define("Foss.adjustTransportationPath.AdjustFormTop", {
	extend : 'Ext.form.Panel',
	bodyStyle : 'padding:5px 5px 0',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	layout : 'column',
	defaultType : 'displayfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.origPath'),
			allowBlank : true,
			name : 'originalPath',
			columnWidth : .5,
			value : ''
		}, {
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.changeEndTime'),
			id : 'Foss_adjustTransportPath_datetimefieldForm_ID',
			time : true,
			format : 'Y-m-d H:i:s',
			xtype : 'datetimefield_date97',
			dateConfig : {
				el : 'Foss_adjustTransportPath_datetimefieldForm_ID-inputEl'
			}
		}, {
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.newPath'),
			allowBlank : true,
			name : 'newPath',
			columnWidth : .5,
			value : ''
		}, {
			fieldLabel : '',
			columnWidth : .5,
			value : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.notes')
		}];
		me.callParent([cfg]);
	}
});

// 调整输入区域
Ext.define("Foss.adjustTransportationPath.AdjustFormInput", {
	extend : 'Ext.form.Panel',
	bodyStyle : 'padding:5px 5px 0',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : true,
	layout : 'column',
	defaultType : 'textfield',
	findObjectiveOrgCode : function(rengong){
		if(!rengong){
			return;
		}
		var params = {
			schedulingVO : {
				changePathEntity : {
					'origOrgCode' : rengong
				}
			}
		},
		me = this;
		Ext.Ajax.request({
			url : scheduling.realPath('findObjectiveOrgCode.action'),
			jsonData : params,
			success : function(response) {
				var result = Ext.decode(response.responseText),
					nextOrgCode = result.schedulingVO.nextOrgCode,
					nextOrgName = result.schedulingVO.nextOrgName,
					orgCodeArray = new Array();
				for(var i=0;i<nextOrgCode.length;i++){
					orgCodeArray.push({'value': nextOrgCode[i],'name': nextOrgName[i]});
				}
				me.getForm().findField('rengong').store.loadData(orgCodeArray);
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert(title, result.message);
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.RadioModel');
		scheduling.adjustTransportationPath.index++;
		me.items = [{
			labelStyle : 'padding:0px 0px 8px;',
			fieldStyle : 'padding:0px 0px 8px;',
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.nowOrg'),
			name : 'nowWhich',
			xtype : 'textfield',
			value : scheduling.adjustTransportationPath.nowWhichName,
			allowBlank : true,
			readOnly : true,
			columnWidth : .5
		}, {
			labelStyle : 'padding:0px 0px 8px;',
			fieldStyle : 'padding:0px 0px 8px;',
			hidden : true,
			readOnly : true,
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.origDestOrg'),
			xtype : 'textfield',
			name : 'defaultNextStation',
			allowBlank : true,
			columnWidth : .5
		}, {
			name : 'rengong',
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.newDestOrg'),
			xtype : 'combobox',
			store: Ext.create('Ext.data.Store', {
			    fields: ['value','name']
			}),
			columnWidth : .7,
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			editable : false,
			forceSelection : true,
			triggerAction : 'all',
			listeners : {
				select : function(src, records, index) {
					var addAreaContainer = scheduling.adjustTransportationPath.adjustTransportPath.addAreaContainer;
					var items = addAreaContainer.items;
					var adjustLinesGrid;
					if (items
							&& items.length != 0
							&& items.items[items.length - 1].xtype == 'gridpanel') {
						adjustLinesGrid = items.items[items.length - 1];
					} else {
						adjustLinesGrid = Ext
								.create('Foss.adjustTransportationPath.AdjustLinesGrid');
						addAreaContainer.add(adjustLinesGrid);
					}
					// 参数
					// 这里创建grid 并且加载数据
					var params = {
						schedulingVO : {
							changePathEntity : {
								'origOrgCode' : scheduling.adjustTransportationPath.nowWhichCode,
								'destOrgCode' : src.lastValue
							}
						}
					};
					Ext.Ajax.request({
						url : scheduling.realPath('findDepartMsg.action'),
						jsonData : params,
						success : function(response) {
							var result = Ext.decode(response.responseText);
							adjustLinesGrid.store
									.loadData(result.schedulingVO.departureDto)
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.MessageBox.alert(title, result.message);
						}
					});
				}
			}
		}, {
			xtype : 'container',
			columnWidth : .2,
			html : '&nbsp;'
		}, {
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.modifyStartTime'),
			id : 'Foss_adjustTransportPath_modifyStartTime_ID'+(scheduling.adjustTransportationPath.index),
			name : 'startTime',
			time : true,
			format : 'Y-m-d H:i:s',
			xtype : 'datetimefield_date97',
			dateConfig : {
				el : 'Foss_adjustTransportPath_modifyStartTime_ID'+ (scheduling.adjustTransportationPath.index) +'-inputEl',
				onpicked: function(){
					var form = me.getForm();
					startTimeField = form.findField('startTime');
					endTimeField = form.findField('endTime');
					
					//modify by liangfuxiang BUG-7547 begin
					if(typeof(scheduling.adjustTransportationPath.betweentime)=="undefined"||scheduling.adjustTransportationPath.betweentime==''||scheduling.adjustTransportationPath.betweentime==null){
						scheduling.adjustTransportationPath.betweentime=0;
					}
					//modify by liangfuxiang BUG-7547 end
					
					var timeLong = Ext.Date.parse(startTimeField.getValue(), startTimeField.format).getTime()
						+scheduling.adjustTransportationPath.betweentime;
					var endTime = new Date(timeLong);
					endTimeField.setValue(Ext.Date.format(endTime,endTimeField.format));
				}
			},
			columnWidth : .5,
			listeners : {
				select: {
					fn: function(field, value){
						if(null != scheduling.adjustTransportationPath.betweentime){
							alert(value+scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.isNotEmpty'));
						}else{
							alert(value+scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.isEmpty'));
						}
					}
				}
			}
		}, {
			fieldLabel : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.modifyEndTime'),
			id : 'Foss_adjustTransportPath_modifyEndTime_ID'+(scheduling.adjustTransportationPath.index),
			name : 'endTime',
			time : true,
			format : 'Y-m-d H:i:s',
			xtype : 'datetimefield_date97',
			dateConfig : {
				el : 'Foss_adjustTransportPath_modifyEndTime_ID'+ (scheduling.adjustTransportationPath.index) +'-inputEl',
				//modify by liangfuxiang 2013-04-14 BUG-7022 begin
				onpicked:function(){
					var form=me.getForm();
					var startTimeField = form.findField('startTime');
					var endTimeField = form.findField('endTime');
					var startTime=Ext.Date.parse(startTimeField.getValue(), startTimeField.format).getTime();
					var endTime=Ext.Date.parse(endTimeField.getValue(), startTimeField.format).getTime();
					if(endTime<startTime){//到达时间早于出发时间 
						Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.badEndTime'));
						return;
					}
				}
		       //modify by liangfuxiang 2013-04-14 BUG-7022 end
			},
			columnWidth : .5
		}];
		me.callParent([cfg]);
		//var rengong = me.getForm().findField('rengong');
		me.findObjectiveOrgCode(scheduling.adjustTransportationPath.nowWhichCode);
	}
}),
Ext.define('Foss.adjustTransportationPath.SelectAdjustLinesGridResultStore', {
			extend : 'Ext.data.Store',
			// 定义字段
			fields : ['lineVirtualCode', 'lineName', 'leaveTime', 'arriveTime', 'arriveDay']
		});

// 班次 线路grid
Ext.define('Foss.adjustTransportationPath.AdjustLinesGrid', {
	extend : 'Ext.grid.Panel',
	stripeRows : true,
	columnLines : true,
	bodyCls : 'autoHeight',
	frame : false,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : '',
	animCollapse : true,
	selModel : null,
	store : null,
	autoScroll : true,
	height : 130,
	itemClick : function(view, record, item, index, e) {
		var time = record.get('leaveTime');
		var ddtime = record.get('arriveTime');
		var betweentime = ddtime-time;
		scheduling.adjustTransportationPath.betweentime = betweentime;
		var window = scheduling.adjustTransportationPath.adjustTransportPath;
		var form = window.adjustFormInput.getForm();
		
		//format
		var date = new Date(time);						
		time = Ext.Date.format(date, 'Y-m-d H:i:s');
		date = new Date(ddtime);						
		ddtime = Ext.Date.format(date, 'Y-m-d H:i:s');
   		
		form.findField('startTime').setValue(time);
		form.findField('endTime').setValue(ddtime);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create("Foss.adjustTransportationPath.SelectAdjustLinesGridResultStore");
		me.selModel = Ext.create('Ext.selection.RadioModel');
		me.callParent([cfg]);
		me.addListener({
					'itemclick' : me.itemClick
				});
	},
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.lineName'),
				// 关联model中的字段名
				dataIndex : 'lineName',
				flex : 1
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.startTime'),
				// 关联model中的字段名
				dataIndex : 'leaveTime',
				renderer: function(value){
					if(!value) return '';
					var date = new Date(value);						
					return Ext.Date.format(date, 'Y-m-d H:i:s');
   				},
				width : 150
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.arriveTime'),
				// 关联model中的字段名
				dataIndex : 'arriveTime',
				renderer: function(value){
					if(!value) return '';
					var date = new Date(value);						
					return Ext.Date.format(date, 'Y-m-d H:i:s');
   				},
				width : 150
			}, {
				// 字段标题
				header : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.arriveDay'),
				// 关联model中的字段名
				dataIndex : 'arriveDay',
				width : 100
			}]
});

scheduling.adjustTransportationPath.getAjustPathLastAdjustFormInput = function() {
	var domList = scheduling.adjustTransportationPath.adjustTransportPathDomList;
	var index = domList.length - 1;
	var lastDom = domList[index];
	return lastDom;
}

scheduling.adjustTransportationPath.addChangePathList = function() {
	var form = scheduling.adjustTransportationPath.getAjustPathLastAdjustFormInput().getForm();
	var values	= form.getValues();
	//取combox值
	var code = form.findField('rengong').lastValue;
	var name = form.findField('rengong').rawValue;
	if (!code || code == ''||!name || name == '') {
		Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.selectArriveOrg'));
		return false;
	}
	if (!values['startTime'] || values['startTime'] == '') {
		Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.selectStartTime'));
		return false;
	}
	if (!values['endTime'] || values['endTime'] == '') {
		Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.selectArriveTime'));
		return false;
	}
	// 只读
	setFormEditAble(scheduling.adjustTransportationPath.getAjustPathLastAdjustFormInput());
	var window = scheduling.adjustTransportationPath.adjustTransportPath;
	var schedulingVO = window.schedulingVO;
	var changePath = {
		'origOrgCode' : scheduling.adjustTransportationPath.nowWhichCode,
		'destOrgCode' : code,
		'planStartTime' : values['startTime'],
		'planArriveTime' : values['endTime']
	}
	scheduling.adjustTransportationPath.adjustTransportPathList.push(changePath);
	//BUG-53100 WEB-调整走货路由无法保存成功，每保存一次人工调整后的转运场新增加一次 mod by songjie
	if(name==scheduling.adjustTransportationPath.nowWhichName){
		
	}else{
	//更新改变路径
		scheduling.adjustTransportationPath.newTransportPath = scheduling.adjustTransportationPath.newTransportPath +"-"+ name;
		scheduling.adjustTransportationPath.newTransportPath = scheduling.adjustTransportationPath.nowWhichName +"-"+ name;
		scheduling.adjustTransportationPath.adjustTransportPath.adjustFormTop.getForm().findField('newPath').setValue(scheduling.adjustTransportationPath.newTransportPath);
		//更新现在所处地点
		scheduling.adjustTransportationPath.nowWhichName = name;
	}
	return code;
}

// 按钮区域
Ext.define('Foss.adjustTransportationPath.BtnAreaPanel', {
	extend : 'Ext.panel.Panel',
	title : '',
	margin : '0 0 0 0',
	layout : 'column',
	frame : false,
	defaultType : 'button',
	items : [{
				xtype : 'container',
				columnWidth : .9,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.add'),
				disabled: !scheduling.adjustTransportationPath.isPermission('scheduling/findObjectiveOrgCodeButton'),
				hidden: !scheduling.adjustTransportationPath.isPermission('scheduling/findObjectiveOrgCodeButton'),
				columnWidth : .1,
				handler : function() {
					
					//modify by liangfuxiang 2013-04-13 begin
					var form = scheduling.adjustTransportationPath.getAjustPathLastAdjustFormInput().getForm();
				    var values	= form.getValues();
					if (!values['startTime'] || values['startTime'] == '') {
						Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.selectStartTime'));
						return false;
					}
					if (!values['endTime'] || values['endTime'] == '') {
						Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.selectArriveTime'));
						return false;
					}
					
					var startTimeField = form.findField('startTime');
					var endTimeField = form.findField('endTime');
					var startTime=Ext.Date.parse(startTimeField.getValue(), startTimeField.format).getTime();
					var endTime=Ext.Date.parse(endTimeField.getValue(), startTimeField.format).getTime();
					if(endTime<startTime){//到达时间早于出发时间 
						Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.badEndTime'));
						return;
					}
					//modify by liangfuxiang 2013-04-13 end
					
					var code = scheduling.adjustTransportationPath.addChangePathList();
					if (!code) {
						return code;
					}
					// 获取上一个现在所处, 人工调整
					scheduling.adjustTransportationPath.nowWhichCode = code;
					var adjustFormInput = Ext
							.create('Foss.adjustTransportationPath.AdjustFormInput');
					var window = scheduling.adjustTransportationPath.adjustTransportPath;
					window.adjustFormInput = adjustFormInput;
					scheduling.adjustTransportationPath.adjustTransportPathDomList.push(adjustFormInput);
					scheduling.adjustTransportationPath.adjustTransportPath.addAreaContainer
							.add([adjustFormInput]);
					adjustFormInput.getForm().findField('nowWhich')
							.setValue(scheduling.adjustTransportationPath.nowWhichName);
				}
			}, {
				xtype : 'container',
				columnWidth : .1,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.cancle'),
				columnWidth : .15,
				cls : '',
				handler : function() {
					scheduling.adjustTransportationPath.adjustTransportPath.close();
				}
			}, {
				xtype : 'container',
				columnWidth : .5,
				html : '&nbsp;'
			}, {
				xtype : 'button',
				text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.save'),
				disabled: !scheduling.adjustTransportationPath.isPermission('scheduling/modifyPathButton'),
				hidden: !scheduling.adjustTransportationPath.isPermission('scheduling/modifyPathButton'),
				columnWidth : .15,
				cls : '',
				handler : function() {
					var code = scheduling.adjustTransportationPath.addChangePathList();
					if (!code) {
						return code;
					}
					
					//modify by liangfuxiang 2013-04-13 begin
					var form = scheduling.adjustTransportationPath.getAjustPathLastAdjustFormInput().getForm();
				    var values	= form.getValues();
					if (!values['startTime'] || values['startTime'] == '') {
						Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.selectStartTime'));
						return false;
					}
					if (!values['endTime'] || values['endTime'] == '') {
						Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint'), scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.selectArriveTime'));
						return false;
					}
					
					var startTimeField = form.findField('startTime');
					var endTimeField = form.findField('endTime');
					var startTime=Ext.Date.parse(startTimeField.getValue(), startTimeField.format).getTime();
					var endTime=Ext.Date.parse(endTimeField.getValue(), startTimeField.format).getTime();
					if(endTime<startTime){//到达时间早于出发时间 
						Ext.MessageBox.alert(scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.badEndTime'));
						return;
					}
					//modify by liangfuxiang 2013-04-13 end
					
					var window = scheduling.adjustTransportationPath.adjustTransportPath;
					var schedulingVO = window.schedulingVO;
					schedulingVO.changePathList = scheduling.adjustTransportationPath.adjustTransportPathList;
					// 时间
					var time = Ext
							.getCmp('Foss_adjustTransportPath_datetimefieldForm_ID').rawValue;
					if (time != null && time != "") {
						schedulingVO.changePathEntity = {};
						schedulingVO.changePathEntity.effectEndTime = time;
					}
					var params = {
						schedulingVO : schedulingVO
					}
					//按钮不可用
					this.setDisabled(true);
					//modify  by liangfuxiang 2013-05-15 BUG-9744 begin
					saveButton=this;
					//modify  by liangfuxiang 2013-05-15 BUG-9744 end
					// ajax请求
					var title = scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.hint');
					Ext.Ajax.request({
						url : scheduling.realPath('modifyPath.action'),
						jsonData : params,
						timeout: 300000,
						success : function(response) {
							// var message =
							// scheduling.i18n('foss.adjustTransportationpath.adjustPathSuccessMessage');
							Ext.ux.Toast.msg(title, scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.success'));
							//成功提示
							scheduling.adjustTransportationPath.tipMessageAreaContainer.setVisible(true);
							//add by liangfuxiang 2013-05-15 BUG-9744 begin
							saveButton.setDisabled(false);
							//add  by liangfuxiang 2013-05-15 BUG-9744 end
						},
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(title, result.message,'error');
							//add by liangfuxiang 2013-05-15 BUG-9744 begin
							saveButton.setDisabled(false);
							//add  by liangfuxiang 2013-05-15 BUG-9744 end
						}
					});
				}
			}, {
				xtype : 'container',
				columnWidth : .1,
				html : '&nbsp;'
			}]

});

// 新增区域
Ext.define('Foss.adjustTransportationPath.AddAreaContainer', {
			extend : 'Ext.container.Container',
			frame : false,
			bodyCls : 'autoHeight',
			items : []
		});

// 提示信息
Ext.define('Foss.adjustTransportationPath.TipMessageAreaContainer', {
			extend : 'Ext.container.Container',
			border : 1,
			padding : '5px 0px 5px 5px',
			style : {
				borderColor : '#000000',
				borderStyle : 'solid',
				borderWidth : '1px'
			},
			layout : 'column',
			items : [{
						xtype : 'label',
						columnWidth : 0.97,
						style : 'font-weight:bold;',
						text : scheduling.adjustTransportationPath.i18n('foss.scheduling.adjustTransportationPath.adjustPath.savePathSuccess')
					}, {
						xtype : 'image',
						columnWidth : 0.03,
						listeners : {
							click : {
								element : 'el',
								fn : function() {
									scheduling.adjustTransportationPath.tipMessageAreaContainer
											.setVisible(false);
								}
							}
						}
					}]
		});
/** **************1.调整走货路径界面 end************* */
