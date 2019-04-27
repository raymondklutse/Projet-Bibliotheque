create table emprunt(
   id     serial primary key,
   id_exemplaire int references exemplaire(id),
   id_abonne int references abonne(id),
   date_emprunt date ,
   date_retour date
)
