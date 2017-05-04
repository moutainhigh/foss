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
var goodAreaNewList=[];
// 定义一个model
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'organizationCode', // 虚拟编码
		type : 'string'
	}, {
		name : 'organizationName', // 组织名称
		type : 'string'
	}, {
		name : 'transferCode', // 外场编码
		type : 'string'
	}, {
		name : 'waitForkAreaCode', // 待叉区编码
		type : 'string'
	}, {
		name : 'virtualCode', // 虚拟编码
		type : 'string'
	}, {
		name : 'abscissa', // 横坐标
		type : 'string'
	}, {
		name : 'ordinate', // 纵坐标
		type : 'string'
	}, {
		name : 'waitForkAreaLength', // 待叉区长度
		type : 'string'
	}, {
		name : 'waitForkAreaWidth', // 待叉区宽度
		type : 'string'
	}, {
		name : 'waitForkAreaHeight', // 待叉区高度
		type : 'string'
	}, {
		name : 'remark', // remark;
		type : 'string'
	}, {
		name : 'active', // active
		type : 'string'
	} ]
});

Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaDistanceEntity', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'waitForkAreaCode', // 待叉区编码
		type : 'string'
	}, {
		name : 'targetCode', // 目标编码
		type : 'string'
	}, {
		name : 'targetType', // 目标类型（当前包括库区、月台）
		type : 'string'
	}, {
		name : 'distance', // 与目标的距离
		type : 'string'
	}, {
		name : 'active', // 是否有效
		type : 'string'
	} ]
});

// 待叉区Store
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.waitForkArea.WaitForkAreaEntity',// 月台的MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryWaitForkAreaByParams.action'),// 请求地址
		reader : {
			type : 'json',
			root : 'waitForkAreaVo.waitForkAreaEntityList',// 获取的数据
			totalProperty : 'totalCount'// 总个数
		}
	}
});

// 定义查询表单
Ext.define('Foss.baseinfo.waitForkArea.QueryWaitForkAreaForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkArea.queryWaitForkAreaInformation'),// 待叉区信息查询
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 1
	},
	defaults : {
		colspan : 1,
		margin : '8 10 5 10'
	},
	height : 180,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ {
			xtype : 'commontransfercenterselector',
			name : 'organizationCode',// 外场名称
			organizationCode : null,// 组织编码
			userCode : Ext.Array.contains(FossUserContext.getCurrentUser().roleids, 'FOSS_SYSTEM_ADMIN') ? null : FossUserContext.getCurrentUserEmp().empCode,
			currentOrgCode : Ext.Array.contains(FossUserContext.getCurrentUser().roleids, 'FOSS_SYSTEM_ADMIN') ? null : FossUserContext.getCurrentDeptCode(),
			fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.outfield'),
			listeners : {
				select : function(comb, records, empo) {
					comb.organizationCode = records[0].get('orgCode');
				}
			}
		} ];
		me.fbar = [ {
			xtype : 'button',
			width : 70,
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.reset'),// 重置
			disabled : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaQueryButton'),
			hidden : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaQueryButton'),
			margin : '0 800 0 0',
			handler : function() {
				me.getForm().reset();
				me.getForm().findField('organizationCode').organizationCode = null;
			}
		}, {
			xtype : 'button',
			width : 70,
			cls : 'yellow_button',
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.query'),// 查询
			disabled : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaQueryButton'),
			hidden : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaQueryButton'),
			handler : function() {
				if (me.getForm().isValid()) {
					if (Ext.isEmpty(me.getForm().findField('organizationCode').rawValue)) {
						me.getForm().findField('organizationCode').organizationCode = null;
						me.up().getQueryWaitForkAreaGrid().getPagingToolbar().moveFirst();
					} else {
						me.up().getQueryWaitForkAreaGrid().getPagingToolbar().moveFirst();
					}
				}

			}
		} ];
		me.callParent([ cfg ]);
	}
});

