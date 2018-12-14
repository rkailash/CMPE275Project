package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.model.PlayHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlayHistoryRepository extends JpaRepository<PlayHistory, Long> {

    @Query(value = "select * from play_history p, movie m where p.customer_id = ?1 and p.movie_id = m.id", nativeQuery = true)
    List<PlayHistory> findMovieAndPlayHistoryByCustomer_Id(Long customerId);

    @Query(value = "select m.id, m.title, count(p.movie_id) as playcount from movie m  left join play_history p on m.id = p.movie_id group by m.id , m.title", nativeQuery = true)
    List<Object[]> getPlayPerMovie();

    @Query(value = "select p.movie_id, m.title, count(p.movie_id) as playcount from play_history p, movie m where p.movie_id = m.id group by movie_id order by playcount limit 10", nativeQuery = true)
    List<Object[]> getMostPlayedMovies();

    @Query(value = "select p.customer_id, m.name, count(p.customer_id) as playcount from play_history p, customer m where p.customer_id = m.id group by customer_id order by playcount limit 10", nativeQuery = true)
    List<Object[]> getMostActiveCustomers();

    @Query(value = "select count(distinct p.customer_id) as playcount from play_history p, customer c where p.customer_id = c.id and p.play_time between ?1 and ?2", nativeQuery = true)
    Long getActiveCustomersByPlayTime(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = "select distinct m.id, m.title, COUNT(p.id) as playcount, m.average_rating\n" +
            "from  play_history p  join movie m on  p.movie_id = m.id  and p.play_time between ?1 AND ?2\n" +
            "group by m.id, m.title\n" +
            "order by playcount desc\n" +
            "limit 10", nativeQuery = true)
    List<Object[]> getTopTenMoviesPlayCountInMonth(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
