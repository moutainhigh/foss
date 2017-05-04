<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
Ext.define('Foss.pkp.ProductData', {
	statics: {
		data: null,
        getData: function() {
        	if(Foss.pkp.ProductData.data==null){
        		Ext.Ajax.request({
//					url : '../predeliver/queryProduct.action',
					url:ContextPath.PKP_DELIVER + '/predeliver/queryProduct.action',
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