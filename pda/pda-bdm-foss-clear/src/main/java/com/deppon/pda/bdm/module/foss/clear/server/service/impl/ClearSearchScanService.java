package com.deppon.pda.bdm.module.foss.clear.server.service.impl;


import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.stock.api.server.service.IFindGoodsAdminService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.FindGoodsAdminScanEntity;

/**
 * 找货任务扫描
 * @author 245955
 *
 */
public class ClearSearchScanService implements IBusinessService<Void,FindGoodsAdminScanEntity> {
	
	private IFindGoodsAdminService findGoodsAdminService;
	private IClearDao clearDao;
	
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 解析包头
	 */
	@Override
	public FindGoodsAdminScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		FindGoodsAdminScanEntity entity=JsonUtil.parseJsonToObject(FindGoodsAdminScanEntity.class, asyncMsg.getContent());
		entity.setOrgCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	/**
	 * 找货任务扫描Servier类
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, FindGoodsAdminScanEntity param)
			throws PdaBusiException {
		this.validate(param);
		param.setSyncStatus(Constant.SYNC_STATUS_INIT);
		//保存找货扫描信息
		clearDao.saveFindGoodsAdminScan(param);
		try {
			//调用FOSS接口
			long startTime = System.currentTimeMillis();
			findGoodsAdminService.scanFGoodsfgoodsDetail(param.getWaybillNo(), param.getSerialNo(), param.getUser(), param.getOrgCode());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]找货扫描接口消耗时间:"+(endTime-startTime)+"ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
	}
    /**
     * 操作类型
     */
	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_SEARCH_SCAN.VERSION;
	}
    /**
     * 是否异步
     */
	@Override
	public boolean isAsync() {
		return true;
	}
	/**
	 * 非空校验
	 * @param entity
	 * @throws ArgumentInvalidException
	 */
	private void validate(FindGoodsAdminScanEntity entity)
			throws ArgumentInvalidException {
		//检验扫描非空
		Argument.notNull(entity, "FindGoodsAdminScanEntity");
		//运单号非空
		Argument.hasText(entity.getWaybillNo(), "FindGoodsAdminScanEntity.waybillNo");
		//流水号非空
		Argument.hasText(entity.getSerialNo(), "FindGoodsAdminScanEntity.serialNo");
	}

	public void setFindGoodsAdminService(
			IFindGoodsAdminService findGoodsAdminService) {
		this.findGoodsAdminService = findGoodsAdminService;
	}

	public void setClearDao(IClearDao clearDao) {
		this.clearDao = clearDao;
	}
	
}
