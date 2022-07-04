/**
 * @author Sabrina MOHAMMEDI
 * @author Vanessa MOHAMMEDI
 *
 * TP4 Projet JDBC
 *
 * Classe Topic : Creation de la classe Topic representant un sujet.
 * La classe Topic est composee d'un nom et d'un id + getters sur les attributs
 *
 */

package Elements;

public class Topic {

    private int topicID;
    private String subject;

    /**
     * Constructeur de Topic :
     * Initialise l'attribut subject de l'instance creee de Topic.
     */
    public Topic(String subject) {
        this.subject = subject;
    }

    /**
     * Getter de topicID
     * @return l'attribut topicID du sujet
     */
    public int getTopicID() {
        return topicID;
    }

    /**
     * Getter de subject
     * @return l'attribut subject du sujet
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter de l'attribut topicID
     * @param id Nouvel id a associer au sujet
     */
    public void setTopicID(int id) {
        topicID = id;
    }
}
