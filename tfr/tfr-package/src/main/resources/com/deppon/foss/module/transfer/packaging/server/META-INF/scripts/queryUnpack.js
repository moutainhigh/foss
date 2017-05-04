/**
 *  本js实现了查询营业部代打包装信息界面的展现
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
//=========================================
/**
 *  前端默认显示的代打包装部门model
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
/*Ext.define('Foss.QueryUnpack.PackDeptModel',{
	extend:'Ext.data.Model',
	fields:[  			 
{name:'packDept'}]
});

*//**
 *  前端默认显示的代打包装部门store
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 *//*
Ext.define('Foss.QueryUnpack.PackDeptStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.QueryUnpack.PackDeptModel',
	proxy: {
	       type: 'ajax',
	       //后台路径
	       url:'../packaging/queryPackDapt.action',
	       reader: {
	           type: 'json',
	           //返回参数
	           root: 'queryUnpackVo.packDept'
	       }
	 },
	 autoLoad: true
	   
});  */ 

/**
 *  前端默认显示货物状态model
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.GoodsStatusModel',{
	extend:'Ext.data.Model',
	fields:[  			 
{name:'valueName'},
{name:'valueCode'}]
});

/**
 *  前端默认显示货物状态store
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.GoodsStatusStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.QueryUnpack.GoodsStatusModel',
	proxy: {
	       type: 'ajax',
	      // url : '../packaging/queryGoodsStatus.action',
	       url : packaging.realPath('queryGoodsStatus.action'),
	       //actionMethods:'post',
	       reader: {
	           type: 'json',
	           root: 'queryUnpackVo.goodsStatusList'
	       }
	 },
	 
	 autoLoad: true
});   
//货物状态combo
/*var goodsStatusCombo = FossDataDictionary.getDataDictionaryCombo('GOODS_STATUS',{
	name: 'goodsStatus',
	fieldLabel: '货物状态',
	queryMode: 'local',
	forceSelection: true,
	editable: true,
	allowBlank: false,
	columnWidth:.25
	});
*/
/**
 *  前端查询条件form
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.QueryUnpackForm',{
	extend: 'Ext.form.Panel',
	title: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.title'),//'查询营业部代打包装信息'
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
		//DPAP自定义日期控件
		xtype: 'rangeDateField',
		dateType: 'datetimefield_date97',
		fromName: 'waybillBeginDate',
		//设置开始时间默认值
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
				,00,00,00), 'Y-m-d H:i:s'),
		//allowFromBlank:false,
		toName: 'waybillEndDate',
		//设置结束时间默认值
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59), 'Y-m-d H:i:s'),
		//allowToBlank:false,
		disallowBlank:true,
		fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.waybillBeginDate'),//'开单时间',
		fieldId: 'Foss_queryUnpack_QueryUnpackForm_WayBill_Id',
		//allowBlank: false,
		columnWidth: .4
	},{
		name: 'goodsStatus',
		id: 'Foss_queryUnpack_QueryUnpackForm_goodsStatus_ID',
		fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.goodsStatus'),//'货物状态',
		//下拉框控件
		xtype: 'combo',
		//采用远程获取的方式
		store : FossDataDictionary.getDataDictionaryStore('TFR_GOODS_STATUS'),
		queryMode: 'local',
		value: 'ALL',
		valueField:'valueCode',
		displayField: 'valueName',
		editable : false,
		allowBlank: false,
		columnWidth:.25
	},
	{
		name: 'waybillNo',
		fieldLabel:'运单号',
		columnWidth:.25
	},{
		name: 'packDept',
		id: 'Foss_queryUnpack_QueryUnpackForm_packDept_ID',
		labelWidth:80,
		fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.packDept'),//'代包装部门',
		//value: '上海事业部',
		//store : Ext.create('Foss.QueryUnpack.PackDeptStore'),
		allowBlank: false,
		readOnly:true,
		columnWidth:.25
	},{//代包装部门编码
		name: 'packDeptCode',
		id: 'Foss_queryUnpack_QueryUnpackForm_packDeptCode_ID',
		hidden:true,
		columnWidth:.25
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
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.resetButton'),//'重置',
			handler: function() {
				var form = this.up('form').getForm();
				form.findField('waybillBeginDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1
						,00,00,00), 'Y-m-d H:i:s'));
				form.findField('waybillEndDate').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,23,59,59), 'Y-m-d H:i:s'));
				form.findField('packDept').setValue(packaging.queryunpack.packedDept);
				form.findField('goodsStatus').setValue(packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.all'));//'全部'
				//Ext.getCmp('Foss_queryUnpack_QueryUnpackForm_packDept_ID').setValue('上海事业部');
				//Ext.getCmp('Foss_queryUnpack_QueryUnpackForm_WayBill_Id').getFromValue.setValue(new Date());
				form.findField('waybillNo').setValue(packaging.queryunpack.waybillNo);
				
			}	
		},{
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			columnWidth:.08,
			xtype : 'button',
			cls:'yellow_button',
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.queryButton'),//'查询',
			disabled:(packaging.queryunpack.isPermission('packaging/queryUnpackAllButton'))?false:true,
			hidden:(packaging.queryunpack.isPermission('packaging/queryUnpackAllButton'))?false:true,
			handler: function() {
				
				if(!packaging.queryunpack.isPermission('packaging/queryUnpackAllButton')){
					return;
				}
				
				//查询条件是否合法（非空等相关约束）
				var beginDate = packaging.queryUnpackForm.getValues().waybillBeginDate;
				var endDate = packaging.queryUnpackForm.getValues().waybillEndDate;
				var difDate = Ext.Date.parse(endDate,'Y-m-d H:i:s') - Ext.Date.parse(beginDate,'Y-m-d H:i:s');
				var difTime = 7*24*60*60*1000-parseInt(difDate);
				if(packaging.queryUnpackForm.getForm().isValid()){
					if(difTime>0){
						//自定义分页
						//packaging.queryunpack.pagingBar.moveFirst();
						packaging.queryUnpackGrid.store.load();
					}else{
						//警告，时间跨度不能超过7天!
						Ext.Msg.alert(packaging.queryunpack.i18n('foss.packaging.warning'), packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.timeWarning'));
						//Ext.ux.Toast.msg('提示', '时间跨度不能超过7天!', 'error', 5000);
					}
					
				}else{
					//警告，请输入查询条件！
					Ext.Msg.alert(packaging.queryunpack.i18n('foss.packaging.warning'), packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackForm.queryWarning'));
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
/**
 *  列表下面的统计信息
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
packaging.queryunpack.sumUnpack = function(records){
	//开单件数
	var serialNum = 0;
	//票数
	var billNum = 0;
	//需要包装体积
	var needPackVolume = 0;
	//需要包装件数
	var needPackNum = 0;
	packaging.queryUnpackGrid.getStore().each(function(record){
		billNum = billNum + parseInt('1');
		if(record.get("waybillNum")!=null&&record.get("waybillNum")!=""){
			serialNum = serialNum + parseInt(record.get("waybillNum"));
		}
		if(record.get("needPackVolume")!=null&&record.get("needPackVolume")!=""){
			needPackVolume = needPackVolume + parseFloat(record.get("needPackVolume"));
		}
		if(record.get("needPackNum")!=null&&record.get("needPackNum")!=""){
			needPackNum = needPackNum + parseInt(record.get("needPackNum"));
		}	
	});
	needPackVolume = Math.round(needPackVolume*Math.pow(10,2))/Math.pow(10,2);
	Ext.getCmp('Foss_QueryUnpack_QueryUnpackGrid_totalWaybillNum_ID').setValue(billNum);
	Ext.getCmp('Foss_QueryUnpack_QueryUnpackGrid_totalSerialNum_ID').setValue(serialNum);
	Ext.getCmp('Foss_QueryUnpack_QueryUnpackGrid_totalPackVolume_ID').setValue(needPackVolume);
	Ext.getCmp('Foss_QueryUnpack_QueryUnpackGrid_totalPackNumi_ID').setValue(needPackNum);
	

};
/**
 *  导出到excel
 * @author 046130-foss-xuduowei
 * @date 2013-03-23 下午4:07:46
 */
