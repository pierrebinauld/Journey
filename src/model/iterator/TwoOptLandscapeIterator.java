package model.iterator;

import model.iterator.key.TwoCityKey;
import model.lookup.Circuit;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TwoOptLandscapeIterator implements Iterator<TwoCityKey> {

	public int cityIndex1 = 0;
	public int cityIndex2 = 0;
	
	private int citiesSize;
	private Circuit circuit;
	List<Integer> distances;

	public TwoOptLandscapeIterator(Circuit circuit) {
		super();

		setCircuit(circuit);
	}

//	@Override
	public void reset() {
		cityIndex1 = 0;
		cityIndex2 = 2;
		citiesSize = circuit.getCities().size();
	}

//	@Override
	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
		this.distances = circuit.getDistances();
		reset();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNext() {
		
		return !(cityIndex1 == citiesSize - 3 && cityIndex2 == citiesSize - 1 || null == circuit);
	}

	@Override
	public TwoCityKey next() {

		if (cityIndex1 == citiesSize - 3 && cityIndex2 == citiesSize - 1 || null == circuit) {
			throw new NoSuchElementException();
		} else if (cityIndex2 == citiesSize - 1) {
			cityIndex1++;
			cityIndex2 = cityIndex1 + 2;
		} else {
			cityIndex2++;

			if (0 == cityIndex1 && cityIndex2 == citiesSize - 1) {
				return next();
			}
		}

		return new TwoCityKey(cityIndex1, cityIndex2);
	}
}
