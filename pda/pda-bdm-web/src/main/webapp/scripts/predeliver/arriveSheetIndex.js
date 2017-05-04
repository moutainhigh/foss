//定义状态model
 Ext.define('Foss.predeliver.arriveSheet.ArriveSheet', {
     extend: 'Ext.data.Model',
     idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
     idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
     fields: [
            {name: ' extid ', type: 'string'},//额外的用于生成的EXT使用的列
          	{name: 'id',  type: 'string'},
            {name: 'waybillNo', type: 'string'},
            {name: 'arrivesheetNo',  type: 'string'},
          	{name: 'printtimes',  type: 'int'},
          	{name: 'status',  type: 'string'},
          	{name: 'arriveSheetGoodsQty',  type: 'int'},
          	{name: 'goodsName',  type: 'string'},
          	{name: 'goodsQtyTotal',  type: 'int'},
          	{name: 'arrangeGoodsGty',  type: 'int'},
          	{name: 'arriveNotoutGoodsQty',  type: 'int'},
          	{name: 'stockGoodsQty',  type: 'string'},
          	{name: 'receiveCustomerName',  type: 'string'},
          	{name: 'receiveCustomerMobilephone',  type: 'string'},
          	{name: 'destroyed',  type: 'string'}
     ]
 });
 
//定义到达联数据store
 Ext.define('Foss.predeliver.arriveSheet.ArriveStore', {
 	 extend: 'Ext.data.Store',
      model: 'Foss.predeliver.arriveSheet.ArriveSheet',
      pageSize: 10,
      proxy: {
    	//代理的类型为内存代理
  		type: 'ajax',
  		//提交方式
  		actionMethods:'POST',
  		url:predeliver.realPath('queryArriveSheet.action'),
  		//定义一个读取器
  		reader: {
  			//以JSON的方式读取
  			type: 'json',
  			//定义读取JSON数据的根对象f
  			root: 'vo.arriveDtoList',
  			//返回总数
			totalProperty : 'totalCount'
  		}
      },//事件监听
  	listeners: {
  		//查询事件
  			beforeload : function(s, operation, eOpts) {
  				//执行查询，首先货物查询条件，为全局变量，在查询条件的FORM创建时生成
  				var queryParams = Ext.getCmp('T_predeliver-arriveSheetIndex_content').getQueryForm().getValues();
  				var waybillNo = queryParams.waybillNo;
  				Ext.apply(operation, {
  					params:{
  						'vo.arriveSheetDto.waybillNo':queryParams.waybillNo,
  						'vo.arriveSheetDto.arrivesheetNo':queryParams.arrivesheetNo,
  						'vo.arriveSheetDto.status':queryParams.status,
  						'vo.arriveSheetDto.isPrinted':queryParams.isPrinted,
  						'vo.arriveSheetDto.goodsName':queryParams.goodsName,
  						'vo.arriveSheetDto.receiveCustomerName':queryParams.receiveCustomerName,
  						'vo.arriveSheetDto.receiveCustomerMobilephone':queryParams.receiveCustomerMobilephone,
  						'vo.arriveSheetDto.createUserName':queryParams.createUserName,
  						'vo.arriveSheetDto.createBeginTime':queryParams.createBeginTime,
  						'vo.arriveSheetDto.createEndTime':queryParams.createEndTime,
  						'vo.arriveSheetDto.destroyed':queryParams.destroyed
  						}
  				});	
  			}
  		}
     // autoLoad: true
  });

 //查询条件
