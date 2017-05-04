package com.deppon.pda.bdm.module.ocb.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
//import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**  
 * 作者：xiaolongwu
 * 描述：集中开单，批量查询
 * 包名：com.deppon.pda.bdm.module.ocb.server.service.impl
 * 时间：2015-1-6 下午2:17:33
 */
public class BatchQueryWaybillPictureService implements IBusinessService<String, Void>{

	private static final Log LOG = LogFactory.getLog(BatchQueryWaybillPictureService.class);
	//private IPdaWaybillService pdaWaybillService;
	
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

	@Override
	public String service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		String waybillNos = asyncMsg.getContent();
		String rtn = "失败：";
		if(StringUtils.isNotEmpty(waybillNos)){
			try {
//				rtn = pdaWaybillService.batchQueryWaybillPictureByWaybillNos(waybillNos);
			} catch (Exception e) {
				LOG.error("FOSS接口调用返回异常",e);
				throw new BusinessException("FOSS接口调用返回异常:"+e.getMessage());
			}
		}else{
			rtn += "运单号为空";
		}
		return rtn;
	}

	/**
	 * @author：xiaolongwu
	 * @description：操作类型OCB_07，批量查询功能
	 * @parameters：void
	 * @return：OCB_07
	 * @time：2015-1-6 下午2:16:42
	 */
	@Override
	public String getOperType() {
		return OcbConstant.OPER_TYPE_OCB_BATCH_QUERY.VERSION;
	}

	/**
	 * @author：xiaolongwu
	 * @description：同步
	 * @parameters：void
	 * @return：false
	 * @time：2015-1-5 上午10:58:04
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * @parameters：注入pdaWaybillService
	 * @return：
	 */
//	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
//		this.pdaWaybillService = pdaWaybillService;
//	}
	
	
}
