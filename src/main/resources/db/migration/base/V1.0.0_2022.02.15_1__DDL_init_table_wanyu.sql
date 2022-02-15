-- *********** file ***********
-- 删除file表相关数据
DROP TABLE IF EXISTS public.file;
DROP SEQUENCE IF EXISTS public.file_id_seq;
DROP INDEX IF EXISTS public.file_code_uindex;

-- 创建file表
CREATE SEQUENCE IF NOT EXISTS public.file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- 创建自增序列
CREATE TABLE IF NOT EXISTS public."file"
(
    id                   serial PRIMARY KEY,             -- id:主键、自增、非空
    flag                 int8    NOT NULL DEFAULT 0,     -- 状态，0正常存在，-id已被删除
    code                 varchar NOT NULL,               -- 唯一code
    file_name            varchar NOT NULL,               -- 显示名

    bucket_name          varchar,                        -- minio中的仓库名称
    path                 varchar,                        -- minio中的仓库路径
    url                  varchar,                        -- 下载地址url
    size                 int8,                           -- 文件大小
    view_frequency       int8,                           -- 被阅览的次数

    creator_account_name varchar,                        -- 创建人名称
    update_account_name  varchar,                        -- 修改人名称
    create_time          timestamp        default now(), -- 创建日期
    update_time          timestamp,                      -- 修改日期
    overdue_time         timestamp                       -- 过期日期
);


-- 调整相关
alter table public.file
    alter column id set default nextval('file_id_seq');
CREATE UNIQUE INDEX IF NOT EXISTS file_code_uindex ON public."file" USING btree ("code", "flag");


-- 注释
COMMENT ON SEQUENCE public.file_id_seq IS '文件id的自增序列';
COMMENT ON INDEX public.file_code_uindex IS '文件code的唯一索引';

COMMENT ON TABLE public."file" IS '文件信息表';

COMMENT ON COLUMN public."file".id IS 'id:主键、自增、非空';
COMMENT ON COLUMN public."file".flag IS '状态，0正常存在，-id已被删除';
COMMENT ON COLUMN public."file".code IS '唯一code:文件md5码';
COMMENT ON COLUMN public."file".file_name IS '显示名';

COMMENT ON COLUMN public."file".bucket_name IS 'minio中的仓库名称';
COMMENT ON COLUMN public."file".path IS 'minio中的仓库路径';
COMMENT ON COLUMN public."file".url IS '下载地址url';
COMMENT ON COLUMN public."file".size IS '文件大小';
COMMENT ON COLUMN public."file".view_frequency IS '被阅览的次数';

COMMENT ON COLUMN public."file".creator_account_name IS '创建人名称';
COMMENT ON COLUMN public."file".update_account_name IS '修改人名称';
COMMENT ON COLUMN public."file".create_time IS '创建时间';
COMMENT ON COLUMN public."file".update_time IS '更新时间';
COMMENT ON COLUMN public."file".overdue_time IS '过期日期';


-- *********** record ***********
-- 删除record表相关数据
DROP TABLE IF EXISTS public.record;
DROP SEQUENCE IF EXISTS public.record_id_seq;

-- 创建file表
CREATE SEQUENCE IF NOT EXISTS public.record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- 创建自增序列
CREATE TABLE IF NOT EXISTS public."record"
(
    id         serial PRIMARY KEY,     -- id:主键、自增、非空
    file_code  varchar NOT NULL,       -- 文件md5码

    ip         varchar,                -- minio中的仓库路径
    type       int2,                   -- 操作类型
    momentTime timestamp default now() -- 创建日期
);

-- 调整相关
alter table public.record
    alter column id set default nextval('record_id_seq');

-- 注释
COMMENT ON SEQUENCE public.record_id_seq IS '记录id的自增序列';

COMMENT ON TABLE public."record" IS '操作记录表';

COMMENT ON COLUMN public."record".id IS 'id:主键、自增、非空';
COMMENT ON COLUMN public."record".file_code IS '唯一code:文件md5码';
COMMENT ON COLUMN public."record".ip IS '操作人的ip地址记录';
COMMENT ON COLUMN public."record".type IS '操作类型:0上传,1更新,2下载,3预览,-1未知';