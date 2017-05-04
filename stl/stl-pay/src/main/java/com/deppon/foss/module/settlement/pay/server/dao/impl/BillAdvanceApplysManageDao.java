package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillAdvanceApplysManageDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceResultDto;

/**
 * 预付单Dao
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:04:00
 */
public class BillAdvanceApplysManageDao extends iBatis3DaoImpl implements
		IBillAdvanceApplysManageDao {

	private static final String NAMESPACE = "foss.stl.BillAdvancedPaymentEntityDao.";// 命名预付单空间

	/**
	 * 查询预付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-19 下午8:00:18
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillAdvanceApplysManageDao#queryBillRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillAdvancedPaymentEntity> queryBillAdvancePayApplys(
			BillAdvanceDto billAdvanceDto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectBillAdvancePaymentApplysParams",
				billAdvanceDto, rowBounds);

	}

	/**
	 * 查询总条数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-4 下午1:32:33
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillAdvanceApplysManageDao#queryCountBillAdvancePayApplys(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto)
	 */
	@Override
	public BillAdvanceResultDto queryCountBillAdvancePayApplys(
			BillAdvanceDto billAdvanceDto) {
		return (BillAdvanceResultDto)this.getSqlSession().selectOne(
				NAMESPACE + "selectCountBillAdvancePaymentApplysParams",
				billAdvanceDto);
	}

	/** 
	 * 导出查询预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-12-6 下午1:21:35
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillAdvanceApplysManageDao#queryExportBillAdvancePayApplys(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillAdvancedPaymentEntity> queryExportBillAdvancePayApplys(
			BillAdvanceDto billAdvanceDto) {
		
		return this.getSqlSession().selectList(
				NAMESPACE + "selectBillAdvancePaymentApplysParams",
				billAdvanceDto);
	}

}
