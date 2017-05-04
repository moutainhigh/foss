<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<title>工作流查看</title>
<link rel="stylesheet" type="text/css" href="${resources}/styles/ext-foss-min.css">
<script type="text/javascript" src="${resources}/scripts/bootstrap.js"></script>
<script type="text/javascript"
	src="${resources}/scripts/ext-lang-${FRAMEWORK__KEY_LOCALE__}.js"></script>
<script type="text/javascript"
	src="${resources}/components/my97DatePicker/WdatePicker.js"></script>

</head>
<body>

</body>

<script type="text/javascript">

//时间格式化
Date.prototype.format = function (format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }

    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds() // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
//工作流审批状态Model
Ext.define('Foss.pkp.WorkFlowStatusModel', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'currentStatus',
			type : 'string'
		}, {
			name : 'partiName',
			type : 'string'
		}, {
			name : 'duty',
			type : 'string'
		}, {
			name : 'result',
			type : 'string',
			convert : function (value) { // convert类型转换器	
				var result;
				switch (value)
	            {
	                case '4':
	                	result='已同意';
	                    break;
	                case '5':
	                	result='不同意';
	                    break;
	            }
	           return result
			}
		}
	]
});

//工作流审批状态panel
Ext.define('Foss.pkp.WorkFlowQueryFirstPanel', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	autoHeight : true,
	title : '工作流审批状态',
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				xtype : 'fieldcontainer',
				layout : {
					type : 'table',
					columns : 3
				},
				defaults : {
					labelAlign : 'left',
					readOnly : true,
					xtype : 'textfield',
					labelStyle : 'font-weight:900;'
				},
				items : [{
						fieldLabel : '当前状态',
						name : 'currentStatus'
					}, {
						fieldLabel : '当前审批人',
						name : 'partiName'
					}, {
						fieldLabel : '审批结果',
						name : 'result'
					}, {
						fieldLabel : '权责',
						name : 'duty'
					}
				]
			}
		],
		me.callParent([cfg]);
	}
});

//申请人Model
Ext.define('Foss.pkp.ProposerInfoModel', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'createUserName',
			type : 'string'
		}, {
			name : 'createUserCode',
			type : 'string'
		}, {
			name : 'createUserTitle',
			type : 'string'
		}, {
			name : 'respectiveRegionalName',
			type : 'string'
		}, {
			name : 'createOrgName',
			type : 'string'
		}
	]
});

Ext.define('Foss.pkp.WorkFlowQuerySecondPanel', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	autoHeight : true,
	defaults : {
		margin : '5 10 5 10',
	},
	title : '申请人信息',
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				xtype : 'fieldcontainer',
				layout : {
					type : 'table',
					columns : 3
				},
				defaults : {
					xtype : 'textfield',
					labelAlign : 'left',
					readOnly : true
				},
				items : [{
						fieldLabel : '申请人',
						name : 'createUserName'
					}, {
						fieldLabel : '工号',
						name : 'createUserCode'
					}, {
						fieldLabel : '职位',
						name : 'createUserTitle'
					}, {
						fieldLabel : '所属区域',
						name : 'respectiveRegionalName'
					}, {
						fieldLabel : '部门',
						name : 'createOrgName'
					}
				]
			}
		],
		me.callParent([cfg]);
	}
});

//详细信息Model
Ext.define('Foss.pkp.ExceptionModel', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'waybillNo',
			type : 'string'
		}, {
			name : 'serialNumber',
			type : 'string'
		}, {
			name : 'errorNumber',
			type : 'string'
		}, {
			name : 'goodsName',
			type : 'string'
		}, {
			name : 'toPayAmount'
		}, {
			name : 'goodsQtyTotal',
			type : 'int'
		}, {
			name : 'prePayAmount'
		}, {
			name : 'goodsWeightTotal'
		}, {
			name : 'codAmount'
		}, {
			name : 'lastLoadOrgName',
			type : 'string'
		}, {
			name : 'goodsVolumeTotal',
			type : 'string'
		}, {
			name : 'insuranceAmount',
			type : 'string'
		}, {
			name : 'abandonedgoodsType',
			type : 'string'
		}, {
			name : 'storageDay',
			type : 'int'
		}, {
			name : 'customerCooperateStatus', // 关联的名称
			type : 'string',
			convert : function (value) { // convert类型转换器
				if (value == 'Y') {
					return '客户不予配合处理';
				} else {
					return '有弃货证明/赔偿协议/无标签货';
				}
			}
		}, {
			name : 'lastStorageName',
			type : 'string'
		}, {
			name : 'billTime'
		}, {
			name : 'notes',
			type : 'string'
		}, {
			name : 'receiveOrgName',
			type : 'string'
		}
	]
});



