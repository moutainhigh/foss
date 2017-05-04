package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IUnloadGoodsDetailNoDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.NoUnloadGoodsDetail;


/**
* @description 卸车进度里的未卸车明细
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月16日 下午2:54:33
*/
public class UnloadGoodsDetailNoDaoImpl extends iBatis3DaoImpl implements IUnloadGoodsDetailNoDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.noUnloadGoodsDetail.";
	
	/**
	* @description 未卸车明细
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午2:53:03
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<NoUnloadGoodsDetail> queryNoUnloadGoodsDetailList(
			Map<String, String> map,int start,int limit) {
		if(start>=0 && limit>=0){
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAME_SPACE + "queryNoUnloadGoodsDetailList", map, rowBounds);
		}else{
			return this.getSqlSession().selectList(NAME_SPACE + "queryNoUnloadGoodsDetailList", map);
		}
	}
	
	
	/**
	* @description 获取总重量和总体积
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:34:01
	*/
	@Override
	public NoUnloadGoodsDetail queryNoUnloadGoodsDetailListSum(
			Map<String, String> map) {
		return (NoUnloadGoodsDetail) this.getSqlSession().selectOne(NAME_SPACE + "queryNoUnloadGoodsDetailListSum",map);
	}




	/**
	* @description 未卸车明细的总记录
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月18日 下午5:29:51
	*/
	@Override
	public int queryNoUnloadGoodsDetailListCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAME_SPACE + "queryNoUnloadGoodsDetailListCount",map);
	}



	/**
	* @description 根据卸车部门 和运单号(集合) 查询对应的下一部门
	* @param unloadOrgCode
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午3:35:04
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<NoUnloadGoodsDetail> queryNextNameByOrgCode(
			String unloadOrgCode, List<String> waybillNo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", waybillNo);
		map.put("unloadOrgCode", unloadOrgCode);
		return this.getSqlSession().selectList(NAME_SPACE + "queryNextNameByOrgCode", map);
	}
	
	

}
