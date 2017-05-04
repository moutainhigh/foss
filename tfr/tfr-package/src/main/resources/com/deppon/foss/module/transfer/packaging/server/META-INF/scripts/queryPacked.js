

/**
 * 包装人员model
 */
Ext.define('Foss.QueryPacked.PackagerModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'empCode',type:'string'},
		{name: 'empName',type:'string'}
	]
});

/**
 * 包装人员store
 */
Ext.define('Foss.QueryPacked.PackagerStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.QueryPacked.PackagerModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为ajax
		type: 'ajax',
		//type: 'memory',
		//url:'../packaging/queryPackedPerson.action',
		url : packaging.realPath('queryPackedPerson.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			//root: 'items'
			root: 'queryUnpackVo.packedPersonList'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**

 *查询表单
 */
Ext.define('Foss.QueryPacked.PackageQueryForm',{
	extend: 'Ext.form.Panel',
	title: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.title'),//'查询包装信息',
	frame:true,
	collapsible: true,
    animCollapse: true,
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	defaultType : 'textfield',
	layout:'column',
	items : [{
		xtype: 'dynamicorgcombselector',
		name: 'waybillCreateDept',
		fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.waybillCreateDept'),//'开单部门',
		type : 'ORG',
		salesDepartment : 'Y',
		columnWidth:.4
	},{
		name: 'packedDept',
		fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.packedDept'),//'包装部门',
		//id:'Foss_querypackedindex_PackageQueryForm_packedDept_ID',
		allowBlank: false,
		readOnly:true,
		columnWidth:.25
	},{//包装部门编码
		name: 'packDeptCode',
		//id: 'Foss_querypackedindex_PackageQueryForm_packDeptCode_ID',
		hidden:true,
		columnWidth:.25
	},{
		xtype: 'commonemployeeselector',
		name: 'packedPerson',
		fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.packedPerson'),//'包装人',
		columnWidth:.25
		//type : 'commonemployeeselector'
		//xtype:'combo',
		//hideTrigger:true,
		//queryParam:'queryUnpackVo.empCode',
		//displayField: 'empName',
		//valueField: 'empCode',
		//minChars:6,
		//store: Ext.create('Foss.QueryPacked.PackagerStore')
	}, {
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fromName: 'packedBeginDate',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-2
				,new Date().getHours(),new Date().getMinutes(),new Date().getSeconds()), 'Y-m-d H:i:s'),
		toName: 'packedEndDate',
		toValue: Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
		fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.packedEndDate'),//'包装时间',
		disallowBlank:true,
		fieldId: 'Foss_querypackedindex_PackageQueryForm_packDate_ID',
		columnWidth: .4
	},{
		name: 'wayBillNumber',
		fieldLabel: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.wayBillNumber'),//'运单号',
		vtype: 'waybill',
		columnWidth:.25
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 0'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.resetButton'),//'重置',
			handler: function() {
				var form = this.up('form').getForm();
				form.findField('packedBeginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-2
						,new Date().getHours(),new Date().getMinutes(),new Date().getSeconds()), 'Y-m-d H:i:s'));
				form.findField('packedEndDate').setValue(Ext.Date.format(new Date(), 'Y-m-d H:i:s'));
				form.findField('wayBillNumber').setValue(null);
				form.findField('packedPerson').setValue(null);
				form.findField('packedDept').setValue(packaging.querypacked.packedDept);
				form.findField('waybillCreateDept').setValue(null);
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			cls:'yellow_button',
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.queryButton'),//'查询',
			disabled:(packaging.querypacked.isPermission('packaging/queryPackedAllButton'))?false:true,
			hidden:(packaging.querypacked.isPermission('packaging/queryPackedAllButton'))?false:true,
			handler: function() {
				
				if(packaging.querypacked.isPermission('packaging/queryPackedAllButton')){
					var beginDate = packaging.packageQueryForm.getValues().packedBeginDate;
					var endDate = packaging.packageQueryForm.getValues().packedEndDate;
					var difDate = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(beginDate,'Y-m-d H:i:s');
					var difTime = 7*24*60*60*1000-parseInt(difDate);
					//查询条件是否合法（非空等相关约束）
					if(packaging.packageQueryForm.getForm().isValid()){
						if(difTime>=0){
							//自定义分页
							packaging.querypacked.pagingBar.moveFirst();
						}else{
							//警告，时间跨度不能超过2天！
							Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.warning'), packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.timeHint'));	
						}
					}else{
						//警告，请输入查询条件！
						Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.warning'),  packaging.querypacked.i18n('foss.packaging.querypacked.packageQueryForm.queryHint'));
					}
					
				
				}
				
			}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.QueryPacked.PackageModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	//定义字段
	fields: [{
		name: ' extid ', type: 'string'
	},{//额外的用于生成的EXT使用的列
		//运单号
		name : 'wayBillNumber'
	}, {
		//包装时间
		name : 'packedDate',
		type : 'string',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return "";
			}
		}
	}, {
		//开单部门
		name : 'waybillCreateDept'
	}, {
		//是否在包装区
		name : 'isInPackageArea'
	}, {
		//包装部门
		name : 'packedDept'
	}, {
		//货物名称
		name : 'goodsName'
	}, {
		//开单件数
		name : 'waybillNum'
	}, {
		//已包装件数
		name : 'packedNum'
	}, {
		//包装材料
		name : 'packedMate'
	}, {
		//行号
		name : 'rowNumber'
	}, {
		//包装类型
		name : 'packageType'
	}, {
		//包装体积
		name : 'packedVolume'
	},{
		//包装人
		name : 'packedPerson'
	}, {
		//创建人
		name : 'createUser'
	}, {
		//创建日期
		name : 'createDateFalse',
		type : 'string',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return "";
			}
		}
	}, {
		//修改人
		name : 'modifyUser'
	}, {
		//修改日期
		name : 'modifyDateFalse',
		type : 'string',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return "";
			}
		}
	}, {
		//包装要求
		name : 'packRequire'
	}, {
		//加托个数
		name : 'plusNum'
	}, {
		//登入包装货区时间
		name : 'loginDate',
		type : 'string',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return "";
			}
		}
	}, {
		//登出包装货区时间
		name : 'logoutDate',
		type : 'string',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return "";
			}
		}
	}]
});
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
Ext.define('Foss.QueryPacked.PackageStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.QueryPacked.PackageModel',
	pageSize: 10,
	autoLoad: false,
	//定义一个代理对象
	proxy: {
		//代理的类型为ajax
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		//路径
		//url:'../packaging/queryPackedAll.action',
		url : packaging.realPath('queryPackedAll.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'queryUnpackVo.queryPackedResultList',
			//返回总数
			totalProperty : 'totalCount',
			successProperty: 'success'
		},
		listeners:{
			exception:function(reader,response,eopts){
				var result = Ext.decode(response.responseText);
				//错误
	            Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.warning'), result.message);   
			}
		}
	},
	listeners: {
		//查询事件
			beforeload : function(store, operation, eOpts) {
				var queryParams = packaging.packageQueryForm.getValues();
				Ext.apply(operation, {
					//设置查询条件与后台实体对应
					params : {
							'queryUnpackVo.queryPackedConditionEntity.waybillCreateDept':queryParams.waybillCreateDept,
							'queryUnpackVo.queryPackedConditionEntity.packedDept':queryParams.packDeptCode,
							'queryUnpackVo.queryPackedConditionEntity.packedPerson':queryParams.packedPerson,
							'queryUnpackVo.queryPackedConditionEntity.packedBeginDate':queryParams.packedBeginDate,
							'queryUnpackVo.queryPackedConditionEntity.packedEndDate':queryParams.packedEndDate,
							'queryUnpackVo.queryPackedConditionEntity.wayBillNumber':queryParams.wayBillNumber
						}
				});	
			},
			load: function( store, records, successful, eOpts ){
				if(successful){
					//统计列表信息
					if(records.length==0){
						//提示，未查询到符合条件的包装数据！
						Ext.ux.Toast.msg(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packageStore.queryHint'), 'success', 5000);
					}
				}	
				
							
			}
		},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**
 * Package列表
 */
Ext.define('Foss.QueryPacked.PackageGrid',{
	extend: 'Ext.grid.Panel',
	title : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.title'),//'包装信息查询结果',
	//id: 'Foss_QueryPacked_PackageGrid_ID',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame: true,
    //增加滚动条
    autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
	selType : "rowmodel", // 选择类型设置为：行选择
	
	//表格行可展开的插件
	plugins: [{
		header : true,
		ptype: 'rowexpander',
		//定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander: false,
		//行体内容
		rowBodyElement : 'Foss.FinishPacked.DetailInfoPanel'
	}],
	newSerialNumberWindow : null,
	getNewSerialNumberWindow : function(){
		if(this.newSerialNumberWindow==null){
			this.newSerialNumberWindow = Ext.create('Foss.QueryPacked.NewSerialNumberWindow');
		}
		return this.newSerialNumberWindow;
	},
	columns : [{
            xtype:'actioncolumn',
            flex: 0.5,
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.operate'),//'操作',
			align: 'center',
            items: [/*{
            	iconCls:'deppon_icons_fold',
                tooltip: '展开',
                handler: function(grid, rowIndex, colIndex) {
					grid.fireEvent('itemdblclick', grid, rowIndex, rowIndex, rowIndex);
                }
            },*/{
            	iconCls:'deppon_icons_print',
                tooltip: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.print'),//'打印新流水号',
                handler: function(grid, rowIndex, colIndex) {
					rowBodyElementId = rowIndex.internalId+'-rowbody-element';
					Ext.apply(packaging.querypacked,{
						waybillno : grid.getStore().data.items[rowIndex].data.wayBillNumber
					});
					//grid.up('gridpanel').getNewSerialNumberWindow().show();
					if(packaging.querypacked.isPermission('packaging/printNewSerialNoButton')){
						Ext.create('Foss.QueryPacked.NewSerialNumberWindow').show();
					}
					
                }
            }]
        }/*,{
			text : 'id',
			align: 'center',
			hidden: true,
			flex: 0.8,
			dataIndex : 'id'
		}*/,{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.wayBillNumber'),//'运单号',
			align: 'center',
			flex: 0.8,
			dataIndex : 'wayBillNumber'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packedDate'),//'包装时间',
			align: 'center',
			flex: 1,
			dataIndex : 'packedDate'
		},{
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.waybillCreateDept'),//"开单部门",
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'waybillCreateDept'
		},{
            text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.isInPackageArea'),//"是否在<br/>包装区",
			flex: 0.5,
			align: 'center',
            dataIndex: 'isInPackageArea'
        },{
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packedDept'),//'包装<br/>部门',
			flex: 1,
			align: 'center',
			xtype: 'ellipsiscolumn',
			dataIndex : 'packedDept'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.goodsName'),//'货物<br/>名称',
			flex: 0.5,
			align: 'center',
			dataIndex : 'goodsName'
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.waybillNum'),//"开单<br/>件数",
			flex: 0.5,
			align: 'center',
			//xtype: 'numbercolumn',
			dataIndex: 'waybillNum'
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packedNum'),//"包装<br/>件数",
			flex: 0.5,
			align: 'center',
			//xtype: 'numbercolumn',
			dataIndex: 'packedNum'
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packedMate'),//"包装<br/>材料",
			flex: 0.5,
			align: 'center',
			dataIndex: 'packedMate',
			renderer: function(value){
				if(value != null){
					var packeMate = FossDataDictionary.rendererSubmitToDisplay (value,'PACKAGING_MATE');
					return packeMate;
				}
			}
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packedVolume'),//"包装体积<br/>(方)",
			//xtype: 'numbercolumn',
			flex: 0.8,
			align: 'center',
			//format:'0.00000',
			dataIndex: 'packedVolume'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packedPerson'),//'包装人',
			flex: 0.8,
			align: 'center',
			dataIndex : 'packedPerson'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.createUser'),//'创建人',
			flex: 0.8,
			align: 'center',
			dataIndex : 'createUser'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.modifyUser'),//'修改人',
			flex: 0.8,
			align: 'center',
			dataIndex : 'modifyUser'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.loginDate'),//'登入包装货区时间',
			flex: 1,
			align: 'center',
			format:'Y-m-d H:i:s',
			dataIndex : 'loginDate'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.logoutDate'),//'登出包装货区时间',
			flex: 1,
			align: 'center',
			format:'Y-m-d H:i:s',
			dataIndex : 'logoutDate'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.packRequire'),//'代包装要求',
			flex: 0.8,
			align: 'center',
			hidden: true,
			dataIndex : 'packRequire'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.plusNum'),//'加托个数',
			flex: 0.8,
			align: 'center',
			hidden: true,
			dataIndex : 'plusNum'
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.QueryPacked.PackageStore');
		me.selModel = Ext.create('Ext.selection.RowModel');
		//导出按钮
		me.tbar = [{
			xtype : 'button',
			text : packaging.querypacked.i18n('foss.packaging.button.export'),//导出
			disabled:(packaging.querypacked.isPermission('packaging/exportExcelPackedButton'))?false:true,
			hidden:(packaging.querypacked.isPermission('packaging/exportExcelPackedButton'))?false:true,
			handler : function(){
				packaging.querypacked.exportExcelPacked();
			}
		}],
		/*me.bbar = Ext.create('Ext.PagingToolbar', {
			store: me.store,
			displayInfo: true,
			displayMsg: '当前显示从{0}到 {1}条记录，共 {2}条',
			emptyMsg: "无记录"
		});*/
		//自定义分页控件
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store,
			plugins: 'pagesizeplugin'
		});
		packaging.querypacked.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 导出到excel
 */
packaging.querypacked.exportExcelPacked = function(){
	//权限校验
	if(!packaging.querypacked.isPermission('packaging/exportExcelPackedButton')){
		return;
	}
	
	
	var actionUrl= packaging.realPath('exportExcelPacked.action');	
	//执行查询，首先货物查询条件，packaging.packageQueryForm为全局变量，在查询条件的FORM创建时生成
	var queryParams = packaging.packageQueryForm.getValues();
	var params = {
			'queryUnpackVo.queryPackedConditionEntity.waybillCreateDept':queryParams.waybillCreateDept,
			'queryUnpackVo.queryPackedConditionEntity.packedDept':queryParams.packDeptCode,
			'queryUnpackVo.queryPackedConditionEntity.packedPerson':queryParams.packedPerson,
			'queryUnpackVo.queryPackedConditionEntity.packedBeginDate':queryParams.packedBeginDate,
			'queryUnpackVo.queryPackedConditionEntity.packedEndDate':queryParams.packedEndDate,
			'queryUnpackVo.queryPackedConditionEntity.wayBillNumber':queryParams.wayBillNumber
		};
	if(!Ext.fly('downloadAttachFileForm')){
	    var frm = document.createElement('form');
	    frm.id = 'downloadAttachFileForm';
	    frm.style.display = 'none';
	    document.body.appendChild(frm);
	}

	Ext.Ajax.request({
		url:actionUrl,
		form: Ext.fly('downloadAttachFileForm'),
		method : 'POST',
		params : params,
		isUpload: true,
		exception : function(response,opts) {
		},
		success : function(response,opts) {	
		}		
	});

	

}

/**
 * 新流水号model
 */
Ext.define('Foss.QueryPacked.PrintSerialNumberModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'newSerialNo',type:'string'},
		{name: 'oldSerialNo',type:'string'}
	]
});

/**
 * 新流水号store
 */
Ext.define('Foss.QueryPacked.PrintSerialNumberStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.QueryPacked.PrintSerialNumberModel',
	proxy: {
		//代理的类型为memory
		type: 'memory'
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 *原流水号model
 */
Ext.define('Foss.QueryPacked.OldSerialNumberModel',{
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'oldSerialNo',type:'string'},
		{name: 'newSerialNo',type:'string'},
		{name: 'isPacked',type:'string'},
		{name: 'tempSerialNo',type:'string'},
		{name: 'relationId',type:'string'},
		{name: 'actualId',type:'string'}
	]
});

/**
 * 原流水号store
 */
Ext.define('Foss.QueryPacked.OldSerialNumberStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.QueryPacked.OldSerialNumberModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory'
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 显示流水号model
 */
Ext.define('Foss.QueryPacked.ShowSerialNoModel', {
	extend: 'Ext.data.Model',
	//定义字段
	fields: [
		{name: 'oldSerialNo',type:'string'}
	]
});

/**
 * 显示流水号store
 */
Ext.define('Foss.QueryPacked.ShowSerialNoStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.QueryPacked.ShowSerialNoModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为memory
		type: 'memory'
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**
 * 显示原流水号grid
 */
Ext.define('Foss.QueryPacked.ShowSerianoNoGrid', {
    extend: 'Ext.grid.Panel',
	store: Ext.create('Foss.QueryPacked.ShowSerialNoStore'),
	autoScroll:true,
	hideHeaders: true,
	columns: [{
		width : 40,
		dataIndex: 'oldSerialNo' 
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	bindData : function(record,value,metadata,store,view){
		var newSerialNo = record.get('newSerialNo');
		var grid = view.up('grid'),
		    window = grid.up('window'),
		    oldGrid = window.getOldSerialNumberGrid(),
		    oldStore = oldGrid.getStore();
		var oldArray = new Array();
		oldStore.each(function(item,index){
			if(item.get('newSerialNo')==newSerialNo){
				oldArray.push({'oldSerialNo': item.get('oldSerialNo')});
			}
		})
		this.store.loadData(oldArray);
	}
});

/**
 * 选择打印标签
 */
//定义新标签的显示窗口
Ext.define('Foss.QueryPacked.NewSerialNumberWindow', {
	extend: 'Ext.window.Window',
	title: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.newSerialNumberWindow.title'),//'选择新标签',
	width: 300,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	modal:true,
	closeAction: 'hide',
	oldSerialNumberGrid: null,
	getOldSerialNumberGrid : function(){
		var me = this;
		if(this.oldSerialNumberGrid==null){
			this.oldSerialNumberGrid = Ext.create('Ext.grid.Panel', {
				hidden: true,
				store: Ext.create('Foss.QueryPacked.OldSerialNumberStore'),
				columns: [{
					width : 100,
					dataIndex: 'oldSerialNo' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'newSerialNo' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'isPacked' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'tempSerialNo' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'relationId' 
				},{
					width : 100,
					hidden: true,
					dataIndex: 'actualId' 
				}]
			});
		}
		return this.oldSerialNumberGrid;		
	},
	printSerialNumberGrid : null,
	getPrintSerialNumberGrid : function(){
		if(this.printSerialNumberGrid==null){
			this.printSerialNumberGrid = Ext.create('Ext.grid.Panel', {
				title: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.newSerialNumberWindow.printSerialNumberGrid.title'),//'新件数流水号',
				store: Ext.create('Foss.QueryPacked.PrintSerialNumberStore'),
				//id: 'Foss_querypackedindex_printSerialNumberGrid_ID',
				frame : true,
				//hideHeaders: true,//add
				selModel: Ext.create('Ext.selection.CheckboxModel'),
				columns: [{
					//xtype: 'tipgridcolumn',
					xtype: 'tipcolumn',
					flex: 1,
					tipBodyElement: 'Foss.QueryPacked.ShowSerianoNoGrid',
					//配置tip的一些属性
					tipConfig: {
				        //如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
						maxWidth: 425,
						width: 100,
						cls:'autoHeight',
						bodyCls:'autoHeight',
				        //Tip的Body是否随鼠标移动
						trackMouse: true
					},
					dataIndex: 'newSerialNo'
				},{
					//xtype: 'tipgridcolumn',
					hidden: true,
					dataIndex: 'oldSerialNo'
				}]
			});
			
		}
		return this.printSerialNumberGrid;		
	},
	showNewSerialNumber:function(me){
		//根据运单号，查询该运单号新流水号，新流水号可能包括旧流水号（未合并的包装获取）
	    Ext.Ajax.request({
			//url: '../packaging/queryNewSerialNo.action',
			url : packaging.realPath('queryNewSerialNo.action'),
			params: {
				'queryUnpackVo.waybillno':packaging.querypacked.waybillno
			},
			success: function(response){
				
				result = Ext.decode(response.responseText);
				var printGrid = me.getPrintSerialNumberGrid();
				var oldGrid = me.getOldSerialNumberGrid();
				printGrid.store.removeAll();
				printGrid.store.loadData(result.queryUnpackVo.newSerialList);	
				oldGrid.store.loadData(result.queryUnpackVo.serialRelationList);	
			}
		});
	},
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getPrintSerialNumberGrid(),{
					xtype:'fieldcontainer',
					fieldLabel: '',
					columnWidth:1,
					defaultType: 'checkboxfield',
					items:[{
					       boxLabel:'标签显示"德邦物流"',
					       name:'isPrintLogo',
					       inputValue:'Y',
					       checked: true,
					       uncheckedValue:'N'
					}]
				},{
				border : false,
				xtype : 'container',
				height:100,
				layout:'column',
				defaults: {
					margin:'0 0 40 0'
				},
				items : [{
					border : false,
					columnWidth:.6,
					html: '&nbsp;'
				},{
					columnWidth:.4,
					xtype : 'button',
					text: packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.newSerialNumberWindow.confirm'),//'确认',
					handler: function(btn) {
						var newSerial = me.getPrintSerialNumberGrid().getSelectionModel();
						var newSerialArray = new Array();
						if(newSerial.getSelection().length==0){
							//'提示','没有选择表格中的记录'
							Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.warning'), packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.newSerialNumberWindow.selectHint'));	
						}else{
							for(var i=0;i<newSerial.getSelection().length;i++){                          
								newSerialArray.push(newSerial.getSelection()[i].data);
							}
							//
							var data = {
							 'queryUnpackVo':{
								'waybillno':packaging.querypacked.waybillno,
								'newSerialList':newSerialArray
							}};
							var vurl = 'http://localhost:8077/print';
							//将新流水号传到后台调用接送货接口获取需要打印的数据然后再调用web打印功能
						    Ext.Ajax.request({
								//url: '../packaging/printNewSerialNo.action',
								url : packaging.realPath('printNewSerialNo.action'),
								jsonData: data,
								
								success: function(response){
									result = Ext.decode(response.responseText);
									var printDataArray = result.queryUnpackVo.barcodePrintLabelDtoList;
									for(var i=0;i<printDataArray.length;i++){
										var printData = printDataArray[i];	
										//var printSerialNos = new Array();
										Ext.data.JsonP.request({
											url: vurl,
											callbackKey: 'callback',
										    params: {
											     lblprtworker:"CommLabelPrintWorker",	
											  	 addr1: printData.addr1,
												 addr2: printData.addr2,
												 addr3: printData.addr3,
												 addr4: printData.addr4,
												 location1: printData.location1,
												 location2: printData.location2,
												 location3: printData.location3,
												 location4: printData.location4,
												 optusernum: printData.optuserNum,
												 number: printData.waybillNumber,
												 serialnos: printData.printSerialnos,
												 leavecity: printData.leavecity,
												 destination: printData.destination,
												 isagent: printData.isAgent,
												 stationnumber: printData.destinationCode,
												 deptno: printData.lastTransCenterNo,
												 finaloutfieldid: printData.finaloutfieldid,
												 finaloutname: printData.lastTransCenterCity,
												 weight: printData.weight,
												 totalpieces: printData.totalPieces,
												 packing: printData.packing,
												 unusual: printData.unusual,
												 transtype: printData.transtype,
												 printdate: printData.printDate,
												 deliver: printData.deliverToDoor,
												 goodstype: printData.goodstype,
												 preassembly: printData.preassembly,
												 signFlag: printData.isStarFlag,
												 countyRegion:printData.countyRegion,
												 deliveryBigCustomer:printData.deliveryBigCustomer,
												 receiveBigCustomer:printData.receiveBigCustomer,
												 isExhibitCargo:printData.isExhibitCargo,
												 isPrintLogo:btn.up('window').down('checkboxfield').getValue()==true?'Y':'N'
											    },
										    callback: function() {
									 			//回调函数，不管请求成功与否都执行
									 			//alert("callback");
										    	
											},
										    success: function(result, request){								
										    	var ret_code = result.data.code;
										    	if(ret_code == '1'){	
										    		//printSerialNos.push(printData.printSerialnos);
										    		Ext.ux.Toast.msg(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), packaging.querypacked.i18n('foss.packaging.querypacked.packageGrid.newSerialNumberWindow.printSuccess'), 'ok', 3000);
										    		Ext.Ajax.request({
										    			url : packaging.realPath('insertLabelRecord.action'),
										    			params: {
															'queryUnpackVo.waybillno':printData.waybillNumber,
															'queryUnpackVo.serialNo':printData.printSerialnos
														},
														success:function(response){
															
														}
										    		});
										    	}else{
										    		Ext.ux.Toast.msg(packaging.querypacked.i18n('foss.packaging.querypacked.hint'), result.data.msg, 'error', 3000);
										    	}								        
										    },
										    failure : function (response){
										    	//var text = response.responseText;
										    	 Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), packaging.querypacked.i18n('foss.packaging.querypacked.error.printMessage'));   
										    }
										    	
										});
									}
									
								},
								 exception: function(response){
							        	var result = Ext.decode(response.responseText);
							            Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), result.message);   
							        }
							});
						}
						
					}
				}]
			}
		];
		me.showNewSerialNumber(me);
		me.callParent([cfg]);
	}/*,
	bindData : function(record){
		//绑定表格数据到表单上
		//this.getPackageForm().getForm().loadRecord(record);
		var waybillno = record.get('wayBillNumber')
		packaging.querypacked.getWaybillPackInfo(waybillno);
	}*/
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	var packageQueryForm = Ext.create('Foss.QueryPacked.PackageQueryForm');
	packaging.packageQueryForm = packageQueryForm;
	//设置新增或修改状态作为全局变量
	Ext.apply(packaging.querypacked,{
		editStatus : 'edit'
	});
	Ext.Ajax.request({
		//url:'../packaging/queryPackDept.action',
		url : packaging.realPath('queryPackDept.action'),
		//获取当前登录人的所在部门
		success:function(response){
			var result = Ext.decode(response.responseText);	
			Ext.apply(packaging.querypacked,{
				packedDept : result.queryUnpackVo.currentDeptDto.deptName,
				packDeptCode : result.queryUnpackVo.currentDeptDto.deptCode
			});
			packaging.packageQueryForm.getForm().findField('packedDept').setValue(result.queryUnpackVo.currentDeptDto.deptName);
			packaging.packageQueryForm.getForm().findField('packDeptCode').setValue(result.queryUnpackVo.currentDeptDto.deptCode);
		},
		exception: function(response){
        	var result = Ext.decode(response.responseText);
            Ext.Msg.alert(packaging.querypacked.i18n('foss.packaging.querypacked.error'), result.message);   
        }
	});
	Ext.create('Ext.panel.Panel',{					 
		id: 'T_packaging-querypackedindex_toolbar',
		height:42,
		cls:'floatToolbar',
		items : [{
			//id:'T_packaging-querypackedindex_addPackageInfo',
			tabIndex: 1,
			textAlign : 'center',
			text: packaging.querypacked.i18n('foss.packaging.querypacked.panel'),//'添加包装信息',
			xtype: 'button',
			disabled:(packaging.querypacked.isPermission('packaging/addPackageInfoButton'))?false:true,
			hidden:(packaging.querypacked.isPermission('packaging/addPackageInfoButton'))?false:true,
			handler: function() {
				if(!packaging.querypacked.isPermission('packaging/addPackageInfoButton')){
					return;
				}
				//alert('You clicked the button!');
				//.show();
				//弹出包装录入界面
				//Ext.destroy('Foss.FinishPacked.DetailInfoPanel');
				//Ext.destroy('Foss_finishPacked_PackageForm_wayBillNumber_ID');
				Ext.apply(packaging.querypacked,{
					editStatus : 'edit'
				});
				
				packaging.querypacked.createWindow=1;
				Ext.create('Ext.window.Window', {
				    title: packaging.querypacked.i18n('foss.packaging.querypacked.detailInfoPanel.window.title'),//'录入包装信息',
				    cls:'specialWin',
				    height: 400,
				    width: 1000,
				    modal: true,
				    layout: 'fit',
				    items: [
				            Ext.create('Foss.FinishPacked.DetailInfoPanel')
				            ],
		            listeners: {	
						hide: function(field, e){
							packaging.querypacked.oldValue='';
							packaging.querypacked.createWindow=0;
						},
						close: function(field, e){
							packaging.querypacked.oldValue='';
							packaging.querypacked.createWindow=0;
						}
					}
				    
				        
				}).show();
			}
		}],
		renderTo: 'T_packaging-querypackedindex-body'
	});
	Ext.create('Ext.panel.Panel',{	
		id:'T_packaging-querypackedindex_content',
		cls:"panelContent",
		bodyCls:'panelContent-body',
		layout:'auto',
		items : [
			packaging.packageQueryForm,
			Ext.create('Foss.QueryPacked.PackageGrid'/*,{
				id : 'Foss_querypackedindex_PackageGrid_Id'
			}*/)
		],
		renderTo: 'T_packaging-querypackedindex-body'
	});
});
