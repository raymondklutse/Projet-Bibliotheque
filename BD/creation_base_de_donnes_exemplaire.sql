-- Titre             : Script SQL (PostgreSQL) de création de la base de données du projet bibliothèque
-- Version           : 1.0
-- Date création     : 07 mars 2006
-- Date modification : 9 avril 2017
-- Auteur            : Philippe TANGUY
-- Description       : Ce script est une ébauche, à compléter, qui permet la création de la table
--                     "livre" pour la réalisation de la fonctionnalité "liste de tous les livres".

-- +----------------------------------------------------------------------------------------------+
-- | Suppression des tables                                                                       |
-- +----------------------------------------------------------------------------------------------+

drop table if exists "exemplaire";

-- +----------------------------------------------------------------------------------------------+
-- | Création des tables                                                                          |
-- +----------------------------------------------------------------------------------------------+

create table exemplaire(
   id  serial primary key, 
   id_livre int references livre(id)
  
)

-- +----------------------------------------------------------------------------------------------+
-- | Insertion de quelques données de pour les tests                                              |
-- +----------------------------------------------------------------------------------------------+

insert into exemplaire values(nextval('exemplaire_id_seq'), 1);    -- id = 1