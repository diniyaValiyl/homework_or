package oris.other;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter<S, T> {
    T convert(S s);

    default List<T> convertList(List<S> list) {
        return list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}