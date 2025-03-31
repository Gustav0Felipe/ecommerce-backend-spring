package com.app.ecommerce.service;

import java.math.BigDecimal;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.app.ecommerce.dto.PedidoDto;

@Service
public class ApiPixService {
	
	public RestClient restClient;

	private final String client_id;
	
	private final String client_secret;
	
	private final String basicAuth;
	
	private final String url;

	ApiPixService(@Value("${CLIENT_ID}") String clientId,
				@Value("${CLIENT_SECRET}") String clientSecret,
				@Value("${URL_PIX}") String url,
				RestClient restClient
			){
		this.client_id = clientId;
		this.client_secret = clientSecret;
		this.url = url;
		this.restClient = restClient;
		basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));
	}
	
	public String getAccessToken() {
	    JSONObject body = new JSONObject();

	    body.put("grant_type", "client_credentials");
	    
	    ResponseEntity<String> apiResponse = restClient.post().uri(url + "/oauth/token", "")
	    		.body(body.toString())
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.header("Authorization", "Basic " + basicAuth).retrieve().toEntity(String.class);
	    		
	    JSONObject response = new JSONObject(apiResponse.getBody());
	    
	    return response.getString("access_token");
	}
	  
    public JSONObject pixCreateEVP(String access_token){
        try {  
		    ResponseEntity<String> apiCreateEvp = restClient.post()
			    		.uri(url + "/v2/gn/evp","")
	    				.header("Authorization",access_token)
			    		.contentType(MediaType.APPLICATION_JSON)
			    		.retrieve().toEntity(String.class);
			JSONObject response = new JSONObject(apiCreateEvp.getBody());
        	return response;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JSONObject pixCreateChargeQrCode(PedidoDto pedido, Double valorTotal, String access_token){
    	try {
    		
		    ResponseEntity<String> apiListEvp = restClient.get()
		    				.uri(url + "/v2/gn/evp", "")
		    				.header("Authorization","Bearer " + access_token)
		    				.header("content-type", "application/json").retrieve().toEntity(String.class);
		    
			JSONObject listEvp = new JSONObject(apiListEvp.getBody());
		    
			String chave = "";
			if(!listEvp.isEmpty()) {
				chave = listEvp.getJSONArray("chaves").getString(0);
			}else {
				chave = pixCreateEVP(access_token).toString();
			}
			
			JSONObject body = new JSONObject();
			
			BigDecimal valor = BigDecimal.valueOf(valorTotal);
			valor = valor.setScale(2);
			
			body.put("calendario", new JSONObject().put("expiracao", 3600));
	        body.put("devedor", new JSONObject()
	        		.put("cpf", pedido.usuario().getCpf().replace(".","").replace("-", ""))
	        		.put("nome", pedido.usuario().getNome_user()));
	        body.put("valor", new JSONObject().put("original", valor.toString()));
	        body.put("chave", chave);
	        //body.put("notification_url", "");
	        JSONArray infoAdicionais = new JSONArray();
	        infoAdicionais.put(new JSONObject().put("nome", pedido.usuario().getNome_user()).put("valor", valor.toString()));
	        body.put("infoAdicionais", infoAdicionais);

	        ResponseEntity<String> responseCharge = restClient.post()
	        		.uri(url + "/v2/cob", "")
	        		.header("Authorization","Bearer " + access_token)
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.body(body.toString())
	        		.retrieve().toEntity(String.class);
	        
			JSONObject charge = new JSONObject(responseCharge.getBody());
			   
			int idFromJson= charge.getJSONObject("loc").getInt("id");
			charge.put("QRCode", pixGenerateQRCode(String.valueOf(idFromJson), access_token));
			    
			JSONObject response = new JSONObject();
			response.put("valor", charge.get("valor"));
			response.put("QRCode", charge.get("QRCode"));
			response.put("Chave", charge.get("chave"));
			    
			System.out.println("Chave: " + chave);
			return response;            	
	    }
	    catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	    return null;
	}

    public String pixGenerateQRCode(String id, String access_token){
        try {
  	        ResponseEntity<String> generateQrCode = restClient.get().uri(url + "/v2/loc/" + id + "/qrcode", "")
  	        				.header("Authorization","Bearer " + access_token).retrieve().toEntity(String.class);

  	        JSONObject response = new JSONObject(generateQrCode.getBody());
            return response.get("imagemQrcode").toString();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public JSONObject pixAbrirPagamentoQrCode(PedidoDto pedido, Double valorTotal) {
    	String access_token = getAccessToken();
    	valorTotal = 0.01;
    	return pixCreateChargeQrCode(pedido, valorTotal, access_token);
    }
}


