
## Video Player

- m3u8下载源 [https://github.com/imDazui/Tvlist-awesome-m3u-m3u8#%E5%B7%A5%E5%85%B7](https://github.
com/imDazui/Tvlist-awesome-m3u-m3u8#%E5%B7%A5%E5%85%B7)
- SQL
```sql
create database o_disk default character set utf8mb4 collate utf8mb4_unicode_ci;

use o_disk;

create table video (
    id bigint primary key comment '视频地址',
    type tinyint comment '视频类型',
    title varchar(255) character set utf8mb4 collate utf8mb4_unicode_ci comment '视频标题',
    source varchar(255) character set utf8mb4 collate utf8mb4_unicode_ci comment '资源地址'
);
```

## Online Docs

## Instant Message