package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictItemAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;

/**
 * TODO(描述类的职责)
 * @author WeiXing
 * @date 2014-10-21 下午1:08:17
 * @since
 * @version
 */
public interface ISendDistrictMapDao {

	/** 
	 * <p> 根据 外场code 分区code 状态 查询 分区（唯一）信息 分页 </p> 
	 * @author WeiXing
	 * @date 2014-10-21 下午3:42:14
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 */
	List<SendDistrictMapEntity> querySendDistrictMap(SendDistrictMapEntity entity, int limit, int start);
	/** 
	 * <p> 根据 外场code 分区code 状态 查询 分区（唯一）信息 </p> 
	 * @author WeiXing
	 * @date 2014-10-21 下午3:42:14
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 */
	List<SendDistrictMapEntity> querySendDistrictMap(SendDistrictMapEntity entity);
	
	/**
     * 
     * 统计 分页查询
     */
	Long queryRecordCount(SendDistrictMapEntity entity);
	
	/**
	 *<P>根据条件分页查询实体<P>
	 * @author :WeiXing
	 * @date : 2014-10-29下午3:40:30
	 * @param areaCode
	 * @param orgCode
	 * @return
	 */
	List<SendDistrictMapEntity> querySendDistrictMapList(SendDistrictMapEntity entity, int start, int limit);
	
	/**
	 *<P>查询总数<P>
	 * @author :WeiXing
	 * @date : 2014-10-29上午11:05:17
	 * @param entity
	 * @return
	 */
	long querySendDistrictMapCount(SendDistrictMapEntity entity);
	
	/**
	 *<P>根据条件分页查询实体<P>
	 *根据transfer_center_code,goods_area_code,zone_code进行分组查询
	 * @author :WeiXing
	 * @date : 2014-10-29下午3:40:30
	 * @param areaCode
	 * @param orgCode
	 * @return
	 */
	List<SendDistrictMapEntity> querySendDistrictMapGroupList(SendDistrictMapEntity entity, int limit, int start);
	
	/**
	 *<P>查询总数<P>
	 *根据transfer_center_code,goods_area_code,zone_code进行分组查询
	 * @author :WeiXing
	 * @date : 2014-10-29上午11:05:17
	 * @param entity
	 * @return
	 */
	long querySendDistrictMapGroupCount(SendDistrictMapEntity entity);

	/**
	 *<P>根据SendDistrictMapEntity查询<P>
	 * @author :WeiXing
	 * @date : 2014-10-29上午11:11:29
	 * @param entity
	 * @return
	 */
	List<SendDistrictMapEntity> querySendDistrictMapListbyEntity(SendDistrictMapEntity entity);
	
	/**
	 *<P>根据ID批量删除<P>
	 * @author :WeiXing
	 * @date : 2014-11-04上午11:11:29
	 * @param List
	 * @return
	 */
	long deleteSendDistrictMapByIds(List<String> ids,String deleteUser);
	
	/**
	 * 
	* @Title: addSendDistrictMap
	* @Description:新增 派送货区行政区域映射基础资料)
	* @author 189284--zhang xu 
	* @date 2014-10-30 下午8:38:01 
	* @param @param sendDistrictMap
	* @param @param sendDistrictMapList
	* @param @return
	* @return Integer    返回类型
	 */
	Integer addSendDistrictMap(SendDistrictMapEntity sendDistrictMap,
			List<SendDistrictMapEntity> sendDistrictMapList);
	/**
	* @Title: updateSendDistrictMap
	* @Description: TODO(修改 派送货区行政区域映射基础资料) 
	* @author 189284--张许   
	* @date 2014-10-22 下午5:15:18 
	* @param @param sendDistrictMap
	* @return void    返回类型
	 */
	void updateSendDistrictMap(SendDistrictMapEntity sendDistrictMap);
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
			SendDistrictMapEntity sendDistrictMap) ;
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
   * @Title: addSendDistrictItemArea 
   * @Description: 批量新增件区信息  
   * @param @param sendDistrictMap
   * @param @param sendDistrictItemAreaList
   * @param @return    设定文件 
   * @return Integer    返回类型 
   * @author 189284 ZhangXu
   * @date 2015-3-18 上午11:33:34 
   * @throws
    */
   Integer addSendDistrictItemArea(SendDistrictMapEntity sendDistrictMap,
		List<SendDistrictItemAreaEntity> sendDistrictItemAreaList);	
   
   Integer addSendDistrictItemArea(
		SendDistrictItemAreaEntity sendDistrictItemAreaList);
   /**
    * 
   * @Title: queryItemAreaByIdOrZoneCode 
   * @Description: 根据zoneCode分区 查询件区信息
   * @param @param id 主键ID
   * @param @param zoneCode分区Code
   * @param @return    设定文件 
   * @return List<SendDistrictItemAreaEntity>    返回类型 
   * @author 189284 ZhangXu
   * @date 2015-3-19 下午3:21:04 
   * @throws
    */
List<SendDistrictItemAreaEntity> queryItemAreaByIdOrZoneCode(String id,
		String zoneCode);
/**
 * 
* @Title: updateItemAreaById 
* @Description:根据ID or zoneCode修改 件区信息（作废）
* @param @param sendDistrictItemAreaEntity
* @param @return    设定文件 
* @return Integer    返回类型 
* @author 189284 ZhangXu
* @date 2015-3-21 上午11:41:20 
* @throws
 */
Integer updateItemAreaByIdOrZoneCode(SendDistrictItemAreaEntity sendDistrictItemAreaEntity);
/**
 * 
* @Title: deleteSendDistrictItemAreaByIds 
* @Description:批量作废 件区信息
* @param @param delteItemAreas 作废件区 Ids
* @param @return    设定文件 
* @return long    返回类型 
* @author 189284 ZhangXu
* @date 2015-3-21 下午2:05:50 
* @throws
 */
long deleteSendDistrictItemAreaByIds(List<String> delteItemAreas);	
	
/**
 * 
* @Title: queryItemAreaByDistricCode 
* @Description: 根据行政区域编码查询件区信息
* @param  queryEntity
* @return List<SendDistrictItemAreaEntity>
* @author 187862-dujunhui
* @date 2015-7-17 下午2:11:35 
 */
List<SendDistrictItemAreaEntity> queryItemAreaByDistricCode(SendDistrictMapEntity queryEntity);

}
