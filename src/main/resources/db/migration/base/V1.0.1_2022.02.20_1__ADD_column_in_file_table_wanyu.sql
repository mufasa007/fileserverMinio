ALTER TABLE public.file
    ADD if not exists "format" varchar NULL;
COMMENT
ON COLUMN public.file."format" IS '文件格式';

ALTER TABLE public.file
    ALTER COLUMN view_frequency SET NOT NULL;
ALTER TABLE public.file
    ALTER COLUMN view_frequency SET DEFAULT 0;

ALTER TABLE public.file RENAME COLUMN "bucket_name" TO minio_bucket;
ALTER TABLE public.file RENAME COLUMN "path" TO minio_path;
