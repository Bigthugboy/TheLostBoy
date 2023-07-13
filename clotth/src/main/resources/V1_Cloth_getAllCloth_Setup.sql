SELECT *
FROM cloth
ORDER BY id
    LIMIT :numberOfItems OFFSET :numberOfPages;


SELECT COUNT(*) as totalNumberOfElementsInDatabase
FROM cloth;

SELECT *
FROM cloth
ORDER BY collection_name
LIMIT :numberOfItems OFFSET :numberOfPages;
