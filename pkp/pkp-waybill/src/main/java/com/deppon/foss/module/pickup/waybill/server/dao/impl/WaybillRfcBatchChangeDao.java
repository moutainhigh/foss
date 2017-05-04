package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcBatchChangeDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcBatchChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;

/**
 * 电子面单批量更改数据持久层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-2-4 09:39:32
 */
public class WaybillRfcBatchChangeDao extends iBatis3DaoImpl implements IWaybillRfcBatchChangeDao {
	private static final String NAMESPACE = "foss.pkp.ewaybill.waybillRfcBatchChangeEntityMapper.";
	
	/**
	 * 根据ID删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey", id);
	}

	/**
	 * 插入数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@Override
	public int insert(WaybillRfcBatchChangeEntity record) {
		return this.getSqlSession().insert(NAMESPACE+"insert", record);
	}

	/**
	 * 插入数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@Override
	public int insertSelective(WaybillRfcBatchChangeEntity record) {
		return this.getSqlSession().insert(NAMESPACE+"insertSelective", record);
	}

	/**
	 * 查询数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillRfcBatchChangeEntity selectByPrimaryKey(String id) {
		List<WaybillRfcBatchChangeEntity> list = this.getSqlSession().selectList(NAMESPACE+"selectByPrimaryKey", id);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据条件查询数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcBatchChangeEntity> queryExpBatchChangeWeightResultByCondition(ExpBatchChangeWeightQueryDto dto) {
		List<WaybillRfcBatchChangeEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryExpBatchChangeWeightResultByCondition", dto);
		return list;
	}
	
	/**
	 * 更新数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@Override
	public int updateByPrimaryKeySelective(WaybillRfcBatchChangeEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
	}

	/**
	 * 更新数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@Override
	public int updateByPrimaryKey(WaybillRfcBatchChangeEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKey", record);
	}

	/**
	 * 批量删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-2-4 09:46:46
	 */
	@Override
	public int batchInsertSelective(List<WaybillRfcBatchChangeEntity> recordList) {
		return this.getSqlSession().insert(NAMESPACE+"batchInsertSelective", recordList);
	}
	
	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpBatchChangeWeightDto> querybatchChangeDtoListResultByCondition(ExpBatchChangeWeightQueryDto dto){
		return this.getSqlSession().selectList(NAMESPACE+"querybatchChangeDtoListResultByCondition", dto);
	}
}
