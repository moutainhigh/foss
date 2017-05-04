/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author : panshiqi (309613)
 * @date : 2016年3月3日 上午10:16:34
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IWscWayBillManageDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WscWayBillManageDto;

/**
 * 
* @description: 待刷卡运单管理数据层接口实现(管理系统页面使用)
* @className: WscWayBillManageDao
* 
* @author panshiqi 309613
* @date 2016年3月3日 上午10:16:46 
*
 */
public class WscWayBillManageDao extends iBatis3DaoImpl implements IWscWayBillManageDao {

	/**
	 * 声明namespace
	 */
	private static final String NAMESPACE = "foss.stl.WscWayBillManageDao.";

	/**
	 * 根据查询条件获取待刷卡运单数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WSCWayBillEntity> queryBySearchCondition(RowBounds rb, WscWayBillManageDto dto) throws Exception {

		// 查询并返回结果
		return this.getSqlSession().selectList(NAMESPACE + "queryBySearchCondition", dto, rb);
	}

	/**
	 * 获取符合条件的待刷卡运单数据条数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> queryCountBySearchCondition(WscWayBillManageDto dto) throws Exception {

		// 查询并返回结果
		return (Map<String, BigDecimal>) this.getSqlSession().selectOne(NAMESPACE + "queryCountBySearchCondition", dto);
	}

	/**
	 * 查询在id集合中的待刷卡运单数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WSCWayBillEntity> queryByIDs(List<String> ids, CurrentInfo currentInfo) throws Exception {

		// 查询并返回结果
		return this.getSqlSession().selectList(NAMESPACE + "queryByIDs", ids);
	}
}
