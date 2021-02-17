package aufgabe7;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CountByGroupCollector<E> implements Collector<E, Map<E, Integer>, Map<E, Integer>> {


    @Override
    public Supplier<Map<E, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<E, Integer>, E> accumulator() {
        return (map, element) -> {
            map.putIfAbsent(element, 0);
            map.compute(element, (k, v) -> ++v);
        };
    }

    @Override
    public BinaryOperator<Map<E, Integer>> combiner() {
        return (left, right) -> {
            for (Map.Entry<E, Integer> entry : right.entrySet()) {
                left.putIfAbsent(entry.getKey(), 0);
                left.compute(entry.getKey(), (k, v) -> v + entry.getValue());
            }
            return left;
        };
    }

    @Override
    public Function<Map<E, Integer>, Map<E, Integer>> finisher() {
        return map -> map;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }
}
