package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;


import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AllAgentEntity;
/**
 * 公共查询选择器--“代理信息”Service接口
 * 
 * @author 130346-foss-lifanghong
 * @date 2013-05-16
 */
public interface ICommonAllAgentService {
	
	 /**
     * 根据传入对象查询符合条件所有空运代理信息 
     * @author 130346-foss-lifanghong
     * @date 2013-05-16
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的vo
     */
	List<AllAgentEntity> queryAgentByCondtion(AllAgentEntity allAgentEntity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 130346-foss-lifanghong
     * @date 2013-05-16
     * @return 符合条件的总记录数
     */
    Long countRecordByCondtion(AllAgentEntity allAgentEntity);

}
