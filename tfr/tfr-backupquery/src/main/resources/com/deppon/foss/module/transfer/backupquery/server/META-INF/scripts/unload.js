backupquery.addTab = function(tabId,tabTitle,tabLocation){
	var mainTab = Ext.getCmp('mainAreaPanel'),
    	tab = Ext.getCmp(tabId);
	if(tab){
		mainTab.remove(tab,true);
		addTab(tabId,tabTitle,tabLocation);
	}else{
		addTab(tabId,tabTitle,tabLocation);
	}
}