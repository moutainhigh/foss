/**
 * 获取上个月期间
 */
closing.mvrPlr.getLastMonthPeriod = function(){
	var now  = new Date();
	now.setMonth(now.getMonth()-1);
	return Ext.Date.format(now,'Ym');
}

/**
 * 声明账期model
 */
Ext.define('Foss.mvrPlr.PeriodModel',{
	extend:'Ext.data.Model',
	fields:['name','code']
});

/**
 * 声明账期store
 */
Ext.define('Foss.mvrPlr.PeriodStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPlr.PeriodModel',
	proxy:{
		type:'ajax',
		url:closing.realPath('queryClosingPeriod.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'periodList'
		}
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'load': function(store, operation, eOpts){

	   			if(operation.length == 0){
	   				Ext.Msg.alert("提示", "没有生成凭证报表数据，凭证期间为空");
					return false;
	   			}
	   		}
		};
		me.callParent([ cfg ]);
	} 
});

/**
 * 偏线月报重置
 */
closing.mvrPlr.mvrPlrQueryReset=function(){
	this.up('form').getForm().reset();
}


/**
 * 偏线月报查询
 */
closing.mvrPlr.mvrPlrQuery=function(form,me){
	/**
	 * 获取偏线月报From和Grid
	 */
	var form=form.getForm();
	
	var grid=Ext.getCmp('T_closing-mvrPlrIndex_content').getGrid();
	
	if(form.isValid()){

		closing.mvrPlr.period = form.findField('mvrPlrDto.period').getValue();
		closing.mvrPlr.productCode= form.findField('mvrPlrDto.productCode').getValue();
		closing.mvrPlr.customerCode= form.findField('mvrPlrDto.customerCode').getValue();
		closing.mvrPlr.destOrgCode= form.findField('mvrPlrDto.destOrgCode').getValue();
		closing.mvrPlr.origOrgCode= form.findField('mvrPlrDto.origOrgCode').getValue();
		
		grid.store.removeAll();
		//设置该按钮灰掉
		me.disable(false);
		//30秒后自动解除灰掉效果
		setTimeout(function() {
			me.enable(true);
		}, 30000);
		grid.store.loadPage(1,{
			callback: function(records, operation, success) {
				var rawData = grid.store.proxy.reader.rawData;
				if(!success && ! rawData.isException){
					Ext.Msg.alert('提示信息',rawData.message);
					me.enable(true);
					return false;
				}
				if(success){
					var result = Ext.decode(operation.response.responseText);  
					if(!Ext.isEmpty(result.vo.list)&& result.vo.list.length>0){
						grid.show();		      	
			      	}
				}
				me.enable(true);
		    }
	});
	}else{
		Ext.Msg.alert('温馨提示', '请检查输入条件是否合法');
		return false;
	}
}






/**
 * 导出月偏线月报表
 */
closing.mvrPlr.exportMvrPlr = function(form){
	var form = Ext.getCmp('T_closing-mvrPlrIndex_content').getForm().getForm();
	//获取主面板、查询GRID
	var mainPane = Ext.getCmp('T_closing-mvrPlrIndex_content');
	var queryGrid = mainPane.getGrid();
	
	//提示是否导出
	Ext.Msg.confirm('温馨提示','确定导出偏线月报表吗?',function(btn,text){
		if('yes' == btn){
			
			var params  = {
				'vo.mvrplrDto.period':closing.mvrPlr.period,	
				'vo.mvrplrDto.customerCode':closing.mvrPlr.customerCode,
				'vo.mvrplrDto.productCode':closing.mvrPlr.productCode,
				'vo.mvrplrDto.origOrgCode':closing.mvrPlr.origOrgCode,
				'vo.mvrplrDto.destOrgCode':closing.mvrPlr.destOrgCode
			}
			
			//创建一个form
			if(!Ext.fly('exportMvrPlrForm')){
				var frm = document.createElement('form');
				frm.id = 'exportMvrPlrForm';
				frm.style.display = 'none';
				document.body.appendChild(frm);
			}
			
			//导出Ajax请求
			Ext.Ajax.request({
				url:closing.realPath('mvrPlrExport.action'), 
				form: Ext.fly('exportMvrPlrForm'),
				params:params,
				method:'post',
				isUpload: true,
				success:function(response){
					//var json = Ext.decode(response.responseText);
				},
				failure:function(response){
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', json.message);
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert('温馨提示', json.message);
				}
		    });
			
		}
	});	
}



