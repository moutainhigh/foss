package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.domain.AirTransportRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.CourierWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ExpressAirportEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKDepartCodeEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResStdEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseAndPageEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TruckTaskInfoResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.AirExpressDateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ExpressDateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.RepAirTransportDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskInfoDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskInfoResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.vo.ResultBean;

public interface IFOSSToWkService extends IService{
	
	/**
	* @description 同步车辆任务到快递系统(wk系统)
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月25日 下午3:40:03
	*/
	TruckTaskInfoResponseDto pushTruckTaskToWk(TruckTaskInfoDto truckTaskInfo);
	
	
	/**
	* @description 更新车辆任务到快递系统(wk系统)
	* @param truckTaskInfo
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月30日 上午11:52:46
	*/
	TruckTaskInfoResponseDto updateTruckTaskToWk(TruckTaskInfoDto truckTaskInfo);
	
	
	

	/**
	 * @description 根据卸车任务编号获取快递卸车明细
	 * @param requestParameter
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 上午10:15:23
	 */
	FossToWKResponseEntity queryUnloadExpressTaskDetail(String requestParameter) throws Exception;
	
	/**
	 * @description 根据卸车运单明细编号到悟空系统查询卸车扫描明细
	 * @param unloadWaybillDetailId
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 上午8:59:07
	 */
	FossToWKResponseEntity  queryExpressUnloadSerialNo(String requestParameter) throws Exception;
	
	/**
	 * @description FOSS同步新建卸车任务到悟空系统
	 * @param requestParameter
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 上午8:59:26
	 */
	FossToWKResponseEntity  syncNewExpressUnloadTaskToWk(String requestParameter) throws Exception;
	
	/**
	 * @description FOSS同步修改卸车任务到悟空系统
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午11:28:49
	 */
	FossToWKResponseEntity  syncupdateExpressUnloadTask(String requestParameter) throws Exception;

	/**
	 * @description FOSS同步取消卸车任务到悟空系统
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午11:28:49
	 */
	FossToWKResponseEntity  syncCancelUnloadTaskToWk(String requestParameter) throws Exception;

	/**
	 * @description Foss同步取消分配卸车任务到悟空
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:15:10
	 */
	FossToWKResponseEntity  syncCancelAssignUnloadTaskToWk(String requestParameter) throws Exception;
    
	/**
	 * @description Foss同步分配卸车任务到悟空
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:36:25
	 */
	FossToWKResponseEntity  syncAssignUnloadTaskToWk(String requestParameter) throws Exception;

	/**
	 * @description 通过卸车任务编号从悟空系统 查询单据明细
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午9:20:00
	 */
	FossToWKResponseEntity  queryUnloadWaybillDetailFromWk(String requestParameter) throws Exception;

	/**
	 * @description 根据交接单编号从WK系统查询笼号，包号，运单号
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午10:33:42
	 */
	FossToWKResponseEntity  expressQueryWaybillListByHandOverBillNo(String requestParameter) throws Exception;
    
	/**
	 * @description FOSS同步确认卸车任务给悟空
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月10日 上午11:28:02
	 */
	FossToWKResponseEntity syncConfirmExpressUnloadTaskToWk(String requestParameter) throws Exception;
	
	
	
	/**
	* @description 调用悟空接口查询悟空装车任务
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月23日 下午2:46:42
	*/
	Object QueryWKLoadTaskDetail(String requestParameter) throws Exception;
	

	
	/**
	* @description 根据航空正单查询运单信息
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 268220-chenmin
	* @update 2016-4-28 下午4:05:59
	*/
	ResultBean<List<RepAirTransportDto>> queryTicketInfo(AirTransportRequestEntity airTransportRequest) throws Exception;

	
	/**
	 * @description 用一句话说明这个方法做什么
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月17日 下午5:23:13
	 */
	FossToWKResponseEntity validateExpressWaybillNoAndSerialNo(String requestParameter) throws Exception;

	/**
	 * @description Foss同步pda确认卸车任务到wk系统
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月18日 下午5:26:27
	 */
	FossToWKResponseEntity syncPDAConfirmUnloadTaskToWk(String requestParameter) throws Exception;

	/**
	 * @description 同步PDA创建卸车任务到悟空系统
	 * @param requestParameter
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月19日 上午9:56:31
	 */
	FossToWKResponseEntity syncPDACreateUnloadTaskToWk(String requestParameter) throws Exception;
	
	/**
	* @description 查询卸车托盘绑定管理信息  
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 328060-foss-yekai
	* @update 2016年5月10日 
	*/
	String queryUnloadTaskbindTrayListExpress(RequestParameterEntity requestParameter) throws Exception;

	/**
	* @description 查询卸车托盘绑定明细管理信息  
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 328060-foss-yekai
	* @update 2016年5月14日 
	*/
	String queryUnloadbindTrayDetailByUnloadTaskNoExpress(
			RequestParameterEntity requestParameter) throws Exception;

	/**
	* @description 查询叉车工作量管理
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 328060-foss-yekai
	* @update 2016年5月16日 
	*/
	String queryTrayScanListExpress(RequestParameterEntity requestParameter) throws Exception;

	/**
	* @description 查询叉车工作量管理明细
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 328060-foss-yekai
	* @update 2016年5月18日 
	*/
	String queryWaybillByTaskNoExpress(RequestParameterEntity requestParameter) throws Exception;

