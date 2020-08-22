package android.example.toys;

final class Stopwatch {
	private long t;

	Stopwatch(){
		t = System.currentTimeMillis();
	}

	void start(){
		t = System.currentTimeMillis();
	}

	long restart(){
    	final long oldT = t;
		t = System.currentTimeMillis();
    	return t - oldT;
    }
}