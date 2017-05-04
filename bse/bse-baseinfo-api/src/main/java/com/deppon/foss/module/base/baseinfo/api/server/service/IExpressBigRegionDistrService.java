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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DistrictDto;

/**
 * 快递大区与行政区域映射关系Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 下午2:00:45 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午2:00:45
 * @since
 * @version
 */
public interface IExpressBigRegionDistrService extends IService {
    
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
     * <p>批量保存快递大区与行政区域映射关系</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-15 上午9:26:01
     * @param list
     * @return
     * @see
     */
    int batchAddInfos(List<ExpressBigRegionDistrEntity> list,String userCode);
    
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
     * <p>批量修改快递大区与行政区域映射关系</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-15 下午7:04:32
     * @param addList 新增的数据集合
     * @param deleteList 删除的数据集合
     * @param userCode 操作人编码
     * @return
     * @see
     */
    int batchUpdateInfos(List<ExpressBigRegionDistrEntity> addList,List<ExpressBigRegionDistrEntity> deleteList,String userCode);
    
    /**
     * <p>根据ID查询快递大区与行政区域映射关系实体</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-15 下午3:55:46
     * @param id
     * @return
     * @see
     */
    ExpressBigRegionDistrEntity queryInfoById(String id);
    
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
     * <p>获取快递大区所映射行政区域LIST(最小一级，如果行政区域是市，返回市下面所有区\县的列表)</p> 
     * @author zhangdongping
     * @date Jul 31, 2013 2:09:39 PM
     * @param orgCode
     * @return
     * @see
     */
    List<DistrictDto> queryDistrictDtoListForOrgCode(String orgCode);
    
    /**
     * 根据有效状态、行政区域编码、快递大区编码查询快递大区行政区域映射信息
     * @author 187862-dujunhui
     * @date 2014-6-23 下午4:56:22
     * @param orgCode
     * @param districtCode
     * @param active
     * @return
     */
    ExpressBigRegionDistrEntity queryInfoByDistrictCodeAndActive(String orgCode,String districtCode,String active);
     
}
