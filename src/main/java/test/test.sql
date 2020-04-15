select sc.sid,cname,score
 from sc,course,student
where student.sid=sc.sid
and sc.cid=course.cid
and sc.sid not in(select sid from sc where score <=70);
select sid
from sc
group by sid
having count(cid) >=2;
explain select * from student where sid=1
+----+-------------+---------+-------+---------------+---------+---------+-------+------+-------+
| id  select_type  table  type  possible_keys  key     key_len ref   rows  Extra
+----+-------------+---------+-------+---------------+---------+---------+-------+------+-------+
|  1 | SIMPLE      | student | const | PRIMARY       | PRIMARY | 4       | const |    1 | NULL  |
+----+-------------+---------+-------+---------------+---------+---------+-------+------+-------+
show variables like