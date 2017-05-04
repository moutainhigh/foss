package com.deppon.foss.module.transfer.airfreight.server.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirNotifyCustomersSmsInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirNotifyCustomersDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirNotifyCustomersVO;
import com.deppon.foss.module.transfer.airfreight.server.service.impl.AirNotifyCustomersService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 查询空运到达相关操作.
 * @author foss-200968-zwd
 * @date 2015-08-18 18:10:14
 */

public class AirNotifyCustomersAction extends AbstractAction {
	
	/** 序列化. */
	private static final long serialVersionUID = -5343745123698287L;
	
	/** 日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirNotifyCustomersAction.class);
 
	AirNotifyCustomersVO vo = new AirNotifyCustomersVO();
	
	public AirNotifyCustomersVO getVo() {
		return vo;
	}
	public void setVo(AirNotifyCustomersVO vo) {
		this.vo = vo;
	}
	private AirNotifyCustomersService airNotifyCustomersService;
	public void setAirNotifyCustomersService(
			AirNotifyCustomersService airNotifyCustomersService) {
		this.airNotifyCustomersService = airNotifyCustomersService;
	}

	/**
	 * 空运通知客户--主查询
	 */
	public String queryStayAirNotificationBill(){
		try {
			// 查询符合条件的运单总记录数
			AirNotifyCustomersDto conditionDto = vo.getAirNotifyCustomersDto();
			// 根据运单总记录数
			if (conditionDto != null) {
				// 运单总记录数   ≥ 0
				// 查询符合条件的记录列表
				List<AirNotifyCustomersDto> airNotifyCustomersDtoList = new ArrayList<AirNotifyCustomersDto>();
				airNotifyCustomersDtoList = airNotifyCustomersService.queryAirNotifyCustomers(conditionDto, this.getStart(), this.getLimit());
				if(CollectionUtils.isNotEmpty(airNotifyCustomersDtoList)){
					managerAirNoifyList(airNotifyCustomersDtoList);
				}
				vo.setAirNotifyCustomersDtoList(airNotifyCustomersDtoList);
				//获取总记录条数,用于分页
				long totalCount = airNotifyCustomersService.queryAirNotifyCustomersCount(conditionDto);
				//分页总数
				this.setTotalCount(totalCount);
			} 
			//返回成功信息
			return returnSuccess();
		} catch (BusinessException e) {
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
   }
	/**
	 * sonar优化，免5层内嵌，311396 2016年12月23日14:24:
	 * @param airNotifyCustomersDtoList
	 */
	private void managerAirNoifyList(List<AirNotifyCustomersDto> airNotifyCustomersDtoList) {
		for (AirNotifyCustomersDto airNotifyCustomersDto : airNotifyCustomersDtoList) {
			if(airNotifyCustomersDto.getReceiveMethod() != null ){
				if(airNotifyCustomersDto.getReceiveMethod().contains("PICKUP")){
					airNotifyCustomersDto.setReceiveMethod("自提");
				}else if(airNotifyCustomersDto.getReceiveMethod().contains("DELIVER")){
					airNotifyCustomersDto.setReceiveMethod("送货");
				}	
			}
			
		}
	}
	
	/**
	 * 查询通知信息和运单信息
	 * @return
	 */
	public String queryAirNotifyCustomersWaybillInfo(){
		try {
			// 查询符合条件的运单总记录数
			AirNotifyCustomersDto conditionDto = vo.getAirNotifyCustomersDto();
			// 根据运单总记录数
			if (conditionDto != null) {
				// 运单总记录数   ≥ 0
				// 查询符合条件的记录列表
				AirNotifyCustomersDto airNotifyCustomersDto = new  AirNotifyCustomersDto();
				airNotifyCustomersDto = airNotifyCustomersService.queryAirNotifyWaybillInfo(conditionDto);
				/**
				 * 提货方式
				 */
				if(airNotifyCustomersDto.getReceiveMethod() != null ){
					if(airNotifyCustomersDto.getReceiveMethod().contains("PICKUP")){
						airNotifyCustomersDto.setReceiveMethod("自提");
					}
					else if(airNotifyCustomersDto.getReceiveMethod().contains("DELIVER")){
						airNotifyCustomersDto.setReceiveMethod("送货");
					}	
				}
				vo.setAirNotifyCustomersDto(airNotifyCustomersDto);
			} 
			//返回成功信息
			return returnSuccess();
		} catch (BusinessException e) {
			//抛出异常
			//写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return returnError(e);
		}
   }
	
	
	/**
	 * 批量通知，获取运单信息.
	 * 2015-08-29
	 */
	@JSON
	public String airBatchNotifyList() {
		try {
			// 获取传入参数通知信息entity
			AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo = vo.getAirNotifyCustomersDto().getAirNotifyCustomersSmsInfo();
			// 判断通知信息entity的null属性
			if (airNotifyCustomersSmsInfo == null) {
				// 如果页面传入的notificationEntity为NULL，说明通过点击批量通知按钮进去，则实例化notificationEntity
				// 不等于NULL，说明弹出的批量通知页面动态加载进去本方法
				airNotifyCustomersSmsInfo = new AirNotifyCustomersSmsInfo();
			}
			// 获取并设置批量通知页面显示列表信息
			vo.setAirNotifyCustomersSmsInfoList(airNotifyCustomersService.queryWaybillsByNos(vo.getAirNotifyCustomersDto().getWaybillNos(), airNotifyCustomersSmsInfo, vo.getAirNotifyCustomersDto().getAirNotifyCustomersSmsInfoList()));
			// 设置通知信息发送时间范围
			vo.setInformationReceiveTimeRange(airNotifyCustomersService.getInformationReceiveTimeRange());
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 执行批量通知.  List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList
	 * 2015-08-29
	 */
	@JSON
	public String airBatchNotify() {
		try {
			List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList =
					vo.getAirNotifyCustomersDto().getAirNotifyCustomersSmsInfoList();
			if(CollectionUtils.isNotEmpty(airNotifyCustomersSmsInfoList)){
				// 执行批量通知
				airNotifyCustomersService.airBatchNotify(airNotifyCustomersSmsInfoList);
			}		
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 通知客户
	 * 1、发送短信息
	 * 2、保存空运通知信息
	 * @return
	 */
	public String addAirNotifyCustomer() {

		try {
			AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo = vo
					.getAirNotifyCustomersDto().getAirNotifyCustomersSmsInfo();
			if (airNotifyCustomersSmsInfo != null) {
				List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList = new ArrayList<AirNotifyCustomersSmsInfo>();
				airNotifyCustomersSmsInfo.setId(UUIDUtils.getUUID());
				airNotifyCustomersSmsInfoList.add(airNotifyCustomersSmsInfo);
				// 保存空运通知信息 && 发送短信息
				
				airNotifyCustomersService
						.airBatchNotify(airNotifyCustomersSmsInfoList);
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	
	
	
	
}