/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadAccountQueryDto;

/**
 * 坏账核销应收单查询Dao接口类
 * 
 * @author foss-wangxuemin
 * @date Dec 5, 2012 10:13:39 AM
 */
public interface IBillBadWriteoffReceivableQueryDao {

	/**
	 * 查询待坏账申请的应收单列表（按客户）
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 5, 2012 10:14:00 AM
	 * @param queryDto
	 *            坏账查询Dto
	 * @return List<BillReceivableEntity>
	 * 			  应收单集合
	 */
	public List<BillReceivableEntity> queryBillBadReceivableByCustomer(
			BillBadAccountQueryDto queryDto);

	/**
	 * 查询待坏账申请的应收单列表（按运单号）
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 10, 2012 2:02:23 PM
	 * @param queryDto
	 *            坏账查询Dto
	 * @return List<BillReceivableEntity>
	 * 			  应收单集合
	 */
	public List<BillReceivableEntity> queryBillBadReceivableByWayBillNo(
			BillBadAccountQueryDto queryDto);
}
