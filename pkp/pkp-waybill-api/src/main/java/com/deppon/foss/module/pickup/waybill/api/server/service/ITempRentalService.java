
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.TempRentalQueryDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;

/**
 * 临时租车管理接口
 * @author HeHaiSu
 * @date 2014-07-23 下午3:43:06
 * @since
 * @version
 */
public interface ITempRentalService extends IService {

	/**
	 * 查询临时租车信息
	 * 运单、派送单
	 * @param TempRentalQueryDto
	 * @param billType  WAYBILL:按运单号查   DELIVERBILL:按派送单查
	 * @return
	 */
	List<RentalMarkEntity> searchTempRental(TempRentalQueryDto tempRentalQueryDto, String billType);
	/**
	 * 查询快递运单号信息
	 * 运单、派送单
	 * @param billType  WAYBILL:按运单号查  
	 * @return
	 */
	List<RentalMarkEntity> searchTWaybill(TempRentalQueryDto tempRentalQueryDto, String billType);
	/**
	 * 根据条件查临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countByWayBillParams(TempRentalQueryDto tempRentalQueryDto);
	
	/**
	 * 根据运单号查临时租车运单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countByWayBill(TempRentalQueryDto tempRentalQueryDto);
	/**
	 * 根据条件查临时租车派送单总条数
	 * @param tempRentalQueryDto
	 * @return
	 */
	public Long countByDeliverBillParams(TempRentalQueryDto tempRentalQueryDto);
}