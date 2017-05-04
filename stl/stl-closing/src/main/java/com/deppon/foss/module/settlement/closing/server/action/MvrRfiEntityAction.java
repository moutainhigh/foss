package com.deppon.foss.module.settlement.closing.server.action;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfiEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrRfiVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 出发到达往来action.
 * 
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午3:58:51
 */
public class MvrRfiEntityAction extends AbstractAction {

	/** 序列化id. */
	private static final long serialVersionUID = -4749008162529566274L;

	/** 出发到达往来vo. */
	private MvrRfiVo vo;

	/** 注入service. */
	private IMvrRfiEntityService mvrRfiEntityService;

	/** 导出excel的文件名称. */
	private String fileName;

	/** 导出excel的输入流. */
	private InputStream stream;

	/** 导出excel名称. */
	private static final String EXPROTNAME = "始发专线往来月报表";

	/** 定义导出异常返回信息. */
	private String errorMessage;

	/**
	 * 查询报表信息.
	 * 
	 * @return the string
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午3:59:54
	 */
	@JSON
	public String queryMvrRfiEntityReport() {
		try {
			// 校验查询参数
			if (vo == null || vo.getDto() == null) {
				throw new SettlementException("查询参数没有正确注入到后台！");
			}
			// 调用service查询
			MvrRfiEntity entity = mvrRfiEntityService.queryTotalCounts(vo
					.getDto());
			// 校验是否存在数据
			if (entity.getCount() > 0) {
				List<MvrRfiEntity> list = mvrRfiEntityService
						.queryReportByConditions(this.getStart(),
								this.getLimit(), vo.getDto());
				// 如果存在记录则将总计添加到lsit中
				if (CollectionUtils.isNotEmpty(list)) {
					entity.setPeriod("合计");
					// 添加合计项
					list.add(entity);
				}
				// 设置返回给前台list
				vo.setList(list);
			}
			this.setTotalCount((long) entity.getCount());
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 导出EXCEL.
	 * 
	 * @return the string
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-11 下午6:12:01
	 */
	public String exportMvrRfiReport() {
		// 输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 校验查询参数
			if (vo == null || vo.getDto() == null) {
				throw new SettlementException("查询参数没有正确注入到后台！");
			}
			// 设置导出excel名称
			String exportXlsName = EXPROTNAME + "_" + vo.getDto().getPeriod();
			// 设置excel名称
			try {
				// 转化编码
				this.setFileName(new String((exportXlsName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e1) {
				// 导出文件名编码转化错误
				throw new SettlementException("导出文件名编码转化错误！");
			}
			// 导出excel
			ExportResource exportResource = mvrRfiEntityService.export(vo.getDto());

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(exportXlsName);
			//设置格式
		    Map<Integer, String> map = new HashMap<Integer, String>();
		    for(int i=SettlementReportNumber.SEVEN;i<=SettlementReportNumber.TWENTY_SEVEN;i++){
		    	map.put(i, "float");
		    }
		    exportSetting.setDataType(map);
			// 创建导出工具类
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			stream = objExporterExecutor.exportSync(exportResource,exportSetting);
			return returnSuccess();
			
		} catch (BusinessException e) {
			errorMessage = e.getErrorCode();
			return returnError(e.getMessage());
		} finally {
			// 关闭流
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					// 流关闭错误！
					throw new SettlementException("流关闭错误！");
				}
			}
		}
	}

	/**
	 * Gets the vo.
	 * 
	 * @return vo
	 */
	public MvrRfiVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 * 
	 * @param vo
	 *            the new vo
	 */
	public void setVo(MvrRfiVo vo) {
		this.vo = vo;
	}

	/**
	 * Sets the mvr rfi entity service.
	 * 
	 * @param mvrRfiEntityService
	 *            the new mvr rfi entity service
	 */
	public void setMvrRfiEntityService(IMvrRfiEntityService mvrRfiEntityService) {
		this.mvrRfiEntityService = mvrRfiEntityService;
	}

	/**
	 * Gets the file name.
	 * 
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @GET
	 * @return stream
	 */
	public InputStream getStream() {
		/*
		 * @get
		 * 
		 * @ return stream
		 */
		return stream;
	}

	/**
	 * @SET
	 * @param stream
	 */
	public void setStream(InputStream stream) {
		/*
		 * @set
		 * 
		 * @this.stream = stream
		 */
		this.stream = stream;
	}

	/**
	 * Gets the error message.
	 * 
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 * 
	 * @param errorMessage
	 *            the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