/**
 * 定义，始发月报的Model
 */
Ext.define('Foss.mvrPlr.Model',{
	extend:'Ext.data.Model',
	fields : [
	          {
	/* ID */
			name:	'id',            
			type:  'string'
		},{	
		/* 期间 */
		name:	'period',        
			type:  'string'             
		},{	
		/* 业务类型 */
		name:		'productCode',   
			type:	 'string'              
		},{	
		/* 客户编码 */
		name:		'customerCode',  
			type:  'string'             
		},{	
		/* 客户名称 */
		name:		'customerName' , 
		  type:  'string'           
		},{	
		/* 始发部门编码 */      
		name:	'origOrgCode',   
			type:  'string'             
		},{	
		/* 始发部门名称 */          
		name:		'origOrgName',   
		  type:  'string'           
		},{	
		/* 到达部门编码 */  
		name:		'destOrgCode',   
		  type: 'string'            
		},{	
		/* 到达部门名称 */ 
		name:	'destOrgName',   
			type: 'string'              
		},
			{
		/* 外发单录入  */
		name : 'plCostVech',
		type : 'decimal'
	}, {
		/* 偏线代理成本确认 */
		name : 'plCostConfirm',
		type : 'decimal'
	}, {
		/* 偏线代理成本反确认 */
		name : 'plCostNotConfirm',
		type : 'decimal'
	}, {
		/* 应付偏线代理成本冲应收到付运费已签收 */
		name : 'plCostWoDestRcvPod',
		type : 'decimal'
	}, {
		/* 应付偏线代理成本冲应收到付运费未签收 */
		name : 'plCostWoDestRcvNpod',
		type : 'decimal'
	}, {
		/* 偏线代理成本付款申请 */
		name : 'plCostPayApply'
	},
	{
		/* 还款现金未签收 */
		name : 'urDestChNpod',
		type : 'decimal'
	}, {
		/*  还款银行未签收 */
		name : 'urDestCdNpod',
		type : 'decimal'
	}, {
		/* 还款现金已签收. */
		name : 'urDestChPod',
		type : 'decimal'
	}, {
		/*还款银行已签收. */
		name : 'urDestCdPod',
		type : 'decimal'
	}, {
		/*  预收偏线代理现金 */
		name : 'plDrCh',
		type : 'decimal'
	}, {
		/* 预收偏线代理银行 */
		name : 'plDrCd',
		type : 'decimal'
	}, {
		/* 预收偏线代理冲应收到付运费已签收 */
		name : 'plDrWoDestRcvPod',
		type : 'decimal'
	}, {
		/*  预收偏线代理冲应收到付运费未签收 */
		name : 'plDrWoDestRcvNpod',
		type : 'decimal'
	}, {
		/* 偏线退预收付款申请 */
		name : 'plDrPayApply',
		type : 'decimal'
	}]

});





/**
 * 声明偏线报表store
 */
Ext.define('Foss.mvrPlr.queryMvrPlrStore',{
	extend:'Ext.data.Store',
	model:'Foss.mvrPlr.Model',
	pageSize:100,
	proxy:{
		type:'ajax',
		url:closing.realPath('queryMvrPlrReport.action'),
		timeout:10*60*1000,
		actionMethods:'post',
		reader:{
			type:'json',
			root:'vo.queryList',
			totalProperty:'totalCount'
		}
	},
	listeners:{
		'beforeLoad':function(store, operation, eOpts){
			var form = Ext.getCmp('T_closing-mvrPlrIndex_content').getForm().getForm();
			
			//如果用户直接点击分页查询，即没点击查询按钮，则在此处获取查询条件
			if(Ext.isEmpty(closing.mvrPlr.period)){
				/**
				 * 期间、业务类型、客户编码、始发部门、到达部门
				 */
				closing.mvrPlr.period = form.findField('mvrplrDto.period').getValue();
				closing.mvrPlr.productCode = form.findField('mvrplrDto.productCode').getValue();
				closing.mvrPlr.customerCode= form.findField('mvrplrDto.customerCode').getValue();
				closing.mvrPlr.origOrgCode= form.findField('mvrplrDto.origOrgCode').getValue();
				closing.mvrPlr.destOrgCode= form.findField('mvrplrDto.destOrgCode').getValue();
			} 
			
			/**
			 * 设置偏线月报查询条件
			 */
			var searchParams = {
					'vo.mvrplrDto.period':closing.mvrPlr.period,	
					'vo.mvrplrDto.customerCode':closing.mvrPlr.customerCode,
					'vo.mvrplrDto.productCode':closing.mvrPlr.productCode,
					'vo.mvrplrDto.origOrgCode':closing.mvrPlr.origOrgCode,
					'vo.mvrplrDto.destOrgCode':closing.mvrPlr.destOrgCode
			};
			Ext.apply(operation,{
				params :searchParams
			}); 
		}
	}
});










