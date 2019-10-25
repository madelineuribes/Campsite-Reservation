SELECT * FROM reservation 
INNER JOIN site
ON reservation.site_id = site.site_id
INNER JOIN campground
ON site.campground_id = campground.campground_id
WHERE site.campground_id = 1
AND from_date > '2019-06-11'
AND to_date < '2019-11-15';