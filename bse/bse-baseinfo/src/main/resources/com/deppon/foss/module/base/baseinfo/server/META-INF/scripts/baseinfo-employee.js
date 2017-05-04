/**
*	综合管理 人员 JS功能代码：
*/

////////////////////////////////////////////////////////////////////
// 全局变量（模块名employee） start
////////////////////////////////////////////////////////////////////

baseinfo.employee.rawRecord = null; //记录表单的当前值，在重置的时候使用。

////////////////////////////////////////////////////////////////////
// 全局变量（模块名employee） end
////////////////////////////////////////////////////////////////////

// 人员查询-主面板：
Ext.define('Foss.baseinfo.employee.SelectForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_employee_SelectForm_Id',
	itemId: 'Foss_baseinfo_employee_SelectForm_ItemId',
	//height: 80,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType : 'textfield',
	layout:'column',
	frame: true,
	columnWidth: 0.99,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 120
	},
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.employee.SelectConditionForm'),
			Ext.create('Foss.baseinfo.employee.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});


//==============================================================================================
// 人员详情的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.employee.EmployeeDetailWindow',{
	extend: 'Ext.window.Window',
	id : 'Foss_baseinfo_employee_EmployeeDetailWindow_Id',
	// title: baseinfo.employee.i18n('foss.baseinfo.addUpdateEmployee'),
    width: 800,
    //height: 700,
	closeAction:'hide',
	layout : 'column',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
		    Ext.create('Foss.baseinfo.employee.EmployeeDetailForm')
		            
		];
		me.callParent([cfg]);
	}
})

//==============================================================================================
// 下面是详细实现
//==============================================================================================



/**
* 人员界面数据模型定义
*/
Ext.define('Foss.baseinfo.employee.EmployeeModel', {
    extend: 'Ext.data.Model',
    fields: [
      // ID
      {name: 'id', type: 'string'},
      // 职员姓名
      {name: 'empName', type: 'string'},
      // 拼音
      {name: 'pinyin', type: 'string'},
      // 工号
      {name: 'empCode', type: 'string'},
      // 性别
      {name: 'gender', type: 'string'},
      // 部门标杆编码
      {name: 'unifieldCode', type: 'string'},
      // 职位
      {name: 'title', type: 'string'},
      // 职等
      {name: 'degree', type: 'string'},
      // 出生日期
      {name: 'birthdate', type: 'date', 
    	  convert: function(val){return val==null?null:new Date(val)}},
      // 状态
      {name: 'status', type: 'string'},
      // 电话
      {name: 'phone', type: 'string'},
      // 身份证号
      {name: 'identityCard', type: 'string'},
      // 入职日期
      {name: 'entryDate', type: 'date', 
    	  convert: function(val){return val==null?null:new Date(val)}},
      // 离职日期
      {name: 'leaveDate', type: 'date', 
        	  convert: function(val){return val==null?null:new Date(val)}},
      // 手机号码
      {name: 'mobilePhone', type: 'string'},
      // 电子邮箱
      {name: 'email', type: 'string'},
      // 创建时间
      {name: 'createTime', type: 'date'},
      // 更新时间
      {name: 'modifyTime', type: 'date'},
      // 是否启用
      {name: 'active', type: 'string'},
      // 创建人
      {name: 'createUserCode', type: 'string'},
      // 更新人
      {name: 'modifyUserCode', type: 'string'}
    ]
});



