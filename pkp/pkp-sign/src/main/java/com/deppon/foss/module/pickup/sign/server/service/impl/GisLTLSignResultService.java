package com.deppon.foss.module.pickup.sign.server.service.impl;



import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.inteface.domain.gis.HisSignDataRequest;
import com.deppon.esb.inteface.domain.gis.HisSignDataTeamRequest;
import com.deppon.foss.module.pickup.sign.api.server.service.IGisLTLSignResultService;
import com.deppon.foss.module.pickup.sign.server.dao.impl.WaybillSignResultDao;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* 	一、FOSS发送目的站匹配信息
     1、FOSS将已全部签收（包含正常签收、异常签收）运单的【运单号】、【收货人地址的省/市/区/四五级地址（分开传输）】、【运输性质】、【提货方式】、【目的站】传给GIS；
 	2、传输时间为：每日凌晨4点传给GIS。（与原有时间点保持一致）
  	3、FOSS系统进行运单识别：
 	①对于有【更改单-客户要求-变更类型：转货/返货】的运单进行剔除，不传给GIS；
 	②对于【运输性质】为【精准空运】、【汽运偏线】的运单进行剔除，不传给GIS；
 	③对于【提货方式】为【内部带货自提】的运单进行剔除，不传给GIS；*/


/**
 * Created by 306566 on 2017/3/2.
 */
public class GisLTLSignResultService implements IGisLTLSignResultService {

    //ESB客户端接口服务编码
    private static String FOSS_SEND_HISEXP_SIGN_INFO_TOGIS = "ESB_FOSS2ESB_SEND_HISEXP_TOGIS";

    //ESB接口版本号
    private static String VERSION = "1.0";
    private static int SEND_LIMIT = 10;

    //ESB接口描述
    private static String DESC = "把签收的目的站匹配信息传输到GIS的ESB接口";
    /**
     * 签收DAO
     */
    private WaybillSignResultDao waybillSignResultDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(GisLTLSignResultService.class);
    public void setWaybillSignResultDao(WaybillSignResultDao waybillSignResultDao) {
        this.waybillSignResultDao = waybillSignResultDao;
    }




    /***
     * Created by 306566 on 2017/3/2.
     * 把签收的目的站匹配信息传输到GIS
     * <p/>
     * 1、FOSS将已全部签收（包含正常签收、异常签收）运单的【运单号】、【收货人地址的省/市/区/四五级地址（分开传输）】、【运输性质】、【提货方式】、【目的站】传给GIS；
     * FOSS系统进行运单识别：
     * ①对于有【更改单-客户要求-变更类型：转货/返货】的运单进行剔除，不传给GIS；
     * ②对于【运输性质】为【精准空运】、【汽运偏线】的运单进行剔除，不传给GIS；
     * ③对于【提货方式】为【内部带货自提】的运单进行剔除，不传给GIS
     */
    public void sendGisLTLSignResultInfo() {

        int start=0; int limit=30;
        //为了防止数据量过大 进行分流分次推送

        //签收的目的站匹配信息
        List<HisSignDataRequest> hisSignDatalistFromDB;
        try{
            hisSignDatalistFromDB = this.queryLTLSignResultInfo();
            //新建一个实体用来存储签收的目的站匹配信息 进行分批传输
        }catch (Exception e) {
            LOGGER.error("查询FOSS签收的目的站匹配信息接口出错！" + e.getMessage(), e);
            throw new SettlementException("查询FOSS签收的目的站匹配信息出错！"
                    + e.getMessage());
        }
        try {
            if(hisSignDatalistFromDB != null && hisSignDatalistFromDB.size() > 0){
                List<HisSignDataRequest>  hisSignDataRequestList;
                for (int i=0; i<hisSignDatalistFromDB.size(); i+=SEND_LIMIT){
                    //新建一个实体用来存储签收的目的站匹配信息 进行分批传输
                   hisSignDataRequestList=new ArrayList<HisSignDataRequest>();

                    for (int j=i; j<+i+SEND_LIMIT && j<hisSignDatalistFromDB.size(); j+=1){
                        hisSignDataRequestList.add(hisSignDatalistFromDB.get(j));
                    }

                    //新建调用传输GIS接口需要的封装实体
                    HisSignDataTeamRequest hisSignDataTeamRequest = new HisSignDataTeamRequest();
                    //把每次限制分次传输的LIST全部赋值到hisSignDataTeamRequest中
                    hisSignDataTeamRequest.getHisSignDataList().addAll(hisSignDataRequestList);

                    try{
                        this.sendSignResultINfoToGIS(hisSignDataTeamRequest);
                    } catch (Exception e) {
                        LOGGER.error("把签收的相关信息传输到GIS出错。调用ESB接口出错！" + e.getMessage(), e);
                        continue;
                    }
                    System.out.println("第"+(i/10+1)+"循环次调用." +"传输的list实体长度为: "+hisSignDataRequestList.size());
                }
            }
        } catch (Exception e) {
            LOGGER.error("把签收的相关信息传输到GIS出错。调用ESB接口出错！" + e.getMessage(), e);
            throw new SettlementException("把签收的相关信息传输到GIS出错。调用ESB接口出错！"
                    + e.getMessage());
        }
    }

