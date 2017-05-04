package com.deppon.pda.bdm.module.core.server.httpService;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.ar.bamp.client.listener.RequestContainer;
import com.deppon.pda.bdm.module.core.server.monitor.MonitorControl;
import com.deppon.pda.bdm.module.core.server.service.IAsyncMsgUploadService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.server.service.IUploadService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.OperationConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ServiceNotLoadException;
import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;

  
/**     
 * 操作类       
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-6 下午09:55:50    
 */

public class OperationFactory {
	private static final Log LOG = LogFactory.getLog(OperationFactory.class);
	/**
	 * 
	 * <p>TODO(调用各个模块service)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午7:47:37
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @throws SQLException 
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object invokeService(AsyncMsg asyncMsg) throws PdaBusiException{
		if(OperationConstant.ASYNC_OPER_TYPE_LIST.contains(asyncMsg.getOperType())){						
			IAsyncMsgUploadService service = (IAsyncMsgUploadService)BeanFactory.getBean(OperationConstant.SERVICE_ASYNMSG_UPLOAD);
		
			//异步接入BAM监控
			RequestContainer.setMethodName(new StringBuffer().append("com.deppon.pda.bdm.module.core.server.service.impl.AsyncMsgUploadService.saveAsyncMsg").toString());
			RequestContainer.setNamespace("/pkp-pda-itf");
			RequestContainer.setEmpCode(asyncMsg.getUserCode());
						
			return service.saveAsyncMsg(asyncMsg);
		}else{
			String serviceBeanName = OperationConstant.OPER_SERVICE_MAP.get(asyncMsg.getOperType());
			IBusinessService service = (IBusinessService)BeanFactory.getBean(serviceBeanName);
			if(service == null){
				LOG.error("服务类未加载，类型为："+asyncMsg.getOperType());
				throw new ServiceNotLoadException();
			}

            //同步接入BAM监控
			String classname="";
			String methodname="";
			if (BeanFactory.getBean(serviceBeanName)!=null)
				{
				classname=BeanFactory.getBean(serviceBeanName).toString();			
				if (classname.indexOf("@")!=-1)					
					methodname = classname.substring(0,classname.indexOf("@"));
				}
			RequestContainer.setMethodName(new StringBuffer().append(methodname).append(".service").toString());
			RequestContainer.setNamespace("/pkp-pda-itf");
			if (asyncMsg!=null){
				RequestContainer.setEmpCode(asyncMsg.getUserCode());
			}
													
			Object param = service.parseBody(asyncMsg);			
			Object obj = service.service(asyncMsg, param);
									
			//将数据提交至监控
			MonitorControl.put(asyncMsg,Constant.MONITOR.NORMAL);
			return obj;
		}
	}
	/**
	 * 
	 * <p>TODO(异步调用方法)</p> 
	 * @author Administrator
	 * @date 2012-10-25 下午5:46:02
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object asyncService(AsyncMsg asyncMsg) throws PdaBusiException{
		String serviceBeanName = OperationConstant.OPER_SERVICE_MAP.get(asyncMsg.getOperType());
		IBusinessService service = (IBusinessService)BeanFactory.getBean(serviceBeanName);
		Object param = service.parseBody(asyncMsg);
		Object obj = service.service(asyncMsg, param);
		//将数据提交至监控
		MonitorControl.put(asyncMsg,Constant.MONITOR.NORMAL);		
		return obj;
	}
	/**
	 * 
	* @Title: uploadService 
	* @Description: 上传调用模块
	* @author 183272
	* @date 2014年10月11日 下午6:41:03 
	* @param @param json
	* @param @param opearType
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public static Object uploadService(AsyncMsg asyncMsg) {
		String serviceBeanName = OperationConstant.OPER_SERVICE_MAP.get(asyncMsg.getOperType());
		IUploadService service = (IUploadService)BeanFactory.getBean(serviceBeanName);
		Object param = service.parseBody(null);
		Object obj = service.service(asyncMsg, param);
		//将数据提交至监控
		MonitorControl.put(asyncMsg,Constant.MONITOR.NORMAL);	
		return obj;
	}
	
	
}
