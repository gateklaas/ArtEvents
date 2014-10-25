package sparql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJSON {

	public static String prettyJSON(Object object)
	{
		String json = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return json;    	
	}
}
