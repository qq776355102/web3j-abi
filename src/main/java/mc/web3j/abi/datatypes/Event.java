package mc.web3j.abi.datatypes;

import static mc.web3j.abi.Utils.convert;

import java.util.List;
import java.util.stream.Collectors;

import mc.web3j.abi.TypeReference;

/**
 * Event wrapper type.
 * @param <T>
 */
public class Event<T> {
    private String name;
    private List<TypeReference<Type<T>>> parameters;

    public Event(String name, List<TypeReference<Type<T>>> parameters) {
        this.name = name;
        this.parameters = convert(parameters);
    }

    public String getName() {
        return name;
    }

    public List<TypeReference<Type<T>>> getParameters() {
        return parameters;
    }

    public List<TypeReference<Type<T>>> getIndexedParameters() {
        return parameters.stream()
                .filter(TypeReference::isIndexed)
                .collect(Collectors.toList());
    }

    public List<TypeReference<Type<T>>> getNonIndexedParameters() {
        return parameters.stream()
                .filter(p -> !p.isIndexed())
                .collect(Collectors.toList());
    }
}
