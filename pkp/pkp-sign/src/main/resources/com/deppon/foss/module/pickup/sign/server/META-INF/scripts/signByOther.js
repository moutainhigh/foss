
/**
 * 定义数据模型 - 展现用
 */
Ext.define('Foss.sign.QuerySignByOtherModel',{
	extend: 'Ext.data.Model',
	fields:	[
	    {name: 'waybillNo',type: 'string'},////运单号
		{name: 'createOrgCode',type: 'string'},//创建部门
		{name: 'deliveryCustomerCode',type: 'string'},//发货客户编码
		{name: 'deliveryCustomerContact',type: 'string'},//发货客户name
		{name: 'deliveryCustomerMobilephone',type: 'string'},//发货客户手机
		{name: 'deliveryCustomerPhone',type: 'string'},//发货客户电话
		{name: 'insuranceAmount' },//保价金额
		{name: 'totalFee' }//总费用
	]
});

/**
 * 定义数据模型 - 更新用 
 */
Ext.define('Foss.sign.QuerySignByOtherUpdateModel',{
	extend: 'Ext.data.Model',
	fields:	[
	    {name: 'waybillNo',type: 'string'}//运单号
	]
});


//查询条件
Ext.define('Foss.QuerySignByOtherInfo.Form.QuerySignByOtherForm',{
	extend:'Ext.form.Panel',
	id:'Foss_sign_signByOther_QuerySignByOtherForm_Id',//id
	// 指定容器的标题
	title: "查询条件",
	frame:true,
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true, 
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//默认边距 间隔
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name:'waybillNo',//运单号
		fieldLabel:'运单号',//字段标题
		regex:/^[0-9]{8,9}$/i,
		regexText:'输入8-9位纯数字单号',
		allowBlank : false,
		columnWidth: 0.4
	},{
		name:'authorizationCode', //授权密码
		allowBlank : false,
		inputType: 'password',
		fieldLabel:'经理授权密码',//字段标题
		columnWidth: 0.4
	},{
		xtype : 'button',
		text:'查询',//字段标题
		cls:'yellow_button',
		disabled:!sign.signByOther.isPermission('signbyotherindex/signbyotherindexquerybutton'),
		hidden:!sign.signByOther.isPermission('signbyotherindex/signbyotherindexquerybutton'),
		//3秒内不能重新点plugin
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3 //3秒内不能重新点
		}),
		columnWidth:.08,
		handler:function(){
			//查询条件是否合法（非空等相关约束）
			if(sign.signByOther.queryPanel.getForm().isValid()){
				var values = sign.signByOther.queryPanel.getForm().getValues();//得到form values
				//ajax 	请求
				var array = {'vo':{'searchWaybillSignByOtherDto':values}};
				Ext.Ajax.request({
				    url: sign.realPath('queryWaybillReceiverInfo.action'),//老的url格式：
				    //'../sign/queryWaybillReceiverInfo.action',
				    jsonData: array,
				    success: function(response){
				    	//get ajax response
				    	var result = Ext.decode(response.responseText);			
				    	//get vo
				    	var data = result.vo.rtSearchWaybillSignByOtherDto;
				    	
				    	if(data.searchResult == 'success'){//成功
				    		var signByOtherModel = Ext.ModelManager.create(data.waybillEntity,
				    			'Foss.sign.QuerySignByOtherModel');
				    		
				    		//加载运单基本信息
				    		sign.signByOther.queryResult.loadRecord(signByOtherModel);
				    		
				    		//创建数据模型
				    		var signByOtherUpdateModel = Ext.ModelManager.create(data.waybillEntity,
			    				'Foss.sign.QuerySignByOtherUpdateModel');
				    		
				    		//读取数据
				    		sign.signByOther.queryResultUpdate.loadRecord(signByOtherUpdateModel);
				    		
				    		//set up id
				    		Ext.getCmp('Foss_sign_signByOther_waybillNoUpdate_Id').setValue(data.waybillEntity.waybillNo);
				    		
				    		//原始的收件人信息不要填写
				    		
				    	}else if(data.searchResult == 'waybillNo_invalid'){//waybill no is invalid
				    		//没有符合的记录
				    		Ext.ux.Toast.msg('查询失败', '对不起，没有符合的记录.', 'error', 2000);
				    		sign.signByOther.queryResult.form.reset();//清空表单
				    		sign.signByOther.queryResultUpdate.form.reset();//清空表单
				    		  
				    	}else if(data.searchResult == 'authorization_error'){//authorization code is invalid 密码不正确
				    		//密码不正确
				    		Ext.ux.Toast.msg('查询失败', '对不起，您所录密码不正确，请重新录入密码.', 'error', 2000);
				    		sign.signByOther.queryResult.form.reset();//清空表单
				    		sign.signByOther.queryResultUpdate.form.reset();//清空表单
				    		  
				    	}
				    	
						
					},
				    exception: function(response) {//查询失败
				    	//查询失败
		                var json = Ext.decode(response.responseText);
		                Ext.ux.Toast.msg('查询失败', json.message, 'error', 2000);
		                sign.signByOther.queryResult.form.reset();//清空表单
		                sign.signByOther.queryResultUpdate.form.reset();//清空表单
		          	  	
	                }
				});	
				
			}else{
				Ext.ux.Toast.msg('警告', '请输入查询条件！', 'error', 2000);
			}
		}
	}]
});



