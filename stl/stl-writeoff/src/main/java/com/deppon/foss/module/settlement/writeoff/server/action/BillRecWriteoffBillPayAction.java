package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillRecWriteoffBillPayService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.BillRecWriteoffBillPayResultVo;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.BillRecWriteoffBillPayVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;

/**
 * 应收冲应付Action
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午2:09:42
 */
public class BillRecWriteoffBillPayAction extends AbstractAction {

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 3003559619424852872L;

	/**
	 * 应收冲应付服务
	 */
	private IBillRecWriteoffBillPayService billRecWriteoffBillPayService;

	/**
	 * 输入参数
	 */
	private BillRecWriteoffBillPayVo billRecWriteoffBillPayVo;

	/**
	 * 应收冲应付,界面返回VO
	 */
	private BillRecWriteoffBillPayResultVo billRecWriteoffBillPayResultVo;

	
	/**
	 * Logger 应收冲应付
	 */
	private static final Logger LOGGER = LogManager.getLogger(BillRecWriteoffBillPayAction.class);

	
	/**
	 * 导出excel的文件名称
	 */
	private String fileName;
	
	/**
	 * /**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream inputStream;
	
	
	/**
	 * 导出应收单excel名称
	 */
	private static final String  EXPROTRECNAME = "导出应收单";
	
