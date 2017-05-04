/*
 @author:dp-zhaobin
   Build date: 2012-10-19
*/

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
waybill.todoAction.getTargetDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};

waybill.todoAction.getTargetDate1 = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};
/**
 * 初始化方法

waybill.todoAction.init=function(form){
	var handleOrgCode=form.getForm().findField('handleOrgCode');
	handleOrgCode.setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode()); 
}

 */

//定义处理待办事项模型
Ext.define('Foss.ToDo.Model.ToDoActionModel', {
	extend: 'Ext.data.Model',
	fields: [
				{ name: 'waybillRfcId',type:'string' },//更改单ID
			    { name: 'waybillNo',type:'string' }, //运单号
				{ name: 'status',type:'string' }, //处理状态
				{ name: 'darftOrgName',type:'string' }, //起草变更部门
				{ name: 'operateOrgName',type:'string' }, //起草变更部门
				{ name: 'rfcInfo',type:'string' },  //变更内容
				{ name: 'darfter',type:'string' },  //变更申请人
				{ name: 'operateTime',convert:dateConvert },//更改受理时间
				{ name: 'remindTime',convert:dateConvert },//待办可见时间,
				{ name: 'isDestiChage',type:'string',//是否目的站变更
					convert:function(value){
						 if(value=='Y'){
							 return waybill.todoAction.i18n('pkp.waybill.todoAction.yes');
						 }else if(value=='N'){
							 return waybill.todoAction.i18n('pkp.waybill.todoAction.not');
						 }
					 }
				}
			 ]
});

//变更基本信息
Ext.define('Foss.ToDo.Model.TodoRfcModel', {
	extend: 'Ext.data.Model',
	fields: [
	         	{ name: 'waybillRfcId',type:'string' }, //运单号,
			    { name: 'waybillNo',type:'string' }, //运单号
				{ name: 'darftOrgName',type:'string' }, //变更前信息
				{ name: 'darftTime',type:'date',convert:dateConvert},//变更申请时间
				{ name: 'darfter',type:'string' }, //起草人
				{ name: 'operator',type:'string' }, //受理人
				{ name: 'operateTime',type:'date',convert:dateConvert}, //变更受理时间
				{ name: 'todooperator',type:'string' }, //处理人
				{ name: 'printed',type:'string',
					 convert:function(value){
						 if(value=='Y'){
							 return waybill.todoAction.i18n('pkp.waybill.todoAction.processed');
						 }else if(value=='N'){
							 return waybill.todoAction.i18n('pkp.waybill.todoAction.pending');
						 }
				}}, //处理状态
				{ name: 'todooperateTime',type:'date',convert:dateConvert},
				{ name: 'waybillRfcId',type:'string'}
			 ]
});

//变更内容模型
Ext.define('Foss.ToDo.Model.WaybillRfcModel', {
	extend: 'Ext.data.Model',
	fields: [
			    { name: 'rfcItems',type:'string' }, //变更项
				{ name: 'beforeRfcInfo',type:'string' }, //变更前信息
				{ name: 'afterRfcInfo',type:'string' }//变更后信息
			 ]
});

//打印模型
Ext.define('Foss.ToDo.Model.LabeledGoodModel', {
	extend: 'Ext.data.Model',
	fields: [
	         	{ name: 'id',type:'string' },//打印ID
				{ name: 'labeledGoodId',type:'string' },//打印ID
			    { name: 'serialNo',type:'string' }, //流水号
				{ name: 'printed',type:'string' }, //是否已打印
				{ name: 'waybillRfcId',type:'string' }, //是否已打印
				{ name: 'printTime',type:'date',convert:dateConvert},//打印时间
				{ name: 'remindTime',type:'date',convert:dateConvert}//可见时间
			 ]
});

//创建一个待办事项枚举store
Ext.define('Foss.ToDo.Store.ToDoTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
		
//创建一个待办事项store
Ext.define('Foss.ToDo.Store.ToDoActionStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.ToDo.Model.ToDoActionModel',
	//是否自动查询
	autoLoad: true,
	//默认每页数据大小
	pageSize:10,
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		url:waybill.realPath('queryTodoAction.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.todoActionDtoList',
			//返回总数
			totalProperty : 'totalCount'
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(s, operation, eOpts) {
			var queryParams = waybill.todoAction.ToDoActionForm.getValues();
			var form = waybill.todoAction.ToDoActionForm.getForm();
			if(!form.isValid())
			{
				return false;
			}
			Ext.apply(operation, {
				params : {					
						'vo.todoConditionDto.waybillNo': queryParams.waybillNo,
						'vo.todoConditionDto.status': queryParams.status,
						'vo.todoConditionDto.isDestiChage': queryParams.isDestiChage,
						'vo.todoConditionDto.darftOrgCode': queryParams.darftOrgCode,
						'vo.todoConditionDto.operateOrgCode': queryParams.operateOrgCode,
						'vo.todoConditionDto.handlerOverNo': queryParams.handlerOverNo,
						'vo.todoConditionDto.loadNo': queryParams.loadNo,
						'vo.todoConditionDto.remainTimeBegin': queryParams.remainTimeBegin,
						'vo.todoConditionDto.remainTimeEnd': queryParams.remainTimeEnd,
						'vo.todoConditionDto.operateTimeBegin': queryParams.operateTimeBegin,
						'vo.todoConditionDto.operateTimeEnd': queryParams.operateTimeEnd
					}
			});	
		}
	}
});

//创建一个变更内容store
Ext.define('Foss.ToDo.Store.WaybillRfcStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.ToDo.Model.WaybillRfcModel',
	//是否自动查询
	autoLoad: false,
	proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
	}
});

//创建一个打印内容store
Ext.define('Foss.ToDo.Store.LabeledGoodStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.ToDo.Model.LabeledGoodModel',
	//是否自动查询
	autoLoad: false,
	//定义一个代理对象
	proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
	}
});

