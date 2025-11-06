CREATE TABLE IF NOT EXISTS matches (
  match_id INT AUTO_INCREMENT PRIMARY KEY,
  match_date DATE NOT NULL,
  opponent VARCHAR(100) NOT NULL,
  runs INT NOT NULL,
  innings INT NOT NULL,
  wickets INT NOT NULL,
  overs_bowled DECIMAL(4,1) NOT NULL
);


