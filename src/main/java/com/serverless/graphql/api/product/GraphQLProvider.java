/**
 * 
 */
package com.serverless.graphql.api.product;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

/**
 * @author felipeguerraoliveira
 *
 */
@Component
public class GraphQLProvider {

	private GraphQL graphQL;

	@Autowired
	private GraphQLDataFetchers graphQLDataFetchers;

	@PostConstruct
	public void init() throws IOException {
		URL url = Resources.getResource("schema.graphqls");
		String sdl = Resources.toString(url, Charsets.UTF_8);
		GraphQLSchema schema = buildSchema(sdl);
		this.graphQL = GraphQL.newGraphQL(schema).build();
	}

	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry definitionRegistry = new SchemaParser().parse(sdl);
		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();

		return schemaGenerator.makeExecutableSchema(definitionRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type(TypeRuntimeWiring.newTypeWiring("Query")
						.dataFetcher("productById", graphQLDataFetchers.getProductByIdDataFetcher()))
				.build();
	}

	@Bean
	public GraphQL getGraphQL() {
		return graphQL;
	}
}
