package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPosCardManageService;
import com.deppon.foss.module.settlement.pay.api.shared.vo.PosCardManageVo;
import com.deppon.foss.util.DateUtils;

/**
 * 
 * @ClassName: PosCardManageAction
 * @Description: (POS刷卡管理)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-12 下午4:08:20
 * 
 */
public class PosCardManageAction extends AbstractAction {
	private static final Logger LOGGER = LogManager
			.getLogger(PosCardManageAction.class);

	private static final long serialVersionUID = 1L;
	/**
	 * POS VO
	 */
	private PosCardManageVo vo;

	/**
	 * POS Service
	 */
	private IPosCardManageService posCardManageService;
	
	/**
	 * 导出excel名称
	 */
	private static final String EXPROTNAME = "POS刷卡管理明细";

	/**
	 * 定义异常返回信息
	 */
	private String errorMessage;

	/**
	 * 导出excel的文件名称
	 */
	private String fileName;

	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream stream;

	/**
	 * 查询POS刷卡管理
	 * 
	 * @Title: queryPostCard
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	public String queryPostCard() {
		try {
			LOGGER.info("Dto" + vo.getPosCardManageDto() + "");
			// 获取前台传递的参数
			PosCardManageDto posCardManageDto = vo.getPosCardManageDto();
			//假如按日期查询
			if ("NO".equals((posCardManageDto.getQueryTabType()))) {
				// 按单号查询
				queryposCardManageByNumber();
			}else if("SN".equals((posCardManageDto.getQueryTabType()))){
				// 按流水号查询
				queryposCardManageBySerialNos();
			} else {
				LOGGER.info("********按日期查询*******");
				// 按日期查询
				if (vo.getPosCardManageDto().getPeriodEndDate() != null) {
					// 结束日期加1天
					vo.getPosCardManageDto().setPeriodEndDate(
							DateUtils.convert(DateUtils.addDay(vo
									.getPosCardManageDto().getPeriodEndDate(),1)));
				}
				PosCardManageDto dto = posCardManageService.queryPosCard(
						vo.getPosCardManageDto(), this.getStart(),
						this.getLimit());
				vo.setPosCardManageDto(dto);
				//设置总行数
				this.setTotalCount(Long.valueOf(dto.getCount()));
			}
		} catch (SettlementException e) {
			// 返回界面异常
			return returnError(e.getErrorCode());
		}
		// 返回界面
		return returnSuccess();
	}

	/**
	 * 按交易流水号查询
	 * 
	 * @Title: queryposCardManageBySerialNos
	 * @author： 357637 |yuanhuijun001@deppon.com
	 */
	private String queryposCardManageBySerialNos() {
		LOGGER.info("******************按流水号查询******************");
		try {
			LOGGER.info("交易流水号:"+ Arrays.asList(vo.getPosCardManageDto().getSerialNos()));
			PosCardManageDto dto = posCardManageService.queryPosCardBySerialNos(vo
					.getPosCardManageDto());
			// 设置总行数
			this.setTotalCount(Long.valueOf(dto.getCount()));
			vo.setPosCardManageDto(dto);
		}catch(SettlementException e){
			// 返回界面异常
			return returnError(e.getErrorCode());
		}
		
		
		return returnSuccess();
	}

