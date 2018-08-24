package spittr.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import spittr.Spitter;

@Component
public class DummySpitterRepository implements SpitterRepository {
	
	private List<Spitter> spitters = new ArrayList<>();
	
	public DummySpitterRepository () {
		
	}

	@Override
	public Spitter save(Spitter spitter) {
		this.spitters.add(spitter);

		return spitter;
	}

	@Override
	public Spitter findByUsername(String username) {		
		for (Spitter spitter : spitters) {
			if ( spitter.getUsername().equals(username) ) {
				return spitter;
			}
		}
		return null;
	}

}
