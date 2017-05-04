package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.DopBillEntity;

/**
 * 家装应收单和应付单服务
 * 
 * @ClassName: IDopPayableAndReceiveService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-30 上午10:17:06
 * 
 */
public interface IDopBillPayableReceivableService extends IService {

	/**
	 * 新增应收单应付单
	 * 
	 * @Title: addDopPayAndRec
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int addDopPayAndRec(List<DopBillEntity> entitys);

	/**
	 * 作废家装应收单应付单
	 * 
	 * @Title: cacleDopPayAndRec
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public int cacleDopPayAndRec(List<DopBillEntity> entitys);

}
