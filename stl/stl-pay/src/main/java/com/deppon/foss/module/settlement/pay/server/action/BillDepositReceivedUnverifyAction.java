package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedUnverifyService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillDepositReceivedPayVo;

/**
 * 未核销预收账款报表
 * @author foss-pengzhen
 * @date 2012-11-22 下午4:29:07
 * @since
 * @version
 */
public class BillDepositReceivedUnverifyAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 747430662638946430L;

	/**
	 * 注入vo
	 */
	private BillDepositReceivedPayVo billDepositReceivedPayVo;
	
	/**
	 * 注入service
	 */
	private IBillDepositReceivedUnverifyService billDepositReceivedUnverifyService;
	
	/**
	 * 导出excel名称
	 */
	private String fileName;
	
	private ByteArrayInputStream inputStream;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillDepositReceivedUnverifyAction.class);
	
	/**
	 * 未核销预收账款报表查询 
	 * @author foss-pengzhen
	 * @date 2012-11-23 下午6:24:25
	 * @return
	 * @see
	 */
	@JSON
	public String queryDepositReceivedUnverifyPage(){
		try{
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo.getBillDepositReceivedPayDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//当业务时间不为空时，设置业务时间，按业务时间查询条件
			if (billDepositReceivedPayDto.getStartBusinessDate() != null
					&& billDepositReceivedPayDto.getEndBusinessDate() != null) {
				// 业务结束时间
				Date dateTemp = DateUtils.addDays(billDepositReceivedPayVo
						.getBillDepositReceivedPayDto().getEndBusinessDate(), 1);
				billDepositReceivedPayDto.setEndBusinessDate(dateTemp);
			}
			//当预收单号不为空，则按预收单号来查询
			else if (StringUtils
					.isNotEmpty(billDepositReceivedPayVo.getBillDepositReceivedPayDto().getDepositReceivedNo())) {
				String[] depositReceivedNos = billDepositReceivedPayVo.getBillDepositReceivedPayDto().getDepositReceivedNo().split(",|;");
    			List<String> depositReceivedNoList = Arrays.asList(depositReceivedNos);
    			billDepositReceivedPayDto.setDepositReceivedNos(depositReceivedNoList);
			}
			billDepositReceivedPayVo.setBillDepositReceivedPayDto(billDepositReceivedUnverifyService.
					queryDepositReceived(billDepositReceivedPayDto,currentInfo, getStart(), getLimit()));
			
			this.setTotalCount(billDepositReceivedPayDto.getTotalNum());
			return returnSuccess();
			
		}catch (BusinessException e) {
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}

	/**
	 * 导出预收单报表
	 * @author foss-pengzhen
	 * @date 2012-12-12 下午3:54:59
	 * @return
	 * @see
	 */
	public String depositReceivedUnverifyListExport(){
		//输入流
		ByteArrayOutputStream baos = null;
		try{
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo.getBillDepositReceivedPayDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setFileName(new String(("预收单报表")
						.getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage());
				returnError("导出文件名编码转化错误！");
				setSuccess(true);
				setException(true);
				return ERROR;
			}
			
			HSSFWorkbook wookBook = billDepositReceivedUnverifyService
					.depositReceivedUnverifyListExport(billDepositReceivedPayDto, currentInfo);
			try {
				baos = new ByteArrayOutputStream();
				wookBook.write(baos);
				inputStream = new ByteArrayInputStream(baos.toByteArray()); 
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
				returnError("生成excel流错误！");
				setSuccess(true);
				setException(true);
				return ERROR;
			}
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			returnError(e);
			setSuccess(true);
			setException(true);
			return ERROR;
		}finally{
			//关闭流
    		if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					returnError("流关闭错误！");
					setSuccess(true);
					setException(true);
					return ERROR;
				}
			}
		}
	}

	
	/**
	 * @return  the billDepositReceivedPayVo
	 */
	public BillDepositReceivedPayVo getBillDepositReceivedPayVo() {
		return billDepositReceivedPayVo;
	}

	
	/**
	 * @param billDepositReceivedPayVo the billDepositReceivedPayVo to set
	 */
	public void setBillDepositReceivedPayVo(
			BillDepositReceivedPayVo billDepositReceivedPayVo) {
		this.billDepositReceivedPayVo = billDepositReceivedPayVo;
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
	 * @param billDepositReceivedUnverifyService the billDepositReceivedUnverifyService to set
	 */
	public void setBillDepositReceivedUnverifyService(
			IBillDepositReceivedUnverifyService billDepositReceivedUnverifyService) {
		this.billDepositReceivedUnverifyService = billDepositReceivedUnverifyService;
	}
	
	
}
