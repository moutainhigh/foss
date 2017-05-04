package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

/**
 * @author 218392 张永雪
 * @date 2015-12-08 11:29:26
 * 现金盘点以及未收银确认查询Dao
 */
public interface ICashCheckConfirmDao {
	
	/**
	 * 查询未收银确认的代收货款的单据
	 */
	List<String> queryCashUnconfirmNo(String code);

}
