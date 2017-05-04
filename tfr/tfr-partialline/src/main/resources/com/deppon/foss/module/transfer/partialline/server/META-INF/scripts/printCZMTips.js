//打印查询Model
Ext.define('Foss.express.printCZMTips.model.printTipsModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id' , type: 'string'}, //id
        {name: 'agentWaybillNo', type: 'string'},	//代理单号
        {name: 'waybillNo',  type: 'string'}, 	//运单号
        {name: 'serialNo',  type: 'string'}, 	//流水号
        {name: 'isBind', type: 'string'},//是否绑定代理
        {name: 'isPicPackage', type: 'string'},//子母件信息
        {name: 'goodsName', type: 'string'},//货物名称
        {name: 'parentWaybillNo', type: 'string'},//对应母件
        {name: 'isPrint', type: 'string'}//是否打印标签
    ]
});

//打印查询数据源
Ext.define('Foss.express.printCZMTips.store.printTipsStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.express.printCZMTips.model.printTipsModel',
	pageSize:200,
	proxy: {
		type: 'ajax',
		url:partialline.realPath('queryCZMTipsList.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.list',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = partialline.printCZMTips.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'vo.handOverBillNos' : queryParams.handoverNo,
						'vo.waybillNos' : queryParams.waybillNo
					}
				});	
			}else{
				return;
			}
		},
		load:function( store, records,successful,operation,eOpts ){
			if(store.getProxy().getReader().jsonData.vo.tips!='' && store.getProxy().getReader().jsonData.vo.tips!=null){
				Ext.Msg.alert(store.getProxy().getReader().jsonData.vo.tips);
			}
			if(Ext.isEmpty(records)) {
				store.removeAll();
				Ext.ux.Toast.msg('提示信息', '查询结果为空!');
			}
		}
	}
});