Ext.define('Foss.predeliver.arriveSheet.queryForm', {
    extend: 'Ext.form.Panel',
    frame: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
    layout: {
        type: 'column'
    },
    bodyPadding: 10,
    title: '查询条件',
    defaults: {
		margin:'5 10 5 10',
		labelWidth:100
	},
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textfield',
                    name: 'waybillNo',
                    columnWidth: 0.3,
                    fieldLabel: '运单号'
                },
                {
                    xtype: 'textfield',
                    name: 'arrivesheetNo', 
                    columnWidth: 0.3,
                    fieldLabel: '到达联编号'
                },{
                    xtype: 'combobox',
                    name: 'status',
                    columnWidth: 0.3,
                    fieldLabel: '到达联状态',
                    value: 'NEW',
                    queryModel:'local',
					displayField:'valueName',
					valueField:'valueCode',
					editable:false,
                    store: FossDataDictionary.getDataDictionaryStore('PKP_ARRIVESHEET_STATUS',null,{
					 'valueCode': '',
               		 'valueName': '全部'
				})
                },{
                    xtype: 'checkboxfield',
                    name: 'isPrinted',
                    inputValue: 'Y',
                    //uncheckedValue: 'N',
                    columnWidth: 0.1,
                    boxLabel: '是否打印'
                },{
                	xtype: 'textfield',
                	name: 'goodsName',
                    columnWidth: 0.3,
                    fieldLabel: '货物名称'
                },{
                	xtype: 'textfield',
                	name: 'receiveCustomerName',
                    columnWidth: 0.3,
                    fieldLabel: '收货人/公司'
                },{
                	xtype: 'textfield',
                	name: 'receiveCustomerMobilephone',
                    columnWidth: 0.3,
                    fieldLabel: '收货人手机'
                },{
                    xtype: 'checkboxfield',
                    name: 'destroyed',
                    inputValue: 'N',
                    //uncheckedValue: 'Y',
                    columnWidth: 0.1,
                    boxLabel: '是否有效',
                    checked: true
                },{
	                xtype: 'rangeDateField',
	        		fieldLabel: '到达联生成时间',
	        		dateType: 'datefield',
	        		fromName: 'createBeginTime',
	        		toName: 'createEndTime',
	        		fromValue: Ext.Date.format(new Date(),'Y-m-d'),
	        		toValue: Ext.Date.format(new Date(),'Y-m-d'),
	        		columnWidth: .6
                },{
        			xtype: 'textfield',
        			name: 'createUserName',
                    columnWidth: .3,
                    fieldLabel: '创建人'
        		},{
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
        				var form = this.up('form').getForm();
        				var arriveValue = form.getValues();
        				/*Ext.Ajax.request({
        				    url: '../predeliver/queryArriveSheet.action',
        				    jsonData:{"vo":{"arriveSheetDto":arriveValue}},
    				    	params: {
    				    		'vo.waybillNo': arriveValue
    					    },
        				    success: function(response){
        				        var text = response.responseText;
        				        var result = Ext.decode(response.responseText);
        						var arriveGridStore = Ext.getCmp('Foss_predeliver_ArriveSheet_GridPanel_Id').store;
        						arriveGridStore.loadData(result.vo.arriveDtoList);
        				    }
        				});
        				var arriveGridStore = Ext.getCmp('Foss_predeliver_ArriveSheet_GridPanel_Id').store;
        				arriveGridStore.load({
        					jsonData:{"vo":{"arriveSheetDto":arriveValue}}
        				});*/
        				Ext.getCmp('T_predeliver-arriveSheetIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
        			}
        		}]
        	}
            ]
        });

        me.callParent(arguments);
    }
});

Ext.define('Foss.predeliver.arriveSheet.warn', {
    extend: 'Ext.panel.Panel',
    cls:'autoHeight',
	bodyCls:'autoHeight',
	 layout:  'column',
	// hidden: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'label',
                    margin:'0 0 0 10',
                    text: '排单、装车、库存、到达联件数不一致：'
                },
                {
                    xtype: 'image',
                    src: '../images/predeliver/Pink.jpg', width: 116, height: 18
                }
            ]
        });

        me.callParent(arguments);
    }

});
Ext.define('Foss.predeliver.arriveSheet.editPanel',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaults: {
		margin:'5 10 5 10',
		xtype : 'textfield',
		labelWidth:90
	},
	items: [
			{
				fieldLabel:'到达联件数',
				columnWidth: 1,
				name: 'arriveSheetGoodsQty',
				enableKeyEvents: true,
                listeners : {
                 	keyup:function(e, t, eOpts ){
                 		var nowValue = e.getValue();
                 		var arriveNotoutGoodsQty = predeliver.arriveSheet.editPanel.getForm().getRecord().data.arriveNotoutGoodsQty;
                 		var arriveSheetGoodsQty = predeliver.arriveSheet.editPanel.getForm().getRecord().data.arriveSheetGoodsQty;
                 		if(isNaN(nowValue)){
                 			e.setValue(parseInt(arriveSheetGoodsQty));
                 		}
                 		if(parseInt(nowValue) > parseInt(arriveNotoutGoodsQty)){
                 			e.setValue(parseInt(arriveSheetGoodsQty));
                 		}
                 	}
                 }
			},
			{
				fieldLabel:'id',
				columnWidth: 1,
				name: 'id',
				hidden:true
			},
			{
				fieldLabel:'运单号',
				columnWidth: 1,
				name: 'waybillNo',
				hidden:true
			},
			{
				fieldLabel:'库存件数',
				columnWidth: 1,
				name: 'arriveNotoutGoodsQty',
				hidden:true
			},
			{
				xtype: 'button',
				text: '确认',
				columnWidth: .4,
				handler:function(){
					var win = this.up('window')
					var arriveValue = this.up('form').getForm().getValues();
					Ext.Ajax.request({
					    url: predeliver.realPath('modifyArriveSheet.action'),
					    jsonData:{"vo":{"arriveSheet":arriveValue}},
					    success: function(response){
					        var text = response.responseText;
					        var result = Ext.decode(response.responseText);
							var arriveGridStore = Ext.getCmp('Foss_predeliver_arriveSheet_GridPanel_Id').store;
							win.close();
							arriveGridStore.load();
					    }
					});
				
			}
			},{
				xtype: 'container',
				html: '&nbsp;',
				columnWidth: .2
			},{
				xtype: 'button',
				text: '取消',
				columnWidth: .4,
				handler:function(){
					this.up('window').close();
				}
			}]
});

