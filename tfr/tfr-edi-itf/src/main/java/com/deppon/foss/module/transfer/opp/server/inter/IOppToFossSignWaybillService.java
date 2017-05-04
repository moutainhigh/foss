package com.deppon.foss.module.transfer.opp.server.inter;

import com.deppon.foss.esb.edi.server.air.SignInfoSendRequest;
import com.deppon.foss.esb.edi.server.air.SignInfoSendResponse;


/**
 * Created by 306566 on 2016/05/13.
 */

public interface IOppToFossSignWaybillService {

    SignInfoSendResponse sendSignInfo(SignInfoSendRequest payload);
}
