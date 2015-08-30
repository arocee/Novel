/*======================================================*/
CREATE TABLE `type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(5) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8

CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article` varchar(10) NOT NULL,
  `tid` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`tid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8

CREATE TABLE `section` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `section` varchar(10) NOT NULL,
  `aid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`aid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8

CREATE TABLE `paragraph` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `paragraph` varchar(500) NOT NULL,
  `sid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `section_id` (`sid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8

/*======================================================*/
CREATE TABLE `user` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `password` varchar(20) NOT NULL,
  `rule` smallint(2) unsigned NOT NULL DEFAULT '2',
  `regtime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

CREATE TABLE `rule` (
  `id` smallint(100) NOT NULL,
  `description` varchar(20) NOT NULL,
  `adduser` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `manage` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `search` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `keyword` varchar(50) NOT NULL,
  `resultcount` int(10) unsigned NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `key_word` (`keyword`),
  KEY `key_time` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `pv` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(50) NOT NULL,
  `type` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '0 前台 1 后台',
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `key_type` (`type`),
  KEY `key_time` (`time`),
  KEY `key_url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

/*======================================================*/
SELECT t.id AS tid, a.id AS aid, s.id AS sid, p.id AS pid, p.paragraph 
FROM (((type AS t LEFT JOIN article AS a ON a.tid = t.id) 
LEFT JOIN section AS s ON s.aid = a.id)) 
LEFT JOIN paragraph AS p ON p.sid = s.id 
ORDER BY p.id ASC;

SELECT t.id AS tid, a.id AS aid, s.id AS sid, p.id AS pid, p.paragraph 
FROM (((paragraph AS p LEFT JOIN section AS s ON p.sid = s.id) 
LEFT JOIN article AS a ON s.aid = a.id)) 
LEFT JOIN type AS t ON a.tid = t.id 
ORDER BY p.id ASC;

SELECT 
	t.tc AS tc, a.ac AS ac, s.sc AS sc, p.pc AS pc 
FROM 
	(SELECT COUNT(*) AS tc FROM type) AS t,
	(SELECT COUNT(*) AS ac FROM article) AS a,
	(SELECT COUNT(*) AS sc FROM section) AS s,
	(SELECT COUNT(*) AS pc FROM paragraph) AS p;
