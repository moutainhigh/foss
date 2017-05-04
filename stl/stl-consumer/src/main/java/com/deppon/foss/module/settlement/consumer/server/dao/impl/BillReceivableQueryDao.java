package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivableQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

/**
 * 应收单查询Dao实现
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 6:09:19 PM
 */
public class BillReceivableQueryDao extends iBatis3DaoImpl implements IBillReceivableQueryDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.stl.BillReceivableEntityDao.";
	
	/**
	 * 通过业务日期查询应收单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 17, 2012 6:11:19 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillReceivaebleByBusinessDate(int offset,int start,BillReceivableDto billReceivableDto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset, start);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "queryByBusinessDate",billReceivableDto,rb);
	}

	/**
	 * 通过记账日期查询应收单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 23, 2012 6:12:19 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryBillReceivaebleByAccountDate(int offset,int start,BillReceivableDto billReceivableDto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset, start);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "queryByAccountDate",billReceivableDto,rb);
	}
	
	/** 
	 * 
	 * 通过业务日期查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:15:02 AM
	 */
	@Override
	public BillReceivableDto queryTotalAmountByBusinessDate(BillReceivableDto billReceivableDto) {
		//返回查询结果
		return (BillReceivableDto)this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmountByBusinessDate",billReceivableDto);
	}

	/**
	 * 通过业务日期查询数据库中记录总条数，代收货款手续费、保价费、公布价运费
	 * @param billReceivableDto
	 * @return
	 */
	@Override
	public BillReceivableDto queryDsicountTotalAmountByBusinessDate(BillReceivableDto billReceivableDto) {
		return (BillReceivableDto)this.getSqlSession().selectOne(NAMESPACES + "queryDiscountTotalAmountByBusinessDate",billReceivableDto);
	}

	/** 
	 * 
	 * 查询数据库中记录总条数，总金额核销总金额，未核销总金额
	 * 
	 * @author foss-zhangxiaohui
	 * @date Nov 6, 2012 10:15:02 AM
	 */
	@Override
	public BillReceivableDto queryTotalAmountByAccountDate(BillReceivableDto billReceivableDto) {
		//返回查询结果
		return (BillReceivableDto)this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmountByAccountDate",billReceivableDto);
	}
}
