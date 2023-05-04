package bank.system.webservicecall;

import feign.RetryableException;
import feign.Retryer;

public class FeignClientRetry implements Retryer{

	private final int maxAttempts=10;
	// sleep time in milliseconds
    private final long sleepTime=1000;
    int attempt;

    public void continueOrPropagate(RetryableException e) {
    	System.out.println("Retry call attempt :"+attempt);
        if (attempt++ >= maxAttempts) {
            throw e;
        }

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new FeignClientRetry();
    }

}
