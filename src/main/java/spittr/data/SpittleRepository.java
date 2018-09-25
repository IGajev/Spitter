package spittr.data;

import java.util.List;

import spittr.Spitter;
import spittr.Spittle;

public interface SpittleRepository {
	List<Spittle> findSpittles(long max, int count);

	Spittle findOne(long i);
	void addSpittle(Spittle spittle, Spitter spitter);

}
