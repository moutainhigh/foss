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

//资料文件下载
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

//发送Ajax请求
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
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
	if (!Ext.isEmpty(value)) {
		return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
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
        format : 'Y-m-d H:i:s',
	   	defaultValue: null,
        convert:dateConvert

    },{
        name : 'modifyDate',// 修改时间
        type : 'date',
        format : 'Y-m-d H:i:s',
	   	defaultValue: null,
        convert:dateConvert
    },{
        name : 'approvalTime',// 审批时间
        type : 'date',
        format : 'Y-m-d H:i:s',
	   	defaultValue: null,
        convert:dateConvert
    },{
        name : 'startCreateTime',// 起始时间
        type : 'date',
        format : 'Y-m-d H:i:s',
	   	defaultValue: null,
        convert:dateConvert

    },{
        name : 'endCreateTime',// 终止时间
        type : 'date',
        format : 'Y-m-d H:i:s',
	   	defaultValue: null,
        convert:dateConvert
    },{
    	name : 'applyType',// 申请类别
    	type : 'string'
    },{
    	name : 'applyAmount',// 申请金额
    	type : 'number'
    },{
    	name : 'totalAmount',// 申请后总金额
    	type : 'number'
    },{
    	name : 'note',      // 原因备注
    	type : 'string'
    },{
    	name : 'url',      // 存放上传文件的服务器url
    	type : 'string'
    },{
    	name : 'pageStatus',//页面状态
    	type : 'string'
    },{
    	name : 'approvalViews',//审批意见
    	type : 'string'
    },{ name : 'approvalResult',//审批结果
    	type : 'string'
    },{
    	name : 'applyTempDept',//申请人 申请临时欠款额度部门
    	type :'string'
    },{
    	name : 'applyTempDeptName',//申请人 申请临时欠款额度部门
    	type :'string'
    }]
});

//审批工作流form
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowApprovalForm', {
	extend : 'Ext.form.Panel',
//	id:'Foss.deptTempArrearsWorkFlowApprovalForm_id',
	title:'审批工作流',
	frame: true,
	height :330,
	border:true,
	collapsible:true,
	defaultType : 'textfield',
	layout:{
      type: 'table',
      columns: 2
  },
  viewState:null,											// 查看状态
  formRecord:null,										// 加载的record
	constructor : function(config){							// 构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
			margin : '8 10 5 10',
			labelWidth:120
	    };
	},
	// 获得表单元素
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:'审批人',
			name:'currentApproverName',
			colspan:1,
			readOnly:true,
