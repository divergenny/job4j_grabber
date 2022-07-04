--1. В одном запросе получить
-- имена всех person, которые не состоят в компании с id = 5;
-- название компании для каждого человека.
select p.name, c.name
from person p join company c on p.company_id = c.id
where company_id != 5

-- 2. Необходимо выбрать название компании с максимальным
-- количеством человек + количество человек в этой компании
-- (нужно учесть, что таких компаний может быть несколько).
select c.name, COUNT(p.company_id) as quantity
from person p join company c on p.company_id = c.id
GROUP BY c.name
HAVING COUNT(p.company_id) = (select  COUNT(c.name) as quantity
from person p join company c on p.company_id = c.id
GROUP BY c.name
ORDER BY 1 DESC
limit 1)
