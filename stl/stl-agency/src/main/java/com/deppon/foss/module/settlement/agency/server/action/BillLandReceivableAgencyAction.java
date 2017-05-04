package com.deppon.foss.module.settlement.agency.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.IBillLandReceivableAgencyService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.settlement.agency.api.shared.vo.BillReceivableAgencyVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;

/**
 * 查询_审核_作废快递代理其它应收
 * 
 * @author foss-guxinhua
 * @date 2012-10-26 上午11:31:54
 * @since
 * @version
 */
public class BillLandReceivableAgencyAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7836387691605246897L;

	/**
	 * 应收单VO
	 */
	private BillReceivableAgencyVo billReceivableAgencyVo;

	/**
	 * 注入service
	 */
	private IBillLandReceivableAgencyService billLandReceivableAgencyService;

	/**
	 * 导出excel名称
	 */
	private String fileName;
	
	private ByteArrayInputStream inputStream;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillLandReceivableAgencyAction.class);
	
	/**
	 * 快递代理其他应收管理的分页查询
	 * 
	 * @author foss-guxinhua
	 * @date 2012-10-31 下午5:32:13
	 * @return
	 * @see
	 */
	@JSON
	public String queryLandReceivablePage() {
		try {
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//快递代理其他管理分页查询
			billReceivableAgencyVo.setBillReceivableAgencyDto(billLandReceivableAgencyService
					.queryLandReceivablePage(billReceivableAgencyDto, getStart(),getLimit(),currentInfo));
			
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
	 * 快递代理其他应收导出
	 * @author foss-guxinhua
	 * @date 2012-12-27 上午9:34:25
	 * @return
	 * @see
	 */
	public String landReceivableListExport(){
		//输入流
		ByteArrayOutputStream baos = null;
		try{
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setFileName(new String(("快递代理其他应收")
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
			
			HSSFWorkbook wookBook = billLandReceivableAgencyService.landReceivableListExport(billReceivableAgencyDto, currentInfo);
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
	 * 快递代理其他应收管理作废
	 * @author foss-guxinhua
	 * @date 2012-10-29 下午2:04:42
	 * @return
	 * @see
	 */
	@JSON
	public String writeBackLandOtherBillReceivable() {
		try {
			//获取集合参数
			List<BillReceivableAgencyDto> billReceivableAgencyDtos = billReceivableAgencyVo.getBillReceivableAgencyDtos();
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//快递代理其他应收作废service
			billLandReceivableAgencyService
					.writeBackLandOtherBillReceivable(billReceivableAgencyDtos,billReceivableAgencyDto,currentInfo);
			
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志信息
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}
	
	/**
	 * 快递代理其他应收管理审核action
	 * @author foss-guxinhua
	 * @date 2012-10-29 下午2:04:42
	 * @return
	 * @see
	 */
	@JSON
	public String auditLandOtherBillReceivable() {
		try {
			//获取集合参数
			List<BillReceivableAgencyDto> billReceivableAgencyDtos = billReceivableAgencyVo.getBillReceivableAgencyDtos();
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//快递代理其他应收管理审核service
			billLandReceivableAgencyService
					.auditLandOtherBillReceivable(billReceivableAgencyDtos,
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
	 * @author foss-guxinhua
	 * @date 2012-10-31 下午3:39:18
	 * @return
	 * @see
	 */
	public String reverseAuditLandOtherBillReceivable() {
		try {
			//获取集合参数
			List<BillReceivableAgencyDto> billReceivableAgencyDtos = billReceivableAgencyVo.getBillReceivableAgencyDtos();
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//快递代理其他应收管理反审核service
			billLandReceivableAgencyService.reverseAuditLandOtherBillReceivable(billReceivableAgencyDtos
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
	 * 新增快递代理其它应收款
	 * 
	 * @author foss-guxinhua
	 * @date 2012-10-29 下午5:09:03
	 * @return
	 * @see
	 */
	@JSON
	public String addLandBillReceivable() {
		try {
			//获取参数
			BillReceivableAgencyDto billReceivableAgencyDto = billReceivableAgencyVo
					.getBillReceivableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			// 调用新增快递代理其它应收款service
			billLandReceivableAgencyService.addBillReceivable(billReceivableAgencyDto,currentInfo);
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
	public void setBillLandReceivableAgencyService(
			IBillLandReceivableAgencyService billLandReceivableAgencyService) {
		this.billLandReceivableAgencyService = billLandReceivableAgencyService;
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

	

}
