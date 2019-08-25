package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Image {
	 @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	public String imagename;
	public Image() {}
	
}
