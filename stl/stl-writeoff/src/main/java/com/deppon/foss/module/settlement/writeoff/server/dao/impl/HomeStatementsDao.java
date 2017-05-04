/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IHomeStatementsDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeItemDto;

public class HomeStatementsDao extends iBatis3DaoImpl implements IHomeStatementsDao {
	static final String NAMESPACE = "home.dao.HomeEntityMapper." ;
	static final String NAMESPACES = "foss.stl.HomeStatementDao." ;
	/**
	 * 页面显示
	 * 按客户查询对账单
	 */
	@Override
	public List<HomeStatementEntity> queryHome(HomeItemDto dto, int start,
			int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		List<HomeStatementEntity> list = getSqlSession().selectList(NAMESPACE + "queryHome", dto,rowBounds);
		return list;
	}
	/**
	 * 页面显示数据总数
	 */
	@Override
	public int queryHomeTotalCount(HomeItemDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryHomeTotalcount", dto);
		return count;
	}
	/**
	 * 按单号查询对账单
	 */
	@Override
	public List<HomeStatementEntity> queryHomeByNumber(HomeItemDto dto) {
		// TODO Auto-generated method stub
		List<HomeStatementEntity> list=getSqlSession().selectList(NAMESPACE + "queryHomeByNumber", dto);
		return list;
	}
	//*************************************    双击对账单记录查看明细            *************************************
	/**
	 * 按对账单单号查询对账单明细总条数
	 * @author 268217
	 */
	@Override
	public int countHomeStatementBillNo(HomeItemDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACES + "countHomeStatementBillNo",dto);
		return count;
	}
	/**
	 * 按对账单单号分页查询对账单明细
	 * @author 268217
	 */
	@Override
	public List<HomeStatementDEntity> queryHomeStatementDBillNo(
			HomeItemDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<HomeStatementDEntity> list = getSqlSession().selectList(NAMESPACES + "queryHomeDByStatementBillNo",dto,rb);
		return list;
	}
	/**
	 * 按对账单单号查询所有对账单明细
	 * @author 268217
	 */
	@Override
	public List<HomeStatementDEntity> queryHomeStatementDBillNo(
			HomeItemDto dto) {
		List<HomeStatementDEntity> list = getSqlSession().selectList(NAMESPACES + "queryHomeDByStatementBillNo",dto);
		return list;
	}
	//*****************************   添加明细   *******************************************
	/**
	 * 家装对账单添加明细查询应收应付单
	 * @author 268217
	 */
	@Override
	public List<HomeStatementDEntity> queryAddHomeByNo(HomeItemDto dto){
		List<HomeStatementDEntity> list = getSqlSession().selectList(NAMESPACES + "queryHomeStatementByNumbers",dto);
		return list;
	}
	/**
	 * 按应付或应收单号查询对账单明细
	 * @author 268217
	 */
	public List<HomeStatementDEntity> queryDelHomeBillNo(HomeItemDto dto){
		List<HomeStatementDEntity> list = getSqlSession().selectList(NAMESPACES + "queryAddHomeBillNo",dto);
		return list;
	}
	/**
	 * 按应付或应收单号添加对账单明细
	 * @author 268217
	 */
	@Override
	public int addHomeStatementDByNumber(HomeItemDto dto) {
		int insertDRows = getSqlSession().insert(NAMESPACES + "homeStatementDSaveByNumbers",dto);
		return insertDRows;
	}
	/**
	 * 更新对账单
	 * @author 268217
	 */
	@Override
	public int UpdateHomeByStatementBillNo(HomeItemDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "UpdateHomeByStatementBillNo",dto);
		return updateRows;
	}
	/**
	 * 更新应付单
	 * @author 268217
	 */
	@Override
	public int UpdatePayRowsBillNo(HomeItemDto dto) {
		int updatePayRows = getSqlSession().update(NAMESPACES + "homePayUpdateByStatementBillNo",dto);
		return updatePayRows;
	}
	/**
	 * 更新应收单
	 * @author 268217
	 */
	@Override
	public int UpdateRecRowsBillNo(HomeItemDto dto) {
		int updateRecRows = getSqlSession().update(NAMESPACES + "homeRecUpdateByStatementBillNo",dto);
		return updateRecRows;
	}
	//*******************************************   删除明细         ************************************************
	/**
	 * 家装对账单删除明细查询应收应付单
	 * @author 268217
	 */
	@Override
	public List<HomeStatementDEntity> queryDelHomeByNo(HomeItemDto dto){
		List<HomeStatementDEntity> list = getSqlSession().selectList(NAMESPACES + "queryAddHomeBillNo",dto);
		return list;
	}
	/**
	 * 家装对账单删除明细
	 * @author 268217
	 */
	@Override
	public int delHomeStatement(HomeItemDto dto){
		int updateRecRows = getSqlSession().update(NAMESPACES + "delHomeStatement",dto);
		return updateRecRows;
	}
	/**
	 * 按对账单单号更新应付单
	 * @author 268217
	 */
	@Override
	public int updatePayable(HomeItemDto dto){
		int updatePayRows = getSqlSession().update(NAMESPACES + "updatePayable",dto);
		return updatePayRows;
	}
	/**
	 * 按对账单单号更新应收单
	 * @author 268217
	 */
	@Override
	public int updateReceivable(HomeItemDto dto){
		int updateRecRows = getSqlSession().update(NAMESPACES + "updateReceivable",dto);
		return updateRecRows;
	}
	//********************************************   确认       ********************************************************
	/**
	 * 按对账单单号查询核销单
	 * @author 268217
	 */
	@Override
	public int queryHomeBillByStatementBillNo(String statementBillNo){
		int homeBillCount = (Integer)getSqlSession().selectOne(NAMESPACE + "queryHomeBillByStatementBillNo",statementBillNo);
		return homeBillCount;
	}
	/**
	 * 确认对账单
	 * @author 268217
	 */
	@Override
	public int confirmHomeStatement(HomeItemDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "confirmHomeStatement",dto);
		return updateRows;
	}
	/**
	 * 反确认对账单
	 * @author 268217
	 */
	@Override
	public int unconfirmHomeStatement(HomeItemDto dto) {
		int updateRows = getSqlSession().update(NAMESPACE + "unconfirmHomeStatement",dto);
		return updateRows;
	}
	//********************************************   付款单       ********************************************************
	
	
	
}
