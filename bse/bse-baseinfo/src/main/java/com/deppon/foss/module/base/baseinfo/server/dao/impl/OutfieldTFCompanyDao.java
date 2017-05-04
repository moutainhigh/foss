package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOutfieldTFCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldTFCompanyEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

	/**
	 * 外场与所属运输财务公司关系Dao接口实现
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-26 下午7:10:52
	 * @since
	 * @version
	 */
	public class OutfieldTFCompanyDao extends SqlSessionDaoSupport implements IOutfieldTFCompanyDao{
		
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.outfieldTFCompany.";
	
	/**
     * <p>新增外场与所属运输财务公司关系信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-11-26 下午7:13:52
     * @param entity
     * @return
     * @see
     */
	@Override
	public int addOutfieldTFCompany(OutfieldTFCompanyEntity entity,String createUser) {
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateUser(createUser);
		entity.setModifyUser(createUser);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	
	/**
     * <p>作废外场与所属运输财务公司关系</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 下午7:16:52
     * @param idList 外场与所属运输财务公司关系信息ID集合
     * @return
     * @see
     */
	@Override
	public int deleteOutfieldTFCompanyById(List<String> idList,String modifyUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);
		map.put("modifyUser", modifyUser);
		return this.getSqlSession().update(NAMESPACE + "deleteById", map);
	}
	
	 /**
     * 根据传入对象查询符合条件所有外场与所属运输财务公司关系信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 下午7:18:52
     * @param entity
     *            外场与所属运输财务公司关系信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<OutfieldTFCompanyEntity> queryAllOutfieldTFCompany(
			OutfieldTFCompanyEntity entity, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryAllOutfieldTFCompany",
				entity, rowBounds);
	}
	
	/**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-11-26 下午7:19:52
     * @param entity
     *             外场与所属运输财务公司关系信息实体
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(OutfieldTFCompanyEntity entity) {
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}
	
	/**
     * <p>根据外场编码查询所属运输财务公司名称</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-11-26 下午7:20:52
     * @param code  外场编码
     * @return
     * @see
     */
	@Override
	public String queryCompanyNameByOutfieldCode(String outfieldCode) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outfieldCode", outfieldCode);
		map.put("active", FossConstants.ACTIVE);
		
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryCompanyNameByOutfieldCode",
				map);
	}

}
