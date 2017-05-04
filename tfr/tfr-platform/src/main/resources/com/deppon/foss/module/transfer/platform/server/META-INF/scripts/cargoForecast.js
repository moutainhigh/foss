/**
*货量预测查询界面
*肖红叶
*2015-03-31
*
*/
//货量统计查询结果实体model
Ext.define('Foss.platform.cargoForecast.Model',{
	extend : 'Ext.data.Model',
	fields : [{//线路名称
		name : 'lineName',
		type : 'string'
	},{//预测到达货量：长途重量
		name : 'fcstWeightArrLng',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//预测到达货量：长途体积
		name : 'fcstVolumeArrLng',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//预测到达货量：短途重量
		name : 'fcstWeightArrSht',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//预测到达货量：短途体积
		name : 'fcstVolumeArrSht',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//预测到达货量：集中接货重量
		name : 'fcstWeightArrPickup',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//预测到达货量：集中接货体积
		name : 'fcstVolumeArrPickup',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//预测出发货量：长途重量
		name : 'fcstWeightDptLng',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//预测出发货量：长途体积
		name : 'fcstVolumeDptLng',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//预测出发货量：短途重量
		name : 'fcstWeightDptSht',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//预测出发货量：短途体积
		name : 'fcstVolumeDptSht',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//预测出发货量：派送货量重量
		name : 'fcstWeightDptDispath',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//预测出发货量：派送货量体积
		name : 'fcstVolumeDptDispath',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//预测总操作货量：重量
		name : 'fcstWeight',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//预测总操作货量：体积
		name : 'fcstVolume',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//前一周同期实际货量：重量
		name : 'actualWeight7',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//前一周同期实际货量：体积
		name : 'actualVolume7',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//前两周同期实际货量：重量
		name : 'actualWeight14',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//前两周同期实际货量：体积
		name : 'actualVolume14',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//前三周同期实际货量：重量
		name : 'actualWeight21',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//前三周同期实际货量：体积
		name : 'actualVolume21',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	},{//前四周同期实际货量：重量
		name : 'actualWeight28',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.000');
			} else {
				return null;
			}
		}
	},{//前四周同期实际货量：体积
		name : 'actualVolume28',
		type:'float',
		convert : function(value) {
			if (value != null) {
				return Ext.util.Format.number(value,'0.00');
			} else {
				return null;
			}
		}
	}]
});
//货量预测查询结果store
Ext.define('Foss.platform.cargoForecast.store',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.cargoForecast.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('findFcstResult.action'),
		reader : {
			type : 'json',
			root : 'cargoFcstVo.cargoFcstResultDtos',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			//查询窗体及查询参数
			var queryForm = platform.cargoForecast.searchFormPanel.getForm();
			if(queryForm != null){
				Ext.apply(operation,{
					params : {
						'cargoFcstVo.cargoFcstDto.lineCode':queryForm.findField('parameter.lineCode').getValue(),
						'cargoFcstVo.cargoFcstDto.tfrCtrCode':queryForm.findField('parameter.tfrCtrCode').getValue(),
						'cargoFcstVo.cargoFcstDto.staDate':queryForm.findField('parameter.staDate').getValue()
					}
				});
			}
		},
		load : function(store,records,successful,eOpts){
			//查询查询结果表格
			var gridByResult = platform.cargoForecast.gridByResult;
			//查询窗体及查询参数
			var queryForm = platform.cargoForecast.searchFormPanel.getForm();
			var config = store.proxy.reader.jsonData.cargoFcstVo.config;
			//在表格中显示统计数据起止时间
			var staDate = queryForm.findField('parameter.staDate').getValue();
			var staDateStart = Ext.Date.format(staDate,'Y-m-d');
			var staDateEnd = Ext.Date.format(Ext.Date.add(staDate, Ext.Date.DAY, 1),'Y-m-d');
			gridByResult.down('displayfield',0).setValue(staDateStart+'&nbsp;'+config+':00'+'&nbsp;至&nbsp;'+staDateEnd+'&nbsp;'+config+':00');
			gridByResult.down('displayfield',0).setVisible(true);
		}
	}
});

