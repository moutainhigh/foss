package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * TODO(ExpressPrintStar的异常处理类)
 * @author 187862-dujunhui
 * @date 2014-5-21 上午9:53:42
 * @since
 * @version
 */
public class ExpressPrintStarException extends BusinessException {

	private static final long serialVersionUID = -4439656353780994235L;
	
	public static final String EXPRESSPRINTSTAR_CODE_EXIST = "foss.bse.baseinfo.expressPrintStar.expressPrintStarCodeExist";
    public static final String EXPRESSPRINTSTAR_TYPE_EXIST = "foss.bse.baseinfo.expressPrintStar.expressPrintStarTypeExist";
    public static final String EXPRESSPRINTSTAR_TYPE_UNIQUE_EXIST = "foss.bse.baseinfo.expressPrintStar.expressPrintStarTypeUniqueExist";
    
    public ExpressPrintStarException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public ExpressPrintStarException(String code,String msg){
	super(code,msg);
    }

}
