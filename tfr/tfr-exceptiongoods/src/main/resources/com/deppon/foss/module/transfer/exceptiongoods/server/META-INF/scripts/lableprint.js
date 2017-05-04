
//打印页面表单
Ext.define('Foss.load.lableprint.TextForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '',
	defaults: {
		margin: '15 5 5 5',
//		width:100,
	},
	items: [{
		xtype: 'numberfield',
		fieldLabel: '运单号',
//		fieldLabel: stock.prioritygoods.i18n('foss.stock.org'),
		name: 'waybill_no',
		allowNegative:false,
		allowDecimals:false,
		minLength:10,
		enableKeyEvents: true,
		hideTrigger: true,
		listeners: {
	        keydown: {
	            //element: 'el', //bind to the underlying el property on the panel
             	fn: function(b,e,eOpts){
	             	if(e.getKey() == 13){
	             		if(exceptiongoods.lablePrintIndex.textform.getValues().waybill_no == '' ||
								exceptiongoods.lablePrintIndex.textform.getValues().waybill_no == null){
	             				Ext.ux.Toast.msg('提示', '运单号不能为空', 'error', 1000);
								return;
						}
						if(exceptiongoods.lablePrintIndex.textform.getForm().findField('topping').getValue()){
								exceptiongoods.lablePrintIndex.print(1);
						}else{
								exceptiongoods.lablePrintIndex.print(0);
						}	
			           //   Ext.Msg.alert('提示','您已经按下了回车键');
	           		}
	            }
	        }
	    }
	},{
			xtype: 'container',
			columnWidth:0.1,
			html: '&nbsp;'
	},{
		 xtype: 'fieldcontainer',
		 fieldLabel: '',
            defaultType: 'checkboxfield',
            items: [
                {
                    boxLabel  : '非始发外场',
                    name      : 'topping',
                    inputValue: '1'
                }
            ]

	},{
			xtype: 'container',
			columnWidth:0.1,
			html: '&nbsp;'
	},{
		xtype: 'button',
		text: '确定',
			width:50,
//		text: stock.movegoods.i18n('foss.stock.search'),
		cls : 'yellow_button',
		handler: function() {
			if(exceptiongoods.lablePrintIndex.textform.getValues().waybill_no == '' ||
					exceptiongoods.lablePrintIndex.textform.getValues().waybill_no == null){
				Ext.ux.Toast.msg('提示', '运单号不能为空', 'error', 1000);
				return;
			}
			//如果勾选了  [非始发外场] 
			if(exceptiongoods.lablePrintIndex.textform.getForm().findField('topping').getValue()){
				exceptiongoods.lablePrintIndex.print(1);
			}else{
				exceptiongoods.lablePrintIndex.print(0);
			}	
		}
	}],
	
	constructor: function(config){
		var me = this,	
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
		
	}
});