//查询条件
Ext.define('Foss.ToDo.Form.ToDoActionForm',{
	extend:'Ext.form.Panel',
	title: waybill.todoAction.i18n('pkp.waybill.todoAction.queryCondition'),//查询条件,
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name:'waybillNo',
		fieldLabel:waybill.todoAction.i18n('pkp.waybill.todoAction.waybillNo'),//运单号,
		vtype: 'waybill',
		minLength:8,
		maxLength:60,
		labelWidth: 50,
		columnWidth: 0.2
	},{
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:true,
		name: 'status',
		fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.operateStatus'),//处理状态,
		columnWidth: 0.15,
		labelWidth: 60,
		value:'N',
		editable : false,
		store:Ext.create('Foss.ToDo.Store.ToDoTypeStore',
		{
		  data:{
		       'items':[
					
					{'code':'N','name':waybill.todoAction.i18n('pkp.waybill.todoAction.pending')},
					{'code':'Y','name':waybill.todoAction.i18n('pkp.waybill.todoAction.processed')},
					{'code':'','name':waybill.todoAction.i18n('pkp.waybill.todoAction.all')}
			   ]
		  }
		})	
	},{
	    xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:true,
		name: 'isDestiChage',
		fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.mudizhan')  + waybill.todoAction.i18n('pkp.waybill.todoAction.biangeng'),//是否目的站更改
		columnWidth: 0.15,
		labelWidth: 80,
		value:'',
		editable : false,
		store:Ext.create('Foss.ToDo.Store.ToDoTypeStore',
		{
		  data:{
		       'items':[
					
					{'code':'N','name':waybill.todoAction.i18n('pkp.waybill.todoAction.not')},
					{'code':'Y','name':waybill.todoAction.i18n('pkp.waybill.todoAction.yes')},
					{'code':'','name':waybill.todoAction.i18n('pkp.waybill.todoAction.all')}
			   ]
		  }
		})	
	},{
		xtype: 'rangeDateField',
		fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.todoremainTime'),//入库时间,
		dateType: 'datetimefield_date97',
		fieldId: 'FOSS_todoAction_Id1',
		fromName: 'remainTimeBegin',
		toName: 'remainTimeEnd',
		disallowBlank:true,
		editable:false,
		fromValue: Ext.Date.format(waybill.todoAction.getTargetDate(new Date(),-6),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(waybill.todoAction.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: .50
	},{
		xtype : 'dynamicorgcombselector',
		name: 'darftOrgCode',
		fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.darftOrgName'),//变更申请部门,
		columnWidth: 0.25
	},{
		xtype : 'dynamicorgcombselector',
		name: 'operateOrgCode',
		fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.handleOrgName'),//变更受理部门,
		columnWidth: 0.25
	},{
		xtype: 'rangeDateField',
		fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.todooperateTime'),//更改受理时间,
		dateType: 'datetimefield_date97',
		fieldId: 'FOSS_todoAction_Id',
		fromName: 'operateTimeBegin',
		toName: 'operateTimeEnd',
		editable:false,
		//fromValue: Ext.Date.format(waybill.todoAction.getTargetDate(new Date(),-6),'Y-m-d H:i:s'),
		//toValue: Ext.Date.format(waybill.todoAction.getTargetDate1(new Date(),0),'Y-m-d H:i:s'),
		columnWidth: .50
	},{
		name:'handlerOverNo',
		labelWidth:60,
		value: waybill.todoAction.handOverBillNo,
		fieldLabel:waybill.todoAction.i18n('pkp.waybill.todoAction.handlerOverNo'),//交接单号,
		xtype: 'textfield',
		minLength:8,
		maxLength:50,
		columnWidth: 0.20
	},{
		name:'loadNo',
		labelWidth:60,
		fieldLabel:waybill.todoAction.i18n('pkp.waybill.todoAction.loadNo'),//交接单号,
		xtype: 'textfield',
		minLength:8,
		maxLength:50,
		columnWidth: 0.20
	},{
		xtype: 'displayfield',
		columnWidth : .60,
		name:'attention',
		fieldStyle:'color:red;font-size:2px;',
		value:'请注意:1、是否入库特殊组织:如是,请先入库  2、走货路径是否正常:①请用当前库存部门(外场请用外场驻地出发部门)-提货网点-运输性质查询②走货路径对应每条线路对应的计划出发、到达、经停时间配置是否为空'//交接单号,
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:waybill.todoAction.i18n('pkp.waybill.todoAction.reset'),//重置,
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.findField('waybillNo').setValue("");
				form.findField('loadNo').setValue("");
				form.findField('handlerOverNo').setValue("");
				form.findField('status').setValue('N');
				form.findField('isDestiChage').setValue("");
				form.findField('darftOrgCode').setValue("");
				form.findField('operateOrgCode').setValue("");
				form.findField('remainTimeBegin').setValue(Ext.Date.format(waybill.todoAction.getTargetDate(new Date(),-6),'Y-m-d H:i:s'));
				form.findField('remainTimeEnd').setValue(Ext.Date.format(waybill.todoAction.getTargetDate1(new Date(),0),'Y-m-d H:i:s'));
				form.findField('operateTimeBegin').setValue("");
				form.findField('operateTimeEnd').setValue("");
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:waybill.todoAction.i18n('pkp.waybill.todoAction.query'),//查询,
			disabled:!waybill.todoAction.isPermission('todoactionindex/todoactionindexquerybutton'),
			hidden:!waybill.todoAction.isPermission('todoactionindex/todoactionindexquerybutton'),
			cls:'yellow_button',
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				//入库可见时间根据两个时间进行对比，结束时间比开始时间还小
				var remainTimeBegin = form.getValues().remainTimeBegin;
				var remainTimeEnd = form.getValues().remainTimeEnd;
				var result = Ext.Date.parse(remainTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(remainTimeBegin,'Y-m-d H:i:s');
				if(result / (24 * 60 * 60 * 1000) >=30){
					Ext.ux.Toast.msg(waybill.todoAction.i18n('pkp.waybill.todoAction.tip'), waybill.todoAction.i18n('foss.pkp.waybill.waybillManagerService.exception.overThirtyDays'), 'error', 3000);
					return;
				}
				//操作时间根据两个时间进行对比，结束时间比开始时间还小
				var operateTimeBegin = form.getValues().operateTimeBegin;
				var operateTimeEnd = form.getValues().operateTimeEnd;
				var result = Ext.Date.parse(operateTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(operateTimeBegin,'Y-m-d H:i:s');
				if(result / (24 * 60 * 60 * 1000) >=30){
					Ext.ux.Toast.msg(waybill.todoAction.i18n('pkp.waybill.todoAction.tip'), waybill.todoAction.i18n('foss.pkp.waybill.waybillManagerService.exception.overThirtyDays'), 'error', 3000);
					return;
				}
				if(form.isValid())
				{
					waybill.todoAction.pagingBar.moveFirst();
				}
				else
				{
					Ext.ux.Toast.msg(waybill.todoAction.i18n('pkp.waybill.todoAction.tip'), waybill.todoAction.i18n('pkp.waybill.todoAction.waybillNoError'), 'error', 3000);
				}
			}
		}]
	}]
});
	
