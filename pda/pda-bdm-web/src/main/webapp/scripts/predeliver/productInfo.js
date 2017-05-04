(function(){
	if(Ext.isEmpty(login.productData)){
		Ext.Ajax.request({
			//url : '../predeliver/queryProduct.action',
			url:ContextPath.PKP_DELIVER + '/predeliver/queryProduct.action',
			async:false,
			success: function(response){
				var result = Ext.decode(response.responseText);
				login.productData = result.vo.productEntitys;
	        	var anyRecords = {'code' : '', 'name' : '全部'};
	        	login.productData.unshift(anyRecords);
			}
		});
	}
	Ext.define('Foss.pkp.ProductData', {
		singleton: true,
		getProductStore : function() {
			if(!Ext.isEmpty(login.productData)){
				var json={
					fields:['code','name'],
					data : login.productData
				}; 
				return Ext.create('Ext.data.Store',json);
			} else {
				return [];
			}
		},
        rendererDisplayToSubmit: function(displayValue) {
         	var data = login.productData;
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
         	var data = login.productData;
			if (!Ext.isEmpty(submitValue) && !Ext.isEmpty(data)) {
				for ( var i = 0; i < data.length; i++) {
					if (submitValue == data[i].code) {
					     return data[i].name;
					}
				}
			}
			return submitValue;
		}
	});
})();