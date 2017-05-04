package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISyncWaybillLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISyncWaybillLogService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * SyncWaybillLogService
 * @author 198771-zhangwei
 * 2016-10-13 上午9:37:04
 */
public class SyncWaybillLogService implements ISyncWaybillLogService {

	private Logger logger = LoggerFactory.getLogger(SyncWaybillLogService.class);
	
	private ISyncWaybillLogDao syncWaybillLogDao;

	/**
	 * @param syncWaybillLogDao the syncWaybillLogDao to set
	 */
	public void setSyncWaybillLogDao(ISyncWaybillLogDao syncWaybillLogDao) {
		this.syncWaybillLogDao = syncWaybillLogDao;
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-10-13 上午9:37:08
	 */
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	@Override
	public void insert(WaybillLogEntity waybillLogEntity) {
		try {
			if(waybillLogEntity == null){
				return;
			}
			//id
			if(StringUtil.isBlank(waybillLogEntity.getId())){
				waybillLogEntity.setId(UUIDUtils.getUUID());
			}
			//创建时间
			if(waybillLogEntity.getCreateTime()==null){
				waybillLogEntity.setCreateTime(new Date());
			}
			//是否启用
			if(waybillLogEntity.getActive()==null){
				waybillLogEntity.setActive(FossConstants.ACTIVE);
			}
			//插入日志
			syncWaybillLogDao.insertWaybillLog(waybillLogEntity);
		} catch (Exception e) {
			logger.debug("进入 cubc insert 异常信息：" + e.getMessage());
		}
	}



	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-11-10 下午5:16:51
	 */
	@Override
	public void deleteById(WaybillLogEntity waybillLogEntity) {
		try {
			if(waybillLogEntity==null||StringUtil.isEmpty(waybillLogEntity.getId())){
				return;
			}
			//作废日志
			syncWaybillLogDao.deleteById(waybillLogEntity);
		} catch (Exception e) {
			logger.debug("进入 cubc updateByWaybillNoAndCode 异常信息：" + e.getMessage());
		}
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-11-11 上午9:41:12
	 */
	@Override
	public void updateById(WaybillLogEntity waybillLogEntity){
		try {
			if(waybillLogEntity==null||StringUtil.isEmpty(waybillLogEntity.getId())){
				return;
			}
			syncWaybillLogDao.updateById(waybillLogEntity);
		} catch (Exception e) {
			logger.debug("进入 cubc updateById 异常信息：" + e.getMessage());
		}
	}
	/**
	 * 根据条件查询推送CUBC接口日志
	 * @return
	 */
	@Override
	public List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto) {
		return syncWaybillLogDao.queryLogEntityByCondition(queryDto);
	}
}
