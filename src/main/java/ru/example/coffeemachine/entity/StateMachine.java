package ru.example.coffeemachine.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import ru.example.coffeemachine.config.statemachine.enums.States;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "state_machine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class StateMachine {
    @Id
    @Column(name = "machine_id")
    String machineId;

    @Column(name = "state")
    States state;

    @Column(name = "state_machine_context")
    String stateMachineContext;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StateMachine that = (StateMachine) o;

        return Objects.equals(machineId, that.machineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMachineId(), getState(), getStateMachineContext());
    }

    @Override
    public String toString() {
        return "StateMachine{" +
                "machineId='" + machineId + '\'' +
                ", state='" + state + '\'' +
                ", stateMachineContext='" + stateMachineContext + '\'' +
                '}';
    }
}
