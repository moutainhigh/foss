package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.server.service.IPdaAddressCollectionService;
import com.deppon.foss.module.pickup.order.api.shared.dto.GpsAddressCollectDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.GpsAddressCollectEntity;



public class CollectGpsService implements IBusinessService<Void, GpsAddressCollectEntity>{
    private static final Log LOG = LogFactory.getLog(GpsAddressCollectEntity.class);

    private IAcctDao acctDao;

    private IPdaAddressCollectionService pdaAddressCollectionService;
    
    public void setAcctDao(IAcctDao acctDao) {
        this.acctDao = acctDao;
    }
   


    public void setPdaAddressCollectionService(IPdaAddressCollectionService pdaAddressCollectionService) {
        this.pdaAddressCollectionService = pdaAddressCollectionService;
    }



    @Override
    public GpsAddressCollectEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
      //解析包体
        GpsAddressCollectEntity gpsAddressCollect = JsonUtil.parseJsonToObject(GpsAddressCollectEntity.class, asyncMsg.getContent());
       
        //pda编号
        gpsAddressCollect.setPdaCode(asyncMsg.getPdaCode());
        //部门编号
        gpsAddressCollect.setDeptCode(asyncMsg.getDeptCode());      
        //用户编号
        gpsAddressCollect.setScanUser(asyncMsg.getUserCode());      
        //扫描类型
        gpsAddressCollect.setScanType(asyncMsg.getOperType());    
        //同步时间
        gpsAddressCollect.setUploadTime(asyncMsg.getUploadTime());
        //gpsAddressCollect.setUploadTime(new Date());
     
        return gpsAddressCollect;
    }

    @Transactional
    @Override
    public Void service(AsyncMsg asyncMsg, GpsAddressCollectEntity gpsAddressCollectEntity) throws PdaBusiException {
        LOG.info(gpsAddressCollectEntity);
        //声明FOSS定义的参数DTO
        GpsAddressCollectDto gpsAddressCollect = new GpsAddressCollectDto();
        
       try{  
           //校验参数
            this.validate(asyncMsg,gpsAddressCollectEntity);
            //实例化参数DTO
            gpsAddressCollect = this.wrapPdaDeliverSignDto(asyncMsg,gpsAddressCollectEntity);
            
            //保存扫描数据
            acctDao.saveGpsScanMessage(gpsAddressCollectEntity);
            
            long startTime = System.currentTimeMillis();
            //调用地址采集接口
            pdaAddressCollectionService.fossAddressCollection(gpsAddressCollect);          
            long endTime = System.currentTimeMillis();
            QueueMonitorInfo.addTotalFossTime(endTime-startTime);
            LOG.info("[asyncinfo]地址采集接口消耗时间:"+(endTime-startTime)+"ms");  
            
            //保存采集地址
            acctDao.saveGpsMessage(gpsAddressCollectEntity);
        } catch (BusinessException e) {
            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
        }
        
        return null;
    }

    /**
     * 拼装实体
     * @param asyncMsg
     * @param excpSignByPcScan
     * @return
     */
    private GpsAddressCollectDto  wrapPdaDeliverSignDto(AsyncMsg asyncMsg, GpsAddressCollectEntity gpsAddressCollectEntity){
        GpsAddressCollectDto gpsAddressCollectDto = new GpsAddressCollectDto();
        //采集地址
        gpsAddressCollectDto.setAddressType(gpsAddressCollectEntity.getAddressType());
        //订单号或运单号
        gpsAddressCollectDto.setBillNo(gpsAddressCollectEntity.getBillNo());
        //采集经度
        gpsAddressCollectDto.setGpsLongitude(gpsAddressCollectEntity.getGpsLongitude());  
        //采集纬度
        gpsAddressCollectDto.setGpsLatitude(gpsAddressCollectEntity.getGpsLatitude());
        //采集时间
        gpsAddressCollectDto.setCollectTime(gpsAddressCollectEntity.getCollectTime()); 
        //采集人工号
        gpsAddressCollectDto.setDriverCode(gpsAddressCollectEntity.getScanUser());
        //采集人部门
        gpsAddressCollectDto.setDriverDept(gpsAddressCollectEntity.getDeptCode());
        
        return gpsAddressCollectDto;
    }
      
        
        
        
    private void validate(AsyncMsg asyncMsg, GpsAddressCollectEntity gpsAddressCollectEntity){
        Argument.notNull(asyncMsg, "AsyncMsg");
        //pda编号
        Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
        //部门编号
        Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
        //用户编号
        Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
        //操作类型
        Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
       
        // 包体信息校验
        Argument.notNull(gpsAddressCollectEntity, "GpsAddressCollectEntity");
      
            
        //订单号/运单号
        Argument.hasText(gpsAddressCollectEntity.getBillNo(), "GpsAddressCollectEntity.billNo");
        //采集地址
        Argument.hasText(gpsAddressCollectEntity.getAddressType(), "GpsAddressCollectEntity.addressType");
        //经度
        Argument.hasText(gpsAddressCollectEntity.getGpsLongitude(), "GpsAddressCollectEntity.gpsLongitude");
        //纬度
        Argument.hasText(gpsAddressCollectEntity.getGpsLatitude(), "GpsAddressCollectEntity.gpsLatitude");
        //采集时间
        Argument.notNull(gpsAddressCollectEntity.getCollectTime(), "GpsAddressCollectEntity.collectTime");
       
            
         
                
    }
 
    
    
    @Override
    public String getOperType() {
        return AcceptConstant.OPER_TYPE_ACCT_COLLECT_GPS.VERSION;
       
    }

    @Override
    public boolean isAsync() {
        return true;
    }


}
