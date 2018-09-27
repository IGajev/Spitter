package spittr.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import spittr.Spittle;
import spittr.data.JdbcSpittleRepository;

public class JdbcSpittleRepositoryTest {

	//Get Spittles from database
	 static final String SELECT_LAST_INSERTED_SPITTLE = 
			"select spittle_id, message, time, latitude, longitude from Spittles where spittle_id = (select max(spittle_id) from Spittles)";
	//Get Spittle from database
	private static final String SELECT_SPITTLE_BY_ID = "select spittle_id, message, time, latitude, longitude from Spittles where spittle_id = ?";

	List<Spittle> createSpittles(int count) {
		List<Spittle> spittles = new ArrayList<>();
		for ( int i = 1 ; i <= count ; i++ ) {
			Spittle spittle = new Spittle("spittle" + i);
			spittle.setId( Long.valueOf(i) );
			spittles.add(spittle);
		}
		return spittles;
	}
	

	@SuppressWarnings("unchecked")
	@Test
	public void TestFindZeroSpittles() {
		
		JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
		JdbcSpittleRepository jdbcSpittleRepository = new JdbcSpittleRepository(mockJdbcOperations);
		
		when( mockJdbcOperations.queryForObject(
				eq(SELECT_LAST_INSERTED_SPITTLE), 
				any(RowMapper.class)))
		.thenReturn( new Spittle("spittle1") );
		
		List<Spittle> spittleList = jdbcSpittleRepository.findSpittles(100, 0);
		assertEquals(spittleList,null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestFindOneSpittle() {
		
		JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
		JdbcSpittleRepository jdbcSpittleRepository = new JdbcSpittleRepository(mockJdbcOperations);
		int numberOfSpittles = 1;
			
		List<Spittle> spittles = createSpittles(numberOfSpittles);
		
		when( mockJdbcOperations.queryForObject(
				eq(SELECT_LAST_INSERTED_SPITTLE), 
				any(RowMapper.class)))
		.thenReturn( spittles.get(0));
		
		List<Spittle> spittleList = jdbcSpittleRepository.findSpittles(100, numberOfSpittles);
		assertEquals(spittleList.size(),numberOfSpittles);
		assertEquals(spittleList.get(0).getMessage(),"spittle1");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestFindTwoSpittles() {
		
		JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
		JdbcSpittleRepository jdbcSpittleRepository = new JdbcSpittleRepository(mockJdbcOperations);
		int numberOfSpittles = 2;
		
		List<Spittle> spittles = createSpittles(numberOfSpittles);
		

		when( mockJdbcOperations.queryForObject(
				eq(SELECT_LAST_INSERTED_SPITTLE), 
				any(RowMapper.class)))
		.thenReturn( spittles.get(numberOfSpittles - 1) );
		
		when( mockJdbcOperations.queryForObject(
				eq(SELECT_SPITTLE_BY_ID), 
				any(RowMapper.class),
				eq( (long)1 )))
		.thenReturn( spittles.get(0) );
		
		List<Spittle> spittleList = jdbcSpittleRepository.findSpittles(100, numberOfSpittles);
		assertEquals(spittleList.size(),numberOfSpittles);
		assertEquals(spittleList.get(0).getMessage(),"spittle2");
		assertEquals(spittleList.get(1).getMessage(),"spittle1");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestFind99Spittles() {
		
		JdbcOperations mockJdbcOperations = mock(JdbcOperations.class);
		JdbcSpittleRepository jdbcSpittleRepository = new JdbcSpittleRepository(mockJdbcOperations);
		int numberOfSpittles = 99;
		
		List<Spittle> spittles = createSpittles(numberOfSpittles);
		
		when( mockJdbcOperations.queryForObject(
				eq(SELECT_LAST_INSERTED_SPITTLE), 
				any(RowMapper.class)))
		.thenReturn( spittles.get(numberOfSpittles - 1) );
		
		for (int i = 0 ; i < numberOfSpittles -1 ; i ++ ) {
			when( mockJdbcOperations.queryForObject(
					eq(SELECT_SPITTLE_BY_ID), 
					any(RowMapper.class),
					eq( (long)i+1 )))
			.thenReturn( spittles.get(i) );
		}
		
		List<Spittle> spittleList = jdbcSpittleRepository.findSpittles(100, numberOfSpittles);
		assertEquals(spittleList.size(),numberOfSpittles);
		for (int i = 0 ; i < numberOfSpittles ; i ++ ) {
			int backwardSpittleNumber = numberOfSpittles - i;
			assertEquals(spittleList.get(i).getMessage(),"spittle" + backwardSpittleNumber );
		}

	}

}
