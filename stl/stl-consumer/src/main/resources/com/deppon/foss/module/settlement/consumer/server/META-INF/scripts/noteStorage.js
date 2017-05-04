/**
 * 刷新Grid列表
 */
consumer.noteStorage.refReshGrid=function(){
	 var grid = Ext.getCmp('T_consumer-initStorage_content').getAreaGrid();
	 grid.getPagingToolbar().moveFirst();
}
/**
 * 弹出入库操作Window窗口
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
consumer.noteStorage.showStorageWin=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex);
	var status=record.get('status');
	var approveStatus=record.get('approveStatus');
	var id=record.get('id');
	
	if(status===consumer.note.STATUS__DISTRIBUTE){	
		if(id){
			Ext.MessageBox.buttonText.yes = consumer.noteStorage.i18n('foss.stl.consumer.common.OK'); // 确定
			Ext.MessageBox.buttonText.no = consumer.noteStorage.i18n('foss.stl.consumer.common.cancel'); //取消
			
			//确定要入库选中的记录么?
			Ext.Msg.confirm(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), consumer.noteStorage.i18n('foss.stl.consumer.note.areYouSureStorageSelectedRecord'), function(btn,text){
			if(btn == 'yes'){
				var noteAppIds=new Array();
				noteAppIds.push(id);  
				Ext.Ajax.request({
					url:consumer.realPath('updateStorage.action'),
					params:{
						'noteVo.noteApplyDto.noteAppIds' : noteAppIds
					},
					success : function(response) { 
						var json = Ext.decode(response.responseText); 
						Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), json.message);
						var grid = Ext.getCmp('T_consumer-initStorage_content').getAreaGrid();
						 grid.getPagingToolbar().moveFirst();
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), json.message);
					}
				}); 
			}
			});
		}else{
			//请选择您要入库的记录
			Ext.Msg.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'),consumer.noteStorage.i18n('foss.stl.consumer.note.pleaseSelectToBeStoragedRecord'));
		}
	}else{
		if(status=='I'){
			
			//已入库，不能再进行入库
			Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'),consumer.noteStorage.i18n('foss.stl.consumer.note.storaged.cannotStorageAgain'));
		}else{
			
			//未下发不能进行入库
			Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'),consumer.noteStorage.i18n('foss.stl.consumer.note.notDistributed.cannotStoraged'));
		} 
	}
}

/**
 * 入库操作
 */
consumer.noteStorage.storeageOperator=function(me){
	//获取选中的记录
	var rowObjs = me.getSelectionModel().getSelection();
	if(rowObjs.length>0){
		Ext.MessageBox.buttonText.yes = consumer.noteStorage.i18n('foss.stl.consumer.common.OK'); // 确定
		Ext.MessageBox.buttonText.no = consumer.noteStorage.i18n('foss.stl.consumer.common.cancel'); //取消
		
		//确定要入库选中的记录么?
		Ext.Msg.confirm(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), consumer.noteStorage.i18n('foss.stl.consumer.note.areYouSureStorageSelectedRecord'), function(btn,text){
		if(btn == 'yes'){
			var noteAppIds=new Array();
			for(var i = 0; i< rowObjs.length; i++){
				var record=rowObjs[i].data;
				var status=record.status;
				if(status!=consumer.note.STATUS__DISTRIBUTE){
					
					//请选择单据状态为已下发的数据进行入库操作
					Ext.Msg.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'),consumer.noteStorage.i18n('foss.stl.consumer.note.pleaseSelectDistributedRecordToStorage'));
					//noteAppIds.length=0;
					return false;
				}
				noteAppIds.push(record.id);
			} 
			if(noteAppIds.length>0){
				Ext.Ajax.request({
					url:consumer.realPath('updateStorage.action'),
					params:{
						'noteVo.noteApplyDto.noteAppIds' : noteAppIds
					},
					success : function(response) { 
						var json = Ext.decode(response.responseText); 
						Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), json.message);
						var grid = Ext.getCmp('T_consumer-initStorage_content').getAreaGrid();
						 grid.getPagingToolbar().moveFirst();
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), json.message);
					}
				});
			} 
		}
		});
	}else{
		
		//请选择您要入库的记录
		Ext.Msg.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'),consumer.noteStorage.i18n('foss.stl.consumer.note.pleaseSelectToBeStoragedRecord'));
		return false;
	}
}

