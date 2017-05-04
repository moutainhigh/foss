/**
 * Grid列上的核销操作弹出Win
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
consumer.noteWriteoff.showWriteOffWin=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex);
	var id=record.get('id');
	var status=record.get('status');
	var writeoffStatus=record.get('writeoffStatus');
	var grid = Ext.getCmp('T_consumer-initWriteoff_content').getAreaGrid();

	/**
	 * 只有当单据状态是"入库"且核销状态是"申请核销"时才能进行入库操作
	 */
	if(status===consumer.note.STATUS__IN && writeoffStatus==consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF){	
		
		if(id){
			var params = {
					"noteVo.noteStockInDto.noteAppId":id
			}; 
			
			var writeoffNoteWin= grid.getNotWriteoffWindow();
			
			var writeoffNoteForm=writeoffNoteWin.getWriteoffNoteForm();
			record.set('applyTime',new Date(record.get('applyTime')));
			writeoffNoteForm.getForm().loadRecord(record);	
			writeoffNoteForm.getNoteStockInNumGrid().store.load({  
				params:params
			});
			writeoffNoteWin.show();
		}else{
			
			//请选择您要核销的记录
			Ext.Msg.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'),consumer.noteWriteoff.i18n('foss.stl.consumer.note.pleaseSelectRecordToWriteoff'));
		}
	}else{
		if(status!=consumer.note.STATUS__IN ){
			
			//单据状态非已入库，不能进行核销
			Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'),consumer.noteWriteoff.i18n('foss.stl.consumer.note.statusNotStoraged.cannotWriteoff'));
		}else if(writeoffStatus!='AW'){
			
			//核销状态非已申请，不能进行核销
			Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'),consumer.noteWriteoff.i18n('foss.stl.consumer.note.statusNotApplied.cannotWriteoff'));
		} 
	}
}
/**
 * 动态改变行的颜色
 * @param me 
 */
consumer.noteWriteoff.changeRowColor=function(me,_this,_record,rowIndex,eopts,color){
	var obj=_record;
	var record=obj.data;
	var status=record.status;
	var writeoffStatus=record.writeoffStatus;
	if(status !=consumer.note.STATUS__IN || writeoffStatus != consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF){
		var cells1 = me.getView().getNodes()[rowIndex].children;
		for(var j=0;j<cells1.length;j++){
			cells1[j].style.backgroundColor=color;
		}
	} 
}
/**
 * 核销操作
 * @param me 
 */
consumer.noteWriteoff.writeoffOperator=function(me){
	var formData=me.getRecord().data;//获取grid传入的数据
	var form = me.getForm();//获取Form组件对象
	var noteAppIds=new Array();

	var id=form.findField('id').getValue();
	var writeoffNotes=form.findField('writeoffNotes').getValue();

	noteAppIds.push(id);  
	Ext.Ajax.request({
		url:consumer.realPath('updateWriteoff.action'),
		params:{
			'noteVo.noteApplyDto.noteAppIds' : noteAppIds,
			'noteVo.noteApplyDto.writeoffStatus':consumer.note.WRITEOFF_STATUS__WRITEOFF_DONE,
			'noteVo.noteApplyDto.writeoffNotes':writeoffNotes
		},
		success : function(response) { 
			var json = Ext.decode(response.responseText); 
			Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), json.message);
			 var grid = Ext.getCmp('T_consumer-initWriteoff_content').getAreaGrid();
			 grid.getPagingToolbar().moveFirst();
			 consumer.noteWriteoff.writeoffNoteWindow.hide();
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), json.message);
		}
	}); 
}

/**
 * 批量核销操作
 * @param me 
 */
