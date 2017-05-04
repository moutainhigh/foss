/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/CODBatchServiceTest.java
 * 
 * FILE NAME        	: CODBatchServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.consumer.api.server.service.INoteDetailsService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;

/**
 * 
 * 代收货款批次号服务测试
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-29 下午5:38:50
 */
public class CODBatchServiceTest extends BaseTestCase {

	
	@Autowired
	private INoteDetailsService noteDetailsService;

	@Test
	@Rollback(false)
	public void testInsertOtherNos(){
		
		final NoteStockInDto noteStockInDto=new NoteStockInDto ();
		
		NoteStockInEntity entity=new NoteStockInEntity();
		List<NoteStockInEntity> stockInList=new ArrayList<NoteStockInEntity>();
		
		entity.setBeginNo(20003251);
		entity.setEndNo(20003300);
		stockInList.add(entity);
		
		noteStockInDto.setStockInList(stockInList);
		
/*		Runnable r = new Runnable() {
			
			@Override
			public void run() {*/
				System.out.println("1");
				try{
				noteDetailsService.createNoteDetails(noteStockInDto, ConsumerTestUtil.getCurrentInfo());
				System.out.println(".........");
				}catch(Exception ex){
					System.out.println("异常."+ex.getMessage());
				}
		/*	}
		};
		
		new Thread(r).start();
		new Thread(r).start();*/
		
	}
	
}
