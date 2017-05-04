//货物位置状态
Ext.define('Foss.platform.fieldStockMovtionDetail.stockStatusLoction',{
	extend:'Ext.data.Store',
	fields:[
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
	],
	data:{
		'items':[
		    {valueCode:'ARRIVED_UNLOAD',valueName:'到达未卸'},
			{valueCode:'UNLOADING',valueName: '卸车中'},
			{valueCode:'TRAY',valueName: '待叉区库存'},
			{valueCode:'PACKAGING',valueName: '包装库区库存'},
			{valueCode:'STOCK',valueName:'零担中转库区库存'},
			{valueCode:'DELIVER',valueName: '零担派送库区库存'},
			{valueCode:'EXPRESS',valueName: '快递中转库区库存'},
			{valueCode:'EXPRESS_DELIVER',valueName: '快递派送库区库存'},
			{valueCode:'LOADED',valueName:'已装车'}

		]
	},
	proxy:{
		type:'memory',
		reader:{
			type: 'json',
			root:'items'
		}
	}
	
});

//运输性质
Ext.define('Foss.platform.fieldStockMovtionDetail.ProductStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: platform.realPath('queryProductList.action'),
		reader : {
			type : 'json',
			root : 'fieldStockMovtionVo.productList'
		}
    }
});


//定义查询条件的panel

