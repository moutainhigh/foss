/**
 * 
 */
package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpRpDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpRpService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * @author 231438
 * 合伙人奖罚月报表
 */
public class MvrPtpRpService implements IMvrPtpRpService {

	/** 日志. */
    private static final Logger LOGGER = LogManager.getLogger(MvrHiService.class);
	/** 合伙人 */
	private IMvrPtpRpDao mvrPtpRpDao;
	
	/** 
	 * 查询总数
	 **/
	public MvrPtpRpDto queryTotalByConditions(MvrPtpRpDto dto){
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，DTO参数为空！");
		}
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}
		LOGGER.info("查询合伙人奖罚月报表合计 Start：" + dto.getPeriod());
		return mvrPtpRpDao.queryTotalByCondition(dto);
		
	}
    /** 
     * 合伙人奖罚月报表查询 
     **/
	@Override
	public List<MvrPtpRpEntity> queryByConditions(MvrPtpRpDto dto,int offset, int limit){
		LOGGER.info("查询合伙人奖罚月报表列表开始："+dto.getPeriod());
		List<MvrPtpRpEntity> queryList = mvrPtpRpDao.queryByConditions(dto,offset,limit);
		
		LOGGER.info("查询合伙人奖罚月报表列表结束！");
		return queryList;
	}
	
    public void setMvrPtpRpDao(IMvrPtpRpDao mvrPtpRpDao) {
		this.mvrPtpRpDao = mvrPtpRpDao;
	}
}