consumer.noteWriteoff.batchWriteoffOperator=function(me){
	//获取选中的复选框
	var rowObjs = me.getSelectionModel().getSelection();
	if(rowObjs.length>0)
	{
		Ext.MessageBox.buttonText.yes = consumer.noteWriteoff.i18n('foss.stl.consumer.common.OK');  
		Ext.MessageBox.buttonText.no = consumer.noteWriteoff.i18n('foss.stl.consumer.common.cancel');
		
		//确定要核销选中的记录么?
		Ext.Msg.confirm(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), consumer.noteWriteoff.i18n('foss.stl.consumer.note.areYouSureWriteoffSelectedRecord'), function(btn,text){
			if(btn == 'yes'){
				var noteAppIds=new Array();
				for(var i = 0; i< rowObjs.length; i++)
				{
				  var record=rowObjs[i].data;
                  var status=record.status;
                  var writeoffStatus=record.writeoffStatus;
                  if(status!=consumer.note.STATUS__IN || writeoffStatus!=consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF){
                      noteAppIds.length=0;
                      break;
                    }
                  noteAppIds.push(record.id);
				} 
				if(noteAppIds.length>0){
					Ext.Ajax.request({
    					url:consumer.realPath('updateWriteoff.action'),
    					params:{
    						'noteVo.noteApplyDto.noteAppIds' : noteAppIds,
							'noteVo.noteApplyDto.writeoffStatus':consumer.note.WRITEOFF_STATUS__WRITEOFF_DONE 
    					},
    					success : function(response) { 
    					  var json = Ext.decode(response.responseText); 
    					  Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), json.message);
    					  var grid = Ext.getCmp('T_consumer-initWriteoff_content').getAreaGrid();
    					  grid.getPagingToolbar().moveFirst();
    					  consumer.noteWriteoff.writeoffNoteWindow.hide();
    					},
    					exception : function(response) {
    					  var json = Ext.decode(response.responseText);
    					  Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), json.message);
    					}
					});
				}else{
					
					//请选择单据状态为已入库且核销状态为已申请的数据进行核销操作
					Ext.Msg.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'),consumer.noteWriteoff.i18n('foss.stl.consumer.note.pleaseSelectStoragedAndAppliedRecordToWriteoff'));
				}
			}
		});
	}else{
		
		//请选择要核销的申请记录
		Ext.Msg.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'),consumer.noteWriteoff.i18n('foss.stl.consumer.note.pleaseSelectRecordToBeWriteoff'));
	}
}
/**
 * Grid列上的查看明细操作弹出Win
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
consumer.noteWriteoff.showQueryDetailsWin=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex);
  	var status=record.get('status');
  	var id=record.get('id');
  	/**
  	 * 当单据状态是"已下发"或"已入库"时才能进行查看明细操作
  	 */
  	if(status===consumer.note.STATUS__DISTRIBUTE || status===consumer.note.STATUS__IN){ 
  		var params = {
					"noteAppId":id 
  				}; 
  		var noteQueryDetailsWin= grid.up('gridpanel').getNoteQueryDetailsWindow();
		noteQueryDetailsWin.getNoteQueryDetailsGrid().store.loadPage(1,{  
				  params:params
			 });
		noteQueryDetailsWin.show();
  	}else{
  		
  		//未下发不能查看明细
  		Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'),consumer.noteWriteoff.i18n('foss.stl.consumer.note.notDistribute.cannotShowDetail'));
  	}								
 }

/**
 * 刷新Grid列表
 */
consumer.noteWriteoff.refReshGrid=function(){
	 var grid = Ext.getCmp('T_consumer-initWriteoff_content').getAreaGrid();
	 grid.getPagingToolbar().moveFirst();
}
/**
 * 可编辑表格设置
 */
