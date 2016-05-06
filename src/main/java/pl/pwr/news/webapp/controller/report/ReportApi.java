package pl.pwr.news.webapp.controller.report;

/**
 * Created by Evelan on 06/05/16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.news.model.report.Report;
import pl.pwr.news.service.exception.ArticleNotExist;
import pl.pwr.news.service.report.ReportService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.report.dto.ReportDTO;


/**
 * Created by jakub on 3/9/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class ReportApi {

    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public Response<ReportDTO> saveReport(
            @RequestParam String text,
            @RequestParam Long articleId) {

        Report report;
        try {
            report = reportService.create(articleId, text);
        } catch (ArticleNotExist articleNotExist) {

            return new Response<>("-1", articleNotExist.getMessage());
        }

        return new Response<>(new ReportDTO(report));
    }
}
