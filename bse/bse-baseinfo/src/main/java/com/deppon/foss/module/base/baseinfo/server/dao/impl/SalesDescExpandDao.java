package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDescExpandDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
/**
 * 
 * 营业部自提派送区域描述扩展类
 * @author 088933-foss-zhangjiheng
 * @date 2013-6-20 下午4:06:44
 */
public class SalesDescExpandDao  extends SqlSessionDaoSupport implements ISalesDescExpandDao {
	 /**
     * 
     * mybatis 命名空间
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".salesDescExpand.";

	@Override
	public int addSalesDescExpand(SalesDescExpandEntity entity) {
		return  getSqlSession().insert(NAMESPACE+"addSaleDescExpand", entity);
	}

	@Override
	public int updateSalesDescExpand(SalesDescExpandEntity entity) {
		return getSqlSession().update(NAMESPACE+"deleteSaleDescExpand", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDescExpandEntity> querySalesDescExpandByCode(String code,String active,String type) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("code", code);
		map.put("active", active);
		map.put("descType", type);
		return getSqlSession().selectList(NAMESPACE+"querySaleDescExpandByCode", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDescExpandEntity> querySalesDescExpandForDownloadByPage(
			SalesDescExpandEntity entity, int start, int lmited) {
		RowBounds rowBounds=new RowBounds(start,lmited);
		if(entity==null){
			entity=new SalesDescExpandEntity();
		}
		return getSqlSession().selectList(NAMESPACE+"querySaleDescExpandForDownload", entity, rowBounds);
	}

	
	
	/**
	 * 下载营业部信息
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDescExpandEntity> querySalesDescExpandForDownload(
			SalesDescExpandEntity entity){
		if(entity==null){
			return new ArrayList<SalesDescExpandEntity>();
		}
		return getSqlSession().selectList(NAMESPACE+"querySaleDescExpandForDownload", entity);
	}
}
