package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.vtsWriteomodifyBillWriteOffDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffToVTSClient;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.ModifyBillWriteoffResultVo;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.ModifyBillWriteoffVo;
import com.deppon.foss.util.DateUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 查询-核销-反核销更改单
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:30:42
 * @since
 * @version
 */

public class ModifyBillWriteoffAction extends AbstractAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger 更改单
	 */
	private static final Logger LOGGER = LogManager.getLogger(BillRecWriteoffBillPayAction.class);

	/**
	 * 查询-核销-反核销更改单传回到界面的VO
	 */
	private ModifyBillWriteoffResultVo modifyBillWriteoffResultVo = new ModifyBillWriteoffResultVo();

	/**
	 * 核销-反核销-更改单界面传入
	 */
	private ModifyBillWriteoffVo modifyBillWriteoffVo;

	/**
	 * 更改单service
	 */
	private IModifyBillWriteoffService modifyBillWriteoffService;
	
	/**
	 * @author 331556 fanjingwei
	 * 
	 */
	private IModifyBillWriteoffToVTSClient modifyBillWriteoffToVTSClient;

	/**
	 * 导出excel的文件名称
	 */
	private String fileName;

	/**
	 * /** 导出excel的输入流
	 */
	private ByteArrayInputStream stream;

	/**
	 * 导出excel名称
	 */
	private static final String EXPROTNAME = "核销更改单";

	/**
	 * 查询财务类更改的更改单清单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-26 上午9:22:31
	 */
	@JSON
	public String queryModifyBill() {
		try {
			if(modifyBillWriteoffVo==null){
				modifyBillWriteoffVo=new ModifyBillWriteoffVo();
			}
			// 更改单界面传入Vo，传递给Dto
			BillWriteoffDto modifyBillDto = modifyBillWriteoffVo.getModifyBillWriteOffDto();
			// 查询参数dto不能为空
			if (null == modifyBillDto) {
				// 为空，提示更改单实体不能为空
				throw new SettlementException("更改单实体不能为空");
			}

			// 受理日期和运单号和起草部门、核销状态不能同时为空，只是要选择一项
			if (null == modifyBillDto.getAcceptStartDate()&& null == modifyBillDto.getAcceptEndDate()) {
				// 为空，提示更改单受理开始结束日期为空
				throw new SettlementException("更改单受理开始结束日期为空!");
			}
			// 更改单受理开始和结束日期时间段不能超过限制天数
			if (DateUtils.getTimeDiff(modifyBillDto.getAcceptStartDate(),modifyBillDto.getAcceptEndDate())+1 > SettlementConstants.DATE_ELEVEN_DAYS_WEEK) {
				// 提示更改单受理开始和结束日期不能超过XX天
				throw new SettlementException("更改单受理开始和结束日期不能超过"+ SettlementConstants.DATE_ELEVEN_DAYS_WEEK + "天!");
			}

			// 获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 将参数传递至service
			BillWriteoffResultDto billWriResDto = modifyBillWriteoffService.queryModifyBill(modifyBillDto, getLimit(), getStart(),currentInfo);
			
			//将核销状态为“未核销”的订单核销时间和核销人设为空
			if(billWriResDto.getWaybillRfcList()!=null) {
				validaBillWriteoffResultDto(billWriResDto);
			}
			// 查询更改单列表返回到界面
			modifyBillWriteoffResultVo.setWaybillRfcList(billWriResDto.getWaybillRfcList());
			// 总条数
			this.setTotalCount(billWriResDto.getTotalCount());
			//记录日志
			LOGGER.info("更改单总条数" + billWriResDto.getTotalCount());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException be) {
			//记录日志
			LOGGER.error(" 查询财务类更改的更改单清单发生异常" + be.getMessage(), be);
			//异常返回
			return returnError(be);
		}
	}

	private void validaBillWriteoffResultDto(BillWriteoffResultDto billWriResDto) {
		if(billWriResDto.getWaybillRfcList().size()>0) {
			for(BillWriteoffResultDto resultDto : billWriResDto.getWaybillRfcList()) {
				if(resultDto.getWriteoffStatus()!=null) {
					if(resultDto.getWriteoffStatus().equals("NO_WRITE_OFF")) {
						resultDto.setWriteOffEmpName(null);
						resultDto.setWriteOffTime(null);
					}
				}
			}			
		}
	}

	/**
	 * 核销该更改单
	 * 
	 */
	@JSON
	public String writeoffModifyBill() {
		LOGGER.info("writeoff into...........");
		if(modifyBillWriteoffVo==null){
			modifyBillWriteoffVo=new ModifyBillWriteoffVo();
		}
		// 更改单界面传入Vo，传递给Dto
		BillWriteoffDto modifyBillWriteOffDto = modifyBillWriteoffVo.getModifyBillWriteOffDto();
		// 获取当前登录人信息
		CurrentInfo cInfo = FossUserContext.getCurrentInfo();
		// 将参数传递至service
		try {
			// 查询参数Dto不能为空
			if (null == modifyBillWriteOffDto) {
				// 为空，提示更改单实体不能为空
				throw new SettlementException("更改单实体不能为空！");
			}
			// 受理日期和运单号和起草部门、核销状态不能同时为空，只是要选择一项
			checkInputWriteoff(modifyBillWriteOffDto);
			// 核销更改单 核销通过、核销不通过
			BillWriteoffResultDto modifyBillWriteoffResultDto = modifyBillWriteoffService.writeoffModifyBill(modifyBillWriteOffDto, cInfo);
			// 设置核销更改单结果
			//modifyBillWriteoffResultVo.setModifyBillWriteoffResultDto(modifyBillWriteoffResultDto);
			/**
			 * @author 331556 fanjingwei
			 * @date 2016-05-13 20:25:40 配合VTS项目 更改单核销报表接口：核销通过不通过状态回传
			 */
			
			// 调用更改单核销报表（核销状态）同步接口
			vtsWriteomodifyBillWriteOffDto vtsWriteomodifyBillWriteOffDto = new vtsWriteomodifyBillWriteOffDto();
			if(StringUtils.isNotBlank(modifyBillWriteOffDto.getWriteoffSuccess())){
				// 核销状态
				vtsWriteomodifyBillWriteOffDto.setWriteoffStatus(modifyBillWriteOffDto.getWriteoffSuccess());
			}else{
				// 核销状态
				vtsWriteomodifyBillWriteOffDto.setWriteoffStatus(modifyBillWriteOffDto.getWriteoffFail());
			}
			// 运单号
			vtsWriteomodifyBillWriteOffDto.setWaybillNumber(modifyBillWriteOffDto.getWaybillNumber());
			// 核销备注
			vtsWriteomodifyBillWriteOffDto.setWriteOffNote(modifyBillWriteOffDto.getNotes());
			// 员工工号
			vtsWriteomodifyBillWriteOffDto.setEmpCode(cInfo.getEmpCode());
			// 员工姓名
			vtsWriteomodifyBillWriteOffDto.setEmpName(cInfo.getEmpName());
			// 当前登录部门名称
			vtsWriteomodifyBillWriteOffDto.setCurrentDeptName(cInfo
					.getCurrentDeptName());
			// 当前登录部门编码
			vtsWriteomodifyBillWriteOffDto.setCurrentDeptCode(cInfo
					.getCurrentDeptCode());

			modifyBillWriteoffToVTSClient
					.sendWriteoffWrapToVTS(vtsWriteomodifyBillWriteOffDto);
			LOGGER.info("调用核销状态同步接口成功...");
			//记录日志
			LOGGER.info("writeoff sucess exit");
			//正常返回
			return returnSuccess();
		//异常处理
		} catch (BusinessException be) {
			//记录日志
			LOGGER.error("核销该更改单异常" + be.getMessage(), be);
			//异常返回
			return returnError(be);
		}

	}

	/**
	 * 反核销更改单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-26 上午9:22:28
	 */
	@JSON
	public String reverseModifyBill() {

		try {
			LOGGER.info("反核销  into.......");
			if(modifyBillWriteoffVo==null){
				modifyBillWriteoffVo=new ModifyBillWriteoffVo();
			}
			// 更改单界面传入Vo，传递给Dto
			BillWriteoffDto modifyBillWriteoffDto = modifyBillWriteoffVo.getModifyBillWriteOffDto();
			// 查询参数Dto不能为空
			if (null == modifyBillWriteoffDto) {
				// 为空，提示更改单实体不能为空
				throw new SettlementException("更改单实体不能为空！");
			}
			// 受理日期和运单号和起草部门、核销状态不能同时为空，只是要选择一项
			checkInputWriteoff(modifyBillWriteoffDto);
			// 获取当前登录人信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 将参数传递至service反核销更改单
			BillWriteoffResultDto modifyBillWriteoffResultDto = modifyBillWriteoffService.reverseModifyBill(modifyBillWriteoffDto, cInfo);
			// 设置反核消更改单结果
			modifyBillWriteoffResultVo.setModifyBillWriteoffResultDto(modifyBillWriteoffResultDto);
			 //记录日志
			LOGGER.info("反核销 sucess exit");
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException be) {
			//记录日志
			LOGGER.error("反核销更改单发生异常" + be.getMessage(), be);
			//异常返回
			return returnError(be);
		}
	}

	/**
	 * 导出更改单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-21 上午11:30:53
	 */
	public String exportBillWillbayChange() {
		LOGGER.info("writeoff into...........");
		if(modifyBillWriteoffVo==null){
			modifyBillWriteoffVo=new ModifyBillWriteoffVo();
		}
		// 更改单界面传入Vo，传递给Dto
		BillWriteoffDto modifyDto = modifyBillWriteoffVo.getModifyBillWriteOffDto();
		
		//如果查询参数dto受理结束日期不为空
		if (null != modifyDto.getAcceptEndDate()) {
			//设置受理结束日期+1天
			Date dateTemp = DateUtils.addDayToDate(modifyDto.getAcceptEndDate(), 1);
			//设置查询参数dto受理结束日期参数
			modifyDto.setAcceptEndDate(dateTemp);
		}
		// 获取当前登录人信息
		CurrentInfo cInfo = FossUserContext.getCurrentInfo();
		ActionContext ac = ActionContext.getContext();   
		ServletContext sc = (ServletContext) ac.get(StrutsStatics.SERVLET_CONTEXT);   
		String path2 = sc.getRealPath("/");  

		try {
			this.setFileName(new String((EXPROTNAME)
					.getBytes(SettlementConstants.UNICODE_GBK),
					SettlementConstants.UNICODE_ISO));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("导出Excel失败");
		}
		stream = modifyBillWriteoffService.exportBillForBatch(modifyDto, cInfo,path2);
		return returnSuccess();
		
	}

	/**
	 * 检查核销操作时参数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午2:34:12
	 */
	private void checkInputWriteoff(BillWriteoffDto modifyBillDto) {

		//默认金额200
		BigDecimal changeAmount = new BigDecimal("200");

		// 变动金额大于200
		modifyBillDto.setChangeAmount(new BigDecimal(modifyBillDto.getSelectWaybillChangeNos().get(1)));

		//备注信息不能为空
		if (modifyBillDto.getChangeAmount().compareTo(changeAmount) == 1&& StringUtils.isEmpty(modifyBillDto.getNotes())) {
			//为空，提示备注信息不为空
			throw new SettlementException("备注信息不为空！");
		}

	}

	
	/**
	 * @get
	 * @return modifyBillWriteoffResultVo
	 */
	public ModifyBillWriteoffResultVo getModifyBillWriteoffResultVo() {
		/*
		 * @get
		 * @return modifyBillWriteoffResultVo
		 */
		return modifyBillWriteoffResultVo;
	}

	
	/**
	 * @set
	 * @param modifyBillWriteoffResultVo
	 */
	public void setModifyBillWriteoffResultVo(
			ModifyBillWriteoffResultVo modifyBillWriteoffResultVo) {
		/*
		 *@set
		 *@this.modifyBillWriteoffResultVo = modifyBillWriteoffResultVo
		 */
		this.modifyBillWriteoffResultVo = modifyBillWriteoffResultVo;
	}

	
	/**
	 * @get
	 * @return modifyBillWriteoffVo
	 */
	public ModifyBillWriteoffVo getModifyBillWriteoffVo() {
		/*
		 * @get
		 * @return modifyBillWriteoffVo
		 */
		return modifyBillWriteoffVo;
	}

	
	/**
	 * @set
	 * @param modifyBillWriteoffVo
	 */
	public void setModifyBillWriteoffVo(ModifyBillWriteoffVo modifyBillWriteoffVo) {
		/*
		 *@set
		 *@this.modifyBillWriteoffVo = modifyBillWriteoffVo
		 */
		this.modifyBillWriteoffVo = modifyBillWriteoffVo;
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
	 * @return stream
	 */
	public ByteArrayInputStream getStream() {
		/*
		 * @get
		 * @return stream
		 */
		return stream;
	}

	
	/**
	 * @set
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
	 * @set
	 * @param modifyBillWriteoffService
	 */
	public void setModifyBillWriteoffService(
			IModifyBillWriteoffService modifyBillWriteoffService) {
		/*
		 *@set
		 *@this.modifyBillWriteoffService = modifyBillWriteoffService
		 */
		this.modifyBillWriteoffService = modifyBillWriteoffService;
	}

	public void setModifyBillWriteoffToVTSClient(
			IModifyBillWriteoffToVTSClient modifyBillWriteoffToVTSClient) {
		this.modifyBillWriteoffToVTSClient = modifyBillWriteoffToVTSClient;
	}

	
}
