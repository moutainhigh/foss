package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IQueryPodInfoDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.PodDto;

/**
 * 签收记录的查询的dao层实现
 * @author 198704 weitao
 * @date 2014-08-23
 */
public class QueryPodInfoDao extends iBatis3DaoImpl implements IQueryPodInfoDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.stl.QueryPodInfoDao.";
	
	/**
	 * 通过前台传来的dto进行查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PodDto> queryPodInfo(PodDto dto, int start, int limit) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(start,limit);
	    //返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "selectAll",dto,rb);
	}

	/**
	 * 得查询结果的总数据条数
	 */
	@Override
	public int queryPodInfoCount(PodDto dto) {
		//返回查询出数据的总条数
		return (Integer)this.getSqlSession().selectOne(NAMESPACES + "selectCount", dto);
	}

}
