package com.deppon.foss.module.transfer.stock.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 
* @ClassName: FindGoodsAdminException
* @Description: 找货管理Exception
* @author 189284--ZX
* @date 2015-7-10 下午4:43:49
*
 */
public class FindGoodsAdminException  extends BusinessException {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = 1L;
	
	public FindGoodsAdminException(String code){
		super();
		this.errCode = code;
	}

}
