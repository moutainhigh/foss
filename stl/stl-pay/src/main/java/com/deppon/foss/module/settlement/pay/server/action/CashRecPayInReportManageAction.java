package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillCashRecPayInDResultVo;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillCashRecPayInDVo;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillCashRecPayInResultVo;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillCashRecPayInVo;

/**
 * 查询现金收入（缴款）报表Action
 * 
 * @author 095793-foss-LiQin
 * @date 2012-11-08 下午2:09:42
 */
public class CashRecPayInReportManageAction extends AbstractAction {

	/**
	 * 查询现金收入（缴款）版本
	 */
	private static final long serialVersionUID = -2617456327230569030L;

	/**
	 *  查询现金收入（缴款）service
	 */
	private IReportCashRecPayInService reportCashRecPayInService;

	/**
	 * 现金收入缴款报表vo
	 */
	private BillCashRecPayInVo billCashRecPayInVo;

	/**
	 * 现金收入缴款报表result vo
	 */
	private BillCashRecPayInResultVo billCashRecPayInRVo;

	/**
	 * 现金收入缴款明细 报表 vo
	 */
	private BillCashRecPayInDVo billCashRecDVo;

	/**
	 * 现金收入报表明细 result Vo
	 */
	private BillCashRecPayInDResultVo billCashRecDRVo;

	/**
	 * 查询现金收入（缴款） Logger
	 */
	private static final Logger logger = LogManager.getLogger(CashRecPayInReportManageAction.class);

	/**
	 * 导出excel的文件名称
	 */
	private String fileName;

	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream stream;

	/**
	 * 查询现金收入报表明细
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-12 下午4:45:03
	 */
	@JSON
	public String queryReportCashRecPayInD() {
		try {
			//记录日志
			logger.info("查询缴款报表开始...");
			//获取界面传入参数
			BillCashRecPayInDDto billCashRecDDto = billCashRecDVo.getBillCashRecPayInDDto();

			//现金收入明细检查			
			checkQueryBillCashPayDParams(billCashRecDDto);
			
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			
			// 设置当前登陆人CODE
			billCashRecDDto.setEmpCode(cInfo.getEmpCode());
			
			// 判断部门不能控
			if(StringUtils.isEmpty(billCashRecDDto.getCollectionOrgCode())) {
				throw new SettlementException("部门不能为空！");
			}
			
			//声明查询结果集返回Vo			
			billCashRecDRVo = new BillCashRecPayInDResultVo();
			//执行查询			
			BillCashRecPayInDResultDto billCashRecDRDto = reportCashRecPayInService.queryReportCashRecPayInD(billCashRecDDto,cInfo,getStart(),getLimit());
			//返回现金收入缴款报表返回结果集			
			billCashRecDRVo.setBillCashRecPayInDResultDto(billCashRecDRDto);
			//打印，查询现金收入缴款报表条数
			if (CollectionUtils.isNotEmpty(billCashRecDRDto.getCashRpDList())) {
				//记录日志
				logger.info("缴款报表条数"+ billCashRecDRVo.getBillCashRecPayInDResultDto().getTotalCount());
			}
			//记录日志
			logger.info("查询缴款报表 exit....");
			this.setTotalCount((long)billCashRecDRVo.getBillCashRecPayInDResultDto().getTotalCount());
			
			return returnSuccess();
		} catch (SettlementException e) {
			//记录日志
			logger.error("缴款报表错误!"+e.getMessage(),e);
			return returnError(e);
		}
	}

	/**
	 * 导出现金收入（缴款）报表
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午6:27:23
	 */
	public String exportCashRecPayIn() {
		// 输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 传入对象非空判断
			if (null == billCashRecDVo || null == billCashRecDVo.getBillCashRecPayInDDto()) {
				// 如果没有值则抛出异常
				throw new SettlementException("传入参数不能为空");
			}

			BillCashRecPayInDDto billCashRecPayInDDto = billCashRecDVo.getBillCashRecPayInDDto();
			// 判断不能为空
			if(StringUtils.isEmpty(billCashRecPayInDDto.getCollectionOrgCode())) {
				throw new SettlementException("部门不能为空！");
			}
			// 设置人员CODE
			billCashRecPayInDDto.setEmpCode(currentInfo.getEmpCode());
			
			// 设置excel名称
			try {
				this.setFileName(new String(("缴款报表").getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				logger.error("导出缴款报表，文件编码类型转换异常"+e1.getMessage(),e1);
			}
			
			
			//调用接口获取excel对象
			HSSFWorkbook wookBook = reportCashRecPayInService.exportCashRecPayIn(billCashRecDVo.getBillCashRecPayInDDto(),currentInfo);
			try {
				//写入流
				wookBook.write(baos);
				//创建爱你输出流
				stream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				//记录日志
				logger.error("导出缴款报表输入流异常"+e.getMessage(), e);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			logger.error("导出缴款报表异常"+e.getMessage(), e);
			return returnError(e.getMessage());
		} finally {
			// 关闭流
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					logger.error("导出缴款报表关闭流异常"+e.getMessage(), e);
				}
			}
		}
	}	
	