//查询form表单 
Ext.define('Foss.express.printCZMTips.form.searchForm',{
	extend: 'Ext.form.Panel',
//	id: 'Foss.express.printCZMTips.form.searchForm.id',
	title : partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.button.label.query'), //查询,
	layout:'column',
	frame: true,
//	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
//	defaultType: 'textfield',
	defaults: {
		margin:'5 10 5 10',
//		anchor: '90%',
		labelWidth:60
	},
	items: [{
		fieldLabel: partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.label.waybillNo'),//运单号
		columnWidth :.3,
		name : 'waybillNo',
		labelWidth : 60,
		xtype : 'textarea',
		emptyText : partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.valitation.waybillNo'),
		regex : /^([0-9]{8,10}\n?)+$/i,
		regexText : partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.valitation.waybillNo')
	},{
		fieldLabel: partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.label.handOverbillNo'),//交接单号
		name: 'handoverNo',
		xtype: 'textarea',
		labelWidth : 60,
		allowBlank:true,
		columnWidth:.3,
		emptyText : partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.valitation.handOverbillNo')
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 10'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.button.label.reset'),//重置
			handler: function() {
				var form = this.up('form').getForm();
				form.reset();
			}
		},{
			border : false,
			columnWidth:.78,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			text: partialline.printCZMTips.i18n('Foss.express.printCZMTips.searchForm.button.label.query'),//查询
			cls:'yellow_button',
			handler: function() {
				var searchParms = this.up('form').getForm().getValues();
				if(!this.up('form').getForm().isValid()){
					return;
				}
				if(Ext.isEmpty(searchParms.waybillNo) && Ext.isEmpty(searchParms.handoverNo)) {
					Ext.ux.Toast.msg(partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.condition'), 'error', 3000); 
					return;
				}
				// 验证运单号输入的行数
				if (!Ext.isEmpty(searchParms.waybillNo)) {
					var arrayWaybillNo = searchParms.waybillNo.split('\n');
					if (arrayWaybillNo.length > 50) {
						Ext.ux.Toast.msg(partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.valitation'), 'error', 3000); // 
						return;	
					}
					for (var i = 0; i < arrayWaybillNo.length; i++) {
						if (Ext.isEmpty(arrayWaybillNo[i])) {
							Ext.ux.Toast.msg(partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.waybillNo.valitation'), 'error', 3000); 
							return;	
						}
					}
				}
				//验证交接单号输入行数
				if (!Ext.isEmpty(searchParms.handoverNo)) {
					var arrayHandOverNo = searchParms.handoverNo.split('\n');
					if (arrayHandOverNo.length > 50) {
						Ext.ux.Toast.msg(partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.handOverNo.valitation'), 'error', 3000); 
						return;	
					}
					for (var i = 0; i < arrayHandOverNo.length; i++) {
						if (Ext.isEmpty(arrayHandOverNo[i])) {
							Ext.ux.Toast.msg(partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.notify'), partialline.printCZMTips.i18n('Foss.printagentwaybill.form.searchWaybill.handOverNo.valitation'), 'error', 3000); // 运单号不能录入空回车
							return;	
						}
					}
				}
				if(this.up('form').getForm().isValid()){
					partialline.printCZMTips.pagingBar.moveFirst();
				}
			}
		}]
	}]
});

//查询结果
Ext.define('Foss.express.printCZMTips.grid.searchResultGrid', {
	extend:'Ext.grid.Panel',
	//id: 'Foss.express.printCZMTips.grid.searchResultGrid.id',
	title : partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.waybillNo'), //运单信息,
	height: 500,
	autoScroll: true,
	columnLines: true,
	frame: true,
	forceFit: true,
	enableColumnHide: false,
    //sortableColumns: false,
    collapsible: true,
    animCollapse: true,
    emptyText : partialline.printCZMTips.i18n('Foss.printagentwaybill.grid.searchWaybill.empty'), //查询结果为空,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns: [{
		header: partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.agentWaybillNo'),//代理单号
		dataIndex: 'agentWaybillNo',
		width : 80,
		flex: 1.3
	},{ 
		header: partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.waybillNo'), //运单号
		dataIndex: 'waybillNo',
		width : 80,
		flex: 1.1
	},{ 
		header: partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.serialNo'), //流水号
		dataIndex: 'serialNo',
		width : 45,
		flex: 1.1
	},{ 
		header: partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.isBind'), //是否绑定
		dataIndex: 'isBind',
		width : 40,
		flex: 0.6,
		renderer : function(value,metaData,record){
			var isBind = record.get('isBind');
			if(isBind == 'Y'){
				value = '已绑定';
			}else{
				value = '未绑定';
			}
			return value;
		}
	},{ 
		header: partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.zmjType'), //子母件信息
		dataIndex: 'isPicPackage',
		width : 65,
		flex: 1.2,
		renderer : function(value,metaData,record){
			var zmjType = record.get('isPicPackage');
			if(zmjType=='Y'){
				value = '母件';
			}else{
				value = '子件';
			}
			return value;
		}
	},{ 
		header: partialline.printCZMTips.i18n('Foss.printagentwaybill.grid.searchWaybill.goodsName.label'), //货物名称
		dataIndex: 'goodsName',
		width : 50,
		flex: 1.0
	},{ 
		header: partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.parentWaybillNo'), //对应母件
		dataIndex: 'parentWaybillNo',
		//fixed: true,
		width : 90,
		flex: 1.8
	},{ 
		header: partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.label.isPrint'), //是否打印标签
		dataIndex: 'isPrint',
		//fixed: true,
		width : 45,
		flex: 0.6,
		renderer : function(value,metaData,record){
			var isPrint = record.get('isPrint');
			if(isPrint=='Y'){
				value = '已打印';
			}else{
				value = '未打印';
			}
			return value;
		}
	}],
	printWindow : null,
	editor: null,
    getEditor: function(){
    	if(this.editor==null){
    		this.editor = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1
			});
    	}
    	return this.editor;
    },
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.express.printCZMTips.store.printTipsStore');
		me.plugins = [
		  			me.getEditor()
		  		];
		me.tbar = [{
				xtype : 'button',
				text : partialline.printCZMTips.i18n('Foss.express.printCZMTips.grid.button.label.print'), //打印提示标签
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				handler : function() {
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel().getSelection();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg("提示信息", "请选择行！");
						return;
					}
					printCZMTips(mygrid);
				}
			}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['100', 100],['200', 200],['500', 500]]
			})
		});
		partialline.printCZMTips.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig: {
		enableTextSelection: true
    }
});

