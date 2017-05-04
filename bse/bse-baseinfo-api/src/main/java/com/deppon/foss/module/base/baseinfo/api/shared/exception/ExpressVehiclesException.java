package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class ExpressVehiclesException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6544915304578128725L;
	
    public static final String EXPRESSVEHICLES_ADD_SUCCESS = "foss.bse.baseinfo.expressVehiclesException.addSuccess";
    
    public static final String EXPRESSVEHICLES_ADD_FAILURE = "foss.bse.baseinfo.expressVehiclesException.addFailure";
    
    public static final String EXPRESSVEHICLES_DEL_SUCCESS = "foss.bse.baseinfo.expressVehiclesException.delSuccess";
    
    public static final String EXPRESSVEHICLES_DEL_FAILURE = "foss.bse.baseinfo.expressVehiclesException.delFailure";
    
    public static final String EXPRESSVEHICLES_UPD_SUCCESS = "foss.bse.baseinfo.expressVehiclesException.updSuccess";
    
    public static final String EXPRESSVEHICLES_UPD_FAILURE = "foss.bse.baseinfo.expressVehiclesException.updFailure";
    
    public static final String PARAMETER_ISNULL = "foss.bse.baseinfo.expressVehiclesException.parmeterIsNull";
    
    public static final String ID_ISNULL = "foss.bse.baseinfo.expressVehiclesException.idIsNull";

	public static final String VEHICLE_NO_ISNULL = "foss.bse.baseinfo.expressVehiclesException.vehicleNOIsNull";

	public static final String CREATER_ISNULL = "foss.bse.baseinfo.expressVehiclesException.createrIsNull";

	public static final String MODIFIER_ISNULL = "foss.bse.baseinfo.expressVehiclesException.modifiterIsNull";

	public static final String HASEMP = "foss.bse.baseinfo.expressVehiclesException.hasEmp";
    
    
    public ExpressVehiclesException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public ExpressVehiclesException(String code, String msg) {
	super(code, msg);
    }
}
