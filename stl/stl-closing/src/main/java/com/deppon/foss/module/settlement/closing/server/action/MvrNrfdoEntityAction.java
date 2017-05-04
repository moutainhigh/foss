package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfdoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdoDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrNrfdoVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 01到达月报表Action.
 * 
 * @author guxinhua
 * @date 2013-3-6 下午3:37:31
 */
public class MvrNrfdoEntityAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrNrfdoEntityAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 01到达月报表Service. */
	private IMvrNrfdoEntityService mvrNrfdoEntityService;

	/**
	 * 产品productService
	 */
	private IProductService productService;

	/** 01到达月报表VO. */
	private MvrNrfdoVo mvrNrfdoVo;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;   


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
			MvrNrfdoDto dto = this.checkAndPackageVoToDto(mvrNrfdoVo);

			// 查询专线到达报表合计
			MvrNrfdoDto totalDto = mvrNrfdoEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<MvrNrfdoEntity> mvrNrfdoEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询专线到达报表
				mvrNrfdoEntityList = mvrNrfdoEntityService.selectByConditions(dto,
						start, limit);
				
				totalDto.setPeriod("汇总");
				totalDto.setProductCode("总条数:"+totalDto.getCount());
				mvrNrfdoEntityList.add(totalDto);
			}

			// 设置Vo的统计Dto
			mvrNrfdoVo.setMvrNrfdoDto(totalDto);
			// 设置Vo的报表集合
			mvrNrfdoVo.setMvrNrfdoEntityList(mvrNrfdoEntityList);
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
			MvrNrfdoDto dto = this.checkAndPackageVoToDto(mvrNrfdoVo);

			// 查询专线到达报表合计
			MvrNrfdoDto totalDto = mvrNrfdoEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<MvrNrfdoEntity> mvrNrfdoEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询01到达月报表
				mvrNrfdoEntityList = mvrNrfdoEntityService.selectByConditions(dto,
						0, totalDto.getCount().intValue());
			} else {
				// 提示导出01到达月报表不能为空
				throw new SettlementException(mvrNrfdoVo.getPeriod()
						+ "账期的01到达月报表数据为空!");
			}

			// 导出文件名称：
			// 01到达月报表_+账期构成
			try {
				String exportExeclName = "01到达月报表" + mvrNrfdoVo.getPeriod();				
				
				// 转化编码
				this.setExeclName(new String((exportExeclName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(mvrNrfdoEntityList);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName(mvrNrfdoVo.getPeriod());

			Map<Integer, String> map = new HashMap<Integer, String>();
			for (int i = SettlementReportNumber.ELEVEN; i <= SettlementReportNumber.TWENTY_NINE; i++) {
				map.put(i, "float");
			}

			exportSetting.setDataType(map);

			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,
					exportSetting));

			return this.returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("01到达月报表导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("01到达月报表导出异常：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("01到达月报表导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("01到达月报表导出异常：" + e.getMessage());
			// 异常处理
		}
	}

	/**
	 * 获得execl报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param mvrNrfdoEntityList
	 */
	private ExportResource exportRfdResource(List<MvrNrfdoEntity> mvrNrfdoEntityList) {

		ExportResource sheet = new ExportResource();
		// 设置表单表头
		sheet.setHeads(this.getExcelHeads());
		// 处理返回的结果集
		List<List<String>> resultList = this.exportMvrNrfdo(mvrNrfdoEntityList);

		sheet.setHeaderHeight(SettlementReportNumber.THREE);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.TEN, "数据统计维度"); // 6
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ELEVEN, SettlementReportNumber.FOURTEEN, "预收客户"); // 4
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.FIFTEEN, SettlementReportNumber.TWENTY_ONE, "理赔"); // 7
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_TWO, SettlementReportNumber.TWENTY_EIGHT, "退运费"); // 7
		HeaderRows rowContent5 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_NINE, SettlementReportNumber.TWENTY_NINE, "服务补救"); // 1
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		headerList.add(rowContent5);
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
				"期间",
				"产品编码",
				"客户编码",
				"客户名称",
				"始发部门编码",
				"始发部门名称",
				"到达部门编码",
				"到达部门名称",
				"统一结算类型",
				"合同部门名称",
				"合同部门编码",
				"01预收客户冲01应收到付运费未签收",
				"01预收客户冲02应收到付运费未签收",
				"01预收客户冲01应收到付运费已签收",
				"01预收客户冲02应收到付运费已签收",
				"01理赔冲收入",
				"01理赔入成本",
				"01理赔冲01到达应收已签收",
				"01理赔冲02到达应收已签收",
				"01理赔冲01到达应收未签收",
				"01理赔冲02到达应收未签收",
				"01理赔付款申请",
				"01退运费冲收入",
				"01退运费入成本",
				"01退运费付款申请",
				"01退运费冲01到达应收已签收",
				"01退运费冲02到达应收已签收",
				"01退运费冲01到达应收未签收",
				"01退运费冲02到达应收未签收",
				"01到达服务补救付款申请" };
	}

	/**
	 * 专线到达报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:48
	 * @param mvrNrfdoEntityList
	 * @return
	 */
	private List<List<String>> exportMvrNrfdo(List<MvrNrfdoEntity> mvrNrfdoEntityList) {

		String[] columns = {
				"period",
				"productCode",
				"customerCode",
				"customerName",
				"origOrgCode",
				"origOrgName",
				"destOrgCode",
				"destOrgName",
				"unifiedSettlementType",
				"contractOrgCode",
				"contractOrgName",
				"custDroWoDestRcvoNpod",
				"custDroWoDestRcvtNpod",
				"custDroWoDestRcvoPod",
				"custDroWoDestRcvtPod",
				"claimDestoIncome",
				"claimDestoCost",
				"claimDestoWoDestRcvoPod",
				"claimDestoWoDestRcvtPod",
				"claimDestoWoDestRcvoNpod",
				"claimDestoWoDestRcvtNpod",
				"claimDestoPayApply",
				"rdDestoIncome",
				"rdDestoCost",
				"rdDestoPayApply",		
				"rdDestoDestRcvoPod",
				"rdDestoDestRcvtPod",
				"rdDestoWoDestRcvoNpod",
				"rdDestoWoDestRcvtNpod",
				"cpoDestPayApply"
			};

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

		if (CollectionUtils.isNotEmpty(mvrNrfdoEntityList)) {
			// 列数据
			List<String> colList = null;
			String cellValue = null;
			// 循环结果集
			for (MvrNrfdoEntity entity : mvrNrfdoEntityList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
					cellValue = validaExtracted(productMap, entity, column);
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}

		return rowList;
	}

	private String validaExtracted(Map<String, String> productMap,
			MvrNrfdoEntity entity, String column) {
		Object fieldValue;
		String cellValue;
		fieldValue = ReflectionUtils.getFieldValue(entity, column);
		cellValue = (fieldValue == null ? "" : fieldValue
				.toString());
		if (StringUtils.equals(column, "productCode")) { // 转换productCode值
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
		}
		// 统一结算类型
		if ("unifiedSettlementType".equalsIgnoreCase(column)) {
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
	private MvrNrfdoDto checkAndPackageVoToDto(MvrNrfdoVo vo) {

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

		MvrNrfdoDto dto = new MvrNrfdoDto();
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
	 * @return mvrNrfdoVo
	 */
	public MvrNrfdoVo getMvrNrfdoVo() {
		return mvrNrfdoVo;
	}

	/**
	 * Sets the mvr rfd vo.
	 * 
	 * @param mvrNrfdoVo
	 *            the new mvr rfd vo
	 */
	public void setMvrNrfdoVo(MvrNrfdoVo mvrNrfdoVo) {
		this.mvrNrfdoVo = mvrNrfdoVo;
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
	 * @param mvrNrfdoEntityService the mvrNrfdoEntityService to set
	 */
	public void setMvrNrfdoEntityService(
			IMvrNrfdoEntityService mvrNrfdoEntityService) {
		this.mvrNrfdoEntityService = mvrNrfdoEntityService;
	}

	
}
