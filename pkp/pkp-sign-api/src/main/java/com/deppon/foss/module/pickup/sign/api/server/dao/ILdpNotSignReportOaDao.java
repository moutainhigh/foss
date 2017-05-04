package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.sign.api.shared.dto.LdpNotSignReportOaDto;

/**
 * 快递代理理外发XX天未签收自动上报OA丢货Dao接口
 * 
 * @ClassName: ILdpNotSignReportOADao
 * @author 200664-yangjinheng
 * @date 2014年9月3日 上午10:52:02
 */
public interface ILdpNotSignReportOaDao {

	/**
	 * 根据超过上报时间 和 上线的历史数据开始时间 查询符合条件的运单
	 * 
	 * @Title: queryCountbyCondition
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午11:05:00
	 * @throws
	 */
	List<LdpNotSignReportOaDto> querybyRegisterTimeCondition(Map<String, String> paramsMap);

}
