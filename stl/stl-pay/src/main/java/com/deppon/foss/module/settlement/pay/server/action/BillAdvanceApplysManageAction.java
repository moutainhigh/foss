package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillAdvanceResultVo;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillAdvanceVo;
import com.deppon.foss.util.DateUtils;

/**
 * 查询预付单Action
 * @description 空运总调经理提交成功空运预付款申请后，
 *              进入查询界面，通过输入查询条件查询已申请空运预付款记录，
 *              查询结果显示在列表中，同时可以在查询列表界面进行导出和新增操作。
 * @author 095793-foss-LiQin
 * @date 2012-11-08 下午2:09:42
 */
public class BillAdvanceApplysManageAction extends AbstractAction {

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 3003559619424852872L;

	/**
	 * 预付单Logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(BillAdvanceApplysManageAction.class);
	
	/**
	 *  查询预付单service
	 */
	private IBillAdvanceApplysManageService billAdvanceApplysManageService;
	
	/**
	 * 预付单传入VO
	 */
	private BillAdvanceVo billAdvanceVo;

	/**
	 * 预付单传出vo
	 */
	private BillAdvanceResultVo billAdvanceResultVo;

	/**
	 * 导出excel的文件名称
	 */
	private String fileName;

	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream stream;

	/**
	 * 查询预付单
	 * 通过输入查询条件查询已申请空运预付款记录，
	 * 查询结果显示在列表中
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午1:02:09
	 * @return 成功失败标记
	 * @param null
	 * @exception  SettlementException
	 */
	@JSON
	public String queryBillAdvanceApplys() {
		try {
			// 设置日期查询条件
			LOGGER.debug(" 查询预付单开始...");
			//获取当前登录用户信息			
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 获取该用户所管辖的部门
			Set<String> userOrgCodes = currentInfo.getUser().getOrgids();
			//声明当前的用户所管理的权限			
			List<String> orgCodeList = new ArrayList<String>(userOrgCodes);
			//判断用户管辖的部门
			if(CollectionUtils.isEmpty(orgCodeList)){
				//抛出异常
				throw new SettlementException("当前登录用户没有任何部门的预付单查询权限！");
			}
			//获取预付单界面输入查询条件			
			BillAdvanceDto billAdDto = billAdvanceVo.getBillAdvanDto();
			//设置查询条件
			billAdDto.setApplyOrgcodeList(orgCodeList);
			//预付单非空校验	
			checkQueryInputParam(billAdDto);
			//执行查询预付单的查询service			
			BillAdvanceResultDto billAdResultDto = billAdvanceApplysManageService.queryBillAdvancePayApply(billAdDto, getStart(), getLimit(),currentInfo);
			
			//预付单返回时Vo		
			billAdvanceResultVo = new BillAdvanceResultVo();
			//查询数据界面的集合				
			billAdvanceResultVo.setBillAdvanResultDto(billAdResultDto);

			// 设置总条数
			this.setTotalCount((long) billAdvanceResultVo.getBillAdvanResultDto().getCountNum());
			//返回界面			
			return returnSuccess();
		} catch (SettlementException e) {
			//查询预付单错误时计入logger			
			LOGGER.error("查询预付单错误"+e.getMessage(), e);
			//返回界面异常			
			return returnError(e);
		}
	}

	/**
	 * 检查预付款查询参数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 上午11:04:30
	 * @return 成功失败标记
	 * @param null
	 * @exception  SettlementException
	 */
	private void checkQueryInputParam(BillAdvanceDto bAdDto) {
		//预付单传入实体是否错误
		if (null == bAdDto) {
			//抛出异常
			throw new SettlementException("传入查询参数错误,不能全部为空！", "");
		}
		// 判断日期是否为空
		if (null == bAdDto.getBusinessStartDate()) {
			//抛出异常
			throw new SettlementException("开始日期不能为空！");
		}
		//	业务结束日期不能为空	
		if (null == bAdDto.getBusinessEndDate()) {
			//抛出异常
			throw new SettlementException("结束日期不能为空！");
		}
	}

	/**
	 * 新增空运预付款申请
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-20 下午12:36:37
	 * @return 成功失败标记
	 * @param null
	 * @exception  SettlementException
	 */
	@JSON
	public String addBillAdvanceApplys() {
		// 新增预付款dto
		BillAdvanceDto dto = billAdvanceVo.getBillAdvanDto();
		try {
			// 新增前，校验
			checkParms(dto);
			// 获取当前用户
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//执行预付单新增service			
			billAdvanceApplysManageService.addAdvancePayApply(dto, cInfo);
			// 新增预付款
			return returnSuccess("成功提交一条，预付款申请！");
		//异常处理
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error("新增预付单失败！"+e.getMessage(), e);
			//失败处理
			return returnError(e);
		}
	}


