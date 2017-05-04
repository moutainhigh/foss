package com.deppon.foss.module.transfer.platform.server;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:com/deppon/foss/module/transfer/platform/server/spring-test.xml",
		"classpath:com/deppon/foss/module/transfer/platform/server/test/spring.xml"
		})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public abstract class BaseTestCase extends DaoDBUnitSupportUnitTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public BaseTestCase() {
    	
    }
}