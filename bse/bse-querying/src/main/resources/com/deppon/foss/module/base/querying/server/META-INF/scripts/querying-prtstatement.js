/**
 * 营业报表清单
 *
 * @author 100847-foss-GaoPeng
 */
querying.salesstatement.printSalesStartement = function () {
	var mainPanel = Ext.getCmp('T_querying-salesStatementIndex_content');
	if (!Ext.isEmpty(mainPanel)) {
		var currentTab = mainPanel.down('tabpanel');
		var queryForm = mainPanel.getQueryForm().getForm();
		if (!Ext.isEmpty(currentTab) && !Ext.isEmpty(queryForm)) {
			var queryingParams = querying.salesstatement.searchConditionParams(
					mainPanel, currentTab, queryForm),
			targetObject = {};
			for (var i in queryingParams) {
				var name = i.toString().substr(i.toString().lastIndexOf('.') + 1);
				if (Ext.isEmpty(name)) {
					continue;
				} else {
					targetObject[name] = queryingParams[i];
				}
			}
			if (targetObject) {
				if (FossUserContext) {
					targetObject['currentSalesman'] = FossUserContext.getCurrentUserEmp().empName;
					targetObject['salesDepartment'] = FossUserContext.getCurrentDept().name;
					do_printpreview('salesstartement', targetObject, ContextPath.BSE_QUERYING);
				} else {
					querying.showInfoMsg(querying.salesstatement.i18n('foss.querying.warningCurrentUserError'));//警告：当前登录用户信息获取失败，请重新登录！
				}
			} else {
				querying.showInfoMsg(querying.salesstatement.i18n('foss.querying.warningConditionError'));//警告：查询参数收集异常！
			}
		} else {
			querying.showInfoMsg(querying.salesstatement.i18n('foss.querying.warningComError'));//警告：前段控件加载异常！
		}
	} else {
		querying.showInfoMsg(querying.salesstatement.i18n('foss.querying.warningComError'));//警告：前段控件加载异常！
	}
}