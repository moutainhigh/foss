package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCheckConfirmDao;
/**
 * @author 218392 张永雪
 * @date 2015-12-08 11:31:46
 * 现金盘点以及未收银确认查询Dao
 */
public class CashCheckConfirmDao extends iBatis3DaoImpl implements ICashCheckConfirmDao{
	/**
	 * 声明namespace
	 */
	public static final String NAMESPACES = "foss.stl.CashCheckConfirmDao.";

	/**
	 * 查询未收银确认的代收货款单据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryCashUnconfirmNo(String deptCode) {
		
		return getSqlSession().selectList(NAMESPACES + "queryUnconfirmedCodRelatedBill",deptCode);
	}

}
