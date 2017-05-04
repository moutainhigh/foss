package com.deppon.foss.module.transfer.platform.server.dao;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelActualEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelBudgetEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.module.transfer.platform.server.dao.impl.TfrCtrPersonnelBudgetDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/platform/server/spring-test.xml" })
public class TfrCtrPersonnelBudgetDaoTest {

	@Autowired
	private TfrCtrPersonnelBudgetDao dao;

	String id = "id";
	String transferCenterCode = "transferCenterCode";
	Date endEffectiveDate = PlatformUtils.getLastMomentOfDay(PlatformUtils
			.parseString2Date("2014-04-22", "yyyy-MM-dd"));
	Date modifyTime = PlatformUtils
			.parseString2Date("2014-04-22", "yyyy-MM-dd");
	TfrCtrPersonnelActualEntity actual = new TfrCtrPersonnelActualEntity();
	TfrCtrPersonnelBudgetEntity budget = new TfrCtrPersonnelBudgetEntity();

	@Before
	public void before() {
		actual.setId(id);
		actual.setModifyTime(modifyTime);
		actual.setTransferCenterCode(transferCenterCode);
		actual.setEndEffectiveDate(endEffectiveDate);
		budget.setId(id);
		budget.setModifyTime(modifyTime);
		budget.setTransferCenterCode(transferCenterCode);
		budget.setEndEffectiveDate(endEffectiveDate);
	}

	@Test
	@Transactional
	public void insertActual() {
		dao.insertActual(actual);
	}

	@Test
	@Transactional
	public void insetBudget() {
		dao.insertBudget(budget);
	}

	@Test
	@Transactional
	public void updateActualEndEffectiveDate() {
		actual.setEndEffectiveDate(new Date());
		dao.updateActualEndEffectiveDate(actual);
	}

	@Test
	@Transactional
	public void updateBudgetEndEffectiveDate() {
		budget.setEndEffectiveDate(new Date());
		dao.updateBudgetEndEffectiveDate(budget);
	}

	@Test
	@Transactional
	public void updateActualPostNum() {
		dao.updateActualPostNum(actual);
	}

	@Test
	@Transactional
	public void updateBudget() {
		dao.updateBudget(budget);
	}

	@Test
	public void selectBudgetById() {
		TfrCtrPersonnelBudgetEntity result = dao.selectBudgetById(id);
		System.out.println(result);
	}

	@Test
	public void selectActualRelated() {
		System.out.println(dao.selectActualRelated(actual));
	}

	@Test
	public void selectBudgetRelated() {
		System.out.println(dao.selectBudgetRelated(budget));
	}

	@Test
	public void selectTfrCtrPersonnelBudgets() {
		TfrCtrPersonnelBudgetQcDto qcDto = new TfrCtrPersonnelBudgetQcDto();
		qcDto.setEffectiveDate(new Date());
//		qcDto.setMonth("2014-04");
		dao.selectTfrCtrPersonnelBudgets(qcDto);
	}
}