//发件人信息
Ext.define('Foss.QuerySignByOtherInfo.Form.QuerySignResultForm',{
	extend:'Ext.form.Panel',
	id:'Foss_sign_signByOther_QuerySignResultForm_Id',
	// 指定容器的标题
	title: "发件人信息",
	frame:true,
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true, 
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//默认边距 间隔
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		id:'Foss_sign_signByOther_waybillNo_Id',//id
		name:'waybillNo', 
		xtype: 'hidden'
	},{
		id:'Foss_sign_signByOther_createOrgCode_Id',//id
		name:'createOrgCode',  // 目前只要查询createOrgCode就可以了 张东平会提供ajax在页面上展示createOrgName,不需要后台自己查询
		fieldLabel:'发货部门',//字段标题
		readOnly: true,
		columnWidth: 0.4
	},{
		id:'Foss_sign_signByOther_deliveryCustomerCode_Id',//id
		name:'deliveryCustomerCode', //发货客户编码
		readOnly: true,
		fieldLabel:'发货客户编码',//字段标题
		columnWidth: 0.4
	},{
		id:'Foss_sign_signByOther_deliveryCustomerName_Id',//id
		name:'deliveryCustomerContact', //发货人姓名
		readOnly: true,
		fieldLabel:'发货人姓名',//字段标题
		columnWidth: 0.4
	},{
		id:'Foss_sign_signByOther_deliveryCustomerMobilephone_Id',//id
		name:'deliveryCustomerMobilephone',//发货人手机 
		readOnly: true,
		fieldLabel:'发货人手机',//字段标题
		columnWidth: 0.4
	},{
		id:'Foss_sign_signByOther_deliveryCustomerPhone_Id',//id
		name:'deliveryCustomerPhone', //发货电话
		readOnly: true,
		fieldLabel:'发货电话',//字段标题
		columnWidth: 0.4
	},{
		id:'Foss_sign_signByOther_insuranceAmount_Id',//id
		name:'insuranceAmount', 
		readOnly: true,
		fieldLabel:'保险申明价值',//字段标题
		columnWidth: 0.4
	},{
		id:'Foss_sign_signByOther_totalFee_Id',//id
		name:'totalFee',    //总运费
		readOnly: true,
		fieldLabel:'总运费',//字段标题
		columnWidth: 0.4
	}]
});


