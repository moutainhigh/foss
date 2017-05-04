/**
 * 
 */
package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfontRDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfontRService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontRDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 02普通业务重分类报表ServiceImpl
 * @author 340778 foss
 * @createTime 2016年8月17日 上午11:11:48
 */
public class MvrNrfontRService implements IMvrNrfontRService {

	/** 日志. */
    private static final Logger LOGGER = LogManager.getLogger(MvrNrfontRService.class);
	/** dao */
	private IMvrNrfontRDao MvrNrfontRDao;
	
	/** 
	 * 查询总数
	 **/
	public MvrNrfontRDto queryTotalByConditions(MvrNrfontRDto dto){
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，DTO参数为空！");
		}
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}
		LOGGER.info("02普通业务重分类报表合计 Start：" + dto.getPeriod());
		return MvrNrfontRDao.queryTotalByCondition(dto);
		
	}
    /** 
     * 合伙人奖罚月报表查询 
     **/
	@Override
	public List<MvrNrfontREntity> queryByConditions(MvrNrfontRDto dto,int offset, int limit){
		LOGGER.info("02普通业务重分类报表列表开始："+dto.getPeriod());
		List<MvrNrfontREntity> queryList = MvrNrfontRDao.queryByConditions(dto,offset,limit);
		
		LOGGER.info("02普通业务重分类报表列表结束！");
		return queryList;
	}
	
    public void setMvrNrfontRDao(IMvrNrfontRDao MvrNrfontRDao) {
		this.MvrNrfontRDao = MvrNrfontRDao;
	}
}
