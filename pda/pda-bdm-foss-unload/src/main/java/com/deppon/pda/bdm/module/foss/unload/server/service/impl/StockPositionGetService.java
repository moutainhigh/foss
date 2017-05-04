package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressTransferScanDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.ExpressTransferScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.StockPositionResultEntity;

public class StockPositionGetService implements IBusinessService<StockPositionResultEntity, ExpressTransferScanEntity>{
	private Logger log = Logger.getLogger(getClass());
	private IPDATrayScanService pdaTrayScanService;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:43
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public ExpressTransferScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ExpressTransferScanEntity entity = JsonUtil.parseJsonToObject(ExpressTransferScanEntity.class, asyncMsg.getContent());
		
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:38
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public StockPositionResultEntity service(AsyncMsg asyncMsg, ExpressTransferScanEntity param)throws PdaBusiException {
	
	
	this.validate(param);
	
	ExpressTransferScanDto dto = new ExpressTransferScanDto();
	//外场编码
	dto.setOrgCode(param.getOrgCode());
	//包号（当有运单号及流水号时可为空）
	dto.setPackageNo(param.getPackageNo());
	//运单号（当包号存在时可为空）
	dto.setWayBillNo(param.getWayBillNo());
	//流水号号（当包号存在时可为空）
	dto.setSerialNo(param.getSerialNo());
			
		try {
			long startTime = System.currentTimeMillis();
			String result = pdaTrayScanService.queryGoodsAreaForPdaTrayScan(dto);
			
			log.debug("---调用FOSS获取零担货区返回结果："+result+"---");
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]调用FOSS获取零担货区:"+(endTime-startTime)+"ms");
			
			StockPositionResultEntity rs = new StockPositionResultEntity();		
			rs.setPositionResult(result);
			return rs;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	
	
	
	private void validate(ExpressTransferScanEntity param) throws ArgumentInvalidException{
		//检验卸车扫描非空
		Argument.notNull(param,"expressTransferScanEntity");
		//检验标签号非空
		Argument.hasText(param.getOrgCode(), "expressTransferScanEntity.orgCode");
		
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:49
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_SSTOCKPOSITION_GET.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}


}
