package com.deppon.foss.module.settlement.agency.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.deppon.foss.module.settlement.agency.api.server.service.IBillPAPayableAgencyService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.settlement.agency.api.shared.vo.BillPayableAgencyVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 查询_审核_作废偏线其它应付
 * @author foss-guxinhua
 * @date 2012-12-5 上午11:02:39
 * @since
 * @version
 */
public class BillPAPayableAgencyAction extends AbstractAction{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1226919741726839208L;

	/**
	 * 应付单VO
	 */
	private BillPayableAgencyVo billPayableAgencyVo;
	
	/**
	 * 注入service
	 */
	private IBillPAPayableAgencyService billPAPayableAgencyService;
	
	/**
	 * 导出excel名称
	 */
	private String fileName;
	
	/**
	 * 流
	 */
	private ByteArrayInputStream inputStream;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillPAPayableAgencyAction.class);
	
	/**
	 * 偏线其他应付管理的分页查询
	 * 开始时间和结束时间相差不能大于一个月
	 * 用户只能查询到当前登录用户下属部门信息
	 * 输入单号的个数不能为空
	 * 输入的单号的个数不能超过10个
	 * 当登陆用户只能查询所属部门的偏线其它应付单信息
	 * 偏线其它应付单必须为有效、非红单的数据
	 * @author foss-guxinhua
	 * @date 2012-11-6 下午3:46:32
	 */
	@JSON
	public String queryPAPayablePage(){
		try {
			//获取界面传入的参数
			BillPayableAgencyDto billPayableAgencyDto = billPayableAgencyVo
					.getBillPayableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 如果时间不为空，则按时间查询，单号不为空就按单号查询，否则按航空正单号查询
			//根据传入获取一到多条应付单信息
			if (billPayableAgencyDto.getStartBusinessDate() != null
					&& billPayableAgencyDto.getEndBusinessDate() != null) {

				// 业务结束时间
				Date dateTemp = DateUtils.addDays(billPayableAgencyVo
						.getBillPayableAgencyDto().getEndBusinessDate(), 1);
				billPayableAgencyDto.setEndBusinessDate(dateTemp);
			} else if (StringUtils.isNotEmpty(billPayableAgencyVo.getBillPayableAgencyDto().getPayableNo())) {
				//根据传入的一到多个应付单号，获取一到多条应付单信息
				// 获取应付单号并分割处理
				List<String> payablesNoList = new ArrayList<String>();
				String regx = "YF[0-9]{8,}";
				Pattern p = Pattern.compile(regx);
				Matcher m = p.matcher(billPayableAgencyVo.getBillPayableAgencyDto().getPayableNo());
				while(m.find()){
					payablesNoList.add(m.group());
				}
    			//设置单号集合到对象中
    			billPayableAgencyDto.setPayableNos(payablesNoList);
			} else if (StringUtils.isNotEmpty(billPayableAgencyVo
					.getBillPayableAgencyDto().getSourceBillNo())) {
				//根据传入的一到多个单号，获取一到多条应付单信息
				List<String> sourceBillNoList = new ArrayList<String>();
				String regx = "[0-9]{8,}";
				Pattern p = Pattern.compile(regx);
				Matcher m = p.matcher(billPayableAgencyVo.getBillPayableAgencyDto().getSourceBillNo());
				while(m.find()){
					sourceBillNoList.add(m.group());
				}
    			//设置单号集合到对象中
    			billPayableAgencyDto.setSourceBillNos(sourceBillNoList);
			}
			//偏线其他管理分页查询
			//设置数据到dto
			billPayableAgencyVo.setBillPayableAgencyDto(billPAPayableAgencyService
					.queryPAPayablePage(billPayableAgencyVo
							.getBillPayableAgencyDto(), getStart(),getLimit(),currentInfo));
			//设置总条数
			this.setTotalCount(billPayableAgencyDto.getTotalNum());
			return returnSuccess();
		} catch (BusinessException e) {
			//写入日志
			logger.error(e.getMessage(),e);
			//返回字符串到界面
			String error = returnError(e);
			//写入参数
			setSuccess(true);
			//写入参数
			setException(true);
			//返回异常信息
			return error;
		}
	}
	
	/**
	 * 偏线其他应付导出
	 * 调用导出数据接口，
	 * 导出相对应的偏线其它应付单信息（全部字段全部信息）
	 * 
	 * 导出完毕
	 * 	自动跳转到下载界面（新打开窗口）
	 * @author foss-guxinhua
	 * @date 2012-12-27 上午11:47:20
	 * @return
	 * @see
	 */
	public String PAPayableListExport(){
		//输入流
		ByteArrayOutputStream baos = null;
		try{
			BillPayableAgencyDto billPayableAgencyDto = billPayableAgencyVo.getBillPayableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setFileName(new String(("偏线其他应付")
						.getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage(),e1);
				returnError("导出文件名编码转化错误！");
				//写入参数
				setSuccess(true);
				//写入参数
				setException(true);
				//返回异常信息
				return ERROR;
			}
			//偏线其他应收单导出
			HSSFWorkbook wookBook = billPAPayableAgencyService.PAPayableListExport(billPayableAgencyDto, currentInfo);
			try {
				//实例对象流
				baos = new ByteArrayOutputStream();
				wookBook.write(baos);
				inputStream = new ByteArrayInputStream(baos.toByteArray()); 
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
				returnError("生成excel流错误！");
				//写入参数
				setSuccess(true);
				//写入参数
				setException(true);
				//返回异常信息
				return ERROR;
			}
			
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			returnError(e);
			//写入参数
			setSuccess(true);
			//写入参数
			setException(true);
			//返回异常信息
			return ERROR;
		}finally{
			//关闭流
    		if(baos!=null){
				try {
					//关闭流
					baos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					//流关闭错误！
					returnError("流关闭错误！");
					//写入参数
					setSuccess(true);
					//写入参数
					setException(true);
					//返回异常信息
					return ERROR;
				}
			}
		}
	}
	
	/**
	 * 偏线其他应付作废
	 * @author foss-guxinhua
	 * @date 2012-11-8 上午9:34:34
	 * @return
	 * @see
	 */
	@JSON
	public String writeBackPAOtherBillPayable(){
		try {
			//获取集合参数
			List<BillPayableAgencyDto> billPayableAgencyDtos = billPayableAgencyVo.getBillPayableAgencyDtos();
			//获取参数
			BillPayableAgencyDto billPayableAgencyDto = 
					billPayableAgencyVo.getBillPayableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//偏线其他应付管理审核service
			billPAPayableAgencyService.writeBackPAOtherBillPayable(billPayableAgencyDtos,
					billPayableAgencyDto,currentInfo);
			
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}
	/**
	 * 偏线其它应付审核
	 * 
	 * 已审核的单据不能被重复审核
	 * 已审核的单据不能被作废
	 * @author foss-guxinhua
	 * @date 2012-11-7 下午2:26:59
	 * @return
	 * @see
	 */
	@JSON
	public String auditPAOtherBillPayable(){
		try {
			//获取集合参数
			List<BillPayableAgencyDto> billPayableAgencyDtos = billPayableAgencyVo.getBillPayableAgencyDtos();
			//获取参数
			BillPayableAgencyDto billPayableAgencyDto = 
					billPayableAgencyVo.getBillPayableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//偏线其他应付管理审核service
			billPAPayableAgencyService.auditPAOtherBillPayable(billPayableAgencyDtos,
					billPayableAgencyDto,currentInfo);
			
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}
	
	/**
	 * 偏线其它应付反审核
	 * 已参与过核销的单据不能进行反审核操作
	 * 未审核的单据不能被反审核
s	 * 已申请付款或冻结中的单据不能进行反审核操作
	 * @author foss-guxinhua
	 * @date 2012-11-7 下午2:26:59
	 * @return String
	 */
	@JSON
	public String reverseAuditPAOtherBillPayable(){
		try {
			//获取集合参数
			List<BillPayableAgencyDto> billPayableAgencyDtos = billPayableAgencyVo.getBillPayableAgencyDtos();
			//获取参数
			BillPayableAgencyDto billPayableAgencyDto = 
					billPayableAgencyVo.getBillPayableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//偏线其他应付管理审核service
			billPAPayableAgencyService.reverseAuditPAOtherBillPayable(billPayableAgencyDtos,
					billPayableAgencyDto,currentInfo);
			
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 新增偏线其它应付款
	 * @author foss-guxinhua
	 * @date 2012-11-7 上午11:29:06
	 * @return
	 * @see
	 */
	@JSON
	public String addPABillPayable(){
		try {
			// 传入对象非空判断
			if (null == billPayableAgencyVo) {
				// 如果没有值则抛出异常
				throw new SettlementException("传入参数不能为空");
			 }
			//获取参数
			BillPayableAgencyDto billPayableAgencyDto = billPayableAgencyVo
					.getBillPayableAgencyDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			// 调用新增偏线其它应付款service
			billPAPayableAgencyService.addBillPayable(billPayableAgencyDto,currentInfo);
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(),e);
			//返回异常信息
			return returnError(e);
		}
	}

	
	/**
	 * @return  the billPayableAgencyVo
	 */
	public BillPayableAgencyVo getBillPayableAgencyVo() {
		return billPayableAgencyVo;
	}

	
	/**
	 * @param billPayableAgencyVo the billPayableAgencyVo to set
	 */
	public void setBillPayableAgencyVo(BillPayableAgencyVo billPayableAgencyVo) {
		this.billPayableAgencyVo = billPayableAgencyVo;
	}

	
	/**
	 * @param billPayableAgencyService the billPayableAgencyService to set
	 */
	public void setBillPAPayableAgencyService(
			IBillPAPayableAgencyService billPAPayableAgencyService) {
		this.billPAPayableAgencyService = billPAPayableAgencyService;
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
