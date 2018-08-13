package spittr.data;

import spittr.Spitter;

public interface SpitterRepository {

	public Spitter save(Spitter spitter);

	public Spitter findByUsername(String username);

}
