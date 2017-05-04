package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTransTeamEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
 
/**
 * 公共查询组件--“车队组信息”的数据库对应数据访问DAO接口
 * @author 187862-dujunhui
 * @date 2014-08-13 上午8:53:21
 */
public interface ICommonTransTeamDao {

    /**
     * 根据传入组织编码查询下属车队组
     * @author 187862-dujunhui
     * @date 2014-08-13 上午8:54:24
     * @param orgCode 组织编码
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */ 
    List<OrgAdministrativeInfoEntity> queryTransTeams(CommonTransTeamEntity entity, int limit, int start);
    
   
    /**
     * 统计总记录数 
     * @author 187862-dujunhui
     * @param orgCode 组织编码
     * @date 2014-08-13 上午8:56:36
     * @return 
     */
    Long countTransTeams(CommonTransTeamEntity entity);
}
