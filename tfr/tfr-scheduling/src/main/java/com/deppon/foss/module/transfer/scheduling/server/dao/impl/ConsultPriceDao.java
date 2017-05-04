package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IConsultPriceDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ConsultPriceEntity;

public class ConsultPriceDao extends iBatis3DaoImpl implements IConsultPriceDao {
	private static final String NAMESPACE="Foss.consultPrice.";
	/**
	 * @param 询价信息实体
	 * @return 返回影响的行数
	 */
	public void addConsultPriceInfo(ConsultPriceEntity consultPriceEntity) {
		
		 this.getSqlSession().insert(NAMESPACE+"addConsultPriceInfo", consultPriceEntity);
	}

	/**
	 * @param 询价编号
	 * @return 返回查询结果
	 */
	@SuppressWarnings("unchecked")
	public List<ConsultPriceEntity> queryByConsultPriceNo(String consultPriceNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryByconsultPriceNo", consultPriceNo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ConsultPriceEntity> queryIfConsultPriceNoUsed(
			String consultPriceNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryIfConsultPriceNoUsed", consultPriceNo);
	}

}
