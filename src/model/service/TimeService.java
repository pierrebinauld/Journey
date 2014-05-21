package model.service;

public class TimeService {

	private long start;

	public TimeService() {
		start = 0;
	}

	public void start() {
		start = System.nanoTime();
	}

	public long tick() {
		return System.nanoTime() - start;
	}

	public float tickInSecond() {
		return (float) tick() / 1000000000f;
	}
}
