package ru.banksystem.training.kostyaback.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Service
public class CourseParseService {

    @Setter
    @Getter
    private Integer rateCbr;

    @Setter
    @Getter
    private Double rateRuonia;

    public Double parseLatestRate(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));

        // Логируем всю структуру
        System.out.println("=== XML Structure ===");
        System.out.println(doc.getDocumentElement().getTagName());
        System.out.println("Child nodes count: " + doc.getDocumentElement().getChildNodes().getLength());

        // Ищем <RuoniaResult>
        NodeList resultNodes = doc.getElementsByTagNameNS("http://web.cbr.ru/", "RuoniaResult");
        if (resultNodes.getLength() == 0) {
            System.out.println("Не найден <RuoniaResult>");
            return 0.0;
        }

        Element result = (Element) resultNodes.item(0);

        // Ищем внутри <ro>
        NodeList roNodes = result.getElementsByTagName("ro"); // без namespace — работает
        System.out.println("Найдено записей <ro>: " + roNodes.getLength());

        if (roNodes.getLength() == 0) {
            System.out.println("Данные RUONIA отсутствуют — возможно, выходной или нет данных");
            return 0.0;
        }

        // Берём последнюю запись
        Element lastRo = (Element) roNodes.item(roNodes.getLength() - 1);

        // Ищем <ruo>
        NodeList ruoNodes = lastRo.getElementsByTagName("ruo");
        if (ruoNodes.getLength() == 0) {
            System.out.println("Тег <ruo> не найден");
            return 0.0;
        }

        String rateText = ruoNodes.item(0).getTextContent().trim();
        System.out.println("Ставка: " + rateText);
        return Double.parseDouble(rateText);
    }
}
