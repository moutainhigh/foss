baseinfo.electronicTicket.number=null;
baseinfo.electronicTicket.electricImagefile=null;
baseinfo.electronicTicket.y=0;
baseinfo.electronicTicket.m=0;

// 发送Ajax请求，发送并接收 json
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				jsonData : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						successFn(result);
					} else {
						failFn(result);
					}
				},
				failure : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				},
				exception : function(response) {
					var result = Ext.decode(response.responseText);
					failFn(result);
				}
			});
};
/**
 * 日期格式化
 */
function dateFormat(date) {
	return Ext.Date.format(new Date(date.getFullYear(), date.getMonth(), date
							.getDate()), 'yyyy/MM/dd');
}
/**
 * 解析字符串，并返回
 * 
 */
function waybillNumberList(str) {
	var arry = new Array();
	arry = str.split(",");
	for (i = 0; i < arry.length; i++) {
		arry[i] = arry[i].trim();
	}
	return arry;
};
/**
 * 校验
 * @param {} str
 * @return {Boolean}
 */
baseinfo.electronicTicket.queryByNumber = function(str){
if (Ext.String.trim(str) != null&& Ext.String.trim(str) != '') {
	var array = waybillNumberList(str);
	if (array.length > 10) {
		Ext.Msg.alert(baseinfo.electronicTicket.i18n('foss.baseinfo.electric.common.alert'),
					baseinfo.electronicTicket.i18n('foss.baseinfo.electric.common.queryNosLimit'));
		return false;
		}
		} else {
		Ext.Msg.alert(baseinfo.electronicTicket.i18n('foss.baseinfo.electric.common.alert'),
		baseinfo.electronicTicket.i18n('foss.baseinfo.electric.queryBillPayable.nosNotNull'));
		return false;
		}
}
	
// 通过storeId和model创建STORE
baseinfo.getStore = function(storeId, model, fields, data) {
	var store = null;
	if (!Ext.isEmpty(storeId)) {
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if (Ext.isEmpty(data)) {
		data = [];
	}
	if (!Ext.isEmpty(model)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						model : model,
						data : data
					});
		}
	}
	if (!Ext.isEmpty(fields)) {
		if (Ext.isEmpty(store)) {
			store = Ext.create('Ext.data.Store', {
						storeId : storeId,
						fields : fields,
						data : data
					});
		}
	}
	return store;
};

// 查询条件Form
Ext.define('Foss.electronicTicket.DetailTabPanel', {
	extend : 'Ext.tab.Panel',
	flex : 1,
	cls : 'innerTabPanel',
	width : 'auto',
	columnWidth : .99,
	id : 'Foss.electronicTicket.DetailTabPanel_Id',
	activeTab : 0,// 默认激活第一个Tab页
	autoScroll : false,
	electronicTicketListByWaybillNumberTabPanel : null,
	queryElectronicTicketListByWaybillNumberTabPanel : function() {
		if (Ext.isEmpty(this.electronicTicketListByWaybillNumberTabPanel)) {
			this.electronicTicketListByWaybillNumberTabPanel = Ext
					.create('Foss.baseinfo.electronicTicket.ElectronicTicketListByWaybillNumberForm');
		}
		return this.electronicTicketListByWaybillNumberTabPanel;
	},
	electronicTicketListBySerialNumberTabPanel : null,
	queryElectronicTicketListBySerialNumberTabPanel : function() {
		if (Ext.isEmpty(this.electronicTicketListBySerialNumberTabPanel)) {
			this.electronicTicketListBySerialNumberTabPanel = Ext
					.create('Foss.baseinfo.electronicTicket.ElectronicTicketListBySerialNumberForm');
		}
		return this.electronicTicketListBySerialNumberTabPanel;
	},
	constructor : function(config) {
		cfg = Ext.apply({}, config);
		this.items = [{
			title : baseinfo.electronicTicket
					.i18n('foss.baseinfo.queryByWaybillNumber'),// 按运单号查询
			tabConfig : {
				width : 100
			},
			items : this.queryElectronicTicketListByWaybillNumberTabPanel()
		}, {
			title : baseinfo.electronicTicket
					.i18n('foss.baseinfo.queryBySerialNumber'),// 按交易流水号查询
			tabConfig : {
				width : 100
			},
			items : this.queryElectronicTicketListBySerialNumberTabPanel()
		}];
		this.callParent([cfg]);
	}
});
// ------------------------------------MODEL----------------------------------
// 刷卡电子小票图片查询Model
Ext.define('Foss.baseinfo.electronicTicket.ElectronicTicketModel', {
			extend : 'Ext.data.Model',
			fields : [
					// 运单号
					{
				name : 'wayBillNo',
				type : 'string'
			},
					// 电子小票图片地址
					{
						name : 'imageUrl',
						type : 'string'
					},
					// 交易流水号
					{
						name : 'serialNo',
						type : 'string'
					},
					// 刷卡金额
					{
						name : 'cardMoney',
						type : 'long'
					},
					// 刷卡时间
					{
						name : 'cardTime',
						convert : dateConvert
					}]
		});

