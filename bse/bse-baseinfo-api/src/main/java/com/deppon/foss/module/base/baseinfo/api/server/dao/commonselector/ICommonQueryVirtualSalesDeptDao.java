package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonQueryVirtualSalesDeptDto;

/**
 * 根据条件查询虚拟营业部信息
 * 
 * @author WangPeng
 * @date   2013-08-07 10:57 AM
 *
 */
public interface ICommonQueryVirtualSalesDeptDao {
	
	/**
	 * 根据快递大区编码查询旗下的所有虚拟营业部
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-7 上午10:57:58
	 * @param 	expressBigRegionCodes
	 * @return  List<String>
	 * 
	 */
	 List<String> queryExpressBigReginIncludeVirtualDeptCodeByExpressBigCode(List<String> expressBigRegionCodes);
	 
	 /**
	  * 查询虚拟营业部的相关信息
	  * 
	  * @author  WangPeng
	  * @Date    2013-8-7 上午10:58:34
	  * @param 	 entity
	  * @return  List<CommonQueryVirtualSalesDeptDto>
	  *
	  */
	 List<OrgAdministrativeInfoEntity> queryVirtualSalesDeptList(CommonQueryVirtualSalesDeptDto entity, int offset, int limit);
	 
	 
	 /**
	  * 统计总行数
	  * 
	  * @author  WangPeng
	  * @Date    2013-8-7 上午10:58:34
	  * @param 	 entity
	  * @return  Long
	  *
	  */
	 Long queryVirtualSalesDeptList(CommonQueryVirtualSalesDeptDto entity);

}
