package com.deppon.foss.module.transfer.scheduling.server.action;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IExhibitForecastService;

import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastForWorldDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ExhibitVo;
import com.deppon.foss.util.CollectionUtils;

public class ExhibitForecastAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ExhibitForecastAction.class);

	/**
	 * vo
	 */
	private ExhibitVo exhibitVo = new ExhibitVo();
	/**
	 * 导出Excel 文件流
	 */
	private transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	/**
	 * 小数点，保留3位小数
	 */
	//没有使用-352203
//	private static final int three = 3;

	
	
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ExhibitVo getExhibitVo() {
		return exhibitVo;
	}
	public void setExhibitVo(ExhibitVo exhibitVo) {
		this.exhibitVo = exhibitVo;
	}
	
	/**
	 * 展会货service
	 */
	private IExhibitForecastService exhibitForecastService;
	
	public void setExhibitForecastService(
			IExhibitForecastService exhibitForecastService) {
		this.exhibitForecastService = exhibitForecastService;
	}
	/**
	 * 根据条件查询所有的展会信息,分页
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-4
	 * @return
	 */
	public String queryForecastExhibitList(){
		try {
			ExhibitForecastDto exhibitForecastDto = exhibitVo.getExhibitForecastDto();
			ExhibitForecastDto exhibitInfo = exhibitForecastService.queryForecastExhibitList(exhibitForecastDto,this.getStart(),this.getLimit());
			Long count = exhibitForecastService.queryForecastExhibitListCount(exhibitForecastDto);
			exhibitVo.setExhibitForecastDto(exhibitInfo);
			this.setTotalCount(count);
			return returnSuccess();
		} catch (TfrBusinessException e) {
			LOG.error(e.getStackTrace().toString());
			return returnError(e);
		}
	}
	
	/**
	 * 导出
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-4
	 * @return
	 */
	public String queryForecastExhibitExcelStream(){
		try {
			/**
			 * 设置文件名
			 */
			fileName = exhibitForecastService.encodeFileName(TransportPathConstants.EXHIBIT_SHEET_NAME);
			
			//check
			if(null == exhibitVo.getExhibitForecastDto()){
				throw new TfrBusinessException("请输入正确的参数!");
			}
			
			//获取当前登陆人员所对应的营业部或外场或为登陆人的当前部门
			try {
				//返回当前员工所在的营业部或者外场
				exhibitVo.getExhibitForecastDto().setTransferCenterCode(exhibitForecastService.findTransforCenter(FossUserContext.getCurrentDept().getCode()));
			} catch (TfrBusinessException ex) {
				//赋值为当前的登陆人的当前部门
				exhibitVo.getExhibitForecastDto().setTransferCenterCode(FossUserContext.getCurrentDept().getCode());
			}
			/**
			 * 获取文件内容流
			 */
			excelStream = exhibitForecastService.queryStatisticalInquiriesExcelStream(exhibitVo.getExhibitForecastDto());
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 全国展会货货量查询
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-8
	 * @return
	 */
	public String queryForecastExhibitForWorldList(){
		try {
			//获取外场
			String transferCenterCode = exhibitVo.getTransferCenterCode();
			List<ExhibitForecastForWorldDto> exhibitForecastForWorldList = exhibitForecastService.queryForecastExhibitForWorldList(transferCenterCode);
			exhibitVo.setExhibitForecastForWorldList(exhibitForecastForWorldList);
			BigDecimal totalWeight = new BigDecimal(0);
			BigDecimal totalVolume = new BigDecimal(0);
			Integer totalWaybillQty = 0; 
			if(!CollectionUtils.isEmpty(exhibitForecastForWorldList)){
				for (ExhibitForecastForWorldDto exhibitForecastForWorldDto : exhibitForecastForWorldList) {
					totalWeight = totalWeight.add(exhibitForecastForWorldDto.getTotalWeight());
					totalVolume = totalVolume.add(exhibitForecastForWorldDto.getTotalVolume());
					totalWaybillQty += exhibitForecastForWorldDto.getTotalWaybillQty();
				}
			}
			exhibitVo.setTotalWeight(totalWeight);
			exhibitVo.setTotalVolume(totalVolume);
			exhibitVo.setTotalWaybillQty(totalWaybillQty);
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 导出全国展会货货量统计
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-9
	 * @return
	 */
	public String queryForecastExhibitForWorldExcelStream(){
		try {
			/**
			 * 设置文件名
			 */
			fileName = exhibitForecastService.encodeFileName(TransportPathConstants.EXHIBITFORWORLD_SHEET_NAME);
			//获取外场
			String transferCenterCode = exhibitVo.getTransferCenterCode();
			/**
			 * 获取文件内容流
			 */
			excelStream = exhibitForecastService.queryExhibitForWorldExcelStream(transferCenterCode);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		
	}
	
}
