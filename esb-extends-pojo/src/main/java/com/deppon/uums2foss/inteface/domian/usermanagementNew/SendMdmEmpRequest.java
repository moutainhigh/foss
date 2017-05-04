
package com.deppon.uums2foss.inteface.domian.usermanagementNew;

import java.util.ArrayList;
import java.util.List;

/** 
 * UUMS������ҵ��ϵͳ����Ա��Ϣ����UUMS����ͳһά����˾Ա����ݣ�
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.deppon.com/uums/inteface/domain/usermanagement" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SendMdmEmpRequest">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:MdmEmpInfo" name="employeeInfo" minOccurs="1" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SendMdmEmpRequest
{
    private List<MdmEmpInfo> employeeInfoList = new ArrayList<MdmEmpInfo>();

    /** 
     * Get the list of 'employeeInfo' element items. ��Ա��Ϣ
     * 
     * @return list
     */
    public List<MdmEmpInfo> getEmployeeInfoList() {
        return employeeInfoList;
    }

    /** 
     * Set the list of 'employeeInfo' element items. ��Ա��Ϣ
     * 
     * @param list
     */
    public void setEmployeeInfoList(List<MdmEmpInfo> list) {
        employeeInfoList = list;
    }
}
