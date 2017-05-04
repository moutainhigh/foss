package com.deppon.foss.module.transfer.airfreight.server.action;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirProtocolFlightService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightQueryDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirProtocolFlightVo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
/*
 * @desc 协议航班货量统计
 * @author foss-105795-wqh
 * @date 2014-02-18
 * */
public class AirProtocolFlightAction extends AbstractAction{

	/**
	 * 协议航班货量统计
	 */
	private static final long serialVersionUID = 156899898898989899L;

	private AirProtocolFlightVo airProtocolFlightVo=new AirProtocolFlightVo();
	
	private IAirProtocolFlightService airProtocolFlightService;
	
	//导出协议航班货量统计
    private String excelFileName; 
	/**
	 * 导出协议航班货量统计
	 */
    
    transient InputStream protocolFlightExcelStream;
	

	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(AirProtocolFlightAction.class);
	/*
	 * @desc 查询协议航班list（分页）
	 * @author foss-105795-wqh
	 * @date 2014-105795-wqh
	 * */
	@JSON
	public String queryProtocolFlightList(){
		AirProtocolFlightQueryDto queryCondition=new AirProtocolFlightQueryDto();
		try {
			//初始化查询条件
			initcondition(queryCondition);
			List<AirProtocolFlightDto> airProtocolFlightList =
					airProtocolFlightService.queryProtocolFlightList(queryCondition, this.start, this.limit);
			airProtocolFlightVo.setAirProtocolFlightList(airProtocolFlightList);
			this.totalCount=(long)airProtocolFlightService.queryProtocolFlightList(queryCondition).size();
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
		return this.returnSuccess();
		
	}
	
	/*
	 * @desc:根据当前用户code查询用户有权限操作的部门code
	 * @param userCode
	 * @author：foss-105795-wqh
	 * @date:2014-02-22
	 * */
	@JSON
	public String queryOptAllOrgCodeByUserCode(){
		List<String> orgs;
		try {
			orgs=airProtocolFlightService.queryOptAllOrgCodeByUserCode();
			airProtocolFlightVo.getAirProtocolFlightQueryDto().setOrgCodeList(orgs);
			
		} catch (TfrBusinessException e) {
			return this.returnError(e);
		}
	   return this.returnSuccess();	
	}
	
	/*
     * @author wqh
     * @date   2013-12-30
     * @desc 导出协议航班货量统计明细 
     * */
	@SuppressWarnings("rawtypes")
	public String exportProtocolFlightDetailExcel(){
		AirProtocolFlightQueryDto queryCondition=new AirProtocolFlightQueryDto();
		//初始化查询条件
		initcondition(queryCondition);
		//调用service获取文件名，输入流
    	List list = null;
    	try {
    		list=airProtocolFlightService.exportProtocolFlightDetailExcel(queryCondition);
    		excelFileName =(String) list.get(0);
    		protocolFlightExcelStream = (InputStream)list.get(1);
    		
		} catch (TfrBusinessException e) {
			e.printStackTrace();
			LOGGER.error(e+"");
			return returnError(e.getMessage());
			
		}
    	return this.returnSuccess();
		
	}
	
	//初始查询条件
	@SuppressWarnings("unused")
	private void initcondition(AirProtocolFlightQueryDto queryCondition){
		//查询协议航班
		queryCondition.setIsProtocol(AirfreightConstants.AIR_PROTOCOLFLIGHT_Y);
		//航班号
		if(StringUtil.isNotEmpty(airProtocolFlightVo.getAirProtocolFlightQueryDto().getFlightNo())){
			queryCondition.setFlightNo(airProtocolFlightVo.getAirProtocolFlightQueryDto().getFlightNo());
		}
		//始发站
		if(StringUtil.isNotEmpty(airProtocolFlightVo.getAirProtocolFlightQueryDto().getDepartCity())){
			queryCondition.setDepartCity(airProtocolFlightVo.getAirProtocolFlightQueryDto().getDepartCity());
		}
		
		//目的站
		if(StringUtil.isNotEmpty(airProtocolFlightVo.getAirProtocolFlightQueryDto().getArriveCity())){
			queryCondition.setArriveCity(airProtocolFlightVo.getAirProtocolFlightQueryDto().getArriveCity());
		}
		//截止时间
		if(StringUtil.isNotEmpty(airProtocolFlightVo.getAirProtocolFlightQueryDto().getCurrQueryTime())){
			queryCondition.setCurrQueryTime(airProtocolFlightVo.getAirProtocolFlightQueryDto().getCurrQueryTime());
		}
		//时间校验
		if(StringUtil.isEmpty(airProtocolFlightVo.getAirProtocolFlightQueryDto().getCurrQueryTime())){
			throw new TfrBusinessException("请输入查询截止时间");
		}
	}
	
	//get
	public AirProtocolFlightVo getAirProtocolFlightVo() {
		return airProtocolFlightVo;
	}
	//set
	public void setAirProtocolFlightVo(AirProtocolFlightVo airProtocolFlightVo) {
		this.airProtocolFlightVo = airProtocolFlightVo;
	}


	public void setAirProtocolFlightService(
			IAirProtocolFlightService airProtocolFlightService) {
		this.airProtocolFlightService = airProtocolFlightService;
	}


	public String getExcelFileName() {
		return excelFileName;
	}


	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}


	public InputStream getProtocolFlightExcelStream() {
		return protocolFlightExcelStream;
	}


	public void setProtocolFlightExcelStream(InputStream protocolFlightExcelStream) {
		this.protocolFlightExcelStream = protocolFlightExcelStream;
	}
	
	
	
}
