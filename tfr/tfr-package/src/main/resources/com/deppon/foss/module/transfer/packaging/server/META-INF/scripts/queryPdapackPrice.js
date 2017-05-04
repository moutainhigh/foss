/**
 * 审核状态
 */
Ext.define('packaging.queryPdapackPrice.AuditStatusStore',{
	extend:'Ext.data.Store',
	fields:['name','value'],
	data:[
		{name:packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.waitingaudit'),value:'WAITINGAUDIT'},
		{name:packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.hasudited'),value:'HASAUDITED'},
		{name:packaging.queryPdapackPrice.i18n('foss.packaging.queryUnpack.queryUnpackForm.all'),value:''}
	]
}); 
 
/**
 *PDA端扫描生成包装金额查询表单
 */
Ext.define('Foss.QueryPacked.PdaPackageQueryForm',{
	extend: 'Ext.form.Panel',
	title: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.title'),//'PDA端扫描生成包装金额查询',
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
		fieldLabel: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.waybillNo'),//'运单号',
		vtype: 'waybill',
		columnWidth:.25,
	},{
		name: 'pdaPackedDept',
		fieldLabel: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.billOrgName'),//'包装部门',
		allowBlank: false,
		readOnly:true,
		columnWidth:.25
	},{//包装部门编码
		name: 'pdaPackDeptCode',
		hidden:true,
		columnWidth:.25
	},{
		 xtype: 'dynamicPackagingSupplierSelector',
		name: 'packageSupplierCode',
		//orgCodeCode : FossUserContext.getCurrentDeptCode(),
		fieldLabel: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.packageSupplierName'),//'包装供应商',
		labelWidth:80,
		columnWidth:.25
		/*listeners : {
			beforequery : function(queryEvent, eOpts) {
				Ext.apply(queryEvent.combo, {
							orgCodeCode : FossUserContext.getCurrentDeptCode()
						});
			}
		}*/
	}, {
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fromName: 'packedBeginDate',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-14
				,00,00,00), 'Y-m-d H:i:s'),
		toName: 'packedEndDate',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59), 'Y-m-d H:i:s'),
		fieldLabel: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.packedEndDate'),//'包装时间',
		disallowBlank:true,
		fieldId: 'Foss_queryPdapackPriceindex_pdaPackageQueryForm_packDate_ID',
		columnWidth: .5
	}, {
		xtype : 'combobox',
		name : 'auditStatus',
		fieldLabel : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus'),// '开单部门',
		store:Ext.create('packaging.queryPdapackPrice.AuditStatusStore'),
		queryMode:'local',
		displayField: 'name',
		valueField: 'value',
		columnWidth : .3,
		value:''
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
			text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.resetButton'),//'重置',
			handler: function() {
				var form = this.up('form').getForm();
				form.findField('packedBeginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-15
						,new Date().getHours(),new Date().getMinutes(),new Date().getSeconds()), 'Y-m-d H:i:s'));
				form.findField('packedEndDate').setValue(Ext.Date.format(new Date(), 'Y-m-d H:i:s'));
				form.findField('waybillNo').setValue(null);
				form.findField('packageSupplierCode').setValue(null);
				form.findField('pdaPackedDept').setValue(packaging.querypacked.pdaPackedDept);
			}
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			cls:'yellow_button',
			text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.queryButton'),//'查询',
			disabled:(packaging.queryPdapackPrice.isPermission('packaging/queryPdaPackagePrice'))?false:true,
			hidden:(packaging.queryPdapackPrice.isPermission('packaging/queryPdaPackagePrice'))?false:true,
			handler: function() {
				
				if(packaging.queryPdapackPrice.isPermission('packaging/queryPdaPackagePrice')){
					var beginDate = packaging.pdaPackageQueryForm.getValues().packedBeginDate;
					var endDate = packaging.pdaPackageQueryForm.getValues().packedEndDate;
					var difDate = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(beginDate,'Y-m-d H:i:s');
					var difTime = 15*24*60*60*1000-parseInt(difDate);
					//查询条件是否合法（非空等相关约束）
					if(packaging.pdaPackageQueryForm.getForm().isValid()){
						if(difTime>=0){
							//自定义分页
							packaging.queryPdapackPrice.pagingBar.moveFirst();
						}else{
							//警告，时间跨度不能超过15天！
							Ext.Msg.alert(packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.warning'), packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.timeHint'));	
						}
					}else{
						//警告，请输入查询条件！
						Ext.Msg.alert(packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.warning'),  packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageQueryForm.queryHint'));
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

Ext.define('Foss.QueryPdapackPrice.PackageModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	//定义字段
	fields: [{
		name:'id' ,type :'string'
	},{
		name: ' extid ', type: 'string'
	},{
	   name:'title' , type: 'string'     //PDA端扫描生成包装金额查询结果
	},{ 
	   name:'waybillNo'  , type: 'string'    //运单号
	},{
	    name:'createTime'  ,    //包装时间
	    type : 'date',
	    convert: dateConvert 
	},{
	    name:'billOrgCode',      //开单部门code
	    type: 'string'
	},{
	    name:'billOrgName', type: 'string'      //开单部门名称
	},{
	   name:'theoryFrameVolume', type: 'number'      //理论打木架体积
	},{
	   name:'theoryWoodenVolume', type: 'number'      //理论打木箱体积
	},{
	   name:'theoryMaskNumber', type: 'number'      //理论打木托个数
	},{
	  name:'actualFrameVolume', type: 'number'      //实际打木架体积
	},{
	  name:'actualWoodenVolume', type: 'number'      //实际打木箱体积
	},{
	  name:'actualMaskNumber', type: 'number'      //实际打木托个数
	},{
	  name:'createUserName', type: 'string'      //创建人
	},{
	  name:'packagePayableMoney' , type: 'number'     //应付金额
	},{
	  name:'packageSupplierName' , type: 'string'     //包装供应商
	},{
	  name:'packageSupplierCode', type: 'string'      //包装供应商code
	},{
		//审核状态
		name : 'auditStatus', type: 'string'
	}]
});

