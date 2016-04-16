package pl.pwr.news.provider.wykop;

import java.util.List;

/**
 * Created by Evelan on 16/04/16.
 */
public interface WykopAPI {

    List<WykopArticleDTO> getWykopApiArticles(String resourceUrl);
}
