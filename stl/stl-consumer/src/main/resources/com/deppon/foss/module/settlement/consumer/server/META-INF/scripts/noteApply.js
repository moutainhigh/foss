/**
 * 小票核销申请Grid列上的申请核销操作
 * @param grid
 * @param rowIndex
 * @param colIndex
 */
consumer.noteApply.appWriteoff=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex)
	var status=record.get('status');
	var writeoffStatus =record.get('writeoffStatus');
	var id = record.get('id');
	/**
	 * 当单据状态为"已提交"，核销状态为未核销时才能进行申请核销操作
	 */
	if(status==consumer.note.STATUS__IN && writeoffStatus==consumer.note.WRITEOFF_STATUS__NOT_WRITEOFF){
		Ext.MessageBox.buttonText.yes = consumer.noteApply.i18n('foss.stl.consumer.common.OK');  
		Ext.MessageBox.buttonText.no = consumer.noteApply.i18n('foss.stl.consumer.common.cancel');  
		Ext.Msg.confirm( consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.note.confirmApplySelectedRecord'), function(btn,text){
			if(btn == 'yes'){	
				var noteAppIds=[];
				noteAppIds.push(id);
				Ext.Ajax.request({
					url:consumer.realPath('applyWriteoff.action'),
					params:{
						"noteVo.noteApplyDto.noteAppIds":noteAppIds ,
						"noteVo.noteApplyDto.writeoffStatus":consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF 
					},
					success : function(response) { 
					  var json = Ext.decode(response.responseText); 
					  Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.note.applySuccess')); 
					  var grid = Ext.getCmp('T_consumer-initApply_content').getAreaGrid();
						grid.getPagingToolbar().moveFirst();
					},
					exception : function(response) {
					  var json = Ext.decode(response.responseText);
					  Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), json.message);
					}
				});
			}
		});
	
	}else{
		if(status !=consumer.note.STATUS__IN){
			Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApply.i18n('foss.stl.consumer.note.notStorage.cannotApplyWriteoff'));	
		}else if(writeoffStatus !=consumer.note.WRITEOFF_STATUS__NOT_WRITEOFF){  
			Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApply.i18n('foss.stl.consumer.note.applied.cannotApplyWriteoffAgain'));
		}else{
			Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApply.i18n('foss.stl.consumer.note.wrongMessage.tryAgain'));
		}
	}
} 


/**
 * 小票申请核销收回申请操作
 * @returns {Boolean}
 */
consumer.noteApply.reAppWriteoff=function(grid, rowIndex, colIndex){
	var record=grid.getStore().getAt(rowIndex)
	var writeoffStatus=record.get('writeoffStatus');
	var id = record.get('id');
	/**
	 * 当单据状态为"已提交"，核销状态为未核销时才能进行申请核销操作
	 */
	if(writeoffStatus==consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF){
		Ext.MessageBox.buttonText.yes = consumer.noteApply.i18n('foss.stl.consumer.common.OK');  
		Ext.MessageBox.buttonText.no = consumer.noteApply.i18n('foss.stl.consumer.common.cancel');  
		Ext.Msg.confirm( consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.otherRevenue.confirmReWriteoffApplySelectedRecord'), function(btn,text){
			if(btn == 'yes'){	
				var noteAppIds=[];
				noteAppIds.push(id);
				Ext.Ajax.request({
					url:consumer.realPath('reApplyWriteoff.action'),
					params:{
						"noteVo.noteApplyDto.noteAppIds":noteAppIds ,
						"noteVo.noteApplyDto.writeoffStatus":consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF
					},
					success : function(response) { 
					  var json = Ext.decode(response.responseText); 
					  Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.note.reApplySuccess')); 
					  var grid = Ext.getCmp('T_consumer-initApply_content').getAreaGrid();
						grid.getPagingToolbar().moveFirst();
					},
					exception : function(response) {
					  var json = Ext.decode(response.responseText);
					  Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), json.message);
					}
				});
			}
		});
	
	}else{
		if(writeoffStatus !=consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF){
			Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApply.i18n('foss.stl.consumer.otherRevenue.cannotApplyReWriteoff'));	
		}else{
			Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApply.i18n('foss.stl.consumer.note.wrongMessage.tryAgain'));
		}
	}
} 
	