//快递中转场标签打印查询条件表单
Ext.define('Foss.load.lableprint.QueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	title : '查询条件',
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'numberfield',
		fieldLabel: '运单号',
//		fieldLabel: stock.prioritygoods.i18n('foss.stock.org'),
		name: 'waybill_no',
		allowNegative:false,
		allowDecimals:false,
		hideTrigger: true,
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: '流水号',
		name: 'serial_no',
		columnWidth:.25
	},{
		xtype: 'commonemployeeselector',
		fieldLabel: '操作人',
		name: 'operate_code',
		columnWidth:.25
	},{
		 xtype: 'fieldcontainer',
		 columnWidth:.25,
     fieldLabel: '',
            defaultType: 'checkboxfield',
            items: [
                {
                    boxLabel  : '标签打印',
                    name      : 'isPrint',
                    inputValue: '1'
                }
            ]
	//exceptiongoods.lablePrintIndex.queryform.getForm().findField('tablePrint').getValue()   选中是true,没选是false
	},{
		xtype: 'rangeDateField',
		fieldLabel: '操作时间',
		dateType: 'datetimefield_date97',
		fromName: 'createtime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'00', '00'), 'Y-m-d H:i:s'),		
		toName: 'finishtime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		allowBlank: false,
		disallowBlank: true,
		blankText:'字段不能为空',
		columnWidth:.5
	},{
			xtype: 'container',
			columnWidth:.5,
			html: '&nbsp;'
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: '重置',
			columnWidth:.08,
			handler: function(){
				exceptiongoods.lablePrintIndex.queryform.getForm().reset();
				exceptiongoods.lablePrintIndex.queryform.getForm().findField('createtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'00', '00','00'), 'Y-m-d H:i:s'));
				exceptiongoods.lablePrintIndex.queryform.getForm().findField('finishtime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
						new Date().getMonth(), new Date().getDate(),
						'23', '59', '59'), 'Y-m-d H:i:s'));
			}
		},{
			xtype: 'container',
			columnWidth:.65,
			html: '&nbsp;'
		},{
			text: '查询',
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function(){
				var startTime = exceptiongoods.lablePrintIndex.queryform.getValues().createtime;
				var endTime = exceptiongoods.lablePrintIndex.queryform.getValues().finishtime;
				var difTime = 0;
				difTime = parseInt(Ext.Date.parse(endTime,'Y-m-d H:i:s') - Ext.Date.parse(startTime,'Y-m-d H:i:s')) / (24 * 60 * 60 * 1000);
				if(difTime > 1){
					Ext.MessageBox.alert('警告', '“查询时间”跨度不能超过1天'); //“任务创建时间”跨度不能超过31天
					//	Ext.MessageBox.alert(stockchecking.i18n('Foss.stockchecking.alert.title'), stockchecking.i18n('Foss.stockchecking.sttask.search.validator.createtime.span.limit')); //“任务创建时间”跨度不能超过7天		
					return;
				}
				var numberLength = exceptiongoods.lablePrintIndex.queryform.getValues().waybill_no.length;
		//		if(numberLength != 10 ){
		//			Ext.MessageBox.alert('警告', '单号只能为10位数字');
		//			return;
		//		}
				
				if(exceptiongoods.lablePrintIndex.queryform.getForm().isValid()){
					exceptiongoods.lablePrintIndex.pagingBar.moveFirst();       //pagingBar   分页用的
				}
			
		
			}
		},{
			text: '导出',
			columnWidth:.08,
			handler: function() {
				var goodsMapList = exceptiongoods.lablePrintIndex.waybillGoodsMap.getValues();
				var ids='';
				var RecordMapList;
				for(var i=0;i<goodsMapList.length;i++){
					RecordMapList = goodsMapList[i].getValues();
					for(var j=0;j<RecordMapList.length;j++){
						ids +=RecordMapList[j].data.id + ","; 
					}
				}
				ids = ids.substring(0,ids.length-1);
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}
				var queryParams = exceptiongoods.lablePrintIndex.queryform.getValues();
				var isPrint = null;
				if(exceptiongoods.lablePrintIndex.queryform.getForm().findField('isPrint').getValue()){
					isPrint = 1;
				}else {
					isPrint = 0;
				}
				
				Ext.Ajax.request({
	    			url: exceptiongoods.realPath('exportSortingOrLabelPringExcel.action'),
	    			form: Ext.fly('downloadAttachFileForm'),
	    			method : 'POST',
	    			params : {'noLabelGoodsVo.ids' : ids,
	    					  'noLabelGoodsVo.sortingAndPringLabelDto.isPrint' : isPrint,
	    					  'noLabelGoodsVo.sortingAndPringLabelDto.serialNo' : queryParams.serial_no,
							  'noLabelGoodsVo.sortingAndPringLabelDto.waybillNo' :queryParams.waybill_no ,
							  'noLabelGoodsVo.sortingAndPringLabelDto.operateCode' :queryParams.operate_code,
							  'noLabelGoodsVo.sortingAndPringLabelDto.beginTime' : queryParams.createtime,
							  'noLabelGoodsVo.sortingAndPringLabelDto.endTime' : queryParams.finishtime	
	    			},
	    			isUpload: true,
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				top.Ext.MessageBox.alert('导出失败',result.message);
	    			}
    			});
			
				
			}
		}]
	
	}
	],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
	
});


