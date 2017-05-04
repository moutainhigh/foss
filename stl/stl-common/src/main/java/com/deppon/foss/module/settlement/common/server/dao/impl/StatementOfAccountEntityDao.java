package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountQueryResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;

/**
 * 对账单公共DAO实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 上午11:47:34
 */
public class StatementOfAccountEntityDao extends iBatis3DaoImpl implements
		IStatementOfAccountEntityDao {

	private static final String NAMESPACE = "foss.stl.StatementOfAccountEntityDao.";

	/**
	 * 保存对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:48:22
	 * @param entity
	 *            对账单
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountEntityDao#add(com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity)
	 */
	@Override
	public int add(StatementOfAccountEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/**
	 * 根据对账单ID查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午2:46:53
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountEntityDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public StatementOfAccountEntity queryByPrimaryKey(String id) {
		return (StatementOfAccountEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 根据对账单单号集合查询对账单(只查询本部门的对账单信息，供按对账单单号、明细单号查询查询对账单使用)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-1-8 下午2:23:39
	 * @param list
	 *            对账单号集合
	 * @param orgCode
	 *            当前操作网点编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StatementOfAccountEntity> queryCurrentStatementNos(
			List<String> list, String orgCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statementsNos", list);
		map.put("orgCode", orgCode);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectCurrentByStatementNos", map);
	}

	/**
	 * 根据对账单号集合查询未被官网锁定的对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午9:12:01
	 * @param list
	 *            对账单号集合
	 * @param confirmStatus
	 *            确认状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountEntity> queryByStatementNos(
			List<String> list, String confirmStatus) {
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statementsNos", list);
			map.put("confirmStatus", confirmStatus);
			return this.getSqlSession().selectList(
					NAMESPACE + "selectByStatementNos", map);
		}
		return null;
	}

	/**
	 * 根据对账单单号查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午10:26:47
	 * @param statementNo
	 *            对账单号
	 * @return
	 */
	@Override
	public StatementOfAccountEntity queryByStatementNo(String statementNo) {
		return (StatementOfAccountEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectByStatementNo", statementNo);
	}

	/**
	 * 新增对账单明细时修改对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 * @param entity
	 *            对账单
	 * @return
	 */
	@Override
	public int updateStatementForAddDetail(StatementOfAccountEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateByAddDetail",
				entity);
	}

	/**
	 * 删除对账单明细时修改对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 * @param entity
	 *            对账单
	 * @return
	 */
	@Override
	public int updateStatementForDeleteDetail(StatementOfAccountEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateByDeleteDetail",
				entity);
	}

	/**
	 * 确认、反确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午5:21:47
	 * @param entity
	 *            对账单
	 * @return
	 */
	@Override
	public int confirmOrUnConfirmStatement(StatementOfAccountEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateByConfirmStatus",
				entity);
	}

	/**
	 * 当对账单明细在对账单之外参与核销、反核销、红冲操作时，更新对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午8:45:03
	 * @param entity
	 *            对账单
	 * @return
	 */
	@Override
	public int updateStatementForChangeDetail(StatementOfAccountEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateByChangeDetail",
				entity);
	}

	/**
	 * 按制作日期查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午5:35:02
	 * @param updateDto
	 *            账单明细信息DTO
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountEntityDao#queryStatementByCreateDate(com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountEntity> queryStatementByCreateDate(
			StatementOfAccountUpdateDto updateDto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "selectByDate",
				updateDto,rb);
	}
	//邓大伟
	@Override
	public StatementOfAccountQueryResultDto countStatementByCreateDate(
			StatementOfAccountUpdateDto updateDto) {
		StatementOfAccountQueryResultDto dto = (StatementOfAccountQueryResultDto)this.getSqlSession().selectOne(NAMESPACE + "countSelectByDate",updateDto);
		return dto;
	}
	
	/**
	 * 对账单核销时修改对账单的本期未还金额
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-15 上午11:53:57
	 * @param entity
	 *            对账单
	 * @return
	 */
	@Override
	public int updateStatementForWriteoff(StatementOfAccountEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateByRealseDetail",
				entity);
	}

	/**
	 * 反核销时，为了释放对账单中的应收单，更新对账单金额
	 * 
	 * @author foss-wangxuemin
	 * @date Apr 1, 2013 4:58:24 PM
	 */
	@Override
	public int updateStatementForReverseWriteoffRelease(
			StatementOfAccountEntity entity) {
		return this.getSqlSession().update(
				NAMESPACE + "updateByReverseWriteoffReleaseDetail", entity);
	}
	
	/**
	 * 
	 * 根据对账单号和对账单版本号查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-6 下午7:05:19
	 * @param statementNo
	 *            对账单号
	 * @param versionNo
	 *            版本号
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService#queryByStatementNoAndVersion(java.lang.String,
	 *      short)
	 */
	@Override
	public StatementOfAccountEntity queryByStatementNoAndVersion(
			String statementNo, short versionNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statementNo", statementNo);
		map.put("versionNo", versionNo);
		return (StatementOfAccountEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectByStatementNoAndVersion", map);
	}

}