	/**
	 * 导出应付单excel名称
	 */
	private static final String  EXPROTPAYNAME = "导出应付单";
	
	
	/**
	 * 查询未核销的应收单和应付单列表信息
	 * 
	 * @date 2012-10-16 下午2:42:42
	 */
	@JSON
	public String queryForBillReceivableAndBillPayable() {
		try {
			//查询参数不能为空
			if(null==billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto()){
				//如果查询参数为空,提示按日期查询未核销应收和应付单Dto,不能为空
				throw new SettlementException("按日期查询未核销应收和应付单Dto,不能为空！");
			}
			// 界面传入查询参数，设置到dto
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto = billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto();
			// 检验输入条件
			checkInputForQueryDate(billRecWriteoffBillPayDto);
			// 获取当前登录人信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			
			//查询执行查询操作
			BillRecWriteoffBillPayResultDto billRecWriteoffBillPayResultDto = billRecWriteoffBillPayService.queryRecWriteoffPayList(billRecWriteoffBillPayDto, cInfo);
			//返回界面Vo
			billRecWriteoffBillPayResultVo=new BillRecWriteoffBillPayResultVo();
			// 设置应收单列表
			billRecWriteoffBillPayResultVo.setBillReceivableEntityList(billRecWriteoffBillPayResultDto.getBillReceivableEntityList());
			// 应收单的总金额
			billRecWriteoffBillPayResultVo.setRecTotalAmount(billRecWriteoffBillPayResultDto.getRecTotalAmount());
			//应收单的未核销总金额
			billRecWriteoffBillPayResultVo.setRecUnverifyTotalAmount(billRecWriteoffBillPayResultDto.getRecUnverifyTotalAmount());
			//应收单的已核销总金额
			billRecWriteoffBillPayResultVo.setRecVerifyTotalAmount(billRecWriteoffBillPayResultDto.getRecVerifyTotalAmount());
			//应收单的应收单总条数
			billRecWriteoffBillPayResultVo.setRecTotalNum(billRecWriteoffBillPayResultDto.getRecTotalNum());
			
			LOGGER.info("应收冲应付:按日期查询应收单未核销金额："+billRecWriteoffBillPayResultDto.getRecUnverifyTotalAmount());
			
			// 设置应付单列表
			billRecWriteoffBillPayResultVo.setBillPayableEntityList(billRecWriteoffBillPayResultDto.getBillPayableEntityList());
			
			// 应付单的总条数
			billRecWriteoffBillPayResultVo.setPayTotalNum(billRecWriteoffBillPayResultDto.getPayTotalNum());
			//应付单的总金额
			billRecWriteoffBillPayResultVo.setPayTotalAmount(billRecWriteoffBillPayResultDto.getPayTotalAmount());
			//应付单的总未核销金额
			billRecWriteoffBillPayResultVo.setPayUnverifyTotalAmount(billRecWriteoffBillPayResultDto.getPayUnverifyTotalAmount());
			//应付单的总核销金额
			billRecWriteoffBillPayResultVo.setPayVerifyTotalAmount(billRecWriteoffBillPayResultDto.getPayVerifyTotalAmount());
			
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error("应收冲应付:按日期查询未核销的应收单和应付单列表信息异常:"+e.getMessage(),e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 按单号查询应收单运单号查询、应付单或者运单查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-16 下午2:09:42
	 */
	@JSON
	public String queryBillReceivableOrBillPayableOrWaybillForNumbers() {
		try {
			//查询参数不能为空
			if(null==billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto()){
				//如果查询参数为空,提示查询应收单应付单Dto不能为空
				throw new SettlementException("查询应收单应付单Dto不能为空！");
			}
			// 界面传入参数，设置到service层
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto = billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto();
			// 校验界面传入
			checkInputForQueryNo(billRecWriteoffBillPayDto);

			// 返回界面vo
			billRecWriteoffBillPayResultVo = new BillRecWriteoffBillPayResultVo();

			// 获取当前登录人信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 按照应收单单号、运单号、应付单、运单号查询待核销单据信息
			BillRecWriteoffBillPayResultDto billRecWriteoffBillPayResultDto = billRecWriteoffBillPayService.queryBillRecOrBillPayNums(billRecWriteoffBillPayDto, cInfo);

			// 设置待核销应收单列表
			billRecWriteoffBillPayResultVo.setBillReceivableEntityList(billRecWriteoffBillPayResultDto.getBillReceivableEntityList());
			// 设置待核销应付单列表
			billRecWriteoffBillPayResultVo.setBillPayableEntityList(billRecWriteoffBillPayResultDto.getBillPayableEntityList());
			LOGGER.info("应收冲应付:应收单已核销金额"+billRecWriteoffBillPayResultDto.getRecVerifyTotalAmount());
			//应收单的条数
			billRecWriteoffBillPayResultVo.setRecTotalNum(billRecWriteoffBillPayResultDto.getRecTotalNum());
			//应收单的总金额
			billRecWriteoffBillPayResultVo.setRecTotalAmount(billRecWriteoffBillPayResultDto.getRecTotalAmount());
			//应收单的总未核销金额
			billRecWriteoffBillPayResultVo.setRecUnverifyTotalAmount(billRecWriteoffBillPayResultDto.getRecUnverifyTotalAmount());
			//应收单的总核销金额
			billRecWriteoffBillPayResultVo.setRecVerifyTotalAmount(billRecWriteoffBillPayResultDto.getRecVerifyTotalAmount());

			//应付单的总条数
			billRecWriteoffBillPayResultVo.setPayTotalNum(billRecWriteoffBillPayResultDto.getPayTotalNum());
			//应付单的总金额
			billRecWriteoffBillPayResultVo.setPayTotalAmount(billRecWriteoffBillPayResultDto.getPayTotalAmount());
			//应付单的总未核销金额
			billRecWriteoffBillPayResultVo.setPayUnverifyTotalAmount(billRecWriteoffBillPayResultDto.getPayUnverifyTotalAmount());
			//应付单的总核销金额
			billRecWriteoffBillPayResultVo.setPayVerifyTotalAmount(billRecWriteoffBillPayResultDto.getPayVerifyTotalAmount());

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error("应收冲应付:按应付单号和应收单号查询异常"+e.getMessage(),e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * <p>
	 * 核销应收应付
	 * </p>
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-24 上午11:30:38
	 */
	@JSON
	public String writeoffBillReceivableAndBillPayable() {
		//获取应收应付查询dto
		BillRecWriteoffBillPayDto billRecWriteoffBillPayDto = billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto();
		try {
			// 界面传入参数，设置到service层
			checkInputForWriteoff(billRecWriteoffBillPayDto);
			// 获取当前登录人信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 应收冲应付
			BillRecWriteoffBillPayResultDto billRecWriteOffPayableResultDto = billRecWriteoffBillPayService.writeoffBillRecivableBillPaybable(billRecWriteoffBillPayDto, cInfo);
			//生成返回Vo
			billRecWriteoffBillPayResultVo=new BillRecWriteoffBillPayResultVo();
			// 返回结果设值到resultvo
			BeanUtils.copyProperties(billRecWriteOffPayableResultDto,billRecWriteoffBillPayResultVo);
			
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error("应收冲应付:应收冲应付核销错误！"+e.getMessage(),e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 
	 * 核销时检查界面输入应付单、应收单是否选中
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午8:07:41
	 */
	public void checkInputForWriteoff(BillRecWriteoffBillPayDto billRecWriteOffPayDto) {

		// 界面，应付单或者运单未选中
		if (CollectionUtils.isEmpty(billRecWriteOffPayDto.getBillPayableEntityList())) {
			//提示请选择至少一条，核销的应付单
			throw new SettlementException("请选择至少一条，核销的应付单！");
		}

		// 界面，应收单或者运单未选中
		if (CollectionUtils.isEmpty(billRecWriteOffPayDto.getBillReceivableEntityList())) {
			//提示请选择至少一条，核销的应收单
			throw new SettlementException("请选择至少一条，核销的应收单！");
		}
		
		
		// 业务结束时间设置，使执行时结束日期等于结束日期+1天
		if (billRecWriteOffPayDto.getRecBusinessEndDate()!= null) {
			//设置业务结束时间+1天
			Date dateTemp = DateUtils.addDayToDate(billRecWriteOffPayDto.getRecBusinessEndDate(),1); 
			//设置查询参数dto的业务结束时间参数
			billRecWriteOffPayDto.setRecBusinessEndDate(dateTemp);
		}
		
		// 业务结束时间设置，使执行时结束日期等于结束日期+1天
		if (billRecWriteOffPayDto.getPayBusinessEndDate()!= null) {
			//设置业务结束时间+1天
			Date dateTemp = DateUtils.addDayToDate(billRecWriteOffPayDto.getPayBusinessEndDate(),1); 
			//设置查询参数dto的业务结束时间参数
			billRecWriteOffPayDto.setPayBusinessEndDate(dateTemp);
		}
	}
	


	/**
	 * 导出应收单
	 * @author 095793-foss-LiQin
	 * @date 2012-12-28 下午4:48:41
	 */
	public String exportWriteoffBillRec(){

		// 导出时输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//查询参数Dto不能为空
			if(null==billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto()){
				//为空，提示导出应收单时，错误Dto，未设置查询参数
				throw new SettlementException("导出应收单时，错误Dto，未设置查询参数！");
			}
			// 核销应收应付dto
			BillRecWriteoffBillPayDto billRecPayDto = billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto();
			
			//查询参数查询类型不能为空
			if(StringUtils.isEmpty(billRecPayDto.getQueryType())){
				//为空，提示导出时，错误Dto，未设置按日期查询或按单号查询
				throw new SettlementException("导出时，错误Dto，未设置按日期查询或按单号查询！");
			}
			//按日期查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(billRecPayDto.getQueryType())){

				//设置导出应收单的导出时间+1天							
				if (billRecPayDto.getRecBusinessEndDate()!= null) {
					Date dateTemp = DateUtils.addDayToDate(billRecPayDto.getRecBusinessEndDate(),1); 
					billRecPayDto.setRecBusinessEndDate(dateTemp);
				}
				
				//设置查询参数dto业务结束时间参数				
				if (billRecPayDto.getPayBusinessEndDate()!= null) {
					Date dateTemp = DateUtils.addDayToDate(billRecPayDto.getPayBusinessEndDate(),1); 
					billRecPayDto.setPayBusinessEndDate(dateTemp);
				}
				
				
			//按单号查询	
			}else if(SettlementConstants.TAB_QUERY_BY_REC_PAY_NO.equals(billRecPayDto.getQueryType())){
				//检查按单号查询条件

			}

			// 设置excel名称
			try {
				// 设置excel名称
				this.setFileName(new String((EXPROTRECNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			//异常处理	
			} catch (UnsupportedEncodingException e1) {
				//记录日志
				LOGGER.error("导出文件名编码转化错误！"+e1.getMessage(), e1);
			}
			//导出应收单
			HSSFWorkbook wookBook = billRecWriteoffBillPayService.exportWriteoffBillRec(billRecPayDto, currentInfo);
			try {
				//write数据流
				wookBook.write(baos);
				//新建导出excel输入流
				inputStream = new ByteArrayInputStream(baos.toByteArray());
			//异常处理	
			} catch (IOException e) {
				//记录日志
				LOGGER.error("生成excel流错误！"+e.getMessage(), e);
			}
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//异常返回
			return returnError("应收冲应付，导出应收单异常："+e.getErrorCode(),e);
		} finally {
			// 关闭流
			if (baos != null) {
				try {
					// 关闭流
					baos.close();
				//异常处理	
				} catch (IOException e) {
					//异常返回
					LOGGER.error("流关闭错误！"+e.getMessage(), e);
				}
			}
		}
	}


	/**
	 * 导出应付单
	 * @author 095793-foss-LiQin
	 * @date 2012-12-28 下午4:48:52
	 */
	public String exportWriteoffBillPay() {
		// 导出时输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//查询参数Dto不能为空
			if(null==billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto()){
				//为空，导出应付单时，错误Dto，未设置查询参数
				throw new SettlementException("导出应付单时，错误Dto，未设置查询参数！");
			}
			
			// 获取核销dto
			BillRecWriteoffBillPayDto billRecPayDto = billRecWriteoffBillPayVo.getBillRecWriteoffBillPayDto();
			
			//查询参数查询类型不能为空
			if(StringUtils.isEmpty(billRecPayDto.getQueryType())){
				//为空，提示导出时，错误Dto，未设置按日期查询或按单号查询
				throw new SettlementException("导出时，错误Dto，未设置按日期查询或按单号查询！");
			}
			//按日期查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(billRecPayDto.getQueryType())){
				//检查按日期查询输入条件
				//设置导出应收单的导出时间+1天				
				if (billRecPayDto.getRecBusinessEndDate()!= null) {
					Date dateTemp = DateUtils.addDayToDate(billRecPayDto.getRecBusinessEndDate(),1); 
					billRecPayDto.setRecBusinessEndDate(dateTemp);
				}
				
				//设置导出应付单的导出时间+1天
				if (billRecPayDto.getPayBusinessEndDate()!= null) {
					Date dateTemp = DateUtils.addDayToDate(billRecPayDto.getPayBusinessEndDate(),1); 
					billRecPayDto.setPayBusinessEndDate(dateTemp);
				}
			//按单号查询	
			}else if(SettlementConstants.TAB_QUERY_BY_REC_PAY_NO.equals(billRecPayDto.getQueryType())){
				//检查按单号查询条件
			}
			
			// 设置excel名称
			try {
				// 设置excel名称
				this.setFileName(new String((EXPROTPAYNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			//异常处理	
			} catch (UnsupportedEncodingException e1) {
				//记录日志
				LOGGER.error("导出文件名编码转化错误！"+e1.getMessage());
			}
			//导出应付单
			HSSFWorkbook wookBook = billRecWriteoffBillPayService.exportWriteoffBillPay(billRecPayDto, currentInfo);
			try {
				//write数据流
				wookBook.write(baos);
				//新建导出excel输入流
				inputStream = new ByteArrayInputStream(baos.toByteArray());
			//异常处理	
			} catch (IOException e) {
				//记录日志
				LOGGER.error("生成excel流错误！"+e.getMessage(), e);
			}
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//异常返回
			return returnError("应收冲应付，导出应付单异常："+e.getErrorCode(),e);
		} finally {
			// 关闭流
			if (baos != null) {
				try {
					// 关闭流
					baos.close();
				//异常处理	
				} catch (IOException e) {
					//记录日志
					LOGGER.error("流关闭错误"+e.getMessage(), e);
				}
			}
		}

	}
	
	
	
	
	/**
	 * 按日期查询应付单、应收单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-3 下午12:46:51
	 */
	private void checkInputForQueryDate(BillRecWriteoffBillPayDto billRecPayDto){
		//应收单查询时业务开始日期为空	
		if(null == billRecPayDto.getRecBusinessStartDate()){
			throw new SettlementException("应收单查询时业务开始日期为空!");
		}
		//应收单查询时业务结束日期为空	
		if(null == billRecPayDto.getRecBusinessEndDate()){
			throw new SettlementException("应收单查询时业务结束日期为空!");
		}
		//应付单查询业务开始日期为空		
		if(null == billRecPayDto.getPayBusinessStartDate()){
			throw new SettlementException("应付单查询业务开始日期为空!");
		}
		//应付单查询业务结束日期为空			
		if(null == billRecPayDto.getPayBusinessEndDate()){
			throw new SettlementException("应付单查询业务结束日期为空!");
		}
		
		//按业务日期查询时间段不能超限制
        if(DateUtils.getTimeDiff(billRecPayDto.getRecBusinessStartDate(),billRecPayDto.getRecBusinessEndDate())>SettlementConstants.DATE_LIMIT_DAYS_MAX_MONTH){
        	//提示业务结束日期为空 业务开始日期和结束日期不能超过XX天
        	throw new SettlementException("应收单查询时，业务开始日期和结束日期不能超过"+SettlementConstants.DATE_LIMIT_DAYS_MAX_MONTH+"天!"); 
        }
        
    	//按业务日期查询时间段不能超限制
        if(DateUtils.getTimeDiff(billRecPayDto.getPayBusinessStartDate(),billRecPayDto.getPayBusinessEndDate())>SettlementConstants.DATE_LIMIT_DAYS_MAX_MONTH){
        	//提示业务结束日期为空 业务开始日期和结束日期不能超过XX天
        	throw new SettlementException("应收单查询时，业务开始日期和结束日期不能超过"+SettlementConstants.DATE_LIMIT_DAYS_MAX_MONTH+"天!"); 
        }
        
        
		// 页面最大条数,为零的情况
		if (billRecPayDto.getMaxShowNum() < 0) {
			//提示页面最大条数，不能为空
			throw new SettlementException("页面最大条数，不能为空!");
		}

		// 业务结束时间设置，使执行时结束日期等于结束日期+1天
		if (billRecPayDto.getRecBusinessEndDate()!= null) {
			//设置业务结束时间+1天
			Date dateTemp = DateUtils.addDayToDate(billRecPayDto.getRecBusinessEndDate(),1); 
			//设置查询参数dto的业务结束时间参数
			billRecPayDto.setRecBusinessEndDate(dateTemp);
		}
		
		// 业务结束时间设置，使执行时结束日期等于结束日期+1天
		if (billRecPayDto.getPayBusinessEndDate()!= null) {
			//设置业务结束时间+1天
			Date dateTemp = DateUtils.addDayToDate(billRecPayDto.getPayBusinessEndDate(),1); 
			//设置查询参数dto的业务结束时间参数
			billRecPayDto.setPayBusinessEndDate(dateTemp);
		}
	}
	
	

	/**
	 * 
	 * 按单号查询应收收单、应付单传入校验
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-7 上午10:40:49
	 */
	private void checkInputForQueryNo(BillRecWriteoffBillPayDto billRecWriteOffPayDto) {
		
		//应收单号或运单不能为空
		if(StringUtils.isEmpty(billRecWriteOffPayDto.getReceivableNosOrWaybillNos())){
			//为空,提示请输入，应收单号或者运单号
			throw new SettlementException("请输入，应付单号或者运单号！");
		}
		//应付单号或运单不能为空
		if(StringUtils.isEmpty(billRecWriteOffPayDto.getPayNosOrWaybillNos())){
			//为空,提示请输入，应付单号或者运单号
			throw new SettlementException("请输入，应收单号或者运单号！");
		}
	}

	
	/**
	 * @get
	 * @return billRecWriteoffBillPayVo
	 */
	public BillRecWriteoffBillPayVo getBillRecWriteoffBillPayVo() {
		/*
		 * @get
		 * @return billRecWriteoffBillPayVo
		 */
		return billRecWriteoffBillPayVo;
	}

	
	/**
	 * @set
	 * @param billRecWriteoffBillPayVo
	 */
	public void setBillRecWriteoffBillPayVo(
			BillRecWriteoffBillPayVo billRecWriteoffBillPayVo) {
		/*
		 *@set
		 *@this.billRecWriteoffBillPayVo = billRecWriteoffBillPayVo
		 */
		this.billRecWriteoffBillPayVo = billRecWriteoffBillPayVo;
	}

	
	/**
	 * @get
	 * @return billRecWriteoffBillPayResultVo
	 */
	public BillRecWriteoffBillPayResultVo getBillRecWriteoffBillPayResultVo() {
		/*
		 * @get
		 * @return billRecWriteoffBillPayResultVo
		 */
		return billRecWriteoffBillPayResultVo;
	}

	
	/**
	 * @set
	 * @param billRecWriteoffBillPayResultVo
	 */
	public void setBillRecWriteoffBillPayResultVo(
			BillRecWriteoffBillPayResultVo billRecWriteoffBillPayResultVo) {
		/*
		 *@set
		 *@this.billRecWriteoffBillPayResultVo = billRecWriteoffBillPayResultVo
		 */
		this.billRecWriteoffBillPayResultVo = billRecWriteoffBillPayResultVo;
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
	 * @param billRecWriteoffBillPayService
	 */
	public void setBillRecWriteoffBillPayService(
			IBillRecWriteoffBillPayService billRecWriteoffBillPayService) {
		/*
		 *@set
		 *@this.billRecWriteoffBillPayService = billRecWriteoffBillPayService
		 */
		this.billRecWriteoffBillPayService = billRecWriteoffBillPayService;
	}
	

	
	

}
