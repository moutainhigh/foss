package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 派送货区行政区域映射信息Exception
 * @author WeiXing
 *
 */
public class SendDistrictMapException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 分区名称或者行政区域为不存在
	 */
	public static final String SENDDISTRICTMAP_PARMS_ERROR="分区名称或者行政区域为不存在";
	/**
	 * 参数不合法
	 */
	public static final String SENDDISTRICTMAP_PARMS_NULL="参数不合法";
	/**
	 * 件区信息不存在
	 * SENDDISTRICTMAP_ITEMAREA_PARMS_NULL
	 */
	public static final String SENDDISTRICTMAP_ITEMAREA_PARMS_NULL="件区信息不存在";
	
	/**
	 * 外场不存在
	 */
	public static final String SENDDISTRICTMAP_TRANSFERCENTERCODE_NULL="外场不存在";
	/**
	 * 驻地派送库区不存在
	 */
	public static final String SENDDISTRICTMAP_GOODSAREACODE_NULL="驻地派送库区不存在";
	/**
	 * 分区不存在
	 */
	public static final String SENDDISTRICTMAP_ZONENAME_NULL="分区不存在";
	/**
	 * 行政区域不存在
	 */
	public static final String SENDDISTRICTMAP_DISTRICTCODE_NULL="行政区域不存在";
	/**
	 * 货区类型不存在
	 */
	public static final String SENDDISTRICTMAP_GOODSTYPE_NULL="货区类型不存在";
	public SendDistrictMapException(String code, String msg) {
		super(code, msg);
	}

	public SendDistrictMapException(String msg) {
		super(msg);
	
	}

	
	
}
