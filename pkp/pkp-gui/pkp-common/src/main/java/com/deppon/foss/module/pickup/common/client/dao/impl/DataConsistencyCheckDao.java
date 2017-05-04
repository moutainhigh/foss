package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.cxf.tools.util.PropertyUtil;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.common.client.dao.IDataConsistencyCheckDao;
import com.deppon.foss.module.pickup.common.client.vo.DataConsistencyCheckVo;

public class DataConsistencyCheckDao implements IDataConsistencyCheckDao {

	private static final String NAMESPACE = "foss.sync.dataConsistencyCheck.";
	
	private SqlSession sqlSession;
	
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public List<DataConsistencyCheckVo> queryDownlodeTableDate() {
		List<DataConsistencyCheckVo> dataConsistencyCheck=new ArrayList<DataConsistencyCheckVo>();
		
		dataConsistencyCheck = sqlSession.selectList(
				NAMESPACE+"findLocalBaseLines");
		
		return dataConsistencyCheck;
	}
	
	@Override
	public List<DataConsistencyCheckVo> countQuerylocalTable(List<DataConsistencyCheckVo> loacalData) {
		Map<String, Object> map = new HashMap<String, Object>();
		//try{
		//	PropertyUtils.copyProperties(tempData, loacalData);
		//}catch(Exception e)
		//{
			
		//}
		
		map.put("ids", loacalData);
		return sqlSession.selectList(
				NAMESPACE+"queryLocalDate",map);
		
		 		
	}

}
