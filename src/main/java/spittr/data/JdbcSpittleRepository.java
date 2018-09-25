package spittr.data;

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
	
	@Override
	public List<Spittle> findSpittles(long max, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	//Get Spittle from database
	private static final String SELECT_SPITTLE_BY_USERNAME = "select spittle_id, message, time, latitude, longitude from Spittles where spittle_id = ?";
	
	@Override
	public Spittle findOne(long id) {
		return jdbcOperations.queryForObject(
				SELECT_SPITTLE_BY_USERNAME, 
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