//待办事项
Ext.define('Foss.ToDo.ToDoActionGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	emptyText:waybill.todoAction.i18n('pkp.waybill.todoAction.emptyText'),//查询结果为空！,
	id:'Foss_ToDo_ToDoActionGrid_ID',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//增加表格列的分割线
	columnLines: true,
	//定义表格的标题
	title:waybill.todoAction.i18n('pkp.waybill.todoAction.todo'),//待办事项,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	collapsible: true,
	animCollapse: true,
	store: null,
	//表格行可展开的插件
	plugins : [{
			header : true,
			ptype : 'rowexpander',
			// 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowsExpander : false,
			layout : 'hbox',
			// 行体内容
			rowBodyElement : 'Foss.waybill.todoaction.handoverbillPanel'
			}],
	//----------定义某行的颜色开始---------------      
	  viewConfig :{
	    //显示重复样式，不用隔行显示
	     stripeRows: false,
	     enableTextSelection: true,
	    getRowClass:function(record, rowIndex, p, ds){
	    //如果库存天数大于90天，标记为粉红色
	        if(record.get('status') == 'N'){
	          return 'toDoAction_mark_color';
	      }
	     }
	  },  
	//----------定义某行的颜色结束---------------    
	  dockedItems : [ {
          xtype : 'toolbar',
          dock : 'top',
          layout : 'column',
          items : [
                {
					xtype:'button',
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						seconds: 3
					}),
					margin : '0 0 0 15',
					columnWidth:.082,
					name:'exportTodoActionInfo',
					text:'导出',
					handler : function(){
						var toDoActionForm = waybill.todoAction.ToDoActionForm;
						if (toDoActionForm != null) {
							var queryParams = toDoActionForm.getValues();
							if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
							}	
							//获取查询出来的异常信息
							var toDoActionGridStore = waybill.todoAction.ToDoActionGrid.getStore();	
							//若异常信息不为空
							if(toDoActionGridStore.getCount()!=0){
								Ext.Ajax.request({
									url:waybill.realPath('exportTodoAction.action'),
									form: Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : {
										'todoActionVo.todoConditionDto.waybillNo': queryParams.waybillNo,
										'todoActionVo.todoConditionDto.status': queryParams.status,
										'todoActionVo.todoConditionDto.isDestiChage': queryParams.isDestiChage,
										'todoActionVo.todoConditionDto.darftOrgName': queryParams.darftOrgName,
										'todoActionVo.todoConditionDto.operateOrgName': queryParams.operateOrgName,
										'todoActionVo.todoConditionDto.handlerOverNo': queryParams.handlerOverNo,
										'todoActionVo.todoConditionDto.loadNo': queryParams.loadNo,
										'todoActionVo.todoConditionDto.operateTimeBegin': queryParams.operateTimeBegin,
										'todoActionVo.todoConditionDto.operateTimeEnd': queryParams.operateTimeEnd,
										'todoActionVo.todoConditionDto.remainTimeBegin': queryParams.remainTimeBegin,
										'todoActionVo.todoConditionDto.remainTimeEnd': queryParams.remainTimeEnd
									},
									isUpload: true
								});
							}else{
								//或者提示不能导出
								Ext.ux.Toast.msg('提示','没有记录，不能导出', 'error', 3000);
							}
						}}
				},{
                  xtype : 'image',
                  margin : '0 0 0 15',
                  columnWidth:.05,
                  imgCls : 'foss_icons_pkp_goodsNumDisac'
                },
                {
                  xtype : 'label',
                  columnWidth:.06,
                  margin : '0 0 0 10',
                  text : waybill.todoAction.i18n('pkp.waybill.todoAction.pending') //未处理
                },{
                	xtype : 'label',
					margin : '0 0 0 10',
					html :'&nbsp;&nbsp;',
					height:15,
					width:25,
					style:'background-color:green;',
                    columnWidth:.05
                  },
                  {
                    xtype : 'label',
                    columnWidth:.08,
                    margin : '0 0 0 10',
                    text : waybill.todoAction.i18n('pkp.waybill.todoAction.notInstock') //未在本库存
                  }
             ]
	  }],
	  
	//定义表格列信息
	columns: [
		{
			xtype:'actioncolumn',
			flex:0.02,
			text: waybill.todoAction.i18n('pkp.waybill.todoAction.operating'),//操作,
			align: 'center',
			items: [{
				iconCls: 'deppon_icons_showdetail',
				tooltip: waybill.todoAction.i18n('pkp.waybill.todoAction.lookOver'),//查看,
				disabled:!waybill.todoAction.isPermission('todoactionindex/todoactionindexviewbutton'),
				handler: function(grid, rowIndex, colIndex) {
					var win = Ext.create('Foss.waybill.QueryWayBillWindow').show();
					var selection = grid.getStore().getAt(rowIndex);
					var	waybillRfcId = selection.get('waybillRfcId');
					
					Ext.Ajax.request({
						url:waybill.realPath('queryLabeledGood.action'),
						params:{'vo.todoConditionDto.waybillRfcId':waybillRfcId},
						success:function(response){
							var result = Ext.decode(response.responseText);	
							var formModel = new Foss.ToDo.Model.TodoRfcModel(result.vo.labeledGoodTodoDto) ;
							waybill.todoAction.waybillRfcForm.loadRecord(formModel);
							
							waybill.todoAction.waybillRfcGrid.store.loadData(result.vo.labeledGoodTodoDto.waybillRfcChangeDetailEntityList);

							waybill.todoAction.labeledGoodForm.loadRecord(formModel);
							
							//waybill.todoAction.labeledGoodForm.down('form').loadRecord(formModel);
							waybill.todoAction.labeledGoodForm.items.items[0].getSelectionModel().deselectAll();
							waybill.todoAction.labeledGoodForm.items.items[0].store.loadData(result.vo.labeledGoodTodoDto.labeledGoodTodoEntityList);
						}
					});
					
				}
			}]
		},{
			header:'id',//id,
			dateIndex:'waybillRfcId',
			hidden: true,
			align: 'center'
		},{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.waybillNo'),//运单号, 
			//关联model中的字段名
			dataIndex: 'waybillNo',
			flex:0.07,
			align: 'center',
			xtype: 'openwindowcolumn', 
            windowClassName:'dpap.openwindowcolumn.Window'
		},{ 
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.operateStatus'),//处理状态, 
			//关联model中的字段名
			dataIndex: 'status',
			hidden: true,
			align: 'center'
		},{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.darftOrgName'),//变更申请部门, 
			//关联model中的字段名
			dataIndex: 'darftOrgName', 
			xtype: 'ellipsiscolumn',
			flex:0.11,
			align: 'center'
		},{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.handleOrgName'),//变更受理部门, 
			//关联model中的字段名
			dataIndex: 'operateOrgName', 
			xtype: 'ellipsiscolumn',
			flex:0.11,
			align: 'center'
		},{
			text : waybill.todoAction.i18n('pkp.waybill.todoAction.mudizhan') + '<br>' + waybill.todoAction.i18n('pkp.waybill.todoAction.biangeng'),//是否目的站更改
			align : 'center',
			flex:0.04,
			dataIndex : 'isDestiChage',
			renderer:function(value){
				if(value=='Y'){
					return waybill.todoAction.i18n('foss.pricing.isYes');//是
				}else if(value=='N'){
					return waybill.todoAction.i18n('foss.pricing.isNo');//否
				}else{
					return value;
				}
			}
		},{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.rfcInfo'),//更改内容, 
			//关联model中的字段名
			dataIndex: 'rfcInfo', 
			xtype: 'ellipsiscolumn',
			flex:0.3,
			align: 'center'
		},{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.biangeng') + '<br>' + waybill.todoAction.i18n('pkp.waybill.todoAction.shenqingren'),//变更申请人, 
			//关联model中的字段名
			dataIndex: 'darfter', 
			flex:0.05,
			align: 'center'
		},{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.todooperateTime'),//更改受理时间, 
			//关联model中的字段名
			dataIndex: 'operateTime', 
			align: 'center',
			flex:0.14,
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}else{
					return null;
				}
			} 
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.ToDo.Store.ToDoActionStore');
			//添加分页工具条
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store,
//  				plugins: 'pagesizeplugin',
				plugins: {
            		ptype: 'pagesizeplugin',
           	 		//超出输入最大限制是否提示，true则提示，默认不提示
            		alertOperation: true,
            		//自定义分页comobo数据
            		sizeList: [['5', 5], ['10', 10], ['20', 20], ['50', 50], ['100', 100], ['200', 200], ['500', 500], ['1000', 1000]],
            		//入最大限制，默认为200
            		maximumSize: 1000
            	},
				displayInfo: true
			});
			waybill.todoAction.pagingBar = me.bbar;
			me.callParent([cfg]);
		}
	});

