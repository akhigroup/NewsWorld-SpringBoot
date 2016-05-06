package pl.pwr.news.webapp.controller.report.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.report.Report;

/**
 * Created by Evelan on 06/05/16.
 */
@Getter
@Setter
public class ReportDTO {

    private Long id;
    private String text;
    private Long articleId;

    public ReportDTO(Report report) {
        if (report != null) {
            this.id = report.getId();
            this.text = report.getText();
            this.articleId = report.getArticle().getId();
        }
    }

}
