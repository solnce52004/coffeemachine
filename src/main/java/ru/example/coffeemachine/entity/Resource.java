package ru.example.coffeemachine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "resources")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Resource {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "resource_uuid", nullable = false)
    String resourceUUID = UUID.randomUUID().toString();

    @Column(name = "water", nullable = false)
    Integer water = 0;

    @Column(name = "coffee", nullable = false)
    Integer coffee = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS) //генерит время +3
    @Column(name="created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name="updated_at", insertable = false, columnDefinition = "TIMESTAMP")
    private Date updatedAt;

    ///
    @OneToMany(
            mappedBy = "resource",
            fetch = FetchType.LAZY
//            , cascade = CascadeType.ALL
    )
    @Fetch(value = FetchMode.JOIN)
    private Set<CoffeeMachine> coffeeMachines = new HashSet<>();
    ///

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;
        Resource resource = (Resource) o;
        return getResourceUUID().equals(resource.getResourceUUID()) && getWater().equals(resource.getWater()) && getCoffee().equals(resource.getCoffee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResourceUUID(), getWater(), getCoffee());
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", resourceId='" + resourceUUID + '\'' +
                ", water=" + water +
                ", coffee=" + coffee +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
