package in.gw.TitoExitApi2;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;




@RestController


public class TitoExistController {

	
	
	@Autowired
	Services se;
	private static final Logger logger = LoggerFactory.getLogger(TitoExistController.class);


	@Value("${api.url}")
    private String apiUrl;

    @Value("${api.location_token}")
    private String locationToken;

    @Value("${api.access_token}")
    private String accessToken;

   
    @Scheduled(fixedRate = 60000)
    public ResponseEntity<List<Map<String, Object>>> getClientData2() {

        List<TitoDetails> getExistData = se.getExistLocationtime();
        List<Map<String, Object>> responseList = new ArrayList<>();

        if (getExistData == null || getExistData.isEmpty()) {
            logger.info("No data available for processing.");
            return ResponseEntity.ok(responseList);
        }

        List<TitoData> gettito = se.getAllTitoDetails();
        if (gettito == null || gettito.isEmpty()) {
            logger.info("No TITO details available.");
            return ResponseEntity.ok(responseList);
        }

        Map<String, TitoData> titoDataMap = new HashMap<>();
        for (TitoData titoData : gettito) {
            titoDataMap.put(titoData.getGateSlipNo(), titoData);
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        RestTemplate restTemplate = new RestTemplate();

        for (TitoDetails titoDetails : getExistData) {
            String gateSlip = titoDetails.getGateSlip();
            TitoData matchedData = titoDataMap.get(gateSlip);

            if (matchedData != null) {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("vehicle_no", matchedData.getVehicleNo());
                String gateExistTime = titoDetails.getTimeStamp().toLocalDateTime().format(timeFormatter);
                responseData.put("gate_exist_time", gateExistTime);
                String gateExistDate = titoDetails.getTimeStamp().toLocalDateTime().format(dateFormatter);
                responseData.put("gate_exist_date", gateExistDate);
                responseList.add(responseData);

                // Log vehicle number, date, and time
                logger.info("Preparing to send data to API for Vehicle No: {}, Date: {}, Time: {}", 
                            matchedData.getVehicleNo(), gateExistDate, gateExistTime);

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("Location_Token", locationToken);
                    headers.set("Access_Token", accessToken);

                    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(responseData, headers);
                    ResponseEntity<String> apiResponse = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

                    if (apiResponse.getStatusCode().is2xxSuccessful()) {
                        se.updateProcessedStatus(titoDetails.getGateSlip());
                        logger.info("GateSlip {} processed successfully.", gateSlip);
                    } else {
                        logger.error("Failed to send data for GateSlip {}. Response: {}", gateSlip, apiResponse.getStatusCode());
                    }

                } catch (HttpClientErrorException | HttpServerErrorException e) {
                    logger.error("HTTP error for GateSlip {}: {}", gateSlip, e.getMessage());
                } catch (ResourceAccessException e) {
                    logger.error("Network error for GateSlip {}: {}", gateSlip, e.getMessage());
                } catch (Exception e) {
                    logger.error("Unexpected error for GateSlip {}: {}", gateSlip, e.getMessage());
                }
            }
        }

        if (responseList.isEmpty()) {
            logger.info("No vehicles available to process for exit.");
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("message", "There is no vehicle to exist");
            responseList.add(responseData);
        }

        return ResponseEntity.ok(responseList);
    }

}
