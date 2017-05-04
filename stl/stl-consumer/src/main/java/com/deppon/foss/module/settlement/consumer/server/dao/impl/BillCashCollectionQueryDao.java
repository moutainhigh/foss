package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillCashCollectionQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto;

/**
 * 查询现金收款单Dao实现
 * 
 * @author foss-zhangxiaohui
 * @date Nov 2, 2012 3:57:59 PM
 */
public class BillCashCollectionQueryDao extends iBatis3DaoImpl implements IBillCashCollectionQueryDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.stl.BillCashCollectionEntityDao.";
	
	/**
	 * 通过业务日期查询现金收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 2, 2012 3:58:43 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCollectionEntity> queryBillCashCollectionByBusinessDate(int offset,int start,
			BillCashCollectionQueryDto billCashCollectionQueryDto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset, start);
		//返回查询结果
		return (List<BillCashCollectionEntity> )this.getSqlSession().selectList(NAMESPACES + "queryByBusinessDate",billCashCollectionQueryDto,rb);
	}
	
	/**
	 * 通过记账日期查询现金收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 2, 2012 3:58:43 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCollectionEntity> queryBillCashCollectionByAccountDate(int offset,int start,
			BillCashCollectionQueryDto billCashCollectionQueryDto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset, start);
		//返回查询结果
		return (List<BillCashCollectionEntity> )this.getSqlSession().selectList(NAMESPACES + "queryByAccountDate",billCashCollectionQueryDto,rb);
	}
	
	/** 
	 * 通过业务日期查询数据库中记录总条数，总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 11:04:01 AM
	 */
	@Override
	public BillCashCollectionQueryDto queryTotalAmountByBusinessDate(BillCashCollectionQueryDto billCashCollectionQueryDto) {
		//返回查询结果
		return (BillCashCollectionQueryDto)this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmountByBusinessDate",billCashCollectionQueryDto);
	}

	/** 
	 * 通过记账日期查询数据库中记录总条数，总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 11:04:01 AM
	 */
	@Override
	public BillCashCollectionQueryDto queryTotalAmountByAccountDate(BillCashCollectionQueryDto billCashCollectionQueryDto) {
		//返回查询结果
		return (BillCashCollectionQueryDto)this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmountByAccountDate",billCashCollectionQueryDto);
	}

	
	/** 
	 * 按运单号查询现金收款单
	 * @author 095793-foss-LiQin
	 * @date 2013-4-3 上午9:31:53
	 * @param billCashCollectionQueryDto
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillCashCollectionQueryDao#queryBillCashCollectionByWaybillNo(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCollectionEntity> queryBillCashCollectionByWaybillNo(
			BillCashCollectionQueryDto billCashDto) {
		//按单号返回查询		
		return this.getSqlSession().selectList(NAMESPACES+"selectCashCollectionByWaybillNo",billCashDto);
	}
}
