
package com.deppon.uums2foss.inteface.domian.usermanagementNew;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.deppon.com/uums/inteface/domain/usermanagement" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SendMdmOrgProcessReult">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="orgChangeId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:boolean" name="result" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="reason" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgBenchmarkCode" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SendMdmOrgProcessReult
{
    private String orgChangeId;
    private boolean result;
    private String reason;
    private String orgBenchmarkCode;

    /** 
     * Get the 'orgChangeId' element value. 组织变动ID
     * 
     * @return value
     */
    public String getOrgChangeId() {
        return orgChangeId;
    }

    /** 
     * Set the 'orgChangeId' element value. 组织变动ID
     * 
     * @param orgChangeId
     */
    public void setOrgChangeId(String orgChangeId) {
        this.orgChangeId = orgChangeId;
    }

    /** 
     * Get the 'result' element value. 处理结果，是否成功 [0: 失败 ,1：成功]
     * 
     * @return value
     */
    public boolean isResult() {
        return result;
    }

    /** 
     * Set the 'result' element value. 处理结果，是否成功 [0: 失败 ,1：成功]
     * 
     * @param result
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /** 
     * Get the 'reason' element value. 失败原因
     * 
     * @return value
     */
    public String getReason() {
        return reason;
    }

    /** 
     * Set the 'reason' element value. 失败原因
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /** 
     * Get the 'orgBenchmarkCode' element value. 组织标杆编码
     * 
     * @return value
     */
    public String getOrgBenchmarkCode() {
        return orgBenchmarkCode;
    }

    /** 
     * Set the 'orgBenchmarkCode' element value. 组织标杆编码
     * 
     * @param orgBenchmarkCode
     */
    public void setOrgBenchmarkCode(String orgBenchmarkCode) {
        this.orgBenchmarkCode = orgBenchmarkCode;
    }
}
