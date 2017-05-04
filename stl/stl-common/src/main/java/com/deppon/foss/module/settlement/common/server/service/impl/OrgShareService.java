package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.server.dao.IOrgShareEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IOrgShareService;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 
 * 理赔成本划分服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-27 下午2:16:52
 */
public class OrgShareService implements IOrgShareService {

	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());

	/**
	 * 理赔成功划分实体DAO
	 */
	private IOrgShareEntityDao orgShareEntityDao;

	/** 
	 * 新增理赔成本划分
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 下午2:22:29
	 * @param entity
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IOrgShareService#addOrgShare(com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity)
	 */
	@Override
	public void addOrgShare(OrgShareEntity entity) {

		// 实体为空，抛异常
		if (entity == null) {
			throw new SettlementException("理赔成本划分实体为空");
		}

		logger.info("Service start ... , sourceBillNo: " + entity.getSourceBillNo());

		// 新增实体
		int i = orgShareEntityDao.add(entity);
		
		if (i != 1) {
			throw new SettlementException("新增理赔成本划分出错");
		}

		logger.info("Service end ... , sourceBillNo: " + entity.getSourceBillNo());
	}

	/** 
	 * 查询成本划分 ，返回部门信息为   标杆编码
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 下午2:03:15
	 * @param entity 理赔成本划分实体
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IOrgShareEntityDao#add(com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity)
	 */
	@Override
	public List<OrgShareEntity> selectBySourceBillNo(String sourceBillNo,String active){
		//判断传入来源单号
		if(StringUtils.isBlank(sourceBillNo)){
			throw new SettlementException("传入来源单号不能为空");
		}
		return orgShareEntityDao.selectBySourceBillNo(sourceBillNo, active);
	}
	
	
	/**
	 * @param orgShareEntityDao
	 */
	public void setOrgShareEntityDao(IOrgShareEntityDao orgShareEntityDao) {
		this.orgShareEntityDao = orgShareEntityDao;
	}

}
