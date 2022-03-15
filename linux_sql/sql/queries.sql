select cpu_number, id, total_mem
from host_info
order by cpu_number ASC, total_mem desc ;

select host_id, hostname, date_trunc('hour', host_usage.timestamp) + date_part('minute', host_usage.timestamp):: int / 5 * interval '5 min' as timenew,
	round((avg( total_mem - memory_free) /total_mem *100),3) As avg_used_mem_percentage
from host_info
join host_usage on host_info.id  = host_usage.host_id
GROUP BY host_id, hostname, timenew, total_mem
order by timenew  ;

SELECT * FROM (
    select host_id, hostname, date_trunc('hour', host_usage.timestamp) + date_part('minute', host_usage.timestamp):: int / 5 * interval '5 min' as timenew,
	    count(*) as num_data_points
    from host_info
    join host_usage on host_info.id  = host_usage.host_id
    GROUP BY host_id, hostname, timenew, total_mem
    order by timenew) as count_executions
WHERE num_data_points <3;