// 待叉区列表
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkArea.waitForkAreaInformation'),// 待叉区信息
	frame : true,
	flex : 1,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : baseinfo.waitForkArea.i18n('foss.baseinfo.queryResultIsNull'),// 查询结果为空
	// 得到bbar
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
	// 待叉区新增WINDOW
	waitForkAreaAddWindow : null,
	getWaitForkAreaAddWindow : function() {
		if (Ext.isEmpty(this.waitForkAreaAddWindow)) {
			this.waitForkAreaAddWindow = Ext.create('Foss.baseinfo.waitForkArea.WaitForkAreaAddWindow');
			this.waitForkAreaAddWindow.parent = this;// 父元素
		}
		return this.waitForkAreaAddWindow;
	},
	// 修改月台WINDOW
	waitForkAreaUpdateWindow : null,
	getWaitForkAreaUpdateWindow : function() {
		if (Ext.isEmpty(this.waitForkAreaUpdateWindow)) {
			this.waitForkAreaUpdateWindow = Ext.create('Foss.baseinfo.waitForkArea.lifanghongUpdateWindow');
			this.waitForkAreaUpdateWindow.parent = this;// 父元素
		}
		return this.waitForkAreaUpdateWindow;
	},
	// 作废月台
	toVoidWaitForkArea : function(btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection();// 获取选中的数据
		if (selections.length < 1) {// 判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');// 请选择一条进行作废操作！
			return;// 没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.waitForkArea.i18n('foss.baseinfo.wantSetAsideTheseWaitForkAreas'), function(e) {// 是否要作废这些月台？
			if (e == 'yes') {// 询问是否删除，是则发送请求
				var waitForkAreaVirtualCodes = new Array();// 月台
				for ( var i = 0; i < selections.length; i++) {
					waitForkAreaVirtualCodes.push(selections[i].get('virtualCode'));
				}
				var params = {
					'waitForkAreaVo' : {
						'waitForkAreaVirtualCodes' : waitForkAreaVirtualCodes
					}
				};
				var successFun = function(json) {
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json) {
					if (Ext.isEmpty(json)) {
						baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.requestTimeout'));// 请求超时
					} else {
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteWaitForkAreas.action');
				baseinfo.requestJsonAjax(url, params, successFun, failureFun);
			}
		})

	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [ {
			xtype : 'rownumberer',
			width : 40,
			text : '序号'// 序号
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.operate'),// 操作
			// dataIndex : 'id',
			xtype : 'actioncolumn',
			align : 'center',
			width : 80,
			items : [{
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.waitForkArea.i18n('foss.baseinfo.update'),//修改
                disabled:!baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaUpdateButton'),
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var virtualCode= record.get('virtualCode');//站点组虚拟编码
    				var params = {'waitForkAreaVo':{'waitForkAreaEntity':{'virtualCode':virtualCode}}};
    				var successFun = function(json){
    					var updateWindow = me.getWaitForkAreaUpdateWindow();//获得修改窗口
    					if(Ext.isEmpty(json.waitForkAreaVo.waitForkAreaEntity)){
    						baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.returnDataEmpty'));//返回数据为空！
    						return ;
    					}
    					json.waitForkAreaVo.waitForkAreaEntity.width = json.waitForkAreaVo.waitForkAreaEntity.width/1000;
    					json.waitForkAreaVo.waitForkAreaEntity.height = json.waitForkAreaVo.waitForkAreaEntity.height/1000;
    					updateWindow.waitForkAreaEntity = json.waitForkAreaVo.waitForkAreaEntity;//站点组
    					if(!Ext.isEmpty(updateWindow.waitForkAreaEntity.distanceBetweenPlatform)){
							for(var i=0; i<updateWindow.waitForkAreaEntity.distanceBetweenPlatform.length; i++ ){
								var waitForkAreaEntity = new Object();
		//						waitForkAreaEntity.billNo=billNo;
								waitForkAreaEntity.targetCode=updateWindow.waitForkAreaEntity.distanceBetweenPlatform[i].targetCode;
								waitForkAreaEntity.distance=updateWindow.waitForkAreaEntity.distanceBetweenPlatform[i].distance;				
								var record = new Foss.baseinfo.waitForkArea.WaitForkAreaDistanceEntity(waitForkAreaEntity);
								updateWindow.getwaitForkAreaForm().getWaitForkAreaPlatformDistanceGrid().getStore().add(record);
							}
						
						}
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.requestTimeout'));//请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('queryWaitForkAreaByCode.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.waitForkArea.i18n('foss.baseinfo.void'),//作废
                disabled:!baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaVoidButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes(baseinfo.waitForkArea.i18n('foss.baseinfo.isDeleteWaitForkArea'),function(e){//是否要作废这个月台？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				// var waitForkAreaVirtualCodes = new Array();//月台
            				// waitForkAreaVirtualCodes.push(record.get('virtualCode'));
            				var waitForkAreaVirtualCode = record.get('virtualCode');
							var waitForkAreaCode = record.get('waitForkAreaCode');
							var transferCode = record.get('transferCode');
            				var params = {'waitForkAreaVo':{'waitForkAreaEntity':{'virtualCode':waitForkAreaVirtualCode,'waitForkAreaCode':waitForkAreaCode,'transferCode':transferCode}}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteWaitForkArea.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.fieldName'),// 外场名称
			dataIndex : 'organizationName'
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.fieldID'),// 外场编号
			dataIndex : 'transferCode'
		}, {
			text : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkArea.waitForkAreaNo'),// 待叉区编号
			dataIndex : 'waitForkAreaCode'
		}, {
			text : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.abscissa'),// 横坐标
			dataIndex : 'abscissa'
		}, {
			text : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.ordinate'),// 纵坐标
			dataIndex : 'ordinate'
		}, {
			text : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkAreaWidth'),// 待叉区宽度
			dataIndex : 'waitForkAreaWidth',
			renderer : function(value) {
				return value / 1000;
			}
		}, {
			text : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkAreaLength'),// 待叉区长度
			dataIndex : 'waitForkAreaLength',
			renderer : function(value) {
				return value / 1000;
			}
		}, {
			text : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkAreaHeight'),// 待叉区高度
			dataIndex : 'waitForkAreaHeight',
			renderer : function(value) {
				return value / 1000;
			}
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.notes'),// 备注
			dataIndex : 'remark'
		} ];
		me.store = Ext.create('Foss.baseinfo.waitForkArea.WaitForkAreaStore', {
			autoLoad : false,// 不自动加载
			pageSize : 20,
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = me.up().getQueryWaitForkAreaForm();
					if (queryForm != null) {
						var organizationCode = queryForm.getForm().findField('organizationCode').organizationCode;

						Ext.apply(operation, {
							params : {// 月台大查询，查询条件组织
								'waitForkAreaVo.waitForkAreaEntity.organizationCode' : organizationCode
							// 外场CODE
							}
						});
					}
				}
			}
		});
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.tbar = [ {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.add'),// 新增
			disabled : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaAddButton'),
			hidden : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaAddButton'),
			handler : function() {
				me.getWaitForkAreaAddWindow().show();
			}
		}, '-', {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.void'),// 作废
			disabled : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaVoidButton'),
			hidden : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaVoidButton'),
			handler : function() {
				me.toVoidWaitForkArea();
			}
		} ];
		me.bbar = me.getPagingToolbar();
		me.callParent([ cfg ]);
	}
});

// ---新增待叉区------------------------------------------------------












// 定义待叉区form
Ext.define('Foss.baseinfo.waitForkArea.lifanghongForkAreaForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkArea.waitForkAreaInformation'),// 待叉区信息
	frame : true,
	isUpdate : false,
	flex : 1,
	collapsible : true,
	oldItems: [], //上一次的库位元素
	oldPlatformItems: [], //上一次的库位元素
	defaults : {
	//margin : '5 5 5 5',
		labelWidth : 80,
	// width:200,
	// colspan : 3
	},
	layout : {
		type : 'table',
		columns : 2
	},
	// 待叉区新增WINDOW
	waitForkAreaPlatformDistanceGrid : null,
	getWaitForkAreaPlatformDistanceGrid : function() {
		if (Ext.isEmpty(this.waitForkAreaAddWindow)) {
			this.waitForkAreaPlatformDistanceGrid = Ext.create('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceGrid');
			this.waitForkAreaPlatformDistanceGrid.parent = this;// 父元素
		}
		return this.waitForkAreaPlatformDistanceGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ {
			xtype : 'commontransfercenterselector',
			forceSelection : true,
			allowBlank : false,
			userCode : FossUserContext.getCurrentUserEmp().empCode,
			currentOrgCode : FossUserContext.getCurrentDeptCode(),
			name : 'organizationName',// 外场名称
			fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.fieldName'),
			listeners : {
				select : function(text, records, eops) {
					me.getForm().findField('organizationCode').setValue(records[0].get('orgCode'));
					me.getForm().findField('transferCode').setValue(records[0].get('code'));
					baseinfo.outFiledCode = me.getForm().findField('organizationCode').getValue();
						// var window = Ext.getCmp('T_baseinfo-waitForkArea_content').getQueryWaitForkAreaGrid().getWaitForkAreaAddWindow().getWaitForkAreaForm().down('grid').getWaitForkAreaLiAddWindow();
						 var window = Ext.getCmp('T_baseinfo-waitForkArea_content').getQueryWaitForkAreaGrid().getWaitForkAreaAddWindow().getWaitForkAreaForm().down('grid').getWaitForkAreaLiAddWindow();
						 window.organizationCode=me.getForm().findField('organizationCode').getValue();
//						 me.getForm().findField('platFormCode');
				}
			}
		}, {
			name : 'transferCode',// 外场编号
			readOnly : true,
			fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.fieldID'),
			colspan : 1,
			xtype : 'textfield'
		}, {
			name : 'organizationCode',// 外场编号
			readOnly : true,
			hidden : true,
			xtype : 'textfield'
		}, {
			name : 'waitForkAreaCode',// 待叉区编号
			allowBlank : false,
			maxLength : 4,
			regex : new RegExp('^\\d{0,5}$'),
			fieldLabel : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkArea.waitForkAreaNo'),
			xtype : 'textfield' 
		}, {
			name : 'abscissa',// 横坐标
			fieldLabel : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.abscissa'),
			allowBlank : false,
			decimalPrecision : 2,
			maxValue : 999999.999,
//			minValue : 0,
//			value : 0,
//			step : 0.001,
			xtype : 'numberfield'
		}, {
			name : 'ordinate',// 纵坐标
			fieldLabel : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.ordinate'),
			allowBlank : false,
			decimalPrecision : 2,
			maxValue : 999999.999,
//			minValue : 0,
//			value : 0,
//			step : 0.001,
			xtype : 'numberfield'
		}, {
			name : 'waitForkAreaWidth',// 待叉区宽度（米）
			fieldLabel : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkAreaWidth'),
			decimalPrecision : 2,
//			step : 0.001,
			value : 0,
			allowBlank : false,
			maxValue : 999999.999,
			minValue : 0,
			xtype : 'numberfield'
		}, {
			name : 'waitForkAreaLength',// 待叉区长度（米）
			fieldLabel : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkAreaLength'),
			decimalPrecision : 2,
//			step : 0.001,
			value : 0,
			allowBlank : false,
			maxValue : 999999.999,
			minValue : 0,
			xtype : 'numberfield'
		}, {
			name : 'waitForkAreaHeight',// 待叉区高度（米）
			fieldLabel : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkAreaHeight'),
			decimalPrecision : 2,
//			step : 0.001,
			value : 0,
			allowBlank : false,
			maxValue : 999999.999,
			minValue : 0,
			xtype : 'numberfield'
		}, {
			name : 'notes',// 备注
			fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.notes'),
			colspan : 2,
			maxLength : 200,
			width : 300,
			xtype : 'textareafield'
		},{
			  xtype:'container',
			  colspan:2,
			  layout:{
			     type:'table',
				 columns:3
			  },
			  items:[{
				xtype : 'commonplatformselector',
				//forceSelection : true,
				allowBlank : true,
	//			userCode : FossUserContext.getCurrentUserEmp().empCode,
//				orgCode:config.organizationCode,
				name : 'platFormCode',// 月台编号
				fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.waitForkAreaNo'),
//				listeners : {
//					select : function(text, records, eops) {
//						baseinfo.outFiledCode = null;
//					//	me.getForm().findField('waitForkAreaCode').setValue(records[0].get('waitForkAreaCode'));
//	//					me.getForm().findField('transferCode').setValue(records[0].get('code'));
//						
//					}
//				}
				listeners:{
			         beforequery:function(queryEvent,eOpts){
			        	 organizationCode = me.getForm().findField('organizationCode').getValue();
			        	 var a_destinationOrg = me.getForm().findField('platFormCode');
			             if(!Ext.isEmpty(organizationCode)){
			             a_destinationOrg.store.remove();
			             a_destinationOrg.store.removeListener('beforeload');
			     //重写公共选择器
			             a_destinationOrg.store.addListener('beforeload',function(store, operation, eOpts){
					     var searchParams =operation.params;
					     if(Ext.isEmpty(searchParams)){
						     searchParams ={};
						     Ext.apply(operation,{
						    	 	params:earchParams
						     	})
						     }
					     searchParams['platformVo.platformEntity.organizationCode']=organizationCode;
			             	});
					             a_destinationOrg.allowBlank =true;
					     }else{
					    	 a_destinationOrg.allowBlank =true;
					     	}
			             }
			         }
			}
//			  , {
//				name : 'distance',// 待叉区高度（米）
//				fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.distance'),
//				decimalPrecision : 2,
//				colspan : 1,
////				step : 0.001,
////				value : 0,
//				width:200,
//				allowBlank : true,
//				maxValue : 9999.99,
//				minValue : 0,
//				xtype : 'numberfield'
//				
////				name : 'distance',// 距离
////				fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.distance'),
////				colspan : 1,
////				xtype : 'textfield'
//			}
			  ,{
				 xtype:'button',
				 text:'加入列表',
				 columnWidth:.1,
				 cls:'yellow_button',
				 handler:function(){
					var me = this;										
					var form  = me.up().up();
					var grid = form.getWaitForkAreaPlatformDistanceGrid();
					var waitForkAreaStore = grid.getStore();
					var platFormCode = form.getForm().findField('platFormCode').getValue();
					if(Ext.isEmpty(platFormCode)){
						baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.platFormCodeAndDistanceNotNull'));//月台号不能为空
						return;
					};
					
					//月台横坐标
					var abscissa = form.getForm().findField('platFormCode').valueModels[0].raw.abscissa;
					//月台纵坐标
					var ordinate = form.getForm().findField('platFormCode').valueModels[0].raw.ordinate;
					//待叉区横坐标
					var waitForAreaAbscissa = form.getForm().findField('abscissa').getValue();
					//待叉区纵坐标
					var waitForAreaOrdinate = form.getForm().findField('ordinate').getValue();
					//如果月台和待叉区没有维护坐标不允许新增
					if(abscissa==null&&ordinate==null){
						Ext.ux.Toast.msg('提醒','该月台没有维护坐标！');
						return;
					};
					if(waitForAreaAbscissa==null&&waitForAreaOrdinate==null){
						Ext.ux.Toast.msg('提醒','请先维护待叉区坐标！');
						return;
					};
					//计算月台到待叉区的距离
					
					var distance = Math.abs(waitForAreaAbscissa-abscissa)+Math.abs(waitForAreaOrdinate-ordinate)+'';
					distance =distance.substring(0,distance.indexOf(".") + 3);
//					var distance = form.getForm().findField('distance').getValue();
					if(Ext.isEmpty(platFormCode)){
						baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.platFormCodeAndDistanceNotNull'));//月台号不能为空
						return;
					};
					
//					//月台横坐标
//					var abscissa = form.getForm().findField('platFormCode').valueModels[0].raw.abscissa;
//					//月台纵坐标
//					var ordinate = form.getForm().findField('platFormCode').valueModels[0].raw.ordinate;
//					//待叉区横坐标
//					var waitForAreaAbscissa = form.getForm().findField('abscissa').getValue();
//					//待叉区纵坐标
//					var waitForAreaOrdinate = form.getForm().findField('ordinate').getValue();
//					//如果月台和待叉区没有维护坐标不允许新增
//					if(abscissa==null&&ordinate==null){
//						Ext.ux.Toast.msg('提醒','改月台没有维护坐标！');
//						return;
//					};
//					if(waitForAreaAbscissa==null&&waitForAreaOrdinate==null){
//						Ext.ux.Toast.msg('提醒','请先维护待叉区坐标！');
//						return;
//					};
//					//计算月台到待叉区的距离
//					
//					var distance = Math.abs(waitForAreaAbscissa-abscissa)+Math.abs(waitForAreaOrdinate-ordinate)
////					var distance = form.getForm().findField('distance').getValue();
//					if(Ext.isEmpty(platFormCode)||Ext.isEmpty(distance)){
//						baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.platFormCodeAndDistanceNotNull'));//月台号不能为空
//						return;
//					}
					var waitForkAreaEntitys = waitForkAreaStore.data.items;
					if(waitForkAreaEntitys.length>0){
						for(var i=0;i<waitForkAreaEntitys.length;i++){
							gridPlatFormCode = waitForkAreaEntitys[i].data.targetCode;
							if(gridPlatFormCode==platFormCode){
								baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.platFormCodeNotSame'));//
								return;
							}
						}
					}		
							var waitForkAreaEntity = new Object();
	//						waitForkAreaEntity.billNo=billNo;
							waitForkAreaEntity.targetCode=platFormCode;
							waitForkAreaEntity.distance=distance;
							waitForkAreaEntity.targetType = 'BETWEEN_WAITFORKAREA_PLATFORM';
							var record = new Foss.baseinfo.waitForkArea.WaitForkAreaDistanceEntity(waitForkAreaEntity);
							waitForkAreaStore.add(record);
				 }
			}]},this.getWaitForkAreaPlatformDistanceGrid()];
		me.callParent([ cfg ]);
		if (me.isUpdate) { //修改
			me.getForm().findField('organizationCode').on('change', function (text, newValue, oldValue) {
				if(Ext.isEmpty(newValue)) {
					return;
				}
				// 优化性能 -> 暂停布局，防止多次回流和重绘
				Ext.suspendLayouts();
				me.checkPlatformItems(me, newValue);
				baseinfo.outFiledCode = me.getForm().findField('organizationCode').getValue();
//				me.checkStorageItems(me, newValue);
				// 优化性能 -> 恢复布局，多次布局操作，一次执行
				Ext.resumeLayouts(true);
			});
		} else {
			me.getForm().findField('organizationCode').on('blur', function (text, obj) {
				// 优化性能 -> 暂停布局，防止多次回流和重绘
				Ext.suspendLayouts();
				//me.genPlatformItems(me);
//				me.genStorageItems(me);
				// 优化性能 -> 恢复布局，多次布局操作，一次执行
				Ext.resumeLayouts(true);
			});
		}
	},
	
	checkPlatformItems: function(scope, newValue) {
		var me = scope;
		if(me.waitForkAreaLabelItem != undefined) {
			me.remove(me.waitForkAreaLabelItem);
		}
		if(!Ext.isEmpty(me.oldPlatformItems)) {
			for(var i=0; i<me.oldPlatformItems.length; i++) {
				me.remove(me.oldPlatformItems[i]);
			}
		}
//		var params = {'waitForkAreaVo':{'waitForkAreaEntity':me.up().waitForkAreaEntity}};
		var organizationCode = newValue;
		var params = {'waitForkAreaVo':{'waitForkAreaEntity':{'transferCode':organizationCode}}};
		params.waitForkAreaVo.waitForkAreaEntity.waitForkAreaCode = me.up().waitForkAreaEntity.waitForkAreaCode;
		// var params = null;
		var successFun = function(json) {
			var goodsAreaEntityList = json.waitForkAreaVo.goodsAreaEntityList;
			if(!Ext.isEmpty(goodsAreaEntityList)) {
				var items = [];
				for(var i=0; i<goodsAreaEntityList.length; i++ ) {
					var distanceList = me.up('window').waitForkAreaEntity.distanceBetweenGoodsArea;
					var numberfieldValue = null;
					//distanceList不为空表示设置了部分距离月台的距离
					if(!Ext.isEmpty(distanceList)) {
						for(var j=0; j<distanceList.length; j++) {
							//如果相同则表示这个语态以前被赋值
							if(distanceList[j].targetCode == goodsAreaEntityList[i].goodsAreaCode) {
								numberfieldValue = distanceList[j].distance;
							}
						}
					}
					var item = null;//月台对应的元素
					if(me.isShow) {
						if(Ext.isEmpty(numberfieldValue)) {
							item = {
								itemId: goodsAreaEntityList[i].virtualCode,
								xtype: 'numberfield',
								fieldLabel: goodsAreaEntityList[i].nameAndCode,
								waitForkAreaEntity: goodsAreaEntityList[i],//月台相关数据
								maxValue: 999999.999,
								readOnly: true,
								minValue: 0,
								step: 0.001,
								decimalPrecision: 3,
								listeners: {
									 blur:function(the,eOpts ){
									  var obj=new Object();
									  obj.targetCode=the.fieldLabel;
									  obj.distance=the.getValue();
									  obj.goodsAreaCode = the.waitForkAreaEntity.goodsAreaCode;
									  goodAreaNewList.push(obj);
									 }	
									}
								
							};
						} else {
							item = {
								itemId: goodsAreaEntityList[i].virtualCode,
								xtype: 'numberfield',
								fieldLabel: goodsAreaEntityList[i].nameAndCode,
								waitForkAreaEntity: goodsAreaEntityList[i],//月台相关数据
								maxValue: 999999.999,
								minValue: 0,
								readOnly:true,
								value: numberfieldValue,//这样设置值，点击reset的时候就不用再重新赋值了
								step: 0.001,
								decimalPrecision: 3,
								listeners: {
									 blur:function(the,eOpts ){
									  var obj=new Object();
									  obj.targetCode=the.fieldLabel;
									  obj.distance=the.getValue();
									  obj.goodsAreaCode = the.waitForkAreaEntity.goodsAreaCode;
									  goodAreaNewList.push(obj);
									 }	
									}
							};
						}
					} else {
						if(Ext.isEmpty(numberfieldValue)) {
							item = {
								itemId: goodsAreaEntityList[i].virtualCode,
								xtype: 'numberfield',
								fieldLabel: goodsAreaEntityList[i].nameAndCode,
								waitForkAreaEntity: goodsAreaEntityList[i],//月台相关数据
								maxValue: 999999.999,
								minValue: 0,
								step: 0.001,
								decimalPrecision: 3,
								listeners: {
								 blur:function(the,eOpts ){
								  var obj=new Object();
								  obj.targetCode=the.fieldLabel;
								  obj.distance=the.getValue();
								  obj.goodsAreaCode = the.waitForkAreaEntity.goodsAreaCode;
								  goodAreaNewList.push(obj);
								 }	
								}
							};
						} else {
							item = {
								itemId: goodsAreaEntityList[i].virtualCode,
								xtype: 'numberfield',
								fieldLabel: goodsAreaEntityList[i].nameAndCode,
								waitForkAreaEntity: goodsAreaEntityList[i],//月台相关数据
								maxValue: 999999.999,
								minValue: 0,
								value: numberfieldValue,//这样设置值，点击reset的时候就不用再重新赋值了
								step: 0.001,
								decimalPrecision: 3,
								listeners: {
									 blur:function(the,eOpts ){
									  var obj=new Object();
									  obj.targetCode=the.fieldLabel;
									  obj.distance=the.getValue();
									  obj.targetCode = the.waitForkAreaEntity.goodsAreaCode;
									  goodAreaNewList.push(obj);
									 }	
									}
							};
						}
					}
					items.push(item);
				}
				me.waitForkAreaLabelItem = me.add(me.waitForkAreaLabel);
				me.oldPlatformItems = me.add(items);
			}
		};
		var failureFun = function(json) {
			if(Ext.isEmpty(json)) {
				baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.requestTimeout'));//请求超时
			} else {
				baseinfo.showErrorMes(json.message);//提示失败原因
			}
		}
		var url = baseinfo.realPath('queryGoodsArea.action');//查询该外场的月台
		baseinfo.requestJsonAjax(url, params, successFun, failureFun);//发送AJAX请求
	}


});







