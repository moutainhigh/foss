package com.deppon.foss.module.transfer.stockchecking.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

@ContextConfiguration(locations = {
		 "classpath:com/deppon/foss/module/transfer/stockchecking/test/META-INF/spring.xml",
		 "classpath:com/deppon/foss/module/transfer/stockchecking/server/META-INF/spring.xml",
	     "classpath:com/deppon/foss/module/transfer/common/server/META-INF/spring.xml",
	     "classpath:com/deppon/foss/module/transfer/stock/server/META-INF/spring.xml",
	     "classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml",
	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/lineBean.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/platformBean.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfoComplex.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfo.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/financialOrganizations.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/outfield.xml",
//	     "classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/motorcade.xml",
	     "classpath:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
	     "classpath:com/deppon/foss/module/base/dict/server/META-INF/spring.xml",
	     "classpath:com/deppon/foss/module/pickup/pickup/server/META-INF/spring.xml"
	})
public abstract class BaseTestCase extends DaoDBUnitSupportUnitTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public BaseTestCase() {
    	
    }
}