//			value:FossUserContext.getCurrentUser().empName
			value:currentAppName
		},{
			fieldLabel:'审批时间',
			xtype:'datefield',
			format:'Y-m-d H:i:s',
			colspan:1,
			allowBlank:false,
			readOnly:true,
			value:Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
			name:'approvalTime'
		},{
			width:900,
			height:120,
			colspan:2,
			xtype:'textareafield',
			maxLength:300,
			maxLengthText:'请不要输入超过300个字~',
			allowBlank:false,
			fieldLabel:'审批意见',
			name:'approvalViews'
		},{
			xtype:'radiogroup',
			colspan:2,
			width:300,
			allowBlank:false,
			fieldLabel :'审批结果',
			items: [{ boxLabel: '同意', name: 'approvalResult',inputValue: 'Y',checked: true},
			         {	xtype:'container',
				        border:false,
				        html:'&nbsp;'},
			        { boxLabel: '不同意', name: 'approvalResult',inputValue: 'N'}]
		
		},{
			xtype : 'container',
			colspan:2,
			defaultType:'button',
			layout:'column',
			items : [{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:0.5
			},{
				width: 75,
				height:29,
				text : '重置',
				handler : function() {
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:0.2
			},{
				xtype:'button',
				width: 75,
				text : '提交',
				cls:'yellow_button',
				handler : function() {
					var approvalForm = this.up('form').getForm();
					// 实时校验的 结果是否通过,必填项是否填写并全部填写合法
					  if(Ext.isEmpty(approvalForm.findField('approvalViews').getValue())){
						baseinfo.showInfoMsg("审批意见为空，此项为必填项！");
							return;
					  }
//					var appBaseInfoForm = Ext.getCmp('T_baseinfo-deptTempArrearsWorkFlowApproval_content').getWorkFlowApprovalOrViewWindow().getTabPanel()
//					.getdetailInfoTabPanel().getWorkFlowApplyInfoForm().getForm();
					var params = {};
		    		//审批意见
		    		if(approvalForm != null){
		    			params = {'workFlowEntityVo.workFlowEntity.currentApproverName':approvalForm.findField('currentApproverName').getValue(),
		    			'workFlowEntityVo.workFlowEntity.currentApprover':currentAppCode,
		    			'workFlowEntityVo.workFlowEntity.approvalTime':approvalForm.findField('approvalTime').getValue(),
		    			'workFlowEntityVo.workFlowEntity.approvalViews':approvalForm.findField('approvalViews').getValue(),
		    			'workFlowEntityVo.workFlowEntity.addNewTempArrears':addNewTempArrears,
		    			'workFlowEntityVo.workFlowEntity.approvalResult':approvalForm.findField('approvalResult').getValue()==true?'Y':'N',
		    			'workFlowEntityVo.workFlowEntity.procInstId':workFlowNum,
		    			'workFlowEntityVo.workFlowEntity.applyManDept':applyManDeptCode
		    			}
		    		}
		    	
		    		baseinfo.submitDeptTempArrearsWorkFlowHandleServices(me,params);
				}
			}]
		}];
	},
});

baseinfo.submitApprovalMessage=function(workFlowEntity){

	var url = '',
	m_success = '保存成功！',
	m_failure = '保存失败！',
	m_dateError = '数据异常！',
	workFlowEntityVo = {};
	workFlowEntityVo.workFlowEntity = workFlowEntity;
	// 新增URL
	url = baseinfo.realPath('approvalDeptTempArrearsWorkFlow.action');
   Ext.Ajax.request({
	url:url,
	jsonData:{'workFlowEntityVo':workFlowEntityVo},
	success:function(response){
		var result = Ext.decode(response.responseText);
		if(Ext.isEmpty(result)){
			baseinfo.showInfoMsg(m_dateError);
		}else{// 操作返回值 1：成功；-1：失败
			if(result.workFlowEntityVo.returnInt === 1){
				baseinfo.showInfoMsg(m_success);
//				win.hide();
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

//显示工作流号的form
Ext.define('Foss.baseinfo.showDeptTempArrearsWorkFlowNoForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height :30,	
	defaultType : 'textfield',
	//collapsible:true,
	readOnly:true,
	frame : false,
	formRecord:null,
	layout:'hbox',
	constructor : function(config){							// 构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{
			readOnly:true,
			width:370
		},{
			fieldLabel:'工作流号',
			cls : 'specilfield',
			name:'procInstId',
			value:workFlowNum,
			align: 'center', 
			readOnly:true
		}];
		me.callParent([cfg]);
	}
});

//工作流审批状态form（审批结束）
Ext.define('Foss.baseinfo.deptTempArrearsApprovalEndStatusForm', {
	extend : 'Ext.form.Panel',
	title:'工作流审批状态',
	frame: true,
	height :80,	
	defaultType : 'textfield',
	formRecord:null,
	collapsible:true,
	readOnly:true,
	layout:{
	      type: 'table',
	      columns: 2
	  },
	constructor : function(config){							// 构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{
			fieldLabel:'当前状态',
			name:'workFlowStatus',
			//value:'已结束',
			readOnly:true
		},{
			fieldLabel:'审批结果',
			name:'approvalResult',
			//value:approvalResult,
			readOnly:true
		}];
		me.callParent([cfg]);
	}
});

//工作流审批状态form（审批中）
Ext.define('Foss.baseinfo.deptTempArrearsApprovalStatusForm', {
	extend : 'Ext.form.Panel',
//	id : 'Foss.deptTempArrearsApprovalStatusForm_id',
	title:'工作流审批状态',
	frame: true,
	height :80,	
	defaultType : 'textfield',
	collapsible:true,
	formRecord:null,
//	readOnly:true,
	layout:{
	      type: 'table',
	      columns: 3
	  },
	constructor : function(config){							// 构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{
			fieldLabel:'当前状态',
			name:'workFlowStatus',
			//value:'应收账款小组会计审批中',
			readOnly:true
		},{
			fieldLabel:'当前审批人',
			name:'currentApproverName',
			//value : '龙鹏鹏',
			readOnly:true
		},{
			fieldLabel:'权责',
			name:'responsibilities',
			value:'审核是否符合临时欠款额度调整条件',
			width:360,
			readOnly:true
		}];
		me.callParent([cfg]);
	}
});

