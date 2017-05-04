var short_orgCode;//部门编码
var short_departmentSignle;//部门标识

//选中的数据
var short_chooseDataListStowageBill= new Array();
var isRepeateMark_st='N';//是否多次标记
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


Ext.define('Foss.scheduling.queryPdaLoadException.status',
	{
		extend : 'Ext.data.Store',
		fields : ['value', 'name'],
		data : [{
				     value : "ALL",
				     name : '全部'
				}, {
					 value : "CREATE",
					 name : "创建"
				}, {
					value : "SUBMIT",
					 name : "提交"
				}]
	});

//查询条件form
Ext.define('Foss.scheduling.shortTemprentalmark.shortQueryForm', {
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
	items : [{
		xtype : 'dynamicorgcombselector',
		allowBlank : true,
		autoWidth : true,
		name: 'useCarDepartment',
	    fieldId : 'useCarDepartment',
	    fieldLabel: '用车部门',
		columnWidth:.25,
		hidden: scheduling.shortTemprentalmark.departmentSignle.split('_')[0] == 'SalesDepartment'
	 	},{
			xtype : 'textfield',
			allowBlank : true,
			autoWidth : true,
			name: 'useCarDepartment1',
		    fieldId : 'useCarDepartment1',
		    fieldLabel: '用车部门',
			columnWidth:.25,
			readOnly: true,
			value: scheduling.shortTemprentalmark.useCarDepartment,
			hidden: scheduling.shortTemprentalmark.departmentSignle.split('_')[0] != 'SalesDepartment'
		 }, {
			 xtype : 'rangeDateField',
			 dateType : 'datetimefield_date97',
			 fieldLabel:'用车时间',
			 fieldId : 'xx'+new Date().getTime(),
			 fromName:'usecarDateBeginTime',
			 toName: 'usecarDateEndTime',
			 fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),
				new Date().getDate()-4, 00, 00, 00), 'Y-m-d H:i:s'),
			 toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
				new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
			 dateRange:5,
			 editable: false,
			 allowBlank:false,
			 columnWidth: .45
	 }, {
		name : 'inviteVehicleNo',
		//id:'short_inviteVehicleNo',
		fieldLabel : "约车编号", // 约车编号
		columnWidth : .25
	 }, {
		 xtype:'commonleasedvehicleselector',
		 name: 'vehicleNo',
		// id:'short_vehicleNo',
		 fieldLabel: "车牌号",   //车牌号
		 columnWidth: .25
	 }, {
	//	id:'short_vehicleState',
		name : 'vehicleState',
		xtype : 'combo',
		store : Ext.create('Foss.scheduling.shortTemprentalmark.status'),
		queryMode : 'local',
		displayField : 'name',
		valueField : 'value',
		editable : false,
		fieldLabel : "车辆状态", // 车辆状态
		columnWidth : .25
	 }, {
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
				form.findField('usecarDateBeginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),
					new Date().getDate()-4, 00, 00, 00), 'Y-m-d H:i:s'));
				form.findField('usecarDateEndTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
					new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'));
			}
	  }, {
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.8
		},{
			text : '查询', // 查询
			disabled : !scheduling.shortTemprentalmark
					.isPermission('scheduling/shortTemprentalmarkButton'),
			hidden : !scheduling.shortTemprentalmark
					.isPermission('scheduling/shortTemprentalmarkButton'),
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = scheduling.shortTemprentalmark.shortTemprentalmarkQueryForm
						.getForm();
				if (!form.isValid()) {
					Ext.Msg.alert('标题',
							'请输入合法的查询条件'); // 请输入合法的查询条件!
					return false;
				}
				scheduling.shortTemprentalmark.pagingBar.moveFirst();
				shortTemprentalTabGridStore.removeAll();
			}
		}]
	}]
});


