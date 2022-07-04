/**
 * @author Sabrina MOHAMMEDI
 * @author Vanessa MOHAMMEDI
 *
 * TP4 Projet JDBC
 *
 * Classe Category : Creation de la classe Category representant une categorie.
 * La classe Category est composee d'un nom et d'un id + getters et setters sur les attributs
 *
 */

package Elements;

public class Category {

    private int categoryID;
    private String name;

    /**
     * Constructeur de Category :
     * Initialise l'attribut name de l'instance creee de Category.
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Getter de categoryID
     * @return l'attribut categoryID de la categorie
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * Getter de name
     * @return l'attribut name de la categorie
     */
    public String getName() {
        return name;
    }

    /**
     * Setter de l'attribut categoryID
     * @param id Nouvel id a associer a la categorie
     */
    public void setCategoryID(int id) {
        categoryID = id;
    }
}
