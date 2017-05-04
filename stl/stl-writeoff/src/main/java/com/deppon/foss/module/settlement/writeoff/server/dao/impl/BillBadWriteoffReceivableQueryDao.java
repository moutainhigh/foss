/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillBadWriteoffReceivableQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadAccountQueryDto;

/**
 * 坏账核销应收单查询Dao实现类
 * 
 * @author foss-wangxuemin
 * @date Dec 5, 2012 10:14:47 AM
 */
public class BillBadWriteoffReceivableQueryDao extends iBatis3DaoImpl implements IBillBadWriteoffReceivableQueryDao {

	//命名空间
	private static final String NAMESPACE = "foss.stl.BillReceivableEntityDao.";

	/**
	 * 查询待坏账申请的应收单列表（按客户）
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 5, 2012 10:14:59 AM
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillBadWriteoffReceivableQueryDao#queryBillBadWriteoffReceivableList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillBadReceivableByCustomer(BillBadAccountQueryDto dto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectBadReceivableByCustomer", dto);
	}

	/**
	 * 查询待坏账申请的应收单列表（按运单号）
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 10, 2012 2:03:25 PM
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillBadWriteoffReceivableQueryDao#queryBillBadReceivableByWayBillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadAccountQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillBadReceivableByWayBillNo(BillBadAccountQueryDto dto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectBadReceivableByWayBillNos", dto);
	}

}
