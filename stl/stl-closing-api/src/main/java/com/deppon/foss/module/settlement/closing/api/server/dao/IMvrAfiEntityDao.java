package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto;

/**
 * 始发空运往来DAO
 * @author ibm-zhuwei
 * @date 2013-3-5 下午6:16:56
 */
public interface IMvrAfiEntityDao {

    /**
	 * 根据多个参数查询始发空运信息(分页)
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	List<MvrAfiEntity> selectMvrAfiByConditions(MvrAfiDto mvrAfiDto,int start,int limit);
	
	/**
	 * 根据多个参数查询始发空运信息
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	List<MvrAfiEntity> selectMvrAfiByConditions(MvrAfiDto mvrAfiDto);
	
	/**
	 * 根据多个参数查询始发空运信息总数
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	Long selectMvrAfiByConditionsCount(MvrAfiDto mvrAfiDto);
	
	/**
	 * 根据多个参数查询统计始发空运信息总数
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	MvrAfiEntity selectMvrAfiByConditionsSum(MvrAfiDto mvrAfiDto);
}
