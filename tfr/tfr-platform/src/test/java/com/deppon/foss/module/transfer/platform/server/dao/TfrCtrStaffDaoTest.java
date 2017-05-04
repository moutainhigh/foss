package com.deppon.foss.module.transfer.platform.server.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrStaffDao;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrStaffNoDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/platform/server/spring-test.xml"})
public class TfrCtrStaffDaoTest {

	@Autowired
	private ITfrCtrStaffDao dao;

	TfrCtrStaffNoDutyEntity entity = new TfrCtrStaffNoDutyEntity();

	@Before
	public void before() {
		entity.setId("id");
		entity.setEmpCode("empCode");
		entity.setEmpName("empName");
		entity.setOrgCode("orgCode");
		entity.setOrgName("orgName");
		entity.setPostCode("postCode");
		entity.setPostName("postName");
		entity.setStatisticDate(new Date());
		entity.setStatisticMonth("statisticMonth");
		entity.setTransferCenterCode("transferCenterCode");
		entity.setTransferCenterName("transferCenterName");
	}

	@Test
	public void getEmployeesByPosts() {
		List<String> posts = Arrays
				.asList(PlatformConstants.POST_FORKLIFT_DRIVER_CODES);
		dao.getEmployeesByPosts(posts);
	}

	@Test
	public void selectOneWorkLoad() {
		TfrCtrStaffNoDutyEntity loader = new TfrCtrStaffNoDutyEntity();
		dao.selectOneWorkLoad(loader);
	}

	@Test
	public void selectOneTrayScan() {
		TfrCtrStaffNoDutyEntity forkliftDriver = new TfrCtrStaffNoDutyEntity();
		dao.selectOneTrayScan(forkliftDriver);
	}

	@Test
	public void selectOneStaff3DayNoDuty() {
		TfrCtrStaffNoDutyEntity entity = null;
		dao.selectOneStaff3DayNoDuty(entity);
	}

	@Test
	public void selectCntStaffNoDutyInPre2Day() {
		TfrCtrStaffNoDutyEntity entity = new TfrCtrStaffNoDutyEntity();
		entity.setEmpCode("042770");
		entity.setTransferCenterCode("transferCenterCode");
		entity.setStatisticDate(new Date());
		entity.setStatisticMonth("statisticMonth");
		dao.selectCntStaffNoDutyInPre2Day(entity);
	}

	@Test
	public void queryTfrCtrActual() {
		dao.queryTfrCtrActual("000", new Date());
	}

	@Test
	@Transactional
	public void insertStaffNoDuty() {
		dao.insertStaffNoDuty(entity);
	}

	@Test
	@Transactional
	public void insertStaff3DayNoDuty() {
		dao.insertStaff3DayNoDuty(entity);
	}

	@Test
	@Transactional
	public void insertStaffOnDuty() {
		dao.insertStaffOnDuty(entity);
	}

	@Test
	public void queryTfrCtrStaff3DayNoDuty() {
		dao.queryTfrCtrStaff3DayNoDuty("transferCenterCode", "statisticMonth");
	}

	@Test
	public void queryTfrCtrStaffDtos() {
		TfrCtrStaffQcDto dto = new TfrCtrStaffQcDto();
		Date queryDate = PlatformUtils.parseString2Date("2014-05-16",
				"yyyy-MM-dd");
		dto.setQueryDate(queryDate);
		dto.setQueryMonth("2014-05");
		dto.setFirstMomOfQueryMonth(PlatformUtils.getFirstDayOfMonth(queryDate));
		dto.setLastMomOfQueryDate(PlatformUtils.getLastMomentOfDay(queryDate));
		dto.setTransferCenterCode("W1200030122");
		List<TfrCtrStaffDto> tfrStaffs = dao.queryTfrCtrStaffDtos(dto);
		for(TfrCtrStaffDto item: tfrStaffs){
			System.out.println(item);
		}
	}

}