// 待叉区Store
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.waitForkArea.WaitForkAreaDistanceEntity',// 月台的MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryWaitForkAreaPlatformDistanceList.action'),// 请求地址
		reader : {
			type : 'json',
			root : 'waitForkAreaDistanceVo.waitForkAreaDistanceEntityList',// 获取的数据
			totalProperty : 'totalCount'// 总个数
		}
	}
});

// 对应月台及距离表单
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceGrid', {
	extend : 'Ext.grid.Panel',
	title : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkAreaPlatformDistance'),// 待叉区信息
	frame : true,
	flex : 1,
	colspan : 2,
	width:500,
	height:250,
	// id:'WaitForkAreaPlatformDistanceGrid_id',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText : baseinfo.waitForkArea.i18n('foss.baseinfo.queryResultIsNull'),// 查询结果为空
	// 得到bbar
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
	// 待叉区月台距离 新增WINDOW
	waitForkAreaLiAddWindow : null,
	getWaitForkAreaLiAddWindow : function() {
		if (Ext.isEmpty(this.waitForkAreaLiAddWindow)) {
			this.waitForkAreaLiAddWindow = Ext.create('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceAddWindow',{
				'organizationCode':baseinfo.outFiledCode
				
			});
			this.waitForkAreaLiAddWindow.parent = this;// 父元素
		}
		this.waitForkAreaLiAddWindow.getWaitForkAreaForm().getForm().reset();
		var obj=this.waitForkAreaLiAddWindow.getWaitForkAreaForm().down('commonplatformselector');
		if(!Ext.isEmpty(obj)){
		  
		 obj.orgCode=baseinfo.outFiledCode;
		}
		return this.waitForkAreaLiAddWindow;
	},
	// 待叉区月台距离 修改WINDOW
	waitForkAreaUpdateWindow : null,
	getWaitForkAreaUpdateWindow : function() {
		if (Ext.isEmpty(this.waitForkAreaUpdateWindow)) {
			this.waitForkAreaUpdateWindow = Ext.create('Foss.baseinfo.waitForkArea.lifanghongUpdateWindow');
			this.waitForkAreaUpdateWindow.parent = this;// 父元素
		}
		return this.waitForkAreaUpdateWindow;
	},
	// 待叉区月台距离 作废
	toVoidWaitForkArea : function(btn) {
		var me = this;
		var selections = me.getSelectionModel().getSelection();// 获取选中的数据
		if (selections.length < 1) {// 判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');// 请选择一条进行作废操作！
			return;// 没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.waitForkArea.i18n('foss.baseinfo.isDeleteWaitForkArea'), function(e) {// 是否要作废这些月台？
			if (e == 'yes') {// 询问是否删除，是则发送请求
				me.getStore().remove(selections);
			}
		})

	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [ {
			xtype : 'rownumberer',
			width : 40,
			text : '序号'// 序号
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.waitForkAreaNo'),// 月台号
			dataIndex : 'targetCode'
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.distance'),// 距离
			dataIndex : 'distance'
		} ];
		// me.store = Ext.create('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceStore', {
			// // autoLoad : false,// 不自动加载
			// pageSize : 20,
			// // listeners : {
				// // beforeload : function(store, operation, eOpts) {
					// // var queryForm = me.up().getQueryWaitForkAreaForm();
					// // if (queryForm != null) {
						// // // var organizationCode =
						// // // me.up().getForm().findField('organizationCode').organizationCode;

						// // Ext.apply(operation, {
							// // params : {// 月台大查询，查询条件组织
							// // // 'waitForkAreaVo.waitForkAreaEntity.organizationCode'
							// // // : organizationCode
							// // // 外场CODE
							// // }
						// // });
					// // }
				// // }
			// // }
		// });
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller);
				}
			}
		}, me.selModel = Ext.create('Ext.selection.CheckboxModel', {// 多选框
			mode : 'MULTI',
			checkOnly : true
		});
		me.tbar = [{
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.void'),// 作废
			disabled : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaVoidButton'),
			hidden : !baseinfo.waitForkArea.isPermission('waitForkArea/waitForkAreaVoidButton'),
			handler : function() {
				me.toVoidWaitForkArea();
			}
		} ];
		// me.bbar = me.getPagingToolbar();
		me.callParent([ cfg ]);
	}
});

