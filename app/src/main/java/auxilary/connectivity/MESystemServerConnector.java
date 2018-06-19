package auxilary.connectivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anastacia on 27.03.2018.
 */
public class MESystemServerConnector {
    private String outData = null;
    private String connectionURL = null;
    private String method = null;

    public MESystemServerConnector(String connectionURL, String outData, String method){
        this.outData = outData;
        this.connectionURL = connectionURL;
        this.method = method;
    }

    public String execute(){
        try {
            URL mesystemEndpoint = new URL(connectionURL + "?" + outData);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) mesystemEndpoint.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            urlConnection.setRequestMethod(method);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
