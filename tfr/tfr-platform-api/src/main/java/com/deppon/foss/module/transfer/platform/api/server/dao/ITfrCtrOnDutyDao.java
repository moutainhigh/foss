package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrOnDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrOnDutyQcDto;

public interface ITfrCtrOnDutyDao {

	/**
	 * @desc 新增
	 * @param parameter
	 * @date 2015年8月12日下午7:17:40
	 */
	void insertTfrCtrOnDuty(TfrCtrOnDutyEntity parameter);

	/**
	 * @desc 修改
	 * @param parameter
	 * @return
	 * @date 2015年8月12日下午7:17:44
	 */
	int updateTfrCtrOnDuty(TfrCtrOnDutyEntity parameter);

	/**
	 * @desc 删除
	 * @param id
	 * @return
	 * @date 2015年8月12日下午7:17:49
	 */
	int deleteTfrCtrOnDuty(String id);

	/**
	 * @desc 查询
	 * @param parameter
	 * @param start
	 * @param limit
	 * @return
	 * @date 2015年8月12日下午7:17:56
	 */
	List<TfrCtrOnDutyEntity> findTfrCtrOnDutys(TfrCtrOnDutyQcDto parameter,
			int start, int limit);

	/**
	 * @desc 查询总数量
	 * @param parameter
	 * @return
	 * @date 2015年8月13日下午12:14:00
	 */
	Long cntTfrCtrOnDutys(TfrCtrOnDutyQcDto parameter);

	/**
	 * @desc 新增时查询
	 * @param parameter
	 * @return
	 * @date 2015年8月12日下午7:18:03
	 */
	List<TfrCtrOnDutyEntity> findInfos4Add(TfrCtrOnDutyQcDto parameter);

}
