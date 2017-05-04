package com.deppon.foss.module.transfer.edi.server.domain;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OppLocusEntity;

/**
 * 
* @description OPP出发到达轨迹到达推送到FOSS
* @version 1.0
* @author 269701-foss-lln
* @update 2016年6月1日 下午6:19:17
 */
public class OPPLocusRequest {
	//轨迹list
private List<OppLocusEntity> oppLocus=new ArrayList<OppLocusEntity>();

/**
 * @return oppLocus : return the property oppLocus.
 * @author 269701-foss-lln
 * @update 2016年6月1日 下午6:21:15
 * @version V1.0
 */
public List<OppLocusEntity> getOppLocus() {
	return oppLocus;
}

/**
 * @param oppLocus : set the property oppLocus.
 * @author 269701-foss-lln
 * @update 2016年6月1日 下午6:21:15
 * @version V1.0
 */

public void setOppLocus(List<OppLocusEntity> oppLocus) {
	this.oppLocus = oppLocus;
}
}
