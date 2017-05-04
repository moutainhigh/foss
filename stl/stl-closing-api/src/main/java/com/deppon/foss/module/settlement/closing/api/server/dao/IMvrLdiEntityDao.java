package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLdiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto;

/**
 * 快递代理往来DAO
 * @author ibm-zhuwei
 * @date 2013-3-5 下午6:15:47
 */
public interface IMvrLdiEntityDao {
	/**
	 * 报表查询dao
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:36:50
	 * @param dto  isPaging--是否分页
	 * @return
	 */
    List<MvrLdiEntity> selectByConditions(RowBounds rb,MvrLdiDto dto);
    
    /**
	 * 查询快递代理往来报表总条数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 * @param start
	 * @param limit
	 * @param dto
	 */
    MvrLdiDto queryTotalCounts(MvrLdiDto dto);
}
