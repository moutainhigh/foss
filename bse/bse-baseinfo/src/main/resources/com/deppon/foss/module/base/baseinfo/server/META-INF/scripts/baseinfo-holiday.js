/*
 * 法定节假日基础资料-dujunhui
*/
Ext.define('Foss.baseinfo.holiday.Model',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id',//id
		type:'string'
	},{
		name:'holidayName',//法定节假日名称
		type:'string'
	},{
		name:'beginTime',//节假日开始时间
		type:'date',
		convert:dateConvert
	},{
		name:'endTime',//节假日结束时间
		type:'date',
		convert:dateConvert
	},{
		name:'remark',//备注
		type:'string'
	}]
});
//创建法定节假日结果集Store
Ext.define('Foss.baseinfo.holiday.Store',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.holiday.Model',//绑定Model
	pageSize:10,//分页
	proxy:{
		//以JSON方式数据传输
		actionMethods:'POST',
		type:'ajax',
		url:baseinfo.realPath('queryHoliday.action'),
		reader:{
			type:'json',
			root:'vo.entityList',
			totalProperty:'totalCount',
			successProperty:'success'
		}
	},
	//构造函数
	constructor:function(config){
		var me=this,
		cfg=Ext.apply({},config);
		me.listeners= {
			beforeload:function(store,operation,eOpts){
				var queryForm=Ext.getCmp('Foss_baseinfo_holiday_QueryForm_id').getForm();
				if(queryForm!=null){
					var beginTime=queryForm.findField('beginTime').getValue();
					var endTime=queryForm.findField('endTime').getValue();
					Ext.apply(operation,{
						params:{
							'vo.entity.beginTime':beginTime,//节假日开始时间
							'vo.entity.endTime':endTime //节假日结束时间
						}
					});
				}
			}
		};
		me.callParent([cfg]);
	},
});
//查询表单
Ext.define('Foss.baseinfo.holiday.QueryForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.holiday.i18n('foss.baseinfo.queryCondition'),//查询条件
	frame:true,
	collapsible:true,
	id:'Foss_baseinfo_holiday_QueryForm_id',
	layout:'column',//列布局
	defaults:{
		labelSeperator:':',//''
		margin:'8,10,5,10',
		anchor:'100%'
	},
	height:150,
	items:[{
		xtype:'rangedatefield',
		datetype:'datefield',
		fieldLabel:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.queryDateRange'),//查询日期范围
		dateRange:999999,//时间间隔，以天为单位
		fromName:'beginTime',//开始时间
		toName:'endTime',//结束时间
		disallowBlank:true,//不允许为空
		columnWidth:0.66
	}],
	constructor:function(config){
		var me=this,
		cfg=Ext.apply({},config);
		me.fbar=[{
			xtype:'button',
			width:70,
			text:baseinfo.holiday.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.holiday.isPermission('holiday/queryButton'),
			hidden:!baseinfo.holiday.isPermission('holiday/queryButton'),
			margin:'0,750,0,0',
			handler:function(){
				me.getForm().reset();
				me.getForm().findField('beginTime').setValue('');
				me.getForm().findField('endTime').setValue('');
			}
		},{
			xtype:'button',
			width:70,
			text:baseinfo.holiday.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.holiday.isPermission('holiday/queryButton'),
			hidden:!baseinfo.holiday.isPermission('holiday/queryButton'),
			cls:'yellow_button',
			handler:function(){
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-holiday_Content').getHolidayResultGrid().getPagingToolbar().moveFirst();
				}
			}
		}];
		me.callParent([cfg]);
	}
});
//结果集Grid
Ext.define('Foss.baseinfo.holiday.ResultGrid',{
	extend:'Ext.grid.Panel',
	title:baseinfo.holiday.i18n('foss.baseinfo.queryResults'),//查询结果
	columnLine:true,//表格列的分隔线
	id:'Foss_baseinfo_holiday_ResultGrid_id',
	frame:true,
	collapsible:true,
	stripeRows:true,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	store:null,
	addWindow:null,//新增节假日信息窗体
	getAddWindow:function(){
		if(Ext.isEmpty(this.addWindow)){
			this.addWindow=Ext.create('Foss.baseinfo.holiday.AddWindow');
			this.addWindow.parent=this;//父元素
		}
		return this.addWindow;
	},
	updateWindow:null,//修改节假日信息窗体
	getUpdateWindow:function(){
		if(Ext.isEmpty(this.updateWindow)){
			this.updateWindow=Ext.create('Foss.baseinfo.holiday.UpdateWindow');
			this.updateWindow.parent=this;//父元素
		}
		return this.updateWindow;
	},
	voidHoliday:function(ids){//作废假日信息
		//作废操作
		Ext.Msg.confirm(baseinfo.holiday.i18n('foss.baseinfo.notice'),
				baseinfo.holiday.i18n('foss.baseinfo.deleteWarnMsg'),function(btn, text){
			Ext.MessageBox.buttonText.yes=baseinfo.holiday.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no=baseinfo.holiday.i18n('foss.baseinfo.cancel');
			if (btn=='yes'){
				Ext.Ajax.request({
					url:baseinfo.realPath('voidHoliday.action'),
					params:{
						"vo.ids":ids
					},
					success:function(response){//成功返回
						var json=Ext.decode(response.responseText);
						baseinfo.showInfoMes(json.message);
						Ext.getCmp('T_baseinfo-holiday_Content').getHolidayResultGrid().store.loadPage(1);
						return;
					},
					exception:function(response){//异常返回
						if(!Ext.isEmpty(response)){//界面抛出异常信息
							baseinfo.showErrorMes(Ext.decode(response.responseText).message);
						}else{
							baseinfo.showErrorMes(baseinfo.holiday.i18n('foss.baseinfo.requestTimeout'));//请求超时
						}
					}
				});
			}
		});
	},
	pagingToolbar:null,//分页组件
	getPagingToolbar:function(){
		if(this.pagingToolbar==null){
			this.pagingToolbar=Ext.create('Deppon.StandardPaging',{
				store:this.store,
				pageSize:10,
				prePendButtons:true,
				defaults:{
					margin:'0,0,15,3'
				}
			});
		}
		return this.pagingToolbar;
		
	},
	columns:[{
		xtype:'actioncolumn',
		text:baseinfo.holiday.i18n('foss.baseinfo.operate'),//操作
		align:'center',
		flex:1,
		items:[{
			iconCls:'deppon_icons_edit',
			tooltip : baseinfo.holiday.i18n('foss.baseinfo.edit'),//编辑
			disabled:!baseinfo.holiday.isPermission('holiday/editButton'),
			//事件操作
			handler : function(grid, rowIndex,colIndex) {
				//获取选中的数据
				var record = grid.getStore().getAt(rowIndex);
				//获得修改窗口
				var updateWindow = grid.up('grid').getUpdateWindow();
				updateWindow.infoModel=record;
				updateWindow.show();
			}
		},{
			iconCls:'deppon_icons_cancel',
			tooltip : baseinfo.holiday.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.holiday.isPermission('holiday/voidButton'),
			//事件操作
			handler:function(grid,rowIndex,colIndex){
				//获取选中的数据
				var record=grid.getStore().getAt(rowIndex);
				grid.up('grid').voidHoliday([record.data.id]);//调用作废函数
			}
		}]
	},{
		header:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.holidayName'),//法定节假日名称
		dataIndex:'holidayName',
		flex:1
	},{
		header:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.beginTime'),//节假日开始日期
		dataIndex:'beginTime',
		flex:1,
		renderer:function(value){
			if(!Ext.isEmpty(value)){
				return Ext.Date.format(new Date(value),'Y-m-d');
			}else{
				return null;
			} 
		}
	},{
		header:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.endTime'),//节假日结束时间
		dataIndex:'endTime',
		flex:1,
		renderer:function(value){
			if(!Ext.isEmpty(value)){
				return Ext.Date.format(new Date(value),'Y-m-d');
			}else{
				return null;
			} 
		}
	}],
	//构造函数
	constructor:function(config){
		var me=this,
		cfg=Ext.apply({},config);
		me.store=Ext.create('Foss.baseinfo.holiday.Store');
		me.bbar=me.getPagingToolbar();
		me.pagingToolbar.store=me.store;
		me.dockedItems=[{
			xtype:'toolbar',
			dock:'top',
			layout:'column',
			defaults:{
				margin:'0,0,5,3'
			},
			items:[{
				xtype:'button',
				width:80,
				text:baseinfo.holiday.i18n('foss.baseinfo.add'),//新增
				disabled:!baseinfo.holiday.isPermission('holiday/addButton'),
				hidden:!baseinfo.holiday.isPermission('holiday/addButton'),
				handler:function(){
					this.addWindow=me.getAddWindow();
					this.addWindow.show();
				}
			},'-',{
				xtype:'button',
				width:80,
				text:baseinfo.holiday.i18n('foss.baseinfo.void'),//作废
				disabled:!baseinfo.holiday.isPermission('holiday/voidButton'),
				hidden:!baseinfo.holiday.isPermission('holiday/voidButton'),
				handler:function(){
					var me=this;
					var selections=me.up('grid').getSelectionModel().getSelection();
					if(selections.length<1){//请至少选择一条数据进行作废！
						baseinfo.showErrorMes(baseinfo.holiday.i18n('foss.baseinfo.deleteNoticeMsg'));
						return;
					}
					var ids=new Array();
					for(var i=0;i<selections.length;i++){
						ids.push(selections[i].get('id'));
					}
					me.up('grid').voidHoliday(ids);//调用作废函数
				}
			}]
		}],
		me.callParent([cfg]);
	}
});
//新增Window
Ext.define('Foss.baseinfo.holiday.AddWindow',{
	extend:'Ext.window.Window',
	title:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.addHolidayInfo'),//新增法定节假日基础资料
	width:500,
	height:240,
	parent:null,
	modal:true,
	closeAction:'hidden',
	listeners:{
		beforehide:function(me){
			//window隐藏前所作逻辑
			me.getAddUpdateForm().getForm().reset();
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){
			//window展示前所作逻辑
		}
	},
	//新增修改Form
	addUpdateForm:null,
	getAddUpdateForm:function(){
		if(Ext.isEmpty(this.addUpdateForm)){
			this.addUpdateForm=Ext.create('Foss.baseinfo.holiday.addUpdateForm');
		}
		return this.addUpdateForm;
	},
	constructor:function(config){
		var me=this,
		cfg=Ext.apply({},config);
		me.items=[me.getAddUpdateForm()];
		me.fbar = [{
			text: baseinfo.holiday.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text: baseinfo.holiday.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				me.down('form').commitInfo();
			} 
		}];
		me.callParent([cfg]);
	}
});
//修改Window
Ext.define('Foss.baseinfo.holiday.UpdateWindow',{
	extend:'Ext.window.Window',
	title:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.updateHolidayInfo'),//修改法定节假日基础资料
	width:500,
	height:240,
	parent:null,
	modal:true,
	infoModel:null,
	closeAction:'hidden',
	listeners:{
		beforehide:function(me){
			//window隐藏前所作逻辑
			me.getAddUpdateForm().getForm().reset();
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){
			//window展示前所作逻辑，window展现前加载数据
			me.getAddUpdateForm().getForm().loadRecord(me.infoModel);
			me.getAddUpdateForm().holidayId=me.infoModel.get('id');
		}
	},
	//新增修改Form
	addUpdateForm:null,
	getAddUpdateForm:function(){
		if(Ext.isEmpty(this.addUpdateForm)){
			this.addUpdateForm=Ext.create('Foss.baseinfo.holiday.addUpdateForm');
			this.addUpdateForm.flag='UPDATE';
		}
		return this.addUpdateForm;
	},
	constructor:function(config){
		var me=this,
		cfg=Ext.apply({},config);
		me.items=[me.getAddUpdateForm()];
		me.fbar = [{
			text: baseinfo.holiday.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text: baseinfo.holiday.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				me.down('form').commitInfo();
			} 
		}];
		me.callParent([cfg]);
	}
});
//新增修改Form
Ext.define('Foss.baseinfo.holiday.addUpdateForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.holidayInfo'),//法定节假日基础资料
	layout:'column',
	frame:true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	height:200,
	flag:'SAVE',
	holidayId:null,
	collapsible:true,
	commitInfo:function(){
		var me=this;
		var commitForm=me.getForm();
		if(commitForm.isValid()){
			var record=new Foss.baseinfo.holiday.Model();
			//将form值设置进model
			commitForm.updateRecord(record);
			if(record.get('beginTime')>record.get('endTime')){//节假日开始时间不能大于结束时间
				baseinfo.showErrorMes(baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.holidayDateRule'));
				return;
			}
			Ext.Ajax.request({
				url:baseinfo.realPath('addUpdateHoliday.action'),
				params:{
					"vo.entity.id":me.holidayId,
					"vo.entity.holidayName":record.get("holidayName"),
					"vo.entity.beginTime":record.get("beginTime"),
					"vo.entity.endTime":record.get("endTime"),
					"vo.entity.flag":me.flag
				},
				success:function(response){//成功返回
					var json=Ext.decode(response.responseText);
					baseinfo.showInfoMes(json.message);
					Ext.getCmp('T_baseinfo-holiday_Content').getHolidayResultGrid().getPagingToolbar().moveFirst();
					me.up('window').close();
					return;
				},
				exception:function(response){//异常返回
					if(!Ext.isEmpty(response)){
						var json=Ext.decode(response.responseText);
						baseinfo.showErrorMes(json.message);
						return;
					}else{//请求超时
						baseinfo.showErrorMes(baseinfo.holiday.i18n('foss.baseinfo.requestTimeout'));
					}
				}
			});
		}
	},
	constructor:function(config){
		var me=this,
		cfg=Ext.apply({},config);
		me.items=[{
			xtype:'textfield',
			name:'holidayName',
			maxLength:10,//限制字符长度
			fieldLabel:baseinfo.holiday.i18n('foss.bse.baseinfo.holiday.holidayName'),//法定节假日名称
			columnWidth:0.60,
			allowBlank:false
		},{
			name: 'beginTime',
			columnWidth:0.50,
			format:'Y-m-d',
			disallowBlank:true,//不允许为空
		    fieldLabel:'开始时间',//baseinfo.priceCoupon.i18n('foss.priceCoupon.businessDate'),//业务日期
	        xtype : 'datefield'
		},{
			name: 'endTime',
			columnWidth:0.50,
			format:'Y-m-d',
			disallowBlank:true,//不允许为空
		    fieldLabel:'结束时间',//baseinfo.priceCoupon.i18n('foss.priceCoupon.businessDate'),//业务日期
	        xtype : 'datefield'
		}];
		me.callParent([cfg]);
	}
});

//初始加载
Ext.onReady(function(){
	//初始化
	Ext.QuickTips.init();
	//查询Form
	var holidayQueryForm=Ext.create("Foss.baseinfo.holiday.QueryForm");
	//结果Grid
	var holidayResultGrid=Ext.create("Foss.baseinfo.holiday.ResultGrid");
	
	Ext.getCmp("T_baseinfo-holiday").add(Ext.create("Ext.panel.Panel"),{
		id:"T_baseinfo-holiday_Content",
		cls:"panelContentNToolbar",
		bodyCls:"panelContentNToolbar-body",
		layout:"auto",
		//获取查询FOrm
		getHolidayQueryForm:function(){
			return holidayQueryForm;
		},
		//获取结果Grid
		getHolidayResultGrid:function(){
			return holidayResultGrid;
		},
		items:[holidayQueryForm,holidayResultGrid]
	});
});