packaging.queryunpack.exportExcelUnpack = function(){
	
	if(!packaging.queryunpack.isPermission('packaging/exportUnpackExcelButton')){
		return;
	}
	
	var actionUrl= packaging.realPath('exportUnpackExcel.action');	
	//执行查询，首先货物查询条件，packaging.queryUnpackForm为全局变量，在查询条件的FORM创建时生成
	var queryParams = packaging.queryUnpackForm.getValues();
	var params = {
				'queryUnpackVo.queryUnpackConditionEntity.waybillBeginDate':queryParams.waybillBeginDate,
				'queryUnpackVo.queryUnpackConditionEntity.waybillEndDate':queryParams.waybillEndDate,
				'queryUnpackVo.queryUnpackConditionEntity.goodsStatus':queryParams.goodsStatus,
				'queryUnpackVo.queryUnpackConditionEntity.packDept':queryParams.packDeptCode
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
			var result = Ext.decode(response.responseText);
			//错误
            Ext.Msg.alert(packaging.queryunpack.i18n('foss.packaging.error'), result.message);  
		},
		success : function(response,opts) {	
		}		
	});

	
};
/**
 *  营业部代打包装查询结果列表model
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.QueryUnpackModel', {
	extend : 'Ext.data.Model',
	idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',//以上生成的主键用在名叫“extid”的列
	fields : [{
		name: ' extid ', type: 'string'
	},{//额外的用于生成的EXT使用的列{
		//运单号
		name : 'wayBillNumber',
		type : 'string'
	}, {
		//开单件数
		name : 'waybillNum',
		type : 'string'
	}, {
		//开单体积
		name : 'waybillVolume',
		type : 'string'
	}, {
		//需要包装体积
		name : 'needPackVolume',
		type : 'string'
	}, {
		//代包装货区入库件数
		name : 'packStockNum',
		type : 'string'
	}, {
		//需要包装件数
		name : 'needPackNum',
		type : 'string'
	}, {
		//已包装件数
		name : 'packedNum',
		type : 'string'
	}, {
		//货物状态
		name : 'goodsStatus',
		type : 'string'
	}, {
		//运输列席
		name : 'transportationType',
		type : 'string'
	}, {
		//货物名称
		name : 'goodsName',
		type : 'string'
	},{
		//开单时间
		name : 'waybillCreateDate',
		type : 'string'
	},{
		//开单部门
		name : 'waybillCreateDept',
		type : 'string'
	},{
		//目的站
		name : 'customerPickupOrgName',
		type : 'string'
	},{
		//开单部门编码
		name : 'waybillCreateDeptCode',
		type : 'string'
	},{
		//代包装部门编码
		name : 'packDept',
		type : 'string'
	}, {
		//预计到达时间
		name : 'predictArriveDate',
		type : 'string'
	}, {
		//预计出发时间
		name : 'predictDepartDate',
		type : 'string'
	}, {
		//代包装要求
		name : 'packRequire',
		type : 'string'
	}]
});

/**
 *  营业部代打包装查询结果列表store
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.QueryUnpackStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.QueryUnpack.QueryUnpackModel',
	//默认每页数据大小
	pageSize:20,
	//是否自动查询
	autoLoad: false,
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		//提交方式
		actionMethods:'POST',
		//路径
		//url:'../packaging/queryUnpackAll.action',
		url : packaging.realPath('queryUnpackAll.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'queryUnpackVo.queryUnpackResultList',
			//返回总数
			totalProperty : 'totalCount',
			successProperty: 'success'
		},
		listeners:{
			exception:function(reader,response,eopts){
				var result = Ext.decode(response.responseText);
				//错误
	            Ext.Msg.alert(packaging.queryunpack.i18n('foss.packaging.error'), result.message);   
			}
		}
	},
	//事件监听
	listeners: {
	//查询事件
		beforeload : function(store, operation, eOpts) {
			//执行查询，首先货物查询条件，packaging.queryUnpackForm为全局变量，在查询条件的FORM创建时生成
			var queryParams = packaging.queryUnpackForm.getValues();
			Ext.apply(operation, {
				params : {
						'queryUnpackVo.queryUnpackConditionEntity.waybillBeginDate':queryParams.waybillBeginDate,
						'queryUnpackVo.queryUnpackConditionEntity.waybillEndDate':queryParams.waybillEndDate,
						'queryUnpackVo.queryUnpackConditionEntity.goodsStatus':queryParams.goodsStatus,
						'queryUnpackVo.queryUnpackConditionEntity.packDept':queryParams.packDeptCode,
						'queryUnpackVo.queryUnpackConditionEntity.waybillNo':queryParams.waybillNo							
					}
			});	
		},
		load: function( store, records, successful, eOpts ){
			//统计列表信息
			if(successful){
				if(records.length==0){
					//提示，未查询到符合条件的代包装数据！
					Ext.ux.Toast.msg(packaging.queryunpack.i18n('foss.packaging.hint'), packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackStore.queryHint'), 'success', 5000);
				}
				packaging.queryunpack.sumUnpack(records);
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
 *  展开列表时panel的左侧代打包装信息
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.UnpackDetailsForm', {
    extend: 'Ext.form.Panel',
	defaults : {
		margin:'5 5 5 5',
		anchor: '100%',
		labelWidth:100
	},
	flex: 1,
	frame: true,
	defaultType : 'textfield',
	layout: 'column',
	items : [{
			name: 'wayBillNumber',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.wayBillNumber'),//'运单号',
			columnWidth:.5
		},{
			name: 'waybillCreateDept',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.waybillCreateDept'),//'开单部门',
			columnWidth:.5
		},{
			name: 'goodsName',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.goodsName'),//'货物名称',
			columnWidth:.5
		},{
			name: 'needPackVolume',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.needPackVolume'),//'需要包装体积(方)',
	        xtype:'numberfield',
	        labelWidth:120,
			columnWidth:.5
		},{
			name: 'transportationType',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.transportationType'),//'运输类型',
			columnWidth:.5
		},{
			name: 'packRequire',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.packRequire'),//'代包装要求',
			columnWidth:.5
		},{
			name: 'waybillCreateDate',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.waybillCreateDate'),//'开单时间',
			columnWidth:.5
		},{
			name: 'predictArriveDate',
	        fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.predictArriveDate'),//'预计到达时间',
			columnWidth:.5
		},{
			name: 'predictDepartDate',
			fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsForm.predictDepartDate'),//'预计发车时间',
			columnWidth:.5
		}]
});


/**
 *  展开列表时panel的右侧的列表信息的model
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.UnpackDetailsModel', {
	extend : 'Ext.data.Model',
	fields : [{
		//流水号
		name : 'serialNum',
		type : 'string'
	}, {
		//是否需要包装
		name : 'isNeedPack',
		type : 'string'
	}, {
		//是否已打木架
		name : 'isPacked',
		type : 'string'
	}, {
		//是否已打木托
		name : 'isWoodCare',
		type : 'string'
	}, {
		//货物状态
		name : 'goodsStatus',
		type : 'string'
	}, {
		//是否代打木架区库存
		name : 'isStockInYoke',
		type : 'string'
	}]
});

/**
 *  展开列表时panel的右侧的列表信息的store
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.UnpackDetailsStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.QueryUnpack.UnpackDetailsModel'
	
});

/**
 *  展开列表时panel的右侧的列表信息的store
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.UnpackDetailsGrid',{
	extend: 'Ext.grid.Panel',
	title : packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsGrid.title'),//'代包装明细',
	//border: true,
	frame: true,
	flex: 0.9,
	store: null,
    //id:'unpackDeailsGrid',
    //增加滚动条
    autoScroll:false,
	hideHeaders: false,
	stripeRows : true, // 交替行效果
	columns : [{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsGrid.serialNum'),//'件数流水号',
			align: 'center',
			flex: 1,
			dataIndex : 'serialNum'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsGrid.isNeedPack'),//'是否需要<br/>代包装',
			align: 'center',
			flex: 1,
			dataIndex : 'isNeedPack'
		},{
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsGrid.isPacked'),//'是否已打木架',
			align: 'center',
			flex: 1,
			dataIndex: 'isPacked'
		},{
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsGrid.isWoodCare'),//'是否已打木托',
			align: 'center',
			flex: 1,
			dataIndex: 'isWoodCare'
		},{
            text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsGrid.goodsStatus'),//'货物状态',
			flex: 1,
			align: 'center',
            dataIndex: 'goodsStatus'
        },{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailsGrid.isStockInYoke'),//'是否木架<br/>区库存',
			flex: 1,
			dataIndex : 'isStockInYoke'
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.QueryUnpack.UnpackDetailsStore');
		me.callParent([cfg]);
	}
});


/**
 *  将代包装明细和明细流水号整合的panel
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.UnpackDetailInfo.Panel', {
	 	extend: 'Ext.panel.Panel',
	 	title : packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailInfo.title'),//'详细信息',
	 	layout: {
	        type: 'hbox',
	        align: 'stretch'
	    },
		//layout: 'auto',
		autoScroll:false,
		frame : true,
		//创建代包装明细信息界面
		unpackDetailsForm: null,
		getUnpackDetailsForm: function(){
			if(this.unpackDetailsForm==null){
				this.unpackDetailsForm = Ext.create('Foss.QueryUnpack.UnpackDetailsForm');
			}
			return this.unpackDetailsForm;
		},
		//创建代包装明细流水号列表
		unpackDetailsGrid: null,
		getUnpackDetailsGrid: function(){
			if(this.unpackDetailsGrid==null){
				this.unpackDetailsGrid = Ext.create('Foss.QueryUnpack.UnpackDetailsGrid');
			}
			return this.unpackDetailsGrid;
		},
			
		
		//绑定表格数据到表单
		bindUnpackData: null,
		getBindUnpackData: function(unPackDetailsGrid){
			//var unpackRecord = grid.getStore().getAt(1);
			var unpackRecord = packaging.queryUnpackGrid.getSelectionModel().getSelection()[0];
			this.getUnpackDetailsForm().getForm().reset();
			this.getUnpackDetailsForm().getForm().loadRecord(unpackRecord);
			var waybillno = unpackRecord.get('wayBillNumber');
			var waybillCreateDeptCode = unpackRecord.get('waybillCreateDeptCode');
			var packDept = unpackRecord.get('packDept');
			var grid = unPackDetailsGrid;
			//列表信息要从后台取
			Ext.Ajax.request({
				//url:'../packaging/queryUnpackDetails.action',
				url : packaging.realPath('queryUnpackDetails.action'),
				//获取当前行的运单号
				params : {
					'queryUnpackVo.queryUnpackConditionEntity.waybillNo': waybillno,
					'queryUnpackVo.queryUnpackConditionEntity.waybillCreateDeptCode': waybillCreateDeptCode,
					'queryUnpackVo.queryUnpackConditionEntity.packDept': packDept
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					if(!result.queryUnpackVo.queryUnpackDetailsList || result.queryUnpackVo.queryUnpackDetailsList.length == 0) {
						//提示，未查询到流水号数据！
						Ext.ux.Toast.msg(packaging.queryunpack.i18n('foss.packaging.hint'),packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailInfo.queryHint'), 'success', 5000);
						return;
					}
					grid.store.loadData(result.queryUnpackVo.queryUnpackDetailsList);
				},
				exception: function(response){
		        	var result = Ext.decode(response.responseText);
		        	//错误
		            Ext.Msg.alert(packaging.queryunpack.i18n('foss.packaging.error'), result.message);   
		        }
			})
			
		},	
		bindData : function(record,grid,rowBodyElement){

			
			//var unpackRecord = packaging.queryUnpackGrid.getSelectionModel().getSelection()[0];
			this.getUnpackDetailsForm().getForm().reset();
			this.getUnpackDetailsForm().getForm().loadRecord(record);
			var waybillno = record.get('wayBillNumber');
			var waybillCreateDeptCode = record.get('waybillCreateDeptCode');
			var packDept = record.get('packDept');
			var grid = this.getUnpackDetailsGrid();
			//列表信息要从后台取
			Ext.Ajax.request({
				//url:'../packaging/queryUnpackDetails.action',
				url : packaging.realPath('queryUnpackDetails.action'),
				//获取当前行的运单号
				params : {
					'queryUnpackVo.queryUnpackConditionEntity.waybillNo': waybillno,
					'queryUnpackVo.queryUnpackConditionEntity.waybillCreateDeptCode': waybillCreateDeptCode,
					'queryUnpackVo.queryUnpackConditionEntity.packDept': packDept
				},
				success:function(response){
					var result = Ext.decode(response.responseText);
					if(!result.queryUnpackVo.queryUnpackDetailsList || result.queryUnpackVo.queryUnpackDetailsList.length == 0) {
						//提示，未查询到流水号数据
						Ext.ux.Toast.msg(packaging.queryunpack.i18n('foss.packaging.hint'), packaging.queryunpack.i18n('foss.packaging.queryUnpack.unpackDetailInfo.queryHint'), 'success', 5000);
						return;
					}
					grid.store.loadData(result.queryUnpackVo.queryUnpackDetailsList);
				},
				exception: function(response){
		        	var result = Ext.decode(response.responseText);
		            Ext.Msg.alert(packaging.queryunpack.i18n('foss.packaging.error'), result.message);   
		        }
			})
			
		
		},
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			var unPackDetailsGrid = this.getUnpackDetailsGrid();
			this.items = [
				this.getUnpackDetailsForm(),/*{
					border : false,
					xtype : 'container',
					margin : '0 10 0 10',
					cls: 'dp-space-body'
				},*/
				unPackDetailsGrid
			];
			//绑定列表数据到查看明细的form上
			//this.getBindUnpackData(unPackDetailsGrid);
			setFormEditAble(this.getUnpackDetailsForm(),false);
			//设置表单不可编辑
			var formEdit = function(){
				var fields = this.getUnpackDetailsForm().getForm().getFields();
				fields.each(function(field){
					field.setReadOnly(true);
				});
			};
			me.callParent([cfg]);
		}
		
});