Ext.define('Foss.QueryPdapackPrice.PackageStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.QueryPdapackPrice.PackageModel',
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
		url : packaging.realPath('queryPdaPackage.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'queryPackedPriceVo.pdaQueryPackResultList',
			//返回总数
			totalProperty : 'totalCount',
			successProperty: 'success'
		},
		listeners:{
			exception:function(reader,response,eopts){
				var result = Ext.decode(response.responseText);
				//错误
	            Ext.Msg.alert(packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.warning'), result.message);   
			}
		}
	},
	listeners: {
		//查询事件
			beforeload : function(store, operation, eOpts) {
				var queryParams = packaging.pdaPackageQueryForm.getValues();
				Ext.apply(operation, {
					//设置查询条件与后台实体对应
					params : {
					'queryPackedPriceVo.pdaQueryPackConditionEntity.waybillNo':queryParams.waybillNo,
					'queryPackedPriceVo.pdaQueryPackConditionEntity.packageOrgName':queryParams.pdaPackedDept,
					'queryPackedPriceVo.pdaQueryPackConditionEntity.packageOrgCode':queryParams.pdaPackDeptCode,
					'queryPackedPriceVo.pdaQueryPackConditionEntity.packageSupplierCode':queryParams.packageSupplierCode,
					'queryPackedPriceVo.pdaQueryPackConditionEntity.packedBeginDate':queryParams.packedBeginDate,
					'queryPackedPriceVo.pdaQueryPackConditionEntity.packedEndDate':queryParams.packedEndDate,
					'queryPackedPriceVo.pdaQueryPackConditionEntity.auditStatus':queryParams.auditStatus
				}
				});	
			},
			load: function( store, records, successful, eOpts ){
				if(successful){
					//统计列表信息
					if(records.length==0){
						//提示，未查询到符合条件的包装数据！
						Ext.ux.Toast.msg(packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.hint'), packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.packageGrid.packageStore.queryHint'), 'success', 5000);
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
 * PdaPackage列表
 */
Ext.define('Foss.queryPdapackPrice.PdaPackageGrid',{
	extend: 'Ext.grid.Panel',
	title : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.title'),// 'PDA端扫描生成包装金额查询结果',
	//id: 'Foss_QueryPdapackPrice_PdaPackageGrid_ID',
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
			text : 'id',// 'id',
			align: 'center',
			width : 80,
			hidden: true,
			dataIndex : 'id'
	},{
			xtype : 'ellipsiscolumn',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.waybillNo'),// '运单号',
			align: 'center',
			width : 80,
			dataIndex : 'waybillNo'
		},{
			xtype : 'datecolumn',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.createTime'),// '包装时间',
			align: 'center',
			width : 130,
			dataIndex : 'createTime',
			format : 'Y-m-d H:i:s'
		},{
			text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.billRrgName'),// '开单部门,
			align: 'center',
			width : 120,
			xtype: 'ellipsiscolumn',
			dataIndex: 'billOrgName'
		},{
            text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.theoryFrameVolume'),// '理论打木架体积",
            width : 80,
			align: 'center',
            dataIndex: 'theoryFrameVolume'
        },{
			text : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.theoryWoodenVolume'),// '理论打木箱体积,
			width : 80,
			align: 'center',
			xtype: 'ellipsiscolumn',
			dataIndex : 'theoryWoodenVolume'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.theoryMaskNumber'),// '理论打木托个数',
			width : 80,
			align: 'center',
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			dataIndex : 'theoryMaskNumber'
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.actualFrameVolume'),// '实际打木架体积",
			width : 80,
			align: 'center',		
			dataIndex: 'actualFrameVolume'
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.actualWoodenVolume'),// '实际打木箱体积",
			width : 80,
			align: 'center',
			defaultValue:0,
			dataIndex: 'actualWoodenVolume'
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.actualMaskNumber'),// '实际打木托个数",
			width : 80,
			defaultValue:0,
			allowBlank: false,
			allowDecimals:false,
			align: 'center',
			dataIndex: 'actualMaskNumber',
		},{
			xtype : 'ellipsiscolumn',
			text: packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.createUserName'),// '创建人",
			//xtype: 'numbercolumn',
			width : 120,
			align: 'center',
			//format:'0.00000',
			dataIndex: 'createUserName'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.packagePayableMoney'),// '应付金额',
			width : 80,
			align: 'center',
			dataIndex : 'packagePayableMoney'
		},{
			xtype : 'ellipsiscolumn',
			labelWidth:80,
			text : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.packageSupplierName'),// '包装供应商',
			width : 120,
			align: 'center',
			dataIndex : 'packageSupplierName'
		},{
			xtype : 'ellipsiscolumn',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus'),//'审核状态',
			width : 80,
			align: 'center',
			dataIndex : 'auditStatus',
			renderer:function(value){
			if(value=='WAITINGAUDIT'){//未审核
				 return packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.waitingaudit');
			}else if(value=='HASAUDITED'){ //已审核
				 return packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.hasudited');
			}
			else if(value=='BACKAUDIT'){//反审核
				 return packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.backaudit');
			}
			else {//失效
				 return packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.pdaPackageGrid.auditStatus.invalid');
			}
		}
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.QueryPdapackPrice.PackageStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		//导出按钮
		me.tbar = [{
			xtype : 'button',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.button.export'),//导出
			disabled:(packaging.queryPdapackPrice.isPermission('packaging/exportExcelPdaPackedButton'))?false:true,
			hidden:(packaging.queryPdapackPrice.isPermission('packaging/exportExcelPdaPackedButton'))?false:true,
			handler : function(){
				packaging.queryPdapackPrice.exportExcelPacked();
			}
		},'->',{
			xtype : 'button',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.button.audited'),//审核
			disabled:(packaging.queryPdapackPrice.isPermission('packaging/auditedPdaPackedButton'))?false:true,
			hidden:(packaging.queryPdapackPrice.isPermission('packaging/auditedPdaPackedButton'))?false:true,
			handler : function(){
				var selectModel = packaging.queryPdapackPrice.PdaPackageGird.getSelectionModel();
				var selectedRecord=selectModel.getSelection();
				if(selectedRecord.length==0){
					Ext.ux.Toast.msg("提示","请选择操作的数据",'error', 2000);
					return;
				}else {
					var row = selectedRecord[0];
					var records=[];
					for(i=0;i<selectedRecord.length;i++){
						records.push(selectedRecord[i].data.id);
					}
					var jsonData = {
						'queryPackedPriceVo':{
							'ids':records,
							'auditType':'AUDIT'
						}
					}
					Ext.Ajax.request({
						url:packaging.realPath('auditPacked.action'),
						//method : 'POST',
						jsonData : jsonData,
						exception : function(response,opts) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg("提示","审核失败"+result.message,'info', 2000);
							
						},
						success : function(response,opts) {	
							var pdaPackageStore=packaging.queryPdapackPrice.PdaPackageGird.getStore();
							pdaPackageStore.each(function(record){
								if(selectModel.isSelected(record)){
									record.set("auditStatus",'HASAUDITED');
								}
								
							});
							Ext.ux.Toast.msg("提示","审核成功！",'info', 2000);
						}		
					});
					
				}
			}
		},{
		  xtype : 'button',
			text : packaging.queryPdapackPrice.i18n('foss.packaging.button.backaudit'),//反审核
			disabled:(packaging.queryPdapackPrice.isPermission('packaging/backauditlPdaPackedButton'))?false:true,
			hidden:(packaging.queryPdapackPrice.isPermission('packaging/backauditlPdaPackedButton'))?false:true,
			handler : function(){
				var selectModel=packaging.queryPdapackPrice.PdaPackageGird.getSelectionModel();
				var selectedRecord=selectModel.getSelection();
				if(selectedRecord.length==0){
					Ext.ux.Toast.msg("提示","请选择操作的数据",'error', 2000);
					return;
				}else {
					var row = selectedRecord[0];
					var records=[];
					for(i=0;i<selectedRecord.length;i++){
						records.push(selectedRecord[i].data.id);
					}
					var jsonData = {
						'queryPackedPriceVo':{
							'ids':records,
							'auditType':'BACKAUDIT'
						}
					}
					Ext.Ajax.request({
						url:packaging.realPath('auditPacked.action'),
						//method : 'POST',
						jsonData : jsonData,
						exception : function(response,opts) {
							var result = Ext.decode(response.responseText);
							Ext.ux.Toast.msg("提示","反审核失败"+result.message,'info', 2000);
						},
						success : function(response,opts) {	
							var pdaPackageStore=packaging.queryPdapackPrice.PdaPackageGird.getStore();
							pdaPackageStore.each(function(record){
								if(selectModel.isSelected(record)){
									record.set("auditStatus",'WAITINGAUDIT');
								}
								
							});
							Ext.ux.Toast.msg("提示","反审核成功！",'info', 2000);
						}		
					});
					
				}
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
		packaging.queryPdapackPrice.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});


/**
 * 导出到excel
 */
packaging.queryPdapackPrice.exportExcelPacked = function(){
	//权限校验
		if(!packaging.queryPdapackPrice.isPermission('packaging/exportExcelPdaPackedButton')){
			return;
		}
		
		
		var actionUrl= packaging.realPath('exportPdaExcelPacked.action');	
		//执行查询，首先货物查询条件，packaging.packageQueryForm为全局变量，在查询条件的FORM创建时生成
		var queryParams = packaging.pdaPackageQueryForm.getValues();
		var params = {
				'queryPackedPriceVo.pdaQueryPackConditionEntity.waybillNo':queryParams.waybillNo,
				'queryPackedPriceVo.pdaQueryPackConditionEntity.packageOrgName':queryParams.pdaPackedDept,
				'queryPackedPriceVo.pdaQueryPackConditionEntity.packageOrgCode':queryParams.pdaPackDeptCode,
				'queryPackedPriceVo.pdaQueryPackConditionEntity.packageSupplierCode':queryParams.packageSupplierCode,
				'queryPackedPriceVo.pdaQueryPackConditionEntity.packedBeginDate':queryParams.packedBeginDate,
				'queryPackedPriceVo.pdaQueryPackConditionEntity.packedEndDate':queryParams.packedEndDate,
				'queryPackedPriceVo.pdaQueryPackConditionEntity.auditStatus':queryParams.auditStatus
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
packaging.queryPdapackPrice.PdaPackageGird=Ext.create('Foss.queryPdapackPrice.PdaPackageGrid');
packaging.queryPdapackPrice.PdaPackageQueryForm=Ext.create('Foss.QueryPacked.PdaPackageQueryForm');
Ext.onReady(function() {
	Ext.QuickTips.init();
	var pdaPackageQueryForm = packaging.queryPdapackPrice.PdaPackageQueryForm;
	packaging.pdaPackageQueryForm = pdaPackageQueryForm;
	//设置新增或修改状态作为全局变量
	Ext.apply(packaging.queryPdapackPrice,{
		editStatus : 'edit'
	});
	Ext.Ajax.request({
		//url:'../packaging/queryPackDept.action',
		url : packaging.realPath('queryPackDept.action'),
		//获取当前登录人的所在部门
		success:function(response){
			var result = Ext.decode(response.responseText);	
			Ext.apply(packaging.queryPdapackPrice,{
				pdaPackedDept : result.queryUnpackVo.currentDeptDto.deptName,
				pdaPackDeptCode : result.queryUnpackVo.currentDeptDto.deptCode
			});
			packaging.pdaPackageQueryForm.getForm().findField('pdaPackedDept').setValue(result.queryUnpackVo.currentDeptDto.deptName);
			packaging.pdaPackageQueryForm.getForm().findField('pdaPackDeptCode').setValue(result.queryUnpackVo.currentDeptDto.deptCode);
		},
		exception: function(response){
        	var result = Ext.decode(response.responseText);
            Ext.Msg.alert(packaging.queryPdapackPrice.i18n('foss.packaging.querypacked.error'), result.message);   
        }
	});
	
	Ext.create('Ext.panel.Panel',{	
		id:'T_packaging-queryPdapackPriceindex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body',
		layout:'auto',
		items : [
			packaging.pdaPackageQueryForm,
			packaging.queryPdapackPrice.PdaPackageGird
			
		],
		renderTo: 'T_packaging-queryPdapackPriceindex-body'
	});
});

