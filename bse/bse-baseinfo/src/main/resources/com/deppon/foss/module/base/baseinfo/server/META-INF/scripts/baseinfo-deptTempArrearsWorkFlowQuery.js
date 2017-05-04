baseinfo.lengthLimit = {workFlowNo:/^\d{6,7}$/};
//申请金额
baseinfo.applyAmount = {limit:/^\d{12}$/};
//用途
baseinfo.use = {use:'此工作流用于公司营业部、派送部部门临时欠款额度申请'};
//使用人
baseinfo.user = {title:'营业部经理、派送部经理'};
//附件说明
baseinfo.AccessoriesDescription = {explanation:'额度申请条件及需上传附件请查阅临时欠款额度申请规范'};
//其它说明
baseinfo.otherDescription = {descOne:'所申请额度为增加额度，申请人必须是部门经理',
		descTwo:'临时欠款额度申请规范，临时欠款额度调整(整车)申请模板'}; 
//资料链接
baseinfo.dataLinks = {linkOne:'临时欠款额度申请规范',linkTwo:'临时欠款额度调整(整车)申请模板'};

var fileDownLoad = function(url){
				
        if(!Ext.fly('downloadAttachFileForm')){
		    var frm = document.createElement('form');
		    frm.id = 'downloadAttachFileForm';
		    frm.style.display = 'none';
		    document.body.appendChild(frm);
		}
        
		Ext.Ajax.request({
			url:url,
			form: Ext.fly('downloadAttachFileForm'),
			method : 'POST',
			isUpload: true
		});
};

var jumpOne = function(){
		var url = arrearsStandardUrl; 
		fileDownLoad(url);
	};
var	jumpTwo = function(){
		var url = arrearsStandardExampleUrl; 
		fileDownLoad(url);
};


// 信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
		title:'警告',
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
};
	var requestJsonAjax = function(url, params, successFn, failFn) {
	    Ext.Ajax.request({
	        url: url,
	        jsonData: params,
	        async :  false,
	        success: function(response) {
	            var result = Ext.decode(response.responseText);
	            if (result.success) {
	                successFn(result);
	            } else {
	                failFn(result);
	            }
	        },
	        failure: function(response) {
	            var result = Ext.decode(response.responseText);
	            failFn(result);
	        },
	        exception: function(response) {
	            var result = Ext.decode(response.responseText);
	            failFn(result);
	        }
	    });
	};

/**
 * 工作流查询
 */

var changeLongToDate = function(value) {
    if (value != null) {
        var date = new Date(value);
        return date;
    } else {
        return null;
    }
};

// 工作流Model
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowQuery.workFlowEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'procInstId',// 工作流号
        type : 'string'
    },{
        name : 'workFlowName',// 工作流名称
        type : 'string'
    },{
        name : 'applyMan',// 申请人
        type : 'string'
    },{
    	name : 'applyManName', // 申请人名称
    	type : 'string'
    },{
    	name : 'currentApprover', // 当前审批人
    	type : 'string'
    },{
    	name : 'currentApproverName',// 当前审批人名称
    	type : 'string'
    },{
    	name : 'currentApproverDept',// 当前审批人所属部门
    	type : 'string' 
    },{
        name : 'applyTime',// 申请日期
        type : 'date',
	   	format : 'Y-m-d',
	   	defaultValue: null,
	    convert: changeLongToDate
    },{
        name : 'title',// 申请人职位
        type : 'string'
    },{
        name : 'active',// 是否有效
        type : 'string'
    },{
        name : 'applyManDept',// 所属部门
        type : 'string'
    },{
    	name : 'applyManDeptName',// 所属部门名称
    	type : 'string'
    },{
        name : 'createUser',// 创建人
        type : 'string'
    },{
        name : 'modifyUser',// 修改人
        type : 'string'
    },{
    	name : 'workFlowStatus',// 工作流状态
    	type : 'string'
    },{
        name : 'createDate',// 创建时间
        type : 'date',
        format : 'Y-m-d',
	   	defaultValue: null,
        convert:dateConvert

    },{
        name : 'modifyDate',// 修改时间
        type : 'date',
        format : 'Y-m-d',
	   	defaultValue: null,
        convert:dateConvert
    },{
        name : 'startCreateTime',// 起始时间
        type : 'date',
        format : 'Y-m-d',
	   	defaultValue: null,
        convert:dateConvert

    },{
        name : 'endCreateTime',// 终止时间
        type : 'date',
        format : 'Y-m-d',
	   	defaultValue: null,
        convert:dateConvert
    },{
    	name : 'applyType',// 申请类别
    	type : 'string'
    },{
    	name : 'applyAmount',// 申请金额
    	type : 'number'
    },{
    	name : 'note',      // 原因备注
    	type : 'string'
    },{
    	name : 'url',      // 存放上传文件的服务器url
    	type : 'string'
    },{
    	name:'applyTempDept',//申请人选中的申请部门
    	type:'string'
    }]
});

