package com.deppon.foss.module.transfer.lostwarning.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.deppon.foss.module.transfer.lostwarning.api.server.dao.ILostWarningDataDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.LostWarningDataEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.WayBillSerialInfoEntity;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningTempDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillPkgInfoDto;
import com.deppon.foss.module.transfer.lostwarning.server.service.impl.FossToMcewService;
import com.deppon.foss.module.transfer.lostwarning.server.service.impl.LostWarningAnalyData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/lostwarning/server/spring-test.xml" })
public class UploadLostWarningTest {
	
	final static String urlStr = "http://10.224.71.132:8081/qms/ws/lostgoods/QMS_DHYJ_FOSS_AsyncWaybill";
	
	@Autowired
	ILostWarningDataDao lostWarningDataDao;
	
	@Autowired
	LostWarningAnalyData lostWarningAnalyData ;
	
	@BeforeTransaction
	public void beforeTransaction() {
	}
	
	public void testSendInfo(){
		List<LostWarningLogDto> warningLogList = lostWarningDataDao.queryUploadLogSuccByWayBill("678606066");
		String str = warningLogList.get(0).getUploadMsg();
		System.out.println(new Date());
		System.out.println(str);
		System.out.println(FossToMcewService.getInstatce().reportWarningData(str));
		
	}
	
	public void testBatchAdd(){
		List<LostWarningLogDto> list=new ArrayList<LostWarningLogDto>();
		LostWarningLogDto bean = new LostWarningLogDto();
		bean.setWayBillNo("456474848");
		bean.setSerialNo("111");
		bean.setRepType("1");
		bean.setRepScene("1");
		bean.setRepCode("1");
		bean.setIsFind("0");
		list.add(bean);
		String infoType="6";
		lostWarningDataDao.insertAndDelWarningData(list, infoType);
	}
	
	public void testSearchLog(){
		List<LostWarningLogDto> warningLogList = lostWarningDataDao.queryUploadLogSuccByWayBill("312071145");
    	List<WayBillSerialInfoEntity> serialList = new ArrayList<WayBillSerialInfoEntity>();
    	WayBillSerialInfoEntity bean = new WayBillSerialInfoEntity();
    	bean.setFlowCode("0003");
    	serialList.add(bean);
    	for(int i=0;i<serialList.size();i++){
    		WayBillSerialInfoEntity serialEntity = serialList.get(i);
    		for(LostWarningLogDto logDto:warningLogList){
    			if(StringUtils.equals(serialEntity.getFlowCode(),logDto.getSerialNo())&&
    					StringUtils.equals(serialEntity.getRespDeptCode(),logDto.getRespDeptCode())
    					){
    				//已上报成功，排除该流水信息
    				serialList.remove(i);
    				i--;
    				break;
    			}
    		}
    	}
    	
		System.out.println(serialList.size());
	}
	
	public void testGetCount(){
		System.out.println(lostWarningDataDao.searchLostData("11111"));
	}
	
	public void testAddInfo(){
		System.out.println(new Date());
		lostWarningDataDao.synStartDptLostData();
		lostWarningDataDao.synJZReceiveLostData();
		lostWarningDataDao.synTransferLostData();
		lostWarningDataDao.synAlreadyArriveLostData();
		lostWarningDataDao.synTransferStoreLostData();
		lostWarningDataDao.synHandoverLostData();
		lostWarningDataDao.synDifferStockLostData();
		lostWarningDataDao.synDeliverLostData();
		lostWarningDataDao.synAirTransferLostData();
		lostWarningDataDao.synExpressExternalLostData();
		System.out.println(new Date());
	}
	
	
	public void test(){
		LostWarningTempDto bean = new LostWarningTempDto();
		System.out.println("start......."+new Date());
		bean.setWayBillNo("234365757");
		LostWarningDataEntity data = lostWarningAnalyData.analyWarningData_startDtpStock(bean);
		 List<LostWarningDataEntity> list = new ArrayList<LostWarningDataEntity>();
		 list.add(data);
		 String msg = JSONArray.fromObject(list).toString();
		 System.out.println(msg);
		// sendInfo(msg);
		System.out.println("end....."+new Date());
	}
	
	@Test
	public void testSearchWayBillPkgList(){
		List<String> strList = new ArrayList<String>();
		strList.add("0002");
		strList.add("0003");
		List<WayBillPkgInfoDto> list = lostWarningDataDao.queryWayBillPkgBySerialList("9909021302",strList);
		System.out.println(list.size());
	}
	
}
