/**
 * 
 */
package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrSummaryRDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrSummaryRService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrSummaryREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrSummaryRDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 02业务重分类汇总报表ServiceImpl
 * @author 340778 foss
 * @createTime 2016年8月18日 下午4:48:26
 */
public class MvrSummaryRService implements IMvrSummaryRService {

	/** 日志. */
    private static final Logger LOGGER = LogManager.getLogger(MvrSummaryRService.class);
	/** 合伙人 */
	private IMvrSummaryRDao MvrSummaryRDao;
	
	/** 
	 * 查询总数
	 **/
	public MvrSummaryRDto queryTotalByConditions(MvrSummaryRDto dto){
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，DTO参数为空！");
		}
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}
		LOGGER.info("查询02业务重分类汇总报表合计 Start：" + dto.getPeriod());
		return MvrSummaryRDao.queryTotalByCondition(dto);
		
	}
    /** 
     * 02业务重分类汇总报表查询 
     **/
	@Override
	public List<MvrSummaryREntity> queryByConditions(MvrSummaryRDto dto,int offset, int limit){
		LOGGER.info("查询02业务重分类汇总报表列表开始："+dto.getPeriod());
		List<MvrSummaryREntity> queryList = MvrSummaryRDao.queryByConditions(dto,offset,limit);
		
		LOGGER.info("查询02业务重分类汇总报表列表结束！");
		return queryList;
	}
	
    public void setMvrSummaryRDao(IMvrSummaryRDao MvrSummaryRDao) {
		this.MvrSummaryRDao = MvrSummaryRDao;
	}
}
