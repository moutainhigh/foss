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
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ValIninvtScanEntity;

/**
 * 
 * TODO(贵重物品入库)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-1 上午11:53:34,content:TODO </p>
 * @author Administrator
 * @date 2012-12-1 上午11:53:34
 * @since
 * @version
 */
public class ValIninvtScanService implements IBusinessService<Void, ValIninvtScanEntity>{
	//private ICrgRegDAO crgRegDAO;
	private IPDAStockService pdaStockService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:30:09
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public ValIninvtScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ValIninvtScanEntity entity = JsonUtil.parseJsonToObject(ValIninvtScanEntity.class, asyncMsg.getContent());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setDeptCode(asyncMsg.getDeptCode());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:30:13
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, ValIninvtScanEntity param)
			throws PdaBusiException {
		log.debug("贵重物品入库");
		this.validate(param);
		//crgRegDAO.saveValIninvtScan(param);
		InOutStockEntity entity = new InOutStockEntity();
		entity.setWaybillNO(param.getWblCode());
		entity.setSerialNO(param.getLabelCode());
		entity.setOrgCode(asyncMsg.getDeptCode());
		entity.setOperatorCode(asyncMsg.getUserCode());
		entity.setScanTime(param.getScanTime());
		entity.setPdaDeviceNO(asyncMsg.getPdaCode());
		try {
			pdaStockService.inStockValuableAreaPDA(entity);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("贵重物品入库成功");
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:30:18
	 * @param param
	 * @see
	 */
	private void validate(ValIninvtScanEntity param) {
		Argument.notNull(param, "ValIninvtScanEntity");
		Argument.hasText(param.getLabelCode(), "ValIninvtScanEntity.labelCode");
		Argument.hasText(param.getWblCode(),  "ValIninvtScanEntity.wblCode");
		Argument.notNull(param.getScanTime(), "ValIninvtScanEntity.scanTime");
	}

	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return CrgregConstant.OPER_TYPE_REG_VAL_ININVT.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

//	public void setCrgRegDAO(ICrgRegDAO crgRegDAO) {
//		this.crgRegDAO = crgRegDAO;
//	}

	public void setPdaStockService(IPDAStockService pdaStockService) {
		this.pdaStockService = pdaStockService;
	}
	
}
