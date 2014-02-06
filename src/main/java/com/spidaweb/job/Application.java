package com.spidaweb.job;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class Application {
	@NotEmpty
	private String name;

	@NotNull
	private String jobId;
	@NotEmpty
	private String justification;
	@URL
	@NotEmpty
	private String code;
	private ArrayList<String> additionalLinks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArrayList<String> getAdditionalLinks() {
		return this.additionalLinks;

	}

	public void setAdditionalLinks(ArrayList<String> additionalLinks) {
		this.additionalLinks = additionalLinks;
	}

}