/**
 * 基本对账单信息
 */
Ext.define('Foss.mvrPlr.QueryForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	frame:true,
	title : '查询条件',
	layout:'column',
	defaults:{
		margin : '10 5 10 5',
		labelWidth : 85,
		colspan : 1
	},
	items:[{
    	xtype:'combobox',
    	name:'mvrPlrDto.period',
    	fieldLabel:'查询期间',
    	/*editable:false,
    	allowBlank:false,
		store:Ext.create('Foss.common.PeriodStore'),
	    queryMode: 'local', 	
		displayField:'name',
		valueField:'value',
		value:closing.mvrPlr.getLastMonthPeriod(),
    	columnWidth:.3*/
    	queryMode: 'remote', 
    	store:Ext.create('Foss.mvrPlr.PeriodStore'),
		displayField:'name',
		valueField:'name',
		allowBlank : false,
		columnWidth : .33
    },{
		xtype : 'combo',
		name : 'mvrPlrDto.productCode',
		fieldLabel : '业务类型',
		forceSelection : true,
		displayField : 'name',
		valueField : 'code',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		store : Ext.create('Foss.pkp.ProductStore'),
		columnWidth : .33
	},{
		xtype:'commonvehagencycompselector',
    	fieldLabel :'偏线代理公司',
    	name:'mvrPlrDto.customerCode',
    	columnWidth:.33
 	},{
    	xtype:'dynamicorgcombselector',
    	name:'mvrPlrDto.origOrgCode',
    	fieldLabel:'始发部门',
    	columnWidth:.33
    },{
    	xtype:'dynamicorgcombselector',
    	name:'mvrPlrDto.destOrgCode',
    	fieldLabel:'到达部门',
    	columnWidth:.33
    },{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:'重置',
			columnWidth:.08,
			handler:closing.mvrPlr.mvrPlrQueryReset
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.74
		},{
			text:'查询',
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form=this.up('form');
				var me = this;
				closing.mvrPlr.mvrPlrQuery(form,me);
			}
		}]
  	}]
});



/**
 * 表格
 */
