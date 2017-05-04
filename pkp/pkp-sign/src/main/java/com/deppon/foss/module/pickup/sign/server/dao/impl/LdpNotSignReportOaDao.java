package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ILdpNotSignReportOaDao;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LdpNotSignReportOaDto;

/**
 * 快递代理理外发XX天未签收自动上报OA丢货Dao实现类
 * 
 * @ClassName: LdpNotSignReportOaDao
 * @author 200664-yangjinheng
 * @date 2014年9月3日 上午10:53:07
 */
public class LdpNotSignReportOaDao extends iBatis3DaoImpl implements ILdpNotSignReportOaDao {

	/**
	 *快递代理外发XX天未签收自动上报OA丢货命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.express.server.job.LdpNotSignReportOaJob.";

	/**
	 * 根据超过上报时间 和 上线的历史数据开始时间 查询符合条件的运单
	 * 
	 * @Title: queryCountbyCondition
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午11:05:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<LdpNotSignReportOaDto> querybyRegisterTimeCondition(Map<String, String> paramsMap) {

		
		//RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, 500);
		return this.getSqlSession().selectList(NAMESPACE + "querybyRegisterTimeCondition", paramsMap);
	}

}
