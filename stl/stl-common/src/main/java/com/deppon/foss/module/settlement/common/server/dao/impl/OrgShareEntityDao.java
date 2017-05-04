package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IOrgShareEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity;

/**
 * 
 * 理赔成本划分实体DAO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-27 上午11:48:01
 */
public class OrgShareEntityDao extends iBatis3DaoImpl implements
		IOrgShareEntityDao {

	private static final String NAMESPACE = "foss.stl.OrgShareEntityDao.";

	/** 
	 * 新增成本划分数据
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 下午2:03:15
	 * @param entity 理赔成本划分实体
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IOrgShareEntityDao#add(com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity)
	 */
	@Override
	public int add(OrgShareEntity entity) {
		return getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	
	/** 
	 * 查询成本划分 ，返回部门信息为   标杆编码
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 下午2:03:15
	 * @param entity 理赔成本划分实体
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IOrgShareEntityDao#add(com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgShareEntity> selectBySourceBillNo(String sourceBillNo,String active){
		//判断传入来源单号
		if(StringUtils.isNotBlank(sourceBillNo)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sourceBillNo", sourceBillNo);
			map.put("active", active);
			return getSqlSession().selectList(NAMESPACE + "selectBySourceBillNo", map);
		}else{
			return null;
		}
		
	}
}

