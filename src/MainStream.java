import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStream {
    public static void main(String[] args) {
       int result =  minValue(new int[] {1,2,3,3,2,3});
        System.out.println(result);

        int result2 =  minValue(new int[] {9, 8});
        System.out.println(result2);

        List<Integer> integers = oddOrEven(Arrays.asList(1, 1, 2, 2, 3, 4, 5, 1));
        System.out.println(integers);
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, b) -> a * 10 + b );
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();
        Integer sum = integers.stream().reduce(0, (a,b) -> {
            if (isEven(b)) {
                evens.add(b);
            } else {
                odds.add(b);
            }
           return a + b;
        });
        if (isEven(sum)) return odds;
        else return evens;
    }

    public static boolean isEven(Integer number) {
        return number % 2 == 0;
    }
}
