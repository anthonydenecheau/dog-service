-- 22/10/2018 : Mantis 0328 : Ajout information Autorise au Travail
-- [TODO] Attente go
ALTER TABLE ws_dog ADD COLUMN IF NOT EXISTS on_travail_nat varchar(1);
ALTER TABLE ws_dog ADD COLUMN IF NOT EXISTS on_travail_int varchar(1);
select 1 
