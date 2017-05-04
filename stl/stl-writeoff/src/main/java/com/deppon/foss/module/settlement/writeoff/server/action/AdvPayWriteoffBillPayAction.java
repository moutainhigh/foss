/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IAdvPayWriteoffBillPayService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.AdvPayWriteoffBillPayDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.AdvPayWriteoffBillPayVo;

/**
 * 空运预付冲应付
 * 
 * @author foss-pengzhen
 * @date 2012-10-12 下午5:52:24
 * @since
 * @version
 */
public class AdvPayWriteoffBillPayAction extends AbstractAction {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -5201072748318982370L;

	/**
	 * 预付注入参数
	 */
	private AdvPayWriteoffBillPayVo advPayWriteoffBillPayVo = new AdvPayWriteoffBillPayVo();

	/**
	 * 待核销预收单、应收单进行查询、核销等操作service
	 */
	private IAdvPayWriteoffBillPayService advPayWriteoffBillPayService;

	/**
	 * 导出excel名称
	 */
	private String fileName;
	
	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream inputStream;
	
	/**
	 * 用于int类型初始化常量
	 */
	private static final int INT_NUM = 0;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(AdvPayWriteoffBillPayAction.class);

	/**
	 * 根据传入的一到多个预付、应付单号，获取一到多条预付、应付单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2013-3-19 上午10:07:33
	 * @param null
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@JSON
	public String queryPaymentPayableNos() {
		try {
			//获取输入参数dto
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto = advPayWriteoffBillPayVo.getAdvPayWriteoffBillPayDto();
			// 获取当前的部门信息
			CurrentInfo currentinfo = FossUserContext.getCurrentInfo();
			// 获取预付单号并分割处理
			if (StringUtils.isNotEmpty(advPayWriteoffBillPayDto.getAdvancesNo())) {
				
				//如果预付单号不为空，将预付单号以,或;分割成数组
				String[] advancesNos = advPayWriteoffBillPayDto.getAdvancesNo()
						.split(",|;");
				
				//将预付单号数字转化成list
				List<String> advancesNoList = Arrays.asList(advancesNos);
				
				//设置预付单号查询参数
				advPayWriteoffBillPayDto.setAdvancesNos(advancesNoList);
			}
			// 获取应付单号并分割处理
			if (StringUtils.isNotEmpty(advPayWriteoffBillPayDto.getPayableNo())) {
				
				//如果应付单号不为空，将应付单号以,或;分割成数组
				String[] payablesNos = advPayWriteoffBillPayDto.getPayableNo()
						.split(",|;");
				
				//将应付单号数字转化成list
				List<String> payablesNoList = Arrays.asList(payablesNos);
				
				//设置应付单号查询参数
				advPayWriteoffBillPayDto.setPayableNos(payablesNoList);
			}

			// 根据预付单号获取用于可用于核销的预付单信息
			List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityList = advPayWriteoffBillPayService.queryBillAdvancedPaymentNos(advPayWriteoffBillPayDto,currentinfo);

			// 根据应付单号获取用于可用于核销的应付单信息
			List<BillPayableEntity> billPayableEntityList = advPayWriteoffBillPayService.queryPayableNOs(advPayWriteoffBillPayDto, currentinfo);
			
			//如果预付单不为空
			if (CollectionUtils.isNotEmpty(billAdvancedPaymentEntityList)) {
				//统计总条数传回界面
				int advPayNum = INT_NUM;
				//统计总金额传回界面
				BigDecimal advPayAmount = BigDecimal.ZERO;
				//统计总已核销金额传回界面
				BigDecimal advPayVerifyAmount = BigDecimal.ZERO;
				//统计总未核销金额传回界面
				BigDecimal advPayUnVerifyAmount = BigDecimal.ZERO;
				//循环统计条数、金额信息
				for(BillAdvancedPaymentEntity advancedPaymentEntity : billAdvancedPaymentEntityList){
					//统计总条数
					advPayNum++;
					//统计总金额
					advPayAmount = advPayAmount.add(advancedPaymentEntity.getAmount());
					//统计总已核销金额
					advPayVerifyAmount = advPayVerifyAmount.add(advancedPaymentEntity.getVerifyAmount());
					//统计总未核销金额
					advPayUnVerifyAmount = advPayUnVerifyAmount.add(advancedPaymentEntity.getUnverifyAmount());
				}
				//设置总条数
				advPayWriteoffBillPayDto.setAdvPayNum(advPayNum);
				//设置总金额
				advPayWriteoffBillPayDto.setAdvPayAmount(advPayAmount);
				//设置总已核销金额
				advPayWriteoffBillPayDto.setAdvPayVerifyAmount(advPayVerifyAmount);
				//设置总未核销金额
				advPayWriteoffBillPayDto.setAdvPayUnVerifyAmount(advPayUnVerifyAmount);
				
				
				//把获取到的信息方进VO中
				advPayWriteoffBillPayDto.setBillAdvancedPaymentEntityList(billAdvancedPaymentEntityList);
			}
			
			//如果应付单不为空
			if (CollectionUtils.isNotEmpty(billPayableEntityList)) {
				//统计总条数金额传回界面
				int billPayNum = INT_NUM;
				//统计总金额传回界面
				BigDecimal billPayAmount = BigDecimal.ZERO;
				//统计总已核销金额传回界面
				BigDecimal billPayVerifyAmount = BigDecimal.ZERO;
				//统计总未核销金额传回界面
				BigDecimal billPayUnVerifyAmount = BigDecimal.ZERO;
				//循环统计条数、金额信息
				for(BillPayableEntity billPayableEntity : billPayableEntityList){
					//统计总条数
					billPayNum++;
					//统计总金额
					billPayAmount = billPayAmount.add(billPayableEntity.getAmount());
					//统计总已核销金额
					billPayVerifyAmount = billPayVerifyAmount.add(billPayableEntity.getVerifyAmount());
					//统计总未核销金额
					billPayUnVerifyAmount = billPayUnVerifyAmount.add(billPayableEntity.getUnverifyAmount());
				}
				//设置总条数
				advPayWriteoffBillPayDto.setBillPayNum(billPayNum);
				//设置总金额
				advPayWriteoffBillPayDto.setBillPayAmount(billPayAmount);
				//设置总总已核销金额
				advPayWriteoffBillPayDto.setBillPayVerifyAmount(billPayVerifyAmount);
				//设置总未核销金额
				advPayWriteoffBillPayDto.setBillPayUnVerifyAmount(billPayUnVerifyAmount);
				
				//把获取到的信息方进VO中
				advPayWriteoffBillPayDto.setBillPayableEntityList(billPayableEntityList);
			}
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.info(e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 根据传入获取一到多条预付单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2013-3-19 上午10:08:03
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see 
	 */
	@JSON
	public String queryPaymentPayableParams() {
		try {
			//获取输入参数dto
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto = advPayWriteoffBillPayVo.getAdvPayWriteoffBillPayDto();
			// 获取当前的部门信息
			CurrentInfo currentinfo = FossUserContext.getCurrentInfo();

			// 根据预付单号获取用于可用于核销的预付单信息
			List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityList = advPayWriteoffBillPayService.queryBillAdvancedPaymentParams(advPayWriteoffBillPayDto,currentinfo);

			// 根据应付单号获取用于可用于核销的应付单信息
			List<BillPayableEntity> billPayableEntityList = advPayWriteoffBillPayService.queryPayableParams(advPayWriteoffBillPayDto, currentinfo);
			// 把获取到的信息方进VO中
			if (CollectionUtils.isNotEmpty(billAdvancedPaymentEntityList)) {
				//统计总条数传回界面
				int advPayNum = INT_NUM;
				//统计总金额传回界面
				BigDecimal advPayAmount = BigDecimal.ZERO;
				//统计总已核销金额传回界面
				BigDecimal advPayVerifyAmount = BigDecimal.ZERO;
				//统计总未核销金额传回界面
				BigDecimal advPayUnVerifyAmount = BigDecimal.ZERO;
				//循环统计条数、金额信息
				for(BillAdvancedPaymentEntity advancedPaymentEntity : billAdvancedPaymentEntityList){
					//统计总条数
					advPayNum++;
					//统计总金额
					advPayAmount = advPayAmount.add(advancedPaymentEntity.getAmount());
					//统计总已核销金额
					advPayVerifyAmount = advPayVerifyAmount.add(advancedPaymentEntity.getVerifyAmount());
					//统计总未核销金额
					advPayUnVerifyAmount = advPayUnVerifyAmount.add(advancedPaymentEntity.getUnverifyAmount());
				}
				//设置总条数
				advPayWriteoffBillPayDto.setAdvPayNum(advPayNum);
				//设置总金额
				advPayWriteoffBillPayDto.setAdvPayAmount(advPayAmount);
				//设置总已核销金额
				advPayWriteoffBillPayDto.setAdvPayVerifyAmount(advPayVerifyAmount);
				//设置总未核销金额
				advPayWriteoffBillPayDto.setAdvPayUnVerifyAmount(advPayUnVerifyAmount);
				//设置dto参数给Vo
				advPayWriteoffBillPayDto.setBillAdvancedPaymentEntityList(billAdvancedPaymentEntityList);
			}
			//如果应收单列表不为空
			if (CollectionUtils.isNotEmpty(billPayableEntityList)) {
				//统计总条数金额传回界面
				int billPayNum = INT_NUM;
				//统计总金额传回界面
				BigDecimal billPayAmount = BigDecimal.ZERO;
				//统计总已核销金额传回界面
				BigDecimal billPayVerifyAmount = BigDecimal.ZERO;
				//统计总未核销金额传回界面
				BigDecimal billPayUnVerifyAmount = BigDecimal.ZERO;
				//循环统计条数、金额信息
				for(BillPayableEntity billPayableEntity : billPayableEntityList){
					//统计总条数
					billPayNum++;
					//统计总金额
					billPayAmount = billPayAmount.add(billPayableEntity.getAmount());
					//统计总已核销金额
					billPayVerifyAmount = billPayVerifyAmount.add(billPayableEntity.getVerifyAmount());
					//统计总未核销金额
					billPayUnVerifyAmount = billPayUnVerifyAmount.add(billPayableEntity.getUnverifyAmount());
				}
				//设置总条数
				advPayWriteoffBillPayDto.setBillPayNum(billPayNum);
				//设置总金额
				advPayWriteoffBillPayDto.setBillPayAmount(billPayAmount);
				//设置总总已核销金额
				advPayWriteoffBillPayDto.setBillPayVerifyAmount(billPayVerifyAmount);
				//设置总未核销金额
				advPayWriteoffBillPayDto.setBillPayUnVerifyAmount(billPayUnVerifyAmount);
				//设置dto参数给Vo
				advPayWriteoffBillPayDto.setBillPayableEntityList(billPayableEntityList);
			}
			//正常返回
			return returnSuccess();
			//异常处理
		} catch (BusinessException e) {
			//记录日志
			logger.info(e);
			//异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 核销预付导出
	 *
	 * @author foss-pengzhen
	 * @date 2013-3-19 上午10:09:36
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	public String advancedListExport(){
		//输入流
		ByteArrayOutputStream baos = null;
		try{
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto = advPayWriteoffBillPayVo.getAdvPayWriteoffBillPayDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setFileName(new String((" 核销预付单").getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
				//异常处理
			} catch (UnsupportedEncodingException e1) {
				//记录日志
				logger.error(e1.getMessage(),e1);
				//异常信息
				returnError("导出文件名编码转化错误！");
				//设置错误标志
				setSuccess(true);
				//设置异常标志
				setException(true);
				//错误返回
				return ERROR;
			}
			
			// 导出预付单
			HSSFWorkbook wookBook = advPayWriteoffBillPayService.advancedListExport(advPayWriteoffBillPayDto, currentInfo);
			try {
				//新建输出流
				baos = new ByteArrayOutputStream();
				//写数据量
				wookBook.write(baos);
				//新建导出excel的输入流
				inputStream = new ByteArrayInputStream(baos.toByteArray()); 
				
			//异常处理
			} catch (IOException e) {
				//记录日志
				logger.error(e.getMessage(),e);
				//异常信息
				returnError("生成excel流错误！");
				//设置错误标志
				setSuccess(true);
				//设置异常标志
				setException(true);
				//错误返回
				return ERROR;
			}
			
			//正常返回
			return returnSuccess();
		//异常处理	
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getMessage(),e);
			//异常信息
			returnError(e);
			//设置错误标志
			setSuccess(true);
			//设置异常标志
			setException(true);
			//错误返回
			return ERROR;
		}finally{
			//关闭流
    		if(baos!=null){
				try {
					//关闭流
					baos.close();
				} catch (IOException e) {
					//记录日志
					logger.error(e.getMessage(),e);
					//异常信息
					returnError("流关闭错误！");
					//设置错误标志
					setSuccess(true);
					//设置异常标志
					setException(true);
					//错误返回
					return ERROR;
				}
			}
		}
	}
	
	/*
	1、	预付单和应付单导出的数据必须为当前查询出的数据
	2、  分页查询时，导出的数据为全部
	3、  导出实现
	 */
	/**
	 * 核销应付导出
	 *
	 * @author foss-pengzhen
	 * @date 2013-3-19 上午10:09:52
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	public String payableListExport(){
		//输入流
		ByteArrayOutputStream baos = null;
		try{
			//获取查询参数Dto
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto = advPayWriteoffBillPayVo.getAdvPayWriteoffBillPayDto();
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setFileName(new String(("核销应付单").getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			//异常处理
			} catch (UnsupportedEncodingException e1) {
				//记录日志
				logger.error(e1.getMessage(),e1);
				//异常信息
				returnError("导出文件名编码转化错误！");
				//设置错误标志
				setSuccess(true);
				//设置异常标志
				setException(true);
				//错误返回
				return ERROR;
			}
			// 导出预付单
			HSSFWorkbook wookBook = advPayWriteoffBillPayService.payableListExport(advPayWriteoffBillPayDto, currentInfo);
			try {
				//新建输出流
				baos = new ByteArrayOutputStream();
				//写输出流
				wookBook.write(baos);
				//新建导出excel输入流
				inputStream = new ByteArrayInputStream(baos.toByteArray()); 
			//异常处理	
			} catch (IOException e) {
				//记录日志
				logger.error(e.getMessage(),e);
				//异常信息
				returnError("生成excel流错误！");
				//设置错误标志
				setSuccess(true);
				//设置异常标志
				setException(true);
				//错误返回
				return ERROR;
			}
			//正常返回
			return returnSuccess();
		//异常处理	
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getMessage(),e);
			//异常信息
			returnError(e);
			//设置错误标志
			setSuccess(true);
			//设置异常标志
			setException(true);
			//错误返回
			return ERROR;
		}finally{
			//关闭流
    		if(baos!=null){
				try {
					//关闭流
					baos.close();
				} catch (IOException e) {
					//记录日志
					logger.error(e.getMessage(),e);
					//异常信息
					returnError("流关闭错误！");
					//设置错误标志
					setSuccess(true);
					//设置异常标志
					setException(true);
					//错误返回
					return ERROR;
				}
			}
		}
	}
	
	
	/**
	 * 预付冲应付
	 *
	 * @author foss-pengzhen
	 * @date 2013-3-19 上午10:10:04
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@JSON
	public String writeoffAdvancedAndPayable() {
		try {
			//获取查询参数Dto
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto = advPayWriteoffBillPayVo.getAdvPayWriteoffBillPayDto();
			// 获取界面选择的待核销的预付单单据
			advPayWriteoffBillPayDto.setBillAdvancedPaymentEntityList(advPayWriteoffBillPayVo.getAdvancedPaymentEntities());
			// 获取界面选择的待核销的应付付单单据
			advPayWriteoffBillPayDto.setBillPayableEntityList(advPayWriteoffBillPayVo.getBillPayableEntities());

			// 获取当前的部门信息
			CurrentInfo currentinfo = FossUserContext.getCurrentInfo();

			//预付冲应付，并设置返回值
			advPayWriteoffBillPayVo.setAdvPayWriteoffBillPayDto( advPayWriteoffBillPayService.writeoffAdvancedAndPayable(advPayWriteoffBillPayDto,currentinfo));
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.info(e);
			//异常返回
			return returnError(e);
		}
	}


	
	/**
	 * @get
	 * @return advPayWriteoffBillPayVo
	 */
	public AdvPayWriteoffBillPayVo getAdvPayWriteoffBillPayVo() {
		/*
		 * @get
		 * @return advPayWriteoffBillPayVo
		 */
		return advPayWriteoffBillPayVo;
	}


	
	/**
	 * @set
	 * @param advPayWriteoffBillPayVo
	 */
	public void setAdvPayWriteoffBillPayVo(AdvPayWriteoffBillPayVo advPayWriteoffBillPayVo) {
		/*
		 *@set
		 *@this.advPayWriteoffBillPayVo = advPayWriteoffBillPayVo
		 */
		this.advPayWriteoffBillPayVo = advPayWriteoffBillPayVo;
	}


	
	/**
	 * @get
	 * @return fileName
	 */
	public String getFileName() {
		/*
		 * @get
		 * @return fileName
		 */
		return fileName;
	}


	
	/**
	 * @set
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		/*
		 *@set
		 *@this.fileName = fileName
		 */
		this.fileName = fileName;
	}


	
	/**
	 * @get
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		/*
		 * @get
		 * @return inputStream
		 */
		return inputStream;
	}


	
	/**
	 * @set
	 * @param inputStream
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}


	
	/**
	 * @set
	 * @param advPayWriteoffBillPayService
	 */
	public void setAdvPayWriteoffBillPayService(IAdvPayWriteoffBillPayService advPayWriteoffBillPayService) {
		/*
		 *@set
		 *@this.advPayWriteoffBillPayService = advPayWriteoffBillPayService
		 */
		this.advPayWriteoffBillPayService = advPayWriteoffBillPayService;
	}

	

}
