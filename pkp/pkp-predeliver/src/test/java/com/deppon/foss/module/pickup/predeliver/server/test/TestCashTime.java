package com.deppon.foss.module.pickup.predeliver.server.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.predeliver.server.service.ITestService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;


//		/tfr-scheduling/src/main/resources/com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml		
//		/pkp-waybill/src/main/resources/com/deppon/foss/module/pickup/waybill/server/META-
//		/bse-baseinfo/src/main/resources/com/deppon/foss/module/base/baseinfo/server/META-
//		/bse-baseinfo/src/main/resources/com/deppon/foss/module/base/baseinfo/server/META- // 加载配置
@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations= {
		"classpath*:com/deppon/foss/module/pickup/predeliver/test/META-INF/spring-test.xml",
//		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/configurationParams.xml",
//		"classpath*:com/deppon/foss/module/base/baseinfo/api/server/META-INF/spring.xml",//base
//		"classpath*:com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml",
//		"classpath*:com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml",//transfer
//		"classpath*:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
//		"classpath*:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml"})//waybill
////		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/commonSelector.xml",
//		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/lclDeliveryToCashManagementUnloading.xml"
		})
public class TestCashTime {
	private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
	
	@Autowired // 注入
	private ITestService testService;
//	@Test 
//	public void Test1(){
//		String waybillNo = "201505260";
////		WaybillEntity waybillEntity = testService.queryWaybillBasicByNo(waybillNo);
//		List<PathDetailEntity>list = testService.queryPathDetailByNos(waybillNo, null);
//		System.out.println(list);
//	}
	/**
	 * @param notificationEntity
	 * @throws ParseException
	 * 计算规定兑现时间
	 */ 
	@Test 
	public  void executeCashTime(){
		String waybillNo = "231610311";
		WaybillEntity waybillEntity = testService.queryWaybillBasicByNo(waybillNo);
		//根据运单的运输性质判断,如果是精准城运和精准卡航有规定兑现时间
		if(waybillEntity.getProductCode()!=null&&
				(waybillEntity.getProductCode().equals("FLF")||
					waybillEntity.getProductCode().equals("FSF"))) {
			//根据运单号查询走货路径详情
			List<PathDetailEntity> pathDetailList = testService.queryPathDetailByNos(waybillNo,null);
			if(pathDetailList==null||pathDetailList.size()==0) {
				throw new NotifyCustomerException("通知信息异常(不存在走货路径)");
			}
			//查出运单的起始时间
			Date waybillBeginTime = waybillEntity.getBillTime();
			//使用Calender类进行计算
			Calendar waybillBeginCalendar = Calendar.getInstance();
			waybillBeginCalendar.setTime(waybillBeginTime);
			//保存最后部门code
			String lastOrgCode = null ;
			try {
				for(int i=0;i<pathDetailList.size();i++) {
					//判断最后的目的地是否为营业部
					if(i==pathDetailList.size()-1) {
						lastOrgCode = pathDetailList.get(i).getObjectiveOrgCode();
						SaleDepartmentEntity saleDepartmentEntity = testService.querySaleDepartmentByCode(lastOrgCode);
						if(saleDepartmentEntity==null) {
							throw new NotifyCustomerException("未查询到目的营业部");
						}
						//如果不是驻地部门则要加上最后一段距离
						if(saleDepartmentEntity.getStation()==null||!saleDepartmentEntity.getStation().equals("Y")) {
							//根据出发部门和目的部门查询运输时间、卸货时间
								addTransportTime( waybillBeginCalendar, pathDetailList.get(i));
						}
					}else {
						//根据出发部门和目的部门查询运输时间、卸货时间
						addTransportTime( waybillBeginCalendar, pathDetailList.get(i));
					}
				}
				//根据目的部门查询相应的兑现时间段
				List<LclDeliveryToCashManagementDeliveryEntity> lclDeliveryToCashManagementDeliveryList = 
						testService.queryLclDeliveryToCashManagementDeliveryByOrgCode(lastOrgCode);
				if(lclDeliveryToCashManagementDeliveryList==null||lclDeliveryToCashManagementDeliveryList.size()==0) {
					throw new NotifyCustomerException("通知信息异常(未查询到兑现时间)");
				}
				Calendar beginCalendar = Calendar.getInstance();
				Calendar endCalendar = Calendar.getInstance();
				for(int i = 0;i<lclDeliveryToCashManagementDeliveryList.size();i++) {
					beginCalendar.setTime(lclDeliveryToCashManagementDeliveryList.get(i).getStartDate());
					endCalendar.setTime(lclDeliveryToCashManagementDeliveryList.get(i).getEndDate());
					//如果结束时间为24:00,转换后后变为00:00,需转换回来
					int endHour =endCalendar.get(Calendar.HOUR_OF_DAY);
					if(endHour==0) {
						endHour = 24;
					}
					//如果小时能够匹配上对应的时间段则取对应的规定兑现时间
					if(waybillBeginCalendar.get(Calendar.HOUR_OF_DAY)>=beginCalendar.get(Calendar.HOUR_OF_DAY)&&
							waybillBeginCalendar.get(Calendar.HOUR_OF_DAY)<endHour) {
						Calendar targetTimeCalendar = Calendar.getInstance();
						SimpleDateFormat targetSdf = new SimpleDateFormat("HH:mm");
						Date targetDate = targetSdf.parse(lclDeliveryToCashManagementDeliveryList.get(i).getDeliverOnTime());
						targetTimeCalendar.setTime(targetDate);
						waybillBeginCalendar.set(Calendar.HOUR_OF_DAY,targetTimeCalendar.get(Calendar.HOUR_OF_DAY));
						waybillBeginCalendar.set(Calendar.MINUTE,targetTimeCalendar.get(Calendar.MINUTE));
						waybillBeginCalendar.add(Calendar.DATE, Integer.parseInt(lclDeliveryToCashManagementDeliveryList.get(0).getDeliverOnDay()));
						break;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
				throw new NotifyCustomerException("时间转换异常！");
			}
			@SuppressWarnings("unused")
			Date cashTime = waybillBeginCalendar.getTime();
		}else {
		}
	}
//
//
//
	/**
	 * @param waybillBeginCalendar
	 * @param pathDetailEntity
	 * @throws ParseException
	 * 计算走货路径时间
	 */
	private void addTransportTime(Calendar waybillBeginCalendar, 
			PathDetailEntity pathDetailEntity) throws ParseException {
		//查询走货路径所需运行时间和卸货时间
		List<LclDeliveryToCashManagementUnloadingEntity> lclList=
				testService.queryLclDeliveryToCashManagementUnloadingEntitytByCode(
				pathDetailEntity.getOrigOrgCode(),pathDetailEntity.getObjectiveOrgCode());
		if(lclList==null) {
			throw new NotifyCustomerException("通知信息异常(未查询到走货时间)");
		}
		//在开单时间上增加运行时间和卸货时间（接口已计算总和，格式为小时分：HH:mm + 天数）
//		//卸出时间(天数)unloadingTimeDay;
//		waybillBeginCalendar.add(Calendar.DATE,Integer.parseInt(lclList.get(lclList.size()-1).getUnloadingTimeDay()));
		//运行时间：时+分
		waybillBeginCalendar.add(Calendar.HOUR_OF_DAY,Integer.parseInt(lclList.get(lclList.size()-1).getScheduleHours()));
		waybillBeginCalendar.add(Calendar.MINUTE,Integer.parseInt(lclList.get(lclList.size()-1).getScheduleMins()));
		//卸出时间(HH:mm)unloadingTime;
		SimpleDateFormat HHmmSdf = new SimpleDateFormat("HH:mm");
		Date unloadingTime  = HHmmSdf.parse(lclList.get(lclList.size()-1).getUnloadingTimeOut());
		Calendar unloadingTimeCalendar = Calendar.getInstance();
		unloadingTimeCalendar.setTime(unloadingTime);
		waybillBeginCalendar.add(Calendar.HOUR_OF_DAY,unloadingTimeCalendar.get(Calendar.HOUR_OF_DAY));
		waybillBeginCalendar.add(Calendar.MINUTE,unloadingTimeCalendar.get(Calendar.MINUTE));
	}
//
//	/**
//	 * @param notificationEntity
//	 * @throws ParseException
//	 * 计算规定兑现时间
//	 */ 
	public static void main(String []args) throws ParseException {
//		executeCashTime();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY,33);
		System.out.println();
	}
	
}



