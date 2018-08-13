package spittr.data;

import java.util.List;

import org.springframework.stereotype.Component;

import spittr.Spittle;

@Component
public class DummySpittleRepository implements SpittleRepository {
	
	public DummySpittleRepository () {
		
	}

	@Override
	public List<Spittle> findSpittles(long max, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spittle findOne(long i) {
		// TODO Auto-generated method stub
		return null;
	}

}
