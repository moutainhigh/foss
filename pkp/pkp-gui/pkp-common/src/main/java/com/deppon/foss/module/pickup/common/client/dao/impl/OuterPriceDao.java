package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import com.deppon.foss.module.pickup.common.client.dao.IOuterPriceDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;

/**
 * 偏线价格Dao

  * @ClassName: IOuterPriceDao

  * @Description: 20131011下载离线数据功能 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
public class OuterPriceDao implements IOuterPriceDao{
	
	public static final String NAMESPACE = "com.deppon.foss.module.pickup.pricing.api.server.dao.OuterPriceEntityMapper.";
	
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean insertSelective(OuterPriceEntity record) {
		String sql = NAMESPACE + "insertSelective";		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", record.getOuterPriceId());
		Object o = sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", map);
		if(o != null){
			String id = ((OuterPriceEntity)o).getOuterPriceId();
			if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
					id, ObjectUtils.NULL))){
				sqlSession.insert(sql, record);
				return true;
			}else{
				return false;
			}
		}else{
			sqlSession.insert(sql, record);
			return true;
		}
	}
	
	@Override
	public int updateByPrimaryKeySelective(OuterPriceEntity record) {
		String sqlAddress = NAMESPACE + "updateOuterPriceByPrimaryKey";		
		return sqlSession.update(sqlAddress, record);				
	}
	
	@Override
	public int deleteByPrimaryKey(List<String> ids) {
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("ids", ids);
		return sqlSession.delete(NAMESPACE + "deleteByPrimaryKey", paraMap);
	}

	@Override
	public OuterPriceEntity selectByPrimaryKey(String id) {
		String sql = NAMESPACE + "selectByPrimaryKey";		
		return (OuterPriceEntity)sqlSession.selectOne(sql, id);				
	}
	
}
