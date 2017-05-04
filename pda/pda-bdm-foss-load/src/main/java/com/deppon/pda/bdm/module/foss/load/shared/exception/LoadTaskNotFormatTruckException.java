package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**     
 *      
 * 装车任务与车牌号不匹配异常
 *     
 * @author gaojia       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */
public class LoadTaskNotFormatTruckException extends PdaBusiException{

	private static final long serialVersionUID = 1L;
	
	public LoadTaskNotFormatTruckException(){
		super();
	}
	
	public LoadTaskNotFormatTruckException(Throwable cause){
		super(cause);
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_TASKNOMACTHTRUCK_ERR;
	}

	@Override
	public String getDefaultMessage() {
		return "该任务与车牌号不匹配!";
	}

}
