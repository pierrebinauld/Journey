package model.service;

public class TimeService {

	private long start;
	private long time;

	public TimeService() {
		start = 0;
		time = 0;
	}

	public void start() {
		start = System.nanoTime();
	}

	public long tick() {
		time = System.nanoTime() - start;

		return time;
	}

	public float tickInSecond() {
		return (float) tick() / 1000000000f;
	}
}
