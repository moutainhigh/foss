
/**
 * 刷新Grid列表
 */
consumer.noteApprove.refReshGrid=function(){
	var grid = Ext.getCmp('T_consumer-initApprove_content').getAreaGrid();
	 grid.getPagingToolbar().moveFirst(); 
}
/**
 * 弹出小票单据审核操作窗口
 * @param grid 
 * @param rowIndex
 * @param colIndex
 */
consumer.noteApprove.showNoteApproveWin=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex);
	var status=record.get('status');
	var approveStatus=record.get('approveStatus');
	if(approveStatus===consumer.note.APPROVE_STATUS__NOT_AUDIT){
		grid.up('gridpanel').getApproveIssueWindow().show();
		var form=grid.up('gridpanel').getApproveIssueWindow().getApproveIssueNoteForm().getForm();
		form.loadRecord(record);
		var applyTime=stl.dateFormat(record.get('applyTime'),stl.FORMAT_TIME);
		form.findField('applyTime').setValue(applyTime); 
	}else{
		Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApprove.i18n('foss.stl.consumer.note.approved.cannotApproveAgain'));
		return false;
	}
}
/**
 * 审核操作
 */
consumer.noteApprove.noteApproveOperator=function(){
	var form = this.up('form').getForm();    
	var id=form.findField("id").getValue();
	var approveStatus=form.findField("approveStatus").getValue();
	var approveNotes=form.findField("approveNotes").getValue();
	if(!Ext.isEmpty(approveStatus) && approveStatus.approveResult!=consumer.note.APPROVE_STATUS__REFUND_DISAGREE && approveStatus.approveResult!=consumer.note.APPROVE_STATUS__REFUND_AGREE){
		Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseSelectApproveStatus'));
		return false;
	}
	if(approveStatus.approveResult==consumer.note.APPROVE_STATUS__REFUND_DISAGREE && Ext.isEmpty(approveNotes)){
		Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseEnterNote'));
		return false;
	}  
	 
	var params = {
			"noteVo.noteApplyDto.id":id,
			"noteVo.noteApplyDto.approveStatus":approveStatus,
			"noteVo.noteApplyDto.approveNotes":approveNotes 
		};  
	Ext.Ajax.request({
		url:consumer.realPath('updateApprove.action'),
		params:params,
		success : function(response) { 
			var json = Ext.decode(response.responseText); 
			Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), json.message); 
			consumer.noteApprove.approveIssueWindow.hide(); 
			var grid = Ext.getCmp('T_consumer-initApprove_content').getAreaGrid();
			grid.getPagingToolbar().moveFirst(); 
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), json.message);
		}
	}); 
}
/**
 * 弹出小票单据下发操作窗口 
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
consumer.noteApprove.showNoteIssueWin=function(grid,rowIndex,colIndex){
	var record=grid.getStore().getAt(rowIndex);
	var approveStatus=record.get('approveStatus');
	var status=record.get('status');
	if(approveStatus===consumer.note.APPROVE_STATUS__REFUND_AGREE && status==consumer.note.STATUS__SUBMIT){ //审核通过且单据状态为已提交时才能下发
		grid.up('gridpanel').getIssueNoteWindow().show();
		grid.up('gridpanel').getIssueNoteWindow().getIssueNoteForm().getForm().loadRecord(record);	
	}else{
		if(approveStatus==consumer.note.APPROVE_STATUS__NOT_AUDIT){
			
			//未审核不能进行下发
			Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApprove.i18n('foss.stl.consumer.note.notApprove.cannotDistribute'));
			return false;
		}
		if(approveStatus==consumer.note.APPROVE_STATUS__REFUND_DISAGREE){
			
			//审核未通过不能进行下发
			Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApprove.i18n('foss.stl.consumer.note.notAudit.cannotDistribute'));
			return;
		}
		if(status==consumer.note.STATUS__DISTRIBUTE){
			
			//已下发不能再进行下发
			Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApprove.i18n('foss.stl.consumer.note.distributed.cannotDistributeAgain'));
			return false;
		} 
		if(status==consumer.note.STATUS__IN){
			
			//已入库不能再进行下发
			Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApprove.i18n('foss.stl.consumer.note.storaged.cannotDistribute'));
			return false;
		}
	}				
}

/**
 * 小票单据下发操作
 * @param me
 */
