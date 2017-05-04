/**
 * 客户查询Form								Foss.baseinfo.customerIndex.QueryCustomerForm
 * 客户查询结果grid							Foss.baseinfo.customerIndex.QueryCustomerGrid
 * 客户修改Win								Foss.baseinfo.customerIndex.CustomerWin
 * 客户界面form								Foss.baseinfo.customerIndex.CustomerWinForm
 */
//------------------------------------业务方法----------------------------------
//修改客户
baseinfo.customerIndex.operatorCount = {defaultV:0,successV:1,failureV:-1};	//偏线代理 操作返回值 1为成功，-1为失败
baseinfo.customerIndex.readOnly;									//readOnly属性（新增）
baseinfo.customerIndex.booleanType = {all:'ALL',yes:'Y',no:'N'};	//booleanType  对应后台常量 "布尔类型"
baseinfo.customerIndex.booleanStr = {yes:'true',no:'false'};		//booleanStr   从复选框中得到值
//查看状态viewCustomerState："ADD"新增,"UPDATE"修改,"VIEW"查看
baseinfo.customerIndex.viewCustomerState = {add:'ADD',update:'UPDATE',view:'VIEW'};
//业务类型：快递/零担
baseinfo.customerIndex.businessType = {EXPRESS:'EXPRESS',LTT:'LTT'};

