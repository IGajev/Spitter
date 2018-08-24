package spittr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import spittr.Spittle;

@Component
public class DummySpittleRepository implements SpittleRepository {
	
	private List<Spittle> spittles = new ArrayList<>();
	
	public DummySpittleRepository () {
		this.spittles.add(new Spittle("Hello World! The first ever Spittle!", new Date()));
		this.spittles.add(new Spittle("Here's another spittle", new Date()));
		this.spittles.add(new Spittle("Spittle spittle spittle", new Date()));
		this.spittles.add(new Spittle("Spittles go fourth!", new Date()));
	}

	@Override
	public List<Spittle> findSpittles(long max, int count) {
		return spittles;
	}

	@Override
	public Spittle findOne(long i) {
		return spittles.get( (int) i );
	}

}