consumer.noteApprove.noteIssueOperator= function(button,me){
	var formData=me.getRecord().data;//获取grid传入的数据
	var store=me.getNoteStockInNumGrid().getStore();
	
	var form = me.getForm();
	
	var applyAmount=form.findField('applyAmount').getValue();
	var applyAmountTotal=applyAmount*consumer.note.NOTENO;
	
	var curStoreAmountTotal=0;
	store.each(function(record){
		var rec_beginNo=record.data.beginNo*1;
		var rec_endNo=record.data.endNo*1;
		var rec_sub=rec_endNo-rec_beginNo;
		curStoreAmountTotal=curStoreAmountTotal+rec_sub+1;
	});
	if(curStoreAmountTotal==0){
		
		//请输入下发编号
		Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseEnterDistributeNo'));
      	return false;
	}
	if(curStoreAmountTotal != applyAmountTotal){
		
		//录入的下发数量与申请数量不符,请确认后再下发
      	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.amountNotRight.pleaseCheck'));
      	return false;
     } 
	
	var id=form.findField('id').getValue();
	var issuedType=form.findField('issuedType').getValue();
	var expressDeliveryNumber=form.findField('expressDeliveryNumber').getValue();
	var noteStockInDto= Ext.merge(formData);
	noteStockInDto.id=id;
	noteStockInDto.issuedType=issuedType;
	noteStockInDto.expressDeliveryNumber=expressDeliveryNumber; 

	var jsonData = new Array();
	me.getNoteStockInNumGrid().store.each(function(record){
		jsonData.push(record.data);
	});
	//设置该按钮灰掉
	button.disable(false);
	setTimeout(function() {
		button.enable(true);
	}, 10000);
	noteStockInDto.stockInList = jsonData;
		Ext.Ajax.request({
		url : consumer.realPath('addStockInNote.action'),
		actionMethods:'post',
		jsonData:{'noteVo':{'noteStockInDto':noteStockInDto}},
		success:function(response){
			var json = Ext.decode(response.responseText); 
			Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), json.message);
			consumer.noteApprove.issueNoteWindow.hide(); 
			var grid = Ext.getCmp('T_consumer-initApprove_content').getAreaGrid();
			grid.getPagingToolbar().moveFirst(); 
			
		},
		exception:function(response){
			var json = Ext.decode(response.responseText); 
			top.Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), json.message);
		}
	}); 
}
/**
 * 弹出小票单据明细操作窗口
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
consumer.noteApprove.showQueryDetailsWin=function(grid,rowIndex,colIndex){
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
		Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApprove.i18n('foss.stl.consumer.note.notDistribute.cannotShowDetail'));
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
		name :'applyTime',   //申请时间
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
				name :'beginWithEndNo', //起始编号
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
	//autoDestroy: true,
	data:[
			
		]
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
		url :consumer.realPath('queryApply.action'),
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
consumer.noteApprove.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 1
});
//编号范围 Grid
Ext.define('Foss.note.NoteStockInNumGrid',{
	extend: 'Ext.grid.Panel', 
    columns: [
	 { header:consumer.noteApprove.i18n('foss.stl.consumer.common.serialNo'), dataIndex :'id',flex:1},//序号
	 {
	        xtype:'actioncolumn',
			text: consumer.noteApprove.i18n('foss.stl.consumer.common.actionColumn'),//操作列
			align: 'center',
			flex:1,
	        items: [{
	        	iconCls:'deppon_icons_delete',
	            tooltip: consumer.noteApprove.i18n('foss.stl.consumer.common.delete'),//删除
	            handler: function(grid, rowIndex, colIndex) {
	                grid.getStore().removeAt(rowIndex);
	            }
	        }]
	 },
     { header:consumer.noteApprove.i18n('foss.stl.consumer.note.start'), dataIndex :'beginNo',flex:2,editor:{//起始
         xtype: 'textfield',
         allowBlank: false,
         emptyText:'请输入8位数字',
         regex:/^[0-9]{8}$/,
         regexText:'请输入8位数字',
         listeners:{
        	 'blur':function(){
        		 value = this.getValue();
        		 if(!Ext.isEmpty(value) && !this.regex.test(value)){
        			 Ext.Msg.alert('提示',this.regexText);
        		 }
        	 }
         }
     }},
     { header:consumer.noteApprove.i18n('foss.stl.consumer.note.end'), dataIndex :'endNo',flex:2,editor: {//终止
         xtype: 'textfield',
         allowBlank: false,
         emptyText:'请输入8位数字',
         regex:/^[0-9]{8}$/,
         regexText:'请输入8位数字',
         listeners:{
        	 'blur':function(){
        		 value = this.getValue();
        		 if(!Ext.isEmpty(value) && !this.regex.test(value)){
        			 Ext.Msg.alert('提示',this.regexText);
        		 }
        	 }
         }
     }} 
     ],
    selModel: {
        selType: 'cellmodel'
    }, 
    width: 450,
    height: 250,
    title: consumer.noteApprove.i18n('foss.stl.consumer.note.numberRange'),//编号范围
    frame: true,
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config); 
		me.store=Ext.create('Foss.note.NoteBeginAndEndStore');
	    me.tbar=[{
	        text: consumer.noteApprove.i18n('foss.stl.consumer.note.addNumber'),//新增编号
	        handler : function(){
	            var r = Ext.create('Foss.note.NoteBeginAndEndModel', {
	            	id: me.store.getCount()+1,
	            	beginNo: '',//起始编号
	            	endNo: ''//终止编号
	            });
	            var count=me.store.count();
	            if(count!=0){
	            	var applyAmount= me.up('form').getForm().findField('applyAmount').getValue();
		            var beginNo=me.store.getAt(0).data.beginNo;
		            var endNo=me.store.getAt(0).data.endNo;
		            if(beginNo==consumer.noteApprove.i18n('foss.stl.consumer.note.startNumber')){//起始编号
		            	
		            	//请输入起始编号
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseEnterStartNumber'));
		            	return false;
		            }
		            
		            //终止编号
		            if(endNo==consumer.noteApprove.i18n('foss.stl.consumer.note.endNumber')){
		            	
		            	//请输入终止编号
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseEnterEndNumber'));
		            	return false;
		            }
		            beginNo =Ext.Number.from(beginNo,1);
		            endNo =Ext.Number.from(endNo,1);
		            applyAmount=Ext.Number.from(applyAmount);
		            var applyAmountTotal=applyAmount*consumer.note.NOTENO;
		            
		            if(beginNo==endNo){
		            	
		            	//起始编号等于终止编号,请重新输入!
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.startNoEqEndNo.pleaseTryAgain'));
		            	return false;
		            }
		            if(beginNo>endNo){
		            	
		            	//起始编号大于编号,请重新输入
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.startNoGtEndNo.pleaseTryAgain'));
		            	return false;
		            }
		            
		            if(beginNo!=1 && (beginNo-1)%50!=0){
		            	
		            	//请输入正确的起始编号
//		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseEnterCorrectStartNo'));
		            	Ext.MessageBox.alert("温馨提示","已输入申请编号不可再次新增");
		            	return false;
		            }
		            
		            if(endNo%50!=0){
		            	
		            	//请输入正确的终止编号
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseEnterCorrentEndNo'));
		            	return false;
		            }
		            
		            if((beginNo+endNo-1)%50!=0){
		            	
		            	//输入的起止编号范围不正确，请重新填写
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.startEndNoRangeNotCorrent.pleaseTryAgain'));
		            	return false;
		            }
		            var curStoreAmountTotal=0;
		            me.store.each(function(record){
		            	rec_beginNo=record.data.beginNo*1;
		            	rec_endNo=record.data.endNo*1;
		            	rec_sub=rec_endNo-rec_beginNo;
						curStoreAmountTotal=curStoreAmountTotal+rec_sub+1;
					});
		            if(curStoreAmountTotal==applyAmountTotal){
		            	
		            	//录入的下发单据数量等于申请数量,不能再新增
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.issuedAmountEqApplyAmount.cannotIncrease'));
		            	return false;
		            }
		            if(curStoreAmountTotal>applyAmountTotal){
		            	
		            	//录入的下发单据数量已大于申请数量,不能再新增
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.issuedAmountGtApplyAmount.cannotIncrease'));
		            	return false;
		            }
		            if(count+1>applyAmount){
		            	
		            	//新增行数不能大于申请数量
		            	Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.newLinesCannotGtApplyAmount'));
		            	return false;
		            }
		            
	        	}
	            me.store.insert(0, r);
	            consumer.noteApprove.cellEditing.startEditByPosition({row:0, column: 1});
	           
	            
	        }
	    }];
		me.callParent([ cfg ]);
	} ,
    plugins: [consumer.noteApprove.cellEditing]
});


//票据申请Grid
Ext.define('Foss.note.NoteApplyGrid',{
	extend: 'Ext.grid.Panel',
	title: consumer.noteApprove.i18n('foss.stl.consumer.note.applyGridTitle'),//申请小票单据列表
	columnWidth: 1,
	stripeRows: true,
    columnLines: true,
	collapsible: false,
    bodyCls: 'autoHeight',
    frame: true,
    //增加表格列的分割线
	cls: 'autoHeight', 
	store : null,
	autoScroll : true,
	height: 650,
    emptyText: consumer.noteApprove.i18n('foss.stl.consumer.common.emptyText'),//查询结果为空
	//分页
	pagingToolbar:null,
	viewConfig:{
	    	enableTextSelection : true//设置行可以选择，进而可以复制    
	},
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
	getApproveIssueWindow:function(){
		if(consumer.noteApprove.approveIssueWindow==null){
			consumer.noteApprove.approveIssueWindow=Ext.create('Foss.note.ApproveIssueWindow');
		}
		return consumer.noteApprove.approveIssueWindow;
	}, 
	getIssueNoteWindow:function(){
		if(consumer.noteApprove.issueNoteWindow==null){
			consumer.noteApprove.issueNoteWindow=Ext.create('Foss.note.IssueNoteWindow');
		}
		return consumer.noteApprove.issueNoteWindow;
	},
	getNoteQueryDetailsWindow:function(){
		if(consumer.noteApprove.noteQueryDetailsWindow==null){
			consumer.noteApprove.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');
		}
		return consumer.noteApprove.noteQueryDetailsWindow;
	},
	columns : [{
		xtype:'actioncolumn',
		flex: 1,
		text:consumer.noteApprove.i18n('foss.stl.consumer.common.actionColumn'),//操作列
		align: 'center',
		items: [{
                tooltip: consumer.noteApprove.i18n('foss.stl.consumer.common.audit'),//审核
				iconCls:'foss_icons_stl_auditing',
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	consumer.noteApprove.showNoteApproveWin(grid,rowIndex,colIndex);
                },
                getClass:function(v,m,r,rowIndex){
	                if(!consumer.noteApprove.isPermission('/stl-web/consumer/updateApprove.action')){
						return 'statementBill_hide';
					}else {
						return 'foss_icons_stl_auditing';
					}
                }
            },{
                tooltip: consumer.noteApprove.i18n('foss.stl.consumer.note.issue'),//下发
				iconCls:'foss_icons_stl_sendmes',
				width:42,
                handler: function(grid, rowIndex, colIndex) {	
                	consumer.noteApprove.showNoteIssueWin(grid,rowIndex,colIndex);
                },
                getClass:function(v,m,r,rowIndex){
	                if(!consumer.noteApprove.isPermission('/stl-web/consumer/addStockInNote.action')){
						return 'statementBill_hide';
					}else {
						return 'foss_icons_stl_sendmes';
					}
                }
            },{
                tooltip: consumer.noteApprove.i18n('foss.stl.consumer.common.showDetail'),//查看明细
				iconCls:'deppon_icons_showdetail',
				width:42,
                handler: function(grid, rowIndex, colIndex) {	
                	consumer.noteApprove.showQueryDetailsWin(grid,rowIndex,colIndex);
                }
            }]
			},  
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyOrgCode'), dataIndex :'applyOrgCode' ,hidden:true},//申请部门编码
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyOrgName'), flex:1, dataIndex :'applyOrgName' ,width:200},//申请部门
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyAmount'), dataIndex :'applyAmount' ,width:100},//申请数量(本)
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.startNumber'),dataIndex:'beginNo',width:100},
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.endNumber'),dataIndex:'endNo',width:100},
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyDate'),flex:1, dataIndex :'applyTime' ,width:200,
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_DATE);
				} 
			},//申请日期
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyUserCode'), dataIndex:'applyUserCode' ,hidden:true},//申请人编码
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyUserName'), flex:1, dataIndex:'applyUserName' ,width:200},//申请人
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.active'), dataIndex:'active' ,width:100,
				renderer: consumer.note.activeRenderer
			},//是否有效
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.status'),  dataIndex:'status' ,width:100,
				renderer: consumer.note.statusRenderer
			},//单据状态
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.approveStatus'),  dataIndex:'approveStatus' ,width:100,
				renderer: consumer.note.approveStatusRenderer
			},//审核状态
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.writeoffStatus'), dataIndex:'writeoffStatus',width:100,
				renderer: consumer.note.writeoffStatusRenderer 
			}//核销状态
			],   
	constructor:function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
		
		me.store = Ext.create('Foss.note.NoteApplyStore',{
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var form = Ext.getCmp('T_consumer-initApprove_content').getQueryForm().getForm(); 
					 if(form.isValid()){
						var applyStartTime=form.findField("noteVo.noteApplyDto.applyStartTime").getValue();
						var applyEndTime=form.findField("noteVo.noteApplyDto.applyEndTime").getValue();
						var issuedStartTime=form.findField("noteVo.noteApplyDto.issuedStartTime").getValue();
						var issuedEndTime=form.findField("noteVo.noteApplyDto.issuedEndTime").getValue();
						
						if(Ext.isEmpty(applyStartTime) && !Ext.isEmpty(applyEndTime)){
							
							//请选择申请开始日期
							Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseSelectStartDate')); 
							return false;
						}else if(!Ext.isEmpty(applyStartTime) && Ext.isEmpty(applyEndTime)){
							
							//请选择申请结束日期
							Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseSelectEndDate')); 
							return false;
						}
						if(Ext.isEmpty(issuedStartTime) && !Ext.isEmpty(issuedEndTime)){
							
							//请选择下发开始日期
							Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseSelectIssuedStartDate')); 
							return false;
						}else if(!Ext.isEmpty(issuedStartTime) && Ext.isEmpty(issuedEndTime)){
							
							//请选择下发结束日期
							Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseSelectIssuedEndDate')); 
							return false;
						}
						if(!applyStartTime && !applyEndTime && !issuedStartTime && !issuedEndTime){
							
							//申请日期与下发日期至少选择一个
							Ext.MessageBox.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApprove.i18n('foss.stl.consumer.note.dateOfIssueAndApplyAtLeastSelectOne')); 
							return false;
						}
						
						//申请
						var result1=consumer.note.validateDateDiff(applyStartTime,applyEndTime,consumer.note.DATELIMITDAYS,consumer.noteApprove.i18n('foss.stl.consumer.note.apply'));
				    	if(result1){
				    		return false;
				    	}
				    	
				    	//下发
				    	var result2=consumer.note.validateDateDiff(issuedStartTime,issuedStartTime,consumer.note.DATELIMITDAYS,consumer.noteApprove.i18n('foss.stl.consumer.note.issue'));
						if(result2){
							return false;
						}
						
						
						var params=form.getValues();
						Ext.apply(params,{
								'noteVo.queryPage':consumer.note.QUERY_PAGE__ISSUE
							});	
						Ext.apply(operation,{
							params :params
						});	
					}else{
						
						//请检查输入条件是否合法
						Ext.Msg.alert(consumer.noteApprove.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApprove.i18n('foss.stl.consumer.note.pleaseCheckConditionLegal'));
						return false;
					}
					
				  }
				}
		    });
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store; 
		me.callParent([ cfg ]);
	} 
});


//小票单据明细Grid
Ext.define('Foss.note.NoteQueryDetailsGrid',{
	extend: 'Ext.grid.Panel',
	title: consumer.noteApprove.i18n('foss.stl.consumer.note.noteDetailList'),//小票单据详细列表
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
		if(consumer.noteApprove.noteQueryDetailsWindow==null){
			consumer.noteApprove.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');
		}
		return consumer.noteApprove.noteQueryDetailsWindow;
	},
	columns : [ 
			{ text:'ID', dataIndex :'noteDetailsId' ,hidden:true},
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.noteNumber'), dataIndex :'detailsNo' ,width:120},//小票单据编号
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyOrgCode'),   dataIndex :'applyOrgCode',hidden:true},//申请部门编码
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyOrgName'),   dataIndex :'applyOrgName' ,width:130},//申请部门
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.applyTime'), dataIndex :'applyTime' ,width:150,
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},//申请时间
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.beginWithEndNo'), dataIndex :'beginWithEndNo' ,width:100},//下发起止号
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.issuedUserCode'), dataIndex:'issuedUserCode' ,hidden:true},//下发人编码
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.issuedUserName'), dataIndex:'issuedUserName' ,width:120},//下发人
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.issuedTime'), dataIndex :'issuedTime' ,width:150,
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},//下发时间
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.storageTime'), dataIndex :'storageTime' ,width:150,
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				} 
			},//入库时间
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.storageUserCode'), dataIndex:'storageUserCode' ,hidden:true},//入库操作人编码
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.storageUserName'), dataIndex:'storageUserName' ,width:120},//入库操作人
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.writeoffTime'), dataIndex :'writeoffTime' ,width:150,
				renderer:function(value){
					return stl.dateFormat(value,stl.FORMAT_TIME);
				}
			},//核销时间
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.writeoffUserCode'), dataIndex:'writeoffUserCode' ,hidden:true},//核销人编码
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.writeoffUserName'), dataIndex:'writeoffUserName' ,width:120},//核销人
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.status'),  dataIndex:'status' ,width:100,
				renderer: consumer.note.statusRenderer 
			},//单据状态
			{ text:consumer.noteApprove.i18n('foss.stl.consumer.note.writeoffStatus'), dataIndex:'writeoffStatus',width:100,
				renderer: consumer.note.writeoffStatusRenderer 
			}//核销状态
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

//审核发放小票单据管理 FORM
Ext.define('Foss.note.ApproveIssueForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteApprove.i18n('foss.stl.consumer.note.aduitIssuedNoteManage'),//审核发放小票单据管理
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
	items:[
		{
			xtype : 'dynamicorgcombselector', 
			fieldLabel : consumer.noteApprove.i18n('foss.stl.consumer.note.applyOrgName'),//申请部门
			name:'noteVo.noteApplyDto.applyOrgCode',
			type : 'ORG' 
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyStartTime', 
		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.applyDate'),//申请日期
		format:'Y-m-d',
		//value:stl.dateFormat(stl.getTargetDate(new Date(),-30),stl.FORMAT_DATE)
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE)
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.to'),//至
		format:'Y-m-d',
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE) 
	},{
		xtype : 'commonemployeeselector',
		name: 'noteVo.noteApplyDto.applyUserCode',
		fieldLabel : consumer.noteApprove.i18n('foss.stl.consumer.note.applyUserName') //申请人
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.issuedStartTime', 
		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.issuedDate'),//下发日期
		format:'Y-m-d',
		columnWidth:.25 
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.issuedEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.to'),//至
		format:'Y-m-d',
		columnWidth:.25 
	},{
		xtype:'combo',
		name:'noteVo.noteApplyDto.status',
		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.status'),//单据状态
		columnWidth:.25, 
		value:'',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore(
				'NOTE_APPLICATION__STATUS', null, {
					'valueCode' : '',
					'valueName' : consumer.noteApprove.i18n('foss.stl.consumer.common.all')
				})
	},{
		xtype:'combo',
		name:'noteVo.noteApplyDto.approveStatus',
		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.approveStatus'),//审核状态
		columnWidth:.25, 
		value:'',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore(
				'NOTE_APPLICATION__APPROVE_STATUS', null, {
					'valueCode' : '',
					'valueName' : consumer.noteApprove.i18n('foss.stl.consumer.common.all')
				})
	}, {
		xtype :'container',
		border :false,
		columnWidth :.25,
		html :'&nbsp;'
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		colspan:3,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:consumer.noteApprove.i18n('foss.stl.consumer.common.reset'),//重置   
			  columnWidth:.12,
			  handler:consumer.note.reset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.76
			},
		  	{
			  text:consumer.noteApprove.i18n('foss.stl.consumer.common.query'),//查询
			  columnWidth:.12,
			  cls:'yellow_button',  
			  handler:consumer.noteApprove.refReshGrid
		  	}]
	}]  
}); 
//win  审核下发小票单据 FORM
Ext.define('Foss.note.ApproveIssueNoteForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteApprove.i18n('foss.stl.consumer.note.aduitIssuedNote'),//审核下发小票单据
	frame:true,
	collapsible:false,
	animcollapse:true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 2
	},
  items: [{
   		name: 'id',
 		fieldLabel: 'ID',
 		hidden:true
 	},{ 
 		name:'applyOrgName',
 		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.applyOrgName'),//申请部门
 		readOnly:'true'
 	},{
 		name: 'applyAmount',
 		fieldLabel: consumer.noteApprove.i18n('foss.stl.consumer.note.applyAmount'),//申请数量
 		readOnly:'true',
 		columnWidth: .5
 	},{ 
 		name:'applyTime',  
 		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.applyTime'),//申请时间
 		readOnly:true 
 	},{
 		name: 'applyUserName',
 		fieldLabel: consumer.noteApprove.i18n('foss.stl.consumer.note.applyUserName'),//申请人
 		readOnly:'true',
 		columnWidth: .5
 	},{
 		xtype:'radiogroup',
 		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.approveStatus'),//审核状态
 		name:'approveStatus',
 		colspan:2,
 		columnWidth:1,
 		allowBlank:true,
 		defaultType:'radio',
 		layout:'table',
 		isFormField: true,
 		defaults:{
 			width:100
 		},
 		items:[{
 			boxLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.pass'),//通过
 			name:'approveResult',
 			inputValue:consumer.note.APPROVE_STATUS__REFUND_AGREE,
 			listeners:{
					change:function(_this, newValue, oldValue, eOpts){
						if(newValue){
							_this.up('form').getForm().findField('approveNotes').setVisible(false);
						}else{
							_this.up('form').getForm().findField('approveNotes').setVisible(true);
						}
					}
 			}
 		},{
 			boxLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.notPass'),//不通过
 			name:'approveResult',
 			inputValue:consumer.note.APPROVE_STATUS__REFUND_DISAGREE
 		}]
 	  },{
 			fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.Note'), //备注
 			name:'approveNotes',
 			width:490,
 			xtype:'textareafield', 
 			colspan:2
 	  },{
 			border: 1,
 			xtype:'container',
 			width:520,
 			height:40,
 			colspan:2,
 			defaultType:'button',
 			layout:'column',
 			items:[{
 				  text:consumer.noteApprove.i18n('foss.stl.consumer.common.cancel'),//取消
 				  columnWidth : .15,
 				  handler: function(){
 					  consumer.noteApprove.approveIssueWindow.hide();
 				  }
 			  	},{
 					xtype:'container',
 					border:false,
 					html:'&nbsp;',
 					columnWidth:.7
 				},
 			  	{
 					text:consumer.noteApprove.i18n('foss.stl.consumer.common.OK'),//确定
 					cls:'yellow_button', 
 					columnWidth : .15,
 					handler: consumer.noteApprove.noteApproveOperator
 			  	}]
 			}] 

});
//审核下发小票单据 Window
Ext.define('Foss.note.ApproveIssueWindow',{
		extend: 'Ext.Window', 
		closeAction: 'destroy',
		title:'',
		x:450,
		y:300,
		modal:true,
		height: 'autoHeight',
        width: 'autoWidth',
        resizable:false,
        //layout: 'fit',
        layout : 'column',
        approveIssueNoteForm:null,
        getApproveIssueNoteForm:function(){  
        	if(this.approveIssueNoteForm==null){
        		this.approveIssueNoteForm=Ext.create('Foss.note.ApproveIssueNoteForm');
        	}
        	return this.approveIssueNoteForm;
        }, 
        listeners:{
        	beforeshow:function(me){
        		me.getApproveIssueNoteForm().getForm().reset();
        	}
        },
        constructor: function(config){
    		var me = this,
			cfg = Ext.apply({}, config);
    		me.items = [me.getApproveIssueNoteForm()];
    		me.callParent([cfg]);
        }
	}); 
consumer.noteApprove.approveIssueWindow=Ext.create('Foss.note.ApproveIssueWindow');

//win  小票单据 下发操作 FORM
Ext.define('Foss.note.IssueNoteForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteApprove.i18n('foss.stl.consumer.note.noteIssueAction'),//小票单据下发操作
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
	  		   		name:'applyOrgName',
	  		   		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.applyOrgName'),//申请部门
	  		   		columnWidth:.5, 
	  		   		readOnly:'true'
	  		   	},{
	  		   		name: 'applyAmount',
	  		   		fieldLabel: consumer.noteApprove.i18n('foss.stl.consumer.note.applyAmounts'), //申请数量
	  		   		readOnly:'true',
	  		   		columnWidth: .5
	  		   	},{
	  				xtype:'container', 
	  				width:450, 
	  		   		colspan:2,
	  		   		columnWidth:1,
	  				layout:'column', 
	  				items:[me.getNoteStockInNumGrid()]
	  			  },{ 
	  		   		xtype:'combo',
	  		   		name:'issuedType',
	  		   		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.issuedType'),//下发方式
	  		   		columnWidth:.5, 
	  		   		value:'', 
	  		   		displayField:'valueName',
	  		   		valueField:'valueCode',
	  		   		queryMode:'local',
	  		   		triggerAction:'all',
	  				editable:false,
	  		   		store:FossDataDictionary.getDataDictionaryStore(
							'NOTE_STOCK_IN__ISSUED_TYPE', null, {
								'valueCode' : '',
								'valueName' : consumer.noteApprove.i18n('foss.stl.consumer.common.all')
							})
	  		   	},{
	  		   		name: 'expressDeliveryNumber',
	  		   		fieldLabel:consumer.noteApprove.i18n('foss.stl.consumer.note.expressDeliveryNumber'), //快递代理单号
	  		   		maxLength:49,
	  		   		columnWidth: .5
	  		   	},{
	  				border: 1,
	  				xtype:'container',
	  				width:490,
	  				height:40,
	  				colspan:2,
	  				defaultType:'button',
	  				layout:'column',
	  				items:[{
	  					  text:consumer.noteApprove.i18n('foss.stl.consumer.common.cancel'), // 取消
	  					  columnWidth : .15,
	  					  handler:function(){  
	  			  			  consumer.noteApprove.issueNoteWindow.hide(); 
	  		  		  		}
	  				  	},{
	  						xtype:'container',
	  						border:false,
	  						html:'&nbsp;',
	  						columnWidth:.7
	  					},
	  				  	{
	  						text:consumer.noteApprove.i18n('foss.stl.consumer.note.confirmedIssue'),//确认下发
	  						cls:'yellow_button', 
	  						columnWidth : .15,
	  						handler:function(){
	  							var button = this;
	  			  			  consumer.noteApprove.noteIssueOperator(button,me); 
	  			  		  	}
	  				  	}]
	  				}];
	  		me.callParent([cfg]);
	      }
});
//小票单据下发操作  Window
Ext.define('Foss.note.IssueNoteWindow',{
	  extend: 'Ext.Window', 
	  closeAction: 'hide',
	  title:'',
	  x:450,
	  y:300,
	  modal:true,
	  height: 'autoHeight',
	  width: 'autoWidth',
	  resizable:false,
	  //layout: 'fit',
	  layout : 'column',
	  issueNoteForm:null,
	  getIssueNoteForm:function(){  
	  	if(this.issueNoteForm==null){
	  		this.issueNoteForm=Ext.create('Foss.note.IssueNoteForm');
	  	}
	  	return this.issueNoteForm;
	  }, 
      listeners:{
      	beforeshow:function(me){
      		me.getIssueNoteForm().getForm().reset();
      		me.getIssueNoteForm().getNoteStockInNumGrid().store.removeAll();
      	}
      }, 
      constructor: function(config){
  		var me = this,
			cfg = Ext.apply({}, config);
  		me.items = [me.getIssueNoteForm()];
  		me.callParent([cfg]);
      }
	}); 
consumer.noteApprove.issueNoteWindow=Ext.create('Foss.note.IssueNoteWindow');

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
	  		  text:consumer.noteApprove.i18n('foss.stl.consumer.common.close'), //关闭 
	  		  columnWidth : .25,
	  		  handler:function(){  
	  			  consumer.noteApprove.noteQueryDetailsWindow.hide(); 
	  		   }
	  		  }];
		me.items = [me.getNoteQueryDetailsGrid()];
		me.callParent([cfg]);
    }
}); 

consumer.noteApprove.noteQueryDetailsWindow=Ext.create('Foss.note.NoteQueryDetailsWindow');

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_consumer-initApprove_content')) {
		return;
	} 
	var queryForm = Ext.create('Foss.note.ApproveIssueForm');//查询FORM
	var areaGrid = Ext.create('Foss.note.NoteApplyGrid');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_consumer-initApprove_content',
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
		renderTo : 'T_consumer-initApprove-body'
	}); 
});

