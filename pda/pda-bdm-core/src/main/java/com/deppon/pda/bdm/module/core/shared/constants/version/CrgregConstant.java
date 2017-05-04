package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 库存
 * @author mt
 * 2013年7月17日18:44:54
 */
public interface CrgregConstant {
	/**
	 * 贵重物品入库
	 */
	interface OPER_TYPE_REG_VAL_ININVT{
		public final static String VERSION = "REG_01";
	}

	/**
	 * 贵重物品出库
	 */
	interface OPER_TYPE_REG_VAL_OUTINVT{
		public final static String VERSION = "REG_02";
	}

	/**
	 * 异常货物入库
	 */
	interface OPER_TYPE_REG_EXCP_CRG_ININVT{
		public final static String VERSION = "REG_03";
	}

	/**
	 * 异常货物出库
	 */
	interface OPER_TYPE_REG_EXCP_CRG_OUTINVT{
		public final static String VERSION = "REG_04";
	}

	/**
	 * 单票入库
	 */
	interface OPER_TYPE_REG_SNGL_VOTE_ININVT{
		public final static String VERSION = "REG_05";		
	}
	
	/**
	 * 
	  * @ClassName OPER_TYPE_REG_STOCK_POSTION_LIST 
	  * @Description TODO 获取库位列表
	  * @author mt hyssmt@vip.qq.com
	  * @date 2013-9-16 上午11:24:58
	 */
	interface OPER_TYPE_REG_STOCK_POSTION_LIST{
		public final static String VERSION = "REG_06";
	}
	
	/**
	 * 
	  * @ClassName OPER_TYPE_REG_IN_STOCK_POSTION 
	  * @Description TODO 扫描货物入库位
	  * @author mt hyssmt@vip.qq.com
	  * @date 2013-9-16 上午11:27:08
	 */
	interface OPER_TYPE_REG_IN_STOCK_POSTION{
		public final static String VERSION = "REG_07";
	}
}
