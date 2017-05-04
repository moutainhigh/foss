
package com.deppon.foss.shared.request;

import java.util.ArrayList;
import java.util.List;


/**
 * 查询详细运单信息集合
 * 
 */
public class QueryDetailRequest {

    protected List<String> waybillNo;

    /**
     * Gets the value of the waybillNo property.
     * 
     */
    public List<String> getWaybillNo() {
        if (waybillNo == null) {
            waybillNo = new ArrayList<String>();
        }
        return this.waybillNo;
    }

}
