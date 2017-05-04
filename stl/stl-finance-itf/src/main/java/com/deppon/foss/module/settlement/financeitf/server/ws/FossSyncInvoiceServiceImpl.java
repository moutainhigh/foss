package com.deppon.foss.module.settlement.financeitf.server.ws;

import java.util.List;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.foss.SysQueryInvoiceBasicRequest;
import com.deppon.esb.inteface.domain.foss.SysQueryInvoiceBasicResponse;
import com.deppon.esb.inteface.domain.foss.WaybillInvoiceInfo;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.finmanager.CommonException;
import com.deppon.foss.inteface.finmanager.FossSyncInvoiceService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IInvoiceRegisterService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillInvoiceDto;

/**
 * 发票系统:处理根据运单、小票集合查询运单小票基础数据WS
 * 
 * @author 163576
 * @date 2014-7-16 10:25:56
 */
public class FossSyncInvoiceServiceImpl implements FossSyncInvoiceService {

	/** 日志. */
	private static final Logger logger = LogManager.getLogger(FossSyncInvoiceServiceImpl.class);
	
	private IInvoiceRegisterService invoiceRegisterService; 
	
	/**
     * 根据运单、小票集合查询运单小票基础数据.
     * @author 163576
	 * @param
	 * @date 2014-7-16 10:25:56
	 * @return
	 */
	@Override
	public SysQueryInvoiceBasicResponse sysQueryWaybillReceiptBasic(
			SysQueryInvoiceBasicRequest sysQueryInvoiceBasicRequest,
			Holder<ESBHeader> esbHeader) throws CommonException {
		//记录日志
		logger.info("发票系统:处理根据运单、小票、合并运单集合查询运单小票基础数据WS开始.");
		//发送seb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		SysQueryInvoiceBasicRequest request = sysQueryInvoiceBasicRequest;
		SysQueryInvoiceBasicResponse response = new SysQueryInvoiceBasicResponse();
		
		WaybillInvoiceDto dto = null;
		boolean success = false;
		String reason = null;
		try {
			dto = invoiceRegisterService.queryWaybillReceiptInfoForFPList(request.getWaybillNoList(), request.getMergeWaybillNoList(),
					request.getOtherRevenueNoList(),request.getHhStatementNoList());
			// 返回对应小票或者运单
			if (dto != null && (CollectionUtils.isNotEmpty(dto.getMergeWaybillDtoList())
					|| CollectionUtils.isNotEmpty(dto.getOtherRevenueDtoList())
					|| CollectionUtils.isNotEmpty(dto.getWaybillDtoList())
					|| CollectionUtils.isNotEmpty(dto.getMergedWaybillNoList()))
			) {
				//获取已经合并过的运单号集合
				if(CollectionUtils.isNotEmpty(dto.getMergedWaybillNoList())){
					response.getMergedWaybillNoList().addAll(dto.getMergedWaybillNoList());
				}
				if(CollectionUtils.isNotEmpty(dto.getWaybillDtoList())){
					List<WaybillInvoiceDto> waybillDtoList = dto.getWaybillDtoList();
					for (WaybillInvoiceDto entity : waybillDtoList) {
						WaybillInvoiceInfo info = new WaybillInvoiceInfo();
						BeanUtils.copyProperties(entity, info, new String[]{"billTime"});//类型不一致，剔除billTime
						info.setBillTime(SettlementUtil.dateToXmlDate(entity.getBillTime()));//手动设置billTime
						response.getWaybillList().add(info);
					}
				}
				if(CollectionUtils.isNotEmpty(dto.getMergeWaybillDtoList())){
					List<WaybillInvoiceDto> mergeWaybillDtoList = dto.getMergeWaybillDtoList();
					for (WaybillInvoiceDto entity : mergeWaybillDtoList) {
						WaybillInvoiceInfo info = new WaybillInvoiceInfo();
						BeanUtils.copyProperties(entity, info, new String[]{"billTime"});//类型不一致，剔除billTime
						info.setBillTime(SettlementUtil.dateToXmlDate(entity.getBillTime()));//手动设置billTime
						response.getMergeWaybillList().add(info);
					}
				}
				if(CollectionUtils.isNotEmpty(dto.getOtherRevenueDtoList())){
					List<WaybillInvoiceDto> otherRevenueDtoList = dto.getOtherRevenueDtoList();
					for (WaybillInvoiceDto entity : otherRevenueDtoList) {
						WaybillInvoiceInfo info = new WaybillInvoiceInfo();
						BeanUtils.copyProperties(entity, info, new String[]{"billTime"});//类型不一致，剔除billTime
						info.setBillTime(SettlementUtil.dateToXmlDate(entity.getBillTime()));//手动设置billTime
						response.getOtherRevenueList().add(info);
					}
				}
				if(CollectionUtils.isEmpty(dto.getWaybillDtoList()) &&
						CollectionUtils.isEmpty(dto.getOtherRevenueDtoList()) &&
						CollectionUtils.isEmpty(dto.getMergeWaybillDtoList())){
					success = false;
					if(CollectionUtils.isNotEmpty(dto.getMergedWaybillNoList())){
						reason="该单号已经做了合并运单，请用单号查询合并运单后，使用合并运单号申请发票";
					}else{
						reason="无效的小票或运单号或合并运单号";
					}
				}else{
					//@218392 张永雪 2016-03-22 21:20:50 配合发票系统修改
					success = true;
				}
			}else if(dto!=null&&CollectionUtils.isNotEmpty(dto.getHhStatementDtoList())){
				List<WaybillInvoiceDto> hhStatementDtoList = dto.getHhStatementDtoList();
				for (WaybillInvoiceDto entity : hhStatementDtoList) {
					WaybillInvoiceInfo info = new WaybillInvoiceInfo();
					BeanUtils.copyProperties(entity, info, new String[]{"billTime"});//类型不一致，剔除billTime
					info.setBillTime(SettlementUtil.dateToXmlDate(entity.getBillTime()));//手动设置billTime
					response.getDzdhList().add(info);
				}
				success = true;
			}else{
				//返回信息
				success = false;
				//处理结果
				reason = "FOSS运单/小票信息同步接口异常：无效的小票或运单号或者合并运单号，返回结果集为空，或对账单获取值时候出现问题";
				//日志
				logger.error(reason);
			}
		}catch(BusinessException e) {
			//日志
			logger.error(e.getErrorCode() , e);
			//返回信息
			success = false;
			//处理结果
			reason = "FOSS运单/小票信息/合伙人对账单同步接口异常："+e.getErrorCode();
			
			//throw new CommonException(reason);
		}catch (Exception e) {
			//日志
			logger.error(e.getMessage() , e);
			//返回信息
			success = false;
			//处理结果
			reason = "FOSS运单/小票信息/合伙人对账单同步接口异常："+e.getMessage();
			
			throw new CommonException(reason);
		}finally{
			//返回信息
			response.setSyncStatus(success);
			//返回信息
			response.setReason(reason);
		}
		
		
		//记录日志
		logger.info("发票系统:处理根据运单、小票集合查询运单小票基础数据WS结束.");
		//返回结果
		return response;
	}

	/**
	 * @param invoiceRegisterService the invoiceRegisterService to set
	 */
	public void setInvoiceRegisterService(
			IInvoiceRegisterService invoiceRegisterService) {
		this.invoiceRegisterService = invoiceRegisterService;
	}

}