// 临时欠款额度申请相关说明 form
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
			html:'<span style="margin-left: 8px;width:120px;text-align:right;">资料链接:   </span><a href="javascript:void(0);" style="margin-left: 66px;" onclick="jumpOne()">临时欠款额度申请规范</a>&nbsp;&nbsp;'//<a href="javascript:void(0);" style="margin-left: 50px;"onclick="jumpTwo()">临时欠款额度调整(整车)申请模板</a>'				
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
	frame: true,
	formRecord:null,
	height :120,	
	defaultType : 'textfield',
	collapsible:true,
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
//			value:FossUserContext.getCurrentUser().empName,
			readOnly:true
		},{
			fieldLabel:'工号',
			name:'applyMan',
			colspan:1,
//			value:FossUserContext.getCurrentUser().userName,
			readOnly:true
		},{
			fieldLabel:'职位',
			name:'title',
			colspan:1,
//			value:FossUserContext.getCurrentUser().title,
			readOnly:true
		},{
			fieldLabel:'部门',
			name:'applyManDeptName',
			colspan:1,
//			value:FossUserContext.getCurrentDept().name,
			readOnly:true
		}];
	}
});

//申请内容 form
Ext.define('Foss.baseinfo.appInfoForm', {
	extend : 'Ext.form.Panel',
	title:'申请内容',
	frame: true,
	height :460,
	formRecord:null,
	collapsible:true,
	defaultType : 'textfield',
	//layout:'vbox',
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
			fieldLabel:'申请类型',
			name:'applyType',
			readOnly:true,
			colspan:3,
			value:applyType
		},{
			fieldLabel:'申请金额',
			width:200,
			name:'applyAmount',
			readOnly:true,
			colspan:1,
			value:addNewTempArrears
		},{
			fieldLabel:'申请人所申请部门',
			labelWidth:120,
			width:240,
			name:'applyTempDeptName',
			readOnly:true,
			colspan:1,
			value:applyTempDeptName
		},{
			fieldLabel:'申请额度为增加额度，申请之后该部门额度为',
			name:'totalTempArrears',
			labelWidth:260,
			width:400,
			allowBlank: false,
			colspan:1,
			xtype:'textfield',
			value:totalTempArrears.toString()+'元',
			readOnly:true
		},{
			xtype:'textareafield',
			fieldLabel:'原因备注',
			name:'note',
			width:750,
			height:100,
			colspan:3,
			readOnly:true,
			value:note
		},Ext.create('Deppon.ux.FileUploadGrid', {
			title:'已上传附件',
			colspan:3,
			autoScroll: true,
			name:'attachment',
			width:750,
			height:200,
//			collapsible:true,
			allowBlank: false,
			file_upload_limit:4,
	        uploadUrl: baseinfo.realPath('uploadFiles.action'),
	        fileTypes: ['xls','xlsx','jpg','jpeg','bmp','png'],
	        downLoadUrl: baseinfo.realPath('downLoadFiles.action'),
	        deleteUrl: baseinfo.realPath('deleteFile.action'),
	        imgReviewUrl: baseinfo.realPath('reviewImg.action')
	        })];
	}
});

