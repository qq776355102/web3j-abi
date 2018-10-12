package mc.web3j.abi.datatypes;

import static mc.web3j.abi.Utils.convert;

import java.util.List;

import mc.web3j.abi.TypeReference;

/**
 * Function type.
 * @param <T>
 */
public class Function<T> {
    private String name;
    private List<Type<T>> inputParameters;
    private List<TypeReference<Type<T>>> outputParameters;

    public Function(String name, List<Type<T>> inputParametersList,
    		List<TypeReference<Type<T>>> outputParametersList) {
        this.name = name;
        this.inputParameters = inputParametersList;
        this.outputParameters = convert(outputParametersList);
    }


	public String getName() {
        return name;
    }

    public List<Type<T>> getInputParameters() {
        return inputParameters;
    }

    public List<TypeReference<Type<T>>> getOutputParameters() {
        return outputParameters;
    }

	public void setName(String name) {
		this.name = name;
	}

	public void setInputParameters(List<Type<T>> inputParameters) {
		this.inputParameters = inputParameters;
	}

	public void setOutputParameters(List<TypeReference<Type<T>>> outputParameters) {
		this.outputParameters = outputParameters;
	}
    
}
