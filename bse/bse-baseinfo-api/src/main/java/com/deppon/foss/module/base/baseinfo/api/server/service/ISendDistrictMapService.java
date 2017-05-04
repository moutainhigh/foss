package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictItemAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SendDistrictMapVo;

/**
 * TODO(SendDistrictMap的Service接口)
 * @author WeiXing
 * @date 2014-10-21 下午1:35:23
 * @since
 * @version
 */
public interface ISendDistrictMapService extends IService {
	/**
	 * 
	* @Title: querySendDistrictMap
	* @Description: 根据 外场code 分区code active状态 查询 分区（唯一）信息 分页
	* @author 189284--zhang xu 
	* @date 2014-11-14 上午10:13:16 
	* @param @param entity
	* @param @param limit
	* @param @param start
	* @param @return
	* @return List<SendDistrictMapEntity>    返回类型
	 */
	List<SendDistrictMapEntity> querySendDistrictMap(SendDistrictMapEntity entity, int limit, int start);
	/**
	 * 
	* @Title: querySendDistrictMap(提供给中转清仓的接口（过滤掉自提区的）)
	* @Description: 根据 外场code 分区code active状态 查询 分区（唯一）信息
	* @author 189284--zhang xu 
	* @date 2014-11-14 上午10:13:16 
	* @param @param entity
	* @param @param limit
	* @param @param start
	* @param @return
	* @return List<SendDistrictMapEntity>    返回类型
	 */
	List<SendDistrictMapEntity> querySendDistrictMap(SendDistrictMapEntity entity);
	
	/**
	 * 
	 * @Description:统计
	 * @author:WeiXing
	 */
	Long queryRecordCount(SendDistrictMapEntity entity);
	
	/**
	 *<P>根据条件分页查询(根据外场Code或者行政区域Code查询派送货区行政区域映射信息)<P>
	 * @author :WeiXing
	 * @date : 2014-10-29下午1:53:32
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<SendDistrictMapEntity> querySendDistrictMapList(SendDistrictMapEntity entity, int limit, int start);
	
	/**
	 *<P>根据条件查询总数<P>
	 * @author :WeiXing
	 * @date : 2014-10-29下午1:57:43
	 * @param entity
	 * @return
	 */
	long querySendDistrictMapCount(SendDistrictMapEntity entity);
	
	/**
	 *<P>根据条件分页查询(根据transfer_center_code,goods_area_code,zone_code进行分组查询)<P>
	 * @author :WeiXing
	 * @date : 2014-10-29下午1:53:32
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<SendDistrictMapEntity> querySendDistrictMapGroupList(SendDistrictMapEntity entity, int limit, int start);
	
	/**
	 *<P>根据条件查询总数<P>
	 *根据transfer_center_code,goods_area_code,zone_code进行分组查询
	 * @author :WeiXing
	 * @date : 2014-10-29下午1:57:43
	 * @param entity
	 * @return
	 */
	long querySendDistrictMapGroupCount(SendDistrictMapEntity entity);
	
	
	/**
	 *<P>根据SendDistrictMapEntity查询实体列表<P>
	 * @author :WeiXing
	 * @date : 2014-10-28下午3:43:13
	 * @param SendDistrictMapEntity
	 * @return
	 */
	List<SendDistrictMapEntity> querySendDistrictMapListbyEntity(SendDistrictMapEntity entity);
	
	/**
	 *<P>根据ID批量删除<P>
	 * @author :WeiXing
	 * @date : 2014-11-04下午3:43:13
	 * @param List,String
	 * @return long
	 */
	long deleteSendDistrictMapByIds(List<String> ids,String deleteUser);
	
	/**
	 * 
	* @Title: addSendDistrictMap
	* @Description:(新增 派送货区行政区域映射基础资料) 
	* @author 189284--zhang xu 
	* @date 2014-10-30 下午8:43:19 
	* @param @param sendDistrictMap
	* @param @param sendDistrictMapList
	* @param @return
	* @return Integer    返回类型
	 */
	Integer addSendDistrictMap(SendDistrictMapEntity sendDistrictMap,
			List<SendDistrictMapEntity> sendDistrictMapList,
			List<SendDistrictItemAreaEntity> sendDistrictMapPiceAreaEntitys
			);
	/**
	 * 
	* @Title: querySendDistrictMapByAreaCodeOrZoneName
    * @Description:根据分区名称 或者分区Code查询 派送货区行政区域映射基础资料 信息
	* sendDistrictMapVo.sendDistrictMap实体
	*zoneName分区名称
	*areaCode分区Code	
	* @author 189284--zhang xu 
	* @date 2014-10-30 上午10:59:35 
	* @param @param sendDistrictMap
	* @param @return
	* @return SendDistrictMapEntity    返回类型
	 */
	List<SendDistrictMapEntity> querySendDistrictMapBydistrictCodeOrdistrictName(
			SendDistrictMapEntity sendDistrictMap);
	/**
	 * 
	* @Title: deleteZoneName
	* @Description: 根据条件作废  派送货区行政区域映射基础资料
	* @author 189284--zhang xu 
	* @date 2014-11-3 下午6:18:25 
	* @param @return
	* @return String    返回类型
	 */
	void deleteByZoneName(SendDistrictMapEntity sendDistrictMap);
	/**
	 * 
	* @Title: deleteSendDistrictMapList
	* @Description: 作废多条  派送货区行政区域映射基础资料
	* @author 189284--zhang xu 
	* @date 2014-11-4 上午10:02:00 
	* @param @param sendDistrictMapList
	* @return void    返回类型
	 */
	void deleteSendDistrictMapList(List<SendDistrictMapEntity> sendDistrictMapList);
	/**
	 * 
	* @Title: deleteAddSendDistrictMapList
	* @Description: 作废和新增多条派送货区行政区域映射基础资料
	* @author WeiXing
	* @date 2014-11-4 上午10:02:00 
	* @param @param SendDistrictMapVo
	* @return void    返回类型
	 */
	void deleteAddSendDistrictMapList(SendDistrictMapVo sendDistrictMapVo);
	/**
	 * 
	* @Title: queryItemAreaByIdOrZoneCode 
	* @Description:根据zoneCode分区 查询件区信息
	* @param @param id 主键id
	* @param @param zoneCode分区Code
	* @param @return    设定文件 
	* @return List<SendDistrictItemAreaEntity>    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-3-19 下午3:17:52 
	* @throws
	 */
	List<SendDistrictItemAreaEntity> queryItemAreaByIdOrZoneCode(String id,
			String zoneCode);
	
}
