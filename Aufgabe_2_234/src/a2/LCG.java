package a2;

public class LCG {
	public LCG(long Seed) {
		seed = Seed;
		reset();
	}

	// ---- Konstanten
	private final long seed;
	private final int Mrd = 1000000000;
	private final long a = (long) (Math.E * Mrd);
	private final long b = (long) (Math.PI * Mrd);
	private final long N = 1 << 30;

	// ---- Var(s)
	private double X_i;

	public void reset() {
		X_i = seed;
	}

	public long nextValue() {
		X_i = (a * X_i + b) % N;
		return Math.round(X_i / N);
	}

	public byte nextByte() {
		int ret = 0;

		for (int i = 0; i < 8; i++) {
			ret = ret << 1;
			ret += nextValue();
		}

		return (byte) ret;
	}

	public static void main(String[] args) {
		LCG l = new LCG(3333);
		for (int i = 0; i < 10; i++)
			System.out.println(l.nextByte());
		// for (int i = 0; i < 500000000; i++)
		// l.nextValue();
		// for (int i = 0; i < 50; i++)
		// System.out.print(l.nextValue());
	}

}
