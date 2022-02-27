-- *********** 修改历史表格 ***********
DROP INDEX if exists public.file_code_uindex;
ALTER TABLE public.file
    DROP COLUMN if exists overdue_time;
ALTER TABLE public.file
    DROP COLUMN if exists update_time;
ALTER TABLE public.file
    DROP COLUMN if exists update_account_name;
ALTER TABLE public.file
    DROP COLUMN if exists file_code;



DO
$$
    BEGIN
        IF EXISTS(SELECT *
                  FROM information_schema.columns
                  WHERE table_name = 'file'
                    and column_name = 'md5_code')
        THEN
            ALTER TABLE "public"."file"
                RENAME COLUMN "md5_code" TO "md5";
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF EXISTS(SELECT *
                  FROM information_schema.columns
                  WHERE table_name = 'file'
                    and column_name = 'minio_bucket')
        THEN
            ALTER TABLE "public"."file"
                RENAME COLUMN "minio_bucket" TO "bucket";
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF EXISTS(SELECT *
                  FROM information_schema.columns
                  WHERE table_name = 'file'
                    and column_name = 'minio_path')
        THEN
            ALTER TABLE "public"."file"
                RENAME COLUMN "minio_path" TO "path";
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF EXISTS(SELECT *
                  FROM information_schema.columns
                  WHERE table_name = 'file'
                    and column_name = 'preview_url')
        THEN
            ALTER TABLE "public"."file"
                RENAME COLUMN "preview_url" TO "url";
        END IF;
    END
$$;

CREATE UNIQUE INDEX IF NOT EXISTS file_code_uindex
    ON public."file" USING btree (md5, "flag");

-- *********** file_relation ***********
-- 删除file_relation表相关数据
DROP TABLE IF EXISTS public.file_relation;
DROP SEQUENCE IF EXISTS public.file_relation_id_seq;
DROP INDEX IF EXISTS public.file_relation_code_uindex;

-- 创建file表
CREATE SEQUENCE IF NOT EXISTS public.file_relation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- 创建自增序列
CREATE TABLE IF NOT EXISTS public."file_relation"
(
    id                   serial PRIMARY KEY,                 -- id:主键、自增、非空
    flag                 int8        NOT NULL DEFAULT 0,     -- 状态，0正常存在，-id已被删除
    file_code            varchar(32) NOT NULL,               -- 唯一code
    md5                  varchar(32) NOT NULL,               -- 文件md5码

    bucket               varchar,                            -- 仓库名称
    path                 varchar,                            -- 仓库路径

    creator_account_name varchar,                            -- 创建人名称
    update_account_name  varchar,                            -- 修改人名称
    create_time          timestamp   NOT NULL default now(), -- 创建日期
    update_time          timestamp,                          -- 修改日期
    overdue_time         timestamp                           -- 过期日期
);

-- 调整相关
alter table public.file_relation
    alter column id set default nextval('file_relation_id_seq');
CREATE UNIQUE INDEX IF NOT EXISTS file_relation_code_uindex ON public."file_relation" USING btree (file_code, "flag");


-- 注释
COMMENT ON SEQUENCE public.file_relation_id_seq IS '文件关系id的自增序列';
COMMENT ON INDEX public.file_relation_code_uindex IS '文件code的唯一索引';

COMMENT ON TABLE public."file_relation" IS '文件信息表';

COMMENT ON COLUMN public."file_relation".id IS 'id:主键、自增、非空';
COMMENT ON COLUMN public."file_relation".flag IS '状态，0正常存在，-id已被删除';
COMMENT ON COLUMN public."file_relation".file_code IS '唯一code';
COMMENT ON COLUMN public."file_relation".md5 IS '文件md5码';

COMMENT ON COLUMN public."file_relation".bucket IS '仓库名称';
COMMENT ON COLUMN public."file_relation".path IS '文件路径';

COMMENT ON COLUMN public."file_relation".creator_account_name IS '创建人名称';
COMMENT ON COLUMN public."file_relation".update_account_name IS '修改人名称';
COMMENT ON COLUMN public."file_relation".create_time IS '创建时间';
COMMENT ON COLUMN public."file_relation".update_time IS '更新时间';

COMMENT ON COLUMN public."file_relation".overdue_time IS '过期日期';