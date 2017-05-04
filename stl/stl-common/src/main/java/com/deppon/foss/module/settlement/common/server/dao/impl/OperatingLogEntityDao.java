package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IOperatingLogEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 操作日志基础服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-8 上午10:53:00
 */
public class OperatingLogEntityDao extends iBatis3DaoImpl implements
		IOperatingLogEntityDao {

	private static final String NAMESPACE = "foss.stl.OperatingLogEntityDao.";

	/**
	 * 新加操作日志
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午10:50:15
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IOperatingLogEntityDao#addOperatingLog(com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity)
	 */
	@Override
	public int addOperatingLog(OperatingLogEntity entity) {
		if (entity != null) {
			if (!StringUtils.isEmpty(entity.getOperateBillNo())
					&& !StringUtils.isEmpty(entity.getOperateOrgCode())) {
				return this.getSqlSession().insert(
						NAMESPACE + "addOperatingLog", entity);
			} else {
				throw new SettlementException("内部错误，关键信息为空!");
			}

		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 按操作单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午11:09:36
	 * @param billNo
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IOperatingLogEntityDao#queryByOperateBillNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OperatingLogEntity> queryByOperateBillNo(String billNo) {
		if (!StringUtils.isEmpty(billNo)) {
			return this.getSqlSession().selectList(
					NAMESPACE + "queryByOperateBillNo", billNo);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}

	}

	/**
	 * 新增多条操作日志信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-25 下午2:00:48
	 * @param entity
	 * @return
	 */
	public int addBatchOperatingLog(List<OperatingLogEntity> list) {

		// 如果参数为空，则抛出异常
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("传入参数为空，不能新增操作日志");
		}

		// 循环插入操作日志信息
		for (OperatingLogEntity entity : list) {
			this.addOperatingLog(entity);
		}

		// 返回插入成功条数
		return list.size();
	}
}