//信息
baseinfo.customerIndex.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.customerIndex.i18n('i18n.baseinfo-util.fossAlert'),
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.INFO,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
	setTimeout(function(){
      Ext.Msg.hide();
  }, 3000);
};
// 联系人类型 转换
baseinfo.customerIndex.showContactType = function(conArray) {
	var linkManArray = ['财务联系人','业务联系人','发货联系人','收货联系人','协议联系人']
		retuen_v = '';
	for(var i = 0;i<conArray.length;i++){
		if(1 == conArray[i] || '1' == conArray[i]){
			if(retuen_v == ''){
				retuen_v = linkManArray[i];
			}else{
				retuen_v += ','+linkManArray[i];
			}
		}
	}
	return retuen_v;
};
//提交客户
baseinfo.customerIndex.queryCustInfoByCode = function(customerEntity,grid){
	var url = baseinfo.realPath('queryCustInfoByCode.action'),customerVo = {};
	customerVo.customerEntity = customerEntity;
	Ext.Ajax.request({
		url:url,
		async:false,
		jsonData:{'customerVo':customerVo},
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes(m_dateError);
			}else{//返回会员实体 DTO customerVo.customerDto
				if(!Ext.isEmpty(result.customerVo.customerDto)){
					customerVo = result.customerVo.customerDto;
					return customerVo;
				}
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				baseinfo.showErrorMes(m_dateError);
			}else{
				baseinfo.showErrorMes(result.message);
			}
		}
	});
	return customerVo;
};
//------------------------------------MODEL----------------------------------
//用来存储交互“客户”的数据库对应实体MODEL
Ext.define('Foss.baseinfo.customerIndex.CustomerModel', {
extend: 'Ext.data.Model',
fields : [//客户地址
    {name:'address',type:'string'},
    //客户属性
    {name:'property',type:'string'},
    //客户类型
    {name:'type',type:'string'},
    //信用额度
    {name:'creditAmount',type:'number'},
    //客户名称
    {name:'name',type:'string'},
    //营业执照号
    {name:'license',type:'string'},
    //客户所属部门标杆编码
    {name:'unifiedCode',type:'string'},
    //客户编码
    {name:'cusCode',type:'string'},
    //客户是否有效
    {name:'activeCus',type:'string'},
    //月结客户总欠款
    {name:'totalArrears',type:'number'},
    //结算方式
    {name:'chargeMode',type:'string'},
    //客户等级
    {name:'degree',type:'string'},
    //CRM客户ID
    {name:'crmCusId',type:'string'},
    //是否启用
    {name:'active',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //是否异地调货
    {name:'fistrangoods',type:'string'},
    
    //部门名称
    {name:'deptname',type:'string'},
    {name:'deptName',type:'string'},
    {name:'contactList',defaultValue:[]},
    {name:'bankAccountList',defaultValue:[]},
    {name:'bargainList',defaultValue:[]},
    
    
    //所在省份名称
    {name:'provName',type:'string'},
    //所在城市名称
    {name:'cityName',type:'string'},
    //客户简称 simpleName
    {name:'simpleName',type:'string'},
    //是否重要客户
    {name:'isImport',type:'string'},
    //是否特殊客户
    {name:'isSpecial',type:'string'},
    //发票抬头
    {name:'billTitle',type:'string'},
    
    //客户合同-优惠信息集合（封装）
    {name:'bargainPreferList',defaultValue:[]},
    //客户联系人-偏好地址-客户接送货地址
    {name:'contactAddressList',defaultValue:[]}]
});
//用来存储交互“联系人”的数据库对应实体MODEL
Ext.define('Foss.baseinfo.customerIndex.LinkmanModel', {
extend: 'Ext.data.Model',
fields : [//性别
    {name:'gender',type:'string'},
    //办公电话
    {name:'contactPhone',type:'string'},
    //移动电话
    {name:'mobilePhone',type:'string'},
    //传真
    {name:'faxNo',type:'string'},
    //联系人地址
    {name:'address',type:'string'},
    //电子邮箱
    {name:'email',type:'string'},
    //邮编
    {name:'zipCode',type:'string'},
    //生日
    {name:'birthday',type:'date'},
    //身份证号
    {name:'idCard',type:'string'},
    //个人爱好
    {name:'hobby',type:'string'},
    //是否接收邮件
    {name:'receiveEmail',type:'string'},
    //是否接收短信
    {name:'receiveMessage',type:'string'},
    //是否接收信件
    {name:'receiveLetter',type:'string'},
    //获知公司途径
    {name:'way',type:'string'},
    //民族
    {name:'nation',type:'string'},
    //籍贯
    {name:'hometown',type:'string'},
    //职务
    {name:'title',type:'string'},
    //任职部门
    {name:'workingDept',type:'string'},
    //联系人姓名
    {name:'contactName',type:'string'},
    //备注
    {name:'notes',type:'string'},
    //联系人类型
    {name:'contactType',type:'string'},
    //是否主联系人
    {name:'mainContract',type:'string'},
    //是否启用
    {name:'active',type:'string'},
    //客户编码
    {name:'customerCode',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null},
    //在CRM中FID
    {name:'crmId',defaultValue:null}, 
    //一个客户联系人对应多个联系人—地址（偏好地址）
    {name:'contactAddrList',defaultValue:[]},
    
    //接送货地址
    {name:'contactAddress',type:'string'},
    //详细地址（接送街道）
    {name:'address',type:'string'},
    //邮编
    {name:'zipCode',type:'string'},
    //省份
    {name:'provinceName',type:'string'},
    //城市
    {name:'cityCode',type:'string'},
    //区县
    {name:'countyCode',type:'string'},
    //地址类型
    {name:'addressType',type:'string'},
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null}]
});
//用来存储交互“客户合同”的数据库对应实体MODEL
Ext.define('Foss.baseinfo.customerIndex.CusBargainModel', {
extend: 'Ext.data.Model',
fields : [//付款方式
    {name:'chargeType',type:'string'},
    //申请欠款额度
    {name:'arrearsAmount',type:'number'},
    //协议联系人姓名
    {name:'name',type:'string'},
    //联系人固定电话
    {name:'contactPhone',type:'string'},
    //联系人手机
    {name:'mobilePhone',type:'string'},
    //联系人详细地址
    {name:'address',type:'string'},
    //对账日期
    {name:'statementDate',defaultValue:null},
    //开发票日期
    {name:'invoicingDate',defaultValue:null},
    //结款日期
    {name:'checkoutDate',defaultValue:null},
    //申请事由
    {name:'applyReason',type:'string'},
    //所属部门标杆编码
    {name:'unifiedCode',type:'string'},
    //合同适用部门
    {name:'applicateOrgId',type:'string'},
    //是否折扣
    {name:'discount',type:'string'},
    //合同状态
    {name:'status',type:'string'},
    //合同主体
    {name:'bargainSubject',type:'string'},
    //注册资金
    {name:'registerFunds',type:'number'},
    //原合同编号
    {name:'lastBargain',type:'string'},
    //合同编号
    {name:'bargainCode',type:'string'},
    //走货名称
    {name:'transName',type:'string'},
    //客户全称
    {name:'customerName',type:'string'},
    //协议联系人
    {name:'linkmanId',type:'string'},
    //结算方式
    {name:'chargeMode',type:'string'},
    //优惠类型
    {name:'preferentialType',type:'string'},
    //已用额度
    {name:'usedAmount',type:'number'},
    //是否超期
    {name:'overdue',type:'string'},
    //业务日期
    {name:'bizDate',type:'date'},
    //合同起始日期
    {name:'beginTime',type:'date',convert: dateConvert,defaultValue:null},
    //合同到期日期
    {name:'endTime',type:'date',convert: dateConvert,defaultValue:null},
    //是否启用
    {name:'active',type:'string'},
    //客户
    {name:'customerCode',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //在CRM中fid
    {name:'crmId',defaultValue:null}, 
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null},
    //一个客户合同对应多个合同适用部门
    {name:'appOrgList',defaultValue:[]},
    {name:'applyDepts',type:'string'},
    
    //运费折扣费率
    {name:'chargeRebate',defaultValue:null},
    //代收货款费率
    {name:'agentGathRate',defaultValue:null},
    //保价费率
    {name:'insureDpriceRate',defaultValue:null},
    //接货费率
    {name:'receivePriceRate',defaultValue:null},
    //送货费率
    {name:'deliveryFeeRate',defaultValue:null},
    //所属系统
    {name:'ftype',defaultValue:null},
    //快递优惠类型
    {name:'exPreferentialType',defaultValue:null}
    ]
});
//用来存储交互“联系人”的数据库对应实体MODEL
Ext.define('Foss.baseinfo.customerIndex.CusAccountModel', {
extend: 'Ext.data.Model',
fields : [//其他支行名称
    {name:'otherBranchBankName',type:'string'},
    //开户账号
    {name:'accountNo',type:'string'},
    //开户人姓名
    {name:'accountName',type:'string'},
    //开户行城市编码
    {name:'cityCode',type:'string'},
    //开户行省份编码
    {name:'provCode',type:'string'},
    //开户行编码
    {name:'bankCode',type:'string'},
    //手机号码
    {name:'mobilePhone',type:'string'},
    //账号与客户关系
    {name:'customer',type:'string'},
    //是否默认账号
    {name:'defaultAccount',type:'string'},
    //支行编码
    {name:'branchBankCode',type:'string'},
    //备注
    {name:'notes',type:'string'},
    //是否启用
    {name:'active',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //账户性质 对公；对私两种
    {name:'accountNature',type:'string'},
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null},
    //在CRM中FID
    {name:'crmId',defaultValue:null}, 
    //开户行所在城市名称
    {name:'cityName',type:'string'},
    //开户行省份名称
    {name:'provinceName',type:'string'},
    //开户行名称
    {name:'openingBankName',type:'string'},
    //支行名称
    {name:'branchBankName',type:'string'},
    //所属客户ID
    {name:'belongCustom',defaultValue:null},
    //财务联系人名称
    {name:'financeLinkman',type:'string'},
    //账户用途
    {name:'accountUse',type:'string'}]
});
//------------------------------------STORE----------------------------------
//客户STORE
Ext.define('Foss.baseinfo.customerIndex.CustomerStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.customerIndex.CustomerModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryCustomers.action'),
		reader : {
			type : 'json',
			root : 'customerVo.customerList',
			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//客户 查询条件
Ext.define('Foss.baseinfo.customerIndex.QueryCustomerForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.customerIndex.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:110
    },
    height :140,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
    record:null,												//绑定的model Foss.baseinfo.customerIndex.CustomerModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.customerIndex.CustomerModel'):me.record);
	},
	getItems:function(){
		var me = this;
		return [{
	        fieldLabel: baseinfo.customerIndex.i18n('foss.baseinfo.customer.custCode'),							//客户编码
			name:'cusCode'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.custName'),							//客户名称
			name:'name'
		},{
			xtype:'dynamicorgcombselector',
	        fieldLabel: baseinfo.customerIndex.i18n('foss.baseinfo.customer.belongDept'),							//所属部门
	        valueField : 'unifiedCode',						// 值
			name:'unifiedCode'
		}];
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	},
	buttons : [ {
		text : baseinfo.customerIndex.i18n('foss.baseinfo.query'),
		cls:'yellow_button',
		disabled:!baseinfo.customerIndex.isPermission('queryCustomers/customersQueryButton'),
		hidden:!baseinfo.customerIndex.isPermission('queryCustomers/customersQueryButton'),
		handler : function() {
			var me = this;
			var queryForm = me.up('form').getForm();
			var cusCode = queryForm.findField('cusCode').getValue();
			var name = queryForm.findField('name').getValue();
			var unifiedCode = queryForm.findField('unifiedCode').getValue();
			//查询条件是否全部可为空
			if(Ext.isEmpty(cusCode) && Ext.isEmpty(name) && Ext.isEmpty(unifiedCode)){
				me.up('form').showWarningMsg('温馨提醒','请输入查询条件！');
			}else{
				var grid  = Ext.getCmp('T_baseinfo-customerIndex_content').getQueryGrid();//得到grid
				grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
			}
		    
			
		}
	}, {
		text : baseinfo.customerIndex.i18n('foss.baseinfo.reset'),
//		cls:'yellow_button',
		disabled:!baseinfo.customerIndex.isPermission('queryCustomers/customersQueryButton'),
		hidden:!baseinfo.customerIndex.isPermission('queryCustomers/customersQueryButton'),
		handler : function() {
			this.up('form').getForm().reset();
		}
	}]
});
//客户 窗体 form
Ext.define('Foss.baseinfo.customerIndex.CustomerWinForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
    record:null,												//绑定的model Foss.baseinfo.customerIndex.CustomerModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults();
		me.items = me.getItems();
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.baseinfo.customerIndex.CustomerModel'):me.record);
	},
	getDefaults:function(){
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
	    	labelWidth:110,
	    	width:375,
	    	allowBlank:true,
	    	readOnly:true
	    };
	},
	getItems:function(){
		var me = this;
		return [{
	        fieldLabel: baseinfo.customerIndex.i18n('foss.baseinfo.customer.belongDept'),									//客户编码
			name:'deptname'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.custCode'),									//客户名称
			name:'cusCode'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.custSimple'),									//省份
			name:'simpleName'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.custName'),									//城市
			name:'name'
		},FossDataDictionary.getDataDictionaryCombo('CRM_CUSTOMER_GRADE',{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.custDegree'),
			name: 'degree',
	    	labelWidth:110,
	    	width:375,
	    	readOnly:true
		})
		//,{
			//fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.settlementStyle'),									//省份
			//name:'chargeMode'
		//}
		,FossDataDictionary.getDataDictionaryCombo('CRM_CUSTOMER_ATTRIBUTE',{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.custAttr'),
			name: 'property',
	    	labelWidth:110,
	    	width:375,
	    	readOnly:true
		})
		,{
			fieldLabel:'所在省份',									//省份
			name:'provName'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.belongCity'),									//城市
			name:'cityName'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.companyAddr'),									//客户名称
			name:'address'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.custTax'),								//省份
			name:'license',
			renderer:function(value){
				if(null == value){
					return '';
				}else{
					return value;
				}
			}
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.isImport'),	
			xtype:'combobox',
			store: Ext.create('Ext.data.Store', {
			    fields: ['code', 'name'],
			    data : [{'code':'Y','name':'是'},{'code':'N','name':'否'}]}),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
			name:'isImport'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.isSpecifici'),	
			xtype:'combobox',
			store: Ext.create('Ext.data.Store', {
			    fields: ['code', 'name'],
			    data : [{'code':'Y','name':'是'},{'code':'N','name':'否'}]}),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',							//城市
			name:'isSpecial'
		},{
			fieldLabel:'信用额度(元)',								//客户名称
			name:'creditAmount'
		},{
			fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.invoice'),									//省份
			name:'billTitle'
		}
		//,{
		//	fieldLabel:'应收金额(元)',									//城市
		//	name:' '
		//}
		];
	}
});
//------------------------------------GRID----------------------------------
/**
 * 客户查询grid
 */
