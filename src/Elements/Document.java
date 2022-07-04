/**
 * @author Sabrina MOHAMMEDI
 * @author Vanessa MOHAMMEDI
 *
 * TP4 Projet JDBC
 *
 * Classe Document : Creation de la classe Document representant un document tel qu'un cours, un rapport, une facture, etc.
 * La classe Document est composee de ses caracteristiques c'est a dire son id, son nom, sa date de publication, son adresse de stockage,
 * l'id de son sujet, l'id de sa category et une liste de ses tags associes + getters et setters sur les attributs
 *
 */

package Elements;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class Document {

    private int documentID;
    private String documentName;
    private Date documentDate;
    private String storage;

    private int topicID;
    private List<Tag> tags;
    private int categoryID;

    /**
     * Constructeur de Document :
     * Initialise les attributs de Document, c'est a dire son nom, sa date, son adresse de stockage, son sujet, sa categorie et la liste de ses tags.
     */
    public Document(String documentName, Date documentDate, String storage, int topicID, int categoryID, List<Tag> tags) {
        this.documentName = documentName;
        this.documentDate = documentDate;
        this.storage = storage;
        this.topicID = topicID;
        this.categoryID = categoryID;
        this.tags = tags;
    }

    /**
     * Getter de documentID
     * @return l'attribut documentID du document
     */
    public int getDocumentID() {
        return documentID;
    }

    /**
     * Setter de l'attribut documentID
     * @param docId Nouvel id a associer au document
     */
    public void setDocumentID(int docId) {
        documentID = docId;
    }

    /**
     * Getter de documentName
     * @return l'attribut documentName du document
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Getter de documentDate
     * @return l'attribut documentDate du document
     */
    public Date getDocumentDate() {
        return documentDate;
    }

    /**
     * Getter de storage
     * @return l'attribut storage du document
     */
    public String getStorage() {
        return storage;
    }

    /**
     * Getter de topicID
     * @return l'attribut topicID du document
     */
    public int getTopic() {
        return topicID;
    }

    /**
     * Getter de tags
     * @return l'attribut tags du document
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Setter de l'attribut tags
     * @param listTags Nouvelle liste de tags a associer au document
     */
    public void setTags(List<Tag> listTags) {
        tags = listTags;
    }

    /**
     * Getter de categoryID
     * @return l'attribut categoryID du document
     */
    public int getCategory() {
        return categoryID;
    }
}
