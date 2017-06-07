package cz.sedlaj19.autoskola.domain.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Honza on 15. 2. 2017.
 */

public class Website {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> out = new HashMap<>();
        out.put("url", url);
        return out;
    }
}
