import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PruefZiffer {

	static ArrayList<String> _results = new ArrayList<String>();
	static ArrayList<String> _gewichtungen = new ArrayList<String>();
	static Integer _gewichtungMax = 999999999;
	static String[] _accounts = { "0060002687", "0060003577", "0060013776",
			"0060012657", "0060002821", "0060009432", "0060002799",
			"0060011978", "0060004233", "0060004386", "0060013829",
			"0060013738", "0060007767", "0060004957", "0060013254" };
	private static int chunkSize = 111111111;

	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(10);

		for (int i = 0; i < _gewichtungMax; i += chunkSize) {
			Runnable worker = new PruefzifferChecker(i, i + chunkSize,
					_accounts, _results);
			exec.execute(worker);
		}
		exec.shutdown();

		while (!exec.isTerminated()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < _results.size(); i++) {
			System.out.println("REEESULT: " + _results.get(i));
		}

	}

}
