package com.deppon.pda.bdm.module.core.server.service;

import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

  
/**         
 * 业务服务类    
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-6 下午09:28:03    
 */

public interface IUploadService<R, T> {
	
	
	/**
	 * 解析包体
	 * @param asyncMsg
	 * @return
	 */
	public T parseBody(AsyncMsg asyncMsg) throws PdaBusiException;
	
	/**
	 * 
	 * <p>TODO(业务方法)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午7:48:50
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @see
	 */
	public R service(AsyncMsg asyncMsg, T param) throws PdaBusiException;
	/**
	 * 
	 * <p>TODO(操作类型)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午7:49:19
	 * @return
	 * @see
	 */
	public String getOperType();
	/**
	 * 
	 * <p>TODO(是否异步)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午7:49:08
	 * @return
	 * @see
	 */
	public boolean isAsync();
}
