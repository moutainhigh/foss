package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;

/**
 * 客户发票标记信息Service接口
 * @author 132599-foss-shenweihua
 * @date 2013-11-19 上午11:30:51
 * @since
 * @version
 */
public interface ICusContractTaxService extends IService{
	/**
	 * 新增客户发票标记信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-19 下午2:21:51
	 * @param entity
	 *            客户发票标记信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addCusContractTax(CusContractTaxEntity entity);

	/**
	 * 修改客户发票标记信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-19 下午2:26:51
	 * @param entity
	 *            客户发票标记信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateCusContractTax(CusContractTaxEntity entity);

	/**
	 * <p>
	 * 根据crmId,最后一次修改时间查询客户发票标记信息是否存在
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-19 下午2:27:29
	 * @param crmId
	 * @param lastupdatetime
	 * @return
	 * @see
	 */
	boolean queryCusContractTaxByCrmId(BigDecimal crmId, Date lastupdatetime);
	
	/**
	 * <p>
	 * 根据客户编码,客户联系方式查询客户当前可以使用的发票标记信息
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-22 上午11:27:29
	 * @param condition
	 * @return
	 * @see
	 */
	CusContractTaxEntity queryCurrentUseInvoiceMark(CustomerQueryConditionDto condition,Date date);

}
