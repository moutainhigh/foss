//定义运单表格信息model
 Ext.define('Foss.predeliver.printArriveSheet.Waybill', {
     extend: 'Ext.data.Model',
     fields: [
          	{name: 'id',  type: 'string'},
            {name: 'waybillNo', type: 'string'},  				//运单号
          	{name: 'receiveCustomerName',  type: 'string'},  	//收货客户姓名
          	{name: 'receiveCustomerMobilephone',  type: 'string'},  //收货客户手机
          	{name: 'goodsName',  type: 'string'},				//货物名称
          	{name: 'goodsQtyTotal',  type: 'int'},  			//货物开单件数
          	{name: 'receiveCustomerPhone',  type: 'string'},  	//收货客户固定电话
          	{name: 'receiveCustomerAddress',  type: 'string'},	//收货具体地址
          	{name: 'arriveNotoutGoodsQty',  type: 'int'}, 		//到达未出库件数
          	{name: 'destOrgCode',  type: 'string'}, 			//车辆到达部门ID
          	{name: 'returnBillType',  type: 'string'},  		//返单类型
          	{name: 'waybillrfcStatus', type: 'string'},  		//是否含未处理更改单
          	{name: 'targetOrgCode', type: 'string'},  			//目的站
          	{name: 'arriveGoodsQty', type: 'int'}, 				//到达件数
          	{name: 'isPrinted', type: 'string'},				//是否打印
          	{name: 'paidMethod'},								//付款方式
          	{name: 'isPay',type: 'string'}						//是否已付款
     ]
 });
 
//定义运单基本信息model
 Ext.define('Foss.predeliver.printArriveSheet.WaybillEntity', {
     extend: 'Ext.data.Model',
     fields: [
              {name: 'arriveType'},				//到达类型
              {name: 'billWeight'},				//计费重量
              {name: 'billingType'},			//运费计费类型
              {name: 'codAmount'},				//代收货款
              {name: 'codFee'},					//代收货款手续费
              {name: 'createOrgCode'},			//开单组织
              {name: 'createUserCode'},			//开单人
              {name: 'targetOrgCode'},			//目的站
              {name: 'receiveCustomerCityCode'},//始发站
              {name: 'customerPickupOrgCode'},	//提货网点
              {name: 'goodsName'},				//货物名称
              {name: 'goodsPackage'},			//包装
              {name: 'goodsQtyTotal'},			//开单件数
              {name: 'goodsVolumeTotal'},		//体积
              {name: 'goodsWeightTotal'},		//重量
              {name: 'insuranceAmount'},		//保价声明值
              {name: 'lastLoadOrgCode'},		//运单配载部门
              {name: 'paidMethod'},				//付款方式
              {name: 'preArriveTime',type: 'date',convert : dateConvert},			//预计到达时间
              {name: 'prePayAmount'},			//现付总计
              {name: 'productCode'},			//精准卡航
              {name: 'receiveCustomerAddress'},		//收货具体地址
              {name: 'receiveCustomerMobilephone'},	//手机/固定电话号码
              {name: 'receiveCustomerName'},		//收货人姓名
              {name: 'receiveCustomerPhone'},		//收货客户固定电话
              {name: 'receiveMethod'},				//提货方式
              {name: 'receiveOrgCode'},				//发货网点
              {name: 'returnBillType'},				//是否含签收单
              {name: 'toPayAmount'},				//到付总计
              {name: 'totalFee'},					//费用总计
              {name: 'transportFee'},				//运费
              {name: 'unitPrice'},					//费率
              {name: 'waybillNo'},					//运单号
              {name: 'billTime',type: 'date',convert : dateConvert},		//开单时间
              {name: 'currencyCode'},				//币种
              {name: 'transportationRemark'},		//储运事项
              {name: 'transportType'},				//运输类型
              {name: 'receiveDepTelephone'},		//发货网点电话
              {name: 'customerPickupOrgCode'},		//提货网点电话
          	  {name: 'waybillChargeDtl',type: 'object'},
          	  {name: 'waybillDisDtl',type: 'object'}
     ]
 });
 
//定义运单费用信息model
 Ext.define('Foss.predeliver.printArriveSheet.WaybillChargeDtlEntity', {
	 extend: 'Ext.data.Model',
	 fields: [
	          {name: 'pricingEntryCode'},		//服务名称
	          {name: 'amount'},					//费用
	          {name: 'currencyCode'},			//币种
	          {name: 'rowNum'}			//序列
	          ]
 });
 
