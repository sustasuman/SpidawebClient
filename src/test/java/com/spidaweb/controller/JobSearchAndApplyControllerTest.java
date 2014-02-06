/**
 * 
 */
package com.spidaweb.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.spidaweb.exception.SpidawebServiceException;
import com.spidaweb.job.Application;
import com.spidaweb.job.ApplicationResponse;
import com.spidaweb.job.Job;
import com.spidaweb.service.SpidawebJobService;

/**
 * @author Suman
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-servlet.xml" })
public class JobSearchAndApplyControllerTest {

	@InjectMocks
	JobSearchAndApplyController jobController;

	MockMvc mockMvc;

	@Mock
	SpidawebJobService spidawebJobService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		this.mockMvc = MockMvcBuilders.standaloneSetup(jobController)
				.setViewResolvers(viewResolver).build();

	}

	@Test
	public void applyJobValidationTest() throws Exception {
		Application app = new Application();
		app.setName("SomeName");
		mockMvc.perform(post("/application").param("name", "Sample Name"))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("application"));
		verify(spidawebJobService, times(0)).applyJob(any(Application.class));

		mockMvc.perform(
				post("/application").param("name", "Sample Name").param("code",
						"http://google.com")).andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("application"));
		verify(spidawebJobService, times(0)).applyJob(any(Application.class));

		mockMvc.perform(
				post("/application").param("name", "Sample Name")
						.param("code", "google")
						.param("justification", "justification")
						.param("jobId", "jobId")).andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("application"));
		verify(spidawebJobService, times(0)).applyJob(any(Application.class));

	}

	@Test
	public void applyJobServiceTest() throws Exception {

		when(spidawebJobService.applyJob(any(Application.class))).thenReturn(
				"ApplicationId");
		mockMvc.perform(
				post("/application").param("name", "Sample Name")
						.param("code", "http://google.com")
						.param("justification", "justification")
						.param("jobId", "jobId")).andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("application"))
				.andExpect(model().attributeExists("message"));
		verify(spidawebJobService, times(1)).applyJob(any(Application.class));

		doThrow(new SpidawebServiceException("Error")).when(spidawebJobService)
				.applyJob(any(Application.class));

		mockMvc.perform(
				post("/application").param("name", "Sample Name")
						.param("code", "http://google.com")
						.param("justification", "justification")
						.param("jobId", "jobId")).andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("application"))
				.andExpect(model().attributeExists("errorMessage"));
		verify(spidawebJobService, times(2)).applyJob(any(Application.class));

	}

	@Test
	public void selectApplicationTest() throws Exception {

		mockMvc.perform(get("/applicationSelect").param("id", "Sample ID"))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("application"))
				.andExpect(model().attributeExists("application"));

	}

	@Test
	public void showJobsTest() throws Exception {

		List<Job> joblist = new LinkedList<Job>();
		when(spidawebJobService.getJobs()).thenReturn(joblist);

		mockMvc.perform(get("/jobs")).andExpect(model().hasNoErrors())
				.andExpect(view().name("jobs"))
				.andExpect(model().attributeExists("jobList"));

		verify(spidawebJobService, times(1)).getJobs();

		doThrow(new SpidawebServiceException("Error")).when(spidawebJobService)
				.getJobs();

		mockMvc.perform(get("/jobs")).andExpect(model().hasNoErrors())
				.andExpect(view().name("jobs")).andExpect(status().isOk())
				.andExpect(model().attributeExists("message"));

		verify(spidawebJobService, times(2)).getJobs();

	}

	@Test
	public void showStatusSuccessTest() throws Exception {

		when(spidawebJobService.getApplicationDetail(anyString())).thenReturn(
				new ApplicationResponse());

		mockMvc.perform(get("/applicationStatus").param("id", "Sample ID"))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("applicationStatus"))
				.andExpect(model().attributeExists("application"));

		verify(spidawebJobService, times(1)).getApplicationDetail(anyString());

	}

	@Test
	public void showStatusFailTest() throws Exception {

		when(spidawebJobService.getApplicationDetail(anyString())).thenReturn(
				null);

		mockMvc.perform(get("/applicationStatus").param("id", "Sample ID"))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("applicationStatus"))
				.andExpect(model().attributeExists("errorMessage"));

		verify(spidawebJobService, times(1)).getApplicationDetail(anyString());

		doThrow(new SpidawebServiceException("Error")).when(spidawebJobService)
				.getApplicationDetail(anyString());

		mockMvc.perform(get("/applicationStatus").param("id", "Sample ID"))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("applicationStatus"))
				.andExpect(model().attributeExists("errorMessage"));
		verify(spidawebJobService, times(2)).getApplicationDetail(anyString());
	}

}
