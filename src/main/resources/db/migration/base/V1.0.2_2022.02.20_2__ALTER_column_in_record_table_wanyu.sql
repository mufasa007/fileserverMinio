ALTER TABLE public.record
    ALTER COLUMN moment_time SET DEFAULT now();
ALTER TABLE public.record
    DROP COLUMN IF EXISTS request_url;
ALTER TABLE public.record
    DROP COLUMN IF EXISTS param_json;