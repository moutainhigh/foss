package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedPayService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillDepositReceivedPayVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;

/**
 * 预收单action
 * 
 * @author foss-pengzhen
 * @date 2012-12-5 上午10:49:57
 * @since
 * @version
 */

public class BillDepositReceivedPayAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7724932689767804658L;

	/**
	 * 预收单vo
	 */
	private BillDepositReceivedPayVo billDepositReceivedPayVo;

	/**
	 * 注入service
	 */
	private IBillDepositReceivedPayService billDepositReceivedPayService;

	/**
	 * 导出excel名称
	 */
	private String fileName;
	/**
	 * 输入流
	 */
	private ByteArrayInputStream inputStream;

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillDepositReceivedPayAction.class);

	/**
	 * 查询预收单
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午5:35:44
	 * @return
	 * @see
	 */
	@JSON
	public String queryDepositReceivedPage() {
		try {
			if(null == billDepositReceivedPayVo || null == billDepositReceivedPayVo.getBillDepositReceivedPayDto()){
				throw new ParameterException("传入参数不能为空！");
			}
			// 获取vo
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo
					.getBillDepositReceivedPayDto();
			// 获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用接口进行查询
			billDepositReceivedPayVo
					.setBillDepositReceivedPayDto(billDepositReceivedPayService
							.queryDepositReceived(billDepositReceivedPayDto,
									currentInfo, getStart(), getLimit()));
			// 设置总条数
			this.setTotalCount(billDepositReceivedPayDto.getTotalNum());
			// 返回成功
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 返回失败原因
			return returnError(e);
		}
	}

	/**
	 * 预收单新增
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-20 下午12:30:18
	 * @return
	 * @see
	 */
	@JSON
	public String addBillDepositReceived() {
		try {
			if(null == billDepositReceivedPayVo || null==billDepositReceivedPayVo
					.getBillDepositReceivedPayDto()){
				throw new ParameterException("预收单参数为空");
			}
			// 获取dto
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo
					.getBillDepositReceivedPayDto();
			// 获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用接口新增
			billDepositReceivedPayVo.setBillDepositReceivedPayDto(billDepositReceivedPayService
							.addBillDepositReceivedPay(
									billDepositReceivedPayDto, currentInfo));
			// 异常处理
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 返回错误
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 根据汇款单号获取汇款信息
	 * 
	 * @author foss-pengzhen
	 * @date 2013-1-15 下午2:24:30
	 * @return
	 * @see
	 */
	@JSON
	public String queryPayRemittanceDetail() {
		try {
			// 获取查询参数
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo
					.getBillDepositReceivedPayDto();
			// 获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用接口查询
			billDepositReceivedPayService.queryPayRemittanceDetail(
					billDepositReceivedPayDto, currentInfo);
			// 异常处理
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 返回失败
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

    /**
     * 根据网上支付编号获取信息
     *
     * @return
     */
	@JSON
	public String queryOnlinePayDetail() {
		try {
			// 获取查询参数
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo
					.getBillDepositReceivedPayDto();
			// 获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用接口查询
			billDepositReceivedPayService.queryOnlinePayDetail(
					billDepositReceivedPayDto, currentInfo);
			// 异常处理
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 返回失败
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 导出预收单
	 * 
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:10:45
	 * @return
	 * @see
	 */
	public String depositReceivedListExport() {
		// 输入流
		ByteArrayOutputStream baos = null;
		try {
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo
					.getBillDepositReceivedPayDto();
			// 获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 生成Excel代码
			try {
				// 设置Excel名称
				this.setFileName(new String(("预收单")
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				// 记录日志
				logger.error(e1.getMessage(), e1);
				// 返回失败
				returnError("导出文件名编码转化错误！");
				// 设置属性值
				setSuccess(true);
				// 设置属性值
				setException(true);
				// 返回失败
				return ERROR;
			}
			// 调用接口封装excel
			HSSFWorkbook wookBook = billDepositReceivedPayService
					.depositReceivedListExport(billDepositReceivedPayDto,
							currentInfo);
			try {
				// 实例化输出流
				baos = new ByteArrayOutputStream();
				// 将excel写到输出流中
				wookBook.write(baos);
				// 声明输入流
				inputStream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				// 记录入日志
				logger.error(e.getMessage(), e);
				// 返回错误
				returnError("生成excel流错误！");
				// 设置属性值
				setSuccess(true);
				// 设置属性值
				setException(true);
				// 返回失败
				return ERROR;
			}
			// 返回失败
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 返回失败
			returnError(e);
			// 设置属性值
			setSuccess(true);
			// 设置属性值
			setException(true);
			// 返回失败
			return ERROR;
		} finally {
			// 关闭流
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					returnError("流关闭错误！");
					setSuccess(true);
					setException(true);
					return ERROR;
				}
			}
		}
	}

	/**
	 * 作废预收单
	 * 
	 * @author foss-pengzhen
	 * @date 2012-11-26 下午6:41:58
	 * @return
	 * @see
	 */
	@JSON
	public String writeBackBillDepositReceived() {
		try {
			// 获取dto
			BillDepositReceivedPayDto billDepositReceivedPayDto = billDepositReceivedPayVo
					.getBillDepositReceivedPayDto();
			// 获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用预付单作废service
			billDepositReceivedPayService.writeBackBillDepositReceived(
					billDepositReceivedPayDto, currentInfo);
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 返回失败
			return returnError(e);
		}
	}

	/**
	 * 退预收
	 * 
	 * @author foss-pengzhen
	 * @date 2012-12-4 上午11:25:48
	 * @param
	 * @see
	 */
	@JSON
	public String backDepositReceived() {
		try {
			BillDepositReceivedPayDto billDepositReceivedPayDto = new BillDepositReceivedPayDto();
			// 获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置属性值
			billDepositReceivedPayDto
					.setBillDepositReceivedEntityList(billDepositReceivedPayVo
							.getBillDepositReceivedEntities());
			// 调用接口退预收
			billDepositReceivedPayService.backDepositReceived(
					billDepositReceivedPayDto, currentInfo, "DEPOSIT");
			// 设置属性值
			billDepositReceivedPayVo
					.setBillDepositReceivedPayDto(billDepositReceivedPayDto);
			// 返回成功
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			logger.error(e.getMessage(), e);
			// 返回失败
			return returnError(e);
		}
	}

	/**
	 * @GET
	 * @return billDepositReceivedPayVo
	 */
	public BillDepositReceivedPayVo getBillDepositReceivedPayVo() {
		/*
		 * @get
		 * 
		 * @ return billDepositReceivedPayVo
		 */
		return billDepositReceivedPayVo;
	}

	/**
	 * @SET
	 * @param billDepositReceivedPayVo
	 */
	public void setBillDepositReceivedPayVo(
			BillDepositReceivedPayVo billDepositReceivedPayVo) {
		/*
		 * @set
		 * 
		 * @this.billDepositReceivedPayVo = billDepositReceivedPayVo
		 */
		this.billDepositReceivedPayVo = billDepositReceivedPayVo;
	}

	/**
	 * @GET
	 * @return fileName
	 */
	public String getFileName() {
		/*
		 * @get
		 * 
		 * @ return fileName
		 */
		return fileName;
	}

	/**
	 * @SET
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		/*
		 * @set
		 * 
		 * @this.fileName = fileName
		 */
		this.fileName = fileName;
	}

	/**
	 * @GET
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		/*
		 * @get
		 * 
		 * @ return inputStream
		 */
		return inputStream;
	}

	/**
	 * @SET
	 * @param inputStream
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		/*
		 * @set
		 * 
		 * @this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}

	/**
	 * @SET
	 * @param billDepositReceivedPayService
	 */
	public void setBillDepositReceivedPayService(
			IBillDepositReceivedPayService billDepositReceivedPayService) {
		/*
		 * @set
		 * 
		 * @this.billDepositReceivedPayService = billDepositReceivedPayService
		 */
		this.billDepositReceivedPayService = billDepositReceivedPayService;
	}
}
