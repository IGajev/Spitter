package spittr;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Spittle {

	public Spittle() {}

	private Long id;
	private String message;
	private Date time;
	private Double latitude;
	private Double longitude;
	
	public Spittle(String message) {
		this(message, null, null);
	}
	
	public Spittle(String message, Double longitude, Double latitude) {
		this.id = null;
		this.message = message;
		this.time = new Date();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Spittle(Long id, String message, Date time, Double longitude, Double latitude) {
		this.id = id;
		this.message = message;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public Date getTime() {
		return time;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "id", "time");
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "time");
	}
}
