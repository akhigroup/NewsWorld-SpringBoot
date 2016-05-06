package pl.pwr.news.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.report.Report;
import pl.pwr.news.repository.article.ArticleRepository;
import pl.pwr.news.repository.reports.ReportRepository;
import pl.pwr.news.service.article.ArticleNotExist;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public Report create(Long articleId, String text) throws ArticleNotExist {

        if (!articleRepository.exists(articleId)) {
            throw new ArticleNotExist("Article not exist by id: " + articleId);
        }

        Article article = articleRepository.findOne(articleId);
        Report report = new Report();
        report.setText(text);
        report.setArticle(article);
        return reportRepository.save(report);
    }

    @Override
    public Page getReports(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }
}
