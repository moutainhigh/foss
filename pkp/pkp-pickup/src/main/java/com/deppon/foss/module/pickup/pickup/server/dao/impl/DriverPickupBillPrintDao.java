package com.deppon.foss.module.pickup.pickup.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pickup.api.server.dao.IDriverPickupBillPrintDao;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.DriverPickupBillPrintDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtDriverPickupBillPrintDto;

/**
 * 司机接货单号打印Dao
 * @author admin
 *
 */
@SuppressWarnings("unchecked")
public class DriverPickupBillPrintDao extends iBatis3DaoImpl implements IDriverPickupBillPrintDao {
	

	private static final String DRIVERPICKUPMAPPER_NAMESPACE = 
			"com.deppon.foss.module.pickup.pickup.server.META-INF.ibatis.DriverPickupBillPrintEntityMapper.";
	
	
	
	//司机接货单号查询sql
	private static final String QUERYDRIVERPICKUPBILLPRINT="queryDriverPickupBillPrint";
	//司机接货单号查询sql
//	private static final String QUERYDRIVERPICKUPBILLPRINTLIST="queryDriverPickupBillPrintList";
	
	//司机接货单号数量查询sql
	private static final String QUERYDRIVERPICKUPBILLPRINTTOTAL="queryDriverPickupBillPrintTotal";
	

	/**
	 * 查询运单列表
	 */
	@Override
	public List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrintList(
			DriverPickupBillPrintDto dto) {
		
		return this.getSqlSession().selectList(DRIVERPICKUPMAPPER_NAMESPACE+QUERYDRIVERPICKUPBILLPRINT, dto);
	}

	/**
	 * 查询运单列表 分页
	 */
	@Override
	public List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrint(
			DriverPickupBillPrintDto driverPickupBillPrintDto, int start,
			int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		 return this.getSqlSession().selectList(DRIVERPICKUPMAPPER_NAMESPACE+QUERYDRIVERPICKUPBILLPRINT, driverPickupBillPrintDto,rowBounds);
	}

	/**
	 * 查询运单列表总数
	 */
	@Override
	public Long queryDriverPickupBillPrintTotal(
			DriverPickupBillPrintDto driverPickupBillPrintDto) {
		
				
				return (Long)this.getSqlSession().selectOne(DRIVERPICKUPMAPPER_NAMESPACE+QUERYDRIVERPICKUPBILLPRINTTOTAL, driverPickupBillPrintDto);
		
	}

}