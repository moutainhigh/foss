package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;

/**
 * 用于 快递签收状态详细信息 
 * @author 272311
 *
 */
public interface IWaybillSignService extends IService {

	/**
	 * 根据当前登陆 客户编码 的客户以及 开始时间和结束时间  获取 快递签收状态 的详细信息
	 * @param waybillSignDetailQueryVo
	 * @param start 起始页码
	 * @param limit 每页条数
	 * @param flag 是否分页，true:分页；false:不进行分页
	 * @return 
	 */
	List<WaybillSignDetailVo> queryWayBillSignDetail(WaybillSignDetailQueryVo waybillSignDetailQueryVo,int start,int limit,boolean flag) ;
	/**
	 * 获取 快递签收状态 的详细信息 的总数量
	 * @param waybillSignDetailQueryVo
	 * @return
	 * @author 272311
	 */
	int countQueryWayBillSignDetail(WaybillSignDetailQueryVo waybillSignDetailQueryVo) ;
	/**
	 * 根据运单号获取作废的票数
	 * @param waybillNumList 运单号列表
	 * @return 作废的票数
	 * @author 272311
	 */
	int queryWaybillInvalid(List<String> waybillNumList);
	/**
	 *  根据运单号获取退回的票数
	 * @param waybillNumList 运单号列表
	 * @return 退回的票数
	 * @author 272311
	 */
	int queryWaybillBack(List<String> waybillNumList);  

}
