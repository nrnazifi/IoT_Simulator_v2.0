package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false; // null or other class
        }
        AbstractEntity other = (AbstractEntity) obj;

        if (id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }
}