Ext.define('Foss.mvrPlr.Grid', {
	extend : 'Ext.grid.Panel',
	title : '报表明细',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:600,
	store : Ext.create('Foss.mvrPlr.queryMvrPlrStore'),
	defaults : {
		align : 'center',
		margin : '5 0 5 0'
	},
	viewConfig : {
		enableTextSelection : true,
		stripeRows: false,//显示重复样式，不用隔行显示
  	 	getRowClass:function(record, rowIndex, p, store){
  	 		var count = store.data.length;
  	 		if(count>0 && rowIndex==store.data.length-1) {
  	 			return 'closing-totalBgColor';
  	 		} 
  	 	}
	},
	columns : [{
		text:'数据统计维度',
		sortable : false,
		defaults : {
			align : 'center'
		},
		columns:[{
			xtype:'rownumberer',
			width:40
		},{
			/* 期间 */
			dataIndex : 'period',
			header: '期间 '
		},{
			/* 业务类型 */
			dataIndex : 'productCode',
			header: '业务类型 '
		},{
			/* 业务类型名称 */
			dataIndex : 'productCode',
			header: '业务类型名称 ',
			renderer:function(value){
				return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
			}
		}, {
			/* 客户名称 */
			dataIndex : 'customerName',
			header: '客户名称'
		}, {
			/* 客户编码 */
			dataIndex : 'customerCode',
			header: '客户编码'
		},{
			/* 部门名称 */
			dataIndex : 'origOrgCode',
			header: '始发部门编码',
			width:150
		},{
			/* 部门名称 */
			dataIndex : 'origOrgName',
			header: '始发部门名称',
			width:150
		},{
			/* 部门编码 */
			dataIndex : 'destOrgCode',
			header: '偏线到达部门编码'
		},{
			/* 部门编码 */
			dataIndex : 'destOrgName',
			header: '偏线到达部门名称 '
		}]
	},{text:'偏线代理成本',
		sortable : false,
		defaults : {
			align : 'center'
		},
		columns:[{
			dataIndex : 'plCostVech',
			header: '外发单录入'
		},{
			dataIndex : 'plCostConfirm',
			header: '偏线代理成本确认'
		}, {
			dataIndex : 'plCostNotConfirm',
			header: '偏线代理成本反确认'
		},{
			dataIndex : 'plCostWoDestRcvPod',
			header: '应付偏线代理成本冲应收到付运费已签收'
		},{
			dataIndex : 'plCostWoDestRcvNpod',
			header: '应付偏线代理成本冲应收到付运费未签收'
		},{
			dataIndex : 'plCostPayApply',
			header: '偏线代理成本付款申请'
		}]
	},{text:'还款运单总运费（到付）',
		sortable : false,
		defaults : {
			align : 'center'
		},
		columns:[{
			dataIndex : 'urDestChNpod',
			header: '还款现金未签收'
		},{
			dataIndex : 'urDestCdNpod',
			header: '还款银行未签收'
		}, {
			dataIndex : 'urDestChPod',
			header: '还款现金已签收'
		},{
			dataIndex : 'urDestCdPod',
			header: ' 还款银行已签收'
		}]
	},{text:'预收偏线代理',
		sortable : false,
		defaults : {
			align : 'center'
		},
		columns:[{
			dataIndex : 'plDrCh',
			header: '预收偏线代理现金'
		},{
			dataIndex : 'plDrCd',
			header: '预收偏线代理银行'
		},{
			dataIndex : 'plDrWoDestRcvPod',
			header: '预收偏线代理冲应收到付运费已签收'
		},{
			dataIndex : 'plDrWoDestRcvNpod',
			header: '预收偏线代理冲应收到付运费未签收'
		}, 
		{
			dataIndex : 'plDrPayApply',
			header: '偏线退预收付款申请'
		}]
	}],
	getExportButton:function(){
		var me = this;
		if(Ext.isEmpty(me.exportButton)){
			me.exportButton = Ext.create('Ext.Button',{
				text:'导出',
				height:20,
				handler:closing.mvrPlr.exportMvrPlr,
				disabled:!closing.mvrPlr.isPermission('/stl-web/closing/mvrPlrExport.action'),
				hidden:!closing.mvrPlr.isPermission('/stl-web/closing/mvrPlrExport.action')
			});
		}
		return me.exportButton;
	},
    initComponent:function(){
		var me = this;
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    		
		    items: [{
				xtype:'standardpaging',
				store:me.store,
				pageSize:100,
				columnWidth:1,
				items:[me.getExportButton()],
				plugins: Ext.create('Deppon.ux.PageSizePlugin', {
					//设置分页记录最大值，防止输入过大的数值
					maximumSize: 100,
					sizeList: [['10', 10], ['30', 30], ['50', 50], ['100', 100]]
				})
			 }]
   		 }];
   		 me.callParent();
    }
});

Ext.onReady(function() {
	var queryForm = Ext.create('Foss.mvrPlr.QueryForm');
	var queryGrid = Ext.create('Foss.mvrPlr.Grid');
	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_closing-mvrPlrIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getGrid:function(){
			return queryGrid;
		},
		getForm:function(){
			return queryForm;
		},
		items : [queryForm,queryGrid],
		renderTo : 'T_closing-mvrPlrIndex-body'
	});
});