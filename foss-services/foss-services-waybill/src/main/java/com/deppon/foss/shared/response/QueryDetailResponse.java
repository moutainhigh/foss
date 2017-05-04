
package com.deppon.foss.shared.response;

import java.util.ArrayList;
import java.util.List;


public class QueryDetailResponse {

    protected List<WayBillDetail> wayBillDetailList;

    public List<WayBillDetail> getWayBillDetailList() {
        if (wayBillDetailList == null) {
            wayBillDetailList = new ArrayList<WayBillDetail>();
        }
        return this.wayBillDetailList;
    }

}
