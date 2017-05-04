package com.deppon.foss.module.settlement.pay.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDiscountManagementDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountManagementDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.ReceivableBillDto;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountManagementDao extends iBatis3DaoImpl implements IDiscountManagementDao{
	private static final String NAMESPACE = "foss.pay.DiscountManagementDao.";
	/**
	 * 按客户查询折扣单
	 * @author wy
	 * @date 2015-02-04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountManagementEntity> queryDiscountByCust(DiscountManagementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<DiscountManagementEntity> list = getSqlSession().selectList(NAMESPACE + "queryDiscountByCust",dto,rb);
		return list;
	}
	/**
	 * 按客户查询折扣单总行数
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public int queryCountDiscountByCust(DiscountManagementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryCountDiscountByCust",dto);
		return count;
	}
	/**
	 * 按单号查询折扣单
	 * @author wy
	 * @date 2015-02-04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountManagementEntity> queryDiscountByNumber(DiscountManagementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<DiscountManagementEntity> list = getSqlSession().selectList(NAMESPACE + "queryDiscountByNumber",dto,rb);
		return list;
	}
	/**
	 * 按折扣单单号查询折扣单明细总条数
	 * @author wy
	 * @date 2014-06-16
	 */
	@Override
	public int queryCountDiscountByNumber(DiscountManagementDto dto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryCountDiscountByNumber",dto);
		return count;
	}
	
	/**
	 * 导出查询折扣单明细
	 * @author wy
	 * @date 2015-02-04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountManagementDEntity> queryDiscountDEntityExport(String discountNo) {
		List<DiscountManagementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryDiscountDEntity",discountNo);
		return list;
	}
	/**
	 * 双击折扣单数据查询折扣单明细
	 * @author wy
	 * @date 2015-02-04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountManagementDEntity> queryDiscountDEntity(String discountNo, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<DiscountManagementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryDiscountDEntity",discountNo,rb);
		return list;
	}
	
	
	/**
	 * 按运单单号查询折扣单信息 
	 * @author ddw
	 * @date 2015-02-06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountManagementEntity> queryDiscountPayable(List<String> waybillNos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		List<DiscountManagementEntity> discountList = getSqlSession().selectList(NAMESPACE + "queryDiscountPayable",map);
		return discountList;
	}
	/**
	 * 确认折扣单更新应付单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	@Override
	public void confirmDiscount(DiscountManagementDto discountManagementDto) {
		getSqlSession().update(NAMESPACE + "confirmDiscount",discountManagementDto);
		
	}
	/**
	 * 确认折扣单更新折扣单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	@Override
	public void confirmDiscountStatus(DiscountManagementDto discountManagementDto) {
		getSqlSession().update(NAMESPACE + "confirmDiscountStatus",discountManagementDto);
	}
	/**
	 * 作废折扣单更新折扣单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	@Override
	public void discountDelete(DiscountManagementDto discountManagementDto) {
		getSqlSession().update(NAMESPACE + "discountDelete",discountManagementDto);
	}
	/**
	 * 作废折扣单更新应收单字段IS_DISCOUNT为'N'
	 * @author wy
	 * @date 2015-02-06
	 */
	@Override
	public void discountDeleteReceivable(DiscountManagementDto discountManagementDto) {
		getSqlSession().update(NAMESPACE + "discountDeleteReceivable",discountManagementDto);
		
	}
	/**
	 * 校验折扣单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	@Override
	public int queryStatus(DiscountManagementDto discountManagementDto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryStatus",discountManagementDto);
		return count;
	}
	/**
	 * 校验折扣单状态
	 * @author wy
	 * @date 2015-02-06
	 */
	@Override
	public int queryDeleteStatus(DiscountManagementDto discountManagementDto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryDeleteStatus",discountManagementDto);
		return count;
	}
	/**
	 * 查询运单号
	 * @author wy
	 * @date 2015-02-06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountManagementDto> selectWaybillNo(DiscountManagementDto discountManagementDto) {
		return getSqlSession().selectList(NAMESPACE + "selectWaybillNo",discountManagementDto);
		
	}
	/**
	 * 按单号查询折扣单明细总行数
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public int queryCountDiscountBydiscountNo(String discountNo) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryCountDiscountBydiscountNo",discountNo);
		return count;
	}
	/**
	 * 确认折扣单时校验折扣单状态C
	 * @author wy
	 * @date 2015-02-04
	 */
	@Override
	public int queryStatusC(DiscountManagementDto discountManagementDto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryStatusC",discountManagementDto);
		return count;
	}
	@Override
	public int queryDeleteStatusD(DiscountManagementDto discountManagementDto) {
		int count = (Integer) getSqlSession().selectOne(NAMESPACE + "queryDeleteStatusD",discountManagementDto);
		return count;
	}
	
	
	/**
	 * 折扣单明细生成
	 * @param dto
	 * @return
	 */
	@Override
	public int discountDetailBathAdd(DiscountAddDto dto) {
		 int rows =getSqlSession().insert(NAMESPACE + "createDiscountDetailBath",dto);
		 return rows;
	}

	/**
	 * 折扣单批量生成
	 * @param dto
	 * @
     *
	 */
	@Override
	public int discountBillAddByDetail(DiscountAddDto dto) {
		int rows =getSqlSession().insert(NAMESPACE + "createDiscountByDetail",dto);
		return rows;
	}
	/**
	 * 更新折扣状态
	 * @param dto
	 * @return
	 */
	@Override
	public int updateReceivableDiscountStatus(DiscountAddDto dto) {
		int rows =getSqlSession().update(NAMESPACE + "updateReceivableDiscountStatus",dto);
		return rows;
	}

    /**
     * 更新折扣状态
     * @param dto
     * @return
     */
    @Override
    public int updateReceivableDiscountStatusLock(DiscountAddDto dto) {
        int rows =getSqlSession().update(NAMESPACE + "updateReceivableDiscountStatusLock",dto);
        return rows;
    }

	/**
	 * 通过折扣单号查询折扣单
	 * @param dto
	 * @return
	 */
	@Override
	public List<DiscountManagementEntity> queryDiscountBillByNo(DiscountAddDto dto) {
		return getSqlSession().selectList(NAMESPACE+"queryDiscountByNo",dto);
	}

	/**
	 * 根据客户查询该期间内折扣单的总条数
	 * @param dto
	 * @return
	 */
	@Override
	public int queryPeroidCountByCustomer(DiscountManagementDto dto) {
		 return (Integer)getSqlSession().selectOne(NAMESPACE + "queryPeriodCountByCustomer",dto);
	}
	/**
	 * 查询应收单总金额
	 */
	@Override
	public ReceivableBillDto queryReceiableAmountByCondition(ReceivableBillDto dto) {
		return (ReceivableBillDto)getSqlSession().selectOne(NAMESPACE + "queryReceiableAmountByCondition",dto);
	}

	@Override
	public List<DiscountManagementDEntity> queryDiscountDetailByDiscountNo(DiscountAddDto dto) {
		List<DiscountManagementDEntity> list = getSqlSession().selectList(NAMESPACE + "queryDiscountDetailByDiscountNo",dto);
		return list;
	}

    @Override
    public int queryCountDiscountByCustS(DiscountManagementDto dto) {
        Integer  count = (Integer)getSqlSession().selectOne(NAMESPACE + "queryCountDiscountByCustS", dto);
        return null == count ? 0 : count;
    }

}
