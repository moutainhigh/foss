package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonQueryVirtualSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonQueryVirtualSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonQueryVirtualSalesDeptDto;

/**
 * 根据条件查询虚拟营业部信息
 * 
 * @author WangPeng
 * @date   2013-08-07 10:57 AM
 *
 */
public class CommonQueryVirtualSalesDeptService implements
		ICommonQueryVirtualSalesDeptService {

	/**
	 * Dao层接口对象
	 */
	ICommonQueryVirtualSalesDeptDao  commonQueryVirtualSalesDeptDao;
	
	public void setCommonQueryVirtualSalesDeptDao(
			ICommonQueryVirtualSalesDeptDao commonQueryVirtualSalesDeptDao) {
		this.commonQueryVirtualSalesDeptDao = commonQueryVirtualSalesDeptDao;
	}

	/**
	 * 根据快递大区编码查询旗下的所有虚拟营业部
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-7 上午10:57:58
	 * @param 	expressBigRegionCodes
	 * @return  List<String>
	 * 
	 */
	@Override
	public List<String> queryExpressBigReginIncludeVirtualDeptCodeByExpressBigCode(
			List<String> expressBigRegionCodes) {
		if(null == expressBigRegionCodes){
			return null;
		}
		return commonQueryVirtualSalesDeptDao.queryExpressBigReginIncludeVirtualDeptCodeByExpressBigCode(expressBigRegionCodes);
	}

	/**
	  * 查询虚拟营业部的相关信息
	  * 
	  * @author  WangPeng
	  * @Date    2013-8-7 上午10:58:34
	  * @param 	 entity
	  * @return  List<CommonQueryVirtualSalesDeptDto>
	  *
	  */
	@Override
	public List<OrgAdministrativeInfoEntity> queryVirtualSalesDeptList(
			CommonQueryVirtualSalesDeptDto entity, int offset, int limit) {
		if(null == entity){
			throw new BusinessException("传入的对象为空！");
		}
//		//用来存放快递大区下的所有虚拟营业部
//		List<String> virtualSalesDeptCodes = null;
//		if(CollectionUtils.isNotEmpty(entity.getCodes())){
//			virtualSalesDeptCodes = commonQueryVirtualSalesDeptDao.queryExpressBigReginIncludeVirtualDeptCodeByExpressBigCode(entity.getCodes());
//		}
//		entity.setCodes(null);
//		entity.setCodes(virtualSalesDeptCodes);
		
		return commonQueryVirtualSalesDeptDao.queryVirtualSalesDeptList(entity, offset,limit);
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
			if(null == entity){
				throw new BusinessException("传入的对象为空！");
			}
			
			return commonQueryVirtualSalesDeptDao.queryVirtualSalesDeptList(entity);
		}

}
