package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfostEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrNrfostEntityVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发应收月报
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-7 上午10:17:01
 */
public class MvrNrfostAction extends AbstractAction {

	/**
	 * 始发应收月报action序列号
	 */
	private static final long serialVersionUID = -7990595546324470754L;

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger(MvrNrfostAction.class);

	/**
	 * 始发应收Vo
	 */
	private MvrNrfostEntityVo mvrNrfostEntityVo;

	/**
	 * 始发应收service
	 * 
	 */
	private IMvrNrfostEntityService mvrNrfostEntityService;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;

	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService productService;

	/**
	 * @SET
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		/*
		 * @set
		 * 
		 * @this.productService = productService
		 */
		this.productService = productService;
	}

	/**
	 * 查询始发应收月报信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-7 上午10:10:54
	 */
	@JSON
	public String queryMvrNrfost() {

		try {

			MvrNrfostEntityQueryDto dto = mvrNrfostEntityVo.getMvrNrfostEntityQueryDto();
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 查询始发应收月报信息
			// param：参数dto，分页数据
			MvrNrfostEntityResultDto mvrNrfostEntityResultDto = mvrNrfostEntityService
					.selectByConditionsAndPage(dto, this.getStart(),
							this.getLimit());

			// 设置返回值
			mvrNrfostEntityVo.setMvrNrfostEntityResultDto(mvrNrfostEntityResultDto);
			// 设置条数
			this.setTotalCount(mvrNrfostEntityResultDto.getMvrNrfostEntityTotalRows());

			// 正常返回
			return returnSuccess();
			// 异常处理
		} catch (BusinessException be) {
			// 记录日志
			logger.error(be.getMessage(), be);
			// 异常返回
			return returnError(be);
		}

	}
     
	/**
	 * 导出始发应收
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-13 下午1:40:58
	 */
	@SuppressWarnings("unchecked")
	public String exportMvrNrfost() {

		try {

			// 导出02特殊业务始发月报表参数不能为空
			if (mvrNrfostEntityVo == null) {
				// 提示查询参数不能为空
				throw new SettlementException("查询参数不能为空");
			}

			MvrNrfostEntityQueryDto dto = mvrNrfostEntityVo.getMvrNrfostEntityQueryDto();

			// 导出02特殊业务始发月报表期间不能为空
			if (StringUtils.isEmpty(dto.getPeriod())) {
				// 提示导出02特殊业务始发月报表期间不能为空
				throw new SettlementException("导出02特殊业务始发月报表期间不能为空");
			}

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			// 导出文件名称：
			// 02特殊业务始发月报表_+账期构成
			try {
				String exportExcelName = "02特殊业务始发月报表" + dto.getPeriod() + ".xls";
				/*this.setExeclName(URLEncoder.encode(exportExcelName,
						SettlementConstants.UNICODE_UTF));*/
				
				// 转化编码
				this.setExeclName(new String((exportExcelName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}

			// 查询导出数据
			List<MvrNrfostEntity> queryList = mvrNrfostEntityService
					.selectByConditions(dto).getMvrNrfostEntityList();
			// 查询结果不能为空
			if (CollectionUtils.isEmpty(queryList)) {
				// 提示导出02特殊业务始发月报表期间不能为空
				throw new SettlementException("导出02特殊业务始发月报表查询数据为空");
			}

			// 生成导出数据源类
			ExportResource sheet = new ExportResource();
			// 设置表单表头
			sheet.setHeads(this.getExcelHeads());

			Object[] dataAndType = this.exportDataAndType(queryList);
			// 处理返回的结果集

			List<List<String>> dataList = (List<List<String>>) dataAndType[0];
			Map<Integer, String> type = (Map<Integer, String>) dataAndType[1];

			// 设置表单数据
			sheet.setRowList(dataList);

			sheet.setHeaderHeight(SettlementReportNumber.THREE);

			HeaderRows[] headerCfg = {
					new HeaderRows(SettlementReportNumber.ZERO,    SettlementReportNumber.ONE,     SettlementReportNumber.ZERO,     SettlementReportNumber.TEN,    "数据统计维度"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ZERO,     SettlementReportNumber.ELEVEN,    SettlementReportNumber.TWENTY_FIVE,    "理赔"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.TWENTY_SIX,    SettlementReportNumber.TWENTY_SEVEN,    "异常冲收入"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.THIRTY_EIGHT,    SettlementReportNumber.THIRTY_NINE,    "坏账损失"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ZERO,     SettlementReportNumber.THIRTY,    SettlementReportNumber.SIXTY_TWO,    "小票"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.SIXTY_THREE,    SettlementReportNumber.SIXTY_FIVE,    "弃货、违禁品、全票丢货"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ZERO,     SettlementReportNumber.SIXTY_SIX,    SettlementReportNumber.EIGHTY,    "退运费"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.EIGHTY_ONE,    SettlementReportNumber.EIGHTY_TWO,    "装卸费"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.EIGHTY_THREE,    SettlementReportNumber.EIGHTY_FOUR,    "服务补救"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ZERO,     SettlementReportNumber.EIGHTY_FIVE,    SettlementReportNumber.ONE_HUNDRED,   "预收客户"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.ONE_HUNDRED_AND_ONE,   SettlementReportNumber.ONE_HUNDRED_AND_TWO,   "偏线代理成本"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.ONE_HUNDRED_AND_THREE,   SettlementReportNumber.ONE_HUNDRED_AND_FOUR,   "预收偏线代理"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.ONE_HUNDRED_AND_FIVE,   SettlementReportNumber.ONE_HUNDRED_AND_SIX,   "预收空运/快递代理"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.ONE_HUNDRED_AND_SEVEN,   SettlementReportNumber.ONE_HUNDRED_AND_TEN,   "空运/快递代理应付冲应收"),
					new HeaderRows(SettlementReportNumber.ZERO,     SettlementReportNumber.ONE,     SettlementReportNumber.ONE_HUNDRED_AND_ELEVEN,   SettlementReportNumber.ONE_HUNDRED_AND_TWELVE,   "偏线应收冲应付"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.ELEVEN,    SettlementReportNumber.TWENTY,    "出发部门申请"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.TWENTY_ONE,    SettlementReportNumber.TWENTY_NINE,    "到达部门申请"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.THIRTY,    SettlementReportNumber.THIRTY_NINE,    "小票录入现金"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.FORTY,    SettlementReportNumber.FORTY_EIGHT,    "小票录入银行"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.FORTY_NINE,    SettlementReportNumber.FORTY_NINE,    "小票录入应收"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.FIFTY,    SettlementReportNumber.SIXTY_FIVE,    "小票应收核销"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.SIXTY_SIX,    SettlementReportNumber.SEVENTY_FIVE,    "出发部门申请"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.SEVENTY_SIX,    SettlementReportNumber.EIGHTY_FOUR,    "到达部门申请"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.EIGHTY_FIVE,    SettlementReportNumber.EIGHTY_SIX,    "预收客户【01】"),
					new HeaderRows(SettlementReportNumber.ONE,     SettlementReportNumber.ONE,     SettlementReportNumber.EIGHTY_SEVEN,    SettlementReportNumber.ONE_HUNDRED_AND_TWELVE,   "预收客户【02】")
			};
			sheet.setHeaderList(Arrays.asList(headerCfg));

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();

			// 设置sheet名称
			String exprotSheetName = mvrNrfostEntityVo.getMvrNrfostEntityQueryDto()
					.getPeriod().toString();
			exportSetting.setSheetName(exprotSheetName);
			exportSetting.setDataType(type);

			// 创建导出工具类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();

			// 导出成文件
			inputStream = objExporterExecutor.exportSync(sheet, exportSetting);

			// 正常返回
			return returnSuccess();
			// 异常处理
		} catch (BusinessException e1) {
			// 记录日志
			logger.error(e1.getMessage(), e1);
			// 异常返回
			return returnError(e1);
			// 异常处理
		}
	}

	/**
	 * 始发应收报表表头
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-18 下午6:06:58
	 * @return
	 */
	private String[] getExcelHeads() {

		// 始发应收报表导出数据的表头
		String[] header = {
				// 数据统计维度
				"期间", "运输性质", "客户编码", "客户名称", "始发部门编码", "始发部门名称", "到达部门编码",
				"到达部门名称", "统一结算类型",  "合同部门编码", "合同部门名称",
				"02理赔冲收入", "02理赔入成本", "01理赔冲02始发应收已签收",
				"02理赔冲01始发应收已签收", "02理赔冲02始发应收已签收", "01理赔冲02始发应收未签收",
				"02理赔冲01始发应收未签收", "02理赔冲02始发应收未签收", "01理赔付款申请", "02理赔付款申请",
				"02理赔冲收入", "01理赔冲02到达应收已签收", "02理赔冲02到达应收已签收",
				"01理赔冲02到达应收未签收", "02理赔冲02到达应收未签收", "02应收始发运费已签收",
				"02应收到付运费已签收", "坏账冲02应收始发运费已签收", "坏账冲02应收到付运费已签收",
				"小票现金之事故赔款 ", "小票现金之变卖废品收入", "小票现金之客户多付运费或盘点长款金额 ", "小票现金之其他",
				"小票现金之富余仓库出租收入",
				"01小票现金主营业务收入", "小票现金之收银员卡利息","小票现金之手续费","小票现金之叉车费","02小票现金主营业务收入", "小票银行之事故赔款 ", "小票银行之收银员卡利息",
				"小票银行之客户多付运费或盘点长款金额 ", "小票银行之其他","小票银行之富余仓库出租收入","小票银行之手续费","小票银行之叉车费", "01小票银行主营业务收入",
				"02小票银行主营业务收入", "02小票应收主营业务收入", "还款现金冲01小票应收", "还款银行冲01小票应收",
				"还款现金冲02小票应收", "还款银行冲02小票应收", "应付代收货款冲02小票应收", "02应付理赔冲02小票应收",
				"01应付理赔冲02小票应收", "02应付理赔冲01小票应收", "02预收客户冲02小票应收",
				"01预收客户冲02小票应收", "02预收客户冲01小票应收", "异常冲收入冲02小票应收",
				"坏账损失冲02小票应收", "开单且为月结临时欠款网上支付未核销", "开单且为月结临时欠款网上支付已核销",
				"开单且为现金银行卡", "02退运费冲收入", "02退运费入成本", "01退运费付款申请", "02退运费付款申请",
				"02退运费冲02始发应收已签收", "01退运费冲02始发应收已签收", "02退运费冲01始发应收已签收",
				"02退运费冲02始发应收未签收", "01退运费冲02始发应收未签收", "02退运费冲01始发应收未签收",
				"02退运费冲收入", "01退运费冲02到达应收已签收", "02退运费冲02到达应收已签收",
				"01退运费冲02到达应收未签收", "02退运费冲02到达应收未签收", "01装卸费付款申请", "02装卸费付款申请",
				"01始发服务补救付款申请", "02始发服务补救付款申请","预收客户现金","预收客户银行","预收客户现金","预收客户银行","01预收客户冲02应收到付运费未签收",
				"02预收客户冲02应收到付运费未签收","01预收客户冲02应收到付运费已签收","02预收客户冲02应收到付运费已签收",
				"02预收客户冲02应收始发运费未签收","01预收客户冲02应收始发运费未签收","02预收客户冲01应收始发运费未签收",
				"02预收客户冲02应收始发运费已签收","01预收客户冲02应收始发运费已签收","02预收客户冲01应收始发运费已签收",
				"01始发退预收付款申请","02始发退预收付款申请","应付偏线代理成本冲02应收到付运费已签收","应付偏线代理成本冲02应收到付运费未签收",
				"预收偏线代理冲02应收到付运费已签收","预收偏线代理冲02应收到付运费未签收","预收空运/落地配代理冲02应收到付运费已签收",
				"预收空运/落地配代理冲02应收到付运费未签收","应付到达代理/落地配代理成本冲02应收到付运费已签收",
				"应付到达代理/落地配代理成本冲02应收到付运费未签收","其他应付冲02应收到付运费已签收","其他应付冲02应收到付运费未签收","其他应付冲02应收到付运费已签收","其他应付冲02应收到付运费未签收" };

		// 返回excel表头
		return header;
	}

	/**
	 * 生成导出始发报表数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-18 下午4:09:28
	 * @param queryList
	 * @return
	 */
	private Object[] exportDataAndType(List<MvrNrfostEntity> queryList) {

		String[] properties = new String[] { "period", "productCode",
				"customerCode", "customerName", "origOrgCode", "origOrgName",
				"destOrgCode", "destOrgName", "unifiedSettlementType", "contractOrgCode", "contractOrgName", 
				"claimOrigtIncome",	"claimOrigtCost", "claimOrigoWoOrigRcvtPod",
				"claimOrigtOrigRcvoPod", "claimOrigtWoOrigRcvtPod",
				"claimOrigoWoOrigRcvtNpod", "claimOrigtWoOrigRcvoNpod",
				"claimOrigtWoOrigRcvtNpod", "claimOrigoPayApply",
				"claimOrigtPayApply", "claimDesttIncome",
				"claimDestoWoDestRcvtPod", "claimDesttWoDestRcvtPod",
				"claimDestoWoDestRcvtNpod", "claimDesttWoDestRcvtNpod",
				"exOrigRcvtPod", "exDestRcvtPod", "bdWoOrigRcvtPod",
				"bdWoDestRcvtPod", "orChAc", "orChSi", "orChOpay", "orChOther","orChRentIncome",
				"orChPbio","orChBankInt","orChHc","orChForklift", "orChPbit", "orCdAc", "orCdBankInt", "orCdOpay",
				"orCdOther","orCdRentIncome","orCdHc","orCdForklift", "orCdPbio", "orCdPbit", "orRcvtPbi", "orChUrRcvo",
				"orCdUrRcvo", "orChUrRcvt", "orCdUrRcvt", "orCodPayWoRcvt",
				"orClaimPaytWoRcvt", "orClaimPayoWoRcvt", "orClaimPaytWoRcvo",
				"orCustDrtWoRcvt", "orCustDroWoRcvt", "orCustDrtWoRcvo",
				"orExWoRcvt", "orBadWoRcvt", "acOrigRcvNwo", "acOrigRcvWo",
				"acCash", "rdOrigtIncome", "rdOrigtCost", "rdOrigoPayApply",
				"rdOrigtPayApply", "rdOrigtWoOrigRcvtPod",
				"rdOrigoWoOrigRcvtPod", "rdOrigtWoOrigRcvoPod",
				"rdOrigtWoOrigRcvtNpod", "rdOrigoWoOrigRcvtNpod",
				"rdOrigtWoOrigRcvoNpod", "rdDesttIncome", "rdDestoDestRcvtPod",
				"rdDesttWoDestRcvtPod", "rdDestoWoDestRcvtNpod",
				"rdDesttWoDestRcvtNpod", "sfoPayApply", "sftPayApply",
				"cpoOrigPayApply", "cptOrigPayApply","custDrOch","custDrOcd","custDrTch", 
				"custDrTcd","custDroWoDestRcvtNpod","custDrtWoDestRcvtNpod",
				"custDroWoDestRcvtPod","custDrtWoDestRcvtPod","custDrtWoOrigRcvtNpod",
				"custDroWoOrigRcvtNpod","custDrtWoOrigRcvoNpod","custDrtWoOrigRcvtPod",
				"custDroWoOrigRcvtPod","custDrtWoOrigRcvoPod","custDroPayApply",
				"custDrtPayApply","plCostWoDestRcvtPod","plCostWoDestRcvtNpod",
				"plDrWoDestRcvtPod","plDrWoDestRcvtNpod","alDrWoDestRcvtPod",
				"alDrWoDestRcvtNpod","alpwrWoDestRcvtPod","alpwrWoDestRcvtNpod",
				"othPayWoDestRcvtPod","othPayWoDestRcvtNpod","popWoDrtPod","popWoDrtNpod"
 };

		List<String> strProperties = Arrays.asList(new String[] { "period",
				"productCode", "customerCode", "customerName", "origOrgCode",
				"origOrgName", "destOrgCode", "destOrgName", 
				"unifiedSettlementType", "contractOrgCode", "contractOrgName" });

		// 生成返回数据集合
		List<List<String>> resultList = new ArrayList<List<String>>();

		// 获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (CollectionUtils.isNotEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}

		List<String> data = null;
		Object fieldValue = null;
		String cellValue = null;

		// 循环处理
		for (MvrNrfostEntity entity : queryList) {

			data = new ArrayList<String>();
			for (String property : properties) {
				// 获取对象的值，如果为空，则设置其为空字符串
				fieldValue = ReflectionUtils.getFieldValue(entity, property);
				cellValue = (fieldValue == null ? "" : fieldValue.toString());

				// 产品类型需要转换
				if ("productCode".equalsIgnoreCase(property)) {

					// 如果数据产品类型编码不为空
					if (StringUtils.isNotEmpty(cellValue)) {
						// 将产品类型转换编码为名称
						cellValue = productMap.get(cellValue);
					}
				}
				// 统一结算类型
				if ("unifiedSettlementType".equalsIgnoreCase(property)) {
					// 如果统一结算类型不为空
					if (StringUtils.isNotEmpty(cellValue)) {
						if(SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_ORIG.equals(cellValue)){
							cellValue = "始发统一结算";
						}else if(SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_DEST.equals(cellValue)){
							cellValue = "到达统一结算";
						}else{
							cellValue = "非统一结算";
						}
					}
				}
				data.add(cellValue);
			}

			resultList.add(data);
		}

		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		for (int i = 0; i < properties.length; i++) {
			String type = strProperties.contains(properties[i]) ? "string"
					: "float";
			typeMap.put(i, type);
		}

		// 返回
		return new Object[] { resultList, typeMap };
	}

	/**
	 * @return mvrNrfostEntityVo
	 */
	public MvrNrfostEntityVo getMvrNrfostEntityVo() {
		return mvrNrfostEntityVo;
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
	 * @return execlName
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * @param execlName
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	/**
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param mvrNrfostEntityService
	 */
	public void setMvrNrfostEntityService(IMvrNrfostEntityService mvrNrfostEntityService) {
		this.mvrNrfostEntityService = mvrNrfostEntityService;
	}

	/**
	 * @param mvrNrfostEntityVo
	 */
	public void setMvrNrfostEntityVo(MvrNrfostEntityVo mvrNrfostEntityVo) {
		this.mvrNrfostEntityVo = mvrNrfostEntityVo;
	}

}
