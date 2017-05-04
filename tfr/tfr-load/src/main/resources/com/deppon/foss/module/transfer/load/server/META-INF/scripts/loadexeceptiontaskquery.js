var orgCode;//部门编码
//选中的数据
var short_chooseDataListStowageBill= new Array();
var dateList_st = new Array();//存放单据时间



/**
 *检查提交的表单合法性
 ***/
function checkForm(window){
	for(i=0;i<window.items.length;i++){
		if(!window.items.items[i].getForm().isValid()){
			return false;
		}
	}
	return true;
}


Ext.define('Foss.load.loadexeceptiontask.status',
	{
		extend : 'Ext.data.Store',
		fields : ['value', 'name'],
		data : [{
				     value : "ALL",
				     name : '全部'
				}, {
					 value : "LOADING",
					 name : "创建"
				}, {
					value : "FINISHED",
					 name : "提交"
				}]
	});

//查询条件form
Ext.define('Foss.load.loadexeceptiontask.QueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件', // 查询条件
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		labelWidth : 85,
		margin : '5 10 5 10'
	},
	items : [ 
		 {
			name : 'loadTaskNo',
			fieldLabel : "装车任务号", // 装车任务号
			columnWidth : .25
		},{
			 xtype : 'rangeDateField',
			 dateType : 'datetimefield_date97',
			 fieldLabel:'任务创建时间',
			 fieldId : 'xx'+new Date().getTime(),
			 fromName:'taskCreateTimeStart',
			 toName: 'taskCreateTimeEnd',
			 fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),
				new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'),
			 toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
				new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
			 dateRange:3,
			 editable: false,
			 allowBlank:false,
			 columnWidth: .45
	 }, {
			name : 'taskStatus',
			xtype : 'combo',
			store : Ext.create('Foss.load.loadexeceptiontask.status'),
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			editable : false,
			fieldLabel : "任务状态", // 任务状态
			columnWidth : .25
	}, {
		 xtype:'commonemployeeselector',
		 name: 'loaderCode',
		 fieldLabel: "理货员",   //理货员
		 columnWidth: .25
	 },{
		 xtype:'commontruckselector',
		 name: 'vehicleNo',
		 fieldLabel: "车牌号",   //车牌号
		 columnWidth: .25
	 },{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		colspan:3,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置', // 重置
			columnWidth:.1,
			handler:function(){
				var form= this.up('form').getForm();
				form.reset();
				form.findField('taskCreateTimeStart').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),
					new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'));
				form.findField('taskCreateTimeEnd').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
					new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'));
			}
	  }, {
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.8
		},{
			text : '查询', // 查询
			disabled : false,
			hidden : false,
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = load.loadexeceptiontask.loadexeceptiontaskQueryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('标题',
							'请输入合法的查询条件'); // 请输入合法的查询条件!
					return false;
				}
				load.loadexeceptiontask.pagingBar.moveFirst();
				loadexeceptiontaskTabGridStore.removeAll();
			}
		}]
	}]
});


//model
Ext.define('Foss.load.loadexeceptiontask.loadexeceptiontaskQueryModel', {
	extend : 'Ext.data.Model',
	fields : [{
				// 车牌号
				name : 'vehicleNo',
				type : 'string'
	        },{
				// 主键id
				name : 'id',
				type : 'string'
			},{
				// 提交总件数 
				name : 'submitTotalCount',
				type : 'string'
			},{
				name : 'loadTaskStatus',//任务状态
				type : 'string'
			},{
				// 用车时间
				name : 'loadStartTime',
				type:'string',//到达时间
				convert : function(value) {
		         if (!value) return '';
		            var date = new Date(value);
		         if (date == 'Invalid Date') {
		               return value;
		         }
		          return Ext.Date.format(date, 'Y-m-d H:i:s');
		         }
			},{
				// 理货员
				name : 'loaderName',
				type : 'string'
			},{
				//装车任务号
				name : 'loadTaskNo',
				type : 'string'
			},{
				// 错误信息
				name : 'errorMsg',
				type : 'string'
			}]
});


