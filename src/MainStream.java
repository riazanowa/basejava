import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        int result = minValue(new int[]{1, 2, 3, 3, 2, 3});
        System.out.println(result);

        int result2 = minValue(new int[]{9, 8});
        System.out.println(result2);

        List<Integer> integers = oddOrEven(Arrays.asList(1, 1, 2, 2, 3, 4, 5, 1));
        System.out.println(integers);

        List<Integer> integers2 = oddOrEven2(Arrays.asList(1, 1, 2, 2, 3, 4, 5, 1, 1));
        System.out.println(integers2);
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b );
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int mod = integers.stream()
                .mapToInt(Integer::intValue)
                .sum() % 2;

        return integers.stream()
                .filter(x -> x % 2 == mod)
                .collect(Collectors.toList());
    }

    public static List<Integer> oddOrEven2(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(MainStream::isEven, Collectors.toList()));
        boolean isEven = integers.stream()
                .mapToInt(Integer::intValue)
                .sum() % 2 == 0;
        return map.get(isEven);
    }

    public static boolean isEven(Integer number) {
        return number % 2 == 0;
    }
}
