package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEarlyWarningDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity;
import com.deppon.foss.base.util.define.NumberConstants;
/**
 * 
 * 提前预警DAO类实现
 * @author 262036 - huangwei
 * @date 2015-8-19 下午7:20:12
 * @since
 * @version
 */
public class EarlyWarningDao extends SqlSessionDaoSupport implements IEarlyWarningDao{
	
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.earlyWarning.";
    /**
     * 
     * 根据传入对象查询提前预警线路信息
     * @author 262036 - huangwei
     * @date 2015-8-19 下午7:20:28
     * @param entity
     * @param limit
     * @param start
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEarlyWarningDao#queryEarlyWarningEntitysByCityCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity, int, int)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<EarlyWarningEntity> queryEarlyWarningEntitysByCityCode(
			EarlyWarningEntity entity, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(NAMESPACE + "queryAllEarlyWarning", entity, rowBounds);
	}
	/**
	 * 
	 * 根据传入对象查询总记录数
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:20:41
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEarlyWarningDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity)
	 */
	@Override
	public Long queryRecordCount(EarlyWarningEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);
	}
	
	/**
	 * 批量插入导入的提前预警线路信息
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:20:55
	 * @param earlyWarningEntitys
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEarlyWarningDao#insertEarlyWarnings(java.util.List)
	 */
	@Override
	public List<EarlyWarningEntity> insertEarlyWarnings(
			List<EarlyWarningEntity> earlyWarningEntitys) {
		
		if(earlyWarningEntitys.size() > 0) {
		    int batchSize = NumberConstants.NUMBER_1000;
		    if(earlyWarningEntitys.size() <= batchSize) {
		        //不够1000个一批
		    	this.getSqlSession().insert(NAMESPACE+"importEarlyWarningBatch", earlyWarningEntitys);
		    } else {
		        int count = earlyWarningEntitys.size() / batchSize;
		        if(earlyWarningEntitys.size() % batchSize != 0) {
		            count += 1;
		        }
		            
		        List<EarlyWarningEntity> tempList;
		        int startIndex;
		        int endIndex;
		            
		        for (int i = 0; i < count; i++) {
		            startIndex = i * batchSize;
		            endIndex = startIndex + batchSize;
		            if(endIndex > earlyWarningEntitys.size()) {
		                endIndex = earlyWarningEntitys.size();
		            }
		            //log.info(" =========== 插入批次：" + (i+1) + ", startIndex:" + startIndex + ", endIndex:" + endIndex);
		            tempList = earlyWarningEntitys.subList(startIndex, endIndex);
		            this.getSqlSession().insert(NAMESPACE+"importEarlyWarningBatch", tempList);
		        }
		    }
		}            
		return earlyWarningEntitys;
	}
	/**
	 * 
	 * 批量作废提前预警信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午7:21:04
	 * @param earlyWarningEntity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEarlyWarningDao#deleteEarlyWarnings(com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity)
	 */
	@Override
	public int deleteEarlyWarnings(EarlyWarningEntity earlyWarningEntity) {
		return this.getSqlSession().update(NAMESPACE+"deleteEarlyWarningBatch", earlyWarningEntity);
	}
	
}
