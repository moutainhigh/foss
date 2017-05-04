package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerDegreeEntity;

/**
 * 公共查询组件--"客户等级"的数据库对应数据访问DAO接口
 * @author 187862-dujunhui
 * @date 2014-9-23 上午8:22:43
 */
public interface ICommonCustomerDegreeDao {

    /**
     * 根据传入对象查询符合条件所有客户等级信息 
     * @author 187862-dujunhui
     * @date 2014-9-23 上午8:23:24
     * @param entity 查询实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */ 
    List<CommonCustomerDegreeEntity> queryCustomerDegreeListByCondition(CommonCustomerDegreeEntity entity,int limit,int start);
   
    /**
     * 统计总记录数 
     * @author 187862-dujunhui
     * @param entity 查询实体
     * @date 2014-9-23 上午8:24:16
     * @return 
     */
    Long countCustomerDegreeListByCondition(CommonCustomerDegreeEntity entity);
}
