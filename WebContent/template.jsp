<%--
  Fichier JSP : template.jsp
  ==========================
  
  1 - Organisation de la pr�sentation des pages
      -----------------------------------------
  
  Le fichier "template.jsp" est la page principale de l'application Web, c'est un
  gabarit qui sert aux pages normales ainsi qu'� la page d'erreur.
  Les pages sont organis�es de la mani�re suivante :
         +------------------------------------------------+
         | Bandeau                                        |
         +------------------------------------------------+
         | Menu          | Contenu qui varie suivant      |
         | de            | le contexte :                  |
         | l'application |   - liste des abonn�s ;        |
         |               |   - modification d'un livre ;  |
         |               |   - emprunt ;                  |
         |               |   - etc.                       |
         +------------------------------------------------+
  Le bandeau, le menu, etc. sont factoris�s au sein de fichiers externes localis�s dans
  le r�pertoire "fragments" (au sein du r�pertoire "WebContent"). Ces diff�rents contenus
  sont int�gr�s dans la page au moyen de la directive JSP :
    <jsp:include page="fragments/contenu_a_inserer.html" />
  
  2 - Bootstrap
      ---------
  
  L'affichage Web de l'application utilise un framwork maintenant tr�s r�pendu, Bootstrap,
  voir :
    http://getbootstrap.com/
  Ce framework facilite grandement la mise en page de sites Web et est utilisable avec
  l'int�gralit� des langages utilis�s pour faire du Web dynamique : Java PHP, Python, etc.

  De nombreux th�mes Bootstrap sont disponibles (libres ou payants). Le th�me utilis�
  pour le projet biblioth�que est le th�me SB Admin 2. Voir :
    https://startbootstrap.com/template-overviews/sb-admin-2/
  
  Comme tout site Web utilisant le framwork Bootstrap, l'application biblioth�que est dite
  "responsive", son contenu s'adapte automatiquement au type de terminal utilis� : ordinateur,
  tablette, t�l�phone, etc.
  
  Le th�me SB Admin 2 et les fichiers n�cessaires au framework Bootstrap sont localis�s dans
  le r�pertoire "WebContent/bootstrap". Une d�monstration des diff�rents composants graphiques
  fournis par le th�me SB Admin 2 est consultable en suivant le lien :
    http://localhost:8080/bibliotheque/bootstrap/pages/
--%>

<%--<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/erreur.jsp" %>
<%@page import="java.util.Arrays"%>
<%
	String action = request.getParameter("action");
	action = (action==null || action.equals("") ? "accueil" : action);
	/*
	 * Note : le tableau"actions" contient la liste des JSP de l'application. Attention,
	 * celle-ci doit �tre tri�e dans l'ordre alphab�tique !
	 */
	String[] actions = {
	                     "accueil",
	                     "emprunter_exemplaire",
                       "emprunts_en_cours",
                       "emprunts_historique",
                       "help",
                       "help_generalites",
                       "infos_abonne",
                       "infos_livre",
                       "inserer_abonne",
                       "inserer_livre",
                       "liste_abonnes",
                       "liste_livres",
                       "retourner_exemplaire"
	                   };
%>
<!DOCTYPE html>
<html lang="en">
  <jsp:include page="fragments/head.html" />
  <body>
    <div id="wrapper">
	    <!-- Navigation -->
	    <nav class="navbar
	                navbar-default
	                navbar-static-top"
	         role="navigation"
	         style="margin-bottom: 0">
	      <jsp:include page="fragments/bandeau.html" />
        <jsp:include page="fragments/menu.html" />
      </nav>
      <div id="page-wrapper">
	<p style="font-size: 5">&nbsp;</p>
        <%
        if(Arrays.binarySearch(actions, action) >= 0)
        {
          String content = null;
          if(action.startsWith("help"))
          {
            content = action + ".html";
          }
          else
          {
            content = action + ".jsp";
          }
          %>
          <jsp:include page="<%=content%>" />
          <%
        }
        else
        {
          %>
	        <div class="row">
	          <div class="alert alert-danger
                        col-xs-offset-2 col-xs-8
	                      text-center">
	            <h3>Fonctionnalit� inexistante</h3>
	          </div> <!-- /.col-lg-12 -->
	        </div> <!-- /.row -->
          <%
        }
        %>
      </div> <!-- /#page-wrapper -->
    </div> <!-- /#wrapper -->
    <jsp:include page="fragments/fin_de_page.jsp" />
	</body>
</html>