// 定义添加待叉区窗口
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaAddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.newWaitForkArea'),// 新增待叉区
	closable : true,
	parent : null,// 父元素（弹出这个window的gird——Foss.baseinfo.waitForkArea.PlatformGrid）
	resizable : true,// 可以调整窗口的大小
	modal:true,
	closeAction : 'hide',// 点击关闭是隐藏窗口
	// layout: 'fit',
	// modal: true,
	width : 590,
	height : 600,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	
	listeners : {
		beforehide : function(me) {// 隐藏WINDOW的时候清除数据
			me.getWaitForkAreaForm().getForm().reset();// 表格重置
		},
		beforeshow : function(me) {// 显示WINDOW的时候清除数据
			
		}
	},
	// 新增待叉区FORM
	waitForkAreaForm : null,
	getWaitForkAreaForm : function() {
		if (Ext.isEmpty(this.waitForkAreaForm)) {
			this.waitForkAreaForm = Ext.create('Foss.baseinfo.waitForkArea.lifanghongForkAreaForm', {
				'isUpdate' : false
			});
		}
		return this.waitForkAreaForm;
	},
	// 提交待叉区数据
	commitForkAreaForm : function(button) {
		var me = this;
		if (me.getWaitForkAreaForm().getForm().isValid()) {// 校验form是否通过校验
			var waitForkAreaModel = new Foss.baseinfo.waitForkArea.WaitForkAreaEntity();
			me.getWaitForkAreaForm().getForm().updateRecord(waitForkAreaModel);// 将FORM中数据设置到MODEL里面
			var organizationName = me.getWaitForkAreaForm().getForm().findField('organizationName').getRawValue();
			waitForkAreaModel.set('waitForkAreaHeight', waitForkAreaModel.get('waitForkAreaHeight') * 1000);// 后台一毫米计算
			waitForkAreaModel.set('waitForkAreaLength', waitForkAreaModel.get('waitForkAreaLength') * 1000);
			waitForkAreaModel.set('waitForkAreaWidth', waitForkAreaModel.get('waitForkAreaWidth') * 1000);
			waitForkAreaModel.set('organizationName',organizationName);
			var store=me.getWaitForkAreaForm().getWaitForkAreaPlatformDistanceGrid().getStore();
			var records=store.getNewRecords();
			var array=new Array();
			for(var i=0;i<records.length;i++){
			  array.push(records[i].data); 
			}
			
			// console.log(records);
			
			var params = {
					'waitForkAreaVo' : {
						'waitForkAreaEntity' : waitForkAreaModel.data
						// 'waitForkAreaEntity' :{	
						   // 'distanceBetweenPlatform':array			
						// }	 
					}
				};// 组织新增数据
			 params.waitForkAreaVo.waitForkAreaEntity.distanceBetweenPlatform = array;
			var successFun = function(json) {
				button.setDisabled(false);
				baseinfo.showInfoMes(json.message);// 提示新增成功
				var store=me.getWaitForkAreaForm().getWaitForkAreaPlatformDistanceGrid().getStore();
				store.removeAll();
				me.close();
				me.parent.getPagingToolbar().moveFirst();// 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				} else {
					baseinfo.showErrorMes(json.message);// 提示失败原因
				}
			};
			var url = baseinfo.realPath('addWaitForkArea.action');// 请求月台新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.cancel'),// 取消
			handler : function() {
				me.close();
			}
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.reset'),// 重置
			handler : function() {
				me.getWaitForkAreaForm().getForm().reset();
			}
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.save'),// 保存
			cls : 'yellow_button',
			margin : '0 0 0 305',
			handler : function() {
				me.commitForkAreaForm(this);
			}
		} ];
		me.items = [ me.getWaitForkAreaForm() ];
		me.callParent([ cfg ]);
	
	}
	
});