//定义到达联数据store
 Ext.define('Foss.predeliver.printArriveSheet.ArriveStore', {
 	 extend: 'Ext.data.Store',
      model: 'Foss.predeliver.printArriveSheet.Waybill',
      proxy: {
    	//代理的类型为ajax代理
  		type: 'ajax',
  		//提交方式
  		actionMethods:'POST',
  		url: predeliver.realPath('queryArriveSheetByWaybill.action'),
  		//定义一个读取器
  		reader: {
  			//以JSON的方式读取
  			type: 'json',
  			//定义读取JSON数据的根对象
  			root: 'vo.arriveSheetWaybillList'
  		}
      },
      listeners: {
    		//查询事件
    			beforeload : function(s, operation, eOpts) {
    				//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
    				var serachParms = Ext.getCmp('T_predeliver-printArriveSheetIndex_content').getQueryForm().getValues();
    				Ext.apply(operation, {
    					params:{
      						'vo.arriveSheetWaybillDto.waybillNo':serachParms.waybillNo,
      						'vo.arriveSheetWaybillDto.handoverNo':serachParms.handoverNo,
      						'vo.arriveSheetWaybillDto.receiveMethod':serachParms.receiveMethod,
      						'vo.arriveSheetWaybillDto.receiveCustomerName':serachParms.receiveCustomerName,
      						'vo.arriveSheetWaybillDto.receiveCustomerMobilephone':serachParms.receiveCustomerMobilephone,
      						'vo.arriveSheetWaybillDto.deliverbillId':serachParms.deliverbillId,
      						'vo.arriveSheetWaybillDto.paidMethod':serachParms.paidMethod
        				}
    				});	
    			}
    		}
  });

//查询条件
Ext.define('Foss.predeliver.printArriveSheet.queryForm', {
    extend: 'Ext.form.Panel',
    layout: 'column',
    frame: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
    defaults: {
    	xtype: 'textfield',
		margin:'5 10 5 10',
		labelWidth:120
	},
   // bodyPadding: 10,
    title: '查询',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    fieldLabel: '运单号',
                    columnWidth: 0.3,
                    name: 'waybillNo'
                },
                {
                    fieldLabel: '交接单号',
                    columnWidth: 0.3,
                    name: 'handoverNo'
                },
                {
                    xtype: 'combobox',
                    name: 'receiveMethod',
                    columnWidth: 0.3,
                    fieldLabel: '提货方式',
                    value: '',
                    queryModel:'local',
					displayField:'valueName',
					valueField:'valueCode',
					editable:false,
                    store: FossDataDictionary.getDataDictionaryStore('PICKUPGOODSHIGHWAYS',null,{
					 'valueCode': '',
               		 'valueName': '全部'
					})
                },
                {
                    fieldLabel: '收货人姓名',
                    columnWidth: 0.3,
                    name: 'receiveCustomerName'
                },
                {
                    fieldLabel: '手机/固定电话号码',
                    columnWidth: 0.3,
                    name: 'receiveCustomerMobilephone'
                },
                {
                    fieldLabel: '预派送单号',
                	columnWidth: 0.3,
                    name: 'deliverbillId'
                },{
                	xtype: 'combobox',
                    name: 'paidMethod',
                    columnWidth: 0.3,
                    fieldLabel: '支付方式',
                    value: '',
                    queryModel:'local',
					displayField:'valueName',
					valueField:'valueCode',
					editable:false,
                    store: FossDataDictionary.getDataDictionaryStore('SETTLEMENT__PAYMENT_TYPE',null,{
					 'valueCode': '',
               		 'valueName': '全部'
					})
                },
                {
        			border: 1,
        			xtype:'container',
        			columnWidth:1,
        			defaultType:'button',
        			layout:'column',
        			items:[{
        				text:'重置',
        				columnWidth:.08,
        				handler:function(){
        					var myform = this.up('form');
        					myform.getForm().reset(); 
        				}
        		},{
        			xtype: 'container',
        			border : false,
        			columnWidth:.84,
        			html: '&nbsp;'
        		},{
        			text: '查询',
        			cls:'yellow_button',
        			columnWidth:.08,
        			handler:function(){
        				var serachParms = this.up('form').getForm().getValues();
        				var resultGridStore = Ext.getCmp('T_predeliver-printArriveSheetIndex_content').getResultGrid().store;
        				if(Ext.isEmpty(serachParms.waybillNo)
        					&& Ext.isEmpty(serachParms.handoverNo)
        					&& Ext.isEmpty(serachParms.receiveCustomerName)
        					&& Ext.isEmpty(serachParms.receiveCustomerMobilephone)
        					&& Ext.isEmpty(serachParms.deliverbillId)){
        					Ext.ux.Toast.msg("提示信息","请输入任意一个条件查询！");
        					return;
        				}
        				resultGridStore.load();
        			}
        		}]
        	}]
        });

        me.callParent(arguments);
    }

});

