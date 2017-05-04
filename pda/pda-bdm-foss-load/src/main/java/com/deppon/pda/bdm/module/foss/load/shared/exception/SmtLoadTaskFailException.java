package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**     
 *      
 * 提交任务用户非创建任务用户
 *     
 * @author gaojia       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */
public class SmtLoadTaskFailException extends PdaBusiException{

	private static final long serialVersionUID = 1L;
	
	public SmtLoadTaskFailException(){
		super();
	}
	
	public SmtLoadTaskFailException(Throwable cause){
		super(cause);
	}
	@Override
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_SMTISNOTADMIN_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "提交任务失败!";
	}
}
