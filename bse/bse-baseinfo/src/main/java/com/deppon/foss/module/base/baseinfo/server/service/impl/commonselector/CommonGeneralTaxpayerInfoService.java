package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;


import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonGeneralTaxpayerInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGeneralTaxpayerInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
import com.deppon.foss.util.CollectionUtils;

/**
 * 
 * 一般纳税人信息service实现 公共选择器
 * @author 308861 
 * @date 2016-2-28 下午2:39:10
 * @since
 * @version
 */
public class CommonGeneralTaxpayerInfoService implements ICommonGeneralTaxpayerInfoService{
	
	/**
	 * 注入ICommonGeneralTaxpayerInfoDao
	 */
	private ICommonGeneralTaxpayerInfoDao commonTaxpayerInfoDao;
	
	public void setCommonTaxpayerInfoDao(
			ICommonGeneralTaxpayerInfoDao commonTaxpayerInfoDao) {
		this.commonTaxpayerInfoDao = commonTaxpayerInfoDao;
	}
	
	/**
	 * 
	 * 查询一般纳税人信息 
	 * @author 308861 
	 * @date 2016-2-29 上午10:11:27
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGeneralTaxpayerInfoService#queryTaxpayerInfoList(com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity, int, int)
	 */
	@Override
	public List<GeneralTaxpayerInfoEntity> queryTaxpayerInfoList(
			GeneralTaxpayerInfoEntity entity, int start, int limit) {
		List<GeneralTaxpayerInfoEntity> list=commonTaxpayerInfoDao.
				queryTaxpayerInfoList(entity, start, limit);
		//bankNumber空值封装
		if(CollectionUtils.isNotEmpty(list)){
			for(GeneralTaxpayerInfoEntity resultEntity:list){
				if(StringUtil.isEmpty(resultEntity.getBankNumber())){
					resultEntity.setBankNumber(" ");
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * 统计一般纳税人信息总数 用于分页 
	 * @author 308861 
	 * @date 2016-2-29 上午10:11:41
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGeneralTaxpayerInfoService#queryGeneralTaxpayerInfoCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity)
	 */
	@Override
	public long queryGeneralTaxpayerInfoCount(GeneralTaxpayerInfoEntity entity) {
		return commonTaxpayerInfoDao.queryGeneralTaxpayerInfoCount(entity);
	}

	
}