//查询条件面板
Ext.define('Foss.baseinfo.employee.SelectConditionForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_employee_SelectConditionForm_Id',
	itemId: 'Foss_baseinfo_employee_SelectConditionForm_ItemId',	
	layout:'column',
	frame : true,
	columnWidth: 0.98,
	title: baseinfo.employee.i18n('foss.baseinfo.queryCondition'),
	defaults: {
		xtype : 'textfield',
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},

  initComponent: function(config){
    var me = this,
			cfg = Ext.apply({}, config);
    
    // 让数据字典可模糊查询
    var a_dictValue = login.dataDictionary.get('UUMS_POSITION');
	
    me.items = [
		Ext.create('Ext.container.Container',{
			columnWidth:.99,
			layout : 'column',	
			defaultType : 'textfield',
			items:[{
					name: 'empCode',
				    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.jobNumber'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},{
					name: 'empName',
				    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.employeeName'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},
				/*{
					name: 'title',
				    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.position'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},
				FossDataDictionary.getDataDictionaryCombo('UUMS_POSITION',
						{
						//人员状态（状态, 0-离职人员；1-在职人员）
						fieldLabel:baseinfo.employee.i18n('foss.baseinfo.position'),								
						name: 'title',
				    	//labelSeparator:'',
						margin:'5 20 5 10',
				    	labelWidth:100,
					    columnWidth:.4,
						queryMode: 'local',
				    	//value:'1',
					}
				),*/
				{
					name: 'title',
					queryMode: 'local',
				    displayField: 'valueName',
				    valueField: 'valueCode',
				    store: Ext.create('Ext.data.Store', {
					    fields: ['valueCode','valueName'],
					    data:a_dictValue
				     }),
			        fieldLabel: baseinfo.employee.i18n('foss.baseinfo.position'),//词名称
			        xtype : 'combo',
					margin:'5 20 5 10',
					columnWidth:.4
				},
				{
					name: 'phone',
				    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.phoneCode'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},
				/**
				{
				    hidden: true,
					name: 'unifieldCode',
				    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.unifiedCode'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},{
					name: 'unifieldCodeName',
				    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.orgName'),
					margin:'5 20 5 10',
				    columnWidth:.4
				}*/
				
				{
					xtype : 'dynamicorgcombselector',
					type: 'ORG',
					//id : 'vardynamicorgselector',
					labelWidth:160,
					name:'unifieldCode',
					valueField : 'unifiedCode',// 这个参数，只需与实体中的某个字段对应即可
					forceSelection : false,
					fieldLabel : baseinfo.employee.i18n('foss.baseinfo.orgName'),
					margin:'5 20 5 10',
					labelWidth:100,
				    columnWidth:.4
				},
				FossDataDictionary.getDataDictionaryCombo('EMPLOYEE_STATUS',
					{
						//人员状态（状态, 0-离职人员；1-在职人员）
						fieldLabel:baseinfo.employee.i18n('foss.baseinfo.status'),								
						name: 'status',
				    	//labelSeparator:'',
						margin:'5 20 5 10',
				    	labelWidth:100,
					    columnWidth:.4,
				    	value:'1'
					},[
						{
						    'valueCode': '',
						    'valueName': '全部'
						}
			
					]
				),
				
				/*
				{
					name: 'status',
				    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.status'),
					margin:'5 20 5 10',
				    columnWidth:.4,					
					store: Ext.create('Ext.data.Store',{
						//定义字段
						fields: [
							{name: 'code',type:'string'},
							{name: 'name',type:'string'}
						],
						data: {
							'items':[
								{"code":"", "name":baseinfo.employee.i18n('foss.baseinfo.all')},
								{"code":"1", "name":baseinfo.employee.i18n('foss.baseinfo.holdPost')},
								{"code":"2", "name":baseinfo.employee.i18n('foss.baseinfo.leaveOffice')}
							]
						},	
						proxy: {
							type: 'memory',
							reader: {
								type: 'json',
								root: 'items'
							}
						}
					})	
				},
				*/
				Ext.create('Ext.container.Container',{
					columnWidth:.99,
					layout : 'column',
					items:[
						{
							columnWidth : 0.75,
							height : 0,
							xtype : 'container',
							style : 'padding-top:20px;border:none',
							hide:true
						},Ext.create('Ext.button.Button',{
							xtype : 'button',
							hidden : false,
							name: 'buttonReset',
							text: baseinfo.employee.i18n('foss.baseinfo.reset'),
							disabled:!baseinfo.employee.isPermission('employeeindex/employeeQuerybutton'),
							hidden:!baseinfo.employee.isPermission('employeeindex/employeeQuerybutton'),
							columnWidth:.12, 
							margin:'0,10,0,0',
							width:20,
							handler: function() {
								var employeeForm=Ext.getCmp("Foss_baseinfo_employee_SelectConditionForm_Id");
								// 将表单中的数据清空：
								employeeForm.getForm().reset();
							}
						}),{
							columnWidth : 0.010,
							height : 0,
							xtype : 'container',
							style : 'padding-top:20px;border:none',
							hide:true
						},Ext.create('Ext.button.Button',{
							xtype : 'button',
							hidden : false,
							cls:'yellow_button',
							name: 'buttonSelect',
							disabled:!baseinfo.employee.isPermission('employeeindex/employeeQuerybutton'),
							hidden:!baseinfo.employee.isPermission('employeeindex/employeeQuerybutton'),
							text: baseinfo.employee.i18n('foss.baseinfo.query'),
							columnWidth:.12, 
							margin:'0,10,0,30',
							width:20,
							
							//查询按钮的响应事件：
							handler: function() {
								var selectResultPanel=Ext.getCmp("Foss_baseinfo_employee_SelectResultForm_Id");
								selectResultPanel.setVisible(true);
								
								baseinfo.employee.pagingBar.moveFirst();
							}
						})
					]
				})					
			]
		})	
	];
    me.callParent([cfg]);
  }
});

// 人员的类定义：
Ext.define('Foss.baseinfo.employee.EmployeeComboboxStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//右边模块-查询结果面板
Ext.define('Foss.baseinfo.employee.SelectResultForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_employee_SelectResultForm_Id',
	itemId: 'Foss_baseinfo_employee_SelectResultForm_ItemId',
	layout:'column',
	frame: true,
	hidden:true,
	columnWidth: 0.98,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	title: baseinfo.employee.i18n('foss.baseinfo.employeeList'),
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},

  	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			Ext.create('Ext.container.Container',{
				columnWidth:.99,
				layout : 'column',
				items:[{
						xtype:'label',
						text:baseinfo.employee.i18n('foss.baseinfo.employeeList'),
						hidden:true,
						margin:'5 10 5 10',
						columnWidth:.99
					},
					Ext.create('Foss.baseinfo.employee.EmployeeGrid')

				]
			})	
		];
	    me.callParent([cfg]);
  	}
});

