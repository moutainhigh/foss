package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountManagementDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.ReceivableBillDto;
public interface IDiscountManagementDao {
	/**
	 * 按客户查询折扣单
	 * @author wy
	 * @date 2015-02-4
	 */
	public List<DiscountManagementEntity> queryDiscountByCust(DiscountManagementDto dto, int start,int limit);
	/**
	 * 按客户查询折扣单总行数
	 * @author wy
	 * @date 2015-02-04
	 */
	public int queryCountDiscountByCust(DiscountManagementDto dto);
	/**
	 * 按单号查询折扣单
	 * @author wy
	 * @date 2015-02-4
	 */
	public List<DiscountManagementEntity> queryDiscountByNumber(DiscountManagementDto dto, int start,int limit);
	/**
	 * 按单号查询折扣单总行数
	 * @author wy
	 * @date 2015-02-04
	 */
	public int queryCountDiscountByNumber(DiscountManagementDto dto);
	/**
	 * 双击折扣单查询折扣单明细
	 * @author wy
	 * @date 2015-02-04
	 */
	public List<DiscountManagementDEntity> queryDiscountDEntity(String statementBillNo, int start, int limit);
	/**
	 * 按运单单号查询折扣单信息 
	 * @author ddw
	 * @date 2015-02-06
	 */
	public List<DiscountManagementEntity> queryDiscountPayable(List<String> waybillNos);
	/**
	 * 确认折扣单更新应付单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	public void confirmDiscount(DiscountManagementDto discountManagementDto);
	/**
	 * 确认折扣单更新折扣单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	public void confirmDiscountStatus(DiscountManagementDto discountManagementDto);
	/**
	 * 作废折扣单更新折扣单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	public void discountDelete(DiscountManagementDto discountManagementDto);
	/**
	 * 作废折扣单更新应收单字段IS_DISCOUNT为'N'
	 * @author wy
	 * @date 2015-02-06
	 */
	public void discountDeleteReceivable(DiscountManagementDto discountManagementDto);
	/**
	 * 确认折扣单单时校验折扣单状态
	 * @author wy
	 * @param discountManagementDto
	 */
	public int queryStatus(DiscountManagementDto discountManagementDto);
	public int queryStatusC(DiscountManagementDto discountManagementDto);
	/**
	 * 查询运单号
	 * @author wy
	 * @date 2015-02-06
	 */
	public List<DiscountManagementDto> selectWaybillNo(DiscountManagementDto discountManagementDto);
	/**
	 * 导出查询折扣单明细
	 * @author wy
	 * @date 2015-02-04
	 */
	public List<DiscountManagementDEntity> queryDiscountDEntityExport(String discountNo);
	/**
	 * 作废折扣单单时校验折扣单状态
	 * @author wy
	 * @param discountManagementDto
	 * @return 
	 */
	public int queryDeleteStatus(DiscountManagementDto discountManagementDto);
	public int queryDeleteStatusD(DiscountManagementDto discountManagementDto);
	/**
	 * 查询折扣单明细总行数
	 * @author wy
	 * @date 2015-02-04
	 */
	public int queryCountDiscountBydiscountNo(String discountNo);

	/**
	 * 批量生成折扣单明细
	 * @param dto
	 */
	int discountDetailBathAdd(DiscountAddDto dto);
	/**
	 *根据折扣单明细生成折扣单
	 */
	int discountBillAddByDetail(DiscountAddDto dto);

	/**
	 * 更新应收单状态
	 * @param dto
	 * @return
	 */
	int updateReceivableDiscountStatus(DiscountAddDto dto);

    /**
     * 更新应收单状态带乐观锁控制
     * @param dto
     * @return
     */
    int updateReceivableDiscountStatusLock(DiscountAddDto dto);
	/**
	 *折扣单总数
	 */
	List<DiscountManagementEntity> queryDiscountBillByNo(DiscountAddDto dto);

	/**
	 * 通过客户查询该客户期间内的记录数
	 * @return
	 */
	int queryPeroidCountByCustomer(DiscountManagementDto dto);
	/**
	 * 通过条件查询汇总
	 * @param dto
	 * @return
	 */
	ReceivableBillDto queryReceiableAmountByCondition(ReceivableBillDto dto);

	/**
	 * 查询折扣单明细所有信息
	 * @param discountNo
	 * @return
	 */
	public List<DiscountManagementDEntity> queryDiscountDetailByDiscountNo(DiscountAddDto dto);

    /**
    * 查询同一客户当月需确认折扣单数量
    * @param dto
    * @return
    */
    public int queryCountDiscountByCustS(DiscountManagementDto dto);
}
