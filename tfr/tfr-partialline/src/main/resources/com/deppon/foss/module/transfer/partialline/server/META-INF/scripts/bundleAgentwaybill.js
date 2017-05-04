Ext.define('Foss.partialline.bundleagentwaybill.model.bundleAgentWaybillModel', {
    extend: 'Ext.data.Model',
    fields: [
	        {name: 'id',type:'string'},//ID
	        {name: 'agentWaybillNo', type: 'string'},//快递外发单号
	        {name: 'waybillNo',  type: 'string'}, 	//运单号
	        {name: 'agentCompanyCode',   type: 'string'},	//外发快递公司
	        {name: 'frightFee', type: 'string'},		//外发费用
	        {name: 'bundleState', type: 'string'},		//绑定状态
	        {name: 'operatTime', type: 'date',			
	        	convert: function(value) {
	        		if (value != null) {
	        			var date = new Date(value);
	        			return Ext.Date.format(date,'Y-m-d H:i:s');
	        		} else {
	        			return null;
	        		}
	        	}},									//绑定时间
	        {name: 'operatorName', type: 'string'},		//操作人
	      ]
});

//查询绑定数据源
Ext.define('Foss.partialline.bundleagentwaybill.store.bundleAgentWaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.partialline.bundleagentwaybill.model.bundleAgentWaybillModel',
	pageSize:50,
	proxy: {
		type: 'ajax',
		url:partialline.realPath('queryBundleWaybills.action'),
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo.listRecordEntities',
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
			var queryForm = partialline.bundleagentwaybill.queryForm;
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				
				Ext.apply(operation, {
					params : {
						'vo.dto.waybillNo' : queryParams.waybillNo,
						'vo.dto.agentWaybillNo' : queryParams.agentWaybillNo,
						'vo.dto.bundleState' : queryParams.bundleState,
						'vo.dto.beginOperatTime' : queryParams.beginOperatTime,
						'vo.dto.endOperatTime' : queryParams.endOperatTime
					}
				});	
			}
		},
		load:function( store, records,successful,operation,eOpts ){
			if(Ext.isEmpty(records)) {
				store.removeAll();
				Ext.ux.Toast.msg('提示信息', '查询结果为空!');
			}
		}
	}
});

//查询form表单
Ext.define('Foss.partialline.bundleagentwaybill.form.searchForm',{
	extend: 'Ext.form.Panel',
	id: 'Foss.partialline.bundleagentwaybill.form.searchForm.id',
	title : partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.searchCondition.title'), //查询条件
	layout:'column',
	frame: true,
	bodyStyle:'padding:5px 5px 0 5px',	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType: 'textfield',
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:80
	},
	items: [{//运单号
		fieldLabel: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.waybillNo.label'),//运单号
		columnWidth :.23,
		name : 'waybillNo',
		labelWidth : 80,
		vtype:'order',
		emptyText : partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.waybillNo.valitation'),
		regex : /^([0-9]{8,10}\n?)+$/i,
	},{//外发快递单号
		fieldLabel: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.agentWaybillNo.label'),//外发快递单号
		name: 'agentWaybillNo',
		xtype: 'textfield',
		labelWidth : 100,
		allowBlank:true,
		regex : /^([0-9]{0,30}\n?)+$/i,
		columnWidth:.26,
		emptyText : partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.agentWaybillNo.valitation'),
	},
//	{//绑定状态
//		fieldLabel: '绑定状态',//partialline.bundleagentwaybill.i18n('Foss.printagentwaybill.form.searchWaybill.handoverNo.label'),//交接单号
//		name: 'bundleState',
//		xtype: 'combo',
//		labelWidth : 100,
//		allowBlank:true,
//		columnWidth:.3,
//		queryMode: 'local',
//	    displayField: 'name',
//	    valueField: 'code',
//	    value:'',	
//	    editable : false,
//		margin: '10 5 10 5',
//		store : Ext.create('Ext.data.Store', {
//	        fields: ['code', 'name'],
//	        data : [
//	            {'code':'','name':'全部'},	//'全部'
//			{'code':'Y','name':'已绑定'},	//'已绑定'
//			{'code':'N','name':'作废'}	//'作废'
//	        ]
//		})
//	    
//	},
	{//绑定时间
		xtype : 'rangeDateField',
		fieldLabel : partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.beginOperatTime.label'),//绑定时间
		columnWidth : .5,
		fieldId : 'Foss_partialline_model.bundleagentwaybill_operatTime_RangeDateField_ID',
		dateType : 'datetimefield_date97',
		dateRange : 31,
		fromName : 'beginOperatTime',
		editable: false,
		labelWidth: 80,
		disallowBlank:true,
		allowBlank:false,
		fromValue : Ext.Date.format(new Date(new Date()
								.getFullYear(), new Date()
								.getMonth(), new Date()
								.getDate(), "00", "00", "00"),
				'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date()
								.getFullYear(), new Date()
								.getMonth(), new Date()
								.getDate(), "23", "59", "59"),
				'Y-m-d H:i:s'),
		toName : 'endOperatTime'
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
			columnWidth:.07,
			text: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.reset'),//重置
			handler: function() {
				var form = this.up('form').getForm();
				form.reset();
				form.findField('beginOperatTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'00','00','00'), 'Y-m-d H:i:s'));
				form.findField('endOperatTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,'23','59','59'), 'Y-m-d H:i:s'));
			}
		},{
			border : false,
			columnWidth:.85,
			html: '&nbsp;'
		},{
			columnWidth:.07,
			xtype : 'button',
			text: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.select'),//查询
			cls:'yellow_button',
			handler: function() {
				var searchParms = this.up('form').getForm().getValues();
				if(!this.up('form').getForm().isValid()){
					return;
				}
				
				if(Ext.isEmpty(searchParms.waybillNo) 
						&& Ext.isEmpty(searchParms.agentWaybillNo)
						&& (Ext.isEmpty(searchParms.beginOperatTime) && Ext.isEmpty(searchParms.endOperatTime))) {
					Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.notify'), partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.condition'), 'error', 3000); 
					return;
				}
				
				
				if((!Ext.isEmpty(searchParms.beginOperatTime) && Ext.isEmpty(searchParms.endOperatTime)) 
						|| (Ext.isEmpty(searchParms.beginOperatTime) && !Ext.isEmpty(searchParms.endOperatTime))) {
					Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.notify'), partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.time.valitation'), 'error', 3000); 
							return;
						}
				
				if(this.up('form').getForm().isValid( )){
					partialline.bundleagentwaybill.pagingBar.moveFirst();
				}
			}
		}]
	}]
});