Ext.define('Foss.platform.fieldStockMovtionDetail.fieldStockMovtionDetailForm', {
	extend : 'Ext.form.Panel',
	title: platform.fieldStockMovtionDetail.i18n('Foss.platform.queryPDAOnlineUsing.title'),//查询条件
	//id : 'Foss.platform.fieldStockMovtionDetail.fieldStockMovtionDetailForm_Id',
	frame : true,
	collapsible : true,
	bodyStyle:'padding:5px 5px 0',
	defaultType: 'textfield',
	layout:'column',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 100,
		maxLength:20,
	    maxLengthText:platform.fieldStockMovtionDetail.i18n('Foss.platform.queryPDAOnlineUsing.tip.maxLength') //长度已超过最大限制
	},
	//height : 245,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		    xtype:'commontransfercenterselector',
		    id:'Foss.platform.fieldStockMovtionDetail.transferCenterCode.id',
		    name:'transferCenterCode',
			fieldLabel:platform.fieldStockMovtionDetail.i18n('Foss.platform.queryPDAOnlineUsing.label.transferCenterCode'), //外场
			columnWidth:.35,
			displayField:'name', 
			valueField:'orgCode'
		  },{
		    id:'Foss.platform.fieldStockMovtionDetail.waybillNo.id',
		    name:'waybillNo',
			fieldLabel:platform.fieldStockMovtionDetail.i18n('Foss.platform.fieldStockMovtionDetail.label.waybillNo'), //运单号
			columnWidth:.35
			
		  },{
			  id:'Foss.platform.fieldStockMovtionDetail.productCode.id',
    		  xtype: 'combo',
    		  fieldLabel: platform.fieldStockMovtionDetail.i18n('Foss.platform.fieldStockMovtionDetail.label.productCode'),//运输性质
    		  name: 'productCode',
    		  displayField: 'valueName',
    		  valueField:'valueCode', 
    		  value: 'ALL',
    		  columnWidth:.35,
    		  queryMode:'local',
    		  triggerAction:'all',
    		  editable:false,
    		  store:Ext.create('Foss.platform.fieldStockMovtionDetail.ProductStore')
			  
		  },{
		  	id:'Foss.platform.fieldStockMovtionDetail.stockStatusLoction.id',
			fieldLabel: platform.fieldStockMovtionDetail.i18n('Foss.platform.fieldStockMovtionDetail.label.stockStatusLoction'),//当前货物位置
		  	columnWidth:.35,
			xtype: 'combo',
			name: 'stockStatusLoction',
  		    displayField: 'valueName',
  		    valueField:'valueCode', 
  		    value: 'ALL',
  		    columnWidth:.35,
  		    queryMode:'local',
  		    triggerAction:'all',
  		    editable:false,
			store:Ext.create('Foss.platform.fieldStockMovtionDetail.stockStatusLoction'),
			listeners : {
				change : function(field, newValue, oldValue, eOpts){ 
					var locationArray = new Array();
				    locationArray.push({valueCode:'ALL',valueName:'全部'});
					locationArray.push({valueCode:'ARRIVED_UNLOAD',valueName:'到达未卸'});
					locationArray.push({valueCode:'UNLOADING',valueName: '卸车中'});
					locationArray.push({valueCode:'TRAY',valueName: '待叉区库存'});
					locationArray.push({valueCode:'PACKAGING',valueName: '包装库区库存'});
					locationArray.push({valueCode:'STOCK',valueName:'零担中转库区库存'});
					locationArray.push({valueCode:'DELIVER',valueName: '零担派送库区库存'});
					locationArray.push({valueCode:'EXPRESS',valueName: '快递中转库区库存'});
					locationArray.push({valueCode:'EXPRESS_DELIVER',valueName: '快递派送库区库存'});
					locationArray.push({valueCode:'LOADED',valueName:'已装车'});
				    var locationType=Ext.getCmp('Foss.platform.fieldStockMovtionDetail.stockStatusLoction.id');
				    locationType.store.loadData(locationArray);
					
				}
				
			}
	     }, 
	      {
			  id:'Foss.platform.fieldStockMovtionDetail.queryDate.id',
			  xtype:'datefield',
		      fieldLabel:platform.fieldStockMovtionDetail.i18n('Foss.platform.queryPDAOnlineUsing.label.queryDate'),  //查询日期
		      allowBlank:false,
			  name:'queryDate',
			  editable:false,
			  readOnly:true,
			  value: login.currentServerTime,
			  format:'Y-m-d H:i:s'
		   },{
				readOnly:true,
				columnWidth:.50
		    },{
				xtype:'button',
				text:platform.fieldStockMovtionDetail.i18n('Foss.platform.queryPDAOnlineUsing.button.reset'),  //重置
				width:90,
				handler: function() {
					var form = this.up('form').getForm();
					form.findField('waybillNo').setValue('');
					form.findField('productCode').setValue('ALL');
				},
				columnWidth:.10
			},{
				readOnly:true,
				columnWidth:.8
			},{
				xtype:'button',
				text:platform.fieldStockMovtionDetail.i18n('Foss.platform.queryPDAOnlineUsing.button.search'),  //查询
				width:90,
				cls:'yellow_button',
				handler:function(){
					var form = this.up('form').getForm();
	                platform.fieldStockMovtionDetail.waybillNo=Ext.getCmp('Foss.platform.fieldStockMovtionDetail.waybillNo.id').getValue();
                    platform.fieldStockMovtionDetail.productCode=Ext.getCmp('Foss.platform.fieldStockMovtionDetail.productCode.id').getValue();
                    var colIndexValue=Ext.getCmp('Foss.platform.fieldStockMovtionDetail.stockStatusLoction.id').getValue();
                    if(colIndexValue==='ARRIVED_UNLOAD'){
                    	platform.fieldStockMovtionDetail.colIndex=1;
	           		 }else if(colIndexValue==='UNLOADING'){
	           			platform.fieldStockMovtionDetail.colIndex=2;
	           		 }else if(colIndexValue==='TRAY'){
	           			platform.fieldStockMovtionDetail.colIndex=3;
	           		 }else if(colIndexValue==='PACKAGING'){
	           			platform.fieldStockMovtionDetail.colIndex=4;
	           		 }else if(colIndexValue==='STOCK'){
	           			platform.fieldStockMovtionDetail.colIndex=5;
	           		 }else if(colIndexValue==='DELIVER'){
	           			platform.fieldStockMovtionDetail.colIndex=6;
	           		 }else if(colIndexValue==='EXPRESS'){
	           			platform.fieldStockMovtionDetail.colIndex=7;
	           		 }else if(colIndexValue==='EXPRESS_DELIVER'){
	           			platform.fieldStockMovtionDetail.colIndex=8;
	           		 }else if(colIndexValue==='LOADED'){
	           			platform.fieldStockMovtionDetail.colIndex=9;
	           		 }else {
	           		 	platform.fieldStockMovtionDetail.colIndex=0;
	           		 }
                    
                    var grid=platform.fieldStockMovtionDetail.queryResult;
					if(!form.isValid()){
						return;
					}
					if(platform.fieldStockMovtionDetail.colIndex==0
						&&platform.fieldStockMovtionDetail.waybillNo==''
					){
					  Ext.MessageBox.alert('提示', '运单为空，不能查询场内库存的全部位置！');
					  return;
					}
					
					var store=grid.getStore( );
					//清除掉之前的数据
					store.removeAll(false);
					//重新加载数据
					platform.fieldStockMovtionDetail.pagingBar.moveFirst();
					Ext.getCmp('Foss.platform.fieldStockMovtionDetail.queryDate.id').setValue(new Date());

				},
				columnWidth:.10
			}]
			
});


//定义查询pda在线使用的model

