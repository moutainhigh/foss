package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcBatchChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;

/**
 * 电子面单批量更改数据持久层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-2-4 09:39:32
 */
public interface IWaybillRfcBatchChangeDao {/**
	 * 根据ID删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 插入数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	int insert(WaybillRfcBatchChangeEntity record);

	/**
	 * 插入数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	int insertSelective(WaybillRfcBatchChangeEntity record);

	/**
	 * 查询数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	WaybillRfcBatchChangeEntity selectByPrimaryKey(String id);

	/**
	 * 更新数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	int updateByPrimaryKeySelective(WaybillRfcBatchChangeEntity record);

	/**
	 * 更新数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	int updateByPrimaryKey(WaybillRfcBatchChangeEntity record);

	/**
	 * 批量删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	int batchInsertSelective(List<WaybillRfcBatchChangeEntity> recordList);

	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	List<WaybillRfcBatchChangeEntity> queryExpBatchChangeWeightResultByCondition(
			ExpBatchChangeWeightQueryDto dto);
	
	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	List<ExpBatchChangeWeightDto> querybatchChangeDtoListResultByCondition(ExpBatchChangeWeightQueryDto dto);
}