// 查询store
Ext.define('Foss.load.loadexeceptiontask.loadexeceptiontaskQueryStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型 
	model : 'Foss.load.loadexeceptiontask.loadexeceptiontaskQueryModel',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		timeout:300000,
		url : load.realPath('queryLoadManagerException.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'loadManagerExceptionVo.returnList',
			successProperty : 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		
		'beforeload' : function(store, operation, eOpts) {
			
			if(load.loadexeceptiontask.orgCode!=null){
				orgCode = load.loadexeceptiontask.orgCode;
			}
			
			var queryParams = load.loadexeceptiontask.loadexeceptiontaskQueryForm.getValues();
			
			var taskStatusPram=queryParams.taskStatus;
			if(Ext.isEmpty(taskStatusPram)){
				taskStatusPram='ALL';
			}
			Ext.apply(operation, {
				params : {
					'loadManagerExceptionVo.taskCreateTimeStart' : queryParams.taskCreateTimeStart, // 用车开始时间
					'loadManagerExceptionVo.taskCreateTimeEnd' : queryParams.taskCreateTimeEnd, // 用车结束时间
					'loadManagerExceptionVo.loadTaskNo' : queryParams.loadTaskNo, // 装车任务号
					'loadManagerExceptionVo.taskStatus' : taskStatusPram, // 任务状态
					'loadManagerExceptionVo.loaderCode' : queryParams.loaderCode, // 理货员工号
					'loadManagerExceptionVo.vehicleNo' : queryParams.vehicleNo, //车牌号
					'loadManagerExceptionVo.orgCode' : orgCode//部门编码
				}
			});
		},
		
	'datachanged' : function(store){
		  document.getElementsByName("shortGridTotalCount")[0].value=store.getTotalCount();
		 //Ext.getCmp("shortGridTotalCount").setValue(store.getTotalCount());
	}
}
});




/**
 *判断选中的数据有哪些单据类型
 **/
function shortDataTruckListSplit(arry){
	//每次分组前先清空数组
	short_chooseDataListStowageBill=[];
	short_chooseDataListStowageBill=arraySplice(arry);
}


function uniqueArray(data){
	data = data || [];
	var a = {};
	for (var i=0; i<data.length; i++) {
		var v = data[i];
		if (typeof(a[v]) == 'undefined'){
			a[v] = 1;
		}
	};
	data.length=0;
	for (var i in a){
		data[data.length] = i;
	}
	return data;
}

function maxDate(dateList_st){
	var temp ;
	for(i=0;i<dateList_st.length;i++){

		if(Date.parse(dateList_st[i])>Date.parse(dateList_st[i+1])){
			temp=dateList_st[i+1];
			dateList_st[i+1]=dateList_st[i];
			dateList_st[i]=temp;
		}
	}
	return dateList_st[dateList_st.length-1];

}



var loadexeceptiontaskTabGridStore = null;

