package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillingGroupTransFerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 外场和集中开单组关系Dao
 * @author foss-lifanghong
 * @date 2013-06-02
 * @version 1.0
 */
public class BillingGroupTransFerDao extends SqlSessionDaoSupport implements IBillingGroupTransFerDao {

    /**
     * 日志类
     */
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(BillingGroupTransFerDao.class);

    /**
     * 
     * mybatis 命名空间
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".billingGroupTransFerEntity."; 
	
    /**
     * 
     * <p>根据集中开单组部门编码查找外场列表</p> 
     * @author foss-lifanghong
     * @date2013-06-02
     * @param billingGroupCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public BillingGroupTransFerEntity queryBillingGroupTransferCenterCode(String billingGroupCode) {
	BillingGroupTransFerEntity entity = new BillingGroupTransFerEntity();
	entity.setBillingGroupCode(billingGroupCode);
	entity.setActive(FossConstants.ACTIVE);
	List<BillingGroupTransFerEntity> list = (List<BillingGroupTransFerEntity>) getSqlSession().selectList(NAMESPACE + "queryBillingGroupByBillingGroupCode", entity, new RowBounds(0, 1));
	return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
    
    
    
    /**
     * 
     * <p>根据集中开单组部门编码和时间查找外场</p> 
     * @author foss-lifanghong
     * @date2013-07-31
     * @param billingGroupCode
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public BillingGroupTransFerEntity queryBillingGroupTransferCenterCode(String billingGroupCode,Date createTime) {
	BillingGroupTransFerEntity entity = new BillingGroupTransFerEntity();
	entity.setBillingGroupCode(billingGroupCode);
	entity.setCreateTime(createTime);
	return (BillingGroupTransFerEntity)this.getSqlSession().selectOne(NAMESPACE + "queryTransFerEntityBillingGroupCode", entity);
    }

    
    
    /**
     * 
     * <p>新增集中开单组和外场对应关系</p> 
     * @author foss-zhujunyong
     * @date Jun 8, 2013 6:06:15 PM
     * @param entity
     * @return
     * @see
     */
    @Override
    public BillingGroupTransFerEntity addBillingGroupTransfer(BillingGroupTransFerEntity entity){
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setVirtualCode(entity.getId());
	entity.setCreateDate(now);
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setActive(FossConstants.ACTIVE);
	entity.setVersion(now.getTime());
	int result =  getSqlSession().insert(NAMESPACE + "addBillingGroupTransfer", entity);
	return result > 0 ? entity : null;
    }
    
    /**
     * 
     * <p>作废集中开单组和外场对应关系</p> 
     * @author foss-zhujunyong
     * @date Jun 8, 2013 6:40:11 PM
     * @param entity
     * @see
     */
    public BillingGroupTransFerEntity deleteBillingGroupTransferByBillingGroupCode(BillingGroupTransFerEntity entity) {
	Date now = new Date();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("inactive", FossConstants.INACTIVE);
	map.put("active", FossConstants.ACTIVE);
	map.put("modifyDate", now);
	map.put("version", now.getTime());
	map.put("modifyUser", entity.getModifyUser());
	map.put("billingGroupCode", entity.getBillingGroupCode());
	getSqlSession().update(NAMESPACE + "deleteBillingGroupTransferByBillingGroupCode", map);
	entity.setModifyDate(now);
	return entity;
    }
    
    /**
     * 
     * <p>更新集中开单组和外场对应关系</p> 
     * @author foss-zhujunyong
     * @date Jun 9, 2013 10:33:34 AM
     * @param parm
     * @return
     * @see
     */
    public BillingGroupTransFerEntity updateBillingGroupTransfer(BillingGroupTransFerEntity parm) {
	BillingGroupTransFerEntity entity = deleteBillingGroupTransferByBillingGroupCode(parm);
	if (entity == null) {
	    return null;
	}
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(entity.getModifyDate());
	entity.setVersion(entity.getModifyDate().getTime());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addBillingGroupTransfer", entity);
	return result > 0 ? entity : null;
    }
    
    /**
     * 
     * <p>查询符合条件的集中开单组和外场关系表供下载</p> 
     * @author foss-zhujunyong
     * @date Jun 3, 2013 1:53:03 PM
     * @param entity
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BillingGroupTransFerEntity> querybillingGroupTransferGroupListForDownload(BillingGroupTransFerEntity entity) {
	return (List<BillingGroupTransFerEntity>) getSqlSession().selectList(NAMESPACE + "querybillingGroupTransferCenterListForDownload", entity);
    }

}
