package com.deppon.pda.bdm.module.foss.crgreg.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.CrgregConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.crgreg.server.dao.ICrgRegDAO;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ExcpCrgOutInvtScanEntity;

/**
 * 
 * TODO(异常物品出库)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-1 下午2:43:52,content:TODO </p>
 * @author Administrator
 * @date 2012-12-1 下午2:43:52
 * @since
 * @version
 */
public class ExcpCrgOutInvtService implements IBusinessService<Void, ExcpCrgOutInvtScanEntity>{
	public final static int NUM = 4;
	private ICrgRegDAO crgRegDAO;
	private IPDAStockService pdaStockService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:29:29
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public ExcpCrgOutInvtScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ExcpCrgOutInvtScanEntity entity = JsonUtil.parseJsonToObject(ExcpCrgOutInvtScanEntity.class, asyncMsg.getContent());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:29:33
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, ExcpCrgOutInvtScanEntity param)
			throws PdaBusiException {
		log.debug("异常物品出库");
		this.validate(param);
		crgRegDAO.saveExcpCrgOutInvtScan(param);
		InOutStockEntity entity = new InOutStockEntity();
		entity.setWaybillNO(param.getWblCode());
		
		// mt 2013年5月28日11:34:25 
		if(param.getLabelCode() != null && param.getLabelCode().length() >= NUM){
			String label = param.getLabelCode();
			entity.setSerialNO(label.substring(label.length()-NUM,label.length()));
		}
		
		entity.setOrgCode(asyncMsg.getDeptCode());
		entity.setOperatorCode(asyncMsg.getUserCode());
		entity.setScanTime(param.getScanTime());
		entity.setPdaDeviceNO(asyncMsg.getPdaCode());
		entity.setInOutStockType(param.getOutInvtType());
		try {
			long startTime = System.currentTimeMillis();
			pdaStockService.outStockExceptionAreaPDA(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]异常物品出库接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("异常物品出库成功");
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:29:39
	 * @param param
	 * @see
	 */
	private void validate(ExcpCrgOutInvtScanEntity param) {
		Argument.notNull(param, "ExcpCrgOutInvtScanEntity");
		Argument.hasText(param.getLabelCode(), "ExcpCrgOutInvtScanEntity.labelCode");
		Argument.hasText(param.getWblCode(),  "ExcpCrgOutInvtScanEntity.wblCode");
		Argument.hasText(param.getOutInvtType(), "ExcpCrgOutInvtScanEntity.OutInvtType");
		Argument.notNull(param.getScanTime(), "ExcpCrgOutInvtScanEntity.scanTime");
	}

	@Override
	public String getOperType() {
		return CrgregConstant.OPER_TYPE_REG_EXCP_CRG_OUTINVT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setCrgRegDAO(ICrgRegDAO crgRegDAO) {
		this.crgRegDAO = crgRegDAO;
	}

	public IPDAStockService getPdaStockService() {
		return pdaStockService;
	}

	public void setPdaStockService(IPDAStockService pdaStockService) {
		this.pdaStockService = pdaStockService;
	}
	
}