	/**
	 * 查询现金收入（缴款）报表明细日期检查
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-12 下午4:48:29
	 */
	private void checkQueryBillCashPayDParams(BillCashRecPayInDDto billCashRecDDto) {
		if (null == billCashRecDDto) {
			throw new SettlementException("DTO不能为空");
		}

		if (null == billCashRecDDto.getBusinessStartDate()) {
			throw new SettlementException("报表开始日期不能为空!");
		}
		if (null == billCashRecDDto.getBusinessEndDate()) {
			throw new SettlementException("报表结束日期不能为空!");
		}
	}
	

	/**
	 * @GET
	 * @return billCashRecPayInVo
	 */
	public BillCashRecPayInVo getBillCashRecPayInVo() {
		/*
		 *@get
		 *@ return billCashRecPayInVo
		 */
		return billCashRecPayInVo;
	}

	/**
	 * @SET
	 * @param billCashRecPayInVo
	 */
	public void setBillCashRecPayInVo(BillCashRecPayInVo billCashRecPayInVo) {
		/*
		 *@set
		 *@this.billCashRecPayInVo = billCashRecPayInVo
		 */
		this.billCashRecPayInVo = billCashRecPayInVo;
	}

	/**
	 * @GET
	 * @return billCashRecPayInRVo
	 */
	public BillCashRecPayInResultVo getBillCashRecPayInRVo() {
		/*
		 *@get
		 *@ return billCashRecPayInRVo
		 */
		return billCashRecPayInRVo;
	}

	/**
	 * @SET
	 * @param billCashRecPayInRVo
	 */
	public void setBillCashRecPayInRVo(BillCashRecPayInResultVo billCashRecPayInRVo) {
		/*
		 *@set
		 *@this.billCashRecPayInRVo = billCashRecPayInRVo
		 */
		this.billCashRecPayInRVo = billCashRecPayInRVo;
	}

	/**
	 * @GET
	 * @return billCashRecDVo
	 */
	public BillCashRecPayInDVo getBillCashRecDVo() {
		/*
		 *@get
		 *@ return billCashRecDVo
		 */
		return billCashRecDVo;
	}

	/**
	 * @SET
	 * @param billCashRecDVo
	 */
	public void setBillCashRecDVo(BillCashRecPayInDVo billCashRecDVo) {
		/*
		 *@set
		 *@this.billCashRecDVo = billCashRecDVo
		 */
		this.billCashRecDVo = billCashRecDVo;
	}

	/**
	 * @GET
	 * @return billCashRecDRVo
	 */
	public BillCashRecPayInDResultVo getBillCashRecDRVo() {
		/*
		 *@get
		 *@ return billCashRecDRVo
		 */
		return billCashRecDRVo;
	}

	/**
	 * @SET
	 * @param billCashRecDRVo
	 */
	public void setBillCashRecDRVo(BillCashRecPayInDResultVo billCashRecDRVo) {
		/*
		 *@set
		 *@this.billCashRecDRVo = billCashRecDRVo
		 */
		this.billCashRecDRVo = billCashRecDRVo;
	}

	/**
	 * @GET
	 * @return fileName
	 */
	public String getFileName() {
		/*
		 *@get
		 *@ return fileName
		 */
		return fileName;
	}

	/**
	 * @SET
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
	 * @GET
	 * @return stream
	 */
	public ByteArrayInputStream getStream() {
		/*
		 *@get
		 *@ return stream
		 */
		return stream;
	}

	/**
	 * @SET
	 * @param stream
	 */
	public void setStream(ByteArrayInputStream stream) {
		/*
		 *@set
		 *@this.stream = stream
		 */
		this.stream = stream;
	}

	/**
	 * @SET
	 * @param reportCashRecPayInService
	 */
	public void setReportCashRecPayInService(
			IReportCashRecPayInService reportCashRecPayInService) {
		/*
		 *@set
		 *@this.reportCashRecPayInService = reportCashRecPayInService
		 */
		this.reportCashRecPayInService = reportCashRecPayInService;
	}


}
