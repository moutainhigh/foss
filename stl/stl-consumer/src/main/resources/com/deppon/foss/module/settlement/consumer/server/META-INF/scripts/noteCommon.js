/** ********************************小票单据常量******************************************* */
// 小票单据申请审批查询页面
consumer.note.QUERY_PAGE__APPLY = "APPLY";
// 小票单据发放查询页面
consumer.note.QUERY_PAGE__ISSUE = "ISSUE";
// 小票单据入库查询页面
consumer.note.QUERY_PAGE__STORAGE = "STORAGE";
// 小票单据核销查询页面
consumer.note.QUERY_PAGE__WRITEOFF = "WRITEOFF";

/**
 * STATUS 单据状态
 */
consumer.note.STATUS__SUBMIT = "S"; // 已提交
consumer.note.STATUS__DISTRIBUTE = "D"; // 已下发
consumer.note.STATUS__IN = "I"; // 已入库
consumer.note.STATUS__USED = "U";  // 已使用 

/**
 * 审批状态
 */
consumer.note.APPROVE_STATUS__NOT_AUDIT = 'NA';// 未审批
consumer.note.APPROVE_STATUS__REFUND_AGREE = 'AA';// 审批通过
consumer.note.APPROVE_STATUS__REFUND_DISAGREE = 'AD'; // 审批不通过

/**
 * 核销状态
 */
consumer.note.WRITEOFF_STATUS__NOT_WRITEOFF = "NW";// 未核销
consumer.note.WRITEOFF_STATUS__APPLY_WRITEOFF = "AW";// 申请核销
consumer.note.WRITEOFF_STATUS__WRITEOFF_DONE = "WD"; // 已核销

/**
 * 下发方式
 */
consumer.note.ISSUED_TYPE__EXPRESS = "E"; // 快递代理
consumer.note.ISSUED_TYPE__INTERNAL = "I"; // 内部带货
consumer.note.ISSUED_TYPE__PICKUP = "P"; // 自领

/**
 * 是否有效
 */
consumer.note.ACTIVE__YES = "Y"; // 有效
consumer.note.ACTIVE__NO = "N"; // 无效



/**
 * 日期范围常量
 */
consumer.note.DATELIMITDAYS = 31; // 日期相差最大天数30天

/**
 * 小票单据每本张数
 */
consumer.note.NOTENO = 50; // 目前DP业务是每本小单据是50张

/** ******************************公用方法*********************************************** */

/**
 * 重置
 */
consumer.note.reset = function() {
	this.up('form').getForm().reset();
};

/**
 * 单据状态Renderer
 */
consumer.note.statusRenderer = function(value) {
	
	//小票申请记录状态
	display = FossDataDictionary.rendererSubmitToDisplay(value,
			'NOTE_APPLICATION__STATUS');
	
	//小票明细单据状态
	if(display == value){
		display = FossDataDictionary.rendererSubmitToDisplay(value,'NOTE_DETAILS__STATUS');
	}
	
	return display;
}

/**
 * 审核状态Render
 */
consumer.note.approveStatusRenderer = function(value) {
	return FossDataDictionary.rendererSubmitToDisplay(value,
			'NOTE_APPLICATION__APPROVE_STATUS');
}

/**
 * 核销状态
 */
consumer.note.writeoffStatusRenderer = function(value) {
	return FossDataDictionary.rendererSubmitToDisplay(value,
			'NOTE_APPLICATION__WRITEOFF_STATUS');
}

/**
 * 是否有效
 */
consumer.note.activeRenderer = function(value) {
	switch (value) {
		case consumer.note.ACTIVE__YES:
			return consumer.note.i18n('foss.stl.consumer.note.common.yes');
		case consumer.note.ACTIVE__NO:
			return consumer.note.i18n('foss.stl.consumer.note.common.no');
		default:
			return null;
	}
}

/**
 * 日期范围校验
 * 
 * @curDate 开始日期
 * @endDate 结束日期
 * @dateLimitDays 日期最大差值
 */
consumer.note.validateDateDiff = function(curDate, endDate, dateLimitDays,
		alertInfo) {
	var diffDays = stl.compareTwoDate(curDate, endDate);
	if (diffDays > dateLimitDays) {
		Ext.Msg.alert(
						consumer.note.i18n('foss.stl.consumer.common.warmTips'),
						alertInfo+ consumer.note.i18n('foss.stl.consumer.note.commmon.startAndEndDateDiffCannotExceedSomeDays',[ dateLimitDays ]));
		return true;
	} else if (diffDays < 1) {
		Ext.Msg.alert(
						consumer.note.i18n('foss.stl.consumer.common.warmTips'),
						alertInfo+consumer.note.i18n('foss.stl.consumer.note.common.startDateCannotGtEndDate.pleaseSelectAgain'));
		return true;
	}
	return false;
};

consumer.note.beginAndEndNoConverter = function(value){
	if(Ext.isEmpty(value)){
		return ''
	}
	return value
}
/** ******************************公用组件定义*********************************************** */