/**
 * 修改库区
 */
Ext.define('Foss.baseinfo.waitForkArea.lifanghongUpdateWindow', {
	extend: 'Ext.window.Window',
	title: baseinfo.waitForkArea.i18n('foss.baseinfo.modifyWaitForkArea'), //修改库区
	closable: true,
//	resizable: false,
	waitForkAreaEntity: null, //修改库区数据
	parent: null, //父元素（弹出这个window的gird——Foss.baseinfo.waitForkArea.waitForkAreaGrid）
	closeAction: 'hide',
	modal: true,
	width: 590,
	autoScroll : true,
	height: 680,
//	layout: 'fit',
	listeners: {
		beforehide: function (me) {
			me.getwaitForkAreaForm().getWaitForkAreaPlatformDistanceGrid().getStore().removeAll();
			me.resetData(); //清除掉这次的数据
		},
		beforeshow: function (me) {
			me.getwaitForkAreaForm().getForm().findField('organizationName').setCombValue(me.waitForkAreaEntity.organizationName, me.waitForkAreaEntity.transferCode); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			me.getwaitForkAreaForm().getForm().findField('organizationCode').setValue(me.waitForkAreaEntity.organizationCode); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			// me.getwaitForkAreaForm().getForm().findField('arriveRegionCode').setCombValue(me.waitForkAreaEntity.arriveRegionName, me.waitForkAreaEntity.arriveRegionCode); //目的站
			me.getwaitForkAreaForm().getForm().findField('transferCode').setValue(me.waitForkAreaEntity.transferCode); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			if (me.waitForkAreaEntity.countingMode == 'KP') {
				me.getwaitForkAreaForm().getForm().findField('countingMode').setValue({
					countingModeKp: true,
					countingModeAb: false
				}); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			}
			if (me.waitForkAreaEntity.countingMode == 'AB') {
				me.getwaitForkAreaForm().getForm().findField('countingMode').setValue({
					countingModeKp: false,
					countingModeAb: true
				}); //只在加载数据的时处理外场信息，在重置的时候，不进行处理
			}
			if (me.waitForkAreaEntity.countingMode == 'KPAB') {
				me.getwaitForkAreaForm().getForm().findField('countingMode').setValue({
					countingModeKp: true,
					countingModeAb: true
				});
			}
			me.loadValue();
		}
	},
	//库区FORM
	waitForkAreaForm: null,
	getwaitForkAreaForm: function () {
		if (Ext.isEmpty(this.waitForkAreaForm)) {
			this.waitForkAreaForm = Ext.create('Foss.baseinfo.waitForkArea.lifanghongForkAreaForm', {
				'isUpdate': true //证明是修改
			});
			this.waitForkAreaForm.getForm().findField('organizationName').setReadOnly(true);
		}
		return this.waitForkAreaForm;
	},
	//修改库区
	commitwaitForkArea: function (button) {
		var me = this;
		var form = me.getwaitForkAreaForm();
		if (form.getForm().isValid()) { //校验form是否通过校验
			// var waitForkAreaModel = new Foss.baseinfo.waitForkArea.waitForkAreaEntity(me.waitForkAreaEntity);
			// var storageList = new Array(); // 到各个库区所拥有的月台
			// form.getForm().updateRecord(waitForkAreaModel); //将FORM中数据设置到MODEL里面
			// if (!Ext.isEmpty(form.oldItems)) {
				// for (var i = 0; i < form.oldItems.length; i++) {
					// if (form.oldItems[i].getValue()) { //选中了
						// storageList.push({
							// 'virtualCode': form.oldItems[i].storageEntity.virtualCode
						// });
					// }
				// }
			// }
			// waitForkAreaModel.set('storageList', storageList);
			// // Start..
    		// var distanceList = [],
    			// platformItems = form.oldPlatformItems;	//到各个月台的距离列表
    		// if(!Ext.isEmpty(platformItems)) {
    			// for(var i=0; i<platformItems.length; i++) {
        			// if(!Ext.isEmpty(platformItems[i].getValue())) {
        				// var distance = {
    						// 'platformVirtualCode': platformItems[i].platformEntity.virtualCode,
    						// 'distance': platformItems[i].getValue()
						// };
        				// distanceList.push(distance);
        			// }
        		// }
    		// }
    		// waitForkAreaModel.set('distanceList', distanceList);
    		// // End...
			// var params = {
				// 'waitForkAreaVo': {
					// 'waitForkAreaEntity': waitForkAreaModel.data
				// }
			// };
			
			var waitForkAreaModel = new Foss.baseinfo.waitForkArea.WaitForkAreaEntity();
			 form.getForm().updateRecord(waitForkAreaModel);// 将FORM中数据设置到MODEL里面
			var organizationName = form.getForm().findField('organizationName').getRawValue();
			
			var store=form.getWaitForkAreaPlatformDistanceGrid().getStore();
			var records=store.getNewRecords();
			var array=new Array();
			for(var i=0;i<records.length;i++){
			  array.push(records[i].data); 
			}
			
			// console.log(records);
			
			var params = {
					'waitForkAreaVo' : {
						'waitForkAreaEntity' : me.waitForkAreaEntity
						// 'waitForkAreaEntity' :{	
						   // 'distanceBetweenPlatform':array			
						// }	 
					}
				};// 组织新增数据
			params.waitForkAreaVo.waitForkAreaEntity.waitForkAreaHeight = waitForkAreaModel.get('waitForkAreaHeight') * 1000;
			params.waitForkAreaVo.waitForkAreaEntity.waitForkAreaLength = waitForkAreaModel.get('waitForkAreaLength') * 1000;
			params.waitForkAreaVo.waitForkAreaEntity.waitForkAreaWidth = waitForkAreaModel.get('waitForkAreaWidth') * 1000;
			params.waitForkAreaVo.waitForkAreaEntity.organizationName = organizationName;
			params.waitForkAreaVo.waitForkAreaEntity.ordinate = waitForkAreaModel.get('ordinate');
			params.waitForkAreaVo.waitForkAreaEntity.abscissa = waitForkAreaModel.get('abscissa');
			 params.waitForkAreaVo.waitForkAreaEntity.distanceBetweenPlatform = array;
			 params.waitForkAreaVo.waitForkAreaEntity.distanceBetweenGoodsArea = goodAreaNewList
			
			//组织新增数据
			var successFun = function (json) {
				button.setDisabled(false);
				baseinfo.showInfoMes(json.message); //提示新增成功
				goodAreaNewList=[];
				me.close();
				me.parent.getPagingToolbar().moveFirst(); //成功之后重新查询刷新结果集
			};
			var failureFun = function (json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.requestTimeout')); //请求超时
				} else {
					baseinfo.showErrorMes(json.message); //提示失败原因
				}
			};
			var url = baseinfo.realPath('updateForkArea.action'); //请求库区新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun); //发送AJAX请求
		}
	},
	//清楚数据
	resetData: function () {
		var me = this;
		var form = me.getwaitForkAreaForm();
		if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
			var oldItems = form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				var oldItem = oldItems[i];
				if(oldItem.isVisible()) {
					form.remove(oldItem);
				}
			}
		}
		form.getForm().reset(); //表格重置
	},
	//加载原有数据
	loadValue: function () { //外场名称和外场code不进行处理
		var me = this;
		   // var waitForkAreaModel = new Foss.baseinfo.waitForkArea.waitForkAreaEntity(me.waitForkAreaEntity);
		   var waitForkAreaModel=  new Foss.baseinfo.waitForkArea.WaitForkAreaEntity(me.waitForkAreaEntity);
		me.getwaitForkAreaForm().getForm().findField('notes').setValue(waitForkAreaModel.get('notes'));
		me.getwaitForkAreaForm().getForm().findField('waitForkAreaCode').setValue(waitForkAreaModel.get('waitForkAreaCode'));
		// me.getwaitForkAreaForm().getForm().findField('waitForkAreaName').setValue(waitForkAreaModel.get('waitForkAreaName'));
		// me.getwaitForkAreaForm().getForm().findField('waitForkAreaType').setValue(waitForkAreaModel.get('waitForkAreaType'));
		// me.getwaitForkAreaForm().getForm().findField('waitForkAreaUsage').setValue(waitForkAreaModel.get('waitForkAreaUsage'));
		// me.getwaitForkAreaForm().getForm().findField('goodsType').setValue(waitForkAreaModel.get('goodsType'));
		// me.getwaitForkAreaForm().getForm().findField('arriveRegionCode').setValue(waitForkAreaModel.get('arriveRegionCode'));
		// if (waitForkAreaModel.get('asteriskCode') == 'N') {
			// me.getwaitForkAreaForm().getForm().findField('asteriskCode').setValue(baseinfo.waitForkArea.i18n('foss.baseinfo.null')); //无
		// } else {
			// me.getwaitForkAreaForm().getForm().findField('asteriskCode').setValue(waitForkAreaModel.get('asteriskCode'));
		// }
		me.getwaitForkAreaForm().getForm().findField('waitForkAreaWidth').setValue(waitForkAreaModel.get('waitForkAreaWidth')/1000);
		me.getwaitForkAreaForm().getForm().findField('waitForkAreaLength').setValue(waitForkAreaModel.get('waitForkAreaLength')/1000);
		me.getwaitForkAreaForm().getForm().findField('waitForkAreaHeight').setValue(waitForkAreaModel.get('waitForkAreaHeight')/1000);
		me.getwaitForkAreaForm().getForm().findField('abscissa').setValue(waitForkAreaModel.get('abscissa'));
		me.getwaitForkAreaForm().getForm().findField('ordinate').setValue(waitForkAreaModel.get('ordinate'));
	},
	constructor: function (config) {
		var me = this,
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text: baseinfo.waitForkArea.i18n('foss.baseinfo.cancel'), //取消
			handler: function () {
				me.close();
			}
		}, {
			text: baseinfo.waitForkArea.i18n('foss.baseinfo.reset'), //重置
			handler: function () {
				//me.getwaitForkAreaForm().getForm().findField('organizationName').setCombValue(me.waitForkAreaEntity.organizationName
				//,me.waitForkAreaEntity.organizationCode);
				//在充重置时不进行处理
				me.getwaitForkAreaForm().getForm().findField('arriveRegionCode').setCombValue(me.waitForkAreaEntity.arriveRegionName, me.waitForkAreaEntity.arriveRegionCode); //目的站
				me.loadValue();
				var form = me.getwaitForkAreaForm();
				if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
					for (var i = 0; i < form.oldItems.length; i++) {
						form.oldItems[i].setValue(form.oldItems[i].isCheck);
					}
				}
			}
		}, {
			text: baseinfo.waitForkArea.i18n('foss.baseinfo.save'), //保存
			cls: 'yellow_button',
			margin: '0 0 0 325',
			handler: function () {
				me.commitwaitForkArea(this);
			}
		}];
		me.items = [me.getwaitForkAreaForm()];
		me.callParent([cfg]);
	}
});




