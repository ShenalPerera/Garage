SELECT COUNT(*) FROM schedule s
WHERE s.date = '2024-02-26'
  AND ((s.start_time >= '14:30:00' AND s.start_time < '16:30:00')
    OR (s.end_time > '14:30:00' AND s.end_time <= '16:30:00'))