//需要修改的收件人信息
Ext.define('Foss.QuerySignByOtherInfo.Form.QuerySignResultUpdateForm',{
	extend:'Ext.form.Panel',
	id:'Foss_sign_signByOther_QuerySignResultUpdateForm_Id',//id
	// 指定容器的标题
	title: "上传凭证",
	frame:true,
	//收缩
	collapsible: true,
	//fileUpload: true, 
    //method:'POST', 
    //enctype:'multipart/form-data', 
	//动画收缩
	animCollapse: true, 
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	defaults: {
		margin: '5 10 5 10',
		labelWidth: 100
	},
	defaultType: 'textfield',
	layout: 'column',
	items: [{
		name:'vo.searchWaybillSignByOtherDto.waybillNo',
		id:'Foss_sign_signByOther_waybillNoUpdate_Id',//id
		xtype:'hidden',
		dataIndex:'waybillNo',//waybill no
		allowBlank : false
	},{
		id:'Foss_sign_signByOther_receiveCustomerContact_Id',//id
		name:'vo.searchWaybillSignByOtherDto.receiveCustomerContact',
		fieldLabel:'真实收货人',//字段标题
		allowBlank : false,
		maxLength : 100, 
		columnWidth: 0.405
	},{
		id:'Foss_sign_signByOther_receiveCustomerPhone_Id',//id
		name:'vo.searchWaybillSignByOtherDto.receiveCustomerPhone', 
		fieldLabel:'收货人电话',//字段标题
		allowBlank : false,
		maxLength:25,
		columnWidth: 0.405
	},{
		xtype : 'button',
		text:'提交',
		disabled:!sign.signByOther.isPermission('signbyotherindex/signbyotherindexsubmitbutton'),
		hidden:!sign.signByOther.isPermission('signbyotherindex/signbyotherindexsubmitbutton'),
		cls:'yellow_button',
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		columnWidth:.08,
		handler:function(){
			var form = sign.signByOther.queryResultUpdate.getForm();//获得form
			
			var filearray = new Array();
			var fileCount = 0;
			sign.signByOther.fileUploadLoad.getStore().each(function(record){ 
				//id 'SWFUpload_0_1'
				//name  aa.txt
				//type  .txt
				//size
				//relativePath
				//status   new is 'add'
				
				filearray.push({
					'id' : record.get('id')/*,
					'name' : record.get('name'),
					'type' : record.get('type'),
					'size' : record.get('size'),
					'status' : record.get('status'),
					'relativePath' : record.get('relativePath')*/
				});
				fileCount ++;
			});
			
			if(!form.isValid()){
				return;
			}
			
			
			var contactman = form.findField('vo.searchWaybillSignByOtherDto.receiveCustomerContact').getValue();
			if(Ext.isEmpty(contactman)){//真实收货人为空
				Ext.ux.Toast.msg("提示信息","真实收货人为空，不允许提交");
				return;
			}
			
			var contactphone = form.findField('vo.searchWaybillSignByOtherDto.receiveCustomerPhone').getValue();
			if(Ext.isEmpty(contactphone)){//收货电话为空
				Ext.ux.Toast.msg("提示信息","收货电话为空，不允许提交");
				return;
			}
			
			var waybillno = form.findField('vo.searchWaybillSignByOtherDto.waybillNo').getValue();
			if(Ext.isEmpty(waybillno)){//运单信息为空
				Ext.ux.Toast.msg("提示信息","运单信息为空，不允许提交");
				return;
			}
			
			
			
			
			if(Ext.isEmpty(filearray)){//客户凭证为空
				Ext.ux.Toast.msg("提示信息","客户凭证为空，不允许提交");
				return;
			}
			
			
			
			//查询条件是否合法（非空等相关约束）
			if(sign.signByOther.queryResultUpdate.getForm().isValid()){
				
				Ext.Ajax.request({//ajax请求
				    url: sign.realPath('updateWaybillReceiverInfo.action'),
				   
				    method : 'POST',
					jsonData : {
						'vo' : {
							'searchWaybillSignByOtherDto' : {
								'waybillNo' : waybillno,
								'receiveCustomerContact' : contactman,
								'receiveCustomerPhone' : contactphone,
								'files' : filearray
							}
						}
					},
				    
				    success: function(response){
				    	//'提交成功
				    	Ext.ux.Toast.msg('提示', '提交成功！', 'ok', 1000);	
				    	
				    	
						
					},
				    exception: function(response) {
				    	//保存失败
		                var json = Ext.decode(response.responseText);
		                Ext.ux.Toast.msg('保存失败', json.message, 'error', 2000);
	                }
				});
				
				
			}else{
				Ext.MessageBox.alert('警告', '请输入更新后的收件人信息！');
			}
		}
    
   
    
	},{
		xtype : 'button',
		text : '取消',
		columnWidth:.08,
		handler : function() {
			sign.signByOther.queryResultUpdate.form.reset();
		}
	}
	// 客户凭证：发货客户更改收货人的电子凭证 上传文件需要统一的组件 需要添加
	
	
	]
});

Ext.define('Foss.QuerySignByOtherInfo.Grid.FileUploadGrid',{
	extend:'Deppon.ux.FileUploadGrid',
	/*id:'Foss_sign_signByOther_FileUploadGrid_Id',//id
	title: "上传凭证附件",
	uploadUrl: ContextPath.PKP_DELIVER+'/sign/uploadFiles.action',
	downLoadUrl: ContextPath.PKP_DELIVER+'/sign/downLoadFiles.action',
	delNewUrl: ContextPath.PKP_DELIVER+'/sign/deleteFile.action',
	imgUrl: ContextPath.PKP_DELIVER+'/sign/reviewImg.action'*/
	fileTypes: ['jpg','jpeg','gif','bmp','png'],
	modulePath : 'pkp-sign-signByOther',
	title: "上传凭证附件",
	uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
	// fileTypes: '*.*',
	downLoadUrl : ContextPath.PKP_DELIVER
			+ '/sign/downLoadFiles.action',
	deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
	imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
});


//查询条件
sign.signByOther.queryPanel = Ext.create('Foss.QuerySignByOtherInfo.Form.QuerySignByOtherForm');

//发件人信息
sign.signByOther.queryResult = Ext.create('Foss.QuerySignByOtherInfo.Form.QuerySignResultForm');

//需要修改的收件人信息
sign.signByOther.queryResultUpdate = Ext.create('Foss.QuerySignByOtherInfo.Form.QuerySignResultUpdateForm');

//上传文件
sign.signByOther.fileUploadLoad = Ext.create('Foss.QuerySignByOtherInfo.Grid.FileUploadGrid');

/**
 * extjs初始化
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		
		id: 'T_sign-signByOtherIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [sign.signByOther.queryPanel,//查询条件panel
		        sign.signByOther.queryResult,//查询结果panel
		        sign.signByOther.queryResultUpdate,
		        sign.signByOther.fileUploadLoad //上传文件
		        ],//需要更新明细信息的panel
		renderTo: 'T_sign-signByOtherIndex-body'
	});
});