//定义待叉区对应月台及距离form
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.waitForkArea.waitForkAreaInformation'),// 待叉区信息
	frame : true,
	isUpdate : false,
	flex : 1,
	collapsible : true,
	organizationCode:null,
	defaults : {
	//margin : '5 5 5 5s',
		labelWidth : 80,
	// width:200,
	// colspan : 3
	},
	layout : {
		type : 'table',
		columns : 2
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		// var code =  Ext.getCmp('T_baseinfo-waitForkArea_content').getQueryWaitForkAreaGrid().getWaitForkAreaAddWindow().getWaitForkAreaForm().getForm().findField('organizationCode').getValue();
		//var code = me.getForm().findField() 
		//var code = me.up().up().up().getForm().findField('organizationCode').getValue();
		// Ext.getCmp('WaitForkAreaPlatformDistanceGrid_id').getWaitForkAreaLiAddWindow()
		
		// me.getForm().findField('organizationCode').getValue()
		me.items = [ {
			xtype : 'commonplatformselector',
			//forceSelection : true,
			allowBlank : true,
//			userCode : FossUserContext.getCurrentUserEmp().empCode,
//			orgCode:config.organizationCode,
			name : 'platFormCode',// 月台编号
			fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.waitForkAreaNo'),
			listeners : {
				select : function(text, records, eops) {
					baseinfo.outFiledCode = null;
				//	me.getForm().findField('waitForkAreaCode').setValue(records[0].get('waitForkAreaCode'));
//					me.getForm().findField('transferCode').setValue(records[0].get('code'));
					
				}
			}
		}, {
			name : 'distance',// 距离
			fieldLabel : baseinfo.waitForkArea.i18n('foss.baseinfo.distance'),
			colspan : 1,
			xtype : 'textfield'
		} ];
		me.callParent([ cfg ]);
	}

});



