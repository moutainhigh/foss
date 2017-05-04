package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfiEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfiDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 出发到达往来报表查询dto.
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午2:33:34
 */
public class MvrRfiEntityDao extends iBatis3DaoImpl implements IMvrRfiEntityDao {

	/** 命名空间. */
	private static final String NAMESPACE = "foss.stv.MvrRfiEntityMapper.";// 命名预付单空间

	/**
	 * 查询出发到达往来报表.
	 *
	 * @param rb the rb
	 * @param dto the dto
	 * @param isPaging the is paging
	 * @return the list
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:33:34
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfiEntityDao#selectByConditions()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrRfiEntity> selectByConditions(RowBounds rb,MvrRfiDto dto,String isPaging) {
		//声明查询map
		Map<String,Object> map = new HashMap<String,Object>();
		//封装查询参数
		map.put("period", dto.getPeriod());
		map.put("customerCode",dto.getCustomerCode());
		map.put("deptCode", dto.getDeptCode());
		map.put("orgType",dto.getOrgType());
		//是否分页
		if(FossConstants.YES.equals(isPaging)){
			return this.getSqlSession().selectList(NAMESPACE+"selectByConditions",map,rb);
		}else{
			return this.getSqlSession().selectList(NAMESPACE+"selectByConditions",map);
		}
		
	}

	/**
	 * 查询始发到达往来报表总条数.
	 *
	 * @param dto the dto
	 * @return the mvr rfi entity
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 */
	public MvrRfiEntity queryTotalCounts(MvrRfiDto dto) {
		//声明查询map
		Map<String,Object> map = new HashMap<String,Object>();
		//封装查询参数
		map.put("period", dto.getPeriod());
		map.put("customerCode",dto.getCustomerCode());
		map.put("deptCode", dto.getDeptCode());
		map.put("orgType",dto.getOrgType());
		return (MvrRfiEntity) this.getSqlSession().selectOne(NAMESPACE+"selectTotalCounts",map);
	}

}