/**
 * 小票单据申请
 */
consumer.noteApply.applyNote=function(){
	var form = this.up('form').getForm(); 
	var me=this;
	//设置该按钮灰掉
	me.disable(false);
	
	setTimeout(function() {
		me.enable(true);
	}, 10000);
	
	
	var applyOrgCode=form.findField("applyOrgCode").getValue();
	var applyOrgName=form.findField("applyOrgName").getValue();
	var applyAmount=form.findField("applyAmount").getValue();
	var applyTime=form.findField("applyTime").getValue();
	var applyUserName=form.findField("applyUserName").getValue();
	var applyUserCode=form.findField("applyUserCode").getValue();
	
	if(applyAmount<=0){
		Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApply.i18n('foss.stl.consumer.note.inputApplyAmount'));	
		return false;
	}
	var params = {
			"noteVo.noteApplyDto.applyOrgCode":applyOrgCode,
			"noteVo.noteApplyDto.applyOrgName":applyOrgName,
			"noteVo.noteApplyDto.applyAmount":applyAmount,
			"noteVo.noteApplyDto.applyTime":applyTime,
			"noteVo.noteApplyDto.applyUserName":applyUserName,
			"noteVo.noteApplyDto.applyUserCode":applyUserCode 
	}; 
	form.submit({
		url:consumer.realPath('applyNote.action'),
		//waitMsg : '查询中...',
		//waitTitle : '请等待',
		actionMethods:'post',
		params:params,
		submitEmptyText:false,
		//waitMsg:'查询中，请稍后...',
		success:function(form,action){
			var json=action.result;
			//var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), json.message);
			consumer.noteApply.reset;
			consumer.noteApply.applyNoteWindow.hide();
			
			var grid = Ext.getCmp('T_consumer-initApply_content').getAreaGrid();
			grid.getPagingToolbar().moveFirst();
			me.enable(true);
		},	
		exception:function(form,action){
			var json = action.result;	 
			Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), json.message);
			me.enable(true);
		}
	}); 
	
}

/**
 * 刷新Grid列表
 */
consumer.noteApply.refReshGrid=function(){
	var grid = Ext.getCmp('T_consumer-initApply_content').getAreaGrid();
	grid.getPagingToolbar().moveFirst();
}
/**
 * 显示弹出申请小票单据窗口
 */
consumer.noteApply.showPopApplyNote=function(){
	consumer.noteApply.applyNoteWindow.show(); 
}

consumer.noteApply.reset = function() {
	var applyUserName=this.up('form').getForm().findField("noteVo.noteApplyDto.applyUserCode").getValue();
	this.up('form').getForm().reset();
	this.up('form').getForm().findField("noteVo.noteApplyDto.applyUserCode").setValue(applyUserName);
};


/**
 * 隐藏弹出申请小票单据窗口
 */
consumer.noteApply.hidePopApplyNote=function(){
	consumer.noteApply.reset;
	consumer.noteApply.applyNoteWindow.hide();
}


