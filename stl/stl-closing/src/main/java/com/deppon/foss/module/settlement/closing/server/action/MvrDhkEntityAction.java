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
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDhkEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDhkDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrDhkVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 代汇款月报表Action.
 * 
 * @author guxinhua
 * @date 2013-3-6 下午3:37:31
 */
public class MvrDhkEntityAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrDhkEntityAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 代汇款月报表Service. */
	private IMvrDhkEntityService mvrDhkEntityService;

	/** 代汇款月报表VO. */
	private MvrDhkVo mvrDhkVo;

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
			MvrDhkDto dto = this.checkAndPackageVoToDto(mvrDhkVo);

			// 查询专线到达报表合计
			MvrDhkDto totalDto = mvrDhkEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<MvrDhkEntity> mvrDhkEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询专线到达报表
				mvrDhkEntityList = mvrDhkEntityService.selectByConditions(dto,
						start, limit);
				
				totalDto.setPeriod("汇总");
				totalDto.setRemitOrgName("总条数:"+totalDto.getCount());
				mvrDhkEntityList.add(totalDto);
			}

			// 设置Vo的统计Dto
			mvrDhkVo.setMvrDhkDto(totalDto);
			// 设置Vo的报表集合
			mvrDhkVo.setMvrDhkEntityList(mvrDhkEntityList);
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
			MvrDhkDto dto = this.checkAndPackageVoToDto(mvrDhkVo);

			// 查询专线到达报表合计
			MvrDhkDto totalDto = mvrDhkEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<MvrDhkEntity> mvrDhkEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询代汇款月报表
				mvrDhkEntityList = mvrDhkEntityService.selectByConditions(dto,
						0, totalDto.getCount().intValue());
			} else {
				// 提示导出代汇款月报表不能为空
				throw new SettlementException(mvrDhkVo.getPeriod()
						+ "账期的代汇款月报表数据为空!");
			}

			// 导出文件名称：
			// 代汇款月报表_+账期构成
			try {
				String exportExeclName = "代汇款月报表" + mvrDhkVo.getPeriod();				
				
				// 转化编码
				this.setExeclName(new String((exportExeclName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败：" + e.getMessage());
			}
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(mvrDhkEntityList);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置sheet名称
			exportSetting.setSheetName(mvrDhkVo.getPeriod());

			Map<Integer, String> map = new HashMap<Integer, String>(2);
			map.put(SettlementReportNumber.FIVE, "float");

			exportSetting.setDataType(map);

			// 创建导出工具类,导出成文件
			this.setInputStream(new ExporterExecutor().exportSync(sheet,
					exportSetting));

			return this.returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("代汇款月报表导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("代汇款月报表导出异常：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("代汇款月报表导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("代汇款月报表导出异常：" + e.getMessage());
			// 异常处理
		}
	}

	/**
	 * 获得execl报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param mvrDhkEntityList
	 */
	private ExportResource exportRfdResource(List<MvrDhkEntity> mvrDhkEntityList) {

		ExportResource sheet = new ExportResource();
		// 设置表单表头
		sheet.setHeads(this.getExcelHeads());
		// 处理返回的结果集
		List<List<String>> resultList = this.exportMvrDhk(mvrDhkEntityList);

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
				"代汇款所属期间",
				"代汇款部门名称",
				"代汇款部门编码",
				"被代汇款部门名称（快递代理点部名称）",
				"被代汇款部门编码（快递代理点部编码）",
				"代汇金额",
				"收款类别" };
	}

	/**
	 * 专线到达报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:48
	 * @param mvrDhkEntityList
	 * @return
	 */
	private List<List<String>> exportMvrDhk(List<MvrDhkEntity> mvrDhkEntityList) {

		String[] columns = {
				"period",
				"remitOrgName",
				"remitOrgCode",
				"byremitOrgName",
				"byremitOrgCode",
				"amount",
				"collectionType"
			};
		

		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();

		if (CollectionUtils.isNotEmpty(mvrDhkEntityList)) {
			// 列数据
			List<String> colList = null;
			// 循环结果集
			for (MvrDhkEntity entity : mvrDhkEntityList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					validaExtracted(colList, entity, column);
				}
				rowList.add(colList);
			}
		}

		return rowList;
	}

	private void validaExtracted(List<String> colList, MvrDhkEntity entity,
			String column) {
		Object fieldValue;
		String cellValue;
		// 获取对象的值，如果为空，则设置其为空字符串
		fieldValue = ReflectionUtils.getFieldValue(entity, column);
		cellValue = (fieldValue == null ? "" : fieldValue
				.toString());
		if (StringUtils.equals(column, "collectionType")) { // 转换paymentType值
			if (StringUtils.isNotBlank(cellValue)) {
				if(StringUtils.equals(cellValue, SettlementConstants.SETTLEMENT__COLLECTION_TYPE__CASH)){
					cellValue = "现金";
				}else if(StringUtils.equals(cellValue, SettlementConstants.SETTLEMENT__COLLECTION_TYPE__NOCASH)){
					cellValue = "非现金";
				}
			}
		}
		colList.add(cellValue);
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
	private MvrDhkDto checkAndPackageVoToDto(MvrDhkVo vo) {

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

		MvrDhkDto dto = new MvrDhkDto();
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
	 * @return mvrDhkVo
	 */
	public MvrDhkVo getMvrDhkVo() {
		return mvrDhkVo;
	}

	/**
	 * Sets the mvr rfd vo.
	 * 
	 * @param mvrDhkVo
	 *            the new mvr rfd vo
	 */
	public void setMvrDhkVo(MvrDhkVo mvrDhkVo) {
		this.mvrDhkVo = mvrDhkVo;
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
	 * @param mvrDhkEntityService the mvrDhkEntityService to set
	 */
	public void setMvrDhkEntityService(
			IMvrDhkEntityService mvrDhkEntityService) {
		this.mvrDhkEntityService = mvrDhkEntityService;
	}

	
}
