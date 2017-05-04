package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.inteface.domain.deliverytask.SyncDeliveryTaskRequest;
import com.deppon.foss.inteface.domain.deliverytask.Waybill;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.define.FossConstants;

public class GpsDeliverService implements IGpsDeliverService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GpsDeliverService.class);
	/**
	 * 基础运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 组织信息Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * <p>将派送任务传递至GPS接口中</p>
	 * 传1：新增派送单和运单， 要求必须有运单列表
	 * 传2：修改司机信息和运单信息(如果有传运单过来)
	 * 传3：删除运单, 必须传运单过来
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月6日 09:23:52
	 * @param 1为确定派送单 2为修改派送单3为取消派送单
	 */
	@Override
	public ResultDto syscConfirmDeliverTaskToGps(DeliverbillEntity deliverbill) {
		//判断派送单编码是否为空的情况
		if(StringUtils.isBlank(deliverbill.getDeliverbillNo())){
			LOGGER.error("派送单编码为空");
			throw new DeliverbillException("系统同步派送任务至GPS的派送单编码为空，请稍候重试");
		}
		//判断车辆号码是否为空的情况
		if(StringUtils.isBlank(deliverbill.getVehicleNo())){
			LOGGER.error("车辆号码为空");
			throw new DeliverbillException("系统同步派送任务至GPS的车辆号码为空，请稍候重试");
		}
		ReflectionToStringBuilder.toString("=====派送单详细数据："+deliverbill+"=========");
		ResultDto dto = new ResultDto();
		//创建具体消息
		SyncDeliveryTaskRequest request = new SyncDeliveryTaskRequest();
		//车辆唯一标识,就是车牌号
		request.setVehicleId(StringUtil.defaultIfNull(deliverbill.getVehicleNo()));
		//司机工号
		request.setDriverCode(StringUtil.defaultIfNull(deliverbill.getDriverCode()));
		//司机姓名
		request.setDriverName(StringUtil.defaultIfNull(deliverbill.getDriverName()));
		//派送单编码
		request.setOrderNo(StringUtil.defaultIfNull(deliverbill.getDeliverbillNo()));
		//操作部门转换
		if(StringUtils.isNotEmpty(deliverbill.getTransferCenter())){
			//进行数据的转换，如果是营业部则传递营业部，外场下面的组织，统一转换成对应的外场
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deliverbill.getTransferCenter());
			//操作部门
			request.setDepartDeptCode(StringUtil.defaultIfNull(orgInfo == null ? deliverbill.getTransferCenter() : orgInfo.getUnifiedCode()));
		}
		//操作类型
		request.setOperationFlag(1);
		
		//封装运单数据
		List<Waybill> waybills = new ArrayList<Waybill>();
		List<WaybillEntity> list = waybillManagerService.queryWaybillEntityByDeliverbillById(deliverbill.getId());
		//用于存储
		StringBuffer sb = new StringBuffer();
		if(CollectionUtils.isNotEmpty(list)){
			Waybill way = null;
			for(int i=0;i<list.size();i++){
				way = new Waybill();
				//运单号不为空
				if(StringUtils.isNotBlank(list.get(i).getWaybillNo())){
					//设置运单号
					way.setWaybillNo(list.get(i).getWaybillNo());
					//重量
					way.setWeight(list.get(i).getGoodsWeightTotal().doubleValue());
					//体积
					way.setVolume(list.get(i).getGoodsVolumeTotal().doubleValue());
					//你懂的
					waybills.add(way);
					if(i !=list.size()-1){
						sb.append(list.get(i).getWaybillNo()).append(",");						
					}else{
						sb.append(list.get(i).getWaybillNo());
					}
				}else{
					LOGGER.info("查询到的运单号为空，查询参数：派送单ID为："+deliverbill.getId());
					throw new DeliverbillException("Foss同步至Gps送货任务时查询到的运单号为空，查询参数：派送单ID为："+deliverbill.getId());
				}
			}
			request.getWaybills().addAll(waybills);
		}
		//准备消息头信息
		AccessHeader header = createAccessHeader(deliverbill, sb.toString(), 1);
		
		// 发送请求
		try {
			LOGGER.info("Foss系统同步派送任务至GPS请求头详细数据："+ReflectionToStringBuilder.toString(header));
			LOGGER.info("Foss系统同步派送任务至GPS请求详细数据："+ReflectionToStringBuilder.toString(request));
			LOGGER.info("Foss系统同步派送任务至GPS请求详细数据："+ReflectionToStringBuilder.toString(request.getWaybills()));
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info("Foss确定派送单同步送货任务至Gps发送成功");
		} catch (Exception e) {
			// 对异常进行处理
			LOGGER.error("Foss系统同步派送任务至GPS失败。详情：", e);
			dto.setCode(ResultDto.FAIL);
			dto.setMsg("JMS请求发送失败："+e.getMessage());
			return dto;
		}
		//设置返回成功参数
		dto.setCode(ResultDto.SUCCESS);
		dto.setMsg("");
		return dto;
	}
	

	/**
	 * <p>将派送任务传递至GPS接口中</p>
	 * 传1：新增派送单和运单， 要求必须有运单列表
	 * 传2：修改司机信息和运单信息(如果有传运单过来)
	 * 传3：删除运单, 必须传运单过来
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月6日 09:23:52
	 * @param 1为确定派送单 2为修改派送单3为取消派送单
	 */
	@Override
	public ResultDto syscCancelDeliverTaskToGps(DeliverbillEntity deliverbill, String waybillNo) {
		//参数都为空的就不管了
		if(deliverbill == null || StringUtils.isEmpty(waybillNo)){
			LOGGER.error("系统同步派送任务至GPS的参数为空");
			throw new DeliverbillException("系统取消同步派送任务至GPS的参数为空，请稍候重试");
		}
		//判断派送单编码是否为空的情况
		if(StringUtils.isBlank(deliverbill.getDeliverbillNo())){
			LOGGER.error("派送单编码为空");
			throw new DeliverbillException("系统取消同步派送任务至GPS的派送单编码为空，请稍候重试");
		}
		//判断车辆号码是否为空的情况
		if(StringUtils.isBlank(deliverbill.getVehicleNo())){
			LOGGER.error("车辆号码为空");
			throw new DeliverbillException("系统取消同步派送任务至GPS的车辆号码为空，请稍候重试");
		}
		//运单号是否为空
		if(StringUtils.isEmpty(waybillNo)){
			LOGGER.error("拉回的货物的运单号为空");
			throw new DeliverbillException("系统取消同步派送任务至GPS的拉回的货物的运单号为空，请稍候重试");
		}
		LOGGER.info("派送单详细数据："+ReflectionToStringBuilder.toString(deliverbill));
		LOGGER.info("到达联数据："+ReflectionToStringBuilder.toString(waybillNo));
		ResultDto resultDto = new ResultDto();
		//创建具体消息
		SyncDeliveryTaskRequest request = new SyncDeliveryTaskRequest();
		//车辆唯一标识,就是车牌号
		request.setVehicleId(StringUtil.defaultIfNull(deliverbill.getVehicleNo()));
		//司机工号
		request.setDriverCode(StringUtil.defaultIfNull(deliverbill.getDriverCode()));
		//司机姓名
		request.setDriverName(StringUtil.defaultIfNull(deliverbill.getDriverName()));
		//派送单编码
		request.setOrderNo(StringUtil.defaultIfNull(deliverbill.getDeliverbillNo()));
		//操作部门转换
		//操作部门转换
		if(StringUtils.isNotEmpty(deliverbill.getTransferCenter())){
			//进行数据的转换，如果是营业部则传递营业部，外场下面的组织，统一转换成对应的外场
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deliverbill.getTransferCenter());
			//操作部门
			request.setDepartDeptCode(StringUtil.defaultIfNull(orgInfo == null ? deliverbill.getTransferCenter() : orgInfo.getUnifiedCode()));
		}
		//操作类型
		request.setOperationFlag(NumberConstants.NUMBER_3);
		
		//运单详情
		Waybill way = new Waybill();
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		//设置request中运单的基础资料
		if(waybillEntity != null){			
			//TODO 这里需要注意的是如果是需要进行快递也要传递GPS任务的时候把这段代码注释掉
			if(ExpWaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
				//设置发送成功的参数
				resultDto.setCode(ResultDto.SUCCESS);
				resultDto.setMsg("");
				return resultDto;
			}
			//设置运单号
			way.setWaybillNo(waybillEntity.getWaybillNo());
			//重量
			way.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
			//体积
			way.setVolume(waybillEntity.getGoodsVolumeTotal().doubleValue());
			request.getWaybills().add(way);
		}
		//准备消息头信息
		AccessHeader header = createAccessHeader(deliverbill, waybillNo, 3);
		// 发送请求
		try {
			LOGGER.info("Foss系取消同步派送任务至GPS请求头详细数据："+ReflectionToStringBuilder.toString(header));
			LOGGER.info("Foss系统取消同步派送任务至GPS请求详细数据："+ReflectionToStringBuilder.toString(request));
			LOGGER.info("Foss系统取消同步派送任务至GPS请求详细数据："+ReflectionToStringBuilder.toString(request.getWaybills()));
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info("Foss取消同步送货任务至Gps发送成功");
		} catch (Exception e) {
			// 对异常进行处理
			LOGGER.error("Foss取消系统同步派送任务至GPS失败。详情：", e);
			resultDto.setCode(ResultDto.FAIL);
			resultDto.setMsg("JMS请求发送失败："+e.getMessage());
			return resultDto;
		}
		//设置发送成功的参数
		resultDto.setCode(ResultDto.SUCCESS);
		resultDto.setMsg("");
		return resultDto;
	}


	/**
	 * <a>修改派送单
	 * 1为确定派送单 2为修改派送单3为取消派送单
	 * </a>
	 * 传1：新增派送单和运单， 要求必须有运单列表
	 * 传2：修改司机信息和运单信息(如果有传运单过来)
	 * 传3：删除运单, 必须传运单过来
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-14 19:36:33
	 * @param DeliverbillEntity deliverbill 派送单
	 */
	@Override
	public ResultDto syscModifyDeliverTaskToGps(DeliverbillEntity deliverbill, WaybillEntity waybillEntity) {
		//判断派送单编码是否为空的情况
		if(StringUtils.isBlank(deliverbill.getDeliverbillNo())){
			LOGGER.error("派送单编码为空");
			throw new DeliverbillException("系统修改派送任务至GPS的派送单编码为空，请稍候重试");
		}
		//判断车辆号码是否为空的情况
		if(StringUtils.isBlank(deliverbill.getVehicleNo())){
			LOGGER.error("车辆号码为空");
			throw new DeliverbillException("系统修改派送任务至GPS的车辆号码为空，请稍候重试");
		}
		ReflectionToStringBuilder.toString("=====派送单详细数据："+deliverbill+"=========");
		ResultDto dto = new ResultDto();
		//创建具体消息
		SyncDeliveryTaskRequest request = new SyncDeliveryTaskRequest();
		//车辆唯一标识,就是车牌号
		request.setVehicleId(StringUtil.defaultIfNull(deliverbill.getVehicleNo()));
		//司机工号
		request.setDriverCode(StringUtil.defaultIfNull(deliverbill.getDriverCode()));
		//司机姓名
		request.setDriverName(StringUtil.defaultIfNull(deliverbill.getDriverName()));
		//派送单编码
		request.setOrderNo(StringUtil.defaultIfNull(deliverbill.getDeliverbillNo()));
		//操作部门转换
		//操作部门转换
		if(StringUtils.isNotEmpty(deliverbill.getTransferCenter())){
			//进行数据的转换，如果是营业部则传递营业部，外场下面的组织，统一转换成对应的外场
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deliverbill.getTransferCenter());
			//操作部门
			request.setDepartDeptCode(StringUtil.defaultIfNull(orgInfo == null ? deliverbill.getTransferCenter() : orgInfo.getUnifiedCode()));
		}
		//操作类型
		request.setOperationFlag(2);
		//运单详情
		Waybill way = new Waybill();
		//设置request中运单的基础资料
		if(waybillEntity != null){
			//TODO 这里需要注意的是如果是需要进行快递也要传递GPS任务的时候把这段代码注释掉
			if(ExpWaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
				//设置发送成功的参数
				dto.setCode(ResultDto.SUCCESS);
				dto.setMsg("");
				return dto;
			}
			//设置运单号
			way.setWaybillNo(waybillEntity.getWaybillNo());
			//重量
			way.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
			//体积
			way.setVolume(waybillEntity.getGoodsVolumeTotal().doubleValue());
			request.getWaybills().add(way);
		}
		//准备消息头信息
		AccessHeader header = createAccessHeader(deliverbill, null, 2);
		
		// 发送请求
		try {
			LOGGER.info("Foss系统修改派送任务至GPS请求头详细数据："+ReflectionToStringBuilder.toString(header));
			LOGGER.info("Foss系统修改派送任务至GPS请求详细数据："+ReflectionToStringBuilder.toString(request));
			LOGGER.info("Foss系统修改派送任务至GPS请求详细数据："+ReflectionToStringBuilder.toString(request.getWaybills()));
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info("Foss修改派送单同步送货任务至Gps发送成功");
		} catch (Exception e) {
			// 对异常进行处理
			LOGGER.error("Foss系统修改派送任务至GPS失败。详情：", e);
			dto.setCode(ResultDto.FAIL);
			dto.setMsg("JMS请求发送失败："+e.getMessage());
			return dto;
		}
		//设置返回成功参数
		dto.setCode(ResultDto.SUCCESS);
		dto.setMsg("");
		return dto;
	}

	/**
	 * 对派送单对应的部门进行转换，如果是营业部则传递营业部，如果是外场下面的组织，需要集体转换成外场标杆编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-13 16:38:19
	 * @param createOrgCode
	 * @return
	 */	
	private String revertToTopUnifiedCode(String createOrgCode) {
		String deptCode = null;
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(createOrgCode);
    	if(saleDepartmentEntity != null){
    		//如果是派送中心，查询对应外场的待办
    		if(FossConstants.YES.equals(saleDepartmentEntity.getStation())){
    			String transferCenter = saleDepartmentEntity.getTransferCenter();
    			if(StringUtil.isNotEmpty(transferCenter)){
    				deptCode = transferCenter;
    			}
    		}else{
    			deptCode = saleDepartmentEntity.getCode();
    		}
    	}else{
    		//非营业部找到它上级所属外场的编码
    		List<String> bizTypes = new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
    		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(createOrgCode, bizTypes);
    		if(orgAdministrativeInfoEntity != null){
    			deptCode = orgAdministrativeInfoEntity.getCode();
    		}else{
    			deptCode = createOrgCode;
    		}
    	}
    	OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
    	if(orgInfo != null){
    		return orgInfo.getUnifiedCode();
    	}else{
    		return null;
    	}
	}
	
	/**
	 * <p>封装表头参数</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月6日 09:32:59
	 * @param deliverbill
	 * @param request
	 * @return
	 */
	private AccessHeader createAccessHeader(DeliverbillEntity deliverbill,String waybillList, int state) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		header.setEsbServiceCode(DeliverbillConstants.ESB_FOSS2ESB_SYNC_DELIVERYTASK_CODE);
		//版本随意
		header.setVersion(DeliverbillConstants.ESB_FOSS2ESB_SYNC_DELIVERYTASK_VERSION);
		//business id 派送单号
		header.setBusinessId(String.valueOf(state));
		//运单集合
		header.setBusinessDesc1(waybillList);
		return header;
	}

	/**
	 * 组织信息Service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 基础运单服务类
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}


	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}
