package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 *上传
 * @author mt
 * 2013年7月17日18:42:36
 */
public interface OcbConstant {
	/**
	 * 运单图片分块上传
	 */
	interface OPER_TYPE_OCB_IMG_SNIP{
 //		public final static String VERSION = "UPLD_01";
		public final static String VERSION = "OCB_01";
	}
	/**
	 * 图片运单查询分块上传  byss运单号 
	 */
	interface OPER_TYPE_OCB_QWBP_WBN{
		public final static String VERSION = "OCB_02";
	}
	/**
	 * 推送状态反馈
	 */
	interface OPER_TYPE_OCB_PMS_WBC{
		public final static String VERSION = "OCB_03";
	}
	/**
	 * 异常信息反馈
	 */
	interface OPER_TYPE_OCB_EXCP_RCV{
		public final static String VERSION = "OCB_04";
	}
	
	/**  
	 * 作者：xiaolongwu
	 * 描述：流量保存
	 * 包名：com.deppon.pda.bdm.module.core.shared.constants.version
	 * 时间：2014-12-30 下午3:45:24
	 */
	interface OPER_TYPE_OCB_FLOW_SAV{
		public final static String VERSION = "OCB_05";
	}
	

	/**  
	 * 作者：xiaolongwu
	 * 描述：订单取消
	 * 包名：com.deppon.pda.bdm.module.core.shared.constants.version
	 * 时间：2014-12-30 下午3:45:24
	 */
	interface OPER_TYPE_OCB_WAYBILL_CAN{
		public final static String VERSION = "OCB_06";
	}
	
	/**  
	 * 作者：xiaolongwu
	 * 描述：批量查询功能，目前没用到，暂时保存
	 * 包名：com.deppon.pda.bdm.module.core.shared.constants.version
	 * 时间：2014-12-30 下午3:45:24
	 */
	interface OPER_TYPE_OCB_BATCH_QUERY{
		public final static String VERSION = "OCB_07";
	}
	
	
	/**
	 * @author 201638
	 * @description 自动注册功能
	 */
	interface OPER_TYPE_OCB_DEVICE_REGIST{
		public final static String VERSION = "OCB_08";
	}
	
}
