/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;

public interface IRegionValueAddDao {
	/**
	 * 
	 * @Description: 根据条件查询区域信息(分页）
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:25:57
	 * @param regionAirEntity
	 * @param start
	 * @param limit
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionValueAddEntity> searchRegionByCondition(PriceRegionValueAddEntity regionAirEntity, int start, int limit);

	/**
	 * 
	 * @Description: 根据条件查询区域信息个数
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:29:01
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	Long countRegionByCondition(PriceRegionValueAddEntity regionAirEntity);

	/**
	 * 
	 * @Description: 新增空运区域
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:29:55
	 * @param regionEntity
	 * @version V1.0
	 */
	void addRegion(PriceRegionValueAddEntity regionAirEntity);

	/**
	 * 
	 * @Description: 修改空运区域
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:30:58
	 * @param regionEntity
	 * @version V1.0
	 */
	void updateRegion(PriceRegionValueAddEntity regionAirEntity);

	/**
	 * 
	 * @Description: 新增空运区域与部门的关联
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:31:49
	 * @param priceRegioOrgnEntity
	 * @version V1.0
	 */
	void addRegionOrg(PriceRegionOrgValueAddEntity priceRegioOrgAirEntity);

	/**
	 * 
	 * @Description: 修改区域与部门的关联
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:33:31
	 * @param priceRegioOrgnEntity
	 * @version V1.0
	 */
	void updateRegionOrg(PriceRegionOrgValueAddEntity priceRegioOrgAirEntity);

	/**
	 * 
	 * @Description: 根据条件查询区域关联部门信息
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:34:46
	 * @param priceRegioOrgnEntity
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionOrgValueAddEntity> searchRegionOrgByCondition(PriceRegionOrgValueAddEntity priceRegioOrgAirEntity);

	/**
	 * 
	 * @Description: 激活空运区域
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:35:27
	 * @param regionIds
	 * @param regionNature
	 * @version V1.0
	 */
	void activeRegion(List<String> regionIds, String regionNature);


	 /**
	  * 
	  * @Description: 查询行政组织区域存在项
	  * @author FOSSDP-sz
	  * @date 2013-3-12 上午11:21:45
	  * @param administrativeRegionCodes
	  * @param regionNature
	  * @return
	  * @version V1.0
	  */
	List<PriceRegionValueAddEntity> checkRegionOrganizationType(String administrativeRegionCodes, String regionNature, Date now);
	
	 /**
	  * 
	  * @Description: 根据区域名称区域信息
	  * @author FOSSDP-sz
	  * @date 2013-3-12 上午11:16:57
	  * @param regionName
	  * @param regionNature
	  * @return
	  * @version V1.0
	  */
	List<PriceRegionValueAddEntity> searchRegionByName(String regionName, String regionNature);

	 /**
	  * 
	  * @Description: 根据条件查询区域信息
	  * @author FOSSDP-sz
	  * @date 2013-3-12 上午11:08:30
	  * @param regionEntity
	  * @return
	  * @version V1.0
	  */
	List<PriceRegionValueAddEntity> findRegionByCondition(PriceRegionValueAddEntity regionEntity);

	/**
	 * 
	 * @Description: 根据行政区域查逻辑区域
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:36:49
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionValueAddEntity> searchRegionByDistrict(PriceRegionValueAddEntity priceRegionAirEntity);
	
	/**
	 * 
	 * @Description: 根据行政区域查询逻辑区域,空运价格区域查询专用
	 * @author FOSSDP-sz
	 * @date 2013-6-5 上午10:36:49
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionValueAddEntity> searchRegionByDistrictNew(PriceRegionValueAddEntity priceRegionAirEntity);

	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:37:32
	 * @param id
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	PriceRegionValueAddEntity searchRegionByID(String id, String regionNature);

	/**
	 * 
	 * @Description: 激活区域下关联的区域部门
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:38:05
	 * @param regionId
	 * @param regionNature
	 * @version V1.0
	 */
	void activeRegionOrg(String regionId, String regionNature);

	/**
	 * 
	 * @Description: 删除区域
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:38:19
	 * @param regionIds
	 * @param regionNature
	 * @version V1.0
	 */
	void deleteRegion(List<String> regionIds, String regionNature);

	/**
	 * 
	 * @Description: 删除区域下关联部门
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:38:30
	 * @param regionIds
	 * @param regionNature
	 * @version V1.0
	 */
	void deleteRegionOrg(List<String> regionIds, String regionNature);

	 /**
	  * 
	  * @Description: 根据条件查询价格区域信息
	  * @author FOSSDP-sz
	  * @date 2013-3-12 上午11:18:20
	  * @param priceRegionEntity
	  * @return
	  * @version V1.0
	  */
	List<PriceRegionValueAddEntity> selectPriceRegionByCondition(PriceRegionValueAddEntity priceRegionAirEntity);
	/**
	 * 
	 * @Description: 根据条件查询价格区域部门信息
	 * @author FOSSDP-Administrator
	 * @date 2013-3-12 上午10:39:05
	 * @param priceRegioOrgnEntity
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionOrgValueAddEntity> selectPriceRegionOrgByCondition(PriceRegionOrgValueAddEntity priceRegioOrgAirEntity);

	/**
	 * 
	 * @Description: 根据区域名称批量查找区域信息
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:40:50
	 * @param names
	 * @param regionNature
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	Map<String, PriceRegionValueAddEntity> findRegionByNames(List<String> names, String regionNature, Date billDate);

	/**
	 * 
	 * @Description: 根据区域CODE 批量查找区域信息
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:41:06
	 * @param codes
	 * @param regionNature
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionValueAddEntity> findRegionByCodes(List<String> codes, String regionNature, Date billDate);

	/**
	 * 
	 * @Description: 根据District CODE查询区域信息
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:41:06
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionValueAddEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode, String countyCode,
			String regionNature, Date billDate);

	/**
	 * 
	 * @Description: 根据网点CODE查询价格区域关联部门
	 * @author FOSSDP-sz
	 * @date 2013-3-12 上午10:41:45
	 * @param deptNo
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionOrgValueAddEntity> searchRegionOrgByDeptNo(String deptNo, String regionNature);
	/**
	 * 
	 * @Description: 根据区域ID查询价格区域关联部门 
	 * @author FOSSDP-sz
	 * @date 2013-4-22 下午2:17:04
	 * @param deptRegionId
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionOrgValueAddEntity> searchRegionOrgByRegionId(String deptRegionId);
	
	/**
	 * 
	 * 获得编号序列
	 * @author 043258-foss-zhaobin
	 * @date 2013-8-15 上午8:19:13
	 */
	String querySequence();
}