consumer.noteWriteoff.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 1
});
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
		name :'issuedUserCode', //下发人编码
		type :'string' 
	}, {
		name :'issuedUserName', //下发人名称
		type :'string'
	}, { 
		name :'issuedOrgCode', //下发人部门编码
		type :'string' 
	}, {
		name :'issuedOrgName', //下发人部门名称
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
				name :'writeoffTime', //核销时间
				type :'date',
				convert:stl.longToDateConvert
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
	title: consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyGridTitle'),//申请小票单据列表
	columnWidth: 1,
	autoScroll : true,
	height: 'autoHeight',
	stripeRows: true,
	columnLines: true,
	collapsible: false,
	bodyCls: 'autoHeight',
	frame: true,
	height: 650,
	store : null,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    emptyText: consumer.noteWriteoff.i18n('foss.stl.consumer.common.emptyText'),  
	viewConfig:{
		enableTextSelection : true /*,//设置行可以选择，进而可以复制
		stripeRows:false, //各行变色
		getRowClass : function(record,rowIndex,rowParams,store){
			alert(rowIndex);
            return "x-panel-header-text x-panel-header-text-default-framed";
		} */
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
		if(consumer.noteWriteoff.noteQueryDetailsWindow==null){
			consumer.noteWriteoff.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');
		}
		return consumer.noteWriteoff.noteQueryDetailsWindow;
	},
	getNotWriteoffWindow:function(){
		if(consumer.noteWriteoff.writeoffNoteWindow==null){
			consumer.noteWriteoff.writeoffNoteWindow=Ext.create('Foss.note.WriteoffNoteWindow');
		}
		return consumer.noteWriteoff.writeoffNoteWindow;
	},
	listeners:{
		select: function(_this,_record,rowIndex,eopts){
			var me = this;
			var color='red';
			consumer.noteWriteoff.changeRowColor(me,_this,_record,rowIndex,eopts,color);
		},
		deselect: function(_this,_record,rowIndex,eopts){
			var me = this;
			var color='white';
			consumer.noteWriteoff.changeRowColor(me,_this,_record,rowIndex,eopts,color);
		}
	},
	columns : [{
		xtype:'actioncolumn',
		flex: 1,
		text: consumer.noteWriteoff.i18n('foss.stl.consumer.common.actionColumn'),//操作列
		align: 'center',
		items: [{
              tooltip: consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoff'),//核销
			  iconCls:'foss_icons_stl_verifCancel',
			  width:42,
              handler: function(grid, rowIndex, colIndex) {	 
            	  var grid = Ext.getCmp('T_consumer-initWriteoff_content').getAreaGrid();
            	  grid.getStore().load(function(){
            		  consumer.noteWriteoff.showWriteOffWin(grid, rowIndex, colIndex);
            	  });
              },
//              hidden:!consumer.noteWriteoff.isPermission('/stl-web/consumer/updateWriteoff.action')
              getClass:function(v,m,r,rowIndex){
	                if(!consumer.noteWriteoff.isPermission('/stl-web/consumer/updateWriteoff.action')){
						return 'statementBill_hide';
					}else {
						return 'foss_icons_stl_verifCancel';
					}
              }
          },{
              tooltip: consumer.noteWriteoff.i18n('foss.stl.consumer.common.showDetail'),//查看明细
              iconCls:'deppon_icons_showdetail',
              width:42,
              handler: function(grid, rowIndex, colIndex) {	
            	  consumer.noteWriteoff.showQueryDetailsWin(grid, rowIndex, colIndex);
               }
          	}]
			},  
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyOrgCode'), dataIndex :'applyOrgCode' ,hidden:true},//申请部门编码
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyOrgName'), flex:1, dataIndex :'applyOrgName' ,width:200},//申请部门
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyAmount'), dataIndex :'applyAmount' ,width:100},//数据数据(本)
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.startNumber'),dataIndex:'beginNo',width:100},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.endNumber'),dataIndex:'endNo',width:100},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyDate'),flex:1, dataIndex :'applyTime' ,width:200,//申请日期
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_DATE);
				}
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyUserCode'), dataIndex:'applyUserCode' ,hidden:true},//申请人编码
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyUserName'), flex:1, dataIndex:'applyUserName' ,width:200},//申请人
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.active'), dataIndex:'active' ,width:100,//是否有效
				renderer: consumer.note.activeRenderer   
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.status'),  dataIndex:'status' ,width:100,//单据状态
				renderer: consumer.note.statusRenderer 
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.approveStatus'),  dataIndex:'approveStatus' ,width:100,//审核状态
				renderer: consumer.note.approveStatusRenderer 
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffStatus'), dataIndex:'writeoffStatus',width:100,//核销状态
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
					var form = Ext.getCmp('T_consumer-initWriteoff_content').getQueryForm().getForm(); 
					if(form.isValid()){
						var applyStartTime=form.findField("noteVo.noteApplyDto.applyStartTime").getValue();
						var applyEndTime=form.findField("noteVo.noteApplyDto.applyEndTime").getValue();
						var writeoffStartTime=form.findField("noteVo.noteApplyDto.writeoffStartTime").getValue();
						var writeoffEndTime=form.findField("noteVo.noteApplyDto.writeoffEndTime").getValue();
						
						
						if(Ext.isEmpty(applyStartTime) && !Ext.isEmpty(applyEndTime)){
							
							//请选择申请开始日期
							Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), consumer.noteWriteoff.i18n('foss.stl.consumer.note.pleaseSelectStartDate')); 
							return false;
						}else if(!Ext.isEmpty(applyStartTime) && Ext.isEmpty(applyEndTime)){
							
							//请选择申请结束日期
							Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), consumer.noteWriteoff.i18n('foss.stl.consumer.note.pleaseSelectEndDate')); 
							return false;
						}
						
						if(Ext.isEmpty(writeoffStartTime) && !Ext.isEmpty(writeoffEndTime)){
							
							//请选择核销开始日期
							Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), consumer.noteWriteoff.i18n('foss.stl.consumer.note.pleaseSelectWriteoffStartDate')); 
							return false;
						}else if(!Ext.isEmpty(writeoffStartTime) && Ext.isEmpty(writeoffEndTime)){
							
							//请选择核销结束日期
							Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), consumer.noteWriteoff.i18n('foss.stl.consumer.note.pleaseSelectWriteoffEndDate')); 
							return false;
						}
						
						if(!applyStartTime && !applyEndTime && !writeoffStartTime && !writeoffEndTime){
							
							//申请日期与核销日期至少选择一个
							Ext.MessageBox.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), consumer.noteWriteoff.i18n('foss.stl.consumer.note.dateOfApplyAndWriteoffAtLeastSelectOne')); 
							return false;
						}
						
						var result1=consumer.note.validateDateDiff(applyStartTime,applyEndTime,consumer.note.DATELIMITDAYS,'申请');
				    	if(result1){
				    		return false;
				    	}
				    	var result2=consumer.note.validateDateDiff(writeoffStartTime,writeoffEndTime,consumer.note.DATELIMITDAYS,'核销');
						if(result2){
							return false;
						}
						
						var params=form.getValues();
						Ext.apply(params,{
								'noteVo.queryPage':consumer.note.QUERY_PAGE__WRITEOFF
							});	
						
						Ext.apply(operation,{
							params : params
						});	
					}else{
						
						//请检查输入条件是否合法
						Ext.Msg.alert(consumer.noteWriteoff.i18n('foss.stl.consumer.common.warmTips'), consumer.noteWriteoff.i18n('foss.stl.consumer.cod.pleaseCheckTheInputConditionIsLegal'));
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
				text :consumer.noteWriteoff.i18n('foss.stl.consumer.note.confirmWriteoff'),//确认核销
				columnWidth :.1,
				handler :function() { 
					consumer.noteWriteoff.batchWriteoffOperator(me);
				},
				disabled:!consumer.noteWriteoff.isPermission('/stl-web/consumer/updateWriteoff.action'),
				hidden:!consumer.noteWriteoff.isPermission('/stl-web/consumer/updateWriteoff.action')
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
	title: consumer.noteWriteoff.i18n('foss.stl.consumer.note.noteDetailList'),//小票单据详细列表
	columnWidth: 1,
	height: 500,
	stripeRows: true,
	columnLines: true,
	collapsible: false, 
    bodyCls: 'autoHeight',
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
		if(consumer.noteWriteoff.noteQueryDetailsWindow==null){
			consumer.noteWriteoff.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');
		}
		return consumer.noteWriteoff.noteQueryDetailsWindow;
	},
	columns : [ 
			{ text:'ID', dataIndex :'noteDetailsId' ,hidden:true},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.noteNumber'), dataIndex :'detailsNo' ,width:120},//小票单据编号
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyOrgCode'),   dataIndex :'applyOrgCode',hidden:true},//申请部门编码
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyOrgName'),   dataIndex :'applyOrgName' ,width:130},//申请部门
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyTime'), dataIndex :'applyTime' ,width:150,//申请时间
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.beginWithEndNo'), dataIndex :'beginWithEndNo' ,width:100},//下发起卡号
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.issuedUserCode'), dataIndex:'issuedUserCode' ,hidden:true},//下发人编码
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.issuedUserName'), dataIndex:'issuedUserName' ,width:120},//下发人
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.issuedTime'), dataIndex :'issuedTime' ,width:150,//下发时间
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.storageTime'), dataIndex :'storageTime' ,width:150,//入库时间
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.storageUserCode'), dataIndex:'storageUserCode' ,hidden:true},//入库操作人编码
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.storageUserName'), dataIndex:'storageUserName' ,width:120},//入库操作人
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffTime'), dataIndex :'writeoffTime' ,width:150,//核销时间
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffUserCode'), dataIndex:'writeoffUserCode' ,hidden:true},//核销人编码
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffUserName'), dataIndex:'writeoffUserName' ,width:120},//核销人
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.status'),  dataIndex:'status' ,width:100,//单据状态
				renderer: consumer.note.statusRenderer
			},
			{ text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffStatus'), dataIndex:'writeoffStatus',width:100,//核销状态
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

//核销小票单据管理 FORM
Ext.define('Foss.note.WriteoffForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffNoteManage'),//核销小票单据管理
	columnWidth:1,
	frame:true,
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
	items:[{
		xtype: 'dynamicorgcombselector',
		name:'noteVo.noteApplyDto.applyOrgCode',
		fieldLabel: consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyOrgName'),//申请部门
		allowBlank: false
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyStartTime', 
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyDate'),//申请日期
		format:'Y-m-d',
		allowBlank:true,
		//value:stl.dateFormat(stl.getTargetDate(new Date(),-30),stl.FORMAT_DATE)
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE)
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.to'),//至
		format:'Y-m-d',
		allowBlank:true,
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE) 
	},{
		xtype : 'commonemployeeselector',
		name: 'noteVo.noteApplyDto.applyUserCode',
		fieldLabel : consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyUserName')//申请人 
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.writeoffStartTime', 
		fieldLabel: consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffDate'),//核销日期
		format:'Y-m-d',
		allowBlank:true 
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.writeoffEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.to'),//至
		format:'Y-m-d',
		allowBlank:true 
	},{
		xtype:'combo',
		name:'noteVo.noteApplyDto.status',
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.status'),//单据状态
		value:'',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore(
				'NOTE_APPLICATION__STATUS', null, {
					'valueCode' : '',
					'valueName' : consumer.noteWriteoff.i18n('foss.stl.consumer.common.all')
				})
	},{
		xtype:'combo',
		name:'noteVo.noteApplyDto.writeoffStatus',
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffStatus'),//核销状态
		value:'',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore(
				'NOTE_APPLICATION__WRITEOFF_STATUS', null, {
					'valueCode' : '',
					'valueName' : consumer.noteWriteoff.i18n('foss.stl.consumer.common.all')
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
			  text:consumer.noteWriteoff.i18n('foss.stl.consumer.common.reset'),  
			  columnWidth : .12,
			  handler:consumer.note.reset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.76
			},
		  	{
				 text:consumer.noteWriteoff.i18n('foss.stl.consumer.common.query'),
				cls:'yellow_button', 
				columnWidth : .12,
				handler:consumer.noteWriteoff.refReshGrid
		  	}
		]
	}]  
}); 
//票据起始与结束号 Model
Ext.define('Foss.note.NoteBeginAndEndModel', {
	extend :'Ext.data.Model', 
	fields :[  
	{	
		name :'id',  //申请部门ID
		type :'int'
	},{	
		name :'beginNo',  //申请部门ID
		type :'string'
	},{	
		name :'endNo',  //申请部门编码
		type :'string'
	} ]
		}); 