Ext.define('Foss.baseinfo.customerIndex.QueryCustomerGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.customerIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    bodyCls:'autoHeight',
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.customerIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//得到客户编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.customerIndex.CustomerModel'):Ext.create('Foss.baseinfo.customerIndex.CustomerModel',param.formRecord);
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.customerIndex.CustomerWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		win.editForm.loadRecord(formRecord);
		//TODO 加载 联系人，合同，账号 信息		
		win.linkmanGrid.store.loadData(Ext.isEmpty(formRecord.get('contactAddressList'))?[]:formRecord.get('contactAddressList'));
		win.contractGrid.store.loadData(Ext.isEmpty(formRecord.get('bargainPreferList'))?[]:formRecord.get('bargainPreferList'));//bargainPreferList
		win.accountGrid.store.loadData(Ext.isEmpty(formRecord.get('bankAccountList'))?[]:formRecord.get('bankAccountList'));
		return win;
	},
	//得到客户编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getCustomerWin:function(viewState,param){
		if(baseinfo.customerIndex.viewCustomerState.update === viewState){
			baseinfo.customerIndex.readOnly = false;
			return this.getAgencyDeptWin(this.updateCustomerWin,baseinfo.customerIndex.i18n('foss.baseinfo.customer.alterCust'),viewState,param);
		}else if(baseinfo.customerIndex.viewCustomerState.view === viewState){
			baseinfo.customerIndex.readOnly = true;
			return this.getAgencyDeptWin(this.viewCustomerWin,baseinfo.customerIndex.i18n('foss.baseinfo.customer.custDetails'),viewState,param);
		}
	},
	updateCustomerWin:null,						//修改基客户
	updateCustomer:function(param){
		return this.getCustomerWin(baseinfo.customerIndex.viewCustomerState.update,param);
	},
	viewCustomerWin:null,						//查看基客户
	viewCustomer:function(param){
		return this.getCustomerWin(baseinfo.customerIndex.viewCustomerState.view,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns();
		me.store = me.getStore();
		me.listeners = me.getMyListeners();
		//添加分页控件
		me.bbar = me.getPagingToolbar();
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 客户
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = baseinfo.customerIndex.queryCustInfoByCode(record.data);
            	if(!Ext.isEmpty(param.formRecord)){
            		//正常返回数据 才可打开界面
    				me.viewCustomer(param).show();
            	}
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.customerIndex.CustomerStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-customerIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var customerEntity = queryForm.record.getData();
					
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//客户编码
								'customerVo.customerEntity.cusCode':queryForm.findField('cusCode').getValue(),
								//客户名称
								'customerVo.customerEntity.name':queryForm.findField('name').getValue(),
								//客户名称
								'customerVo.customerEntity.unifiedCode':queryForm.findField('unifiedCode').getValue()
							}
						});	
					}
				}
		    }
		});
	},
	getColumns:function(){
		var me = this;
		return [{flex:1,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.custCode'),dataIndex : 'cusCode'}		//代理编码
			,{flex:1,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.custName'),dataIndex : 'name'}				//客户名称
			,{flex:1,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.custDegree'),dataIndex : 'degree'
				,renderer:function(v){
					return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_CUSTOMER_GRADE');
			}}			//客户等级
			,{flex:1,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.custType'),dataIndex : 'type'
				,renderer:function(v){
					return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_CUSTOMER_TYPE');
			}}				//客户类型
			,{flex:1,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.belongDept'),dataIndex : 'deptName'}			//所属部门
			,{flex:1,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.custAttr'),dataIndex : 'property'
				,renderer:function(v){
					return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_CUSTOMER_ATTRIBUTE');
			}}			//客户属性
//			,{flex:1,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.settlementStyle'),dataIndex : 'chargeMode'}		//客户类型
		];
	}
});
/**
 * 客户查询 联系人信息
 */
Ext.define('Foss.baseinfo.customerIndex.CustomerWinLinkmanGrid', {
	extend: 'Ext.grid.Panel',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	autoScroll:true,									//滚动轴
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.customerIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	columns:[{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.cantactName'),dataIndex : 'contactName'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.phoneNo'),dataIndex : 'mobilePhone'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.contactType'),dataIndex : 'contactType'
			,renderer:function(v){
				v = Ext.isEmpty(v)?[]:v.split(',');
				return baseinfo.customerIndex.showContactType(v);
			}}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.isMainContact'),dataIndex : 'mainContract'
			,renderer:function(v){
				return 'Y' == v?'是':'否';
			}
		}
		,{sortable: false,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.shippAddr'),dataIndex : 'contactAddress'}
		,{sortable: false,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.shippAddrPostCode'),dataIndex : 'zipCode'}
		,{sortable: false,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.shippProvince'),dataIndex : 'provinceName'}
		,{sortable: false,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.shippCity'),dataIndex : 'cityCode'}
		,{sortable: false,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.shippArea'),dataIndex : 'countyCode'}
		,{sortable: false,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.shippStreet'),dataIndex : 'address'}
		,{sortable: false,text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.shippAddrType'),dataIndex : 'addressType'
			,renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_ADDRESS_TYPE');
			}}],
	//选择模式
	selType: 'cellmodel',
    //增加表格列的分割线
	columnLines: true,
	viewConfig: {
		stripeRows: false,
		listeners: {
			viewready: {
				fn: function(view) {
					var grid = view.up('grid');
					mergeCells(grid, [1,2,3,4]);
				}
			}
		}
	}
});
/**
 * 客户查询 合同信息
 */
Ext.define('Foss.baseinfo.customerIndex.CustomerWinContractGrid', {
	extend: 'Ext.grid.Panel',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	autoScroll:true,									//滚动轴
	stripeRows : true, 									// 交替行效果s
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.customerIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	columns:[{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.contractCode'),dataIndex : 'bargainCode'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.businessType'),dataIndex : 'ftype'
			,renderer:function(v){
			if(v==baseinfo.customerIndex.businessType.LTT){
				return baseinfo.customerIndex.i18n('foss.baseinfo.customer.businessTypeOfLTT');
			}else if(v==baseinfo.customerIndex.businessType.EXPRESS){
				return baseinfo.customerIndex.i18n('foss.baseinfo.customer.businessTypeOfEXPRESS');
			}else{
				return v;
			}
		}}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.settlementStyle'),dataIndex : 'chargeType'
			,renderer:function(v){
				return FossDataDictionary.rendererSubmitToDisplay(v,'CLEARING_TYPE');
			}}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.preferentialType'),dataIndex : 'preferentialType'
			,renderer:function(v,o,r){
				// 优惠类型
				if(baseinfo.customerIndex.businessType.LTT==r.get('ftype')){
					return FossDataDictionary. rendererSubmitToDisplay (r.get('preferentialType'),'CRM_PREFERENTIAL_TYPE');
				}else if(baseinfo.customerIndex.businessType.EXPRESS==r.get('ftype')){
					return FossDataDictionary. rendererSubmitToDisplay (r.get('exPreferentialType'),'CRM_PREFERENTIAL_TYPE');
				}else{
					return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_PREFERENTIAL_TYPE');
				}
			}}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.effectiveDate'),dataIndex : 'beginTime'
			,renderer:function(v){
				if(!Ext.isEmpty(v)){
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
				return v;
			}}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.expirationDate'),dataIndex : 'endTime'
			,renderer:function(v){
				if(!Ext.isEmpty(v)){
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
				return v;
			}}
		,{text : '申请欠款额度(元)',dataIndex : 'arrearsAmount'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.protocolContact'),dataIndex : 'name'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.discountRates'),dataIndex : 'chargeRebate'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.insuredRate'),dataIndex : 'insureDpriceRate'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.collectionRate'),dataIndex : 'agentGathRate'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.receivingRate'),dataIndex : 'receivePriceRate'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.deliveryRate'),dataIndex : 'deliveryFeeRate'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.bindSector'),	dataIndex : 'applyDepts'}],
	//选择模式
	selType: 'cellmodel',	
	viewConfig: {
		stripeRows: false,
		listeners: {
			viewready: {
				fn: function(view) {
//					var grid = view.up('grid');
//					mergeCells(grid, [1,2,3,4,5,6,7]);
				}
			}
		}
	}
});
/**
 * 客户查询 财务信息
 */
