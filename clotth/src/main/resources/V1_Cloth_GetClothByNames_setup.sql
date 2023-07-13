SELECT *
FROM cloth
WHERE designer_name >= :startDate AND cloth.designer_name <= :endDate;

SELECT *
FROM cloth
WHERE collection_Name >= :startDate AND cloth.collection_name <= :endDate;