//工作流审批记录model
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowHandleRecord.workFlowHandleResultEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'approvedate',// 处理时间
        type : 'date',
        format:'Y-m-d H:i:s',
	   	defaultValue: null,
	    convert: changeLongToDate
    },{
        name : 'approver',// 处理人
        type : 'string'
    },{
        name : 'isagree',// 处理结果
        type : 'string'
    },{
    	name : 'approvever', // 处理意见
    	type : 'string'
    },{
    	name : 'duty', //权责
    	type : 'string'
    },{
    	name : 'procInstId', //工作流号
    	type : 'string'
    }]
});

//工作流审批记录STORE
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowTrackRecordStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.deptTempArrearsWorkFlowHandleRecord.workFlowHandleResultEntity',
	pageSize:10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryWorkFlowTrackRecordsView.action'),
		reader : {
			type : 'json',
			root : 'workFlowEntityVo.approvalList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
	    beforeload: function(store, operation, eOpts) {    	

	            Ext.apply(operation, {
	                params: {
                	'workFlowEntityVo.workFlowEntity.procInstId': workFlowNum,
	                }
	            });
	    }
	}
});

//工作流审批记录grid
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowTrackRecordGridPanel', {
	extend: 'Ext.grid.Panel',
	frame: true,
	autoScroll: true,
	height:200,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    //selModel : Ext.create('Ext.selection.CheckboxModel'),
	stripeRows : false, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
	    	text : '处理时间',//处理时间
	    	dataIndex : 'approvedate',
	    	flex : 0.6
	    },{
	    	text : '处理人',//处理人
	    	dataIndex : 'approver'
	    },{
	    	text : '处理结果',//处理结果
	    	dataIndex : 'isagree',
	    	renderer :function(value){
				switch(value){
					case '0' :
						return '同意';
					break;
					case '1' :
						return '不同意';
					break;
					case '2' :
						return '起草'; 	
					break;
					case '4' :
						return '退回';
					break;
					case '5' :
						return '同意并结束';
					break;
					case '6' :
						return '收回';
					break;
					case '9' :
						return '业务退回';
					break;
				}
			}
	    },{
	    	text : '处理意见',//处理意见
	    	dataIndex : 'approvever',
	    	flex : 0.8
	    },{
	    	text : '权责',//权责
	    	dataIndex : 'duty',
	    	flex : 2.0
	    },{
	    	text : '工作流号',//工作流号
	    	dataIndex : 'procInstId',
	    	hidden : true
	     }];
		me.store = Ext.create('Foss.baseinfo.deptTempArrearsWorkFlowTrackRecordStore');
		me.callParent([cfg]);
	}
});

//工作流审批tab页
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowTrackRecordTabPanel', {
	extend : 'Ext.tab.Panel',
//	flex : 1,
	//cls : 'innerTabPanel',
	defaults:{
      autoHeight:true,  
      cls:'tab-panel-item'  
    },
  //width:'auto',
