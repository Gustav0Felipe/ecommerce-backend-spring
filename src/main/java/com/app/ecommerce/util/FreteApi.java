package com.app.ecommerce.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.app.ecommerce.dto.CartItemDto;
import com.app.ecommerce.dto.PedidoDto;
import com.app.ecommerce.model.Produto;
import com.app.ecommerce.repository.ProdutoRepository;

@Component
public class FreteApi {
	public static String url = "https://melhorenvio.com.br/api/v2/me/shipment/calculate";
	
	@Value("${CORREIO_TOKEN}")
	public String token = "";
	
	@Autowired
	public ProdutoRepository produtoRepository;
	
	public JSONObject calculaFrete(PedidoDto pedido) {
		JSONObject body = new JSONObject();
		JSONArray lista = new JSONArray();

		String postalCodeFrom = "09332080";
		String postalCodeTo = String.valueOf(pedido.cep());
		body.put("from", new JSONObject().put("postal_code", postalCodeFrom));
		body.put("to", new JSONObject().put("postal_code", postalCodeTo));
		
		for(CartItemDto item : pedido.produtos()) {
			Produto produto =  produtoRepository.findById(item.id_prod()).get();
			lista.put(new JSONObject()
					.put("height", produto.getAltura_cm()).put("width", produto.getLargura_cm())
					.put("length", produto.getComprimento_cm()).put("weight", produto.getPeso_kg())
					.put("quantity", item.quantity()).put("insurance_value", produto.getValor())
					);
		}
		body.put("products", lista);
		body.put("options", new JSONObject().put("receipt", false).put("own_hand", false));
	    body.put("services", "1,2,3,4,7,11");
    	
    	HttpClient client = HttpClient.newHttpClient();
    	HttpRequest request = HttpRequest.newBuilder(URI.create(url))
    			.POST(HttpRequest.BodyPublishers.ofString(body.toString()))
    			.header("Accept", "application/json")
    			.header("Content-Type", "application/json\"")
    			.header("Authorization","Bearer " + token)
	    		.header("User-Agent", "Aplicação gustavo.custodio55@hotmail.com")
    			.build();
    	try {
		CompletableFuture<HttpResponse<String>> futureResponse = 
				client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
			
			HttpResponse<String> apiResponse = futureResponse.get();
			
			JSONArray formasDeEnvio = new JSONArray(apiResponse.body());
			JSONArray opcoes = new JSONArray();
			
			formasDeEnvio.forEach( f -> {
				JSONObject e = new JSONObject(f.toString());
				if(e.has("name") && e.has("price")){
					opcoes.put(new JSONObject().put("name", e.get("name").toString())
							.put("company", e.getJSONObject("company").getString("name"))
							.put("price", e.getDouble("price"))
							);
				}
			});
			JSONObject response = new JSONObject().put("formas", opcoes);
			return response;
    	} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} catch (ExecutionException e) {
			System.out.println(e.getMessage());
		}
    	return null;
    }

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		FreteApi.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ProdutoRepository getProdutoRepository() {
		return produtoRepository;
	}

	public void setProdutoRepository(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	
}
