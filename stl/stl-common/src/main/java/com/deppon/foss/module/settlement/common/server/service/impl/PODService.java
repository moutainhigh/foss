package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IPODEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IPODService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 财务签收记录表 Service
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-10 下午2:13:35
 * @since
 * @version
 */
public class PODService implements IPODService{
	
	private static final Logger logger = LogManager.getLogger(PODService.class);
	
	/**
	 * 财务签收记录DAO接口
	 */
	private IPODEntityDao podEntityDao;

	/**
	 * 新增财务签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午2:03:38
	 * @param entity      财务签收记录
	 * @param currentInfo 当前用户信息
	 */
	@Override
	public void addPOD(PODEntity entity,CurrentInfo currentInfo) {
		
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getWaybillNo())
				|| StringUtils.isEmpty(entity.getPodType())
				|| entity.getPodDate() == null) {
			throw new SettlementException("新增财务签收记录参数不能为空");
		}
		
		logger.info(" ----Start 新增财务签收记录，运单号： "+entity.getWaybillNo());
		
		Date date = new Date();
		entity.setCreateTime(date);
		entity.setPodUserCode(currentInfo.getEmpCode());
		entity.setPodUserName(currentInfo.getEmpName());
		entity.setPodOrgCode(currentInfo.getCurrentDeptCode());
		entity.setPodOrgName(currentInfo.getCurrentDeptName());
		
		int i = podEntityDao.add(entity);
		if (i != 1) {
			throw new SettlementException("保存财务签收记录失败");
		}
		
		logger.info("---End 新增财务签收记录 ");
	}

	/**
	 * 根据运单号查询运单的财务签收记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午2:04:30
	 * @param waybillNo  运单号
	 * @param podType    签收/反签收类型
	 * @return
	 */
	@Override
	public List<PODEntity> queryByWaybillNo(String waybillNo, String podType) {
		if(StringUtils.isEmpty(waybillNo)){
			throw new SettlementException("查询参数不能为空");
		}
		return this.podEntityDao.queryByWaybillNo(waybillNo, podType);
	}
	
	/**
	 * 根据运单号查询运单的最新财务签收记录
	 * @author 092036-foss-bochenlong
	 * @date 2013-07-24 下午6:43:00
	 * @param waybillNo  运单号
	 * @return PODEntity
	 */
	@Override
	public PODEntity queryNewestPOD(String waybillNo) {
		if(StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("查询参数不能为空");
		}
		return this.podEntityDao.queryNewestPOD(waybillNo);
	}
	
	/**
	 * @param podEntityDao
	 */
	public void setPodEntityDao(IPODEntityDao podEntityDao) {
		this.podEntityDao = podEntityDao;
	}

}
