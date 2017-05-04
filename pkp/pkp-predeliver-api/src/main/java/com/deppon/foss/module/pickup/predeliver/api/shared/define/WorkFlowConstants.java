package com.deppon.foss.module.pickup.predeliver.api.shared.define;

/**
 * 工作流常量类
 * 
 * @author 136892
 * 
 */
public class WorkFlowConstants {

	public static final String AGREE = "agree";

	public static final String DISAGREE = "disagree";

	public static final String CANCEL = "cancel";
	
	/**
	 * 仓库异常货申请   流程定义名称
	 */
	public static final String STORE_EXCEPTION_GOODS = "com.deppon.bpms.module.foss.bpsdesign.logistics.storeExceptioGoods";

	/**
	 * 流程实例名称
	 */
	public static final String PROCESS_INST_NAME  = "仓库异常货物处理申请";
			
	/**
	 * 流程描述
	 */
	public static final String PROCESS_DESC = "仓库异常货物处理申请";
	
	/**
	 *storeExceptioGoodsExamine.action
	 */
	public static final String  STORE_EXCEPTION_GOODS_EXAMINE= "storeExceptioGoodsExamine";
	
	/**
	 * storeExceptioGoodsQuery.action
	 */
	public static final String  STORE_EXCEPTION_GOODS_QUERY= "storeExceptioGoodsQuery";
	
	
	public static final String TEMPORARY_DEB_LIMIT_QUERY="workFlowQuery";
	
	public static final String TEMPORARY_DEB_LIMIT_EXAMINE="workFlowExamine";
	
	public static final String TEMPORARY_DEB_LIMIT_DRAFT="workFlowDraft";
}