//票据申请Model
Ext.define('Foss.note.NoteApplyModel', {
			extend :'Ext.data.Model', 
			fields :[  
			{	
				name :'id',  //申请编号
				type :'string'
			}, {	
				name :'applyOrgCode',  //申请部门编码
				type :'string'
			}, {	
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
				name :'applyUserName', //申请人,
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

//票据申请Grid
Ext.define('Foss.note.NoteApplyGrid',{
	extend:'Ext.grid.Panel',
	title:consumer.noteApply.i18n('foss.stl.consumer.note.applyGridTitle'),
	columnWidth:1,
	stripeRows:true,
    columnLines:true,
	collapsible:false,
    bodyCls:'autoHeight',
    emptyText: consumer.noteApply.i18n('foss.stl.consumer.common.emptyText'),
    viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
    },
    frame:true,
    //增加表格列的分割线
	cls:'autoHeight', 
	store :null,
	autoScroll :true,
	height: 650,
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
	dockedItems:[{
		xtype :'toolbar',
		dock :'top',
		layout :'column',
		defaults :{
			margin :'0 0 5 3'
		},
		items :[ {
			xtype :'button',
			text :consumer.noteApply.i18n('foss.stl.consumer.note.applyNote'),
			columnWidth :.1,
			handler : consumer.noteApply.showPopApplyNote,
			disabled:!consumer.noteApply.isPermission('/stl-web/consumer/applyNote.action'),
			hidden:!consumer.noteApply.isPermission('/stl-web/consumer/applyNote.action')
		}]
	}],
	columns :[ {
		xtype:'actioncolumn',
		flex: 1,
		text: consumer.noteApply.i18n('foss.stl.consumer.common.actionColumn'),
		align: 'center',
		items: [{
                tooltip: consumer.noteApply.i18n('foss.stl.consumer.note.applyWriteoff'),
				iconCls:'foss_icons_stl_applyVerifCancel',
				width:22,
				handler: function(grid, rowIndex, colIndex){
					consumer.noteApply.appWriteoff(grid, rowIndex, colIndex);
				},
//				hidden:!consumer.noteApply.isPermission('/stl-web/consumer/updateWriteoff.action')
				getClass:function(v,m,r,rowIndex){
	                if(!consumer.noteApply.isPermission('/stl-web/consumer/applyWriteoff.action')){
						return 'statementBill_hide';
					}else {
						return 'foss_icons_stl_applyVerifCancel';
					}
              }
            },{
                tooltip: consumer.noteApply.i18n('foss.stl.consumer.note.noApplyWriteoff'),
				iconCls:'foss_icons_bse_applyReturn',
				width:22,
				handler: function(grid, rowIndex, colIndex){
					consumer.noteApply.reAppWriteoff(grid, rowIndex, colIndex);
				},
				getClass:function(v,m,r,rowIndex){
	                if(!consumer.noteApply.isPermission('/stl-web/consumer/reApplyWriteoff.action')){
						return 'statementBill_hide';
					}else {
						return 'foss_icons_bse_applyReturn';
					}
              }
            }]
		},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.applyOrgCode'), dataIndex :'applyOrgCode' ,hidden:true},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.applyOrgName'), flex:1,dataIndex :'applyOrgName' ,width:200},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.applyAmount'), dataIndex :'applyAmount' ,width:100},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.startNumber'),dataIndex:'beginNo',width:100},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.endNumber'),dataIndex:'endNo',width:100},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.applyDate'), flex:1,dataIndex :'applyTime' ,width:200
			,renderer:function(value){
				return stl.dateFormat(value,stl.FORMAT_DATE);
			}
		},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.applyUserCode'),dataIndex:'applyUserCode',hidden:true},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.applyUserName'), flex:1, dataIndex:'applyUserName' ,width:200},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.active'),  dataIndex:'active' ,width:100,
			renderer: consumer.note.activeRenderer
		},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.status'),   dataIndex:'status' ,width:100,
			renderer: consumer.note.statusRenderer
		},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.approveStatus'),  dataIndex:'approveStatus' ,width:100,
			renderer: consumer.note.approveStatusRenderer
		},
		{ text:consumer.noteApply.i18n('foss.stl.consumer.note.writeoffStatus'), dataIndex:'writeoffStatus',width:100,
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
					var form = Ext.getCmp('T_consumer-initApply_content').getQueryForm().getForm(); 
					
					if(form.isValid()){
						var applyStartTime=form.findField("noteVo.noteApplyDto.applyStartTime").getValue();
						var applyEndTime=form.findField("noteVo.noteApplyDto.applyEndTime").getValue();
						var storageStartTime=form.findField("noteVo.noteApplyDto.storageStartTime").getValue();
						var storageEndTime=form.findField("noteVo.noteApplyDto.storageEndTime").getValue();
						
						if(Ext.isEmpty(applyStartTime)){
							 Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.note.pleaseSelectStartDate')); 
							 return false;
						}else if(Ext.isEmpty(applyEndTime)){
							 Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.note.pleaseSelectEndDate')); 
							return false;
						}
						
						if(Ext.isEmpty(storageStartTime) && !Ext.isEmpty(storageEndTime)){
							 Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.note.pleaseSelectStorageStartDate')); 
							 return false;
						}else if(!Ext.isEmpty(storageStartTime) && Ext.isEmpty(storageEndTime)){
							 Ext.MessageBox.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'), consumer.noteApply.i18n('foss.stl.consumer.note.pleaseSelectStorageEndDate')); 
							return false;
						}
				    	 
						var result1=consumer.note.validateDateDiff(applyStartTime,applyEndTime,consumer.note.DATELIMITDAYS,consumer.noteApply.i18n('foss.stl.consumer.note.apply'));
				    	if(result1){
				    		return false;
				    	}
				    	var result2=consumer.note.validateDateDiff(storageStartTime,storageEndTime,consumer.note.DATELIMITDAYS,consumer.noteApply.i18n('foss.stl.consumer.note.storage'));
						if(result2){
							return false;
						} 
						var params=form.getValues();
						Ext.apply(params ,{
							"noteVo.queryPage" : consumer.note.QUERY_PAGE__APPLY 
						});
						
						Ext.apply(operation,{
							params :  params
						});	
					}else{
						Ext.Msg.alert(consumer.noteApply.i18n('foss.stl.consumer.common.warmTips'),consumer.noteApply.i18n('foss.stl.consumer.note.pleaseCheckConditionLegal'));
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

//申请小票单据 FORM WIN
Ext.define('Foss.note.ApplyNoteForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteApply.i18n('foss.stl.consumer.note.applyNoteMange'),
	frame:true, 
	collapsible:false,
	animcollapse:true,
	defaults:{
		margin :'5 5 5 0',
		labelWidth :85,
		colspan :1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :2
	},
	
	items:[
	 {
		name:'applyOrgName',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyOrgName'),
		readOnly: true ,
		value:stl.currentDept.name
	},{
		name:'applyOrgCode',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyOrgCode'),
		value:stl.currentDept.code,
		hidden:true
	},{
		xtype: 'numberfield',
		name:'applyAmount',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyAmount'),
		allowDecimals:false,
		allowBlank:false,
		minValue: 1 ,
        maxValue: 1,
        editable:false,
        value:1
	},{
		name:'applyTime', 
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyTime'),
		readOnly:true
	},{
		name:'applyUserName',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyUserName'),
		readOnly:'true',
		maxLength: 20,
		value:stl.emp.empName
	},{
		name:'applyUserCode',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyUserCode'),
		hidden:true,
		value:stl.emp.empCode
	},{
		border: 1,
		xtype:'container',
		width:460,
		height:40,
		colspan:2,
		defaultType:'button',
		layout:'column',
		items:[{
			  text:consumer.noteApply.i18n('foss.stl.consumer.common.cancel'),  
			  columnWidth : .12,
			  handler:consumer.noteApply.hidePopApplyNote
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.76
			},
		  	{
				text:consumer.noteApply.i18n('foss.stl.consumer.common.OK'),
				cls:'yellow_button', 
				columnWidth : .12,
				handler:consumer.noteApply.applyNote
		  	}]
		}]
});
 

//申请票据信息
Ext.define('Foss.note.ApplyNoteWindow',{
		extend:'Ext.Window', 
		closeAction:'hide',
		title:'',
		x:450,
		y:300,
		modal:true,
		height:'autoHeight',
        width:'autoWidth',
        resizable:false,
        //layout:'fit',
        layout :'column',
        applyNoteForm:null,
        listeners:{
        	beforeshow:function(me){
        		me.getApplyNoteForm().getForm().reset();
        		me.getApplyNoteForm().getForm().findField('applyTime').setValue(stl.dateFormat(new Date(),stl.FORMAT_TIME));
        	}
        },
        getApplyNoteForm:function(){  
        	if(this.applyNoteForm==null){
        		this.applyNoteForm=Ext.create('Foss.note.ApplyNoteForm');
        	}
        	return this.applyNoteForm;
        }, 
        constructor:function(config){
    		var me = this,
			cfg = Ext.apply({}, config);
    		me.items = [me.getApplyNoteForm()];
    		me.callParent([cfg]);
        }
	}); 
consumer.noteApply.applyNoteWindow=Ext.create('Foss.note.ApplyNoteWindow');



//查询申请小票管理 FORM
Ext.define('Foss.note.QueryNoteForm',{
	extend:'Ext.form.Panel',
	title:consumer.noteApply.i18n('foss.stl.consumer.note.applyNoteMange'),
	frame:true,
	columnWidth:1,
	collapsible:true,
	animcollapse:true,
	defaults:{
		margin :'5 5 5 0',
		labelWidth :85,
		colspan :1 
	},
	defaultType:'textfield',
	layout:{
		type :'table',
		columns :3
	},
	items:[
	{
		name:'noteVo.noteApplyDto.applyOrgName',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyOrgName'),
		readOnly:true,
		value:stl.currentDept.name
	},{
		name:'noteVo.noteApplyDto.applyOrgCode',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyOrgCode'),
		value:stl.currentDept.code,
		hidden:true
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyStartTime', 
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.applyDate'),
		format:'Y-m-d',
		allowBlank:false,
		columnWidth:.25,
		//value:stl.dateFormat(stl.getTargetDate(new Date(),-30),stl.FORMAT_DATE)
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE)
    	
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.applyEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.to'),
		format:'Y-m-d',
		allowBlank:false,
		columnWidth:.25,
		value:stl.dateFormat(new Date(),stl.FORMAT_DATE)
	}, {
		xtype : 'commonemployeeselector',
		name:'noteVo.noteApplyDto.applyUserCode',
		fieldLabel :consumer.noteApply.i18n('foss.stl.consumer.note.applyUserName')
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.storageStartTime', 
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.storageDate'),
		format:'Y-m-d',
		allowBlank:true,
		columnWidth:.25 
	},{
		xtype:'datefield',
		name:'noteVo.noteApplyDto.storageEndTime',
		labelWidth:30,
	    labelSeparator:'',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.to'),
		format:'Y-m-d',
		allowBlank:true,
		columnWidth:.25 
	},{
		xtype:'combo',
		name:'noteVo.noteApplyDto.status',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.status'),
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
					'valueName' : consumer.noteApply.i18n('foss.stl.consumer.common.all')
				})
	},{
		xtype:'combo',
		name:'noteVo.noteApplyDto.writeoffStatus',
		fieldLabel:consumer.noteApply.i18n('foss.stl.consumer.note.writeoffStatus'),
		columnWidth:.25, 
		value:'',
		displayField:'valueName',
		valueField:'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:FossDataDictionary.getDataDictionaryStore(
				'NOTE_APPLICATION__WRITEOFF_STATUS', null, {
					'valueCode' : '',
					'valueName' : consumer.noteApply.i18n('foss.stl.consumer.common.all')
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
			  text:consumer.noteApply.i18n('foss.stl.consumer.common.reset'),   
			  columnWidth:.12,
			  handler:consumer.noteApply.reset
		  	},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.76
			},
		  	{
			  text:consumer.noteApply.i18n('foss.stl.consumer.common.query'),
			  columnWidth:.12,
			  cls:'yellow_button',  
			  handler:consumer.noteApply.refReshGrid
		  	}]
		}
	]  
});
  

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp('T_consumer-initApply_content')) {
		return;
	} 
	var queryForm = Ext.create('Foss.note.QueryNoteForm');//查询FORM
	var areaGrid = Ext.create('Foss.note.NoteApplyGrid');//查询结果GRID
	queryForm.getForm().findField('noteVo.noteApplyDto.applyUserCode').setCombValue(stl.emp.empName,stl.emp.empCode);//部门
	Ext.create('Ext.panel.Panel', {
		id :'T_consumer-initApply_content',
		cls:"panelContentNToolbar", //必须添加,内容定位用。
		bodyCls:'panelContentNToolbar-body', //必须添加,内容定位用。
		layout :'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return areaGrid;
		},
		items:[ queryForm,areaGrid],
		renderTo :'T_consumer-initApply-body'
	}); 
});

