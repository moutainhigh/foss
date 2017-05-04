package transfer.tracking.server.callback;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.esb.pojo.domain.dmp2foss.DMPSynTrackingToFOSSRequestEntity;
import com.deppon.esb.pojo.domain.dmp2foss.DMPSynTrackingToFOSSResponseEntity;
import com.deppon.esb.pojo.transformer.json.SynDMPTrackingRequestTrans;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.WayBillNoLocusConstant;
import com.deppon.foss.module.trackings.api.server.dao.ISynTrackingDao;
import com.deppon.foss.module.trackings.api.server.service.ISynDMPTrackingsServer;
import com.deppon.foss.module.trackings.api.server.service.ISynTrackingServer;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingsToWQSEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @author 362917
 *DMP大件家具同步数据_FOSS
 */
public class SyncTransDMPtrackingsProcessor implements IProcess{
	//；逻辑类接口
	private static final Logger logger = LoggerFactory.getLogger(SyncTransDMPtrackingsProcessor.class);
	@Autowired
	private ISynDMPTrackingsServer synDMPTrackingsServer;
	@Autowired
    private ISynTrackingServer synTrackingServer;
	@Autowired
	private ISynTrackingDao synTrackingDao;

	@Override
	public Object process(Object req) throws ESBBusinessException {
		logger.info("DMP大件家装轨迹同步到FOSS 开始。。。。。");
		DMPSynTrackingToFOSSResponseEntity response = new DMPSynTrackingToFOSSResponseEntity();
		DMPSynTrackingToFOSSRequestEntity request = (DMPSynTrackingToFOSSRequestEntity)req;
		response.setWayBillNo(request.getWayBillNo());
		response.setOperateTime(request.getOperateTime());
		response.setCurrentStatus(request.getCurrentStatus());
		response.setRespResult(FossConstants.YES);
		logger.info("回传参数"+response.getWayBillNo()+response.getCurrentStatus()+response.getOperateTime()+response.getRespResult());
		try {
			//将object强制转换为请求实体
			logger.info("传入参数："+new SynDMPTrackingRequestTrans().fromMessage(request));
			DMPSynTrackingsToWQSEntity trackingsEntity=new DMPSynTrackingsToWQSEntity();
			trackingsEntity.setId(UUIDUtils.getUUID());
			trackingsEntity.setWayBillNo(request.getWayBillNo());
			trackingsEntity.setCreateTime(request.getOperateTime());
			trackingsEntity.setEventType(request.getCurrentStatus()==null?null:request.getCurrentStatus());
			trackingsEntity.setOperateCity(request.getOperateCity()==null?null:request.getOperateCity());
			trackingsEntity.setOperateTime(request.getOperateTime());
			trackingsEntity.setOperateName(request.getOperateName()==null?null:request.getOperateName());
			trackingsEntity.setOrgCode(request.getOrgCode()==null?null:request.getOrgCode());
			trackingsEntity.setOrgName(request.getOrgName()==null?null:request.getOrgName());
			trackingsEntity.setSigner(request.getOperateName()==null?null:request.getOperateName());
			//trackInfo(长度500)，OperateTypeName(长度1000)，
			trackingsEntity.setTrackInfo(request.getOperateTypeName()==null?null:request.getOperateTypeName());
			
			DMPSynTrackingEntity synTrackingEntity=new DMPSynTrackingEntity();
			synTrackingEntity.setId(UUIDUtils.getUUID());
			synTrackingEntity.setWayBillNo(request.getWayBillNo());
			synTrackingEntity.setCurrentStatus(request.getCurrentStatus()==null?null:request.getCurrentStatus());
			synTrackingEntity.setNotes(request.getNotes()==null?null:request.getNotes());
			synTrackingEntity.setOperateName(request.getOperateName()==null?null:request.getOperateName());
			synTrackingEntity.setOperateNumber(request.getOperateNumber()==null?null:request.getOperateNumber());
			synTrackingEntity.setOperateOrgName(request.getOrgName()==null?null:request.getOrgName());
			synTrackingEntity.setOperateTime(request.getOperateTime());
			synTrackingEntity.setOperateTypeName(request.getOperateTypeName()==null?null:request.getOperateTypeName());

			//根据运单号查询WQS轨迹表的大件家装信息是否为空
			List<DMPSynTrackingsToWQSEntity> wqsSnyTrackingEntity=synDMPTrackingsServer.queryWQSTrackings(trackingsEntity);
			if(wqsSnyTrackingEntity.size()<1){
				synDMPTrackingsServer.addWQSTrackings(trackingsEntity);
			//如果数据库存在信息则对比二者运单号是否一致
			}else{
				for (DMPSynTrackingsToWQSEntity wqsSnyTrackingEntity2 : wqsSnyTrackingEntity) {
					if(request.getWayBillNo().equals(wqsSnyTrackingEntity2.getWayBillNo())&&request.getCurrentStatus().equals(wqsSnyTrackingEntity2.getEventType())){
						if(request.getOperateTime().after(wqsSnyTrackingEntity2.getOperateTime())){
							synDMPTrackingsServer.deleteWQSTrackings(wqsSnyTrackingEntity2.getWayBillNo(),wqsSnyTrackingEntity2.getEventType());
							synDMPTrackingsServer.addWQSTrackings(trackingsEntity);
						}else if(request.getOperateTime().equals(wqsSnyTrackingEntity2.getOperateTime())){
							continue;
						}
					}
				}
			}
			//根据运单号查询存储到FOSS表的大件家装信息是否为空
			List<DMPSynTrackingEntity> dmpSynTrackingEntity=synTrackingServer.queryDMPTrackings(synTrackingEntity);
			if(dmpSynTrackingEntity.size()<1){
				synTrackingServer.addDMPTrackings(synTrackingEntity);
			//如果数据库存在信息则对比二者运单号是否一致
			}else{
				for (DMPSynTrackingEntity dmpSynTrackingEntity2 : dmpSynTrackingEntity) {
					//二者WayBillNo一致说明数据库已存在改运单相关信息，需先全量删除才能全量推送修改的数据
					if(request.getWayBillNo().equals(dmpSynTrackingEntity2.getWayBillNo())&&request.getCurrentStatus().equals(dmpSynTrackingEntity2.getCurrentStatus())){
						if(request.getOperateTime().after(dmpSynTrackingEntity2.getOperateTime())){
							synTrackingDao.deleteDMPTrackings(dmpSynTrackingEntity2.getWayBillNo(),dmpSynTrackingEntity2.getCurrentStatus());
							synTrackingServer.addDMPTrackings(synTrackingEntity);
						}else if(request.getOperateTime().equals(dmpSynTrackingEntity2.getOperateTime())){
							continue;
						}
					}
				}
			}
			logger.info("DMP大件家装轨迹同步到FOSS success");
		} catch (Exception e) {
			response.setWayBillNo(request.getWayBillNo());
			response.setOperateTime(request.getOperateTime());
			response.setCurrentStatus(request.getCurrentStatus());
			response.setRespResult(FossConstants.NO);
			logger.info("回传参数"+response.getWayBillNo()+response.getCurrentStatus()+response.getOperateTime()+response.getRespResult());
			logger.error("DMP大件家装轨迹同步到FOSS失败："+e.getMessage());
		}
		return response;
		
	}
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		logger.info("DMP大件家装轨迹同步到FOSS失败");
		return null;
	}
	public void setSynDMPTrackingsServer(ISynDMPTrackingsServer synDMPTrackingsServer) {
		this.synDMPTrackingsServer = synDMPTrackingsServer;
	}
	public void setSynTrackingServer(ISynTrackingServer synTrackingServer) {
		this.synTrackingServer = synTrackingServer;
	}
	public void setSynTrackingDao(ISynTrackingDao synTrackingDao) {
		this.synTrackingDao = synTrackingDao;
	}

	


}