/**
 * 弹出查看明细Window窗口
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
consumer.noteStorage.noteQueryDetailsWin=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex);
	var status=record.get('status');
	var id=record.get('id');
	if(status===consumer.note.STATUS__DISTRIBUTE || status===consumer.note.STATUS__IN){ 
		var params = {
				"noteAppId":id 
		}; 
		var noteQueryWin= grid.up('gridpanel').getNoteQueryDetailsWindow();
		noteQueryWin.getNoteQueryDetailsGrid().store.loadPage(1,{
			params:params
		});
		noteQueryWin.show();
	}else{
		
		//未下发不能查看明细
		Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'),consumer.noteStorage.i18n('foss.stl.consumer.note.notDistribute.cannotShowDetail'));
	}	
}
	

//票据申请Model
Ext.define('Foss.note.NoteApplyModel', {
	extend :'Ext.data.Model', 
	fields :[  
	{	
		name :'id',  //申请编号
		type :'string'
	},{	
		name :'applyOrgCode',  //申请部门编码
		type :'string'
	},{	
		name :'applyOrgName',  //申请部门
		type :'string'
	}, {
		name :'applyAmount',   //申请数量
		type :'int'
	}, {
		name :'applyTime',   //申请日期
		type :'date',
		convert:stl.longToDateConvert
	}, {
		name :'applyUserCode', //申请人编码
		type :'string'
	}, {
		name :'applyUserName', //申请人
		type :'string'
	}, {
		name :'active', //是否有效
		type :'string'
	}, { 
		name :'status', //单据状态
		type :'string' 
	}, {
		name :'approveStatus', //审核状态
		type :'string'
	}, {
		name :'writeoffStatus', //核销状态
		type :'string'
	},{
		name:'beginNo',
		convert:consumer.note.beginAndEndNoConverter 
	},{
		name:'endNo',
		convert:consumer.note.beginAndEndNoConverter 
	}]
}); 

//票据明细Model
Ext.define('Foss.note.NoteQueryDetailsModel', {
			extend :'Ext.data.Model', 
			fields :[  
			{	
				name :'noteDetailsId',  //票据明细ID
				type :'string'
			},{	
				name :'noteAppId',  //票据申请Id
				type :'string'
			},{	
				name :'noteStockInId',  //票据入库ID
				type :'string'
			}, {
				name :'detailsNo',   //票据明细编码ID
				type :'string'
			}, {
				name :'applyOrgCode', //申请部门编码
				type :'string'
			}, {
				name :'applyOrgName', //申请部门编码
				type :'string'
			}, {
				name :'applyTime', //申请时间
				type :'date',
				convert:stl.longToDateConvert
			}, {
				name :'beginWithEndNo', //起止编号
				type :'string'
			}, { 
				name :'issuedUserCode', //下发人编码
				type :'string' 
			}, {
				name :'issuedUserName', //下发人名称
				type :'string'
			}, {
				name :'issuedTime', //下发时间
				type :'date',
				convert:stl.longToDateConvert
			}, {
				name :'storageTime', //入库时间
				type :'date',
				convert:stl.longToDateConvert
			}, {
				name :'storageUserCode', //入库人编码
				type :'string'
			}, {
				name :'storageUserName', //入库人名称
				type :'string'
			}, {
				name :'writeoffStatus', //核销状态
				type :'string'
			}, {
				name :'writeoffUserCode', //核销人编码
				type :'string'
			}, {
				name :'writeoffUserName', //核销人名称
				type :'string'
			}, {
				name :'status', //单据状态
				type :'string'
			}]
		}); 

//票据申请Store
Ext.define('Foss.note.NoteApplyStore',{
	extend:'Ext.data.Store',
	model:'Foss.note.NoteApplyModel',  
	sorters: [{
        property: 'applyTime',
        direction: 'DESC'
    }],
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : consumer.realPath('queryApply.action'),
		reader : {
			type : 'json',
			root : 'noteVo.noteApplyEntityList',
			totalProperty : 'totalCount'
		}
	}
}); 

//小票票据明细Store
Ext.define('Foss.note.NoteQueryDetailsStore',{
	extend:'Ext.data.Store',
	model:'Foss.note.NoteQueryDetailsModel',  
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : consumer.realPath('queryNoteDetails.action'),
		reader : {
			type : 'json',
			root : 'noteVo.noteQueryDtoList',
			totalProperty : 'totalCount'
		}
	}
}); 
  

//票据申请Grid
Ext.define('Foss.note.NoteApplyGrid',{
	extend: 'Ext.grid.Panel',
	title: consumer.noteStorage.i18n('foss.stl.consumer.note.applyGridTitle'),//申请小票单据列表
	columnWidth: 1,
	autoScroll : true,
	height: 650,
	stripeRows: true,
    columnLines: true,
	collapsible: false,
    bodyCls: 'autoHeight',
    frame: true,
	cls: 'autoHeight', 
	store : null,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    emptyText: consumer.noteStorage.i18n('foss.stl.consumer.common.emptyText'),
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	},
	//分页
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:50,
				maximumSize:500,
				plugins:'pagesizeplugin'
			});
		}
       return me.pagingToolbar;
	},
	getNoteQueryDetailsWindow:function(){
		if(consumer.noteStorage.noteQueryDetailsWindow==null){
			consumer.noteStorage.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');
		}
		return consumer.noteStorage.noteQueryDetailsWindow;
	},
	columns : [{
		xtype:'actioncolumn',
		flex: 1,
		text: consumer.noteStorage.i18n('foss.stl.consumer.common.actionColumn'),//操作列
		align: 'center',
		items: [{
                tooltip: consumer.noteStorage.i18n('foss.stl.consumer.note.storage'),//入库
				iconCls:'foss_icons_stl_inStorage',
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                		consumer.noteStorage.showStorageWin(grid, rowIndex, colIndex);
                },
                getClass:function(v,m,r,rowIndex){
	                if(!consumer.noteStorage.isPermission('/stl-web/consumer/updateStorage.action')){
						return 'statementBill_hide';
					}else {
						return 'foss_icons_stl_inStorage';
					}
                }
            },{
                tooltip: consumer.noteStorage.i18n('foss.stl.consumer.common.showDetail'),//查看明细
				iconCls:'deppon_icons_showdetail',
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                		consumer.noteStorage.noteQueryDetailsWin(grid, rowIndex, colIndex);
                	}
            }]
			},  
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyOrgCode'), dataIndex :'applyOrgCode' ,hidden:true},//申请部门编码
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyOrgName'), flex:1, dataIndex :'applyOrgName' ,width:200},//申请部门
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyAmount'), dataIndex :'applyAmount' ,width:100},//申请数据(本)
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.startNumber'),dataIndex:'beginNo',width:100},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.endNumber'),dataIndex:'endNo',width:100},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyDate'),flex:1, dataIndex :'applyTime' ,width:200,//申请日期
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_DATE);
				}
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyUserCode'), dataIndex:'applyUserCode' ,hidden:true},//申请人编码
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyUserName'), flex:1, dataIndex:'applyUserName' ,width:200},//申请人
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.active'), dataIndex:'active' ,width:100,//是否有效
				renderer: consumer.note.activeRenderer 
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.status'),  dataIndex:'status' ,width:100,//单据状态
				renderer: consumer.note.statusRenderer 
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.approveStatus'),  dataIndex:'approveStatus' ,width:100,//审核状态
				renderer: consumer.note.approveStatusRenderer 
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.writeoffStatus'), dataIndex:'writeoffStatus',width:100,//核销状态
				renderer: consumer.note.writeoffStatusRenderer 
			}
			],   
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config); 
		me.store = Ext.create('Foss.note.NoteApplyStore',{
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var form = Ext.getCmp('T_consumer-initStorage_content').getQueryForm().getForm(); 
					if(form.isValid()){
						var applyStartTime=form.findField("noteVo.noteApplyDto.applyStartTime").getValue();
						var applyEndTime=form.findField("noteVo.noteApplyDto.applyEndTime").getValue();
						
						if(Ext.isEmpty(applyStartTime)){
							
							//请选择申请开始日期
							Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), consumer.noteStorage.i18n('foss.stl.consumer.note.pleaseSelectStartDate')); 
							return false;
						}else if(Ext.isEmpty(applyEndTime)){
							
							//请选择申请结束日期
							Ext.MessageBox.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), consumer.noteStorage.i18n('foss.stl.consumer.note.pleaseSelectEndDate')); 
							return false;
						}
						
						var result1=consumer.note.validateDateDiff(applyStartTime,applyEndTime,consumer.note.DATELIMITDAYS,'申请');
				    	if(result1){
				    		return false;
				    	}
						var params=form.getValues();
						Ext.apply(params ,{
							"noteVo.queryPage" : consumer.note.QUERY_PAGE__STORAGE 
						});
						Ext.apply(operation,{
							params :  params
						});	
					}else{
						
						//请检查输入条件是否合法
						Ext.Msg.alert(consumer.noteStorage.i18n('foss.stl.consumer.common.warmTips'), consumer.noteStorage.i18n('foss.stl.consumer.note.pleaseCheckConditionLegal'));
						return false;
					}
				}
		    }
		}); 
		me.dockedItems = [{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 0 5 3'
			},
			items :[ {
				xtype :'button',
				text :consumer.noteStorage.i18n('foss.stl.consumer.note.confirmStorage'),//确认入库
				columnWidth :.1,
				handler : function(){
					consumer.noteStorage.storeageOperator(me);
				},
				disabled:!consumer.noteStorage.isPermission('/stl-web/consumer/updateStorage.action'),
				hidden:!consumer.noteStorage.isPermission('/stl-web/consumer/updateStorage.action')
			}]
		}];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store; 
		me.callParent([ cfg ]);
	} 
});


//小票单据明细Grid
Ext.define('Foss.note.NoteQueryDetailsGrid',{
	extend: 'Ext.grid.Panel',
	title: consumer.noteStorage.i18n('foss.stl.consumer.note.noteDetailList'),//小票单据详细列表
	columnWidth: 1,
	height: 500,
	stripeRows: true,
	columnLines: true,
	collapsible: false, 
    bodyCls: 'autoHeight',
    emptyText: consumer.noteStorage.i18n('foss.stl.consumer.common.emptyText'),
    frame: true,
    //增加表格列的分割线
	//cls: 'autoHeight', 
	store : null,
	autoScroll : true,
	pagingToolbar:null,
	hiddenAppId:null,
	setHiddenAppId:function(appId){
		this.hiddenAppId=appId;
	},
	getHiddenAppId:function(){
		return this.hiddenAppId;
	},
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
				pageSize:5
			});
		}
     return me.pagingToolbar;
	}, 
	getNoteQueryDetailsWindow:function(){
		if(consumer.noteStorage.noteQueryDetailsWindow==null){
			consumer.noteStorage.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');
		}
		return consumer.noteStorage.noteQueryDetailsWindow;
	},
	columns : [ 
			{ text:'ID', dataIndex :'noteDetailsId' ,hidden:true},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.noteNumber'), dataIndex :'detailsNo' ,width:120},//小票单据编号
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyOrgCode'),   dataIndex :'applyOrgCode',hidden:true},//申请部门编码
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyOrgName'),   dataIndex :'applyOrgName' ,width:130},//申请部门
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.applyTime'), dataIndex :'applyTime' ,width:150,//申请时间
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.beginWithEndNo'), dataIndex :'beginWithEndNo' ,width:100},//下发起止号
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.issuedUserCode'), dataIndex:'issuedUserCode' ,hidden:true},//下发人编码
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.issuedUserName'), dataIndex:'issuedUserName' ,width:120},//下发人
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.issuedTime'), dataIndex :'issuedTime' ,width:150,//下发时间
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.storageTime'), dataIndex :'storageTime' ,width:150,//入库时间
				 renderer:function(value){
						return stl.dateFormat(value,stl.FORMAT_TIME);
					}
			 },
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.storageUserCode'), dataIndex:'storageUserCode' ,hidden:true},//入库操作人编码
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.storageUserName'), dataIndex:'storageUserName' ,width:120},//入库操作人
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.writeoffTime'), dataIndex :'writeoffTime' ,width:150,//核销时间
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.writeoffUserCode'), dataIndex:'writeoffUserCode' ,hidden:true},//核销人编码
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.writeoffUserName'), dataIndex:'writeoffUserName' ,width:120},//核销人
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.status'),  dataIndex:'status' ,width:100,//单据状态
				renderer: consumer.note.statusRenderer
			},
			{ text:consumer.noteStorage.i18n('foss.stl.consumer.note.writeoffStatus'), dataIndex:'writeoffStatus',width:100,//核销状态
				renderer: consumer.note.writeoffStatusRenderer
			}
			],   
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config); 
		me.store = Ext.create('Foss.note.NoteQueryDetailsStore'); 
		me.store.on('beforeload',function(store, operation, eOpts){
			var meParams ;
			var params=operation.params;
			if(params!=null){
				meParams={
						"noteVo.noteQueryDto.noteAppId":params.noteAppId
					};
				me.setHiddenAppId(params.noteAppId);
			}else{
				meParams={
						"noteVo.noteQueryDto.noteAppId":me.getHiddenAppId(),
						"limit":operation.limit,
						"page":operation.page,
						"start":operation.start
						};
			}
			Ext.apply(operation,{params:meParams});	
		});
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store; 
		me.callParent([ cfg ]);
	} 
});

//入库小票单据管理 FORM
Ext.define('Foss.note.NoteStorageForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteStorage.i18n('foss.stl.consumer.note.noteStorageManage'),//入库小票单据管理
	frame:true,
	columnWidth:1,
	collapsible:true,
	animcollapse:true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 3
	},
	items:[
	{
		name:'noteVo.noteApplyDto.applyOrgName',
		fieldLabel:consumer.noteStorage.i18n('foss.stl.consumer.note.applyOrgName'),//申请部门
		readOnly:'true',
		value:stl.currentDept.name
	},{
		name:'noteVo.noteApplyDto.applyOrgCode',
		fieldLabel:consumer.noteStorage.i18n('foss.stl.consumer.note.applyOrgCode'),//申请部门编码
		value:stl.currentDept.code,
		hidden:true
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyStartTime', 
		fieldLabel:consumer.noteStorage.i18n('foss.stl.consumer.note.applyDate'),//申请日期
		format:'Y-m-d',
		allowBlank:false,
		//value:stl.dateFormat(stl.getTargetDate(new Date(),-30),stl.FORMAT_DATE)
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE)
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:consumer.noteStorage.i18n('foss.stl.consumer.note.to'),//至
		format:'Y-m-d',
		allowBlank:false,
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE) 
	},{
		xtype : 'commonemployeeselector',
		name: 'noteVo.noteApplyDto.applyUserCode',
		fieldLabel :consumer.noteStorage.i18n('foss.stl.consumer.note.applyUserName')//申请人
	},{
		xtype:'combo',
		name:'noteVo.noteApplyDto.status',
		fieldLabel:consumer.noteStorage.i18n('foss.stl.consumer.note.status'),//单据状态
		value:consumer.note.STATUS__DISTRIBUTE,
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore(
				'NOTE_APPLICATION__STATUS', null, {
					'valueCode' : '',
					'valueName' : consumer.noteStorage.i18n('foss.stl.consumer.common.all')
				})
	}, {
		xtype :'container',
		border :false,
		colspan:1,
		html :'&nbsp;'
	},{
		border: 1,
		xtype:'container',
		width:670,
		height:40,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:consumer.noteStorage.i18n('foss.stl.consumer.common.reset'),//重置 
			  columnWidth : .12,
			  handler:consumer.note.reset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.76
			},
		  	{
				text:consumer.noteStorage.i18n('foss.stl.consumer.common.query'),//查询
				cls:'yellow_button', 
				columnWidth : .12,
				handler:consumer.noteStorage.refReshGrid
		  	}
		]
	}
  ]  
}); 
 
//小票单据明细查看操作  Window
Ext.define('Foss.note.NoteQueryDetailsWindow',{
	extend: 'Ext.Window', 
	closeAction: 'hide',
    title:'',
    x:250,
	y:250,
    modal:true,
    width:1000,
	height:600,
    resizable:false,
    //layout: 'fit',
    layout : 'column',
    noteQueryDetailsGrid:null,
    getNoteQueryDetailsGrid:function(){  
    	if(this.noteQueryDetailsGrid==null){
    		this.noteQueryDetailsGrid=Ext.create('Foss.note.NoteQueryDetailsGrid');
    	}
    	return this.noteQueryDetailsGrid;
    }, 
    constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.fbar=[{
	  		  text:consumer.noteStorage.i18n('foss.stl.consumer.common.close'),//关闭
	  		  columnWidth : .25,
	  		  handler:function(){  
	  			  consumer.noteStorage.noteQueryDetailsWindow.hide(); 
	  		   }
	  		  }];
		me.items = [me.getNoteQueryDetailsGrid()];
		me.callParent([cfg]);
    }
	}); 

consumer.noteStorage.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_consumer-initStorage_content')) {
		return;
	} 
	var queryForm = Ext.create('Foss.note.NoteStorageForm');//查询FORM
	var areaGrid = Ext.create('Foss.note.NoteApplyGrid');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_consumer-initStorage_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body', //必须添加，内容定位用。
		layout : 'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return areaGrid;
		},
		items: [ queryForm,areaGrid],
		renderTo : 'T_consumer-initStorage-body'
	}); 
});

