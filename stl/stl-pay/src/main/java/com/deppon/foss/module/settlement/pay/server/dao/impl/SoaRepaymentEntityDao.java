package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ISoaRepaymentEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;


/**
 * 对账单还款单管理dao接口
 * @author foss-qiaolifeng
 * @date 2012-11-29 下午4:55:15
 */
public class SoaRepaymentEntityDao extends iBatis3DaoImpl implements
		ISoaRepaymentEntityDao {
	public static final String NAMESPACES = "foss.stl.SoaRepaymentEntityDao.";
	
	
	/** 
	 * 新增对账单还款单关系数据
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 下午4:55:30
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ISoaRepaymentEntityDao#add(com.deppon.foss.module.settlement.common.api.shared.domain.SoaRepaymentEntity)
	 */
	@Override
	public int add(SoaRepaymentEntity entity) {
		if (entity != null) {
			return this.getSqlSession().insert(NAMESPACES + "insert", entity);
		}
		return 0;
	}

	/** 
	 * 根据还款单号查询对账单还款单关系
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 下午4:55:44
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ISoaRepaymentEntityDao#queryListByRepaymentNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SoaRepaymentEntity> queryListByRepaymentNo(String repaymentNo) {
		return this.getSqlSession().selectList(NAMESPACES + "selectByRepaymentNo", repaymentNo);
	}

}
