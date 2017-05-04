package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressIntelligentSortCabDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.PathDetailEntity;

public class ExpressIntelligentSortCabDao extends iBatis3DaoImpl implements IExpressIntelligentSortCabDao{

	private static final String NAMESPACE = "foss.load.express.intelligentSortCab.";
	/**
	* @description 通过部门，出发时间 范围拉取在途运单的走货路由
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressIntelligentSortCabDao#queryWaybillPathDetail(java.lang.String, java.util.Date, java.util.Date)
	* @author 105869-foss-heyongdong
	* @update 2015年4月13日 上午9:52:57
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryWaybillPathDetail(String depetCode, Date startTime, Date endTime,int weightLimit) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("depetCode",depetCode);
		map.put("startTime", startTime);
		map.put("endTime",endTime);
		map.put("weightLimit", weightLimit);
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillPathDetail",map);
	}
	
	
	/**
	* @description 查询路由总条数
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressIntelligentSortCabDao#queryWaybillPathDetailCount(java.lang.String, java.util.Date, java.util.Date)
	* @author 105869-foss-heyongdong
	* @update 2015年4月25日 下午2:35:46
	* @version V1.0
	*/
	@Override
	public int queryWaybillPathDetailCount(String depetCode, Date startTime, Date endTime,int weightLimit) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("depetCode",depetCode);
		map.put("startTime", startTime);
		map.put("endTime",endTime);
		map.put("weightLimit", weightLimit);
		return   (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryWaybillPathDetailCount", map);
	}


	
	/**
	* @description 查询运输性质
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressIntelligentSortCabDao#queryWaybillTransportName(java.lang.String)
	* @author 105869-foss-heyongdong
	* @update 2015年5月21日 下午2:12:50
	* @version V1.0
	*/
	@Override
	public String queryWaybillTransportName(String productCode) {
		// TODO Auto-generated method stub
		return (String)this.getSqlSession().selectOne(NAMESPACE+"queryWaybillTransportName",productCode);
	}

}
