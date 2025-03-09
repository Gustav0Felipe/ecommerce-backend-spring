package com.app.ecommerce.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.ecommerce.dto.PedidoDto;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;

@Service
public class PixService {
	
	private JSONObject options = new JSONObject();
		

	
	PixService(@Value("#{environment.CLIENT_ID}") String clientId,
				@Value("#{environment.CLIENT_SECRET}") String clientSecret,
				@Value("#{environment.CERTIFICATE_PATH}") String certificatePath 
			){
        options.put("client_id", clientId);
        options.put("client_secret", clientSecret);
        options.put("certificate", certificatePath);
        options.put("sandbox", false);
	}
	
    public JSONObject pixCreateEVP(){
        try {  
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateEvp", new HashMap<String,String>(), new JSONObject());
            return response;
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JSONObject pixCreateCharge(PedidoDto pedido, Double valorTotal){
    	try {
	    	EfiPay efi = new EfiPay(options); 
	    			
			JSONObject body = new JSONObject();
			JSONObject keys = efi.call("pixListEvp",  new HashMap<String,String>(), body);
			String chave = "";
			if(keys != null) {
				chave = keys.getJSONArray("chaves").getString(0);
			}else {
				chave = pixCreateEVP().toString();
			}
			System.out.println("Valor Total : " + valorTotal);
			
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

            JSONObject charge = efi.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);
            int idFromJson= charge.getJSONObject("loc").getInt("id");
            charge.put("QRCode", pixGenerateQRCode(String.valueOf(idFromJson)));
            
            JSONObject response = new JSONObject();
            response.put("valor", charge.get("valor"));
            response.put("QRCode", charge.get("QRCode"));
            response.put("Chave", charge.get("chave"));
            
            System.out.println("Chave: " + chave);
            return response;            	
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String pixGenerateQRCode(String id){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
      
        try {
            EfiPay efi= new EfiPay(options);
            Map<String, Object> response = efi.call("pixGenerateQRCode", params, new HashMap<String, Object>());
            return response.get("imagemQrcode").toString();
        }catch (EfiPayException e){ 
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