//临时欠款额度申请相关说明 form
Ext.define('Foss.baseinfo.deptTempArrearsAppInstructionsForm', {
	extend : 'Ext.form.Panel',
	title:'临时欠款额度申请相关说明',
	frame: true,
	height :300,	
	defaultType : 'textfield',
	collapsible:true,
	layout:{
      type: 'column'
  },
  viewState:null,											// 查看状态
  formRecord:null,										// 加载的record
	constructor : function(config){							// 构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);	
		me.items = [{
			fieldLabel:'用途',
			name:'use',
			columnWidth:.99,
			readOnly:true,
			value:baseinfo.use.use
		},{
			fieldLabel:'使用人',
			name:'applyer',
			readOnly:true,
			columnWidth:.99,
			value:baseinfo.user.title
		},{
			fieldLabel:'附件说明',
			name:'accessoriesDescription',
			readOnly:true,
			columnWidth:.99,
			value:baseinfo.AccessoriesDescription.explanation
		},{
			fieldLabel:'其他说明',
			name:'otherDescription',
			readOnly:true,
			columnWidth:.99,
			value:baseinfo.otherDescription.descOne
		},/*{
			      xtype:'container',
				    border:false,
				    columnWidth:.13,
		},{
			name:'othersDescription',
			readOnly:true,
			columnWidth:.8,
			value:baseinfo.otherDescription.descTwo
		},*/{
			xtype:'panel',
			columnWidth:1,
			html:'<span style="margin-left: 8px;width:120px;text-align:right;">资料链接:   </span><a href="javascript:void(0);" style="margin-left: 66px;" onclick="jumpOne()">临时欠款额度申请规范</a>&nbsp;&nbsp;'//<a href="javascript:void(0);" style="margin-left: 50px;"onclick="jumpTwo()">临时欠款额度调整申请模板</a>'				
		}];		
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
			margin : '8 10 5 10',
			labelWidth:120,
//			labelAlign:'right',
			width:500
	    };
	}	
});

//申请人基本信息 form
Ext.define('Foss.baseinfo.appBaseInfoForm', {
	extend : 'Ext.form.Panel',
	title:'申请人基本信息',
	collapsible:true,
	frame: true,
	height :120,	
	defaultType : 'textfield',
	layout:{
      type: 'table',
      columns: 3
    },
	constructor : function(config){							// 构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	// 获得表单元素
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:'申请人',
			name:'applyManName',
			colspan:1,
			//value:FossUserContext.getCurrentUser().empName,
			value:currentName,
			readOnly:true
		},{
			fieldLabel:'工号',
			name:'applyMan',
			colspan:1,
//			value:FossUserContext.getCurrentUser().userName,
			value:currentEmpCode,
			readOnly:true
		},{
			fieldLabel:'职位',
			name:'title',
			colspan:1,
//			value:FossUserContext.getCurrentUser().title,
			value:currentTitle,
			readOnly:true
		},{
			fieldLabel:'部门',
			name:'applyManDeptName',
			colspan:1,
//			value:FossUserContext.getCurrentDept().name,
			value:currentDeptName,
			readOnly:true
		}];
	}
});