//显示的grid
Ext.define('Foss.load.loadexeceptiontask.loadexeceptiontaskQueryGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 450,
	emptyText : "查询结果为空",
	constructor : function(config) {
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.loadexeceptiontask.loadexeceptiontaskQueryStore');
		loadexeceptiontaskTabGridStore = me.store;
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SINGLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : '装车修改',//装车修改
			disabled : !load.loadexeceptiontask.isPermission('load/loadexeceptiontaskButton'),
			hidden : true,
			handler : function(){
				var grid = this.ownerCt.ownerCt;
				dataList= grid.getSelectionModel().getSelection();
				if(dataList.length==0){
					Ext.Msg.alert('提示','请选择数据后操作');
					return;
				}
				shortDataTruckListSplit(dataList);
				
				for(i=0;i<dataList.length;i++){
					if(dataList[i].get('vehicleState')!= "ARRIVED" && dataList[i].get('vehicleState')!= "UNLOADED"){
						Ext.Msg.alert('提示','车辆任务尚未结束,不能标记');
						return;
					}
				}
				
				var repatMarkSigln=shortNoRepeatMark(short_chooseDataListStowageBill);
				if(repatMarkSigln=='Y'){
					Ext.Msg.alert('提示','同一车辆任务不可重复标记');
					return;
				} else{ 
				}
			}
		}],
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store : me.store,
				pageSize : 50,
				maximumSize : 50,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['50', 50], ['100', 100], ['500', 500]]
				})
			});
		load.loadexeceptiontask.pagingBar = me.bbar;
		me.callParent([ cfg ]);
		},
		dockedItems: [{
			xtype: 'toolbar',
			dock: 'bottom',
			layout : 'column',
			defaults: {
				xtype : 'textfield',
				readOnly : true,
				labelWidth : 60
			},
			items: [{
				fieldLabel : '总条数',
				xtype : 'textfield',
				readOnly : true,
				columnWidth : 0.2,
				value: 0,
				name:'shortGridTotalCount',
				fieldId :'shortGridTotalCount'
			}]
		}],
	title : "装车异常任务信息",  
	columns : [{
		xtype:'actioncolumn',
		align : 'center',
		header: '操作',
		width : 80,
        items: [{
            iconCls: 'deppon_icons_edit',
            tooltip: '修改创建任务',
            handler: function(grid, rowIndex, colIndex) {
            	//unload.changelabelgoods.changelabelgoodsPrintAgain(record);
            	var record = grid.getStore().getAt(rowIndex);
				load.loadexeceptiontask.showEditLoadTaskForm(record);
				
            }
        },{
            iconCls: 'foss_icons_stl_dispose',
            tooltip: '修改提交任务',
            handler: function(grid, rowIndex, colIndex) {
            	var record = grid.getStore().getAt(rowIndex);
            	load.loadexeceptiontask.showEditLoadTaskForm(record);
            }
        }],
        renderer:function(value, metadata, record){
        	if(record.data.loadTaskStatus == 'LOADING'){
    			this.items[0].iconCls = 'deppon_icons_edit';
    			this.items[1].iconCls = '';
    		} else if(record.data.loadTaskStatus == 'FINISHED'){
    			this.items[0].iconCls = '';
    			this.items[1].iconCls = 'foss_icons_stl_dispose';
    		}
        }
	}, {
		header : "装车任务号",  
		dataIndex : 'loadTaskNo',
		flex : 1
	}, {
		header : "任务状态",   
		dataIndex : 'loadTaskStatus',
		flex : 1,
		renderer:function(value){
			if(value == 'LOADING'){
				return '创建';
			} else if(value == 'FINISHED'){
				return '提交';
			}
			
		}
	},{
		header : "车牌号",   
		dataIndex : 'vehicleNo',
		flex : 1
	}, {
		header: '提交总票数',
		dataIndex: 'submitTotalCount'
	},{
		header: '理货员',
		dataIndex: 'loaderName'
	}, {
		header : "任务创建时间", 
		dataIndex : 'loadStartTime',
		flex : 2
	}, {	
		header : "异常原因", 
		dataIndex : 'errorMsg',
		flex : 2
	}, {	
		header : "id", 
		dataIndex : 'id',
		hidden:true,
		flex : 2
	}]
});



/**
 * 去除数组中的空元素
 */
function arraySplice(arrayList){
	if(arrayList.length!=0){
		for(i=0;i<arrayList.length;i++){
			if(arrayList[i]==null||arrayList[i]==''){

				arrayList.splice(i,1);
				i--;
			}
		}
	}
	return arrayList;
}


/**
 *日期格式化
 **/

function dateFormat(date){
	if(!date){
		return '';
	}else{
		return Ext.Date.format(date,'Y-m-d H:i:s');
	}
}



