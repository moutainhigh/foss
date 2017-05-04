package com.deppon.foss.module.settlement.agency.server.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.IBillRecAndPayImportService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillRecAndPayImportDto;
import com.deppon.foss.module.settlement.agency.api.shared.vo.BillRecAndPayImportVo;

/**
 * 
 * 导入空运应收、应付的信息
 * @author foss-pengzhen
 * @date 2012-11-12 上午11:39:51
 * @since
 * @version
 */
public class BillRecAndPayImportAction extends AbstractAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2169290356977785172L;

	/**
	 * 注入导入空运应收、应付的Vo
	 */
	private BillRecAndPayImportVo billRecAndPayImportVo;
	
	/**
	 * 注入导入空运应收、应付的service
	 */
	private IBillRecAndPayImportService billRecAndPayImportService;
	
	/**
	 * 异常信息
	 */
	private String errorMessage;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillRecAndPayImportAction.class);
	
	/**
	 * 导入应收、应付信息
	 * @author foss-pengzhen
	 * @date 2012-11-12 上午11:39:31
	 * @return
	 * @throws Exception 
	 * @see
	 */
	public String importExcel() throws Exception{
		try{
			//获取并设置应收应付的信息
			billRecAndPayImportVo.setBillRecAndPayImportDto(billRecAndPayImportService.
					importRecAndPaylist(billRecAndPayImportVo.getBillRecAndPayImportDto().getFile(),
					billRecAndPayImportVo.getBillRecAndPayImportDto().getFileName()));
			return SUCCESS;
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			//错误编码
			errorMessage = e.getErrorCode();
			returnError(e);
			setSuccess(true);
			setException(false);
			return ERROR;
		}
		
	}
	
	/**
	 * 保存应收、应付信息
	 * @author foss-pengzhen
	 * @date 2012-11-13 下午3:06:33
	 * @return
	 * @see
	 */
	@JSON
	public String saveAirImportPayAndRec(){
		try{
			//获取参数
			BillRecAndPayImportDto billRecAndPayImportDto = new BillRecAndPayImportDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			billRecAndPayImportDto.setBillRecAndPayImportDtoList(billRecAndPayImportVo.getRecAndPaylist());
			//保存应收信息、应付信息
    	    billRecAndPayImportService.saveAirImportPayAndRec(billRecAndPayImportDto,currentInfo);
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
		return returnSuccess();
	}

	
	/**
	 * @return  the billRecAndPayImportVo
	 */
	public BillRecAndPayImportVo getBillRecAndPayImportVo() {
		return billRecAndPayImportVo;
	}

	
	/**
	 * @param billRecAndPayImportVo the billRecAndPayImportVo to set
	 */
	public void setBillRecAndPayImportVo(BillRecAndPayImportVo billRecAndPayImportVo) {
		this.billRecAndPayImportVo = billRecAndPayImportVo;
	}

	
	/**
	 * @param billRecAndPayImportService the billRecAndPayImportService to set
	 */
	public void setBillRecAndPayImportService(
			IBillRecAndPayImportService billRecAndPayImportService) {
		this.billRecAndPayImportService = billRecAndPayImportService;
	}

	/**
	 * @GET
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		/*
		 *@get
		 *@ return errorMessage
		 */
		return errorMessage;
	}

	/**
	 * @SET
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		/*
		 *@set
		 *@this.errorMessage = errorMessage
		 */
		this.errorMessage = errorMessage;
	}
	
	
}
