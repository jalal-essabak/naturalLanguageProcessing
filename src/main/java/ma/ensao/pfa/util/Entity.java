package ma.ensao.pfa.util;

public class Entity {
	/**
	 * Properties
	 */

	/**
	 * The salience score for an entity provides information about the importance or
	 * centrality of that entity to the entire document text. Scores closer to 0 are
	 * less salient, while scores closer to 1.0 are highly salient.
	 */
	private double salience;
	/**
	 * Represents the type of the entity, such as a person, an organization, or
	 * location.
	 */
	private String type;

	/**
	 * The actual entity this instance represents.
	 */

	private String entity;

	/**
	 * Parameterized constructor to initializes all fields when creating a new
	 * entity.
	 */

	public Entity(String entity, String type, float salience) {
		this.entity = entity;
		this.type = type;
		this.salience = salience;
	}

	/**
	 * ACCESSORS
	 */
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public double getSalience() {

		return salience;
	}

	public void setSalience(double salience) {
		this.salience = salience;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
