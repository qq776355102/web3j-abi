package abi;

import java.util.Arrays;

import mc.web3j.abi.FunctionEncoder;
import mc.web3j.abi.TypeReference;
import mc.web3j.abi.datatypes.Address;
import mc.web3j.abi.datatypes.Function;

public class FunctionTest {

	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	    @SuppressWarnings("rawtypes")
		Function function = new Function(
                "getBalance", //合约方法名
                Arrays.asList(new Address("0xb6191034Eb4B2c36f97235ae5A41e35Fee4075C5")),//合约方法的参数
                Arrays.asList(new TypeReference<Address>(){}) //返回值
        );
	    // 交易串，发送给合约的data数据
        String encodedFunction = FunctionEncoder.encode(function);
        System.out.println(encodedFunction);
	}
}
