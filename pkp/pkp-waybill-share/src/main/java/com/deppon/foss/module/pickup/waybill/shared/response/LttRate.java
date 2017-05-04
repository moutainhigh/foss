package com.deppon.foss.module.pickup.waybill.shared.response;

import java.math.BigDecimal;


/**
 * 
 * @author 305082
 *
 */
public class LttRate {

    protected String sectionId;
    protected BigDecimal weightStart;
    protected BigDecimal weightEnd;
    protected BigDecimal volumeStart;
    protected BigDecimal volumeEnd;
    protected GeneralPrice generateRate;

    /**
     * Gets the value of the sectionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * Sets the value of the sectionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectionId(String value) {
        this.sectionId = value;
    }

    /**
     * Gets the value of the weightStart property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWeightStart() {
        return weightStart;
    }

    /**
     * Sets the value of the weightStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWeightStart(BigDecimal value) {
        this.weightStart = value;
    }

    /**
     * Gets the value of the weightEnd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWeightEnd() {
        return weightEnd;
    }

    /**
     * Sets the value of the weightEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWeightEnd(BigDecimal value) {
        this.weightEnd = value;
    }

    /**
     * Gets the value of the volumeStart property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolumeStart() {
        return volumeStart;
    }

    /**
     * Sets the value of the volumeStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolumeStart(BigDecimal value) {
        this.volumeStart = value;
    }

    /**
     * Gets the value of the volumeEnd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolumeEnd() {
        return volumeEnd;
    }

    /**
     * Sets the value of the volumeEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolumeEnd(BigDecimal value) {
        this.volumeEnd = value;
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

}