//显示结果运单详细信息
Ext.define('Foss.predeliver.printArriveSheet.waybillInfo', {
    extend: 'Ext.form.Panel',
    //frame: true,
	defaults: {
		xtype: 'textfield',
		margin:'5 5 5 5',
		labelWidth:60,
		readOnly : true
	},
	//自动收缩高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	layout:'column',
	items: [
            {
                fieldLabel: '始发站',
                columnWidth: .25,
                name: 'receiveCustomerCityCode'
            },
            {
                fieldLabel: '目的站',
                columnWidth: .25,
                name: 'targetOrgCode'
            },
            {
                fieldLabel: '运输类型',
                columnWidth: .25,
                name: 'transportType'
            },
            {
                fieldLabel: '收货人',
                columnWidth: .25,
                name: 'receiveCustomerName'
            },
            {
                fieldLabel: '电话',
                columnWidth: .25,
                name: 'receiveCustomerMobilephone'
            },
            {
                fieldLabel: '地址',
                columnWidth: .25,
                name: 'receiveCustomerAddress'
            },
            {
                fieldLabel: '件数',
                columnWidth: .25,
                name: 'goodsQtyTotal'
            },
            {
                fieldLabel: '包装',
                columnWidth: .25,
                name: 'goodsPackage'
            },
            {
                fieldLabel: '体积',
                columnWidth: .25,
                name: 'goodsVolumeTotal'
            },
            {
                fieldLabel: '重量',
                columnWidth: .25,
                name: 'goodsWeightTotal'
            },
            {
                fieldLabel: '运费',
                columnWidth: .25,
                name: 'transportFee'
            },
            {
                fieldLabel: '费率',
                columnWidth: .25,
                name: 'unitPrice'
            },
            {
                fieldLabel: '现付总计',
                columnWidth: .25,
                name: 'prePayAmount'
            },
            {
                fieldLabel: '到付总计',
                columnWidth: .25,
                name: 'toPayAmount'
            },
            {
                fieldLabel: '交货方式',
                columnWidth: .25,
                name: 'receiveMethod'
            },
            {
                fieldLabel: '付款方式',
                columnWidth: .25,
                name: 'paidMethod',
            	renderer:function(value){
            		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,'SETTLEMENT__PAYMENT_TYPE');
            		return displayField;
        		}
            },
            {
                fieldLabel: '贷收货款',
                columnWidth: .25,
                name: 'codAmount'
            },
            {
                fieldLabel: '航班/日期',
                columnWidth: .4,
                name: '',
                labelWidth:65
            },
            {
                fieldLabel: '预计到达时间',
                columnWidth: .35,
                name: 'preArriveTime',
                xtype: 'datefield',
				format : 'Y-m-d H:i:s',
                labelWidth:85
            },
            {
                fieldLabel: '发货网点',
                columnWidth: .5,
                name: 'receiveOrgCode'
            },
            {
                fieldLabel: '电话',
                columnWidth: .25,
                name: 'receiveDepTelephone'
            },	
            {
                fieldLabel: '提货网点',
                columnWidth: .5,
                name: 'customerPickupOrgCode'
            },
            {
                fieldLabel: '电话',
                columnWidth: .25,
                name: 'customerPickup\DepTelephone'
            },
            {
                fieldLabel: '制单人/时间（发货网点）',
                columnWidth: .3,
                name: 'createUserCode',
                labelWidth:150
            },
            {
                columnWidth:.2,
                name: 'billTime',
                labelWidth:150,
                xtype: 'datefield',
				format : 'Y-m-d H:i:s'
            },
            {
                columnWidth:.5,
                name: 'createOrgCode',
                labelWidth:150
            },
            {
                fieldLabel: '制单人/时间（提货网点）',
                columnWidth: 1,
                name: '',
                labelWidth:150
            },
            {
            	xtype: 'textareafield',
                fieldLabel: '其他储运事项',
                columnWidth: .6,
                name: 'transportationRemark',
                labelWidth:150
            }
            
        ]
});

