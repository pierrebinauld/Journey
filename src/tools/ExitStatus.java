package tools;

public enum ExitStatus {
	OK(0),
	DB_ERROR(1337);

	private int code;

	private ExitStatus(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}
}