//打印方法
exceptiongoods.lablePrintIndex.print = function(action_type){			
		//运单号
		var waybillCmp = exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').getValue();
		newValue = waybillCmp+"";
		if (Ext.isEmpty(newValue) || !(newValue.length == 10 || newValue.length==14 || newValue.length==18)) {
			Ext.ux.Toast.msg('警告', '流水号只能为10位,14位或18位数字,请重新输入', 'error', 1000);
			exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
			//Ext.MessageBox.alert('警告', '流水号只能为10或者18位数字,请重新输入');
			waybillCmp = null;
		}  else if (newValue.length === 10 || newValue.length==14 || newValue.length == 18) {
				params = {
					'noLabelGoodsVo' : { 'printLabelDto' :{	'waybillNo' : newValue}}
				};
			if(action_type == 1){
				Ext.Ajax.request({
					url : exceptiongoods.realPath('printAppointedLabelExpress.action'),
					jsonData : params,
					async: false,
					success : function(response) {
						var result = Ext.decode(response.responseText);
							if(result.noLabelGoodsVo.barcodePrintDtoList == null){
								Ext.ux.Toast.msg('提示', '查询信息失败', 'error', 1000);
											exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
									//	Ext.MessageBox.alert('警告', '查询信息失败');
										return;
							}
							noLabelGoodsVo = result.noLabelGoodsVo.barcodePrintDtoList[0];
							//当前用户 工号
							optusernum = noLabelGoodsVo.optuserNum;
							//单号 
							number = noLabelGoodsVo.waybillNumber;
							//打印流水号 
							serialnos = noLabelGoodsVo.printSerialnos;
							//始发站(出发城市) 
							leavecity = noLabelGoodsVo.leavecity;
							//目的站编码  
							stationnumber = noLabelGoodsVo.destinationCode;
							//最终外场城市名称 
							finaloutname = noLabelGoodsVo.lastTransCenterCity;
							//件数 
							totalpieces = noLabelGoodsVo.totalPieces;
							//打印日期 
							printdate = noLabelGoodsVo.printDate;
							//是否送货 
							deliver = noLabelGoodsVo.deliverToDoor;
							//第二城市外场
							secLoadOrgName = noLabelGoodsVo.secLoadOrgName;
							//判定是否出发快递营业部        
							isNoStop = noLabelGoodsVo.isNoStop;
						
						
						Ext.data.JsonP.request({
							url : "http://localhost:8077/print",
							callbackKey : 'callback',
							async: false,
							params : {
								lblprtworker : "ExpLabelPrintWorker",
								optusernum : optusernum,
								number : number,
								serialnos : serialnos,
								leavecity : leavecity,
								stationnumber : stationnumber,
								finaloutname : finaloutname,
								totalpieces : totalpieces,
								printdate : printdate,
								deliver : deliver,
								secLoadOrgName : secLoadOrgName,
								isNoStop : isNoStop
							},
							success : function(result, request) {
								//打印成功,将此条运单插入到 标签打印表中
								//运单号
								var waybillCmp = exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').getValue();
								newValue = waybillCmp+"";
								params1 = {
										'noLabelGoodsVo' : { 'printLabelDto' :{	'waybillNo' : newValue}}
									};
								Ext.Ajax.request({
									url : exceptiongoods.realPath('insertPrintLabel.action'),
									jsonData : params1,
									async: true
								});	
								Ext.ux.Toast.msg('提示', '打印成功', 'error', 1000);
								exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
							},
							failure : function(result, request) {
								Ext.ux.Toast.msg('提示', '打印失败', 'error', 1000);
								exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
							//	Ext.MessageBox.alert('提示', '打印失败');
							}
						});
					},
					exception : function(response) {
							Ext.Msg.hide();
	    				var result = Ext.decode(response.responseText);
	    				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
	    			//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
	    				return false;
					}
				});	
			}else{
				//action_type == 0 此时要后台去判断是否是第一外场,如果是就打印标签,如果不是就不打印
				var queryParams = exceptiongoods.lablePrintIndex.textform.getValues();
				params = {
						'noLabelGoodsVo' : { 'sortingAndPringLabelDto' :{'waybillNo' : queryParams.waybill_no}}
					};
				Ext.Ajax.request({
					url : exceptiongoods.realPath('sortingAndPringLabel.action'),
					jsonData : params,
					async: false,//同步
					success : function(response) {
						var result = Ext.decode(response.responseText);
						//如果是第一外场的话就打印标签,如果不是就结束了
						if(result.noLabelGoodsVo.isFirstTransfer == 'Y'){
							if(result.noLabelGoodsVo.barcodePrintDtoList == null){
								Ext.ux.Toast.msg('提示', '查询信息失败', 'error', 1000);
								exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
								//Ext.MessageBox.alert('警告', '查询信息失败');
								return;
							}
							noLabelGoodsVo = result.noLabelGoodsVo.barcodePrintDtoList[0];
							//当前用户 工号
							optusernum = noLabelGoodsVo.optuserNum;
							//单号 
							number = noLabelGoodsVo.waybillNumber;
							//打印流水号 
							serialnos = noLabelGoodsVo.printSerialnos;
							//始发站(出发城市) 
							leavecity = noLabelGoodsVo.leavecity;
							//目的站编码  
							stationnumber = noLabelGoodsVo.destinationCode;
							//最终外场城市名称 
							finaloutname = noLabelGoodsVo.lastTransCenterCity;
							//件数 
							totalpieces = noLabelGoodsVo.totalPieces;
							//打印日期 
							printdate = noLabelGoodsVo.printDate;
							//是否送货 
							deliver = noLabelGoodsVo.deliverToDoor;
							//第二城市外场
							secLoadOrgName = noLabelGoodsVo.secLoadOrgName;
							//判定是否出发快递营业部        
							isNoStop = noLabelGoodsVo.isNoStop;
						
							
							Ext.data.JsonP.request({
								url : "http://localhost:8077/print",
								callbackKey : 'callback',
								async: false,
								params : {
									lblprtworker : "ExpLabelPrintWorker",
									optusernum : optusernum,
									number : number,
									serialnos : serialnos,
									leavecity : leavecity,
									stationnumber : stationnumber,
									finaloutname : finaloutname,
									totalpieces : totalpieces,
									printdate : printdate,
									deliver : deliver,
									secLoadOrgName : secLoadOrgName,
									isNoStop : isNoStop
								},
								success : function(result, request) {
									//打印成功,将此条运单插入到 标签打印表中
									//运单号
									var waybillCmp = exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').getValue();
									newValue = waybillCmp+"";
									params2 = {
											'noLabelGoodsVo' : { 'printLabelDto' :{	'waybillNo' : newValue}}
										};
									Ext.Ajax.request({
										url : exceptiongoods.realPath('insertPrintLabel.action'),
										jsonData : params2,
										async: true
									});	
									Ext.ux.Toast.msg('提示', '打印成功', 'error', 1000);
									exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
								},
								failure : function(result, request) {
									Ext.ux.Toast.msg('提示', '打印失败', 'error', 1000);
									//Ext.MessageBox.alert('提示', '打印失败');
									exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
								}
							});
						}else{
							exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
						}
					},
					exception : function(response) {
						//	Ext.Msg.hide();
	    				var result = Ext.decode(response.responseText);
	    				Ext.ux.Toast.msg('提示', result.message, 'error', 3000);
	    				exceptiongoods.lablePrintIndex.textform.getForm().findField('waybill_no').setValue();
	    			//Ext.ux.Toast.msg(exceptiongoods.nolabelgoods.i18n('foss.exceptiongoods.prompt'), result.message, 'error', 3000);
	    				return false;
					}
				});	
			
			}
			
	//		waybillCmp.select();
		}	
};

