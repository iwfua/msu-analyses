package ru.banksystem.training.kostyaback.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.banksystem.training.kostyaback.service.CourseParseService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/course")
public class CourseController {

    @Value("${cb.course.url.with.date}")
    private String getCourseCbUrl;

    private final String getCourseRuoniaUrl;

    @Value("${token}")
    private String token;

    private final WebClient webClient;
    private final CourseParseService service;
    private final RestTemplate restTemplate;

    public CourseController(WebClient.Builder webClientBuilder, CourseParseService service, RestTemplate restTemplate) {
        this.getCourseRuoniaUrl = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "                    <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "                                   xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                                   xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "                      <soap:Body>\n" +
                "                        <Ruonia xmlns=\"http://web.cbr.ru/\">\n" +
                "                          <fromDate>%sT00:00:00</fromDate>\n" +
                "                          <ToDate>%sT23:59:59</ToDate>\n" +
                "                        </Ruonia>\n" +
                "                      </soap:Body>\n" +
                "                    </soap:Envelope>";
        this.webClient = webClientBuilder.baseUrl("https://www.cbr.ru").build();
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/cb")
    public Integer getCourseForCb() {
        log.info("Прилетел запрос GET /cb");
        if (service.getRateCbr() == null) {
            requestCbRate();
            return service.getRateCbr();
        }

        return service.getRateCbr();
    }

    @GetMapping("/ruonia")
    public Double getCourseForRuonia() {

        if (service.getRateRuonia() == null) {
            requestRuoniaRate();
        }

        return service.getRateRuonia();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchCbRate() {
        requestCbRate();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void fetchRuoniaRate() {
        requestRuoniaRate();
    }

    private void requestRuoniaRate() {
        try {
            String fromDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE);
            String toDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

            String soapRequest = String.format(getCourseRuoniaUrl, fromDate, toDate);

            String response = webClient.post()
                    .uri("/DailyInfoWebServ/DailyInfo.asmx")
                    .header("Content-Type", "text/xml; charset=utf-8")
                    .header("SOAPAction", "\"http://web.cbr.ru/Ruonia\"")
                    .bodyValue(soapRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (response == null) {
                throw new RuntimeException("No response from CBR");
            }

            Double res = service.parseLatestRate(response);
            service.setRateRuonia(res);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения ставки RUONIA: " + e.getMessage(), e);
        }
    }


    private void requestCbRate() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String url = String.format(getCourseCbUrl, token, date);

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode body = response.getBody();
            JsonNode data = body.get("data");
            if (data != null && data.has("rate")) {
                Integer rate = data.get("rate").asInt();
                service.setRateCbr(rate);
            }
        }
    }
}
