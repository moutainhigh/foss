package com.deppon.pda.bdm.module.foss.packaging.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackagingConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packaging.server.dao.IWrapDao;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedOutInvtEntity;
/**
 * 
 * TODO(包装出库)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 下午1:51:27,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 下午1:51:27
 * @since
 * @version
 */
public class WrapedOutInvtService implements IBusinessService<Void,WrapedOutInvtEntity>{
	private IWrapDao wrapDao;
	private IPDAStockService pdaStockService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:32:07
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public WrapedOutInvtEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		WrapedOutInvtEntity entity = JsonUtil.parseJsonToObject(WrapedOutInvtEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:32:12
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, WrapedOutInvtEntity param)
			throws PdaBusiException {
		log.debug("包装出库开始");
		this.validate(param);
		wrapDao.saveWrapOutinvtScan(param);
		InOutStockEntity entity = new InOutStockEntity();
		entity.setWaybillNO(param.getWblCode());
		entity.setSerialNO(param.getLabelCode());
		entity.setOrgCode(asyncMsg.getDeptCode());
		entity.setOperatorCode(asyncMsg.getUserCode());
		entity.setScanTime(param.getScanTime());
		entity.setPdaDeviceNO(asyncMsg.getPdaCode());
		try {
			long startTime = System.currentTimeMillis();
			pdaStockService.outStockPackageAreaPDA(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]包装出库接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("包装出库成功");
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:32:18
	 * @param param
	 * @see
	 */
	private void validate(WrapedOutInvtEntity param) {
		Argument.notNull(param, "WrapedOutInvtEntity");
		Argument.hasText(param.getLabelCode(), "WrapedOutInvtEntity.labelCode");
		Argument.hasText(param.getWblCode(),  "WrapedOutInvtEntity.wblCode");
		Argument.notNull(param.getScanTime(), "WrapedOutInvtEntity.scanTime");
	}
	@Override
	public String getOperType() {
		return PackagingConstant.OPER_TYPE_WRAP_OUTINVT_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setWrapDao(IWrapDao wrapDao) {
		this.wrapDao = wrapDao;
	}

	public void setPdaStockService(IPDAStockService pdaStockService) {
		this.pdaStockService = pdaStockService;
	}
	
	
}
