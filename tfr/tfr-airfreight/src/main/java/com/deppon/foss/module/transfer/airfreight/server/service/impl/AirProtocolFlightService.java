package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirProtocolFlightDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirProtocolFlightService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirProtocolFlightQueryDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.CollectionUtils;
public class AirProtocolFlightService implements IAirProtocolFlightService{

	static final Logger logger = LoggerFactory
			.getLogger(AirProtocolFlightService.class);
	//
	private IAirProtocolFlightDao airProtocolFlightDao;
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;
	
	/*
	 * @desc:查询协议航班货量list（分页）
	 * @param queryCondition
	 * @author：foss-105795-wqh
	 * @date:2014-02-18
	 * */
	public List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition,int start,int limit){
		//根据当前用户code查询用户有权限操作的部门code
		List<String> orgList=queryOptAllOrgCodeByUserCode();
		queryCondition.setOrgCodeList(orgList);
		
		List<AirProtocolFlightDto> airProtocolFlightList=
				airProtocolFlightDao.queryProtocolFlightList(queryCondition,start,limit);
		if(airProtocolFlightList!=null && airProtocolFlightList.size()>0){
			airProtocolFlightList= calculateProtocolFlightList(airProtocolFlightList,queryCondition);
			
		}else{
			throw new TfrBusinessException("查询结果为空");
		}

		return airProtocolFlightList;
	}
	/*
	 * @desc:查询协议航班货量list 
	 * @param queryCondition
	 * @author：foss-105795-wqh
	 * @date:2014-02-18
	 * */
	public List<AirProtocolFlightDto> queryProtocolFlightList(AirProtocolFlightQueryDto queryCondition){
		//根据当前用户code查询用户有权限操作的部门code
		List<String> orgList=queryOptAllOrgCodeByUserCode();
		queryCondition.setOrgCodeList(orgList);
		
		List<AirProtocolFlightDto> airProtocolFlightList=
				airProtocolFlightDao.queryProtocolFlightList(queryCondition);
		if(airProtocolFlightList!=null && airProtocolFlightList.size()>0){
			return airProtocolFlightList;
			
		}else{
			throw new TfrBusinessException("查询结果为空");
		}
	}

	//处理查询结果的list，其中包括所有的计算
	/*
	 * @desc 处理查询结果的list，其中包括所有的计算
	 * @param resultList
	 * @param queryCondition
	 * @author foss-105795-wqh
	 * @date:2014-02-20
    * */
	@SuppressWarnings("unused")
	private List<AirProtocolFlightDto> calculateProtocolFlightList(List<AirProtocolFlightDto> airProtocolFlightList,
			AirProtocolFlightQueryDto queryCondition){
		if(airProtocolFlightList!=null && airProtocolFlightList.size()>0){
			for(int i=0;i<airProtocolFlightList.size();i++){
				//本月协议货量初始化
				if(airProtocolFlightList.get(i).getCurrMonthGoodsAmount()==null){
					airProtocolFlightList.get(i).setCurrMonthGoodsAmount(0+"");
				}
				//本月已配载货量初始化
				if(airProtocolFlightList.get(i).getCurrMonthTotalOptGoodsAmount()==null){
					airProtocolFlightList.get(i).setCurrMonthTotalOptGoodsAmount(0+"");
				}
				//当日已配载货量初始化
				if(airProtocolFlightList.get(i).getCurrTimeOptGoodsAmount()==null){
					airProtocolFlightList.get(i).setCurrTimeOptGoodsAmount(0+"");
				}
				//本月天数
				int monthDays=getMonthOfDays(queryCondition.getCurrQueryTime());
				//本月剩余天数
				int reMonthDays=getReDaysOfMonth(queryCondition.getCurrQueryTime());
				
				//计算对应航班的本月协议货量：  航班号“对应航班的”日均协议货量(公斤)“为a，当月的总天数为b，“本月协议货量(公斤) ”= a*b
				BigDecimal currGoodsMonthAmount=new BigDecimal(airProtocolFlightList.get(i).getCurrMonthGoodsAmount());
				currGoodsMonthAmount=currGoodsMonthAmount.multiply(new BigDecimal(monthDays));
				airProtocolFlightList.get(i).setCurrMonthGoodsAmount(currGoodsMonthAmount.toString());
				
				//百分号后保留2位小数	
				 NumberFormat format = NumberFormat.getPercentInstance();
				 format.setMinimumFractionDigits(2);
				/*
				 * 计算本月时间进度 ：所包含的本月天数(未满一天则按一天算)”为a，本月的总天数为b，则”本月时间进度“=a/b*100%，四舍五入保留两位小数
				 * */
			    
				 //如果剩余时间为0，则时间进度为100.00%
				 if(reMonthDays==0){
					 airProtocolFlightList.get(i).setCurrMonthTimeschedule("100.00%");
				 }else{
					 double resultTime=(monthDays-reMonthDays)*1.0/monthDays;
					 String timeShedule=format.format(resultTime);
					 airProtocolFlightList.get(i).setCurrMonthTimeschedule(timeShedule);
				 }
				
				/*
				 * 计算本月货量进度：7、	本月1号00:00:00至”查询截止时间“这段时间内新增的、使用”航班号“对应航班进行配载的正单的货物计费重量的总和为a，
				 * ”本月协议货量(公斤)“为b，则”当月货量进度“=a/b*100%，四舍五入保留两位小数
				 * */
				 //月配载货量
				 BigDecimal monthOptGoodsAmount=new BigDecimal(airProtocolFlightList.get(i).getCurrMonthTotalOptGoodsAmount());
				 //月协议货量
				 BigDecimal monthProtocolGoodsAmount=new BigDecimal(airProtocolFlightList.get(i).getCurrMonthGoodsAmount());

				 if(monthOptGoodsAmount.compareTo(BigDecimal.ZERO)==0){
					 airProtocolFlightList.get(i).setCurrMonthGoodschedule("0.00%");
				 }else{
					 //检查除数为0的情况
					 try {
						double resultDev= monthOptGoodsAmount.doubleValue()/monthProtocolGoodsAmount.doubleValue();
						 String goodsShedule=format.format(resultDev);
						 airProtocolFlightList.get(i).setCurrMonthGoodschedule(goodsShedule);
					 } catch (ArithmeticException e) {
						 airProtocolFlightList.get(i).setCurrMonthGoodschedule("0.00%");
					 }
					 
				 }
				 
				/*
				 * 计算剩余日需货量：所包含的本月天数(未满一天则按一天算)”为a，本月总天数为b，”本月累计已配载货量(公斤)“为c，”本月协议货量(公斤)”为d，
				 * 则”剩余日需货量(公斤)“=(d-c)/(b-a)，四舍五入保留两位小数
				 * */
				BigDecimal resultGoods= monthProtocolGoodsAmount.subtract(monthOptGoodsAmount);
				
				//处理分母为0的情况
				if(resultGoods.compareTo(BigDecimal.ZERO)==0){
					airProtocolFlightList.get(i).setReDateOptGoodsAmount(0.00+"");
				}else if(reMonthDays<=0){
					airProtocolFlightList.get(i).setReDateOptGoodsAmount(
							resultGoods.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}else{
					try {
						//正常情况
						double result=resultGoods.doubleValue()/reMonthDays;
						BigDecimal resultBig=new BigDecimal(result);
						
						airProtocolFlightList.get(i).setReDateOptGoodsAmount(
								resultBig.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					} catch (ArithmeticException e) {
						//处理分子为0的情况
						airProtocolFlightList.get(i).setReDateOptGoodsAmount(0.00+"");
					}
				}
				
				
			}
		}
			return airProtocolFlightList;
	}
	
	/**
	 * @author 105795
	 * @date   2014-02-22
	 * @param  conditionDto
	 * @see    导出协议航班货量统计明细
	 * */
	public List exportProtocolFlightDetailExcel(AirProtocolFlightQueryDto queryCondition){
		List<AirProtocolFlightDto> airProtocolFlightList;

		List<List<String>> rowList = new ArrayList<List<String>>();
		//参数检查
		if(queryCondition==null){
			throw new TfrBusinessException("参数不合法");
		}
		
	   
		airProtocolFlightList= queryProtocolFlightList(queryCondition);

		
		//如果查询结果为空，则导出空文件
		if(CollectionUtils.isEmpty(airProtocolFlightList)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}
		//计算 卸车任务绑定总票数、总件数、绑定件数、叉车扫描件数、绑定率
		airProtocolFlightList=calculateProtocolFlightList(airProtocolFlightList,queryCondition);
		
		for(AirProtocolFlightDto airProtocolFlight : airProtocolFlightList){
			List<String> columnList = new ArrayList<String>();
			//航班号
			columnList.add(airProtocolFlight.getFlightNo()==null ?"":airProtocolFlight.getFlightNo());
			//始发站
			columnList.add(airProtocolFlight.getDepartCity()==null ?"":airProtocolFlight.getDepartCity());
			//目的站
			columnList.add(airProtocolFlight.getArriveCity()==null?"":airProtocolFlight.getArriveCity());
			//本月协议货量(公斤)
			columnList.add(airProtocolFlight.getCurrMonthGoodsAmount()==null?"":airProtocolFlight.getCurrMonthGoodsAmount());
			//当日已配载货量(公斤)
			columnList.add(airProtocolFlight.getCurrTimeOptGoodsAmount()==null? "":airProtocolFlight.getCurrTimeOptGoodsAmount());
			//本月累计已配载货量(公斤)
			columnList.add(airProtocolFlight.getCurrMonthTotalOptGoodsAmount()==null?"":airProtocolFlight.getCurrMonthTotalOptGoodsAmount());
			//本月时间进度
			columnList.add(airProtocolFlight.getCurrMonthTimeschedule()==null?"":airProtocolFlight.getCurrMonthTimeschedule());
			//本月货量进度
			columnList.add(airProtocolFlight.getCurrMonthGoodschedule()==null?"":airProtocolFlight.getCurrMonthGoodschedule());
			//剩余日需货量
			columnList.add(airProtocolFlight.getReDateOptGoodsAmount()==null?"":airProtocolFlight.getReDateOptGoodsAmount());
			rowList.add(columnList);
		}
		
		String[] rowHeaders={
				"航班号",
				"始发站",
				"目的站",
				"本月协议货量(公斤)",
				"当日已配载货量(公斤)",
				"本月累计已配载货量(公斤)",
				"本月时间进度",
				"本月货量进度",
				"剩余日需货量(公斤)"	
		};
		//导出资源类
		ExportResource exportResource = new ExportResource();
		//设置导出文件的表头
	    exportResource.setHeads(rowHeaders);
	    //设置导出数据
	    exportResource.setRowList(rowList);
	    //导出设置
	    ExportSetting exportSetting = new ExportSetting();
	    //设置sheetname
	    exportSetting.setSheetName("协议航班货量统计明细列表");
	    //设置sheet行数
	    exportSetting.setSize(airProtocolFlightList.size() + 1);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    List list = new ArrayList();
	    InputStream excelStream=null;
		try {
			// 获取输入流
			 excelStream = objExporterExecutor.exportSync(
					exportResource, exportSetting);

			 //拼凑导出文件名
			String yearStr= queryCondition.getCurrQueryTime().substring(0, ConstantsNumberSonar.SONAR_NUMBER_4);
			String monthStr=queryCondition.getCurrQueryTime().substring(ConstantsNumberSonar.SONAR_NUMBER_5, ConstantsNumberSonar.SONAR_NUMBER_7);
			String dateStr=queryCondition.getCurrQueryTime().substring(ConstantsNumberSonar.SONAR_NUMBER_8, ConstantsNumberSonar.SONAR_NUMBER_10);

			// 文件名
			String name = "协议航班货量统计_"+yearStr+monthStr+dateStr;
			String fileName;

			String agent = (String) ServletActionContext.getRequest()
					.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(name, "UTF-8");
			}
			list.add(fileName);
			list.add(excelStream);
		} catch (UnsupportedEncodingException e) {
			//抛出业务异常
			throw new TfrBusinessException(e.getMessage());
		} //设置fileName
		finally{
			if(excelStream!=null){
				try {
					excelStream.close();
				} catch (IOException e) {
					logger.error("{exportProtocolFlightDetailExcel：} 文件关闭失败");
					throw new TfrBusinessException("文件关闭失败", "");
				}
			}
		}
        //返回action
        return list;
	}
	
	/*
	 * @desc 根据前台传过来的时间计算当月的天数
	 * @param currQueryTime
	 * @author foss-105795-wqh
	 * @date:2014-02-18
	 * */
	private int getMonthOfDays(String currQueryTime){
		int days=0;
		//坼分前台传过来的时间参数
		//获得年份
		String yearStr=currQueryTime.substring(0, ConstantsNumberSonar.SONAR_NUMBER_4);
		//获得月份
		String monthStr=currQueryTime.substring(ConstantsNumberSonar.SONAR_NUMBER_5, ConstantsNumberSonar.SONAR_NUMBER_7);
		int year=0;
		int month=0;		
	    //将字符转为整数	
		try {
			 year=Integer.parseInt(yearStr);
			
			 month=Integer.parseInt(monthStr);
			
		} catch (TfrBusinessException e) {
			throw new TfrBusinessException("非法时间格式");
		}
		 if(month!=2){//非闰年
		   switch(month)
		   {
		   case 1:
		   case ConstantsNumberSonar.SONAR_NUMBER_3:
		   case ConstantsNumberSonar.SONAR_NUMBER_5:
		   case ConstantsNumberSonar.SONAR_NUMBER_7:
		   case ConstantsNumberSonar.SONAR_NUMBER_8:
		   case ConstantsNumberSonar.SONAR_NUMBER_10:
		   case ConstantsNumberSonar.SONAR_NUMBER_12:days = ConstantsNumberSonar.SONAR_NUMBER_31 ;
		   		break;
		   case ConstantsNumberSonar.SONAR_NUMBER_4:
		   case ConstantsNumberSonar.SONAR_NUMBER_6:
		   case ConstantsNumberSonar.SONAR_NUMBER_9:
		   case ConstantsNumberSonar.SONAR_NUMBER_11:days = ConstantsNumberSonar.SONAR_NUMBER_30;

		   }
		  }
		  else{//闰年
		   if(year%ConstantsNumberSonar.SONAR_NUMBER_4==0 && year%ConstantsNumberSonar.SONAR_NUMBER_100!=0 || year%ConstantsNumberSonar.SONAR_NUMBER_400==0)
		    days = ConstantsNumberSonar.SONAR_NUMBER_29;
		   else  days = ConstantsNumberSonar.SONAR_NUMBER_28;

		  }
		return days;
	}
	
	/*
	 * @desc 根据前台传过来的时间计算当月剩下的天数
	 * @param currQueryTime
	 * @author foss-105795-wqh
	 * @date:2014-02-18
	 * */
	@SuppressWarnings("unused")
	private int getReDaysOfMonth(String currQueryTime){
		int days=0;
		String dayStr=currQueryTime.substring(ConstantsNumberSonar.SONAR_NUMBER_8, ConstantsNumberSonar.SONAR_NUMBER_10);
		int passDays=0;//过去天数
		
		try {
			passDays=Integer.parseInt(dayStr);
		} catch (TfrBusinessException e) {
			throw new TfrBusinessException("非法时间格式");
		}
		//本月天数
		days=getMonthOfDays(currQueryTime);
		//剩下天数
		return days-passDays;
	}
	
	/*
	 * @desc:根据当前用户code查询用户有权限操作的部门code
	 * @param userCode
	 * @author：foss-105795-wqh
	 * @date:2014-02-22
	 * */
	public List<String> queryOptAllOrgCodeByUserCode(){
		Set<String> set=new HashSet<String>();
		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		//获取用户信息
		UserEntity user = FossUserContext.getCurrentInfo().getUser();
		//获取该用户的数据权限部门
		List<String> orgList=airProtocolFlightDao.queryOptAllOrgCodeByUserCode(user.getEmployee().getEmpCode());
				
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		//默认获取本空运总调
		set.add(orgAdministrativeInfoEntity.getCode());
		if(orgList!=null && orgList.size()>0){
			for(int i=0;i<orgList.size();i++){
				set.add(orgList.get(i));
			}
		}
		List<String> orgcodes=new ArrayList(set);
		
		return orgcodes;	
	}
	
	//set 
	
	public void setAirProtocolFlightDao(IAirProtocolFlightDao airProtocolFlightDao) {
		this.airProtocolFlightDao = airProtocolFlightDao;
	}
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	
}