Ext.define('Foss.waybill.todoaction.handoverbillPanel', {
	extend : 'Ext.panel.Panel',
	layout : {
		type : 'column'
	},
	handoverbillGrid : null,
	getHandoverbillGrid : function() {
		if (this.handoverbillGrid == null) {
			this.handoverbillGrid = Ext.create("Foss.waybill.todoaction.handoverbillGrid",{
				columnWidth : 1
			});
		}
		return this.handoverbillGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getHandoverbillGrid()];
		me.callParent([cfg]);
	},
	bindData : function(record, grid, rowBodyElement) {
		// 绑定表格数据到表单上
		Ext.Ajax.request({
					url : waybill.realPath('queryLatestHandoverDto.action'),
					timeout: 600000,
					params : {
						'vo.todoConditionDto.waybillRfcId' : record
								.get('waybillRfcId')
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (!Ext.isEmpty(result.vo.latestHandoverDtoList)) {
						rowBodyElement.getHandoverbillGrid().getStore()
								.loadData(result.vo.latestHandoverDtoList);
						}
					}
				});
		//rowBodyElement.getWaybillInfo().loadRecord(record);
	}
});

//显示结果右下grid
Ext.define('Foss.waybill.todoaction.handoverbillGrid', {
	extend : 'Ext.grid.Panel',
	frame: true,  
	viewConfig :{
	   //显示重复样式，不用隔行显示
	   stripeRows: false,
	   enableTextSelection: true,
	   getRowClass:function(record, rowIndex, p, ds){
		   //如果库存天数大于90天，标记为粉红色
		   if(record.get('exceptMsg') == 'ERROR'){
			   return 'failToDoAction_mark_color';
		   }else{
			   if(record.get('isInstock') == 'N'){
				   return 'toDoAction_mark_color_blur';
			   }
		   }
	   }
	}, 
	defaults : {
		margin:'5 10 5 10',
		labelWidth : 60
	},
	store : Ext.create('Ext.data.Store', {
				fields : ['waybillNo', 'serialNo', 'productName',
				          'receiveCustomerName', 'customerPickUpOrgName',
				          'handoverBillNo','vehicleAssembleNo','exceptMsg','isInstock'],
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				}
			}),
	//自动收缩高度
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	columns : [{
				xtype : 'rownumberer',
				text : waybill.todoAction.i18n('pkp.waybill.exceptMsgAction.number'), //序号,
				align : 'center',
				flex:0.04
			},{ 
				//字段标题
				header: waybill.todoAction.i18n('pkp.waybill.todoAction.operateStatus'),//处理状态, 
				//关联model中的字段名
				dataIndex: 'exceptMsg',
				hidden: true,
				align: 'center'
			},{ 
				//字段标题
				header: waybill.todoAction.i18n('pkp.waybill.todoAction.operateStatus'),//处理状态, 
				//关联model中的字段名
				dataIndex: 'isInstock',
				hidden: true,
				align: 'center'
			}, {
				header : waybill.todoAction.i18n('pkp.waybill.todoAction.waybillNo'), //运单号,
				align : 'center',
				dataIndex : 'waybillNo',
				flex:0.1
			}, {
				header : waybill.todoAction.i18n('pkp.waybill.exceptMsgAction.serialNo'), //流水号,
				align : 'center',
				dataIndex : 'serialNo',
				flex:0.07,
				align: 'center'
			}, {
				header : waybill.todoAction.i18n('pkp.waybill.todoAction.productCode'), //产品类型
				align : 'center',
				dataIndex : 'productName',
				flex:0.1,
				align: 'center'
			}, {
				header : waybill.todoAction.i18n('pkp.waybill.todoAction.receiveCustomerName'), //收货部门,
				align : 'center',
				dataIndex : 'receiveCustomerName',
				flex:0.15
			}, {
				header : waybill.todoAction.i18n('pkp.waybill.todoAction.customerPickUpOrgName'), //提货网点,
				align : 'center',
				dataIndex : 'customerPickUpOrgName',
				flex:0.15
			}, {
				header : waybill.todoAction.i18n('pkp.waybill.todoAction.handlerOverNo'), //交接单,
				align : 'center',
				dataIndex : 'handoverBillNo',
				flex:0.17
			}, {
				header : waybill.todoAction.i18n('pkp.waybill.todoAction.loadNo'), //配载单,
				align : 'center',
				dataIndex : 'vehicleAssembleNo',
				flex:0.17
			}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}

});

