package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ILostCargoNotifyDao;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoReportDto;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.server.dao.impl.LostCargoNotifyDao
 * @author: foss-yuting
 * @description: 丢货差错自动上报
 * @date:2014年12月3日 下午2:27:21
 */
public class LostCargoNotifyDao extends iBatis3DaoImpl implements ILostCargoNotifyDao {

	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoReportDto.";
	/**
	 * 获取上报oa丢货数据
	 * @date 2014-12-3 下午3:58:10
	 * @return  
	 * @see com.deppon.foss.module.pickup.sign.server.dao.impl.LostCargoNotifyDao#queryReportOAList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LostCargoReportDto> queryReportOAList() {
		return this.getSqlSession().selectList(NAMESPACE + "queryReportOALostList");
	}
}