//查询的显示表格：
Ext.define('Foss.baseinfo.employee.EmployeeGrid',{
	extend: 'Ext.grid.Panel',
	id : 'Foss_baseinfo_employee_EmployeeGrid_Id',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	columnWidth:.99, 
	
	
	// 设置表格不可排序
	sortableColumns:false,
	// 去掉排序的倒三角
    enableColumnHide:false,
    // 设置“如果查询的结果为空，则提示”
    emptyText: baseinfo.employee.i18n('foss.baseinfo.queryResultIsNull'),
    
    
    //增加滚动条
    autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	selModel : Ext.create('Ext.selection.RowModel'),
	//selModel: Ext.create('Ext.selection.CheckboxModel'),
	//表格行可展开的插件
	plugins: [{
		ptype: 'rowexpander',
		//定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander: false,
		//行体内容
		rowBodyElement : 'Ext.panel.Panel'
	}],
	columns : [{
			text: baseinfo.employee.i18n('foss.baseinfo.jobNumber'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'empCode'
			/*
			renderer: function(value) {				
				
				return value;
			}*/
		},{
			text: baseinfo.employee.i18n('foss.baseinfo.employeeName'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'empName'
		},{
			text: baseinfo.employee.i18n('foss.baseinfo.position'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'title',
//			renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
//				//var a_value =value;
//				// a_value = a_value=='1'?baseinfo.employee.i18n('foss.baseinfo.holdPost'):a_value=='2'?'离职':a_value
////				var a_value = FossDataDictionary.rendererSubmitToDisplay(value,'UUMS_POSITION');
////				a_value =a_value==null?value:a_value;
//				
//				return a_value;
//			}
		},{
			text: baseinfo.employee.i18n('foss.baseinfo.phoneCode'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'phone'
		},{
			hidden: true,
			text: baseinfo.employee.i18n('foss.baseinfo.department'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'unifieldCode'
		},{
			text: baseinfo.employee.i18n('foss.baseinfo.orgName'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'unifieldCodeName',
			renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
				var aimStr=value;
				if(record && record.raw && record.raw.department){
					aimStr = record.raw.department.name;
				}
				return aimStr;
			}
		},{
			text: baseinfo.employee.i18n('foss.baseinfo.status'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'status',
			renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
				//var a_value =value;
				// a_value = a_value=='1'?baseinfo.employee.i18n('foss.baseinfo.holdPost'):a_value=='2'?'离职':a_value
				var a_value = FossDataDictionary.rendererSubmitToDisplay(value,'EMPLOYEE_STATUS');
				a_value =a_value==null?value:a_value;
				
				return a_value;
			}
		}
	],
	
	listeners:{
		itemdblclick: function(me, record, item, index, e, eOpts) {
			// 获得当前选择的行的的数据
			//var rowInfo = me.getStore().getAt(rowIndex);

			// 设置人员详情
			var a_detailForm=Ext.getCmp('Foss_baseinfo_employee_EmployeeDetailForm_Id');
			if(record.raw && record.raw.department){
				record.data.unifieldCodeName = record.raw.department.name;
			}
			a_detailForm.getForm().setValues(record.data);
			
			var a_window=Ext.getCmp('Foss_baseinfo_employee_EmployeeDetailWindow_Id');
			a_window.show();
            
	    }
	},

	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.employee.EmployeeModel',
			pageSize: 10,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				//'../baseinfo/queryEmployeeByEntity.action'
				url : baseinfo.realPath("queryEmployeeByEntity.action"),
				//定义一个读取器
				reader: {
					type: 'json',
					root: 'employeeVo.employeeList',
					totalProperty : 'totalCount'
				}
			},
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners: {
				beforeload : function(store, operation, eOpts) {
					var queryForm = baseinfo.employee.queryForm;
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								'employeeVo.employeeDetail.empCode' : queryParams.empCode,
								'employeeVo.employeeDetail.empName' : queryParams.empName,
								'employeeVo.employeeDetail.title' : queryParams.title,
								'employeeVo.employeeDetail.phone' : queryParams.phone,
								'employeeVo.employeeDetail.unifieldCode' : queryParams.unifieldCode,
								'employeeVo.employeeDetail.status' : queryParams.status
							}
						});	
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
		baseinfo.employee.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});




//定义baseinfo.employee.i18n('foss.baseinfo.persionInfoPage')
Ext.define('Foss.baseinfo.employee.EmployeeDetailForm',{
	extend: 'Ext.form.Panel',
	id: 'Foss_baseinfo_employee_EmployeeDetailForm_Id',
	title: baseinfo.employee.i18n('foss.baseinfo.employeeInfo'),
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	layout:'column',
	align: 'stretch',
	buttonAlign: 'center',//按钮居中
	columnWidth: 0.98,
	defaults: {
		readOnly : true,
		margin:'5 5 5 5',
		anchor: '90%',
		columnWidth: 0.99,
		labelWidth: 120
	},
	
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [{
			name: 'empCode',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.jobNumber'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			name: 'empName',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.employeeName'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			name: 'pinyin',
		    fieldLabel: '拼音',
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			name: 'gender',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.gender'),
			margin:'5 20 5 10',
		    columnWidth:.4,
			listeners: {  
				change : function(me, newValue, oldValue, eOpts){
					var a_value = FossDataDictionary.rendererSubmitToDisplay(newValue,'GENDER');
					if(a_value == '0'){
						me.setValue(baseinfo.employee.i18n('foss.baseinfo.female'));
						return;
					}
					else if(a_value == '1'){
						me.setValue(baseinfo.employee.i18n('foss.baseinfo.male'));
						return;
					}
					else{
						me.setValue(a_value);
						return ;
					}
				}	
			}
		},{
			name: 'unifieldCodeName',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.orgName'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			name: 'title',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.position'),
			margin:'5 20 5 10',
		    columnWidth:.4,
			listeners: {  
				change : function(me, newValue, oldValue, eOpts){
					var a_value = FossDataDictionary.rendererSubmitToDisplay(newValue,'UUMS_POSITION');
					if(a_value == null){
						return ;
					}else{
						me.setValue(a_value);
						return ;
					}
				}	
			}
		},{
			name: 'degree',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.degree'),
			margin:'5 20 5 10',
		    columnWidth:.4,
			listeners: {  
				change : function(me, newValue, oldValue, eOpts){
					var a_value = FossDataDictionary.rendererSubmitToDisplay(newValue,'UUMS_DEGREE');
					if(a_value == null){
						return ;
					}else{
						me.setValue(a_value);
						return ;
					}
				}	
			}
		},{
			xtype: 'datefield',
			format: 'Y-m-d',
			name: 'birthdate',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.birthday'),
			margin:'5 20 5 10',
		    columnWidth:.4,
			listeners: {  
				change : function(me, newValue, oldValue, eOpts){
					var oldValue = '2015-06-25';
					var resultPermission = !baseinfo.employee.isPermission('/bse-baseinfo-web/baseinfo/showPrivacyInfor.action');
					if(resultPermission){
						me.setValue(oldValue);
						return;
					}else{
						var birthdayArray = oldValue.split('-');
						if(!Ext.isEmpty(birthdayArray[0])){
							me.setValue(birthdayArray[0]+'-'+'**'+'-'+'**');
							return;
						}
						return ;
					}
				}	
			}
		},{
			name: 'status',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.status'),
			margin:'5 20 5 10',
		    columnWidth:.4,
			listeners: {  
				change : function(me, newValue, oldValue, eOpts){
					var a_value = FossDataDictionary.rendererSubmitToDisplay(newValue,'EMPLOYEE_STATUS');
					if(a_value == null){
						return ;
					}else{
						me.setValue(a_value);
						return ;
					}
				}	
			}
		},{
			name: 'phone',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.phoneCode'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			name: 'identityCard',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.idNumber'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			xtype: 'datefield',
			format: 'Y-m-d',
			name: 'entryDate',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.entryDate'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			xtype: 'datefield',
			format: 'Y-m-d',
			name: 'leaveDate',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.leaveDate'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			name: 'mobilePhone',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.customer.phoneNo'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},{
			name: 'email',
		    fieldLabel: baseinfo.employee.i18n('foss.baseinfo.electronicMailBox'),
			margin:'5 20 5 10',
		    columnWidth:.4
		},Ext.create('Foss.baseinfo.employee.EmployeeDetailButtonPanel')

	
		];
	  	me.callParent([cfg]);
	}
});

//修改的按钮的面板
Ext.define('Foss.baseinfo.employee.EmployeeDetailButtonPanel',{
	extend:'Ext.panel.Panel',
	id: 'Foss_baseinfo_employee_EmployeeDetailButtonPanel_Id',
	itemId: 'Foss_baseinfo_employee_EmployeeDetailButtonPanel_ItemId',
	layout:'column',
	columnWidth: 0.99,
	defaults: {
		readOnly : false
	},
	
	// 定义获取按钮的一些方法：
	closeButton : null,
	
	getCloseButton:function(me){
      if(this.cancelButton==null){
          	this.cancelButton = Ext.create('Ext.button.Button',{
	            xtype : 'button',
	            //height:38,
	            //cls:'yellow_button',
	            text: baseinfo.employee.i18n('foss.baseinfo.close'),
	            //margin:'0 0 0 150',
	            columnWidth:.12, 
	            hidden : false,
	            handler: function(){
		    		var a_window = Ext.getCmp('Foss_baseinfo_employee_EmployeeDetailWindow_Id');
		    		a_window.hide();
		    		//a_window.setVisible(false);
	            }
          	});
        }
        return this.cancelButton;
    },

	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
	    	me.getCloseButton()
		];
	    me.callParent([cfg]);

	}
});

/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	baseinfo.employee.windowz = Ext.create('Foss.baseinfo.employee.EmployeeDetailWindow');
	if (Ext.getCmp('T_baseinfo-employeeindex_content')) {
		return;
	}
	
	Ext.getCmp('T_baseinfo-employeeindex').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-employeeindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		items: [
			Ext.create('Foss.baseinfo.employee.SelectForm')
		] 
	}));
	baseinfo.employee.queryForm=Ext.getCmp('Foss_baseinfo_employee_SelectConditionForm_Id');
});


