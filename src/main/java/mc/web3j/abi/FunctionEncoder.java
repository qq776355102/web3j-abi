package mc.web3j.abi;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import mc.web3j.abi.datatypes.Address;
import mc.web3j.abi.datatypes.Function;
import mc.web3j.abi.datatypes.StaticArray;
import mc.web3j.abi.datatypes.Type;
import mc.web3j.abi.datatypes.Uint;

/**
 * <p>Ethereum Contract Application Binary Interface (ABI) encoding for functions.
 * Further details are available
 * <a href="https://github.com/ethereum/wiki/wiki/Ethereum-Contract-ABI">here</a>.
 * </p>
 */
public class FunctionEncoder {

    private FunctionEncoder() { }

    public static <T> String encode(Function function) {
        List<Type<T>> parameters = function.getInputParameters();

        String methodSignature = buildMethodSignature(function.getName(), parameters);
        String methodId = buildMethodId(methodSignature);

        StringBuilder result = new StringBuilder();
        result.append(methodId);

        return encodeParameters(parameters, result);
    }

    public static <T> String encodeConstructor(List<Type<T>> parameters) {
        return encodeParameters(parameters, new StringBuilder());
    }

    private static <T> String encodeParameters(List<Type<T>> parameters, StringBuilder result) {
        int dynamicDataOffset = getLength(parameters) * Type.MAX_BYTE_LENGTH;
        StringBuilder dynamicData = new StringBuilder();

        for (Type parameter:parameters) {
            String encodedValue = TypeEncoder.encode(parameter);

            if (TypeEncoder.isDynamic(parameter)) {
                String encodedDataOffset = TypeEncoder.encodeNumeric(
                        new Uint(BigInteger.valueOf(dynamicDataOffset)));
                result.append(encodedDataOffset);
                dynamicData.append(encodedValue);
                dynamicDataOffset += encodedValue.length() >> 1;
            } else {
                result.append(encodedValue);
            }
        }
        result.append(dynamicData);

        return result.toString();
    }

    private static<T> int getLength(List<Type<T>> parameters) {
        int count = 0;
        for (Type type:parameters) {
            if (type instanceof StaticArray) {
                count += ((StaticArray) type).getValue().size();
            } else {
                count++;
            }
        }
        return count;
    }

    static <T> String buildMethodSignature(String methodName, List<Type<T>> parameters) {
        StringBuilder result = new StringBuilder();
        result.append(methodName);
        result.append("(");
        String params = parameters.stream()
                .map(Type::getTypeAsString)
                .collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        return result.toString();
    }

    static String buildMethodId(String methodSignature) {
        byte[] input = methodSignature.getBytes();
        byte[] hash = Hash.sha3(input);
        return Numeric.toHexString(hash).substring(0, 10);
    }
}
