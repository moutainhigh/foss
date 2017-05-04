package com.deppon.foss.module.transfer.platform.server.dao;

import java.text.ParseException;
import java.util.ArrayList;
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

import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrAbsenteeInfoDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrAbsenteeInfoQcDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/platform/server/spring-test.xml" })
@Transactional
public class TfrCtrAbsenteeInfoDaoTest {

	@Autowired
	private ITfrCtrAbsenteeInfoDao dao;

	@Test
	public void insertTfrCtrAbsenteeInfoTest() {

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Date absenteeEntryDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date absentBeginDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date absentEndDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date createDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date modifyDate = calendar.getTime();

		TfrCtrAbsenteeInfoEntity entity = new TfrCtrAbsenteeInfoEntity();
		entity.setId("id");
		entity.setAbsenteeCode("absenteeCode");
		entity.setAbsenteeName("absenteeName");
		entity.setAbsenteePostName("absenteePostName");
		entity.setAbsenteeStatus("absenteeStatus");
		entity.setAbsenteeEntryDate(absenteeEntryDate);
		entity.setAbsentBeginDate(absentBeginDate);
		entity.setAbsentEndDate(absentEndDate);
		entity.setAbsenteeOrgCode("absenteeOrgCode");
		entity.setAbsenteeOrgName("absenteeOrgName");
		entity.setTransferCenterCode("transferCenterCode");
		entity.setTransferCenterName("transferCenterName");
		entity.setCreateUser("createUser");
		entity.setCreateUserName("createUserName");
		entity.setCreateDate(createDate);
		entity.setModifyUser("modifyUser");
		entity.setModifyUserName("modifyUserName");
		entity.setModifyDate(modifyDate);

		dao.insertTfrCtrAbsenteeInfo(entity);
	}

	@Test
	public void deleteTfrCtrAbsenteeInfosTest() {
		List<String> ids = new ArrayList<String>();
		ids.add("id");
		ids.add("id2");
		dao.deleteTfrCtrAbsenteeInfos(ids);
	}

	@Test
	public void updateTfrCtrAbsenteeInfoTest() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Date absenteeEntryDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date absentBeginDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date absentEndDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date createDate = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date modifyDate = calendar.getTime();

		TfrCtrAbsenteeInfoEntity entity = new TfrCtrAbsenteeInfoEntity();
		entity.setId("id");
		entity.setAbsenteeCode("absenteeCode");
		entity.setAbsenteeName("absenteeName");
		entity.setAbsenteePostName("absenteePostName");
		entity.setAbsenteeStatus("absenteeStatus");
		entity.setAbsenteeEntryDate(absenteeEntryDate);
		entity.setAbsentBeginDate(absentBeginDate);
		entity.setAbsentEndDate(absentEndDate);
		entity.setAbsenteeOrgCode("absenteeOrgCode");
		entity.setAbsenteeOrgName("absenteeOrgName");
		entity.setTransferCenterCode("transferCenterCode");
		entity.setTransferCenterName("transferCenterName");
		entity.setCreateUser("createUser");
		entity.setCreateUserName("createUserName");
		entity.setCreateDate(createDate);
		entity.setModifyUser("modifyUser");
		entity.setModifyUserName("modifyUserName");
		entity.setModifyDate(modifyDate);

		dao.updateTfrCtrAbsenteeInfo(entity);
	}

	@Test
	public void queryTfrCtrAbsenteeInfoByIdTest() {
		TfrCtrAbsenteeInfoEntity entity = dao.queryTfrCtrAbsenteeInfoById("id");
		System.out.println(entity);
	}

	@Test
	public void queryTfrCtrAbsenteeInfoCountTest() {
		TfrCtrAbsenteeInfoQcDto qcDto = new TfrCtrAbsenteeInfoQcDto();
		System.out.println(dao.queryTfrCtrAbsenteeInfoCount(qcDto));
	}

	@Test
	public void queryTfrCtrAbsenteeInfosTest() {
		TfrCtrAbsenteeInfoQcDto qcDto = new TfrCtrAbsenteeInfoQcDto();
		qcDto.setTransferCenterCode("transferCenterCode");
		qcDto.setAbsenteeStatus("absenteeStatus");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date createBeginDate = calendar.getTime();
		qcDto.setCreateBeginDate(createBeginDate);
		Date createEndDate = new Date();
		qcDto.setCreateEndDate(createEndDate);
		List<TfrCtrAbsenteeInfoEntity> infos = dao
				.queryTfrCtrAbsenteeInfos(qcDto);
		for (TfrCtrAbsenteeInfoEntity info : infos) {
			System.out.println(info);
		}
	}

	@Test
	public void queryPagingTfrCtrAbsenteeInfosTest() {
		TfrCtrAbsenteeInfoQcDto qcDto = new TfrCtrAbsenteeInfoQcDto();
		qcDto.setTransferCenterCode("transferCenterCode");
		qcDto.setAbsenteeStatus("absenteeStatus");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date createBeginDate = calendar.getTime();
		qcDto.setCreateBeginDate(createBeginDate);
		Date createEndDate = new Date();
		qcDto.setCreateEndDate(createEndDate);
		List<TfrCtrAbsenteeInfoEntity> infos = dao
				.queryPagingTfrCtrAbsenteeInfos(qcDto,1,0);
		for (TfrCtrAbsenteeInfoEntity info : infos) {
			System.out.println(info);
		}
		System.out.println(infos.size());
	}
	
	@Test
	public void select1TfrCtrAbsenteeInfoTest() throws ParseException{
		TfrCtrAbsenteeInfoEntity entity = new TfrCtrAbsenteeInfoEntity();
		entity.setAbsenteeCode("050903");
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = dateFormat.parse("2014-03-07 00:00:00");
		Date endDate = dateFormat.parse("2014-03-07 23:59:59");
		System.out.println(beginDate);
		System.out.println(endDate);
		entity.setAbsentBeginDate(beginDate);
		entity.setAbsentEndDate(endDate);*/
		TfrCtrAbsenteeInfoEntity info = dao.select1TfrCtrAbsenteeInfo(entity);
		System.out.println(info);
		System.out.println(info == null);
	}
}
