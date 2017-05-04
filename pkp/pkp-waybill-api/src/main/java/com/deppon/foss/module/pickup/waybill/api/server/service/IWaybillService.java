package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.pickup.waybill.shared.request.CubcQueryTotalAmountRequest;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListQueryVo;

/**
 * waybillService 接口
 * @author 272311
 *
 */
public interface IWaybillService extends IService {
	
	/**
	 * 根据请求参数 查询 发货清单 列表 的总记录数（无 货物状态 查询条件）
	 * @param deliverGoodsListQueryVo 请求参数
	 * @return 数据总记录数
	 */
	int queryWaybillDeliverGoodsListCount(DeliverGoodsListQueryVo deliverGoodsListQueryVo);
	
	/**
	 * 根据请求参数 查询 发货清单 列表<br>
	 * 以map的形式返回值：count：条数 ；resultList：结果集
	 * @param deliverGoodsListQueryVo 请求参数
	 * @return DeliverGoodsListVo 获取的货物信息-发货清单
	 * @author 272311
	 */
	 Map<String, Object> queryWaybillDeliverGoodsList(DeliverGoodsListQueryVo deliverGoodsListQueryVo,int start,int limit) ;
	
	 /**
	  * 供结算丢货弃货时调用，将运单号等信息同步至PTP
	  * @param waybillNo
	  */
	 void syncLostGoodsToPtp(String waybillNo,String status);
	 
	    /**
		 * 通过运单号判断是否为悟空快递单
		 * @param waybillNo 
		 * @return
		 * @author 272311-sangwenhao
		 */
		String queryIsECSByWayBillNo(String waybillNo) ;

		/**
		 * <p>根据代理网点编码查询代理网点信息</p> 
		 * @author 272311-sangwenhao 
		 * @date 2016-8-13 下午7:18:08
		 * @param agencyBranchCode
		 * @return
		 */
		OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);

		CustomerCircleNewDto queryCustomerByCusCode(
				String deliveryCustomerCode, Date billTime, String string);
		
		/**
		 * cubc，根据开单时间和客户编码查询（预付+到付-代收货款）总金额
		 * String
		 * @author 198771-zhangwei
		 * 2017-1-6 下午4:54:23
		 */
		String queryTotalAmount(CubcQueryTotalAmountRequest requestParam);	
}
