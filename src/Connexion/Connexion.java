/**
 * @author Sabrina MOHAMMEDI
 * @author Vanessa MOHAMMEDI
 *
 * TP4 Projet JDBC
 *
 * Classe Connexion : Creation de la classe Connexion representant une connexion a la base de donnees.
 * La classe Connexion est composee d'un attribut de type Connexion + fonctions gerant les requetes interragissant avec la base de donnees.
 *
 */

package Connexion;

import Elements.Document;
import Elements.Tag;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Connexion {

    Connection con = null;

    /**
     * Constructeur de Connexion :
     * Ajoute le driver de jdbc et cree une connexion a la base de donnees a l'aide de la fonction createConnexion().
     */
    public Connexion() {
        // Ajout du driver
        try {
            String classname = "com.mysql.cj.jdbc.Driver";
            Class.forName(classname);
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver cannot load, exception: " + ex.getMessage());
        }

        createConnexion();

    }

    /**
     * Cette fonction permet de creer la connexion a la base de donnees.
     */
    public void createConnexion() {
        String USERNAME = "root";//your_mysql_username
        String PASSWORD = "mdp";//your_mysql_password
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String URL = "jdbc:mysql://localhost:3306/databaseName";//"jdbc:mysql://localhost:3306/yourDBname

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established");
            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction permet d'inserer un nouveau document en base de donnees dont on connait la date de publication.
     * @param doc Document a inserer en base de donnees
     * @return L'id du document recupere apres son insertion en base de donnees a l'aide de la requete "SELECT LAST_INSERT_ID()".
     */
    public int writeDocumentInDB(Document doc) {
        // Insertion du document en base de donnees
        String query = "INSERT INTO Document (DocumentName, DocumentDate, StorageAddress, TopicID, CategoryID) VALUES('" + doc.getDocumentName() + "', '" + doc.getDocumentDate() + "', '" + doc.getStorage() + "', " + doc.getTopic() + ", " + doc.getCategory() + ")";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Le document a été enregistré !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion en base de données!");
        }

        // Recuperer l'id du dernier insert
        int DocID = 0;

        ResultSet rs = null;

        try {
            Statement stmt2 = con.createStatement();
            rs = stmt2.executeQuery("SELECT LAST_INSERT_ID()");

            while(rs.next()) {
                DocID = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur id Doc");
        }
        return DocID;
    }

    /**
     * Cette fonction permet d'inserer un nouveau document en base de donnees dont on ne connait pas encore la date de publication.
     * @param doc Document a inserer en base de donnees
     * @return L'id du document recupere apres son insertion en base de donnees a l'aide de la requete "SELECT LAST_INSERT_ID()".
     */
    public int writeDocumentInDBWithoutDate(Document doc) {
        // Insertion du document en base de donnees avec NULL dans la colonne DocumentDate pour signifier qu'il n'y a pas de date.
        String query = "INSERT INTO Document (DocumentName, DocumentDate, StorageAddress, TopicID, CategoryID) VALUES('" + doc.getDocumentName() + "', NULL, '" + doc.getStorage() + "', " + doc.getTopic() + ", " + doc.getCategory() + ")";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Le document a été enregistré sans la date !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion en base de données!");
        }


        // Recuperer l'id du dernier insert
        int DocID = 0;

        ResultSet rs = null;

        try {
            Statement stmt2 = con.createStatement();
            rs = stmt2.executeQuery("SELECT LAST_INSERT_ID()");

            while(rs.next()) {
                DocID = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur id Doc");
        }
        return DocID;
    }

    /**
     * Cette fonction permet de recuperer la liste de tous les elements de la table Document en vue de les afficher.
     * @return Liste des documents recuperes dans la table Document. Un element de la liste est de la forme "DocumentID. DocumentName  (DocumentDate)".
     */
    public List<String> getListDoc() {
        // Requete pour récupérer la liste des documents
        String query = "SELECT DocumentID, DocumentName, DocumentDate FROM Document";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(query);

            // On récupère la liste de tous les documents en base de données ainsi que leur ID afin de les afficher
            while(rs.next()) {
                result.add('\n' + rs.getString("DocumentID") + ". " + rs.getString("DocumentName") + "  (" + rs.getString("DocumentDate") + ")\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nous n'avons pas pu récupérer la liste des documents !");
        }
        return result;
    }

    /**
     * Cette fonction permet de mettre a jour la date d'un element de la table Document.
     * @param date La nouvelle date a renseigner dans la colonne DocumentDate d'un element de la table Document.
     * @param idDoc L'id de l'element de la table Document pour lequel on souhaite mettre a jour la date.
     */
    public void updateDateOfDoc(java.sql.Date date, int idDoc) {
        // Requete pour modifier la date du document selectionné
        String query2 = "UPDATE Document SET DocumentDate = '" + new java.sql.Date(date.getTime()) + "' WHERE DocumentID = " + idDoc + ";";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query2);
            System.out.println("La date du document a été modifié !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mofidification de la date !");
        }
    }

    /**
     * Cette fonction permet de recuperer la liste de tous les elements de la table Topic en vue de les afficher.
     * @return Liste des elements de la table Topic. Un element de la liste est de la forme "TopicID. TopicName".
     */
    public List<String> getListTopics() {
        // Requete pour récupérer la liste des topics
        String requete = "SELECT * FROM Topic";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            while(rs.next()) {
                result.add('\n' + rs.getString("TopicID") + ". " + rs.getString("TopicName") + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer la liste de tous les elements de la table Category en vue de les afficher.
     * @return Liste des elements de la table Category. Un element de la liste est de la forme "CategoryID. Name".
     */
    public List<String> getListCategory() {
        // Requete pour récupérer la liste des categories
        String requete = "SELECT * FROM Category";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            while(rs.next()) {
                result.add('\n' + rs.getString("CategoryID") + ". " + rs.getString("Name") + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer la liste de tous les elements de la table Tag en vue de les afficher.
     * @return Liste des elements de la table Tag. Un element de la liste est de la forme "TagID. TagName".
     */
    public List<String> getListTag() {
        // Requete pour récupérer la liste des tags
        String requete = "SELECT * FROM Tag";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            while(rs.next()) {
                result.add('\n' + rs.getString("TagID") + ". " + rs.getString("TagName") + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer la liste de tous les elements de la table Document en vue de les afficher.
     * On recupere l'ensemble des tags associes a chaque document avec la fonction getListDocumentWithTags.
     * @return Liste des documents recuperes dans la table Document. Un element de la liste est de la forme "DocumentName (DocumentDate) : Liste des tags associes au document".
     */
    public List<String> getListDocTags() {
        // requete pour recuperer les documents de la table Document
        String requete = "SELECT DocumentID, DocumentName, DocumentDate FROM Document";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            // On recupere l'ensemble des tags associes au document a l'aide de la fonction getListDocumentWithTags prenant en parametre l'id du document
            while(rs.next()) {
                result.add('\n' + rs.getString("DocumentName") + " (" + rs.getString("DocumentDate") + ") : " + getListDocumentWithTags(rs.getInt("DocumentID")) + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer la liste des documents appartemant a une categorie donnee.
     * @param category L'id de la categorie pour laquelle on veut obtenir la liste des documents y etant associes.
     * @return Liste des documents recuperes dans la table Document associes a la categorie dont l'id est donne en parametre. Un element de la liste est de la forme "DocumentName    DocumentDate    StorageAddress     Liste des tags associes au document".
     */
    public List<String> getListDocumentByCategory(int category) {
        // requete pour recuperer les documents associes a la categorie passee en parametre de la fonction
        String requete = "SELECT DocumentID, DocumentName, DocumentDate, StorageAddress FROM Document INNER JOIN Category ON Document.CategoryID = Category.CategoryID WHERE Document.CategoryID = " + category + ";";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            // On recupere l'ensemble des tags associes au document a l'aide de la fonction getListDocumentWithTags prenant en parametre l'id du document
            while(rs.next()) {
                result.add('\n' + rs.getString("DocumentName") + "     " + rs.getString("DocumentDate") + "     " + rs.getString("StorageAddress") + '\t' + getListDocumentWithTags(rs.getInt("DocumentID")) + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer la liste des documents appartemant a un sujet donne.
     * @param topic L'id du topic pour lequel on veut obtenir la liste des documents y etant associes.
     * @return Liste des documents recuperes dans la table Document associes au topic dont l'id est donne en parametre. Un element de la liste est de la forme "DocumentName    DocumentDate    StorageAddress     Liste des tags associes au document".
     */
    public List<String> getListDocumentByTopic(int topic) {
        // requete pour recuperer les documents associes au topic passe en parametre de la fonction
        String requete = "SELECT DocumentID, DocumentName, DocumentDate, StorageAddress FROM Document INNER JOIN Topic ON Document.TopicID = Topic.TopicID WHERE Document.TopicID = " + topic + ";";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            // On recupere l'ensemble des tags associes au document a l'aide de la fonction getListDocumentWithTags prenant en parametre l'id du document
            while(rs.next()) {
                result.add('\n' + rs.getString("DocumentName") + "     " + rs.getString("DocumentDate") + "     " + rs.getString("StorageAddress") + '\n' + getListDocumentWithTags(rs.getInt("DocumentID")) + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer la liste des tags associes a un document donne.
     * @param docId L'id du document pour lequel on veut obtenir la liste des tags y etant associes.
     * @return La chaine de caractere representant l'ensemble des tags associes au document dont l'id est donne en parametre. Un element de la liste est de la forme "Liste des tags associes au document".
     */
    public String getListDocumentWithTags(int docId) {
        // requete pour recuperer l'ensemble des tags associes au document
        String requete = "SELECT Document.DocumentName, tags.TagName as TagN FROM (SELECT Tag.TagName, Avoir.DocumentID FROM Avoir INNER JOIN Tag ON Avoir.TagID = Tag.TagID WHERE DocumentID = " + docId + ") as tags INNER JOIN Document ON tags.DocumentID = Document.DocumentID;";

        // chaine de caracteres retournee
        String result = "";
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            while(rs.next()) {
                result += '#' + rs.getString("TagN") + " ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer la liste des documents associes a un tag donne.
     * @param tag L'id du tag pour lequel on veut obtenir la liste des documents y etant associes.
     * @return Liste des documents recuperes dans la table Document associes au tag dont l'id est donne en parametre. Un element de la liste est de la forme "DocumentName    DocumentDate    StorageAddress     Liste des tags associes au document".
     */
    public List<String> getListDocumentByTag(int tag) {
        // requete pour recuperer les documents associes au topic passe en parametre de la fonction
        String requete = "SELECT DocumentID, DocumentName, DocumentDate, StorageAddress, TagName, list_docs.TagID FROM (SELECT Document.DocumentID, DocumentName, DocumentDate, StorageAddress, TagID FROM Document INNER JOIN Avoir ON Document.DocumentID = Avoir.DocumentID) as list_docs INNER JOIN (SELECT DISTINCT TagName, Tag.TagID FROM Avoir INNER JOIN Tag ON Avoir.TagID = Tag.TagID) AS list_tag ON list_docs.TagID = list_tag.TagID WHERE list_tag.TagID = " + tag + ";";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            // On recupere l'ensemble des tags associes au document a l'aide de la fonction getListDocumentWithTags prenant en parametre l'id du document
            while(rs.next()) {
                result.add('\n' + rs.getString("DocumentName") + "     " + rs.getString("DocumentDate") + "     " + rs.getString("StorageAddress") + '\t' + getListDocumentWithTags(rs.getInt("DocumentID")) + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet de recuperer le sujet le plus frequent.
     * @return Liste contenant le nom du sujet le plus frequent.
     */
    public List<String> getPlusFrequentTopic() {
        // requete pour recuperer le sujet le plus frequent
        String requete = "SELECT Topic.TopicName FROM Topic INNER JOIN (SELECT TopicID, count(*) as nb_occ FROM Document GROUP BY TopicID ORDER BY nb_occ DESC) AS topic_occ ON Topic.TopicID = topic_occ.TopicID LIMIT 1;";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            while(rs.next()) {
                result.add('\n' + rs.getString("TopicName") + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet d'afficher le nombre d'occurrences de chaque element de la table Tag.
     * @return Liste contenant le nom de chaque tag associe a son nombre d'occurrences. Un element de la liste est de la forme "TagName: nombre d'occurence".
     */
    public List<String> getNbOccTags() {
        // requete pour recuperer l'id, le nom et le nombre d'ocurrence de chaque tag
        String requete = "SELECT Avoir.TagID, Tag.TagName, count(*) as nb_occ FROM Avoir INNER JOIN Tag ON Tag.TagID = Avoir.TagID GROUP BY Avoir.TagID;";
        List<String> result = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            var rs = stmt.executeQuery(requete);
            while(rs.next()) {
                result.add('\n' + rs.getString("TagName") + " : " + rs.getString("nb_occ") + '\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cette fonction permet d'enregistrer en base de donnees un nouveau tag si celui ci n'existe pas, puis de recuperer son id apres insertion a l'aide de la requete "SELECT LAST_INSERT_ID()" afin de l'associer a l'id du document passe en parametre de la fonction, dans la table Avoir de la base de donnees.
     * Dans le cas ou le tag existe deja dans la table Tag de la base de donnees, alors nous recuperons seulement son id afin de l'associer a l'id du document passe en parametre de la fonction, dans la table Avoir de la base de donnees.
     * @param tags Liste des tags a associe au document dont on renseigne l'id en parametre de la fonction
     * @param docID L'id du document auquel on veut associer la liste de tags passee en parametre de la fonction.
     */
    public void writeTagsInDB(List<Tag> tags, int docID) throws SQLException {
        // Contient l'ensemble des id des tags a associer au document
        List<Integer> listeIdTags = new ArrayList<>();

        // ResultSet contenant l'ensemble des id des tags que l'on veut associer au doc que l'on vient d'inserer en base de donnees. C'est a dire qu'ils n'existaient pas auparavant.
        ResultSet rsTags = null;

        // ResultSet contenant l'ensemble des id des tags que l'on veut associer au doc mais qui existaient deja en base de données.
        ResultSet rsIDTag = null;

        for(Tag tg: tags) {
            try {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                rsIDTag = stmt.executeQuery("SELECT TagID FROM Tag WHERE TagName = '" + tg.getName() + "';");


                // Si rsIDTag est vide, c'est a dire si le tag n'existe pas en base de donnees
                if(!rsIDTag.first()) {
                        try {
                            Statement stmt2 = con.createStatement();
                            // On insert le nouveau tag en base de donnees.
                            stmt2.executeUpdate("INSERT INTO Tag (TagName) VALUES ('" + tg.getName() + "');");
                            System.out.println("Le tag " + tg.getName() + " a été inséré en base de données!");

                            Statement stmt7 = con.createStatement();
                            // On recupere l'id du tag que l'on vient juste d'inserer en base de donnees
                            rsTags = stmt7.executeQuery("SELECT LAST_INSERT_ID()");

                            while(rsTags.next()) {
                                listeIdTags.add(rsTags.getInt(1));
                            }

                        } catch (SQLException ea) {
                            ea.printStackTrace();
                            System.out.println("Erreur lors de l'insertion du tag " + tg.getName() + " en base de données!");
                        }
                    }
                    // si le tag existe deja en base de donnees
                    else {
                        // On remet le curseur au début du ResultSet afin de le parcourir
                        rsIDTag.absolute(0);

                        while(rsIDTag.next()) {
                            listeIdTags.add(rsIDTag.getInt("TagID"));
                        }
                    }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Statement stmt4 = con.createStatement();

        // On parcourt la liste des id des tags a associer au document
        for(int idTag: listeIdTags) {
            System.out.println(idTag);
            try {
                // On associe dans la table Avoir un document à un tag
                stmt4.executeUpdate("INSERT INTO Avoir VALUES (" + docID + "," + idTag + ");");
                System.out.println("Le tag a été associé au document!");
            } catch (SQLException e) {
                System.out.println("Le tag n'a pas été associé au document !");
                e.printStackTrace();
            }
        }
    }
}