// ------------------------------------STORE----------------------------------
// 刷卡电子小票图片查询STORE
Ext.define('Foss.baseinfo.electronicTicket.ElectronicTicketStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.electronicTicket.ElectronicTicketModel',
	id:'Foss.electronicTicket.store_Id',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '',
		reader : {
			type : 'json',
			root : 'electronicTicketProcVo.electronicTicketEntitys',
			totalProperty : 'totalCount'
		}
	},
	// 构造函数
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	// 监听器
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var tabpanel = Ext.getCmp('Foss.electronicTicket.DetailTabPanel_Id');
			if (tabpanel.items.items[0] == tabpanel.activeTab) {
				this.getProxy().url = baseinfo.realPath('queryElectronicTicketByWayBill.action');
				var queryForm = Ext.getCmp('Foss.electronicTicket.ByWaybillNum_Id').getForm();
				Ext.apply(operation, {
					params : {'electronicTicketProcVo.electronicTicketDetail.wayBillNo' : queryForm.getValues().wayBillNo
					}
				});
			}
			if (tabpanel.items.items[1] == tabpanel.activeTab) {
				this.getProxy().url = baseinfo.realPath('queryElectronicTicketBySerial.action');
				var queryForm = Ext.getCmp('Foss.electronicTicket.BySerialNum_Id').getForm();
				Ext.apply(operation, {
					params : {'electronicTicketProcVo.electronicTicketDetail.serialNo' : queryForm.getValues().serialNo
					}
				});
			}
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
				var grid = Ext.getCmp('T_baseinfo-electronicTicket_content').getQueryGrid();
				toolBar = grid.getDockedItems('toolbar[dock="bottom"]')[0];
				toolBar.getComponent('Foss.electronicTicket.totalMoney_Id').setValue(data.electronicTicketProcVo.totalAmount);
				toolBar.getComponent('Foss.electronicTicket.totalCount_Id').setValue(data.electronicTicketProcVo.totalCount);
		}
	}

});

// ------------------------------------FORM----------------------------------
// 刷卡电子小票图片查询 查询条件:按运单号查询
Ext.define(
				'Foss.baseinfo.electronicTicket.ElectronicTicketListByWaybillNumberForm',
				{
					extend : 'Ext.form.Panel',
					title : baseinfo.electronicTicket
							.i18n('foss.baseinfo.searchCondiction'),// 查询条件
					frame : true,
					id : 'Foss.electronicTicket.ByWaybillNum_Id',
					collapsible : true,
					defaults : {
						columnWidth : 0.3,
						margin : '8 10 5 10',
						anchor : '99%'
					},
					height : 260,
					defaultType : 'textfield',
					layout : 'column',
					record : null, // 绑定的model
									// Foss.baseinfo.electronicTicket.electronicTicketModel
					constructor : function(config) { // 构造器
						var me = this, cfg = Ext.apply({}, config);
						me.items = [{
									name : 'wayBillNo',
									fieldLabel : '运单单号', // 运单单号
									xtype : 'textarea',
									allowBlank:false,
									emptyText : '请输入1个或10个以内单号，单号之间用半角逗号隔开。',
									regex : /^([0-9]{9,10}[,])*([0-9]{9,10}[,]?)$/i,
									regexText : baseinfo.electronicTicket.i18n('foss.baseinfo.electric.payableNoRegexTextWarning')
								}];
						me.fbar = [{
							xtype : 'button',
							width : 70,
							text : baseinfo.electronicTicket
									.i18n('foss.baseinfo.reset'),// 重置
							hidden : !baseinfo.electronicTicket
									.isPermission('electronicTicket/electronicTicketResetbutton'),
							handler : function() {
								me.getForm().reset();
							}
						}, {
							xtype : 'button',
							width : 70,
							cls : 'yellow_button',
							margin : '0 50 0 725',
							text : baseinfo.electronicTicket
									.i18n('foss.baseinfo.query'),// 查询
							hidden : !baseinfo.electronicTicket
									.isPermission('electronicTicket/electronicTicketQuerybutton'),
							handler : function() {
							var grid = Ext.getCmp('T_baseinfo-electronicTicket_content').getQueryGrid();
								if (me.getForm().isValid()) {
									var wayBillNos = me.getForm().findField('wayBillNo').getValue();
									baseinfo.electronicTicket.queryByNumber(wayBillNos);
									me.up().up().up().getQueryGrid().getPagingToolbar().moveFirst();
								}else{
									Ext.Msg.alert(baseinfo.electronicTicket.i18n('foss.baseinfo.electric.common.alert'), baseinfo.electronicTicket.i18n('foss.baseinfo.electric.common.validateFailAlert'));
									return false;
								}
							}
						}];
						me.callParent([cfg]);
					}
				});

