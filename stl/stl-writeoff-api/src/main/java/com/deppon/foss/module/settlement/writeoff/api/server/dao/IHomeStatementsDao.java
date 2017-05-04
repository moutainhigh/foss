package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeItemDto;

public interface IHomeStatementsDao {
	/**
	 * 268217
	 * 查询符合条件的对账单
	 */
	List<HomeStatementEntity> queryHome(HomeItemDto dto,int start, int limit);
	
	/**
	 * 268217
	 * 页面显示数据总数
	 */
	public int queryHomeTotalCount(HomeItemDto dto);
	/**
	 * 268217
	 * 按对账单号查询对账单
	 */
	List<HomeStatementEntity> queryHomeByNumber(HomeItemDto dto);
	
	//***********************************   双击对账单记录查看明细        ********************************************
	/**
	 * 按对账单单号查询对账单明细总条数
	 * @author 268217
	 */
	public int countHomeStatementBillNo(HomeItemDto dto);
	/**
	 * 按对账单单号查询对账单明细
	 * @author 268217
	 */
	public List<HomeStatementDEntity> queryHomeStatementDBillNo(HomeItemDto dto, int start, int limit);
	/**
	 * 按单号查询对账单明细
	 * @author 268217
	 */
	public List<HomeStatementDEntity> queryHomeStatementDBillNo(HomeItemDto dto);
	
	//************************************    添加明细            **************************************************
	/**
	 * 家装对账单添加明细查询应收应付单
	 * @author 268217
	 */
	public List<HomeStatementDEntity> queryAddHomeByNo(HomeItemDto dto);
	/**
	 * 按应付或应收单号查询对账单明细
	 * @author 268217
	 */
	public List<HomeStatementDEntity> queryDelHomeBillNo(HomeItemDto dto);
	/**
	 * 按单号添加对账单明细
	 * @author 268217
	 */
	int addHomeStatementDByNumber(HomeItemDto dto);
	/**
	 * 更新对账单
	 * @author 268217
	 */
	public int UpdateHomeByStatementBillNo(HomeItemDto dto);
	/**
	 * 更新应付单
	 * @author 268217
	 */
	public int UpdatePayRowsBillNo(HomeItemDto dto);
	/**
	 * 更新应收单
	 * @author 268217
	 */
	public int UpdateRecRowsBillNo(HomeItemDto dto);
	
	//****************************************    删除明细        *******************************************************
	/**
	 * 家装对账单删除明细查询应收应付单
	 * @author 268217
	 */
	List<HomeStatementDEntity> queryDelHomeByNo(HomeItemDto dto);
	/**
	 * 家装对账单删除明细
	 * @author 268217
	 */
	public int delHomeStatement(HomeItemDto dto);
	/**
	 * 按对账单单号更新应付单
	 * @author 268217
	 */
	public int updatePayable(HomeItemDto dto);
	/**
	 * 按对账单单号更新应收单
	 * @author 268217
	 */
	public int updateReceivable(HomeItemDto dto);
	
	//********************************************   确认       ********************************************************
	/**
	 * 按对账单单号查询核销单
	 * @author 268217
	 */
	public int queryHomeBillByStatementBillNo(String statementBillNo);
	/**
	 * 确认对账单
	 * @author 268217
	 */
	public int confirmHomeStatement(HomeItemDto dto);
	/**
	 * 反确认对账单
	 * @author 268217
	 */
	public int unconfirmHomeStatement(HomeItemDto dto);
}
