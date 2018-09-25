package spittr.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spittr.Spitter;

@Repository 
public class JdbcSpitterRepository implements SpitterRepository {

	private JdbcOperations jdbcOperations;

	private class SpitterRowMapper implements RowMapper<Spitter> {

		@Override
		public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Spitter(
					rs.getLong("id"),
					rs.getString("firstName"),
					rs.getString("lastName"),
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("email")
					);
		}

	}

	@Inject
	public JdbcSpitterRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
	
	//Add Spitter
	private static final String INSERT_SPITTER = "insert into Spitters (username, password, firstname, lastname, email) values (?, ?, ?, ?, ?)";
	
	@Override
	public Spitter save(Spitter spitter) {
		jdbcOperations.update(INSERT_SPITTER,
				spitter.getUsername(),
				spitter.getPassword(),
				spitter.getFirstName(),
				spitter.getLastName(),
				spitter.getEmail()
				);
		return null;
	}

	//Get Spitter
	private static final String SELECT_SPITTER_BY_USERNAME = "select id, firstName, lastName, username, password, email from Spitters where username = ?";

	@Override
	public Spitter findByUsername(String username) {
		return jdbcOperations.queryForObject(SELECT_SPITTER_BY_USERNAME, new SpitterRowMapper(), username);
	}

}
