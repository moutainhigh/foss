Ext.define('Foss.main.AnnouncementModel',{
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'topic'
	}, {
		name : 'announcement'
	}, {
		name : 'active'
	}, {
		name : 'createUser'
	}, {
		name : 'createDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	}, {
		name : 'modifyUser'
	}, {
		name : 'modifyDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	} ]
});
Ext.define('Foss.main.AnnouncementGird', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.announcementgird',
	hideHeaders: true,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store
			});
		}
		return this.pagingToolbar;
	},
    plugins: [{
		header: false,
        ptype: 'rowexpander',
		rowsExpander: false,
        rowBodyTpl : [
            '<br><div>{announcement}</div><br>'
        ]
    }],
	initComponent: function() {
		var me = this;
		me.store = Ext.create('Ext.data.Store', {
			model: 'Foss.main.AnnouncementModel',
			pageSize : 10,
			autoLoad: true,
			proxy: {
		         type: 'ajax',
		         actionMethods : 'POST',
		         url: '../login/announcementInfo.action',
		         reader: {
		             type: 'json',
		             totalProperty : 'totalCount',
		             root: 'announcementEntityList'
		         }
		     }
		});
		me.columns = [{
			dataIndex : 'topic',
			flex: 1
		}, {
			xtype: 'datecolumn',   
			format:'Y-m-d H:i:s',
			dataIndex : 'modifyDate',
			width: 135
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent();
	}
});

Ext.define('Foss.main.SystemHelpModel',{
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'topic'
	}, {
		name : 'systemHelp'
	}, {
		name : 'active'
	}, {
		name : 'createUser'
	}, {
		name : 'createDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	}, {
		name : 'modifyUser'
	}, {
		name : 'modifyDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	} ]
});

login.openHelpWindow = function(sysHelpId){
	window.open("../login/sysHelpInfoById.action?sysHelpId="+sysHelpId);
}

Ext.define('Foss.main.SystemHelpGird', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.systemHelpGird',
	hideHeaders: true,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store
			});
		}
		return this.pagingToolbar;
	},
	initComponent: function() {
		var me = this;
		me.store = Ext.create('Ext.data.Store', {
			model: 'Foss.main.SystemHelpModel',
			pageSize : 5,
			autoLoad: true,
			proxy: {
		         type: 'ajax',
		         actionMethods : 'POST',
		         url: '../login/sytemHelpInfo.action',
		         reader: {
		             type: 'json',
		             totalProperty : 'totalCount',
		             root: 'sysHelpInfoList'
		         }
		     }
		});
		me.columns = [{
			xtype: 'templatecolumn', 
			tpl: '<a onclick="login.openHelpWindow(\'{id}\')" href="#">{topic}</a>',
			flex: 1
		}, {
			xtype: 'datecolumn',   
			format:'Y-m-d H:i:s',
			dataIndex : 'modifyDate',
			width: 135
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent();
	}
});
Ext.onReady(function() {
	var announcementGird = Ext.create('Foss.main.AnnouncementGird', {
		renderTo: 'announcementgird'
	});
	
	var systemHelpGird = Ext.create('Foss.main.SystemHelpGird', {
		renderTo: 'systemHelpGird'
	});
});