	/**
	* @description 查询离线叉车工作量
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 328060-foss-yekai
	* @update 2016年5月19日 
	*/
	String queryTrayOfflineScanListExpress(RequestParameterEntity parameter) throws Exception;
	
	/**
	* @description 查询待叉区货物
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 328060-foss-yekai
	* @update 2016年5月19日 
	*/
	String querySubForkAreaGoodsExpress(RequestParameterEntity parameter) throws Exception;
	
   /**
	 * @description 返回快递卸车差异信息
	 * (non-Javadoc)
	 * @author 332209-foss-ruilibao
	 * @update 2016年5月17日 下午3:44:26
	 * @version V1.0
	 */
	 FossToWKResponseAndPageEntity getExpressDiffList(
			String diffReportNo,String orgCode, int limit, int start);
			
	
	/**
	* @description 同步装车给悟空
	* @param reqMsg
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月30日 上午10:42:21
	*/
	public Object sysnCreateLoadToWK(String reqMsg);
	
	
	/**		
	 * @description 同步临时租车快递运单到悟空系统(按日期查询)		
	 * (non-Javadoc)		
	 * @author 313352-foss-gouyangyang		
	 * @update 2016年5月29日 下午3:44:26		
	 * @version V1.0		
	 */		
	public CourierWaybillEntity  expressDeliveryMarkToWk(ExpressDateDto expressDateDto);
	
	/**		
	 * @description 同步临时租车快递运单到悟空系统(按单号查询)		
	 * (non-Javadoc)		
	 * @author 313352-foss-gouyangyang		
	 * @update 2016年5月29日 下午3:44:26		
	 * @version V1.0		
	 */		
	public CourierWaybillEntity  expressDeliveryToWk(ExpressDateDto expressDateDto);
	
	/**
	 * 
	* @description FOSS获取快递发车计划线路信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#getExpresslineInfoFromWk(com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity)
	* @author 332209-foss-ruilibao
	* @update 2016-5-12 下午4:30:12
	* @version V1.0
	 */
	public FossToWKResStdEntity getExpresslineInfoFromWk(String origOrgCode, String destOrgCode) throws Exception;

    /**
     * @description 查询交接单号list
     * @param requestJsonStr
     * @return
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年6月8日 上午8:39:10
     */
	FossToWKResponseEntity queryExpressHandOverBillListByWaybillNo(String requestJsonStr);
	
	/**
	 * 
	* @description FOSS从悟空系统查询快递卸车差异信息(添加少或和多货)
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#getExpressDiffList(java.lang.String)
	* @author 313352-foss-gouyangyang
	* @update 2016-5-24 下午5:23:14
	* @version V1.0
	* 
	*/
	public Object getExpressLessCargoList(String[] str, Date startDate, Date endDate);
			
	
	/**
	 * 
	* @description FOSS获取快递到达部门列表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService#getExpresslineInfoFromWk(com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity)
	* @author 332209-foss-ruilibao
	* @update 2016-5-12 下午4:30:12
	* @version V1.0
	 */
	public FossToWKDepartCodeEntity getExpressArriveDepartListFromWk(String origOrgCode, String planType) throws Exception;

    /**
    *
    * @description 调用悟空接口修改悟空交接单车牌号
    * (non-Javadoc)
    * @author 332209-foss-ruilibao
    * @update 2016-5-12 下午4:30:12
    * @version V1.0
    */
	TruckTaskInfoResponse editWkHandOverBillVehicleno(String vehicleno, String handoverBillNo, String operationOrgCode) throws Exception;

	/**
	 * 同步提交装车任务给悟空
	 * <p>第二套改同步</p>
	 * @param jsonString
	 * @return
	 */
	Map<String, Object> sysnSubmitLoadToWK(String jsonString);
	
	/**
	* @description 修改交接单到悟空系统
	* @param requestParameter
	* @return
	* @throws Exception
	* @version 1.0
	* @author 33209-foss ruilibao
	* @update 2016年4月25日 下午3:40:03
	*/
	Map<String, Object> editHandOverBillToWk(String jsonString);
	
	/**
	 * @description 同步修改理货员信息到悟空系统
	 * @param requestParameter
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年11月18日 上午10:15:23
	 */
	FossToWKResponseEntity syncPDAmodifyLoaderToWk(String requestParameter) throws Exception;
	
	/**
	 * @description 同步装车修改理货员信息到悟空系统
	 * @param requestParameter
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年11月18日 上午10:15:23
	 */
	FossToWKResponseEntity syncPDALoadModifyLoaderToWk(String requestParameter) throws Exception;
	
	/**
	 * @description 根据任务号修改任务号对应的车牌号
	 * @param requestParameter
	 * @return
	 * @version 1.0
	 * @author 332209-foss-ruilibao
	 */
	Object sysnEditLoadToWkByTaskNo (String reqMsg);
	/**
     * @description 快递机场扫面单据(日期和单号)
     * (non-Javadoc)
     * @author 313352-foss-gouyangyang
     * @update 2016年12月06日 下午3:44:26
     * @version V1.0
     */
    public ExpressAirportEntity expressAirportToWk(AirExpressDateDto airExpressDateDto) ;
}
