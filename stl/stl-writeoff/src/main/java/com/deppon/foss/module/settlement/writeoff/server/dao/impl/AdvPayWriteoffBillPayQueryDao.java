package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IAdvPayWriteoffBillPayQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.AdvPayWriteoffBillPayDto;

/**
 * 预付冲应付Dao
 * @author foss-pengzhen
 * @date 2012-10-26 下午3:17:50
 * @since
 * @version
 */
public class AdvPayWriteoffBillPayQueryDao extends iBatis3DaoImpl implements IAdvPayWriteoffBillPayQueryDao {
	
	//命名空间
	private static final String NAMESPACE = "foss.stl.BillAdvancedPaymentEntityDao.";

	/**
	 * 根据传入的一到多个预付单号，获取一到多条预付单信息
	 * @author foss-pengzhen
	 * @date 2012-10-17 下午4:50:47
	 * @param billAdvancedPaymentDto
	 * @return 
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillAdvancedPaymentQueryDao#queryBillAdvancedPaymentNos(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillAdvancedPaymentDto)
	 */
	@SuppressWarnings("unchecked")
	public List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentNos(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectBillAdvancedPaymentNos",advPayWriteoffBillPayDto);
	}

	/**
	 * 根据传入获取一到多条预付单信息
	 * @author foss-pengzhen
	 * @date 2012-10-17 下午4:49:41
	 * @param billAdvancedPaymentDto
	 * @return 
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillAdvancedPaymentQueryDao#queryBillAdvancedPaymentParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillAdvancedPaymentDto)
	 */
	@SuppressWarnings("unchecked")
	public List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentParams(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectBillAdvancedPaymentParams",advPayWriteoffBillPayDto);
	}
}