//货量统计查询条件
Ext.define('Foss.platform.cargoForecast.form', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'parameter.tfrCtrCode',
		fieldLabel : '外场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				if(!Ext.isEmpty(platform.cargoForecast.outfieldCode)){
					_this.readOnly = true;
				}
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'parameter.staDate',
		editable:false,
		value: login.currentServerTime,
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, 1),
		minValue:login.currentServerTime,
		format:'Y-m-d',
		columnWidth:.25
	},{
		name : 'parameter.lineCode',
		fieldLabel : '线路',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG'
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function() {
				//获得创建的查询条件的form
				var form = platform.cargoForecast.searchFormPanel.getForm();				
				//重置查询条件form
				form.reset();
				//初始化页面外场信息
				if(!Ext.isEmpty(platform.cargoForecast.outfieldCode)){
					//外场初始化
					form.findField('parameter.tfrCtrCode').setCombValue(
						platform.cargoForecast.outfield,
						platform.cargoForecast.outfieldCode
					);
				}
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.cargoForecast.searchFormPanel.getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				platform.cargoForecast.gridByResult.store.load();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

platform.cargoForecast.searchFormPanel = Ext.create('Foss.platform.cargoForecast.form');

//货量统计查询结果表格
Ext.define('Foss.platform.cargoForecast.grid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	enableColumnHide : false,
	cls : 'autoHeight',
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	title : '查询结果&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #FF0000">注：节假日数据不予参考且不出数据</span>',
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '线路',
		align : 'center',
		sortable:false,
		dataIndex : 'lineName',
		width:80
	},{
		xtype : 'ellipsiscolumn',
		header : '预测到达货量', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'长途',
            columns: [{
            	xtype : 'ellipsiscolumn',
                header:'重量(T)',
                align : 'center',
                dataIndex: 'fcstWeightArrLng',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'体积(F)',
                align : 'center',
                dataIndex: 'fcstVolumeArrLng',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'票数',
                align : 'center',
                hidden:true,
                dataIndex: 'fcstVoteArrLng',
                width:80
            }]
        },{
        	xtype : 'ellipsiscolumn',
            header:'短途',
            columns: [{
            	xtype : 'ellipsiscolumn',
                header:'重量(T)',
                align : 'center',
                dataIndex: 'fcstWeightArrSht',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'体积(F)',
                align : 'center',
                dataIndex: 'fcstVolumeArrSht',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'票数',
                align : 'center',
                hidden:true,
                dataIndex: 'fcstVoteArrSht',
                width:80
            }]
        },{
        	xtype : 'ellipsiscolumn',
            header:'集中接货',
            columns: [{
            	xtype : 'ellipsiscolumn',
                header:'重量(T)',
                align : 'center',
                dataIndex: 'fcstWeightArrPickup',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'体积(F)',
                align : 'center',
                dataIndex: 'fcstVolumeArrPickup',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'票数',
                align : 'center',
                hidden:true,
                dataIndex: 'fcstVoteArrPickup',
                width:80
            }]
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '预测出发货量', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'长途',
            columns: [{
            	xtype : 'ellipsiscolumn',
                header:'重量(T)',
                align : 'center',
                dataIndex: 'fcstWeightDptLng',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'体积(F)',
                align : 'center',
                dataIndex: 'fcstVolumeDptLng',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'票数',
                align : 'center',
                hidden:true,
                dataIndex: 'fcstVoteDptLng',
                width:80
            }]
        },{
        	xtype : 'ellipsiscolumn',
            header:'短途',
            columns: [{
            	xtype : 'ellipsiscolumn',
                header:'重量(T)',
                align : 'center',
                dataIndex: 'fcstWeightDptSht',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'体积(F)',
                align : 'center',
                dataIndex: 'fcstVolumeDptSht',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'票数',
                align : 'center',
                hidden:true,
                dataIndex: 'fcstVoteDptSht',
                width:80
            }]
        },{
        	xtype : 'ellipsiscolumn',
            header:'派送货量',
            columns: [{
            	xtype : 'ellipsiscolumn',
                header:'重量(T)',
                align : 'center',
                dataIndex: 'fcstWeightDptDispath',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'体积(F)',
                align : 'center',
                dataIndex: 'fcstVolumeDptDispath',
                width:80
            }, {
            	xtype : 'ellipsiscolumn',
                header:'票数',
                align : 'center',
                hidden:true,
                dataIndex: 'fcstVoteDptDispath',
                width:80
            }]
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '预测总操作货量', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'重量(T)',
            align : 'center',
            dataIndex: 'fcstWeight',
            width:80
        }, {
        	xtype : 'ellipsiscolumn',
            header:'体积(F)',
            align : 'center',
            dataIndex: 'fcstVolume',
            width:80
        },{
        	xtype : 'ellipsiscolumn',
            header:'票数',
            align : 'center',
            hidden:true,
            dataIndex: 'fcstVote',
            width:80
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '前一周同期实际货量', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'重量(T)',
            align : 'center',
            dataIndex: 'actualWeight7',
            width:80
        }, {
        	xtype : 'ellipsiscolumn',
            header:'体积(F)',
            align : 'center',
            dataIndex: 'actualVolume7',
            width:80
        },{
        	xtype : 'ellipsiscolumn',
            header:'票数',
            align : 'center',
            hidden:true,
            dataIndex: 'actualVote7',
            width:80
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '前两周同期实际货量', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'重量(T)',
            align : 'center',
            dataIndex: 'actualWeight14',
            width:80
        }, {
        	xtype : 'ellipsiscolumn',
            header:'体积(F)',
            align : 'center',
            dataIndex: 'actualVolume14',
            width:80
        },{
        	xtype : 'ellipsiscolumn',
            header:'票数',
            align : 'center',
            hidden:true,
            dataIndex: 'actualVote14',
            width:80
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '前三周同期实际货量', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'重量(T)',
            align : 'center',
            dataIndex: 'actualWeight21',
            width:80
        }, {
        	xtype : 'ellipsiscolumn',
            header:'体积(F)',
            align : 'center',
            dataIndex: 'actualVolume21',
            width:80
        },{
        	xtype : 'ellipsiscolumn',
            header:'票数',
            align : 'center',
            hidden:true,
            dataIndex: 'actualVote21',
            width:80
        }]
    },{
		xtype : 'ellipsiscolumn',
		header : '前四周同期实际货量', 
        columns: [{
        	xtype : 'ellipsiscolumn',
            header:'重量(T)',
            align : 'center',
            dataIndex: 'actualWeight28',
            width:80
        }, {
        	xtype : 'ellipsiscolumn',
            header:'体积(F)',
            align : 'center',
            dataIndex: 'actualVolume28',
            width:80
        },{
        	xtype : 'ellipsiscolumn',
            header:'票数',
            align : 'center',
            hidden:true,
            dataIndex: 'actualVote28',
            width:80
        }]
    }],
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.cargoForecast.store');
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				//查询窗体及查询参数
				var queryForm = platform.cargoForecast.searchFormPanel.getForm();
				var params = {
					'cargoFcstVo.cargoFcstDto.lineCode':queryForm.findField('parameter.lineCode').getValue(),
					'cargoFcstVo.cargoFcstDto.tfrCtrCode':queryForm.findField('parameter.tfrCtrCode').getValue(),
					'cargoFcstVo.cargoFcstDto.staDate':queryForm.findField('parameter.staDate').getValue()
				};

				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportCargoFcst.action'),
					form : Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : params,
					isUpload : true,
					success : function(response) {

					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败', result.message);
					}
				});
			}
		},{
			text: '&nbsp;',
			xtype: 'tbtext',
			width:250
       },{
			text: '预测时间范围：',
			xtype: 'tbtext',
			width:80
       },{
			value: '00',
			xtype: 'displayfield',
			hidden:true,
			fieldLabel:'',
			labelSeparator:'',
			labelWidth:0,
			name:'cargoFcstVo.config',
			width:400
       }];
		me.callParent([cfg]);
	}
});

platform.cargoForecast.gridByResult =Ext.create('Foss.platform.cargoForecast.grid') ;

//货量统计页面
Ext.define('Foss.platform.cargoForecast.Panel', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//货量统计查询结果表格
		var gridByResult = platform.cargoForecast.gridByResult;
		//货量统计查询条件
		var queryForm = platform.cargoForecast.searchFormPanel;
		me.items = [queryForm, gridByResult];
		me.callParent([cfg]);
	}
});

/** -----------------------------------------------页面显示view--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	platform.cargoForecast.MainPanel = Ext.create('Foss.platform.cargoForecast.Panel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-cargoForecast_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		items : [platform.cargoForecast.MainPanel],
		renderTo : 'T_platform-cargoForecast-body'
	});
	//初始化页面外场信息
	if(!Ext.isEmpty(platform.cargoForecast.outfieldCode)){
		//月台操作效率页签外场初始化
		platform.cargoForecast.searchFormPanel.getForm().findField('parameter.tfrCtrCode').setCombValue(
			platform.cargoForecast.outfield,
			platform.cargoForecast.outfieldCode
		);
	}
});