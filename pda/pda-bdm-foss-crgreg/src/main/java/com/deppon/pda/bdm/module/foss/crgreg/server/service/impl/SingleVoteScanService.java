package com.deppon.pda.bdm.module.foss.crgreg.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.CrgregConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.SingleVoteScanEntity;

/**
 * @author chenliang
 * @version 1.0
 * @created 2012-9-8 上午11:06:58
 */
public class SingleVoteScanService implements IBusinessService<Void, SingleVoteScanEntity> {
	private Logger log = Logger.getLogger(getClass());
	//private ICrgRegDAO crgRegDAO;
	private IPDAStockService pdaStockService;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:29:52
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public SingleVoteScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		SingleVoteScanEntity singleVoteScan = JsonUtil.parseJsonToObject(SingleVoteScanEntity.class, asyncMsg.getContent());
		singleVoteScan.setPdaCode(asyncMsg.getPdaCode());
		singleVoteScan.setScanUser(asyncMsg.getUserCode());
		singleVoteScan.setScanType(asyncMsg.getOperType());
		singleVoteScan.setDeptCode(asyncMsg.getDeptCode());
		return singleVoteScan;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:29:56
	 * @param asyncMsg
	 * @param singleVoteScan
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, SingleVoteScanEntity singleVoteScan)
			throws PdaBusiException {
		log.debug("单件入库开始");
		this.validate(singleVoteScan);
		log.debug("单件入库保存信息");
		//crgRegDAO.saveSingleVoteScan(singleVoteScan);
		InOutStockEntity entity = new InOutStockEntity();
		entity.setWaybillNO(singleVoteScan.getWblCode());
		entity.setSerialNO(singleVoteScan.getLabelCode());
		entity.setInOutStockType(singleVoteScan.getInInvtType());
		entity.setOrgCode(asyncMsg.getDeptCode());
		entity.setOperatorCode(asyncMsg.getUserCode());
		entity.setScanTime(singleVoteScan.getScanTime());
		entity.setPdaDeviceNO(asyncMsg.getPdaCode());
		try {
			pdaStockService.singleInStockPDA(entity);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("单件入库成功");
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:30:01
	 * @param singleVoteScan
	 * @see
	 */
	private void validate(SingleVoteScanEntity singleVoteScan) {
		Argument.hasText(singleVoteScan.getId(), "singleVoteScan.id");
		Argument.notNull(singleVoteScan.getScanTime(), "singleVoteScan.scanTime");
		Argument.notNull(singleVoteScan.getScanFlag(), "singleVoteScan.scanFlag");
		Argument.hasText(singleVoteScan.getInInvtType(), "singleVoteScan.inInvtType");
	}
	@Override
	public String getOperType() {
		return CrgregConstant.OPER_TYPE_REG_SNGL_VOTE_ININVT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
//	public void setCrgRegDAO(ICrgRegDAO crgRegDAO) {
//		this.crgRegDAO = crgRegDAO;
//	}

	public void setPdaStockService(IPDAStockService pdaStockService) {
		this.pdaStockService = pdaStockService;
	}
	
}
