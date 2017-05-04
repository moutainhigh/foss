package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IDataConsistencyDao;
import com.deppon.foss.module.pickup.waybill.shared.vo.DataConsistencyVo;

public class DataConsistencyDao extends iBatis3DaoImpl implements IDataConsistencyDao {

	/**
	 * 服务器对应数据表条目查询
	 * @author foss-dengyao
	 * @date 2013-05-02 上午9:32:15
	 * @param countServiceTableDate
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataConsistencyVo> countQueryTableDate(List<DataConsistencyVo> tableName,String userCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", tableName);
		map.put("user", "'"+userCode+"'");
		return this.getSqlSession().selectList(
				"foss.pkp.DataConsistencyMapper.countQueryTableDate",map);
	}

}
