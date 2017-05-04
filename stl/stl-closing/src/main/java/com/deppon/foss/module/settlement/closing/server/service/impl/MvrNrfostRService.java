/**
 * 
 */
package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfostRDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfostRService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostRDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 02特殊业务重分类报表ServiceImpl
 * @author 340778 foss
 * @createTime 2016年8月17日 上午11:11:48
 */
public class MvrNrfostRService implements IMvrNrfostRService {

	/** 日志. */
    private static final Logger LOGGER = LogManager.getLogger(MvrNrfostRService.class);
	/** dao */
	private IMvrNrfostRDao MvrNrfostRDao;
	
	/** 
	 * 查询总数
	 **/
	public MvrNrfostRDto queryTotalByConditions(MvrNrfostRDto dto){
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，DTO参数为空！");
		}
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}
		LOGGER.info("02特殊业务重分类报表合计 Start：" + dto.getPeriod());
		return MvrNrfostRDao.queryTotalByCondition(dto);
		
	}
    /** 
     * 合伙人奖罚月报表查询 
     **/
	@Override
	public List<MvrNrfostREntity> queryByConditions(MvrNrfostRDto dto,int offset, int limit){
		LOGGER.info("02特殊业务重分类报表列表开始："+dto.getPeriod());
		List<MvrNrfostREntity> queryList = MvrNrfostRDao.queryByConditions(dto,offset,limit);
		
		LOGGER.info("02特殊业务重分类报表列表结束！");
		return queryList;
	}
	
    public void setMvrNrfostRDao(IMvrNrfostRDao MvrNrfostRDao) {
		this.MvrNrfostRDao = MvrNrfostRDao;
	}
}
