package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODQueryDto;

/**
 * 
 * 空运代收货款审核Dao
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-29 下午5:32:51
 */
public class AirBillPaidCODQueryDao extends iBatis3DaoImpl implements
		IAirBillPaidCODQueryDao {

	private static final String NAMESPACE = "foss.stl.AirBillPaidCODQueryDao.";

	/**
	 * 按签收时间查询总行数，合计金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午10:54:35
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao#queryTotalRowsBySignDate(java.lang.String,
	 *      java.util.Date, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public AirBillPaidCODGridDto queryTotalRowsBySignDate(AirBillPaidCODQueryDto queryDto) {

		return (AirBillPaidCODGridDto) this.getSqlSession().selectOne(
				NAMESPACE + "queryTotalRowsBySignDate", queryDto);
	}

	/**
	 * 按签收时间分页查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午10:55:00
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao#queryBySignDate(java.lang.String,
	 *      java.util.Date, java.util.Date, java.lang.String, java.lang.String,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirBillPaidCODGridDto> queryBySignDate( AirBillPaidCODQueryDto queryDto,int offset, int limit) {

		// 设置rowBounds
		RowBounds rowBounds = new RowBounds(offset, limit);

		return this.getSqlSession().selectList(NAMESPACE + "queryBySignDate",
				queryDto,rowBounds);
	}

	/**
	 * 按审核时间查询总行数，合计金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午11:03:04
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao#queryTotalRowsByAuditDate(java.lang.String,
	 *      java.util.Date, java.util.Date, java.lang.String)
	 */
	@Override
	public AirBillPaidCODGridDto queryTotalRowsByAuditDate(AirBillPaidCODQueryDto queryDto) {
		
		return (AirBillPaidCODGridDto) this.getSqlSession().selectOne(
				NAMESPACE + "queryTotalRowsByAuditDate", queryDto);
	}

	/**
	 * 按审核时间查询总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午11:04:07
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao#queryByAuditDate(java.lang.String,
	 *      java.util.Date, java.util.Date, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirBillPaidCODGridDto> queryByAuditDate(AirBillPaidCODQueryDto queryDto,
			int offset, int limit) {

		// 设置rowBounds
		RowBounds rowBounds = new RowBounds(offset, limit);

		return this.getSqlSession().selectList(NAMESPACE + "queryByAuditDate",
				queryDto, rowBounds);
	}

	/**
	 * 按运单单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午9:08:10
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao#queryCODByWaybillNos(java.lang.String,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirBillPaidCODGridDto> queryByWaybillNos(AirBillPaidCODQueryDto queryDto) {

		return this.getSqlSession().selectList(NAMESPACE + "queryByWaybillNos",
				queryDto);
	}


}