//	columnWidth : .99,
	id:'Foss.deptTempArrearsWorkFlowTrackRecordTabPanel_Id',
	activeTab : 0,// 默认激活第一个Tab页
	autoScroll : false,
	pageState:null,//页面状态
	currentWorkFlowState:null,//当前工作流状态
	formRecord:null,
	height :1100,	
	//tab 切换事件
	listeners:{
	      tabchange:function(t,p){//其中参数p指的是当前活动的panel
	    	  var me = this;
	      		if(p.title == '工作流跟踪'){ 
	      			var _store = me.getTrackRecordTabPanel().getStore();
	      			if(_store.count()==0){
	      				_store.load();
	      			}
	            }/*else if(p.title == '工作流图示'){
	            	if(Ext.isEmpty(p.items) || p.items.length == 0){
	            		p.items = [this.getWorkFlowTrackIndex()];
	            	}
	            }*/
			}
	},
    
	//详细信息Panel
	detailInfoTabPanel: null,
	getdetailInfoTabPanel : function() {
		if(Ext.isEmpty(this.detailInfoTabPanel)){
			this.detailInfoTabPanel = Ext.create('Foss.baseinfo.detailInfoTabPanel');
		}
		if(pageState === 'VIEW'){
			if(workFlowApprovalState === 'WF_STATUS_APPROVALING'){
				this.detailInfoTabPanel.getWorkFlowApprovaledStatusForm().hide();
			}else{
				this.detailInfoTabPanel.getWorkFlowApprovalingStatusForm().hide();
			}
		}else if(pageState === 'APPROVAL'){
			this.detailInfoTabPanel.getWorkFlowApprovalingStatusForm().hide();
			this.detailInfoTabPanel.getWorkFlowApprovaledStatusForm().hide();
		}
		return this.detailInfoTabPanel;
	},
	//流程图u
	workFlowTrackIndex: null,
	getWorkFlowTrackIndex : function() {
		if(Ext.isEmpty(this.workFlowTrackIndex)){
			  this.workFlowTrackIndex = Ext.create('Foss.baseinfo.workFlowTrackIndex');
		}
		return this.workFlowTrackIndex;
	},
	trackRecordTabPanel: null,
	getTrackRecordTabPanel : function() {
		if(Ext.isEmpty(this.trackRecordTabPanel)){
			this.trackRecordTabPanel = Ext.create('Foss.baseinfo.deptTempArrearsWorkFlowTrackRecordGridPanel');
		}
		this.trackRecordTabPanel.getView().on('render', function(view) {
		    view.tip = Ext.create('Ext.tip.ToolTip', {
		        // 所有的目标元素
		        target: view.el,
		        // 每个网格行导致其自己单独的显示和隐藏。
		        delegate: view.itemSelector,
		        // 在行上移动不能隐藏提示框
		        trackMouse: true,
		        // 立即呈现，tip.body可参照首秀前。
		        renderTo: Ext.getBody(),
		        listeners: {
		            // 当元素被显示时动态改变内容.
		            beforeshow: function updateTipBody(tip) {
		                tip.update(view.getRecord(tip.triggerElement).get('approvever'));
		            }
		        }
		    });
		});
		return this.trackRecordTabPanel;
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [{
			title : '详细信息',  //详细信息
			tabConfig : {
				width : 100
			},
			items :[{xtype:'container',
								html:'&nbsp;',
								height:40},this.getdetailInfoTabPanel()]
		}, {
			title : '工作流跟踪',  //工作流跟踪
			tabConfig : {
				layout:'fit',
				width : 100
			},
			items : [{xtype:'container',
								html:'&nbsp;',
								height:40},this.getTrackRecordTabPanel()]
		}, {
			title : '工作流图示',  //工作流视图
			tabConfig : {
				layout:'fit',
				width : 100
			},
		    items:[{xtype:'container',
								html:'&nbsp;',
								height:40},this.getWorkFlowTrackIndex()]
		}];
		this.callParent(arguments);
	}
});

