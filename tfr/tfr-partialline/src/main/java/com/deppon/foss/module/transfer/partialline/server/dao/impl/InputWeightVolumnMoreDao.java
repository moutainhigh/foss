package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnMoreDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnMoreDto;

public class InputWeightVolumnMoreDao extends iBatis3DaoImpl implements IInputWeightVolumnMoreDao{
	/**
	 * 前缀 一票多件录入重量体积 foss.partialline.inputWeightVolumnMoreMapper.
	 */ 
	private static String prefix = "foss.partialline.inputWeightVolumnMoreMapper.";
	/**
	 * 分页查询一票多件重量体积录入list
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<InputWeightVolumnMoreDto> queryInputWeightVolumnMoreList(
			InputWeightVolumnMoreDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix + "queryInputWeightVolumnMoreList", dto, rb);
	}
	/**
	 * 查询一票多件重量体积数量
	 */
	public Long queryInputWeightVolumnMoreListCount(InputWeightVolumnMoreDto dto){
		return (Long)this.getSqlSession().selectOne(prefix + "queryInputWeightVolumnMoreListCount", dto);
	}
	/**
	 * 根据运单号流水号查询条件查询实体
	 */
	@SuppressWarnings("unchecked")
	public InputWeightVolumnEntity queryEntityByWaybillNoSerialNo(String waybillNo, String serialNo){
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		List<InputWeightVolumnEntity> list = this.getSqlSession().selectList(prefix + "queryEntityByWaybillNoSerialNo", map);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int addWeightVolumnMoreInfo(
			InputWeightVolumnMoreDto inputWeightVolumnMoreDto) {
		return this.getSqlSession().insert(prefix + "addWeightVolumnMoreInfo", inputWeightVolumnMoreDto);
	}

	@Override
	public int updateWeightVolumnMoreInfo(
			InputWeightVolumnMoreDto inputWeightVolumnMoreDto) {
		return this.getSqlSession().update(prefix + "updateWeightVolumnMoreInfo", inputWeightVolumnMoreDto);
	}
}
