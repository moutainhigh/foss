package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IAutoCheckDpjzSignInDao;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;
/**
 * 
 * 查询需要自动更新的运单数据
 * @author 269701--lln
 * @date 2015-12-15
 * 
 *
 */
public class AutoCheckDpjzSignInDao extends iBatis3DaoImpl implements IAutoCheckDpjzSignInDao {
	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.dpjzSignInMsgMapper.";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DpjzSignInDetailDto> queryDpjzSignInMsgWaybill(Map<String,Object> map) {
		// 查询
		return this.getSqlSession().selectList(prefix + "queryDpjzSignInMsgWaybill",map);
	}

}
