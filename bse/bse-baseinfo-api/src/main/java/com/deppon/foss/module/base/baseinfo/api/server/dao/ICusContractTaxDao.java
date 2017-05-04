package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;

/**
 * 客户发票标记信息Dao接口
 * @author 132599-foss-shenweihua
 * @date 2013-11-19 下午2:56:51
 * @since
 * @version
 */
public interface ICusContractTaxDao {
	
	/**
	 * 新增客户发票标记信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-19 下午3:01:51
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
	 * @date 2013-11-19 下午3:03:51
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
	 * @date 2013-11-19 下午3:04:29
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
	 * @date 2013-11-22 下午1:47:29
	 * @param condition
	 * @return
	 * @see
	 */
	List<CusContractTaxEntity> queryCurrentUseInvoiceMark(CustomerQueryConditionDto condition,Date date);


}
