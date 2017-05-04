package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.NoUnloadGoodsDetail;


/**
* @description 卸车进度里的未卸车明细
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月16日 下午2:52:41
*/
public interface IUnloadGoodsDetailNoDao {
	
	/**
	* @description 未卸车明细
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午2:53:03
	*/
	List<NoUnloadGoodsDetail> queryNoUnloadGoodsDetailList(Map<String,String> map,int start,int limit);
	
	
	/**
	* @description 获取总重量和总体积
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:34:01
	*/
	NoUnloadGoodsDetail queryNoUnloadGoodsDetailListSum(Map<String,String> map);
	/**
	* @description 未卸车明细的总记录
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:29:51
	*/
	int queryNoUnloadGoodsDetailListCount(Map<String,String> map);
	
	
	/**
	* @description 根据卸车部门 和运单号(集合) 查询对应的下一部门
	* @param unloadOrgCode
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午3:35:04
	*/
	List<NoUnloadGoodsDetail> queryNextNameByOrgCode(String unloadOrgCode,List<String> waybillNo);
	
}