//申请内容 form
Ext.define('Foss.baseinfo.appInfoForm', {
	extend : 'Ext.form.Panel',
	collapsible:true,
	title:'申请内容',
	frame: true,
	height :550,	
	layout:{
	       type: 'table',
	       columns: 3
	       },
	tempArrears:null,      
	constructor : function(config){							// 构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	// 获得表单元素
	getItems:function(config){
		var me = this;
		return [{
			xtype:'textfield',
		  width:700,
			colspan:3,
			readOnly:true,
			value:'请填写以下申请信息，提交成功后，系统会将您的申请自动进入审批工作流：（"*"为必填项）'
		},{
			fieldLabel:'申请部门',
			xtype:'commonsaledepartmentselector',
			name:'applyTempDept',
			allowBlank: false,
			colspan:3,
			listeners:{
				select:function(comb,records,obj){
					//若是选择的部门
					//获取部门编码
					var code =records[0].get('code');
					var params ={'attachementVo.selectedSalesDept':code};
					Ext.Ajax.request({
						url:baseinfo.realPath('queryMaxTempArrears.action'),
						params:params,
						success:function(response){
							var josn =Ext.decode(response.responseText);
							//获取部门的临时欠款额度
							var tempArrears =josn.attachementVo.maxTempArrears;
							if(!Ext.isEmpty(tempArrears)){
								//设置选中的部门的临时欠款额度
								me.getForm().findField('totalTempArrears').setValue((parseInt(tempArrears/100)).toString()+'元');
								me.tempArrears =tempArrears;
							}
						},
						exception:function(response){
							var josn =Ext.decode(response.responseText);
							baseinfo.showInfoMsg(josn.message);
						}
					});
				}
			}
		},{
			fieldLabel:'申请类型',
			xtype:'combo',
			name:'applyType',
			displayField: 'name',
			valueField: 'code',
			allowBlank: false,
			editable:false,
			colspan:3,
			store:Ext.create('Ext.data.Store', 
					         {fields: ['code', 'name'],
				                data: [{'code': 'SECTOR_REVENUE_GROWTH','name': '部门收入增长'},
				                      /* {'code': 'WHOLE_VEHICLE','name': '整车'},*/
				                       {'code': 'OTHER','name': '其它'}
				                	  ]
					         })
		},{
			fieldLabel:'申请金额',
			name:'applyAmount',
			width:255,
			labelWidth:100,
			decimalPrecision:0,
			step:1,
			allowBlank:false,
			maxValue:9999999999,
			minValue:0,
			xtype : 'numberfield',
			listeners:{
				blur:function(field){
					
	        		//申请增加金额的改变，总金额的改变
        			if(!Ext.isEmpty(field.getValue())){
        				var addAmount = field.getValue();
        				var totalAmount = new Number(addAmount) + new Number(currentTempArreas/100);
        				if(addAmount.toString().length > 7)
        				{
        					field.setValue(null);
        					baseinfo.showInfoMsg("申请金额位数不能超过七位！");
						      return;
        				}
        				if((parseInt(totalAmount)).toString().length > 8)
        				{
        					field.setValue(null);
        					baseinfo.showInfoMsg("申请之后部门总额度位数不能超过八位！");
						        return;
        				}
        				var oldAmount =0;
        				if(Ext.isEmpty(me.tempArrears)){
        					oldAmount =currentTempArreas;
        				}else{
        					oldAmount = me.tempArrears;
        				}
        				var totalAmout = 0;
        				if(!Ext.isEmpty(addAmount)){
        					totalAmout = parseInt(oldAmount/100) + parseInt(addAmount);
        					if((parseInt(totalAmout)).toString().length > 8)
            				{
            					field.setValue(null);
            					baseinfo.showInfoMsg("申请之后部门总额度位数不能超过八位！");
    						        return;
            				}
    						me.getForm().findField('totalTempArrears').setValue(totalAmout.toString()+'元');
    					}else{
    						if((parseInt(oldAmount/100)).toString().length > 8)
            				{
            					field.setValue(null);
            					baseinfo.showInfoMsg("申请之后部门总额度位数不能超过八位！");
    						        return;
            				}
    						me.getForm().findField('totalTempArrears').setValue((parseInt(oldAmount/100)).toString()+'元');
    					}
        			}else{
        				if(!Ext.isEmpty(me.tempArrears)){
        					if((parseInt(me.tempArrears/100)).toString().length > 8)
            				{
            					field.setValue(null);
            					baseinfo.showInfoMsg("申请之后部门总额度位数不能超过八位！");
    						        return;
            				}
        					me.getForm().findField('totalTempArrears').setValue((parseInt(me.tempArrears/100)).toString()+'元');
        				}
					}
        		}
        	}
		},{
			fieldLabel:'申请额度为增加额度，申请之后该部门额度为',
			name:'totalTempArrears',
			labelWidth:260,
			width:400,
			colspan:2,
			allowBlank: false,
			xtype:'textfield',
			value:(parseInt(currentTempArreas/100)).toString()+'元',
			readOnly:true
		},{
			width:750,
			height:120,
			colspan:3,
			xtype:'textareafield',
			maxLength:300,
			maxLengthText:'请不要输入超过300个字~',
			fieldLabel:'原因备注（不超过300个字）',
			allowBlank: false,
			name:'note'
		},Ext.create('Deppon.ux.FileUploadGrid', {
			title:'请上传附件',
			colspan:3,
			width:750,
			height:250,
			autoScroll: true,
			id:'fileUp_id',
			collapsible:true,
			allowBlank: false,
			file_upload_limit:5,
	    uploadUrl: baseinfo.realPath('uploadFiles.action'),
	    fileTypes: ['xls','xlsx','jpg','jpeg','bmp','png'],
	    downLoadUrl: baseinfo.realPath('downLoadFiles.action'),
	    deleteUrl: baseinfo.realPath('deleteFile.action'),
	    imgReviewUrl: baseinfo.realPath('reviewImg.action')
	    })];
	}
});

/**
 * 新增或修改临时欠款额度申请工作流信息win
 */
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowHandleWin',{
	extend : 'Ext.panel.Panel',
	title:'临时欠款额度申请工作流编辑信息',
	modal : true,
	resizable:false,
	closeAction : 'hide',
	height :1100,	
	layout :'auto',
	intructionForm:null,									// 临时欠款额度申请相关说明 Form
	baseInfoForm:null,										// 申请人基本信息Form
	editForm:null,											// 申请内容Form
	formRecord:null,										// 工作流实体
	fileUploadResultGrid : null,
  constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.intructionForm = Ext.create('Foss.baseinfo.deptTempArrearsAppInstructionsForm');
		me.baseInfoForm = Ext.create('Foss.baseinfo.appBaseInfoForm');
		me.editForm = Ext.create('Foss.baseinfo.appInfoForm');
		//var currentTempArreas = "${workFlowEntityVo.workFlowEntity.currentTempArrears}";
		me.items = [me.intructionForm,me.baseInfoForm,me.editForm];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	// 操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text:'取消',
			xtype:'button',
			id:'wp_cancell_id',
			handler :function(){
				me.hide();
			}
		},{
			text:'提交',
			xtype:'button',
			id:'wp_save_id',
			handler :function(){
				var editForm = me.editForm.getForm();
		    	// 实时校验的 结果是否通过,必填项是否填写并全部填写合法
				if(Ext.isEmpty(editForm.findField('applyType').getValue())){
					baseinfo.showInfoMsg("申请类型为空，必须填写！");
						return;
				}
				if(Ext.isEmpty(editForm.findField('applyAmount').getValue())){
					baseinfo.showInfoMsg("申请金额为空，必须填写！");
						return;
				}
				if(Ext.isEmpty(editForm.findField('note').getValue())){
					baseinfo.showInfoMsg("申请备注为空，必须填写！");
						return;
				}
			
				var note = editForm.findField('note').getValue();
				if(note.length > 300){
						Ext.ux.Toast.msg('提示', '请输入不多于300字的审批意见', "error", 2000);
						return;
				}
				
				var attachStore = me.editForm.queryById('fileUp_id').getStore();
				if(attachStore.count() == 0){
					baseinfo.showInfoMsg("附件内容不能为空！");
						return;
				}
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg("请检测数据必填项【*】是否填写完整且符合规范，有叉号的地方" +
		    				"表示输入有问题，将鼠标移动至有叉号的地方，会有详细提示！");
		    		    return;
		    	}
		    	//获得附件的store
//		    	var attachStore = me.editForm.items.items[5].items.items[0].store;
		    	var attIdList = new Array();
		    	attachStore.each(function(record){
		    		var id = record.getId();
		    		attIdList.push(id);
		    	  });
		    	me.formRecord = {'workFlowEntityVo.workFlowEntity.applyMan':currentEmpCode,
		    			'workFlowEntityVo.workFlowEntity.applyType':editForm.findField('applyType').getValue(),
		    			'workFlowEntityVo.workFlowEntity.totalTempArrears':parseInt(editForm.findField('totalTempArrears').getValue()),
		    			'workFlowEntityVo.workFlowEntity.addNewTempArrears':parseInt(editForm.findField('applyAmount').getValue()),
		    			'workFlowEntityVo.workFlowEntity.currentTempArrears':currentTempArreas/100,
		    			'workFlowEntityVo.workFlowEntity.note':editForm.findField('note').getValue(),
		    			'workFlowEntityVo.workFlowEntity.applyTempDept':editForm.findField('applyTempDept').getValue(),
		    			'workFlowEntityVo.attachIdList':attIdList
		    			};
	    		baseinfo.submitDeptTempArrearsWorkFlowHandleServices(me,me.formRecord);
			} 
		}];
	}
});

