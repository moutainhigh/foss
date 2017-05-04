 
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;
 
/**
 * 综合查询标记和跟踪记录Service
 * @author 101911-foss-zhouChunlai
 * @date 2013-6-1 下午5:19:47
 */
public interface IBseWaybillQueryService extends IService {
	 /**
     * 新增运单紧急情况标记
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @return
     * @see
     */
    int addWaybillMark(WaybillMarkEntity entity);

    /**
     * 修改运单紧急情况标记
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @return
     * @see
     */
    int updateWaybillMark(WaybillMarkEntity entity);
    
    
    
    /**
     * 新增跟踪记录方案
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @return
     * @see
     */
    int addTrackRecord(TrackRecordEntity entity);
    /**
     * 根据code作废自定义查询方案
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param codes
     * @return
     * @see
     */
    int deleteUserDefinedQuerySchemeByCode(String[] codes, String modifyUser);

    /**
     * 新增自定义查询方案和条件
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @param limit
     * @param start
     * @return List<UserDefinedQueryDto>
     * @see
     */
    int addUserDefinedQueryDto(UserDefinedQueryDto userDefinedQuery,
	    String createUser);

    /**
     * 修改自定义查询方案和条件
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @param limit
     * @param start
     * @return List<UserDefinedQueryDto>
     * @see
     */
    int updateUserDefinedQueryDto(UserDefinedQueryDto userDefinedQuery,
	    String modifyUser); 
    
    /**
     * 批量新增  标记
     * 
     * @param waybillNo 运单号
     *        serialNo  流水号
     * @return WaybillVo
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-24 下午5:20:22
     */
    int addWaybillMarkList(String[] codeStr, String markStatus);
} 