//显示结果右上form
Ext.define('Foss.predeliver.printArriveSheet.rightGoodsNamePanel', {
    extend: 'Ext.form.Panel',
    //frame: true,
	defaults: {
		margin:'5 10 5 10',
		labelWidth:100,
		readOnly : true
	},
	defaultType : 'textfield',
	//自动收缩高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	layout:'column',
	items: [
			{
			    xtype: 'textfield',
			    fieldLabel: '货品名称',
			    name:'goodsName'
			},
			{
			    xtype: 'textfield',
			    fieldLabel: '保价声明值',
			    name:'insuranceAmount'
			},
			{
			    xtype: 'textfield',
			    fieldLabel: '到货类型',
			    name:'arriveType'
			}
        ]
});


//显示结果右下grid
Ext.define('Foss.predeliver.printArriveSheet.rightDownGrid', {
    extend: 'Ext.grid.Panel',
    //frame: true,
	defaults: {
		//margin:'5 10 5 10',
		labelWidth:60,
		readOnly : true
	},
	store: Ext.create('Ext.data.Store', {
	    fields:['pricingEntryCode', 'amount', 'currencyCode','rowNum'],
	    proxy: {
	        type: 'memory',
	        reader: {
	            type: 'json'
	        }
	    }
	}),
	//自动收缩高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	columns: [
	          
				{ xtype: 'rownumberer',text: '序号', align: 'center',width:60},
				{ header: '服务名称', align: 'center', dataIndex: 'pricingEntryCode',width:95},
				{ header: '费用', align: 'center', dataIndex: 'amount',width:80}
          ],
          
  constructor: function(config){
	var me = this,
		cfg = Ext.apply({}, config);
	me.dockedItems = [{
		xtype: 'toolbar',
		dock: 'bottom',
		layout:'column',
		defaults:{
			margin:'5 5 5 5',
			readOnly : true
		},		
		items: [{
			xtype: 'textfield',
        	name: 'totalFee',
            columnWidth: .8,
            fieldLabel: '费用总计'
	    },{
			xtype: 'textfield',
        	name: 'billWeight',
            columnWidth: .8,
            fieldLabel: '计费重量'
	    },{
			xtype: 'textfield',
        	name: 'billingType',
            columnWidth: .8,
            fieldLabel: '计费方式'
	    }]
	}]
	
	me.callParent([cfg]);
  }
	
});

Ext.define('Foss.predeliver.printArriveSheet.waybillTotalPanels', {
    extend: 'Ext.panel.Panel',
    layout: {
        type: 'column'
    },
    waybillInfo: null,
	getWaybillInfo: function(){
		if(this.waybillInfo == null){
			this.waybillInfo = Ext.create("Foss.predeliver.printArriveSheet.waybillInfo",{
				columnWidth:.75
			});
		}
		return this.waybillInfo;
	},
	rightGoodsNamePanel: null,
	getRightGoodsNamePanel: function(){
		if(this.rightGoodsNamePanel == null){
			this.rightGoodsNamePanel = Ext.create("Foss.predeliver.printArriveSheet.rightGoodsNamePanel");
		}
		return this.rightGoodsNamePanel;
	},
	rightDownGrid: null,
	getRightDownGrid: function(){
		if(this.rightDownGrid == null){
			this.rightDownGrid = Ext.create("Foss.predeliver.printArriveSheet.rightDownGrid");
		}
		return this.rightDownGrid;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
           me.items = [
                me.getWaybillInfo(),
                {
                border: 1,
       			xtype:'container',
       			columnWidth:.238,
       			items : [
       			         me.getRightGoodsNamePanel(),me.getRightDownGrid()
       			]}
               ];
           me.callParent([cfg]);
    },
    bindData : function(record, grid, rowBodyElement){
    	// 绑定表格数据到表单上
		Ext.Ajax.request({
			url: predeliver.realPath('queryWaybillInfoByWaybillNo.action'),
			params: {
				'vo.arriveSheetWaybillDto.waybillNo': record.get('waybillNo'),
				'vo.arriveSheetWaybillDto.arriveNotoutGoodsQty': record.get('arriveNotoutGoodsQty')
			},
			success: function(response){
				var result = Ext.decode(response.responseText);
				var waybillEntity = Ext.ModelManager.create(result.vo.arriveSheetWaybillAddPropertyDto,'Foss.predeliver.printArriveSheet.WaybillEntity');
				rowBodyElement.getWaybillInfo().loadRecord(waybillEntity);
				rowBodyElement.getRightGoodsNamePanel().loadRecord(waybillEntity);
				rowBodyElement.getRightDownGrid().getStore().loadData(result.vo.waybillDto.waybillChargeDtlEntity);
				var items = rowBodyElement.getRightDownGrid().getDockedItems('toolbar[dock="bottom"]');
				items[0].getComponent(0).setValue(waybillEntity.data.totalFee);
				items[0].getComponent(1).setValue(waybillEntity.data.billWeight);
				items[0].getComponent(2).setValue(waybillEntity.data.billingType);
				/*items[0].getComponent(3).setValue(waybillEntity.data.currencyCode);*/
			}
		});
    	//rowBodyElement.getWaybillInfo().loadRecord(record);
    }
});


