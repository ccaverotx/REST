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
	private String Color;

	Cabina() {}

	Cabina(String Name, String Color) {
		this.Name = Name;
		this.Color = Color;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String name) {
		String[] parts =name.split(" ");
		this.Name = parts[0];
	}
}
