package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerProfessionEntity;
/**
 * 公共查询组件--"客户行业"的数据库对应数据访问DAO接口
 * @author 187862-dujunhui
 * @date 2014-9-23 上午10:35:28
 */
public interface ICommonCustomerProfessionDao {

    /**
     * 根据传入对象查询符合条件所有客户行业信息 
     * @author 187862-dujunhui
     * @date 2014-9-23 上午10:36:15
     * @param entity 查询实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */ 
    List<CommonCustomerProfessionEntity> queryCustomerProfessionListByCondition(CommonCustomerProfessionEntity entity,int limit,int start);
   
    /**
     * 统计总记录数 
     * @author 187862-dujunhui
     * @param entity 查询实体
     * @date 2014-9-23 上午10:37:09
     * @return 
     */
    Long countCustomerProfessionListByCondition(CommonCustomerProfessionEntity entity);
}