/**
 *  界面下方的主列表信息即查询营业部代打包装列表信息
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.define('Foss.QueryUnpack.QueryUnpackGrid',{
	extend: 'Ext.grid.Panel',
	title : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.title'),//'代包装信息查询结果',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame: true,
    //增加滚动条
    autoScroll : false,
	stripeRows : false, // 交替行效果
	collapsible: true,
    animCollapse: true,
	selType : 'rowmodel', // 选择类型设置为：行选择
	//store : null,
	selModel : Ext.create('Ext.selection.RowModel'),
	//表格行可展开的插件
	plugins: [{
		header : true,
		ptype: 'rowexpander',
		//定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander: false,
		//行体内容
		rowBodyElement : 'Foss.UnpackDetailInfo.Panel'
	}],
	
	
	columns : [/*{
        	xtype:'actioncolumn',
        	flex: 0.1,
        	text: '操作',
        	align: 'center',
        	items: [{
        		iconCls:'deppon_icons_unfold',
        		tooltip: '展开',
        		handler: function(grid, rowIndex, colIndex) {
        			grid.fireEvent('itemdblclick', grid, rowIndex, rowIndex, rowIndex);
        		}
        	}]
    	},*/{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.wayBillNumber'),//'运单号',
			align: 'center',
			flex: 0.8,
			//xtype: 'textfield',
			dataIndex : 'wayBillNumber'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.waybillNum'),//'开单<br/>件数',
			align: 'center',
			flex: 0.5,
			//xtype: 'numbercolumn',
			dataIndex : 'waybillNum'
		},{
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.waybillVolume'),//'开单体积<br/>(方)',
			align: 'center',
			flex: 1,
			xtype: 'numbercolumn',
			dataIndex: 'waybillVolume'
		},{
            text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.needPackVolume'),//'需要包装<br/>体积(方)',
			flex: 1,
			align: 'center',
			xtype: 'numbercolumn',
            dataIndex: 'needPackVolume'
        },{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.packStockNum'),//'代包装货<br/>区入库件数',
			flex: 1,
			align: 'center',
			//xtype: 'numbercolumn',
			dataIndex : 'packStockNum'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.needPackNum'),//'需要包<br/>装件数',
			flex: 0.8,
			align: 'center',
			//xtype: 'numbercolumn',
			dataIndex : 'needPackNum'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.packRequire'),//'代包装要求',
			flex: 0.8,
			align: 'center',
			hidden: false,
			dataIndex : 'packRequire'
		},{
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.packedNum'),//'已包装<br/>件数',
			flex: 0.8,
			align: 'center',
			//xtype: 'numbercolumn',
			dataIndex: 'packedNum'
		},{
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.goodsStatus'),//'货物<br/>状态',
			flex: 0.5,
			align: 'center',
			dataIndex: 'goodsStatus',
			renderer: function(value){
				if(value != null){
					var goodsStatus = FossDataDictionary.rendererSubmitToDisplay (value,'TFR_GOODS_STATUS_QUERY');
					return goodsStatus;
				}
			}
		},{
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.transportationType'),//'运输<br/>类型',
			flex: 0.5,
			align: 'center',
			dataIndex: 'transportationType'
		},{
			text: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.goodsName'),//'货物<br/>名称',
			flex: 0.5,
			align: 'center',
			dataIndex: 'goodsName'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.waybillCreateDept'),//'开单<br/>部门',
			flex: 0.5,
			align: 'center',
			dataIndex : 'waybillCreateDept'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.customerPickupOrgName'),//'目的站',
			flex: 0.5,
			align: 'center',
			dataIndex : 'customerPickupOrgName'
		},{
			//开单部门编码，用于查询明细使用
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.waybillCreateDeptCode'),//'开单部门编码',
			flex: 0.5,
			hidden: true,
			align: 'center',
			dataIndex : 'waybillCreateDeptCode'
		},{
			//代包装部门编码，用于查询明细使用
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.packDept'),//'代包装部门编码',
			flex: 0.5,
			hidden: true,
			align: 'center',
			dataIndex : 'packDept'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.waybillCreateDate'),//'货物开<br/>单时间',
			flex: 0.8,
			align: 'center',
			dataIndex : 'waybillCreateDate'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.predictArriveDate'),//'预计到<br/>达时间',
			flex: 0.8,
			align: 'center',
			dataIndex : 'predictArriveDate'
		},{
			text : packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.predictDepartDate'),//'预计发<br/>车时间',
			flex: 0.8,
			align: 'center',
			dataIndex : 'predictDepartDate'
		}],	
		viewConfig:{
			//显示重复样式，不用隔行显示
		 	stripeRows: false,

				getRowClass:function(record, rowIndex, p, ds){
					if(record.get('predictDepartDate')!=null){
						var predictDate = record.get('predictDepartDate');
						var difDate = Ext.Date.parse(Ext.Date.format(predictDate,'Y-m-d H:i:s').toString(),'Y-m-d H:i:s') - 
							Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s').toString(),'Y-m-d H:i:s');
						//若预计发车时间减去当前时间小于1小时，标记为浅黄色
						if(parseInt(difDate)<60*60*1000) {
							return  'queryunpack_row_color';
						} 
					}
				}
	    },
	    dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'bottom',
		        defaults: {
		        	margin:'0 0 0 0',
		    		xtype: 'textfield',
		    		readOnly:true,
		    		anchor: '100%',
		    		labelWidth:100
		    	},
		        items: [
		                //总票数
		                {name: 'totalWaybillNum',id : 'Foss_QueryUnpack_QueryUnpackGrid_totalWaybillNum_ID',labelWidth:60,fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.dockedItems.totalWaybillNum'),flex: 1.2},
		                //需要包装总体积
		                {name: 'totalPackVolume',id : 'Foss_QueryUnpack_QueryUnpackGrid_totalPackVolume_ID',fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.dockedItems.totalPackVolume'),flex: 1.8},
		                //开单总件数
		                {name: 'totalSerialNum',id : 'Foss_QueryUnpack_QueryUnpackGrid_totalSerialNum_ID',labelWidth:80,fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.dockedItems.totalSerialNum'),flex: 1.5},
		                //需要包装总件数
		                {name: 'totalPackNum',id : 'Foss_QueryUnpack_QueryUnpackGrid_totalPackNumi_ID',fieldLabel: packaging.queryunpack.i18n('foss.packaging.queryUnpack.queryUnpackGrid.dockedItems.totalPackNum'),flex: 6}
		                ]
		    }],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.QueryUnpack.QueryUnpackStore');
		me.tbar = [{
			xtype : 'button',
			text : packaging.queryunpack.i18n('foss.packaging.button.export'),//导出
			disabled:(packaging.queryunpack.isPermission('packaging/exportUnpackExcelButton'))?false:true,
			hidden:(packaging.queryunpack.isPermission('packaging/exportUnpackExcelButton'))?false:true,
			handler : function(){
				packaging.queryunpack.exportExcelUnpack();
			}
		}],

	/*	me.bbar = Ext.create('Ext.PagingToolbar', {
			store: me.store,
			displayInfo: true,
			displayMsg: '当前显示从{0}到 {1}条记录，共 {2}条',
			emptyMsg: "无记录"
		});
		*/
		//自定义分页控件
		//2013-04-02，徐多为，取消分页
		/*me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		packaging.queryunpack.pagingBar = me.bbar;*/
		me.callParent([cfg]);
	}
});

