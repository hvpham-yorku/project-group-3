INSERT INTO terms(season, year) VALUES ('SUMMER', 2027)
ON DUPLICATE KEY UPDATE year = year;
