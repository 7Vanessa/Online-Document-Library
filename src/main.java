/**
 * @author Sabrina MOHAMMEDI
 * @author Vanessa MOHAMMEDI
 *
 * TP4 Projet JDBC
 *
 * Programme main
 *
 */

import Connexion.Connexion;
import Elements.Document;
import Elements.Tag;

import java.beans.PropertyEditorSupport;
import java.sql.SQLException;
import java.util.*;

public class main {

    /**
     * Cette fonction permet de demander a l'utilisateur d'effectuer une saisie tant que l'element renseigne n'est pas un entier.
     * @return Un entier saisi par l'utilisateur.
     */
    public static int getInt() {
        int choix = 0;
        boolean mismatch = true;
        while(mismatch) {
            try {
                Scanner sc = new Scanner(System.in);
                choix = sc.nextInt();
                mismatch = false;
            } catch (InputMismatchException e) {
                System.out.println("\nRecommencez!");
            }
        }
        return choix;
    }

    /**
     * Cette fonction permet d'afficher les differentes actions possibles du programme.
     */
    public static void affichageMenu() {
        System.out.println("\nQuelle action souhaitez-vous réaliser ?\n");
        System.out.println("\t1. Enregistrer un nouveau document en base de données.");
        System.out.println("\t2. Récupérer la liste des documents en fonction de la catégorie.");
        System.out.println("\t3. Récupérer la liste des documents en fonction du tag.");
        System.out.println("\t4. Récupérer la liste des documents en fonction du topic.");
        System.out.println("\t5. Recupérer le topic le plus fréquent.");
        System.out.println("\t6. Recupérer le nombre d'occurences de chaque tag.");
        System.out.println("\t7. Enregistrer un nouveau document en base de données sans la date.");
        System.out.println("\t8. Modifier la date d'un document.");
        System.out.println("\t9. Ajouter un tag à un document.");
        System.out.println("\t10. Récupérer la liste des documents.");
        System.out.println("\t11. Quitter le programme.");
    }


    /**
     * Cette fonction permet de recuperer la liste des tags entres par l'utilisateur.
     * @return La liste des tags saisis au clavier par l'utilisateur.
     */
    public static List<Tag> getListTag() {
        Scanner sc = new Scanner(System.in);
        String tags = sc.nextLine();

        // Liste des tags a retourner
        List<Tag> tagsList = new ArrayList<>();

        // On recupere chaque mot de la chaine de caractere dans un tableau
        String[] tagsArray = tags.split(" ");

        // On parcourt le tableau afin d'ajouter chaque element pour creer un nouveau tag et l'ajouter a la liste de tags
        for(int i=0; i<tagsArray.length; i++) {
            Tag tag = new Tag(tagsArray[i]);
            tagsList.add(tag);
        }
        return tagsList;
    }

    /**
     * Cette fonction permet de recuperer toutes les infos afin de creer un nouveau document dont on connait la date de publication et insere ce document en base de donnees.
     * @param myCo Connexion a la base de donnees
     * @return Le nouveau document cree.
     */
    public static Document createDoc(Connexion myCo) {
        System.out.println("\nSelectionnez le numéro d'une categorie: \n");
        System.out.println(myCo.getListCategory());
        int category = getInt();

        System.out.println("\nSelectionnez le numéro d'un topic: \n");
        System.out.println(myCo.getListTopics());
        int topic = getInt();

        Scanner sc = new Scanner(System.in);
        System.out.println("\nEntrez un titre au document: \n");
        String docName = sc.nextLine();

        Scanner sc2 = new Scanner(System.in);
        System.out.println("\nEntrez l'adresse de stockage du document: \n");
        String storage = sc2.nextLine();

        System.out.println("Entrez un ou plusieurs tags séparé d'un espace: \n");
        List<Tag> docTags = getListTag();

        // creation du document
        Document newDoc = new Document(docName, new java.sql.Date(getValidDate().getTime()), storage, topic, category, docTags);
        // Insert le nouveau document en base de donnees et set son attribut id avec la valeur retournee a la suite de l'insertion
        newDoc.setDocumentID(myCo.writeDocumentInDB(newDoc));

        return newDoc;
    }

    /**
     * Cette fonction permet de recuperer toutes les infos afin de creer un nouveau document dont on ne connait pas la date de publication et insere ce document en base de donnees.
     * @param myCo Connexion a la base de donnees
     * @return Le nouveau document cree.
     */
    public static Document createDocWithoutDate(Connexion myCo) {
        System.out.println("\nSelectionnez le numéro d'une categorie: \n");
        System.out.println(myCo.getListCategory());
        int category = getInt();

        System.out.println("\nSelectionnez le numéro d'un topic: \n");
        System.out.println(myCo.getListTopics());
        int topic = getInt();

        Scanner sc = new Scanner(System.in);
        System.out.println("\nEntrez un titre au document: \n");
        String docName = sc.nextLine();

        Scanner sc2 = new Scanner(System.in);
        System.out.println("\nEntrez l'adresse de stockage du document: \n");
        String storage = sc2.nextLine();

        System.out.println("Entrez un ou plusieurs tags séparé d'un espace: \n");
        List<Tag> docTags = getListTag();

        // creation du document
        Document newDoc = new Document(docName, null, storage, topic, category, docTags);
        // Insert le nouveau document en base de donnees et set son attribut id avec la valeur retournee a la suite de l'insertion
        newDoc.setDocumentID(myCo.writeDocumentInDBWithoutDate(newDoc));

        return newDoc;
    }

