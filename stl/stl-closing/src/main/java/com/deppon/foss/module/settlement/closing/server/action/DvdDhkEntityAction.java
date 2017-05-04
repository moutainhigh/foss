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
import com.deppon.foss.framework.server.components.export.excel.ExportInfo;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.IExportListener;
import com.deppon.foss.framework.server.components.export.excel.IResourceListener;
import com.deppon.foss.framework.server.components.ftp.FTPClient;
import com.deppon.foss.framework.server.components.ftp.FTPConfig;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IrecordImportExcelFileInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DownloadInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IDvdDhkEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdDhkDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.DvdDhkVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.DateUtils;

/**
 * 代汇款明细报表Action.
 * 
 * @author guxinhua
 * @date 2013-3-6 下午3:37:31
 */
public class DvdDhkEntityAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(DvdDhkEntityAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 代汇款明细报表Service. */
	private IDvdDhkEntityService dvdDhkEntityService;

	/** 代汇款明细报表VO. */
	private DvdDhkVo dvdDhkVo;

	/**
	 * 导出输出流
	 */
	private InputStream inputStream;

	/**
	 * 导出excel名称
	 */
	private String execlName;   

	/**
	 * FTP客户端
	 */
	private FTPClient ftpClient;

	/**
	 * 下载ftp配置
	 */
	private FTPConfig downloadFtpConfig;

	/**
	 * 文件下载服务
	 */
	private IrecordImportExcelFileInfoService recordImportExcelFileInfoService;
	
	/** 汇款导出execl文件名. */
	private static final String DHK_EXPORT_EXECL_NAME = "代汇款明细报表";
	
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
			DvdDhkDto dto = this.checkAndPackageVoToDto(dvdDhkVo);

			// 查询专线到达报表合计
			DvdDhkDto totalDto = dvdDhkEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<DvdDhkEntity> dvdDhkEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询专线到达报表
				dvdDhkEntityList = dvdDhkEntityService.selectByConditions(dto,
						start, limit);
				
				totalDto.setPeriod("汇总");
				totalDto.setRemitOrgName("总条数:"+totalDto.getCount());
				dvdDhkEntityList.add(totalDto);
			}

			// 设置Vo的统计Dto
			dvdDhkVo.setDvdDhkDto(totalDto);
			// 设置Vo的报表集合
			dvdDhkVo.setDvdDhkEntityList(dvdDhkEntityList);
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
			DvdDhkDto dto = this.checkAndPackageVoToDto(dvdDhkVo);

			// 查询专线到达报表合计
			DvdDhkDto totalDto = dvdDhkEntityService
					.selectTotalByConditions(dto);

			// 专线到达报表集合
			List<DvdDhkEntity> dvdDhkEntityList = null;

			// 总条数>0时查询
			if (null != totalDto
					&& totalDto.getCount().compareTo(NumberUtils.LONG_ZERO) > 0) {
				// 查询代汇款明细报表
				dvdDhkEntityList = dvdDhkEntityService.selectByConditions(dto,
						0, totalDto.getCount().intValue());
			} else {
				// 提示导出代汇款明细报表不能为空
				throw new SettlementException("代汇款明细报表数据为空!");
			}

			
			// 生成导出数据源类
			ExportResource sheet = this.exportRfdResource(dvdDhkEntityList);
			
			// 小于EXCEL_MAX_COUNTS，同步导出。反之，异步导出
			if(totalDto.getCount().longValue() < SettlementConstants.EXPORT_EXCEL_MAX_COUNTS){
				// 导出文件名称：
				// 代汇款明细报表_+账期构成
				try {
					// 转化编码
					this.setExeclName(new String((DHK_EXPORT_EXECL_NAME)
							.getBytes(SettlementConstants.UNICODE_GBK),
							SettlementConstants.UNICODE_ISO));	
				} catch (UnsupportedEncodingException e) {
					LOGGER.error(e.getMessage(), e);
					return returnError("导出Excel失败：" + e.getMessage());
				}
				// 2,同步导出操作
				// 创建导出表头对象
				ExportSetting exportSetting = new ExportSetting();

				exportSetting.setDataType(this.getDataType());

				// 创建导出工具类,导出成文件
				this.setInputStream(new ExporterExecutor().exportSync(sheet, exportSetting));
				
				return this.returnSuccess();
			}else{
				this.setExeclName(DHK_EXPORT_EXECL_NAME);
				
				// 1,先异步导出操作
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				ExporterExecutor executor = new ExporterExecutor();
				this.dhkExportAsync(executor, sheet,currentInfo);

				return this.returnError("异步导出成功,可在[运作基础数据]Execl文件下载！");
			}
			
			
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error("代汇款明细报表导出异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError("代汇款明细报表导出异常：" + e.getErrorCode());
			// 异常处理
		} catch (Exception e) {
			// 记录日志
			LOGGER.error("代汇款明细报表导出异常：" + e.getMessage(), e);
			// 异常返回
			return returnError("代汇款明细报表导出异常：" + e.getMessage());
			// 异常处理
		}
	}

	/**
	 * 获得execl报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午6:42:11
	 * @param dvdDhkEntityList
	 */
	private ExportResource exportRfdResource(List<DvdDhkEntity> dvdDhkEntityList) {

		ExportResource sheet = new ExportResource();
		// 设置表单表头
		sheet.setHeads(this.getExcelHeads());
		// 处理返回的结果集
		List<List<String>> resultList = this.exportDvdDhk(dvdDhkEntityList);

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
				"代汇款日期",
				"代汇款所属期间",
				"代汇款部门名称",
				"代汇款部门编码",
				"被代汇款部门名称（快递代理点部名称）",
				"被代汇款部门编码（快递代理点部编码）",
				"单据编号",
				"来源单号",
				"单据类型",
				"代汇金额",
				"付款方式"
				 };
	}

	/**
	 * 专线到达报表数据
	 * 
	 * @author guxinhua
	 * @date 2013-3-21 下午5:28:48
	 * @param dvdDhkEntityList
	 * @return
	 */
	private List<List<String>> exportDvdDhk(List<DvdDhkEntity> dvdDhkEntityList) {

		String[] columns = {
				"remitDate",
				"period",
				"remitOrgName",
				"remitOrgCode",
				"byremitOrgName",
				"byremitOrgCode",
				"billNo",
				"sourceBillNo",
				"billType",
				"amount",
				"paymentType"
			};

		//数据字典转换
		List<String> types=new ArrayList<String>();
		types.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);// 付款方式
		//获取全部待转换缓存
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
		
		// 设置数据:通过反射获取对象的值
		List<List<String>> rowList = new ArrayList<List<String>>();

		if (CollectionUtils.isNotEmpty(dvdDhkEntityList)) {
			// 列数据
			List<String> colList = null;
			Object fieldValue = null;
			String cellValue = null;
			// 循环结果集
			for (DvdDhkEntity entity : dvdDhkEntityList) {
				colList = new ArrayList<String>();
				// 循环列
				for (String column : columns) {

					// 获取对象的值，如果为空，则设置其为空字符串
					fieldValue = ReflectionUtils.getFieldValue(entity, column);
					cellValue = (fieldValue == null ? "" : fieldValue.toString());
					
					if(StringUtils.equals(column, "remitDate")){
						cellValue = DateUtils.convert(entity.getRemitDate(),DateUtils.DATE_TIME_FORMAT);
					}
					
					if (StringUtils.equals(column, "billType")) { // 转换billType值
						if (StringUtils.isNotBlank(cellValue)) {

							if (StringUtils.isNotBlank(cellValue)) {
								if(StringUtils.equals(cellValue, SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__XS)){
									cellValue = "现金收款单";
								}else if(StringUtils.equals(cellValue, SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__HK)){
									cellValue = "还款单";
								}
							}
							
						}
					}
					if (StringUtils.equals(column, "paymentType")) { // 转换paymentType值
						if (StringUtils.isNotBlank(cellValue)) {

							cellValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,cellValue);
						}
					}
					
					if (StringUtils.equals(column, "period")){
						
						// 把期间每天表示形式20140102  转换为201401-01 
						
						String ymStr = cellValue.substring(0,SettlementReportNumber.SIX);
						int dd = Integer.parseInt(cellValue.substring(SettlementReportNumber.SIX,SettlementReportNumber.EIGHT));
						
						if(dd < SettlementReportNumber.ELEVEN){
							cellValue = ymStr + "-01";
						}else if(dd > SettlementReportNumber.TWENTY){
							cellValue = ymStr + "-03";
						}else{
							cellValue = ymStr + "-02";
						}
					}
					
					colList.add(cellValue);
				}
				rowList.add(colList);
			}
		}

		return rowList;
	}

	/**
	 * 代汇款异步导出
	 * 
	 * @author foss-guxinhua
	 * @date 2013-10-25 上午17:14:17
	 * @param executor
	 * @param sheet
	 */
	private void dhkExportAsync(ExporterExecutor executor,final ExportResource sheet,final CurrentInfo currentInfo){
		
		final ExportSetting settings = new ExportSetting();
	
		// 导出设置
		settings.setFileName(this.getExeclName());
		settings.setSize(SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
		settings.setDataType(this.getDataType());
		settings.setFileDesc(String.format("%s导出", settings.getFileName()));
	
		// 导出后结果处理
		IExportListener exportListener = new IExportListener() {
			@Override
			public void doExported(ExportInfo info) {
				DownloadInfoEntity entity = new DownloadInfoEntity();
	
				String encodedUsername = downloadFtpConfig.getUserName();
				String encodedPassword = downloadFtpConfig.getPassword();
	
				//对用户名密码加密
				try {
					encodedUsername = URLEncoder.encode(encodedUsername,
							SettlementConstants.UNICODE_UTF);
					encodedPassword = URLEncoder.encode(encodedPassword,
							SettlementConstants.UNICODE_UTF);
				} catch (UnsupportedEncodingException e) {
					LOG.error(e.getMessage(), e);
				}
	
				entity.setEmpCode(currentInfo.getEmpCode());
				entity.setOrgCode(currentInfo.getCurrentDeptCode());
				entity.setFileName(settings.getFileDesc());
				entity.setFileLoadPath(String.format(
						"ftp://%s:%s@%s/%s/%s", encodedUsername,
						encodedPassword, downloadFtpConfig.getHost(),
						downloadFtpConfig.getPath(),
						info.getRemoteFileName()));
				recordImportExcelFileInfoService
						.saveImportExcelFileInfo(entity);
			}
		};
	
		// 导出数据来源：包括总分页数和每页数据
		IResourceListener resourceListener = new IResourceListener() {
			@Override
			public int getDataPage() {
				int size = sheet.getRowList().size();
				// 返回总导出页数
				return (size + SettlementConstants.EXPORT_EXCEL_MAX_COUNTS - 1)
						/ SettlementConstants.EXPORT_EXCEL_MAX_COUNTS;
			}
			@Override
			public List<List<String>> getDataList(int page) {
				return sheet.getRowList();
			}
		};
		// 异步导出
		executor.exportAsync(sheet, settings, ftpClient, exportListener,
				resourceListener);
	}
	
	/**
	 * 获取列数据属性
	 * 
	 * @return
	 */
	private Map<Integer, String> getDataType() {
		
		Map<Integer, String> map = new HashMap<Integer, String>(2);
		map.put(SettlementReportNumber.NINE, "float");
		
		return map;
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
	private DvdDhkDto checkAndPackageVoToDto(DvdDhkVo vo) {

		// 专线到达VO非空判断
		if (null == vo) {
			// 内部错误，专线到达VO参数为空！
			throw new SettlementException("内部错误，专线到达VO参数为空！");
		}

		DvdDhkDto dto = new DvdDhkDto();
		try {
			// 把vo上属性拷贝到dto上
			BeanUtils.copyProperties(dto, vo);

			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			dto.setEndDate(DateUtils.addDayToDate(dto.getEndDate(), 1)); // 结束时间加1
			
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
	 * @return dvdDhkVo
	 */
	public DvdDhkVo getDvdDhkVo() {
		return dvdDhkVo;
	}

	/**
	 * Sets the mvr rfd vo.
	 * 
	 * @param dvdDhkVo
	 *            the new mvr rfd vo
	 */
	public void setDvdDhkVo(DvdDhkVo dvdDhkVo) {
		this.dvdDhkVo = dvdDhkVo;
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
	 * @param dvdDhkEntityService the dvdDhkEntityService to set
	 */
	public void setDvdDhkEntityService(
			IDvdDhkEntityService dvdDhkEntityService) {
		this.dvdDhkEntityService = dvdDhkEntityService;
	}

	/**
	 * @param ftpClient the ftpClient to set
	 */
	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	/**
	 * @param downloadFtpConfig the downloadFtpConfig to set
	 */
	public void setDownloadFtpConfig(FTPConfig downloadFtpConfig) {
		this.downloadFtpConfig = downloadFtpConfig;
	}

	/**
	 * @param recordImportExcelFileInfoService the recordImportExcelFileInfoService to set
	 */
	public void setRecordImportExcelFileInfoService(
			IrecordImportExcelFileInfoService recordImportExcelFileInfoService) {
		this.recordImportExcelFileInfoService = recordImportExcelFileInfoService;
	}

	
}
