package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 长途车队管理信息
 * @author 323136
 *
 */
public class VehicleDrivingException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 45991929604282656L;

	public static final String VehicleDriving_ADD_SUCCESS = "新增成功";
    
    public static final String VehicleDriving_ADD_FAILURE = "新增失败";
    
    public static final String VehicleDriving_DEL_SUCCESS = "作废成功";
    
    public static final String VehicleDriving_DEL_FAILURE = "作废失败";
    
    public static final String VehicleDriving_UPD_SUCCESS = "修改成功";
    
    public static final String VehicleDriving_UPD_FAILURE = "修改失败";

    public VehicleDrivingException(String code, String msg) {
	super(code, msg);
    }

    public VehicleDrivingException(String msg, Throwable cause) {
	super(msg, cause);
    }

    public VehicleDrivingException(String msg) {
	super(msg);
    }
}
