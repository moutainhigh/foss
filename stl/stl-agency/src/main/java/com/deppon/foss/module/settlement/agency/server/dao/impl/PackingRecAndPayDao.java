package com.deppon.foss.module.settlement.agency.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayTfrDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 包装其他应收应付输入界面
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-5 上午11:07:04,content:TODO </p>
 * @author 105762
 * @date 2014-6-5 上午11:07:04
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayDao extends iBatis3DaoImpl implements IPackingRecAndPayDao {

	private static final String NAMESPACE = "foss.stl.PackingRecAndPayDao.";// 命名空间路径

	/** 
	 * <p>按记账日期查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:20:38
	 * @param dto 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao#queryByAccountDate(com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PackingRecAndPayDto> queryByAccountDate(PackingRecAndPayQueryDto dto) {
		return (List<PackingRecAndPayDto>) this.getSqlSession().selectList(NAMESPACE + "queryByAccountDate", dto,
				new RowBounds(dto.getStart(), dto.getLimit()));
    }
	/**
	 * <p>按记账日期查询总数</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:20:38
	 * @param dto 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao#queryByAccountDate(com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto)
	 */
	@Override
	public Map queryTotalCountByAccountDate(PackingRecAndPayQueryDto dto) {
		return (Map) this.getSqlSession().selectOne(NAMESPACE + "queryTotalCountByAccountDate", dto);
	}

	/** 
	 * <p>按单号查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:20:38
	 * @param dto 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao#queryByWaybillNo(com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PackingRecAndPayDto> queryByWaybillNo(PackingRecAndPayQueryDto dto) {
		return (List<PackingRecAndPayDto>) this.getSqlSession().selectList(NAMESPACE + "queryByWaybillNo", dto,
				new RowBounds(dto.getStart(), dto.getLimit()));
	}

	/** 
	 * <p>按应收应付单号查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午5:20:38
	 * @param dto 
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao#queryByBillNo(com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PackingRecAndPayDto> queryByBillNo(PackingRecAndPayQueryDto dto) {
		return (List<PackingRecAndPayDto>) this.getSqlSession().selectList(NAMESPACE + "queryByBillNo", dto,
				new RowBounds(dto.getStart(), dto.getLimit()));
	}

	/** 
	 * <p>按运单号但句子类型应付类型查询应付单</p> 
	 * @author 105762
	 * @date 2014-6-18 上午11:10:08
	 * @param map
	 * @return 应付单
	 * @see com.deppon.foss.module.settlement.agency.api.server.dao.IPackingRecAndPayDao#queryByWaybillNosAndBillTypePayableType(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryByDtosAndBillTypePayableType(List<PackingRecAndPayTfrDto> packingRecAndPayTfrDtoList,
			String billPayableBillTypeWoodenPayable, String payableType) {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("list", packingRecAndPayTfrDtoList);
		map.put("billType", billPayableBillTypeWoodenPayable);
		map.put("payableType", payableType);
		map.put("active", FossConstants.YES);
		return (List<BillPayableEntity>) this.getSqlSession().selectList(NAMESPACE + "queryByDtosAndBillTypePayableType", map);
	}

}
