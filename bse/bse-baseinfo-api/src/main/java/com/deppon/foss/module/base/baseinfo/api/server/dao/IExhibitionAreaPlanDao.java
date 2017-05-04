package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionAreaPlanEntity;
/**
 * 展馆区域规划Dao接口
 * @author 187862
 * @date 2015-7-7 上午9:8:7
 *
 */
public interface IExhibitionAreaPlanDao {

	/**
	 * 新增展馆区域规划
	 * @param entity
	 * @return
	 * @param @param entity
	 * @author 187862-dujunhui
	 * @date 2015-7-7 上午9:39:14
	 */
    int addExhibitionAreaPlan(ExhibitionAreaPlanEntity entity);

    /**
     * 根据code作废展馆区域规划信息
     * @param codes
     * @param modifyUser
     * @return
     * @param @param codes
     * @param @param modifyUser
     * @author 187862-dujunhui
     * @date 2015-7-7 上午9:40:22
     */
    int deleteExhibitionAreaPlanByCode(String[] codes, String modifyUser);
    
    /**
     * 根据虚拟code作废展馆区域规划信息
     * @param virtualCodes
     * @param modifyUser
     * @return
     * @param @param codes
     * @param @param modifyUser
     * @author 187862-dujunhui
     * @date 2015-9-6 下午4:07:48
     */
	int deleteExhibitionAreaPlanByVirCode(String[] virtualCodes, String modifyUser);

    /**
     * 根据传入对象查询符合条件的所有展馆区域规划信息
     * @param entity
     * @param limit
     * @param start
     * @return
     * @param @param entity
     * @param @param limit
     * @param @param start
     * @author 187862-dujunhui
     * @date 2015-7-7 上午9:43:05
     */
    List<ExhibitionAreaPlanEntity> queryExhibitionAreaPlans(
    		ExhibitionAreaPlanEntity entity,int limit, int start);

   /**
    * 统计总记录数
    * @param entity
    * @return
    * @param @param entity
    * @param @return
    * @author 187862-dujunhui
    * @date 2015-7-7 上午9:45:35
    */
    Long queryRecordCount(ExhibitionAreaPlanEntity entity);

   /**
    * 根据展馆区域编码查询展馆区域规划详细信息，验证唯一性
    * @param exhibitionCode
    * @return
    * @param @param exhibitionCode
    * @author 187862-dujunhui
    * @date 2015-7-7 上午10:27:42
    */
    ExhibitionAreaPlanEntity queryExhibitionAreaPlanByCode(String exhibitionCode);

   /**
    * 验证展馆区域名称是否唯一
    * @param exhibitionName
    * @return
    * @param @param exhibitionName
    * @author 187862-dujunhui
    * @date 2015-7-7 上午10:29:22
    */
    ExhibitionAreaPlanEntity queryExhibitionAreaPlanByName(String exhibitionName);

    /**
     * 通过管理部门编码查询小区编码
     * @param entity
     * @return
     * @param @param entity
     * @param @return
     * @author 187862-dujunhui
     * @date 2015-7-7 上午10:33:17
     */
    String queryCodeByManagement(ExhibitionAreaPlanEntity entity);
    
}