//查询条件form
Ext.define('Foss.load.loadexeceptiontask.editLoadTaskQueryForm', {
	extend : 'Ext.form.Panel',
	title : '装车任务修改', // 查询条件
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		labelWidth : 85,
		margin : '5 10 5 10'
	},
	items : [
		 {
			name : 'loadTaskNo',
			fieldLabel : "装车任务号", // 装车任务号
			columnWidth : .30
		},{
			name : 'taskCreateTimeStart',
			fieldLabel : "任务创建时间", // 任务开始时间
			columnWidth : .30
	 },{
			name : 'scanTotalCount',
			fieldLabel : "扫描总票数", // 扫描总票数
			columnWidth : .30
		},{
			name : 'loaderName',
			fieldLabel : "理货员", // 理货员
			columnWidth : .30
		},,{
			name : 'taskStatus',
			fieldLabel : "任务状态", // 任务状态
			hidden:true,
			columnWidth : .30
		},{
			name : 'driverPhone',
			fieldLabel : "司机电话", // 司机电话
			hidden:true,
			columnWidth : .30
		},{
		 xtype:'commontruckselector',
		 name: 'vehicleNo',
		 fieldLabel: "车牌号",   //车牌号
		 columnWidth: .30,
		 listeners: {

				'blur' : function(cmp,eObject,eOpts){
					if(!Ext.isEmpty(cmp.getValue())){
						var form = this.up('form').getForm();
						var vehicleNo=form.findField('vehicleNo').getValue();
							//后台获取司机信息
							var myMask = new Ext.LoadMask(this.up('form'), {
								msg:"加载中，请稍候..."
							});
							var editButton = Ext.getCmp('Foss_load_exeception_edit_ID');
							var editMask = new Ext.LoadMask(editButton, {
								msg:"请稍候..."
							});
							myMask.show();
							editMask.show();
							Ext.Ajax.request({
								url : load.realPath('queryDriverMsgByVehicleNo.action'),
								params : {'loadManagerExceptionVo.vehicleNo' :vehicleNo
									      },
								success : function(response){
									var result = Ext.decode(response.responseText);
									var driverInfo = result.loadManagerExceptionVo.driverBaseDto;
									var driverCmp = form.findField('driver');
									var driverPhoneCmp=form.findField('driverPhone');
										if(driverInfo != null){
											var driverCode = driverInfo.driverCode;
											var driverName= driverInfo.driverName;
											var driverPhone=driverInfo.driverPhone;
											driverCmp.setValue(driverName+' '+driverCode);
											driverPhoneCmp.setValue(driverPhone);
										}else{
											driverCmp.setValue('');
											driverPhoneCmp.setValue('');
										}
									myMask.hide();
									editMask.hide();
								},
								exception : function(response){
									var result = Ext.decode(response.responseText);
									myMask.hide();
									editMask.hide();
									Ext.Msg.alert('提示',result.message);
								},
				    			error : function(response) {
				    				var result = Ext.decode(response.responseText);
				    				myMask.hide();
									editMask.hide();
									Ext.Msg.alert('提示',result.message);
				    			}
							});
					}
				}
		 }
	 },{
			name : 'driver',
			fieldLabel : "司机", // 司机
			columnWidth : .30
		},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		colspan:3,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '返回', // 重置
			columnWidth:.1,
			handler:function(){
				var form= this.up('form').getForm();
				form.reset();
				editLoadTaskQuerywin.close();
        		//this.up('window').close();
			}
	  }, {
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.8
		},{
			id:'Foss_load_exeception_edit_ID',
			text : '保存', // 查询
			disabled : false,
			hidden : false,
			cls : 'yellow_button',
			columnWidth : .1,
			handler : function() {
				var form = this.up('form').getForm();
				if(Ext.isEmpty(form.findField('loadTaskNo').getValue())){
					Ext.Msg.alert('提示','装车任务号不能为空'); 
					return false;
				}
				if(Ext.isEmpty(form.findField('vehicleNo').getValue())){
					Ext.Msg.alert('提示','车牌号不能为空'); 
					return false;
				}
				var driverCode=null;
				var driverName=null;
				var driver=form.findField('driver').getValue();
				if(!Ext.isEmpty(driver)){
					driverName=driver.split(' ')[0];
					driverCode=driver.split(' ')[1];
				}
				var params = {
				'loadManagerExceptionVo.vehicleNo' : form.findField('vehicleNo').getValue(),
				'loadManagerExceptionVo.loadTaskNo' : form.findField('loadTaskNo').getValue(),
				'loadManagerExceptionVo.taskStatus' : form.findField('taskStatus').getValue(),
				'loadManagerExceptionVo.submitTotalCount' : form.findField('scanTotalCount').getValue(),
				'loadManagerExceptionVo.driverBaseDto.driverCode' : driverCode,
				'loadManagerExceptionVo.driverBaseDto.driverName' : driverName,
				'loadManagerExceptionVo.driverBaseDto.driverPhone' : form.findField('driverPhone').getValue()
			};
				
				var myMask = new Ext.LoadMask(this.up('form'), {
					msg:"加载中，请稍候..."
				});
				var editButton = Ext.getCmp('Foss_load_exeception_edit_ID');
				var editMask = new Ext.LoadMask(editButton, {
					msg:"请稍候..."
				});
				myMask.show();
				editMask.show();
				
			Ext.Ajax.request({
				url : load.realPath('editLoadTaskVehicleNo.action'),
				params : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					myMask.hide();
					editMask.hide();
					editLoadTaskQuerywin.close();
					Ext.Msg.alert('提示','修改成功！');
					load.loadexeceptiontask.pagingBar.moveFirst();
					loadexeceptiontaskTabGridStore.removeAll();
				},exception : function(response){
					var result = Ext.decode(response.responseText);
					myMask.hide();
					editMask.hide();
					Ext.Msg.alert('提示',result.message);
				},
    			error : function(response) {
    				var result = Ext.decode(response.responseText);
    				myMask.hide();
					editMask.hide();
					Ext.Msg.alert('提示',result.message);
    			}
			});
				
			}
		}]
	}]
});




