package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 坏账核销服务接口类
 * 
 * @author foss-wangxuemin
 * @date Dec 4, 2012 2:35:02 PM
 */
public interface IBillBadAccountService extends IService {

	/**
	 * 查询待坏账处理的应收单
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:54:55 PM
	 */
	List<BillReceivableEntity> queryBadAccountReceiableList(String customerCode);

	/**
	 * 生成坏账记录
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 5:52:49 PM
	 */
	void add(BillBadAccountEntity entity);
	
	/**
	 * 根据运单号查询是否存在坏账信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-24 上午11:52:34
	 * @param waybillNo
	 * @return 大于0：存在坏账
	 */
	int queryByWaybillNO(String waybillNo);
	
	/**
	 * 根据运单号集合查询坏账信息
	 * @author 198704 weitao
	 * @date Oct 4, 2014 4:20 pm
	 * @param waybillNos
	 * @return List<BillBadAccountEntity>
	 */
	List<BillBadAccountEntity> queryByWaybillNOs(List<String> waybillNos);
}
