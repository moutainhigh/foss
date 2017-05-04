/**
 * Copyright 2013 STL TEAM
 */
package com.deppon.foss.module.settlement.closing.test.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto; 
import com.deppon.foss.module.settlement.closing.test.BaseTestCase;

public class MvrRfdEntityServiceTest extends BaseTestCase {

	
	/** 专线到达Service. */
	@Autowired
	private IMvrRfdEntityService mvrRfdEntityService;
	
	@Test
	@Rollback(true)
	public void selectByConditionsTest(){
		
		MvrRfdDto dto = new MvrRfdDto();
		dto.setPeriod("201302");
		List<MvrRfdEntity> list = mvrRfdEntityService.selectByConditions(dto, 0, 10);
		MvrRfdDto totalDto = mvrRfdEntityService.selectTotalByConditions(dto);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>0);
		Assert.assertNotNull(totalDto);
	}
			
}
