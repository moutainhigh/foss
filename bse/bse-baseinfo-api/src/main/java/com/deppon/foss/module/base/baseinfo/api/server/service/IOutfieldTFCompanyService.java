package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldTFCompanyEntity;

/**
 * 外场与所属运输财务公司关系Service接口
 * @author 132599-foss-shenweihua
 * @date 2013-11-26 上午10:16:52
 * @since
 * @version
 */
public interface IOutfieldTFCompanyService extends IService{
	
	/**
     * <p>新增外场与所属运输财务公司关系信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-11-26 上午10:20:21
     * @param entity
     * @return
     * @see
     */
    int addOutfieldTFCompany(OutfieldTFCompanyEntity entity,String createUser);
    
    /**
     * <p>修改外场与所属运输财务公司关系</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-11-26 上午10:22:21
     * @param entity
     * @return
     * @see
     */
    int updateOutfieldTFCompany(OutfieldTFCompanyEntity entity,String modifyUser);
    
    /**
     * <p>作废外场与所属运输财务公司关系</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:23:21
     * @param idList 外场与所属运输财务公司关系信息ID集合
     * @return
     * @see
     */
    int deleteOutfieldTFCompanyById(List<String> idList,String modifyUser);
	    

    /**
     * 根据传入对象查询符合条件所有外场与所属运输财务公司关系信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:24:21
     * @param entity
     *            外场与所属运输财务公司关系信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<OutfieldTFCompanyEntity> queryAllOutfieldTFCompany(OutfieldTFCompanyEntity entity,
	    int limit, int start);

    /**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-11-26 上午10:25:32
     * @param entity
     *             外场与所属运输财务公司关系信息实体
     * @return
     * @see
     */
    Long queryRecordCount(OutfieldTFCompanyEntity entity);
    
    /**
     * <p>根据外场编码查询所属运输财务公司名称</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-11-26 上午10:35:21
     * @param code  外场编码
     * @return
     * @see
     */
    String queryCompanyNameByOutfieldCode(String code);
    

}
