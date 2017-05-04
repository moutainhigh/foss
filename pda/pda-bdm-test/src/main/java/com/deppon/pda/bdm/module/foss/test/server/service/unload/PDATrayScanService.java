package com.deppon.pda.bdm.module.foss.test.server.service.unload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryTrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressTransferScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDATrayOfflineScanDto;

public class PDATrayScanService implements IPDATrayScanService {

	@Override
	public String createTrayScanTask(PDATrayScanTaskEntity fossEntity) {
		StringBuffer buffer=new StringBuffer();
		System.out.println("-----------提交托盘扫描绑定任务------------------");
		buffer.append(fossEntity.getBindingCode()).append("\r\n");
		buffer.append(fossEntity.getTaskNo()).append("\r\n");
		buffer.append(fossEntity.getBindingDept()).append("\r\n");
		buffer.append(fossEntity.getTraytaskCreatTime()).append("\r\n");
		buffer.append(fossEntity.getTrayScanDetails().toArray()).append("\r\n");
		System.out.println(buffer.toString());
		System.out.println("-----------提交托盘扫描绑定任务------------------");
		return null;
	}

	@Override
	public PDATrayScanTaskEntity queryTrayScanDetail(
			QueryTrayScanTaskEntity querytask) {
		StringBuffer buffer=new StringBuffer();
		System.out.println("-----------下拉叉车司机人去请求参数------------------");
		buffer.append(querytask.getWaybillNo()).append("\r\n");
		buffer.append(querytask.getSerialNo()).append("\r\n");
		buffer.append(querytask.getOrgCode()).append("\r\n");
		
		PDATrayScanTaskEntity scanEntity=new PDATrayScanTaskEntity();
		List<PDATrayScanDetailEntity> trayScanDetails=new ArrayList<PDATrayScanDetailEntity>();
		for(int i=0;i<10;i++){
			PDATrayScanDetailEntity en=new PDATrayScanDetailEntity();
			en.setDestDeptName("北京外场营业部");
			en.setSerialNo("001"+i);
			en.setWaybillNo("18512458");
			en.setGoodAreaCode("AAAAA");//库区编码
			en.setAdminiRegions("上海");//行政区域
			if(i%3==1){
				en.setWaybillInfo("3");	
			}
			else
			if(i%3==2){
				en.setWaybillInfo("2");	
			}
			else
			if(i%3==3){
				en.setWaybillInfo("3");	
			}
			else{
				en.setWaybillInfo("0");	
			}
			trayScanDetails.add(en);
		}
		
		for(int i=0;i<8;i++){
			PDATrayScanDetailEntity en=new PDATrayScanDetailEntity();
			en.setDestDeptName("上海营业部");
			en.setSerialNo("001"+i);
			en.setWaybillNo("15201245");
			en.setGoodAreaCode("AAAAA");//库区编码
			en.setAdminiRegions("上海");//行政区域
			if(i%3==1){
				en.setWaybillInfo("1");	
			}
			else
			if(i%3==2){
				en.setWaybillInfo("1");	
			}
			else
			if(i%3==3){
				en.setWaybillInfo("1");	
			}
			else{
				en.setWaybillInfo("1");	
			}
			trayScanDetails.add(en);
		}
		for(int i=0;i<5;i++){
			PDATrayScanDetailEntity en=new PDATrayScanDetailEntity();
			en.setDestDeptName("广州营业部");
			en.setSerialNo("001"+i);
			en.setWaybillNo("52012458");
			en.setGoodAreaCode("AAAAA");//库区编码
			en.setAdminiRegions("上海");//行政区域
			if(i%3==1){
				en.setWaybillInfo("2");	
			}
			else
			if(i%3==2){
				en.setWaybillInfo("2");	
			}
			else
			if(i%3==3){
				en.setWaybillInfo("2");	
			}
			else{
				en.setWaybillInfo("2");	
			}
			trayScanDetails.add(en);
		}
		
		scanEntity.setTaskNo("11101");
		scanEntity.setTrayScanDetails(trayScanDetails);
		System.out.println("-----------Foss 返回下拉任务明细------------------");
		return scanEntity;
	}

	@Override
	public String updateTrayScanTask(PDATrayScanTaskEntity trayScanTask) {
		StringBuffer buffer=new StringBuffer();
		System.out.println("-----------下拉叉车司机人去请求参数------------------");
		buffer.append(trayScanTask.getForkliftDriverCode()).append("\r\n");
		buffer.append(trayScanTask.getTaskNo()).append("\r\n");
		buffer.append(trayScanTask.getForkliftDept()).append("\r\n");
		buffer.append(trayScanTask.getTrayscanTime()).append("\r\n");
		return null;
	}

    @Override
    public String createTrayOfflineScanTask(PDATrayOfflineScanDto arg0) {
        // TODO Auto-generated method stub
        return null;
    }

/*    @Override
    public List<PDATrayScanTaskEntity> queryTraybindforkLiftTicks(String arg0, String arg1, Date arg2) {
        // TODO Auto-generated method stub
        return null;
    }*/

    @Override
    public List<PDATrayScanTaskEntity> queryTraybindforkLiftTicks(String ceaterCode, String createrOrgCode,
            Date currentTime, int hours) {
        List<PDATrayScanTaskEntity> pdaTrayScanTaskEntitys = new ArrayList<PDATrayScanTaskEntity>();
        PDATrayScanTaskEntity st = new PDATrayScanTaskEntity();
        st.setTaskNo("tast-0001");
        st.setForkliftVotes(10);
        st.setTrayscanTime(new Date());
        pdaTrayScanTaskEntitys.add(st);
        
        st = new PDATrayScanTaskEntity();
        st.setTaskNo("tast-0002");
        st.setForkliftVotes(11);
        st.setTrayscanTime(new Date());
        pdaTrayScanTaskEntitys.add(st);
        
        st = new PDATrayScanTaskEntity();
        st.setTaskNo("tast-0003");
        st.setForkliftVotes(12);
        st.setTrayscanTime(new Date());
        pdaTrayScanTaskEntitys.add(st);
        
        
        // TODO Auto-generated method stub
        return pdaTrayScanTaskEntitys;
    }

    @Override
    public List<PDATrayOfflineScanDto> queryTrayOfflineScanWaybillQty(String ceaterCode, String createrOrgCode,
            Date currentTime, int hours) {
        List<PDATrayOfflineScanDto> pdaTrayScanTaskEntitys = new ArrayList<PDATrayOfflineScanDto>();
        PDATrayOfflineScanDto st = new PDATrayOfflineScanDto();
        st.setOfflineTaskNo("tast-0001");
        st.setWaybillQty(10);
        st.setTrayOfflineScanTime(new Date());
        pdaTrayScanTaskEntitys.add(st);
        
        st = new PDATrayOfflineScanDto();
        st.setOfflineTaskNo("tast-0002");
        st.setWaybillQty(12);
        st.setTrayOfflineScanTime(new Date());
        pdaTrayScanTaskEntitys.add(st);
        
        st = new PDATrayOfflineScanDto();
        st.setOfflineTaskNo("tast-0003");
        st.setWaybillQty(14);
        st.setTrayOfflineScanTime(new Date());
        pdaTrayScanTaskEntitys.add(st);
        
        
        // TODO Auto-generated method stub
        return pdaTrayScanTaskEntitys;
    }

	@Override
	public String queryGoodsAreaForPdaTrayScan(ExpressTransferScanDto arg0) {
		// TODO Auto-generated method stub
		return "dddddddddddd";
	}
	
	
}
