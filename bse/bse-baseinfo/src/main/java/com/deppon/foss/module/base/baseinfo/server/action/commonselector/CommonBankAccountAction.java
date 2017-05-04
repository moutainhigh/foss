package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonBankAccountVo;

public class CommonBankAccountAction extends AbstractAction implements
IQueryAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 账户Service
	 */
	private ICommonBankAccountService commonBankAccountService;

	/**
	 * 账户Vo
	 */
	private CommonBankAccountVo commonBankAccountVo = new CommonBankAccountVo();

	/**
	 * 查询对公银行账户.
	 * 
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 下午3:25:42
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		Long totalCount=commonBankAccountService.countQueryBankAccountByDto(commonBankAccountVo.getCommonBankAccountEntity());
		if(totalCount>0){
			List<CommonBankAccountEntity> pBankAccountEntityList = commonBankAccountService.queryBankAccountByDto(commonBankAccountVo.getCommonBankAccountEntity(), start, limit);
			commonBankAccountVo.setCommonBankAccountEntityList(pBankAccountEntityList);
		}
		setTotalCount(totalCount);
		
		return returnSuccess();
	}

	public CommonBankAccountVo getCommonBankAccountVo() {
		return commonBankAccountVo;
	}

	public void setCommonBankAccountVo(CommonBankAccountVo commonBankAccountVo) {
		this.commonBankAccountVo = commonBankAccountVo;
	}

	public void setCommonBankAccountService(
			ICommonBankAccountService commonBankAccountService) {
		this.commonBankAccountService = commonBankAccountService;
	}

}
