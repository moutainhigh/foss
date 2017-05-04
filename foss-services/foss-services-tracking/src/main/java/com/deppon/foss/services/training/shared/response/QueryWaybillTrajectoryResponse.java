
package com.deppon.foss.services.training.shared.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.services.training.shared.vo.WaybillTrajectoryInfo;

public class QueryWaybillTrajectoryResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<WaybillTrajectoryInfo> waybillTrajectoryInfo;

    public List<WaybillTrajectoryInfo> getWaybillTrajectoryInfo() {
        if (waybillTrajectoryInfo == null) {
            waybillTrajectoryInfo = new ArrayList<WaybillTrajectoryInfo>();
        }
        return this.waybillTrajectoryInfo;
    }

}
