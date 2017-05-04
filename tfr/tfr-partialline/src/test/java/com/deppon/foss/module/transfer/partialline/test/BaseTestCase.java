package com.deppon.foss.module.transfer.partialline.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

@ContextConfiguration(locations = {
		 "classpath:com/deppon/foss/module/transfer/partialline/test/META-INF/spring.xml",
		 "classpath:com/deppon/foss/module/transfer/partialline/server/META-INF/spring.xml"
//	     "classpath:com/deppon/foss/module/transfer/common/server/META-INF/spring.xml",
//	     "classpath:com/deppon/foss/module/transfer/stock/server/META-INF/spring.xml",
//	     "classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml",
//	     "classpath:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
//	     "classpath:com/deppon/foss/module/base/dict/server/META-INF/spring.xml",
//	     "classpath:com/deppon/foss/module/pickup/pickup/server/META-INF/spring.xml"
	})
public abstract class BaseTestCase extends DaoDBUnitSupportUnitTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public BaseTestCase() {
    	
    }
}