//票据起始与结束 Store
Ext.define('Foss.note.NoteBeginAndEndStore',{
	extend:'Ext.data.Store',
	model:'Foss.note.NoteBeginAndEndModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : consumer.realPath('queryStockIn.action'),
		reader : {
			type : 'json',
			root : 'noteVo.noteStockInEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//编号范围 Grid
Ext.define('Foss.note.NoteStockInNumGrid',{
  extend: 'Ext.grid.Panel', 
  columns: [ 
	 { header:consumer.noteWriteoff.i18n('foss.stl.consumer.note.start'), dataIndex :'beginNo',flex:.4},
	 { header:consumer.noteWriteoff.i18n('foss.stl.consumer.note.end'), dataIndex :'endNo',flex:.4} 
   ], 
  width: 450,
  height: 250,
  title: consumer.noteWriteoff.i18n('foss.stl.consumer.note.numberRange'),//编号范围
  frame: true,
  constructor:function(config){
	var me = this, 
	cfg = Ext.apply({}, config); 
	me.store=Ext.create('Foss.note.NoteBeginAndEndStore');
	
	me.callParent([ cfg ]);
  } 
});

//核销小票单据  WIN Form 
Ext.define('Foss.note.WriteoffNoteForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteWriteoff.i18n('foss.stl.consumer.note.writeoffNotePage'),//核销小票单据页面
	frame:true,
	collapsible:false,
	animcollapse:true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 85,
		colspan : 1 
	},
	noteStockInNumGrid:null,
	getNoteStockInNumGrid:function(){
		if(Ext.isEmpty(this.noteStockInNumGrid)){
			this.noteStockInNumGrid = Ext.create('Foss.note.NoteStockInNumGrid');  
		}
		return this.noteStockInNumGrid;
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 2
	},	
	constructor: function(config){
  		var me = this,cfg = Ext.apply({}, config);
  		me.items=[{
  		   		name: 'id',
   		fieldLabel: 'ID',
   		hidden:true
   	},{ 
		name: 'applyOrgName',
		fieldLabel: consumer.noteWriteoff.i18n('foss.stl.consumer.note.useOrgName'), //使用部门
		readOnly:'true',
		columnWidth: .5
   	},{
   		name: 'applyAmount',
   		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.pinAmount'), //销号数量
   		readOnly:'true',
   		columnWidth: .5
   	},{
		xtype:'datefield',
		name:'applyTime', 
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyTime'),//申请时间
		format:'Y-m-d H:i:s', 
		readOnly:true,
		renderer:function(value){
 			return stl.dateFormat(value,stl.FORMAT_TIME);
 		}
	},{
		name:'applyUserName',
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.applyUserName'),//申请人
		columnWidth:.5,
		readOnly:true
	},{
		name:'issuedUserName',
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.issuedUserName'),//下发人
		columnWidth:.5,
		readOnly:'true' 
	},{
		name:'issuedOrgName',
		fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.issuedOrgName'),//下发部门
		columnWidth:.5,
		readOnly:'true' 
	},{
		xtype:'container', 
		width:450, 
   		colspan:2,
   		columnWidth:1,
		layout:'column', 
		items:[me.getNoteStockInNumGrid()]
	  },{ 
		xtype : 'textareafield', 
	    fieldLabel:consumer.noteWriteoff.i18n('foss.stl.consumer.note.Note'), //备注
	    name : 'writeoffNotes',
	    width:450,
   		columnWidth:1,
		colspan:2
	},{
		border: 1,
		xtype:'container',
		width:450,
		columnWidth:1,
		height:40,
		colspan:2,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:consumer.noteWriteoff.i18n('foss.stl.consumer.common.cancel'),  //取消
			  columnWidth : .18,
			  handler:function(){  
				  consumer.noteWriteoff.writeoffNoteWindow.hide(); 
			  	}
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.63
			},
		  	{
				text:consumer.noteWriteoff.i18n('foss.stl.consumer.note.confirmWriteoff'),//确认核销
				cls:'yellow_button', 
				columnWidth : .18,
				handler:function(){
		  			consumer.noteWriteoff.writeoffOperator(me);
		  		  }
		  	}
		]
	}];
	me.callParent([cfg]);
  }
});


