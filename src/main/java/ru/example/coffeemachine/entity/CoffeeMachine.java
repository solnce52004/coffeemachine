package ru.example.coffeemachine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import ru.example.coffeemachine.config.statemachine.enums.States;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "coffee_machines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class CoffeeMachine {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "machine_id", nullable = false)
    String machineId;

    @Column(name = "machine_uuid", nullable = false)
    String machineUUID;

    @Column(name = "state", nullable = false)
    States state;

    ///
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "resource_id")
    @Fetch(value = FetchMode.JOIN)
    private Resource resource = new Resource();
    ///

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoffeeMachine)) return false;
        CoffeeMachine that = (CoffeeMachine) o;
        return getMachineId().equals(that.getMachineId()) &&
                getMachineUUID().equals(that.getMachineUUID()) &&
                getState().equals(that.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMachineId(), getMachineUUID(), getState());
    }

    @Override
    public String toString() {
        return "CoffeeMachineInterface{" +
                "id=" + id +
                ", machineId='" + machineId + '\'' +
                ", machineUuid='" + machineUUID + '\'' +
                ", state='" + state + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
