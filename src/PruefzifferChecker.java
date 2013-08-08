import java.util.ArrayList;

public class PruefzifferChecker implements Runnable {

	private int startIndex, stopIndex;
	private String[] accounts;
	private ArrayList<String> results;

	public PruefzifferChecker(int startIndex, int stopIndex, String[] accounts,
			ArrayList<String> results) {
		this.startIndex = startIndex;
		this.stopIndex = stopIndex;
		this.accounts = accounts;
		this.results = results;
	}

	@Override
	public void run() {
		for (int i = startIndex; i <= stopIndex; i++) {
			recordMatch(fillWithZeros(i));
		}
		System.out.println(Thread.currentThread().getName() + " done!");
	}

	private void recordMatch(String gewichtung) {
		if (gewichtung.matches(".*000000")) {
			System.out.println(Thread.currentThread().getName() + " Checking: "
					+ gewichtung);
		}
		boolean success = true;
		for (int i = 0; i < accounts.length; i++) {
			int summe = 0;
			for (int j = 0; j < gewichtung.length(); j++) {
				int first = Integer.parseInt(gewichtung.substring(j, j + 1));
				int second = Integer.parseInt(accounts[i].substring(j, j + 1));
				summe += first * second;
			}
			int pruefziffer = summe % 11;
			if (pruefziffer != Integer.parseInt(accounts[i]
					.substring(accounts[i].length() - 1))) {
				success = false;
				break;
			}
		}
		if (success) {
			synchronized (results) {
				results.add(gewichtung);
			}
		}

	}

	private String fillWithZeros(int i) {
		String result = "" + i;
		while (result.length() < ((Integer) stopIndex).toString().length()) {
			result = "0" + result;
		}
		return result;
	}

}
