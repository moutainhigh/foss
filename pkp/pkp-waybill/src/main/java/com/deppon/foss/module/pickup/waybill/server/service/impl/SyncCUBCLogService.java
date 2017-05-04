package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISyncCUBCLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISyncCUBCLogService;
import com.deppon.foss.module.pickup.waybill.shared.domain.CUBCLogEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * SyncCUBCLogService
 * @author 198771-zhangwei
 * 2016-10-13 上午9:37:04
 */
public class SyncCUBCLogService implements ISyncCUBCLogService {

	private Logger logger = LoggerFactory.getLogger(SyncCUBCLogService.class);
	
	private ISyncCUBCLogDao syncCUBCLogDao;

	/**
	 * @param syncCUBCLogDao the syncCUBCLogDao to set
	 */
	public void setSyncCUBCLogDao(ISyncCUBCLogDao syncCUBCLogDao) {
		this.syncCUBCLogDao = syncCUBCLogDao;
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-10-13 上午9:37:08
	 */
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	@Override
	public void insert(CUBCLogEntity cubcLogEntity) {
		try {
			if(cubcLogEntity == null){
				return;
			}
			//id
			if(StringUtil.isBlank(cubcLogEntity.getId())){
				cubcLogEntity.setId(UUIDUtils.getUUID());
			}
			//创建时间
			if(cubcLogEntity.getCreateTime()==null){
				cubcLogEntity.setCreateTime(new Date());
			}
			//是否启用
			if(cubcLogEntity.getActive()==null){
				cubcLogEntity.setActive(FossConstants.ACTIVE);
			}
			//插入日志
			syncCUBCLogDao.insertCUBCLog(cubcLogEntity);
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
	public void updateByWaybillNoAndCode(CUBCLogEntity cubcLogEntity) {
		try {
			if(cubcLogEntity==null||StringUtil.isEmpty(cubcLogEntity.getWaybillNo())||StringUtil.isEmpty(cubcLogEntity.getCodeOrUrl())){
				return;
			}
			//作废日志
			syncCUBCLogDao.updateByWaybillNoAndCode(cubcLogEntity);
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
	public void updateById(CUBCLogEntity cubcLogEntity){
		try {
			if(cubcLogEntity==null||StringUtil.isEmpty(cubcLogEntity.getId())){
				return;
			}
			syncCUBCLogDao.updateById(cubcLogEntity);
		} catch (Exception e) {
			logger.debug("进入 cubc updateById 异常信息：" + e.getMessage());
		}
	}
}
