package com.app.ecommerce.service;

import java.math.BigDecimal;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.ecommerce.dto.PedidoDto;

@Service
public class ApiPixService {
	
	public RestTemplate restTemplate;

	private final String client_id;
	
	private final String client_secret;
	
	private final String basicAuth;
	
	private final String url;

	ApiPixService(@Value("${CLIENT_ID}") String clientId,
				@Value("${CLIENT_SECRET}") String clientSecret,
				@Value("${URL_PIX}") String url,
				RestTemplate restTemplate
			){
		this.client_id = clientId;
		this.client_secret = clientSecret;
		this.url = url;
		this.restTemplate = restTemplate;
		basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));
	}
	
	public String getAccessToken() {
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.APPLICATION_JSON);
	    header.setBasicAuth(basicAuth);
	    
	    JSONObject body = new JSONObject();
	    
	    body.put("grant_type", "client_credentials");
	    HttpEntity<String> entity = new HttpEntity<String>(body.toString(), header);
	    
	    ResponseEntity<String> apiResponse = restTemplate.postForEntity(url + "/oauth/token", entity, String.class);
	    JSONObject response = new JSONObject(apiResponse.getBody());
	    
	    return response.getString("access_token");
	}
	  
    public JSONObject pixCreateEVP(HttpHeaders header){
        try {  
			HttpEntity<String> entity = new HttpEntity<String>(header);
		    ResponseEntity<String> apiCreateEvp = restTemplate.postForEntity(
		    	    url + "/v2/gn/evp", entity, String.class);
			JSONObject response = new JSONObject(apiCreateEvp.getBody());
        	return response;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JSONObject pixCreateChargeQrCode(PedidoDto pedido, Double valorTotal, HttpHeaders header){
    	try {
		    header.setContentType(MediaType.APPLICATION_JSON);
		    
			HttpEntity<String> entity = new HttpEntity<String>(header);
		    ResponseEntity<String> apiListEvp = restTemplate.exchange(
		    	    url + "/v2/gn/evp", HttpMethod.GET, entity, String.class);
			JSONObject listEvp = new JSONObject(apiListEvp.getBody());
		    
			String chave = "";
			if(!listEvp.isEmpty()) {
				chave = listEvp.getJSONArray("chaves").getString(0);
			}else {
				chave = pixCreateEVP(header).toString();
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


	        HttpEntity<String> entityCharge = new HttpEntity<String>(body.toString(), header);
	        ResponseEntity<String> responseCharge = restTemplate.postForEntity(
	        		url + "/v2/cob", entityCharge, String.class);
	        
			JSONObject charge = new JSONObject(responseCharge.getBody());
			   
			int idFromJson= charge.getJSONObject("loc").getInt("id");
			charge.put("QRCode", pixGenerateQRCode(String.valueOf(idFromJson), header));
			    
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

    public String pixGenerateQRCode(String id, HttpHeaders header){
        try {
        	HttpEntity<String> entity = new HttpEntity<String>(header);
  	        ResponseEntity<String> generateQrCode = restTemplate.exchange(
  		    	    url + "/v2/loc/" + id + "/qrcode", HttpMethod.GET, entity, String.class);
  	        JSONObject response = new JSONObject(generateQrCode.getBody());
            return response.get("imagemQrcode").toString();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public JSONObject pixAbrirPagamentoQrCode(PedidoDto pedido, Double valorTotal) {
    	HttpHeaders header = new HttpHeaders();
	    header.setBearerAuth(getAccessToken());
    	
    	return pixCreateChargeQrCode(pedido, valorTotal, header);
    }
}


