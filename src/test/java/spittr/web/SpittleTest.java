package spittr.web;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.Spittle;
import spittr.data.SpitterRepository;
import spittr.data.SpittleRepository;
import spittr.web.SpittleController;

public class SpittleTest {

	@Test
	public void shouldShowRecentSpittles() throws Exception {
		List<Spittle> expectedSpittles = createSpittleList(20);
		
		SpittleRepository mockSpittleRepository =
			mock(SpittleRepository.class);
		
		SpitterRepository mockSpitterRepository = 
			mock(SpitterRepository.class);
		
		when(mockSpittleRepository.findSpittles(Long.MAX_VALUE, 20))
		.thenReturn(expectedSpittles);
		
		SpittleController controller =
				new SpittleController(mockSpittleRepository, mockSpitterRepository);
		
		MockMvc mockMvc = standaloneSetup(controller)
				.setSingleView(
				new InternalResourceView("/WEB-INF/views/spittles.jsp"))
				.build();
		
		mockMvc.perform(get("/spittles"))
			.andExpect(view().name("spittles"))
			.andExpect(model().attributeExists("spittleList"))
			.andExpect(model().attribute("spittleList",
			hasItems(expectedSpittles.toArray())));
	}
	
	private List<Spittle> createSpittleList(int count) {
		List<Spittle> spittles = new ArrayList<Spittle>();
		for (int i=0; i < count; i++) {
		spittles.add(new Spittle("Spittle " + i));
		}
		return spittles;
	}
		
	@Test
	public void shouldShowPagedSpittles() throws Exception {
		List<Spittle> expectedSpittles = createSpittleList(50);
		
		SpittleRepository mockSpittleRepository =
				mock(SpittleRepository.class);
			
			SpitterRepository mockSpitterRepository = 
				mock(SpitterRepository.class);
			
		
		when(mockSpittleRepository.findSpittles(238900, 50)).thenReturn(expectedSpittles);
		
		SpittleController spittleController = new SpittleController(mockSpittleRepository, mockSpitterRepository);
		
		MockMvc mockMvc = standaloneSetup(spittleController)
				.setSingleView(
				new InternalResourceView("/WEB-INF/views/spittles.jsp"))
				.build();
		
		mockMvc.perform(get("/spittles?max=238900&count=50"))
			.andExpect(view().name("spittles"))
			.andExpect(model().attributeExists("spittleList"))
			.andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));

	}
	
	@Test
	public void testSpittle() throws Exception {
		Spittle expectedSpittle = new Spittle("Hello");
		
		SpittleRepository mockSpittleRepository =
				mock(SpittleRepository.class);
			
			SpitterRepository mockSpitterRepository = 
				mock(SpitterRepository.class);

		when(mockSpittleRepository.findOne(12345)).thenReturn(expectedSpittle);
		
		SpittleController controller = new SpittleController(mockSpittleRepository, mockSpitterRepository);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		

		mockMvc.perform(get("/spittles/12345"))
		.andExpect(view().name("spittle"))
		.andExpect(model().attributeExists("spittle"))
		.andExpect(model().attribute("spittle", expectedSpittle));

	}
	
	@Test
	public void testRegister() throws Exception {
		
		SpitterRepository mockRepository = mock(SpitterRepository.class);
		
		SpitterController controller = new SpitterController(mockRepository);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/spitter/register")).andExpect(view().name("registerForm"));
	}

}
