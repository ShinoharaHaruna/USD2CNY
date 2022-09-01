import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class USD2CNY {
    public static void main(String[] args) {
        String url = "https://www.xe.com/currencyconverter/convert/?Amount=1&From=USD&To=CNY";
        String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0";
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent(UA)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert document != null;
        String body = document.body().toString();
        String crop1 = "result__ConvertedText";
        String crop2 = "result__Repulsor-sc";
        body = body.substring(body.indexOf(crop1) + crop1.length(), body.indexOf(crop2) - crop2.length());
//        System.out.println("^" + body + "$");
        String reg = ">.+<s";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(body);
        BigDecimal rate = null;
        if (matcher.find()) {
            String str = matcher.group();
            str = str.substring(1, str.indexOf("<"));
            rate = new BigDecimal(str);
        }
        if (rate != null) {
            System.out.println("1 USD = " + rate + "CNY");
        }
    }
}
