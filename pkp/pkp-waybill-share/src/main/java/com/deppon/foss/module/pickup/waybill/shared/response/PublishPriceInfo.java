package com.deppon.foss.module.pickup.waybill.shared.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 305082
 *
 */
public class PublishPriceInfo {

    protected String productCode;
    protected String productName;
    protected List<LttRate> lttRates;
    protected ProductAging speed;
    protected GeneralPrice generateRate;
    protected ExpressPrice expressRate;

    /**
     * Gets the value of the productCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the value of the productCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the lttRates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lttRates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLttRates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LttRate }
     * 
     * 
     */
    public List<LttRate> getLttRates() {
        if (lttRates == null) {
            lttRates = new ArrayList<LttRate>();
        }
        return this.lttRates;
    }

    /**
     * Gets the value of the speed property.
     * 
     * @return
     *     possible object is
     *     {@link ProductAging }
     *     
     */
    public ProductAging getSpeed() {
        return speed;
    }

    /**
     * Sets the value of the speed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductAging }
     *     
     */
    public void setSpeed(ProductAging value) {
        this.speed = value;
    }

    /**
     * Gets the value of the generateRate property.
     * 
     * @return
     *     possible object is
     *     {@link GeneralPrice }
     *     
     */
    public GeneralPrice getGenerateRate() {
        return generateRate;
    }

    /**
     * Sets the value of the generateRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneralPrice }
     *     
     */
    public void setGenerateRate(GeneralPrice value) {
        this.generateRate = value;
    }

    /**
     * Gets the value of the expressRate property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressPrice }
     *     
     */
    public ExpressPrice getExpressRate() {
        return expressRate;
    }

    /**
     * Sets the value of the expressRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressPrice }
     *     
     */
    public void setExpressRate(ExpressPrice value) {
        this.expressRate = value;
    }

}
