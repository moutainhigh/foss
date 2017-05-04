package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 综合查询
 * @author mt
 * 2013年7月17日18:47:06
 */
public interface GeneralQueryConstant {
	/**
	 * 查询货物追踪信息
	 */
	interface OPER_TYPE_AID_CRG_TRCE_QRY{
		public final static String VERSION = "AID_01";
	}
	/**
	 * 查询标题信息
	 */
	interface OPER_TYPE_AID_KNOWLEDGE_TITLE{
		public final static String VERSION = "KNOW_TITLE";
	}
	/**
	 * 查询内容信息
	 */
	interface OPER_TYPE_AID_KNOWLEDGE_CONTENT{
		public final static String VERSION = "KNOW_CONTENT";
	}
	
	/**
	 * 查询内容信息
	 */
	interface OPER_TYPE_AID_QUERY_POSITION{
		public final static String VERSION = "QUERY_POSITION";
	}
	
	/**
	 * 查询差错
	 */
	interface OPER_TYPE_AID_QUERY_MISTAKE{
		public final static String VERSION = "MISTAKE_DETAIL_GET";
	}
	
	/**
	 * 查询投诉
	 */
	interface OPER_TYPE_AID_QUERY_COMPLAINT{
		public final static String VERSION = "COMPLAINT_DETAIL_GET";
	}
	
	/**
	 * 查询访问量
	 */
	interface OPER_TYPE_AID_KNOW_QUERYSOUNT{
		public final static String VERSION = "KNOW_ACCESSS_TO";
	}
	/**
	 * China包自动更新
	 * @author 245955
	 *
	 */
	interface OPER_TYPE_AID_CHINA_EXPORTURL{
		public final static String VERSION = "RETURN_CHINA_EXPORTURL";
	}
	/**
	 * 查询近两天热点知识库
	 */
	interface OPER_TYPE_AID_KNOWLEDGE_TIME{
	public final static String VERSION = "KNOW_TIME";
	}
	/**
	 * 查询鹰眼位置开关
	 */
	interface OPER_TYPE_AID_EAGLEEYE_POSITION{
		public final static String VERSION = "EAGLEEYE_POSITION";
	}
}
