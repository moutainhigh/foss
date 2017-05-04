//------------------------------------常量----------------------------------
baseinfo.operatorCount = {defaultV:0,successV:1,failureV:-1};		//操作返回值 1为成功，-1为失败
baseinfo.delAgencyType = 'MANY';									//删除的类型，批量
baseinfo.delType = 'MANY';											//删除的类型，批量
baseinfo.viewState = {add:'ADD',update:'UPDATE',view:'VIEW'};		// 查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
baseinfo.booleanType = {yes:'Y',no:'N'};							//booleanType  对应后台常量 "布尔类型"
baseinfo.effectiveState = {active:'Y',inactive:'N'};				//booleanType  对应后台常量 "生效/未生效"
baseinfo.booleanStr = {yes:'true',no:'false'};						//booleanStr   从复选框中得到值
baseinfo.operateType = {save:'SAVE'};								//标识 是否 保存操作
baseinfo.levelType = {p:'PARENT',c:'CHILDREN'};						//标识 是父容器还是子容器
/**.
 * <p>
 * AJAX请求<br/>
 * <p>
 * @author LIXUEXING
 * @param url请求地址,PARAMS参数,successFn调用成功,
 * 		  exceptionFn调用异常,FAILFN调用失败,ASYNC是否异步
 * @时间 2012-12-07
 */
baseinfo.requestAjaxJson = function(url,params,successFn,exceptionFn,failFn,async)
{
	Ext.Ajax.request({
		url:url,
		async:Ext.isEmpty(async)?true:false,//默认是异步
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				exceptionFn(result);
			}
		},
		exception:function(response){
			exceptionFn(Ext.decode(response.responseText));
		}
//		,failure:function(response){//平台已拦截
//			var result = Ext.decode(response.responseText);
//			failFn(result);
//		}
	});
};
/**.
 * <p>
 * 填值方法<br/>
 * <p>
 * @author LIXUEXING
 * @param form[]需要加载数据的form,formRecord[]需要加载的数据model
 * 		  grid[]需要加载数据的grid,girdData[]需要加载的数据data
 * @时间 2012-12-07
 */
baseinfo.formReset = function(form,formRecord,grid,girdData){
	if(!Ext.isEmpty(form)&&!Ext.isEmpty(formRecord)){
		Ext.Array.each(form, function(name, index, countriesItSelf) {
			form[index].loadRecord(formRecord[index]);
		});
	}
	if(!Ext.isEmpty(grid)){
		Ext.Array.each(grid, function(name, index, countriesItSelf) {
			if(Ext.isEmpty(girdData)){
				grid[index].store.loadData([]);
			}else{
				grid[index].store.loadPage(1);
			}
		});
	}
};
/**.
 * <p>
 * form表单所有元素 设置 readOnly值<br/>
 * <p>
 * @author LIXUEXING
 * @param form需要设值的表单，flag表单readOnly值
 * @时间 2012-12-13
 */
baseinfo.formSetReadOnly = function(flag,form) {
	var arr = form.items.items;
	if(!Ext.isEmpty(arr)){
		for(var i = 0;i<arr.length;i++){
			arr[i].setReadOnly(flag);
		}
	}
};
baseinfo.formFieldSetReadOnly = function(flag,form) {
	var arr = form.query('field');
	if(!Ext.isEmpty(arr)){
		for(var i = 0;i<arr.length;i++){
			arr[i].setReadOnly(flag);
		}
	}
};
/**.
 * <p>
 * 消息提示框 ，无国际化<br/>
 * <p>
 * @author LIXUEXING
 * @param message,fun
 * @时间 2012-12-13
 */
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
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

//	setTimeout(function(){
//      Ext.Msg.hide();
//  }, 3000);
};

/**.
 * <p>
 * 查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1<br/>
 * <p>
 * @author LIXUEXING
 * @param message,fun
 * @时间 2012-12-13
 */
