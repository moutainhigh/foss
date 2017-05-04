<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/ext" prefix="ext"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<title>工作流审批</title>
<link rel="stylesheet" type="text/css" href="${resources}/styles/ext-foss-min.css">

<script type="text/javascript" src="${resources}/scripts/bootstrap.js"></script>
<script type="text/javascript"
	src="${resources}/scripts/ext-lang-${FRAMEWORK__KEY_LOCALE__}.js"></script>
<script type="text/javascript"
	src="${resources}/components/my97DatePicker/WdatePicker.js"></script>
</head>
<body>
<object id="plugin0" type="application/x-oie" width="0" height="0"></object>
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

function splitWindow() {
    var plugin = document.getElementById('plugin0');
    if (!plugin || !plugin.openWin) {
    	Ext.Msg.alert('提示', "请先安装插件！",
          function() {
        		location.href = 'http://file.deppon.com.cn/deppon-nopie.xpi';
           });
        return;
    }
}	

var closeWindow = function(containerId) {
	if (window.opener == null) {
		if (navigator.userAgent.indexOf("Firefox") > 0) {
			var plugin = document.getElementById('plugin0');
			if (plugin.openIE) {
				plugin.closeWin();
			}
		} else {
			window.open("", "_self");
			window.close();
		}
		return;
	}
};	

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

Ext.define('Foss.pkp.WorkFlowInfoSecondPanel', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	autoHeight : true,
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
		},{
			name:'receiveOrgName',
			type:'string'
		}
	]
});

// 附件详情
Ext.define('Foss.predeliver.processAbandonGoods.FileUploadResultGrid', {
	extend : 'Deppon.ux.FileUploadGrid',
	reviewFlag : true,
	//fileTypes: ['jpg','jpeg','gif','bmp','png'],
	title : '附件', // 上传凭证,
	downLoadUrl : ContextPath.PKP_DELIVER + '/sign/downLoadFiles.action',
	imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
});

