package com.deppon.foss.module.pickup.sign.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.sign.api.server.dao.IClaimStatusDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IClaimStatusService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

/**
 * 查询更改理赔状态接口
 */
public class ClaimStatusService implements IClaimStatusService {
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PtpSignService.class);
	/**
	 * 查询更改理赔状态Dao
	 */
	private IClaimStatusDao ClaimStatusDao;
	
	public void setClaimStatusDao(IClaimStatusDao claimStatusDao) {
		ClaimStatusDao = claimStatusDao;
	}
	
	/**
     * 根据运单号设置理赔状态为理赔中
     * @author foss-xujie
     * @date 2016-6-24
     * @param WaybillSignResultEntity
	 * @return 
     */
	@Override
	public int setStatus(WaybillSignResultEntity entity) {
		LOGGER.info("设置理赔状态为理赔中");
		return ClaimStatusDao.setStatus(entity);
	}

	/**
     * 根据运单号取消理赔状态
     * @author foss-xujie
     * @date 2016-6-24
     * @param WaybillSignResultEntity
	 * @return 
	 * @return 
     * @return
     */
	@Override
	public int cancelStatus(WaybillSignResultEntity entity) {
		LOGGER.info("取消理赔状态");
		return ClaimStatusDao.cancelStatus(entity);
		
	}

	/**
     * 根据运单号查询理赔状态
     * @author foss-xujie
     * @date 2016-6-24
     * @param WaybillSignResultEntity
	 * @return 
	 * @return 
	 * @return 
     * @return
     */
	@Override
	public String getStatus(WaybillSignResultEntity entity) {
		LOGGER.info("查询理赔状态");
		return ClaimStatusDao.getStatus(entity);
	}

}
