package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 
 * 业务异常码描述:
 * 				BUS_SCHEEEMENAME_NULL:方案名为空
 * 		        BUS_AGENCYDEPTNAME_NOT_EXIST:目的地在系统中不存在
 *              BUS_AGENCYDEPTNAME_NULL:目的地为空
 *              BUS_OUTFIELDNAME_NOT_EXIST:外场在系统中不存在
 *              BUS_OUTFIELDNAME_NOT_NULL:外场为空
 *              BUS_TRANTYPE_NULL:运输类型为空
 *              BUS_HEAVYPRICE_ERROR:重货价格错误
 *              BUS_HEAVYPRICE_NULL:重货价格为空
 *              BUS_LIGHTPRICE_ERROR:轻货价格错误
 *              BUS_LIGHTPRICE_NULL:轻货价格为空
 *              BUS_LOWESTPRICE_ERROR:最低一票有误
 *              BUS_LOWESTPRICE_NULL:最低一票为空
 *              BUS_BEGINTIME_ERROR:生效日期错误
 *              BUS_BEGINTIME_NULL:未填写 生效日期
 *              BUS_ENTTIME_ERROR:结束日期错误
 * @author 200945-wutao
 * @date 2014-7-24
 */
public class ExternalPriceSchemeException extends BusinessException {

	
	/**
	 * 编写异常构造器的时候出现错误，导致出现前台无法正常显示相应的提示的信息
	 */
	private static final long serialVersionUID = -6267462287995508544L;
    
    public ExternalPriceSchemeException(String code,String msg){
    	super(code,msg);
    }
}
