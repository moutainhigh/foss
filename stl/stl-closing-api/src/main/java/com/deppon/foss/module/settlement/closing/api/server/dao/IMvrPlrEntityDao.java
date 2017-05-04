package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPlrDto;

/**
 * 偏线月报DAO
 * @author ibm-zhuwei
 * @date 2013-3-5 下午6:16:16
 */
public interface IMvrPlrEntityDao {
	
    /**
     *根据传入查询偏线月报--分页
     * @author 095793-foss-LiQin
     * @param 
     * @date 2013-3-12 上午11:57:20
     * @return 
     */
    List<MvrPlrEntity> selectMvrPlrByParam(MvrPlrEntity dto,int start, int limit);
    
    /**
     * 根据传入查询偏线月报条数
     * @author 095793-foss-LiQin
     * @param 
     * @date 2013-3-12 上午11:57:22
     * @return 
     */
    MvrPlrDto selectMvrPlrByParamCount(MvrPlrEntity dto);
    
    
    
    /**
     * 根据传入查询偏线月报表--不分页
     * @author 095793-foss-LiQin
     * @date 2013-3-22 下午5:32:00
     * @param dto
     * @return
     */
    List<MvrPlrEntity> selectMvrPlrByParam(MvrPlrEntity dto);
}
