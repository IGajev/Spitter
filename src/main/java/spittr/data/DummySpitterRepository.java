package spittr.data;

import org.springframework.stereotype.Component;

import spittr.Spitter;

@Component
public class DummySpitterRepository implements SpitterRepository {
	
	public DummySpitterRepository () {
		
	}

	@Override
	public Spitter save(Spitter spitter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spitter findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
