package telef;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Cabina {

	private @Id @GeneratedValue Long id;
	private String Name; //Numero de cabina(?)
	private String color;

	Cabina() {}

	Cabina(String Name, String color) {
		this.Name = Name;
		this.color = color;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String name) {
		String[] parts =name.split(" ");
		this.Name = parts[0];
	}
}