baseinfo.operateWinBtn = function(win,viewState,operateType){
	//查看状态下 只有 取消按钮可用 [添加网点,取消]按钮分别占 0和1
	if(baseinfo.viewState.view === viewState){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i != 2);
		}
	}else if(!Ext.isEmpty(operateType)&&baseinfo.operateType.save === operateType){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i > 2);
		}
	}
};
/**.
 * <p>
 * 覆盖Ext.form.RadioGroup的setValue方法<br/>
 * item.getRawValue全为false
 * <p>
 * @author 张斌
 * @时间 2012-3-25
 */
Ext.override(Ext.form.RadioGroup, {   
    setValue: function(v){
        if (this.rendered)    
            this.items.each(function(item){   
                item.setValue(item.inputValue == v);   
            });   
        else {
            for (var k = 0;k<this.items.items.length;k++) {   
                this.items.items[k].setValue(this.items.items[k].inputValue == v);   
            }   
        }   
    }   
}); 


/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author 张斌
 * @时间 2012-8-31
 */
baseinfo.getStore = function(storeId,model,fields,data) {
	var store = null;
	if(!Ext.isEmpty(storeId)){
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if(Ext.isEmpty(data)){
		data = [];
	}
	if(!Ext.isEmpty(model)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    model:model,
			    data:data
		     });
		}
	}
	if(!Ext.isEmpty(fields)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    fields:fields,
			    data:data
		     });
		}
	}
	return store;
};
//Ajax请求--json
baseinfo.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};

//Ajax请求--文件表单的请求
baseinfo.requestFileUploadAjax = function(url,form,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		form:form,
		isUpload:true,
		success:function(form, action){
			var result = action.result;
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(form, action){
			var result = action.result;
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};

//Ajax请求--非json字符串
baseinfo.requestAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		params:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};



/**.
 * <p>
 * 设置元素为readOnly<br/>
 * <p>
 * @param readOnlyIdList  设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setReadOnly = function(readOnlyIdList){
     for(var i=0;i<readOnlyIdList.length;i++){
    	 Ext.getCmp(readOnlyIdList[i]).setReadOnly(true);
    	 Ext.getCmp(readOnlyIdList[i]).addCls('readonly');
     }
};

/**.
 * <p>
 * 设置元素为隐藏并且销毁，使其不在校验<br/>
 * <p>
 * @param hiddenIdList  设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setHiddenAndDestroy = function(hiddenIdList){
     for(var i=0;i<hiddenIdList.length;i++){
    	 Ext.getCmp(hiddenIdList[i]).hide();
    	 Ext.getCmp(hiddenIdList[i]).destroy( );
     }
};
/**.
 * <p>
 * 设置元素为隐藏<br/>
 * <p>
 * @param hiddenIdList  设置为readOnly的元素ID数组
 * @author 张斌
 * @时间 2012-3-15
 */
baseinfo.setHidden = function(hiddenIdList){
     for(var i=0;i<hiddenIdList.length;i++){
    	 Ext.getCmp(hiddenIdList[i]).hide();
     }
};
/**.
 * <p>
 * 设置元素为销毁<br/>
 * <p>
 * @param destoryIdList  设置为destory的元素ID数组
 * @author 张斌
 * @时间 2012-3-21
 */
baseinfo.setDestroy = function(destoryIdList){
     for(var i=0;i<destoryIdList.length;i++){
    	 Ext.getCmp(destoryIdList[i]).destroy();
     }
};
/**.
 * <p>
 * 设置元素为不可用<br/>
 * <p>
 * @param disabledIdList  设置为Disabled的元素ID数组
 * @author 张斌
 * @时间 2012-3-21
 */
baseinfo.setDisabled =function(disabledIdList){
	for(var i=0;i<disabledIdList.length;i++){
   	 Ext.getCmp(disabledIdList[i]).setDisabled(true);
    }
};
/**.
 * <p>
 * 清楚事件<br/>
 * <p>
 * @param clearIdList  设置为清楚时间的元素ID数组
 * @author 张斌
 * @时间 2012-3-22
 */
baseinfo.clearListeners =function(clearIdList){
	for(var i=0;i<clearIdList.length;i++){
   	 Ext.getCmp(clearIdList[i]).clearListeners( );
    }
};




/**.
 * <p>
 * 数组中是否有空值<br/>
 * <p>
 * @param array 数组
 * @author 张斌
 * @时间 2012-3-24
 */
baseinfo.isHaveEmpty = function(array){
	var boolen = false;
	for(var i = 0;i<array.length;i++){
		if(Ext.isEmpty(array[i])){
			boolen = true;
			return boolen;
		}
	}
	return boolen;
};
/**.
 * <p>
 * JS日期的format方法<br/>
 * <p>
 * @param format 日期格式
 * @author 张斌
 * @时间 2012-3-23
 */
Date.prototype.format = function(format){
	if(Ext.isEmpty(this)||this.getTime()==0||this.toString().indexOf('GMT')==-1){
		return null;
	}
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	};

	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	};

	for(var k in o) {
		if(new RegExp("("+ k +")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	};
	return format;
} ;

/**.
 * <p>
 * 根据传的参数生成查询条件<br/>
 * <p>
 * @param modelList 要转换的Modellist
 * @returns  dataList
 * @author 张斌
 * @时间 2012-4-16
 */
baseinfo.changeModelListToDataList = function(modelList){
	var dataList = new Array();
	for(var i = 0;i<modelList.length;i++){
		dataList.push(modelList[i].data);
	}
	return dataList;
};
/**.
 * <p>
 * 数据的将全局变量复制出来<br/>
 * <p>
 * @param modelList 要转换的Modellist
 * @returns  dataList
 * @author 张斌
 * @时间 2012-4-16
 */
baseinfo.copyModelListToDataList = function(modelList){
	var dataList = new Array();
	for(var i = 0;i<modelList.length;i++){
		dataList.push(modelList[i]);
	}
	return dataList;
};
/**
 * @功能：为js中的STRING加上trim方法
 * @作者： 张斌
 * @创建时间：2012-02-20
 */
String.prototype.trim= function(){  
    // 用正则表达式将前后空格  
    // 用空字符串替代。  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
};

//警告
baseinfo.showWoringMessage = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
	    msg:message,
	    //cls:'mesbox',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
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

//	setTimeout(function(){
//        Ext.Msg.hide();
//    }, 3000);
};
//是和否选择
baseinfo.showQuestionMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.YESNO,
	    icon:Ext.MessageBox.QUESTION,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
		    		fun(e);
	    	}
	    }
	});
};
//信息
baseinfo.showInfoMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
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

