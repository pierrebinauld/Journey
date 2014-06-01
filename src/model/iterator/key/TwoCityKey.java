package model.iterator.key;

public class TwoCityKey implements Key {

	public int index1;
	public int index2;

	public TwoCityKey(int index1, int index2) {
		super();
		this.index1 = index1;
		this.index2 = index2;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index1;
		result = prime * result + index2;
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
		if (index1 != other.index1)
			return false;
		if (index2 != other.index2)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TwoCityKey [index1=" + index1 + ", index2=" + index2 + "]";
	}

	
}