// 工作流流程图panel
Ext.define('Foss.baseinfo.workFlowTrackIndex', {
	extend : 'Ext.panel.Panel',
	height : 1200,
	frame : true,
	layout : 'auto',
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		var url = baseinfo.realPath('getWorkFlowFlowChart.action');
		me.items = [{
			html : "<iframe width=950 height=800 src='"+url+"?workFlowEntityVo.workFlowEntity.procInstId="+workFlowNum+"'/>"
		}]
		me.callParent([cfg]);
	}
});
// 定义一个工作流详细信息的panel
Ext.define('Foss.baseinfo.detailInfoTabPanel', {
	extend : 'Ext.panel.Panel',
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	height : 1000,
	frame : true,
	layout : 'auto',
 	//工作流审批中状态form
 	workFlowApprovalingStatusForm:null,
    getWorkFlowApprovalingStatusForm:function (){
    	if(Ext.isEmpty(this.workFlowApprovalingStatusForm)){
    		 this.workFlowApprovalingStatusForm = Ext.create('Foss.baseinfo.deptTempArrearsApprovalStatusForm');
    		 var workAppStatus = null;
    		 if(workFlowApprovalState === 'WF_STATUS_APPROVALING'){
    			 workAppStatus = '应收账款小组会计审批中';
    		 }else if(workFlowApprovalState === 'WF_STATUS_APPROVAL_OVER'){
    			 workAppStatus = '已结束';
    		 }
    		 this.workFlowApprovalingStatusForm.getForm().findField('workFlowStatus').setValue(workAppStatus);
    		 this.workFlowApprovalingStatusForm.getForm().findField('currentApproverName').setValue(currentAppName);
    	}
    	return this.workFlowApprovalingStatusForm;
    },

 	//工作流审批结束状态form
 	workFlowApprovaledStatusForm:null,
    getWorkFlowApprovaledStatusForm:function (){
    	if(Ext.isEmpty(this.workFlowApprovaledStatusForm)){
    		this.workFlowApprovaledStatusForm = Ext.create('Foss.baseinfo.deptTempArrearsApprovalEndStatusForm');
    		this.workFlowApprovaledStatusForm.getForm().findField('approvalResult').setValue(approvalResult);
    		 var  workAppStatus = null;
    		 if(workFlowApprovalState === 'WF_STATUS_APPROVALING'){
	  					workAppStatus = '应收账款小组会计审核中';
					}else if(workFlowApprovalState === 'WF_STATUS_APPROVAL_OVER'){
							workAppStatus = '已结束';
			   }
    		 this.workFlowApprovaledStatusForm.getForm().findField('workFlowStatus').setValue(workAppStatus);
    	}
    	return this.workFlowApprovaledStatusForm;
    },
    //申请人基本信息form
    workFlowApplierBaseInfoForm:null,
    getWorkFlowApplierBaseInfoForm:function(){
    	if(Ext.isEmpty(this.workFlowApplierBaseInfoForm)){
    		this.workFlowApplierBaseInfoForm = Ext.create('Foss.baseinfo.appBaseInfoForm');
    	}
			this.workFlowApplierBaseInfoForm.getForm().findField('applyManName').setValue(applyManName);
			this.workFlowApplierBaseInfoForm.getForm().findField('applyMan').setValue(applyMan);
  		    this.workFlowApplierBaseInfoForm.getForm().findField('title').setValue(title);
			this.workFlowApplierBaseInfoForm.getForm().findField('applyManDeptName').setValue(applyManDeptName);
    	return this.workFlowApplierBaseInfoForm;
    },
    //申请内容form
    workFlowApplyInfoForm:null,
    getWorkFlowApplyInfoForm:function(){
    	if(Ext.isEmpty(this.workFlowApplyInfoForm)){
    		this.workFlowApplyInfoForm = Ext.create('Foss.baseinfo.appInfoForm');
    	}
    	return this.workFlowApplyInfoForm;
    },
    //临时欠款额度相关申请说明
    deptTempArrearsAppInstructionsForm:null,
    getDeptTempArrearsAppInstructionsForm:function(){
    	if(Ext.isEmpty(this.deptTempArrearsAppInstructionsForm)){
    		this.deptTempArrearsAppInstructionsForm = Ext.create('Foss.baseinfo.deptTempArrearsAppInstructionsForm');
    	}
    	return this.deptTempArrearsAppInstructionsForm;
    },

   //显示工作流号的form
    showWorkFlowNoForm:null,
    getShowWorkFlowNoForm:function(){
    	if(Ext.isEmpty(this.showWorkFlowNoForm)){
    		this.showWorkFlowNoForm = Ext.create('Foss.baseinfo.showDeptTempArrearsWorkFlowNoForm');
    		if(!Ext.isEmpty(this.formRecord)){
    			this.showWorkFlowNoForm.getForm().findField('procInstId').setValue(this.formRecord.workFlowNo);
    		}
    	}
    	return this.showWorkFlowNoForm;
    },
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [me.getShowWorkFlowNoForm(),
		me.getWorkFlowApprovaledStatusForm(),
		me.getWorkFlowApprovalingStatusForm(),
		me.getWorkFlowApplierBaseInfoForm(),
		me.getWorkFlowApplyInfoForm(),
		me.getDeptTempArrearsAppInstructionsForm()];
		me.callParent([cfg]);
	}
});
/**
 * 新增或修改临时欠款额度申请工作流信息win
 */
