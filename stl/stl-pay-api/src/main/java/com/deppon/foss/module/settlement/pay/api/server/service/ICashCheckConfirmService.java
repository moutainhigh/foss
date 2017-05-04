package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

/**
 * @author 218392 张永雪
 * @date 2015-12-08 10:58:12
 * 现金盘点以及未收银确认查询Service
 */
public interface ICashCheckConfirmService {
	/**
	 * 查询未收银确认的单号: 根据部门编码查询该部门为收银确认的运单号
	 */
	List<String> queryCashUnconfirmCod(String deptCode);
	
	
}
