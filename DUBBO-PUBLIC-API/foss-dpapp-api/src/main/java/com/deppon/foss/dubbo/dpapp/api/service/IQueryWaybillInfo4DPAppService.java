package com.deppon.foss.dubbo.dpapp.api.service;

import com.deppon.foss.dubbo.dpapp.api.define.QueryAppWaybillInfoEntity;
import com.deppon.foss.dubbo.dpapp.api.define.QueryAppWaybillInfoResponseEntity;

public interface IQueryWaybillInfo4DPAppService {

	/**
	 * 根据请求参数查询 运单信息(包括子母件)-APP子母件发货记录查询
	 * 
	 * @param entity
	 *            请求参数
	 * @return 查询到的结果集
	 * @author 335284
	 */
	QueryAppWaybillInfoResponseEntity queryAppWaybillRelateDetailInfo(QueryAppWaybillInfoEntity entity);
}