//exceptiongoods.lablePrintIndex.html = '<br>'+
//'<input  type="text" id="Foss_load_complementLabel_mainForm_items_waybill_id-inputEl" onkeydown="exceptiongoods.lablePrintIndex.enterKeyClick(event)"  style="width:350px;height:80px; font-size: 30px;	margin: 15px;" /> ';


//运单库存表格
Ext.define('Foss.load.lableprint.WaybillGrid', {
	extend:'Ext.grid.Panel',
	height: 450,
	autoScroll:true,
	columnLines: true,
    frame: true,
	columns: [{
			header: 'id', 
			dataIndex: 'id',
			width : 120,
			align: 'center',
			hidden:true	
		},{
			header: '运单号', 
			dataIndex: 'waybillNo',
			width : 150,
			align: 'center'
		},{ 
			header: '流水号', 
			dataIndex: 'serialNo',
			width : 150,
			align: 'center'
		},{ 
			header: '操作人', 
			dataIndex: 'operateName',
			width : 200,
			align: 'center'
		},{ 
			header: '操作人工号', 
			dataIndex: 'operateCode',
			width : 120,
			align: 'center',
			hidden:true	
		},{ 
			header: '操作部门', 
			dataIndex: 'orgName',
			width : 200,
			align: 'center'
		},{ 
			header: '操作部门code', 
			dataIndex: 'orgCode',
			width : 120,
			align: 'center',
			hidden:true	
		},{ 
			header: '操作时间',
			dataIndex: 'operateTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 200,
			align: 'center'
		}],
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.load.lableprint.WaybillStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
//				showHeaderCheckbox : false,
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize : 10,
				maximumSize : 200,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['10', 10], ['25', 25], ['50', 50], ['100', 100],['200', 200]]
				})
			});
			exceptiongoods.lablePrintIndex.pagingBar = me.bbar;
			me.callParent([cfg]);
		},
		listeners: {
			select : function(rowModel, record, index, eOpts) {
				var grid = this;
				var id = record.get('id');
				var goodsMap = exceptiongoods.lablePrintIndex.waybillGoodsMap.get(id);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(id,record);
				exceptiongoods.lablePrintIndex.waybillGoodsMap.add(id,goodsMap);	
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var id = record.get('id');
				var selectedList = grid.getSelectionModel().selected;
				exceptiongoods.lablePrintIndex.waybillGoodsMap.removeAtKey(id);
			}
		}
});

	//已打印快递单号 model
