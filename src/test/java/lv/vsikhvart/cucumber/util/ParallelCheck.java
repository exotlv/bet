package lv.vsikhvart.cucumber.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelCheck {

    public static void parallelCheck(Runnable... runnables) throws AssertionError {
        parallelCheck("Asserts failed", runnables);
    }

    public static void parallelCheck(String message, Runnable... runnables) throws AssertionError {
        List<Throwable> throwables = Collections.synchronizedList(new ArrayList<>());
        Stream.of(runnables)
                .parallel()
                .map(r -> CompletableFuture.runAsync(r, Executors.newFixedThreadPool(runnables.length)))
                .forEach(tr -> {
                            try {
                                tr.get();
                            } catch (InterruptedException e) {
                                e.getMessage();
                            } catch (ExecutionException e) {
                                throwables.add(e);
                            }
                        }
                );
        assertAll(message, throwables);
    }

    private static void assertAll(String msg, List<Throwable> throwables) {
        if (!throwables.isEmpty()) {
            String s = throwables.stream()
                    .map(Throwable::getMessage)
                    .collect(Collectors.joining(";\n", msg + ":\n", ";"));
            throw new AssertionError(s);
        }
    }
}
