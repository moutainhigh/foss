package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.CrgAreaCodeInfo;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.GetCrgAreaCode;
/**
 * 
 * @package com.deppon.pda.bdm.module.foss.clear.server.service.impl 
 * @file GetCrgAreaCodeService.java 
 * @description  获取获取编码
 * @author xujun
 * @created 2012-12-27 上午3:19:37    
 * @version 1.0
 */
public class GetCrgAreaCodeService implements IBusinessService<CrgAreaCodeInfo, GetCrgAreaCode> {
	private static final Log LOG = LogFactory.getLog(GetCrgAreaCodeService.class);
	
	private IPdaStockcheckingService pdaStockcheckingService;
	
	@Override
	public GetCrgAreaCode parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		GetCrgAreaCode areaCode = JsonUtil.parseJsonToObject(GetCrgAreaCode.class, asyncMsg.getContent());
		return areaCode;
	}

	@Transactional
	@Override
	public CrgAreaCodeInfo service(AsyncMsg asyncMsg, GetCrgAreaCode param)
			throws PdaBusiException {
		LOG.info("get cargo Area start...");
		this.validate(asyncMsg,param);
		String goodsAreaCode = null;
		try {
			goodsAreaCode = pdaStockcheckingService.queryGoodsAreaCode(param.getWblCode(), 
					param.getSerialCode(), 
					asyncMsg.getDeptCode());
		} catch (BusinessException e) {
			LOG.error("获取货区编号异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.info("货区编号："+goodsAreaCode);
		CrgAreaCodeInfo areaCodeInfo = new CrgAreaCodeInfo();
		areaCodeInfo.setCrgAreaCode(goodsAreaCode);
		
		LOG.info("get cargo Area end...");
		return areaCodeInfo;
	}

	private void validate(AsyncMsg asyncMsg, GetCrgAreaCode param) {
		Argument.notNull(param, "GetCrgAreaCode");
		Argument.hasText(param.getWblCode(), "GetCrgAreaCode.WblCode");
		Argument.hasText(param.getSerialCode(), "GetCrgAreaCode.SerialCode");
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.DeptCode");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_AREA_GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}

	
}
