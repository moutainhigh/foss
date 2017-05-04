package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo;

/**
 * 合伙人快递签收service
 * @author gpz
 * @date 2016年1月22日
 */
public interface IPtpExpressSignService extends IService {

	/**
	 * 提交录入的签收信息
	 * @author gpz
	 * @date 2016年1月23日
	 * @param ptpSignVo 快递签收vo
	 * @param signDetailList 签收明细列表
	 * @param arriveSheet 到达联实体
	 * @param signVo 签收出库Vo
	 * @param currentInfo 当前登录人
	 * @return String
	 */
	String writeOffAndSign(PtpSignVo ptpSignVo,List<SignDetailEntity> signDetailList,
			ArriveSheetEntity arriveSheet, SignVo signVo,CurrentInfo currentInfo);

    
}
