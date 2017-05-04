/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementMakeDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;

/**
 * 制作对账单DAO实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-24 下午8:27:29
 */
public class StatementMakeDao extends iBatis3DaoImpl implements IStatementMakeDao {
	//命名空间
	private static final String NAMESPACE = "foss.stl.StatementOfAccountDEntityDao.";

	/**
	 * 制作对账单，按日期查询应收单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYSMakeSOAByParams(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYSByParams",statementOfAccountMakeQueryDto);
	}

	/**
	 * 制作对账单，按应收单号查询应收单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYSMakeSOAByReceivabeNos(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYSByReceivabeNos",statementOfAccountMakeQueryDto);
	}

	/**
	 * 制作对账单，按运单号查询应收单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYSMakeSOAByWaybillNos(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYSByWaybillNos",statementOfAccountMakeQueryDto);
	}

	/**
	 * 
	 * 制作对账单，按航空正单开始和结束单号查询应收单信息
	 * @author foss-wangxuemin
	 * @date May 27, 2013 2:10:49 PM
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementMakeDao#queryYSMakeSOAByAirWaybillBeginAndEndNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYSMakeSOAByAirWaybillBeginAndEndNo(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYSByAirWaybillBeginAndEndNo",statementOfAccountMakeQueryDto);
	}

	/**
	 * 制作对账单，按日期查询应付单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFMakeSOAByParams(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYFByParams",statementOfAccountMakeQueryDto);
	}

	/**
	 * 制作对账单，按应付单号查询应付单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFMakeSOAByPayableNos(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYFByPayableNos",statementOfAccountMakeQueryDto);
	}

	/**
	 * 制作对账单，按运单号查询应付单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFMakeSOAByWaybillNos(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYFByWaybillNos",statementOfAccountMakeQueryDto);
	}
	
	/**
	 * 
	 * 制作对账单，按航空正单开始和结束单号查询应付单信息
	 * @author foss-wangxuemin
	 * @date May 27, 2013 2:12:19 PM
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementMakeDao#queryYFMakeSOAByAirWaybillBeginAndEndNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFMakeSOAByAirWaybillBeginAndEndNo(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYFByAirWaybillBeginAndEndNo",statementOfAccountMakeQueryDto);
	}

	/**
	 * 制作对账单，按日期查询预收单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryUSMakeSOAByParams(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectUSByParams",statementOfAccountMakeQueryDto);
	}
	
	/**
	 * 制作对账单，按预收单号查询预收单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryUSMakeSOAByReceivedNos(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectUSByRepositReceivedNos",statementOfAccountMakeQueryDto);
	}

	/**
	 * 制作对账单，按日期查询预付单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryUFMakeSOAByParams(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectUFByParams",statementOfAccountMakeQueryDto);
	}
	
	/**
	 * 制作对账单，按预付单号查询预付单信息
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryUFMakeSOAByAdvancedNos(StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectUFByAdvancedNos",statementOfAccountMakeQueryDto);
	}

	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author 邓大伟
	 * @param recNos
	 *            //应收单号集合
	 * @param  active 是否有效
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryByReceivableNOs(List<String> recNos,
			String active) {
		if (CollectionUtils.isEmpty(recNos)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivableNos", recNos);
		if (StringUtils.isNotEmpty(active)) {
			map.put("active", active);
		}
		return this.getSqlSession().selectList(
				NAMESPACE + "selectByReceivableNos", map);
		
	}
	/**
	 * 根据客户信息等查询空运应付单
	 * @author 邓大伟
	 * @param  statementOfAccountMakeQueryDto 对账单实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFKYMakeSOAByParams(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		
		return getSqlSession().selectList(NAMESPACE + "selectYFKYByParams",statementOfAccountMakeQueryDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFKYMakeSOAByAirWaybillBeginAndEndNo(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYFKYByAirWaybillBeginAndEndNo",statementOfAccountMakeQueryDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFKYMakeSOAByPayableNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		//执行查询操作
		return getSqlSession().selectList(NAMESPACE + "selectYFKYByPayableNos",statementOfAccountMakeQueryDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryYFKYMakeSOAByWaybillNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto) {
		return getSqlSession().selectList(NAMESPACE + "selectYFKYByWaybillNos",statementOfAccountMakeQueryDto);
	}

	/**
	 * 查询对账单信息
	 * @author ddw
	 * @date 2014-08-19
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountEntity> queryStatementBIllEntity(StatementOfAccountMakeQueryDto statementQueryDto) {
		return getSqlSession().selectList(NAMESPACE + "queryStatementBIllEntity",statementQueryDto);
	}
	/**
	 * 查询对账单本期应收总数
	 * @author cjy
	 * @date 2016-12-07
	 */
	public int queryYSCountByParms(StatementOfAccountMakeQueryDto statementQueryDto){
		return (Integer)getSqlSession().selectOne(NAMESPACE + "selectYSCountByParams",statementQueryDto);
	}
}