//查询结果
Ext.define('Foss.partialline.bundleagentwaybill.grid.searchResultGrid', {
	extend:'Ext.grid.Panel',
	id: 'Foss.partialline.bundleagentwaybill.grid.searchResultGrid.id',
	title :partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.searchResult.title'), //查询结果
	height: 500,
	autoScroll: true,
	columnLines: true,
	frame: true,
	forceFit: true,
	enableColumnHide: false,
    //sortableColumns: false,
    collapsible: true,
    animCollapse: true,
    emptyText : partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.empty'), //查询结果为空,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	columns: [{ //ID
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.id.label'), //id
		dataIndex: 'id',
		hideable: false, 
		hidden: true 
	},{//外发快递单号
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentWaybillNo.label'),//代理单号
		dataIndex: 'agentWaybillNo',
		width : 65,
		flex: 1.3,
		field:{
			xtype: 'textfield',
			editable:true,
			allowBlank:false,
			regex : /^([0-9]{0,30}\n?)+$/i,
			maxLength:30,
			hideTrigger: true
		}
	},{ //运单号
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.form.searchWaybill.waybillNo.label'), //运单号
		dataIndex: 'waybillNo',
		width : 55,
		flex: 1
	},{ //快递公司
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.label'), //快递公司
		dataIndex: 'agentCompanyCode',
		width : 60,
		flex: 1,
		editor:{
			xtype: 'combo',
			labelWidth : 100,
			allowBlank:true,
			columnWidth:.3,
			queryMode: 'local',
			displayField: 'name',
			valueField: 'code',
			editable : false,
			margin: '0 0 0 0',
			store : Ext.create('Ext.data.Store', {
				fields: ['code', 'name'],
				data : [
		        {'code':'SF','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.sf')},	//
				{'code':'YTO','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.yto')},	//
				{'code':'ZTO','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.zto')},	//
			    {'code':'STO','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.sto')},	//
				{'code':'BEST','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.best')},	//
				{'code':'YUNDA','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.yunda')},	//
			    {'code':'TTK','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.ttk')},	//
				{'code':'EMS','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.ems')},	//
				{'code':'ZJS','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.zjs')},	//
				{'code':'QF','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.qf')},	//
				{'code':'GTO','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.gto')},	//
				{'code':'UC','name':partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.uc')},	//
		        ]
				})
		},
		renderer:function(value){
			if (value == 'SF') {				
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.sf');
			}
			else if(value == 'YTO'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.yto');
			} 
			else if(value == 'ZTO'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.zto');
			} 
			else if(value == 'STO'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.sto');
			} 
			else if(value == 'BEST'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.best');
			} 
			else if(value == 'YUNDA'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.yunda');
			} 
			else if(value == 'TTK'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.ttk');
			} 
			else if(value == 'EMS'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.ems');
			} 
			else if(value == 'ZJS'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.zjs');
			} 
			else if(value == 'QF'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.qf');
			} 
			else if(value == 'GTO'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.gto');
			} 
			else if(value == 'UC'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.agentCompanyCode.uc');
			} 
				
		}
	},{ //外发费用
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.frightFee.label'), //外发费用
		dataIndex: 'frightFee',
		width : 60,
		flex: 1,
		field:{
			xtype: 'numberfield',
			editable:true,
			allowBlank:false,
			maxLength:30,
			hideTrigger: true
		}
	},{ //绑定状态
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.bundleState.label'), //绑定状态
		dataIndex: 'bundleState',
		width : 60,
		flex: 1,
		renderer:function(value){
			if(value == 'Y'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.bundleState.bundling');//已绑定
			}
			else if(value == 'N'){
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.bundleState.unbundling');//作废			
				}
			else{
				return partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.bundleState.disbundling');//未绑定			
				}
		}
			
	},{ //绑定时间
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.operatTime.label'), //绑定时间
		dataIndex: 'operatTime',
		width : 120,
		flex: 2.0
	},{ //操作人
		header: partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.operatorName.label'), //操作人
		dataIndex: 'operatorName',
		//fixed: true,
		width : 30,
		flex: 0.6
	}],
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
		me.store = Ext.create('Foss.partialline.bundleagentwaybill.store.bundleAgentWaybillStore');
		me.plugins = [
		  			me.getEditor()
		  		];
		me.tbar = [{//绑定
				xtype : 'button',
				text : partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.button.bundling'), //绑定
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				handler : function() {
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel().getSelection();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'), partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.pleaseSelectLine'));
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						var agentWaybillNo = selectWaybill[i].data.agentWaybillNo;
						var agentCompanyCode = selectWaybill[i].data.agentCompanyCode;
						var frightFee = selectWaybill[i].data.frightFee;
						var bundleState = selectWaybill[i].data.bundleState;
						if (bundleState == 'Y') {
							Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'),
									selectWaybill[i].data.waybillNo
											+ partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notifyOfbundleState'));//运单已经绑定，请作废再绑定！
							return;
							break;
						}
						if (Ext.isEmpty(agentWaybillNo)) {
							Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'),
									selectWaybill[i].data.waybillNo
											+ partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notifyOfagentWaybill'));//运单未输入外发快递单号，不能绑定！
							return;
							break;
						}
						if (Ext.isEmpty(agentCompanyCode)) {
							Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'),
									selectWaybill[i].data.waybillNo
											+ partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notifyOfagentCompany'));//运单未输入快递公司，不能绑定！
							return;
							break;
						}
						if (Ext.isEmpty(frightFee)) {
							Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'),
									selectWaybill[i].data.waybillNo
											+ partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notifyOffrightFee'));//运单未输入快递外发费用，不能绑定！
							return;
							break;
						}
						
					}
					var win = this.up('window');
					var parentGrid = this.up('gridpanel');
					bundle(parentGrid,win);//调用绑定方法
				}
			}, {//作废
				xtype : 'button',
				text : partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.button.unbundling'), //作废
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				margin : '0 10 5 10',
				handler : function() {
					var mygrid = this.up('gridpanel');
					var selectWaybill = mygrid.getSelectionModel().getSelection();
					if (selectWaybill.length == 0) {
						Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'), partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.pleaseSelectLine'));
						return;
					}
					for (var i = 0; i < selectWaybill.length; i++) {
						var agentWaybillNo = selectWaybill[i].data.agentWaybillNo;
						var agentCompanyCode = selectWaybill[i].data.agentCompanyCode;
						var frightFee = selectWaybill[i].data.frightFee;
						var bundleState = selectWaybill[i].data.bundleState;
						if (Ext.isEmpty(bundleState)||bundleState == '') {
							Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'),
									selectWaybill[i].data.waybillNo
											+ partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notifyOfDisbundling'));//运单未绑定状态，不能作废！
							return;
							break;
						}
						if (bundleState == 'N') {
							Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'),
									selectWaybill[i].data.waybillNo
											+ partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notifyOfUnbundling'));//运单目前已作废，不能作废！
							return;
							break;
						}
						
						
					}
					var win = this.up('window');
					var parentGrid = this.up('gridpanel');
					disbundle(parentGrid,win);//作废

				}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10],['20',20],['50',50]]
			})
		});
		partialline.bundleagentwaybill.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	viewConfig: {
		enableTextSelection: true
    }
});
//绑定操作
function bundle(parentGrid,win){
//    win.hide();
	var formEl = Ext.getCmp('Foss.partialline.bundleagentwaybill.grid.searchResultGrid.id');
	var selectWaybill = parentGrid.getSelectionModel().getSelection();
	var map = new Ext.util.HashMap();
	var waybillNos = '';
	var agentWaybillNos = '';
	var agentCompanyCodes = '';
	var frightFees = '';
//	var currentDeptCode = FossUserContext.getCurrentDept().code;
//	var currentDeptName = FossUserContext.getCurrentDept().name;
//	var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
//	var currentUserName = FossUserContext.getCurrentUser().employee.empName;
	for(var i = 0;i<selectWaybill.length;i++){
		if(waybillNos.length == 0) {
			
			waybillNos = selectWaybill[i].data.waybillNo;
		} else {
			waybillNos = waybillNos + "," + selectWaybill[i].data.waybillNo;
		}
		
		if(agentWaybillNos.length == 0) {
			agentWaybillNos = selectWaybill[i].data.agentWaybillNo;
		} else {
			agentWaybillNos = agentWaybillNos + "," + selectWaybill[i].data.agentWaybillNo;
		}
		
		if(agentCompanyCodes.length == 0) {
			agentCompanyCodes = selectWaybill[i].data.agentCompanyCode;
		} else {
			agentCompanyCodes = agentCompanyCodes + "," + selectWaybill[i].data.agentCompanyCode;
		}
		if(frightFees.length == 0) {
			frightFees = selectWaybill[i].data.frightFee;
		} else {
			frightFees = frightFees + "," + selectWaybill[i].data.frightFee;
		}
	}
	formEl.getEl().mask(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.bundling'));
	Ext.Ajax.request({
		url : partialline.realPath('bundleSdExternalBill.action'),
		params: {
			'vo.waybillNos': waybillNos,
			'vo.agentWaybillNos': agentWaybillNos,
			'vo.agentCompanyCodes': agentCompanyCodes,
			'vo.frightFees': frightFees
		},
		success : function(response) {
			formEl.getEl().unmask();
			Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'), partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.successBundling'));//绑定成功!
			if(partialline.bundleagentwaybill.queryForm.getForm().isValid( )){
				partialline.bundleagentwaybill.pagingBar.moveFirst();
			}
		},
		exception : function(response) {
			formEl.getEl().unmask();
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.error'),json.message);
		}
	});

	} 
