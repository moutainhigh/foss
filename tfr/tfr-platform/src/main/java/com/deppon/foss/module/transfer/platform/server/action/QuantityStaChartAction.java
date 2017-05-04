package com.deppon.foss.module.transfer.platform.server.action;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.platform.api.server.service.IQuantityStaService;
import com.deppon.foss.module.transfer.platform.api.shared.define.QuantityStaConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantitySta4ChartDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.QuantityStaChartVo;

/**
 * 货量统计图表action
 * @author 045923
 *
 */
public class QuantityStaChartAction extends AbstractAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -809029005485635520L;
	
	/**
	 * service
	 */
	private IQuantityStaService quantityStaService;
	
	/**
	 * Vo
	 */
	private QuantityStaChartVo quantityStaChartVo = new QuantityStaChartVo();
	
	public void setQuantityStaService(IQuantityStaService quantityStaService) {
		this.quantityStaService = quantityStaService;
	}

	public QuantityStaChartVo getQuantityStaChartVo() {
		return quantityStaChartVo;
	}

	public void setQuantityStaChartVo(QuantityStaChartVo quantityStaChartVo) {
		this.quantityStaChartVo = quantityStaChartVo;
	}
	
	/**
	* @Title: queryQuantityStaInfo4Chart 
	* @Description: 货量统计图表页面跳转
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年9月13日 上午8:35:34 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String quantityStaChartIndex(){
		//为了监控页面访问数据
		return SUCCESS;
	}
	
	/**
	* @Title: queryQuantityStaInfo4Chart 
	* @Description: 货量统计图表查询
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年9月2日 上午10:50:34 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryDayChart(){
		QuantityStaQcDto quantityStaQcDto = quantityStaChartVo.getQuantityStaQcDto();
		String transferCenterCode = quantityStaQcDto.getTransferCenterCode();
		//获取展示图表所需的数据（分为出发、到达）
		List<QuantitySta4ChartDto> quantitySta4ChartDtoList = null;
		if(StringUtils.equals(quantityStaQcDto.getType(), QuantityStaConstants.STA_TYPE_DEPARTURE)){
			quantitySta4ChartDtoList = quantityStaService.queryDepart1Day4Chart(quantityStaQcDto);
		}else{
			quantitySta4ChartDtoList = quantityStaService.queryArrive1Day4Chart(quantityStaQcDto);
		}
		//获取警戒重量、警戒体积
		BigDecimal[] warnValues = quantityStaService.queryWarnWeightAndVolume(transferCenterCode);
		//设置警戒重量、警戒体积
		for(QuantitySta4ChartDto dto : quantitySta4ChartDtoList){
			dto.setWeightWarn(warnValues[0]);
			dto.setVolumeWarn(warnValues[1]);
		}
		quantityStaChartVo.setQuantitySta4ChartDtoList(quantitySta4ChartDtoList);
		return SUCCESS;
	}
	
	/**
	* @Title: queryHourChart 
	* @Description: 查询最近8天某个整天的货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年9月11日 上午10:45:56 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryHourChart(){
		//获取查询条件
		QuantityStaQcDto quantityStaQcDto = quantityStaChartVo.getQuantityStaQcDto();
		String transferCenterCode = quantityStaQcDto.getTransferCenterCode();
		//获取展示图表所需的数据（分为出发、到达）
		List<QuantitySta4ChartDto> quantitySta4ChartDtoList = null;
		if(StringUtils.equals(quantityStaQcDto.getType(), QuantityStaConstants.STA_TYPE_DEPARTURE)){
			quantitySta4ChartDtoList = quantityStaService.queryDepartFewDays1Hh4Chart(quantityStaQcDto);
		}else{
			quantitySta4ChartDtoList = quantityStaService.queryArriveFewDays1Hh4Chart(quantityStaQcDto);
		}
		//获取警戒重量、警戒体积
		BigDecimal[] warnValues = quantityStaService.queryWarnWeightAndVolume(transferCenterCode);
		//设置警戒重量、警戒体积
		for(QuantitySta4ChartDto dto : quantitySta4ChartDtoList){
			dto.setWeightWarn(warnValues[0]);
			dto.setVolumeWarn(warnValues[1]);
		}
		quantityStaChartVo.setQuantitySta4ChartDtoList(quantitySta4ChartDtoList);
		return SUCCESS;
	}
	
	
}
