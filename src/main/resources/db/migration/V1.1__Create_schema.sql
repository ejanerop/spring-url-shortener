CREATE TABLE IF NOT EXISTS "url" (
	"key" text,
	"url" text,
	created_at timestamptz,
	CONSTRAINT url_pkey PRIMARY KEY ("key")
);