Ext.define('Foss.baseinfo.customerIndex.CustomerWinAccountGrid', {
	extend: 'Ext.grid.Panel',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	autoScroll:true,									//滚动轴
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.customerIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	columns:[{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.bank'),dataIndex : 'openingBankName'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.bankName'),dataIndex : 'accountName'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.bankAccount'),dataIndex : 'accountNo'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.subBankAccount'),dataIndex : 'branchBankName'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.accountAttr'),dataIndex : 'accountNature'
			,renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_ACCOUNT_NATURE');
			}}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.bankProvence'),dataIndex : 'provinceName'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.bankCity'),	dataIndex : 'cityName'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.accountUse'),dataIndex : 'accountUse'
			,renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_ACCOUNT_USE');
			}}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.financeContact'),	dataIndex : 'financeLinkman'}
		,{text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.isDefaultAccount'),	dataIndex : 'defaultAccount'
			,renderer:function(v){
				return 'Y'==v?'是':'否';
			}}]
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-customerIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.customerIndex.QueryCustomerForm',{'record':Ext.create('Foss.baseinfo.customerIndex.CustomerModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.customerIndex.QueryCustomerGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-customerIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-customerIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items : [ queryForm, queryGrid]
	}));
});
//------------------------------------WINDOW----------------------------------
/**
 * 客户window
 */
