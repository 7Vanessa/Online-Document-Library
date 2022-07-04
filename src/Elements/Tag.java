/**
 * @author Sabrina MOHAMMEDI
 * @author Vanessa MOHAMMEDI
 *
 * TP4 Projet JDBC
 *
 * Classe Tag : Creation de la classe Tag representant un tag.
 * La classe Tag est composee d'un nom et d'un id + getters et setters sur les attributs
 *
 */

package Elements;

public class Tag {

    private int tagID;
    private String name;

    /**
     * Constructeur de Tag :
     * Initialise l'attribut name de l'instance creee de Tag.
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Getter de tagID
     * @return l'attribut tagID du tag
     */
    public int getTagID() {
        return tagID;
    }

    /**
     * Getter de name
     * @return l'attribut name du tag
     */
    public String getName() {
        return name;
    }

    /**
     * Setter de l'attribut tagID
     * @param id Nouvel id a associer au tag
     */
    public void setTagID(int id) {
        tagID = id;
    }
}