//	setTimeout(function(){
//        Ext.Msg.hide();
//    }, 3000);
};
//错误
baseinfo.showErrorMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.ERROR,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
};
//ADD -ALL
baseinfo.addAll = function(list,all) {
	var newlist = new Array();
	newlist.push(all);
	for(var i = 0;i<list.length;i++){
		newlist.push(list[i]);
	}
	return newlist;
};

//changeCodeToName(LIST)
baseinfo.changeCodeToName = function(list,code) {
	var name = '';
	for(var i = 0;i<list.length;i++){
		if(list[i].valueCode==code){
			name = list[i].valueName;
		}
	}
	return name;
};
//changeCodeToName(store)
baseinfo.changeCodeToNameStore = function(store,code) {
	var name = '';
	if(!Ext.isEmpty(store)){
		store.each(function(record){
			if(record.get('valueCode')==code){
				name = record.get('valueName');
			}
		});
	}
	return name;
};
/**
*	综合管理 权限互斥 JS功能代码：
*/

////////////////////////////////////////////////////////////////////
// 全局变量（模块名resourceConflict） start
////////////////////////////////////////////////////////////////////
baseinfo.resourceConflict.deleteResourceConflict = function(){

	var a_grid=Ext.getCmp('Foss_baseinfo_resourceConflict_ResourceConflictGrid_Id');
	// 获取选中的记录
	var selectionRecord = a_grid.getSelectionModel().getSelection();
	var ids = '';
	if (selectionRecord && selectionRecord.length > 0) {
		// 将id通过,分隔：
		for ( var i = 0; i < selectionRecord.length; i++) {
			ids = ids + selectionRecord[i].data.virtualCode+ ",";
		}
		ids = ids.substring(0,ids.length-1);
		Ext.MessageBox.show({
			title : '确认提示',
			msg : '作废（权限互斥）后不可恢复，确认是否继续？',
			buttons : Ext.Msg.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : function (btn) {
				if (btn == 'yes') {
					//获取结果表格对象
					var resourceConflictVo = new Object();
					resourceConflictVo.resourceConflictEntity = new Object();
					resourceConflictVo.resourceConflictEntity.virtualCode = ids;
					var params = {'resourceConflictVo':resourceConflictVo};
					Ext.Ajax.request({
						url : baseinfo.realPath('deleteResourceConflictMore.action'),
						jsonData :params,
						//"作废"成功
						success : function (response) {
							baseinfo.resourceConflict.pagingBar.moveFirst();
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.show({
								title : '信息（成功）提示',
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.INFO
							});
						},
						//"作废"失败
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.show({
								title : '信息（失败）提示',
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					});
				}
			}
		});		
	}else {
		Ext.MessageBox.show({
			title : '信息（失败）提示',
			msg : '无任何选中记录！',
			width : 300,
			buttons : Ext.Msg.OK,
			icon : Ext.MessageBox.WARNING
		});
		}
   
};


//==============================================================================================
// 权限互斥新增的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.resourceConflict.UpdateResourceConflictEntityWindow',{
	extend: 'Ext.window.Window',
	id : 'Foss_baseinfo_resourceConflict_UpdateResourceConflictEntityWindow_Id',
	title: '新增/修改权限互斥',
    width: 645,
    modal:true,
	closeAction:'hide',
	layout : 'column',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
		    Ext.create('Foss.baseinfo.resourceConflict.AddResourceConflictForm')
		];
		me.callParent([cfg]);
	}
});
// 权限互斥新增表单
Ext.define('Foss.baseinfo.resourceConflict.AddResourceConflictForm',{
	extend: 'Ext.form.Panel',
	id: 'Foss_baseinfo_resourceConflict_AddResourceConflictForm_Id',
	title: '新增',
	frame: true,
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 2
	}, 
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [{
				xtype : 'commonresourceselector', 
				name:'firstCode',
				valueField : 'code',// 这个参数，只需与实体中的某个字段对应即可
				 allowBlank: false, 
				fieldLabel : '权限一' 
			    },{
				xtype : 'commonresourceselector', 
				name:'secondCode',
				valueField : 'code',// 这个参数，只需与实体中的某个字段对应即可
				 allowBlank: false, 
				fieldLabel : '权限二' 
			},{
				border: 1,
				xtype:'container',
				columnWidth:1,
				colspan:2,
				defaultType:'button',
				layout:'column',
				items:[{
					xtype : 'button',
					hidden : false,
					name: 'reset',
					text: '取消',
					columnWidth:.14, 
					handler: function() {
						 var a_window = Ext.getCmp('Foss_baseinfo_resourceConflict_UpdateResourceConflictEntityWindow_Id');
		                    var resourceConflictForm = Ext.getCmp('Foss_baseinfo_resourceConflict_AddResourceConflictForm_Id');
		                    resourceConflictForm.getForm().reset();
		                    a_window.hide();
						}
					},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.58
					},{
						xtype : 'button',
						hidden : false,
						name: 'reset',
						text: '重置',
						columnWidth:.14, 
						handler: function() {
							var a_updateForm=Ext.getCmp('Foss_baseinfo_resourceConflict_AddResourceConflictForm_Id');
							a_updateForm.getForm().reset();	
						}
					},
				  	{

						cls:'yellow_button',
						text: '保存',
						columnWidth:.14, 
						handler: function() { 
							var a_form = Ext.getCmp("Foss_baseinfo_resourceConflict_AddResourceConflictForm_Id");
							var a_model = Ext.create('Foss.baseinfo.resourceConflict.ResourceConflictModel', a_form.getForm().getValues());
							// 请求合法性验证
							if(!a_form.getForm().isValid() 
									|| a_model.data.firstCode == a_model.data.secondCode){
								Ext.Msg.alert('温馨提示', "权限一和权限二不能相同");
								return;
							}
							var resourceConflictVo = new Object();
							resourceConflictVo.resourceConflictEntity = new Object();
							resourceConflictVo.resourceConflictEntity = a_model.data

							var params = {'resourceConflictVo':resourceConflictVo};
							
							// "../baseinfo/queryResourceConflictExactByEntity.action"
							var r_url = "queryResourceConflictExactByEntity.action";
							r_url=	baseinfo.realPath(r_url) ;
							// 重复性验证
							var a_isRepeat = false;
							Ext.Ajax.request({
								url: r_url,
								jsonData:params,
								async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
								success : function(response) {
					                var json = Ext.decode(response.responseText);
					                var a_list = json.resourceConflictVo.resourceConflictList;
					                if(a_list == null || a_list.length == 0){
					                	a_isRepeat = false;
					                }else{
					                	a_isRepeat = true;
					                }
					            },
				                exception : function(response) {
				                    var json = Ext.decode(response.responseText);
				                }
							});
							
							if(a_isRepeat){
								Ext.Msg.alert('温馨提示', "两个互斥的权限已经存在");
								return "";
							}
							
							// "../baseinfo/addResourceConflict.action"
							var r_url = "addResourceConflict.action";
							r_url=	baseinfo.realPath(r_url) ;
							//发送新增结点的Ajax请求.
							Ext.Ajax.request({
								url: r_url,
								jsonData:params,
								//作废成功
								success : function(response) {
					                var json = Ext.decode(response.responseText);
									//删除完成后，将“从表格删除”改为刷新表格：
									//a_grid.getStore().remove(rowObjs);
									baseinfo.resourceConflict.pagingBar.moveFirst();
									Ext.MessageBox.show({
													title : '信息（成功）提示',
													msg : json.message,
													width : 300,
													buttons : Ext.Msg.OK,
													callback : function () {
														a_form.up('window').hide();
													},
													icon : Ext.MessageBox.INFO
												});
									/**var tipMsg = "保存成功";
									if(json.message){
										tipMsg = json.message;
									}
									top.Ext.MessageBox.alert("保存成功",json.message);
									*/
					            },
					            //保存失败
				                exception : function(response) {
				                    var json = Ext.decode(response.responseText);
				                    //top.Ext.MessageBox.alert("保存失败",json.message);
				                    Ext.MessageBox.show({
													title : '信息（失败）提示',
													msg : json.message,
													width : 300,
													buttons : Ext.Msg.OK,
													callback : function () {
														a_form.up('window').hide();
													},
													icon : Ext.MessageBox.WARNING
												});
				                }
							});

							var a_conditionForm=Ext.getCmp("Foss_baseinfo_resourceConflict_AddResourceConflictForm_Id");
							// 将表单中的数据清空：
							a_conditionForm.getForm().reset();
							Ext.getCmp("Foss_baseinfo_resourceConflict_UpdateResourceConflictEntityWindow_Id").setVisible(false);
						}
				  	}]
			}
		];
		me.callParent([cfg]);
	}
});

