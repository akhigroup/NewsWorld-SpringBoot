package pl.pwr.news.service.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.report.Report;
import pl.pwr.news.service.article.ArticleNotExist;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface ReportService {


    Report create(Long articleId, String text) throws ArticleNotExist;

    Page getReports(Pageable pageable);
}
