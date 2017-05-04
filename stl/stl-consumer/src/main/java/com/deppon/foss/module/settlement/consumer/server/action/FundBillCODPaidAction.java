package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCODStateService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOfflineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODVo;

/**
 * 代收货款资金部付款管理Action.
 * 
 * @author ibm-guxinhua
 * @date 2012-10-23 上午11:12:32
 */
public class FundBillCODPaidAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6345147438312615698L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LogManager
			.getLogger(FundBillCODPaidAction.class);

	/** 代收货款离线付款服务. */
	private IBillPayCODOfflineService billPayCODOfflineService;

	/** 在线支付代收货款服务. */
	private IBillPayCODOnlineService billPayCODOnlineService;

	/** 代收货款状态管理服务. */
	private IBillCODStateService billCODStateService;

	/** 代收货款记录管理服务. */
	private ICodCommonService codCommonService;

	/** 代收货款VO. */
	private CODVo codFundBillVO;
	
	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/** execl文件名. */
	private String execlName;

	/** 汇款导出execl文件名. */
	private static final String COD_EXPORT_EXECL_NAME = "代收货款信息";

	/** 全选后操作 */
	private static final String COD_ALL_CHECKBOX_STATUS = "ALL_CHECKBOX";
	
	/**
	 * 排序字段
	 */
	private String sortProperty;
	
	/**
	 * 排序方式
	 */
	private String sortDirection;

	/**
	 * 资金线下付款，导出应付代收货款并核销代收货款应付单.
	 * 
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-23 上午11:14:17
	 */
	public String payCODOffline() {

		try {
			if (null == codFundBillVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}
			
			/*// 获取mapper,进行前台转换
			ObjectMapper mapper = new ObjectMapper();
			// 将前台出入json字符串转化为对象
			codFundBillVO = mapper.readValue((String) codFundBillVOStr ,CODVo.class);*/
						
			CODDto coddto = null; // 返回结果
			String waybillNoStr = codFundBillVO.getWaybillNos();
			// 判断是否全选冻结操作,并通过日期查询页面操作
			if (StringUtils.equals(codFundBillVO.getAllCheckBoxStatus(),
					COD_ALL_CHECKBOX_STATUS) && StringUtils.isEmpty(waybillNoStr)) {
				
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				
				CODDto dto = this.vaildCodFundBillToParam( codFundBillVO); // 参数返回
				coddto = billPayCODOfflineService.doWithExportBillCODAll(
						dto.getSignDate(), dto.getCodTypes(), dto.getBankList(), dto.getPublicPrivateFlag(),
						currentInfo);
			} else {

				String[] entityIds = codFundBillVO.getEntityIds();

				if (ArrayUtils.isEmpty(entityIds)) {
					// 代收货款参数为空
					throw new SettlementException("代收货款参数为空");
				}

				// 处理导出代收货款数据
				coddto = billPayCODOfflineService.doWithExportBillCOD(
						Arrays.asList(entityIds),
						FossUserContext.getCurrentInfo());

			}

			// 获得批次号
			String batchNumber = coddto.getBatchNumber(); // 由于每一批的批次号是一致的，可取第一条批次号
			String typeName = this.changeCodTypeBatchNoToName(batchNumber);

			// 导出文件名称：
			try {
				this.setExeclName(new String((typeName + "+" + batchNumber)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(coddto.getCodList());
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName(COD_EXPORT_EXECL_NAME);
			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,exportSetting));
			
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("代收货款汇款导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("代收货款汇款导出异常：" + e.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("代收货款汇款导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("代收货款汇款导出异常：" + e.getMessage());
		} 
	}

	/**
	 * 获得execl报表数据
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param mvrRfdEntityList
	 */
	private ExportResource exportRfdResource(List<CODPayableDto> codPayableList){
		
		ExportResource sheet = new ExportResource();
		//设置表单表头
		sheet.setHeads(new String[] { "类别", "所属子公司", "出发部门", "运单号",
				"收款人", "金额", "账号", "开户行", "省", "市", "支行", "对公对私标志", "手机号码",
				"签收日期", "银行行号", "汇款导出时间", "汇款导出人", "批次号" });
		// 处理返回的结果集
		List<List<String>> resultList = this.exportBillCod(codPayableList);
		//设置表单数据
		sheet.setRowList(resultList);
		
		return sheet;
	}
	
	/**
	 * 代收货款综合查询数据
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:48
	 * @param mvrRfdEntityList
	 * @return
	 */
	private List<List<String>> exportBillCod(List<CODPayableDto> codPayableList) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> cellList = null;

		List<String> types = new ArrayList<String>();
		types.add(DictionaryConstants.COD__COD_TYPE);// 代收货款类型
		types.add(DictionaryConstants.COD__PUBLIC_PRIVATE_FLAG);// 对公对私标志
		// 后台获取数据字典对应的数据
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(types);

		for (CODPayableDto codDto : codPayableList) {
			cellList = new ArrayList<String>();
			// 类别
			cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
					DictionaryConstants.COD__COD_TYPE, codDto.getCodType()));
			// 所属子公司
			cellList.add(codDto.getPayableComName());
			// 出发部门
			cellList.add(codDto.getPayablePayableOrgName());
			// 运单号
			cellList.add(codDto.getWaybillNo());
			// 收款人
			cellList.add(codDto.getPayeeName());

			// 金额
			cellList.add(String.valueOf(codDto.getUnverifyAmount()));

			// 账号
			cellList.add(codDto.getAccountNo());
			// 开户行
			cellList.add(codDto.getBankHQName());
			// 省
			cellList.add(codDto.getProvinceName());
			// 市
			cellList.add(codDto.getCityName());
			// 支行
			cellList.add(codDto.getBankBranchName());
			// 对公对私状态
			cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,
					DictionaryConstants.COD__PUBLIC_PRIVATE_FLAG,
					codDto.getPublicPrivateFlag()));
			// 电话号码
			cellList.add(codDto.getPayeePhone());
			// 签收时间
			cellList.add(null == codDto.getSignDate() ? "" : sdf.format(codDto
					.getSignDate()));
			// 银行编码
			cellList.add(codDto.getBankBranchCode());
			// 导出时间
			cellList.add(null == codDto.getCodExportTime() ? "" : sdf
					.format(codDto.getCodExportTime()));
			// 导出人
			cellList.add(codDto.getCodExportName());
			// 批次号
			cellList.add(codDto.getBatchNumber());
			rowList.add(cellList);
		}
		
		return rowList;
	}
	
	
	/**
	 * 资金部在线付款，发送付款请求至费控系统.
	 * 
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-23 上午11:14:53
	 */
	@JSON
	public String payCODOnline() {
		try {
			if (null == codFundBillVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}
			// 获得批次号
			String batchNumber = null;
			String waybillNoStr = codFundBillVO.getWaybillNos();
			// 判断是否全选冻结操作,并通过日期查询页面操作
			if (StringUtils.equals(codFundBillVO.getAllCheckBoxStatus(),
					COD_ALL_CHECKBOX_STATUS) && StringUtils.isEmpty(waybillNoStr)) {
				
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				
				CODDto dto = this.vaildCodFundBillToParam( codFundBillVO);

				batchNumber = billPayCODOnlineService
						.doWithSendBillPaybleToBankAll(dto.getSignDate(), dto.getCodTypes(), dto.getBankList(), dto.getPublicPrivateFlag(),
								currentInfo);
			} else {
				String[] entityIds = codFundBillVO.getEntityIds();
				if (ArrayUtils.isEmpty(entityIds)) {
					// 代收货款参数为空
					throw new SettlementException("代收货款参数为空");
				}

				List<String> entityIdList = Arrays.asList(entityIds);

				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				/**
				 * @author 218392 zhangyongxue 2015-09-01 09:14:59
				 * 下面这段主要的目的是：把前台页面代收货款类型获取到，方便service层中使用
				 * 虽然mapper文件中，是不需要代收货款类型作为条件了，因为只需要根据ID即可，但是service中需要根据
				 * 代收货款类型中打包还是不打包来进行封装实体集合的。
				 */
				String codTypeStr = codFundBillVO.getCodType();
				CODDto dto = new CODDto();
				if (!StringUtils.isEmpty(codTypeStr)) {
					// 增加退单类型
					List<String> codTypes = new ArrayList<String>();
					String[] codTypeArray = StringUtils.split(codTypeStr, ',');
					for (String codType : codTypeArray) {
						codTypes.add(codType);
					}
					dto.setCodTypes(codTypes);
				}

				// 处理线上汇代收货款服务
				batchNumber = billPayCODOnlineService
						.doWithSendBillPaybleToBank(entityIdList, dto.getCodTypes(), currentInfo);

			}

			codFundBillVO.setBatchNumber(batchNumber);// 批次号给予页面显示
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 冻结代收货款.
	 * 
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-23 上午11:15:14
	 */
	@JSON
	public String freezeCOD() {
		try {
			if (null == codFundBillVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}
			CODVo vo = null;
			String waybillNoStr = codFundBillVO.getWaybillNos();
			// 判断是否全选冻结操作,并通过日期查询页面操作
			if (StringUtils.equals(codFundBillVO.getAllCheckBoxStatus(),
					COD_ALL_CHECKBOX_STATUS)&& StringUtils.isEmpty(waybillNoStr)) {
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				
				CODDto dto = this.vaildCodFundBillToParam( codFundBillVO);

				vo = billCODStateService.fundFreezeCODAll(dto.getSignDate(), dto.getCodTypes(), dto.getBankList(), dto.getPublicPrivateFlag(),
						currentInfo);
			} else {
				String[] entityIds = codFundBillVO.getEntityIds();
				if (ArrayUtils.isEmpty(entityIds)) {
					// 代收货款参数为空
					throw new SettlementException("代收货款参数为空");
				}
				// 资金部冻结代收货款
				vo = billCODStateService.fundFreezeCOD(
						Arrays.asList(entityIds),
						FossUserContext.getCurrentInfo());
			}

			// 设置vo到页面上
			this.setCodFundBillVO(vo);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 反冻结代收货款.
	 * 
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-23 上午11:15:40
	 */
	@JSON
	public String releaseCOD() {
		try {
			// 查询参数无效
			if (null == codFundBillVO) {
				throw new SettlementException("查询参数无效");
			}
			String waybillNoStr = codFundBillVO.getWaybillNos();
			// 判断是否全选冻结操作
			if (StringUtils.equals(codFundBillVO.getAllCheckBoxStatus(),
					COD_ALL_CHECKBOX_STATUS)&& StringUtils.isEmpty(waybillNoStr)) {
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				
				CODDto dto = this.vaildCodFundBillToParam( codFundBillVO);

				billCODStateService.fundReleaseCODAll(dto.getSignDate(), dto.getCodTypes(), dto.getBankList(), dto.getPublicPrivateFlag(),
						currentInfo);
			} else {

				String[] entityIds = codFundBillVO.getEntityIds();
				if (ArrayUtils.isEmpty(entityIds)) {
					// 代收货款参数为空
					throw new SettlementException("代收货款参数为空");
				}
				// 释放冻结代收货款
				billCODStateService.fundReleaseCOD(Arrays.asList(entityIds),
						FossUserContext.getCurrentInfo());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 查询代收货款应付单.
	 * 
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-10-23 上午11:15:59
	 */
	@JSON
	public String queryBillPayableCOD() {
		try {
			if (null == codFundBillVO) {
				// 查询参数无效
				throw new SettlementException("查询参数无效");
			}

			List<String> waybillNos = null;
			List<CODDto> codDtoList = null;
			CODDto totalDto = null;
			String waybillNoStr = codFundBillVO.getWaybillNos();
			if (!StringUtils.isEmpty(waybillNoStr)) {// 输入单号进行查询，则忽略界面其他查询条件。只按照单号进行查询。
				// 增加单号
				waybillNos = new ArrayList<String>();
				String[] waybillNoArray = StringUtils.split(waybillNoStr, ',');
				for (String waybillNo : waybillNoArray) {
					waybillNos.add(waybillNo);
				}
				totalDto = codCommonService
						.queryBillCODPayableByWaybillNosSum(waybillNos); // 查询汇总数据
				if (null != totalDto && totalDto.getTotalCount().intValue() > 0) {
					// 查询数据
					// 按运单号查询可支付的代收货款
					codDtoList = codCommonService
							.queryBillCODPayableByWaybillNos(waybillNos,this.getSortProperty(),this.getSortDirection());
				}
			} else {// 如果输入单号为空，则查询条件按照界面其它四个控件所选条件继续进行查询。
				
				CODDto dto = this.vaildCodFundBillToParam( codFundBillVO);

				totalDto = billPayCODOfflineService.queryBillCODPayableSum(
						dto.getSignDate(), dto.getCodTypes(), dto.getBankList(), dto.getPublicPrivateFlag());// 查询汇总数据
				if (null != totalDto && totalDto.getTotalCount().intValue() > 0) {
					// 查询数据
					// 资金部汇款界面查询
					// 【代收货款状态不能为空，截止签收日期、银行、对公对私标志可为空】
					codDtoList = billPayCODOfflineService.queryBillCODPayable(
							dto.getSignDate(), dto.getCodTypes(), dto.getBankList(), dto.getPublicPrivateFlag(),
							getStart(), getLimit(),this.getSortProperty(),this.getSortDirection());
				}
			}
			codFundBillVO.setCods(codDtoList);// 设置到VO对象中
			if (null != totalDto && totalDto.getTotalCount().intValue() > 0) { // 设置页面统计值
				this.setTotalCount(totalDto.getTotalCount());
				codFundBillVO.setTotalCount(totalDto.getTotalCount());
				codFundBillVO.setTotalAmount(totalDto.getTotalAmount());
				codFundBillVO.setFreezeTotalCount(totalDto
						.getFreezeTotalCount());
				codFundBillVO.setFreezeTotalAmount(totalDto
						.getFreezeTotalAmount());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	/**
	 * 根据批次号获得代收货款类型名称
	 * 
	 * @author guxinhua
	 * @date 2013-3-25 上午9:45:40
	 * @param batchNumber
	 * @return
	 */
	private String changeCodTypeBatchNoToName(String batchNumber) {

		String codTypeBatchNo = StringUtils.substring(batchNumber,
				SettlementReportNumber.EIGHT, SettlementReportNumber.TEN); // 获得代收货款退批次号类型:01,02
		if (StringUtils.equals(codTypeBatchNo,
				SettlementConstants.COD_TYPE__BATCHNO_R1)) {
			return SettlementConstants.COD_TYPE__BATCHNO_R1_NAME;
		} else if (StringUtils.equals(codTypeBatchNo,
				SettlementConstants.COD_TYPE__BATCHNO_R3_RA)) {
			return SettlementConstants.COD__COD_TYPE__RETURN_3_A_DAY_NAME;
		}
		return null;
	}

	/**
	 * 验证codFundBillVO，返回CodDto实体，包括(截止时间，代收货款类型，银行，对公对私标示)
	 * @author foss-guxinhua
	 * @date 2013-5-13 上午8:57:29
	 * @param codFundBillVO
	 * @return
	 */
	private CODDto vaildCodFundBillToParam(CODVo codFundBillVO){
		
		CODDto cod = new CODDto();

		 Date endSignDate = codFundBillVO.getEndSignDate();
		// 代收货款截止签收日期不能为空
		if (endSignDate == null) {
			throw new SettlementException("代收货款截止签收日期为空");
		}
		cod.setSignDate(endSignDate);
		
		String codTypeStr = codFundBillVO.getCodType();
		String publicPrivateFlag = codFundBillVO.getPublicPrivateFlag();
		
		cod.setPublicPrivateFlag(publicPrivateFlag);
		if (!StringUtils.isEmpty(codTypeStr)) {
			// 增加退单类型
			List<String> codTypes = new ArrayList<String>();
			String[] codTypeArray = StringUtils.split(codTypeStr, ',');
			for (String codType : codTypeArray) {
				codTypes.add(codType);
			}
			cod.setCodTypes(codTypes);
		} else {
			// 代收货款类型为空，不能进行代收货款汇款导出查询
			throw new SettlementException("代收货款类型为空");
		}

		List<String> bankList = codFundBillVO.getBankList();
		// 银行非空判断
		// 转化为银行集合
		/*if (StringUtils.isNotBlank(banks)) {
			List<String> bankList = new ArrayList<String>();
			String[] bankStrArr = StringUtils.split(banks, ',');
			for (String bankStr : bankStrArr) {
				bankList.add(StringUtil.trim(bankStr));
			}*/
			cod.setBankList(bankList);
		/*} else {
			// 这里不做处理
		}*/
		
		return cod;
	}
	
	/**
	 * Sets the bill pay cod offline service.
	 * 
	 * @param billPayCODOfflineService
	 *            the new bill pay cod offline service
	 */
	public void setBillPayCODOfflineService(
			IBillPayCODOfflineService billPayCODOfflineService) {
		this.billPayCODOfflineService = billPayCODOfflineService;
	}

	/**
	 * Sets the bill pay cod online service.
	 * 
	 * @param billPayCODOnlineService
	 *            the new bill pay cod online service
	 */
	public void setBillPayCODOnlineService(
			IBillPayCODOnlineService billPayCODOnlineService) {
		this.billPayCODOnlineService = billPayCODOnlineService;
	}

	/**
	 * Sets the bill cod state service.
	 * 
	 * @param billCODStateService
	 *            the new bill cod state service
	 */
	public void setBillCODStateService(IBillCODStateService billCODStateService) {
		this.billCODStateService = billCODStateService;
	}

	/**
	 * Sets the cod common service.
	 * 
	 * @param codCommonService
	 *            the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Gets the cod fund bill vo.
	 * 
	 * @return codFundBillVO
	 */
	public CODVo getCodFundBillVO() {
		return codFundBillVO;
	}

	/**
	 * Sets the cod fund bill vo.
	 * 
	 * @param codFundBillVO
	 *            the new cod fund bill vo
	 */
	public void setCodFundBillVO(CODVo codFundBillVO) {
		this.codFundBillVO = codFundBillVO;
	}
 
	/**
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Gets the execl name.
	 * 
	 * @return execlName
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * Sets the execl name.
	 * 
	 * @param execlName
	 *            the new execl name
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	/**
	 * @return sortProperty
	 */
	public String getSortProperty() {
		return sortProperty;
	}

	/**
	 * @param sortProperty
	 */
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

	/**
	 * @return sortDirection
	 */
	public String getSortDirection() {
		return sortDirection;
	}

	/**
	 * @param sortDirection
	 */
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

}
