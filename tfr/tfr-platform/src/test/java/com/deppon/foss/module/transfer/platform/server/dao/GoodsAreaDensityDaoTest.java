package com.deppon.foss.module.transfer.platform.server.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.platform.api.server.dao.IGoodsAreaDensityDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensity4SumDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/platform/server/spring-test.xml" })
public class GoodsAreaDensityDaoTest {

	@Autowired
	private IGoodsAreaDensityDao dao;

	private GoodsAreaDensityQcDto params4Sum() {
		GoodsAreaDensityQcDto dto = new GoodsAreaDensityQcDto();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date statisticDate = calendar.getTime();
		dto.setStatisticDate(statisticDate);
		dto.setStatisticMonth(PlatformUtils
				.formatDate2String(statisticDate, "yyyy-MM"));

		dto.setTransferCenterCode("W04061520xxx");
		return dto;
	}

	@Test
	public void selectGoodsAreaDensity4Sum() {
		List<GoodsAreaDensity4SumDto> infos = dao
				.selectGoodsAreaDensity4Sum(params4Sum());
		for (GoodsAreaDensity4SumDto info : infos) {
			System.out.println(info);
		}
	}

	@Test
	public void selectCntGoodsAreaDensity4Sum() {
		System.out.println(dao.selectCntGoodsAreaDensity4Sum(params4Sum()));
	}

	private GoodsAreaDensityQcDto params4Timely() {
		GoodsAreaDensityQcDto dto = new GoodsAreaDensityQcDto();
		dto.setTransferCenterCode("W3100020655");
		dto.setGoodsAreaCode("514");
		return dto;
	}

	@Test
	public void selectGoodsAreaDensity4Timely() {
		List<GoodsAreaDensityEntity> infos = dao
				.selectGoodsAreaDensity4Timely(params4Timely());
		System.out.println(infos);
	}

	@Test
	public void selectCntGoodsAreaDensity4Timely() {
		dao.selectCntGoodsAreaDensity4Timely(params4Timely());
	}

	private GoodsAreaDensityQcDto params4Daily() {
		GoodsAreaDensityQcDto dto = new GoodsAreaDensityQcDto();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = calendar.getTime();

		dto.setTransferCenterCode("W3100020655");
		dto.setBeginStatisticDate(date);
		dto.setEndStatisticDate(date);
		return dto;
	}

	@Test
	public void selectTfrCtrGoodsAreaAvgDensity4Daily() {
		dao.selectTfrCtrGoodsAreaAvgDensity4Daily(params4Daily());
	}

	@Test
	public void selectCntTfrCtrGoodsAreaAvgDensity4Daily() {
		dao.selectCntTfrCtrGoodsAreaAvgDensity4Daily(params4Daily());
	}

	@Test
	public void selectTfrCtrAvgDensity4Daily() {
		dao.selectTfrCtrAvgDensity4Daily(params4Daily());
	}

	@Test
	public void selectCntTfrCtrAvgDensity4Daily() {
		dao.selectCntTfrCtrAvgDensity4Daily(params4Daily());
	}

	private GoodsAreaDensityQcDto params4Monthly() {
		GoodsAreaDensityQcDto dto = new GoodsAreaDensityQcDto();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String format = dateFormat.format(new Date());
		dto.setTransferCenterCode("W3100020655");
		dto.setGoodsAreaCode("514");
		dto.setBeginStatisticMonth(format);
		dto.setEndStatisticMonth(format);
		return dto;
	}

	@Test
	public void selectTfrCtrGoodsAreaAvgDensity4Monthly() {
		List<GoodsAreaDensityEntity> infos = dao.selectTfrCtrGoodsAreaAvgDensity4Monthly(params4Monthly());
		System.out.println(infos.size());
	}
	
	@Test
	public void selectCntTfrCtrGoodsAreaAvgDensity4Monthly() {
		dao.selectCntTfrCtrGoodsAreaAvgDensity4Monthly(params4Monthly());
	}
	
	@Test
	public void selectTfrCtrAvgDensity4Monthly(){
		dao.selectTfrCtrAvgDensity4Monthly(params4Monthly());
	}

	@Test
	public void selectCntTfrCtrAvgDensity4Monthly(){
		dao.selectCntTfrCtrAvgDensity4Monthly(params4Monthly());
	}
	
	@Test
	@Transactional
	public void generateGoodsAreaDensity(){
		Date date = new Date();
		dao.generateGoodsAreaDensity(date);
	}
}