Ext.define('Foss.load.lableprint.WaybillModel',{
	extend: 'Ext.data.Model',
	fields: [
	    {name: 'id', type: 'string'},
		{name: 'waybillNo', type: 'string'},
		{name: 'serialNo', type: 'string'},
		{name: 'operateName', type: 'string'},
		{name: 'operateCode', type: 'string'},
		{name: 'orgName', type: 'string'},
		{name: 'orgCode', type: 'string'},
		{name: 'operateTime',type:'date',convert: dateConvert}
	]
});


//已打印快递单号 Store
Ext.define('Foss.load.lableprint.WaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.load.lableprint.WaybillModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: exceptiongoods.realPath('querySortingOrLabelPring.action'),
		reader : {
			type : 'json',
			root : 'noLabelGoodsVo.sortingAndPringLabelList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = exceptiongoods.lablePrintIndex.queryform.getValues();
				var labelprint = null;
				if(exceptiongoods.lablePrintIndex.queryform.getForm().findField('isPrint').getValue()){
					labelprint = 1;
				}else {
					labelprint = 0;
				}
				Ext.apply(operation, {
					params : {
						'noLabelGoodsVo.sortingAndPringLabelDto.waybillNo' : queryParams.waybill_no,
						'noLabelGoodsVo.sortingAndPringLabelDto.serialNo' : queryParams.serial_no,
						'noLabelGoodsVo.sortingAndPringLabelDto.operateCode' :queryParams.operate_code,
						'noLabelGoodsVo.sortingAndPringLabelDto.labelPrint' : labelprint,
						'noLabelGoodsVo.sortingAndPringLabelDto.beginTime' : queryParams.createtime,
						'noLabelGoodsVo.sortingAndPringLabelDto.endTime' : queryParams.finishtime	
					}
				});	
		},
		load : function( store, records, successful, eOpts){
	//		var waybillStockStatisticsDto = store.proxy.reader.rawData.stockVO.waybillStockStatisticsDto;
		
			
		}
		
	}
	
});


Ext.onReady(function(){
	Ext.QuickTips.init();
	var queryform = Ext.create('Foss.load.lableprint.QueryForm');
	exceptiongoods.lablePrintIndex.queryform = queryform;
	
	var waybillGrid = Ext.create('Foss.load.lableprint.WaybillGrid');
	exceptiongoods.lablePrintIndex.waybillGrid = waybillGrid;
	
	var textform = Ext.create('Foss.load.lableprint.TextForm');
	exceptiongoods.lablePrintIndex.textform = textform;
	
	exceptiongoods.lablePrintIndex.waybillGoodsMap = new Ext.util.HashMap();//用于查询库存界面存放已勾选的货件库存
	
	Ext.create('Ext.panel.Panel',{
		id:'T_exceptiongoods-lablePrintIndex_content',	
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [textform,queryform,waybillGrid],		
		renderTo: 'T_exceptiongoods-lablePrintIndex-body'
	});

});
