package spittr.data;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import spittr.Spitter;
import spittr.Spittle;

@Repository 
public class JdbcSpittleRepository implements SpittleRepository {

	private JdbcOperations jdbcOperations;

	@Inject
	public JdbcSpittleRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
	
	//Get Spittles from database
	private static final String SELECT_LAST_INSERTED_SPITTLE = 
			"select spittle_id, message, time, latitude, longitude from Spittles where spittle_id = (select max(spittle_id) from Spittles)";
	
	@Override
	public List<Spittle> findSpittles(long max, int count) {
		
		List<Spittle> resultSpittles = new ArrayList<>();
		
		if ( count == 0 ) {
			return null;
		}
		
		resultSpittles.add(jdbcOperations.queryForObject(
				SELECT_LAST_INSERTED_SPITTLE, 
				//This lambda expression returns a new Spittle based on the interface RowMapper<T>
				//which has to implement the method "T mapRow(ResultSet rs, int rowNum) throws SQLException;"
				(rs, rowNum) ->  { 
					return new Spittle(
							rs.getLong("spittle_id"),
							rs.getString("message"),
							rs.getTime("time"),
							rs.getDouble("latitude"),
							rs.getDouble("longitude")); 
				}));
		
		if( resultSpittles.get(0) == null ) {
			return null;
		} else {
			long max_spittle_id = resultSpittles.get(0).getId().longValue();
			while ( --max_spittle_id > 0 ) {
				if ( resultSpittles.size() == count ) {
					break;
				}
				resultSpittles.add(jdbcOperations.queryForObject(
						SELECT_SPITTLE_BY_ID, 
						//This lambda expression returns a new Spittle based on the interface RowMapper<T>
						//which has to implement the method "T mapRow(ResultSet rs, int rowNum) throws SQLException;"
						(rs, rowNum) ->  { 
							return new Spittle(
									rs.getLong("spittle_id"),
									rs.getString("message"),
									rs.getTime("time"),
									rs.getDouble("latitude"),
									rs.getDouble("longitude")); 
						},
						max_spittle_id ));
			}
		}
		
		 return resultSpittles;
	}

	//Get Spittle from database
	private static final String SELECT_SPITTLE_BY_ID = "select spittle_id, message, time, latitude, longitude from Spittles where spittle_id = ?";
	
	@Override
	public Spittle findOne(long id) {
		return jdbcOperations.queryForObject(
				SELECT_SPITTLE_BY_ID, 
				//This lambda expression returns a new Spittle based on the interface RowMapper<T>
				//which has to implement the method "T mapRow(ResultSet rs, int rowNum) throws SQLException;"
				(rs, rowNum) ->  { 
					return new Spittle(
							rs.getLong("spittle_id"),
							rs.getString("message"),
							rs.getTime("time"),
							rs.getDouble("latitude"),
							rs.getDouble("longitude")); 
				},
				id );
	}

	//Add Spittle to database
	private static final String INSERT_SPITTLE = "insert into spittles (message, id, time, latitude, longitude) values (?,?,?,?,?)";
	
	@Override
	public void addSpittle(Spittle spittle, Spitter spitter) {
		jdbcOperations.update(
				INSERT_SPITTLE, 
				spittle.getMessage(),
				spitter.getId(),
				spittle.getTime(),
				spittle.getLatitude(),
				spittle.getLongitude());
	}

}