Ext.define('dpap.openwindowcolumn.grid', {
    extend:'Ext.form.Panel',
    title: waybill.todoAction.i18n('pkp.waybill.todoAction.todo'),//待办事项,
    frame:true, 
    height: 280,
    width: 600,
    defaults: {
        margin:'5 10 5 10',
        anchor: '90%',
        labelWidth:120
    },
    defaultType : 'textfield',
    layout:'column',
    items: [{ 
        name: 'waybillNo',
        fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.waybillNo'),//运单号, 
        readOnly:true,
        columnWidth:1
    },{
        name: 'darftOrgName',
        fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.darftOrgName'),//变更申请部门,
        columnWidth:1,
        readOnly:true
    },{ 
        name: 'operateOrgName',
        fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.handleOrgName'),//变更受理部门, 
        columnWidth:1,
        readOnly:true
    },{ 
        name: 'rfcInfo',
        fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.rfcInfo'),//更改内容,
        columnWidth:1,
        readOnly:true
    },{ 
        name: 'darfter',
        fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.waybillRfcApplicant'),//变更申请人,
        columnWidth:1,
        readOnly:true
    },{ 
        name: 'operateTime',
        fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.todooperateTime'),//更改受理时间,  
        columnWidth:1,
        readOnly:true,
        listeners:{
        	change:function(me, newValue, oldValue,eOpts ){
        		if(newValue){
        			if(!Ext.isEmpty(newValue)){
        				me.setValue(Ext.Date.format(new Date(newValue),'Y-m-d H:i:s'));
        			}
        		}
        	}
        }
        //
        //change( Ext.form.field.Field this, Object newValue, Object oldValue, Object eOpts )
      
    }],
    bindData: function(record){
        this.getForm().loadRecord(record);
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});
Ext.define('dpap.openwindowcolumn.Window', {
    extend : 'Ext.window.Window',
    modal : true,
    closeAction : 'hide',
    width : 650,
    height : 350,
    openwindowcolumnForm : null,
    getOpenwindowcolumnForm : function(){
        if(this.openwindowcolumnForm == null){
            this.openwindowcolumnForm = Ext.create('dpap.openwindowcolumn.grid');
        }   
        return this.openwindowcolumnForm;
    },
    bindData : function(record, cellIndex, rowIndex){
        this.getOpenwindowcolumnForm().getForm().loadRecord(record);
    },
    initComponent :function(config){
        var me = this ,cfg = Ext.apply({}, config);
        me.items = [ me.getOpenwindowcolumnForm() ];
        me.callParent(cfg);
    }
});
//变更基本信息
Ext.define('Foss.ToDo.Form.WaybillRfcForm',{
	extend:'Ext.form.Panel',	
	title: waybill.todoAction.i18n('pkp.waybill.todoAction.waybillRfcInfo'),//变更基本信息,
	frame:true,
	collapsible: true,
	animCollapse: true,
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items:[
		{
			name: 'waybillNo',
			fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.waybillNo'),//运单号,
			labelWidth: 85,
			readOnly: true,
			columnWidth: .33
		},{
			name:'darftOrgName',
			fieldLabel:waybill.todoAction.i18n('pkp.waybill.todoAction.darftOrgName'),//变更申请部门,
			labelWidth: 85,
			readOnly: true,
			columnWidth: .33
		},{
			xtype: 'datefield',
			name: 'darftTime',
			fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.darftTime'),//变更申请时间,
			labelWidth: 85,
			readOnly: true,
			columnWidth: .33,
			format : 'Y-m-d H:i:s'
		},{
			name:'waybillRfcId',
			hidden: true,
			columnWidth: .01
		},{
			name:'darfter',
			fieldLabel:waybill.todoAction.i18n('pkp.waybill.todoAction.waybillRfcApplicant'),//变更申请人,
			labelWidth: 85,
			readOnly: true,
			columnWidth: .33
		},{
			name:'operator',
			fieldLabel:waybill.todoAction.i18n('pkp.waybill.todoAction.waybillRfcOperator'),//变更受理人,
			labelWidth: 85,
			readOnly: true,
			columnWidth: .33
		},{
			xtype: 'datefield',
			name: 'operateTime',
			fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.operateTime'),//变更受理时间,
			labelWidth: 85,
			readOnly: true,
			columnWidth: .33,
			format : 'Y-m-d H:i:s'
		}]
});

//变更内容
Ext.define('Foss.ToDo.Grid.WaybillRfcGrid', {
	extend:'Ext.grid.Panel',
	//增加表格列的分割线
	columnLines: true,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//表格对象增加一个边框
	frame: true,
	stripeRows: true,
	//定义表格的标题
	title:waybill.todoAction.i18n('pkp.waybill.todoAction.waybillRfcApplication'),//变更申请内容,
	collapsible: true,
	animCollapse: true,
	height: 160,
	store: Ext.create('Foss.ToDo.Store.WaybillRfcStore'),	 
	//定义表格列信息
	columns: [
		{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.rfcItems'),//变更项, 
			//关联model中的字段名
			dataIndex: 'rfcItems',
			align: 'center',
			width:200			
		},{ 
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.beforeRfcInfo'),//变更前信息, 
			//关联model中的字段名
			dataIndex: 'beforeRfcInfo',
			align: 'center',
			width:280
		},{
			//字段标题
			header: waybill.todoAction.i18n('pkp.waybill.todoAction.afterRfcInfo'),//变更后信息, 
			//关联model中的字段名
			dataIndex: 'afterRfcInfo', 
			align: 'center',
			width:280
		}]
	});	
	
var serialnos = '';	

////打印标签
Ext.define('Foss.ToDo.Form.LabeledGoodForm',{
	extend:'Ext.form.Panel',	
	title: waybill.todoAction.i18n('pkp.waybill.todoAction.printLabels'),//打印标签,
	frame:true,
	layout:'column',
	items:[{
		xtype:'grid',
		columnWidth:.6,
		height : 160,
		stripeRows: true,
		selModel:Ext.create('Ext.selection.CheckboxModel'),
		store: Ext.create('Foss.ToDo.Store.LabeledGoodStore'),
		//定义表格列信息
		columns: [
			{
				header:waybill.todoAction.i18n('pkp.waybill.todoAction.number'),//序号,
				dataIndex:'id',
				hidden: true,
				align: 'center'
			},{
				header:waybill.todoAction.i18n('pkp.waybill.todoAction.number'),//序号,
				dataIndex:'labeledGoodId',
				hidden: true,
				align: 'center'
			},{
				//字段标题
				header: waybill.todoAction.i18n('pkp.waybill.todoAction.serialNo'),//运单流水号, 
				//关联model中的字段名
				dataIndex: 'serialNo',
				align: 'center'
			},{ 
				//字段标题
				header: waybill.todoAction.i18n('pkp.waybill.todoAction.printed'),//已打印, 
				//关联model中的字段名
				dataIndex: 'printed',
				width:70,
				renderer : function(value, metadata, record, rowIndex, columnIndex, store){
					if(record.get('printed')=='Y'){
						return waybill.todoAction.i18n('pkp.waybill.todoAction.yes');
					}else{
						return waybill.todoAction.i18n('pkp.waybill.todoAction.not');
					}
				},
				align: 'center'
			},{
				//字段标题
				header: waybill.todoAction.i18n('pkp.waybill.todoAction.printTime'),//打印时间, 
				//关联model中的字段名
				dataIndex: 'printTime',
				align: 'center',
				width:130,
				renderer:function(value){
					if(value!=null){
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					}else{
						return null;
					}
				} 
			},{
				//字段标题
				header: waybill.todoAction.i18n('pkp.waybill.todoAction.todoremainTime'),//入库时间, 
				//关联model中的字段名
				dataIndex: 'remindTime',
				align: 'center',
				width:130,
				renderer:function(value){
					if(value!=null){
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					}else{
						return null;
					}
				} 
			}],
			bbar :[{
				xtype: 'button',
		        text:'打印有Logo标签',//打印标签,
		        disabled:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
		        hidden:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
				handler: function(grid, rowIndex, colIndex) {
					var waybillNo = waybill.todoAction.waybillRfcForm.getForm().findField('waybillNo').getValue();
					var selectRow = waybill.todoAction.labeledGoodForm.items.items[0].getSelectionModel().getSelection();
					if(selectRow.length==0){
						Ext.Msg.alert(waybill.todoAction.i18n('pkp.waybill.todoAction.tip'),waybill.todoAction.i18n('pkp.waybill.todoAction.choseOperateData'));
						return;
					}
					Ext.Msg.confirm( waybill.todoAction.i18n('pkp.waybill.todoAction.tip'), waybill.todoAction.i18n('pkp.waybill.todoAction.makeSure'), function(btn,text){
					if(btn=="yes"){
						//定义流水号数组
						var serialNos = new Array();
						var entity =null;
						for(var i = 0;i<selectRow.length;i++){
							entity =new Object();
							entity.serialNo =selectRow[i].data.serialNo;
							entity.id =selectRow[i].data.id;
							entity.labeledGoodId =selectRow[i].data.labeledGoodId;
							entity.waybillRfcId =selectRow[i].data.waybillRfcId;
							entity.printed =selectRow[i].data.printed;
							serialNos.push(entity);
							selectRow[i].set('printed',true);
							selectRow[i].commit();
						}
						
						var newVo = {
							'vo' : {
								'todoConditionDto' : {
									'waybillNo' : waybillNo,
									'labeledGoodTodoEntityList' : serialNos,
									'waybillRfcId':selectRow[0].data.waybillRfcId
								}
							}
						}
						
						Ext.Ajax.request({
						    url:waybill.realPath('updateLabeledPrintStatus.action'),
						    jsonData: newVo,
						    success: function(response){
						    	var json = Ext.decode(response.responseText);
						    	var printDtoList = json.vo.barcodePrintLabelDto;
						    	d_prtlabel(printDtoList);
						    	alert("打印成功！");
						   },
    	        			exception : function(response) {
    	        				var result = Ext.decode(response.responseText);
						          Ext.ux.Toast.msg(waybill.todoAction.i18n('pkp.waybill.todoAction.tip'), result.message, 'error', 3000);
    	        			}
						});
					}
				});
				}
			},{
				xtype: 'button',
		        text: '打印无Logo标签',//打印标签,
		        disabled:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
		        hidden:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
				handler: function(grid, rowIndex, colIndex) {
					var waybillNo = waybill.todoAction.waybillRfcForm.getForm().findField('waybillNo').getValue();
					var selectRow = waybill.todoAction.labeledGoodForm.items.items[0].getSelectionModel().getSelection();
					if(selectRow.length==0){
						Ext.Msg.alert(waybill.todoAction.i18n('pkp.waybill.todoAction.tip'),waybill.todoAction.i18n('pkp.waybill.todoAction.choseOperateData'));
						return;
					}
					Ext.Msg.confirm( waybill.todoAction.i18n('pkp.waybill.todoAction.tip'), waybill.todoAction.i18n('pkp.waybill.todoAction.makeSure'), function(btn,text){
					if(btn=="yes"){
						//定义流水号数组
						var serialNos = new Array();
						var entity =null;
						for(var i = 0;i<selectRow.length;i++){
							entity =new Object();
							entity.serialNo =selectRow[i].data.serialNo;
							entity.id =selectRow[i].data.id;
							entity.labeledGoodId =selectRow[i].data.labeledGoodId;
							entity.waybillRfcId =selectRow[i].data.waybillRfcId;
							entity.printed =selectRow[i].data.printed;
							serialNos.push(entity);
							selectRow[i].set('printed',true);
							selectRow[i].commit();
						}
						
						var newVo = {
							'vo' : {
								'todoConditionDto' : {
									'waybillNo' : waybillNo,
									'labeledGoodTodoEntityList' : serialNos,
									'waybillRfcId':selectRow[0].data.waybillRfcId
								}
							}
						}
						
						Ext.Ajax.request({
						    url:waybill.realPath('updateLabeledPrintStatus.action'),
						    jsonData: newVo,
						    success: function(response){
						    	var json = Ext.decode(response.responseText);
						    	var printDtoList = json.vo.barcodePrintLabelDto;
						    	d_prtlabels(printDtoList);
						    	alert("打印成功！");
						   },
    	        			exception : function(response) {
    	        				var result = Ext.decode(response.responseText);
						          Ext.ux.Toast.msg(waybill.todoAction.i18n('pkp.waybill.todoAction.tip'), result.message, 'error', 3000);
    	        			}
						});
					}
				});
				}
			},{
				xtype: 'button',
		        text: waybill.todoAction.i18n('pkp.waybill.todoAction.getNotPrinted'),//'选中未打印',//
		        disabled:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
		        hidden:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
				handler: function(grid, rowIndex, colIndex) {
					//waybill_todoaction_printLabels
					var me = this.up('toolbar').up('grid'),
					store = me.getStore(),
					records = store.getRange(),choose = [];
					//var array = Ext.getCmp('waybill_todoaction_printLabels').items;
					for(var i = 0; i < records.length; i ++){
						if('N'==records[i].get('printed')){
							choose.push(records[i]);
						}
					}
	        		if(choose !=null&&choose.length>0){
	        			me.getSelectionModel().select(choose);
	        		}
				}
			},{
				xtype: 'button',
		        text: waybill.todoAction.i18n('pkp.waybill.todoAction.reGetNotPrinted'),//'反向选择',//
		        disabled:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
		        hidden:!waybill.todoAction.isPermission('todoactionindex/todoactionindexprintbutton'),
				handler: function(grid, rowIndex, colIndex) {
					//waybill_todoaction_printLabels
					var me = this.up('toolbar').up('grid'),
					store = me.getStore(),
					records = store.getRange(),choose = [];
					//得到选中列
					var selectRow = me.getSelectionModel().getSelection();
					if (selectRow != null && selectRow.length >= 0){
						for(var i = 0; i < selectRow.length; i ++){
								choose.push(selectRow[i]);
						}
						me.getSelectionModel().selectAll(true);
						me.getSelectionModel().deselect(choose,true);
					}else {
						me.getSelectionModel().selectAll(true);
					}
				}
			}]			
	},{
		xtype: 'container',
		html: '&nbsp;',
		columnWidth: .1
	},{
		xtype:'form',
		columnWidth:.35,
		height : 100,
		width : 290,
		layout: 'vbox',
		margin: '50 10 10 10',
		items:[
			{
				xtype: 'textfield',
				name: 'todooperator',
				fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.handler'),//处理人,
				readOnly: true,
				labelWidth: 85
			},/**{
				xtype: 'textfield',
				name:'status',
				fieldLabel:waybill.todoAction.i18n('pkp.waybill.todoAction.operateStatus'),//处理状态,
				readOnly: true,
				labelWidth: 85
			},*/{
				xtype: 'datefield',
				name: 'todooperateTime',
				fieldLabel: waybill.todoAction.i18n('pkp.waybill.todoAction.handlerTime'),//处理时间,
				readOnly: true,
				labelWidth: 85,
				format : 'Y-m-d H:i:s'
			}],
			bbar :[{
				xtype: 'button',
				text : '打印更改明细',
				margin: '10 0 0 150',
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				handler : function() {
					var waybillRfcId = waybill.todoAction.waybillRfcForm.getForm().findField('waybillRfcId').getValue();
					var waybillNo = waybill.todoAction.waybillRfcForm.getForm().findField('waybillNo').getValue();
					do_printpreview('todoAction', {'waybillNo' : waybillNo,'waybillRfcId' : waybillRfcId, 'currentUserDepCode' : FossUserContext.getCurrentDeptCode()}, ContextPath.PKP_DELIVER);
				}
			}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
	}]
	
});


function d_prtlabel(printDtoList){
	if(printDtoList!=null && printDtoList.length>0){
		  Ext.data.JsonP.request({
			  url: 'http://localhost:8077/print',
			  callbackKey: 'callback',
	    	  params: {
	    	  	 lblprtworker:'CommLabelPrintWorker',
	    		 addr1:printDtoList[0].addr1,
				 addr2:printDtoList[0].addr2,
				 addr3:printDtoList[0].addr3,
				 addr4:printDtoList[0].addr4,
				 location1:printDtoList[0].location1,
				 location2:printDtoList[0].location2,
				 location3:printDtoList[0].location3,
				 location4:printDtoList[0].location4,
				 optusernum:printDtoList[0].optuserNum,
				 number:printDtoList[0].waybillNumber,
		 		 serialnos:printDtoList[0].printSerialnos,
				 leavecity:printDtoList[0].leavecity,
				 destination:printDtoList[0].destination,
				 isagent:printDtoList[0].isAgent,
				 stationnumber:printDtoList[0].destinationCode,
				 deptno:printDtoList[0].lastTransCenterNo,
				 finaloutfieldid:printDtoList[0].finaloutfieldid,
				 finaloutname:printDtoList[0].lastTransCenterCity,
				 weight:printDtoList[0].weight,
				 totalpieces:printDtoList[0].totalPieces,
				 packing:printDtoList[0].packing,
				 unusual:printDtoList[0].unusual,
				 transtype:printDtoList[0].transtype,
				 printdate:printDtoList[0].printDate,
				 deliver:printDtoList[0].deliverToDoor,
				 goodstype:printDtoList[0].goodstype,
				 preassembly:printDtoList[0].preassembly,
				 signFlag:printDtoList[0].isStarFlag,
				 //@author wutao-200945
				 //DMANA-3745
				 //FOSS标签打印：货物标签左上角添加收货地址行政区域
				 countyRegion:printDtoList[0].countyRegion,
				 //FOSS标签打印：打印标签时增加“VIP”标识
				 deliveryBigCustomer:printDtoList[0].deliveryBigCustomer,
				 receiveBigCustomer:printDtoList[0].receiveBigCustomer,
				 //FOSS标签打印：打印标签时增加展会货标识
				 isExhibitCargo:printDtoList[0].isExhibitCargo,
				 isPrintLogo:'Y',
				 lastFirstFinalOutName:printDtoList[0].lastFirstTransCenterCity,
				 lastSecondFinalOutName:printDtoList[0].lastSecondTransCenterCity,
				 lastThirdFinalOutName:printDtoList[0].lastThirdTransCenterCity,
				 lastSecondFinaloutFieldId:printDtoList[0].lastSecondFinaloutFieldId,
				 lastThirdFinaloutFieldId:printDtoList[0].lastThirdFinaloutFieldId,
				 simpleLeaveCity:printDtoList[0].simpleLeaveCity,
				 sortingResult:printDtoList[0].sortingResult
	    	  },		    
			     success: function(result, request) {
					 var ret_code = result.data.code;
					 if(ret_code==1){
						 d_prtlabel(printDtoList.slice(1));
					 }
					 else {
						 alert(result.data.msg);	 
					 }
			     },
			     failure : function (result, request) {
			      	 var ret_code = result.data.code;
					 alert(result.data.msg);
			     }
	    	});
	}
}
function d_prtlabels(printDtoList){
	if(printDtoList!=null && printDtoList.length>0){
		  Ext.data.JsonP.request({
			  url: 'http://localhost:8077/print',
			  callbackKey: 'callback',
	    	  params: {
	    	  	 lblprtworker:'CommLabelPrintWorker',
	    		 addr1:printDtoList[0].addr1,
				 addr2:printDtoList[0].addr2,
				 addr3:printDtoList[0].addr3,
				 addr4:printDtoList[0].addr4,
				 location1:printDtoList[0].location1,
				 location2:printDtoList[0].location2,
				 location3:printDtoList[0].location3,
				 location4:printDtoList[0].location4,
				 optusernum:printDtoList[0].optuserNum,
				 number:printDtoList[0].waybillNumber,
		 		 serialnos:printDtoList[0].printSerialnos,
				 leavecity:printDtoList[0].leavecity,
				 destination:printDtoList[0].destination,
				 isagent:printDtoList[0].isAgent,
				 stationnumber:printDtoList[0].destinationCode,
				 deptno:printDtoList[0].lastTransCenterNo,
				 finaloutfieldid:printDtoList[0].finaloutfieldid,
				 finaloutname:printDtoList[0].lastTransCenterCity,
				 weight:printDtoList[0].weight,
				 totalpieces:printDtoList[0].totalPieces,
				 packing:printDtoList[0].packing,
				 unusual:printDtoList[0].unusual,
				 transtype:printDtoList[0].transtype,
				 printdate:printDtoList[0].printDate,
				 deliver:printDtoList[0].deliverToDoor,
				 goodstype:printDtoList[0].goodstype,
				 preassembly:printDtoList[0].preassembly,
				 signFlag:printDtoList[0].isStarFlag,
				 //@author wutao-200945
				 //DMANA-3745
				 //FOSS标签打印：货物标签左上角添加收货地址行政区域
				 countyRegion:printDtoList[0].countyRegion,
				 //FOSS标签打印：打印标签时增加“VIP”标识
				 deliveryBigCustomer:printDtoList[0].deliveryBigCustomer,
				 receiveBigCustomer:printDtoList[0].receiveBigCustomer,
				 //FOSS标签打印：打印标签时增加展会货标识
				 isExhibitCargo:printDtoList[0].isExhibitCargo,
				 isPrintLogo:'N',
				 lastFirstFinalOutName:printDtoList[0].lastFirstTransCenterCity,
				 lastSecondFinalOutName:printDtoList[0].lastSecondTransCenterCity,
				 lastThirdFinalOutName:printDtoList[0].lastThirdTransCenterCity,
				 lastSecondFinaloutFieldId:printDtoList[0].lastSecondFinaloutFieldId,
				 lastThirdFinaloutFieldId:printDtoList[0].lastThirdFinaloutFieldId,
				 simpleLeaveCity:printDtoList[0].simpleLeaveCity,
				 sortingResult:printDtoList[0].sortingResult
	    	  },		    
			     success: function(result, request) {
					 var ret_code = result.data.code;
					 if(ret_code==1){
						 d_prtlabels(printDtoList.slice(1));
					 }
					 else {
						 alert(result.data.msg);	 
					 }
			     },
			     failure : function (result, request) {
			      	 var ret_code = result.data.code;
					 alert(result.data.msg);
			     }
	    	});
	}
}


// 定义查询条件表单
waybill.todoAction.waybillRfcForm = Ext.create('Foss.ToDo.Form.WaybillRfcForm');
waybill.todoAction.waybillRfcGrid = Ext.create('Foss.ToDo.Grid.WaybillRfcGrid');
waybill.todoAction.labeledGoodForm = Ext.create('Foss.ToDo.Form.LabeledGoodForm');
Ext.define('Foss.waybill.QueryWayBillWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	cls: 'autoHeight',
	height : 650,
	width : 850,
	items : [waybill.todoAction.waybillRfcForm,waybill.todoAction.waybillRfcGrid,waybill.todoAction.labeledGoodForm]
});

Ext.onReady(function() 
{
	Ext.QuickTips.init();
	if (Ext.getCmp('T_waybill-todoActionIndex_content')) {
		return;
	}
	waybill.todoAction.ToDoActionForm = Ext.create('Foss.ToDo.Form.ToDoActionForm'); 
	waybill.todoAction.ToDoActionGrid = Ext.create('Foss.ToDo.ToDoActionGrid'); //Ext.create('Foss.ToDo.Form.PrintLabelDetailForm')
//	waybill.todoAction.init(waybill.todoAction.ToDoActionForm);
	Ext.create('Ext.panel.Panel',{
		id: 'T_waybill-todoActionIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [waybill.todoAction.ToDoActionForm,waybill.todoAction.ToDoActionGrid],
		renderTo: 'T_waybill-todoActionIndex-body'
	});
});