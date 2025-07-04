package customapis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;

@TestApi(
        title="AWS REST API Connection",
        summary="Makes a REST API request with AWS authentication",
        remarks="Uses AWS Signature Version 4 for authentication.",
        iconBase="",
        defaultApiGroups={"Custom"}
)
@TestApiParameterGroups(parameterGroups={
        @TestApiParameterGroup(groupName="inputs", title="Inputs"),
        @TestApiParameterGroup(groupName="result", title="Result")
})
public class AWSRestAPIConnection {

    @TestApiParameter(seq=1, summary="API Endpoint URL", mandatory=true, parameterGroup="inputs")
    public String url;

    @TestApiParameter(seq=2, summary="AWS Access Key", mandatory=true, parameterGroup="inputs")
    public String accessKey;

    @TestApiParameter(seq=3, summary="AWS Secret Key", mandatory=true, parameterGroup="inputs")
    public String secretKey;

    @TestApiParameter(seq=4, summary="AWS Region", mandatory=true, parameterGroup="inputs")
    public String awsRegion;

    @TestApiParameter(seq=5, summary="AWS Service Name", mandatory=true, parameterGroup="inputs")
    public String serviceName;

    @TestApiParameter(seq=6, summary="The name that the result will be stored under.", mandatory=true, parameterGroup="result")
    public String resultName;

    @TestApiParameter(seq=7, summary="The lifespan of the result.", mandatory=true, parameterGroup="result", defaultValue="Test")
    public ValueScope resultScope;

    @TestLogger
    public Logger testLogger;

    @TestExecutionContext
    public ITestExecutionContext testExecutionContext;

    @TestApiExecutor
    public void execute() {
        try {
            String signedUrl = signRequest(url, accessKey, secretKey, awsRegion, serviceName);
            String response = sendRequest(signedUrl);
            testLogger.info("API Response: " + response);
            testExecutionContext.setValue(resultName, response, resultScope);
        } catch (Exception e) {
            testLogger.severe("Error in AWS REST API request: " + e.getMessage());
            testExecutionContext.setValue(resultName, "Error: " + e.getMessage(), resultScope);
        }
    }

    private String signRequest(String url, String accessKey, String secretKey, String region, String service) throws Exception {
        String stringToSign = "AWS4" + secretKey;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(stringToSign.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signature = mac.doFinal(url.getBytes("UTF-8"));
        String encodedSignature = Base64.getEncoder().encodeToString(signature);
        return url + "?X-Amz-Signature=" + encodedSignature + "&X-Amz-Date=" + System.currentTimeMillis();
    }

    private String sendRequest(String signedUrl) throws Exception {
        URL url = new URL(signedUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