//点击“操作列”打开装车任务修改窗口
javascript:load.loadexeceptiontask.showEditLoadTaskForm = function(record){
	
	var editLoadTaskQuerywinId = Ext.getCmp('editLoadTaskQuerywinId');
	   if(null==editLoadTaskQuerywinId){
		   editLoadTaskQueryGrid = Ext.create('Foss.load.loadexeceptiontask.editLoadTaskQueryForm');
			editLoadTaskQuerywin = Ext.create('Ext.window.Window', {
				    id: 'editLoadTaskQuerywinId',
				    height:350,
				    width:850,
				    closable:true,
					closeAction:'hide',
				    modal: true,
				    items: [
		            editLoadTaskQueryGrid
			        ]
				}); 
	   }
		
	var editForm = editLoadTaskQueryGrid.getForm();
	editForm.reset();
	editForm.findField('loadTaskNo').setValue(record.get('loadTaskNo')).readOnly = true;
	editForm.findField('taskCreateTimeStart').setValue(record.get('loadStartTime')).readOnly = true;
	editForm.findField('scanTotalCount').setValue(record.get('submitTotalCount')).readOnly = true;
	editForm.findField('loaderName').setValue(record.get('loaderName')).readOnly = true;
	editForm.findField('taskStatus').setValue(record.get('loadTaskStatus'));
	editForm.findField('driverPhone').setValue(record.get('driverPhone'));
	editForm.findField('driver').readOnly = true;
	editLoadTaskQuerywin.show();
	
}



//页面初始化入口
Ext.onReady(function() {
	//悬浮提示开启
	Ext.QuickTips.init();
	// 查询条件
	//var loadexeceptiontaskQueryForm = Ext.create('Foss.load.loadexeceptiontask.loadexeceptiontaskQueryForm');
	// grid
	//var loadexeceptiontaskQueryGrid = Ext.create('Foss.load.loadexeceptiontask.loadexeceptiontaskQueryGrid');
	// 保存到全局变量中
	load.loadexeceptiontask.loadexeceptiontaskQueryForm = Ext.create('Foss.load.loadexeceptiontask.QueryForm');
	load.loadexeceptiontask.loadexeceptiontaskQueryGrid = Ext.create('Foss.load.loadexeceptiontask.loadexeceptiontaskQueryGrid');

	Ext.create('Ext.panel.Panel', {
				//id : 'T_load-loadexeceptiontask_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [load.loadexeceptiontask.loadexeceptiontaskQueryForm, 
					load.loadexeceptiontask.loadexeceptiontaskQueryGrid],
				renderTo : 'T_load-loadexeceptiontask-body'
			});
	 //alert(load.loadexeceptiontask.loadexeceptiontaskQueryGrid.getStore());
});

