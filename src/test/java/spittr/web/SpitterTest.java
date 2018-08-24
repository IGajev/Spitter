package spittr.web;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import spittr.Spitter;
import spittr.data.DummySpitterRepository;
import spittr.data.SpitterRepository;

public class SpitterTest {
	
	@Test
	public void shouldProcessRegistration() throws Exception {
		
		Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jbauer@email.com");
		
		Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@email.com");
		
		SpitterRepository mockRepository = mock(SpitterRepository.class);

		when(mockRepository.save(unsaved)).thenReturn(saved);

		SpitterController controller = new SpitterController(mockRepository);

		MockMvc mockMvc = standaloneSetup(controller).build();

		mockMvc.perform(post("/spitter/register")
		.param("firstName", "Jack")
		.param("lastName", "Bauer")
		.param("username", "jbauer")
		.param("password", "24hours")
		.param("email", "jbauer@email.com"))
		.andExpect(redirectedUrl("/spitter/jbauer"));
		
		verify(mockRepository, atLeastOnce()).save(unsaved);
	}

	  @Test
	  public void shouldProcessRegistrationDummyRepository() throws Exception {
	    SpitterRepository dummyRepository = new DummySpitterRepository();

	    SpitterController controller = new SpitterController(dummyRepository);
	    MockMvc mockMvc = standaloneSetup(controller).build();

	    mockMvc.perform(post("/spitter/register")
	           .param("firstName", "Jack")
	           .param("lastName", "Bauer")
	           .param("username", "jbauer")
	           .param("password", "24hours")
	           .param("email", "jbauer@email.com"))
	           .andExpect(redirectedUrl("/spitter/jbauer"));

		mockMvc.perform(get("/spitter/jbauer"))
		.andExpect(view().name("profile"))
		.andExpect(model().attributeExists("spitter"));	  
	  }

	  @Test
	  public void shouldProcessRegistrationFaultInjection() throws Exception {
	    SpitterRepository mockRepository = mock(SpitterRepository.class);

	    SpitterController controller = new SpitterController(mockRepository);
	    MockMvc mockMvc = standaloneSetup(controller).build();

	    mockMvc.perform(post("/spitter/register"))
	           .andExpect(view().name("registerForm"));

	  }

	  @Test
	  public void shouldFailValidationWithNoData() throws Exception {
	    SpitterRepository mockRepository = mock(SpitterRepository.class);    
	    SpitterController controller = new SpitterController(mockRepository);
	    MockMvc mockMvc = standaloneSetup(controller).build();
	    
	    mockMvc.perform(post("/spitter/register"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("registerForm"))
	        .andExpect(model().errorCount(5))
	        .andExpect(model().attributeHasFieldErrors(
	            "spitter", "firstName", "lastName", "username", "password", "email"));
	  }

}
