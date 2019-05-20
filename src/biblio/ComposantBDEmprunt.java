package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Composant logiciel assurant la gestion des emprunts d'exemplaires
 * de livre par les abonnés.
 */
public class ComposantBDEmprunt {

  /**
   * Retourne le nombre total d'emprunts en cours référencés dans la base.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours() throws SQLException {
	  int nbEmpruntsEnCours;
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "select count(*) from emprunt";
	  ResultSet rset = stmt.executeQuery(sql);
	  rset.next();
	  nbEmpruntsEnCours=rset.getInt("count");
	  rset.close();
	  stmt.close();
	  
      return nbEmpruntsEnCours;
  }

  /**
   * Retourne le nombre d'emprunts en cours pour un abonné donné connu
   * par son identifiant.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours(int idAbonne) throws SQLException {
	  int nbEmpruntsEnCours;
	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "select count(*) from emprunt where id_abonne=" + idAbonne;
	  ResultSet rset = stmt.executeQuery(sql);
      rset.next();
      nbEmpruntsEnCours=rset.getInt("count");
      rset.close();
	  stmt.close();
	  
      return nbEmpruntsEnCours;
  }

  
  /**
   * Récupération de la liste complète des emprunts en cours.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours.<br/>
   * Il doit contenir 8 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours() throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();

    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select distinct exemplaire.id as id_exemplaire  ,exemplaire.id_livre as id_livre,livre.titre ,livre.auteur ,abonne.id as id_abonne ,abonne.nom as abonne_nom ,abonne.prenom as abonne_prenom,emprunt.date_emprunt "
    		+"from emprunt "
    		+"inner join abonne on emprunt.id_abonne=abonne.id " 
    		+"inner join exemplaire on emprunt.id_exemplaire=exemplaire.id " 
    		+"inner join livre on exemplaire.id_livre=livre.id " 
    		+"where emprunt.date_retour is null ";
	  ResultSet rset = stmt.executeQuery(sql);
	  String[] emprunt =new String[8];
	  System.out.println(sql);
	  
	  while(rset.next()){
	  emprunt[0]=rset.getString("id_exemplaire");
	  System.out.println("ID exemplaire : " + emprunt[0] + " \n");
	  emprunt[1]=rset.getString("id_livre");
	  emprunt[2]=rset.getString("titre");
	  emprunt[3]=rset.getString("auteur");
	  emprunt[4]=rset.getString("id_abonne");
	  emprunt[5]=rset.getString("abonne_nom");
	  emprunt[6]=rset.getString("abonne_prenom");
	  emprunt[7]=rset.getString("date_emprunt");
	  emprunts.add(emprunt);
	  System.out.println(rset.next() + " \n");
		  }
	  
	  rset.close();
	  stmt.close();
    return emprunts;
  }

  
  /**
   * Récupération de la liste des emprunts en cours pour un abonné donné.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours pour l'abonné.<br/>
   * Il doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours(int idAbonne) throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select distinct exemplaire.id as id_exemplaire  ,exemplaire.id_livre as id_livre,livre.titre ,livre.auteur ,abonne.id as id_abonne ,abonne.nom as abonne_nom ,abonne.prenom as abonne_prenom,emprunt.date_emprunt "
    		+"from emprunt "
    		+"inner join abonne on emprunt.id_abonne=abonne.id " 
    		+"inner join exemplaire on emprunt.id_exemplaire=exemplaire.id " 
    		+"inner join livre on exemplaire.id_livre=livre.id " 
    		+"where emprunt.date_retour is null and abonne.id = "+ idAbonne;
    ResultSet rset = stmt.executeQuery(sql);

	while(rset.next()){
	  String[] emprunt = new String[5];
	  emprunt[0]=rset.getString("id_exemplaire");
	  emprunt[1]=rset.getString("id_livre");
	  emprunt[2]=rset.getString("titre");
	  emprunt[3]=rset.getString("auteur");
	  emprunt[4]=rset.getString("date_emprunt");
	  
	  emprunts.add(emprunt);
	}
	  rset.close();
	  stmt.close();
    return emprunts;
    
  }

  /**
   * Récupération de la liste complète des emprunts passés.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt passé.<br/>
   * Il doit contenir 9 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   *   <li>8 : la date de retour</li>
   * </ul>
   * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsHistorique() throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select distinct exemplaire.id as id_exemplaire  ,exemplaire.id_livre as id_livre,livre.titre ,livre.auteur ,abonne.id as id_abonne ,abonne.nom as abonne_nom ,abonne.prenom as abonne_prenom,emprunt.date_emprunt,emprunt.date_retour "
    		+"from emprunt "
    		+"inner join abonne on emprunt.id_abonne=abonne.id " 
    		+"inner join exemplaire on emprunt.id_exemplaire=exemplaire.id " 
    		+"inner join livre on exemplaire.id_livre=livre.id " ;
    		//+"where emprunt.date_emprunt is not null";
	  ResultSet rset = stmt.executeQuery(sql);
	  
	  while(rset.next()){
	 
	
	  String[] emprunt =new String[9];
	  emprunt[0]=rset.getString("id_exemplaire");
	  emprunt[1]=rset.getString("id_livre");
	  emprunt[2]=rset.getString("titre");
	  emprunt[3]=rset.getString("auteur");
	  emprunt[4]=rset.getString("id_abonne");
	  emprunt[5]=rset.getString("abonne_nom");
	  emprunt[6]=rset.getString("abonne_prenom");
	  emprunt[7]=rset.getString("date_emprunt");
	  emprunt[8]=rset.getString("date_retour");
	  if(rset.getString("date_retour") == null){
		  emprunt[8]= " ";
	  }
	  
	  emprunts.add(emprunt);
	  }
      rset.close();
	  stmt.close();
    return emprunts;
  }

  /**
   * Emprunter un exemplaire à partir de l'identifiant de l'abonné et de
   * l'identifiant de l'exemplaire.
   * 
   * @param idAbonne : id de l'abonné emprunteur.
   * @param idExemplaire id de l'exemplaire emprunté.
   * @throws SQLException en cas d'erreur de connexion à la base.
   * @throws ParseException 
   */
  public static void emprunter(int idAbonne, int idExemplaire) throws SQLException, ParseException {
	  Statement stmt = Connexion.getConnection().createStatement();
	  Calendar calendar = Calendar.getInstance();
	  String date_emprunt = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); 
	  System.out.println("Date emprunt:" + date_emprunt);
	  