    /**
     * 调用异步接口同步到GIS
     * @param
     */
    public void sendSignResultINfoToGIS(HisSignDataTeamRequest hisSignDataTeamRequest){

        LOGGER.info("把签收的目的站匹配信息传输到GIS开始！");
        //判空操作
        if (hisSignDataTeamRequest==null){
            LOGGER.info("把签收的目的站匹配信息传输到GIS:封装是的实体:hisSignDataTeamRequest 为空");
            return;
        }
        if(CollectionUtils.isEmpty(hisSignDataTeamRequest.getHisSignDataList())){
            LOGGER.info("把签收的目的站匹配信息传输到GIS:查询不到GIS需要的签收数据");
            return;
        }

        //设置ESB请求头
        AccessHeader header = buildHeader();
        // 发送付款信息到ESB
        try {
            LOGGER.info("签收的目的站匹配信息传输到GIS: " + ReflectionToStringBuilder.toString(hisSignDataTeamRequest));
            ESBJMSAccessor.asynReqeust(header,hisSignDataTeamRequest);
        } catch (ESBException e) {
            LOGGER.error("把签收的目的站匹配信息传输到GIS出错。调用ESB接口出错！" + e.getMessage(), e);
            throw new SettlementException("把签收的目的站匹配信息传输到GIS出错。调用ESB接口出错！"
                    + e.getMessage());
        }
        LOGGER.info("把签收的目的站匹配信息传输到GIS！");
    }

    /**
     * Created by 306566 on 2017/3/2.
     * 根据条件查询签收接结果目的站匹配信息
     */
    public  List<HisSignDataRequest> queryLTLSignResultInfo() {

        List<HisSignDataRequest> hisSignDataList;
        try{
            //查询运单签结果里的运单信息用于传给GIS
            hisSignDataList = waybillSignResultDao.queryLTLSigfnResultInfoDuringAllYesterday();
        } catch (Exception e) {
            LOGGER.error("根据条件查询签收接结果目的站匹配信息出错！" + e.getMessage(), e);
            throw new SettlementException("根据条件查询签收接结果的目的站匹配信息接口出错！"
                    + e.getMessage());
        }
        return hisSignDataList;
    }




    /**
     * 设置ESB请求头
     * @author foss-Jiang Xun
     * @date 2016-05-23 上午10:01:00
     */
    private AccessHeader buildHeader() {
        AccessHeader header = new AccessHeader();
        //服务编码
        header.setEsbServiceCode(FOSS_SEND_HISEXP_SIGN_INFO_TOGIS);
        header.setVersion(VERSION);
        //接口描述
        header.setBusinessDesc1(DESC);
        header.setBusinessDesc2(DESC);
        header.setBusinessDesc3(DESC);
        //businessId
        header.setBusinessId(new Date().toString());
        return header;
    }

}
