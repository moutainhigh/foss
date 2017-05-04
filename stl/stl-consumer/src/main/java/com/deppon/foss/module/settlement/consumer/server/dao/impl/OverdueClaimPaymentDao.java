package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOverdueClaimPaymentDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueClaimPaymentDto;

import java.util.List;
import java.util.Map;

/**
 * 查询超时理赔付款数据
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 下午3:34:16,content:</p>
 * @author 105762
 * @date 2014-7-28 下午3:34:16
 * @since 1.6
 * @version 1.0
 */
public class OverdueClaimPaymentDao extends iBatis3DaoImpl implements IOverdueClaimPaymentDao {
	private static final String NAMESPACE = "foss.stl.OverdueClaimPaymentDao.";

	/**
	 * <p>查询超时理赔付款数据</p>
	 * @author 105762
	 * @date
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OverdueClaimPaymentDto> queryOverdueClaimPaymentData(Map map) {
		return getSqlSession().selectList(NAMESPACE + "queryOverdueClaimPaymentData", map);
	}

    @Override
    public List<EmployeeEntity> queryEmployeeAndUserByEntity(EmployeeEntity employeeEntity) {
        return getSqlSession().selectList(NAMESPACE + "queryEmployeeAndUserByEntity", employeeEntity);
    }
}
