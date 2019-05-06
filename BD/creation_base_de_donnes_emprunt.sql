-- Titre             : Script SQL (PostgreSQL) de création de la base de données du projet bibliothèque
-- Version           : 1.0
-- Date création     : 07 mars 2006
-- Date modification : 9 avril 2017
-- Auteur            : Qu Runlu
-- Description       : Ce script est une ébauche, à compléter, qui permet la création de la table
--                     "emprunt" pour la réalisation de la fonctionnalité "liste de tous les livres".

-- +----------------------------------------------------------------------------------------------+
-- | Suppression des tables                                                                       |
-- +----------------------------------------------------------------------------------------------+

drop table if exists "emprunt";

-- +----------------------------------------------------------------------------------------------+
-- | Création des tables                                                                          |
-- +----------------------------------------------------------------------------------------------+
create table emprunt(
   id     serial primary key,
   id_exemplaire int references exemplaire(id),
   id_abonne int references abonne(id),
   date_emprunt date ,
   date_retour date
)
-- +----------------------------------------------------------------------------------------------+
-- | Insertion de quelques données de pour les tests                                              |
-- +----------------------------------------------------------------------------------------------+

--insert into emprunt values(nextval('livre_id_seq'), '2-84177-042-7', NULL,                'JDBC et JAVA',                            'George REESE');    -- id = 1



-- +----------------------------------------------------------------------------------------------+
-- | Selection de quelques données de pour les tests                                              |
-- +----------------------------------------------------------------------------------------------+

--select EX.id as id_exemplaire,EX.id_livre as id_livre,L.titre as livre_titre,L.auteur as livre_auteur,EM.date_emprunt as date_emprunt
--from exemplaire EX
--inner join livre L on EX.id_livre=L.id)
--inner join emprunt EM on EX.id = EM.id_exemplaire
--where EM.id_abonne = " + idAbonne