//核销小票单据  Window
Ext.define('Foss.note.WriteoffNoteWindow',{
  extend: 'Ext.Window', 
  closeAction: 'hide',
  title:'',
  x:320,
  y:150,
  modal:true,
  width:'autoWidth',
  height:'autoHeight',
  resizable:false,
  //layout: 'fit',
  layout : 'column',
  writeoffNoteForm:null,
  getWriteoffNoteForm:function(){  
  	if(this.writeoffNoteForm==null){
  		this.writeoffNoteForm=Ext.create('Foss.note.WriteoffNoteForm');
  	}
  	return this.writeoffNoteForm;
  }, 
  constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getWriteoffNoteForm()];
		me.callParent([cfg]);
  }
}); 

consumer.noteWriteoff.writeoffNoteWindow=Ext.create('Foss.note.WriteoffNoteWindow');

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
	  		  text:consumer.noteWriteoff.i18n('foss.stl.consumer.common.close'),  //关闭
	  		  columnWidth : .25,
	  		  handler:function(){  
	  			  consumer.noteWriteoff.noteQueryDetailsWindow.hide(); 
	  		   }
	  		  }];
		me.items = [me.getNoteQueryDetailsGrid()];
		me.callParent([cfg]);
    }
}); 

consumer.noteWriteoff.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_consumer-initWriteoff_content')) {
		return;
	} 
	var queryForm = Ext.create('Foss.note.WriteoffForm');//查询FORM
	var areaGrid = Ext.create('Foss.note.NoteApplyGrid');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_consumer-initWriteoff_content',
		cls:"panelContentNToolbar", //必须添加，内容定位用。
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
		renderTo : 'T_consumer-initWriteoff-body'
	}); 
});