//执行打印操作
function printCZMTips(grid){
	//获取字段
	var selectWaybill = grid.getSelectionModel().getSelection();
	var currentDeptCode = FossUserContext.getCurrentDept().code;
	var currentDeptName = FossUserContext.getCurrentDept().name;
	var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
	var currentUserName = FossUserContext.getCurrentUser().employee.empName;
	var waybillNos = "";
	var serialNos = "";
	var recordList = new Array();
	for (var i = 0; i < selectWaybill.length; i++) {
		var record = selectWaybill[i];
		record.data.operatorCode = currentUserCode;
		record.data.operatorName = currentUserName;
		record.data.orgCode = currentDeptCode;
		record.data.orgName = currentDeptName;
		recordList.push(record.data);
	}
	params = {
			'vo' : { 'list' :recordList}
	};
	var needPrintNum = recordList.length;
	Ext.Ajax.request({
		url : partialline.realPath('obtainPrintTipsData.action'), //从 后台获取打印数据
		jsonData : params,
		async: false,
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var printTipsDtos = result.vo.list;
		    var itemNumber = printTipsDtos.length;
		    var tips = result.vo.tips;
		    if(tips !=''){
		    	alert(tips);
		    }
		    if(itemNumber == 0){
		    	return;
		    }
		    Ext.Msg.wait( '处理中，请稍候...' , '提示' ); //进度条等待  
			var succPrintNum = 0;//打印成功数
			var failPrintNum = 0;//打印失败数
			url = "http://localhost:8077/print";
			for(var i=0;i<itemNumber;i++){
				var printTipsDto = printTipsDtos[i];
				var agentWaybillNos = printTipsDto.refAgentWaybillNos;
				var waybillNo = printTipsDto.waybillNo;
				var number = printTipsDto.number;
				Ext.data.JsonP.request({
					url : url,
					callbackKey : 'callback',
					async: false,
					params : {
						lblprtworker : "TipsPrintWorker",
						agentWaybillNos:agentWaybillNos,
						number : number
					},
					success : function(result, request) {
						itemNumber -- ;
						succPrintNum ++;
						if(itemNumber == 0){
							Ext.Msg.hide();
							Ext.ux.Toast.msg('提示信息', '打印成功'+succPrintNum+'件，打印失败'+(needPrintNum - succPrintNum) +'件！');
						}
						params = {
								'vo' : { 'printCZMTipsDto' :printTipsDto}
						};
						Ext.Ajax.request({
							url : partialline.realPath('savePrintRecord.action'), //从 后台获取打印数据
							jsonData : params,
							async: false,
							success : function(response) {
								partialline.printCZMTips.searchResultGrid.getStore().load();
							},
							exception : function(response){
								Ext.ux.Toast.msg('提示信息', '保存打印信息失败！');
							}
						//打印成功将数据保存
						});
					},
					failure : function (result, request) {
						itemNumber -- ;
						failPrintNum ++;
						if(itemNumber == 0){
							Ext.Msg.hide();
							Ext.ux.Toast.msg('提示信息', '打印成功'+succPrintNum+'件，打印失败'+(needPrintNum - succPrintNum) +'件！');
						}
					}
				});
			}
			partialline.printCZMTips.pagingBar.moveFirst();
		},
		exception : function(response) {
		 //	myMask.hide();
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert("提示", result.message);
		}
	});
}

/************************************end***************************************/
Ext.onReady(function() {
	Ext.QuickTips.init();//提示语
	var searchForm=Ext.create('Foss.express.printCZMTips.form.searchForm');
	var searchResult=Ext.create('Foss.express.printCZMTips.grid.searchResultGrid');
	partialline.printCZMTips.queryForm=searchForm;
	partialline.printCZMTips.searchResultGrid=searchResult;
	//显示打印查询界面
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-printCZMTipsIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [searchForm,searchResult],
		renderTo: 'T_partialline-printCZMTipsIndex-body'
	});	
//	searchResult.getStore().load();
});