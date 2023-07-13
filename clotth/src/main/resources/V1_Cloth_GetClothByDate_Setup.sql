SELECT *
FROM cloth
WHERE cloth.date_time >= :startDate AND cloth.date_time <= :endDate;



SELECT *
FROM cloth
WHERE date_time >= :startDate
  AND date_time <= :endDate
ORDER BY date_time
LIMIT :pageSize OFFSET :pageNumber;