Ext.define('Foss.baseinfo.deptTempArrearsWorkFlowApprovalWin',{
	extend : 'Ext.panel.Panel',
	title:"工作流",
	//closable : true,
	//modal : true,
	autoHeight : true,
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	layout : 'auto',
	resizable:false,
	height :1500,	
	listeners:{
		beforehide:function(me){
		}
	},
	tabPanel:null,	
    getTabPanel:function(){
    	if(Ext.isEmpty(this.tabPanel)){
    		this.tabPanel = Ext.create('Foss.baseinfo.deptTempArrearsWorkFlowTrackRecordTabPanel');
    	}
    	return this.tabPanel;
    },									     // 查看或审批tabPanel
	viewState:null,											// 页面状态
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.tabPanel = Ext.create('Foss.baseinfo.deptTempArrearsWorkFlowTrackRecordTabPanel');
//		me.tabPanel.pageState = pageState;//页面状态
//		me.tabPanel.currentWorkFlowState = workFlowApprovalState;//工作流当前状态
		me.viewState = pageState;
		if(me.viewState === 'VIEW'){
			me.items = [me.tabPanel];
		}else{
			me.workFlowApprovalForm = Ext.create('Foss.baseinfo.deptTempArrearsWorkFlowApprovalForm');
			me.items = [me.workFlowApprovalForm,me.tabPanel];
		}
		//TODO
		var detailForm = me.tabPanel.getdetailInfoTabPanel()
							.getWorkFlowApplyInfoForm().getForm();
		var inxStore = detailForm.owner.items.items[5].getStore();
		//灰掉按钮
//		detailForm.owner.items.items[3].getDockedItems()[0].items.items[0].setDisabled(true);
//		detailForm.owner.items.items[3].getDockedItems()[0].items.items[1].setDisabled(true);
		detailForm.owner.items.items[5].getDockedItems()[0].items.items[0].hide();
		detailForm.owner.items.items[5].getDockedItems()[0].items.items[1].hide();
		 var attachementVo = new Object();
		 attachementVo.attachementEntity = new Object();
         attachementVo.attachementEntity.relatedKey = relatedKey;
         var jsonData = {'attachementVo':attachementVo}; 
         
         var successFn = function(response){
             if (Ext.isEmpty(response.attachementVo.attachementEntityList)) {
             	return;
             }
             inxStore.loadData(response.attachementVo.attachementEntityList);
         };
         var exceptionFn = function(response){
				var result = Ext.decode(response.responseText);
				if(Ext.isEmpty(result)){
					baseinfo.showInfoMsg(m_dateError);
				}else{
		   baseinfo.showInfoMsg(result.message);
		}
	   };
       var  url = baseinfo.realPath('queryAttachmentContent.action');
      baseinfo.requestJsonAjax(url, jsonData, successFn,exceptionFn);
		
		me.callParent([cfg]);
	}
});

//提交审批意见的请求
baseinfo.submitDeptTempArrearsWorkFlowHandleServices = function(win,params){
	var url = '',
	m_success = '提交成功！',
	m_failure = '提交失败！',
	m_dateError = '数据异常！',
	// 提交审批意见的URL
   url = baseinfo.realPath('submitWorkFlowApprovalViews.action');
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
				baseinfo.showInfoMsg(m_success);
				win.hide();
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

//工作审批或查看
Ext.onReady(function() {
	Ext.QuickTips.init();
 var workFlowApprovalOrViewWindow = Ext.create('Foss.baseinfo.deptTempArrearsWorkFlowApprovalWin');
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-deptTempArrearsWorkFlowApproval_content',
		frame : true,
		cls : 'autoHeight',
		bodyCls : 'autoHeight',
		toDoMsgGrid : null,
		border : false,
		renderTo:Ext.getBody(),
		getWorkFlowApprovalOrViewWindow:function(){
			return workFlowApprovalOrViewWindow;
		},
		items : [workFlowApprovalOrViewWindow]
	});
});
