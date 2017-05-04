/**
 *去出字符串前后空格 
 **/
trim=function() {

    return this.replace(/(^\s*)|(\s*$)/g,'');
}
/**校验包号的正确性**/
function validatePackage(packageNo){
	
	Ext.Ajax.request({
		url : load.realPath('validatePackageNo.action'),
		params : {
				   'printPackageVo.packageNo':packageNo
				  },
		success : function(response) {
			var result = Ext.decode(response.responseText);
			var isRight=result.printPackageVo.isRight;
			if(isRight!='Y'){
				Ext.getCmp('packageNo').reset();
				Ext.getCmp('numbers').setReadOnly(false);
				Ext.getCmp('numbers').reset();
				Ext.Msg.alert("提示","此包号不存在或已撤销");
				}
			},
		exception:function (response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert("提示",result.message)
		
		}
	});
	
}
/**
 * 
 *打印包标签的方法 
 ***/
function print(packageNo,empCode){
	var vurl = "http://localhost:8077/print";
		var packageNo=packageNo.trim();
		Ext.data.JsonP.request({
		    url: vurl,
		    callbackKey: 'callback',
		    async : false,//同步调用
		    params: {
		    lblprtworker:'PackageLabelPrintWorker',
		     barCode: packageNo 
		    },
			callback: function() {
				//回调函数，不管请求成功与否都执行
				//alert("callback");
			},   	    
		    success: function(result, request) {
		    		Ext.Ajax.request({
					url: load.realPath('addPackagePrintLog.action'),
					params : {
						   'printPackageVo.packageNo':packageNo,
						   'printPackageVo.empCode':empCode
						  },
					success:function(response){
//						Ext.ux.Toast.msg('提示', '打印成功', 'ok', 3000);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert('提示',  result.message);					
						}
		         })	
		    	
		    },
		    failure : function (result, request) {
				//Ext.ux.Toast.msg('提示', '打印失败', 'error', 3000);
				Ext.ux.Toast.msg('提示', '打印失败', 'error', 3000);
		    }
		});	
	
}


/**打印包**/
Ext.define('Foss.printPackageLabel.printForm',{
	extend:'Ext.form.Panel',
	title : '打印包标签',//“打印包标签”
	frame : true,
	collapsible : true,
	height : 400,
	animCollapse : true,
	defaults:{
		margin :'15 5 5 0',
		labelWidth :85,
		colspan : 1 ,
		width:300,
		height:40
	},
	defaultType:'textfield',
	layout:'column',
	items:[{
			fieldLabel:'包号',
			name : 'packageNo',
			id:'packageNo',
			listeners:{
				  'blur': function(cmp,eObject,eOpts){
					  
					  if(cmp.getValue()!=null&&cmp.getValue().trim()!=''){
						  Ext.getCmp('numbers').setValue(1);
						  Ext.getCmp('numbers').setReadOnly(true);
						  var packageNo=cmp.getValue();
						  validatePackage(packageNo);//检查包号是否正确
						  
					  }else{
						  Ext.getCmp('numbers').setReadOnly(false);
					  }
				  }
				  
			  }
		},{
			fieldLabel:'用户工号',
			name : 'empCode',
			allowBlank:false
		},{
			inputType:'password',
			fieldLabel:'密 码',
			name : 'empPassword',
			allowBlank:false
		},{
			fieldLabel:'份数',
			name : 'numbers',
			id:'numbers',
			allowBlank:false
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			width:300
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			width:300
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			width:300
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			width:300
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			width:300
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			width:300
		},{
		  text:'打印指定包标签 ',
		  xtype:'button',
		  id:'printlabelButton',
		  width:200,
		  cls:'yellow_button',
		  handler:function(){
			  var form=this.up('form').getForm();
			  if(!form.isValid()){
				  return false;
			  }
			  var parmars=form.getValues();
			  var packageNo=parmars.packageNo;
			  if(packageNo==null||packageNo.trim()==''){
				  Ext.Msg.alert('提示','请输入包号');
				  return;
			  }
			  var empCode=parmars.empCode;
			  var empPassword=parmars.empPassword;
			  var numbers=parmars.numbers;
			  
			  Ext.getCmp('printlabelButton').setDisabled(true);//不可点击
			 setTimeout(function(){
				 Ext.getCmp('printlabelButton').setDisabled(false);//恢复可点击
				      }, 3000);
			  //校验用户密码和包号
			  Ext.Ajax.request({
					url : load.realPath('validation.action'),
					params : {
							   'printPackageVo.packageNo':packageNo.trim(),
							   'printPackageVo.empCode':empCode,
							   'printPackageVo.empPassword':empPassword
							  },
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var isRight=result.printPackageVo.isRight;
						
						if(isRight=='Y'){
							print(packageNo.trim());
							}
						},
					exception:function (response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert('提示', result.message);
					
					}
				});
			  
		  }
		 },
		  	{
			  text:'打印',//打印
			  xtype:'button',
			  id:'printButton',
			  width:150,
			  cls:'yellow_button',
			  handler:function(){
				  
				  var form=this.up('form').getForm();
				  if(!form.isValid()){
					  return false;
				  }
				  var parmars=form.getValues();
				  var packageNo=parmars.packageNo;
				  var empCode=parmars.empCode;
				  var empPassword=parmars.empPassword;
				  var numbers=parmars.numbers; 
				  if(packageNo!=null&&packageNo.trim()!=''){
					  Ext.Msg.alert('提示', '如需打印指定包，请点击打印指定包标签');//'提示','如需打印指定包，请点击打印指定包标签'
					  return;
				  }
				 if(numbers>50){
					 Ext.Msg.alert('提示','打印份数不能超过50');
					  return;
				 }
				 
				 Ext.getCmp('printButton').setDisabled(true);
				 setTimeout(function(){
					 Ext.getCmp('printButton').setDisabled(false);
					      }, 3000);
				 //校验用户名和密码，并打印
				  Ext.Ajax.request({
						url : load.realPath('creatPackageNo.action'),
						params : {
								   'printPackageVo.empCode':empCode,
								   'printPackageVo.empPassword':empPassword,
								   'printPackageVo.numbers':numbers
								  },
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var isRight=result.printPackageVo.isRight;
							if(isRight=='Y'){
								var packageNos=new Array();
								packageNos=result.printPackageVo.printPackageNoList;
								for(i=0;i<packageNos.length;i++){
									print(packageNos[i],empCode);
								}
								
							}
						},
						exception:function (response){
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert("提示", result.message);
						
						}
					});
			  }
		  	}
		]});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var printForm = Ext.create('Foss.printPackageLabel.printForm');
	Ext.create('Ext.panel.Panel',{
		id: 'T_load-printPackageLabelIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items: [printForm],
		renderTo: 'T_load-printPackageLabelIndex-body'
	});
	
});