Ext.define('Foss.baseinfo.customerIndex.CustomerWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.customerIndex.i18n('foss.baseinfo.customer.custDetails'),										//客户详情   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :850,
	height :600,		
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	autoScroll:true,										//滚动轴
	editForm:null,											//客户表单Form   Foss.baseinfo.customerIndex.CustomerWinForm
	linkmanGrid:null,										//客户联系人Grid Foss.baseinfo.customerIndex.CustomerWinLinkmanGrid
	contractGrid:null,										//客户合同Grid   Foss.baseinfo.customerIndex.CustomerWinContractGrid
	accountGrid:null,										//客户财务Grid   Foss.baseinfo.customerIndex.CustomerWinAccountGrid
	sourceGrid:null,										//来源表格 Grid
	viewState:null,											//查看状态 baseinfo.customerIndex.viewCustomerState
	formRecord:null,										//客户实体 Foss.baseinfo.BusinessPartnerModel
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.customerIndex.CustomerWinForm',{'height':360});
		//查询结果 联系人 显示列表
		me.linkmanGrid  = Ext.create('Foss.baseinfo.customerIndex.CustomerWinLinkmanGrid',{
			'height':150,
			store:Ext.create('Ext.data.Store',{
				model : 'Foss.baseinfo.customerIndex.LinkmanModel',
				data:[]
			})
		});
		//查询结果 合同 显示列表
		me.contractGrid  = Ext.create('Foss.baseinfo.customerIndex.CustomerWinContractGrid',{
			'height':150,
			store:Ext.create('Ext.data.Store',{
				model : 'Foss.baseinfo.customerIndex.CusBargainModel',
				data:[]
			})
		});
		//查询结果 账户，财务 显示列表
		me.accountGrid  = Ext.create('Foss.baseinfo.customerIndex.CustomerWinAccountGrid',{
			'height':150,
			store:Ext.create('Ext.data.Store',{
				model : 'Foss.baseinfo.customerIndex.CusAccountModel',
				data:[]
			})
		});
		me.items = [{html : baseinfo.customerIndex.i18n('foss.baseinfo.customer.custBasicInfo')},me.editForm,
		            {html : baseinfo.customerIndex.i18n('foss.baseinfo.customer.contactInfo')},me.linkmanGrid,
		            {html : baseinfo.customerIndex.i18n('foss.baseinfo.customer.contractInfo')},me.contractGrid,
		            {html : baseinfo.customerIndex.i18n('foss.baseinfo.customer.financeInfo')},me.accountGrid];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text : baseinfo.customerIndex.i18n('foss.baseinfo.customer.return'),
			margin : '0 720 0 0',
			handler :function(){
				me.hide();
			} 
		}];
	}
});

