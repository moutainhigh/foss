<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/ext" prefix="ext"  %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" >

/**
 * 产品类型转化 **********************************************
 */
Ext.define('Foss.pkp.ProductData', {
	statics: {
		data: null,
        getData: function() {
        	if(Foss.pkp.ProductData.data==null){
        		Ext.Ajax.request({
        			/**
        			* 注意：如果用到本段js,本地启动时，采用下面url的话需要启动pkp-predelvier的war，如果不想启动，
        			*  可以采用第一种url,将第2种url注销掉。但是提交时，还是要提交第2中url，为了配合服务器单点登录。
        			*  切记，别吧第一种url给提交上去了
        			*/
        			
					//url : '../predeliver/queryProduct.action',
					url:ContextPath.STL_WEB+ '/predeliver/queryThreeLevelProductAll.action',
					async:false,
					success: function(response){
						var result = Ext.decode(response.responseText);
						Foss.pkp.ProductData.data = result.vo.productEntitys;
					}
				});
        	}
        	var anyRecords = {'code' : '', 'name' : '全部'};
        	Foss.pkp.ProductData.data.unshift(anyRecords);
        	return Foss.pkp.ProductData.data;
         },
         rendererDisplayToSubmit: function(displayValue) {
         	var data = Foss.pkp.ProductData.getData();
         	if (!Ext.isEmpty(displayValue) && !Ext.isEmpty(data)) {
         		for ( var i = 0; i < data.length; i++) {
					if (displayValue == data[i].name) {
					     return data[i].code;
					}
				}
         	}
         	return displayValue;
         },
         rendererSubmitToDisplay: function(submitValue) {
         	var data = Foss.pkp.ProductData.getData();
			if (!Ext.isEmpty(submitValue) && !Ext.isEmpty(data)) {
				for ( var i = 0; i < data.length; i++) {
					if (submitValue == data[i].code) {
					     return data[i].name;
					}
				}
			}
			return submitValue;
		}
     }
});

Ext.define('Foss.pkp.ProductStore', {
	extend : 'Ext.data.Store',
	fields:['code','name'],
	data : Foss.pkp.ProductData.getData()
});

</script>