predeliver.arriveSheet.editPanel = Ext.create('Foss.predeliver.arriveSheet.editPanel');
//编辑任务订单状态window
Ext.define('Foss.predeliver.arriveSheet.editWindow', {
		extend: 'Ext.window.Window',
		width: 250,
		height : 140,
		title: '修改到达联',
		layout:'column',
		border: false,
		//将window的关闭事件close 设成 hide
		closeAction : 'close',
		items: [predeliver.arriveSheet.editPanel]
	});

function lessCargoDetailsWin(){
	var arriveGrid = Ext.getCmp('T_predeliver-arriveSheetIndex_content').getArriveGrid();
	var selectRow = arriveGrid.getSelectionModel().getSelection();
	var arriveSheet = Ext.ModelManager.create(selectRow[0].data,'Foss.predeliver.arriveSheet.ArriveSheet');
	predeliver.arriveSheet.editPanel.loadRecord(arriveSheet);
	var win  = Ext.create('Foss.predeliver.arriveSheet.editWindow').show();
	
	/*Ext.Msg.prompt('修改到达联', '到达联件数:', function(btn, text){
		var me = this;
	    if (btn == 'ok'){
    		var id = selectRow[0].data.id;
    		var waybillNo = selectRow[0].data.waybillNo;
    		if(parseInt(text)> parseInt(arriveNotoutGoodsQty)){
    			Ext.Msg.alert("提示信息","修改失败，到达联件数不能大于库存件数！");
    			return;
    		}
	    	Ext.Ajax.request({
			    url: predeliver.realPath('modifyArriveSheet.action'),
		    	params: {
		    		'vo.arriveSheet.arriveSheetGoodsQty': text,
		    		'vo.arriveSheet.id': id,
		    		'vo.arriveSheet.waybillNo': waybillNo
			    },
			    success: function(response){
			        var text = response.responseText;
			        var result = Ext.decode(response.responseText);
					var arriveGridStore = Ext.getCmp('Foss_predeliver_arriveSheet_GridPanel_Id').store;
					arriveGridStore.load();
			    }
			});
	    }
	},this,false,arriveSheetGoodsQty);*/
}