//==============================================================================================
// 下面是详细实现
//==============================================================================================


/**
* 权限互斥 界面数据模型定义
*/
Ext.define('Foss.baseinfo.resourceConflict.ResourceConflictModel', {
    extend: 'Ext.data.Model',
    fields: [
      // ID
      {name: 'id', type: 'string'},
      // 虚拟编码
      {name: 'virtualCode', type: 'string'},
      // 权限编码一
      {name: 'firstCode', type: 'string'},
      // 权限编码一名称
      {name: 'firstCodeName', type: 'string'},
      // 权限编码二
      {name: 'secondCode', type: 'string'},
      // 权限编码二名称
      {name: 'secondCodeName', type: 'string'},
      // 创建时间
      {name: 'createTime', type: 'date'},
      // 创建时间
      {name: 'createTime', type: 'date'},
      // 更新时间
      {name: 'modifyTime', type: 'date'},
      // 是否启用
      {name: 'active', type: 'string'},
      // 创建人
      {name: 'createUserCode', type: 'string'},
      // 更新人
      {name: 'modifyUserCode', type: 'string'} 
    ]
});


//查询条件面板
Ext.define('Foss.baseinfo.resourceConflict.SelectConditionForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_resourceConflict_SelectConditionForm_Id',
	itemId: 'Foss_baseinfo_resourceConflict_SelectConditionForm_ItemId',	
	layout:'column',
	frame : true,
	columnWidth: 0.98,
	title: '查询条件',
	defaults: {
		xtype : 'textfield',
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},

  initComponent: function(config){
    var me = this,
			cfg = Ext.apply({}, config);
	me.buttons = [{
		xtype : 'button',
		text : '重置',
		disabled:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictRestButton'),
		hidden:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictRestButton'),
		width : 30,
		handler : function() {
			var userOrgDeptForm = Ext
					.getCmp("Foss_baseinfo_resourceConflict_SelectConditionForm_Id");
			// 将表单中的数据清空：
			userOrgDeptForm.getForm().reset();
		}
	}, '->', {
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		disabled:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictQueryButton'),
		hidden:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictQueryButton'),
		width : 30,
		// 查询按钮的响应事件：
		handler : function() {
				baseinfo.resourceConflict.pagingBar.moveFirst();
		}
	}];
    me.items = [
Ext.create('Ext.container.Container',{
		columnWidth:.99,
		layout : 'column',	
		defaultType : 'textfield',
		items:[{
				hidden:true,
				name: 'virtualCode',
			    fieldLabel: '虚拟编码',
				margin:'5 20 5 10',
			    columnWidth:.4
			} ,{
				xtype : 'commonresourceselector',
				labelWidth:160,
				name:'firstCode',
				valueField : 'code',// 这个参数，只需与实体中的某个字段对应即可
				forceSelection : true,
				fieldLabel : '权限一',
				margin:'5 20 5 10',
				labelWidth:120,
			    columnWidth:.33
			},{
				xtype : 'commonresourceselector',
				labelWidth:160,
				name:'secondCode',
				valueField : 'code',// 这个参数，只需与实体中的某个字段对应即可
				forceSelection : true,
				fieldLabel : '权限二',
				margin:'5 20 5 10',
				labelWidth:120,
			    columnWidth:.33
			}]
			})];
    me.callParent([cfg]);
  }
});


