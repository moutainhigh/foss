package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpDqpaDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpDqpaService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpDqpaEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpDqpaQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 合伙人德启代付月报表service
 * 
 * @author gpz
 * @date 2016年3月21日
 */
public class MvrPtpDqpaService implements IMvrPtpDqpaService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(MvrPtpDqpaService.class);
	
	/**
	 * 合伙人德启代付月报表dao
	 */
	@Autowired
	private IMvrPtpDqpaDao mvrPtpDqpaDao;
	
	@Override
	public List<MvrPtpDqpaEntity> queryMvrPtpDqpaByParams(
			MvrPtpDqpaQueryDto queryDto, int start, int limit) {
		logger.debug("根据条件查询合伙人德启代付月报表（分页）开始...");
		// 输入参数不能为空
		if(queryDto == null){
			throw new SettlementException("输入参数为空,根据条件查询合伙人德启代付月报表失败!");
		}
		
		List<MvrPtpDqpaEntity> list = mvrPtpDqpaDao.queryMvrPtpDqpaByParams(queryDto,start,limit);
		
		logger.debug("根据条件查询合伙人德启代付月报表（分页）结束...");
		return list;
	}

	@Override
	public Long queryMvrPtpDqpaEntityTotalCount(MvrPtpDqpaQueryDto queryDto) {
		return mvrPtpDqpaDao.queryMvrPtpDqpaEntityTotalCount(queryDto);
	}

	
}
