/***
 * @author foss-meiying
 * page:异常丢货签收
 */
sign.lostCargoSign.expType = 'LG';//异常类型 --丢货
sign.lostCargoSign.unnormalLostargo='UNNORMAL_LOSTCARGO';//异常丢货
//签收情况
sign.lostCargoSign.situationStore =FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION',null,null, sign.lostCargoSign.unnormalLostargo);//异常丢货

//签收信息Model
Ext.define('Foss.sign.lostCargoSign.SignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'waybillNo',type: 'string'},// 运单编号
		{name: 'signGoodsQty', type: 'int'},// 签收件数
		{name: 'deliveryCustomerContact', type: 'string'},// 发货客户联系人
		{name: 'receiveCustomerContact', type: 'string'},// 收货客户联系人
		{name: 'goodsQtyTotal', type: 'int'},// 开单件数
		{name: 'stockGoodsQty'},// 库存件数
		{name: 'generateGoodsQty'},// 生成件数
		{name: 'productCode'},// 运输性质code 
		{name: 'serviceTime'}//服务器上的时间
	]
});
 
// 签收信息--签收件 流水号Model
Ext.define('Foss.sign.lostCargoSign.SerialNoSignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'}// 流水号
	]
});
// 查询条件---Store
Ext.define('Foss.sign.lostCargoSign.SignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.lostCargoSign.SignOutStorageModel',
	pageSize: 10,
	proxy : {
		type : 'ajax',
		url:sign.realPath('queryLostCargoInfoByCondition.action'),
		actionMethods : 'post',
		reader : {
			type : 'json', 
			root : 'vo.lostCargoInfoDtoList',
			totalProperty : 'totalCount' //总数
		}
	},// 事件监听
	listeners:{
		// 查询事件
		beforeload:function(s,operation,eOpts){
			// 执行查询
			var queryParams=Ext.getCmp('T_sign-lostCargoSignIndex_content').getQueryForm().getValues();
			Ext.apply(operation,{
				params:{
					'vo.lostCargoInfoDto.waybillNo':queryParams.waybillNo,// 运单号
					'vo.lostCargoInfoDto.inStockTimeStart':queryParams.inStockTimeStart,// 入库时间起
					'vo.lostCargoInfoDto.inStockTimeEnd':queryParams.inStockTimeEnd// 入库时间止
				}
			});
		}
	}
});
// 签收信息---查询签收件下的流水号Store
Ext.define('Foss.sign.lostCargoSign.SerialNoSignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.lostCargoSign.SerialNoSignOutStorageModel',
	proxy : {
		type : 'ajax',
		url:sign.realPath('queryOptStockSerinalNo.action'),
		actionMethods : 'post',//方式
		reader : {
			type : 'json',
			root : 'vo.contrabandInfoDto.signDetailList'
		}
	}
});
// 丢货签收信息
Ext.define('Foss.sign.lostCargoSign.SignOutStorageFormPanel',{
	extend: 'Ext.form.Panel',
	title: sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signInformation'), //标题--签收信息
	layout: 'column',//布局
	frame:true,
	defaults: { //默认值
		margin:'5 10 5 10',
		anchor: '98%',
		labelWidth:65
	},
	defaultType : 'textfield',
	items: [{
		xtype: 'combobox',
		name:'situation',
		fieldLabel:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.situation'),//签收情况
		allowBlank: false,//不允许为空
		valueField:'valueCode', 
		columnWidth:.43,
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		readOnly : true,
		store:sign.lostCargoSign.situationStore//异常签收-丢货
	},{
		name: 'signGoodsQty',
		xtype:'numberfield',
		hideTrigger: true,
		allowBlank:false,
		fieldLabel:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signGoodsQty'),//签收件数
		allowDecimals : false,// 不允许有小数点
	    minValue: 1, //最小值为1
		columnWidth:.23
	},{
		xtype: 'datetimefield_date97',
		editable : true,
		format : 'Y-m-d H:i:s',
		id: 'Foss_sign_LostCargoSign_SignOutStorageFormPanel_SignTime_ID',
		fieldLabel:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signTime'),//签收时间
		allowBlank:false,
//		value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		columnWidth:.45,
		name: 'signTime',
		time : true,
		dateConfig: { 
			el : 'Foss_sign_LostCargoSign_SignOutStorageFormPanel_SignTime_ID-inputEl',
			minDate: '%y-%M-%d 00:00:00',
			maxDate:'%y-%M-%d 23:59:59'
		}
	},{
		labelWidth: 60,
		xtype: 'textarea',
		name:'signNote',
		hight: 25,
		allowBlank:false,
		maxLength:200,
		fieldLabel:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signNote'),//备注
		columnWidth: 1
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


// 签收流水号GridPanel
Ext.define('Foss.sign.lostCargoSign.SerialNoOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
	height: 300,//高度
	id: 'Foss_sign_LostCargoSign_SerialNoOutStorageGridPanel_ID',
	frame: true,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    title:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signPieces'),//签收件
    emptyText:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.emptyText'),//查询结果为空
	columns: [//流水号
        {header:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.serialNo'), dataIndex: 'serialNo', flex: 1,align: 'center' }//流水号
    ],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.lostCargoSign.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});


// 弹出窗口
Ext.define('Foss.sign.lostCargoSign.SiginOutStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_LostCargoSign_SiginOutStoraWindow_ID',
	closeAction: 'close',
	layout: 'auto',	
	resizable : false,
	width:615,
	modal: true,
	title:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.lostCargoSignInfo'),//丢货签收信息
	signOutStorageFormPanel :　null,
	getSignOutStorageFormPanel: function(){
		if(this.signOutStorageFormPanel == null){
			this.signOutStorageFormPanel = Ext.create('Foss.sign.lostCargoSign.SignOutStorageFormPanel',{
				width:580
			});
		}
		return this.signOutStorageFormPanel;
	},
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.lostCargoSign.SerialNoOutStorageGridPanel',{
				width:580,
				text : sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signPieces'),//签收件
				store : Ext.create('Foss.sign.lostCargoSign.SerialNoSignOutStorageStore')
			});
		}
		return this.SerialNoOutStorageGridPanel;
	},
	resetPanel :  function(waybillInfo){
		var baseForm = this.getSignOutStorageFormPanel(),
			form = baseForm.getForm(),
			serialNoOutStorage = this.getSerialNoOutStorageGridPanel().getStore();
		this.getSerialNoOutStorageGridPanel().getSelectionModel().deselectAll();
		form.loadRecord(waybillInfo);
		form.findField('signGoodsQty').setValue(waybillInfo.get('stockGoodsQty'));
		var situation =form.findField('situation').store.getAt(0).get('valueCode');// 签收情况 code
		form.findField('situation').setValue(situation);// 签收情况默认选中“异常-丢货”
	    form.findField('signNote').setValue(null);// 备注
//		form.findField('signTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));// 签收时间默认为当前时间
	    form.findField('signTime').setValue(waybillInfo.get('serviceTime'));// 签收时间默认为服务器当前时间
		//清除运单流水号的记录
		serialNoOutStorage.removeAll();
		// 根据运单编号查询运单流水号
		serialNoOutStorage.load({
			params:{
				'vo.waybillNo':waybillInfo.get('waybillNo')
			}
		});
	},	
	serialButtons  : null,
	getSerialButtons: function(){
		if(this.serialButtons  == null){
			this.serialButtons = Ext.create('Ext.button.Button',{
			text:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signIn'),//签收 
			disabled:!sign.lostCargoSign.isPermission('lostcargosignindex/lostcargosignindexsignbutton'),
			hidden:!sign.lostCargoSign.isPermission('lostcargosignindex/lostcargosignindexsignbutton'),
			width:'100',
			style : {
				float : 'center'
			},
			cls:'yellow_button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 3
			}),
			handler:function(){
				var form  = this.up('window').signOutStorageFormPanel.getForm();
				if(!form.isValid()){
					Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.notcompleteInfo'),'error',2000);//签收信息未填写完整!
					return;
				}else if(form.findField('signGoodsQty').getValue() == 0){
					Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signGoodsQtyGreateThanZero'),'error',2000);//签收件数必须大于0
					return;
				}else if(form.getRecord().get('stockGoodsQty') < form.findField('signGoodsQty').getValue()){
					Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.stockGoodsQtyLess'),'error',2000);//签收件数不能大于库存件数
					return;
				}
				var serialNoPanel = Ext.getCmp('Foss_sign_LostCargoSign_SerialNoOutStorageGridPanel_ID'),
				  serialNorowObjs = serialNoPanel.getSelectionModel().getSelection();//得到选中的流水号信息
				if(serialNorowObjs.length <= 0 ){
					Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.selectSerialNo'),'error',2000);//请选择流水号!
					return;
				}else if(form.findField('signGoodsQty').getValue() != serialNorowObjs.length){
					Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.serialNoSignGoodsQtySame'),'error',3000);//选择流水号数量必须与签收件数一致，请确认！
					return;
				}
				Ext.MessageBox.confirm(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.okTosubmit'),function(btn){//确定提交信息？
					if(btn == 'yes'){
						var serialNos = new Array();
						for (var i = 0; i < serialNorowObjs.length; i++) {//把前台选中的流水号放入数组里
							serialNos.push({
								'serialNo':serialNorowObjs[i].get("serialNo")//流水号
							});
						}
						Ext.Ajax.request({
							url:sign.realPath('addLostCargoInfo.action'),
							method: 'POST',
							jsonData: {
								'vo':{
									'contrabandInfoDto':{
										'signDetailList':serialNos, //流水号信息集合
										'waybillNo':form.getRecord().get('waybillNo'),//运单号
										'signNote':form.findField('signNote').getValue(),//备注
										'signTime':Ext.Date.parse(form.findField('signTime').getValue(), "Y-m-d H:i:s", true),//签收时间
										'signGoodsQty': form.findField('signGoodsQty').getValue(),//签收件数
										'productCode':form.getRecord().get('productCode'),//运输性质   
										'generateGoodsQty':form.getRecord().get('generateGoodsQty'),//生成件数   
										'expType':sign.lostCargoSign.expType,//异常类型 
										'situation':form.findField('situation').getValue()//签收情况
									
									}
								}
							},
							success: function(response){
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.submitSuccess'),'ok',2000);//提交成功！
								Ext.getCmp('Foss_sign_LostCargoSign_SiginOutStoraWindow_ID').close();
								// 更新查询结果里表格的记录
								Ext.getCmp('T_sign-lostCargoSignIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
							},exception: function(response){
								var json = Ext.decode(response.responseText);
		              			Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.lostCargoFailed'), json.message, 'error', 4000);
							}
						});
					}else{
						Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'),sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.cancelSign'),'error',1000);//已取消签收！
						}
					},this);
				}
			});
		}
		return  this.serialButtons ;
		
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getSignOutStorageFormPanel(),
			me.getSerialNoOutStorageGridPanel()
		];
		me.buttons = [me.getSerialButtons()];
		me.callParent([cfg]);
	}
});