Ext.define('Foss.platform.fieldStockMovtionDetail.queryPDAOnlineMothModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'transferCenterCode',//外场code
				type : 'string'
			}, {
				name : 'transferCenterName',//外场name
				type : 'string'
			}, {
				name : 'waybillNo',//运单号
				type : 'string'
			},{
				name : 'transportType',//运输类型
				type : 'string'
			}, {
				name : 'pickupMethod',//提货方式
				type : 'string'
			}, {
				name : 'goodsQtyTotal',//开单件数
				type : 'string'
			}, {
				name : 'goodsWeightTotal',//开单重量(千克)
				type : 'string'
			},{
				name : 'goodsVolumeTotal',//开单体积(方)
				type : 'string'
			},{
				name : 'currentGoodsQty',//当前件数
				type : 'string'
			}, {
				name : 'currentWeight',//当前重量(千克)
				type : 'string'
			},{
				name : 'currentVolume',//当前体积(方)
				type : 'string'
			},{
				name : 'currentLocaton',//当前位置
				type : 'string'
			}, {
				name : 'vehicleNo',//车牌号
				type : 'string'
			}, {
				name : 'lastOrgName',//上一部门
				type : 'string'
			}, {
				name : 'lastOrgCode',//上一部门
				type : 'string'
			},{
				name : 'nextOrgName',//下一部门
				type : 'string'
			}, {
				name : 'nextOrgCode',//下一部门
				type : 'string'
			}
		 ]
});
//查询pda在线使用的store
Ext.define('Foss.platform.fieldStockMovtionDetail.fieldStockMovtionDetailStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	// 绑定一个模型
	model : 'Foss.platform.fieldStockMovtionDetail.queryPDAOnlineMothModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		//Ext.BLANK_IMAGE_URL='sendRateDayQuery.action',
		//Ext.Ajax.timeout=300000,
		url : platform.realPath('queryFieldStockMovtionDetail.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'fieldStockMovtionVo.fieldStockMovtionDetailList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
			           'fieldStockMovtionVo.queryConditionStockMovtionDto.colIndex':platform.fieldStockMovtionDetail.colIndex,
			           'fieldStockMovtionVo.queryConditionStockMovtionDto.waybillNo':platform.fieldStockMovtionDetail.waybillNo,
			           'fieldStockMovtionVo.queryConditionStockMovtionDto.productCode':platform.fieldStockMovtionDetail.productCode,
 				       'fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgCode':platform.fieldStockMovtionDetail.transferCenterCode,
					   'fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgName':platform.fieldStockMovtionDetail.transferCenterName
					}
				});	
		}
	},
			
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日派送率 列表
Ext.define('Foss.platform.fieldStockMovtionDetail.fieldStockMovtionDetailGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	//columnLines: true,
	title : '场内流动库存查询',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	frame : true,
	columnLines : true,
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.fieldStockMovtionDetail.fieldStockMovtionDetailStore');
		me.bbar = Ext.create('Deppon.StandardPaging', {
			store : me.store,
			pageSize : 10,
			maximumSize : 100,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['10', 10], ['30', 30],['50', 50], ['100', 100]]
			})
		});
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function() {
			
				// 获取查询参数
				params = {
			           'fieldStockMovtionVo.queryConditionStockMovtionDto.colIndex':platform.fieldStockMovtionDetail.colIndex,
			           'fieldStockMovtionVo.queryConditionStockMovtionDto.waybillNo':platform.fieldStockMovtionDetail.waybillNo,
			           'fieldStockMovtionVo.queryConditionStockMovtionDto.productCode':platform.fieldStockMovtionDetail.productCode,
 				       'fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgCode':platform.fieldStockMovtionDetail.transferCenterCode,
					   'fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgName':platform.fieldStockMovtionDetail.transferCenterName
					}
				if (!Ext.fly('downloadAttachFileForm')) {
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm';
					frm.style.display = 'none';
					document.body.appendChild(frm);
				}

				Ext.Ajax.request({
					url : platform.realPath('exportFieldStockMovtionDetail.action'),
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
		}]
	platform.fieldStockMovtionDetail.pagingBar=me.bbar;
	me.callParent([cfg]);
	},
	columns : [{
		dataIndex : 'transferCenterName',
		align : 'center',
		text:'转运场',
		width:150,
		height:50
	}, {
		text : '运单号',
		dataIndex : 'waybillNo',
		align : 'center',
		xtype : 'ellipsiscolumn',
		width:100
   },{
	    dataIndex : 'currentLocaton',//当前位置
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '当前位置',
		width:100
	},{
	    dataIndex : 'transportType',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '运输性质',
		width:100
	},{
		text : '提货方式',
		dataIndex : 'pickupMethod',
		align : 'center',
		xtype : 'ellipsiscolumn',
		width:100
   },{
	    dataIndex : 'vehicleNo',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '车牌号',
		width:80
	},{
	    dataIndex : 'lastOrgName',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '上一线路',
		width:120
	},{
		text : '下一线路',
		dataIndex : 'nextOrgName',
		align : 'center',
		xtype : 'ellipsiscolumn',
		width:150
   },{
	    dataIndex : 'goodsQtyTotal',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '开单件数',
		width:80
	},{
	    dataIndex : 'goodsWeightTotal',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '开单重量(千克)',
		width:100
		
	},{
		text : '当前件数',
		dataIndex : 'currentGoodsQty',
		align : 'center',
		xtype : 'ellipsiscolumn',
		width:80
   },{
	    dataIndex : 'currentWeight',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '当前重量(千克)',
		width:100
	},{
	    dataIndex : 'currentVolume',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '当前体积(方)',
		width:100
	}]
});