    /**
     * Cette fonction permet de creer une date au format sql.
     * @return La date sql a associer a un document en base de donnees.
     */
    public static java.sql.Date getValidDate() {
        System.out.println("Veuillez entrer une année au format yyyy: \n");
        int year = getInt();

        System.out.println("Veuillez entrer un mois au format mm: \n");
        int month = getInt();

        System.out.println("Veuillez entrer un jour au format dd: \n");
        int day = getInt();

        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month-1, day);

        return new java.sql.Date(calendar.getTimeInMillis());
    }


    public static void main(String[]args) throws ClassNotFoundException, SQLException {

        // Création d'une connexion à la base de données
        Connexion myCo = new Connexion();

        // Creation d'une instance de document et insertion en base de donnees
        /*Document doc1 = new Document("Java pour les nuls", new java.sql.Date(new java.util.Date().getTime()), "Rayon B3", 1, 6);
        myCo.writeDocumentInDB(doc1);

        Document doc2 = new Document("C# for the begginers", new java.sql.Date(new java.util.Date().getTime()), "Rayon A2", 2, 6);
        myCo.writeDocumentInDB(doc2);

        Document doc3 = new Document("English for business", new java.sql.Date(new java.util.Date().getTime()), "Rayon A6", 5,6);
        myCo.writeDocumentInDB(doc3);

        Document doc4 = new Document("Biologie intermediaire", new java.sql.Date(new java.util.Date().getTime()), "Rayon C4", 6,6);
        myCo.writeDocumentInDB(doc4);

        Document doc5 = new Document("Algorithmique en exercices", new java.sql.Date(new java.util.Date().getTime()), "Rayon D8", 3,6);
        myCo.writeDocumentInDB(doc5);*/


        Boolean actif = true;

        while(actif) {

            affichageMenu();
            int choix = getInt();

            switch (choix) {
                case 1:
                    Document doc = createDoc(myCo);
                    myCo.writeTagsInDB(doc.getTags(), doc.getDocumentID());
                    break;

                case 2:
                    System.out.println("\nSelectionnez le numéro d'une categorie: \n");
                    System.out.println(myCo.getListCategory());
                    int category2 = getInt();

                    System.out.println("\nVoici la liste de tous les documents en rapport avec la categorie recherché: \n");
                    System.out.println(myCo.getListDocumentByCategory(category2));
                    break;

                case 3:
                    System.out.println("\nSelectionnez le numéro d'un tag: \n");
                    System.out.println(myCo.getListTag());
                    int tag2 = getInt();

                    System.out.println("\nVoici la liste de tous les documents en rapport avec le tag recherché: \n");
                    System.out.println(myCo.getListDocumentByTag(tag2));
                    break;

                case 4:
                    System.out.println("\nSelectionnez le numéro d'un topic: \n");
                    System.out.println(myCo.getListTopics());
                    int topic2 = getInt();

                    System.out.println("\nVoici la liste de tous les documents en rapport avec le topic recherché: \n");
                    System.out.println(myCo.getListDocumentByTopic(topic2));
                    break;

                case 5:
                    System.out.println("Le topic le plus fréquent est: " + myCo.getPlusFrequentTopic());
                    break;

                case 6:
                    System.out.println("Voici le nombre d'occurence de chaque tag: \n");
                    System.out.println(myCo.getNbOccTags());
                    break;

                case 7:
                    Document doc2 = createDocWithoutDate(myCo);
                    myCo.writeTagsInDB(doc2.getTags(), doc2.getDocumentID());
                    break;

                case 8:
                    // Recuperer l'id du doc a modifier
                    System.out.println(myCo.getListDoc());
                    System.out.println("Veuillez entrer le numéro du document selectionné: \n");
                    int docId = getInt();
                    myCo.updateDateOfDoc(getValidDate(), docId);
                    break;

                case 9:
                    System.out.println(myCo.getListDocTags());
                    System.out.println("Veuillez entrer le numéro du document selectionné: \n");
                    int id = getInt();
                    System.out.println("Entrez un ou plusieurs tags séparé d'un espace: \n");
                    myCo.writeTagsInDB(getListTag(), id);
                    break;

                case 10:
                    System.out.println(myCo.getListDocTags());
                    break;

                case 11:
                    System.exit(1);
                    break;

                default:
                    System.out.println("Entrez une valeur valide !");
                    break;

            }
        }
    }
}
