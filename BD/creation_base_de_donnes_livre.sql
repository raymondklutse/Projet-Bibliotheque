create table exemplaire(
   id     serial primary key, 
   id_livre int references livre(id)
  
)
