//model
Ext.define('Foss.baseinfo.expressSortingStationMapping.DataModel',{
	extend:'Ext.data.Model',
	fields:[{
		type:'string',
		name:'id'
	},{
		type:'string', //外场编码
		name:'outfieldCode'
	},{
		type:'string',
		name:'outfieldName'
	},{
		type:'string',//零担库区编码
		name:'warehouseAreaCode'
	},{
		type:'string',
		name:'warehouseAreaName'
	},{
		type:'string',//目的站编码
		name:'destinationCode'
	},{
		type:'string',
		name:'destinationName'
	},{
		type:'string',//备注
		name:'remark'
	},{
		type:'string',//虚拟编码
		name:'virtualCode'
	},{
		type:'string',
		name:'active'
	}]
});
//store
Ext.define('Foss.baseinfo.expressSortingStationMapping.DataStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressSortingStationMapping.DataModel',
	pageSize:20,
	proxy:{
		type: 'ajax',
        url: baseinfo.realPath('queryMoreMappingList.action'),
        actionMethods : 'post',
        reader: {
            type: 'json',
            root: 'mappingVo.mappingEntityList',
            totalProperty : 'totalCount'// 个数
        }
	},
	autoLoad:false
});
//queryForm
Ext.define('Foss.baseinfo.expressSortingStationMapping.MappingQueryForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.queryCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height :180,
	defaultType : 'textfield',
	layout:'column',
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.items=[{
			xtype:'commontransfercenterselector',
			fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.outfield'),//外场
			name:'outfieldCode',
			columnWidth:0.3
		},{
			xtype:'commongoodsareaselector',
			fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.warehouseArea'),//零担库区名称
			name:'warehouseAreaCode',
			columnWidth:0.3,	
		},{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.expressDestination'),//目的站
			name:'destinationCode',
			columnWidth:0.3,
			type:'ORG',
			transferCenter:'Y',
			salesDepartment:'Y'
		},{
			xtype:'container',
			columnWidth:1
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.reset'),//重置
				  disabled:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/queryButton'),
				  hidden:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/queryButton'),
				  columnWidth:.08,
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/queryButton'),
				  hidden:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/queryButton'),
				  columnWidth:.08,
				  cls:'yellow_button',  
				  handler:function() {
					  //表单校验，分页查询
					if(me.getForm().isValid()){
						Ext.getCmp('T_baseinfo-expressSortingStationMapping_content').getResultGird().getPagingToolbar().moveFirst();
					}
				  }
			  	}]
		}];
		me.callParent([cfg]);
	}
});
//resultGrid
Ext.define('Foss.baseinfo.expressSortingStationMapping.MappingresultGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressCity.queryResultList'),// 查询结果列表,
	frame:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:'查询结果为空',
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				plugins: 'pagesizeplugin'
			});
		}
		return this.pagingToolbar;
	},
	//新增win
	addMappingWin:null,
	getAddMappingWin:function(){
		if(this.addMappingWin==null){
			this.addMappingWin =Ext.create('Foss.baseinfo.expressSortingStationMapping.AllMappingWindow',{
				title:'新增快递分拣目的站映射',
				winStatus:'Add'
			});
			this.addMappingWin.parent =this;
		}
		return this.addMappingWin;
	},
	//修改窗口
	updateMappingWin:null,
	getUpdateMappingWin:function(){
		if(this.updateMappingWin==null){
			this.updateMappingWin =Ext.create('Foss.baseinfo.expressSortingStationMapping.AllMappingWindow',{
				title:'修改快递分拣目的站映射',
				winStatus:'Update'
			});
			this.updateMappingWin.parent =this;
		}
		return this.updateMappingWin;
	},
	//查看窗口
	showMappingWin:null,
	getShowMappingWin:function(){
		if(this.showMappingWin==null){
			this.showMappingWin =Ext.create('Foss.baseinfo.expressSortingStationMapping.AllMappingWindow',{
				title:'查询快递分拣目的站映射',
				winStatus:'Show_detail'
			});
			this.showMappingWin.parent =this;
		}
		return this.showMappingWin;
	},
	//删除方法
	deleteMappingFun:function(grid,virtualCodeList){
		Ext.Ajax.request({
			url:baseinfo.realPath('delectMappingByvirualCodeList.action'),
			jsonData:{'mappingVo':{'virtualCodeList':virtualCodeList}},
			success: function(response){
		        var json = Ext.decode(response.responseText);
		        baseinfo.showInfoMes(json.message);
		        grid.getPagingToolbar().moveFirst();
		    },
		    exception:function(response){
				var json =Ext.decode(response.responseText);
				baseinfo.showErrorMes(json.message);
			}
		});
	},
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.columns =[{
			align:'center',
			xtype:'actioncolumn',
			text :baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.operate'),//操作 
			items:[{
				iconCls:'deppon_icons_edit',
				tooltip:'编辑',
				width:30,
				disabled:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/updateButton'),
				handler:function(grid,rowIndex,colIndex){
					var currentModel =grid.getStore().getAt(rowIndex);
					var win =me.getUpdateMappingWin();
					win.mappingModel =currentModel;
					win.show();
				}
			},{
				iconCls:'deppon_icons_cancel',
				tooltip:'作废',
				width:30,
				disabled:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/deleteButton'),
				handler:function(grid,rowIndex,colIndex){
					var currentModel =grid.getStore().getAt(rowIndex);
					var list =new Array();
					list.push(currentModel.data.virtualCode);
					me.deleteMappingFun(me,list);
				}
			},{
				iconCls:'deppon_icons_showdetail',
				tooltip:'查询详情',
				handler:function(grid,rowIndex,colIndex){
					var currentModel =grid.getStore().getAt(rowIndex);
					var win =me.getShowMappingWin();
					win.mappingModel =currentModel;
					win.show();
				}
			}]
		},{
			dataIndex:'outfieldCode',
			text:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.outfieldCode'),//外场编号
			flex:1
		},{
			dataIndex:'outfieldName',
			text:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.outfieldName'), //外场名称
			flex:1
		},{
			dataIndex:'warehouseAreaCode',
			text:'零担库区编码',
			flex:1
		},{
			dataIndex:'warehouseAreaName',
			text:'零担库区名称',
			flex:1
		},{
			dataIndex:'destinationCode',
			text:'目的站编码',
			flex:1
		},{
			dataIndex:'destinationName',
			text:'快递目的站',
			flex:1
		},{
			dataIndex:'remark',
			text:'备注',
			flex:1
		}];
		me.store =Ext.create('Foss.baseinfo.expressSortingStationMapping.DataStore',{
			listeners:{
				beforeload:function(store, operation, eOpts){
					//查询表单
					var queryForm =Ext.getCmp('T_baseinfo-expressSortingStationMapping_content').getQueryForm();
					if(queryForm != null){
						//获取查询参数
						var values = queryForm.getForm().getValues();
						Ext.apply(operation,{
							params:{
								'mappingVo.entity.outfieldCode':values.outfieldCode,
								'mappingVo.entity.warehouseAreaCode':values.warehouseAreaCode,
								'mappingVo.entity.destinationCode':values.destinationCode
							}
						});
					}
				}
			}
		});
		me.tbar=[{
			xtype:'button',
			text:'新增',
			disabled:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/addButton'),
			hidden:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/addButton'),
			handler:function(){
				me.getAddMappingWin().show();
			}
		},{
			xtype:'button',
			text:'作废',
			disabled:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/deleteButton'),
			hidden:!baseinfo.expressSortingStationMapping.isPermission('expressSortingStationMapping/deleteButton'),
			handler:function(){
				var selection =grid.getSelectionModel().getSelection();
				if(selection.length<1){
					baseinfo.showInfoMes('请选中一条进行作废操作');
					return;
				}
				var virtualCodeList =new Array();
				for ( var i = 0; i < selection.length; i++) {
					virtualCodeList.push(selection[i].data.virtualCode);
				}
				me.deleteMappingFun(me,virtualCodeList);
				
			}
		}];
		me.listeners ={
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}	
		};
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.bbar =me.getPagingToolbar();
		me.getPagingToolbar().store =me.store;
		me.callParent([cfg]);
	}
});
//addUpdateForm
Ext.define('Foss.baseinfo.expressSortingStationMapping.AddUpdateMappingForm',{
	extend:'Ext.form.Panel',
	frame: true,
	height:280,
	collapsible: true,
	defaults:{
		colspan : 1,
		margin : '10 5 5 5',
		maxLength:50,
		allowBlank:false,
		labelWidth:80,
		anchor : '100%'
	},
	defaultType:'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    addOrUpdate:'ADD',
    constructor:function(config){
    	var me =this,cfg =Ext.apply({},config);
    	me.items =[{
    		fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.outfieldCode'), //外场编号
    		name:'outfieldCode',
    		columnWidth:300
    	},{
    		fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.outfieldName'), //外场名称
    		name:'outfieldName',
    		xtype:'commontransfercenterselector',
    		columnWidth:300,
    		listeners:{
    			select:function(combo,record,eOpts){
    				var outfieldCode=me.getForm().findField('outfieldCode');
    				if(!Ext.isEmpty(record[0])){
	    					outfieldCode.setValue(record[0].data.code);
    				}
    			}
    		}
    	},{
    		fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.goodsAreaCode'), //库区编码
    		name:'warehouseAreaCode',
    		columnWidth:300
    	},{
    		fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.goodsAreaName'), //外场名称
    		name:'warehouseAreaName',
    		xtype:'commongoodsareaselector',
    		columnWidth:300,
    		listeners:{
    			select:function(combo,record,eOpts){
    				var warehouseAreaCode=me.getForm().findField('warehouseAreaCode');
    				if(!Ext.isEmpty(record[0])){
    					warehouseAreaCode.setValue(record[0].data.goodsAreaCode);
    				}
    			}
    		}
    	},{
    		fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.expressSortingStationMapping.expressDestinationCode'), //目的站编码
    		name:'destinationCode',
    		columnWidth:300
    	},{
    		fieldLabel:baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.flightsDestinationStation'), //目的站
    		name:'destinationName',
    		xtype:'dynamicorgcombselector',
    		columnWidth:300,
    		type:'ORG',
			transferCenter:'Y',
			salesDepartment:'Y',
    		listeners:{
    			select:function(combo,record,eOpts){
    				var destinationCode=me.getForm().findField('destinationCode');
    				if(!Ext.isEmpty(record[0])){
    					destinationCode.setValue(record[0].data.code);
    				}
    			}
    		}
    	},{
    		name:'remark',
    		fieldLabel:'备注',
    		xtype:'textareafield',
    		width:500,
    		maxLength:333,
    		maxLengthText:'输入字数不能超过333个字',
    		colspan : 2,
    		allowBlank:true
    	}];
    	me.callParent([cfg]);
    }
});
//window
Ext.define('Foss.baseinfo.expressSortingStationMapping.AllMappingWindow',{
	extend:'Ext.window.Window',
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	//关闭动作为hide，默认为destroy
	closeAction : 'hide',
	mappingModel:null, //实体model
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :400,
	winStatus:'Add', //设置窗口状态， 有Add，Update，Show_detail，默认Add
	valueForm:null,
	getValueForm:function(){
		if(null ==this.valueForm){
			this.valueForm =Ext.create('Foss.baseinfo.expressSortingStationMapping.AddUpdateMappingForm');
		}
		return this.valueForm;
	},
	listeners:{
		beforeshow:function(me){
			//若是修改
			if(me.winStatus ==='Update'){
				me.getValueForm().getForm().loadRecord(me.mappingModel);
				me.getValueForm().getForm().findField('outfieldName').setCombValue(me.mappingModel.data.outfieldName,me.mappingModel.data.outfieldCode);
				me.getValueForm().getForm().findField('warehouseAreaName').setCombValue(me.mappingModel.data.warehouseAreaName,me.mappingModel.data.warehouseAreaCode);
				me.getValueForm().getForm().findField('destinationName').setCombValue(me.mappingModel.data.destinationName,me.mappingModel.data.destinationCode);
			//若查询	
			}else if(me.winStatus ==='Show_detail'){
				me.getValueForm().getForm().loadRecord(me.mappingModel);
				me.getValueForm().getForm().getFields().each(function(item){//设置不可编辑
					item.setReadOnly(true);
					if(item.name ==='outfieldName'||item.name ==='warehouseAreaName'||item.name ==='destinationName'){
						item.setDisabled(true);
					}
		    	});
			}
		},
		beforehide:function(me){
			me.getValueForm().getForm().reset();
			me.getValueForm().getForm().getFields().each(function(item){//设置可编辑
				if(item.hidden ==true || item.disabled==true){
					item.setDisabled(false);
					item.setReadOnly(false);
				}
	    	});
		}
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.items =[me.getValueForm()];
		me.fbar=[{
			text : baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.cancel'),//取消
			disabled:me.winStatus==='Show_detail'?true:false,
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.reset'),//重置
			disabled:me.winStatus==='Show_detail'?true:false,
			handler :function(){
				//重新加载之前的数据
				if(me.winStatus ==='Update'){
					me.getValueForm().getForm().loadRecord(me.mappingModel);
					me.getValueForm().getForm().findField('outfieldName').setCombValue(me.mappingModel.data.outfieldName,me.mappingModel.data.outfieldCode);
					me.getValueForm().getForm().findField('warehouseAreaName').setCombValue(me.mappingModel.data.warehouseAreaName,me.mappingModel.data.warehouseAreaCode);
					me.getValueForm().getForm().findField('destinationName').setCombValue(me.mappingModel.data.destinationName,me.mappingModel.data.destinationCode);
				}else if(me.winStatus ==='Add'){
					me.getValueForm().getForm().reset();
				}
			} 
		},{
			text : baseinfo.expressSortingStationMapping.i18n('foss.baseinfo.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 235',
			handler :function(){
			if(me.winStatus ==='Show_detail'){
				me.close();
				//不是查看的话，要设置校验表单
			}else if(me.getValueForm().getForm().isValid()){
				var model =null,
					url =null;
					if(me.winStatus ==='Add'){
						model =new Foss.baseinfo.expressSortingStationMapping.DataModel();
						url =baseinfo.realPath('addExpressSortStationMapping.action');
					}else if(me.winStatus ==='Update'){
						model =me.mappingModel;
						url =baseinfo.realPath('updateExpressSortStationMapping.action');
					}	
						//获取表单的值
						me.getValueForm().getForm().updateRecord(model);
						var form =me.getValueForm().getForm();
						if(model.get('outfieldCode')!=form.findField('outfieldName').value){
							baseinfo.showInfoMes('外场编码和系统中存在外场编码不一致；请勿自己手动修改外场编码！');
							return;
						}
						if(model.get('warehouseAreaCode')!=form.findField('warehouseAreaName').value){
							baseinfo.showInfoMes('库区编码和系统中存在库区编码不一致；请勿自己手动修改库区编码！');
							return;
						}
						if(model.get('destinationCode')!=form.findField('destinationName').value){
							baseinfo.showInfoMes('目的站编码和系统中存在目的站编码不一致；请勿自己手动修改目的站编码！');
							return;
						}
						model.set('outfieldName',form.findField('outfieldName').rawValue);
						model.set('warehouseAreaName',form.findField('warehouseAreaName').rawValue);
						model.set('destinationName',form.findField('destinationName').rawValue);
						var params ={'mappingVo':{'entity':model.data}};
						Ext.Ajax.request({
							url:url,
							jsonData:params,
							success: function(response){
						        var json = Ext.decode(response.responseText);
						        baseinfo.showInfoMes(json.message);
						        me.close();
						        Ext.getCmp('T_baseinfo-expressSortingStationMapping_content').getResultGird().getPagingToolbar().moveFirst();
						    },
						    exception:function(response){
								var json =Ext.decode(response.responseText);
								baseinfo.showErrorMes(json.message);
							}
						});
				}
			} 
		}];
		me.callParent([cfg]);
	}
	
});
//程序入口
Ext.onReady(function(){
	Ext.QuickTips.init();
	if(Ext.getCmp('T_baseinfo-expressSortingStationMapping_content')){
		return;
	}
	var queryForm =Ext.create('Foss.baseinfo.expressSortingStationMapping.MappingQueryForm');
	var resultGird =Ext.create('Foss.baseinfo.expressSortingStationMapping.MappingresultGrid');
	Ext.getCmp('T_baseinfo-expressSortingStationMapping').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-expressSortingStationMapping_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		getQueryForm : function() {
			return queryForm;
		},
		getResultGird : function() {
			return resultGird;
		},
		items: [queryForm,resultGird]
	}));
});