	  if(!estEmprunte(idExemplaire)){
		  System.out.println("Emprunter");
		  //String date_retour = " ";
		  String sql ="insert into emprunt(id_exemplaire,id_abonne,date_emprunt) values(" + idExemplaire + "," + idAbonne + "," + "'" + date_emprunt + "'" + " )";
		  System.out.println(sql);
		  stmt.executeUpdate(sql);
		  stmt.close();
	  }
	  
  }
  private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}

  /**
   * Retourner un exemplaire à partir de son identifiant.
   * 
   * @param idExemplaire id de l'exemplaire à rendre.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void rendre(int idExemplaire) throws SQLException {
	  Statement stmt = Connexion.getConnection().createStatement();
	  
	  Calendar calendar = Calendar.getInstance();
	  String date_retour = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); 
	  System.out.println("Date retour:" + date_retour);
	  
	  String sql ="update emprunt set date_retour = " + "'" + date_retour +"'" +"where id_exemplaire = " +idExemplaire;
	  System.out.println(sql);
	  stmt.executeUpdate(sql);
	  stmt.close();
  }
  
  /**
   * Détermine si un exemplaire sonné connu par son identifiant est
   * actuellement emprunté.
   * 
   * @param idExemplaire
   * @return <code>true</code> si l'exemplaire est emprunté, <code>false</code> sinon
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static boolean estEmprunte(int idExemplaire) throws SQLException {
    boolean estEmprunte ;
    Statement stmt = Connexion.getConnection().createStatement();
	  String sql ="select date_retour ,date_emprunt from emprunt where id_exemplaire=" + idExemplaire;
	  System.out.println(sql);
	  ResultSet rset = stmt.executeQuery(sql);
	  rset.next();
	  String R = null;
	  String E = null;
	  if(rset.next()){
		  R=rset.getString("date_retour");
		  E=rset.getString("date_emprunt");
	  }
	  
	  if(R==null){
		  if(E!=null){
			   estEmprunte = true;
		  }
		  else {
			  estEmprunte = false;
		  }
	  }
	  else{
		  estEmprunte = false;
	  }
	  
	  
    return estEmprunte;
    
  }

  /**
   * Récupération des statistiques sur les emprunts (nombre d'emprunts et de
   * retours par jour).
   * 
   * @return un <code>HashMap<String, int[]></code>. Chaque enregistrement de la
   * structure de données est identifiée par la date (la clé) exprimée sous la forme
   * d'une chaîne de caractères. La valeur est un tableau de 2 entiers qui représentent :
   * <ul>
   *   <li>0 : le nombre d'emprunts</li>
   *   <li>1 : le nombre de retours</li>
   * </ul>
   * Exemple :
   * <pre>
   * +-------------------------+
   * | "2017-04-01" --> [3, 1] |
   * | "2017-04-02" --> [0, 1] |
   * | "2017-04-07" --> [5, 9] |
   * +-------------------------+
   * </pre>
   *   
   * @throws SQLException
   */
  public static HashMap<String, int[]> statsEmprunts() throws SQLException
  {
    HashMap<String, int[]> stats = new HashMap<String, int[]>();
    
    //Connection pour les resultats d'emprunts
    Statement stmt1 = Connexion.getConnection().createStatement();
    String sql1 = "select date_emprunt ,count(*) from emprunt group by date_emprunt";
	ResultSet rset1 = stmt1.executeQuery(sql1);
	
	//Connection pour les resultats d'emprunts
	Statement stmt2 = Connexion.getConnection().createStatement();
	String sql2 = "select date_retour ,count(*) from emprunt group by date_retour";
	ResultSet rset2 = stmt2.executeQuery(sql2);
	
	LinkedList<String[]> emprunts = new LinkedList<String[]>();
	LinkedList<String[]> retours = new LinkedList<String[]>();
	
	// System.out.println(sql1);
	// System.out.println(sql2);
	 System.out.println("Emprunts");
	 while(rset1.next()){
		 String[] emprunt =new String[2];
		 //System.out.println("Start Comparing");
		 if(rset1.getString("date_emprunt")!=null){
			
			 emprunt[0]=rset1.getString("date_emprunt");
			 emprunt[1]=rset1.getString("count");
		
			 System.out.println(emprunt[0] + ":" + emprunt[1]);
			 System.out.println("");
			// System.out.println();
			 emprunts.add(emprunt);
			 System.out.println("");
		 }
	 }
	 System.out.println("Retours");
	while(rset2.next()){
		 String[] retour =new String[2];
		 
		 retour[0]=rset2.getString("date_retour");
		 retour[1]=rset2.getString("count");
		 
		 retours.add(retour);
	
		 System.out.println(retour[0] + ":" + retour[1]);
		 System.out.println("");
	 }
	
	//Comparing Items in list
	
	System.out.println("Size of Emprunts : " + emprunts.size());
	System.out.println("Size of Retours : " + retours.size());
	
	if(emprunts.size()!=0 && retours.size()!=0 ){
		for (String[] emprunt : emprunts){
			int[] nums = new int[2];
			
			//Nombre d'emprunts
			nums[0] = Integer.valueOf(emprunt[1]);
			
			for (String[] retour :retours){
				if(retour[0] != null){
					
					if(emprunt[0].compareTo(retour[0])==0){
						//Nombre d'retours
						 nums[1] = Integer.valueOf(retour[1]);
					 }
					else{
						
						if(stats.get(retour[0])==null){
							//System.out.println("Not present "+ retour[0]);
						
						}
						if(emprunts.contains(emprunt[0])){
							//nums[0] = 0;
							//nums[1] = Integer.valueOf(retour[1]);
						}
					
					}
				}
				stats.put(emprunt[0],nums); 
				
			}
			
		 }
		
		for (String[] retour: retours){
			int[] nums = new int[2];
			nums[1] = Integer.valueOf(retour[1]);
			
			for (String[] emprunt :emprunts){

				if(retour[0] != null){
					if(emprunt[0].compareTo(retour[0])==0){
						 nums[0] = Integer.valueOf(emprunt[1]);
						 nums[1] = Integer.valueOf(retour[1]);
					 }
					else{				
						if(stats.get(retour[0])==null){
							System.out.println("Not present "+ retour[0]);	
							
						}
						if(!retours.contains(retour[0])){
							//nums[0] = 0;
							//nums[1] = Integer.valueOf(retour[1]);
						}				
					}
					stats.put(retour[0],nums); 				
				}
			}
		}
	}
	
	rset1.close();
	rset2.close();
	stmt1.close();
	stmt2.close();
	return stats;
	 
  }
  
}
