

import java.util.HashMap;
import java.util.Map;

public class Memoire {
	Map<String, String> results = new HashMap<>();
	
	public void setValue(String ref, String s) {
		results.put(ref, s);
	}
	
	public String getValue(String ref) {
		return results.get(ref);
	}
}
