package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.ptp.SendWithholdStatus;
import com.deppon.esb.inteface.domain.ptp.SendWithholdStatusRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.settlement.common.api.server.service.ISendStatusToPtpService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.WithholdStatusDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对接PTP系统服务
 * 
 * @author 269044-zhurongrong
 * @date 2016-12-23
 */
public class SendStatusToPtpService implements ISendStatusToPtpService {
	
	/**
	 * 记录日志
	 */
	public static final Logger logger = LogManager.getLogger(SendStatusToPtpService.class); 
	
	/**
	 * 服务编码
	 */
	private static final String FOSS_PTP_SEND_WITHHOLD_STATUS = "ESB_FOSS2ESB_SEND_WITHHOLD_STATUS";
	
	/**
	 * 版本编号
	 */
	private static final String version = "1.0";
	
	/**
	 * 注入合伙人部门service
	 */
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * SPBP-用户需求说明书-FOSS-D到H客户网上支付的需求优化
	 * 当客户进行网上支付，FOSS到达应收单已核销， FOSS进行校验该单的到达部门是否加盟网点，如果“是“，则将运单号、单据子类型, 
	 * 到达部门，到达部门编码，通知给PTP（FOSS扣款状态不变，仍为“未扣款”）
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-12-22
     */
	@Override
	public void sendStatusToPtp(List<WithholdStatusDto> withholdStatusDtoList) {
		
		//记录日志
		logger.info("更新ptp扣款状态开始,需更新扣款状态的运单个数为：" + withholdStatusDtoList.size());
		
		//请求单个实体
		SendWithholdStatus sendWithholdStatus = new SendWithholdStatus();
		//请求对象
		SendWithholdStatusRequest request = new SendWithholdStatusRequest();
		
		//循环操作
		for (int i = 0; i < withholdStatusDtoList.size(); i++) {
			//获取对象
			WithholdStatusDto dto = withholdStatusDtoList.get(i);
			//记录日志
			logger.info("运单号：" + dto.getWaybillNo());
			//记录日志
			logger.info("单据子类型：" + dto.getBillType());
			//记录日志
			logger.info("到达部门编码：" + dto.getDestOrgCode());
			//记录日志
			logger.info("到达部门名称：" + dto.getDestOrgName());
			//记录日志
			logger.info("场景：" + dto.getScene());
			//查询到达部门信息
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
					.querySaleDepartmentInfoByCode(dto.getDestOrgCode());
			//到达部门是合伙人部门并且单据子类型是到达应收单
			if(null != saleDepartmentEntity && FossConstants.ACTIVE.equals(saleDepartmentEntity.getIsLeagueSaleDept())
					&& SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
					.equals(dto.getBillType())){
				//设置运单号
				sendWithholdStatus.setWaybillNo(dto.getWaybillNo());
				//设置单据子类型
				sendWithholdStatus.setBillType(dto.getBillType());
				//设置到达部门编码
				sendWithholdStatus.setDestOrgCode(dto.getDestOrgCode());
				//设置到达部门名称
				sendWithholdStatus.setDestOrgName(dto.getDestOrgName());
				//设置场景
				sendWithholdStatus.setSence(dto.getScene());
				//添加到请求中
				request.getSendWithholdStatusList().add(sendWithholdStatus);
			}
		}
		try{
			//判空
			if(null != request && request.getSendWithholdStatusList().size() > 0) {
				// 设置服务编码
				AccessHeader accessHeader = new AccessHeader();
				//设置唯一标示
				accessHeader.setBusinessId(UUIDUtils.getUUID());
				//设置服务编码
				accessHeader.setEsbServiceCode(FOSS_PTP_SEND_WITHHOLD_STATUS);
				//设置版本号
				accessHeader.setVersion(version);
				//设置业务字段
				accessHeader.setBusinessDesc1("更新ptp扣款状态");
				//记录日志
				logger.info("更新扣款状态ESB_FOSS2ESB_SEND_WITHHOLD_STATUS send message.." +
						"请求个数为：" + request.getSendWithholdStatusList().size());
				
				ESBJMSAccessor.asynReqeust(accessHeader, request);
			}
			//记录日志
			logger.info("更新ptp扣款状态结束。。。");
			
		} catch (Exception e) {
			throw new SettlementException("通知PTP更新扣款状态失败:" + e.getMessage());		
		}
		
	}
	
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

}