// 刷卡电子小票图片查询 查询条件:按交易流水号查询
Ext.define(
				'Foss.baseinfo.electronicTicket.ElectronicTicketListBySerialNumberForm',
				{
					extend : 'Ext.form.Panel',
					title : baseinfo.electronicTicket
							.i18n('foss.baseinfo.searchCondiction'),// 查询条件
					frame : true,
					id : 'Foss.electronicTicket.BySerialNum_Id',
					collapsible : true,
					defaults : {
						columnWidth : 0.3,
						margin : '8 10 5 10',
						anchor : '100%'
					},
					height : 260,
					defaultType : 'textfield',
					layout : 'column',
					record : null, // 绑定的model
									// Foss.baseinfo.electronicTicket.electronicTicketModel
					constructor : function(config) { // 构造器
						var me = this, cfg = Ext.apply({}, config);
						me.items = [{
									name : 'serialNo',
									fieldLabel : '交易流水号', // 交易流水号
									xtype : 'textarea',
									allowBlank:false,
									emptyText : '请输入1个或10个以内单号，单号之间用半角逗号隔开。',
									regex : /^([0-9]{12}[,])*([0-9]{12}[,]?)$/i,
									regexText : baseinfo.electronicTicket.i18n('foss.baseinfo.electric.payableNoRegexTextWarning')
								}];
						me.fbar = [{
							xtype : 'button',
							width : 70,
							text : baseinfo.electronicTicket
									.i18n('foss.baseinfo.reset'),// 重置
							hidden : !baseinfo.electronicTicket
									.isPermission('electronicTicket/electronicTicketResetbutton'),
							handler : function() {
								me.getForm().reset();
							}
						}, {
							xtype : 'button',
							width : 70,
							cls : 'yellow_button',
							margin : '0 50 0 725',
							text : baseinfo.electronicTicket
									.i18n('foss.baseinfo.query'),// 查询
							hidden : !baseinfo.electronicTicket
									.isPermission('electronicTicket/electronicTicketQuerybutton'),
							handler : function() {
								if (me.getForm().isValid()) {
									var serialNos = me.getForm().findField('serialNo').getValue();
									baseinfo.electronicTicket.queryByNumber(serialNos);
									me.up().up().up().getQueryGrid().getPagingToolbar().moveFirst();
								}else{
									Ext.Msg.alert(baseinfo.electronicTicket.i18n('foss.baseinfo.electric.common.alert'), baseinfo.electronicTicket.i18n('foss.baseinfo.electric.common.validateFailAlert'));
									return false;
								}
								}
							
						}];
						me.callParent([cfg]);
					}
				});

