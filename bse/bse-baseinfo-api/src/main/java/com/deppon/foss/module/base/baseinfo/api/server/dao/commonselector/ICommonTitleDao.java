package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity;
 
/**
 * 公共查询组件--“岗位信息”的数据库对应数据访问DAO接口
 * @author 187862-dujunhui
 * @date 2014-08-08 上午8:30:21
 */
public interface ICommonTitleDao {

    /**
     * 根据传入对象查询符合条件所有职位信息 
     * @author 187862-dujunhui
     * @date 2014-08-08 上午8:31:24
     * @param entity 查询实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */ 
    List<CommonTitleEntity> queryTitleListByCondition(CommonTitleEntity entity,int limit,int start);
    
   
    /**
     * 统计总记录数 
     * @author 187862-dujunhui
     * @param entity 查询实体
     * @date 2014-08-08 上午8:35:36
     * @return 
     */
    Long countTitleListByCondition(CommonTitleEntity entity);
}
