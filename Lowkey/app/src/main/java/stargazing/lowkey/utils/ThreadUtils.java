package stargazing.lowkey.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ThreadUtils {

    public static boolean runInBackground(Callable<Boolean> callable) {
        return runInBackground(callable, false);
    }

    private static <T> T runInBackground(Callable<T> callable, T defaultValue) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<T> futureTask = new FutureTask<>(callable);
        try {
            executor.submit(futureTask);
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            return defaultValue;
        } finally {
            executor.shutdown();
        }
    }

}
