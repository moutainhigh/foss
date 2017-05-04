package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.ClosingPeriodDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrRfdVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 专线到达Action.
 * 
 * @author guxinhua
 * @date 2013-3-6 下午3:37:31
 */
public class MvrRfdEntityAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrRfdEntityAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 专线到达Service. */
	private IMvrRfdEntityService mvrRfdEntityService;

	/**
	 * 产品productService
	 */
	private IProductService productService;

	/** 专线到达VO. */
	private MvrRfdVo mvrRfdVo;

	private List<ClosingPeriodDto> periodList;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;   

	@JSON
	public String queryClosingPeriod() {
		periodList = mvrRfdEntityService.queryClosingPeriodList();

		return this.returnSuccess();
	}

	/**
	 * 查询专线到达.
	 * 
	 * @return the string
	 * @author guxinhua
	 * @date 2013-3-6 下午3:39:28
	 */
	@JSON
	public String query() {

		try {
			// 检查并封装查询条件
			MvrRfdDto dto = this.checkAndPackageVoToDto(mvrRfdVo);

			// 查询专线到达报表合计
			MvrRfdDto totalDto = mvrRfdEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<MvrRfdEntity> mvrRfdEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询专线到达报表
				mvrRfdEntityList = mvrRfdEntityService.selectByConditions(dto,
						start, limit);
			}

			// 设置Vo的统计Dto
			mvrRfdVo.setMvrRfdDto(totalDto);
			// 设置Vo的报表集合
			mvrRfdVo.setMvrRfdEntityList(mvrRfdEntityList);
		} catch (SettlementException e) {
			// 查询专线到达报表异常
			LOGGER.error("查询专线到达报表异常：" + e.getErrorCode(), e);
			return this.returnError(e.getErrorCode());
		}

		return this.returnSuccess();
	}

	/**
	 * 专线到达报表导出
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午3:27:30
	 * @return
	 */
	public String export() {

		try {
			// 检查并封装查询条件
			MvrRfdDto dto = this.checkAndPackageVoToDto(mvrRfdVo);

			// 查询专线到达报表合计
			MvrRfdDto totalDto = mvrRfdEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<MvrRfdEntity> mvrRfdEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询专线到达月报表
				mvrRfdEntityList = mvrRfdEntityService.selectByConditions(dto,
						0, totalDto.getCount().intValue());
			} else {
				// 提示导出专线到达月报表不能为空
				throw new SettlementException(mvrRfdVo.getPeriod()
						+ "账期的专线到达月报表数据为空!");
			}

			// 导出文件名称：
			// 专线到达月报表_+账期构成
			try {
				String exportExeclName = "专线到达月报表" + mvrRfdVo.getPeriod();				
//				this.setExeclName(URLEncoder.encode(exportExeclName,
//						SettlementConstants.UNICODE_UTF));
				
				// 转化编码
				this.setExeclName(new String((exportExeclName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(mvrRfdEntityList);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName(mvrRfdVo.getPeriod());

			Map<Integer, String> map = new HashMap<Integer, String>();
			for (int i = SettlementReportNumber.EIGHT; i <= SettlementReportNumber.TWENTY_SIX; i++) {
				map.put(i, "float");
			}

			exportSetting.setDataType(map);

			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,
					exportSetting));

			return this.returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("专线到达月报表导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("专线到达月报表导出异常：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("专线到达月报表导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("专线到达月报表导出异常：" + e.getMessage());
			// 异常处理
		}
	}

	/**
	 * 获得execl报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param mvrRfdEntityList
	 */
	private ExportResource exportRfdResource(List<MvrRfdEntity> mvrRfdEntityList) {

		ExportResource sheet = new ExportResource();
		// 设置表单表头
		sheet.setHeads(this.getExcelHeads());
		// 处理返回的结果集
		List<List<String>> resultList = this.exportMvrRfd(mvrRfdEntityList);

		sheet.setHeaderHeight(SettlementReportNumber.THREE);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.SEVEN, "数据统计维度"); // 8
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.EIGHT, SettlementReportNumber.ELEVEN, "还款运单总运费（到付）"); // 4
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWELVE, SettlementReportNumber.SIXTEEN, "理赔"); // 5
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTEEN, SettlementReportNumber.EIGHTEEN, "代收货款"); // 2
		HeaderRows rowContent5 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.NINETEEN, SettlementReportNumber.TWENTY, "营业部预收客户"); // 2
		HeaderRows rowContent6 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_ONE, SettlementReportNumber.TWENTY_FIVE, "退运费"); // 5
		HeaderRows rowContent7 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_SIX, SettlementReportNumber.TWENTY_SIX, "服务补救"); // 1
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		headerList.add(rowContent5);
		headerList.add(rowContent6);
		headerList.add(rowContent7);
		sheet.setHeaderList(headerList);

		// 设置表单数据
		sheet.setRowList(resultList);

		return sheet;
	}

	/**
	 * 专线到达报表表头
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:39
	 * @return
	 */
	private String[] getExcelHeads() {
		return new String[] {
		/**
		 * 期间, 业务类型, 客户编码, 客户名称, 始发部门编码, 始发部门名称, 到达部门编码, 到达部门名称,
		 * 还款运单总运费（到付）-还款现金未签收, 还款运单总运费（到付）-还款银行未签收, 还款运单总运费（到付）-还款现金已签收,
		 * 还款运单总运费（到付）-还款银行已签收, 理赔-理赔冲收入, 理赔-理赔入成本, 理赔-理赔冲到达应收已签收,
		 * 理赔-理赔冲到达应收未签收, 理赔-理赔付款申请, 代收货款-还款代收货款现金未签收, 代收货款-还款代收货款银行未签收,
		 * 退运费-退运费冲收入, 退运费-退运费入成本, 退运费-退运费付款申请, 服务补救-到达服务补救付款申请
		 */
				"期间", "运输性质", "客户编码", "客户名称", "始发部门编码", "始发部门名称", "到达部门编码", "到达部门名称",
				"还款现金未签收", "还款银行未签收", "还款现金已签收", "还款银行已签收", "理赔冲收入", "理赔入成本",
				"理赔冲到达应收已签收", "理赔冲到达应收未签收", "理赔付款申请", "还款代收货款现金未签收",
				"还款代收货款银行未签收", "预收客户冲应收到付运费未签收", "预收客户冲应收到付运费已签收", "退运费冲收入",
				"退运费入成本", "退运费付款申请", "退运费冲到达应收已签收", "退运费冲到达应收未签收", "到达服务补救付款申请" };
	}

	/**
	 * 专线到达报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:48
	 * @param mvrRfdEntityList
	 * @return
	 */
	private List<List<String>> exportMvrRfd(List<MvrRfdEntity> mvrRfdEntityList) {

		String[] columns = {
		/**
		 * 期间, 业务类型, 客户编码, 客户名称, 始发部门编码, 始发部门名称, 到达部门编码, 到达部门名称,
		 * 还款运单总运费（到付）-还款现金未签收, 还款运单总运费（到付）-还款银行未签收, 还款运单总运费（到付）-还款现金已签收,
		 * 还款运单总运费（到付）-还款银行已签收, 理赔-理赔冲收入, 理赔-理赔入成本, 理赔-理赔冲到达应收已签收,
		 * 理赔-理赔冲到达应收未签收, 理赔-理赔付款申请, 代收货款-还款代收货款现金未签收, 代收货款-还款代收货款银行未签收,
		 * 退运费-退运费冲收入, 退运费-退运费入成本, 退运费-退运费付款申请, 服务补救-到达服务补救付款申请
		 */

		"period", "productCode", "customerCode", "customerName", "origOrgCode",
		"origOrgName", "destOrgCode", "destOrgName", "urDestChNpod",
		"urDestCdNpod", "urDestChPod", "urDestCdPod",
		"claimDestWoIncome", "claimDestCost", "claimWoDestRcvPod",
		"claimWoDestRcvNpod", "claimDestPayApply", "codUrChNpod",
		"codUrCdNpod", "custDrDestRcvNpod", "custDrDestRcvPod",
		"rdDestWoIncome", "rdDestCost", "rdDestPayApply",
		"rdWoDestRcvPod", "rdWoDestRcvNpod", "cnDestPayApply" };

		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();

		// 获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}

		if (CollectionUtils.isNotEmpty(mvrRfdEntityList)) {
			// 列数据
			// 循环结果集
			for (MvrRfdEntity entity : mvrRfdEntityList) {
				validaList(columns, rowList, productMap, entity);
			}
		}

		return rowList;
	}

	private void validaList(String[] columns, List<List<String>> rowList,
			Map<String, String> productMap, MvrRfdEntity entity) {
		List<String> colList;
		Object fieldValue;
		String cellValue;
		colList = new ArrayList<String>();
		// 循环列
		for (String column : columns) {

			// 获取对象的值，如果为空，则设置其为空字符串
			fieldValue = ReflectionUtils.getFieldValue(entity, column);
			cellValue = (fieldValue == null ? "" : fieldValue
					.toString());
			if (StringUtils.equals(column, "productCode")) { 
				cellValue = validaProduct(productMap, cellValue);
			}
			colList.add(cellValue);
		}
		rowList.add(colList);
	}

	private String validaProduct(Map<String, String> productMap,
			String cellValue) {
		// 转换productCode值
		if (StringUtils.isNotBlank(cellValue)) {

			// 默认的产品类型为空
			String productEntityName = "";
			// 如果数据产品类型编码不为空
			if (StringUtils.isNotEmpty(cellValue)) {
				// 将产品类型转换编码为名称
				productEntityName = productMap.get(cellValue);
			}
			cellValue = productEntityName;
		}
		return cellValue;
	}

	/**
	 * 检查并封装查询条件.
	 * 
	 * @param vo
	 *            the vo
	 * @return the mvr rfd dto
	 * @author guxinhua
	 * @date 2013-3-6 上午11:44:51
	 */
	private MvrRfdDto checkAndPackageVoToDto(MvrRfdVo vo) {

		// 专线到达VO非空判断
		if (null == vo) {
			// 内部错误，专线到达VO参数为空！
			throw new SettlementException("内部错误，专线到达VO参数为空！");
		}

		// 统计期间非空判断
		if (StringUtil.isBlank(vo.getPeriod())) {
			// 统计期间不能为空！
			throw new SettlementException("统计期间不能为空！");
		}

		MvrRfdDto dto = new MvrRfdDto();
		try {
			// 把vo上属性拷贝到dto上
			BeanUtils.copyProperties(dto, vo);

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

		} catch (IllegalAccessException e) {
			// BeanUtils.copyProperties异常
			LOGGER.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			// BeanUtils.copyProperties异常
			LOGGER.error(e.getMessage(), e);
		}

		return dto;
	}

	/**
	 * Gets the mvr rfd vo.
	 * 
	 * @return mvrRfdVo
	 */
	public MvrRfdVo getMvrRfdVo() {
		return mvrRfdVo;
	}

	/**
	 * Sets the mvr rfd vo.
	 * 
	 * @param mvrRfdVo
	 *            the new mvr rfd vo
	 */
	public void setMvrRfdVo(MvrRfdVo mvrRfdVo) {
		this.mvrRfdVo = mvrRfdVo;
	}

	/**
	 * Sets the mvr rfd entity service.
	 * 
	 * @param mvrRfdEntityService
	 *            the new mvr rfd entity service
	 */
	public void setMvrRfdEntityService(IMvrRfdEntityService mvrRfdEntityService) {
		this.mvrRfdEntityService = mvrRfdEntityService;
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
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * @return periodList
	 */
	public List<ClosingPeriodDto> getPeriodList() {
		return periodList;
	}

	/**
	 * @param periodList
	 */
	public void setPeriodList(List<ClosingPeriodDto> periodList) {
		this.periodList = periodList;
	}

}
