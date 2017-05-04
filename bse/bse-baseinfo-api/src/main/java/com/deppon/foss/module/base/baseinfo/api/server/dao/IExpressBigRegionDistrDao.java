/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;

/**
 * 快递大区与行政区域映射关系DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 下午2:00:45 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午2:00:45
 * @since
 * @version
 */
public interface IExpressBigRegionDistrDao {
    
    /**
     * 新增快递大区与行政区域映射关系
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param entity 快递大区与行政区域映射关系明细实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addInfo(ExpressBigRegionDistrEntity entity);
    
    /**
     * 根据code作废快递大区与行政区域映射关系 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteInfo(List<String> codes,String modifyUser);
    
    /**
     * 修改快递大区与行政区域映射关系
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param entity 快递大区与行政区域映射关系实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateInfo(ExpressBigRegionDistrEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有快递大区与行政区域映射关系信息 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<ExpressBigRegionDistrEntity> queryInfos(ExpressBigRegionDistrEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午2:00:45
     * @param entity 快递大区与行政区域映射关系实体
     * @return
     * @see
     */
    Long queryRecordCount(ExpressBigRegionDistrEntity entity);

    /**
     * 根据有效状态、行政区域编码、快递大区编码查询快递大区行政区域映射信息
     * @author foss-dujunhui
     * @date 2014-6-23 下午4:52:50
     * @param orgCode
     * @param districtCode
     * @param active
     * @return
     */
    ExpressBigRegionDistrEntity queryInfoByDistrictCodeAndActive(String orgCode,String districtCode,String active);

	List<ExpressBigRegionDistrEntity> queryDeletEntityByIds(List<String> codes);
    
}
