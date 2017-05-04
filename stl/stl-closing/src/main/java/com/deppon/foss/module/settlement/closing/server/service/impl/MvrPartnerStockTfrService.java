package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPartnerStockTfrDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPartnerStockTfrService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpStEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpStDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-18 下午2:27:18    
 */
public class MvrPartnerStockTfrService implements IMvrPartnerStockTfrService {

	
	/**
	 * 记录日志 
	 */
	private static final Logger logger = LoggerFactory.getLogger(MvrPtpPscService.class);
	
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService  orgAdministrativeInfoService;

	/**
	 * 注入合伙人股份中转报表dao
	 */
	private IMvrPartnerStockTfrDao mvrPartnerStockTfrDao;
	
	/**
	 * 查询合伙人股份中转月报表分页条数
	 * @param dto
	 * @return dto
	 * @throws SettlementException
     */
	@Override
	public Long queryMvrPtpStCount(MvrPtpStDto mvrPtpStDto) {
		Long count = 0L;
		try {
			 count = this.mvrPartnerStockTfrDao.queryMvrPtpStCount(mvrPtpStDto);
		} catch (SettlementException e){
			logger.error(e.getMessage());
			throw  new SettlementException("","查询总记录数失败！ "+e.getMessage());
		}
		return count;
	}
	
	/**
	 * 查询合伙人股份中转月报表集合
	 * @param dto
	 * @return dto
	 * @throws SettlementException
     */
	@Override
	public MvrPtpStDto queryMvrPtpStEntityList(MvrPtpStDto mvrPtpStDto,int start, int limit) {
		//调用dao查询合伙人股份中转月报表集合
		List<MvrPtpStEntity> list = null;
		try {
			list = this.mvrPartnerStockTfrDao.queryMvrPtpStEntityList(mvrPtpStDto,start,limit);
			//异常
		} catch (SettlementException e){
			logger.error(e.getMessage());
			throw new SettlementException("","查询失败！ "+e.getMessage());
		}
		//集合非空判断
		if(CollectionUtils.isEmpty(list)){
			throw  new SettlementException("查询合伙人中转月报表为空！");
		}
		mvrPtpStDto.setMvrPtpStEntityList(list);
		return mvrPtpStDto;
	}

	public void setMvrPartnerStockTfrDao(
			IMvrPartnerStockTfrDao mvrPartnerStockTfrDao) {
		this.mvrPartnerStockTfrDao = mvrPartnerStockTfrDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	

}