//附件详情
Ext.define('Foss.predeliver.processAbandonGoods.FileUploadResultGrid', {
	extend : 'Deppon.ux.FileUploadGrid',
	reviewFlag : true,
	//fileTypes: ['jpg','jpeg','gif','bmp','png'],
	title : '附件', // 上传凭证,
	downLoadUrl : ContextPath.PKP_DELIVER + '/sign/downLoadFiles.action',
	imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
});

//弃货详细信息
Ext.define('Foss.pkp.WorkFlowQueryThirdPanel', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	layout : {
		type : 'column'
	},
	bodyPadding : 10,
	title : '仓库异常货物/违禁品（香烟、光碟） 处理申请内容',
	defaults : {
		margin : '5 10 5 10',
	},
	fileUploadResultGrid : null,
	getFileUploadResultGrid : function () {
		if (this.fileUploadResultGrid == null) {
			this.fileUploadResultGrid = Ext.create('Foss.predeliver.processAbandonGoods.FileUploadResultGrid', {
					columnWidth : 1,
					autoScroll : true
				});
		}
		return this.fileUploadResultGrid;
	},
	initComponent : function () {
		var me = this;
		Ext.applyIf(me, {
			items : [{
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3,
					fieldLabel : '运单号',
					name : 'waybillNo'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3,
					fieldLabel : '流水号',
					name : 'serialNumber'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '差错编号',
					name : 'errorNumber'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '货物名称',
					name : 'goodsName'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '到付金额（元）',
					name : 'toPayAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '收货日期',
					name : 'billTime'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '件数',
					name : 'goodsQtyTotal'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '预付金额（元）',
					name : 'prePayAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '收货部门',
					name : 'receiveOrgName'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '重量（KG）',
					name : 'goodsWeightTotal'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '代收金额（元）',
					name : 'codAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '到达部门',
					name : 'lastLoadOrgName'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '体积（方）',
					name : 'goodsVolumeTotal'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '保险价值（元）',
					name : 'insuranceAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '仓储部门',
					name : 'lastStorageName' // 仓储部门
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, 
					fieldLabel : '仓储时长（天）',
					name : 'storageDay'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 2 / 3, 
					fieldLabel : '处理类型',
					name : 'customerCooperateStatus', // 关联的名称,
					fieldStyle:'color:red;'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 3 / 3,
					fieldLabel : '申请事由',
					name : 'notes',
					xtype : 'textareafield'
				}, {
					border : 1,
					xtype : 'container',
					columnWidth : 1,
					defaultType : 'button',
					layout : 'column',
					items : [{
							xtype : 'label',
							columnWidth : 1 / 5
						},
						me.getFileUploadResultGrid()]
				}
			]
		});
		me.callParent(arguments);
	}
});

Ext.define('Foss.pkp.UploadFile', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	autoHeight : true,
	defaults : {
		labelAlign : 'right',
		grow : true,
		growMin : 0,
		readOnly : true,
		xtype : 'textareafield',
		labelStyle : 'font-weight:900;'
	},
	layout : {
		type : 'table',
		columns : 3

	},
	title : '仓库异常货申请相关说明',
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				fieldLabel : '用途',
				name : 'use',
				width : 800,
				colspan : 3,
				value : '此工作流用于申请仓库异常货物（发货人给出书面弃货证明的货物或库存状态超过三个月，客户不予配合处理的货物）处理'
			}, {
				fieldLabel : '使用人',
				name : 'user',
				width : 800,
				colspan : 3,
				value : '部门负责人'
			}, {
				fieldLabel : '附件说明',
				name : 'desc',
				width : 800,
				colspan : 3,
				value : '弃货证明/赔偿协议/无标签货必须通过扫描或拍照上传电子版附件，否则退回'
			}, {
				fieldLabel : '其他说明',
				name : 'otherDesc',
				width : 800,
				colspan : 3,
				value : '参考《经10-027营业部异常货处理规范》'
			}
		];
		me.callParent([cfg]);
	}
});
//详细信息end

//工作流追踪start
Ext.define('Foss.pkp.workFlowQueryProcDetailStore', {
	extend : 'Ext.data.Store',
	autoLoad : true,
	fields : ['approveno', 'applyno', 'approver',
		'approvedate',
		'approvever', {
			name : 'isagree',
			type : 'int',
			convert : function (value) { // convert类型转换器
				if (value == 0) {
					return '同意';
				}

				if (value == 1) {
					return '不同意';
				}

				if (value == 2) {
					return '起草';
				}

				if (value == 4) {
					return '退回';
				}

				if (value == 5) {
					return '同意并结束';
				}

				if (value == 6) {
					return '收回';
				}

				if (value == 9) {
					return '业务回退';
				}
			}
		}, 'processinstid', 'userid',
		'workitemid', 'busino', 'currentactivitydefid',
		'currentactivitydefname', 'nextactivitydefid',
		'nextactivitydefname', 'iseffective', 'starttime','duty'],
	proxy : {
		type : 'ajax',
		url : '../predeliver/getProcess.action',
		reader : {
			type : 'json',
			root : 'approvalList'
		}
	}

});

