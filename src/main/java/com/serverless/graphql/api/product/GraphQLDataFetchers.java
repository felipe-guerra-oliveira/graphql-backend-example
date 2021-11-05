/**
 * 
 */
package com.serverless.graphql.api.product;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import graphql.com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;

/**
 * @author felipeguerraoliveira
 *
 */
@Component
public class GraphQLDataFetchers {
	
	private static List<ImmutableMap<String, String>> products = Arrays.asList(
			ImmutableMap.of("id", "product-1",
					"description", "Teste 1",
					"status", "true",
					"ranking", "1"),
			ImmutableMap.of("id", "product-2",
					"description", "Teste 2",
					"status", "false",
					"ranking", "3")
	);
	
	@SuppressWarnings("rawtypes")
	public DataFetcher getProductByIdDataFetcher() {
		return dataFetchingEnvironment -> {
			String productId = dataFetchingEnvironment.getArgument("id");
			return products.
					stream().
					filter(product -> product.get("id").equals(productId))
					.findFirst()
					.orElse(null);
		};
	}

}
