package com.deppon.foss.module.settlement.agency.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IBillReceivableAgencyService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.settlement.agency.api.shared.vo.BillReceivableAgencyVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;

/**
 * 查询_审核_作废空运其它应收
 * 
 * @author foss-pengzhen
 * @date 2012-10-26 上午11:31:54
 * @since
 * @version
 */
public class BillReceivableAgencyAction extends AbstractAction {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7836387691605246897L;
	
	/**
	 * njs
	 */	
	IAirAgencyCompanyService  airAgencyCompanyService;
	
	/**
	 * njs 
	 */
	IAirlinesService airlinesService;

	/**
	 * @param airlinesService the airlinesService to set
	 */
	public void setAirlinesService(IAirlinesService airlinesService) {
		this.airlinesService = airlinesService;
	}
		
	/**
	 * 应收单VO
	 */
	private BillReceivableAgencyVo billReceivableAgencyVo;

	/**
	 * 注入service
	 */
	private IBillReceivableAgencyService billReceivableAgencyService;

	/**
	 * 导出excel名称
	 */
	private String fileName;
	
	/**
	 * 接送货运单服务
	 */
	private IWaybillManagerService WaybillManagerService;
	

	/**
	 * 录入航空正单service
	 */
	private IAirWaybillDetailService pointsSingleJointTicketService;

	/**
	 * 产品服务管理类
	 */
	private IProductService productService;

