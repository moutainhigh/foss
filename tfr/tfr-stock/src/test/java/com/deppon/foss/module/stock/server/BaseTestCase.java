package com.deppon.foss.module.stock.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

@ContextConfiguration(locations = {
		 "classpath:com/deppon/foss/module/transfer/stock/server/spring-test.xml"/*,
		 "classpath:com/deppon/foss/module/transfer/stock/server/META-INF/spring.xml"*/
	})
public abstract class BaseTestCase extends DaoDBUnitSupportUnitTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public BaseTestCase() {
    	
    }
}


