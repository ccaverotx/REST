package payroll;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Cabina {

	private @Id @GeneratedValue Long id;
	private String name;
	private String role;

