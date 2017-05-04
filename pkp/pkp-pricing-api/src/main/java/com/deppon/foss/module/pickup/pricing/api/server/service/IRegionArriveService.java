/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;

public interface IRegionArriveService extends IService {
	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:42:42
	 * @param regionEntity
	 * @param start
	 * @param limit
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionArriveEntity> searchRegionByCondition(PriceRegionArriveEntity priceRegionArriveEntity, int start, int limit);

	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:43:02
	 * @param id
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	PriceRegionArriveEntity searchRegionByID(String id, String regionNature);

	/**
	 * 
	 * @Description: 根据条件查询区域信息个数
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:44:08
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	Long countRegionByCondition(PriceRegionArriveEntity regionEntity);

	/**
	 * 
	 * @Description: 新增区域
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:44:26
	 * @param regionEntity
	 * @param addPriceRegioOrgnEntityList
	 * @version V1.0
	 */
	void addRegion(PriceRegionArriveEntity regionEntity, List<PriceRegionOrgArriveEntity> addPriceRegioOrgArriveEntityList);

	/**
	 * 
	 * @Description: searchRegionOrgByCondition
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:46:04
	 * @param priceRegioOrgArriveEntity
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionOrgArriveEntity> searchRegionOrgByCondition(PriceRegionOrgArriveEntity priceRegioOrgArriveEntity);

	/**
	 * 
	 * @Description: 激活区域
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:46:16
	 * @param regionIds
	 * @param regionNature
	 * @param date
	 * @version V1.0
	 */
	void activeRegion(List<String> regionIds, String regionNature, Date date);

	/**
	 * 
	 * @Description: 删除区域
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:46:31
	 * @param regionIds
	 * @param regionNature
	 * @version V1.0
	 */
	void deleteRegion(List<String> regionIds, String regionNature);

	/**
	 * 
	 * @Description: 修改区域相关信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:47:17
	 * @param regionEntity
	 * @param addPriceRegioOrgArriveEntityList
	 * @param updatePriceRegioOrgnEntityList
	 * @version V1.0
	 */
	void updateRegion(PriceRegionArriveEntity regionEntity, List<PriceRegionOrgArriveEntity> addPriceRegioOrgArriveEntityList,
			List<PriceRegionOrgArriveEntity> updatePriceRegioOrgnEntityList);

	/**
	 * 
	 * @Description: 
	 * 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:47:32
	 * @param deptNo
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	String findRegionOrgByDeptNo(String deptNo, Date billDate, String productCode, String regionNature);
	
	
	/**
	 * 
	 * <p>
	 * Description:寻找价格到达区域ID 查询规则：根据获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * </p>
	 * <br />
	 * </p>
	 * 
	 * @author zhangwei
	 * @version 0.1 2012-10-25
	 * @param countyCode 区县
	 * @param cityCode 城市
	 * @param provCode 省
	 * @param billDate
	 *            开单日期
	 * @param  productCode      
	 *            产品code     
	 * @param regionNature
	 *            区域性质（时效或价格），根据区域性质的不同查询时效或价格的区域表
	 * @return String
	 */
	String findRegionOrgByCode(String countyCode,String cityCode,String provCode, Date billDate, String productCode, String regionNature);
	
	/**
	 * 
	 * @Description: 根据行政区域Code查询区域ID
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:47:54
	 * @param code
	 * @param billDate
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	String findRegionIdByDistrictCode(String code, Date billDate, String regionNature);

	/**
	 * 
	 * @Description: 根据条件查询区域信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:48:05
	 * @param regionEntity
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionArriveEntity> findRegionByCondition(PriceRegionArriveEntity regionEntity);

	/**
	 * 
	 * @Description: 根据时效区域名称查询时效区域
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:48:50
	 * @param regionName
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<PriceRegionArriveEntity> searchRegionByName(String regionName, String regionNature);

	/**
	 * 
	 * @Description: 根据区域名称批量查找区域信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:49:09
	 * @param names
	 * @param regionNature
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	Map<String, PriceRegionArriveEntity> findRegionByNames(List<String> names, String regionNature, Date billDate);

	/**
	 * 
	 * @Description: 刷新价格区域缓存
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:49:58
	 * @param provinceCode
	 * @param cityCode
	 * @param countyCode
	 * @version V1.0
	 */
	void refreshPriceRegionArriveCache(String provinceCode, String cityCode, String countyCode);

	/**
	 * 
	 * @Description: 刷新价格区域与组织缓存
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-12 上午10:50:04
	 * @param deptNo
	 * @version V1.0
	 */
	void refreshPriceRegionOrgArriveCache(String deptNo);
	/**
	 * 
	 * @Description: 立即中止
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-3-21 下午2:09:39
	 * 
	 * @param regionId
	 * 
	 * @param regionNature
	 * 
	 * @param date
	 * 
	 * @version V1.0
	 */
	void immedietelyStopRegionArrive(String regionId, String regionNature, Date endTime);
	/**
	 * 
	 * @Description: 
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-3-25 上午10:56:07
	 * 
	 * @param regionId
	 * 
	 * @param regionNature
	 * 
	 * @param endTime
	 * 
	 * @version V1.0
	 * 
	 */
	void immedietelyActiveRegionArrive(String regionId, String regionNature, Date beginTime);
	/**
	 * 
	 * @Description: 根据区域ID获取其下所有的营业部
	 * @author 043258-foss-zhaobin
	 * @date 2013-4-22 下午2:16:14
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	List<String> searchRegionOrgCodeByRegionId(String deptRegionId);
	
	/**
	 * 
	 * 获得编号序列
	 * @author 043258-foss-zhaobin
	 * @date 2013-8-15 上午8:19:13
	 */
	String querySequence();
	/**
	 * 到达区域关联部门
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 */
	List<String> findBGRegionOrgByDeptNo(String orgCode, Date billDate,
			String productCode, String regionNature);

	PriceRegionBigGoodsArriveEntity searchRegionByID(String id);

	String findEcGoodsRegionOrgByDeptNo(String destinationOrgCode, Date receiveDate, String productCode, String arriveRegion);

	PriceRegionEcGoodsArriveEntity searchRegionEcGoodsByID(String destinationId);
}