// 弃货详细信息
Ext.define('Foss.pkp.WorkFlowInfoThirdPanel', {
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
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '差错编号',
					name : 'errorNumber'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '货物名称',
					name : 'goodsName'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '到付金额（元）',
					name : 'toPayAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '收货日期',
					name : 'billTime'

				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '件数',
					name : 'goodsQtyTotal'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '预付金额（元）',
					name : 'prePayAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '收货部门',
					name : 'receiveOrgName'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '重量（KG）',
					name : 'goodsWeightTotal'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '代收金额（元）',
					name : 'codAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '到达部门',
					name : 'lastLoadOrgName'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, // 默认长度 一行可以显示4个
					fieldLabel : '体积（方）',
					name : 'goodsVolumeTotal'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, //默认长度 一行可以显示4个
					fieldLabel : '保险价值（元）',
					name : 'insuranceAmount'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, //默认长度 一行可以显示4个
					fieldLabel : '仓储部门',
					name : 'lastStorageName' // 仓储部门
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 1 / 3, //默认长度 一行可以显示4个
					fieldLabel : '仓储时长（天）',
					name : 'storageDay'
				}, {
					xtype : 'textfield',
					readOnly : true,
					columnWidth : 2 / 3, //默认长度 一行可以显示4个
					fieldLabel : '处理类型',
					name : 'customerCooperateStatus', // 关联的名称
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
Ext.define('Foss.pkp.workFlowProcDetailStore', {
	extend : 'Ext.data.Store',
	// autoLoad: true,
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

Ext.define('Foss.pkp.WorkFlowInfoRecord', {
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
		me.store = Ext.create('Foss.pkp.workFlowProcDetailStore');
		me.callParent([cfg]);
	}
});
//工作流追踪end


//工作流图示
Ext.define('Foss.pkp.WorkFlowFollowTrack', {
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

// 上传付款文件的panel
Ext.define('Foss.predeliver.Grid.FileUploadGrid', {
	extend : 'Deppon.ux.FileUploadGrid',
	modulePath : 'pkp-predeliver',
	title : "上传录音", // 上传凭证附件,
	uploadUrl : ContextPath.PKP_DELIVER + '/predeliver/uploadFiles.action',
	//fileTypes: ['jpg','jpeg','gif','bmp','png'],
	downLoadUrl : ContextPath.PKP_DELIVER
	 + '/predeliver/downLoadFiles.action',
	deleteUrl : ContextPath.PKP_DELIVER + '/predeliver/deleteFile.action',
	imgReviewUrl : ContextPath.PKP_DELIVER + '/predeliver/reviewImg.action'
});

/*审批form*/
Ext.define('Foss.pkp.WorkFlowExaminePanel', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	autoHeight : true,
	buttonAlign: 'center',//居中
	title : '审批工作流',
	style : {
		border : '0px'
	},
	defaultType : 'textfield',
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 90
	},
	layout : 'column',
	fileUploadGrid : null,
	getFileUploadGrid : function () {
		if (this.fileUploadGrid == null) {
			this.fileUploadGrid = Ext.create(
					'Foss.predeliver.Grid.FileUploadGrid', {
					columnWidth : 1
				});
		}
		return this.fileUploadGrid;
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				fieldLabel : '当前审批人',
				name : 'currentUser',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : .5
			}, {
				fieldLabel : '审批时间',
				readOnly : true,
				xtype : 'textfield',
				value : Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
				columnWidth : .5
			}, {
				fieldLabel : '审批意见',
				name : 'opinion',
				xtype : 'textarea',
				height : 80,
				width : 600,
				allowBlank : false,
				colspan : 3,
				columnWidth : 1,
				emptyText : '请输入不多于300字的审批意见'
			}, me.getFileUploadGrid(), {
				fieldLabel : '您的决策',
				xtype : 'radiogroup',
				layout : 'column',
				items : [{
						boxLabel : '同意',
						name : 'approveType',
						inputValue : 'agree',
						columnWidth : .2
					}, {
						boxLabel : '不同意',
						name : 'approveType',
						inputValue : 'disagree',
						columnWidth : .2
					}, {
						boxLabel : '客户要求返货',
						name : 'approveType',
						inputValue : 'cancel',
						id:'customerCancelCheck',
						columnWidth : .3
					}
				],
				columnWidth : .5
			}, ],
		me.buttons = [{
				text : '提交',
				cls : 'yellow_button',
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds : 10
				}),
				handler : function () {
					flowCode = ${workFlowStatus.flowCode};
					
					
					var queryForm = Ext.getCmp('T_pkp-exceptionIndex_content').getQueryForm().getForm().getValues();
					var approveType = Ext.getCmp('T_pkp-exceptionIndex_content').getQueryForm().getForm().getValues()["approveType"];
					opinion = queryForm.opinion;
					if(!Ext.getCmp('T_pkp-exceptionIndex_content').getQueryForm().getForm().isValid()){
						return;
					}
					
					if(opinion.length>300){
						Ext.ux.Toast.msg('提示', '请输入不多于300字的审批意见', "error", 2000);
						return;
					}
					
					if(typeof approveType == "undefined"){
						Ext.ux.Toast.msg('提示', '请选择您的审批结论', "error", 2000);
						return;
					}
					
					var fileGrid = me.getFileUploadGrid();
					var filearray = new Array();
					fileGrid.getStore().each(function(record) {
								filearray.push({
											'id' : record.get('id')
										});
							});
					Ext.Ajax.request({
						url : '../predeliver/workFlowApprove.action',
						method : 'POST',
						jsonData :{
							'workFlowApproveDto':{
								'flowCode' : flowCode,
								'opinion' : opinion,
								'approveType' : approveType,
								'attachementFiles' : filearray
							}
						},
						success : function (response) {
							closeWindow();
						},
						exception : function (response, opts) {
							var json = Ext.decode(response.responseText);
							Ext.ux.Toast.msg('提示', json.message, "error", 2000);
						}
					});

				}

			}, {
				text : '退回',
				cls : 'yellow_button',
				hidden:true,
				plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds : 10
				}),
				id:'returnButton',
				handler : function () {
					flowCode = ${workFlowStatus.flowCode};
					var queryForm = Ext.getCmp('T_pkp-exceptionIndex_content').getQueryForm().getForm().getValues();
					opinion = queryForm.opinion;
					if(opinion.length>600){
						Ext.ux.Toast.msg('提示', '请输入不多于300字的审批意见', "error", 2000);
						return;
					}
					Ext.Ajax.request({
						url : '../predeliver/workFlowGoBack.action',
						params : {
							'workFlowApproveDto.flowCode' : flowCode,
							'workFlowApproveDto.opinion' : opinion
						},
						success : function (response) {
							closeWindow();
						},
						exception : function (response, opts) {
							var json = Ext.decode(response.responseText);
							Ext.ux.Toast.msg('提示', json.message, "error", 2000);
						}
						
					});
				}
			}, {
				text : '返回',
				cls : 'yellow_button',
				handler : function () {
					  Ext.Msg.confirm('提示','确定关闭审批页面？',function(btn){
							if(btn == 'yes'){
								closeWindow();
							}
						});
				}
			}
		],
		me.callParent([cfg]);
	}
});
Ext.onReady(function () {
	if (navigator.userAgent.indexOf("Firefox") > 0 || navigator.userAgent.indexOf("MSIE") > 0 || navigator.userAgent.indexOf("Chrome") > 0) {
        //打开分屏窗口
        splitWindow();
    } else {
        Ext.Msg.alert('温馨提示', '非常抱歉，此功能目前仅支持火狐/谷歌/IE浏览器。');
        return;
    }

	Ext.QuickTips.init();

	var secondPanel = Ext.create('Foss.pkp.WorkFlowInfoSecondPanel');
	var thirdPanel = Ext.create('Foss.pkp.WorkFlowInfoThirdPanel');
	var fourthPanel = Ext.create('Foss.pkp.UploadFile');
	var workFlowRecord = Ext.create('Foss.pkp.WorkFlowInfoRecord');
	var workFlowTrack = Ext.create('Foss.pkp.WorkFlowFollowTrack');
	var examinePanel = Ext.create('Foss.pkp.WorkFlowExaminePanel');

	var detailsPlanContent = Ext.create('Ext.panel.Panel', {
			animCollapse : true,
			height : 800,
			autoScroll : true,
			getSecondPanel : function () {
				return secondPanel;
			},
			getThirdPanel : function () {
				return thirdPanel;
			},
			getFourthPanel : function () {
				return fourthPanel;
			},
			getExaminePanel : function () {
				return examinePanel;
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
						    },secondPanel, thirdPanel, fourthPanel],
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

	Ext.create('Ext.panel.Panel', {
		cls : "panelContent",
		margin : '0 0 0 0',
		bodyCls : 'panelContent-body',
		id : 'T_pkp-exceptionIndex_content',
		renderTo : Ext.getBody(),
		getQueryForm : function () {
			return examinePanel;
		},
		items : [examinePanel, detailsPlanContent]
	});

	var abandonedGoodsDetail = ${abandonedGoodsDetail};
	var model = new Foss.pkp.ExceptionModel(abandonedGoodsDetail);
	thirdPanel.loadRecord(model);
	thirdPanel.fileUploadResultGrid.getStore().loadData(${abandonedGoodsDetail.abandonedGoodsFiles});


	var proposerInfo = new Foss.pkp.ProposerInfoModel(${proposerInfo});
	secondPanel.loadRecord(proposerInfo);

	workFlowRecord.store.on('beforeload', function () {
		workFlowRecord.store.proxy.extraParams = {
			'workFlowApproveDto.flowCode' : ${workFlowStatus.flowCode}
		};
	});
	workFlowRecord.store.load();
	
	detailsPlanContent.getExaminePanel().getForm().findField('currentUser').setValue("${currentUser}");

	if("${workFlowStatus.activityDefId}"!='manualActivity'){
		examinePanel.getFileUploadGrid().hide();
	}
	
	if("${workFlowStatus.activityDefId}"=='manualActivity1'&&"${abandonedGoodsDetail.customerCooperateStatus}"=='Y'){
		Ext.getCmp('returnButton').show();
	}
	
	
	
	if("${workFlowStatus.activityDefId}"=='manualActivity1'){
		Ext.getCmp('customerCancelCheck').hide();
	}
	
	
});

</script>