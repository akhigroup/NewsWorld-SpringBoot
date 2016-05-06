package pl.pwr.news.repository.reports;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.report.Report;

/**
 * Created by jakub on 2/29/16.
 */
@Repository
public interface ReportRepository extends CrudRepository<Report, Long>, PagingAndSortingRepository<Report, Long> {

}
