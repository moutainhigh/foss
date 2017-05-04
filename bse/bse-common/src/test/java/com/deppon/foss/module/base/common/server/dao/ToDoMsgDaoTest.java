package com.deppon.foss.module.base.common.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganDao;
import com.deppon.foss.module.base.common.api.server.dao.IToDoMsgDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.common.server.util.SpringTestHelper;


public class ToDoMsgDaoTest {

    private JdbcTemplate jdbc;
    
    private IToDoMsgDao toDoMsgDao;
    private ToDoMsgEntity entity = new ToDoMsgEntity();

    /**
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午4:11:44
     * @throws java.lang.Exception
     * @see
     */
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	toDoMsgDao = SpringTestHelper.get().getBeanByInterface(IToDoMsgDao.class);
    }


    @After
    public void tearDown() throws Exception {
    }
    
    @Ignore
    @Test
    public void testSaveToDoMsg() {
	entity.setId("0001");
	entity.setTitle("测试1");
	entity.setReceiveOrgCode("000221");
	entity.setReceiveSubOrgCode("00022122");
	
	int resulte = toDoMsgDao.saveToDoMsg(entity);
	
	Assert.assertTrue(resulte > 0);
	
    }
    
    @Ignore
    @Test
    public void testDeleteToDoMsg() {
	testSaveToDoMsg();
	int resulte = toDoMsgDao.deleteToDoMsgOne(entity);
	
	Assert.assertTrue(resulte > 0);
    }

}
