package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.QryLoadInfo;
/**
 * 快递装车提交检验（是否存在多件 货物异常）
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class KdLoadSubmitCheckService implements IBusinessService<List<String>, QryLoadInfo>{
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	private static final Log LOG = LogFactory.getLog(KdLoadSubmitCheckService.class);
	private ILoadDao loadDao;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:59
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public QryLoadInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QryLoadInfo model = JsonUtil.parseJsonToObject(QryLoadInfo.class, asyncMsg.getContent());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:05
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public List<String> service(AsyncMsg asyncMsg, QryLoadInfo param)
			throws PdaBusiException {
		this.validate(param);
		List<String> result = null;
		long start = System.currentTimeMillis();
		try {
			if(loadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			result =  pdaExpressDeliverLoadService.queryWayBillNo(param.getTaskCode());//根据任务号查询异常运单号
			LOG.info("调用FOSS接口所需时间："+(System.currentTimeMillis()-start));
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return result;
	}
	
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:15
	 * @param qryLoadInfo
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(QryLoadInfo qryLoadInfo) throws ArgumentInvalidException{
		Argument.notNull(qryLoadInfo, "qryLoadInfo");
		//任务号非空
		Argument.hasText(qryLoadInfo.getTaskCode(), "qryLoadInfo.taskCode");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_EXP_LOAD_SUB_CHE.VERSION;
	}
	
	@Override
	public boolean isAsync() {
		return false;//同步
	}
	
	/**
	 * @param pdaExpressDeliverLoadService the pdaExpressDeliverLoadService to set
	 */
	public void setPdaExpressDeliverLoadService(
			IPDAExpressDeliverLoadService pdaExpressDeliverLoadService) {
		this.pdaExpressDeliverLoadService = pdaExpressDeliverLoadService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	

	
	
}