	/**
	 * 按单号查询
	 * 
	 * @Title: queryposCardManageByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	private String queryposCardManageByNumber() {
		try {
			LOGGER.info("**************按单号查询--单号："
					+ Arrays.asList(vo.getPosCardManageDto().getInvoices()));
			PosCardManageDto dto = posCardManageService.queryPosCardByNumber(vo
					.getPosCardManageDto());
			// 设置总行数
			this.setTotalCount(Long.valueOf(dto.getCount()));
			vo.setPosCardManageDto(dto);
		} catch (SettlementException e) {
			// 返回界面异常
			return returnError(e.getErrorCode());
		}
		// 返回界面
		return returnSuccess();
	}

	/**
	 * 根据流水号查询POS刷卡管理明细
	 * 
	 * @Title: queryPostCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	public String queryPostCardDetail() {
		try {
			LOGGER.info("流水号：" + vo.getPosCardManageDto().getTradeSerialNo());
			LOGGER.info("***********根据流水号查询POS刷卡管理明细Action***************");
			PosCardManageDto dto = posCardManageService.queryPosCardDetail(vo
					.getPosCardManageDto());
			vo.setPosCardManageDto(dto);
			LOGGER.info("***********根据流水号查询POS刷卡管理明细Action***************");
		} catch (SettlementException e) {
			// 返回界面异常
			return returnError(e.getErrorCode());
		}
		// 返回界面
		return returnSuccess();
	}

	/**
	 * 导出
	 * 
	 * @Title: exportPostCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public String exportPostCardDetail() {
		// 输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			
			LOGGER.info("*****************导出操作开始*****************");
			// 获取前台传递的参数
			PosCardManageDto posCardManageDto = vo.getPosCardManageDto();
			LOGGER.info("posCardManageDto :" +posCardManageDto);
			/*if (posCardManageDto.getInvoices() != null) {
				//按单号查询导出 
				//queryposCardManageByNumber();
			} else {*/
			if (vo.getPosCardManageDto().getPeriodEndDate() != null) {
				// 结束日期加1天
				vo.getPosCardManageDto().setPeriodEndDate(
						DateUtils.convert(DateUtils.addDay(vo
								.getPosCardManageDto().getPeriodEndDate(),
								1)));
			}
			/**
			 * 假如是按日期，又是自己部导出，则限定时间
			 */
			if("TD".equals(posCardManageDto.getQueryTabType())&&"true".equals(posCardManageDto.getIsExport())){
				if(DateUtils.getTimeDiff(posCardManageDto.getPeriodBeginDate(),posCardManageDto.getPeriodEndDate())>SettlementReportNumber.TEN){
					throw new SettlementException("资金部导出时间不能超过10天，请重新选择查询！");
				}
			}
			/*}*/
			// 设置excel名称
			try { // 设置excel名称
				this.setFileName(new String((EXPROTNAME)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				// 抛出异常
				throw new SettlementException("导出文件名编码转化错误！");
			}
			// 调用接口获取导出excel
			HSSFWorkbook wookBook = posCardManageService.exportPosCard(
					vo.getPosCardManageDto(), 0, Integer.MAX_VALUE);
			try {
				// 将excel写到输出流中
				wookBook.write(baos);
				// 进行流包装
				stream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				// 抛出异常
				throw new SettlementException("生成excel流错误！");
			}
			// 返回成功
			return returnSuccess();
		} catch (BusinessException e) {
			//错误编码
			errorMessage = e.getErrorCode();
			//返回失败
			return returnError(e.getMessage());
		} finally {
			// 关闭流
			if (baos != null) {
				try {
					// 关闭流
					baos.close();
				} catch (IOException e) {
					// 抛出异常
					throw new SettlementException("流关闭错误！");
				}
			}
		}
	}
	
	/**
	 * 冻结
	 * 
	 * @Title: frozenPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 */
	public String frozenPostCard() {
		LOGGER.info("********************冻结POS机刷卡管理数据开始!********************");
		try {
		LOGGER.info("冻结数据条数：" + vo.getPosCardManageDto().getPosCardEntitys().size());
		 List<PosCardEntity> posCardEntitys = vo.getPosCardManageDto().getPosCardEntitys();
		 posCardManageService.frozenPostCard(posCardEntitys);
		}catch(BusinessException e){
			//错误编码
			errorMessage = e.getErrorCode();
			//返回失败
			return returnError(e.getMessage());
		}
		LOGGER.info("********************冻结POS机刷卡管理数据结束!********************");
		return returnSuccess();
	}
	
	/**
	 * 取消冻结
	 * 
	 * @Title: thawPostCard
	 * @author： 357637 |yuanhuijun001@deppon.com
	 */
	public String thawPostCard() {
		LOGGER.info("********************取消冻结POS机刷卡管理数据开始!********************");
		try {
		LOGGER.info("取消冻结数据条数：" + vo.getPosCardManageDto().getPosCardEntitys().size());
		 List<PosCardEntity> posCardEntitys = vo.getPosCardManageDto().getPosCardEntitys();
		 posCardManageService.thawPostCard(posCardEntitys);
		}catch(BusinessException e){
			//错误编码
			errorMessage = e.getErrorCode();
			//返回失败
			return returnError(e.getMessage());
		}
		LOGGER.info("********************取消冻结POS机刷卡管理数据结束!********************");
		return returnSuccess();
	}
	/*********** getter**setter *************/
	public void setVo(PosCardManageVo vo) {
		this.vo = vo;
	}

	public PosCardManageVo getVo() {
		return vo;
	}

	public void setPosCardManageService(
			IPosCardManageService posCardManageService) {
		this.posCardManageService = posCardManageService;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setStream(ByteArrayInputStream stream) {
		this.stream = stream;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getFileName() {
		return fileName;
	}

	public ByteArrayInputStream getStream() {
		return stream;
	}
}
