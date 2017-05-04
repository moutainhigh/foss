/**
 * 
 */
package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto;

/**
 * @author 045738
 *
 */
public interface IWithholdingEntityService extends IService {
	/**
	 * 功能：根据传入的单号，类型，和预提状态来查询。
	 * 		单号、类型为必填项，预提状态为非必填
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public List<WithholdingEntity> selectByNosAndType(List<String> billNos,
			String billType, List<String> status,String active);
	
	/**
	 * 功能：新增预提记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-23
	 * @return
	 */
	public void add(WithholdingEntity entity, CurrentInfo currentInfo);
	
	/**
	 * <p>反写预提状态</p> 
	 * @author 105762
	 * @date 2014-7-15 下午2:57:10
	 * @param dto
	 * @return 
	 * @see
	 */
	boolean rewriteWithholdingState(WithholdingDto dto);
}
