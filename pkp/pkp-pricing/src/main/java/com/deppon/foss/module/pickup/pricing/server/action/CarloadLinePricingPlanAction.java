package com.deppon.foss.module.pickup.pricing.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICarloadLinePricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadLinePricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.CarloadLinePricePlanVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
/**
 * 
 * 整车价格方案Action
 *
 */
public class CarloadLinePricingPlanAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 日志处理
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CarloadLinePricingPlanAction.class);

	private CarloadLinePricePlanVo carloadLinePricePlanVo = new CarloadLinePricePlanVo();

	private ICarloadLinePricePlanService carloadLinePricePlanService;

	private IAreaAddressService areaAddressService;

	/**
	 * 
	 * 
	 * 导入文件
	 * 
	 */
	private File uploadFile;
	/**
	 * 按重量
	 */
	private final static  String BY_WEIGHT = "按重量";
	/**
	 * 按体积
	 */
	private final static  String BY_VOLUME = "按体积";

	/**
	 * 整车线路价格方案查询
	 * @return
	 */
	@JSON
	public String queryCarloadLinePricePlanInfo() {
		try {
			carloadLinePricePlanVo
					.setCarloadLinePricePlanEntityList(carloadLinePricePlanService
							.queryCarloadLinePricePlanByEntity(
									carloadLinePricePlanVo
											.getCarloadLinePricePlanEntity(),
									getStart(), getLimit()));
			this.setTotalCount(carloadLinePricePlanService
					.queryCarloadLinePricePlanCountByEntity(carloadLinePricePlanVo
							.getCarloadLinePricePlanEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询整车线路价格方案信息出现异常: " + e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * 整车线路价格方案明细查询
	 * @return
	 */
	@JSON
	public String queryCarloadLinePricePlanDetailInfo() {
		try {
			if ("ALL".equals(carloadLinePricePlanVo.getCarloadLinePricePlanDetailEntity().getInvoiceType()))
				carloadLinePricePlanVo.getCarloadLinePricePlanDetailEntity().setInvoiceType(null);
			List<CarloadLinePricePlanDetailEntity> list = carloadLinePricePlanService.queryCarloadLinePricePlanDetailByEntity(carloadLinePricePlanVo
							.getCarloadLinePricePlanDetailEntity(), getStart(), getLimit());
			carloadLinePricePlanVo
					.setCarloadLinePricePlanDetailEntityList(list);
			this.setTotalCount(carloadLinePricePlanService
					.queryCarloadLinePricePlanDetailCountByEntity(carloadLinePricePlanVo
							.getCarloadLinePricePlanDetailEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询整车线路价格方案明细信息出现异常: " + e.getMessage());
			return returnError(e);
		}
	}

	public String importLinePrice() {
		// 初始化
		Workbook book = null;
		FileInputStream inputStream = null;
		try {
			if (uploadFile != null) {
				inputStream = new FileInputStream(uploadFile);
				try {
					// 如果是2003版本
					book = new XSSFWorkbook(inputStream);
				} catch (Exception ex) {
					// 如果是2007版本
					book = new HSSFWorkbook(inputStream);
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (book != null) {
				// 默认获取获取工作表1
				Sheet sheet = book.getSheetAt(0);// 默认第一个

				List<CarloadLinePricePlanDto> dtos = readSheet(sheet);

				if (dtos == null || dtos.size() < 1) {
					throw new FileException("导入数据为空,请检查", "导入数据为空,请检查");
				}

				// 导入
				carloadLinePricePlanService.batchInsert(dtos);

			}
			return super.returnSuccess();
		} catch (FileException e) {
			return super.returnError("数据验证失败:" + e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("数据文件被破坏，请重新制作导入文件" + e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return returnError("数据文件不符合规范，请重新制作导入文件" + e);
		} finally {
			if (book != null) {
				book = null;
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					return returnError("文件关闭失败");
				}
			}
		}
	}

	/**
	 * 读取sheet
	 * 
	 * @param sheet
	 */
	private List<CarloadLinePricePlanDto> readSheet(Sheet sheet) {
		StringBuilder builder = new StringBuilder();
		List<CarloadLinePricePlanDto> dtos = new ArrayList<CarloadLinePricePlanDto>();
		if (sheet != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// EXCEL行记录
			if (rowCount < 2) {
				return null;// no data
			}

			// 循环读取行和列
			for (int rowNum = 1; rowNum < rowCount; rowNum++) {
				Row row = sheet.getRow(rowNum);
				CarloadLinePricePlanDto dto = new CarloadLinePricePlanDto();
				// 读取始发城市
				Cell departureCityCell = row.getCell(0);
				if (departureCityCell != null) {
					// 取单元格值
					String departureCityName = ExcelHandleUtil.obtainStringVal(departureCityCell);
					if (StringUtil.isNotBlank(departureCityName)) {
						AdministrativeRegionsEntity regionsEntity = new AdministrativeRegionsEntity();
						regionsEntity.setActive(WaybillConstants.YES);
						// 市
						regionsEntity
								.setDegree(DictionaryValueConstants.DISTRICT_CITY);
						regionsEntity.setName(departureCityName);
						List<AdministrativeRegionsEntity> regionsEntities = areaAddressService
								.getAdministrativeRegionsEntityByName(
										regionsEntity, 0, 1);
						if (CollectionUtils.isEmpty(regionsEntities)) {
							builder.append("第" + (rowNum+1) + "行，始发城市错误  ");
						} else {
							regionsEntity = regionsEntities.get(0);
							dto.setDepartureCityCode(regionsEntity.getCode());
							dto.setDepartureCityName(regionsEntity.getName());
						}
					}
				} else {
					builder.append("第" + (rowNum+1) + "行，始发城市为空  ");
				}
				
				// 读取到达城市
				Cell arrivalCityCell = row.getCell(1);
				if (arrivalCityCell != null) {
					// 取单元格值
					String arrivalCityName = ExcelHandleUtil.obtainStringVal(arrivalCityCell);
					if (StringUtil.isNotBlank(arrivalCityName)) {
						AdministrativeRegionsEntity regionsEntity = new AdministrativeRegionsEntity();
						regionsEntity.setActive(WaybillConstants.YES);
						// 市
						regionsEntity
								.setDegree(DictionaryValueConstants.DISTRICT_CITY);
						regionsEntity.setName(arrivalCityName);
						List<AdministrativeRegionsEntity> regionsEntities = areaAddressService
								.getAdministrativeRegionsEntityByName(
										regionsEntity, 0, 1);
						if (CollectionUtils.isEmpty(regionsEntities)) {
							builder.append("第" + (rowNum+1) + "行，到达城市错误  ");
						} else {
							regionsEntity = regionsEntities.get(0);
							dto.setArrivalCityCode(regionsEntity.getCode());
							dto.setArrivalCityName(regionsEntity.getName());
						}
					} 
				} else {
					builder.append("第" + (rowNum+1) + "行，到达城市为空  ");
				}
				
				// 读取发票标记
				Cell invoiceTypeCell = row.getCell(NumericConstants.NUM_2);
				if (invoiceTypeCell != null) {
					String invoiceTypeName = ExcelHandleUtil.obtainStringVal(invoiceTypeCell);
					if (WaybillConstants.INVOICE_TYPE_01.equals(invoiceTypeName)) {
						dto.setInvoiceType(WaybillConstants.INVOICE_01);
					} else if (WaybillConstants.INVOICE_TYPE_02.equals(invoiceTypeName)) {
						dto.setInvoiceType(WaybillConstants.INVOICE_02);
					} else {
						builder.append("第" + (rowNum+1) + "行，发票标记错误  ");
					}
				} else {
					builder.append("第" + (rowNum+1) + "行，发票标记为空  ");
				}
				
				// 读取起点
				Cell upLimitCell = row.getCell(NumericConstants.NUM_3);
				if (upLimitCell != null) {
					try {
						BigDecimal upLimit = new BigDecimal(Double.valueOf(ExcelHandleUtil.obtainStringVal(upLimitCell)).intValue());
						if (upLimit.compareTo(BigDecimal.ZERO) > -1) {
							dto.setUpLimit(upLimit);
						} else {
							builder.append("第" + (rowNum+1) + "行，起点不能为负数  ");
						}
					} catch (Exception e) {
						builder.append("第" + (rowNum+1)+ "行，起点必须为整数  ");
					}
				} else {
					builder.append("第" + (rowNum+1) + "行，起点为空  ");
				}
				
				// 读取终点
				Cell downLimitCell = row.getCell(NumericConstants.NUM_4);
				if (downLimitCell != null) {
					try {
						BigDecimal downLimit =  new BigDecimal(Double.valueOf(ExcelHandleUtil.obtainStringVal(downLimitCell)).intValue());
						if (downLimit.compareTo(BigDecimal.ZERO) > -1) {
							dto.setDownLimit(downLimit);
						} else {
							builder.append("第" + (rowNum+1) + "行，终点不能为负数  ");
						}
					} catch (Exception e) {
						builder.append("第" + (rowNum+1) + "行，终点必须为整数  ");
					}
				} else {
					builder.append("第" + (rowNum+1) + "行，终点为空  ");
				}
				
				// 读取收费标准
				Cell chargeStandardCell = row.getCell(NumericConstants.NUM_5);
				if (chargeStandardCell != null) {
					try {
						BigDecimal chargeStandard = new BigDecimal(ExcelHandleUtil.obtainStringVal(chargeStandardCell));
						if (chargeStandard.compareTo(BigDecimal.ZERO) >= 0) {
							dto.setChargeStandard(chargeStandard);
						} else {
							builder.append("第" + (rowNum+1) + "行，收费标准不能为负数  ");
						}
					} catch (Exception e) {
						builder.append("第" + (rowNum+1) + "行，收费标准必须为数字  ");
					}
				} else {
					builder.append("第" + (rowNum+1) + "行，收费标准为空  ");
				}
				
				// 读取类别 按重量/按体积
				Cell typeCell = row.getCell(NumericConstants.NUM_6);
				if (typeCell != null) {
					String type = ExcelHandleUtil.obtainStringVal(typeCell);
					if (BY_WEIGHT.equals(type)) {
						dto.setType(WaybillConstants.BILLINGWAY_WEIGHT);
					} else if (BY_VOLUME.equals(type)) {
						dto.setType(WaybillConstants.BILLINGWAY_VOLUME);
					} else {
						builder.append("第" + (rowNum+1) + "行，类别必须为:按重量/按体积  ");
					}
				} else {
					builder.append("第" + (rowNum+1) + "行，类别为空  ");
				}
				
				if(builder.length()>0){
					throw new FileException("整车线路价格导入时发生错误: ", builder.toString());
				}
 
				//验证后的数据
				dtos.add(dto);

			}
			
		}

		return dtos;
	}


	public CarloadLinePricePlanVo getCarloadLinePricePlanVo() {
		return carloadLinePricePlanVo;
	}

	public void setCarloadLinePricePlanVo(
			CarloadLinePricePlanVo carloadLinePricePlanVo) {
		this.carloadLinePricePlanVo = carloadLinePricePlanVo;
	}

	public void setCarloadLinePricePlanService(
			ICarloadLinePricePlanService carloadLinePricePlanService) {
		this.carloadLinePricePlanService = carloadLinePricePlanService;
	}

	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

}
