package com.bean;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class TemplateResponseBean  {

//    @XStreamAsAttribute
//	@XStreamAlias(value="service")
	private String service;
	private String File;
	private String Source;
	private String Description;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getFile() {
		return File;
	}

	public void setFile(String file) {
		File = file;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