// ------------------------------------GRID----------------------------------
// ------------------------------------GRID----------------------------------
// 刷卡电子小票图片查询 查询结果grid
Ext.define('Foss.baseinfo.electronicTicket.QueryResultGrid', {
	extend : 'Ext.grid.Panel',
	id:'Foss.electronicTicket.grid_Id',
	title : baseinfo.electronicTicket.i18n('foss.baseinfo.queryResultsDetails'),// 查询结果明细
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	height:500,
	stripeRows : true,
	columnLines : false, // 增加表格列的分割线
	collapsible : true,
	animCollapse : true,
	autoScroll: true,
	// 表格多选插件
	//selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 列表分页组件对象
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : me.store,
						pageSize : 10,
						prependButtons : true,
						defaults : {
							margin : '0 0 15 3'
						}
					});
		}
		return me.pagingToolbar;
	},
	totalAmountMoney:null,// 总金额
	getTotalAmountMoney:function(){
		var me=this;
		if(Ext.isEmpty(me.totalAmountMoney)){
			me.totalAmountMoney=0;
		}
		return me.totalAmountMoney;
	},
	totalRows:null,// 总条数
	getTotalRows:function(){
		var me=this;
		if(Ext.isEmpty(me.totalRows)){
			me.totalRows=0;
		}
		return me.totalRows;
	},
	// 定义表格列信息
	columns : [{
				text : '序号', // 序号
				flex : 1,
				align : 'center',
				xtype : 'rownumberer'

			}, {
				text : '运单号', // 运单号
				flex : 1,
				align : 'center',
				dataIndex : 'wayBillNo'
			}, {
				text : '电子小票图片地址', // 电子小票图片地址
				flex : 2,
				align : 'center',
				dataIndex : 'imageUrl',
				renderer : function(value, e, a, x) {
					return '<a href="#" onclick="operWindow(' + x + ')">'+'查看图片'+'</a>';
				}
			}, {
				text : '交易流水号', // 交易流水号
				flex : 1,
				align : 'center',
				dataIndex : 'serialNo'
			}, {
				text : '刷卡金额', // 刷卡金额
				flex : 1,
				align : 'center',
				dataIndex : 'cardMoney',
				xtype : 'numbercolumn',
				format : '0,0.00',
				align : 'right'
			}, {
				text : '刷卡时间', // 刷卡时间
				flex : 1,
				align : 'center',
				dataIndex : 'cardTime',
				renderer : function(value) {	
				if (value != null) {	
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');	
					return date;	
				} else {	
					return null;	
				}	
			} 

			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.electronicTicket.ElectronicTicketStore');
		me.dockedItems = [{
					xtype: 'toolbar',
					dock: 'bottom',
					layout:'column',
					defaults:{
						margin:'0 0 5 3',
						allowBlank:true
					},
					items : [{
								height : 5,
								columnWidth : 1
					}, {
						xtype : 'displayfield',
						allowBlank : true,
						itemId : 'Foss.electronicTicket.totalMoney_Id',
						name : 'totalAmount',
						columnWidth : .2,
						labelWidth : 100,
						fieldLabel : '合计      总金额',//总金额,
						value : me.getTotalAmountMoney()
					},{
						xtype: 'container',
						border : false,
						columnWidth:.05,
						html: '&nbsp;&nbsp;'
					},{
						xtype:'displayfield',
						allowBlank:true,
						itemId : 'Foss.electronicTicket.totalCount_Id',
						name:'totalCount',
						id:'',
						columnWidth : .2,
						labelWidth : 100,
						fieldLabel: '总条数',//总条数
						value : me.getTotalRows()
					}]
		}];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

// ------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
			Ext.QuickTips.init();
			if (Ext.getCmp('T_baseinfo-electronicTicket_content')) {
				return;
			}
			var myDate = new Date();
			baseinfo.electronicTicket.y= parseInt(myDate.getDate()+''+myDate.getHours()+''+myDate.getMinutes()+''+myDate.getSeconds()+''+myDate.getMilliseconds());
			baseinfo.electronicTicket.electricImagefile = document.getElementById('electricUrl').value+'filePath=';
			var queryForm = Ext.create('Foss.electronicTicket.DetailTabPanel');
			var queryGrid = Ext
					.create('Foss.baseinfo.electronicTicket.QueryResultGrid');//查询结果显示列表
			Ext.getCmp('T_baseinfo-electronicTicket').add(Ext.create(
					'Ext.panel.Panel', {
						id : 'T_baseinfo-electronicTicket_content',
						cls : 'panelContentNToolbar',
						bodyCls : 'panelContentNToolbar-body',
						//获得查询FORM
						getQueryForm : function() {
							return queryForm;
						},
						//获得查询结果GRID
						getQueryGrid : function() {
							return queryGrid;
						},
						items : [queryForm, queryGrid]
					}));
		});
		