Ext.define('Foss.predeliver.arriveSheet.GridPanel', {
    extend: 'Ext.grid.Panel',
    title: '到达联信息',
    frame: true,
    cls:'autoHeight',
	bodyCls:'autoHeight',
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
	emptyText: '查询结果为空',
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	columns: [
	          {xtype:'actioncolumn',
	  			width:80,
				text: '打印',
				align: 'center',
				items: [{
					iconCls: 'deppon_icons_print',
					tooltip: '打印',
					handler: function(grid, rowIndex, colIndex) {
						var selection = grid.getStore().getAt(rowIndex);
						var mygrid = this.up('gridpanel');
						mygrid.getSelectionModel().select(selection);
						mygrid.getPrintWindow().show();
						}
				}]},
	          { header: '运单号', align: 'center', dataIndex: 'waybillNo', flex: 1 },
	          { header: '到达联编号', align: 'center', dataIndex: 'arrivesheetNo', flex: 1 },
	          { header: '打印次数', align: 'center', dataIndex: 'printtimes', flex: 1 },
	          { header: '到达联状态', align: 'center', dataIndex: 'status', flex: 1,
		          renderer:function(value){
	    			return FossDataDictionary.rendererSubmitToDisplay(value,'PKP_ARRIVESHEET_STATUS');
				}
			  },
	          { header: '到达联件数', align: 'center', dataIndex: 'arriveSheetGoodsQty', flex: 1, 
	        	renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
	        		 var goodsQtyTotal = record.data["goodsQtyTotal"];
	        		 var status = record.data["status"];
	        		 var destroyed = record.data["destroyed"];
	        		 var arriveNotoutGoodsQty = record.data["arriveNotoutGoodsQty"];
	        		 if(status == 'SIGN' || destroyed == 'Y'){
	        			 return '<div style="float:left">'+value+'/'+goodsQtyTotal+'</div>'+
	     	  			'<div style="float:right">'+'修改'+'</a></div>';
	        		 }else{
	        			 return '<div style="float:left">'+value+'/'+goodsQtyTotal+'</div>'+
	     	  			'<div style="float:right"><a href="javascript:lessCargoDetailsWin();">'+'修改'+'</a></div>';
	        		 }
	  		}},
	          { header: '排单件数', align: 'center', dataIndex: 'arrangeGoodsGty', flex: 1 },
	          { header: '库存件数', align: 'center', dataIndex: 'arriveNotoutGoodsQty', flex: 1 },
	          { header: '货物名称', align: 'center', dataIndex: 'goodsName', flex: 1 },
	          { header: '收货人公司', align: 'center', dataIndex: 'receiveCustomerName', flex: 1 }
	      ],
	//给表格行涂层
    viewConfig: {
        stripeRows: false,
        enableTextSelection: true,
		getRowClass: function(record, rowIndex, rp, ds) {
			var arriveSheetGoodsQty = record.get('arriveSheetGoodsQty');
			var arriveNotoutGoodsQty = record.get('arriveNotoutGoodsQty');
			var arrangeGoodsGty = record.get('arrangeGoodsGty');
			if (parseInt(arriveSheetGoodsQty) != parseInt(arriveNotoutGoodsQty) != parseInt(arrangeGoodsGty)) {
				return 'predeliver-arriveSheetIndex-row-pink';
			} 
		}
	},
	printWindow: null,
	getPrintWindow: function(){
		var me = this;
		if(this.printWindow==null){
			me.printWindow = Ext.create('Foss.printArriveSheet.printOneWindow',me);
		}
		return me.printWindow;
	},
    pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store
  			});
  		}
  		return this.pagingToolbar;
  	},
	  constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.predeliver.arriveSheet.ArriveStore');
		Ext.MessageBox.buttonText.yes = "确定";  
		Ext.MessageBox.buttonText.no = "取消";
		me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'column',
				defaults:{
					margin:'0 0 5 3'
				},		
				items: [{
					xtype: 'button',
			        text: '批量打印',
			        handler:function(){
			        		var mygrid = this.up('gridpanel');
			        		var selectWaybill = mygrid.getSelectionModel().getSelection();
			        		var arriveSheetNos = '';
			        		if(selectWaybill.length==0){
			        			Ext.ux.Toast.msg("提示信息","请选择打印行！");
			        		}else{
			        			var mygrid = this.up('gridpanel');
			        			var arriveSheetNos = '';
			        			for(var i = 0;i<selectWaybill.length;i++){
			        				if(parseInt(selectWaybill[i].data.printtimes)>0 || selectWaybill[i].data.destroyed =='Y'){
			        					if(arriveSheetNos.length == 0){
			        						arriveSheetNos = selectWaybill[i].data.arrivesheetNo;
			        					}else{
			        						arriveSheetNos = arriveSheetNos+","+selectWaybill[i].data.arrivesheetNo;
			        					}
			        				}
			        			}
			        			if(arriveSheetNos.length>0){
				        			Ext.Msg.confirm( '提示信息', arriveSheetNos+'到达联已作废或已打印，确定要继续打印到达联', function(btn,text){
				        				if(btn=="yes"){
											mygrid.getPrintWindow().show();
				        				}
				        			});
			        			}
			        			//do_printpreview('arriveSheet1',{arriveSheetNos:arriveSheetNos});
			        		}
			        	}
			    },{
			    	xtype: 'button',
			        text: '作废',
			    	handler:function(){
						var selectRow = me.getSelectionModel().getSelection();
						if(selectRow.length==0){
							Ext.ux.Toast.msg("提示信息","请选中一行");
							return;
						}
						Ext.Msg.confirm( '提示信息', '确定要作废此到达联？', function(btn,text){
							if(btn=="yes"){
								var ids = '';
								var waybillNos = '';
								var arriveSheetGoodsQtys ='';
								for(var i = 0;i<selectRow.length;i++){
									var arriveSheetNos = selectRow[i].data.arrivesheetNo;
									if(selectRow[i].data.status == 'SIGN' || selectRow[i].data.status == 'DELIVER'){
										Ext.ux.Toast.msg("提示信息",arriveSheetNos+"到达联已签收或派送中，不能作废！",'error', 3000);
										return;
										break;
									}
									if(selectRow[i].data.destroyed == 'Y'){
										Ext.ux.Toast.msg("提示信息",arriveSheetNos+"到达联已作废，无需再作废",'error', 3000);
										return;
										break;
									}
									if(ids.length == 0) {
										ids = selectRow[i].data.id;
										waybillNos = selectRow[i].data.waybillNo;
										arriveSheetGoodsQtys = selectRow[i].data.arriveSheetGoodsQty;
									} else {
										ids = ids + "," + selectRow[i].data.id;
										waybillNos = waybillNos + "," + selectRow[i].data.waybillNo;
										arriveSheetGoodsQtys = arriveSheetGoodsQtys + "," + selectRow[i].data.arriveSheetGoodsQty;
									}
								}
								Ext.Ajax.request({
								    url: predeliver.realPath('cancelArriveSheet.action'),
								    params: {
								    	'vo.arriveSheet.id': ids,
								    	'vo.arriveSheet.waybillNo': waybillNos,
								    	'vo.arriveSheetGoodsQtys': arriveSheetGoodsQtys
								    },
								    success: function(response){
								    	var json = Ext.decode(response.responseText);
								    	Ext.ux.Toast.msg("提示信息",json.message);
								        me.store.load();
								    },
								    exception: function(response){
										var json = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg('提示信息', json.message, 'error', 3000);
									}
								});
							}
						});
					}
			    },{
			    	xtype: 'button',
			        text: '激活',
			        handler:function(){
						var selectRow = me.getSelectionModel().getSelection();
						if(selectRow.length==0){
							Ext.ux.Toast.msg("提示信息","请选中一行");
							return;
						}
						Ext.Msg.confirm( '提示信息', '确定要激活此到达联？', function(btn,text){
							var ids = '';
							var waybillNos = '';
							var arriveSheetGoodsQtys ='';
							if(btn=="yes"){
								var ids = '';
								for(var i = 0;i<selectRow.length;i++){
									var arriveSheetNos = selectRow[i].data.arrivesheetNo;
									if(selectRow[i].data.destroyed == 'N'){
										Ext.ux.Toast.msg("提示信息",arriveSheetNos+"到达联已是激活状态！",'error', 3000);
										return;
										break;
									}
									if(ids.length == 0) {
										ids = selectRow[i].data.id;
										waybillNos = selectRow[i].data.waybillNo;
										arriveSheetGoodsQtys = selectRow[i].data.arriveSheetGoodsQty;
									} else {
										ids = ids + "," + selectRow[i].data.id;
										waybillNos = waybillNos + "," + selectRow[i].data.waybillNo;
										arriveSheetGoodsQtys = arriveSheetGoodsQtys + "," + selectRow[i].data.arriveSheetGoodsQty;
									}
								}
								Ext.Ajax.request({
								    url: predeliver.realPath('activateArriveSheet.action'),
								    params: {
								    	'vo.arriveSheet.id': ids,
								    	'vo.arriveSheet.waybillNo': waybillNos,
								    	'vo.arriveSheetGoodsQtys': arriveSheetGoodsQtys
								    },
								    success: function(response){
								    	var json = Ext.decode(response.responseText);
								    	Ext.ux.Toast.msg("提示信息",json.message);
								        me.store.load();
								    },
								    exception: function(response){
										var json = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg('提示信息', json.message, 'error', 3000);
									}
								});
							}
						});
					}
			    }]
		}]
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
    var queryForm = Ext.create("Foss.predeliver.arriveSheet.queryForm");
    var warnPanel = Ext.create("Foss.predeliver.arriveSheet.warn");
    var arriveGrid = Ext.create("Foss.predeliver.arriveSheet.GridPanel",{
    	id: "Foss_predeliver_arriveSheet_GridPanel_Id"
    });
    
	Ext.create('Ext.panel.Panel',{
		id: 'T_predeliver-arriveSheetIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body',
		layout:'auto',
		getArriveGrid: function(){
			return arriveGrid;
		},
		getQueryForm: function(){
			return queryForm;
		},
		items: [queryForm,warnPanel,arriveGrid],
		renderTo: 'T_predeliver-arriveSheetIndex-body'
	});
});
