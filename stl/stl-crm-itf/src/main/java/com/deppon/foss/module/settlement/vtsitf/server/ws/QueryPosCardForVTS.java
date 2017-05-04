package com.deppon.foss.module.settlement.vtsitf.server.ws;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IQueryPosCardForVTS;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by yaq on 2016/5/11.
 */
public class QueryPosCardForVTS implements IQueryPosCardForVTS {
    /**
     * 日志
     */
    private final Logger logger = LogManager.getLogger(getClass());
    private IPdaPosManageService pdaPosManageService;
    @Context
    protected HttpServletRequest req;
    @Context
    protected HttpServletResponse resp;

    @Override
    @ResponseBody
    public String queryPosCardForVTS(@RequestBody String jsonObject) {
        resp.setHeader("ESB-ResultCode", "1");
        try {
            //JSONObject parseObject = JSONObject.parseObject(jsonObject);//repaymentEntity
            jsonObject = JSONObject.parseObject(jsonObject).getString("requestEntity");
            PosCardEntity entity = JSONObject.parseObject(jsonObject, PosCardEntity.class);
            if (entity == null || entity.getTradeSerialNo() == null || entity.getTradeSerialNo().isEmpty()) {
                logger.info("传入数据有误");
                return "";
            }
            PosCardManageDto dto = new PosCardManageDto();
            dto.setTradeSerialNo(entity.getTradeSerialNo());
            dto.setOrgCode(entity.getCardDeptCode());
            List<PosCardEntity> posCardEntitys = pdaPosManageService.queryPosCardData(dto).getPosCardEntitys();
            //是否存在
            if (posCardEntitys != null && posCardEntitys.size() > 0) {
                ResponseForVTS respons = new ResponseForVTS(true,"",posCardEntitys.get(0));
                return JSONObject.toJSONString(respons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  JSONObject.toJSONString(new ResponseForVTS(false,"查询不到相应数据",null));
    }

    public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
        this.pdaPosManageService = pdaPosManageService;
    }


class  ResponseForVTS{
        private boolean ifsuccess;
        private String  errorMsg;
        private PosCardEntity posCardEntity;

    public ResponseForVTS() {
    }

    public ResponseForVTS(boolean ifsuccess, String errorMsg, PosCardEntity posCardEntity) {
        this.ifsuccess = ifsuccess;
        this.errorMsg = errorMsg;
        this.posCardEntity = posCardEntity;
    }
    public boolean isIfsuccess() {
            return ifsuccess;
        }
        public void setIfsuccess(boolean ifsuccess) {
            this.ifsuccess = ifsuccess;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public PosCardEntity getPosCardEntity() {
            return posCardEntity;
        }
        public void setPosCardEntity(PosCardEntity posCardEntity) {
            this.posCardEntity = posCardEntity;
        }
    }
}
