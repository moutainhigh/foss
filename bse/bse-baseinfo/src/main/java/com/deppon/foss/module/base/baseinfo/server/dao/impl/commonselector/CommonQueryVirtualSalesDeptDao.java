package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonQueryVirtualSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonQueryVirtualSalesDeptDto;

/**
 * 根据条件查询虚拟营业部信息
 * 
 * @author WangPeng
 * @date   2013-08-07 10:57 AM
 *
 */
public class CommonQueryVirtualSalesDeptDao  extends SqlSessionDaoSupport implements
		ICommonQueryVirtualSalesDeptDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonQueryVirtualSalesDept.";
	
	/**
	 * 根据快递大区编码查询旗下的所有虚拟营业部
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-7 上午10:57:58
	 * @param 	expressBigRegionCodes
	 * @return  List<String>
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryExpressBigReginIncludeVirtualDeptCodeByExpressBigCode(
			List<String> expressBigRegionCodes) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("org", expressBigRegionCodes);
		return this.getSqlSession().selectList(NAMESPACE + "queryVirtualDeptCodes", map);
	}

	/**
	 * 根据快递大区编码查询旗下的所有虚拟营业部
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-7 上午10:57:58
	 * @param 	entity
	 * @return  List<String>
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgAdministrativeInfoEntity> queryVirtualSalesDeptList(
			CommonQueryVirtualSalesDeptDto entity, int offset, int limit) {
		RowBounds rowBounds = new RowBounds(offset,limit);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("org", entity.getOrgAdministrativeInfoEntity());
		map.put("virtualDeptCodes", entity.getCodes());
		
		return this.getSqlSession().selectList(NAMESPACE + "queryVirtualSalesDeptList", map, rowBounds);
	}

	/**
	  * 统计总行数
	  * 
	  * @author  WangPeng
	  * @Date    2013-8-7 上午10:58:34
	  * @param 	 entity
	  * @return  Long
	  *
	  */
	@Override
	public Long queryVirtualSalesDeptList(CommonQueryVirtualSalesDeptDto entity) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("org", entity.getOrgAdministrativeInfoEntity());
		map.put("virtualDeptCodes", entity.getCodes());
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countRecords", map);
	}
}