//定义添加待叉区月台距离窗口
Ext.define('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceAddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.waitForkArea.i18n('foss.bse.baseinfo.newWaitForkAreaPlatformDistance'),// 新增对应月台及距离
	closable : true,
	parent : null,// 父元素（弹出这个window的gird——Foss.baseinfo.waitForkArea.PlatformGrid）
	resizable : true,// 可以调整窗口的大小
	closeAction : 'hide',// 点击关闭是隐藏窗口
	width : 590,
	height : 550,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	organizationCode:null,
	listeners : {
		// beforehide : function(me) {// 隐藏WINDOW的时候清除数据
			// me.getWaitForkAreaForm().getForm().reset();// 表格重置
			// me.organizationCode =null;
		// },
		// beforeshow : function(me) {// 显示WINDOW的时候清除数据
		// console.log(1111);
			// // if(!Ext.isEmpty(me.organizationCode)){
				// // me.getWaitForkAreaForm().getForm().findField('platFormCode').orgCode =me.organizationCode;
			// // }
		// }
	},
	// 新增对应月台及距离FORM
	waitForkAreaForm : null,
	getWaitForkAreaForm : function(organizationCode) {
		if (Ext.isEmpty(this.waitForkAreaForm)) {
			this.waitForkAreaForm = Ext.create('Foss.baseinfo.waitForkArea.WaitForkAreaPlatformDistanceForm', {
				'isUpdate' : false,
				'organizationCode':organizationCode
			});
		}
		return this.waitForkAreaForm;
	},
	// 提交新增对应月台及距离数据
	commitForkAreaForm : function(button) {
		var me = this;
		if (me.getWaitForkAreaForm().getForm().isValid()) {// 校验form是否通过校验
			var waitForkAreaDistanceModel = new Foss.baseinfo.waitForkArea.WaitForkAreaDistanceEntity();
			me.getWaitForkAreaForm().getForm().updateRecord(waitForkAreaDistanceModel);// 将FORM中数据设置到MODEL里面
			waitForkAreaDistanceModel.set('waitForkAreaHeight', waitForkAreaDistanceModel.get('waitForkAreaHeight') * 1000);// 后台一毫米计算

			var params = {
				'waitForkAreaDiatanceVo' : {
					'WaitForkAreaDiatanceEntity' : waitForkAreaDistanceModel.data
				}
			};// 组织新增数据
			var successFun = function(json) {
				button.setDisabled(false);
				me.close();
				me.parent.getPagingToolbar().moveFirst();// 成功之后重新查询刷新结果集
			};
			var failureFun = function(json) {
				button.setDisabled(false);
				if (Ext.isEmpty(json)) {
					baseinfo.showErrorMes(baseinfo.waitForkArea.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				} else {
					baseinfo.showErrorMes(json.message);// 提示失败原因
				}
			};
			var url = baseinfo.realPath('addWaitForkArea.action');// 请求月台新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.cancel'),// 取消
			handler : function() {
				me.close();
			}
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.reset'),// 重置
			handler : function() {
				me.getWaitForkAreaForm().getForm().reset();
			}
		}, {
			text : baseinfo.waitForkArea.i18n('foss.baseinfo.save'),// 保存
			cls : 'yellow_button',
			margin : '0 0 0 305',
			handler : function() {
				me.commitForkAreaForm(this);
			}
		} ];
		me.items = [ me.getWaitForkAreaForm(config.organizationCode) ];
		// me.listeners={
			// beforehide : function(me) {// 隐藏WINDOW的时候清除数据
				// me.getWaitForkAreaForm().getForm().reset();// 表格重置
				// me.organizationCode =null;
			// },
			// beforeshow : function(me) {// 显示WINDOW的时候清除数据
				// console.log(1111);
				 // if(!Ext.isEmpty(me.organizationCode)){
					 // me.getWaitForkAreaForm().getForm().findField('platFormCode').orgCode =me.organizationCode;
				 // }
			// }
		// }
		me.callParent([ cfg ]);
	}

});





Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryWaitForkAreaForm = Ext.create("Foss.baseinfo.waitForkArea.QueryWaitForkAreaForm");
	var queryWaitForkAreaGrid = Ext.create("Foss.baseinfo.waitForkArea.WaitForkAreaGrid");
	Ext.getCmp('T_baseinfo-waitForkArea').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-waitForkArea_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		// 获得查询FORM
		getQueryWaitForkAreaForm : function() {
			return queryWaitForkAreaForm;
		},
		getQueryWaitForkAreaGrid : function() {
			return queryWaitForkAreaGrid
		},
		items : [ queryWaitForkAreaForm, queryWaitForkAreaGrid ]
	}))
});
