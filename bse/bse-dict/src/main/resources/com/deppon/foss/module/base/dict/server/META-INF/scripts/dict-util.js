
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
/**
 * 修改date对象数据的JSON提交方式
 */
Ext.JSON.encodeDate = function(date) {
	return date.getTime();
};


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
dict.getStore = function(storeId,model,fields,data) {
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
dict.requestJsonAjax = function(url,params,successFn,failFn)
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
dict.requestFileUploadAjax = function(url,form,successFn,failFn)
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
dict.requestAjax = function(url,params,successFn,failFn)
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
dict.setReadOnly = function(readOnlyIdList){
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
dict.setHiddenAndDestroy = function(hiddenIdList){
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
dict.setHidden = function(hiddenIdList){
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
dict.setDestroy = function(destoryIdList){
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
dict.setDisabled =function(disabledIdList){
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
dict.clearListeners =function(clearIdList){
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
dict.isHaveEmpty = function(array){
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
dict.changeModelListToDataList = function(modelList){
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
dict.copyModelListToDataList = function(modelList){
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
dict.showWoringMessage = function(message,fun) {
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
	setTimeout(function(){
        Ext.Msg.hide();
    }, 3000);
};
//是和否选择
dict.showQuestionMes = function(message,fun) {
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
dict.showInfoMes = function(message,fun) {
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
	setTimeout(function(){
        Ext.Msg.hide();
    }, 3000);
};
//错误
dict.showErrorMes = function(message,fun) {
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
	setTimeout(function(){
        Ext.Msg.hide();
    }, 3000);
};