/**
 * 初始化界面
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午4:07:46
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryUnpackForm = Ext.create('Foss.QueryUnpack.QueryUnpackForm');
	packaging.queryUnpackForm = queryUnpackForm;
	var queryUnpackGrid = Ext.create('Foss.QueryUnpack.QueryUnpackGrid');
	packaging.queryUnpackGrid = queryUnpackGrid;
	Ext.Ajax.request({
		//url:'../packaging/queryPackDept.action',
		url : packaging.realPath('queryPackDept.action'),
		//获取当前行的运单号
		//params:{'queryUnpackVo.waybillno': waybillno},
		//params:{'queryUnpackVo.queryUnpackConditionEntity.waybillBeginDate': waybillNO},
		success:function(response){
			var result = Ext.decode(response.responseText);	
			Ext.apply(packaging.queryunpack,{
				packedDept : result.queryUnpackVo.currentDeptDto.deptName,
				packedDeptCode : result.queryUnpackVo.currentDeptDto.deptCode
			});
			Ext.getCmp('Foss_queryUnpack_QueryUnpackForm_packDept_ID').setValue(result.queryUnpackVo.currentDeptDto.deptName);
			Ext.getCmp('Foss_queryUnpack_QueryUnpackForm_packDeptCode_ID').setValue(result.queryUnpackVo.currentDeptDto.deptCode);
		},
		exception: function(response){
        	var result = Ext.decode(response.responseText);
        	//错误
            Ext.Msg.alert(packaging.queryunpack.i18n('foss.packaging.error'), result.message);   
        }
	});
	//初始化页面
	Ext.create('Ext.panel.Panel',{
		id:'T_packaging-queryunpackindex_content',
		//必须添加，内容定位用
		cls:'panelContentNToolbar',
		//必须添加，内容定位用
		bodyCls:'panelContentNToolbar-body',
		//自动布局
		layout:'auto',
		items : [
		         queryUnpackForm,
		         queryUnpackGrid
		],
		renderTo: 'T_packaging-queryunpackindex-body'			
	});
});
