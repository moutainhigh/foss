package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;

public interface ICommonInfoDeptService {
	/**
     * 根据传入对象查询符合条件所有落地公司信息 
     * 
     * @author lifanghong
     * @date 2014-02-18 
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
    List<InfoDeptEntity> queryInfoDeptEntityByCondtion(InfoDeptEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author lifanghong
     * @date  2014-02-18
     * @return 符合条件的总记录数
     */
    Long countRecordByCondition(InfoDeptEntity entity);

}