Ext.define('Foss.pkp.WorkFlowQueryRecord', {
	extend : 'Ext.grid.Panel',
	columns : [{
			header : '处理时间',
			dataIndex : 'approvedate',
			flex : 1,
			renderer : function (value) {
				if (value != null) {
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}
			}
		}, {
			header : '处理人',
			dataIndex : 'approver',
			flex : 1
		}, {
			header : '处理结果',
			dataIndex : 'isagree',
			flex : 1
		}, {
			header : '处理意见',
			dataIndex : 'approvever',
			flex : 1
		}, {
			header : '权责',
			dataIndex : 'duty',
			xtype : 'linebreakcolumn',
			flex : 1
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.pkp.workFlowQueryProcDetailStore');
		me.callParent([cfg]);
	}
});
//工作流追踪end


Ext.onReady(function () {

	Ext.QuickTips.init();

	var firstPanel = Ext.create('Foss.pkp.WorkFlowQueryFirstPanel');
	var secondPanel = Ext.create('Foss.pkp.WorkFlowQuerySecondPanel');
	var thirdPanel = Ext.create('Foss.pkp.WorkFlowQueryThirdPanel');
	var fourthPanel = Ext.create('Foss.pkp.UploadFile');


	//工作流图示
	Ext.define('Foss.pkp.WorkFlowQueryFollowTrack', {
		extend : 'Ext.panel.Panel',
		animCollapse : true,
		autoHeight : true,
		constructor : function (config) {
			var me = this,
			cfg = Ext.apply({}, config);
			me.items = [{
					html : "<iframe width=950 height=800 src='../predeliver/workFlowTrack.action?processInstId=${workFlowStatus.flowCode}'/>"
				}
			];
			me.callParent([cfg]);
		}
	});

	var workFlowRecord = Ext.create('Foss.pkp.WorkFlowQueryRecord');

	var workFlowTrack = Ext.create('Foss.pkp.WorkFlowQueryFollowTrack');

	var detailsPlanContent = Ext.create('Ext.panel.Panel', {
			cls : "panelContent",
			margin : '0 0 0 0',
			bodyCls : 'panelContent-body',
			renderTo : Ext.getBody(),
			getFirstPanel : function () {
				return firstPanel;
			},
			getSecondPanel : function () {
				return secondPanel;
			},
			getThirdPanel : function () {
				return thirdPanel;
			},
			getFourthPanel : function () {
				return fourthPanel;
			},
			items : [{
					xtype : 'tabpanel',
					cls : "innerTabPanel",
					bodyCls : "overrideChildLeft",
					activeTab : 0, // 默认激活第一个Tab页
					frame : true,
					items : [{
							title : '详细信息',
							closable : false,
							items : [{
						        xtype: 'displayfield',
						        fieldLabel: '工作流号',
						    	readOnly : true,
						        value: "${workFlowStatus.flowCode}"
						    },firstPanel, secondPanel, thirdPanel, fourthPanel],
							tabConfig : {
								width : 100
							}
						}, {
							title : '工作流追踪',
							closable : false,
							tabConfig : {
								width : 100
							},
							items : [workFlowRecord]
						}, {
							title : '工作流图示',
							layout : 'fit',
							closable : false,
							tabConfig : {
								width : 100
							},
							items : [workFlowTrack]
						}
					]
				}
			]
		});

	var abandonedGoodsDetail = ${abandonedGoodsDetail};
	var model = new Foss.pkp.ExceptionModel(abandonedGoodsDetail);
	
	thirdPanel.fileUploadResultGrid.getStore().loadData(${abandonedGoodsDetail.abandonedGoodsFiles});
	thirdPanel.loadRecord(model);

	var proposerInfo = new Foss.pkp.ProposerInfoModel(${proposerInfo});
	secondPanel.loadRecord(proposerInfo);

	workFlowRecord.store.on('beforeload', function () {
		workFlowRecord.store.proxy.extraParams = {
			'workFlowApproveDto.flowCode' : ${workFlowStatus.flowCode}
		};
	});
	workFlowRecord.store.load();
	
	
	
	var workFlowStatus = new Foss.pkp.WorkFlowStatusModel(${workFlowStatus});
	firstPanel.loadRecord(workFlowStatus);
	if("${workFlowStatus.partiName}"==""){
		firstPanel.getForm().findField('partiName').hide();
		firstPanel.getForm().findField('duty').hide();
	}else{
		firstPanel.getForm().findField('result').hide();
	}
	
});

</script>