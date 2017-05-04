package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICashPaymentReportQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashPaymentReportDto;

/**
 * 现金支出报表Dao实现
 * @author foss-zhangxiaohui
 * @date Dec 11, 2012 5:27:42 PM
 */
public class CashPaymentReportQueryDao extends iBatis3DaoImpl implements ICashPaymentReportQueryDao {

	/**
	 * 声明命名空间
	 */
	private static final String NAMESPACES = "foss.stl.BillPaymentEntityDao.";
	
	/**
	 * 根据Dto分页查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 5:29:06 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryCashPaymentReportByDto(int offset,int start,CashPaymentReportDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset, start);
		//返回查询结果
		return (List<BillPaymentEntity> )this.getSqlSession().selectList(NAMESPACES + "queryCashPaymentReportByDto",dto,rb);
	}

	/**
	 * 根据Dto查询符合条件的数据库总记录条数
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 12, 2012 4:34:06 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashPaymentReportDto> queryTotalRecordsInDB(CashPaymentReportDto dto){
		//返回查询结果
		return (List<CashPaymentReportDto>)this.getSqlSession().selectList(NAMESPACES + "queryTotalRecordsInDBByDto",dto);
	}
	
	/**
	 * 根据Dto查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 5:29:06 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryCashPaymentReportByDto(CashPaymentReportDto dto) {
		//返回查询结果
		return (List<BillPaymentEntity> )this.getSqlSession().selectList(NAMESPACES + "queryCashPaymentReportByDto",dto);
	}
}
