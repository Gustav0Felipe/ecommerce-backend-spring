package com.app.ecommerce.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.app.ecommerce.dto.CartItemDto;
import com.app.ecommerce.dto.PedidoDto;
import com.app.ecommerce.model.Produto;
import com.app.ecommerce.repository.ProdutoRepository;

@Service
public class ApiFreteService {
	
	@Value("${URL_CORREIO}")
	public final String url;
	
	@Value("${TOKEN_CORREIO}")
	public final String token;
	
	public RestClient restClient;
	
	public ProdutoRepository produtoRepository;
	
	ApiFreteService(@Value("${URL_CORREIO}") String url,
			@Value("${TOKEN_CORREIO}") String token,
			RestClient template,
			ProdutoRepository produtoRepository
		){
		this.url = url;
		this.token = token;
		this.restClient = template;
		this.produtoRepository = produtoRepository;
	}
	
	public JSONObject calculaFrete(PedidoDto pedido)  {
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
    	
	    try {
		    ResponseEntity<String> apiResponse = 
		    		restClient.post().uri(url).body(body.toString()).contentType(MediaType.APPLICATION_JSON)
		    		.accept(MediaType.APPLICATION_JSON).header("Authorization","Bearer " + token)
		    		.retrieve().toEntity(String.class)
		    		;
			JSONArray formasDeEnvio = new JSONArray(apiResponse.getBody());
			JSONArray opcoes = new JSONArray();
			formasDeEnvio.forEach( f -> {
				JSONObject e = new JSONObject(f.toString());
				if(e.has("name") && e.has("price")){
					opcoes.put(new JSONObject()
							.put("id", e.getInt("id"))
							.put("name", e.get("name").toString())
							.put("company", e.getJSONObject("company").getString("name"))
							.put("price", e.getDouble("price"))
							);
				}
			});
			JSONObject response = new JSONObject().put("formas", opcoes);
			return response;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	    return null;
    }
}