//查询的显示表格：
Ext.define('Foss.baseinfo.resourceConflict.ResourceConflictGrid',{
	extend: 'Ext.grid.Panel',
	id : 'Foss_baseinfo_resourceConflict_ResourceConflictGrid_Id',
	columnWidth: 1,
	stripeRows: true,
    columnLines: true,
	collapsible: false,
    bodyCls: 'autoHeight',
    autoScroll : true,
    //增加表格列的分割线
	cls: 'autoHeight', 
    frame: true,
    // 设置“如果查询的结果为空，则提示”
    emptyText: '查询结果为空',
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	//selModel : Ext.create('Ext.selection.RowModel'),
	selModel: Ext.create('Ext.selection.CheckboxModel'),
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	},
	columns : [{
            xtype:'actioncolumn',
            flex: 1,
			text: '操作',
			align: 'center',
            items: [{
            	iconCls:'deppon_icons_cancel',
                tooltip: '作废',
                disabled:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictVoidButton'),
                handler: function(grid, rowIndex, colIndex) {
                Ext.MessageBox.show({
	                title : '确认提示',
					msg : '作废（权限互斥）后不可恢复，确认是否继续？',
					buttons : Ext.Msg.YESNO,
					icon : Ext.MessageBox.QUESTION,
					fn : function (btn) {
						if (btn == 'yes') {
						// 获得当前选择的数据：
						var rowInfo = grid.getStore().getAt(rowIndex).data;
						//发送作废结点的Ajax请求.
						var resourceConflictVo = new Object();
						resourceConflictVo.resourceConflictEntity = new Object();
						resourceConflictVo.resourceConflictEntity.virtualCode = rowInfo.virtualCode;
						var params = {'resourceConflictVo':resourceConflictVo};
							Ext.Ajax.request({
								url : baseinfo.realPath('deleteResourceConflict.action'),
								jsonData : params,
								//"作废"成功
								success : function (response) {
									baseinfo.resourceConflict.pagingBar.moveFirst();
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '信息（成功）提示',
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.INFO
									});
									},
								//"作废"失败
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '信息（失败）提示',
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
									}
							});
				
                  }
            }
            });
            }
            }]
        },{
			hidden:true,
			dataIndex: 'virtualCode',
		    fieldLabel: '虚拟编码' 
		},{
			hidden:true,
			dataIndex: 'roleName',
			text:'角色名称',
		    flex: 4
		},{
			hidden:true,
			dataIndex: 'firstCode',
		    fieldLabel: '权限编码一' 
		},{
			dataIndex: 'firstCodeName',
			text: '权限编码一',
	        flex: 4
		},{
			hidden:true,
			dataIndex: 'secondCode',
			text: '权限编码二' 
		},{
			dataIndex: 'secondCodeName',
			text: '权限编码二',
	        flex: 4
		}
	],
	
	getTbar:function(){
		var me = this;
		return [{
			text : '新增',								//新增
			//hidden:!pricing.isPermission('../pricing/saveRole.action'),
			disabled:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictAddButton'),
			hidden:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictAddButton'),
			handler :function(){
				Ext.getCmp("Foss_baseinfo_resourceConflict_UpdateResourceConflictEntityWindow_Id").setVisible(true);
			} 
		},'-', {
			text : '作废',								//作废
			//hidden:!pricing.isPermission('../pricing/deleteRole.action'),
			disabled:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictVoidButton'),
			hidden:!baseinfo.resourceConflict.isPermission('resourceConflictindex/resourceConflictVoidButton'),
			handler :function(){
				baseinfo.resourceConflict.deleteResourceConflict();	
			} 
		}];
	},

	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.resourceConflict.ResourceConflictModel',
			pageSize: 10,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				// '../baseinfo/queryResourceConflictExactByEntity.action'
				url : baseinfo.realPath("queryResourceConflictExactByEntity.action"),
				//定义一个读取器
				reader: {
					type: 'json',
					root: 'resourceConflictVo.resourceConflictList',
					totalProperty : 'totalCount'
				}
			},
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners: {
				beforeload : function(store, operation, eOpts) {
					var queryForm = baseinfo.resourceConflict.queryForm;
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								'resourceConflictVo.resourceConflictEntity.firstCode' : queryParams.firstCode,
								'resourceConflictVo.resourceConflictEntity.secondCode' : queryParams.secondCode 
							}
						});	
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
		//添加头部按钮
		me.tbar = me.getTbar();
		baseinfo.resourceConflict.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//右边模块-查询结果面板
