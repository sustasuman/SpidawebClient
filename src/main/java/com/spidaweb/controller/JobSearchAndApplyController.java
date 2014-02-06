/**
 * 
 */
package com.spidaweb.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spidaweb.job.Application;
import com.spidaweb.job.ApplicationResponse;
import com.spidaweb.service.SpidawebJobService;

/**
 * @author Suman
 * 
 */
@Controller
// @SessionAttributes
public class JobSearchAndApplyController {

	private static final Logger log = Logger
			.getLogger(JobSearchAndApplyController.class);

	public static final String APPLICATION_URL = "application";

	@Autowired
	SpidawebJobService spidawebJobService;

	@RequestMapping(value = "/application", method = RequestMethod.POST)
	public String applyJob(
			@Valid @ModelAttribute("application") Application application,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			return APPLICATION_URL;
		}

		String response = null;

		try {
			response = spidawebJobService.applyJob(application);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("errorMessage", e.getMessage());
		}

		if (null != response) {
			model.addAttribute("message", response);
		}

		return APPLICATION_URL;
	}

	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public String showJobs(Map<String, Object> map, Model mv) {
		try {
			mv.addAttribute("jobList", spidawebJobService.getJobs());
		} catch (Exception e) {
			mv.addAttribute("message", e.getMessage());
			log.error(e.getMessage(), e);
		}
		return "jobs";
	}

	@RequestMapping(value = "/applicationSelect", method = RequestMethod.GET)
	public String selectApplication(@RequestParam("id") String id, Model model) {
		Application application = new Application();
		model.addAttribute("application", application);
		return "application";

	}

	@RequestMapping(value = "/applicationStatus", method = RequestMethod.GET)
	public String showStatus(@RequestParam("id") String id,
			Map<String, Object> map, Model model) {

		ApplicationResponse response = null;
		if (StringUtils.isNotEmpty(id)) {
			try {
				response = spidawebJobService.getApplicationDetail(id);
				model.addAttribute("application", response);
				if (null == response) {
					model.addAttribute("errorMessage",
							"No result found for the application Id: " + id);
				}
			} catch (Exception e) {
				model.addAttribute("errorMessage", e.getMessage());
			}
		}

		return "applicationStatus";
	}
}
