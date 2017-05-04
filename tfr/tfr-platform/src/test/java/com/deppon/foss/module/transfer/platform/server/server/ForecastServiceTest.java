//package com.deppon.foss.module.transfer.platform.server.server;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.deppon.foss.module.transfer.platform.api.server.service.IForecastService;
//import com.deppon.foss.module.transfer.platform.api.shared.define.ForecastConstants;
//import com.deppon.foss.module.transfer.platform.api.shared.domain.BillingEntity;
//import com.deppon.foss.module.transfer.platform.api.shared.domain.ChangeQuantityEntity;
//import com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity;
//import com.deppon.foss.module.transfer.platform.api.shared.domain.InTransitEntity;
//import com.deppon.foss.module.transfer.platform.api.shared.dto.ForecastDto;
//import com.deppon.foss.module.transfer.platform.api.shared.dto.StatisticalInquiriesDto;
//import com.deppon.foss.module.transfer.platform.api.shared.vo.ForecastVO;
//import com.deppon.foss.module.transfer.platform.server.BaseTestCase;
//import com.deppon.foss.util.DateUtils;
//
//public class ForecastServiceTest extends BaseTestCase {
//	@Autowired
//	IForecastService forecastService;
//	
//	@Test
//	public void queryByPage() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.convert("2014-05-14 09:00:00", "yyyy-MM-dd hh:mm:ss"));
//			forecastService.queryByPage(forecastQuantityEntity, 10, 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryByPageCount() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.queryByPageCount(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void getCount() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setRelevantOrgCode("W140110");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.getCount(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void queryForecastTimeList() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setRelevantOrgCode("W140110");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.queryForecastTimeList(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void selectMaxStatisticsTime() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.selectMaxStatisticsTime(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryForecastQuantityList() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.queryForecastQuantityList(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryTotalList() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.queryTotalList(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void querySpecHourList() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.querySpecHourList(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void countRealWeightAndVolume() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setRelevantOrgCode("W140110");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.countRealWeightAndVolume(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void calculateAverageWeightAndVolume() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setRelevantOrgCode("W140110");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.getStartDatetime(new Date()));
//			forecastService.calculateAverageWeightAndVolume(DateUtils.getStartDatetime(new Date()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryBillingList() {
//		try {
//			BillingEntity billing = new BillingEntity();
//			billing.setType(ForecastConstants.FORECAST_DEPART);
//			billing.setBelongOrgCode("W3100020616");
//			billing.setRelevantOrgCode("W140110");
//			forecastService.queryBillingList(billing);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryInTransitList() {
//		try {
//			InTransitEntity billing = new InTransitEntity();
//			billing.setType(ForecastConstants.FORECAST_DEPART);
//			billing.setBelongOrgCode("W3100020616");
//			billing.setRelevantOrgCode("W140110");
//			forecastService.queryInTransitList(billing);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void changeQuantity() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.changeQuantity(forecastQuantityList, changeQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void queryChangeInByDate() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.queryChangeInByDate(new Date(), "W3100020616", "W140110");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryChangeOutByDate() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.queryChangeOutByDate(new Date(), "W3100020616", "W140110");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void billingForecast() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			List<ForecastDto> l =  new ArrayList<ForecastDto>();
//			Set<String> sal = new HashSet<String>();
//			forecastService.billingForecast("8e6ff110-bc86-40b9-ba6f-5d583227a524",
//					"DEPART", new Date(), "W3100020616", "W140110", "无", l, sal);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void inTransitForecast() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			List<ForecastDto> l =  new ArrayList<ForecastDto>();
//			Set<String> sal = new HashSet<String>();
//			forecastService.inTransitForecast("8e6ff110-bc86-40b9-ba6f-5d583227a524",
//					"DEPART", new Date(), "W3100020616", "W140110", "无", l, sal);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void departBilling() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.departBilling("W3100020616", "W140110", new Date(), DateUtils.getEndDatetime(new Date()));;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void departInTransit() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.departInTransit("W3100020616", "W140110", new Date(), DateUtils.getEndDatetime(new Date()));;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void departInventory() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("W3100020616");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.departInventory("W3100020616", "W140110", new Date(), DateUtils.getEndDatetime(new Date()));;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryDepartureType() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.queryDepartureType("W3100020616", "W140110");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void encodeFileName() {
//		try {
//			List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setForecastQuantityId("8e6ff110-bc86-40b9-ba6f-5d583227a524");
//			forecastQuantityEntity.setWeightTotal(BigDecimal.valueOf(100));
//			forecastQuantityList.add(forecastQuantityEntity);
//			ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
//			changeQuantityEntity.setOrigDestOrg("");
//			changeQuantityEntity.setModifyWeight(BigDecimal.valueOf(100));
//			forecastService.encodeFileName("W3100020616");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void exportExcelStream() {
//		try {
//			ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
//			forecastQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
//			forecastQuantityEntity.setBelongOrgCode("W3100020616");
//			forecastQuantityEntity.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
//			forecastQuantityEntity.setForecastTime(DateUtils.convert("2014-05-14 09:00:00", "yyyy-MM-dd hh:mm:ss"));
//			forecastService.exportExcelStream(forecastQuantityEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void detailExportExcelStream() {
//		try {
//			forecastService.detailExportExcelStream("123123");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void queryStatisticalInquiries() {
//		try {
//			ForecastVO forecastVO = new ForecastVO();
//			forecastVO.setStart(0);
//			forecastVO.setLimit(10);
//			StatisticalInquiriesDto sd = new StatisticalInquiriesDto();
//			sd.setTransforCenterCode("W3100020616");
//			sd.setTransportModelCode("ALL");
//			sd.setGoodStatus(ForecastConstants.FORECAST_NO_DEPARTURE_TRANSFER);
//			forecastVO.setStatisticalInquiriesDto(sd);
//			forecastService.queryStatisticalInquiries(forecastVO);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryStatisticalInquiriesExcelStream() {
//		try {
//			ForecastVO forecastVO = new ForecastVO();
//			forecastVO.setStart(0);
//			forecastVO.setLimit(10);
//			StatisticalInquiriesDto sd = new StatisticalInquiriesDto();
//			sd.setTransforCenterCode("W3100020616");
//			sd.setTransportModelCode("ALL");
//			sd.setGoodStatus(ForecastConstants.FORECAST_NO_DEPARTURE_TRANSFER);
//			forecastVO.setStatisticalInquiriesDto(sd);
//			forecastService.queryStatisticalInquiriesExcelStream(forecastVO);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryForecastQtyByRelevantOrgCode() {
//		try {
//			List<String> deptCodes = new ArrayList<String>();
//			deptCodes.add("W3100020616");
//			forecastService.queryForecastQtyByRelevantOrgCode(deptCodes, new Date());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void queryForecastWeightByRelevantOrgCode() {
//		try {
//			List<String> deptCodes = new ArrayList<String>();
//			deptCodes.add("W3100020616");
//			forecastService.queryForecastWeightByRelevantOrgCode(deptCodes, new Date());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void querySerialnoByVehicleassembleNo() {
//		try {
//			ForecastVO forecastVO = new ForecastVO();
//			StatisticalInquiriesDto sd = new StatisticalInquiriesDto();
//			sd.setGoodStatus(ForecastConstants.FORECAST_IN_LIBRARY_NAME);
//			forecastService.querySerialnoByVehicleassembleNo(forecastVO);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
