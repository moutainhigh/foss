package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrderSourceEntity;
 
/**
 * 公共查询组件--"订单来源"的数据库对应数据访问DAO接口
 * @author 187862-dujunhui
 * @date 2014-9-22 下午2:36:12
 */
public interface ICommonOrderSourceDao {

    /**
     * 根据传入对象查询符合条件所有订单来源信息 
     * @author 187862-dujunhui
     * @date 2014-9-22 下午2:36:58
     * @param entity 查询实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */ 
    List<CommonOrderSourceEntity> queryOrderSourceListByCondition(CommonOrderSourceEntity entity,int limit,int start);
   
    /**
     * 统计总记录数 
     * @author 187862-dujunhui
     * @param entity 查询实体
     * @date 2014-9-22 下午2:37:53
     * @return 
     */
    Long countOrderSourceListByCondition(CommonOrderSourceEntity entity);
}