function operWindow(rowIndex) {
	baseinfo.electronicTicket.number = rowIndex;
	var win = Ext.create('Foss.baseinfo.electronicTicketWindow').show();
}

Ext.define('Foss.baseinfo.electronicTicketWindow',{
	extend : 'Ext.window.Window',
	title: baseinfo.electronicTicket.i18n('电子小票图片'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'close',
	parent:null,
	//width :1200,
	//height :500,
	grid:null,
	//x:0,
	//y:100,
    electricImagePanel:null,
    getelectricImagePanel:function(){
    	if(Ext.isEmpty(this.electricImagePanel)){
    		this.electricImagePanel = Ext.create('Foss.baseinfo.electronicTicket.ElectricImagePanel');
    	}
    	return this.electricImagePanel;
    },
    constructor : function(config) {
		baseinfo.electronicTicket.y++;
    	var currutrow = 0;
    	var currutrowmsg ='';
    	var rowidx = baseinfo.electronicTicket.number;
    	var grid=Ext.getCmp('Foss.electronicTicket.grid_Id').getStore().getAt(rowidx);
		this.width = document.documentElement.clientWidth;
		this.height = document.documentElement.clientHeight;
		cfg = Ext.apply({}, config);
		this.items = [{
			items : this.getelectricImagePanel()
		}];
		Ext.getCmp('baseinfoImage'+baseinfo.electronicTicket.y).setSrc(baseinfo.electronicTicket.electricImagefile+''+grid.get('imageUrl'));
		Ext.getCmp('baseinfoImage'+baseinfo.electronicTicket.y).setWidth(800);
		Ext.getCmp('baseinfoImage'+baseinfo.electronicTicket.y).setHeight(800);
		baseinfo.electronicTicket.m=0;
		
		
		this.callParent([cfg]);
	
	}
});
Ext.define('Foss.baseinfo.electronicTicket.ElectricImagePanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.electronicTicket.i18n('电子小票图片'),
	frame: true,
	//height:350,
	//width:550,
	autoScroll:true,
	//style:'float:left',
    defaults : {
    	margin : '0 0 0 0'
    },
    layout:{
    	type: 'column',
        columns: 1
	},
	
    constructor : function(config) {
		var me = this;
		me.width = document.documentElement.clientWidth;
		me.height = (document.documentElement.clientHeight)*0.53;
		cfg = Ext.apply({}, config);
		me.items = [
		            {
		            	xtype:'image',
		            	id:'baseinfoImage'+baseinfo.electronicTicket.y
		             }
		            ];
		me.bbar =[{
    	 text :'左旋转',
    	 handler:function(){
    	baseinfo.electronicTicket.m -= 90;
    	document.getElementById('baseinfoImage'+baseinfo.electronicTicket.y).style.webkitTransform = "rotate("+baseinfo.electronicTicket.m+"deg)";
    	//rotate('electronicTicketimage'+electronicTicket.electronicTicket.x,'left'); 
    	 }
     },
     {
    	 text :'右旋转',
    	 handler:function(){
    	 baseinfo.electronicTicket.m += 90;
         document.getElementById('baseinfoImage'+baseinfo.electronicTicket.y).style.webkitTransform = "rotate("+baseinfo.electronicTicket.m+"deg)";	 
    	 //rotate('electronicTicketimage'+electronicTicket.electronicTicket.x,'right');
    	 }
     },{
    	 text :'放大',
    	 handler:function(){
         var imageipt =  document.getElementById('baseinfoImage'+baseinfo.electronicTicket.y); 
         Ext.getCmp('baseinfoImage'+baseinfo.electronicTicket.y).setWidth(imageipt.width+20);
         Ext.getCmp('baseinfoImage'+baseinfo.electronicTicket.y).setHeight(imageipt.height+20);
         //imageipt.width = imageipt.width+20;
         //imageipt.height = imageipt.height+20;
    	 }
     },{
    	 text :'缩小',
    	 handler:function(){
    	 var imageipt =  document.getElementById('baseinfoImage'+baseinfo.electronicTicket.y); 
    	 Ext.getCmp('baseinfoImage'+baseinfo.electronicTicket.y).setWidth(imageipt.width-20);
         Ext.getCmp('baseinfoImage'+baseinfo.electronicTicket.y).setHeight(imageipt.height-20);
    	 }
     }];
		me.callParent([cfg]);
	}
});