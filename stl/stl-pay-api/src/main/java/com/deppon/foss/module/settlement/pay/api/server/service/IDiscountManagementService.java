package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountManagementDto;



public interface IDiscountManagementService {
	/**
	 * 按客户查询折扣单
	 * @author wy
	 * @date 2015-02-04
	 */
	public DiscountManagementDto queryDiscountByCust(DiscountManagementDto dto, int start, int limit);
	/**
	 * 按单号查询折扣单
	 * @author wy
	 * @date 2015-02-04
	 */
	public DiscountManagementDto queryDiscountByNumber(DiscountManagementDto dto,int start, int limit);
	/**
	 * 双击折扣单数据查询明细信息
	 * @author wy
	 * @date 2015-02-04
	 */
	public List<DiscountManagementDEntity> queryDiscountDEntity(String discountNo,int start, int limit);
	
	/**
	 * 按运单单号查询折扣单信息 
	 * @author ddw
	 * @date 2015-02-06
	 */
	public List<DiscountManagementEntity> queryDiscountPayable(List<String> waybillNos);
	/**
	 * 确认折扣单 
	 * @author wy
	 * @date 2015-02-06
	 */
	public void confirmDiscount(DiscountManagementDto discountManagementDto);
	/**
	 * 作废折扣单 
	 * @author wy
	 * @date 2015-02-06
	 */
	public void discountDelete(DiscountManagementDto discountManagementDto);
	/**
	 * 根据折扣单号查询折扣单明细总数
	 * @param discountNo
	 * @return
	 */
	public int queryCountByDiscount(String discountNo);
	/**
	 * 导出查询折扣单明细
	 * @param discountNo
	 * @return
	 */
	public List<DiscountManagementDEntity> queryDiscountDEntityExport(String discountNo);

}
