package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class StationOtherFuncAreaException extends BusinessException{

	
	
	 /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1824470438587792695L;
    
    // 
    public static final String STATIONOTHERFUNCAREA_CODE_NULL_ERROR_CODE = "foss.bse.bse-baseinfo.StationOtherFuncAreaCodeNullException";
    //null异常
    public static final String STATIONOTHERFUNCAREA_NULL_ERROR_CODE = "foss.bse.bse-baseinfo.StationOtherFuncAreaNullException";
    
    
    public StationOtherFuncAreaException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public StationOtherFuncAreaException(String code,String msg){
	
	super(code,msg);
    }
}
