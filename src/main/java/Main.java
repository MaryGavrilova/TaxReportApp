import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static void main(String[] args) {
        int[] shopReport1 = generateArray(30, 10, 1);
        int[] shopReport2 = generateArray(10, 30, 1);
        int[] shopReport3 = generateArray(20, 5, 1);

        LongAdder taxOfficeDatabase = new LongAdder();
        ExecutorService poolShops = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Arrays.stream(shopReport1)
                .forEach(i -> poolShops.submit(() -> taxOfficeDatabase.add(i)));
        Arrays.stream(shopReport2)
                .forEach(i -> poolShops.submit(() -> taxOfficeDatabase.add(i)));
        Arrays.stream(shopReport3)
                .forEach(i -> poolShops.submit(() -> taxOfficeDatabase.add(i)));

        try {
            poolShops.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nРезультат по всем магазинам: " + taxOfficeDatabase.sum());
        poolShops.shutdown();
    }

    public static int[] generateArray(int numberOfArrayElements, int upperBorder, int lowerBorder) {
        int[] array = new int[numberOfArrayElements];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * (upperBorder - lowerBorder) + lowerBorder);
        }
        return array;
    }
}