function disbundle(parentGrid,win){//作废
	var formEl = Ext.getCmp('Foss.partialline.bundleagentwaybill.grid.searchResultGrid.id');
	var selectWaybill = parentGrid.getSelectionModel().getSelection();
	var map = new Ext.util.HashMap();
	var waybillNos = '';
	var agentWaybillNos = '';
	var agentCompanyCodes = '';
	var frightFees = '';
	var ids = '';
	for(var i = 0;i<selectWaybill.length;i++){
		if(waybillNos.length == 0) {
			
			waybillNos = selectWaybill[i].data.waybillNo;
		} else {
			waybillNos = waybillNos + "," + selectWaybill[i].data.waybillNo;
		}
		
		if(agentWaybillNos.length == 0) {
			agentWaybillNos = selectWaybill[i].data.agentWaybillNo;
		} else {
			agentWaybillNos = agentWaybillNos + "," + selectWaybill[i].data.agentWaybillNo;
		}
		if(ids.length == 0) {
			ids = selectWaybill[i].data.id;
		} else {
			ids = ids + "," + selectWaybill[i].data.id;
		}
		
	}
	formEl.getEl().mask(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.unbundling'));//正在作废绑定快递单，请稍等...
	Ext.Ajax.request({
		url : partialline.realPath('unBundleSdExternalBill.action'),
		params: {
			'vo.waybillNos': waybillNos,
			'vo.agentWaybillNos': agentWaybillNos
		},
		success : function(response) {
			formEl.getEl().unmask();
		
			//信息提示
			Ext.ux.Toast.msg(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.notify'), partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.successUnbundling'));//作废成功!
			if(partialline.bundleagentwaybill.queryForm.getForm().isValid( )){
				partialline.bundleagentwaybill.pagingBar.moveFirst();
			}
		},
		exception : function(response) {
			formEl.getEl().unmask();
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(partialline.bundleagentwaybill.i18n('Foss.bundleagentwaybill.grid.searchWaybill.error'),json.message);
		}
	});
}

Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Loader.setConfig({enabled:true});  
	var searchForm=Ext.create('Foss.partialline.bundleagentwaybill.form.searchForm');
	var searchResult=Ext.create('Foss.partialline.bundleagentwaybill.grid.searchResultGrid');
	partialline.bundleagentwaybill.queryForm=searchForm;
	partialline.bundleagentwaybill.searchResultGrid=searchResult;
	//显示绑定查询界面
	Ext.create('Ext.panel.Panel',{
		id:'T_partialline-bundleagentwaybillIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'auto',
		margin:'0 0 0 0',
		items : [searchForm,searchResult],
		renderTo: 'T_partialline-bundleagentwaybillIndex-body'
	});	
	searchResult.getStore().load();
});