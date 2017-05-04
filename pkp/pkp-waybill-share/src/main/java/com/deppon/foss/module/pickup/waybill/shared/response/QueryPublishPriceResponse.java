package com.deppon.foss.module.pickup.waybill.shared.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询价格时效的response
 * @author 305082
 *
 */
public class QueryPublishPriceResponse {

    protected List<PublishPriceInfo> publishPriceInfos;

    /**
     * Gets the value of the publishPriceInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the publishPriceInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublishPriceInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PublishPriceInfo }
     * 
     * 
     */
    public List<PublishPriceInfo> getPublishPriceInfos() {
        if (publishPriceInfos == null) {
            publishPriceInfos = new ArrayList<PublishPriceInfo>();
        }
        return this.publishPriceInfos;
    }

}