//查询结果显示grid
Ext.define('Foss.predeliver.printArriveSheet.resultGrid', {
    extend: 'Ext.grid.Panel',
    title: '到达联信息',
    frame: true,
    //收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	emptyText: "查询结果为空",
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns: [
	          { header: '',  align: 'center', dataIndex: '', flex: 1,
	        	  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
	        		  var targetOrgCode = record.data["targetOrgCode"];
	        		  //获取当前用户设置的当前部门编码
	        		  var currentDeptCode = FossUserContext.getCurrentDeptCode();
	        		  var result = '';
	        		  //非到达部门库存用黄旗表示
	        		  if(targetOrgCode !== currentDeptCode){
	        			  result = '<div class="foss_icons_pkp_flagyellow"></div>'+result;
	        		  }
	        		  //判断是否含签收单用绿旗表示  NO_RETURN_BILL == 无需返单
	        		  if(record.data.returnBillType=='NO_RETURN_BILL' || record.data.returnBillType==''){
	        			  result = '<div class="foss_icons_pkp_flaggreen"></div>'+result;
	        		  }
	        		  //判断是否含有未受理更改单  001 状态为待审核  002 ：待受理  红旗表示
	        		  if(!Ext.isEmpty(record.data.waybillrfcStatus)){
	        			  result = '<div class="foss_icons_pkp_flagred"></div>'+result;
	        		  }
	        		  //判断是否打印
	        		  if(record.data.isPrinted=='Y'){
	        			  result = '<div class="foss_icons_pkp_yes"></div>'+result;
	        		  }
	        		  return result;
	        	  }
	          },
	          { header: '运单号', align: 'center', dataIndex: 'waybillNo', flex: 1},
	          { header: '收货人', align: 'center', dataIndex: 'receiveCustomerName', flex: 1 },
	          { header: '电话', align: 'center', dataIndex: 'receiveCustomerMobilephone', flex: 1 },
	          { header: '货物名称', align: 'center', dataIndex: 'goodsName', flex: 1 },
	          { header: '件数', align: 'center', dataIndex: 'arriveNotoutGoodsQty', flex: 1},
	          { header: '地址', align: 'center', dataIndex: 'receiveCustomerAddress', flex: 1 }
	      ],
	     
		//表格行可展开的插件
        plugins: [{
        	header:true,
			//定义行可展开的插件ID
			pluginId: 'rowexpander_plugin_id',
			//定义插件的类型
            ptype: 'rowexpander',
			//定义行展开模式（单行与多行），默认是多行展开(值true)
			rowsExpander: false,
			//行体内容
			rowBodyElement : 'Foss.predeliver.printArriveSheet.waybillTotalPanels'
        }],
        //给表格行涂层
        viewConfig: {
            stripeRows: false,
            enableTextSelection: true,
    		getRowClass: function(record, rowIndex, rp, ds) {
    			var goodsQtyTotal = record.get('goodsQtyTotal');
    			var arriveGoodsQty = record.get('arriveGoodsQty');
    			if (parseInt(goodsQtyTotal) != parseInt(arriveGoodsQty)) {
    				return 'predeliver-printArriveSheetIndex-row-B4E0FD';
    			} 
    		}
    	},
    	printWindow: null,
    	getPrintWindow: function(){
    		var me = this;
    		if(this.printWindow==null){
    			me.printWindow = Ext.create('Foss.printArriveSheet.printWindow',me);
    		}
    		return me.printWindow;
    	},
	    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.printArriveSheet.ArriveStore');
		Ext.MessageBox.buttonText.yes = "确定";  
		Ext.MessageBox.buttonText.no = "取消";
		me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'top',
				layout:'column',
				defaults:{
					margin:'0 0 5 3'
				},		
				items: [{
					xtype: 'button',
			        text: '预览',
			        handler:function(){
			    		//alert("打印");
			    		var mygrid = this.up('gridpanel');
			    		var selectWaybill = mygrid.getSelectionModel().getSelection();
			    		if(selectWaybill.length==0){
			    			Ext.ux.Toast.msg("提示信息","请选择行！");
			    			return;
			    		}
			    		for(var i = 0;i<selectWaybill.length;i++){
		    				var waybillrfcStatus = selectWaybill[i].data.waybillrfcStatus;
		    				var paidMethod = selectWaybill[i].data.paidMethod;
		    				if(waybillrfcStatus == '002'){
		    					Ext.ux.Toast.msg("提示信息",selectWaybill[i].data.waybillNo+"运单有未受理更改单，不能打印！");
		    					return;
		    					break;
		    				}
		    				//临时欠款
		    				if(paidMethod == 'DT'){
		    					Ext.ux.Toast.msg("提示信息",selectWaybill[i].data.waybillNo+"运单为临欠，还未付款，不能打印到达联！");
		    					return;
		    					break;
		    				}
		    			}
			    		mygrid.getPrintWindow().show();
			    		
					}
			    },{
			    	xtype: 'button',
			        text: '打印',
			        margin:'0 10 5 10',
			    	handler:function(){
			    		//alert("打印");
			    		var mygrid = this.up('gridpanel');
			    		var selectWaybill = mygrid.getSelectionModel().getSelection();
			    		if(selectWaybill.length==0){
			    			Ext.ux.Toast.msg("提示信息","请选择打印行！");
			    			return;
			    		}
			    		for(var i = 0;i<selectWaybill.length;i++){
		    				var waybillrfcStatus = selectWaybill[i].data.waybillrfcStatus;
		    				var paidMethod = selectWaybill[i].data.paidMethod;
		    				if(waybillrfcStatus == '002'){
		    					Ext.ux.Toast.msg("提示信息",selectWaybill[i].data.waybillNo+"运单有未受理更改单，不能打印！");
		    					return;
		    					break;
		    				}
		    				//临时欠款
		    				if(paidMethod == 'DT'){
		    					Ext.ux.Toast.msg("提示信息",selectWaybill[i].data.waybillNo+"运单为临欠，还未付款，不能打印到达联！");
		    					return;
		    					break;
		    				}
		    			}
			    		mygrid.getPrintWindow().show();
			    		
					}
			    },{
                	xtype: 'image',
                	imgCls: 'foss_icons_pkp_purplebg'
                },{
                	xtype: 'label',
                    margin:'0 30 0 10',
                    text: '库存与开单不一致'
                },{
	            	xtype: 'image',
	            	imgCls: 'foss_icons_pkp_flagyellow'
                },{
                	 xtype: 'label',
                     margin:'0 30 0 10',
                     text: '非到达部门库存'
                },{
                	xtype: 'image',
                	imgCls: 'foss_icons_pkp_flagred'
                },{
                	 xtype: 'label',
                     margin:'0 30 0 10',
                     text: '含未受理更改'
                },{
                	 xtype: 'image',
                	 imgCls: 'foss_icons_pkp_flaggreen'
                },{
                	 xtype: 'label',
                     margin:'0 30 0 10',
                     text: '含签收单'
                },{
                	 xtype: 'image',
                	 imgCls: 'foss_icons_pkp_yes'
                },{
                	 xtype: 'label',
                     margin:'0 30 0 10',
                     text: '已打印'
                }]
		}]
		me.callParent([cfg]);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
    var queryForm = Ext.create("Foss.predeliver.printArriveSheet.queryForm");
    //var warnPanel = Ext.create("Foss.predeliver.printArriveSheet.warn");
    var resultGrid = Ext.create("Foss.predeliver.printArriveSheet.resultGrid");

	Ext.create('Ext.panel.Panel',{
		id: 'T_predeliver-printArriveSheetIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body',
		layout:'auto',
		getQueryForm: function(){
			return queryForm;
		},
		getResultGrid: function(){
			return resultGrid;
		},
		items: [queryForm,resultGrid],
		renderTo: 'T_predeliver-printArriveSheetIndex-body'
	});
});
