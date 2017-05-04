package com.deppon.foss.module.settlement.pay.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillCashCashierConfirmPayService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashCashierConfirmDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillCashCashierConfirmVo;

/**
 * 确认收银Action
 * @author foss-pengzhen
 * @date 2012-12-13 下午2:13:45
 * @since
 * @version
 */
public class BillCashCashierConfirmPayAction extends AbstractAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2221725433727214774L;
	
	/**
	 * 部门名称
	 */
	private String billingGroup;
	
	/**
	 * 确认收银vo
	 */
	private BillCashCashierConfirmVo billCashCashierConfirmVo;
	
	/**
	 * 确认收银服务
	 */
	private IBillCashCashierConfirmPayService billCashCashierConfirmPayService;
	
	/**
	 * 用于int类型初始化常量
	 */
	
	private static final int INT_NUM = 0;
	/**
	 * Logger
	 */
	private static Logger logger = LogManager
			.getLogger(BillCashCashierConfirmPayAction.class);
	
	/**
	 * 导出excel名称
	 */
	private String fileName;
	
	 /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;
					
	/**
	 * 收银确认判断部门
	 * @author foss-pengzhen
	 * @date 2012-12-21 下午4:35:37
	 * @return
	 * @see
	 */
	@JSON
	public String initCashCashierConfirm(){
		try {
			//获取用户、部门信息
    		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
    		
    		billingGroup = billCashCashierConfirmPayService.deptJudge(currentInfo);
    		return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 查询收银信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午2:16:55
	 * @return
	 * @see
	 */
	@JSON
	public String queryCashCashierConfirmParams(){
		try {
			BillCashCashierConfirmDto billCashCashierConfirmDto = billCashCashierConfirmVo.getBillCashCashierConfirmDto();
			
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			List<BillCashCashierConfirmDto> billCashCashierConfirmDtos = billCashCashierConfirmPayService
					.queryCashCashierConfirmPags(billCashCashierConfirmDto, currentInfo);
			
			//声明变量来装需要统计的数据
			//总条数
			int totalNum = INT_NUM;
			//银行卡总条数
			int totalCardNum = INT_NUM;
			//现金总条数
			int totalCashNum = INT_NUM;
			//电汇总条数
			int totalTelegpaphTransferNum = INT_NUM;
			//总金额
			BigDecimal totalAmount = BigDecimal.ZERO;
			//现金总金额
			BigDecimal totalCashAmount = BigDecimal.ZERO;
			//银行卡总金额
			BigDecimal totalCardAmount = BigDecimal.ZERO;
			//电汇总金额
			BigDecimal totalTelegpaphTransferAmount = BigDecimal.ZERO;
			//循环拿数据中的付款状态，把现金、银行卡、汇款的票数给统计出来返回到界面
			for(BillCashCashierConfirmDto billDto : billCashCashierConfirmDtos){
				//统计银行卡票数和总金额
    			if(StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
    					, billDto.getPaymentType())){
    				totalCardNum ++ ;
    				totalCardAmount = totalCardAmount.add(billDto.getAmount());
    			}
    			//统计现金票数和总金额
    			if(StringUtils.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
    					, billDto.getPaymentType())){
    				totalCashNum ++ ;
    				totalCashAmount = totalCashAmount.add(billDto.getAmount());
    			}
    			//统计汇款票数(电汇和支票)和总金额
    			if(StringUtils.equals(SettlementDictionaryConstants
    					.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER, billDto.getPaymentType())
    					|| StringUtils.equals(SettlementDictionaryConstants
    							.SETTLEMENT__PAYMENT_TYPE__NOTE,  billDto.getPaymentType())
    							|| StringUtils.equals(SettlementDictionaryConstants
    	    							.SETTLEMENT__PAYMENT_TYPE__ONLINE,  billDto.getPaymentType())){
    				totalTelegpaphTransferNum ++ ;
    				totalTelegpaphTransferAmount = totalTelegpaphTransferAmount.add(billDto.getAmount());
    			}
			}
			//设置银行卡总条数、总金额
			billCashCashierConfirmDto.setTotalCardNum(totalCardNum);
			billCashCashierConfirmDto.setTotalCardAmount(totalCardAmount);
			//设置现金总条数、总金额
			billCashCashierConfirmDto.setTotalCashNum(totalCashNum);
			billCashCashierConfirmDto.setTotalCashAmount(totalCashAmount);
			//设置电汇总条数、总金额
			billCashCashierConfirmDto.setTotalTelegpaphTransferNum(totalTelegpaphTransferNum);
			billCashCashierConfirmDto.setTotalTelegpaphTransferAmount(totalTelegpaphTransferAmount);
			
			//设置总条数
			totalNum = totalCardNum + totalCashNum + totalTelegpaphTransferNum;
			billCashCashierConfirmDto.setTotalNum(totalNum);
			//设置总金额
			totalAmount = totalAmount.add(totalCardAmount);
			totalAmount = totalAmount.add(totalTelegpaphTransferAmount);
			totalAmount = totalAmount.add(totalCashAmount);
			billCashCashierConfirmDto.setTotalAmount(totalAmount);
			
			billCashCashierConfirmVo.setBillCashCashierConfirmDtos(billCashCashierConfirmDtos);
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}

	/**
	 * 查询运单号
	 * @author foss-pengzhen
	 * @date 2013-6-28 上午10:54:33
	 * @return
	 * @see
	 */
	@JSON
	public String cashCashierConfirmDetail(){
		try {
			//获取界面传入数据
			BillCashCashierConfirmDto billCashCashierConfirmDto = billCashCashierConfirmVo.getBillCashCashierConfirmDto();
			
			String billNo = billCashCashierConfirmDto.getBillNo();
			//调用接口获取运单号集合
			StringBuffer waybillNo = billCashCashierConfirmPayService.cashCashierConfirmDetailWaybillNo(billNo);
			//写入数据到对象并传到界面
			billCashCashierConfirmDto.setWaybillNo(waybillNo.toString());
			billCashCashierConfirmVo.setBillCashCashierConfirmDto(billCashCashierConfirmDto);
			return returnSuccess();
		}catch (BusinessException e) {
			logger.error(e.getMessage(), e);
		    return returnError(e);
		}
	}
	
	/**
	 * 导出现金收银列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:48:03
	 * @return
	 * @see
	 */
	public String billCashCashierConfirmsExport(){
		try {
			//导出文件名称
		    fileName = URLEncoder.encode("导出现金收银", "UTF-8");
    		// 获取mapper,进行前台转换
    		ObjectMapper mapper = new ObjectMapper();
    		// canshu界面获取
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<LinkedHashMap> billCashCashierConfirms = mapper.readValue((String) billCashCashierConfirmVo.getExportBillCashCashierConfirm(), List.class);
		    //获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		    //查询未确认收银的数据
			ExportResource exportResource = billCashCashierConfirmPayService.exportBillCashCashierConfirms(billCashCashierConfirms, currentInfo);
			//创建导出表头对象
		    ExportSetting exportSetting = new ExportSetting();
		    //设置名称
		    exportSetting.setSheetName("导出现金收银");
		    //创建导出工具类
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		    // 导出成文件
		    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);
		    return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
		    return returnError(e);
		}catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		    return returnError("UnsupportedEncodingException", e);
		}catch (Exception e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return ERROR;
		}
	}
	
	/**
	 * 收银确认提交
	 * @author foss-pengzhen
	 * @date 2012-12-17 下午2:18:24
	 * @return
	 * @see
	 */
	@JSON
	public String cashCashierConfirmCommit(){
		try{
			List<BillCashCashierConfirmDto> billCashCashierConfirmDtos = billCashCashierConfirmVo.getBillCashCashierConfirmDtos();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			//调用确认收银服务
			billCashCashierConfirmPayService.cashCashierConfirmCommit(billCashCashierConfirmDtos, currentInfo);
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}

    /**
     * 查询未收银的代收货款单据
     *
     *
     */
	public String queryUnconfirmedCodRelatedBill(){
        try {
            BillCashCashierConfirmDto dto = null;
            if (billCashCashierConfirmVo != null
                    && billCashCashierConfirmVo.getBillCashCashierConfirmDto() != null) {
                dto = billCashCashierConfirmVo.getBillCashCashierConfirmDto();
            } else {
                throw new SettlementException("查询参数dto为空");
            }

            if (dto.getCollectionOrgCode() == null) {
                throw new SettlementException("查询参数部门编码为空");
            }

            //查询
            List<String> unconfirmedCodRelatedBillList = billCashCashierConfirmPayService.queryUnconfirmedCodRelatedBill(dto);

            billCashCashierConfirmVo.setUnconfirmedCodRelatedBillList(unconfirmedCodRelatedBillList);
        }catch(BusinessException e){
            logger.error(e);
            return returnError(e);
        }

        return returnSuccess();
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
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return  the billingGroup
	 */
	public String getBillingGroup() {
		return billingGroup;
	}

	
	/**
	 * @param billingGroup the billingGroup to set
	 */
	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}

	/**
	 * @return  the billCashCashierConfirmVo
	 */
	public BillCashCashierConfirmVo getBillCashCashierConfirmVo() {
		return billCashCashierConfirmVo;
	}

	
	/**
	 * @param billCashCashierConfirmVo the billCashCashierConfirmVo to set
	 */
	public void setBillCashCashierConfirmVo(
			BillCashCashierConfirmVo billCashCashierConfirmVo) {
		this.billCashCashierConfirmVo = billCashCashierConfirmVo;
	}

	
	/**
	 * @param billCashCashierConfirmPayService the billCashCashierConfirmPayService to set
	 */
	public void setBillCashCashierConfirmPayService(
			IBillCashCashierConfirmPayService billCashCashierConfirmPayService) {
		this.billCashCashierConfirmPayService = billCashCashierConfirmPayService;
	}
	
}