	private ByteArrayInputStream inputStream;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillReceivableAgencyAction.class);
	
	/**
	 * 空运其他应收管理的分页查询
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-31 下午5:32:13
	 * @return
	 * @see
	 */
	@JSON
	public String queryAirReceivablePage() {
		try {
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			BillReceivableAgencyDto dto=billReceivableAgencyService
					.queryAirReceivablePage(billReceivableAgencyDto, getStart(),getLimit(),currentInfo);
			//空运其他管理分页查询
			billReceivableAgencyVo.setBillReceivableAgencyDto(dto);
			
			this.setTotalCount(billReceivableAgencyDto.getTotalNumRec());
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}
	
	/**
	 * 通过航空单号查询，应收单信息njs
	 */
	@JSON
	public String queryAirReceivableCusName(){
		try {
			
		//获取用户、部门信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		
		String airWaybillNo=billReceivableAgencyVo.getBillReceivableAgencyDto().getSourceBillNo();
		
		AirWaybillEntity airWaybillEntity=null;
		//通过航空正单号查询航空正单信息		
		airWaybillEntity=billReceivableAgencyService
				.queryAirReceivableCusName(airWaybillNo, 0, 1, currentInfo);
		
		//根据正单号中的运单号查询并设置运输性质
		//start modify by 269044-2016-07-05 解决bamp上的问题空指针异常
		//定义运单号
		String waybillNo = "";
		//定义运输性质
		String productCode = "";
		//定义产品名称
		String productName = "";
		//查询航空正单对应的一个明细运单号
		List<String> waybillNos = pointsSingleJointTicketService.queryWaybillNoList(airWaybillNo);
		//判空
		if(CollectionUtils.isNotEmpty(waybillNos)) {
			//获取运单值
			waybillNo = waybillNos.get(0);
		}
		//根据明细运单号查找运输性质
		WaybillEntity waybillEntity = WaybillManagerService.queryWaybillBasicByNo(waybillNo);
		//判空
		if(waybillEntity != null && StringUtils.isNotEmpty(waybillEntity.getProductCode())) {
			//获取运输性质
			productCode = waybillEntity.getProductCode();
		}
		//根据产品代码查找产品名称
		ProductEntity productEntity = productService.getProductByCache(productCode, new Date());
		//判空
		if(productEntity != null && StringUtils.isNotEmpty(productEntity.getName())) {
			//获取产品名称
			productName = productEntity.getName();
		}
		//end
		//设置运输性质code
		airWaybillEntity.setProductCode(productCode);
		//设置运输性质name
		airWaybillEntity.setProductName(productName);
		
		billReceivableAgencyVo.setAirWaybillEntity(airWaybillEntity);
		
		return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}
	
	/**
	 * njs 运单号
	 */
	public String  queryAiragencyCusName(){
		try {			
			//获取用户、部门信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		
		String waybillNo=billReceivableAgencyVo.getBillReceivableAgencyDto().getWaybillNo();
				
		List<AirPickupbillEntity> airweResult=billReceivableAgencyService.queryAiragencyCusName(waybillNo, 0, 
	    																1, currentInfo);
		
		AirPickupbillEntity airPickupbillEntity=null;
		if(airweResult.size()>0){
			airPickupbillEntity=airweResult.get(0);
			 
			//根据代理网点编码获得代理公司信息
			BusinessPartnerEntity businessPartnerEntity=null;
					
			businessPartnerEntity=airAgencyCompanyService.
					queryBusinessPartnerByAgencyDeptCode(airPickupbillEntity.getDestOrgCode());
			//判空
			if(StringUtils.isNotEmpty(businessPartnerEntity.getAgentCompanyName())&&
					StringUtils.isNotEmpty(businessPartnerEntity.getAgentCompanyCode())){	
				//转换代理网点信息，获取代理公司信息	
				airPickupbillEntity.setDestOrgName(businessPartnerEntity.getAgentCompanyName()); 
			
				airPickupbillEntity.setDestOrgCode(businessPartnerEntity.getAgentCompanyCode());
			}else{
				throw new  SettlementException("代理网点编号"+airPickupbillEntity.getDestOrgCode()+"没有对应的代理公司信息！");
			}				
		}else{
			airPickupbillEntity=new AirPickupbillEntity();
		}
		
		billReceivableAgencyVo.setAirPickupbillEntity(airPickupbillEntity);
		
		return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}

	
	/**
	 * 空运其他应收导出
	 * @author foss-pengzhen
	 * @date 2012-12-27 上午9:34:25
	 * @return
	 * @see
	 */
	public String airReceivableListExport(){
		//输入流
		ByteArrayOutputStream baos = null;
		try{
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setFileName(new String(("空运其他应收")
						.getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				//记录日志信息
				logger.error(e1.getMessage(),e1);
				//导出文件名编码转化错误！
				returnError("导出文件名编码转化错误！");
				setSuccess(true);
				setException(true);
				//返回异常信息
				return ERROR;
			}
			
			HSSFWorkbook wookBook = billReceivableAgencyService.airReceivableListExport(billReceivableAgencyDto, currentInfo);
			try {
				baos = new ByteArrayOutputStream();
				wookBook.write(baos);
				inputStream = new ByteArrayInputStream(baos.toByteArray()); 
			} catch (IOException e) {
				//记录日志信息
				logger.error(e.getMessage(),e);
				//生成excel流错误！
				returnError("生成excel流错误！");
				setSuccess(true);
				setException(true);
				//返回异常信息
				return ERROR;
			}
			
			return returnSuccess();
		}catch(BusinessException e){
			//记录日志信息
			logger.error(e.getMessage(),e);
			returnError(e);
			setSuccess(true);
			setException(true);
			//返回异常信息
			return ERROR;
		}finally{
			//关闭流
    		if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					//记录日志信息
					logger.error(e.getMessage(),e);
					//流关闭错误！
					returnError("流关闭错误！");
					setSuccess(true);
					setException(true);
					//返回异常信息
					return ERROR;
				}
			}
		}
	}
	
	
	
	
	/**
	 * 空运其他应收管理作废
	 * @author foss-pengzhen
	 * @date 2012-10-29 下午2:04:42
	 * @return
	 * @see
	 */
	@JSON
	public String writeBackAirOtherBillReceivable() {
		try {
			//获取集合参数
			List<BillReceivableAgencyDto> billReceivableAgencyDtos = billReceivableAgencyVo.getBillReceivableAgencyDtos();
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//空运其他应收作废service
			billReceivableAgencyService
					.writeBackAirOtherBillReceivable(billReceivableAgencyDtos,billReceivableAgencyDto,currentInfo);
			
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}
	
	/**
	 * 空运其他应收管理审核action
	 * @author foss-pengzhen
	 * @date 2012-10-29 下午2:04:42
	 * @return
	 * @see
	 */
	@JSON
	public String auditAirOtherBillReceivable() {
		try {
			//获取集合参数
			List<BillReceivableAgencyDto> billReceivableAgencyDtos = billReceivableAgencyVo.getBillReceivableAgencyDtos();
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//空运其他应收管理审核service
			billReceivableAgencyService
					.auditAirOtherBillReceivable(billReceivableAgencyDtos,
							billReceivableAgencyDto,currentInfo);
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}

	/**
	 * 反审核
	 * @author foss-pengzhen
	 * @date 2012-10-31 下午3:39:18
	 * @return
	 * @see
	 */
	public String reverseAuditAirOtherBillReceivable() {
		try {
			//获取集合参数
			List<BillReceivableAgencyDto> billReceivableAgencyDtos = billReceivableAgencyVo.getBillReceivableAgencyDtos();
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//空运其他应收管理反审核service
			billReceivableAgencyService.reverseAuditAirOtherBillReceivable(billReceivableAgencyDtos
					,billReceivableAgencyDto,currentInfo);
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}

	/**
	 * 新增空运其它应收款
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-29 下午5:09:03
	 * @return
	 * @see
	 */
	@JSON
	public String addBillReceivable() {
		try {
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			// 调用新增空运其它应收款service
			billReceivableAgencyService.addBillReceivable(billReceivableAgencyDto,currentInfo);
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}

	
	/**
	 * @return  the billReceivableAgencyVo
	 */
	public BillReceivableAgencyVo getBillReceivableAgencyVo() {
		return billReceivableAgencyVo;
	}

	
	/**
	 * @param billReceivableAgencyVo the billReceivableAgencyVo to set
	 */
	public void setBillReceivableAgencyVo(
			BillReceivableAgencyVo billReceivableAgencyVo) {
		this.billReceivableAgencyVo = billReceivableAgencyVo;
	}

	
	/**
	 * @param billReceivableAgencyService the billReceivableAgencyService to set
	 */
	public void setBillReceivableAgencyService(
			IBillReceivableAgencyService billReceivableAgencyService) {
		this.billReceivableAgencyService = billReceivableAgencyService;
	}

	
	/**
	 * @return  the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * @return  the inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * @param airAgencyCompanyService the airAgencyCompanyService to set
	 */
	public void setAirAgencyCompanyService(
			IAirAgencyCompanyService airAgencyCompanyService) {
		this.airAgencyCompanyService = airAgencyCompanyService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		WaybillManagerService = waybillManagerService;
	}

	public void setPointsSingleJointTicketService(
			IAirWaybillDetailService pointsSingleJointTicketService) {
		this.pointsSingleJointTicketService = pointsSingleJointTicketService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}


}