baseinfo.submitDeptTempArrearsWorkFlowHandleServices = function(win,params){
	var url = '',
	m_success = '起草临欠额度申请工作流成功,',
	m_failure = '起草临欠额度申请工作流失败！',
	m_dateError = '数据异常！',
	// 新增URL
	url = baseinfo.realPath('addNewDeptTempArrearsWorkFlow.action');
   Ext.Ajax.request({
	url:url,
	params:params,
	method:'post',
	success:function(response){
		var result = Ext.decode(response.responseText);
		if(Ext.isEmpty(result)){
			baseinfo.showInfoMsg(m_dateError);
		}else{// 操作返回值 1：成功；-1：失败
			if(result.workFlowEntityVo.returnInt === 1){
				win.editForm.getForm().getFields().each(function(value){
					  value.setDisabled(true);
					});
				win.queryById('wp_cancell_id').setDisabled(true);
				win.queryById('wp_save_id').setDisabled(true);
				win.editForm.getForm().owner.items.items[5].getDockedItems()[1].items.items[0].hide();
				win.editForm.getForm().owner.items.items[5].getDockedItems()[1].items.items[1].hide();
				baseinfo.showInfoMsg(m_success+'生成的工作流号为:'+result.model.procInstId);
				
			}else if(result.workFlowEntityVo.returnInt === -1){
				baseinfo.showInfoMsg(Ext.isEmpty(result.message)? m_failure:result.message);
			}
		}
	},
	exception:function(response){
		var result = Ext.decode(response.responseText);
		if(Ext.isEmpty(result)){
			baseinfo.showInfoMsg(m_dateError);
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	}
});
};
// 工作流起草
Ext.onReady(function() {
	Ext.QuickTips.init();

	var workFlowDraftWindow = Ext.create('Foss.baseinfo.deptTempArrearsWorkFlowHandleWin');

	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-deptTempArrearsWorkFlowDraft_content',
		frame : true,
		cls : 'autoHeight',
		bodyCls : 'autoHeight',
		toDoMsgGrid : null,
		border : false,
		renderTo:Ext.getBody(),
		items : [workFlowDraftWindow]
	});
});
