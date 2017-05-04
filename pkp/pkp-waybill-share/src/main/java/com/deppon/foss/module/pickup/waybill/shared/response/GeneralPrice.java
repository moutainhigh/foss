package com.deppon.foss.module.pickup.waybill.shared.response;

import java.math.BigDecimal;

/**
 * 
 * @author 305082
 *
 */
public class GeneralPrice {

    protected BigDecimal heavyRate;
    protected BigDecimal receiveHeavyRate;
    protected BigDecimal lightRate;
    protected BigDecimal receiveLightRate;
    protected BigDecimal lowestPrice;
    protected BigDecimal receiveLowestPrice;
    protected String originatingArea;
    protected String destinationArea;

    /**
     * Gets the value of the heavyRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHeavyRate() {
        return heavyRate;
    }

    /**
     * Sets the value of the heavyRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHeavyRate(BigDecimal value) {
        this.heavyRate = value;
    }

    /**
     * Gets the value of the receiveHeavyRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReceiveHeavyRate() {
        return receiveHeavyRate;
    }

    /**
     * Sets the value of the receiveHeavyRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReceiveHeavyRate(BigDecimal value) {
        this.receiveHeavyRate = value;
    }

    /**
     * Gets the value of the lightRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLightRate() {
        return lightRate;
    }

    /**
     * Sets the value of the lightRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLightRate(BigDecimal value) {
        this.lightRate = value;
    }

    /**
     * Gets the value of the receiveLightRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReceiveLightRate() {
        return receiveLightRate;
    }

    /**
     * Sets the value of the receiveLightRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReceiveLightRate(BigDecimal value) {
        this.receiveLightRate = value;
    }

    /**
     * Gets the value of the lowestPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    /**
     * Sets the value of the lowestPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLowestPrice(BigDecimal value) {
        this.lowestPrice = value;
    }

    /**
     * Gets the value of the receiveLowestPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReceiveLowestPrice() {
        return receiveLowestPrice;
    }

    /**
     * Sets the value of the receiveLowestPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReceiveLowestPrice(BigDecimal value) {
        this.receiveLowestPrice = value;
    }

    /**
     * Gets the value of the originatingArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginatingArea() {
        return originatingArea;
    }

    /**
     * Sets the value of the originatingArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginatingArea(String value) {
        this.originatingArea = value;
    }

    /**
     * Gets the value of the destinationArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationArea() {
        return destinationArea;
    }

    /**
     * Sets the value of the destinationArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationArea(String value) {
        this.destinationArea = value;
    }

}