// 创建查询form
platform.fieldStockMovtionDetail.fieldStockMovtionDetailForm = Ext.create('Foss.platform.fieldStockMovtionDetail.fieldStockMovtionDetailForm');
// 创建查询grid
platform.fieldStockMovtionDetail.queryResult = Ext.create('Foss.platform.fieldStockMovtionDetail.fieldStockMovtionDetailGrid');
//入口函数
Ext.onReady(function() {
	
 //通过当前登录部门编码去找顶级外场
          currentOrgCode=FossUserContext.getCurrentDept().code;
          if(currentOrgCode==null||currentOrgCode==''){
             return;
          	  
          }
		 platform.fieldStockMovtionDetail.transferCenterCode='';
		 platform.fieldStockMovtionDetail.transferCenterName='';
		 //设当前位置名称
		 var stockLocationCode='';
		 var stockLocationName='';
		 if(platform.fieldStockMovtionDetail.colIndex==1){
			 stockLocationCode='ARRIVED_UNLOAD';
			 stockLocationName='到达未卸';
		 }else if(platform.fieldStockMovtionDetail.colIndex==2){
			 stockLocationCode='UNLOADING';
			 stockLocationName='卸车中';
		 }else if(platform.fieldStockMovtionDetail.colIndex==3){
			 stockLocationCode='TRAY';
			 stockLocationName='待叉区库存';
		 }else if(platform.fieldStockMovtionDetail.colIndex==4){
			 stockLocationCode='PACKAGING';
			 stockLocationName='包装库区库存';
		 }else if(platform.fieldStockMovtionDetail.colIndex==5){
			 stockLocationCode='STOCK';
			 stockLocationName='零担中转库区库存';
		 }else if(platform.fieldStockMovtionDetail.colIndex==6){
			 stockLocationCode='DELIVER';
			 stockLocationName='零担派送库区库存';
		 }else if(platform.fieldStockMovtionDetail.colIndex==7){
			 stockLocationCode='EXPRESS';
			 stockLocationName='快递中转库区库存';
		 }else if(platform.fieldStockMovtionDetail.colIndex==8){
			 stockLocationCode='EXPRESS_DELIVER';
			 stockLocationName='快递派送库区库存';
		 }else{
			 stockLocationCode='LOADED';
			 stockLocationName='已装车';
		 }


	        //去获取外场编码名称
	        Ext.Ajax.request({
				url : platform.realPath('queryTfrNameByCode.action'),
				params: {
						'fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgCode':currentOrgCode
					},
				success : function(response) {
					var json = Ext.decode(response.responseText);
				    var transferCenterName  =json.fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgName;//外场名称
					platform.fieldStockMovtionDetail.transferCenterCode=json.fieldStockMovtionVo.queryConditionStockMovtionDto.origOrgCode;//外场code					 
					Ext.getCmp('Foss.platform.fieldStockMovtionDetail.transferCenterCode.id').setReadOnly(true);
					Ext.getCmp('Foss.platform.fieldStockMovtionDetail.transferCenterCode.id').setValue(transferCenterName);
					platform.fieldStockMovtionDetail.transferCenterName=transferCenterName;
					 
					var locationArray = new Array();
					locationArray.push({valueCode : stockLocationCode,valueName: stockLocationName});
				    var locationType=Ext.getCmp('Foss.platform.fieldStockMovtionDetail.stockStatusLoction.id');
				    locationType.store.loadData(locationArray);
				    locationType.setValue(locationType.store.getAt(0));
                    
			         Ext.create('Ext.panel.Panel', {
			        	id : 'T_platform-fieldStockMovtionDetailIndex_content',
			        	cls : "panelContentNToolbar",
			        	bodyCls : 'panelContent-body',
			        	layout : 'auto',
			        	items : [platform.fieldStockMovtionDetail.fieldStockMovtionDetailForm,platform.fieldStockMovtionDetail.queryResult],
			        	renderTo : 'T_platform-fieldStockMovtionDetailIndex-body'
	                 });
	                 //加载数据
	                platform.fieldStockMovtionDetail.pagingBar.moveFirst();
 
			 
				},
				exception : function(response) {
				   	 var result = Ext.decode(response.responseText);
					  Ext.MessageBox.alert('提示',result.message);
					
				}
			});


			
			
		});


 