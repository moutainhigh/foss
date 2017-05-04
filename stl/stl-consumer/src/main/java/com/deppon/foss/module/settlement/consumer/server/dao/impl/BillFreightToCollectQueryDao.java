package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillFreightToCollectQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectResultDto;

/**
 * 到付清查Dao实现
 * 
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 4:23:49 PM
 */
public class BillFreightToCollectQueryDao extends iBatis3DaoImpl implements
		IBillFreightToCollectQueryDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.stl.BillReceivableEntityDao.";
	
	/**
	 * 通过业务日期Dto查询到付清单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 4:25:49 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillFreightToCollectResultDto> queryBillByBusinessDate(int offset,int start,
			BillFreightToCollectQueryDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset,start);
		//返回查询结果
		return (List<BillFreightToCollectResultDto> )this.getSqlSession().selectList(NAMESPACES + "queryFreightToCollectBillByBusinessDate",dto,rb);
	}

	/**
	 * 通过记账日期Dto查询到付清单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 23, 2012 12:08:49 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillFreightToCollectResultDto> queryBillByAccountDate(int offset,int start,
			BillFreightToCollectQueryDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset,start);
		//返回查询结果
		return (List<BillFreightToCollectResultDto> )this.getSqlSession().selectList(NAMESPACES + "queryFreightToCollectBillByAccountDate",dto,rb);
	}
	
	/** 
	 * 通过业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 11:03:26 AM
	 */
	@Override
	public BillFreightToCollectQueryDto queryTotalAmountByBusinessDate(BillFreightToCollectQueryDto billFreightToCollectQueryDto) {
		//返回查询结果
		return (BillFreightToCollectQueryDto)this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmountInFreightToCollectBillByBusiness",billFreightToCollectQueryDto);
	}

	/** 
	 * 通过记账日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 11:04:26 AM
	 */
	@Override
	public BillFreightToCollectQueryDto queryTotalAmountByAccountDate(BillFreightToCollectQueryDto billFreightToCollectQueryDto) {
		//返回查询结果
		return (BillFreightToCollectQueryDto)this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmountInFreightToCollectBillByAccountDate",billFreightToCollectQueryDto);
	}
}
