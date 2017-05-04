package com.deppon.foss.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

@ContextConfiguration( 
        locations = {"classpath:com/deppon/foss/module/base/querying/server/META-INF/springTest.xml"})

public abstract class BaseTestCase extends DaoDBUnitSupportUnitTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 
     */
   
    public BaseTestCase() {
    	
    }
    
}