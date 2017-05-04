package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionAreaPlanEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExhibitionAreaPlanException;
/**
 * 展馆区域规划Service接口
 * @author 187862
 * @date 2015-7-7 上午10:40:13
 *
 */
public interface IExhibitionAreaPlanService extends IService{
	
	/**
	 * 新增展馆区域规划
	 * @param entity
	 * @return
	 * @throws ExhibitionAreaPlanException
	 * @param @param entity
	 * @param @return
	 * @param @throws ExhibitionAreaPlanException
	 * @author 187862-dujunhui
	 * @date 2015-7-7 上午10:46:08
	 */
	int addExhibitionAreaPlan(ExhibitionAreaPlanEntity entity)throws ExhibitionAreaPlanException;

	/**
	 * 根据code作废展馆区域规划信息
	 * @param codes
	 * @param modifyUser
	 * @return
	 * @param @param codes
	 * @param @param modifyUser
	 * @author 187862-dujunhui
	 * @date 2015-7-7 上午10:46:08
	 */
	int deleteExhibitionAreaPlanByCode(String[] codes, String modifyUser);
	
	/**
	 * 根据code作废展馆区域规划信息
	 * @param codes
	 * @param modifyUser
	 * @return
	 * @param @param codes
	 * @param @param modifyUser
	 * @author 187862-dujunhui
	 * @date 2015-7-7 上午10:46:08
	 */
	int updateExhibitionAreaPlanByCode(ExhibitionAreaPlanEntity entity);

	/**
	 * 根据传入对象查询符合条件的所有展馆区域规划信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @param @param entity
	 * @param @param limit
	 * @param @param start
	 * @param @return
	 * @author 187862-dujunhui
	 * @date 2015-7-7 上午11:30:16
	 */
	List<ExhibitionAreaPlanEntity> queryExhibitionAreaPlanByCondition(
			ExhibitionAreaPlanEntity entity, int limit, int start);

	/**
	 * 统计总记录数
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午1:58:48
	 */
	Long queryRecordCount(ExhibitionAreaPlanEntity entity);

	/**
	 * 根据区域编码查询展馆区域规划详细信息，验证唯一
	 * @param exhibitionCode
	 * @return
	 * @param @param exhibitionCode
	 * @param @return
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午2:04:15
	 */
	ExhibitionAreaPlanEntity queryExhibitionAreaPlanByCode(String exhibitionCode);


	/**
	 * 验证展馆名称是否唯一
	 * @param exhibitionName
	 * @return
	 * @param @param exhibitionName virtualCode
	 * @param @return
	 * @author 187862-dujunhui
	 * @date 2015-7-7 下午2:05:33
	 */
	ExhibitionAreaPlanEntity queryExhibitionAreaPlanByName(String exhibitionName,String virtualCode);

	/**
	 * 根据管理编码查询展馆区域规划编码最大值
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 187862-dujunhui
	 * @date 2015-7-9 上午10:40:27
	 */
	String queryCodeByManagement(ExhibitionAreaPlanEntity entity);
	
	/**
   	 * 自动生成展馆编码
   	 * @param entity
   	 * @return
   	 * @param @param entity
   	 * @param @return
   	 * @author 187862-dujunhui
   	 * @date 2015-7-9 上午10:43:43
   	 */
	String createCodeByManagement(ExhibitionAreaPlanEntity entity);
	
}