/**
 *包装金额汇总（PDA与PC）查询表单
 */
Ext.define('Foss.QueryPacked.PdaPcTotalPriceQueryForm',{
	extend: 'Ext.form.Panel',
	title: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.title'),//'包装金额汇总（PDA与PC）',
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
		name: 'waybillNo',
		fieldLabel: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.waybillNo'),//'运单号',
		vtype: 'waybill',
		columnWidth:.25,
	},{
		name: 'packedDept',
		fieldLabel: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.pdaPcPackedDept'),//'包装部门',
		allowBlank: false,
		readOnly:true,
		columnWidth:.25
	},{//包装部门编码
		name: 'packedDeptCode',
		hidden:true,
		columnWidth:.25
	},{
		xtype: 'dynamicPackagingSupplierSelector',
		name: 'packageSupplierCode',
		//orgCodeCode : FossUserContext.getCurrentDeptCode(),
		fieldLabel: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.packageSupplierName'),//'包装供应商',
		labelWidth:80,
		columnWidth:.25/*,
		listeners : {
			beforequery : function(queryEvent, eOpts) {
				Ext.apply(queryEvent.combo, {
							orgCodeCode : FossUserContext.getCurrentDeptCode()
						});
			}
		}*/
	},{
		xtype: 'dynamicorgcombselector',
		name: 'billOrgCode',
		fieldLabel: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.waybillCreateDept'),//'开单部门',
		type : 'ORG',
		salesDepartment : 'Y',
		columnWidth:.25,
	}, {
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fromName: 'packedBeginDate',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-14
				,00,00,00), 'Y-m-d H:i:s'),
		toName: 'packedEndDate',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59), 'Y-m-d H:i:s'),
		fieldLabel: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.packedEndDate'),//'包装时间',
		disallowBlank:true,
		fieldId: 'Foss_queryPdaPcTotalPriceindex_pdaPcTotalPriceQueryForm_packDate_ID',
		columnWidth: .5
	},{
		border : false,
		xtype : 'container',
		columnWidth:0.9,
		layout:'column',
		defaults: {
			margin:'5 0 5 0',
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.resetButton'),//'重置',
			handler: function() {
				var form = this.up('form').getForm();
				form.findField('packedBeginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-31
						,new Date().getHours(),new Date().getMinutes(),new Date().getSeconds()), 'Y-m-d H:i:s'));
				form.findField('packedEndDate').setValue(Ext.Date.format(new Date(), 'Y-m-d H:i:s'));
				form.findField('waybillNo').setValue(null);
				form.findField('packageSupplierCode').setValue(null);
				form.findField('waybillCreateDept').setValue(null);
				form.findField('packedDept').setValue(backupquery.querypacked.packedDept);
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			cls:'yellow_button',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.queryButton'),//'查询',
			disabled:(backupquery.queryPdaPcTotalPrice.isPermission('packaging/queryPdaPcTotalPrice'))?false:true,
			hidden:(backupquery.queryPdaPcTotalPrice.isPermission('packaging/queryPdaPcTotalPrice'))?false:true,
			handler: function() {
				
				if(backupquery.queryPdaPcTotalPrice.isPermission('packaging/queryPdaPcTotalPrice')){
					var beginDate = backupquery.pdaPcTotalPriceQueryForm.getValues().packedBeginDate;
					var endDate = backupquery.pdaPcTotalPriceQueryForm.getValues().packedEndDate;
					var difDate = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(beginDate,'Y-m-d H:i:s');
					var difTime = 31*24*60*60*1000-parseInt(difDate);
					//查询条件是否合法（非空等相关约束）
					if(backupquery.pdaPcTotalPriceQueryForm.getForm().isValid()){
						if(difTime>=0){
							//自定义分页
							backupquery.queryPdaPcTotalPrice.pagingBar.moveFirst();
						}else{
							//警告，时间跨度不能超过31天！
							Ext.Msg.alert(backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.warning'), backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.timeHint'));	
						}
					}else{
						//警告，请输入查询条件！
						Ext.Msg.alert(backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.warning'),  backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcTotalPriceQueryForm.queryHint'));
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

Ext.define('Foss.QueryPdaPcTotalPrice.PackageModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	//定义字段
	fields: [{
		name: ' extid ', type: 'string'
	},{
	   name:'title'  ,type:'string'    //包装金额汇总（PDA与PC）查询表单
	}, {// 运单号
	    name:'waybillNo', type:'string'
	  },{//创建时间  包装时间
		 name:'createTime' 	,
		 type : 'date',
		 convert: dateConvert   	
	  },{//开单部门名称
		 name:'billOrgName',type:'string'    	
	  },{//包装部门名称
		 name:'packageOrgName',type:'string'    	
	  },{//理论打木架体积
		 name:'theoryFrameVolume' ,
		 type : 'number'
	  },{//实际打木架体积
		 name:'actualFrameVolume' ,
		 type : 'number'
	  },{//理论打木箱体积
		 name:'theoryWoodenVolume' ,
		 type : 'number'
	  },{//实际打木箱体积
		 name:'actualWoodenVolume',
		 type : 'number'
	  },{//理论打木托个数
		 name:'theoryMaskNumber',
		 type : 'number'
	  },{//实际打木托个数
		 name:'actualMaskNumber',
		 type : 'number'
	  },{//木条长度
		 name:'woodenBarLong',
		 type : 'number'
	  },{//	气泡膜体积
		 name:'bubbVelamenVolume',
		 type : 'number'
	  },{// 	缠绕膜体积
		 name:'bindVelamenVolume',
		 type : 'number'
	  },{//	包带根数
		 name:'bagBeltNum',
		 type : 'number'
	  },{//	包装供应商
		 name:'packageSupplierName',
		 type : 'string'
	  },{//创建人
		 name:'createUserName',
		 type:'string'
	  },{//新增人
		 name:'newAddUserName',
		 type:'string'
				 
	  },{//修改人
		 name:'modifyUserName',
		 type:'string'
	   },{//应付金额
	    name:'packagePayableMoney',
	    type:'number'
	   },{//开单包装费
		   name:'waybillPackgeFee',
		   type:'number'
	   },{//是否盈利
	    name:'profitStatus',
	    type:'string'
	    }]
});

Ext.define('Foss.QueryPdaPcTotalPrice.PackageStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.QueryPdaPcTotalPrice.PackageModel',
	pageSize: 10,
	autoLoad: false,
	//定义一个代理对象
	proxy: {
		//代理的类型为ajax
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		//路径
		//url:'../packaging/queryPdaPackageMainPrice.action',
		url : backupquery.realPath('queryPdaPcTotalPrice.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'queryPackedPriceVo.queryPdaPcPackResultList',
			//返回总数
			totalProperty : 'totalCount',
			successProperty: 'success'
		},
		listeners:{
			exception:function(reader,response,eopts){
				var result = Ext.decode(response.responseText);
				//错误
	            Ext.Msg.alert(backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.warning'), result.message);   
			}
		}
	},
	listeners: {
		//查询事件
			beforeload : function(store, operation, eOpts) {
				var queryParams = backupquery.pdaPcTotalPriceQueryForm.getValues();
				Ext.apply(operation, {
					//设置查询条件与后台实体对应
					params : {
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.waybillNo':queryParams.waybillNo,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedDept':queryParams.packedDept,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedDeptCode':queryParams.packedDeptCode,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packageSupplierCode':queryParams.packageSupplierCode,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.billOrgCode':queryParams.billOrgCode,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedBeginDate':queryParams.packedBeginDate,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedEndDate':queryParams.packedEndDate,
				}
				});	
			},
			load: function( store, records, successful, eOpts ){
				if(successful){
					//统计列表信息
					if(records.length==0){
						//提示，未查询到符合条件的包装数据！
						Ext.ux.Toast.msg(backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.hint'), backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.packageGrid.packageStore.queryHint'), 'success', 5000);
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
 * 包装金额汇总（PDA与PC）列表
 */
Ext.define('Foss.queryPdaPcTotalPrice.PdaPcPackageGrid',{
	extend: 'Ext.grid.Panel',
	title : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.title'),// '包装金额汇总（PDA与PC)查询结果',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame: true,
    //增加滚动条
    autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
	selType : "rowmodel", // 选择类型设置为：行选择
	columns : [{
			xtype : 'ellipsiscolumn',
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.waybillNo'),// '运单号',
			align: 'center',
			width:80,
			
			dataIndex : 'waybillNo'
		},{
			xtype : 'datecolumn',
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.createTime'),// '包装时间',
			align: 'center',
			width:130,
			dataIndex : 'createTime',
			format : 'Y-m-d H:i:s'
		},{
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.billRrgName'),// '开单部门,
			align: 'center',
			width:130,
			xtype: 'ellipsiscolumn',
			dataIndex: 'billOrgName'
		},{
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.packageOrgName'),// '包装部门,
			align: 'center',
			width:130,
			xtype: 'ellipsiscolumn',
			dataIndex: 'packageOrgName'
		},{
            text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.theoryFrameVolume'),// '理论打木架体积",
            width:70,
			align: 'center',
            dataIndex: 'theoryFrameVolume'
        },{
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.theoryWoodenVolume'),// '理论打木箱体积,
			width:70,
			align: 'center',
			xtype: 'ellipsiscolumn',
			dataIndex : 'theoryWoodenVolume'
		},{
			xtype : 'ellipsiscolumn',
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.theoryMaskNumber'),// '理论打木托个数',
			width:70,
			align: 'center',
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			dataIndex : 'theoryMaskNumber'
		},{
			xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.actualFrameVolume'),// '实际打木架体积",
			width:70,
			align: 'center',		
			dataIndex: 'actualFrameVolume'
		},{
			xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.actualWoodenVolume'),// '实际打木箱体积",
			width:70,
			align: 'center',
			defaultValue:0,
			dataIndex: 'actualWoodenVolume'
		},{
			xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.actualMaskNumber'),// '实际打木托个数",
			width:70,
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			align: 'center',
			dataIndex: 'actualMaskNumber'
		},{xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.woodenBarLong'),//木条长度
			width:70,
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			align: 'center',
			dataIndex: 'woodenBarLong'    	
	  },{
	        xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.bubbVelamenVolume'),//	气泡膜体积
			width:70,
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			align: 'center',
			dataIndex: 'bubbVelamenVolume'    
	  },{
		  	xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.bindVelamenVolume'),// 	缠绕膜体积
			width:70,
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			align: 'center',
			dataIndex: 'bindVelamenVolume'   
	  },{
	        xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.bagBeltNum'),//	包带根数
			width:70,
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			align: 'center',
			dataIndex: 'bagBeltNum'    
	  },{
			xtype : 'ellipsiscolumn',
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.packagePayableMoney'),// '应付金额',
			width:70,
			align: 'center',
			dataIndex : 'packagePayableMoney'
		},{
			xtype : 'ellipsiscolumn',
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.waybillPackgeFee'),// '开单包装费',
			width:70,
			align: 'center',
			dataIndex : 'waybillPackgeFee'
		},{
			xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.createUserName'),// '创建人",
			width:70,
			align: 'center',
			dataIndex: 'createUserName'
		},{
			xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.newAddUserName'),// '新增人",
			width:70,
			align: 'center',
			dataIndex: 'newAddUserName'
		},{
			xtype : 'ellipsiscolumn',
			text: backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.modifyUserName'),// '修改人",
			width:70,
			align: 'center',
			dataIndex: 'modifyUserName'
		},{
			xtype : 'ellipsiscolumn',
			labelWidth:80,
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.packageSupplierName'),// '包装供应商',
			width:120,
			align: 'center',
			dataIndex : 'packageSupplierName'
		},{
			xtype : 'ellipsiscolumn',
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.pdaPcPackageGrid.profitStatus'),// '是否盈利',
			width:70,
			align: 'center',
			dataIndex : 'profitStatus'
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.QueryPdaPcTotalPrice.PackageStore');
		me.selModel = Ext.create('Ext.selection.RowModel');
		//导出按钮
		me.tbar = [{
			xtype : 'button',
			text : backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.button.export'),//导出
			disabled:(backupquery.queryPdaPcTotalPrice.isPermission('packaging/exportExcelPdaPcPackedButton'))?false:true,
			hidden:(backupquery.queryPdaPcTotalPrice.isPermission('packaging/exportExcelPdaPcPackedButton'))?false:true,
			handler : function(){
				backupquery.queryPdaPcTotalPrice.exportExcelPacked();
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
		backupquery.queryPdaPcTotalPrice.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

/**
 * 导出到excel
 */
backupquery.queryPdaPcTotalPrice.exportExcelPacked = function(){
	//权限校验
			if(!backupquery.queryPdaPcTotalPrice.isPermission('packaging/exportExcelPdaPcPackedButton')){
				return;
			}
			
			
			var actionUrl= backupquery.realPath('exportPdaPcExcelPacked.action');	
			//执行查询，首先货物查询条件，packaging.packageQueryForm为全局变量，在查询条件的FORM创建时生成
			var queryParams = backupquery.pdaPcTotalPriceQueryForm.getValues();
			var params = {
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.waybillNo':queryParams.waybillNo,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedDept':queryParams.packedDept,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedDeptCode':queryParams.packedDeptCode,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packageSupplierCode':queryParams.packageSupplierCode,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.billOrgCode':queryParams.billOrgCode,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedBeginDate':queryParams.packedBeginDate,
					'queryPackedPriceVo.queryPdaPcPackConditionEntity.packedEndDate':queryParams.packedEndDate,
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

Ext.onReady(function() {
	Ext.QuickTips.init();
	var pdaPcTotalPriceQueryForm = Ext.create('Foss.QueryPacked.PdaPcTotalPriceQueryForm');
	backupquery.pdaPcTotalPriceQueryForm = pdaPcTotalPriceQueryForm;
	//设置新增或修改状态作为全局变量
	Ext.apply(backupquery.queryPdaPcTotalPrice,{
		editStatus : 'edit'
	});
	Ext.Ajax.request({
		//url:'../packaging/queryPackDept.action',
		url : backupquery.realPath('queryPackDept.action'),
		//获取当前登录人的所在部门
		success:function(response){
			var result = Ext.decode(response.responseText);	
			Ext.apply(backupquery.queryPdaPcTotalPrice,{
				pdaPackedDept : result.queryUnpackVo.currentDeptDto.deptName,
				pdaPackDeptCode : result.queryUnpackVo.currentDeptDto.deptCode
			});
			backupquery.pdaPcTotalPriceQueryForm.getForm().findField('packedDept').setValue(result.queryUnpackVo.currentDeptDto.deptName);
			backupquery.pdaPcTotalPriceQueryForm.getForm().findField('packedDeptCode').setValue(result.queryUnpackVo.currentDeptDto.deptCode);
		},
		exception: function(response){
        	var result = Ext.decode(response.responseText);
            Ext.Msg.alert(backupquery.queryPdaPcTotalPrice.i18n('foss.packaging.querypacked.error'), result.message);   
        }
	});
	
	Ext.create('Ext.panel.Panel',{	
		id:'T_backupquery-queryPdaPcTotalPriceindex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body',
		layout:'auto',
		items : [
			backupquery.pdaPcTotalPriceQueryForm,
			Ext.create('Foss.queryPdaPcTotalPrice.PdaPcPackageGrid')
		],
		renderTo: 'T_backupquery-queryPdaPcTotalPriceindex-body'
	});
});

