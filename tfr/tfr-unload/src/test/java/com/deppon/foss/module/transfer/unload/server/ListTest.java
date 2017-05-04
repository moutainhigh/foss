/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server
 * FILE    NAME: Test.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;

/**
 * @author dp-duyi
 * @date 2013-3-23 下午4:53:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/unload/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class ListTest {
	@Test
	public void test(){
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0;i<51;i++){
			l.add(i);
			System.out.println(i);
		}
		int start = 0;
		int end;
		if(start+UnloadConstants.BATCH_COUNT<l.size()){
			end = start+UnloadConstants.BATCH_COUNT;
		}else{
			end = l.size();
		}
		while(start < l.size()){
			 System.out.println(l.subList(start, end));
			start = end;
			if(start+UnloadConstants.BATCH_COUNT<l.size()){
				end = start+UnloadConstants.BATCH_COUNT;
			}else{
				end = l.size();
			}
		}
	}
}
