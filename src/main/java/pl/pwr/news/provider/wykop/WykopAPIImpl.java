package pl.pwr.news.provider.wykop;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Evelan on 16/04/16.
 */
@Component
@Log4j
public class WykopAPIImpl implements WykopAPI {

    private static final String APPKEY = "rhOXQTrs55";
    public static final String EXAMPLE_URL = "http://a.wykop.pl/popular/upcoming/appkey," + APPKEY;
    private static final String SECRET_KEY = "tWG8OSV7ML";

    public List<WykopArticleDTO> getWykopApiArticles(String resourceUrl) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();


        String hashedSign = signResource(SECRET_KEY, resourceUrl);
        body.add("apisign", hashedSign);
        body.add("Content-Type", "application/json");

        HttpEntity<?> entity = new HttpEntity<>(body);

        ResponseEntity<WykopArticleDTO[]> reponseDtoArr = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, WykopArticleDTO[].class);


        List<WykopArticleDTO> list = new ArrayList<>();
        Collections.addAll(list, reponseDtoArr.getBody());

        return list;
    }

    private String signResource(String secretKey, String resourceUrl) {

        String original = secretKey + resourceUrl;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