//model
Ext.define('Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryModel', {
	extend : 'Ext.data.Model',
	fields : [{
				// 约车编号
				name : 'inviteVehicleNo',
				type : 'string'
	        }, {
				// 主键id
				name : 'id',
				type : 'string'
			}, {
				// 金额
				name : 'rentalAmount',
				type : 'String'
			}, {
				// 车牌号 
				name : 'vehicleNo',
				type : 'string'
			},{
				name : 'vehicleState',//车辆状态
				type : 'string'
			},{
				// 用车时间
				name : 'useCareDate',
				type : 'date',
				convert: dateConvert
			},{
				//目的站
				name : 'destination',
				type : 'string'
			},{
				// 出发部门
				name : 'origOrgName',
				type : 'string'
			},{
				// 租车编号
				name : 'temprentalMarkNo',
				type : 'string'
			},{
				// 车辆任务ID
				name : 'truckTaskId',
				type : 'string'
			},{
				// 出发部门编码
				name : 'origOrgCode',
				type : 'string'
			},{
				// 到达部门编码
				name : 'destOrgCode',
				type : 'string'
			}]
});

//租车信息model
Ext.define('Foss.shortTemprentalmark.tempmarkModel',{
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'vehicleNo',//车牌号
		type : 'string'
	},{
		name : 'rentalAmount',//租车金额
		type : 'number'
	},{
		name : 'kmsNum',//公里数
		type : 'String'
	},{
		name : 'departureName',//出发部门
		type : 'string'
	},{
		name : 'departureCode',//出发部门编码
		type : 'string'
	},{
		name : 'destinationCode',//出发部门编码
		type : 'string'
	},{
		name : 'destinationName',//到达部门
		type : 'string'
	}]
});

// 查询store
Ext.define('Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型 
	model : 'Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryModel',
	proxy : {
		// 以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		timeout:300000,
		url : scheduling.realPath('shortTemprentalmarkQuery.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'shortRentalMarkVo.shortRentalMarkEntityList',
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
			
			var useCarDepartmentPam;
			if(scheduling.shortTemprentalmark.departmentSignle!=null){
				short_orgCode = scheduling.shortTemprentalmark.departmentSignle.split('_')[1];
				short_departmentSignle = scheduling.shortTemprentalmark.departmentSignle.split('_')[0];
			}
			
			var queryParams = scheduling.shortTemprentalmark.shortTemprentalmarkQueryForm.getValues();
			
			if (short_departmentSignle == 'SalesDepartment') {
				useCarDepartmentPam = short_orgCode;
			} else {
				useCarDepartmentPam = queryParams.useCarDepartment;
			}
			
			Ext.apply(operation, {
				params : {
					'shortRentalMarkVo.shortRentalMarkDto.usecarDateBeginTime' : Ext.Date.parse(queryParams.usecarDateBeginTime,'Y-m-d H:i:s'), // 用车开始时间
					'shortRentalMarkVo.shortRentalMarkDto.usecarDateEndTime' : Ext.Date.parse(queryParams.usecarDateEndTime,'Y-m-d H:i:s'), // 用车结束时间
					'shortRentalMarkVo.shortRentalMarkDto.inviteVehicleNo' : queryParams.inviteVehicleNo, // 约车编号
					'shortRentalMarkVo.shortRentalMarkDto.vehicleNo' : queryParams.vehicleNo, // 车牌号
					'shortRentalMarkVo.shortRentalMarkDto.vehicleState' : queryParams.vehicleState, // 车辆状态
					'shortRentalMarkVo.shortRentalMarkDto.useCarDepartment' : useCarDepartmentPam, // 部门编码
					'shortRentalMarkVo.shortRentalMarkDto.orgCode' : short_orgCode,//当前部门编码
					'shortRentalMarkVo.shortRentalMarkDto.departmentSignle' : short_departmentSignle//当前部门标识
				}
			});
		},
		
	'datachanged' : function(store){
		  document.getElementsByName("shortGridTotalCount")[0].value=store.getTotalCount();
		 //Ext.getCmp("shortGridTotalCount").setValue(store.getTotalCount());
	}
}
});

