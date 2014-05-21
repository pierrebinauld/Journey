package model.iterator.landscape.keys;

public class TwoCityKey {

	private int cityIndex1;
	private int cityIndex2;

	public TwoCityKey(int cityIndex1, int cityIndex2) {
		super();
		this.cityIndex1 = cityIndex1;
		this.cityIndex2 = cityIndex2;
	}

	public int getCityIndex1() {
		return cityIndex1;
	}

	public void setCityIndex1(int cityIndex1) {
		this.cityIndex1 = cityIndex1;
	}

	public int getCityIndex2() {
		return cityIndex2;
	}

	public void setCityIndex2(int cityIndex2) {
		this.cityIndex2 = cityIndex2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cityIndex1;
		result = prime * result + cityIndex2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwoCityKey other = (TwoCityKey) obj;
		if (cityIndex1 != other.cityIndex1)
			return false;
		if (cityIndex2 != other.cityIndex2)
			return false;
		return true;
	}
	
	
}
