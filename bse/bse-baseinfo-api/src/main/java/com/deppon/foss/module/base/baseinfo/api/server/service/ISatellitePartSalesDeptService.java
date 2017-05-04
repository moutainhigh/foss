package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SatellitePartSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 卫星点与营业部关系
 * @author 130566
 *
 */
public interface ISatellitePartSalesDeptService {
	/**
	 *<P>添加实体<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午1:47:12
	 * @param entity
	 * @return
	 */
	SatellitePartSalesDeptEntity addSatellitePartSales(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据id 作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午1:51:21
	 * @param entity
	 * @return
	 */
	int deleteSatellitePartSalesById(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据卫星点编码 和营业部编码作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-1下午2:38:31
	 * @param entity
	 * @return
	 */
	SatellitePartSalesDeptEntity  deleteSatelliteBySatelliteCodeAndSalesCode(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据卫星点编码作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-8下午3:38:31
	 * @param entity
	 * @return
	 */
	SatellitePartSalesDeptEntity deleteSatelliteBySatelliteCode(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据营业部编码作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-8下午3:38:31
	 * @param entity
	 * @return
	 */
	SatellitePartSalesDeptEntity deleteSatelliteBySalesCode(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据条件分页查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午1:53:32
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<SatellitePartSalesDeptEntity> querySatellitePartSalesList(SatellitePartSalesDeptEntity entity,int start,int limit);
	/**
	 *<P>根据条件查询总数<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午1:57:43
	 * @param entity
	 * @return
	 */
	long querySatellitePartSalesCount(SatellitePartSalesDeptEntity entity);
	/**
	 *<P>根据营业部编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午1:55:42
	 * @param entity
	 * @return
	 */
	List<SatellitePartSalesDeptEntity> querySatellitepartSalesBySalesCode(String salesDeptcode);
	/**
	 *<P>根据卫星点部编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-26下午1:56:38
	 * @param entity
	 * @return
	 */
	SatellitePartSalesDeptEntity querySatellitePartsalesBySatelliteCode(String satelliteDeptCode);
	/**
	 * 
	 *<P>根据id批量作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午6:08:31
	 * @param idList
	 * @param modifyUser
	 * @return
	 */
	int deleteSatellitePartSalesByIdList(List<String> idList,
			CurrentInfo modifyUser);
	/**
	 *<P>新增实体列表<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午7:09:26
	 * @param addDeptEntities
	 * @param currentInfo
	 */
	List<SatellitePartSalesDeptEntity> addSatellitePartSalesList(
			List<SatellitePartSalesDeptEntity> addDeptEntities,
			String modifyUser);
	/**
	 *<P>修改映射关系<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-1下午2:13:54
	 * @param vo
	 * @param modifyUser
	 * @return
	 */
	List<SatellitePartSalesDeptEntity> updateSatellitePartSalesList(SatellitePartSalesDeptVo vo,
			String modifyUser);
}
