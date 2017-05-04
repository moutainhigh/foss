package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonBankAccountEntity;
/**
 * 公共查询选择器--（对公，对私）账号Service
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-11 上午8:49:42
 */
public interface ICommonBankAccountService {
	 
	/**
	 * 查询组织对公账号信息
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2013-08-22 下午2:35:52
	 * @return
	 */
    List<CommonBankAccountEntity> queryBankAccountByDto(CommonBankAccountEntity commonBankAccountEntity,int start, int limit);
	
    /**
	 * 查询组织对公账号信息 总数
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2013-08-22 下午2:35:52
	 * @return
	 */
    long countQueryBankAccountByDto(CommonBankAccountEntity commonBankAccountEntity);

}