//用车原因store
Ext.define('Foss.shortTemprentalmark.CarReasonStore', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	data : {
		'items' : [{
			code : '',
			name : ''
		}, {
			code : 'shortHanded',
			name : '缺人'
		}, {
			code : 'specialGoods',
			name : '异形货'
		}, {
			code : 'exhibition',
			name : '会展'
		}, {
			code : 'warehouseEntry',
			name : '进仓'
		}, {
			code : 'lackVehiclesS',
			name : '缺车'
		}, {
			code : 'limitLine',
			name : '公司限行'
		}, {
			code : 'customerReason',
			name : '客户原因'
		}, {
			code : 'longDelivery',
			name : '超远派送'
		}, {
			code : 'externalCauses',
			name : '外场原因'
		}, {
			code : 'others',
			name : '其他'
		}]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

//租车用途store1
Ext.define('Foss.shortTemprentalmark.ShortRentalUseStore', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	data : {
		'items' : [{
			code : '',
			name : ''
		}, {
			code : 'ZH',
			name : '转货'//转货
		},{
			code : 'DJH',
			name : '大客户接货'//大客户接货
		}]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
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

function shortNoRepeatMark(dataListAll){

	//不能多次标记
	if(dataListAll.length>0){
		for(i=0;i<dataListAll.length;i++){
			if(dataListAll[i].get('rentalAmount')!=null&&dataListAll[i].get('rentalAmount')!=''){
				return 'Y';
			}
		}
		
//		if (dataListAll.length>1) {
//			Ext.Msg.alert('标题',
//			'一次只能标记一条');
//			return 'N'
//		}
		var noList=new Array() ;//存放车辆任务ID
		var origOrgCodeList = new Array();
		var destOrgCodeList = new Array();
		for(i=0;i<dataListAll.length;i++){
			noList[i] = dataListAll[i].get('truckTaskId');
			origOrgCodeList.push(dataListAll[i].get('origOrgCode'));
			destOrgCodeList.push(dataListAll[i].get('destOrgCode'));
		}
		var kmsNum=0;
		Ext.Ajax.request({
			url : scheduling.realPath('truckTaskRepeatMark.action'),
			async :  false,
			params : {
				'shortRentalMarkVo.shortRentalMarkDto.truckTaskIdList':noList,
				'shortRentalMarkVo.shortRentalMarkDto.origOrgCodeList':origOrgCodeList,
				'shortRentalMarkVo.shortRentalMarkDto.destOrgCodeList':destOrgCodeList
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				kmsNum=result.shortRentalMarkVo.kmsNum;
				//Ext.getCmp('short_temprentalmarkKmsNum').setValue(kmsNum);
				//alert(kmsNum);
				scheduling.shortTemprentalmark.temprentalMarkForm2.getForm().findField('short_temprentalmarkKmsNum').setValue(kmsNum);
			},
			exception:function (response){
				Ext.Msg.alert("租车标记多次标记校验异常");//('提示','小票单号查询失败')
				return;
			}
		});
	}else{
		return 'N';
	}
}

/**
 * 去掉相同的出发部门或到达部门
 */
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

/**
 *比较最晚时间
 **/
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

/**
 *租车标记函数
 ***/
function shortRentalMark(){
	//创建租车标记窗口
	if(scheduling.shortTemprentalmark.markWin==null){
		scheduling.shortTemprentalmark.markWin=Ext.create('Foss.shortTemprentalmark.window.temprentalMark');
	}

	var vehicleNoList = new Array();//存放车牌号
	var origOrgCodeList = new Array();
	var destOrgCodeList = new Array();
	var origOrgEntityList = new Array();
	var destOrgEntityList = new Array();
	var kmsNumList = new Array();
	var j=0;
	
	/**为初始化车牌号和用车日期做准备**/
	for(i=0;i<dataList.length;i++){
		dateList_st[i]=dateFormat(dataList[i].get('useCareDate'));
		vehicleNoList[i]=dataList[i].get('vehicleNo');
		kmsNumList[i]=dataList[i].get('kmsNum');
		origOrgCodeList[j]=dataList[i].get('origOrgCode');
		destOrgCodeList[j]=dataList[i].get('destOrgCode');
		origOrgEntityList[j]=dataList[i].get('origOrgName');
		destOrgEntityList[j]=dataList[i].get('destination');
		j++;
	}
	origOrgCodeList = uniqueArray(origOrgCodeList);
	destOrgCodeList = uniqueArray(destOrgCodeList);

	var temprentalMarkForm1 = scheduling.shortTemprentalmark.temprentalMarkForm1.getForm();
	var temprentalMarkForm2 = scheduling.shortTemprentalmark.temprentalMarkForm2.getForm();
	
	for(i=0;i<dataList.length;i++){
		useCareDate=temprentalMarkForm1.findField('short_useCareDate').setValue(maxDate(dateList_st));//用车时间
	}
	inviteVehicleNo= temprentalMarkForm1.findField('invite_VehicleNo'); //Ext.getCmp('invite_VehicleNo');
	
	temprentalMarkForm2.findField('short_origOrgName').setValue(origOrgEntityList);
	temprentalMarkForm2.findField('short_destOrgName').setValue(destOrgEntityList);

	if(scheduling.shortTemprentalmark.departmentSignle!=null){
		short_orgCode = scheduling.shortTemprentalmark.departmentSignle.split('_')[1];
		short_departmentSignle = scheduling.shortTemprentalmark.departmentSignle.split('_')[0];
	}


	temprentalMarkForm2.findField('short_origOrgName').readOnly = true;
	temprentalMarkForm2.findField('short_destOrgName').readOnly = true;
	temprentalMarkForm2.findField('short_temprentalmarkKmsNum').readOnly = true;
	temprentalMarkForm2.findField('short_vehicleNo_id').readOnly = true;
	
	var shortRentalAmount = temprentalMarkForm2.findField('short_rentalAmount');
	if(short_departmentSignle=='SalesDepartment'){//为营业部时
		inviteVehicleNo.allowBlank=false;
		inviteVehicleNo.hidden = false;
		shortRentalAmount.xtype = 'textfield';
		shortRentalAmount.readOnly = true;
	}else{
		shortRentalAmount.xtype = 'numberfield';
		shortRentalAmount.readOnly = false;
		inviteVehicleNo.hidden = true;
	}
	//车牌号
	scheduling.shortTemprentalmark.temprentalMarkForm2.getForm().findField('short_vehicleNo_id').setValue(vehicleNoList[0]);
	scheduling.shortTemprentalmark.temprentalMarkForm2.getForm().findField('short_vehicleNo_id').readOnly = true;
	//显示租车标记窗口
	scheduling.shortTemprentalmark.markWin.show();
}


var shortTemprentalTabGridStore = null;

//显示的grid
Ext.define('Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 450,
	emptyText : "查询结果为空",
	constructor : function(config) {
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryStore');
		shortTemprentalTabGridStore = me.store;
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SINGLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : '租车标记',//租车标记按钮
			disabled : !scheduling.shortTemprentalmark.isPermission('scheduling/shortTemprentalmarkButton'),
			hidden : !scheduling.shortTemprentalmark.isPermission('scheduling/shortTemprentalmarkButton'),
			handler : function(){
				var grid = this.ownerCt.ownerCt;
				dataList= grid.getSelectionModel().getSelection();
				if(dataList.length==0){
					Ext.Msg.alert('提示','请选择数据后操作');//("提示","请选择数据后操作")
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
				//("提示","该交接单已被标记，不能再次标记")
				if(repatMarkSigln=='Y'){
					Ext.Msg.alert('提示','同一车辆任务不可重复标记');
					return;
				} else{ 
					shortRentalMark();//执行点击租车标记后续操作
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
		scheduling.shortTemprentalmark.pagingBar = me.bbar;
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
	title : "查询结果",  
	columns : [{
		header : "约车编号", 
		dataIndex: 'inviteVehicleNo',
		flex : 1.5
	}, {
		header : "金额",  
		dataIndex : 'rentalAmount',
		flex : 1
	}, {
		header : "车牌号",   
		dataIndex : 'vehicleNo',
		flex : 1
	}, {
		header : "车辆状态",   
		dataIndex : 'vehicleState',
		flex : 1,
		renderer:function(value){
			if(value == 'ARRIVED' || value == 'UNLOADED'){
				return '已到达';
			} else{
				return '未到达';
			}
			
		}
	}, {
		header : "用车时间", 
		dataIndex: 'useCareDate',
		flex : 2,
		renderer:function(value){
			if(value!=null){
				return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),value.getDate(),value.getHours(),value.getMinutes(),value.getSeconds()),'Y-m-d H:i:s');
			}
		}
	},{
		header: '目的站',
		dataIndex: 'destination'
	}, {
		header : "用车部门", 
		dataIndex : 'origOrgName',
		flex : 2
	}, {	
		header : "租车编号", 
		dataIndex : 'temprentalMarkNo',
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

/**
 * 租车标记表单1
 */
Ext.define('Foss.shortTemprentalmark.form.temprentalMark1',{
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	columnWidth: 1,
	frame:true,
	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:90
	},
	items : [{
		xtype:'combobox',
		editable : false,
		autoWidth:true,
		name: 'shortRentalUse',
		store: Ext.create('Foss.shortTemprentalmark.ShortRentalUseStore'),
		displayField : 'name',
		maxLength : 20,
//		id:'short_RentalUse',
		columnWidth : .33,
		valueField : 'code',
		allowBlank: false,
		fieldLabel: '租车用途',//租车用途
		listeners:{
			select : {	
			    fn : function(combo, records, eOpts) {
				var record = records[0];
				var memory = record.get('code');
				if (memory == 'DJH') {
					Ext.Msg.alert('系统提示','大客户接货需要满足:①车上仅装一个快递大客户货;②车价占整车收入占比<12%,方可发车。');
			     }
			}
		}
	}	
	},{
		xtype:'textfield',
		readOnly: true,
		format: 'Y-m-d H:i:s',
		altFormats: 'Y,m,d H:i:s|Y.m.d H:i:s',
		columnWidth : .33,
		autoWidth:true,
		allowBlank: false,
		maxLength : 30,
		name: 'short_useCareDate',
		//id :'short_useCareDate',
		fieldLabel: '用车日期'   //用车日期
	}, {
		xtype : 'combo',
		fieldLabel : "用车原因",//用车原因
		value : '',
		allowBlank: false,
		maxLength : 20,
		name : 'carReason',
		displayField : 'name',
		valueField : 'code',
		columnWidth : .33,
		autoWidth:true,
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		store : Ext.create('Foss.shortTemprentalmark.CarReasonStore')
	}, {
		xtype : 'textfield',
		fieldLabel: "约车编号",//约车编号
		name: 'invite_VehicleNo',
		//id :'invite_VehicleNo',
		maxLength : 20,
		allowBlank: true,
		autoWidth:true,
		columnWidth:1,
		listeners:{
			'blur':function( cmp, the, eOpts){
				var inviteVehicleNo=this.getValue();
				if(inviteVehicleNo!=null&&inviteVehicleNo!=''){
					Ext.Ajax.request({
						url : scheduling.realPath('queryShortInviteVehicleNo.action'),
						params : {'shortRentalMarkVo.inviteVehicleNo' : inviteVehicleNo},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var inviteVehicleNoResult=result.shortRentalMarkVo.inviteVehicleNo;//约车编号
							var inviteState=result.shortRentalMarkVo.inviteState;//约车编号状态
							var rentalAmount=result.shortRentalMarkVo.rentalAmount;//租车金额
							if(inviteState!='RETURN'){
								cmp.setFieldStyle('color:red;');
								cmp.validator=false;
								Ext.Msg.alert('请输入已退回的同城外租车约车编码');
								cmp.reset();
								cmp.setFieldStyle('color:black;')
							}else{
								cmp.validator=true;
							}
							if(rentalAmount!=null||rentalAmount!=''){
								scheduling.shortTemprentalmark.temprentalMarkForm2.getForm().findField('short_rentalAmount').setValue(result.shortRentalMarkVo.rentalAmount);
								scheduling.shortTemprentalmark.temprentalMarkForm2.getForm().findField('short_rentalAmount').setReadOnly(true);
							}
							if(null==rentalAmount||rentalAmount==''){
								scheduling.shortTemprentalmark.temprentalMarkForm2.getForm().findField('short_rentalAmount').applyEmptyText();
								scheduling.shortTemprentalmark.temprentalMarkForm2.getForm().findField('short_rentalAmount').setReadOnly(true);
							}
						},
						failure:function (response){
							Ext.Msg.alert(('提示'), '约车编号验证失败');
						}
					});
				}
			}
		}
	}, {
		xtype:'radiogroup',
		vertical:true,
		allowBlank:false,
		columnWidth:.33,
		name:'useVehiclePlatform',
		//id:'YesOrNo',
		//id :'short_yesOrNo',
		fieldLabel:'使用请车平台',//使用请车平台
		items:[{
			xtype:'radio',
			boxLabel:'是',
			name:'useVehiclePlatform',
			inputValue:'YES'
	}, {
			xtype:'radio',
			boxLabel:'否',
			name:'useVehiclePlatform',
			inputValue:'NO'
		}],
		listeners:{
			'change':function(tabPanel,newValue,oldValue,eOpts){
//				var shortSalesVehiclePlatformName = this.up('form').findField('short_salesVehiclePlatformName');
				var shortSalesVehiclePlatformName = this.next();
				if(newValue.useVehiclePlatform== "YES"){
					shortSalesVehiclePlatformName.setDisabled(false);
					shortSalesVehiclePlatformName.setValue("");
				}else{
					shortSalesVehiclePlatformName.setDisabled(true);
					shortSalesVehiclePlatformName.setValue("请输入请车平台名称");
				}
			}
		}
	}, {
		disabled:true,
		readonly:true,
		fieldLabel : '请车平台名称',
		name : 'short_salesVehiclePlatformName',
		//id :'short_salesVehiclePlatformName',
		hideLabel:true,
		xtype:'textfield',
		value :'请输入请车平台名称',
		allowBlank: false,
		columnWidth:.18,
		labelWidth : 35
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 租车标记表单2
 */
Ext.define('Foss.shortTemprentalmark.form.temprentalMark2',{
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	columnWidth: 1,
	frame:true,
	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:70
	},
	items : [{
		xtype:'textfield',
		name: 'short_vehicleNo_id',
		//id:'short_vehicleNo_id',
		allowBlank: false,
		autoWidth:true,
		maxLength:100,
		forceSelection: true,
		fieldLabel: '车牌号',   //车牌号
		columnWidth: .33
	}, {
		name: 'short_rentalAmount',
		//id:'short_rentalAmount',
		allowBlank: false,
		autoWidth:true,
		fieldLabel : '租车金额',//租车金额
		regex:/^-?\d{1,10}\.?\d{0,2}$/,
		validator : function(value) {
			if(value != '' && value <= 0) {
				return '请输入大于0的值';
			}
			return true;
		},
		columnWidth: .33
	}, {
		xtype: 'textfield',
		autoWidth:true,
		allowBlank: false,
		allowNegative: false,
		fieldLabel: '公里数',//公里数
		name: 'short_temprentalmarkKmsNum',
		//id:'short_temprentalmarkKmsNum',
		columnWidth: .33
	}, {
		xtype : 'textfield',
		allowBlank: false,
		autoWidth:true,
		name: 'short_origOrgName',
		//id :'short_origOrgName',
		columnWidth: .80,
		fieldLabel: '出发地'//出发地
	}, {
		xtype : 'textfield',
		allowBlank: false,
		autoWidth:true,
		name: 'short_destOrgName',
		//id : 'short_destOrgName',
		fieldLabel: '目的地',//目的地
		columnWidth: .80
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.shortTemprentalmark.form.temprentalMark4',{
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	columnWidth: 1,
	frame:false,

	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:70
	},
	items : [{
		name: 'shortRemark',
		xtype: 'textarea',
		allowBlank: true,
		columnWidth: 1,
		maxLength:165,
		autoWidth:true,
		height:100,
		fieldLabel : '备注'

	}, {
		xtype: 'container',
		columnWidth:1,
		buttonAlign: 'center',
		layout:'column',
		defaults: {
			margin:'5 0 5 0'
		},
	items: [{
		xtype:'button',
		text:'返回',
		columnWidth: .25,
		handler:function(){
			scheduling.shortTemprentalmark.markWin.hide();
		}
	}, {
		border : false,
		columnWidth:.5,
		html: '&nbsp;'
	}, {
		xtype:'button',
		//id :'shortRentalmarkCommitBtn',
		text:'提交',
		columnWidth: .25,
		handler:function(){
			var length=scheduling.shortTemprentalmark.markWin.items.length;
			var param1 = scheduling.shortTemprentalmark.markWin.items.items[0].getForm().getValues();
			//用车原因
			var carReason = param1.carReason;
			if(checkForm(scheduling.shortTemprentalmark.markWin)){
				var myMask = new Ext.LoadMask(scheduling.shortTemprentalmark.markWin, {
					msg:'加载中，请稍后...'
				});
				myMask.show();
				var rentalUse = param1.shortRentalUse;//租车用途
				var useCareDate = param1.short_useCareDate;//用车日期
				var inviteVehicleNo = param1.invite_VehicleNo;//约车编号
				var origOrgCode=dataList[0].get('origOrgCode');//出发部门编码
				var destOrgCode=dataList[0].get('destOrgCode');//到达部门编码
				var salesVehiclePlatformName=param1.short_salesVehiclePlatformName;//请车平台名称
				var useVehiclePlatform=param1.useVehiclePlatform;//使用请车平台
				var multiCarTakeGoodsDtoList = new Array();
				var shortRentalMarkEntityList= new Array() ;
				for(i=1;i<length-1;i++){
					var form = scheduling.shortTemprentalmark.markWin.items.items[i].getForm().getValues();
					var departureName = scheduling.shortTemprentalmark.markWin.items.items[i].getForm().findField('short_origOrgName').getValue();
					var destinationName = scheduling.shortTemprentalmark.markWin.items.items[i].getForm().findField('short_destOrgName').getValue();
					// 1、出发地长度校验，由最长16位code变更为最长50位；2、字段输入和保存的规则不变
					if(scheduling.shortTemprentalmark.markWin.items.items[i].getForm().findField('short_origOrgName').getValue().length>50){
						myMask.hide();//解锁屏
						Ext.Msg.alert("提示","出发地长度超过限定值");
						return;
					}
					// 1、 目的地长度校验，由最长16位code变更为最长50位；2、字段输入和保存的规则不变
					if(scheduling.shortTemprentalmark.markWin.items.items[i].getForm().findField('short_destOrgName').getValue().length>50){
						myMask.hide();//解锁屏
						Ext.Msg.alert("提示","目的地长度超过限定值");
						return;
					}
					
					var multiCarTakeGoodsDto = new Object();//配置参数
					multiCarTakeGoodsDto.vehicleNo =form.short_vehicleNo_id;
					multiCarTakeGoodsDto.rentalAmount =form.short_rentalAmount;
					multiCarTakeGoodsDto.kmsNum =form.short_temprentalmarkKmsNum;
					multiCarTakeGoodsDto.departureCode =origOrgCode;
					multiCarTakeGoodsDto.destinationCode =destOrgCode;
					multiCarTakeGoodsDto.departureName=departureName;
					multiCarTakeGoodsDto.destinationName=destinationName;
					var record = new Foss.shortTemprentalmark.tempmarkModel(multiCarTakeGoodsDto);
					multiCarTakeGoodsDtoList.push(record.data);
				}
				for(i=0;i<dataList.length;i++){
					shortRentalMarkEntityList.push(dataList[i].data);
				}
				
				var param2 = scheduling.shortTemprentalmark.markWin.items.items[length-1].getForm().getValues()
				var remark = param2.shortRemark;
				Ext.Ajax.request({
					url : scheduling.realPath('addShortTempRentalMark.action'),
					jsonData : {
						'shortTempRentalMatkVo':{
							'isRepeateMark':isRepeateMark_st,
							'useCareDate':useCareDate,
							'carReason':carReason,
							'inviteVehicleNo':inviteVehicleNo,
							'multiCarTakeGoodsDtoList':multiCarTakeGoodsDtoList,
							'shortRentalMarkEntityList':shortRentalMarkEntityList,
							'remark':remark,
							'departmentSignle':short_departmentSignle,
							'salesVehiclePlatformName':salesVehiclePlatformName,
							'useVehiclePlatform' : useVehiclePlatform,
							'shortRentalUse' : rentalUse
						}
					},
					success : function(response) {
						myMask.hide();//解锁屏
						Ext.Msg.alert('租车标记','操作成功');//("租车标记","操作成功")
						scheduling.shortTemprentalmark.markWin.close();
					},
					exception:function (response){
						myMask.hide();//解锁屏
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert('提示',result.message);
					}
				});
			}
			}}]}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 租车标记窗口
 */
Ext.define('Foss.shortTemprentalmark.window.temprentalMark', {
	extend:'Ext.window.Window',
	title: '租车标记',//租车标记,
	modal:true,
	closeAction:'hide',
	width: 800,
	bodyCls: 'autoHeight',
	layout: 'auto',
	items:[scheduling.shortTemprentalmark.temprentalMarkForm1=Ext.create('Foss.shortTemprentalmark.form.temprentalMark1'),
		scheduling.shortTemprentalmark.temprentalMarkForm2=Ext.create('Foss.shortTemprentalmark.form.temprentalMark2'),
		scheduling.shortTemprentalmark.temprentalMarkForm4=Ext.create('Foss.shortTemprentalmark.form.temprentalMark4')],
	listeners:{
		'hide':function(window,eOpts){
			var len = window.items.length;
			for(var i=2 ;i<len-1;i++){
				if(window.items.length>3)
					window.remove(window.items.items[2],true);
			}
			len = window.items.length;
			for(var i=0;i<len;i++){
				window.items.items[i].getForm().reset();
			}
		}
	}
});

//页面初始化入口
Ext.onReady(function() {
	//悬浮提示开启
	Ext.QuickTips.init();
	// 查询条件
	//var shortTemprentalmarkQueryForm = Ext.create('Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryForm');
	// grid
	//var shortTemprentalmarkQueryGrid = Ext.create('Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryGrid');
//	rentalUseStore1=Ext.create('Foss.shortTemprentalmark.RentalUseStore1');
//	rentalUseStore2=Ext.create('Foss.shortTemprentalmark.RentalUseStore2');
	// 保存到全局变量中
	scheduling.shortTemprentalmark.shortTemprentalmarkQueryForm = Ext.create('Foss.scheduling.shortTemprentalmark.shortQueryForm');
	scheduling.shortTemprentalmark.shortTemprentalmarkQueryGrid = Ext.create('Foss.scheduling.shortTemprentalmark.shortTemprentalmarkQueryGrid');

	Ext.create('Ext.panel.Panel', {
				//id : 'T_scheduling-shortTemprentalmark_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				layout : 'auto',
				items : [scheduling.shortTemprentalmark.shortTemprentalmarkQueryForm, 
					scheduling.shortTemprentalmark.shortTemprentalmarkQueryGrid],
				renderTo : 'T_scheduling-shortTemprentalmark-body'
			});
	 //alert(scheduling.shortTemprentalmark.shortTemprentalmarkQueryGrid.getStore());
	 //shortGridTotalCount = Ext.get('shortGridTotalCount');
});