// 查询条件----第一层
Ext.define('Foss.sign.lostCargoSign.QuerysignOutStorageFormPanel',{
	extend: 'Ext.form.Panel',
	title: sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.queryBuilder'),//查询条件
    defaultType : 'textfield',
	collapsible: true,
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:90
	},
	items: [{
		fieldLabel:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.waybillNo'),
		name: 'waybillNo',//运单号
		vtype: 'waybill',
		columnWidth:.30
	},{
		xtype: 'rangeDateField',
		fieldLabel:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.inStockTime'),//入库时间
		fieldId:'Foss_sign_LostCargoSign_QuerysignOutStorageFormPanel_rangeDateField_ID',
		fromEditable:false,
		toEditable :false,
		allowBlank:false,
		disallowBlank:true,
		//dateRange: 30,
		dateType: 'datetimefield_date97',
		fromName: 'inStockTimeStart',
		toName: 'inStockTimeEnd',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,0,0,0),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .60
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.reset'),//重置
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.reset();
				//入库时间默认为当前系统时间0:00至23:59
				form.findField('inStockTimeStart').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,0,0,0),'Y-m-d H:i:s'));
				
				form.findField('inStockTimeEnd').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,23,59,59),'Y-m-d H:i:s'));
				
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.search'),//查询
			disabled:!sign.lostCargoSign.isPermission('lostcargosignindex/lostcargosignindexquerybutton'),
			hidden:!sign.lostCargoSign.isPermission('lostcargosignindex/lostcargosignindexquerybutton'),
			cls:'yellow_button',
			columnWidth:.08,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 3
			}),
			handler:function(){
				var form = this.up('form').getForm();
				// 入库时间验证
				var inStockTimeStart = form.getValues().inStockTimeStart, inStockTimeEnd = form.getValues().inStockTimeEnd;
				if (!Ext.isEmpty(inStockTimeStart) && !Ext.isEmpty(inStockTimeEnd)) {	
					var result = Ext.Date.parse(inStockTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(inStockTimeStart,'Y-m-d H:i:s');	
					if(result / (24 * 60 * 60 * 1000) >= 30){	
						Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'), sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.the.date.range.cannot.be.more.than.30.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
						return;	
					}	
				}else {//如果入库时间为空
					Ext.ux.Toast.msg(sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.tip'), sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.inStockTimeNotNull'), 'error', 3000);
						return;
				}
				if (!form.isValid())
				{
					return;
				}
				Ext.getCmp('T_sign-lostCargoSignIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
			}
		}]
	}]
});

	
// 待处理-GridPanel---第一层
Ext.define('Foss.sign.lostCargoSign.QuerysignOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    frame: true,
	id: 'Foss_sign_lostCargoSign_QuerysignOutStorage_GridPanel_Id',
    stripeRows: true,
    title:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.pending'),//待处理
    emptyText:sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.emptyText'),//查询结果为空
	collapsible: true,
	animCollapse: true,
	store: null,
	viewConfig: {
        enableTextSelection: true
    },
	siginOutStoraWindow : null,
	getSiginOutStoraWindow : function(){
		if(this.siginOutStoraWindow == null){
			this.siginOutStoraWindow = Ext.create('Foss.sign.lostCargoSign.SiginOutStoraWindow',{
			});
		}
		return this.siginOutStoraWindow;
	},
	columns: [
			{
            xtype:'actioncolumn',
            text: sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.operate'),//操作
			width: 50,
			align: 'center',
            items: [{
               iconCls: 'deppon_icons_signin',
                tooltip: sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.signIn'),//签收
                disabled:!sign.lostCargoSign.isPermission('lostcargosignindex/lostcargosignindexquerydetailbutton'),
                handler: function(grid, rowIndex, colIndex) {
                	var waybillInfo = grid.getStore().getAt(rowIndex),
                		window = grid.up('gridpanel').getSiginOutStoraWindow();
                		window.show();
                		window.resetPanel(waybillInfo);
                }
            }]
        },
		{ text :sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.waybillNo'),align: 'center',flex: 1,xtype: 'ellipsiscolumn',dataIndex : 'waybillNo' },//运单号
		{ text : sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.productCode'),align: 'center',flex: 1,dataIndex : 'productCode',
			renderer : function(value) {//运输性质
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		},
		{ text : sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.deliveryCustomerContact'),align: 'center',flex: 1,dataIndex : 'deliveryCustomerContact' },//发货客户联系人
		{ text : sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.receiveCustomerContact'),align: 'center',flex: 1,dataIndex : 'receiveCustomerContact' },//收货客户联系人
		{ text : sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.goodsQtyTotal'),align: 'center',flex: 1,dataIndex : 'goodsQtyTotal' },//开单件数
		{ text : sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.stockGoodsQty'),align: 'center',flex: 1,dataIndex : 'stockGoodsQty' },//库存件数
		{ text : sign.lostCargoSign.i18n('pkp.sign.lostCargoSign.serviceTime'),align: 'center',flex: 1,dataIndex : 'serviceTime',hidden:true,hideable:false }//服务器上的当前时间
    ],
    pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store/*,
  				plugins: 'pagesizeplugin',
				displayInfo: true*/
  			});
  		}
  		return this.pagingToolbar;
  	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.lostCargoSign.SignOutStorageStore');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm =  Ext.create('Foss.sign.lostCargoSign.QuerysignOutStorageFormPanel');
	var queryResultGrid = Ext.create('Foss.sign.lostCargoSign.QuerysignOutStorageGridPanel');
	// // 定义到达联查询列表
	Ext.create('Ext.panel.Panel',{
		id:'T_sign-lostCargoSignIndex_content',
		cls:"panelContent",
		bodyCls:'panelContent-body',
		layout:'auto',
		getArriveGrid: function(){
			return queryResultGrid;
		},
		getQueryForm: function(){
			return queryForm;
		},
		margin:'0 0 0 0',
		items : [queryForm,queryResultGrid],
		renderTo: 'T_sign-lostCargoSignIndex-body'
	});
	
});

 