package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonBankAccountEntity;
/**
 * 公共选择器--账户查询(包括对公，对私)dao接口
 * 
 * @author 130346-foss-lifanghong
 * @date 2013-08-22 下午2:35:52
 */
public interface ICommonBankAccountDao {

	/**
	 * 查询组织对公账号信息
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2013-08-22 下午2:35:52
	 * @return
	 */
	List<CommonBankAccountEntity> queryBankAccountByDto(
			CommonBankAccountEntity commonBankAccountEntity, int start, int limit);

	/**
	 * 查询组织对公账号信息 总数
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2013-08-22 下午2:35:52
	 * @return
	 */
	Long queryRecordCount(CommonBankAccountEntity commonBankAccountEntity);
}
