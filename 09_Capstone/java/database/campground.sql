SELECT r.name c.name, site.site_id, site.campground_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, c.daily_fee 
FROM site
JOIN reservation r ON r.site_id = site.site_id 
JOIN campground c ON c.campground_id = site.campground_id
WHERE site.site_id NOT IN 
(SELECT site.site_id FROM campground 
INNER JOIN site ON site.campground_id = campground.campground_id 
INNER JOIN reservation ON reservation.site_id = site.site_id 
WHERE reservation.from_date <= '2019-08-08' 
AND reservation.to_date >= '2019-08-15' 
AND site.campground_id = 2);

select * from reservation;

select site_number, max_occupancy, accessible, max_rv_length, utilities, daily_fee 
from site join campground on site.campground_id = campground.campground_id 
where site.campground_id = ? and site_id not in (select site_id from reservation where (?, ?) 
overlaps (from_date, to_date) group by site_id) limit 5;


GROUP BY site.site_id, site.campground_id, c.daily_fee LIMIT 5;

(SELECT s.site_id, s.campground_id, s.site_number, s.max_occupancy, s.accessible, s.max_rv_length, s.utilities, c.daily_fee 
FROM site s 
JOIN reservation r ON r.site_id = s.site_id 
JOIN campground c ON c.campground_id = s.campground_id 
WHERE s.site_id IN (SELECT r.site_id FROM reservation 
WHERE (r.to_date BETWEEN '2019-03-09' AND '2019-03-18' ) OR (r.from_date BETWEEN '2019-03-09' AND '2019-03-18' ) 
OR (r.to_date < '2019-03-09' AND r.from_date > '2019-03-18' )) AND s.campground_id = 2 
GROUP BY s.site_id,s.campground_id, c.daily_fee LIMIT 5);



select site_id, site_number, max_occupancy, accessible, max_rv_length, utilities, daily_fee from site join campground on site.campground_id = campground.campground_id where site.campground_id = 2 and site_id not in  
(select site_id from reservation where ('2019-08-08', '2019-08-15') overlaps (from_date, to_date) group by site_id) limit 5;