	/**
	 * 新增空运预付款时，必填校验
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-15 下午2:01:49
	 * @return null
	 * @param dto
	 * @exception  SettlementException
	 */
	private void checkParms(BillAdvanceDto dto) {
		// 最迟日期
		if (null == dto.getLastpaymentTime()) {
			//抛出异常
			throw new SettlementException("最迟汇款日期不能为空！");
		}
		// 审核状态
		BigDecimal payAmount = BigDecimal.ZERO;
		//判空
		if (dto.getAmount().compareTo(payAmount) == 0|| dto.getAmount().compareTo(payAmount) == -1) {
			//抛出异常
			throw new SettlementException("付款金额必须大于零！");
		}
		//判断，收款客户是否为空
		if (StringUtil.isBlank(dto.getCustomerCode())) {
			//抛出异常
			throw new SettlementException("收款客户不能为空！");
		}
		//判断，发票抬头不能为空		
		if (StringUtil.isBlank(dto.getInvoiceTitle())) {
			//抛出异常
			throw new SettlementException("发票抬头不能为空！");
		}
		//开户行不能为空		
		if (StringUtil.isBlank(dto.getBankHqName())) {
			//抛出异常
			throw new SettlementException("开户行,不能为空！");
		}
		//开户支行不能为空		
		if (StringUtil.isBlank(dto.getBankBranchName())) {
			//抛出异常
			throw new SettlementException("开户支行,不能为空！");
		}
		//开户名不能为空		
		if (StringUtil.isBlank(dto.getPayeeName())) {
			//抛出异常
			throw new SettlementException("开户名，不能为空！");
		}
		//判断开户身份是否为空		
		if (StringUtil.isBlank(dto.getProvinceName())) {
			throw new SettlementException("开户省份，不能为空！");
		}
		//开户城市不能为空		
		if (StringUtil.isBlank(dto.getCityName())) {
			//抛出异常
			throw new SettlementException("开户城市，不能为空！");
		}
		//判空
		if (StringUtil.isBlank(dto.getMobilePhone())) {
			//抛出异常
			throw new SettlementException("最迟汇款日期不能为空！");
		}
		//判空
		if (StringUtil.isBlank(dto.getAccountNo())) {
			//抛出异常
			throw new SettlementException("银行账号,不能为空！");
		}
		//判空 
		if (StringUtil.isBlank(dto.getCustomerName())) {
			//抛出异常
			throw new SettlementException("收款客户，不能为空！");
		}
	}

	/**
	 * 导出预付单 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午6:27:23
	 * @return 成功失败标记
	 * @param null
	 * @exception  SettlementException
	 */
	public String exportBillAdvancePayment() {
		// 输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 获取该用户所管辖的部门
			Set<String> userOrgCodes = currentInfo.getUser().getOrgids();
			//获取当前登录用户管辖部门集合			
			List<String> orgCodeList = new ArrayList<String>(userOrgCodes);
			//判断用户管辖的部门
			if(CollectionUtils.isEmpty(orgCodeList)){
				throw new SettlementException("当前登录用户没有任何部门的预付单查询权限！");
			}
			//获取界面输入dto			
			BillAdvanceDto billAdDto = billAdvanceVo.getBillAdvanDto();
			//设置查询条件
			billAdDto.setApplyOrgcodeList(orgCodeList);
			// 结束日期加1天
			billAdvanceVo.getBillAdvanDto().setBusinessEndDate(DateUtils.addDayToDate(billAdvanceVo.getBillAdvanDto().getBusinessEndDate(), 1));
			// 设置excel名称
			try {
				// 设置导出文件名称为预付单
				this.setFileName(new String(("预付单").getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				//导出预付单时异常				
				throw new SettlementException("导出文件名编码转化错误！"+e1.getMessage());
			}
			//返回预付单的数据集合			
			HSSFWorkbook wookBook = billAdvanceApplysManageService.exportBillAdvancePay(billAdvanceVo.getBillAdvanDto(),currentInfo);
			try {
				//将	流数据写入wookBook			
				wookBook.write(baos);
				//读入流
				stream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				//记录导出时流异常				
				LOGGER.error("预付单导出"+e.getMessage(),e);
			}
			//返回信息
			return returnSuccess();
		} catch (BusinessException e) {
			//预付单错误信息			
			LOGGER.error("导出预付单错误！"+e.getMessage(),e);
			//返回错误信息
			return returnError(e);
		} finally {
			// 关闭流
			if (baos != null) {
				try {
					//关闭流					
					baos.close();
				} catch (IOException e) {
					//预付单导出关闭输出流异常										
					LOGGER.error("预付单导出关闭IOException错误"+e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * @GET
	 * @return billAdvanceVo
	 */
	public BillAdvanceVo getBillAdvanceVo() {
		/*
		 *@get
		 *@ return billAdvanceVo
		 */
		return billAdvanceVo;
	}

	/**
	 * @SET
	 * @param billAdvanceVo
	 */
	public void setBillAdvanceVo(BillAdvanceVo billAdvanceVo) {
		/*
		 *@set
		 *@this.billAdvanceVo = billAdvanceVo
		 */
		this.billAdvanceVo = billAdvanceVo;
	}

	/**
	 * @GET
	 * @return billAdvanceResultVo
	 */
	public BillAdvanceResultVo getBillAdvanceResultVo() {
		/*
		 *@get
		 *@ return billAdvanceResultVo
		 */
		return billAdvanceResultVo;
	}

	/**
	 * @SET
	 * @param billAdvanceResultVo
	 */
	public void setBillAdvanceResultVo(BillAdvanceResultVo billAdvanceResultVo) {
		/*
		 *@set
		 *@this.billAdvanceResultVo = billAdvanceResultVo
		 */
		this.billAdvanceResultVo = billAdvanceResultVo;
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
	 * @param billAdvanceApplysManageService
	 */
	public void setBillAdvanceApplysManageService(
			IBillAdvanceApplysManageService billAdvanceApplysManageService) {
		/*
		 *@set
		 *@this.billAdvanceApplysManageService = billAdvanceApplysManageService
		 */
		this.billAdvanceApplysManageService = billAdvanceApplysManageService;
	}
	
	
	

}
