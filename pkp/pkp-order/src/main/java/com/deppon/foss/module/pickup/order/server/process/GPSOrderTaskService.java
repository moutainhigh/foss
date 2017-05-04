/**
 * @remark 
 * @author YANGBIN
 * @date 2014-3-8 上午10:19:22
 */
package com.deppon.foss.module.pickup.order.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.foss.inteface.domain.collectiontask.SyncCollectionTaskRequest;
import com.deppon.foss.module.pickup.order.api.server.service.IGPSOrderTaskService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;

/** 
 * @ClassName: GPSOrderTaskService 
 * @Description: PDA确认接货将约车订单发送到GPS
 * @author YANGBIN
 * @date 2014-3-8 上午10:19:22 
 *  
 */
public class GPSOrderTaskService implements IGPSOrderTaskService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GPSOrderTaskService.class.getName());
	/** (非 Javadoc) 
	 * <p>Title: putOrderTaskToGPS</p> 
	 * <p>PDA确认接货将约车订单发送到GPS</p> 
	 * @param orderHandleDto 
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IGPSOrderTaskService#putOrderTaskToGPS(com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto) 
	 */
	@Override
	public boolean putOrderTaskToGPS(OrderHandleDto orderHandleDto,int optState) {
		//初始化发送状态
		boolean status = false;
		//判断约车订单是否为空
		if(orderHandleDto == null){
			return false;
		}else{
			//取得推送头
			AccessHeader header = createAccessHeader(orderHandleDto.getOrderNo());
			//定义订单约车任务推送请求
			SyncCollectionTaskRequest request = new SyncCollectionTaskRequest();
			//设置约车部门编码
			request.setDepartDeptCode(orderHandleDto.getReceiveOrgCode());
			//设置司机工号
			request.setDriverCode(orderHandleDto.getDriverCode());
			//设置司机姓名
			request.setDriverName(orderHandleDto.getDriverName());
			//设置操作状态
			request.setOperationFlag(optState);
			//设置订单号
			request.setOrderNo(orderHandleDto.getOrderNo());
			//设置车牌号
			request.setVehicleId(orderHandleDto.getVehicleNo());
			try {
				//ESB进行异步消息推送调用
				ESBJMSAccessor.asynReqeust(header, request);
				//推送成功设置发送成功
				status = true;
			} catch (ESBException e) {
				//推送失败，抛出异常消息
				LOGGER.error(DispatchOrderStatusConstants.ESB_FOSS_SYNC_GPS_ORDER +"订单约车确认接货GPS接口失败！"+e);
			}
			return status;
		}
		
	}
	/**
	 * 
	 * @Title: createAccessHeader 
	 * @Description: JMS推送Header设置
	 * @param @param orderNo
	 * @param @return    设定文件 
	 * @return AccessHeader    返回类型 
	 * @throws
	 */
	private AccessHeader createAccessHeader(String orderNo) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供服务编码
		header.setEsbServiceCode(DispatchOrderStatusConstants.ESB_FOSS_SYNC_GPS_ORDER);
		//版本随意
		header.setVersion("0.1");
		//business id 随意
		header.setBusinessId(orderNo);
		//运单号放在消息头中传给GPS
		header.setBusinessDesc1(orderNo);
		return header;
	}

}
