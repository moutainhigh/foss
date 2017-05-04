package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 包装
 * @author mt
 * 2013年7月17日18:43:14
 */
public interface PackagingConstant {
	/**
	 * 入库登记
	 */
	interface OPER_TYPE_WRAP_ININVT_SCAN{
		public final static String VERSION = "WRAP_01";
	}

	/**
	 * 查看待包装货物
	 */
	interface OPER_TYPE_WRAP_QUERY{
		public final static String VERSION = "WRAP_02";
	}

	/**
	 * 打包扫描
	 */
	interface OPER_TYPE_WRAP_SCAN{
		public final static String VERSION = "WRAP_03";
	}

	/**
	 * 出库登记
	 */
	interface OPER_TYPE_WRAP_OUTINVT_SCAN{
		public final static String VERSION = "WRAP_04";
	}
	
	/**
	 * 获取包装供应商
	 */
	interface OPER_TYPE_WRAP_SUPPLIER_SCAN{
		public final static String VERSION = "WRAP_05";
	}
}
