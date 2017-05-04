package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SortdestStationDataDictionaryValueDto")
public class SortdestStationDataDictionaryValueDto {
	   
	    private String termsCode;
	    
	    private String termsName;
	    
	    private String valueName;
	    
	    private String valueCode;
	    
	    private String value;
	    
		public String getTermsCode() {
			return termsCode;
		}
		public void setTermsCode(String termsCode) {
			this.termsCode = termsCode;
		}
		public String getTermsName() {
			return termsName;
		}
		public void setTermsName(String termsName) {
			this.termsName = termsName;
		}
		public String getValueName() {
			return valueName;
		}
		public void setValueName(String valueName) {
			this.valueName = valueName;
		}
		public String getValueCode() {
			return valueCode;
		}
		public void setValueCode(String valueCode) {
			this.valueCode = valueCode;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	    
		
	
}
