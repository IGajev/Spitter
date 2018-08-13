package spittr;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Spitter {

	@NotNull
	@Size(min=2, max=30)
	private String firstName;
	
	@NotNull
	@Size(min=2, max=30)
	private String lastName;
	
	@NotNull
	@Size(min=5, max=16)
	private String username;
	
	@NotNull
	@Size(min=5, max=25)
	private String password;
	
	private Long id;
	
	public Spitter() {}
	  
	public Spitter(String firstName, String lastName, String username, String password) {
		this(null, firstName, lastName, username, password);
	}
	
	public Spitter(Long id, String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	  @Override
	  public boolean equals(Object that) {
	    return EqualsBuilder.reflectionEquals(this, that, "firstName", "lastName", "username", "password");
	  }
	  
	@Override
	  public int hashCode() {
	    return HashCodeBuilder.reflectionHashCode(this, "firstName", "lastName", "username", "password");
	  }

	
}