Ext.define('Foss.baseinfo.resourceConflict.SelectResultForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_resourceConflict_SelectResultForm_Id',
	itemId: 'Foss_baseinfo_resourceConflict_SelectResultForm_ItemId',
	layout:'column',
	frame: true,
	hidden:false,
	columnWidth: 0.98,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	title: '权限互斥列表',
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},

  	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			Ext.create('Ext.container.Container',{
				columnWidth:.99,
				layout : 'column',
				items:[
				    Ext.create('Foss.baseinfo.resourceConflict.ResourceConflictGrid')
				]
			})
		];
	    me.callParent([cfg]);
  	}
});




/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	baseinfo.resourceConflict.windowz = Ext.create('Foss.baseinfo.resourceConflict.UpdateResourceConflictEntityWindow');
	if (Ext.getCmp('T_baseinfo-resourceConflictindex_content')) {
		return;
	}
	
	Ext.getCmp('T_baseinfo-resourceConflictindex').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-resourceConflictindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		items: [
			Ext.create('Foss.baseinfo.resourceConflict.SelectConditionForm'),
			Ext.create('Foss.baseinfo.resourceConflict.SelectResultForm')
		] 
	}));
	baseinfo.resourceConflict.queryForm=Ext.getCmp('Foss_baseinfo_resourceConflict_SelectConditionForm_Id');
});